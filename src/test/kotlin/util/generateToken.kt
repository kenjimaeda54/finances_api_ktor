package util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.util.Constants.PAYLOAD_CLAIM_ID
import com.util.Constants.PAYLOAD_CLAIM_PHONE

fun generateTestToken(customerId: String, phone: String): String {
    return JWT.create()
        .withClaim(PAYLOAD_CLAIM_ID, customerId)
        .withClaim(PAYLOAD_CLAIM_PHONE, phone)
        .withAudience(TestsConstants.JWT_AUDIENCE)
        .withIssuer(TestsConstants.JWT_ISSUER)
        .sign(
            Algorithm.HMAC256(TestsConstants.JWT_SECRET),
        )
}