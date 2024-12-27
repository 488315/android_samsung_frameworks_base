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
import com.android.systemui.Flags;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.settings.brightness.ToggleSlider;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BrightnessController implements ToggleSlider.Listener {
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
    public final Executor mMainExecutor;
    public final Handler mMainHandler;
    public final SecBrightnessController mSecBrightnessController;
    public final SecureSettings mSecureSettings;
    public ValueAnimator mSliderAnimator;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final IVrManager mVrManager;
    public final AnonymousClass1 mBrightnessListener = new DisplayTracker.Callback() { // from class: com.android.systemui.settings.brightness.BrightnessController.1
        @Override // com.android.systemui.settings.DisplayTracker.Callback
        public final void onDisplayChanged(int i) {
            BrightnessController brightnessController = BrightnessController.this;
            brightnessController.mBackgroundHandler.post(brightnessController.mUpdateSliderRunnable);
        }
    };
    public boolean mTrackingTouch = false;
    public float mBrightnessMin = 0.0f;
    public float mBrightnessMax = 1.0f;
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
                BrightnessController.this.mSecureSettings.registerContentObserverForUserSync(BrightnessController.BRIGHTNESS_MODE_URI, false, (ContentObserver) brightnessObserver, -1);
            }
            SecBrightnessController secBrightnessController = BrightnessController.this.mSecBrightnessController;
            if (secBrightnessController != null && (brightnessControllerObserver = secBrightnessController.brightnessControllerObserver) != null && (contentResolver = brightnessControllerObserver.context.getContentResolver()) != null) {
                contentResolver.unregisterContentObserver(brightnessControllerObserver);
                SecBrightnessController.Companion.getClass();
                contentResolver.registerContentObserver(SecBrightnessController.USING_HIGH_BRIGHTNESS_DIALOG_URI, false, brightnessControllerObserver, -1);
                contentResolver.registerContentObserver(SecBrightnessController.SYSTEM_BRIGHTNESS_ENABLED_URI, false, brightnessControllerObserver, -1);
                contentResolver.registerContentObserver(SecBrightnessController.HIGH_BRIGHTNESS_MODE_ENTER_URI, false, brightnessControllerObserver, -1);
                contentResolver.registerContentObserver(SecBrightnessController.SCREEN_DISPLAY_OUTDOOR_MODE_URI, false, brightnessControllerObserver, -1);
                contentResolver.registerContentObserver(SecBrightnessController.SEC_AUTO_BRIGHTNESS_TRANSITION_TIME_URI, false, brightnessControllerObserver, -1);
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
                BrightnessController.this.mSecureSettings.unregisterContentObserverSync(brightnessObserver);
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.settings.brightness.BrightnessController$10, reason: invalid class name */
    public final class AnonymousClass10 implements Runnable {
        public AnonymousClass10() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            int userId = ((UserTrackerImpl) BrightnessController.this.mUserTracker).getUserId();
            RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(BrightnessController.this.mContext, "no_config_brightness", userId);
            Flags.FEATURE_FLAGS.getClass();
            if (checkIfRestrictionEnforced == null && RestrictedLockUtilsInternal.hasBaseUserRestriction(BrightnessController.this.mContext, "no_config_brightness", userId)) {
                checkIfRestrictionEnforced = new RestrictedLockUtils.EnforcedAdmin();
            }
            BrightnessController.this.mControl.setEnforcedAdmin(checkIfRestrictionEnforced);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.settings.brightness.BrightnessController$4, reason: invalid class name */
    public final class AnonymousClass4 implements Runnable {
        public AnonymousClass4() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            int intForUser = Settings.System.getIntForUser(BrightnessController.this.mContext.getContentResolver(), "screen_brightness_mode", 0, ((UserTrackerImpl) BrightnessController.this.mUserTracker).getUserId());
            BrightnessController.this.mAutomatic = intForUser != 0;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.settings.brightness.BrightnessController$5, reason: invalid class name */
    public final class AnonymousClass5 implements Runnable {
        public AnonymousClass5() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            boolean z = BrightnessController.this.mIsVrModeEnabled;
            BrightnessInfo brightnessInfo = BrightnessController.this.mContext.getDisplay().getBrightnessInfo();
            if (brightnessInfo == null) {
                return;
            }
            BrightnessController brightnessController = BrightnessController.this;
            brightnessController.mBrightnessMax = brightnessInfo.brightnessMaximum;
            brightnessController.mBrightnessMin = brightnessInfo.brightnessMinimum;
            BrightnessController.this.mMainHandler.obtainMessage(1, Float.floatToIntBits(brightnessInfo.brightness), z ? 1 : 0).sendToTarget();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class BrightnessObserver extends ContentObserver {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        BrightnessController create(ToggleSlider toggleSlider);
    }

    /* renamed from: -$$Nest$mupdateSlider, reason: not valid java name */
    public static void m2101$$Nest$mupdateSlider(final BrightnessController brightnessController, float f) {
        SecBrightnessDialogController secBrightnessDialogController;
        float f2 = brightnessController.mBrightnessMin;
        float f3 = brightnessController.mBrightnessMax;
        ValueAnimator valueAnimator = brightnessController.mSliderAnimator;
        if (valueAnimator != null && valueAnimator.isStarted()) {
            brightnessController.mSliderAnimator.cancel();
        }
        SecBrightnessController secBrightnessController = brightnessController.mSecBrightnessController;
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
        SecBrightnessController secBrightnessController2 = brightnessController.mSecBrightnessController;
        int convertLinearToGammaFloat = secBrightnessController2 != null ? (int) ((f * secBrightnessController2.maximumBacklight) - secBrightnessController2.minimumBacklight) : BrightnessUtils.convertLinearToGammaFloat(f, f2, f3);
        if (!brightnessController.mControlValueInitialized || (!brightnessController.mAutomatic && !brightnessController.mTrackingTouch)) {
            brightnessController.mControl.setValue(convertLinearToGammaFloat);
            brightnessController.mControlValueInitialized = true;
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(brightnessController.mControl.getValue(), convertLinearToGammaFloat);
        brightnessController.mSliderAnimator = ofInt;
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.settings.brightness.BrightnessController$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                BrightnessController brightnessController2 = BrightnessController.this;
                brightnessController2.mExternalChange = true;
                brightnessController2.mControl.setValue(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                brightnessController2.mExternalChange = false;
            }
        });
        brightnessController.mSliderAnimator.setDuration((brightnessController.mSecBrightnessController != null ? r0.sliderAnimationDuration / brightnessController.mControl.getMax() : 0L) * Math.abs(brightnessController.mControl.getValue() - convertLinearToGammaFloat));
        brightnessController.mSliderAnimator.start();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.settings.brightness.BrightnessController$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.settings.brightness.BrightnessController$2] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.settings.brightness.BrightnessController$3] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.settings.brightness.BrightnessController$6] */
    public BrightnessController(Context context, ToggleSlider toggleSlider, UserTracker userTracker, DisplayTracker displayTracker, DisplayManager displayManager, SecureSettings secureSettings, IVrManager iVrManager, Executor executor, Looper looper, Handler handler) {
        Handler.Callback callback = new Handler.Callback() { // from class: com.android.systemui.settings.brightness.BrightnessController.7
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                BrightnessController brightnessController = BrightnessController.this;
                brightnessController.mExternalChange = true;
                try {
                    int i = message.what;
                    if (i == 1) {
                        BrightnessController.m2101$$Nest$mupdateSlider(brightnessController, Float.intBitsToFloat(message.arg1));
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
        Handler handler2 = new Handler(looper, callback);
        this.mMainHandler = handler2;
        this.mBrightnessObserver = new BrightnessObserver(handler2);
        this.mSecBrightnessController = new SecBrightnessController(handler2, toggleSlider, context, handler);
    }

    public final void onChanged(int i, boolean z, boolean z2) {
        final float min;
        this.mTrackingTouch = z;
        if (this.mExternalChange) {
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
        if (secBrightnessController != null) {
            min = (i + secBrightnessController.minimumBacklight) / secBrightnessController.maximumBacklight;
        } else {
            float norm = MathUtils.norm(0.0f, 65535.0f, i);
            min = MathUtils.min(MathUtils.lerp(f, f2, MathUtils.constrain(norm <= 0.5f ? MathUtils.sq(norm / 0.5f) : MathUtils.exp((norm - 0.5599107f) / 0.17883277f) + 0.28466892f, 0.0f, 12.0f) / 12.0f), f2);
        }
        if (z2) {
            MetricsLogger.action(this.mContext, i2, BrightnessSynchronizer.brightnessFloatToInt(min));
        }
        this.mDisplayManager.setTemporaryBrightness(this.mDisplayId, min);
        if (z) {
            return;
        }
        AsyncTask.execute(new Runnable() { // from class: com.android.systemui.settings.brightness.BrightnessController.9
            @Override // java.lang.Runnable
            public final void run() {
                Settings.System.putIntForUser(BrightnessController.this.mSecBrightnessController.context.getContentResolver(), SettingsHelper.INDEX_AUTO_BRIGHTNESS_TRANSITION_TIME, -1, -2);
                BrightnessController brightnessController = BrightnessController.this;
                brightnessController.mDisplayManager.setBrightness(brightnessController.mDisplayId, min);
            }
        });
    }
}
