package faf.pam.mynotes.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateNotebookDto extends CreateNotebookDto {
  private String id;
}
