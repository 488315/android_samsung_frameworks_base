package com.samsung.android.net;

import android.net.IpConfiguration;
import android.p009os.RemoteException;
import android.util.Log;

/* loaded from: classes5.dex */
public class ExtendedEthernetManager {
    private static final String TAG = "ExtendedEthernetManager";
    private final IExtendedEthernetManager mService;

    public ExtendedEthernetManager(IExtendedEthernetManager service) {
        Log.m98i(TAG, "ExtendedEthernetManager created");
        this.mService = service;
    }

    public IpConfiguration getConfiguration(String iface) {
        try {
            return this.mService.getConfiguration(iface);
        } catch (RemoteException e) {
            Log.m96e(TAG, e.toString());
            return null;
        } catch (Exception e2) {
            Log.m96e(TAG, e2.toString());
            return null;
        }
    }
}
