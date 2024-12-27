package com.android.systemui.shade;

import android.os.PowerManager;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.ambient.touch.dagger.AmbientTouchComponent;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.ui.compose.CommunalContent;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.communal.util.CommunalColors;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.SceneDataSourceDelegator;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import java.util.HashSet;
import java.util.function.Consumer;
import kotlin.collections.EmptyList;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;

public final class GlanceableHubContainerController implements LifecycleOwner {
    public final AmbientTouchComponent.Factory ambientTouchComponentFactory;
    public boolean anyBouncerShowing;
    public final CommunalColors communalColors;
    public View communalContainerView;
    public final CommunalContent communalContent;
    public final CommunalInteractor communalInteractor;
    public final CommunalViewModel communalViewModel;
    public final SceneDataSourceDelegator dataSourceDelegator;
    public boolean hubShowing;
    public boolean isTrackingHubTouch;
    public final KeyguardInteractor keyguardInteractor;
    public final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    public final PowerManager powerManager;
    public int rightEdgeSwipeRegionWidth;
    public final ShadeInteractor shadeInteractor;
    public boolean shadeShowing;
    public TouchMonitor touchMonitor;

    public GlanceableHubContainerController(CommunalInteractor communalInteractor, CommunalViewModel communalViewModel, KeyguardInteractor keyguardInteractor, ShadeInteractor shadeInteractor, PowerManager powerManager, CommunalColors communalColors, AmbientTouchComponent.Factory factory, CommunalContent communalContent, SceneDataSourceDelegator sceneDataSourceDelegator, NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.communalInteractor = communalInteractor;
        this.communalViewModel = communalViewModel;
        this.keyguardInteractor = keyguardInteractor;
        this.shadeInteractor = shadeInteractor;
        this.powerManager = powerManager;
        this.communalColors = communalColors;
        this.ambientTouchComponentFactory = factory;
        this.communalContent = communalContent;
        this.dataSourceDelegator = sceneDataSourceDelegator;
    }

    public static final void access$updateTouchHandlingState(GlanceableHubContainerController glanceableHubContainerController) {
        boolean z = glanceableHubContainerController.hubShowing;
        LifecycleRegistry lifecycleRegistry = glanceableHubContainerController.lifecycleRegistry;
        if (z && !glanceableHubContainerController.shadeShowing && !glanceableHubContainerController.anyBouncerShowing) {
            lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
            return;
        }
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
        View view = glanceableHubContainerController.communalContainerView;
        Intrinsics.checkNotNull(view);
        view.setSystemGestureExclusionRects(EmptyList.INSTANCE);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.lifecycleRegistry;
    }

    public final View initView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(View view) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        if (this.communalContainerView != null) {
            throw new RuntimeException("Communal view has already been initialized");
        }
        if (this.touchMonitor == null) {
            TouchMonitor touchMonitor = this.ambientTouchComponentFactory.create(this, new HashSet()).getTouchMonitor();
            if (touchMonitor.mInitialized) {
                throw new IllegalStateException("TouchMonitor already initialized");
            }
            touchMonitor.mLifecycle.addObserver(touchMonitor.mLifecycleObserver);
            Flags.FEATURE_FLAGS.getClass();
            touchMonitor.mInitialized = true;
            this.touchMonitor = touchMonitor;
        }
        this.lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        this.communalContainerView = view;
        this.rightEdgeSwipeRegionWidth = view.getResources().getDimensionPixelSize(R.dimen.communal_right_edge_swipe_region_width);
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new GlanceableHubContainerController$initView$3(this, view.getResources().getDimensionPixelSize(R.dimen.communal_top_edge_swipe_region_height), view, view.getResources().getDimensionPixelSize(R.dimen.communal_bottom_edge_swipe_region_height), null));
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        KeyguardInteractor keyguardInteractor = this.keyguardInteractor;
        JavaAdapterKt.collectFlow$default(view, booleanFlowOperators.anyOf(keyguardInteractor.primaryBouncerShowing, keyguardInteractor.alternateBouncerShowing), new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$initView$4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                GlanceableHubContainerController glanceableHubContainerController = GlanceableHubContainerController.this;
                glanceableHubContainerController.anyBouncerShowing = booleanValue;
                GlanceableHubContainerController.access$updateTouchHandlingState(glanceableHubContainerController);
            }
        }, null, null, 24, null);
        JavaAdapterKt.collectFlow$default(view, this.communalInteractor.isCommunalVisible, new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$initView$5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                GlanceableHubContainerController glanceableHubContainerController = GlanceableHubContainerController.this;
                glanceableHubContainerController.hubShowing = booleanValue;
                GlanceableHubContainerController.access$updateTouchHandlingState(glanceableHubContainerController);
            }
        }, null, null, 24, null);
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) this.shadeInteractor;
        JavaAdapterKt.collectFlow$default(view, booleanFlowOperators.allOf(shadeInteractorImpl.isAnyFullyExpanded, booleanFlowOperators.not(shadeInteractorImpl.isUserInteracting)), new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$initView$6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                GlanceableHubContainerController glanceableHubContainerController = GlanceableHubContainerController.this;
                glanceableHubContainerController.shadeShowing = booleanValue;
                GlanceableHubContainerController.access$updateTouchHandlingState(glanceableHubContainerController);
            }
        }, null, null, 24, null);
        JavaAdapterKt.collectFlow$default(view, keyguardInteractor.isDreaming, new Consumer() { // from class: com.android.systemui.shade.GlanceableHubContainerController$initView$7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Boolean) obj).booleanValue();
                GlanceableHubContainerController.this.getClass();
            }
        }, null, null, 24, null);
        Flags.glanceableHubFullscreenSwipe();
        return view;
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        Flags.glanceableHubFullscreenSwipe();
        View view = this.communalContainerView;
        if (view == null) {
            return false;
        }
        boolean z = motionEvent.getActionMasked() == 0;
        boolean z2 = motionEvent.getActionMasked() == 1;
        boolean z3 = motionEvent.getActionMasked() == 3;
        boolean z4 = this.anyBouncerShowing || this.shadeShowing;
        if (z && !z4) {
            Flags.glanceableHubFullscreenSwipe();
            if (motionEvent.getRawX() >= view.getWidth() - this.rightEdgeSwipeRegionWidth || this.hubShowing) {
                this.isTrackingHubTouch = true;
            }
        }
        if (!this.isTrackingHubTouch) {
            return false;
        }
        if (z2 || z3) {
            this.isTrackingHubTouch = false;
        }
        try {
            new Ref$BooleanRef();
            Flags.glanceableHubFullscreenSwipe();
            view.dispatchTouchEvent(motionEvent);
            return true;
        } finally {
            this.powerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
        }
    }
}
