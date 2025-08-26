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
import com.android.systemui.shade.ShadeController;
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
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class ShadeControllerSceneImpl extends BaseShadeControllerImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
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
                StateFlow stateFlowIsAnyExpanded = ((ShadeInteractorImpl) ShadeControllerSceneImpl.this.shadeInteractor).baseShadeInteractor.isAnyExpanded();
                final ShadeControllerSceneImpl shadeControllerSceneImpl = ShadeControllerSceneImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shade.ShadeControllerSceneImpl.1.1

                    /* renamed from: com.android.systemui.shade.ShadeControllerSceneImpl$1$1$1, reason: invalid class name and collision with other inner class name */
                    final class C04681 extends SuspendLambda implements Function2 {
                        int label;
                        final /* synthetic */ ShadeControllerSceneImpl this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C04681(ShadeControllerSceneImpl shadeControllerSceneImpl, Continuation continuation) {
                            super(2, continuation);
                            this.this$0 = shadeControllerSceneImpl;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new C04681(this.this$0, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((C04681) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                        if (((Boolean) obj2).booleanValue()) {
                            return Unit.INSTANCE;
                        }
                        ShadeControllerSceneImpl shadeControllerSceneImpl2 = shadeControllerSceneImpl;
                        Object objWithContext = BuildersKt.withContext(shadeControllerSceneImpl2.mainDispatcher, new C04681(shadeControllerSceneImpl2, null), continuation);
                        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (stateFlowIsAnyExpanded.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.shade.ShadeControllerSceneImpl$animateCollapseShade$1, reason: invalid class name and case insensitive filesystem */
    final class C10371 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.shade.ShadeControllerSceneImpl$animateCollapseShade$1$1, reason: invalid class name and collision with other inner class name */
        final class C04691 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ ShadeControllerSceneImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04691(ShadeControllerSceneImpl shadeControllerSceneImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = shadeControllerSceneImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04691(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04691) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                ShadeControllerSceneImpl shadeControllerSceneImpl = this.this$0;
                int i = ShadeControllerSceneImpl.$r8$clinit;
                shadeControllerSceneImpl.getClass();
                TransitionKeys.INSTANCE.getClass();
                ((ShadeInteractorImpl) shadeControllerSceneImpl.shadeInteractor).collapseEitherShade("ShadeController.animateCollapseShade", TransitionKeys.SlightlyFasterShadeCollapse);
                return Unit.INSTANCE;
            }
        }

        public C10371(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShadeControllerSceneImpl.this.new C10371(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10371) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x003a, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r1, r3, r5) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(125L, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ShadeControllerSceneImpl shadeControllerSceneImpl = ShadeControllerSceneImpl.this;
            CoroutineDispatcher coroutineDispatcher = shadeControllerSceneImpl.mainDispatcher;
            C04691 c04691 = new C04691(shadeControllerSceneImpl, null);
            this.label = 2;
        }
    }

    /* renamed from: com.android.systemui.shade.ShadeControllerSceneImpl$postOnShadeExpanded$1, reason: invalid class name and case insensitive filesystem */
    final class C10381 extends SuspendLambda implements Function2 {
        final /* synthetic */ Runnable $action;
        int label;

        /* renamed from: com.android.systemui.shade.ShadeControllerSceneImpl$postOnShadeExpanded$1$1, reason: invalid class name and collision with other inner class name */
        final class C04701 extends SuspendLambda implements Function2 {
            /* synthetic */ boolean Z$0;
            int label;

            public C04701(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C04701 c04701 = new C04701(continuation);
                c04701.Z$0 = ((Boolean) obj).booleanValue();
                return c04701;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return ((C04701) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Boolean.valueOf(this.Z$0);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10381(Runnable runnable, Continuation continuation) {
            super(2, continuation);
            this.$action = runnable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShadeControllerSceneImpl.this.new C10381(this.$action, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10381) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ReadonlyStateFlow readonlyStateFlow = ((ShadeInteractorImpl) ShadeControllerSceneImpl.this.shadeInteractor).isAnyFullyExpanded;
                C04701 c04701 = new C04701(null);
                this.label = 1;
                if (FlowKt.first(readonlyStateFlow, c04701, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.$action.run();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.shade.ShadeControllerSceneImpl$setVisibilityListener$1, reason: invalid class name and case insensitive filesystem */
    final class C10391 extends SuspendLambda implements Function2 {
        final /* synthetic */ ShadeController.ShadeVisibilityListener $listener;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10391(ShadeController.ShadeVisibilityListener shadeVisibilityListener, Continuation continuation) {
            super(2, continuation);
            this.$listener = shadeVisibilityListener;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShadeControllerSceneImpl.this.new C10391(this.$listener, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10391) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ShadeControllerSceneImpl shadeControllerSceneImpl = ShadeControllerSceneImpl.this;
                ReadonlyStateFlow readonlyStateFlow = shadeControllerSceneImpl.sceneInteractor.isVisible;
                final ShadeController.ShadeVisibilityListener shadeVisibilityListener = this.$listener;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shade.ShadeControllerSceneImpl.setVisibilityListener.1.1

                    /* renamed from: com.android.systemui.shade.ShadeControllerSceneImpl$setVisibilityListener$1$1$1, reason: invalid class name and collision with other inner class name */
                    final class C04721 extends SuspendLambda implements Function2 {
                        final /* synthetic */ boolean $isVisible;
                        final /* synthetic */ ShadeController.ShadeVisibilityListener $listener;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C04721(ShadeController.ShadeVisibilityListener shadeVisibilityListener, boolean z, Continuation continuation) {
                            super(2, continuation);
                            this.$listener = shadeVisibilityListener;
                            this.$isVisible = z;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new C04721(this.$listener, this.$isVisible, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((C04721) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            ((CentralSurfacesImpl.AnonymousClass4) this.$listener).expandedVisibleChanged(this.$isVisible);
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                        Object objWithContext = BuildersKt.withContext(shadeControllerSceneImpl.mainDispatcher, new C04721(shadeVisibilityListener, ((Boolean) obj2).booleanValue(), null), continuation);
                        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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
                CoroutineTracingKt.launchTraced$default(this.scope, null, null, new C10371(null), 7);
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
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new C10381(statusBarRemoteInputCallback$$ExternalSyntheticLambda1, null), 7);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void setVisibilityListener(CentralSurfacesImpl.AnonymousClass4 anonymousClass4) {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new C10391(anonymousClass4, null), 7);
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
