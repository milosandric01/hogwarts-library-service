package com.homework.hogwartslibrary.application.rest;

import com.homework.hogwartslibrary.domain.Book;
import com.homework.hogwartslibrary.domain.service.InventoryService;
import com.homework.hogwartslibrary.infrastructure.BookEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final InventoryService inventoryService;

    @PostMapping
    ResponseEntity<BookResponse> addBook(@Valid @RequestBody final BookRequest request) {
        inventoryService.addBook(request.getTitle(), request.getAuthor(), request.getBasePrice(), request.getType(), request.getStockQuantity());
//        final BookResponse response = new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getBasePrice(), book.getType(), book.getStockQuantity(), book.getAvailability());

        return new ResponseEntity<>(null, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(
            @PathVariable UUID id,
            @Valid @RequestBody BookRequest request) {

        inventoryService.updateBook(id, request.getTitle(), request.getAuthor(), request.getBasePrice(), request.getType(), request.getStockQuantity());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookResponse>> getAvailableBooks() {

        final List<BookEntity> availableBooks = inventoryService.getAvailableBooks();

        List<BookResponse> response = availableBooks.stream().map(book ->
                new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getBasePrice(), book.getType(), book.getStockQuantity(), book.getAvailability())).toList();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {

        inventoryService.removeBook(id);

        return ResponseEntity.ok().build();
    }
}
