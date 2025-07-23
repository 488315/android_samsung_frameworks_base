package kotlinx.coroutines.channels;

import kotlin.coroutines.Continuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface SendChannel {
    boolean close(Throwable th);

    void invokeOnClose(ProduceKt$awaitClose$4$1 produceKt$awaitClose$4$1);

    boolean isClosedForSend();

    Object send(Object obj, Continuation continuation);

    /* renamed from: trySend-JP2dKIU */
    Object mo3456trySendJP2dKIU(Object obj);
}
