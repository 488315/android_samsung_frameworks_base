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
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.data.policy.PolicyClientContract;
import com.android.systemui.edgelighting.data.policy.PolicyInfo;
import com.android.systemui.edgelighting.policy.EdgeLightingPolicyUpdateService;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.edgelighting.utils.ExtractAppIconUtils;
import com.android.systemui.edgelighting.utils.Utils;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.edge.EdgeLightingPolicy;
import com.samsung.android.edge.EdgeLightingPolicyInfo;
import com.samsung.android.edge.SemEdgeManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
    /* JADX WARN: Removed duplicated region for block: B:154:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01a7 A[Catch: JSONException -> 0x01ae, TRY_LEAVE, TryCatch #16 {JSONException -> 0x01ae, blocks: (B:161:0x0190, B:163:0x01a7), top: B:274:0x0190 }] */
    /* JADX WARN: Removed duplicated region for block: B:170:0x01dc A[Catch: JSONException -> 0x01f7, TryCatch #9 {JSONException -> 0x01f7, blocks: (B:168:0x01bc, B:170:0x01dc, B:172:0x01f0, B:176:0x01fb, B:178:0x0201, B:180:0x020b, B:182:0x0211, B:184:0x021a, B:186:0x0227, B:188:0x0238, B:191:0x0242, B:192:0x024b), top: B:268:0x01bc }] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0278 A[Catch: JSONException -> 0x028b, TryCatch #34 {JSONException -> 0x028b, blocks: (B:196:0x0258, B:198:0x0278, B:200:0x0286, B:204:0x0290, B:206:0x0296, B:208:0x029f, B:210:0x02a5, B:211:0x02a9, B:212:0x02be), top: B:296:0x0258 }] */
    /* JADX WARN: Removed duplicated region for block: B:218:0x02eb A[Catch: JSONException -> 0x02fe, LOOP:4: B:216:0x02e7->B:218:0x02eb, LOOP_END, TryCatch #6 {JSONException -> 0x02fe, blocks: (B:215:0x02c9, B:218:0x02eb, B:221:0x0300), top: B:266:0x02c9 }] */
    /* JADX WARN: Removed duplicated region for block: B:225:0x030b  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0329 A[Catch: all -> 0x0322, IOException -> 0x0325, TRY_LEAVE, TryCatch #30 {IOException -> 0x0325, blocks: (B:232:0x031e, B:238:0x0329), top: B:290:0x031e, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0348 A[Catch: IOException -> 0x0336, TRY_ENTER, TRY_LEAVE, TryCatch #3 {IOException -> 0x0336, blocks: (B:253:0x0348, B:242:0x0332), top: B:257:0x031c }] */
    /* JADX WARN: Removed duplicated region for block: B:270:0x00ba A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:290:0x031e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0115 A[EDGE_INSN: B:298:0x0115->B:103:0x0115 BREAK  A[LOOP:1: B:311:0x0103->B:96:0x0109], EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0117 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:327:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:331:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:336:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00c5 A[Catch: all -> 0x00be, IOException -> 0x00c1, TRY_LEAVE, TryCatch #10 {IOException -> 0x00c1, blocks: (B:67:0x00ba, B:73:0x00c5), top: B:270:0x00ba, outer: #17 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00e4 A[Catch: IOException -> 0x00d2, TRY_ENTER, TRY_LEAVE, TryCatch #22 {IOException -> 0x00d2, blocks: (B:88:0x00e4, B:77:0x00ce), top: B:282:0x00b8 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0109 A[Catch: all -> 0x010d, IOException -> 0x0112, LOOP:1: B:311:0x0103->B:96:0x0109, LOOP_END, TRY_LEAVE, TryCatch #35 {IOException -> 0x0112, all -> 0x010d, blocks: (B:94:0x0103, B:96:0x0109), top: B:311:0x0103 }] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private EdgeLightingPolicyManager(Context context, boolean z) throws Throwable {
        StringBuilder sb;
        StringBuilder sb2;
        Throwable th;
        BufferedReader bufferedReader;
        int i;
        boolean z2;
        int length;
        int i2;
        int length2;
        int i3;
        int length3;
        int i4;
        String str;
        JSONObject jSONObject;
        boolean z3;
        InputStream inputStreamOpenRawResource;
        String line;
        Throwable th2;
        BufferedReader bufferedReader2;
        FileInputStream fileInputStreamOpenFileInput;
        this.mPolicyType = 0;
        InputStream inputStream = null;
        if (new File(context.getFilesDir(), "edge_lighting_policy.json").exists()) {
            sb = new StringBuilder();
            try {
                try {
                    fileInputStreamOpenFileInput = context.openFileInput("edge_lighting_policy.json");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                e = e2;
                fileInputStreamOpenFileInput = null;
                bufferedReader2 = null;
            } catch (Throwable th3) {
                th2 = th3;
                bufferedReader2 = null;
                try {
                    if (inputStream != null) {
                    }
                    if (bufferedReader2 != null) {
                    }
                    if (bufferedReader2 == null) {
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                    throw th2;
                }
            }
            try {
                bufferedReader2 = new BufferedReader(new InputStreamReader(fileInputStreamOpenFileInput));
                while (true) {
                    try {
                        try {
                            String line2 = bufferedReader2.readLine();
                            if (line2 == null) {
                                try {
                                    break;
                                } catch (Throwable th4) {
                                    try {
                                        bufferedReader2.close();
                                        throw th4;
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                        throw th4;
                                    }
                                }
                            }
                            sb.append(line2);
                        } catch (IOException e5) {
                            e = e5;
                            e.printStackTrace();
                            if (fileInputStreamOpenFileInput != null) {
                                try {
                                    try {
                                        fileInputStreamOpenFileInput.close();
                                    } catch (IOException e6) {
                                        e6.printStackTrace();
                                        if (bufferedReader2 != null) {
                                            bufferedReader2.close();
                                        }
                                    }
                                } catch (Throwable th5) {
                                    if (bufferedReader2 == null) {
                                        throw th5;
                                    }
                                    try {
                                        bufferedReader2.close();
                                        throw th5;
                                    } catch (IOException e7) {
                                        e7.printStackTrace();
                                        throw th5;
                                    }
                                }
                            }
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            sb2 = new StringBuilder();
                            try {
                                inputStreamOpenRawResource = context.getResources().openRawResource(R.raw.edge_lighting_policy);
                                bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpenRawResource));
                                while (true) {
                                    try {
                                        line = bufferedReader.readLine();
                                        if (line != null) {
                                        }
                                        sb2.append(line);
                                    } catch (IOException e8) {
                                        e = e8;
                                        inputStream = inputStreamOpenRawResource;
                                        try {
                                            e.printStackTrace();
                                            if (inputStream != null) {
                                                try {
                                                    try {
                                                        inputStream.close();
                                                    } catch (IOException e9) {
                                                        e9.printStackTrace();
                                                        if (bufferedReader != null) {
                                                            bufferedReader.close();
                                                        }
                                                    }
                                                } catch (Throwable th6) {
                                                    if (bufferedReader == null) {
                                                        throw th6;
                                                    }
                                                    try {
                                                        bufferedReader.close();
                                                        throw th6;
                                                    } catch (IOException e10) {
                                                        e10.printStackTrace();
                                                        throw th6;
                                                    }
                                                }
                                            }
                                            if (bufferedReader != null) {
                                                bufferedReader.close();
                                            }
                                            if (bufferedReader != null) {
                                                bufferedReader.close();
                                            }
                                            i = 1;
                                            if (sb != null) {
                                            }
                                            jSONObject = new JSONObject(sb.toString());
                                            this.mPolicyVersion = jSONObject.getLong("policy_version");
                                            if (jSONObject.has("policy_type")) {
                                            }
                                            String str2 = "versionCode";
                                            this.mPolicyInfoData.clear();
                                            JSONArray jSONArray = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_policy"));
                                            length3 = jSONArray.length();
                                            HashMap map = new HashMap();
                                            i4 = 0;
                                            while (i4 < length3) {
                                            }
                                            this.mPolicyInfoData.put(i, map);
                                            HashMap map2 = new HashMap();
                                            JSONArray jSONArray2 = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_priority"));
                                            length2 = jSONArray2.length();
                                            while (i3 < length2) {
                                            }
                                            this.mPolicyInfoData.put(10, map2);
                                            HashMap map3 = new HashMap();
                                            JSONArray jSONArray3 = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_whitelist"));
                                            length = jSONArray3.length();
                                            while (i2 < length) {
                                            }
                                            this.mPolicyInfoData.put(11, map3);
                                            if (z2) {
                                            }
                                            if (z) {
                                            }
                                        } catch (Throwable th7) {
                                            th = th7;
                                            try {
                                                try {
                                                    if (inputStream != null) {
                                                        try {
                                                            inputStream.close();
                                                        } catch (IOException e11) {
                                                            e11.printStackTrace();
                                                            if (bufferedReader == null) {
                                                                throw th;
                                                            }
                                                            bufferedReader.close();
                                                            throw th;
                                                        }
                                                    }
                                                    if (bufferedReader != null) {
                                                        bufferedReader.close();
                                                    }
                                                    if (bufferedReader != null) {
                                                        throw th;
                                                    }
                                                    bufferedReader.close();
                                                    throw th;
                                                } catch (Throwable th8) {
                                                    if (bufferedReader == null) {
                                                        throw th8;
                                                    }
                                                    try {
                                                        bufferedReader.close();
                                                        throw th8;
                                                    } catch (IOException e12) {
                                                        e12.printStackTrace();
                                                        throw th8;
                                                    }
                                                }
                                            } catch (IOException e13) {
                                                e13.printStackTrace();
                                                throw th;
                                            }
                                        }
                                    } catch (Throwable th9) {
                                        th = th9;
                                        inputStream = inputStreamOpenRawResource;
                                        if (inputStream != null) {
                                        }
                                        if (bufferedReader != null) {
                                        }
                                        if (bufferedReader != null) {
                                        }
                                    }
                                }
                                if (inputStreamOpenRawResource != null) {
                                }
                                bufferedReader.close();
                                bufferedReader.close();
                            } catch (IOException e14) {
                                e14.printStackTrace();
                            }
                            i = 1;
                            if (sb != null) {
                            }
                            jSONObject = new JSONObject(sb.toString());
                            this.mPolicyVersion = jSONObject.getLong("policy_version");
                            if (jSONObject.has("policy_type")) {
                            }
                            String str22 = "versionCode";
                            this.mPolicyInfoData.clear();
                            JSONArray jSONArray4 = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_policy"));
                            length3 = jSONArray4.length();
                            HashMap map4 = new HashMap();
                            i4 = 0;
                            while (i4 < length3) {
                            }
                            this.mPolicyInfoData.put(i, map4);
                            HashMap map22 = new HashMap();
                            JSONArray jSONArray22 = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_priority"));
                            length2 = jSONArray22.length();
                            while (i3 < length2) {
                            }
                            this.mPolicyInfoData.put(10, map22);
                            HashMap map32 = new HashMap();
                            JSONArray jSONArray32 = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_whitelist"));
                            length = jSONArray32.length();
                            while (i2 < length) {
                            }
                            this.mPolicyInfoData.put(11, map32);
                            if (z2) {
                            }
                            if (z) {
                            }
                        }
                    } catch (Throwable th10) {
                        th2 = th10;
                        inputStream = fileInputStreamOpenFileInput;
                        if (inputStream != null) {
                            try {
                                try {
                                    inputStream.close();
                                } catch (IOException e15) {
                                    e15.printStackTrace();
                                    if (bufferedReader2 == null) {
                                        throw th2;
                                    }
                                    bufferedReader2.close();
                                    throw th2;
                                }
                            } catch (Throwable th11) {
                                if (bufferedReader2 == null) {
                                    throw th11;
                                }
                                try {
                                    bufferedReader2.close();
                                    throw th11;
                                } catch (IOException e16) {
                                    e16.printStackTrace();
                                    throw th11;
                                }
                            }
                        }
                        if (bufferedReader2 != null) {
                            bufferedReader2.close();
                        }
                        if (bufferedReader2 == null) {
                            throw th2;
                        }
                        bufferedReader2.close();
                        throw th2;
                    }
                }
                if (fileInputStreamOpenFileInput != null) {
                    try {
                        fileInputStreamOpenFileInput.close();
                    } catch (IOException e17) {
                        e17.printStackTrace();
                        bufferedReader2.close();
                    }
                }
                bufferedReader2.close();
                bufferedReader2.close();
            } catch (IOException e18) {
                e = e18;
                bufferedReader2 = null;
            } catch (Throwable th12) {
                th2 = th12;
                bufferedReader2 = null;
                inputStream = fileInputStreamOpenFileInput;
                if (inputStream != null) {
                }
                if (bufferedReader2 != null) {
                }
                if (bufferedReader2 == null) {
                }
            }
        } else {
            sb = null;
        }
        sb2 = new StringBuilder();
        try {
            inputStreamOpenRawResource = context.getResources().openRawResource(R.raw.edge_lighting_policy);
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpenRawResource));
                while (true) {
                    line = bufferedReader.readLine();
                    if (line != null) {
                        try {
                            break;
                        } catch (Throwable th13) {
                            try {
                                bufferedReader.close();
                                throw th13;
                            } catch (IOException e19) {
                                e19.printStackTrace();
                                throw th13;
                            }
                        }
                    }
                    sb2.append(line);
                }
                if (inputStreamOpenRawResource != null) {
                    try {
                        inputStreamOpenRawResource.close();
                    } catch (IOException e20) {
                        e20.printStackTrace();
                        bufferedReader.close();
                    }
                }
                bufferedReader.close();
                bufferedReader.close();
            } catch (IOException e21) {
                e = e21;
                bufferedReader = null;
            } catch (Throwable th14) {
                th = th14;
                bufferedReader = null;
            }
        } catch (IOException e22) {
            e = e22;
            bufferedReader = null;
        } catch (Throwable th15) {
            th = th15;
            bufferedReader = null;
        }
        i = 1;
        if (sb != null) {
            if (getJsonVersion(sb2) > getJsonVersion(sb)) {
                z3 = true;
                sb = sb2;
            } else {
                z3 = false;
            }
            z2 = z3;
        } else {
            sb = sb2;
            z2 = false;
        }
        try {
            jSONObject = new JSONObject(sb.toString());
            this.mPolicyVersion = jSONObject.getLong("policy_version");
            if (jSONObject.has("policy_type")) {
                this.mPolicyType = jSONObject.getInt("policy_type");
            }
        } catch (JSONException e23) {
            e23.printStackTrace();
        }
        String str222 = "versionCode";
        this.mPolicyInfoData.clear();
        try {
            JSONArray jSONArray42 = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_policy"));
            length3 = jSONArray42.length();
            HashMap map42 = new HashMap();
            i4 = 0;
            while (i4 < length3) {
                JSONObject jSONObject2 = jSONArray42.getJSONObject(i4);
                String string = jSONObject2.getString("item");
                int i5 = jSONObject2.getInt("category");
                PolicyInfo policyInfo = new PolicyInfo(string, i5, jSONObject2.has("range") ? jSONObject2.getInt("range") : 0, jSONObject2.has("color") ? jSONObject2.getInt("color") : -11761985, jSONObject2.has(str222) ? jSONObject2.getInt(str222) : 0);
                if (i != i5) {
                    str = str222;
                    this.mPolicyInfoData.put(i, map42);
                    HashMap map5 = (HashMap) this.mPolicyInfoData.get(i5);
                    map42 = map5 == null ? new HashMap() : map5;
                    i = i5;
                } else {
                    str = str222;
                }
                map42.put(string, policyInfo);
                i4++;
                str222 = str;
            }
            this.mPolicyInfoData.put(i, map42);
        } catch (JSONException e24) {
            e24.printStackTrace();
        }
        try {
            HashMap map222 = new HashMap();
            JSONArray jSONArray222 = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_priority"));
            length2 = jSONArray222.length();
            for (i3 = 0; i3 < length2; i3++) {
                JSONObject jSONObject3 = jSONArray222.getJSONObject(i3);
                String string2 = jSONObject3.getString("item");
                int i6 = jSONObject3.has(SystemUIAnalytics.QPNE_VID_PRIORITY) ? jSONObject3.getInt(SystemUIAnalytics.QPNE_VID_PRIORITY) : -11761985;
                boolean z4 = jSONObject3.has("default_on") ? jSONObject3.getBoolean("default_on") : false;
                if (jSONObject3.has("color")) {
                    i6 = jSONObject3.getInt("color");
                }
                map222.put(string2, new PolicyInfo(string2, 10, 1, z4, i6));
            }
            this.mPolicyInfoData.put(10, map222);
        } catch (JSONException e25) {
            e25.printStackTrace();
        }
        try {
            HashMap map322 = new HashMap();
            JSONArray jSONArray322 = new JSONArray(new JSONObject(sb.toString()).getString("edge_lighting_whitelist"));
            length = jSONArray322.length();
            for (i2 = 0; i2 < length; i2++) {
                String string3 = jSONArray322.getJSONObject(i2).getString("item");
                map322.put(string3, new PolicyInfo(string3, 11));
            }
            this.mPolicyInfoData.put(11, map322);
        } catch (JSONException e26) {
            e26.printStackTrace();
        }
        if (z2) {
            PolicyJSONManager.writeJson(context, this.mPolicyVersion, this.mPolicyType, this.mPolicyInfoData);
        }
        if (z) {
            return;
        }
        EdgeLightingPolicyUpdateService.startActionUpdate(context);
    }

    public static PolicyInfo createPolicyInfo(String str, String str2, String str3, String str4, String str5) throws NumberFormatException {
        int i;
        int i2;
        NumberFormatException numberFormatException;
        int i3;
        int i4;
        int i5;
        int i6 = 1;
        if (str2 != null) {
            try {
                i2 = Integer.parseInt(str2.trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                i = 1;
            }
        } else {
            i2 = 1;
        }
        i = i2;
        int i7 = 0;
        boolean z = false;
        int i8 = -11761985;
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
                    i6 = Integer.parseInt(str3.trim());
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                }
            }
            z = str4 != null ? Boolean.parseBoolean(str4.trim()) : false;
            if (str5 != null) {
                i8 = Integer.parseInt(str5.trim());
            }
            return new PolicyInfo(str, i, i6, z, i8);
        }
        if (str3 != null) {
            try {
                i4 = Integer.parseInt(str3.trim());
            } catch (NumberFormatException e3) {
                numberFormatException = e3;
                i3 = 0;
                numberFormatException.printStackTrace();
                i5 = i3;
                return new PolicyInfo(str, i, i5, i8, i7);
            }
        } else {
            i4 = 0;
        }
        if (str4 != null) {
            try {
                i8 = Integer.parseInt(str4.trim());
            } catch (NumberFormatException e4) {
                i3 = i4;
                numberFormatException = e4;
                numberFormatException.printStackTrace();
                i5 = i3;
                return new PolicyInfo(str, i, i5, i8, i7);
            }
        }
        i7 = str5 != null ? Integer.parseInt(str5.trim()) : 0;
        i5 = i4;
        return new PolicyInfo(str, i, i5, i8, i7);
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

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ac, code lost:
    
        r0 = r8.semGetCscPackageItemIcon(r3.getPackageName());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getEdgeLightingColor(Context context, String str) {
        Drawable applicationIcon;
        Drawable drawableSemGetCscPackageItemIcon;
        PolicyInfo policyInfo;
        int i;
        PolicyInfo policyInfo2;
        int i2;
        int i3;
        HashMap map = (HashMap) this.mPolicyInfoData.get(1);
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
            StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m(" pkgName : ", str, " version NAme : ", str2, " ");
            sbM.append(i3);
            Slog.i("Utils", sbM.toString());
            if (i3 < 500000000) {
                return 15888924;
            }
        }
        if (map != null && (policyInfo2 = (PolicyInfo) map.get(str)) != null && (i2 = policyInfo2.color) != -11761985) {
            return i2;
        }
        HashMap map2 = (HashMap) this.mPolicyInfoData.get(10);
        if (map2 != null && (policyInfo = (PolicyInfo) map2.get(str)) != null && (i = policyInfo.color) != -11761985) {
            return i;
        }
        List appInfoSupportingEdgeLighting = EdgeLightingSettingUtils.getAppInfoSupportingEdgeLighting(context.getPackageManager(), str);
        if (appInfoSupportingEdgeLighting == null || appInfoSupportingEdgeLighting.size() <= 0) {
            try {
                applicationIcon = context.getPackageManager().getApplicationIcon(str);
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
                applicationIcon = null;
            }
            drawableSemGetCscPackageItemIcon = applicationIcon;
        } else {
            ActivityInfo activityInfo = ((ResolveInfo) appInfoSupportingEdgeLighting.get(0)).activityInfo;
            ComponentName componentName = new ComponentName(activityInfo.packageName, activityInfo.name);
            String strFlattenToString = componentName.flattenToString();
            if (strFlattenToString != null) {
                componentName = ComponentName.unflattenFromString(strFlattenToString);
            }
            PackageManager packageManager = context.getPackageManager();
            drawableSemGetCscPackageItemIcon = packageManager.semGetCscPackageItemIcon(componentName.getClassName());
            if (drawableSemGetCscPackageItemIcon == null && drawableSemGetCscPackageItemIcon == null) {
                try {
                    drawableSemGetCscPackageItemIcon = packageManager.semGetActivityIconForIconTray(componentName, 1);
                } catch (PackageManager.NameNotFoundException e3) {
                    e3.printStackTrace();
                }
            }
        }
        if (drawableSemGetCscPackageItemIcon == null) {
            return -11761985;
        }
        int iProcessDominantColorInImage = ExtractAppIconUtils.processDominantColorInImage(drawableSemGetCscPackageItemIcon);
        EdgeLightingSettingUtils.saveAppCustomColor(context, str, iProcessDominantColorInImage);
        Slog.i("ELPolicyManager", "package : " + str + " Extract color : " + iProcessDominantColorInImage);
        return iProcessDominantColorInImage;
    }

    public final void updateEdgeLightingPolicy(Context context, boolean z) {
        HashMap map;
        SemEdgeManager semEdgeManager = (SemEdgeManager) context.getSystemService("edge");
        if (semEdgeManager == null) {
            return;
        }
        int i = this.mPolicyType;
        int i2 = z ? i | 1 : i & (-2);
        EdgeLightingPolicy edgeLightingPolicy = new EdgeLightingPolicy();
        edgeLightingPolicy.setPolicyType(i2);
        edgeLightingPolicy.setPolicyVersion(this.mPolicyVersion);
        HashMap map2 = (HashMap) this.mPolicyInfoData.get(1);
        if (map2 != null) {
            Iterator it = map2.entrySet().iterator();
            while (it.hasNext()) {
                PolicyInfo policyInfo = (PolicyInfo) ((Map.Entry) it.next()).getValue();
                edgeLightingPolicy.addEdgeLightingPolicyInfo(new EdgeLightingPolicyInfo(policyInfo.item, 1, policyInfo.range));
            }
        }
        if ((this.mPolicyType & 4) != 0 && (map = (HashMap) this.mPolicyInfoData.get(2)) != null) {
            Iterator it2 = map.entrySet().iterator();
            while (it2.hasNext()) {
                PolicyInfo policyInfo2 = (PolicyInfo) ((Map.Entry) it2.next()).getValue();
                edgeLightingPolicy.addEdgeLightingPolicyInfo(new EdgeLightingPolicyInfo(policyInfo2.item, 2, policyInfo2.range));
            }
        }
        HashMap map3 = (HashMap) this.mPolicyInfoData.get(10);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : map3.entrySet()) {
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
