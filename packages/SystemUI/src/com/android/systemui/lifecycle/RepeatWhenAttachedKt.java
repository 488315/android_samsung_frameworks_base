package com.android.systemui.lifecycle;

import android.view.View;
import androidx.lifecycle.LifecycleKt;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceDataThreadLocal;
import com.android.systemui.Flags;
import com.android.systemui.util.Assert;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class RepeatWhenAttachedKt {
    public static final CoroutineContext MAIN_DISPATCHER_SINGLETON;

    static {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        MainCoroutineDispatcher mainCoroutineDispatcher = MainDispatcherLoader.dispatcher;
        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
        Flags.FEATURE_FLAGS.getClass();
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        mainCoroutineDispatcher.getClass();
        MAIN_DISPATCHER_SINGLETON = CoroutineContext.DefaultImpls.plus(mainCoroutineDispatcher, emptyCoroutineContext);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [android.view.View$OnAttachStateChangeListener, com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1] */
    /* JADX WARN: Type inference failed for: r9v0, types: [T, com.android.systemui.lifecycle.ViewLifecycleOwner] */
    public static final RepeatWhenAttachedKt$repeatWhenAttached$1 repeatWhenAttached(final View view, CoroutineContext coroutineContext, final Function3 function3) {
        Assert.isMainThread();
        final CoroutineContext plus = MAIN_DISPATCHER_SINGLETON.plus(coroutineContext);
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        final String str = "repeatWhenAttached";
        ?? r7 = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r9v2, types: [T, com.android.systemui.lifecycle.ViewLifecycleOwner] */
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view2) {
                Assert.isMainThread();
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) ref$ObjectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                String str2 = str;
                View view3 = view;
                CoroutineContext coroutineContext2 = plus;
                Function3 function32 = function3;
                CoroutineContext coroutineContext3 = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
                ?? viewLifecycleOwner2 = new ViewLifecycleOwner(view3);
                viewLifecycleOwner2.onCreate();
                BuildersKt.launch$default(LifecycleKt.getCoroutineScope(viewLifecycleOwner2.getLifecycle()), coroutineContext2, null, new RepeatWhenAttachedKt$createLifecycleOwnerAndRun$lambda$1$$inlined$launch$1(str2, null, function32, viewLifecycleOwner2, view3), 2);
                ref$ObjectRef2.element = viewLifecycleOwner2;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view2) {
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) ref$ObjectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                ref$ObjectRef.element = null;
            }
        };
        view.addOnAttachStateChangeListener(r7);
        if (view.isAttachedToWindow()) {
            ?? viewLifecycleOwner = new ViewLifecycleOwner(view);
            viewLifecycleOwner.onCreate();
            BuildersKt.launch$default(LifecycleKt.getCoroutineScope(viewLifecycleOwner.getLifecycle()), plus, null, new RepeatWhenAttachedKt$createLifecycleOwnerAndRun$lambda$1$$inlined$launch$1("repeatWhenAttached", null, function3, viewLifecycleOwner, view), 2);
            ref$ObjectRef.element = viewLifecycleOwner;
        }
        return new RepeatWhenAttachedKt$repeatWhenAttached$1(ref$ObjectRef, view, r7);
    }
}
