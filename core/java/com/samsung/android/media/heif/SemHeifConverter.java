package com.samsung.android.media.heif;

import java.io.FileDescriptor;
import java.nio.ByteBuffer;
import java.util.List;

public interface SemHeifConverter extends AutoCloseable {
    int convert(SemHeifConfig semHeifConfig, FileDescriptor fileDescriptor);

    int convert(SemHeifConfig semHeifConfig, ByteBuffer byteBuffer);

    int convert(List<SemHeifConfig> list, int i, FileDescriptor fileDescriptor);

    int convert(List<SemHeifConfig> list, int i, ByteBuffer byteBuffer);

    void deinitialize();

    void initialize();

    public static class Factory {
        public static SemHeifConverter create(int format, int quality) {
            return new HeifConverterNativeImpl(format, quality);
        }
    }
}
