package com.android.server.enterprise.vpn.knoxvpn;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class KnoxVpnConstants {
    public static final String[] BLACK_LISTED_APPLICATION = {"com.android.providers.downloads"};
    public static final String[] APPS_TO_RESTART_PROXY = {"com.android.chrome", KnoxCustomManagerService.SBROWSER_CHAMELEON_PACKAGE_NAME};
    public static final String[] EMAIL_PACKAGE_LIST = {"com.samsung.android.email.provider", "com.samsung.android.email.sync", "com.samsung.android.email.ui", "com.samsung.android.email.widget", "com.samsung.android.email.composer"};
    public static final Integer[] AID_EXEMPT_LIST = {1051, 1029, 1052};
    public static final String[] USB_TETHERING_IFACE = {"rndis", "usb"};
}
