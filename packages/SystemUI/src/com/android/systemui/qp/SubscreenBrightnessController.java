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
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.settings.brightness.BrightnessDialog;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;

/* loaded from: classes2.dex */
public class SubscreenBrightnessController extends ViewController {
    public static final Uri HIGH_BRIGHTNESS_MODE_ENTER_URI = Settings.System.getUriFor("high_brightness_mode_pms_enter");
    public static boolean mControlValueInitialized = false;
    public static boolean mExternalChange = false;
    public static boolean mIsHighBrightnessMode = false;
    public static boolean mTracking;
    public static boolean mUsingHighBrightnessDialogEnabled;
    public String BRIGHTNESS_DIALOG_TAG;
    public final Handler mBackgroundHandler;
    public float mBrightness;
    public BrightnessDialog mBrightnessDialog;
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
    public final AnonymousClass6 mStartListeningRunnable;
    public final AnonymousClass7 mStopListeningRunnable;
    public final AnonymousClass2 mUpdateSliderRunnable;
    public final SubroomBrightnessSettingsView mView;

    public class BrightnessObserver extends ContentObserver {
        public final ContentResolver mCr;
        public final AnonymousClass1 mHighBrightnessModeEnterRunnable;

        /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.qp.SubscreenBrightnessController$BrightnessObserver$1] */
        public BrightnessObserver(Handler handler) {
            super(handler);
            this.mHighBrightnessModeEnterRunnable = new Runnable() { // from class: com.android.systemui.qp.SubscreenBrightnessController.BrightnessObserver.1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r0v5 */
                /* JADX WARN: Type inference failed for: r0v6, types: [boolean, int] */
                /* JADX WARN: Type inference failed for: r0v7 */
                @Override // java.lang.Runnable
                public final void run() {
                    ?? r0 = Settings.System.getIntForUser(SubscreenBrightnessController.this.mContext.getContentResolver(), "high_brightness_mode_pms_enter", 0, -2) != 0 ? 1 : 0;
                    EmergencyButtonController$$ExternalSyntheticOutline0.m("UPDATE_HIGH_BRIGHTNESS_MODE = ", "SubscreenBrightnessController", r0);
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
    public static void m2895$$Nest$monProgressSnap(SubscreenBrightnessController subscreenBrightnessController, SeekBar seekBar) {
        subscreenBrightnessController.getClass();
        int progress = seekBar.getProgress();
        if (!QpRune.QUICK_SUBSCREEN_PANEL) {
            int i = Integer.MAX_VALUE;
            int i2 = 0;
            for (int i3 : subscreenBrightnessController.mView.mBrightnessLevels) {
                int iAbs = Math.abs(i3 - progress);
                if (iAbs < i) {
                    i2 = i3;
                    i = iAbs;
                }
            }
            progress = i2;
        }
        subscreenBrightnessController.mSettingsHelper.setSubscreenBrightness(progress);
    }

    /* renamed from: -$$Nest$mupdateSlider, reason: not valid java name */
    public static void m2896$$Nest$mupdateSlider(final SubscreenBrightnessController subscreenBrightnessController, float f) {
        StringBuilder sb = new StringBuilder("mMinimumBacklight=");
        float f2 = subscreenBrightnessController.mMinimumBacklight;
        sb.append(f2);
        sb.append(" mMaximumBacklight=");
        float f3 = subscreenBrightnessController.mMaximumBacklight;
        sb.append(f3);
        sb.append(" mBrightnessMin=");
        sb.append(subscreenBrightnessController.mBrightnessMin);
        sb.append(" mBrightnessMax=");
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(subscreenBrightnessController.mBrightnessMax, "SubscreenBrightnessController", sb);
        int autoBrightnessTransitionTime = subscreenBrightnessController.mSettingsHelper.getAutoBrightnessTransitionTime();
        ListPopupWindow$$ExternalSyntheticOutline0.m(autoBrightnessTransitionTime, "animation duration: ", "SubscreenBrightnessController");
        if (subscreenBrightnessController.mSliderAnimationDuration != autoBrightnessTransitionTime) {
            if (autoBrightnessTransitionTime < 0) {
                autoBrightnessTransitionTime = 0;
            }
            subscreenBrightnessController.mSliderAnimationDuration = autoBrightnessTransitionTime;
        }
        int iRound = Math.round((f * f3) - f2);
        Log.d("SubscreenBrightnessController", "updateSlider() = " + iRound + ", brightnessValue = " + f + ", min = " + f2 + " max = " + f3);
        if (QpRune.QUICK_SUBSCREEN_PANEL && subscreenBrightnessController.mBrightnessDialog != null) {
            Log.d("SubscreenBrightnessController", "updateSlider() - BrightnessDialog resetTimer()");
        }
        boolean z = mControlValueInitialized;
        SubroomBrightnessSettingsView subroomBrightnessSettingsView = subscreenBrightnessController.mView;
        if (!z || subroomBrightnessSettingsView.getVisibility() != 0) {
            subroomBrightnessSettingsView.setProgress(iRound);
            mControlValueInitialized = true;
            return;
        }
        ValueAnimator valueAnimator = subscreenBrightnessController.mSliderAnimator;
        if (valueAnimator != null && valueAnimator.isStarted()) {
            subscreenBrightnessController.mSliderAnimator.cancel();
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(subroomBrightnessSettingsView.mSeekBar.getProgress(), iRound);
        subscreenBrightnessController.mSliderAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qp.SubscreenBrightnessController$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SubscreenBrightnessController subscreenBrightnessController2 = this.f$0;
                boolean z2 = SubscreenBrightnessController.mExternalChange;
                subscreenBrightnessController2.getClass();
                SubscreenBrightnessController.mExternalChange = true;
                subscreenBrightnessController2.mView.setProgress(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                SubscreenBrightnessController.mExternalChange = false;
            }
        });
        subscreenBrightnessController.mSliderAnimator.setDuration(subscreenBrightnessController.BRIGHTNESS_DIALOG_TAG != "brightness_dialog_subscreen" ? subscreenBrightnessController.mSliderAnimationDuration : 0L);
        subscreenBrightnessController.mSliderAnimator.start();
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.qp.SubscreenBrightnessController$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.qp.SubscreenBrightnessController$2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [android.os.Handler, com.android.systemui.qp.SubscreenBrightnessController$3] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.qp.SubscreenBrightnessController$4] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qp.SubscreenBrightnessController$6] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qp.SubscreenBrightnessController$7] */
    public SubscreenBrightnessController(Context context, SubroomBrightnessSettingsView subroomBrightnessSettingsView) {
        super(subroomBrightnessSettingsView);
        this.mBrightnessMin = 0.0f;
        this.mBrightnessMax = 1.0f;
        this.mSliderAnimationDuration = 0;
        this.BRIGHTNESS_DIALOG_TAG = null;
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.qp.SubscreenBrightnessController.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                Display display = SubscreenBrightnessController.this.mDisplay;
                if (display == null) {
                    Log.d("SubscreenBrightnessController", "mDisplay is null ");
                    return;
                }
                BrightnessInfo brightnessInfo = display.getBrightnessInfo();
                if (brightnessInfo == null) {
                    Log.d("SubscreenBrightnessController", "info is null ");
                    return;
                }
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m(brightnessInfo.brightness, "SubscreenBrightnessController", new StringBuilder("info.brightness:"));
                if (SubscreenBrightnessController.mTracking) {
                    return;
                }
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                float f = subscreenBrightnessController.mBrightness;
                float f2 = brightnessInfo.brightness;
                if (f != f2) {
                    subscreenBrightnessController.mBrightness = f2;
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m(SubscreenBrightnessController.this.mBrightness, "SubscreenBrightnessController", new StringBuilder("onDisplayChanged mBrightness:"));
                    SubscreenBrightnessController subscreenBrightnessController2 = SubscreenBrightnessController.this;
                    subscreenBrightnessController2.mBackgroundHandler.post(subscreenBrightnessController2.mUpdateSliderRunnable);
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
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m(brightnessInfo.brightnessMinimum, "SubscreenBrightnessController", sb);
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
                        SubscreenBrightnessController.m2896$$Nest$mupdateSlider(subscreenBrightnessController, Float.intBitsToFloat(message.arg1));
                    } else if (i != 10) {
                        super.handleMessage(message);
                    } else {
                        if (message.arg1 == 0) {
                            z = false;
                        }
                        subscreenBrightnessController.getClass();
                        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("updateHighBrightnessModeEnter : ", ", slider is ", z);
                        SubroomBrightnessSettingsView subroomBrightnessSettingsView2 = subscreenBrightnessController.mView;
                        sbM.append(subroomBrightnessSettingsView2.mSeekBar);
                        Log.d("SubscreenBrightnessController", sbM.toString());
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
                        SubscreenBrightnessController.this.mView.setThumbScale(i);
                    } else if (subroomBrightnessSettingsView3 != null) {
                        subroomBrightnessSettingsView3.setDualSeekBarResources(false, subscreenBrightnessController.mDetailActivity);
                        SubscreenBrightnessController.this.mView.setThumbScale(i);
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
                SubscreenBrightnessController.mTracking = true;
                ValueAnimator valueAnimator = SubscreenBrightnessController.this.mView.mThumbAnimator;
                if (valueAnimator != null) {
                    valueAnimator.start();
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar) {
                SubscreenBrightnessController.mTracking = false;
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                subscreenBrightnessController.onChanged(subscreenBrightnessController.mView.mSeekBar.getProgress(), false, true);
                SubscreenBrightnessController.m2895$$Nest$monProgressSnap(SubscreenBrightnessController.this, seekBar);
                ValueAnimator valueAnimator = SubscreenBrightnessController.this.mView.mThumbAnimator;
                if (valueAnimator != null) {
                    valueAnimator.reverse();
                }
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
        this.mStartListeningRunnable = new Runnable() { // from class: com.android.systemui.qp.SubscreenBrightnessController.6
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
                subscreenBrightnessController2.mDisplayManager.registerDisplayListener(subscreenBrightnessController2.mDisplayListener, subscreenBrightnessController2.mHandler, 4L, 1L);
                SubscreenBrightnessController subscreenBrightnessController3 = SubscreenBrightnessController.this;
                SubroomBrightnessSettingsView subroomBrightnessSettingsView2 = subscreenBrightnessController3.mView;
                subroomBrightnessSettingsView2.mSeekBar.setOnSeekBarChangeListener(subscreenBrightnessController3.mSeekListener);
                BrightnessObserver brightnessObserver = SubscreenBrightnessController.this.mBrightnessObserver;
                brightnessObserver.mCr.unregisterContentObserver(brightnessObserver);
                brightnessObserver.mCr.registerContentObserver(SubscreenBrightnessController.HIGH_BRIGHTNESS_MODE_ENTER_URI, false, brightnessObserver, -1);
            }
        };
        this.mStopListeningRunnable = new Runnable() { // from class: com.android.systemui.qp.SubscreenBrightnessController.7
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
        CarrierTextManager$$ExternalSyntheticOutline0.m(new StringBuilder("onChanged: mExternalChange="), mExternalChange, " stopTracking=", z2, "SubscreenBrightnessController");
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
            this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mHandler, 4L, 1L);
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
