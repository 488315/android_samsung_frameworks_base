package com.android.systemui.development.ui.viewmodel;

import androidx.compose.runtime.State;
import com.android.systemui.development.domain.interactor.BuildNumberInteractor;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BuildNumberViewModel extends ExclusiveActivatable {
    public final State buildNumber$delegate;
    public final BuildNumberInteractor buildNumberInteractor;
    public final BufferedChannel copyRequests;
    public final Hydrator hydrator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        BuildNumberViewModel create();
    }

    public BuildNumberViewModel(BuildNumberInteractor buildNumberInteractor) {
        this.buildNumberInteractor = buildNumberInteractor;
        Hydrator hydrator = new Hydrator("BuildNumberViewModel", null, 2, null);
        this.hydrator = hydrator;
        this.copyRequests = ChannelKt.Channel$default(0, null, null, 7);
        this.buildNumber$delegate = hydrator.hydratedStateOf(buildNumberInteractor.buildNumber, "buildNumber");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.development.ui.viewmodel.BuildNumberViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.development.ui.viewmodel.BuildNumberViewModel$onActivated$1 r0 = (com.android.systemui.development.ui.viewmodel.BuildNumberViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.development.ui.viewmodel.BuildNumberViewModel$onActivated$1 r0 = new com.android.systemui.development.ui.viewmodel.BuildNumberViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.development.ui.viewmodel.BuildNumberViewModel$onActivated$2 r5 = new com.android.systemui.development.ui.viewmodel.BuildNumberViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.development.ui.viewmodel.BuildNumberViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
