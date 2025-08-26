package android.os;

import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BinaryOperator;

/* loaded from: classes3.dex */
public class BundleMerger implements Parcelable {
    public static final Parcelable.Creator<BundleMerger> CREATOR = new Parcelable.Creator<BundleMerger>() { // from class: android.os.BundleMerger.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BundleMerger createFromParcel(Parcel parcel) {
            return new BundleMerger(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BundleMerger[] newArray(int i) {
            return new BundleMerger[i];
        }
    };
    public static final int STRATEGY_ARRAY_APPEND = 50;
    public static final int STRATEGY_ARRAY_LIST_APPEND = 60;
    public static final int STRATEGY_ARRAY_UNION = 55;
    public static final int STRATEGY_BOOLEAN_AND = 30;
    public static final int STRATEGY_BOOLEAN_OR = 40;
    public static final int STRATEGY_COMPARABLE_MAX = 4;
    public static final int STRATEGY_COMPARABLE_MIN = 3;
    public static final int STRATEGY_FIRST = 1;
    public static final int STRATEGY_LAST = 2;
    public static final int STRATEGY_NUMBER_ADD = 10;
    public static final int STRATEGY_NUMBER_INCREMENT_FIRST = 20;
    public static final int STRATEGY_NUMBER_INCREMENT_FIRST_AND_ADD = 25;
    public static final int STRATEGY_REJECT = 0;
    public static final int STRATEGY_STRING_APPEND = 70;
    private static final String TAG = "BundleMerger";
    private int mDefaultStrategy;
    private final ArrayMap<String, Integer> mStrategies;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Strategy {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BundleMerger() {
        this.mDefaultStrategy = 0;
        this.mStrategies = new ArrayMap<>();
    }

    private BundleMerger(Parcel parcel) {
        this.mDefaultStrategy = 0;
        this.mStrategies = new ArrayMap<>();
        this.mDefaultStrategy = parcel.readInt();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.mStrategies.put(parcel.readString(), Integer.valueOf(parcel.readInt()));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDefaultStrategy);
        int size = this.mStrategies.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeString(this.mStrategies.keyAt(i2));
            parcel.writeInt(this.mStrategies.valueAt(i2).intValue());
        }
    }

    public void setDefaultMergeStrategy(int i) {
        this.mDefaultStrategy = i;
    }

    public void setMergeStrategy(String str, int i) {
        this.mStrategies.put(str, Integer.valueOf(i));
    }

    public int getMergeStrategy(String str) {
        return this.mStrategies.getOrDefault(str, Integer.valueOf(this.mDefaultStrategy)).intValue();
    }

