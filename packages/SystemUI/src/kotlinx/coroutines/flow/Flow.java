package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface Flow {
    Object collect(FlowCollector flowCollector, Continuation continuation);
}
