package com.android.systemui.qs.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneContentViewModel;
import com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel;
import com.android.systemui.scene.session.ui.composable.SaveableSession;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.Scene;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickSettingsScene extends ExclusiveActivatable implements Scene {
    public final Lazy actionsViewModel$delegate;
    public final QuickSettingsUserActionsViewModel.Factory actionsViewModelFactory;
    public final QuickSettingsSceneContentViewModel.Factory contentViewModelFactory;
    public final InteractionJankMonitor jankMonitor;
    public final SceneKey key = Scenes.QuickSettings;
    public final MediaCarouselController mediaCarouselController;
    public final MediaHost mediaHost;
    public final dagger.Lazy notificationStackScrollView;
    public final NotificationsPlaceholderViewModel.Factory notificationsPlaceholderViewModelFactory;
    public final SaveableSession shadeSession;
    public final ReadonlyStateFlow userActions;

    public QuickSettingsScene(SaveableSession saveableSession, dagger.Lazy lazy, NotificationsPlaceholderViewModel.Factory factory, QuickSettingsUserActionsViewModel.Factory factory2, QuickSettingsSceneContentViewModel.Factory factory3, MediaCarouselController mediaCarouselController, MediaHost mediaHost, InteractionJankMonitor interactionJankMonitor) {
        this.shadeSession = saveableSession;
        this.notificationStackScrollView = lazy;
        this.notificationsPlaceholderViewModelFactory = factory;
        this.actionsViewModelFactory = factory2;
        this.contentViewModelFactory = factory3;
        this.mediaCarouselController = mediaCarouselController;
        this.mediaHost = mediaHost;
        this.jankMonitor = interactionJankMonitor;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.ui.composable.QuickSettingsScene$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return QuickSettingsScene.this.actionsViewModelFactory.create();
            }
        });
        this.actionsViewModel$delegate = lazy2;
        this.userActions = ((QuickSettingsUserActionsViewModel) lazy2.getValue()).actions;
        mediaHost.setExpansion(1.0f);
        mediaHost.setShowsOnlyActiveMedia(false);
        mediaHost.init(0);
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
            boolean r0 = r5 instanceof com.android.systemui.qs.ui.composable.QuickSettingsScene$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.qs.ui.composable.QuickSettingsScene$onActivated$1 r0 = (com.android.systemui.qs.ui.composable.QuickSettingsScene$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.ui.composable.QuickSettingsScene$onActivated$1 r0 = new com.android.systemui.qs.ui.composable.QuickSettingsScene$onActivated$1
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
            com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel r4 = (com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel) r4
            r0.label = r3
            java.lang.Object r4 = r4.activate(r0)
            if (r4 != r1) goto L43
            return r1
        L43:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.composable.QuickSettingsScene.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
