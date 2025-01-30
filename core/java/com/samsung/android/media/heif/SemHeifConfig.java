package com.samsung.android.media.heif;

import java.nio.ByteBuffer;

/* loaded from: classes5.dex */
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
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(length);
        this.mExifBuffer = allocateDirect;
        allocateDirect.put(buffer, offset, length);
        this.mExifBuffer.flip();
    }

    public void setExifData(ByteBuffer exifBuffer) {
        if (exifBuffer.isDirect()) {
            this.mExifBuffer = exifBuffer;
            return;
        }
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(exifBuffer.limit());
        this.mExifBuffer = allocateDirect;
        allocateDirect.put(exifBuffer);
    }

    public void setCameraInfo(ByteBuffer info) {
        if (info.isDirect()) {
            this.mCameraInfoBuffer = info;
            return;
        }
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(info.limit());
        this.mCameraInfoBuffer = allocateDirect;
        allocateDirect.put(info);
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
