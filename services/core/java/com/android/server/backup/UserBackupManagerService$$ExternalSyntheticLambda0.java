package com.android.server.backup;

import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.transport.TransportConnection;

public final /* synthetic */ class UserBackupManagerService$$ExternalSyntheticLambda0
        implements OnTaskFinishedListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserBackupManagerService f$0;
    public final /* synthetic */ TransportConnection f$1;

    public /* synthetic */ UserBackupManagerService$$ExternalSyntheticLambda0(
            UserBackupManagerService userBackupManagerService,
            TransportConnection transportConnection,
            int i) {
        this.$r8$classId = i;
        this.f$0 = userBackupManagerService;
        this.f$1 = transportConnection;
    }

    @Override // com.android.server.backup.internal.OnTaskFinishedListener
    public final void onFinished(String str) {
        switch (this.$r8$classId) {
            case 0:
                UserBackupManagerService userBackupManagerService = this.f$0;
                userBackupManagerService.mTransportManager.disposeOfTransportClient(this.f$1, str);
                userBackupManagerService.mWakelock.release();
                break;
            case 1:
                this.f$0.mTransportManager.disposeOfTransportClient(this.f$1, str);
                break;
            default:
                this.f$0.mTransportManager.disposeOfTransportClient(this.f$1, str);
                break;
        }
    }
}
