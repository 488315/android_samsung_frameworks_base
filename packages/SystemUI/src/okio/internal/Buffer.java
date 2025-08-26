package okio.internal;

import kotlin.text.Charsets;
import okio.Options;
import okio.Segment;

/* renamed from: okio.internal.-Buffer, reason: invalid class name */
/* loaded from: classes4.dex */
public abstract class Buffer {
    static {
        "0123456789abcdef".getBytes(Charsets.UTF_8);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0057, code lost:
    
        if (r18 == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0059, code lost:
    
        return -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x007c, code lost:
    
        return r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final int selectPrefix(okio.Buffer buffer, Options options, boolean z) {
        int i;
        byte[] bArr;
        int i2;
        int i3;
        Segment segment;
        byte[] bArr2;
        int i4;
        Segment segment2 = buffer.head;
        if (segment2 != null) {
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
                int i11 = i8 + 2;
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
                                segment3.getClass();
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
                            if (i >= 0) {
                                return i;
                            }
                            byte[] bArr4 = bArr;
                            i8 = -i;
                            i5 = i3;
                            i6 = i2;
                            bArr3 = bArr4;
                        } else {
                            i11++;
                        }
                    }
                    break loop0;
                }
                int i16 = (i10 * (-1)) + i11;
                while (true) {
                    int i17 = i5 + 1;
                    int i18 = i11 + 1;
                    if ((bArr3[i5] & 255) != iArr[i11]) {
                        break loop0;
                    }
                    boolean z2 = i18 == i16;
                    if (i17 == i6) {
                        segment3.getClass();
                        Segment segment4 = segment3.next;
                        segment4.getClass();
                        i4 = segment4.pos;
                        int i19 = segment4.limit;
                        bArr2 = segment4.data;
                        if (segment4 != segment2) {
                            segment = segment4;
                            i6 = i19;
                        } else {
                            if (!z2) {
                                break loop0;
                            }
                            i6 = i19;
                            segment = null;
                        }
                    } else {
                        segment = segment3;
                        bArr2 = bArr3;
                        i4 = i17;
                    }
                    if (z2) {
                        i = iArr[i18];
                        int i20 = i4;
                        i2 = i6;
                        i3 = i20;
                        byte[] bArr5 = bArr2;
                        segment3 = segment;
                        bArr = bArr5;
                        break;
                    }
                    i5 = i4;
                    bArr3 = bArr2;
                    segment3 = segment;
                    i11 = i18;
                }
            }
        } else {
            return z ? -2 : -1;
        }
    }
}
