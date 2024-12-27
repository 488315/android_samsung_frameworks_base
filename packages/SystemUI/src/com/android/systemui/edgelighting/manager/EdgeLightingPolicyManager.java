package com.android.systemui.edgelighting.manager;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import com.android.systemui.edgelighting.data.policy.PolicyClientContract;
import com.android.systemui.edgelighting.data.policy.PolicyInfo;
import com.samsung.android.edge.EdgeLightingPolicy;
import com.samsung.android.edge.EdgeLightingPolicyInfo;
import com.samsung.android.edge.SemEdgeManager;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public final class EdgeLightingPolicyManager {
    public static final Uri EL_POLICY_ITEM_URI = Uri.withAppendedPath(PolicyClientContract.PolicyItems.CONTENT_URI, "EdgeLighting");
    public static final String[] POLICY_ITEM_PROJECTION = {"item", "category", "data1", "data2", "data3"};
    public static EdgeLightingPolicyManager mInstance;
    public final int mPolicyType;
    public long mPolicyVersion;
    public final SparseArray mPolicyInfoData = new SparseArray();
    public final AnonymousClass1 mCategoryComparator = new Comparator(this) { // from class: com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager.1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((PolicyInfo) obj).category - ((PolicyInfo) obj2).category;
        }
    };

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private EdgeLightingPolicyManager(android.content.Context r23, boolean r24) {
        /*
            Method dump skipped, instructions count: 865
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager.<init>(android.content.Context, boolean):void");
    }

    public static PolicyInfo createPolicyInfo(String str, String str2, String str3, String str4, String str5) {
        int i;
        int parseInt;
        int i2;
        int parseInt2;
        int i3;
        int i4;
        int i5 = 1;
        if (str2 != null) {
            try {
                parseInt = Integer.parseInt(str2.trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                i = 1;
            }
        } else {
            parseInt = 1;
        }
        i = parseInt;
        boolean z = false;
        int i6 = -11761985;
        if (i != 1 && i != 2) {
            if (i != 10) {
                switch (i) {
                    case 21:
                    case 22:
                    case 23:
                        return new PolicyInfo(str, i);
                    default:
                        Slog.w("ELPolicyManager", "createPolicyInfo : wrong category = " + i);
                        return null;
                }
            }
            if (str3 != null) {
                try {
                    i5 = Integer.parseInt(str3.trim());
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                }
            }
            z = str4 != null ? Boolean.parseBoolean(str4.trim()) : false;
            if (str5 != null) {
                i6 = Integer.parseInt(str5.trim());
            }
            return new PolicyInfo(str, i, i5, z, i6);
        }
        if (str3 != null) {
            try {
                parseInt2 = Integer.parseInt(str3.trim());
            } catch (NumberFormatException e3) {
                e = e3;
                i2 = 0;
                e.printStackTrace();
                i3 = 0;
                i4 = i2;
                return new PolicyInfo(str, i, i4, i6, i3);
            }
        } else {
            parseInt2 = 0;
        }
        if (str4 != null) {
            try {
                i6 = Integer.parseInt(str4.trim());
            } catch (NumberFormatException e4) {
                i2 = parseInt2;
                e = e4;
                e.printStackTrace();
                i3 = 0;
                i4 = i2;
                return new PolicyInfo(str, i, i4, i6, i3);
            }
        }
        i3 = str5 != null ? Integer.parseInt(str5.trim()) : 0;
        i4 = parseInt2;
        return new PolicyInfo(str, i, i4, i6, i3);
    }

    public static EdgeLightingPolicyManager getInstance(Context context, boolean z) {
        if (mInstance == null) {
            mInstance = new EdgeLightingPolicyManager(context, z);
        }
        return mInstance;
    }

    public static long getJsonVersion(StringBuilder sb) {
        try {
            return new JSONObject(sb.toString()).getLong("policy_version");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getEdgeLightingColor(android.content.Context r9, java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 247
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager.getEdgeLightingColor(android.content.Context, java.lang.String):int");
    }

    public final void updateEdgeLightingPolicy(Context context, boolean z) {
        HashMap hashMap;
        SemEdgeManager semEdgeManager = (SemEdgeManager) context.getSystemService("edge");
        if (semEdgeManager == null) {
            return;
        }
        int i = this.mPolicyType;
        int i2 = z ? i | 1 : i & (-2);
        EdgeLightingPolicy edgeLightingPolicy = new EdgeLightingPolicy();
        edgeLightingPolicy.setPolicyType(i2);
        edgeLightingPolicy.setPolicyVersion(this.mPolicyVersion);
        HashMap hashMap2 = (HashMap) this.mPolicyInfoData.get(1);
        if (hashMap2 != null) {
            Iterator it = hashMap2.entrySet().iterator();
            while (it.hasNext()) {
                PolicyInfo policyInfo = (PolicyInfo) ((Map.Entry) it.next()).getValue();
                edgeLightingPolicy.addEdgeLightingPolicyInfo(new EdgeLightingPolicyInfo(policyInfo.item, 1, policyInfo.range));
            }
        }
        if ((this.mPolicyType & 4) != 0 && (hashMap = (HashMap) this.mPolicyInfoData.get(2)) != null) {
            Iterator it2 = hashMap.entrySet().iterator();
            while (it2.hasNext()) {
                PolicyInfo policyInfo2 = (PolicyInfo) ((Map.Entry) it2.next()).getValue();
                edgeLightingPolicy.addEdgeLightingPolicyInfo(new EdgeLightingPolicyInfo(policyInfo2.item, 2, policyInfo2.range));
            }
        }
        HashMap hashMap3 = (HashMap) this.mPolicyInfoData.get(10);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : hashMap3.entrySet()) {
            if (entry.getValue() != null && ((PolicyInfo) entry.getValue()).item != null) {
                sb.append(((PolicyInfo) entry.getValue()).item);
                sb.append(",");
            }
        }
        Settings.Secure.putString(context.getContentResolver(), "edge_lighting_recommend_app_list", sb.toString());
        Slog.i("ELPolicyManager", " update Policy : " + edgeLightingPolicy.getEdgeLightingPolicyInfoList().size());
        semEdgeManager.updateEdgeLightingPolicy(edgeLightingPolicy);
    }
}
