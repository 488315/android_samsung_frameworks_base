package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ThrowingCollector implements FlowCollector {

    /* renamed from: e */
    public final Throwable f668e;

    public ThrowingCollector(Throwable th) {
        this.f668e = th;
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        throw this.f668e;
    }
}
