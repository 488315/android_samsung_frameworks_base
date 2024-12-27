package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SchedProcessExitData extends TracepointData {
    public final String comm;
    public final int pid;
    public final int prio;

    public SchedProcessExitData(
            int i, long j, long j2, long j3, String str, String str2, int i2, int i3) {
        super(i, j, j2, j3, str);
        this.comm = str2;
        this.pid = i2;
        this.prio = i3;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("when", this.actualEventTime);
        bundle.putInt("what", this.event);
        bundle.putInt("pid_base", getPid());
        bundle.putInt("uid", (int) this.uidGid);
        bundle.putString("comm_base", super.comm);
        bundle.putString("comm", this.comm);
        bundle.putInt("pid", this.pid);
        bundle.putInt("prio", this.prio);
        readExtras(bundle);
        return bundle;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("when", this.actualEventTime);
            jSONObject.put("what", this.event);
            jSONObject.put("pid_base", getPid());
            jSONObject.put("uid", (int) this.uidGid);
            jSONObject.put("comm_base", super.comm);
            jSONObject.put("comm", this.comm);
            jSONObject.put("pid", this.pid);
            jSONObject.put("prio", this.prio);
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
        String str = super.comm;
        String str2 = this.comm;
        int i3 = this.pid;
        int i4 = this.prio;
        String readExtras = readExtras(true);
        StringBuilder m =
                SystemServiceManager$$ExternalSyntheticOutline0.m(i, "when : ", j, " | what : ");
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                pid, i2, " | pid_base : ", " | uid : ", m);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                m, " | comm_base : ", str, " | comm : ", str2);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                i3, i4, " | pid : ", " | prio : ", m);
        m.append(readExtras);
        return m.toString();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Map toMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("when", Long.toString(this.actualEventTime));
        hashMap.put("what", Integer.toString(this.event));
        hashMap.put("pid_base", Integer.toString(getPid()));
        hashMap.put("uid", Integer.toString((int) this.uidGid));
        hashMap.put("comm_base", super.comm);
        hashMap.put("comm", this.comm);
        hashMap.put("pid", Integer.toString(this.pid));
        hashMap.put("prio", Integer.toString(this.prio));
        return hashMap;
    }
}
