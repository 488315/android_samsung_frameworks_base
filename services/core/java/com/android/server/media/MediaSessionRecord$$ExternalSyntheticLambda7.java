package com.android.server.media;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaSessionRecord$$ExternalSyntheticLambda7
        implements MediaSessionRecord.ControllerCallbackCall {
    @Override // com.android.server.media.MediaSessionRecord.ControllerCallbackCall
    public final void performOn(
            MediaSessionRecord.ISessionControllerCallbackHolder iSessionControllerCallbackHolder) {
        iSessionControllerCallbackHolder
                .mCallback
                .asBinder()
                .unlinkToDeath(iSessionControllerCallbackHolder.mDeathMonitor, 0);
        iSessionControllerCallbackHolder.mCallback.onSessionDestroyed();
    }
}
