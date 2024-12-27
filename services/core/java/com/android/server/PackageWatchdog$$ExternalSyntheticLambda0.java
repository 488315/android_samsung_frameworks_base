package com.android.server;

import android.os.Handler;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.watchdog.ExplicitHealthCheckService;
import android.util.ArraySet;
import android.util.Slog;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public final /* synthetic */ class PackageWatchdog$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PackageWatchdog f$0;

    public /* synthetic */ PackageWatchdog$$ExternalSyntheticLambda0(PackageWatchdog packageWatchdog, int i) {
        this.$r8$classId = i;
        this.f$0 = packageWatchdog;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        boolean z2;
        int i = this.$r8$classId;
        PackageWatchdog packageWatchdog = this.f$0;
        switch (i) {
            case 0:
                synchronized (packageWatchdog.mLock) {
                    try {
                        if (packageWatchdog.mIsPackagesReady) {
                            Set packagesPendingHealthChecksLocked = packageWatchdog.getPackagesPendingHealthChecksLocked();
                            if (!packageWatchdog.mSyncRequired) {
                                ArraySet arraySet = (ArraySet) packagesPendingHealthChecksLocked;
                                if (arraySet.equals(packageWatchdog.mRequestedHealthCheckPackages)) {
                                    if (arraySet.isEmpty()) {
                                    }
                                }
                            }
                            packageWatchdog.mRequestedHealthCheckPackages = packagesPendingHealthChecksLocked;
                            z = true;
                        }
                        z = false;
                    } finally {
                    }
                }
                if (z) {
                    Slog.i("PackageWatchdog", "Syncing health check requests for packages: " + packageWatchdog.mRequestedHealthCheckPackages);
                    final ExplicitHealthCheckController explicitHealthCheckController = packageWatchdog.mHealthCheckController;
                    final Set set = packageWatchdog.mRequestedHealthCheckPackages;
                    synchronized (explicitHealthCheckController.mLock) {
                        z2 = explicitHealthCheckController.mEnabled;
                    }
                    if (z2) {
                        Consumer consumer = new Consumer() { // from class: com.android.server.ExplicitHealthCheckController$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                final ExplicitHealthCheckController explicitHealthCheckController2 = ExplicitHealthCheckController.this;
                                final Set set2 = set;
                                final List list = (List) obj;
                                explicitHealthCheckController2.mSupportedConsumer.accept(list);
                                ?? r1 = new Consumer() { // from class: com.android.server.ExplicitHealthCheckController$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj2) {
                                        final ExplicitHealthCheckController explicitHealthCheckController3 = ExplicitHealthCheckController.this;
                                        List list2 = list;
                                        Set<String> set3 = set2;
                                        List<String> list3 = (List) obj2;
                                        synchronized (explicitHealthCheckController3.mLock) {
                                            try {
                                                ArraySet arraySet2 = new ArraySet();
                                                Iterator it = list2.iterator();
                                                while (it.hasNext()) {
                                                    arraySet2.add(((ExplicitHealthCheckService.PackageConfig) it.next()).getPackageName());
                                                }
                                                set3.retainAll(arraySet2);
                                                final int i2 = 0;
                                                Consumer consumer2 = new Consumer() { // from class: com.android.server.ExplicitHealthCheckController$$ExternalSyntheticLambda4
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj3) {
                                                        int i3 = i2;
                                                        ExplicitHealthCheckController explicitHealthCheckController4 = explicitHealthCheckController3;
                                                        String str = (String) obj3;
                                                        switch (i3) {
                                                            case 0:
                                                                synchronized (explicitHealthCheckController4.mLock) {
                                                                    try {
                                                                        if (explicitHealthCheckController4.prepareServiceLocked("cancel health check for " + str)) {
                                                                            Slog.i("ExplicitHealthCheckController", "Cancelling health check for package " + str);
                                                                            try {
                                                                                explicitHealthCheckController4.mRemoteService.cancel(str);
                                                                            } catch (RemoteException e) {
                                                                                Slog.w("ExplicitHealthCheckController", "Failed to cancel health check for package " + str, e);
                                                                            }
                                                                            return;
                                                                        }
                                                                        return;
                                                                    } finally {
                                                                    }
                                                                }
                                                            default:
                                                                synchronized (explicitHealthCheckController4.mLock) {
                                                                    try {
                                                                        if (explicitHealthCheckController4.prepareServiceLocked("request health check for " + str)) {
                                                                            Slog.i("ExplicitHealthCheckController", "Requesting health check for package " + str);
                                                                            try {
                                                                                explicitHealthCheckController4.mRemoteService.request(str);
                                                                            } catch (RemoteException e2) {
                                                                                Slog.w("ExplicitHealthCheckController", "Failed to request health check for package " + str, e2);
                                                                            }
                                                                            return;
                                                                        }
                                                                        return;
                                                                    } finally {
                                                                    }
                                                                }
                                                        }
                                                    }
                                                };
                                                for (String str : list3) {
                                                    if (!set3.contains(str)) {
                                                        consumer2.accept(str);
                                                    }
                                                }
                                                final int i3 = 1;
                                                Consumer consumer3 = new Consumer() { // from class: com.android.server.ExplicitHealthCheckController$$ExternalSyntheticLambda4
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj3) {
                                                        int i32 = i3;
                                                        ExplicitHealthCheckController explicitHealthCheckController4 = explicitHealthCheckController3;
                                                        String str2 = (String) obj3;
                                                        switch (i32) {
                                                            case 0:
                                                                synchronized (explicitHealthCheckController4.mLock) {
                                                                    try {
                                                                        if (explicitHealthCheckController4.prepareServiceLocked("cancel health check for " + str2)) {
                                                                            Slog.i("ExplicitHealthCheckController", "Cancelling health check for package " + str2);
                                                                            try {
                                                                                explicitHealthCheckController4.mRemoteService.cancel(str2);
                                                                            } catch (RemoteException e) {
                                                                                Slog.w("ExplicitHealthCheckController", "Failed to cancel health check for package " + str2, e);
                                                                            }
                                                                            return;
                                                                        }
                                                                        return;
                                                                    } finally {
                                                                    }
                                                                }
                                                            default:
                                                                synchronized (explicitHealthCheckController4.mLock) {
                                                                    try {
                                                                        if (explicitHealthCheckController4.prepareServiceLocked("request health check for " + str2)) {
                                                                            Slog.i("ExplicitHealthCheckController", "Requesting health check for package " + str2);
                                                                            try {
                                                                                explicitHealthCheckController4.mRemoteService.request(str2);
                                                                            } catch (RemoteException e2) {
                                                                                Slog.w("ExplicitHealthCheckController", "Failed to request health check for package " + str2, e2);
                                                                            }
                                                                            return;
                                                                        }
                                                                        return;
                                                                    } finally {
                                                                    }
                                                                }
                                                        }
                                                    }
                                                };
                                                for (String str2 : set3) {
                                                    if (!list3.contains(str2)) {
                                                        consumer3.accept(str2);
                                                    }
                                                }
                                                if (set3.isEmpty()) {
                                                    Slog.i("ExplicitHealthCheckController", "No more health check requests, unbinding...");
                                                    explicitHealthCheckController3.unbindService();
                                                }
                                            } finally {
                                            }
                                        }
                                    }
                                };
                                synchronized (explicitHealthCheckController2.mLock) {
                                    try {
                                        if (explicitHealthCheckController2.prepareServiceLocked("get health check requested packages")) {
                                            Slog.d("ExplicitHealthCheckController", "Getting health check requested packages");
                                            try {
                                                explicitHealthCheckController2.mRemoteService.getRequestedPackages(new RemoteCallback(new ExplicitHealthCheckController$$ExternalSyntheticLambda3(r1)));
                                            } catch (RemoteException e) {
                                                Slog.w("ExplicitHealthCheckController", "Failed to get health check requested packages", e);
                                            }
                                        }
                                    } finally {
                                    }
                                }
                            }
                        };
                        synchronized (explicitHealthCheckController.mLock) {
                            try {
                                if (explicitHealthCheckController.prepareServiceLocked("get health check supported packages")) {
                                    Slog.d("ExplicitHealthCheckController", "Getting health check supported packages");
                                    try {
                                        explicitHealthCheckController.mRemoteService.getSupportedPackages(new RemoteCallback(new ExplicitHealthCheckController$$ExternalSyntheticLambda3(0, consumer)));
                                    } catch (RemoteException e) {
                                        Slog.w("ExplicitHealthCheckController", "Failed to get health check supported packages", e);
                                    }
                                }
                            } finally {
                            }
                        }
                    } else {
                        Slog.i("ExplicitHealthCheckController", "Health checks disabled, no supported packages");
                        explicitHealthCheckController.mSupportedConsumer.accept(Collections.emptyList());
                    }
                    packageWatchdog.mSyncRequired = false;
                    return;
                }
                return;
            case 1:
                synchronized (packageWatchdog.mLock) {
                    packageWatchdog.mSyncRequired = true;
                    Handler handler = packageWatchdog.mShortTaskHandler;
                    PackageWatchdog$$ExternalSyntheticLambda0 packageWatchdog$$ExternalSyntheticLambda0 = packageWatchdog.mSyncRequests;
                    handler.removeCallbacks(packageWatchdog$$ExternalSyntheticLambda0);
                    handler.post(packageWatchdog$$ExternalSyntheticLambda0);
                }
                return;
            case 2:
                packageWatchdog.checkAndMitigateNativeCrashes();
                return;
            case 3:
                packageWatchdog.syncState("scheduled");
                return;
            case 4:
                packageWatchdog.saveToFile();
                return;
            default:
                packageWatchdog.checkAndMitigateNativeCrashes();
                return;
        }
    }
}
