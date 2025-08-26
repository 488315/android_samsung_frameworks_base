package com.android.systemui.ambient.touch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.ambient.touch.BouncerSwipeTouchHandler;
import com.android.systemui.ambient.touch.TouchHandler;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.ambient.touch.dagger.BouncerSwipeModule$$ExternalSyntheticLambda0;
import com.android.systemui.ambient.touch.scrim.ScrimController;
import com.android.systemui.ambient.touch.scrim.ScrimManager;
import com.android.systemui.ambient.touch.scrim.ScrimManager$$ExternalSyntheticLambda1;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.wm.shell.animation.FlingAnimationUtils;
import java.util.Optional;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes.dex */
public final class BouncerSwipeTouchHandler implements TouchHandler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final float bouncerZoneScreenPercentage;
    public Boolean capture;
    public final Optional centralSurfaces;
    public final CommunalViewModel communalViewModel;
    public float currentExpansion;
    public ScrimController currentScrimController;
    public boolean expanded;
    public final FlingAnimationUtils flingAnimationUtils;
    public final FlingAnimationUtils flingAnimationUtilsClosing;
    public boolean isKeyguardScreenRotationAllowed;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardStateController keyguardStateController;
    public final float minBouncerZoneScreenPercentage;
    public final BouncerSwipeTouchHandler$onGestureListener$1 onGestureListener;
    public final ScrimManager scrimManager;
    public final ShadeRepository shadeRepository;
    public boolean touchAvailable;
    public TouchHandler.TouchSession touchSession;
    public final UiEventLogger uiEventLogger;
    public final ValueAnimatorCreator valueAnimatorCreator;
    public VelocityTracker velocityTracker;
    public final VelocityTrackerFactory velocityTrackerFactory;
    public final Optional windowRootViewProvider;
    public final BouncerSwipeTouchHandler$scrimManagerCallback$1 scrimManagerCallback = new BouncerSwipeTouchHandler$scrimManagerCallback$1(this);
    public final Lazy windowRootView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (WindowRootView) ((Provider) this.f$0.windowRootViewProvider.get()).get();
        }
    });

    /* renamed from: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BouncerSwipeTouchHandler.this.new AnonymousClass1(continuation);
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
                final BouncerSwipeTouchHandler bouncerSwipeTouchHandler = BouncerSwipeTouchHandler.this;
                Flow flow = bouncerSwipeTouchHandler.communalViewModel.glanceableTouchAvailable;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        bouncerSwipeTouchHandler.onGlanceableTouchAvailable(((Boolean) obj2).booleanValue());
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class DreamEvent implements UiEventLogger.UiEventEnum {
        public static final /* synthetic */ DreamEvent[] $VALUES;
        public static final DreamEvent DREAM_BOUNCER_FULLY_VISIBLE;
        public static final DreamEvent DREAM_SWIPED;
        private final int mId;

        static {
            DreamEvent dreamEvent = new DreamEvent("DREAM_SWIPED", 0, 988);
            DREAM_SWIPED = dreamEvent;
            DreamEvent dreamEvent2 = new DreamEvent("DREAM_BOUNCER_FULLY_VISIBLE", 1, 1056);
            DREAM_BOUNCER_FULLY_VISIBLE = dreamEvent2;
            DreamEvent[] dreamEventArr = {dreamEvent, dreamEvent2};
            $VALUES = dreamEventArr;
            EnumEntriesKt.enumEntries(dreamEventArr);
        }

        private DreamEvent(String str, int i, int i2) {
            this.mId = i2;
        }

        public static DreamEvent valueOf(String str) {
            return (DreamEvent) Enum.valueOf(DreamEvent.class, str);
        }

        public static DreamEvent[] values() {
            return (DreamEvent[]) $VALUES.clone();
        }

        public final int getId() {
            return this.mId;
        }
    }

    public interface ValueAnimatorCreator {
    }

    public interface VelocityTrackerFactory {
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$onGestureListener$1] */
    public BouncerSwipeTouchHandler(CoroutineScope coroutineScope, ScrimManager scrimManager, Optional<CentralSurfaces> optional, NotificationShadeWindowController notificationShadeWindowController, ValueAnimatorCreator valueAnimatorCreator, VelocityTrackerFactory velocityTrackerFactory, CommunalViewModel communalViewModel, FlingAnimationUtils flingAnimationUtils, FlingAnimationUtils flingAnimationUtils2, float f, float f2, UiEventLogger uiEventLogger, ActivityStarter activityStarter, KeyguardInteractor keyguardInteractor, SceneInteractor sceneInteractor, ShadeRepository shadeRepository, Optional<Provider> optional2, KeyguardStateController keyguardStateController, final CommunalSettingsInteractor communalSettingsInteractor) {
        this.scrimManager = scrimManager;
        this.centralSurfaces = optional;
        this.valueAnimatorCreator = valueAnimatorCreator;
        this.velocityTrackerFactory = velocityTrackerFactory;
        this.communalViewModel = communalViewModel;
        this.flingAnimationUtils = flingAnimationUtils;
        this.flingAnimationUtilsClosing = flingAnimationUtils2;
        this.bouncerZoneScreenPercentage = f;
        this.minBouncerZoneScreenPercentage = f2;
        this.uiEventLogger = uiEventLogger;
        this.activityStarter = activityStarter;
        this.keyguardInteractor = keyguardInteractor;
        this.shadeRepository = shadeRepository;
        this.windowRootViewProvider = optional2;
        this.keyguardStateController = keyguardStateController;
        this.onGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$onGestureListener$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f3, float f4) {
                ScrimController scrimController;
                boolean z = ((WindowRootView) this.this$0.windowRootView$delegate.getValue()).getResources().getConfiguration().orientation == 2;
                BouncerSwipeTouchHandler bouncerSwipeTouchHandler = this.this$0;
                if (bouncerSwipeTouchHandler.capture == null) {
                    bouncerSwipeTouchHandler.capture = Boolean.valueOf(Math.abs((double) f4) > Math.abs((double) f3) && f4 > 0.0f && this.this$0.touchAvailable);
                    if (Intrinsics.areEqual(this.this$0.capture, Boolean.TRUE)) {
                        BouncerSwipeTouchHandler bouncerSwipeTouchHandler2 = this.this$0;
                        bouncerSwipeTouchHandler2.expanded = false;
                        if ((bouncerSwipeTouchHandler2.isKeyguardScreenRotationAllowed || !z) && (scrimController = bouncerSwipeTouchHandler2.currentScrimController) != null) {
                            scrimController.show$2();
                        }
                    }
                }
                if (!Intrinsics.areEqual(this.this$0.capture, Boolean.TRUE)) {
                    return false;
                }
                if (this.this$0.centralSurfaces.isPresent() && motionEvent != null) {
                    final BouncerSwipeTouchHandler bouncerSwipeTouchHandler3 = this.this$0;
                    CommunalSettingsInteractor communalSettingsInteractor2 = communalSettingsInteractor;
                    if (motionEvent.getY() >= motionEvent2.getY()) {
                        if (motionEvent.getY() > motionEvent2.getY() && ((Boolean) bouncerSwipeTouchHandler3.keyguardInteractor.isKeyguardDismissible.getValue()).booleanValue()) {
                            bouncerSwipeTouchHandler3.activityStarter.executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$onGestureListener$1$onScroll$2$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ((CentralSurfacesImpl) ((CentralSurfaces) bouncerSwipeTouchHandler3.centralSurfaces.get())).awakenDreams();
                                }
                            }, null, true, true, false);
                            return true;
                        }
                        if (bouncerSwipeTouchHandler3.touchSession != null) {
                            double dAbs = Math.abs(motionEvent.getY() - motionEvent2.getY());
                            bouncerSwipeTouchHandler3.touchSession.getClass();
                            float fHeight = (float) (dAbs / ((TouchMonitor.TouchSessionImpl) r12).mBounds.height());
                            communalSettingsInteractor2.isV2FlagEnabled();
                            if (bouncerSwipeTouchHandler3.touchSession != null) {
                                float f5 = 1 - fHeight;
                                bouncerSwipeTouchHandler3.currentExpansion = f5;
                                ShadeExpansionChangeEvent shadeExpansionChangeEvent = new ShadeExpansionChangeEvent(f5, bouncerSwipeTouchHandler3.expanded, true);
                                ScrimController scrimController2 = bouncerSwipeTouchHandler3.currentScrimController;
                                if (scrimController2 != null) {
                                    scrimController2.expand(shadeExpansionChangeEvent);
                                }
                            }
                        }
                    }
                }
                return true;
            }
        };
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7);
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void getTouchInitiationRegion(Rect rect, Region region, Rect rect2) {
        int iWidth = rect.width();
        int iHeight = rect.height();
        float f = iHeight;
        float f2 = 1;
        int iRound = Math.round((f2 - this.minBouncerZoneScreenPercentage) * f);
        Rect rect3 = new Rect(0, Math.round((f2 - this.bouncerZoneScreenPercentage) * f), iWidth, iHeight);
        region.op(rect, Region.Op.UNION);
        if (rect2 != null) {
            region.op(rect2, Region.Op.DIFFERENCE);
        }
        if (rect2 != null) {
            rect3.top = (int) Math.max(rect3.top, (int) Math.min(Math.max(0.0d, rect2.bottom), iRound));
        }
        region.union(rect3);
    }

    public final void onGlanceableTouchAvailable(boolean z) {
        this.touchAvailable = z;
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void onSessionStart(TouchHandler.TouchSession touchSession) {
        ((BouncerSwipeModule$$ExternalSyntheticLambda0) this.velocityTrackerFactory).getClass();
        VelocityTracker velocityTrackerObtain = VelocityTracker.obtain();
        this.velocityTracker = velocityTrackerObtain;
        this.touchSession = touchSession;
        if (velocityTrackerObtain != null) {
            velocityTrackerObtain.clear();
        }
        ScrimManager scrimManager = this.scrimManager;
        scrimManager.mExecutor.execute(new ScrimManager$$ExternalSyntheticLambda1(scrimManager, this.scrimManagerCallback, 0));
        this.currentScrimController = scrimManager.mCurrentController;
        this.isKeyguardScreenRotationAllowed = DeviceState.shouldEnableKeyguardScreenRotation(((KeyguardStateControllerImpl) this.keyguardStateController).mContext);
        ShadeRepositoryImpl shadeRepositoryImpl = (ShadeRepositoryImpl) this.shadeRepository;
        shadeRepositoryImpl._legacyShadeTracking.updateState(null, Boolean.TRUE);
        TouchMonitor.TouchSessionImpl touchSessionImpl = (TouchMonitor.TouchSessionImpl) touchSession;
        touchSessionImpl.mCallbacks.add(new TouchHandler.TouchSession.Callback() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler.onSessionStart.2
            @Override // com.android.systemui.ambient.touch.TouchHandler.TouchSession.Callback
            public final void onRemoved() {
                BouncerSwipeTouchHandler bouncerSwipeTouchHandler = BouncerSwipeTouchHandler.this;
                VelocityTracker velocityTracker = bouncerSwipeTouchHandler.velocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                }
                bouncerSwipeTouchHandler.velocityTracker = null;
                ScrimManager scrimManager2 = bouncerSwipeTouchHandler.scrimManager;
                scrimManager2.mExecutor.execute(new ScrimManager$$ExternalSyntheticLambda1(scrimManager2, bouncerSwipeTouchHandler.scrimManagerCallback, 1));
                bouncerSwipeTouchHandler.capture = null;
                bouncerSwipeTouchHandler.touchSession = null;
                ShadeRepositoryImpl shadeRepositoryImpl2 = (ShadeRepositoryImpl) bouncerSwipeTouchHandler.shadeRepository;
                shadeRepositoryImpl2._legacyShadeTracking.updateState(null, Boolean.FALSE);
            }
        });
        touchSessionImpl.mGestureListeners.add(this.onGestureListener);
        touchSessionImpl.mEventListeners.add(new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler.onSessionStart.3
            @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
            public final void onInputEvent(InputEvent inputEvent) {
                TouchHandler.TouchSession touchSession2;
                ValueAnimator valueAnimator;
                int i = BouncerSwipeTouchHandler.$r8$clinit;
                final BouncerSwipeTouchHandler bouncerSwipeTouchHandler = BouncerSwipeTouchHandler.this;
                bouncerSwipeTouchHandler.getClass();
                if (!(inputEvent instanceof MotionEvent)) {
                    Log.e("BouncerSwipeTouchHandler", "non MotionEvent received:" + inputEvent);
                    return;
                }
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                int action = motionEvent.getAction();
                if (action != 1 && action != 3) {
                    VelocityTracker velocityTracker = bouncerSwipeTouchHandler.velocityTracker;
                    velocityTracker.getClass();
                    velocityTracker.addMovement(motionEvent);
                    return;
                }
                if (Intrinsics.areEqual(bouncerSwipeTouchHandler.capture, Boolean.TRUE)) {
                    bouncerSwipeTouchHandler.communalViewModel.onResetTouchState();
                }
                TouchHandler.TouchSession touchSession3 = bouncerSwipeTouchHandler.touchSession;
                if (touchSession3 != null) {
                    ((TouchMonitor.TouchSessionImpl) touchSession3).pop();
                }
                Boolean bool = bouncerSwipeTouchHandler.capture;
                if (bool == null || !bool.booleanValue()) {
                    return;
                }
                VelocityTracker velocityTracker2 = bouncerSwipeTouchHandler.velocityTracker;
                velocityTracker2.getClass();
                velocityTracker2.computeCurrentVelocity(1000);
                VelocityTracker velocityTracker3 = bouncerSwipeTouchHandler.velocityTracker;
                velocityTracker3.getClass();
                float yVelocity = velocityTracker3.getYVelocity();
                VelocityTracker velocityTracker4 = bouncerSwipeTouchHandler.velocityTracker;
                velocityTracker4.getClass();
                boolean z = Math.abs((double) ((float) Math.hypot((double) velocityTracker4.getXVelocity(), (double) yVelocity))) >= ((double) bouncerSwipeTouchHandler.flingAnimationUtils.mMinVelocityPxPerSecond) ? yVelocity > 0.0f : bouncerSwipeTouchHandler.currentExpansion > 0.5f;
                bouncerSwipeTouchHandler.expanded = !z;
                float f = !z ? 0.0f : 1.0f;
                if (f == 0.0f) {
                    bouncerSwipeTouchHandler.uiEventLogger.log(DreamEvent.DREAM_SWIPED);
                }
                if (!bouncerSwipeTouchHandler.centralSurfaces.isPresent() || ((Boolean) bouncerSwipeTouchHandler.keyguardInteractor.isKeyguardDismissible.getValue()).booleanValue() || (touchSession2 = bouncerSwipeTouchHandler.touchSession) == null) {
                    return;
                }
                float fHeight = ((TouchMonitor.TouchSessionImpl) touchSession2).mBounds.height();
                float f2 = bouncerSwipeTouchHandler.currentExpansion;
                float f3 = fHeight * f2;
                float f4 = fHeight * f;
                ((BouncerSwipeModule$$ExternalSyntheticLambda0) bouncerSwipeTouchHandler.valueAnimatorCreator).getClass();
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f2, f);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$createExpansionAnimator$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                        BouncerSwipeTouchHandler bouncerSwipeTouchHandler2 = bouncerSwipeTouchHandler;
                        bouncerSwipeTouchHandler2.currentExpansion = fFloatValue;
                        ShadeExpansionChangeEvent shadeExpansionChangeEvent = new ShadeExpansionChangeEvent(fFloatValue, bouncerSwipeTouchHandler2.expanded, true);
                        ScrimController scrimController = bouncerSwipeTouchHandler2.currentScrimController;
                        if (scrimController != null) {
                            scrimController.expand(shadeExpansionChangeEvent);
                        }
                    }
                });
                if (f == 0.0f) {
                    valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$createExpansionAnimator$2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            bouncerSwipeTouchHandler.uiEventLogger.log(BouncerSwipeTouchHandler.DreamEvent.DREAM_BOUNCER_FULLY_VISIBLE);
                        }
                    });
                }
                if (f == 1.0f) {
                    valueAnimator = valueAnimatorOfFloat;
                    bouncerSwipeTouchHandler.flingAnimationUtilsClosing.apply(valueAnimator, f3, f4, yVelocity, fHeight);
                } else {
                    valueAnimator = valueAnimatorOfFloat;
                    bouncerSwipeTouchHandler.flingAnimationUtils.apply(valueAnimator, f3, f4, yVelocity, fHeight);
                }
                valueAnimator.start();
            }
        });
    }
}
