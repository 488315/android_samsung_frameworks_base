package com.samsung.android.provider;

import android.os.RemoteException;
import android.util.Slog;
import com.samsung.android.provider.SemDynamicFeature;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class DynamicFeatureManager {
    private String TAG = "DynamicFeature_DynamicFeatureManager";
    private IDynamicFeatureManager mService;

    public DynamicFeatureManager(IDynamicFeatureManager iDynamicFeatureManager) {
        this.mService = iDynamicFeatureManager;
    }

    public SemDynamicFeature.Properties getProperties(String str, String... strArr) {
        IDynamicFeatureManager iDynamicFeatureManager = this.mService;
        if (iDynamicFeatureManager == null) {
            return new SemDynamicFeature.Properties(str, new ArrayList());
        }
        try {
            return iDynamicFeatureManager.getProperties(str, strArr);
        } catch (Exception e) {
            Slog.e(this.TAG, e.getMessage());
            return new SemDynamicFeature.Properties(str, new ArrayList());
        }
    }

    public boolean sendAbTestResult(String str, String str2, String str3) {
        if (this.mService == null) {
            return false;
        }
        if (str3.length() > 10000) {
            Slog.e(this.TAG, "Too long text has been entered. Please reduce it under 1000 characters ");
        }
        try {
            return this.mService.sendAbTestResult(str, str2, str3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setEndpoint(int i) {
        IDynamicFeatureManager iDynamicFeatureManager = this.mService;
        if (iDynamicFeatureManager == null) {
            return -1;
        }
        try {
            return iDynamicFeatureManager.setEndpoint(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getVid() {
        IDynamicFeatureManager iDynamicFeatureManager = this.mService;
        if (iDynamicFeatureManager == null) {
            return null;
        }
        try {
            return iDynamicFeatureManager.getVid();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
