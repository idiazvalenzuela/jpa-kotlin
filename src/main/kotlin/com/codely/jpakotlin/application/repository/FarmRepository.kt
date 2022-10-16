package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Farm
import com.codely.jpakotlin.domain.Location
import org.springframework.data.jpa.repository.JpaRepository

interface FarmRepository : JpaRepository<Farm, Int> {

    fun findFarmByLocation(location: Location): Farm

    fun countFarmsByLocationLatitude(latitude: Double): Int

}
