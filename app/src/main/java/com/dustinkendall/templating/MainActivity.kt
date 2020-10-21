package com.dustinkendall.templating

import android.content.Context
import android.os.Bundle
import android.print.PrintManager
import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dustinkendall.htmltemplater.Html


/**
 * Created by Dustin Kendall on 10/20/20.
 */

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var button: Button = findViewById(R.id.generate_button)
        button.setOnClickListener( object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var pmanager : PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
                var wV = WebView(applicationContext)
                wV.loadDataWithBaseURL(null, getHtml(), "text/HTML", "UTF-8", null)
                pmanager.print("Test Job", wV.createPrintDocumentAdapter("Test Job"), null)


            }
        })
    }

    fun getHtml() : String {
        var things = arrayListOf<Item>()
        things.add(Item("Tires", "Brand new vehicles tires", 4, 75.50))
        things.add(Item("Rims", "New rims for vehicle", 4, 200.00))
        things.add(Item("Labor", "Labor to install all 4 tires", 1, 300.00))


        var cust = Customer("Billy G.", "123 Easy St", "Chicago", "IL")
        var inv = Invoice(3526)

        var row = applicationContext.assets.open("item_row.html");
        var itemsHtml = ""
        for(it in things) itemsHtml += Html(row).put("item", it).render()


        var main_doc = Html(applicationContext.assets.open("sample_report.html"))


        return main_doc.put("customer", cust)
            .put("item_rows", itemsHtml)
            .put("invoice", inv)
            .put("total_price", things.stream().mapToDouble { item -> item.sub_total }.sum())
            .render()
    }
}