package com.android.systemui.qp;

import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.widget.SeekBar;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;

public final class SubscreenBrightnessController extends ViewController {
    public static final Uri HIGH_BRIGHTNESS_MODE_ENTER_URI = Settings.System.getUriFor("high_brightness_mode_pms_enter");
    public static boolean mControlValueInitialized = false;
    public static boolean mExternalChange = false;
    public static boolean mIsHighBrightnessMode = false;
    public static boolean mTracking;
    public static boolean mUsingHighBrightnessDialogEnabled;
    public final Handler mBackgroundHandler;
    public float mBrightness;
    public float mBrightnessMax;
    public float mBrightnessMin;
    public final BrightnessObserver mBrightnessObserver;
    public final Context mContext;
    public boolean mDetailActivity;
    public Display mDisplay;
    public int mDisplayId;
    public final AnonymousClass1 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public final AnonymousClass3 mHandler;
    public boolean mListening;
    public final float mMaximumBacklight;
    public final float mMinimumBacklight;
    public final AnonymousClass4 mSeekListener;
    private SettingsHelper mSettingsHelper;
    public int mSliderAnimationDuration;
    public ValueAnimator mSliderAnimator;
    public final AnonymousClass2 mUpdateSliderRunnable;
    public final SubroomBrightnessSettingsView mView;

    public final class BrightnessObserver extends ContentObserver {
        public final ContentResolver mCr;
        public final AnonymousClass1 mHighBrightnessModeEnterRunnable;

        public BrightnessObserver(Handler handler) {
            super(handler);
            this.mHighBrightnessModeEnterRunnable = new Runnable() { // from class: com.android.systemui.qp.SubscreenBrightnessController.BrightnessObserver.1
                @Override // java.lang.Runnable
                public final void run() {
                    ?? r0 = Settings.System.getIntForUser(SubscreenBrightnessController.this.mContext.getContentResolver(), "high_brightness_mode_pms_enter", 0, -2) != 0 ? 1 : 0;
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("UPDATE_HIGH_BRIGHTNESS_MODE = ", "SubscreenBrightnessController", r0);
                    SubscreenBrightnessController.mIsHighBrightnessMode = r0;
                    obtainMessage(10, r0, 0).sendToTarget();
                }
            };
            this.mCr = SubscreenBrightnessController.this.mContext.getContentResolver();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (z) {
                return;
            }
            if (SubscreenBrightnessController.HIGH_BRIGHTNESS_MODE_ENTER_URI.equals(uri)) {
                Log.d("SubscreenBrightnessController", "BrightnessObserver.onChange() : HIGH_BRIGHTNESS_MODE_ENTER_URI");
                SubscreenBrightnessController.this.mBackgroundHandler.post(this.mHighBrightnessModeEnterRunnable);
            } else {
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                subscreenBrightnessController.mBackgroundHandler.post(subscreenBrightnessController.mUpdateSliderRunnable);
            }
        }
    }

    /* renamed from: -$$Nest$monProgressSnap, reason: not valid java name */
    public static void m2058$$Nest$monProgressSnap(SubscreenBrightnessController subscreenBrightnessController, SeekBar seekBar) {
        subscreenBrightnessController.getClass();
        int progress = seekBar.getProgress();
        if (!QpRune.QUICK_SUBSCREEN_PANEL) {
            int i = Integer.MAX_VALUE;
            int i2 = 0;
            for (int i3 : subscreenBrightnessController.mView.mBrightnessLevels) {
                int abs = Math.abs(i3 - progress);
                if (abs < i) {
                    i2 = i3;
                    i = abs;
                }
            }
            progress = i2;
        }
        subscreenBrightnessController.mSettingsHelper.setSubscreenBrightness(progress);
    }

