package androidx.navigation;

import android.net.Uri;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArraysKt__ArraysKt;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;

/* loaded from: classes.dex */
public abstract class NavType {
    public static final NavType$Companion$BoolArrayType$1 BoolArrayType;
    public static final NavType$Companion$BoolListType$1 BoolListType;
    public static final NavType$Companion$BoolType$1 BoolType;
    public static final NavType$Companion$FloatArrayType$1 FloatArrayType;
    public static final NavType$Companion$FloatListType$1 FloatListType;
    public static final NavType$Companion$FloatType$1 FloatType;
    public static final NavType$Companion$IntArrayType$1 IntArrayType;
    public static final NavType$Companion$IntListType$1 IntListType;
    public static final NavType$Companion$LongArrayType$1 LongArrayType;
    public static final NavType$Companion$LongListType$1 LongListType;
    public static final NavType$Companion$LongType$1 LongType;
    public static final NavType$Companion$StringArrayType$1 StringArrayType;
    public static final NavType$Companion$StringListType$1 StringListType;
    public static final NavType$Companion$StringType$1 StringType;
    public final boolean isNullableAllowed;
    public static final Companion Companion = new Companion(null);
    public static final NavType$Companion$IntType$1 IntType = new NavType$Companion$IntType$1();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [androidx.navigation.NavType$Companion$FloatListType$1] */
    /* JADX WARN: Type inference failed for: r0v12, types: [androidx.navigation.NavType$Companion$BoolArrayType$1] */
    /* JADX WARN: Type inference failed for: r0v13, types: [androidx.navigation.NavType$Companion$BoolListType$1] */
    /* JADX WARN: Type inference failed for: r0v14, types: [androidx.navigation.NavType$Companion$StringType$1] */
    /* JADX WARN: Type inference failed for: r0v15, types: [androidx.navigation.NavType$Companion$StringArrayType$1] */
    /* JADX WARN: Type inference failed for: r0v16, types: [androidx.navigation.NavType$Companion$StringListType$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.navigation.NavType$Companion$IntArrayType$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.navigation.NavType$Companion$IntListType$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [androidx.navigation.NavType$Companion$LongArrayType$1] */
    /* JADX WARN: Type inference failed for: r0v7, types: [androidx.navigation.NavType$Companion$LongListType$1] */
    /* JADX WARN: Type inference failed for: r0v9, types: [androidx.navigation.NavType$Companion$FloatArrayType$1] */
    static {
        new NavType() { // from class: androidx.navigation.NavType$Companion$ReferenceType$1
            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                return (Integer) bundle.get(str);
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "reference";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) throws NumberFormatException {
                int i;
                if (str.startsWith("0x")) {
                    String strSubstring = str.substring(2);
                    CharsKt__CharJVMKt.checkRadix(16);
                    i = Integer.parseInt(strSubstring, 16);
                } else {
                    i = Integer.parseInt(str);
                }
                return Integer.valueOf(i);
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                bundle.putInt(str, ((Number) obj).intValue());
            }
        };
        IntArrayType = new CollectionNavType() { // from class: androidx.navigation.NavType$Companion$IntArrayType$1
            @Override // androidx.navigation.CollectionNavType
            public final Object emptyCollection() {
                return new int[0];
            }

            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                return (int[]) bundle.get(str);
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "integer[]";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(Object obj, String str) {
                int[] iArr = (int[]) obj;
                NavType$Companion$IntType$1 navType$Companion$IntType$1 = NavType.IntType;
                return iArr != null ? ArraysKt___ArraysJvmKt.plus(iArr, new int[]{((Number) navType$Companion$IntType$1.parseValue(str)).intValue()}) : new int[]{((Number) navType$Companion$IntType$1.parseValue(str)).intValue()};
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                bundle.putIntArray(str, (int[]) obj);
            }

            @Override // androidx.navigation.CollectionNavType
            public final List serializeAsValues(Object obj) {
                List list;
                int[] iArr = (int[]) obj;
                if (iArr == null || (list = ArraysKt___ArraysKt.toList(iArr)) == null) {
                    return EmptyList.INSTANCE;
                }
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(String.valueOf(((Number) it.next()).intValue()));
                }
                return arrayList;
            }

