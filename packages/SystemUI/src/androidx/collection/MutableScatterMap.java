package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;
import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        int iHashCode = (obj != null ? obj.hashCode() : 0) * (-862048943);
        int i4 = iHashCode ^ (iHashCode << 16);
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
                int iNumberOfTrailingZeros = (i8 + (Long.numberOfTrailingZeros(j7) >> 3)) & i7;
                int i14 = i3;
                if (Intrinsics.areEqual(this.keys[iNumberOfTrailingZeros], obj)) {
                    return iNumberOfTrailingZeros;
                }
                j7 &= j7 - 1;
                i3 = i14;
            }
            int i15 = i3;
            if ((j3 & ((~j3) << 6) & (-9187201950435737472L)) != 0) {
                int iFindFirstAvailableSlot = findFirstAvailableSlot(i5);
                long j8 = 255;
                if (this.growthLimit != 0 || ((this.metadata[iFindFirstAvailableSlot >> 3] >> ((iFindFirstAvailableSlot & 7) << 3)) & 255) == 254) {
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
                                    int iHashCode2 = (obj2 != null ? obj2.hashCode() : 0) * i15;
                                    int i26 = (iHashCode2 ^ (iHashCode2 << 16)) >>> 7;
                                    int iFindFirstAvailableSlot2 = findFirstAvailableSlot(i26);
                                    int i27 = i26 & i18;
                                    boolean z2 = z;
                                    if (((iFindFirstAvailableSlot2 - i27) & i18) / 8 == ((i23 - i27) & i18) / 8) {
                                        i = i18;
                                        objArr = objArr2;
                                        jArr2[i24] = (jArr2[i24] & (~(j << i25))) | ((r9 & 127) << i25);
                                        jArr2[jArr2.length - 1] = jArr2[0];
                                    } else {
                                        i = i18;
                                        objArr = objArr2;
                                        int i28 = iFindFirstAvailableSlot2 >> 3;
                                        long j13 = jArr2[i28];
                                        int i29 = (iFindFirstAvailableSlot2 & 7) << 3;
                                        if (((j13 >> i29) & j) == 128) {
                                            jArr2[i28] = (j13 & (~(j << i29))) | ((r9 & 127) << i29);
                                            jArr2[i24] = (jArr2[i24] & (~(j << i25))) | (128 << i25);
                                            objArr[iFindFirstAvailableSlot2] = objArr[i23];
                                            objArr[i23] = null;
                                            objArr3[iFindFirstAvailableSlot2] = objArr3[i23];
                                            objArr3[i23] = null;
                                        } else {
                                            jArr2[i28] = ((r9 & 127) << i29) | (j13 & (~(j << i29)));
                                            Object obj3 = objArr[iFindFirstAvailableSlot2];
                                            objArr[iFindFirstAvailableSlot2] = objArr[i23];
                                            objArr[i23] = obj3;
                                            Object obj4 = objArr3[iFindFirstAvailableSlot2];
                                            objArr3[iFindFirstAvailableSlot2] = objArr3[i23];
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
                        }
                        iFindFirstAvailableSlot = findFirstAvailableSlot(i5);
                    } else {
                        j2 = 128;
                    }
                    j = 255;
                    resizeStorage$collection(ScatterMapKt.nextCapacity(this._capacity));
                    iFindFirstAvailableSlot = findFirstAvailableSlot(i5);
                }
                this._size++;
                int i30 = this.growthLimit;
                long[] jArr3 = this.metadata;
                int i31 = iFindFirstAvailableSlot >> 3;
                long j14 = jArr3[i31];
                int i32 = (iFindFirstAvailableSlot & 7) << 3;
                this.growthLimit = i30 - (((j14 >> i32) & j) == j2 ? i12 : 0);
                int i33 = this._capacity;
                long j15 = (j14 & (~(j << i32))) | (j4 << i32);
                jArr3[i31] = j15;
                jArr3[(((iFindFirstAvailableSlot - 7) & i33) + (i33 & 7)) >> 3] = j15;
                return ~iFindFirstAvailableSlot;
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
        int iMax = i > 0 ? Math.max(7, ScatterMapKt.normalizeCapacity(i)) : 0;
        this._capacity = iMax;
        if (iMax == 0) {
            jArr = ScatterMapKt.EmptyGroup;
        } else {
            int i2 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i2];
            Arrays.fill(jArr2, 0, i2, -9187201950435737472L);
            int i3 = iMax >> 3;
            long j = 255 << ((iMax & 7) << 3);
            jArr2[i3] = (jArr2[i3] & (~j)) | j;
            jArr = jArr2;
        }
        this.metadata = jArr;
        this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - this._size;
        Object[] objArr = ContainerHelpersKt.EMPTY_OBJECTS;
        this.keys = iMax == 0 ? objArr : new Object[iMax];
        if (iMax != 0) {
            objArr = new Object[iMax];
        }
        this.values = objArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0068, code lost:
    
        if (((r4 & ((~r4) << 6)) & (-9187201950435737472L)) == 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x006a, code lost:
    
        r10 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object remove(Object obj) {
        int iNumberOfTrailingZeros;
        int i = 0;
        int iHashCode = (obj != null ? obj.hashCode() : 0) * (-862048943);
        int i2 = iHashCode ^ (iHashCode << 16);
        int i3 = i2 & 127;
        int i4 = this._capacity;
        int i5 = i2 >>> 7;
        loop0: while (true) {
            int i6 = i5 & i4;
            long[] jArr = this.metadata;
            int i7 = i6 >> 3;
            int i8 = (i6 & 7) << 3;
            long j = ((jArr[i7 + 1] << (64 - i8)) & ((-i8) >> 63)) | (jArr[i7] >>> i8);
            long j2 = (i3 * 72340172838076673L) ^ j;
            long j3 = (~j2) & (j2 - 72340172838076673L) & (-9187201950435737472L);
            while (true) {
                if (j3 == 0) {
                    break;
                }
                iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j3) >> 3) + i6) & i4;
                if (Intrinsics.areEqual(this.keys[iNumberOfTrailingZeros], obj)) {
                    break loop0;
                }
                j3 &= j3 - 1;
            }
            i += 8;
            i5 = i6 + i;
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
                int iHashCode = (obj != null ? obj.hashCode() : 0) * (-862048943);
                int i6 = iHashCode ^ (iHashCode << 16);
                int iFindFirstAvailableSlot = findFirstAvailableSlot(i6 >>> 7);
                i2 = i5;
                long j = i6 & 127;
                int i7 = iFindFirstAvailableSlot >> 3;
                int i8 = (iFindFirstAvailableSlot & 7) << 3;
                long j2 = (j << i8) | (jArr2[i7] & (~(255 << i8)));
                jArr2[i7] = j2;
                jArr2[(((iFindFirstAvailableSlot - 7) & i4) + (i4 & 7)) >> 3] = j2;
                objArr3[iFindFirstAvailableSlot] = obj;
                objArr4[iFindFirstAvailableSlot] = objArr2[i2];
            } else {
                i2 = i5;
            }
            i5 = i2 + 1;
        }
    }

    public final void set(Object obj, Object obj2) {
        int iFindInsertIndex = findInsertIndex(obj);
        if (iFindInsertIndex < 0) {
            iFindInsertIndex = ~iFindInsertIndex;
        }
        this.keys[iFindInsertIndex] = obj;
        this.values[iFindInsertIndex] = obj2;
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
