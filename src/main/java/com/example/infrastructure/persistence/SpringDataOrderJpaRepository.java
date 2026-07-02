package com.example.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataOrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
}
