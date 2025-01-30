package com.samsung.android.motionphoto.core;

import java.io.FileDescriptor;

/* loaded from: classes5.dex */
public class MPSplitter {
    private native long native_reserve_xmp_on_heic(FileDescriptor fileDescriptor, int i);

    private native int native_split(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, String str);

    static {
        System.loadLibrary(Def.MP_LEGACY_NATIVE_LIB);
    }

    public int split(FileDescriptor videoFd, FileDescriptor imageFd, String params) {
        return native_split(videoFd, imageFd, params);
    }

    public long reserveXMPOnHeic(FileDescriptor inputFd, int reservedSize) {
        return native_reserve_xmp_on_heic(inputFd, reservedSize);
    }
}
