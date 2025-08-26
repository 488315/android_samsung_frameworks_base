package android.content.pm;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.rune.PMRune;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
abstract class BaseParceledListSlice<T> implements Parcelable {
    private static final boolean DEBUG = false;
    private static final int MAX_IPC_SIZE;
    private static final String TAG = "ParceledListSlice";
    private static final int WARN_ELM_SIZE;
    private List<T> mList;
    private int mInlineCountLimit = Integer.MAX_VALUE;
    private boolean mHasBeenParceled = false;
    private int mStartIndexForWrite = -1;

    protected abstract Parcelable.Creator<?> readParcelableCreator(Parcel parcel, ClassLoader classLoader);

    protected abstract void writeElement(T t, Parcel parcel, int i);

    protected abstract void writeParcelableCreator(T t, Parcel parcel);

    static {
        int suggestedMaxIpcSizeBytes = IBinder.getSuggestedMaxIpcSizeBytes();
        MAX_IPC_SIZE = suggestedMaxIpcSizeBytes;
        WARN_ELM_SIZE = suggestedMaxIpcSizeBytes / 4;
    }

    public BaseParceledListSlice(List<T> list) {
        this.mList = list;
    }

    BaseParceledListSlice(Parcel parcel, ClassLoader classLoader) {
        int i = parcel.readInt();
        this.mList = new ArrayList(i);
        if (i <= 0) {
            return;
        }
        Parcelable.Creator<?> parcelableCreator = readParcelableCreator(parcel, classLoader);
        Class<?> verifyAndAddElement = null;
        int i2 = 0;
        while (i2 < i && parcel.readInt() != 0) {
            verifyAndAddElement = readVerifyAndAddElement(parcelableCreator, parcel, classLoader, verifyAndAddElement);
            i2++;
        }
        if (i2 >= i) {
            return;
        }
        IBinder strongBinder = parcel.readStrongBinder();
        while (i2 < i) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            parcelObtain.writeInt(i2);
            try {
                try {
                    strongBinder.transact(1, parcelObtain, parcelObtain2, 0);
                    if (!PMRune.PM_WA_PARCELED_LIST) {
                        parcelObtain2.readException();
                    }
                    while (i2 < i && parcelObtain2.readInt() != 0) {
                        verifyAndAddElement = readVerifyAndAddElement(parcelableCreator, parcelObtain2, classLoader, verifyAndAddElement);
                        i2++;
                    }
                } catch (RemoteException e) {
                    throw new BadParcelableException("Failure retrieving array; only received " + i2 + " of " + i, e);
                }
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }
    }

    private Class<?> readVerifyAndAddElement(Parcelable.Creator<?> creator, Parcel parcel, ClassLoader classLoader, Class<?> cls) {
        T creator2 = readCreator(creator, parcel, classLoader);
        if (cls == null) {
            cls = creator2.getClass();
        } else {
            verifySameType(cls, creator2.getClass());
        }
        this.mList.add(creator2);
        return cls;
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
        if (this.mHasBeenParceled) {
            throw new IllegalStateException("Can't Parcel a ParceledListSlice more than once");
        }
        this.mHasBeenParceled = true;
        final int size = this.mList.size();
        parcel.writeInt(size);
        if (size > 0) {
            final Class<?> cls = this.mList.get(0).getClass();
            writeParcelableCreator(this.mList.get(0), parcel);
            int i2 = 0;
            while (i2 < size && i2 < this.mInlineCountLimit && parcel.dataSize() < MAX_IPC_SIZE) {
                parcel.writeInt(1);
                T t = this.mList.get(i2);
                verifySameType(cls, t.getClass());
                writeElement(t, parcel, i);
                i2++;
            }
            if (i2 < size) {
                parcel.writeInt(0);
                parcel.writeStrongBinder(new Binder() { // from class: android.content.pm.BaseParceledListSlice.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // android.os.Binder
                    protected boolean onTransact(int i3, Parcel parcel2, Parcel parcel3, int i4) throws RemoteException {
                        if (i3 != 1) {
                            return super.onTransact(i3, parcel2, parcel3, i4);
                        }
                        if (BaseParceledListSlice.this.mList == null) {
                            throw new IllegalArgumentException("Attempt to transfer null list, did transfer finish?");
                        }
                        int i5 = parcel2.readInt();
                        try {
                            if (PMRune.PM_WA_PARCELED_LIST) {
                                if (BaseParceledListSlice.this.mStartIndexForWrite == i5) {
                                    throw new RuntimeException("Requested twice for the same index");
                                }
                                BaseParceledListSlice.this.mStartIndexForWrite = i5;
                            }
                            if (!PMRune.PM_WA_PARCELED_LIST) {
                                parcel3.writeNoException();
                            }
                            while (i5 < size && parcel3.dataSize() < 65536) {
                                parcel3.writeInt(1);
                                int iDataSize = parcel3.dataSize();
                                Object obj = BaseParceledListSlice.this.mList.get(i5);
                                BaseParceledListSlice.verifySameType(cls, obj.getClass());
                                BaseParceledListSlice.this.writeElement(obj, parcel3, i);
                                int iDataSize2 = parcel3.dataSize() - iDataSize;
                                if (iDataSize2 >= BaseParceledListSlice.WARN_ELM_SIZE) {
                                    Log.w(BaseParceledListSlice.TAG, "Element #" + i5 + " is " + iDataSize2 + " bytes.");
                                }
                                i5++;
                            }
                            if (i5 < size) {
                                parcel3.writeInt(0);
                            } else {
                                BaseParceledListSlice.this.mList = null;
                            }
                            if (parcel3.dataSize() >= BaseParceledListSlice.WARN_ELM_SIZE + 65536) {
                                Log.w(BaseParceledListSlice.TAG, "Overly large reply size: " + parcel3.dataSize());
                            }
                            return true;
                        } catch (RuntimeException e) {
                            BaseParceledListSlice.this.mList = null;
                            throw e;
                        }
                    }
                });
            }
        }
    }
}
