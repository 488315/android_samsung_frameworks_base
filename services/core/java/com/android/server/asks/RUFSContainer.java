package com.android.server.asks;

/* loaded from: classes.dex */
public class RUFSContainer {
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

    public void setHasRUFSToken(boolean z) {
        this.mHasRUFSToken = z;
    }

    public void setDigest(String str) {
        this.mDigestInToken = str;
    }

    public void setPolicyVersion(String str) {
        this.mRUFSpolicyVersion = str;
    }

    public void setDeltaPolicyVersion(String str) {
        this.mRUFSpolicyDeltaVersion = str;
    }

    public void setSizeofFiles(long j) {
        this.mSizeofFiles = j;
    }

    public void setSizeofZip(long j) {
        this.mSizeofZip = j;
    }

    public void setADPModels(String str) {
        this.mADPModels = str;
    }

    public void setADPCarriers(String str) {
        this.mADPCarriers = str;
    }

    public void setASKSRNEWModels(String str) {
        this.mASKSRNEWModels = str;
    }

    public void setASKSRNEWCarriers(String str) {
        this.mASKSRNEWCarriers = str;
    }

    public void setIsDelta(boolean z) {
        this.mIsDelta = z;
    }

    public boolean getIsDelta() {
        return this.mIsDelta;
    }

    public boolean getHasRUFSToken() {
        return this.mHasRUFSToken;
    }

    public String getDigest() {
        return this.mDigestInToken;
    }

    public String getPolicyPath() {
        return this.mRUFSpolicyPath;
    }

    public String getPolicyVersion() {
        return this.mRUFSpolicyVersion;
    }

    public String getDeltaPolicyVersion() {
        return this.mRUFSpolicyDeltaVersion;
    }

    public long getSizeofFiles() {
        return this.mSizeofFiles;
    }

    public long getSizeofzip() {
        return this.mSizeofZip;
    }

    public String getADPModels() {
        return this.mADPModels;
    }

    public String getADPCarriers() {
        return this.mADPCarriers;
    }

    public String getASKSRNEWModels() {
        return this.mASKSRNEWModels;
    }

    public String getASKSRNEWCarriers() {
        return this.mASKSRNEWCarriers;
    }
}
