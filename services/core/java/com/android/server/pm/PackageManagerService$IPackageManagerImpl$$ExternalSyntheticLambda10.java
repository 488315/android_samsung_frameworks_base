package com.android.server.pm;

import android.app.BroadcastOptions;
import android.content.IntentSender;
import android.content.pm.IPackageDataObserver;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;

import java.io.IOException;

public final /* synthetic */
class PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda10 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PackageManagerService.IPackageManagerImpl f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ Object f$4;

    public /* synthetic */ PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda10(
            PackageManagerService.IPackageManagerImpl iPackageManagerImpl,
            String str,
            long j,
            int i,
            Object obj,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = iPackageManagerImpl;
        this.f$1 = str;
        this.f$2 = j;
        this.f$3 = i;
        this.f$4 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        int i;
        switch (this.$r8$classId) {
            case 0:
                PackageManagerService.IPackageManagerImpl iPackageManagerImpl = this.f$0;
                String str = this.f$1;
                long j = this.f$2;
                int i2 = this.f$3;
                IPackageDataObserver iPackageDataObserver = (IPackageDataObserver) this.f$4;
                iPackageManagerImpl.getClass();
                try {
                    iPackageManagerImpl.this$0.freeStorage(i2, str, j);
                    z = true;
                } catch (IOException e) {
                    Slog.w("PackageManager", e);
                    z = false;
                }
                if (iPackageDataObserver != null) {
                    try {
                        Log.d(
                                "PackageManager",
                                "result of freeStorageAndNotify: "
                                        + z
                                        + "{"
                                        + iPackageDataObserver.hashCode()
                                        + "}");
                        iPackageDataObserver.onRemoveCompleted((String) null, z);
                        break;
                    } catch (RemoteException e2) {
                        Slog.w("PackageManager", e2);
                        return;
                    }
                }
                break;
            default:
                PackageManagerService.IPackageManagerImpl iPackageManagerImpl2 = this.f$0;
                String str2 = this.f$1;
                long j2 = this.f$2;
                int i3 = this.f$3;
                IntentSender intentSender = (IntentSender) this.f$4;
                iPackageManagerImpl2.getClass();
                try {
                    iPackageManagerImpl2.this$0.freeStorage(i3, str2, j2);
                    i = 1;
                } catch (IOException e3) {
                    Slog.w("PackageManager", e3);
                    i = 0;
                }
                if (intentSender != null) {
                    try {
                        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                        makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
                        intentSender.sendIntent(
                                null, i, null, null, null, null, makeBasic.toBundle());
                        break;
                    } catch (IntentSender.SendIntentException e4) {
                        Slog.w("PackageManager", e4);
                    }
                }
                break;
        }
    }
}
