package com.android.systemui.emergency;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.emergency.EmergencyGestureModule;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class EmergencyGestureModule$emergencyGestureIntentFactory$1 implements EmergencyGestureModule.EmergencyGestureIntentFactory {
    public final /* synthetic */ PackageManager $packageManager;
    public final /* synthetic */ Resources $resources;

    public EmergencyGestureModule$emergencyGestureIntentFactory$1(PackageManager packageManager, Resources resources) {
        this.$packageManager = packageManager;
        this.$resources = resources;
    }

    public final Intent invoke() {
        ResolveInfo resolveInfo;
        EmergencyGestureModule emergencyGestureModule = EmergencyGestureModule.INSTANCE;
        PackageManager packageManager = this.$packageManager;
        Resources resources = this.$resources;
        emergencyGestureModule.getClass();
        Intent intent = new Intent("com.android.systemui.action.LAUNCH_EMERGENCY");
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 1048576);
        if (!queryIntentActivities.isEmpty()) {
            String string = resources.getString(R.string.config_preferredEmergencySosPackage);
            if (!TextUtils.isEmpty(string)) {
                Iterator<ResolveInfo> it = queryIntentActivities.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        resolveInfo = queryIntentActivities.get(0);
                        break;
                    }
                    ResolveInfo next = it.next();
                    if (TextUtils.equals(next.activityInfo.packageName, string)) {
                        resolveInfo = next;
                        break;
                    }
                }
            } else {
                resolveInfo = queryIntentActivities.get(0);
            }
        } else {
            resolveInfo = null;
        }
        if (resolveInfo == null) {
            Log.wtf(EmergencyGestureModule.TAG, "Couldn't find an app to process the emergency intent.");
            return null;
        }
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
        intent.setFlags(268435456);
        return intent;
    }
}
