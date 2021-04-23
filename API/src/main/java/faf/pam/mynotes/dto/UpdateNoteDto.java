package faf.pam.mynotes.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateNoteDto extends CreateNoteDto {
  private String id;
}
