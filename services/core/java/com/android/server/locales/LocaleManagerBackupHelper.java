package com.android.server.locales;

import android.app.LocaleConfig;
import android.app.backup.BackupManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.LocaleList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class LocaleManagerBackupHelper {
  public static final Duration STAGE_DATA_RETENTION_PERIOD = Duration.ofDays(3);
  public final Clock mClock;
  public final Context mContext;
  public final SharedPreferences mDelegateAppLocalePackages;
  public final LocaleManagerService mLocaleManagerService;
  public final PackageManager mPackageManager;
  public final SparseArray mStagedData;
  public final Object mStagedDataLock;
  public final BroadcastReceiver mUserMonitor;

  public LocaleManagerBackupHelper(
      LocaleManagerService localeManagerService,
      PackageManager packageManager,
      HandlerThread handlerThread) {
    this(
        localeManagerService.mContext,
        localeManagerService,
        packageManager,
        Clock.systemUTC(),
        new SparseArray(),
        handlerThread,
        null);
  }

  public LocaleManagerBackupHelper(
      Context context,
      LocaleManagerService localeManagerService,
      PackageManager packageManager,
      Clock clock,
      SparseArray sparseArray,
      HandlerThread handlerThread,
      SharedPreferences sharedPreferences) {
    this.mStagedDataLock = new Object();
    this.mContext = context;
    this.mLocaleManagerService = localeManagerService;
    this.mPackageManager = packageManager;
    this.mClock = clock;
    this.mStagedData = sparseArray;
    this.mDelegateAppLocalePackages =
        sharedPreferences == null ? createPersistedInfo() : sharedPreferences;
    UserMonitor userMonitor = new UserMonitor();
    this.mUserMonitor = userMonitor;
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.USER_REMOVED");
    context.registerReceiverAsUser(
        userMonitor, UserHandle.ALL, intentFilter, null, handlerThread.getThreadHandler());
  }

  public BroadcastReceiver getUserMonitor() {
    return this.mUserMonitor;
  }

  public byte[] getBackupPayload(int i) {
    synchronized (this.mStagedDataLock) {
      cleanStagedDataForOldEntriesLocked();
    }
    HashMap hashMap = new HashMap();
    for (ApplicationInfo applicationInfo :
        this.mPackageManager.getInstalledApplicationsAsUser(
            PackageManager.ApplicationInfoFlags.of(0L), i)) {
      try {
        LocaleList applicationLocales =
            this.mLocaleManagerService.getApplicationLocales(applicationInfo.packageName, i);
        if (!applicationLocales.isEmpty()) {
          SharedPreferences sharedPreferences = this.mDelegateAppLocalePackages;
          hashMap.put(
              applicationInfo.packageName,
              new LocalesInfo(
                  applicationLocales.toLanguageTags(),
                  sharedPreferences != null
                      ? sharedPreferences
                          .getStringSet(Integer.toString(i), Collections.emptySet())
                          .contains(applicationInfo.packageName)
                      : false));
        }
      } catch (RemoteException | IllegalArgumentException e) {
        Slog.e(
            "LocaleManagerBkpHelper",
            "Exception when getting locales for package: " + applicationInfo.packageName,
            e);
      }
    }
    if (hashMap.isEmpty()) {
      return null;
    }
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      writeToXml(byteArrayOutputStream, hashMap);
      return byteArrayOutputStream.toByteArray();
    } catch (IOException e2) {
      Slog.e("LocaleManagerBkpHelper", "Could not write to xml for backup ", e2);
      return null;
    }
  }

  public final void cleanStagedDataForOldEntriesLocked() {
    for (int i = 0; i < this.mStagedData.size(); i++) {
      int keyAt = this.mStagedData.keyAt(i);
      if (((StagedData) this.mStagedData.get(keyAt)).mCreationTimeMillis
          < this.mClock.millis() - STAGE_DATA_RETENTION_PERIOD.toMillis()) {
        deleteStagedDataLocked(keyAt);
      }
    }
  }

  public void stageAndApplyRestoredPayload(byte[] bArr, int i) {
    if (bArr == null) {
      Slog.e(
          "LocaleManagerBkpHelper",
          "stageAndApplyRestoredPayload: no payload to restore for user " + i);
      return;
    }
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
    try {
      TypedXmlPullParser newFastPullParser = Xml.newFastPullParser();
      newFastPullParser.setInput(byteArrayInputStream, StandardCharsets.UTF_8.name());
      XmlUtils.beginDocument(newFastPullParser, "locales");
      HashMap readFromXml = readFromXml(newFastPullParser);
      synchronized (this.mStagedDataLock) {
        StagedData stagedData = new StagedData(this.mClock.millis(), new HashMap());
        for (String str : readFromXml.keySet()) {
          LocalesInfo localesInfo = (LocalesInfo) readFromXml.get(str);
          if (isPackageInstalledForUser(str, i)) {
            checkExistingLocalesAndApplyRestore(str, localesInfo, i);
          } else {
            stagedData.mPackageStates.put(str, localesInfo);
          }
        }
        if (!stagedData.mPackageStates.isEmpty()) {
          this.mStagedData.put(i, stagedData);
        }
      }
    } catch (IOException | XmlPullParserException e) {
      Slog.e("LocaleManagerBkpHelper", "Could not parse payload ", e);
    }
  }

  public void notifyBackupManager() {
    BackupManager.dataChanged("android");
  }

  public void onPackageAdded(String str, int i) {
    try {
      synchronized (this.mStagedDataLock) {
        cleanStagedDataForOldEntriesLocked();
        int userId = UserHandle.getUserId(i);
        if (this.mStagedData.contains(userId)) {
          doLazyRestoreLocked(str, userId);
        }
      }
    } catch (Exception e) {
      Slog.e("LocaleManagerBkpHelper", "Exception in onPackageAdded.", e);
    }
  }

  public void onPackageUpdateFinished(String str, int i) {
    cleanApplicationLocalesIfNeeded(str, UserHandle.getUserId(i));
  }

  public void onPackageDataCleared(String str, int i) {
    try {
      notifyBackupManager();
      removePackageFromPersistedInfo(str, UserHandle.getUserId(i));
    } catch (Exception e) {
      Slog.e("LocaleManagerBkpHelper", "Exception in onPackageDataCleared.", e);
    }
  }

  public void onPackageRemoved(String str, int i) {
    try {
      notifyBackupManager();
      removePackageFromPersistedInfo(str, UserHandle.getUserId(i));
    } catch (Exception e) {
      Slog.e("LocaleManagerBkpHelper", "Exception in onPackageRemoved.", e);
    }
  }

  public final boolean isPackageInstalledForUser(String str, int i) {
    PackageInfo packageInfo;
    try {
      packageInfo = this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, i);
    } catch (PackageManager.NameNotFoundException unused) {
      packageInfo = null;
    }
    return packageInfo != null;
  }

  public final void checkExistingLocalesAndApplyRestore(
      String str, LocalesInfo localesInfo, int i) {
    if (localesInfo == null) {
      Slog.w("LocaleManagerBkpHelper", "No locales info for " + str);
      return;
    }
    try {
      if (!this.mLocaleManagerService.getApplicationLocales(str, i).isEmpty()) {
        return;
      }
    } catch (RemoteException | IllegalArgumentException e) {
      Slog.e("LocaleManagerBkpHelper", "Could not check for current locales before restoring", e);
    }
    try {
      this.mLocaleManagerService.setApplicationLocales(
          str,
          i,
          LocaleList.forLanguageTags(localesInfo.mLocales),
          localesInfo.mSetFromDelegate,
          3);
    } catch (RemoteException | IllegalArgumentException e2) {
      Slog.e("LocaleManagerBkpHelper", "Could not restore locales for " + str, e2);
    }
  }

  public final void deleteStagedDataLocked(int i) {
    this.mStagedData.remove(i);
  }

  public final HashMap readFromXml(TypedXmlPullParser typedXmlPullParser) {
    HashMap hashMap = new HashMap();
    int depth = typedXmlPullParser.getDepth();
    while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
      if (typedXmlPullParser.getName().equals("package")) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "locales");
        boolean attributeBoolean =
            typedXmlPullParser.getAttributeBoolean((String) null, "delegate_selector");
        if (!TextUtils.isEmpty(attributeValue) && !TextUtils.isEmpty(attributeValue2)) {
          hashMap.put(attributeValue, new LocalesInfo(attributeValue2, attributeBoolean));
        }
      }
    }
    return hashMap;
  }

  public static void writeToXml(OutputStream outputStream, HashMap hashMap) {
    if (hashMap.isEmpty()) {
      return;
    }
    TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
    newFastSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
    newFastSerializer.startDocument((String) null, Boolean.TRUE);
    newFastSerializer.startTag((String) null, "locales");
    for (String str : hashMap.keySet()) {
      newFastSerializer.startTag((String) null, "package");
      newFastSerializer.attribute((String) null, "name", str);
      newFastSerializer.attribute(
          (String) null, "locales", ((LocalesInfo) hashMap.get(str)).mLocales);
      newFastSerializer.attributeBoolean(
          (String) null, "delegate_selector", ((LocalesInfo) hashMap.get(str)).mSetFromDelegate);
      newFastSerializer.endTag((String) null, "package");
    }
    newFastSerializer.endTag((String) null, "locales");
    newFastSerializer.endDocument();
  }

  public class StagedData {
    public final long mCreationTimeMillis;
    public final HashMap mPackageStates;

    public StagedData(long j, HashMap hashMap) {
      this.mCreationTimeMillis = j;
      this.mPackageStates = hashMap;
    }
  }

  public class LocalesInfo {
    public final String mLocales;
    public final boolean mSetFromDelegate;

    public LocalesInfo(String str, boolean z) {
      this.mLocales = str;
      this.mSetFromDelegate = z;
    }
  }

  public final class UserMonitor extends BroadcastReceiver {
    public UserMonitor() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      try {
        if (intent.getAction().equals("android.intent.action.USER_REMOVED")) {
          int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
          synchronized (LocaleManagerBackupHelper.this.mStagedDataLock) {
            LocaleManagerBackupHelper.this.deleteStagedDataLocked(intExtra);
            LocaleManagerBackupHelper.this.removeProfileFromPersistedInfo(intExtra);
          }
        }
      } catch (Exception e) {
        Slog.e("LocaleManagerBkpHelper", "Exception in user monitor.", e);
      }
    }
  }

  public final void doLazyRestoreLocked(String str, int i) {
    if (!isPackageInstalledForUser(str, i)) {
      Slog.e(
          "LocaleManagerBkpHelper",
          str + " not installed for user " + i + ". Could not restore locales from stage data");
      return;
    }
    StagedData stagedData = (StagedData) this.mStagedData.get(i);
    for (String str2 : stagedData.mPackageStates.keySet()) {
      LocalesInfo localesInfo = (LocalesInfo) stagedData.mPackageStates.get(str2);
      if (str2.equals(str)) {
        checkExistingLocalesAndApplyRestore(str2, localesInfo, i);
        stagedData.mPackageStates.remove(str2);
        if (stagedData.mPackageStates.isEmpty()) {
          this.mStagedData.remove(i);
          return;
        }
        return;
      }
    }
  }

  public SharedPreferences createPersistedInfo() {
    return this.mContext
        .createDeviceProtectedStorageContext()
        .getSharedPreferences(
            new File(Environment.getDataSystemDeDirectory(0), "LocalesFromDelegatePrefs.xml"), 0);
  }

  public final void removePackageFromPersistedInfo(String str, int i) {
    if (this.mDelegateAppLocalePackages == null) {
      Slog.w("LocaleManagerBkpHelper", "Failed to persist data into the shared preference!");
      return;
    }
    String num = Integer.toString(i);
    ArraySet arraySet =
        new ArraySet(this.mDelegateAppLocalePackages.getStringSet(num, new ArraySet()));
    if (arraySet.contains(str)) {
      arraySet.remove(str);
      SharedPreferences.Editor edit = this.mDelegateAppLocalePackages.edit();
      edit.putStringSet(num, arraySet);
      if (edit.commit()) {
        return;
      }
      Slog.e("LocaleManagerBkpHelper", "Failed to commit data!");
    }
  }

  public final void removeProfileFromPersistedInfo(int i) {
    String num = Integer.toString(i);
    SharedPreferences sharedPreferences = this.mDelegateAppLocalePackages;
    if (sharedPreferences == null || !sharedPreferences.contains(num)) {
      Slog.w("LocaleManagerBkpHelper", "The profile is not existed in the persisted info");
    } else {
      if (this.mDelegateAppLocalePackages.edit().remove(num).commit()) {
        return;
      }
      Slog.e("LocaleManagerBkpHelper", "Failed to commit data!");
    }
  }

  public void persistLocalesModificationInfo(int i, String str, boolean z, boolean z2) {
    SharedPreferences sharedPreferences = this.mDelegateAppLocalePackages;
    if (sharedPreferences == null) {
      Slog.w("LocaleManagerBkpHelper", "Failed to persist data into the shared preference!");
      return;
    }
    SharedPreferences.Editor edit = sharedPreferences.edit();
    String num = Integer.toString(i);
    ArraySet arraySet =
        new ArraySet(this.mDelegateAppLocalePackages.getStringSet(num, new ArraySet()));
    if (z && !z2) {
      if (!arraySet.contains(str)) {
        arraySet.add(str);
        edit.putStringSet(num, arraySet);
      }
    } else if (arraySet.contains(str)) {
      arraySet.remove(str);
      edit.putStringSet(num, arraySet);
    }
    if (edit.commit()) {
      return;
    }
    Slog.e("LocaleManagerBkpHelper", "failed to commit locale setter info");
  }

  public boolean areLocalesSetFromDelegate(int i, String str) {
    if (this.mDelegateAppLocalePackages == null) {
      Slog.w("LocaleManagerBkpHelper", "Failed to persist data into the shared preference!");
      return false;
    }
    return new ArraySet(
            this.mDelegateAppLocalePackages.getStringSet(Integer.toString(i), new ArraySet()))
        .contains(str);
  }

  public final void cleanApplicationLocalesIfNeeded(String str, int i) {
    if (this.mDelegateAppLocalePackages == null) {
      Slog.w("LocaleManagerBkpHelper", "Failed to persist data into the shared preference!");
      return;
    }
    ArraySet arraySet =
        new ArraySet(
            this.mDelegateAppLocalePackages.getStringSet(Integer.toString(i), new ArraySet()));
    try {
      if (this.mLocaleManagerService.getApplicationLocales(str, i).isEmpty()) {
        return;
      }
      if (arraySet.contains(str)) {
        try {
          this.mLocaleManagerService.removeUnsupportedAppLocales(
              str,
              i,
              new LocaleConfig(this.mContext.createPackageContextAsUser(str, 0, UserHandle.of(i))),
              4);
        } catch (PackageManager.NameNotFoundException e) {
          Slog.e("LocaleManagerBkpHelper", "Can not found the package name : " + str + " / " + e);
        }
      }
    } catch (RemoteException | IllegalArgumentException e2) {
      Slog.e("LocaleManagerBkpHelper", "Exception when getting locales for " + str, e2);
    }
  }
}
