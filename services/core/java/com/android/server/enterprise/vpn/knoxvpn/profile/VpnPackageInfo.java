package com.android.server.enterprise.vpn.knoxvpn.profile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
