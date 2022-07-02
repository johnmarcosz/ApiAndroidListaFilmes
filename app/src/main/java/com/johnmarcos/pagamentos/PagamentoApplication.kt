package com.johnmarcos.pagamentos

import android.app.Application


class PagamentoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}