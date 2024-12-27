package com.android.server.usb;

import android.R;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.XmlResourceParser;
import android.hardware.usb.AccessoryFilter;
import android.hardware.usb.DeviceFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.util.sysfwutil.Slog;
import com.android.internal.app.IntentForwarderActivity;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.usb.UsbProfileGroupSettingsManager;
import com.android.server.utils.EventLogger;
import com.samsung.android.knoxguard.service.SystemIntentProcessor;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbProfileGroupSettingsManager {
    public static EventLogger sEventLogger;
    public static final File sSingleUserSettingsFile = new File("/data/system/usb_device_manager.xml");
    public final Context mContext;
    public final boolean mDisablePermissionDialogs;
    public boolean mIsWriteSettingsScheduled;
    public final Object mLock;
    public final PackageManager mPackageManager;
    public final MyPackageMonitor mPackageMonitor;
    public final UserHandle mParentUser;
    public final AtomicFile mSettingsFile;
    public final UsbSettingsManager mSettingsManager;
    public final UsbHandlerManager mUsbHandlerManager;
    public final UserManager mUserManager;
    public final HashMap mDevicePreferenceMap = new HashMap();
    public final ArrayMap mDevicePreferenceDeniedMap = new ArrayMap();
    public final HashMap mAccessoryPreferenceMap = new HashMap();
    public final ArrayMap mAccessoryPreferenceDeniedMap = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyPackageMonitor extends PackageMonitor {
        public MyPackageMonitor() {
        }

        public final void onPackageAdded(String str, int i) {
            UsbProfileGroupSettingsManager usbProfileGroupSettingsManager = UsbProfileGroupSettingsManager.this;
            if (usbProfileGroupSettingsManager.mUserManager.isSameProfileGroup(usbProfileGroupSettingsManager.mParentUser.getIdentifier(), UserHandle.getUserId(i))) {
                UsbProfileGroupSettingsManager usbProfileGroupSettingsManager2 = UsbProfileGroupSettingsManager.this;
                UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i);
                UserPackage userPackage = new UserPackage(str, userHandleForUid);
                synchronized (usbProfileGroupSettingsManager2.mLock) {
                    try {
                        try {
                            ActivityInfo[] activityInfoArr = usbProfileGroupSettingsManager2.mPackageManager.getPackageInfoAsUser(str, 129, userHandleForUid.getIdentifier()).activities;
                            if (activityInfoArr == null) {
                                return;
                            }
                            boolean z = false;
                            for (int i2 = 0; i2 < activityInfoArr.length; i2++) {
                                if (usbProfileGroupSettingsManager2.handlePackageAddedLocked(userPackage, activityInfoArr[i2], "android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                                    z = true;
                                }
                                if (usbProfileGroupSettingsManager2.handlePackageAddedLocked(userPackage, activityInfoArr[i2], "android.hardware.usb.action.USB_ACCESSORY_ATTACHED")) {
                                    z = true;
                                }
                            }
                            if (z) {
                                usbProfileGroupSettingsManager2.scheduleWriteSettingsLocked();
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            Slog.e("UsbProfileGroupSettingsManager", "handlePackageUpdate could not find package " + userPackage, e);
                        }
                    } finally {
                    }
                }
            }
        }

        public final void onPackageRemoved(String str, int i) {
            UsbProfileGroupSettingsManager usbProfileGroupSettingsManager = UsbProfileGroupSettingsManager.this;
            if (usbProfileGroupSettingsManager.mUserManager.isSameProfileGroup(usbProfileGroupSettingsManager.mParentUser.getIdentifier(), UserHandle.getUserId(i))) {
                UsbProfileGroupSettingsManager.this.clearDefaults(str, UserHandle.getUserHandleForUid(i));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserPackage {
        public final String packageName;
        public final UserHandle user;

        public UserPackage(String str, UserHandle userHandle) {
            this.packageName = str;
            this.user = userHandle;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof UserPackage)) {
                return false;
            }
            UserPackage userPackage = (UserPackage) obj;
            return this.user.equals(userPackage.user) && this.packageName.equals(userPackage.packageName);
        }

        public final int hashCode() {
            return this.packageName.hashCode() + (this.user.hashCode() * 31);
        }

        public final String toString() {
            return this.user.getIdentifier() + "/" + this.packageName;
        }
    }

    public UsbProfileGroupSettingsManager(Context context, UserHandle userHandle, UsbSettingsManager usbSettingsManager, UsbHandlerManager usbHandlerManager) {
        Object obj = new Object();
        this.mLock = obj;
        MyPackageMonitor myPackageMonitor = new MyPackageMonitor();
        this.mPackageMonitor = myPackageMonitor;
        Slog.v("UsbProfileGroupSettingsManager", "Creating settings for " + userHandle);
        try {
            context.createPackageContextAsUser("android", 0, userHandle);
            this.mContext = context;
            this.mPackageManager = context.getPackageManager();
            this.mSettingsManager = usbSettingsManager;
            this.mUserManager = (UserManager) context.getSystemService("user");
            this.mParentUser = userHandle;
            this.mSettingsFile = new AtomicFile(new File(Environment.getUserSystemDirectory(userHandle.getIdentifier()), "usb_device_manager.xml"), "usb-state");
            this.mDisablePermissionDialogs = context.getResources().getBoolean(R.bool.config_displayColorFadeDisabled);
            synchronized (obj) {
                try {
                    if (UserHandle.SYSTEM.equals(userHandle)) {
                        upgradeSingleUserLocked();
                    }
                    readSettingsLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
            myPackageMonitor.register(context, (Looper) null, UserHandle.ALL, true);
            this.mUsbHandlerManager = usbHandlerManager;
            sEventLogger = new EventLogger(200, "UsbProfileGroupSettingsManager activity");
        } catch (PackageManager.NameNotFoundException unused) {
            throw new RuntimeException("Missing android package");
        }
    }

    public static ArrayList getAccessoryFilters(PackageManager packageManager, ResolveInfo resolveInfo) {
        ArrayList arrayList;
        XmlResourceParser xmlResourceParser = null;
        ArrayList arrayList2 = null;
        xmlResourceParser = null;
        try {
            try {
                XmlResourceParser loadXmlMetaData = resolveInfo.activityInfo.loadXmlMetaData(packageManager, "android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
                try {
                    if (loadXmlMetaData == null) {
                        Slog.w("UsbProfileGroupSettingsManager", "no meta-data for " + resolveInfo);
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                        }
                        return null;
                    }
                    XmlUtils.nextElement(loadXmlMetaData);
                    while (loadXmlMetaData.getEventType() != 1) {
                        if ("usb-accessory".equals(loadXmlMetaData.getName())) {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList(1);
                            }
                            arrayList2.add(AccessoryFilter.read(loadXmlMetaData));
                        }
                        XmlUtils.nextElement(loadXmlMetaData);
                    }
                    loadXmlMetaData.close();
                    return arrayList2;
                } catch (Exception e) {
                    e = e;
                    xmlResourceParser = loadXmlMetaData;
                    arrayList = null;
                    Slog.w("UsbProfileGroupSettingsManager", "Unable to load component info " + resolveInfo.toString(), e);
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    return arrayList;
                } catch (Throwable th) {
                    th = th;
                    xmlResourceParser = loadXmlMetaData;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
            arrayList = null;
        }
    }

    public static ArrayList getDeviceFilters(PackageManager packageManager, ResolveInfo resolveInfo) {
        ArrayList arrayList;
        XmlResourceParser xmlResourceParser = null;
        ArrayList arrayList2 = null;
        xmlResourceParser = null;
        try {
            try {
                XmlResourceParser loadXmlMetaData = resolveInfo.activityInfo.loadXmlMetaData(packageManager, "android.hardware.usb.action.USB_DEVICE_ATTACHED");
                try {
                    if (loadXmlMetaData == null) {
                        Slog.w("UsbProfileGroupSettingsManager", "no meta-data for " + resolveInfo);
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                        }
                        return null;
                    }
                    XmlUtils.nextElement(loadXmlMetaData);
                    while (loadXmlMetaData.getEventType() != 1) {
                        if ("usb-device".equals(loadXmlMetaData.getName())) {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList(1);
                            }
                            arrayList2.add(DeviceFilter.read(loadXmlMetaData));
                        }
                        XmlUtils.nextElement(loadXmlMetaData);
                    }
                    loadXmlMetaData.close();
                    return arrayList2;
                } catch (Exception e) {
                    e = e;
                    xmlResourceParser = loadXmlMetaData;
                    arrayList = null;
                    Slog.w("UsbProfileGroupSettingsManager", "Unable to load component info " + resolveInfo.toString(), e);
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    return arrayList;
                } catch (Throwable th) {
                    th = th;
                    xmlResourceParser = loadXmlMetaData;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
            arrayList = null;
        }
    }

    public static ArrayList preferHighPriority(ArrayList arrayList) {
        SparseArray sparseArray = new SparseArray();
        SparseIntArray sparseIntArray = new SparseIntArray();
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo = (ResolveInfo) arrayList.get(i);
            if (resolveInfo.getComponentInfo().name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE)) {
                arrayList2.add(resolveInfo);
            } else {
                if (sparseIntArray.indexOfKey(resolveInfo.targetUserId) < 0) {
                    sparseIntArray.put(resolveInfo.targetUserId, Integer.MIN_VALUE);
                    sparseArray.put(resolveInfo.targetUserId, new ArrayList());
                }
                int i2 = sparseIntArray.get(resolveInfo.targetUserId);
                ArrayList arrayList3 = (ArrayList) sparseArray.get(resolveInfo.targetUserId);
                int i3 = resolveInfo.priority;
                if (i3 == i2) {
                    arrayList3.add(resolveInfo);
                } else if (i3 > i2) {
                    sparseIntArray.put(resolveInfo.targetUserId, i3);
                    arrayList3.clear();
                    arrayList3.add(resolveInfo);
                }
            }
        }
        ArrayList arrayList4 = new ArrayList(arrayList2);
        int size2 = sparseArray.size();
        for (int i4 = 0; i4 < size2; i4++) {
            arrayList4.addAll((Collection) sparseArray.valueAt(i4));
        }
        return arrayList4;
    }

    public final void accessoryAttached(UsbAccessory usbAccessory) {
        ArrayList removeForwardIntentIfNotNeeded;
        ActivityInfo defaultActivityLocked;
        Intent intent = new Intent("android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
        intent.putExtra("accessory", usbAccessory);
        intent.addFlags(285212672);
        synchronized (this.mLock) {
            ArrayList arrayList = new ArrayList();
            ArrayList queryIntentActivitiesForAllProfiles = queryIntentActivitiesForAllProfiles(intent);
            int size = queryIntentActivitiesForAllProfiles.size();
            for (int i = 0; i < size; i++) {
                ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivitiesForAllProfiles.get(i);
                if (packageMatchesLocked(resolveInfo, null, usbAccessory)) {
                    arrayList.add(resolveInfo);
                }
            }
            removeForwardIntentIfNotNeeded = removeForwardIntentIfNotNeeded(preferHighPriority(arrayList));
            defaultActivityLocked = getDefaultActivityLocked(removeForwardIntentIfNotNeeded, (UserPackage) this.mAccessoryPreferenceMap.get(new AccessoryFilter(usbAccessory)));
        }
        sEventLogger.enqueue(new EventLogger.StringEvent("accessoryAttached: " + intent));
        resolveActivity(intent, removeForwardIntentIfNotNeeded, defaultActivityLocked, null, usbAccessory);
    }

    public final void addAccessoryPackagesToDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) {
        ArraySet arraySet;
        if (strArr.length == 0) {
            return;
        }
        AccessoryFilter accessoryFilter = new AccessoryFilter(usbAccessory);
        synchronized (this.mLock) {
            try {
                if (this.mAccessoryPreferenceDeniedMap.containsKey(accessoryFilter)) {
                    arraySet = (ArraySet) this.mAccessoryPreferenceDeniedMap.get(accessoryFilter);
                } else {
                    ArraySet arraySet2 = new ArraySet();
                    this.mAccessoryPreferenceDeniedMap.put(accessoryFilter, arraySet2);
                    arraySet = arraySet2;
                }
                boolean z = false;
                for (String str : strArr) {
                    UserPackage userPackage = new UserPackage(str, userHandle);
                    if (!arraySet.contains(userPackage)) {
                        arraySet.add(userPackage);
                        z = true;
                    }
                }
                if (z) {
                    scheduleWriteSettingsLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addDevicePackagesToDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) {
        ArraySet arraySet;
        if (strArr.length == 0) {
            return;
        }
        DeviceFilter deviceFilter = new DeviceFilter(usbDevice);
        synchronized (this.mLock) {
            try {
                if (this.mDevicePreferenceDeniedMap.containsKey(deviceFilter)) {
                    arraySet = (ArraySet) this.mDevicePreferenceDeniedMap.get(deviceFilter);
                } else {
                    ArraySet arraySet2 = new ArraySet();
                    this.mDevicePreferenceDeniedMap.put(deviceFilter, arraySet2);
                    arraySet = arraySet2;
                }
                boolean z = false;
                for (String str : strArr) {
                    UserPackage userPackage = new UserPackage(str, userHandle);
                    if (!arraySet.contains(userPackage)) {
                        arraySet.add(userPackage);
                        z = true;
                    }
                }
                if (z) {
                    scheduleWriteSettingsLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean clearCompatibleMatchesLocked(UserPackage userPackage, AccessoryFilter accessoryFilter) {
        ArrayList arrayList = new ArrayList();
        for (AccessoryFilter accessoryFilter2 : this.mAccessoryPreferenceMap.keySet()) {
            if (accessoryFilter.contains(accessoryFilter2) && !((UserPackage) this.mAccessoryPreferenceMap.get(accessoryFilter2)).equals(userPackage)) {
                arrayList.add(accessoryFilter2);
            }
        }
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.mAccessoryPreferenceMap.remove((AccessoryFilter) it.next());
            }
        }
        return !arrayList.isEmpty();
    }

    public final boolean clearCompatibleMatchesLocked(UserPackage userPackage, DeviceFilter deviceFilter) {
        ArrayList arrayList = new ArrayList();
        for (DeviceFilter deviceFilter2 : this.mDevicePreferenceMap.keySet()) {
            if (deviceFilter.contains(deviceFilter2) && !((UserPackage) this.mDevicePreferenceMap.get(deviceFilter2)).equals(userPackage)) {
                arrayList.add(deviceFilter2);
            }
        }
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.mDevicePreferenceMap.remove((DeviceFilter) it.next());
            }
        }
        return !arrayList.isEmpty();
    }

    public final void clearDefaults(String str, UserHandle userHandle) {
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return;
        }
        UserPackage userPackage = new UserPackage(str, userHandle);
        synchronized (this.mLock) {
            try {
                if (clearPackageDefaultsLocked(userPackage)) {
                    scheduleWriteSettingsLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean clearPackageDefaultsLocked(UserPackage userPackage) {
        boolean z;
        synchronized (this.mLock) {
            try {
                if (this.mDevicePreferenceMap.containsValue(userPackage)) {
                    z = false;
                    for (DeviceFilter deviceFilter : (DeviceFilter[]) this.mDevicePreferenceMap.keySet().toArray(new DeviceFilter[0])) {
                        if (userPackage.equals(this.mDevicePreferenceMap.get(deviceFilter))) {
                            this.mDevicePreferenceMap.remove(deviceFilter);
                            z = true;
                        }
                    }
                } else {
                    z = false;
                }
                if (this.mAccessoryPreferenceMap.containsValue(userPackage)) {
                    for (AccessoryFilter accessoryFilter : (AccessoryFilter[]) this.mAccessoryPreferenceMap.keySet().toArray(new AccessoryFilter[0])) {
                        if (userPackage.equals(this.mAccessoryPreferenceMap.get(accessoryFilter))) {
                            this.mAccessoryPreferenceMap.remove(accessoryFilter);
                            z = true;
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final void deviceAttached(UsbDevice usbDevice) {
        if (usbDevice.getVendorId() == 3034 && (usbDevice.getProductId() == 33106 || usbDevice.getProductId() == 33107)) {
            Slog.d("UsbProfileGroupSettingsManager", "Do not send ACTION_USB_DEVICE_ATTACHED intent, because of Dex");
            return;
        }
        Intent intent = new Intent("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intent.putExtra("device", usbDevice);
        intent.addFlags(285212672);
        Slog.d("UsbProfileGroupSettingsManager", "resolveActivity(Intent, UsbDevice) - start");
        synchronized (this.mLock) {
            try {
                Slog.d("UsbProfileGroupSettingsManager", "usbDeviceAttached, sending " + intent);
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                ArrayList arrayList = new ArrayList();
                ArrayList queryIntentActivitiesForAllProfiles = queryIntentActivitiesForAllProfiles(intent);
                int size = queryIntentActivitiesForAllProfiles.size();
                for (int i = 0; i < size; i++) {
                    ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivitiesForAllProfiles.get(i);
                    if (packageMatchesLocked(resolveInfo, usbDevice, null)) {
                        arrayList.add(resolveInfo);
                    }
                }
                ArrayList removeForwardIntentIfNotNeeded = removeForwardIntentIfNotNeeded(preferHighPriority(arrayList));
                ActivityInfo smartSwitchActivityInfo = getSmartSwitchActivityInfo(removeForwardIntentIfNotNeeded);
                ActivityInfo defaultActivityLocked = smartSwitchActivityInfo != null ? smartSwitchActivityInfo : getDefaultActivityLocked(removeForwardIntentIfNotNeeded, (UserPackage) this.mDevicePreferenceMap.get(new DeviceFilter(usbDevice)));
                if (Settings.Secure.getInt(this.mContext.getContentResolver(), "user_setup_complete", 1) != 1 && smartSwitchActivityInfo == null) {
                    Slog.d("UsbProfileGroupSettingsManager", "Setup wizard is not finished");
                } else {
                    resolveActivity(intent, removeForwardIntentIfNotNeeded, defaultActivityLocked, usbDevice, null);
                    Slog.d("UsbProfileGroupSettingsManager", "resolveActivity(Intent, UsbDevice) - end");
                }
            } finally {
            }
        }
    }

    public final void deviceAttachedForFixedHandler(UsbDevice usbDevice, ComponentName componentName) {
        Slog.d("UsbProfileGroupSettingsManager", "deviceAttachedForFixedHandler()");
        Intent intent = new Intent("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intent.putExtra("device", usbDevice);
        intent.addFlags(285212672);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.of(ActivityManager.getCurrentUser()));
        try {
            ApplicationInfo applicationInfoAsUser = this.mPackageManager.getApplicationInfoAsUser(componentName.getPackageName(), 0, this.mParentUser.getIdentifier());
            this.mSettingsManager.mUsbService.mPermissionManager.getPermissionsForUser(UserHandle.getUserId(applicationInfoAsUser.uid)).grantDevicePermission(usbDevice, applicationInfoAsUser.uid);
            Intent intent2 = new Intent(intent);
            intent2.setComponent(componentName);
            try {
                this.mContext.startActivityAsUser(intent2, this.mParentUser);
            } catch (ActivityNotFoundException unused) {
                Slog.e("UsbProfileGroupSettingsManager", "unable to start activity " + intent2);
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            Slog.e("UsbProfileGroupSettingsManager", "Default USB handling package (" + componentName.getPackageName() + ") not found  for user " + this.mParentUser);
        }
    }

    public final void dump(DualDumpOutputStream dualDumpOutputStream) {
        long j;
        long j2;
        long j3 = 2246267895810L;
        long start = dualDumpOutputStream.start("profile_group_settings", 2246267895810L);
        synchronized (this.mLock) {
            try {
                dualDumpOutputStream.write("parent_user_id", 1120986464257L, this.mParentUser.getIdentifier());
                Iterator it = this.mDevicePreferenceMap.keySet().iterator();
                while (true) {
                    j = 1146756268034L;
                    j2 = 1146756268033L;
                    if (!it.hasNext()) {
                        break;
                    }
                    DeviceFilter deviceFilter = (DeviceFilter) it.next();
                    long start2 = dualDumpOutputStream.start("device_preferences", j3);
                    deviceFilter.dump(dualDumpOutputStream, "filter", 1146756268033L);
                    UserPackage userPackage = (UserPackage) this.mDevicePreferenceMap.get(deviceFilter);
                    userPackage.getClass();
                    long start3 = dualDumpOutputStream.start("user_package", 1146756268034L);
                    dualDumpOutputStream.write("user_id", 1120986464257L, userPackage.user.getIdentifier());
                    dualDumpOutputStream.write("package_name", 1138166333442L, userPackage.packageName);
                    dualDumpOutputStream.end(start3);
                    dualDumpOutputStream.end(start2);
                    j3 = 2246267895810L;
                }
                for (AccessoryFilter accessoryFilter : this.mAccessoryPreferenceMap.keySet()) {
                    long start4 = dualDumpOutputStream.start("accessory_preferences", 2246267895811L);
                    accessoryFilter.dump(dualDumpOutputStream, "filter", j2);
                    UserPackage userPackage2 = (UserPackage) this.mAccessoryPreferenceMap.get(accessoryFilter);
                    userPackage2.getClass();
                    long start5 = dualDumpOutputStream.start("user_package", j);
                    dualDumpOutputStream.write("user_id", 1120986464257L, userPackage2.user.getIdentifier());
                    dualDumpOutputStream.write("package_name", 1138166333442L, userPackage2.packageName);
                    dualDumpOutputStream.end(start5);
                    dualDumpOutputStream.end(start4);
                    j2 = 1146756268033L;
                    j = 1146756268034L;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        sEventLogger.dump(new DualOutputStreamDumpSink(dualDumpOutputStream, 1138166333444L));
        dualDumpOutputStream.end(start);
    }

    public final ActivityInfo getDefaultActivityLocked(ArrayList arrayList, UserPackage userPackage) {
        ActivityInfo activityInfo;
        if (CoreRune.BAIDU_CARLIFE && arrayList.size() >= 1) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ResolveInfo resolveInfo = (ResolveInfo) it.next();
                ActivityInfo activityInfo2 = resolveInfo.activityInfo;
                if (activityInfo2 != null) {
                    String str = activityInfo2.packageName;
                    if (str != null ? "com.samsung.android.carlink".equals(str) : false) {
                        return resolveInfo.activityInfo;
                    }
                }
            }
        }
        if (userPackage != null) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ResolveInfo resolveInfo2 = (ResolveInfo) it2.next();
                ActivityInfo activityInfo3 = resolveInfo2.activityInfo;
                if (activityInfo3 != null && userPackage.equals(new UserPackage(activityInfo3.packageName, UserHandle.getUserHandleForUid(activityInfo3.applicationInfo.uid)))) {
                    return resolveInfo2.activityInfo;
                }
            }
        }
        if (arrayList.size() != 1 || (activityInfo = ((ResolveInfo) arrayList.get(0)).activityInfo) == null) {
            return null;
        }
        if (this.mDisablePermissionDialogs) {
            return activityInfo;
        }
        ApplicationInfo applicationInfo = activityInfo.applicationInfo;
        if (applicationInfo == null || (applicationInfo.flags & 1) == 0) {
            return null;
        }
        return activityInfo;
    }

    public final ActivityInfo getSmartSwitchActivityInfo(ArrayList arrayList) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE);
        if (runningTasks.size() == 0) {
            return null;
        }
        Iterator<ActivityManager.RunningTaskInfo> it = runningTasks.iterator();
        while (it.hasNext()) {
            Slog.d("UsbProfileGroupSettingsManager", "task.topActivity.getPackageName()=" + it.next().topActivity.getPackageName());
        }
        String str = runningTasks.get(0).topActivity.getPackageName().toString();
        Slog.d("UsbProfileGroupSettingsManager", "foregroundApp=" + str);
        if (str.equals("com.sec.android.easyMover")) {
            boolean z = this.mPackageManager.checkSignatures("android", "com.sec.android.easyMover") == 0;
            Slog.d("UsbProfileGroupSettingsManager", "isPreinstalledPackage: ret=" + z);
            if (z) {
                for (int i = 0; i < arrayList.size(); i++) {
                    ResolveInfo resolveInfo = (ResolveInfo) arrayList.get(i);
                    Slog.d("UsbProfileGroupSettingsManager", "rInfo=" + resolveInfo);
                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                    if (activityInfo != null && "com.sec.android.easyMover".equals(activityInfo.packageName)) {
                        Slog.d("UsbProfileGroupSettingsManager", "activityInfo=" + resolveInfo.activityInfo);
                        return resolveInfo.activityInfo;
                    }
                }
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x006c, code lost:
    
        if (0 == 0) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handlePackageAddedLocked(com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage r5, android.content.pm.ActivityInfo r6, java.lang.String r7) {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
            android.content.pm.PackageManager r2 = r4.mPackageManager     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            android.content.res.XmlResourceParser r0 = r6.loadXmlMetaData(r2, r7)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            if (r0 != 0) goto L10
            if (r0 == 0) goto Lf
            r0.close()
        Lf:
            return r1
        L10:
            com.android.internal.util.XmlUtils.nextElement(r0)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
        L13:
            int r7 = r0.getEventType()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            r2 = 1
            if (r7 == r2) goto L4e
            java.lang.String r7 = r0.getName()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            java.lang.String r3 = "usb-device"
            boolean r3 = r3.equals(r7)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            if (r3 == 0) goto L36
            android.hardware.usb.DeviceFilter r7 = android.hardware.usb.DeviceFilter.read(r0)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            boolean r7 = r4.clearCompatibleMatchesLocked(r5, r7)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            if (r7 == 0) goto L4a
            goto L49
        L32:
            r4 = move-exception
            goto L70
        L34:
            r4 = move-exception
            goto L52
        L36:
            java.lang.String r3 = "usb-accessory"
            boolean r7 = r3.equals(r7)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            if (r7 == 0) goto L4a
            android.hardware.usb.AccessoryFilter r7 = android.hardware.usb.AccessoryFilter.read(r0)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            boolean r7 = r4.clearCompatibleMatchesLocked(r5, r7)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            if (r7 == 0) goto L4a
        L49:
            r1 = r2
        L4a:
            com.android.internal.util.XmlUtils.nextElement(r0)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            goto L13
        L4e:
            r0.close()
            goto L6f
        L52:
            java.lang.String r5 = "UsbProfileGroupSettingsManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L32
            r7.<init>()     // Catch: java.lang.Throwable -> L32
            java.lang.String r2 = "Unable to load component info "
            r7.append(r2)     // Catch: java.lang.Throwable -> L32
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L32
            r7.append(r6)     // Catch: java.lang.Throwable -> L32
            java.lang.String r6 = r7.toString()     // Catch: java.lang.Throwable -> L32
            android.util.sysfwutil.Slog.w(r5, r6, r4)     // Catch: java.lang.Throwable -> L32
            if (r0 == 0) goto L6f
            goto L4e
        L6f:
            return r1
        L70:
            if (r0 == 0) goto L75
            r0.close()
        L75:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbProfileGroupSettingsManager.handlePackageAddedLocked(com.android.server.usb.UsbProfileGroupSettingsManager$UserPackage, android.content.pm.ActivityInfo, java.lang.String):boolean");
    }

    public final boolean hasDefaults(String str, UserHandle userHandle) {
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return false;
        }
        UserPackage userPackage = new UserPackage(str, userHandle);
        synchronized (this.mLock) {
            try {
                if (this.mDevicePreferenceMap.values().contains(userPackage)) {
                    return true;
                }
                return this.mAccessoryPreferenceMap.values().contains(userPackage);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean packageMatchesLocked(ResolveInfo resolveInfo, UsbDevice usbDevice, UsbAccessory usbAccessory) {
        ArrayList accessoryFilters;
        ArrayList deviceFilters;
        if (resolveInfo.getComponentInfo().name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            return true;
        }
        if (usbDevice != null && (deviceFilters = getDeviceFilters(this.mPackageManager, resolveInfo)) != null) {
            int size = deviceFilters.size();
            Slog.d("UsbProfileGroupSettingsManager", "packageMatchesLocked num of DeviceFilters [" + size + "]");
            for (int i = 0; i < size; i++) {
                try {
                    if (((DeviceFilter) deviceFilters.get(i)).matches(usbDevice)) {
                        return true;
                    }
                } catch (NullPointerException unused) {
                    Slog.d("UsbProfileGroupSettingsManager", "packageMatchesLocked delivered UsbDevice=" + usbDevice);
                    Slog.d("UsbProfileGroupSettingsManager", "packageMatchesLocked NPE at deviceFilter(" + i + ") = [" + ((DeviceFilter) deviceFilters.get(i)).toString() + "]");
                } catch (Exception e) {
                    Slog.w("UsbProfileGroupSettingsManager", "packageMatchesLocked got Exception ", e);
                }
            }
        }
        if (usbAccessory != null && (accessoryFilters = getAccessoryFilters(this.mPackageManager, resolveInfo)) != null) {
            int size2 = accessoryFilters.size();
            for (int i2 = 0; i2 < size2; i2++) {
                if (((AccessoryFilter) accessoryFilters.get(i2)).matches(usbAccessory)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final ArrayList queryIntentActivitiesForAllProfiles(Intent intent) {
        List enabledProfiles = this.mUserManager.getEnabledProfiles(this.mParentUser.getIdentifier());
        ArrayList arrayList = new ArrayList();
        int size = enabledProfiles.size();
        for (int i = 0; i < size; i++) {
            UsbUserSettingsManager settingsForUser = this.mSettingsManager.getSettingsForUser(((UserInfo) enabledProfiles.get(i)).id);
            arrayList.addAll(settingsForUser.mPackageManager.queryIntentActivitiesAsUser(intent, 128, settingsForUser.mUser.getIdentifier()));
        }
        return arrayList;
    }

    public final void readPreference(XmlPullParser xmlPullParser) {
        UserHandle userHandle = this.mParentUser;
        int attributeCount = xmlPullParser.getAttributeCount();
        String str = null;
        for (int i = 0; i < attributeCount; i++) {
            if ("package".equals(xmlPullParser.getAttributeName(i))) {
                str = xmlPullParser.getAttributeValue(i);
            }
            if ("user".equals(xmlPullParser.getAttributeName(i))) {
                userHandle = this.mUserManager.getUserForSerialNumber(Integer.parseInt(xmlPullParser.getAttributeValue(i)));
            }
        }
        XmlUtils.nextElement(xmlPullParser);
        if ("usb-device".equals(xmlPullParser.getName())) {
            DeviceFilter read = DeviceFilter.read(xmlPullParser);
            if (userHandle != null) {
                this.mDevicePreferenceMap.put(read, new UserPackage(str, userHandle));
            }
        } else if ("usb-accessory".equals(xmlPullParser.getName())) {
            AccessoryFilter read2 = AccessoryFilter.read(xmlPullParser);
            if (userHandle != null) {
                this.mAccessoryPreferenceMap.put(read2, new UserPackage(str, userHandle));
            }
        }
        XmlUtils.nextElement(xmlPullParser);
    }

    public final void readPreferenceDeniedList(XmlPullParser xmlPullParser) {
        int depth = xmlPullParser.getDepth();
        if (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("usb-device".equals(xmlPullParser.getName())) {
                DeviceFilter read = DeviceFilter.read(xmlPullParser);
                while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
                    if ("user-package".equals(xmlPullParser.getName())) {
                        try {
                            int readIntAttribute = XmlUtils.readIntAttribute(xmlPullParser, "user");
                            String readStringAttribute = XmlUtils.readStringAttribute(xmlPullParser, "package");
                            if (readStringAttribute == null) {
                                Slog.e("UsbProfileGroupSettingsManager", "Unable to parse package name");
                            }
                            ArraySet arraySet = (ArraySet) this.mDevicePreferenceDeniedMap.get(read);
                            if (arraySet == null) {
                                arraySet = new ArraySet();
                                this.mDevicePreferenceDeniedMap.put(read, arraySet);
                            }
                            arraySet.add(new UserPackage(readStringAttribute, UserHandle.of(readIntAttribute)));
                        } catch (ProtocolException e) {
                            Slog.e("UsbProfileGroupSettingsManager", "Unable to parse user id", e);
                        }
                    }
                }
            } else if ("usb-accessory".equals(xmlPullParser.getName())) {
                AccessoryFilter read2 = AccessoryFilter.read(xmlPullParser);
                while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
                    if ("user-package".equals(xmlPullParser.getName())) {
                        try {
                            int readIntAttribute2 = XmlUtils.readIntAttribute(xmlPullParser, "user");
                            String readStringAttribute2 = XmlUtils.readStringAttribute(xmlPullParser, "package");
                            if (readStringAttribute2 == null) {
                                Slog.e("UsbProfileGroupSettingsManager", "Unable to parse package name");
                            }
                            ArraySet arraySet2 = (ArraySet) this.mAccessoryPreferenceDeniedMap.get(read2);
                            if (arraySet2 == null) {
                                arraySet2 = new ArraySet();
                                this.mAccessoryPreferenceDeniedMap.put(read2, arraySet2);
                            }
                            arraySet2.add(new UserPackage(readStringAttribute2, UserHandle.of(readIntAttribute2)));
                        } catch (ProtocolException e2) {
                            Slog.e("UsbProfileGroupSettingsManager", "Unable to parse user id", e2);
                        }
                    }
                }
            }
            while (xmlPullParser.getDepth() > depth) {
                xmlPullParser.nextTag();
            }
        }
    }

    public final void readSettingsLocked() {
        Slog.v("UsbProfileGroupSettingsManager", "readSettingsLocked()");
        this.mDevicePreferenceMap.clear();
        this.mAccessoryPreferenceMap.clear();
        FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = this.mSettingsFile.openRead();
                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                XmlUtils.nextElement(resolvePullParser);
                while (resolvePullParser.getEventType() != 1) {
                    String name = resolvePullParser.getName();
                    if ("preference".equals(name)) {
                        readPreference(resolvePullParser);
                    } else if ("preference-denied-list".equals(name)) {
                        readPreferenceDeniedList(resolvePullParser);
                    } else {
                        XmlUtils.nextElement(resolvePullParser);
                    }
                }
            } catch (FileNotFoundException unused) {
                Slog.d("UsbProfileGroupSettingsManager", "settings file not found");
            } catch (Exception e) {
                Slog.e("UsbProfileGroupSettingsManager", "error reading settings file, deleting to start fresh", e);
                this.mSettingsFile.delete();
            }
            IoUtils.closeQuietly(fileInputStream);
        } catch (Throwable th) {
            IoUtils.closeQuietly(fileInputStream);
            throw th;
        }
    }

    public final void removeAccessoryPackagesFromDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) {
        AccessoryFilter accessoryFilter = new AccessoryFilter(usbAccessory);
        synchronized (this.mLock) {
            try {
                ArraySet arraySet = (ArraySet) this.mAccessoryPreferenceDeniedMap.get(accessoryFilter);
                if (arraySet != null) {
                    int length = strArr.length;
                    int i = 0;
                    boolean z = false;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        UserPackage userPackage = new UserPackage(strArr[i], userHandle);
                        if (arraySet.contains(userPackage)) {
                            arraySet.remove(userPackage);
                            if (arraySet.size() == 0) {
                                this.mAccessoryPreferenceDeniedMap.remove(accessoryFilter);
                                z = true;
                                break;
                            }
                            z = true;
                        }
                        i++;
                    }
                    if (z) {
                        scheduleWriteSettingsLocked();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeDevicePackagesFromDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) {
        DeviceFilter deviceFilter = new DeviceFilter(usbDevice);
        synchronized (this.mLock) {
            try {
                ArraySet arraySet = (ArraySet) this.mDevicePreferenceDeniedMap.get(deviceFilter);
                if (arraySet != null) {
                    int length = strArr.length;
                    int i = 0;
                    boolean z = false;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        UserPackage userPackage = new UserPackage(strArr[i], userHandle);
                        if (arraySet.contains(userPackage)) {
                            arraySet.remove(userPackage);
                            if (arraySet.size() == 0) {
                                this.mDevicePreferenceDeniedMap.remove(deviceFilter);
                                z = true;
                                break;
                            }
                            z = true;
                        }
                        i++;
                    }
                    if (z) {
                        scheduleWriteSettingsLocked();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ArrayList removeForwardIntentIfNotNeeded(ArrayList arrayList) {
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            ResolveInfo resolveInfo = (ResolveInfo) arrayList.get(i3);
            if (!resolveInfo.getComponentInfo().name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE)) {
                if (UserHandle.getUserHandleForUid(resolveInfo.activityInfo.applicationInfo.uid).equals(this.mParentUser)) {
                    i++;
                } else {
                    i2++;
                }
            }
        }
        if (i != 0 && i2 != 0) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList(i + i2);
        for (int i4 = 0; i4 < size; i4++) {
            ResolveInfo resolveInfo2 = (ResolveInfo) arrayList.get(i4);
            if (!resolveInfo2.getComponentInfo().name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE)) {
                arrayList2.add(resolveInfo2);
            }
        }
        return arrayList2;
    }

    public final void removeUser(UserHandle userHandle) {
        synchronized (this.mLock) {
            try {
                Iterator it = this.mDevicePreferenceMap.entrySet().iterator();
                boolean z = false;
                while (it.hasNext()) {
                    if (((UserPackage) ((Map.Entry) it.next()).getValue()).user.equals(userHandle)) {
                        it.remove();
                        z = true;
                    }
                }
                Iterator it2 = this.mAccessoryPreferenceMap.entrySet().iterator();
                while (it2.hasNext()) {
                    if (((UserPackage) ((Map.Entry) it2.next()).getValue()).user.equals(userHandle)) {
                        it2.remove();
                        z = true;
                    }
                }
                int size = this.mDevicePreferenceDeniedMap.size();
                for (int i = 0; i < size; i++) {
                    ArraySet arraySet = (ArraySet) this.mDevicePreferenceDeniedMap.valueAt(i);
                    for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                        if (((UserPackage) arraySet.valueAt(size2)).user.equals(userHandle)) {
                            arraySet.removeAt(size2);
                            z = true;
                        }
                    }
                }
                int size3 = this.mAccessoryPreferenceDeniedMap.size();
                for (int i2 = 0; i2 < size3; i2++) {
                    ArraySet arraySet2 = (ArraySet) this.mAccessoryPreferenceDeniedMap.valueAt(i2);
                    for (int size4 = arraySet2.size() - 1; size4 >= 0; size4--) {
                        if (((UserPackage) arraySet2.valueAt(size4)).user.equals(userHandle)) {
                            arraySet2.removeAt(size4);
                            z = true;
                        }
                    }
                }
                if (z) {
                    scheduleWriteSettingsLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void resolveActivity(Intent intent, ArrayList arrayList, ActivityInfo activityInfo, UsbDevice usbDevice, UsbAccessory usbAccessory) {
        ArraySet arraySet = usbDevice != null ? (ArraySet) this.mDevicePreferenceDeniedMap.get(new DeviceFilter(usbDevice)) : usbAccessory != null ? (ArraySet) this.mAccessoryPreferenceDeniedMap.get(new AccessoryFilter(usbAccessory)) : null;
        if (arraySet != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ActivityInfo activityInfo2 = ((ResolveInfo) arrayList.get(size)).activityInfo;
                if (arraySet.contains(new UserPackage(activityInfo2.packageName, UserHandle.getUserHandleForUid(activityInfo2.applicationInfo.uid)))) {
                    arrayList.remove(size);
                }
            }
        }
        Slog.d("UsbProfileGroupSettingsManager", "resolveActivity: intent=" + intent + " defaultActivity=" + activityInfo);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Slog.d("UsbProfileGroupSettingsManager", ((ResolveInfo) it.next()).toString());
        }
        int size2 = arrayList.size();
        UsbHandlerManager usbHandlerManager = this.mUsbHandlerManager;
        if (size2 == 0) {
            if (usbAccessory != null) {
                UserHandle userHandle = this.mParentUser;
                usbHandlerManager.getClass();
                String uri = usbAccessory.getUri();
                if (uri == null || uri.length() <= 0) {
                    return;
                }
                Intent intent2 = new Intent();
                intent2.addFlags(268435456);
                intent2.setComponent(ComponentName.unflattenFromString(usbHandlerManager.mContext.getResources().getString(R.string.face_dangling_notification_msg)));
                intent2.putExtra("accessory", usbAccessory);
                intent2.putExtra(SystemIntentProcessor.KEY_URI, uri);
                try {
                    usbHandlerManager.mContext.startActivityAsUser(intent2, userHandle);
                    return;
                } catch (ActivityNotFoundException unused) {
                    Slog.e("UsbHandlerManager", "unable to start UsbAccessoryUriActivity");
                    return;
                }
            }
            return;
        }
        if (activityInfo != null) {
            UsbUserPermissionManager permissionsForUser = this.mSettingsManager.mUsbService.mPermissionManager.getPermissionsForUser(UserHandle.getUserId(activityInfo.applicationInfo.uid));
            if (usbDevice != null) {
                permissionsForUser.grantDevicePermission(usbDevice, activityInfo.applicationInfo.uid);
            } else if (usbAccessory != null) {
                permissionsForUser.grantAccessoryPermission(usbAccessory, activityInfo.applicationInfo.uid);
            }
            try {
                intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                this.mContext.startActivityAsUser(intent, UserHandle.getUserHandleForUid(activityInfo.applicationInfo.uid));
                return;
            } catch (ActivityNotFoundException e) {
                Slog.e("UsbProfileGroupSettingsManager", "startActivity failed", e);
                return;
            }
        }
        if (arrayList.size() != 1) {
            UserHandle userHandle2 = this.mParentUser;
            usbHandlerManager.getClass();
            Intent intent3 = new Intent();
            intent3.addFlags(268435456);
            intent3.setComponent(ComponentName.unflattenFromString(usbHandlerManager.mContext.getResources().getString(R.string.face_error_hw_not_available)));
            intent3.putParcelableArrayListExtra("rlist", arrayList);
            intent3.putExtra("android.intent.extra.INTENT", intent);
            try {
                usbHandlerManager.mContext.startActivityAsUser(intent3, userHandle2);
                return;
            } catch (ActivityNotFoundException e2) {
                Slog.e("UsbHandlerManager", "unable to start activity " + intent3, e2);
                return;
            }
        }
        ResolveInfo resolveInfo = (ResolveInfo) arrayList.get(0);
        usbHandlerManager.getClass();
        Intent intent4 = new Intent();
        intent4.addFlags(268435456);
        intent4.setComponent(ComponentName.unflattenFromString(usbHandlerManager.mContext.getResources().getString(R.string.face_dangling_notification_title)));
        intent4.putExtra("rinfo", resolveInfo);
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(resolveInfo.activityInfo.applicationInfo.uid);
        if (usbDevice != null) {
            intent4.putExtra("device", usbDevice);
        } else {
            intent4.putExtra("accessory", usbAccessory);
        }
        try {
            usbHandlerManager.mContext.startActivityAsUser(intent4, userHandleForUid);
        } catch (ActivityNotFoundException e3) {
            Slog.e("UsbHandlerManager", "unable to start activity " + intent4, e3);
        }
    }

    public final void scheduleWriteSettingsLocked() {
        if (this.mIsWriteSettingsScheduled) {
            return;
        }
        this.mIsWriteSettingsScheduled = true;
        AsyncTask.execute(new Runnable() { // from class: com.android.server.usb.UsbProfileGroupSettingsManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FileOutputStream fileOutputStream;
                IOException e;
                UsbProfileGroupSettingsManager usbProfileGroupSettingsManager = UsbProfileGroupSettingsManager.this;
                synchronized (usbProfileGroupSettingsManager.mLock) {
                    try {
                        try {
                            fileOutputStream = usbProfileGroupSettingsManager.mSettingsFile.startWrite();
                        } catch (Throwable th) {
                            throw th;
                        }
                    } catch (IOException e2) {
                        fileOutputStream = null;
                        e = e2;
                    }
                    try {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                        resolveSerializer.startTag((String) null, "settings");
                        for (DeviceFilter deviceFilter : usbProfileGroupSettingsManager.mDevicePreferenceMap.keySet()) {
                            resolveSerializer.startTag((String) null, "preference");
                            resolveSerializer.attribute((String) null, "package", ((UsbProfileGroupSettingsManager.UserPackage) usbProfileGroupSettingsManager.mDevicePreferenceMap.get(deviceFilter)).packageName);
                            resolveSerializer.attribute((String) null, "user", String.valueOf(usbProfileGroupSettingsManager.mUserManager.getUserSerialNumber(((UsbProfileGroupSettingsManager.UserPackage) usbProfileGroupSettingsManager.mDevicePreferenceMap.get(deviceFilter)).user.getIdentifier())));
                            deviceFilter.write(resolveSerializer);
                            resolveSerializer.endTag((String) null, "preference");
                        }
                        for (AccessoryFilter accessoryFilter : usbProfileGroupSettingsManager.mAccessoryPreferenceMap.keySet()) {
                            resolveSerializer.startTag((String) null, "preference");
                            resolveSerializer.attribute((String) null, "package", ((UsbProfileGroupSettingsManager.UserPackage) usbProfileGroupSettingsManager.mAccessoryPreferenceMap.get(accessoryFilter)).packageName);
                            resolveSerializer.attribute((String) null, "user", String.valueOf(usbProfileGroupSettingsManager.mUserManager.getUserSerialNumber(((UsbProfileGroupSettingsManager.UserPackage) usbProfileGroupSettingsManager.mAccessoryPreferenceMap.get(accessoryFilter)).user.getIdentifier())));
                            accessoryFilter.write(resolveSerializer);
                            resolveSerializer.endTag((String) null, "preference");
                        }
                        int size = usbProfileGroupSettingsManager.mDevicePreferenceDeniedMap.size();
                        for (int i = 0; i < size; i++) {
                            DeviceFilter deviceFilter2 = (DeviceFilter) usbProfileGroupSettingsManager.mDevicePreferenceDeniedMap.keyAt(i);
                            ArraySet arraySet = (ArraySet) usbProfileGroupSettingsManager.mDevicePreferenceDeniedMap.valueAt(i);
                            resolveSerializer.startTag((String) null, "preference-denied-list");
                            deviceFilter2.write(resolveSerializer);
                            int size2 = arraySet.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                UsbProfileGroupSettingsManager.UserPackage userPackage = (UsbProfileGroupSettingsManager.UserPackage) arraySet.valueAt(i2);
                                resolveSerializer.startTag((String) null, "user-package");
                                resolveSerializer.attribute((String) null, "user", String.valueOf(usbProfileGroupSettingsManager.mUserManager.getUserSerialNumber(userPackage.user.getIdentifier())));
                                resolveSerializer.attribute((String) null, "package", userPackage.packageName);
                                resolveSerializer.endTag((String) null, "user-package");
                            }
                            resolveSerializer.endTag((String) null, "preference-denied-list");
                        }
                        int size3 = usbProfileGroupSettingsManager.mAccessoryPreferenceDeniedMap.size();
                        for (int i3 = 0; i3 < size3; i3++) {
                            AccessoryFilter accessoryFilter2 = (AccessoryFilter) usbProfileGroupSettingsManager.mAccessoryPreferenceDeniedMap.keyAt(i3);
                            ArraySet arraySet2 = (ArraySet) usbProfileGroupSettingsManager.mAccessoryPreferenceDeniedMap.valueAt(i3);
                            resolveSerializer.startTag((String) null, "preference-denied-list");
                            accessoryFilter2.write(resolveSerializer);
                            int size4 = arraySet2.size();
                            for (int i4 = 0; i4 < size4; i4++) {
                                UsbProfileGroupSettingsManager.UserPackage userPackage2 = (UsbProfileGroupSettingsManager.UserPackage) arraySet2.valueAt(i4);
                                resolveSerializer.startTag((String) null, "user-package");
                                resolveSerializer.attribute((String) null, "user", String.valueOf(usbProfileGroupSettingsManager.mUserManager.getUserSerialNumber(userPackage2.user.getIdentifier())));
                                resolveSerializer.attribute((String) null, "package", userPackage2.packageName);
                                resolveSerializer.endTag((String) null, "user-package");
                            }
                            resolveSerializer.endTag((String) null, "preference-denied-list");
                        }
                        resolveSerializer.endTag((String) null, "settings");
                        resolveSerializer.endDocument();
                        usbProfileGroupSettingsManager.mSettingsFile.finishWrite(fileOutputStream);
                    } catch (IOException e3) {
                        e = e3;
                        Slog.e("UsbProfileGroupSettingsManager", "Failed to write settings", e);
                        if (fileOutputStream != null) {
                            usbProfileGroupSettingsManager.mSettingsFile.failWrite(fileOutputStream);
                        }
                        usbProfileGroupSettingsManager.mIsWriteSettingsScheduled = false;
                    }
                    usbProfileGroupSettingsManager.mIsWriteSettingsScheduled = false;
                }
            }
        });
    }

    public final void setAccessoryPackage(UsbAccessory usbAccessory, String str, UserHandle userHandle) {
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return;
        }
        AccessoryFilter accessoryFilter = new AccessoryFilter(usbAccessory);
        synchronized (this.mLock) {
            boolean z = true;
            try {
                if (str != null) {
                    UserPackage userPackage = new UserPackage(str, userHandle);
                    z = true ^ userPackage.equals(this.mAccessoryPreferenceMap.get(accessoryFilter));
                    if (z) {
                        this.mAccessoryPreferenceMap.put(accessoryFilter, userPackage);
                    }
                } else if (this.mAccessoryPreferenceMap.remove(accessoryFilter) == null) {
                    z = false;
                }
                if (z) {
                    scheduleWriteSettingsLocked();
                }
            } finally {
            }
        }
    }

    public final void setDevicePackage(UsbDevice usbDevice, String str, UserHandle userHandle) {
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return;
        }
        DeviceFilter deviceFilter = new DeviceFilter(usbDevice);
        synchronized (this.mLock) {
            boolean z = true;
            try {
                if (str != null) {
                    UserPackage userPackage = new UserPackage(str, userHandle);
                    z = true ^ userPackage.equals(this.mDevicePreferenceMap.get(deviceFilter));
                    if (z) {
                        this.mDevicePreferenceMap.put(deviceFilter, userPackage);
                    }
                } else if (this.mDevicePreferenceMap.remove(deviceFilter) == null) {
                    z = false;
                }
                if (z) {
                    scheduleWriteSettingsLocked();
                }
            } finally {
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void upgradeSingleUserLocked() {
        int eventType;
        File file = sSingleUserSettingsFile;
        if (!file.exists()) {
            return;
        }
        this.mDevicePreferenceMap.clear();
        this.mAccessoryPreferenceMap.clear();
        FileInputStream fileInputStream = null;
        FileInputStream fileInputStream2 = null;
        try {
            try {
                FileInputStream fileInputStream3 = new FileInputStream(file);
                try {
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream3);
                    XmlUtils.nextElement(resolvePullParser);
                    while (true) {
                        eventType = resolvePullParser.getEventType();
                        if (eventType == 1) {
                            break;
                        } else if ("preference".equals(resolvePullParser.getName())) {
                            readPreference(resolvePullParser);
                        } else {
                            XmlUtils.nextElement(resolvePullParser);
                        }
                    }
                    IoUtils.closeQuietly(fileInputStream3);
                    fileInputStream = eventType;
                } catch (IOException | XmlPullParserException e) {
                    e = e;
                    fileInputStream2 = fileInputStream3;
                    Log.wtf("UsbProfileGroupSettingsManager", "Failed to read single-user settings", e);
                    IoUtils.closeQuietly(fileInputStream2);
                    fileInputStream = fileInputStream2;
                    scheduleWriteSettingsLocked();
                    sSingleUserSettingsFile.delete();
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream3;
                    IoUtils.closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (IOException | XmlPullParserException e2) {
                e = e2;
            }
            scheduleWriteSettingsLocked();
            sSingleUserSettingsFile.delete();
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
