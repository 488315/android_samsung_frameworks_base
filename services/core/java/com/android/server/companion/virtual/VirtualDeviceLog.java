package com.android.server.companion.virtual;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.util.SparseArray;

import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;

public final class VirtualDeviceLog {
    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault());
    public final Context mContext;
    public final ArrayDeque mLogEntries = new ArrayDeque();

    public final class LogEntry {
        public final int mDeviceId;
        public final int mOwnerUid;
        public final long mTimestamp;
        public final int mType;

        public LogEntry(int i, int i2, int i3, long j) {
            this.mType = i;
            this.mDeviceId = i2;
            this.mTimestamp = j;
            this.mOwnerUid = i3;
        }

        public final void dump(
                PrintWriter printWriter, UidToPackageNameCache uidToPackageNameCache) {
            String str;
            String str2;
            StringBuilder sb = new StringBuilder("  ");
            sb.append(VirtualDeviceLog.DATE_FORMAT.format(Instant.ofEpochMilli(this.mTimestamp)));
            sb.append(" - ");
            sb.append(this.mType == 0 ? "START" : "CLOSE");
            sb.append(" Device ID: ");
            sb.append(this.mDeviceId);
            sb.append(" ");
            int i = this.mOwnerUid;
            sb.append(i);
            sb.append(" (");
            if (uidToPackageNameCache.mUidToPackagesCache.contains(i)) {
                str2 = (String) uidToPackageNameCache.mUidToPackagesCache.get(i);
            } else {
                String[] packagesForUid =
                        uidToPackageNameCache.mPackageManager.getPackagesForUid(i);
                if (packagesForUid == null || packagesForUid.length <= 0) {
                    str = "";
                } else {
                    String str3 = packagesForUid[0];
                    str =
                            packagesForUid.length > 1
                                    ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                            str3, ",...")
                                    : str3;
                }
                uidToPackageNameCache.mUidToPackagesCache.put(i, str);
                str2 = str;
            }
            sb.append(str2);
            sb.append(")");
            printWriter.println(sb);
        }
    }

    public final class UidToPackageNameCache {
        public final PackageManager mPackageManager;
        public final SparseArray mUidToPackagesCache = new SparseArray();

        public UidToPackageNameCache(PackageManager packageManager) {
            this.mPackageManager = packageManager;
        }
    }

    public VirtualDeviceLog(Context context) {
        this.mContext = context;
    }
}
