package androidx.collection;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class FloatSet {
    public int _capacity;
    public float[] elements;
    public long[] metadata;

    public /* synthetic */ FloatSet(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c3, code lost:
    
        if (((r11 & ((~r11) << 6)) & r22) == 0) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00c5, code lost:
    
        r12 = -1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r18v3 */
    /* JADX WARN: Type inference failed for: r18v4 */
    /* JADX WARN: Type inference failed for: r18v5, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean equals(Object obj) {
        boolean z;
        int i;
        boolean z2;
        ?? r18;
        int i2;
        long j;
        int iNumberOfTrailingZeros;
        boolean z3 = true;
        if (obj == this) {
            return true;
        }
        int i3 = 0;
        if (!(obj instanceof FloatSet)) {
            return false;
        }
        FloatSet floatSet = (FloatSet) obj;
        floatSet.getClass();
        float[] fArr = this.elements;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return true;
        }
        int i4 = 0;
        while (true) {
            long j2 = jArr[i4];
            long j3 = -9187201950435737472L;
            if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                int i5 = 8;
                int i6 = 8 - ((~(i4 - length)) >>> 31);
                int i7 = i3;
                while (i7 < i6) {
                    if ((255 & j2) < 128) {
                        float f = fArr[(i4 << 3) + i7];
                        floatSet.getClass();
                        int iHashCode = Float.hashCode(f) * (-862048943);
                        int i8 = iHashCode ^ (iHashCode << 16);
                        z2 = z3;
                        int i9 = i8 & 127;
                        r18 = i3;
                        int i10 = floatSet._capacity;
                        int i11 = (i8 >>> 7) & i10;
                        int i12 = r18 == true ? 1 : 0;
                        while (true) {
                            long[] jArr2 = floatSet.metadata;
                            int i13 = i11 >> 3;
                            j = j3;
                            int i14 = (i11 & 7) << 3;
                            long j4 = (jArr2[i13] >>> i14) | ((jArr2[i13 + 1] << (64 - i14)) & ((-i14) >> 63));
                            i2 = i5;
                            long j5 = j4 ^ (i9 * 72340172838076673L);
                            long j6 = (~j5) & (j5 - 72340172838076673L) & j;
                            while (true) {
                                if (j6 == 0) {
                                    break;
                                }
                                iNumberOfTrailingZeros = (i11 + (Long.numberOfTrailingZeros(j6) >> 3)) & i10;
                                if (floatSet.elements[iNumberOfTrailingZeros] == f) {
                                    break;
                                }
                                j6 &= j6 - 1;
                            }
                            i12 += 8;
                            i11 = (i11 + i12) & i10;
                            i5 = i2;
                            j3 = j;
                        }
                        if (!(iNumberOfTrailingZeros >= 0 ? z2 : r18 == true ? 1 : 0)) {
                            return r18;
                        }
                    } else {
                        z2 = z3;
                        r18 = i3;
                        i2 = i5;
                        j = j3;
                    }
                    j2 >>= i2;
                    i7++;
                    i5 = i2;
                    z3 = z2;
                    i3 = r18;
                    j3 = j;
                }
                z = z3;
                i = i3;
                if (i6 != i5) {
                    return z;
                }
            } else {
                z = z3;
                i = i3;
            }
            if (i4 == length) {
                return z;
            }
            i4++;
            z3 = z;
            i3 = i;
        }
    }

    public final int hashCode() {
        float[] fArr = this.elements;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return 0;
        }
        int i = 0;
        int iHashCode = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        iHashCode = Float.hashCode(fArr[(i << 3) + i3]) + iHashCode;
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return iHashCode;
                }
            }
            if (i == length) {
                return iHashCode;
            }
            i++;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005b A[PHI: r5
      0x005b: PHI (r5v2 int) = (r5v1 int), (r5v3 int) binds: [B:6:0x0024, B:18:0x0059] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) "[");
        float[] fArr = this.elements;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            int i2 = 0;
            loop0: while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i3 = 8 - ((~(i - length)) >>> 31);
                    for (int i4 = 0; i4 < i3; i4++) {
                        if ((255 & j) < 128) {
                            float f = fArr[(i << 3) + i4];
                            if (i2 == -1) {
                                sb.append((CharSequence) "...");
                                break loop0;
                            }
                            if (i2 != 0) {
                                sb.append((CharSequence) ", ");
                            }
                            sb.append(f);
                            i2++;
                        }
                        j >>= 8;
                    }
                    if (i3 != 8) {
                        break;
                    }
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
            sb.append((CharSequence) "]");
        } else {
            sb.append((CharSequence) "]");
        }
        return sb.toString();
    }

    private FloatSet() {
        this.metadata = ScatterMapKt.EmptyGroup;
        this.elements = FloatSetKt.EmptyFloatArray;
    }
}
