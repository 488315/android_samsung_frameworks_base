package com.android.systemui.kairos;

import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda4;
import com.android.systemui.kairos.internal.StateScopeImpl;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class BuildScopeKt {
    public static final Events asyncEvent(BuildScope buildScope, Function1 function1) {
        BuildScopeKt$asyncEvent$1 buildScopeKt$asyncEvent$1 = new BuildScopeKt$asyncEvent$1(function1, null);
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        buildScopeImpl.getClass();
        Events buildEvents$default = BuildScopeImpl.buildEvents$default(buildScopeImpl, new BuildScopeImpl$$ExternalSyntheticLambda4(buildScopeImpl, 1), buildScopeKt$asyncEvent$1);
        BuildScope.DefaultImpls.observe$default(buildScopeImpl, buildEvents$default, null, 3);
        return buildEvents$default;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.coroutines.intrinsics.CoroutineSingletons awaitClose(kotlin.jvm.functions.Function0 r4, kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            boolean r0 = r5 instanceof com.android.systemui.kairos.BuildScopeKt$awaitClose$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.kairos.BuildScopeKt$awaitClose$1 r0 = (com.android.systemui.kairos.BuildScopeKt$awaitClose$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.kairos.BuildScopeKt$awaitClose$1 r0 = new com.android.systemui.kairos.BuildScopeKt$awaitClose$1
            r0.<init>(r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            java.lang.Object r4 = r0.L$0
            kotlin.jvm.functions.Function0 r4 = (kotlin.jvm.functions.Function0) r4
            kotlin.ResultKt.throwOnFailure(r5)     // Catch: java.lang.Throwable -> L33
            goto L43
        L33:
            r5 = move-exception
            goto L49
        L35:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.L$0 = r4     // Catch: java.lang.Throwable -> L33
            r0.label = r3     // Catch: java.lang.Throwable -> L33
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)     // Catch: java.lang.Throwable -> L33
            if (r5 != r1) goto L43
            return r1
        L43:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L33
            r5.<init>()     // Catch: java.lang.Throwable -> L33
            throw r5     // Catch: java.lang.Throwable -> L33
        L49:
            r4.invoke()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.BuildScopeKt.awaitClose(kotlin.jvm.functions.Function0, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }

    public static Job effect$default(BuildScope buildScope, Function1 function1) {
        return launchScope(buildScope, new BuildScopeKt$$ExternalSyntheticLambda0(2, EmptyCoroutineContext.INSTANCE, function1));
    }

    public static final CompletableDeferredImpl launchEffect(BuildScope buildScope, Function2 function2) {
        final CompletableDeferredImpl CompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
        Job effect$default = effect$default(buildScope, new BuildScopeKt$$ExternalSyntheticLambda0(CompletableDeferred$default, function2));
        CompletableDeferred$default.invokeOnCompletion(new BuildScopeKt$$ExternalSyntheticLambda0(1, effect$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.kairos.BuildScopeKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                CompletableDeferredImpl.this.cancel(null);
                return Unit.INSTANCE;
            }
        }), effect$default));
        return CompletableDeferred$default;
    }

    public static final Job launchScope(BuildScope buildScope, Function1 function1) {
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        ContextScope childScope$default = UtilKt.childScope$default(buildScopeImpl.coroutineScope);
        BuildScopeImpl buildScopeImpl2 = new BuildScopeImpl(new StateScopeImpl(buildScopeImpl.stateScope.evalScope, LazyKt__LazyJVMKt.lazy(new BuildScopeImpl$$ExternalSyntheticLambda0(buildScopeImpl, childScope$default))), childScope$default);
        return (Job) new Pair(new DeferredValue(buildScopeImpl.deferAsync(new BuildScopeImpl$$ExternalSyntheticLambda0(function1, buildScopeImpl2, 0))), JobKt.getJob(buildScopeImpl2.coroutineScope.getCoroutineContext())).getSecond();
    }

    public static final StateInit rebuildOn(BuildScope buildScope, Events events, Function1 function1) {
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        Pair applyLatestSpec = buildScopeImpl.applyLatestSpec(EventsKt.map(events, new BuildScopeKt$$ExternalSyntheticLambda7(function1, 1)), function1);
        return buildScopeImpl.stateScope.holdStateDeferred((Events) applyLatestSpec.component1(), (DeferredValue) applyLatestSpec.component2());
    }
}
