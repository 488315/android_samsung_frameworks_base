package com.android.server.blob;

import android.app.blob.BlobHandle;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.Arrays;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BlobStoreManagerService$$ExternalSyntheticLambda21 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        BlobStoreSession blobStoreSession = (BlobStoreSession) obj;
        synchronized (blobStoreSession.mSessionLock) {
            try {
                byte[] bArr = blobStoreSession.mDataDigest;
                if (bArr == null || !Arrays.equals(bArr, blobStoreSession.mBlobHandle.digest)) {
                    StringBuilder sb = new StringBuilder("Digest of the data (");
                    byte[] bArr2 = blobStoreSession.mDataDigest;
                    sb.append(bArr2 == null ? "null" : BlobHandle.safeDigest(bArr2));
                    sb.append(") didn't match the given BlobHandle.digest (");
                    sb.append(BlobHandle.safeDigest(blobStoreSession.mBlobHandle.digest));
                    sb.append(")");
                    Slog.d("BlobStore", sb.toString());
                    blobStoreSession.mState = 5;
                    FrameworkStatsLog.write(FrameworkStatsLog.BLOB_COMMITTED, blobStoreSession.mOwnerUid, blobStoreSession.mSessionId, blobStoreSession.getSize(), 3);
                    blobStoreSession.sendCommitCallbackResult(1);
                } else {
                    blobStoreSession.mState = 4;
                }
                BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
                blobStoreManagerService.mHandler.post(PooledLambda.obtainRunnable(new BlobStoreManagerService$SessionStateChangeListener$$ExternalSyntheticLambda0(), blobStoreManagerService, blobStoreSession).recycleOnUse());
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
