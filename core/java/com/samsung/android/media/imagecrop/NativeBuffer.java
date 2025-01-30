package com.samsung.android.media.imagecrop;

import java.nio.ByteBuffer;

/* loaded from: classes5.dex */
public class NativeBuffer {
    static native ByteBuffer allocNativeBuffer(long j);

    static native void freeNativeBuffer(ByteBuffer byteBuffer);
}
