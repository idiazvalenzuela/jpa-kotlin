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
open class ManagedFarm(
    @Id
    open var id: Int? = null,

    open var name: String? = null,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "managed_farm_id")
    open var foremen: MutableList<Foreman>? = mutableListOf()
) {
}
