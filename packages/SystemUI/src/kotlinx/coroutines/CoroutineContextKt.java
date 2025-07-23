package kotlinx.coroutines;

import android.os.Trace;
import com.android.app.tracing.coroutines.CoroutineTraceName;
import com.android.app.tracing.coroutines.TraceContextElement;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CoroutineContextKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v4, types: [T, java.lang.Object] */
    public static final CoroutineContext foldCopies(CoroutineContext coroutineContext, CoroutineContext coroutineContext2, final boolean z) {
        Boolean bool = Boolean.FALSE;
        boolean booleanValue = ((Boolean) coroutineContext.fold(bool, new CoroutineContextKt$$ExternalSyntheticLambda0(0))).booleanValue();
        boolean booleanValue2 = ((Boolean) coroutineContext2.fold(bool, new CoroutineContextKt$$ExternalSyntheticLambda0(0))).booleanValue();
        if (!booleanValue && !booleanValue2) {
            return coroutineContext.plus(coroutineContext2);
        }
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = coroutineContext2;
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        CoroutineContext coroutineContext3 = (CoroutineContext) coroutineContext.fold(emptyCoroutineContext, new Function2() { // from class: kotlinx.coroutines.CoroutineContextKt$$ExternalSyntheticLambda1
            /* JADX WARN: Type inference failed for: r5v3, types: [T, kotlin.coroutines.CoroutineContext] */
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                CoroutineContext coroutineContext4 = (CoroutineContext) obj;
                CoroutineContext.Element element = (CoroutineContext.Element) obj2;
                if (!(element instanceof CopyableThreadContextElement)) {
                    return coroutineContext4.plus(element);
                }
                Ref$ObjectRef ref$ObjectRef2 = Ref$ObjectRef.this;
                CoroutineContext coroutineContext5 = (CoroutineContext) ref$ObjectRef2.element;
                ((CoroutineTraceName) element).getClass();
                CoroutineTraceName.Key key = CoroutineTraceName.Key;
                CoroutineContext.Element element2 = coroutineContext5.get(key);
                if (element2 == null) {
                    return coroutineContext4.plus(z ? ((TraceContextElement) ((CopyableThreadContextElement) element)).copyForChild() : (CopyableThreadContextElement) element);
                }
                ref$ObjectRef2.element = ((CoroutineContext) ref$ObjectRef2.element).minusKey(key);
                TraceContextElement traceContextElement = (TraceContextElement) ((CopyableThreadContextElement) element);
                traceContextElement.getClass();
                try {
                    Trace.traceBegin(4096L, traceContextElement.mergeForChildTraceMessage);
                    CoroutineTraceName coroutineTraceName = (CoroutineTraceName) element2.get(key);
                    TraceContextElement createChildContext = traceContextElement.createChildContext(coroutineTraceName != null ? coroutineTraceName.name : null);
                    Trace.traceEnd(4096L);
                    return coroutineContext4.plus(createChildContext);
                } catch (Throwable th) {
                    Trace.traceEnd(4096L);
                    throw th;
                }
            }
        });
        if (booleanValue2) {
            ref$ObjectRef.element = ((CoroutineContext) ref$ObjectRef.element).fold(emptyCoroutineContext, new CoroutineContextKt$$ExternalSyntheticLambda0(1));
        }
        return coroutineContext3.plus((CoroutineContext) ref$ObjectRef.element);
    }

    public static final CoroutineContext newCoroutineContext(CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        CoroutineContext foldCopies = foldCopies(coroutineScope.getCoroutineContext(), coroutineContext, true);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        return (foldCopies == defaultScheduler || foldCopies.get(ContinuationInterceptor.Key) != null) ? foldCopies : foldCopies.plus(defaultScheduler);
    }

    public static final UndispatchedCoroutine updateUndispatchedCompletion(Continuation continuation, CoroutineContext coroutineContext, Object obj) {
        UndispatchedCoroutine undispatchedCoroutine = null;
        if ((continuation instanceof CoroutineStackFrame) && coroutineContext.get(UndispatchedMarker.INSTANCE) != null) {
            CoroutineStackFrame coroutineStackFrame = (CoroutineStackFrame) continuation;
            while (true) {
                if ((coroutineStackFrame instanceof DispatchedCoroutine) || (coroutineStackFrame = coroutineStackFrame.getCallerFrame()) == null) {
                    break;
                }
                if (coroutineStackFrame instanceof UndispatchedCoroutine) {
                    undispatchedCoroutine = (UndispatchedCoroutine) coroutineStackFrame;
                    break;
                }
            }
            if (undispatchedCoroutine != null) {
                undispatchedCoroutine.saveThreadContext(coroutineContext, obj);
            }
        }
        return undispatchedCoroutine;
    }
}
