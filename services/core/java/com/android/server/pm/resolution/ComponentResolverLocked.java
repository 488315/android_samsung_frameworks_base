package com.android.server.pm.resolution;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ProviderInfo;

import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.server.pm.Computer;
import com.android.server.pm.DumpState;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerTracedLock;
import com.android.server.pm.UserManagerService;

import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class ComponentResolverLocked extends ComponentResolverBase {
    public final PackageManagerTracedLock mLock;

    public ComponentResolverLocked(UserManagerService userManagerService) {
        super(userManagerService);
        this.mLock = new PackageManagerTracedLock(null);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final boolean componentExists(ComponentName componentName) {
        boolean componentExists;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                componentExists = super.componentExists(componentName);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return componentExists;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final void dumpActivityResolvers(
            DumpState dumpState, PrintWriter printWriter, String str) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpActivityResolvers(dumpState, printWriter, str);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final void dumpContentProviders(
            Computer computer, PrintWriter printWriter, DumpState dumpState, String str) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpContentProviders(computer, printWriter, dumpState, str);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final void dumpProviderResolvers(
            DumpState dumpState, PrintWriter printWriter, String str) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpProviderResolvers(dumpState, printWriter, str);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final void dumpReceiverResolvers(
            DumpState dumpState, PrintWriter printWriter, String str) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpReceiverResolvers(dumpState, printWriter, str);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final void dumpServicePermissions(PrintWriter printWriter, DumpState dumpState) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpServicePermissions(printWriter, dumpState);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final void dumpServiceResolvers(
            DumpState dumpState, PrintWriter printWriter, String str) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpServiceResolvers(dumpState, printWriter, str);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final ParsedActivity getActivity(ComponentName componentName) {
        ParsedActivity activity;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                activity = super.getActivity(componentName);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return activity;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final ParsedProvider getProvider(ComponentName componentName) {
        ParsedProvider provider;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                provider = super.getProvider(componentName);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return provider;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final ParsedActivity getReceiver(ComponentName componentName) {
        ParsedActivity receiver;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                receiver = super.getReceiver(componentName);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return receiver;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final ParsedService getService(ComponentName componentName) {
        ParsedService service;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                service = super.getService(componentName);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return service;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final List queryActivities(Computer computer, Intent intent, String str, long j, int i) {
        List queryIntent;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                queryIntent = this.mActivities.queryIntent(computer, intent, str, j, i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return queryIntent;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final List queryActivities(
            Computer computer, Intent intent, String str, long j, List list, int i) {
        List queryIntentForPackage;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                queryIntentForPackage =
                        this.mActivities.queryIntentForPackage(computer, intent, str, j, list, i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return queryIntentForPackage;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final ProviderInfo queryProvider(Computer computer, String str, long j, int i) {
        ProviderInfo queryProvider;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                queryProvider = super.queryProvider(computer, str, j, i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return queryProvider;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final List queryProviders(Computer computer, Intent intent, String str, long j, int i) {
        List queryProviders;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                queryProviders = super.queryProviders(computer, intent, str, j, i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return queryProviders;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final List queryProviders(
            Computer computer, Intent intent, String str, long j, List list, int i) {
        List queryProviders;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                queryProviders = super.queryProviders(computer, intent, str, j, list, i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return queryProviders;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final List queryProviders(
            Computer computer, String str, String str2, int i, long j, int i2) {
        List queryProviders;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                queryProviders = super.queryProviders(computer, str, str2, i, j, i2);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return queryProviders;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final List queryReceivers(Computer computer, Intent intent, String str, long j, int i) {
        List queryIntent;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                queryIntent = this.mReceivers.queryIntent(computer, intent, str, j, i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return queryIntent;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final List queryReceivers(
            Computer computer, Intent intent, String str, long j, List list, int i) {
        List queryIntentForPackage;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                queryIntentForPackage =
                        this.mReceivers.queryIntentForPackage(computer, intent, str, j, list, i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return queryIntentForPackage;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final List queryServices(Computer computer, Intent intent, String str, long j, int i) {
        List queryServices;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                queryServices = super.queryServices(computer, intent, str, j, i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return queryServices;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final List queryServices(
            Computer computer, Intent intent, String str, long j, List list, int i) {
        List queryServices;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                queryServices = super.queryServices(computer, intent, str, j, list, i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return queryServices;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverBase,
              // com.android.server.pm.resolution.ComponentResolverApi
    public final void querySyncProviders(
            Computer computer, List list, List list2, boolean z, int i) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                super.querySyncProviders(computer, list, list2, z, i);
            } catch (Throwable th) {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }
}
