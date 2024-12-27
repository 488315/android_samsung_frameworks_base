package com.android.systemui.subscreen;

import android.content.ComponentName;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
