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

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x011c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x033c A[Catch: all -> 0x0335, IOException -> 0x0338, TRY_LEAVE, TryCatch #2 {IOException -> 0x0338, blocks: (B:202:0x0331, B:173:0x033c), top: B:201:0x0331, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x035d A[Catch: IOException -> 0x0349, TRY_ENTER, TRY_LEAVE, TryCatch #8 {IOException -> 0x0349, blocks: (B:198:0x035d, B:179:0x0345, B:202:0x0331, B:173:0x033c), top: B:201:0x0331, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:200:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0331 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:268:0x00c8 A[Catch: all -> 0x00c1, IOException -> 0x00c4, TRY_LEAVE, TryCatch #13 {IOException -> 0x00c4, blocks: (B:295:0x00bd, B:268:0x00c8), top: B:294:0x00bd, outer: #17 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x010e A[Catch: all -> 0x0112, IOException -> 0x0117, LOOP:1: B:23:0x0108->B:26:0x010e, LOOP_END, TRY_LEAVE, TryCatch #36 {IOException -> 0x0117, all -> 0x0112, blocks: (B:24:0x0108, B:26:0x010e), top: B:23:0x0108 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x011a A[EDGE_INSN: B:27:0x011a->B:28:0x011a BREAK  A[LOOP:1: B:23:0x0108->B:26:0x010e], EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:292:0x00e9 A[Catch: IOException -> 0x00d5, TRY_ENTER, TRY_LEAVE, TryCatch #20 {IOException -> 0x00d5, blocks: (B:292:0x00e9, B:274:0x00d1), top: B:265:0x00bb }] */
    /* JADX WARN: Removed duplicated region for block: B:293:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:294:0x00bd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01b0 A[Catch: JSONException -> 0x01b7, TRY_LEAVE, TryCatch #12 {JSONException -> 0x01b7, blocks: (B:39:0x0199, B:41:0x01b0), top: B:38:0x0199 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01e5 A[Catch: JSONException -> 0x0200, TryCatch #7 {JSONException -> 0x0200, blocks: (B:45:0x01c5, B:47:0x01e5, B:49:0x01f9, B:50:0x0204, B:52:0x020a, B:53:0x0214, B:55:0x021a, B:56:0x0223, B:58:0x0230, B:60:0x0241, B:63:0x024b, B:70:0x0254), top: B:44:0x01c5 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0281 A[Catch: JSONException -> 0x0294, TryCatch #30 {JSONException -> 0x0294, blocks: (B:73:0x0261, B:75:0x0281, B:77:0x028f, B:78:0x0299, B:80:0x029f, B:81:0x02a8, B:83:0x02ae, B:85:0x02b7, B:91:0x02d1), top: B:72:0x0261 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x02fe A[Catch: JSONException -> 0x0311, LOOP:4: B:94:0x02fa->B:96:0x02fe, LOOP_END, TryCatch #28 {JSONException -> 0x0311, blocks: (B:93:0x02dc, B:96:0x02fe, B:98:0x0313), top: B:92:0x02dc }] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager$1] */
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

    /* JADX WARN: Removed duplicated region for block: B:45:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f6 A[RETURN] */
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
