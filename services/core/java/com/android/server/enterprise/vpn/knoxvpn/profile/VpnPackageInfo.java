package com.android.server.enterprise.vpn.knoxvpn.profile;

/* loaded from: classes2.dex */
public class VpnPackageInfo {
    public int mCid;
    public String mPersonaedPackageName;
    public int mUid;

    public VpnPackageInfo(String str, int i, int i2) {
        this.mPersonaedPackageName = str;
        this.mUid = i;
        this.mCid = i2;
    }

    public synchronized String getPackageName() {
        return this.mPersonaedPackageName;
    }

    public synchronized int getUid() {
        return this.mUid;
    }

    public synchronized int getCid() {
        return this.mCid;
    }
}
