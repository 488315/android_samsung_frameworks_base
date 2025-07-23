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
        int convert = convert(list, aMessageJNI);
        if (convert > 0) {
            byteBuffer.limit(convert);
            byteBuffer.position(0);
        }
        return convert;
    }

    private int convert(List<SemHeifConfig> list, AMessageJNI aMessageJNI) {
        aMessageJNI.setInt("cover-count", list.size());
        int i = 1;
        for (SemHeifConfig semHeifConfig : list) {
            CaptureSourceInternal makeInternalSource = CaptureSourceInternal.Parser.makeInternalSource(semHeifConfig.getMasterImage());
            makeInternalSource.setImageRole(0);
            if (semHeifConfig.getExifData() != null) {
                makeInternalSource.setExifData(semHeifConfig.getExifData());
            }
            if (semHeifConfig.getCameraInfo() != null) {
                makeInternalSource.setCameraInfo(semHeifConfig.getCameraInfo());
            }
            aMessageJNI.setMessage(String.format(Locale.US, "cover%02d", Integer.valueOf(i)), makeInternalSource.getMsg());
            int i2 = i + 1;
            makeInternalSource.setId(i);
            if (semHeifConfig.getThumbnailImage() != null) {
                CaptureSourceInternal makeInternalSource2 = CaptureSourceInternal.Parser.makeInternalSource(semHeifConfig.getThumbnailImage());
                makeInternalSource2.setImageRole(1);
                i += 2;
                makeInternalSource2.setId(i2);
                makeInternalSource.setThumbnail(makeInternalSource2);
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
