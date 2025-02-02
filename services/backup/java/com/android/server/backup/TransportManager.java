package com.android.server.backup;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.util.Preconditions;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.OnTransportRegisteredListener;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.transport.TransportConnectionManager;
import com.android.server.backup.transport.TransportNotAvailableException;
import com.android.server.backup.transport.TransportNotRegisteredException;
import com.android.server.backup.transport.TransportStats;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class TransportManager {
  public static final String SERVICE_ACTION_TRANSPORT_HOST = "android.backup.TRANSPORT_HOST";
  public volatile String mCurrentTransportName;
  public OnTransportRegisteredListener mOnTransportRegisteredListener;
  public final PackageManager mPackageManager;
  public final Map mRegisteredTransportsDescriptionMap;
  public final TransportConnectionManager mTransportConnectionManager;
  public final Object mTransportLock;
  public final Intent mTransportServiceIntent;
  public final TransportStats mTransportStats;
  public final Set mTransportWhitelist;
  public final int mUserId;

  public static /* synthetic */ void lambda$new$0(String str, String str2) {}

  public static /* synthetic */ boolean lambda$onPackageAdded$1(ComponentName componentName) {
    return true;
  }

  public static /* synthetic */ boolean lambda$registerTransports$2(ComponentName componentName) {
    return true;
  }

  public TransportManager(int i, Context context, Set set, String str) {
    this.mTransportServiceIntent = new Intent(SERVICE_ACTION_TRANSPORT_HOST);
    this.mOnTransportRegisteredListener =
        new OnTransportRegisteredListener() { // from class:
                                              // com.android.server.backup.TransportManager$$ExternalSyntheticLambda0
          @Override // com.android.server.backup.transport.OnTransportRegisteredListener
          public final void onTransportRegistered(String str2, String str3) {
            TransportManager.lambda$new$0(str2, str3);
          }
        };
    this.mTransportLock = new Object();
    this.mRegisteredTransportsDescriptionMap = new ArrayMap();
    this.mUserId = i;
    this.mPackageManager = context.getPackageManager();
    this.mTransportWhitelist = (Set) Preconditions.checkNotNull(set);
    this.mCurrentTransportName = str;
    TransportStats transportStats = new TransportStats();
    this.mTransportStats = transportStats;
    this.mTransportConnectionManager = new TransportConnectionManager(i, context, transportStats);
  }

  public TransportManager(
      int i,
      Context context,
      Set set,
      String str,
      TransportConnectionManager transportConnectionManager) {
    this.mTransportServiceIntent = new Intent(SERVICE_ACTION_TRANSPORT_HOST);
    this.mOnTransportRegisteredListener =
        new OnTransportRegisteredListener() { // from class:
                                              // com.android.server.backup.TransportManager$$ExternalSyntheticLambda0
          @Override // com.android.server.backup.transport.OnTransportRegisteredListener
          public final void onTransportRegistered(String str2, String str3) {
            TransportManager.lambda$new$0(str2, str3);
          }
        };
    this.mTransportLock = new Object();
    this.mRegisteredTransportsDescriptionMap = new ArrayMap();
    this.mUserId = i;
    this.mPackageManager = context.getPackageManager();
    this.mTransportWhitelist = (Set) Preconditions.checkNotNull(set);
    this.mCurrentTransportName = str;
    this.mTransportStats = new TransportStats();
    this.mTransportConnectionManager = transportConnectionManager;
  }

  public void setOnTransportRegisteredListener(
      OnTransportRegisteredListener onTransportRegisteredListener) {
    this.mOnTransportRegisteredListener = onTransportRegisteredListener;
  }

  public void onPackageAdded(String str) {
    registerTransportsFromPackage(
        str,
        new Predicate() { // from class:
                          // com.android.server.backup.TransportManager$$ExternalSyntheticLambda3
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$onPackageAdded$1;
            lambda$onPackageAdded$1 = TransportManager.lambda$onPackageAdded$1((ComponentName) obj);
            return lambda$onPackageAdded$1;
          }
        });
  }

  public void onPackageRemoved(String str) {
    synchronized (this.mTransportLock) {
      this.mRegisteredTransportsDescriptionMap.keySet().removeIf(fromPackageFilter(str));
    }
  }

  public void onPackageEnabled(String str) {
    onPackageAdded(str);
  }

  public void onPackageDisabled(String str) {
    onPackageRemoved(str);
  }

  public void onPackageChanged(String str, String... strArr) {
    if (strArr.length == 1 && strArr[0].equals(str)) {
      try {
        int applicationEnabledSetting = this.mPackageManager.getApplicationEnabledSetting(str);
        if (applicationEnabledSetting == 0) {
          onPackageEnabled(str);
          return;
        }
        if (applicationEnabledSetting == 1) {
          onPackageEnabled(str);
          return;
        }
        if (applicationEnabledSetting == 2) {
          onPackageDisabled(str);
          return;
        }
        if (applicationEnabledSetting == 3) {
          onPackageDisabled(str);
          return;
        }
        Slog.w(
            "BackupTransportManager",
            addUserIdToLogMessage(
                this.mUserId, "Package " + str + " enabled setting: " + applicationEnabledSetting));
        return;
      } catch (IllegalArgumentException unused) {
        return;
      }
    }
    final ArraySet arraySet = new ArraySet(strArr.length);
    for (String str2 : strArr) {
      arraySet.add(new ComponentName(str, str2));
    }
    if (arraySet.isEmpty()) {
      return;
    }
    synchronized (this.mTransportLock) {
      this.mRegisteredTransportsDescriptionMap
          .keySet()
          .removeIf(
              new Predicate() { // from class:
                                // com.android.server.backup.TransportManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  return arraySet.contains((ComponentName) obj);
                }
              });
    }
    registerTransportsFromPackage(
        str,
        new Predicate() { // from class:
                          // com.android.server.backup.TransportManager$$ExternalSyntheticLambda2
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            return arraySet.contains((ComponentName) obj);
          }
        });
  }

  public ComponentName[] getRegisteredTransportComponents() {
    ComponentName[] componentNameArr;
    synchronized (this.mTransportLock) {
      componentNameArr =
          (ComponentName[])
              this.mRegisteredTransportsDescriptionMap
                  .keySet()
                  .toArray(new ComponentName[this.mRegisteredTransportsDescriptionMap.size()]);
    }
    return componentNameArr;
  }

  public String[] getRegisteredTransportNames() {
    String[] strArr;
    synchronized (this.mTransportLock) {
      strArr = new String[this.mRegisteredTransportsDescriptionMap.size()];
      Iterator it = this.mRegisteredTransportsDescriptionMap.values().iterator();
      int i = 0;
      while (it.hasNext()) {
        strArr[i] = ((TransportDescription) it.next()).name;
        i++;
      }
    }
    return strArr;
  }

  public Set getTransportWhitelist() {
    return this.mTransportWhitelist;
  }

  public String getCurrentTransportName() {
    return this.mCurrentTransportName;
  }

  public ComponentName getCurrentTransportComponent() {
    synchronized (this.mTransportLock) {
      if (this.mCurrentTransportName == null) {
        return null;
      }
      return getRegisteredTransportComponentOrThrowLocked(this.mCurrentTransportName);
    }
  }

  public String getTransportName(ComponentName componentName) {
    String str;
    synchronized (this.mTransportLock) {
      str = getRegisteredTransportDescriptionOrThrowLocked(componentName).name;
    }
    return str;
  }

  public String getTransportDirName(ComponentName componentName) {
    String str;
    synchronized (this.mTransportLock) {
      str = getRegisteredTransportDescriptionOrThrowLocked(componentName).transportDirName;
    }
    return str;
  }

  public String getTransportDirName(String str) {
    String str2;
    synchronized (this.mTransportLock) {
      str2 = getRegisteredTransportDescriptionOrThrowLocked(str).transportDirName;
    }
    return str2;
  }

  public Intent getTransportConfigurationIntent(String str) {
    Intent intent;
    synchronized (this.mTransportLock) {
      intent = getRegisteredTransportDescriptionOrThrowLocked(str).configurationIntent;
    }
    return intent;
  }

  public String getTransportCurrentDestinationString(String str) {
    String str2;
    synchronized (this.mTransportLock) {
      str2 = getRegisteredTransportDescriptionOrThrowLocked(str).currentDestinationString;
    }
    return str2;
  }

  public Intent getTransportDataManagementIntent(String str) {
    Intent intent;
    synchronized (this.mTransportLock) {
      intent = getRegisteredTransportDescriptionOrThrowLocked(str).dataManagementIntent;
    }
    return intent;
  }

  public CharSequence getTransportDataManagementLabel(String str) {
    CharSequence charSequence;
    synchronized (this.mTransportLock) {
      charSequence = getRegisteredTransportDescriptionOrThrowLocked(str).dataManagementLabel;
    }
    return charSequence;
  }

  public boolean isTransportRegistered(String str) {
    boolean z;
    synchronized (this.mTransportLock) {
      z = getRegisteredTransportEntryLocked(str) != null;
    }
    return z;
  }

  public void forEachRegisteredTransport(Consumer consumer) {
    synchronized (this.mTransportLock) {
      Iterator it = this.mRegisteredTransportsDescriptionMap.values().iterator();
      while (it.hasNext()) {
        consumer.accept(((TransportDescription) it.next()).name);
      }
    }
  }

  public void updateTransportAttributes(
      ComponentName componentName,
      String str,
      Intent intent,
      String str2,
      Intent intent2,
      CharSequence charSequence) {
    synchronized (this.mTransportLock) {
      TransportDescription transportDescription =
          (TransportDescription) this.mRegisteredTransportsDescriptionMap.get(componentName);
      if (transportDescription == null) {
        Slog.e(
            "BackupTransportManager",
            addUserIdToLogMessage(
                this.mUserId, "Transport " + str + " not registered tried to change description"));
        return;
      }
      transportDescription.name = str;
      transportDescription.configurationIntent = intent;
      transportDescription.currentDestinationString = str2;
      transportDescription.dataManagementIntent = intent2;
      transportDescription.dataManagementLabel = charSequence;
      Slog.d(
          "BackupTransportManager",
          addUserIdToLogMessage(this.mUserId, "Transport " + str + " updated its attributes"));
    }
  }

  public final ComponentName getRegisteredTransportComponentOrThrowLocked(String str) {
    ComponentName registeredTransportComponentLocked = getRegisteredTransportComponentLocked(str);
    if (registeredTransportComponentLocked != null) {
      return registeredTransportComponentLocked;
    }
    throw new TransportNotRegisteredException(str);
  }

  public final TransportDescription getRegisteredTransportDescriptionOrThrowLocked(
      ComponentName componentName) {
    TransportDescription transportDescription =
        (TransportDescription) this.mRegisteredTransportsDescriptionMap.get(componentName);
    if (transportDescription != null) {
      return transportDescription;
    }
    throw new TransportNotRegisteredException(componentName);
  }

  public final TransportDescription getRegisteredTransportDescriptionOrThrowLocked(String str) {
    TransportDescription registeredTransportDescriptionLocked =
        getRegisteredTransportDescriptionLocked(str);
    if (registeredTransportDescriptionLocked != null) {
      return registeredTransportDescriptionLocked;
    }
    throw new TransportNotRegisteredException(str);
  }

  public final ComponentName getRegisteredTransportComponentLocked(String str) {
    Map.Entry registeredTransportEntryLocked = getRegisteredTransportEntryLocked(str);
    if (registeredTransportEntryLocked == null) {
      return null;
    }
    return (ComponentName) registeredTransportEntryLocked.getKey();
  }

  public final TransportDescription getRegisteredTransportDescriptionLocked(String str) {
    Map.Entry registeredTransportEntryLocked = getRegisteredTransportEntryLocked(str);
    if (registeredTransportEntryLocked == null) {
      return null;
    }
    return (TransportDescription) registeredTransportEntryLocked.getValue();
  }

  public final Map.Entry getRegisteredTransportEntryLocked(String str) {
    for (Map.Entry entry : this.mRegisteredTransportsDescriptionMap.entrySet()) {
      if (str.equals(((TransportDescription) entry.getValue()).name)) {
        return entry;
      }
    }
    return null;
  }

  public TransportConnection getTransportClient(String str, String str2) {
    try {
      return getTransportClientOrThrow(str, str2);
    } catch (TransportNotRegisteredException unused) {
      Slog.w(
          "BackupTransportManager",
          addUserIdToLogMessage(this.mUserId, "Transport " + str + " not registered"));
      return null;
    }
  }

  public TransportConnection getTransportClientOrThrow(String str, String str2) {
    TransportConnection transportClient;
    synchronized (this.mTransportLock) {
      ComponentName registeredTransportComponentLocked = getRegisteredTransportComponentLocked(str);
      if (registeredTransportComponentLocked == null) {
        throw new TransportNotRegisteredException(str);
      }
      transportClient =
          this.mTransportConnectionManager.getTransportClient(
              registeredTransportComponentLocked, str2);
    }
    return transportClient;
  }

  public TransportConnection getCurrentTransportClient(String str) {
    TransportConnection transportClient;
    if (this.mCurrentTransportName == null) {
      throw new IllegalStateException("No transport selected");
    }
    synchronized (this.mTransportLock) {
      transportClient = getTransportClient(this.mCurrentTransportName, str);
    }
    return transportClient;
  }

  public TransportConnection getCurrentTransportClientOrThrow(String str) {
    TransportConnection transportClientOrThrow;
    if (this.mCurrentTransportName == null) {
      throw new IllegalStateException("No transport selected");
    }
    synchronized (this.mTransportLock) {
      transportClientOrThrow = getTransportClientOrThrow(this.mCurrentTransportName, str);
    }
    return transportClientOrThrow;
  }

  public void disposeOfTransportClient(TransportConnection transportConnection, String str) {
    this.mTransportConnectionManager.disposeOfTransportClient(transportConnection, str);
  }

  public String selectTransport(String str) {
    String str2;
    synchronized (this.mTransportLock) {
      str2 = this.mCurrentTransportName;
      this.mCurrentTransportName = str;
    }
    return str2;
  }

  public int registerAndSelectTransport(ComponentName componentName) {
    synchronized (this.mTransportLock) {
      try {
        try {
          selectTransport(getTransportName(componentName));
        } catch (TransportNotRegisteredException unused) {
          int registerTransport = registerTransport(componentName);
          if (registerTransport != 0) {
            return registerTransport;
          }
          synchronized (this.mTransportLock) {
            try {
              try {
                selectTransport(getTransportName(componentName));
                return 0;
              } finally {
              }
            } catch (TransportNotRegisteredException unused2) {
              Slog.wtf(
                  "BackupTransportManager",
                  addUserIdToLogMessage(this.mUserId, "Transport got unregistered"));
              return -1;
            }
          }
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return 0;
  }

  public void registerTransports() {
    registerTransportsForIntent(
        this.mTransportServiceIntent,
        new Predicate() { // from class:
                          // com.android.server.backup.TransportManager$$ExternalSyntheticLambda1
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$registerTransports$2;
            lambda$registerTransports$2 =
                TransportManager.lambda$registerTransports$2((ComponentName) obj);
            return lambda$registerTransports$2;
          }
        });
  }

  public final void registerTransportsFromPackage(String str, Predicate predicate) {
    try {
      this.mPackageManager.getPackageInfoAsUser(str, 0, this.mUserId);
      registerTransportsForIntent(
          new Intent(this.mTransportServiceIntent).setPackage(str),
          predicate.and(fromPackageFilter(str)));
    } catch (PackageManager.NameNotFoundException unused) {
      Slog.e(
          "BackupTransportManager",
          addUserIdToLogMessage(
              this.mUserId, "Trying to register transports from package not found " + str));
    }
  }

  public final void registerTransportsForIntent(Intent intent, Predicate predicate) {
    List queryIntentServicesAsUser =
        this.mPackageManager.queryIntentServicesAsUser(intent, 0, this.mUserId);
    if (queryIntentServicesAsUser == null) {
      return;
    }
    Iterator it = queryIntentServicesAsUser.iterator();
    while (it.hasNext()) {
      ComponentName componentName = ((ResolveInfo) it.next()).serviceInfo.getComponentName();
      if (predicate.test(componentName) && isTransportTrusted(componentName)) {
        registerTransport(componentName);
      }
    }
  }

  public final boolean isTransportTrusted(ComponentName componentName) {
    if (!this.mTransportWhitelist.contains(componentName)) {
      Slog.w(
          "BackupTransportManager",
          addUserIdToLogMessage(
              this.mUserId,
              "BackupTransport " + componentName.flattenToShortString() + " not whitelisted."));
      return false;
    }
    try {
      if ((this.mPackageManager.getPackageInfoAsUser(
                      componentName.getPackageName(), 0, this.mUserId)
                  .applicationInfo
                  .privateFlags
              & 8)
          != 0) {
        return true;
      }
      Slog.w(
          "BackupTransportManager",
          addUserIdToLogMessage(
              this.mUserId,
              "Transport package " + componentName.getPackageName() + " not privileged"));
      return false;
    } catch (PackageManager.NameNotFoundException e) {
      Slog.w(
          "BackupTransportManager", addUserIdToLogMessage(this.mUserId, "Package not found."), e);
      return false;
    }
  }

  public final int registerTransport(ComponentName componentName) {
    checkCanUseTransport();
    if (!isTransportTrusted(componentName)) {
      return -2;
    }
    String flattenToShortString = componentName.flattenToShortString();
    Bundle bundle = new Bundle();
    bundle.putBoolean("android.app.backup.extra.TRANSPORT_REGISTRATION", true);
    TransportConnection transportClient =
        this.mTransportConnectionManager.getTransportClient(
            componentName, bundle, "TransportManager.registerTransport()");
    int i = -1;
    try {
      BackupTransportClient connectOrThrow =
          transportClient.connectOrThrow("TransportManager.registerTransport()");
      try {
        String name = connectOrThrow.name();
        String transportDirName = connectOrThrow.transportDirName();
        registerTransport(componentName, connectOrThrow);
        Slog.d(
            "BackupTransportManager",
            addUserIdToLogMessage(
                this.mUserId, "Transport " + flattenToShortString + " registered"));
        this.mOnTransportRegisteredListener.onTransportRegistered(name, transportDirName);
        i = 0;
      } catch (RemoteException unused) {
        Slog.e(
            "BackupTransportManager",
            addUserIdToLogMessage(
                this.mUserId, "Transport " + flattenToShortString + " died while registering"));
      }
      this.mTransportConnectionManager.disposeOfTransportClient(
          transportClient, "TransportManager.registerTransport()");
      return i;
    } catch (TransportNotAvailableException unused2) {
      Slog.e(
          "BackupTransportManager",
          addUserIdToLogMessage(
              this.mUserId,
              "Couldn't connect to transport " + flattenToShortString + " for registration"));
      this.mTransportConnectionManager.disposeOfTransportClient(
          transportClient, "TransportManager.registerTransport()");
      return -1;
    }
  }

  public final void registerTransport(
      ComponentName componentName, BackupTransportClient backupTransportClient) {
    checkCanUseTransport();
    TransportDescription transportDescription =
        new TransportDescription(
            backupTransportClient.name(),
            backupTransportClient.transportDirName(),
            backupTransportClient.configurationIntent(),
            backupTransportClient.currentDestinationString(),
            backupTransportClient.dataManagementIntent(),
            backupTransportClient.dataManagementIntentLabel());
    synchronized (this.mTransportLock) {
      this.mRegisteredTransportsDescriptionMap.put(componentName, transportDescription);
    }
  }

  public final void checkCanUseTransport() {
    Preconditions.checkState(
        !Thread.holdsLock(this.mTransportLock), "Can't call transport with transport lock held");
  }

  public void dumpTransportClients(PrintWriter printWriter) {
    this.mTransportConnectionManager.dump(printWriter);
  }

  public void dumpTransportStats(PrintWriter printWriter) {
    this.mTransportStats.dump(printWriter);
  }

  public static Predicate fromPackageFilter(final String str) {
    return new Predicate() { // from class:
                             // com.android.server.backup.TransportManager$$ExternalSyntheticLambda4
      @Override // java.util.function.Predicate
      public final boolean test(Object obj) {
        boolean lambda$fromPackageFilter$3;
        lambda$fromPackageFilter$3 =
            TransportManager.lambda$fromPackageFilter$3(str, (ComponentName) obj);
        return lambda$fromPackageFilter$3;
      }
    };
  }

  public static /* synthetic */ boolean lambda$fromPackageFilter$3(
      String str, ComponentName componentName) {
    return str.equals(componentName.getPackageName());
  }

  public class TransportDescription {
    public Intent configurationIntent;
    public String currentDestinationString;
    public Intent dataManagementIntent;
    public CharSequence dataManagementLabel;
    public String name;
    public final String transportDirName;

    public TransportDescription(
        String str,
        String str2,
        Intent intent,
        String str3,
        Intent intent2,
        CharSequence charSequence) {
      this.name = str;
      this.transportDirName = str2;
      this.configurationIntent = intent;
      this.currentDestinationString = str3;
      this.dataManagementIntent = intent2;
      this.dataManagementLabel = charSequence;
    }
  }

  public static String addUserIdToLogMessage(int i, String str) {
    return "[UserID:" + i + "] " + str;
  }
}
