package android.hardware.usb;

import android.util.Log;
import com.android.internal.util.Preconditions;
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
        boolean zNative_init = native_init(usbDeviceConnection, usbEndpoint.getAddress(), usbEndpoint.getAttributes(), usbEndpoint.getMaxPacketSize(), usbEndpoint.getInterval());
        if (zNative_init) {
            this.mCloseGuard.open("UsbRequest.close");
        }
        return zNative_init;
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
        boolean zNative_queue_array;
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
                zNative_queue_array = native_queue_direct(byteBuffer, i, z);
            } else if (byteBuffer.hasArray()) {
                zNative_queue_array = native_queue_array(byteBuffer.array(), i, z);
            } else {
                throw new IllegalArgumentException("buffer is not direct and has no array");
            }
            if (!zNative_queue_array) {
                this.mBuffer = null;
                this.mLength = 0;
            }
        }
        return zNative_queue_array;
    }

    public boolean queue(ByteBuffer byteBuffer) {
        UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection == null) {
            throw new IllegalStateException("invalid connection");
        }
        return usbDeviceConnection.queueRequest(this, byteBuffer);
    }

    boolean queueIfConnectionOpen(ByteBuffer byteBuffer) {
        boolean zNative_queue;
        UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection == null || !usbDeviceConnection.isOpen()) {
            throw new IllegalStateException("invalid connection");
        }
        Preconditions.checkState(this.mNativeContext != 0, "request is not initialized");
        Preconditions.checkState(!this.mIsUsingNewQueue, "this request is currently queued");
        boolean z = this.mEndpoint.getDirection() == 0;
        synchronized (this.mLock) {
            this.mBuffer = byteBuffer;
            if (byteBuffer == null) {
                this.mIsUsingNewQueue = true;
                zNative_queue = native_queue(null, 0, 0);
            } else {
                if (usbDeviceConnection.getContext().getApplicationInfo().targetSdkVersion < 28) {
                    Preconditions.checkArgumentInRange(byteBuffer.remaining(), 0, 16384, "number of remaining bytes");
                }
                Preconditions.checkArgument(!byteBuffer.isReadOnly() || z, "buffer can not be read-only when receiving data");
                if (!byteBuffer.isDirect()) {
                    this.mTempBuffer = ByteBuffer.allocateDirect(this.mBuffer.remaining());
                    if (z) {
                        this.mBuffer.mark();
                        this.mTempBuffer.put(this.mBuffer);
                        this.mTempBuffer.flip();
                        this.mBuffer.reset();
                    }
                    byteBuffer = this.mTempBuffer;
                }
                this.mIsUsingNewQueue = true;
                zNative_queue = native_queue(byteBuffer, byteBuffer.position(), byteBuffer.remaining());
            }
        }
        if (!zNative_queue) {
            this.mIsUsingNewQueue = false;
            this.mTempBuffer = null;
            this.mBuffer = null;
        }
        return zNative_queue;
    }

    void dequeue(boolean z) {
        int iNative_dequeue_array;
        boolean z2 = this.mEndpoint.getDirection() == 0;
        synchronized (this.mLock) {
            if (this.mIsUsingNewQueue) {
                int iNative_dequeue_direct = native_dequeue_direct();
                this.mIsUsingNewQueue = false;
                ByteBuffer byteBuffer = this.mBuffer;
                if (byteBuffer != null) {
                    ByteBuffer byteBuffer2 = this.mTempBuffer;
                    if (byteBuffer2 == null) {
                        byteBuffer.position(byteBuffer.position() + iNative_dequeue_direct);
                    } else {
                        byteBuffer2.limit(iNative_dequeue_direct);
                        try {
                            if (z2) {
                                ByteBuffer byteBuffer3 = this.mBuffer;
                                byteBuffer3.position(byteBuffer3.position() + iNative_dequeue_direct);
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
                    iNative_dequeue_array = native_dequeue_direct();
                } else {
                    iNative_dequeue_array = native_dequeue_array(this.mBuffer.array(), this.mLength, z2);
                }
                if (iNative_dequeue_array >= 0) {
                    int iMin = Math.min(iNative_dequeue_array, this.mLength);
                    try {
                        this.mBuffer.position(iMin);
                    } catch (IllegalArgumentException e) {
                        if (z) {
                            Log.e(TAG, "Buffer " + this.mBuffer + " does not have enough space to read " + iMin + " bytes", e);
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
