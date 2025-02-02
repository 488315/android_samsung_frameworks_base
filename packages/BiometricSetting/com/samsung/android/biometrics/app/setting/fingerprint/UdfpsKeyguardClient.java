package com.samsung.android.biometrics.app.setting.fingerprint;

import android.app.OnSemColorsChangedListener;
import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.BackgroundThread;
import com.samsung.android.biometrics.app.setting.ClientCallback;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.PowerServiceProvider;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SysUiManager;
import com.samsung.android.biometrics.app.setting.SysUiManager$Injector$$ExternalSyntheticLambda0;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.vi.VisualEffectContainer;
import java.util.function.IntSupplier;

/* loaded from: classes.dex */
public final class UdfpsKeyguardClient extends UdfpsPrivilegedAuthClient implements AodStatusMonitor.Callback, DisplayBrightnessMonitor.OnBrightnessListener, OnSemColorsChangedListener, HbmListener {

    @VisibleForTesting
    static final int MAX_TIME_BLACK_MASK_MODE_FOR_TAP_TO_SHOW = 250;

    @VisibleForTesting
    protected AodStatusMonitor mAodStatusMonitor;

    @VisibleForTesting
    UdfpsOpticalBlackMaskWindow mBlackMaskWindow;

    @VisibleForTesting
    protected UdfpsIconOptionMonitor mIconOptionMonitor;
    private boolean mIsAuthenticated;

    @VisibleForTesting
    UdfpsKeyguardSensorWindow mKeyguardWindow;
    protected FpServiceProvider mProvider;
    private final PowerServiceProvider mPsProvider;
    protected UdfpsKeyguardClient$$ExternalSyntheticLambda0 mRunnableOnDisplayStateDoze;
    private int mWallpaperFontColor;
    private final WallpaperManager mWallpaperManager;

    public UdfpsKeyguardClient(Context context, int i, int i2, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, Bundle bundle, String str, DisplayStateManager displayStateManager, UdfpsSensorWindow udfpsSensorWindow, UdfpsInfo udfpsInfo, SysUiManager$Injector$$ExternalSyntheticLambda0 sysUiManager$Injector$$ExternalSyntheticLambda0, FpServiceProvider fpServiceProvider, PowerServiceProvider powerServiceProvider) {
        super(context, i, i2, iSemBiometricSysUiCallback, bundle, str, displayStateManager, udfpsSensorWindow, udfpsInfo, sysUiManager$Injector$$ExternalSyntheticLambda0, false);
        this.mIsKeyguard = true;
        this.mProvider = fpServiceProvider;
        this.mWallpaperManager = (WallpaperManager) getContext().getSystemService(WallpaperManager.class);
        this.mPsProvider = powerServiceProvider;
        displayStateManager.updateDisplayState();
    }

    private void handleOnAodStatusChanged() {
        Log.i("BSS_UdfpsKeyguardClient", "handleOnAodStatusChanged");
        if (this.mIsAuthenticated || this.mDisplayStateManager.isOnState()) {
            return;
        }
        UdfpsIconOptionMonitor udfpsIconOptionMonitor = this.mIconOptionMonitor;
        boolean z = true;
        if ((udfpsIconOptionMonitor.isEnabledTapToShow() || udfpsIconOptionMonitor.isEnabledOnAod()) ? false : true) {
            return;
        }
        if (!this.mAodStatusMonitor.isShowing()) {
            hideSensorIconWhenScreenOff();
            return;
        }
        if (!this.mIconOptionMonitor.isEnabledOnAod()) {
            if (this.mIconOptionMonitor.isEnabledTapToShow() && this.mAodStatusMonitor.isEnabledTapToShow()) {
                this.mSensorInfo.getClass();
                if (Utils.Config.FP_FEATURE_SENSOR_IS_ULTRASONIC) {
                    ((SysUiManager) this.mProvider).forceQDB();
                }
            } else {
                z = false;
            }
        }
        if (z) {
            showSensorIconDueToAodWhenScreenIsOff();
        }
    }

