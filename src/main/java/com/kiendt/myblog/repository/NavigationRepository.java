package com.kiendt.myblog.repository;

import com.kiendt.myblog.model.Navigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavigationRepository extends JpaRepository<Navigation, Long> {
}
