package com.android.server;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.ISystemUpdateManager;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;
import com.android.modules.utils.TypedXmlSerializer;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemUpdateManagerService extends ISystemUpdateManager.Stub {
    public final Context mContext;
    public final AtomicFile mFile;
    public int mLastUid;
    public final Object mLock;

    public SystemUpdateManagerService(Context context) {
        Object obj = new Object();
        this.mLock = obj;
        this.mLastUid = -1;
        this.mContext = context;
        this.mFile = new AtomicFile(new File(Environment.getDataSystemDirectory(), "system-update-info.xml"));
        synchronized (obj) {
            loadSystemUpdateInfoLocked();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0024, code lost:
    
        r1 = android.os.PersistableBundle.restoreFromXml(r3);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle loadSystemUpdateInfoLocked() {
        /*
            r7 = this;
            java.lang.String r0 = "SystemUpdateManagerService"
            r1 = 0
            android.util.AtomicFile r2 = r7.mFile     // Catch: java.io.IOException -> L2e org.xmlpull.v1.XmlPullParserException -> L30 java.io.FileNotFoundException -> L4a
            java.io.FileInputStream r2 = r2.openRead()     // Catch: java.io.IOException -> L2e org.xmlpull.v1.XmlPullParserException -> L30 java.io.FileNotFoundException -> L4a
            com.android.modules.utils.TypedXmlPullParser r3 = android.util.Xml.resolvePullParser(r2)     // Catch: java.lang.Throwable -> L32
        Ld:
            int r4 = r3.next()     // Catch: java.lang.Throwable -> L32
            r5 = 1
            if (r4 == r5) goto L28
            r5 = 2
            if (r4 != r5) goto Ld
            java.lang.String r4 = "info"
            java.lang.String r5 = r3.getName()     // Catch: java.lang.Throwable -> L32
            boolean r4 = r4.equals(r5)     // Catch: java.lang.Throwable -> L32
            if (r4 == 0) goto Ld
            android.os.PersistableBundle r1 = android.os.PersistableBundle.restoreFromXml(r3)     // Catch: java.lang.Throwable -> L32
        L28:
            if (r2 == 0) goto L61
            r2.close()     // Catch: java.io.IOException -> L2e org.xmlpull.v1.XmlPullParserException -> L30 java.io.FileNotFoundException -> L4a
            goto L61
        L2e:
            r2 = move-exception
            goto L3e
        L30:
            r2 = move-exception
            goto L44
        L32:
            r3 = move-exception
            if (r2 == 0) goto L3d
            r2.close()     // Catch: java.lang.Throwable -> L39
            goto L3d
        L39:
            r2 = move-exception
            r3.addSuppressed(r2)     // Catch: java.io.IOException -> L2e org.xmlpull.v1.XmlPullParserException -> L30 java.io.FileNotFoundException -> L4a
        L3d:
            throw r3     // Catch: java.io.IOException -> L2e org.xmlpull.v1.XmlPullParserException -> L30 java.io.FileNotFoundException -> L4a
        L3e:
            java.lang.String r3 = "Failed to read the info file:"
            android.util.Slog.e(r0, r3, r2)
            goto L61
        L44:
            java.lang.String r3 = "Failed to parse the info file:"
            android.util.Slog.e(r0, r3, r2)
            goto L61
        L4a:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "No existing info file "
            r2.<init>(r3)
            android.util.AtomicFile r3 = r7.mFile
            java.io.File r3 = r3.getBaseFile()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Slog.i(r0, r2)
        L61:
            if (r1 != 0) goto L68
            android.os.Bundle r7 = r7.removeInfoFileAndGetDefaultInfoBundleLocked()
            return r7
        L68:
            java.lang.String r2 = "version"
            r3 = -1
            int r2 = r1.getInt(r2, r3)
            if (r2 != r3) goto L7c
            java.lang.String r1 = "Invalid info file (invalid version). Ignored"
            android.util.Slog.w(r0, r1)
            android.os.Bundle r7 = r7.removeInfoFileAndGetDefaultInfoBundleLocked()
            return r7
        L7c:
            java.lang.String r2 = "uid"
            int r2 = r1.getInt(r2, r3)
            if (r2 != r3) goto L8f
            java.lang.String r1 = "Invalid info file (invalid UID). Ignored"
            android.util.Slog.w(r0, r1)
            android.os.Bundle r7 = r7.removeInfoFileAndGetDefaultInfoBundleLocked()
            return r7
        L8f:
            java.lang.String r4 = "boot-count"
            int r4 = r1.getInt(r4, r3)
            if (r4 == r3) goto Ld7
            android.content.Context r3 = r7.mContext
            android.content.ContentResolver r3 = r3.getContentResolver()
            java.lang.String r5 = "boot_count"
            r6 = 0
            int r3 = android.provider.Settings.Global.getInt(r3, r5, r6)
            if (r4 == r3) goto La9
            goto Ld7
        La9:
            java.lang.String r3 = "info-bundle"
            android.os.PersistableBundle r1 = r1.getPersistableBundle(r3)
            if (r1 != 0) goto Lbc
            java.lang.String r1 = "Invalid info file (missing info). Ignored"
            android.util.Slog.w(r0, r1)
            android.os.Bundle r7 = r7.removeInfoFileAndGetDefaultInfoBundleLocked()
            return r7
        Lbc:
            java.lang.String r3 = "status"
            int r3 = r1.getInt(r3, r6)
            if (r3 != 0) goto Lcf
            java.lang.String r1 = "Invalid info file (invalid status). Ignored"
            android.util.Slog.w(r0, r1)
            android.os.Bundle r7 = r7.removeInfoFileAndGetDefaultInfoBundleLocked()
            return r7
        Lcf:
            r7.mLastUid = r2
            android.os.Bundle r7 = new android.os.Bundle
            r7.<init>(r1)
            return r7
        Ld7:
            java.lang.String r1 = "Outdated info file. Ignored"
            android.util.Slog.w(r0, r1)
            android.os.Bundle r7 = r7.removeInfoFileAndGetDefaultInfoBundleLocked()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SystemUpdateManagerService.loadSystemUpdateInfoLocked():android.os.Bundle");
    }

    public final Bundle removeInfoFileAndGetDefaultInfoBundleLocked() {
        if (this.mFile.exists()) {
            Slog.i("SystemUpdateManagerService", "Removing info file");
            this.mFile.delete();
        }
        this.mLastUid = -1;
        return SystemUpdateManagerService$$ExternalSyntheticOutline0.m(0, Constants.JSON_CLIENT_DATA_STATUS);
    }

    public final Bundle retrieveSystemUpdateInfo() {
        Bundle loadSystemUpdateInfoLocked;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.READ_SYSTEM_UPDATE_INFO") == -1 && this.mContext.checkCallingOrSelfPermission("android.permission.RECOVERY") == -1) {
            throw new SecurityException("Can't read system update info. Requiring READ_SYSTEM_UPDATE_INFO or RECOVERY permission.");
        }
        synchronized (this.mLock) {
            loadSystemUpdateInfoLocked = loadSystemUpdateInfoLocked();
        }
        return loadSystemUpdateInfoLocked;
    }

    public final void saveSystemUpdateInfoLocked(int i, PersistableBundle persistableBundle) {
        PersistableBundle persistableBundle2 = new PersistableBundle();
        persistableBundle2.putPersistableBundle("info-bundle", persistableBundle);
        persistableBundle2.putInt("version", 0);
        persistableBundle2.putInt("uid", i);
        persistableBundle2.putInt("boot-count", Settings.Global.getInt(this.mContext.getContentResolver(), "boot_count", 0));
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream startWrite = this.mFile.startWrite();
            try {
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                resolveSerializer.startTag((String) null, "info");
                persistableBundle2.saveToXml(resolveSerializer);
                resolveSerializer.endTag((String) null, "info");
                resolveSerializer.endDocument();
                this.mFile.finishWrite(startWrite);
                this.mLastUid = i;
                persistableBundle.getInt(Constants.JSON_CLIENT_DATA_STATUS);
            } catch (IOException | XmlPullParserException e) {
                e = e;
                fileOutputStream = startWrite;
                Slog.e("SystemUpdateManagerService", "Failed to save the info file:", e);
                if (fileOutputStream != null) {
                    this.mFile.failWrite(fileOutputStream);
                }
            }
        } catch (IOException | XmlPullParserException e2) {
            e = e2;
        }
    }

    public final void updateSystemUpdateInfo(PersistableBundle persistableBundle) {
        updateSystemUpdateInfo_enforcePermission();
        int i = persistableBundle.getInt(Constants.JSON_CLIENT_DATA_STATUS, 0);
        if (i == 0) {
            Slog.w("SystemUpdateManagerService", "Invalid status info. Ignored");
            return;
        }
        int callingUid = Binder.getCallingUid();
        int i2 = this.mLastUid;
        if (i2 != -1 && i2 != callingUid && i == 1) {
            Slog.i("SystemUpdateManagerService", "Inactive updater reporting IDLE status. Ignored");
            return;
        }
        synchronized (this.mLock) {
            saveSystemUpdateInfoLocked(callingUid, persistableBundle);
        }
    }
}
