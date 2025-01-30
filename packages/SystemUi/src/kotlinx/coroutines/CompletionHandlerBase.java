package kotlinx.coroutines;

import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class CompletionHandlerBase extends LockFreeLinkedListNode implements Function1 {
    public abstract void invoke(Throwable th);
}
