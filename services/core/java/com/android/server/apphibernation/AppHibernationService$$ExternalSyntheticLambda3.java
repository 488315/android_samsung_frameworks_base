package com.android.server.apphibernation;

import android.app.Flags;
import android.app.IApplicationThread;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Trace;
import android.util.ArrayMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppHibernationService$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppHibernationService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ AppHibernationService$$ExternalSyntheticLambda3(AppHibernationService appHibernationService, Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = appHibernationService;
        this.f$1 = obj;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String[] strArr;
        switch (this.$r8$classId) {
            case 0:
                AppHibernationService appHibernationService = this.f$0;
                HibernationStateDiskStore hibernationStateDiskStore = (HibernationStateDiskStore) this.f$1;
                int i = this.f$2;
                appHibernationService.getClass();
                List readHibernationStates = hibernationStateDiskStore.readHibernationStates();
                synchronized (appHibernationService.mLock) {
                    try {
                        if (appHibernationService.mUserManager.isUserUnlockingOrUnlocked(i)) {
                            appHibernationService.initializeUserHibernationStates(i, readHibernationStates);
                            for (UserLevelState userLevelState : ((Map) appHibernationService.mUserStates.get(i)).values()) {
                                String str = userLevelState.packageName;
                                if (((GlobalLevelState) ((ArrayMap) appHibernationService.mGlobalHibernationStates).get(str)).hibernated && !userLevelState.hibernated) {
                                    appHibernationService.setHibernatingGlobally(str, false);
                                }
                            }
                        }
                    } finally {
                    }
                }
                return;
            default:
                AppHibernationService appHibernationService2 = this.f$0;
                String str2 = (String) this.f$1;
                int i2 = this.f$2;
                appHibernationService2.getClass();
                Trace.traceBegin(524288L, "unhibernatePackage");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        if (Flags.appRestrictionsApi()) {
                            int i3 = appHibernationService2.mIPackageManager.getApplicationInfo(str2, 537698816L, i2).uid;
                        }
                        strArr = new String[]{"android.permission.RECEIVE_BOOT_COMPLETED"};
                    } catch (RemoteException e) {
                        e = e;
                    } catch (Throwable th) {
                        th = th;
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        Trace.traceEnd(524288L);
                        throw th;
                    }
                    try {
                        appHibernationService2.mIActivityManager.broadcastIntentWithFeature((IApplicationThread) null, (String) null, new Intent("android.intent.action.LOCKED_BOOT_COMPLETED").setPackage(str2), (String) null, (IIntentReceiver) null, -1, (String) null, (Bundle) null, strArr, (String[]) null, (String[]) null, -1, (Bundle) null, false, false, i2);
                        appHibernationService2.mIActivityManager.broadcastIntentWithFeature((IApplicationThread) null, (String) null, new Intent("android.intent.action.BOOT_COMPLETED").setPackage(str2), (String) null, (IIntentReceiver) null, -1, (String) null, (Bundle) null, strArr, (String[]) null, (String[]) null, -1, (Bundle) null, false, false, i2);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        Trace.traceEnd(524288L);
                        return;
                    } catch (RemoteException e2) {
                        e = e2;
                        throw e.rethrowFromSystemServer();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Trace.traceEnd(524288L);
                    throw th;
                }
        }
    }
}
