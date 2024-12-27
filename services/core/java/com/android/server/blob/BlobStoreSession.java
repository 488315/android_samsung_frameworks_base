package com.android.server.blob;

import android.app.blob.BlobHandle;
import android.app.blob.IBlobCommitCallback;
import android.app.blob.IBlobStoreSession;
import android.content.Context;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.LimitExceededException;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.RevocableFileDescriptor;
import android.os.storage.StorageManager;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.ExceptionUtils;
import android.util.Slog;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.blob.BlobAccessMode;
import com.android.server.blob.BlobStoreConfig;
import com.android.server.blob.BlobStoreManagerService;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
class BlobStoreSession extends IBlobStoreSession.Stub {
    public IBlobCommitCallback mBlobCommitCallback;
    public final BlobHandle mBlobHandle;
    public final Context mContext;
    public final long mCreationTimeMs;
    public byte[] mDataDigest;
    public final BlobStoreManagerService.SessionStateChangeListener mListener;
    public final String mOwnerPackageName;
    public final int mOwnerUid;
    public File mSessionFile;
    public final long mSessionId;
    public final Object mSessionLock = new Object();
    public final ArrayList mRevocableFds = new ArrayList();
    public int mState = 0;
    public final BlobAccessMode mBlobAccessMode = new BlobAccessMode();

    public BlobStoreSession(Context context, long j, BlobHandle blobHandle, int i, String str, long j2, BlobStoreManagerService.SessionStateChangeListener sessionStateChangeListener) {
        this.mContext = context;
        this.mBlobHandle = blobHandle;
        this.mSessionId = j;
        this.mOwnerUid = i;
        this.mOwnerPackageName = str;
        this.mCreationTimeMs = j2;
        this.mListener = sessionStateChangeListener;
    }

