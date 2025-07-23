package com.samsung.android.media.heif;

import android.media.MediaFormat;
import com.samsung.android.media.heif.jni.AMessageJNI;
import com.samsung.android.sume.core.message.Message;
import java.io.FileDescriptor;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
final class CaptureSourceInternal {
    public static final String KEY_CAMERA_INFO = "camera-info";
    public static final String KEY_CAMERA_INFO_SIZE = "camera-info-size";
    private AMessageJNI msg = new AMessageJNI();

    CaptureSourceInternal() {
    }

    public AMessageJNI getMsg() {
        return this.msg;
    }

    public void setInputFileDescriptor(FileDescriptor fileDescriptor) {
        this.msg.setFileDescriptor("input-fd", fileDescriptor);
    }

    public void setInputByteBuffer(ByteBuffer byteBuffer) {
        if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("bytebuffer must allocate direct");
        }
        this.msg.setByteBuffer(Message.KEY_IN_BUFFER, byteBuffer);
    }

    public void setId(int i) {
        this.msg.setInt("id", i);
    }

    public void setWidth(int i) {
        this.msg.setInt("width", i);
    }

    public void setHeight(int i) {
        this.msg.setInt("height", i);
    }

    public void setStride(int i) {
        this.msg.setInt("stride", i);
    }

    public void setSliceHeight(int i) {
        this.msg.setInt(MediaFormat.KEY_SLICE_HEIGHT, i);
    }

    public void setRotationDegree(int i) {
        this.msg.setInt("rotation-degrees", i);
    }

    public void setColorFormat(int i) {
        this.msg.setInt(MediaFormat.KEY_COLOR_FORMAT, i);
    }

    public void setThumbnail(CaptureSourceInternal captureSourceInternal) {
        this.msg.setMessage("thumbnail", captureSourceInternal.msg);
    }

    public void setImageRole(int i) {
        this.msg.setInt("image-role", i);
    }

    public void setExifData(ByteBuffer byteBuffer) {
        this.msg.setByteBuffer("exif-buffer", byteBuffer);
        this.msg.setInt("exif-buffer-size", byteBuffer.limit());
    }

    public void setIccProfile(ByteBuffer byteBuffer) {
        this.msg.setByteBuffer("icc-buffer", byteBuffer);
        this.msg.setInt("icc-buffer-size", byteBuffer.limit());
    }

    public void setCameraInfo(ByteBuffer byteBuffer) {
        this.msg.setByteBuffer(KEY_CAMERA_INFO, byteBuffer);
        this.msg.setInt(KEY_CAMERA_INFO_SIZE, byteBuffer.limit());
    }

    public int getWidth() {
        return this.msg.getInt("width");
    }

    public int getHeight() {
        return this.msg.getInt("height");
    }

    public int getStride() {
        return this.msg.getInt("stride");
    }

    public int getSliceHeight() {
        return this.msg.getInt(MediaFormat.KEY_SLICE_HEIGHT);
    }

    public int getRotationDegree() {
        return this.msg.getInt("rotation-degrees");
    }

    public int getColorFormat() {
        return this.msg.getInt(MediaFormat.KEY_COLOR_FORMAT);
    }

    public int getImageRole() {
        return this.msg.getInt("image-role");
    }

    static class Parser {
        Parser() {
        }

        static CaptureSourceInternal makeInternalSource(SemInputImage semInputImage) {
            CaptureSourceInternal captureSourceInternal = new CaptureSourceInternal();
            captureSourceInternal.setWidth(semInputImage.getWidth());
            captureSourceInternal.setHeight(semInputImage.getHeight());
            captureSourceInternal.setStride(semInputImage.getStride());
            captureSourceInternal.setSliceHeight(semInputImage.getSliceHeight());
            captureSourceInternal.setRotationDegree(semInputImage.getRotationDegree());
            captureSourceInternal.setColorFormat(semInputImage.getColorFormat());
            if (semInputImage.getFileDescriptor() != null) {
                captureSourceInternal.setInputFileDescriptor(semInputImage.getFileDescriptor());
            }
            if (semInputImage.getBuffer() != null) {
                captureSourceInternal.setInputByteBuffer(semInputImage.getBuffer());
            }
            if (semInputImage.getIccProfile() != null) {
                captureSourceInternal.setIccProfile(semInputImage.getIccProfile());
            }
            return captureSourceInternal;
        }
    }
}
