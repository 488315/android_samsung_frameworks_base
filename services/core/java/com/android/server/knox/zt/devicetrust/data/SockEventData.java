package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class SockEventData extends EndpointData {
    public final String daddr;
    public final int dport;
    public final int exitCode;
    public final int family;
    public final int fd;
    public final String filepath;
    public final int gid;
    public final int newstate;
    public final int oldstate;
    public final int pid;
    public final int ppid;
    public final int protocol;
    public final String saddr;
    public final int sport;
    public final int syscall;
    public final int tid;
    public final int type;
    public final int uid;

    public SockEventData(
            int i,
            long j,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6,
            int i7,
            int i8,
            int i9,
            int i10,
            int i11,
            int i12,
            int i13,
            int i14,
            int i15,
            int i16,
            String str,
            String str2,
            String str3) {
        super(i, j);
        this.syscall = i2;
        this.exitCode = i3;
        this.tid = i4;
        this.pid = i5;
        this.ppid = i6;
        this.uid = i7;
        this.gid = i8;
        this.oldstate = i9;
        this.newstate = i10;
        this.fd = i11;
        this.family = i12;
        this.type = i13;
        this.protocol = i14;
        this.sport = i15;
        this.dport = i16;
        this.saddr = str;
        this.daddr = str2;
        this.filepath = str3;
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
        bundle.putInt("syscall", this.syscall);
        bundle.putInt("exitCode", this.exitCode);
        bundle.putInt("tid", this.tid);
        bundle.putInt("pid", this.pid);
        bundle.putInt("ppid", this.ppid);
        bundle.putInt("uid", this.uid);
        bundle.putInt("gid", this.gid);
        bundle.putInt("oldstate", this.oldstate);
        bundle.putInt("newstate", this.newstate);
        bundle.putInt("fd", this.fd);
        bundle.putInt("family", this.family);
        bundle.putInt("type", this.type);
        bundle.putInt("protocol", this.protocol);
        bundle.putInt("sport", this.sport);
        bundle.putInt("dport", this.dport);
        bundle.putString("saddr", this.saddr);
        bundle.putString("daddr", this.daddr);
        bundle.putString("filepath", this.filepath);
        readExtras(bundle);
        return bundle;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("when", this.actualEventTime);
            jSONObject.put("what", this.event);
            jSONObject.put("syscall", this.syscall);
            jSONObject.put("exitCode", this.exitCode);
            jSONObject.put("tid", this.tid);
            jSONObject.put("pid", this.pid);
            jSONObject.put("ppid", this.ppid);
            jSONObject.put("uid", this.uid);
            jSONObject.put("gid", this.gid);
            jSONObject.put("oldstate", this.oldstate);
            jSONObject.put("newstate", this.newstate);
            jSONObject.put("fd", this.fd);
            jSONObject.put("family", this.family);
            jSONObject.put("type", this.type);
            jSONObject.put("protocol", this.protocol);
            jSONObject.put("sport", this.sport);
            jSONObject.put("dport", this.dport);
            jSONObject.put("saddr", this.saddr);
            jSONObject.put("daddr", this.daddr);
            jSONObject.put("filepath", this.filepath);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toLine() {
        return String.format(
                Locale.US,
                "when : %d | what : %d | syscall : %d | exitCode : %d | tid : %d | pid : %d | ppid"
                    + " : %d | uid : %d | gid : %d | oldstate : %d | newstate : %d | fd : %d |"
                    + " family : %d | type : %d | protocol : %d | sport : %d | dport : %d | saddr :"
                    + " %d | daddr : %d | filepath : %s%s",
                Long.valueOf(this.actualEventTime),
                Integer.valueOf(this.event),
                Integer.valueOf(this.syscall),
                Integer.valueOf(this.exitCode),
                Integer.valueOf(this.tid),
                Integer.valueOf(this.pid),
                Integer.valueOf(this.ppid),
                Integer.valueOf(this.uid),
                Integer.valueOf(this.gid),
                Integer.valueOf(this.oldstate),
                Integer.valueOf(this.newstate),
                Integer.valueOf(this.fd),
                Integer.valueOf(this.family),
                Integer.valueOf(this.type),
                Integer.valueOf(this.protocol),
                Integer.valueOf(this.sport),
                Integer.valueOf(this.dport),
                this.saddr,
                this.daddr,
                this.filepath,
                readExtras(true));
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Map toMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("when", Long.toString(this.actualEventTime));
        hashMap.put("what", Integer.toString(this.event));
        hashMap.put("syscall", Integer.toString(this.syscall));
        hashMap.put("exitCode", Integer.toString(this.exitCode));
        hashMap.put("tid", Integer.toString(this.tid));
        hashMap.put("pid", Integer.toString(this.pid));
        hashMap.put("ppid", Integer.toString(this.ppid));
        hashMap.put("uid", Integer.toString(this.uid));
        hashMap.put("gid", Integer.toString(this.gid));
        hashMap.put("oldstate", Integer.toString(this.oldstate));
        hashMap.put("newstate", Integer.toString(this.newstate));
        hashMap.put("fd", Integer.toString(this.fd));
        hashMap.put("family", Integer.toString(this.family));
        hashMap.put("type", Integer.toString(this.type));
        hashMap.put("protocol", Integer.toString(this.protocol));
        hashMap.put("sport", Integer.toString(this.sport));
        hashMap.put("dport", Integer.toString(this.dport));
        hashMap.put("saddr", this.saddr);
        hashMap.put("daddr", this.daddr);
        hashMap.put("filepath", this.filepath);
        return hashMap;
    }
}
