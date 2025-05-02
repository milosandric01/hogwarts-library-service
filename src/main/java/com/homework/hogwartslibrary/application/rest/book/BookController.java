package com.homework.hogwartslibrary.application.rest.book;

import com.homework.hogwartslibrary.domain.inventory.InventoryService;
import com.homework.hogwartslibrary.infrastructure.BookEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Add a new book to library",
            description = "Creates a new book and adds it to the inventory",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Book successfully created",
                            content = @Content(schema = @Schema(implementation = BookResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping
    ResponseEntity<BookResponse> addBook(@Valid @RequestBody final BookRequest request) {
        final BookEntity newBook = inventoryService.addBook(request.getTitle(), request.getAuthor(), request.getBasePrice(), request.getType());
        final BookResponse response = new BookResponse(newBook);

        return new ResponseEntity<>(response, CREATED);
    }

    @Operation(
            summary = "Update a book in library",
            description = "Updates book details by ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Book successfully updated",
                            content = @Content(schema = @Schema(implementation = BookResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable UUID id,
            @Valid @RequestBody BookRequest request) {

        final BookEntity updatedBook = inventoryService.updateBook(id, request.getTitle(), request.getAuthor(), request.getBasePrice(), request.getType());
        final BookResponse response = new BookResponse(updatedBook);

        return new ResponseEntity<>(response, CREATED);
    }

    @Operation(
            summary = "Get all available books from library",
            description = "Fetches all active book from inventory",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Books successfully fetched",
                            content = @Content(schema = @Schema(implementation = BookResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/available")
    public ResponseEntity<List<BookResponse>> getAvailableBooks() {

        final List<BookEntity> availableBooks = inventoryService.getAvailableBooks();

        List<BookResponse> response = availableBooks.stream().map(BookResponse::new).toList();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Remove a book (soft delete)",
            description = "Removes the book from active inventory by marking it as deleted",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Book successfully removed"),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }

    )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable final UUID id) {

        inventoryService.removeBook(id);

        return ResponseEntity.noContent().build();
    }
}
