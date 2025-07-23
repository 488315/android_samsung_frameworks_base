package com.android.systemui.shade;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.TransitionKey;
import com.android.systemui.ExpandHelper;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
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
import java.util.Collection;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeControllerSceneImpl extends BaseShadeControllerImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher mainDispatcher;
    public final NotificationStackScrollLayout notificationStackScrollLayout;
    public final SceneInteractor sceneInteractor;
    public final CoroutineScope scope;
    public final ShadeInteractor shadeInteractor;
    public final VibratorHelper vibratorHelper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.shade.ShadeControllerSceneImpl$1$1$1, reason: invalid class name and collision with other inner class name */
                    final class C03031 extends SuspendLambda implements Function2 {
                        int label;
                        final /* synthetic */ ShadeControllerSceneImpl this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C03031(ShadeControllerSceneImpl shadeControllerSceneImpl, Continuation continuation) {
                            super(2, continuation);
                            this.this$0 = shadeControllerSceneImpl;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new C03031(this.this$0, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((C03031) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            this.this$0.runPostCollapseActions();
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        if (((Boolean) obj2).booleanValue()) {
                            return Unit.INSTANCE;
                        }
                        ShadeControllerSceneImpl shadeControllerSceneImpl2 = ShadeControllerSceneImpl.this;
                        Object withContext = BuildersKt.withContext(shadeControllerSceneImpl2.mainDispatcher, new C03031(shadeControllerSceneImpl2, null), continuation);
                        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
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
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7);
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
            ExpandHelper expandHelper = this.notificationStackScrollLayout.mExpandHelper;
            expandHelper.finishExpanding(0.0f, true, true);
            expandHelper.mResizedView = null;
            expandHelper.mSGD = new ScaleGestureDetector(expandHelper.mContext, expandHelper.mScaleGestureListener);
            if (z2) {
                CoroutineTracingKt.launchTraced$default(this.scope, null, null, new ShadeControllerSceneImpl$animateCollapseShade$1(this, null), 7);
            } else {
                TransitionKeys.INSTANCE.getClass();
                ((ShadeInteractorImpl) shadeInteractor).collapseEitherShade("ShadeController.animateCollapseShade", TransitionKeys.SlightlyFasterShadeCollapse);
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
        ((ShadeInteractorImpl) this.shadeInteractor).expandNotificationsShade("ShadeController.animateExpandShade");
    }

    @Override // com.android.systemui.shade.BaseShadeControllerImpl
    public final void expandToQs() {
        ((ShadeInteractorImpl) this.shadeInteractor).expandQuickSettingsShade("ShadeController.animateExpandQs");
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void instantCollapseShade() {
        TransitionKeys.INSTANCE.getClass();
        TransitionKey transitionKey = TransitionKeys.Instant;
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) this.shadeInteractor;
        shadeInteractorImpl.collapseNotificationsShade("ShadeControllerSceneImpl.instantCollapseShade", transitionKey);
        shadeInteractorImpl.collapseQuickSettingsShade("ShadeControllerSceneImpl.instantCollapseShade", transitionKey, true);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final boolean isExpandedVisible() {
        SceneInteractor sceneInteractor = this.sceneInteractor;
        return (Intrinsics.areEqual(sceneInteractor.currentScene.getValue(), Scenes.Gone) && ((Collection) sceneInteractor.currentOverlays.getValue()).isEmpty()) ? false : true;
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
    public final void performHapticFeedback(int i) {
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
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new ShadeControllerSceneImpl$postOnShadeExpanded$1(this, statusBarRemoteInputCallback$$ExternalSyntheticLambda1, null), 7);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void setVisibilityListener(CentralSurfacesImpl.AnonymousClass4 anonymousClass4) {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new ShadeControllerSceneImpl$setVisibilityListener$1(this, anonymousClass4, null), 7);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void collapseShade() {
        animateCollapseShadeForcedDelayed();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void makeExpandedVisible(boolean z) {
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void instantExpandShade() {
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void makeExpandedInvisible() {
    }
}
