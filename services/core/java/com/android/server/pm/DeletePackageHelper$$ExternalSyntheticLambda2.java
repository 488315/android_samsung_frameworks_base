package com.android.server.pm;

import android.content.Intent;
import android.content.pm.IPackageDeleteObserver2;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DeletePackageHelper$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ IPackageDeleteObserver2 f$1;

    public /* synthetic */ DeletePackageHelper$$ExternalSyntheticLambda2(
            IPackageDeleteObserver2 iPackageDeleteObserver2, String str, int i) {
        this.$r8$classId = i;
        this.f$1 = iPackageDeleteObserver2;
        this.f$0 = str;
    }

    public /* synthetic */ DeletePackageHelper$$ExternalSyntheticLambda2(
            String str, IPackageDeleteObserver2 iPackageDeleteObserver2, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
        this.f$1 = iPackageDeleteObserver2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                String str = this.f$0;
                IPackageDeleteObserver2 iPackageDeleteObserver2 = this.f$1;
                try {
                    Intent intent = new Intent("android.intent.action.UNINSTALL_PACKAGE");
                    intent.setData(Uri.fromParts("package", str, null));
                    intent.putExtra(
                            "android.content.pm.extra.CALLBACK",
                            iPackageDeleteObserver2.asBinder());
                    iPackageDeleteObserver2.onUserActionRequired(intent);
                    break;
                } catch (RemoteException unused) {
                    return;
                }
            case 1:
                String str2 = this.f$0;
                IPackageDeleteObserver2 iPackageDeleteObserver22 = this.f$1;
                try {
                    Slog.w(
                            "PackageManager",
                            "Not removing package " + str2 + ": has active device admin");
                    iPackageDeleteObserver22.onPackageDeleted(str2, -2, (String) null);
                    break;
                } catch (RemoteException unused2) {
                    return;
                }
            case 2:
                String str3 = this.f$0;
                IPackageDeleteObserver2 iPackageDeleteObserver23 = this.f$1;
                try {
                    Slog.w("PackageManager", "Attempted to delete protected package: " + str3);
                    iPackageDeleteObserver23.onPackageDeleted(str3, -1, (String) null);
                    break;
                } catch (RemoteException unused3) {
                    return;
                }
            case 3:
                try {
                    this.f$1.onPackageDeleted(this.f$0, -3, (String) null);
                    break;
                } catch (RemoteException unused4) {
                    return;
                }
            default:
                try {
                    this.f$1.onPackageDeleted(this.f$0, -4, (String) null);
                    break;
                } catch (RemoteException unused5) {
                    return;
                }
        }
    }
}
