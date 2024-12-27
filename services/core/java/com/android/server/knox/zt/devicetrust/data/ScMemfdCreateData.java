package com.android.server.knox.zt.devicetrust.data;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.Bundle;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class ScMemfdCreateData extends TracepointData {
    public final int flags;
    public final long ret;
    public final String uname_ptr;

    public ScMemfdCreateData(
            int i, String str, int i2, long j, long j2, long j3, long j4, String str2) {
        super(i, j2, j3, j4, str2);
        this.uname_ptr = str;
        this.flags = i2;
        this.ret = j;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("when", this.actualEventTime);
        bundle.putInt("what", this.event);
        bundle.putInt("pid", getPid());
        bundle.putInt("uid", (int) this.uidGid);
        bundle.putString("comm", this.comm);
        bundle.putString("uname_ptr", this.uname_ptr);
        bundle.putInt("flags", this.flags);
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
            jSONObject.put("uname_ptr", this.uname_ptr);
            jSONObject.put("flags", this.flags);
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
        String str2 = this.uname_ptr;
        int i3 = this.flags;
        long j2 = this.ret;
        String readExtras = readExtras(true);
        StringBuilder m =
                SystemServiceManager$$ExternalSyntheticOutline0.m(i, "when : ", j, " | what : ");
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                pid, i2, " | pid : ", " | uid : ", m);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                m, " | comm : ", str, " | uname_ptr : ", str2);
        m.append(" | flags : ");
        m.append(i3);
        m.append(" | ret : ");
        return AudioConfig$$ExternalSyntheticOutline0.m(m, j2, readExtras);
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Map toMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("when", Long.toString(this.actualEventTime));
        hashMap.put("what", Integer.toString(this.event));
        hashMap.put("pid", Integer.toString(getPid()));
        hashMap.put("uid", Integer.toString((int) this.uidGid));
        hashMap.put("comm", this.comm);
        hashMap.put("uname_ptr", this.uname_ptr);
        hashMap.put("flags", Integer.toString(this.flags));
        hashMap.put("ret", Long.toString(this.ret));
        return hashMap;
    }
}
