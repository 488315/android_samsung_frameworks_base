package com.android.server.sepunion.eventdelegator;

import android.app.PendingIntent;
import android.content.Intent;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.sepunion.SemDeviceInfoManagerService;
import com.samsung.android.sepunion.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public class UnionEventListenerItem {
    public static final String TAG = SemDeviceInfoManagerService.TAG;
    public final HashMap mUnionEventComponentsWithConditions = new HashMap();

    public boolean isEmpty() {
        return this.mUnionEventComponentsWithConditions.isEmpty();
    }

    public boolean add(String str, PendingIntent pendingIntent) {
        return add(str, new PendingIntentWithConditions(pendingIntent, 0, null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0094, code lost:
    
        r6 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean add(String str, PendingIntentWithConditions pendingIntentWithConditions) {
        boolean z;
        ArrayList arrayList = (ArrayList) this.mUnionEventComponentsWithConditions.get(str);
        if (arrayList == null) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(pendingIntentWithConditions);
            Log.d(TAG, "PendingIntentWithConditions added f=" + pendingIntentWithConditions.getFlag());
            this.mUnionEventComponentsWithConditions.put(str, arrayList2);
            return true;
        }
        Intent intent = pendingIntentWithConditions.getPendingIntent().getIntent();
        Iterator it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PendingIntentWithConditions pendingIntentWithConditions2 = (PendingIntentWithConditions) it.next();
            if (pendingIntentWithConditions2.getPendingIntent().getIntent().filterEquals(intent)) {
                if (pendingIntentWithConditions2.getPendingIntent().getTarget() == pendingIntentWithConditions.getPendingIntent().getTarget()) {
                    Log.d(TAG, "Same PendingIntent is in " + str);
                    return false;
                }
                if (arrayList.remove(pendingIntentWithConditions2)) {
                    Log.d(TAG, "removed before add");
                } else {
                    z = false;
                }
            }
        }
        if (!z) {
            return false;
        }
        Log.d(TAG, "PendingIntentWithConditions replaced f=" + pendingIntentWithConditions.getFlag());
        arrayList.add(pendingIntentWithConditions);
        return true;
    }

    public boolean remove(String str, PendingIntent pendingIntent) {
        return remove(str, new PendingIntentWithConditions(pendingIntent, 0, null));
    }

    public boolean remove(String str, PendingIntentWithConditions pendingIntentWithConditions) {
        if (pendingIntentWithConditions == null) {
            Log.d(TAG, "pending intent is null");
            return false;
        }
        ArrayList arrayList = (ArrayList) this.mUnionEventComponentsWithConditions.get(str);
        if (arrayList == null) {
            Log.d(TAG, "No item for the calling package in the component list.");
            return false;
        }
        boolean remove = arrayList.size() > 0 ? arrayList.remove(pendingIntentWithConditions) : false;
        if (arrayList.size() == 0) {
            this.mUnionEventComponentsWithConditions.remove(str);
        }
        return remove;
    }

    public boolean clear(String str) {
        return ((ArrayList) this.mUnionEventComponentsWithConditions.remove(str)) != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of registered components = ");
        sb.append(this.mUnionEventComponentsWithConditions.size());
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        for (Map.Entry entry : this.mUnionEventComponentsWithConditions.entrySet()) {
            String str = (String) entry.getKey();
            ArrayList arrayList = (ArrayList) entry.getValue();
            sb.append("  - Package : ");
            sb.append(str);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            sb.append("  - Number of PendingIntentWithConditions = ");
            sb.append(arrayList.size());
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                PendingIntentWithConditions pendingIntentWithConditions = (PendingIntentWithConditions) it.next();
                sb.append("\n     - ");
                sb.append(pendingIntentWithConditions.getPendingIntent() + ", ");
                sb.append(pendingIntentWithConditions.getFlag() + ", ");
                sb.append(pendingIntentWithConditions.getConditions());
            }
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        return sb.toString();
    }

    public String toStringForDump(String str) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : this.mUnionEventComponentsWithConditions.entrySet()) {
            String str2 = (String) entry.getKey();
            ArrayList arrayList = (ArrayList) entry.getValue();
            sb.append(str + "  Package: ");
            sb.append(str2 + " (" + arrayList.size() + ")");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                PendingIntentWithConditions pendingIntentWithConditions = (PendingIntentWithConditions) it.next();
                sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                sb.append(str + "      ");
                StringBuilder sb2 = new StringBuilder();
                sb2.append(pendingIntentWithConditions.getPendingIntent());
                sb2.append(", ");
                sb.append(sb2.toString());
                sb.append(pendingIntentWithConditions.getFlag() + ", ");
                sb.append(pendingIntentWithConditions.getConditions());
            }
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        return sb.toString();
    }
}
