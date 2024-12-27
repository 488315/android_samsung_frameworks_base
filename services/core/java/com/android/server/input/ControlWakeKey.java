package com.android.server.input;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ControlWakeKey {
    public Context mContext;
    public HashMap mWakeKeyRefCount;

    /* JADX WARN: Removed duplicated region for block: B:12:0x0042 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x002b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void writeWakeKeyVolume(java.lang.String r5, boolean r6) {
        /*
            java.lang.String r0 = "InputManager.ControlWakeKey"
            java.lang.String r1 = "fosWakeKeyvolume writing is passed "
            if (r6 == 0) goto La
            java.lang.String r6 = "1"
            goto Lc
        La:
            java.lang.String r6 = "0"
        Lc:
            java.io.File r2 = new java.io.File
            r2.<init>(r5)
            r3 = 0
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.io.FileNotFoundException -> L1e
            r4.<init>(r2)     // Catch: java.io.FileNotFoundException -> L1e
            java.lang.String r2 = "FileOutputStreamvolume is passed"
            android.util.Slog.i(r0, r2)     // Catch: java.io.FileNotFoundException -> L1d
            goto L29
        L1d:
            r3 = r4
        L1e:
            java.lang.String r2 = "file not found: "
            java.lang.String r5 = r2.concat(r5)
            android.util.Slog.i(r0, r5)
            r4 = r3
        L29:
            if (r4 == 0) goto L40
            byte[] r5 = r6.getBytes()     // Catch: java.io.IOException -> L3a
            r4.write(r5)     // Catch: java.io.IOException -> L3a
            java.lang.String r5 = r1.concat(r6)     // Catch: java.io.IOException -> L3a
            android.util.Slog.i(r0, r5)     // Catch: java.io.IOException -> L3a
            goto L40
        L3a:
            java.lang.String r5 = "fosWakeKeyvolume writing is failed"
            android.util.Slog.i(r0, r5)
        L40:
            if (r4 == 0) goto L52
            r4.close()     // Catch: java.io.IOException -> L4c
            java.lang.String r5 = "closing fosWakeKeyvolume is passed"
            android.util.Slog.i(r0, r5)     // Catch: java.io.IOException -> L4c
            goto L52
        L4c:
            java.lang.String r5 = "closing fosWakeKeyvolume is failed"
            android.util.Slog.i(r0, r5)
        L52:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.input.ControlWakeKey.writeWakeKeyVolume(java.lang.String,"
                    + " boolean):void");
    }

    public final void makeWakeKeyRefCount(String str, boolean z) {
        String[] split = str.split(",");
        int i = 0;
        if (z) {
            int length = split.length;
            while (i < length) {
                String trim = split[i].trim();
                int intValue =
                        this.mWakeKeyRefCount.containsKey(trim)
                                ? ((Integer) this.mWakeKeyRefCount.get(trim)).intValue() + 1
                                : 1;
                this.mWakeKeyRefCount.put(trim, Integer.valueOf(intValue));
                Slog.d(
                        "InputManager.ControlWakeKey",
                        "mWakeKeyRefCount(" + trim + ") is increased: " + intValue);
                i++;
            }
            return;
        }
        int length2 = split.length;
        while (i < length2) {
            String trim2 = split[i].trim();
            if (this.mWakeKeyRefCount.containsKey(trim2)) {
                int intValue2 = ((Integer) this.mWakeKeyRefCount.get(trim2)).intValue() - 1;
                if (intValue2 <= 0) {
                    this.mWakeKeyRefCount.remove(trim2);
                    Slog.i("InputManager.ControlWakeKey", "keyCode is removed: " + trim2);
                } else {
                    this.mWakeKeyRefCount.put(trim2, Integer.valueOf(intValue2));
                    Slog.d(
                            "InputManager.ControlWakeKey",
                            "mWakeKeyRefCount(" + trim2 + ") is decreased: " + intValue2);
                }
            }
            i++;
        }
    }

    public final String makeWakeKeyString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.mWakeKeyRefCount.isEmpty()) {
            Slog.d("InputManager.ControlWakeKey", "mWakeKeyRefCount is empty");
        } else {
            int size = this.mWakeKeyRefCount.size();
            String[] strArr = new String[size];
            this.mWakeKeyRefCount.keySet().toArray(strArr);
            for (int i = 0; i < size; i++) {
                stringBuffer.append(strArr[i]);
                stringBuffer.append(",");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            Slog.d(
                    "InputManager.ControlWakeKey",
                    "keyCodes in makeWakeKeyString is " + stringBuffer.toString());
        }
        return new String(stringBuffer.toString());
    }

    public final void setWakeKeyDynamically(String str, boolean z, String str2) {
        boolean z2;
        if (TextUtils.isEmpty(str)) {
            Slog.i(
                    "InputManager.ControlWakeKey",
                    "setWakeKeyDynamically: pkg=".concat(str == null ? "null" : "empty"));
            return;
        }
        if (TextUtils.isEmpty(str2)) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "setWakeKeyDynamically: pkg=", str, ", keyCodes="),
                    str2 == null ? "null" : "empty",
                    "InputManager.ControlWakeKey");
            return;
        }
        int callingUid = Binder.getCallingUid();
        StringBuilder m =
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        callingUid, "setWakeKeyDynamically: pkg=", str, ", uid=", ", keyCodes=");
        m.append(str2);
        m.append(", put=");
        m.append(z);
        Slog.i("InputManager.ControlWakeKey", m.toString());
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            Slog.i("InputManager.ControlWakeKey", "pm is null");
            return;
        }
        String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
        if (packagesForUid == null || packagesForUid.length == 0) {
            Slog.i(
                    "InputManager.ControlWakeKey",
                    "packages: ".concat(packagesForUid == null ? "null" : "empty"));
            return;
        }
        int length = packagesForUid.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z2 = false;
                break;
            } else {
                if (str.equals(packagesForUid[i])) {
                    z2 = true;
                    break;
                }
                i++;
            }
        }
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            if (applicationInfo == null) {
                Slog.i("InputManager.ControlWakeKey", "appinfo is null");
                return;
            }
            if (!z2 || !applicationInfo.isPrivilegedApp()) {
                SystemServiceManager$$ExternalSyntheticOutline0.m(
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "uidHasPackage is ", ", appinfo.privateFlags is ", z2),
                        applicationInfo.privateFlags,
                        "InputManager.ControlWakeKey");
                if (!z2) {
                    for (String str3 : packagesForUid) {
                        Slog.d(
                                "InputManager.ControlWakeKey",
                                "packages with uid " + callingUid + ": " + str3);
                    }
                }
                if (Binder.getCallingUid() != 1000) {
                    throw new SecurityException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "only system app can use this method, but ",
                                    str,
                                    " is not system app"));
                }
            }
            try {
                setWakeKeyDynamicallyInternal(str2, z);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.i("InputManager.ControlWakeKey", "NameNotFoundException is occured");
        }
    }

    public final void setWakeKeyDynamicallyInternal(String str, boolean z) {
        boolean z2 = true;
        if (this.mWakeKeyRefCount.isEmpty()) {
            makeWakeKeyRefCount("116,172", true);
        }
        makeWakeKeyRefCount(str, z);
        if (!this.mWakeKeyRefCount.containsKey("114")
                && !this.mWakeKeyRefCount.containsKey("115")) {
            z2 = false;
        }
        writeWakeKeyVolume("/sys/power/volkey_wakeup", z2);
        writeWakeKeyVolume("/sys/class/sec/ap_pmic/volkey_wakeup", z2);
        writeWakeKeyString(makeWakeKeyString());
    }

    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x001f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0035 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeWakeKeyString(java.lang.String r4) {
        /*
            r3 = this;
            java.lang.String r3 = "InputManager.ControlWakeKey"
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "/sys/class/sec/sec_key/wakeup_keys"
            r0.<init>(r1)
            r1 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.io.FileNotFoundException -> L16
            r2.<init>(r0)     // Catch: java.io.FileNotFoundException -> L16
            java.lang.String r0 = "FileOutputStream is passed"
            android.util.Slog.i(r3, r0)     // Catch: java.io.FileNotFoundException -> L15
            goto L1d
        L15:
            r1 = r2
        L16:
            java.lang.String r0 = "file not found: /sys/class/sec/sec_key/wakeup_keys"
            android.util.Slog.i(r3, r0)
            r2 = r1
        L1d:
            if (r2 == 0) goto L33
            byte[] r4 = r4.getBytes()     // Catch: java.io.IOException -> L2d
            r2.write(r4)     // Catch: java.io.IOException -> L2d
            java.lang.String r4 = "fosWakeKey writing is passed"
            android.util.Slog.i(r3, r4)     // Catch: java.io.IOException -> L2d
            goto L33
        L2d:
            java.lang.String r4 = "fosWakeKey writing is failed"
            android.util.Slog.i(r3, r4)
        L33:
            if (r2 == 0) goto L45
            r2.close()     // Catch: java.io.IOException -> L3f
            java.lang.String r4 = "closing fosWakeKey is passed"
            android.util.Slog.i(r3, r4)     // Catch: java.io.IOException -> L3f
            goto L45
        L3f:
            java.lang.String r4 = "closing fosWakeKey is failed"
            android.util.Slog.i(r3, r4)
        L45:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.input.ControlWakeKey.writeWakeKeyString(java.lang.String):void");
    }
}
