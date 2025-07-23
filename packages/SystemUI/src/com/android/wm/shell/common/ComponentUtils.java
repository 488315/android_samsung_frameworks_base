package com.android.wm.shell.common;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ComponentUtils {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new ComponentUtils();
    }

    private ComponentUtils() {
    }

    public static final String getPackageName(Intent intent) {
        ComponentName component;
        String packageName;
        if (intent != null && (component = intent.getComponent()) != null && (packageName = component.getPackageName()) != null) {
            return packageName;
        }
        if (intent != null) {
            return intent.getPackage();
        }
        return null;
    }

    public static final String getPackageName(PendingIntent pendingIntent) {
        return getPackageName(pendingIntent != null ? pendingIntent.getIntent() : null);
    }
}
