package com.mh.openweather

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class AppScope private constructor(
    private val default: CoroutineContext,
    private val main: CoroutineContext,
    private val io: CoroutineContext
) {
    @Inject
    constructor() : this(
        Dispatchers.Default,
        Dispatchers.Main,
        Dispatchers.IO
    )

    val defaultScope: CoroutineScope
        get() = CoroutineScope(default)

    val mainScope: CoroutineScope
        get() = CoroutineScope(main)

    val ioScope: CoroutineScope
        get() = CoroutineScope(io)
}