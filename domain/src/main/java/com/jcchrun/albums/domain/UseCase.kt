package com.jcchrun.albums.domain

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class UseCase<in Params, out Type> : CoroutineScope where Type : Any {

    private val job = Job()
    private val uiScope = Dispatchers.Main

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    abstract suspend fun run(params: Params): Type

    operator fun invoke(params: Params, onResult: (Type) -> Unit = {}) =
        launch {
            val result = run(params)

            withContext(uiScope) {
                onResult(result)
            }
        }

    fun cancel() {
        coroutineContext.cancel()
    }

    class None
}