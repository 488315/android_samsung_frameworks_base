package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppDyingData extends EndpointData {
    public final long cpuTime;
    public final int pid;
    public final String procName;
    public final int uid;

    public AppDyingData(int i, long j, int i2, int i3, String str, long j2) {
        super(i, j);
        this.pid = i2;
        this.uid = i3;
        this.procName = str;
        this.cpuTime = j2;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getPid() {
        return this.pid;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getUid() {
        return this.uid;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("when", this.actualEventTime);
        bundle.putInt("what", this.event);
        bundle.putInt("pid", this.pid);
        bundle.putInt("uid", this.uid);
        bundle.putString("procName", this.procName);
        bundle.putLong("cpuTime", this.cpuTime);
        readExtras(bundle);
        return bundle;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("when", this.actualEventTime);
            jSONObject.put("what", this.event);
            jSONObject.put("pid", this.pid);
            jSONObject.put("uid", this.uid);
            jSONObject.put("procName", this.procName);
            jSONObject.put("cpuTime", this.cpuTime);
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
        int i2 = this.pid;
        int i3 = this.uid;
        String str = this.procName;
        long j2 = this.cpuTime;
        StringBuilder m = SystemServiceManager$$ExternalSyntheticOutline0.m(i, "when : ", j, " | what : ");
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i2, i3, " | pid : ", " | uid : ", m);
        RCPManagerService$$ExternalSyntheticOutline0.m$1(m, " | procName : ", str, " | cpuTime : ");
        m.append(j2);
        return m.toString();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Map toMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("when", Long.toString(this.actualEventTime));
        hashMap.put("what", Integer.toString(this.event));
        hashMap.put("pid", Integer.toString(this.pid));
        hashMap.put("uid", Integer.toString(this.uid));
        hashMap.put("procName", this.procName);
        hashMap.put("cpuTime", Long.toString(this.cpuTime));
        return hashMap;
    }
}
