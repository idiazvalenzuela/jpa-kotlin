package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Owner
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

@SpringBootTest
class OwnerRepositoryTest {

    @Autowired
    lateinit var ownerRepository: OwnerRepository

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

    @BeforeEach
    fun setUp() {
        ownerRepository.deleteAll()
    }

    @Test
    fun saveOneElementTest() {
        val owner = Owner(
            id = 123,
            name = "Jane Marie",
            preferredName = "Jany"
        )
        val savedOwner = ownerRepository.save(owner)

        val ownerInDb = ownerRepository.findAll()

        assertThat(ownerInDb).hasSize(1)
        assertThat(ownerInDb).contains(savedOwner)
    }

    @Test
    fun saveUpsertTest() {
        val owner = Owner(
            id = 123,
            name = "Jane Marie",
            preferredName = "Jany"
        )
        val anotherOwner = Owner(
            id = 123,
            name = "John",
            preferredName = "John"
        )
        ownerRepository.save(owner)
        ownerRepository.save(anotherOwner)

        val ownerInDb = ownerRepository.findAll()
        assertThat(ownerInDb).hasSize(1)
        assertThat(ownerInDb).contains(anotherOwner)
    }

    @Test
    fun uniqueConstraint() {
        val owner = Owner(
            id = 123,
            name = "Jane Marie",
            email = "mail@mail.com"
        )
        val anotherOwner = Owner(
            id = 456,
            name = "John Smith",
            email = "mail@mail.com"
        )
        ownerRepository.save(owner)
        val ownerInDb = ownerRepository.findAll()
        assertThat(ownerInDb).hasSize(1)
        assertThat(ownerInDb).contains(owner)

        assertThatExceptionOfType(
            DataIntegrityViolationException::class.java
        ).isThrownBy { ownerRepository.save(anotherOwner) }
    }

//    @Test
//    fun notNullConstraint() {
//        val owner = Owner(id = 123,  email = "mail@mail.com")
//        assertThatExceptionOfType(TransactionSystemException::class.java)
//            .isThrownBy { ownerRepository.save(owner) }
//    }

    @Test
    fun findByMultipleCriteria() {
        val owner = Owner(
            id = 123,
            name = "Jane Marie",
            email = "mail@mail.com",
            preferredName = "Jany"
        )
        val anotherOwner = Owner(
            id = 456,
            name = "Jane Marie",
            email = "anotherEmail@mail.com",
            preferredName = "Mary"
        )

        ownerRepository.saveAll(listOf(owner, anotherOwner))

        val found = ownerRepository.findOwnerByNameAndPreferredName("Jane Marie", "Jany")
        assertThat(found).isEqualTo(owner)
    }

    @Test
    fun findByMultipleCriteriaWithNull() {
        val owner = Owner(
            id = 123,
            name = "Jane Marie",
            email = "mail@mail.com",
            preferredName = "Jany"
        )
        val anotherOwner = Owner(
            id = 456,
            name = "Jane Marie",
            email = "anotherEmail@mail.com"
        )

        ownerRepository.saveAll(listOf(owner, anotherOwner))

        val found = ownerRepository.findOwnerByNameAndPreferredName("Jane Marie", null)
        assertThat(found).isEqualTo(anotherOwner)
    }

    @Test
    fun findByMultipleCriteriaOr() {

        val owner = Owner(
            id = 123,
            name = "Jane Marie",
            email = "mail@mail.com",
            preferredName = "Jany"
        )
        val anotherOwner = Owner(
            id = 456,
            name = "Jane Marie",
            email = "anotherEmail@mail.com",
            preferredName = "Mary"
        )
        val yetAnotherOwner = Owner(
            id = 789,
            name = "Janice Jane",
            email = "yetAnotherEmail@mail.com",
            preferredName = "Jany"
        )

        ownerRepository.saveAll(listOf(owner, anotherOwner, yetAnotherOwner))

        val found = ownerRepository.findOwnersByNameOrPreferredName("Jane Marie", "Jany")
        assertThat(found).hasSize(3)
        assertThat(found).contains(owner, anotherOwner, yetAnotherOwner)
    }

