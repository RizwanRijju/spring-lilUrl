package com.javaspring.lilurl.service;

import com.javaspring.lilurl.config.AppConfig;
import com.javaspring.lilurl.dto.RedirectRequestDto;
import com.javaspring.lilurl.dto.RedirectResponseDto;
import com.javaspring.lilurl.entity.Redirect;
import com.javaspring.lilurl.exception.AliasNotFoundException;
import com.javaspring.lilurl.exception.AliasTakenException;
import com.javaspring.lilurl.exception.InvalidUrlException;
import com.javaspring.lilurl.exception.UrlNotInDBException;
import com.javaspring.lilurl.repository.RedirectRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class RedirectService {

    private RedirectRepository redirectRepository;
    private RandomWordService randomWordService;
    private AppConfig appConfig;

    public RedirectResponseDto createRedirect(RedirectRequestDto redirectRequestDTO){
        String alias = redirectRequestDTO.getAlias();
        String url = redirectRequestDTO.getUrl();
        if(alias != null &&  alias != "" && redirectRepository.existsByAlias(alias))
            throw new AliasTakenException("Alias already exists. Choose another");

        String aliasWord = alias;
        Long id;
        String shortenedUrl;
        String userUrl;
        if(isValidUrl(url)){
            if(alias == null || alias == ""){
                if(redirectRepository.existsByUrl(url)){
                    Redirect redirect = redirectRepository.findTopByUrlOrderByIdDesc(url)
                            .orElseThrow(() ->  new UrlNotInDBException("This URL doesn't already exist in the DB"));

                    id = redirect.getId();
                    shortenedUrl = appConfig.getUrl() + "/" + redirect.getAlias();
                    userUrl = redirect.getUrl();

                }else{
                    aliasWord = getUniqueRandomWord();
                    Redirect redirect = new Redirect(aliasWord, url);
                    Redirect saveRedirect = redirectRepository.save(redirect);

                    id = saveRedirect.getId();
                    shortenedUrl = appConfig.getUrl() + "/" + aliasWord;
                    userUrl = url;
                }
            }
            else{
                Redirect redirect = new Redirect(aliasWord, url);
                Redirect saveRedirect = redirectRepository.save(redirect);

                id = saveRedirect.getId();
                shortenedUrl = appConfig.getUrl() + "/" + aliasWord;
                userUrl = url;
            }
            return RedirectResponseDto.builder()
                    .id(id)
                    .shortenedUrl(shortenedUrl)
                    .url(userUrl)
                    .build();
        }
        else {
            throw new InvalidUrlException("Please provide a valid URL");
        }
    }

    private String getUniqueRandomWord() {
        String randomWord;
        while(true){
            randomWord = randomWordService.getRandomWord();
            if(!redirectRepository.existsByAlias(randomWord))
                break;
        }
        return randomWord;
    }

    private boolean isValidUrl(String url) {
        try{
            new URL(url).toURI();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public Redirect getRedirectByAlias(String alias){

        Redirect redirect = redirectRepository.findByAlias(alias)
                .orElseThrow(() -> new AliasNotFoundException("We don't have that alias. Try making it by visiting 'lilurl.herokuapp.com'"));
        return redirect;
    }

    public RedirectResponseDto getRedirectById(Long id){
        Redirect redirect = redirectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return RedirectResponseDto.builder()
                .id(redirect.getId())
                .url(redirect.getUrl())
                .shortenedUrl(appConfig.getUrl() + "/" + redirect.getAlias())
                .build();
    }
}
