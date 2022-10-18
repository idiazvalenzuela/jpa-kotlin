package com.codely.jpakotlin.domain

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "managed_farm")
class ManagedFarm(
    @Id
    var id: Int? = null,

    var name: String? = null,

    @OneToMany(
        cascade = [CascadeType.ALL],
        orphanRemoval = true, fetch = FetchType.EAGER
    )
    @JoinColumn(name = "managed_farm_id")
    var foremen: MutableList<Foreman>? = mutableListOf()
)
