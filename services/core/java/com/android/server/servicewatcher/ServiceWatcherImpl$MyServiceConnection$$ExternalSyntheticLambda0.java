package com.android.server.servicewatcher;

import com.android.server.servicewatcher.ServiceWatcherImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ServiceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ServiceWatcherImpl.MyServiceConnection f$0;

    public /* synthetic */ ServiceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0(ServiceWatcherImpl.MyServiceConnection myServiceConnection, int i) {
        this.$r8$classId = i;
        this.f$0 = myServiceConnection;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ServiceWatcherImpl.MyServiceConnection myServiceConnection = this.f$0;
        switch (i) {
            case 0:
                myServiceConnection.this$0.onServiceChanged(true);
                break;
            default:
                myServiceConnection.bind();
                break;
        }
    }
}
