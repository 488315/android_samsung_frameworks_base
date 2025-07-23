package android.content.pm;

import android.os.BadParcelableException;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
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
        int readInt = parcel.readInt();
        this.mList = new ArrayList(readInt);
        if (readInt <= 0) {
            return;
        }
        Parcelable.Creator<?> readParcelableCreator = readParcelableCreator(parcel, classLoader);
        Class<?> cls = null;
        int i = 0;
        while (i < readInt && parcel.readInt() != 0) {
            cls = readVerifyAndAddElement(readParcelableCreator, parcel, classLoader, cls);
            i++;
        }
        if (i >= readInt) {
            return;
        }
        IBinder readStrongBinder = parcel.readStrongBinder();
        while (i < readInt) {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            obtain.writeInt(i);
            try {
                try {
                    readStrongBinder.transact(1, obtain, obtain2, 0);
                    if (!PMRune.PM_WA_PARCELED_LIST) {
                        obtain2.readException();
                    }
                    while (i < readInt && obtain2.readInt() != 0) {
                        cls = readVerifyAndAddElement(readParcelableCreator, obtain2, classLoader, cls);
                        i++;
                    }
                } catch (RemoteException e) {
                    throw new BadParcelableException("Failure retrieving array; only received " + i + " of " + readInt, e);
                }
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }

    private Class<?> readVerifyAndAddElement(Parcelable.Creator<?> creator, Parcel parcel, ClassLoader classLoader, Class<?> cls) {
        T readCreator = readCreator(creator, parcel, classLoader);
        if (cls == null) {
            cls = readCreator.getClass();
        } else {
            verifySameType(cls, readCreator.getClass());
        }
        this.mList.add(readCreator);
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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004d, code lost:
    
        r8.writeInt(0);
        r8.writeStrongBinder(new android.content.pm.BaseParceledListSlice.AnonymousClass1(r7));
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0058, code lost:
    
        return;
     */
    @Override // android.os.Parcelable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeToParcel(android.os.Parcel r8, final int r9) {
        /*
            r7 = this;
            boolean r0 = r7.mHasBeenParceled
            if (r0 != 0) goto L59
            r0 = 1
            r7.mHasBeenParceled = r0
            java.util.List<T> r1 = r7.mList
            int r1 = r1.size()
            r8.writeInt(r1)
            if (r1 <= 0) goto L58
            java.util.List<T> r2 = r7.mList
            r3 = 0
            java.lang.Object r2 = r2.get(r3)
            java.lang.Class r2 = r2.getClass()
            java.util.List<T> r4 = r7.mList
            java.lang.Object r4 = r4.get(r3)
            r7.writeParcelableCreator(r4, r8)
            r4 = r3
        L27:
            if (r4 >= r1) goto L4b
            int r5 = r7.mInlineCountLimit
            if (r4 >= r5) goto L4b
            int r5 = r8.dataSize()
            int r6 = android.content.pm.BaseParceledListSlice.MAX_IPC_SIZE
            if (r5 >= r6) goto L4b
            r8.writeInt(r0)
            java.util.List<T> r5 = r7.mList
            java.lang.Object r5 = r5.get(r4)
            java.lang.Class r6 = r5.getClass()
            verifySameType(r2, r6)
            r7.writeElement(r5, r8, r9)
            int r4 = r4 + 1
            goto L27
        L4b:
            if (r4 >= r1) goto L58
            r8.writeInt(r3)
            android.content.pm.BaseParceledListSlice$1 r0 = new android.content.pm.BaseParceledListSlice$1
            r0.<init>()
            r8.writeStrongBinder(r0)
        L58:
            return
        L59:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "Can't Parcel a ParceledListSlice more than once"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.BaseParceledListSlice.writeToParcel(android.os.Parcel, int):void");
    }
}
