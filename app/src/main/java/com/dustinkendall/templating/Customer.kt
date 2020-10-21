package com.dustinkendall.templating

import kotlin.properties.Delegates

/**
 * Created by Dustin Kendall on 10/20/20.
 */

class Customer(name:String = "", street: String = "", city: String = "", state: String = ""){
    var name: String by Delegates.notNull()
    var street: String by Delegates.notNull()
    var city: String by Delegates.notNull()
    var state: String by Delegates.notNull()

    init {
        this.name = name
        this.city = city
        this.state = state
        this.street = street
    }
}