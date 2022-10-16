package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Foreman
import org.springframework.data.jpa.repository.JpaRepository

interface ForemanRepository : JpaRepository<Foreman, Int>
