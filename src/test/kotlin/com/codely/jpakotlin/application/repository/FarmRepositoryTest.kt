package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Farm
import com.codely.jpakotlin.domain.Location
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.InvalidDataAccessApiUsageException

@SpringBootTest
class FarmRepositoryTest @Autowired constructor(
    val farmRepository: FarmRepository,
    val locationRepository: LocationRepository
) {
    @BeforeEach
    fun setUp() {
        farmRepository.deleteAll()
        locationRepository.deleteAll()
    }

    @Test
    fun saveOneElementTest() {
        val farm = Farm(
            name = "a farm",
            location = Location(
                latitude = 12.3,
                longitude = 34.0
            )
        )
        farmRepository.save(farm)
        val farmInDb = farmRepository.findAll()
        assertThat(farmInDb).hasSize(1)
        assertThat(farmInDb).extracting("name").containsExactly("a farm")
        assertThat(farmInDb).extracting("location")
            .extracting("latitude", "longitude")
            .contains(Tuple.tuple(12.3, 34.0))
        val locationInDb = locationRepository.findAll().toList()
        assertThat(locationInDb).hasSize(1)
        assertThat(locationInDb)
            .extracting("latitude", "longitude")
            .contains(Tuple.tuple(12.3, 34.0))
        assertThat(locationInDb).extracting("farm").extracting("name")
            .contains("a farm")
    }

    @Test
    fun insertExistingLocationTest() {
        var location = Location(
            latitude = 12.3,
            longitude = 34.0
        )
        location = locationRepository.save(location)
        val aFarm = Farm(
            name = "a farm",
            location = location
        )

        assertThatExceptionOfType(
            InvalidDataAccessApiUsageException::class.java
        )
            .isThrownBy { farmRepository.save(aFarm) }
    }

    @Test
    fun violateOneToOneTest() {
        val location = Location(
            latitude = 12.3,
            longitude = 34.0
        )
        val aFarm = Farm(
            name = "a farm",
            location = location
        )
        farmRepository.save(aFarm)
        val anotherFarm = Farm(
            name = "another farm",
            location = location
        )
        assertThatExceptionOfType(
            InvalidDataAccessApiUsageException::class.java
        ).isThrownBy { farmRepository.save(anotherFarm) }
    }

    @Test
    fun deleteOneElementTest() {
        val farm = Farm(
            name = "a farm",
            location = Location(
                latitude = 12.3,
                longitude = 34.0
            )
        )
        farmRepository.save(farm)
        assertThat(farmRepository.findAll()).hasSize(1)
        assertThat(locationRepository.findAll()).hasSize(1)
        farmRepository.deleteById(farm.id!!)
        assertThat(farmRepository.findAll()).hasSize(0)
        assertThat(locationRepository.findAll()).hasSize(0)
    }

    @Test
    fun findByLocationTest() {
        val location = Location(
            latitude = 12.3,
            longitude = 34.0
        )
        val aFarm = Farm(
            name = "a farm",
            location = location
        )
        val anotherFarm = Farm(
            name = "a farm",
            location = Location(
                latitude = 12.3,
                longitude = 34.0
            )
        )
        farmRepository.saveAll(listOf(aFarm, anotherFarm))
        val farmInDb: Farm = farmRepository.findFarmByLocation(location)
        assertThat(farmInDb).extracting("name").isEqualTo("a farm")
    }

    @Test
    fun countByLocationLatitudeTest() {
        val location = Location(
            latitude = 12.3,
            longitude = 34.0
        )
        val aFarm = Farm(
            name = "a farm",
            location = location
        )
        val anotherFarm = Farm(
            name = "a farm",
            location = Location(
                latitude = 12.3,
                longitude = 34.0
            )
        )
        farmRepository.saveAll(listOf(aFarm, anotherFarm))
        assertThat(farmRepository.countFarmsByLocationLatitude(12.3)).isEqualTo(2)
    }

}
