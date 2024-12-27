package com.android.systemui.shade;

import android.view.MotionEvent;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.domain.resolver.SceneResolver;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.shared.model.TransitionKeys;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback$$ExternalSyntheticLambda1;
import dagger.Lazy;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

public final class ShadeControllerSceneImpl extends BaseShadeControllerImpl {
    public final CoroutineDispatcher mainDispatcher;
    public final NotificationStackScrollLayout notificationStackScrollLayout;
    public final SceneInteractor sceneInteractor;
    public final CoroutineScope scope;
    public final ShadeInteractor shadeInteractor;
    public final VibratorHelper vibratorHelper;

    /* renamed from: com.android.systemui.shade.ShadeControllerSceneImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShadeControllerSceneImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlow isAnyExpanded = ((ShadeInteractorImpl) ShadeControllerSceneImpl.this.shadeInteractor).baseShadeInteractor.isAnyExpanded();
                final ShadeControllerSceneImpl shadeControllerSceneImpl = ShadeControllerSceneImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shade.ShadeControllerSceneImpl.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        if (!((Boolean) obj2).booleanValue()) {
                            ShadeControllerSceneImpl.this.runPostCollapseActions();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (isAnyExpanded.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    public ShadeControllerSceneImpl(CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, ShadeInteractor shadeInteractor, SceneInteractor sceneInteractor, NotificationStackScrollLayout notificationStackScrollLayout, VibratorHelper vibratorHelper, CommandQueue commandQueue, StatusBarKeyguardViewManager statusBarKeyguardViewManager, NotificationShadeWindowController notificationShadeWindowController, Lazy lazy) {
        super(commandQueue, statusBarKeyguardViewManager, notificationShadeWindowController, lazy);
        this.mainDispatcher = coroutineDispatcher;
        this.scope = coroutineScope;
        this.shadeInteractor = shadeInteractor;
        this.sceneInteractor = sceneInteractor;
        this.notificationStackScrollLayout = notificationStackScrollLayout;
        this.vibratorHelper = vibratorHelper;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void animateCollapseShade(float f, int i, boolean z, boolean z2) {
        ShadeInteractor shadeInteractor = this.shadeInteractor;
        if (!z && !((Boolean) ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue()) {
            runPostCollapseActions();
            return;
        }
        if (((Boolean) ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue() && (i & 4) == 0) {
            ((NotificationShadeWindowControllerImpl) this.notificationShadeWindowController).setNotificationShadeFocusable(false);
            this.notificationStackScrollLayout.cancelExpandHelper();
            if (z2) {
                BuildersKt.launch$default(this.scope, null, null, new ShadeControllerSceneImpl$animateCollapseShade$1(this, null), 3);
            } else {
                SceneKey sceneKey = SceneFamilies.Home;
                TransitionKeys.INSTANCE.getClass();
                SceneInteractor.changeScene$default(this.sceneInteractor, sceneKey, "ShadeController.animateCollapseShade", TransitionKeys.SlightlyFasterShadeCollapse, 8);
            }
        }
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void cancelExpansionAndCollapseShade() {
        animateCollapseShade(0);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void closeShadeIfOpen() {
        if (((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue()) {
            this.commandQueue.animateCollapsePanels(2, true);
            ((AssistManager) this.assistManagerLazy.get()).hideAssist();
        }
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void collapseOnMainThread() {
        animateCollapseShadeForcedDelayed();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void collapseShade(boolean z) {
        if (z) {
            animateCollapseShade(0);
        } else {
            instantCollapseShade();
        }
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void collapseShadeForActivityStart() {
        if (((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue()) {
            animateCollapseShadeForcedDelayed();
        } else {
            runPostCollapseActions();
        }
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void collapseWithDuration(int i) {
        animateCollapseShade(0);
    }

    @Override // com.android.systemui.shade.BaseShadeControllerImpl
    public final void expandToNotifications() {
        SceneInteractor.changeScene$default(this.sceneInteractor, SceneFamilies.NotifShade, "ShadeController.animateExpandShade", null, 12);
    }

    @Override // com.android.systemui.shade.BaseShadeControllerImpl
    public final void expandToQs() {
        SceneInteractor.changeScene$default(this.sceneInteractor, SceneFamilies.QuickSettings, "ShadeController.animateExpandQs", null, 12);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void instantCollapseShade() {
        SceneKey sceneKey = SceneFamilies.Home;
        SceneInteractor sceneInteractor = this.sceneInteractor;
        SceneKey sceneKey2 = (SceneKey) sceneInteractor.currentScene.$$delegate_0.getValue();
        SceneResolver sceneResolver = (SceneResolver) ((Map) sceneInteractor.sceneFamilyResolvers.get()).get(sceneKey);
        if (sceneResolver != null) {
            if (sceneResolver.includesScene(sceneKey2)) {
                return;
            }
            SceneKey sceneKey3 = (SceneKey) sceneResolver.getResolvedScene().getValue();
            if (sceneKey3 != null) {
                sceneKey = sceneKey3;
            }
        }
        if (sceneInteractor.validateSceneChange(sceneKey2, sceneKey, "hide shade")) {
            sceneInteractor.logger.logSceneChangeRequested(sceneKey2, sceneKey, "hide shade", true);
            sceneInteractor.repository.dataSource.snapToScene(sceneKey);
        }
    }

    @Override // com.android.systemui.shade.ShadeController
    public final boolean isExpandedVisible() {
        return !Intrinsics.areEqual(this.sceneInteractor.currentScene.$$delegate_0.getValue(), Scenes.Gone);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final boolean isExpandingOrCollapsing() {
        return ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).isUserInteracting.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final boolean isShadeEnabled() {
        return ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).isShadeEnabled.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final boolean isShadeFullyOpen() {
        return ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).isAnyFullyExpanded.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void onStatusBarTouch(MotionEvent motionEvent) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void performHapticFeedback() {
        this.vibratorHelper.getClass();
        this.notificationStackScrollLayout.performHapticFeedback(12);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void postAnimateCollapseShade() {
        animateCollapseShade(0);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void postAnimateForceCollapseShade() {
        animateCollapseShade(1.0f, 0, true, false);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void postOnShadeExpanded(StatusBarRemoteInputCallback$$ExternalSyntheticLambda1 statusBarRemoteInputCallback$$ExternalSyntheticLambda1) {
        BuildersKt.launch$default(this.scope, null, null, new ShadeControllerSceneImpl$postOnShadeExpanded$1(this, statusBarRemoteInputCallback$$ExternalSyntheticLambda1, null), 3);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void setVisibilityListener(CentralSurfacesImpl.AnonymousClass4 anonymousClass4) {
        BuildersKt.launch$default(this.scope, null, null, new ShadeControllerSceneImpl$setVisibilityListener$1(this, anonymousClass4, null), 3);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void collapseShade() {
        animateCollapseShadeForcedDelayed();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void instantExpandShade() {
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void makeExpandedInvisible() {
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void makeExpandedVisible(boolean z) {
    }
}
