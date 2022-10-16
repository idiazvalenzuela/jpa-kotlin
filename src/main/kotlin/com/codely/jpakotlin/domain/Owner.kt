package com.codely.jpakotlin.domain

import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Table(name = "owner")
@Entity
open class Owner(
    @Id
    private var id: Int? = null,

    @Column(unique = true)
    private val email: String? = null,

    private val name: String,

    @Column(name = "preferred_name")
    private val preferredName: String? = null,

    @Temporal(TemporalType.TIMESTAMP)
    private val joinedAt: Date? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Owner

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}
