package com.android.server.usb;

import android.hardware.usb.AccessoryFilter;
import android.hardware.usb.DeviceFilter;
import android.util.SparseBooleanArray;
import android.util.Xml;
import android.util.sysfwutil.Slog;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class UsbUserPermissionManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ UsbUserPermissionManager f$0;

    @Override // java.lang.Runnable
    public final void run() {
        int size;
        DeviceFilter[] deviceFilterArr;
        int[][] iArr;
        boolean[][] zArr;
        int i;
        int size2;
        AccessoryFilter[] accessoryFilterArr;
        int[][] iArr2;
        UsbUserPermissionManager usbUserPermissionManager = this.f$0;
        synchronized (usbUserPermissionManager.mLock) {
            try {
                size = usbUserPermissionManager.mDevicePersistentPermissionMap.size();
                deviceFilterArr = new DeviceFilter[size];
                iArr = new int[size][];
                zArr = new boolean[size][];
                i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    deviceFilterArr[i2] = new DeviceFilter((DeviceFilter) usbUserPermissionManager.mDevicePersistentPermissionMap.keyAt(i2));
                    SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) usbUserPermissionManager.mDevicePersistentPermissionMap.valueAt(i2);
                    int size3 = sparseBooleanArray.size();
                    iArr[i2] = new int[size3];
                    zArr[i2] = new boolean[size3];
                    for (int i3 = 0; i3 < size3; i3++) {
                        iArr[i2][i3] = sparseBooleanArray.keyAt(i3);
                        zArr[i2][i3] = sparseBooleanArray.valueAt(i3);
                    }
                }
                size2 = usbUserPermissionManager.mAccessoryPersistentPermissionMap.size();
                accessoryFilterArr = new AccessoryFilter[size2];
                iArr2 = new int[size2][];
                boolean[][] zArr2 = new boolean[size2][];
                for (int i4 = 0; i4 < size2; i4++) {
                    accessoryFilterArr[i4] = new AccessoryFilter((AccessoryFilter) usbUserPermissionManager.mAccessoryPersistentPermissionMap.keyAt(i4));
                    SparseBooleanArray sparseBooleanArray2 = (SparseBooleanArray) usbUserPermissionManager.mAccessoryPersistentPermissionMap.valueAt(i4);
                    int size4 = sparseBooleanArray2.size();
                    iArr2[i4] = new int[size4];
                    zArr2[i4] = new boolean[size4];
                    for (int i5 = 0; i5 < size4; i5++) {
                        iArr2[i4][i5] = sparseBooleanArray2.keyAt(i5);
                        zArr2[i4][i5] = sparseBooleanArray2.valueAt(i5);
                    }
                }
                usbUserPermissionManager.mIsCopyPermissionsScheduled = false;
            } finally {
            }
        }
        synchronized (usbUserPermissionManager.mPermissionsFile) {
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    FileOutputStream startWrite = usbUserPermissionManager.mPermissionsFile.startWrite();
                    try {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.startTag((String) null, "permissions");
                        int i6 = 0;
                        while (i6 < size) {
                            int length = iArr[i6].length;
                            int i7 = i;
                            while (i7 < length) {
                                resolveSerializer.startTag((String) null, "permission");
                                resolveSerializer.attribute((String) null, "uid", Integer.toString(iArr[i6][i7]));
                                resolveSerializer.attribute((String) null, "granted", Boolean.toString(zArr[i6][i7]));
                                deviceFilterArr[i6].write(resolveSerializer);
                                resolveSerializer.endTag((String) null, "permission");
                                i7++;
                                size = size;
                            }
                            i6++;
                            i = 0;
                        }
                        for (int i8 = 0; i8 < size2; i8++) {
                            int length2 = iArr2[i8].length;
                            for (int i9 = 0; i9 < length2; i9++) {
                                resolveSerializer.startTag((String) null, "permission");
                                resolveSerializer.attribute((String) null, "uid", Integer.toString(iArr2[i8][i9]));
                                resolveSerializer.attribute((String) null, "granted", Boolean.toString(zArr[i8][i9]));
                                accessoryFilterArr[i8].write(resolveSerializer);
                                resolveSerializer.endTag((String) null, "permission");
                            }
                        }
                        resolveSerializer.endTag((String) null, "permissions");
                        resolveSerializer.endDocument();
                        usbUserPermissionManager.mPermissionsFile.finishWrite(startWrite);
                    } catch (IOException e) {
                        e = e;
                        fileOutputStream = startWrite;
                        Slog.e("UsbUserPermissionManager", "Failed to write permissions", e);
                        if (fileOutputStream != null) {
                            usbUserPermissionManager.mPermissionsFile.failWrite(fileOutputStream);
                        }
                    }
                } finally {
                }
            } catch (IOException e2) {
                e = e2;
            }
        }
    }
}
