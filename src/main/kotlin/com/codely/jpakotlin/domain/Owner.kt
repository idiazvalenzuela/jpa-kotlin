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
    open var id: Int? = null,

    @Column(unique = true)
    open val email: String? = null,

    open var name: String,

    @Column(name = "preferred_name")
    open var preferredName: String? = null,

    @Temporal(TemporalType.TIMESTAMP)
    open var joinedAt: Date? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Owner

        if (id != other.id) return false

        return true
    }
}
