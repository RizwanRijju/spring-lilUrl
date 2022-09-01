package com.javaspring.lilurl.repository;

import com.javaspring.lilurl.entity.Redirect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RedirectRepository extends JpaRepository<Redirect, Long> {


    Optional<Redirect> findByAlias(String alias);

    Optional<Redirect> findByUrl(String url);

    boolean existsByAlias(String alias);
    boolean existsByUrl(String url);

    Optional<Redirect> findTopByUrlOrderByIdDesc(String url);
}
