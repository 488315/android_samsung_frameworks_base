package com.android.server.biometrics.sensors.fingerprint;

import android.R;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricSysUiManager;
import com.android.server.biometrics.Utils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;

/* loaded from: classes.dex */
public class SemUdfpsSysUiImpl implements SemBiometricSysUiManager.SysUiListener {
  static final String DB_UDFPS_HIDE_AUTH_GUIDE_LAYER_PACKAGES =
      "config_biometric_udfps_flag_hide_auth_guide_layer_packages";
  public final Context mContext;
  public Bundle mCustomIconAttribute;
  boolean mHideAuthenticationGuide;
  public boolean mIsAuthenticated;
  public boolean mIsRequireTouchBlock;
  public final String mPackageName;
  public SysUiCallback mSysUiCallback;
  public int mSysUiSessionId;
  public int mSysUiType;
  public final IBinder mToken;
  public int mUserId;
  SemBiometricSysUiManager mSysUiManager = SemBiometricSysUiManager.get();
  SemUdfpsTspManager mUdfpsTspManager = SemUdfpsTspManager.get();
  public boolean mIsBiometricPrompt = true;
  public final int mHashCode = hashCode();

  public interface SysUiCallback {
    void onSysUiDismissed();

    void onSysUiError(int i, int i2);
  }

  public SemUdfpsSysUiImpl(Context context, IBinder iBinder, String str, int i, boolean z) {
    this.mContext = context;
    this.mToken = iBinder;
    this.mPackageName = str;
    this.mUserId = i;
    this.mHideAuthenticationGuide = z;
  }

