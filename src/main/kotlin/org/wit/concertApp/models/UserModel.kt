package org.wit.concertApp.models

data class UserModel
    (
    var userId : Long = 0,
    var firstName : String = "",
    var lastName : String = "",
    var upcomingConcerts : ArrayList<Long> = ArrayList<Long>()
)