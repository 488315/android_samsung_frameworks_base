package com.samsung.android.knox.sdp.core;

import java.io.Serializable;

/* loaded from: classes6.dex */
public class SdpDomain implements Serializable {
    private final String mAlias;
    private final String mPackageName;

    public SdpDomain(String alias, String pkgName) {
        this.mAlias = alias == null ? "" : alias;
        this.mPackageName = pkgName != null ? pkgName : "";
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
