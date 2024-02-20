package alessiapalmieri.U5W3D2.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewEmployeeDTO(
        @NotEmpty
        @Size(min = 3,max = 20, message = "The username must be a minimum of 3 characters and a maximum of 20")
        String username,
        @NotEmpty(message = "enter name")
        @Size(min = 3,max = 20, message = "The name must be a minimum of 3 characters and a maximum of 20")
        String name,
        @NotEmpty(message = "Enter lastname")
        @Size(min = 3,max = 20, message = "The lastname must be a minimum of 3 characters and a maximum of 20")
        String lastname,
        @Email(message = "Enter email")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is not valid!")
        String email,
        @NotEmpty(message = "Enter a password")
                String password) {


}
