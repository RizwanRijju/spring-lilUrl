package com.javaspring.lilurl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedirectResponseDto {

    private Long id;
    private String url;
    private String shortenedUrl;
}
