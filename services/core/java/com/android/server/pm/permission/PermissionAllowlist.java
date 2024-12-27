package com.android.server.pm.permission;

import android.util.ArrayMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PermissionAllowlist {
    public final ArrayMap mOemAppAllowlist = new ArrayMap();
    public final ArrayMap mPrivilegedAppAllowlist = new ArrayMap();
    public final ArrayMap mVendorPrivilegedAppAllowlist = new ArrayMap();
    public final ArrayMap mProductPrivilegedAppAllowlist = new ArrayMap();
    public final ArrayMap mSystemExtPrivilegedAppAllowlist = new ArrayMap();
    public final ArrayMap mApexPrivilegedAppAllowlists = new ArrayMap();
    public final ArrayMap mSignatureAppAllowlist = new ArrayMap();
    public final ArrayMap mVendorSignatureAppAllowlist = new ArrayMap();
    public final ArrayMap mProductSignatureAppAllowlist = new ArrayMap();
    public final ArrayMap mSystemExtSignatureAppAllowlist = new ArrayMap();
    public final ArrayMap mApexSignatureAppAllowlist = new ArrayMap();

    public final Boolean getPrivilegedAppAllowlistState(String str, String str2) {
        ArrayMap arrayMap = (ArrayMap) this.mPrivilegedAppAllowlist.get(str);
        if (arrayMap == null) {
            return null;
        }
        return (Boolean) arrayMap.get(str2);
    }
}
