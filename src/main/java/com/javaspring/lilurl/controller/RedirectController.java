package com.javaspring.lilurl.controller;

import com.javaspring.lilurl.dto.RedirectRequestDto;
import com.javaspring.lilurl.dto.RedirectResponseDto;
import com.javaspring.lilurl.entity.Redirect;
import com.javaspring.lilurl.service.RedirectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
public class RedirectController {


    private RedirectService redirectService;

    @GetMapping("/{alias}")
    public ResponseEntity<?> handleRedirect(@PathVariable String alias) throws URISyntaxException {
        Redirect redirect = redirectService.getRedirectByAlias(alias);
        URI uri = new URI(redirect.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("")
    public RedirectResponseDto createRedirect(@Valid @RequestBody RedirectRequestDto redirectRequestDTO){
        return redirectService.createRedirect(redirectRequestDTO);
    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<RedirectResponseDto> getShortUrl(@PathVariable Long id){
        return status(HttpStatus.OK).body(redirectService.getRedirectById(id));
    }
}
