package android.content.pm;

import android.os.BadParcelableException;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import com.samsung.android.rune.PMRune;

import java.util.ArrayList;
import java.util.List;

abstract class BaseParceledListSlice<T> implements Parcelable {
    private static final boolean DEBUG = false;
    private static final String TAG = "ParceledListSlice";
    private List<T> mList;
    private static final int MAX_IPC_SIZE = IBinder.getSuggestedMaxIpcSizeBytes();
    private static final int WARN_ELM_SIZE = MAX_IPC_SIZE / 4;
    private int mInlineCountLimit = Integer.MAX_VALUE;
    private boolean mHasBeenParceled = false;
    private int mStartIndexForWrite = -1;

    protected abstract Parcelable.Creator<?> readParcelableCreator(
            Parcel parcel, ClassLoader classLoader);

    protected abstract void writeElement(T t, Parcel parcel, int i);

    protected abstract void writeParcelableCreator(T t, Parcel parcel);

    public BaseParceledListSlice(List<T> list) {
        this.mList = list;
    }

    BaseParceledListSlice(Parcel p, ClassLoader loader) {
        int N = p.readInt();
        this.mList = new ArrayList(N);
        if (N <= 0) {
            return;
        }
        Parcelable.Creator<?> creator = readParcelableCreator(p, loader);
        Class<?> listElementClass = null;
        int i = 0;
        while (i < N && p.readInt() != 0) {
            listElementClass = readVerifyAndAddElement(creator, p, loader, listElementClass);
            i++;
        }
        if (i >= N) {
            return;
        }
        IBinder retriever = p.readStrongBinder();
        while (i < N) {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeInt(i);
            try {
                try {
                    retriever.transact(1, data, reply, 0);
                    if (!PMRune.PM_WA_PARCELED_LIST) {
                        reply.readException();
                    }
                    while (i < N && reply.readInt() != 0) {
                        listElementClass =
                                readVerifyAndAddElement(creator, reply, loader, listElementClass);
                        i++;
                    }
                } catch (RemoteException e) {
                    throw new BadParcelableException(
                            "Failure retrieving array; only received " + i + " of " + N, e);
                }
            } finally {
                reply.recycle();
                data.recycle();
            }
        }
    }

    private Class<?> readVerifyAndAddElement(
            Parcelable.Creator<?> creator,
            Parcel p,
            ClassLoader loader,
            Class<?> listElementClass) {
        T parcelable = readCreator(creator, p, loader);
        if (listElementClass == null) {
            listElementClass = parcelable.getClass();
        } else {
            verifySameType(listElementClass, parcelable.getClass());
        }
        this.mList.add(parcelable);
        return listElementClass;
    }

    private T readCreator(Parcelable.Creator<?> creator, Parcel parcel, ClassLoader classLoader) {
        if (creator instanceof Parcelable.ClassLoaderCreator) {
            return (T)
                    ((Parcelable.ClassLoaderCreator) creator).createFromParcel(parcel, classLoader);
        }
        return (T) creator.createFromParcel(parcel);
    }

    public static void verifySameType(Class<?> expected, Class<?> actual) {
        if (!actual.equals(expected)) {
            throw new IllegalArgumentException(
                    "Can't unparcel type "
                            + (actual == null ? null : actual.getName())
                            + " in list of type "
                            + (expected != null ? expected.getName() : null));
        }
    }

    public List<T> getList() {
        return this.mList;
    }

    public void setInlineCountLimit(int maxCount) {
        this.mInlineCountLimit = maxCount;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004f, code lost:

       r9.writeInt(0);
       r0 = new android.content.pm.BaseParceledListSlice.AnonymousClass1(r8);
       r9.writeStrongBinder(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005a, code lost:

       return;
    */
    @Override // android.os.Parcelable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeToParcel(android.os.Parcel r9, final int r10) {
        /*
            r8 = this;
            boolean r0 = r8.mHasBeenParceled
            if (r0 != 0) goto L5b
            r0 = 1
            r8.mHasBeenParceled = r0
            java.util.List<T> r1 = r8.mList
            int r1 = r1.size()
            r2 = r10
            r9.writeInt(r1)
            if (r1 <= 0) goto L5a
            java.util.List<T> r3 = r8.mList
            r4 = 0
            java.lang.Object r3 = r3.get(r4)
            java.lang.Class r3 = r3.getClass()
            java.util.List<T> r5 = r8.mList
            java.lang.Object r5 = r5.get(r4)
            r8.writeParcelableCreator(r5, r9)
            r5 = 0
        L28:
            if (r5 >= r1) goto L4d
            int r6 = r8.mInlineCountLimit
            if (r5 >= r6) goto L4d
            int r6 = r9.dataSize()
            int r7 = android.content.pm.BaseParceledListSlice.MAX_IPC_SIZE
            if (r6 >= r7) goto L4d
            r9.writeInt(r0)
            java.util.List<T> r6 = r8.mList
            java.lang.Object r6 = r6.get(r5)
            java.lang.Class r7 = r6.getClass()
            verifySameType(r3, r7)
            r8.writeElement(r6, r9, r2)
            int r5 = r5 + 1
            goto L28
        L4d:
            if (r5 >= r1) goto L5a
            r9.writeInt(r4)
            android.content.pm.BaseParceledListSlice$1 r0 = new android.content.pm.BaseParceledListSlice$1
            r0.<init>()
            r9.writeStrongBinder(r0)
        L5a:
            return
        L5b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Can't Parcel a ParceledListSlice more than once"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.content.pm.BaseParceledListSlice.writeToParcel(android.os.Parcel,"
                    + " int):void");
    }
}
