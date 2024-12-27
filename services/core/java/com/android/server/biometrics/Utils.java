package com.android.server.biometrics;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.biometrics.IBiometricService;
import android.hardware.input.InputManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Slog;
import android.view.HapticFeedbackConstants;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.vibrator.VibRune;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class Utils {
    public static final boolean DEBUG;
    public static boolean mDBCorrupted;

    static {
        DEBUG = Debug.semIsProductDev() || Build.IS_ENG || Build.IS_USERDEBUG;
    }

    public static int authenticatorStatusToBiometricConstant(int i) {
        switch (i) {
            case 1:
                return 0;
            case 2:
            case 4:
                return 12;
            case 3:
            case 6:
            default:
                return 1;
            case 5:
                return 15;
            case 7:
                return 11;
            case 8:
                return com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags.mandatoryBiometrics() ? 21 : 1;
            case 9:
                return 14;
            case 10:
                return 7;
            case 11:
                return 9;
            case 12:
                return 18;
            case 13:
                return 20;
        }
    }

    public static void checkPermission(Context context, String str) {
        context.enforceCallingOrSelfPermission(str, "Must have " + str + " permission.");
    }

    public static int getAuthenticationTypeForResult(int i) {
        if (i == 1 || i == 4) {
            return 2;
        }
        if (i == 7) {
            return 1;
        }
        throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unsupported dismissal reason: "));
    }

    public static String getClientName(BaseClientMonitor baseClientMonitor) {
        return baseClientMonitor != null ? baseClientMonitor.getClass().getSimpleName() : "null";
    }

    public static int getCurrentStrength(int i) {
        try {
            return IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")).getCurrentStrength(i);
        } catch (RemoteException e) {
            Slog.e("BiometricUtils", "RemoteException", e);
            return 0;
        }
    }

    public static int getIntDb(Context context, int i, int i2, String str, boolean z) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            i = z ? Settings.Secure.getIntForUser(contentResolver, str, i, i2) : Settings.System.getIntForUser(contentResolver, str, i, i2);
            return i;
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("getIntDb: "), "BiometricUtils");
            return i;
        }
    }

    public static int getUserOrWorkProfileId(Context context, int i) {
        int i2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (isWorkProfile(context, i)) {
                return i;
            }
            UserManager userManager = UserManager.get(context);
            if (userManager == null) {
                Slog.e("BiometricUtils", "Unable to acquire UserManager");
                return i;
            }
            UserInfo userInfo = userManager.getUserInfo(i);
            if (userInfo == null) {
                Slog.w("BiometricUtils", "Unable to acquire UserInfo: " + i);
                return i;
            }
            if (SemPersonaManager.isSecureFolderId(i)) {
                i2 = userInfo.profileGroupId;
                Slog.i("BiometricUtils", "SecureFolder, " + i2);
            } else {
                i2 = i;
            }
            if (SemPersonaManager.isAppSeparationUserId(i2)) {
                i2 = userInfo.profileGroupId;
                Slog.i("BiometricUtils", "AppSeparation, " + i2);
            }
            if (SemDualAppManager.isDualAppId(i2)) {
                Slog.i("BiometricUtils", "DualAppId, " + i2);
                UserInfo profileParent = userManager.getProfileParent(i2);
                if (profileParent != null) {
                    i2 = profileParent.id;
                }
            }
            int credentialOwnerProfile = userManager.getCredentialOwnerProfile(i2);
            if (i != credentialOwnerProfile) {
                Slog.v("BiometricUtils", "getUserOrWorkProfileId: change userId,  " + i + " > " + credentialOwnerProfile);
            }
            return credentialOwnerProfile;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean hasPermission(Context context, String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }

    public static boolean isAtLeastStrength(int i, int i2) {
        int i3 = i & 32767;
        if (((~i2) & i3) != 0) {
            return false;
        }
        for (int i4 = 1; i4 <= i2; i4 = (i4 << 1) | 1) {
            if (i4 == i3) {
                return true;
            }
        }
        Slog.e("BiometricService", "Unknown sensorStrength: " + i3 + ", requestedStrength: " + i2);
        return false;
    }

    public static boolean isBackground(String str) {
        Slog.v("BiometricUtils", "Checking if the authenticating is in background, clientPackage:" + str);
        List<ActivityManager.RunningTaskInfo> tasks = ActivityTaskManager.getInstance().getTasks(Integer.MAX_VALUE);
        if (tasks == null || tasks.isEmpty()) {
            Slog.d("BiometricUtils", "No running tasks reported");
            return true;
        }
        for (ActivityManager.RunningTaskInfo runningTaskInfo : tasks) {
            ComponentName componentName = runningTaskInfo.topActivity;
            if (componentName != null) {
                String packageName = componentName.getPackageName();
                if (packageName.contentEquals(str) && runningTaskInfo.isVisible()) {
                    return false;
                }
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Running task, top: ", packageName, ", isVisible: ");
                m.append(runningTaskInfo.isVisible());
                Slog.i("BiometricUtils", m.toString());
            }
        }
        return true;
    }

    public static boolean isCredentialRequested(int i) {
        return (i & 32768) != 0;
    }

    public static boolean isCurrentUserOrProfile(Context context, int i) {
        UserManager userManager = UserManager.get(context);
        if (userManager == null) {
            Slog.e("BiometricUtils", "Unable to get UserManager");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int[] enabledProfileIds = userManager.getEnabledProfileIds(ActivityManager.getCurrentUser());
            if (enabledProfileIds != null && enabledProfileIds.length != 0) {
                for (int i2 : enabledProfileIds) {
                    if (i2 == i) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                }
                return false;
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isDebugEnabled(Context context, int i) {
        if (i == -10000) {
            return false;
        }
        return (Build.IS_ENG || Build.IS_USERDEBUG) && Settings.Secure.getIntForUser(context.getContentResolver(), "biometric_debug_enabled", 0, i) != 0;
    }

    public static boolean isFingerprintVirtualEnabled(Context context) {
        if (Build.isDebuggable()) {
            return Settings.Secure.getIntForUser(context.getContentResolver(), "biometric_fingerprint_virtual_enabled", 0, -2) == 1 || Settings.Secure.getIntForUser(context.getContentResolver(), "biometric_virtual_enabled", 0, -2) == 1;
        }
        return false;
    }

    public static boolean isFlipFolded(Context context) {
        InputManager inputManager;
        return SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FLIP && (inputManager = (InputManager) context.getSystemService("input")) != null && inputManager.semGetLidState() == 1;
    }

    public static boolean isFlipOpened(Context context) {
        InputManager inputManager;
        return (!SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FLIP || (inputManager = (InputManager) context.getSystemService("input")) == null || inputManager.semGetLidState() == 1) ? false : true;
    }

    public static boolean isFolderFolded(Context context) {
        InputManager inputManager;
        return SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD && (inputManager = (InputManager) context.getSystemService("input")) != null && inputManager.semGetLidState() == 1;
    }

    public static boolean isFolderOpened(Context context) {
        InputManager inputManager;
        return (!SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD || (inputManager = (InputManager) context.getSystemService("input")) == null || inputManager.semGetLidState() == 1) ? false : true;
    }

    public static boolean isForeground(int i, int i2) {
        List runningAppProcesses;
        try {
            runningAppProcesses = ActivityManager.getService().getRunningAppProcesses();
        } catch (RemoteException unused) {
            Slog.w("BiometricUtils", "am.getRunningAppProcesses() failed");
        }
        if (runningAppProcesses == null) {
            Slog.e("BiometricUtils", "No running app processes found, defaulting to true");
            return true;
        }
        for (int i3 = 0; i3 < runningAppProcesses.size(); i3++) {
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = (ActivityManager.RunningAppProcessInfo) runningAppProcesses.get(i3);
            if (runningAppProcessInfo.pid == i2 && runningAppProcessInfo.uid == i && runningAppProcessInfo.importance <= 125) {
                return true;
            }
        }
        return false;
    }

    public static boolean isKeyguard(Context context, String str) {
        boolean z = context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") == 0;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getResources().getString(R.string.emailTypeOther));
        String packageName = unflattenFromString != null ? unflattenFromString.getPackageName() : null;
        return z && packageName != null && packageName.equals(str);
    }

    public static boolean isStrongBiometric(int i) {
        try {
            return isAtLeastStrength(IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")).getCurrentStrength(i), 15);
        } catch (RemoteException e) {
            Slog.e("BiometricUtils", "RemoteException", e);
            return false;
        }
    }

    public static boolean isSystem(Context context, String str) {
        return context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") == 0 && "android".equals(str);
    }

    public static boolean isTalkBackEnabled(Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        return accessibilityManager != null && accessibilityManager.semIsScreenReaderEnabled();
    }

    public static boolean isValidAuthenticatorConfig(Context context, int i) {
        if (i == 0) {
            return true;
        }
        if (((com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags.mandatoryBiometrics() ? -131072 : -65536) & i) != 0) {
            NandswapManager$$ExternalSyntheticOutline0.m(i, "Non-biometric, non-credential bits found. Authenticators: ", "BiometricService");
            return false;
        }
        int i2 = i & 32767;
        if ((i2 == 0 && isCredentialRequested(i)) || i2 == 15 || i2 == 255) {
            return true;
        }
        if ((65536 & i) != 0) {
            context.enforceCallingOrSelfPermission("android.permission.SET_BIOMETRIC_DIALOG_ADVANCED", "Must have SET_BIOMETRIC_DIALOG_ADVANCED permission");
            return true;
        }
        NandswapManager$$ExternalSyntheticOutline0.m(i, "Unsupported biometric flags. Authenticators: ", "BiometricService");
        return false;
    }

    public static boolean isWorkProfile(Context context, int i) {
        if (SemPersonaManager.isSecureFolderId(i)) {
            Slog.d("BiometricUtils", "useOwnerBiometrics: SecureFolder profile!");
        } else if (SemPersonaManager.isAppSeparationUserId(i)) {
            Slog.d("BiometricUtils", "useOwnerBiometrics: AppSeparation profile!");
        } else {
            if (!SemDualAppManager.isDualAppId(i)) {
                UserManager userManager = UserManager.get(context);
                if (userManager != null) {
                    UserInfo userInfo = userManager.getUserInfo(i);
                    return userInfo != null && userInfo.isManagedProfile();
                }
                Slog.e("BiometricUtils", "isWorkProfile: Unable to acquire UserManager");
                return false;
            }
            Slog.d("BiometricUtils", "useOwnerBiometrics: DualApp profile!");
        }
        return false;
    }

    public static boolean listContains(int i, int[] iArr) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static int propertyStrengthToAuthenticatorStrength(int i) {
        if (i == 0) {
            return 4095;
        }
        if (i == 1) {
            return IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        }
        if (i == 2) {
            return 15;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(i, "propertyStrengthToAuthenticatorStrength: Unknown strength ", "BiometricUtils");
        return 4095;
    }

    public static void putIntDb(int i, Context context, String str, boolean z) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            if (z) {
                Settings.Secure.putIntForUser(contentResolver, str, i, -2);
            } else {
                Settings.System.putIntForUser(contentResolver, str, i, -2);
            }
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("putIntDb: "), "BiometricUtils");
        }
    }

    public static void putLongDb(Context context, long j, int i) {
        try {
            Settings.Secure.putLongForUser(context.getContentResolver(), "biometrics_strong_enroll_timestamp", j, i);
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("putIntDb: "), "BiometricUtils");
        }
    }

    public static byte[] readFile(File file) {
        FileInputStream fileInputStream;
        byte[] bArr = null;
        if (!file.exists()) {
            Slog.i("BiometricUtils", "Invalid file info, " + file);
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                bArr = new byte[(int) file.length()];
            } finally {
            }
        } catch (IOException e) {
            Slog.w("BiometricUtils", "failed to read file", e);
        }
        if (fileInputStream.read(bArr) > 0) {
            fileInputStream.close();
            return bArr;
        }
        fileInputStream.close();
        return bArr;
    }

    public static void registerBroadcastAsUser(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, UserHandle userHandle, Handler handler) {
        try {
            context.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, null, handler);
        } catch (Exception e) {
            Slog.i("BiometricUtils", "registerBroadcast: failed to set receiver", e);
        }
    }

    public static void semVibrate(Context context, int i) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Vibrator.class);
        if (vibrator == null) {
            Slog.w("BiometricUtils", "semVibrate: No vibrator service");
            return;
        }
        if (VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR && vibrator.semGetSupportedVibrationType() == 1 && i == 113) {
            i = 100;
        }
        if (vibrator.semGetSupportedVibrationType() >= 1) {
            vibrator.vibrate(VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(i), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
        } else if (DEBUG) {
            Slog.d("BiometricUtils", "semVibrate: supported type=" + vibrator.semGetSupportedVibrationType());
        }
    }

    public static void unregisterBroadcast(Context context, BroadcastReceiver broadcastReceiver) {
        try {
            context.unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            Slog.i("BiometricUtils", "unregisterBroadcast: failed to set receiver" + e.getMessage());
        }
    }

    public static void writeFile(File file, byte[] bArr) {
        if (bArr == null) {
            return;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(bArr);
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.w("BiometricUtils", "failed to write file", e);
        }
    }
}
