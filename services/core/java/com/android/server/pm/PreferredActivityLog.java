package com.android.server.pm;

import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Binder;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

import com.samsung.android.server.pm.PmLog;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class PreferredActivityLog {
    public static void logDroppingPreferredActivity(
            PreferredActivity preferredActivity, List list, String str) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("Dropping Preferred: ");
        PreferredComponent preferredComponent = preferredActivity.mPref;
        sb2.append(preferredComponent.mShortComponent);
        sb2.append("\n");
        sb.append(sb2.toString());
        sb.append(str + "\n");
        String[] strArr = preferredComponent.mSetComponents;
        int i = 0;
        if (strArr != null) {
            sb.append("Sets:\n");
            int i2 = 0;
            while (i2 < strArr.length) {
                sb.append("  " + strArr[i2]);
                i2++;
                if (i2 < strArr.length) {
                    sb.append("\n");
                }
            }
        }
        if (list != null) {
            sb.append("\nQuery sets: \n");
            int size = list.size();
            while (i < size) {
                ActivityInfo activityInfo = ((ResolveInfo) list.get(i)).activityInfo;
                sb.append("  " + activityInfo.packageName + "/" + activityInfo.name);
                i++;
                if (i < size) {
                    sb.append("\n");
                }
            }
        }
        PmLog.logDebugInfoAndLogcat(
                sb.toString()
                        + ", uid: "
                        + Binder.getCallingUid()
                        + ", pid: "
                        + Binder.getCallingPid());
    }

    public static void logPreferenceChange(PreferredActivity preferredActivity, String str) {
        StringBuilder sb = new StringBuilder();
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, ": ");
        PreferredComponent preferredComponent = preferredActivity.mPref;
        m.append(preferredComponent.mShortComponent);
        m.append("\n");
        sb.append(m.toString());
        String[] strArr = preferredComponent.mSetComponents;
        if (strArr != null) {
            sb.append("Sets:\n");
            int i = 0;
            while (i < strArr.length) {
                sb.append("  " + strArr[i]);
                i++;
                if (i < strArr.length) {
                    sb.append("\n");
                }
            }
        }
        PmLog.logDebugInfoAndLogcat(
                sb.toString()
                        + ", uid: "
                        + Binder.getCallingUid()
                        + ", pid: "
                        + Binder.getCallingPid());
    }
}
