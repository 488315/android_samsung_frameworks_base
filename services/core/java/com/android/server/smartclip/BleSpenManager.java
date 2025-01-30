package com.android.server.smartclip;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/* compiled from: SpenGestureManagerService.java */
/* loaded from: classes3.dex */
public class BleSpenManager {
    public static final String TAG = "BleSpenManager";
    public boolean mBundledRemoteSpenSupport;
    public Context mContext;
    public Handler mHandler = new Handler();
    public SemInputDeviceManager mSemInputDeviceManager;
    public boolean mUnbundledRemoteSpenSupport;

    public BleSpenManager(Context context) {
        this.mBundledRemoteSpenSupport = false;
        this.mUnbundledRemoteSpenSupport = false;
        this.mContext = context;
        this.mBundledRemoteSpenSupport = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BLE_SPEN");
        this.mUnbundledRemoteSpenSupport = SpenGarageSpecManager.getInstance().isUnbundledRemoteSpenSupported();
        this.mSemInputDeviceManager = (SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService");
        if (isSupportBleSpen()) {
            registerAirActionSettingObserver();
        }
    }

    public synchronized String getBleSpenAddress() {
        if (!isSupportBleSpen()) {
            Log.e(TAG, "getBleSpenAddress : BLE Spen is not supported");
            return null;
        }
        return readStringFromFile("/efs/spen/blespen_addr");
    }

    public synchronized void setBleSpenAddress(String str) {
        if (!isSupportBleSpen()) {
            Log.e(TAG, "setBleSpenAddress : BLE Spen is not supported");
        } else {
            writeStringToFile("/efs/spen/blespen_addr", str);
        }
    }

    public synchronized String getBleSpenCmfCode() {
        if (!isSupportBleSpen()) {
            Log.e(TAG, "getBleSpenCmfCode : BLE Spen is not supported");
            return null;
        }
        return readStringFromFile("/efs/spen/blespen_cmf");
    }

    public synchronized void setBleSpenCmfCode(String str) {
        if (!isSupportBleSpen()) {
            Log.e(TAG, "setBleSpenCmfCode : BLE Spen is not supported");
        } else {
            writeStringToFile("/efs/spen/blespen_cmf", str);
        }
    }

    public synchronized boolean isSupportBleSpen() {
        boolean z;
        if (!this.mBundledRemoteSpenSupport) {
            z = this.mUnbundledRemoteSpenSupport;
        }
        return z;
    }

    public synchronized void writeBleSpenCommand(String str) {
        if (!isSupportBleSpen()) {
            Log.e(TAG, "writeBleSpenCommand : BLE Spen is not supported");
            return;
        }
        try {
            this.mSemInputDeviceManager.setSpenBleChargeMode(Integer.parseInt(str));
        } catch (Exception e) {
            Log.e(TAG, "mSemInputDeviceManager.setSpenBleChargeMode: Exception:" + e);
        }
    }

    public synchronized void setSpenPdctLowSensitivityEnable() {
        try {
            this.mSemInputDeviceManager.setSpenPdctLowSensitivityEnable(1);
        } catch (Exception e) {
            Log.e(TAG, "mSemInputDeviceManager.setSpenPdctLowSensitivityEnable: Exception:" + e);
        }
    }

    public synchronized void saveBleSpenLogFile(byte[] bArr) {
        if (bArr != null) {
            String str = TAG;
            Log.i(str, "saveBleSpenLogFile : length=" + bArr.length);
            File file = new File(Environment.getDataDirectory() + "/log/spen");
            if (!file.exists() && !file.mkdirs()) {
                Log.e(str, "saveBleSpenLogFile : failed to make dirs");
            }
            makeFilePublic(file);
            String str2 = file + File.separator + "Spen_dumpState.log";
            File file2 = new File(str2);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    fileOutputStream.write(bArr);
                    makeFilePublic(file2);
                    Log.i(str, "saveBleSpenLogFile : " + str2);
                    fileOutputStream.close();
                } finally {
                }
            } catch (Throwable th) {
                Log.e(TAG, "saveBleSpenLogFile fail : " + th.toString());
            }
            return;
        }
        Log.e(TAG, "saveBleSpenLogFile : no buffer");
    }

    public final void makeFilePublic(File file) {
        if (!file.setReadable(true, false)) {
            Log.e(TAG, "saveBleSpenLogFile : failed to set readable");
        }
        if (!file.setWritable(true, false)) {
            Log.e(TAG, "saveBleSpenLogFile : failed to set writable");
        }
        if (file.setExecutable(true, false)) {
            return;
        }
        Log.e(TAG, "saveBleSpenLogFile : failed to set executable");
    }

    public synchronized void startRemoteSpenService(Context context, Bundle bundle) {
        if (!isSupportBleSpen()) {
            Log.e(TAG, "startRemoteSpenService : BLE Spen is not supported");
            return;
        }
        if (SemPersonaManager.isKioskModeEnabled(context)) {
            Log.i(TAG, "startRemoteSpenService : BLE Spen is disabled on knox container enabled mode");
            return;
        }
        try {
            Intent intent = new Intent("com.samsung.android.service.aircommand.action.REMOTE_SPEN_SERVICE");
            intent.setPackage("com.samsung.android.service.aircommand");
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if (context.startServiceAsUser(intent, UserHandle.CURRENT) == null) {
                Log.e(TAG, "startRemoteSpenService : failed to launch the service");
            }
        } catch (IllegalStateException | SecurityException e) {
            Log.e(TAG, "startRemoteSpenService : Failed to start BLE SPen service " + e);
        }
    }

    public synchronized void startBlindChargeService(Context context, Bundle bundle) {
        if (!isSupportBleSpen()) {
            Log.e(TAG, "startBlindChargeService : BLE Spen is not supported");
            return;
        }
        if (SemPersonaManager.isKioskModeEnabled(context)) {
            Log.i(TAG, "startBlindChargeService : BLE Spen is disabled on knox container enabled mode");
            return;
        }
        try {
            Intent intent = new Intent("com.samsung.android.service.aircommand.action.SPEN_BLIND_CHARGE_SERVICE");
            intent.setPackage("com.samsung.android.service.aircommand");
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if (context.startServiceAsUser(intent, UserHandle.CURRENT) == null) {
                Log.e(TAG, "startBlindChargeService : failed to launch the service");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "startBlindChargeService : Failed to start service " + e);
        }
    }

    public boolean isAirActionSettingEnabled() {
        return Settings.System.semGetIntForUser(this.mContext.getContentResolver(), "spen_air_action", 1, -2) != 0;
    }

    public final void registerAirActionSettingObserver() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("spen_air_action"), false, new ContentObserver(this.mHandler) { // from class: com.android.server.smartclip.BleSpenManager.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                BleSpenManager.this.onAirActionSettingChanged();
            }
        }, -1);
    }

    public final void onAirActionSettingChanged() {
        boolean isAirActionSettingEnabled = isAirActionSettingEnabled();
        Log.i(TAG, "onAirActionSettingChanged : " + isAirActionSettingEnabled);
        if (isSupportBleSpen()) {
            if (isAirActionSettingEnabled) {
                startRemoteSpenService(this.mContext, null);
            } else {
                startBlindChargeService(this.mContext, null);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String readStringFromFile(String str) {
        String str2;
        BufferedReader bufferedReader;
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
                try {
                    str2 = bufferedReader.readLine();
                } catch (IOException e) {
                    e = e;
                    str2 = null;
                }
            } catch (IOException e2) {
                e = e2;
                str2 = null;
                bufferedReader = null;
            }
        } catch (FileNotFoundException e3) {
            e = e3;
            str2 = null;
        }
        try {
            bufferedReader.close();
        } catch (FileNotFoundException e4) {
            e = e4;
            Log.e(TAG, "getBleSpenAddress : file not exist. e=" + e);
            if (!TextUtils.isEmpty(str2)) {
            }
        } catch (IOException e5) {
            e = e5;
            Log.e(TAG, "getBleSpenAddress : e=" + e);
            try {
                bufferedReader.close();
            } catch (IOException unused) {
            }
            if (!TextUtils.isEmpty(str2)) {
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            return str2;
        }
        Log.d(TAG, "readStringFromFile : empty file");
        return null;
    }

    public final void writeStringToFile(String str, String str2) {
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
                Log.e(TAG, "writeStringToFile : Parent dir is null! filePathName=" + str);
                return;
            }
            if (!parentFile.isDirectory()) {
                Log.e(TAG, "writeStringToFile : No directoy, make directoy : " + parentFile.getAbsolutePath());
                parentFile.mkdirs();
            }
            if (!parentFile.canRead() && !parentFile.setReadable(true, false)) {
                Log.e(TAG, "writeStringToFile : failed setreadable:" + parentFile.toString());
            }
            if (!parentFile.canExecute() && !parentFile.setExecutable(true, false)) {
                Log.e(TAG, "writeStringToFile : failed setexecutable:" + parentFile.toString());
            }
            BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str, false)));
            try {
                bufferedWriter2.write(str2);
                File file = new File(str);
                if (!file.setReadable(true, true)) {
                    Log.e(TAG, "writeStringToFile : failed setreadable:" + file.toString());
                }
                if (!file.setExecutable(false, true)) {
                    Log.e(TAG, "writeStringToFile : failed setexecutable:" + file.toString());
                }
                if (!file.setWritable(true, true)) {
                    Log.e(TAG, "writeStringToFile : failed setWritable:" + file.toString());
                }
                bufferedWriter2.close();
            } catch (IOException e2) {
                e = e2;
                bufferedWriter = bufferedWriter2;
                Log.e(TAG, "writeStringToFile : e=" + e);
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
}
