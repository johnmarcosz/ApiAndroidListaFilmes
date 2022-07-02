package com.johnmarcos.pagamentos.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johnmarcos.pagamentos.data.entity.Categoria
import com.johnmarcos.pagamentos.data.entity.Pagamento

@Database(
    entities = [Categoria::class, Pagamento::class],
    version = 2,
    exportSchema = false
)
abstract class PagamentoDatabase : RoomDatabase() {
    abstract fun categoriaDao(): CategoriaDao
    abstract fun pagamentoDao(): PagamentoDao
}