package com.android.server.biometrics;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.hardware.biometrics.PromptInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserManager;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.logging.InstanceIdSequence;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextProvider;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public final class SemBiometricSysUiWrapper {
    public final BiometricContext mBiometricContext;
    public final Context mContext;
    public Pair mCurrentSession;
    public final IntFunction mGetModalityFunc;
    public final Handler mHandler;
    public final InstanceIdSequence mInstanceIdGenerator = new InstanceIdSequence(1048576);
    public final Map mSessionToInstanceId = new HashMap();
    public final Map mSessions = new HashMap(2);
    public final SemBiometricSysUiManager mSysUiManager = SemBiometricSysUiManager.sInstance;
    public final SemBiometricSysUiWrapper$$ExternalSyntheticLambda0 mShowDialogWatchdog =
            new Runnable() { // from class:
                             // com.android.server.biometrics.SemBiometricSysUiWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SemBiometricSysUiWrapper semBiometricSysUiWrapper =
                            SemBiometricSysUiWrapper.this;
                    if (semBiometricSysUiWrapper.mCurrentSession != null) {
                        Slog.d(
                                "SemBiometricSysUiWrapper",
                                "ShowDialogWatchdog: No response from BP UI");
                        BiometricService.AnonymousClass2 anonymousClass2 =
                                (BiometricService.AnonymousClass2)
                                        semBiometricSysUiWrapper.mCurrentSession.second;
                        BiometricService.this.mHandler.post(
                                new BiometricService$2$$ExternalSyntheticLambda4(
                                        anonymousClass2, anonymousClass2.val$requestId, 3, -1));
                    }
                }
            };

    public SemBiometricSysUiWrapper(
            Context context,
            Handler handler,
            BiometricContextProvider biometricContextProvider,
            BiometricService$$ExternalSyntheticLambda0 biometricService$$ExternalSyntheticLambda0) {
        this.mContext = context;
        this.mHandler = handler;
        this.mBiometricContext = biometricContextProvider;
        this.mGetModalityFunc = biometricService$$ExternalSyntheticLambda0;
    }

    public final void hideAuthenticationDialog(long j) {
        Pair pair = (Pair) ((HashMap) this.mSessions).get(Long.valueOf(j));
        if (pair == null) {
            Slog.w(
                    "SemBiometricSysUiWrapper",
                    "hideAuthenticationDialog: no session info with " + j);
        } else {
            int intValue = ((Integer) pair.first).intValue();
            SemBiometricSysUiManager semBiometricSysUiManager = this.mSysUiManager;
            semBiometricSysUiManager.getClass();
            semBiometricSysUiManager.handleRequest(
                    new SemBiometricSysUiManager$$ExternalSyntheticLambda8(
                            semBiometricSysUiManager, intValue));
        }
    }

    public final void onBiometricError(final int i, final int i2, final int i3) {
        if (i2 == 100) {
            ((HashMap) this.mSessions)
                    .forEach(
                            new BiConsumer() { // from class:
                                               // com.android.server.biometrics.SemBiometricSysUiWrapper$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    SemBiometricSysUiWrapper semBiometricSysUiWrapper =
                                            SemBiometricSysUiWrapper.this;
                                    int i4 = i;
                                    semBiometricSysUiWrapper.getClass();
                                    int intValue = ((Integer) ((Pair) obj2).first).intValue();
                                    SemBiometricSysUiManager semBiometricSysUiManager =
                                            semBiometricSysUiWrapper.mSysUiManager;
                                    semBiometricSysUiManager.getClass();
                                    semBiometricSysUiManager.handleRequest(
                                            new SemBiometricSysUiManager$$ExternalSyntheticLambda10(
                                                    semBiometricSysUiManager,
                                                    intValue,
                                                    i4,
                                                    false,
                                                    null));
                                }
                            });
        } else {
            ((HashMap) this.mSessions)
                    .forEach(
                            new BiConsumer() { // from class:
                                               // com.android.server.biometrics.SemBiometricSysUiWrapper$$ExternalSyntheticLambda2
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    SemBiometricSysUiWrapper semBiometricSysUiWrapper =
                                            SemBiometricSysUiWrapper.this;
                                    int i4 = i;
                                    int i5 = i2;
                                    int i6 = i3;
                                    semBiometricSysUiWrapper.getClass();
                                    int intValue = ((Integer) ((Pair) obj2).first).intValue();
                                    SemBiometricSysUiManager semBiometricSysUiManager =
                                            semBiometricSysUiWrapper.mSysUiManager;
                                    semBiometricSysUiManager.getClass();
                                    semBiometricSysUiManager.handleRequest(
                                            new SemBiometricSysUiManager$$ExternalSyntheticLambda11(
                                                    semBiometricSysUiManager,
                                                    intValue,
                                                    i4,
                                                    i5,
                                                    i6));
                                }
                            });
        }
    }

    public final void showAuthenticationDialog(
            PromptInfo promptInfo,
            int[] iArr,
            boolean z,
            boolean z2,
            int i,
            long j,
            String str,
            long j2) {
        int credentialOwnerProfile = UserManager.get(this.mContext).getCredentialOwnerProfile(i);
        Pair pair = (Pair) ((HashMap) this.mSessions).get(Long.valueOf(j2));
        if (pair == null) {
            Slog.w(
                    "SemBiometricSysUiWrapper",
                    "showAuthenticationDialog: no session info with " + j2);
            return;
        }
        Bundle bundle = new Bundle();
        int i2 = 32768;
        if (iArr != null && iArr.length > 0) {
            int i3 = 0;
            for (int i4 : iArr) {
                i3 |= ((Integer) this.mGetModalityFunc.apply(i4)).intValue();
            }
            bundle.putInt("KEY_AVAILABILITY_BIOMETRIC", i3);
            int intValue = ((Integer) this.mGetModalityFunc.apply(iArr[0])).intValue();
            boolean z3 = Utils.DEBUG;
            if (intValue != 1) {
                if (intValue == 2) {
                    i2 = 8;
                } else if (intValue == 4) {
                    i2 = 8192;
                } else if (intValue == 8) {
                    i2 = 4096;
                } else if (intValue != 256) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            intValue,
                            "biometricAuthenticatorModalityToVendorSysUiType: Unknown modality:",
                            "BiometricUtils");
                    i2 = 0;
                } else {
                    i2 = EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
                }
            }
        } else if (!z) {
            Slog.w("SemBiometricSysUiWrapper", "showAuthenticationDialog: Unknown prompt type");
            BiometricService.AnonymousClass2 anonymousClass2 =
                    (BiometricService.AnonymousClass2) pair.second;
            BiometricService.this.mHandler.post(
                    new BiometricService$2$$ExternalSyntheticLambda4(
                            anonymousClass2, anonymousClass2.val$requestId, 3, -1));
            return;
        }
        int i5 = i2;
        if (Utils.isWorkProfile(this.mContext, credentialOwnerProfile)) {
            try {
                DevicePolicyManager devicePolicyManager =
                        (DevicePolicyManager)
                                this.mContext.getSystemService(DevicePolicyManager.class);
                bundle.putBoolean("MANAGED_PROFILE", true);
                bundle.putInt(
                        "MANAGED_PROFILE_COLOR",
                        devicePolicyManager.getOrganizationColorForUser(credentialOwnerProfile));
            } catch (Exception e) {
                MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("setManagedProfileInfo: "),
                        "SemBiometricSysUiWrapper");
            }
        }
        if (SemPersonaManager.isKnoxId(credentialOwnerProfile)
                && (promptInfo.semGetPrivilegedFlag() & 32) != 0) {
            if (SemPersonaManager.isSecureFolderId(credentialOwnerProfile)) {
                bundle.putBoolean("SECURE_FOLDER", true);
                bundle.putString(
                        "SECURE_FOLDER_NAME",
                        SemPersonaManager.getPersonaName(this.mContext, credentialOwnerProfile));
            } else {
                bundle.putBoolean("MANAGED_PROFILE_KNOX", true);
                bundle.putString(
                        "MANAGED_PROFILE_KNOX_NAME",
                        SemPersonaManager.getPersonaName(this.mContext, credentialOwnerProfile));
                bundle.putBoolean(
                        "MANAGED_PROFILE_KNOX_TWO_FACTOR",
                        Utils.getIntDb(
                                        this.mContext,
                                        0,
                                        credentialOwnerProfile,
                                        "knox_finger_print_plus",
                                        true)
                                == 1);
                if (Utils.getIntDb(
                                        this.mContext,
                                        0,
                                        credentialOwnerProfile,
                                        "knox_finger_print_plus",
                                        true)
                                == 1
                        && (promptInfo.semGetPrivilegedFlag() & 128) != 0) {
                    bundle.putBoolean("MANAGED_PROFILE_KNOX_ONLY_CONFIRM_BIOMETRIC", true);
                }
            }
        }
        int intValue2 = ((Integer) pair.first).intValue();
        SemBiometricSysUiManager semBiometricSysUiManager = this.mSysUiManager;
        semBiometricSysUiManager.getClass();
        semBiometricSysUiManager.handleRequest(
                new SemBiometricSysUiManager$$ExternalSyntheticLambda5(
                        semBiometricSysUiManager,
                        intValue2,
                        i5,
                        z2,
                        credentialOwnerProfile,
                        str,
                        j,
                        bundle,
                        promptInfo));
        this.mCurrentSession = pair;
        this.mHandler.postDelayed(this.mShowDialogWatchdog, 3000L);
    }
}
