package com.android.systemui;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import com.android.internal.widget.LockPatternUtils;
import java.util.List;

/* loaded from: classes.dex */
public class ActivityIntentHelper {
    public final KeyguardManager mKm;
    public final LockPatternUtils mLpu;
    public final PackageManager mPm;

    public ActivityIntentHelper(Context context) {
        this.mPm = context.getPackageManager();
        this.mKm = (KeyguardManager) context.getSystemService("keyguard");
        this.mLpu = new LockPatternUtils(context);
    }

    public final ActivityInfo getPendingTargetActivityInfo(int i, PendingIntent pendingIntent) {
        List listQueryIntentComponents = pendingIntent.queryIntentComponents(852096);
        if (listQueryIntentComponents.size() == 0) {
            return null;
        }
        if (listQueryIntentComponents.size() == 1) {
            return ((ResolveInfo) listQueryIntentComponents.get(0)).activityInfo;
        }
        ResolveInfo resolveInfoResolveActivityAsUser = this.mPm.resolveActivityAsUser(pendingIntent.getIntent(), 852096, i);
        if (resolveInfoResolveActivityAsUser == null || wouldLaunchResolverActivity(resolveInfoResolveActivityAsUser, listQueryIntentComponents)) {
            return null;
        }
        return resolveInfoResolveActivityAsUser.activityInfo;
    }

    public final ActivityInfo getTargetActivityInfo(Intent intent, boolean z, int i) {
        int i2 = !z ? 852096 : 65664;
        List listQueryIntentActivitiesAsUser = this.mPm.queryIntentActivitiesAsUser(intent, i2, i);
        if (listQueryIntentActivitiesAsUser.size() == 0) {
            return null;
        }
        if (listQueryIntentActivitiesAsUser.size() == 1) {
            return ((ResolveInfo) listQueryIntentActivitiesAsUser.get(0)).activityInfo;
        }
        ResolveInfo resolveInfoResolveActivityAsUser = this.mPm.resolveActivityAsUser(intent, i2, i);
        if (resolveInfoResolveActivityAsUser == null || wouldLaunchResolverActivity(resolveInfoResolveActivityAsUser, listQueryIntentActivitiesAsUser)) {
            return null;
        }
        return resolveInfoResolveActivityAsUser.activityInfo;
    }

    public final boolean wouldLaunchResolverActivity(int i, Intent intent) {
        return (intent != null && "android.intent.action.CHOOSER".equals(intent.getAction())) || getTargetActivityInfo(intent, false, i) == null;
    }

    public final boolean wouldPendingShowOverLockscreen(int i, PendingIntent pendingIntent) {
        ActivityInfo pendingTargetActivityInfo = getPendingTargetActivityInfo(i, pendingIntent);
        return pendingTargetActivityInfo != null && (pendingTargetActivityInfo.flags & 8389632) > 0;
    }

    public static boolean wouldLaunchResolverActivity(ResolveInfo resolveInfo, List list) {
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo resolveInfo2 = (ResolveInfo) list.get(i);
            if (resolveInfo2.activityInfo.name.equals(resolveInfo.activityInfo.name) && resolveInfo2.activityInfo.packageName.equals(resolveInfo.activityInfo.packageName)) {
                return false;
            }
        }
        return true;
    }
}
