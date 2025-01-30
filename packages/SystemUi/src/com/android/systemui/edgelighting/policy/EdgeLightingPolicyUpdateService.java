package com.android.systemui.edgelighting.policy;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Slog;
import android.util.SparseArray;
import com.android.systemui.edgelighting.data.policy.PolicyClientContract;
import com.android.systemui.edgelighting.data.policy.PolicyInfo;
import com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager;
import com.android.systemui.edgelighting.manager.EdgeLightingSettingManager;
import com.android.systemui.edgelighting.manager.PolicyJSONManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class EdgeLightingPolicyUpdateService extends IntentService {
    public EdgeLightingPolicyUpdateService() {
        super("EdgeLightingPolicyUpdateService");
    }

    public static void startActionUpdate(Context context) {
        Slog.d("ELPolicyUpdateService", "startActionUpdate");
        Intent intent = new Intent(context, (Class<?>) EdgeLightingPolicyUpdateService.class);
        intent.setAction("com.android.systemui.edgelighting.action.UPDATE_POLICY");
        context.startService(intent);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
    @Override // android.app.IntentService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onHandleIntent(Intent intent) {
        long j;
        if (intent == null || !"com.android.systemui.edgelighting.action.UPDATE_POLICY".equals(intent.getAction())) {
            return;
        }
        EdgeLightingPolicyManager edgeLightingPolicyManager = EdgeLightingPolicyManager.getInstance(this, true);
        edgeLightingPolicyManager.getClass();
        boolean z = getPackageManager().resolveContentProvider("com.samsung.android.sm.policy", 0) != null;
        Slog.d("ELPolicyManager", "updateEdgeLightingServerPolicy : isSCPMClientExist = " + z);
        if (!z) {
            return;
        }
        Cursor query = getContentResolver().query(PolicyClientContract.PolicyList.CONTENT_URI, new String[]{"policyVersion"}, "policyName=?", new String[]{"EdgeLighting"}, null);
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    j = query.getLong(0);
                    if (query != null) {
                        query.close();
                    }
                    Slog.d("PolicyVersion", "Server version : " + j);
                    if (j <= edgeLightingPolicyManager.mPolicyVersion) {
                        ContentResolver contentResolver = getContentResolver();
                        ArrayList arrayList = new ArrayList();
                        query = contentResolver.query(EdgeLightingPolicyManager.EL_POLICY_ITEM_URI, EdgeLightingPolicyManager.POLICY_ITEM_PROJECTION, null, null, "category");
                        if (query != null) {
                            while (query.moveToNext()) {
                                try {
                                    PolicyInfo createPolicyInfo = EdgeLightingPolicyManager.createPolicyInfo(query.getString(0), query.getString(1), query.getString(2), query.getString(3), query.getString(4));
                                    if (createPolicyInfo != null) {
                                        arrayList.add(createPolicyInfo);
                                    }
                                } finally {
                                }
                            }
                        }
                        if (query != null) {
                            query.close();
                        }
                        boolean isEmpty = arrayList.isEmpty();
                        SparseArray sparseArray = edgeLightingPolicyManager.mPolicyInfoData;
                        if (!isEmpty) {
                            Collections.sort(arrayList, edgeLightingPolicyManager.mCategoryComparator);
                            edgeLightingPolicyManager.mPolicyVersion = j;
                            HashMap hashMap = (HashMap) sparseArray.get(1);
                            if (hashMap == null) {
                                hashMap = new HashMap();
                            }
                            Iterator it = arrayList.iterator();
                            int i = 1;
                            while (it.hasNext()) {
                                PolicyInfo policyInfo = (PolicyInfo) it.next();
                                int i2 = policyInfo.category;
                                switch (i2) {
                                    case 21:
                                        i2 = 1;
                                        break;
                                    case 22:
                                        i2 = 2;
                                        break;
                                    case 23:
                                        i2 = 10;
                                        break;
                                }
                                if (i != i2) {
                                    sparseArray.put(i, hashMap);
                                    hashMap = (HashMap) sparseArray.get(i2);
                                    if (hashMap == null) {
                                        hashMap = new HashMap();
                                    }
                                    i = i2;
                                }
                                int i3 = policyInfo.category;
                                String str = policyInfo.item;
                                switch (i3) {
                                    case 21:
                                    case 22:
                                    case 23:
                                        hashMap.remove(str);
                                        break;
                                    default:
                                        hashMap.put(str, policyInfo);
                                        break;
                                }
                            }
                            sparseArray.put(i, hashMap);
                            EdgeLightingSettingManager edgeLightingSettingManager = EdgeLightingSettingManager.getInstance(this);
                            edgeLightingSettingManager.removeBlockListInEnabledEdgeLightingList(this, (HashMap) sparseArray.get(2));
                            edgeLightingPolicyManager.updateEdgeLightingPolicy(this, edgeLightingSettingManager.mAllApplication);
                        }
                        PolicyJSONManager.writeJson(this, edgeLightingPolicyManager.mPolicyVersion, edgeLightingPolicyManager.mPolicyType, sparseArray);
                        return;
                    }
                    return;
                }
            } finally {
            }
        }
        j = 0;
        if (query != null) {
        }
        Slog.d("PolicyVersion", "Server version : " + j);
        if (j <= edgeLightingPolicyManager.mPolicyVersion) {
        }
    }
}
