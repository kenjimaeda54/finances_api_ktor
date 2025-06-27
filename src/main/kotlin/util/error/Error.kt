package com.util.error

enum class Error(val code: String, val message: String) {

    ML00(code = "ML--00", message = "ACCESS DENIED"),

    ML001(code = "ML-001", message = "Invalid request"),
    ML002(code = "ML-002", message = "Missing property look documentation"),

    ML301(code = "ML-301", message = "Property {%s} is empty is mandatory"),

    ML201(code = "ML-201", message = "Customer {%s} not exist")
}