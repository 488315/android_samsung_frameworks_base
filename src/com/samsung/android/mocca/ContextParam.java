package com.samsung.android.mocca;

import android.hardware.scontext.SContextConstants;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes6.dex */
public final class ContextParam implements Parcelable {
    public static final Parcelable.Creator<ContextParam> CREATOR = new Parcelable.Creator<ContextParam>() { // from class: com.samsung.android.mocca.ContextParam.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextParam createFromParcel(Parcel parcel) {
            return new ContextParam(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextParam[] newArray(int i) {
            return new ContextParam[i];
        }
    };
    private static final String TAG = "ContextParam";
    private final ArrayMap<String, Object> mParams = new ArrayMap<>();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContextParam() {
    }

    public ContextParam(Bundle bundle) {
        for (String str : bundle.keySet()) {
            this.mParams.put(str, bundle.get(str));
        }
    }

    protected ContextParam(Parcel parcel) {
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.mParams.put(parcel.readString(), parcel.readValue(null));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(final Parcel parcel, int i) {
        parcel.writeInt(this.mParams.size());
        this.mParams.forEach(new BiConsumer() { // from class: com.samsung.android.mocca.ContextParam$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws IOException {
                ContextParam.lambda$writeToParcel$0(parcel, (String) obj, obj2);
            }
        });
    }

    static /* synthetic */ void lambda$writeToParcel$0(Parcel parcel, String str, Object obj) throws IOException {
        parcel.writeString(str);
        parcel.writeValue(obj);
    }

    public boolean isEmpty() {
        return this.mParams.isEmpty();
    }

    public boolean containsKey(String str) {
        return this.mParams.containsKey(str);
    }

    public void putByte(String str, byte b) {
        this.mParams.put(str, Byte.valueOf(b));
    }

    public byte getByte(String str) {
        try {
            return ((Byte) this.mParams.get(str)).byteValue();
        } catch (ClassCastException | NullPointerException unused) {
            return (byte) 0;
        }
    }

    public void putChar(String str, char c) {
        this.mParams.put(str, Character.valueOf(c));
    }

    public char getChar(String str) {
        try {
            return ((Character) this.mParams.get(str)).charValue();
        } catch (ClassCastException | NullPointerException unused) {
            return (char) 0;
        }
    }

    public void putShort(String str, short s) {
        this.mParams.put(str, Short.valueOf(s));
    }

    public short getShort(String str) {
        try {
            return ((Short) this.mParams.get(str)).shortValue();
        } catch (ClassCastException | NullPointerException unused) {
            return (short) 0;
        }
    }

    public void putInt(String str, int i) {
        this.mParams.put(str, Integer.valueOf(i));
    }

    public int getInt(String str) {
        try {
            return ((Integer) this.mParams.get(str)).intValue();
        } catch (ClassCastException | NullPointerException unused) {
            return 0;
        }
    }

    public void putLong(String str, long j) {
        this.mParams.put(str, Long.valueOf(j));
    }

    public long getLong(String str) {
        try {
            return ((Long) this.mParams.get(str)).longValue();
        } catch (ClassCastException | NullPointerException unused) {
            return 0L;
        }
    }

    public void putFloat(String str, float f) {
        this.mParams.put(str, Float.valueOf(f));
    }

    public float getFloat(String str) {
        try {
            return ((Float) this.mParams.get(str)).floatValue();
        } catch (ClassCastException | NullPointerException unused) {
            return 0.0f;
        }
    }

    public void putDouble(String str, double d) {
        this.mParams.put(str, Double.valueOf(d));
    }

    public double getDouble(String str) {
        try {
            return ((Double) this.mParams.get(str)).doubleValue();
        } catch (ClassCastException | NullPointerException unused) {
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
    }

    public void putString(String str, String str2) {
        this.mParams.put(str, str2);
    }

    public String getString(String str) {
        try {
            return (String) this.mParams.get(str);
        } catch (ClassCastException unused) {
            return null;
        }
    }

    public void putByteArray(String str, byte[] bArr) {
        this.mParams.put(str, bArr);
    }

    public byte[] getByteArray(String str) {
        try {
            return (byte[]) this.mParams.get(str);
        } catch (ClassCastException unused) {
            return new byte[0];
        }
    }

    public void putIntArray(String str, int[] iArr) {
        this.mParams.put(str, iArr);
    }

    public int[] getIntArray(String str) {
        try {
            return (int[]) this.mParams.get(str);
        } catch (ClassCastException unused) {
            return new int[0];
        }
    }

    public Map<String, Object> getAsMap() {
        return Collections.unmodifiableMap(this.mParams);
    }
}
