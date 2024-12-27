package com.android.server.enterprise.threatdefense;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public final class RUFSParser {
    public final int mExceptionLength;
    public final ArrayList mExceptions = new ArrayList();
    public final String mPolicy;
    public final String mVersion;

    public RUFSParser(String str) {
        JSONObject jSONObject = new JSONObject(str);
        this.mPolicy = jSONObject.getString("policy");
        this.mVersion = jSONObject.getString("version");
        JSONArray jSONArray = new JSONArray(jSONObject.getString("exceptions"));
        for (int i = 0; i < jSONArray.length(); i++) {
            String obj = jSONArray.get(i).toString();
            if (!obj.isEmpty()) {
                this.mExceptions.add(obj);
            }
        }
        this.mExceptionLength = this.mExceptions.size();
    }

    public final ArrayList getExceptionList() {
        int size = this.mExceptions.size();
        int i = this.mExceptionLength;
        if (size > 0 && this.mExceptions.size() == i) {
            return this.mExceptions;
        }
        Log.e("RUFSParser", "No exceptions : " + this.mExceptions.size() + "/" + i);
        return null;
    }

    public final int getPolicyVersion() {
        String str = this.mPolicy;
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            throw new NumberFormatException("RUFSParser, Invalid policy format : " + str);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPolicyVersion());
        sb.append("|");
        String str = this.mVersion;
        try {
            sb.append(Integer.parseInt(str));
            sb.append("|");
            sb.append(this.mExceptions.size());
            sb.append("|");
            if (ThreatDefenseService.DEBUG) {
                Iterator it = this.mExceptions.iterator();
                while (it.hasNext()) {
                    sb.append((String) it.next());
                    sb.append("|");
                }
            }
            return sb.toString();
        } catch (NumberFormatException unused) {
            throw new NumberFormatException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "RUFSParser, Invalid format : ", str));
        }
    }
}
