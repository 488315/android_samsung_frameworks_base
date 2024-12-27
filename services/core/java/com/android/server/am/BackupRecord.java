package com.android.server.am;

import android.content.pm.ApplicationInfo;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BackupRecord {
    public ProcessRecord app;
    public final ApplicationInfo appInfo;
    public final int backupDestination;
    public final int backupMode;
    public String stringName;
    public final int userId;

    public BackupRecord(ApplicationInfo applicationInfo, int i, int i2, int i3) {
        this.appInfo = applicationInfo;
        this.backupMode = i;
        this.userId = i2;
        this.backupDestination = i3;
    }

    public final String toString() {
        String str = this.stringName;
        if (str != null) {
            return str;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "BackupRecord{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(' ');
        m.append(this.appInfo.packageName);
        m.append(' ');
        m.append(this.appInfo.name);
        m.append(' ');
        m.append(this.appInfo.backupAgentName);
        m.append('}');
        String sb = m.toString();
        this.stringName = sb;
        return sb;
    }
}
