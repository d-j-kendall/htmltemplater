package com.dustinkendall.templating

import kotlin.properties.Delegates

/**
 * Created by Dustin Kendall on 10/20/20.
 */

class Customer(name:String = "", street: String = "", city: String = "", state: String = ""){
    var name: String = name
    var street: String = street
    var city: String = city
    var state: String = state

    init {
        this.name = name
        this.city = city
        this.state = state
        this.street = street
    }
}