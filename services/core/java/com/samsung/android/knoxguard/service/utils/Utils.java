package com.samsung.android.knoxguard.service.utils;

import android.R;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.RemoteLockInfo;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knoxguard.service.KnoxGuardNative;
import com.samsung.android.knoxguard.service.KnoxGuardSeService;
import com.samsung.android.knoxguard.service.KnoxGuardService;
import com.samsung.android.service.RemoteLockControl.KnoxGuard.KnoxGuardVaultException;
import com.samsung.android.service.RemoteLockControl.KnoxGuard.KnoxGuardVaultManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class Utils {
    public static final String TAG = "KG." + Utils.class.getSimpleName();
    public static ILockSettings mLockSettingsService;

    public static boolean isStateForEnrolledDevice(int i) {
        return 2 == i || 3 == i;
    }

    public static boolean isSupportKGOnSEC() {
        return true;
    }

    public static boolean isExistFile(String str) {
        if (str != null) {
            return new File(str).exists();
        }
        return false;
    }

    public static String getTextFromFile(String str) {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(str), StandardCharsets.UTF_8));
                } catch (Throwable th) {
                    th = th;
                }
            } catch (UnsupportedEncodingException unused) {
                str = null;
            } catch (IOException unused2) {
                str = null;
            } catch (Exception unused3) {
                str = null;
            }
        } catch (IOException unused4) {
            Slog.w(TAG, "Stream closing error");
        }
        try {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                readLine = readLine.trim();
            }
            try {
                bufferedReader.close();
                return readLine;
            } catch (IOException unused5) {
                Slog.w(TAG, "Stream closing error");
                return readLine;
            }
        } catch (UnsupportedEncodingException unused6) {
            str = null;
            bufferedReader2 = bufferedReader;
            Slog.e(TAG, "Encoding not supported");
            if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
            return str;
        } catch (IOException unused7) {
            str = null;
            bufferedReader2 = bufferedReader;
            Slog.e(TAG, "File not found");
            if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
            return str;
        } catch (Exception unused8) {
            str = null;
            bufferedReader2 = bufferedReader;
            Slog.e(TAG, "File reading error");
            if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
            return str;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException unused9) {
                    Slog.w(TAG, "Stream closing error");
                }
            }
            throw th;
        }
    }

    public static boolean checkPermission(Context context, String str) {
        if (context.checkCallingPermission(str) == 0) {
            return true;
        }
        throw new SecurityException("does not have " + str);
    }

    public static String getRlcState(Context context) {
        String str = TAG;
        Slog.d(str, "getRlcState.");
        try {
            KnoxGuardVaultManager kgvm = KnoxGuardService.getKGVM();
            if (kgvm == null) {
                Slog.d(str, "mKGVM is null");
                kgvm = new KnoxGuardVaultManager(context);
            }
            String query = kgvm.query();
            Slog.d(str, "getRlcState rlcState : " + query);
            return query;
        } catch (KnoxGuardVaultException unused) {
            Slog.e(TAG, "KnoxGuardVaultManager not supported (KnoxGuardVaultException)");
            return null;
        } catch (Exception e) {
            Slog.e(TAG, "Exception: " + e);
            return null;
        }
    }

    public static String getRlcClientDataCompany(Context context) {
        try {
            KnoxGuardVaultManager kgvm = KnoxGuardService.getKGVM();
            if (kgvm == null) {
                Slog.d(TAG, "mKGVM is null");
                kgvm = new KnoxGuardVaultManager(context);
            }
            String clientData = kgvm.getClientData();
            String str = TAG;
            Slog.d(str, "getRlcClientDataCompany clientData : " + clientData);
            JSONObject jSONObject = new JSONObject(clientData);
            String string = !jSONObject.isNull("companyName") ? jSONObject.getString("companyName") : null;
            Slog.d(str, "getRlcClientDataCompany company " + string);
            return string;
        } catch (JSONException unused) {
            Slog.e(TAG, "Parsing Error!");
            return null;
        } catch (KnoxGuardVaultException unused2) {
            Slog.e(TAG, "KnoxGuardVaultManager not supported (KnoxGuardVaultException)");
            return null;
        }
    }

    public static String getKGPolicyCompany() {
        try {
            String str = TAG;
            Slog.d(str, "getKGPolicyCompany");
            String stringResult = KnoxGuardSeService.getStringResult(KnoxGuardNative.getKGPolicyRefactor());
            if (stringResult == null) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(stringResult);
            String string = !jSONObject.isNull("generalInfo") ? jSONObject.getJSONObject("generalInfo").getString("companyName") : null;
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

    public static void setCheckingStateToRlc(Context context) {
        String str = TAG;
        Slog.d(str, "setCheckingStateToRlc");
        try {
            KnoxGuardVaultManager kgvm = KnoxGuardService.getKGVM();
            if (kgvm == null) {
                Slog.d(str, "mKGVM is null");
                kgvm = new KnoxGuardVaultManager(context);
            }
            Slog.d(str, "setCheckingStateToRlc result " + kgvm.setRestrictedDevice());
        } catch (KnoxGuardVaultException e) {
            Slog.e(TAG, "setCheckingStateToRlc Exception " + e.getMessage(), e);
        } catch (Exception e2) {
            Slog.e(TAG, "setCheckingStateToRlc Exception " + e2.getMessage(), e2);
        }
    }

    public static void autoLockDevice(Context context, String str) {
        String string;
        String string2;
        String str2;
        if (isTabletDevice()) {
            string = context.getString(R.string.notification_title_abusive_bg_apps);
            string2 = context.getString(R.string.notification_listener_binding_label);
        } else {
            string = context.getString(R.string.notification_title);
            string2 = context.getString(R.string.notification_inbox_ellipsis);
        }
        String str3 = string;
        if (str == null || str.isEmpty()) {
            str2 = string2;
        } else {
            str2 = " (" + str + ")\n\n" + string2;
        }
        setRemoteLockToLockscreen(context, 3, true, str2, "", "", true, str3, 0, 0L, 0, true, null);
    }

    public static void lockDevice(Context context, String str) {
        String format;
        String str2;
        String rlcClientDataCompany = getRlcClientDataCompany(context);
        if (isTabletDevice()) {
            if (rlcClientDataCompany != null) {
                format = String.format(context.getString(R.string.notification_ranker_binding_label), "Device Services", rlcClientDataCompany);
            } else {
                format = String.format(context.getString(R.string.notification_reply_button_accessibility), "Device Services");
                rlcClientDataCompany = context.getString(R.string.notification_title_abusive_bg_apps);
            }
        } else if (rlcClientDataCompany != null) {
            format = String.format(context.getString(R.string.notification_messaging_title_template), "Device Services", rlcClientDataCompany);
        } else {
            format = String.format(context.getString(R.string.notification_phishing_alert_content_description), "Device Services");
            rlcClientDataCompany = context.getString(R.string.notification_title);
        }
        if (str == null || str.isEmpty()) {
            str2 = format;
        } else {
            str2 = " (" + str + ")\n\n" + format;
        }
        if (rlcClientDataCompany == null) {
            rlcClientDataCompany = context.getString(R.string.notification_title);
        }
        setRemoteLockToLockscreen(context, 3, true, str2, "", "", true, rlcClientDataCompany, 0, 0L, 0, true, null);
    }

    public static void lockSeDevice(Context context, String str) {
        String format;
        String str2;
        String kGPolicyCompany = getKGPolicyCompany();
        if (isTabletDevice()) {
            if (kGPolicyCompany != null) {
                format = String.format(context.getString(R.string.notification_ranker_binding_label), "Device Services", kGPolicyCompany);
            } else {
                format = String.format(context.getString(R.string.notification_reply_button_accessibility), "Device Services");
                kGPolicyCompany = context.getString(R.string.notification_title_abusive_bg_apps);
            }
        } else if (kGPolicyCompany != null) {
            format = String.format(context.getString(R.string.notification_messaging_title_template), "Device Services", kGPolicyCompany);
        } else {
            format = String.format(context.getString(R.string.notification_phishing_alert_content_description), "Device Services");
            kGPolicyCompany = context.getString(R.string.notification_title);
        }
        if (str == null || str.isEmpty()) {
            str2 = format;
        } else {
            str2 = " (" + str + ")\n\n" + format;
        }
        if (kGPolicyCompany == null) {
            kGPolicyCompany = context.getString(R.string.notification_title);
        }
        setRemoteLockToLockscreen(context, 3, true, str2, "", "", true, kGPolicyCompany, 0, 0L, 0, true, null);
    }

    public static void setRemoteLockToLockscreen(Context context, int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle) {
        setRemoteLockToLockscreen(context, i, z, str, str2, str3, z2, str4, i2, j, i3, z3, bundle, true);
    }

    public static void setRemoteLockToLockscreen(Context context, int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle, boolean z4) {
        try {
            if (mLockSettingsService == null) {
                mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            }
            RemoteLockInfo build = new RemoteLockInfo.Builder(i, z).setClientName(str4).setPhoneNumber(str2).setEmailAddress(str3).setMessage(str).setAllowFailCount(i2).setEnableEmergencyCall(z2).setLockTimeOut(j).setBlockCount(i3).setSkipPinContainer(z3).setBundle(bundle).setSkipSupportContainer(z4).build();
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

    public static boolean performLockscreen(ILockSettings iLockSettings, RemoteLockInfo remoteLockInfo, boolean z) {
        if (Constants.IS_SUPPORT_KGTA) {
            KnoxGuardSeService.setUserPresentReceiverEnabled(z);
        }
        boolean knoxGuard = iLockSettings.setKnoxGuard(ActivityManager.getCurrentUser(), remoteLockInfo);
        KnoxGuardSeService.setLockResult(knoxGuard);
        return knoxGuard;
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

    public static boolean isSetupWizardFinished(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
    }

    public static boolean isTabletDevice() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains("tablet");
    }

    public static boolean isChinaDevice() {
        String str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY, "");
        return str != null && str.equalsIgnoreCase(ActivationMonitor.CHINA_COUNTRY_CODE);
    }

    public static boolean isStateForEnrolledDevice(String str) {
        return str != null && ("Normal".equalsIgnoreCase(str) || "Blink".equalsIgnoreCase(str) || "Locked".equalsIgnoreCase(str));
    }

    public static String getStringSystemProperty(String str, String str2) {
        if (str == null || str.isEmpty()) {
            return str2;
        }
        try {
            return SystemProperties.get(str, str2);
        } catch (Exception e) {
            Slog.w(TAG, "Cannot get String property(" + str + ") - " + e.getMessage());
            return str2;
        }
    }

    public static int getStateAndSetToKGSystemProperty() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int intResult = KnoxGuardSeService.getIntResult(KnoxGuardNative.getTAStateRefactor());
        int i = 5;
        if (intResult >= 0 && intResult <= 5) {
            i = intResult;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getStateAndSetToKGSystemProperty - knox.kg.state : ");
        String[] strArr = Constants.strState;
        sb.append(strArr[i]);
        Slog.d(str, sb.toString());
        try {
            SystemProperties.set("knox.kg.state", strArr[i]);
        } catch (Exception e) {
            Slog.w(TAG, "Cannot set SystemProperty(knox.kg.state) - " + e.getMessage());
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return intResult;
    }

    public static void setKGSystemProperty() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int intResult = KnoxGuardSeService.getIntResult(KnoxGuardNative.getTAStateRefactor(), false);
        if (intResult < 0 || intResult > 5) {
            intResult = 5;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("setKGSystemProperty - knox.kg.state : ");
        String[] strArr = Constants.strState;
        sb.append(strArr[intResult]);
        Slog.d(str, sb.toString());
        try {
            SystemProperties.set("knox.kg.state", strArr[intResult]);
        } catch (Exception e) {
            Slog.w(TAG, "Cannot set SystemProperty(knox.kg.state) - " + e.getMessage());
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public static boolean needClientHealthCheck() {
        int tAState = KnoxGuardNative.getTAState();
        Slog.i(TAG, "needClientHealthCheck state : " + tAState);
        if (KnoxGuardSeService.getTAVersion() >= 3) {
            return isOtpBitFusedWithActiveAndStateIsNotCompleted(tAState);
        }
        return isOtpBitFusedWithActive() || 3 == tAState || 2 == tAState;
    }

    public static void startClientHealthCheckAlarm(Context context) {
        String str = TAG;
        Slog.i(str, "startClientHealthCheckAlarm");
        if (needClientHealthCheck()) {
            long elapsedRealtime = SystemClock.elapsedRealtime() + BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
            AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
            Intent intent = new Intent("com.samsung.android.knoxguard.CLIENT_HEALTH_CHECK");
            intent.putExtra("alarm_type", 0);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 67108864);
            if (broadcast != null && alarmManager != null) {
                alarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime, broadcast);
                Slog.i(str, "startClientHealthCheckAlarm - alarm scheduled @" + System.currentTimeMillis());
                return;
            }
            Slog.e(str, "startClientHealthCheckAlarm - null pi or am");
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
        long elapsedRealtime = SystemClock.elapsedRealtime() + BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        PendingIntent pendingIntentForRetryLockAction = getPendingIntentForRetryLockAction(context, 1);
        if (pendingIntentForRetryLockAction != null && alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime, pendingIntentForRetryLockAction);
            Slog.i(str, "startRetryLockAlarm - alarm scheduled @" + System.currentTimeMillis());
            return;
        }
        Slog.e(str, "startRetryLockAlarm - null pi or am");
    }

    public static boolean isRetryLockAlarmRunning(Context context, int i) {
        Intent intent = new Intent("com.samsung.android.knoxguard.RETRY_LOCK");
        intent.putExtra("alarm_type", i);
        return PendingIntent.getBroadcast(context, i, intent, 603979776) != null;
    }

    public static PendingIntent getPendingIntentForRetryLockAction(Context context, int i) {
        Intent intent = new Intent("com.samsung.android.knoxguard.RETRY_LOCK");
        intent.putExtra("alarm_type", i);
        return PendingIntent.getBroadcast(context, i, intent, 201326592);
    }

    public static void powerOff(Context context, int i) {
        Slog.i(TAG, "powerOff by " + i);
        ((PowerManager) context.getSystemService("power")).shutdown(false, String.format("KnoxGuard : system recovery (%d)", Integer.valueOf(i)), false);
    }

    public static boolean setRetryLock(Context context) {
        try {
            if (mLockSettingsService == null) {
                mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            }
            RemoteLockInfo remoteLockInfoForRetry = KnoxGuardSeService.getRemoteLockInfoForRetry();
            if (remoteLockInfoForRetry == null) {
                remoteLockInfoForRetry = buildDefaultRetryLockInfo(context);
            }
            boolean knoxGuard = mLockSettingsService.setKnoxGuard(ActivityManager.getCurrentUser(), remoteLockInfoForRetry);
            KnoxGuardSeService.setLockResult(knoxGuard);
            return knoxGuard;
        } catch (RemoteException e) {
            Slog.e(TAG, "call setRetryLock Exception : " + e);
            e.printStackTrace();
            KnoxGuardSeService.setRetryRemoteLockInfo(null);
            return false;
        }
    }

    public static RemoteLockInfo buildDefaultRetryLockInfo(Context context) {
        String string;
        String string2;
        if (isTabletDevice()) {
            string = context.getString(R.string.notification_title_abusive_bg_apps);
            string2 = context.getString(R.string.notification_listener_binding_label);
        } else {
            string = context.getString(R.string.notification_title);
            string2 = context.getString(R.string.notification_inbox_ellipsis);
        }
        return new RemoteLockInfo.Builder(3, true).setClientName(string).setPhoneNumber("").setEmailAddress("").setMessage(" (2002)\n\n" + string2).setAllowFailCount(0).setEnableEmergencyCall(true).setLockTimeOut(0L).setBlockCount(0).setSkipPinContainer(true).setBundle((Bundle) null).setSkipSupportContainer(true).build();
    }

    public static boolean isOtpBitFusedWithActiveAndStateIsNotCompleted(int i) {
        return isOtpBitFusedWithActive() && 4 != i;
    }

    public static boolean isOtpBitFusedWithActiveAndStateIsNotCompleted(String str) {
        return isOtpBitFusedWithActive() && !Constants.strState[4].equals(str);
    }

    public static boolean isOtpBitFusedWithActive() {
        String stringSystemProperty = getStringSystemProperty("ro.boot.kg.bit", "FF");
        boolean z = "01".equals(stringSystemProperty) || "1".equals(stringSystemProperty);
        Slog.d(TAG, "isOtpBitFusedWithActive - " + z);
        return z;
    }

    public static boolean isSingleOtpBitFusedWithActive() {
        boolean equals = "1".equals(getStringSystemProperty("ro.boot.kg.bit", "FF"));
        Slog.d(TAG, "isSingleOtpBitFusedWithActive - " + equals);
        return equals;
    }
}
