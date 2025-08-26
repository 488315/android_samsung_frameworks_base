package com.android.systemui.media.mediaoutput.ext;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import kotlin.collections.ArraysKt___ArraysKt;

/* loaded from: classes2.dex */
public abstract class BitmapExtKt {
    public static final String getHash(Bitmap bitmap) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBufferAllocate);
        return ArraysKt___ArraysKt.joinToString$default(MessageDigest.getInstance("MD5").digest(byteBufferAllocate.array()), "", new BitmapExtKt$$ExternalSyntheticLambda0(), 30);
    }
}
