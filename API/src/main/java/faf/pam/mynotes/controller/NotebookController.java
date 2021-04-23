package faf.pam.mynotes.controller;

import faf.pam.mynotes.domain.Note;
import faf.pam.mynotes.domain.Notebook;
import faf.pam.mynotes.dto.CreateNoteDto;
import faf.pam.mynotes.dto.CreateNotebookDto;
import faf.pam.mynotes.dto.UpdateNoteDto;
import faf.pam.mynotes.dto.UpdateNotebookDto;
import faf.pam.mynotes.service.NotebookService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notebooks")
@RequiredArgsConstructor
public class NotebookController {

  private final NotebookService notebookService;

  @GetMapping
  public ResponseEntity<List<Notebook>> listNotebooks(
      @RequestParam(required = false, defaultValue = "") String q) {

    List<Notebook> result = q.isBlank()
        ? notebookService.findAllNotebooks()
        : notebookService.searchNotebooks(q);

    return ResponseEntity.ok(result);
  }

  @GetMapping("{notebookId}")
  public ResponseEntity<Notebook> findNotebookById(@PathVariable String notebookId) {
    var notebookOptional = notebookService.findNotebookById(notebookId);
    return ResponseEntity.of(notebookOptional);
  }

  @PostMapping
  public ResponseEntity<Notebook> createNotebook(
      @RequestBody @Valid CreateNotebookDto notebookDto) {

    Notebook newNotebook = notebookService.createNotebook(notebookDto);
    return ResponseEntity.ok(newNotebook);
  }

  @PutMapping("{notebookId}")
  public ResponseEntity<Notebook> updateNotebook(
      @PathVariable String notebookId,
      @RequestBody @Valid UpdateNotebookDto notebookDto) {

    notebookDto.setId(notebookId);
    Notebook updatedNotebook = notebookService.updateNotebook(notebookDto);
    return ResponseEntity.ok(updatedNotebook);
  }

  @DeleteMapping("{notebookId}")
  public ResponseEntity<Void> deleteNotebook(@PathVariable String notebookId) {
    notebookService.deleteNotebook(notebookId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("{notebookId}/notes")
  public ResponseEntity<List<Note>> listNotebookNotes(
      @PathVariable String notebookId,
      @RequestParam(required = false, defaultValue = "") String q,
      @RequestParam(required = false, defaultValue = "false") boolean archived) {

    List<Note> result = q.isBlank()
        ? notebookService.findNotebookNotes(notebookId, archived)
        : notebookService.searchNotebookNotes(notebookId, q);

    return ResponseEntity.ok(result);
  }

  @GetMapping("{notebookId}/notes/{noteId}")
  public ResponseEntity<Note> findNotebookNote(
      @PathVariable String notebookId,
      @PathVariable String noteId) {

    var noteOptional = notebookService.findNoteById(notebookId, noteId);
    return ResponseEntity.of(noteOptional);
  }

  @PostMapping("{notebookId}/notes")
  public ResponseEntity<Note> createNotebookNote(
      @PathVariable String notebookId,
      @RequestBody @Valid CreateNoteDto noteDto) {

    Note newNote = notebookService.createNote(notebookId, noteDto);
    return ResponseEntity.ok(newNote);
  }

  @PutMapping("{notebookId}/notes/{noteId}")
  public ResponseEntity<Note> updateNotebookNote(
      @PathVariable String notebookId,
      @PathVariable String noteId,
      @RequestBody @Valid UpdateNoteDto noteDto) {

    noteDto.setId(noteId);
    Note updatedNote = notebookService.updateNote(notebookId, noteDto);
    return ResponseEntity.ok(updatedNote);
  }

  @PostMapping("{notebookId}/notes/{noteId}/archive")
  public ResponseEntity<Note> archiveNotebookNote(
      @PathVariable String notebookId,
      @PathVariable String noteId) {

    notebookService.archiveNote(notebookId, noteId);
    return ResponseEntity.accepted().build();
  }

  @PostMapping("{notebookId}/notes/{noteId}/restore")
  public ResponseEntity<Note> restoreNotebookNote(
      @PathVariable String notebookId,
      @PathVariable String noteId) {

    notebookService.restoreNote(notebookId, noteId);
    return ResponseEntity.accepted().build();
  }

  @DeleteMapping("{notebookId}/notes/{noteId}")
  public ResponseEntity<Void> deleteNotebookNote(
      @PathVariable String notebookId,
      @PathVariable String noteId) {

    notebookService.deleteNote(notebookId, noteId);
    return ResponseEntity.noContent().build();
  }
}
