package com.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object Environment {
    private var _jwtSecret = MutableStateFlow("")
    var jwtSecret: StateFlow<String> = _jwtSecret
    private var _jwtIssuer = MutableStateFlow("")
    var jwtIssuer: StateFlow<String> = _jwtIssuer
    private var _jwtAudience = MutableStateFlow("")
    var jwtAudience: StateFlow<String> = _jwtAudience
    private var _jwtRealm = MutableStateFlow("")
    var jwtRealm: StateFlow<String> = _jwtRealm
    private var _dbUrl = MutableStateFlow("")
    var dbUrl: MutableStateFlow<String> = _dbUrl
    private var _dbUser = MutableStateFlow("")
    var dbUser: StateFlow<String> = _dbUser
    private var _dbPassword = MutableStateFlow("")
    var dbPassword: StateFlow<String> = _dbPassword


    fun updateJwtSecret(secret: String)  {
        _jwtSecret.value = secret
    }

    fun updateJwtIssuer(issuer: String) {
        _jwtIssuer.value = issuer
    }

    fun updateJwtAudience(audience: String) {
        _jwtAudience.value = audience
    }

    fun updateJwtRealm(realm: String) {
       _jwtRealm.value = realm
    }

    fun updateDbUrl(url: String) {
        _dbUrl.value = url
    }

    fun updateDbUser(user: String) {
        _dbUser.value = user
    }

    fun updateDbPassword(password: String) {
        _dbPassword.value = password
    }


}