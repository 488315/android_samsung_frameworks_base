package android.hardware.usb;

import android.util.Log;

import dalvik.system.CloseGuard;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.Objects;

/* loaded from: classes2.dex */
public class UsbRequest {
    static final int MAX_USBFS_BUFFER_SIZE = 16384;
    private static final String TAG = "UsbRequest";
    private ByteBuffer mBuffer;
    private Object mClientData;
    private UsbDeviceConnection mConnection;
    private UsbEndpoint mEndpoint;
    private boolean mIsUsingNewQueue;
    private int mLength;
    private long mNativeContext;
    private ByteBuffer mTempBuffer;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final Object mLock = new Object();

    private native boolean native_cancel();

    private native void native_close();

    private native int native_dequeue_array(byte[] bArr, int i, boolean z);

    private native int native_dequeue_direct();

    private native boolean native_init(
            UsbDeviceConnection usbDeviceConnection, int i, int i2, int i3, int i4);

    private native boolean native_queue(ByteBuffer byteBuffer, int i, int i2);

    private native boolean native_queue_array(byte[] bArr, int i, boolean z);

    private native boolean native_queue_direct(ByteBuffer byteBuffer, int i, boolean z);

    public boolean initialize(UsbDeviceConnection connection, UsbEndpoint endpoint) {
        this.mEndpoint = endpoint;
        this.mConnection = (UsbDeviceConnection) Objects.requireNonNull(connection, "connection");
        boolean wasInitialized =
                native_init(
                        connection,
                        endpoint.getAddress(),
                        endpoint.getAttributes(),
                        endpoint.getMaxPacketSize(),
                        endpoint.getInterval());
        if (wasInitialized) {
            this.mCloseGuard.open("UsbRequest.close");
        }
        return wasInitialized;
    }

