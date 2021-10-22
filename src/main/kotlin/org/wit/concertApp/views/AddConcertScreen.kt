package org.wit.concertApp.console.views

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import org.wit.concertApp.console.controllers.ConcertUIController
import tornadofx.*
import java.time.LocalDate

class AddConcertScreen : View("My View") {
    val model = ViewModel()
    val _dateProperty = model.bind { SimpleObjectProperty<LocalDate>() }
    var _date = ""
    val _headlineAct = model.bind { SimpleStringProperty() }
    val _url = model.bind { SimpleStringProperty() }
    val _address = model.bind { SimpleStringProperty() }
    val concertUIController: ConcertUIController by inject()

    override val root = form {
        setPrefSize(400.0, 400.0)
        alignment = Pos.CENTER
        spacing = 20.0

        vbox {
            alignment = Pos.CENTER
            spacing = 10.0
            style{
                fontWeight = FontWeight.EXTRA_BOLD
            }
            label("Date"){}

            datepicker(_dateProperty) {
                value = LocalDate.now()
            }.required()
        }

        vbox {
            alignment = Pos.CENTER
            spacing = 10.0
            style{
                fontWeight = FontWeight.EXTRA_BOLD
            }
            label("Headline Act")

            textfield(_headlineAct).required()
        }

        vbox {
            alignment = Pos.CENTER
            spacing = 10.0
            style{
                fontWeight = FontWeight.EXTRA_BOLD
            }
            label("url")

            textfield(_url).required()
        }

        vbox {
            alignment = Pos.CENTER
            spacing = 10.0
            style{
                fontWeight = FontWeight.EXTRA_BOLD
            }
            label("Address")

            textfield(_address).required()
        }

        button("Add Concert") {
            enableWhen(model.valid)
            isDefaultButton = true
            setPrefSize(200.0, 10.0)
            action {
                runAsyncWithProgress {
                    concertUIController.addConcert(_dateProperty.value.toString(), _headlineAct.value.toString(),
                        _url.value.toString(), _address.value.toString())
                }
            }
        }
        button("Close") {
            isDefaultButton = true
            setPrefSize(200.0, 10.0)
            action {
                runAsyncWithProgress {
                    concertUIController.closeAddConcertScreen()
                }
            }
        }
    }

    override fun onDock() {
        _headlineAct.value = ""
        _address.value = ""
        _url.value = ""
        model.clearDecorators()
    }
}
