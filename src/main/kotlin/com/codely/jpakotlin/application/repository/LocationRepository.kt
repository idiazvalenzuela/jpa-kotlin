package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Location
import org.springframework.data.repository.CrudRepository

interface LocationRepository : CrudRepository<Location, Int> {
}
