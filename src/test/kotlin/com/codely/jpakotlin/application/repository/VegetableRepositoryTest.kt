package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Vegetable
import com.codely.jpakotlin.domain.valueobject.VegetableType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Sort

@DataJpaTest
class VegetableRepositoryTest {

    @Autowired
    private lateinit var vegetableRepository: VegetableRepository

    @BeforeEach
    fun setUp() {
        vegetableRepository.deleteAll()
    }

    @Test
    fun saveOneElementTest() {
        val vegetable = Vegetable(
            name = "Carrot",
            family = "Apiaceae",
            type = VegetableType.TUBER,
            averageWeight = 35.6
        )
        vegetableRepository.save(vegetable)
        val vegetableInDb = vegetableRepository.findAll()
        assertThat(vegetableInDb).containsExactly(vegetable)
    }

    @Test
    fun orderByWeightTest() {
        val carrot = Vegetable(
            name = "Carrot",
            family = "Apiaceae",
            type = VegetableType.TUBER,
            averageWeight = 35.6
        )
        val potato = Vegetable(
            name = "Potato",
            family = "Solanaceace",
            type = VegetableType.TUBER,
            averageWeight = 100.0
        )
        val lettuce = Vegetable(
            name = "Lettuce",
            family = "Compositae",
            type = VegetableType.WITH_LEAVES,
            averageWeight = 15.6
        )
        vegetableRepository.saveAll(java.util.List.of(carrot, potato, lettuce))
        val tubers =
            vegetableRepository.findVegetablesByTypeOrderByAverageWeight(
                VegetableType.TUBER,
                Sort.unsorted().descending()
            )
        assertThat(tubers).hasSize(2)
        assertThat(tubers).containsExactly(carrot, potato)
    }

    @Test
    fun findByNameLikeTest() {
        val carrot = Vegetable(
            name = "Carrot",
            family = "Apiaceae",
            type = VegetableType.TUBER,
            averageWeight = 35.6
        )
        val potato = Vegetable(
            name = "Potato",
            family = "Solanaceace",
            type = VegetableType.TUBER,
            averageWeight = 100.0
        )
        val lettuce = Vegetable(
            name = "Lettuce",
            family = "Compositae",
            type = VegetableType.WITH_LEAVES,
            averageWeight = 15.6
        )
        vegetableRepository.saveAll(listOf(carrot, potato, lettuce))
        assertThat(vegetableRepository.findAllByNameLike("Lett%"))
            .hasSize(1)
    }
}
