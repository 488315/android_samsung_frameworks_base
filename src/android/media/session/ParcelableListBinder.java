package android.media.session;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class ParcelableListBinder<T extends Parcelable> extends Binder {
    private static final int END_OF_PARCEL = 0;
    private static final int ITEM_CONTINUED = 1;
    private static final int SUGGESTED_MAX_IPC_SIZE = IBinder.getSuggestedMaxIpcSizeBytes();
    private boolean mConsumed;
    private final Consumer<List<T>> mConsumer;
    private int mCount;
    private final Class<T> mListElementsClass;
    private final Object mLock = new Object();
    private final List<T> mList = new ArrayList();

    public ParcelableListBinder(Class<T> cls, Consumer<List<T>> consumer) {
        this.mListElementsClass = cls;
        this.mConsumer = consumer;
    }

    @Override // android.os.Binder
    protected boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        List<T> list;
        if (i != 1) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        synchronized (this.mLock) {
            if (this.mConsumed) {
                return false;
            }
            int size = this.mList.size();
            if (size == 0) {
                this.mCount = parcel.readInt();
            }
            while (true) {
                list = null;
                if (size >= this.mCount || parcel.readInt() == 0) {
                    break;
                }
                Parcelable parcelable = parcel.readParcelable(null);
                if (this.mListElementsClass.isAssignableFrom(parcelable.getClass())) {
                    this.mList.add(parcelable);
                }
                size++;
            }
            if (size >= this.mCount) {
                list = this.mList;
                this.mConsumed = true;
            }
            if (list != null) {
                this.mConsumer.accept(list);
            }
            return true;
        }
    }

    public static <T extends Parcelable> void send(IBinder iBinder, List<T> list) throws RemoteException {
        int size = list.size();
        int i = 0;
        do {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            if (i == 0) {
                parcelObtain.writeInt(size);
            }
            while (i < size && parcelObtain.dataSize() < SUGGESTED_MAX_IPC_SIZE) {
                parcelObtain.writeInt(1);
                parcelObtain.writeParcelable(list.get(i), 0);
                i++;
            }
            if (i < size) {
                parcelObtain.writeInt(0);
            }
            iBinder.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.recycle();
            parcelObtain.recycle();
        } while (i < size);
    }
}
