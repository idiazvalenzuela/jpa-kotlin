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
    private var name: String? = null,

    @Id
    private val family: String? = null,

    @Column(name = "vegetable_type")
    @Enumerated(value = EnumType.STRING)
    private val type: VegetableType,

    @Column(name = "average_weight")
    private var averageWeight: Double? = null
) {
}
