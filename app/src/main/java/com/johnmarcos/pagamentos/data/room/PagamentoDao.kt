package com.johnmarcos.pagamentos.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.johnmarcos.pagamentos.data.entity.Pagamento
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PagamentoDao {
    @Query("""
        SELECT pagamentos.* FROM pagamentos
        INNER JOIN categorias ON pagamentos.pagamento_categoria_id = categorias.id
        WHERE pagamento_categoria_id = :idCategoria
    """)
    abstract fun pagamentosDaCategoria(idCategoria: Long): Flow<List<PagamentoParaCategoria>>

    @Query("""SELECT * FROM pagamentos WHERE id = :idPagamento""")
    abstract fun payment(idPagamento: Long): Pagamento?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Pagamento): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Pagamento)

    @Delete
    abstract suspend fun delete(entity: Pagamento): Int
}