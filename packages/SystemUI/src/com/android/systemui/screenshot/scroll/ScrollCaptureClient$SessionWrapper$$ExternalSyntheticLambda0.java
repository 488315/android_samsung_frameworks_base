package com.android.systemui.screenshot.scroll;

import android.os.RemoteException;
import com.android.systemui.screenshot.scroll.ScrollCaptureClient;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScrollCaptureClient.SessionWrapper f$0;

    public /* synthetic */ ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0(ScrollCaptureClient.SessionWrapper sessionWrapper, int i) {
        this.$r8$classId = i;
        this.f$0 = sessionWrapper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ScrollCaptureClient.SessionWrapper sessionWrapper = this.f$0;
        switch (i) {
            case 0:
                int i2 = ScrollCaptureClient.SessionWrapper.$r8$clinit;
                sessionWrapper.getClass();
                try {
                    sessionWrapper.mCancellationSignal.cancel();
                    break;
                } catch (RemoteException unused) {
                    return;
                }
            default:
                int i3 = ScrollCaptureClient.SessionWrapper.$r8$clinit;
                sessionWrapper.getClass();
                try {
                    sessionWrapper.mCancellationSignal.cancel();
                    break;
                } catch (RemoteException unused2) {
                    return;
                }
        }
    }
}
