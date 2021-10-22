package org.wit.concertApp.console.views

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import org.wit.concertApp.console.controllers.ConcertUIController
import org.wit.concertApp.console.models.ConcertModel
import org.wit.concertApp.console.models.ConcertViewModel
import tornadofx.*
import java.time.LocalDate


class ConcertsScreen : View("Concerts Editor") {

    val concertUIController: ConcertUIController by inject()
    var tableContent = concertUIController.concerts.findAll()
    var data = tableContent.observable()
    val model = ConcertViewModel(ConcertModel())
    var date = model.bind { SimpleObjectProperty<LocalDate>() }

    override val root = borderpane (){
        setPrefSize(1200.0, 800.0)
    }

    init {
        with(root){
            center{
                tableview (data){
                    readonlyColumn("ID", ConcertModel::concertId)
                    column("Headliner", ConcertModel::headlineAct)
                    column("Date", ConcertModel::date)
                    column("Address", ConcertModel::address)
                    column("url", ConcertModel::url)

                    model.rebindOnChange(this){ selectedConcert ->
                        item = selectedConcert ?: ConcertModel()
                    }
                }
            }
            right{
                form{
                    fieldset ("Edit Concert"){
                        alignment = Pos.CENTER
                        spacing = 10.0

                        field ("Headliner"){
                            textfield(model.headlineAct)
                        }
                        field ("Date"){
                            datepicker(date)
                        }
                        field ("Address"){
                            textfield(model.address)
                        }
                        field ("url"){
                            textfield(model.url)
                        }
                        button("Update"){
                            enableWhen(model.dirty)
                            action{
                                update()
                            }
                        }
                        button("Delete"){
                            action{
                                delete()
                            }
                        }
                        button("Reset"){
                            model.rollback()
                        }

                        button("Close") {
                            isDefaultButton = true
                            setPrefSize(100.0, 10.0)
                            action {
                                runAsyncWithProgress {
                                    concertUIController.closeConcertsScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private fun update(){
        val dateAsString = (date.value).toString()
        model.date.value = dateAsString
        val concert = model.item

        model.commit()

        concertUIController.updateConcert(concert)

    }

    private  fun delete(){
        model.commit()
        val concert = model.item
        concertUIController.deleteConcert(concert.concertId)
    }
}

