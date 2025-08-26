package com.android.systemui.edgelighting.policy;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Slog;
import com.android.systemui.edgelighting.data.policy.PolicyClientContract;
import com.android.systemui.edgelighting.data.policy.PolicyInfo;
import com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager;
import com.android.systemui.edgelighting.manager.EdgeLightingSettingManager;
import com.android.systemui.edgelighting.manager.PolicyJSONManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: classes2.dex */
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

    /* JADX WARN: Removed duplicated region for block: B:24:0x006e  */
    @Override // android.app.IntentService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onHandleIntent(Intent intent) throws Throwable {
        long j;
        if (intent == null || !"com.android.systemui.edgelighting.action.UPDATE_POLICY".equals(intent.getAction())) {
            return;
        }
        EdgeLightingPolicyManager edgeLightingPolicyManager = EdgeLightingPolicyManager.getInstance(this, true);
        edgeLightingPolicyManager.getClass();
        int i = 0;
        boolean z = getPackageManager().resolveContentProvider("com.samsung.android.sm.policy", 0) != null;
        Slog.d("ELPolicyManager", "updateEdgeLightingServerPolicy : isSCPMClientExist = " + z);
        if (z) {
            Cursor cursorQuery = getContentResolver().query(PolicyClientContract.PolicyList.CONTENT_URI, new String[]{"policyVersion"}, "policyName=?", new String[]{"EdgeLighting"}, null);
            if (cursorQuery != null) {
                try {
                    j = cursorQuery.moveToFirst() ? cursorQuery.getLong(0) : 0L;
                } finally {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            Slog.d("PolicyVersion", "Server version : " + j);
            if (j > edgeLightingPolicyManager.mPolicyVersion) {
                ContentResolver contentResolver = getContentResolver();
                ArrayList arrayList = new ArrayList();
                cursorQuery = contentResolver.query(EdgeLightingPolicyManager.EL_POLICY_ITEM_URI, EdgeLightingPolicyManager.POLICY_ITEM_PROJECTION, null, null, "category");
                if (cursorQuery != null) {
                    while (cursorQuery.moveToNext()) {
                        try {
                            PolicyInfo policyInfoCreatePolicyInfo = EdgeLightingPolicyManager.createPolicyInfo(cursorQuery.getString(0), cursorQuery.getString(1), cursorQuery.getString(2), cursorQuery.getString(3), cursorQuery.getString(4));
                            if (policyInfoCreatePolicyInfo != null) {
                                arrayList.add(policyInfoCreatePolicyInfo);
                            }
                        } finally {
                        }
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                if (!arrayList.isEmpty()) {
                    Collections.sort(arrayList, edgeLightingPolicyManager.mCategoryComparator);
                    edgeLightingPolicyManager.mPolicyVersion = j;
                    HashMap map = (HashMap) edgeLightingPolicyManager.mPolicyInfoData.get(1);
                    if (map == null) {
                        map = new HashMap();
                    }
                    int size = arrayList.size();
                    int i2 = 1;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        PolicyInfo policyInfo = (PolicyInfo) obj;
                        int i3 = policyInfo.category;
                        switch (i3) {
                            case 21:
                                i3 = 1;
                                break;
                            case 22:
                                i3 = 2;
                                break;
                            case 23:
                                i3 = 10;
                                break;
                        }
                        if (i2 != i3) {
                            edgeLightingPolicyManager.mPolicyInfoData.put(i2, map);
                            map = (HashMap) edgeLightingPolicyManager.mPolicyInfoData.get(i3);
                            if (map == null) {
                                map = new HashMap();
                            }
                            i2 = i3;
                        }
                        int i4 = policyInfo.category;
                        String str = policyInfo.item;
                        switch (i4) {
                            case 21:
                            case 22:
                            case 23:
                                map.remove(str);
                                break;
                            default:
                                map.put(str, policyInfo);
                                break;
                        }
                    }
                    edgeLightingPolicyManager.mPolicyInfoData.put(i2, map);
                    EdgeLightingSettingManager edgeLightingSettingManager = EdgeLightingSettingManager.getInstance(this);
                    edgeLightingSettingManager.removeBlockListInEnabledEdgeLightingList(this, (HashMap) edgeLightingPolicyManager.mPolicyInfoData.get(2));
                    edgeLightingPolicyManager.updateEdgeLightingPolicy(this, edgeLightingSettingManager.mAllApplication);
                }
                PolicyJSONManager.writeJson(this, edgeLightingPolicyManager.mPolicyVersion, edgeLightingPolicyManager.mPolicyType, edgeLightingPolicyManager.mPolicyInfoData);
            }
        }
    }
}
