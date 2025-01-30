package kotlinx.coroutines.channels;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface SendChannel {
    boolean close(Throwable th);

    void invokeOnClose(Function1 function1);

    boolean isClosedForSend();

    Object send(Object obj, Continuation continuation);

    /* renamed from: trySend-JP2dKIU */
    Object mo2872trySendJP2dKIU(Object obj);
}
