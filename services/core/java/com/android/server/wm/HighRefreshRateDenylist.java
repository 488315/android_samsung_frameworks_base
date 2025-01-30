package com.android.server.wm;

import android.R;
import android.content.res.Resources;
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.util.ArraySet;
import com.android.internal.os.BackgroundThread;
import java.io.PrintWriter;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class HighRefreshRateDenylist {
    public final String[] mDefaultDenylist;
    public final ArraySet mDenylistedPackages = new ArraySet();
    public final Object mLock = new Object();

    public static HighRefreshRateDenylist create(Resources resources) {
        return new HighRefreshRateDenylist(resources, DeviceConfigInterface.REAL);
    }

    public HighRefreshRateDenylist(Resources resources, DeviceConfigInterface deviceConfigInterface) {
        this.mDefaultDenylist = resources.getStringArray(R.array.resolver_target_actions_unpin);
        deviceConfigInterface.addOnPropertiesChangedListener("display_manager", BackgroundThread.getExecutor(), new OnPropertiesChangedListener());
        updateDenylist(deviceConfigInterface.getProperty("display_manager", "high_refresh_rate_blacklist"));
    }

    public final void updateDenylist(String str) {
        synchronized (this.mLock) {
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
        }
    }

    public boolean isDenylisted(String str) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mDenylistedPackages.contains(str);
        }
        return contains;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("High Refresh Rate Denylist");
        printWriter.println("  Packages:");
        synchronized (this.mLock) {
            Iterator it = this.mDenylistedPackages.iterator();
            while (it.hasNext()) {
                printWriter.println("    " + ((String) it.next()));
            }
        }
    }

    public class OnPropertiesChangedListener implements DeviceConfig.OnPropertiesChangedListener {
        public OnPropertiesChangedListener() {
        }

        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            if (properties.getKeyset().contains("high_refresh_rate_blacklist")) {
                HighRefreshRateDenylist.this.updateDenylist(properties.getString("high_refresh_rate_blacklist", (String) null));
            }
        }
    }
}
