package kotlin.sequences;

import java.util.Iterator;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class SequenceScope {
    public abstract CoroutineSingletons yield(Object obj, Continuation continuation);

    public abstract Object yieldAll(Iterator it, Continuation continuation);

    public final Object yieldAll(Sequence sequence, Continuation continuation) {
        Object yieldAll = yieldAll(sequence.iterator(), continuation);
        return yieldAll == CoroutineSingletons.COROUTINE_SUSPENDED ? yieldAll : Unit.INSTANCE;
    }
}
