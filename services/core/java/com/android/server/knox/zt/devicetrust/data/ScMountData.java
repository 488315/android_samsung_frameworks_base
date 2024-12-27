package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ScMountData extends TracepointData {
    public final String data;
    public final String devName;
    public final String dirName;
    public final long flags;
    public final long ret;
    public final String type;

    public ScMountData(
            int i,
            String str,
            String str2,
            String str3,
            long j,
            String str4,
            long j2,
            long j3,
            long j4,
            long j5,
            String str5) {
        super(i, j3, j4, j5, str5);
        this.devName = str;
        this.dirName = str2;
        this.type = str3;
        this.flags = j;
        this.data = str4;
        this.ret = j2;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("when", this.actualEventTime);
        bundle.putInt("what", this.event);
        bundle.putInt("pid", getPid());
        bundle.putInt("uid", (int) this.uidGid);
        bundle.putString("comm", this.comm);
        bundle.putString("dev_name", this.devName);
        bundle.putString("dir_name", this.dirName);
        bundle.putString("type", this.type);
        bundle.putLong("flags", this.flags);
        bundle.putString("data", this.data);
        bundle.putLong("ret", this.ret);
        readExtras(bundle);
        return bundle;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("when", this.actualEventTime);
            jSONObject.put("what", this.event);
            jSONObject.put("pid", getPid());
            jSONObject.put("uid", (int) this.uidGid);
            jSONObject.put("comm", this.comm);
            jSONObject.put("dev_name", this.devName);
            jSONObject.put("dir_name", this.dirName);
            jSONObject.put("type", this.type);
            jSONObject.put("flags", this.flags);
            jSONObject.put("data", this.data);
            jSONObject.put("ret", this.ret);
            readExtras(jSONObject);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toLine() {
        Locale locale = Locale.US;
        long j = this.actualEventTime;
        int i = this.event;
        int pid = getPid();
        int i2 = (int) this.uidGid;
        String str = this.comm;
        String str2 = this.devName;
        String str3 = this.dirName;
        String str4 = this.type;
        long j2 = this.flags;
        String str5 = this.data;
        long j3 = this.ret;
        String readExtras = readExtras(true);
        StringBuilder m =
                SystemServiceManager$$ExternalSyntheticOutline0.m(i, "when : ", j, " | what : ");
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                pid, i2, " | pid : ", " | uid : ", m);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                m, " | comm : ", str, " | dev_name : ", str2);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                m, " | dir_name : ", str3, " | type : ", str4);
        BootReceiver$$ExternalSyntheticOutline0.m(m, " | flags : ", j2, " | data : ");
        m.append(str5);
        m.append(" | ret : ");
        m.append(j3);
        m.append(readExtras);
        return m.toString();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Map toMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("when", Long.toString(this.actualEventTime));
        hashMap.put("what", Integer.toString(this.event));
        hashMap.put("pid", Integer.toString(getPid()));
        hashMap.put("uid", Integer.toString((int) this.uidGid));
        hashMap.put("comm", this.comm);
        hashMap.put("dev_name", this.devName);
        hashMap.put("dir_name", this.dirName);
        hashMap.put("type", this.type);
        hashMap.put("flags", Long.toString(this.flags));
        hashMap.put("data", this.data);
        hashMap.put("ret", Long.toString(this.ret));
        return hashMap;
    }
}
