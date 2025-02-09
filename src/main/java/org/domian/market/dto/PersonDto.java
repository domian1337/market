package org.domian.market.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private int id;
    private String name;
    private int surname;
    private String email;
    private String password;
}
