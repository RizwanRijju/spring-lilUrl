package com.javaspring.lilurl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedirectRequestDto {

    @NotNull
    private String url;

    private String alias;



}
