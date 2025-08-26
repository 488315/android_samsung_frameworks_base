package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;
import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import java.util.Collection;
import kotlin.ULong;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class MutableOrderedScatterSet extends OrderedScatterSet {
    public int growthLimit;

    public MutableOrderedScatterSet() {
        this(0, 1, null);
    }

    public final boolean add(Object obj) {
        int i = this._size;
        int iFindAbsoluteInsertIndex = findAbsoluteInsertIndex(obj);
        this.elements[iFindAbsoluteInsertIndex] = obj;
        long[] jArr = this.nodes;
        int i2 = this.head;
        jArr[iFindAbsoluteInsertIndex] = (i2 & 2147483647L) | 4611686016279904256L;
        if (i2 != Integer.MAX_VALUE) {
            jArr[i2] = ((iFindAbsoluteInsertIndex & 2147483647L) << 31) | (jArr[i2] & (-4611686016279904257L));
        }
        this.head = iFindAbsoluteInsertIndex;
        if (this.tail == Integer.MAX_VALUE) {
            this.tail = iFindAbsoluteInsertIndex;
        }
        return this._size != i;
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
        Arrays.fill(this.elements, 0, this._capacity, (Object) null);
        long[] jArr3 = this.nodes;
        Arrays.fill(jArr3, 0, jArr3.length, 4611686018427387903L);
        this.head = Integer.MAX_VALUE;
        this.tail = Integer.MAX_VALUE;
        this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - this._size;
    }

    public final int findAbsoluteInsertIndex(Object obj) {
        int i;
        long j;
        long j2;
        long j3;
        char c;
        long[] jArr;
        long[] jArr2;
        int i2;
        Object[] objArr;
        int i3 = 1;
        char c2 = 7;
        int i4 = 0;
        int i5 = -862048943;
        int iHashCode = (obj != null ? obj.hashCode() : 0) * (-862048943);
        int i6 = iHashCode ^ (iHashCode << 16);
        int i7 = i6 >>> 7;
        int i8 = i6 & 127;
        int i9 = this._capacity;
        int i10 = i7 & i9;
        int i11 = 0;
        while (true) {
            long[] jArr3 = this.metadata;
            int i12 = i10 >> 3;
            int i13 = (i10 & 7) << 3;
            int i14 = i3;
            char c3 = c2;
            long j4 = (((-i13) >> 63) & (jArr3[i12 + i3] << (64 - i13))) | (jArr3[i12] >>> i13);
            long j5 = i8;
            long j6 = j4 ^ (j5 * 72340172838076673L);
            long j7 = (j6 - 72340172838076673L) & (~j6) & (-9187201950435737472L);
            while (j7 != 0) {
                int iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j7) >> 3) + i10) & i9;
                int i15 = i5;
                if (Intrinsics.areEqual(this.elements[iNumberOfTrailingZeros], obj)) {
                    return iNumberOfTrailingZeros;
                }
                j7 &= j7 - 1;
                i5 = i15;
            }
            int i16 = i5;
            if ((j4 & ((~j4) << 6) & (-9187201950435737472L)) != 0) {
                int iFindFirstAvailableSlot = findFirstAvailableSlot(i7);
                if (this.growthLimit != 0 || ((this.metadata[iFindFirstAvailableSlot >> 3] >> ((iFindFirstAvailableSlot & 7) << 3)) & 255) == 254) {
                    i = 0;
                    j = 255;
                    j2 = j5;
                    j3 = 128;
                } else {
                    int i17 = this._capacity;
                    if (i17 > 8) {
                        c = 31;
                        long j8 = this._size;
                        int i18 = ULong.$r8$clinit;
                        j3 = 128;
                        if (Long.compareUnsigned(j8 * 32, i17 * 25) <= 0) {
                            long[] jArr4 = this.metadata;
                            if (jArr4 == null) {
                                i = 0;
                                j = 255;
                                j2 = j5;
                            } else {
                                int i19 = this._capacity;
                                Object[] objArr2 = this.elements;
                                long[] jArr5 = this.nodes;
                                long[] jArr6 = new long[i19];
                                j = 255;
                                long j9 = 9223372034707292159L;
                                Arrays.fill(jArr6, 0, i19, 9223372034707292159L);
                                int i20 = (i19 + 7) >> 3;
                                i = 0;
                                while (i4 < i20) {
                                    long j10 = j9;
                                    long j11 = jArr4[i4] & (-9187201950435737472L);
                                    jArr4[i4] = (-72340172838076674L) & ((~j11) + (j11 >>> c3));
                                    i4++;
                                    j9 = j10;
                                }
                                long j12 = j9;
                                int length = jArr4.length;
                                int i21 = length - 1;
                                int i22 = length - 2;
                                jArr4[i22] = (jArr4[i22] & 72057594037927935L) | (-72057594037927936L);
                                jArr4[i21] = jArr4[0];
                                int i23 = 0;
                                while (i23 != i19) {
                                    int i24 = i23 >> 3;
                                    int i25 = (i23 & 7) << 3;
                                    long j13 = (jArr4[i24] >> i25) & 255;
                                    if (j13 != 128 && j13 == 254) {
                                        Object obj2 = objArr2[i23];
                                        int iHashCode2 = (obj2 != null ? obj2.hashCode() : 0) * i16;
                                        int i26 = (iHashCode2 ^ (iHashCode2 << 16)) >>> 7;
                                        int iFindFirstAvailableSlot2 = findFirstAvailableSlot(i26);
                                        int i27 = i26 & i19;
                                        if (((iFindFirstAvailableSlot2 - i27) & i19) / 8 == ((i23 - i27) & i19) / 8) {
                                            long j14 = j5;
                                            jArr4[i24] = ((r14 & 127) << i25) | ((~(255 << i25)) & jArr4[i24]);
                                            if (jArr6[i23] == j12) {
                                                long j15 = i23;
                                                jArr6[i23] = j15 | (j15 << 32);
                                            }
                                            jArr4[jArr4.length - 1] = jArr4[0];
                                            i23++;
                                            j5 = j14;
                                        } else {
                                            long j16 = j5;
                                            int i28 = iFindFirstAvailableSlot2 >> 3;
                                            long j17 = jArr4[i28];
                                            int i29 = (iFindFirstAvailableSlot2 & 7) << 3;
                                            if (((j17 >> i29) & 255) == 128) {
                                                i2 = i19;
                                                objArr = objArr2;
                                                jArr4[i28] = ((~(255 << i29)) & j17) | ((r14 & 127) << i29);
                                                jArr4[i24] = (jArr4[i24] & (~(255 << i25))) | (128 << i25);
                                                objArr[iFindFirstAvailableSlot2] = objArr[i23];
                                                objArr[i23] = null;
                                                jArr5[iFindFirstAvailableSlot2] = jArr5[i23];
                                                jArr5[i23] = 4611686018427387903L;
                                                int i30 = (int) ((jArr6[i23] >> 32) & 4294967295L);
                                                if (i30 != Integer.MAX_VALUE) {
                                                    jArr6[i30] = (jArr6[i30] & (-4294967296L)) | iFindFirstAvailableSlot2;
                                                    jArr6[i23] = (jArr6[i23] & 4294967295L) | (-4294967296L);
                                                } else {
                                                    jArr6[i23] = (Integer.MAX_VALUE << 32) | iFindFirstAvailableSlot2;
                                                }
                                                jArr6[iFindFirstAvailableSlot2] = (i23 << 32) | Integer.MAX_VALUE;
                                            } else {
                                                i2 = i19;
                                                objArr = objArr2;
                                                jArr4[i28] = ((r14 & 127) << i29) | (j17 & (~(255 << i29)));
                                                Object obj3 = objArr[iFindFirstAvailableSlot2];
                                                objArr[iFindFirstAvailableSlot2] = objArr[i23];
                                                objArr[i23] = obj3;
                                                long j18 = jArr5[iFindFirstAvailableSlot2];
                                                jArr5[iFindFirstAvailableSlot2] = jArr5[i23];
                                                jArr5[i23] = j18;
                                                int i31 = (int) ((jArr6[i23] >> 32) & 4294967295L);
                                                if (i31 != Integer.MAX_VALUE) {
                                                    long j19 = iFindFirstAvailableSlot2;
                                                    jArr6[i31] = (jArr6[i31] & (-4294967296L)) | j19;
                                                    jArr6[i23] = (jArr6[i23] & 4294967295L) | (j19 << 32);
                                                } else {
                                                    long j20 = iFindFirstAvailableSlot2;
                                                    jArr6[i23] = j20 | (j20 << 32);
                                                    i31 = i23;
                                                }
                                                jArr6[iFindFirstAvailableSlot2] = (i31 << 32) | i23;
                                                i23--;
                                            }
                                            jArr4[jArr4.length - 1] = jArr4[0];
                                            i23++;
                                            j5 = j16;
                                            i19 = i2;
                                            objArr2 = objArr;
                                        }
                                    } else {
                                        i23++;
                                    }
                                }
                                j2 = j5;
                                this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - this._size;
                                long[] jArr7 = this.nodes;
                                int length2 = jArr7.length;
                                for (int i32 = 0; i32 < length2; i32++) {
                                    long j21 = jArr7[i32];
                                    jArr7[i32] = (((j21 & (-4611686018427387904L)) | (((int) ((j21 >> 31) & 2147483647L)) == Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) (jArr6[r8] & 4294967295L))) << 31) | (((int) (j21 & 2147483647L)) == Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) (jArr6[r9] & 4294967295L));
                                }
                                int i33 = this.head;
                                if (i33 != Integer.MAX_VALUE) {
                                    this.head = (int) (jArr6[i33] & 4294967295L);
                                }
                                int i34 = this.tail;
                                if (i34 != Integer.MAX_VALUE) {
                                    this.tail = (int) (jArr6[i34] & 4294967295L);
                                }
                            }
                        }
                        iFindFirstAvailableSlot = findFirstAvailableSlot(i7);
                    } else {
                        c = 31;
                        j3 = 128;
                    }
                    i = 0;
                    j = 255;
                    j2 = j5;
                    int iNextCapacity = ScatterMapKt.nextCapacity(this._capacity);
                    long[] jArr8 = this.metadata;
                    Object[] objArr3 = this.elements;
                    long[] jArr9 = this.nodes;
                    int i35 = this._capacity;
                    int[] iArr = new int[i35];
                    initializeStorage(iNextCapacity);
                    long[] jArr10 = this.metadata;
                    Object[] objArr4 = this.elements;
                    long[] jArr11 = this.nodes;
                    int i36 = this._capacity;
                    int i37 = 0;
                    while (i37 < i35) {
                        if (((jArr8[i37 >> 3] >> ((i37 & 7) << 3)) & 255) < j3) {
                            Object obj4 = objArr3[i37];
                            int iHashCode3 = (obj4 != null ? obj4.hashCode() : 0) * i16;
                            int i38 = iHashCode3 ^ (iHashCode3 << 16);
                            int iFindFirstAvailableSlot3 = findFirstAvailableSlot(i38 >>> 7);
                            long j22 = i38 & 127;
                            int i39 = iFindFirstAvailableSlot3 >> 3;
                            int i40 = (iFindFirstAvailableSlot3 & 7) << 3;
                            jArr = jArr10;
                            jArr2 = jArr8;
                            long j23 = (jArr10[i39] & (~(255 << i40))) | (j22 << i40);
                            jArr[i39] = j23;
                            jArr[(((iFindFirstAvailableSlot3 - 7) & i36) + (i36 & 7)) >> 3] = j23;
                            objArr4[iFindFirstAvailableSlot3] = obj4;
                            jArr11[iFindFirstAvailableSlot3] = jArr9[i37];
                            iArr[i37] = iFindFirstAvailableSlot3;
                        } else {
                            jArr = jArr10;
                            jArr2 = jArr8;
                        }
                        i37++;
                        jArr8 = jArr2;
                        jArr10 = jArr;
                    }
                    long[] jArr12 = this.nodes;
                    int length3 = jArr12.length;
                    for (int i41 = 0; i41 < length3; i41++) {
                        long j24 = jArr12[i41];
                        jArr12[i41] = (((j24 & (-4611686018427387904L)) | (((int) ((j24 >> c) & 2147483647L)) == Integer.MAX_VALUE ? Integer.MAX_VALUE : iArr[r8])) << c) | (((int) (j24 & 2147483647L)) == Integer.MAX_VALUE ? Integer.MAX_VALUE : iArr[r9]);
                    }
                    int i42 = this.head;
                    if (i42 != Integer.MAX_VALUE) {
                        this.head = iArr[i42];
                    }
                    int i43 = this.tail;
                    if (i43 != Integer.MAX_VALUE) {
                        this.tail = iArr[i43];
                    }
                    iFindFirstAvailableSlot = findFirstAvailableSlot(i7);
                }
                this._size++;
                int i44 = this.growthLimit;
                long[] jArr13 = this.metadata;
                int i45 = iFindFirstAvailableSlot >> 3;
                long j25 = jArr13[i45];
                int i46 = (iFindFirstAvailableSlot & 7) << 3;
                if (((j25 >> i46) & j) == j3) {
                    i = i14;
                }
                this.growthLimit = i44 - i;
                int i47 = this._capacity;
                long j26 = (j25 & (~(j << i46))) | (j2 << i46);
                jArr13[i45] = j26;
                jArr13[(((iFindFirstAvailableSlot - 7) & i47) + (i47 & 7)) >> 3] = j26;
                return iFindFirstAvailableSlot;
            }
            i11 += 8;
            i10 = (i10 + i11) & i9;
            i3 = i14;
            c2 = c3;
            i5 = i16;
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
        long[] jArr2;
        int iMax = i > 0 ? Math.max(7, ScatterMapKt.normalizeCapacity(i)) : 0;
        this._capacity = iMax;
        if (iMax == 0) {
            jArr = ScatterMapKt.EmptyGroup;
        } else {
            int i2 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr3 = new long[i2];
            Arrays.fill(jArr3, 0, i2, -9187201950435737472L);
            jArr = jArr3;
        }
        this.metadata = jArr;
        int i3 = iMax >> 3;
        long j = 255 << ((iMax & 7) << 3);
        jArr[i3] = (jArr[i3] & (~j)) | j;
        this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - this._size;
        this.elements = iMax == 0 ? ContainerHelpersKt.EMPTY_OBJECTS : new Object[iMax];
        if (iMax == 0) {
            jArr2 = SieveCacheKt.EmptyNodes;
        } else {
            long[] jArr4 = new long[iMax];
            Arrays.fill(jArr4, 0, iMax, 4611686018427387903L);
            jArr2 = jArr4;
        }
        this.nodes = jArr2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x006d, code lost:
    
        if (((r7 & ((~r7) << 6)) & (-9187201950435737472L)) == 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x006f, code lost:
    
        r11 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean remove(Object obj) {
        int iNumberOfTrailingZeros;
        int iHashCode = (obj != null ? obj.hashCode() : 0) * (-862048943);
        int i = iHashCode ^ (iHashCode << 16);
        int i2 = i & 127;
        int i3 = this._capacity;
        int i4 = (i >>> 7) & i3;
        int i5 = 0;
        loop0: while (true) {
            long[] jArr = this.metadata;
            int i6 = i4 >> 3;
            int i7 = (i4 & 7) << 3;
            long j = ((jArr[i6 + 1] << (64 - i7)) & ((-i7) >> 63)) | (jArr[i6] >>> i7);
            long j2 = (i2 * 72340172838076673L) ^ j;
            long j3 = (~j2) & (j2 - 72340172838076673L) & (-9187201950435737472L);
            while (true) {
                if (j3 == 0) {
                    break;
                }
                iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j3) >> 3) + i4) & i3;
                if (Intrinsics.areEqual(this.elements[iNumberOfTrailingZeros], obj)) {
                    break loop0;
                }
                j3 &= j3 - 1;
            }
            i5 += 8;
            i4 = (i4 + i5) & i3;
        }
        boolean z = iNumberOfTrailingZeros >= 0;
        if (z) {
            removeElementAt(iNumberOfTrailingZeros);
        }
        return z;
    }

    public final void removeElementAt(int i) {
        this._size--;
        long[] jArr = this.metadata;
        int i2 = this._capacity;
        int i3 = i >> 3;
        int i4 = (i & 7) << 3;
        long j = (jArr[i3] & (~(255 << i4))) | (254 << i4);
        jArr[i3] = j;
        jArr[(((i - 7) & i2) + (i2 & 7)) >> 3] = j;
        this.elements[i] = null;
        long[] jArr2 = this.nodes;
        long j2 = jArr2[i];
        int i5 = (int) ((j2 >> 31) & 2147483647L);
        int i6 = (int) (j2 & 2147483647L);
        if (i5 != Integer.MAX_VALUE) {
            jArr2[i5] = (jArr2[i5] & (-2147483648L)) | (i6 & 2147483647L);
        } else {
            this.head = i6;
        }
        if (i6 != Integer.MAX_VALUE) {
            jArr2[i6] = ((i5 & 2147483647L) << 31) | (jArr2[i6] & (-4611686016279904257L));
        } else {
            this.tail = i5;
        }
        jArr2[i] = 4611686018427387903L;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean retainAll(Collection collection) {
        Object[] objArr = this.elements;
        int i = this._size;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i2 = 0;
            while (true) {
                long j = jArr[i2];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i3 = 8 - ((~(i2 - length)) >>> 31);
                    for (int i4 = 0; i4 < i3; i4++) {
                        if ((255 & j) < 128) {
                            int i5 = (i2 << 3) + i4;
                            if (!CollectionsKt___CollectionsKt.contains(collection, objArr[i5])) {
                                removeElementAt(i5);
                            }
                        }
                        j >>= 8;
                    }
                    if (i3 != 8) {
                        break;
                    }
                    if (i2 == length) {
                        break;
                    }
                    i2++;
                }
            }
        }
        return i != this._size;
    }

    public /* synthetic */ MutableOrderedScatterSet(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }

    public MutableOrderedScatterSet(int i) {
        super(null);
        if (i >= 0) {
            initializeStorage(ScatterMapKt.unloadedCapacity(i));
        } else {
            RuntimeHelpersKt.throwIllegalArgumentException("Capacity must be a positive value.");
            throw null;
        }
    }
}
