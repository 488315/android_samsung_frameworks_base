package com.android.systemui.lifecycle;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.util.Assert;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class RepeatWhenAttachedKt {
    public static final ViewLifecycleOwner createLifecycleOwnerAndRun(View view, CoroutineContext coroutineContext, Function3 function3) {
        ViewLifecycleOwner viewLifecycleOwner = new ViewLifecycleOwner(view);
        viewLifecycleOwner.registry.setCurrentState(Lifecycle.State.CREATED);
        View view2 = viewLifecycleOwner.view;
        view2.getViewTreeObserver().addOnWindowVisibilityChangeListener(viewLifecycleOwner.windowVisibleListener);
        view2.getViewTreeObserver().addOnWindowFocusChangeListener(viewLifecycleOwner.windowFocusListener);
        viewLifecycleOwner.updateState();
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner), coroutineContext, null, new RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1(function3, viewLifecycleOwner, view, null), 2);
        return viewLifecycleOwner;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.view.View$OnAttachStateChangeListener, com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1] */
    /* JADX WARN: Type inference failed for: r4v3, types: [T, com.android.systemui.lifecycle.ViewLifecycleOwner] */
    public static final RepeatWhenAttachedKt$repeatWhenAttached$1 repeatWhenAttached(final View view, CoroutineContext coroutineContext, final Function3 function3) {
        Assert.isMainThread();
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        MainCoroutineDispatcher mainCoroutineDispatcher = MainDispatcherLoader.dispatcher;
        mainCoroutineDispatcher.getClass();
        final CoroutineContext plus = CoroutineContext.DefaultImpls.plus(mainCoroutineDispatcher, coroutineContext);
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ?? r1 = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v2, types: [T, com.android.systemui.lifecycle.ViewLifecycleOwner] */
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view2) {
                Assert.isMainThread();
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) ref$ObjectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                ref$ObjectRef.element = RepeatWhenAttachedKt.createLifecycleOwnerAndRun(view, plus, function3);
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
        view.addOnAttachStateChangeListener(r1);
        if (view.isAttachedToWindow()) {
            ref$ObjectRef.element = createLifecycleOwnerAndRun(view, plus, function3);
        }
        return new RepeatWhenAttachedKt$repeatWhenAttached$1(ref$ObjectRef, view, r1);
    }
}
