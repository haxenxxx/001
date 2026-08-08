package com.ticketchef.app.ocr

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/** Thin coroutine wrapper around ML Kit's on-device Latin text recognizer. */
object TicketTextRecognizer {

    suspend fun recognize(image: InputImage): String = suspendCancellableCoroutine { continuation ->
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { result -> continuation.resume(result.text) }
            .addOnFailureListener { error -> continuation.resumeWithException(error) }
    }
}
