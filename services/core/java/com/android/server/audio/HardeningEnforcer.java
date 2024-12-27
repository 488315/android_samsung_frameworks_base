package com.android.server.audio;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.audio.Flags;
import android.os.Binder;
import android.text.TextUtils;
import android.util.Slog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HardeningEnforcer {
    public final ActivityManager mActivityManager;
    public final AppOpsManager mAppOps;
    public final Context mContext;
    public final EventLogger mEventLogger = new EventLogger(20, "Hardening enforcement");
    public final boolean mIsAutomotive;
    public final PackageManager mPackageManager;

    public HardeningEnforcer(Context context, boolean z, AppOpsManager appOpsManager, PackageManager packageManager) {
        this.mContext = context;
        this.mIsAutomotive = z;
        this.mAppOps = appOpsManager;
        this.mActivityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
        this.mPackageManager = packageManager;
    }

    public final boolean blockFocusMethod(int i, int i2, String str, String str2, String str3, int i3) {
        if (str2.isEmpty()) {
            str2 = getPackNameForUid(i);
        }
        if (this.mAppOps.noteOpNoThrow(32, i, str2, str3, (String) null) == 0 || i3 < 35) {
            return false;
        }
        StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i, "Focus request DENIED for uid:", " clientId:", str, " req:");
        m.append(i2);
        m.append(" procState:");
        m.append(this.mActivityManager.getUidProcessState(i));
        String sb = m.toString();
        EventLogger eventLogger = this.mEventLogger;
        synchronized (eventLogger) {
            EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(sb);
            stringEvent.printSlog(0, "AS.HardeningEnforcer");
            eventLogger.enqueue(stringEvent);
        }
        return true;
    }

    public final boolean blockVolumeMethod(int i) {
        if (!this.mIsAutomotive || !Flags.autoPublicVolumeApiHardening() || this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED") == 0 || Binder.getCallingUid() < 10000) {
            return false;
        }
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Preventing volume method ", " for ");
        m.append(getPackNameForUid(Binder.getCallingUid()));
        Slog.e("AS.HardeningEnforcer", m.toString());
        return true;
    }

    public final String getPackNameForUid(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid != null && packagesForUid.length != 0 && !TextUtils.isEmpty(packagesForUid[0])) {
                return packagesForUid[0];
            }
            return "[" + i + "]";
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
