package com.android.systemui.subscreen;

import android.content.ComponentName;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

public final class SubScreenComponentChecker {
    public static boolean isCellBroadCastAlertDialog(ComponentName componentName) {
        String className = componentName.getClassName();
        if (!"com.android.cellbroadcastreceiver.CellBroadcastAlertDialog".equals(className)) {
            return false;
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific class: ", className, "SubScreenPackageChecker");
        return true;
    }
}
