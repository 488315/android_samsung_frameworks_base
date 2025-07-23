package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;
import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutableScatterMap extends ScatterMap {
    public int growthLimit;

    public MutableScatterMap() {
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
        Arrays.fill(this.keys, 0, this._capacity, (Object) null);
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

    public final int findInsertIndex(Object obj) {
        long j;
        long j2;
        int i;
        Object[] objArr;
        int i2 = 1;
        char c = 7;
        int i3 = -862048943;
        int hashCode = (obj != null ? obj.hashCode() : 0) * (-862048943);
        int i4 = hashCode ^ (hashCode << 16);
        int i5 = i4 >>> 7;
        int i6 = i4 & 127;
        int i7 = this._capacity;
        int i8 = i5 & i7;
        int i9 = 0;
        while (true) {
            long[] jArr = this.metadata;
            int i10 = i8 >> 3;
            int i11 = (i8 & 7) << 3;
            int i12 = i2;
            char c2 = c;
            long j3 = (((-i11) >> 63) & (jArr[i10 + i2] << (64 - i11))) | (jArr[i10] >>> i11);
            long j4 = i6;
            int i13 = i6;
            long j5 = j3 ^ (j4 * 72340172838076673L);
            long j6 = -9187201950435737472L;
            long j7 = (~j5) & (j5 - 72340172838076673L) & (-9187201950435737472L);
            while (j7 != 0) {
                int numberOfTrailingZeros = (i8 + (Long.numberOfTrailingZeros(j7) >> 3)) & i7;
                int i14 = i3;
                if (Intrinsics.areEqual(this.keys[numberOfTrailingZeros], obj)) {
                    return numberOfTrailingZeros;
                }
                j7 &= j7 - 1;
                i3 = i14;
            }
            int i15 = i3;
            if ((j3 & ((~j3) << 6) & (-9187201950435737472L)) != 0) {
                int findFirstAvailableSlot = findFirstAvailableSlot(i5);
                long j8 = 255;
                if (this.growthLimit != 0 || ((this.metadata[findFirstAvailableSlot >> 3] >> ((findFirstAvailableSlot & 7) << 3)) & 255) == 254) {
                    j = 255;
                    j2 = 128;
                } else {
                    int i16 = this._capacity;
                    if (i16 > 8) {
                        boolean z = 8;
                        j2 = 128;
                        long j9 = this._size;
                        int i17 = ULong.$r8$clinit;
                        if (Long.compareUnsigned(j9 * 32, i16 * 25) <= 0) {
                            long[] jArr2 = this.metadata;
                            int i18 = this._capacity;
                            Object[] objArr2 = this.keys;
                            Object[] objArr3 = this.values;
                            int i19 = (i18 + 7) >> 3;
                            int i20 = 0;
                            while (i20 < i19) {
                                long j10 = j8;
                                long j11 = jArr2[i20] & j6;
                                jArr2[i20] = (-72340172838076674L) & ((~j11) + (j11 >>> c2));
                                i20++;
                                j8 = j10;
                                j6 = -9187201950435737472L;
                            }
                            j = j8;
                            int length = jArr2.length;
                            int i21 = length - 1;
                            int i22 = length - 2;
                            jArr2[i22] = (jArr2[i22] & 72057594037927935L) | (-72057594037927936L);
                            jArr2[i21] = jArr2[0];
                            int i23 = 0;
                            while (i23 != i18) {
                                int i24 = i23 >> 3;
                                int i25 = (i23 & 7) << 3;
                                long j12 = (jArr2[i24] >> i25) & j;
                                if (j12 != 128 && j12 == 254) {
                                    Object obj2 = objArr2[i23];
                                    int hashCode2 = (obj2 != null ? obj2.hashCode() : 0) * i15;
                                    int i26 = (hashCode2 ^ (hashCode2 << 16)) >>> 7;
                                    int findFirstAvailableSlot2 = findFirstAvailableSlot(i26);
                                    int i27 = i26 & i18;
                                    boolean z2 = z;
                                    if (((findFirstAvailableSlot2 - i27) & i18) / 8 == ((i23 - i27) & i18) / 8) {
                                        i = i18;
                                        objArr = objArr2;
                                        jArr2[i24] = (jArr2[i24] & (~(j << i25))) | ((r9 & 127) << i25);
                                        jArr2[jArr2.length - 1] = jArr2[0];
                                    } else {
                                        i = i18;
                                        objArr = objArr2;
                                        int i28 = findFirstAvailableSlot2 >> 3;
                                        long j13 = jArr2[i28];
                                        int i29 = (findFirstAvailableSlot2 & 7) << 3;
                                        if (((j13 >> i29) & j) == 128) {
                                            jArr2[i28] = (j13 & (~(j << i29))) | ((r9 & 127) << i29);
                                            jArr2[i24] = (jArr2[i24] & (~(j << i25))) | (128 << i25);
                                            objArr[findFirstAvailableSlot2] = objArr[i23];
                                            objArr[i23] = null;
                                            objArr3[findFirstAvailableSlot2] = objArr3[i23];
                                            objArr3[i23] = null;
                                        } else {
                                            jArr2[i28] = ((r9 & 127) << i29) | (j13 & (~(j << i29)));
                                            Object obj3 = objArr[findFirstAvailableSlot2];
                                            objArr[findFirstAvailableSlot2] = objArr[i23];
                                            objArr[i23] = obj3;
                                            Object obj4 = objArr3[findFirstAvailableSlot2];
                                            objArr3[findFirstAvailableSlot2] = objArr3[i23];
                                            objArr3[i23] = obj4;
                                            i23--;
                                        }
                                        jArr2[jArr2.length - 1] = jArr2[0];
                                    }
                                    i23++;
                                    i18 = i;
                                    objArr2 = objArr;
                                    z = z2;
                                } else {
                                    i23++;
                                }
                            }
                            this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - this._size;
                            findFirstAvailableSlot = findFirstAvailableSlot(i5);
                        }
                    } else {
                        j2 = 128;
                    }
                    j = 255;
                    resizeStorage$collection(ScatterMapKt.nextCapacity(this._capacity));
                    findFirstAvailableSlot = findFirstAvailableSlot(i5);
                }
                this._size++;
                int i30 = this.growthLimit;
                long[] jArr3 = this.metadata;
                int i31 = findFirstAvailableSlot >> 3;
                long j14 = jArr3[i31];
                int i32 = (findFirstAvailableSlot & 7) << 3;
                this.growthLimit = i30 - (((j14 >> i32) & j) == j2 ? i12 : 0);
                int i33 = this._capacity;
                long j15 = (j14 & (~(j << i32))) | (j4 << i32);
                jArr3[i31] = j15;
                jArr3[(((findFirstAvailableSlot - 7) & i33) + (i33 & 7)) >> 3] = j15;
                return ~findFirstAvailableSlot;
            }
            i9 += 8;
            i8 = (i8 + i9) & i7;
            i2 = i12;
            c = c2;
            i6 = i13;
            i3 = i15;
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
            int i3 = max >> 3;
            long j = 255 << ((max & 7) << 3);
            jArr2[i3] = (jArr2[i3] & (~j)) | j;
            jArr = jArr2;
        }
        this.metadata = jArr;
        this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - this._size;
        Object[] objArr = ContainerHelpersKt.EMPTY_OBJECTS;
        this.keys = max == 0 ? objArr : new Object[max];
        if (max != 0) {
            objArr = new Object[max];
        }
        this.values = objArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0068, code lost:
    
        if (((r4 & ((~r4) << 6)) & (-9187201950435737472L)) == 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x006a, code lost:
    
        r10 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object remove(java.lang.Object r14) {
        /*
            r13 = this;
            r0 = 0
            if (r14 == 0) goto L8
            int r1 = r14.hashCode()
            goto L9
        L8:
            r1 = r0
        L9:
            r2 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r1 = r1 * r2
            int r2 = r1 << 16
            r1 = r1 ^ r2
            r2 = r1 & 127(0x7f, float:1.78E-43)
            int r3 = r13._capacity
            int r1 = r1 >>> 7
        L16:
            r1 = r1 & r3
            long[] r4 = r13.metadata
            int r5 = r1 >> 3
            r6 = r1 & 7
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
            long r6 = (long) r2
            r8 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r6 = r6 * r8
            long r6 = r6 ^ r4
            long r8 = r6 - r8
            long r6 = ~r6
            long r6 = r6 & r8
            r8 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r6 = r6 & r8
        L42:
            r10 = 0
            int r12 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r12 == 0) goto L61
            int r10 = java.lang.Long.numberOfTrailingZeros(r6)
            int r10 = r10 >> 3
            int r10 = r10 + r1
            r10 = r10 & r3
            java.lang.Object[] r11 = r13.keys
            r11 = r11[r10]
            boolean r11 = kotlin.jvm.internal.Intrinsics.areEqual(r11, r14)
            if (r11 == 0) goto L5b
            goto L6b
        L5b:
            r10 = 1
            long r10 = r6 - r10
            long r6 = r6 & r10
            goto L42
        L61:
            long r6 = ~r4
            r12 = 6
            long r6 = r6 << r12
            long r4 = r4 & r6
            long r4 = r4 & r8
            int r4 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r4 == 0) goto L74
            r10 = -1
        L6b:
            if (r10 < 0) goto L72
            java.lang.Object r13 = r13.removeValueAt(r10)
            return r13
        L72:
            r13 = 0
            return r13
        L74:
            int r0 = r0 + 8
            int r1 = r1 + r0
            goto L16
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableScatterMap.remove(java.lang.Object):java.lang.Object");
    }

    public final Object removeValueAt(int i) {
        this._size--;
        long[] jArr = this.metadata;
        int i2 = this._capacity;
        int i3 = i >> 3;
        int i4 = (i & 7) << 3;
        long j = (jArr[i3] & (~(255 << i4))) | (254 << i4);
        jArr[i3] = j;
        jArr[(((i - 7) & i2) + (i2 & 7)) >> 3] = j;
        this.keys[i] = null;
        Object[] objArr = this.values;
        Object obj = objArr[i];
        objArr[i] = null;
        return obj;
    }

    public final void resizeStorage$collection(int i) {
        int i2;
        long[] jArr = this.metadata;
        Object[] objArr = this.keys;
        Object[] objArr2 = this.values;
        int i3 = this._capacity;
        initializeStorage(i);
        long[] jArr2 = this.metadata;
        Object[] objArr3 = this.keys;
        Object[] objArr4 = this.values;
        int i4 = this._capacity;
        int i5 = 0;
        while (i5 < i3) {
            if (((jArr[i5 >> 3] >> ((i5 & 7) << 3)) & 255) < 128) {
                Object obj = objArr[i5];
                int hashCode = (obj != null ? obj.hashCode() : 0) * (-862048943);
                int i6 = hashCode ^ (hashCode << 16);
                int findFirstAvailableSlot = findFirstAvailableSlot(i6 >>> 7);
                i2 = i5;
                long j = i6 & 127;
                int i7 = findFirstAvailableSlot >> 3;
                int i8 = (findFirstAvailableSlot & 7) << 3;
                long j2 = (j << i8) | (jArr2[i7] & (~(255 << i8)));
                jArr2[i7] = j2;
                jArr2[(((findFirstAvailableSlot - 7) & i4) + (i4 & 7)) >> 3] = j2;
                objArr3[findFirstAvailableSlot] = obj;
                objArr4[findFirstAvailableSlot] = objArr2[i2];
            } else {
                i2 = i5;
            }
            i5 = i2 + 1;
        }
    }

    public final void set(Object obj, Object obj2) {
        int findInsertIndex = findInsertIndex(obj);
        if (findInsertIndex < 0) {
            findInsertIndex = ~findInsertIndex;
        }
        this.keys[findInsertIndex] = obj;
        this.values[findInsertIndex] = obj2;
    }

    public /* synthetic */ MutableScatterMap(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }

    public MutableScatterMap(int i) {
        super(null);
        if (i >= 0) {
            initializeStorage(ScatterMapKt.unloadedCapacity(i));
        } else {
            RuntimeHelpersKt.throwIllegalArgumentException("Capacity must be a positive value.");
            throw null;
        }
    }
}
