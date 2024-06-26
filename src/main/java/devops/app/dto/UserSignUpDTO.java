package devops.app.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserSignUpDTO {
    private String username;
    private String password;
    private Set<String> roleNames;
}
