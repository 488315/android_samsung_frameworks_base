package com.android.server.backup.fullbackup;

import com.android.server.backup.TransportManager;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.transport.TransportConnection;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