    public void close() {
        synchronized (this.mLock) {
            if (this.mNativeContext != 0) {
                this.mEndpoint = null;
                this.mConnection = null;
                native_close();
                this.mCloseGuard.close();
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }

    public UsbEndpoint getEndpoint() {
        return this.mEndpoint;
    }

    public Object getClientData() {
        return this.mClientData;
    }

    public void setClientData(Object data) {
        this.mClientData = data;
    }

    @Deprecated
    public boolean queue(ByteBuffer buffer, int length) {
        UsbDeviceConnection connection = this.mConnection;
        if (connection == null) {
            throw new NullPointerException("invalid connection");
        }
        return connection.queueRequest(this, buffer, length);
    }

    boolean queueIfConnectionOpen(ByteBuffer buffer, int length) {
        int length2;
        boolean result;
        UsbDeviceConnection connection = this.mConnection;
        if (connection == null || !connection.isOpen()) {
            throw new NullPointerException("invalid connection");
        }
        boolean out = this.mEndpoint.getDirection() == 0;
        if (connection.getContext().getApplicationInfo().targetSdkVersion < 28 && length > 16384) {
            length2 = 16384;
        } else {
            length2 = length;
        }
        synchronized (this.mLock) {
            this.mBuffer = buffer;
            this.mLength = length2;
            if (buffer.isDirect()) {
                result = native_queue_direct(buffer, length2, out);
            } else {
                boolean result2 = buffer.hasArray();
                if (result2) {
                    result = native_queue_array(buffer.array(), length2, out);
                } else {
                    throw new IllegalArgumentException("buffer is not direct and has no array");
                }
            }
            if (!result) {
                this.mBuffer = null;
                this.mLength = 0;
            }
        }
        return result;
    }

    public boolean queue(ByteBuffer buffer) {
        UsbDeviceConnection connection = this.mConnection;
        if (connection == null) {
            throw new IllegalStateException("invalid connection");
        }
        return connection.queueRequest(this, buffer);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x006e A[Catch: all -> 0x00ad, TryCatch #0 {, blocks: (B:14:0x0032, B:16:0x0037, B:17:0x00a3, B:23:0x003e, B:25:0x004c, B:26:0x0057, B:31:0x0063, B:33:0x006e, B:35:0x007c, B:36:0x0092, B:37:0x0095), top: B:13:0x0032 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean queueIfConnectionOpen(java.nio.ByteBuffer r10) {
        /*
            r9 = this;
            android.hardware.usb.UsbDeviceConnection r0 = r9.mConnection
            if (r0 == 0) goto Lb0
            boolean r1 = r0.isOpen()
            if (r1 == 0) goto Lb0
            long r1 = r9.mNativeContext
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L16
            r1 = r2
            goto L17
        L16:
            r1 = r3
        L17:
            java.lang.String r4 = "request is not initialized"
            com.android.internal.util.Preconditions.checkState(r1, r4)
            boolean r1 = r9.mIsUsingNewQueue
            r1 = r1 ^ r2
            java.lang.String r4 = "this request is currently queued"
            com.android.internal.util.Preconditions.checkState(r1, r4)
            android.hardware.usb.UsbEndpoint r1 = r9.mEndpoint
            int r1 = r1.getDirection()
            if (r1 != 0) goto L2e
            r1 = r2
            goto L2f
        L2e:
            r1 = r3
        L2f:
            java.lang.Object r4 = r9.mLock
            monitor-enter(r4)
            r9.mBuffer = r10     // Catch: java.lang.Throwable -> Lad
            r5 = 0
            if (r10 != 0) goto L3e
            r9.mIsUsingNewQueue = r2     // Catch: java.lang.Throwable -> Lad
            boolean r2 = r9.native_queue(r5, r3, r3)     // Catch: java.lang.Throwable -> Lad
            goto La3
        L3e:
            android.content.Context r6 = r0.getContext()     // Catch: java.lang.Throwable -> Lad
            android.content.pm.ApplicationInfo r6 = r6.getApplicationInfo()     // Catch: java.lang.Throwable -> Lad
            int r6 = r6.targetSdkVersion     // Catch: java.lang.Throwable -> Lad
            r7 = 28
            if (r6 >= r7) goto L57
            int r6 = r10.remaining()     // Catch: java.lang.Throwable -> Lad
            java.lang.String r7 = "number of remaining bytes"
            r8 = 16384(0x4000, float:2.2959E-41)
            com.android.internal.util.Preconditions.checkArgumentInRange(r6, r3, r8, r7)     // Catch: java.lang.Throwable -> Lad
        L57:
            boolean r6 = r10.isReadOnly()     // Catch: java.lang.Throwable -> Lad
            if (r6 == 0) goto L62
            if (r1 == 0) goto L60
            goto L62
        L60:
            r6 = r3
            goto L63
        L62:
            r6 = r2
        L63:
            java.lang.String r7 = "buffer can not be read-only when receiving data"
            com.android.internal.util.Preconditions.checkArgument(r6, r7)     // Catch: java.lang.Throwable -> Lad
            boolean r6 = r10.isDirect()     // Catch: java.lang.Throwable -> Lad
            if (r6 != 0) goto L95
            java.nio.ByteBuffer r6 = r9.mBuffer     // Catch: java.lang.Throwable -> Lad
            int r6 = r6.remaining()     // Catch: java.lang.Throwable -> Lad
            java.nio.ByteBuffer r6 = java.nio.ByteBuffer.allocateDirect(r6)     // Catch: java.lang.Throwable -> Lad
            r9.mTempBuffer = r6     // Catch: java.lang.Throwable -> Lad
            if (r1 == 0) goto L92
            java.nio.ByteBuffer r6 = r9.mBuffer     // Catch: java.lang.Throwable -> Lad
            r6.mark()     // Catch: java.lang.Throwable -> Lad
            java.nio.ByteBuffer r6 = r9.mTempBuffer     // Catch: java.lang.Throwable -> Lad
            java.nio.ByteBuffer r7 = r9.mBuffer     // Catch: java.lang.Throwable -> Lad
            r6.put(r7)     // Catch: java.lang.Throwable -> Lad
            java.nio.ByteBuffer r6 = r9.mTempBuffer     // Catch: java.lang.Throwable -> Lad
            r6.flip()     // Catch: java.lang.Throwable -> Lad
            java.nio.ByteBuffer r6 = r9.mBuffer     // Catch: java.lang.Throwable -> Lad
            r6.reset()     // Catch: java.lang.Throwable -> Lad
        L92:
            java.nio.ByteBuffer r6 = r9.mTempBuffer     // Catch: java.lang.Throwable -> Lad
            r10 = r6
        L95:
            r9.mIsUsingNewQueue = r2     // Catch: java.lang.Throwable -> Lad
            int r2 = r10.position()     // Catch: java.lang.Throwable -> Lad
            int r6 = r10.remaining()     // Catch: java.lang.Throwable -> Lad
            boolean r2 = r9.native_queue(r10, r2, r6)     // Catch: java.lang.Throwable -> Lad
        La3:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> Lad
            if (r2 != 0) goto Lac
            r9.mIsUsingNewQueue = r3
            r9.mTempBuffer = r5
            r9.mBuffer = r5
        Lac:
            return r2
        Lad:
            r2 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> Lad
            throw r2
        Lb0:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "invalid connection"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.hardware.usb.UsbRequest.queueIfConnectionOpen(java.nio.ByteBuffer):boolean");
    }

    void dequeue(boolean useBufferOverflowInsteadOfIllegalArg) {
        int bytesTransferred;
        boolean isSend = this.mEndpoint.getDirection() == 0;
        synchronized (this.mLock) {
            if (this.mIsUsingNewQueue) {
                int bytesTransferred2 = native_dequeue_direct();
                this.mIsUsingNewQueue = false;
                if (this.mBuffer != null) {
                    if (this.mTempBuffer == null) {
                        this.mBuffer.position(this.mBuffer.position() + bytesTransferred2);
                    } else {
                        this.mTempBuffer.limit(bytesTransferred2);
                        try {
                            if (isSend) {
                                this.mBuffer.position(this.mBuffer.position() + bytesTransferred2);
                            } else {
                                this.mBuffer.put(this.mTempBuffer);
                            }
                            this.mTempBuffer = null;
                        } catch (Throwable th) {
                            this.mTempBuffer = null;
                            throw th;
                        }
                    }
                }
                this.mBuffer = null;
                this.mLength = 0;
            } else {
                if (this.mBuffer.isDirect()) {
                    bytesTransferred = native_dequeue_direct();
                } else {
                    bytesTransferred =
                            native_dequeue_array(this.mBuffer.array(), this.mLength, isSend);
                }
                if (bytesTransferred >= 0) {
                    int bytesToStore = Math.min(bytesTransferred, this.mLength);
                    try {
                        this.mBuffer.position(bytesToStore);
                    } catch (IllegalArgumentException e) {
                        if (!useBufferOverflowInsteadOfIllegalArg) {
                            throw e;
                        }
                        Log.e(
                                TAG,
                                "Buffer "
                                        + this.mBuffer
                                        + " does not have enough space to read "
                                        + bytesToStore
                                        + " bytes",
                                e);
                        throw new BufferOverflowException();
                    }
                }
                this.mBuffer = null;
                this.mLength = 0;
            }
        }
    }

    public boolean cancel() {
        UsbDeviceConnection connection = this.mConnection;
        if (connection == null) {
            return false;
        }
        return connection.cancelRequest(this);
    }

    boolean cancelIfOpen() {
        UsbDeviceConnection connection = this.mConnection;
        if (this.mNativeContext == 0 || (connection != null && !connection.isOpen())) {
            Log.w(TAG, "Detected attempt to cancel a request on a connection which isn't open");
            return false;
        }
        return native_cancel();
    }
}
