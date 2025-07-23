package com.android.systemui.scene.ui.viewmodel;

import android.view.View;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneContainerHapticsViewModel extends ExclusiveActivatable {
    public final Flow isShadePullHapticsRequired;
    public final View view;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        SceneContainerHapticsViewModel create(View view);
    }

    public SceneContainerHapticsViewModel(View view, SceneInteractor sceneInteractor, ShadeInteractor shadeInteractor, MSDLPlayer mSDLPlayer) {
        this.view = view;
        this.isShadePullHapticsRequired = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((ShadeInteractorImpl) shadeInteractor).isUserInteracting, sceneInteractor.transitionState, new SceneContainerHapticsViewModel$isShadePullHapticsRequired$1(this, null)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0059, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005b, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0050, code lost:
    
        if (r5 == r1) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel$onActivated$1 r0 = (com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel$onActivated$1 r0 = new com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel$onActivated$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
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
            kotlin.ResultKt.throwOnFailure(r6)
            goto L5c
        L32:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L53
        L36:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.label = r4
            com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel$playShadePullHaptics$$inlined$filter$1 r6 = new com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel$playShadePullHaptics$$inlined$filter$1
            kotlinx.coroutines.flow.Flow r2 = r5.isShadePullHapticsRequired
            r6.<init>()
            com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel$playShadePullHaptics$3 r2 = new com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel$playShadePullHaptics$3
            r2.<init>()
            java.lang.Object r5 = r6.collect(r2, r0)
            if (r5 != r1) goto L4e
            goto L50
        L4e:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
        L50:
            if (r5 != r1) goto L53
            goto L5b
        L53:
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)
            if (r5 != r1) goto L5c
        L5b:
            return r1
        L5c:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
            r5.<init>()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
