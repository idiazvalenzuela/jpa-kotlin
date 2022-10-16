package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Foreman
import org.springframework.data.repository.CrudRepository

interface ForemanRepository : CrudRepository<Foreman, Int>
