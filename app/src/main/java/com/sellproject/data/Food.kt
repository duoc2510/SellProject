package com.sellproject.data

data class Food(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var price: Int = 0,
    var userId: String = ""   // ← thêm dòng này

)