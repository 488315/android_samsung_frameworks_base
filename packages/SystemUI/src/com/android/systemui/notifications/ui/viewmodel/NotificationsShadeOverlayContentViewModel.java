package com.android.systemui.notifications.ui.viewmodel;

import androidx.compose.runtime.State;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel;
import com.android.systemui.statusbar.disableflags.domain.interactor.DisableFlagsInteractor;
import com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NotificationsShadeOverlayContentViewModel extends ExclusiveActivatable {
    public final Hydrator hydrator;
    public final NotificationsPlaceholderViewModel.Factory notificationsPlaceholderViewModelFactory;
    public final ShadeHeaderViewModel.Factory shadeHeaderViewModelFactory;
    public final ShadeInteractor shadeInteractor;
    public final State showMedia$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    public NotificationsShadeOverlayContentViewModel(ShadeHeaderViewModel.Factory factory, NotificationsPlaceholderViewModel.Factory factory2, SceneInteractor sceneInteractor, ShadeInteractor shadeInteractor, DisableFlagsInteractor disableFlagsInteractor, MediaCarouselInteractor mediaCarouselInteractor) {
        this.shadeHeaderViewModelFactory = factory;
        this.notificationsPlaceholderViewModelFactory = factory2;
        this.shadeInteractor = shadeInteractor;
        Hydrator hydrator = new Hydrator("NotificationsShadeOverlayContentViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        this.showMedia$delegate = hydrator.hydratedStateOf("showMedia", Boolean.valueOf(((DisableFlagsModel) disableFlagsInteractor.disableFlags.$$delegate_0.getValue()).isQuickSettingsEnabled() && ((Boolean) mediaCarouselInteractor.hasActiveMediaOrRecommendation.$$delegate_0.getValue()).booleanValue()), LatestConflatedKt.flatMapLatestConflated(disableFlagsInteractor.disableFlags, new NotificationsShadeOverlayContentViewModel$showMedia$2(mediaCarouselInteractor, null)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x004e, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0050, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0045, code lost:
    
        if (kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r6, r0) == r1) goto L21;
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
            boolean r0 = r6 instanceof com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayContentViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayContentViewModel$onActivated$1 r0 = (com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayContentViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayContentViewModel$onActivated$1 r0 = new com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayContentViewModel$onActivated$1
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
            goto L51
        L32:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L48
        L36:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayContentViewModel$onActivated$2 r6 = new com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayContentViewModel$onActivated$2
            r2 = 0
            r6.<init>(r5, r2)
            r0.label = r4
            java.lang.Object r5 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r6, r0)
            if (r5 != r1) goto L48
            goto L50
        L48:
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)
            if (r5 != r1) goto L51
        L50:
            return r1
        L51:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
            r5.<init>()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayContentViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
