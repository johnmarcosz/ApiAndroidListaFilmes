package com.johnmarcos.pagamentos.data.repository

import com.johnmarcos.pagamentos.data.entity.Categoria
import com.johnmarcos.pagamentos.data.room.CategoriaDao
import kotlinx.coroutines.flow.Flow

class CategoriaRepository(
    private val categoriaDao: CategoriaDao
) {
    fun categorias(): Flow<List<Categoria>> = categoriaDao.categorias()
    fun getCategoryWithId(idCategoria: Long): Categoria? = categoriaDao.getCategoriaComId(idCategoria)


    suspend fun addCategoria(categoria: Categoria): Long {
        return when (val local = categoriaDao.getCategoriaComNome(categoria.nome)) {
            null -> categoriaDao.insert(categoria)
            else -> local.id
        }
    }
}