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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class EdgeLightingPolicyManager {
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
    /* JADX WARN: Removed duplicated region for block: B:100:0x030b  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0117 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0329 A[Catch: all -> 0x0322, IOException -> 0x0325, TRY_LEAVE, TryCatch #30 {IOException -> 0x0325, blocks: (B:189:0x031e, B:174:0x0329), top: B:188:0x031e, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0348 A[Catch: IOException -> 0x0336, TRY_ENTER, TRY_LEAVE, TryCatch #3 {IOException -> 0x0336, blocks: (B:186:0x0348, B:180:0x0332), top: B:171:0x031c }] */
    /* JADX WARN: Removed duplicated region for block: B:187:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:188:0x031e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:268:0x00c5 A[Catch: all -> 0x00be, IOException -> 0x00c1, TRY_LEAVE, TryCatch #10 {IOException -> 0x00c1, blocks: (B:295:0x00ba, B:268:0x00c5), top: B:294:0x00ba, outer: #17 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0109 A[Catch: all -> 0x010d, IOException -> 0x0112, LOOP:1: B:24:0x0103->B:27:0x0109, LOOP_END, TRY_LEAVE, TryCatch #35 {IOException -> 0x0112, all -> 0x010d, blocks: (B:25:0x0103, B:27:0x0109), top: B:24:0x0103 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0115 A[EDGE_INSN: B:28:0x0115->B:29:0x0115 BREAK  A[LOOP:1: B:24:0x0103->B:27:0x0109], EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:292:0x00e4 A[Catch: IOException -> 0x00d2, TRY_ENTER, TRY_LEAVE, TryCatch #22 {IOException -> 0x00d2, blocks: (B:292:0x00e4, B:274:0x00ce), top: B:265:0x00b8 }] */
    /* JADX WARN: Removed duplicated region for block: B:293:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:294:0x00ba A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01a7 A[Catch: JSONException -> 0x01ae, TRY_LEAVE, TryCatch #16 {JSONException -> 0x01ae, blocks: (B:40:0x0190, B:42:0x01a7), top: B:39:0x0190 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01dc A[Catch: JSONException -> 0x01f7, TryCatch #9 {JSONException -> 0x01f7, blocks: (B:46:0x01bc, B:48:0x01dc, B:50:0x01f0, B:51:0x01fb, B:53:0x0201, B:54:0x020b, B:56:0x0211, B:57:0x021a, B:59:0x0227, B:61:0x0238, B:64:0x0242, B:71:0x024b), top: B:45:0x01bc }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0278 A[Catch: JSONException -> 0x028b, TryCatch #34 {JSONException -> 0x028b, blocks: (B:74:0x0258, B:76:0x0278, B:78:0x0286, B:79:0x0290, B:81:0x0296, B:82:0x029f, B:84:0x02a5, B:86:0x02a9, B:91:0x02be), top: B:73:0x0258 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x02eb A[Catch: JSONException -> 0x02fe, LOOP:4: B:94:0x02e7->B:96:0x02eb, LOOP_END, TryCatch #6 {JSONException -> 0x02fe, blocks: (B:93:0x02c9, B:96:0x02eb, B:98:0x0300), top: B:92:0x02c9 }] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private EdgeLightingPolicyManager(android.content.Context r29, boolean r30) {
        /*
            Method dump skipped, instructions count: 844
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager.<init>(android.content.Context, boolean):void");
    }

    public static PolicyInfo createPolicyInfo(String str, String str2, String str3, String str4, String str5) {
        int i;
        int parseInt;
        NumberFormatException numberFormatException;
        int i2;
        int parseInt2;
        int i3;
        int i4 = 1;
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
        int i5 = 0;
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
                    i4 = Integer.parseInt(str3.trim());
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                }
            }
            z = str4 != null ? Boolean.parseBoolean(str4.trim()) : false;
            if (str5 != null) {
                i6 = Integer.parseInt(str5.trim());
            }
            return new PolicyInfo(str, i, i4, z, i6);
        }
        if (str3 != null) {
            try {
                parseInt2 = Integer.parseInt(str3.trim());
            } catch (NumberFormatException e3) {
                numberFormatException = e3;
                i2 = 0;
                numberFormatException.printStackTrace();
                i3 = i2;
                return new PolicyInfo(str, i, i3, i6, i5);
            }
        } else {
            parseInt2 = 0;
        }
        if (str4 != null) {
            try {
                i6 = Integer.parseInt(str4.trim());
            } catch (NumberFormatException e4) {
                i2 = parseInt2;
                numberFormatException = e4;
                numberFormatException.printStackTrace();
                i3 = i2;
                return new PolicyInfo(str, i, i3, i6, i5);
            }
        }
        i5 = str5 != null ? Integer.parseInt(str5.trim()) : 0;
        i3 = parseInt2;
        return new PolicyInfo(str, i, i3, i6, i5);
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

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ac, code lost:
    
        r0 = r8.semGetCscPackageItemIcon(r3.getPackageName());
     */
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
