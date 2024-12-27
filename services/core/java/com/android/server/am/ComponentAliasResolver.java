package com.android.server.am;

import android.content.ComponentName;
import android.content.Context;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.content.PackageMonitor;
import com.android.server.compat.PlatformCompat;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ComponentAliasResolver {
    public final Context mContext;
    public boolean mEnabledByDeviceConfig;
    public String mOverrideString;
    public final AnonymousClass1 mPackageMonitor;
    public PlatformCompat mPlatformCompat;
    public final Object mLock = new Object();
    public final ArrayMap mFromTo = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Resolution {
        public final Object resolved;
        public final Object source;

        public Resolution(Object obj, Object obj2) {
            this.source = obj;
            this.resolved = obj2;
        }
    }

    /* renamed from: -$$Nest$mrefresh, reason: not valid java name */
    public static void m187$$Nest$mrefresh(ComponentAliasResolver componentAliasResolver) {
        synchronized (componentAliasResolver.mLock) {
            componentAliasResolver.update(componentAliasResolver.mOverrideString, componentAliasResolver.mEnabledByDeviceConfig);
        }
    }

    public ComponentAliasResolver(ActivityManagerService activityManagerService) {
        new PackageMonitor() { // from class: com.android.server.am.ComponentAliasResolver.1
            public final void onPackageAdded(String str, int i) {
                ComponentAliasResolver.m187$$Nest$mrefresh(ComponentAliasResolver.this);
            }

            public final void onPackageModified(String str) {
                ComponentAliasResolver.m187$$Nest$mrefresh(ComponentAliasResolver.this);
            }

            public final void onPackageRemoved(String str, int i) {
                ComponentAliasResolver.m187$$Nest$mrefresh(ComponentAliasResolver.this);
            }
        };
        Context context = activityManagerService.mContext;
    }

    public final void dump(PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println("ACTIVITY MANAGER COMPONENT-ALIAS (dumpsys activity component-alias)");
                printWriter.print("  Enabled: ");
                printWriter.println(false);
                printWriter.println("  Aliases:");
                for (int i = 0; i < this.mFromTo.size(); i++) {
                    ComponentName componentName = (ComponentName) this.mFromTo.keyAt(i);
                    ComponentName componentName2 = (ComponentName) this.mFromTo.valueAt(i);
                    printWriter.print("    ");
                    printWriter.print(componentName.flattenToShortString());
                    printWriter.print(" -> ");
                    printWriter.print(componentName2.flattenToShortString());
                    printWriter.println();
                }
                printWriter.println();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSystemReady(String str, boolean z) {
        synchronized (this.mLock) {
            this.mPlatformCompat = (PlatformCompat) ServiceManager.getService("platform_compat");
        }
        Slog.d("ComponentAliasResolver", "Compat listener set.");
        update(str, z);
    }

    public final void update(String str, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mPlatformCompat == null) {
                    return;
                }
                this.mEnabledByDeviceConfig = z;
                this.mOverrideString = str;
                this.mFromTo.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
