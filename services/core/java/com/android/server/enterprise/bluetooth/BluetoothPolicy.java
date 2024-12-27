package com.android.server.enterprise.bluetooth;

import android.R;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.Utils;
import com.android.server.location.gnss.hal.GnssNative;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ControlInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.bluetooth.BluetoothControlInfo;
import com.samsung.android.knox.bluetooth.IBluetoothPolicy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BluetoothPolicy extends IBluetoothPolicy.Stub implements EnterpriseServiceCallback {
    public final Context mContext;
    public final BluetoothDevicePolicy mDevicePolicy;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final EnterpriseDumpHelper mEnterpriseDumpHelper;
    public final BlockingQueue mLogQueue;
    public final Map mProfileMap;
    public final BluetoothDevicePolicy mProfilePolicy;
    public final AnonymousClass1 mReceiver;
    public boolean mRestart;
    public boolean mCacheIsBluetoothLogEnabled = false;
    public boolean isCacheUpdated = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.bluetooth.BluetoothPolicy$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice;
            BluetoothClass bluetoothClass;
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    Log.d("BluetoothPolicyService", "onReceive " + action);
                    boolean z = false;
                    if (!action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                        if (!"com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(action)) {
                            if ("android.bluetooth.device.action.ACL_CONNECTED".equals(action) && !((BluetoothPolicy) this.this$0).isDesktopConnectivityEnabled(false) && (bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")) != null && (bluetoothClass = bluetoothDevice.getBluetoothClass()) != null && bluetoothClass.getMajorDeviceClass() == 256) {
                                Log.d("BluetoothPolicyService", "Unpair this bluetooth computer(ACL CONNECTED) : " + bluetoothDevice.getAddress());
                                bluetoothDevice.removeBond();
                                break;
                            }
                        } else {
                            int intExtra = intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0);
                            BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) this.this$0;
                            if (bluetoothPolicy.isBluetoothEnabled(false)) {
                                if (!(bluetoothPolicy.getEDM$2() != null ? bluetoothPolicy.getEDM$2().isRestrictedByConstrainedState(8) : false)) {
                                    z = true;
                                }
                            }
                            bluetoothPolicy.setBluetoothAllowedSystemUI(intExtra, z);
                            break;
                        }
                    } else if (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE) == 10 && ((BluetoothPolicy) this.this$0).mRestart) {
                        Log.d("BluetoothPolicyService", "***** Restarting Bluetooth *****");
                        ((BluetoothPolicy) this.this$0).mRestart = false;
                        if (BluetoothAdapter.getDefaultAdapter() != null) {
                            BluetoothAdapter.getDefaultAdapter().enable();
                            break;
                        }
                    }
                    break;
                default:
                    if (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE) == 12) {
                        ((ConditionVariable) this.this$0).open();
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.bluetooth.BluetoothPolicy$2, reason: invalid class name */
    public final class AnonymousClass2 extends Thread {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BluetoothPolicy this$0;

        public /* synthetic */ AnonymousClass2(BluetoothPolicy bluetoothPolicy, int i) {
            this.$r8$classId = i;
            this.this$0 = bluetoothPolicy;
        }

        /* JADX INFO: Infinite loop detected, blocks: 17, insns: 0 */
        /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            BluetoothAdapter defaultAdapter;
            switch (this.$r8$classId) {
                case 0:
                    ConditionVariable conditionVariable = new ConditionVariable();
                    IntentFilter intentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
                    defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                    this.this$0.mContext.registerReceiver(new AnonymousClass1(1, conditionVariable), intentFilter);
                    conditionVariable.block(10000L);
                    break;
                default:
                    while (true) {
                        try {
                            String str = (String) ((LinkedBlockingQueue) this.this$0.mLogQueue).take();
                            if (str != null) {
                                long timeInMillis = Calendar.getInstance().getTimeInMillis();
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("time", String.valueOf(timeInMillis));
                                contentValues.put("log", str);
                                Log.d("BluetoothPolicyService", "StoreLogThread - cv: " + contentValues);
                                if (!this.this$0.mEdmStorageProvider.putValuesNoUpdate("BluetoothLogTable", contentValues)) {
                                    Log.d("BluetoothPolicyService", "StoreLogThread - Failed on inserting log");
                                }
                            }
                        } catch (InterruptedException unused) {
                            Log.d("BluetoothPolicyService", "StoreLogThread - InterruptedException on take");
                        }
                    }
            }
            if (defaultAdapter != null) {
                defaultAdapter.disable();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BluetoothDevicePolicy {
        public final /* synthetic */ int $r8$classId;
        public boolean bUpdateToDB;
        public final EdmStorageProvider mEdmStorageProvider;
        public final List mEffectiveBlacklist;
        public final List mEffectiveWhitelist;
        public final String mTable;
        public final /* synthetic */ BluetoothPolicy this$0;

        public BluetoothDevicePolicy(Context context, String str) {
            this.mEffectiveBlacklist = new ArrayList();
            this.mEffectiveWhitelist = new ArrayList();
            this.bUpdateToDB = false;
            this.mEdmStorageProvider = new EdmStorageProvider(context);
            this.mTable = str;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public BluetoothDevicePolicy(BluetoothPolicy bluetoothPolicy, Context context, int i) {
            this(context, "BLUETOOTH_DEVICE_BWLIST");
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = bluetoothPolicy;
                    this(context, "BLUETOOTH_PROFILE_BWLIST");
                    break;
                default:
                    this.this$0 = bluetoothPolicy;
                    break;
            }
        }

        public static void addList(List list, List list2) {
            for (int i = 0; i < list2.size(); i++) {
                if (((String) list2.get(i)).equalsIgnoreCase("*")) {
                    list.clear();
                    list.add("*");
                    return;
                }
                list.add((String) list2.get(i));
            }
            removeDuplicates(list);
        }

        public static void logList(String str, List list) {
            for (int i = 0; i < list.size(); i++) {
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("logList:", str, " ");
                m.append((String) list.get(i));
                Log.i("BlackWhiteListPolicyService", m.toString());
            }
        }

        public static void removeDuplicates(List list) {
            if (list.size() == 0) {
                return;
            }
            HashSet hashSet = new HashSet();
            hashSet.addAll(list);
            list.clear();
            list.addAll(hashSet);
        }

        public static void subtractList(List list, List list2) {
            for (int i = 0; i < list2.size(); i++) {
                if (((String) list2.get(i)).equalsIgnoreCase("*")) {
                    list.clear();
                    return;
                }
                list.remove(list2.get(i));
            }
        }

        public final boolean addObjectsToList(int i, String str, List list) {
            removeDuplicates(list);
            for (int i2 = 0; i2 < list.size(); i2++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("Object", (String) list.get(i2));
                contentValues.put("adminUid", String.valueOf(i));
                contentValues.put("ListType", str);
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("Object", (String) list.get(i2));
                contentValues2.put("adminUid", String.valueOf(i));
                contentValues2.put("ListType", str);
                if (!this.mEdmStorageProvider.putValues(this.mTable, contentValues2, contentValues)) {
                    return false;
                }
            }
            updateEffectivePolicy();
            return true;
        }

        public final boolean clearObjectsFromList(int i, String str) {
            HashMap hashMap = new HashMap();
            hashMap.put("adminUid", String.valueOf(i));
            hashMap.put("ListType", str);
            if (this.mEdmStorageProvider.removeByFieldsAsUser(0, this.mTable, hashMap) == -1) {
                return false;
            }
            updateEffectivePolicy();
            return true;
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x006a, code lost:
        
            android.util.Log.i("BlackWhiteListPolicyService", "getAllObjectsFromList:");
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0070, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0067, code lost:
        
            if (r3 == null) goto L19;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void getAllObjectsFromList(int r10, java.lang.String r11, java.util.List r12) {
            /*
                r9 = this;
                java.lang.String r0 = "BlackWhiteListPolicyService"
                java.lang.String r1 = "Object"
                java.lang.String[] r2 = new java.lang.String[]{r1}
                r3 = 0
                com.android.server.enterprise.storage.EdmStorageProvider r4 = r9.mEdmStorageProvider     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                java.lang.String r9 = r9.mTable     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                java.lang.String r5 = "ListType"
                r4.getClass()     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                android.content.ContentValues r6 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                r6.<init>()     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                r7 = 0
                long r7 = com.android.server.enterprise.storage.EdmStorageProviderBase.translateToAdminLUID(r10, r7)     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                java.lang.Long r10 = java.lang.Long.valueOf(r7)     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                java.lang.String r7 = "adminUid"
                r6.put(r7, r10)     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                r6.put(r5, r11)     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                android.database.Cursor r3 = r4.getCursor(r9, r2, r6)     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                if (r3 == 0) goto L49
                java.util.ArrayList r12 = (java.util.ArrayList) r12     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                r12.clear()     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
            L33:
                boolean r9 = r3.moveToNext()     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                if (r9 == 0) goto L49
                int r9 = r3.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                java.lang.String r9 = r3.getString(r9)     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                r12.add(r9)     // Catch: java.lang.Throwable -> L45 android.database.SQLException -> L47
                goto L33
            L45:
                r9 = move-exception
                goto L71
            L47:
                r9 = move-exception
                goto L4f
            L49:
                if (r3 == 0) goto L6a
            L4b:
                r3.close()
                goto L6a
            L4f:
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L45
                r10.<init>()     // Catch: java.lang.Throwable -> L45
                java.lang.String r11 = "Exception occurred accessing Enterprise db "
                r10.append(r11)     // Catch: java.lang.Throwable -> L45
                java.lang.String r9 = r9.getMessage()     // Catch: java.lang.Throwable -> L45
                r10.append(r9)     // Catch: java.lang.Throwable -> L45
                java.lang.String r9 = r10.toString()     // Catch: java.lang.Throwable -> L45
                android.util.Log.e(r0, r9)     // Catch: java.lang.Throwable -> L45
                if (r3 == 0) goto L6a
                goto L4b
            L6a:
                java.lang.String r9 = "getAllObjectsFromList:"
                android.util.Log.i(r0, r9)
                return
            L71:
                if (r3 == 0) goto L76
                r3.close()
            L76:
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.bluetooth.BluetoothPolicy.BluetoothDevicePolicy.getAllObjectsFromList(int, java.lang.String, java.util.List):void");
        }

        public final List getAllObjectsFromListForAllAdmins(String str) {
            ArrayList arrayList = new ArrayList();
            EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
            Iterator it = edmStorageProvider.getAdminUidList().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                ArrayList arrayList2 = new ArrayList();
                getAllObjectsFromList(intValue, str, arrayList2);
                if (!arrayList2.isEmpty()) {
                    ControlInfo controlInfo = new ControlInfo();
                    controlInfo.adminPackageName = edmStorageProvider.getPackageNameForUid(intValue);
                    controlInfo.entries = arrayList2;
                    arrayList.add(controlInfo);
                }
            }
            Log.i("BlackWhiteListPolicyService", "getAllObjectsFromLists:");
            return arrayList;
        }

        public final List getEffectiveBlackList() {
            if (!this.bUpdateToDB) {
                updateEffectivePolicy();
                this.bUpdateToDB = true;
            }
            return this.mEffectiveBlacklist;
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0039, code lost:
        
            return true;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean isObjectAllowed(java.lang.String r8) {
            /*
                r7 = this;
                boolean r0 = r7.bUpdateToDB
                r1 = 1
                if (r0 == 0) goto La
                r7.updateEffectivePolicy()
                r7.bUpdateToDB = r1
            La:
                java.util.List r0 = r7.mEffectiveWhitelist
                java.util.List r7 = r7.getEffectiveBlackList()
                r2 = 0
                r3 = r2
            L12:
                r4 = r0
                java.util.ArrayList r4 = (java.util.ArrayList) r4
                int r5 = r4.size()
                java.lang.String r6 = "*"
                if (r3 >= r5) goto L3a
                java.lang.Object r5 = r4.get(r3)
                java.lang.String r5 = (java.lang.String) r5
                boolean r5 = r5.equalsIgnoreCase(r8)
                if (r5 != 0) goto L39
                java.lang.Object r4 = r4.get(r3)
                java.lang.String r4 = (java.lang.String) r4
                boolean r4 = r4.equalsIgnoreCase(r6)
                if (r4 == 0) goto L36
                goto L39
            L36:
                int r3 = r3 + 1
                goto L12
            L39:
                return r1
            L3a:
                r0 = r2
            L3b:
                r3 = r7
                java.util.ArrayList r3 = (java.util.ArrayList) r3
                int r4 = r3.size()
                if (r0 >= r4) goto L61
                java.lang.Object r4 = r3.get(r0)
                java.lang.String r4 = (java.lang.String) r4
                boolean r4 = r4.equalsIgnoreCase(r8)
                if (r4 != 0) goto L60
                java.lang.Object r3 = r3.get(r0)
                java.lang.String r3 = (java.lang.String) r3
                boolean r3 = r3.equalsIgnoreCase(r6)
                if (r3 == 0) goto L5d
                goto L60
            L5d:
                int r0 = r0 + 1
                goto L3b
            L60:
                return r2
            L61:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.bluetooth.BluetoothPolicy.BluetoothDevicePolicy.isObjectAllowed(java.lang.String):boolean");
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:
        
            android.util.Log.d("BluetoothPolicyService", r3.toString());
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x002e, code lost:
        
            r3 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x002f, code lost:
        
            android.util.Log.d("BluetoothPolicyService", r3.toString());
         */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0014, code lost:
        
            r3 = move-exception;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean isPolicyActive(int r4) {
            /*
                r3 = this;
                int r0 = r3.$r8$classId
                switch(r0) {
                    case 0: goto L1f;
                    default: goto L5;
                }
            L5:
                r0 = 0
                com.android.server.enterprise.bluetooth.BluetoothPolicy r3 = r3.this$0     // Catch: java.lang.Exception -> L14
                com.android.server.enterprise.storage.EdmStorageProvider r3 = r3.mEdmStorageProvider     // Catch: java.lang.Exception -> L14
                java.lang.String r1 = "BLUETOOTH"
                java.lang.String r2 = "profilePolicyEnabled"
                boolean r0 = r3.getBoolean(r4, r0, r1, r2)     // Catch: java.lang.Exception -> L14
                goto L1e
            L14:
                r3 = move-exception
                java.lang.String r4 = "BluetoothPolicyService"
                java.lang.String r3 = r3.toString()
                android.util.Log.d(r4, r3)
            L1e:
                return r0
            L1f:
                r0 = 0
                com.android.server.enterprise.bluetooth.BluetoothPolicy r3 = r3.this$0     // Catch: java.lang.Exception -> L2e
                com.android.server.enterprise.storage.EdmStorageProvider r3 = r3.mEdmStorageProvider     // Catch: java.lang.Exception -> L2e
                java.lang.String r1 = "BLUETOOTH"
                java.lang.String r2 = "devicePolicyEnabled"
                boolean r0 = r3.getBoolean(r4, r0, r1, r2)     // Catch: java.lang.Exception -> L2e
                goto L38
            L2e:
                r3 = move-exception
                java.lang.String r4 = "BluetoothPolicyService"
                java.lang.String r3 = r3.toString()
                android.util.Log.d(r4, r3)
            L38:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.bluetooth.BluetoothPolicy.BluetoothDevicePolicy.isPolicyActive(int):boolean");
        }

        public final void reload() {
            this.bUpdateToDB = false;
            updateEffectivePolicy();
        }

        public final boolean removeObjectsFromList(int i, String str, List list) {
            removeDuplicates(list);
            for (int i2 = 0; i2 < list.size(); i2++) {
                HashMap hashMap = new HashMap();
                hashMap.put("adminUid", String.valueOf(i));
                hashMap.put("Object", (String) list.get(i2));
                hashMap.put("ListType", str);
                if (this.mEdmStorageProvider.removeByFieldsAsUser(0, this.mTable, hashMap) == -1) {
                    return false;
                }
            }
            updateEffectivePolicy();
            return true;
        }

        public final void updateEffectivePolicy() {
            List list = this.mEffectiveBlacklist;
            List list2 = this.mEffectiveWhitelist;
            ArrayList arrayList = (ArrayList) list;
            arrayList.clear();
            ArrayList arrayList2 = (ArrayList) list2;
            arrayList2.clear();
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            Iterator it = this.mEdmStorageProvider.getAdminUidList().iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                int intValue = num.intValue();
                if (isPolicyActive(intValue)) {
                    hashMap.put(num, new ArrayList());
                    hashMap2.put(num, new ArrayList());
                    List list3 = (List) hashMap.get(num);
                    List list4 = (List) hashMap2.get(num);
                    list3.clear();
                    list4.clear();
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    getAllObjectsFromList(intValue, "black", arrayList3);
                    addList(list3, arrayList3);
                    getAllObjectsFromList(intValue, "white", arrayList4);
                    addList(list4, arrayList4);
                    subtractList(list3, list4);
                    logList("effectiveblacklist for admin " + String.valueOf(intValue) + ": ", list3);
                    logList("effectivewhitelist for admin " + String.valueOf(intValue) + ": ", list4);
                    addList(arrayList, (List) hashMap.get(num));
                    logList("finalblacklist after adding admin " + String.valueOf(intValue) + ": ", arrayList);
                }
            }
            for (Map.Entry entry : hashMap2.entrySet()) {
                int intValue2 = ((Integer) entry.getKey()).intValue();
                List list5 = (List) entry.getValue();
                for (Map.Entry entry2 : hashMap.entrySet()) {
                    if (((Integer) entry2.getKey()).intValue() != intValue2) {
                        subtractList(list5, (List) entry2.getValue());
                    }
                }
                addList(arrayList2, (List) entry.getValue());
                logList("finalwhitelist after adding admin " + String.valueOf(entry.getKey()) + ": ", arrayList2);
            }
        }
    }

    public BluetoothPolicy(Context context) {
        this.mLogQueue = null;
        HashMap hashMap = new HashMap();
        this.mProfileMap = hashMap;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(0, this);
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.mRestart = false;
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        context.registerReceiver(anonymousClass1, intentFilter, 2);
        this.mLogQueue = new LinkedBlockingQueue();
        this.mProfilePolicy = new BluetoothDevicePolicy(this, context, 1);
        this.mDevicePolicy = new BluetoothDevicePolicy(this, context, 0);
        this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(context);
        hashMap.put(1, new ArrayList());
        hashMap.put(2, new ArrayList());
        hashMap.put(16, new ArrayList());
        hashMap.put(4, new ArrayList());
        hashMap.put(8, new ArrayList());
        hashMap.put(32, new ArrayList());
        hashMap.put(64, new ArrayList());
        hashMap.put(128, new ArrayList());
        hashMap.put(256, new ArrayList());
        hashMap.put(512, new ArrayList());
        ((List) hashMap.get(1)).add("00001108-0000-1000-8000-00805F9B34FB");
        ((List) hashMap.get(1)).add("00001112-0000-1000-8000-00805F9B34FB");
        ((List) hashMap.get(2)).add("0000111E-0000-1000-8000-00805F9B34FB");
        ((List) hashMap.get(2)).add("0000111F-0000-1000-8000-00805F9B34FB");
        ((List) hashMap.get(16)).add("0000110E-0000-1000-8000-00805F9B34FB");
        ((List) hashMap.get(16)).add("0000110C-0000-1000-8000-00805F9B34FB");
        ((List) hashMap.get(4)).add("0000112f-0000-1000-8000-00805F9B34FB");
        ((List) hashMap.get(4)).add("00001130-0000-1000-8000-00805f9b34fb");
        ((List) hashMap.get(8)).add("0000110A-0000-1000-8000-00805F9B34FB");
        ((List) hashMap.get(8)).add("0000110B-0000-1000-8000-00805F9B34FB");
        ((List) hashMap.get(8)).add("0000110D-0000-1000-8000-00805F9B34FB");
        ((List) hashMap.get(32)).add("00001103-0000-1000-8000-00805f9b34fb");
        ((List) hashMap.get(64)).add("00001106-0000-1000-8000-00805f9b34fb");
        ((List) hashMap.get(128)).add("00001101-0000-1000-8000-00805f9b34fb");
        ((List) hashMap.get(256)).add("0000112D-0000-1000-8000-00805F9B34FB");
        ((List) hashMap.get(512)).add("00001122-0000-1000-8000-00805f9b34fb");
    }

    public static void auditLogMessage(int i, int i2, int i3, boolean z) {
        String str = i2 != 1 ? i2 != 2 ? i2 != 4 ? i2 != 8 ? i2 != 16 ? i2 != 32 ? i2 != 64 ? i2 != 128 ? i2 != 256 ? i2 != 512 ? "" : "BPP" : "SAP" : "SPP" : "FTP" : "DUN" : "AVRCP" : "A2DP" : "PBAP" : "HFP" : "HSP";
        int myPid = Process.myPid();
        String str2 = z ? "Admin %d has allowed %s bluetooth profile." : "Admin %d has blocked %s bluetooth profile.";
        Integer valueOf = Integer.valueOf(i);
        boolean isEmpty = str.isEmpty();
        Object obj = str;
        if (isEmpty) {
            obj = Integer.valueOf(i2);
        }
        AuditLog.logAsUser(5, 1, true, myPid, "BluetoothPolicy", String.format(str2, valueOf, obj), i3);
    }

    public static void disableDesktopConnectivity() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            for (BluetoothDevice bluetoothDevice : defaultAdapter.getBondedDevices()) {
                BluetoothClass bluetoothClass = bluetoothDevice.getBluetoothClass();
                if (bluetoothClass != null && bluetoothClass.getMajorDeviceClass() == 256) {
                    if (bluetoothDevice.isConnected()) {
                        Log.d("BluetoothPolicyService", "Unpair this bluetooth computer(connected) : " + bluetoothDevice.getAddress());
                        bluetoothDevice.removeBond();
                    } else {
                        Log.d("BluetoothPolicyService", "Keep this bluetooth computer(not connected) : " + bluetoothDevice.getAddress());
                    }
                }
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public static void showProfileBlockedToast(int i) {
        int i2;
        if (i == 1) {
            i2 = R.string.config_displayWhiteBalanceColorTemperatureSensorName;
        } else if (i == 2) {
            i2 = R.string.config_displayLightSensorType;
        } else if (i == 4) {
            i2 = R.string.config_doubleTouchGestureEnableFile;
        } else if (i == 8) {
            i2 = R.string.config_default_dns_server;
        } else if (i == 16) {
            i2 = R.string.config_devicePolicyManagementUpdater;
        } else if (i == 32) {
            i2 = R.string.config_deviceSpecificDisplayAreaPolicyProvider;
        } else if (i == 64) {
            i2 = R.string.config_deviceSpecificInputMethodManagerService;
        } else if (i == 128) {
            i2 = R.string.config_dozeUdfpsLongPressSensorType;
        } else if (i == 256) {
            i2 = R.string.config_dozeDoubleTapSensorType;
        } else if (i != 512) {
            return;
        } else {
            i2 = R.string.config_deviceProvisioningPackage;
        }
        RestrictionToastManager.show(i2);
    }

    public static List translateList(List list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) list;
        if (!arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                ControlInfo controlInfo = (ControlInfo) it.next();
                List list2 = controlInfo.entries;
                if (list2 != null && !list2.isEmpty()) {
                    BluetoothControlInfo bluetoothControlInfo = new BluetoothControlInfo();
                    ArrayList arrayList3 = new ArrayList();
                    bluetoothControlInfo.entries = arrayList3;
                    bluetoothControlInfo.adminPackageName = controlInfo.adminPackageName;
                    arrayList3.addAll(controlInfo.entries);
                    arrayList.add(bluetoothControlInfo);
                }
            }
        }
        return arrayList;
    }

    public final boolean activateBluetoothDeviceRestriction(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "activateBluetoothDeviceRestriction");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            boolean z3 = z != this.mDevicePolicy.isPolicyActive(i);
            z2 = this.mEdmStorageProvider.putBoolean("BLUETOOTH", i, z, 0, "devicePolicyEnabled");
            if (z2 && z3) {
                this.mDevicePolicy.reload();
                applyDevicePolicy();
            }
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public final boolean activateBluetoothUUIDRestriction(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "activateBluetoothUUIDRestriction");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            boolean z3 = z != this.mProfilePolicy.isPolicyActive(i);
            z2 = this.mEdmStorageProvider.putBoolean("BLUETOOTH", i, z, 0, "profilePolicyEnabled");
            if (z2 && z3) {
                this.mProfilePolicy.reload();
                restartBluetooth();
            }
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public final boolean addBluetoothDevicesToBlackList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "addDevicesToBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean addObjectsToList = this.mDevicePolicy.addObjectsToList(i, "black", list);
        if (addObjectsToList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return addObjectsToList;
    }

    public final boolean addBluetoothDevicesToWhiteList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "addDevicesToWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean addObjectsToList = this.mDevicePolicy.addObjectsToList(i, "white", list);
        if (addObjectsToList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return addObjectsToList;
    }

    public final boolean addBluetoothUUIDsToBlackList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "addProfilesToBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean addObjectsToList = this.mProfilePolicy.addObjectsToList(i, "black", list);
        if (addObjectsToList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return addObjectsToList;
    }

    public final boolean addBluetoothUUIDsToWhiteList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "addProfilesToWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean addObjectsToList = this.mProfilePolicy.addObjectsToList(i, "white", list);
        if (addObjectsToList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return addObjectsToList;
    }

    public final boolean allowBLE(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndActiveAdminPermission = getEDM$2().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z3 = true;
        if (!z) {
            try {
                z2 = this.mEdmStorageProvider.getBoolean(enforceOwnerOnlyAndActiveAdminPermission.mCallerUid, 0, "BLUETOOTH", "bluetoothEnabled");
            } catch (Exception e) {
                Log.d("BluetoothPolicyService", e.toString());
                z2 = true;
            }
            this.mEdmStorageProvider.putBoolean("BLUETOOTH", enforceOwnerOnlyAndActiveAdminPermission.mCallerUid, z2, 0, "allowBluetoothRestore");
        }
        try {
            z3 = this.mEdmStorageProvider.getBoolean(enforceOwnerOnlyAndActiveAdminPermission.mCallerUid, 0, "BLUETOOTH", "allowBluetoothRestore");
        } catch (Exception e2) {
            Log.d("BluetoothPolicyService", e2.toString());
        }
        if (z3) {
            allowBluetoothForBLE(enforceOwnerOnlyAndActiveAdminPermission, z);
        }
        if (!allowBluetoothForBLE(enforceOwnerOnlyAndActiveAdminPermission, z)) {
            Log.d("BluetoothPolicyService", "allowBLE was failed");
            return false;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("BLUETOOTH", enforceOwnerOnlyAndActiveAdminPermission.mCallerUid, z, 0, "allowBLE");
        if (!z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (defaultAdapter != null) {
                try {
                    defaultAdapter.semShutdown();
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            Settings.Global.putInt(contentResolver, "ble_scan_always_enabled", 0);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return putBoolean;
    }

    public final boolean allowBluetooth(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        if (!isBLEAllowed(enforceOwnerOnlyAndBluetoothPermission)) {
            int adminByField = this.mEdmStorageProvider.getAdminByField("BLUETOOTH", "allowBLE", Integer.toString(0));
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(adminByField, "getAdminUidForBLE - ", "BluetoothPolicyService");
            if (adminByField == enforceOwnerOnlyAndBluetoothPermission.mCallerUid) {
                Log.d("BluetoothPolicyService", "failed to allowBluetooth due to BLE policy");
                return false;
            }
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndBluetoothPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!z) {
                try {
                    BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (defaultAdapter.getState() == 12) {
                        defaultAdapter.disable();
                    } else if (defaultAdapter.getState() == 11) {
                        new AnonymousClass2(this, 0).start();
                    }
                } catch (Exception e) {
                    Log.w("BluetoothPolicyService", e.toString());
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
            }
            boolean putBoolean = this.mEdmStorageProvider.putBoolean("BLUETOOTH", enforceOwnerOnlyAndBluetoothPermission.mCallerUid, z, 0, "bluetoothEnabled");
            if (putBoolean) {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "BluetoothPolicy", String.format("Admin %d has changed allow bluetooth to %s", Integer.valueOf(enforceOwnerOnlyAndBluetoothPermission.mCallerUid), Boolean.valueOf(z)), UserHandle.getUserId(enforceOwnerOnlyAndBluetoothPermission.mCallerUid));
                setBluetoothAllowedSystemUI(callingOrCurrentUserId, isBluetoothEnabled(false));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return putBoolean;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean allowBluetoothForBLE(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!z) {
            try {
                try {
                    BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (defaultAdapter.getState() == 12) {
                        defaultAdapter.disable();
                    } else if (defaultAdapter.getState() == 11) {
                        new AnonymousClass2(this, 0).start();
                    }
                } catch (Exception e) {
                    Log.w("BluetoothPolicyService", e.toString());
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("BLUETOOTH", contextInfo.mCallerUid, z, 0, "bluetoothEnabled");
        if (putBoolean) {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "BluetoothPolicy", String.format("Admin %d has changed allow bluetooth to %s", Integer.valueOf(contextInfo.mCallerUid), Boolean.valueOf(z)), UserHandle.getUserId(contextInfo.mCallerUid));
            setBluetoothAllowedSystemUI(callingOrCurrentUserId, isBluetoothEnabled(false));
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return putBoolean;
    }

    public final boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) {
        try {
            return this.mEdmStorageProvider.putBoolean("BLUETOOTH", enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid, z, 0, "allowCallerID");
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
            return false;
        }
    }

    public final boolean allowOutgoingCalls(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "allowOutgoingCalls = " + z);
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            z2 = this.mEdmStorageProvider.putBoolean("BLUETOOTH", enforceOwnerOnlyAndBluetoothPermission.mCallerUid, z, 0, "allowOutgoingCalls");
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (z2 && defaultAdapter != null && defaultAdapter.isEnabled()) {
                this.mRestart = true;
                defaultAdapter.disable();
            }
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public final void applyDevicePolicy() {
        List effectiveBlackList = this.mDevicePolicy.getEffectiveBlackList();
        try {
            for (BluetoothDevice bluetoothDevice : BluetoothAdapter.getDefaultAdapter().getBondedDevices()) {
                String address = bluetoothDevice.getAddress();
                ArrayList arrayList = (ArrayList) effectiveBlackList;
                if (!arrayList.contains(address) && !arrayList.contains("*")) {
                }
                Log.d("BluetoothPolicyService", "device.removeBond() : " + address);
                bluetoothDevice.removeBond();
            }
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
        }
    }

    public final boolean bluetoothLog(ContextInfo contextInfo, String str, String str2) {
        if (!isBluetoothLogEnabled(contextInfo)) {
            return false;
        }
        Log.d("BluetoothPolicyService", "on server bluetoothLoglogConnectionChanged [" + str + "] " + str2);
        return ((LinkedBlockingQueue) this.mLogQueue).offer(BootReceiver$$ExternalSyntheticOutline0.m("[", str, "]\n", str2));
    }

    public final boolean clearBluetoothDevicesFromBlackList(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "clearDevicesFromBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean clearObjectsFromList = this.mDevicePolicy.clearObjectsFromList(i, "black");
        if (clearObjectsFromList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return clearObjectsFromList;
    }

    public final boolean clearBluetoothDevicesFromWhiteList(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "clearDevicesFromWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean clearObjectsFromList = this.mDevicePolicy.clearObjectsFromList(i, "white");
        if (clearObjectsFromList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return clearObjectsFromList;
    }

    public final boolean clearBluetoothUUIDsFromBlackList(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "clearProfilesFromBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean clearObjectsFromList = this.mProfilePolicy.clearObjectsFromList(i, "black");
        if (clearObjectsFromList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return clearObjectsFromList;
    }

    public final boolean clearBluetoothUUIDsFromWhiteList(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "clearProfilesFromWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean clearObjectsFromList = this.mProfilePolicy.clearObjectsFromList(i, "white");
        if (clearObjectsFromList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return clearObjectsFromList;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump SecurityPolicy");
        } else {
            this.mEnterpriseDumpHelper.dumpTable(printWriter, "BLUETOOTH", new String[]{"adminUid", "allowCallerID", "allowDataTransfer", "allowOutgoingCalls", "desktopConnectivityEnabled", "devicePolicyEnabled", "discoverableModeEnabled", "bluetoothEnabled", "limitedDiscoverableEnabled", "bluetoothLogEnabled", "pairingEnabled", "profileSettings", "profilePolicyEnabled"}, null);
        }
    }

    public final ContextInfo enforceOwnerOnlyAndBluetoothPermission(ContextInfo contextInfo) {
        return getEDM$2().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_BLUETOOTH")));
    }

    public final List getAllBluetoothDevicesBlackLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getAllDevicesBlackLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List translateList = translateList(this.mDevicePolicy.getAllObjectsFromListForAllAdmins("black"));
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return translateList;
    }

    public final List getAllBluetoothDevicesWhiteLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getAllDevicesWhiteLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List translateList = translateList(this.mDevicePolicy.getAllObjectsFromListForAllAdmins("white"));
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return translateList;
    }

    public final List getAllBluetoothUUIDsBlackLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getAllProfilesBlackLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List translateList = translateList(this.mProfilePolicy.getAllObjectsFromListForAllAdmins("black"));
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return translateList;
    }

    public final List getAllBluetoothUUIDsWhiteLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getAllProfilesWhiteLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List translateList = translateList(this.mProfilePolicy.getAllObjectsFromListForAllAdmins("white"));
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return translateList;
    }

    public final boolean getAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) {
        boolean z2;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("getAllowBluetoothDataTransfer - showMsg: ", "BluetoothPolicyService", z);
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "BLUETOOTH", "allowDataTransfer").iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = true;
                break;
            }
            z2 = ((Boolean) it.next()).booleanValue();
            if (!z2) {
                break;
            }
        }
        if (z && !z2) {
            RestrictionToastManager.show(R.string.config_deviceSpecificAudioService);
        }
        return z2;
    }

    public final List getBluetoothLog(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getBluetoothLog()");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("BluetoothLogTable", null, null, new String[]{"time", "log"});
        Log.d("BluetoothPolicyService", "getBluetoothLog() - cvList: " + dataByFields);
        Iterator it = dataByFields.iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            String asString = contentValues.getAsString("time");
            if (asString != null) {
                String concat = asString.concat(":");
                String asString2 = contentValues.getAsString("log");
                if (asString2 != null) {
                    concat = concat.concat(asString2);
                }
                arrayList.add(concat);
                Log.d("BluetoothPolicyService", "getBluetoothLog() - report: " + concat);
            }
        }
        Log.d("BluetoothPolicyService", "getBluetoothLog() - reportList: " + arrayList);
        return arrayList;
    }

    public final boolean getBluetoothLogEnabled(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getBluetoothLogEnabled(true)");
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(-1, "getBluetoothLogEnabled - uid: ", "BluetoothPolicyService");
        boolean z = false;
        List valuesListAsUser = this.mEdmStorageProvider.getValuesListAsUser(0, 0, "BLUETOOTH", new String[]{"bluetoothLogEnabled"});
        Log.d("BluetoothPolicyService", "getBluetoothLogEnabled - cvList: " + valuesListAsUser);
        Iterator it = ((ArrayList) valuesListAsUser).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ContentValues contentValues = (ContentValues) it.next();
            Log.d("BluetoothPolicyService", "getBluetoothLogEnabled - cv: " + contentValues);
            if ("true".equals(contentValues.getAsString("bluetoothLogEnabled"))) {
                z = true;
                break;
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("getBluetoothLogEnabled - ret: ", "BluetoothPolicyService", z);
        return z;
    }

    public final EnterpriseDeviceManager getEDM$2() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final List getEffectiveBluetoothDevicesBlackLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getEffectiveBluetoothDevicesBlackLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List effectiveBlackList = this.mDevicePolicy.getEffectiveBlackList();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return effectiveBlackList;
    }

    public final List getEffectiveBluetoothDevicesWhiteLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getEffectiveBluetoothDevicesWhiteLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        BluetoothDevicePolicy bluetoothDevicePolicy = this.mDevicePolicy;
        if (bluetoothDevicePolicy.bUpdateToDB) {
            bluetoothDevicePolicy.updateEffectivePolicy();
            bluetoothDevicePolicy.bUpdateToDB = true;
        }
        List list = bluetoothDevicePolicy.mEffectiveWhitelist;
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return list;
    }

    public final List getEffectiveBluetoothUUIDsBlackLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getEffectiveBluetoothUUIDsBlackLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List effectiveBlackList = this.mProfilePolicy.getEffectiveBlackList();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return effectiveBlackList;
    }

    public final List getEffectiveBluetoothUUIDsWhiteLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getEffectiveBluetoothUUIDsWhiteLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        BluetoothDevicePolicy bluetoothDevicePolicy = this.mProfilePolicy;
        if (bluetoothDevicePolicy.bUpdateToDB) {
            bluetoothDevicePolicy.updateEffectivePolicy();
            bluetoothDevicePolicy.bUpdateToDB = true;
        }
        List list = bluetoothDevicePolicy.mEffectiveWhitelist;
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return list;
    }

    public final boolean isBLEAllowed(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "BLUETOOTH", "allowBLE").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public final boolean isBluetoothDeviceAllowed(ContextInfo contextInfo, String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return this.mDevicePolicy.isObjectAllowed(str);
    }

    public final boolean isBluetoothDeviceRestrictionActive(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "isBluetoothDeviceRestrictionActive");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean isPolicyActive = this.mDevicePolicy.isPolicyActive(contextInfo.mCallerUid);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return isPolicyActive;
    }

    public final boolean isBluetoothEnabled(ContextInfo contextInfo) {
        return isBluetoothEnabled(false);
    }

    public final boolean isBluetoothEnabled(boolean z) {
        boolean z2 = false;
        if (!(getEDM$2() != null ? getEDM$2().isRestrictedByConstrainedState(8) : false)) {
            Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "BLUETOOTH", "bluetoothEnabled").iterator();
            while (true) {
                if (!it.hasNext()) {
                    z2 = true;
                    break;
                }
                z2 = ((Boolean) it.next()).booleanValue();
                if (!z2) {
                    break;
                }
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isBluetoothEnabled : ", "BluetoothPolicyService", z2);
        if (z && !z2) {
            RestrictionToastManager.show(R.string.config_dozeComponent);
        }
        return z2;
    }

    public final boolean isBluetoothEnabledWithMsg(boolean z) {
        return isBluetoothEnabled(z);
    }

    public final boolean isBluetoothLogEnabled(ContextInfo contextInfo) {
        if (!this.isCacheUpdated) {
            this.mCacheIsBluetoothLogEnabled = getBluetoothLogEnabled(contextInfo);
            this.isCacheUpdated = true;
        }
        return this.mCacheIsBluetoothLogEnabled;
    }

    public final boolean isBluetoothUUIDAllowed(ContextInfo contextInfo, String str) {
        return this.mProfilePolicy.isObjectAllowed(str);
    }

    public final boolean isBluetoothUUIDRestrictionActive(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "isBluetoothUUIDRestrictionActive");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean isPolicyActive = this.mProfilePolicy.isPolicyActive(contextInfo.mCallerUid);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return isPolicyActive;
    }

    public final boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) {
        try {
            Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "BLUETOOTH", "allowCallerID").iterator();
            while (it.hasNext()) {
                boolean booleanValue = ((Boolean) it.next()).booleanValue();
                if (!booleanValue) {
                    return booleanValue;
                }
            }
            return true;
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
            return true;
        }
    }

    public final boolean isDesktopConnectivityEnabled(ContextInfo contextInfo) {
        return isDesktopConnectivityEnabled(false);
    }

    public final boolean isDesktopConnectivityEnabled(boolean z) {
        boolean z2;
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "BLUETOOTH", "desktopConnectivityEnabled").iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = true;
                break;
            }
            z2 = ((Boolean) it.next()).booleanValue();
            if (!z2) {
                break;
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isDesktopConnectivityEnabled : ", "BluetoothPolicyService", z2);
        if (z && !z2) {
            RestrictionToastManager.show(R.string.config_deviceSpecificDevicePolicyManagerService);
        }
        return z2;
    }

    public final boolean isDiscoverableEnabled(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "BLUETOOTH", "discoverableModeEnabled").iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isDiscoverableEnabled : ", "BluetoothPolicyService", z);
        return z;
    }

    public final boolean isLimitedDiscoverableEnabled(ContextInfo contextInfo) {
        boolean z;
        Log.d("BluetoothPolicyService", "isLimitedDiscoverableEnabled ");
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "BLUETOOTH", "limitedDiscoverableEnabled").iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isLimitedDiscoverableEnabled ret:", "BluetoothPolicyService", z);
        return z;
    }

    public final boolean isOutgoingCallsAllowed(ContextInfo contextInfo) {
        return isOutgoingCallsAllowed(false);
    }

    public final boolean isOutgoingCallsAllowed(boolean z) {
        boolean z2;
        Log.d("BluetoothPolicyService", "isPairingEnabled ");
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "BLUETOOTH", "allowOutgoingCalls").iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = true;
                break;
            }
            z2 = ((Boolean) it.next()).booleanValue();
            if (!z2) {
                break;
            }
        }
        if (z && !z2) {
            RestrictionToastManager.show(R.string.config_display_features);
        }
        return z2;
    }

    public final boolean isPairingEnabled(ContextInfo contextInfo) {
        return isPairingEnabled(false);
    }

    public final boolean isPairingEnabled(boolean z) {
        boolean z2;
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "BLUETOOTH", "pairingEnabled").iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = true;
                break;
            }
            z2 = ((Boolean) it.next()).booleanValue();
            if (!z2) {
                break;
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isPairingEnabled ", "BluetoothPolicyService", z2);
        if (z && !z2) {
            RestrictionToastManager.show(R.string.config_doublePressOnPowerTargetActivity);
        }
        return z2;
    }

    public final boolean isProfileEnabled(int i) {
        try {
            if (i <= 0) {
                throw new InvalidParameterException();
            }
            ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(0, 0, "BLUETOOTH", "profileSettings");
            if (intListAsUser.isEmpty()) {
                return true;
            }
            Iterator it = intListAsUser.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                if (num.intValue() >= 0 && i != (num.intValue() & i)) {
                    return false;
                }
            }
            return true;
        } catch (InvalidParameterException unused) {
            Log.w("BluetoothPolicyService", "isProfileEnabled() failed: INVALID PARAMETER INPUT");
            return true;
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
            return true;
        }
    }

    public final boolean isProfileEnabled(ContextInfo contextInfo, int i) {
        Utils.getCallingOrUserUid(contextInfo);
        return isProfileEnabled(i);
    }

    public final boolean isProfileEnabledInternal(int i, boolean z) {
        boolean z2 = true;
        try {
        } catch (InvalidParameterException unused) {
            Log.w("BluetoothPolicyService", "isProfileEnabledInternal() failed: INVALID PARAMETER INPUT");
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
        }
        if (i <= 0) {
            throw new InvalidParameterException();
        }
        Binder.getCallingUid();
        if (!isProfileEnabled(i)) {
            if (z) {
                showProfileBlockedToast(i);
            }
            return false;
        }
        List list = (List) ((HashMap) this.mProfileMap).get(Integer.valueOf(i));
        int i2 = 0;
        while (true) {
            if (i2 >= list.size()) {
                break;
            }
            if (!this.mProfilePolicy.isObjectAllowed((String) list.get(i2))) {
                z2 = false;
                break;
            }
            i2++;
        }
        if (z && !z2) {
            showProfileBlockedToast(i);
        }
        return z2;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        Log.d("BluetoothPolicyService", "onAdminRemoved - uid: " + i);
        boolean z = false;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        this.mProfilePolicy.reload();
        this.mDevicePolicy.reload();
        this.isCacheUpdated = false;
        if (!getBluetoothLogEnabled(null)) {
            Log.d("BluetoothPolicyService", "onAdminRemoved - Clean log");
            this.mEdmStorageProvider.delete("BluetoothLogTable", null);
        }
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            if (isBluetoothEnabled(false)) {
                if (!(getEDM$2() != null ? getEDM$2().isRestrictedByConstrainedState(8) : false)) {
                    z = true;
                }
            }
            setBluetoothAllowedSystemUI(callingOrCurrentUserId, z);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
    }

    public final boolean removeBluetoothDevicesFromBlackList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "removeDevicesFromBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean removeObjectsFromList = this.mDevicePolicy.removeObjectsFromList(i, "black", list);
        if (removeObjectsFromList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return removeObjectsFromList;
    }

    public final boolean removeBluetoothDevicesFromWhiteList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "removeDevicesFromWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean removeObjectsFromList = this.mDevicePolicy.removeObjectsFromList(i, "white", list);
        if (removeObjectsFromList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return removeObjectsFromList;
    }

    public final boolean removeBluetoothUUIDsFromBlackList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "removeProfilesFromBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean removeObjectsFromList = this.mProfilePolicy.removeObjectsFromList(i, "black", list);
        if (removeObjectsFromList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return removeObjectsFromList;
    }

    public final boolean removeBluetoothUUIDsFromWhiteList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "removeProfilesFromWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean removeObjectsFromList = this.mProfilePolicy.removeObjectsFromList(i, "white", list);
        if (removeObjectsFromList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return removeObjectsFromList;
    }

    public final void restartBluetooth() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || !defaultAdapter.isEnabled()) {
            return;
        }
        this.mRestart = true;
        defaultAdapter.disable();
    }

    public final boolean setAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "setAllowBluetoothDataTransfer = " + z);
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        Binder.restoreCallingIdentity(Binder.clearCallingIdentity());
        return this.mEdmStorageProvider.putBoolean("BLUETOOTH", enforceOwnerOnlyAndBluetoothPermission.mCallerUid, z, 0, "allowDataTransfer");
    }

    public final boolean setBluetooth(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndBluetoothPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z2 = this.mEdmStorageProvider.putBoolean("BLUETOOTH", enforceOwnerOnlyAndBluetoothPermission.mCallerUid, z, 0, "bluetoothEnabled");
                Log.w("BluetoothPolicyService", "setBluetooth : " + z);
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                if (z && isBluetoothEnabled(false)) {
                    defaultAdapter.enable();
                } else if (defaultAdapter.getState() == 12) {
                    defaultAdapter.disable();
                } else if (defaultAdapter.getState() == 11) {
                    new AnonymousClass2(this, 0).start();
                }
                if (z2) {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "BluetoothPolicy", String.format("Admin %d has changed bluetooth enabled to %s", Integer.valueOf(enforceOwnerOnlyAndBluetoothPermission.mCallerUid), Boolean.valueOf(z)), UserHandle.getUserId(enforceOwnerOnlyAndBluetoothPermission.mCallerUid));
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Exception e) {
                Log.w("BluetoothPolicyService", e.toString());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z2 = false;
            }
            if (z2) {
                setBluetoothAllowedSystemUI(callingOrCurrentUserId, isBluetoothEnabled(false));
            }
            return z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void setBluetoothAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setBluetoothAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("BluetoothPolicyService", "setBluetoothAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setBluetoothLogEnabled(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "setBluetoothLogEnabled(" + z + ")");
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        ContentValues contentValues = new ContentValues();
        contentValues.put("bluetoothLogEnabled", String.valueOf(z));
        boolean putValues = this.mEdmStorageProvider.putValues(enforceOwnerOnlyAndBluetoothPermission.mCallerUid, 0, "BLUETOOTH", contentValues);
        if (!z && !getBluetoothLogEnabled(enforceOwnerOnlyAndBluetoothPermission)) {
            Log.d("BluetoothPolicyService", "setBluetoothLogEnabled - Clean log");
            this.mEdmStorageProvider.delete("BluetoothLogTable", null);
            ((LinkedBlockingQueue) this.mLogQueue).clear();
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("setBluetoothLogEnabled - return = ", "BluetoothPolicyService", putValues);
        if (putValues) {
            this.isCacheUpdated = false;
        }
        return putValues;
    }

    public final boolean setDesktopConnectivityState(ContextInfo contextInfo, boolean z) {
        boolean z2;
        Log.d("BluetoothPolicyService", "setDesktopConnectivityState = " + z);
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            z2 = this.mEdmStorageProvider.putBoolean("BLUETOOTH", enforceOwnerOnlyAndBluetoothPermission.mCallerUid, z, 0, "desktopConnectivityEnabled");
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!z && z2 && defaultAdapter != null && defaultAdapter.isEnabled()) {
                disableDesktopConnectivity();
            }
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
            z2 = false;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public final boolean setDiscoverableState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                boolean putBoolean = this.mEdmStorageProvider.putBoolean("BLUETOOTH", enforceOwnerOnlyAndBluetoothPermission.mCallerUid, z, 0, "discoverableModeEnabled");
                Log.w("BluetoothPolicyService", "setDiscoverableState : " + z);
                if (putBoolean) {
                    AuditLog.logEventAsUser(UserHandle.getUserId(enforceOwnerOnlyAndBluetoothPermission.mCallerUid), z ? 28 : 29, new Object[]{Integer.valueOf(enforceOwnerOnlyAndBluetoothPermission.mCallerUid)});
                }
                return putBoolean;
            } catch (Exception e) {
                Log.w("BluetoothPolicyService", e.toString());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setLimitedDiscoverableState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                boolean putBoolean = this.mEdmStorageProvider.putBoolean("BLUETOOTH", enforceOwnerOnlyAndBluetoothPermission.mCallerUid, z, 0, "limitedDiscoverableEnabled");
                Log.w("BluetoothPolicyService", "setLimitedDiscoverableState : " + z);
                if (putBoolean) {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "BluetoothPolicy", String.format(z ? "Admin %d has enabled bluetooth limited discoverable state." : "Admin %d has disabled bluetooth limited discoverable state.", Integer.valueOf(enforceOwnerOnlyAndBluetoothPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndBluetoothPermission.mCallerUid));
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return putBoolean;
            } catch (Exception e) {
                Log.w("BluetoothPolicyService", e.toString());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean setPairingState(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "setPairingState = " + z);
        return this.mEdmStorageProvider.putBoolean("BLUETOOTH", enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid, z, 0, "pairingEnabled");
    }

    public final boolean setProfileState(ContextInfo contextInfo, boolean z, int i) {
        int i2;
        Log.d("BluetoothPolicyService", "setProfileState = " + i + " state " + z);
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        int i3 = enforceOwnerOnlyAndBluetoothPermission.mCallerUid;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndBluetoothPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
            } catch (InvalidParameterException unused) {
                Log.w("BluetoothPolicyService", "setProfileState() failed: INVALID PARAMETER INPUT");
            } catch (Exception e) {
                Log.w("BluetoothPolicyService", e.toString());
            }
            if (i <= 0) {
                throw new InvalidParameterException();
            }
            try {
                i2 = this.mEdmStorageProvider.getInt(i3, 0, "BLUETOOTH", "profileSettings");
            } catch (SettingNotFoundException unused2) {
                i2 = GnssNative.GNSS_AIDING_TYPE_ALL;
            }
            z2 = this.mEdmStorageProvider.putInt(i3, 0, true == z ? i2 | i : i2 & (~i), "BLUETOOTH", "profileSettings");
            if (z2) {
                auditLogMessage(i3, i, callingOrCurrentUserId, z);
            }
            restartBluetooth();
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        this.mProfilePolicy.reload();
        this.mDevicePolicy.reload();
        new AnonymousClass2(this, 1).start();
    }
}
