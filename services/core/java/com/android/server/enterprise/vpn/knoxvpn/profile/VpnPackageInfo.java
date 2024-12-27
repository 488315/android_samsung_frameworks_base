package com.android.server.enterprise.vpn.knoxvpn.profile;

public final class VpnPackageInfo {
    public int mCid;
    public String mPersonaedPackageName;
    public int mUid;

    public final synchronized int getCid() {
        return this.mCid;
    }

    public final synchronized String getPackageName() {
        return this.mPersonaedPackageName;
    }

    public final synchronized int getUid() {
        return this.mUid;
    }
}
