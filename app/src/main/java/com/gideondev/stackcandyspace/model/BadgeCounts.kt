package com.gideondev.stackcandyspace.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BadgeCounts(

    @field:SerializedName("gold")
    val gold: Int? = null,

    @field:SerializedName("silver")
    val silver: Int? = null,

    @field:SerializedName("bronze")
    val bronze: Int? = null

): Serializable {
    override fun toString(): String {
        return "gold=$gold, silver=$silver, bronze=$bronze"
    }
}
