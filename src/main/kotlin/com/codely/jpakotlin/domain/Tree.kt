package com.codely.jpakotlin.domain

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table
class Tree(
    @Id
    var name: String? = null,

    @ManyToMany(mappedBy = "trees", fetch = FetchType.EAGER)
    var forests: MutableSet<Forest> = mutableSetOf()
)
