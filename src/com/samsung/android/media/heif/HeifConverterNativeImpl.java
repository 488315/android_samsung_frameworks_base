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

/* loaded from: classes6.dex */
final class HeifConverterNativeImpl implements SemHeifConverter {
    private final HeifCaptureJNI mCaptureNative = new HeifCaptureJNI();
    private final int mFormat;
    private final int mQuality;

    HeifConverterNativeImpl(int i, int i2) {
        this.mFormat = i;
        this.mQuality = i2;
    }

    @Override // com.samsung.android.media.heif.SemHeifConverter
    public void initialize() {
        AMessageJNI aMessageJNI = new AMessageJNI();
        aMessageJNI.setInt(MediaFormat.KEY_COLOR_FORMAT, this.mFormat);
        this.mCaptureNative.nativeStart(aMessageJNI);
    }

    @Override // com.samsung.android.media.heif.SemHeifConverter
    public int convert(SemHeifConfig semHeifConfig, FileDescriptor fileDescriptor) {
        return convert(Collections.singletonList(semHeifConfig), 0, fileDescriptor);
    }

    @Override // com.samsung.android.media.heif.SemHeifConverter
    public int convert(SemHeifConfig semHeifConfig, ByteBuffer byteBuffer) {
        return convert(Collections.singletonList(semHeifConfig), 0, byteBuffer);
    }

    @Override // com.samsung.android.media.heif.SemHeifConverter
    public int convert(List<SemHeifConfig> list, int i, FileDescriptor fileDescriptor) {
        AMessageJNI aMessageJNI = new AMessageJNI();
        aMessageJNI.setFileDescriptor("output-fd", fileDescriptor);
        return convert(list, aMessageJNI);
    }

    @Override // com.samsung.android.media.heif.SemHeifConverter
    public int convert(List<SemHeifConfig> list, int i, ByteBuffer byteBuffer) {
        AMessageJNI aMessageJNI = new AMessageJNI();
        aMessageJNI.setByteBuffer(Message.KEY_OUT_BUFFER, byteBuffer);
        aMessageJNI.setInt("output-buffer-capacity", byteBuffer.capacity());
        int iConvert = convert(list, aMessageJNI);
        if (iConvert > 0) {
            byteBuffer.limit(iConvert);
            byteBuffer.position(0);
        }
        return iConvert;
    }

    private int convert(List<SemHeifConfig> list, AMessageJNI aMessageJNI) {
        aMessageJNI.setInt("cover-count", list.size());
        int i = 1;
        for (SemHeifConfig semHeifConfig : list) {
            CaptureSourceInternal captureSourceInternalMakeInternalSource = CaptureSourceInternal.Parser.makeInternalSource(semHeifConfig.getMasterImage());
            captureSourceInternalMakeInternalSource.setImageRole(0);
            if (semHeifConfig.getExifData() != null) {
                captureSourceInternalMakeInternalSource.setExifData(semHeifConfig.getExifData());
            }
            if (semHeifConfig.getCameraInfo() != null) {
                captureSourceInternalMakeInternalSource.setCameraInfo(semHeifConfig.getCameraInfo());
            }
            aMessageJNI.setMessage(String.format(Locale.US, "cover%02d", Integer.valueOf(i)), captureSourceInternalMakeInternalSource.getMsg());
            int i2 = i + 1;
            captureSourceInternalMakeInternalSource.setId(i);
            if (semHeifConfig.getThumbnailImage() != null) {
                CaptureSourceInternal captureSourceInternalMakeInternalSource2 = CaptureSourceInternal.Parser.makeInternalSource(semHeifConfig.getThumbnailImage());
                captureSourceInternalMakeInternalSource2.setImageRole(1);
                i += 2;
                captureSourceInternalMakeInternalSource2.setId(i2);
                captureSourceInternalMakeInternalSource.setThumbnail(captureSourceInternalMakeInternalSource2);
            } else {
                i = i2;
            }
        }
        return this.mCaptureNative.nativeStore(aMessageJNI);
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
