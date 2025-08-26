package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;

/* loaded from: classes4.dex */
public interface FlowCollector {
    Object emit(Object obj, Continuation continuation);
}
