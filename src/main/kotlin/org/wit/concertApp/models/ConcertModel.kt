package org.wit.concertApp.models

import java.net.Inet4Address

data class ConcertModel
    (
    var id : Long = 0,
    var date : String = "",
    var headlineAct : String = "",
    var supportingActs : ArrayList<String> = ArrayList<String>(),
    var url : String = "Blank",
    var address: String = ""
)