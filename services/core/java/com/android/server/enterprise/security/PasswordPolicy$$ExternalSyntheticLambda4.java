package com.android.server.enterprise.security;

import android.os.RemoteException;
import android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;

import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda4
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PasswordPolicy f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda4(
            PasswordPolicy passwordPolicy, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = passwordPolicy;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                PasswordPolicy passwordPolicy = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                SystemUIAdapter systemUIAdapter =
                        SystemUIAdapter.getInstance(passwordPolicy.mContext);
                if (i <= 0) {
                    systemUIAdapter.setMaximumFailedPasswordsForDisableAsUser(i2, i, null);
                    break;
                } else {
                    systemUIAdapter.setMaximumFailedPasswordsForDisableAsUser(
                            i2,
                            i,
                            passwordPolicy
                                    .mContext
                                    .getPackageManager()
                                    .getNameForUid(
                                            passwordPolicy.mEdmStorageProvider.getAdminByField(
                                                    "PASSWORD",
                                                    "passwordAttemptDeviceDisable",
                                                    Integer.toString(i))));
                    break;
                }
            case 1:
                PasswordPolicy passwordPolicy2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                passwordPolicy2.getClass();
                LockPatternUtils lockPatternUtils = new LockPatternUtils(passwordPolicy2.mContext);
                Log.d("PasswordPolicy", "removeBiometricAuthentication()");
                if ((i3 & 1) != 0) {
                    lockPatternUtils.setBiometricState(1, 0, i4);
                }
                if ((i3 & 4) != 0) {
                    lockPatternUtils.setBiometricState(256, 0, i4);
                    break;
                }
                break;
            case 2:
                PasswordPolicy passwordPolicy3 = this.f$0;
                int i5 = this.f$1;
                int i6 = this.f$2;
                SystemUIAdapter systemUIAdapter2 =
                        SystemUIAdapter.getInstance(passwordPolicy3.mContext);
                systemUIAdapter2.getClass();
                Log.d(
                        "SystemUIAdapter",
                        "setPwdChangeRequestedAsUser() userId = " + i5 + ", flag = " + i6);
                if (SystemUIAdapter.isCalledFromSystem()
                        && i5 == systemUIAdapter2.getCurrentUserId()) {
                    try {
                        Iterator it = systemUIAdapter2.mCallbacks.entrySet().iterator();
                        while (it.hasNext()) {
                            ISystemUIAdapterCallback iSystemUIAdapterCallback =
                                    (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                            if (iSystemUIAdapterCallback != null) {
                                iSystemUIAdapterCallback.setPwdChangeRequested(i6);
                            }
                        }
                        break;
                    } catch (RemoteException e) {
                        Log.e(
                                "SystemUIAdapter",
                                "setPwdChangeRequested() Failed with RemoteException",
                                e);
                        return;
                    } catch (NullPointerException unused) {
                        Log.e(
                                "SystemUIAdapter",
                                "setPwdChangeRequested() failed with NullPointerException.");
                        return;
                    } catch (Exception unused2) {
                        return;
                    }
                }
                break;
            default:
                PasswordPolicy passwordPolicy4 = this.f$0;
                int i7 = this.f$1;
                int i8 = this.f$2;
                SystemUIAdapter systemUIAdapter3 =
                        SystemUIAdapter.getInstance(passwordPolicy4.mContext);
                systemUIAdapter3.getClass();
                Log.d(
                        "SystemUIAdapter",
                        "setPasswordLockDelayAsUser() userId = " + i7 + ", time = " + i8);
                if (SystemUIAdapter.isCalledFromSystem()) {
                    boolean isSeparateProfileChallengeEnabled =
                            new LockPatternUtils(SystemUIAdapter.mContext)
                                    .isSeparateProfileChallengeEnabled(i7);
                    Log.d(
                            "SystemUIAdapter",
                            "setMultifactorAuthEnabled() setPasswordLockDelayAsUser = "
                                    + isSeparateProfileChallengeEnabled);
                    if (i7 == systemUIAdapter3.getCurrentUserId()
                            || !isSeparateProfileChallengeEnabled) {
                        try {
                            Iterator it2 = systemUIAdapter3.mCallbacks.entrySet().iterator();
                            while (it2.hasNext()) {
                                ISystemUIAdapterCallback iSystemUIAdapterCallback2 =
                                        (ISystemUIAdapterCallback)
                                                ((Map.Entry) it2.next()).getValue();
                                if (iSystemUIAdapterCallback2 != null) {
                                    iSystemUIAdapterCallback2.setPasswordLockDelay(i8);
                                }
                            }
                            break;
                        } catch (RemoteException e2) {
                            Log.e(
                                    "SystemUIAdapter",
                                    "setPasswordLockDelay() Failed with RemoteException",
                                    e2);
                            return;
                        } catch (NullPointerException unused3) {
                            Log.e(
                                    "SystemUIAdapter",
                                    "setPasswordLockDelay() failed with NullPointerException.");
                        } catch (Exception unused4) {
                            return;
                        }
                    }
                }
                break;
        }
    }
}
