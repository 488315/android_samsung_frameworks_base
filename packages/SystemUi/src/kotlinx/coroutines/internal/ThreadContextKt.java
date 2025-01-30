package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineId;
import kotlinx.coroutines.ThreadContextElement;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ThreadContextKt {
    public static final Symbol NO_THREAD_ELEMENTS = new Symbol("NO_THREAD_ELEMENTS");
    public static final Function2 countAll = new Function2() { // from class: kotlinx.coroutines.internal.ThreadContextKt$countAll$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            CoroutineContext.Element element = (CoroutineContext.Element) obj2;
            if (!(element instanceof ThreadContextElement)) {
                return obj;
            }
            Integer num = obj instanceof Integer ? (Integer) obj : null;
            int intValue = num != null ? num.intValue() : 1;
            return intValue == 0 ? element : Integer.valueOf(intValue + 1);
        }
    };
    public static final Function2 findOne = new Function2() { // from class: kotlinx.coroutines.internal.ThreadContextKt$findOne$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ThreadContextElement threadContextElement = (ThreadContextElement) obj;
            CoroutineContext.Element element = (CoroutineContext.Element) obj2;
            if (threadContextElement != null) {
                return threadContextElement;
            }
            if (element instanceof ThreadContextElement) {
                return (ThreadContextElement) element;
            }
            return null;
        }
    };
    public static final Function2 updateState = new Function2() { // from class: kotlinx.coroutines.internal.ThreadContextKt$updateState$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ThreadState threadState = (ThreadState) obj;
            CoroutineContext.Element element = (CoroutineContext.Element) obj2;
            if (element instanceof ThreadContextElement) {
                ThreadContextElement threadContextElement = (ThreadContextElement) element;
                Object updateThreadContext = ((CoroutineId) threadContextElement).updateThreadContext(threadState.context);
                int i = threadState.f673i;
                threadState.values[i] = updateThreadContext;
                threadState.f673i = i + 1;
                threadState.elements[i] = threadContextElement;
            }
            return threadState;
        }
    };

    public static final void restoreThreadContext(CoroutineContext coroutineContext, Object obj) {
        if (obj == NO_THREAD_ELEMENTS) {
            return;
        }
        if (!(obj instanceof ThreadState)) {
            ((CoroutineId) ((ThreadContextElement) coroutineContext.fold(null, findOne))).getClass();
            Thread.currentThread().setName((String) obj);
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
            Intrinsics.checkNotNull(threadContextElementArr[length]);
            Thread.currentThread().setName((String) threadState.values[length]);
            if (i < 0) {
                return;
            } else {
                length = i;
            }
        }
    }

    public static final Object threadContextElements(CoroutineContext coroutineContext) {
        Object fold = coroutineContext.fold(0, countAll);
        Intrinsics.checkNotNull(fold);
        return fold;
    }

    public static final Object updateThreadContext(CoroutineContext coroutineContext, Object obj) {
        if (obj == null) {
            obj = threadContextElements(coroutineContext);
        }
        return obj == 0 ? NO_THREAD_ELEMENTS : obj instanceof Integer ? coroutineContext.fold(new ThreadState(coroutineContext, ((Number) obj).intValue()), updateState) : ((CoroutineId) ((ThreadContextElement) obj)).updateThreadContext(coroutineContext);
    }
}
