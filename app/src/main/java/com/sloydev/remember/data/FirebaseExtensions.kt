package com.sloydev.remember.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlin.coroutines.experimental.suspendCoroutine


suspend fun DatabaseReference.getValue(): DataSnapshot {
    return async(CommonPool) {
        suspendCoroutine<DataSnapshot> { continuation ->
            addListenerForSingleValueEvent(FValueEventListener(
                    onDataChange = { continuation.resume(it) },
                    onError = { continuation.resumeWithException(it.toException()) }
            ))
        }
    }.await()
}

class FValueEventListener(val onDataChange: (DataSnapshot) -> Unit, val onError: (DatabaseError) -> Unit) : ValueEventListener {
    override fun onDataChange(data: DataSnapshot) = onDataChange.invoke(data)
    override fun onCancelled(error: DatabaseError) = onError.invoke(error)
}
