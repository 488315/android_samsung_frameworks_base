package com.samsung.android.transcode.info;

/* loaded from: classes6.dex */
public class ExportMediaInfo {
    private final int mFrameRate;
    private final int mHeight;
    private final boolean mIsHdr;
    private final String mVideoCodecType;
    private final int mWidth;

    public ExportMediaInfo(int i, int i2, int i3, String str, boolean z) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mFrameRate = i3;
        this.mVideoCodecType = str;
        this.mIsHdr = z;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getFrameRate() {
        return this.mFrameRate;
    }

    public String getVideoCodecType() {
        return this.mVideoCodecType;
    }

    public boolean isHdr() {
        return this.mIsHdr;
    }
}
