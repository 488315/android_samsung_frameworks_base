package com.android.server.usb;

import android.R;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorPrivacyManagerInternal;
import android.hardware.usb.AccessoryFilter;
import android.hardware.usb.DeviceFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.SparseBooleanArray;
import android.util.Xml;
import android.util.sysfwutil.Slog;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.pm.PersonaServiceHelper;
import com.samsung.android.knox.application.IApplicationPolicy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes3.dex */
public class UsbUserPermissionManager {
    public static final String TAG = "UsbUserPermissionManager";
    public final Context mContext;
    public final boolean mDisablePermissionDialogs;
    public boolean mIsCopyPermissionsScheduled;
    public final Object mLock;
    public final AtomicFile mPermissionsFile;
    public final SensorPrivacyManagerInternal mSensorPrivacyMgrInternal;
    public final UsbUserSettingsManager mUsbUserSettingsManager;
    public final UserHandle mUser;
    public final ArrayMap mDevicePermissionMap = new ArrayMap();
    public final ArrayMap mAccessoryPermissionMap = new ArrayMap();
    public final ArrayMap mDevicePersistentPermissionMap = new ArrayMap();
    public final ArrayMap mAccessoryPersistentPermissionMap = new ArrayMap();

    public UsbUserPermissionManager(Context context, UsbUserSettingsManager usbUserSettingsManager) {
        Object obj = new Object();
        this.mLock = obj;
        this.mContext = context;
        UserHandle user = context.getUser();
        this.mUser = user;
        this.mUsbUserSettingsManager = usbUserSettingsManager;
        this.mSensorPrivacyMgrInternal = (SensorPrivacyManagerInternal) LocalServices.getService(SensorPrivacyManagerInternal.class);
        this.mDisablePermissionDialogs = context.getResources().getBoolean(R.bool.config_customUserSwitchUi);
        this.mPermissionsFile = new AtomicFile(new File(Environment.getUserSystemDirectory(user.getIdentifier()), "usb_permissions.xml"), "usb-permissions");
        synchronized (obj) {
            readPermissionsLocked();
        }
    }

    public void removeAccessoryPermissions(UsbAccessory usbAccessory) {
        synchronized (this.mLock) {
            this.mAccessoryPermissionMap.remove(usbAccessory);
        }
    }

    public void removeDevicePermissions(UsbDevice usbDevice) {
        synchronized (this.mLock) {
            this.mDevicePermissionMap.remove(usbDevice.getDeviceName());
        }
    }

