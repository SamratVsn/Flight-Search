package com.example.flightsearch.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

fun <T> Flow<List<T>>.listStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000),
    initialValue: List<T> = emptyList<T>(),
): StateFlow<List<T>> = this.stateIn(
    scope = scope,
    started = started,
    initialValue = initialValue,
)