package org.wit.concertApp.console.models

import tornadofx.*

class UserViewModel(user: UserModel) : ItemViewModel<UserModel>(user){
    val id = bind(UserModel::userId)
    val firstName = bind(UserModel::firstName)
    val lastName = bind(UserModel::lastName)
}