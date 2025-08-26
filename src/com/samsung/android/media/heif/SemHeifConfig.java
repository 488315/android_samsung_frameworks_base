package com.samsung.android.media.heif;

import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class SemHeifConfig {
    private ByteBuffer mCameraInfoBuffer;
    private ByteBuffer mExifBuffer;
    private final SemInputImage mMaster;
    private SemInputImage mThumb;

    public SemHeifConfig(SemInputImage semInputImage) {
        this.mMaster = semInputImage;
    }

    public void setThumbnailImage(SemInputImage semInputImage) {
        this.mThumb = semInputImage;
    }

    public void setExifData(byte[] bArr, int i, int i2) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i2);
        this.mExifBuffer = byteBufferAllocateDirect;
        byteBufferAllocateDirect.put(bArr, i, i2);
        this.mExifBuffer.flip();
    }

    public void setExifData(ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            this.mExifBuffer = byteBuffer;
            return;
        }
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(byteBuffer.limit());
        this.mExifBuffer = byteBufferAllocateDirect;
        byteBufferAllocateDirect.put(byteBuffer);
    }

    public void setCameraInfo(ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            this.mCameraInfoBuffer = byteBuffer;
            return;
        }
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(byteBuffer.limit());
        this.mCameraInfoBuffer = byteBufferAllocateDirect;
        byteBufferAllocateDirect.put(byteBuffer);
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
