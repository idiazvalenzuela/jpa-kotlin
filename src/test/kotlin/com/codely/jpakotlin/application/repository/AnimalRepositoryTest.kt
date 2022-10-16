package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Animal
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.IncorrectResultSizeDataAccessException

@SpringBootTest
internal class AnimalRepositoryTest {

    @Autowired
    lateinit var animalRepository: AnimalRepository

    @BeforeEach
    fun setUp() {
        animalRepository.deleteAll();
    }

    @Test
    fun `save one element`() {
        val animal = Animal(name = "cow")
        val savedAnimal = animalRepository.save(animal)

        val animalsInDb = animalRepository.findAll()

        assertThat(animalsInDb).hasSize(1)
        assertThat(animalsInDb).contains(savedAnimal)
    }

    @Test
    fun `save several elements`() {
        val first = Animal(name = "cow")
        val second = Animal(name = "horse")
        val third = Animal(name = "sheep")
        animalRepository.save(first)
        animalRepository.save(second)
        animalRepository.save(third)

        val animalsInDb = animalRepository.findAll().toList()

        assertThat(animalsInDb).hasSize(3)
        assertThat(animalsInDb).containsExactly(first, second, third)
        assertThat(animalsInDb.get(1).id).isGreaterThan(animalsInDb.get(0).id)
        assertThat(animalsInDb.get(2).id).isGreaterThan(animalsInDb.get(1).id)
    }

    @Test
    fun `find Animals by Name`() {
        val cow = Animal(name = "cow")
        val horse = Animal(name = "horse")
        val anotherCow = Animal(name = "cow")
        animalRepository.save(cow)
        animalRepository.save(horse)
        animalRepository.save(anotherCow)

        assertThat(animalRepository.findAnimalByName("horse")).isEqualTo(horse)

        assertThatExceptionOfType(IncorrectResultSizeDataAccessException::class.java)
            .isThrownBy {
                animalRepository.findAnimalByName(
                    "cow"
                )
            }

        assertThat(animalRepository.findAnimalsByName("cow")).hasSize(2)
    }

    @Test
    fun `find Animals by Name not found`() {
        val cow = Animal(name = "cow")
        val horse = Animal(name = "horse")
        animalRepository.save(cow)
        animalRepository.save(horse)

        assertThat(animalRepository.findAnimalByName("goat")).isNull()
    }

    @Test
    fun `find animals by name ignoring case`() {
        val cow = Animal(name = "cow")
        val horse = Animal(name = "horse")

        animalRepository.saveAll(listOf(cow, horse))

        assertThat(animalRepository.findAnimalsByNameIgnoreCase("COW")).containsExactly(cow);

        assertThat(animalRepository.findAnimalsByNameIgnoreCase("CoW")).containsExactly(cow);

        assertThat(animalRepository.findAnimalsByNameIgnoreCase("CW")).isEmpty();

    }
}
