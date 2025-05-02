package br.com.erudio.dto;



import br.com.erudio.serializer.GenderSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String address;
    private String firstName;
    @JsonSerialize(using = GenderSerializer.class)
    private String gender;
    private String lastName;


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PersonDTO personDTO)) return false;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(),
                personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName())
                && Objects.equals(getAddress(), personDTO.getAddress()) && Objects.equals(getGender(),
                personDTO.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGender());
    }
}