    @Test
    fun findByDate() {
        val owner = Owner(
            id = 123,
            name = "Jane Marie",
            email = "mail@mail.com",
            joinedAt = parseDate("2018-05-05 11:50:55")
        )
        val anotherOwner = Owner(
            id = 456,
            name = "Jane Marie",
            email = "anotherEmail@mail.com",
            joinedAt = parseDate("2020-07-09 14:00:00")
        )
        val yetAnotherOwner = Owner(
            id = 789,
            name = "Jack Jackson",
            email = "yetAnotherEmail@mail.com",
            joinedAt = parseDate("1985-01-01 10:18:00")
        )

        ownerRepository.saveAll(listOf(owner, anotherOwner, yetAnotherOwner))

        val found = ownerRepository.findOwnerByJoinedAt(parseDate("2020-07-09 14:00:00"))
        assertThat(found).isEqualTo(anotherOwner)
    }

    @Test
    fun findByDateAfter() {
        val owner = Owner(
            id = 123,
            name = "Jane Marie",
            email = "mail@mail.com",
            joinedAt = parseDate("2018-05-05 11:50:55")
        )
        val anotherOwner = Owner(
            id = 456,
            name = "Jane Marie",
            email = "anotherEmail@mail.com",
            joinedAt = parseDate("2020-07-09 14:00:00")
        )
        val yetAnotherOwner = Owner(
            id = 789,
            name = "Jack Jackson",
            email = "yetAnotherEmail@mail.com",
            joinedAt = parseDate("1985-01-01 10:18:00")
        )

        ownerRepository.saveAll(listOf(owner, anotherOwner, yetAnotherOwner))

        val found = ownerRepository.findOwnersByJoinedAtAfter(parseDate("2015-01-01 00:00:00"))
        assertThat(found).hasSize(2)
        assertThat(found).extracting("id").contains(123, 456)
    }

    @Test
    fun findByDateBefore() {
        val owner = Owner(
            id = 123,
            name = "Jane Marie",
            email = "mail@mail.com",
            joinedAt = parseDate("2018-05-05 11:50:55")
        )
        val anotherOwner = Owner(
            id = 456,
            name = "Jane Marie",
            email = "anotherEmail@mail.com",
            joinedAt = parseDate("2020-07-09 14:00:00")
        )
        val yetAnotherOwner = Owner(
            id = 789,
            name = "Jack Jackson",
            email = "yetAnotherEmail@mail.com",
            joinedAt = parseDate("1985-01-01 10:18:00")
        )
        ownerRepository.saveAll(listOf(owner, anotherOwner, yetAnotherOwner))

        val found = ownerRepository.findOwnersByJoinedAtBefore(parseDate("2015-01-01 00:00:00"))
        assertThat(found).hasSize(1)
        assertThat(found).extracting("id").contains(789)
    }

    @Test
    fun findByDateBetween() {
        val owner = Owner(
            id = 123,
            name = "Jane Marie",
            email = "mail@mail.com",
            joinedAt = parseDate("2018-05-05 11:50:55")
        )
        val anotherOwner = Owner(
            id = 456,
            name = "Jane Marie",
            email = "anotherEmail@mail.com",
            joinedAt = parseDate("2020-07-09 14:00:00")
        )
        val yetAnotherOwner = Owner(
            id = 789,
            name = "Jack Jackson",
            email = "yetAnotherEmail@mail.com",
            joinedAt = parseDate("1985-01-01 10:18:00")
        )
        ownerRepository.saveAll(listOf(owner, anotherOwner, yetAnotherOwner))

        val found = ownerRepository.findFirstByJoinedAtBetween(
            parseDate("2015-01-01 00:00:00"),
            parseDate("2020-01-01 00:00:00")
        )
        assertThat(found!!.id).isEqualTo(123)
    }

    @Throws(ParseException::class)
    private fun parseDate(input: String): Date {
        return simpleDateFormat.parse(input)
    }
}
