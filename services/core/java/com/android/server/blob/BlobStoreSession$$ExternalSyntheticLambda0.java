package com.android.server.blob;

import android.os.ParcelFileDescriptor;
import android.os.RevocableFileDescriptor;

import java.io.IOException;

public final /* synthetic */ class BlobStoreSession$$ExternalSyntheticLambda0
        implements ParcelFileDescriptor.OnCloseListener {
    public final /* synthetic */ BlobStoreSession f$0;
    public final /* synthetic */ RevocableFileDescriptor f$1;

    public /* synthetic */ BlobStoreSession$$ExternalSyntheticLambda0(
            BlobStoreSession blobStoreSession, RevocableFileDescriptor revocableFileDescriptor) {
        this.f$0 = blobStoreSession;
        this.f$1 = revocableFileDescriptor;
    }

    @Override // android.os.ParcelFileDescriptor.OnCloseListener
    public final void onClose(IOException iOException) {
        BlobStoreSession blobStoreSession = this.f$0;
        RevocableFileDescriptor revocableFileDescriptor = this.f$1;
        synchronized (blobStoreSession.mRevocableFds) {
            blobStoreSession.mRevocableFds.remove(revocableFileDescriptor);
        }
    }
}
