package com.gsl.scm.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Min 3 Character is required")
    private String name;

    private String email;

    private String password;

    private String about;

    private String phoneNumber;
}
