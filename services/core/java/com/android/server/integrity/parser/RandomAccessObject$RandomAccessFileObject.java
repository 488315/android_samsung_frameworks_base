package com.android.server.integrity.parser;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RandomAccessObject$RandomAccessFileObject {
    public final int mLength;
    public final RandomAccessFile mRandomAccessFile;

    public RandomAccessObject$RandomAccessFileObject(File file) {
        long length = file.length();
        if (length > 2147483647L) {
            throw new IOException(
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            length, "Unsupported file size (too big) "));
        }
        this.mRandomAccessFile = new RandomAccessFile(file, "r");
        this.mLength = (int) length;
    }
}
