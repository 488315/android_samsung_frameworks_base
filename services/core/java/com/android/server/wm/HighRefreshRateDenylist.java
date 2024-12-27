package com.android.server.wm;

import android.R;
import android.content.res.Resources;
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.util.ArraySet;

import com.android.internal.os.BackgroundThread;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class HighRefreshRateDenylist {
    public final String[] mDefaultDenylist;
    public final ArraySet mDenylistedPackages = new ArraySet();
    public final Object mLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OnPropertiesChangedListener
            implements DeviceConfig.OnPropertiesChangedListener {
        public OnPropertiesChangedListener() {}

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            if (properties.getKeyset().contains("high_refresh_rate_blacklist")) {
                HighRefreshRateDenylist.this.updateDenylist(
                        properties.getString("high_refresh_rate_blacklist", (String) null));
            }
        }
    }

    public HighRefreshRateDenylist(
            Resources resources, DeviceConfigInterface deviceConfigInterface) {
        this.mDefaultDenylist =
                resources.getStringArray(R.array.vendor_disallowed_apps_managed_device);
        deviceConfigInterface.addOnPropertiesChangedListener(
                "display_manager",
                BackgroundThread.getExecutor(),
                new OnPropertiesChangedListener());
        updateDenylist(
                deviceConfigInterface.getProperty(
                        "display_manager", "high_refresh_rate_blacklist"));
    }

    public final void updateDenylist(String str) {
        synchronized (this.mLock) {
            try {
                this.mDenylistedPackages.clear();
                int i = 0;
                if (str != null) {
                    String[] split = str.split(",");
                    int length = split.length;
                    while (i < length) {
                        String trim = split[i].trim();
                        if (!trim.isEmpty()) {
                            this.mDenylistedPackages.add(trim);
                        }
                        i++;
                    }
                } else {
                    String[] strArr = this.mDefaultDenylist;
                    int length2 = strArr.length;
                    while (i < length2) {
                        this.mDenylistedPackages.add(strArr[i]);
                        i++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
