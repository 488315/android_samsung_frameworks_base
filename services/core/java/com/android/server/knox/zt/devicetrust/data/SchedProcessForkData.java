package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SchedProcessForkData extends TracepointData {
    public final String childComm;
    public final int childPid;
    public final String parentComm;
    public final int parentPid;

    public SchedProcessForkData(int i, long j, long j2, long j3, String str, String str2, int i2, String str3, int i3) {
        super(i, j, j2, j3, str);
        this.parentComm = str2;
        this.parentPid = i2;
        this.childComm = str3;
        this.childPid = i3;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("when", this.actualEventTime);
        bundle.putInt("what", this.event);
        bundle.putInt("pid", getPid());
        bundle.putInt("uid", (int) this.uidGid);
        bundle.putString("comm", this.comm);
        bundle.putString("parent_comm", this.parentComm);
        bundle.putInt("parent_pid", this.parentPid);
        bundle.putString("child_comm", this.childComm);
        bundle.putInt("child_pid", this.childPid);
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
            jSONObject.put("parent_comm", this.parentComm);
            jSONObject.put("parent_pid", this.parentPid);
            jSONObject.put("child_comm", this.childComm);
            jSONObject.put("child_pid", this.childPid);
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
        String str2 = this.parentComm;
        int i3 = this.parentPid;
        String str3 = this.childComm;
        int i4 = this.childPid;
        String readExtras = readExtras(true);
        StringBuilder m = SystemServiceManager$$ExternalSyntheticOutline0.m(i, "when : ", j, " | what : ");
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(pid, i2, " | pid : ", " | uid : ", m);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(m, " | comm : ", str, " | parent_comm : ", str2);
        m.append(" | parent_pid : ");
        m.append(i3);
        m.append(" | child_comm : ");
        m.append(str3);
        m.append(" | child_pid : ");
        m.append(i4);
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
        hashMap.put("parent_comm", this.parentComm);
        hashMap.put("parent_pid", Integer.toString(this.parentPid));
        hashMap.put("child_comm", this.childComm);
        hashMap.put("child_pid", Integer.toString(this.childPid));
        return hashMap;
    }
}
