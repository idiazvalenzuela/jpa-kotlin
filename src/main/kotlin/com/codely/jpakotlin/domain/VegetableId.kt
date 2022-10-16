package com.codely.jpakotlin.domain

import java.io.Serializable

class VegetableId(
    private var name: String? = null,
    private val family: String? = null
) : Serializable {
}
