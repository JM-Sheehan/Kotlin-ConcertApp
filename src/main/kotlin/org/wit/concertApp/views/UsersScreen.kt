package org.wit.concertApp.console.views

import javafx.geometry.Pos
import org.wit.concertApp.console.controllers.ConcertController
import org.wit.concertApp.console.controllers.ConcertUIController
import org.wit.concertApp.console.models.UserModel
import org.wit.concertApp.console.models.UserViewModel
import tornadofx.*

class UsersScreen : View("Users Editor") {

    val concertUIController: ConcertUIController by inject()
    val tableContent = concertUIController.users.findAll()
    val data = tableContent.observable()
    val model = UserViewModel(UserModel())

    override val root = borderpane () {
        setPrefSize(1200.0, 800.0)
    }
    init{
        with(root){
            center{
                tableview(data){
                    readonlyColumn( "ID" , UserModel::userId)
                    column("First Name", UserModel::firstName)
                    column("Last Name", UserModel::lastName)
                    readonlyColumn("Upcoming", UserModel::upcomingConcerts)
                    model.rebindOnChange(this){ selectedUser ->
                        item = selectedUser ?: UserModel()
                    }
                }
            }
            right{
                form{
                    fieldset ("Edit User"){
                        alignment = Pos.CENTER
                        spacing = 10.0
                        field("First Name"){
                            textfield(model.firstName)
                        }
                        field("Last Name") {
                            textfield(model.lastName)
                        }
                        button("Update"){
                            isDefaultButton = true
                            setPrefSize(100.0, 10.0)
                            enableWhen(model.dirty)
                            action{
                                update()
                            }
                        }

                        button("Delete") {
                            isDefaultButton = true
                            setPrefSize(100.0, 10.0)
                            action{
                                delete()
                            }
                        }
                        button("Reset"){
                            isDefaultButton = true
                            setPrefSize(100.0, 10.0)
                            model.rollback()
                        }
                        button("Close"){
                            isDefaultButton = true
                            setPrefSize(100.0, 10.0)
                            action {
                                runAsyncWithProgress {
                                    concertUIController.closeUsersScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun update(){
        model.commit()
        val user = model.item

        concertUIController.updateUser(user)
    }

    private fun delete(){
        model.commit()
        val user = model.item
        concertUIController.deleteUser(user.userId!!)
    }
}
