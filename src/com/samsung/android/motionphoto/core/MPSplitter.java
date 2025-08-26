package com.samsung.android.motionphoto.core;

import java.io.FileDescriptor;

/* loaded from: classes6.dex */
public class MPSplitter {
    private native long native_reserve_xmp_on_heic(FileDescriptor fileDescriptor, int i);

    private native String native_split(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, String str);

    static {
        String property = System.getProperty(Def.JUNIT_TEST_EXECUTION_MODE);
        if (property == null || !Boolean.parseBoolean(property)) {
            System.loadLibrary(Def.MP_NATIVE_LIB);
        }
    }

    public String split(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, String str) {
        return native_split(fileDescriptor, fileDescriptor2, str);
    }

    public long reserveXMPOnHeic(FileDescriptor fileDescriptor, int i) {
        return native_reserve_xmp_on_heic(fileDescriptor, i);
    }
}
