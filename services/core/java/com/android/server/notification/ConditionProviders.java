package com.android.server.notification;

import android.R;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.service.notification.Condition;
import android.service.notification.IConditionProvider;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class ConditionProviders extends ManagedServices {
  static final String TAG_ENABLED_DND_APPS = "dnd_apps";
  public Callback mCallback;
  public final ArrayList mRecords;
  public final ArraySet mSystemConditionProviderNames;
  public final ArraySet mSystemConditionProviders;

  public interface Callback {
    void onBootComplete();

    void onConditionChanged(Uri uri, Condition condition);

    void onServiceAdded(ComponentName componentName);

    void onUserSwitched();
  }

  @Override // com.android.server.notification.ManagedServices
  public void ensureFilters(ServiceInfo serviceInfo, int i) {}

  @Override // com.android.server.notification.ManagedServices
  public String getRequiredPermission() {
    return null;
  }

  @Override // com.android.server.notification.ManagedServices
  public boolean isValidEntry(String str, int i) {
    return true;
  }

  public ConditionProviders(
      Context context, ManagedServices.UserProfiles userProfiles, IPackageManager iPackageManager) {
    super(context, new Object(), userProfiles, iPackageManager);
    this.mRecords = new ArrayList();
    this.mSystemConditionProviders = new ArraySet();
    this.mSystemConditionProviderNames =
        safeSet(PropConfig.getStringArray(this.mContext, "system.condition.providers", 17236313));
    this.mApprovalLevel = 0;
  }

  public void setCallback(Callback callback) {
    this.mCallback = callback;
  }

  public boolean isSystemProviderEnabled(String str) {
    return this.mSystemConditionProviderNames.contains(str);
  }

  public void addSystemProvider(SystemConditionProviderService systemConditionProviderService) {
    this.mSystemConditionProviders.add(systemConditionProviderService);
    systemConditionProviderService.attachBase(this.mContext);
    registerSystemService(
        systemConditionProviderService.asInterface(),
        systemConditionProviderService.getComponent(),
        0,
        1000);
  }

  public Iterable getSystemProviders() {
    return this.mSystemConditionProviders;
  }

  public boolean resetPackage(String str, int i) {
    ArraySet arraySet;
    boolean isPackageOrComponentAllowed = super.isPackageOrComponentAllowed(str, i);
    boolean isDefaultComponentOrPackage = super.isDefaultComponentOrPackage(str);
    if (!isPackageOrComponentAllowed && isDefaultComponentOrPackage) {
      setPackageOrComponentEnabled(str, i, true, true);
      synchronized (this.mApproved) {
        ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i));
        if (arrayMap != null && (arraySet = (ArraySet) arrayMap.get(Boolean.FALSE)) != null) {
          String packageName = getPackageName(str);
          if (arraySet.contains(packageName)) {
            arraySet.remove(packageName);
          }
        }
      }
    }
    if (isPackageOrComponentAllowed && !isDefaultComponentOrPackage) {
      setPackageOrComponentEnabled(str, i, true, false);
    }
    return !isPackageOrComponentAllowed && isDefaultComponentOrPackage;
  }

  @Override // com.android.server.notification.ManagedServices
  public void writeDefaults(TypedXmlSerializer typedXmlSerializer) {
    synchronized (this.mDefaultsLock) {
      typedXmlSerializer.attribute(
          (String) null,
          "defaults",
          String.join(XmlUtils.STRING_ARRAY_SEPARATOR, this.mDefaultPackages));
    }
  }

  @Override // com.android.server.notification.ManagedServices
  public ManagedServices.Config getConfig() {
    ManagedServices.Config config = new ManagedServices.Config();
    config.caption = "condition provider";
    config.serviceInterface = "android.service.notification.ConditionProviderService";
    config.secureSettingName = null;
    config.xmlTag = TAG_ENABLED_DND_APPS;
    config.secondarySettingName = "enabled_notification_listeners";
    config.bindPermission = "android.permission.BIND_CONDITION_PROVIDER_SERVICE";
    config.settingsAction = "android.settings.ACTION_CONDITION_PROVIDER_SETTINGS";
    config.clientLabel = R.string.config_wearMediaSessionsPackage;
    return config;
  }

  @Override // com.android.server.notification.ManagedServices
  public void dump(PrintWriter printWriter, NotificationManagerService.DumpFilter dumpFilter) {
    int i;
    super.dump(printWriter, dumpFilter);
    synchronized (this.mMutex) {
      printWriter.print("    mRecords(");
      printWriter.print(this.mRecords.size());
      printWriter.println("):");
      for (int i2 = 0; i2 < this.mRecords.size(); i2++) {
        ConditionRecord conditionRecord = (ConditionRecord) this.mRecords.get(i2);
        if (dumpFilter == null || dumpFilter.matches(conditionRecord.component)) {
          printWriter.print("      ");
          printWriter.println(conditionRecord);
          String tryParseDescription =
              CountdownConditionProvider.tryParseDescription(conditionRecord.f1715id);
          if (tryParseDescription != null) {
            printWriter.print("        (");
            printWriter.print(tryParseDescription);
            printWriter.println(")");
          }
        }
      }
    }
    printWriter.print("    mSystemConditionProviders: ");
    printWriter.println(this.mSystemConditionProviderNames);
    for (i = 0; i < this.mSystemConditionProviders.size(); i++) {
      ((SystemConditionProviderService) this.mSystemConditionProviders.valueAt(i))
          .dump(printWriter, dumpFilter);
    }
  }

  @Override // com.android.server.notification.ManagedServices
  public IInterface asInterface(IBinder iBinder) {
    return IConditionProvider.Stub.asInterface(iBinder);
  }

  @Override // com.android.server.notification.ManagedServices
  public boolean checkType(IInterface iInterface) {
    return iInterface instanceof IConditionProvider;
  }

  @Override // com.android.server.notification.ManagedServices
  public void onBootPhaseAppsCanStart() {
    super.onBootPhaseAppsCanStart();
    for (int i = 0; i < this.mSystemConditionProviders.size(); i++) {
      ((SystemConditionProviderService) this.mSystemConditionProviders.valueAt(i)).onBootComplete();
    }
    Callback callback = this.mCallback;
    if (callback != null) {
      callback.onBootComplete();
    }
  }

  @Override // com.android.server.notification.ManagedServices
  public void onUserSwitched(int i) {
    super.onUserSwitched(i);
    Callback callback = this.mCallback;
    if (callback != null) {
      callback.onUserSwitched();
    }
  }

  @Override // com.android.server.notification.ManagedServices
  public void onServiceAdded(ManagedServices.ManagedServiceInfo managedServiceInfo) {
    try {
      provider(managedServiceInfo).onConnected();
    } catch (RemoteException e) {
      Slog.e(this.TAG, "can't connect to service " + managedServiceInfo, e);
    }
    Callback callback = this.mCallback;
    if (callback != null) {
      callback.onServiceAdded(managedServiceInfo.component);
    }
  }

  @Override // com.android.server.notification.ManagedServices
  public void loadDefaultsFromConfig() {
    String string =
        this.mContext.getResources().getString(R.string.date_picker_day_of_week_typeface);
    if (string != null) {
      String[] split = string.split(XmlUtils.STRING_ARRAY_SEPARATOR);
      for (int i = 0; i < split.length; i++) {
        if (!TextUtils.isEmpty(split[i])) {
          addDefaultComponentOrPackage(split[i]);
        }
      }
    }
  }

  @Override // com.android.server.notification.ManagedServices
  public void onServiceRemovedLocked(ManagedServices.ManagedServiceInfo managedServiceInfo) {
    if (managedServiceInfo == null) {
      return;
    }
    for (int size = this.mRecords.size() - 1; size >= 0; size--) {
      if (((ConditionRecord) this.mRecords.get(size))
          .component.equals(managedServiceInfo.component)) {
        this.mRecords.remove(size);
      }
    }
  }

  @Override // com.android.server.notification.ManagedServices
  public void onPackagesChanged(boolean z, String[] strArr, int[] iArr) {
    if (z) {
      INotificationManager service = NotificationManager.getService();
      if (strArr != null && strArr.length > 0) {
        for (String str : strArr) {
          try {
            service.removeAutomaticZenRules(str);
            service.setNotificationPolicyAccessGranted(str, false);
          } catch (Exception e) {
            Slog.e(this.TAG, "Failed to clean up rules for " + str, e);
          }
        }
      }
    }
    super.onPackagesChanged(z, strArr, iArr);
  }

  public ManagedServices.ManagedServiceInfo checkServiceToken(
      IConditionProvider iConditionProvider) {
    ManagedServices.ManagedServiceInfo checkServiceTokenLocked;
    synchronized (this.mMutex) {
      checkServiceTokenLocked = checkServiceTokenLocked(iConditionProvider);
    }
    return checkServiceTokenLocked;
  }

  public final Condition[] getValidConditions(String str, Condition[] conditionArr) {
    if (conditionArr == null || conditionArr.length == 0) {
      return null;
    }
    int length = conditionArr.length;
    ArrayMap arrayMap = new ArrayMap(length);
    for (int i = 0; i < length; i++) {
      Condition condition = conditionArr[i];
      if (condition == null) {
        Slog.w(this.TAG, "Ignoring null condition from " + str);
      } else {
        Uri uri = condition.id;
        if (arrayMap.containsKey(uri)) {
          Slog.w(this.TAG, "Ignoring condition from " + str + " for duplicate id: " + uri);
        } else {
          arrayMap.put(uri, conditionArr[i]);
        }
      }
    }
    if (arrayMap.size() == 0) {
      return null;
    }
    if (arrayMap.size() == length) {
      return conditionArr;
    }
    int size = arrayMap.size();
    Condition[] conditionArr2 = new Condition[size];
    for (int i2 = 0; i2 < size; i2++) {
      conditionArr2[i2] = (Condition) arrayMap.valueAt(i2);
    }
    return conditionArr2;
  }

  public final ConditionRecord getRecordLocked(Uri uri, ComponentName componentName, boolean z) {
    if (uri != null && componentName != null) {
      int size = this.mRecords.size();
      for (int i = 0; i < size; i++) {
        ConditionRecord conditionRecord = (ConditionRecord) this.mRecords.get(i);
        if (conditionRecord.f1715id.equals(uri)
            && conditionRecord.component.equals(componentName)) {
          return conditionRecord;
        }
      }
      if (z) {
        ConditionRecord conditionRecord2 = new ConditionRecord(uri, componentName);
        this.mRecords.add(conditionRecord2);
        return conditionRecord2;
      }
    }
    return null;
  }

  public void notifyConditions(
      String str, ManagedServices.ManagedServiceInfo managedServiceInfo, Condition[] conditionArr) {
    synchronized (this.mMutex) {
      if (this.DEBUG) {
        String str2 = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("notifyConditions pkg=");
        sb.append(str);
        sb.append(" info=");
        sb.append(managedServiceInfo);
        sb.append(" conditions=");
        sb.append(conditionArr == null ? null : Arrays.asList(conditionArr));
        Slog.d(str2, sb.toString());
      }
      Condition[] validConditions = getValidConditions(str, conditionArr);
      if (validConditions != null && validConditions.length != 0) {
        for (Condition condition : validConditions) {
          ConditionRecord recordLocked =
              getRecordLocked(condition.id, managedServiceInfo.component, true);
          recordLocked.info = managedServiceInfo;
          recordLocked.condition = condition;
        }
        for (Condition condition2 : validConditions) {
          Callback callback = this.mCallback;
          if (callback != null) {
            callback.onConditionChanged(condition2.id, condition2);
          }
        }
      }
    }
  }

  public IConditionProvider findConditionProvider(ComponentName componentName) {
    if (componentName == null) {
      return null;
    }
    for (ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
      if (componentName.equals(managedServiceInfo.component)) {
        return provider(managedServiceInfo);
      }
    }
    return null;
  }

  public Condition findCondition(ComponentName componentName, Uri uri) {
    Condition condition;
    if (componentName == null || uri == null) {
      return null;
    }
    synchronized (this.mMutex) {
      ConditionRecord recordLocked = getRecordLocked(uri, componentName, false);
      condition = recordLocked != null ? recordLocked.condition : null;
    }
    return condition;
  }

  public void ensureRecordExists(
      ComponentName componentName, Uri uri, IConditionProvider iConditionProvider) {
    synchronized (this.mMutex) {
      ConditionRecord recordLocked = getRecordLocked(uri, componentName, true);
      if (recordLocked.info == null) {
        recordLocked.info = checkServiceTokenLocked(iConditionProvider);
      }
    }
  }

  public boolean subscribeIfNecessary(ComponentName componentName, Uri uri, boolean z) {
    synchronized (this.mMutex) {
      ConditionRecord recordLocked = getRecordLocked(uri, componentName, false);
      if (recordLocked == null) {
        Slog.w(this.TAG, "Unable to subscribe to " + componentName + " " + uri);
        return false;
      }
      if (recordLocked.subscribed && z) {
        return true;
      }
      subscribeLocked(recordLocked);
      return recordLocked.subscribed;
    }
  }

  public void unsubscribeIfNecessary(ComponentName componentName, Uri uri) {
    synchronized (this.mMutex) {
      ConditionRecord recordLocked = getRecordLocked(uri, componentName, false);
      if (recordLocked == null) {
        Slog.w(this.TAG, "Unable to unsubscribe to " + componentName + " " + uri);
        return;
      }
      if (recordLocked.subscribed) {
        unsubscribeLocked(recordLocked);
      }
    }
  }

  public final void subscribeLocked(ConditionRecord conditionRecord) {
    if (this.DEBUG) {
      Slog.d(this.TAG, "subscribeLocked " + conditionRecord);
    }
    IConditionProvider provider = provider(conditionRecord);
    if (provider != null) {
      try {
        Slog.d(
            this.TAG,
            "Subscribing to " + conditionRecord.f1715id + " with " + conditionRecord.component);
        provider.onSubscribe(conditionRecord.f1715id);
        conditionRecord.subscribed = true;
      } catch (RemoteException e) {
        e = e;
        Slog.w(this.TAG, "Error subscribing to " + conditionRecord, e);
      }
    }
    e = null;
    ZenLog.traceSubscribe(conditionRecord != null ? conditionRecord.f1715id : null, provider, e);
  }

  public static ArraySet safeSet(Object... objArr) {
    ArraySet arraySet = new ArraySet();
    if (objArr != null && objArr.length != 0) {
      for (Object obj : objArr) {
        if (obj != null) {
          arraySet.add(obj);
        }
      }
    }
    return arraySet;
  }

  public final void unsubscribeLocked(ConditionRecord conditionRecord) {
    if (this.DEBUG) {
      Slog.d(this.TAG, "unsubscribeLocked " + conditionRecord);
    }
    IConditionProvider provider = provider(conditionRecord);
    if (provider != null) {
      try {
        provider.onUnsubscribe(conditionRecord.f1715id);
        e = null;
      } catch (RemoteException e) {
        e = e;
        Slog.w(this.TAG, "Error unsubscribing to " + conditionRecord, e);
      }
      conditionRecord.subscribed = false;
    } else {
      e = null;
    }
    ZenLog.traceUnsubscribe(conditionRecord != null ? conditionRecord.f1715id : null, provider, e);
  }

  public static IConditionProvider provider(ConditionRecord conditionRecord) {
    if (conditionRecord == null) {
      return null;
    }
    return provider(conditionRecord.info);
  }

  public static IConditionProvider provider(ManagedServices.ManagedServiceInfo managedServiceInfo) {
    if (managedServiceInfo == null) {
      return null;
    }
    return managedServiceInfo.service;
  }

  public class ConditionRecord {
    public final ComponentName component;
    public Condition condition;

    /* renamed from: id */
    public final Uri f1715id;
    public ManagedServices.ManagedServiceInfo info;
    public boolean subscribed;

    public ConditionRecord(Uri uri, ComponentName componentName) {
      this.f1715id = uri;
      this.component = componentName;
    }

    public String toString() {
      return "ConditionRecord[id="
          + this.f1715id
          + ",component="
          + this.component
          + ",subscribed="
          + this.subscribed
          + ']';
    }
  }
}
