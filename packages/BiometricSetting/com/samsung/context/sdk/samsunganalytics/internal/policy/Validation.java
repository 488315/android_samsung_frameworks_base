package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.UserManager;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/* loaded from: classes.dex */
public final class Validation {
    public static boolean isValidConfig(final Application application, final Configuration configuration) {
        boolean z;
        if (application == null) {
            Utils.throwException("context cannot be null");
            return false;
        }
        if (configuration == null) {
            Utils.throwException("Configuration cannot be null");
            return false;
        }
        if (TextUtils.isEmpty(configuration.getTrackingId())) {
            Utils.throwException("TrackingId is empty, set TrackingId");
            return false;
        }
        if (TextUtils.isEmpty(configuration.getDeviceId()) && !configuration.isEnableAutoDeviceId()) {
            Utils.throwException("Device Id is empty, set Device Id or enable auto device id");
            return false;
        }
        PolicyUtils.setSenderType(application, configuration);
        if (PolicyUtils.getSenderType() == 2) {
            try {
                String[] strArr = application.getPackageManager().getPackageInfo(application.getPackageName(), 4096).requestedPermissions;
                if (strArr != null) {
                    for (String str : strArr) {
                        if (str.startsWith("com.sec.spp.permission.TOKEN")) {
                            z = true;
                            break;
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Debug.LogException(Validation.class, e);
            }
            z = false;
            if (!z) {
                Utils.throwException("If you want to use DLC Logger, define 'com.sec.spp.permission.TOKEN_XXXX' permission in AndroidManifest");
                return false;
            }
        }
        if (!TextUtils.isEmpty(configuration.getDeviceId())) {
            Utils.throwException("This mode is not allowed to set device Id");
            return false;
        }
        if (!TextUtils.isEmpty(null)) {
            Utils.throwException("This mode is not allowed to set user Id");
            return false;
        }
        if (TextUtils.isEmpty(configuration.getVersion())) {
            Utils.throwException("you should set the UI version");
            return false;
        }
        UserManager userManager = (UserManager) application.getSystemService("user");
        if (userManager == null || userManager.isUserUnlocked()) {
            return true;
        }
        Debug.LogE("The user has not unlocked the device.");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.context.sdk.samsunganalytics.internal.policy.Validation.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Debug.LogD("receive " + intent.getAction());
                SamsungAnalytics.setConfiguration(application, configuration);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        application.registerReceiver(broadcastReceiver, intentFilter);
        return false;
    }

    public static String sha256(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            return String.format(Locale.US, "%064x", new BigInteger(1, messageDigest.digest()));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Debug.LogException(Validation.class, e);
            return null;
        }
    }
}
