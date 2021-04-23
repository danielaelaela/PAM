package faf.pam.mynotes.service;

import faf.pam.mynotes.domain.Note;
import faf.pam.mynotes.domain.Notebook;
import faf.pam.mynotes.dto.CreateNoteDto;
import faf.pam.mynotes.dto.CreateNotebookDto;
import faf.pam.mynotes.dto.UpdateNoteDto;
import faf.pam.mynotes.dto.UpdateNotebookDto;
import java.util.List;
import java.util.Optional;

public interface NotebookService {

  /**
   * List all notebooks
   *
   * @return List of all notebooks
   */
  List<Notebook> findAllNotebooks();

  /**
   * List notebooks that contain query as part of their name
   *
   * @param query Search query
   * @return List of matching notebooks
   */
  List<Notebook> searchNotebooks(String query);

  /**
   * Find a notebook by ID
   *
   * @param notebookId Notebook ID
   * @return Matching notebook if any
   */
  Optional<Notebook> findNotebookById(String notebookId);

  /**
   * Create a new notebook
   * Its name must be unique.
   *
   * @param notebookDto Attributes of the new notebook
   * @return Created notebook
   */
  Notebook createNotebook(CreateNotebookDto notebookDto);

  /**
   * Update an existing notebook
   *
   * @param notebookDto New attributes of the notebook
   * @return Updated notebook
   */
  Notebook updateNotebook(UpdateNotebookDto notebookDto);

  /**
   * Delete a notebook
   * If notebook is not present nothing happens.
   *
   * @param notebookId Notebook ID
   */
  void deleteNotebook(String notebookId);

  /**
   * Find all notes contained in a notebook
   *
   * @param notebookId Notebook ID
   * @return List of notes
   */
  List<Note> findNotebookNotes(String notebookId);

  /**
   * Find all notes contained in a notebook
   *
   * @param notebookId Notebook ID
   * @param archived Note status
   * @return List of notes
   */
  List<Note> findNotebookNotes(String notebookId, boolean archived);

  /**
   * Find all notes contained in a notebook that have query as part of their name
   *
   * @param notebookId Notebook ID
   * @return List of matching notes
   */
  List<Note> searchNotebookNotes(String notebookId, String query);

  /**
   * Find a note by ID
   *
   * @param notebookId ID of notebook containing this note
   * @param noteId ID of the note
   * @return Matching note if any
   */
  Optional<Note> findNoteById(String notebookId, String noteId);

  /**
   * Create a new note
   * Its name must by unique inside a notebook
   *
   * @param notebookId Enclosing notebook ID
   * @param noteDto Attributes of the new note
   * @return Create note
   */
  Note createNote(String notebookId, CreateNoteDto noteDto);

  /**
   * Update an existing note
   *
   * @param notebookId ID of notebook containing this note
   * @param noteDto New attributes of the note
   * @return Updated note
   */
  Note updateNote(String notebookId, UpdateNoteDto noteDto);

  /**
   * Archive an active note
   *
   * @param notebookId ID of notebook containing this note
   * @param noteId ID of the note
   */
  void archiveNote(String notebookId, String noteId);

  /**
   * Restore an archived note to an active state
   *
   * @param notebookId ID of notebook containing this note
   * @param noteId ID of the note
   */
  void restoreNote(String notebookId, String noteId);

  /**
   * Delete a note
   *
   * @param notebookId ID of notebook containing this note
   * @param noteId ID of note to be deleted
   */
  void deleteNote(String notebookId, String noteId);
}
