package com.johnmarcos.pagamentos

import android.content.Context
import androidx.room.Room
import com.johnmarcos.pagamentos.data.repository.CategoriaRepository
import com.johnmarcos.pagamentos.data.repository.PagamentoRepository
import com.johnmarcos.pagamentos.data.room.PagamentoDatabase

/**
 * A simple singleton dependency graph
 *
 * For a real app, please use something like Koin/Dagger/Hilt instead
 */
object Graph {
    lateinit var database: PagamentoDatabase

    val categoriaRepository by lazy {
        CategoriaRepository(
            categoriaDao = database.categoriaDao()
        )
    }

    val pagamentoRepository by lazy {
        PagamentoRepository(
            pagamentoDao = database.pagamentoDao()
        )
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, PagamentoDatabase::class.java, "mcData.db")
            .fallbackToDestructiveMigration() // don't use this in production app
            .build()
    }
}