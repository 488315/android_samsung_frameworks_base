package com.google.android.setupcompat.partnerconfig;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ResourceEntry {
    static final String KEY_FALLBACK_CONFIG = "fallbackConfig";
    static final String KEY_PACKAGE_NAME = "packageName";
    static final String KEY_RESOURCE_ID = "resourceId";
    static final String KEY_RESOURCE_NAME = "resourceName";
    public final String packageName;
    public final int resourceId;
    public final String resourceName;
    public final Resources resources;

    @Deprecated
    public ResourceEntry(String str, String str2, int i) {
        this(str, str2, i, null);
    }

    public static ResourceEntry fromBundle(Context context, Bundle bundle) {
        if (bundle.containsKey("packageName") && bundle.containsKey(KEY_RESOURCE_NAME) && bundle.containsKey(KEY_RESOURCE_ID)) {
            String string = bundle.getString("packageName");
            String string2 = bundle.getString(KEY_RESOURCE_NAME);
            int i = bundle.getInt(KEY_RESOURCE_ID);
            try {
                PackageManager packageManager = context.getPackageManager();
                return new ResourceEntry(string, string2, i, packageManager.getResourcesForApplication(packageManager.getApplicationInfo(string, 512)));
            } catch (PackageManager.NameNotFoundException unused) {
                Bundle bundle2 = bundle.getBundle("fallbackConfig");
                if (bundle2 != null) {
                    Log.w("ResourceEntry", string + " not found, " + string2 + " fallback to default value");
                    return fromBundle(context, bundle2);
                }
            }
        }
        return null;
    }

    public ResourceEntry(String str, String str2, int i, Resources resources) {
        this.packageName = str;
        this.resourceName = str2;
        this.resourceId = i;
        this.resources = resources;
    }
}
