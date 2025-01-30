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
import android.os.SystemProperties;
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
import com.android.server.baiducarlife.BaiduCarlifeADBConnectUtils;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.utils.EventLogger;
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

/* loaded from: classes3.dex */
public class UsbProfileGroupSettingsManager {
  public static final String TAG = "UsbProfileGroupSettingsManager";
  public static EventLogger sEventLogger;
  public final Context mContext;
  public final boolean mDisablePermissionDialogs;
  public boolean mIsWriteSettingsScheduled;
  public final Object mLock;
  public final PackageManager mPackageManager;
  public MyPackageMonitor mPackageMonitor;
  public final UserHandle mParentUser;
  public final AtomicFile mSettingsFile;
  public final UsbSettingsManager mSettingsManager;
  public boolean mSmartSwitchInTheProcess;
  public final UsbHandlerManager mUsbHandlerManager;
  public final UserManager mUserManager;
  public static final File sSingleUserSettingsFile =
      new File("/data/system/usb_device_manager.xml");
  public static final boolean RELEASE_BUILD = "user".equals(SystemProperties.get("ro.build.type"));
  public final HashMap mDevicePreferenceMap = new HashMap();
  public final ArrayMap mDevicePreferenceDeniedMap = new ArrayMap();
  public final HashMap mAccessoryPreferenceMap = new HashMap();
  public final ArrayMap mAccessoryPreferenceDeniedMap = new ArrayMap();

  public void usbDeviceRemoved(UsbDevice usbDevice) {}

  public class UserPackage {
    public final String packageName;
    public final UserHandle user;

    public UserPackage(String str, UserHandle userHandle) {
      this.packageName = str;
      this.user = userHandle;
    }

    public boolean equals(Object obj) {
      if (!(obj instanceof UserPackage)) {
        return false;
      }
      UserPackage userPackage = (UserPackage) obj;
      return this.user.equals(userPackage.user) && this.packageName.equals(userPackage.packageName);
    }

    public int hashCode() {
      return (this.user.hashCode() * 31) + this.packageName.hashCode();
    }

    public String toString() {
      return this.user.getIdentifier() + "/" + this.packageName;
    }

