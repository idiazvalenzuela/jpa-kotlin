package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Tree
import org.springframework.data.jpa.repository.JpaRepository

interface TreeRepository : JpaRepository<Tree, String> {

    fun findAllByForestsId(id: Int?): List<Tree>

    fun findDistinctByForestsIdIn(ids: Set<Int>): List<Tree>
}
