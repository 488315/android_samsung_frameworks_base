package com.samsung.android.knox.kpm;

import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.knox.kpm.IKnoxPushServiceCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class KnoxPushServiceCallback {
    public static final String TAG = "KnoxPushServiceCallback";
    public KnoxPushServiceCallback acb = this;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class PushServiceCallback extends IKnoxPushServiceCallback.Stub {
        public /* synthetic */ PushServiceCallback(KnoxPushServiceCallback knoxPushServiceCallback, int i) {
            this();
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public void onRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException {
            Log.d(KnoxPushServiceCallback.TAG, "onRegistrationFinished: ");
            KnoxPushService.getInstance().removeFromTrackMap(KnoxPushServiceCallback.this.acb);
            KnoxPushServiceCallback.this.acb.onRegistrationFinished(knoxPushServiceResult);
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public void onRegistrationStatus(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException {
            Log.d(KnoxPushServiceCallback.TAG, "onRegistrationStatus: ");
            KnoxPushService.getInstance().removeFromTrackMap(KnoxPushServiceCallback.this.acb);
            KnoxPushServiceCallback.this.acb.onRegistrationStatus(knoxPushServiceResult);
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public void onUnRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult) throws RemoteException {
            Log.d(KnoxPushServiceCallback.TAG, "onUnRegistrationFinished: ");
            KnoxPushService.getInstance().removeFromTrackMap(KnoxPushServiceCallback.this.acb);
            KnoxPushServiceCallback.this.acb.onUnRegistrationFinished(knoxPushServiceResult);
        }

        private PushServiceCallback() {
        }
    }

    public final IKnoxPushServiceCallback getKnoxPushServiceCb() {
        return new PushServiceCallback(this, 0);
    }

    public abstract void onRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult);

    public abstract void onRegistrationStatus(KnoxPushServiceResult knoxPushServiceResult);

    public abstract void onUnRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult);
}
