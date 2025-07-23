package com.samsung.android.feature;

import java.util.Map;

/* loaded from: classes6.dex */
class SecCarrier {
    private static final String FEATURE_GROUP_KEY = "CarrierFeature_Common_CarrierGroup";
    private static final String TAG_CARRIER_ID = "canonical_id";
    private static final String TAG_CUSTOMER = "customer";
    private static final String TAG_FEATURE = "feature";
    private static final String TAG_MAPPED_CID_VER = "mapped_cid_version";
    private static final String TAG_NAME = "carrier_group";
    private static final String TAG_SPECIFIC = "specific";
    private static final String TAG_VERSION = "version";
    private static final String VERSION_DEFAULT = "-1";
    private Map<String, String> feature;
    private boolean isCarrierGroupValid;
    private String mapped_cid_version;
    private String version;

    /* JADX WARN: Removed duplicated region for block: B:27:0x00a0 A[LOOP:2: B:27:0x00a0->B:39:0x00e7, LOOP_START, PHI: r0
      0x00a0: PHI (r0v1 int) = (r0v0 int), (r0v2 int) binds: [B:26:0x009e, B:39:0x00e7] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    SecCarrier(java.lang.String r11, java.lang.String r12, int r13) throws org.json.JSONException {
        /*
            r10 = this;
            r10.<init>()
            r0 = 0
            r10.isCarrierGroupValid = r0
            java.util.LinkedHashMap r1 = new java.util.LinkedHashMap
            r1.<init>()
            r10.feature = r1
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>(r11)
            java.lang.String r11 = "version"
            java.lang.String r2 = r1.getString(r11)
            r10.version = r2
            java.lang.String r2 = "mapped_cid_version"
            boolean r3 = r1.has(r2)
            if (r3 == 0) goto L2b
            java.lang.String r2 = r1.getString(r2)
            r10.mapped_cid_version = r2
            goto L2f
        L2b:
            java.lang.String r2 = "-1"
            r10.mapped_cid_version = r2
        L2f:
            java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
            r2.<init>()
            java.lang.String r3 = "specific"
            boolean r4 = r1.has(r3)
            java.lang.String r5 = "feature"
            if (r4 == 0) goto L8d
            org.json.JSONArray r3 = r1.getJSONArray(r3)
            if (r3 == 0) goto L8d
            r4 = r0
        L46:
            int r6 = r3.length()
            if (r4 >= r6) goto L8d
            org.json.JSONObject r6 = r3.getJSONObject(r4)
            java.lang.String r7 = "canonical_id"
            int r6 = r6.getInt(r7)
            if (r6 != r13) goto L8a
            org.json.JSONObject r13 = r3.getJSONObject(r4)
            org.json.JSONObject r13 = r13.getJSONObject(r5)
            java.util.Iterator r13 = r13.keys()
            r6 = r12
        L65:
            boolean r7 = r13.hasNext()
            if (r7 == 0) goto L8e
            java.lang.Object r7 = r13.next()
            java.lang.String r7 = (java.lang.String) r7
            org.json.JSONObject r8 = r3.getJSONObject(r4)
            org.json.JSONObject r8 = r8.getJSONObject(r5)
            java.lang.String r8 = r8.getString(r7)
            java.lang.String r9 = "CarrierFeature_Common_CarrierGroup"
            boolean r9 = r7.equals(r9)
            if (r9 == 0) goto L86
            r6 = r8
        L86:
            r2.put(r7, r8)
            goto L65
        L8a:
            int r4 = r4 + 1
            goto L46
        L8d:
            r6 = r12
        L8e:
            java.util.LinkedHashMap r13 = new java.util.LinkedHashMap
            r13.<init>()
            java.lang.String r3 = r10.version
            r13.put(r11, r3)
            java.lang.String r11 = "customer"
            org.json.JSONArray r11 = r1.getJSONArray(r11)
            if (r11 == 0) goto Lea
        La0:
            int r1 = r11.length()
            if (r0 >= r1) goto Lea
            org.json.JSONObject r1 = r11.getJSONObject(r0)
            java.lang.String r3 = "carrier_group"
            java.lang.String r1 = r1.getString(r3)
            boolean r3 = r12.equals(r1)
            if (r3 == 0) goto Lb9
            r3 = 1
            r10.isCarrierGroupValid = r3
        Lb9:
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto Le7
            org.json.JSONObject r1 = r11.getJSONObject(r0)
            org.json.JSONObject r1 = r1.getJSONObject(r5)
            java.util.Iterator r1 = r1.keys()
        Lcb:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto Le7
            java.lang.Object r3 = r1.next()
            java.lang.String r3 = (java.lang.String) r3
            org.json.JSONObject r4 = r11.getJSONObject(r0)
            org.json.JSONObject r4 = r4.getJSONObject(r5)
            java.lang.String r4 = r4.getString(r3)
            r13.put(r3, r4)
            goto Lcb
        Le7:
            int r0 = r0 + 1
            goto La0
        Lea:
            r13.putAll(r2)
            r10.feature = r13
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.feature.SecCarrier.<init>(java.lang.String, java.lang.String, int):void");
    }

    Map<String, String> getFeature() {
        return this.feature;
    }

    int getVersion() {
        return Integer.parseInt(this.version);
    }

    int getMappedCidVersion() {
        return Integer.parseInt(this.mapped_cid_version);
    }

    boolean isCarrierGroupValid() {
        return this.isCarrierGroupValid;
    }
}
