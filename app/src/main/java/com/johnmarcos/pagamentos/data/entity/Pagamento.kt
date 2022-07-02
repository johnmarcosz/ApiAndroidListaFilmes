package com.johnmarcos.pagamentos.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pagamentos",
    indices = [
        Index("id", unique = true),
        Index("pagamento_categoria_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["id"],
            childColumns = ["pagamento_categoria_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Pagamento(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val idPagamento: Long = 0,
    @ColumnInfo(name = "titulo_pagamento") val titulo_pagamento: String,
    @ColumnInfo(name = "data_pagamento") val data_pagamento: Long,
    @ColumnInfo(name = "pagamento_categoria_id") val pagamento_categoria_id: Long,
    @ColumnInfo(name = "valor_pagamento") val valor_pagamento: Double
)
