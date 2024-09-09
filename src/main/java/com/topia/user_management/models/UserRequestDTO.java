package com.topia.user_management.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequestDTO {

    private String name;
    private Integer age;

}
