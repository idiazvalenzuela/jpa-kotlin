package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Owner
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Date

interface OwnerRepository : JpaRepository<Owner, Int> {

    fun findOwnerByNameAndPreferredName(name: String?, preferredName: String?): Owner?

    fun findOwnersByNameOrPreferredName(name: String?, preferredName: String?): List<Owner>?

    fun findOwnerByJoinedAt(date: Date): Owner?

    fun findOwnersByJoinedAtAfter(date: Date): List<Owner>?

    fun findOwnersByJoinedAtBefore(date: Date): List<Owner>?

    fun findFirstByJoinedAtBetween(before: Date, after: Date): Owner?
}
