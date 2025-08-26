package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class MutableObjectIntMap extends ObjectIntMap {
    public int growthLimit;

    public MutableObjectIntMap() {
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

    public final int findIndex(Object obj) {
        long j;
        long j2;
        long[] jArr;
        long[] jArr2;
        int i;
        Object[] objArr;
        int i2 = 1;
        char c = 7;
        int i3 = -862048943;
        int iHashCode = (obj != null ? obj.hashCode() : 0) * (-862048943);
        int i4 = iHashCode ^ (iHashCode << 16);
        int i5 = i4 >>> 7;
        int i6 = i4 & 127;
        int i7 = this._capacity;
        int i8 = i5 & i7;
        int i9 = 0;
        while (true) {
            long[] jArr3 = this.metadata;
            int i10 = i8 >> 3;
            int i11 = (i8 & 7) << 3;
            int i12 = i2;
            char c2 = c;
            long j3 = (((-i11) >> 63) & (jArr3[i10 + i2] << (64 - i11))) | (jArr3[i10] >>> i11);
            long j4 = i6;
            int i13 = i6;
            int i14 = 0;
            long j5 = j3 ^ (j4 * 72340172838076673L);
            long j6 = -9187201950435737472L;
            long j7 = (~j5) & (j5 - 72340172838076673L) & (-9187201950435737472L);
            while (j7 != 0) {
                int iNumberOfTrailingZeros = (i8 + (Long.numberOfTrailingZeros(j7) >> 3)) & i7;
                int i15 = i3;
                if (Intrinsics.areEqual(this.keys[iNumberOfTrailingZeros], obj)) {
                    return iNumberOfTrailingZeros;
                }
                j7 &= j7 - 1;
                i3 = i15;
            }
            int i16 = i3;
            if ((j3 & ((~j3) << 6) & (-9187201950435737472L)) != 0) {
                int iFindFirstAvailableSlot = findFirstAvailableSlot(i5);
                long j8 = 255;
                if (this.growthLimit != 0 || ((this.metadata[iFindFirstAvailableSlot >> 3] >> ((iFindFirstAvailableSlot & 7) << 3)) & 255) == 254) {
                    j = 255;
                    j2 = 128;
                } else {
                    int i17 = this._capacity;
                    if (i17 > 8) {
                        boolean z = 8;
                        j2 = 128;
                        long j9 = this._size;
                        int i18 = ULong.$r8$clinit;
                        if (Long.compareUnsigned(j9 * 32, i17 * 25) <= 0) {
                            long[] jArr4 = this.metadata;
                            int i19 = this._capacity;
                            Object[] objArr2 = this.keys;
                            int[] iArr = this.values;
                            int i20 = (i19 + 7) >> 3;
                            int i21 = 0;
                            while (i21 < i20) {
                                long j10 = j8;
                                long j11 = jArr4[i21] & j6;
                                jArr4[i21] = (-72340172838076674L) & ((~j11) + (j11 >>> c2));
                                i21++;
                                j8 = j10;
                                j6 = -9187201950435737472L;
                            }
                            j = j8;
                            int length = jArr4.length;
                            int i22 = length - 1;
                            int i23 = length - 2;
                            long j12 = 72057594037927935L;
                            jArr4[i23] = (jArr4[i23] & 72057594037927935L) | (-72057594037927936L);
                            jArr4[i22] = jArr4[0];
                            int i24 = 0;
                            while (i24 != i19) {
                                int i25 = i24 >> 3;
                                int i26 = (i24 & 7) << 3;
                                long j13 = (jArr4[i25] >> i26) & j;
                                if (j13 != 128 && j13 == 254) {
                                    Object obj2 = objArr2[i24];
                                    int iHashCode2 = (obj2 != null ? obj2.hashCode() : 0) * i16;
                                    int i27 = (iHashCode2 ^ (iHashCode2 << 16)) >>> 7;
                                    int iFindFirstAvailableSlot2 = findFirstAvailableSlot(i27);
                                    int i28 = i27 & i19;
                                    boolean z2 = z;
                                    if (((iFindFirstAvailableSlot2 - i28) & i19) / 8 == ((i24 - i28) & i19) / 8) {
                                        long j14 = j12;
                                        jArr4[i25] = ((r9 & 127) << i26) | (jArr4[i25] & (~(j << i26)));
                                        jArr4[jArr4.length - 1] = (jArr4[0] & j14) | Long.MIN_VALUE;
                                        i24++;
                                        z = z2;
                                        j12 = j14;
                                    } else {
                                        long j15 = j12;
                                        int i29 = iFindFirstAvailableSlot2 >> 3;
                                        long j16 = jArr4[i29];
                                        int i30 = (iFindFirstAvailableSlot2 & 7) << 3;
                                        if (((j16 >> i30) & j) == 128) {
                                            i = i19;
                                            objArr = objArr2;
                                            jArr4[i29] = ((~(j << i30)) & j16) | ((r9 & 127) << i30);
                                            jArr4[i25] = (jArr4[i25] & (~(j << i26))) | (128 << i26);
                                            objArr[iFindFirstAvailableSlot2] = objArr[i24];
                                            objArr[i24] = null;
                                            iArr[iFindFirstAvailableSlot2] = iArr[i24];
                                            iArr[i24] = 0;
                                        } else {
                                            i = i19;
                                            objArr = objArr2;
                                            jArr4[i29] = ((r9 & 127) << i30) | ((~(j << i30)) & j16);
                                            Object obj3 = objArr[iFindFirstAvailableSlot2];
                                            objArr[iFindFirstAvailableSlot2] = objArr[i24];
                                            objArr[i24] = obj3;
                                            int i31 = iArr[iFindFirstAvailableSlot2];
                                            iArr[iFindFirstAvailableSlot2] = iArr[i24];
                                            iArr[i24] = i31;
                                            i24--;
                                        }
                                        jArr4[jArr4.length - 1] = (jArr4[0] & j15) | Long.MIN_VALUE;
                                        i24++;
                                        i19 = i;
                                        z = z2;
                                        j12 = j15;
                                        objArr2 = objArr;
                                    }
                                } else {
                                    i24++;
                                }
                            }
                            this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - this._size;
                        }
                        iFindFirstAvailableSlot = findFirstAvailableSlot(i5);
                    } else {
                        j2 = 128;
                    }
                    j = 255;
                    int iNextCapacity = ScatterMapKt.nextCapacity(this._capacity);
                    long[] jArr5 = this.metadata;
                    Object[] objArr3 = this.keys;
                    int[] iArr2 = this.values;
                    int i32 = this._capacity;
                    initializeStorage(iNextCapacity);
                    long[] jArr6 = this.metadata;
                    Object[] objArr4 = this.keys;
                    int[] iArr3 = this.values;
                    int i33 = this._capacity;
                    int i34 = 0;
                    while (i34 < i32) {
                        if (((jArr5[i34 >> 3] >> ((i34 & 7) << 3)) & 255) < j2) {
                            Object obj4 = objArr3[i34];
                            int iHashCode3 = (obj4 != null ? obj4.hashCode() : i14) * i16;
                            int i35 = iHashCode3 ^ (iHashCode3 << 16);
                            int iFindFirstAvailableSlot3 = findFirstAvailableSlot(i35 >>> 7);
                            jArr = jArr6;
                            jArr2 = jArr5;
                            long j17 = i35 & 127;
                            int i36 = iFindFirstAvailableSlot3 >> 3;
                            int i37 = (iFindFirstAvailableSlot3 & 7) << 3;
                            long j18 = (jArr[i36] & (~(255 << i37))) | (j17 << i37);
                            jArr[i36] = j18;
                            jArr[(((iFindFirstAvailableSlot3 - 7) & i33) + (i33 & 7)) >> 3] = j18;
                            objArr4[iFindFirstAvailableSlot3] = obj4;
                            iArr3[iFindFirstAvailableSlot3] = iArr2[i34];
                        } else {
                            jArr = jArr6;
                            jArr2 = jArr5;
                        }
                        i34++;
                        jArr5 = jArr2;
                        jArr6 = jArr;
                        i14 = 0;
                    }
                    iFindFirstAvailableSlot = findFirstAvailableSlot(i5);
                }
                this._size++;
                int i38 = this.growthLimit;
                long[] jArr7 = this.metadata;
                int i39 = iFindFirstAvailableSlot >> 3;
                long j19 = jArr7[i39];
                int i40 = (iFindFirstAvailableSlot & 7) << 3;
                if (((j19 >> i40) & j) != j2) {
                    i12 = 0;
                }
                this.growthLimit = i38 - i12;
                int i41 = this._capacity;
                long j20 = (j19 & (~(j << i40))) | (j4 << i40);
                jArr7[i39] = j20;
                jArr7[(((iFindFirstAvailableSlot - 7) & i41) + (i41 & 7)) >> 3] = j20;
                return ~iFindFirstAvailableSlot;
            }
            i9 += 8;
            i8 = (i8 + i9) & i7;
            i2 = i12;
            c = c2;
            i6 = i13;
            i3 = i16;
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
        this.keys = new Object[iMax];
        this.values = new int[iMax];
    }

    public final void removeValueAt(int i) {
        this._size--;
        long[] jArr = this.metadata;
        int i2 = this._capacity;
        int i3 = i >> 3;
        int i4 = (i & 7) << 3;
        long j = (jArr[i3] & (~(255 << i4))) | (254 << i4);
        jArr[i3] = j;
        jArr[(((i - 7) & i2) + (i2 & 7)) >> 3] = j;
        this.keys[i] = null;
    }

    public final void set(int i, Object obj) {
        int iFindIndex = findIndex(obj);
        if (iFindIndex < 0) {
            iFindIndex = ~iFindIndex;
        }
        this.keys[iFindIndex] = obj;
        this.values[iFindIndex] = i;
    }

    public /* synthetic */ MutableObjectIntMap(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }

    public MutableObjectIntMap(int i) {
        super(null);
        if (i >= 0) {
            initializeStorage(ScatterMapKt.unloadedCapacity(i));
        } else {
            RuntimeHelpersKt.throwIllegalArgumentException("Capacity must be a positive value.");
            throw null;
        }
    }
}
