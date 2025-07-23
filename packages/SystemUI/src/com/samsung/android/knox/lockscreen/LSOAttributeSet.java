package com.samsung.android.knox.lockscreen;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LSOAttributeSet implements Parcelable {
    public static final Parcelable.Creator<LSOAttributeSet> CREATOR = new Parcelable.Creator<LSOAttributeSet>() { // from class: com.samsung.android.knox.lockscreen.LSOAttributeSet.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LSOAttributeSet createFromParcel(Parcel parcel) {
            return LSOAttributeSet.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LSOAttributeSet[] newArray(int i) {
            return new LSOAttributeSet[i];
        }
    };
    public static final String TAG = "LSO";
    public HashMap<String, Object> mValues;

    public LSOAttributeSet() {
        this.mValues = new HashMap<>(8);
    }

    public static HashMap<String, Object> convertBundleToValues(Bundle bundle) {
        HashMap<String, Object> hashMap = new HashMap<>();
        for (String str : bundle.keySet()) {
            hashMap.put(str, bundle.get(str));
        }
        return hashMap;
    }

    public static LSOAttributeSet createFromParcel(Parcel parcel) {
        return new LSOAttributeSet(convertBundleToValues(parcel.readBundle()));
    }

    public static LSOAttributeSet fromByteArray(byte[] bArr) {
        try {
            return new LSOAttributeSet((HashMap<String, Object>) new ObjectInputStream(new ByteArrayInputStream(bArr)).readObject());
        } catch (IOException e) {
            Log.e("LSO", "IOException: " + e);
            return null;
        } catch (ClassNotFoundException e2) {
            Log.e("LSO", "ClassNotFound: " + e2);
            return null;
        }
    }

    public void clear() {
        this.mValues.clear();
    }

    public boolean containsKey(String str) {
        return this.mValues.containsKey(str);
    }

    public final Bundle convertValuesToBundle(Map<String, Object> map) {
        Bundle bundle = new Bundle();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                bundle.putString(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Byte) {
                bundle.putByte(entry.getKey(), ((Byte) entry.getValue()).byteValue());
            } else if (entry.getValue() instanceof Short) {
                bundle.putShort(entry.getKey(), ((Short) entry.getValue()).shortValue());
            } else if (entry.getValue() instanceof Integer) {
                bundle.putInt(entry.getKey(), ((Integer) entry.getValue()).intValue());
            } else if (entry.getValue() instanceof Long) {
                bundle.putLong(entry.getKey(), ((Long) entry.getValue()).longValue());
            } else if (entry.getValue() instanceof Double) {
                bundle.putDouble(entry.getKey(), ((Double) entry.getValue()).doubleValue());
            } else if (entry.getValue() instanceof Float) {
                bundle.putFloat(entry.getKey(), ((Float) entry.getValue()).floatValue());
            } else if (entry.getValue() instanceof Boolean) {
                bundle.putBoolean(entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
            } else {
                if (!(entry.getValue() instanceof byte[])) {
                    throw new UnsupportedOperationException("Error on convertValuesToBundle");
                }
                bundle.putByteArray(entry.getKey(), (byte[]) entry.getValue());
            }
        }
        return bundle;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj instanceof LSOAttributeSet) {
            return this.mValues.equals(((LSOAttributeSet) obj).mValues);
        }
        return false;
    }

    public Object get(String str) {
        return this.mValues.get(str);
    }

    public Boolean getAsBoolean(String str) {
        Object obj = this.mValues.get(str);
        try {
            return (Boolean) obj;
        } catch (ClassCastException e) {
            if (obj instanceof CharSequence) {
                return Boolean.valueOf(obj.toString());
            }
            if (obj instanceof Number) {
                return Boolean.valueOf(((Number) obj).intValue() != 0);
            }
            Log.e("LSO", "Cannot cast value for " + str + " to a Boolean: " + obj, e);
            return null;
        }
    }

    public Byte getAsByte(String str) {
        Object obj = this.mValues.get(str);
        if (obj != null) {
            try {
                return Byte.valueOf(((Number) obj).byteValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Byte.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e("LSO", "Cannot parse Byte value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e("LSO", "Cannot cast value for " + str + " to a Byte: " + obj, e);
            }
        }
        return null;
    }

    public byte[] getAsByteArray(String str) {
        Object obj = this.mValues.get(str);
        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }
        return null;
    }

    public Double getAsDouble(String str) {
        Object obj = this.mValues.get(str);
        if (obj != null) {
            try {
                return Double.valueOf(((Number) obj).doubleValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Double.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e("LSO", "Cannot parse Double value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e("LSO", "Cannot cast value for " + str + " to a Double: " + obj, e);
            }
        }
        return null;
    }

    public Float getAsFloat(String str) {
        Object obj = this.mValues.get(str);
        if (obj != null) {
            try {
                return Float.valueOf(((Number) obj).floatValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Float.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e("LSO", "Cannot parse Float value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e("LSO", "Cannot cast value for " + str + " to a Float: " + obj, e);
            }
        }
        return null;
    }

    public Integer getAsInteger(String str) {
        Object obj = this.mValues.get(str);
        if (obj != null) {
            try {
                return Integer.valueOf(((Number) obj).intValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Integer.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e("LSO", "Cannot parse Integer value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e("LSO", "Cannot cast value for " + str + " to a Integer: " + obj, e);
            }
        }
        return null;
    }

    public Long getAsLong(String str) {
        Object obj = this.mValues.get(str);
        if (obj != null) {
            try {
                return Long.valueOf(((Number) obj).longValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Long.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e("LSO", "Cannot parse Long value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e("LSO", "Cannot cast value for " + str + " to a Long: " + obj, e);
            }
        }
        return null;
    }

    public Short getAsShort(String str) {
        Object obj = this.mValues.get(str);
        if (obj != null) {
            try {
                return Short.valueOf(((Number) obj).shortValue());
            } catch (ClassCastException e) {
                if (obj instanceof CharSequence) {
                    try {
                        return Short.valueOf(obj.toString());
                    } catch (NumberFormatException unused) {
                        Log.e("LSO", "Cannot parse Short value for " + obj + " at key " + str);
                        return null;
                    }
                }
                Log.e("LSO", "Cannot cast value for " + str + " to a Short: " + obj, e);
            }
        }
        return null;
    }

    public String getAsString(String str) {
        Object obj = this.mValues.get(str);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public HashMap<String, Object> getMap() {
        return this.mValues;
    }

    public int hashCode() {
        return this.mValues.hashCode();
    }

    public Set<String> keySet() {
        return this.mValues.keySet();
    }

    public void put(String str, String str2) {
        this.mValues.put(str, str2);
    }

    public void putAll(LSOAttributeSet lSOAttributeSet) {
        this.mValues.putAll(lSOAttributeSet.mValues);
    }

    public void remove(String str) {
        this.mValues.remove(str);
    }

    public int size() {
        return this.mValues.size();
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                new ObjectOutputStream(byteArrayOutputStream).writeObject(this.mValues);
            } catch (IOException e) {
                e = e;
                byteArrayOutputStream2 = byteArrayOutputStream;
                Log.e("LSO", "Exception: " + e);
                byteArrayOutputStream = byteArrayOutputStream2;
                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e2) {
            e = e2;
        }
        return byteArrayOutputStream.toByteArray();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : this.mValues.keySet()) {
            String asString = getAsString(str);
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(str + "=" + asString);
        }
        return sb.toString();
    }

    public Set<Map.Entry<String, Object>> valueSet() {
        return this.mValues.entrySet();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(convertValuesToBundle(this.mValues));
    }

    public void put(String str, Byte b) {
        this.mValues.put(str, b);
    }

    public LSOAttributeSet(int i) {
        this.mValues = new HashMap<>(i, 1.0f);
    }

    public void put(String str, Short sh) {
        this.mValues.put(str, sh);
    }

    public void put(String str, Integer num) {
        this.mValues.put(str, num);
    }

    public LSOAttributeSet(LSOAttributeSet lSOAttributeSet) {
        this.mValues = new HashMap<>(lSOAttributeSet.mValues);
    }

    public void put(String str, Long l) {
        this.mValues.put(str, l);
    }

    public void put(String str, Float f) {
        this.mValues.put(str, f);
    }

    public LSOAttributeSet(HashMap<String, Object> hashMap) {
        this.mValues = hashMap;
    }

    public void put(String str, Double d) {
        this.mValues.put(str, d);
    }

    public void put(String str, Boolean bool) {
        this.mValues.put(str, bool);
    }

    public void put(String str, byte[] bArr) {
        this.mValues.put(str, bArr);
    }
}
