package com.android.server.biometrics;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.statusbar.ISessionListener;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.biometrics.log.BiometricContext;
import com.samsung.android.knox.SemPersonaManager;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

/* loaded from: classes.dex */
public class SemBiometricSysUiWrapper {
  public final BiometricContext mBiometricContext;
  public final Context mContext;
  public final IntFunction mGetModalityFunc;
  public final IStatusBarService mStatusBarService;
  public final InstanceIdSequence mInstanceIdGenerator = new InstanceIdSequence(1048576);
  public final Map mSessionToInstanceId = new HashMap();
  public final Map mSessions = new HashMap(2);
  public final SemBiometricSysUiManager mSysUiManager = SemBiometricSysUiManager.get();

  public SemBiometricSysUiWrapper(
      Context context,
      BiometricContext biometricContext,
      IStatusBarService iStatusBarService,
      IntFunction intFunction) {
    this.mContext = context;
    this.mBiometricContext = biometricContext;
    this.mStatusBarService = iStatusBarService;
    this.mGetModalityFunc = intFunction;
  }

  public void openSession(
      IBinder iBinder, long j, IBiometricSysuiReceiver iBiometricSysuiReceiver) {
    if (iBiometricSysuiReceiver instanceof SemBiometricSysUiReceiver) {
      SemBiometricSysUiReceiver semBiometricSysUiReceiver =
          (SemBiometricSysUiReceiver) iBiometricSysuiReceiver;
      this.mSessions.put(
          Long.valueOf(j),
          new Pair(
              Integer.valueOf(
                  this.mSysUiManager.openSession(
                      iBinder.toString() + XmlUtils.STRING_ARRAY_SEPARATOR + j,
                      semBiometricSysUiReceiver.getSysUiListener())),
              semBiometricSysUiReceiver));
    }
  }

  public void closeSession(long j) {
    Pair pair = (Pair) this.mSessions.get(Long.valueOf(j));
    if (pair == null) {
      Slog.w("SemBiometricSysUiWrapper", "closeSession: no session info with " + j);
      return;
    }
    this.mSessions.remove(Long.valueOf(j));
    this.mSysUiManager.closeSession(((Integer) pair.first).intValue());
  }

  public void showAuthenticationDialog(
      PromptInfo promptInfo,
      IBiometricSysuiReceiver iBiometricSysuiReceiver,
      int[] iArr,
      boolean z,
      boolean z2,
      int i,
      long j,
      String str,
      long j2) {
    int i2;
    int credentialOwnerProfile = UserManager.get(this.mContext).getCredentialOwnerProfile(i);
    Pair pair = (Pair) this.mSessions.get(Long.valueOf(j2));
    if (pair == null) {
      Slog.w("SemBiometricSysUiWrapper", "showAuthenticationDialog: no session info with " + j2);
      return;
    }
    Bundle bundle = new Bundle();
    if (iArr != null && iArr.length > 0) {
      int i3 = 0;
      for (int i4 : iArr) {
        i3 |= ((Integer) this.mGetModalityFunc.apply(i4)).intValue();
      }
      bundle.putInt("KEY_AVAILABILITY_BIOMETRIC", i3);
      i2 =
          Utils.biometricAuthenticatorModalityToVendorSysUiType(
              ((Integer) this.mGetModalityFunc.apply(iArr[0])).intValue());
    } else {
      if (!z) {
        Slog.w("SemBiometricSysUiWrapper", "showAuthenticationDialog: Unknown prompt type");
        ((SemBiometricSysUiReceiver) pair.second).onSysUiError(3, -1);
        return;
      }
      i2 = 32768;
    }
    setManagedProfileInfo(credentialOwnerProfile, promptInfo, bundle);
    this.mSysUiManager.show(
        ((Integer) pair.first).intValue(),
        i2,
        bundle,
        z2,
        credentialOwnerProfile,
        str,
        j,
        promptInfo);
  }

