package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.StringJoiner;

public abstract class EndpointData {
    public static final boolean USE_ACTUAL_TIME = true;
    public long actualEventTime;
    public final int event;
    public final long eventTime;
    public EndpointExtras extras;

    public EndpointData(int i, long j) {
        this.event = i;
        this.eventTime = j;
    }

    public EndpointData adjustTime(long j) {
        if (this.actualEventTime == 0) {
            this.actualEventTime = (j + this.eventTime) / 1000000;
        }
        return this;
    }

    public int getEvent() {
        return this.event;
    }

    public abstract int getPid();

    public long getTime() {
        return this.actualEventTime;
    }

    public abstract int getUid();

    public final String readExtras(boolean z) {
        if (this.extras == null) {
            return "";
        }
        StringJoiner stringJoiner = new StringJoiner(z ? " | " : "", " | ", "");
        if (this.extras.label != null) {
            stringJoiner.add("label : " + this.extras.label);
        }
        if (this.extras.packageName != null) {
            stringJoiner.add("pkgName : " + this.extras.packageName);
        }
        if (this.extras.processName != null) {
            stringJoiner.add("procName : " + this.extras.processName);
        }
        return stringJoiner.toString();
    }

    public final void readExtras(Bundle bundle) {
        EndpointExtras endpointExtras;
        if (bundle == null || (endpointExtras = this.extras) == null) {
            return;
        }
        String str = endpointExtras.label;
        if (str != null) {
            bundle.putString("label", str);
        }
        String str2 = this.extras.packageName;
        if (str2 != null) {
            bundle.putString("pkgName", str2);
        }
        String str3 = this.extras.processName;
        if (str3 != null) {
            bundle.putString("procName", str3);
        }
    }

    public final void readExtras(JSONObject jSONObject) throws JSONException {
        EndpointExtras endpointExtras;
        if (jSONObject == null || (endpointExtras = this.extras) == null) {
            return;
        }
        String str = endpointExtras.label;
        if (str != null) {
            jSONObject.put("label", str);
        }
        String str2 = this.extras.packageName;
        if (str2 != null) {
            jSONObject.put("pkgName", str2);
        }
        String str3 = this.extras.processName;
        if (str3 != null) {
            jSONObject.put("procName", str3);
        }
    }

    public abstract Bundle toBundle();

    public abstract String toJson();

    public abstract String toLine();

    public abstract Map toMap();

    public final EndpointData updateExtras(int i) {
        if (i == 0) {
            return this;
        }
        if (this.extras == null) {
            this.extras = new EndpointExtras();
        }
        if ((i & 1) != 0) {
            this.extras.processName = Utils.InstanceHolder.INSTANCE.getProcessNameForPid(getPid());
        }
        if ((i & 2) != 0) {
            this.extras.packageName = Utils.InstanceHolder.INSTANCE.getPackageNameForUid(getUid());
        }
        if ((i & 4) != 0) {
            this.extras.label = Utils.InstanceHolder.INSTANCE.getSecurityContextForPid(getPid());
        }
        return this;
    }
}
