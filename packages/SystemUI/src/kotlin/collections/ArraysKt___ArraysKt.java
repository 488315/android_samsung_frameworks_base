package kotlin.collections;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.ext.BitmapExtKt$$ExternalSyntheticLambda0;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Pair;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.ranges.IntRange;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.Sequence;

/* loaded from: classes4.dex */
public class ArraysKt___ArraysKt extends ArraysKt___ArraysJvmKt {
    public static Iterable asIterable(Object[] objArr) {
        return objArr.length == 0 ? EmptyList.INSTANCE : new ArraysKt___ArraysKt$asIterable$$inlined$Iterable$1(objArr);
    }

    public static Sequence asSequence(final Object[] objArr) {
        return objArr.length == 0 ? EmptySequence.INSTANCE : new Sequence() { // from class: kotlin.collections.ArraysKt___ArraysKt$asSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public final Iterator iterator() {
                return new ArrayIterator(objArr);
            }
        };
    }

    public static boolean contains(Object[] objArr, Object obj) {
        return indexOf(objArr, obj) >= 0;
    }

    public static List drop(int i, Object[] objArr) {
        if (i < 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Requested element count ", " is less than zero.").toString());
        }
        int length = objArr.length - i;
        if (length < 0) {
            length = 0;
        }
        if (length < 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(length, "Requested element count ", " is less than zero.").toString());
        }
        if (length == 0) {
            return EmptyList.INSTANCE;
        }
        int length2 = objArr.length;
        if (length >= length2) {
            return toList(objArr);
        }
        if (length == 1) {
            return Collections.singletonList(objArr[length2 - 1]);
        }
        ArrayList arrayList = new ArrayList(length);
        for (int i2 = length2 - length; i2 < length2; i2++) {
            arrayList.add(objArr[i2]);
        }
        return arrayList;
    }

    public static List filterNotNull(Object[] objArr) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : objArr) {
            if (obj != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static Object first(Object[] objArr) {
        if (objArr.length != 0) {
            return objArr[0];
        }
        throw new NoSuchElementException("Array is empty.");
    }

    public static IntRange getIndices(int[] iArr) {
        return new IntRange(0, iArr.length - 1);
    }

    public static Object getOrNull(int i, Object[] objArr) {
        if (i < 0 || i >= objArr.length) {
            return null;
        }
        return objArr[i];
    }

    public static int indexOf(Object[] objArr, Object obj) {
        int i = 0;
        if (obj == null) {
            int length = objArr.length;
            while (i < length) {
                if (objArr[i] == null) {
                    return i;
                }
                i++;
            }
            return -1;
        }
        int length2 = objArr.length;
        while (i < length2) {
            if (obj.equals(objArr[i])) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static String joinToString$default(byte[] bArr, CharSequence charSequence, BitmapExtKt$$ExternalSyntheticLambda0 bitmapExtKt$$ExternalSyntheticLambda0, int i) throws IOException {
        String str = (i & 2) != 0 ? "" : "[";
        String str2 = (i & 4) == 0 ? "]" : "";
        if ((i & 32) != 0) {
            bitmapExtKt$$ExternalSyntheticLambda0 = null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) str);
        int i2 = 0;
        for (byte b : bArr) {
            i2++;
            if (i2 > 1) {
                sb.append(charSequence);
            }
            if (bitmapExtKt$$ExternalSyntheticLambda0 != null) {
                sb.append((CharSequence) bitmapExtKt$$ExternalSyntheticLambda0.mo781invoke(Byte.valueOf(b)));
            } else {
                sb.append((CharSequence) String.valueOf((int) b));
            }
        }
        sb.append((CharSequence) str2);
        return sb.toString();
    }

    public static void toCollection(Collection collection, Object[] objArr) {
        for (Object obj : objArr) {
            collection.add(obj);
        }
    }

    public static List toList(Object[] objArr) {
        int length = objArr.length;
        return length != 0 ? length != 1 ? toMutableList(objArr) : Collections.singletonList(objArr[0]) : EmptyList.INSTANCE;
    }

    public static List toMutableList(Object[] objArr) {
        return new ArrayList(new ArrayAsCollection(objArr, false));
    }

    public static Set toSet(Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return EmptySet.INSTANCE;
        }
        if (length == 1) {
            return Collections.singleton(objArr[0]);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(objArr.length));
        toCollection(linkedHashSet, objArr);
        return linkedHashSet;
    }

    public static List zip(int[] iArr, int[] iArr2) {
        int iMin = Math.min(iArr.length, iArr2.length);
        ArrayList arrayList = new ArrayList(iMin);
        for (int i = 0; i < iMin; i++) {
            arrayList.add(new Pair(Integer.valueOf(iArr[i]), Integer.valueOf(iArr2[i])));
        }
        return arrayList;
    }

    public static boolean contains(int i, int[] iArr) {
        return indexOf(i, iArr) >= 0;
    }

    public static int indexOf(int i, int[] iArr) {
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (i == iArr[i2]) {
                return i2;
            }
        }
        return -1;
    }

    public static List toList(int[] iArr) {
        int length = iArr.length;
        if (length == 0) {
            return EmptyList.INSTANCE;
        }
        if (length != 1) {
            ArrayList arrayList = new ArrayList(iArr.length);
            for (int i : iArr) {
                arrayList.add(Integer.valueOf(i));
            }
            return arrayList;
        }
        return Collections.singletonList(Integer.valueOf(iArr[0]));
    }

    public static Set toSet(int[] iArr) {
        int length = iArr.length;
        if (length == 0) {
            return EmptySet.INSTANCE;
        }
        if (length != 1) {
            LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(iArr.length));
            for (int i : iArr) {
                linkedHashSet.add(Integer.valueOf(i));
            }
            return linkedHashSet;
        }
        return Collections.singleton(Integer.valueOf(iArr[0]));
    }

    public static List toList(long[] jArr) {
        int length = jArr.length;
        if (length == 0) {
            return EmptyList.INSTANCE;
        }
        if (length != 1) {
            ArrayList arrayList = new ArrayList(jArr.length);
            for (long j : jArr) {
                arrayList.add(Long.valueOf(j));
            }
            return arrayList;
        }
        return Collections.singletonList(Long.valueOf(jArr[0]));
    }

    public static List toList(float[] fArr) {
        int length = fArr.length;
        if (length == 0) {
            return EmptyList.INSTANCE;
        }
        if (length != 1) {
            ArrayList arrayList = new ArrayList(fArr.length);
            for (float f : fArr) {
                arrayList.add(Float.valueOf(f));
            }
            return arrayList;
        }
        return Collections.singletonList(Float.valueOf(fArr[0]));
    }

    public static List toList(boolean[] zArr) {
        int length = zArr.length;
        if (length == 0) {
            return EmptyList.INSTANCE;
        }
        if (length != 1) {
            ArrayList arrayList = new ArrayList(zArr.length);
            for (boolean z : zArr) {
                arrayList.add(Boolean.valueOf(z));
            }
            return arrayList;
        }
        return Collections.singletonList(Boolean.valueOf(zArr[0]));
    }
}
