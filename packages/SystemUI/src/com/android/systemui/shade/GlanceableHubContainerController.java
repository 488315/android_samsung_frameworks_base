package com.android.systemui.shade;

import android.content.Context;
import android.graphics.Rect;
import android.os.PowerManager;
import android.util.ArraySet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.keyguard.UserActivityNotifier;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.ambient.touch.dagger.AmbientTouchComponent;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.ui.compose.CommunalContent;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.communal.util.CommunalColors;
import com.android.systemui.communal.util.UserTouchActivityNotifier;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.media.controls.ui.controller.KeyguardMediaController;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.SceneDataSourceDelegator;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.animation.UniqueObjectHostView;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.kotlin.Quad;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class GlanceableHubContainerController implements LifecycleOwner {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AmbientTouchComponent.Factory ambientTouchComponentFactory;
    public boolean anyBouncerShowing;
    public final CommunalColors communalColors;
    public View communalContainerView;
    public CommunalWrapper communalContainerWrapper;
    public final CommunalContent communalContent;
    public final CommunalInteractor communalInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final CommunalViewModel communalViewModel;
    public final SceneDataSourceDelegator dataSourceDelegator;
    public boolean hubShowing;
    public boolean inEditModeTransition;
    public boolean isDreaming;
    public boolean isTrackingHubTouch;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardMediaController keyguardMediaController;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final LockscreenSmartspaceController lockscreenSmartspaceController;
    public final Logger logger;
    public final NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public boolean onLockscreen;
    public boolean shadeConsumingTouches;
    public final ShadeInteractor shadeInteractor;
    public boolean shadeShowing;
    public boolean shadeShowingAndConsumingTouches;
    public boolean swipeToHubEnabled;
    public TouchMonitor touchMonitor;
    public boolean touchTakenByKeyguardGesture;
    public boolean userNotInteractiveAtShadeFullyExpanded;
    public final UserTouchActivityNotifier userTouchActivityNotifier;
    public final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    public final GlanceableHubContainerController$touchLifecycleLogger$1 touchLifecycleLogger = new LifecycleEventObserver() { // from class: com.android.systemui.shade.GlanceableHubContainerController$touchLifecycleLogger$1
        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            GlanceableHubContainerController glanceableHubContainerController = this.this$0;
            Logger logger = glanceableHubContainerController.logger;
            GlanceableHubContainerController$$ExternalSyntheticLambda0 glanceableHubContainerController$$ExternalSyntheticLambda0 = new GlanceableHubContainerController$$ExternalSyntheticLambda0(5);
            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, glanceableHubContainerController$$ExternalSyntheticLambda0, null);
            logMessageObtain.setStr1(event.toString());
            logMessageObtain.setBool1(glanceableHubContainerController.hubShowing);
            logMessageObtain.setBool2(glanceableHubContainerController.shadeShowingAndConsumingTouches);
            logMessageObtain.setBool3(glanceableHubContainerController.anyBouncerShowing);
            logMessageObtain.setBool4(glanceableHubContainerController.inEditModeTransition);
            logger.getBuffer().commit(logMessageObtain);
        }
    };

    public final class CommunalWrapper extends FrameLayout {
        public final CommunalSettingsInteractor communalSettingsInteractor;
        public final Set consumers;

        public CommunalWrapper(Context context, CommunalSettingsInteractor communalSettingsInteractor) {
            super(context);
            this.communalSettingsInteractor = communalSettingsInteractor;
            this.consumers = new ArraySet();
        }

        @Override // android.view.View
        public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
            this.communalSettingsInteractor.isV2FlagEnabled();
            return super.onApplyWindowInsets(windowInsets);
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public final void requestDisallowInterceptTouchEvent(boolean z) {
            Iterator it = this.consumers.iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(Boolean.valueOf(z));
            }
            super.requestDisallowInterceptTouchEvent(z);
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.shade.GlanceableHubContainerController$touchLifecycleLogger$1] */
    public GlanceableHubContainerController(CommunalInteractor communalInteractor, CommunalSettingsInteractor communalSettingsInteractor, CommunalViewModel communalViewModel, KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, ShadeInteractor shadeInteractor, PowerManager powerManager, CommunalColors communalColors, AmbientTouchComponent.Factory factory, CommunalContent communalContent, SceneDataSourceDelegator sceneDataSourceDelegator, NotificationStackScrollLayoutController notificationStackScrollLayoutController, KeyguardMediaController keyguardMediaController, LockscreenSmartspaceController lockscreenSmartspaceController, UserTouchActivityNotifier userTouchActivityNotifier, LogBuffer logBuffer, UserActivityNotifier userActivityNotifier) {
        this.communalInteractor = communalInteractor;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.communalViewModel = communalViewModel;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.shadeInteractor = shadeInteractor;
        this.communalColors = communalColors;
        this.ambientTouchComponentFactory = factory;
        this.communalContent = communalContent;
        this.dataSourceDelegator = sceneDataSourceDelegator;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.keyguardMediaController = keyguardMediaController;
        this.lockscreenSmartspaceController = lockscreenSmartspaceController;
        this.userTouchActivityNotifier = userTouchActivityNotifier;
        this.logger = new Logger(logBuffer, "GlanceableHubContainer");
    }

    public static final void access$updateTouchHandlingState(GlanceableHubContainerController glanceableHubContainerController) {
        boolean z = glanceableHubContainerController.hubShowing;
        LifecycleRegistry lifecycleRegistry = glanceableHubContainerController.lifecycleRegistry;
        if (z && !glanceableHubContainerController.shadeShowingAndConsumingTouches && !glanceableHubContainerController.anyBouncerShowing && !glanceableHubContainerController.inEditModeTransition) {
            lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
            return;
        }
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
        View view = glanceableHubContainerController.communalContainerView;
        view.getClass();
        view.setSystemGestureExclusionRects(EmptyList.INSTANCE);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.lifecycleRegistry;
    }

    public final View initView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(View view) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        if (this.communalContainerView != null) {
            throw new RuntimeException("Communal view has already been initialized");
        }
        TouchMonitor touchMonitor = this.touchMonitor;
        if (touchMonitor != null) {
            touchMonitor.destroy();
            this.touchMonitor = null;
        }
        TouchMonitor touchMonitor2 = ((DaggerReferenceGlobalRootComponent.AmbientTouchComponentImpl) this.ambientTouchComponentFactory.create(this, new HashSet(), "GlanceableHubContainer")).getTouchMonitor();
        touchMonitor2.init();
        this.touchMonitor = touchMonitor2;
        LifecycleRegistry lifecycleRegistry = this.lifecycleRegistry;
        lifecycleRegistry.addObserver(this.touchLifecycleLogger);
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        this.communalContainerView = view;
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        KeyguardInteractor keyguardInteractor = this.keyguardInteractor;
        JavaAdapterKt.collectFlow$default(view, booleanFlowOperators.anyOf(keyguardInteractor.primaryBouncerShowing, keyguardInteractor.alternateBouncerShowing), new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$initView$4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Boolean bool = (Boolean) obj;
                this.this$0.anyBouncerShowing = bool.booleanValue();
                GlanceableHubContainerController glanceableHubContainerController = this.this$0;
                if (glanceableHubContainerController.hubShowing) {
                    Logger logger = glanceableHubContainerController.logger;
                    GlanceableHubContainerController$$ExternalSyntheticLambda0 glanceableHubContainerController$$ExternalSyntheticLambda0 = new GlanceableHubContainerController$$ExternalSyntheticLambda0(4);
                    LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, glanceableHubContainerController$$ExternalSyntheticLambda0, null);
                    logMessageObtain.setBool1(bool.booleanValue());
                    logger.getBuffer().commit(logMessageObtain);
                }
                GlanceableHubContainerController.access$updateTouchHandlingState(this.this$0);
            }
        }, null, null, 24, null);
        KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
        KeyguardTransitionInteractor keyguardTransitionInteractor = this.keyguardTransitionInteractor;
        JavaAdapterKt.collectFlow$default(view, keyguardTransitionInteractor.isFinishedIn$1(keyguardState), new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$initView$5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.this$0.onLockscreen = ((Boolean) obj).booleanValue();
            }
        }, null, null, 24, null);
        CommunalInteractor communalInteractor = this.communalInteractor;
        JavaAdapterKt.collectFlow$default(view, communalInteractor.isCommunalVisible, new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$initView$6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.this$0.hubShowing = ((Boolean) obj).booleanValue();
                GlanceableHubContainerController.access$updateTouchHandlingState(this.this$0);
            }
        }, null, null, 24, null);
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState2 = KeyguardState.GONE;
        KeyguardState keyguardState3 = KeyguardState.GLANCEABLE_HUB;
        companion.getClass();
        JavaAdapterKt.collectFlow$default(view, booleanFlowOperators.anyOf(communalInteractor.editActivityShowing, keyguardTransitionInteractor.isInTransition(new Edge.StateToState(keyguardState2, keyguardState3), null)), new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$initView$7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.this$0.inEditModeTransition = ((Boolean) obj).booleanValue();
                GlanceableHubContainerController.access$updateTouchHandlingState(this.this$0);
            }
        }, null, null, 24, null);
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) this.shadeInteractor;
        JavaAdapterKt.collectFlow$default(view, FlowKt.combine(shadeInteractorImpl.isAnyFullyExpanded, shadeInteractorImpl.isUserInteracting, shadeInteractorImpl.isShadeFullyCollapsed, shadeInteractorImpl.baseShadeInteractor.isQsExpanded(), GlanceableHubContainerController$initView$10.INSTANCE), new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$initView$11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Quad quad = (Quad) obj;
                boolean zBooleanValue = ((Boolean) quad.component1()).booleanValue();
                boolean zBooleanValue2 = ((Boolean) quad.component2()).booleanValue();
                boolean zBooleanValue3 = ((Boolean) quad.component3()).booleanValue();
                boolean zBooleanValue4 = ((Boolean) quad.component4()).booleanValue();
                GlanceableHubContainerController glanceableHubContainerController = this.this$0;
                glanceableHubContainerController.shadeConsumingTouches = zBooleanValue2;
                boolean z = true;
                glanceableHubContainerController.shadeShowing = zBooleanValue4 || !zBooleanValue3;
                boolean z2 = zBooleanValue && !zBooleanValue2;
                boolean z3 = !zBooleanValue3 && (glanceableHubContainerController.userNotInteractiveAtShadeFullyExpanded || z2);
                glanceableHubContainerController.userNotInteractiveAtShadeFullyExpanded = z3;
                if (!z3 && !z2) {
                    z = false;
                }
                if (z != glanceableHubContainerController.shadeShowingAndConsumingTouches && glanceableHubContainerController.hubShowing) {
                    Logger logger = glanceableHubContainerController.logger;
                    LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new GlanceableHubContainerController$$ExternalSyntheticLambda0(3), null);
                    logMessageObtain.setBool1(z);
                    logger.getBuffer().commit(logMessageObtain);
                }
                glanceableHubContainerController.shadeShowingAndConsumingTouches = z;
                GlanceableHubContainerController.access$updateTouchHandlingState(this.this$0);
            }
        }, null, null, 24, null);
        JavaAdapterKt.collectFlow$default(view, keyguardInteractor.isDreaming, new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$initView$12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.this$0.isDreaming = ((Boolean) obj).booleanValue();
            }
        }, null, null, 24, null);
        JavaAdapterKt.collectFlow$default(view, (Flow) this.communalViewModel.swipeToHubEnabled$delegate.getValue(), new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$initView$13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.this$0.swipeToHubEnabled = ((Boolean) obj).booleanValue();
            }
        }, null, null, 24, null);
        CommunalWrapper communalWrapper = new CommunalWrapper(view.getContext(), this.communalSettingsInteractor);
        this.communalContainerWrapper = communalWrapper;
        communalWrapper.addView(this.communalContainerView);
        Logger.d$default(this.logger, "Hub container initialized", null, 2, null);
        CommunalWrapper communalWrapper2 = this.communalContainerWrapper;
        communalWrapper2.getClass();
        return communalWrapper2;
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        CommunalWrapper communalWrapper;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        if (this.communalContainerView != null) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.notificationStackScrollLayoutController;
            notificationStackScrollLayoutController.getClass();
            boolean zIsBelowLastNotification = notificationStackScrollLayoutController.mView.isBelowLastNotification(x, y);
            boolean z2 = !zIsBelowLastNotification;
            int x2 = (int) motionEvent.getX();
            int y2 = (int) motionEvent.getY();
            KeyguardMediaController keyguardMediaController = this.keyguardMediaController;
            keyguardMediaController.getClass();
            Rect rect = new Rect();
            UniqueObjectHostView uniqueObjectHostView = keyguardMediaController.mediaHost.hostView;
            if (uniqueObjectHostView == null) {
                uniqueObjectHostView = null;
            }
            uniqueObjectHostView.getBoundsOnScreen(rect);
            boolean zContains = rect.contains(x2, y2);
            int x3 = (int) motionEvent.getX();
            int y3 = (int) motionEvent.getY();
            Iterator it = this.lockscreenSmartspaceController.smartspaceViews.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                Object obj = (BcSmartspaceDataPlugin.SmartspaceView) it.next();
                Rect rect2 = new Rect();
                ((View) obj).getBoundsOnScreen(rect2);
                if (rect2.contains(x3, y3)) {
                    z = true;
                    break;
                }
            }
            this.communalSettingsInteractor.isV2FlagEnabled();
            boolean z3 = this.hubShowing;
            Logger logger = this.logger;
            if (!z3 && (!zIsBelowLastNotification || zContains || z || !this.swipeToHubEnabled)) {
                LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new GlanceableHubContainerController$$ExternalSyntheticLambda0(0), null);
                logMessageObtain.setBool1(z2);
                logMessageObtain.setBool2(zContains);
                logMessageObtain.setBool3(z);
                logMessageObtain.setBool4(false);
                logger.getBuffer().commit(logMessageObtain);
                return false;
            }
            boolean z4 = motionEvent.getActionMasked() == 0;
            boolean z5 = motionEvent.getActionMasked() == 1;
            boolean z6 = motionEvent.getActionMasked() == 2;
            boolean z7 = motionEvent.getActionMasked() == 3;
            boolean z8 = this.anyBouncerShowing || this.shadeConsumingTouches || this.shadeShowing;
            if ((z4 || z6) && !z8) {
                if (z4) {
                    LogMessage logMessageObtain2 = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new GlanceableHubContainerController$$ExternalSyntheticLambda0(1), null);
                    logMessageObtain2.setInt1((int) motionEvent.getX());
                    logMessageObtain2.setInt2((int) motionEvent.getY());
                    logMessageObtain2.setBool1(this.hubShowing);
                    logMessageObtain2.setBool2(this.isDreaming);
                    logMessageObtain2.setBool3(this.onLockscreen);
                    logger.getBuffer().commit(logMessageObtain2);
                }
                this.isTrackingHubTouch = true;
            }
            if (this.isTrackingHubTouch) {
                boolean z9 = this.onLockscreen && (this.shadeConsumingTouches || this.anyBouncerShowing);
                if (z9 != this.touchTakenByKeyguardGesture && z9) {
                    Logger.d$default(logger, "Lock screen touch consumed by shade or bouncer, ignoring subsequent touches", null, 2, null);
                }
                this.touchTakenByKeyguardGesture = z9;
                if (z5 || z7) {
                    LogMessage logMessageObtain3 = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new GlanceableHubContainerController$$ExternalSyntheticLambda0(2), null);
                    logMessageObtain3.setInt1((int) motionEvent.getX());
                    logMessageObtain3.setInt2((int) motionEvent.getY());
                    logMessageObtain3.setBool1(z5);
                    logMessageObtain3.setBool2(this.shadeConsumingTouches);
                    logMessageObtain3.setBool3(this.anyBouncerShowing);
                    logger.getBuffer().commit(logMessageObtain3);
                    this.isTrackingHubTouch = false;
                    this.touchTakenByKeyguardGesture = false;
                }
                UserTouchActivityNotifier userTouchActivityNotifier = this.userTouchActivityNotifier;
                if (this.inEditModeTransition) {
                    return true;
                }
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                ref$BooleanRef.element = this.hubShowing;
                try {
                    if (!this.touchTakenByKeyguardGesture && (communalWrapper = this.communalContainerWrapper) != null) {
                        ((ArraySet) communalWrapper.consumers).add(new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$dispatchTouchEvent$1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                if (((Boolean) obj2).booleanValue()) {
                                    ref$BooleanRef.element = true;
                                }
                            }
                        });
                        try {
                            communalWrapper.dispatchTouchEvent(motionEvent);
                            ((ArraySet) communalWrapper.consumers).clear();
                        } catch (Throwable th) {
                            ((ArraySet) communalWrapper.consumers).clear();
                            throw th;
                        }
                    }
                    boolean z10 = ref$BooleanRef.element;
                    if (z10) {
                        userTouchActivityNotifier.notifyActivity(motionEvent);
                    }
                    return z10;
                } catch (Throwable th2) {
                    if (ref$BooleanRef.element) {
                        userTouchActivityNotifier.notifyActivity(motionEvent);
                    }
                    throw th2;
                }
            }
        }
        return false;
    }
}
