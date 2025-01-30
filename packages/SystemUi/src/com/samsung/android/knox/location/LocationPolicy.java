package com.samsung.android.knox.location;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.location.ILocationPolicy;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LocationPolicy {
    public static String TAG = "LocationPolicy";
    public ContextInfo mContextInfo;
    public ILocationPolicy mService;

    public LocationPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final List<String> getAllLocationProviders() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getAllLocationProviders");
        Log.w(TAG, "LocationPolicy.getAllLocationProviders - Deprecated API LEVEL 30");
        return new ArrayList();
    }

    public final boolean getLocationProviderState(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getLocationProviderState");
        Log.w(TAG, "LocationPolicy.getLocationProviderState - Deprecated API LEVEL 30");
        return true;
    }

    public final ILocationPolicy getService() {
        if (this.mService == null) {
            this.mService = ILocationPolicy.Stub.asInterface(ServiceManager.getService("location_policy"));
        }
        return this.mService;
    }

    public final boolean isGPSOn() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isGPSOn");
        Log.w(TAG, ">>> isGPSOn");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isGPSOn(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "isGPSOn - Failed talking with Location service", e);
            return false;
        }
    }

    public final boolean isGPSStateChangeAllowed() {
        Log.w(TAG, ">>> isGPSStateChangeAllowed");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isGPSStateChangeAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "isGPSStateChangeAllowed - Failed talking with Location service", e);
            return true;
        }
    }

    public final boolean isLocationProviderBlocked(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "isLocationProviderBlocked");
        Log.w(TAG, "LocationPolicy.isLocationProviderBlocked - Deprecated API LEVEL 30");
        return false;
    }

    public final boolean setGPSStateChangeAllowed(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "LocationPolicy.setGPSStateChangeAllowed");
        Log.w(TAG, ">>> setGPSStateChangeAllowed");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setGPSStateChangeAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "setGPSStateChangeAllowed - Failed talking with Location service", e);
            return false;
        }
    }

    public final boolean setLocationProviderState(String str, boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setLocationProviderState");
        Log.w(TAG, "LocationPolicy.setLocationProviderState - Deprecated API LEVEL 30");
        return false;
    }

    public final boolean startGPS(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "startGPS");
        EnterpriseLicenseManager.log(this.mContextInfo, "LocationPolicy.startGPS");
        Log.w(TAG, ">>> startGPS");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.startGPS(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "startGPS - Failed talking with Location service", e);
            return false;
        }
    }
}
