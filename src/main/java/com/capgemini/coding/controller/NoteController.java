package com.capgemini.coding.controller;

import com.capgemini.coding.exception.ResourceNotFoundException;
import com.capgemini.coding.model.Note;
import com.capgemini.coding.repository.CategoryRepository;
import com.capgemini.coding.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories/{id}/notes")
    public Page<Note> getAllNotesByCategoryId(@PathVariable Long id,
                                              Pageable pageable) {
        return noteRepository.findByCategoryId(id, pageable);
    }

    @PostMapping("/categories/{id}/notes")
    public Note createNote(@PathVariable Long id, @Valid @RequestBody Note note){
        return categoryRepository.findById(id).map(category -> {
            note.setCategory(category);
            return noteRepository.save(note);

        }).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    @PutMapping("/categories/{categoryId}/notes/{noteId}")
    public Note updateNote(@PathVariable (value= "categoryId") Long categoryId,
                           @PathVariable (value = "noteId") Long noteId,
                           @Valid @RequestBody Note noteRequest) {
        if(!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }

        return noteRepository.findById(noteId).map(note -> {
            note.setTitle(noteRequest.getTitle());
            note.setContent(noteRequest.getContent());
            return noteRepository.save(note);
        }).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    @DeleteMapping("/categories/{categoryId}/notes/{noteId}")
    public ResponseEntity<?>  deleteNote(@PathVariable (value = "categoryId") Long categoryId,
                                         @PathVariable (value = "noteId") Long noteId) {
        if(!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }

        return noteRepository.findById(noteId).map(note -> {
            noteRepository.delete(note);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    // Get All Notes
    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // Create a new Note
    @PostMapping("/notes")
    public Note createNote(@Valid @RequestBody Note note) {
        return noteRepository.save(note);
    }

    // Get a Single Note
    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
    }

    // Update a Note
    @PutMapping("/notes/{id}")
    public Note updateNote(@PathVariable Long id,
                           @Valid @RequestBody Note noteDetails) {

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        Note updatedNote = noteRepository.save(note);
        return updatedNote;
    }

    // Delete a Note
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));

        noteRepository.delete(note);

        return ResponseEntity.ok().build();
    }

}
