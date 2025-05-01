package com.homework.hogwartslibrary.domain.inventory;

import com.homework.hogwartslibrary.domain.Book;
import com.homework.hogwartslibrary.domain.BookRepository;
import com.homework.hogwartslibrary.domain.BookType;
import com.homework.hogwartslibrary.infrastructure.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Test
    void addBook_givenCorrectParams_shouldPersistBook() {
        inventoryService.addBook("The Hobbit", "Tolkien", 19.99, BookType.OLD_EDITION);

        final ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(captor.capture());

        final Book captured = captor.getValue();
        assertEquals("The Hobbit", captured.getTitle());
        assertEquals("Tolkien", captured.getAuthor());
        assertEquals(new BigDecimal("19.99"), captured.getBasePrice());
        assertEquals(BookType.OLD_EDITION, captured.getType());
        assertTrue(captured.isActive());
    }

    @Test
    void updateBook_givenNonExistentBookId_shouldThrowException() {
        final UUID id = UUID.randomUUID();

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> inventoryService.updateBook(id, "The Hobbit", "Tolkien", 19.99, BookType.OLD_EDITION));

        assertEquals("Book with ID " + id + " not found", exception.getMessage());
    }

    @Test
    void updateBook_givenCorrectParams_shouldUpdateBook() {
        final UUID id = UUID.randomUUID();
        final BookEntity existing = mock(BookEntity.class);

        when(existing.getId()).thenReturn(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(existing));
        when(bookRepository.update(eq(id), any(Book.class))).thenReturn(existing);

        final BookEntity updated = inventoryService.updateBook(id, "Clean Code", "Robert Martin", 33.0, BookType.REGULAR);

        final ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).update(eq(id), captor.capture());

        final Book captured = captor.getValue();
        assertEquals("Clean Code", captured.getTitle());
        assertEquals("Robert Martin", captured.getAuthor());
        assertEquals(new BigDecimal("33.0"), captured.getBasePrice());
        assertEquals(BookType.REGULAR, captured.getType());
        assertEquals(existing, updated);
    }

    @Test
    void removeBook_givenNonExistentBookId_shouldThrowException() {
        final UUID id = UUID.randomUUID();

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> inventoryService.removeBook(id));

        assertEquals("Book with ID " + id + " not found", exception.getMessage());
    }

    @Test
    void removeBook_givenCorrectParams_shouldUpdateBook() {
        final UUID id = UUID.randomUUID();
        final BookEntity existing = mock(BookEntity.class);

        when(existing.getId()).thenReturn(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(existing));

        inventoryService.removeBook(id);

        verify(bookRepository).delete(id);
    }


    @Test
    void getAvailableBooks_shouldReturnListFromRepository() {
        inventoryService.getAvailableBooks();

        verify(bookRepository).fetchAvailable();
    }
}