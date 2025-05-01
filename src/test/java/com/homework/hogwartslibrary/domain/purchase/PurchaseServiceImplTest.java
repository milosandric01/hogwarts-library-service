package com.homework.hogwartslibrary.domain.purchase;

import com.homework.hogwartslibrary.domain.*;
import com.homework.hogwartslibrary.domain.purchase.strategy.BookPricingStrategy;
import com.homework.hogwartslibrary.domain.purchase.strategy.BookPricingStrategyFactory;
import com.homework.hogwartslibrary.infrastructure.BookEntity;
import com.homework.hogwartslibrary.infrastructure.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookPricingStrategyFactory strategyFactory;
    @Mock
    private BookPricingStrategy pricingStrategy;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    private final UUID customerId = UUID.randomUUID();
    private final UUID bookIdOne = UUID.randomUUID();
    private final UUID bookIdTwo = UUID.randomUUID();
    private final BigDecimal bookPrice = BigDecimal.valueOf(100);

    @Test
    void purchaseBook_givenNonExistingCustomer_shouldThrowException() {
        when(customerRepository.fetch(any(UUID.class))).thenReturn(Optional.empty());

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                purchaseService.purchaseBook(customerId, List.of(bookIdOne), BookType.NEW_RELEASE)
        );

        assertEquals("Customer with ID " + customerId + " not found", exception.getMessage());

        verify(customerRepository, never()).updateLoyaltyPoints(any(), anyInt());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void purchaseBook_givenNonExistingBook_shouldThrowException() {
        final CustomerEntity customer = mockCustomer(0);

        when(customerRepository.fetch(customerId)).thenReturn(Optional.of(customer));

        when(bookRepository.fetch(any(UUID.class))).thenReturn(Optional.empty());

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                purchaseService.purchaseBook(customerId, List.of(bookIdOne), BookType.NEW_RELEASE)
        );

        assertEquals("Book with ID " + bookIdOne + " not found", exception.getMessage());

        verify(customerRepository, never()).updateLoyaltyPoints(any(), anyInt());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void purchaseBook_givenWrongLoyaltyDiscountBookType_shouldThrowException() {
        final CustomerEntity customer = mockCustomer(0);
        final BookEntity book = mock(BookEntity.class);
        when(book.getType()).thenReturn(null);

        when(customerRepository.fetch(customerId)).thenReturn(Optional.of(customer));

        when(bookRepository.fetch(any(UUID.class))).thenReturn(Optional.of(book));

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                purchaseService.purchaseBook(customerId, List.of(bookIdOne), BookType.NEW_RELEASE)
        );

        assertEquals("This book type does not support loyalty discount", exception.getMessage());

        verify(customerRepository, never()).updateLoyaltyPoints(any(), anyInt());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void purchaseBook_givenNotEnoughPoints_shouldNotApplyDiscount() {
        final BookType loyaltyDiscountBookType = BookType.REGULAR;
        final int customerLoyaltyPoints = 5;

        final BookEntity book = mockBook(bookIdOne, BookType.REGULAR, bookPrice);
        final CustomerEntity customer = mockCustomer(customerLoyaltyPoints);

        when(customerRepository.fetch(customerId)).thenReturn(Optional.of(customer));
        when(bookRepository.fetch(any(UUID.class))).thenReturn(Optional.of(book));
        when(strategyFactory.getStrategy(any(BookType.class))).thenReturn(pricingStrategy);
        when(pricingStrategy.calculatePrice(any(BookEntity.class), anyInt())).thenReturn(bookPrice);

        purchaseService.purchaseBook(customerId, List.of(bookIdOne), loyaltyDiscountBookType);

        assertCapturedOrder(
                bookPrice,
                List.of(bookPrice),
                List.of(bookPrice),
                6
        );
    }

    @Test
    void purchaseBook_givenEnoughPointsAndDifferentBookType_shouldNotApplyDiscount() {
        final BookType loyaltyDiscountBookType = BookType.REGULAR;
        final int customerLoyaltyPoints = 15;

        final BookEntity book = mockBook(bookIdOne, BookType.OLD_EDITION, bookPrice);
        final CustomerEntity customer = mockCustomer(customerLoyaltyPoints);

        when(customerRepository.fetch(customerId)).thenReturn(Optional.of(customer));
        when(bookRepository.fetch(any(UUID.class))).thenReturn(Optional.of(book));
        when(strategyFactory.getStrategy(any(BookType.class))).thenReturn(pricingStrategy);
        when(pricingStrategy.calculatePrice(any(BookEntity.class), anyInt())).thenReturn(bookPrice);

        purchaseService.purchaseBook(customerId, List.of(bookIdOne), loyaltyDiscountBookType);

        assertCapturedOrder(
                bookPrice,
                List.of(bookPrice),
                List.of(bookPrice),
                16
        );
    }

    @Test
    void purchaseBook_givenEnoughPointsAndCorrectBookType_shouldApplyDiscount() {
        final BookType loyaltyDiscountBookType = BookType.REGULAR;
        final int customerLoyaltyPoints = 15;

        final BookEntity book = mockBook(bookIdOne, BookType.REGULAR, bookPrice);
        final CustomerEntity customer = mockCustomer(customerLoyaltyPoints);

        when(customerRepository.fetch(customerId)).thenReturn(Optional.of(customer));
        when(bookRepository.fetch(any(UUID.class))).thenReturn(Optional.of(book));
        when(strategyFactory.getStrategy(any(BookType.class))).thenReturn(pricingStrategy);

        purchaseService.purchaseBook(customerId, List.of(bookIdOne), loyaltyDiscountBookType);

        assertCapturedOrder(
                BigDecimal.ZERO,
                List.of(bookPrice),
                List.of(BigDecimal.ZERO),
                0
        );
    }

    @Test
    void purchaseBook_givenEnoughPointsAndCorrectBookType_shouldApplyDiscountForOneBook() {
        final BookType loyaltyDiscountBookType = BookType.REGULAR;
        final int customerLoyaltyPoints = 15;

        final BookEntity bookOne = mockBook(bookIdOne, BookType.OLD_EDITION, bookPrice);
        final BookEntity bookTwo = mockBook(bookIdOne, BookType.REGULAR, bookPrice);
        final CustomerEntity customer = mockCustomer(customerLoyaltyPoints);

        when(customerRepository.fetch(customerId)).thenReturn(Optional.of(customer));
        when(bookRepository.fetch(bookIdOne)).thenReturn(Optional.of(bookOne));
        when(bookRepository.fetch(bookIdTwo)).thenReturn(Optional.of(bookTwo));
        when(strategyFactory.getStrategy(any(BookType.class))).thenReturn(pricingStrategy);
        when(pricingStrategy.calculatePrice(any(BookEntity.class), anyInt())).thenReturn(bookPrice);

        purchaseService.purchaseBook(customerId, List.of(bookIdOne, bookIdTwo), loyaltyDiscountBookType);

        assertCapturedOrder(
                bookPrice,
                List.of(bookPrice, bookPrice),
                List.of(bookPrice, BigDecimal.ZERO),
                0
        );
    }

    private void assertCapturedOrder(final BigDecimal expectedTotalPrice, final List<BigDecimal> expectedUnitPrices, final List<BigDecimal> expectedDiscountedPrices, final int expectedLoyaltyPoints) {
        final ArgumentCaptor<Integer> loyaltyPointsCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(customerRepository).updateLoyaltyPoints(any(), loyaltyPointsCaptor.capture());
        assertEquals(expectedLoyaltyPoints, loyaltyPointsCaptor.getValue());

        final ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());

        final Order capturedOrder = orderCaptor.getValue();
        assertEquals(expectedTotalPrice, capturedOrder.getTotalPrice());
        assertEquals(expectedUnitPrices.size(), capturedOrder.getOrderItems().size());

        for (int i = 0; i < expectedUnitPrices.size(); i++) {
            assertEquals(expectedUnitPrices.get(i), capturedOrder.getOrderItems().get(i).getUnitPrice(), "Unit price mismatch at index " + i);
            assertEquals(expectedDiscountedPrices.get(i), capturedOrder.getOrderItems().get(i).getDiscountedPrice(), "Discounted price mismatch at index " + i);
        }
    }

    private CustomerEntity mockCustomer(final int loyaltyPoints) {
        final CustomerEntity customer = mock(CustomerEntity.class);
        when(customer.getLoyaltyPoints()).thenReturn(loyaltyPoints);
        return customer;
    }

    private BookEntity mockBook(final UUID id, final BookType bookType, final BigDecimal bookPrice) {
        final BookEntity book = mock(BookEntity.class);
        when(book.getId()).thenReturn(id);
        when(book.getType()).thenReturn(bookType);
        when(book.getBasePrice()).thenReturn(bookPrice);
        return book;
    }
}