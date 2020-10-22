package com.dustinkendall.templating

import kotlin.properties.Delegates

/**
 * Created by Dustin Kendall on 10/20/20.
 */

class Item(name: String, description: String, qty: Int, rate: Double, sub_total : Double = qty*rate ){

    var name: String = name
    var description: String = description
    var rate: Double = rate
    var qty: Int = qty
    var sub_total: Double = sub_total

    init {
        this.name = name
        this.description = description
        this.rate = rate
        this.qty = qty
        this.sub_total = sub_total
    }
}