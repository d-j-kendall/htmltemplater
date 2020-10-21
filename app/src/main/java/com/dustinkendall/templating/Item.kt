package com.dustinkendall.templating

import kotlin.properties.Delegates

/**
 * Created by Dustin Kendall on 10/20/20.
 */

class Item(name: String, description: String, qty: Int, rate: Double, sub_total : Double = qty*rate ){

    var name: String by Delegates.notNull()
    var description: String by Delegates.notNull()
    var rate: Double by Delegates.notNull()
    var qty: Int by Delegates.notNull()
    var sub_total: Double by Delegates.notNull()

    init {
        this.name = name
        this.description = description
        this.rate = rate
        this.qty = qty
        this.sub_total = sub_total
    }
}