package com.samsung.android.allshare;

/* loaded from: classes6.dex */
public final class ServiceConnectionChecker {
    public static boolean isAllShareServiceConnected(IAllShareConnector iAllShareConnector) {
        return iAllShareConnector != null && iAllShareConnector.isAllShareServiceConnected();
    }
}
