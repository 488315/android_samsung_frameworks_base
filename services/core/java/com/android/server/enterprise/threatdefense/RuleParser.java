package com.android.server.enterprise.threatdefense;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class RuleParser {
    public static final String TAG = "RuleParser";
    public String mPacakagePublicSignature;
    public String mPackageName;
    public String mPolicyVersion;
    public int mProcLength;
    public int mProcessProcLength;
    public final ArrayList mProcList = new ArrayList();
    public final ArrayList mProcessProcList = new ArrayList();

    public RuleParser(String str) {
        JSONObject jSONObject = new JSONObject(str);
        this.mPackageName = jSONObject.getString("pkg");
        this.mPacakagePublicSignature = jSONObject.getString("pkg_pem");
        this.mPolicyVersion = jSONObject.getString("version");
        JSONArray jSONArray = new JSONArray(jSONObject.getString("proc_list"));
        for (int i = 0; i < jSONArray.length(); i++) {
            String obj = jSONArray.get(i).toString();
            if (!obj.isEmpty()) {
                this.mProcList.add(obj);
            }
        }
        this.mProcLength = this.mProcList.size();
        JSONArray jSONArray2 = new JSONArray(jSONObject.getString("process_proc_list"));
        for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
            String obj2 = jSONArray2.get(i2).toString();
            if (!obj2.isEmpty()) {
                this.mProcessProcList.add(obj2);
            }
        }
        this.mProcessProcLength = this.mProcessProcList.size();
    }

    public String getVersion() {
        return this.mPolicyVersion;
    }

    public String getPackagePublicSignature() {
        return this.mPacakagePublicSignature;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public ArrayList getProcList() {
        if (this.mProcList.size() <= 0 || this.mProcList.size() != this.mProcLength) {
            Log.e(TAG, "Invalid proc : " + this.mProcList.size() + "/" + this.mProcLength);
            return null;
        }
        return this.mProcList;
    }

    public ArrayList getProcessProcList() {
        if (this.mProcessProcList.size() <= 0 || this.mProcessProcList.size() != this.mProcessProcLength) {
            Log.e(TAG, "Invalid process proc : " + this.mProcessProcList.size() + "/" + this.mProcessProcList);
            return null;
        }
        return this.mProcessProcList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getVersion());
        sb.append("|");
        sb.append(getPackageName());
        sb.append("|");
        sb.append(this.mProcList.size());
        sb.append("|");
        sb.append(this.mProcessProcList.size());
        sb.append("|");
        if (ThreatDefenseService.DEBUG) {
            Iterator it = this.mProcList.iterator();
            while (it.hasNext()) {
                sb.append((String) it.next());
                sb.append("|");
            }
            Iterator it2 = this.mProcessProcList.iterator();
            while (it2.hasNext()) {
                sb.append((String) it2.next());
                sb.append("|");
            }
        }
        return sb.toString();
    }
}
