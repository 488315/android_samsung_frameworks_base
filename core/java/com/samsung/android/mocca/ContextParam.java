package com.samsung.android.mocca;

import android.hardware.scontext.SContextConstants;
import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Set;
import java.util.function.BiConsumer;

/* loaded from: classes5.dex */
public final class ContextParam implements Serializable, Parcelable {
    public static final Parcelable.Creator<ContextParam> CREATOR = new Parcelable.Creator<ContextParam>() { // from class: com.samsung.android.mocca.ContextParam.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextParam createFromParcel(Parcel in) {
            return new ContextParam(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextParam[] newArray(int size) {
            return new ContextParam[size];
        }
    };
    private static final String TAG = "ContextParam";
    private static final long serialVersionUID = -3026284218292166456L;
    private final ArrayMap<String, Object> mParams = new ArrayMap<>();

    public ContextParam() {
    }

    public ContextParam(Bundle param) {
        Set<String> keys = param.keySet();
        for (String key : keys) {
            Object value = param.get(key);
            this.mParams.put(key, value);
        }
    }

    protected ContextParam(Parcel in) {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            this.mParams.put(in.readString(), in.readValue(null));
        }
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(final Parcel dest, int flags) {
        dest.writeInt(this.mParams.size());
        this.mParams.forEach(new BiConsumer() { // from class: com.samsung.android.mocca.ContextParam$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ContextParam.lambda$writeToParcel$0(Parcel.this, (String) obj, obj2);
            }
        });
    }

    static /* synthetic */ void lambda$writeToParcel$0(Parcel dest, String k, Object v) {
        dest.writeString(k);
        dest.writeValue(v);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isEmpty() {
        return this.mParams.isEmpty();
    }

    public boolean containsKey(String key) {
        return this.mParams.containsKey(key);
    }

    public void putByte(String key, byte value) {
        this.mParams.put(key, Byte.valueOf(value));
    }

    public byte getByte(String key) {
        try {
            return ((Byte) this.mParams.get(key)).byteValue();
        } catch (ClassCastException | NullPointerException e) {
            return (byte) 0;
        }
    }

    public void putChar(String key, char value) {
        this.mParams.put(key, Character.valueOf(value));
    }

    public char getChar(String key) {
        try {
            return ((Character) this.mParams.get(key)).charValue();
        } catch (ClassCastException | NullPointerException e) {
            return (char) 0;
        }
    }

    public void putShort(String key, short value) {
        this.mParams.put(key, Short.valueOf(value));
    }

    public short getShort(String key) {
        try {
            return ((Short) this.mParams.get(key)).shortValue();
        } catch (ClassCastException | NullPointerException e) {
            return (short) 0;
        }
    }

    public void putInt(String key, int value) {
        this.mParams.put(key, Integer.valueOf(value));
    }

    public int getInt(String key) {
        try {
            return ((Integer) this.mParams.get(key)).intValue();
        } catch (ClassCastException | NullPointerException e) {
            return 0;
        }
    }

    public void putLong(String key, long value) {
        this.mParams.put(key, Long.valueOf(value));
    }

    public long getLong(String key) {
        try {
            return ((Long) this.mParams.get(key)).longValue();
        } catch (ClassCastException | NullPointerException e) {
            return 0L;
        }
    }

    public void putFloat(String key, float value) {
        this.mParams.put(key, Float.valueOf(value));
    }

    public float getFloat(String key) {
        try {
            return ((Float) this.mParams.get(key)).floatValue();
        } catch (ClassCastException | NullPointerException e) {
            return 0.0f;
        }
    }

    public void putDouble(String key, double value) {
        this.mParams.put(key, Double.valueOf(value));
    }

    public double getDouble(String key) {
        try {
            return ((Double) this.mParams.get(key)).doubleValue();
        } catch (ClassCastException | NullPointerException e) {
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
    }

    public void putString(String key, String value) {
        this.mParams.put(key, value);
    }

    public String getString(String key) {
        try {
            return (String) this.mParams.get(key);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public void putByteArray(String key, byte[] value) {
        try {
            ByteArrayInputStream valueIn = new ByteArrayInputStream(value);
            try {
                ObjectInputStream ois = new ObjectInputStream(valueIn);
                try {
                    Object obj = ois.readObject();
                    this.mParams.put(key, obj);
                    ois.close();
                    valueIn.close();
                } finally {
                }
            } catch (Throwable th) {
                try {
                    valueIn.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | ClassNotFoundException e) {
            Log.m97e(TAG, "putByteArray - " + e.getMessage(), e);
        }
    }

    public byte[] getByteArray(String key) {
        Object obj = this.mParams.get(key);
        if (obj == null) {
            return new byte[0];
        }
        try {
            ByteArrayOutputStream valueOut = new ByteArrayOutputStream();
            try {
                ObjectOutputStream os = new ObjectOutputStream(valueOut);
                try {
                    os.writeObject(obj);
                    byte[] byteArray = valueOut.toByteArray();
                    os.close();
                    valueOut.close();
                    return byteArray;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            Log.m97e(TAG, "getByteArray - " + e.getMessage(), e);
            return null;
        }
    }
}
