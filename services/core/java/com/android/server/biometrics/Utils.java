package com.android.server.biometrics;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.WindowConfiguration;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.PromptInfo;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayStatus;
import android.hardware.input.InputManager;
import android.media.MediaRouter;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.IInstalld;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.view.HapticFeedbackConstants;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.vibrator.VibRune;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public abstract class Utils {
    public static final boolean DEBUG = Debug.semIsProductDev();

    public static int authenticatorStatusToBiometricConstant(int i) {
        switch (i) {
            case 1:
                return 0;
            case 2:
            case 4:
                return 12;
            case 3:
            case 6:
            case 8:
            default:
                return 1;
            case 5:
                return 15;
            case 7:
                return 11;
            case 9:
                return 14;
            case 10:
                return 7;
            case 11:
                return 9;
            case 12:
                return 18;
        }
    }

    public static boolean containsFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public static int getPublicBiometricStrength(int i) {
        return i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    }

    public static boolean isConfirmationSupported(int i) {
        return i == 4 || i == 8;
    }

    public static boolean isCredentialRequested(int i) {
        return (i & 32768) != 0;
    }

    public static boolean isKnoxInternal(int i) {
        return (i & 32) != 0;
    }

    public static boolean isKnoxOnlyConfirmBiometric(int i) {
        return (i & 128) != 0;
    }

    public static int removeBiometricBits(int i) {
        return i & (-32768);
    }

    public static int sepBiometricPromptTypeToBiometricAuthenticatorModality(int i) {
        int i2 = (i & 1) != 0 ? 2 : 0;
        if ((i & 2) != 0) {
            i2 |= 8;
        }
        if ((i & 4) != 0) {
            i2 |= 4;
        }
        return (i & 8) != 0 ? i2 | 256 : i2;
    }

    public static boolean isDebugEnabled(Context context, int i) {
        if (i == -10000) {
            return false;
        }
        return (Build.IS_ENG || Build.IS_USERDEBUG) && Settings.Secure.getIntForUser(context.getContentResolver(), "biometric_debug_enabled", 0, i) != 0;
    }

    public static boolean isVirtualEnabled(Context context) {
        return Build.isDebuggable() && Settings.Secure.getIntForUser(context.getContentResolver(), "biometric_virtual_enabled", 0, -2) == 1;
    }

    public static List filterAvailableHalInstances(Context context, List list) {
        if (list.size() <= 1) {
            return list;
        }
        int indexOf = list.indexOf("virtual");
        if (isVirtualEnabled(context) && indexOf != -1) {
            return List.of((String) list.get(indexOf));
        }
        ArrayList arrayList = new ArrayList(list);
        if (indexOf != -1) {
            arrayList.remove(indexOf);
        }
        return arrayList;
    }

    public static void combineAuthenticatorBundles(PromptInfo promptInfo) {
        int i;
        boolean isDeviceCredentialAllowed = promptInfo.isDeviceCredentialAllowed();
        promptInfo.setDeviceCredentialAllowed(false);
        if (promptInfo.getAuthenticators() != 0) {
            i = promptInfo.getAuthenticators();
        } else {
            i = isDeviceCredentialAllowed ? 33023 : IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        }
        promptInfo.setAuthenticators(i);
    }

    public static boolean isCredentialRequested(PromptInfo promptInfo) {
        return isCredentialRequested(promptInfo.getAuthenticators());
    }

    public static int getPublicBiometricStrength(PromptInfo promptInfo) {
        return getPublicBiometricStrength(promptInfo.getAuthenticators());
    }

    public static boolean isBiometricRequested(int i) {
        return getPublicBiometricStrength(i) != 0;
    }

    public static boolean isBiometricRequested(PromptInfo promptInfo) {
        return getPublicBiometricStrength(promptInfo) != 0;
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

    public static boolean isValidAuthenticatorConfig(PromptInfo promptInfo) {
        return isValidAuthenticatorConfig(promptInfo.getAuthenticators());
    }

    public static boolean isValidAuthenticatorConfig(int i) {
        if (i == 0) {
            return true;
        }
        if (((-65536) & i) != 0) {
            Slog.e("BiometricService", "Non-biometric, non-credential bits found. Authenticators: " + i);
            return false;
        }
        int i2 = i & 32767;
        if ((i2 == 0 && isCredentialRequested(i)) || i2 == 15 || i2 == 255) {
            return true;
        }
        Slog.e("BiometricService", "Unsupported biometric flags. Authenticators: " + i);
        return false;
    }

    public static int biometricConstantsToBiometricManager(int i) {
        if (i == 0) {
            return 0;
        }
        if (i != 1) {
            if (i == 7 || i == 9) {
                return 0;
            }
            if (i != 18) {
                if (i == 11) {
                    return 11;
                }
                if (i == 12) {
                    return 12;
                }
                if (i == 14) {
                    return 11;
                }
                if (i == 15) {
                    return 15;
                }
                Slog.e("BiometricService", "Unhandled result code: " + i);
            }
        }
        return 1;
    }

    public static int getAuthenticationTypeForResult(int i) {
        if (i == 1 || i == 4) {
            return 2;
        }
        if (i == 7) {
            return 1;
        }
        throw new IllegalArgumentException("Unsupported dismissal reason: " + i);
    }

    public static boolean listContains(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static void checkPermissionOrShell(Context context, String str) {
        if (Binder.getCallingUid() == 2000) {
            return;
        }
        checkPermission(context, str);
    }

    public static void checkPermission(Context context, String str) {
        context.enforceCallingOrSelfPermission(str, "Must have " + str + " permission.");
    }

    public static boolean isCurrentUserOrProfile(Context context, int i) {
        UserManager userManager = UserManager.get(context);
        if (userManager == null) {
            Slog.e("BiometricUtils", "Unable to get UserManager");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            for (int i2 : userManager.getEnabledProfileIds(ActivityManager.getCurrentUser())) {
                if (i2 == i) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isStrongBiometric(int i) {
        try {
            return isAtLeastStrength(IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")).getCurrentStrength(i), 15);
        } catch (RemoteException e) {
            Slog.e("BiometricUtils", "RemoteException", e);
            return false;
        }
    }

    public static int getCurrentStrength(int i) {
        try {
            return IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")).getCurrentStrength(i);
        } catch (RemoteException e) {
            Slog.e("BiometricUtils", "RemoteException", e);
            return 0;
        }
    }

    public static boolean isKeyguard(Context context, String str) {
        boolean hasInternalPermission = hasInternalPermission(context);
        ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getResources().getString(R.string.duration_days_relative_future));
        String packageName = unflattenFromString != null ? unflattenFromString.getPackageName() : null;
        return hasInternalPermission && packageName != null && packageName.equals(str);
    }

    public static boolean isSystem(Context context, String str) {
        return hasInternalPermission(context) && "android".equals(str);
    }

    public static boolean isSettings(Context context, String str) {
        return hasInternalPermission(context) && "com.android.settings".equals(str);
    }

    public static boolean hasInternalPermission(Context context) {
        return context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") == 0;
    }

    public static String getClientName(BaseClientMonitor baseClientMonitor) {
        return baseClientMonitor != null ? baseClientMonitor.getClass().getSimpleName() : "null";
    }

    public static boolean isUserEncryptedOrLockdown(LockPatternUtils lockPatternUtils, int i) {
        int strongAuthForUser = lockPatternUtils.getStrongAuthForUser(i);
        boolean containsFlag = containsFlag(strongAuthForUser, 1);
        boolean z = containsFlag(strongAuthForUser, 2) || containsFlag(strongAuthForUser, 32);
        Slog.d("BiometricUtils", "isEncrypted: " + containsFlag + " isLockdown: " + z);
        return containsFlag || z;
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

    public static int authenticatorStrengthToPropertyStrength(int i) {
        if (i == 15) {
            return 2;
        }
        if (i == 255) {
            return 1;
        }
        if (i != 4095) {
            Slog.w("BiometricUtils", "authenticatorStrengthToPropertyStrength: Unknown strength " + i);
        }
        return 0;
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
        Slog.w("BiometricUtils", "propertyStrengthToAuthenticatorStrength: Unknown strength " + i);
        return 4095;
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
                Slog.i("BiometricUtils", "Running task, top: " + packageName + ", isVisible: " + runningTaskInfo.isVisible());
            }
        }
        return true;
    }

    public static int oemStrengthToPropertyStrength(int i) {
        int i2 = 1;
        if (i == 1) {
            return 2;
        }
        if (i != 2) {
            i2 = 0;
            if (i != 3) {
                Slog.w("BiometricUtils", "oemStrengthToPropertyStrength: Unknown security level " + i);
            }
        }
        return i2;
    }

    public static int propertyStrengthToOemStrength(int i) {
        if (i == 0) {
            return 3;
        }
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 1;
        }
        Slog.w("BiometricUtils", "propertyStrengthToOemStrength: Unknown security level " + i);
        return 3;
    }

    public static int biometricAuthenticatorModalityToVendorSysUiType(int i) {
        if (i == 1) {
            return 32768;
        }
        if (i == 2) {
            return 8;
        }
        if (i == 4) {
            return IInstalld.FLAG_FORCE;
        }
        if (i == 8) {
            return IInstalld.FLAG_USE_QUOTA;
        }
        if (i == 256) {
            return 16384;
        }
        Slog.w("BiometricUtils", "biometricAuthenticatorModalityToVendorSysUiType: Unknown modality:" + i);
        return 0;
    }

    public static boolean isDexMode(Context context) {
        SemDesktopModeManager semDesktopModeManager;
        SemDesktopModeState desktopModeState;
        return SemBiometricFeature.FEATURE_SUPPORT_DESKTOP_MODE && (semDesktopModeManager = (SemDesktopModeManager) context.getSystemService(SemDesktopModeManager.class)) != null && (desktopModeState = semDesktopModeManager.getDesktopModeState()) != null && desktopModeState.getEnabled() == 4;
    }

    public static byte[] readFile(File file) {
        byte[] bArr;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        byte[] bArr2 = null;
        fileInputStream2 = null;
        if (file == null || !file.exists()) {
            Log.i("BiometricUtils", "Invalid file info, " + file);
            return null;
        }
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Exception e) {
                e = e;
                bArr = null;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            bArr2 = new byte[(int) file.length()];
            if (fileInputStream.read(bArr2) > 0) {
                try {
                    fileInputStream.close();
                } catch (Exception e2) {
                    Log.w("BiometricUtils", "failed to close file", e2);
                }
                return bArr2;
            }
            try {
                fileInputStream.close();
                return bArr2;
            } catch (Exception e3) {
                Log.w("BiometricUtils", "failed to close file", e3);
                return bArr2;
            }
        } catch (Exception e4) {
            e = e4;
            bArr = bArr2;
            fileInputStream2 = fileInputStream;
            Log.w("BiometricUtils", "failure to read file : " + e.getMessage());
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (Exception e5) {
                    Log.w("BiometricUtils", "failed to close file", e5);
                }
            }
            return bArr;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (Exception e6) {
                    Log.w("BiometricUtils", "failed to close file", e6);
                }
            }
            throw th;
        }
    }

    public static boolean writeFile(File file, byte[] bArr) {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            fileOutputStream.write(bArr);
            try {
                fileOutputStream.close();
                return true;
            } catch (Exception unused) {
                return true;
            }
        } catch (Exception e2) {
            e = e2;
            fileOutputStream2 = fileOutputStream;
            Slog.w("BiometricUtils", "failed to write file", e);
            if (fileOutputStream2 == null) {
                return false;
            }
            try {
                fileOutputStream2.close();
                return false;
            } catch (Exception unused2) {
                return false;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (Exception unused3) {
                }
            }
            throw th;
        }
    }

    public static boolean isFactoryBinary() {
        return SystemProperties.get("ro.factory.factory_binary", "").equalsIgnoreCase("factory");
    }

    public static void registerBroadcastAsUser(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, UserHandle userHandle, Handler handler) {
        try {
            context.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, null, handler);
        } catch (Exception e) {
            Log.i("BiometricUtils", "registerBroadcast: failed to set receiver", e);
        }
    }

    public static void registerBroadcastAsUser(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, UserHandle userHandle, String str, Handler handler) {
        try {
            context.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, str, handler);
        } catch (Exception e) {
            Log.i("BiometricUtils", "registerBroadcast: failed to set receiver", e);
        }
    }

    public static void unregisterBroadcast(Context context, BroadcastReceiver broadcastReceiver) {
        try {
            context.unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            Log.i("BiometricUtils", "unregisterBroadcast: failed to set receiver" + e.getMessage());
        }
    }

    public static boolean isTalkBackEnabled(Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        return accessibilityManager != null && accessibilityManager.semIsScreenReaderEnabled();
    }

    public static boolean isCutoutNotchHidden(Context context) {
        return Settings.Secure.getIntForUser(context.getContentResolver(), "display_cutout_hide_notch", 0, 0) != 0;
    }

    public static boolean isOneHandMode(Context context) {
        return getIntDb(context, "any_screen_running", false, 0, -2) == 1;
    }

    public static boolean isSmartViewDisplayWithFitToAspectRatio(Context context) {
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        SemWifiDisplayStatus semGetWifiDisplayStatus = displayManager.semGetWifiDisplayStatus();
        boolean z = semGetWifiDisplayStatus != null && semGetWifiDisplayStatus.getActiveDisplayState() == 2;
        MediaRouter.RouteInfo selectedRoute = ((MediaRouter) context.getSystemService("media_router")).getSelectedRoute(4);
        return (z || ((4 & selectedRoute.getSupportedTypes()) != 0 && selectedRoute.semGetDeviceAddress() == null && selectedRoute.semGetStatusCode() == 6 && (selectedRoute.getPresentationDisplay() != null || (selectedRoute.getDescription() != null && selectedRoute.getDescription().toString().contains("Audio")))) || (Settings.Global.getInt(context.getContentResolver(), "lelink_cast_on", 0) == 1)) && displayManager.semIsFitToActiveDisplay();
    }

    public static void putIntDb(Context context, String str, boolean z, int i) {
        putIntDb(context, str, z, i, -2);
    }

    public static void putIntDb(Context context, String str, boolean z, int i, int i2) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            if (z) {
                Settings.Secure.putIntForUser(contentResolver, str, i, i2);
            } else {
                Settings.System.putIntForUser(contentResolver, str, i, i2);
            }
        } catch (Exception e) {
            Log.w("BiometricUtils", "putIntDb: " + e.getMessage());
        }
    }

    public static void putLongDb(Context context, String str, boolean z, long j, int i) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            if (z) {
                Settings.Secure.putLongForUser(contentResolver, str, j, i);
            } else {
                Settings.System.putLongForUser(contentResolver, str, j, i);
            }
        } catch (Exception e) {
            Log.w("BiometricUtils", "putIntDb: " + e.getMessage());
        }
    }

    public static int getIntDb(Context context, String str, boolean z, int i) {
        return getIntDb(context, str, z, i, -2);
    }

    public static int getIntDb(Context context, String str, boolean z, int i, int i2) {
        int intForUser;
        ContentResolver contentResolver = context.getContentResolver();
        try {
            if (z) {
                intForUser = Settings.Secure.getIntForUser(contentResolver, str, i, i2);
            } else {
                intForUser = Settings.System.getIntForUser(contentResolver, str, i, i2);
            }
            i = intForUser;
            return i;
        } catch (Exception e) {
            Log.w("BiometricUtils", "getIntDb: " + e.getMessage());
            return i;
        }
    }

    public static long getLongDb(Context context, String str, boolean z, long j, int i) {
        long longForUser;
        ContentResolver contentResolver = context.getContentResolver();
        try {
            if (z) {
                longForUser = Settings.Secure.getLongForUser(contentResolver, str, j, i);
            } else {
                longForUser = Settings.System.getLongForUser(contentResolver, str, j, i);
            }
            j = longForUser;
            return j;
        } catch (Exception e) {
            Log.w("BiometricUtils", "getIntDb: " + e.getMessage());
            return j;
        }
    }

    public static boolean hasPermission(Context context, String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }

    public static boolean useOwnerBiometrics(Context context, int i) {
        if (SemPersonaManager.isSecureFolderId(i)) {
            Slog.d("BiometricUtils", "useOwnerBiometrics: SecureFolder profile!");
            return true;
        }
        if (SemPersonaManager.isAppSeparationUserId(context, i)) {
            Slog.d("BiometricUtils", "useOwnerBiometrics: AppSeparation profile!");
            return true;
        }
        if (!SemDualAppManager.isDualAppId(i)) {
            return false;
        }
        Slog.d("BiometricUtils", "useOwnerBiometrics: DualApp profile!");
        return true;
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
            if (SemPersonaManager.isAppSeparationUserId(context, i2)) {
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

    public static boolean isWorkProfile(Context context, int i) {
        if (useOwnerBiometrics(context, i)) {
            return false;
        }
        UserManager userManager = UserManager.get(context);
        if (userManager != null) {
            UserInfo userInfo = userManager.getUserInfo(i);
            return userInfo != null && userInfo.isManagedProfile();
        }
        Slog.e("BiometricUtils", "isWorkProfile: Unable to acquire UserManager");
        return false;
    }

    public static String[] getHidlSensorConfiguration() {
        ArrayList arrayList = new ArrayList();
        if (SemBiometricFeature.FP_FEATURE_SUPPORT_FINGERPRINT) {
            int i = SystemProperties.getInt("ro.board.first_api_level", 0);
            int i2 = Build.VERSION.DEVICE_INITIAL_SDK_INT;
            boolean z = true;
            if ((i != 0 || i2 >= 33) && i >= 33 && !SemBiometricFeature.FEATURE_FINGERPRINT_JDM_HAL) {
                z = false;
            }
            if (z) {
                arrayList.add("0:2:15");
            }
        }
        arrayList.add("1:8:" + (SemBiometricFeature.FEATURE_JDM_HAL ? 4095 : IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT));
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static boolean isAutoTime(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "auto_time", 0) > 0;
    }

    public static String byteArrayToString(byte[] bArr, int i) {
        return (bArr == null || bArr.length < i) ? "" : new String(Arrays.copyOf(bArr, i), StandardCharsets.UTF_8);
    }

    public static boolean isTablet() {
        return SystemProperties.get("ro.build.characteristics", "").contains("tablet");
    }

    public static boolean isFlipOpened(Context context) {
        InputManager inputManager;
        return (!SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FLIP || (inputManager = (InputManager) context.getSystemService("input")) == null || inputManager.semGetLidState() == 1) ? false : true;
    }

    public static boolean isFlipFolded(Context context) {
        InputManager inputManager;
        return SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FLIP && (inputManager = (InputManager) context.getSystemService("input")) != null && inputManager.semGetLidState() == 1;
    }

    public static boolean isFolderOpened(Context context) {
        InputManager inputManager;
        return (!SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD || (inputManager = (InputManager) context.getSystemService("input")) == null || inputManager.semGetLidState() == 1) ? false : true;
    }

    public static boolean isFolderFolded(Context context) {
        InputManager inputManager;
        return SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD && (inputManager = (InputManager) context.getSystemService("input")) != null && inputManager.semGetLidState() == 1;
    }

    public static boolean inMultiWindowMode(String str) {
        ComponentName componentName;
        if (str == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> tasks = ActivityTaskManager.getInstance().getTasks(3);
        if (tasks == null || tasks.isEmpty()) {
            Slog.w("BiometricUtils", "inMultiWindowMode: No running tasks reported");
            return false;
        }
        for (ActivityManager.RunningTaskInfo runningTaskInfo : tasks) {
            if (runningTaskInfo != null && (componentName = runningTaskInfo.topActivity) != null && str.contentEquals(componentName.getPackageName()) && WindowConfiguration.inMultiWindowMode(runningTaskInfo.configuration.windowConfiguration.getWindowingMode())) {
                Slog.i("BiometricUtils", "inMultiWindowMode: " + str);
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.FileReader] */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.FileReader] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.FileReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.lang.String] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:65:0x006d -> B:21:0x0070). Please report as a decompilation issue!!! */
    public static JSONObject getJSONObject(String str) {
        BufferedReader bufferedReader;
        File file = new File(str);
        ?? exists = file.exists();
        ?? r2 = 0;
        r2 = 0;
        if (exists == 0) {
            Slog.i("BiometricUtils", "getJSONObject: No file, " + str);
            return null;
        }
        ?? sb = new StringBuilder();
        try {
            try {
                try {
                    exists = new FileReader(file);
                } catch (Throwable th) {
                    th = th;
                    r2 = file;
                }
            } catch (IOException e) {
                e = e;
                bufferedReader = null;
                exists = 0;
            } catch (Throwable th2) {
                th = th2;
                exists = 0;
            }
            try {
                bufferedReader = new BufferedReader(exists);
                try {
                    for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                        sb.append(readLine);
                    }
                    try {
                        bufferedReader.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    exists.close();
                } catch (IOException e3) {
                    e = e3;
                    e.printStackTrace();
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (exists != 0) {
                        exists.close();
                    }
                    sb = sb.toString();
                    return new JSONObject((String) sb);
                }
            } catch (IOException e5) {
                e = e5;
                bufferedReader = null;
            } catch (Throwable th3) {
                th = th3;
                if (r2 != 0) {
                    try {
                        r2.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                if (exists != 0) {
                    try {
                        exists.close();
                        throw th;
                    } catch (IOException e7) {
                        e7.printStackTrace();
                        throw th;
                    }
                }
                throw th;
            }
        } catch (IOException e8) {
            e8.printStackTrace();
        }
        try {
            sb = sb.toString();
            return new JSONObject((String) sb);
        } catch (JSONException e9) {
            e9.printStackTrace();
            return null;
        }
    }

    public static void semVibrate(Context context, int i) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Vibrator.class);
        if (vibrator == null) {
            Slog.w("BiometricUtils", "semVibrate: No vibrator service");
            return;
        }
        if (VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR && vibrator.semGetSupportedVibrationType() == 1 && (i == 113 || i == 5)) {
            i = 100;
        }
        if (vibrator.semGetSupportedVibrationType() >= 1) {
            vibrator.vibrate(VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(i), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
        } else if (DEBUG) {
            Slog.d("BiometricUtils", "semVibrate: supported type=" + vibrator.semGetSupportedVibrationType());
        }
    }

    public static boolean isRuneStoneApp(Context context, String str) {
        if (hasInternalPermission(context)) {
            return "com.android.vending".equals(str) || "com.samsung.android.spay".equals(str) || "com.paypal.android.p2pmobile".equals(str) || "com.squareup.cash".equals(str) || "com.venmo".equals(str) || "com.zellepay.zell".equals(str);
        }
        return false;
    }

    public static boolean isFirstApiLevel31orGreater() {
        return Build.VERSION.DEVICE_INITIAL_SDK_INT > 30;
    }

    public static boolean isKnoxTwoFactor(Context context, int i) {
        return getIntDb(context, "knox_finger_print_plus", true, 0, i) == 1;
    }
}
