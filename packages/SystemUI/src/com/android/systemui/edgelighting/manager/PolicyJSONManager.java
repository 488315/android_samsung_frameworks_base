package com.android.systemui.edgelighting.manager;

import android.content.Context;
import android.util.JsonWriter;
import android.util.SparseArray;
import com.android.systemui.edgelighting.data.policy.PolicyInfo;
import com.android.systemui.util.SystemUIAnalytics;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class PolicyJSONManager {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.io.OutputStreamWriter, java.io.Writer] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.OutputStreamWriter] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.OutputStreamWriter] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.util.JsonWriter] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.util.JsonWriter] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v9, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0036 -> B:66:0x006d). Please report as a decompilation issue!!! */
    public static void writeJson(Context e, long j, int i, SparseArray sparseArray) throws Throwable {
        JsonWriter jsonWriter;
        ?? outputStreamWriter = "edge_lighting_policy.json";
        ?? r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        try {
            try {
                try {
                    e.deleteFile("edge_lighting_policy.json");
                    e = e.openFileOutput("edge_lighting_policy.json", 0);
                    try {
                        outputStreamWriter = new OutputStreamWriter((OutputStream) e, "UTF-8");
                        try {
                            jsonWriter = new JsonWriter(outputStreamWriter);
                        } catch (IOException e2) {
                            e = e2;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        outputStreamWriter = 0;
                    } catch (Throwable th) {
                        th = th;
                        outputStreamWriter = 0;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e4) {
                e = e4;
                e = 0;
                outputStreamWriter = 0;
            } catch (Throwable th3) {
                th = th3;
                e = 0;
                outputStreamWriter = 0;
            }
        } catch (IOException e5) {
            e = e5;
            e.printStackTrace();
        }
        try {
            r1 = " ";
            jsonWriter.setIndent(" ");
            writePolicy(jsonWriter, j, i, sparseArray);
            try {
                jsonWriter.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            try {
                outputStreamWriter.close();
            } catch (IOException e7) {
                e7.printStackTrace();
            }
            if (e != 0) {
                e.close();
            }
        } catch (IOException e8) {
            e = e8;
            r1 = jsonWriter;
            e.printStackTrace();
            if (r1 != 0) {
                try {
                    r1.close();
                } catch (IOException e9) {
                    e9.printStackTrace();
                }
            }
            if (outputStreamWriter != 0) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e10) {
                    e10.printStackTrace();
                }
            }
            if (e != 0) {
                e.close();
            }
        } catch (Throwable th4) {
            th = th4;
            r1 = jsonWriter;
            if (r1 != 0) {
                try {
                    r1.close();
                } catch (IOException e11) {
                    e11.printStackTrace();
                }
            }
            if (outputStreamWriter != 0) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e12) {
                    e12.printStackTrace();
                }
            }
            if (e == 0) {
                throw th;
            }
            try {
                e.close();
                throw th;
            } catch (IOException e13) {
                e13.printStackTrace();
                throw th;
            }
        }
    }

    public static void writePolicy(JsonWriter jsonWriter, long j, int i, SparseArray sparseArray) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("policy_version").value(j);
        jsonWriter.name("policy_type").value(i);
        jsonWriter.name("edge_lighting_policy");
        jsonWriter.beginArray();
        int size = sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            int iKeyAt = sparseArray.keyAt(i2);
            HashMap map = (HashMap) sparseArray.valueAt(i2);
            if (iKeyAt == 1 || iKeyAt == 2) {
                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    PolicyInfo policyInfo = (PolicyInfo) ((Map.Entry) it.next()).getValue();
                    jsonWriter.beginObject();
                    jsonWriter.name("item").value(policyInfo.item);
                    jsonWriter.name("category").value(policyInfo.category);
                    jsonWriter.name("range").value(policyInfo.range);
                    jsonWriter.name("versionCode").value(policyInfo.versionCode);
                    jsonWriter.name("color").value(policyInfo.color);
                    jsonWriter.endObject();
                }
            }
        }
        jsonWriter.endArray();
        HashMap map2 = (HashMap) sparseArray.get(10);
        if (map2 != null) {
            jsonWriter.name("edge_lighting_priority");
            jsonWriter.beginArray();
            Iterator it2 = map2.entrySet().iterator();
            while (it2.hasNext()) {
                PolicyInfo policyInfo2 = (PolicyInfo) ((Map.Entry) it2.next()).getValue();
                jsonWriter.beginObject();
                jsonWriter.name("item").value(policyInfo2.item);
                jsonWriter.name(SystemUIAnalytics.QPNE_VID_PRIORITY).value(policyInfo2.priority);
                jsonWriter.name("default_on").value(policyInfo2.defaultOn);
                jsonWriter.name("color").value(policyInfo2.color);
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
        }
        HashMap map3 = (HashMap) sparseArray.get(11);
        if (map3 != null) {
            jsonWriter.name("edge_lighting_whitelist");
            jsonWriter.beginArray();
            Iterator it3 = map3.entrySet().iterator();
            while (it3.hasNext()) {
                PolicyInfo policyInfo3 = (PolicyInfo) ((Map.Entry) it3.next()).getValue();
                jsonWriter.beginObject();
                jsonWriter.name("item").value(policyInfo3.item);
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
        }
        jsonWriter.endObject();
    }
}
