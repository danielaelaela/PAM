package faf.pam.mynotes.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateNoteDto {
  @NotBlank
  private String name;
  private String content;
}
