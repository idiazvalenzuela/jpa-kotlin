package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Animal
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.CrudRepository

@EnableJpaRepositories
interface AnimalRepository : CrudRepository<Animal, Int> {

    fun findAnimalByName(name: String): Animal?

    fun findFirstByName(name: String): Animal?

    fun findTopByName(name: String): Animal?

    fun findAnimalsByName(name: String): List<Animal>

    fun findAnimalsByNameIgnoreCase(name: String): List<Animal>
}
