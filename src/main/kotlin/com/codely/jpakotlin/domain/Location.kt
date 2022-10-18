package com.codely.jpakotlin.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

@Table
@Entity
class Location(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    var latitude: Double,

    var longitude: Double,

    @OneToOne(mappedBy = "location")
    var farm: Farm? = null
)
