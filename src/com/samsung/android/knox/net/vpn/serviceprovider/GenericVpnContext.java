package com.samsung.android.knox.net.vpn.serviceprovider;

import android.content.Context;
import android.content.ContextWrapper;

/* loaded from: classes6.dex */
public class GenericVpnContext extends ContextWrapper {
    public static final boolean CONNECTING = true;
    public static final boolean DISCONNECTING = false;
    public static final boolean META_DISABLED = false;
    public static final boolean META_ENABLED = true;
    public static final boolean PER_APP_VPN = false;
    public static final boolean SYSTEM_VPN = true;
    private static String TAG = "GenericVpnContext";
    private boolean mEnableMetaHeader;
    private boolean mIsConnecting;
    private String mProfile;

    public GenericVpnContext(Context context) {
        super(context);
        this.mProfile = "";
        this.mIsConnecting = false;
        this.mEnableMetaHeader = false;
    }

    public void setGenericVpnParams(String str, boolean z) {
        this.mProfile = str;
        this.mIsConnecting = z;
    }

    public void enableMetaData(boolean z) {
        this.mEnableMetaHeader = z;
    }

    public String getVPNProfile() {
        return this.mProfile;
    }

    public boolean getVPNState() {
        return this.mIsConnecting;
    }

    public boolean isMetaEnabled() {
        return this.mEnableMetaHeader;
    }
}
