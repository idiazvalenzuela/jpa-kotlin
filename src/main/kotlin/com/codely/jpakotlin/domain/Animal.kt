package com.codely.jpakotlin.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "animal")
open class Animal(
    @Id
    @GeneratedValue
    var id: Int? = null,

    val name: String
)
