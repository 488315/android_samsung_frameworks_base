package com.android.server.backup.restore;

import com.android.server.backup.TransportManager;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.transport.TransportConnection;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ActiveRestoreSession$$ExternalSyntheticLambda0 implements OnTaskFinishedListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TransportManager f$0;
    public final /* synthetic */ TransportConnection f$1;
    public final /* synthetic */ UserBackupManagerService.BackupWakeLock f$2;

    public /* synthetic */ ActiveRestoreSession$$ExternalSyntheticLambda0(TransportManager transportManager, TransportConnection transportConnection, UserBackupManagerService.BackupWakeLock backupWakeLock, int i) {
        this.$r8$classId = i;
        this.f$0 = transportManager;
        this.f$1 = transportConnection;
        this.f$2 = backupWakeLock;
    }

    @Override // com.android.server.backup.internal.OnTaskFinishedListener
    public final void onFinished(String str) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.disposeOfTransportClient(this.f$1, str);
                this.f$2.release();
                break;
            default:
                this.f$0.disposeOfTransportClient(this.f$1, str);
                this.f$2.release();
                break;
        }
    }
}
