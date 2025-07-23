package com.android.systemui.scene.ui.viewmodel;

import com.android.systemui.lifecycle.ExclusiveActivatable;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class UserActionsViewModel extends ExclusiveActivatable {
    public final StateFlowImpl _actions;
    public final ReadonlyStateFlow actions;

    public UserActionsViewModel() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(MapsKt__MapsKt.emptyMap());
        this._actions = MutableStateFlow;
        this.actions = FlowKt.asStateFlow(MutableStateFlow);
    }

    public abstract Object hydrateActions(UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation);

    /* JADX WARN: Code restructure failed: missing block: B:22:0x005b, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$onActivated$1 r0 = (com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$onActivated$1 r0 = new com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$onActivated$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L40
            if (r2 == r4) goto L38
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2e:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.scene.ui.viewmodel.UserActionsViewModel r5 = (com.android.systemui.scene.ui.viewmodel.UserActionsViewModel) r5
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L36
            goto L5e
        L36:
            r6 = move-exception
            goto L64
        L38:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.scene.ui.viewmodel.UserActionsViewModel r5 = (com.android.systemui.scene.ui.viewmodel.UserActionsViewModel) r5
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L36
            goto L53
        L40:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0 r6 = new com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L36
            r6.<init>(r5)     // Catch: java.lang.Throwable -> L36
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L36
            r0.label = r4     // Catch: java.lang.Throwable -> L36
            java.lang.Object r6 = r5.hydrateActions(r6, r0)     // Catch: java.lang.Throwable -> L36
            if (r6 != r1) goto L53
            goto L5d
        L53:
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L36
            r0.label = r3     // Catch: java.lang.Throwable -> L36
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)     // Catch: java.lang.Throwable -> L36
            if (r6 != r1) goto L5e
        L5d:
            return r1
        L5e:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L36
            r6.<init>()     // Catch: java.lang.Throwable -> L36
            throw r6     // Catch: java.lang.Throwable -> L36
        L64:
            kotlinx.coroutines.flow.StateFlowImpl r5 = r5._actions
            java.util.Map r0 = kotlin.collections.MapsKt__MapsKt.emptyMap()
            r5.setValue(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.ui.viewmodel.UserActionsViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
