package okio.internal;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import okio.Buffer;
import okio.Options;
import okio.Segment;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class BufferKt {
    static {
        "0123456789abcdef".getBytes(Charsets.UTF_8);
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x004f, code lost:
    
        if (r20 == false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0051, code lost:
    
        return -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0052, code lost:
    
        return r10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final int selectPrefix(Buffer buffer, Options options, boolean z) {
        int i;
        byte[] bArr;
        int i2;
        int i3;
        byte[] bArr2;
        int i4;
        Segment segment;
        Segment segment2 = buffer.head;
        if (segment2 == null) {
            return z ? -2 : -1;
        }
        int i5 = segment2.pos;
        int i6 = segment2.limit;
        int[] iArr = options.trie;
        byte[] bArr3 = segment2.data;
        Segment segment3 = segment2;
        int i7 = -1;
        int i8 = 0;
        loop0: while (true) {
            int i9 = i8 + 1;
            int i10 = iArr[i8];
            int i11 = i9 + 1;
            int i12 = iArr[i9];
            if (i12 != -1) {
                i7 = i12;
            }
            if (segment3 == null) {
                break;
            }
            if (i10 >= 0) {
                int i13 = i5 + 1;
                int i14 = bArr3[i5] & 255;
                int i15 = i11 + i10;
                while (i11 != i15) {
                    if (i14 == iArr[i11]) {
                        i = iArr[i11 + i10];
                        if (i13 == i6) {
                            segment3 = segment3.next;
                            if (segment3 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                            i3 = segment3.pos;
                            i2 = segment3.limit;
                            bArr = segment3.data;
                            if (segment3 == segment2) {
                                segment3 = null;
                            }
                        } else {
                            bArr = bArr3;
                            i2 = i6;
                            i3 = i13;
                        }
                    } else {
                        i11++;
                    }
                }
                return i7;
            }
            int i16 = (i10 * (-1)) + i11;
            while (true) {
                int i17 = i5 + 1;
                int i18 = i11 + 1;
                if ((bArr3[i5] & 255) != iArr[i11]) {
                    return i7;
                }
                boolean z2 = i18 == i16;
                if (i17 != i6) {
                    Segment segment4 = segment3;
                    bArr2 = bArr3;
                    i4 = i17;
                    segment = segment4;
                } else {
                    if (segment3 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    Segment segment5 = segment3.next;
                    if (segment5 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    i4 = segment5.pos;
                    int i19 = segment5.limit;
                    bArr2 = segment5.data;
                    if (segment5 != segment2) {
                        segment = segment5;
                        i6 = i19;
                    } else {
                        if (!z2) {
                            break loop0;
                        }
                        i6 = i19;
                        segment = null;
                    }
                }
                if (z2) {
                    i = iArr[i18];
                    int i20 = i4;
                    i2 = i6;
                    i3 = i20;
                    byte[] bArr4 = bArr2;
                    segment3 = segment;
                    bArr = bArr4;
                    break;
                }
                i5 = i4;
                bArr3 = bArr2;
                i11 = i18;
                segment3 = segment;
            }
            if (i >= 0) {
                return i;
            }
            byte[] bArr5 = bArr;
            i8 = -i;
            i5 = i3;
            i6 = i2;
            bArr3 = bArr5;
        }
    }
}
