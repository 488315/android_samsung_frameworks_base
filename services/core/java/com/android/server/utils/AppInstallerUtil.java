package com.android.server.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class AppInstallerUtil {
    public static Intent createIntent(Context context, String str) {
        String str2;
        Intent intent;
        try {
            str2 = context.getPackageManager().getInstallerPackageName(str);
        } catch (IllegalArgumentException e) {
            Log.e("AppInstallerUtil", "Exception while retrieving the package installer of " + str, e);
            str2 = null;
        }
        if (str2 == null) {
            str2 = null;
        }
        Intent intent2 = new Intent("android.intent.action.SHOW_APP_INFO").setPackage(str2);
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent2, 0);
        if (resolveActivity != null) {
            Intent intent3 = new Intent(intent2.getAction());
            ActivityInfo activityInfo = resolveActivity.activityInfo;
            intent = intent3.setClassName(activityInfo.packageName, activityInfo.name);
        } else {
            intent = null;
        }
        if (intent == null) {
            return null;
        }
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.addFlags(268435456);
        return intent;
    }
}
