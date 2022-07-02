package com.johnmarcos.pagamentos.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.johnmarcos.pagamentos.data.entity.Categoria
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CategoriaDao {

    @Query(value = "SELECT * FROM categorias WHERE nome = :name")
    abstract suspend fun getCategoriaComNome(name: String): Categoria?

    @Query("SELECT * FROM categorias WHERE id = :idCategoria")
    abstract fun getCategoriaComId(idCategoria: Long): Categoria?

    @Query("SELECT * FROM categorias LIMIT 15")
    abstract fun categorias(): Flow<List<Categoria>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Categoria): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<Categoria>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Categoria)

    @Delete
    abstract suspend fun delete(entity: Categoria): Int
}