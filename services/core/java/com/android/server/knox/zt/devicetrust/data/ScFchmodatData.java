package com.android.server.knox.zt.devicetrust.data;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.Bundle;

import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ScFchmodatData extends TracepointData {
    public final int dfd;
    public final String filename;
    public final int mode;
    public final long ret;

    public ScFchmodatData(
            int i, int i2, String str, int i3, long j, long j2, long j3, long j4, String str2) {
        super(i, j2, j3, j4, str2);
        this.dfd = i2;
        this.filename = str;
        this.mode = i3;
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
        bundle.putInt("dfd", this.dfd);
        bundle.putString("filename", this.filename);
        bundle.putInt("mode", this.mode);
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
            jSONObject.put("dfd", this.dfd);
            jSONObject.put("filename", this.filename);
            jSONObject.put("mode", this.mode);
            jSONObject.put("mode_t", Integer.toOctalString(this.mode));
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
        int i3 = this.dfd;
        String str2 = this.filename;
        int i4 = this.mode;
        String octalString = Integer.toOctalString(this.mode);
        long j2 = this.ret;
        String readExtras = readExtras(true);
        StringBuilder m =
                SystemServiceManager$$ExternalSyntheticOutline0.m(i, "when : ", j, " | what : ");
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                pid, i2, " | pid : ", " | uid : ", m);
        m.append(" | comm : ");
        m.append(str);
        m.append(" | dfd : ");
        m.append(i3);
        m.append(" | filename : ");
        m.append(str2);
        m.append(" | mode : ");
        m.append(i4);
        RCPManagerService$$ExternalSyntheticOutline0.m$1(
                m, " | mode_t : ", octalString, " | ret : ");
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
        hashMap.put("dfd", Integer.toString(this.dfd));
        hashMap.put("filename", this.filename);
        hashMap.put("mode", Integer.toString(this.mode));
        hashMap.put("ret", Long.toString(this.ret));
        return hashMap;
    }
}
