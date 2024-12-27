package com.android.server.enterprise.threatdefense;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public final class RuleParser {
    public final String mPacakagePublicSignature;
    public final String mPackageName;
    public final String mPolicyVersion;
    public final int mProcLength;
    public final int mProcessProcLength;
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

    public final ArrayList getProcList() {
        int size = this.mProcList.size();
        int i = this.mProcLength;
        if (size > 0 && this.mProcList.size() == i) {
            return this.mProcList;
        }
        Log.e("RuleParser", "Invalid proc : " + this.mProcList.size() + "/" + i);
        return null;
    }

    public final ArrayList getProcessProcList() {
        if (this.mProcessProcList.size() > 0
                && this.mProcessProcList.size() == this.mProcessProcLength) {
            return this.mProcessProcList;
        }
        Log.e(
                "RuleParser",
                "Invalid process proc : "
                        + this.mProcessProcList.size()
                        + "/"
                        + this.mProcessProcList);
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mPolicyVersion);
        sb.append("|");
        sb.append(this.mPackageName);
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
