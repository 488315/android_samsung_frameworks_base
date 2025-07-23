package com.samsung.android.knox.sdp.core;

import java.io.Serializable;

/* loaded from: classes6.dex */
public class SdpDomain implements Serializable {
    private final String mAlias;
    private final String mPackageName;

    public SdpDomain(String str, String str2) {
        this.mAlias = str == null ? "" : str;
        this.mPackageName = str2 == null ? "" : str2;
    }

    public String getAlias() {
        return this.mAlias;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String toString() {
        return "SdpDomain { alias : " + this.mAlias + " / pkgName : " + this.mPackageName + " }";
    }
}
