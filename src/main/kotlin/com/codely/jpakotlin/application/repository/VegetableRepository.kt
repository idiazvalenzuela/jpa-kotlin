package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Vegetable
import com.codely.jpakotlin.domain.VegetableId
import com.codely.jpakotlin.domain.valueobject.VegetableType
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface VegetableRepository : JpaRepository<Vegetable, VegetableId> {

    fun findVegetablesByTypeOrderByAverageWeight(type: VegetableType, sort: Sort): List<Vegetable>

    fun findAllByNameLike(like: String?): List<Vegetable>
}