    public static BlobStoreSession createFromXml(XmlPullParser xmlPullParser, int i, Context context, BlobStoreManagerService.SessionStateChangeListener sessionStateChangeListener) {
        long readLongAttribute = XmlUtils.readLongAttribute(xmlPullParser, "id");
        String readStringAttribute = XmlUtils.readStringAttribute(xmlPullParser, KnoxAnalyticsDataConverter.PAYLOAD);
        int readIntAttribute = XmlUtils.readIntAttribute(xmlPullParser, "u");
        long readLongAttribute2 = i >= 5 ? XmlUtils.readLongAttribute(xmlPullParser, "crt") : System.currentTimeMillis();
        int depth = xmlPullParser.getDepth();
        BlobHandle blobHandle = null;
        BlobAccessMode blobAccessMode = null;
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("bh".equals(xmlPullParser.getName())) {
                blobHandle = BlobHandle.createFromXml(xmlPullParser);
            } else if ("am".equals(xmlPullParser.getName())) {
                blobAccessMode = BlobAccessMode.createFromXml(xmlPullParser);
            }
        }
        if (blobHandle == null) {
            Slog.wtf("BlobStore", "blobHandle should be available");
            return null;
        }
        if (blobAccessMode == null) {
            Slog.wtf("BlobStore", "blobAccessMode should be available");
            return null;
        }
        BlobStoreSession blobStoreSession = new BlobStoreSession(context, readLongAttribute, blobHandle, readIntAttribute, readStringAttribute, readLongAttribute2, sessionStateChangeListener);
        BlobAccessMode blobAccessMode2 = blobStoreSession.mBlobAccessMode;
        blobAccessMode2.getClass();
        if ((blobAccessMode.mAccessType & 8) != 0) {
            blobAccessMode2.mAllowedPackages.addAll(blobAccessMode.mAllowedPackages);
        }
        blobAccessMode2.mAccessType |= blobAccessMode.mAccessType;
        return blobStoreSession;
    }

    public static String stateToString(int i) {
        if (i == 0) {
            return "<closed>";
        }
        if (i == 1) {
            return "<opened>";
        }
        if (i == 2) {
            return "<abandoned>";
        }
        if (i == 3) {
            return "<committed>";
        }
        if (i == 4) {
            return "<verified_valid>";
        }
        if (i == 5) {
            return "<verified_invalid>";
        }
        Slog.wtf("BlobStore", "Unknown state: " + i);
        return "<unknown>";
    }

    public final void abandon() {
        closeSession(2, true);
    }

    public final void allowPackageAccess(String str, byte[] bArr) {
        assertCallerIsOwner();
        Objects.requireNonNull(str, "packageName must not be null");
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new IllegalStateException("Not allowed to change access type in state: ".concat(stateToString(this.mState)));
                }
                int size = this.mBlobAccessMode.mAllowedPackages.size();
                boolean z = BlobStoreConfig.LOGV;
                if (size >= BlobStoreConfig.DeviceConfigProperties.MAX_BLOB_ACCESS_PERMITTED_PACKAGES) {
                    throw new ParcelableException(new LimitExceededException("Too many packages permitted to access the blob: " + this.mBlobAccessMode.mAllowedPackages.size()));
                }
                BlobAccessMode blobAccessMode = this.mBlobAccessMode;
                blobAccessMode.mAccessType |= 8;
                blobAccessMode.mAllowedPackages.add(new BlobAccessMode.PackageIdentifier(str, bArr));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void allowPublicAccess() {
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new IllegalStateException("Not allowed to change access type in state: ".concat(stateToString(this.mState)));
                }
                this.mBlobAccessMode.mAccessType |= 2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void allowSameSignatureAccess() {
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new IllegalStateException("Not allowed to change access type in state: ".concat(stateToString(this.mState)));
                }
                this.mBlobAccessMode.mAccessType |= 4;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void assertCallerIsOwner() {
        if (Binder.getCallingUid() == this.mOwnerUid) {
            return;
        }
        throw new SecurityException(AmFmBandRange$$ExternalSyntheticOutline0.m(this.mOwnerUid, new StringBuilder(), " is not the session owner"));
    }

    public final void close() {
        closeSession(0, false);
    }

    public final void closeSession(int i, boolean z) {
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    if (i != 0) {
                        throw new IllegalStateException("Not allowed to delete or abandon a session with state: ".concat(stateToString(this.mState)));
                    }
                    return;
                }
                this.mState = i;
                revokeAllFds();
                if (z) {
                    BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
                    blobStoreManagerService.mHandler.post(PooledLambda.obtainRunnable(new BlobStoreManagerService$SessionStateChangeListener$$ExternalSyntheticLambda0(), blobStoreManagerService, this).recycleOnUse());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void commit(IBlobCommitCallback iBlobCommitCallback) {
        synchronized (this.mSessionLock) {
            this.mBlobCommitCallback = iBlobCommitCallback;
            closeSession(3, true);
        }
    }

    public final BlobAccessMode getBlobAccessMode() {
        BlobAccessMode blobAccessMode;
        synchronized (this.mSessionLock) {
            blobAccessMode = this.mBlobAccessMode;
        }
        return blobAccessMode;
    }

    public final File getSessionFile() {
        if (this.mSessionFile == null) {
            long j = this.mSessionId;
            File file = new File(BlobStoreConfig.prepareBlobStoreRootDir(), "blobs");
            if (!file.exists() && !file.mkdir()) {
                Slog.e("BlobStore", "Failed to mkdir(): " + file);
                file = null;
            }
            this.mSessionFile = file != null ? new File(file, String.valueOf(j)) : null;
        }
        return this.mSessionFile;
    }

    public final long getSize() {
        return getSessionFile().length();
    }

    public final boolean isPackageAccessAllowed(String str, byte[] bArr) {
        boolean contains;
        assertCallerIsOwner();
        Objects.requireNonNull(str, "packageName must not be null");
        Preconditions.checkByteArrayNotEmpty(bArr, "certificate");
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new IllegalStateException("Not allowed to get access type in state: ".concat(stateToString(this.mState)));
                }
                BlobAccessMode blobAccessMode = this.mBlobAccessMode;
                contains = (blobAccessMode.mAccessType & 8) == 0 ? false : blobAccessMode.mAllowedPackages.contains(new BlobAccessMode.PackageIdentifier(str, bArr));
            } finally {
            }
        }
        return contains;
    }

    public final boolean isPublicAccessAllowed() {
        boolean z;
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                z = true;
                if (this.mState != 1) {
                    throw new IllegalStateException("Not allowed to get access type in state: ".concat(stateToString(this.mState)));
                }
                if ((this.mBlobAccessMode.mAccessType & 2) == 0) {
                    z = false;
                }
            } finally {
            }
        }
        return z;
    }

    public final boolean isSameSignatureAccessAllowed() {
        boolean z;
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                z = true;
                if (this.mState != 1) {
                    throw new IllegalStateException("Not allowed to get access type in state: ".concat(stateToString(this.mState)));
                }
                if ((this.mBlobAccessMode.mAccessType & 4) == 0) {
                    z = false;
                }
            } finally {
            }
        }
        return z;
    }

    public final void open() {
        boolean z;
        synchronized (this.mSessionLock) {
            try {
                synchronized (this.mSessionLock) {
                    int i = this.mState;
                    if (i != 3 && i != 2) {
                        z = false;
                    }
                    z = true;
                }
                if (z) {
                    throw new IllegalStateException("Not allowed to open session with state: ".concat(stateToString(this.mState)));
                }
                this.mState = 1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ParcelFileDescriptor openRead() {
        FileDescriptor fileDescriptor;
        ParcelFileDescriptor revocableFileDescriptor;
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new IllegalStateException("Not allowed to read in state: ".concat(stateToString(this.mState)));
                }
                boolean z = BlobStoreConfig.LOGV;
                if (!BlobStoreConfig.DeviceConfigProperties.USE_REVOCABLE_FD_FOR_READS) {
                    try {
                        return new ParcelFileDescriptor(openReadInternal());
                    } catch (IOException e) {
                        throw ExceptionUtils.wrap(e);
                    }
                }
                try {
                    fileDescriptor = openReadInternal();
                    try {
                        RevocableFileDescriptor revocableFileDescriptor2 = new RevocableFileDescriptor(this.mContext, fileDescriptor);
                        synchronized (this.mSessionLock) {
                            if (this.mState != 1) {
                                IoUtils.closeQuietly(fileDescriptor);
                                throw new IllegalStateException("Not allowed to read in state: ".concat(stateToString(this.mState)));
                            }
                            synchronized (this.mRevocableFds) {
                                this.mRevocableFds.add(revocableFileDescriptor2);
                            }
                            revocableFileDescriptor2.addOnCloseListener(new BlobStoreSession$$ExternalSyntheticLambda0(this, revocableFileDescriptor2));
                            revocableFileDescriptor = revocableFileDescriptor2.getRevocableFileDescriptor();
                        }
                        return revocableFileDescriptor;
                    } catch (IOException e2) {
                        e = e2;
                        IoUtils.closeQuietly(fileDescriptor);
                        throw ExceptionUtils.wrap(e);
                    }
                } catch (IOException e3) {
                    e = e3;
                    fileDescriptor = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final FileDescriptor openReadInternal() {
        try {
            File sessionFile = getSessionFile();
            if (sessionFile != null) {
                return Os.open(sessionFile.getPath(), OsConstants.O_RDONLY, 0);
            }
            throw new IllegalStateException("Couldn't get the file for this session");
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public final ParcelFileDescriptor openWrite(long j, long j2) {
        FileDescriptor fileDescriptor;
        ParcelFileDescriptor revocableFileDescriptor;
        Preconditions.checkArgumentNonnegative(j, "offsetBytes must not be negative");
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            if (this.mState != 1) {
                throw new IllegalStateException("Not allowed to write in state: ".concat(stateToString(this.mState)));
            }
        }
        try {
            fileDescriptor = openWriteInternal(j, j2);
            try {
                RevocableFileDescriptor revocableFileDescriptor2 = new RevocableFileDescriptor(this.mContext, fileDescriptor, BlobStoreUtils.getRevocableFdHandler());
                synchronized (this.mSessionLock) {
                    if (this.mState != 1) {
                        IoUtils.closeQuietly(fileDescriptor);
                        throw new IllegalStateException("Not allowed to write in state: ".concat(stateToString(this.mState)));
                    }
                    synchronized (this.mRevocableFds) {
                        this.mRevocableFds.add(revocableFileDescriptor2);
                    }
                    revocableFileDescriptor2.addOnCloseListener(new BlobStoreSession$$ExternalSyntheticLambda0(this, revocableFileDescriptor2));
                    revocableFileDescriptor = revocableFileDescriptor2.getRevocableFileDescriptor();
                }
                return revocableFileDescriptor;
            } catch (IOException e) {
                e = e;
                IoUtils.closeQuietly(fileDescriptor);
                throw ExceptionUtils.wrap(e);
            }
        } catch (IOException e2) {
            e = e2;
            fileDescriptor = null;
        }
    }

    public final FileDescriptor openWriteInternal(long j, long j2) {
        try {
            File sessionFile = getSessionFile();
            if (sessionFile == null) {
                throw new IllegalStateException("Couldn't get the file for this session");
            }
            FileDescriptor open = Os.open(sessionFile.getPath(), OsConstants.O_CREAT | OsConstants.O_RDWR, FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
            if (j > 0 && Os.lseek(open, j, OsConstants.SEEK_SET) != j) {
                throw new IllegalStateException("Failed to seek " + j + "; curOffset=" + j);
            }
            if (j2 > 0) {
                ((StorageManager) this.mContext.getSystemService(StorageManager.class)).allocateBytes(open, j2);
            }
            return open;
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public final void revokeAllFds() {
        synchronized (this.mRevocableFds) {
            try {
                for (int size = this.mRevocableFds.size() - 1; size >= 0; size--) {
                    ((RevocableFileDescriptor) this.mRevocableFds.get(size)).revoke();
                }
                this.mRevocableFds.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendCommitCallbackResult(int i) {
        synchronized (this.mSessionLock) {
            try {
                this.mBlobCommitCallback.onResult(i);
            } catch (RemoteException e) {
                Slog.d("BlobStore", "Error sending the callback result", e);
            }
            this.mBlobCommitCallback = null;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BlobStoreSession {id:");
        sb.append(this.mSessionId);
        sb.append(",handle:");
        sb.append(this.mBlobHandle);
        sb.append(",uid:");
        sb.append(this.mOwnerUid);
        sb.append(",pkg:");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.mOwnerPackageName, "}");
    }

    public final void writeToXml(XmlSerializer xmlSerializer) {
        synchronized (this.mSessionLock) {
            XmlUtils.writeLongAttribute(xmlSerializer, "id", this.mSessionId);
            XmlUtils.writeStringAttribute(xmlSerializer, KnoxAnalyticsDataConverter.PAYLOAD, this.mOwnerPackageName);
            XmlUtils.writeIntAttribute(xmlSerializer, "u", this.mOwnerUid);
            XmlUtils.writeLongAttribute(xmlSerializer, "crt", this.mCreationTimeMs);
            FastXmlSerializer fastXmlSerializer = (FastXmlSerializer) xmlSerializer;
            fastXmlSerializer.startTag((String) null, "bh");
            this.mBlobHandle.writeToXml(xmlSerializer);
            fastXmlSerializer.endTag((String) null, "bh");
            fastXmlSerializer.startTag((String) null, "am");
            this.mBlobAccessMode.writeToXml(xmlSerializer);
            fastXmlSerializer.endTag((String) null, "am");
        }
    }
}
