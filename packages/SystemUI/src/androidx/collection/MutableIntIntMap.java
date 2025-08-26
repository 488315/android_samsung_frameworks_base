package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class MutableIntIntMap extends IntIntMap {
    public int growthLimit;

    public MutableIntIntMap() {
        this(0, 1, null);
    }

    public final void clear() {
        this._size = 0;
        long[] jArr = this.metadata;
        if (jArr != ScatterMapKt.EmptyGroup) {
            Arrays.fill(jArr, 0, jArr.length, -9187201950435737472L);
            long[] jArr2 = this.metadata;
            int i = this._capacity;
            int i2 = i >> 3;
            long j = 255 << ((i & 7) << 3);
            jArr2[i2] = (jArr2[i2] & (~j)) | j;
        }
        this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - this._size;
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
        this.keys = new int[iMax];
        this.values = new int[iMax];
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0073, code lost:
    
        r21 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x007e, code lost:
    
        if (((r2 & ((~r2) << 6)) & (-9187201950435737472L)) == 0) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0080, code lost:
    
        r2 = findFirstAvailableSlot(r6);
        r9 = 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0088, code lost:
    
        if (r36.growthLimit != 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x009b, code lost:
    
        if (((r36.metadata[r2 >> 3] >> ((r2 & 7) << 3)) & 255) != 254) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x009d, code lost:
    
        r27 = 255;
        r22 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a3, code lost:
    
        r2 = r36._capacity;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00a5, code lost:
    
        if (r2 <= 8) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a7, code lost:
    
        r4 = r36._size;
        r20 = kotlin.ULong.$r8$clinit;
        r22 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00bb, code lost:
    
        if (java.lang.Long.compareUnsigned(r4 * 32, r2 * 25) > 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00bd, code lost:
    
        r2 = r36.metadata;
        r4 = r36._capacity;
        r5 = r36.keys;
        r7 = r36.values;
        r8 = (r4 + 7) >> 3;
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00cc, code lost:
    
        if (r3 >= r8) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00ce, code lost:
    
        r27 = r9;
        r9 = r2[r3] & r13;
        r2[r3] = (-72340172838076674L) & ((~r9) + (r9 >>> r17));
        r3 = r3 + 1;
        r9 = r27;
        r13 = -9187201950435737472L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00ea, code lost:
    
        r27 = r9;
        r3 = r2.length;
        r8 = r3 - 1;
        r3 = r3 - 2;
        r13 = 72057594037927935L;
        r2[r3] = (r2[r3] & 72057594037927935L) | (-72057594037927936L);
        r2[r8] = r2[0];
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0104, code lost:
    
        if (r3 == r4) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0106, code lost:
    
        r8 = r3 >> 3;
        r20 = (r3 & 7) << 3;
        r9 = (r2[r8] >> r20) & r27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0114, code lost:
    
        if (r9 != 128) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0116, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x011b, code lost:
    
        if (r9 == 254) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x011e, code lost:
    
        r9 = java.lang.Integer.hashCode(r5[r3]) * r21;
        r10 = (r9 ^ (r9 << 16)) >>> 7;
        r25 = findFirstAvailableSlot(r10);
        r10 = r10 & r4;
        r29 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x013f, code lost:
    
        if ((((r25 - r10) & r4) / 8) != (((r3 - r10) & r4) / 8)) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0141, code lost:
    
        r26 = r4;
        r2[r8] = ((~(r27 << r20)) & r2[r8]) | ((r9 & 127) << r20);
        r2[r2.length - 1] = (r2[0] & r29) | Long.MIN_VALUE;
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0160, code lost:
    
        r4 = r26;
        r13 = r29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0165, code lost:
    
        r33 = r3;
        r26 = r4;
        r3 = r25 >> 3;
        r13 = r2[r3];
        r4 = (r25 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0177, code lost:
    
        if (((r13 >> r4) & r27) != 128) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0179, code lost:
    
        r2[r3] = ((~(r27 << r4)) & r13) | ((r9 & 127) << r4);
        r2[r8] = (r2[r8] & (~(r27 << r20))) | (128 << r20);
        r5[r25] = r5[r33];
        r5[r33] = 0;
        r7[r25] = r7[r33];
        r7[r33] = 0;
        r3 = r33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x01a3, code lost:
    
        r2[r3] = ((r9 & 127) << r4) | ((~(r27 << r4)) & r13);
        r3 = r5[r25];
        r5[r25] = r5[r33];
        r5[r33] = r3;
        r3 = r7[r25];
        r7[r25] = r7[r33];
        r7[r33] = r3;
        r3 = r33 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x01c5, code lost:
    
        r2[r2.length - 1] = (r2[0] & r29) | Long.MIN_VALUE;
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x01d3, code lost:
    
        r36.growthLimit = androidx.collection.ScatterMapKt.loadedCapacity(r36._capacity) - r36._size;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01e0, code lost:
    
        r27 = 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01e3, code lost:
    
        r22 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01e6, code lost:
    
        r2 = androidx.collection.ScatterMapKt.nextCapacity(r36._capacity);
        r3 = r36.metadata;
        r4 = r36.keys;
        r5 = r36.values;
        r7 = r36._capacity;
        initializeStorage(r2);
        r2 = r36.metadata;
        r8 = r36.keys;
        r9 = r36.values;
        r10 = r36._capacity;
        r13 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0200, code lost:
    
        if (r13 >= r7) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0210, code lost:
    
        if (((r3[r13 >> 3] >> ((r13 & 7) << 3)) & 255) >= r22) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0212, code lost:
    
        r14 = r4[r13];
        r18 = java.lang.Integer.hashCode(r14) * r21;
        r18 = r18 ^ (r18 << 16);
        r15 = findFirstAvailableSlot(r18 >>> 7);
        r1 = r18 & 127;
        r18 = r2;
        r20 = r15 >> 3;
        r24 = (r15 & 7) << 3;
        r1 = (r18[r20] & (~(255 << r24))) | (r1 << r24);
        r18[r20] = r1;
        r18[(((r15 - 7) & r10) + (r10 & 7)) >> 3] = r1;
        r8[r15] = r14;
        r9[r15] = r5[r13];
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0251, code lost:
    
        r18 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0253, code lost:
    
        r13 = r13 + 1;
        r2 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x025b, code lost:
    
        r2 = findFirstAvailableSlot(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x025f, code lost:
    
        r36._size++;
        r1 = r36.growthLimit;
        r3 = r36.metadata;
        r4 = r2 >> 3;
        r5 = r3[r4];
        r7 = (r2 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0277, code lost:
    
        if (((r5 >> r7) & r27) != r22) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x027a, code lost:
    
        r16 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x027c, code lost:
    
        r36.growthLimit = r1 - r16;
        r1 = r36._capacity;
        r5 = (r5 & (~(r27 << r7))) | (r11 << r7);
        r3[r4] = r5;
        r3[(((r2 - 7) & r1) + (r1 & 7)) >> 3] = r5;
        r1 = ~r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void set(int i, int i2) {
        int i3;
        int i4;
        int i5 = i;
        int i6 = 1;
        char c = 7;
        int i7 = -862048943;
        int iHashCode = Integer.hashCode(i5) * (-862048943);
        int i8 = iHashCode ^ (iHashCode << 16);
        int i9 = i8 >>> 7;
        int i10 = i8 & 127;
        int i11 = this._capacity;
        int i12 = i9 & i11;
        int i13 = 0;
        loop0: while (true) {
            long[] jArr = this.metadata;
            int i14 = i12 >> 3;
            int i15 = (i12 & 7) << 3;
            int i16 = i6;
            char c2 = c;
            long j = (((-i15) >> 63) & (jArr[i14 + i6] << (64 - i15))) | (jArr[i14] >>> i15);
            long j2 = i10;
            int i17 = i13;
            long j3 = j ^ (j2 * 72340172838076673L);
            long j4 = -9187201950435737472L;
            long j5 = (~j3) & (j3 - 72340172838076673L) & (-9187201950435737472L);
            while (true) {
                if (j5 == 0) {
                    break;
                }
                int iNumberOfTrailingZeros = (i12 + (Long.numberOfTrailingZeros(j5) >> 3)) & i11;
                int i18 = i7;
                if (this.keys[iNumberOfTrailingZeros] == i5) {
                    i4 = iNumberOfTrailingZeros;
                    break loop0;
                } else {
                    j5 &= j5 - 1;
                    i7 = i18;
                }
            }
            i13 = i17 + 8;
            i12 = (i12 + i13) & i11;
            i5 = i;
            i6 = i16;
            c = c2;
            i7 = i3;
        }
        if (i4 < 0) {
            i4 = ~i4;
        }
        this.keys[i4] = i;
        this.values[i4] = i2;
    }

    public MutableIntIntMap(int i) {
        super(null);
        if (i >= 0) {
            initializeStorage(ScatterMapKt.unloadedCapacity(i));
        } else {
            RuntimeHelpersKt.throwIllegalArgumentException("Capacity must be a positive value.");
            throw null;
        }
    }

    public /* synthetic */ MutableIntIntMap(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }
}
