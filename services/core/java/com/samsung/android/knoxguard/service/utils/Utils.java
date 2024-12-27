package com.samsung.android.knoxguard.service.utils;

import android.R;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Slog;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.RemoteLockInfo;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knoxguard.service.KnoxGuardNative;
import com.samsung.android.knoxguard.service.KnoxGuardSeService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public final class Utils {
    public static final String TAG = "KG.Utils";
    public static ILockSettings mLockSettingsService;

    public static void autoLockDevice(Context context, String str) {
        String string;
        String string2;
        if (isTabletDevice()) {
            string = context.getString(R.string.permdesc_manageOngoingCalls);
            string2 = context.getString(R.string.permdesc_imagesWrite);
        } else {
            string = context.getString(R.string.permdesc_manageNetworkPolicy);
            string2 = context.getString(R.string.permdesc_highSamplingRateSensors);
        }
        String str2 = string;
        if (str != null && !str.isEmpty()) {
            string2 = BootReceiver$$ExternalSyntheticOutline0.m(" (", str, ")\n\n", string2);
        }
        setRemoteLockToLockscreen(
                context, 3, true, string2, "", "", true, str2, 0, 0L, 0, true, null, true);
    }

    public static RemoteLockInfo buildDefaultRetryLockInfo(Context context) {
        String string;
        String string2;
        if (isTabletDevice()) {
            string = context.getString(R.string.permdesc_manageOngoingCalls);
            string2 = context.getString(R.string.permdesc_imagesWrite);
        } else {
            string = context.getString(R.string.permdesc_manageNetworkPolicy);
            string2 = context.getString(R.string.permdesc_highSamplingRateSensors);
        }
        return new RemoteLockInfo.Builder(3, true)
                .setClientName(string)
                .setPhoneNumber("")
                .setEmailAddress("")
                .setMessage(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                " (2002)\n\n", string2))
                .setAllowFailCount(0)
                .setEnableEmergencyCall(true)
                .setLockTimeOut(0L)
                .setBlockCount(0)
                .setSkipPinContainer(true)
                .setBundle((Bundle) null)
                .setSkipSupportContainer(true)
                .build();
    }

    public static void checkCaller(Context context) {
        if (!"com.samsung.android.kgclient"
                .equals(context.getPackageManager().getNameForUid(Binder.getCallingUid()))) {
            throw new SecurityException("caller should be com.samsung.android.kgclient");
        }
    }

    public static void checkCallerAndKgPermission(Context context) {
        checkPermission(context, Constants.KG_PERMISSION);
        checkCaller(context);
    }

    public static boolean checkPermission(Context context, String str) {
        if (context.checkCallingPermission(str) == 0) {
            return true;
        }
        throw new SecurityException(
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("does not have ", str));
    }

    public static String getKGPolicyCompany() {
        try {
            String str = TAG;
            Slog.d(str, "getKGPolicyCompany");
            String stringResult =
                    KnoxGuardSeService.getStringResult(KnoxGuardNative.getKGPolicyRefactor());
            if (stringResult == null) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(stringResult);
            String string =
                    !jSONObject.isNull(Constants.JSON_KG_POLICY_GENERAL)
                            ? jSONObject
                                    .getJSONObject(Constants.JSON_KG_POLICY_GENERAL)
                                    .getString("companyName")
                            : null;
            Slog.d(str, "getKGPolicyCompany company " + string);
            return string;
        } catch (JSONException unused) {
            Slog.e(TAG, "getKGPolicyCompany Exception: Parsing Error!");
            return null;
        } catch (Exception e) {
            Slog.e(TAG, "getKGPolicyCompany Exception: " + e.getMessage(), e);
            return null;
        }
    }

    public static PendingIntent getPendingIntentForRetryLockAction(Context context, int i) {
        Intent intent = new Intent(Constants.INTENT_RETRY_LOCK);
        intent.putExtra(Constants.ALARM_TYPE, i);
        return PendingIntent.getBroadcast(context, i, intent, 201326592);
    }

    public static String getRlcClientDataCompany(Context context) {
        return null;
    }

    public static String getRlcClientDataMessageType(Context context) {
        return null;
    }

    public static int getStateAndSetToKGSystemProperty() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int intResult =
                KnoxGuardSeService.getIntResult(
                        KnoxGuardNative.tz_getTAState(KnoxGuardNative.KGTA_PARAM_DEFAULT), true);
        int i = 5;
        if (intResult >= 0 && intResult <= 5) {
            i = intResult;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder("getStateAndSetToKGSystemProperty - knox.kg.state : ");
        String[] strArr = Constants.strState;
        BootReceiver$$ExternalSyntheticOutline0.m(sb, strArr[i], str);
        try {
            SystemProperties.set(Constants.KG_SYSTEM_PROPERTY, strArr[i]);
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Cannot set SystemProperty(knox.kg.state) - "), TAG);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return intResult;
    }

    public static String getStringSystemProperty(String str, String str2) {
        if (str == null || str.isEmpty()) {
            return str2;
        }
        try {
            return SystemProperties.get(str, str2);
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e,
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "Cannot get String property(", str, ") - "),
                    TAG);
            return str2;
        }
    }

    public static String getTextFromFile(String str) {
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 =
                            new BufferedReader(
                                    new InputStreamReader(
                                            new FileInputStream(str), StandardCharsets.UTF_8));
                    try {
                        String readLine = bufferedReader2.readLine();
                        if (readLine != null) {
                            readLine = readLine.trim();
                        }
                        try {
                            bufferedReader2.close();
                            return readLine;
                        } catch (IOException unused) {
                            Slog.w(TAG, "Stream closing error");
                            return readLine;
                        }
                    } catch (UnsupportedEncodingException unused2) {
                        str = null;
                        bufferedReader = bufferedReader2;
                        Slog.e(TAG, "Encoding not supported");
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        return str;
                    } catch (IOException unused3) {
                        str = null;
                        bufferedReader = bufferedReader2;
                        Slog.e(TAG, "File not found");
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        return str;
                    } catch (Exception unused4) {
                        str = null;
                        bufferedReader = bufferedReader2;
                        Slog.e(TAG, "File reading error");
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        return str;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException unused5) {
                                Slog.w(TAG, "Stream closing error");
                            }
                        }
                        throw th;
                    }
                } catch (UnsupportedEncodingException unused6) {
                    str = null;
                } catch (IOException unused7) {
                    str = null;
                } catch (Exception unused8) {
                    str = null;
                }
            } catch (IOException unused9) {
                Slog.w(TAG, "Stream closing error");
                return str;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean isChinaDevice() {
        String str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY, "");
        return str != null && str.equalsIgnoreCase(ActivationMonitor.CHINA_COUNTRY_CODE);
    }

    public static boolean isEqualOrAbove(int i) {
        return Build.VERSION.SDK_INT >= i;
    }

    public static boolean isExistFile(String str) {
        if (str != null) {
            return BatteryService$$ExternalSyntheticOutline0.m45m(str);
        }
        return false;
    }

    public static boolean isOtpBitFusedWithActive() {
        boolean equals =
                Constants.OTP_BIT_KG_ENABLED.equals(
                        getStringSystemProperty(
                                Constants.KG_OTP_BIT_SYSTEM_PROPERTY,
                                Constants.OTP_BIT_KG_UNKNOWN));
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isOtpBitFusedWithActive - ", TAG, equals);
        return equals;
    }

    public static boolean isPackageInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, e.getMessage(), e);
            return false;
        }
    }

    public static boolean isRetryLockAlarmRunning(Context context, int i) {
        Intent intent = new Intent(Constants.INTENT_RETRY_LOCK);
        intent.putExtra(Constants.ALARM_TYPE, i);
        return PendingIntent.getBroadcast(context, i, intent, 603979776) != null;
    }

    public static boolean isSetupWizardFinished(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
    }

    public static boolean isSingleOtpBitFused() {
        boolean equals =
                "1"
                        .equals(
                                getStringSystemProperty(
                                        Constants.KG_OTP_BIT_SYSTEM_PROPERTY,
                                        Constants.OTP_BIT_KG_UNKNOWN));
        DeviceIdleController$$ExternalSyntheticOutline0.m("isSingleOtpBitFused - ", TAG, equals);
        return equals;
    }

    public static boolean isSingleOtpBitFusedAndStateIsNotCompleted(int i) {
        return isSingleOtpBitFused() && 4 != i;
    }

    public static boolean isSingleOtpBitFusedAndStateIsNotCompleted(String str) {
        return isSingleOtpBitFused() && !Constants.strState[4].equals(str);
    }

    public static boolean isSkipSupportContainerSupported() {
        try {
            new RemoteLockInfo.Builder(3, true).setSkipSupportContainer(true).build();
            return true;
        } catch (NoSuchMethodError e) {
            Slog.e(TAG, "call isSkipSupportContainerSupported : " + e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isStateForEnrolledDevice(int i) {
        return 2 == i || 3 == i;
    }

    public static boolean isStateForEnrolledDevice(String str) {
        return str != null
                && (Constants.RLC_STATE_NORMAL.equalsIgnoreCase(str)
                        || Constants.RLC_STATE_BLINKED.equalsIgnoreCase(str)
                        || Constants.RLC_STATE_LOCKED.equalsIgnoreCase(str));
    }

    public static boolean isSupportKGOnCsc() {
        return SemCscFeature.getInstance().getInt(Constants.CSC_FEATURE_SUPPORT_KNOXGUARD, 0) != 0;
    }

    public static boolean isSupportKGOnSEC() {
        return false;
    }

    public static boolean isTabletDevice() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains("tablet");
    }

    public static void lockSeDevice(Context context, String str) {
        String format;
        String kGPolicyCompany = getKGPolicyCompany();
        if (isTabletDevice()) {
            if (kGPolicyCompany != null) {
                format =
                        String.format(
                                context.getString(R.string.permdesc_killBackgroundProcesses),
                                "Device Services",
                                kGPolicyCompany);
            } else {
                format =
                        String.format(
                                context.getString(R.string.permdesc_manageFingerprint),
                                "Device Services");
                kGPolicyCompany = context.getString(R.string.permdesc_manageOngoingCalls);
            }
        } else if (kGPolicyCompany != null) {
            format =
                    String.format(
                            context.getString(R.string.permdesc_install_shortcut),
                            "Device Services",
                            kGPolicyCompany);
        } else {
            format =
                    String.format(
                            context.getString(R.string.permdesc_invokeCarrierSetup),
                            "Device Services");
            kGPolicyCompany = context.getString(R.string.permdesc_manageNetworkPolicy);
        }
        if (str != null && !str.isEmpty()) {
            format = BootReceiver$$ExternalSyntheticOutline0.m(" (", str, ")\n\n", format);
        }
        setRemoteLockToLockscreen(
                context,
                3,
                true,
                format,
                "",
                "",
                true,
                kGPolicyCompany != null
                        ? kGPolicyCompany
                        : context.getString(R.string.permdesc_manageNetworkPolicy),
                0,
                0L,
                0,
                true,
                null,
                true);
    }

    public static boolean performLockscreen(
            ILockSettings iLockSettings, RemoteLockInfo remoteLockInfo, boolean z)
            throws RemoteException {
        KnoxGuardSeService.setUserPresentReceiverEnabled(z);
        boolean knoxGuard =
                iLockSettings.setKnoxGuard(ActivityManager.getCurrentUser(), remoteLockInfo);
        KnoxGuardSeService.setLockResult(knoxGuard);
        return knoxGuard;
    }

    public static void powerOff(Context context, int i) {
        Slog.i(TAG, "powerOff by " + i);
        ((PowerManager) context.getSystemService("power"))
                .shutdown(
                        false,
                        String.format("KnoxGuard : system recovery (%d)", Integer.valueOf(i)),
                        false);
    }

    public static void setKGSystemProperty() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int intResult =
                KnoxGuardSeService.getIntResult(
                        KnoxGuardNative.tz_getTAState(KnoxGuardNative.KGTA_PARAM_DEFAULT), false);
        if (intResult < 0 || intResult > 5) {
            intResult = 5;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder("setKGSystemProperty - knox.kg.state : ");
        String[] strArr = Constants.strState;
        BootReceiver$$ExternalSyntheticOutline0.m(sb, strArr[intResult], str);
        try {
            SystemProperties.set(Constants.KG_SYSTEM_PROPERTY, strArr[intResult]);
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Cannot set SystemProperty(knox.kg.state) - "), TAG);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public static void setRemoteLockToLockscreen(
            Context context,
            int i,
            boolean z,
            String str,
            String str2,
            String str3,
            boolean z2,
            String str4,
            int i2,
            long j,
            int i3,
            boolean z3,
            Bundle bundle) {
        setRemoteLockToLockscreen(
                context, i, z, str, str2, str3, z2, str4, i2, j, i3, z3, bundle, true);
    }

    public static void setRemoteLockToLockscreen(
            Context context,
            int i,
            boolean z,
            String str,
            String str2,
            String str3,
            boolean z2,
            String str4,
            int i2,
            long j,
            int i3,
            boolean z3,
            Bundle bundle,
            boolean z4) {
        try {
            if (mLockSettingsService == null) {
                mLockSettingsService =
                        ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            }
            RemoteLockInfo build =
                    new RemoteLockInfo.Builder(i, z)
                            .setClientName(str4)
                            .setPhoneNumber(str2)
                            .setEmailAddress(str3)
                            .setMessage(str)
                            .setAllowFailCount(i2)
                            .setEnableEmergencyCall(z2)
                            .setLockTimeOut(j)
                            .setBlockCount(i3)
                            .setSkipPinContainer(z3)
                            .setBundle(bundle)
                            .setSkipSupportContainer(z4)
                            .build();
            boolean performLockscreen = performLockscreen(mLockSettingsService, build, z);
            if (z && !performLockscreen && Constants.IS_SUPPORT_KGTA) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                startRetryLockAlarm(context, build);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (RemoteException e) {
            Slog.e(TAG, "call setRemoteLockToLockscreen Exception : " + e);
            e.printStackTrace();
        }
    }

    public static boolean setRetryLock(Context context) {
        try {
            if (mLockSettingsService == null) {
                mLockSettingsService =
                        ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            }
            RemoteLockInfo remoteLockInfoForRetry = KnoxGuardSeService.getRemoteLockInfoForRetry();
            if (remoteLockInfoForRetry == null) {
                remoteLockInfoForRetry = buildDefaultRetryLockInfo(context);
            }
            boolean knoxGuard =
                    mLockSettingsService.setKnoxGuard(
                            ActivityManager.getCurrentUser(), remoteLockInfoForRetry);
            KnoxGuardSeService.setLockResult(knoxGuard);
            return knoxGuard;
        } catch (RemoteException e) {
            Slog.e(TAG, "call setRetryLock Exception : " + e);
            e.printStackTrace();
            KnoxGuardSeService.setRetryRemoteLockInfo(null);
            return false;
        }
    }

    public static void startRetryLockAlarm(Context context, RemoteLockInfo remoteLockInfo) {
        String str = TAG;
        Slog.i(str, "startRetryLockAlarm");
        if (isRetryLockAlarmRunning(context, 1)) {
            Slog.i(str, "Retry lock alarm is already running!!! - do nothing.");
            return;
        }
        KnoxGuardSeService.setRetryRemoteLockInfo(remoteLockInfo);
        long elapsedRealtime = SystemClock.elapsedRealtime() + 300000;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        PendingIntent pendingIntentForRetryLockAction =
                getPendingIntentForRetryLockAction(context, 1);
        if (pendingIntentForRetryLockAction == null || alarmManager == null) {
            Slog.e(str, "startRetryLockAlarm - null pi or am");
            return;
        }
        alarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime, pendingIntentForRetryLockAction);
        Slog.i(str, "startRetryLockAlarm - alarm scheduled @" + System.currentTimeMillis());
    }
}
