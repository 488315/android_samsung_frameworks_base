package com.samsung.android.biometrics.app.setting.fingerprint;

import android.app.OnSemColorsChangedListener;
import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.BackgroundThread;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.FpServiceProviderImpl;
import com.samsung.android.biometrics.app.setting.PowerServiceProvider;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SysUiManager;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.vi.VisualEffectContainer;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class UdfpsKeyguardClient extends UdfpsPrivilegedAuthClient
        implements AodStatusMonitor.Callback,
                DisplayBrightnessMonitor.OnBrightnessListener,
                OnSemColorsChangedListener,
                HbmListener {
    static final int MAX_TIME_BLACK_MASK_MODE_FOR_TAP_TO_SHOW = 250;
    protected AodStatusMonitor mAodStatusMonitor;
    UdfpsOpticalBlackMaskWindow mBlackMaskWindow;
    protected UdfpsIconOptionMonitor mIconOptionMonitor;
    public boolean mIsAuthenticated;
    UdfpsKeyguardSensorWindow mKeyguardWindow;
    public final FpServiceProvider mProvider;
    public final PowerServiceProvider mPsProvider;
    public UdfpsKeyguardClient$$ExternalSyntheticLambda0 mRunnableOnDisplayStateDoze;
    public int mWallpaperFontColor;
    public final WallpaperManager mWallpaperManager;

    public UdfpsKeyguardClient(
            Context context,
            int i,
            int i2,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback,
            Bundle bundle,
            String str,
            DisplayStateManager displayStateManager,
            UdfpsSensorWindow udfpsSensorWindow,
            FingerprintSensorInfo fingerprintSensorInfo,
            Consumer consumer,
            FpServiceProvider fpServiceProvider,
            PowerServiceProvider powerServiceProvider) {
        super(
                context,
                i,
                i2,
                iSemBiometricSysUiCallback,
                bundle,
                str,
                displayStateManager,
                udfpsSensorWindow,
                fingerprintSensorInfo,
                consumer,
                false);
        this.mIsKeyguard = true;
        this.mProvider = fpServiceProvider;
        this.mWallpaperManager =
                (WallpaperManager) this.mContext.getSystemService(WallpaperManager.class);
        this.mPsProvider = powerServiceProvider;
        displayStateManager.updateDisplayState();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient, android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Log.i("BSS_UdfpsKeyguardClient", Utils.getLogFormat(message));
        int i = message.what;
        if (i == 1) {
            hideSensorIconWhenScreenOff();
        } else if (i == 2) {
            UdfpsKeyguardSensorWindow udfpsKeyguardSensorWindow = this.mKeyguardWindow;
            if (udfpsKeyguardSensorWindow != null) {
                udfpsKeyguardSensorWindow.mHelpMessageOnAodView.setVisibility(4);
            }
        } else if (i != 3) {
            if (i == 4) {
                hideSensorIconOnLock();
            }
        } else if (this.mIsAuthenticated && this.mAodStatusMonitor.isShowing()) {
            Log.i("BSS_UdfpsKeyguardClient", "AOD is showing, extend black mask time");
            setBlackMaskMode(35L, true);
        } else {
            setBlackMaskMode(0L, false);
        }
        return true;
    }

    public final void handleOnAodStatusChanged() {
        Log.i("BSS_UdfpsKeyguardClient", "handleOnAodStatusChanged");
        if (this.mIsAuthenticated || this.mDisplayStateManager.isOnState()) {
            return;
        }
        UdfpsIconOptionMonitor udfpsIconOptionMonitor = this.mIconOptionMonitor;
        if (udfpsIconOptionMonitor.mIconOptionWhenScreenOff == 1
                || udfpsIconOptionMonitor.isEnabledOnAod()) {
            if (!this.mAodStatusMonitor.isShowing()) {
                hideSensorIconWhenScreenOff();
                return;
            }
            if (!this.mIconOptionMonitor.isEnabledOnAod()) {
                if (this.mIconOptionMonitor.mIconOptionWhenScreenOff != 1) {
                    return;
                }
                AodStatusMonitor aodStatusMonitor = this.mAodStatusMonitor;
                if (!aodStatusMonitor.mIsEnabledAod || !aodStatusMonitor.mIsEnabledAodTapToShow) {
                    return;
                }
                if (this.mSensorInfo.mIsUltrasonic) {
                    ((FpServiceProviderImpl) this.mProvider).requestToFpSvc(100, 0, 0L, null);
                }
            }
            showSensorIconDueToAodWhenScreenIsOff();
        }
    }

    public final void handleSingleTapEvent() {
        if (this.mIconOptionMonitor.mIconOptionWhenScreenOff == 1) {
            AodStatusMonitor aodStatusMonitor = this.mAodStatusMonitor;
            if ((aodStatusMonitor.mIsEnabledAod && aodStatusMonitor.mIsEnabledAodTapToShow)
                    || this.mKeyguardWindow.isSensorIconShown()) {
                return;
            }
            showSensorIconDueToFodTouchWhenScreenIsOff(10000L, true);
            if (this.mSensorInfo.mIsUltrasonic) {
                ((FpServiceProviderImpl) this.mProvider).requestToFpSvc(100, 0, 0L, null);
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthClient,
              // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void handleTspBlock(boolean z) {
        if (z) {
            if (this.mKeyguardWindow.isSensorIconShown()) {
                this.mPsProvider.acquireWakeLock(10000L);
                this.mHandler.removeMessages(2);
                this.mKeyguardWindow.hideSensorIcon(1);
                if (this.mDisplayStateManager.isOnState()) {
                    return;
                }
                this.mKeyguardWindow.showHelpMessageOnAod(
                        FingerprintManager.getAcquiredString(this.mContext, 6, 1004));
                this.mHandler.sendEmptyMessageDelayed(2, 3000L);
                return;
            }
            return;
        }
        if (this.mKeyguardWindow.isVisible()) {
            this.mPsProvider.acquireWakeLock(10000L);
            if (!this.mDisplayStateManager.isOnState()) {
                UdfpsKeyguardSensorWindow udfpsKeyguardSensorWindow = this.mKeyguardWindow;
                if (udfpsKeyguardSensorWindow.mFingerIconHideReason == 1) {
                    udfpsKeyguardSensorWindow.showSensorIcon();
                }
            } else if (this.mIconOptionMonitor.isEnabledScreenOn()) {
                this.mKeyguardWindow.showSensorIcon();
            }
            this.mKeyguardWindow.mHelpMessageOnAodView.setVisibility(4);
        }
    }

    public final void hideSensorIconOnLock() {
        try {
            this.mHandler.removeMessages(4);
            this.mKeyguardWindow.hideSensorIcon(0);
        } finally {
            this.mPsProvider.releaseWakeLock();
        }
    }

    public void hideSensorIconWhenScreenOff() {
        Log.i("BSS_UdfpsKeyguardClient", "hideSensorIconWhenScreenOff");
        try {
            this.mPsProvider.acquireWakeLock(1000L);
            this.mHandler.removeMessages(1);
            if (this.mIconOptionMonitor.isEnabledOnAod() && this.mAodStatusMonitor.isShowing()) {
                return;
            }
            this.mKeyguardWindow.hideSensorIcon(0);
            ((FpServiceProviderImpl) this.mProvider).requestToFpSvc(4, 2, 0L, null);
            this.mDisplayStateManager.turnOffDoze("BSS_UdfpsKeyguardClient");
            DisplayStateManager displayStateManager = this.mDisplayStateManager;
            displayStateManager.getClass();
            try {
                displayStateManager
                        .mInjector
                        .getIFingerprintService()
                        .semBioSysUiRequest(3, 0, 0L, (String) null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
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

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationError(int i, int i2, String str) {
        VisualEffectContainer visualEffectContainer = this.mKeyguardWindow.mVisualEffectView;
        visualEffectContainer.mIsReadTouchMap = false;
        visualEffectContainer.invalidate();
        this.mKeyguardWindow.setVisibility(4);
        stop();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationFailed(String str) {
        showIconOnAuthenticationFail();
        VisualEffectContainer visualEffectContainer = this.mKeyguardWindow.mVisualEffectView;
        visualEffectContainer.mIsReadTouchMap = false;
        visualEffectContainer.invalidate();
        showHelpMessageOnAod$1(this.mContext.getString(R.string.sem_fingerprint_no_match_for_aod));
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationHelp(int i, String str) {
        showIconOnAuthenticationFail();
        if (TextUtils.isEmpty(str)) {
            return;
        }
        showHelpMessageOnAod$1(str);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationSucceeded(String str) {
        if (this.mIsAuthenticated) {
            return;
        }
        this.mIsAuthenticated = true;
        if (this.mSensorInfo.mIsOptical && !this.mDisplayStateManager.isOnState()) {
            setBlackMaskMode(70L, true);
            DisplayBrightnessMonitor.sInstanceHolder.sInstance.registerListener(this);
            ((FpServiceProviderImpl) this.mProvider).requestToFpSvc(10, 0, 0L, null);
        }
        VisualEffectContainer visualEffectContainer = this.mKeyguardWindow.mVisualEffectView;
        visualEffectContainer.mIsReadTouchMap = false;
        visualEffectContainer.invalidate();
        this.mKeyguardWindow.hideSensorIcon(0);
        this.mKeyguardWindow.mHelpMessageOnAodView.setVisibility(4);
    }

    public final void onBouncerScreen(boolean z) {
        this.mHandler.removeMessages(4);
        updateWallpaperFontColor();
        if (!z) {
            if (this.mIconOptionMonitor.isEnabledScreenOn()) {
                this.mKeyguardWindow.showSensorIcon();
                return;
            } else {
                this.mKeyguardWindow.hideSensorIcon(0);
                return;
            }
        }
        if (this.mDisplayStateManager.isOnState()) {
            if (this.mKeyguardWindow.isSensorIconShown()) {
                this.mKeyguardWindow.showSensorIcon();
            } else {
                this.mKeyguardWindow.showSensorIconWithAnimation();
            }
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
        VisualEffectContainer visualEffectContainer = this.mKeyguardWindow.mVisualEffectView;
        visualEffectContainer.mIsReadTouchMap = false;
        visualEffectContainer.invalidate();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient
    public final void onCaptureStart() {
        this.mKeyguardWindow.moveIcon(0, 0);
        if (this.mIconOptionMonitor.mViEffectType != 0
                && this.mDisplayStateManager.mCurrentDisplayState != 1001) {
            UdfpsKeyguardSensorWindow udfpsKeyguardSensorWindow = this.mKeyguardWindow;
            udfpsKeyguardSensorWindow.getClass();
            Log.i("BSS_UdfpsKeyguardSensorWindow", "startVisualEffect");
            VisualEffectContainer visualEffectContainer =
                    udfpsKeyguardSensorWindow.mVisualEffectView;
            if (visualEffectContainer != null) {
                visualEffectContainer.mHandler.post(visualEffectContainer.mStartCallback);
            }
        }
        this.mKeyguardWindow.setVisibility(0);
    }

    public final void onColorsChanged(SemWallpaperColors semWallpaperColors, int i) {
        if ((i & 2) != 0) {
            updateWallpaperFontColor();
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            UdfpsKeyguardClient udfpsKeyguardClient = UdfpsKeyguardClient.this;
                            if (udfpsKeyguardClient.mDisplayStateManager.isOnState()
                                    && udfpsKeyguardClient.mKeyguardWindow.isSensorIconShown()) {
                                udfpsKeyguardClient.mKeyguardWindow.showSensorIcon();
                            }
                        }
                    });
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void onDisplayStateChanged(int i) {
        if (i == 2) {
            this.mHandler.removeMessages(1);
        } else {
            this.mHandler.removeMessages(4);
        }
        if (i == 1) {
            if (this.mAodStatusMonitor.isShowing() && this.mIconOptionMonitor.isEnabledOnAod()) {
                this.mKeyguardWindow.showSensorIconWithAnimation();
                return;
            }
            VisualEffectContainer visualEffectContainer = this.mKeyguardWindow.mVisualEffectView;
            if (visualEffectContainer == null || visualEffectContainer.getVisibility() != 0) {
                this.mKeyguardWindow.setVisibility(4);
                return;
            }
            return;
        }
        if (i != 2) {
            if (i != 3 && i != 4) {
                if (i != 1001) {
                    return;
                }
                this.mKeyguardWindow.hideSensorIcon(0);
                return;
            } else {
                UdfpsKeyguardClient$$ExternalSyntheticLambda0
                        udfpsKeyguardClient$$ExternalSyntheticLambda0 =
                                this.mRunnableOnDisplayStateDoze;
                if (udfpsKeyguardClient$$ExternalSyntheticLambda0 != null) {
                    udfpsKeyguardClient$$ExternalSyntheticLambda0.run();
                    this.mRunnableOnDisplayStateDoze = null;
                    return;
                }
                return;
            }
        }
        this.mKeyguardWindow.mHelpMessageOnAodView.setVisibility(4);
        if ((!this.mSensorInfo.mIsOptical || this.mDisplayStateManager.isEnabledHbm())
                && !this.mIsAuthenticated) {
            if (!this.mIconOptionMonitor.isEnabledScreenOn()) {
                hideSensorIconOnLock();
                return;
            }
            updateWallpaperFontColor();
            this.mKeyguardWindow.moveIcon(0, 0);
            this.mKeyguardWindow.showSensorIconWithAnimation();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmListener
    public final void onHbmChanged(boolean z) {
        if (z && this.mDisplayStateManager.isOnState() && !this.mIsAuthenticated) {
            this.mKeyguardWindow.moveIcon(0, 0);
            if (this.mIconOptionMonitor.isEnabledScreenOn()) {
                this.mKeyguardWindow.showSensorIconWithAnimation();
            } else {
                hideSensorIconOnLock();
            }
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

    /* JADX WARN: Type inference failed for: r5v0, types: [com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient$$ExternalSyntheticLambda2] */
    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthClient,
              // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void prepareWindows() {
        UdfpsKeyguardSensorWindow udfpsKeyguardSensorWindow =
                new UdfpsKeyguardSensorWindow(
                        this.mContext,
                        this,
                        this.mSensorInfo,
                        this.mDisplayStateManager,
                        new Supplier() { // from class:
                            // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                UdfpsKeyguardClient udfpsKeyguardClient = UdfpsKeyguardClient.this;
                                if (!udfpsKeyguardClient.mDisplayStateManager.isOnState()) {
                                    return "ic_fingerprint_aod.json";
                                }
                                boolean z =
                                        (Settings.System.getString(
                                                                        udfpsKeyguardClient.mContext
                                                                                .getContentResolver(),
                                                                        "current_sec_active_themepackage")
                                                                == null
                                                        || ((FpServiceProviderImpl)
                                                                        udfpsKeyguardClient
                                                                                .mProvider)
                                                                .isKeyguardBouncerShowing())
                                                ? false
                                                : true;
                                return udfpsKeyguardClient.mWallpaperFontColor == 0
                                        ? z
                                                ? "ic_fingerprint_dark_theme_non_alpha.json"
                                                : "ic_fingerprint_dark_theme.json"
                                        : z
                                                ? "ic_fingerprint_light_theme_non_alpha.json"
                                                : "ic_fingerprint_light_theme.json";
                            }
                        },
                        new Function() { // from class:
                            // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient$$ExternalSyntheticLambda2
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                int color;
                                UdfpsKeyguardClient udfpsKeyguardClient = UdfpsKeyguardClient.this;
                                String str = (String) obj;
                                udfpsKeyguardClient.getClass();
                                if (str.contains("aod")) {
                                    color = 0;
                                } else {
                                    boolean contains = str.contains("dark_theme");
                                    color =
                                            (Settings.System.getString(
                                                                            udfpsKeyguardClient
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "current_sec_active_themepackage")
                                                                    == null
                                                            || !((FpServiceProviderImpl)
                                                                            udfpsKeyguardClient
                                                                                    .mProvider)
                                                                    .isKeyguardBouncerShowing())
                                                    ? contains
                                                            ? udfpsKeyguardClient.mContext.getColor(
                                                                    R.color
                                                                            .fingerprint_icon_lock_tint)
                                                            : udfpsKeyguardClient.mContext.getColor(
                                                                    R.color
                                                                            .fingerprint_icon_lock_tint_dark)
                                                    : contains
                                                            ? udfpsKeyguardClient.mContext.getColor(
                                                                    R.color
                                                                            .fingerprint_icon_lock_tint_org)
                                                            : udfpsKeyguardClient.mContext.getColor(
                                                                    R.color
                                                                            .fingerprint_icon_lock_tint_dark_org);
                                }
                                return Integer.valueOf(color);
                            }
                        });
        this.mKeyguardWindow = udfpsKeyguardSensorWindow;
        udfpsKeyguardSensorWindow.initFromBaseWindow(this.mBaseSensorWindow);
        this.mWindows.add(this.mKeyguardWindow);
        if (this.mSensorInfo.mIsOptical && this.mAodStatusMonitor.mIsEnabledAod) {
            Context context = this.mContext;
            UdfpsOpticalBlackMaskWindow udfpsOpticalBlackMaskWindow =
                    new UdfpsOpticalBlackMaskWindow(context);
            BlackMaskView blackMaskView = new BlackMaskView(context, null);
            udfpsOpticalBlackMaskWindow.mBlackMaskView = blackMaskView;
            blackMaskView.setBlackMaskMode(false);
            blackMaskView.setVisibility(8);
            udfpsOpticalBlackMaskWindow.mBaseView = blackMaskView;
            this.mBlackMaskWindow = udfpsOpticalBlackMaskWindow;
            this.mWindows.add(udfpsOpticalBlackMaskWindow);
        }
    }

    public final void setAodStatusMonitor(AodStatusMonitor aodStatusMonitor) {
        this.mAodStatusMonitor = aodStatusMonitor;
    }

    public final void setBlackMaskMode(long j, boolean z) {
        UdfpsOpticalBlackMaskWindow udfpsOpticalBlackMaskWindow = this.mBlackMaskWindow;
        if (udfpsOpticalBlackMaskWindow == null) {
            return;
        }
        Log.i("BSS_UdfpsOpticalBlackMaskWindow", "setBlackMaskMode: " + z);
        BlackMaskView blackMaskView = udfpsOpticalBlackMaskWindow.mBlackMaskView;
        if (blackMaskView.mIsBlackMaskModeOn != z) {
            blackMaskView.setVisibility(z ? 0 : 8);
            blackMaskView.setBlackMaskMode(z);
        }
        this.mHandler.removeMessages(3);
        if (z) {
            this.mHandler.sendEmptyMessageDelayed(3, j);
            return;
        }
        DisplayBrightnessMonitor.sInstanceHolder.sInstance.unregisterListener(this);
        if (!this.mIsAuthenticated || shouldMaintainWindow()) {
            Log.i("BSS_UdfpsKeyguardClient", "setBlackMaskMode: should maintain window");
        } else {
            super.stop();
        }
    }

    public final void setUdfpsIconOptionMonitor(UdfpsIconOptionMonitor udfpsIconOptionMonitor) {
        this.mIconOptionMonitor = udfpsIconOptionMonitor;
    }

    public final boolean shouldMaintainWindow() {
        UdfpsOpticalBlackMaskWindow udfpsOpticalBlackMaskWindow;
        VisualEffectContainer visualEffectContainer;
        return this.mIsAuthenticated
                && this.mKeyguardWindow.isVisible()
                && (((udfpsOpticalBlackMaskWindow = this.mBlackMaskWindow) != null
                                && udfpsOpticalBlackMaskWindow.mBlackMaskView.mIsBlackMaskModeOn)
                        || ((visualEffectContainer = this.mKeyguardWindow.mVisualEffectView) != null
                                && visualEffectContainer.getVisibility() == 0));
    }

    public final void showFingerIconOnLock() {
        if (!this.mIsAuthenticated
                && this.mDisplayStateManager.isOnState()
                && this.mIconOptionMonitor.mIconOptionWhenScreenOn == 1) {
            if (!this.mKeyguardWindow.isSensorIconShown()) {
                this.mKeyguardWindow.showSensorIconWithAnimation();
            }
            this.mHandler.removeMessages(4);
            this.mHandler.sendEmptyMessageDelayed(4, 5000L);
        }
    }

    public final void showHelpMessageOnAod$1(String str) {
        this.mHandler.removeMessages(2);
        if (this.mDisplayStateManager.isOnState()) {
            return;
        }
        showSensorIconDueToFodTouchWhenScreenIsOff(10000L, false);
        this.mKeyguardWindow.showHelpMessageOnAod(str);
        this.mHandler.sendEmptyMessageDelayed(2, 3000L);
    }

    public final void showIconOnAuthenticationFail() {
        this.mKeyguardWindow.showSensorIcon();
        if (!this.mDisplayStateManager.isOnState() || this.mIconOptionMonitor.isEnabledScreenOn()) {
            return;
        }
        this.mHandler.removeMessages(4);
        this.mHandler.sendEmptyMessageDelayed(4, 5000L);
    }

    public void showSensorIconDueToAodWhenScreenIsOff() {
        Log.i("BSS_UdfpsKeyguardClient", "showSensorIconDueToAodWhenScreenIsOff");
        AodStatusMonitor aodStatusMonitor = this.mAodStatusMonitor;
        if (((!aodStatusMonitor.mIsEnabledAod
                                || aodStatusMonitor.mIsEnabledAodTapToShow
                                || aodStatusMonitor.mAodStartTime != aodStatusMonitor.mAodEndTime)
                        && !aodStatusMonitor.isInAodScheduleTime())
                || !this.mIconOptionMonitor.isEnabledOnAod()
                || this.mDisplayStateManager.mCurrentDisplayState == 4) {
            this.mPsProvider.acquireWakeLock(10000L);
        }
        this.mHandler.removeMessages(1);
        this.mDisplayStateManager.turnOnDoze("BSS_UdfpsKeyguardClient");
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        displayStateManager.getClass();
        try {
            displayStateManager
                    .mInjector
                    .getIFingerprintService()
                    .semBioSysUiRequest(3, 1, 0L, (String) null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        turnOnTsp();
        this.mKeyguardWindow.showSensorIconWithAnimation();
    }

    public void showSensorIconDueToFodTouchWhenScreenIsOff(long j, boolean z) {
        int i;
        Log.i("BSS_UdfpsKeyguardClient", "showSensorIconDueToTouchWhenScreenIsOff: " + j);
        this.mPsProvider.acquireWakeLock(10000 + j);
        this.mHandler.removeMessages(1);
        turnOnTsp();
        this.mDisplayStateManager.turnOnDoze("BSS_UdfpsKeyguardClient");
        if (!this.mAodStatusMonitor.isShowing()
                && (i = this.mDisplayStateManager.mCurrentDisplayState) != 3
                && i != 4) {
            Log.d("BSS_UdfpsKeyguardClient", "showSensorIcon: pending, waiting for doze");
            this.mRunnableOnDisplayStateDoze =
                    new UdfpsKeyguardClient$$ExternalSyntheticLambda0(this, j, z);
            return;
        }
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        displayStateManager.getClass();
        try {
            displayStateManager
                    .mInjector
                    .getIFingerprintService()
                    .semBioSysUiRequest(3, 1, 0L, (String) null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (this.mSensorInfo.mIsOptical) {
            ((FpServiceProviderImpl) this.mProvider)
                    .requestToFpSvc(11, 0, SystemClock.elapsedRealtime(), null);
        }
        if (z) {
            this.mKeyguardWindow.showSensorIconWithAnimation();
        } else {
            this.mKeyguardWindow.setVisibility(0);
        }
        if (this.mAodStatusMonitor.isShowing()) {
            AodStatusMonitor aodStatusMonitor = this.mAodStatusMonitor;
            if (aodStatusMonitor.mIsEnabledAod && aodStatusMonitor.mIsEnabledAodTapToShow) {
                j = 0;
            }
        }
        if (j > 0) {
            this.mHandler.sendEmptyMessageDelayed(1, j);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient,
              // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void start(SysUiManager.AnonymousClass1 anonymousClass1) {
        super.start(anonymousClass1);
        WallpaperManager wallpaperManager = this.mWallpaperManager;
        if (wallpaperManager != null) {
            BackgroundThread.get().getClass();
            wallpaperManager.addOnSemColorsChangedListener(this, BackgroundThread.sHandler);
            updateWallpaperFontColor();
        }
        this.mAodStatusMonitor.addCallback(this);
        if (this.mSensorInfo.mIsOptical) {
            this.mDisplayStateManager.registerHbmListener(this);
        }
        if (this.mDisplayStateManager.isOnState()) {
            if (this.mIconOptionMonitor.isEnabledScreenOn()
                    || ((FpServiceProviderImpl) this.mProvider).isKeyguardBouncerShowing()) {
                this.mKeyguardWindow.showSensorIconWithAnimation();
            }
        } else if (this.mIconOptionMonitor.isEnabledOnAod() && this.mAodStatusMonitor.isShowing()) {
            showSensorIconDueToAodWhenScreenIsOff();
        }
        this.mCallback.onClientStarted(this);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsClient,
              // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void stop() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(4);
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        displayStateManager.getClass();
        try {
            displayStateManager
                    .mInjector
                    .getIFingerprintService()
                    .semBioSysUiRequest(3, 0, 0L, (String) null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (!this.mIsAuthenticated) {
            this.mDisplayStateManager.turnOffDoze("BSS_UdfpsKeyguardClient");
        }
        this.mAodStatusMonitor.removeCallback(this);
        WallpaperManager wallpaperManager = this.mWallpaperManager;
        if (wallpaperManager != null) {
            wallpaperManager.removeOnSemColorsChangedListener(this);
        }
        if (this.mSensorInfo.mIsOptical) {
            ((ArrayList) this.mDisplayStateManager.mHbmListeners).remove(this);
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
        this.mPsProvider.releaseWakeLock();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void stopImmediate() {
        this.mKeyguardWindow.setVisibility(8);
        stop();
    }

    public final void turnOnTsp() {
        String str;
        FpServiceProvider fpServiceProvider = this.mProvider;
        if (this.mSensorInfo.mIsUltrasonic) {
            AodStatusMonitor aodStatusMonitor = this.mAodStatusMonitor;
            aodStatusMonitor.getClass();
            if (Utils.Config.FEATURE_SUPPORT_AOD_TRANSITION_ANIMATION) {
                aodStatusMonitor.updateValue();
                if ((aodStatusMonitor.mIsEnabledAod
                                && !aodStatusMonitor.mIsEnabledAodTapToShow
                                && aodStatusMonitor.mAodStartTime == aodStatusMonitor.mAodEndTime)
                        || aodStatusMonitor.isInAodScheduleTime()) {
                    str = "BSS";
                    ((FpServiceProviderImpl) fpServiceProvider).requestToFpSvc(4, 1, 0L, str);
                }
            }
        }
        str = null;
        ((FpServiceProviderImpl) fpServiceProvider).requestToFpSvc(4, 1, 0L, str);
    }

    public final void updateWallpaperFontColor() {
        Context context = this.mContext;
        boolean isKeyguardBouncerShowing =
                ((FpServiceProviderImpl) this.mProvider).isKeyguardBouncerShowing();
        SemWallpaperColors semGetWallpaperColors =
                ((WallpaperManager) context.getSystemService(WallpaperManager.class))
                        .semGetWallpaperColors(2);
        int i = 0;
        if (semGetWallpaperColors != null) {
            SemWallpaperColors.Item item =
                    isKeyguardBouncerShowing
                            ? semGetWallpaperColors.get(512L)
                            : semGetWallpaperColors.get(128L);
            if (item == null || item.getFontColor() == 0) {
                i = 1;
            }
        }
        this.mWallpaperFontColor = i ^ 1;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsClient,
              // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback
    public final void onUserCancel(int i) {}
}
