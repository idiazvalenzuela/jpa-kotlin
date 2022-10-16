package com.codely.jpakotlin.domain

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table
open class Tree(
    @Id
    private var name: String? = null,

    @ManyToMany(mappedBy = "trees", fetch = FetchType.EAGER)
    private val forests: Set<Forest>? = null
)
