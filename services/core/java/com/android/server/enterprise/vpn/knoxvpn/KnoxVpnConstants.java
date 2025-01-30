package com.android.server.enterprise.vpn.knoxvpn;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* loaded from: classes2.dex */
public abstract class KnoxVpnConstants {
    public static String BLOCK_APP_TRAFFIC = "block_traffic";
    public static final String[] BLACK_LISTED_APPLICATION = {"com.android.providers.downloads"};
    public static final Integer[] BLACK_LISTED_UID = {0, 1000};
    public static final String[] WHITE_LISTED_APPLICATION_TO_SEND_FD = {"com.android.providers.downloads"};
    public static final String DEFAULT_PROXY_SERVER = null;
    public static final String DEFAULT_PROXY_CREDENTIALS = null;
    public static final String DEFAULT_PAC_URL = null;
    public static final String[] APPS_TO_RESTART_PROXY = {"com.android.chrome", KnoxCustomManagerService.SBROWSER_CHAMELEON_PACKAGE_NAME};
    public static final String[] EMAIL_PACKAGE_LIST = {"com.samsung.android.email.provider", "com.samsung.android.email.sync", "com.samsung.android.email.ui", "com.samsung.android.email.widget", "com.samsung.android.email.composer"};
    public static final Integer[] AID_EXEMPT_LIST = {1051, 1029, 1052};
    public static final String[] USB_TETHERING_IFACE = {"rndis", "usb"};
}
