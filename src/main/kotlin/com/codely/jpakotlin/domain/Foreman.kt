package com.codely.jpakotlin.domain

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table
open class Foreman(
    @Id
    private var id: Int? = null,

    private var name: String? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "managed_farm_id", referencedColumnName = "id")
    private val managedFarm: ManagedFarm? = null
) {
}
