package com.samsung.android.knox.sdp.core;

import java.util.ArrayList;

/* loaded from: classes4.dex */
public class SdpCreationParamBuilder {
    private String mAlias;
    private int mFlags;
    private ArrayList<SdpDomain> mPrivilegedApps;

    public SdpCreationParamBuilder(String str, int i) {
        this.mAlias = str == null ? "" : str;
        this.mFlags = validateFlags(i);
        this.mPrivilegedApps = new ArrayList<>();
    }

    private int validateFlags(int i) {
        if (i < 0 || i > 1) {
            return 0;
        }
        return i;
    }

    public void addPrivilegedApp(SdpDomain sdpDomain) {
        this.mPrivilegedApps.add(sdpDomain);
    }

    public SdpCreationParam getParam() {
        if (this.mAlias == null) {
            return null;
        }
        return new SdpCreationParam(this.mAlias, this.mFlags, this.mPrivilegedApps);
    }

    public void setMdfpp() {
        this.mFlags &= -2;
    }

    public void setMinor() {
        this.mFlags |= 1;
    }
}
