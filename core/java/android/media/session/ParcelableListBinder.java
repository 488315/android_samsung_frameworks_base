package android.media.session;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
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

    public ParcelableListBinder(Class<T> listElementsClass, Consumer<List<T>> consumer) {
        this.mListElementsClass = listElementsClass;
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
            while (size < this.mCount && parcel.readInt() != 0) {
                Parcelable readParcelable = parcel.readParcelable(null);
                if (this.mListElementsClass.isAssignableFrom(readParcelable.getClass())) {
                    this.mList.add(readParcelable);
                }
                size++;
            }
            if (size >= this.mCount) {
                list = this.mList;
                this.mConsumed = true;
            } else {
                list = null;
            }
            if (list != null) {
                this.mConsumer.accept(list);
            }
            return true;
        }
    }

    public static <T extends Parcelable> void send(IBinder binder, List<T> list) throws RemoteException {
        int count = list.size();
        int i = 0;
        do {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            if (i == 0) {
                data.writeInt(count);
            }
            while (i < count && data.dataSize() < SUGGESTED_MAX_IPC_SIZE) {
                data.writeInt(1);
                data.writeParcelable(list.get(i), 0);
                i++;
            }
            if (i < count) {
                data.writeInt(0);
            }
            binder.transact(1, data, reply, 0);
            reply.recycle();
            data.recycle();
        } while (i < count);
    }
}
