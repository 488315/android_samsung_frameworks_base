package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
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
public final class ProcEventData extends EndpointData {
    public final long atime;
    public final String cmdline;
    public final long ctime;
    public final String cwd;
    public final int egid;
    public final int euid;
    public final int exitCode;
    public final String filepath;
    public final int fsgid;
    public final int fsuid;
    public final int gid;
    public final long mtime;
    public final int ownerGid;
    public final int ownerUid;
    public final int pid;
    public final int ppid;
    public final long reserved_1;
    public final long reserved_2;
    public final long reserved_3;
    public final int sgid;
    public final int suid;
    public final int syscall;
    public final int tid;
    public final int uid;

    public ProcEventData(int i, long j, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, long j2, long j3, long j4, String str, String str2, String str3, long j5, long j6, long j7) {
        super(i, j);
        this.syscall = i2;
        this.exitCode = i3;
        this.tid = i4;
        this.pid = i5;
        this.ppid = i6;
        this.uid = i7;
        this.gid = i8;
        this.suid = i9;
        this.sgid = i10;
        this.euid = i11;
        this.egid = i12;
        this.fsuid = i13;
        this.fsgid = i14;
        this.ownerUid = i15;
        this.ownerGid = i16;
        this.atime = j2;
        this.mtime = j3;
        this.ctime = j4;
        this.filepath = str;
        this.cwd = str2;
        this.cmdline = str3;
        this.reserved_1 = j5;
        this.reserved_2 = j6;
        this.reserved_3 = j7;
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
        bundle.putInt("suid", this.suid);
        bundle.putInt("sgid", this.sgid);
        bundle.putInt("euid", this.euid);
        bundle.putInt("egid", this.egid);
        bundle.putInt("fsuid", this.fsuid);
        bundle.putInt("fsgid", this.fsgid);
        bundle.putInt("ownerUid", this.ownerUid);
        bundle.putInt("ownerGid", this.ownerGid);
        bundle.putLong("atime", this.atime);
        bundle.putLong("mtime", this.mtime);
        bundle.putLong("ctime", this.ctime);
        bundle.putString("filepath", this.filepath);
        bundle.putString("cwd", this.cwd);
        bundle.putString("cmdline", this.cmdline);
        bundle.putLong("reserved_1", this.reserved_1);
        bundle.putLong("reserved_2", this.reserved_2);
        bundle.putLong("reserved_3", this.reserved_3);
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
            jSONObject.put("suid", this.suid);
            jSONObject.put("sgid", this.sgid);
            jSONObject.put("euid", this.euid);
            jSONObject.put("egid", this.egid);
            jSONObject.put("fsuid", this.fsuid);
            jSONObject.put("fsgid", this.fsgid);
            jSONObject.put("ownerUid", this.ownerUid);
            jSONObject.put("ownerGid", this.ownerGid);
            jSONObject.put("atime", this.atime);
            jSONObject.put("mtime", this.mtime);
            jSONObject.put("ctime", this.ctime);
            jSONObject.put("filepath", this.filepath);
            jSONObject.put("cwd", this.cwd);
            jSONObject.put("cmdline", this.cmdline);
            jSONObject.put("reserved_1", this.reserved_1);
            jSONObject.put("reserved_2", this.reserved_2);
            jSONObject.put("reserved_3", this.reserved_3);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toLine() {
        Locale locale = Locale.US;
        long j = this.actualEventTime;
        int i = this.event;
        int i2 = this.syscall;
        int i3 = this.exitCode;
        int i4 = this.tid;
        int i5 = this.pid;
        int i6 = this.ppid;
        int i7 = this.uid;
        int i8 = this.gid;
        int i9 = this.suid;
        int i10 = this.sgid;
        int i11 = this.euid;
        int i12 = this.egid;
        int i13 = this.fsuid;
        int i14 = this.fsgid;
        int i15 = this.ownerUid;
        int i16 = this.ownerGid;
        long j2 = this.atime;
        long j3 = this.mtime;
        long j4 = this.ctime;
        String str = this.filepath;
        String str2 = this.cwd;
        String str3 = this.cmdline;
        long j5 = this.reserved_1;
        long j6 = this.reserved_2;
        long j7 = this.reserved_3;
        String readExtras = readExtras(true);
        StringBuilder m = SystemServiceManager$$ExternalSyntheticOutline0.m(i, "when : ", j, " | what : ");
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i2, i3, " | syscall : ", " | exitCode : ", m);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i4, i5, " | tid : ", " | pid : ", m);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i6, i7, " | ppid : ", " | uid : ", m);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i8, i9, " | gid : ", " | suid : ", m);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i10, i11, " | sgid : ", " | euid : ", m);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i12, i13, " | egid : ", " | fsuid : ", m);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i14, i15, " | fsgid : ", " | ownerUid : ", m);
        m.append(" | ownerGid : ");
        m.append(i16);
        m.append(" | atime : ");
        m.append(j2);
        BootReceiver$$ExternalSyntheticOutline0.m(m, " | mtime : ", j3, " | ctime : ");
        m.append(j4);
        m.append(" | filepath : ");
        m.append(str);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(m, " | cwd : ", str2, " | cmdline : ", str3);
        BootReceiver$$ExternalSyntheticOutline0.m(m, "reserved_1 : ", j5, " | reserved_2 : ");
        m.append(j6);
        m.append(" | reserved_3 : ");
        m.append(j7);
        m.append(readExtras);
        return m.toString();
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
        hashMap.put("suid", Integer.toString(this.suid));
        hashMap.put("sgid", Integer.toString(this.sgid));
        hashMap.put("euid", Integer.toString(this.euid));
        hashMap.put("egid", Integer.toString(this.egid));
        hashMap.put("fsuid", Integer.toString(this.fsuid));
        hashMap.put("fsgid", Integer.toString(this.fsgid));
        hashMap.put("ownerUid", Integer.toString(this.ownerUid));
        hashMap.put("ownerGid", Integer.toString(this.ownerGid));
        hashMap.put("atime", Long.toString(this.atime));
        hashMap.put("mtime", Long.toString(this.mtime));
        hashMap.put("ctime", Long.toString(this.ctime));
        hashMap.put("filepath", this.filepath);
        hashMap.put("cwd", this.cwd);
        hashMap.put("cmdline", this.cmdline);
        hashMap.put("reserved_1", Long.toString(this.reserved_1));
        hashMap.put("reserved_2", Long.toString(this.reserved_2));
        hashMap.put("reserved_3", Long.toString(this.reserved_3));
        return hashMap;
    }
}