    public void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
      long start = dualDumpOutputStream.start(str, j);
      dualDumpOutputStream.write("user_id", 1120986464257L, this.user.getIdentifier());
      dualDumpOutputStream.write("package_name", 1138166333442L, this.packageName);
      dualDumpOutputStream.end(start);
    }
  }

  public class MyPackageMonitor extends PackageMonitor {
    public MyPackageMonitor() {}

    public void onPackageAdded(String str, int i) {
      if (UsbProfileGroupSettingsManager.this.mUserManager.isSameProfileGroup(
          UsbProfileGroupSettingsManager.this.mParentUser.getIdentifier(),
          UserHandle.getUserId(i))) {
        UsbProfileGroupSettingsManager.this.handlePackageAdded(
            new UserPackage(str, UserHandle.getUserHandleForUid(i)));
      }
    }

    public void onPackageRemoved(String str, int i) {
      if (UsbProfileGroupSettingsManager.this.mUserManager.isSameProfileGroup(
          UsbProfileGroupSettingsManager.this.mParentUser.getIdentifier(),
          UserHandle.getUserId(i))) {
        UsbProfileGroupSettingsManager.this.clearDefaults(str, UserHandle.getUserHandleForUid(i));
      }
    }
  }

  public UsbProfileGroupSettingsManager(
      Context context,
      UserHandle userHandle,
      UsbSettingsManager usbSettingsManager,
      UsbHandlerManager usbHandlerManager) {
    Object obj = new Object();
    this.mLock = obj;
    this.mPackageMonitor = new MyPackageMonitor();
    this.mSmartSwitchInTheProcess = false;
    Slog.v(TAG, "Creating settings for " + userHandle);
    try {
      context.createPackageContextAsUser("android", 0, userHandle);
      this.mContext = context;
      this.mPackageManager = context.getPackageManager();
      this.mSettingsManager = usbSettingsManager;
      this.mUserManager = (UserManager) context.getSystemService("user");
      this.mParentUser = userHandle;
      this.mSettingsFile =
          new AtomicFile(
              new File(
                  Environment.getUserSystemDirectory(userHandle.getIdentifier()),
                  "usb_device_manager.xml"),
              "usb-state");
      this.mDisablePermissionDialogs =
          context.getResources().getBoolean(R.bool.config_customUserSwitchUi);
      synchronized (obj) {
        if (UserHandle.SYSTEM.equals(userHandle)) {
          upgradeSingleUserLocked();
        }
        readSettingsLocked();
      }
      this.mPackageMonitor.register(context, (Looper) null, UserHandle.ALL, true);
      this.mUsbHandlerManager = usbHandlerManager;
      sEventLogger = new EventLogger(200, "UsbProfileGroupSettingsManager activity");
    } catch (PackageManager.NameNotFoundException unused) {
      throw new RuntimeException("Missing android package");
    }
  }

  public void unregisterReceivers() {
    this.mPackageMonitor.unregister();
  }

  public void removeUser(UserHandle userHandle) {
    synchronized (this.mLock) {
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
    }
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
        userHandle =
            this.mUserManager.getUserForSerialNumber(
                Integer.parseInt(xmlPullParser.getAttributeValue(i)));
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
                Slog.e(TAG, "Unable to parse package name");
              }
              ArraySet arraySet = (ArraySet) this.mDevicePreferenceDeniedMap.get(read);
              if (arraySet == null) {
                arraySet = new ArraySet();
                this.mDevicePreferenceDeniedMap.put(read, arraySet);
              }
              arraySet.add(new UserPackage(readStringAttribute, UserHandle.of(readIntAttribute)));
            } catch (ProtocolException e) {
              Slog.e(TAG, "Unable to parse user id", e);
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
                Slog.e(TAG, "Unable to parse package name");
              }
              ArraySet arraySet2 = (ArraySet) this.mAccessoryPreferenceDeniedMap.get(read2);
              if (arraySet2 == null) {
                arraySet2 = new ArraySet();
                this.mAccessoryPreferenceDeniedMap.put(read2, arraySet2);
              }
              arraySet2.add(
                  new UserPackage(readStringAttribute2, UserHandle.of(readIntAttribute2)));
            } catch (ProtocolException e2) {
              Slog.e(TAG, "Unable to parse user id", e2);
            }
          }
        }
      }
      while (xmlPullParser.getDepth() > depth) {
        xmlPullParser.nextTag();
      }
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public final void upgradeSingleUserLocked() {
    FileInputStream fileInputStream;
    int eventType;
    File file = sSingleUserSettingsFile;
    if (file.exists()) {
      this.mDevicePreferenceMap.clear();
      this.mAccessoryPreferenceMap.clear();
      FileInputStream fileInputStream2 = null;
      FileInputStream fileInputStream3 = null;
      try {
        try {
          fileInputStream = new FileInputStream(file);
        } catch (Throwable th) {
          th = th;
        }
      } catch (IOException | XmlPullParserException e) {
        e = e;
      }
      try {
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
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
        IoUtils.closeQuietly(fileInputStream);
        fileInputStream2 = eventType;
      } catch (IOException | XmlPullParserException e2) {
        e = e2;
        fileInputStream3 = fileInputStream;
        Log.wtf(TAG, "Failed to read single-user settings", e);
        IoUtils.closeQuietly(fileInputStream3);
        fileInputStream2 = fileInputStream3;
        scheduleWriteSettingsLocked();
        sSingleUserSettingsFile.delete();
      } catch (Throwable th2) {
        th = th2;
        fileInputStream2 = fileInputStream;
        IoUtils.closeQuietly(fileInputStream2);
        throw th;
      }
      scheduleWriteSettingsLocked();
      sSingleUserSettingsFile.delete();
    }
  }

  public final void readSettingsLocked() {
    Slog.v(TAG, "readSettingsLocked()");
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
        Slog.d(TAG, "settings file not found");
      } catch (Exception e) {
        Slog.e(TAG, "error reading settings file, deleting to start fresh", e);
        this.mSettingsFile.delete();
      }
    } finally {
      IoUtils.closeQuietly(fileInputStream);
    }
  }

  public final void scheduleWriteSettingsLocked() {
    if (this.mIsWriteSettingsScheduled) {
      return;
    }
    this.mIsWriteSettingsScheduled = true;
    AsyncTask.execute(
        new Runnable() { // from class:
          // com.android.server.usb.UsbProfileGroupSettingsManager$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            UsbProfileGroupSettingsManager.this.lambda$scheduleWriteSettingsLocked$0();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleWriteSettingsLocked$0() {
    FileOutputStream fileOutputStream;
    IOException e;
    synchronized (this.mLock) {
      try {
        fileOutputStream = this.mSettingsFile.startWrite();
        try {
          TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
          resolveSerializer.startDocument((String) null, Boolean.TRUE);
          resolveSerializer.setFeature(
              "http://xmlpull.org/v1/doc/features.html#indent-output", true);
          resolveSerializer.startTag((String) null, "settings");
          for (DeviceFilter deviceFilter : this.mDevicePreferenceMap.keySet()) {
            resolveSerializer.startTag((String) null, "preference");
            resolveSerializer.attribute(
                (String) null,
                "package",
                ((UserPackage) this.mDevicePreferenceMap.get(deviceFilter)).packageName);
            resolveSerializer.attribute(
                (String) null,
                "user",
                String.valueOf(
                    getSerial(((UserPackage) this.mDevicePreferenceMap.get(deviceFilter)).user)));
            deviceFilter.write(resolveSerializer);
            resolveSerializer.endTag((String) null, "preference");
          }
          for (AccessoryFilter accessoryFilter : this.mAccessoryPreferenceMap.keySet()) {
            resolveSerializer.startTag((String) null, "preference");
            resolveSerializer.attribute(
                (String) null,
                "package",
                ((UserPackage) this.mAccessoryPreferenceMap.get(accessoryFilter)).packageName);
            resolveSerializer.attribute(
                (String) null,
                "user",
                String.valueOf(
                    getSerial(
                        ((UserPackage) this.mAccessoryPreferenceMap.get(accessoryFilter)).user)));
            accessoryFilter.write(resolveSerializer);
            resolveSerializer.endTag((String) null, "preference");
          }
          int size = this.mDevicePreferenceDeniedMap.size();
          for (int i = 0; i < size; i++) {
            DeviceFilter deviceFilter2 = (DeviceFilter) this.mDevicePreferenceDeniedMap.keyAt(i);
            ArraySet arraySet = (ArraySet) this.mDevicePreferenceDeniedMap.valueAt(i);
            resolveSerializer.startTag((String) null, "preference-denied-list");
            deviceFilter2.write(resolveSerializer);
            int size2 = arraySet.size();
            for (int i2 = 0; i2 < size2; i2++) {
              UserPackage userPackage = (UserPackage) arraySet.valueAt(i2);
              resolveSerializer.startTag((String) null, "user-package");
              resolveSerializer.attribute(
                  (String) null, "user", String.valueOf(getSerial(userPackage.user)));
              resolveSerializer.attribute((String) null, "package", userPackage.packageName);
              resolveSerializer.endTag((String) null, "user-package");
            }
            resolveSerializer.endTag((String) null, "preference-denied-list");
          }
          int size3 = this.mAccessoryPreferenceDeniedMap.size();
          for (int i3 = 0; i3 < size3; i3++) {
            AccessoryFilter accessoryFilter2 =
                (AccessoryFilter) this.mAccessoryPreferenceDeniedMap.keyAt(i3);
            ArraySet arraySet2 = (ArraySet) this.mAccessoryPreferenceDeniedMap.valueAt(i3);
            resolveSerializer.startTag((String) null, "preference-denied-list");
            accessoryFilter2.write(resolveSerializer);
            int size4 = arraySet2.size();
            for (int i4 = 0; i4 < size4; i4++) {
              UserPackage userPackage2 = (UserPackage) arraySet2.valueAt(i4);
              resolveSerializer.startTag((String) null, "user-package");
              resolveSerializer.attribute(
                  (String) null, "user", String.valueOf(getSerial(userPackage2.user)));
              resolveSerializer.attribute((String) null, "package", userPackage2.packageName);
              resolveSerializer.endTag((String) null, "user-package");
            }
            resolveSerializer.endTag((String) null, "preference-denied-list");
          }
          resolveSerializer.endTag((String) null, "settings");
          resolveSerializer.endDocument();
          this.mSettingsFile.finishWrite(fileOutputStream);
        } catch (IOException e2) {
          e = e2;
          Slog.e(TAG, "Failed to write settings", e);
          if (fileOutputStream != null) {
            this.mSettingsFile.failWrite(fileOutputStream);
          }
          this.mIsWriteSettingsScheduled = false;
        }
      } catch (IOException e3) {
        fileOutputStream = null;
        e = e3;
      }
      this.mIsWriteSettingsScheduled = false;
    }
  }

  public static ArrayList getDeviceFilters(PackageManager packageManager, ResolveInfo resolveInfo) {
    ArrayList arrayList;
    XmlResourceParser loadXmlMetaData;
    XmlResourceParser xmlResourceParser = null;
    ArrayList arrayList2 = null;
    xmlResourceParser = null;
    try {
      try {
        loadXmlMetaData =
            resolveInfo.activityInfo.loadXmlMetaData(
                packageManager, "android.hardware.usb.action.USB_DEVICE_ATTACHED");
      } catch (Exception e) {
        e = e;
        arrayList = null;
      }
    } catch (Throwable th) {
      th = th;
    }
    try {
      if (loadXmlMetaData == null) {
        Slog.w(TAG, "no meta-data for " + resolveInfo);
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
    } catch (Exception e2) {
      e = e2;
      xmlResourceParser = loadXmlMetaData;
      arrayList = null;
      Slog.w(TAG, "Unable to load component info " + resolveInfo.toString(), e);
      if (xmlResourceParser != null) {
        xmlResourceParser.close();
      }
      return arrayList;
    } catch (Throwable th2) {
      th = th2;
      xmlResourceParser = loadXmlMetaData;
      if (xmlResourceParser != null) {
        xmlResourceParser.close();
      }
      throw th;
    }
  }

  public static ArrayList getAccessoryFilters(
      PackageManager packageManager, ResolveInfo resolveInfo) {
    ArrayList arrayList;
    XmlResourceParser loadXmlMetaData;
    XmlResourceParser xmlResourceParser = null;
    ArrayList arrayList2 = null;
    xmlResourceParser = null;
    try {
      try {
        loadXmlMetaData =
            resolveInfo.activityInfo.loadXmlMetaData(
                packageManager, "android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
      } catch (Exception e) {
        e = e;
        arrayList = null;
      }
    } catch (Throwable th) {
      th = th;
    }
    try {
      if (loadXmlMetaData == null) {
        Slog.w(TAG, "no meta-data for " + resolveInfo);
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
    } catch (Exception e2) {
      e = e2;
      xmlResourceParser = loadXmlMetaData;
      arrayList = null;
      Slog.w(TAG, "Unable to load component info " + resolveInfo.toString(), e);
      if (xmlResourceParser != null) {
        xmlResourceParser.close();
      }
      return arrayList;
    } catch (Throwable th2) {
      th = th2;
      xmlResourceParser = loadXmlMetaData;
      if (xmlResourceParser != null) {
        xmlResourceParser.close();
      }
      throw th;
    }
  }

  public final boolean packageMatchesLocked(
      ResolveInfo resolveInfo, UsbDevice usbDevice, UsbAccessory usbAccessory) {
    ArrayList accessoryFilters;
    ArrayList deviceFilters;
    if (isForwardMatch(resolveInfo)) {
      return true;
    }
    if (usbDevice != null
        && (deviceFilters = getDeviceFilters(this.mPackageManager, resolveInfo)) != null) {
      int size = deviceFilters.size();
      Slog.d(TAG, "packageMatchesLocked num of DeviceFilters [" + size + "]");
      for (int i = 0; i < size; i++) {
        try {
          if (((DeviceFilter) deviceFilters.get(i)).matches(usbDevice)) {
            return true;
          }
        } catch (NullPointerException unused) {
          String str = TAG;
          Slog.d(str, "packageMatchesLocked delivered UsbDevice=" + usbDevice);
          Slog.d(
              str,
              "packageMatchesLocked NPE at deviceFilter("
                  + i
                  + ") = ["
                  + ((DeviceFilter) deviceFilters.get(i)).toString()
                  + "]");
        } catch (Exception e) {
          Slog.w(TAG, "packageMatchesLocked got Exception ", e);
        }
      }
    }
    if (usbAccessory != null
        && (accessoryFilters = getAccessoryFilters(this.mPackageManager, resolveInfo)) != null) {
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
      arrayList.addAll(
          this.mSettingsManager
              .getSettingsForUser(((UserInfo) enabledProfiles.get(i)).id)
              .queryIntentActivities(intent));
    }
    return arrayList;
  }

  public final boolean isForwardMatch(ResolveInfo resolveInfo) {
    return resolveInfo
        .getComponentInfo()
        .name
        .equals(IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE);
  }

  public final ArrayList preferHighPriority(ArrayList arrayList) {
    SparseArray sparseArray = new SparseArray();
    SparseIntArray sparseIntArray = new SparseIntArray();
    ArrayList arrayList2 = new ArrayList();
    int size = arrayList.size();
    for (int i = 0; i < size; i++) {
      ResolveInfo resolveInfo = (ResolveInfo) arrayList.get(i);
      if (isForwardMatch(resolveInfo)) {
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

  public final ArrayList removeForwardIntentIfNotNeeded(ArrayList arrayList) {
    int size = arrayList.size();
    int i = 0;
    int i2 = 0;
    for (int i3 = 0; i3 < size; i3++) {
      ResolveInfo resolveInfo = (ResolveInfo) arrayList.get(i3);
      if (!isForwardMatch(resolveInfo)) {
        if (UserHandle.getUserHandleForUid(resolveInfo.activityInfo.applicationInfo.uid)
            .equals(this.mParentUser)) {
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
      if (!isForwardMatch(resolveInfo2)) {
        arrayList2.add(resolveInfo2);
      }
    }
    return arrayList2;
  }

  public final ArrayList getDeviceMatchesLocked(UsbDevice usbDevice, Intent intent) {
    ArrayList arrayList = new ArrayList();
    ArrayList queryIntentActivitiesForAllProfiles = queryIntentActivitiesForAllProfiles(intent);
    int size = queryIntentActivitiesForAllProfiles.size();
    for (int i = 0; i < size; i++) {
      ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivitiesForAllProfiles.get(i);
      if (packageMatchesLocked(resolveInfo, usbDevice, null)) {
        arrayList.add(resolveInfo);
      }
    }
    return removeForwardIntentIfNotNeeded(preferHighPriority(arrayList));
  }

  public final ArrayList getAccessoryMatchesLocked(UsbAccessory usbAccessory, Intent intent) {
    ArrayList arrayList = new ArrayList();
    ArrayList queryIntentActivitiesForAllProfiles = queryIntentActivitiesForAllProfiles(intent);
    int size = queryIntentActivitiesForAllProfiles.size();
    for (int i = 0; i < size; i++) {
      ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivitiesForAllProfiles.get(i);
      if (packageMatchesLocked(resolveInfo, null, usbAccessory)) {
        arrayList.add(resolveInfo);
      }
    }
    return removeForwardIntentIfNotNeeded(preferHighPriority(arrayList));
  }

  public final boolean isPreinstalledPackage(String str) {
    boolean z = true;
    if (RELEASE_BUILD && this.mPackageManager.checkSignatures("android", str) != 0) {
      z = false;
    }
    Slog.d(TAG, "isPreinstalledPackage: ret=" + z);
    return z;
  }

  public final ActivityInfo getSmartSwitchActivityInfo(UsbDevice usbDevice, ArrayList arrayList) {
    List<ActivityManager.RunningTaskInfo> runningTasks =
        ((ActivityManager) this.mContext.getSystemService("activity"))
            .getRunningTasks(Integer.MAX_VALUE);
    if (runningTasks.size() == 0) {
      return null;
    }
    for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
      Slog.d(
          TAG, "task.topActivity.getPackageName()=" + runningTaskInfo.topActivity.getPackageName());
    }
    String str = runningTasks.get(0).topActivity.getPackageName().toString();
    Slog.d(TAG, "foregroundApp=" + str);
    if (str.equals("com.sec.android.easyMover")
        && isPreinstalledPackage("com.sec.android.easyMover")) {
      for (int i = 0; i < arrayList.size(); i++) {
        ResolveInfo resolveInfo = (ResolveInfo) arrayList.get(i);
        String str2 = TAG;
        Slog.d(str2, "rInfo=" + resolveInfo);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        if (activityInfo != null && "com.sec.android.easyMover".equals(activityInfo.packageName)) {
          Slog.d(str2, "activityInfo=" + resolveInfo.activityInfo);
          return resolveInfo.activityInfo;
        }
      }
    }
    return null;
  }

  public final boolean isDex(UsbDevice usbDevice) {
    if (usbDevice.getVendorId() != 3034) {
      return false;
    }
    if (usbDevice.getProductId() != 33106 && usbDevice.getProductId() != 33107) {
      return false;
    }
    Slog.d(TAG, "Do not send ACTION_USB_DEVICE_ATTACHED intent, because of Dex");
    return true;
  }

  public void deviceAttached(UsbDevice usbDevice) {
    if (isDex(usbDevice)) {
      return;
    }
    resolveActivity(createDeviceAttachedIntent(usbDevice), usbDevice);
  }

  public final void resolveActivity(Intent intent, UsbDevice usbDevice) {
    String str = TAG;
    Slog.d(str, "resolveActivity(Intent, UsbDevice) - start");
    synchronized (this.mLock) {
      Slog.d(str, "usbDeviceAttached, sending " + intent);
      this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
      ArrayList deviceMatchesLocked = getDeviceMatchesLocked(usbDevice, intent);
      ActivityInfo smartSwitchActivityInfo =
          getSmartSwitchActivityInfo(usbDevice, deviceMatchesLocked);
      ActivityInfo defaultActivityLocked =
          smartSwitchActivityInfo != null
              ? smartSwitchActivityInfo
              : getDefaultActivityLocked(
                  deviceMatchesLocked,
                  (UserPackage) this.mDevicePreferenceMap.get(new DeviceFilter(usbDevice)));
      if (shouldRestrictOverlayActivities() && smartSwitchActivityInfo == null) {
        Slog.d(str, "Setup wizard is not finished");
      } else {
        resolveActivity(intent, deviceMatchesLocked, defaultActivityLocked, usbDevice, null);
        Slog.d(str, "resolveActivity(Intent, UsbDevice) - end");
      }
    }
  }

  public final boolean shouldRestrictOverlayActivities() {
    if (Settings.Secure.getIntForUser(
            this.mContext.getContentResolver(),
            "user_setup_complete",
            1,
            UserHandle.CURRENT.getIdentifier())
        != 0) {
      return false;
    }
    Slog.d(TAG, "restricting usb overlay activities as setup is not complete");
    return true;
  }

  public void deviceAttachedForFixedHandler(UsbDevice usbDevice, ComponentName componentName) {
    Slog.d(TAG, "deviceAttachedForFixedHandler()");
    Intent createDeviceAttachedIntent = createDeviceAttachedIntent(usbDevice);
    this.mContext.sendBroadcastAsUser(
        createDeviceAttachedIntent, UserHandle.of(ActivityManager.getCurrentUser()));
    try {
      ApplicationInfo applicationInfoAsUser =
          this.mPackageManager.getApplicationInfoAsUser(
              componentName.getPackageName(), 0, this.mParentUser.getIdentifier());
      this.mSettingsManager
          .mUsbService
          .getPermissionsForUser(UserHandle.getUserId(applicationInfoAsUser.uid))
          .grantDevicePermission(usbDevice, applicationInfoAsUser.uid);
      Intent intent = new Intent(createDeviceAttachedIntent);
      intent.setComponent(componentName);
      try {
        this.mContext.startActivityAsUser(intent, this.mParentUser);
      } catch (ActivityNotFoundException unused) {
        Slog.e(TAG, "unable to start activity " + intent);
      }
    } catch (PackageManager.NameNotFoundException unused2) {
      Slog.e(
          TAG,
          "Default USB handling package ("
              + componentName.getPackageName()
              + ") not found  for user "
              + this.mParentUser);
    }
  }

  public void accessoryAttached(UsbAccessory usbAccessory) {
    ArrayList accessoryMatchesLocked;
    ActivityInfo defaultActivityLocked;
    Intent intent = new Intent("android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
    intent.putExtra("accessory", usbAccessory);
    intent.addFlags(285212672);
    synchronized (this.mLock) {
      accessoryMatchesLocked = getAccessoryMatchesLocked(usbAccessory, intent);
      defaultActivityLocked =
          getDefaultActivityLocked(
              accessoryMatchesLocked,
              (UserPackage) this.mAccessoryPreferenceMap.get(new AccessoryFilter(usbAccessory)));
    }
    sEventLogger.enqueue(new EventLogger.StringEvent("accessoryAttached: " + intent));
    resolveActivity(intent, accessoryMatchesLocked, defaultActivityLocked, null, usbAccessory);
  }

  public final void resolveActivity(
      Intent intent,
      ArrayList arrayList,
      ActivityInfo activityInfo,
      UsbDevice usbDevice,
      UsbAccessory usbAccessory) {
    ArraySet arraySet;
    if (usbDevice != null) {
      arraySet = (ArraySet) this.mDevicePreferenceDeniedMap.get(new DeviceFilter(usbDevice));
    } else {
      arraySet =
          usbAccessory != null
              ? (ArraySet) this.mAccessoryPreferenceDeniedMap.get(new AccessoryFilter(usbAccessory))
              : null;
    }
    if (arraySet != null) {
      for (int size = arrayList.size() - 1; size >= 0; size--) {
        ActivityInfo activityInfo2 = ((ResolveInfo) arrayList.get(size)).activityInfo;
        if (arraySet.contains(
            new UserPackage(
                activityInfo2.packageName,
                UserHandle.getUserHandleForUid(activityInfo2.applicationInfo.uid)))) {
          arrayList.remove(size);
        }
      }
    }
    Slog.d(TAG, "resolveActivity: intent=" + intent + " defaultActivity=" + activityInfo);
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      Slog.d(TAG, ((ResolveInfo) it.next()).toString());
    }
    if (arrayList.size() == 0) {
      if (usbAccessory != null) {
        this.mUsbHandlerManager.showUsbAccessoryUriActivity(usbAccessory, this.mParentUser);
        return;
      }
      return;
    }
    if (activityInfo != null) {
      UsbUserPermissionManager permissionsForUser =
          this.mSettingsManager.mUsbService.getPermissionsForUser(
              UserHandle.getUserId(activityInfo.applicationInfo.uid));
      if (usbDevice != null) {
        permissionsForUser.grantDevicePermission(usbDevice, activityInfo.applicationInfo.uid);
      } else if (usbAccessory != null) {
        permissionsForUser.grantAccessoryPermission(usbAccessory, activityInfo.applicationInfo.uid);
      }
      try {
        intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
        this.mContext.startActivityAsUser(
            intent, UserHandle.getUserHandleForUid(activityInfo.applicationInfo.uid));
        return;
      } catch (ActivityNotFoundException e) {
        Slog.e(TAG, "startActivity failed", e);
        return;
      }
    }
    if (arrayList.size() == 1) {
      this.mUsbHandlerManager.confirmUsbHandler(
          (ResolveInfo) arrayList.get(0), usbDevice, usbAccessory);
    } else {
      this.mUsbHandlerManager.selectUsbHandler(arrayList, this.mParentUser, intent);
    }
  }

  public final ActivityInfo getDefaultActivityLocked(ArrayList arrayList, UserPackage userPackage) {
    ActivityInfo activityInfo;
    if (CoreRune.BAIDU_CARLIFE && arrayList.size() >= 1) {
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        ResolveInfo resolveInfo = (ResolveInfo) it.next();
        ActivityInfo activityInfo2 = resolveInfo.activityInfo;
        if (activityInfo2 != null
            && BaiduCarlifeADBConnectUtils.isSamsungCarlifePkg(activityInfo2.packageName)) {
          return resolveInfo.activityInfo;
        }
      }
    }
    if (userPackage != null) {
      Iterator it2 = arrayList.iterator();
      while (it2.hasNext()) {
        ResolveInfo resolveInfo2 = (ResolveInfo) it2.next();
        ActivityInfo activityInfo3 = resolveInfo2.activityInfo;
        if (activityInfo3 != null
            && userPackage.equals(
                new UserPackage(
                    activityInfo3.packageName,
                    UserHandle.getUserHandleForUid(activityInfo3.applicationInfo.uid)))) {
          return resolveInfo2.activityInfo;
        }
      }
    }
    if (arrayList.size() == 1
        && (activityInfo = ((ResolveInfo) arrayList.get(0)).activityInfo) != null) {
      if (this.mDisablePermissionDialogs) {
        return activityInfo;
      }
      ApplicationInfo applicationInfo = activityInfo.applicationInfo;
      if (applicationInfo != null && (applicationInfo.flags & 1) != 0) {
        return activityInfo;
      }
    }
    return null;
  }

  public final boolean clearCompatibleMatchesLocked(
      UserPackage userPackage, DeviceFilter deviceFilter) {
    ArrayList arrayList = new ArrayList();
    for (DeviceFilter deviceFilter2 : this.mDevicePreferenceMap.keySet()) {
      if (deviceFilter.contains(deviceFilter2)
          && !((UserPackage) this.mDevicePreferenceMap.get(deviceFilter2)).equals(userPackage)) {
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

  public final boolean clearCompatibleMatchesLocked(
      UserPackage userPackage, AccessoryFilter accessoryFilter) {
    ArrayList arrayList = new ArrayList();
    for (AccessoryFilter accessoryFilter2 : this.mAccessoryPreferenceMap.keySet()) {
      if (accessoryFilter.contains(accessoryFilter2)
          && !((UserPackage) this.mAccessoryPreferenceMap.get(accessoryFilter2))
              .equals(userPackage)) {
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

  /* JADX WARN: Code restructure failed: missing block: B:33:0x006b, code lost:

     if (0 == 0) goto L30;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean handlePackageAddedLocked(
      UserPackage userPackage, ActivityInfo activityInfo, String str) {
    XmlResourceParser xmlResourceParser = null;
    boolean z = false;
    try {
      try {
        xmlResourceParser = activityInfo.loadXmlMetaData(this.mPackageManager, str);
      } catch (Exception e) {
        Slog.w(TAG, "Unable to load component info " + activityInfo.toString(), e);
      }
      if (xmlResourceParser == null) {
        return false;
      }
      XmlUtils.nextElement(xmlResourceParser);
      while (xmlResourceParser.getEventType() != 1) {
        String name = xmlResourceParser.getName();
        if ("usb-device".equals(name)) {
          if (clearCompatibleMatchesLocked(userPackage, DeviceFilter.read(xmlResourceParser))) {
            z = true;
            XmlUtils.nextElement(xmlResourceParser);
          } else {
            XmlUtils.nextElement(xmlResourceParser);
          }
        } else {
          if ("usb-accessory".equals(name)) {
            if (!clearCompatibleMatchesLocked(
                userPackage, AccessoryFilter.read(xmlResourceParser))) {}
            z = true;
          }
          XmlUtils.nextElement(xmlResourceParser);
        }
      }
      xmlResourceParser.close();
      return z;
    } finally {
      if (0 != 0) {
        xmlResourceParser.close();
      }
    }
  }

  public final void handlePackageAdded(UserPackage userPackage) {
    synchronized (this.mLock) {
      try {
        try {
          ActivityInfo[] activityInfoArr =
              this.mPackageManager.getPackageInfoAsUser(
                      userPackage.packageName, 129, userPackage.user.getIdentifier())
                  .activities;
          if (activityInfoArr == null) {
            return;
          }
          boolean z = false;
          for (int i = 0; i < activityInfoArr.length; i++) {
            if (handlePackageAddedLocked(
                userPackage,
                activityInfoArr[i],
                "android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
              z = true;
            }
            if (handlePackageAddedLocked(
                userPackage,
                activityInfoArr[i],
                "android.hardware.usb.action.USB_ACCESSORY_ATTACHED")) {
              z = true;
            }
          }
          if (z) {
            scheduleWriteSettingsLocked();
          }
        } catch (PackageManager.NameNotFoundException e) {
          Slog.e(TAG, "handlePackageUpdate could not find package " + userPackage, e);
        }
      } catch (Throwable th) {
        throw th;
      }
    }
  }

  public final int getSerial(UserHandle userHandle) {
    return this.mUserManager.getUserSerialNumber(userHandle.getIdentifier());
  }

  public void setDevicePackage(UsbDevice usbDevice, String str, UserHandle userHandle) {
    if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
      return;
    }
    DeviceFilter deviceFilter = new DeviceFilter(usbDevice);
    synchronized (this.mLock) {
      boolean z = true;
      if (str == null) {
        if (this.mDevicePreferenceMap.remove(deviceFilter) == null) {
          z = false;
        }
      } else {
        UserPackage userPackage = new UserPackage(str, userHandle);
        z = true ^ userPackage.equals(this.mDevicePreferenceMap.get(deviceFilter));
        if (z) {
          this.mDevicePreferenceMap.put(deviceFilter, userPackage);
        }
      }
      if (z) {
        scheduleWriteSettingsLocked();
      }
    }
  }

  public void addDevicePackagesToDenied(
      UsbDevice usbDevice, String[] strArr, UserHandle userHandle) {
    ArraySet arraySet;
    if (strArr.length == 0) {
      return;
    }
    DeviceFilter deviceFilter = new DeviceFilter(usbDevice);
    synchronized (this.mLock) {
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
    }
  }

  public void addAccessoryPackagesToDenied(
      UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) {
    ArraySet arraySet;
    if (strArr.length == 0) {
      return;
    }
    AccessoryFilter accessoryFilter = new AccessoryFilter(usbAccessory);
    synchronized (this.mLock) {
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
    }
  }

  public void removeDevicePackagesFromDenied(
      UsbDevice usbDevice, String[] strArr, UserHandle userHandle) {
    DeviceFilter deviceFilter = new DeviceFilter(usbDevice);
    synchronized (this.mLock) {
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
    }
  }

  public void removeAccessoryPackagesFromDenied(
      UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) {
    AccessoryFilter accessoryFilter = new AccessoryFilter(usbAccessory);
    synchronized (this.mLock) {
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
    }
  }

  public void setAccessoryPackage(UsbAccessory usbAccessory, String str, UserHandle userHandle) {
    if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
      return;
    }
    AccessoryFilter accessoryFilter = new AccessoryFilter(usbAccessory);
    synchronized (this.mLock) {
      boolean z = true;
      if (str == null) {
        if (this.mAccessoryPreferenceMap.remove(accessoryFilter) == null) {
          z = false;
        }
      } else {
        UserPackage userPackage = new UserPackage(str, userHandle);
        z = true ^ userPackage.equals(this.mAccessoryPreferenceMap.get(accessoryFilter));
        if (z) {
          this.mAccessoryPreferenceMap.put(accessoryFilter, userPackage);
        }
      }
      if (z) {
        scheduleWriteSettingsLocked();
      }
    }
  }

  public boolean hasDefaults(String str, UserHandle userHandle) {
    if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
      return false;
    }
    UserPackage userPackage = new UserPackage(str, userHandle);
    synchronized (this.mLock) {
      if (this.mDevicePreferenceMap.values().contains(userPackage)) {
        return true;
      }
      return this.mAccessoryPreferenceMap.values().contains(userPackage);
    }
  }

  public void clearDefaults(String str, UserHandle userHandle) {
    if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
      return;
    }
    UserPackage userPackage = new UserPackage(str, userHandle);
    synchronized (this.mLock) {
      if (clearPackageDefaultsLocked(userPackage)) {
        scheduleWriteSettingsLocked();
      }
    }
  }

  public final boolean clearPackageDefaultsLocked(UserPackage userPackage) {
    boolean z;
    synchronized (this.mLock) {
      if (this.mDevicePreferenceMap.containsValue(userPackage)) {
        z = false;
        for (DeviceFilter deviceFilter :
            (DeviceFilter[]) this.mDevicePreferenceMap.keySet().toArray(new DeviceFilter[0])) {
          if (userPackage.equals(this.mDevicePreferenceMap.get(deviceFilter))) {
            this.mDevicePreferenceMap.remove(deviceFilter);
            z = true;
          }
        }
      } else {
        z = false;
      }
      if (this.mAccessoryPreferenceMap.containsValue(userPackage)) {
        for (AccessoryFilter accessoryFilter :
            (AccessoryFilter[])
                this.mAccessoryPreferenceMap.keySet().toArray(new AccessoryFilter[0])) {
          if (userPackage.equals(this.mAccessoryPreferenceMap.get(accessoryFilter))) {
            this.mAccessoryPreferenceMap.remove(accessoryFilter);
            z = true;
          }
        }
      }
    }
    return z;
  }

  public void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
    long start = dualDumpOutputStream.start(str, j);
    synchronized (this.mLock) {
      dualDumpOutputStream.write(
          "parent_user_id", 1120986464257L, this.mParentUser.getIdentifier());
      for (DeviceFilter deviceFilter : this.mDevicePreferenceMap.keySet()) {
        long start2 = dualDumpOutputStream.start("device_preferences", 2246267895810L);
        deviceFilter.dump(dualDumpOutputStream, "filter", 1146756268033L);
        ((UserPackage) this.mDevicePreferenceMap.get(deviceFilter))
            .dump(dualDumpOutputStream, "user_package", 1146756268034L);
        dualDumpOutputStream.end(start2);
      }
      for (AccessoryFilter accessoryFilter : this.mAccessoryPreferenceMap.keySet()) {
        long start3 = dualDumpOutputStream.start("accessory_preferences", 2246267895811L);
        accessoryFilter.dump(dualDumpOutputStream, "filter", 1146756268033L);
        ((UserPackage) this.mAccessoryPreferenceMap.get(accessoryFilter))
            .dump(dualDumpOutputStream, "user_package", 1146756268034L);
        dualDumpOutputStream.end(start3);
      }
    }
    sEventLogger.dump(new DualOutputStreamDumpSink(dualDumpOutputStream, 1138166333444L));
    dualDumpOutputStream.end(start);
  }

  public static Intent createDeviceAttachedIntent(UsbDevice usbDevice) {
    Intent intent = new Intent("android.hardware.usb.action.USB_DEVICE_ATTACHED");
    intent.putExtra("device", usbDevice);
    intent.addFlags(285212672);
    return intent;
  }
}
