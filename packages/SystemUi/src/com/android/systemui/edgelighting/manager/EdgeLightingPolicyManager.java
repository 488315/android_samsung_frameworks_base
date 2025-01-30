package com.android.systemui.edgelighting.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.R;
import com.android.systemui.edgelighting.data.policy.PolicyClientContract;
import com.android.systemui.edgelighting.data.policy.PolicyInfo;
import com.android.systemui.edgelighting.policy.EdgeLightingPolicyUpdateService;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.edgelighting.utils.ExtractAppIconUtils;
import com.android.systemui.edgelighting.utils.Utils;
import com.samsung.android.edge.EdgeLightingPolicy;
import com.samsung.android.edge.EdgeLightingPolicyInfo;
import com.samsung.android.edge.SemEdgeManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EdgeLightingPolicyManager {
    public static final Uri EL_POLICY_ITEM_URI = Uri.withAppendedPath(PolicyClientContract.PolicyItems.CONTENT_URI, "EdgeLighting");
    public static final String[] POLICY_ITEM_PROJECTION = {"item", "category", "data1", "data2", "data3"};
    public static EdgeLightingPolicyManager mInstance;
    public int mPolicyType;
    public long mPolicyVersion;
    public final SparseArray mPolicyInfoData = new SparseArray();
    public final C13481 mCategoryComparator = new Comparator(this) { // from class: com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager.1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((PolicyInfo) obj).category - ((PolicyInfo) obj2).category;
        }
    };

    /* JADX WARN: Code restructure failed: missing block: B:175:0x0152, code lost:
    
        if (r9 == 0) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x0163, code lost:
    
        r10 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x0161, code lost:
    
        if (r9 != 0) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x008e, code lost:
    
        if (r9 == null) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x009d, code lost:
    
        if (r9 != null) goto L304;
     */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0333 A[Catch: all -> 0x032c, IOException -> 0x032f, TRY_LEAVE, TryCatch #9 {IOException -> 0x032f, blocks: (B:226:0x0328, B:197:0x0333), top: B:225:0x0328, outer: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:224:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0328 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:276:0x00b9 A[Catch: all -> 0x00b2, IOException -> 0x00b5, TRY_LEAVE, TryCatch #2 {IOException -> 0x00b5, blocks: (B:305:0x00ae, B:276:0x00b9), top: B:304:0x00ae, outer: #15 }] */
    /* JADX WARN: Removed duplicated region for block: B:303:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:304:0x00ae A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager$1] */
    /* JADX WARN: Type inference failed for: r10v8, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARN: Type inference failed for: r9v22 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4, types: [java.io.BufferedReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private EdgeLightingPolicyManager(Context context, boolean z) {
        StringBuilder sb;
        Throwable th;
        BufferedReader bufferedReader;
        ?? r9;
        Throwable th2;
        BufferedReader bufferedReader2;
        boolean z2;
        long j;
        boolean z3;
        Throwable th3;
        BufferedReader bufferedReader3;
        BufferedReader bufferedReader4;
        this.mPolicyType = 0;
        ?? r7 = "edge_lighting_policy.json";
        InputStream inputStream = null;
        if (new File(context.getFilesDir(), "edge_lighting_policy.json").exists()) {
            sb = new StringBuilder();
            try {
                try {
                    r7 = context.openFileInput("edge_lighting_policy.json");
                } catch (IOException e) {
                    e = e;
                    r7 = 0;
                    bufferedReader4 = null;
                } catch (Throwable th4) {
                    th3 = th4;
                    bufferedReader3 = null;
                    if (inputStream != null) {
                        try {
                            try {
                                inputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                if (bufferedReader3 == null) {
                                    throw th3;
                                }
                                bufferedReader3.close();
                                throw th3;
                            }
                        } catch (Throwable th5) {
                            if (bufferedReader3 == null) {
                                throw th5;
                            }
                            try {
                                bufferedReader3.close();
                                throw th5;
                            } catch (IOException e3) {
                                e3.printStackTrace();
                                throw th5;
                            }
                        }
                    }
                    if (bufferedReader3 != null) {
                        bufferedReader3.close();
                    }
                    if (bufferedReader3 == null) {
                        throw th3;
                    }
                    try {
                        bufferedReader3.close();
                        throw th3;
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        throw th3;
                    }
                }
                try {
                    bufferedReader4 = new BufferedReader(new InputStreamReader(r7));
                    while (true) {
                        try {
                            String readLine = bufferedReader4.readLine();
                            if (readLine == null) {
                                break;
                            } else {
                                sb.append(readLine);
                            }
                        } catch (IOException e5) {
                            e = e5;
                            e.printStackTrace();
                            if (r7 != 0) {
                                try {
                                    try {
                                        r7.close();
                                    } catch (IOException e6) {
                                        e6.printStackTrace();
                                    }
                                } catch (Throwable th6) {
                                    if (bufferedReader4 == null) {
                                        throw th6;
                                    }
                                    try {
                                        bufferedReader4.close();
                                        throw th6;
                                    } catch (IOException e7) {
                                        e7.printStackTrace();
                                        throw th6;
                                    }
                                }
                            }
                            if (bufferedReader4 != null) {
                                bufferedReader4.close();
                            }
                        }
                    }
                    if (r7 != 0) {
                        try {
                            try {
                                r7.close();
                            } finally {
                            }
                        } catch (IOException e8) {
                            e8.printStackTrace();
                        }
                    }
                    bufferedReader4.close();
                } catch (IOException e9) {
                    e = e9;
                    bufferedReader4 = null;
                } catch (Throwable th7) {
                    th = th7;
                    bufferedReader3 = null;
                    inputStream = r7;
                    th3 = th;
                    if (inputStream != null) {
                    }
                    if (bufferedReader3 != null) {
                    }
                    if (bufferedReader3 == null) {
                    }
                    bufferedReader3.close();
                    throw th3;
                }
                try {
                    bufferedReader4.close();
                } catch (IOException e10) {
                    e10.printStackTrace();
                }
            } catch (Throwable th8) {
                th = th8;
            }
        } else {
            sb = null;
        }
        StringBuilder sb2 = new StringBuilder();
        try {
            InputStream openRawResource = context.getResources().openRawResource(R.raw.edge_lighting_policy);
            try {
                ?? bufferedReader5 = new BufferedReader(new InputStreamReader(openRawResource));
                while (true) {
                    try {
                        String readLine2 = bufferedReader5.readLine();
                        if (readLine2 != null) {
                            sb2.append(readLine2);
                        } else {
                            try {
                                break;
                            } finally {
                            }
                        }
                    } catch (IOException e11) {
                        e = e11;
                        inputStream = bufferedReader5;
                        r9 = inputStream;
                        inputStream = openRawResource;
                        try {
                            e.printStackTrace();
                            if (inputStream != null) {
                                try {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e12) {
                                        e12.printStackTrace();
                                    }
                                } catch (Throwable th9) {
                                    if (r9 == 0) {
                                        throw th9;
                                    }
                                    try {
                                        r9.close();
                                        throw th9;
                                    } catch (IOException e13) {
                                        e13.printStackTrace();
                                        throw th9;
                                    }
                                }
                            }
                            if (r9 != 0) {
                                r9.close();
                            }
                        } catch (Throwable th10) {
                            th2 = th10;
                            th = th2;
                            bufferedReader = r9;
                            if (inputStream != null) {
                                try {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e14) {
                                        e14.printStackTrace();
                                        if (bufferedReader == null) {
                                            throw th;
                                        }
                                        bufferedReader.close();
                                        throw th;
                                    }
                                } catch (Throwable th11) {
                                    if (bufferedReader == null) {
                                        throw th11;
                                    }
                                    try {
                                        bufferedReader.close();
                                        throw th11;
                                    } catch (IOException e15) {
                                        e15.printStackTrace();
                                        throw th11;
                                    }
                                }
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (bufferedReader == null) {
                                throw th;
                            }
                            try {
                                bufferedReader.close();
                                throw th;
                            } catch (IOException e16) {
                                e16.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (Throwable th12) {
                        th2 = th12;
                        inputStream = openRawResource;
                        r9 = bufferedReader5;
                        th = th2;
                        bufferedReader = r9;
                        if (inputStream != null) {
                        }
                        if (bufferedReader != null) {
                        }
                        if (bufferedReader == null) {
                        }
                        bufferedReader.close();
                        throw th;
                    }
                }
                if (openRawResource != null) {
                    try {
                        openRawResource.close();
                    } catch (IOException e17) {
                        e17.printStackTrace();
                        bufferedReader2 = bufferedReader5;
                    }
                }
                bufferedReader5.close();
                bufferedReader2 = bufferedReader5;
            } catch (IOException e18) {
                e = e18;
            } catch (Throwable th13) {
                th2 = th13;
                r9 = 0;
                inputStream = openRawResource;
            }
        } catch (IOException e19) {
            e = e19;
            r9 = 0;
        } catch (Throwable th14) {
            th = th14;
            bufferedReader = null;
            if (inputStream != null) {
            }
            if (bufferedReader != null) {
            }
            if (bufferedReader == null) {
            }
            bufferedReader.close();
            throw th;
        }
        try {
            bufferedReader2.close();
        } catch (IOException e20) {
            e20.printStackTrace();
        }
        int i = 1;
        if (sb != null) {
            long j2 = 0;
            try {
                j = new JSONObject(sb.toString()).getLong("policy_version");
            } catch (JSONException e21) {
                e21.printStackTrace();
                j = 0;
            }
            try {
                j2 = new JSONObject(sb2.toString()).getLong("policy_version");
            } catch (JSONException e22) {
                e22.printStackTrace();
            }
            if (j2 > j) {
                sb = sb2;
                z3 = true;
            } else {
                z3 = false;
            }
            z2 = z3;
        } else {
            sb = sb2;
            z2 = false;
        }
        try {
            JSONObject jSONObject = new JSONObject(sb.toString());
            this.mPolicyVersion = jSONObject.getLong("policy_version");
            if (jSONObject.has("policy_type")) {
                this.mPolicyType = jSONObject.getInt("policy_type");
            }
        } catch (JSONException e23) {
            e23.printStackTrace();
        }
        SparseArray sparseArray = this.mPolicyInfoData;
        sparseArray.clear();
        try {
            JSONArray jSONArray = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_policy"));
            int length = jSONArray.length();
            HashMap hashMap = new HashMap();
            int i2 = 0;
            while (i2 < length) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                String string = jSONObject2.getString("item");
                int i3 = length;
                int i4 = jSONObject2.getInt("category");
                PolicyInfo policyInfo = new PolicyInfo(string, i4, jSONObject2.has("range") ? jSONObject2.getInt("range") : 0, jSONObject2.has("color") ? jSONObject2.getInt("color") : -11761985, jSONObject2.has("versionCode") ? jSONObject2.getInt("versionCode") : 0);
                if (i != i4) {
                    sparseArray.put(i, hashMap);
                    HashMap hashMap2 = (HashMap) sparseArray.get(i4);
                    hashMap = hashMap2 == null ? new HashMap() : hashMap2;
                    i = i4;
                }
                hashMap.put(string, policyInfo);
                i2++;
                length = i3;
            }
            sparseArray.put(i, hashMap);
        } catch (JSONException e24) {
            e24.printStackTrace();
        }
        try {
            HashMap hashMap3 = new HashMap();
            JSONArray jSONArray2 = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_priority"));
            int length2 = jSONArray2.length();
            for (int i5 = 0; i5 < length2; i5++) {
                JSONObject jSONObject3 = jSONArray2.getJSONObject(i5);
                String string2 = jSONObject3.getString("item");
                hashMap3.put(string2, new PolicyInfo(string2, 10, 1, jSONObject3.has("default_on") ? jSONObject3.getBoolean("default_on") : false, jSONObject3.has("color") ? jSONObject3.getInt("color") : jSONObject3.has("priority") ? jSONObject3.getInt("priority") : -11761985));
            }
            sparseArray.put(10, hashMap3);
        } catch (JSONException e25) {
            e25.printStackTrace();
        }
        try {
            HashMap hashMap4 = new HashMap();
            JSONArray jSONArray3 = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_whitelist"));
            int length3 = jSONArray3.length();
            for (int i6 = 0; i6 < length3; i6++) {
                String string3 = jSONArray3.getJSONObject(i6).getString("item");
                hashMap4.put(string3, new PolicyInfo(string3, 11));
            }
            sparseArray.put(11, hashMap4);
        } catch (JSONException e26) {
            e26.printStackTrace();
        }
        if (z2) {
            PolicyJSONManager.writeJson(context, this.mPolicyVersion, this.mPolicyType, sparseArray);
        }
        if (z) {
            return;
        }
        EdgeLightingPolicyUpdateService.startActionUpdate(context);
    }

    public static PolicyInfo createPolicyInfo(String str, String str2, String str3, String str4, String str5) {
        int i;
        int parseInt;
        int i2;
        int parseInt2;
        int i3;
        int i4;
        boolean z;
        int i5;
        int i6;
        int i7 = 1;
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
        boolean z2 = false;
        int i8 = -11761985;
        if (i == 1 || i == 2) {
            if (str3 != null) {
                try {
                    parseInt2 = Integer.parseInt(str3.trim());
                } catch (NumberFormatException e2) {
                    e = e2;
                    i2 = 0;
                    e.printStackTrace();
                    i3 = 0;
                    i4 = i2;
                    return new PolicyInfo(str, i, i4, i8, i3);
                }
            } else {
                parseInt2 = 0;
            }
            if (str4 != null) {
                try {
                    i8 = Integer.parseInt(str4.trim());
                } catch (NumberFormatException e3) {
                    i2 = parseInt2;
                    e = e3;
                    e.printStackTrace();
                    i3 = 0;
                    i4 = i2;
                    return new PolicyInfo(str, i, i4, i8, i3);
                }
            }
            i3 = str5 != null ? Integer.parseInt(str5.trim()) : 0;
            i4 = parseInt2;
            return new PolicyInfo(str, i, i4, i8, i3);
        }
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
                i7 = Integer.parseInt(str3.trim());
            } catch (NumberFormatException e4) {
                e4.printStackTrace();
            }
        }
        z2 = str4 != null ? Boolean.parseBoolean(str4.trim()) : false;
        if (str5 != null) {
            z = z2;
            i6 = Integer.parseInt(str5.trim());
            i5 = i7;
            return new PolicyInfo(str, i, i5, z, i6);
        }
        z = z2;
        i5 = i7;
        i6 = -11761985;
        return new PolicyInfo(str, i, i5, z, i6);
    }

    public static EdgeLightingPolicyManager getInstance(Context context, boolean z) {
        if (mInstance == null) {
            mInstance = new EdgeLightingPolicyManager(context, z);
        }
        return mInstance;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f5 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getEdgeLightingColor(Context context, String str) {
        Drawable drawable;
        Drawable drawable2;
        PolicyInfo policyInfo;
        int i;
        PolicyInfo policyInfo2;
        int i2;
        int i3;
        SparseArray sparseArray = this.mPolicyInfoData;
        HashMap hashMap = (HashMap) sparseArray.get(1);
        if ("com.samsung.android.messaging".equals(str)) {
            int i4 = Utils.$r8$clinit;
            String str2 = "";
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 0);
                str2 = packageInfo.versionName;
                i3 = packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                i3 = -1;
            }
            StringBuilder m87m = AbstractC0866xb1ce8deb.m87m(" pkgName : ", str, " version NAme : ", str2, " ");
            m87m.append(i3);
            Slog.i("Utils", m87m.toString());
            if (i3 < 500000000) {
                return 15888924;
            }
        }
        if (hashMap != null && (policyInfo2 = (PolicyInfo) hashMap.get(str)) != null && (i2 = policyInfo2.color) != -11761985) {
            return i2;
        }
        HashMap hashMap2 = (HashMap) sparseArray.get(10);
        if (hashMap2 != null && (policyInfo = (PolicyInfo) hashMap2.get(str)) != null && (i = policyInfo.color) != -11761985) {
            return i;
        }
        List appInfoSupportingEdgeLighting = EdgeLightingSettingUtils.getAppInfoSupportingEdgeLighting(context.getPackageManager(), str);
        if (appInfoSupportingEdgeLighting != null && appInfoSupportingEdgeLighting.size() > 0) {
            ActivityInfo activityInfo = ((ResolveInfo) appInfoSupportingEdgeLighting.get(0)).activityInfo;
            ComponentName componentName = new ComponentName(activityInfo.packageName, activityInfo.name);
            String flattenToString = componentName.flattenToString();
            if (flattenToString != null) {
                componentName = ComponentName.unflattenFromString(flattenToString);
            }
            PackageManager packageManager = context.getPackageManager();
            drawable2 = packageManager.semGetCscPackageItemIcon(componentName.getClassName());
            if (drawable2 == null && (drawable2 = packageManager.semGetCscPackageItemIcon(componentName.getPackageName())) == null) {
                try {
                    drawable = packageManager.semGetActivityIconForIconTray(componentName, 1);
                } catch (PackageManager.NameNotFoundException e2) {
                    e2.printStackTrace();
                }
            }
            if (drawable2 != null) {
                return -11761985;
            }
            int processDominantColorInImage = ExtractAppIconUtils.processDominantColorInImage(drawable2);
            EdgeLightingSettingUtils.saveAppCustomColor(context, str, processDominantColorInImage);
            Slog.i("ELPolicyManager", "package : " + str + " Extract color : " + processDominantColorInImage);
            return processDominantColorInImage;
        }
        try {
            drawable = context.getPackageManager().getApplicationIcon(str);
        } catch (PackageManager.NameNotFoundException e3) {
            e3.printStackTrace();
            drawable = null;
        }
        drawable2 = drawable;
        if (drawable2 != null) {
        }
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
        SparseArray sparseArray = this.mPolicyInfoData;
        HashMap hashMap2 = (HashMap) sparseArray.get(1);
        if (hashMap2 != null) {
            Iterator it = hashMap2.entrySet().iterator();
            while (it.hasNext()) {
                PolicyInfo policyInfo = (PolicyInfo) ((Map.Entry) it.next()).getValue();
                edgeLightingPolicy.addEdgeLightingPolicyInfo(new EdgeLightingPolicyInfo(policyInfo.item, 1, policyInfo.range));
            }
        }
        if ((this.mPolicyType & 4) != 0 && (hashMap = (HashMap) sparseArray.get(2)) != null) {
            Iterator it2 = hashMap.entrySet().iterator();
            while (it2.hasNext()) {
                PolicyInfo policyInfo2 = (PolicyInfo) ((Map.Entry) it2.next()).getValue();
                edgeLightingPolicy.addEdgeLightingPolicyInfo(new EdgeLightingPolicyInfo(policyInfo2.item, 2, policyInfo2.range));
            }
        }
        HashMap hashMap3 = (HashMap) sparseArray.get(10);
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
