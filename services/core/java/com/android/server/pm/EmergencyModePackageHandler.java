package com.android.server.pm;

import android.app.ActivityThread;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.SparseArray;
import com.android.server.ServiceThread;
import com.android.server.Watchdog;
import com.android.server.pm.pkg.PackageStateInternal;
import com.samsung.android.emergencymode.Elog;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class EmergencyModePackageHandler {
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public final Context mContext;
    public final EMPackageHandler mEMPackageHander;
    public final ServiceThread mEMPackageHanderThread;
    public final PendingPackageBroadcastsWithList mPendingBroadcastsForBurst;
    public final PackageManagerService mPkgMgrSvc;
    public final ProtectedPackages mProtectedPackages;
    public UserManagerInternal mUserManager;

    public EmergencyModePackageHandler(Context context, PackageManagerService packageManagerService, UserManagerInternal userManagerInternal, ProtectedPackages protectedPackages) {
        ServiceThread serviceThread = new ServiceThread("EMPackageHandler", 0, true);
        this.mEMPackageHanderThread = serviceThread;
        this.mPendingBroadcastsForBurst = new PendingPackageBroadcastsWithList();
        serviceThread.start();
        this.mContext = context;
        this.mPkgMgrSvc = packageManagerService;
        this.mProtectedPackages = protectedPackages;
        EMPackageHandler eMPackageHandler = new EMPackageHandler(serviceThread.getLooper());
        this.mEMPackageHander = eMPackageHandler;
        this.mUserManager = userManagerInternal;
        Watchdog.getInstance().addThread(eMPackageHandler, 600000L);
    }

    public class EMPackageHandler extends Handler {
        public final long TIMEOUT;
        public ArrayList[] emComponents;
        public int emCurrentPosition;
        public boolean[] emDontKillFlags;
        public int emID;
        public int[] emNewState;
        public String[] emPackages;
        public int emPreviousID;
        public int emProgression;
        public IIntentReceiver emReceiverBroadcastNext;
        public int emSize;
        public int emTotSize;
        public int[] emUids;
        public int emUserId;

        public EMPackageHandler(Looper looper) {
            super(looper);
            this.emID = 0;
            this.emPreviousID = -1;
            this.TIMEOUT = 60000L;
            this.emReceiverBroadcastNext = new IIntentReceiver.Stub() { // from class: com.android.server.pm.EmergencyModePackageHandler.EMPackageHandler.1
                public void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
                    int i3 = -1;
                    if (intent == null) {
                        Elog.v("EMPkgHandler", "intent is null!");
                    } else {
                        i3 = intent.getIntExtra("EM_PKG_HADNLER_ID", -1);
                    }
                    if (EMPackageHandler.this.emID == i3) {
                        Elog.d("EMPkgHandler", "performReceive sending EM_SEND_PENDING_BROADCAST id[" + i3 + "]");
                        EmergencyModePackageHandler.this.mEMPackageHander.sendEmptyMessage(2);
                        return;
                    }
                    Elog.v("EMPkgHandler", "performReceive canceled emID[" + EMPackageHandler.this.emID + "]  thisID[" + i3 + "]");
                }
            };
        }

        public void initForPendingBroadcast(int i) {
            makeNewID();
            this.emPreviousID = this.emID;
            this.emPackages = null;
            this.emComponents = null;
            this.emUids = null;
            this.emDontKillFlags = null;
            this.emNewState = null;
            this.emSize = 0;
            this.emTotSize = 0;
            this.emCurrentPosition = 0;
            this.emUserId = i;
            this.emProgression = 0;
        }

        public void resetTask() {
            makeNewID();
            this.emPreviousID = this.emID;
            this.emPackages = null;
            this.emComponents = null;
            this.emUids = null;
            this.emDontKillFlags = null;
            this.emNewState = null;
            this.emSize = 0;
            this.emTotSize = 0;
            this.emCurrentPosition = 0;
            this.emProgression = 0;
            Elog.d("EMPkgHandler", "EMPackageHandler memory references are released");
        }

        public void makeNewID() {
            this.emID = (this.emID + 1) % 1000;
            Elog.d("EMPkgHandler", "makeNewID [" + this.emID + "]");
        }

        public boolean isCanceled() {
            return this.emPreviousID != this.emID;
        }

        public int getProgressionOfPackageChanged() {
            return this.emProgression;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                doHandleMessage(message);
            } finally {
                Process.setThreadPriority(0);
            }
        }

        public void doHandleMessage(Message message) {
            int i;
            int i2 = message.what;
            if (i2 == 1) {
                Elog.d("EMPkgHandler", "EM_MAKE_PENDING_BROADCAST Start");
                initForPendingBroadcast(message.arg1);
                PendingPackageBroadcastsWithList pendingPackageBroadcastsWithList = EmergencyModePackageHandler.this.mPendingBroadcastsForBurst;
                if (pendingPackageBroadcastsWithList != null) {
                    int size = pendingPackageBroadcastsWithList.size();
                    this.emSize = size;
                    if (size <= 0) {
                        Elog.v("EMPkgHandler", "pending size [" + this.emSize + "] EM_MAKE_PENDING_BROADCAST Cancel");
                        this.emProgression = 0;
                        EmergencyModePackageHandler.this.mEMPackageHander.sendEmptyMessage(4);
                        return;
                    }
                    String[] strArr = new String[size];
                    this.emPackages = strArr;
                    ArrayList[] arrayListArr = new ArrayList[size];
                    this.emComponents = arrayListArr;
                    int[] iArr = new int[size];
                    this.emUids = iArr;
                    boolean[] zArr = new boolean[size];
                    this.emDontKillFlags = zArr;
                    int[] iArr2 = new int[size];
                    this.emNewState = iArr2;
                    int handlePendingBroadcastsForBurst = EmergencyModePackageHandler.this.handlePendingBroadcastsForBurst(strArr, arrayListArr, iArr, zArr, iArr2, size);
                    this.emTotSize = handlePendingBroadcastsForBurst;
                    if (handlePendingBroadcastsForBurst > 0) {
                        EmergencyModePackageHandler.this.mEMPackageHander.sendEmptyMessage(2);
                    }
                }
                Elog.d("EMPkgHandler", "EM_MAKE_PENDING_BROADCAST End");
                return;
            }
            if (i2 != 2) {
                if (i2 == 3) {
                    Elog.d("EMPkgHandler", "EM_CHECK_TIMEOUT_OF_BROADCAST");
                    EmergencyModePackageHandler.this.mEMPackageHander.sendEmptyMessage(2);
                    return;
                }
                if (i2 == 4) {
                    Elog.d("EMPkgHandler", "EMERGENCY_FINISHED_SENDING_PACKAGE_CHANGED");
                    Intent intent = new Intent("com.samsung.intent.action.EMERGENCY_FINISHED_SENDING_PACKAGE_CHANGED");
                    intent.addFlags(268435456);
                    EmergencyModePackageHandler.this.mContext.sendBroadcast(intent);
                    resetTask();
                    return;
                }
                if (i2 != 5) {
                    return;
                }
                if (EmergencyModePackageHandler.this.mEMPackageHander.hasMessages(4)) {
                    Elog.d("EMPkgHandler", "cancelEMHandlerSendPendingBroadcast : Nothing to do");
                    return;
                }
                if (EmergencyModePackageHandler.this.mEMPackageHander.hasMessages(2)) {
                    EmergencyModePackageHandler.this.mEMPackageHander.removeMessages(2);
                }
                if (EmergencyModePackageHandler.this.mEMPackageHander.hasMessages(3)) {
                    EmergencyModePackageHandler.this.mEMPackageHander.removeMessages(3);
                }
                if (EmergencyModePackageHandler.this.mEMPackageHander.hasMessages(1)) {
                    EmergencyModePackageHandler.this.mEMPackageHander.removeMessages(1);
                }
                Elog.v("EMPkgHandler", "EM_CANCEL_SENDING_BROADCAST");
                EmergencyModePackageHandler.this.mEMPackageHander.sendEmptyMessage(4);
                return;
            }
            Elog.d("EMPkgHandler", "EM_SEND_PENDING_BROADCAST Start");
            if (EmergencyModePackageHandler.this.mEMPackageHander.hasMessages(3)) {
                EmergencyModePackageHandler.this.mEMPackageHander.removeMessages(3);
            }
            if (this.emCurrentPosition >= this.emTotSize) {
                this.emProgression = 0;
                if (this.emPreviousID == this.emID) {
                    EmergencyModePackageHandler.this.mEMPackageHander.sendEmptyMessage(4);
                    return;
                }
                Elog.v("EMPkgHandler", "SKIP EMERGENCY_FINISHED_SENDING_PACKAGE_CHANGED emPrevioudID[" + this.emPreviousID + "] emID[" + this.emID + "]");
                return;
            }
            Computer snapshotComputer = EmergencyModePackageHandler.this.mPkgMgrSvc.snapshotComputer();
            int i3 = 0;
            while (i3 < 10 && (i = this.emCurrentPosition) < this.emTotSize) {
                PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(this.emPackages[i]);
                int enabledState = packageStateInternal != null ? packageStateInternal.getUserStateOrDefault(this.emUserId).getEnabledState() : -1;
                boolean z = i3 == 9 || this.emCurrentPosition == this.emTotSize - 1;
                int[] iArr3 = this.emNewState;
                int i4 = this.emCurrentPosition;
                if (enabledState == iArr3[i4]) {
                    this.emProgression = (int) ((i4 / this.emTotSize) * 100.0f);
                    if (z) {
                        EmergencyModePackageHandler.this.mEMPackageHander.sendEmptyMessageDelayed(3, 60000L);
                        EmergencyModePackageHandler emergencyModePackageHandler = EmergencyModePackageHandler.this;
                        String[] strArr2 = this.emPackages;
                        int i5 = this.emCurrentPosition;
                        emergencyModePackageHandler.sendPackageChangedBroadcastWithReceiver(strArr2[i5], this.emDontKillFlags[i5], this.emComponents[i5], this.emUids[i5], this.emReceiverBroadcastNext, this.emID);
                    } else {
                        EmergencyModePackageHandler.this.sendPackageChangedBroadcastWithReceiver(this.emPackages[i4], this.emDontKillFlags[i4], this.emComponents[i4], this.emUids[i4], null, this.emID);
                    }
                    this.emCurrentPosition++;
                } else {
                    Elog.v("EMPkgHandler", "SKIP EM_SEND_PENDING_BROADCAST [" + this.emCurrentPosition + "] / [" + (this.emTotSize - 1) + "] name[" + this.emPackages[this.emCurrentPosition] + "] curr[" + enabledState + "]  now[" + this.emNewState[this.emCurrentPosition] + "]  emUserId[" + this.emUserId + "]");
                    if (i3 == 9 || this.emCurrentPosition == this.emTotSize - 1) {
                        EmergencyModePackageHandler.this.mEMPackageHander.sendEmptyMessage(2);
                    }
                    this.emCurrentPosition++;
                }
                i3++;
            }
            Elog.d("EMPkgHandler", "EM_SEND_PENDING_BROADCAST End");
        }
    }

    public class PendingPackageBroadcastsWithList {
        public final SparseArray mUidMap = new SparseArray(2);
        public final SparseArray mUidMapOfNewPkgState = new SparseArray(2);

        public ArrayList get(int i, String str) {
            return (ArrayList) getOrAllocate(i).get(str);
        }

        public void put(int i, String str, ArrayList arrayList) {
            getOrAllocate(i).put(str, arrayList);
        }

        public void putNewState(int i, String str, int i2) {
            getOrAllocateNewState(i).put(str, new Integer(i2));
        }

        public void remove(int i) {
            this.mUidMap.remove(i);
            this.mUidMapOfNewPkgState.remove(i);
        }

        public LinkedHashMap packagesForUserId(int i) {
            return (LinkedHashMap) this.mUidMap.get(i);
        }

        public LinkedHashMap packagesNewStateForUserId(int i) {
            return (LinkedHashMap) this.mUidMapOfNewPkgState.get(i);
        }

        public int userIdCount() {
            return this.mUidMap.size();
        }

        public int userIdAt(int i) {
            return this.mUidMap.keyAt(i);
        }

        public int size() {
            int i = 0;
            for (int i2 = 0; i2 < this.mUidMap.size(); i2++) {
                i += ((LinkedHashMap) this.mUidMap.valueAt(i2)).size();
            }
            return i;
        }

        public void clear() {
            this.mUidMap.clear();
            this.mUidMapOfNewPkgState.clear();
        }

        public final LinkedHashMap getOrAllocate(int i) {
            LinkedHashMap linkedHashMap = (LinkedHashMap) this.mUidMap.get(i);
            if (linkedHashMap != null) {
                return linkedHashMap;
            }
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            this.mUidMap.put(i, linkedHashMap2);
            return linkedHashMap2;
        }

        public final LinkedHashMap getOrAllocateNewState(int i) {
            LinkedHashMap linkedHashMap = (LinkedHashMap) this.mUidMapOfNewPkgState.get(i);
            if (linkedHashMap != null) {
                return linkedHashMap;
            }
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            this.mUidMapOfNewPkgState.put(i, linkedHashMap2);
            return linkedHashMap2;
        }
    }

    public PendingPackageBroadcastsWithList getPendingBroadcastsForBurst() {
        return this.mPendingBroadcastsForBurst;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sendPackageChangedBroadcastWithReceiver(String str, boolean z, ArrayList arrayList, int i, IIntentReceiver iIntentReceiver, int i2) {
        boolean z2;
        int userId = UserHandle.getUserId(i);
        try {
            try {
                z2 = getIPackageManager().isInstantApp(str, userId);
            } catch (RemoteException e) {
                e = e;
                e.printStackTrace();
                z2 = false;
                int[] iArr = !z2 ? new int[]{userId} : EMPTY_INT_ARRAY;
                Bundle bundle = new Bundle(4);
                bundle.putString("android.intent.extra.changed_component_name", (String) arrayList.get(0));
                String[] strArr = new String[arrayList.size()];
                arrayList.toArray(strArr);
                bundle.putStringArray("android.intent.extra.changed_component_name_list", strArr);
                bundle.putBoolean("android.intent.extra.DONT_KILL_APP", z);
                bundle.putInt("android.intent.extra.UID", i);
                bundle.putInt("EM_PKG_HADNLER_ID", i2);
                this.mPkgMgrSvc.sendPackageBroadcast("android.intent.action.PACKAGE_CHANGED", str, bundle, 268435456, null, iIntentReceiver, new int[]{UserHandle.getUserId(i)}, iArr, null, null);
            }
        } catch (RemoteException e2) {
            e = e2;
        }
        int[] iArr2 = !z2 ? new int[]{userId} : EMPTY_INT_ARRAY;
        Bundle bundle2 = new Bundle(4);
        bundle2.putString("android.intent.extra.changed_component_name", (String) arrayList.get(0));
        String[] strArr2 = new String[arrayList.size()];
        arrayList.toArray(strArr2);
        bundle2.putStringArray("android.intent.extra.changed_component_name_list", strArr2);
        bundle2.putBoolean("android.intent.extra.DONT_KILL_APP", z);
        bundle2.putInt("android.intent.extra.UID", i);
        bundle2.putInt("EM_PKG_HADNLER_ID", i2);
        this.mPkgMgrSvc.sendPackageBroadcast("android.intent.action.PACKAGE_CHANGED", str, bundle2, 268435456, null, iIntentReceiver, new int[]{UserHandle.getUserId(i)}, iArr2, null, null);
    }

    public final IPackageManager getIPackageManager() {
        return ActivityThread.getPackageManager();
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0192 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setApplicationEnabledSettingWithList(List list, int i, int i2, boolean z, boolean z2, int i3, String str) {
        String[] strArr;
        ArrayList[] arrayListArr;
        int[] iArr;
        boolean[] zArr;
        int handlePendingBroadcastsForBurst;
        int i4;
        int i5;
        ArrayList arrayList;
        if (list == null || list.size() == 0) {
            Log.e("EMPkgHandler", "list of PackageName is invalid");
            return;
        }
        if (!this.mUserManager.exists(i3)) {
            return;
        }
        String num = str == null ? Integer.toString(Binder.getCallingUid()) : str;
        int i6 = (i2 & (-2)) | 4;
        Elog.d("EMPkgHandler", "setApplicationEnabledSettingWithList usePending = " + z + "  Start now = " + z2);
        int[] userIds = this.mUserManager.getUserIds();
        ArrayList arrayList2 = new ArrayList();
        int length = userIds.length;
        int i7 = 0;
        while (i7 < length) {
            int i8 = userIds[i7];
            if (isKnox(i8)) {
                Elog.d("EMPkgHandler", "setApplicationEnabledSettingWithList- skip knox user = " + i8);
                i4 = i7;
                i5 = length;
                arrayList = arrayList2;
            } else {
                Elog.d("EMPkgHandler", "setApplicationEnabledSettingWithList - user id = " + i8);
                arrayList2.clear();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    if (this.mProtectedPackages.isPackageStateProtected(i8, str2)) {
                        Elog.d("EMPkgHandler", "setApplicationEnabledSettingWithList- skip ProtectedPackage = " + str2);
                        it = it;
                    } else {
                        arrayList2.add(str2);
                    }
                }
                i4 = i7;
                i5 = length;
                arrayList = arrayList2;
                try {
                    setEnabledSettingEMPkgHndlr(arrayList2, i, i6, i8, num);
                } catch (RemoteException e) {
                    Elog.d("EMPkgHandler", "setEnabledSettingEMPkgHndlr :  " + e);
                    e.printStackTrace();
                } catch (IllegalArgumentException e2) {
                    Elog.d("EMPkgHandler", "setEnabledSettingEMPkgHndlr :  " + e2);
                    e2.printStackTrace();
                } catch (SecurityException e3) {
                    Elog.d("EMPkgHandler", "setEnabledSettingEMPkgHndlr :  " + e3);
                    e3.printStackTrace();
                }
            }
            i7 = i4 + 1;
            length = i5;
            arrayList2 = arrayList;
        }
        Elog.d("EMPkgHandler", "setApplicationEnabledSettingWithList");
        if (z) {
            if (z2 && !this.mEMPackageHander.hasMessages(1)) {
                Message obtainMessage = this.mEMPackageHander.obtainMessage(1);
                obtainMessage.arg1 = i3;
                this.mEMPackageHander.sendMessage(obtainMessage);
            }
        } else {
            PendingPackageBroadcastsWithList pendingPackageBroadcastsWithList = this.mPendingBroadcastsForBurst;
            if (pendingPackageBroadcastsWithList != null) {
                int size = pendingPackageBroadcastsWithList.size();
                if (size <= 0) {
                    return;
                }
                strArr = new String[size];
                arrayListArr = new ArrayList[size];
                iArr = new int[size];
                zArr = new boolean[size];
                handlePendingBroadcastsForBurst = handlePendingBroadcastsForBurst(strArr, arrayListArr, iArr, zArr, new int[size], size);
                if (z) {
                    for (int i9 = 0; i9 < handlePendingBroadcastsForBurst; i9++) {
                        sendPackageChangedBroadcastEMPkgHndlr(strArr[i9], zArr[i9], arrayListArr[i9], iArr[i9]);
                    }
                    return;
                }
                return;
            }
        }
        strArr = null;
        arrayListArr = null;
        iArr = null;
        zArr = null;
        handlePendingBroadcastsForBurst = 0;
        if (z) {
        }
    }

    public final int handlePendingBroadcastsForBurst(String[] strArr, ArrayList[] arrayListArr, int[] iArr, boolean[] zArr, int[] iArr2, int i) {
        Elog.d("EMPkgHandler", "handlePendingBroadcastsForBurst size[" + i + "]");
        if (i <= 0) {
            return 0;
        }
        Computer snapshotComputer = this.mPkgMgrSvc.snapshotComputer();
        int i2 = 0;
        for (int i3 = 0; i3 < this.mPendingBroadcastsForBurst.userIdCount(); i3++) {
            int userIdAt = this.mPendingBroadcastsForBurst.userIdAt(i3);
            Iterator it = this.mPendingBroadcastsForBurst.packagesForUserId(userIdAt).entrySet().iterator();
            LinkedHashMap packagesNewStateForUserId = this.mPendingBroadcastsForBurst.packagesNewStateForUserId(userIdAt);
            if (packagesNewStateForUserId != null) {
                while (it.hasNext() && i2 < i) {
                    Map.Entry entry = (Map.Entry) it.next();
                    PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal((String) entry.getKey());
                    if (packageStateInternal != null) {
                        strArr[i2] = (String) entry.getKey();
                        arrayListArr[i2] = (ArrayList) entry.getValue();
                        iArr[i2] = UserHandle.getUid(userIdAt, packageStateInternal.getAppId());
                        int intValue = ((Integer) packagesNewStateForUserId.get(strArr[i2])).intValue();
                        iArr2[i2] = intValue;
                        if (intValue == 1) {
                            zArr[i2] = true;
                        } else if (intValue == 0) {
                            zArr[i2] = true;
                        } else {
                            zArr[i2] = false;
                        }
                        i2++;
                    }
                }
            }
        }
        this.mPendingBroadcastsForBurst.clear();
        return i2;
    }

    public int getProgressionOfPackageChanged() {
        EMPackageHandler eMPackageHandler = this.mEMPackageHander;
        if (eMPackageHandler != null) {
            return eMPackageHandler.getProgressionOfPackageChanged();
        }
        return -1;
    }

    public void cancelEMPHandlerSendPendingBroadcast() {
        EMPackageHandler eMPackageHandler = this.mEMPackageHander;
        if (eMPackageHandler != null) {
            if (eMPackageHandler.isCanceled()) {
                Elog.d("EMPkgHandler", "cancelEMHandlerSendPendingBroadcast : Already done");
                this.mEMPackageHander.sendEmptyMessage(4);
            } else {
                this.mEMPackageHander.sendEmptyMessage(5);
            }
        }
    }

    public boolean isKnox(int i) {
        return SemPersonaManager.isKnoxId(i);
    }

    public void setEnabledSettingEMPkgHndlr(ArrayList arrayList, int i, int i2, int i3, String str) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(new PackageManager.ComponentEnabledSetting((String) it.next(), i, i2));
        }
        getIPackageManager().setComponentEnabledSettings(arrayList2, i3, str);
    }

    public void sendPackageChangedBroadcastEMPkgHndlr(String str, boolean z, ArrayList arrayList, int i) {
        this.mPkgMgrSvc.sendPackageChangedBroadcast(this.mPkgMgrSvc.snapshotComputer(), str, z, arrayList, i, "EmergencyMode");
    }
}
