package com.android.systemui.shade.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.scene.session.ui.composable.SaveableSession;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.Scene;
import com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel;
import com.android.systemui.shade.ui.viewmodel.ShadeUserActionsViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import kotlin.KotlinNothingValueException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class ShadeScene extends ExclusiveActivatable implements Scene {
    public final Lazy actionsViewModel$delegate;
    public final ShadeUserActionsViewModel.Factory actionsViewModelFactory;
    public final BatteryMeterViewController.Factory batteryMeterViewControllerFactory;
    public final ShadeSceneContentViewModel.Factory contentViewModelFactory;
    public final InteractionJankMonitor jankMonitor;
    public final SceneKey key = Scenes.Shade;
    public final MediaCarouselController mediaCarouselController;
    public final dagger.Lazy notificationStackScrollView;
    public final NotificationsPlaceholderViewModel.Factory notificationsPlaceholderViewModelFactory;
    public final MediaHost qqsMediaHost;
    public final MediaHost qsMediaHost;
    public final SaveableSession shadeSession;
    public final StatusBarIconController statusBarIconController;
    public final TintedIconManager.Factory tintedIconManagerFactory;
    public final ReadonlyStateFlow userActions;

    /* renamed from: com.android.systemui.shade.ui.composable.ShadeScene$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ShadeScene.this.onActivated(this);
        }
    }

    public ShadeScene(SaveableSession saveableSession, dagger.Lazy lazy, ShadeUserActionsViewModel.Factory factory, ShadeSceneContentViewModel.Factory factory2, NotificationsPlaceholderViewModel.Factory factory3, TintedIconManager.Factory factory4, BatteryMeterViewController.Factory factory5, StatusBarIconController statusBarIconController, MediaCarouselController mediaCarouselController, MediaHost mediaHost, MediaHost mediaHost2, InteractionJankMonitor interactionJankMonitor) {
        this.shadeSession = saveableSession;
        this.notificationStackScrollView = lazy;
        this.actionsViewModelFactory = factory;
        this.contentViewModelFactory = factory2;
        this.notificationsPlaceholderViewModelFactory = factory3;
        this.tintedIconManagerFactory = factory4;
        this.batteryMeterViewControllerFactory = factory5;
        this.statusBarIconController = statusBarIconController;
        this.mediaCarouselController = mediaCarouselController;
        this.qqsMediaHost = mediaHost;
        this.qsMediaHost = mediaHost2;
        this.jankMonitor = interactionJankMonitor;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.ui.composable.ShadeScene$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.actionsViewModelFactory.create();
            }
        });
        this.actionsViewModel$delegate = lazy2;
        this.userActions = ((ShadeUserActionsViewModel) lazy2.getValue()).actions;
        mediaHost.setExpansion(1.0f);
        mediaHost.setShowsOnlyActiveMedia(true);
        mediaHost.init(1);
        mediaHost2.setExpansion(1.0f);
        mediaHost2.setShowsOnlyActiveMedia(false);
        mediaHost2.init(0);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ShadeUserActionsViewModel shadeUserActionsViewModel = (ShadeUserActionsViewModel) this.actionsViewModel$delegate.getValue();
            anonymousClass1.label = 1;
            if (shadeUserActionsViewModel.activate(anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
