package com.android.framework.protobuf.nano.android;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes5.dex */
public final class ParcelableMessageNanoCreator<T extends MessageNano> implements Parcelable.Creator<T> {
    private static final String TAG = "PMNCreator";
    private final Class<T> mClazz;

    public ParcelableMessageNanoCreator(Class<T> cls) {
        this.mClazz = cls;
    }

    @Override // android.os.Parcelable.Creator
    public T createFromParcel(Parcel parcel) {
        T t;
        String readString = parcel.readString();
        byte[] createByteArray = parcel.createByteArray();
        T t2 = null;
        try {
            Class<? extends U> asSubclass = Class.forName(readString, false, getClass().getClassLoader()).asSubclass(MessageNano.class);
            Class[] clsArr = new Class[0];
            t = (T) asSubclass.getConstructor(null).newInstance(null);
        } catch (InvalidProtocolBufferNanoException e) {
            e = e;
        } catch (ClassNotFoundException e2) {
            e = e2;
        } catch (IllegalAccessException e3) {
            e = e3;
        } catch (InstantiationException e4) {
            e = e4;
        } catch (NoSuchMethodException e5) {
            e = e5;
        } catch (InvocationTargetException e6) {
            e = e6;
        }
        try {
            MessageNano.mergeFrom(t, createByteArray);
            return t;
        } catch (InvalidProtocolBufferNanoException e7) {
            e = e7;
            t2 = t;
            Log.e(TAG, "Exception trying to create proto from parcel", e);
            return t2;
        } catch (ClassNotFoundException e8) {
            e = e8;
            t2 = t;
            Log.e(TAG, "Exception trying to create proto from parcel", e);
            return t2;
        } catch (IllegalAccessException e9) {
            e = e9;
            t2 = t;
            Log.e(TAG, "Exception trying to create proto from parcel", e);
            return t2;
        } catch (InstantiationException e10) {
            e = e10;
            t2 = t;
            Log.e(TAG, "Exception trying to create proto from parcel", e);
            return t2;
        } catch (NoSuchMethodException e11) {
            e = e11;
            t2 = t;
            Log.e(TAG, "Exception trying to create proto from parcel", e);
            return t2;
        } catch (InvocationTargetException e12) {
            e = e12;
            t2 = t;
            Log.e(TAG, "Exception trying to create proto from parcel", e);
            return t2;
        }
    }

    @Override // android.os.Parcelable.Creator
    public T[] newArray(int i) {
        return (T[]) ((MessageNano[]) Array.newInstance((Class<?>) this.mClazz, i));
    }

    static <T extends MessageNano> void writeToParcel(Class<T> cls, MessageNano messageNano, Parcel parcel) {
        parcel.writeString(cls.getName());
        parcel.writeByteArray(MessageNano.toByteArray(messageNano));
    }
}
