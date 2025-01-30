package com.android.systemui.p014qp;

import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
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
import com.android.systemui.R;
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.android.systemui.settings.brightness.BrightnessDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.SystemUIDialogUtils;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenBrightnessController extends ViewController {
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
    public final C19931 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public final HandlerC19953 mHandler;
    public boolean mListening;
    public final float mMaximumBacklight;
    public final float mMinimumBacklight;
    public final C19964 mSeekListener;
    public final SettingsHelper mSettingsHelper;
    public int mSliderAnimationDuration;
    public ValueAnimator mSliderAnimator;
    public final RunnableC19997 mStartListeningRunnable;
    public final RunnableC20008 mStopListeningRunnable;
    public final RunnableC19942 mUpdateSliderRunnable;
    public SystemUIDialog mUsingHighBrightnessDialog;
    public final SubroomBrightnessSettingsView mView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BrightnessObserver extends ContentObserver {
        public final ContentResolver mCr;
        public final RunnableC20011 mHighBrightnessModeEnterRunnable;

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
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("UPDATE_HIGH_BRIGHTNESS_MODE = ", r0, "SubscreenBrightnessController");
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

    /* renamed from: -$$Nest$mupdateSlider, reason: not valid java name */
    public static void m1615$$Nest$mupdateSlider(final SubscreenBrightnessController subscreenBrightnessController, float f) {
        StringBuilder sb = new StringBuilder("mMinimumBacklight=");
        float f2 = subscreenBrightnessController.mMinimumBacklight;
        sb.append(f2);
        sb.append(" mMaximumBacklight=");
        float f3 = subscreenBrightnessController.mMaximumBacklight;
        sb.append(f3);
        sb.append(" mBrightnessMin=");
        sb.append(subscreenBrightnessController.mBrightnessMin);
        sb.append(" mBrightnessMax=");
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(sb, subscreenBrightnessController.mBrightnessMax, "SubscreenBrightnessController");
        int intValue = subscreenBrightnessController.mSettingsHelper.mItemLists.get("auto_brightness_transition_time").getIntValue();
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("animation duration: ", intValue, "SubscreenBrightnessController");
        if (subscreenBrightnessController.mSliderAnimationDuration != intValue) {
            if (intValue < 0) {
                intValue = 0;
            }
            subscreenBrightnessController.mSliderAnimationDuration = intValue;
        }
        int round = Math.round((f * f3) - f2);
        Log.d("SubscreenBrightnessController", "updateSlider() = " + round + ", brightnessValue = " + f + ", min = " + f2 + " max = " + f3);
        if (QpRune.QUICK_PANEL_SUBSCREEN && subscreenBrightnessController.mBrightnessDialog != null) {
            Log.d("SubscreenBrightnessController", "updateSlider() - BrightnessDialog resetTimer()");
            BrightnessDialog brightnessDialog = subscreenBrightnessController.mBrightnessDialog;
            BrightnessDialog.CountDownTimerC23952 countDownTimerC23952 = brightnessDialog.mTimer;
            if (countDownTimerC23952 != null) {
                countDownTimerC23952.cancel();
            }
            BrightnessDialog.CountDownTimerC23952 countDownTimerC239522 = brightnessDialog.mTimer;
            if (countDownTimerC239522 != null) {
                countDownTimerC239522.cancel();
            }
            BrightnessDialog.CountDownTimerC23952 countDownTimerC239523 = brightnessDialog.mTimer;
            if (countDownTimerC239523 != null) {
                countDownTimerC239523.start();
            }
        }
        boolean z = mControlValueInitialized;
        SubroomBrightnessSettingsView subroomBrightnessSettingsView = subscreenBrightnessController.mView;
        if (!z || subroomBrightnessSettingsView.getVisibility() != 0) {
            subroomBrightnessSettingsView.setProgress(round);
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
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qp.SubscreenBrightnessController$7] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qp.SubscreenBrightnessController$8] */
    public SubscreenBrightnessController(Context context, SubroomBrightnessSettingsView subroomBrightnessSettingsView) {
        super(subroomBrightnessSettingsView);
        this.mBrightnessMin = 0.0f;
        this.mBrightnessMax = 1.0f;
        this.mSliderAnimationDuration = 0;
        this.BRIGHTNESS_DIALOG_TAG = null;
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
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(new StringBuilder("info.brightness:"), brightnessInfo.brightness, "SubscreenBrightnessController");
                if (SubscreenBrightnessController.mTracking) {
                    return;
                }
                SubscreenBrightnessController subscreenBrightnessController2 = SubscreenBrightnessController.this;
                float f = subscreenBrightnessController2.mBrightness;
                float f2 = brightnessInfo.brightness;
                if (f != f2) {
                    subscreenBrightnessController2.mBrightness = f2;
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(new StringBuilder("onDisplayChanged mBrightness:"), SubscreenBrightnessController.this.mBrightness, "SubscreenBrightnessController");
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
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(sb, brightnessInfo.brightnessMinimum, "SubscreenBrightnessController");
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
                    if (i == 1) {
                        SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                        float intBitsToFloat = Float.intBitsToFloat(message.arg1);
                        int i2 = message.arg2;
                        SubscreenBrightnessController.m1615$$Nest$mupdateSlider(subscreenBrightnessController, intBitsToFloat);
                    } else if (i != 10) {
                        super.handleMessage(message);
                    } else {
                        SubscreenBrightnessController subscreenBrightnessController2 = SubscreenBrightnessController.this;
                        if (message.arg1 == 0) {
                            z = false;
                        }
                        subscreenBrightnessController2.updateHighBrightnessModeEnter(z);
                    }
                } finally {
                    SubscreenBrightnessController.mExternalChange = false;
                }
            }
        };
        this.mHandler = r0;
        this.mSeekListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.qp.SubscreenBrightnessController.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Log.d("SubscreenBrightnessController", "onProgressChanged");
                SubscreenBrightnessController.this.onChanged(i, SubscreenBrightnessController.mTracking, false);
                boolean z2 = QpRune.QUICK_PANEL_SUBSCREEN;
                if (z2) {
                    SubscreenBrightnessController.this.getClass();
                    ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).getClass();
                    if (!(z2 && ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("sub_screen_brightness_mode").getIntValue() != 0) && SubscreenBrightnessController.mUsingHighBrightnessDialogEnabled && SubscreenBrightnessController.mTracking) {
                        final SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                        SubroomBrightnessSettingsView subroomBrightnessSettingsView2 = subscreenBrightnessController.mView;
                        if (subroomBrightnessSettingsView2 != null && subroomBrightnessSettingsView2.mDualSeekBarThreshold <= i) {
                            if (subscreenBrightnessController.mUsingHighBrightnessDialog == null) {
                                subscreenBrightnessController.mUsingHighBrightnessDialog = SystemUIDialogUtils.createSystemUIDialogUtils(2132018521, subscreenBrightnessController.getContext());
                                String string = subscreenBrightnessController.getContext().getResources().getString(R.string.sec_brightness_using_high_brightness_dialog_message_support_hbm);
                                subscreenBrightnessController.mUsingHighBrightnessDialog.setTitle(subscreenBrightnessController.getContext().getResources().getString(R.string.sec_brightness_using_high_brightness_dialog_title));
                                subscreenBrightnessController.mUsingHighBrightnessDialog.setMessage(string);
                                subscreenBrightnessController.mUsingHighBrightnessDialog.setPositiveButton(R.string.sec_brightness_using_high_brightness_dialog_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qp.SubscreenBrightnessController$$ExternalSyntheticLambda1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i2) {
                                        Settings.System.semPutIntForUser(SubscreenBrightnessController.this.getContext().getContentResolver(), "sub_screen_brightness_mode", 1, -2);
                                    }
                                });
                                subscreenBrightnessController.mUsingHighBrightnessDialog.setNegativeButton(R.string.sec_brightness_using_high_brightness_dialog_negative_button, new SubscreenBrightnessController$$ExternalSyntheticLambda2());
                                subscreenBrightnessController.mUsingHighBrightnessDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qp.SubscreenBrightnessController.6
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        SubscreenBrightnessController.this.mUsingHighBrightnessDialog = null;
                                        boolean z3 = false;
                                        SubscreenBrightnessController.mUsingHighBrightnessDialogEnabled = false;
                                        ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).getClass();
                                        if (QpRune.QUICK_PANEL_SUBSCREEN && ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("sub_screen_brightness_mode").getIntValue() != 0) {
                                            z3 = true;
                                        }
                                        if (!z3) {
                                            SubroomBrightnessSettingsView subroomBrightnessSettingsView3 = SubscreenBrightnessController.this.mView;
                                            subroomBrightnessSettingsView3.setProgress(subroomBrightnessSettingsView3.mDualSeekBarThreshold + 1);
                                        }
                                        Settings.System.putIntForUser(SubscreenBrightnessController.this.mSettingsHelper.mContext.getContentResolver(), "shown_max_brightness_dialog", 1, -2);
                                    }
                                });
                                subscreenBrightnessController.mUsingHighBrightnessDialog.show();
                                return;
                            }
                            return;
                        }
                        SystemUIDialog systemUIDialog = subscreenBrightnessController.mUsingHighBrightnessDialog;
                        if (systemUIDialog != null && systemUIDialog.isShowing()) {
                            SubscreenBrightnessController.this.mUsingHighBrightnessDialog.dismiss();
                        }
                    }
                    SubscreenBrightnessController subscreenBrightnessController2 = SubscreenBrightnessController.this;
                    SubroomBrightnessSettingsView subroomBrightnessSettingsView3 = subscreenBrightnessController2.mView;
                    if (subroomBrightnessSettingsView3 != null && subroomBrightnessSettingsView3.mDualSeekBarThreshold <= i && SubscreenBrightnessController.mTracking) {
                        subroomBrightnessSettingsView3.setDualSeekBarResources(true, subscreenBrightnessController2.mDetailActivity);
                    } else if (subroomBrightnessSettingsView3 != null) {
                        subroomBrightnessSettingsView3.setDualSeekBarResources(false, subscreenBrightnessController2.mDetailActivity);
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
                SubscreenBrightnessController subscreenBrightnessController2 = SubscreenBrightnessController.this;
                subscreenBrightnessController2.getClass();
                int progress = seekBar.getProgress();
                if (!QpRune.QUICK_PANEL_SUBSCREEN) {
                    int i = Integer.MAX_VALUE;
                    int i2 = 0;
                    for (int i3 : subscreenBrightnessController2.mView.mBrightnessLevels) {
                        int abs = Math.abs(i3 - progress);
                        if (abs < i) {
                            i2 = i3;
                            i = abs;
                        }
                    }
                    progress = i2;
                }
                Settings.System.putIntForUser(subscreenBrightnessController2.mSettingsHelper.mContext.getContentResolver(), "sub_screen_brightness", progress, -2);
                SubscreenBrightnessController subscreenBrightnessController3 = SubscreenBrightnessController.this;
                subscreenBrightnessController3.mView.setDualSeekBarResources(false, subscreenBrightnessController3.mDetailActivity);
                if (!QpRune.QUICK_PANEL_SUBSCREEN) {
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE2009");
                } else {
                    if (SubscreenBrightnessController.this.mDetailActivity) {
                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE2025");
                        return;
                    }
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE2024");
                    SystemUIAnalytics.sendEventLog(seekBar.getProgress() + 1, SystemUIAnalytics.sCurrentScreenID, "QPDS2025");
                }
            }
        };
        this.mStartListeningRunnable = new Runnable() { // from class: com.android.systemui.qp.SubscreenBrightnessController.7
            @Override // java.lang.Runnable
            public final void run() {
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                if (subscreenBrightnessController.mListening || !QpRune.QUICK_PANEL_SUBSCREEN) {
                    return;
                }
                subscreenBrightnessController.mListening = true;
                SubscreenUtil subscreenUtil = (SubscreenUtil) Dependency.get(SubscreenUtil.class);
                Context context2 = SubscreenBrightnessController.this.mContext;
                subscreenUtil.getClass();
                subscreenBrightnessController.mDisplay = SubscreenUtil.getSubDisplay(context2);
                SubscreenBrightnessController subscreenBrightnessController2 = SubscreenBrightnessController.this;
                subscreenBrightnessController2.mDisplayManager.registerDisplayListener(subscreenBrightnessController2.mDisplayListener, subscreenBrightnessController2.mHandler, 8L);
                SubscreenBrightnessController subscreenBrightnessController3 = SubscreenBrightnessController.this;
                subscreenBrightnessController3.mView.mSeekBar.setOnSeekBarChangeListener(subscreenBrightnessController3.mSeekListener);
                BrightnessObserver brightnessObserver = SubscreenBrightnessController.this.mBrightnessObserver;
                brightnessObserver.mCr.unregisterContentObserver(brightnessObserver);
                brightnessObserver.mCr.registerContentObserver(SubscreenBrightnessController.HIGH_BRIGHTNESS_MODE_ENTER_URI, false, brightnessObserver, -1);
            }
        };
        this.mStopListeningRunnable = new Runnable() { // from class: com.android.systemui.qp.SubscreenBrightnessController.8
            @Override // java.lang.Runnable
            public final void run() {
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                if (subscreenBrightnessController.mListening && QpRune.QUICK_PANEL_SUBSCREEN) {
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
        this.mBackgroundHandler = (Handler) Dependency.get(Dependency.BG_HANDLER);
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mMinimumBacklight = powerManager.getMinimumScreenBrightnessSetting();
        this.mMaximumBacklight = powerManager.getMaximumScreenBrightnessSetting();
        this.mSettingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        this.mBrightnessObserver = new BrightnessObserver(r0);
    }

    public final void onChanged(int i, boolean z, boolean z2) {
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(new StringBuilder("onChanged: mExternalChange="), mExternalChange, " stopTracking=", z2, "SubscreenBrightnessController");
        if (mExternalChange) {
            return;
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(RowView$$ExternalSyntheticOutline0.m49m("tracking : ", z, "mIsHighBrightnessMode : "), mIsHighBrightnessMode, "SubscreenBrightnessController");
        if (!z && mIsHighBrightnessMode && this.mSettingsHelper.mItemLists.get("sub_screen_brightness_mode").getIntValue() != 0) {
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
                Settings.System.putIntForUser(SubscreenBrightnessController.this.mSettingsHelper.mContext.getContentResolver(), "auto_brightness_transition_time", -1, -2);
                SubscreenBrightnessController subscreenBrightnessController = SubscreenBrightnessController.this;
                subscreenBrightnessController.mDisplayManager.setBrightness(subscreenBrightnessController.mDisplayId, f);
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        SubroomBrightnessSettingsView subroomBrightnessSettingsView = this.mView;
        subroomBrightnessSettingsView.mSeekBar.setOnSeekBarChangeListener(this.mSeekListener);
        ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).getClass();
        this.mDisplay = SubscreenUtil.getSubDisplay(this.mContext);
        Log.d("SubscreenBrightnessController", "mDisplay = :" + this.mDisplay);
        Display display = this.mDisplay;
        if (display != null) {
            this.mDisplayId = display.getDisplayId();
        }
        SettingsHelper settingsHelper = this.mSettingsHelper;
        int intValue = settingsHelper.mItemLists.get("sub_screen_brightness").getIntValue();
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("setBrightness - brightness ", intValue, "SubscreenBrightnessController");
        Settings.System.putIntForUser(settingsHelper.mContext.getContentResolver(), "sub_screen_brightness", intValue, -2);
        subroomBrightnessSettingsView.setProgress(intValue);
        boolean z = QpRune.QUICK_PANEL_SUBSCREEN;
        if (z) {
            mUsingHighBrightnessDialogEnabled = settingsHelper.mItemLists.get("shown_max_brightness_dialog").getIntValue() == 0;
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
        boolean z = QpRune.QUICK_PANEL_SUBSCREEN;
        if (z) {
            this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        }
        if (z) {
            BrightnessObserver brightnessObserver = this.mBrightnessObserver;
            brightnessObserver.mCr.unregisterContentObserver(brightnessObserver);
        }
    }

    public final void updateHighBrightnessModeEnter(boolean z) {
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("updateHighBrightnessModeEnter : ", z, ", slider is ");
        SubroomBrightnessSettingsView subroomBrightnessSettingsView = this.mView;
        m49m.append(subroomBrightnessSettingsView.mSeekBar);
        Log.d("SubscreenBrightnessController", m49m.toString());
        SubScreenBrightnessToggleSeekBar subScreenBrightnessToggleSeekBar = subroomBrightnessSettingsView.mSeekBar;
        if (subScreenBrightnessToggleSeekBar != null) {
            subScreenBrightnessToggleSeekBar.getClass();
            SubScreenBrightnessToggleSeekBar.mHighBrightnessModeEnter = z;
        }
    }
}
