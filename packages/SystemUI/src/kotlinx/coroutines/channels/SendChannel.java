package kotlinx.coroutines.channels;

import kotlin.coroutines.Continuation;

/* loaded from: classes4.dex */
public interface SendChannel {
    boolean close(Throwable th);

    void invokeOnClose(ProduceKt$awaitClose$4$1 produceKt$awaitClose$4$1);

    boolean isClosedForSend();

    Object send(Object obj, Continuation continuation);

    /* renamed from: trySend-JP2dKIU */
    Object mo3475trySendJP2dKIU(Object obj);
}
