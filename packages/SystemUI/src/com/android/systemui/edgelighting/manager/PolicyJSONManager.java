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

public final class PolicyJSONManager {
    public static void writeJson(Context e, long j, int i, SparseArray sparseArray) {
        ?? r0 = "edge_lighting_policy.json";
        ?? r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        try {
        } catch (IOException e2) {
            e = e2;
            e.printStackTrace();
        }
        try {
            try {
                e.deleteFile("edge_lighting_policy.json");
                e = e.openFileOutput("edge_lighting_policy.json", 0);
                try {
                    r0 = new OutputStreamWriter((OutputStream) e, "UTF-8");
                    try {
                        JsonWriter jsonWriter = new JsonWriter(r0);
                        try {
                            r1 = " ";
                            jsonWriter.setIndent(" ");
                            writePolicy(jsonWriter, j, i, sparseArray);
                            try {
                                jsonWriter.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                            try {
                                r0.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                            if (e != 0) {
                                e.close();
                            }
                        } catch (IOException e5) {
                            e = e5;
                            r1 = jsonWriter;
                            e.printStackTrace();
                            if (r1 != 0) {
                                try {
                                    r1.close();
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                }
                            }
                            if (r0 != 0) {
                                try {
                                    r0.close();
                                } catch (IOException e7) {
                                    e7.printStackTrace();
                                }
                            }
                            if (e != 0) {
                                e.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            r1 = jsonWriter;
                            if (r1 != 0) {
                                try {
                                    r1.close();
                                } catch (IOException e8) {
                                    e8.printStackTrace();
                                }
                            }
                            if (r0 != 0) {
                                try {
                                    r0.close();
                                } catch (IOException e9) {
                                    e9.printStackTrace();
                                }
                            }
                            if (e == 0) {
                                throw th;
                            }
                            try {
                                e.close();
                                throw th;
                            } catch (IOException e10) {
                                e10.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (IOException e11) {
                        e = e11;
                    }
                } catch (IOException e12) {
                    e = e12;
                    r0 = 0;
                } catch (Throwable th2) {
                    th = th2;
                    r0 = 0;
                }
            } catch (IOException e13) {
                e = e13;
                e = 0;
                r0 = 0;
            } catch (Throwable th3) {
                th = th3;
                e = 0;
                r0 = 0;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public static void writePolicy(JsonWriter jsonWriter, long j, int i, SparseArray sparseArray) {
        jsonWriter.beginObject();
        jsonWriter.name("policy_version").value(j);
        jsonWriter.name("policy_type").value(i);
        jsonWriter.name("edge_lighting_policy");
        jsonWriter.beginArray();
        int size = sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = sparseArray.keyAt(i2);
            HashMap hashMap = (HashMap) sparseArray.valueAt(i2);
            if (keyAt == 1 || keyAt == 2) {
                Iterator it = hashMap.entrySet().iterator();
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
        HashMap hashMap2 = (HashMap) sparseArray.get(10);
        if (hashMap2 != null) {
            jsonWriter.name("edge_lighting_priority");
            jsonWriter.beginArray();
            Iterator it2 = hashMap2.entrySet().iterator();
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
        HashMap hashMap3 = (HashMap) sparseArray.get(11);
        if (hashMap3 != null) {
            jsonWriter.name("edge_lighting_whitelist");
            jsonWriter.beginArray();
            Iterator it3 = hashMap3.entrySet().iterator();
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
