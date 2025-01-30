package com.android.server.am;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import com.android.internal.content.PackageMonitor;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.compat.PlatformCompat;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class ComponentAliasResolver {
  public final ActivityManagerService mAm;
  public final Context mContext;
  public boolean mEnabled;
  public boolean mEnabledByDeviceConfig;
  public String mOverrideString;
  public PlatformCompat mPlatformCompat;
  public final Object mLock = new Object();
  public final ArrayMap mFromTo = new ArrayMap();
  public final PackageMonitor mPackageMonitor =
      new PackageMonitor() { // from class: com.android.server.am.ComponentAliasResolver.1
        public void onPackageModified(String str) {
          ComponentAliasResolver.this.refresh();
        }

        public void onPackageAdded(String str, int i) {
          ComponentAliasResolver.this.refresh();
        }

        public void onPackageRemoved(String str, int i) {
          ComponentAliasResolver.this.refresh();
        }
      };

  public ComponentAliasResolver(ActivityManagerService activityManagerService) {
    this.mAm = activityManagerService;
    this.mContext = activityManagerService.mContext;
  }

  public void onSystemReady(boolean z, String str) {
    synchronized (this.mLock) {
      this.mPlatformCompat = (PlatformCompat) ServiceManager.getService("platform_compat");
    }
    Slog.d("ComponentAliasResolver", "Compat listener set.");
    update(z, str);
  }

  public void update(boolean z, String str) {
    synchronized (this.mLock) {
      if (this.mPlatformCompat == null) {
        return;
      }
      if (this.mEnabled) {
        Slog.i("ComponentAliasResolver", "Disabling component aliases...");
        FgThread.getHandler()
            .post(
                new Runnable() { // from class:
                                 // com.android.server.am.ComponentAliasResolver$$ExternalSyntheticLambda0
                  @Override // java.lang.Runnable
                  public final void run() {
                    ComponentAliasResolver.this.lambda$update$0();
                  }
                });
      }
      this.mEnabled = false;
      this.mEnabledByDeviceConfig = z;
      this.mOverrideString = str;
      this.mFromTo.clear();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$update$0() {
    this.mPackageMonitor.unregister();
  }

  public final void refresh() {
    synchronized (this.mLock) {
      update(this.mEnabledByDeviceConfig, this.mOverrideString);
    }
  }

  public void dump(PrintWriter printWriter) {
    synchronized (this.mLock) {
      printWriter.println("ACTIVITY MANAGER COMPONENT-ALIAS (dumpsys activity component-alias)");
      printWriter.print("  Enabled: ");
      printWriter.println(this.mEnabled);
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
    }
  }

  public class Resolution {
    public final Object resolved;
    public final Object source;

    public Resolution(Object obj, Object obj2) {
      this.source = obj;
      this.resolved = obj2;
    }

    public boolean isAlias() {
      return this.resolved != null;
    }

    public Object getAlias() {
      if (isAlias()) {
        return this.source;
      }
      return null;
    }

    public Object getTarget() {
      if (isAlias()) {
        return this.resolved;
      }
      return null;
    }
  }

  public Resolution resolveComponentAlias(Supplier supplier) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        if (!this.mEnabled) {
          return new Resolution(null, null);
        }
        ComponentName componentName = (ComponentName) supplier.get();
        ComponentName componentName2 = (ComponentName) this.mFromTo.get(componentName);
        if (componentName2 != null) {
          Slog.d(
              "ComponentAliasResolver",
              "Alias resolved: "
                  + componentName.flattenToShortString()
                  + " -> "
                  + componentName2.flattenToShortString(),
              Log.isLoggable("ComponentAliasResolver", 2)
                  ? new RuntimeException("STACKTRACE")
                  : null);
        }
        return new Resolution(componentName, componentName2);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public Resolution resolveService(
      final Intent intent, final String str, final int i, final int i2, final int i3) {
    Resolution resolveComponentAlias =
        resolveComponentAlias(
            new Supplier() { // from class:
                             // com.android.server.am.ComponentAliasResolver$$ExternalSyntheticLambda2
              @Override // java.util.function.Supplier
              public final Object get() {
                ComponentName lambda$resolveService$1;
                lambda$resolveService$1 =
                    ComponentAliasResolver.lambda$resolveService$1(intent, str, i, i2, i3);
                return lambda$resolveService$1;
              }
            });
    if (resolveComponentAlias != null && resolveComponentAlias.isAlias()) {
      intent.setOriginalIntent(new Intent(intent));
      intent.setPackage(null);
      intent.setComponent((ComponentName) resolveComponentAlias.getTarget());
    }
    return resolveComponentAlias;
  }

  public static /* synthetic */ ComponentName lambda$resolveService$1(
      Intent intent, String str, int i, int i2, int i3) {
    ResolveInfo resolveService =
        ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
            .resolveService(intent, str, i, i2, i3);
    ServiceInfo serviceInfo = resolveService != null ? resolveService.serviceInfo : null;
    if (serviceInfo == null) {
      return null;
    }
    return new ComponentName(serviceInfo.applicationInfo.packageName, serviceInfo.name);
  }

  public Resolution resolveReceiver(
      Intent intent, final ResolveInfo resolveInfo, String str, int i, int i2, int i3, boolean z) {
    Resolution resolveComponentAlias =
        resolveComponentAlias(
            new Supplier() { // from class:
                             // com.android.server.am.ComponentAliasResolver$$ExternalSyntheticLambda1
              @Override // java.util.function.Supplier
              public final Object get() {
                ComponentName lambda$resolveReceiver$2;
                lambda$resolveReceiver$2 =
                    ComponentAliasResolver.lambda$resolveReceiver$2(resolveInfo);
                return lambda$resolveReceiver$2;
              }
            });
    ComponentName componentName = (ComponentName) resolveComponentAlias.getTarget();
    if (componentName == null) {
      return new Resolution(resolveInfo, null);
    }
    PackageManagerInternal packageManagerInternal =
        (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    Intent intent2 = new Intent(intent);
    intent2.setPackage(null);
    intent2.setComponent((ComponentName) resolveComponentAlias.getTarget());
    List queryIntentReceivers =
        packageManagerInternal.queryIntentReceivers(intent2, str, i, i3, i2, z);
    if (queryIntentReceivers == null || queryIntentReceivers.size() == 0) {
      Slog.w(
          "ComponentAliasResolver",
          "Alias target " + componentName.flattenToShortString() + " not found");
      return null;
    }
    return new Resolution(resolveInfo, (ResolveInfo) queryIntentReceivers.get(0));
  }

  public static /* synthetic */ ComponentName lambda$resolveReceiver$2(ResolveInfo resolveInfo) {
    return resolveInfo.activityInfo.getComponentName();
  }
}
