package com.android.server.sepunion;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.sepunion.eventdelegator.EventProcessHandler;
import com.android.server.sepunion.eventdelegator.ListenerContainer;
import com.android.server.sepunion.eventdelegator.PendingIntentWithConditions;
import com.android.server.sepunion.eventdelegator.UnionContentBroadcastReceiver;
import com.android.server.sepunion.eventdelegator.UnionContentObserver;
import com.android.server.sepunion.eventdelegator.UnionEventListenerItem;
import com.samsung.android.sepunion.IDeviceInfoManager;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public class SemDeviceInfoManagerService extends IDeviceInfoManager.Stub
    implements AbsSemSystemService, AbsSemSystemServiceForS {
  public static final String TAG = SemDeviceInfoManagerService.class.getSimpleName();
  public final Context mContext;
  public final EventProcessHandler mHandler;
  public final Object mLock = new Object();
  public final Object mCallbackLock = new Object();
  public boolean mIsWatchingPackageRemoved = false;
  public final SparseArray mListenerContainers = new SparseArray();
  public final SparseArray mSystemCallbacks = new SparseArray();

  public class SystemCallbacks {
    public final HashMap mContentObservers = new HashMap();
    public final HashMap mBroadcastReceivers = new HashMap();
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onBootPhase(int i) {}

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onCreate(Bundle bundle) {}

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserStopped(int i) {}

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserSwitching(int i, int i2) {}

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserUnlocked(int i) {}

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserUnlocking(int i) {}

  public SemDeviceInfoManagerService(Context context) {
    Log.d(TAG, "SemDeviceInfoManagerService");
    this.mContext = context;
    HandlerThread handlerThread = new HandlerThread("SemEventDelegationHandler");
    handlerThread.start();
    this.mHandler = new EventProcessHandler(context, this, handlerThread);
  }

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserStarting(int i) {
    startWatchingPackageRemoved();
  }

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserStopping(int i) {
    clearEventListenersAsUser(i);
  }

  public final void startWatchingPackageRemoved() {
    if (this.mIsWatchingPackageRemoved) {
      return;
    }
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.PACKAGE_REMOVED_INTERNAL");
    intentFilter.addDataScheme("package");
    this.mContext.registerReceiverAsUser(
        new BroadcastReceiver() { // from class:
                                  // com.android.server.sepunion.SemDeviceInfoManagerService.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
            Uri data = intent.getData();
            String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
            if (intExtra < 0 || schemeSpecificPart == null) {
              Log.w(
                  SemDeviceInfoManagerService.TAG,
                  "Intent contains invalid userId or package name");
              return;
            }
            Log.d(
                SemDeviceInfoManagerService.TAG,
                "Package " + schemeSpecificPart + " is removed on User " + intExtra);
            SemDeviceInfoManagerService.this.clearPendingIntentAsUserInternal(
                schemeSpecificPart, intExtra);
          }
        },
        UserHandle.ALL,
        intentFilter,
        null,
        this.mHandler);
    this.mIsWatchingPackageRemoved = true;
  }

  public ListenerContainer getSystemListenerContainer() {
    return getListenerContainer(ActivityManager.getCurrentUser());
  }

  public ListenerContainer getListenerContainer(int i) {
    ListenerContainer listenerContainer;
    synchronized (this.mLock) {
      listenerContainer = (ListenerContainer) this.mListenerContainers.get(i);
      if (listenerContainer == null) {
        listenerContainer = new ListenerContainer(i);
        this.mListenerContainers.put(i, listenerContainer);
      }
    }
    return listenerContainer;
  }

  public final void removeListenerContainer(int i) {
    synchronized (this.mLock) {
      this.mListenerContainers.remove(i);
    }
  }

  public SystemCallbacks getSystemCallbacks(int i) {
    SystemCallbacks systemCallbacks;
    synchronized (this.mCallbackLock) {
      systemCallbacks = (SystemCallbacks) this.mSystemCallbacks.get(i);
      if (systemCallbacks == null) {
        systemCallbacks = new SystemCallbacks();
        this.mSystemCallbacks.put(i, systemCallbacks);
      }
    }
    return systemCallbacks;
  }

  public final void removeSystemCallbacks(int i) {
    synchronized (this.mCallbackLock) {
      this.mSystemCallbacks.remove(i);
    }
  }

  public EventProcessHandler getEventProcessHandler() {
    return this.mHandler;
  }

  public void registerPendingIntentForUriAsUser(
      Uri uri, PendingIntent pendingIntent, String str, int i) {
    if (uri == null || pendingIntent == null || str == null) {
      return;
    }
    enforceCallingPermission("registerPendingIntentForUriAsUser");
    String str2 = "Register uri = " + uri.toString() + ", package = " + str + ", userId = " + i;
    String str3 = TAG;
    Log.d(str3, str2);
    Log.addLogString(str3, str2);
    synchronized (this.mLock) {
      ListenerContainer listenerContainer = getListenerContainer(i);
      if (listenerContainer.mUriEventMap.containsKey(uri)) {
        ((UnionEventListenerItem) listenerContainer.mUriEventMap.get(uri)).add(str, pendingIntent);
      } else {
        UnionEventListenerItem unionEventListenerItem = new UnionEventListenerItem();
        unionEventListenerItem.add(str, pendingIntent);
        listenerContainer.mUriEventMap.put(uri, unionEventListenerItem);
        registerContentObserver(
            uri, pendingIntent.getIntent().getBooleanExtra("notify_for_descendants", false), i);
      }
    }
  }

  public final void registerContentObserver(final Uri uri, final boolean z, final int i) {
    if (i < 0 || uri == null) {
      return;
    }
    synchronized (this.mCallbackLock) {
      SystemCallbacks systemCallbacks = getSystemCallbacks(i);
      if (systemCallbacks.mContentObservers.get(uri) == null) {
        Log.d(TAG, "Creating new UnionContentObserver for the uri:" + uri);
        final UnionContentObserver unionContentObserver =
            new UnionContentObserver(this.mHandler, this);
        systemCallbacks.mContentObservers.put(uri, unionContentObserver);
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.sepunion.SemDeviceInfoManagerService$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                SemDeviceInfoManagerService.this.lambda$registerContentObserver$0(
                    uri, z, unionContentObserver, i);
              }
            });
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$registerContentObserver$0(
      Uri uri, boolean z, ContentObserver contentObserver, int i) {
    try {
      this.mContext.getContentResolver().registerContentObserver(uri, z, contentObserver, i);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void unregisterPendingIntentForUriAsUser(
      Uri uri, PendingIntent pendingIntent, String str, int i) {
    if (uri == null || pendingIntent == null || str == null) {
      return;
    }
    enforceCallingPermission("unregisterPendingIntentForUriAsUser");
    String str2 = "Unregister uri = " + uri + ", package = " + str + ", userId = " + i;
    String str3 = TAG;
    Log.d(str3, str2);
    Log.addLogString(str3, str2);
    synchronized (this.mLock) {
      ListenerContainer listenerContainer = getListenerContainer(i);
      UnionEventListenerItem unionEventListenerItem =
          (UnionEventListenerItem) listenerContainer.mUriEventMap.get(uri);
      if (unionEventListenerItem != null) {
        unionEventListenerItem.remove(str, pendingIntent);
        if (unionEventListenerItem.isEmpty()) {
          listenerContainer.mUriEventMap.remove(uri);
          unregisterContentObserver(uri, i);
        }
      }
    }
  }

  public final void unregisterContentObserver(Uri uri, int i) {
    if (i < 0 || uri == null) {
      return;
    }
    synchronized (this.mCallbackLock) {
      final ContentObserver contentObserver =
          (ContentObserver) getSystemCallbacks(i).mContentObservers.remove(uri);
      if (contentObserver != null) {
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.sepunion.SemDeviceInfoManagerService$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                SemDeviceInfoManagerService.this.lambda$unregisterContentObserver$1(
                    contentObserver);
              }
            });
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$unregisterContentObserver$1(ContentObserver contentObserver) {
    try {
      this.mContext.getContentResolver().unregisterContentObserver(contentObserver);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void registerPendingIntentForIntentAsUser(
      IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) {
    enforceCallingPermission("registerPendingIntentForIntentAsUser");
    registerPendingIntentInternal(intentFilter, pendingIntent, 0, null, str, i);
  }

  public void registerPendingIntent(
      IntentFilter intentFilter,
      PendingIntent pendingIntent,
      int i,
      List list,
      String str,
      int i2) {
    enforceCallingPermission("registerPendingIntent");
    registerPendingIntentInternal(intentFilter, pendingIntent, i, list, str, i2);
  }

  public final void registerPendingIntentInternal(
      IntentFilter intentFilter,
      PendingIntent pendingIntent,
      int i,
      List list,
      String str,
      int i2) {
    if (intentFilter == null
        || intentFilter.countActions() <= 0
        || pendingIntent == null
        || str == null) {
      return;
    }
    if (i == 0 || (i & 3) != 0) {
      int countActions = intentFilter.countActions();
      StringBuilder sb = new StringBuilder();
      for (int i3 = 0; i3 < countActions; i3++) {
        sb.append(intentFilter.getAction(i3) + ", ");
      }
      String str2 =
          "Register intent = "
              + sb.toString()
              + " package = "
              + str
              + ", userId = "
              + i2
              + ", flag = "
              + i
              + ", conditions = "
              + list;
      String str3 = TAG;
      Log.d(str3, str2);
      Log.addLogString(str3, str2);
      PendingIntentWithConditions pendingIntentWithConditions =
          new PendingIntentWithConditions(pendingIntent, i, i == 0 ? null : new ArrayList(list));
      for (int i4 = 0; i4 < countActions; i4++) {
        synchronized (this.mLock) {
          ListenerContainer listenerContainer = getListenerContainer(i2);
          String action = intentFilter.getAction(i4);
          if (!listenerContainer.mIntentActionMap.containsKey(action)) {
            UnionEventListenerItem unionEventListenerItem = new UnionEventListenerItem();
            unionEventListenerItem.add(str, pendingIntentWithConditions);
            listenerContainer.mIntentActionMap.put(action, unionEventListenerItem);
            registerReceiver(action, i2);
          } else {
            ((UnionEventListenerItem) listenerContainer.mIntentActionMap.get(action))
                .add(str, pendingIntentWithConditions);
          }
        }
      }
    }
  }

  public final void registerReceiver(final String str, final int i) {
    if (i < 0 || TextUtils.isEmpty(str)) {
      return;
    }
    synchronized (this.mCallbackLock) {
      SystemCallbacks systemCallbacks = getSystemCallbacks(i);
      if (systemCallbacks.mBroadcastReceivers.get(str) == null) {
        Log.d(TAG, "Adding new UnionBroadcastReceiver for the action: " + str + " in User " + i);
        final UnionContentBroadcastReceiver unionContentBroadcastReceiver =
            new UnionContentBroadcastReceiver(this);
        systemCallbacks.mBroadcastReceivers.put(str, unionContentBroadcastReceiver);
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.sepunion.SemDeviceInfoManagerService$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SemDeviceInfoManagerService.this.lambda$registerReceiver$2(
                    str, unionContentBroadcastReceiver, i);
              }
            });
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$registerReceiver$2(
      String str, BroadcastReceiver broadcastReceiver, int i) {
    try {
      this.mContext.registerReceiverAsUser(
          broadcastReceiver,
          UserHandle.of(i),
          createIntentFilterWithAction(str),
          null,
          this.mHandler);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final IntentFilter createIntentFilterWithAction(String str) {
    IntentFilter intentFilter = new IntentFilter(str);
    if (str.equals("android.intent.action.PACKAGE_DATA_CLEARED")
        || str.equals("android.intent.action.PACKAGE_ADDED")
        || str.equals("android.intent.action.PACKAGE_CHANGED")
        || str.equals("android.intent.action.PACKAGE_REPLACED")
        || str.equals("android.intent.action.PACKAGE_FULLY_REMOVED")
        || str.equals("android.intent.action.PACKAGE_REMOVED")) {
      intentFilter.addDataScheme("package");
    } else if (str.equals("android.intent.action.PROVIDER_CHANGED")) {
      intentFilter.addDataScheme("content");
    }
    return intentFilter;
  }

  public void registerPendingIntentForIntentForAllUsers(
      IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) {
    enforceCallingPermission("registerPendingIntentForIntentForAllUsers");
    Log.e(TAG, "Do not support observing Intent for all users");
  }

  public void unregisterPendingIntent(
      IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) {
    enforceCallingPermission("unregisterPendingIntent");
    unregisterPendingIntentInternal(intentFilter, pendingIntent, str, i);
  }

  public void unregisterPendingIntentForIntentAsUser(
      IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) {
    enforceCallingPermission("unregisterPendingIntentForIntentAsUser");
    unregisterPendingIntentInternal(intentFilter, pendingIntent, str, i);
  }

  public final void unregisterPendingIntentInternal(
      IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) {
    if (intentFilter == null
        || intentFilter.actionsIterator() == null
        || intentFilter.countActions() <= 0
        || pendingIntent == null
        || str == null) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    for (int i2 = 0; i2 < intentFilter.countActions(); i2++) {
      sb.append(intentFilter.getAction(i2) + ", ");
    }
    String str2 = "Unregister intent = " + sb.toString() + ", package = " + str + ", userId = " + i;
    String str3 = TAG;
    Log.d(str3, str2);
    Log.addLogString(str3, str2);
    intentFilter.actionsIterator();
    for (int i3 = 0; i3 < intentFilter.countActions(); i3++) {
      String action = intentFilter.getAction(i3);
      synchronized (this.mLock) {
        ListenerContainer listenerContainer = getListenerContainer(i);
        UnionEventListenerItem unionEventListenerItem =
            (UnionEventListenerItem) listenerContainer.mIntentActionMap.get(action);
        if (unionEventListenerItem != null) {
          unionEventListenerItem.remove(str, pendingIntent);
          if (unionEventListenerItem.isEmpty()) {
            listenerContainer.mIntentActionMap.remove(action);
            unregisterReceiver(action, i);
          }
        }
      }
    }
  }

  public final void unregisterReceiver(String str, int i) {
    if (i < 0 || TextUtils.isEmpty(str)) {
      return;
    }
    synchronized (this.mCallbackLock) {
      final BroadcastReceiver broadcastReceiver =
          (BroadcastReceiver) getSystemCallbacks(i).mBroadcastReceivers.remove(str);
      if (broadcastReceiver != null) {
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.sepunion.SemDeviceInfoManagerService$$ExternalSyntheticLambda3
              @Override // java.lang.Runnable
              public final void run() {
                SemDeviceInfoManagerService.this.lambda$unregisterReceiver$3(broadcastReceiver);
              }
            });
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$unregisterReceiver$3(BroadcastReceiver broadcastReceiver) {
    try {
      this.mContext.unregisterReceiver(broadcastReceiver);
    } catch (Exception e) {
      Log.e(TAG, "Failed to unregister receiver " + e);
    }
  }

  public void registerPendingIntentForCustomEventAsUser(
      String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) {
    boolean z;
    if (str == null || pendingIntent == null) {
      return;
    }
    if (bundle == null
        && (str.equals("monitor_activity_state") || str.equals("monitor_package_state"))) {
      return;
    }
    enforceCallingPermission("registerPendingIntentForCustomEventAsUser");
    String str3 = "Register custom event = " + str + ", package = " + str2 + ", userId = " + i;
    String str4 = TAG;
    Log.d(str4, str3);
    Log.addLogString(str4, str3);
    ArrayList customEventKeys = getCustomEventKeys(str, bundle);
    synchronized (this.mLock) {
      ListenerContainer listenerContainer = getListenerContainer(i);
      Iterator it = customEventKeys.iterator();
      while (it.hasNext()) {
        String str5 = (String) it.next();
        if (listenerContainer.mCustomEventMap.containsKey(str5)) {
          UnionEventListenerItem unionEventListenerItem =
              (UnionEventListenerItem) listenerContainer.mCustomEventMap.get(str);
          if (unionEventListenerItem != null) {
            unionEventListenerItem.add(str2, pendingIntent);
          }
        } else {
          Iterator it2 = listenerContainer.mCustomEventMap.keySet().iterator();
          while (true) {
            if (!it2.hasNext()) {
              z = false;
              break;
            } else if (((String) it2.next()).startsWith(str)) {
              z = true;
              break;
            }
          }
          if (!z) {
            this.mHandler.notifyToHandler(getCustomEventMessageId(str, true), bundle);
          }
          UnionEventListenerItem unionEventListenerItem2 = new UnionEventListenerItem();
          unionEventListenerItem2.add(str2, pendingIntent);
          listenerContainer.mCustomEventMap.put(str5, unionEventListenerItem2);
        }
      }
    }
  }

  public void unregisterPendingIntentForCustomEventAsUser(
      String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) {
    if (str == null || pendingIntent == null) {
      return;
    }
    if (bundle == null
        && (str.equals("monitor_activity_state") || str.equals("monitor_package_state"))) {
      return;
    }
    enforceCallingPermission("unregisterPendingIntentForCustomEventAsUser");
    String str3 = "Unregister custom event = " + str + ", package = " + str2 + ", userId = " + i;
    String str4 = TAG;
    Log.d(str4, str3);
    Log.addLogString(str4, str3);
    ArrayList customEventKeys = getCustomEventKeys(str, bundle);
    synchronized (this.mLock) {
      ListenerContainer listenerContainer = getListenerContainer(i);
      Iterator it = customEventKeys.iterator();
      while (it.hasNext()) {
        String str5 = (String) it.next();
        UnionEventListenerItem unionEventListenerItem =
            (UnionEventListenerItem) listenerContainer.mCustomEventMap.get(str5);
        if (unionEventListenerItem != null) {
          unionEventListenerItem.remove(str2, pendingIntent);
          if (unionEventListenerItem.isEmpty()) {
            listenerContainer.mCustomEventMap.remove(str5);
            this.mHandler.notifyToHandler(getCustomEventMessageId(str, false), bundle);
          } else {
            listenerContainer.mCustomEventMap.put(str5, unionEventListenerItem);
          }
        }
      }
    }
  }

  public final ArrayList getCustomEventKeys(String str, Bundle bundle) {
    ArrayList<String> stringArrayList;
    ArrayList arrayList = new ArrayList();
    if (!TextUtils.isEmpty(str)) {
      if (str.equals("monitor_call_state")) {
        arrayList.add(str);
      } else if (str.equals("monitor_activity_state")) {
        ArrayList parcelableArrayList = bundle.getParcelableArrayList("component_list");
        if (parcelableArrayList != null) {
          Iterator it = parcelableArrayList.iterator();
          while (it.hasNext()) {
            ComponentName componentName = (ComponentName) it.next();
            arrayList.add(
                "monitor_activity_state;"
                    + componentName.getPackageName()
                    + KnoxVpnFirewallHelper.DELIMITER
                    + componentName.getClassName());
          }
        }
      } else if (str.equals("monitor_package_state")
          && (stringArrayList = bundle.getStringArrayList("package_list")) != null) {
        Iterator<String> it2 = stringArrayList.iterator();
        while (it2.hasNext()) {
          arrayList.add("monitor_package_state;" + it2.next());
        }
      }
    }
    return arrayList;
  }

  public final int getCustomEventMessageId(String str, boolean z) {
    if (TextUtils.isEmpty(str)) {
      return -1;
    }
    if (str.startsWith("monitor_call_state")) {
      return z ? 1 : 2;
    }
    if (str.startsWith("monitor_package_state")) {
      return z ? 3 : 4;
    }
    str.startsWith("monitor_activity_state");
    return -1;
  }

  public void clearEventListenersAsUser(int i) {
    String str = "Clear event listeners for User " + i;
    String str2 = TAG;
    Log.d(str2, str);
    Log.addLogString(str2, str);
    synchronized (this.mLock) {
      ListenerContainer listenerContainer = getListenerContainer(i);
      Iterator it = new ArrayList(listenerContainer.mIntentActionMap.keySet()).iterator();
      while (it.hasNext()) {
        unregisterReceiver((String) it.next(), i);
      }
      Iterator it2 = new ArrayList(listenerContainer.mUriEventMap.keySet()).iterator();
      while (it2.hasNext()) {
        unregisterContentObserver((Uri) it2.next(), i);
      }
      removeListenerContainer(i);
      removeSystemCallbacks(i);
    }
  }

  public void clearPendingIntentAsUser(String str, int i) {
    enforceCallingPermission("clearPendingIntentAsUser");
    String str2 = "Clear pending intents for package = " + str + ", userId = " + i;
    String str3 = TAG;
    Log.d(str3, str2);
    Log.addLogString(str3, str2);
    clearPendingIntentAsUserInternal(str, i);
  }

  public final void clearPendingIntentAsUserInternal(String str, int i) {
    synchronized (this.mLock) {
      ListenerContainer listenerContainer = getListenerContainer(i);
      ArrayList<Uri> arrayList = new ArrayList(listenerContainer.mUriEventMap.keySet());
      ArrayList<String> arrayList2 = new ArrayList(listenerContainer.mIntentActionMap.keySet());
      ArrayList<String> arrayList3 = new ArrayList(listenerContainer.mCustomEventMap.keySet());
      for (Uri uri : arrayList) {
        UnionEventListenerItem unionEventListenerItem =
            (UnionEventListenerItem) listenerContainer.mUriEventMap.get(uri);
        if (unionEventListenerItem.clear(str) && unionEventListenerItem.isEmpty()) {
          listenerContainer.mUriEventMap.remove(uri);
          unregisterContentObserver(uri, i);
        }
      }
      for (String str2 : arrayList2) {
        UnionEventListenerItem unionEventListenerItem2 =
            (UnionEventListenerItem) listenerContainer.mIntentActionMap.get(str2);
        if (unionEventListenerItem2.clear(str) && unionEventListenerItem2.isEmpty()) {
          listenerContainer.mIntentActionMap.remove(str2);
          unregisterReceiver(str2, i);
        }
      }
      for (String str3 : arrayList3) {
        UnionEventListenerItem unionEventListenerItem3 =
            (UnionEventListenerItem) listenerContainer.mCustomEventMap.get(str3);
        if (unionEventListenerItem3.clear(str) && unionEventListenerItem3.isEmpty()) {
          listenerContainer.mCustomEventMap.remove(str3);
        }
      }
    }
  }

  public void sendIntentAction(
      UnionEventListenerItem unionEventListenerItem, Intent intent, int i) {
    if (unionEventListenerItem == null) {
      return;
    }
    synchronized (this.mLock) {
      for (String str : unionEventListenerItem.mUnionEventComponentsWithConditions.keySet()) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
          String uri = intent.getData().toString();
          if (!uri.contains(str)) {
            Log.d(TAG, "Data cleared package = " + uri + ", key = " + str);
          }
        }
        Iterator it =
            ((ArrayList) unionEventListenerItem.mUnionEventComponentsWithConditions.get(str))
                .iterator();
        while (it.hasNext()) {
          PendingIntentWithConditions pendingIntentWithConditions =
              (PendingIntentWithConditions) it.next();
          int flag = pendingIntentWithConditions.getFlag();
          boolean z = true;
          if (flag != 0) {
            if (flag == 1) {
              z =
                  containsPackageName(
                      pendingIntentWithConditions.getConditions(),
                      intent.getData().getSchemeSpecificPart());
            } else {
              z =
                  flag == 2
                      ? containsAnyPermission(
                          pendingIntentWithConditions.getConditions(),
                          intent.getData().getSchemeSpecificPart(),
                          i)
                      : false;
            }
          }
          if (z) {
            PendingIntent pendingIntent = pendingIntentWithConditions.getPendingIntent();
            try {
              Intent intent2 = new Intent();
              intent2.setAction(pendingIntent.getIntent().getAction());
              intent2.setData(intent.getData());
              intent2.putExtras(intent);
              intent2.putExtra("action_origin", intent.getAction());
              pendingIntent.send(this.mContext, 0, intent2, null, null);
              String str2 =
                  "Send intent " + intent2.getAction() + ", userId: " + i + " by broadcast";
              String str3 = TAG;
              Log.d(str3, str2);
              Log.addLogString(str3, str2);
            } catch (PendingIntent.CanceledException | NullPointerException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }

  public final boolean containsPackageName(ArrayList arrayList, String str) {
    if (arrayList == null || str == null) {
      Log.d(TAG, "containsPackageName() packageNameList = " + arrayList + ", packageName = " + str);
      return false;
    }
    return arrayList.contains(str);
  }

  public final boolean containsAnyPermission(ArrayList arrayList, String str, int i) {
    if (arrayList != null && str != null) {
      PackageManager packageManager = this.mContext.getPackageManager();
      int size = arrayList.size();
      for (int i2 = 0; i2 < size; i2++) {
        String str2 = (String) arrayList.get(i2);
        if (str2 != null && packageManager.checkPermission(str2, str) == 0) {
          return true;
        }
      }
    }
    return false;
  }

  public void reportUriChanged(
      UnionEventListenerItem unionEventListenerItem, boolean z, Uri uri, int i, boolean z2) {
    if (unionEventListenerItem == null) {
      return;
    }
    synchronized (this.mLock) {
      Iterator it = unionEventListenerItem.mUnionEventComponentsWithConditions.keySet().iterator();
      while (it.hasNext()) {
        Iterator it2 =
            ((ArrayList)
                    unionEventListenerItem.mUnionEventComponentsWithConditions.get(
                        (String) it.next()))
                .iterator();
        while (it2.hasNext()) {
          PendingIntent pendingIntent =
              ((PendingIntentWithConditions) it2.next()).getPendingIntent();
          Intent intent = pendingIntent.getIntent();
          intent.putExtra("uri", uri);
          boolean booleanExtra =
              z2 ? intent.getBooleanExtra("notify_for_descendants", false) : false;
          if (booleanExtra || !z2) {
            if (booleanExtra) {
              try {
                Log.d(TAG, "Notifying descendants changes.");
              } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
              }
            }
            pendingIntent.send(this.mContext, 0, intent, null, null);
            String str = "Send intent: " + intent.getAction() + ", userId: " + i + " by uri change";
            String str2 = TAG;
            Log.d(str2, str);
            Log.addLogString(str2, str);
          }
        }
      }
    }
  }

  public void reportCallStateChanged(
      UnionEventListenerItem unionEventListenerItem, int i, String str) {
    if (unionEventListenerItem == null) {
      return;
    }
    synchronized (this.mLock) {
      Iterator it = unionEventListenerItem.mUnionEventComponentsWithConditions.keySet().iterator();
      while (it.hasNext()) {
        Iterator it2 =
            ((ArrayList)
                    unionEventListenerItem.mUnionEventComponentsWithConditions.get(
                        (String) it.next()))
                .iterator();
        while (it2.hasNext()) {
          PendingIntent pendingIntent =
              ((PendingIntentWithConditions) it2.next()).getPendingIntent();
          Intent intent = pendingIntent.getIntent();
          intent.putExtra("call_state", i);
          intent.putExtra("phone_number", str);
          try {
            pendingIntent.send(this.mContext, 0, intent, null, null);
            String str2 = "Report call state changed, state = " + i;
            String str3 = TAG;
            Log.d(str3, str2);
            Log.addLogString(str3, str2);
          } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  public void reportPackageStateChanged(
      UnionEventListenerItem unionEventListenerItem, String str, String str2) {
    if (unionEventListenerItem == null) {
      return;
    }
    synchronized (this.mLock) {
      Iterator it = unionEventListenerItem.mUnionEventComponentsWithConditions.keySet().iterator();
      while (it.hasNext()) {
        Iterator it2 =
            ((ArrayList)
                    unionEventListenerItem.mUnionEventComponentsWithConditions.get(
                        (String) it.next()))
                .iterator();
        while (it2.hasNext()) {
          PendingIntent pendingIntent =
              ((PendingIntentWithConditions) it2.next()).getPendingIntent();
          Intent intent = pendingIntent.getIntent();
          intent.putExtra("package_name", str);
          intent.putExtra("package_state", str2);
          try {
            pendingIntent.send(this.mContext, 0, intent, null, null);
            String str3 = "Report package changed for " + str;
            String str4 = TAG;
            Log.d(str4, str3);
            Log.addLogString(str4, str3);
          } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  public int getNumPendingIntentAsUser(int i, String str, int i2) {
    int i3;
    int i4;
    int i5;
    ArrayList arrayList;
    ArrayList arrayList2;
    ArrayList arrayList3;
    enforceCallingPermission("getNumPendingIntentAsUser");
    synchronized (this.mLock) {
      ListenerContainer listenerContainer = getListenerContainer(i2);
      Set keySet = listenerContainer.mCustomEventMap.keySet();
      i3 = 0;
      if (keySet == null || keySet.size() <= 0) {
        i4 = 0;
      } else {
        Iterator it = keySet.iterator();
        i4 = 0;
        while (it.hasNext()) {
          UnionEventListenerItem unionEventListenerItem =
              (UnionEventListenerItem) listenerContainer.mCustomEventMap.get((String) it.next());
          if (unionEventListenerItem != null
              && (arrayList3 =
                      (ArrayList)
                          unionEventListenerItem.mUnionEventComponentsWithConditions.get(str))
                  != null) {
            i4 += arrayList3.size();
          }
        }
      }
      Set keySet2 = listenerContainer.mIntentActionMap.keySet();
      if (keySet2 == null || keySet2.size() <= 0) {
        i5 = 0;
      } else {
        Iterator it2 = keySet2.iterator();
        i5 = 0;
        while (it2.hasNext()) {
          UnionEventListenerItem unionEventListenerItem2 =
              (UnionEventListenerItem) listenerContainer.mIntentActionMap.get((String) it2.next());
          if (unionEventListenerItem2 != null
              && (arrayList2 =
                      (ArrayList)
                          unionEventListenerItem2.mUnionEventComponentsWithConditions.get(str))
                  != null) {
            i5 += arrayList2.size();
          }
        }
      }
      Set keySet3 = listenerContainer.mUriEventMap.keySet();
      if (keySet3 != null && keySet3.size() > 0) {
        Iterator it3 = keySet3.iterator();
        while (it3.hasNext()) {
          UnionEventListenerItem unionEventListenerItem3 =
              (UnionEventListenerItem) listenerContainer.mUriEventMap.get((Uri) it3.next());
          if (unionEventListenerItem3 != null
              && (arrayList =
                      (ArrayList)
                          unionEventListenerItem3.mUnionEventComponentsWithConditions.get(str))
                  != null) {
            i3 += arrayList.size();
          }
        }
      }
    }
    if (i == 0) {
      return i3 + i5 + i4;
    }
    if (i == 1) {
      return i3;
    }
    if (i == 2) {
      return i5;
    }
    if (i == 3) {
      return i4;
    }
    Log.d(TAG, "getNumPendingIntent(): Invalid type request. Requested type = " + i);
    return -1;
  }

  public final void enforceCallingPermission(String str) {
    int callingUid = Binder.getCallingUid();
    if (this.mContext.getPackageManager().checkSignatures(1000, callingUid) == 0) {
      return;
    }
    String str2 =
        "Permission denied: "
            + str
            + " callingUid="
            + callingUid
            + ", you need system uid of to be signed with the system certificate.";
    Log.d(TAG, str2);
    throw new SecurityException(str2);
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    synchronized (this.mLock) {
      printWriter.println(
          "\n"
              + "##### SEM DEVICE INFO MANAGER SERVICE #####\n"
              + "##### (dumpsys sepunion semeventdelegator) #####\n");
      int size = this.mListenerContainers.size();
      printWriter.println("Listener containers(Users: " + size + "):");
      for (int i = 0; i < size; i++) {
        int keyAt = this.mListenerContainers.keyAt(i);
        printWriter.println(
            "  Listener container for User " + keyAt + XmlUtils.STRING_ARRAY_SEPARATOR);
        ListenerContainer listenerContainer =
            (ListenerContainer) this.mListenerContainers.get(keyAt);
        if (listenerContainer != null) {
          Set<String> keySet = listenerContainer.mCustomEventMap.keySet();
          if (keySet != null && keySet.size() > 0) {
            printWriter.println("    Custom Event Map(registered event: " + keySet.size() + "):");
            for (String str : keySet) {
              printWriter.println("      Event: " + str + XmlUtils.STRING_ARRAY_SEPARATOR);
              UnionEventListenerItem unionEventListenerItem =
                  (UnionEventListenerItem) listenerContainer.mCustomEventMap.get(str);
              if (unionEventListenerItem != null) {
                printWriter.println(unionEventListenerItem.toStringForDump("      "));
              }
            }
            printWriter.println("");
          }
          Set<String> keySet2 = listenerContainer.mIntentActionMap.keySet();
          if (keySet2 != null && keySet2.size() > 0) {
            printWriter.println(
                "    Intent Action Map(registered action: " + keySet2.size() + "):");
            for (String str2 : keySet2) {
              printWriter.println("      Action: " + str2 + XmlUtils.STRING_ARRAY_SEPARATOR);
              UnionEventListenerItem unionEventListenerItem2 =
                  (UnionEventListenerItem) listenerContainer.mIntentActionMap.get(str2);
              if (unionEventListenerItem2 != null) {
                printWriter.println(unionEventListenerItem2.toStringForDump("      "));
              }
            }
            printWriter.println("");
          }
          Set<Uri> keySet3 = listenerContainer.mUriEventMap.keySet();
          if (keySet3 != null && keySet3.size() > 0) {
            printWriter.println("    URI Event Map(registered uri: " + keySet3.size() + "):");
            for (Uri uri : keySet3) {
              printWriter.println("      Uri: " + uri + XmlUtils.STRING_ARRAY_SEPARATOR);
              UnionEventListenerItem unionEventListenerItem3 =
                  (UnionEventListenerItem) listenerContainer.mUriEventMap.get(uri);
              if (unionEventListenerItem3 != null) {
                printWriter.println(unionEventListenerItem3.toStringForDump("      "));
              }
            }
          }
          printWriter.println();
        }
      }
      Log.dump(TAG, fileDescriptor, printWriter, strArr);
    }
  }
}
