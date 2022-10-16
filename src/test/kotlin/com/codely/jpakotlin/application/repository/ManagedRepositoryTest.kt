package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Foreman
import com.codely.jpakotlin.domain.ManagedFarm
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ManagedRepositoryTest {

    @Autowired
    private lateinit var managedFarmRepository: ManagedFarmRepository

    @Autowired
    private lateinit var foremanRepository: ForemanRepository

    @BeforeEach
    fun setUp() {
        managedFarmRepository.deleteAll()
        foremanRepository.deleteAll()
    }

    @Test
    fun saveOneElementTest() {
        val foreman = Foreman(id = 1, name = "James")
        val anotherForeman = Foreman(id = 2, name = "Kaily")
        val managedFarm = ManagedFarm(id = 1, name = "small farm", foremen = mutableListOf(foreman, anotherForeman))
        managedFarmRepository.save(managedFarm)
        assertThat(managedFarmRepository.findAll()).hasSize(1)
        assertThat(foremanRepository.findAll()).hasSize(2)
    }

    @Test
    fun saveOneElementPreviouslyExistingTest() {
        val foreman = Foreman(id = 1, name = "James")
        val anotherForeman = Foreman(id = 2, name = "Kaily")
        foremanRepository.saveAll(listOf(foreman, anotherForeman))
        val managedFarm = ManagedFarm(id = 1, name = "small farm", foremen = mutableListOf(foreman, anotherForeman))
        managedFarmRepository.save(managedFarm)
        assertThat(managedFarmRepository.findAll()).hasSize(1)
        assertThat(foremanRepository.findAll()).hasSize(2)
    }

    @Test
    fun deleteAlsoDeleteForemanTest() {
        val foreman = Foreman(id = 1, name = "James")
        val anotherForeman = Foreman(id = 2, name = "Kaily")
        val managedFarm = ManagedFarm(id = 1, name = "small farm", foremen = mutableListOf(foreman, anotherForeman))
        managedFarmRepository.save(managedFarm)

        val managedFarmInDb: ManagedFarm = managedFarmRepository.findFirstByName("small farm")
        assertThat(managedFarmInDb.foremen).hasSize(2)
        managedFarmRepository.deleteById(1)
        assertThat(managedFarmRepository.findAll()).hasSize(0)
        assertThat(foremanRepository.findAll()).hasSize(0)
    }

    @Test
    fun violateConstraint() {
        val foreman = Foreman(id = 1, name = "James")
        val anotherForeman = Foreman(id = 2, name = "Kaily")
        val managedFarm = ManagedFarm(id = 1, name = "small farm", foremen = mutableListOf(foreman, anotherForeman))
        managedFarmRepository.save(managedFarm)

        assertThat(
            managedFarmRepository.findFirstByName("small farm").foremen
        ).hasSize(2)

        val anotherManagedFarm =
            ManagedFarm(id = 2, name = "another farm", foremen = mutableListOf(foreman, anotherForeman))
        managedFarmRepository.save(anotherManagedFarm)

        assertThat(
            managedFarmRepository.findFirstByName("small farm").foremen
        ).hasSize(0)
        assertThat(
            managedFarmRepository.findFirstByName("another farm").foremen
        ).hasSize(2)
    }

    @Test
    fun checkForemanDataTest() {
        val foreman = Foreman(id = 1, name = "James")
        val anotherForeman = Foreman(id = 2, name = "Kaily")
        val managedFarm = ManagedFarm(id = 1, name = "small farm", foremen = mutableListOf(foreman, anotherForeman))
        managedFarmRepository.save(managedFarm)

        val managedFarmInDb: ManagedFarm = managedFarmRepository.findFirstByName("small farm")
        assertThat(managedFarmInDb.foremen).extracting("name")
            .contains("James", "Kaily")
    }

    @Test
    fun findFarmByForemanTest() {
        val foreman = Foreman(id = 1, name = "James")
        val anotherForeman = Foreman(id = 2, name = "Kaily")
        val managedFarm = ManagedFarm(id = 1, name = "small farm", foremen = mutableListOf(foreman, anotherForeman))
        managedFarmRepository.save(managedFarm)
        val foremanInFarm: ManagedFarm = managedFarmRepository.findByForemenName("James")
        assertThat(foremanInFarm.name).isEqualTo("small farm")
    }
}
