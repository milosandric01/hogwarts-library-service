package com.homework.hogwartslibrary.application.rest.book;

import com.homework.hogwartslibrary.domain.service.InventoryService;
import com.homework.hogwartslibrary.infrastructure.BookEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final InventoryService inventoryService;

    @PostMapping
    ResponseEntity<BookResponse> addBook(@Valid @RequestBody final BookRequest request) {
        final BookEntity newBook = inventoryService.addBook(request.getTitle(), request.getAuthor(), request.getBasePrice(), request.getType(), request.getStockQuantity());
        final BookResponse response = new BookResponse(newBook);

        return new ResponseEntity<>(response, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable UUID id,
            @Valid @RequestBody BookRequest request) {

        final BookEntity updatedBook = inventoryService.updateBook(id, request.getTitle(), request.getAuthor(), request.getBasePrice(), request.getType(), request.getStockQuantity());
        final BookResponse response = new BookResponse(updatedBook);

        return new ResponseEntity<>(response, CREATED);
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookResponse>> getAvailableBooks() {

        final List<BookEntity> availableBooks = inventoryService.getAvailableBooks();

        List<BookResponse> response = availableBooks.stream().map(BookResponse::new).toList();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable final UUID id) {

        inventoryService.removeBook(id);

        return ResponseEntity.noContent().build();
    }
}