  @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
  public void onDismissed(int i, byte[] bArr) {
    SemFpMainThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                SemUdfpsSysUiImpl.this.lambda$onDismissed$0();
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onDismissed$0() {
    SysUiCallback sysUiCallback = this.mSysUiCallback;
    if (sysUiCallback != null) {
      sysUiCallback.onSysUiDismissed();
    }
  }

  @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
  public void onError(final int i, final int i2) {
    SemFpMainThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SemUdfpsSysUiImpl.this.lambda$onError$1(i, i2);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onError$1(int i, int i2) {
    SysUiCallback sysUiCallback = this.mSysUiCallback;
    if (sysUiCallback != null) {
      sysUiCallback.onSysUiError(i, i2);
    }
  }

  public void setSysUiType(int i) {
    this.mSysUiType = i;
  }

  public void setSysUiListener(SysUiCallback sysUiCallback) {
    this.mIsBiometricPrompt = false;
    this.mSysUiCallback = sysUiCallback;
    this.mSysUiSessionId = this.mSysUiManager.openSession(toString(), this);
  }

  public void checkGuideLayerAndTouchBlock(int i) {
    if (!this.mHideAuthenticationGuide && canHideAuthenticationGuideLayerWithoutFlag()) {
      Slog.i("Biometrics/SemUdfpsSysUiImpl", "ClientExtImpl: specific allowlist attr");
      this.mHideAuthenticationGuide = true;
    }
    this.mIsRequireTouchBlock =
        this.mHideAuthenticationGuide
            && (i & 16) == 0
            && Utils.inMultiWindowMode(this.mPackageName);
  }

  public final boolean canHideAuthenticationGuideLayerWithoutFlag() {
    String[] allowedPackagesForHideAuthenticationGuideLayer =
        getAllowedPackagesForHideAuthenticationGuideLayer();
    if (allowedPackagesForHideAuthenticationGuideLayer != null) {
      for (String str : allowedPackagesForHideAuthenticationGuideLayer) {
        if (str != null && str.contentEquals(this.mPackageName)) {
          return true;
        }
      }
    }
    return false;
  }

  public final String[] getAllowedPackagesForHideAuthenticationGuideLayer() {
    String stringForUser =
        Settings.Secure.getStringForUser(
            this.mContext.getContentResolver(), DB_UDFPS_HIDE_AUTH_GUIDE_LAYER_PACKAGES, 0);
    String[] split =
        !TextUtils.isEmpty(stringForUser)
            ? stringForUser.split(KnoxVpnFirewallHelper.DELIMITER)
            : null;
    return (split == null || split.length < 1)
        ? this.mContext.getResources().getStringArray(R.array.config_reduceBrightColorsCoefficients)
        : split;
  }

  public void start() {
    if (!this.mIsBiometricPrompt) {
      this.mSysUiManager.show(
          this.mSysUiSessionId,
          getSysUiType(),
          this.mCustomIconAttribute,
          this.mIsRequireTouchBlock,
          this.mUserId,
          this.mPackageName,
          0L,
          null);
    }
    this.mUdfpsTspManager.enable(this.mHashCode);
  }

  public final int getSysUiType() {
    if (this.mSysUiType == 0) {
      this.mSysUiType = this.mHideAuthenticationGuide ? 2 : 1;
    }
    return this.mSysUiType;
  }

  public void stop() {
    if (!this.mIsBiometricPrompt) {
      this.mSysUiManager.hide(this.mSysUiSessionId, 0, 0);
    }
    this.mUdfpsTspManager.disable(this.mHashCode);
    hideLightSource();
  }

  public final void hideLightSource() {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL
        && SemBiometricFeature.FP_FEATURE_HW_LIGHT_SOURCE) {
      SemUdfpsHelper.getInstance().getOpticalSensorHelper().turnOffHwLightMode();
    }
  }

  public void destroy() {
    stop();
    this.mSysUiManager.closeSession(this.mSysUiSessionId);
  }

  public void handleOnAuthenticated(boolean z) {
    if (this.mIsAuthenticated) {
      return;
    }
    this.mIsAuthenticated = z;
    this.mSysUiManager.onAuthenticated(this.mSysUiSessionId, 2, z, null);
  }

  public void handleOnAcquired(int i, int i2) {
    if (i == 6) {
      if (this.mIsBiometricPrompt) {
        this.mSysUiManager.sendCommandIfSessionExist(202, i2, null);
      } else {
        this.mSysUiManager.sendCommand(this.mSysUiSessionId, 202, i2, null);
      }
      if (i2 == 10002) {
        this.mUdfpsTspManager.onCaptureStarted();
        return;
      }
      return;
    }
    this.mSysUiManager.sendCommand(this.mSysUiSessionId, 201, i, null);
  }

  public void handleOnError(int i, int i2) {
    if (i == 8) {
      this.mSysUiManager.sendCommand(this.mSysUiSessionId, 204, i2, null);
    } else {
      this.mSysUiManager.sendCommand(this.mSysUiSessionId, 203, i, null);
    }
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
      hideLightSource();
      this.mUdfpsTspManager.disable(this.mHashCode);
    }
  }

  public void handleOnPause() {
    this.mSysUiManager.sendCommand(this.mSysUiSessionId, 112, 0, null);
  }

  public void handleOnResume() {
    this.mSysUiManager.sendCommand(this.mSysUiSessionId, 113, 0, null);
  }

  public void setCustomIconAttribute(Bundle bundle, int i) {
    if (bundle.getString("EXTRA_KEY_ICON_COLOR") == null
        && bundle.getString("EXTRA_KEY_ICON_CONTAINER_COLOR") == null
        && (i & 32) == 0) {
      return;
    }
    Bundle bundle2 = new Bundle();
    this.mCustomIconAttribute = bundle2;
    bundle2.putString("EXTRA_KEY_ICON_COLOR", bundle.getString("EXTRA_KEY_ICON_COLOR"));
    this.mCustomIconAttribute.putString(
        "EXTRA_KEY_ICON_CONTAINER_COLOR", bundle.getString("EXTRA_KEY_ICON_CONTAINER_COLOR"));
    this.mCustomIconAttribute.putInt("sem_privileged_attr", i);
  }
}
