# htmltemplater

A tiny Java/Android helper that fills `${...}` placeholders in an HTML template from plain objects — handy for generating printable HTML documents (invoices, reports) to render or print from a `WebView`.

_Status: small library (2020) · Java · Android_

## How it works

Bind values to a template with `.put(name, object)` and call `.render()`. Placeholders resolve against:

- primitives — `${total_price}`
- an object's fields and zero-arg getters — `${customer.name}`, `${invoice.getId()}`
- or another already-rendered `Html` fragment, so you can compose a row template into a larger report

## Example

```java
// item_row.html:  <div>${item.name} — ${item.qty} @ ${item.rate}</div>
String itemsHtml = "";
for (Item it : items) {
    itemsHtml += new Html(rowTemplate).put("item", it).render();
}

Html report = new Html(assets.open("sample_report.html"));
String html = report
    .put("customer", customer)     // ${customer.name}, ${customer.city}, …
    .put("item_rows", itemsHtml)   // a pre-rendered fragment
    .put("invoice", invoice)       // ${invoice.getId()}
    .put("total_price", total)
    .render();
```

On Android, hand `html` to a `WebView` and print it via `PrintManager` — see `app/MainActivity.kt`
for a complete invoice example.

## Install

It was originally published as `com.dustinkendall.htmltemplater:0.0.1` via jcenter (now sunset), so
the simplest path today is to build the `htmltemplater` module from source or drop `Html.java` into
your project.

---
Built by **Dustin Kendall** — production readiness & rescue for AI-built apps.
[dustinkendall.com](https://dustinkendall.com) · [LinkedIn](https://www.linkedin.com/in/dustinjkendall)
