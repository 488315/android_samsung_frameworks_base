package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int max = i > 0 ? Math.max(7, ScatterMapKt.normalizeCapacity(i)) : 0;
        this._capacity = max;
        if (max == 0) {
            jArr = ScatterMapKt.EmptyGroup;
        } else {
            int i2 = ((max + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i2];
            Arrays.fill(jArr2, 0, i2, -9187201950435737472L);
            jArr = jArr2;
        }
        this.metadata = jArr;
        int i3 = max >> 3;
        long j = 255 << ((max & 7) << 3);
        jArr[i3] = (jArr[i3] & (~j)) | j;
        this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - this._size;
        this.keys = new long[max];
        this.values = new Object[max];
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0062, code lost:
    
        if (((r4 & ((~r4) << 6)) & (-9187201950435737472L)) == 0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0064, code lost:
    
        r10 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object remove(long r14) {
        /*
            r13 = this;
            int r0 = java.lang.Long.hashCode(r14)
            r1 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r0 = r0 * r1
            int r1 = r0 << 16
            r0 = r0 ^ r1
            r1 = r0 & 127(0x7f, float:1.78E-43)
            int r2 = r13._capacity
            int r0 = r0 >>> 7
            r0 = r0 & r2
            r3 = 0
        L13:
            long[] r4 = r13.metadata
            int r5 = r0 >> 3
            r6 = r0 & 7
            int r6 = r6 << 3
            r7 = r4[r5]
            long r7 = r7 >>> r6
            int r5 = r5 + 1
            r4 = r4[r5]
            int r9 = 64 - r6
            long r4 = r4 << r9
            long r9 = (long) r6
            long r9 = -r9
            r6 = 63
            long r9 = r9 >> r6
            long r4 = r4 & r9
            long r4 = r4 | r7
            long r6 = (long) r1
            r8 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r6 = r6 * r8
            long r6 = r6 ^ r4
            long r8 = r6 - r8
            long r6 = ~r6
            long r6 = r6 & r8
            r8 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r6 = r6 & r8
        L3e:
            r10 = 0
            int r12 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r12 == 0) goto L5b
            int r10 = java.lang.Long.numberOfTrailingZeros(r6)
            int r10 = r10 >> 3
            int r10 = r10 + r0
            r10 = r10 & r2
            long[] r11 = r13.keys
            r11 = r11[r10]
            int r11 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            if (r11 != 0) goto L55
            goto L65
        L55:
            r10 = 1
            long r10 = r6 - r10
            long r6 = r6 & r10
            goto L3e
        L5b:
            long r6 = ~r4
            r12 = 6
            long r6 = r6 << r12
            long r4 = r4 & r6
            long r4 = r4 & r8
            int r4 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r4 == 0) goto L98
            r10 = -1
        L65:
            r14 = 0
            if (r10 < 0) goto L97
            int r15 = r13._size
            int r15 = r15 + (-1)
            r13._size = r15
            long[] r15 = r13.metadata
            int r0 = r13._capacity
            int r1 = r10 >> 3
            r2 = r10 & 7
            int r2 = r2 << 3
            r3 = r15[r1]
            r5 = 255(0xff, double:1.26E-321)
            long r5 = r5 << r2
            long r5 = ~r5
            long r3 = r3 & r5
            r5 = 254(0xfe, double:1.255E-321)
            long r5 = r5 << r2
            long r2 = r3 | r5
            r15[r1] = r2
            int r1 = r10 + (-7)
            r1 = r1 & r0
            r0 = r0 & 7
            int r1 = r1 + r0
            int r0 = r1 >> 3
            r15[r0] = r2
            java.lang.Object[] r13 = r13.values
            r15 = r13[r10]
            r13[r10] = r14
            return r15
        L97:
            return r14
        L98:
            int r3 = r3 + 8
            int r0 = r0 + r3
            r0 = r0 & r2
            goto L13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableLongObjectMap.remove(long):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x006f, code lost:
    
        r20 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x007a, code lost:
    
        if (((r1 & ((~r1) << 6)) & (-9187201950435737472L)) == 0) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x007c, code lost:
    
        r1 = findFirstAvailableSlot(r5);
        r8 = 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0084, code lost:
    
        if (r38.growthLimit != 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0097, code lost:
    
        if (((r38.metadata[r1 >> 3] >> ((r1 & 7) << 3)) & 255) != 254) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00a3, code lost:
    
        r1 = r38._capacity;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00a5, code lost:
    
        if (r1 <= 8) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00a7, code lost:
    
        r3 = r38._size;
        r19 = kotlin.ULong.$r8$clinit;
        r23 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00bb, code lost:
    
        if (java.lang.Long.compareUnsigned(r3 * 32, r1 * 25) > 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00bd, code lost:
    
        r1 = r38.metadata;
        r3 = r38._capacity;
        r4 = r38.keys;
        r6 = r38.values;
        r7 = (r3 + 7) >> 3;
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00cc, code lost:
    
        if (r2 >= r7) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00ce, code lost:
    
        r28 = r8;
        r8 = r1[r2] & r12;
        r1[r2] = (-72340172838076674L) & ((~r8) + (r8 >>> r16));
        r2 = r2 + r15;
        r8 = r28;
        r12 = -9187201950435737472L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00e9, code lost:
    
        r28 = r8;
        r2 = r1.length;
        r7 = r2 - 1;
        r2 = r2 - 2;
        r12 = 72057594037927935L;
        r1[r2] = (r1[r2] & 72057594037927935L) | (-72057594037927936L);
        r1[r7] = r1[0];
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0103, code lost:
    
        if (r2 == r3) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0105, code lost:
    
        r7 = r2 >> 3;
        r19 = (r2 & 7) << 3;
        r8 = (r1[r7] >> r19) & r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0113, code lost:
    
        if (r8 != 128) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0119, code lost:
    
        if (r8 == 254) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x011c, code lost:
    
        r8 = java.lang.Long.hashCode(r4[r2]) * r20;
        r9 = (r8 ^ (r8 << 16)) >>> 7;
        r26 = findFirstAvailableSlot(r9);
        r9 = r9 & r3;
        r30 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x013d, code lost:
    
        if ((((r26 - r9) & r3) / 8) != (((r2 - r9) & r3) / 8)) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x013f, code lost:
    
        r34 = r14;
        r27 = r15;
        r1[r7] = ((r8 & 127) << r19) | (r1[r7] & (~(r28 << r19)));
        r1[r1.length - 1] = (r1[r34] & r30) | Long.MIN_VALUE;
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x015e, code lost:
    
        r15 = r27;
        r12 = r30;
        r14 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0165, code lost:
    
        r34 = r14;
        r27 = r15;
        r9 = r26 >> 3;
        r12 = r1[r9];
        r14 = (r26 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0177, code lost:
    
        if (((r12 >> r14) & r28) != 128) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0179, code lost:
    
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
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01c5, code lost:
    
        r1[r1.length - 1] = (r1[r34] & r30) | Long.MIN_VALUE;
        r2 = r2 + 1;
        r3 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x01a5, code lost:
    
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
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0115, code lost:
    
        r2 = r2 + r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x01d4, code lost:
    
        r34 = r14;
        r27 = r15;
        r38.growthLimit = androidx.collection.ScatterMapKt.loadedCapacity(r38._capacity) - r38._size;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0268, code lost:
    
        r1 = findFirstAvailableSlot(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x026c, code lost:
    
        r17 = r1;
        r38._size++;
        r1 = r38.growthLimit;
        r2 = r38.metadata;
        r3 = r17 >> 3;
        r4 = r2[r3];
        r6 = (r17 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0286, code lost:
    
        if (((r4 >> r6) & r28) != r23) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0288, code lost:
    
        r34 = r27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x028a, code lost:
    
        r38.growthLimit = r1 - r34;
        r1 = r38._capacity;
        r4 = (r4 & (~(r28 << r6))) | (r10 << r6);
        r2[r3] = r4;
        r2[(((r17 - 7) & r1) + (r1 & 7)) >> 3] = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01e5, code lost:
    
        r28 = 255;
        r34 = 0;
        r27 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01ef, code lost:
    
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
    /* JADX WARN: Code restructure failed: missing block: B:60:0x020a, code lost:
    
        if (r12 >= r6) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0219, code lost:
    
        if (((r2[r12 >> 3] >> ((r12 & 7) << 3)) & 255) >= r23) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x021b, code lost:
    
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
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0261, code lost:
    
        r12 = r12 + 1;
        r1 = r17;
        r2 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x025d, code lost:
    
        r17 = r1;
        r18 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x01ec, code lost:
    
        r23 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0099, code lost:
    
        r28 = 255;
        r34 = 0;
        r27 = r15;
        r23 = 128;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void set(long r39, java.lang.Object r41) {
        /*
            Method dump skipped, instructions count: 702
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableLongObjectMap.set(long, java.lang.Object):void");
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
