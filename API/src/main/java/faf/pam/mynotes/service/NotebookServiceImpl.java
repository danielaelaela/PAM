package faf.pam.mynotes.service;

import com.sun.tools.attach.AgentInitializationException;
import faf.pam.mynotes.domain.Note;
import faf.pam.mynotes.domain.Notebook;
import faf.pam.mynotes.dto.CreateNoteDto;
import faf.pam.mynotes.dto.CreateNotebookDto;
import faf.pam.mynotes.dto.UpdateNoteDto;
import faf.pam.mynotes.dto.UpdateNotebookDto;
import faf.pam.mynotes.repository.NotebookRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotebookServiceImpl implements NotebookService {

  private final NotebookRepository notebookRepository;

  @Override
  public List<Notebook> findAllNotebooks() {
    return notebookRepository.findAll();
  }

  @Override
  public List<Notebook> searchNotebooks(String query) {
    return findAllNotebooks().stream()
        .filter(notebook -> notebook.getName().contains(query))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Notebook> findNotebookById(String notebookId) {
    return notebookRepository.findById(notebookId);
  }

  @Override
  public Notebook createNotebook(CreateNotebookDto notebookDto) {
    if (findNotebookWithName(notebookDto.getName()).isPresent()) {
      throw new IllegalArgumentException("Notebook with this name already exists");
    }

    var newNotebook = new Notebook(notebookDto.getName(), notebookDto.getDescription());
    newNotebook = notebookRepository.create(newNotebook);

    return newNotebook;
  }

  private Optional<Notebook> findNotebookWithName(String name) {
    return notebookRepository.findAll().stream()
        .filter(notebook -> notebook.getName().equalsIgnoreCase(name))
        .findFirst();
  }

  @Override
  public Notebook updateNotebook(UpdateNotebookDto notebookDto) {
    if (findNotebookWithName(notebookDto.getName())
        /* Ignoring case when this name is owned by the current notebook */
        .filter(notebook -> !notebook.getId().equals(notebookDto.getId()))
        .isPresent()) {
      throw new IllegalArgumentException("Notebook with this name already exists");
    }

    Notebook notebook = ensureNotebookExists(notebookDto.getId());
    notebook.setName(notebookDto.getName());
    notebook.setDescription(notebookDto.getDescription());
    notebook = notebookRepository.update(notebook);

    return notebook;
  }

  private Notebook ensureNotebookExists(String notebookId) {
    return notebookRepository.findById(notebookId)
        .orElseThrow(() -> new IllegalArgumentException(
            "Notebook with ID '" + notebookId + "' does not exist"));
  }

  @Override
  public void deleteNotebook(String notebookId) {
    notebookRepository.delete(notebookId);
  }

  @Override
  public List<Note> findNotebookNotes(String notebookId) {
    Notebook notebook = ensureNotebookExists(notebookId);
    return notebook.getNotes();
  }

  @Override
  public List<Note> findNotebookNotes(String notebookId, boolean archived) {
    return findNotebookNotes(notebookId).stream()
        .filter(note -> note.isArchived() == archived)
        .collect(Collectors.toList());
  }

  @Override
  public List<Note> searchNotebookNotes(String notebookId, String query) {
    Notebook notebook = ensureNotebookExists(notebookId);
    return notebook.searchNotes(query);
  }

  @Override
  public Optional<Note> findNoteById(String notebookId, String noteId) {
    Notebook notebook = ensureNotebookExists(notebookId);
    return notebook.findNoteById(noteId);
  }

  @Override
  public Note createNote(String notebookId, CreateNoteDto noteDto) {
    Notebook notebook = ensureNotebookExists(notebookId);

    Note newNote = new Note(noteDto.getName(), noteDto.getContent());
    notebook.addNote(newNote);

    notebookRepository.update(notebook);
    return newNote;
  }

  @Override
  public Note updateNote(String notebookId, UpdateNoteDto noteDto) {
    Notebook notebook = ensureNotebookExists(notebookId);
    Note note = notebook.findNoteById(noteDto.getId()).orElseThrow(
        () -> new IllegalArgumentException(
            "Notebook does not have any notes with ID '" + noteDto.getId() + "'"));

    note.setName(noteDto.getName());
    note.setContent(noteDto.getContent());

    notebookRepository.update(notebook);
    return note;
  }


  @Override
  public void archiveNote(String notebookId, String noteId) {
    Notebook notebook = ensureNotebookExists(notebookId);
    Note note = notebook.findNoteById(noteId).orElseThrow(
        () -> new IllegalArgumentException(
            "Notebook does not have any notes with ID '" + noteId + "'"));

    note.setArchived(true);

    notebookRepository.update(notebook);
  }

  @Override
  public void restoreNote(String notebookId, String noteId) {
    Notebook notebook = ensureNotebookExists(notebookId);
    Note note = notebook.findNoteById(noteId).orElseThrow(
        () -> new IllegalArgumentException(
            "Notebook does not have any notes with ID '" + noteId + "'"));

    note.setArchived(false);

    notebookRepository.update(notebook);
  }

  @Override
  public void deleteNote(String notebookId, String noteId) {
    Notebook notebook = ensureNotebookExists(notebookId);
    notebook.deleteNoteById(noteId);
  }
}
