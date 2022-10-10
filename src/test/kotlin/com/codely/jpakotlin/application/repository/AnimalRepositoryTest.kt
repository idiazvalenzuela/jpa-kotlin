package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Animal
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AnimalRepositoryTest {

    @Autowired
    lateinit var animalRepository: AnimalRepository

    @Test
    fun test() {
        val animal = Animal(null, "cow")
        animalRepository.save(animal);

        assertThat(animalRepository.findAll()).hasSize(1)
    }
}
