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

    private SemInputImage(int width, int height, int format) {
        this.mWidth = width;
        this.mHeight = height;
        this.mColorFormat = format;
        this.mStride = width;
        this.mSliceHeight = height;
        this.mRotationDegree = 0;
    }

    public SemInputImage(FileDescriptor fd, int width, int height, int format) {
        this(width, height, format);
        this.mFd = fd;
    }

    public SemInputImage(byte[] buffer, int offset, int bufferLength, int width, int height, int format) {
        this(width, height, format);
        this.mBuffer = ByteBuffer.allocateDirect(bufferLength);
        this.mBuffer.put(buffer, offset, bufferLength);
        this.mBuffer.flip();
    }

    public SemInputImage(ByteBuffer buffer, int width, int height, int format) {
        this(width, height, format);
        this.mBuffer = buffer;
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

    public void setStride(int stride) {
        this.mStride = stride;
    }

    public void setSliceHeight(int sliceHeight) {
        this.mSliceHeight = sliceHeight;
    }

    public void setRotationDegree(int rotationDegree) {
        this.mRotationDegree = rotationDegree;
    }

    public void setIccProfile(ByteBuffer buffer) {
        this.mIccProfile = buffer;
    }
}
