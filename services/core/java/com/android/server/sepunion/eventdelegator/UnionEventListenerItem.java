package com.android.server.sepunion.eventdelegator;

import android.app.PendingIntent;
import android.content.Intent;

import com.android.server.sepunion.SemDeviceInfoManagerService;

import com.samsung.android.sepunion.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UnionEventListenerItem {
    public static final String TAG;
    public final HashMap mUnionEventComponentsWithConditions = new HashMap();

    static {
        int i = SemDeviceInfoManagerService.$r8$clinit;
        TAG = "SemDeviceInfoManagerService";
    }

    public final boolean add(String str, PendingIntentWithConditions pendingIntentWithConditions) {
        ArrayList arrayList = (ArrayList) this.mUnionEventComponentsWithConditions.get(str);
        String str2 = TAG;
        if (arrayList == null) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(pendingIntentWithConditions);
            Log.d(str2, "PendingIntentWithConditions added f=" + pendingIntentWithConditions.mFlag);
            this.mUnionEventComponentsWithConditions.put(str, arrayList2);
            return true;
        }
        Intent intent = pendingIntentWithConditions.mPendingIntent.getIntent();
        Iterator it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PendingIntentWithConditions pendingIntentWithConditions2 =
                    (PendingIntentWithConditions) it.next();
            if (pendingIntentWithConditions2.mPendingIntent.getIntent().filterEquals(intent)) {
                if (pendingIntentWithConditions2.mPendingIntent.getTarget()
                        == pendingIntentWithConditions.mPendingIntent.getTarget()) {
                    Log.d(str2, "Same PendingIntent is in " + str);
                    return false;
                }
                if (!arrayList.remove(pendingIntentWithConditions2)) {
                    return false;
                }
                Log.d(str2, "removed before add");
            }
        }
        Log.d(str2, "PendingIntentWithConditions replaced f=" + pendingIntentWithConditions.mFlag);
        arrayList.add(pendingIntentWithConditions);
        return true;
    }

    public final void remove(String str, PendingIntent pendingIntent) {
        PendingIntentWithConditions pendingIntentWithConditions =
                new PendingIntentWithConditions(pendingIntent, 0, null);
        ArrayList arrayList = (ArrayList) this.mUnionEventComponentsWithConditions.get(str);
        if (arrayList == null) {
            Log.d(TAG, "No item for the calling package in the component list.");
            return;
        }
        if (arrayList.size() > 0) {
            arrayList.remove(pendingIntentWithConditions);
        }
        if (arrayList.size() == 0) {
            this.mUnionEventComponentsWithConditions.remove(str);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Number of registered components = ");
        sb.append(this.mUnionEventComponentsWithConditions.size());
        sb.append("\n");
        for (Map.Entry entry : this.mUnionEventComponentsWithConditions.entrySet()) {
            String str = (String) entry.getKey();
            ArrayList arrayList = (ArrayList) entry.getValue();
            sb.append("  - Package : ");
            sb.append(str);
            sb.append("\n  - Number of PendingIntentWithConditions = ");
            sb.append(arrayList.size());
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                PendingIntentWithConditions pendingIntentWithConditions =
                        (PendingIntentWithConditions) it.next();
                sb.append("\n     - ");
                sb.append(pendingIntentWithConditions.mPendingIntent + ", ");
                sb.append(pendingIntentWithConditions.mFlag + ", ");
                sb.append(pendingIntentWithConditions.mConditions);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public final String toStringForDump() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : this.mUnionEventComponentsWithConditions.entrySet()) {
            String str = (String) entry.getKey();
            ArrayList arrayList = (ArrayList) entry.getValue();
            sb.append("        Package: ");
            sb.append(str + " (" + arrayList.size() + ")");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                PendingIntentWithConditions pendingIntentWithConditions =
                        (PendingIntentWithConditions) it.next();
                sb.append("\n            ");
                sb.append(pendingIntentWithConditions.mPendingIntent + ", ");
                sb.append(pendingIntentWithConditions.mFlag + ", ");
                sb.append(pendingIntentWithConditions.mConditions);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
