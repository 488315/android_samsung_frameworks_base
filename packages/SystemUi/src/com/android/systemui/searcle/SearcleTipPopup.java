package com.android.systemui.searcle;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
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
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.QpRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.searcle.SearcleTipPopup.CloseAnimatorListener;
import com.android.systemui.searcle.SearcleTipPopup.OpenAnimatorListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SearcleTipPopup {
    public static final int BUBBLE_ALPHA_DURATION;
    public static final float DEST_SCALE;
    public static final Interpolator ELASTIC_CUSTOM_INTERPOLATOR;
    public static final float INIT_SCALE;
    public static final int POSITION_DURATION;
    public static final int SCALE_DURATION;
    public static final Interpolator SINE_IN_OUT_33_INTERPOLATOR;
    public static final Interpolator SINE_IN_OUT_70_INTERPOLATOR;
    public static final int TEXT_ALPHA_DURATION;
    public final ArrayList closeAnimList;
    public AnimatorSet closeAnimSet;
    public final Context context;
    public final Display defaultDisplay;
    public final Handler handler = new Handler(Looper.getMainLooper());
    public InputChannelCompat$InputEventReceiver inputEventReceiver;
    public InputMonitorCompat inputMonitor;
    public boolean isTipPopupShowing;
    public NavBarStateManager navBarStateManager;
    public final SearcleTipPopup$onAttachStateChangeListener$1 onAttachStateChangeListener;
    public final ArrayList openAnimList;
    public AnimatorSet openAnimSet;
    public final SettingsHelper settingsHelper;
    public SearcleTipView tipLayout;
    public final WindowManager windowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CloseAnimatorListener extends BaseAnimatorListener {
        public CloseAnimatorListener(String str) {
            super(SearcleTipPopup.this, str);
        }

        @Override // com.android.systemui.searcle.SearcleTipPopup.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            float f = SearcleTipPopup.INIT_SCALE;
            SearcleTipPopup.this.hideImmediate();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OpenAnimatorListener extends BaseAnimatorListener {
        public OpenAnimatorListener(String str) {
            super(SearcleTipPopup.this, str);
        }

        @Override // com.android.systemui.searcle.SearcleTipPopup.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            float f = SearcleTipPopup.INIT_SCALE;
            SearcleTipPopup searcleTipPopup = SearcleTipPopup.this;
            WindowManager windowManager = searcleTipPopup.windowManager;
            if (windowManager != null) {
                SearcleTipView searcleTipView = searcleTipPopup.tipLayout;
                searcleTipPopup.getClass();
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2038, 0, -3);
                layoutParams.semAddPrivateFlags(16);
                layoutParams.setTitle("SearcleTip");
                windowManager.addView(searcleTipView, layoutParams);
            }
        }
    }

    static {
        new Companion(null);
        INIT_SCALE = 0.32f;
        DEST_SCALE = 1.0f;
        POSITION_DURATION = 167;
        SCALE_DURATION = 167;
        BUBBLE_ALPHA_DURATION = 83;
        TEXT_ALPHA_DURATION = 167;
        ELASTIC_CUSTOM_INTERPOLATOR = new PathInterpolator(1.0f, 1.3f);
        SINE_IN_OUT_70_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f);
        SINE_IN_OUT_33_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
    }

    /* JADX WARN: Type inference failed for: r3v13, types: [com.android.systemui.searcle.SearcleTipPopup$onAttachStateChangeListener$1] */
    public SearcleTipPopup(Context context) {
        this.context = context;
        Object systemService = context.getSystemService("window");
        this.windowManager = systemService instanceof WindowManager ? (WindowManager) systemService : null;
        this.defaultDisplay = ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).getDisplay(0);
        this.settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        this.openAnimList = new ArrayList();
        this.closeAnimList = new ArrayList();
        this.onAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.searcle.SearcleTipPopup$onAttachStateChangeListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                final SearcleTipPopup searcleTipPopup = SearcleTipPopup.this;
                if (view == searcleTipPopup.tipLayout) {
                    searcleTipPopup.isTipPopupShowing = true;
                    InputMonitorCompat inputMonitorCompat = searcleTipPopup.inputMonitor;
                    if (inputMonitorCompat != null) {
                        inputMonitorCompat.mInputMonitor.dispose();
                        searcleTipPopup.inputMonitor = null;
                    }
                    InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = searcleTipPopup.inputEventReceiver;
                    if (inputChannelCompat$InputEventReceiver != null) {
                        inputChannelCompat$InputEventReceiver.dispose();
                        searcleTipPopup.inputEventReceiver = null;
                    }
                    InputMonitorCompat inputMonitorCompat2 = new InputMonitorCompat("SearcleTip", searcleTipPopup.defaultDisplay.getDisplayId());
                    searcleTipPopup.inputMonitor = inputMonitorCompat2;
                    searcleTipPopup.inputEventReceiver = new InputChannelCompat$InputEventReceiver(inputMonitorCompat2.mInputMonitor.getInputChannel(), Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.searcle.SearcleTipPopup$startInputListening$1
                        @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                        public final void onInputEvent(InputEvent inputEvent) {
                            Log.d("SearcleTipPopup", "startInputListening ev = " + inputEvent);
                            if (inputEvent instanceof MotionEvent) {
                                MotionEvent motionEvent = (MotionEvent) inputEvent;
                                if (motionEvent.getActionMasked() == 0) {
                                    float f = SearcleTipPopup.INIT_SCALE;
                                    SearcleTipPopup searcleTipPopup2 = SearcleTipPopup.this;
                                    searcleTipPopup2.getClass();
                                    Rect rect = new Rect();
                                    LinearLayout bubbleLayout = searcleTipPopup2.getBubbleLayout();
                                    if (bubbleLayout != null) {
                                        bubbleLayout.getGlobalVisibleRect(rect);
                                    }
                                    Context context2 = searcleTipPopup2.context;
                                    int dimensionPixelSize = context2.getResources().getDimensionPixelSize(R.dimen.notification_custom_view_max_image_height_low_ram);
                                    if (QpRune.QUICK_TABLET) {
                                        rect.top += dimensionPixelSize;
                                        rect.bottom += dimensionPixelSize;
                                    } else {
                                        if (context2.getResources().getConfiguration().orientation == 2 && !searcleTipPopup2.settingsHelper.isNavigationBarGestureWhileHidden()) {
                                            rect.right += dimensionPixelSize;
                                            rect.left += dimensionPixelSize;
                                        } else {
                                            rect.top += dimensionPixelSize;
                                            rect.bottom += dimensionPixelSize;
                                        }
                                    }
                                    if (rect.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                                        return;
                                    }
                                    searcleTipPopup2.hide();
                                }
                            }
                        }
                    });
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                SearcleTipPopup searcleTipPopup = SearcleTipPopup.this;
                if (view == searcleTipPopup.tipLayout) {
                    searcleTipPopup.isTipPopupShowing = false;
                    InputMonitorCompat inputMonitorCompat = searcleTipPopup.inputMonitor;
                    if (inputMonitorCompat != null) {
                        inputMonitorCompat.mInputMonitor.dispose();
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
    }

    public static void initProperty(int i, View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                updateProperty(0.0f, i, view);
            }
        }
    }

    public static void updateProperty(float f, int i, View view) {
        switch (i) {
            case 1:
                view.setAlpha(f);
                break;
            case 2:
                view.setScaleX(f);
                view.setScaleY(f);
                break;
            case 3:
                view.setTranslationY(f);
                break;
            case 4:
                view.setTranslationY(f);
                break;
            case 5:
                view.setTranslationY(f);
                break;
            case 6:
                view.setTranslationX(f);
                break;
            case 7:
                view.setTranslationX(-f);
                break;
        }
    }

    public final LinearLayout getBubbleLayout() {
        SearcleTipView searcleTipView = this.tipLayout;
        if (searcleTipView != null) {
            return (LinearLayout) searcleTipView.findViewById(com.android.systemui.R.id.searcle_tip_bubble);
        }
        return null;
    }

    public final void hide() {
        AnimatorSet animatorSet;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("hide isTipPopupShowing = ", this.isTipPopupShowing, "SearcleTipPopup");
        if (!this.isTipPopupShowing || (animatorSet = this.closeAnimSet) == null || animatorSet == null) {
            return;
        }
        animatorSet.start();
    }

    public final void hideImmediate() {
        if (this.tipLayout != null) {
            View[] viewArr = new View[2];
            viewArr[0] = getBubbleLayout();
            SearcleTipView searcleTipView = this.tipLayout;
            viewArr[1] = searcleTipView != null ? searcleTipView.findViewById(com.android.systemui.R.id.searcle_tip_content) : null;
            initProperty(1, viewArr);
            initProperty(3, getBubbleLayout());
            initProperty(6, getBubbleLayout());
            initProperty(2, getBubbleLayout());
            if (this.isTipPopupShowing) {
                WindowManager windowManager = this.windowManager;
                if (windowManager != null) {
                    windowManager.removeViewImmediate(this.tipLayout);
                }
                this.openAnimList.clear();
                this.closeAnimList.clear();
                this.openAnimSet = null;
                this.closeAnimSet = null;
                this.tipLayout = null;
            }
        }
    }

    public final Animator makeAnimator(final View view, final int i, int i2, float f, float f2, Interpolator interpolator) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        if (view != null) {
            ofFloat.setDuration(i2);
            ofFloat.setStartDelay(0);
            ofFloat.setInterpolator(interpolator);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.searcle.SearcleTipPopup$makeAnimator$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SearcleTipPopup searcleTipPopup = SearcleTipPopup.this;
                    View view2 = view;
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    int i3 = i;
                    float f3 = SearcleTipPopup.INIT_SCALE;
                    searcleTipPopup.getClass();
                    SearcleTipPopup.updateProperty(floatValue, i3, view2);
                }
            });
        }
        return ofFloat;
    }

    public final void showSearcleTip(final boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("showSearcleTip isRetryShowing = ", z, "SearcleTipPopup");
        this.handler.post(new Runnable() { // from class: com.android.systemui.searcle.SearcleTipPopup$showSearcleTip$1
            /* JADX WARN: Code restructure failed: missing block: B:138:0x016a, code lost:
            
                if (r6 == false) goto L103;
             */
            /* JADX WARN: Code restructure failed: missing block: B:155:0x00da, code lost:
            
                if (r9.isTaskBarEnabled(false) == true) goto L51;
             */
            /* JADX WARN: Code restructure failed: missing block: B:156:0x00de, code lost:
            
                if (r2 != false) goto L52;
             */
            /* JADX WARN: Code restructure failed: missing block: B:38:0x00cd, code lost:
            
                if (r14 != 2) goto L59;
             */
            /* JADX WARN: Code restructure failed: missing block: B:39:0x00e0, code lost:
            
                r4 = 4;
             */
            /* JADX WARN: Code restructure failed: missing block: B:60:0x0157, code lost:
            
                if (r6 != 2) goto L103;
             */
            /* JADX WARN: Code restructure failed: missing block: B:61:0x016c, code lost:
            
                r6 = 8388693;
             */
            /* JADX WARN: Removed duplicated region for block: B:102:0x02b5  */
            /* JADX WARN: Removed duplicated region for block: B:105:0x02c3  */
            /* JADX WARN: Removed duplicated region for block: B:108:0x02e6  */
            /* JADX WARN: Removed duplicated region for block: B:111:0x02ff  */
            /* JADX WARN: Removed duplicated region for block: B:114:0x0306  */
            /* JADX WARN: Removed duplicated region for block: B:124:0x02eb  */
            /* JADX WARN: Removed duplicated region for block: B:125:0x0295  */
            /* JADX WARN: Removed duplicated region for block: B:126:0x01ff  */
            /* JADX WARN: Removed duplicated region for block: B:127:0x01e8  */
            /* JADX WARN: Removed duplicated region for block: B:129:0x01b5  */
            /* JADX WARN: Removed duplicated region for block: B:142:0x0174  */
            /* JADX WARN: Removed duplicated region for block: B:150:0x0142  */
            /* JADX WARN: Removed duplicated region for block: B:151:0x0101  */
            /* JADX WARN: Removed duplicated region for block: B:42:0x00fc  */
            /* JADX WARN: Removed duplicated region for block: B:45:0x0111  */
            /* JADX WARN: Removed duplicated region for block: B:53:0x0146  */
            /* JADX WARN: Removed duplicated region for block: B:64:0x018f  */
            /* JADX WARN: Removed duplicated region for block: B:69:0x01e3  */
            /* JADX WARN: Removed duplicated region for block: B:71:0x01ec  */
            /* JADX WARN: Removed duplicated region for block: B:74:0x01f2  */
            /* JADX WARN: Removed duplicated region for block: B:77:0x01fa  */
            /* JADX WARN: Removed duplicated region for block: B:80:0x0220  */
            /* JADX WARN: Removed duplicated region for block: B:89:0x023c  */
            /* JADX WARN: Removed duplicated region for block: B:96:0x0290  */
            /* JADX WARN: Removed duplicated region for block: B:99:0x02ae  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                boolean z2;
                int i;
                int i2;
                float f;
                int i3;
                ViewGroup.LayoutParams layoutParams;
                SearcleTipView searcleTipView;
                AnimatorSet animatorSet;
                AnimatorSet animatorSet2;
                AnimatorSet animatorSet3;
                AnimatorSet animatorSet4;
                AnimatorSet animatorSet5;
                final SearcleTipPopup searcleTipPopup = SearcleTipPopup.this;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("show isTipPopupShowing = ", searcleTipPopup.isTipPopupShowing, "SearcleTipPopup");
                boolean z3 = searcleTipPopup.isTipPopupShowing;
                boolean z4 = true;
                if (!z3) {
                    if (z3 && searcleTipPopup.tipLayout != null) {
                        searcleTipPopup.hideImmediate();
                    }
                    if (searcleTipPopup.tipLayout == null) {
                        Context context = searcleTipPopup.context;
                        Boolean bool = null;
                        SearcleTipView searcleTipView2 = (SearcleTipView) LayoutInflater.from(context).inflate(com.android.systemui.R.layout.searcle_tip_popup, (ViewGroup) null);
                        searcleTipPopup.tipLayout = searcleTipView2;
                        searcleTipView2.addOnAttachStateChangeListener(searcleTipPopup.onAttachStateChangeListener);
                        SearcleTipView searcleTipView3 = searcleTipPopup.tipLayout;
                        if (searcleTipView3 != null) {
                            searcleTipView3.dismiss = new Runnable() { // from class: com.android.systemui.searcle.SearcleTipPopup$startOpenAnimatorSet$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SearcleTipPopup.this.hide();
                                }
                            };
                        }
                        SearcleTipView searcleTipView4 = searcleTipPopup.tipLayout;
                        TextView textView = searcleTipView4 != null ? (TextView) searcleTipView4.findViewById(com.android.systemui.R.id.searcle_tip_text) : null;
                        SettingsHelper settingsHelper = searcleTipPopup.settingsHelper;
                        if (textView != null) {
                            textView.setText(context.getString(settingsHelper.isNavigationBarGestureWhileHidden() ? com.android.systemui.R.string.searcle_tip_gesture : com.android.systemui.R.string.searcle_tip_button));
                        }
                        boolean z5 = context.getResources().getConfiguration().orientation == 2 && !settingsHelper.isNavigationBarGestureWhileHidden();
                        int navigationBarAlignPosition = settingsHelper.getNavigationBarAlignPosition();
                        NavBarStateManager navBarStateManager = searcleTipPopup.navBarStateManager;
                        if (navBarStateManager != null) {
                            int i4 = NavBarStateManager.$r8$clinit;
                            bool = Boolean.valueOf(navBarStateManager.isTaskBarEnabled(false));
                        }
                        Log.d("SearcleTipPopup", "getDirection position = " + navigationBarAlignPosition + ", taskbar enable = " + bool);
                        boolean z6 = QpRune.QUICK_TABLET;
                        Display display = searcleTipPopup.defaultDisplay;
                        if (z6) {
                            if (!settingsHelper.isNavigationBarGestureWhileHidden()) {
                                int navigationBarAlignPosition2 = settingsHelper.getNavigationBarAlignPosition();
                                if (navigationBarAlignPosition2 == 0) {
                                    i = 5;
                                } else if (navigationBarAlignPosition2 == 1) {
                                    NavBarStateManager navBarStateManager2 = searcleTipPopup.navBarStateManager;
                                    if (navBarStateManager2 != null) {
                                        int i5 = NavBarStateManager.$r8$clinit;
                                    }
                                    z4 = false;
                                }
                                LinearLayout bubbleLayout = searcleTipPopup.getBubbleLayout();
                                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) (bubbleLayout == null ? bubbleLayout.getLayoutParams() : null);
                                int dimensionPixelSize = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.tips_margin_without_offset);
                                if (i != 3) {
                                    layoutParams2.bottomMargin = dimensionPixelSize;
                                } else if (i == 4) {
                                    layoutParams2.bottomMargin = dimensionPixelSize;
                                    layoutParams2.setMarginEnd(context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.tips_margin));
                                } else if (i == 5) {
                                    layoutParams2.bottomMargin = dimensionPixelSize;
                                    layoutParams2.setMarginStart(context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.tips_margin));
                                } else if (i == 6) {
                                    layoutParams2.setMarginEnd(dimensionPixelSize);
                                } else if (i == 7) {
                                    layoutParams2.setMarginStart(dimensionPixelSize);
                                }
                                if (z6) {
                                    if (z5) {
                                        i2 = DeviceState.getRotation(display.getRotation()) == 1 ? 8388629 : 8388627;
                                        layoutParams2.gravity = i2;
                                        if (z6) {
                                        }
                                        int i6 = (int) (i3 * f);
                                        LinearLayout bubbleLayout2 = searcleTipPopup.getBubbleLayout();
                                        if (bubbleLayout2 == null) {
                                        }
                                        if (layoutParams != null) {
                                        }
                                        searcleTipView = searcleTipPopup.tipLayout;
                                        if (searcleTipView != null) {
                                        }
                                        SearcleTipView searcleTipView5 = searcleTipPopup.tipLayout;
                                        if (searcleTipView5 == null) {
                                        }
                                        searcleTipPopup.hideImmediate();
                                        ArrayList arrayList = searcleTipPopup.openAnimList;
                                        arrayList.clear();
                                        ArrayList arrayList2 = searcleTipPopup.closeAnimList;
                                        arrayList2.clear();
                                        searcleTipPopup.openAnimSet = new AnimatorSet();
                                        searcleTipPopup.closeAnimSet = new AnimatorSet();
                                        if (bubbleLayout != null) {
                                        }
                                        if (bubbleLayout != null) {
                                        }
                                        int i7 = SearcleTipPopup.POSITION_DURATION;
                                        float dimensionPixelSize2 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.tips_trans_position);
                                        Interpolator interpolator = SearcleTipPopup.ELASTIC_CUSTOM_INTERPOLATOR;
                                        arrayList.add(searcleTipPopup.makeAnimator(bubbleLayout, i, i7, 0.0f, dimensionPixelSize2, interpolator));
                                        int i8 = SearcleTipPopup.SCALE_DURATION;
                                        float f2 = SearcleTipPopup.INIT_SCALE;
                                        float f3 = SearcleTipPopup.DEST_SCALE;
                                        arrayList.add(searcleTipPopup.makeAnimator(bubbleLayout, 2, i8, f2, f3, interpolator));
                                        arrayList.add(searcleTipPopup.makeAnimator(bubbleLayout, 1, SearcleTipPopup.BUBBLE_ALPHA_DURATION, 0.0f, 1.0f, SearcleTipPopup.SINE_IN_OUT_70_INTERPOLATOR));
                                        SearcleTipView searcleTipView6 = searcleTipPopup.tipLayout;
                                        if (searcleTipView6 == null) {
                                        }
                                        int i9 = SearcleTipPopup.TEXT_ALPHA_DURATION;
                                        Interpolator interpolator2 = SearcleTipPopup.SINE_IN_OUT_33_INTERPOLATOR;
                                        arrayList.add(searcleTipPopup.makeAnimator(r1, 1, i9, 0.0f, 1.0f, interpolator2));
                                        animatorSet = searcleTipPopup.openAnimSet;
                                        if (animatorSet != null) {
                                        }
                                        animatorSet2 = searcleTipPopup.openAnimSet;
                                        if (animatorSet2 != null) {
                                        }
                                        animatorSet3 = searcleTipPopup.openAnimSet;
                                        if (animatorSet3 != null) {
                                        }
                                        arrayList2.add(searcleTipPopup.makeAnimator(bubbleLayout, 2, i8, f3, f2, interpolator));
                                        arrayList2.add(searcleTipPopup.makeAnimator(bubbleLayout, 1, i8, 1.0f, 0.0f, interpolator2));
                                        SearcleTipView searcleTipView7 = searcleTipPopup.tipLayout;
                                        arrayList2.add(searcleTipPopup.makeAnimator(searcleTipView7 == null ? searcleTipView7.findViewById(com.android.systemui.R.id.searcle_tip_content) : null, 1, 0, f3, f2, interpolator2));
                                        animatorSet4 = searcleTipPopup.closeAnimSet;
                                        if (animatorSet4 != null) {
                                        }
                                        animatorSet5 = searcleTipPopup.closeAnimSet;
                                        if (animatorSet5 != null) {
                                        }
                                        z2 = true;
                                    }
                                    i2 = 81;
                                    layoutParams2.gravity = i2;
                                    if (z6) {
                                    }
                                    int i62 = (int) (i3 * f);
                                    LinearLayout bubbleLayout22 = searcleTipPopup.getBubbleLayout();
                                    if (bubbleLayout22 == null) {
                                    }
                                    if (layoutParams != null) {
                                    }
                                    searcleTipView = searcleTipPopup.tipLayout;
                                    if (searcleTipView != null) {
                                    }
                                    SearcleTipView searcleTipView52 = searcleTipPopup.tipLayout;
                                    if (searcleTipView52 == null) {
                                    }
                                    searcleTipPopup.hideImmediate();
                                    ArrayList arrayList3 = searcleTipPopup.openAnimList;
                                    arrayList3.clear();
                                    ArrayList arrayList22 = searcleTipPopup.closeAnimList;
                                    arrayList22.clear();
                                    searcleTipPopup.openAnimSet = new AnimatorSet();
                                    searcleTipPopup.closeAnimSet = new AnimatorSet();
                                    if (bubbleLayout != null) {
                                    }
                                    if (bubbleLayout != null) {
                                    }
                                    int i72 = SearcleTipPopup.POSITION_DURATION;
                                    float dimensionPixelSize22 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.tips_trans_position);
                                    Interpolator interpolator3 = SearcleTipPopup.ELASTIC_CUSTOM_INTERPOLATOR;
                                    arrayList3.add(searcleTipPopup.makeAnimator(bubbleLayout, i, i72, 0.0f, dimensionPixelSize22, interpolator3));
                                    int i82 = SearcleTipPopup.SCALE_DURATION;
                                    float f22 = SearcleTipPopup.INIT_SCALE;
                                    float f32 = SearcleTipPopup.DEST_SCALE;
                                    arrayList3.add(searcleTipPopup.makeAnimator(bubbleLayout, 2, i82, f22, f32, interpolator3));
                                    arrayList3.add(searcleTipPopup.makeAnimator(bubbleLayout, 1, SearcleTipPopup.BUBBLE_ALPHA_DURATION, 0.0f, 1.0f, SearcleTipPopup.SINE_IN_OUT_70_INTERPOLATOR));
                                    SearcleTipView searcleTipView62 = searcleTipPopup.tipLayout;
                                    if (searcleTipView62 == null) {
                                    }
                                    int i92 = SearcleTipPopup.TEXT_ALPHA_DURATION;
                                    Interpolator interpolator22 = SearcleTipPopup.SINE_IN_OUT_33_INTERPOLATOR;
                                    arrayList3.add(searcleTipPopup.makeAnimator(r1, 1, i92, 0.0f, 1.0f, interpolator22));
                                    animatorSet = searcleTipPopup.openAnimSet;
                                    if (animatorSet != null) {
                                    }
                                    animatorSet2 = searcleTipPopup.openAnimSet;
                                    if (animatorSet2 != null) {
                                    }
                                    animatorSet3 = searcleTipPopup.openAnimSet;
                                    if (animatorSet3 != null) {
                                    }
                                    arrayList22.add(searcleTipPopup.makeAnimator(bubbleLayout, 2, i82, f32, f22, interpolator3));
                                    arrayList22.add(searcleTipPopup.makeAnimator(bubbleLayout, 1, i82, 1.0f, 0.0f, interpolator22));
                                    SearcleTipView searcleTipView72 = searcleTipPopup.tipLayout;
                                    arrayList22.add(searcleTipPopup.makeAnimator(searcleTipView72 == null ? searcleTipView72.findViewById(com.android.systemui.R.id.searcle_tip_content) : null, 1, 0, f32, f22, interpolator22));
                                    animatorSet4 = searcleTipPopup.closeAnimSet;
                                    if (animatorSet4 != null) {
                                    }
                                    animatorSet5 = searcleTipPopup.closeAnimSet;
                                    if (animatorSet5 != null) {
                                    }
                                    z2 = true;
                                } else {
                                    if (!settingsHelper.isNavigationBarGestureWhileHidden()) {
                                        int navigationBarAlignPosition3 = settingsHelper.getNavigationBarAlignPosition();
                                        if (navigationBarAlignPosition3 == 0) {
                                            i2 = 8388691;
                                        } else if (navigationBarAlignPosition3 == 1) {
                                            NavBarStateManager navBarStateManager3 = searcleTipPopup.navBarStateManager;
                                            if (navBarStateManager3 != null) {
                                                int i10 = NavBarStateManager.$r8$clinit;
                                                boolean z7 = navBarStateManager3.isTaskBarEnabled(false);
                                            }
                                        }
                                        layoutParams2.gravity = i2;
                                        if (z6) {
                                            f = 0.94f;
                                            i3 = context.getResources().getConfiguration().orientation == 2 ? context.getResources().getDisplayMetrics().heightPixels : context.getResources().getDisplayMetrics().widthPixels;
                                        } else {
                                            f = 0.2552f;
                                            i3 = context.getResources().getConfiguration().orientation == 2 ? context.getResources().getDisplayMetrics().widthPixels : context.getResources().getDisplayMetrics().heightPixels;
                                        }
                                        int i622 = (int) (i3 * f);
                                        LinearLayout bubbleLayout222 = searcleTipPopup.getBubbleLayout();
                                        layoutParams = bubbleLayout222 == null ? bubbleLayout222.getLayoutParams() : null;
                                        if (layoutParams != null) {
                                            layoutParams.width = i622;
                                        }
                                        searcleTipView = searcleTipPopup.tipLayout;
                                        if (searcleTipView != null) {
                                            searcleTipView.measure(0, 0);
                                        }
                                        SearcleTipView searcleTipView522 = searcleTipPopup.tipLayout;
                                        int measuredHeight = searcleTipView522 == null ? searcleTipView522.getMeasuredHeight() : 0;
                                        searcleTipPopup.hideImmediate();
                                        ArrayList arrayList32 = searcleTipPopup.openAnimList;
                                        arrayList32.clear();
                                        ArrayList arrayList222 = searcleTipPopup.closeAnimList;
                                        arrayList222.clear();
                                        searcleTipPopup.openAnimSet = new AnimatorSet();
                                        searcleTipPopup.closeAnimSet = new AnimatorSet();
                                        if (bubbleLayout != null) {
                                            bubbleLayout.setPivotX((!z6 && z5) ? DeviceState.getRotation(display.getRotation()) == 1 ? i622 : 0.0f : i622 / 2.0f);
                                        }
                                        if (bubbleLayout != null) {
                                            bubbleLayout.setPivotY((!z6 && z5) ? measuredHeight / 2.0f : measuredHeight);
                                        }
                                        int i722 = SearcleTipPopup.POSITION_DURATION;
                                        float dimensionPixelSize222 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.tips_trans_position);
                                        Interpolator interpolator32 = SearcleTipPopup.ELASTIC_CUSTOM_INTERPOLATOR;
                                        arrayList32.add(searcleTipPopup.makeAnimator(bubbleLayout, i, i722, 0.0f, dimensionPixelSize222, interpolator32));
                                        int i822 = SearcleTipPopup.SCALE_DURATION;
                                        float f222 = SearcleTipPopup.INIT_SCALE;
                                        float f322 = SearcleTipPopup.DEST_SCALE;
                                        arrayList32.add(searcleTipPopup.makeAnimator(bubbleLayout, 2, i822, f222, f322, interpolator32));
                                        arrayList32.add(searcleTipPopup.makeAnimator(bubbleLayout, 1, SearcleTipPopup.BUBBLE_ALPHA_DURATION, 0.0f, 1.0f, SearcleTipPopup.SINE_IN_OUT_70_INTERPOLATOR));
                                        SearcleTipView searcleTipView622 = searcleTipPopup.tipLayout;
                                        View findViewById = searcleTipView622 == null ? searcleTipView622.findViewById(com.android.systemui.R.id.searcle_tip_content) : null;
                                        int i922 = SearcleTipPopup.TEXT_ALPHA_DURATION;
                                        Interpolator interpolator222 = SearcleTipPopup.SINE_IN_OUT_33_INTERPOLATOR;
                                        arrayList32.add(searcleTipPopup.makeAnimator(findViewById, 1, i922, 0.0f, 1.0f, interpolator222));
                                        animatorSet = searcleTipPopup.openAnimSet;
                                        if (animatorSet != null) {
                                            animatorSet.playTogether(arrayList32);
                                        }
                                        animatorSet2 = searcleTipPopup.openAnimSet;
                                        if (animatorSet2 != null) {
                                            animatorSet2.addListener(searcleTipPopup.new OpenAnimatorListener("OpenAnim"));
                                        }
                                        animatorSet3 = searcleTipPopup.openAnimSet;
                                        if (animatorSet3 != null) {
                                            animatorSet3.start();
                                        }
                                        arrayList222.add(searcleTipPopup.makeAnimator(bubbleLayout, 2, i822, f322, f222, interpolator32));
                                        arrayList222.add(searcleTipPopup.makeAnimator(bubbleLayout, 1, i822, 1.0f, 0.0f, interpolator222));
                                        SearcleTipView searcleTipView722 = searcleTipPopup.tipLayout;
                                        arrayList222.add(searcleTipPopup.makeAnimator(searcleTipView722 == null ? searcleTipView722.findViewById(com.android.systemui.R.id.searcle_tip_content) : null, 1, 0, f322, f222, interpolator222));
                                        animatorSet4 = searcleTipPopup.closeAnimSet;
                                        if (animatorSet4 != null) {
                                            animatorSet4.playTogether(arrayList222);
                                        }
                                        animatorSet5 = searcleTipPopup.closeAnimSet;
                                        if (animatorSet5 != null) {
                                            animatorSet5.addListener(searcleTipPopup.new CloseAnimatorListener("CloseAnim"));
                                        }
                                        z2 = true;
                                    }
                                    i2 = 81;
                                    layoutParams2.gravity = i2;
                                    if (z6) {
                                    }
                                    int i6222 = (int) (i3 * f);
                                    LinearLayout bubbleLayout2222 = searcleTipPopup.getBubbleLayout();
                                    if (bubbleLayout2222 == null) {
                                    }
                                    if (layoutParams != null) {
                                    }
                                    searcleTipView = searcleTipPopup.tipLayout;
                                    if (searcleTipView != null) {
                                    }
                                    SearcleTipView searcleTipView5222 = searcleTipPopup.tipLayout;
                                    if (searcleTipView5222 == null) {
                                    }
                                    searcleTipPopup.hideImmediate();
                                    ArrayList arrayList322 = searcleTipPopup.openAnimList;
                                    arrayList322.clear();
                                    ArrayList arrayList2222 = searcleTipPopup.closeAnimList;
                                    arrayList2222.clear();
                                    searcleTipPopup.openAnimSet = new AnimatorSet();
                                    searcleTipPopup.closeAnimSet = new AnimatorSet();
                                    if (bubbleLayout != null) {
                                    }
                                    if (bubbleLayout != null) {
                                    }
                                    int i7222 = SearcleTipPopup.POSITION_DURATION;
                                    float dimensionPixelSize2222 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.tips_trans_position);
                                    Interpolator interpolator322 = SearcleTipPopup.ELASTIC_CUSTOM_INTERPOLATOR;
                                    arrayList322.add(searcleTipPopup.makeAnimator(bubbleLayout, i, i7222, 0.0f, dimensionPixelSize2222, interpolator322));
                                    int i8222 = SearcleTipPopup.SCALE_DURATION;
                                    float f2222 = SearcleTipPopup.INIT_SCALE;
                                    float f3222 = SearcleTipPopup.DEST_SCALE;
                                    arrayList322.add(searcleTipPopup.makeAnimator(bubbleLayout, 2, i8222, f2222, f3222, interpolator322));
                                    arrayList322.add(searcleTipPopup.makeAnimator(bubbleLayout, 1, SearcleTipPopup.BUBBLE_ALPHA_DURATION, 0.0f, 1.0f, SearcleTipPopup.SINE_IN_OUT_70_INTERPOLATOR));
                                    SearcleTipView searcleTipView6222 = searcleTipPopup.tipLayout;
                                    if (searcleTipView6222 == null) {
                                    }
                                    int i9222 = SearcleTipPopup.TEXT_ALPHA_DURATION;
                                    Interpolator interpolator2222 = SearcleTipPopup.SINE_IN_OUT_33_INTERPOLATOR;
                                    arrayList322.add(searcleTipPopup.makeAnimator(findViewById, 1, i9222, 0.0f, 1.0f, interpolator2222));
                                    animatorSet = searcleTipPopup.openAnimSet;
                                    if (animatorSet != null) {
                                    }
                                    animatorSet2 = searcleTipPopup.openAnimSet;
                                    if (animatorSet2 != null) {
                                    }
                                    animatorSet3 = searcleTipPopup.openAnimSet;
                                    if (animatorSet3 != null) {
                                    }
                                    arrayList2222.add(searcleTipPopup.makeAnimator(bubbleLayout, 2, i8222, f3222, f2222, interpolator322));
                                    arrayList2222.add(searcleTipPopup.makeAnimator(bubbleLayout, 1, i8222, 1.0f, 0.0f, interpolator2222));
                                    SearcleTipView searcleTipView7222 = searcleTipPopup.tipLayout;
                                    arrayList2222.add(searcleTipPopup.makeAnimator(searcleTipView7222 == null ? searcleTipView7222.findViewById(com.android.systemui.R.id.searcle_tip_content) : null, 1, 0, f3222, f2222, interpolator2222));
                                    animatorSet4 = searcleTipPopup.closeAnimSet;
                                    if (animatorSet4 != null) {
                                    }
                                    animatorSet5 = searcleTipPopup.closeAnimSet;
                                    if (animatorSet5 != null) {
                                    }
                                    z2 = true;
                                }
                            }
                            i = 3;
                            LinearLayout bubbleLayout3 = searcleTipPopup.getBubbleLayout();
                            FrameLayout.LayoutParams layoutParams22 = (FrameLayout.LayoutParams) (bubbleLayout3 == null ? bubbleLayout3.getLayoutParams() : null);
                            int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.tips_margin_without_offset);
                            if (i != 3) {
                            }
                            if (z6) {
                            }
                        } else {
                            if (z5) {
                                i = DeviceState.getRotation(display.getRotation()) == 1 ? 6 : 7;
                                LinearLayout bubbleLayout32 = searcleTipPopup.getBubbleLayout();
                                FrameLayout.LayoutParams layoutParams222 = (FrameLayout.LayoutParams) (bubbleLayout32 == null ? bubbleLayout32.getLayoutParams() : null);
                                int dimensionPixelSize32 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.tips_margin_without_offset);
                                if (i != 3) {
                                }
                                if (z6) {
                                }
                            }
                            i = 3;
                            LinearLayout bubbleLayout322 = searcleTipPopup.getBubbleLayout();
                            FrameLayout.LayoutParams layoutParams2222 = (FrameLayout.LayoutParams) (bubbleLayout322 == null ? bubbleLayout322.getLayoutParams() : null);
                            int dimensionPixelSize322 = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.tips_margin_without_offset);
                            if (i != 3) {
                            }
                            if (z6) {
                            }
                        }
                        if (z && z2) {
                            SearcleTipPopupUtil searcleTipPopupUtil = SearcleTipPopupUtil.INSTANCE;
                            Context context2 = SearcleTipPopup.this.context;
                            searcleTipPopupUtil.getClass();
                            Prefs.putInt(context2, "SearcleTipCount", Prefs.getInt(context2, "SearcleTipCount", 0) + 1);
                            return;
                        }
                    }
                    Log.d("SearcleTipPopup", "startOpenAnimatorSet : There is a tipLayout that has already been created, but it has not yet been attachedToWindowed");
                }
                z2 = false;
                if (z) {
                }
            }
        });
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class BaseAnimatorListener implements Animator.AnimatorListener {
        public BaseAnimatorListener(SearcleTipPopup searcleTipPopup, String str) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            float f = SearcleTipPopup.INIT_SCALE;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            float f = SearcleTipPopup.INIT_SCALE;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            float f = SearcleTipPopup.INIT_SCALE;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    }
}
