package com.samsung.android.server.packagefeature.core;

import android.util.Base64;

import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.util.CoreEncryptor;
import com.samsung.android.server.util.CoreLogger;

import java.io.BufferedReader;
import java.util.concurrent.ConcurrentHashMap;

public final class PackageFeatureGroupDataUtil$GroupDataReader implements AutoCloseable {
    public static final boolean SAFE_LOGGABLE;
    public BufferedReader mBufferedReader;
    public CoreLogger mErrorLogger;
    public PackageFeatureGroupData mGroupData;
    public boolean mIsBase64PackageName;
    public int mLineCount;
    public String mName;
    public int mVersion;

    static {
        SAFE_LOGGABLE = CoreRune.IS_DEBUG_LEVEL_MID || CoreRune.IS_DEBUG_LEVEL_HIGH;
    }

    @Override // java.lang.AutoCloseable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void close() {
        /*
            r5 = this;
            java.lang.String r0 = "line: "
            r1 = 0
            com.samsung.android.server.util.CoreLogger r2 = r5.mErrorLogger     // Catch: java.lang.Throwable -> L42
            if (r2 == 0) goto L46
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L42
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L42
            int r0 = r5.mLineCount     // Catch: java.lang.Throwable -> L42
            r2.append(r0)     // Catch: java.lang.Throwable -> L42
            java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> L42
            r2 = 1
            r3 = 3
            r5.log(r3, r0, r2)     // Catch: java.lang.Throwable -> L42
            com.samsung.android.server.util.CoreLogger r0 = r5.mErrorLogger     // Catch: java.lang.Throwable -> L42
            java.util.List r2 = r0.mBuffer     // Catch: java.lang.Throwable -> L42
            if (r2 != 0) goto L22
        L20:
            r3 = r1
            goto L37
        L22:
            monitor-enter(r2)     // Catch: java.lang.Throwable -> L42
            java.util.List r3 = r0.mBuffer     // Catch: java.lang.Throwable -> L2d
            boolean r3 = r3.isEmpty()     // Catch: java.lang.Throwable -> L2d
            if (r3 == 0) goto L2f
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L2d
            goto L20
        L2d:
            r0 = move-exception
            goto L40
        L2f:
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L2d
            java.util.List r4 = r0.mBuffer     // Catch: java.lang.Throwable -> L2d
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L2d
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L2d
        L37:
            if (r3 != 0) goto L3a
            goto L46
        L3a:
            java.lang.String r0 = r0.mDumpTitle     // Catch: java.lang.Throwable -> L42
            com.android.server.wm.WindowManagerServiceExt.logCriticalInfo(r0, r3)     // Catch: java.lang.Throwable -> L42
            goto L46
        L40:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L2d
            throw r0     // Catch: java.lang.Throwable -> L42
        L42:
            r0 = move-exception
            r0.printStackTrace()
        L46:
            r5.mGroupData = r1
            java.io.BufferedReader r0 = r5.mBufferedReader
            if (r0 == 0) goto L56
            r0.close()     // Catch: java.lang.Throwable -> L52
            r5.mBufferedReader = r1
            goto L56
        L52:
            r0 = move-exception
            r5.mBufferedReader = r1
            throw r0
        L56:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.packagefeature.core.PackageFeatureGroupDataUtil$GroupDataReader.close():void");
    }

    public final PackageFeatureGroupData getPackageFeatureGroupDataInner() {
        this.mGroupData = new PackageFeatureGroupData(this.mVersion);
        boolean z = this.mIsBase64PackageName || this.mVersion <= 2023080100;
        while (true) {
            String readLine = this.mBufferedReader.readLine();
            if (readLine == null) {
                break;
            }
            this.mLineCount++;
            String[] split = readLine.split(",");
            String str = split[0];
            if (!z && "999999".equals(str)) {
                z = true;
                break;
            }
            int length = split.length;
            if (length <= 1) {
                log(6, "get: ".concat(readLine), SAFE_LOGGABLE);
                return null;
            }
            String str2 = split[1];
            if (this.mIsBase64PackageName) {
                ConcurrentHashMap concurrentHashMap = CoreEncryptor.sCoreEncryptor;
                str2 = str2 != null ? new String(Base64.decode(str2, 0)) : "";
            }
            String str3 = length > 2 ? split[2] : PackageFeatureData.EMPTY_STRING;
            String str4 = length > 3 ? split[3] : PackageFeatureData.EMPTY_STRING;
            if (PackageFeatureData.EMPTY_STRING.equals(str3)) {
                log(3, "get: ".concat(readLine), SAFE_LOGGABLE);
            }
            this.mGroupData.putPackageFeature(str3, str, str2, str4);
        }
        if (z) {
            return this.mGroupData;
        }
        log(6, "Invalid!", true);
        return null;
    }

    public final void log(int i, String str, boolean z) {
        if (this.mErrorLogger == null) {
            StringBuilder sb = new StringBuilder("PackageFeature_");
            String str2 = this.mName + "_" + this.mVersion + "_" + this.mIsBase64PackageName;
            if (!z) {
                ConcurrentHashMap concurrentHashMap = CoreEncryptor.sCoreEncryptor;
                str2 = str2 != null ? new String(Base64.encode(str2.getBytes(), 0)) : "";
            }
            sb.append(str2);
            this.mErrorLogger = new CoreLogger("CoreLogger", 20, sb.toString(), false, false);
        }
        CoreLogger coreLogger = this.mErrorLogger;
        if (!z) {
            ConcurrentHashMap concurrentHashMap2 = CoreEncryptor.sCoreEncryptor;
            str = str != null ? new String(Base64.encode(str.getBytes(), 0)) : "";
        }
        coreLogger.log(i, str, null);
    }

    public final void open(boolean z) {
        String str = null;
        try {
            str = this.mBufferedReader.readLine();
            String[] split = str.split(",");
            this.mName = split[0];
            this.mVersion = Integer.valueOf(split[1]).intValue();
            this.mIsBase64PackageName = z;
            if (this.mName != null) {
            } else {
                throw new IllegalStateException("It hasn't been opened yet.");
            }
        } catch (Throwable th) {
            log(6, "open: " + th + ", " + str, true);
            throw th;
        }
    }
}
