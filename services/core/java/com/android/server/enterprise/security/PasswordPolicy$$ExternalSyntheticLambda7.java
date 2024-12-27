package com.android.server.enterprise.security;

import android.os.RemoteException;
import android.os.UserManager;
import android.provider.Settings;
import android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;

import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda7
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PasswordPolicy f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda7(
            PasswordPolicy passwordPolicy, int i, boolean z, int i2) {
        this.$r8$classId = i2;
        this.f$0 = passwordPolicy;
        this.f$1 = i;
        this.f$2 = z;
    }

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda7(
            PasswordPolicy passwordPolicy, boolean z, int i) {
        this.$r8$classId = 1;
        this.f$0 = passwordPolicy;
        this.f$2 = z;
        this.f$1 = i;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                PasswordPolicy passwordPolicy = this.f$0;
                int i = this.f$1;
                boolean z = this.f$2;
                SystemUIAdapter systemUIAdapter =
                        SystemUIAdapter.getInstance(passwordPolicy.mContext);
                systemUIAdapter.getClass();
                Log.d("SystemUIAdapter", "setMultifactorAuthEnabled()");
                if (SystemUIAdapter.isCalledFromSystem()
                        && i == systemUIAdapter.getCurrentUserId()) {
                    try {
                        Iterator it = systemUIAdapter.mCallbacks.entrySet().iterator();
                        while (it.hasNext()) {
                            ISystemUIAdapterCallback iSystemUIAdapterCallback =
                                    (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                            if (iSystemUIAdapterCallback != null) {
                                iSystemUIAdapterCallback.setMultifactorAuthEnabled(z);
                            }
                        }
                        break;
                    } catch (RemoteException e) {
                        Log.e(
                                "SystemUIAdapter",
                                "setMultifactorAuthEnabled() Failed with RemoteException",
                                e);
                        return;
                    } catch (NullPointerException unused) {
                        Log.e(
                                "SystemUIAdapter",
                                "setMultifactorAuthEnabled() failed with NullPointerException.");
                        return;
                    } catch (Exception e2) {
                        Log.e(
                                "SystemUIAdapter",
                                "setMultifactorAuthEnabled() Failed with Exception",
                                e2);
                        return;
                    }
                }
                break;
            case 1:
                PasswordPolicy passwordPolicy2 = this.f$0;
                boolean z2 = this.f$2;
                int i2 = this.f$1;
                passwordPolicy2.getClass();
                new LockPatternUtils(passwordPolicy2.mContext).setVisiblePatternEnabled(z2, i2);
                UserManager userManager =
                        (UserManager) passwordPolicy2.mContext.getSystemService("user");
                if (userManager.getUserInfo(i2).isManagedProfile()
                        && !new LockPatternUtils(passwordPolicy2.mContext)
                                .isSeparateProfileChallengeEnabled(i2)) {
                    new LockPatternUtils(passwordPolicy2.mContext)
                            .setVisiblePatternEnabled(
                                    z2,
                                    userManager
                                            .getProfileParent(i2)
                                            .getUserHandle()
                                            .getIdentifier());
                    break;
                }
                break;
            case 2:
                PasswordPolicy passwordPolicy3 = this.f$0;
                int i3 = this.f$1;
                boolean z3 = this.f$2;
                if (passwordPolicy3.isPasswordVisibilityEnabledAsUser(i3)
                        && !z3
                        && !passwordPolicy3.isPersona(i3)) {
                    Log.d("PasswordPolicy", "do not putIntForUser");
                    Settings.System.putIntForUser(
                            passwordPolicy3.mContext.getContentResolver(), "show_password", 0, i3);
                }
                if (((UserManager) passwordPolicy3.mContext.getSystemService("user"))
                                .getUserInfo(i3)
                                .isManagedProfile()
                        && !new LockPatternUtils(passwordPolicy3.mContext)
                                .isSeparateProfileChallengeEnabled(i3)) {
                    Log.d("PasswordPolicy", "!hasSeparateChallenge");
                    Settings.System.putIntForUser(
                            passwordPolicy3.mContext.getContentResolver(), "show_password", 0, i3);
                    break;
                }
                break;
            case 3:
                PasswordPolicy passwordPolicy4 = this.f$0;
                int i4 = this.f$1;
                boolean z4 = this.f$2;
                SystemUIAdapter systemUIAdapter2 =
                        SystemUIAdapter.getInstance(passwordPolicy4.mContext);
                systemUIAdapter2.getClass();
                Log.d(
                        "SystemUIAdapter",
                        "setPasswordVisibilityEnabledAsUser() userId = " + i4 + ", allow = " + z4);
                if (SystemUIAdapter.isCalledFromSystem()) {
                    boolean isSeparateProfileChallengeEnabled =
                            new LockPatternUtils(SystemUIAdapter.mContext)
                                    .isSeparateProfileChallengeEnabled(i4);
                    Log.d(
                            "SystemUIAdapter",
                            "setPasswordVisibilityEnabledAsUser() isSeparateProfileChallengeEnabled"
                                + " = "
                                    + isSeparateProfileChallengeEnabled);
                    if (i4 == systemUIAdapter2.getCurrentUserId()
                            || !isSeparateProfileChallengeEnabled) {
                        try {
                            Iterator it2 = systemUIAdapter2.mCallbacks.entrySet().iterator();
                            while (it2.hasNext()) {
                                ISystemUIAdapterCallback iSystemUIAdapterCallback2 =
                                        (ISystemUIAdapterCallback)
                                                ((Map.Entry) it2.next()).getValue();
                                if (iSystemUIAdapterCallback2 != null) {
                                    iSystemUIAdapterCallback2.setPasswordVisibilityEnabled(z4);
                                }
                            }
                            break;
                        } catch (RemoteException e3) {
                            Log.e(
                                    "SystemUIAdapter",
                                    "setPasswordVisibilityEnabled() Failed with RemoteException",
                                    e3);
                            return;
                        } catch (NullPointerException unused2) {
                            Log.e(
                                    "SystemUIAdapter",
                                    "setPasswordVisibilityEnabled() failed with"
                                        + " NullPointerException.");
                            return;
                        } catch (Exception unused3) {
                            return;
                        }
                    }
                }
                break;
            default:
                PasswordPolicy passwordPolicy5 = this.f$0;
                int i5 = this.f$1;
                boolean z5 = this.f$2;
                SystemUIAdapter systemUIAdapter3 =
                        SystemUIAdapter.getInstance(passwordPolicy5.mContext);
                systemUIAdapter3.getClass();
                Log.d(
                        "SystemUIAdapter",
                        "excludeExternalStorageForFailedPasswordsWipeAsUser() userId = "
                                + i5
                                + ", exclude = "
                                + z5);
                if (SystemUIAdapter.isCalledFromSystem()) {
                    boolean isSeparateProfileChallengeEnabled2 =
                            new LockPatternUtils(SystemUIAdapter.mContext)
                                    .isSeparateProfileChallengeEnabled(i5);
                    Log.d(
                            "SystemUIAdapter",
                            "setMultifactorAuthEnabled()"
                                + " excludeExternalStorageForFailedPasswordsWipeAsUser = "
                                    + isSeparateProfileChallengeEnabled2);
                    if (i5 == systemUIAdapter3.getCurrentUserId()
                            || !isSeparateProfileChallengeEnabled2) {
                        try {
                            Iterator it3 = systemUIAdapter3.mCallbacks.entrySet().iterator();
                            while (it3.hasNext()) {
                                ISystemUIAdapterCallback iSystemUIAdapterCallback3 =
                                        (ISystemUIAdapterCallback)
                                                ((Map.Entry) it3.next()).getValue();
                                if (iSystemUIAdapterCallback3 != null) {
                                    iSystemUIAdapterCallback3
                                            .excludeExternalStorageForFailedPasswordsWipe(z5);
                                }
                            }
                            break;
                        } catch (RemoteException e4) {
                            Log.e(
                                    "SystemUIAdapter",
                                    "excludeExternalStorageForFailedPasswordsWipe() Failed with"
                                        + " RemoteException",
                                    e4);
                            return;
                        } catch (NullPointerException unused4) {
                            Log.e(
                                    "SystemUIAdapter",
                                    "excludeExternalStorageForFailedPasswordsWipe() failed with"
                                        + " NullPointerException.");
                        } catch (Exception unused5) {
                            return;
                        }
                    }
                }
                break;
        }
    }
}
