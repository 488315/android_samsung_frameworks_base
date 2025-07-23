package android.os.incremental;

import android.content.pm.DataLoaderParams;
import android.content.pm.IDataLoaderStatusListener;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.incremental.V4Signature;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes3.dex */
public final class IncrementalStorage {
    private static final int INCFS_MAX_ADD_DATA_SIZE = 128;
    private static final int INCFS_MAX_HASH_SIZE = 32;
    private static final String TAG = "IncrementalStorage";
    private static final int UUID_BYTE_SIZE = 16;
    private final int mId;
    private final IIncrementalService mService;

    public IncrementalStorage(IIncrementalService iIncrementalService, int i) {
        this.mService = iIncrementalService;
        this.mId = i;
    }

    public int getId() {
        return this.mId;
    }

    public void bind(String str) throws IOException {
        bind("", str);
    }

    public void bind(String str, String str2) throws IOException {
        try {
            int makeBindMount = this.mService.makeBindMount(this.mId, str, str2, 0);
            if (makeBindMount >= 0) {
                return;
            }
            throw new IOException("bind() failed with errno " + (-makeBindMount));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void bindPermanent(String str) throws IOException {
        bindPermanent("", str);
    }

    public void bindPermanent(String str, String str2) throws IOException {
        try {
            int makeBindMount = this.mService.makeBindMount(this.mId, str, str2, 1);
            if (makeBindMount >= 0) {
                return;
            }
            throw new IOException("bind() permanent failed with errno " + (-makeBindMount));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unBind(String str) throws IOException {
        try {
            int deleteBindMount = this.mService.deleteBindMount(this.mId, str);
            if (deleteBindMount >= 0) {
                return;
            }
            throw new IOException("unbind() failed with errno " + (-deleteBindMount));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeDirectory(String str) throws IOException {
        try {
            int makeDirectory = this.mService.makeDirectory(this.mId, str);
            if (makeDirectory >= 0) {
                return;
            }
            throw new IOException("makeDirectory() failed with errno " + (-makeDirectory));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeDirectories(String str) throws IOException {
        try {
            int makeDirectories = this.mService.makeDirectories(this.mId, str);
            if (makeDirectories >= 0) {
                return;
            }
            throw new IOException("makeDirectory() failed with errno " + (-makeDirectories));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeFile(String str, long j, int i, UUID uuid, byte[] bArr, byte[] bArr2, byte[] bArr3) throws IOException {
        try {
            if (uuid == null && bArr == null) {
                throw new IOException("File ID and metadata cannot both be null");
            }
            validateV4Signature(bArr2);
            IncrementalNewFileParams incrementalNewFileParams = new IncrementalNewFileParams();
            incrementalNewFileParams.size = j;
            if (bArr == null) {
                bArr = new byte[0];
            }
            incrementalNewFileParams.metadata = bArr;
            incrementalNewFileParams.fileId = idToBytes(uuid);
            incrementalNewFileParams.signature = bArr2;
            int makeFile = this.mService.makeFile(this.mId, str, i, incrementalNewFileParams, bArr3);
            if (makeFile == 0) {
                return;
            }
            throw new IOException("makeFile() failed with errno " + (-makeFile));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeFileFromRange(String str, String str2, long j, long j2) throws IOException {
        try {
            int makeFileFromRange = this.mService.makeFileFromRange(this.mId, str, str2, j, j2);
            if (makeFileFromRange >= 0) {
                return;
            }
            throw new IOException("makeFileFromRange() failed, errno " + (-makeFileFromRange));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeLink(String str, IncrementalStorage incrementalStorage, String str2) throws IOException {
        try {
            int makeLink = this.mService.makeLink(this.mId, str, incrementalStorage.getId(), str2);
            if (makeLink >= 0) {
                return;
            }
            throw new IOException("makeLink() failed with errno " + (-makeLink));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unlink(String str) throws IOException {
        try {
            int unlink = this.mService.unlink(this.mId, str);
            if (unlink >= 0) {
                return;
            }
            throw new IOException("unlink() failed with errno " + (-unlink));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void moveFile(String str, String str2) throws IOException {
        int makeLink;
        try {
            IIncrementalService iIncrementalService = this.mService;
            int i = this.mId;
            makeLink = iIncrementalService.makeLink(i, str, i, str2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (makeLink < 0) {
            throw new IOException("moveFile() failed at makeLink(), errno " + (-makeLink));
        }
        try {
            this.mService.unlink(this.mId, str);
        } catch (RemoteException unused) {
        }
    }

    public void moveDir(String str, String str2) throws IOException {
        int makeBindMount;
        if (!new File(str2).exists()) {
            throw new IOException("moveDir() requires that destination dir already exists.");
        }
        try {
            makeBindMount = this.mService.makeBindMount(this.mId, str, str2, 1);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (makeBindMount < 0) {
            throw new IOException("moveDir() failed at making bind mount, errno " + (-makeBindMount));
        }
        try {
            this.mService.deleteBindMount(this.mId, str);
        } catch (RemoteException unused) {
        }
    }

    public boolean isFileFullyLoaded(String str) throws IOException {
        try {
            int isFileFullyLoaded = this.mService.isFileFullyLoaded(this.mId, str);
            if (isFileFullyLoaded >= 0) {
                return isFileFullyLoaded == 0;
            }
            throw new IOException("isFileFullyLoaded() failed, errno " + (-isFileFullyLoaded));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isFullyLoaded() throws IOException {
        try {
            int isFullyLoaded = this.mService.isFullyLoaded(this.mId);
            if (isFullyLoaded >= 0) {
                return isFullyLoaded == 0;
            }
            throw new IOException("isFullyLoaded() failed at querying loading progress, errno " + (-isFullyLoaded));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public float getLoadingProgress() throws IOException {
        try {
            float loadingProgress = this.mService.getLoadingProgress(this.mId);
            if (loadingProgress >= 0.0f) {
                return loadingProgress;
            }
            throw new IOException("getLoadingProgress() failed at querying loading progress, errno " + (-loadingProgress));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return 0.0f;
        }
    }

    public byte[] getFileMetadata(String str) {
        try {
            return this.mService.getMetadataByPath(this.mId, str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public byte[] getFileMetadata(UUID uuid) {
        try {
            return this.mService.getMetadataById(this.mId, idToBytes(uuid));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public boolean startLoading(DataLoaderParams dataLoaderParams, IDataLoaderStatusListener iDataLoaderStatusListener, StorageHealthCheckParams storageHealthCheckParams, IStorageHealthListener iStorageHealthListener, PerUidReadTimeouts[] perUidReadTimeoutsArr) {
        Objects.requireNonNull(perUidReadTimeoutsArr);
        try {
            return this.mService.startLoading(this.mId, dataLoaderParams.getData(), iDataLoaderStatusListener, storageHealthCheckParams, iStorageHealthListener, perUidReadTimeoutsArr);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void onInstallationComplete() {
        try {
            this.mService.onInstallationComplete(this.mId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static byte[] idToBytes(UUID uuid) {
        if (uuid == null) {
            return new byte[0];
        }
        ByteBuffer wrap = ByteBuffer.wrap(new byte[16]);
        wrap.putLong(uuid.getMostSignificantBits());
        wrap.putLong(uuid.getLeastSignificantBits());
        return wrap.array();
    }

    public static UUID bytesToId(byte[] bArr) throws IllegalArgumentException {
        if (bArr.length != 16) {
            throw new IllegalArgumentException("Expected array of size 16, got " + bArr.length);
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    public void disallowReadLogs() {
        try {
            this.mService.disallowReadLogs(this.mId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private static void validateV4Signature(byte[] bArr) throws IOException {
        if (bArr == null || bArr.length == 0) {
            return;
        }
        try {
            V4Signature readFrom = V4Signature.readFrom(bArr);
            if (!readFrom.isVersionSupported()) {
                throw new IOException("v4 signature version " + readFrom.version + " is not supported");
            }
            V4Signature.HashingInfo fromByteArray = V4Signature.HashingInfo.fromByteArray(readFrom.hashingInfo);
            V4Signature.SigningInfos fromByteArray2 = V4Signature.SigningInfos.fromByteArray(readFrom.signingInfos);
            if (fromByteArray.hashAlgorithm != 1) {
                throw new IOException("Unsupported hashAlgorithm: " + fromByteArray.hashAlgorithm);
            }
            if (fromByteArray.log2BlockSize != 12) {
                throw new IOException("Unsupported log2BlockSize: " + ((int) fromByteArray.log2BlockSize));
            }
            if (fromByteArray.salt != null && fromByteArray.salt.length > 0) {
                throw new IOException("Unsupported salt: " + Arrays.toString(fromByteArray.salt));
            }
            if (fromByteArray.rawRootHash.length != 32) {
                throw new IOException("rawRootHash has to be 32 bytes");
            }
            if (fromByteArray2.signingInfo.additionalData.length > 128) {
                throw new IOException("additionalData has to be at most 128 bytes");
            }
        } catch (IOException e) {
            throw new IOException("Failed to read v4 signature:", e);
        }
    }

    public boolean configureNativeBinaries(String str, String str2, String str3, boolean z) {
        try {
            return this.mService.configureNativeBinaries(this.mId, str, str2, str3, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean waitForNativeBinariesExtraction() {
        try {
            return this.mService.waitForNativeBinariesExtraction(this.mId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean registerLoadingProgressListener(IStorageLoadingProgressListener iStorageLoadingProgressListener) {
        try {
            return this.mService.registerLoadingProgressListener(this.mId, iStorageLoadingProgressListener);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean unregisterLoadingProgressListener() {
        try {
            return this.mService.unregisterLoadingProgressListener(this.mId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public PersistableBundle getMetrics() {
        try {
            return this.mService.getMetrics(this.mId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }
}
