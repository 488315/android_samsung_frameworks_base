package com.android.server.notification;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseSetArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.function.TriPredicate;
import com.android.internal.util.jobs.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.utils.TimingsTraceAndSlog;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class ManagedServices {
  public final boolean DEBUG;
  public final String TAG;
  public int mApprovalLevel;
  public final ArrayMap mApproved;
  public final Config mConfig;
  public final Context mContext;
  public final ArraySet mDefaultComponents;
  public final ArraySet mDefaultPackages;
  public final Object mDefaultsLock;
  public final ArraySet mEnabledServicesForCurrentProfiles;
  public final ArraySet mEnabledServicesPackageNames;
  public final Handler mHandler;
  public ArrayMap mIsUserChanged;
  public final Object mMutex;
  public final IPackageManager mPm;
  public final ArrayList mServices;
  public final ArrayList mServicesBound;
  public final ArraySet mServicesRebinding;
  public final SparseSetArray mSnoozing;
  public final UserManager mUm;
  public boolean mUseXml;
  public final UserProfiles mUserProfiles;
  public ArrayMap mUserSetServices;

  public class Config {
    public String bindPermission;
    public String caption;
    public int clientLabel;
    public String secondarySettingName;
    public String secureSettingName;
    public String serviceInterface;
    public String settingsAction;
    public String xmlTag;
  }

  public abstract IInterface asInterface(IBinder iBinder);

  public abstract boolean checkType(IInterface iInterface);

  public void clearApprovedList(String str) {}

  public abstract void ensureFilters(ServiceInfo serviceInfo, int i);

  public int getBindFlags() {
    return 83886081;
  }

  public abstract Config getConfig();

  public abstract String getRequiredPermission();

  public abstract void loadDefaultsFromConfig();

  public void onBootPhaseAppsCanStart() {}

  public abstract void onServiceAdded(ManagedServiceInfo managedServiceInfo);

  public abstract void onServiceRemovedLocked(ManagedServiceInfo managedServiceInfo);

  public void readExtraAttributes(String str, TypedXmlPullParser typedXmlPullParser, int i) {}

  public void readExtraTag(String str, TypedXmlPullParser typedXmlPullParser) {}

  public boolean shouldReflectToSettings() {
    return false;
  }

  public void upgradeUserSet() {}

  public void writeExtraAttributes(TypedXmlSerializer typedXmlSerializer, int i) {}

  public void writeExtraXmlTags(TypedXmlSerializer typedXmlSerializer) {}

  public ManagedServices(
      Context context, Object obj, UserProfiles userProfiles, IPackageManager iPackageManager) {
    String simpleName = getClass().getSimpleName();
    this.TAG = simpleName;
    this.DEBUG = Log.isLoggable(simpleName, 3);
    this.mHandler = new Handler(Looper.getMainLooper());
    this.mServices = new ArrayList();
    this.mServicesBound = new ArrayList();
    this.mServicesRebinding = new ArraySet();
    this.mDefaultsLock = new Object();
    this.mDefaultComponents = new ArraySet();
    this.mDefaultPackages = new ArraySet();
    this.mEnabledServicesForCurrentProfiles = new ArraySet();
    this.mEnabledServicesPackageNames = new ArraySet();
    this.mSnoozing = new SparseSetArray();
    this.mApproved = new ArrayMap();
    this.mUserSetServices = new ArrayMap();
    this.mIsUserChanged = new ArrayMap();
    this.mContext = context;
    this.mMutex = obj;
    this.mUserProfiles = userProfiles;
    this.mPm = iPackageManager;
    this.mConfig = getConfig();
    this.mApprovalLevel = 1;
    this.mUm = (UserManager) context.getSystemService("user");
  }

  public final String getCaption() {
    return this.mConfig.caption;
  }

  public List getServices() {
    ArrayList arrayList;
    synchronized (this.mMutex) {
      arrayList = new ArrayList(this.mServices);
    }
    return arrayList;
  }

  public void addDefaultComponentOrPackage(String str) {
    if (TextUtils.isEmpty(str)) {
      return;
    }
    synchronized (this.mDefaultsLock) {
      if (this.mApprovalLevel == 0) {
        this.mDefaultPackages.add(str);
        return;
      }
      ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
      if (unflattenFromString == null || this.mApprovalLevel != 1) {
        return;
      }
      this.mDefaultPackages.add(unflattenFromString.getPackageName());
      this.mDefaultComponents.add(unflattenFromString);
    }
  }

  public boolean isDefaultComponentOrPackage(String str) {
    synchronized (this.mDefaultsLock) {
      ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
      if (unflattenFromString == null) {
        return this.mDefaultPackages.contains(str);
      }
      return this.mDefaultComponents.contains(unflattenFromString);
    }
  }

  public ArraySet getDefaultComponents() {
    ArraySet arraySet;
    synchronized (this.mDefaultsLock) {
      arraySet = new ArraySet(this.mDefaultComponents);
    }
    return arraySet;
  }

  public ArraySet getDefaultPackages() {
    ArraySet arraySet;
    synchronized (this.mDefaultsLock) {
      arraySet = new ArraySet(this.mDefaultPackages);
    }
    return arraySet;
  }

  public ArrayMap resetComponents(String str, int i) {
    ArrayList arrayList;
    ArrayList arrayList2;
    boolean z;
    ArraySet arraySet = new ArraySet(getAllowedComponents(i));
    synchronized (this.mDefaultsLock) {
      arrayList = new ArrayList(this.mDefaultComponents.size());
      arrayList2 = new ArrayList(this.mDefaultComponents.size());
      for (int i2 = 0; i2 < this.mDefaultComponents.size() && arraySet.size() > 0; i2++) {
        ComponentName componentName = (ComponentName) this.mDefaultComponents.valueAt(i2);
        if (str.equals(componentName.getPackageName()) && !arraySet.contains(componentName)) {
          arrayList.add(componentName);
        }
      }
      synchronized (this.mApproved) {
        ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i));
        if (arrayMap != null) {
          int size = arrayMap.size();
          z = false;
          for (int i3 = 0; i3 < size; i3++) {
            ArraySet arraySet2 = (ArraySet) arrayMap.valueAt(i3);
            for (int i4 = 0; i4 < arraySet.size(); i4++) {
              ComponentName componentName2 = (ComponentName) arraySet.valueAt(i4);
              if (str.equals(componentName2.getPackageName())
                  && !this.mDefaultComponents.contains(componentName2)
                  && arraySet2.remove(componentName2.flattenToString())) {
                arrayList2.add(componentName2);
                clearUserSetFlagLocked(componentName2, i);
                z = true;
              }
            }
            for (int i5 = 0; i5 < arrayList.size(); i5++) {
              z |= arraySet2.add(((ComponentName) arrayList.get(i5)).flattenToString());
            }
          }
        } else {
          z = false;
        }
      }
    }
    if (z) {
      rebindServices(false, -1);
    }
    ArrayMap arrayMap2 = new ArrayMap();
    arrayMap2.put(Boolean.TRUE, arrayList);
    arrayMap2.put(Boolean.FALSE, arrayList2);
    return arrayMap2;
  }

  public final boolean clearUserSetFlagLocked(ComponentName componentName, int i) {
    String approvedValue = getApprovedValue(componentName.flattenToString());
    ArraySet arraySet = (ArraySet) this.mUserSetServices.get(Integer.valueOf(i));
    return arraySet != null && arraySet.remove(approvedValue);
  }

  public final ManagedServiceInfo newServiceInfo(
      IInterface iInterface,
      ComponentName componentName,
      int i,
      boolean z,
      ServiceConnection serviceConnection,
      int i2,
      int i3) {
    return new ManagedServiceInfo(iInterface, componentName, i, z, serviceConnection, i2, i3);
  }

  public void dump(PrintWriter printWriter, NotificationManagerService.DumpFilter dumpFilter) {
    int i;
    SparseSetArray sparseSetArray;
    printWriter.println("    Allowed " + getCaption() + "s:");
    synchronized (this.mApproved) {
      int size = this.mApproved.size();
      for (int i2 = 0; i2 < size; i2++) {
        int intValue = ((Integer) this.mApproved.keyAt(i2)).intValue();
        ArrayMap arrayMap = (ArrayMap) this.mApproved.valueAt(i2);
        Boolean bool = (Boolean) this.mIsUserChanged.get(Integer.valueOf(intValue));
        if (arrayMap != null) {
          int size2 = arrayMap.size();
          for (int i3 = 0; i3 < size2; i3++) {
            boolean booleanValue = ((Boolean) arrayMap.keyAt(i3)).booleanValue();
            ArraySet arraySet = (ArraySet) arrayMap.valueAt(i3);
            if (arrayMap.size() > 0) {
              StringBuilder sb = new StringBuilder();
              sb.append("      ");
              sb.append(String.join(XmlUtils.STRING_ARRAY_SEPARATOR, arraySet));
              sb.append(" (user: ");
              sb.append(intValue);
              sb.append(" isPrimary: ");
              sb.append(booleanValue);
              sb.append(bool == null ? "" : " isUserChanged: " + bool);
              sb.append(")");
              printWriter.println(sb.toString());
            }
          }
        }
      }
      printWriter.println("    Has user set:");
      Iterator it = this.mUserSetServices.keySet().iterator();
      while (it.hasNext()) {
        int intValue2 = ((Integer) it.next()).intValue();
        if (this.mIsUserChanged.get(Integer.valueOf(intValue2)) == null) {
          printWriter.println(
              "      userId="
                  + intValue2
                  + " value="
                  + this.mUserSetServices.get(Integer.valueOf(intValue2)));
        }
      }
    }
    synchronized (this.mMutex) {
      printWriter.println(
          "    All "
              + getCaption()
              + "s ("
              + this.mEnabledServicesForCurrentProfiles.size()
              + ") enabled for current profiles:");
      Iterator it2 = this.mEnabledServicesForCurrentProfiles.iterator();
      while (it2.hasNext()) {
        ComponentName componentName = (ComponentName) it2.next();
        if (dumpFilter == null || dumpFilter.matches(componentName)) {
          printWriter.println("      " + componentName);
        }
      }
      printWriter.println("    Live " + getCaption() + "s (" + this.mServices.size() + "):");
      Iterator it3 = this.mServices.iterator();
      while (it3.hasNext()) {
        ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) it3.next();
        if (dumpFilter == null || dumpFilter.matches(managedServiceInfo.component)) {
          StringBuilder sb2 = new StringBuilder();
          sb2.append("      ");
          sb2.append(managedServiceInfo.component);
          sb2.append(" (user ");
          sb2.append(managedServiceInfo.userid);
          sb2.append("): ");
          sb2.append(managedServiceInfo.service);
          sb2.append(managedServiceInfo.isSystem ? " SYSTEM" : "");
          sb2.append(managedServiceInfo.isGuest(this) ? " GUEST" : "");
          printWriter.println(sb2.toString());
        }
      }
    }
    synchronized (this.mSnoozing) {
      sparseSetArray = new SparseSetArray(this.mSnoozing);
    }
    printWriter.println("    Snoozed " + getCaption() + "s (" + sparseSetArray.size() + "):");
    for (i = 0; i < sparseSetArray.size(); i++) {
      printWriter.println("      User: " + sparseSetArray.keyAt(i));
      Iterator it4 = sparseSetArray.valuesAt(i).iterator();
      while (it4.hasNext()) {
        ComponentName componentName2 = (ComponentName) it4.next();
        ServiceInfo serviceInfo = getServiceInfo(componentName2, sparseSetArray.keyAt(i));
        StringBuilder sb3 = new StringBuilder();
        sb3.append("        ");
        sb3.append(componentName2.flattenToShortString());
        sb3.append(isAutobindAllowed(serviceInfo) ? "" : " (META_DATA_DEFAULT_AUTOBIND=false)");
        printWriter.println(sb3.toString());
      }
    }
  }

  public void dump(
      ProtoOutputStream protoOutputStream, NotificationManagerService.DumpFilter dumpFilter) {
    int i;
    protoOutputStream.write(1138166333441L, getCaption());
    synchronized (this.mApproved) {
      int size = this.mApproved.size();
      int i2 = 0;
      while (true) {
        long j = 2246267895810L;
        if (i2 >= size) {
          break;
        }
        int intValue = ((Integer) this.mApproved.keyAt(i2)).intValue();
        ArrayMap arrayMap = (ArrayMap) this.mApproved.valueAt(i2);
        if (arrayMap != null) {
          int size2 = arrayMap.size();
          int i3 = 0;
          while (i3 < size2) {
            boolean booleanValue = ((Boolean) arrayMap.keyAt(i3)).booleanValue();
            ArraySet arraySet = (ArraySet) arrayMap.valueAt(i3);
            if (arrayMap.size() > 0) {
              i = i2;
              long start = protoOutputStream.start(j);
              Iterator it = arraySet.iterator();
              while (it.hasNext()) {
                protoOutputStream.write(2237677961217L, (String) it.next());
              }
              protoOutputStream.write(1120986464258L, intValue);
              protoOutputStream.write(1133871366147L, booleanValue);
              protoOutputStream.end(start);
            } else {
              i = i2;
            }
            i3++;
            i2 = i;
            j = 2246267895810L;
          }
        }
        i2++;
      }
    }
    synchronized (this.mMutex) {
      Iterator it2 = this.mEnabledServicesForCurrentProfiles.iterator();
      while (it2.hasNext()) {
        ComponentName componentName = (ComponentName) it2.next();
        if (dumpFilter == null || dumpFilter.matches(componentName)) {
          componentName.dumpDebug(protoOutputStream, 2246267895811L);
        }
      }
      Iterator it3 = this.mServices.iterator();
      while (it3.hasNext()) {
        ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) it3.next();
        if (dumpFilter == null || dumpFilter.matches(managedServiceInfo.component)) {
          managedServiceInfo.dumpDebug(protoOutputStream, 2246267895812L, this);
        }
      }
    }
    synchronized (this.mSnoozing) {
      for (int i4 = 0; i4 < this.mSnoozing.size(); i4++) {
        long start2 = protoOutputStream.start(2246267895814L);
        protoOutputStream.write(1120986464257L, this.mSnoozing.keyAt(i4));
        Iterator it4 = this.mSnoozing.valuesAt(i4).iterator();
        while (it4.hasNext()) {
          ((ComponentName) it4.next()).dumpDebug(protoOutputStream, 2246267895810L);
        }
        protoOutputStream.end(start2);
      }
    }
  }

  public void onSettingRestored(String str, String str2, int i, int i2) {
    String str3;
    if (this.mUseXml) {
      return;
    }
    Slog.d(this.TAG, "Restored managed service setting: " + str);
    if (this.mConfig.secureSettingName.equals(str)
        || ((str3 = this.mConfig.secondarySettingName) != null && str3.equals(str))) {
      if (i < 26) {
        String approved = getApproved(i2, this.mConfig.secureSettingName.equals(str));
        if (!TextUtils.isEmpty(approved)) {
          if (TextUtils.isEmpty(str2)) {
            str2 = approved;
          } else {
            str2 = str2 + XmlUtils.STRING_ARRAY_SEPARATOR + approved;
          }
        }
      }
      if (shouldReflectToSettings()) {
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), str, str2, i2);
      }
      Iterator it = this.mUm.getUsers().iterator();
      while (it.hasNext()) {
        addApprovedList(
            str2, ((UserInfo) it.next()).id, this.mConfig.secureSettingName.equals(str));
      }
      Slog.d(this.TAG, "Done loading approved values from settings");
      rebindServices(false, i2);
    }
  }

  public void writeDefaults(TypedXmlSerializer typedXmlSerializer) {
    synchronized (this.mDefaultsLock) {
      ArrayList arrayList = new ArrayList(this.mDefaultComponents.size());
      for (int i = 0; i < this.mDefaultComponents.size(); i++) {
        arrayList.add(((ComponentName) this.mDefaultComponents.valueAt(i)).flattenToString());
      }
      typedXmlSerializer.attribute(
          (String) null, "defaults", String.join(XmlUtils.STRING_ARRAY_SEPARATOR, arrayList));
    }
  }

  public void writeXml(TypedXmlSerializer typedXmlSerializer, boolean z, int i) {
    typedXmlSerializer.startTag((String) null, getConfig().xmlTag);
    typedXmlSerializer.attributeInt((String) null, "version", Integer.parseInt("4"));
    writeDefaults(typedXmlSerializer);
    if (z) {
      trimApprovedListsAccordingToInstalledServices(i);
    }
    synchronized (this.mApproved) {
      int size = this.mApproved.size();
      for (int i2 = 0; i2 < size; i2++) {
        int intValue = ((Integer) this.mApproved.keyAt(i2)).intValue();
        if (!z || intValue == i) {
          ArrayMap arrayMap = (ArrayMap) this.mApproved.valueAt(i2);
          Boolean bool = (Boolean) this.mIsUserChanged.get(Integer.valueOf(intValue));
          if (arrayMap != null) {
            int size2 = arrayMap.size();
            for (int i3 = 0; i3 < size2; i3++) {
              boolean booleanValue = ((Boolean) arrayMap.keyAt(i3)).booleanValue();
              Set set = (Set) arrayMap.valueAt(i3);
              Set set2 = (Set) this.mUserSetServices.get(Integer.valueOf(intValue));
              if (set != null || set2 != null || bool != null) {
                String join = set == null ? "" : String.join(XmlUtils.STRING_ARRAY_SEPARATOR, set);
                typedXmlSerializer.startTag((String) null, "service_listing");
                typedXmlSerializer.attribute((String) null, "approved", join);
                typedXmlSerializer.attributeInt((String) null, "user", intValue);
                typedXmlSerializer.attributeBoolean((String) null, "primary", booleanValue);
                if (bool != null) {
                  typedXmlSerializer.attributeBoolean(
                      (String) null, "user_changed", bool.booleanValue());
                } else if (set2 != null) {
                  typedXmlSerializer.attribute(
                      (String) null,
                      "user_set_services",
                      String.join(XmlUtils.STRING_ARRAY_SEPARATOR, set2));
                }
                writeExtraAttributes(typedXmlSerializer, intValue);
                typedXmlSerializer.endTag((String) null, "service_listing");
                if (!z && booleanValue && shouldReflectToSettings()) {
                  Settings.Secure.putStringForUser(
                      this.mContext.getContentResolver(),
                      getConfig().secureSettingName,
                      join,
                      intValue);
                }
              }
            }
          }
        }
      }
    }
    writeExtraXmlTags(typedXmlSerializer);
    typedXmlSerializer.endTag((String) null, getConfig().xmlTag);
  }

  public final void migrateToXml() {
    for (UserInfo userInfo : this.mUm.getUsers()) {
      ContentResolver contentResolver = this.mContext.getContentResolver();
      if (!TextUtils.isEmpty(getConfig().secureSettingName)) {
        addApprovedList(
            Settings.Secure.getStringForUser(
                contentResolver, getConfig().secureSettingName, userInfo.id),
            userInfo.id,
            true);
      }
      if (!TextUtils.isEmpty(getConfig().secondarySettingName)) {
        addApprovedList(
            Settings.Secure.getStringForUser(
                contentResolver, getConfig().secondarySettingName, userInfo.id),
            userInfo.id,
            false);
      }
    }
  }

  public void readDefaults(TypedXmlPullParser typedXmlPullParser) {
    String readStringAttribute =
        com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "defaults");
    if (TextUtils.isEmpty(readStringAttribute)) {
      return;
    }
    String[] split = readStringAttribute.split(XmlUtils.STRING_ARRAY_SEPARATOR);
    synchronized (this.mDefaultsLock) {
      for (int i = 0; i < split.length; i++) {
        if (!TextUtils.isEmpty(split[i])) {
          ComponentName unflattenFromString = ComponentName.unflattenFromString(split[i]);
          if (unflattenFromString != null) {
            this.mDefaultPackages.add(unflattenFromString.getPackageName());
            this.mDefaultComponents.add(unflattenFromString);
          } else {
            this.mDefaultPackages.add(split[i]);
          }
        }
      }
    }
  }

  public void readXml(
      TypedXmlPullParser typedXmlPullParser, TriPredicate triPredicate, boolean z, int i) {
    boolean z2;
    String readStringAttribute =
        com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "version");
    readDefaults(typedXmlPullParser);
    boolean z3 = false;
    while (true) {
      int next = typedXmlPullParser.next();
      z2 = true;
      if (next == 1) {
        break;
      }
      String name = typedXmlPullParser.getName();
      if (next == 3 && getConfig().xmlTag.equals(name)) {
        break;
      }
      if (next == 2) {
        if ("service_listing".equals(name)) {
          Slog.i(this.TAG, "Read " + this.mConfig.caption + " permissions from xml");
          String readStringAttribute2 =
              com.android.internal.util.XmlUtils.readStringAttribute(
                  typedXmlPullParser, "approved");
          int attributeInt = z ? i : typedXmlPullParser.getAttributeInt((String) null, "user", 0);
          boolean attributeBoolean =
              typedXmlPullParser.getAttributeBoolean((String) null, "primary", true);
          String readStringAttribute3 =
              com.android.internal.util.XmlUtils.readStringAttribute(
                  typedXmlPullParser, "user_changed");
          String readStringAttribute4 =
              com.android.internal.util.XmlUtils.readStringAttribute(
                  typedXmlPullParser, "user_set");
          String readStringAttribute5 =
              com.android.internal.util.XmlUtils.readStringAttribute(
                  typedXmlPullParser, "user_set_services");
          if (!"4".equals(readStringAttribute)) {
            if (readStringAttribute5 == null) {
              if (readStringAttribute4 == null
                  || !Boolean.valueOf(readStringAttribute4).booleanValue()) {
                readStringAttribute5 = "";
              } else {
                synchronized (this.mApproved) {
                  this.mIsUserChanged.put(Integer.valueOf(attributeInt), Boolean.TRUE);
                }
                z3 = false;
                readStringAttribute5 = readStringAttribute2;
              }
            }
            z3 = true;
          } else if (readStringAttribute3 == null) {
            readStringAttribute5 = TextUtils.emptyIfNull(readStringAttribute5);
          } else {
            synchronized (this.mApproved) {
              this.mIsUserChanged.put(
                  Integer.valueOf(attributeInt), Boolean.valueOf(readStringAttribute3));
            }
            readStringAttribute5 =
                Boolean.valueOf(readStringAttribute3).booleanValue() ? readStringAttribute2 : "";
            if (z && readStringAttribute2.isEmpty()) {
              clearApprovedList(readStringAttribute2);
            }
          }
          readExtraAttributes(name, typedXmlPullParser, attributeInt);
          if (triPredicate == null
              || triPredicate.test(
                  getPackageName(readStringAttribute2),
                  Integer.valueOf(attributeInt),
                  getRequiredPermission())
              || readStringAttribute2.isEmpty()) {
            if (this.mUm.getUserInfo(attributeInt) != null) {
              addApprovedList(
                  readStringAttribute2, attributeInt, attributeBoolean, readStringAttribute5);
            }
            this.mUseXml = true;
          }
        } else {
          readExtraTag(name, typedXmlPullParser);
        }
      }
    }
    if (!TextUtils.isEmpty(readStringAttribute)
        && !"1".equals(readStringAttribute)
        && !"2".equals(readStringAttribute)
        && !"3".equals(readStringAttribute)) {
      z2 = false;
    }
    if (z2) {
      upgradeDefaultsXmlVersion();
    }
    if (z3) {
      upgradeUserSet();
    }
    rebindServices(false, -1);
  }

  public void upgradeDefaultsXmlVersion() {
    int size;
    int size2;
    synchronized (this.mDefaultsLock) {
      size = this.mDefaultComponents.size() + this.mDefaultPackages.size();
    }
    if (size == 0) {
      if (this.mApprovalLevel == 1) {
        List allowedComponents = getAllowedComponents(0);
        for (int i = 0; i < allowedComponents.size(); i++) {
          addDefaultComponentOrPackage(
              ((ComponentName) allowedComponents.get(i)).flattenToString());
        }
      }
      if (this.mApprovalLevel == 0) {
        List allowedPackages = getAllowedPackages(0);
        for (int i2 = 0; i2 < allowedPackages.size(); i2++) {
          addDefaultComponentOrPackage((String) allowedPackages.get(i2));
        }
      }
    }
    synchronized (this.mDefaultsLock) {
      size2 = this.mDefaultComponents.size() + this.mDefaultPackages.size();
    }
    if (size2 == 0) {
      loadDefaultsFromConfig();
    }
  }

  public void addApprovedList(String str, int i, boolean z) {
    addApprovedList(str, i, z, str);
  }

  public void addApprovedList(String str, int i, boolean z, String str2) {
    if (TextUtils.isEmpty(str)) {
      str = "";
    }
    if (str2 == null) {
      str2 = str;
    }
    synchronized (this.mApproved) {
      ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i));
      if (arrayMap == null) {
        arrayMap = new ArrayMap();
        this.mApproved.put(Integer.valueOf(i), arrayMap);
      }
      ArraySet arraySet = (ArraySet) arrayMap.get(Boolean.valueOf(z));
      if (arraySet == null) {
        arraySet = new ArraySet();
        arrayMap.put(Boolean.valueOf(z), arraySet);
      }
      for (String str3 : str.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
        String approvedValue = getApprovedValue(str3);
        if (approvedValue != null) {
          arraySet.add(approvedValue);
        }
      }
      ArraySet arraySet2 = (ArraySet) this.mUserSetServices.get(Integer.valueOf(i));
      if (arraySet2 == null) {
        arraySet2 = new ArraySet();
        this.mUserSetServices.put(Integer.valueOf(i), arraySet2);
      }
      for (String str4 : str2.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
        String approvedValue2 = getApprovedValue(str4);
        if (approvedValue2 != null) {
          arraySet2.add(approvedValue2);
        }
      }
    }
  }

  public boolean isComponentEnabledForPackage(String str) {
    boolean contains;
    synchronized (this.mMutex) {
      contains = this.mEnabledServicesPackageNames.contains(str);
    }
    return contains;
  }

  public void setPackageOrComponentEnabled(String str, int i, boolean z, boolean z2) {
    setPackageOrComponentEnabled(str, i, z, z2, true);
  }

  public void setPackageOrComponentEnabled(String str, int i, boolean z, boolean z2, boolean z3) {
    String str2 = this.TAG;
    StringBuilder sb = new StringBuilder();
    sb.append(z2 ? " Allowing " : "Disallowing ");
    sb.append(this.mConfig.caption);
    sb.append(" ");
    sb.append(str);
    sb.append(" (userSet: ");
    sb.append(z3);
    sb.append(")");
    Slog.i(str2, sb.toString());
    synchronized (this.mApproved) {
      ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i));
      if (arrayMap == null) {
        arrayMap = new ArrayMap();
        this.mApproved.put(Integer.valueOf(i), arrayMap);
      }
      ArraySet arraySet = (ArraySet) arrayMap.get(Boolean.valueOf(z));
      if (arraySet == null) {
        arraySet = new ArraySet();
        arrayMap.put(Boolean.valueOf(z), arraySet);
      }
      String approvedValue = getApprovedValue(str);
      if (approvedValue != null) {
        if (z2) {
          arraySet.add(approvedValue);
        } else {
          arraySet.remove(approvedValue);
        }
      }
      ArraySet arraySet2 = (ArraySet) this.mUserSetServices.get(Integer.valueOf(i));
      if (arraySet2 == null) {
        arraySet2 = new ArraySet();
        this.mUserSetServices.put(Integer.valueOf(i), arraySet2);
      }
      if (z3) {
        arraySet2.add(str);
      } else {
        arraySet2.remove(str);
      }
    }
    rebindServices(false, i);
  }

  public final String getApprovedValue(String str) {
    if (this.mApprovalLevel == 1) {
      if (ComponentName.unflattenFromString(str) != null) {
        return str;
      }
      return null;
    }
    return getPackageName(str);
  }

  public String getApproved(int i, boolean z) {
    String join;
    synchronized (this.mApproved) {
      join =
          String.join(
              XmlUtils.STRING_ARRAY_SEPARATOR,
              (ArraySet)
                  ((ArrayMap) this.mApproved.getOrDefault(Integer.valueOf(i), new ArrayMap()))
                      .getOrDefault(Boolean.valueOf(z), new ArraySet()));
    }
    return join;
  }

  public List getAllowedComponents(int i) {
    ArrayList arrayList = new ArrayList();
    synchronized (this.mApproved) {
      ArrayMap arrayMap =
          (ArrayMap) this.mApproved.getOrDefault(Integer.valueOf(i), new ArrayMap());
      for (int i2 = 0; i2 < arrayMap.size(); i2++) {
        ArraySet arraySet = (ArraySet) arrayMap.valueAt(i2);
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
          ComponentName unflattenFromString =
              ComponentName.unflattenFromString((String) arraySet.valueAt(i3));
          if (unflattenFromString != null) {
            arrayList.add(unflattenFromString);
          }
        }
      }
    }
    return arrayList;
  }

  public List getAllowedPackages(int i) {
    ArrayList arrayList = new ArrayList();
    synchronized (this.mApproved) {
      ArrayMap arrayMap =
          (ArrayMap) this.mApproved.getOrDefault(Integer.valueOf(i), new ArrayMap());
      for (int i2 = 0; i2 < arrayMap.size(); i2++) {
        ArraySet arraySet = (ArraySet) arrayMap.valueAt(i2);
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
          String packageName = getPackageName((String) arraySet.valueAt(i3));
          if (!TextUtils.isEmpty(packageName)) {
            arrayList.add(packageName);
          }
        }
      }
    }
    return arrayList;
  }

  public boolean isPackageOrComponentAllowed(String str, int i) {
    synchronized (this.mApproved) {
      ArrayMap arrayMap =
          (ArrayMap) this.mApproved.getOrDefault(Integer.valueOf(i), new ArrayMap());
      for (int i2 = 0; i2 < arrayMap.size(); i2++) {
        if (((ArraySet) arrayMap.valueAt(i2)).contains(str)) {
          return true;
        }
      }
      return false;
    }
  }

  public boolean isPackageOrComponentAllowedWithPermission(ComponentName componentName, int i) {
    if (isPackageOrComponentAllowed(componentName.flattenToString(), i)
        || isPackageOrComponentAllowed(componentName.getPackageName(), i)) {
      return componentHasBindPermission(componentName, i);
    }
    return false;
  }

  public final boolean componentHasBindPermission(ComponentName componentName, int i) {
    ServiceInfo serviceInfo = getServiceInfo(componentName, i);
    if (serviceInfo == null) {
      return false;
    }
    return this.mConfig.bindPermission.equals(serviceInfo.permission);
  }

  public boolean isPackageOrComponentUserSet(String str, int i) {
    boolean z;
    synchronized (this.mApproved) {
      ArraySet arraySet = (ArraySet) this.mUserSetServices.get(Integer.valueOf(i));
      z = arraySet != null && arraySet.contains(str);
    }
    return z;
  }

  public boolean isPackageAllowed(String str, int i) {
    if (str == null) {
      return false;
    }
    synchronized (this.mApproved) {
      ArrayMap arrayMap =
          (ArrayMap) this.mApproved.getOrDefault(Integer.valueOf(i), new ArrayMap());
      for (int i2 = 0; i2 < arrayMap.size(); i2++) {
        Iterator it = ((ArraySet) arrayMap.valueAt(i2)).iterator();
        while (it.hasNext()) {
          String str2 = (String) it.next();
          ComponentName unflattenFromString = ComponentName.unflattenFromString(str2);
          if (unflattenFromString != null) {
            if (str.equals(unflattenFromString.getPackageName())) {
              return true;
            }
          } else if (str.equals(str2)) {
            return true;
          }
        }
      }
      return false;
    }
  }

  public void onPackagesChanged(boolean z, String[] strArr, int[] iArr) {
    boolean z2;
    if (this.DEBUG) {
      synchronized (this.mMutex) {
        String str = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onPackagesChanged removingPackage=");
        sb.append(z);
        sb.append(" pkgList=");
        sb.append(strArr == null ? null : Arrays.asList(strArr));
        sb.append(" mEnabledServicesPackageNames=");
        sb.append(this.mEnabledServicesPackageNames);
        Slog.d(str, sb.toString());
      }
    }
    if (strArr == null || strArr.length <= 0) {
      return;
    }
    if (!z || iArr == null) {
      z2 = false;
    } else {
      int min = Math.min(strArr.length, iArr.length);
      z2 = false;
      for (int i = 0; i < min; i++) {
        z2 = removeUninstalledItemsFromApprovedLists(UserHandle.getUserId(iArr[i]), strArr[i]);
      }
    }
    for (String str2 : strArr) {
      if (isComponentEnabledForPackage(str2)) {
        z2 = true;
      }
      if (iArr != null && iArr.length > 0) {
        for (int i2 : iArr) {
          if (isPackageAllowed(str2, UserHandle.getUserId(i2))) {
            trimApprovedListsForInvalidServices(str2, UserHandle.getUserId(i2));
            z2 = true;
          }
        }
      }
    }
    if (z2) {
      rebindServices(false, -1);
    }
  }

  public void onUserRemoved(int i) {
    Slog.i(this.TAG, "Removing approved services for removed user " + i);
    synchronized (this.mApproved) {
      this.mApproved.remove(Integer.valueOf(i));
    }
    synchronized (this.mSnoozing) {
      this.mSnoozing.remove(i);
    }
    rebindServices(true, i);
  }

  public void onUserSwitched(int i) {
    if (this.DEBUG) {
      Slog.d(this.TAG, "onUserSwitched u=" + i);
    }
    unbindOtherUserServices(i);
    rebindServices(true, i);
  }

  public void onUserUnlocked(int i) {
    if (this.DEBUG) {
      Slog.d(this.TAG, "onUserUnlocked u=" + i);
    }
    rebindServices(false, i);
  }

  public final ManagedServiceInfo getServiceFromTokenLocked(IInterface iInterface) {
    if (iInterface == null) {
      return null;
    }
    IBinder asBinder = iInterface.asBinder();
    synchronized (this.mMutex) {
      int size = this.mServices.size();
      for (int i = 0; i < size; i++) {
        ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) this.mServices.get(i);
        if (managedServiceInfo.service.asBinder() == asBinder) {
          return managedServiceInfo;
        }
      }
      return null;
    }
  }

  public boolean isServiceTokenValidLocked(IInterface iInterface) {
    return (iInterface == null || getServiceFromTokenLocked(iInterface) == null) ? false : true;
  }

  public ManagedServiceInfo checkServiceTokenLocked(IInterface iInterface) {
    checkNotNull(iInterface);
    ManagedServiceInfo serviceFromTokenLocked = getServiceFromTokenLocked(iInterface);
    if (serviceFromTokenLocked != null) {
      return serviceFromTokenLocked;
    }
    throw new SecurityException(
        "Disallowed call from unknown "
            + getCaption()
            + ": "
            + iInterface
            + " "
            + iInterface.getClass());
  }

  public boolean isSameUser(IInterface iInterface, int i) {
    checkNotNull(iInterface);
    synchronized (this.mMutex) {
      ManagedServiceInfo serviceFromTokenLocked = getServiceFromTokenLocked(iInterface);
      if (serviceFromTokenLocked == null) {
        return false;
      }
      return serviceFromTokenLocked.isSameUser(i);
    }
  }

  public void unregisterService(IInterface iInterface, int i) {
    checkNotNull(iInterface);
    unregisterServiceImpl(iInterface, i);
  }

  public void registerSystemService(
      IInterface iInterface, ComponentName componentName, int i, int i2) {
    checkNotNull(iInterface);
    ManagedServiceInfo registerServiceImpl =
        registerServiceImpl(iInterface, componentName, i, 10000, i2);
    if (registerServiceImpl != null) {
      onServiceAdded(registerServiceImpl);
    }
  }

  public void registerGuestService(ManagedServiceInfo managedServiceInfo) {
    checkNotNull(managedServiceInfo.service);
    if (!checkType(managedServiceInfo.service)) {
      throw new IllegalArgumentException();
    }
    if (registerServiceImpl(managedServiceInfo) != null) {
      onServiceAdded(managedServiceInfo);
    }
  }

  public void setComponentState(ComponentName componentName, int i, boolean z) {
    synchronized (this.mSnoozing) {
      if ((!this.mSnoozing.contains(i, componentName)) == z) {
        return;
      }
      if (z) {
        this.mSnoozing.remove(i, componentName);
      } else {
        this.mSnoozing.add(i, componentName);
      }
      String str = this.TAG;
      StringBuilder sb = new StringBuilder();
      sb.append(z ? "Enabling " : "Disabling ");
      sb.append("component ");
      sb.append(componentName.flattenToShortString());
      Slog.d(str, sb.toString());
      synchronized (this.mMutex) {
        if (z) {
          if (isPackageOrComponentAllowedWithPermission(componentName, i)) {
            registerServiceLocked(componentName, i);
          } else {
            Slog.d(this.TAG, componentName + " no longer has permission to be bound");
          }
        } else {
          unregisterServiceLocked(componentName, i);
        }
      }
    }
  }

  public final ArraySet loadComponentNamesFromValues(ArraySet arraySet, int i) {
    if (arraySet == null || arraySet.size() == 0) {
      return new ArraySet();
    }
    ArraySet arraySet2 = new ArraySet(arraySet.size());
    for (int i2 = 0; i2 < arraySet.size(); i2++) {
      String str = (String) arraySet.valueAt(i2);
      if (!TextUtils.isEmpty(str)) {
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString != null) {
          arraySet2.add(unflattenFromString);
        } else {
          arraySet2.addAll(queryPackageForServices(str, i));
        }
      }
    }
    return arraySet2;
  }

  public Set queryPackageForServices(String str, int i) {
    return queryPackageForServices(str, 0, i);
  }

  public ArraySet queryPackageForServices(String str, int i, int i2) {
    ArraySet arraySet = new ArraySet();
    PackageManager packageManager = this.mContext.getPackageManager();
    Intent intent = new Intent(this.mConfig.serviceInterface);
    if (!TextUtils.isEmpty(str)) {
      intent.setPackage(str);
    }
    List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, i | 132, i2);
    if (this.DEBUG) {
      Slog.v(this.TAG, this.mConfig.serviceInterface + " services: " + queryIntentServicesAsUser);
    }
    if (queryIntentServicesAsUser != null) {
      int size = queryIntentServicesAsUser.size();
      for (int i3 = 0; i3 < size; i3++) {
        ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServicesAsUser.get(i3)).serviceInfo;
        ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
        if (!this.mConfig.bindPermission.equals(serviceInfo.permission)) {
          Slog.w(
              this.TAG,
              "Skipping "
                  + getCaption()
                  + " service "
                  + serviceInfo.packageName
                  + "/"
                  + serviceInfo.name
                  + ": it does not require the permission "
                  + this.mConfig.bindPermission);
        } else {
          arraySet.add(componentName);
        }
      }
    }
    return arraySet;
  }

  public final void trimApprovedListsAccordingToInstalledServices(int i) {
    synchronized (this.mApproved) {
      ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i));
      if (arrayMap == null) {
        return;
      }
      for (int i2 = 0; i2 < arrayMap.size(); i2++) {
        ArraySet arraySet = (ArraySet) arrayMap.valueAt(i2);
        for (int size = arraySet.size() - 1; size >= 0; size--) {
          String str = (String) arraySet.valueAt(size);
          if (!isValidEntry(str, i)) {
            arraySet.removeAt(size);
            Slog.v(this.TAG, "Removing " + str + " from approved list; no matching services found");
          } else if (this.DEBUG) {
            Slog.v(this.TAG, "Keeping " + str + " on approved list; matching services found");
          }
        }
      }
    }
  }

  public final boolean removeUninstalledItemsFromApprovedLists(int i, String str) {
    synchronized (this.mApproved) {
      ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i));
      if (arrayMap != null) {
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
          ArraySet arraySet = (ArraySet) arrayMap.valueAt(i2);
          for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
            String str2 = (String) arraySet.valueAt(size2);
            if (TextUtils.equals(str, getPackageName(str2))) {
              arraySet.removeAt(size2);
              if (this.DEBUG) {
                Slog.v(this.TAG, "Removing " + str2 + " from approved list; uninstalled");
              }
            }
          }
        }
      }
    }
    return false;
  }

  public final void trimApprovedListsForInvalidServices(String str, int i) {
    ComponentName unflattenFromString;
    synchronized (this.mApproved) {
      ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i));
      if (arrayMap == null) {
        return;
      }
      for (int i2 = 0; i2 < arrayMap.size(); i2++) {
        ArraySet arraySet = (ArraySet) arrayMap.valueAt(i2);
        for (int size = arraySet.size() - 1; size >= 0; size--) {
          String str2 = (String) arraySet.valueAt(size);
          if (TextUtils.equals(getPackageName(str2), str)
              && (unflattenFromString = ComponentName.unflattenFromString(str2)) != null
              && !componentHasBindPermission(unflattenFromString, i)) {
            arraySet.removeAt(size);
            if (this.DEBUG) {
              Slog.v(
                  this.TAG,
                  "Removing "
                      + str2
                      + " from approved list; no bind permission found "
                      + this.mConfig.bindPermission);
            }
          }
        }
      }
    }
  }

  public String getPackageName(String str) {
    ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
    return unflattenFromString != null ? unflattenFromString.getPackageName() : str;
  }

  public boolean isValidEntry(String str, int i) {
    return hasMatchingServices(str, i);
  }

  public final boolean hasMatchingServices(String str, int i) {
    return !TextUtils.isEmpty(str) && queryPackageForServices(getPackageName(str), i).size() > 0;
  }

  public SparseArray getAllowedComponents(IntArray intArray) {
    int size = intArray.size();
    SparseArray sparseArray = new SparseArray();
    for (int i = 0; i < size; i++) {
      int i2 = intArray.get(i);
      synchronized (this.mApproved) {
        ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i2));
        if (arrayMap != null) {
          int size2 = arrayMap.size();
          for (int i3 = 0; i3 < size2; i3++) {
            ArraySet arraySet = (ArraySet) sparseArray.get(i2);
            if (arraySet == null) {
              arraySet = new ArraySet();
              sparseArray.put(i2, arraySet);
            }
            arraySet.addAll(loadComponentNamesFromValues((ArraySet) arrayMap.valueAt(i3), i2));
          }
        }
      }
    }
    return sparseArray;
  }

  public void populateComponentsToBind(
      SparseArray sparseArray, IntArray intArray, SparseArray sparseArray2) {
    this.mEnabledServicesForCurrentProfiles.clear();
    this.mEnabledServicesPackageNames.clear();
    int size = intArray.size();
    for (int i = 0; i < size; i++) {
      int i2 = intArray.get(i);
      ArraySet arraySet = (ArraySet) sparseArray2.get(i2);
      if (arraySet == null) {
        sparseArray.put(i2, new ArraySet());
      } else {
        HashSet hashSet = new HashSet(arraySet);
        synchronized (this.mSnoozing) {
          ArraySet arraySet2 = this.mSnoozing.get(i2);
          if (arraySet2 != null) {
            hashSet.removeAll(arraySet2);
          }
        }
        sparseArray.put(i2, hashSet);
        this.mEnabledServicesForCurrentProfiles.addAll(arraySet);
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
          this.mEnabledServicesPackageNames.add(
              ((ComponentName) arraySet.valueAt(i3)).getPackageName());
        }
      }
    }
  }

  public Set getRemovableConnectedServices() {
    ArraySet arraySet = new ArraySet();
    Iterator it = this.mServices.iterator();
    while (it.hasNext()) {
      ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) it.next();
      if (!managedServiceInfo.isSystem && !managedServiceInfo.isGuest(this)) {
        arraySet.add(managedServiceInfo);
      }
    }
    return arraySet;
  }

  public void populateComponentsToUnbind(
      boolean z, Set set, SparseArray sparseArray, SparseArray sparseArray2) {
    Iterator it = set.iterator();
    while (it.hasNext()) {
      ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) it.next();
      Set set2 = (Set) sparseArray.get(managedServiceInfo.userid);
      if (set2 != null && (z || !set2.contains(managedServiceInfo.component))) {
        Set set3 = (Set) sparseArray2.get(managedServiceInfo.userid, new ArraySet());
        set3.add(managedServiceInfo.component);
        sparseArray2.put(managedServiceInfo.userid, set3);
      }
    }
  }

  public void rebindServices(boolean z, int i) {
    if (this.DEBUG) {
      Slog.d(this.TAG, "rebindServices " + z + " " + i);
    }
    IntArray currentProfileIds = this.mUserProfiles.getCurrentProfileIds();
    SparseArray sparseArray = new SparseArray();
    SparseArray sparseArray2 = new SparseArray();
    synchronized (this.mMutex) {
      SparseArray allowedComponents = getAllowedComponents(currentProfileIds);
      Set removableConnectedServices = getRemovableConnectedServices();
      populateComponentsToBind(sparseArray, currentProfileIds, allowedComponents);
      populateComponentsToUnbind(z, removableConnectedServices, sparseArray, sparseArray2);
    }
    unbindFromServices(sparseArray2);
    bindToServices(sparseArray);
  }

  public void unbindOtherUserServices(int i) {
    TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
    timingsTraceAndSlog.traceBegin("ManagedServices.unbindOtherUserServices_current" + i);
    SparseArray sparseArray = new SparseArray();
    synchronized (this.mMutex) {
      for (ManagedServiceInfo managedServiceInfo : getRemovableConnectedServices()) {
        int i2 = managedServiceInfo.userid;
        if (i2 != i) {
          Set set = (Set) sparseArray.get(i2, new ArraySet());
          set.add(managedServiceInfo.component);
          sparseArray.put(managedServiceInfo.userid, set);
        }
      }
    }
    unbindFromServices(sparseArray);
    timingsTraceAndSlog.traceEnd();
  }

  public void unbindFromServices(SparseArray sparseArray) {
    for (int i = 0; i < sparseArray.size(); i++) {
      int keyAt = sparseArray.keyAt(i);
      for (ComponentName componentName : (Set) sparseArray.get(keyAt)) {
        Slog.v(this.TAG, "disabling " + getCaption() + " for user " + keyAt + ": " + componentName);
        unregisterService(componentName, keyAt);
      }
    }
  }

  public final void bindToServices(SparseArray sparseArray) {
    for (int i = 0; i < sparseArray.size(); i++) {
      int keyAt = sparseArray.keyAt(i);
      for (ComponentName componentName : (Set) sparseArray.get(keyAt)) {
        ServiceInfo serviceInfo = getServiceInfo(componentName, keyAt);
        if (serviceInfo == null) {
          Slog.w(
              this.TAG,
              "Not binding " + getCaption() + " service " + componentName + ": service not found");
        } else if (!this.mConfig.bindPermission.equals(serviceInfo.permission)) {
          Slog.w(
              this.TAG,
              "Not binding "
                  + getCaption()
                  + " service "
                  + componentName
                  + ": it does not require the permission "
                  + this.mConfig.bindPermission);
        } else if (!isAutobindAllowed(serviceInfo) && !isBoundOrRebinding(componentName, keyAt)) {
          synchronized (this.mSnoozing) {
            Slog.d(
                this.TAG,
                "Not binding "
                    + getCaption()
                    + " service "
                    + componentName
                    + ": has META_DATA_DEFAULT_AUTOBIND = false");
            this.mSnoozing.add(keyAt, componentName);
          }
        } else {
          Slog.v(this.TAG, "enabling " + getCaption() + " for " + keyAt + ": " + componentName);
          registerService(serviceInfo, keyAt);
        }
      }
    }
  }

  public void registerService(ServiceInfo serviceInfo, int i) {
    ensureFilters(serviceInfo, i);
    registerService(serviceInfo.getComponentName(), i);
  }

  public void registerService(ComponentName componentName, int i) {
    synchronized (this.mMutex) {
      registerServiceLocked(componentName, i);
    }
  }

  public void reregisterService(ComponentName componentName, int i) {
    if (isPackageOrComponentAllowedWithPermission(componentName, i)) {
      registerService(componentName, i);
    }
  }

  public void registerSystemService(ComponentName componentName, int i) {
    synchronized (this.mMutex) {
      registerServiceLocked(componentName, i, true);
    }
  }

  public final void registerServiceLocked(ComponentName componentName, int i) {
    registerServiceLocked(componentName, i, false);
  }

  public final void registerServiceLocked(ComponentName componentName, int i, boolean z) {
    ApplicationInfo applicationInfo;
    if (this.DEBUG) {
      Slog.v(this.TAG, "registerService: " + componentName + " u=" + i);
    }
    Pair create = Pair.create(componentName, Integer.valueOf(i));
    if (this.mServicesBound.contains(create)) {
      Slog.v(this.TAG, "Not registering " + componentName + " is already bound");
      return;
    }
    this.mServicesBound.add(create);
    for (int size = this.mServices.size() - 1; size >= 0; size--) {
      ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) this.mServices.get(size);
      if (componentName.equals(managedServiceInfo.component) && managedServiceInfo.userid == i) {
        Slog.v(
            this.TAG, "    disconnecting old " + getCaption() + ": " + managedServiceInfo.service);
        removeServiceLocked(size);
        ServiceConnection serviceConnection = managedServiceInfo.connection;
        if (serviceConnection != null) {
          unbindService(serviceConnection, managedServiceInfo.component, managedServiceInfo.userid);
        }
      }
    }
    Intent intent = new Intent(this.mConfig.serviceInterface);
    intent.setComponent(componentName);
    intent.putExtra("android.intent.extra.client_label", this.mConfig.clientLabel);
    intent.putExtra(
        "android.intent.extra.client_intent",
        PendingIntent.getActivity(
            this.mContext, 0, new Intent(this.mConfig.settingsAction), 67108864));
    try {
      applicationInfo =
          this.mContext.getPackageManager().getApplicationInfo(componentName.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException unused) {
      applicationInfo = null;
    }
    int i2 = applicationInfo != null ? applicationInfo.targetSdkVersion : 1;
    int i3 = applicationInfo != null ? applicationInfo.uid : -1;
    try {
      Slog.v(this.TAG, "binding: " + intent);
      if (this.mContext.bindServiceAsUser(
          intent,
          new ServiceConnectionC19571(i, create, z, i2, i3),
          getBindFlags(),
          new UserHandle(i))) {
        return;
      }
      this.mServicesBound.remove(create);
      Slog.w(this.TAG, "Unable to bind " + getCaption() + " service: " + intent + " in user " + i);
    } catch (SecurityException e) {
      this.mServicesBound.remove(create);
      Slog.e(this.TAG, "Unable to bind " + getCaption() + " service: " + intent, e);
    }
  }

  /* renamed from: com.android.server.notification.ManagedServices$1 */
  public class ServiceConnectionC19571 implements ServiceConnection {
    public ManagedServiceInfo mRemovedInfo;
    public IInterface mService;
    public final /* synthetic */ boolean val$isSystem;
    public final /* synthetic */ Pair val$servicesBindingTag;
    public final /* synthetic */ int val$targetSdkVersion;
    public final /* synthetic */ int val$uid;
    public final /* synthetic */ int val$userid;

    public ServiceConnectionC19571(int i, Pair pair, boolean z, int i2, int i3) {
      this.val$userid = i;
      this.val$servicesBindingTag = pair;
      this.val$isSystem = z;
      this.val$targetSdkVersion = i2;
      this.val$uid = i3;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      boolean z;
      ManagedServiceInfo managedServiceInfo;
      Slog.v(
          ManagedServices.this.TAG,
          this.val$userid
              + " "
              + ManagedServices.this.getCaption()
              + " service connected: "
              + componentName);
      synchronized (ManagedServices.this.mMutex) {
        ManagedServices.this.mServicesRebinding.remove(this.val$servicesBindingTag);
        z = false;
        managedServiceInfo = null;
        try {
          IInterface asInterface = ManagedServices.this.asInterface(iBinder);
          this.mService = asInterface;
          managedServiceInfo =
              ManagedServices.this.newServiceInfo(
                  asInterface,
                  componentName,
                  this.val$userid,
                  this.val$isSystem,
                  this,
                  this.val$targetSdkVersion,
                  this.val$uid);
          iBinder.linkToDeath(managedServiceInfo, 0);
          z = ManagedServices.this.mServices.add(managedServiceInfo);
        } catch (RemoteException e) {
          Slog.e(ManagedServices.this.TAG, "Failed to linkToDeath, already dead", e);
        }
      }
      if (z) {
        ManagedServices.this.onServiceAdded(managedServiceInfo);
      }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
      Slog.v(
          ManagedServices.this.TAG,
          this.val$userid
              + " "
              + ManagedServices.this.getCaption()
              + " connection lost: "
              + componentName);
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(final ComponentName componentName) {
      Slog.w(
          ManagedServices.this.TAG,
          this.val$userid
              + " "
              + ManagedServices.this.getCaption()
              + " binding died: "
              + componentName);
      synchronized (ManagedServices.this.mMutex) {
        int size = ManagedServices.this.mServices.size() - 1;
        while (true) {
          if (size < 0) {
            break;
          }
          ManagedServiceInfo managedServiceInfo =
              (ManagedServiceInfo) ManagedServices.this.mServices.get(size);
          if (managedServiceInfo.isSystem && componentName.equals(managedServiceInfo.component)) {
            this.mRemovedInfo = managedServiceInfo;
            break;
          }
          size--;
        }
        ManagedServices.this.unbindService(this, componentName, this.val$userid);
        if (!ManagedServices.this.mServicesRebinding.contains(this.val$servicesBindingTag)) {
          ManagedServices.this.mServicesRebinding.add(this.val$servicesBindingTag);
          Handler handler = ManagedServices.this.mHandler;
          final int i = this.val$userid;
          handler.postDelayed(
              new Runnable() { // from class:
                               // com.android.server.notification.ManagedServices$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  ManagedServices.ServiceConnectionC19571.this.lambda$onBindingDied$0(
                      componentName, i);
                }
              },
              10000L);
        } else {
          Slog.v(
              ManagedServices.this.TAG,
              ManagedServices.this.getCaption()
                  + " not rebinding in user "
                  + this.val$userid
                  + " as a previous rebind attempt was made: "
                  + componentName);
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindingDied$0(ComponentName componentName, int i) {
      ManagedServiceInfo managedServiceInfo = this.mRemovedInfo;
      if (managedServiceInfo != null && managedServiceInfo.isSystem) {
        ManagedServices.this.registerSystemService(componentName, i);
      } else {
        ManagedServices.this.reregisterService(componentName, i);
      }
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(ComponentName componentName) {
      Slog.v(
          ManagedServices.this.TAG, "onNullBinding() called with: name = [" + componentName + "]");
      ManagedServices.this.mContext.unbindService(this);
    }
  }

  public boolean isBound(ComponentName componentName, int i) {
    boolean contains;
    Pair create = Pair.create(componentName, Integer.valueOf(i));
    synchronized (this.mMutex) {
      contains = this.mServicesBound.contains(create);
    }
    return contains;
  }

  public boolean isBoundOrRebinding(ComponentName componentName, int i) {
    boolean z;
    synchronized (this.mMutex) {
      z =
          isBound(componentName, i)
              || this.mServicesRebinding.contains(Pair.create(componentName, Integer.valueOf(i)));
    }
    return z;
  }

  public void unregisterService(ComponentName componentName, int i) {
    synchronized (this.mMutex) {
      unregisterServiceLocked(componentName, i);
    }
  }

  public final void unregisterServiceLocked(ComponentName componentName, int i) {
    for (int size = this.mServices.size() - 1; size >= 0; size--) {
      ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) this.mServices.get(size);
      if (componentName.equals(managedServiceInfo.component) && managedServiceInfo.userid == i) {
        removeServiceLocked(size);
        ServiceConnection serviceConnection = managedServiceInfo.connection;
        if (serviceConnection != null) {
          unbindService(serviceConnection, managedServiceInfo.component, managedServiceInfo.userid);
        }
      }
    }
  }

  public final ManagedServiceInfo removeServiceImpl(IInterface iInterface, int i) {
    ManagedServiceInfo managedServiceInfo;
    if (this.DEBUG) {
      Slog.d(this.TAG, "removeServiceImpl service=" + iInterface + " u=" + i);
    }
    synchronized (this.mMutex) {
      managedServiceInfo = null;
      for (int size = this.mServices.size() - 1; size >= 0; size--) {
        ManagedServiceInfo managedServiceInfo2 = (ManagedServiceInfo) this.mServices.get(size);
        if (managedServiceInfo2.service.asBinder() == iInterface.asBinder()
            && managedServiceInfo2.userid == i) {
          Slog.d(this.TAG, "Removing active service " + managedServiceInfo2.component);
          managedServiceInfo = removeServiceLocked(size);
        }
      }
    }
    return managedServiceInfo;
  }

  public final ManagedServiceInfo removeServiceLocked(int i) {
    ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) this.mServices.remove(i);
    onServiceRemovedLocked(managedServiceInfo);
    return managedServiceInfo;
  }

  public final void checkNotNull(IInterface iInterface) {
    if (iInterface != null) {
      return;
    }
    throw new IllegalArgumentException(getCaption() + " must not be null");
  }

  public final ManagedServiceInfo registerServiceImpl(
      IInterface iInterface, ComponentName componentName, int i, int i2, int i3) {
    return registerServiceImpl(newServiceInfo(iInterface, componentName, i, true, null, i2, i3));
  }

  public final ManagedServiceInfo registerServiceImpl(ManagedServiceInfo managedServiceInfo) {
    synchronized (this.mMutex) {
      try {
        try {
          managedServiceInfo.service.asBinder().linkToDeath(managedServiceInfo, 0);
          this.mServices.add(managedServiceInfo);
        } catch (RemoteException unused) {
          return null;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return managedServiceInfo;
  }

  public final void unregisterServiceImpl(IInterface iInterface, int i) {
    ManagedServiceInfo removeServiceImpl = removeServiceImpl(iInterface, i);
    if (removeServiceImpl == null
        || removeServiceImpl.connection == null
        || removeServiceImpl.isGuest(this)) {
      return;
    }
    unbindService(
        removeServiceImpl.connection, removeServiceImpl.component, removeServiceImpl.userid);
  }

  public final void unbindService(
      ServiceConnection serviceConnection, ComponentName componentName, int i) {
    try {
      this.mContext.unbindService(serviceConnection);
    } catch (IllegalArgumentException e) {
      Slog.e(this.TAG, getCaption() + " " + componentName + " could not be unbound", e);
    }
    synchronized (this.mMutex) {
      this.mServicesBound.remove(Pair.create(componentName, Integer.valueOf(i)));
    }
  }

  public final ServiceInfo getServiceInfo(ComponentName componentName, int i) {
    try {
      return this.mPm.getServiceInfo(componentName, 786560L, i);
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
      return null;
    }
  }

  public final boolean isAutobindAllowed(ServiceInfo serviceInfo) {
    Bundle bundle;
    if (serviceInfo == null
        || (bundle = serviceInfo.metaData) == null
        || !bundle.containsKey("android.service.notification.default_autobind_listenerservice")) {
      return true;
    }
    return serviceInfo.metaData.getBoolean(
        "android.service.notification.default_autobind_listenerservice", true);
  }

  public class ManagedServiceInfo implements IBinder.DeathRecipient {
    public ComponentName component;
    public ServiceConnection connection;
    public boolean isSystem;
    public Pair mKey;
    public IInterface service;
    public int targetSdkVersion;
    public int uid;
    public int userid;

    public ManagedServiceInfo(
        IInterface iInterface,
        ComponentName componentName,
        int i,
        boolean z,
        ServiceConnection serviceConnection,
        int i2,
        int i3) {
      this.service = iInterface;
      this.component = componentName;
      this.userid = i;
      this.isSystem = z;
      this.connection = serviceConnection;
      this.targetSdkVersion = i2;
      this.uid = i3;
      this.mKey = Pair.create(componentName, Integer.valueOf(i));
    }

    public boolean isGuest(ManagedServices managedServices) {
      return ManagedServices.this != managedServices;
    }

    public ManagedServices getOwner() {
      return ManagedServices.this;
    }

    public IInterface getService() {
      return this.service;
    }

    public boolean isSystem() {
      return this.isSystem;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder("ManagedServiceInfo[");
      sb.append("component=");
      sb.append(this.component);
      sb.append(",userid=");
      sb.append(this.userid);
      sb.append(",isSystem=");
      sb.append(this.isSystem);
      sb.append(",targetSdkVersion=");
      sb.append(this.targetSdkVersion);
      sb.append(",connection=");
      sb.append(this.connection == null ? null : "<connection>");
      sb.append(",service=");
      sb.append(this.service);
      sb.append(']');
      return sb.toString();
    }

    public void dumpDebug(
        ProtoOutputStream protoOutputStream, long j, ManagedServices managedServices) {
      long start = protoOutputStream.start(j);
      this.component.dumpDebug(protoOutputStream, 1146756268033L);
      protoOutputStream.write(1120986464258L, this.userid);
      protoOutputStream.write(1138166333443L, this.service.getClass().getName());
      protoOutputStream.write(1133871366148L, this.isSystem);
      protoOutputStream.write(1133871366149L, isGuest(managedServices));
      protoOutputStream.end(start);
    }

    public boolean isSameUser(int i) {
      if (isEnabledForCurrentProfiles()) {
        return i == -1 || i == this.userid;
      }
      return false;
    }

    public boolean enabledAndUserMatches(int i) {
      if (!isEnabledForCurrentProfiles()) {
        return false;
      }
      int i2 = this.userid;
      if (i2 == -1 || this.isSystem || i == -1 || i == i2) {
        return true;
      }
      return supportsProfiles()
          && ManagedServices.this.mUserProfiles.isCurrentProfile(i)
          && isPermittedForProfile(i);
    }

    public boolean supportsProfiles() {
      return this.targetSdkVersion >= 21;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      ManagedServices managedServices = ManagedServices.this;
      if (managedServices.DEBUG) {
        Slog.d(managedServices.TAG, "binderDied");
      }
      ManagedServices.this.removeServiceImpl(this.service, this.userid);
    }

    public boolean isEnabledForCurrentProfiles() {
      boolean contains;
      if (this.isSystem) {
        return true;
      }
      if (this.connection == null) {
        return false;
      }
      synchronized (ManagedServices.this.mMutex) {
        contains = ManagedServices.this.mEnabledServicesForCurrentProfiles.contains(this.component);
      }
      return contains;
    }

    public boolean isPermittedForProfile(int i) {
      if (!ManagedServices.this.mUserProfiles.isProfileUser(i)) {
        return true;
      }
      DevicePolicyManager devicePolicyManager =
          (DevicePolicyManager) ManagedServices.this.mContext.getSystemService("device_policy");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return devicePolicyManager.isNotificationListenerServicePermitted(
            this.component.getPackageName(), i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) obj;
      return this.userid == managedServiceInfo.userid
          && this.isSystem == managedServiceInfo.isSystem
          && this.targetSdkVersion == managedServiceInfo.targetSdkVersion
          && Objects.equals(this.service, managedServiceInfo.service)
          && Objects.equals(this.component, managedServiceInfo.component)
          && Objects.equals(this.connection, managedServiceInfo.connection);
    }

    public int hashCode() {
      return Objects.hash(
          this.service,
          this.component,
          Integer.valueOf(this.userid),
          Boolean.valueOf(this.isSystem),
          this.connection,
          Integer.valueOf(this.targetSdkVersion));
    }
  }

  public boolean isComponentEnabledForCurrentProfiles(ComponentName componentName) {
    boolean contains;
    synchronized (this.mMutex) {
      contains = this.mEnabledServicesForCurrentProfiles.contains(componentName);
    }
    return contains;
  }

  public class UserProfiles {
    public final SparseArray mCurrentProfiles = new SparseArray();

    public void updateCache(Context context) {
      UserManager userManager = (UserManager) context.getSystemService("user");
      if (userManager != null) {
        List<UserInfo> profiles = userManager.getProfiles(ActivityManager.getCurrentUser());
        synchronized (this.mCurrentProfiles) {
          this.mCurrentProfiles.clear();
          for (UserInfo userInfo : profiles) {
            this.mCurrentProfiles.put(userInfo.id, userInfo);
          }
        }
      }
    }

    public IntArray getCurrentProfileIds() {
      IntArray intArray;
      synchronized (this.mCurrentProfiles) {
        intArray = new IntArray(this.mCurrentProfiles.size());
        int size = this.mCurrentProfiles.size();
        for (int i = 0; i < size; i++) {
          intArray.add(this.mCurrentProfiles.keyAt(i));
        }
      }
      return intArray;
    }

    public boolean isCurrentProfile(int i) {
      boolean z;
      synchronized (this.mCurrentProfiles) {
        z = this.mCurrentProfiles.get(i) != null;
      }
      return z;
    }

    public boolean isProfileUser(int i) {
      synchronized (this.mCurrentProfiles) {
        UserInfo userInfo = (UserInfo) this.mCurrentProfiles.get(i);
        if (userInfo == null) {
          return false;
        }
        if (!userInfo.isManagedProfile() && !userInfo.isCloneProfile()) {
          return false;
        }
        return true;
      }
    }
  }
}
