package com.android.server.biometrics.sensors.fingerprint;

import android.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;

import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricSysUiManager;
import com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda10;
import com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda5;
import com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda6;
import com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda8;

public final class SemUdfpsSysUiImpl implements SemBiometricSysUiManager.SysUiListener {
    static final String DB_UDFPS_HIDE_AUTH_GUIDE_LAYER_PACKAGES =
            "config_biometric_udfps_flag_hide_auth_guide_layer_packages";
    public int mAuthFailCount;
    public final Context mContext;
    public Bundle mCustomIconAttribute;
    boolean mHideAuthenticationGuide;
    public boolean mIsAuthenticated;
    public final boolean mIsKeyguard;
    public boolean mIsRequireTouchBlock;
    public final String mPackageName;
    public SysUiCallback mSysUiCallback;
    public int mSysUiSessionId;
    public int mSysUiType;
    public final IBinder mToken;
    public final int mUserId;
    SemBiometricSysUiManager mSysUiManager = SemBiometricSysUiManager.sInstance;
    SemUdfpsTspManager mUdfpsTspManager = SemUdfpsTspManager.get();
    public boolean mIsBiometricPrompt = true;
    public final int mHashCode = hashCode();

    public interface SysUiCallback {
        void onSysUiDismissed();

        void onSysUiError(int i, int i2);
    }

    public SemUdfpsSysUiImpl(
            Context context, IBinder iBinder, String str, int i, boolean z, boolean z2) {
        this.mContext = context;
        this.mToken = iBinder;
        this.mPackageName = str;
        this.mUserId = i;
        this.mHideAuthenticationGuide = z;
        this.mIsKeyguard = z2;
    }