  public final void setManagedProfileInfo(int i, PromptInfo promptInfo, Bundle bundle) {
    if (Utils.isWorkProfile(this.mContext, i)) {
      try {
        DevicePolicyManager devicePolicyManager =
            (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
        bundle.putBoolean("MANAGED_PROFILE", true);
        bundle.putInt("MANAGED_PROFILE_COLOR", devicePolicyManager.getOrganizationColorForUser(i));
      } catch (Exception e) {
        Slog.w("SemBiometricSysUiWrapper", "setManagedProfileInfo: " + e.getMessage());
      }
    }
    if (SemPersonaManager.isKnoxId(i) && Utils.isKnoxInternal(promptInfo.semGetPrivilegedFlag())) {
      if (SemPersonaManager.isSecureFolderId(i)) {
        bundle.putBoolean("SECURE_FOLDER", true);
        bundle.putString("SECURE_FOLDER_NAME", SemPersonaManager.getPersonaName(this.mContext, i));
        return;
      }
      bundle.putBoolean("MANAGED_PROFILE_KNOX", true);
      bundle.putString(
          "MANAGED_PROFILE_KNOX_NAME", SemPersonaManager.getPersonaName(this.mContext, i));
      bundle.putBoolean("MANAGED_PROFILE_KNOX_TWO_FACTOR", Utils.isKnoxTwoFactor(this.mContext, i));
      if (Utils.isKnoxTwoFactor(this.mContext, i)
          && Utils.isKnoxOnlyConfirmBiometric(promptInfo.semGetPrivilegedFlag())) {
        bundle.putBoolean("MANAGED_PROFILE_KNOX_ONLY_CONFIRM_BIOMETRIC", true);
      }
    }
  }

  public void onBiometricAuthenticated(int i) {
    onBiometricAuthenticated(i, "");
  }

  public void onBiometricAuthenticated(final int i, final String str) {
    this.mSessions.forEach(
        new BiConsumer() { // from class:
                           // com.android.server.biometrics.SemBiometricSysUiWrapper$$ExternalSyntheticLambda3
          @Override // java.util.function.BiConsumer
          public final void accept(Object obj, Object obj2) {
            SemBiometricSysUiWrapper.this.lambda$onBiometricAuthenticated$0(
                i, str, (Long) obj, (Pair) obj2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onBiometricAuthenticated$0(
      int i, String str, Long l, Pair pair) {
    this.mSysUiManager.onAuthenticated(((Integer) pair.first).intValue(), i, true, str);
  }

  public void onBiometricHelp(final int i, final int i2, final int i3, final String str) {
    this.mSessions.forEach(
        new BiConsumer() { // from class:
                           // com.android.server.biometrics.SemBiometricSysUiWrapper$$ExternalSyntheticLambda2
          @Override // java.util.function.BiConsumer
          public final void accept(Object obj, Object obj2) {
            SemBiometricSysUiWrapper.this.lambda$onBiometricHelp$1(
                i, i2, i3, str, (Long) obj, (Pair) obj2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onBiometricHelp$1(
      int i, int i2, int i3, String str, Long l, Pair pair) {
    this.mSysUiManager.onBiometricHelp(((Integer) pair.first).intValue(), i, i2, i3, str);
  }

  public void onBiometricError(final int i, final int i2, final int i3) {
    if (i2 == 100) {
      this.mSessions.forEach(
          new BiConsumer() { // from class:
                             // com.android.server.biometrics.SemBiometricSysUiWrapper$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
              SemBiometricSysUiWrapper.this.lambda$onBiometricError$2(i, (Long) obj, (Pair) obj2);
            }
          });
    } else {
      this.mSessions.forEach(
          new BiConsumer() { // from class:
                             // com.android.server.biometrics.SemBiometricSysUiWrapper$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
              SemBiometricSysUiWrapper.this.lambda$onBiometricError$3(
                  i, i2, i3, (Long) obj, (Pair) obj2);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onBiometricError$2(int i, Long l, Pair pair) {
    this.mSysUiManager.onAuthenticated(((Integer) pair.first).intValue(), i, false, null);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onBiometricError$3(int i, int i2, int i3, Long l, Pair pair) {
    this.mSysUiManager.onBiometricError(((Integer) pair.first).intValue(), i, i2, i3, null);
  }

  public void hideAuthenticationDialog(long j) {
    Pair pair = (Pair) this.mSessions.get(Long.valueOf(j));
    if (pair == null) {
      Slog.w("SemBiometricSysUiWrapper", "hideAuthenticationDialog: no session info with " + j);
      return;
    }
    this.mSysUiManager.hide(((Integer) pair.first).intValue(), 0, 0);
  }

  public synchronized void startSession(int i) {
    ISessionListener iSessionListener = this.mBiometricContext.getISessionListener();
    if (iSessionListener == null) {
      return;
    }
    if (this.mSessionToInstanceId.getOrDefault(Integer.valueOf(i), null) != null) {
      Slog.e("SemBiometricSysUiWrapper", "session [" + getString(i) + "] was already started");
      return;
    }
    InstanceId newInstanceId = this.mInstanceIdGenerator.newInstanceId();
    this.mSessionToInstanceId.put(Integer.valueOf(i), newInstanceId);
    try {
      if (Utils.DEBUG) {
        Slog.d(
            "SemBiometricSysUiWrapper",
            "Session start for [" + getString(i) + "] id=" + newInstanceId);
      }
      iSessionListener.onSessionStarted(i, newInstanceId);
    } catch (RemoteException e) {
      Slog.e(
          "SemBiometricSysUiWrapper",
          "Unable to send onSessionStarted for session=[" + getString(i) + "]",
          e);
    }
  }

  public synchronized void endSession(int i) {
    ISessionListener iSessionListener = this.mBiometricContext.getISessionListener();
    if (iSessionListener == null) {
      return;
    }
    if (this.mSessionToInstanceId.getOrDefault(Integer.valueOf(i), null) == null) {
      Slog.e("SemBiometricSysUiWrapper", "session [" + getString(i) + "] was not started");
      return;
    }
    InstanceId instanceId = (InstanceId) this.mSessionToInstanceId.get(Integer.valueOf(i));
    this.mSessionToInstanceId.put(Integer.valueOf(i), null);
    try {
      if (Utils.DEBUG) {
        Slog.d(
            "SemBiometricSysUiWrapper", "Session end for [" + getString(i) + "] id=" + instanceId);
      }
      iSessionListener.onSessionEnded(i, instanceId);
    } catch (RemoteException e) {
      Slog.e(
          "SemBiometricSysUiWrapper",
          "Unable to send onSessionEnded for session=[" + getString(i) + "]",
          e);
    }
  }

  public static String getString(int i) {
    if (i == 1) {
      return "KEYGUARD";
    }
    if (i == 2) {
      return "BIOMETRIC_PROMPT";
    }
    return "unknownType=" + i;
  }
}
