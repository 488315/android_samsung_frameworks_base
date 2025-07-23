package com.android.systemui.notifications.ui.composable;

import com.android.compose.animation.scene.OverlayKey;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.keyguard.ui.composable.section.DefaultClockSection;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayActionsViewModel;
import com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayContentViewModel;
import com.android.systemui.scene.session.ui.composable.SaveableSession;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.ui.composable.Overlay;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NotificationsShadeOverlay implements Overlay {
    public final Lazy actionsViewModel$delegate;
    public final NotificationsShadeOverlayActionsViewModel.Factory actionsViewModelFactory;
    public final DefaultClockSection clockSection;
    public final NotificationsShadeOverlayContentViewModel.Factory contentViewModelFactory;
    public final InteractionJankMonitor jankMonitor;
    public final OverlayKey key = Overlays.NotificationsShade;
    public final KeyguardClockViewModel keyguardClockViewModel;
    public final MediaCarouselController mediaCarouselController;
    public final dagger.Lazy mediaHost;
    public final SaveableSession shadeSession;
    public final dagger.Lazy stackScrollView;
    public final ReadonlyStateFlow userActions;

    public NotificationsShadeOverlay(NotificationsShadeOverlayActionsViewModel.Factory factory, NotificationsShadeOverlayContentViewModel.Factory factory2, SaveableSession saveableSession, dagger.Lazy lazy, DefaultClockSection defaultClockSection, KeyguardClockViewModel keyguardClockViewModel, MediaCarouselController mediaCarouselController, dagger.Lazy lazy2, InteractionJankMonitor interactionJankMonitor) {
        this.actionsViewModelFactory = factory;
        this.contentViewModelFactory = factory2;
        this.shadeSession = saveableSession;
        this.stackScrollView = lazy;
        this.clockSection = defaultClockSection;
        this.keyguardClockViewModel = keyguardClockViewModel;
        this.mediaCarouselController = mediaCarouselController;
        this.mediaHost = lazy2;
        this.jankMonitor = interactionJankMonitor;
        Lazy lazy3 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.notifications.ui.composable.NotificationsShadeOverlay$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return NotificationsShadeOverlay.this.actionsViewModelFactory.create();
            }
        });
        this.actionsViewModel$delegate = lazy3;
        this.userActions = ((NotificationsShadeOverlayActionsViewModel) lazy3.getValue()).actions;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.Activatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object activate(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.notifications.ui.composable.NotificationsShadeOverlay$activate$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.notifications.ui.composable.NotificationsShadeOverlay$activate$1 r0 = (com.android.systemui.notifications.ui.composable.NotificationsShadeOverlay$activate$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.notifications.ui.composable.NotificationsShadeOverlay$activate$1 r0 = new com.android.systemui.notifications.ui.composable.NotificationsShadeOverlay$activate$1
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
            goto L43
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlin.Lazy r4 = r4.actionsViewModel$delegate
            java.lang.Object r4 = r4.getValue()
            com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayActionsViewModel r4 = (com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayActionsViewModel) r4
            r0.label = r3
            java.lang.Object r4 = r4.activate(r0)
            if (r4 != r1) goto L43
            return r1
        L43:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.notifications.ui.composable.NotificationsShadeOverlay.activate(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
