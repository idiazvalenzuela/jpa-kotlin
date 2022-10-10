package com.codely.jpakotlin.domain

import javax.annotation.processing.Generated
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "animal")
data class Animal(
    @Id
    @Generated
    var id: Int?,
    val name: String
)
