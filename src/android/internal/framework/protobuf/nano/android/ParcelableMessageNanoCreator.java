package android.internal.framework.protobuf.nano.android;

import android.internal.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import android.internal.framework.protobuf.nano.MessageNano;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public final class ParcelableMessageNanoCreator<T extends MessageNano> implements Parcelable.Creator<T> {
    private static final String TAG = "PMNCreator";
    private final Class<T> mClazz;

    public ParcelableMessageNanoCreator(Class<T> cls) {
        this.mClazz = cls;
    }

    @Override // android.os.Parcelable.Creator
    public T createFromParcel(Parcel parcel) {
        String string = parcel.readString();
        byte[] bArrCreateByteArray = parcel.createByteArray();
        T t = null;
        try {
            Class<? extends U> clsAsSubclass = Class.forName(string, false, getClass().getClassLoader()).asSubclass(MessageNano.class);
            Class[] clsArr = new Class[0];
            T t2 = (T) clsAsSubclass.getConstructor(null).newInstance(null);
            try {
                MessageNano.mergeFrom(t2, bArrCreateByteArray);
                return t2;
            } catch (InvalidProtocolBufferNanoException e) {
                e = e;
                t = t2;
                Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            } catch (ClassNotFoundException e2) {
                e = e2;
                t = t2;
                Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            } catch (IllegalAccessException e3) {
                e = e3;
                t = t2;
                Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            } catch (InstantiationException e4) {
                e = e4;
                t = t2;
                Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            } catch (NoSuchMethodException e5) {
                e = e5;
                t = t2;
                Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            } catch (InvocationTargetException e6) {
                e = e6;
                t = t2;
                Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            }
        } catch (InvalidProtocolBufferNanoException e7) {
            e = e7;
        } catch (ClassNotFoundException e8) {
            e = e8;
        } catch (IllegalAccessException e9) {
            e = e9;
        } catch (InstantiationException e10) {
            e = e10;
        } catch (NoSuchMethodException e11) {
            e = e11;
        } catch (InvocationTargetException e12) {
            e = e12;
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