            @Override // androidx.navigation.NavType
            public final boolean valueEquals(Object obj, Object obj2) {
                int[] iArr = (int[]) obj;
                int[] iArr2 = (int[]) obj2;
                return ArraysKt__ArraysKt.contentDeepEquals(iArr != null ? ArraysKt___ArraysJvmKt.toTypedArray(iArr) : null, iArr2 != null ? ArraysKt___ArraysJvmKt.toTypedArray(iArr2) : null);
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) {
                return new int[]{((Number) NavType.IntType.parseValue(str)).intValue()};
            }
        };
        IntListType = new CollectionNavType() { // from class: androidx.navigation.NavType$Companion$IntListType$1
            @Override // androidx.navigation.CollectionNavType
            public final Object emptyCollection() {
                return EmptyList.INSTANCE;
            }

            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                int[] iArr = (int[]) bundle.get(str);
                if (iArr != null) {
                    return ArraysKt___ArraysKt.toList(iArr);
                }
                return null;
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "List<Int>";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(Object obj, String str) {
                List list = (List) obj;
                NavType$Companion$IntType$1 navType$Companion$IntType$1 = NavType.IntType;
                if (list == null) {
                    return Collections.singletonList(navType$Companion$IntType$1.parseValue(str));
                }
                return CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(navType$Companion$IntType$1.parseValue(str)), (Collection) list);
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                List list = (List) obj;
                bundle.putIntArray(str, list != null ? CollectionsKt___CollectionsKt.toIntArray(list) : null);
            }

            @Override // androidx.navigation.CollectionNavType
            public final List serializeAsValues(Object obj) {
                List list = (List) obj;
                if (list == null) {
                    return EmptyList.INSTANCE;
                }
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(String.valueOf(((Number) it.next()).intValue()));
                }
                return arrayList;
            }

            @Override // androidx.navigation.NavType
            public final boolean valueEquals(Object obj, Object obj2) {
                List list = (List) obj;
                List list2 = (List) obj2;
                return ArraysKt__ArraysKt.contentDeepEquals(list != null ? (Integer[]) list.toArray(new Integer[0]) : null, list2 != null ? (Integer[]) list2.toArray(new Integer[0]) : null);
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) {
                return Collections.singletonList(NavType.IntType.parseValue(str));
            }
        };
        LongType = new NavType$Companion$LongType$1();
        LongArrayType = new CollectionNavType() { // from class: androidx.navigation.NavType$Companion$LongArrayType$1
            @Override // androidx.navigation.CollectionNavType
            public final Object emptyCollection() {
                return new long[0];
            }

            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                return (long[]) bundle.get(str);
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "long[]";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(Object obj, String str) {
                long[] jArr = (long[]) obj;
                NavType$Companion$LongType$1 navType$Companion$LongType$1 = NavType.LongType;
                if (jArr == null) {
                    return new long[]{((Number) navType$Companion$LongType$1.parseValue(str)).longValue()};
                }
                long[] jArr2 = {((Number) navType$Companion$LongType$1.parseValue(str)).longValue()};
                int length = jArr.length;
                long[] jArrCopyOf = Arrays.copyOf(jArr, length + 1);
                System.arraycopy(jArr2, 0, jArrCopyOf, length, 1);
                jArrCopyOf.getClass();
                return jArrCopyOf;
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                bundle.putLongArray(str, (long[]) obj);
            }

            @Override // androidx.navigation.CollectionNavType
            public final List serializeAsValues(Object obj) {
                List list;
                long[] jArr = (long[]) obj;
                if (jArr == null || (list = ArraysKt___ArraysKt.toList(jArr)) == null) {
                    return EmptyList.INSTANCE;
                }
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(String.valueOf(((Number) it.next()).longValue()));
                }
                return arrayList;
            }

            @Override // androidx.navigation.NavType
            public final boolean valueEquals(Object obj, Object obj2) {
                Long[] lArr;
                long[] jArr = (long[]) obj;
                long[] jArr2 = (long[]) obj2;
                Long[] lArr2 = null;
                if (jArr != null) {
                    lArr = new Long[jArr.length];
                    int length = jArr.length;
                    for (int i = 0; i < length; i++) {
                        lArr[i] = Long.valueOf(jArr[i]);
                    }
                } else {
                    lArr = null;
                }
                if (jArr2 != null) {
                    lArr2 = new Long[jArr2.length];
                    int length2 = jArr2.length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        lArr2[i2] = Long.valueOf(jArr2[i2]);
                    }
                }
                return ArraysKt__ArraysKt.contentDeepEquals(lArr, lArr2);
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) {
                return new long[]{((Number) NavType.LongType.parseValue(str)).longValue()};
            }
        };
        LongListType = new CollectionNavType() { // from class: androidx.navigation.NavType$Companion$LongListType$1
            @Override // androidx.navigation.CollectionNavType
            public final Object emptyCollection() {
                return EmptyList.INSTANCE;
            }

            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                long[] jArr = (long[]) bundle.get(str);
                if (jArr != null) {
                    return ArraysKt___ArraysKt.toList(jArr);
                }
                return null;
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "List<Long>";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(Object obj, String str) {
                List list = (List) obj;
                NavType$Companion$LongType$1 navType$Companion$LongType$1 = NavType.LongType;
                if (list == null) {
                    return Collections.singletonList(navType$Companion$LongType$1.parseValue(str));
                }
                return CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(navType$Companion$LongType$1.parseValue(str)), (Collection) list);
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                long[] jArr;
                List list = (List) obj;
                if (list != null) {
                    List list2 = list;
                    jArr = new long[list2.size()];
                    Iterator it = list2.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        jArr[i] = ((Number) it.next()).longValue();
                        i++;
                    }
                } else {
                    jArr = null;
                }
                bundle.putLongArray(str, jArr);
            }

            @Override // androidx.navigation.CollectionNavType
            public final List serializeAsValues(Object obj) {
                List list = (List) obj;
                if (list == null) {
                    return EmptyList.INSTANCE;
                }
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(String.valueOf(((Number) it.next()).longValue()));
                }
                return arrayList;
            }

            @Override // androidx.navigation.NavType
            public final boolean valueEquals(Object obj, Object obj2) {
                List list = (List) obj;
                List list2 = (List) obj2;
                return ArraysKt__ArraysKt.contentDeepEquals(list != null ? (Long[]) list.toArray(new Long[0]) : null, list2 != null ? (Long[]) list2.toArray(new Long[0]) : null);
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) {
                return Collections.singletonList(NavType.LongType.parseValue(str));
            }
        };
        FloatType = new NavType$Companion$FloatType$1();
        FloatArrayType = new CollectionNavType() { // from class: androidx.navigation.NavType$Companion$FloatArrayType$1
            @Override // androidx.navigation.CollectionNavType
            public final Object emptyCollection() {
                return new float[0];
            }

            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                return (float[]) bundle.get(str);
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "float[]";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(Object obj, String str) {
                float[] fArr = (float[]) obj;
                NavType$Companion$FloatType$1 navType$Companion$FloatType$1 = NavType.FloatType;
                if (fArr == null) {
                    return new float[]{((Number) navType$Companion$FloatType$1.parseValue(str)).floatValue()};
                }
                float[] fArr2 = {((Number) navType$Companion$FloatType$1.parseValue(str)).floatValue()};
                int length = fArr.length;
                float[] fArrCopyOf = Arrays.copyOf(fArr, length + 1);
                System.arraycopy(fArr2, 0, fArrCopyOf, length, 1);
                fArrCopyOf.getClass();
                return fArrCopyOf;
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                bundle.putFloatArray(str, (float[]) obj);
            }

            @Override // androidx.navigation.CollectionNavType
            public final List serializeAsValues(Object obj) {
                List list;
                float[] fArr = (float[]) obj;
                if (fArr == null || (list = ArraysKt___ArraysKt.toList(fArr)) == null) {
                    return EmptyList.INSTANCE;
                }
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(String.valueOf(((Number) it.next()).floatValue()));
                }
                return arrayList;
            }

            @Override // androidx.navigation.NavType
            public final boolean valueEquals(Object obj, Object obj2) {
                Float[] fArr;
                float[] fArr2 = (float[]) obj;
                float[] fArr3 = (float[]) obj2;
                Float[] fArr4 = null;
                if (fArr2 != null) {
                    fArr = new Float[fArr2.length];
                    int length = fArr2.length;
                    for (int i = 0; i < length; i++) {
                        fArr[i] = Float.valueOf(fArr2[i]);
                    }
                } else {
                    fArr = null;
                }
                if (fArr3 != null) {
                    fArr4 = new Float[fArr3.length];
                    int length2 = fArr3.length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        fArr4[i2] = Float.valueOf(fArr3[i2]);
                    }
                }
                return ArraysKt__ArraysKt.contentDeepEquals(fArr, fArr4);
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) {
                return new float[]{((Number) NavType.FloatType.parseValue(str)).floatValue()};
            }
        };
        FloatListType = new CollectionNavType() { // from class: androidx.navigation.NavType$Companion$FloatListType$1
            @Override // androidx.navigation.CollectionNavType
            public final Object emptyCollection() {
                return EmptyList.INSTANCE;
            }

            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                float[] fArr = (float[]) bundle.get(str);
                if (fArr != null) {
                    return ArraysKt___ArraysKt.toList(fArr);
                }
                return null;
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "List<Float>";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(Object obj, String str) {
                List list = (List) obj;
                NavType$Companion$FloatType$1 navType$Companion$FloatType$1 = NavType.FloatType;
                if (list == null) {
                    return Collections.singletonList(navType$Companion$FloatType$1.parseValue(str));
                }
                return CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(navType$Companion$FloatType$1.parseValue(str)), (Collection) list);
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                List list = (List) obj;
                bundle.putFloatArray(str, list != null ? CollectionsKt___CollectionsKt.toFloatArray(list) : null);
            }

            @Override // androidx.navigation.CollectionNavType
            public final List serializeAsValues(Object obj) {
                List list = (List) obj;
                if (list == null) {
                    return EmptyList.INSTANCE;
                }
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(String.valueOf(((Number) it.next()).floatValue()));
                }
                return arrayList;
            }

            @Override // androidx.navigation.NavType
            public final boolean valueEquals(Object obj, Object obj2) {
                List list = (List) obj;
                List list2 = (List) obj2;
                return ArraysKt__ArraysKt.contentDeepEquals(list != null ? (Float[]) list.toArray(new Float[0]) : null, list2 != null ? (Float[]) list2.toArray(new Float[0]) : null);
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) {
                return Collections.singletonList(NavType.FloatType.parseValue(str));
            }
        };
        BoolType = new NavType$Companion$BoolType$1();
        BoolArrayType = new CollectionNavType() { // from class: androidx.navigation.NavType$Companion$BoolArrayType$1
            @Override // androidx.navigation.CollectionNavType
            public final Object emptyCollection() {
                return new boolean[0];
            }

            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                return (boolean[]) bundle.get(str);
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "boolean[]";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(Object obj, String str) {
                boolean[] zArr = (boolean[]) obj;
                NavType$Companion$BoolType$1 navType$Companion$BoolType$1 = NavType.BoolType;
                if (zArr == null) {
                    return new boolean[]{((Boolean) navType$Companion$BoolType$1.parseValue(str)).booleanValue()};
                }
                boolean[] zArr2 = {((Boolean) navType$Companion$BoolType$1.parseValue(str)).booleanValue()};
                int length = zArr.length;
                boolean[] zArrCopyOf = Arrays.copyOf(zArr, length + 1);
                System.arraycopy(zArr2, 0, zArrCopyOf, length, 1);
                zArrCopyOf.getClass();
                return zArrCopyOf;
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                bundle.putBooleanArray(str, (boolean[]) obj);
            }

            @Override // androidx.navigation.CollectionNavType
            public final List serializeAsValues(Object obj) {
                List list;
                boolean[] zArr = (boolean[]) obj;
                if (zArr == null || (list = ArraysKt___ArraysKt.toList(zArr)) == null) {
                    return EmptyList.INSTANCE;
                }
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(String.valueOf(((Boolean) it.next()).booleanValue()));
                }
                return arrayList;
            }

            @Override // androidx.navigation.NavType
            public final boolean valueEquals(Object obj, Object obj2) {
                Boolean[] boolArr;
                boolean[] zArr = (boolean[]) obj;
                boolean[] zArr2 = (boolean[]) obj2;
                Boolean[] boolArr2 = null;
                if (zArr != null) {
                    boolArr = new Boolean[zArr.length];
                    int length = zArr.length;
                    for (int i = 0; i < length; i++) {
                        boolArr[i] = Boolean.valueOf(zArr[i]);
                    }
                } else {
                    boolArr = null;
                }
                if (zArr2 != null) {
                    boolArr2 = new Boolean[zArr2.length];
                    int length2 = zArr2.length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        boolArr2[i2] = Boolean.valueOf(zArr2[i2]);
                    }
                }
                return ArraysKt__ArraysKt.contentDeepEquals(boolArr, boolArr2);
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) {
                return new boolean[]{((Boolean) NavType.BoolType.parseValue(str)).booleanValue()};
            }
        };
        BoolListType = new CollectionNavType() { // from class: androidx.navigation.NavType$Companion$BoolListType$1
            @Override // androidx.navigation.CollectionNavType
            public final Object emptyCollection() {
                return EmptyList.INSTANCE;
            }

            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                boolean[] zArr = (boolean[]) bundle.get(str);
                if (zArr != null) {
                    return ArraysKt___ArraysKt.toList(zArr);
                }
                return null;
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "List<Boolean>";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(Object obj, String str) {
                List list = (List) obj;
                NavType$Companion$BoolType$1 navType$Companion$BoolType$1 = NavType.BoolType;
                if (list == null) {
                    return Collections.singletonList(navType$Companion$BoolType$1.parseValue(str));
                }
                return CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(navType$Companion$BoolType$1.parseValue(str)), (Collection) list);
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                List list = (List) obj;
                bundle.putBooleanArray(str, list != null ? CollectionsKt___CollectionsKt.toBooleanArray(list) : null);
            }

            @Override // androidx.navigation.CollectionNavType
            public final List serializeAsValues(Object obj) {
                List list = (List) obj;
                if (list == null) {
                    return EmptyList.INSTANCE;
                }
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(String.valueOf(((Boolean) it.next()).booleanValue()));
                }
                return arrayList;
            }

            @Override // androidx.navigation.NavType
            public final boolean valueEquals(Object obj, Object obj2) {
                List list = (List) obj;
                List list2 = (List) obj2;
                return ArraysKt__ArraysKt.contentDeepEquals(list != null ? (Boolean[]) list.toArray(new Boolean[0]) : null, list2 != null ? (Boolean[]) list2.toArray(new Boolean[0]) : null);
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) {
                return Collections.singletonList(NavType.BoolType.parseValue(str));
            }
        };
        StringType = new NavType() { // from class: androidx.navigation.NavType$Companion$StringType$1
            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                return (String) bundle.get(str);
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "string";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) {
                if (Intrinsics.areEqual(str, "null")) {
                    return null;
                }
                return str;
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                bundle.putString(str, (String) obj);
            }

            @Override // androidx.navigation.NavType
            public final String serializeAsValue(Object obj) {
                String str = (String) obj;
                String strEncode = str != null ? Uri.encode(str) : null;
                return strEncode == null ? "null" : strEncode;
            }
        };
        StringArrayType = new CollectionNavType() { // from class: androidx.navigation.NavType$Companion$StringArrayType$1
            @Override // androidx.navigation.CollectionNavType
            public final Object emptyCollection() {
                return new String[0];
            }

            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                return (String[]) bundle.get(str);
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "string[]";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(Object obj, String str) {
                String[] strArr = (String[]) obj;
                if (strArr == null) {
                    return new String[]{str};
                }
                String[] strArr2 = {str};
                int length = strArr.length;
                Object[] objArrCopyOf = Arrays.copyOf(strArr, length + 1);
                System.arraycopy(strArr2, 0, objArrCopyOf, length, 1);
                objArrCopyOf.getClass();
                return (String[]) objArrCopyOf;
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                bundle.putStringArray(str, (String[]) obj);
            }

            @Override // androidx.navigation.CollectionNavType
            public final List serializeAsValues(Object obj) {
                String[] strArr = (String[]) obj;
                if (strArr == null) {
                    return EmptyList.INSTANCE;
                }
                ArrayList arrayList = new ArrayList(strArr.length);
                for (String str : strArr) {
                    arrayList.add(Uri.encode(str));
                }
                return arrayList;
            }

            @Override // androidx.navigation.NavType
            public final boolean valueEquals(Object obj, Object obj2) {
                return ArraysKt__ArraysKt.contentDeepEquals((String[]) obj, (String[]) obj2);
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) {
                return new String[]{str};
            }
        };
        StringListType = new CollectionNavType() { // from class: androidx.navigation.NavType$Companion$StringListType$1
            @Override // androidx.navigation.CollectionNavType
            public final Object emptyCollection() {
                return EmptyList.INSTANCE;
            }

            @Override // androidx.navigation.NavType
            public final Object get(Bundle bundle, String str) {
                String[] strArr = (String[]) bundle.get(str);
                if (strArr != null) {
                    return ArraysKt___ArraysKt.toList(strArr);
                }
                return null;
            }

            @Override // androidx.navigation.NavType
            public final String getName() {
                return "List<String>";
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(String str) {
                return Collections.singletonList(str);
            }

            @Override // androidx.navigation.NavType
            public final void put(Bundle bundle, String str, Object obj) {
                List list = (List) obj;
                bundle.putStringArray(str, list != null ? (String[]) list.toArray(new String[0]) : null);
            }

            @Override // androidx.navigation.CollectionNavType
            public final List serializeAsValues(Object obj) {
                List list = (List) obj;
                if (list == null) {
                    return EmptyList.INSTANCE;
                }
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(Uri.encode((String) it.next()));
                }
                return arrayList;
            }

            @Override // androidx.navigation.NavType
            public final boolean valueEquals(Object obj, Object obj2) {
                List list = (List) obj;
                List list2 = (List) obj2;
                return ArraysKt__ArraysKt.contentDeepEquals(list != null ? (String[]) list.toArray(new String[0]) : null, list2 != null ? (String[]) list2.toArray(new String[0]) : null);
            }

            @Override // androidx.navigation.NavType
            public final Object parseValue(Object obj, String str) {
                List list = (List) obj;
                if (list == null) {
                    return Collections.singletonList(str);
                }
                return CollectionsKt___CollectionsKt.plus((Iterable) Collections.singletonList(str), (Collection) list);
            }
        };
    }

    public NavType(boolean z) {
        this.isNullableAllowed = z;
    }

    public abstract Object get(Bundle bundle, String str);

    public String getName() {
        return "nav_type";
    }

    public Object parseValue(Object obj, String str) {
        return parseValue(str);
    }

    public abstract Object parseValue(String str);

    public abstract void put(Bundle bundle, String str, Object obj);

    public String serializeAsValue(Object obj) {
        return String.valueOf(obj);
    }

    public final String toString() {
        return getName();
    }

    public boolean valueEquals(Object obj, Object obj2) {
        return Intrinsics.areEqual(obj, obj2);
    }
}
