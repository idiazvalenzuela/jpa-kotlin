package com.codely.jpakotlin.domain

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
    private var id: Int? = null,

    private var name: String? = null,

    @ManyToMany(
        cascade = [javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE],
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "tree_forest",
        joinColumns = [JoinColumn(name = "forest_id")],
        inverseJoinColumns = [JoinColumn(name = "tree_name")]
    )
    private val trees: Set<Tree>? = null
) {
}
