package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.ThreadContextElement;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ThreadContextKt {
    public static final Symbol NO_THREAD_ELEMENTS = new Symbol("NO_THREAD_ELEMENTS");
    public static final ThreadContextKt$$ExternalSyntheticLambda0 countAll;
    public static final ThreadContextKt$$ExternalSyntheticLambda0 findOne;
    public static final ThreadContextKt$$ExternalSyntheticLambda0 updateState;

    /* JADX WARN: Type inference failed for: r0v1, types: [kotlinx.coroutines.internal.ThreadContextKt$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v2, types: [kotlinx.coroutines.internal.ThreadContextKt$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v3, types: [kotlinx.coroutines.internal.ThreadContextKt$$ExternalSyntheticLambda0] */
    static {
        final int i = 0;
        countAll = new Function2() { // from class: kotlinx.coroutines.internal.ThreadContextKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i) {
                    case 0:
                        CoroutineContext.Element element = (CoroutineContext.Element) obj2;
                        if (!(element instanceof ThreadContextElement)) {
                            return obj;
                        }
                        Integer num = obj instanceof Integer ? (Integer) obj : null;
                        int intValue = num != null ? num.intValue() : 1;
                        return intValue == 0 ? element : Integer.valueOf(intValue + 1);
                    case 1:
                        ThreadContextElement threadContextElement = (ThreadContextElement) obj;
                        CoroutineContext.Element element2 = (CoroutineContext.Element) obj2;
                        if (threadContextElement != null) {
                            return threadContextElement;
                        }
                        if (element2 instanceof ThreadContextElement) {
                            return (ThreadContextElement) element2;
                        }
                        return null;
                    default:
                        ThreadState threadState = (ThreadState) obj;
                        CoroutineContext.Element element3 = (CoroutineContext.Element) obj2;
                        if (element3 instanceof ThreadContextElement) {
                            ThreadContextElement threadContextElement2 = (ThreadContextElement) element3;
                            Object updateThreadContext = threadContextElement2.updateThreadContext(threadState.context);
                            int i2 = threadState.i;
                            threadState.values[i2] = updateThreadContext;
                            threadState.i = i2 + 1;
                            threadState.elements[i2] = threadContextElement2;
                        }
                        return threadState;
                }
            }
        };
        final int i2 = 1;
        findOne = new Function2() { // from class: kotlinx.coroutines.internal.ThreadContextKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i2) {
                    case 0:
                        CoroutineContext.Element element = (CoroutineContext.Element) obj2;
                        if (!(element instanceof ThreadContextElement)) {
                            return obj;
                        }
                        Integer num = obj instanceof Integer ? (Integer) obj : null;
                        int intValue = num != null ? num.intValue() : 1;
                        return intValue == 0 ? element : Integer.valueOf(intValue + 1);
                    case 1:
                        ThreadContextElement threadContextElement = (ThreadContextElement) obj;
                        CoroutineContext.Element element2 = (CoroutineContext.Element) obj2;
                        if (threadContextElement != null) {
                            return threadContextElement;
                        }
                        if (element2 instanceof ThreadContextElement) {
                            return (ThreadContextElement) element2;
                        }
                        return null;
                    default:
                        ThreadState threadState = (ThreadState) obj;
                        CoroutineContext.Element element3 = (CoroutineContext.Element) obj2;
                        if (element3 instanceof ThreadContextElement) {
                            ThreadContextElement threadContextElement2 = (ThreadContextElement) element3;
                            Object updateThreadContext = threadContextElement2.updateThreadContext(threadState.context);
                            int i22 = threadState.i;
                            threadState.values[i22] = updateThreadContext;
                            threadState.i = i22 + 1;
                            threadState.elements[i22] = threadContextElement2;
                        }
                        return threadState;
                }
            }
        };
        final int i3 = 2;
        updateState = new Function2() { // from class: kotlinx.coroutines.internal.ThreadContextKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i3) {
                    case 0:
                        CoroutineContext.Element element = (CoroutineContext.Element) obj2;
                        if (!(element instanceof ThreadContextElement)) {
                            return obj;
                        }
                        Integer num = obj instanceof Integer ? (Integer) obj : null;
                        int intValue = num != null ? num.intValue() : 1;
                        return intValue == 0 ? element : Integer.valueOf(intValue + 1);
                    case 1:
                        ThreadContextElement threadContextElement = (ThreadContextElement) obj;
                        CoroutineContext.Element element2 = (CoroutineContext.Element) obj2;
                        if (threadContextElement != null) {
                            return threadContextElement;
                        }
                        if (element2 instanceof ThreadContextElement) {
                            return (ThreadContextElement) element2;
                        }
                        return null;
                    default:
                        ThreadState threadState = (ThreadState) obj;
                        CoroutineContext.Element element3 = (CoroutineContext.Element) obj2;
                        if (element3 instanceof ThreadContextElement) {
                            ThreadContextElement threadContextElement2 = (ThreadContextElement) element3;
                            Object updateThreadContext = threadContextElement2.updateThreadContext(threadState.context);
                            int i22 = threadState.i;
                            threadState.values[i22] = updateThreadContext;
                            threadState.i = i22 + 1;
                            threadState.elements[i22] = threadContextElement2;
                        }
                        return threadState;
                }
            }
        };
    }

    public static final void restoreThreadContext(CoroutineContext coroutineContext, Object obj) {
        if (obj == NO_THREAD_ELEMENTS) {
            return;
        }
        if (!(obj instanceof ThreadState)) {
            ((ThreadContextElement) coroutineContext.fold(null, findOne)).restoreThreadContext(obj);
            return;
        }
        ThreadState threadState = (ThreadState) obj;
        ThreadContextElement[] threadContextElementArr = threadState.elements;
        int length = threadContextElementArr.length - 1;
        if (length < 0) {
            return;
        }
        while (true) {
            int i = length - 1;
            ThreadContextElement threadContextElement = threadContextElementArr[length];
            threadContextElement.getClass();
            threadContextElement.restoreThreadContext(threadState.values[length]);
            if (i < 0) {
                return;
            } else {
                length = i;
            }
        }
    }

    public static final Object threadContextElements(CoroutineContext coroutineContext) {
        Object fold = coroutineContext.fold(0, countAll);
        fold.getClass();
        return fold;
    }

    public static final Object updateThreadContext(CoroutineContext coroutineContext, Object obj) {
        if (obj == null) {
            obj = threadContextElements(coroutineContext);
        }
        return obj == 0 ? NO_THREAD_ELEMENTS : obj instanceof Integer ? coroutineContext.fold(new ThreadState(coroutineContext, ((Number) obj).intValue()), updateState) : ((ThreadContextElement) obj).updateThreadContext(coroutineContext);
    }
}
