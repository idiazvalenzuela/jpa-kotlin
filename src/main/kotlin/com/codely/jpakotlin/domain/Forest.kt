package com.codely.jpakotlin.domain

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table
open class Forest(
    @Id
    open var id: Int? = null,

    open var name: String? = null,

    @ManyToMany(
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "tree_forest",
        joinColumns = [JoinColumn(name = "forest_id")],
        inverseJoinColumns = [JoinColumn(name = "tree_name")]
    )
    open var trees: MutableSet<Tree> = mutableSetOf()
)
