package com.android.server.am;

import android.os.RemoteCallback;
import android.os.RemoteException;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SameProcessApplicationThread$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SameProcessApplicationThread f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SameProcessApplicationThread$$ExternalSyntheticLambda2(SameProcessApplicationThread sameProcessApplicationThread, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = sameProcessApplicationThread;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SameProcessApplicationThread sameProcessApplicationThread = this.f$0;
                RemoteCallback remoteCallback = (RemoteCallback) this.f$1;
                sameProcessApplicationThread.getClass();
                try {
                    sameProcessApplicationThread.mWrapped.schedulePing(remoteCallback);
                    return;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            default:
                SameProcessApplicationThread sameProcessApplicationThread2 = this.f$0;
                List list = (List) this.f$1;
                sameProcessApplicationThread2.getClass();
                try {
                    sameProcessApplicationThread2.mWrapped.scheduleReceiverList(list);
                    return;
                } catch (RemoteException e2) {
                    throw new RuntimeException(e2);
                }
        }
    }
}
