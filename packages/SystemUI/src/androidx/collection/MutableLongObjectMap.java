package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class MutableLongObjectMap extends LongObjectMap {
    public int growthLimit;

    public MutableLongObjectMap() {
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
        Arrays.fill(this.values, 0, this._capacity, (Object) null);
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
        this.keys = new long[iMax];
        this.values = new Object[iMax];
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0062, code lost:
    
        if (((r4 & ((~r4) << 6)) & (-9187201950435737472L)) == 0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0064, code lost:
    
        r10 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object remove(long j) {
        int iNumberOfTrailingZeros;
        int iHashCode = Long.hashCode(j) * (-862048943);
        int i = iHashCode ^ (iHashCode << 16);
        int i2 = i & 127;
        int i3 = this._capacity;
        int i4 = (i >>> 7) & i3;
        int i5 = 0;
        loop0: while (true) {
            long[] jArr = this.metadata;
            int i6 = i4 >> 3;
            int i7 = (i4 & 7) << 3;
            long j2 = ((jArr[i6 + 1] << (64 - i7)) & ((-i7) >> 63)) | (jArr[i6] >>> i7);
            long j3 = (i2 * 72340172838076673L) ^ j2;
            long j4 = (~j3) & (j3 - 72340172838076673L) & (-9187201950435737472L);
            while (true) {
                if (j4 == 0) {
                    break;
                }
                iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j4) >> 3) + i4) & i3;
                if (this.keys[iNumberOfTrailingZeros] == j) {
                    break loop0;
                }
                j4 &= j4 - 1;
            }
            i5 += 8;
            i4 = (i4 + i5) & i3;
        }
        if (iNumberOfTrailingZeros < 0) {
            return null;
        }
        this._size--;
        long[] jArr2 = this.metadata;
        int i8 = this._capacity;
        int i9 = iNumberOfTrailingZeros >> 3;
        int i10 = (iNumberOfTrailingZeros & 7) << 3;
        long j5 = (jArr2[i9] & (~(255 << i10))) | (254 << i10);
        jArr2[i9] = j5;
        jArr2[(((iNumberOfTrailingZeros - 7) & i8) + (i8 & 7)) >> 3] = j5;
        Object[] objArr = this.values;
        Object obj = objArr[iNumberOfTrailingZeros];
        objArr[iNumberOfTrailingZeros] = null;
        return obj;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x006f, code lost:
    
        r20 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x007a, code lost:
    
        if (((r1 & ((~r1) << 6)) & (-9187201950435737472L)) == 0) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x007c, code lost:
    
        r1 = findFirstAvailableSlot(r5);
        r8 = 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0084, code lost:
    
        if (r38.growthLimit != 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0097, code lost:
    
        if (((r38.metadata[r1 >> 3] >> ((r1 & 7) << 3)) & 255) != 254) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0099, code lost:
    
        r28 = 255;
        r34 = 0;
        r27 = r15;
        r23 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a3, code lost:
    
        r1 = r38._capacity;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00a5, code lost:
    
        if (r1 <= 8) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a7, code lost:
    
        r3 = r38._size;
        r19 = kotlin.ULong.$r8$clinit;
        r23 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00bb, code lost:
    
        if (java.lang.Long.compareUnsigned(r3 * 32, r1 * 25) > 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00bd, code lost:
    
        r1 = r38.metadata;
        r3 = r38._capacity;
        r4 = r38.keys;
        r6 = r38.values;
        r7 = (r3 + 7) >> 3;
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00cc, code lost:
    
        if (r2 >= r7) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00ce, code lost:
    
        r28 = r8;
        r8 = r1[r2] & r12;
        r1[r2] = (-72340172838076674L) & ((~r8) + (r8 >>> r16));
        r2 = r2 + r15;
        r8 = r28;
        r12 = -9187201950435737472L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00e9, code lost:
    
        r28 = r8;
        r2 = r1.length;
        r7 = r2 - 1;
        r2 = r2 - 2;
        r12 = 72057594037927935L;
        r1[r2] = (r1[r2] & 72057594037927935L) | (-72057594037927936L);
        r1[r7] = r1[0];
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0103, code lost:
    
        if (r2 == r3) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0105, code lost:
    
        r7 = r2 >> 3;
        r19 = (r2 & 7) << 3;
        r8 = (r1[r7] >> r19) & r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0113, code lost:
    
        if (r8 != 128) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0115, code lost:
    
        r2 = r2 + r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0119, code lost:
    
        if (r8 == 254) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x011c, code lost:
    
        r8 = java.lang.Long.hashCode(r4[r2]) * r20;
        r9 = (r8 ^ (r8 << 16)) >>> 7;
        r26 = findFirstAvailableSlot(r9);
        r9 = r9 & r3;
        r30 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x013d, code lost:
    
        if ((((r26 - r9) & r3) / 8) != (((r2 - r9) & r3) / 8)) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x013f, code lost:
    
        r34 = r14;
        r27 = r15;
        r1[r7] = ((r8 & 127) << r19) | (r1[r7] & (~(r28 << r19)));
        r1[r1.length - 1] = (r1[r34] & r30) | Long.MIN_VALUE;
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x015e, code lost:
    
        r15 = r27;
        r12 = r30;
        r14 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0165, code lost:
    
        r34 = r14;
        r27 = r15;
        r9 = r26 >> 3;
        r12 = r1[r9];
        r14 = (r26 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0177, code lost:
    
        if (((r12 >> r14) & r28) != 128) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0179, code lost:
    
        r35 = r2;
        r15 = r3;
        r1[r9] = ((~(r28 << r14)) & r12) | ((r8 & 127) << r14);
        r1[r7] = (r1[r7] & (~(r28 << r19))) | (128 << r19);
        r4[r26] = r4[r35];
        r4[r35] = 0;
        r6[r26] = r6[r35];
        r6[r35] = null;
        r2 = r35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x01a5, code lost:
    
        r35 = r2;
        r15 = r3;
        r1[r9] = ((r8 & 127) << r14) | ((~(r28 << r14)) & r12);
        r2 = r4[r26];
        r4[r26] = r4[r35];
        r4[r35] = r2;
        r2 = r6[r26];
        r6[r26] = r6[r35];
        r6[r35] = r2;
        r2 = r35 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x01c5, code lost:
    
        r1[r1.length - 1] = (r1[r34] & r30) | Long.MIN_VALUE;
        r2 = r2 + 1;
        r3 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x01d4, code lost:
    
        r34 = r14;
        r27 = r15;
        r38.growthLimit = androidx.collection.ScatterMapKt.loadedCapacity(r38._capacity) - r38._size;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01e5, code lost:
    
        r28 = 255;
        r34 = 0;
        r27 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01ec, code lost:
    
        r23 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01ef, code lost:
    
        r1 = androidx.collection.ScatterMapKt.nextCapacity(r38._capacity);
        r2 = r38.metadata;
        r3 = r38.keys;
        r4 = r38.values;
        r6 = r38._capacity;
        initializeStorage(r1);
        r1 = r38.metadata;
        r7 = r38.keys;
        r8 = r38.values;
        r9 = r38._capacity;
        r12 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x020a, code lost:
    
        if (r12 >= r6) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0219, code lost:
    
        if (((r2[r12 >> 3] >> ((r12 & 7) << 3)) & 255) >= r23) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x021b, code lost:
    
        r13 = r3[r12];
        r15 = java.lang.Long.hashCode(r13) * r20;
        r15 = r15 ^ (r15 << 16);
        r17 = r1;
        r1 = findFirstAvailableSlot(r15 >>> 7);
        r18 = r2;
        r1 = r15 & 127;
        r15 = r1 >> 3;
        r21 = (r1 & 7) << 3;
        r1 = (r17[r15] & (~(255 << r21))) | (r1 << r21);
        r17[r15] = r1;
        r17[(((r1 - 7) & r9) + (r9 & 7)) >> 3] = r1;
        r7[r1] = r13;
        r8[r1] = r4[r12];
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x025d, code lost:
    
        r17 = r1;
        r18 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0261, code lost:
    
        r12 = r12 + 1;
        r1 = r17;
        r2 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0268, code lost:
    
        r1 = findFirstAvailableSlot(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x026c, code lost:
    
        r17 = r1;
        r38._size++;
        r1 = r38.growthLimit;
        r2 = r38.metadata;
        r3 = r17 >> 3;
        r4 = r2[r3];
        r6 = (r17 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0286, code lost:
    
        if (((r4 >> r6) & r28) != r23) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0288, code lost:
    
        r34 = r27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x028a, code lost:
    
        r38.growthLimit = r1 - r34;
        r1 = r38._capacity;
        r4 = (r4 & (~(r28 << r6))) | (r10 << r6);
        r2[r3] = r4;
        r2[(((r17 - 7) & r1) + (r1 & 7)) >> 3] = r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void set(long j, Object obj) {
        int i;
        int iNumberOfTrailingZeros;
        int i2 = 1;
        char c = 7;
        int i3 = -862048943;
        int iHashCode = Long.hashCode(j) * (-862048943);
        int i4 = iHashCode ^ (iHashCode << 16);
        int i5 = i4 >>> 7;
        int i6 = i4 & 127;
        int i7 = this._capacity;
        int i8 = i5 & i7;
        int i9 = 0;
        loop0: while (true) {
            long[] jArr = this.metadata;
            int i10 = i8 >> 3;
            int i11 = (i8 & 7) << 3;
            int i12 = i2;
            char c2 = c;
            long j2 = (((-i11) >> 63) & (jArr[i10 + i2] << (64 - i11))) | (jArr[i10] >>> i11);
            long j3 = i6;
            int i13 = i9;
            int i14 = 0;
            long j4 = j2 ^ (j3 * 72340172838076673L);
            long j5 = -9187201950435737472L;
            long j6 = (~j4) & (j4 - 72340172838076673L) & (-9187201950435737472L);
            while (true) {
                if (j6 == 0) {
                    break;
                }
                iNumberOfTrailingZeros = (i8 + (Long.numberOfTrailingZeros(j6) >> 3)) & i7;
                int i15 = i3;
                if (this.keys[iNumberOfTrailingZeros] == j) {
                    break loop0;
                }
                j6 &= j6 - 1;
                i3 = i15;
            }
            i9 = i13 + 8;
            i8 = (i8 + i9) & i7;
            c = c2;
            i3 = i;
            i2 = i12;
        }
        this.keys[iNumberOfTrailingZeros] = j;
        this.values[iNumberOfTrailingZeros] = obj;
    }

    public /* synthetic */ MutableLongObjectMap(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }

    public MutableLongObjectMap(int i) {
        super(null);
        if (i >= 0) {
            initializeStorage(ScatterMapKt.unloadedCapacity(i));
        } else {
            RuntimeHelpersKt.throwIllegalArgumentException("Capacity must be a positive value.");
            throw null;
        }
    }
}
