package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class MutableIntSet extends IntSet {
    public int growthLimit;

    public MutableIntSet() {
        this(0, 1, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0074, code lost:
    
        r22 = r6;
        r3 = '\b';
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x007f, code lost:
    
        if (((r2 & ((~r2) << 6)) & (-9187201950435737472L)) == 0) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0081, code lost:
    
        r2 = findFirstAvailableSlot(r7);
        r10 = 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0089, code lost:
    
        if (r37.growthLimit != 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x009c, code lost:
    
        if (((r37.metadata[r2 >> 3] >> ((r2 & 7) << 3)) & 255) != 254) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x009e, code lost:
    
        r27 = 255;
        r23 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a4, code lost:
    
        r2 = r37._capacity;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00a6, code lost:
    
        if (r2 <= 8) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a8, code lost:
    
        r5 = r37._size;
        r21 = kotlin.ULong.$r8$clinit;
        r23 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00bc, code lost:
    
        if (java.lang.Long.compareUnsigned(r5 * 32, r2 * 25) > 0) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00be, code lost:
    
        r2 = r37.metadata;
        r5 = r37._capacity;
        r6 = r37.elements;
        r8 = (r5 + 7) >> 3;
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00ca, code lost:
    
        if (r9 >= r8) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00cc, code lost:
    
        r27 = r10;
        r10 = r2[r9] & r14;
        r2[r9] = (-72340172838076674L) & ((~r10) + (r10 >>> r18));
        r9 = r9 + 1;
        r10 = r27;
        r14 = -9187201950435737472L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00e8, code lost:
    
        r27 = r10;
        r8 = r2.length;
        r9 = r8 - 1;
        r8 = r8 - 2;
        r14 = 72057594037927935L;
        r2[r8] = (r2[r8] & 72057594037927935L) | (-72057594037927936L);
        r2[r9] = r2[0];
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0103, code lost:
    
        if (r8 == r5) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0105, code lost:
    
        r9 = r8 >> 3;
        r21 = (r8 & 7) << 3;
        r10 = (r2[r9] >> r21) & r27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0113, code lost:
    
        if (r10 != 128) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0115, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x011a, code lost:
    
        if (r10 == 254) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x011d, code lost:
    
        r10 = java.lang.Integer.hashCode(r6[r8]) * r22;
        r11 = (r10 ^ (r10 << 16)) >>> 7;
        r25 = findFirstAvailableSlot(r11);
        r11 = r11 & r5;
        r29 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x013e, code lost:
    
        if ((((r25 - r11) & r5) / 8) != (((r8 - r11) & r5) / 8)) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0140, code lost:
    
        r32 = r14;
        r2[r9] = ((r10 & 127) << r21) | (r2[r9] & (~(r27 << r21)));
        r2[r2.length - 1] = (r2[0] & r32) | Long.MIN_VALUE;
        r8 = r8 + 1;
        r3 = r29;
        r14 = r32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0163, code lost:
    
        r32 = r14;
        r3 = r25 >> 3;
        r14 = r2[r3];
        r11 = (r25 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0173, code lost:
    
        if (((r14 >> r11) & r27) != 128) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0175, code lost:
    
        r26 = r5;
        r34 = r6;
        r2[r3] = ((~(r27 << r11)) & r14) | ((r10 & 127) << r11);
        r2[r9] = (r2[r9] & (~(r27 << r21))) | (128 << r21);
        r34[r25] = r34[r8];
        r34[r8] = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0199, code lost:
    
        r26 = r5;
        r34 = r6;
        r2[r3] = ((r10 & 127) << r11) | ((~(r27 << r11)) & r14);
        r3 = r34[r25];
        r34[r25] = r34[r8];
        r34[r8] = r3;
        r8 = r8 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x01b2, code lost:
    
        r2[r2.length - 1] = (r2[0] & r32) | Long.MIN_VALUE;
        r8 = r8 + 1;
        r5 = r26;
        r3 = r29;
        r14 = r32;
        r6 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x01c9, code lost:
    
        r37.growthLimit = androidx.collection.ScatterMapKt.loadedCapacity(r37._capacity) - r37._size;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x01d6, code lost:
    
        r27 = 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01d9, code lost:
    
        r23 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01dc, code lost:
    
        r2 = androidx.collection.ScatterMapKt.nextCapacity(r37._capacity);
        r3 = r37.metadata;
        r5 = r37.elements;
        r6 = r37._capacity;
        initializeStorage(r2);
        r2 = r37.metadata;
        r8 = r37.elements;
        r9 = r37._capacity;
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01f3, code lost:
    
        if (r10 >= r6) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0202, code lost:
    
        if (((r3[r10 >> 3] >> ((r10 & 7) << 3)) & 255) >= r23) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0204, code lost:
    
        r11 = r5[r10];
        r14 = java.lang.Integer.hashCode(r11) * r22;
        r14 = r14 ^ (r14 << 16);
        r15 = findFirstAvailableSlot(r14 >>> 7);
        r19 = r2;
        r1 = r14 & 127;
        r14 = r15 >> 3;
        r20 = (r15 & 7) << 3;
        r1 = (r19[r14] & (~(255 << r20))) | (r1 << r20);
        r19[r14] = r1;
        r19[(((r15 - 7) & r9) + (r9 & 7)) >> 3] = r1;
        r8[r15] = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x023d, code lost:
    
        r19 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x023f, code lost:
    
        r10 = r10 + 1;
        r2 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0246, code lost:
    
        r19 = findFirstAvailableSlot(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x024d, code lost:
    
        r19 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x024f, code lost:
    
        r37._size++;
        r1 = r37.growthLimit;
        r2 = r37.metadata;
        r3 = r19 >> 3;
        r5 = r2[r3];
        r7 = (r19 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0267, code lost:
    
        if (((r5 >> r7) & r27) != r23) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0269, code lost:
    
        r8 = r17 ? 1 : 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x026c, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x026e, code lost:
    
        r37.growthLimit = r1 - r8;
        r1 = r37._capacity;
        r5 = (r5 & (~(r27 << r7))) | (r12 << r7);
        r2[r3] = r5;
        r2[(((r19 - 7) & r1) + (r1 & 7)) >> 3] = r5;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean add(int i) {
        boolean z;
        int i2;
        int iFindFirstAvailableSlot;
        int i3 = i;
        int i4 = 1;
        char c = 7;
        int i5 = this._size;
        int i6 = -862048943;
        int iHashCode = Integer.hashCode(i3) * (-862048943);
        int i7 = iHashCode ^ (iHashCode << 16);
        int i8 = i7 >>> 7;
        int i9 = i7 & 127;
        int i10 = this._capacity;
        int i11 = i8 & i10;
        int i12 = 0;
        loop0: while (true) {
            long[] jArr = this.metadata;
            int i13 = i11 >> 3;
            int i14 = (i11 & 7) << 3;
            z = i4;
            char c2 = c;
            long j = (((-i14) >> 63) & (jArr[i13 + i4] << (64 - i14))) | (jArr[i13] >>> i14);
            long j2 = i9;
            int i15 = i12;
            long j3 = j ^ (j2 * 72340172838076673L);
            long j4 = -9187201950435737472L;
            long j5 = (~j3) & (j3 - 72340172838076673L) & (-9187201950435737472L);
            while (true) {
                if (j5 == 0) {
                    break;
                }
                iFindFirstAvailableSlot = (i11 + (Long.numberOfTrailingZeros(j5) >> 3)) & i10;
                int i16 = i6;
                if (this.elements[iFindFirstAvailableSlot] == i3) {
                    break loop0;
                }
                j5 &= j5 - 1;
                i6 = i16;
            }
            i12 = i15 + 8;
            i11 = (i11 + i12) & i10;
            i3 = i;
            i4 = z ? 1 : 0;
            c = c2;
            i6 = i2;
        }
        this.elements[iFindFirstAvailableSlot] = i;
        if (this._size != i5) {
            return z;
        }
        return false;
    }

    public final int findFirstAvailableSlot(int i) {
        int i2 = this._capacity;
        int i3 = i & i2;
        int i4 = 0;
        while (true) {
            long[] jArr = this.metadata;
            int i5 = i3 >> 3;
            int i6 = (i3 & 7) << 3;
            long j = ((jArr[i5 + 1] << (64 - i6)) & ((-i6) >> 63)) | (jArr[i5] >>> i6);
            long j2 = j & ((~j) << 7) & (-9187201950435737472L);
            if (j2 != 0) {
                return (i3 + (Long.numberOfTrailingZeros(j2) >> 3)) & i2;
            }
            i4 += 8;
            i3 = (i3 + i4) & i2;
        }
    }

    public final void initializeStorage(int i) {
        long[] jArr;
        int iMax = i > 0 ? Math.max(7, ScatterMapKt.normalizeCapacity(i)) : 0;
        this._capacity = iMax;
        if (iMax == 0) {
            jArr = ScatterMapKt.EmptyGroup;
        } else {
            int i2 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i2];
            Arrays.fill(jArr2, 0, i2, -9187201950435737472L);
            jArr = jArr2;
        }
        this.metadata = jArr;
        int i3 = iMax >> 3;
        long j = 255 << ((iMax & 7) << 3);
        jArr[i3] = (jArr[i3] & (~j)) | j;
        this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - this._size;
        this.elements = new int[iMax];
    }

    public MutableIntSet(int i) {
        super(null);
        if (i >= 0) {
            initializeStorage(ScatterMapKt.unloadedCapacity(i));
        } else {
            RuntimeHelpersKt.throwIllegalArgumentException("Capacity must be a positive value.");
            throw null;
        }
    }

    public /* synthetic */ MutableIntSet(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }
}
