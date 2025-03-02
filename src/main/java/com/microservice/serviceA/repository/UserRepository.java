package com.microservice.serviceA.repository;

import com.microservice.serviceA.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findByUuid(UUID uuid);

    @Query("FROM User u where u.role = 'TECHNICIAN'")
    public List<User> findAllTechnician();

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.uuid = :uuid")
    public void deleteByUuid(@Param("uuid") UUID uuid);

    @Query("FROM User u where (:keyword IS NULL OR :keyword = '' OR UPPER(u.fullName) LIKE UPPER(CONCAT('%', :keyword, '%')))")
    public Page<User> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
