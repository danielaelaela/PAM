package faf.pam.mynotes.repository;

import faf.pam.mynotes.domain.Notebook;
import java.util.List;
import java.util.Optional;

public interface NotebookRepository {

  List<Notebook> findAll();

  Optional<Notebook> findById(String id);

  Notebook create(Notebook notebook);

  Notebook update(Notebook notebook);

  void delete(String id);
}
