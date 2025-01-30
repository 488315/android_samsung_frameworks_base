package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.net.NetworkPolicyManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NetworkPolicyManagerWrapper {
    private static final NetworkPolicyManagerWrapper sInstance = new NetworkPolicyManagerWrapper();
    private static final NetworkPolicyManager mNetworkPolicyManager = (NetworkPolicyManager) AppGlobals.getInitialApplication().getSystemService("netpolicy");

    private NetworkPolicyManagerWrapper() {
    }

    public static NetworkPolicyManagerWrapper getInstance() {
        return sInstance;
    }

    public boolean getRestrictBackground() {
        return mNetworkPolicyManager.getRestrictBackground();
    }
}
