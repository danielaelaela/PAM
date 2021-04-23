package faf.pam.mynotes.infastructure;

import faf.pam.mynotes.domain.Notebook;
import faf.pam.mynotes.repository.NotebookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryNotebookRepository implements NotebookRepository {

  private final List<Notebook> notebooks = new ArrayList<>();

  @Override
  public List<Notebook> findAll() {
    return new ArrayList<>(notebooks);
  }

  @Override
  public Optional<Notebook> findById(String id) {
    return notebooks.stream()
        .filter(notebookIdMatcher(id))
        .findFirst();
  }

  @Override
  public Notebook create(Notebook notebook) {
    if (isAlreadySaved(notebook)) {
      throw new IllegalArgumentException("Notebook is already saved");
    }

    notebook.setId(generateId());
    notebooks.add(notebook);
    ensureNotesHaveIds(notebook);

    return notebook;
  }

  private void ensureNotesHaveIds(Notebook notebook) {
    notebook.getNotes().stream()
        .filter(note -> note.getId() == null)
        .forEach(note -> note.setId(generateId()));
  }

  private boolean isAlreadySaved(Notebook notebook) {
    return notebook.getId() != null;
  }

  @Override
  public Notebook update(Notebook notebook) {
    if (!isAlreadySaved(notebook)) {
      throw new IllegalArgumentException("Notebook is not saved yet, cannot update");
    }

    /* Replace notebooks that have the same ID as given argument */
    var idMatcher = notebookIdMatcher(notebook.getId());
    notebooks.replaceAll(
        n -> idMatcher.test(n) ? notebook : n);
    ensureNotesHaveIds(notebook);

    return notebook;
  }

  @Override
  public void delete(String id) {
    notebooks.removeIf(notebookIdMatcher(id));
  }

  private static Predicate<Notebook> notebookIdMatcher(String id) {
    return notebook -> Objects.equals(notebook.getId(), id);
  }

  /** Generate unique ID for new objects */
  private static String generateId() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
