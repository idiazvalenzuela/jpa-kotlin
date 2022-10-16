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
    open var name: String? = null,

    @ManyToMany(mappedBy = "trees", fetch = FetchType.EAGER)
    open var forests: MutableSet<Forest> = mutableSetOf()
)
