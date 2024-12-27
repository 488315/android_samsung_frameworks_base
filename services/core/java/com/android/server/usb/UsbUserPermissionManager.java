package com.android.server.usb;

import android.R;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
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
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.pm.PersonaServiceHelper;

import com.samsung.android.knox.application.IApplicationPolicy;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class UsbUserPermissionManager {
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

    public UsbUserPermissionManager(
            Context context, UsbUserSettingsManager usbUserSettingsManager) {
        Object obj = new Object();
        this.mLock = obj;
        this.mContext = context;
        UserHandle user = context.getUser();
        this.mUser = user;
        this.mUsbUserSettingsManager = usbUserSettingsManager;
        this.mSensorPrivacyMgrInternal =
                (SensorPrivacyManagerInternal)
                        LocalServices.getService(SensorPrivacyManagerInternal.class);
        this.mDisablePermissionDialogs =
                context.getResources().getBoolean(R.bool.config_displayColorFadeDisabled);
        this.mPermissionsFile =
                new AtomicFile(
                        new File(
                                Environment.getUserSystemDirectory(user.getIdentifier()),
                                "usb_permissions.xml"),
                        "usb-permissions");
        synchronized (obj) {
            readPermissionsLocked();
        }
    }

    public final void checkPermission(UsbAccessory usbAccessory, int i, int i2) {
        if (hasPermission(usbAccessory, i, i2)) {
            return;
        }
        throw new SecurityException(
                "User has not given " + i2 + " permission to accessory " + usbAccessory);
    }

    public final void checkPermission(UsbDevice usbDevice, String str, int i, int i2) {
        if (hasPermission(usbDevice, str, i, i2)) {
            return;
        }
        synchronized (this.mLock) {
            try {
                SparseBooleanArray sparseBooleanArray =
                        (SparseBooleanArray)
                                this.mDevicePermissionMap.get(usbDevice.getDeviceName());
                if (sparseBooleanArray == null) {
                    Slog.d("UsbUserPermissionManager", "checkPermission: uidList is null");
                } else {
                    Slog.d(
                            "UsbUserPermissionManager",
                            "checkPermission: device="
                                    + usbDevice.getDeviceName()
                                    + " uidList="
                                    + sparseBooleanArray.toString()
                                    + ", For uid("
                                    + i2
                                    + "):"
                                    + sparseBooleanArray.get(i2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        StringBuilder m =
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        i2, "User has not given ", "/", str, " permission to access device ");
        m.append(usbDevice.getDeviceName());
        throw new SecurityException(m.toString());
    }

    public final void dump(DualDumpOutputStream dualDumpOutputStream) {
        long j;
        long j2;
        long j3;
        long start = dualDumpOutputStream.start("user_permissions", 2246267895809L);
        synchronized (this.mLock) {
            try {
                dualDumpOutputStream.write("user_id", 1120986464257L, this.mUser.getIdentifier());
                int size = this.mDevicePermissionMap.size();
                int i = 0;
                while (true) {
                    j = 1138166333441L;
                    if (i >= size) {
                        break;
                    }
                    String str = (String) this.mDevicePermissionMap.keyAt(i);
                    long start2 = dualDumpOutputStream.start("device_permissions", 2246267895810L);
                    dualDumpOutputStream.write("device_name", 1138166333441L, str);
                    SparseBooleanArray sparseBooleanArray =
                            (SparseBooleanArray) this.mDevicePermissionMap.valueAt(i);
                    int size2 = sparseBooleanArray.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        dualDumpOutputStream.write(
                                "uids", 2220498092034L, sparseBooleanArray.keyAt(i2));
                    }
                    dualDumpOutputStream.end(start2);
                    i++;
                }
                int size3 = this.mAccessoryPermissionMap.size();
                int i3 = 0;
                while (i3 < size3) {
                    UsbAccessory usbAccessory =
                            (UsbAccessory) this.mAccessoryPermissionMap.keyAt(i3);
                    long start3 =
                            dualDumpOutputStream.start("accessory_permissions", 2246267895811L);
                    dualDumpOutputStream.write(
                            "accessory_description", j, usbAccessory.getDescription());
                    SparseBooleanArray sparseBooleanArray2 =
                            (SparseBooleanArray) this.mAccessoryPermissionMap.valueAt(i3);
                    int size4 = sparseBooleanArray2.size();
                    for (int i4 = 0; i4 < size4; i4++) {
                        dualDumpOutputStream.write(
                                "uids", 2220498092034L, sparseBooleanArray2.keyAt(i4));
                    }
                    dualDumpOutputStream.end(start3);
                    i3++;
                    j = 1138166333441L;
                }
                int size5 = this.mDevicePersistentPermissionMap.size();
                int i5 = 0;
                while (true) {
                    j2 = 1146756268033L;
                    if (i5 >= size5) {
                        break;
                    }
                    DeviceFilter deviceFilter =
                            (DeviceFilter) this.mDevicePersistentPermissionMap.keyAt(i5);
                    long start4 =
                            dualDumpOutputStream.start(
                                    "device_persistent_permissions", 2246267895812L);
                    deviceFilter.dump(dualDumpOutputStream, "device", 1146756268033L);
                    SparseBooleanArray sparseBooleanArray3 =
                            (SparseBooleanArray) this.mDevicePersistentPermissionMap.valueAt(i5);
                    int size6 = sparseBooleanArray3.size();
                    int i6 = 0;
                    while (i6 < size6) {
                        long start5 = dualDumpOutputStream.start("uid_permission", 2246267895810L);
                        dualDumpOutputStream.write(
                                "uid", 1120986464257L, sparseBooleanArray3.keyAt(i6));
                        dualDumpOutputStream.write(
                                "is_granted", 1133871366146L, sparseBooleanArray3.valueAt(i6));
                        dualDumpOutputStream.end(start5);
                        i6++;
                        start = start;
                    }
                    dualDumpOutputStream.end(start4);
                    i5++;
                    start = start;
                }
                j3 = start;
                int size7 = this.mAccessoryPersistentPermissionMap.size();
                int i7 = 0;
                while (i7 < size7) {
                    AccessoryFilter accessoryFilter =
                            (AccessoryFilter) this.mAccessoryPersistentPermissionMap.keyAt(i7);
                    long start6 =
                            dualDumpOutputStream.start(
                                    "accessory_persistent_permissions", 2246267895813L);
                    accessoryFilter.dump(dualDumpOutputStream, "accessory", j2);
                    SparseBooleanArray sparseBooleanArray4 =
                            (SparseBooleanArray) this.mAccessoryPersistentPermissionMap.valueAt(i7);
                    int size8 = sparseBooleanArray4.size();
                    for (int i8 = 0; i8 < size8; i8++) {
                        long start7 = dualDumpOutputStream.start("uid_permission", 2246267895810L);
                        dualDumpOutputStream.write(
                                "uid", 1120986464257L, sparseBooleanArray4.keyAt(i8));
                        dualDumpOutputStream.write(
                                "is_granted", 1133871366146L, sparseBooleanArray4.valueAt(i8));
                        dualDumpOutputStream.end(start7);
                    }
                    dualDumpOutputStream.end(start6);
                    i7++;
                    j2 = 1146756268033L;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        dualDumpOutputStream.end(j3);
    }

    public final void grantAccessoryPermission(UsbAccessory usbAccessory, int i) {
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return;
        }
        synchronized (this.mLock) {
            try {
                SparseBooleanArray sparseBooleanArray =
                        (SparseBooleanArray) this.mAccessoryPermissionMap.get(usbAccessory);
                if (sparseBooleanArray == null) {
                    sparseBooleanArray = new SparseBooleanArray(1);
                    this.mAccessoryPermissionMap.put(usbAccessory, sparseBooleanArray);
                }
                sparseBooleanArray.put(i, true);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void grantDevicePermission(UsbDevice usbDevice, int i) {
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return;
        }
        Slog.d(
                "UsbUserPermissionManager",
                "grantDevicePermission: device=" + usbDevice.getDeviceName() + " For uid=" + i);
        synchronized (this.mLock) {
            try {
                String deviceName = usbDevice.getDeviceName();
                SparseBooleanArray sparseBooleanArray =
                        (SparseBooleanArray) this.mDevicePermissionMap.get(deviceName);
                if (sparseBooleanArray == null) {
                    sparseBooleanArray = new SparseBooleanArray(1);
                    this.mDevicePermissionMap.put(deviceName, sparseBooleanArray);
                }
                sparseBooleanArray.put(i, true);
                SparseBooleanArray sparseBooleanArray2 =
                        (SparseBooleanArray)
                                this.mDevicePermissionMap.get(usbDevice.getDeviceName());
                if (sparseBooleanArray2 == null) {
                    Slog.d("UsbUserPermissionManager", "grantDevicePermission: uidList is null");
                } else if (!sparseBooleanArray2.get(i)) {
                    Slog.d("UsbUserPermissionManager", "grantDevicePermission: uidList put error");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean hasPermission(UsbAccessory usbAccessory, int i, int i2) {
        int indexOfKey;
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return false;
        }
        synchronized (this.mLock) {
            if (i2 != 1000) {
                try {
                    if (!this.mDisablePermissionDialogs
                            && this.mContext.checkPermission("android.permission.MANAGE_USB", i, i2)
                                    != 0) {
                        SparseBooleanArray sparseBooleanArray =
                                (SparseBooleanArray)
                                        this.mAccessoryPersistentPermissionMap.get(
                                                new AccessoryFilter(usbAccessory));
                        if (sparseBooleanArray != null
                                && (indexOfKey = sparseBooleanArray.indexOfKey(i2)) >= 0) {
                            return sparseBooleanArray.valueAt(indexOfKey);
                        }
                        SparseBooleanArray sparseBooleanArray2 =
                                (SparseBooleanArray) this.mAccessoryPermissionMap.get(usbAccessory);
                        if (sparseBooleanArray2 == null) {
                            return false;
                        }
                        return sparseBooleanArray2.get(i2);
                    }
                } finally {
                }
            }
            return true;
        }
    }

    public final boolean hasPermission(UsbDevice usbDevice, String str, int i, int i2) {
        int indexOfKey;
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return false;
        }
        if (usbDevice.getHasVideoCapture()
                && (this.mSensorPrivacyMgrInternal.isSensorPrivacyEnabled(
                                UserHandle.getUserId(i2), 2)
                        || !isCameraPermissionGranted(i, i2, str))) {
            return false;
        }
        if (usbDevice.getHasAudioCapture()
                && this.mSensorPrivacyMgrInternal.isSensorPrivacyEnabled(
                        UserHandle.getUserId(i2), 1)) {
            return false;
        }
        synchronized (this.mLock) {
            if (i2 != 1000) {
                try {
                    if (!this.mDisablePermissionDialogs) {
                        SparseBooleanArray sparseBooleanArray =
                                (SparseBooleanArray)
                                        this.mDevicePersistentPermissionMap.get(
                                                new DeviceFilter(usbDevice));
                        if (sparseBooleanArray != null
                                && (indexOfKey = sparseBooleanArray.indexOfKey(i2)) >= 0) {
                            return sparseBooleanArray.valueAt(indexOfKey);
                        }
                        SparseBooleanArray sparseBooleanArray2 =
                                (SparseBooleanArray)
                                        this.mDevicePermissionMap.get(usbDevice.getDeviceName());
                        if (sparseBooleanArray2 == null) {
                            return false;
                        }
                        return sparseBooleanArray2.get(i2);
                    }
                } finally {
                }
            }
            return true;
        }
    }

    public final boolean isCameraPermissionGranted(int i, int i2, String str) {
        try {
            ApplicationInfo applicationInfo =
                    this.mContext.getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo.uid == i2) {
                if (applicationInfo.targetSdkVersion < 28
                        || -1
                                != this.mContext.checkPermission(
                                        "android.permission.CAMERA", i, i2)) {
                    return true;
                }
                Slog.i(
                        "UsbUserPermissionManager",
                        "Camera permission required for USB video class devices");
                return false;
            }
            Slog.i(
                    "UsbUserPermissionManager",
                    "Package " + str + " does not match caller's uid " + i2);
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.i(
                    "UsbUserPermissionManager",
                    "Package not found, likely due to invalid package name!");
            return false;
        }
    }

    public final void readPermission(XmlPullParser xmlPullParser) {
        try {
            int readIntAttribute = XmlUtils.readIntAttribute(xmlPullParser, "uid");
            String attributeValue = xmlPullParser.getAttributeValue(null, "granted");
            if (attributeValue != null) {
                Boolean bool = Boolean.TRUE;
                if (attributeValue.equals(bool.toString())
                        || attributeValue.equals(Boolean.FALSE.toString())) {
                    boolean equals = attributeValue.equals(bool.toString());
                    XmlUtils.nextElement(xmlPullParser);
                    if ("usb-device".equals(xmlPullParser.getName())) {
                        DeviceFilter read = DeviceFilter.read(xmlPullParser);
                        int indexOfKey = this.mDevicePersistentPermissionMap.indexOfKey(read);
                        if (indexOfKey >= 0) {
                            ((SparseBooleanArray)
                                            this.mDevicePersistentPermissionMap.valueAt(indexOfKey))
                                    .put(readIntAttribute, equals);
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
                            ((SparseBooleanArray)
                                            this.mAccessoryPersistentPermissionMap.valueAt(
                                                    indexOfKey2))
                                    .put(readIntAttribute, equals);
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
            Slog.e("UsbUserPermissionManager", "error reading usb permission granted state");
            XmlUtils.skipCurrentTag(xmlPullParser);
        } catch (NumberFormatException e) {
            Slog.e("UsbUserPermissionManager", "error reading usb permission uid", e);
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
            Slog.e(
                    "UsbUserPermissionManager",
                    "error reading usb permissions file, deleting to start fresh",
                    e);
            this.mPermissionsFile.delete();
        }
    }

    public final void requestPermission(
            UsbAccessory usbAccessory, String str, PendingIntent pendingIntent, int i, int i2) {
        ActivityInfo[] activityInfoArr;
        XmlResourceParser loadXmlMetaData;
        Slog.d(
                "UsbUserPermissionManager",
                "requestPermission: accessory="
                        + usbAccessory
                        + " packageName="
                        + str
                        + " pi="
                        + pendingIntent
                        + " pid="
                        + i
                        + " uid="
                        + i2);
        boolean z = true;
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
        UsbUserSettingsManager usbUserSettingsManager = this.mUsbUserSettingsManager;
        usbUserSettingsManager.getClass();
        try {
            activityInfoArr =
                    usbUserSettingsManager.mPackageManager.getPackageInfo(str, 129).activities;
        } catch (PackageManager.NameNotFoundException unused2) {
            activityInfoArr = null;
        }
        ActivityInfo[] activityInfoArr2 = activityInfoArr;
        if (activityInfoArr2 != null) {
            loop0:
            for (ActivityInfo activityInfo : activityInfoArr2) {
                try {
                    loadXmlMetaData =
                            activityInfo.loadXmlMetaData(
                                    usbUserSettingsManager.mPackageManager,
                                    "android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
                } catch (Exception e) {
                    Slog.w(
                            "UsbUserSettingsManager",
                            "Unable to load component info " + activityInfo.toString(),
                            e);
                }
                if (loadXmlMetaData != null) {
                    try {
                        XmlUtils.nextElement(loadXmlMetaData);
                        while (loadXmlMetaData.getEventType() != 1) {
                            if ("usb-accessory".equals(loadXmlMetaData.getName())
                                    && AccessoryFilter.read(loadXmlMetaData)
                                            .matches(usbAccessory)) {
                                loadXmlMetaData.close();
                                break loop0;
                            }
                            XmlUtils.nextElement(loadXmlMetaData);
                        }
                    } finally {
                    }
                } else if (loadXmlMetaData == null) {
                }
                loadXmlMetaData.close();
            }
        }
        z = false;
        requestPermissionDialog(null, usbAccessory, z, str, pendingIntent, i2);
    }

    public final void requestPermission(
            UsbDevice usbDevice, String str, PendingIntent pendingIntent, int i, int i2) {
        ActivityInfo[] activityInfoArr;
        boolean z;
        XmlResourceParser loadXmlMetaData;
        Slog.d(
                "UsbUserPermissionManager",
                "requestPermission: device="
                        + usbDevice.getDeviceName()
                        + " packageName="
                        + str
                        + " pi="
                        + pendingIntent
                        + " pid="
                        + i
                        + " uid="
                        + i2);
        IApplicationPolicy asInterface =
                IApplicationPolicy.Stub.asInterface(
                        ServiceManager.getService("application_policy"));
        if (asInterface != null) {
            try {
                if (asInterface.isUsbDevicePermittedForPackage(i2, usbDevice, str)) {
                    Slog.d("UsbUserPermissionManager", "Allowed by MDM policy");
                    if (!hasPermission(usbDevice, str, i, i2)) {
                        Slog.d(
                                "UsbUserPermissionManager",
                                AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                                        i2,
                                        "Policy forcely granted usb device permission for package=",
                                        str,
                                        "(",
                                        ")"));
                        grantDevicePermission(usbDevice, i2);
                    }
                }
            } catch (Exception e) {
                Slog.w("UsbUserPermissionManager", "isUsbDevicePermittedForPackageByMdm " + e);
            }
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
        if (usbDevice.getHasVideoCapture() && !isCameraPermissionGranted(i, i2, str)) {
            intent.putExtra("device", usbDevice);
            intent.putExtra("permission", false);
            try {
                pendingIntent.send(this.mContext, 0, intent);
                return;
            } catch (PendingIntent.CanceledException unused2) {
                return;
            }
        }
        UsbUserSettingsManager usbUserSettingsManager = this.mUsbUserSettingsManager;
        usbUserSettingsManager.getClass();
        try {
            activityInfoArr =
                    usbUserSettingsManager.mPackageManager.getPackageInfo(str, 129).activities;
        } catch (PackageManager.NameNotFoundException unused3) {
            activityInfoArr = null;
        }
        ActivityInfo[] activityInfoArr2 = activityInfoArr;
        if (activityInfoArr2 != null) {
            loop0:
            for (ActivityInfo activityInfo : activityInfoArr2) {
                try {
                    loadXmlMetaData =
                            activityInfo.loadXmlMetaData(
                                    usbUserSettingsManager.mPackageManager,
                                    "android.hardware.usb.action.USB_DEVICE_ATTACHED");
                } catch (Exception e2) {
                    Slog.w(
                            "UsbUserSettingsManager",
                            "Unable to load component info " + activityInfo.toString(),
                            e2);
                }
                if (loadXmlMetaData != null) {
                    try {
                        XmlUtils.nextElement(loadXmlMetaData);
                        while (loadXmlMetaData.getEventType() != 1) {
                            if ("usb-device".equals(loadXmlMetaData.getName())
                                    && DeviceFilter.read(loadXmlMetaData).matches(usbDevice)) {
                                loadXmlMetaData.close();
                                z = true;
                                break loop0;
                            }
                            XmlUtils.nextElement(loadXmlMetaData);
                        }
                    } finally {
                    }
                } else if (loadXmlMetaData == null) {
                }
                loadXmlMetaData.close();
            }
        }
        z = false;
        requestPermissionDialog(usbDevice, null, z, str, pendingIntent, i2);
    }

    public final void requestPermissionDialog(
            UsbDevice usbDevice,
            UsbAccessory usbAccessory,
            boolean z,
            String str,
            PendingIntent pendingIntent,
            int i) {
        try {
            if (this.mContext.getPackageManager().getApplicationInfo(str, 0).uid != i) {
                Slog.w(
                        "UsbUserPermissionManager",
                        "package " + str + " does not match caller's uid " + i);
                EventLog.writeEvent(1397638484, "180104273", -1, "");
                throw new IllegalArgumentException(
                        XmlUtils$$ExternalSyntheticOutline0.m("package ", str, " not found"));
            }
            Context context = this.mContext;
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
                    intent.setComponent(
                            ComponentName.unflattenFromString(
                                    context.getResources()
                                            .getString(R.string.face_error_canceled)));
                    intent.addFlags(268435456);
                    context.startActivityAsUser(intent, this.mUser);
                } catch (ActivityNotFoundException unused) {
                    Slog.e("UsbUserPermissionManager", "unable to start UsbPermissionActivity");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m("package ", str, " not found"));
        }
    }

    public final void setAccessoryPersistentPermission(
            UsbAccessory usbAccessory, int i, boolean z) {
        boolean z2;
        AccessoryFilter accessoryFilter = new AccessoryFilter(usbAccessory);
        synchronized (this.mLock) {
            try {
                SparseBooleanArray sparseBooleanArray =
                        (SparseBooleanArray)
                                this.mAccessoryPersistentPermissionMap.get(accessoryFilter);
                if (sparseBooleanArray == null) {
                    sparseBooleanArray = new SparseBooleanArray();
                    this.mAccessoryPersistentPermissionMap.put(accessoryFilter, sparseBooleanArray);
                }
                int indexOfKey = sparseBooleanArray.indexOfKey(i);
                if (indexOfKey >= 0) {
                    z2 = sparseBooleanArray.valueAt(indexOfKey) != z;
                    sparseBooleanArray.setValueAt(indexOfKey, z);
                } else {
                    sparseBooleanArray.put(i, z);
                    z2 = true;
                }
                if (z2 && !this.mIsCopyPermissionsScheduled) {
                    this.mIsCopyPermissionsScheduled = true;
                    AsyncTask.execute(new UsbUserPermissionManager$$ExternalSyntheticLambda0(this));
                }
            } finally {
            }
        }
    }

    public final void setDevicePersistentPermission(UsbDevice usbDevice, int i, boolean z) {
        boolean z2;
        DeviceFilter deviceFilter = new DeviceFilter(usbDevice);
        synchronized (this.mLock) {
            try {
                SparseBooleanArray sparseBooleanArray =
                        (SparseBooleanArray) this.mDevicePersistentPermissionMap.get(deviceFilter);
                if (sparseBooleanArray == null) {
                    sparseBooleanArray = new SparseBooleanArray();
                    this.mDevicePersistentPermissionMap.put(deviceFilter, sparseBooleanArray);
                }
                int indexOfKey = sparseBooleanArray.indexOfKey(i);
                if (indexOfKey >= 0) {
                    z2 = sparseBooleanArray.valueAt(indexOfKey) != z;
                    sparseBooleanArray.setValueAt(indexOfKey, z);
                } else {
                    sparseBooleanArray.put(i, z);
                    z2 = true;
                }
                if (z2 && !this.mIsCopyPermissionsScheduled) {
                    this.mIsCopyPermissionsScheduled = true;
                    AsyncTask.execute(new UsbUserPermissionManager$$ExternalSyntheticLambda0(this));
                }
            } finally {
            }
        }
    }
}
