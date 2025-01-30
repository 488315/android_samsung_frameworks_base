package com.android.server.companion;

import android.companion.AssociationInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.infra.PerUser;
import com.android.server.companion.CompanionDeviceServiceConnector;
import com.android.server.companion.presence.CompanionDevicePresenceMonitor;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class CompanionApplicationController {
    public final AssociationStore mAssociationStore;
    public final Context mContext;
    public final CompanionDevicePresenceMonitor mDevicePresenceMonitor;
    public final CompanionServicesRegister mCompanionServicesRegister = new CompanionServicesRegister();
    public final AndroidPackageMap mBoundCompanionApplications = new AndroidPackageMap();
    public final AndroidPackageMap mScheduledForRebindingCompanionApplications = new AndroidPackageMap();

    /* JADX WARN: Multi-variable type inference failed */
    public CompanionApplicationController(Context context, AssociationStore associationStore, CompanionDevicePresenceMonitor companionDevicePresenceMonitor) {
        this.mContext = context;
        this.mAssociationStore = associationStore;
        this.mDevicePresenceMonitor = companionDevicePresenceMonitor;
    }

    public void onPackagesChanged(int i) {
        this.mCompanionServicesRegister.invalidate(i);
    }

    public void bindCompanionApplication(int i, String str, boolean z) {
        List forPackage = this.mCompanionServicesRegister.forPackage(i, str);
        if (forPackage.isEmpty()) {
            Slog.w("CDM_CompanionApplicationController", "Can not bind companion applications u" + i + "/" + str + ": eligible CompanionDeviceService not found.\nA CompanionDeviceService should declare an intent-filter for \"android.companion.CompanionDeviceService\" action and require \"android.permission.BIND_COMPANION_DEVICE_SERVICE\" permission.");
            return;
        }
        ArrayList arrayList = new ArrayList();
        synchronized (this.mBoundCompanionApplications) {
            if (this.mBoundCompanionApplications.containsValueForPackage(i, str)) {
                return;
            }
            int i2 = 0;
            while (i2 < forPackage.size()) {
                arrayList.add(CompanionDeviceServiceConnector.newInstance(this.mContext, i, (ComponentName) forPackage.get(i2), z, i2 == 0));
                i2++;
            }
            this.mBoundCompanionApplications.setValueForPackage(i, str, arrayList);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((CompanionDeviceServiceConnector) it.next()).setListener(new CompanionDeviceServiceConnector.Listener() { // from class: com.android.server.companion.CompanionApplicationController$$ExternalSyntheticLambda0
                    @Override // com.android.server.companion.CompanionDeviceServiceConnector.Listener
                    public final void onBindingDied(int i3, String str2, CompanionDeviceServiceConnector companionDeviceServiceConnector) {
                        CompanionApplicationController.this.onBinderDied(i3, str2, companionDeviceServiceConnector);
                    }
                });
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((CompanionDeviceServiceConnector) it2.next()).connect();
            }
        }
    }

    public void unbindCompanionApplication(int i, String str) {
        List list;
        synchronized (this.mBoundCompanionApplications) {
            list = (List) this.mBoundCompanionApplications.removePackage(i, str);
        }
        synchronized (this.mScheduledForRebindingCompanionApplications) {
            this.mScheduledForRebindingCompanionApplications.removePackage(i, str);
        }
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((CompanionDeviceServiceConnector) it.next()).postUnbind();
        }
    }

    public boolean isCompanionApplicationBound(int i, String str) {
        boolean containsValueForPackage;
        synchronized (this.mBoundCompanionApplications) {
            containsValueForPackage = this.mBoundCompanionApplications.containsValueForPackage(i, str);
        }
        return containsValueForPackage;
    }

    public final void scheduleRebinding(final int i, final String str, final CompanionDeviceServiceConnector companionDeviceServiceConnector) {
        Slog.i("CDM_CompanionApplicationController", "scheduleRebinding() " + i + "/" + str);
        if (isRebindingCompanionApplicationScheduled(i, str)) {
            return;
        }
        if (companionDeviceServiceConnector.isPrimary()) {
            synchronized (this.mScheduledForRebindingCompanionApplications) {
                this.mScheduledForRebindingCompanionApplications.setValueForPackage(i, str, Boolean.TRUE);
            }
        }
        Handler.getMain().postDelayed(new Runnable() { // from class: com.android.server.companion.CompanionApplicationController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CompanionApplicationController.this.lambda$scheduleRebinding$0(i, str, companionDeviceServiceConnector);
            }
        }, 10000L);
    }

    public final boolean isRebindingCompanionApplicationScheduled(int i, String str) {
        boolean containsValueForPackage;
        synchronized (this.mScheduledForRebindingCompanionApplications) {
            containsValueForPackage = this.mScheduledForRebindingCompanionApplications.containsValueForPackage(i, str);
        }
        return containsValueForPackage;
    }

    /* renamed from: onRebindingCompanionApplicationTimeout, reason: merged with bridge method [inline-methods] */
    public final void lambda$scheduleRebinding$0(int i, String str, CompanionDeviceServiceConnector companionDeviceServiceConnector) {
        if (companionDeviceServiceConnector.isPrimary()) {
            synchronized (this.mBoundCompanionApplications) {
                if (!this.mBoundCompanionApplications.containsValueForPackage(i, str)) {
                    this.mBoundCompanionApplications.setValueForPackage(i, str, Collections.singletonList(companionDeviceServiceConnector));
                }
            }
            synchronized (this.mScheduledForRebindingCompanionApplications) {
                this.mScheduledForRebindingCompanionApplications.removePackage(i, str);
            }
        }
        companionDeviceServiceConnector.connect();
    }

    public void notifyCompanionApplicationDeviceAppeared(AssociationInfo associationInfo) {
        int userId = associationInfo.getUserId();
        String packageName = associationInfo.getPackageName();
        Log.i("CDM_CompanionApplicationController", "notifyDevice_Appeared() id=" + associationInfo.getId() + " u" + userId + "/" + packageName);
        CompanionDeviceServiceConnector primaryServiceConnector = getPrimaryServiceConnector(userId, packageName);
        if (primaryServiceConnector == null) {
            Log.e("CDM_CompanionApplicationController", "notify_CompanionApplicationDevice_Appeared(): u" + userId + "/" + packageName + " is NOT bound.");
            Log.d("CDM_CompanionApplicationController", "Stacktrace", new Throwable());
            return;
        }
        Log.i("CDM_CompanionApplicationController", "Calling onDeviceAppeared to userId=[" + userId + "] package=[" + packageName + "] associationId=[" + associationInfo.getId() + "]");
        primaryServiceConnector.postOnDeviceAppeared(associationInfo);
    }

    public void notifyCompanionApplicationDeviceDisappeared(AssociationInfo associationInfo) {
        int userId = associationInfo.getUserId();
        String packageName = associationInfo.getPackageName();
        Log.i("CDM_CompanionApplicationController", "notifyDevice_Disappeared() id=" + associationInfo.getId() + " u" + userId + "/" + packageName);
        CompanionDeviceServiceConnector primaryServiceConnector = getPrimaryServiceConnector(userId, packageName);
        if (primaryServiceConnector == null) {
            Log.e("CDM_CompanionApplicationController", "notify_CompanionApplicationDevice_Disappeared(): u" + userId + "/" + packageName + " is NOT bound.");
            Log.d("CDM_CompanionApplicationController", "Stacktrace", new Throwable());
            return;
        }
        Log.i("CDM_CompanionApplicationController", "Calling onDeviceDisappeared to userId=[" + userId + "] package=[" + packageName + "] associationId=[" + associationInfo.getId() + "]");
        primaryServiceConnector.postOnDeviceDisappeared(associationInfo);
    }

    public void dump(PrintWriter printWriter) {
        printWriter.append("Companion Device Application Controller: \n");
        synchronized (this.mBoundCompanionApplications) {
            printWriter.append("  Bound Companion Applications: ");
            if (this.mBoundCompanionApplications.size() == 0) {
                printWriter.append("<empty>\n");
            } else {
                printWriter.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                this.mBoundCompanionApplications.dump(printWriter);
            }
        }
        printWriter.append("  Companion Applications Scheduled For Rebinding: ");
        if (this.mScheduledForRebindingCompanionApplications.size() == 0) {
            printWriter.append("<empty>\n");
        } else {
            printWriter.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            this.mScheduledForRebindingCompanionApplications.dump(printWriter);
        }
    }

    public final void onBinderDied(int i, String str, CompanionDeviceServiceConnector companionDeviceServiceConnector) {
        boolean isPrimary = companionDeviceServiceConnector.isPrimary();
        Slog.i("CDM_CompanionApplicationController", "onBinderDied() u" + i + "/" + str + " isPrimary: " + isPrimary);
        synchronized (this.mBoundCompanionApplications) {
            if (companionDeviceServiceConnector.isPrimary()) {
                this.mBoundCompanionApplications.removePackage(i, str);
            }
        }
        if (shouldScheduleRebind(i, str, isPrimary)) {
            scheduleRebinding(i, str, companionDeviceServiceConnector);
        }
    }

    public final CompanionDeviceServiceConnector getPrimaryServiceConnector(int i, String str) {
        List list;
        synchronized (this.mBoundCompanionApplications) {
            list = (List) this.mBoundCompanionApplications.getValueForPackage(i, str);
        }
        if (list != null) {
            return (CompanionDeviceServiceConnector) list.get(0);
        }
        return null;
    }

    public final boolean shouldScheduleRebind(int i, String str, boolean z) {
        boolean z2 = false;
        boolean z3 = false;
        for (AssociationInfo associationInfo : this.mAssociationStore.getAssociationsForPackage(i, str)) {
            int id = associationInfo.getId();
            if (associationInfo.isSelfManaged()) {
                if (z && this.mDevicePresenceMonitor.isDevicePresent(id)) {
                    this.mDevicePresenceMonitor.onSelfManagedDeviceReporterBinderDied(id);
                }
                z3 = isCompanionApplicationBound(i, str);
            } else if (associationInfo.isNotifyOnDeviceNearby()) {
                z3 = true;
            }
            z2 = true;
        }
        return z2 && z3;
    }

    public class CompanionServicesRegister extends PerUser {
        public CompanionServicesRegister() {
        }

        public synchronized Map forUser(int i) {
            return (Map) super.forUser(i);
        }

        public synchronized List forPackage(int i, String str) {
            return (List) forUser(i).getOrDefault(str, Collections.emptyList());
        }

        public synchronized void invalidate(int i) {
            remove(i);
        }

        public final Map create(int i) {
            return PackageUtils.getCompanionServicesForUser(CompanionApplicationController.this.mContext, i);
        }
    }

    public class AndroidPackageMap extends SparseArray {
        public AndroidPackageMap() {
        }

        public void setValueForPackage(int i, String str, Object obj) {
            Map map = (Map) get(i);
            if (map == null) {
                map = new HashMap();
                put(i, map);
            }
            map.put(str, obj);
        }

        public boolean containsValueForPackage(int i, String str) {
            Map map = (Map) get(i);
            return map != null && map.containsKey(str);
        }

        public Object getValueForPackage(int i, String str) {
            Map map = (Map) get(i);
            if (map != null) {
                return map.get(str);
            }
            return null;
        }

        public Object removePackage(int i, String str) {
            Map map = (Map) get(i);
            if (map == null) {
                return null;
            }
            return map.remove(str);
        }

        public final void dump(PrintWriter printWriter) {
            for (int i = 0; i < size(); i++) {
                int keyAt = keyAt(i);
                Map map = (Map) get(keyAt);
                if (map.isEmpty()) {
                    printWriter.append("    u").append((CharSequence) String.valueOf(keyAt)).append(": <empty>\n");
                }
                for (Map.Entry entry : map.entrySet()) {
                    printWriter.append("    u").append((CharSequence) String.valueOf(keyAt)).append("\\").append((CharSequence) entry.getKey()).append(" -> ").append((CharSequence) entry.getValue().toString()).append('\n');
                }
            }
        }
    }
}
