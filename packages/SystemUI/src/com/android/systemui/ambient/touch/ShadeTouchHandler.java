package com.android.systemui.ambient.touch;

import android.app.DreamManager;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.ambient.touch.TouchHandler;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.util.Optional;
import javax.inject.Provider;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes.dex */
public final class ShadeTouchHandler implements TouchHandler {
    public Boolean capture;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final CommunalViewModel communalViewModel;
    public final DreamManager dreamManager;
    public final int initiationHeight;
    public final ShadeViewController shadeViewController;
    public final Optional surfaces;
    public boolean touchAvailable;
    public final Optional windowRootViewProvider;

    /* renamed from: com.android.systemui.ambient.touch.ShadeTouchHandler$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShadeTouchHandler.this.new AnonymousClass1(continuation);
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
                final ShadeTouchHandler shadeTouchHandler = ShadeTouchHandler.this;
                Flow flow = shadeTouchHandler.communalViewModel.glanceableTouchAvailable;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.ambient.touch.ShadeTouchHandler.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        shadeTouchHandler.onGlanceableTouchAvailable(((Boolean) obj2).booleanValue());
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

    public ShadeTouchHandler(CoroutineScope coroutineScope, Optional<CentralSurfaces> optional, ShadeViewController shadeViewController, DreamManager dreamManager, CommunalViewModel communalViewModel, CommunalSettingsInteractor communalSettingsInteractor, SceneInteractor sceneInteractor, Optional<Provider> optional2, int i) {
        this.surfaces = optional;
        this.shadeViewController = shadeViewController;
        this.dreamManager = dreamManager;
        this.communalViewModel = communalViewModel;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.windowRootViewProvider = optional2;
        this.initiationHeight = i;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.ambient.touch.ShadeTouchHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (WindowRootView) ((Provider) this.f$0.windowRootViewProvider.get()).get();
            }
        });
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7);
    }

    public static final void access$sendTouchEvent(ShadeTouchHandler shadeTouchHandler, MotionEvent motionEvent) {
        if (!shadeTouchHandler.communalSettingsInteractor.isCommunalFlagEnabled() || shadeTouchHandler.dreamManager.isDreaming()) {
            shadeTouchHandler.shadeViewController.handleExternalTouch(motionEvent);
            return;
        }
        NotificationShadeWindowViewController notificationShadeWindowViewController = ((CentralSurfacesImpl) ((CentralSurfaces) shadeTouchHandler.surfaces.get())).getNotificationShadeWindowViewController();
        notificationShadeWindowViewController.getClass();
        if (motionEvent.getActionMasked() == 0) {
            notificationShadeWindowViewController.mExternalTouchIntercepted = false;
        }
        NotificationShadeWindowView notificationShadeWindowView = notificationShadeWindowViewController.mView;
        if (notificationShadeWindowView.dispatchTouchEvent(motionEvent)) {
            if (!notificationShadeWindowViewController.mExternalTouchIntercepted) {
                notificationShadeWindowViewController.mExternalTouchIntercepted = notificationShadeWindowView.onInterceptTouchEvent(motionEvent);
            }
            if (notificationShadeWindowViewController.mExternalTouchIntercepted) {
                notificationShadeWindowView.onTouchEvent(motionEvent);
            }
        }
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void getTouchInitiationRegion(Rect rect, Region region, Rect rect2) {
        Region.Op op = Region.Op.UNION;
        region.op(rect, op);
        if (rect2 != null) {
            region.op(rect2, Region.Op.DIFFERENCE);
        }
        Rect rect3 = new Rect(rect);
        rect3.inset(0, 0, 0, rect3.height() - this.initiationHeight);
        region.op(rect3, op);
    }

    public final void onGlanceableTouchAvailable(boolean z) {
        this.touchAvailable = z;
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void onSessionStart(TouchHandler.TouchSession touchSession) {
        if (this.surfaces.isEmpty()) {
            ((TouchMonitor.TouchSessionImpl) touchSession).pop();
            return;
        }
        final TouchMonitor.TouchSessionImpl touchSessionImpl = (TouchMonitor.TouchSessionImpl) touchSession;
        touchSessionImpl.mCallbacks.add(new TouchHandler.TouchSession.Callback() { // from class: com.android.systemui.ambient.touch.ShadeTouchHandler.onSessionStart.1
            @Override // com.android.systemui.ambient.touch.TouchHandler.TouchSession.Callback
            public final void onRemoved() {
                ShadeTouchHandler.this.capture = null;
            }
        });
        touchSessionImpl.mEventListeners.add(new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.ambient.touch.ShadeTouchHandler.onSessionStart.2
            @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
            public final void onInputEvent(InputEvent inputEvent) {
                if (inputEvent instanceof MotionEvent) {
                    ShadeTouchHandler shadeTouchHandler = ShadeTouchHandler.this;
                    Boolean bool = shadeTouchHandler.capture;
                    Boolean bool2 = Boolean.TRUE;
                    if (Intrinsics.areEqual(bool, bool2)) {
                        ShadeTouchHandler.access$sendTouchEvent(shadeTouchHandler, (MotionEvent) inputEvent);
                    }
                    MotionEvent motionEvent = (MotionEvent) inputEvent;
                    if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                        if (Intrinsics.areEqual(shadeTouchHandler.capture, bool2)) {
                            shadeTouchHandler.communalViewModel.onResetTouchState();
                        }
                        ((TouchMonitor.TouchSessionImpl) touchSessionImpl).pop();
                    }
                }
            }
        });
        touchSessionImpl.mGestureListeners.add(new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.ambient.touch.ShadeTouchHandler.onSessionStart.3
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                return Intrinsics.areEqual(ShadeTouchHandler.this.capture, Boolean.TRUE);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                ShadeTouchHandler shadeTouchHandler = ShadeTouchHandler.this;
                if (shadeTouchHandler.capture == null) {
                    shadeTouchHandler.capture = Boolean.valueOf(Math.abs((double) f2) > Math.abs((double) f) && f2 < 0.0f && ShadeTouchHandler.this.touchAvailable);
                    if (Intrinsics.areEqual(ShadeTouchHandler.this.capture, Boolean.TRUE)) {
                        if (motionEvent != null) {
                            ShadeTouchHandler.access$sendTouchEvent(ShadeTouchHandler.this, motionEvent);
                        }
                        ShadeTouchHandler.access$sendTouchEvent(ShadeTouchHandler.this, motionEvent2);
                    }
                }
                return Intrinsics.areEqual(ShadeTouchHandler.this.capture, Boolean.TRUE);
            }
        });
    }
}
