package com.samsung.android.media.heif;

import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class SemHeifConfig {
    private ByteBuffer mCameraInfoBuffer;
    private ByteBuffer mExifBuffer;
    private final SemInputImage mMaster;
    private SemInputImage mThumb;

    public SemHeifConfig(SemInputImage master) {
        this.mMaster = master;
    }

    public void setThumbnailImage(SemInputImage image) {
        this.mThumb = image;
    }

    public void setExifData(byte[] buffer, int offset, int length) {
        this.mExifBuffer = ByteBuffer.allocateDirect(length);
        this.mExifBuffer.put(buffer, offset, length);
        this.mExifBuffer.flip();
    }

    public void setExifData(ByteBuffer exifBuffer) {
        if (exifBuffer.isDirect()) {
            this.mExifBuffer = exifBuffer;
        } else {
            this.mExifBuffer = ByteBuffer.allocateDirect(exifBuffer.limit());
            this.mExifBuffer.put(exifBuffer);
        }
    }

    public void setCameraInfo(ByteBuffer info) {
        if (info.isDirect()) {
            this.mCameraInfoBuffer = info;
        } else {
            this.mCameraInfoBuffer = ByteBuffer.allocateDirect(info.limit());
            this.mCameraInfoBuffer.put(info);
        }
    }

    public SemInputImage getMasterImage() {
        return this.mMaster;
    }

    public SemInputImage getThumbnailImage() {
        return this.mThumb;
    }

    public ByteBuffer getExifData() {
        return this.mExifBuffer;
    }

    public ByteBuffer getCameraInfo() {
        return this.mCameraInfoBuffer;
    }
}
