package com.codely.jpakotlin.domain

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "managed_farm")
open class ManagedFarm(
    @Id
    private var id: Int? = null,
    private val name: String? = null,

    @OneToMany(cascade = [javax.persistence.CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "managed_farm_id")
    private var foremen: MutableList<Foreman>? = null
) {
}
