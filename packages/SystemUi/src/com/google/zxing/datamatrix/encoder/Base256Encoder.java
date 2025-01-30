package com.google.zxing.datamatrix.encoder;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
            int lookAheadTest = HighLevelEncoder.lookAheadTest(i, 5, encoderContext.msg);
            if (lookAheadTest != 5) {
                encoderContext.newEncoding = lookAheadTest;
                break;
            }
        }
        int length = sb.length() - 1;
        int codewordCount = encoderContext.getCodewordCount() + length + 1;
        encoderContext.updateSymbolInfo(codewordCount);
        boolean z = encoderContext.symbolInfo.dataCapacity - codewordCount > 0;
        if (encoderContext.hasMoreCharacters() || z) {
            if (length <= 249) {
                sb.setCharAt(0, (char) length);
            } else {
                if (length <= 249 || length > 1555) {
                    throw new IllegalStateException(AbstractC0000x2c234b15.m0m("Message length not in valid ranges: ", length));
                }
                sb.setCharAt(0, (char) ((length / IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend) + IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcut));
                sb.insert(1, (char) (length % IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend));
            }
        }
        int length2 = sb.length();
        for (int i2 = 0; i2 < length2; i2++) {
            int codewordCount2 = (((encoderContext.getCodewordCount() + 1) * 149) % 255) + 1 + sb.charAt(i2);
            if (codewordCount2 > 255) {
                codewordCount2 -= 256;
            }
            encoderContext.writeCodeword((char) codewordCount2);
        }
    }
}
