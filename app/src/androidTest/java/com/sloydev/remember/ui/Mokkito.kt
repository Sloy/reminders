package com.sloydev.remember.ui

import kotlinx.coroutines.experimental.runBlocking
import org.mockito.BDDMockito
import org.mockito.Mockito

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)
fun <T> givenn(methodCall: suspend () -> T): BDDMockito.BDDMyOngoingStubbing<T> {
    return runBlocking {
        BDDMockito.given(methodCall.invoke())
    }
}