    private void setBlackMaskMode(long j, boolean z) {
        UdfpsOpticalBlackMaskWindow udfpsOpticalBlackMaskWindow = this.mBlackMaskWindow;
        if (udfpsOpticalBlackMaskWindow == null) {
            return;
        }
        udfpsOpticalBlackMaskWindow.getClass();
        Log.i("BSS_UdfpsOpticalBlackMaskWindow", "setBlackMaskMode: " + z);
        if (udfpsOpticalBlackMaskWindow.mBlackMaskView.isBlackMaskMode() != z) {
            udfpsOpticalBlackMaskWindow.mBlackMaskView.setVisibility(z ? 0 : 8);
            udfpsOpticalBlackMaskWindow.mBlackMaskView.setBlackMaskMode(z);
        }
        this.mHandler.removeMessages(3);
        if (z) {
            this.mHandler.sendEmptyMessageDelayed(3, j);
            return;
        }
        DisplayBrightnessMonitor.getInstance().unregisterListener(this);
        if (!this.mIsAuthenticated || shouldMaintainWindow()) {
            Log.i("BSS_UdfpsKeyguardClient", "setBlackMaskMode: should maintain window");
        } else {
            super.stop();
        }
    }

    private boolean shouldMaintainWindow() {
        if (!this.mIsAuthenticated || !this.mKeyguardWindow.isVisible()) {
            return false;
        }
        UdfpsOpticalBlackMaskWindow udfpsOpticalBlackMaskWindow = this.mBlackMaskWindow;
        if (udfpsOpticalBlackMaskWindow == null || !udfpsOpticalBlackMaskWindow.mBlackMaskView.isBlackMaskMode()) {
            VisualEffectContainer visualEffectContainer = this.mKeyguardWindow.mVisualEffectView;
            if (!(visualEffectContainer != null && visualEffectContainer.getVisibility() == 0)) {
                return false;
            }
        }
        return true;
    }

    private void showHelpMessageOnAod(String str) {
        this.mHandler.removeMessages(2);
        if (this.mDisplayStateManager.isOnState()) {
            return;
        }
        showSensorIconDueToFodTouchWhenScreenIsOff(10000L, false);
        this.mKeyguardWindow.showHelpMessageOnAod(str);
        this.mHandler.sendEmptyMessageDelayed(2, 3000L);
    }

    private void updateWallpaperFontColor() {
        int i;
        Context context = this.mContext;
        boolean isKeyguardBouncerShowing = ((SysUiManager) this.mProvider).isKeyguardBouncerShowing();
        SemWallpaperColors semGetWallpaperColors = ((WallpaperManager) context.getSystemService(WallpaperManager.class)).semGetWallpaperColors(2);
        if (semGetWallpaperColors != null) {
            SemWallpaperColors.Item item = isKeyguardBouncerShowing ? semGetWallpaperColors.get(512L) : semGetWallpaperColors.get(128L);
            if (item == null || item.getFontColor() == 0) {
                i = 1;
                this.mWallpaperFontColor = i ^ 1;
            }
        }
        i = 0;
        this.mWallpaperFontColor = i ^ 1;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient, com.samsung.android.biometrics.app.setting.SysUiClient, android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Log.i("BSS_UdfpsKeyguardClient", Utils.getLogFormat(message));
        int i = message.what;
        if (i == 1) {
            hideSensorIconWhenScreenOff();
        } else if (i == 2) {
            UdfpsKeyguardSensorWindow udfpsKeyguardSensorWindow = this.mKeyguardWindow;
            if (udfpsKeyguardSensorWindow != null) {
                udfpsKeyguardSensorWindow.hideHelpMessageOnAod();
            }
        } else if (i == 3) {
            if (this.mIsAuthenticated && this.mAodStatusMonitor.isShowing()) {
                Log.i("BSS_UdfpsKeyguardClient", "AOD is showing, extend black mask time");
                setBlackMaskMode(35L, true);
            } else {
                setBlackMaskMode(0L, false);
            }
        }
        return true;
    }

