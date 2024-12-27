package com.android.server.blob;

import android.os.FileUtils;
import android.os.Handler;
import android.os.RemoteCallback;
import android.os.Trace;
import android.util.Slog;

import com.android.internal.util.function.pooled.PooledLambda;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BlobStoreManagerService$$ExternalSyntheticLambda17
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BlobStoreManagerService$$ExternalSyntheticLambda17(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BlobStoreManagerService blobStoreManagerService =
                        (BlobStoreManagerService) this.f$0;
                BlobStoreSession blobStoreSession = (BlobStoreSession) this.f$1;
                blobStoreManagerService.getClass();
                blobStoreSession.getClass();
                try {
                    try {
                        Trace.traceBegin(
                                524288L,
                                "computeBlobDigest-i"
                                        + blobStoreSession.mSessionId
                                        + "-l"
                                        + blobStoreSession.getSessionFile().length());
                        blobStoreSession.mDataDigest =
                                FileUtils.digest(
                                        blobStoreSession.getSessionFile(),
                                        blobStoreSession.mBlobHandle.algorithm);
                    } catch (IOException | NoSuchAlgorithmException e) {
                        Slog.e("BlobStore", "Error computing the digest", e);
                    }
                    blobStoreManagerService.mHandler.post(
                            PooledLambda.obtainRunnable(
                                            new BlobStoreManagerService$$ExternalSyntheticLambda21(),
                                            blobStoreSession)
                                    .recycleOnUse());
                    return;
                } finally {
                    Trace.traceEnd(524288L);
                }
            case 1:
                BlobStoreManagerService.Stub stub = (BlobStoreManagerService.Stub) this.f$0;
                BlobStoreManagerService.this.mBackgroundHandler.post(
                        new BlobStoreManagerService$$ExternalSyntheticLambda17(
                                2, stub, (RemoteCallback) this.f$1));
                return;
            default:
                BlobStoreManagerService.Stub stub2 = (BlobStoreManagerService.Stub) this.f$0;
                RemoteCallback remoteCallback = (RemoteCallback) this.f$1;
                Handler handler = BlobStoreManagerService.this.mHandler;
                Objects.requireNonNull(remoteCallback);
                handler.post(
                        PooledLambda.obtainRunnable(
                                        new BlobStoreManagerService$Stub$$ExternalSyntheticLambda2(
                                                0, remoteCallback),
                                        (Object) null)
                                .recycleOnUse());
                return;
        }
    }
}
