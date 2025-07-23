package android.app.blob;

import android.app.blob.IBlobCommitCallback;
import android.content.Context;
import android.os.Bundle;
import android.os.LimitExceededException;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class BlobStoreManager {
    public static final int COMMIT_RESULT_ERROR = 1;
    public static final int COMMIT_RESULT_SUCCESS = 0;
    public static final int INVALID_RES_ID = -1;
    public static final int MAX_CERTIFICATE_LENGTH = 32;
    public static final int MAX_PACKAGE_NAME_LENGTH = 223;
    private final Context mContext;
    private final IBlobStoreManager mService;

    public BlobStoreManager(Context context, IBlobStoreManager iBlobStoreManager) {
        this.mContext = context;
        this.mService = iBlobStoreManager;
    }

    public long createSession(BlobHandle blobHandle) throws IOException {
        try {
            return this.mService.createSession(blobHandle, this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            e.maybeRethrow(LimitExceededException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public Session openSession(long j) throws IOException {
        try {
            return new Session(this.mService.openSession(j, this.mContext.getOpPackageName()));
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void abandonSession(long j) throws IOException {
        try {
            this.mService.abandonSession(j, this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor openBlob(BlobHandle blobHandle) throws IOException {
        try {
            return this.mService.openBlob(blobHandle, this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void acquireLease(BlobHandle blobHandle, int i, long j) throws IOException {
        try {
            this.mService.acquireLease(blobHandle, i, null, j, this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            e.maybeRethrow(LimitExceededException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void acquireLease(BlobHandle blobHandle, CharSequence charSequence, long j) throws IOException {
        try {
            this.mService.acquireLease(blobHandle, -1, charSequence, j, this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            e.maybeRethrow(LimitExceededException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void acquireLease(BlobHandle blobHandle, int i) throws IOException {
        acquireLease(blobHandle, i, 0L);
    }

    public void acquireLease(BlobHandle blobHandle, CharSequence charSequence) throws IOException {
        acquireLease(blobHandle, charSequence, 0L);
    }

    public void releaseLease(BlobHandle blobHandle) throws IOException {
        try {
            this.mService.releaseLease(blobHandle, this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void releaseAllLeases() throws Exception {
        try {
            this.mService.releaseAllLeases(this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public long getRemainingLeaseQuotaBytes() {
        try {
            return this.mService.getRemainingLeaseQuotaBytes(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void waitForIdle(long j) throws InterruptedException, TimeoutException {
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            this.mService.waitForIdle(new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.app.blob.BlobStoreManager$$ExternalSyntheticLambda0
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(Bundle bundle) {
                    countDownLatch.countDown();
                }
            }));
            if (countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
            } else {
                throw new TimeoutException("Timed out waiting for service to become idle");
            }
        } catch (ParcelableException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public List<BlobInfo> queryBlobsForUser(UserHandle userHandle) throws IOException {
        try {
            return this.mService.queryBlobsForUser(userHandle.getIdentifier());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void deleteBlob(BlobInfo blobInfo) throws IOException {
        try {
            this.mService.deleteBlob(blobInfo.getId());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public List<BlobHandle> getLeasedBlobs() throws IOException {
        try {
            return this.mService.getLeasedBlobs(this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public LeaseInfo getLeaseInfo(BlobHandle blobHandle) throws IOException {
        try {
            return this.mService.getLeaseInfo(blobHandle, this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public static class Session implements Closeable {
        private final IBlobStoreSession mSession;

        private Session(IBlobStoreSession iBlobStoreSession) {
            this.mSession = iBlobStoreSession;
        }

        public ParcelFileDescriptor openWrite(long j, long j2) throws IOException {
            try {
                ParcelFileDescriptor openWrite = this.mSession.openWrite(j, j2);
                openWrite.seekTo(j);
                return openWrite;
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public ParcelFileDescriptor openRead() throws IOException {
            try {
                return this.mSession.openRead();
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public long getSize() throws IOException {
            try {
                return this.mSession.getSize();
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            try {
                this.mSession.close();
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public void abandon() throws IOException {
            try {
                this.mSession.abandon();
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public void allowPackageAccess(String str, byte[] bArr) throws IOException {
            Objects.requireNonNull(str);
            Preconditions.checkArgument(str.length() <= 223, "packageName is longer than 223 chars");
            Objects.requireNonNull(bArr);
            Preconditions.checkArgument(bArr.length <= 32, "certificate is longer than 32 chars");
            try {
                this.mSession.allowPackageAccess(str, bArr);
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                e.maybeRethrow(LimitExceededException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public boolean isPackageAccessAllowed(String str, byte[] bArr) throws IOException {
            try {
                return this.mSession.isPackageAccessAllowed(str, bArr);
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public void allowSameSignatureAccess() throws IOException {
            try {
                this.mSession.allowSameSignatureAccess();
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public boolean isSameSignatureAccessAllowed() throws IOException {
            try {
                return this.mSession.isSameSignatureAccessAllowed();
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public void allowPublicAccess() throws IOException {
            try {
                this.mSession.allowPublicAccess();
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public boolean isPublicAccessAllowed() throws IOException {
            try {
                return this.mSession.isPublicAccessAllowed();
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public void commit(final Executor executor, final Consumer<Integer> consumer) throws IOException {
            try {
                this.mSession.commit(new IBlobCommitCallback.Stub(this) { // from class: android.app.blob.BlobStoreManager.Session.1
                    @Override // android.app.blob.IBlobCommitCallback
                    public void onResult(int i) {
                        executor.execute(PooledLambda.obtainRunnable(new BiConsumer() { // from class: android.app.blob.BlobStoreManager$Session$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ((Consumer) obj).accept((Integer) obj2);
                            }
                        }, consumer, Integer.valueOf(i)));
                    }
                });
            } catch (ParcelableException e) {
                e.maybeRethrow(IOException.class);
                throw new RuntimeException(e);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
    }
}
