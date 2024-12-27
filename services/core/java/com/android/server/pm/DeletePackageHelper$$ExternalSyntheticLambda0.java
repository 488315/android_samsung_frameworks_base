package com.android.server.pm;

import android.content.pm.IPackageDeleteObserver2;
import android.os.RemoteException;
import android.util.Slog;

import com.samsung.android.server.pm.PmLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DeletePackageHelper$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DeletePackageHelper$$ExternalSyntheticLambda0(
            DeletePackageHelper deletePackageHelper, String str, int i) {
        this.$r8$classId = 2;
        this.f$2 = deletePackageHelper;
        this.f$0 = str;
        this.f$1 = i;
    }

    public /* synthetic */ DeletePackageHelper$$ExternalSyntheticLambda0(
            String str, int i, IPackageDeleteObserver2 iPackageDeleteObserver2, int i2) {
        this.$r8$classId = i2;
        this.f$0 = str;
        this.f$1 = i;
        this.f$2 = iPackageDeleteObserver2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                String str = this.f$0;
                int i = this.f$1;
                IPackageDeleteObserver2 iPackageDeleteObserver2 =
                        (IPackageDeleteObserver2) this.f$2;
                try {
                    Slog.w(
                            "PackageManager",
                            "Attempted to delete "
                                    + str
                                    + ", callingUid: "
                                    + i
                                    + ", observer.hashCode(): "
                                    + iPackageDeleteObserver2.hashCode());
                    iPackageDeleteObserver2.onPackageDeleted(str, -1, (String) null);
                    break;
                } catch (RemoteException unused) {
                    return;
                }
            case 1:
                String str2 = this.f$0;
                int i2 = this.f$1;
                IPackageDeleteObserver2 iPackageDeleteObserver22 =
                        (IPackageDeleteObserver2) this.f$2;
                try {
                    PmLog.logDebugInfoAndLogcat(
                            "Attempted to delete system required package: "
                                    + str2
                                    + " callingUid: "
                                    + i2);
                    iPackageDeleteObserver22.onPackageDeleted(str2, -1, (String) null);
                    break;
                } catch (RemoteException unused2) {
                    return;
                }
            default:
                ((DeletePackageHelper) this.f$2).deletePackageX(this.f$1, 0, -1L, this.f$0, true);
                break;
        }
    }
}
