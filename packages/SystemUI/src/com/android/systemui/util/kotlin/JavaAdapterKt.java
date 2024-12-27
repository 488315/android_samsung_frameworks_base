package com.android.systemui.util.kotlin;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import java.util.function.Consumer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

public final class JavaAdapterKt {
    public static final <T> Job collectFlow(Lifecycle lifecycle, Flow flow, Consumer<T> consumer) {
        return collectFlow$default(lifecycle, flow, consumer, null, 8, null);
    }

    public static /* synthetic */ void collectFlow$default(View view, Flow flow, Consumer consumer, CoroutineContext coroutineContext, Lifecycle.State state, int i, Object obj) {
        if ((i & 8) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 16) != 0) {
            state = Lifecycle.State.CREATED;
        }
        collectFlow(view, flow, consumer, coroutineContext, state);
    }

    public static final <A, B, R> Flow combineFlows(Flow flow, Flow flow2, Function2 function2) {
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, flow2, new JavaAdapterKt$combineFlows$1(function2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object combineFlows$suspendConversion0(Function2 function2, Object obj, Object obj2, Continuation continuation) {
        return function2.invoke(obj, obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object combineFlows$suspendConversion0$0(Function3 function3, Object obj, Object obj2, Object obj3, Continuation continuation) {
        return function3.invoke(obj, obj2, obj3);
    }

    public static final <T> void collectFlow(View view, Flow flow, Consumer<T> consumer) {
        collectFlow$default(view, flow, consumer, null, null, 24, null);
    }

    public static final <T> void collectFlow(View view, Flow flow, Consumer<T> consumer, CoroutineContext coroutineContext) {
        collectFlow$default(view, flow, consumer, coroutineContext, null, 16, null);
    }

    public static final <A, B, C, R> Flow combineFlows(Flow flow, Flow flow2, Flow flow3, Function3 function3) {
        return kotlinx.coroutines.flow.FlowKt.combine(flow, flow2, flow3, new JavaAdapterKt$combineFlows$2(function3));
    }

    public static final <T> void collectFlow(View view, Flow flow, Consumer<T> consumer, CoroutineContext coroutineContext, Lifecycle.State state) {
        RepeatWhenAttachedKt.repeatWhenAttached(view, coroutineContext, new JavaAdapterKt$collectFlow$1(state, flow, consumer, null));
    }

    public static /* synthetic */ Job collectFlow$default(Lifecycle lifecycle, Flow flow, Consumer consumer, Lifecycle.State state, int i, Object obj) {
        if ((i & 8) != 0) {
            state = Lifecycle.State.CREATED;
        }
        return collectFlow(lifecycle, flow, consumer, state);
    }

    public static final <T> Job collectFlow(Lifecycle lifecycle, Flow flow, Consumer<T> consumer, Lifecycle.State state) {
        return BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycle), null, null, new JavaAdapterKt$collectFlow$2(lifecycle, state, flow, consumer, null), 3);
    }
}
