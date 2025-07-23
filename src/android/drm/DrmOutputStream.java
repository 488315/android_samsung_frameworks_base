package android.drm;

import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import com.android.internal.util.ArrayUtils;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownServiceException;
import libcore.io.IoBridge;
import libcore.io.Streams;

@Deprecated
/* loaded from: classes.dex */
public class DrmOutputStream extends OutputStream {
    private static final String TAG = "DrmOutputStream";
    private final DrmManagerClient mClient;
    private final FileDescriptor mFd;
    private final ParcelFileDescriptor mPfd;
    private int mSessionId;

    public DrmOutputStream(DrmManagerClient drmManagerClient, ParcelFileDescriptor parcelFileDescriptor, String str) throws IOException {
        this.mSessionId = -1;
        this.mClient = drmManagerClient;
        this.mPfd = parcelFileDescriptor;
        this.mFd = parcelFileDescriptor.getFileDescriptor();
        int openConvertSession = drmManagerClient.openConvertSession(str);
        this.mSessionId = openConvertSession;
        if (openConvertSession != -1) {
            return;
        }
        throw new UnknownServiceException("Failed to open DRM session for " + str);
    }

    public void finish() throws IOException {
        DrmConvertedStatus closeConvertSession = this.mClient.closeConvertSession(this.mSessionId);
        if (closeConvertSession.statusCode == 1) {
            try {
                Os.lseek(this.mFd, closeConvertSession.offset, OsConstants.SEEK_SET);
            } catch (ErrnoException e) {
                e.rethrowAsIOException();
            }
            IoBridge.write(this.mFd, closeConvertSession.convertedData, 0, closeConvertSession.convertedData.length);
            this.mSessionId = -1;
            return;
        }
        throw new IOException("Unexpected DRM status: " + closeConvertSession.statusCode);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.mSessionId == -1) {
            Log.w(TAG, "Closing stream without finishing");
        }
        this.mPfd.close();
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        ArrayUtils.throwsIfOutOfBounds(bArr.length, i, i2);
        if (i2 != bArr.length) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            bArr = bArr2;
        }
        DrmConvertedStatus convertData = this.mClient.convertData(this.mSessionId, bArr);
        if (convertData.statusCode == 1) {
            IoBridge.write(this.mFd, convertData.convertedData, 0, convertData.convertedData.length);
        } else {
            throw new IOException("Unexpected DRM status: " + convertData.statusCode);
        }
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        Streams.writeSingleByte(this, i);
    }
}