    public final void checkGuideLayerAndTouchBlock(int i) {
        boolean z = false;
        if (!this.mHideAuthenticationGuide) {
            String stringForUser =
                    Settings.Secure.getStringForUser(
                            this.mContext.getContentResolver(),
                            DB_UDFPS_HIDE_AUTH_GUIDE_LAYER_PACKAGES,
                            0);
            String[] split = !TextUtils.isEmpty(stringForUser) ? stringForUser.split(";") : null;
            if (split == null || split.length < 1) {
                split =
                        this.mContext
                                .getResources()
                                .getStringArray(R.array.config_protectedNetworks);
            }
            if (split != null) {
                int length = split.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        String str = split[i2];
                        if (str != null && str.contentEquals(this.mPackageName)) {
                            Slog.i(
                                    "Biometrics/SemUdfpsSysUiImpl",
                                    "ClientExtImpl: specific allowlist attr");
                            this.mHideAuthenticationGuide = true;
                            break;
                        }
                        i2++;
                    } else {
                        break;
                    }
                }
            }
        }
        if (this.mHideAuthenticationGuide && (i & 16) == 0) {
            z = true;
        }
        this.mIsRequireTouchBlock = z;
    }

    public final void destroy() {
        stop();
        this.mSysUiManager.closeSession(this.mSysUiSessionId, 4000L);
    }

    public Handler getHandler() {
        return BiometricHandlerProvider.sBiometricHandlerProvider.getFingerprintHandler();
    }

    public final void handleOnAcquired(int i, int i2) {
        if (i == 6) {
            if (this.mIsBiometricPrompt) {
                SemBiometricSysUiManager semBiometricSysUiManager = this.mSysUiManager;
                semBiometricSysUiManager.getClass();
                semBiometricSysUiManager.handleRequest(
                        new SemBiometricSysUiManager$$ExternalSyntheticLambda6(
                                semBiometricSysUiManager, 202, i2, (Bundle) null));
            } else {
                this.mSysUiManager.sendCommand(null, this.mSysUiSessionId, 202, i2);
            }
            if (i2 == 10002) {
                SemUdfpsTspManager semUdfpsTspManager = this.mUdfpsTspManager;
                synchronized (semUdfpsTspManager) {
                    semUdfpsTspManager.sendCommand(9);
                }
            }
        } else {
            this.mSysUiManager.sendCommand(null, this.mSysUiSessionId, 201, i);
        }
        if (FingerprintUtils.semIsQualityFailedEvent(i, i2)) {
            setTspModeControl();
        }
    }

    public final void handleOnAuthenticated(boolean z) {
        if (!this.mIsAuthenticated) {
            this.mIsAuthenticated = z;
            SemBiometricSysUiManager semBiometricSysUiManager = this.mSysUiManager;
            int i = this.mSysUiSessionId;
            semBiometricSysUiManager.getClass();
            semBiometricSysUiManager.handleRequest(
                    new SemBiometricSysUiManager$$ExternalSyntheticLambda10(
                            semBiometricSysUiManager, i, 2, z, null));
            setTspModeControl();
            return;
        }
        if (this.mIsKeyguard) {
            this.mAuthFailCount = 0;
            SemUdfpsTspManager semUdfpsTspManager = this.mUdfpsTspManager;
            synchronized (semUdfpsTspManager) {
                semUdfpsTspManager.mIsInterruptDelayModeOn = false;
                semUdfpsTspManager.mInterruptDelay = 0;
            }
        }
    }

    public final void handleOnError(int i, int i2) {
        if (i == 8) {
            this.mSysUiManager.sendCommand(null, this.mSysUiSessionId, 204, i2);
        } else {
            this.mSysUiManager.sendCommand(null, this.mSysUiSessionId, 203, i);
        }
        boolean z = SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL;
        if (z) {
            if (z && SemBiometricFeature.FP_FEATURE_HW_LIGHT_SOURCE) {
                boolean z2 = SemUdfpsHelper.DEBUG;
                SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl.setHwLightMode("0");
            }
            this.mUdfpsTspManager.disable(this.mHashCode);
        }
    }

    public final void handleOnPause() {
        this.mSysUiManager.sendCommand(null, this.mSysUiSessionId, 112, 0);
    }

    public final void handleOnResume() {
        this.mSysUiManager.sendCommand(null, this.mSysUiSessionId, 113, 0);
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
    public final void onDismissed(int i, byte[] bArr) {
        getHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemUdfpsSysUiImpl.SysUiCallback sysUiCallback =
                                        SemUdfpsSysUiImpl.this.mSysUiCallback;
                                if (sysUiCallback != null) {
                                    sysUiCallback.onSysUiDismissed();
                                }
                            }
                        });
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
    public final void onError(final int i, final int i2) {
        getHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemUdfpsSysUiImpl semUdfpsSysUiImpl = SemUdfpsSysUiImpl.this;
                                int i3 = i;
                                int i4 = i2;
                                SemUdfpsSysUiImpl.SysUiCallback sysUiCallback =
                                        semUdfpsSysUiImpl.mSysUiCallback;
                                if (sysUiCallback != null) {
                                    sysUiCallback.onSysUiError(i3, i4);
                                }
                            }
                        });
    }

    public final void setSysUiListener(SysUiCallback sysUiCallback) {
        this.mIsBiometricPrompt = false;
        this.mSysUiCallback = sysUiCallback;
        this.mSysUiSessionId = this.mSysUiManager.openSession(toString(), this);
    }

    public final void setTspModeControl() {
        if (this.mIsKeyguard) {
            int i = this.mAuthFailCount + 1;
            this.mAuthFailCount = i;
            if (i < 3 || i > 25) {
                return;
            }
            SemUdfpsTspManager semUdfpsTspManager = this.mUdfpsTspManager;
            synchronized (semUdfpsTspManager) {
                if (i >= 3 && i < 5) {
                    semUdfpsTspManager.mInterruptDelay = 3;
                } else if (i >= 5 && i < 10) {
                    semUdfpsTspManager.mInterruptDelay = 5;
                } else if (i >= 10 && i < 15) {
                    semUdfpsTspManager.mInterruptDelay = 7;
                } else if (i >= 15 && i < 25) {
                    semUdfpsTspManager.mInterruptDelay = 10;
                } else if (semUdfpsTspManager.mInterruptDelay >= 25) {
                    semUdfpsTspManager.mInterruptDelay = 15;
                }
                semUdfpsTspManager.mIsInterruptDelayModeOn = true;
                semUdfpsTspManager.sendCommand(10);
            }
        }
    }

    public final void start() {
        if (!this.mIsBiometricPrompt) {
            SemBiometricSysUiManager semBiometricSysUiManager = this.mSysUiManager;
            int i = this.mSysUiSessionId;
            if (this.mSysUiType == 0) {
                this.mSysUiType = this.mHideAuthenticationGuide ? 2 : 1;
            }
            int i2 = this.mSysUiType;
            Bundle bundle = this.mCustomIconAttribute;
            boolean z = this.mIsRequireTouchBlock;
            int i3 = this.mUserId;
            semBiometricSysUiManager.getClass();
            semBiometricSysUiManager.handleRequest(
                    new SemBiometricSysUiManager$$ExternalSyntheticLambda5(
                            semBiometricSysUiManager,
                            i,
                            i2,
                            z,
                            i3,
                            this.mPackageName,
                            0L,
                            bundle,
                            null));
        }
        this.mUdfpsTspManager.enable(this.mHashCode);
    }

    public final void stop() {
        if (!this.mIsBiometricPrompt) {
            SemBiometricSysUiManager semBiometricSysUiManager = this.mSysUiManager;
            int i = this.mSysUiSessionId;
            semBiometricSysUiManager.getClass();
            semBiometricSysUiManager.handleRequest(
                    new SemBiometricSysUiManager$$ExternalSyntheticLambda8(
                            semBiometricSysUiManager, i));
        }
        this.mUdfpsTspManager.disable(this.mHashCode);
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL
                && SemBiometricFeature.FP_FEATURE_HW_LIGHT_SOURCE) {
            boolean z = SemUdfpsHelper.DEBUG;
            SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl.setHwLightMode("0");
        }
        if (this.mIsKeyguard) {
            this.mAuthFailCount = 0;
            SemUdfpsTspManager semUdfpsTspManager = this.mUdfpsTspManager;
            synchronized (semUdfpsTspManager) {
                semUdfpsTspManager.mIsInterruptDelayModeOn = false;
                semUdfpsTspManager.mInterruptDelay = 0;
            }
        }
    }
}
