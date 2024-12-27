package com.android.server.asks;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RUFSContainer {
    public boolean mHasRUFSToken = false;
    public boolean mIsDelta = false;
    public String mDigestInToken = "";
    public String mRUFSpolicyPath = "";
    public String mRUFSpolicyVersion = "";
    public String mRUFSpolicyDeltaVersion = "";
    public long mSizeofFiles = 0;
    public long mSizeofZip = 0;
    public String mADPModels = null;
    public String mADPCarriers = null;
    public String mASKSRNEWModels = null;
    public String mASKSRNEWCarriers = null;

    public final void setADPCarriers(String str) {
        this.mADPCarriers = str;
    }

    public final void setADPModels(String str) {
        this.mADPModels = str;
    }

    public final void setASKSRNEWCarriers(String str) {
        this.mASKSRNEWCarriers = str;
    }

    public final void setASKSRNEWModels(String str) {
        this.mASKSRNEWModels = str;
    }

    public final void setDigest(String str) {
        this.mDigestInToken = str;
    }

    public final void setSizeofFiles(long j) {
        this.mSizeofFiles = j;
    }

    public final void setSizeofZip(long j) {
        this.mSizeofZip = j;
    }
}
