package com.samsung.ucm.ucmservice;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.SystemProperties;
import android.util.Log;

import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class UcmServiceUtil {
    public static int getOrganizationOwnedProfileUserId() {
        int i = EnterpriseDeviceManagerService.$r8$clinit;
        return ((EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance)
                .getOrganizationOwnedProfileUserId();
    }

    public static boolean isDebug() {
        String str = SystemProperties.get("ro.build.type");
        return "eng".equals(str) || "userdebug".equals(str);
    }

    public static boolean isOrganizationOwnedProfile(Context context) {
        return ((DevicePolicyManager) context.getSystemService("device_policy"))
                .isOrganizationOwnedDeviceWithManagedProfile();
    }

    public static int readIntFromFile(String str) {
        BufferedReader bufferedReader;
        String str2 = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(str));
        } catch (FileNotFoundException unused) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            str2 = readLine;
            try {
                return Integer.parseInt(str2);
            } catch (NullPointerException | NumberFormatException unused2) {
                return 0;
            }
        } catch (Throwable th) {
            try {
                bufferedReader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static boolean saveDataToFile(String str, byte[] bArr) {
        new File("/efs/sec_efs/tz_esecomm").mkdirs();
        return saveDataToFile$1("/efs/sec_efs/tz_esecomm/".concat(str), bArr);
    }

    public static boolean saveDataToFile$1(String str, byte[] bArr) {
        File file = new File(str);
        if (file.exists() && !file.delete()) {
            Log.e("UcmServiceUtil", "failed to delete the existing file");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(bArr);
                fileOutputStream.close();
                return true;
            } finally {
            }
        } catch (IOException e) {
            Log.e("UcmServiceUtil", "saveDataToFile. write. IOException.");
            e.printStackTrace();
            file.delete();
            return false;
        }
    }
}
