package com.samsung.android.provider;

import android.os.RemoteException;
import android.util.Slog;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public class DynamicFeatureManager {
    private String TAG = "DynamicFeature_DynamicFeatureManager";
    private IDynamicFeatureManager mService;

    public DynamicFeatureManager(IDynamicFeatureManager service) {
        this.mService = service;
    }

    public SemDynamicFeature.Properties getProperties(String namespace, String... names) {
        if (this.mService == null) {
            return new SemDynamicFeature.Properties(namespace, new ArrayList());
        }
        try {
            SemDynamicFeature.Properties properties = this.mService.getProperties(namespace, names);
            return properties;
        } catch (Exception e) {
            Slog.e(this.TAG, e.getMessage());
            return new SemDynamicFeature.Properties(namespace, new ArrayList());
        }
    }

    public boolean sendAbTestResult(String namespace, String name, String message) {
        if (this.mService == null) {
            return false;
        }
        if (message.length() > 10000) {
            Slog.e(
                    this.TAG,
                    "Too long text has been entered. Please reduce it under 1000 characters ");
        }
        try {
            return this.mService.sendAbTestResult(namespace, name, message);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
