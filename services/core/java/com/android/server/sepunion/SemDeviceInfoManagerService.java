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
import android.os.Message;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.SparseArray;

import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.sepunion.eventdelegator.EventProcessHandler;
import com.android.server.sepunion.eventdelegator.ListenerContainer;
import com.android.server.sepunion.eventdelegator.PendingIntentWithConditions;
import com.android.server.sepunion.eventdelegator.UnionContentBroadcastReceiver;
import com.android.server.sepunion.eventdelegator.UnionContentObserver;
import com.android.server.sepunion.eventdelegator.UnionEventListenerItem;

import com.samsung.android.knoxguard.service.SystemIntentProcessor;
import com.samsung.android.sepunion.IDeviceInfoManager;
import com.samsung.android.sepunion.Log;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemDeviceInfoManagerService extends IDeviceInfoManager.Stub
        implements AbsSemSystemService, AbsSemSystemServiceForS {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final EventProcessHandler mHandler;
    public final Object mLock = new Object();
    public final Object mCallbackLock = new Object();
    public boolean mIsWatchingPackageRemoved = false;
    public final SparseArray mListenerContainers = new SparseArray();
    public final SparseArray mSystemCallbacks = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemCallbacks {
        public final HashMap mContentObservers = new HashMap();
        public final HashMap mBroadcastReceivers = new HashMap();
    }

    public SemDeviceInfoManagerService(Context context) {
        Log.d("SemDeviceInfoManagerService", "SemDeviceInfoManagerService");
        this.mContext = context;
        this.mHandler =
                new EventProcessHandler(
                        context,
                        this,
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                                "SemEventDelegationHandler"));
    }

    public static ArrayList getCustomEventKeys(Bundle bundle, String str) {
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
                                        + ";"
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

    public static int getCustomEventMessageId(String str, boolean z) {
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
        Log.d("SemDeviceInfoManagerService", str);
        Log.addLogString("SemDeviceInfoManagerService", str);
        synchronized (this.mLock) {
            try {
                ListenerContainer listenerContainer = getListenerContainer(i);
                Iterator it = new ArrayList(listenerContainer.mIntentActionMap.keySet()).iterator();
                while (it.hasNext()) {
                    unregisterReceiver(i, (String) it.next());
                }
                Iterator it2 = new ArrayList(listenerContainer.mUriEventMap.keySet()).iterator();
                while (it2.hasNext()) {
                    unregisterContentObserver(i, (Uri) it2.next());
                }
                synchronized (this.mLock) {
                    this.mListenerContainers.remove(i);
                }
                synchronized (this.mCallbackLock) {
                    this.mSystemCallbacks.remove(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void clearPendingIntentAsUser(String str, int i) {
        enforceCallingPermission("clearPendingIntentAsUser");
        String str2 = "Clear pending intents for package = " + str + ", userId = " + i;
        Log.d("SemDeviceInfoManagerService", str2);
        Log.addLogString("SemDeviceInfoManagerService", str2);
        clearPendingIntentAsUserInternal(i, str);
    }

    public final void clearPendingIntentAsUserInternal(int i, String str) {
        synchronized (this.mLock) {
            try {
                ListenerContainer listenerContainer = getListenerContainer(i);
                ArrayList arrayList = new ArrayList(listenerContainer.mUriEventMap.keySet());
                ArrayList arrayList2 = new ArrayList(listenerContainer.mIntentActionMap.keySet());
                ArrayList arrayList3 = new ArrayList(listenerContainer.mCustomEventMap.keySet());
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Uri uri = (Uri) it.next();
                    UnionEventListenerItem unionEventListenerItem =
                            (UnionEventListenerItem) listenerContainer.mUriEventMap.get(uri);
                    if (((ArrayList)
                                            unionEventListenerItem
                                                    .mUnionEventComponentsWithConditions.remove(
                                                    str))
                                    != null
                            && unionEventListenerItem.mUnionEventComponentsWithConditions
                                    .isEmpty()) {
                        listenerContainer.mUriEventMap.remove(uri);
                        unregisterContentObserver(i, uri);
                    }
                }
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    String str2 = (String) it2.next();
                    UnionEventListenerItem unionEventListenerItem2 =
                            (UnionEventListenerItem) listenerContainer.mIntentActionMap.get(str2);
                    if (((ArrayList)
                                            unionEventListenerItem2
                                                    .mUnionEventComponentsWithConditions.remove(
                                                    str))
                                    != null
                            && unionEventListenerItem2.mUnionEventComponentsWithConditions
                                    .isEmpty()) {
                        listenerContainer.mIntentActionMap.remove(str2);
                        unregisterReceiver(i, str2);
                    }
                }
                Iterator it3 = arrayList3.iterator();
                while (it3.hasNext()) {
                    String str3 = (String) it3.next();
                    UnionEventListenerItem unionEventListenerItem3 =
                            (UnionEventListenerItem) listenerContainer.mCustomEventMap.get(str3);
                    if (((ArrayList)
                                            unionEventListenerItem3
                                                    .mUnionEventComponentsWithConditions.remove(
                                                    str))
                                    != null
                            && unionEventListenerItem3.mUnionEventComponentsWithConditions
                                    .isEmpty()) {
                        listenerContainer.mCustomEventMap.remove(str3);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (this.mLock) {
            try {
                printWriter.println(
                        "\n"
                            + "##### SEM DEVICE INFO MANAGER SERVICE #####\n"
                            + "##### (dumpsys sepunion semeventdelegator) #####\n");
                int size = this.mListenerContainers.size();
                printWriter.println("Listener containers(Users: " + size + "):");
                for (int i = 0; i < size; i++) {
                    int keyAt = this.mListenerContainers.keyAt(i);
                    printWriter.println("  Listener container for User " + keyAt + ":");
                    ListenerContainer listenerContainer =
                            (ListenerContainer) this.mListenerContainers.get(keyAt);
                    if (listenerContainer != null) {
                        Set<String> keySet = listenerContainer.mCustomEventMap.keySet();
                        if (keySet != null && keySet.size() > 0) {
                            printWriter.println(
                                    "    Custom Event Map(registered event: "
                                            + keySet.size()
                                            + "):");
                            for (String str : keySet) {
                                printWriter.println("      Event: " + str + ":");
                                UnionEventListenerItem unionEventListenerItem =
                                        (UnionEventListenerItem)
                                                listenerContainer.mCustomEventMap.get(str);
                                if (unionEventListenerItem != null) {
                                    printWriter.println(unionEventListenerItem.toStringForDump());
                                }
                            }
                            printWriter.println("");
                        }
                        Set<String> keySet2 = listenerContainer.mIntentActionMap.keySet();
                        if (keySet2 != null && keySet2.size() > 0) {
                            printWriter.println(
                                    "    Intent Action Map(registered action: "
                                            + keySet2.size()
                                            + "):");
                            for (String str2 : keySet2) {
                                printWriter.println("      Action: " + str2 + ":");
                                UnionEventListenerItem unionEventListenerItem2 =
                                        (UnionEventListenerItem)
                                                listenerContainer.mIntentActionMap.get(str2);
                                if (unionEventListenerItem2 != null) {
                                    printWriter.println(unionEventListenerItem2.toStringForDump());
                                }
                            }
                            printWriter.println("");
                        }
                        Set<Uri> keySet3 = listenerContainer.mUriEventMap.keySet();
                        if (keySet3 != null && keySet3.size() > 0) {
                            printWriter.println(
                                    "    URI Event Map(registered uri: " + keySet3.size() + "):");
                            for (Uri uri : keySet3) {
                                printWriter.println("      Uri: " + uri + ":");
                                UnionEventListenerItem unionEventListenerItem3 =
                                        (UnionEventListenerItem)
                                                listenerContainer.mUriEventMap.get(uri);
                                if (unionEventListenerItem3 != null) {
                                    printWriter.println(unionEventListenerItem3.toStringForDump());
                                }
                            }
                        }
                        printWriter.println();
                    }
                }
                Log.dump("SemDeviceInfoManagerService", fileDescriptor, printWriter, strArr);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void enforceCallingPermission(String str) {
        int callingUid = Binder.getCallingUid();
        if (this.mContext.getPackageManager().checkSignatures(1000, callingUid) == 0) {
            return;
        }
        String m =
                AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                        callingUid,
                        "Permission denied: ",
                        str,
                        " callingUid=",
                        ", you need system uid of to be signed with the system certificate.");
        Log.d("SemDeviceInfoManagerService", m);
        throw new SecurityException(m);
    }

    public EventProcessHandler getEventProcessHandler() {
        return this.mHandler;
    }

    public final ListenerContainer getListenerContainer(int i) {
        ListenerContainer listenerContainer;
        synchronized (this.mLock) {
            try {
                listenerContainer = (ListenerContainer) this.mListenerContainers.get(i);
                if (listenerContainer == null) {
                    listenerContainer = new ListenerContainer(i);
                    this.mListenerContainers.put(i, listenerContainer);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return listenerContainer;
    }

    public final int getNumPendingIntentAsUser(int i, String str, int i2) {
        int i3;
        int i4;
        int i5;
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        enforceCallingPermission("getNumPendingIntentAsUser");
        synchronized (this.mLock) {
            try {
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
                                (UnionEventListenerItem)
                                        listenerContainer.mCustomEventMap.get((String) it.next());
                        if (unionEventListenerItem != null
                                && (arrayList3 =
                                                (ArrayList)
                                                        unionEventListenerItem
                                                                .mUnionEventComponentsWithConditions
                                                                .get(str))
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
                                (UnionEventListenerItem)
                                        listenerContainer.mIntentActionMap.get((String) it2.next());
                        if (unionEventListenerItem2 != null
                                && (arrayList2 =
                                                (ArrayList)
                                                        unionEventListenerItem2
                                                                .mUnionEventComponentsWithConditions
                                                                .get(str))
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
                                (UnionEventListenerItem)
                                        listenerContainer.mUriEventMap.get((Uri) it3.next());
                        if (unionEventListenerItem3 != null
                                && (arrayList =
                                                (ArrayList)
                                                        unionEventListenerItem3
                                                                .mUnionEventComponentsWithConditions
                                                                .get(str))
                                        != null) {
                            i3 += arrayList.size();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
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
        Log.d(
                "SemDeviceInfoManagerService",
                "getNumPendingIntent(): Invalid type request. Requested type = " + i);
        return -1;
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    public final SystemCallbacks getSystemCallbacks(int i) {
        SystemCallbacks systemCallbacks;
        synchronized (this.mCallbackLock) {
            try {
                systemCallbacks = (SystemCallbacks) this.mSystemCallbacks.get(i);
                if (systemCallbacks == null) {
                    systemCallbacks = new SystemCallbacks();
                    this.mSystemCallbacks.put(i, systemCallbacks);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return systemCallbacks;
    }

    public final ListenerContainer getSystemListenerContainer() {
        return getListenerContainer(ActivityManager.getCurrentUser());
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {}

    public final void onCleanupUser(int i) {
        synchronized (this.mLock) {
        }
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {}

    public final void onDestroy() {}

    public final void onStart() {}

    public final void onStartUser(int i) {}

    public final void onStopUser(int i) {}

    public final void onSwitchUser(int i) {}

    public final void onUnlockUser(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserStarting(int i) {
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
                    public final void onReceive(Context context, Intent intent) {
                        int intExtra =
                                intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        Uri data = intent.getData();
                        String schemeSpecificPart =
                                data != null ? data.getSchemeSpecificPart() : null;
                        if (intExtra < 0 || schemeSpecificPart == null) {
                            int i2 = SemDeviceInfoManagerService.$r8$clinit;
                            Log.w(
                                    "SemDeviceInfoManagerService",
                                    "Intent contains invalid userId or package name");
                            return;
                        }
                        int i3 = SemDeviceInfoManagerService.$r8$clinit;
                        Log.d(
                                "SemDeviceInfoManagerService",
                                "Package "
                                        + schemeSpecificPart
                                        + " is removed on User "
                                        + intExtra);
                        SemDeviceInfoManagerService.this.clearPendingIntentAsUserInternal(
                                intExtra, schemeSpecificPart);
                    }
                },
                UserHandle.ALL,
                intentFilter,
                null,
                this.mHandler,
                2);
        this.mIsWatchingPackageRemoved = true;
    }

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserStopped(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserStopping(int i) {
        clearEventListenersAsUser(i);
    }

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserSwitching(int i, int i2) {}

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserUnlocked(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserUnlocking(int i) {}

    public final void registerContentObserver(final boolean z, final Uri uri, final int i) {
        if (i < 0 || uri == null) {
            return;
        }
        synchronized (this.mCallbackLock) {
            try {
                SystemCallbacks systemCallbacks = getSystemCallbacks(i);
                if (systemCallbacks.mContentObservers.get(uri) == null) {
                    Log.d(
                            "SemDeviceInfoManagerService",
                            "Creating new UnionContentObserver for the uri:" + uri);
                    final UnionContentObserver unionContentObserver =
                            new UnionContentObserver(this.mHandler, this);
                    systemCallbacks.mContentObservers.put(uri, unionContentObserver);
                    this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.sepunion.SemDeviceInfoManagerService$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SemDeviceInfoManagerService semDeviceInfoManagerService =
                                            SemDeviceInfoManagerService.this;
                                    Uri uri2 = uri;
                                    boolean z2 = z;
                                    ContentObserver contentObserver = unionContentObserver;
                                    int i2 = i;
                                    int i3 = SemDeviceInfoManagerService.$r8$clinit;
                                    semDeviceInfoManagerService.getClass();
                                    try {
                                        semDeviceInfoManagerService
                                                .mContext
                                                .getContentResolver()
                                                .registerContentObserver(
                                                        uri2, z2, contentObserver, i2);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerPendingIntent(
            IntentFilter intentFilter,
            PendingIntent pendingIntent,
            int i,
            List list,
            String str,
            int i2) {
        enforceCallingPermission("registerPendingIntent");
        registerPendingIntentInternal(intentFilter, pendingIntent, i, list, str, i2);
    }

    public final void registerPendingIntentForCustomEventAsUser(
            String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) {
        if (str == null || pendingIntent == null) {
            return;
        }
        if (bundle == null
                && (str.equals("monitor_activity_state") || str.equals("monitor_package_state"))) {
            return;
        }
        enforceCallingPermission("registerPendingIntentForCustomEventAsUser");
        String str3 = "Register custom event = " + str + ", package = " + str2 + ", userId = " + i;
        Log.d("SemDeviceInfoManagerService", str3);
        Log.addLogString("SemDeviceInfoManagerService", str3);
        ArrayList customEventKeys = getCustomEventKeys(bundle, str);
        synchronized (this.mLock) {
            try {
                ListenerContainer listenerContainer = getListenerContainer(i);
                Iterator it = customEventKeys.iterator();
                while (it.hasNext()) {
                    String str4 = (String) it.next();
                    if (listenerContainer.mCustomEventMap.containsKey(str4)) {
                        UnionEventListenerItem unionEventListenerItem =
                                (UnionEventListenerItem) listenerContainer.mCustomEventMap.get(str);
                        if (unionEventListenerItem != null) {
                            unionEventListenerItem.add(
                                    str2, new PendingIntentWithConditions(pendingIntent, 0, null));
                        }
                    } else {
                        Iterator it2 = listenerContainer.mCustomEventMap.keySet().iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                int customEventMessageId = getCustomEventMessageId(str, true);
                                EventProcessHandler eventProcessHandler = this.mHandler;
                                Message obtainMessage =
                                        eventProcessHandler.obtainMessage(customEventMessageId);
                                obtainMessage.setData(bundle);
                                eventProcessHandler.sendMessage(obtainMessage);
                                break;
                            }
                            if (((String) it2.next()).startsWith(str)) {
                                break;
                            }
                        }
                        UnionEventListenerItem unionEventListenerItem2 =
                                new UnionEventListenerItem();
                        unionEventListenerItem2.add(
                                str2, new PendingIntentWithConditions(pendingIntent, 0, null));
                        listenerContainer.mCustomEventMap.put(str4, unionEventListenerItem2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerPendingIntentForIntentAsUser(
            IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) {
        enforceCallingPermission("registerPendingIntentForIntentAsUser");
        registerPendingIntentInternal(intentFilter, pendingIntent, 0, null, str, i);
    }

    public final void registerPendingIntentForIntentForAllUsers(
            IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) {
        enforceCallingPermission("registerPendingIntentForIntentForAllUsers");
        Log.e("SemDeviceInfoManagerService", "Do not support observing Intent for all users");
    }

    public final void registerPendingIntentForUriAsUser(
            Uri uri, PendingIntent pendingIntent, String str, int i) {
        if (uri == null || pendingIntent == null || str == null) {
            return;
        }
        enforceCallingPermission("registerPendingIntentForUriAsUser");
        String str2 = "Register uri = " + uri.toString() + ", package = " + str + ", userId = " + i;
        Log.d("SemDeviceInfoManagerService", str2);
        Log.addLogString("SemDeviceInfoManagerService", str2);
        synchronized (this.mLock) {
            try {
                ListenerContainer listenerContainer = getListenerContainer(i);
                if (listenerContainer.mUriEventMap.containsKey(uri)) {
                    UnionEventListenerItem unionEventListenerItem =
                            (UnionEventListenerItem) listenerContainer.mUriEventMap.get(uri);
                    unionEventListenerItem.getClass();
                    unionEventListenerItem.add(
                            str, new PendingIntentWithConditions(pendingIntent, 0, null));
                } else {
                    UnionEventListenerItem unionEventListenerItem2 = new UnionEventListenerItem();
                    unionEventListenerItem2.add(
                            str, new PendingIntentWithConditions(pendingIntent, 0, null));
                    listenerContainer.mUriEventMap.put(uri, unionEventListenerItem2);
                    registerContentObserver(
                            pendingIntent
                                    .getIntent()
                                    .getBooleanExtra("notify_for_descendants", false),
                            uri,
                            i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
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
            StringBuilder sb2 = new StringBuilder("Register intent = ");
            sb2.append(sb.toString());
            sb2.append(" package = ");
            sb2.append(str);
            sb2.append(", userId = ");
            ServiceKeeper$$ExternalSyntheticOutline0.m(i2, i, ", flag = ", ", conditions = ", sb2);
            sb2.append(list);
            String sb3 = sb2.toString();
            Log.d("SemDeviceInfoManagerService", sb3);
            Log.addLogString("SemDeviceInfoManagerService", sb3);
            PendingIntentWithConditions pendingIntentWithConditions =
                    new PendingIntentWithConditions(
                            pendingIntent, i, i == 0 ? null : new ArrayList(list));
            for (int i4 = 0; i4 < countActions; i4++) {
                synchronized (this.mLock) {
                    try {
                        ListenerContainer listenerContainer = getListenerContainer(i2);
                        String action = intentFilter.getAction(i4);
                        if (!listenerContainer.mIntentActionMap.containsKey(action)) {
                            UnionEventListenerItem unionEventListenerItem =
                                    new UnionEventListenerItem();
                            unionEventListenerItem.add(str, pendingIntentWithConditions);
                            listenerContainer.mIntentActionMap.put(action, unionEventListenerItem);
                            registerReceiver(i2, action);
                        } else {
                            ((UnionEventListenerItem)
                                            listenerContainer.mIntentActionMap.get(action))
                                    .add(str, pendingIntentWithConditions);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    public final void registerReceiver(final int i, final String str) {
        if (i < 0 || TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.mCallbackLock) {
            try {
                SystemCallbacks systemCallbacks = getSystemCallbacks(i);
                if (systemCallbacks.mBroadcastReceivers.get(str) == null) {
                    Log.d(
                            "SemDeviceInfoManagerService",
                            "Adding new UnionBroadcastReceiver for the action: "
                                    + str
                                    + " in User "
                                    + i);
                    final UnionContentBroadcastReceiver unionContentBroadcastReceiver =
                            new UnionContentBroadcastReceiver(this);
                    systemCallbacks.mBroadcastReceivers.put(str, unionContentBroadcastReceiver);
                    this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.sepunion.SemDeviceInfoManagerService$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SemDeviceInfoManagerService semDeviceInfoManagerService =
                                            SemDeviceInfoManagerService.this;
                                    String str2 = str;
                                    BroadcastReceiver broadcastReceiver =
                                            unionContentBroadcastReceiver;
                                    int i2 = i;
                                    int i3 = SemDeviceInfoManagerService.$r8$clinit;
                                    semDeviceInfoManagerService.getClass();
                                    IntentFilter intentFilter = new IntentFilter(str2);
                                    if (str2.equals("android.intent.action.PACKAGE_DATA_CLEARED")
                                            || str2.equals("android.intent.action.PACKAGE_ADDED")
                                            || str2.equals("android.intent.action.PACKAGE_CHANGED")
                                            || str2.equals("android.intent.action.PACKAGE_REPLACED")
                                            || str2.equals(
                                                    "android.intent.action.PACKAGE_FULLY_REMOVED")
                                            || str2.equals(
                                                    "android.intent.action.PACKAGE_REMOVED")) {
                                        intentFilter.addDataScheme("package");
                                    } else if (str2.equals(
                                            "android.intent.action.PROVIDER_CHANGED")) {
                                        intentFilter.addDataScheme("content");
                                    }
                                    try {
                                        semDeviceInfoManagerService.mContext.registerReceiverAsUser(
                                                broadcastReceiver,
                                                UserHandle.of(i2),
                                                intentFilter,
                                                null,
                                                semDeviceInfoManagerService.mHandler,
                                                2);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reportCallStateChanged(
            UnionEventListenerItem unionEventListenerItem, int i, String str) {
        if (unionEventListenerItem == null) {
            return;
        }
        synchronized (this.mLock) {
            Iterator it =
                    unionEventListenerItem.mUnionEventComponentsWithConditions.keySet().iterator();
            while (it.hasNext()) {
                Iterator it2 =
                        ((ArrayList)
                                        unionEventListenerItem.mUnionEventComponentsWithConditions
                                                .get((String) it.next()))
                                .iterator();
                while (it2.hasNext()) {
                    PendingIntent pendingIntent =
                            ((PendingIntentWithConditions) it2.next()).mPendingIntent;
                    Intent intent = pendingIntent.getIntent();
                    intent.putExtra("call_state", i);
                    intent.putExtra("phone_number", str);
                    try {
                        pendingIntent.send(this.mContext, 0, intent, null, null);
                        String str2 = "Report call state changed, state = " + i;
                        Log.d("SemDeviceInfoManagerService", str2);
                        Log.addLogString("SemDeviceInfoManagerService", str2);
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public final void reportComponentStateChanged(
            UnionEventListenerItem unionEventListenerItem, ComponentName componentName, boolean z) {
        if (unionEventListenerItem == null) {
            return;
        }
        synchronized (this.mLock) {
            Iterator it =
                    unionEventListenerItem.mUnionEventComponentsWithConditions.keySet().iterator();
            while (it.hasNext()) {
                Iterator it2 =
                        ((ArrayList)
                                        unionEventListenerItem.mUnionEventComponentsWithConditions
                                                .get((String) it.next()))
                                .iterator();
                while (it2.hasNext()) {
                    PendingIntent pendingIntent =
                            ((PendingIntentWithConditions) it2.next()).mPendingIntent;
                    Intent intent = pendingIntent.getIntent();
                    intent.putExtra("component", componentName);
                    intent.putExtra("is_resumed", z);
                    try {
                        pendingIntent.send(this.mContext, 0, intent, null, null);
                        String str =
                                "Report component state changed, componentName = " + componentName;
                        Log.d("SemDeviceInfoManagerService", str);
                        Log.addLogString("SemDeviceInfoManagerService", str);
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public final void reportPackageStateChanged(
            UnionEventListenerItem unionEventListenerItem, String str, String str2) {
        if (unionEventListenerItem == null) {
            return;
        }
        synchronized (this.mLock) {
            Iterator it =
                    unionEventListenerItem.mUnionEventComponentsWithConditions.keySet().iterator();
            while (it.hasNext()) {
                Iterator it2 =
                        ((ArrayList)
                                        unionEventListenerItem.mUnionEventComponentsWithConditions
                                                .get((String) it.next()))
                                .iterator();
                while (it2.hasNext()) {
                    PendingIntent pendingIntent =
                            ((PendingIntentWithConditions) it2.next()).mPendingIntent;
                    Intent intent = pendingIntent.getIntent();
                    intent.putExtra("package_name", str);
                    intent.putExtra("package_state", str2);
                    try {
                        pendingIntent.send(this.mContext, 0, intent, null, null);
                        String str3 = "Report package changed for " + str;
                        Log.d("SemDeviceInfoManagerService", str3);
                        Log.addLogString("SemDeviceInfoManagerService", str3);
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public final void reportUriChanged(
            UnionEventListenerItem unionEventListenerItem, boolean z, Uri uri, int i, boolean z2) {
        if (unionEventListenerItem == null) {
            return;
        }
        synchronized (this.mLock) {
            Iterator it =
                    unionEventListenerItem.mUnionEventComponentsWithConditions.keySet().iterator();
            while (it.hasNext()) {
                Iterator it2 =
                        ((ArrayList)
                                        unionEventListenerItem.mUnionEventComponentsWithConditions
                                                .get((String) it.next()))
                                .iterator();
                while (it2.hasNext()) {
                    PendingIntent pendingIntent =
                            ((PendingIntentWithConditions) it2.next()).mPendingIntent;
                    Intent intent = pendingIntent.getIntent();
                    intent.putExtra(SystemIntentProcessor.KEY_URI, uri);
                    boolean booleanExtra =
                            z2 ? intent.getBooleanExtra("notify_for_descendants", false) : false;
                    if (booleanExtra || !z2) {
                        if (booleanExtra) {
                            try {
                                Log.d(
                                        "SemDeviceInfoManagerService",
                                        "Notifying descendants changes.");
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
                        }
                        pendingIntent.send(this.mContext, 0, intent, null, null);
                        String str =
                                "Send intent: "
                                        + intent.getAction()
                                        + ", userId: "
                                        + i
                                        + " by uri change";
                        Log.d("SemDeviceInfoManagerService", str);
                        Log.addLogString("SemDeviceInfoManagerService", str);
                    }
                }
            }
        }
    }

    public final void sendIntentAction(
            UnionEventListenerItem unionEventListenerItem, Intent intent, int i) {
        if (unionEventListenerItem == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                for (String str :
                        unionEventListenerItem.mUnionEventComponentsWithConditions.keySet()) {
                    if (intent.getAction().equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
                        String uri = intent.getData().toString();
                        if (!uri.contains(str)) {
                            Log.d(
                                    "SemDeviceInfoManagerService",
                                    "Data cleared package = " + uri + ", key = " + str);
                        }
                    }
                    Iterator it =
                            ((ArrayList)
                                            unionEventListenerItem
                                                    .mUnionEventComponentsWithConditions.get(str))
                                    .iterator();
                    while (it.hasNext()) {
                        PendingIntentWithConditions pendingIntentWithConditions =
                                (PendingIntentWithConditions) it.next();
                        int i2 = pendingIntentWithConditions.mFlag;
                        boolean z = true;
                        if (i2 != 0) {
                            if (i2 == 1) {
                                ArrayList arrayList = pendingIntentWithConditions.mConditions;
                                String schemeSpecificPart =
                                        intent.getData().getSchemeSpecificPart();
                                if (arrayList != null && schemeSpecificPart != null) {
                                    z = arrayList.contains(schemeSpecificPart);
                                }
                                Log.d(
                                        "SemDeviceInfoManagerService",
                                        "containsPackageName() packageNameList = "
                                                + arrayList
                                                + ", packageName = "
                                                + schemeSpecificPart);
                                z = false;
                            } else {
                                if (i2 == 2) {
                                    ArrayList arrayList2 = pendingIntentWithConditions.mConditions;
                                    String schemeSpecificPart2 =
                                            intent.getData().getSchemeSpecificPart();
                                    if (arrayList2 != null && schemeSpecificPart2 != null) {
                                        PackageManager packageManager =
                                                this.mContext.getPackageManager();
                                        int size = arrayList2.size();
                                        for (int i3 = 0; i3 < size; i3++) {
                                            String str2 = (String) arrayList2.get(i3);
                                            if (str2 != null
                                                    && packageManager.checkPermission(
                                                                    str2, schemeSpecificPart2)
                                                            == 0) {
                                                break;
                                            }
                                        }
                                    }
                                }
                                z = false;
                            }
                        }
                        if (z) {
                            PendingIntent pendingIntent =
                                    pendingIntentWithConditions.mPendingIntent;
                            try {
                                Intent intent2 = new Intent();
                                intent2.setAction(pendingIntent.getIntent().getAction());
                                intent2.setData(intent.getData());
                                intent2.putExtras(intent);
                                intent2.putExtra("action_origin", intent.getAction());
                                pendingIntent.send(this.mContext, 0, intent2, null, null);
                                String str3 =
                                        "Send intent "
                                                + intent2.getAction()
                                                + ", userId: "
                                                + i
                                                + " by broadcast";
                                Log.d("SemDeviceInfoManagerService", str3);
                                Log.addLogString("SemDeviceInfoManagerService", str3);
                            } catch (PendingIntent.CanceledException | NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterContentObserver(int i, Uri uri) {
        if (i < 0 || uri == null) {
            return;
        }
        synchronized (this.mCallbackLock) {
            try {
                ContentObserver contentObserver =
                        (ContentObserver) getSystemCallbacks(i).mContentObservers.remove(uri);
                if (contentObserver != null) {
                    this.mHandler.post(
                            new SemDeviceInfoManagerService$$ExternalSyntheticLambda1(
                                    this, contentObserver, 0));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterPendingIntent(
            IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) {
        enforceCallingPermission("unregisterPendingIntent");
        unregisterPendingIntentInternal(intentFilter, pendingIntent, str, i);
    }

    public final void unregisterPendingIntentForCustomEventAsUser(
            String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) {
        if (str == null || pendingIntent == null) {
            return;
        }
        if (bundle == null
                && (str.equals("monitor_activity_state") || str.equals("monitor_package_state"))) {
            return;
        }
        enforceCallingPermission("unregisterPendingIntentForCustomEventAsUser");
        String str3 =
                "Unregister custom event = " + str + ", package = " + str2 + ", userId = " + i;
        Log.d("SemDeviceInfoManagerService", str3);
        Log.addLogString("SemDeviceInfoManagerService", str3);
        ArrayList customEventKeys = getCustomEventKeys(bundle, str);
        synchronized (this.mLock) {
            try {
                ListenerContainer listenerContainer = getListenerContainer(i);
                Iterator it = customEventKeys.iterator();
                while (it.hasNext()) {
                    String str4 = (String) it.next();
                    UnionEventListenerItem unionEventListenerItem =
                            (UnionEventListenerItem) listenerContainer.mCustomEventMap.get(str4);
                    if (unionEventListenerItem != null) {
                        unionEventListenerItem.remove(str2, pendingIntent);
                        if (unionEventListenerItem.mUnionEventComponentsWithConditions.isEmpty()) {
                            listenerContainer.mCustomEventMap.remove(str4);
                            int customEventMessageId = getCustomEventMessageId(str, false);
                            EventProcessHandler eventProcessHandler = this.mHandler;
                            Message obtainMessage =
                                    eventProcessHandler.obtainMessage(customEventMessageId);
                            obtainMessage.setData(bundle);
                            eventProcessHandler.sendMessage(obtainMessage);
                        } else {
                            listenerContainer.mCustomEventMap.put(str4, unionEventListenerItem);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterPendingIntentForIntentAsUser(
            IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) {
        enforceCallingPermission("unregisterPendingIntentForIntentAsUser");
        unregisterPendingIntentInternal(intentFilter, pendingIntent, str, i);
    }

    public final void unregisterPendingIntentForUriAsUser(
            Uri uri, PendingIntent pendingIntent, String str, int i) {
        if (uri == null || pendingIntent == null || str == null) {
            return;
        }
        enforceCallingPermission("unregisterPendingIntentForUriAsUser");
        String str2 = "Unregister uri = " + uri + ", package = " + str + ", userId = " + i;
        Log.d("SemDeviceInfoManagerService", str2);
        Log.addLogString("SemDeviceInfoManagerService", str2);
        synchronized (this.mLock) {
            try {
                ListenerContainer listenerContainer = getListenerContainer(i);
                UnionEventListenerItem unionEventListenerItem =
                        (UnionEventListenerItem) listenerContainer.mUriEventMap.get(uri);
                if (unionEventListenerItem != null) {
                    unionEventListenerItem.remove(str, pendingIntent);
                    if (unionEventListenerItem.mUnionEventComponentsWithConditions.isEmpty()) {
                        listenerContainer.mUriEventMap.remove(uri);
                        unregisterContentObserver(i, uri);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
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
        String str2 =
                "Unregister intent = " + sb.toString() + ", package = " + str + ", userId = " + i;
        Log.d("SemDeviceInfoManagerService", str2);
        Log.addLogString("SemDeviceInfoManagerService", str2);
        for (int i3 = 0; i3 < intentFilter.countActions(); i3++) {
            String action = intentFilter.getAction(i3);
            synchronized (this.mLock) {
                try {
                    ListenerContainer listenerContainer = getListenerContainer(i);
                    UnionEventListenerItem unionEventListenerItem =
                            (UnionEventListenerItem) listenerContainer.mIntentActionMap.get(action);
                    if (unionEventListenerItem != null) {
                        unionEventListenerItem.remove(str, pendingIntent);
                        if (unionEventListenerItem.mUnionEventComponentsWithConditions.isEmpty()) {
                            listenerContainer.mIntentActionMap.remove(action);
                            unregisterReceiver(i, action);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void unregisterReceiver(int i, String str) {
        if (i < 0 || TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.mCallbackLock) {
            try {
                BroadcastReceiver broadcastReceiver =
                        (BroadcastReceiver) getSystemCallbacks(i).mBroadcastReceivers.remove(str);
                if (broadcastReceiver != null) {
                    this.mHandler.post(
                            new SemDeviceInfoManagerService$$ExternalSyntheticLambda1(
                                    this, broadcastReceiver, 1));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
