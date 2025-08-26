package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class MutableIntObjectMap extends IntObjectMap {
    public int growthLimit;

    public MutableIntObjectMap() {
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int findAbsoluteInsertIndex(int i) {
        long j;
        int i2;
        long j2;
        long[] jArr;
        long[] jArr2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = 1;
        int i8 = -862048943;
        int iHashCode = Integer.hashCode(i) * (-862048943);
        int i9 = iHashCode ^ (iHashCode << 16);
        char c = 7;
        int i10 = i9 >>> 7;
        int i11 = i9 & 127;
        int i12 = this._capacity;
        int i13 = i10 & i12;
        int i14 = 0;
        while (true) {
            long[] jArr3 = this.metadata;
            int i15 = i13 >> 3;
            int i16 = (i13 & 7) << 3;
            int i17 = i8;
            char c2 = c;
            long j3 = (((-i16) >> 63) & (jArr3[i15 + i7] << (64 - i16))) | (jArr3[i15] >>> i16);
            long j4 = i11;
            int i18 = i7;
            int i19 = i11;
            long j5 = j3 ^ (j4 * 72340172838076673L);
            long j6 = -9187201950435737472L;
            long j7 = (~j5) & (j5 - 72340172838076673L) & (-9187201950435737472L);
            while (j7 != 0) {
                int iNumberOfTrailingZeros = (i13 + (Long.numberOfTrailingZeros(j7) >> 3)) & i12;
                long j8 = j6;
                if (this.keys[iNumberOfTrailingZeros] == i) {
                    return iNumberOfTrailingZeros;
                }
                j7 &= j7 - 1;
                j6 = j8;
            }
            long j9 = j6;
            if ((((~j3) << 6) & j3 & j9) != 0) {
                int iFindFirstAvailableSlot = findFirstAvailableSlot(i10);
                long j10 = 255;
                if (this.growthLimit == 0) {
                    long j11 = 254;
                    if (((this.metadata[iFindFirstAvailableSlot >> 3] >> ((iFindFirstAvailableSlot & 7) << 3)) & 255) == 254) {
                        j = 255;
                        i2 = i18;
                        j2 = 128;
                    } else {
                        int i20 = this._capacity;
                        if (i20 > 8) {
                            long j12 = this._size;
                            int i21 = ULong.$r8$clinit;
                            j2 = 128;
                            if (Long.compareUnsigned(j12 * 32, i20 * 25) <= 0) {
                                long[] jArr4 = this.metadata;
                                int i22 = this._capacity;
                                int[] iArr = this.keys;
                                Object[] objArr = this.values;
                                int i23 = (i22 + 7) >> 3;
                                int i24 = 0;
                                while (i24 < i23) {
                                    long j13 = j10;
                                    long j14 = jArr4[i24] & j9;
                                    jArr4[i24] = (-72340172838076674L) & ((~j14) + (j14 >>> c2));
                                    i24 += i18;
                                    j11 = j11;
                                    j10 = j13;
                                }
                                j = j10;
                                long j15 = j11;
                                int length = jArr4.length;
                                int i25 = length - 1;
                                int i26 = length - 2;
                                long j16 = 72057594037927935L;
                                jArr4[i26] = (jArr4[i26] & 72057594037927935L) | (-72057594037927936L);
                                jArr4[i25] = jArr4[0];
                                int i27 = 0;
                                while (i27 != i22) {
                                    int i28 = i27 >> 3;
                                    int i29 = (i27 & 7) << 3;
                                    long j17 = (jArr4[i28] >> i29) & j;
                                    if (j17 != 128 && j17 == j15) {
                                        int iHashCode2 = Integer.hashCode(iArr[i27]) * i17;
                                        int i30 = (iHashCode2 ^ (iHashCode2 << 16)) >>> 7;
                                        int iFindFirstAvailableSlot2 = findFirstAvailableSlot(i30);
                                        int i31 = i30 & i22;
                                        long j18 = j16;
                                        if (((iFindFirstAvailableSlot2 - i31) & i22) / 8 == ((i27 - i31) & i22) / 8) {
                                            i3 = i18;
                                            i4 = i17;
                                            jArr4[i28] = ((r8 & 127) << i29) | (jArr4[i28] & (~(j << i29)));
                                            jArr4[jArr4.length - 1] = (jArr4[0] & j18) | Long.MIN_VALUE;
                                            i27++;
                                        } else {
                                            i3 = i18;
                                            i4 = i17;
                                            int i32 = iFindFirstAvailableSlot2 >> 3;
                                            long j19 = jArr4[i32];
                                            int i33 = (iFindFirstAvailableSlot2 & 7) << 3;
                                            if (((j19 >> i33) & j) == 128) {
                                                int i34 = i27;
                                                i5 = i22;
                                                jArr4[i32] = ((~(j << i33)) & j19) | ((r8 & 127) << i33);
                                                jArr4[i28] = (jArr4[i28] & (~(j << i29))) | (128 << i29);
                                                iArr[iFindFirstAvailableSlot2] = iArr[i34];
                                                iArr[i34] = 0;
                                                objArr[iFindFirstAvailableSlot2] = objArr[i34];
                                                objArr[i34] = null;
                                                i6 = i34;
                                            } else {
                                                int i35 = i27;
                                                i5 = i22;
                                                jArr4[i32] = ((r8 & 127) << i33) | ((~(j << i33)) & j19);
                                                int i36 = iArr[iFindFirstAvailableSlot2];
                                                iArr[iFindFirstAvailableSlot2] = iArr[i35];
                                                iArr[i35] = i36;
                                                Object obj = objArr[iFindFirstAvailableSlot2];
                                                objArr[iFindFirstAvailableSlot2] = objArr[i35];
                                                objArr[i35] = obj;
                                                i6 = i35 - 1;
                                            }
                                            jArr4[jArr4.length - 1] = (jArr4[0] & j18) | Long.MIN_VALUE;
                                            i27 = i6 + 1;
                                            i22 = i5;
                                        }
                                        i17 = i4;
                                        j16 = j18;
                                        i18 = i3;
                                    } else {
                                        i27 += i18;
                                    }
                                }
                                i2 = i18;
                                this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - this._size;
                            }
                            iFindFirstAvailableSlot = findFirstAvailableSlot(i10);
                        } else {
                            j2 = 128;
                        }
                        j = 255;
                        i2 = i18;
                        int iNextCapacity = ScatterMapKt.nextCapacity(this._capacity);
                        long[] jArr5 = this.metadata;
                        int[] iArr2 = this.keys;
                        Object[] objArr2 = this.values;
                        int i37 = this._capacity;
                        initializeStorage(iNextCapacity);
                        long[] jArr6 = this.metadata;
                        int[] iArr3 = this.keys;
                        Object[] objArr3 = this.values;
                        int i38 = this._capacity;
                        int i39 = 0;
                        while (i39 < i37) {
                            if (((jArr5[i39 >> 3] >> ((i39 & 7) << 3)) & 255) < j2) {
                                int i40 = iArr2[i39];
                                int iHashCode3 = Integer.hashCode(i40) * i17;
                                int i41 = iHashCode3 ^ (iHashCode3 << 16);
                                int iFindFirstAvailableSlot3 = findFirstAvailableSlot(i41 >>> 7);
                                jArr = jArr6;
                                jArr2 = jArr5;
                                long j20 = i41 & 127;
                                int i42 = iFindFirstAvailableSlot3 >> 3;
                                int i43 = (iFindFirstAvailableSlot3 & 7) << 3;
                                long j21 = (jArr[i42] & (~(255 << i43))) | (j20 << i43);
                                jArr[i42] = j21;
                                jArr[(((iFindFirstAvailableSlot3 - 7) & i38) + (i38 & 7)) >> 3] = j21;
                                iArr3[iFindFirstAvailableSlot3] = i40;
                                objArr3[iFindFirstAvailableSlot3] = objArr2[i39];
                            } else {
                                jArr = jArr6;
                                jArr2 = jArr5;
                            }
                            i39++;
                            jArr5 = jArr2;
                            jArr6 = jArr;
                        }
                        iFindFirstAvailableSlot = findFirstAvailableSlot(i10);
                    }
                }
                this._size++;
                int i44 = this.growthLimit;
                long[] jArr7 = this.metadata;
                int i45 = iFindFirstAvailableSlot >> 3;
                long j22 = jArr7[i45];
                int i46 = (iFindFirstAvailableSlot & 7) << 3;
                if (((j22 >> i46) & j) != j2) {
                    i2 = 0;
                }
                this.growthLimit = i44 - i2;
                int i47 = this._capacity;
                long j23 = (j22 & (~(j << i46))) | (j4 << i46);
                jArr7[i45] = j23;
                jArr7[(((iFindFirstAvailableSlot - 7) & i47) + (i47 & 7)) >> 3] = j23;
                return iFindFirstAvailableSlot;
            }
            i14 += 8;
            i13 = (i13 + i14) & i12;
            c = c2;
            i11 = i19;
            i8 = i17;
            i7 = i18;
        }
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
        this.values = new Object[iMax];
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0060, code lost:
    
        if (((r4 & ((~r4) << 6)) & (-9187201950435737472L)) == 0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0062, code lost:
    
        r10 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object remove(int i) {
        int iNumberOfTrailingZeros;
        int iHashCode = Integer.hashCode(i) * (-862048943);
        int i2 = iHashCode ^ (iHashCode << 16);
        int i3 = i2 & 127;
        int i4 = this._capacity;
        int i5 = (i2 >>> 7) & i4;
        int i6 = 0;
        loop0: while (true) {
            long[] jArr = this.metadata;
            int i7 = i5 >> 3;
            int i8 = (i5 & 7) << 3;
            long j = ((jArr[i7 + 1] << (64 - i8)) & ((-i8) >> 63)) | (jArr[i7] >>> i8);
            long j2 = (i3 * 72340172838076673L) ^ j;
            long j3 = (~j2) & (j2 - 72340172838076673L) & (-9187201950435737472L);
            while (true) {
                if (j3 == 0) {
                    break;
                }
                iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j3) >> 3) + i5) & i4;
                if (this.keys[iNumberOfTrailingZeros] == i) {
                    break loop0;
                }
                j3 &= j3 - 1;
            }
            i6 += 8;
            i5 = (i5 + i6) & i4;
        }
        if (iNumberOfTrailingZeros >= 0) {
            return removeValueAt(iNumberOfTrailingZeros);
        }
        return null;
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
        Object[] objArr = this.values;
        Object obj = objArr[i];
        objArr[i] = null;
        return obj;
    }

    public final void set(int i, Object obj) {
        int iFindAbsoluteInsertIndex = findAbsoluteInsertIndex(i);
        this.keys[iFindAbsoluteInsertIndex] = i;
        this.values[iFindAbsoluteInsertIndex] = obj;
    }

    public /* synthetic */ MutableIntObjectMap(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }

    public MutableIntObjectMap(int i) {
        super(null);
        if (i >= 0) {
            initializeStorage(ScatterMapKt.unloadedCapacity(i));
        } else {
            RuntimeHelpersKt.throwIllegalArgumentException("Capacity must be a positive value.");
            throw null;
        }
    }
}
