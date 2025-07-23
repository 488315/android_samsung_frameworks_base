package com.android.systemui.subscreen;

import android.content.ComponentName;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SubScreenComponentChecker {
    public static boolean isCellBroadCastAlertDialog(ComponentName componentName) {
        String className = componentName.getClassName();
        if (!"com.android.cellbroadcastreceiver.CellBroadcastAlertDialog".equals(className)) {
            return false;
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific class: ", className, "SubScreenPackageChecker");
        return true;
    }
}
