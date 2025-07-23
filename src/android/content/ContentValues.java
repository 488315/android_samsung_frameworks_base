package android.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class ContentValues implements Parcelable {
    public static final Parcelable.Creator<ContentValues> CREATOR = new Parcelable.Creator<ContentValues>() { // from class: android.content.ContentValues.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentValues createFromParcel(Parcel parcel) {
            return new ContentValues(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentValues[] newArray(int i) {
            return new ContentValues[i];
        }
    };
    public static final String TAG = "ContentValues";
    private final ArrayMap<String, Object> mMap;

    @Deprecated
    private HashMap<String, Object> mValues;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContentValues() {
        this.mMap = new ArrayMap<>();
    }

    public ContentValues(int i) {
        Preconditions.checkArgumentNonnegative(i);
        this.mMap = new ArrayMap<>(i);
    }

    public ContentValues(ContentValues contentValues) {
        Objects.requireNonNull(contentValues);
        this.mMap = new ArrayMap<>(contentValues.mMap);
    }

    @Deprecated
    private ContentValues(HashMap<String, Object> hashMap) {
        ArrayMap<String, Object> arrayMap = new ArrayMap<>();
        this.mMap = arrayMap;
        arrayMap.putAll(hashMap);
    }

    private ContentValues(Parcel parcel) {
        ArrayMap<String, Object> arrayMap = new ArrayMap<>(parcel.readInt());
        this.mMap = arrayMap;
        parcel.readArrayMap(arrayMap, null);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ContentValues) {
            return this.mMap.equals(((ContentValues) obj).mMap);
        }
        return false;
    }

    public ArrayMap<String, Object> getValues() {
        return this.mMap;
    }

    public int hashCode() {
        return this.mMap.hashCode();
    }

    public void put(String str, String str2) {
        this.mMap.put(str, str2);
    }

    public void putAll(ContentValues contentValues) {
        this.mMap.putAll((ArrayMap<? extends String, ? extends Object>) contentValues.mMap);
    }

    public void put(String str, Byte b) {
        this.mMap.put(str, b);
    }

    public void put(String str, Short sh) {
        this.mMap.put(str, sh);
    }

    public void put(String str, Integer num) {
        this.mMap.put(str, num);
    }

    public void put(String str, Long l) {
        this.mMap.put(str, l);
    }

    public void put(String str, Float f) {
        this.mMap.put(str, f);
    }

    public void put(String str, Double d) {
        this.mMap.put(str, d);
    }

    public void put(String str, Boolean bool) {
        this.mMap.put(str, bool);
    }

    public void put(String str, byte[] bArr) {
        this.mMap.put(str, bArr);
    }

    public void putNull(String str) {
        this.mMap.put(str, null);
    }

    public void putObject(String str, Object obj) {
        if (obj == null) {
            putNull(str);
            return;
        }
        if (obj instanceof String) {
            put(str, (String) obj);
            return;
        }
        if (obj instanceof Byte) {
            put(str, (Byte) obj);
            return;
        }
        if (obj instanceof Short) {
            put(str, (Short) obj);
            return;
        }
        if (obj instanceof Integer) {
            put(str, (Integer) obj);
            return;
        }
        if (obj instanceof Long) {
            put(str, (Long) obj);
            return;
        }
        if (obj instanceof Float) {
            put(str, (Float) obj);
            return;
        }
        if (obj instanceof Double) {
            put(str, (Double) obj);
            return;
        }
        if (obj instanceof Boolean) {
            put(str, (Boolean) obj);
        } else if (obj instanceof byte[]) {
            put(str, (byte[]) obj);
        } else {
            throw new IllegalArgumentException("Unsupported type " + obj.getClass());
        }
    }

    public int size() {
        return this.mMap.size();
    }

    public boolean isEmpty() {
        return this.mMap.isEmpty();
    }

    public void remove(String str) {
        this.mMap.remove(str);
    }

    public void clear() {
        this.mMap.clear();
    }

    public boolean containsKey(String str) {
        return this.mMap.containsKey(str);
    }

    public Object get(String str) {
        return this.mMap.get(str);
    }

    public String getAsString(String str) {
        Object obj = this.mMap.get(str);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public Long getAsLong(String str) {
        Object obj = this.mMap.get(str);
        if (obj != null) {
            try {
                return Long.valueOf(((Number) obj).longValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Long.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e(TAG, "Cannot parse Long value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e(TAG, "Cannot cast value for " + str + " to a Long: " + obj, e);
            }
        }
        return null;
    }

    public Integer getAsInteger(String str) {
        Object obj = this.mMap.get(str);
        if (obj != null) {
            try {
                return Integer.valueOf(((Number) obj).intValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Integer.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e(TAG, "Cannot parse Integer value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e(TAG, "Cannot cast value for " + str + " to a Integer: " + obj, e);
            }
        }
        return null;
    }

    public Short getAsShort(String str) {
        Object obj = this.mMap.get(str);
        if (obj != null) {
            try {
                return Short.valueOf(((Number) obj).shortValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Short.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e(TAG, "Cannot parse Short value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e(TAG, "Cannot cast value for " + str + " to a Short: " + obj, e);
            }
        }
        return null;
    }

    public Byte getAsByte(String str) {
        Object obj = this.mMap.get(str);
        if (obj != null) {
            try {
                return Byte.valueOf(((Number) obj).byteValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Byte.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e(TAG, "Cannot parse Byte value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e(TAG, "Cannot cast value for " + str + " to a Byte: " + obj, e);
            }
        }
        return null;
    }

    public Double getAsDouble(String str) {
        Object obj = this.mMap.get(str);
        if (obj != null) {
            try {
                return Double.valueOf(((Number) obj).doubleValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Double.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e(TAG, "Cannot parse Double value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e(TAG, "Cannot cast value for " + str + " to a Double: " + obj, e);
            }
        }
        return null;
    }

    public Float getAsFloat(String str) {
        Object obj = this.mMap.get(str);
        if (obj != null) {
            try {
                return Float.valueOf(((Number) obj).floatValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Float.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e(TAG, "Cannot parse Float value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e(TAG, "Cannot cast value for " + str + " to a Float: " + obj, e);
            }
        }
        return null;
    }

    public Boolean getAsBoolean(String str) {
        Object obj = this.mMap.get(str);
        try {
            return (Boolean) obj;
        } catch (ClassCastException e) {
            if (obj instanceof CharSequence) {
                return Boolean.valueOf(Boolean.valueOf(obj.toString()).booleanValue() || "1".equals(obj));
            }
            if (obj instanceof Number) {
                return Boolean.valueOf(((Number) obj).intValue() != 0);
            }
            Log.e(TAG, "Cannot cast value for " + str + " to a Boolean: " + obj, e);
            return null;
        }
    }

    public byte[] getAsByteArray(String str) {
        Object obj = this.mMap.get(str);
        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }
        return null;
    }

    public Set<Map.Entry<String, Object>> valueSet() {
        return this.mMap.entrySet();
    }

    public Set<String> keySet() {
        return this.mMap.keySet();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMap.size());
        parcel.writeArrayMap(this.mMap);
    }

    @Deprecated
    public void putStringArrayList(String str, ArrayList<String> arrayList) {
        this.mMap.put(str, arrayList);
    }

    @Deprecated
    public ArrayList<String> getStringArrayList(String str) {
        return (ArrayList) this.mMap.get(str);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : this.mMap.keySet()) {
            String asString = getAsString(str);
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(str + "=" + asString);
        }
        return sb.toString();
    }

    public static boolean isSupportedValue(Object obj) {
        return obj == null || (obj instanceof String) || (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof Boolean) || (obj instanceof byte[]);
    }
}
