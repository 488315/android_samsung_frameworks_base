package com.android.server.backup.fullbackup;

import com.android.server.backup.TransportManager;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.transport.TransportConnection;

public final /* synthetic */ class PerformFullTransportBackupTask$$ExternalSyntheticLambda0
        implements OnTaskFinishedListener {
    public final /* synthetic */ TransportManager f$0;
    public final /* synthetic */ TransportConnection f$1;

    public /* synthetic */ PerformFullTransportBackupTask$$ExternalSyntheticLambda0(
            TransportManager transportManager, TransportConnection transportConnection) {
        this.f$0 = transportManager;
        this.f$1 = transportConnection;
    }

    @Override // com.android.server.backup.internal.OnTaskFinishedListener
    public final void onFinished(String str) {
        this.f$0.disposeOfTransportClient(this.f$1, str);
    }
}
