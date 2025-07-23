package com.google.protobuf.nano;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class WireFormatNano {
    private WireFormatNano() {
    }

    public static final int getRepeatedFieldArrayLength(CodedInputByteBufferNano codedInputByteBufferNano, int i) {
        int i2 = codedInputByteBufferNano.bufferPos;
        int i3 = codedInputByteBufferNano.bufferStart;
        int i4 = i2 - i3;
        codedInputByteBufferNano.skipField(i);
        int i5 = 1;
        while (codedInputByteBufferNano.readTag() == i) {
            codedInputByteBufferNano.skipField(i);
            i5++;
        }
        if (i4 > codedInputByteBufferNano.bufferPos - i3) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i4, "Position ", " is beyond current ");
            m.append(codedInputByteBufferNano.bufferPos - i3);
            throw new IllegalArgumentException(m.toString());
        }
        if (i4 < 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i4, "Bad position "));
        }
        codedInputByteBufferNano.bufferPos = i3 + i4;
        return i5;
    }
}
