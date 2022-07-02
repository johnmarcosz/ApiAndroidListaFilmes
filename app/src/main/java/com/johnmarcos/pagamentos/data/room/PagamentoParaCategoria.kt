package com.johnmarcos.pagamentos.data.room

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.johnmarcos.pagamentos.data.entity.Categoria
import com.johnmarcos.pagamentos.data.entity.Pagamento
import java.util.*

class PagamentoParaCategoria {
    @Embedded
    lateinit var pagamento: Pagamento

    @Relation(parentColumn = "pagamento_categoria_id", entityColumn = "id")
    lateinit var _categorias: List<Categoria>

    @get:Ignore
    val categoria: Categoria
        get() = _categorias[0]


    operator fun component1() = pagamento
    operator fun component2() = categoria

    override fun equals(outro: Any?): Boolean = when {
        outro === this -> true
        outro is PagamentoParaCategoria -> pagamento == outro.pagamento && _categorias == outro._categorias
        else -> false
    }

    override fun hashCode(): Int = Objects.hash(pagamento, _categorias)
}