    public BinaryOperator<Bundle> asBinaryOperator() {
        return new BinaryOperator() { // from class: android.os.BundleMerger$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.merge((Bundle) obj, (Bundle) obj2);
            }
        };
    }

    public Bundle merge(Bundle bundle, Bundle bundle2) {
        if (bundle == null && bundle2 == null) {
            return null;
        }
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        Bundle bundle3 = new Bundle();
        bundle3.putAll(bundle);
        bundle3.putAll(bundle2);
        ArraySet arraySet = new ArraySet();
        arraySet.addAll(bundle.keySet());
        arraySet.retainAll(bundle2.keySet());
        for (int i = 0; i < arraySet.size(); i++) {
            String str = (String) arraySet.valueAt(i);
            int mergeStrategy = getMergeStrategy(str);
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            try {
                bundle3.putObject(str, merge(mergeStrategy, obj, obj2));
            } catch (Exception e) {
                Log.w(TAG, "Failed to merge key " + str + " with " + obj + " and " + obj2 + " using strategy " + mergeStrategy, e);
            }
        }
        return bundle3;
    }

    public static Object merge(int i, Object obj, Object obj2) {
        if (obj != null) {
            if (obj2 != null) {
                if (obj.getClass() != obj2.getClass()) {
                    throw new IllegalArgumentException("Merging requires consistent classes; first " + obj.getClass() + " last " + obj2.getClass());
                }
                if (i != 0) {
                    if (i != 1) {
                        if (i != 2) {
                            if (i == 3) {
                                return comparableMin(obj, obj2);
                            }
                            if (i == 4) {
                                return comparableMax(obj, obj2);
                            }
                            if (i == 10) {
                                return numberAdd(obj, obj2);
                            }
                            if (i == 20) {
                                return numberIncrementFirst(obj, obj2);
                            }
                            if (i == 25) {
                                return numberAdd(numberIncrementFirst(obj, obj2), obj2);
                            }
                            if (i == 30) {
                                return booleanAnd(obj, obj2);
                            }
                            if (i == 40) {
                                return booleanOr(obj, obj2);
                            }
                            if (i == 50) {
                                return arrayAppend(obj, obj2);
                            }
                            if (i == 55) {
                                return arrayUnion(obj, obj2);
                            }
                            if (i == 60) {
                                return arrayListAppend(obj, obj2);
                            }
                            if (i == 70) {
                                return stringAppend(obj, obj2);
                            }
                            throw new UnsupportedOperationException();
                        }
                    }
                } else if (!Objects.deepEquals(obj, obj2)) {
                    return null;
                }
            }
            return obj;
        }
        return obj2;
    }

    private static Object comparableMin(Object obj, Object obj2) {
        return ((Comparable) obj).compareTo(obj2) < 0 ? obj : obj2;
    }

    private static Object comparableMax(Object obj, Object obj2) {
        return ((Comparable) obj).compareTo(obj2) >= 0 ? obj : obj2;
    }

    private static Object numberAdd(Object obj, Object obj2) {
        if (obj instanceof Integer) {
            return Integer.valueOf(((Integer) obj).intValue() + ((Integer) obj2).intValue());
        }
        if (obj instanceof Long) {
            return Long.valueOf(((Long) obj).longValue() + ((Long) obj2).longValue());
        }
        if (obj instanceof Float) {
            return Float.valueOf(((Float) obj).floatValue() + ((Float) obj2).floatValue());
        }
        if (obj instanceof Double) {
            return Double.valueOf(((Double) obj).doubleValue() + ((Double) obj2).doubleValue());
        }
        throw new IllegalArgumentException("Unable to add " + obj.getClass());
    }

    private static Number numberIncrementFirst(Object obj, Object obj2) {
        if (obj instanceof Integer) {
            return Integer.valueOf(((Integer) obj).intValue() + 1);
        }
        if (obj instanceof Long) {
            return Long.valueOf(((Long) obj).longValue() + 1);
        }
        throw new IllegalArgumentException("Unable to add " + obj.getClass());
    }

    private static Object booleanAnd(Object obj, Object obj2) {
        return Boolean.valueOf(((Boolean) obj).booleanValue() && ((Boolean) obj2).booleanValue());
    }

    private static Object booleanOr(Object obj, Object obj2) {
        return Boolean.valueOf(((Boolean) obj).booleanValue() || ((Boolean) obj2).booleanValue());
    }

    private static Object arrayAppend(Object obj, Object obj2) throws NegativeArraySizeException {
        if (!obj.getClass().isArray()) {
            throw new IllegalArgumentException("Unable to append " + obj.getClass());
        }
        Class<?> componentType = obj.getClass().getComponentType();
        int length = Array.getLength(obj);
        int length2 = Array.getLength(obj2);
        Object objNewInstance = Array.newInstance(componentType, length + length2);
        System.arraycopy(obj, 0, objNewInstance, 0, length);
        System.arraycopy(obj2, 0, objNewInstance, length, length2);
        return objNewInstance;
    }

    private static Object arrayUnion(Object obj, Object obj2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (!obj.getClass().isArray()) {
            throw new IllegalArgumentException("Unable to union " + obj.getClass());
        }
        int length = Array.getLength(obj);
        int length2 = Array.getLength(obj2);
        new ArrayList(length + length2);
        ArraySet arraySet = new ArraySet();
        for (int i = 0; i < length; i++) {
            arraySet.add(Array.get(obj, i));
        }
        for (int i2 = 0; i2 < length2; i2++) {
            arraySet.add(Array.get(obj2, i2));
        }
        Class<?> componentType = obj.getClass().getComponentType();
        int size = arraySet.size();
        Object objNewInstance = Array.newInstance(componentType, size);
        for (int i3 = 0; i3 < size; i3++) {
            Array.set(objNewInstance, i3, arraySet.valueAt(i3));
        }
        return objNewInstance;
    }

    private static Object arrayListAppend(Object obj, Object obj2) {
        if (!(obj instanceof ArrayList)) {
            throw new IllegalArgumentException("Unable to append " + obj.getClass());
        }
        ArrayList arrayList = (ArrayList) obj;
        ArrayList arrayList2 = (ArrayList) obj2;
        ArrayList arrayList3 = new ArrayList(arrayList.size() + arrayList2.size());
        arrayList3.addAll(arrayList);
        arrayList3.addAll(arrayList2);
        return arrayList3;
    }

    private static Object stringAppend(Object obj, Object obj2) {
        if (!(obj instanceof String)) {
            throw new IllegalArgumentException("Unable to append " + obj.getClass());
        }
        return ((String) obj) + ((String) obj2);
    }
}
