package com.android.systemui.media.mediaoutput.ext;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class BitmapExtKt {
    public static final String getHash(Bitmap bitmap) {
        ByteBuffer allocate = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(allocate);
        return ArraysKt___ArraysKt.joinToString$default(MessageDigest.getInstance("MD5").digest(allocate.array()), "", new BitmapExtKt$$ExternalSyntheticLambda0(), 30);
    }
}
