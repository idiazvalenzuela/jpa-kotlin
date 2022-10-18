package com.codely.jpakotlin.domain

import java.io.Serializable

class VegetableId(
    private var name: String? = null,
    private val family: String? = null
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VegetableId

        if (name != other.name) return false
        if (family != other.family) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (family?.hashCode() ?: 0)
        return result
    }
}
