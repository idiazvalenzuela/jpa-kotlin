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
    open var id: Int? = null,

    open var latitude: Double,

    open var longitude: Double,

    @OneToOne(mappedBy = "location")
    open var farm: Farm? = null
)
