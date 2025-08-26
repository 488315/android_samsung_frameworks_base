package com.samsung.android.knox.kpm;

import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.knox.kpm.IKnoxPushServiceCallback;

/* loaded from: classes4.dex */
public abstract class KnoxPushServiceCallback {
    public static final String TAG = "KnoxPushServiceCallback";
    public KnoxPushServiceCallback acb = this;

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
