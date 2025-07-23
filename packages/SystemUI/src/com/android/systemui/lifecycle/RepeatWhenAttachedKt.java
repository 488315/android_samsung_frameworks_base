package com.android.systemui.lifecycle;

import android.view.View;
import androidx.lifecycle.LifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceDataThreadLocal;
import com.android.systemui.util.Assert;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class RepeatWhenAttachedKt {
    public static final CoroutineContext MAIN_DISPATCHER_SINGLETON;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WindowLifecycleState.values().length];
            try {
                iArr[WindowLifecycleState.ATTACHED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WindowLifecycleState.VISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[WindowLifecycleState.FOCUSED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        HandlerContext handlerContext = MainDispatcherLoader.dispatcher;
        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        handlerContext.getClass();
        MAIN_DISPATCHER_SINGLETON = CoroutineContext.DefaultImpls.plus(handlerContext, emptyCoroutineContext);
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0054, code lost:
    
        if (repeatWhenWindowHasFocus(r6, r8, r0) == r1) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0069, code lost:
    
        if (repeatWhenWindowIsVisible(r6, r8, r0) == r1) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0078, code lost:
    
        if (repeatWhenAttachedToWindow(r6, r8, r0) == r1) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.coroutines.intrinsics.CoroutineSingletons repeatOnWindowLifecycle(android.view.View r6, com.android.systemui.lifecycle.WindowLifecycleState r7, kotlin.jvm.functions.Function2 r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            boolean r0 = r9 instanceof com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatOnWindowLifecycle$1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatOnWindowLifecycle$1 r0 = (com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatOnWindowLifecycle$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatOnWindowLifecycle$1 r0 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatOnWindowLifecycle$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 3
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L3d
            if (r2 == r5) goto L39
            if (r2 == r4) goto L35
            if (r2 == r3) goto L31
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L31:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L57
        L35:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L6c
        L39:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L7b
        L3d:
            kotlin.ResultKt.throwOnFailure(r9)
            int[] r9 = com.android.systemui.lifecycle.RepeatWhenAttachedKt.WhenMappings.$EnumSwitchMapping$0
            int r7 = r7.ordinal()
            r7 = r9[r7]
            if (r7 == r5) goto L72
            if (r7 == r4) goto L63
            if (r7 != r3) goto L5d
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = repeatWhenWindowHasFocus(r6, r8, r0)
            if (r6 != r1) goto L57
            goto L7a
        L57:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException
            r6.<init>()
            throw r6
        L5d:
            kotlin.NoWhenBranchMatchedException r6 = new kotlin.NoWhenBranchMatchedException
            r6.<init>()
            throw r6
        L63:
            r0.label = r4
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = repeatWhenWindowIsVisible(r6, r8, r0)
            if (r6 != r1) goto L6c
            goto L7a
        L6c:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException
            r6.<init>()
            throw r6
        L72:
            r0.label = r5
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = repeatWhenAttachedToWindow(r6, r8, r0)
            if (r6 != r1) goto L7b
        L7a:
            return r1
        L7b:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.lifecycle.RepeatWhenAttachedKt.repeatOnWindowLifecycle(android.view.View, com.android.systemui.lifecycle.WindowLifecycleState, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.view.View$OnAttachStateChangeListener, com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1] */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, com.android.systemui.lifecycle.ViewLifecycleOwner] */
    public static final RepeatWhenAttachedKt$repeatWhenAttached$1 repeatWhenAttached(final View view, CoroutineContext coroutineContext, final Function3 function3) {
        Assert.isMainThread();
        final CoroutineContext plus = MAIN_DISPATCHER_SINGLETON.plus(coroutineContext);
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ?? r1 = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v1, types: [T, com.android.systemui.lifecycle.ViewLifecycleOwner] */
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view2) {
                Assert.isMainThread();
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) ref$ObjectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                View view3 = view;
                CoroutineContext coroutineContext2 = plus;
                Function3 function32 = function3;
                CoroutineContext coroutineContext3 = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
                ?? viewLifecycleOwner2 = new ViewLifecycleOwner(view3);
                viewLifecycleOwner2.onCreate();
                CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(viewLifecycleOwner2.registry), coroutineContext2, null, new RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1(function32, viewLifecycleOwner2, view3, null), 5);
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
        view.addOnAttachStateChangeListener(r1);
        if (view.isAttachedToWindow()) {
            ?? viewLifecycleOwner = new ViewLifecycleOwner(view);
            viewLifecycleOwner.onCreate();
            CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(viewLifecycleOwner.registry), plus, null, new RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1(function3, viewLifecycleOwner, view, null), 5);
            ref$ObjectRef.element = viewLifecycleOwner;
        }
        return new RepeatWhenAttachedKt$repeatWhenAttached$1(ref$ObjectRef, view, r1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005a, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005c, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0051, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.collectLatest(r5, r7, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.coroutines.intrinsics.CoroutineSingletons repeatWhenAttachedToWindow(android.view.View r5, kotlin.jvm.functions.Function2 r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            boolean r0 = r7 instanceof com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttachedToWindow$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttachedToWindow$1 r0 = (com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttachedToWindow$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttachedToWindow$1 r0 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttachedToWindow$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2e:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5d
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L54
        L36:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.util.Assert.isMainThread()
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$isAttached$1 r7 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$isAttached$1
            r2 = 0
            r7.<init>(r5, r2)
            kotlinx.coroutines.flow.Flow r5 = com.android.systemui.utils.coroutines.flow.FlowConflatedKt.conflatedCallbackFlow(r7)
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttachedToWindow$2 r7 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttachedToWindow$2
            r7.<init>(r6, r2)
            r0.label = r4
            java.lang.Object r5 = kotlinx.coroutines.flow.FlowKt.collectLatest(r5, r7, r0)
            if (r5 != r1) goto L54
            goto L5c
        L54:
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)
            if (r5 != r1) goto L5d
        L5c:
            return r1
        L5d:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
            r5.<init>()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.lifecycle.RepeatWhenAttachedKt.repeatWhenAttachedToWindow(android.view.View, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0068, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x006a, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005f, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.collectLatest(r6, r8, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.coroutines.intrinsics.CoroutineSingletons repeatWhenWindowHasFocus(android.view.View r6, kotlin.jvm.functions.Function2 r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            boolean r0 = r8 instanceof com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowHasFocus$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowHasFocus$1 r0 = (com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowHasFocus$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowHasFocus$1 r0 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowHasFocus$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2e:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6b
        L32:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L62
        L36:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.systemui.util.Assert.isMainThread()
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$isAttached$1 r8 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$isAttached$1
            r2 = 0
            r8.<init>(r6, r2)
            kotlinx.coroutines.flow.Flow r8 = com.android.systemui.utils.coroutines.flow.FlowConflatedKt.conflatedCallbackFlow(r8)
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$special$$inlined$map$1 r5 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$special$$inlined$map$1
            r5.<init>(r8, r6)
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$isWindowFocused$1 r8 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$isWindowFocused$1
            r8.<init>(r6, r2)
            kotlinx.coroutines.flow.Flow r6 = com.android.systemui.utils.coroutines.flow.LatestConflatedKt.flatMapLatestConflated(r5, r8)
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowHasFocus$2 r8 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowHasFocus$2
            r8.<init>(r7, r2)
            r0.label = r4
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.collectLatest(r6, r8, r0)
            if (r6 != r1) goto L62
            goto L6a
        L62:
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)
            if (r6 != r1) goto L6b
        L6a:
            return r1
        L6b:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.lifecycle.RepeatWhenAttachedKt.repeatWhenWindowHasFocus(android.view.View, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0068, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x006a, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005f, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.collectLatest(r6, r8, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.coroutines.intrinsics.CoroutineSingletons repeatWhenWindowIsVisible(android.view.View r6, kotlin.jvm.functions.Function2 r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            boolean r0 = r8 instanceof com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowIsVisible$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowIsVisible$1 r0 = (com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowIsVisible$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowIsVisible$1 r0 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowIsVisible$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2e:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6b
        L32:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L62
        L36:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.systemui.util.Assert.isMainThread()
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$isAttached$1 r8 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$isAttached$1
            r2 = 0
            r8.<init>(r6, r2)
            kotlinx.coroutines.flow.Flow r8 = com.android.systemui.utils.coroutines.flow.FlowConflatedKt.conflatedCallbackFlow(r8)
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$special$$inlined$map$1 r5 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$special$$inlined$map$1
            r5.<init>(r8, r6)
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$isWindowVisible$1 r8 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$isWindowVisible$1
            r8.<init>(r6, r2)
            kotlinx.coroutines.flow.Flow r6 = com.android.systemui.utils.coroutines.flow.LatestConflatedKt.flatMapLatestConflated(r5, r8)
            com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowIsVisible$2 r8 = new com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowIsVisible$2
            r8.<init>(r7, r2)
            r0.label = r4
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.collectLatest(r6, r8, r0)
            if (r6 != r1) goto L62
            goto L6a
        L62:
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)
            if (r6 != r1) goto L6b
        L6a:
            return r1
        L6b:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.lifecycle.RepeatWhenAttachedKt.repeatWhenWindowIsVisible(android.view.View, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }
}
