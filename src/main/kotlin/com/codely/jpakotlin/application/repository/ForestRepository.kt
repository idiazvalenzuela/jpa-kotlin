package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Forest
import com.codely.jpakotlin.domain.Tree
import org.springframework.data.jpa.repository.JpaRepository

interface ForestRepository : JpaRepository<ForestRepository, Int> {

    fun findAllByTreesIn(trees: Set<Tree>): List<Forest>

    fun findAllByTrees(tree: Tree?): List<Forest>

    fun findAllByTreesName(name: String?): List<Forest>
    
}
