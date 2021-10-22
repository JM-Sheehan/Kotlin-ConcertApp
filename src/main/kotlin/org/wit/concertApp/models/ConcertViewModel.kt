package org.wit.concertApp.console.models

import tornadofx.*

class ConcertViewModel(concert: ConcertModel) : ItemViewModel<ConcertModel>(concert){
    val id = bind(ConcertModel::concertId)
    var date = bind(ConcertModel::date)
    val headlineAct = bind(ConcertModel::headlineAct)
    val url = bind(ConcertModel::url)
    val address = bind(ConcertModel::address)
}