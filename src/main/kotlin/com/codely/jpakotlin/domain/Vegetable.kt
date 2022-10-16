package com.codely.jpakotlin.domain

import com.codely.jpakotlin.domain.valueobject.VegetableType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Table

@Entity
@Table(name = "vegetable")
@IdClass(VegetableId::class)
open class Vegetable(

    @Id
    open var name: String? = null,

    @Id
    open var family: String? = null,

    @Column(name = "vegetable_type")
    @Enumerated(value = EnumType.STRING)
    open var type: VegetableType,

    @Column(name = "average_weight")
    open var averageWeight: Double? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vegetable

        if (name != other.name) return false
        if (family != other.family) return false
        if (type != other.type) return false
        if (averageWeight != other.averageWeight) return false

        return true
    }
}
