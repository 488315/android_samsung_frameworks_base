package com.google.zxing.datamatrix.encoder;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* loaded from: classes4.dex */
public final class Base256Encoder implements Encoder {
    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public final void encode(EncoderContext encoderContext) {
        StringBuilder sb = new StringBuilder();
        sb.append((char) 0);
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            sb.append(encoderContext.getCurrentChar());
            int i = encoderContext.pos + 1;
            encoderContext.pos = i;
            if (HighLevelEncoder.lookAheadTest(i, 5, encoderContext.msg) != 5) {
                encoderContext.newEncoding = 0;
                break;
            }
        }
        int length = sb.length() - 1;
        StringBuilder sb2 = encoderContext.codewords;
        int length2 = sb2.length() + length + 1;
        encoderContext.updateSymbolInfo(length2);
        boolean z = encoderContext.symbolInfo.dataCapacity - length2 > 0;
        if (encoderContext.hasMoreCharacters() || z) {
            if (length <= 249) {
                sb.setCharAt(0, (char) length);
            } else {
                if (length > 1555) {
                    throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(length, "Message length not in valid ranges: "));
                }
                sb.setCharAt(0, (char) ((length / IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend) + 249));
                sb.insert(1, (char) (length % IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend));
            }
        }
        int length3 = sb.length();
        for (int i2 = 0; i2 < length3; i2++) {
            int length4 = (((sb2.length() + 1) * 149) % 255) + 1 + sb.charAt(i2);
            if (length4 > 255) {
                length4 -= 256;
            }
            encoderContext.writeCodeword((char) length4);
        }
    }
}
