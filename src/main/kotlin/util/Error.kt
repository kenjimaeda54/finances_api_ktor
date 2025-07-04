package com.util

enum class Error(val code: String, val message: String) {

    ML00(code = "ML-00", message = "Access denied, token invalid or experienced"),
    ML01(code =  "ML-01", message = "Invalid credentials"),
    ML02(code = "ML-02", message = "%s"),

    ML100(code = "ML-100", message = "Invalid request"),
    ML101(code = "ML-101", message = "Missing property look documentation"),

    ML301(code = "ML-301", message = "Property {%s} is empty is mandatory"),

    ML201(code = "ML-201", message = "Customer {%s} not exist"),
    ML202(code = "ML-202", message = "Not found customer with phone {%s}")
}