package faf.pam.mynotes.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateNotebookDto {
  @NotBlank
  private String name;
  private String description;
}
