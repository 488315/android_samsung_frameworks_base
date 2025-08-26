package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;

/* loaded from: classes4.dex */
public interface Flow {
    Object collect(FlowCollector flowCollector, Continuation continuation);
}
