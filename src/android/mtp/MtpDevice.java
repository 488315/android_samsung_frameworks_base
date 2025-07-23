package android.mtp;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class MtpDevice {
    private static final String TAG = "MtpDevice";
    private UsbDeviceConnection mConnection;
    private final UsbDevice mDevice;
    private long mNativeContext;
    private CloseGuard mCloseGuard = CloseGuard.get();
    private final Object mLock = new Object();

    private native void native_close();

    private native boolean native_delete_object(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native void native_discard_event_request(int i);

    private native MtpDeviceInfo native_get_device_info();

    private native byte[] native_get_object(int i, long j);

    private native int[] native_get_object_handles(int i, int i2, int i3);

    private native MtpObjectInfo native_get_object_info(int i);

    private native long native_get_object_size_long(int i, int i2) throws IOException;

    private native int native_get_parent(int i);

    private native long native_get_partial_object(int i, long j, long j2, byte[] bArr) throws IOException;

    private native int native_get_partial_object_64(int i, long j, long j2, byte[] bArr) throws IOException;

    private native int native_get_storage_id(int i);

    private native int[] native_get_storage_ids();

    private native MtpStorageInfo native_get_storage_info(int i);

    private native byte[] native_get_thumbnail(int i);

    private native boolean native_import_file(int i, int i2);

    private native boolean native_import_file(int i, String str);

    private native boolean native_open(String str, int i);

    private native MtpEvent native_reap_event_request(int i) throws IOException;

    private native boolean native_send_object(int i, long j, int i2);

    private native MtpObjectInfo native_send_object_info(MtpObjectInfo mtpObjectInfo);

    private native int native_set_device_property_init_version(String str);

    private native int native_submit_event_request() throws IOException;

    static {
        System.loadLibrary("media_jni");
    }

    public MtpDevice(UsbDevice usbDevice) {
        Preconditions.checkNotNull(usbDevice);
        this.mDevice = usbDevice;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033 A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:17:0x0009, B:19:0x001b, B:6:0x002f, B:7:0x003c, B:15:0x0033), top: B:16:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x002f A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:17:0x0009, B:19:0x001b, B:6:0x002f, B:7:0x003c, B:15:0x0033), top: B:16:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean open(android.hardware.usb.UsbDeviceConnection r4) {
        /*
            r3 = this;
            android.content.Context r0 = r4.getContext()
            java.lang.Object r1 = r3.mLock
            monitor-enter(r1)
            if (r0 == 0) goto L2c
            java.lang.String r2 = "user"
            java.lang.Object r0 = r0.getSystemService(r2)     // Catch: java.lang.Throwable -> L2a
            android.os.UserManager r0 = (android.os.UserManager) r0     // Catch: java.lang.Throwable -> L2a
            java.lang.String r2 = "no_usb_file_transfer"
            boolean r0 = r0.hasUserRestriction(r2)     // Catch: java.lang.Throwable -> L2a
            if (r0 != 0) goto L2c
            android.hardware.usb.UsbDevice r0 = r3.mDevice     // Catch: java.lang.Throwable -> L2a
            java.lang.String r0 = r0.getDeviceName()     // Catch: java.lang.Throwable -> L2a
            int r2 = r4.getFileDescriptor()     // Catch: java.lang.Throwable -> L2a
            boolean r0 = r3.native_open(r0, r2)     // Catch: java.lang.Throwable -> L2a
            goto L2d
        L2a:
            r3 = move-exception
            goto L3e
        L2c:
            r0 = 0
        L2d:
            if (r0 != 0) goto L33
            r4.close()     // Catch: java.lang.Throwable -> L2a
            goto L3c
        L33:
            r3.mConnection = r4     // Catch: java.lang.Throwable -> L2a
            dalvik.system.CloseGuard r3 = r3.mCloseGuard     // Catch: java.lang.Throwable -> L2a
            java.lang.String r4 = "close"
            r3.open(r4)     // Catch: java.lang.Throwable -> L2a
        L3c:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L2a
            return r0
        L3e:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L2a
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpDevice.open(android.hardware.usb.UsbDeviceConnection):boolean");
    }

    public void close() {
        synchronized (this.mLock) {
            if (this.mConnection != null) {
                this.mCloseGuard.close();
                native_close();
                this.mConnection.close();
                this.mConnection = null;
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

    public String getDeviceName() {
        return this.mDevice.getDeviceName();
    }

    public int getDeviceId() {
        return this.mDevice.getDeviceId();
    }

    public String toString() {
        return this.mDevice.getDeviceName();
    }

    public MtpDeviceInfo getDeviceInfo() {
        return native_get_device_info();
    }

    public int setDevicePropertyInitVersion(String str) {
        return native_set_device_property_init_version(str);
    }

    public int[] getStorageIds() {
        return native_get_storage_ids();
    }

    public int[] getObjectHandles(int i, int i2, int i3) {
        return native_get_object_handles(i, i2, i3);
    }

    public byte[] getObject(int i, int i2) {
        Preconditions.checkArgumentNonnegative(i2, "objectSize should not be negative");
        return native_get_object(i, i2);
    }

    public long getPartialObject(int i, long j, long j2, byte[] bArr) throws IOException {
        return native_get_partial_object(i, j, j2, bArr);
    }

    public long getPartialObject64(int i, long j, long j2, byte[] bArr) throws IOException {
        return native_get_partial_object_64(i, j, j2, bArr);
    }

    public byte[] getThumbnail(int i) {
        return native_get_thumbnail(i);
    }

    public MtpStorageInfo getStorageInfo(int i) {
        return native_get_storage_info(i);
    }

    public MtpObjectInfo getObjectInfo(int i) {
        return native_get_object_info(i);
    }

    public boolean deleteObject(int i) {
        return native_delete_object(i);
    }

    public long getParent(int i) {
        return native_get_parent(i);
    }

    public long getStorageId(int i) {
        return native_get_storage_id(i);
    }

    public boolean importFile(int i, String str) {
        return native_import_file(i, str);
    }

    public boolean importFile(int i, ParcelFileDescriptor parcelFileDescriptor) {
        return native_import_file(i, parcelFileDescriptor.getFd());
    }

    public boolean sendObject(int i, long j, ParcelFileDescriptor parcelFileDescriptor) {
        return native_send_object(i, j, parcelFileDescriptor.getFd());
    }

    public MtpObjectInfo sendObjectInfo(MtpObjectInfo mtpObjectInfo) {
        return native_send_object_info(mtpObjectInfo);
    }

    public MtpEvent readEvent(CancellationSignal cancellationSignal) throws IOException {
        final int native_submit_event_request = native_submit_event_request();
        Preconditions.checkState(native_submit_event_request >= 0, "Other thread is reading an event.");
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.mtp.MtpDevice.1
                @Override // android.os.CancellationSignal.OnCancelListener
                public void onCancel() {
                    MtpDevice.this.native_discard_event_request(native_submit_event_request);
                }
            });
        }
        try {
            return native_reap_event_request(native_submit_event_request);
        } finally {
            if (cancellationSignal != null) {
                cancellationSignal.setOnCancelListener(null);
            }
        }
    }

    public long getObjectSizeLong(int i, int i2) throws IOException {
        return native_get_object_size_long(i, i2);
    }
}
