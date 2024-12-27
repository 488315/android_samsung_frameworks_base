package com.android.server.blob;

import android.os.ParcelFileDescriptor;
import android.os.RevocableFileDescriptor;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BlobStoreSession$$ExternalSyntheticLambda0 implements ParcelFileDescriptor.OnCloseListener {
    public final /* synthetic */ BlobStoreSession f$0;
    public final /* synthetic */ RevocableFileDescriptor f$1;

    public /* synthetic */ BlobStoreSession$$ExternalSyntheticLambda0(BlobStoreSession blobStoreSession, RevocableFileDescriptor revocableFileDescriptor) {
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
