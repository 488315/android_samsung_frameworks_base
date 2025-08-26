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
import kotlin.KotlinNothingValueException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

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

    /* renamed from: com.android.systemui.qs.ui.composable.QuickSettingsScene$onActivated$1, reason: invalid class name */
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
            return QuickSettingsScene.this.onActivated(this);
        }
    }

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
                return this.f$0.actionsViewModelFactory.create();
            }
        });
        this.actionsViewModel$delegate = lazy2;
        this.userActions = ((QuickSettingsUserActionsViewModel) lazy2.getValue()).actions;
        mediaHost.setExpansion(1.0f);
        mediaHost.setShowsOnlyActiveMedia(false);
        mediaHost.init(0);
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
            QuickSettingsUserActionsViewModel quickSettingsUserActionsViewModel = (QuickSettingsUserActionsViewModel) this.actionsViewModel$delegate.getValue();
            anonymousClass1.label = 1;
            if (quickSettingsUserActionsViewModel.activate(anonymousClass1) == coroutineSingletons) {
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
