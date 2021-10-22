package org.wit.concertApp.console.models

data class ConcertModel
    (
    var concertId : Long = 0,
    var date : String = "",
    var headlineAct : String = "",
    var supportingActs : ArrayList<String> = ArrayList<String>(),
    var url : String = "Blank",
    var address: String = ""
)