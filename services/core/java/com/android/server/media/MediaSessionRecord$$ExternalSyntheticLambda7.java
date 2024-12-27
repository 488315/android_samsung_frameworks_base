package com.android.server.media;


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
