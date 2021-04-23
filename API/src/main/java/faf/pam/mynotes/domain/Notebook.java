package faf.pam.mynotes.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class Notebook {
  private String id;
  private String name;
  private String description;
  private List<Note> notes;

  public Notebook(String name, String description) {
    this.name = name;
    this.description = description;
    this.notes = new ArrayList<>();
  }

  /**
   * Find a note by ID
   *
   * @param noteId Note ID
   * @return Matching note if any
   */
  public Optional<Note> findNoteById(String noteId) {
    return notes.stream()
        .filter(note -> Objects.equals(note.getId(), noteId))
        .findFirst();
  }

  /**
   * Find notes that contain query inside their name of content
   *
   * @param query Query to match across notes
   * @return List of matching notes
   */
  public List<Note> searchNotes(String query) {
    return notes.stream()
        .filter(note -> note.containsKeyword(query))
        .collect(Collectors.toList());
  }

  /**
   * Add new note to this notebook
   * @param note Note to be added
   */
  public void addNote(Note note) {
    if (hasNoteWithName(note.getName())) {
      throw new IllegalArgumentException(
          "Notebook already has a note with name '" + note.getName() + "'");
    }

    notes.add(note);
  }

  /**
   * Delete note from this notebook
   * @param noteId ID of the note to be deleted
   */
  public void deleteNoteById(String noteId) {
    notes.removeIf(note -> Objects.equals(note.getId(), noteId));
  }

  private boolean hasNoteWithName(String noteName) {
    return notes.stream()
        .map(Note::getName)
        .anyMatch(name -> name.equalsIgnoreCase(noteName));
  }
}
