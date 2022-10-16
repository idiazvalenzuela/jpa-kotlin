package com.codely.jpakotlin.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

@Table
@Entity
open class Location(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int? = null,
    private var latitude: Double,
    private var longitude: Double,
    @OneToOne(mappedBy = "location")
    private var farm: Farm? = null
) {
}
