package android.mtp;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.UserManager;
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean open(UsbDeviceConnection usbDeviceConnection) {
        boolean zNative_open;
        Context context = usbDeviceConnection.getContext();
        synchronized (this.mLock) {
            if (context != null) {
                try {
                    zNative_open = !((UserManager) context.getSystemService("user")).hasUserRestriction(UserManager.DISALLOW_USB_FILE_TRANSFER) ? native_open(this.mDevice.getDeviceName(), usbDeviceConnection.getFileDescriptor()) : false;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (!zNative_open) {
                usbDeviceConnection.close();
            } else {
                this.mConnection = usbDeviceConnection;
                this.mCloseGuard.open("close");
            }
        }
        return zNative_open;
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
        final int iNative_submit_event_request = native_submit_event_request();
        Preconditions.checkState(iNative_submit_event_request >= 0, "Other thread is reading an event.");
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.mtp.MtpDevice.1
                @Override // android.os.CancellationSignal.OnCancelListener
                public void onCancel() {
                    MtpDevice.this.native_discard_event_request(iNative_submit_event_request);
                }
            });
        }
        try {
            return native_reap_event_request(iNative_submit_event_request);
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
