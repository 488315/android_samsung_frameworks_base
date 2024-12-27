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

    public void setInputFileDescriptor(FileDescriptor fd) {
        this.msg.setFileDescriptor("input-fd", fd);
    }

    public void setInputByteBuffer(ByteBuffer byteBuffer) {
        if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("bytebuffer must allocate direct");
        }
        this.msg.setByteBuffer(Message.KEY_IN_BUFFER, byteBuffer);
    }

    public void setId(int id) {
        this.msg.setInt("id", id);
    }

    public void setWidth(int width) {
        this.msg.setInt("width", width);
    }

    public void setHeight(int height) {
        this.msg.setInt("height", height);
    }

    public void setStride(int stride) {
        this.msg.setInt("stride", stride);
    }

    public void setSliceHeight(int sliceHeight) {
        this.msg.setInt(MediaFormat.KEY_SLICE_HEIGHT, sliceHeight);
    }

    public void setRotationDegree(int rotationDegree) {
        this.msg.setInt("rotation-degrees", rotationDegree);
    }

    public void setColorFormat(int colorFormat) {
        this.msg.setInt(MediaFormat.KEY_COLOR_FORMAT, colorFormat);
    }

    public void setThumbnail(CaptureSourceInternal thumb) {
        this.msg.setMessage("thumbnail", thumb.msg);
    }

    public void setImageRole(int role) {
        this.msg.setInt("image-role", role);
    }

    public void setExifData(ByteBuffer exifBuffer) {
        this.msg.setByteBuffer("exif-buffer", exifBuffer);
        this.msg.setInt("exif-buffer-size", exifBuffer.limit());
    }

    public void setIccProfile(ByteBuffer iccBuffer) {
        this.msg.setByteBuffer("icc-buffer", iccBuffer);
        this.msg.setInt("icc-buffer-size", iccBuffer.limit());
    }

    public void setCameraInfo(ByteBuffer info) {
        this.msg.setByteBuffer(KEY_CAMERA_INFO, info);
        this.msg.setInt(KEY_CAMERA_INFO_SIZE, info.limit());
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

        static CaptureSourceInternal makeInternalSource(SemInputImage in) {
            CaptureSourceInternal source = new CaptureSourceInternal();
            source.setWidth(in.getWidth());
            source.setHeight(in.getHeight());
            source.setStride(in.getStride());
            source.setSliceHeight(in.getSliceHeight());
            source.setRotationDegree(in.getRotationDegree());
            source.setColorFormat(in.getColorFormat());
            if (in.getFileDescriptor() != null) {
                source.setInputFileDescriptor(in.getFileDescriptor());
            }
            if (in.getBuffer() != null) {
                source.setInputByteBuffer(in.getBuffer());
            }
            if (in.getIccProfile() != null) {
                source.setIccProfile(in.getIccProfile());
            }
            return source;
        }
    }
}