    public void grantDevicePermission(UsbDevice usbDevice, int i) {
        String str = TAG;
        Slog.d(str, "grantDevicePermission: device=" + usbDevice.getDeviceName() + " For uid=" + i);
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return;
        }
        synchronized (this.mLock) {
            String deviceName = usbDevice.getDeviceName();
            SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mDevicePermissionMap.get(deviceName);
            if (sparseBooleanArray == null) {
                sparseBooleanArray = new SparseBooleanArray(1);
                this.mDevicePermissionMap.put(deviceName, sparseBooleanArray);
            }
            sparseBooleanArray.put(i, true);
            SparseBooleanArray sparseBooleanArray2 = (SparseBooleanArray) this.mDevicePermissionMap.get(usbDevice.getDeviceName());
            if (sparseBooleanArray2 == null) {
                Slog.d(str, "grantDevicePermission: uidList is null");
            } else if (!sparseBooleanArray2.get(i)) {
                Slog.d(str, "grantDevicePermission: uidList put error");
            }
        }
    }

    public void grantAccessoryPermission(UsbAccessory usbAccessory, int i) {
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return;
        }
        synchronized (this.mLock) {
            SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mAccessoryPermissionMap.get(usbAccessory);
            if (sparseBooleanArray == null) {
                sparseBooleanArray = new SparseBooleanArray(1);
                this.mAccessoryPermissionMap.put(usbAccessory, sparseBooleanArray);
            }
            sparseBooleanArray.put(i, true);
        }
    }

    public boolean hasPermission(UsbDevice usbDevice, String str, int i, int i2) {
        int indexOfKey;
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return false;
        }
        if (usbDevice.getHasVideoCapture() && (this.mSensorPrivacyMgrInternal.isSensorPrivacyEnabled(UserHandle.getUserId(i2), 2) || !isCameraPermissionGranted(str, i, i2))) {
            return false;
        }
        if (usbDevice.getHasAudioCapture() && this.mSensorPrivacyMgrInternal.isSensorPrivacyEnabled(UserHandle.getUserId(i2), 1)) {
            return false;
        }
        synchronized (this.mLock) {
            if (i2 != 1000) {
                if (!this.mDisablePermissionDialogs) {
                    SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mDevicePersistentPermissionMap.get(new DeviceFilter(usbDevice));
                    if (sparseBooleanArray != null && (indexOfKey = sparseBooleanArray.indexOfKey(i2)) >= 0) {
                        return sparseBooleanArray.valueAt(indexOfKey);
                    }
                    SparseBooleanArray sparseBooleanArray2 = (SparseBooleanArray) this.mDevicePermissionMap.get(usbDevice.getDeviceName());
                    if (sparseBooleanArray2 == null) {
                        return false;
                    }
                    return sparseBooleanArray2.get(i2);
                }
            }
            return true;
        }
    }

    public boolean hasPermission(UsbAccessory usbAccessory, int i, int i2) {
        int indexOfKey;
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return false;
        }
        synchronized (this.mLock) {
            if (i2 != 1000) {
                if (!this.mDisablePermissionDialogs && this.mContext.checkPermission("android.permission.MANAGE_USB", i, i2) != 0) {
                    SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mAccessoryPersistentPermissionMap.get(new AccessoryFilter(usbAccessory));
                    if (sparseBooleanArray != null && (indexOfKey = sparseBooleanArray.indexOfKey(i2)) >= 0) {
                        return sparseBooleanArray.valueAt(indexOfKey);
                    }
                    SparseBooleanArray sparseBooleanArray2 = (SparseBooleanArray) this.mAccessoryPermissionMap.get(usbAccessory);
                    if (sparseBooleanArray2 == null) {
                        return false;
                    }
                    return sparseBooleanArray2.get(i2);
                }
            }
            return true;
        }
    }

    public void setDevicePersistentPermission(UsbDevice usbDevice, int i, boolean z) {
        DeviceFilter deviceFilter = new DeviceFilter(usbDevice);
        synchronized (this.mLock) {
            SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mDevicePersistentPermissionMap.get(deviceFilter);
            if (sparseBooleanArray == null) {
                sparseBooleanArray = new SparseBooleanArray();
                this.mDevicePersistentPermissionMap.put(deviceFilter, sparseBooleanArray);
            }
            int indexOfKey = sparseBooleanArray.indexOfKey(i);
            boolean z2 = true;
            if (indexOfKey >= 0) {
                if (sparseBooleanArray.valueAt(indexOfKey) == z) {
                    z2 = false;
                }
                sparseBooleanArray.setValueAt(indexOfKey, z);
            } else {
                sparseBooleanArray.put(i, z);
            }
            if (z2) {
                scheduleWritePermissionsLocked();
            }
        }
    }

    public void setAccessoryPersistentPermission(UsbAccessory usbAccessory, int i, boolean z) {
        AccessoryFilter accessoryFilter = new AccessoryFilter(usbAccessory);
        synchronized (this.mLock) {
            SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mAccessoryPersistentPermissionMap.get(accessoryFilter);
            if (sparseBooleanArray == null) {
                sparseBooleanArray = new SparseBooleanArray();
                this.mAccessoryPersistentPermissionMap.put(accessoryFilter, sparseBooleanArray);
            }
            int indexOfKey = sparseBooleanArray.indexOfKey(i);
            boolean z2 = true;
            if (indexOfKey >= 0) {
                if (sparseBooleanArray.valueAt(indexOfKey) == z) {
                    z2 = false;
                }
                sparseBooleanArray.setValueAt(indexOfKey, z);
            } else {
                sparseBooleanArray.put(i, z);
            }
            if (z2) {
                scheduleWritePermissionsLocked();
            }
        }
    }

    public final void readPermission(XmlPullParser xmlPullParser) {
        try {
            int readIntAttribute = XmlUtils.readIntAttribute(xmlPullParser, "uid");
            String attributeValue = xmlPullParser.getAttributeValue(null, "granted");
            if (attributeValue != null) {
                Boolean bool = Boolean.TRUE;
                if (attributeValue.equals(bool.toString()) || attributeValue.equals(Boolean.FALSE.toString())) {
                    boolean equals = attributeValue.equals(bool.toString());
                    XmlUtils.nextElement(xmlPullParser);
                    if ("usb-device".equals(xmlPullParser.getName())) {
                        DeviceFilter read = DeviceFilter.read(xmlPullParser);
                        int indexOfKey = this.mDevicePersistentPermissionMap.indexOfKey(read);
                        if (indexOfKey >= 0) {
                            ((SparseBooleanArray) this.mDevicePersistentPermissionMap.valueAt(indexOfKey)).put(readIntAttribute, equals);
                            return;
                        }
                        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
                        this.mDevicePersistentPermissionMap.put(read, sparseBooleanArray);
                        sparseBooleanArray.put(readIntAttribute, equals);
                        return;
                    }
                    if ("usb-accessory".equals(xmlPullParser.getName())) {
                        AccessoryFilter read2 = AccessoryFilter.read(xmlPullParser);
                        int indexOfKey2 = this.mAccessoryPersistentPermissionMap.indexOfKey(read2);
                        if (indexOfKey2 >= 0) {
                            ((SparseBooleanArray) this.mAccessoryPersistentPermissionMap.valueAt(indexOfKey2)).put(readIntAttribute, equals);
                            return;
                        }
                        SparseBooleanArray sparseBooleanArray2 = new SparseBooleanArray();
                        this.mAccessoryPersistentPermissionMap.put(read2, sparseBooleanArray2);
                        sparseBooleanArray2.put(readIntAttribute, equals);
                        return;
                    }
                    return;
                }
            }
            Slog.e(TAG, "error reading usb permission granted state");
            XmlUtils.skipCurrentTag(xmlPullParser);
        } catch (NumberFormatException e) {
            Slog.e(TAG, "error reading usb permission uid", e);
            XmlUtils.skipCurrentTag(xmlPullParser);
        }
    }

    public final void readPermissionsLocked() {
        this.mDevicePersistentPermissionMap.clear();
        this.mAccessoryPersistentPermissionMap.clear();
        try {
            FileInputStream openRead = this.mPermissionsFile.openRead();
            try {
                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                XmlUtils.nextElement(resolvePullParser);
                while (resolvePullParser.getEventType() != 1) {
                    if ("permission".equals(resolvePullParser.getName())) {
                        readPermission(resolvePullParser);
                    } else {
                        XmlUtils.nextElement(resolvePullParser);
                    }
                }
                if (openRead != null) {
                    openRead.close();
                }
            } catch (Throwable th) {
                if (openRead != null) {
                    try {
                        openRead.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException unused) {
        } catch (Exception e) {
            Slog.e(TAG, "error reading usb permissions file, deleting to start fresh", e);
            this.mPermissionsFile.delete();
        }
    }

    public final void scheduleWritePermissionsLocked() {
        if (this.mIsCopyPermissionsScheduled) {
            return;
        }
        this.mIsCopyPermissionsScheduled = true;
        AsyncTask.execute(new Runnable() { // from class: com.android.server.usb.UsbUserPermissionManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                UsbUserPermissionManager.this.lambda$scheduleWritePermissionsLocked$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleWritePermissionsLocked$0() {
        int size;
        DeviceFilter[] deviceFilterArr;
        int[][] iArr;
        boolean[][] zArr;
        int i;
        int size2;
        AccessoryFilter[] accessoryFilterArr;
        int[][] iArr2;
        FileOutputStream startWrite;
        synchronized (this.mLock) {
            size = this.mDevicePersistentPermissionMap.size();
            deviceFilterArr = new DeviceFilter[size];
            iArr = new int[size][];
            zArr = new boolean[size][];
            i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                deviceFilterArr[i2] = new DeviceFilter((DeviceFilter) this.mDevicePersistentPermissionMap.keyAt(i2));
                SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mDevicePersistentPermissionMap.valueAt(i2);
                int size3 = sparseBooleanArray.size();
                iArr[i2] = new int[size3];
                zArr[i2] = new boolean[size3];
                for (int i3 = 0; i3 < size3; i3++) {
                    iArr[i2][i3] = sparseBooleanArray.keyAt(i3);
                    zArr[i2][i3] = sparseBooleanArray.valueAt(i3);
                }
            }
            size2 = this.mAccessoryPersistentPermissionMap.size();
            accessoryFilterArr = new AccessoryFilter[size2];
            iArr2 = new int[size2][];
            boolean[][] zArr2 = new boolean[size2][];
            for (int i4 = 0; i4 < size2; i4++) {
                accessoryFilterArr[i4] = new AccessoryFilter((AccessoryFilter) this.mAccessoryPersistentPermissionMap.keyAt(i4));
                SparseBooleanArray sparseBooleanArray2 = (SparseBooleanArray) this.mAccessoryPersistentPermissionMap.valueAt(i4);
                int size4 = sparseBooleanArray2.size();
                iArr2[i4] = new int[size4];
                zArr2[i4] = new boolean[size4];
                for (int i5 = 0; i5 < size4; i5++) {
                    iArr2[i4][i5] = sparseBooleanArray2.keyAt(i5);
                    zArr2[i4][i5] = sparseBooleanArray2.valueAt(i5);
                }
            }
            this.mIsCopyPermissionsScheduled = false;
        }
        synchronized (this.mPermissionsFile) {
            FileOutputStream fileOutputStream = null;
            try {
                startWrite = this.mPermissionsFile.startWrite();
            } catch (IOException e) {
                e = e;
            }
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
                this.mPermissionsFile.finishWrite(startWrite);
            } catch (IOException e2) {
                e = e2;
                fileOutputStream = startWrite;
                Slog.e(TAG, "Failed to write permissions", e);
                if (fileOutputStream != null) {
                    this.mPermissionsFile.failWrite(fileOutputStream);
                }
            }
        }
    }

    public void requestPermissionDialog(UsbDevice usbDevice, UsbAccessory usbAccessory, boolean z, String str, int i, Context context, PendingIntent pendingIntent) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent = new Intent();
                if (usbDevice != null) {
                    intent.putExtra("device", usbDevice);
                } else {
                    intent.putExtra("accessory", usbAccessory);
                }
                intent.putExtra("android.intent.extra.INTENT", pendingIntent);
                intent.putExtra("android.intent.extra.UID", i);
                intent.putExtra("android.hardware.usb.extra.CAN_BE_DEFAULT", z);
                intent.putExtra("android.hardware.usb.extra.PACKAGE", str);
                intent.setComponent(ComponentName.unflattenFromString(context.getResources().getString(R.string.ext_media_unmount_action)));
                intent.addFlags(268435456);
                context.startActivityAsUser(intent, this.mUser);
            } catch (ActivityNotFoundException unused) {
                Slog.e(TAG, "unable to start UsbPermissionActivity");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
        long j2;
        long j3;
        long j4;
        long start = dualDumpOutputStream.start(str, j);
        synchronized (this.mLock) {
            dualDumpOutputStream.write("user_id", 1120986464257L, this.mUser.getIdentifier());
            int size = this.mDevicePermissionMap.size();
            int i = 0;
            while (true) {
                j2 = 1138166333441L;
                if (i >= size) {
                    break;
                }
                String str2 = (String) this.mDevicePermissionMap.keyAt(i);
                long start2 = dualDumpOutputStream.start("device_permissions", 2246267895810L);
                dualDumpOutputStream.write("device_name", 1138166333441L, str2);
                SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mDevicePermissionMap.valueAt(i);
                int size2 = sparseBooleanArray.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    dualDumpOutputStream.write("uids", 2220498092034L, sparseBooleanArray.keyAt(i2));
                }
                dualDumpOutputStream.end(start2);
                i++;
            }
            int size3 = this.mAccessoryPermissionMap.size();
            int i3 = 0;
            while (i3 < size3) {
                UsbAccessory usbAccessory = (UsbAccessory) this.mAccessoryPermissionMap.keyAt(i3);
                long start3 = dualDumpOutputStream.start("accessory_permissions", 2246267895811L);
                dualDumpOutputStream.write("accessory_description", j2, usbAccessory.getDescription());
                SparseBooleanArray sparseBooleanArray2 = (SparseBooleanArray) this.mAccessoryPermissionMap.valueAt(i3);
                int size4 = sparseBooleanArray2.size();
                for (int i4 = 0; i4 < size4; i4++) {
                    dualDumpOutputStream.write("uids", 2220498092034L, sparseBooleanArray2.keyAt(i4));
                }
                dualDumpOutputStream.end(start3);
                i3++;
                j2 = 1138166333441L;
            }
            int size5 = this.mDevicePersistentPermissionMap.size();
            int i5 = 0;
            while (true) {
                j3 = 1146756268033L;
                if (i5 >= size5) {
                    break;
                }
                DeviceFilter deviceFilter = (DeviceFilter) this.mDevicePersistentPermissionMap.keyAt(i5);
                long start4 = dualDumpOutputStream.start("device_persistent_permissions", 2246267895812L);
                deviceFilter.dump(dualDumpOutputStream, "device", 1146756268033L);
                SparseBooleanArray sparseBooleanArray3 = (SparseBooleanArray) this.mDevicePersistentPermissionMap.valueAt(i5);
                int size6 = sparseBooleanArray3.size();
                int i6 = 0;
                while (i6 < size6) {
                    long start5 = dualDumpOutputStream.start("uid_permission", 2246267895810L);
                    dualDumpOutputStream.write("uid", 1120986464257L, sparseBooleanArray3.keyAt(i6));
                    dualDumpOutputStream.write("is_granted", 1133871366146L, sparseBooleanArray3.valueAt(i6));
                    dualDumpOutputStream.end(start5);
                    i6++;
                    start = start;
                }
                dualDumpOutputStream.end(start4);
                i5++;
                start = start;
            }
            j4 = start;
            int size7 = this.mAccessoryPersistentPermissionMap.size();
            int i7 = 0;
            while (i7 < size7) {
                AccessoryFilter accessoryFilter = (AccessoryFilter) this.mAccessoryPersistentPermissionMap.keyAt(i7);
                long start6 = dualDumpOutputStream.start("accessory_persistent_permissions", 2246267895813L);
                accessoryFilter.dump(dualDumpOutputStream, "accessory", j3);
                SparseBooleanArray sparseBooleanArray4 = (SparseBooleanArray) this.mAccessoryPersistentPermissionMap.valueAt(i7);
                int size8 = sparseBooleanArray4.size();
                for (int i8 = 0; i8 < size8; i8++) {
                    long start7 = dualDumpOutputStream.start("uid_permission", 2246267895810L);
                    dualDumpOutputStream.write("uid", 1120986464257L, sparseBooleanArray4.keyAt(i8));
                    dualDumpOutputStream.write("is_granted", 1133871366146L, sparseBooleanArray4.valueAt(i8));
                    dualDumpOutputStream.end(start7);
                }
                dualDumpOutputStream.end(start6);
                i7++;
                j3 = 1146756268033L;
            }
        }
        dualDumpOutputStream.end(j4);
    }

    public final boolean isCameraPermissionGranted(String str, int i, int i2) {
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo.uid != i2) {
                Slog.i(TAG, "Package " + str + " does not match caller's uid " + i2);
                return false;
            }
            if (applicationInfo.targetSdkVersion < 28 || -1 != this.mContext.checkPermission("android.permission.CAMERA", i, i2)) {
                return true;
            }
            Slog.i(TAG, "Camera permission required for USB video class devices");
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.i(TAG, "Package not found, likely due to invalid package name!");
            return false;
        }
    }

    public void checkPermission(UsbDevice usbDevice, String str, int i, int i2) {
        if (hasPermission(usbDevice, str, i, i2)) {
            return;
        }
        synchronized (this.mLock) {
            SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mDevicePermissionMap.get(usbDevice.getDeviceName());
            if (sparseBooleanArray == null) {
                Slog.d(TAG, "checkPermission: uidList is null");
            } else {
                Slog.d(TAG, "checkPermission: device=" + usbDevice.getDeviceName() + " uidList=" + sparseBooleanArray.toString() + ", For uid(" + i2 + "):" + sparseBooleanArray.get(i2));
            }
        }
        throw new SecurityException("User has not given " + i2 + "/" + str + " permission to access device " + usbDevice.getDeviceName());
    }

    public void checkPermission(UsbAccessory usbAccessory, int i, int i2) {
        if (hasPermission(usbAccessory, i, i2)) {
            return;
        }
        throw new SecurityException("User has not given " + i2 + " permission to accessory " + usbAccessory);
    }

    public final void requestPermissionDialog(UsbDevice usbDevice, UsbAccessory usbAccessory, boolean z, String str, PendingIntent pendingIntent, int i) {
        try {
            boolean z2 = false;
            if (this.mContext.getPackageManager().getApplicationInfo(str, 0).uid != i) {
                Slog.w(TAG, "package " + str + " does not match caller's uid " + i);
                EventLog.writeEvent(1397638484, "180104273", -1, "");
                z2 = true;
            }
            if (z2) {
                throw new IllegalArgumentException("package " + str + " not found");
            }
            requestPermissionDialog(usbDevice, usbAccessory, z, str, i, this.mContext, pendingIntent);
        } catch (PackageManager.NameNotFoundException unused) {
            throw new IllegalArgumentException("package " + str + " not found");
        }
    }

    public void requestPermission(UsbDevice usbDevice, String str, PendingIntent pendingIntent, int i, int i2) {
        String str2 = TAG;
        Slog.d(str2, "requestPermission: device=" + usbDevice.getDeviceName() + " packageName=" + str + " pi=" + pendingIntent + " pid=" + i + " uid=" + i2);
        if (isUsbDevicePermittedForPackageByMdm(i2, usbDevice, str) && !hasPermission(usbDevice, str, i, i2)) {
            Slog.d(str2, "Policy forcely granted usb device permission for package=" + str + "(" + i2 + ")");
            grantDevicePermission(usbDevice, i2);
        }
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return;
        }
        Intent intent = new Intent();
        if (hasPermission(usbDevice, str, i, i2)) {
            intent.putExtra("device", usbDevice);
            intent.putExtra("permission", true);
            try {
                pendingIntent.send(this.mContext, 0, intent);
                return;
            } catch (PendingIntent.CanceledException unused) {
                return;
            }
        }
        if (usbDevice.getHasVideoCapture() && !isCameraPermissionGranted(str, i, i2)) {
            intent.putExtra("device", usbDevice);
            intent.putExtra("permission", false);
            try {
                pendingIntent.send(this.mContext, 0, intent);
                return;
            } catch (PendingIntent.CanceledException unused2) {
                return;
            }
        }
        requestPermissionDialog(usbDevice, null, this.mUsbUserSettingsManager.canBeDefault(usbDevice, str), str, pendingIntent, i2);
    }

    public void requestPermission(UsbAccessory usbAccessory, String str, PendingIntent pendingIntent, int i, int i2) {
        Slog.d(TAG, "requestPermission: accessory=" + usbAccessory + " packageName=" + str + " pi=" + pendingIntent + " pid=" + i + " uid=" + i2);
        if (hasPermission(usbAccessory, i, i2)) {
            Intent intent = new Intent();
            intent.putExtra("accessory", usbAccessory);
            intent.putExtra("permission", true);
            try {
                pendingIntent.send(this.mContext, 0, intent);
                return;
            } catch (PendingIntent.CanceledException unused) {
                return;
            }
        }
        requestPermissionDialog(null, usbAccessory, this.mUsbUserSettingsManager.canBeDefault(usbAccessory, str), str, pendingIntent, i2);
    }

    public final boolean isUsbDevicePermittedForPackageByMdm(int i, UsbDevice usbDevice, String str) {
        IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        if (asInterface == null) {
            return false;
        }
        try {
            if (!asInterface.isUsbDevicePermittedForPackage(i, usbDevice, str)) {
                return false;
            }
            Slog.d(TAG, "Allowed by MDM policy");
            return true;
        } catch (Exception e) {
            Slog.w(TAG, "isUsbDevicePermittedForPackageByMdm " + e);
            return false;
        }
    }
}
