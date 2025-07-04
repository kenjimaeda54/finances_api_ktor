package com.util

import org.mindrot.jbcrypt.BCrypt

object PasswordHashing {

    fun hashingPassword(password: String): String =
        BCrypt.hashpw(password, BCrypt.gensalt())

    fun verifyPassword(hashingPassword: String, plainPassword: String)  =
        BCrypt.checkpw(plainPassword,hashingPassword)

}