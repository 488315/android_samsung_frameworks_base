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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.searcle.SearcleTipLayoutHelper;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import com.android.systemui.util.SettingsHelper;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                if (view.equals(SearcleTipPopup.this.rootView)) {
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
                if (view.equals(SearcleTipPopup.this.rootView)) {
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

    /* JADX WARN: Removed duplicated region for block: B:109:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean access$show(final com.android.systemui.searcle.SearcleTipPopup r19) {
        /*
            Method dump skipped, instructions count: 685
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.searcle.SearcleTipPopup.access$show(com.android.systemui.searcle.SearcleTipPopup):boolean");
    }

    public final void hide() {
        SearcleTipAnimHelper searcleTipAnimHelper;
        AnimatorSet animatorSet;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("hide isTipPopupShowing = ", "SearcleTipPopup", this.isTipPopupShowing);
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
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("showSearcleTip isRetryShowing = ", "SearcleTipPopup", z);
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
