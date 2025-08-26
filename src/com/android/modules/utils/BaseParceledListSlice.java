package com.android.modules.utils;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
abstract class BaseParceledListSlice<T> implements Parcelable {
    private static boolean DEBUG = false;
    private static final int MAX_IPC_SIZE = IBinder.getSuggestedMaxIpcSizeBytes();
    private static String TAG = "ParceledListSlice";
    private int mInlineCountLimit = Integer.MAX_VALUE;
    private final List<T> mList;

    protected abstract Parcelable.Creator<?> readParcelableCreator(Parcel parcel, ClassLoader classLoader);

    protected abstract void writeElement(T t, Parcel parcel, int i);

    protected abstract void writeParcelableCreator(T t, Parcel parcel);

    public BaseParceledListSlice(List<T> list) {
        this.mList = list;
    }

    BaseParceledListSlice(Parcel parcel, ClassLoader classLoader) {
        int i = parcel.readInt();
        this.mList = new ArrayList(i);
        if (DEBUG) {
            Log.d(TAG, "Retrieving " + i + " items");
        }
        if (i <= 0) {
            return;
        }
        Parcelable.Creator<?> parcelableCreator = readParcelableCreator(parcel, classLoader);
        Class<?> cls = null;
        int i2 = 0;
        while (i2 < i && parcel.readInt() != 0) {
            T creator = readCreator(parcelableCreator, parcel, classLoader);
            if (cls == null) {
                cls = creator.getClass();
            } else {
                verifySameType(cls, creator.getClass());
            }
            this.mList.add(creator);
            if (DEBUG) {
                String str = TAG;
                StringBuilder sb = new StringBuilder("Read inline #");
                sb.append(i2);
                sb.append(": ");
                List<T> list = this.mList;
                sb.append(list.get(list.size() - 1));
                Log.d(str, sb.toString());
            }
            i2++;
        }
        if (i2 >= i) {
            return;
        }
        IBinder strongBinder = parcel.readStrongBinder();
        while (i2 < i) {
            if (DEBUG) {
                Log.d(TAG, "Reading more @" + i2 + " of " + i + ": retriever=" + strongBinder);
            }
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            parcelObtain.writeInt(i2);
            try {
                strongBinder.transact(1, parcelObtain, parcelObtain2, 0);
                while (i2 < i && parcelObtain2.readInt() != 0) {
                    T creator2 = readCreator(parcelableCreator, parcelObtain2, classLoader);
                    verifySameType(cls, creator2.getClass());
                    this.mList.add(creator2);
                    if (DEBUG) {
                        String str2 = TAG;
                        StringBuilder sb2 = new StringBuilder("Read extra #");
                        sb2.append(i2);
                        sb2.append(": ");
                        List<T> list2 = this.mList;
                        sb2.append(list2.get(list2.size() - 1));
                        Log.d(str2, sb2.toString());
                    }
                    i2++;
                }
                parcelObtain2.recycle();
                parcelObtain.recycle();
            } catch (RemoteException e) {
                Log.w(TAG, "Failure retrieving array; only received " + i2 + " of " + i, e);
                return;
            }
        }
    }

    private T readCreator(Parcelable.Creator<?> creator, Parcel parcel, ClassLoader classLoader) {
        if (creator instanceof Parcelable.ClassLoaderCreator) {
            return (T) ((Parcelable.ClassLoaderCreator) creator).createFromParcel(parcel, classLoader);
        }
        return (T) creator.createFromParcel(parcel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void verifySameType(Class<?> cls, Class<?> cls2) {
        if (cls2.equals(cls)) {
            return;
        }
        StringBuilder sb = new StringBuilder("Can't unparcel type ");
        sb.append(cls2 == null ? null : cls2.getName());
        sb.append(" in list of type ");
        sb.append(cls != null ? cls.getName() : null);
        throw new IllegalArgumentException(sb.toString());
    }

    public List<T> getList() {
        return this.mList;
    }

    public void setInlineCountLimit(int i) {
        this.mInlineCountLimit = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, final int i) {
        final int size = this.mList.size();
        parcel.writeInt(size);
        if (DEBUG) {
            Log.d(TAG, "Writing " + size + " items");
        }
        if (size > 0) {
            final Class<?> cls = this.mList.get(0).getClass();
            writeParcelableCreator(this.mList.get(0), parcel);
            int i2 = 0;
            while (i2 < size && i2 < this.mInlineCountLimit && parcel.dataSize() < MAX_IPC_SIZE) {
                parcel.writeInt(1);
                T t = this.mList.get(i2);
                verifySameType(cls, t.getClass());
                writeElement(t, parcel, i);
                if (DEBUG) {
                    Log.d(TAG, "Wrote inline #" + i2 + ": " + this.mList.get(i2));
                }
                i2++;
            }
            if (i2 < size) {
                parcel.writeInt(0);
                Binder binder = new Binder() { // from class: com.android.modules.utils.BaseParceledListSlice.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // android.os.Binder
                    protected boolean onTransact(int i3, Parcel parcel2, Parcel parcel3, int i4) throws RemoteException {
                        if (i3 != 1) {
                            return super.onTransact(i3, parcel2, parcel3, i4);
                        }
                        int i5 = parcel2.readInt();
                        if (BaseParceledListSlice.DEBUG) {
                            Log.d(BaseParceledListSlice.TAG, "Writing more @" + i5 + " of " + size);
                        }
                        while (i5 < size && parcel3.dataSize() < BaseParceledListSlice.MAX_IPC_SIZE) {
                            parcel3.writeInt(1);
                            Object obj = BaseParceledListSlice.this.mList.get(i5);
                            BaseParceledListSlice.verifySameType(cls, obj.getClass());
                            BaseParceledListSlice.this.writeElement(obj, parcel3, i);
                            if (BaseParceledListSlice.DEBUG) {
                                Log.d(BaseParceledListSlice.TAG, "Wrote extra #" + i5 + ": " + BaseParceledListSlice.this.mList.get(i5));
                            }
                            i5++;
                        }
                        if (i5 < size) {
                            if (BaseParceledListSlice.DEBUG) {
                                Log.d(BaseParceledListSlice.TAG, "Breaking @" + i5 + " of " + size);
                            }
                            parcel3.writeInt(0);
                        }
                        return true;
                    }
                };
                if (DEBUG) {
                    Log.d(TAG, "Breaking @" + i2 + " of " + size + ": retriever=" + binder);
                }
                parcel.writeStrongBinder(binder);
            }
        }
    }
}
