package faf.pam.mynotes.domain;

import lombok.Data;

@Data
public class Note {
  private String id;
  private String name;
  private String content;
  private boolean archived;

  public Note(String name, String content) {
    this.name = name;
    this.content = content;
    this.archived = false;
  }

  /**
   * Determine if this note contains the given keyword
   *
   * @param keyword Keyword to test
   * @return Result of the test
   */
  public boolean containsKeyword(String keyword) {
    return name.contains(keyword) || content.contains(keyword);
  }
}
