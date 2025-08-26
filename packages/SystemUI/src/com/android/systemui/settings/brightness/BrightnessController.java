package com.android.systemui.settings.brightness;

import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.Log;
import android.util.MathUtils;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.display.BrightnessUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.settings.brightness.ToggleSlider;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.concurrent.Executor;
import kotlin.Unit;

/* loaded from: classes3.dex */
public class BrightnessController implements ToggleSlider.Listener {
    public static final Uri BRIGHTNESS_MODE_URI = Settings.System.getUriFor("screen_brightness_mode");
    public volatile boolean mAutomatic;
    public final Handler mBackgroundHandler;
    public final BrightnessObserver mBrightnessObserver;
    public final Context mContext;
    public final ToggleSlider mControl;
    public boolean mControlValueInitialized;
    public final int mDisplayId;
    public final DisplayManager mDisplayManager;
    public final DisplayTracker mDisplayTracker;
    public boolean mExternalChange;
    public final AnonymousClass7 mHandlerCallback;
    public volatile boolean mIsVrModeEnabled;
    public boolean mListening;
    public final LogBuffer mLogBuffer;
    public final Executor mMainExecutor;
    public final Handler mMainHandler;
    public final SecBrightnessController mSecBrightnessController;
    public final SecureSettings mSecureSettings;
    public ValueAnimator mSliderAnimator;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final IVrManager mVrManager;
    public final DisplayTracker.Callback mBrightnessListener = new DisplayTracker.Callback() { // from class: com.android.systemui.settings.brightness.BrightnessController.1
        @Override // com.android.systemui.settings.DisplayTracker.Callback
        public final void onDisplayChanged(int i) {
            BrightnessController brightnessController = BrightnessController.this;
            brightnessController.mBackgroundHandler.post(brightnessController.mUpdateSliderRunnable);
        }
    };
    public boolean mTrackingTouch = false;
    public float mBrightnessMin = 0.0f;
    public float mBrightnessMax = 1.0f;
    public boolean mIsBrightnessOverriddenByWindow = false;
    public final AnonymousClass2 mStartListeningRunnable = new Runnable() { // from class: com.android.systemui.settings.brightness.BrightnessController.2
        @Override // java.lang.Runnable
        public final void run() {
            BrightnessControllerObserver brightnessControllerObserver;
            ContentResolver contentResolver;
            BrightnessController brightnessController = BrightnessController.this;
            if (brightnessController.mListening) {
                return;
            }
            brightnessController.mListening = true;
            IVrManager iVrManager = brightnessController.mVrManager;
            if (iVrManager != null) {
                try {
                    iVrManager.registerListener(brightnessController.mVrStateCallbacks);
                    BrightnessController brightnessController2 = BrightnessController.this;
                    brightnessController2.mIsVrModeEnabled = brightnessController2.mVrManager.getVrModeState();
                } catch (RemoteException e) {
                    Log.e("CentralSurfaces.BrightnessController", "Failed to register VR mode state listener: ", e);
                }
            }
            BrightnessObserver brightnessObserver = BrightnessController.this.mBrightnessObserver;
            if (!brightnessObserver.mObserving) {
                brightnessObserver.mObserving = true;
                BrightnessController.this.mSecureSettings.registerContentObserverForUserAsync(BrightnessController.BRIGHTNESS_MODE_URI, false, (ContentObserver) brightnessObserver, -1);
            }
            SecBrightnessController secBrightnessController = BrightnessController.this.mSecBrightnessController;
            if (secBrightnessController != null && (brightnessControllerObserver = secBrightnessController.brightnessControllerObserver) != null && (contentResolver = brightnessControllerObserver.context.getContentResolver()) != null) {
                contentResolver.unregisterContentObserver(brightnessControllerObserver);
                SecBrightnessController.Companion.getClass();
                contentResolver.registerContentObserver(SecBrightnessController.USING_HIGH_BRIGHTNESS_DIALOG_URI, false, brightnessControllerObserver, -1);
                contentResolver.registerContentObserver(SecBrightnessController.HIGH_BRIGHTNESS_MODE_ENTER_URI, false, brightnessControllerObserver, -1);
                contentResolver.registerContentObserver(SecBrightnessController.SCREEN_DISPLAY_OUTDOOR_MODE_URI, false, brightnessControllerObserver, -1);
                contentResolver.registerContentObserver(SecBrightnessController.SEC_AUTO_BRIGHTNESS_TRANSITION_TIME_URI, false, brightnessControllerObserver, -1);
                contentResolver.registerContentObserver(SecBrightnessController.BRIGHTNESS_MODE_URI, false, brightnessControllerObserver, -1);
            }
            BrightnessController brightnessController3 = BrightnessController.this;
            ((DisplayTrackerImpl) brightnessController3.mDisplayTracker).addBrightnessChangeCallback(brightnessController3.mBrightnessListener, new HandlerExecutor(BrightnessController.this.mMainHandler));
            BrightnessController brightnessController4 = BrightnessController.this;
            ((UserTrackerImpl) brightnessController4.mUserTracker).addCallback(brightnessController4.mUserChangedCallback, brightnessController4.mMainExecutor);
            BrightnessController.this.mUpdateModeRunnable.run();
            BrightnessController.this.mUpdateSliderRunnable.run();
            SecBrightnessController secBrightnessController2 = BrightnessController.this.mSecBrightnessController;
            if (secBrightnessController2 != null) {
                secBrightnessController2.handler.obtainMessage(9).sendToTarget();
            }
            BrightnessController.this.mMainHandler.sendEmptyMessage(2);
        }
    };
    public final AnonymousClass3 mStopListeningRunnable = new Runnable() { // from class: com.android.systemui.settings.brightness.BrightnessController.3
        @Override // java.lang.Runnable
        public final void run() {
            BrightnessControllerObserver brightnessControllerObserver;
            ContentResolver contentResolver;
            BrightnessController brightnessController = BrightnessController.this;
            if (brightnessController.mListening) {
                brightnessController.mListening = false;
                IVrManager iVrManager = brightnessController.mVrManager;
                if (iVrManager != null) {
                    try {
                        iVrManager.unregisterListener(brightnessController.mVrStateCallbacks);
                    } catch (RemoteException e) {
                        Log.e("CentralSurfaces.BrightnessController", "Failed to unregister VR mode state listener: ", e);
                    }
                }
                BrightnessObserver brightnessObserver = BrightnessController.this.mBrightnessObserver;
                BrightnessController.this.mSecureSettings.unregisterContentObserverAsync(brightnessObserver);
                brightnessObserver.mObserving = false;
                SecBrightnessController secBrightnessController = BrightnessController.this.mSecBrightnessController;
                if (secBrightnessController != null && (brightnessControllerObserver = secBrightnessController.brightnessControllerObserver) != null && (contentResolver = brightnessControllerObserver.context.getContentResolver()) != null) {
                    contentResolver.unregisterContentObserver(brightnessControllerObserver);
                }
                BrightnessController brightnessController2 = BrightnessController.this;
                ((DisplayTrackerImpl) brightnessController2.mDisplayTracker).removeCallback(brightnessController2.mBrightnessListener);
                BrightnessController brightnessController3 = BrightnessController.this;
                ((UserTrackerImpl) brightnessController3.mUserTracker).removeCallback(brightnessController3.mUserChangedCallback);
                BrightnessController.this.mMainHandler.sendEmptyMessage(3);
            }
        }
    };
    public final AnonymousClass4 mUpdateModeRunnable = new AnonymousClass4();
    public final AnonymousClass5 mUpdateSliderRunnable = new AnonymousClass5();
    public final AnonymousClass6 mVrStateCallbacks = new IVrStateCallbacks.Stub() { // from class: com.android.systemui.settings.brightness.BrightnessController.6
        public final void onVrStateChanged(boolean z) {
            BrightnessController.this.mMainHandler.obtainMessage(4, z ? 1 : 0, 0).sendToTarget();
        }
    };

