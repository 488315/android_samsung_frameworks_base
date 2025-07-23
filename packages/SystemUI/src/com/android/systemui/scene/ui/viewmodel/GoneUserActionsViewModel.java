package com.android.systemui.scene.ui.viewmodel;

import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GoneUserActionsViewModel extends UserActionsViewModel {
    public final ShadeModeInteractor shadeModeInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        GoneUserActionsViewModel create();
    }

    public GoneUserActionsViewModel(ShadeModeInteractor shadeModeInteractor) {
        this.shadeModeInteractor = shadeModeInteractor;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.scene.ui.viewmodel.UserActionsViewModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object hydrateActions(final com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0 r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.scene.ui.viewmodel.GoneUserActionsViewModel$hydrateActions$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.scene.ui.viewmodel.GoneUserActionsViewModel$hydrateActions$1 r0 = (com.android.systemui.scene.ui.viewmodel.GoneUserActionsViewModel$hydrateActions$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.scene.ui.viewmodel.GoneUserActionsViewModel$hydrateActions$1 r0 = new com.android.systemui.scene.ui.viewmodel.GoneUserActionsViewModel$hydrateActions$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
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
            kotlin.ResultKt.throwOnFailure(r6)
            goto L48
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.shade.domain.interactor.ShadeModeInteractor r4 = r4.shadeModeInteractor
            com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl r4 = (com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl) r4
            kotlinx.coroutines.flow.ReadonlyStateFlow r4 = r4.shadeMode
            com.android.systemui.scene.ui.viewmodel.GoneUserActionsViewModel$hydrateActions$2 r6 = new com.android.systemui.scene.ui.viewmodel.GoneUserActionsViewModel$hydrateActions$2
            r6.<init>()
            r0.label = r3
            kotlinx.coroutines.flow.StateFlow r4 = r4.$$delegate_0
            java.lang.Object r4 = r4.collect(r6, r0)
            if (r4 != r1) goto L48
            return r1
        L48:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.ui.viewmodel.GoneUserActionsViewModel.hydrateActions(com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
