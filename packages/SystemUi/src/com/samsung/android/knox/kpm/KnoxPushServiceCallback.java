package com.samsung.android.knox.kpm;

import android.util.Log;
import com.samsung.android.knox.kpm.IKnoxPushServiceCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class KnoxPushServiceCallback {
    public static final String TAG = "KnoxPushServiceCallback";
    public KnoxPushServiceCallback acb = this;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class PushServiceCallback extends IKnoxPushServiceCallback.Stub {
        public /* synthetic */ PushServiceCallback(KnoxPushServiceCallback knoxPushServiceCallback, int i) {
            this();
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public final void onRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult) {
            Log.d(KnoxPushServiceCallback.TAG, "onRegistrationFinished: ");
            KnoxPushService.getInstance().removeFromTrackMap(KnoxPushServiceCallback.this.acb);
            KnoxPushServiceCallback.this.acb.onRegistrationFinished(knoxPushServiceResult);
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public final void onRegistrationStatus(KnoxPushServiceResult knoxPushServiceResult) {
            Log.d(KnoxPushServiceCallback.TAG, "onRegistrationStatus: ");
            KnoxPushService.getInstance().removeFromTrackMap(KnoxPushServiceCallback.this.acb);
            KnoxPushServiceCallback.this.acb.onRegistrationStatus(knoxPushServiceResult);
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public final void onUnRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult) {
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
