package com.android.server.backup.restore;

import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IRestoreObserver;
import android.app.backup.RestoreSet;
import android.content.pm.PackageInfo;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.params.RestoreParams;
import com.android.server.backup.transport.TransportConnection;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ActiveRestoreSession$$ExternalSyntheticLambda2 implements BiFunction {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ ActiveRestoreSession f$0;
    public final /* synthetic */ IRestoreObserver f$1;
    public final /* synthetic */ IBackupManagerMonitor f$2;
    public final /* synthetic */ long f$3;
    public final /* synthetic */ Object f$4;

    public /* synthetic */ ActiveRestoreSession$$ExternalSyntheticLambda2(ActiveRestoreSession activeRestoreSession, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor, long j, RestoreSet restoreSet) {
        this.f$0 = activeRestoreSession;
        this.f$1 = iRestoreObserver;
        this.f$2 = iBackupManagerMonitor;
        this.f$3 = j;
        this.f$4 = restoreSet;
    }

    public /* synthetic */ ActiveRestoreSession$$ExternalSyntheticLambda2(ActiveRestoreSession activeRestoreSession, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor, long j, PackageInfo packageInfo) {
        this.f$0 = activeRestoreSession;
        this.f$1 = iRestoreObserver;
        this.f$2 = iBackupManagerMonitor;
        this.f$3 = j;
        this.f$4 = packageInfo;
    }

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return new RestoreParams((TransportConnection) obj, this.f$1, this.f$2, this.f$3, null, 0, true, null, (OnTaskFinishedListener) obj2, this.f$0.getBackupEligibilityRules((RestoreSet) this.f$4));
            default:
                return new RestoreParams((TransportConnection) obj, this.f$1, this.f$2, this.f$3, (PackageInfo) this.f$4, 0, false, null, (OnTaskFinishedListener) obj2, this.f$0.mBackupEligibilityRules);
        }
    }
}
