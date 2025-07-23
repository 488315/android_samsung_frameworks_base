package com.android.systemui.searcle;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;
import android.view.Display;
import android.view.InputEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.searcle.SearcleTipAnimHelper;
import com.android.systemui.searcle.SearcleTipAnimHelper.HideAnimatorListener;
import com.android.systemui.searcle.SearcleTipAnimHelper.ShowAnimatorListener;
import com.android.systemui.searcle.SearcleTipLayoutHelper;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.TestHelper;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SearcleTipPopup {
    public SearcleTipAnimHelper animHelper;
    public LinearLayout bubbleLayout;
    public LinearLayout contentLayout;
    public final Context context;
    public final Display defaultDisplay;
    public final Handler handler = new Handler(Looper.getMainLooper());
    public final SearcleTipPopup$inputEventListener$1 inputEventListener;
    public InputChannelCompat$InputEventReceiver inputEventReceiver;
    public InputMonitorCompat inputMonitor;
    public boolean isTipPopupShowing;
    public SearcleTipLayoutHelper layoutHelper;
    public NavBarStateManager navBarStateManager;
    public final SearcleTipPopup$onAttachStateChangeListener$1 onAttachStateChangeListener;
    public SearcleTipView rootView;
    private final SettingsHelper settingsHelper;
    public final WindowManager windowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SearcleTipLayoutHelper.DirectionType.values().length];
            try {
                iArr[SearcleTipLayoutHelper.DirectionType.Y.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SearcleTipLayoutHelper.DirectionType.XOnLTR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SearcleTipLayoutHelper.DirectionType.XOnRTL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SearcleTipLayoutHelper.DirectionType.XYOnLTR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[SearcleTipLayoutHelper.DirectionType.XYOnRTL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.systemui.searcle.SearcleTipPopup$onAttachStateChangeListener$1] */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.android.systemui.searcle.SearcleTipPopup$inputEventListener$1] */
    public SearcleTipPopup(Context context) {
        this.context = context;
        Object systemService = context.getSystemService("window");
        this.windowManager = systemService instanceof WindowManager ? (WindowManager) systemService : null;
        this.defaultDisplay = ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).getDisplay(0);
        this.settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        this.onAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.searcle.SearcleTipPopup$onAttachStateChangeListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                if (Intrinsics.areEqual(view, SearcleTipPopup.this.rootView)) {
                    SearcleTipPopup searcleTipPopup = SearcleTipPopup.this;
                    searcleTipPopup.isTipPopupShowing = true;
                    InputMonitorCompat inputMonitorCompat = searcleTipPopup.inputMonitor;
                    if (inputMonitorCompat != null) {
                        inputMonitorCompat.dispose();
                        searcleTipPopup.inputMonitor = null;
                    }
                    InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = searcleTipPopup.inputEventReceiver;
                    if (inputChannelCompat$InputEventReceiver != null) {
                        inputChannelCompat$InputEventReceiver.dispose();
                        searcleTipPopup.inputEventReceiver = null;
                    }
                    InputMonitorCompat inputMonitorCompat2 = new InputMonitorCompat("SearcleTip", searcleTipPopup.defaultDisplay.getDisplayId());
                    searcleTipPopup.inputMonitor = inputMonitorCompat2;
                    searcleTipPopup.inputEventReceiver = inputMonitorCompat2.getInputReceiver(Looper.getMainLooper(), Choreographer.getInstance(), searcleTipPopup.inputEventListener);
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                if (Intrinsics.areEqual(view, SearcleTipPopup.this.rootView)) {
                    SearcleTipPopup searcleTipPopup = SearcleTipPopup.this;
                    searcleTipPopup.isTipPopupShowing = false;
                    InputMonitorCompat inputMonitorCompat = searcleTipPopup.inputMonitor;
                    if (inputMonitorCompat != null) {
                        inputMonitorCompat.dispose();
                        searcleTipPopup.inputMonitor = null;
                    }
                    InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = searcleTipPopup.inputEventReceiver;
                    if (inputChannelCompat$InputEventReceiver != null) {
                        inputChannelCompat$InputEventReceiver.dispose();
                        searcleTipPopup.inputEventReceiver = null;
                    }
                }
            }
        };
        this.inputEventListener = new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.searcle.SearcleTipPopup$inputEventListener$1
            @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
            public final void onInputEvent(InputEvent inputEvent) {
                SearcleTipPopup searcleTipPopup = SearcleTipPopup.this;
                SearcleTipLayoutHelper searcleTipLayoutHelper = searcleTipPopup.layoutHelper;
                if (searcleTipLayoutHelper != null) {
                    Log.d("SearcleTipPopup", "onInputEvent ev = " + inputEvent);
                    Rect rect = new Rect();
                    LinearLayout linearLayout = searcleTipPopup.bubbleLayout;
                    if (linearLayout != null) {
                        linearLayout.getGlobalVisibleRect(rect);
                    }
                    if (inputEvent instanceof MotionEvent) {
                        MotionEvent motionEvent = (MotionEvent) inputEvent;
                        if (motionEvent.getActionMasked() == 0) {
                            int rawX = (int) motionEvent.getRawX();
                            int rawY = (int) motionEvent.getRawY();
                            boolean z = searcleTipLayoutHelper.isTablet;
                            int i = searcleTipLayoutHelper.naviBarHeight;
                            if (z || searcleTipLayoutHelper.isFoldWithMainDisplay) {
                                rect.top += i;
                                rect.bottom += i;
                            } else if (searcleTipLayoutHelper.isNaviBtnAndLandscape) {
                                rect.right += i;
                                rect.left += i;
                            } else {
                                rect.top += i;
                                rect.bottom += i;
                            }
                            if (rect.contains(rawX, rawY)) {
                                return;
                            }
                            searcleTipPopup.hide();
                        }
                    }
                }
            }
        };
    }

    public static final boolean access$show(final SearcleTipPopup searcleTipPopup) {
        boolean z;
        boolean z2;
        SearcleTipAnimHelper.AnimationType animationType;
        AnimatorSet animatorSet;
        Log.d("SearcleTipPopup", "show isTipPopupShowing = " + searcleTipPopup.isTipPopupShowing + ", rootView = " + searcleTipPopup.rootView);
        if (searcleTipPopup.isTipPopupShowing && searcleTipPopup.rootView != null) {
            searcleTipPopup.hideImmediate();
        }
        int i = 0;
        if (!searcleTipPopup.isTipPopupShowing && searcleTipPopup.rootView == null) {
            Context context = searcleTipPopup.context;
            int rotation = DeviceState.getRotation(searcleTipPopup.defaultDisplay.getRotation());
            boolean isTablet = TestHelper.isRoboUnitTest() ? DeviceType.isTablet() : ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
            boolean isSubDisplay = TestHelper.isRoboUnitTest() ? DeviceState.isSubDisplay(searcleTipPopup.context) : ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isFoldWide();
            boolean isNavigationBarGestureWhileHidden = searcleTipPopup.settingsHelper.isNavigationBarGestureWhileHidden();
            int navigationBarAlignPosition = searcleTipPopup.settingsHelper.getNavigationBarAlignPosition();
            NavBarStateManager navBarStateManager = searcleTipPopup.navBarStateManager;
            if (navBarStateManager == null || !((NavBarStateManagerImpl) navBarStateManager).isTaskBarEnabled(false)) {
                z = isTablet;
                z2 = false;
            } else {
                z = isTablet;
                z2 = true;
            }
            SearcleTipLayoutHelper searcleTipLayoutHelper = new SearcleTipLayoutHelper(context, rotation, z, isSubDisplay, isNavigationBarGestureWhileHidden, navigationBarAlignPosition, z2);
            Log.d("SearcleTipPopup", "startOpenAnimatorSet layoutHelper = " + searcleTipLayoutHelper);
            searcleTipPopup.layoutHelper = searcleTipLayoutHelper;
            SearcleTipView searcleTipView = searcleTipPopup.rootView;
            if (searcleTipView != null) {
                Log.d("SearcleTipPopup", "makeRootLayout remove old tipLayout = " + searcleTipView);
                searcleTipPopup.hideImmediate();
            }
            View inflate = LayoutInflater.from(searcleTipPopup.context).inflate(R.layout.searcle_tip_popup, (ViewGroup) null);
            SearcleTipView searcleTipView2 = inflate instanceof SearcleTipView ? (SearcleTipView) inflate : null;
            searcleTipPopup.rootView = searcleTipView2;
            Log.d("SearcleTipPopup", "makeRootLayout tipLayout = " + searcleTipView2);
            SearcleTipView searcleTipView3 = searcleTipPopup.rootView;
            if (searcleTipView3 != null) {
                searcleTipView3.addOnAttachStateChangeListener(searcleTipPopup.onAttachStateChangeListener);
                searcleTipView3.dismiss = new SearcleTipPopup$makeRootLayout$1$1(searcleTipPopup);
                TextView textView = (TextView) searcleTipView3.findViewById(R.id.searcle_tip_text);
                if (textView != null) {
                    textView.setText(searcleTipView3.getContext().getString(searcleTipPopup.settingsHelper.isNavigationBarGestureWhileHidden() ? R.string.searcle_tip_gesture : R.string.searcle_tip_button));
                }
                SearcleTipView searcleTipView4 = searcleTipPopup.rootView;
                LinearLayout linearLayout = searcleTipView4 != null ? (LinearLayout) searcleTipView4.findViewById(R.id.searcle_tip_bubble) : null;
                searcleTipPopup.bubbleLayout = linearLayout;
                SearcleTipLayoutHelper.DirectionType directionType = searcleTipLayoutHelper.direction;
                if (linearLayout != null) {
                    ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
                    FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
                    int i2 = searcleTipLayoutHelper.bubbleLayoutWidth;
                    if (layoutParams2 != null) {
                        int dimensionPixelSize = searcleTipLayoutHelper.context.getResources().getDimensionPixelSize(R.dimen.tips_margin_without_offset);
                        int i3 = SearcleTipLayoutHelper.WhenMappings.$EnumSwitchMapping$1[directionType.ordinal()];
                        if (i3 == 1) {
                            layoutParams2.bottomMargin = dimensionPixelSize;
                        } else if (i3 == 2) {
                            layoutParams2.setMarginEnd(dimensionPixelSize);
                        } else if (i3 == 3) {
                            layoutParams2.setMarginStart(dimensionPixelSize);
                        } else if (i3 == 4) {
                            layoutParams2.bottomMargin = dimensionPixelSize;
                            layoutParams2.setMarginEnd(searcleTipLayoutHelper.context.getResources().getDimensionPixelSize(R.dimen.tips_margin));
                        } else {
                            if (i3 != 5) {
                                throw new NoWhenBranchMatchedException();
                            }
                            layoutParams2.bottomMargin = dimensionPixelSize;
                            layoutParams2.setMarginStart(searcleTipLayoutHelper.context.getResources().getDimensionPixelSize(R.dimen.tips_margin));
                        }
                        layoutParams2.gravity = searcleTipLayoutHelper.gravity;
                        layoutParams2.width = i2;
                    }
                    View rootView = linearLayout.getRootView();
                    if (rootView != null) {
                        rootView.measure(0, 0);
                        i = rootView.getMeasuredHeight();
                    }
                    boolean z3 = searcleTipLayoutHelper.isTablet;
                    boolean z4 = searcleTipLayoutHelper.isNaviBtnAndLandscape;
                    boolean z5 = searcleTipLayoutHelper.isFoldWithMainDisplay;
                    linearLayout.setPivotX((z3 || z5 || !z4) ? i2 / 2.0f : searcleTipLayoutHelper.rotation == 1 ? i2 : 0.0f);
                    linearLayout.setPivotY((z3 || z5 || !z4) ? i : i / 2.0f);
                }
                SearcleTipView searcleTipView5 = searcleTipPopup.rootView;
                LinearLayout linearLayout2 = searcleTipView5 != null ? (LinearLayout) searcleTipView5.findViewById(R.id.searcle_tip_content) : null;
                searcleTipPopup.contentLayout = linearLayout2;
                LinearLayout linearLayout3 = searcleTipPopup.bubbleLayout;
                int i4 = WhenMappings.$EnumSwitchMapping$0[directionType.ordinal()];
                if (i4 == 1) {
                    animationType = SearcleTipAnimHelper.AnimationType.TransY;
                } else if (i4 == 2) {
                    animationType = SearcleTipAnimHelper.AnimationType.TransXOnLTR;
                } else if (i4 == 3) {
                    animationType = SearcleTipAnimHelper.AnimationType.TransXOnRTL;
                } else if (i4 == 4) {
                    animationType = SearcleTipAnimHelper.AnimationType.TransXYOnLTR;
                } else {
                    if (i4 != 5) {
                        throw new NoWhenBranchMatchedException();
                    }
                    animationType = SearcleTipAnimHelper.AnimationType.TransXYOnRTL;
                }
                if (linearLayout3 != null && linearLayout2 != null) {
                    SearcleTipAnimHelper searcleTipAnimHelper = new SearcleTipAnimHelper(searcleTipPopup.context, new Runnable() { // from class: com.android.systemui.searcle.SearcleTipPopup$makeAnimHelper$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SearcleTipPopup searcleTipPopup2 = SearcleTipPopup.this;
                            Log.d("SearcleTipPopup", "addView rootView = " + searcleTipPopup2.rootView);
                            WindowManager windowManager = searcleTipPopup2.windowManager;
                            if (windowManager != null) {
                                SearcleTipView searcleTipView6 = searcleTipPopup2.rootView;
                                WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(-1, -1, 0, 0, 2038, 0, -3);
                                if (!TestHelper.isRoboUnitTest()) {
                                    layoutParams3.semAddPrivateFlags(16);
                                }
                                layoutParams3.setTitle("SearcleTip");
                                windowManager.addView(searcleTipView6, layoutParams3);
                            }
                        }
                    }, new Runnable() { // from class: com.android.systemui.searcle.SearcleTipPopup$makeAnimHelper$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SearcleTipPopup.this.hideImmediate();
                        }
                    }, linearLayout3, linearLayout2);
                    searcleTipAnimHelper.showAnimList.clear();
                    searcleTipAnimHelper.hideAnimList.clear();
                    searcleTipAnimHelper.showAnimSet = null;
                    searcleTipAnimHelper.hideAnimSet = null;
                    LinearLayout linearLayout4 = searcleTipAnimHelper.bubbleLayout;
                    SearcleTipAnimHelper.AnimProperty animProperty = SearcleTipAnimHelper.SHOW_ANIM_BUBBLE_TRANS_PROPERTY;
                    animProperty.animType = animationType;
                    animProperty.toValue = searcleTipAnimHelper.context.getResources().getDimensionPixelSize(R.dimen.tips_trans_position);
                    Unit unit = Unit.INSTANCE;
                    searcleTipAnimHelper.showAnimSet = searcleTipAnimHelper.makeAnimSet(new SearcleTipAnimHelper.AnimPairSet[]{new SearcleTipAnimHelper.AnimPairSet(linearLayout4, animProperty), new SearcleTipAnimHelper.AnimPairSet(searcleTipAnimHelper.bubbleLayout, SearcleTipAnimHelper.SHOW_ANIM_BUBBLE_ALPHA_PROPERTY), new SearcleTipAnimHelper.AnimPairSet(searcleTipAnimHelper.bubbleLayout, SearcleTipAnimHelper.SHOW_ANIM_BUBBLE_SCALE_PROPERTY), new SearcleTipAnimHelper.AnimPairSet(searcleTipAnimHelper.contentLayout, SearcleTipAnimHelper.SHOW_ANIM_CONTENT_ALPHA_PROPERTY)}, searcleTipAnimHelper.showAnimList, searcleTipAnimHelper.new ShowAnimatorListener("ShowAnim"));
                    searcleTipAnimHelper.hideAnimSet = searcleTipAnimHelper.makeAnimSet(new SearcleTipAnimHelper.AnimPairSet[]{new SearcleTipAnimHelper.AnimPairSet(searcleTipAnimHelper.bubbleLayout, SearcleTipAnimHelper.HIDE_ANIM_BUBBLE_ALPHA_PROPERTY), new SearcleTipAnimHelper.AnimPairSet(searcleTipAnimHelper.bubbleLayout, SearcleTipAnimHelper.HIDE_ANIM_BUBBLE_SCALE_PROPERTY), new SearcleTipAnimHelper.AnimPairSet(searcleTipAnimHelper.contentLayout, SearcleTipAnimHelper.HIDE_ANIM_CONTENT_ALPHA_PROPERTY)}, searcleTipAnimHelper.hideAnimList, searcleTipAnimHelper.new HideAnimatorListener("HideAnim"));
                    searcleTipPopup.animHelper = searcleTipAnimHelper;
                }
                SearcleTipAnimHelper searcleTipAnimHelper2 = searcleTipPopup.animHelper;
                if (searcleTipAnimHelper2 != null && (animatorSet = searcleTipAnimHelper2.showAnimSet) != null) {
                    animatorSet.start();
                    Unit unit2 = Unit.INSTANCE;
                }
                return true;
            }
        }
        return false;
    }

    public final void hide() {
        SearcleTipAnimHelper searcleTipAnimHelper;
        AnimatorSet animatorSet;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("hide isTipPopupShowing = ", "SearcleTipPopup", this.isTipPopupShowing);
        if (!this.isTipPopupShowing || (searcleTipAnimHelper = this.animHelper) == null || (animatorSet = searcleTipAnimHelper.hideAnimSet) == null) {
            return;
        }
        animatorSet.start();
        Unit unit = Unit.INSTANCE;
    }

    public final void hideImmediate() {
        SearcleTipAnimHelper searcleTipAnimHelper;
        if (this.rootView != null) {
            if (this.bubbleLayout != null && this.contentLayout != null && (searcleTipAnimHelper = this.animHelper) != null) {
                SearcleTipAnimHelper.initProperty(SearcleTipAnimHelper.INIT_BUBBLE_PROPERTY_FIELDS, searcleTipAnimHelper.bubbleLayout);
                SearcleTipAnimHelper.initProperty(SearcleTipAnimHelper.INIT_CONTENT_PROPERTY_FIELDS, searcleTipAnimHelper.contentLayout);
            }
            if (this.isTipPopupShowing) {
                WindowManager windowManager = this.windowManager;
                if (windowManager != null) {
                    windowManager.removeViewImmediate(this.rootView);
                }
                SearcleTipAnimHelper searcleTipAnimHelper2 = this.animHelper;
                if (searcleTipAnimHelper2 != null) {
                    searcleTipAnimHelper2.showAnimList.clear();
                    searcleTipAnimHelper2.hideAnimList.clear();
                    searcleTipAnimHelper2.showAnimSet = null;
                    searcleTipAnimHelper2.hideAnimSet = null;
                }
                this.animHelper = null;
                this.layoutHelper = null;
                this.rootView = null;
                this.bubbleLayout = null;
                this.contentLayout = null;
            }
        }
    }

    public final void showSearcleTip(final boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("showSearcleTip isRetryShowing = ", "SearcleTipPopup", z);
        this.handler.post(new Runnable() { // from class: com.android.systemui.searcle.SearcleTipPopup$showSearcleTip$1
            @Override // java.lang.Runnable
            public final void run() {
                boolean access$show = SearcleTipPopup.access$show(SearcleTipPopup.this);
                if (z || !access$show) {
                    return;
                }
                SearcleTipPopupUtil searcleTipPopupUtil = SearcleTipPopupUtil.INSTANCE;
                Context context = SearcleTipPopup.this.context;
                searcleTipPopupUtil.getClass();
                Prefs.putInt(context, "SearcleTipCount", SearcleTipPopupUtil.getSearcleTipCount(context) + 1);
            }
        });
    }
}
