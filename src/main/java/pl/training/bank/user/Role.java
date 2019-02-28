package pl.training.bank.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Role implements GrantedAuthority {

    @GeneratedValue
    @Id
    private Long id;
    @Column(unique = true)
    @NonNull
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

}
