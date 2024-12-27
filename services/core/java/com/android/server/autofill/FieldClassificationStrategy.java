package com.android.server.autofill;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.autofill.IAutofillFieldClassificationService;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FieldClassificationStrategy {
    public final Context mContext;
    public final Object mLock = new Object();
    public ArrayList mQueuedCommands;
    public IAutofillFieldClassificationService mRemoteService;
    public AnonymousClass1 mServiceConnection;
    public final int mUserId;

    public FieldClassificationStrategy(Context context, int i) {
        this.mContext = context;
        this.mUserId = i;
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.server.autofill.FieldClassificationStrategy$1] */
    public final void calculateScores(RemoteCallback remoteCallback, List list, String[] strArr, String[] strArr2, String str, Bundle bundle, ArrayMap arrayMap, ArrayMap arrayMap2) {
        FieldClassificationStrategy$$ExternalSyntheticLambda0 fieldClassificationStrategy$$ExternalSyntheticLambda0 = new FieldClassificationStrategy$$ExternalSyntheticLambda0(remoteCallback, list, strArr, strArr2, str, bundle, arrayMap, arrayMap2);
        synchronized (this.mLock) {
            try {
                if (this.mRemoteService != null) {
                    try {
                        if (Helper.sVerbose) {
                            Slog.v("FieldClassificationStrategy", "running command right away");
                        }
                        this.mRemoteService.calculateScores(remoteCallback, list, strArr, strArr2, str, bundle, arrayMap, arrayMap2);
                    } catch (RemoteException e) {
                        Slog.w("FieldClassificationStrategy", "exception calling service: " + e);
                    }
                    return;
                }
                if (Helper.sDebug) {
                    Slog.d("FieldClassificationStrategy", "service is null; queuing command");
                }
                if (this.mQueuedCommands == null) {
                    this.mQueuedCommands = new ArrayList(1);
                }
                this.mQueuedCommands.add(fieldClassificationStrategy$$ExternalSyntheticLambda0);
                if (this.mServiceConnection != null) {
                    return;
                }
                if (Helper.sVerbose) {
                    Slog.v("FieldClassificationStrategy", "creating connection");
                }
                this.mServiceConnection = new ServiceConnection() { // from class: com.android.server.autofill.FieldClassificationStrategy.1
                    @Override // android.content.ServiceConnection
                    public final void onBindingDied(ComponentName componentName) {
                        if (Helper.sVerbose) {
                            Slog.v("FieldClassificationStrategy", "onBindingDied(): " + componentName);
                        }
                        synchronized (FieldClassificationStrategy.this.mLock) {
                            FieldClassificationStrategy.this.mRemoteService = null;
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public final void onNullBinding(ComponentName componentName) {
                        if (Helper.sVerbose) {
                            Slog.v("FieldClassificationStrategy", "onNullBinding(): " + componentName);
                        }
                        synchronized (FieldClassificationStrategy.this.mLock) {
                            FieldClassificationStrategy.this.mRemoteService = null;
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        if (Helper.sVerbose) {
                            Slog.v("FieldClassificationStrategy", "onServiceConnected(): " + componentName);
                        }
                        synchronized (FieldClassificationStrategy.this.mLock) {
                            try {
                                FieldClassificationStrategy.this.mRemoteService = IAutofillFieldClassificationService.Stub.asInterface(iBinder);
                                ArrayList arrayList = FieldClassificationStrategy.this.mQueuedCommands;
                                if (arrayList != null) {
                                    int size = arrayList.size();
                                    if (Helper.sDebug) {
                                        Slog.d("FieldClassificationStrategy", "running " + size + " queued commands");
                                    }
                                    for (int i = 0; i < size; i++) {
                                        FieldClassificationStrategy$$ExternalSyntheticLambda0 fieldClassificationStrategy$$ExternalSyntheticLambda02 = (FieldClassificationStrategy$$ExternalSyntheticLambda0) FieldClassificationStrategy.this.mQueuedCommands.get(i);
                                        try {
                                            if (Helper.sVerbose) {
                                                Slog.v("FieldClassificationStrategy", "running queued command #" + i);
                                            }
                                            FieldClassificationStrategy.this.mRemoteService.calculateScores(fieldClassificationStrategy$$ExternalSyntheticLambda02.f$0, fieldClassificationStrategy$$ExternalSyntheticLambda02.f$1, fieldClassificationStrategy$$ExternalSyntheticLambda02.f$2, fieldClassificationStrategy$$ExternalSyntheticLambda02.f$3, fieldClassificationStrategy$$ExternalSyntheticLambda02.f$4, fieldClassificationStrategy$$ExternalSyntheticLambda02.f$5, fieldClassificationStrategy$$ExternalSyntheticLambda02.f$6, fieldClassificationStrategy$$ExternalSyntheticLambda02.f$7);
                                        } catch (RemoteException e2) {
                                            Slog.w("FieldClassificationStrategy", "exception calling " + componentName + ": " + e2);
                                        }
                                    }
                                    FieldClassificationStrategy.this.mQueuedCommands = null;
                                } else if (Helper.sDebug) {
                                    Slog.d("FieldClassificationStrategy", "no queued commands");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public final void onServiceDisconnected(ComponentName componentName) {
                        if (Helper.sVerbose) {
                            Slog.v("FieldClassificationStrategy", "onServiceDisconnected(): " + componentName);
                        }
                        synchronized (FieldClassificationStrategy.this.mLock) {
                            FieldClassificationStrategy.this.mRemoteService = null;
                        }
                    }
                };
                ComponentName serviceComponentName = getServiceComponentName();
                if (Helper.sVerbose) {
                    Slog.v("FieldClassificationStrategy", "binding to: " + serviceComponentName);
                }
                if (serviceComponentName != null) {
                    Intent intent = new Intent();
                    intent.setComponent(serviceComponentName);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mContext.bindServiceAsUser(intent, this.mServiceConnection, 1, UserHandle.of(this.mUserId));
                        if (Helper.sVerbose) {
                            Slog.v("FieldClassificationStrategy", "bound");
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                return;
            } catch (Throwable th2) {
                throw th2;
            }
            throw th2;
        }
    }

    public final String[] getAvailableAlgorithms() {
        ServiceInfo serviceInfo = getServiceInfo();
        if (serviceInfo == null) {
            return null;
        }
        try {
            return this.mContext.getPackageManager().getResourcesForApplication(serviceInfo.applicationInfo).getStringArray(serviceInfo.metaData.getInt("android.autofill.field_classification.available_algorithms"));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("FieldClassificationStrategy", "Error getting application resources for " + serviceInfo, e);
            return null;
        }
    }

    public final String getDefaultAlgorithm() {
        ServiceInfo serviceInfo = getServiceInfo();
        if (serviceInfo == null) {
            return null;
        }
        try {
            return this.mContext.getPackageManager().getResourcesForApplication(serviceInfo.applicationInfo).getString(serviceInfo.metaData.getInt("android.autofill.field_classification.default_algorithm"));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("FieldClassificationStrategy", "Error getting application resources for " + serviceInfo, e);
            return null;
        }
    }

    public final ComponentName getServiceComponentName() {
        ServiceInfo serviceInfo = getServiceInfo();
        if (serviceInfo == null) {
            return null;
        }
        ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
        if ("android.permission.BIND_AUTOFILL_FIELD_CLASSIFICATION_SERVICE".equals(serviceInfo.permission)) {
            if (Helper.sVerbose) {
                Slog.v("FieldClassificationStrategy", "getServiceComponentName(): " + componentName);
            }
            return componentName;
        }
        Slog.w("FieldClassificationStrategy", componentName.flattenToShortString() + " does not require permission android.permission.BIND_AUTOFILL_FIELD_CLASSIFICATION_SERVICE");
        return null;
    }

    public final ServiceInfo getServiceInfo() {
        ServiceInfo serviceInfo;
        String servicesSystemSharedLibraryPackageName = this.mContext.getPackageManager().getServicesSystemSharedLibraryPackageName();
        if (servicesSystemSharedLibraryPackageName == null) {
            Slog.w("FieldClassificationStrategy", "no external services package!");
            return null;
        }
        ResolveInfo resolveService = this.mContext.getPackageManager().resolveService(ExplicitHealthCheckController$$ExternalSyntheticOutline0.m("android.service.autofill.AutofillFieldClassificationService", servicesSystemSharedLibraryPackageName), 132);
        if (resolveService != null && (serviceInfo = resolveService.serviceInfo) != null) {
            return serviceInfo;
        }
        Slog.w("FieldClassificationStrategy", "No valid components found.");
        return null;
    }
}
