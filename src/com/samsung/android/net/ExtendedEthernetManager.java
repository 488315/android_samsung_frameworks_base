package com.samsung.android.net;

import android.net.IpConfiguration;
import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes6.dex */
public class ExtendedEthernetManager {
    private static final String TAG = "ExtendedEthernetManager";
    private final IExtendedEthernetManager mService;

    public ExtendedEthernetManager(IExtendedEthernetManager iExtendedEthernetManager) {
        Log.i(TAG, "ExtendedEthernetManager created");
        this.mService = iExtendedEthernetManager;
    }

    public IpConfiguration getConfiguration(String str) {
        try {
            return this.mService.getConfiguration(str);
        } catch (RemoteException e) {
            Log.e(TAG, e.toString());
            return null;
        } catch (Exception e2) {
            Log.e(TAG, e2.toString());
            return null;
        }
    }
}