    /* renamed from: -$$Nest$mupdateSlider, reason: not valid java name */
    public static void m2059$$Nest$mupdateSlider(final SubscreenBrightnessController subscreenBrightnessController, float f) {
        StringBuilder sb = new StringBuilder("mMinimumBacklight=");
        float f2 = subscreenBrightnessController.mMinimumBacklight;
        sb.append(f2);
        sb.append(" mMaximumBacklight=");
        float f3 = subscreenBrightnessController.mMaximumBacklight;
        sb.append(f3);
        sb.append(" mBrightnessMin=");
        sb.append(subscreenBrightnessController.mBrightnessMin);
        sb.append(" mBrightnessMax=");
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(sb, subscreenBrightnessController.mBrightnessMax, "SubscreenBrightnessController");
        int autoBrightnessTransitionTime = subscreenBrightnessController.mSettingsHelper.getAutoBrightnessTransitionTime();
        ListPopupWindow$$ExternalSyntheticOutline0.m(autoBrightnessTransitionTime, "animation duration: ", "SubscreenBrightnessController");
        if (subscreenBrightnessController.mSliderAnimationDuration != autoBrightnessTransitionTime) {
            if (autoBrightnessTransitionTime < 0) {
                autoBrightnessTransitionTime = 0;
            }
            subscreenBrightnessController.mSliderAnimationDuration = autoBrightnessTransitionTime;
        }
        int round = Math.round((f * f3) - f2);
        Log.d("SubscreenBrightnessController", "updateSlider() = " + round + ", brightnessValue = " + f + ", min = " + f2 + " max = " + f3);
        boolean z = QpRune.QUICK_PANEL_BLUR_DEFAULT;
        boolean z2 = mControlValueInitialized;
        SubroomBrightnessSettingsView subroomBrightnessSettingsView = subscreenBrightnessController.mView;
        if (!z2 || subroomBrightnessSettingsView.getVisibility() != 0) {
            subroomBrightnessSettingsView.mSeekBar.setProgress(round);
            if (subroomBrightnessSettingsView.mDualSeekBarThreshold <= round) {
                subroomBrightnessSettingsView.mSunIcon.play(subroomBrightnessSettingsView.mSeekBar.getProgress(), subroomBrightnessSettingsView.mSeekBar.getMax());
            }
            mControlValueInitialized = true;
            return;
        }
        ValueAnimator valueAnimator = subscreenBrightnessController.mSliderAnimator;
        if (valueAnimator != null && valueAnimator.isStarted()) {
            subscreenBrightnessController.mSliderAnimator.cancel();
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(subroomBrightnessSettingsView.mSeekBar.getProgress(), round);
        subscreenBrightnessController.mSliderAnimator = ofInt;
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qp.SubscreenBrightnessController$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SubscreenBrightnessController subscreenBrightnessController2 = SubscreenBrightnessController.this;
                subscreenBrightnessController2.getClass();
                SubscreenBrightnessController.mExternalChange = true;
                int intValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                SubroomBrightnessSettingsView subroomBrightnessSettingsView2 = subscreenBrightnessController2.mView;
                subroomBrightnessSettingsView2.mSeekBar.setProgress(intValue);
                if (subroomBrightnessSettingsView2.mDualSeekBarThreshold <= intValue) {
                    subroomBrightnessSettingsView2.mSunIcon.play(subroomBrightnessSettingsView2.mSeekBar.getProgress(), subroomBrightnessSettingsView2.mSeekBar.getMax());
                }
                SubscreenBrightnessController.mExternalChange = false;
            }
        });
        subscreenBrightnessController.mSliderAnimator.setDuration(subscreenBrightnessController.mSliderAnimationDuration);
        subscreenBrightnessController.mSliderAnimator.start();
    }

    public SubscreenBrightnessController(Context context, SubroomBrightnessSettingsView subroomBrightnessSettingsView) {
        super(subroomBrightnessSettingsView);
        this.mBrightnessMin = 0.0f;
        this.mBrightnessMax = 1.0f;
        this.mSliderAnimationDuration = 0;
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.qp.SubscreenBrightnessController.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                subscreenBrightnessController.mDisplayId = i;
                BrightnessInfo brightnessInfo = subscreenBrightnessController.mDisplay.getBrightnessInfo();
                if (brightnessInfo == null) {
                    Log.d("SubscreenBrightnessController", "info is null ");
                    return;
                }
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m(new StringBuilder("info.brightness:"), brightnessInfo.brightness, "SubscreenBrightnessController");
                if (SubscreenBrightnessController.mTracking) {
                    return;
                }
                SubscreenBrightnessController subscreenBrightnessController2 = SubscreenBrightnessController.this;
                float f = subscreenBrightnessController2.mBrightness;
                float f2 = brightnessInfo.brightness;
                if (f != f2) {
                    subscreenBrightnessController2.mBrightness = f2;
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m(new StringBuilder("onDisplayChanged mBrightness:"), SubscreenBrightnessController.this.mBrightness, "SubscreenBrightnessController");
                    SubscreenBrightnessController subscreenBrightnessController3 = SubscreenBrightnessController.this;
                    subscreenBrightnessController3.mBackgroundHandler.post(subscreenBrightnessController3.mUpdateSliderRunnable);
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
            }
        };
        this.mUpdateSliderRunnable = new Runnable() { // from class: com.android.systemui.qp.SubscreenBrightnessController.2
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessInfo brightnessInfo = SubscreenBrightnessController.this.mDisplay.getBrightnessInfo();
                if (brightnessInfo == null) {
                    Log.d("SubscreenBrightnessController", "info.brightness: null ");
                    return;
                }
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                subscreenBrightnessController.mBrightnessMax = brightnessInfo.brightnessMaximum;
                subscreenBrightnessController.mBrightnessMin = brightnessInfo.brightnessMinimum;
                StringBuilder sb = new StringBuilder("info.brightness:");
                sb.append(brightnessInfo.brightness);
                sb.append(" info.brightnessMaximum:");
                sb.append(brightnessInfo.brightnessMaximum);
                sb.append(" info.brightnessMinimum:");
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m(sb, brightnessInfo.brightnessMinimum, "SubscreenBrightnessController");
                obtainMessage(1, Float.floatToIntBits(brightnessInfo.brightness), 0).sendToTarget();
            }
        };
        ?? r0 = new Handler() { // from class: com.android.systemui.qp.SubscreenBrightnessController.3
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                boolean z = true;
                SubscreenBrightnessController.mExternalChange = true;
                try {
                    int i = message.what;
                    SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                    if (i == 1) {
                        SubscreenBrightnessController.m2059$$Nest$mupdateSlider(subscreenBrightnessController, Float.intBitsToFloat(message.arg1));
                    } else if (i != 10) {
                        super.handleMessage(message);
                    } else {
                        if (message.arg1 == 0) {
                            z = false;
                        }
                        subscreenBrightnessController.getClass();
                        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("updateHighBrightnessModeEnter : ", ", slider is ", z);
                        SubroomBrightnessSettingsView subroomBrightnessSettingsView2 = subscreenBrightnessController.mView;
                        m.append(subroomBrightnessSettingsView2.mSeekBar);
                        Log.d("SubscreenBrightnessController", m.toString());
                        SubScreenBrightnessToggleSeekBar subScreenBrightnessToggleSeekBar = subroomBrightnessSettingsView2.mSeekBar;
                        if (subScreenBrightnessToggleSeekBar != null) {
                            subScreenBrightnessToggleSeekBar.mHighBrightnessModeEnter = z;
                        }
                    }
                    SubscreenBrightnessController.mExternalChange = false;
                } catch (Throwable th) {
                    SubscreenBrightnessController.mExternalChange = false;
                    throw th;
                }
            }
        };
        this.mHandler = r0;
        this.mSeekListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.qp.SubscreenBrightnessController.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                SubroomBrightnessSettingsView subroomBrightnessSettingsView2;
                Log.d("SubscreenBrightnessController", "onProgressChanged");
                SubscreenBrightnessController.this.onChanged(i, SubscreenBrightnessController.mTracking, false);
                boolean z2 = QpRune.QUICK_SUBSCREEN_PANEL;
                if (z2) {
                    SubscreenBrightnessController.this.getClass();
                    ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).getClass();
                    if ((!z2 || ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getSubscreenBrightnessMode() == 0) && SubscreenBrightnessController.mUsingHighBrightnessDialogEnabled && SubscreenBrightnessController.mTracking && (subroomBrightnessSettingsView2 = SubscreenBrightnessController.this.mView) != null && subroomBrightnessSettingsView2.mDualSeekBarThreshold <= i) {
                        return;
                    }
                    SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                    SubroomBrightnessSettingsView subroomBrightnessSettingsView3 = subscreenBrightnessController.mView;
                    if (subroomBrightnessSettingsView3 != null && subroomBrightnessSettingsView3.mDualSeekBarThreshold <= i && SubscreenBrightnessController.mTracking) {
                        subroomBrightnessSettingsView3.setDualSeekBarResources(true, subscreenBrightnessController.mDetailActivity);
                    } else if (subroomBrightnessSettingsView3 != null) {
                        subroomBrightnessSettingsView3.setDualSeekBarResources(false, subscreenBrightnessController.mDetailActivity);
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
                SubscreenBrightnessController.mTracking = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar) {
                SubscreenBrightnessController.mTracking = false;
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                subscreenBrightnessController.onChanged(subscreenBrightnessController.mView.mSeekBar.getProgress(), false, true);
                SubscreenBrightnessController.m2058$$Nest$monProgressSnap(SubscreenBrightnessController.this, seekBar);
                SubscreenBrightnessController subscreenBrightnessController2 = SubscreenBrightnessController.this;
                subscreenBrightnessController2.mView.setDualSeekBarResources(false, subscreenBrightnessController2.mDetailActivity);
                if (!QpRune.QUICK_SUBSCREEN_PANEL) {
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_BAR_COVER);
                } else if (SubscreenBrightnessController.this.mDetailActivity) {
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.STID_BRIGHTNESS_EVENT_DETAIL_COVER);
                } else {
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_BAR_QP_COVER);
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.STID_BRIGHTNESS_BAR_COVER, seekBar.getProgress() + 1);
                }
            }
        };
        new Runnable() { // from class: com.android.systemui.qp.SubscreenBrightnessController.6
            @Override // java.lang.Runnable
            public final void run() {
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                if (subscreenBrightnessController.mListening || !QpRune.QUICK_SUBSCREEN_PANEL) {
                    return;
                }
                subscreenBrightnessController.mListening = true;
                SubscreenUtil subscreenUtil = (SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class);
                Context context2 = SubscreenBrightnessController.this.mContext;
                subscreenUtil.getClass();
                subscreenBrightnessController.mDisplay = SubscreenUtil.getSubDisplay(context2);
                SubscreenBrightnessController subscreenBrightnessController2 = SubscreenBrightnessController.this;
                subscreenBrightnessController2.mDisplayManager.registerDisplayListener(subscreenBrightnessController2.mDisplayListener, subscreenBrightnessController2.mHandler, 8L);
                SubscreenBrightnessController subscreenBrightnessController3 = SubscreenBrightnessController.this;
                SubroomBrightnessSettingsView subroomBrightnessSettingsView2 = subscreenBrightnessController3.mView;
                subroomBrightnessSettingsView2.mSeekBar.setOnSeekBarChangeListener(subscreenBrightnessController3.mSeekListener);
                BrightnessObserver brightnessObserver = SubscreenBrightnessController.this.mBrightnessObserver;
                brightnessObserver.mCr.unregisterContentObserver(brightnessObserver);
                brightnessObserver.mCr.registerContentObserver(SubscreenBrightnessController.HIGH_BRIGHTNESS_MODE_ENTER_URI, false, brightnessObserver, -1);
            }
        };
        new Runnable() { // from class: com.android.systemui.qp.SubscreenBrightnessController.7
            @Override // java.lang.Runnable
            public final void run() {
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                if (subscreenBrightnessController.mListening && QpRune.QUICK_SUBSCREEN_PANEL) {
                    subscreenBrightnessController.mListening = false;
                    subscreenBrightnessController.mDisplayManager.unregisterDisplayListener(subscreenBrightnessController.mDisplayListener);
                    SubscreenBrightnessController.this.mView.mSeekBar.setOnSeekBarChangeListener(null);
                    BrightnessObserver brightnessObserver = SubscreenBrightnessController.this.mBrightnessObserver;
                    brightnessObserver.mCr.unregisterContentObserver(brightnessObserver);
                }
            }
        };
        this.mView = subroomBrightnessSettingsView;
        this.mContext = context;
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mBackgroundHandler = (Handler) Dependency.sDependency.getDependencyInner(Dependency.BG_HANDLER);
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mMinimumBacklight = powerManager.getMinimumScreenBrightnessSetting();
        this.mMaximumBacklight = powerManager.getMaximumScreenBrightnessSetting();
        this.mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        this.mBrightnessObserver = new BrightnessObserver(r0);
    }

    public final void onChanged(int i, boolean z, boolean z2) {
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(new StringBuilder("onChanged: mExternalChange="), mExternalChange, " stopTracking=", z2, "SubscreenBrightnessController");
        if (mExternalChange) {
            return;
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("tracking : ", "mIsHighBrightnessMode : ", z), mIsHighBrightnessMode, "SubscreenBrightnessController");
        if (!z && mIsHighBrightnessMode && this.mSettingsHelper.getSubscreenBrightnessMode() != 0) {
            BrightnessInfo brightnessInfo = this.mDisplay.getBrightnessInfo();
            if (brightnessInfo == null) {
                Log.d("TAG", "info.brightness: null aaa ");
                return;
            } else {
                this.mBrightness = brightnessInfo.brightness;
                this.mBackgroundHandler.post(this.mUpdateSliderRunnable);
            }
        }
        ValueAnimator valueAnimator = this.mSliderAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        final float f = (i + this.mMinimumBacklight) / this.mMaximumBacklight;
        this.mDisplayManager.setTemporaryBrightness(this.mDisplayId, f);
        if (z) {
            return;
        }
        AsyncTask.execute(new Runnable() { // from class: com.android.systemui.qp.SubscreenBrightnessController.5
            @Override // java.lang.Runnable
            public final void run() {
                SubscreenBrightnessController.this.mSettingsHelper.setAutoBrightnessTransitionTime(-1);
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                subscreenBrightnessController.mDisplayManager.setBrightness(subscreenBrightnessController.mDisplayId, f);
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mView.mSeekBar.setOnSeekBarChangeListener(this.mSeekListener);
        SubscreenUtil subscreenUtil = (SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class);
        Context context = this.mContext;
        subscreenUtil.getClass();
        this.mDisplay = SubscreenUtil.getSubDisplay(context);
        Log.d("SubscreenBrightnessController", "mDisplay = :" + this.mDisplay);
        Display display = this.mDisplay;
        if (display != null) {
            this.mDisplayId = display.getDisplayId();
        }
        boolean z = QpRune.QUICK_SUBSCREEN_PANEL;
        if (z) {
            mUsingHighBrightnessDialogEnabled = this.mSettingsHelper.getShownMaxBrightnessDialog() == 0;
            this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mHandler, 8L);
        }
        if (z) {
            BrightnessObserver brightnessObserver = this.mBrightnessObserver;
            brightnessObserver.mCr.unregisterContentObserver(brightnessObserver);
            brightnessObserver.mCr.registerContentObserver(HIGH_BRIGHTNESS_MODE_ENTER_URI, false, brightnessObserver, -1);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mView.mSeekBar.setOnSeekBarChangeListener(null);
        boolean z = QpRune.QUICK_SUBSCREEN_PANEL;
        if (z) {
            this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        }
        if (z) {
            BrightnessObserver brightnessObserver = this.mBrightnessObserver;
            brightnessObserver.mCr.unregisterContentObserver(brightnessObserver);
        }
    }
}
