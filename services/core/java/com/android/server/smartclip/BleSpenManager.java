package com.android.server.smartclip;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.SemPersonaManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BleSpenManager {
    public final boolean mBundledRemoteSpenSupport;
    public final Context mContext;
    public final SemInputDeviceManager mSemInputDeviceManager;
    public final boolean mUnbundledRemoteSpenSupport;

    public BleSpenManager(Context context) {
        this.mBundledRemoteSpenSupport = false;
        this.mUnbundledRemoteSpenSupport = false;
        this.mContext = context;
        this.mBundledRemoteSpenSupport =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BLE_SPEN");
        this.mUnbundledRemoteSpenSupport =
                SpenGarageSpecManager.getInstance()
                        .mSupportedExternalSpenFeatures
                        .contains(SpenGarageSpecManager.SupportedExternalSpenFeature.REMOTE);
        Handler handler = new Handler();
        this.mSemInputDeviceManager =
                (SemInputDeviceManager) context.getSystemService("SemInputDeviceManagerService");
        if (isSupportBleSpen()) {
            context.getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor("spen_air_action"),
                            false,
                            new ContentObserver(
                                    handler) { // from class:
                                               // com.android.server.smartclip.BleSpenManager.1
                                @Override // android.database.ContentObserver
                                public final void onChange(boolean z) {
                                    BleSpenManager bleSpenManager = BleSpenManager.this;
                                    boolean isAirActionSettingEnabled =
                                            bleSpenManager.isAirActionSettingEnabled();
                                    Log.i(
                                            "BleSpenManager",
                                            "onAirActionSettingChanged : "
                                                    + isAirActionSettingEnabled);
                                    if (bleSpenManager.isSupportBleSpen()) {
                                        if (isAirActionSettingEnabled) {
                                            bleSpenManager.startRemoteSpenService(
                                                    bleSpenManager.mContext);
                                        } else {
                                            bleSpenManager.startBlindChargeService(
                                                    bleSpenManager.mContext);
                                        }
                                    }
                                }
                            },
                            -1);
        }
    }

    public static void makeFilePublic(File file) {
        if (!file.setReadable(true, false)) {
            Log.e("BleSpenManager", "saveBleSpenLogFile : failed to set readable");
        }
        if (!file.setWritable(true, false)) {
            Log.e("BleSpenManager", "saveBleSpenLogFile : failed to set writable");
        }
        if (file.setExecutable(true, false)) {
            return;
        }
        Log.e("BleSpenManager", "saveBleSpenLogFile : failed to set executable");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String readStringFromFile(java.lang.String r5) {
        /*
            java.lang.String r0 = "BleSpenManager"
            r1 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.io.IOException -> L28 java.io.FileNotFoundException -> L2b
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.io.IOException -> L28 java.io.FileNotFoundException -> L2b
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.io.IOException -> L28 java.io.FileNotFoundException -> L2b
            r4.<init>(r5)     // Catch: java.io.IOException -> L28 java.io.FileNotFoundException -> L2b
            r3.<init>(r4)     // Catch: java.io.IOException -> L28 java.io.FileNotFoundException -> L2b
            r2.<init>(r3)     // Catch: java.io.IOException -> L28 java.io.FileNotFoundException -> L2b
            java.lang.String r5 = r2.readLine()     // Catch: java.lang.Throwable -> L1e
            r2.close()     // Catch: java.io.IOException -> L1a java.io.FileNotFoundException -> L1c
            goto L51
        L1a:
            r2 = move-exception
            goto L2e
        L1c:
            r2 = move-exception
            goto L40
        L1e:
            r5 = move-exception
            r2.close()     // Catch: java.lang.Throwable -> L23
            goto L27
        L23:
            r2 = move-exception
            r5.addSuppressed(r2)     // Catch: java.io.IOException -> L28 java.io.FileNotFoundException -> L2b
        L27:
            throw r5     // Catch: java.io.IOException -> L28 java.io.FileNotFoundException -> L2b
        L28:
            r2 = move-exception
            r5 = r1
            goto L2e
        L2b:
            r2 = move-exception
            r5 = r1
            goto L40
        L2e:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "getBleSpenAddress : e="
            r3.<init>(r4)
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            android.util.Log.e(r0, r2)
            goto L51
        L40:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "getBleSpenAddress : file not exist. e="
            r3.<init>(r4)
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            android.util.Log.e(r0, r2)
        L51:
            boolean r2 = android.text.TextUtils.isEmpty(r5)
            if (r2 == 0) goto L5e
            java.lang.String r5 = "readStringFromFile : empty file"
            android.util.Log.d(r0, r5)
            goto L5f
        L5e:
            r1 = r5
        L5f:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.smartclip.BleSpenManager.readStringFromFile(java.lang.String):java.lang.String");
    }

    public static void writeStringToFile(String str, String str2) {
        File parentFile;
        if (str2 == null) {
            str2 = "";
        }
        BufferedWriter bufferedWriter = null;
        try {
            try {
                try {
                    parentFile = new File(str).getParentFile();
                } catch (IOException unused) {
                    return;
                }
            } catch (IOException e) {
                e = e;
            }
            if (parentFile == null) {
                Log.e(
                        "BleSpenManager",
                        "writeStringToFile : Parent dir is null! filePathName=".concat(str));
                return;
            }
            if (!parentFile.isDirectory()) {
                Log.e(
                        "BleSpenManager",
                        "writeStringToFile : No directoy, make directoy : "
                                + parentFile.getAbsolutePath());
                parentFile.mkdirs();
            }
            if (!parentFile.canRead() && !parentFile.setReadable(true, false)) {
                Log.e(
                        "BleSpenManager",
                        "writeStringToFile : failed setreadable:" + parentFile.toString());
            }
            if (!parentFile.canExecute() && !parentFile.setExecutable(true, false)) {
                Log.e(
                        "BleSpenManager",
                        "writeStringToFile : failed setexecutable:" + parentFile.toString());
            }
            BufferedWriter bufferedWriter2 =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str, false)));
            try {
                bufferedWriter2.write(str2);
                File file = new File(str);
                if (!file.setReadable(true, true)) {
                    Log.e(
                            "BleSpenManager",
                            "writeStringToFile : failed setreadable:" + file.toString());
                }
                if (!file.setExecutable(false, true)) {
                    Log.e(
                            "BleSpenManager",
                            "writeStringToFile : failed setexecutable:" + file.toString());
                }
                if (!file.setWritable(true, true)) {
                    Log.e(
                            "BleSpenManager",
                            "writeStringToFile : failed setWritable:" + file.toString());
                }
                bufferedWriter2.close();
            } catch (IOException e2) {
                e = e2;
                bufferedWriter = bufferedWriter2;
                Log.e("BleSpenManager", "writeStringToFile : e=" + e);
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (Throwable th) {
                th = th;
                bufferedWriter = bufferedWriter2;
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final boolean isAirActionSettingEnabled() {
        return Settings.System.semGetIntForUser(
                        this.mContext.getContentResolver(), "spen_air_action", 1, -2)
                != 0;
    }

    public final synchronized boolean isSupportBleSpen() {
        boolean z;
        if (!this.mBundledRemoteSpenSupport) {
            z = this.mUnbundledRemoteSpenSupport;
        }
        return z;
    }

    public final synchronized void startBlindChargeService(Context context) {
        if (!isSupportBleSpen()) {
            Log.e("BleSpenManager", "startBlindChargeService : BLE Spen is not supported");
            return;
        }
        if (SemPersonaManager.isKioskModeEnabled(context)) {
            Log.i(
                    "BleSpenManager",
                    "startBlindChargeService : BLE Spen is disabled on knox container enabled"
                        + " mode");
            return;
        }
        try {
            Intent intent =
                    new Intent(
                            "com.samsung.android.service.aircommand.action.SPEN_BLIND_CHARGE_SERVICE");
            intent.setPackage("com.samsung.android.service.aircommand");
            if (context.startServiceAsUser(intent, UserHandle.CURRENT) == null) {
                Log.e("BleSpenManager", "startBlindChargeService : failed to launch the service");
            } else {
                Log.i("BleSpenManager", "startBlindChargeService");
            }
        } catch (SecurityException e) {
            Log.e("BleSpenManager", "startBlindChargeService : Failed to start service " + e);
        }
    }

    public final synchronized void startRemoteSpenService(Context context) {
        if (!isSupportBleSpen()) {
            Log.e("BleSpenManager", "startRemoteSpenService : BLE Spen is not supported");
            return;
        }
        if (SemPersonaManager.isKioskModeEnabled(context)) {
            Log.i(
                    "BleSpenManager",
                    "startRemoteSpenService : BLE Spen is disabled on knox container enabled mode");
            return;
        }
        try {
            Intent intent =
                    new Intent("com.samsung.android.service.aircommand.action.REMOTE_SPEN_SERVICE");
            intent.setPackage("com.samsung.android.service.aircommand");
            if (context.startServiceAsUser(intent, UserHandle.CURRENT) == null) {
                Log.e("BleSpenManager", "startRemoteSpenService : failed to launch the service");
            }
        } catch (IllegalStateException | SecurityException e) {
            Log.e(
                    "BleSpenManager",
                    "startRemoteSpenService : Failed to start BLE SPen service " + e);
        }
    }
}
