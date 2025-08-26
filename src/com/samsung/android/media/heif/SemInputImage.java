package com.samsung.android.media.heif;

import java.io.FileDescriptor;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class SemInputImage {
    private ByteBuffer mBuffer;
    private final int mColorFormat;
    private FileDescriptor mFd;
    private final int mHeight;
    private ByteBuffer mIccProfile;
    private int mRotationDegree;
    private int mSliceHeight;
    private int mStride;
    private final int mWidth;

    private SemInputImage(int i, int i2, int i3) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mColorFormat = i3;
        this.mStride = i;
        this.mSliceHeight = i2;
        this.mRotationDegree = 0;
    }

    public SemInputImage(FileDescriptor fileDescriptor, int i, int i2, int i3) {
        this(i, i2, i3);
        this.mFd = fileDescriptor;
    }

    public SemInputImage(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        this(i3, i4, i5);
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i2);
        this.mBuffer = byteBufferAllocateDirect;
        byteBufferAllocateDirect.put(bArr, i, i2);
        this.mBuffer.flip();
    }

    public SemInputImage(ByteBuffer byteBuffer, int i, int i2, int i3) {
        this(i, i2, i3);
        this.mBuffer = byteBuffer;
    }

    FileDescriptor getFileDescriptor() {
        return this.mFd;
    }

    ByteBuffer getBuffer() {
        return this.mBuffer;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    int getStride() {
        return this.mStride;
    }

    int getSliceHeight() {
        return this.mSliceHeight;
    }

    public int getRotationDegree() {
        return this.mRotationDegree;
    }

    public int getColorFormat() {
        return this.mColorFormat;
    }

    public ByteBuffer getIccProfile() {
        return this.mIccProfile;
    }

    public void setStride(int i) {
        this.mStride = i;
    }

    public void setSliceHeight(int i) {
        this.mSliceHeight = i;
    }

    public void setRotationDegree(int i) {
        this.mRotationDegree = i;
    }

    public void setIccProfile(ByteBuffer byteBuffer) {
        this.mIccProfile = byteBuffer;
    }
}