    public final void handleSingleTapEvent() {
        if (!this.mIconOptionMonitor.isEnabledTapToShow() || this.mAodStatusMonitor.isEnabledTapToShow()) {
            return;
        }
        UdfpsKeyguardSensorWindow udfpsKeyguardSensorWindow = this.mKeyguardWindow;
        if (udfpsKeyguardSensorWindow.isVisible() && udfpsKeyguardSensorWindow.mFpIconContainer.getVisibility() == 0) {
            return;
        }
        showSensorIconDueToFodTouchWhenScreenIsOff(10000L, true);
        this.mSensorInfo.getClass();
        if (Utils.Config.FP_FEATURE_SENSOR_IS_ULTRASONIC) {
            ((SysUiManager) this.mProvider).forceQDB();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient
    public final void handleTspBlock(boolean z) {
        this.mHandler.removeMessages(2);
        if (z) {
            if (this.mDisplayStateManager.isOnState()) {
                return;
            }
            this.mKeyguardWindow.showHelpMessageOnAod(FingerprintManager.getAcquiredString(getContext(), 6, 1004));
            this.mHandler.sendEmptyMessageDelayed(2, 3000L);
            return;
        }
        if (!this.mDisplayStateManager.isOnState() && ((!this.mIconOptionMonitor.isEnabledTapToShow() || !this.mHandler.hasMessages(1)) && ((!this.mIconOptionMonitor.isEnabledOnAod() || !this.mAodStatusMonitor.isShowing()) && this.mIconOptionMonitor.isEnabledTapToShow() && this.mAodStatusMonitor.isEnabledTapToShow()))) {
            this.mAodStatusMonitor.getClass();
        }
        this.mKeyguardWindow.hideHelpMessageOnAod();
    }

    @VisibleForTesting
    void hideSensorIconWhenScreenOff() {
        Log.i("BSS_UdfpsKeyguardClient", "hideSensorIconWhenScreenOff");
        try {
            this.mPsProvider.acquireWakeLock(1000L);
            this.mHandler.removeMessages(1);
            if (this.mIconOptionMonitor.isEnabledOnAod() && this.mAodStatusMonitor.isShowing()) {
                return;
            }
            this.mKeyguardWindow.hideSensorIcon();
            ((SysUiManager) this.mProvider).turnOnTspHalfMode();
            this.mDisplayStateManager.turnOffDoze("BSS_UdfpsKeyguardClient");
            this.mDisplayStateManager.turnOffDozeHLPM();
        } finally {
            this.mPsProvider.releaseWakeLock();
        }
    }

    public final void moveSensorIcon(int i, int i2) {
        this.mKeyguardWindow.moveIcon(i, i2);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.Callback
    public final void onAodStart() {
        handleOnAodStatusChanged();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.Callback
    public final void onAodStop() {
        handleOnAodStatusChanged();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationError(int i, int i2, String str) {
        this.mKeyguardWindow.mVisualEffectView.disableTouchMapUpdate();
        this.mKeyguardWindow.setVisibility(4);
        stop();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationFailed(String str) {
        this.mKeyguardWindow.showSensorIcon();
        this.mKeyguardWindow.mVisualEffectView.disableTouchMapUpdate();
        showHelpMessageOnAod(getContext().getString(R.string.sem_fingerprint_no_match_for_aod));
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationHelp(int i, String str) {
        this.mKeyguardWindow.showSensorIcon();
        if (TextUtils.isEmpty(str)) {
            return;
        }
        showHelpMessageOnAod(str);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationSucceeded(String str) {
        if (this.mIsAuthenticated) {
            return;
        }
        this.mIsAuthenticated = true;
        this.mSensorInfo.getClass();
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL && !this.mDisplayStateManager.isOnState()) {
            setBlackMaskMode(70L, true);
            DisplayBrightnessMonitor.getInstance().registerListener(this);
            ((SysUiManager) this.mProvider).request(10, 0, 0L);
        }
        this.mKeyguardWindow.mVisualEffectView.disableTouchMapUpdate();
        this.mKeyguardWindow.hideSensorIcon();
        this.mKeyguardWindow.hideHelpMessageOnAod();
    }

    public final void onBouncerScreen() {
        if (this.mDisplayStateManager.isOnState()) {
            updateWallpaperFontColor();
            this.mKeyguardWindow.showSensorIconWithAnimation();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor.OnBrightnessListener
    public final void onBrightnessChanged(int i) {
        if (this.mAodStatusMonitor.isShowing()) {
            Log.i("BSS_UdfpsKeyguardClient", "onBrightnessChanged, but AOD is showing");
        } else {
            setBlackMaskMode(0L, false);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient
    public final void onCaptureComplete() {
        this.mKeyguardWindow.mVisualEffectView.disableTouchMapUpdate();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient
    public final void onCaptureStart() {
        this.mKeyguardWindow.moveIcon(0, 0);
        if (this.mIconOptionMonitor.isEnabledViEffect() && this.mDisplayStateManager.getDisplayState() != 1001) {
            UdfpsKeyguardSensorWindow udfpsKeyguardSensorWindow = this.mKeyguardWindow;
            udfpsKeyguardSensorWindow.getClass();
            Log.i("BSS_UdfpsKeyguardSensorWindow", "startVisualEffect");
            VisualEffectContainer visualEffectContainer = udfpsKeyguardSensorWindow.mVisualEffectView;
            if (visualEffectContainer != null) {
                visualEffectContainer.start();
            }
        }
        this.mKeyguardWindow.setVisibility(0);
    }

    public final void onColorsChanged(SemWallpaperColors semWallpaperColors, int i) {
        if ((i & 2) != 0) {
            updateWallpaperFontColor();
            this.mHandler.post(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    UdfpsKeyguardClient udfpsKeyguardClient = UdfpsKeyguardClient.this;
                    if (udfpsKeyguardClient.mDisplayStateManager.isOnState()) {
                        UdfpsKeyguardSensorWindow udfpsKeyguardSensorWindow = udfpsKeyguardClient.mKeyguardWindow;
                        if (udfpsKeyguardSensorWindow.isVisible() && udfpsKeyguardSensorWindow.mFpIconContainer.getVisibility() == 0) {
                            udfpsKeyguardClient.mKeyguardWindow.showSensorIcon();
                        }
                    }
                }
            });
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void onDisplayStateChanged(int i) {
        boolean z = false;
        if (i == 1) {
            if (this.mAodStatusMonitor.isShowing() && this.mIconOptionMonitor.isEnabledOnAod()) {
                this.mKeyguardWindow.showSensorIconWithAnimation();
                return;
            }
            VisualEffectContainer visualEffectContainer = this.mKeyguardWindow.mVisualEffectView;
            if (visualEffectContainer != null && visualEffectContainer.getVisibility() == 0) {
                z = true;
            }
            if (z) {
                return;
            }
            this.mKeyguardWindow.setVisibility(4);
            return;
        }
        if (i == 2) {
            this.mHandler.removeMessages(1);
            this.mKeyguardWindow.hideHelpMessageOnAod();
            this.mSensorInfo.getClass();
            if ((!Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL || this.mDisplayStateManager.isEnabledHbm()) && !this.mIsAuthenticated) {
                updateWallpaperFontColor();
                this.mKeyguardWindow.moveIcon(0, 0);
                this.mKeyguardWindow.showSensorIconWithAnimation();
                return;
            }
            return;
        }
        if (i != 3 && i != 4) {
            if (i != 1001) {
                return;
            }
            this.mKeyguardWindow.hideSensorIcon();
        } else {
            UdfpsKeyguardClient$$ExternalSyntheticLambda0 udfpsKeyguardClient$$ExternalSyntheticLambda0 = this.mRunnableOnDisplayStateDoze;
            if (udfpsKeyguardClient$$ExternalSyntheticLambda0 != null) {
                udfpsKeyguardClient$$ExternalSyntheticLambda0.run();
                this.mRunnableOnDisplayStateDoze = null;
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmListener
    public final void onHbmChanged(boolean z) {
        if (z && this.mDisplayStateManager.isOnState() && !this.mIsAuthenticated) {
            this.mKeyguardWindow.moveIcon(0, 0);
            this.mKeyguardWindow.showSensorIconWithAnimation();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback
    public final void onVisualEffectFinished() {
        Log.d("BSS_UdfpsKeyguardClient", "onVisualEffectFinished");
        if (this.mIsAuthenticated) {
            if (shouldMaintainWindow()) {
                Log.i("BSS_UdfpsKeyguardClient", "onVisualEffectFinished: should maintain window");
            } else {
                super.stop();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r5v0, types: [com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient$$ExternalSyntheticLambda2] */
    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthClient, com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient, com.samsung.android.biometrics.app.setting.SysUiClient
    public final void prepareWindows() {
        UdfpsKeyguardSensorWindow udfpsKeyguardSensorWindow = new UdfpsKeyguardSensorWindow(this.mContext, this, this.mSensorInfo, this.mDisplayStateManager, new IntSupplier() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient$$ExternalSyntheticLambda2
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                int i;
                i = UdfpsKeyguardClient.this.mWallpaperFontColor;
                return i;
            }
        });
        this.mKeyguardWindow = udfpsKeyguardSensorWindow;
        udfpsKeyguardSensorWindow.initFromBaseWindow(this.mBaseSensorWindow);
        this.mWindows.add(this.mKeyguardWindow);
        this.mSensorInfo.getClass();
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL && this.mAodStatusMonitor.isEnabled()) {
            UdfpsOpticalBlackMaskWindow udfpsOpticalBlackMaskWindow = new UdfpsOpticalBlackMaskWindow(this.mContext);
            this.mBlackMaskWindow = udfpsOpticalBlackMaskWindow;
            this.mWindows.add(udfpsOpticalBlackMaskWindow);
        }
    }

    public final void setAodStatusMonitor(AodStatusMonitor aodStatusMonitor) {
        this.mAodStatusMonitor = aodStatusMonitor;
    }

    public final void setUdfpsIconOptionMonitor(UdfpsIconOptionMonitor udfpsIconOptionMonitor) {
        this.mIconOptionMonitor = udfpsIconOptionMonitor;
    }

    @VisibleForTesting
    void showSensorIconDueToAodWhenScreenIsOff() {
        Log.i("BSS_UdfpsKeyguardClient", "showSensorIconDueToAodWhenScreenIsOff");
        if (((this.mAodStatusMonitor.isEnabledShowAlways() || this.mAodStatusMonitor.isInAodScheduleTime()) && this.mIconOptionMonitor.isEnabledOnAod()) ? false : true) {
            this.mPsProvider.acquireWakeLock(10000L);
        }
        this.mHandler.removeMessages(1);
        this.mDisplayStateManager.turnOnDoze("BSS_UdfpsKeyguardClient");
        this.mDisplayStateManager.turnOnDozeHLPM();
        ((SysUiManager) this.mProvider).turnOnTsp();
        this.mKeyguardWindow.showSensorIconWithAnimation();
    }

    @VisibleForTesting
    void showSensorIconDueToFodTouchWhenScreenIsOff(long j, boolean z) {
        Log.i("BSS_UdfpsKeyguardClient", "showSensorIconDueToTouchWhenScreenIsOff: " + j);
        this.mPsProvider.acquireWakeLock(10000 + j);
        this.mHandler.removeMessages(1);
        ((SysUiManager) this.mProvider).turnOnTsp();
        this.mDisplayStateManager.turnOnDoze("BSS_UdfpsKeyguardClient");
        if (!this.mAodStatusMonitor.isShowing() && !this.mDisplayStateManager.isDozeState()) {
            Log.d("BSS_UdfpsKeyguardClient", "showSensorIcon: pending, waiting for doze");
            this.mRunnableOnDisplayStateDoze = new UdfpsKeyguardClient$$ExternalSyntheticLambda0(this, j, z);
            return;
        }
        this.mDisplayStateManager.turnOnDozeHLPM();
        this.mSensorInfo.getClass();
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
            ((SysUiManager) this.mProvider).request(11, 0, SystemClock.elapsedRealtime());
        }
        if (z) {
            this.mKeyguardWindow.showSensorIconWithAnimation();
        } else {
            this.mKeyguardWindow.setVisibility(0);
        }
        if (this.mAodStatusMonitor.isShowing() && this.mAodStatusMonitor.isEnabledTapToShow()) {
            j = 0;
        }
        if (j > 0) {
            this.mHandler.sendEmptyMessageDelayed(1, j);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient, com.samsung.android.biometrics.app.setting.SysUiClient
    public final void start(ClientCallback clientCallback) {
        super.start(clientCallback);
        WallpaperManager wallpaperManager = this.mWallpaperManager;
        if (wallpaperManager != null) {
            BackgroundThread.get().getClass();
            wallpaperManager.addOnSemColorsChangedListener(this, BackgroundThread.getHandler());
            updateWallpaperFontColor();
        }
        this.mAodStatusMonitor.addCallback(this);
        this.mSensorInfo.getClass();
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
            this.mDisplayStateManager.registerHbmListener(this);
        }
        if (this.mDisplayStateManager.isOnState()) {
            this.mKeyguardWindow.showSensorIconWithAnimation();
        } else if (this.mIconOptionMonitor.isEnabledOnAod() && this.mAodStatusMonitor.isShowing()) {
            showSensorIconDueToAodWhenScreenIsOff();
        }
        this.mCallback.onClientStarted();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsClient, com.samsung.android.biometrics.app.setting.SysUiClient
    public final void stop() {
        this.mHandler.removeMessages(1);
        this.mDisplayStateManager.turnOffDozeHLPM();
        if (!this.mIsAuthenticated) {
            this.mDisplayStateManager.turnOffDoze("BSS_UdfpsKeyguardClient");
        }
        this.mAodStatusMonitor.removeCallback(this);
        WallpaperManager wallpaperManager = this.mWallpaperManager;
        if (wallpaperManager != null) {
            wallpaperManager.removeOnSemColorsChangedListener(this);
        }
        this.mSensorInfo.getClass();
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
            this.mDisplayStateManager.unregisterHbmListener(this);
            UdfpsOpticalBlackMaskWindow udfpsOpticalBlackMaskWindow = this.mBlackMaskWindow;
            if (udfpsOpticalBlackMaskWindow != null) {
                udfpsOpticalBlackMaskWindow.removeView();
            }
        }
        if (shouldMaintainWindow()) {
            Log.d("BSS_UdfpsKeyguardClient", "stop: should maintain window");
        } else {
            super.stop();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void stopImmediate() {
        this.mKeyguardWindow.setVisibility(8);
        stop();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsClient, com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback
    public final void onUserCancel(int i) {
    }
}
