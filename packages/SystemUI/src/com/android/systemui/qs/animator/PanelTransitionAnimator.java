package com.android.systemui.qs.animator;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.PanelTransitionState;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PanelTransitionAnimator extends SecQSImplAnimatorBase {
    public View buttonContainer;
    public TouchAnimator buttonFadeInAnimator;
    public TouchAnimator buttonFadeOutAnimator;
    public TouchAnimator buttonLockScreenFadeInAnimator;
    public TouchAnimator carrierFadeInAnimator;
    public TouchAnimator carrierFadeOutAnimator;
    public View clockDateContainer;
    public TouchAnimator clockDateContainerFadeInAnimator;
    public TouchAnimator clockDateContainerFadeOutAnimator;
    public final Context context;
    public TouchAnimator headerQsStateAnimator;
    public TouchAnimator headerShadeStateAnimator;
    public final HeadsUpManager headsUpManager;
    public boolean inPinnedMode;
    public View networkSpeedContainer;
    public ViewGroup plmn;
    public View privacyContainer;
    public TouchAnimator qsFadeInDown;
    public TouchAnimator qsFadeInLeft;
    public TouchAnimator qsFadeOutDown;
    public TouchAnimator qsFadeOutRight;
    public View qsRootView;
    public TouchAnimator shadeFadeInDown;
    public TouchAnimator shadeFadeInRight;
    public TouchAnimator shadeFadeOutDown;
    public TouchAnimator shadeFadeOutLeft;
    public final ShadeHeaderController shadeHeaderController;
    public NotificationStackScrollLayout shadeRootView;
    public boolean splitEnabled;
    public View systemIconContainer;
    public final Lazy resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$resourcePicker$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        }
    });
    public final PanelTransitionAnimator$onHeadsUpChangedListener$1 onHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$onHeadsUpChangedListener$1
        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public final void onHeadsUpPinnedModeChanged(boolean z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onHeadsUpPinnedModeChanged: ", "PanelTransitionAnimator", z);
            PanelTransitionAnimator.this.inPinnedMode = z;
        }
    };
    public final float yDiff = 50.0f;
    public final float xDiff = 50.0f;
    public int panelTransitionState = 1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.animator.PanelTransitionAnimator$onHeadsUpChangedListener$1] */
    public PanelTransitionAnimator(Context context, ShadeHeaderController shadeHeaderController, HeadsUpManager headsUpManager) {
        this.context = context;
        this.shadeHeaderController = shadeHeaderController;
        this.headsUpManager = headsUpManager;
    }

    public static void updateVisibility(View view, boolean z) {
        if (z && view.getVisibility() == 8) {
            view.setVisibility(0);
        } else {
            if (z || view.getVisibility() != 0) {
                return;
            }
            view.setVisibility(8);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void clearAnimationState() {
        boolean z = this.mUserChanged;
        this.mUserChanged = false;
        if (!isThereNoView() || z) {
            int i = this.panelTransitionState;
            if (i != 2 || z) {
                Log.d("PanelTransitionAnimator", "clearAnimationState ".concat(PanelTransitionState.toString(i)));
                float headerTranslationXDiff = ((SecQSPanelResourcePicker) this.resourcePicker$delegate.getValue()).getHeaderTranslationXDiff(this.context);
                View view = this.qsRootView;
                if (view != null) {
                    int i2 = this.panelTransitionState;
                    if (i2 == 0) {
                        view.setAlpha(1.0f);
                    } else if (i2 == 1) {
                        view.setAlpha(0.0f);
                    }
                    view.setTranslationX(0.0f);
                    view.setTranslationY(0.0f);
                }
                NotificationStackScrollLayout notificationStackScrollLayout = this.shadeRootView;
                if (notificationStackScrollLayout != null) {
                    int i3 = this.panelTransitionState;
                    if (i3 == 0) {
                        notificationStackScrollLayout.setAlpha(0.0f);
                    } else if (i3 == 1) {
                        notificationStackScrollLayout.setAlpha(1.0f);
                    }
                    notificationStackScrollLayout.setTranslationX(0.0f);
                    notificationStackScrollLayout.setTranslationY(0.0f);
                }
                View view2 = this.networkSpeedContainer;
                if (view2 != null) {
                    int i4 = this.panelTransitionState;
                    if (i4 == 0) {
                        view2.setTranslationX(0.0f);
                    } else if (i4 == 1) {
                        view2.setTranslationX(-headerTranslationXDiff);
                    }
                }
                View view3 = this.systemIconContainer;
                if (view3 != null) {
                    int i5 = this.panelTransitionState;
                    if (i5 == 0) {
                        view3.setTranslationX(0.0f);
                    } else if (i5 == 1) {
                        view3.setTranslationX(-headerTranslationXDiff);
                    }
                }
                View view4 = this.privacyContainer;
                if (view4 != null) {
                    int i6 = this.panelTransitionState;
                    if (i6 == 0) {
                        view4.setTranslationX(0.0f);
                    } else if (i6 == 1) {
                        view4.setTranslationX(-headerTranslationXDiff);
                    }
                }
                View view5 = this.buttonContainer;
                if (view5 != null) {
                    int i7 = this.panelTransitionState;
                    if (i7 == 0) {
                        view5.setAlpha(1.0f);
                        view5.setTranslationX(0.0f);
                    } else if (i7 == 1) {
                        view5.setAlpha(0.0f);
                        view5.setTranslationX(-headerTranslationXDiff);
                    }
                }
                View view6 = this.clockDateContainer;
                if (view6 != null) {
                    int i8 = this.panelTransitionState;
                    if (i8 == 0) {
                        view6.setAlpha(0.0f);
                        view6.setTranslationX(-headerTranslationXDiff);
                    } else if (i8 == 1) {
                        view6.setAlpha(1.0f);
                        view6.setTranslationX(0.0f);
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        this.qsRootView = null;
        ((BaseHeadsUpManager) this.headsUpManager).mListeners.remove(this.onHeadsUpChangedListener);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final boolean isThereNoView() {
        if (!super.isThereNoView()) {
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled && !this.inPinnedMode && !QsAnimatorState.isDetailShowing) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed() {
        if (this.splitEnabled) {
            updateAnimators();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
        if (!isThereNoView() || this.mUserChanged) {
            int i = panelTransitionStateChangeEvent.state;
            if (i == 0) {
                View view = this.buttonContainer;
                if (view != null) {
                    updateVisibility(view, true);
                }
                View view2 = this.clockDateContainer;
                if (view2 != null) {
                    updateVisibility(view2, false);
                }
            } else if (i == 1) {
                View view3 = this.buttonContainer;
                if (view3 != null) {
                    updateVisibility(view3, false);
                }
                View view4 = this.clockDateContainer;
                if (view4 != null) {
                    updateVisibility(view4, true);
                }
            } else if (i == 2) {
                View view5 = this.buttonContainer;
                if (view5 != null) {
                    updateVisibility(view5, true);
                }
                View view6 = this.clockDateContainer;
                if (view6 != null) {
                    updateVisibility(view6, true);
                }
            }
            this.panelTransitionState = i;
            boolean z = this.splitEnabled;
            boolean z2 = panelTransitionStateChangeEvent.enabled;
            if (z != z2) {
                if (z2) {
                    updateAnimators();
                } else {
                    clearAnimationState();
                }
                this.splitEnabled = z2;
            }
            if (panelTransitionStateChangeEvent.fraction == 1.0f) {
                clearAnimationState();
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onStateChanged(int i) {
        clearAnimationState();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onUserSwitched(int i) {
        this.mUserChanged = true;
        SecPanelSplitHelper.Companion.getClass();
        if (SecPanelSplitHelper.isEnabled) {
            onPanelTransitionStateChanged(new PanelTransitionStateChangeEvent(true, 1.0f, 0));
            View view = this.clockDateContainer;
            if (view != null) {
                updateVisibility(view, true);
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setNotificationStackScrollerController(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.shadeRootView = notificationStackScrollLayoutController != null ? notificationStackScrollLayoutController.mView : null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
            return;
        }
        this.mQs = qs;
        View view = qs.getView();
        if (view != null) {
            this.qsRootView = view.findViewById(R.id.expanded_qs_scroll_view);
            this.buttonContainer = view.findViewById(R.id.header_settings_container);
            this.clockDateContainer = view.findViewById(R.id.clock_parent);
        }
        MotionLayout motionLayout = this.shadeHeaderController.header;
        this.networkSpeedContainer = motionLayout.findViewById(R.id.quick_qs_network_speed_container);
        this.systemIconContainer = motionLayout.findViewById(R.id.shade_header_system_icons);
        this.privacyContainer = motionLayout.findViewById(R.id.privacy_container);
        View findViewById = motionLayout.findViewById(R.id.anim_view);
        this.plmn = findViewById instanceof ViewGroup ? (ViewGroup) findViewById : null;
        ((BaseHeadsUpManager) this.headsUpManager).addListener(this.onHeadsUpChangedListener);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void slide(float f, float f2, PanelSlideEventHandler.Direction direction, int i) {
        View view;
        TouchAnimator touchAnimator;
        TouchAnimator touchAnimator2;
        TouchAnimator touchAnimator3;
        View view2;
        TouchAnimator touchAnimator4;
        if (isThereNoView()) {
            return;
        }
        StringBuilder sb = new StringBuilder("fraction = ");
        sb.append(f);
        sb.append(" direction = ");
        sb.append(direction);
        sb.append(" stateToChange = ");
        RecyclerView$$ExternalSyntheticOutline0.m(i, "PanelTransitionAnimator", sb);
        if (f2 != 0.0f) {
            float pow = (float) (((1 / Math.pow(1.0045d, Math.abs(f2))) * (-100.0f)) + 100.0f);
            Log.d("PanelTransitionAnimator", "overScrollX = " + f2 + " overScrollAmount = " + pow);
            if (f2 > 0.0f) {
                NotificationStackScrollLayout notificationStackScrollLayout = this.shadeRootView;
                if (notificationStackScrollLayout == null) {
                    return;
                }
                notificationStackScrollLayout.setTranslationX(pow);
                return;
            }
            if (f2 >= 0.0f || (view = this.qsRootView) == null) {
                return;
            }
            view.setTranslationX(-pow);
            return;
        }
        int i2 = this.panelTransitionState;
        if (i2 == 1) {
            Log.d("PanelTransitionAnimator", "SHADE_STATE");
            clearAnimationState();
            return;
        }
        if (i2 == 0) {
            Log.d("PanelTransitionAnimator", "QS_STATE");
            clearAnimationState();
            return;
        }
        if (direction != PanelSlideEventHandler.Direction.DOWN) {
            if (direction == PanelSlideEventHandler.Direction.LEFT) {
                Log.d("PanelTransitionAnimator", "LEFT animation");
                View view3 = this.qsRootView;
                if (view3 != null) {
                    view3.setTranslationX(0.0f);
                }
                NotificationStackScrollLayout notificationStackScrollLayout2 = this.shadeRootView;
                if (notificationStackScrollLayout2 != null) {
                    notificationStackScrollLayout2.setTranslationX(0.0f);
                }
                TouchAnimator touchAnimator5 = this.qsFadeInLeft;
                if (touchAnimator5 != null) {
                    touchAnimator5.setPosition(f);
                }
                TouchAnimator touchAnimator6 = this.shadeFadeOutLeft;
                if (touchAnimator6 != null) {
                    touchAnimator6.setPosition(f);
                }
                TouchAnimator touchAnimator7 = this.headerQsStateAnimator;
                if (touchAnimator7 != null) {
                    touchAnimator7.setPosition(f);
                }
                TouchAnimator touchAnimator8 = this.buttonFadeInAnimator;
                if (touchAnimator8 != null) {
                    touchAnimator8.setPosition(f);
                }
                if (QsAnimatorState.isNotificationImmersiceScrolling && (touchAnimator2 = this.carrierFadeInAnimator) != null) {
                    touchAnimator2.setPosition(f);
                }
                TouchAnimator touchAnimator9 = this.clockDateContainerFadeOutAnimator;
                if (touchAnimator9 != null) {
                    touchAnimator9.setPosition(f);
                    return;
                }
                return;
            }
            if (direction == PanelSlideEventHandler.Direction.RIGHT) {
                Log.d("PanelTransitionAnimator", "RIGHT animation");
                View view4 = this.qsRootView;
                if (view4 != null) {
                    view4.setTranslationX(0.0f);
                }
                NotificationStackScrollLayout notificationStackScrollLayout3 = this.shadeRootView;
                if (notificationStackScrollLayout3 != null) {
                    notificationStackScrollLayout3.setTranslationX(0.0f);
                }
                TouchAnimator touchAnimator10 = this.qsFadeOutRight;
                if (touchAnimator10 != null) {
                    touchAnimator10.setPosition(f);
                }
                TouchAnimator touchAnimator11 = this.shadeFadeInRight;
                if (touchAnimator11 != null) {
                    touchAnimator11.setPosition(f);
                }
                TouchAnimator touchAnimator12 = this.headerShadeStateAnimator;
                if (touchAnimator12 != null) {
                    touchAnimator12.setPosition(f);
                }
                TouchAnimator touchAnimator13 = this.buttonFadeOutAnimator;
                if (touchAnimator13 != null) {
                    touchAnimator13.setPosition(f);
                }
                if (QsAnimatorState.isNotificationImmersiceScrolling && (touchAnimator = this.carrierFadeOutAnimator) != null) {
                    touchAnimator.setPosition(f);
                }
                TouchAnimator touchAnimator14 = this.clockDateContainerFadeInAnimator;
                if (touchAnimator14 != null) {
                    touchAnimator14.setPosition(f);
                    return;
                }
                return;
            }
            return;
        }
        Log.d("PanelTransitionAnimator", "DOWN animation");
        View view5 = this.qsRootView;
        if (view5 != null) {
            view5.setTranslationX(0.0f);
        }
        NotificationStackScrollLayout notificationStackScrollLayout4 = this.shadeRootView;
        if (notificationStackScrollLayout4 != null) {
            notificationStackScrollLayout4.setTranslationX(0.0f);
        }
        if (QsAnimatorState.state == 1) {
            if (i == 0) {
                View view6 = this.buttonContainer;
                if (view6 != null) {
                    updateVisibility(view6, true);
                }
                View view7 = this.clockDateContainer;
                if (view7 != null) {
                    updateVisibility(view7, false);
                }
                TouchAnimator touchAnimator15 = this.buttonLockScreenFadeInAnimator;
                if (touchAnimator15 != null) {
                    touchAnimator15.setPosition(f);
                }
                TouchAnimator touchAnimator16 = this.shadeFadeOutDown;
                if (touchAnimator16 != null) {
                    touchAnimator16.setPosition(f);
                }
                TouchAnimator touchAnimator17 = this.qsFadeInDown;
                if (touchAnimator17 != null) {
                    touchAnimator17.setPosition(f);
                    return;
                }
                return;
            }
            return;
        }
        if (i != 0) {
            if (i != 1) {
                return;
            }
            TouchAnimator touchAnimator18 = this.qsFadeOutDown;
            if (touchAnimator18 != null) {
                touchAnimator18.setPosition(f);
            }
            TouchAnimator touchAnimator19 = this.shadeFadeInDown;
            if (touchAnimator19 != null) {
                touchAnimator19.setPosition(f);
            }
            TouchAnimator touchAnimator20 = this.headerShadeStateAnimator;
            if (touchAnimator20 != null) {
                touchAnimator20.setPosition(f);
            }
            TouchAnimator touchAnimator21 = this.buttonFadeOutAnimator;
            if (touchAnimator21 != null) {
                touchAnimator21.setPosition(f);
            }
            if (QsAnimatorState.isNotificationImmersiceScrolling && (touchAnimator4 = this.carrierFadeOutAnimator) != null) {
                touchAnimator4.setPosition(f);
            }
            TouchAnimator touchAnimator22 = this.clockDateContainerFadeInAnimator;
            if (touchAnimator22 != null) {
                touchAnimator22.setPosition(f);
                return;
            }
            return;
        }
        QS qs = this.mQs;
        if (!Intrinsics.areEqual((qs == null || (view2 = qs.getView()) == null) ? null : Float.valueOf(view2.getAlpha()), 1.0f)) {
            QS qs2 = this.mQs;
            View view8 = qs2 != null ? qs2.getView() : null;
            if (view8 != null) {
                view8.setAlpha(1.0f);
            }
        }
        TouchAnimator touchAnimator23 = this.shadeFadeOutDown;
        if (touchAnimator23 != null) {
            touchAnimator23.setPosition(f);
        }
        TouchAnimator touchAnimator24 = this.qsFadeInDown;
        if (touchAnimator24 != null) {
            touchAnimator24.setPosition(f);
        }
        TouchAnimator touchAnimator25 = this.headerQsStateAnimator;
        if (touchAnimator25 != null) {
            touchAnimator25.setPosition(f);
        }
        TouchAnimator touchAnimator26 = this.buttonFadeInAnimator;
        if (touchAnimator26 != null) {
            touchAnimator26.setPosition(f);
        }
        if (QsAnimatorState.isNotificationImmersiceScrolling && (touchAnimator3 = this.carrierFadeInAnimator) != null) {
            touchAnimator3.setPosition(f);
        }
        TouchAnimator touchAnimator27 = this.clockDateContainerFadeOutAnimator;
        if (touchAnimator27 != null) {
            touchAnimator27.setPosition(f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        Log.d("PanelTransitionAnimator", "updateAnimators");
        clearAnimationState();
        View view = this.qsRootView;
        if (view != null) {
            TouchAnimator.Builder builder = new TouchAnimator.Builder();
            builder.addFloat(view, "alpha", 0.0f, 1.0f);
            builder.addFloat(view, "translationY", -150.0f, 0.0f);
            builder.mStartDelay = 0.5f;
            this.qsFadeInDown = builder.build();
            TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
            builder2.addFloat(view, "alpha", 1.0f, 0.0f);
            builder2.addFloat(view, "translationY", 0.0f, 150.0f);
            builder2.mEndDelay = 0.5f;
            this.qsFadeOutDown = builder2.build();
            TouchAnimator.Builder builder3 = new TouchAnimator.Builder();
            builder3.addFloat(view, "alpha", 0.0f, 1.0f);
            builder3.addFloat(view, "translationX", 300.0f, 0.0f);
            builder3.mStartDelay = 0.5f;
            this.qsFadeInLeft = builder3.build();
            TouchAnimator.Builder builder4 = new TouchAnimator.Builder();
            builder4.addFloat(view, "alpha", 1.0f, 0.0f);
            builder4.addFloat(view, "translationX", 0.0f, 300.0f);
            builder4.mEndDelay = 0.5f;
            this.qsFadeOutRight = builder4.build();
        }
        NotificationStackScrollLayout notificationStackScrollLayout = this.shadeRootView;
        if (notificationStackScrollLayout != null) {
            TouchAnimator.Builder builder5 = new TouchAnimator.Builder();
            builder5.addFloat(notificationStackScrollLayout, "alpha", 0.0f, 1.0f);
            builder5.addFloat(notificationStackScrollLayout, "translationY", -150.0f, 0.0f);
            builder5.mStartDelay = 0.5f;
            this.shadeFadeInDown = builder5.build();
            TouchAnimator.Builder builder6 = new TouchAnimator.Builder();
            builder6.addFloat(notificationStackScrollLayout, "alpha", 1.0f, 0.0f);
            builder6.addFloat(notificationStackScrollLayout, "translationY", 0.0f, 150.0f);
            builder6.mEndDelay = 0.5f;
            this.shadeFadeOutDown = builder6.build();
            TouchAnimator.Builder builder7 = new TouchAnimator.Builder();
            builder7.addFloat(notificationStackScrollLayout, "alpha", 1.0f, 0.0f);
            builder7.addFloat(notificationStackScrollLayout, "translationX", 0.0f, -300.0f);
            builder7.mEndDelay = 0.5f;
            this.shadeFadeOutLeft = builder7.build();
            TouchAnimator.Builder builder8 = new TouchAnimator.Builder();
            builder8.addFloat(notificationStackScrollLayout, "alpha", 0.0f, 1.0f);
            builder8.addFloat(notificationStackScrollLayout, "translationX", -300.0f, 0.0f);
            builder8.mStartDelay = 0.5f;
            this.shadeFadeInRight = builder8.build();
        }
        float headerTranslationXDiff = ((SecQSPanelResourcePicker) this.resourcePicker$delegate.getValue()).getHeaderTranslationXDiff(this.context);
        TouchAnimator.Builder builder9 = new TouchAnimator.Builder();
        View view2 = this.networkSpeedContainer;
        if (view2 != null) {
            builder9.addFloat(view2, "translationX", -headerTranslationXDiff, 0.0f);
        }
        View view3 = this.systemIconContainer;
        if (view3 != null) {
            builder9.addFloat(view3, "translationX", -headerTranslationXDiff, 0.0f);
        }
        View view4 = this.privacyContainer;
        if (view4 != null) {
            builder9.addFloat(view4, "translationX", -headerTranslationXDiff, 0.0f);
        }
        this.headerQsStateAnimator = builder9.build();
        TouchAnimator.Builder builder10 = new TouchAnimator.Builder();
        View view5 = this.networkSpeedContainer;
        if (view5 != null) {
            builder10.addFloat(view5, "translationX", 0.0f, -headerTranslationXDiff);
        }
        View view6 = this.systemIconContainer;
        if (view6 != null) {
            builder10.addFloat(view6, "translationX", 0.0f, -headerTranslationXDiff);
        }
        View view7 = this.privacyContainer;
        if (view7 != null) {
            builder10.addFloat(view7, "translationX", 0.0f, -headerTranslationXDiff);
        }
        this.headerShadeStateAnimator = builder10.build();
        View view8 = this.buttonContainer;
        if (view8 != null) {
            TouchAnimator.Builder builder11 = new TouchAnimator.Builder();
            builder11.addFloat(view8, "alpha", 0.0f, 1.0f);
            float f = -headerTranslationXDiff;
            builder11.addFloat(view8, "translationX", f, 0.0f);
            builder11.mListener = new TouchAnimator.Listener() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$updateAnimators$5$1
                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationStarted() {
                    View view9 = PanelTransitionAnimator.this.buttonContainer;
                    if (view9 != null) {
                        PanelTransitionAnimator.updateVisibility(view9, true);
                    }
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtEnd() {
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtStart() {
                }
            };
            this.buttonFadeInAnimator = builder11.build();
            TouchAnimator.Builder builder12 = new TouchAnimator.Builder();
            builder12.addFloat(view8, "alpha", 1.0f, 0.0f);
            builder12.addFloat(view8, "translationX", 0.0f, f);
            this.buttonFadeOutAnimator = builder12.build();
            TouchAnimator.Builder builder13 = new TouchAnimator.Builder();
            builder13.addFloat(view8, "alpha", 0.0f, 1.0f);
            builder13.addFloat(view8, "translationX", this.xDiff, 0.0f);
            builder13.addFloat(view8, "translationY", -this.yDiff, 0.0f);
            builder13.addFloat(view8, "scaleX", 0.8f, 1.0f);
            builder13.addFloat(view8, "scaleY", 0.8f, 1.0f);
            builder13.mStartDelay = 0.7f;
            this.buttonLockScreenFadeInAnimator = builder13.build();
        }
        TouchAnimator.Builder builder14 = new TouchAnimator.Builder();
        ViewGroup viewGroup = this.plmn;
        if (viewGroup != null) {
            builder14.addFloat(viewGroup, "alpha", 0.0f, 1.0f);
            builder14.addFloat(viewGroup, "translationX", headerTranslationXDiff, 0.0f);
        }
        this.carrierFadeInAnimator = builder14.build();
        TouchAnimator.Builder builder15 = new TouchAnimator.Builder();
        ViewGroup viewGroup2 = this.plmn;
        if (viewGroup2 != null) {
            builder15.addFloat(viewGroup2, "alpha", 1.0f, 0.0f);
            builder15.addFloat(viewGroup2, "translationX", 0.0f, headerTranslationXDiff);
        }
        this.carrierFadeOutAnimator = builder15.build();
        TouchAnimator.Builder builder16 = new TouchAnimator.Builder();
        View view9 = this.clockDateContainer;
        if (view9 != null) {
            builder16.addFloat(view9, "alpha", 0.0f, 1.0f);
            builder16.addFloat(view9, "translationX", -headerTranslationXDiff, 0.0f);
        }
        builder16.mListener = new TouchAnimator.Listener() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$updateAnimators$8$2
            @Override // com.android.systemui.qs.TouchAnimator.Listener
            public final void onAnimationStarted() {
                View view10 = PanelTransitionAnimator.this.clockDateContainer;
                if (view10 != null) {
                    PanelTransitionAnimator.updateVisibility(view10, true);
                }
            }

            @Override // com.android.systemui.qs.TouchAnimator.Listener
            public final void onAnimationAtEnd() {
            }

            @Override // com.android.systemui.qs.TouchAnimator.Listener
            public final void onAnimationAtStart() {
            }
        };
        this.clockDateContainerFadeInAnimator = builder16.build();
        TouchAnimator.Builder builder17 = new TouchAnimator.Builder();
        View view10 = this.clockDateContainer;
        if (view10 != null) {
            builder17.addFloat(view10, "alpha", 1.0f, 0.0f);
            builder17.addFloat(view10, "translationX", 0.0f, -headerTranslationXDiff);
        }
        this.clockDateContainerFadeOutAnimator = builder17.build();
    }
}
