package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.ManagedFarm
import org.springframework.data.jpa.repository.JpaRepository

interface ManagedFarmRepository : JpaRepository<ManagedFarm, Int> {

    fun findFirstByName(name: String): ManagedFarm

    fun findByForemenName(name: String): ManagedFarm

}
