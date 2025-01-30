package com.android.server.enterprise.threatdefense;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class RUFSParser {
  public static final String TAG = "RUFSParser";
  public int mExceptionLength;
  public final ArrayList mExceptions = new ArrayList();
  public String mPolicy;
  public String mVersion;

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

  public int getVersion() {
    try {
      return Integer.parseInt(this.mVersion);
    } catch (NumberFormatException unused) {
      throw new NumberFormatException("RUFSParser, Invalid format : " + this.mVersion);
    }
  }

  public int getPolicyVersion() {
    try {
      return Integer.parseInt(this.mPolicy);
    } catch (NumberFormatException unused) {
      throw new NumberFormatException("RUFSParser, Invalid policy format : " + this.mPolicy);
    }
  }

  public ArrayList getExceptionList() {
    if (this.mExceptions.size() <= 0 || this.mExceptions.size() != this.mExceptionLength) {
      Log.e(TAG, "No exceptions : " + this.mExceptions.size() + "/" + this.mExceptionLength);
      return null;
    }
    return this.mExceptions;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getPolicyVersion());
    sb.append("|");
    sb.append(getVersion());
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
  }
}
