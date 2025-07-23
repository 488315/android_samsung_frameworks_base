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

    private native boolean native_init(UsbDeviceConnection usbDeviceConnection, int i, int i2, int i3, int i4);

    private native boolean native_queue(ByteBuffer byteBuffer, int i, int i2);

    private native boolean native_queue_array(byte[] bArr, int i, boolean z);

    private native boolean native_queue_direct(ByteBuffer byteBuffer, int i, boolean z);

    public boolean initialize(UsbDeviceConnection usbDeviceConnection, UsbEndpoint usbEndpoint) {
        this.mEndpoint = usbEndpoint;
        this.mConnection = (UsbDeviceConnection) Objects.requireNonNull(usbDeviceConnection, "connection");
        boolean native_init = native_init(usbDeviceConnection, usbEndpoint.getAddress(), usbEndpoint.getAttributes(), usbEndpoint.getMaxPacketSize(), usbEndpoint.getInterval());
        if (native_init) {
            this.mCloseGuard.open("UsbRequest.close");
        }
        return native_init;
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
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
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

    public void setClientData(Object obj) {
        this.mClientData = obj;
    }

    @Deprecated
    public boolean queue(ByteBuffer byteBuffer, int i) {
        UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection == null) {
            throw new NullPointerException("invalid connection");
        }
        return usbDeviceConnection.queueRequest(this, byteBuffer, i);
    }

    boolean queueIfConnectionOpen(ByteBuffer byteBuffer, int i) {
        boolean native_queue_array;
        UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection == null || !usbDeviceConnection.isOpen()) {
            throw new NullPointerException("invalid connection");
        }
        boolean z = this.mEndpoint.getDirection() == 0;
        if (usbDeviceConnection.getContext().getApplicationInfo().targetSdkVersion < 28 && i > 16384) {
            i = 16384;
        }
        synchronized (this.mLock) {
            this.mBuffer = byteBuffer;
            this.mLength = i;
            if (byteBuffer.isDirect()) {
                native_queue_array = native_queue_direct(byteBuffer, i, z);
            } else if (byteBuffer.hasArray()) {
                native_queue_array = native_queue_array(byteBuffer.array(), i, z);
            } else {
                throw new IllegalArgumentException("buffer is not direct and has no array");
            }
            if (!native_queue_array) {
                this.mBuffer = null;
                this.mLength = 0;
            }
        }
        return native_queue_array;
    }

    public boolean queue(ByteBuffer byteBuffer) {
        UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection == null) {
            throw new IllegalStateException("invalid connection");
        }
        return usbDeviceConnection.queueRequest(this, byteBuffer);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x006e A[Catch: all -> 0x00ac, TryCatch #0 {, blocks: (B:14:0x0032, B:16:0x0037, B:17:0x00a2, B:23:0x003e, B:25:0x004c, B:26:0x0057, B:31:0x0063, B:33:0x006e, B:35:0x007c, B:36:0x0092, B:37:0x0094), top: B:13:0x0032 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean queueIfConnectionOpen(java.nio.ByteBuffer r9) {
        /*
            r8 = this;
            android.hardware.usb.UsbDeviceConnection r0 = r8.mConnection
            if (r0 == 0) goto Laf
            boolean r1 = r0.isOpen()
            if (r1 == 0) goto Laf
            long r1 = r8.mNativeContext
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
            boolean r1 = r8.mIsUsingNewQueue
            r1 = r1 ^ r2
            java.lang.String r4 = "this request is currently queued"
            com.android.internal.util.Preconditions.checkState(r1, r4)
            android.hardware.usb.UsbEndpoint r1 = r8.mEndpoint
            int r1 = r1.getDirection()
            if (r1 != 0) goto L2e
            r1 = r2
            goto L2f
        L2e:
            r1 = r3
        L2f:
            java.lang.Object r4 = r8.mLock
            monitor-enter(r4)
            r8.mBuffer = r9     // Catch: java.lang.Throwable -> Lac
            r5 = 0
            if (r9 != 0) goto L3e
            r8.mIsUsingNewQueue = r2     // Catch: java.lang.Throwable -> Lac
            boolean r9 = r8.native_queue(r5, r3, r3)     // Catch: java.lang.Throwable -> Lac
            goto La2
        L3e:
            android.content.Context r0 = r0.getContext()     // Catch: java.lang.Throwable -> Lac
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo()     // Catch: java.lang.Throwable -> Lac
            int r0 = r0.targetSdkVersion     // Catch: java.lang.Throwable -> Lac
            r6 = 28
            if (r0 >= r6) goto L57
            int r0 = r9.remaining()     // Catch: java.lang.Throwable -> Lac
            java.lang.String r6 = "number of remaining bytes"
            r7 = 16384(0x4000, float:2.2959E-41)
            com.android.internal.util.Preconditions.checkArgumentInRange(r0, r3, r7, r6)     // Catch: java.lang.Throwable -> Lac
        L57:
            boolean r0 = r9.isReadOnly()     // Catch: java.lang.Throwable -> Lac
            if (r0 == 0) goto L62
            if (r1 == 0) goto L60
            goto L62
        L60:
            r0 = r3
            goto L63
        L62:
            r0 = r2
        L63:
            java.lang.String r6 = "buffer can not be read-only when receiving data"
            com.android.internal.util.Preconditions.checkArgument(r0, r6)     // Catch: java.lang.Throwable -> Lac
            boolean r0 = r9.isDirect()     // Catch: java.lang.Throwable -> Lac
            if (r0 != 0) goto L94
            java.nio.ByteBuffer r9 = r8.mBuffer     // Catch: java.lang.Throwable -> Lac
            int r9 = r9.remaining()     // Catch: java.lang.Throwable -> Lac
            java.nio.ByteBuffer r9 = java.nio.ByteBuffer.allocateDirect(r9)     // Catch: java.lang.Throwable -> Lac
            r8.mTempBuffer = r9     // Catch: java.lang.Throwable -> Lac
            if (r1 == 0) goto L92
            java.nio.ByteBuffer r9 = r8.mBuffer     // Catch: java.lang.Throwable -> Lac
            r9.mark()     // Catch: java.lang.Throwable -> Lac
            java.nio.ByteBuffer r9 = r8.mTempBuffer     // Catch: java.lang.Throwable -> Lac
            java.nio.ByteBuffer r0 = r8.mBuffer     // Catch: java.lang.Throwable -> Lac
            r9.put(r0)     // Catch: java.lang.Throwable -> Lac
            java.nio.ByteBuffer r9 = r8.mTempBuffer     // Catch: java.lang.Throwable -> Lac
            r9.flip()     // Catch: java.lang.Throwable -> Lac
            java.nio.ByteBuffer r9 = r8.mBuffer     // Catch: java.lang.Throwable -> Lac
            r9.reset()     // Catch: java.lang.Throwable -> Lac
        L92:
            java.nio.ByteBuffer r9 = r8.mTempBuffer     // Catch: java.lang.Throwable -> Lac
        L94:
            r8.mIsUsingNewQueue = r2     // Catch: java.lang.Throwable -> Lac
            int r0 = r9.position()     // Catch: java.lang.Throwable -> Lac
            int r1 = r9.remaining()     // Catch: java.lang.Throwable -> Lac
            boolean r9 = r8.native_queue(r9, r0, r1)     // Catch: java.lang.Throwable -> Lac
        La2:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> Lac
            if (r9 != 0) goto Lab
            r8.mIsUsingNewQueue = r3
            r8.mTempBuffer = r5
            r8.mBuffer = r5
        Lab:
            return r9
        Lac:
            r8 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> Lac
            throw r8
        Laf:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "invalid connection"
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.usb.UsbRequest.queueIfConnectionOpen(java.nio.ByteBuffer):boolean");
    }

    void dequeue(boolean z) {
        int native_dequeue_array;
        boolean z2 = this.mEndpoint.getDirection() == 0;
        synchronized (this.mLock) {
            if (this.mIsUsingNewQueue) {
                int native_dequeue_direct = native_dequeue_direct();
                this.mIsUsingNewQueue = false;
                ByteBuffer byteBuffer = this.mBuffer;
                if (byteBuffer != null) {
                    ByteBuffer byteBuffer2 = this.mTempBuffer;
                    if (byteBuffer2 == null) {
                        byteBuffer.position(byteBuffer.position() + native_dequeue_direct);
                    } else {
                        byteBuffer2.limit(native_dequeue_direct);
                        try {
                            if (z2) {
                                ByteBuffer byteBuffer3 = this.mBuffer;
                                byteBuffer3.position(byteBuffer3.position() + native_dequeue_direct);
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
                    native_dequeue_array = native_dequeue_direct();
                } else {
                    native_dequeue_array = native_dequeue_array(this.mBuffer.array(), this.mLength, z2);
                }
                if (native_dequeue_array >= 0) {
                    int min = Math.min(native_dequeue_array, this.mLength);
                    try {
                        this.mBuffer.position(min);
                    } catch (IllegalArgumentException e) {
                        if (z) {
                            Log.e(TAG, "Buffer " + this.mBuffer + " does not have enough space to read " + min + " bytes", e);
                            throw new BufferOverflowException();
                        }
                        throw e;
                    }
                }
                this.mBuffer = null;
                this.mLength = 0;
            }
        }
    }

    public boolean cancel() {
        UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection == null) {
            return false;
        }
        return usbDeviceConnection.cancelRequest(this);
    }

    boolean cancelIfOpen() {
        UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (this.mNativeContext == 0 || (usbDeviceConnection != null && !usbDeviceConnection.isOpen())) {
            Log.w(TAG, "Detected attempt to cancel a request on a connection which isn't open");
            return false;
        }
        return native_cancel();
    }
}
