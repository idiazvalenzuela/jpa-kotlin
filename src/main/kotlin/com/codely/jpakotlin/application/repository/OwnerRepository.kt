package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Owner
import org.springframework.data.repository.CrudRepository
import java.util.Date

interface OwnerRepository : CrudRepository<Owner, Int> {

    fun findOwnerByNameAndPreferredName(name: String?, preferredName: String?): Owner?

    fun findOwnersByNameOrPreferredName(name: String?, preferredName: String?): List<Owner>?

    fun findOwnerByJoinedAt(date: Date): Owner?

    fun findOwnersByJoinedAtAfter(date: Date): List<Owner>?

    fun findOwnersByJoinedAtBefore(date: Date): List<Owner>?

    fun findFirstByJoinedAtBetween(before: Date, after: Date): Owner?
}
