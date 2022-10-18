package com.codely.jpakotlin.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "farm")
class Farm(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    var name: String,

    @OneToOne(cascade = [javax.persistence.CascadeType.ALL])
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    var location: Location? = null
)
