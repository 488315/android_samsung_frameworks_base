package com.samsung.android.knox.sdp.core;

import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
