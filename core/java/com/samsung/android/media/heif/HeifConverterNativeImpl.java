package com.samsung.android.media.heif;

import android.media.MediaFormat;
import com.samsung.android.media.heif.CaptureSourceInternal;
import com.samsung.android.media.heif.jni.AMessageJNI;
import com.samsung.android.media.heif.jni.HeifCaptureJNI;
import com.samsung.android.sume.core.message.Message;
import java.io.FileDescriptor;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
final class HeifConverterNativeImpl implements SemHeifConverter {
    private final HeifCaptureJNI mCaptureNative = new HeifCaptureJNI();
    private final int mFormat;
    private final int mQuality;

    HeifConverterNativeImpl(int format, int quality) {
        this.mFormat = format;
        this.mQuality = quality;
    }

    @Override // com.samsung.android.media.heif.SemHeifConverter
    public void initialize() {
        AMessageJNI msg = new AMessageJNI();
        msg.setInt(MediaFormat.KEY_COLOR_FORMAT, this.mFormat);
        this.mCaptureNative.nativeStart(msg);
    }

    @Override // com.samsung.android.media.heif.SemHeifConverter
    public int convert(SemHeifConfig config, FileDescriptor outputFd) {
        return convert(Collections.singletonList(config), 0, outputFd);
    }

    @Override // com.samsung.android.media.heif.SemHeifConverter
    public int convert(SemHeifConfig config, ByteBuffer outputBuffer) {
        return convert(Collections.singletonList(config), 0, outputBuffer);
    }

    @Override // com.samsung.android.media.heif.SemHeifConverter
    public int convert(List<SemHeifConfig> configs, int coverIndex, FileDescriptor outputFd) {
        AMessageJNI msg = new AMessageJNI();
        msg.setFileDescriptor("output-fd", outputFd);
        return convert(configs, msg);
    }

    @Override // com.samsung.android.media.heif.SemHeifConverter
    public int convert(List<SemHeifConfig> configs, int coverIndex, ByteBuffer outputBuffer) {
        AMessageJNI msg = new AMessageJNI();
        msg.setByteBuffer(Message.KEY_OUT_BUFFER, outputBuffer);
        msg.setInt("output-buffer-capacity", outputBuffer.capacity());
        int limit = convert(configs, msg);
        if (limit > 0) {
            outputBuffer.limit(limit);
            outputBuffer.position(0);
        }
        return limit;
    }

    private int convert(List<SemHeifConfig> configs, AMessageJNI msg) {
        int id = 1;
        msg.setInt("cover-count", configs.size());
        for (SemHeifConfig config : configs) {
            CaptureSourceInternal masterInternal = CaptureSourceInternal.Parser.makeInternalSource(config.getMasterImage());
            masterInternal.setImageRole(0);
            if (config.getExifData() != null) {
                masterInternal.setExifData(config.getExifData());
            }
            if (config.getCameraInfo() != null) {
                masterInternal.setCameraInfo(config.getCameraInfo());
            }
            msg.setMessage(String.format(Locale.US, "cover%02d", Integer.valueOf(id)), masterInternal.getMsg());
            int id2 = id + 1;
            masterInternal.setId(id);
            if (config.getThumbnailImage() == null) {
                id = id2;
            } else {
                CaptureSourceInternal thumbInternal = CaptureSourceInternal.Parser.makeInternalSource(config.getThumbnailImage());
                thumbInternal.setImageRole(1);
                thumbInternal.setId(id2);
                masterInternal.setThumbnail(thumbInternal);
                id = id2 + 1;
            }
        }
        return this.mCaptureNative.nativeStore(msg);
    }

    @Override // com.samsung.android.media.heif.SemHeifConverter
    public void deinitialize() {
        this.mCaptureNative.nativeStop();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        deinitialize();
    }
}
