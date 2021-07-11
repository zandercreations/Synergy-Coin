package com.supplychain.baf

import net.corda.core.serialization.CordaSerializable
import java.util.*

@CordaSerializable
data class CreateContainerRequest(
    val health: String?,
    val misc: Map<String, Any>,
    val trackingID: UUID,
    val counterparties: List<String>
)