    /* renamed from: com.android.systemui.settings.brightness.BrightnessController$10, reason: invalid class name */
    public class AnonymousClass10 implements Runnable {
        public AnonymousClass10() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            int userId = ((UserTrackerImpl) BrightnessController.this.mUserTracker).getUserId();
            RestrictedLockUtils.EnforcedAdmin enforcedAdminCheckIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(BrightnessController.this.mContext, "no_config_brightness", userId);
            if (enforcedAdminCheckIfRestrictionEnforced == null && RestrictedLockUtilsInternal.hasBaseUserRestriction(BrightnessController.this.mContext, "no_config_brightness", userId)) {
                enforcedAdminCheckIfRestrictionEnforced = new RestrictedLockUtils.EnforcedAdmin();
            }
            BrightnessController.this.mControl.setEnforcedAdmin(enforcedAdminCheckIfRestrictionEnforced);
        }
    }

    /* renamed from: com.android.systemui.settings.brightness.BrightnessController$4, reason: invalid class name */
    public class AnonymousClass4 implements Runnable {
        public AnonymousClass4() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            int intForUser = Settings.System.getIntForUser(BrightnessController.this.mContext.getContentResolver(), "screen_brightness_mode", 0, ((UserTrackerImpl) BrightnessController.this.mUserTracker).getUserId());
            BrightnessController.this.mAutomatic = intForUser != 0;
        }
    }

    /* renamed from: com.android.systemui.settings.brightness.BrightnessController$5, reason: invalid class name */
    public class AnonymousClass5 implements Runnable {
        public AnonymousClass5() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            boolean z = BrightnessController.this.mIsVrModeEnabled;
            BrightnessInfo brightnessInfo = BrightnessController.this.getBrightnessInfo();
            if (brightnessInfo == null) {
                return;
            }
            BrightnessController brightnessController = BrightnessController.this;
            brightnessController.getClass();
            brightnessController.mBrightnessMax = brightnessInfo.brightnessMaximum;
            brightnessController.mBrightnessMin = brightnessInfo.brightnessMinimum;
            brightnessController.mIsBrightnessOverriddenByWindow = brightnessInfo.isBrightnessOverrideByWindow;
            BrightnessController.this.mMainHandler.obtainMessage(1, Float.floatToIntBits(brightnessInfo.brightness), z ? 1 : 0).sendToTarget();
        }
    }

    public class BrightnessObserver extends ContentObserver {
        public boolean mObserving;

        public BrightnessObserver(Handler handler) {
            super(handler);
            this.mObserving = false;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (z) {
                return;
            }
            if (BrightnessController.BRIGHTNESS_MODE_URI.equals(uri)) {
                BrightnessController brightnessController = BrightnessController.this;
                brightnessController.mBackgroundHandler.post(brightnessController.mUpdateModeRunnable);
                BrightnessController brightnessController2 = BrightnessController.this;
                brightnessController2.mBackgroundHandler.post(brightnessController2.mUpdateSliderRunnable);
                return;
            }
            BrightnessController brightnessController3 = BrightnessController.this;
            brightnessController3.mBackgroundHandler.post(brightnessController3.mUpdateModeRunnable);
            BrightnessController brightnessController4 = BrightnessController.this;
            brightnessController4.mBackgroundHandler.post(brightnessController4.mUpdateSliderRunnable);
        }
    }

    public interface Factory {
        BrightnessController create(ToggleSlider toggleSlider);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.settings.brightness.BrightnessController$2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.settings.brightness.BrightnessController$3] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.settings.brightness.BrightnessController$6] */
    /* JADX WARN: Type inference failed for: r0v7, types: [android.os.Handler$Callback, com.android.systemui.settings.brightness.BrightnessController$7] */
    public BrightnessController(Context context, ToggleSlider toggleSlider, UserTracker userTracker, DisplayTracker displayTracker, DisplayManager displayManager, SecureSettings secureSettings, LogBuffer logBuffer, IVrManager iVrManager, Executor executor, Looper looper, Handler handler) {
        ?? r0 = new Handler.Callback() { // from class: com.android.systemui.settings.brightness.BrightnessController.7
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                BrightnessController brightnessController = BrightnessController.this;
                brightnessController.mExternalChange = true;
                try {
                    int i = message.what;
                    if (i == 1) {
                        brightnessController.updateSlider(Float.intBitsToFloat(message.arg1));
                    } else if (i == 2) {
                        brightnessController.mControl.setOnChangedListener(brightnessController);
                    } else if (i == 3) {
                        brightnessController.mControl.setOnChangedListener(null);
                    } else if (i == 4) {
                        boolean z = message.arg1 != 0;
                        if (brightnessController.mIsVrModeEnabled != z) {
                            brightnessController.mIsVrModeEnabled = z;
                            brightnessController.mBackgroundHandler.post(brightnessController.mUpdateSliderRunnable);
                        }
                    } else if (!brightnessController.mSecBrightnessController.handleMessage(message)) {
                        return false;
                    }
                    return true;
                } finally {
                    BrightnessController.this.mExternalChange = false;
                }
            }
        };
        this.mHandlerCallback = r0;
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.settings.brightness.BrightnessController.8
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                BrightnessController brightnessController = BrightnessController.this;
                brightnessController.mBackgroundHandler.post(brightnessController.mUpdateModeRunnable);
                brightnessController.mBackgroundHandler.post(brightnessController.mUpdateSliderRunnable);
            }
        };
        this.mContext = context;
        this.mControl = toggleSlider;
        this.mMainExecutor = executor;
        this.mBackgroundHandler = handler;
        this.mUserTracker = userTracker;
        this.mDisplayTracker = displayTracker;
        this.mSecureSettings = secureSettings;
        this.mDisplayId = context.getDisplayId();
        this.mDisplayManager = displayManager;
        this.mVrManager = iVrManager;
        this.mLogBuffer = logBuffer;
        Handler handler2 = new Handler(looper, r0);
        this.mMainHandler = handler2;
        this.mBrightnessObserver = new BrightnessObserver(handler2);
        this.mSecBrightnessController = new SecBrightnessController(handler2, toggleSlider, context, handler);
    }

    public BrightnessInfo getBrightnessInfo() {
        return this.mContext.getDisplay().getBrightnessInfo();
    }

    public final void logBrightnessChange(float f, boolean z, int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        BrightnessController$$ExternalSyntheticLambda1 brightnessController$$ExternalSyntheticLambda1 = new BrightnessController$$ExternalSyntheticLambda1();
        LogBuffer logBuffer = this.mLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("CentralSurfaces.BrightnessController", logLevel, brightnessController$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        double d = f;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.double1 = d;
        logMessageImpl.bool1 = z;
        Unit unit = Unit.INSTANCE;
        logBuffer.commit(logMessageObtain);
    }

    public final void onChanged(int i, boolean z, boolean z2) {
        boolean z3 = !this.mTrackingTouch && z;
        this.mTrackingTouch = z;
        if (this.mExternalChange || this.mIsBrightnessOverriddenByWindow) {
            return;
        }
        ValueAnimator valueAnimator = this.mSliderAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        int i2 = this.mAutomatic ? IKnoxCustomManager.Stub.TRANSACTION_getAutoCallPickupState : IKnoxCustomManager.Stub.TRANSACTION_setAutoCallPickupState;
        float f = this.mBrightnessMin;
        float f2 = this.mBrightnessMax;
        SecBrightnessController secBrightnessController = this.mSecBrightnessController;
        final float fMin = secBrightnessController != null ? (i + secBrightnessController.minimumBacklight) / secBrightnessController.maximumBacklight : MathUtils.min(BrightnessUtils.convertGammaToLinearFloat(f, f2, i), f2);
        if (z2) {
            MetricsLogger.action(this.mContext, i2, BrightnessSynchronizer.brightnessFloatToInt(fMin));
        }
        this.mDisplayManager.setTemporaryBrightness(this.mDisplayId, fMin);
        if (z3) {
            logBrightnessChange(fMin, true, this.mDisplayId);
        }
        if (z) {
            return;
        }
        AsyncTask.execute(new Runnable() { // from class: com.android.systemui.settings.brightness.BrightnessController.9
            @Override // java.lang.Runnable
            public final void run() {
                Settings.System.putIntForUser(BrightnessController.this.mSecBrightnessController.context.getContentResolver(), SettingsHelper.INDEX_AUTO_BRIGHTNESS_TRANSITION_TIME, -1, -2);
                BrightnessController brightnessController = BrightnessController.this;
                brightnessController.logBrightnessChange(fMin, false, brightnessController.mDisplayId);
                BrightnessController brightnessController2 = BrightnessController.this;
                brightnessController2.mDisplayManager.setBrightness(brightnessController2.mDisplayId, fMin);
            }
        });
    }

    public final void unregisterCallbacks() {
        AnonymousClass3 anonymousClass3 = this.mStopListeningRunnable;
        Handler handler = this.mBackgroundHandler;
        handler.removeCallbacks(anonymousClass3);
        handler.post(anonymousClass3);
        this.mControlValueInitialized = false;
    }

    public final void updateSlider(float f) {
        SecBrightnessDialogController secBrightnessDialogController;
        float f2 = this.mBrightnessMin;
        float f3 = this.mBrightnessMax;
        ValueAnimator valueAnimator = this.mSliderAnimator;
        if (valueAnimator != null && valueAnimator.isStarted()) {
            this.mSliderAnimator.cancel();
        }
        SecBrightnessController secBrightnessController = this.mSecBrightnessController;
        if (secBrightnessController != null) {
            int i = secBrightnessController.sliderAnimationDuration;
            int i2 = secBrightnessController.transitionTime;
            if (i != i2) {
                if (i2 < 0) {
                    i2 = 0;
                }
                secBrightnessController.sliderAnimationDuration = i2;
            }
            Log.d("SecBrightnessController", "updateSlider() - BrightnessDialog resetTimer()");
            BrightnessDialog brightnessDialog = secBrightnessController.brightnessDialog;
            if (brightnessDialog != null && (secBrightnessDialogController = brightnessDialog.secBrightnessDialogController) != null) {
                SecBrightnessDialogController$createTimer$1 secBrightnessDialogController$createTimer$1 = secBrightnessDialogController.countDownTimer;
                if (secBrightnessDialogController$createTimer$1 != null) {
                    secBrightnessDialogController$createTimer$1.cancel();
                }
                SecBrightnessDialogController$createTimer$1 secBrightnessDialogController$createTimer$12 = secBrightnessDialogController.countDownTimer;
                if (secBrightnessDialogController$createTimer$12 != null) {
                    secBrightnessDialogController$createTimer$12.cancel();
                }
                SecBrightnessDialogController$createTimer$1 secBrightnessDialogController$createTimer$13 = secBrightnessDialogController.countDownTimer;
                if (secBrightnessDialogController$createTimer$13 != null) {
                    secBrightnessDialogController$createTimer$13.start();
                }
            }
        }
        SecBrightnessController secBrightnessController2 = this.mSecBrightnessController;
        int iConvertLinearToGammaFloat = secBrightnessController2 != null ? (int) ((f * secBrightnessController2.maximumBacklight) - secBrightnessController2.minimumBacklight) : BrightnessUtils.convertLinearToGammaFloat(f, f2, f3);
        if (!this.mControlValueInitialized || (!this.mAutomatic && !this.mTrackingTouch)) {
            this.mControl.setValue(iConvertLinearToGammaFloat);
            this.mControlValueInitialized = true;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mControl.getValue(), iConvertLinearToGammaFloat);
        this.mSliderAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.settings.brightness.BrightnessController$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                BrightnessController brightnessController = this.f$0;
                brightnessController.mExternalChange = true;
                brightnessController.mControl.setValue(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                brightnessController.mExternalChange = false;
            }
        });
        this.mSliderAnimator.setDuration((this.mSecBrightnessController != null ? r0.sliderAnimationDuration / this.mControl.getMax() : 0L) * Math.abs(this.mControl.getValue() - iConvertLinearToGammaFloat));
        this.mSliderAnimator.start();
    }
}
