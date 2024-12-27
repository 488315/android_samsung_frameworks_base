package com.samsung.android.transcode.info;

/* loaded from: classes6.dex */
public class ExportMediaInfo {
    private final int mFrameRate;
    private final int mHeight;
    private final boolean mIsHdr;
    private final String mVideoCodecType;
    private final int mWidth;

    public ExportMediaInfo(
            int width, int height, int frameRate, String videoCodecType, boolean isHdr) {
        this.mWidth = width;
        this.mHeight = height;
        this.mFrameRate = frameRate;
        this.mVideoCodecType = videoCodecType;
        this.mIsHdr = isHdr;
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
