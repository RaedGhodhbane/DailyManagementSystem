package com.daily.models;

import com.daily.enums.ERole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Rayen Boulehmi
 */
@Document(collection = "roles")
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    private String id; // Use String or Integer, depending on your preference

    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
