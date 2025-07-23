package com.android.systemui.keyguard.ui.viewmodel;

import androidx.compose.runtime.State;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardMediaViewModel extends ExclusiveActivatable {
    public final Hydrator hydrator;
    public final State isMediaVisible$delegate;
    public final State isShadeLayoutWide$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    public KeyguardMediaViewModel(MediaCarouselInteractor mediaCarouselInteractor, KeyguardInteractor keyguardInteractor, ShadeModeInteractor shadeModeInteractor) {
        Hydrator hydrator = new Hydrator("KeyguardMediaViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        this.isMediaVisible$delegate = hydrator.hydratedStateOf("isMediaVisible", Boolean.valueOf(!((Boolean) keyguardInteractor.isDozing.$$delegate_0.getValue()).booleanValue() && ((Boolean) mediaCarouselInteractor.hasActiveMediaOrRecommendation.$$delegate_0.getValue()).booleanValue()), LatestConflatedKt.flatMapLatestConflated(keyguardInteractor.isDozing, new KeyguardMediaViewModel$isMediaVisible$2(mediaCarouselInteractor, null)));
        this.isShadeLayoutWide$delegate = hydrator.hydratedStateOf(((ShadeModeInteractorImpl) shadeModeInteractor).isShadeLayoutWide, "isShadeLayoutWide");
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
            boolean r0 = r5 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardMediaViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.keyguard.ui.viewmodel.KeyguardMediaViewModel$onActivated$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardMediaViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.ui.viewmodel.KeyguardMediaViewModel$onActivated$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardMediaViewModel$onActivated$1
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
            goto L3d
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.label = r3
            com.android.systemui.lifecycle.Hydrator r4 = r4.hydrator
            java.lang.Object r4 = r4.activate(r0)
            if (r4 != r1) goto L3d
            return r1
        L3d:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardMediaViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
