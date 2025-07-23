package android.os;

import android.system.OsConstants;
import com.android.internal.util.ArrayUtils;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import libcore.io.IoBridge;
import libcore.io.IoUtils;
import libcore.io.Memory;
import libcore.io.Streams;

@Deprecated
/* loaded from: classes3.dex */
public class FileBridge extends Thread {
    private static final int CMD_CLOSE = 3;
    private static final int CMD_FSYNC = 2;
    private static final int CMD_WRITE = 1;
    private static final int MSG_LENGTH = 8;
    private static final String TAG = "FileBridge";
    private ParcelFileDescriptor mClient;
    private volatile boolean mClosed;
    private ParcelFileDescriptor mServer;
    private ParcelFileDescriptor mTarget;

    public FileBridge() {
        try {
            ParcelFileDescriptor[] createSocketPair = ParcelFileDescriptor.createSocketPair(OsConstants.SOCK_STREAM);
            this.mServer = createSocketPair[0];
            this.mClient = createSocketPair[1];
        } catch (IOException unused) {
            throw new RuntimeException("Failed to create bridge");
        }
    }

    public boolean isClosed() {
        return this.mClosed;
    }

    public void forceClose() {
        IoUtils.closeQuietly(this.mTarget);
        IoUtils.closeQuietly(this.mServer);
        this.mClosed = true;
    }

    public void setTargetFile(ParcelFileDescriptor parcelFileDescriptor) {
        this.mTarget = parcelFileDescriptor;
    }

    public ParcelFileDescriptor getClientSocket() {
        return this.mClient;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a2, code lost:
    
        return;
     */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r6 = this;
            r0 = 8192(0x2000, float:1.148E-41)
            java.nio.ByteBuffer r1 = java.nio.ByteBuffer.allocateDirect(r0)
            boolean r2 = r1.hasArray()
            if (r2 == 0) goto L11
            byte[] r0 = r1.array()
            goto L13
        L11:
            byte[] r0 = new byte[r0]
        L13:
            android.os.ParcelFileDescriptor r1 = r6.mServer     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            java.io.FileDescriptor r1 = r1.getFileDescriptor()     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            r2 = 8
            r3 = 0
            int r1 = libcore.io.IoBridge.read(r1, r0, r3, r2)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            if (r1 != r2) goto L9f
            java.nio.ByteOrder r1 = java.nio.ByteOrder.BIG_ENDIAN     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            int r1 = libcore.io.Memory.peekInt(r0, r3, r1)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            r4 = 1
            if (r1 != r4) goto L6d
            java.nio.ByteOrder r1 = java.nio.ByteOrder.BIG_ENDIAN     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            r2 = 4
            int r1 = libcore.io.Memory.peekInt(r0, r2, r1)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
        L32:
            if (r1 <= 0) goto L13
            android.os.ParcelFileDescriptor r2 = r6.mServer     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            java.io.FileDescriptor r2 = r2.getFileDescriptor()     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            int r4 = r0.length     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            int r4 = java.lang.Math.min(r4, r1)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            int r2 = libcore.io.IoBridge.read(r2, r0, r3, r4)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            r4 = -1
            if (r2 == r4) goto L51
            android.os.ParcelFileDescriptor r4 = r6.mTarget     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            java.io.FileDescriptor r4 = r4.getFileDescriptor()     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            libcore.io.IoBridge.write(r4, r0, r3, r2)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            int r1 = r1 - r2
            goto L32
        L51:
            java.io.IOException r0 = new java.io.IOException     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            r2.<init>()     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            java.lang.String r3 = "Unexpected EOF; still expected "
            r2.append(r3)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            r2.append(r1)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            java.lang.String r1 = " bytes"
            r2.append(r1)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            java.lang.String r1 = r2.toString()     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            r0.<init>(r1)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            throw r0     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
        L6d:
            r5 = 2
            if (r1 != r5) goto L83
            android.os.ParcelFileDescriptor r1 = r6.mTarget     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            java.io.FileDescriptor r1 = r1.getFileDescriptor()     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            android.system.Os.fsync(r1)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            android.os.ParcelFileDescriptor r1 = r6.mServer     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            java.io.FileDescriptor r1 = r1.getFileDescriptor()     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            libcore.io.IoBridge.write(r1, r0, r3, r2)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            goto L13
        L83:
            r5 = 3
            if (r1 != r5) goto L13
            android.os.ParcelFileDescriptor r1 = r6.mTarget     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            java.io.FileDescriptor r1 = r1.getFileDescriptor()     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            android.system.Os.fsync(r1)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            android.os.ParcelFileDescriptor r1 = r6.mTarget     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            r1.close()     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            r6.mClosed = r4     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            android.os.ParcelFileDescriptor r1 = r6.mServer     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            java.io.FileDescriptor r1 = r1.getFileDescriptor()     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
            libcore.io.IoBridge.write(r1, r0, r3, r2)     // Catch: java.lang.Throwable -> La3 java.lang.Throwable -> La5
        L9f:
            r6.forceClose()
            return
        La3:
            r0 = move-exception
            goto Lb1
        La5:
            r0 = move-exception
            java.lang.String r1 = "FileBridge"
            java.lang.String r2 = "Failed during bridge"
            android.util.Log.wtf(r1, r2, r0)     // Catch: java.lang.Throwable -> La3
            r6.forceClose()
            return
        Lb1:
            r6.forceClose()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.FileBridge.run():void");
    }

    public static class FileBridgeOutputStream extends OutputStream {
        private final FileDescriptor mClient;
        private final ParcelFileDescriptor mClientPfd;
        private final byte[] mTemp;
        private final ByteBuffer mTempBuffer;

        public FileBridgeOutputStream(ParcelFileDescriptor parcelFileDescriptor) {
            byte[] bArr;
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(8);
            this.mTempBuffer = allocateDirect;
            if (allocateDirect.hasArray()) {
                bArr = allocateDirect.array();
            } else {
                bArr = new byte[8];
            }
            this.mTemp = bArr;
            this.mClientPfd = parcelFileDescriptor;
            this.mClient = parcelFileDescriptor.getFileDescriptor();
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            try {
                writeCommandAndBlock(3, "close()");
            } finally {
                IoUtils.closeQuietly(this.mClientPfd);
            }
        }

        public void fsync() throws IOException {
            writeCommandAndBlock(2, "fsync()");
        }

        private void writeCommandAndBlock(int i, String str) throws IOException {
            Memory.pokeInt(this.mTemp, 0, i, ByteOrder.BIG_ENDIAN);
            IoBridge.write(this.mClient, this.mTemp, 0, 8);
            if (IoBridge.read(this.mClient, this.mTemp, 0, 8) == 8 && Memory.peekInt(this.mTemp, 0, ByteOrder.BIG_ENDIAN) == i) {
                return;
            }
            throw new IOException("Failed to execute " + str + " across bridge");
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            ArrayUtils.throwsIfOutOfBounds(bArr.length, i, i2);
            Memory.pokeInt(this.mTemp, 0, 1, ByteOrder.BIG_ENDIAN);
            Memory.pokeInt(this.mTemp, 4, i2, ByteOrder.BIG_ENDIAN);
            IoBridge.write(this.mClient, this.mTemp, 0, 8);
            IoBridge.write(this.mClient, bArr, i, i2);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            Streams.writeSingleByte(this, i);
        }
    }
}
