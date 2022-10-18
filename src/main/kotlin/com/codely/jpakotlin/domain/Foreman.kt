package com.codely.jpakotlin.domain

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table
class Foreman(
    @Id
    var id: Int? = null,

    var name: String? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "managed_farm_id", referencedColumnName = "id")
    var managedFarm: ManagedFarm? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Foreman

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }
}
