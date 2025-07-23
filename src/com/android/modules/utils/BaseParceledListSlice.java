package com.android.modules.utils;

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
        int readInt = parcel.readInt();
        this.mList = new ArrayList(readInt);
        if (DEBUG) {
            Log.d(TAG, "Retrieving " + readInt + " items");
        }
        if (readInt <= 0) {
            return;
        }
        Parcelable.Creator<?> readParcelableCreator = readParcelableCreator(parcel, classLoader);
        Class<?> cls = null;
        int i = 0;
        while (i < readInt && parcel.readInt() != 0) {
            T readCreator = readCreator(readParcelableCreator, parcel, classLoader);
            if (cls == null) {
                cls = readCreator.getClass();
            } else {
                verifySameType(cls, readCreator.getClass());
            }
            this.mList.add(readCreator);
            if (DEBUG) {
                String str = TAG;
                StringBuilder sb = new StringBuilder("Read inline #");
                sb.append(i);
                sb.append(": ");
                List<T> list = this.mList;
                sb.append(list.get(list.size() - 1));
                Log.d(str, sb.toString());
            }
            i++;
        }
        if (i >= readInt) {
            return;
        }
        IBinder readStrongBinder = parcel.readStrongBinder();
        while (i < readInt) {
            if (DEBUG) {
                Log.d(TAG, "Reading more @" + i + " of " + readInt + ": retriever=" + readStrongBinder);
            }
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            obtain.writeInt(i);
            try {
                readStrongBinder.transact(1, obtain, obtain2, 0);
                while (i < readInt && obtain2.readInt() != 0) {
                    T readCreator2 = readCreator(readParcelableCreator, obtain2, classLoader);
                    verifySameType(cls, readCreator2.getClass());
                    this.mList.add(readCreator2);
                    if (DEBUG) {
                        String str2 = TAG;
                        StringBuilder sb2 = new StringBuilder("Read extra #");
                        sb2.append(i);
                        sb2.append(": ");
                        List<T> list2 = this.mList;
                        sb2.append(list2.get(list2.size() - 1));
                        Log.d(str2, sb2.toString());
                    }
                    i++;
                }
                obtain2.recycle();
                obtain.recycle();
            } catch (RemoteException e) {
                Log.w(TAG, "Failure retrieving array; only received " + i + " of " + readInt, e);
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

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0088, code lost:
    
        r8.writeInt(0);
        r2 = new com.android.modules.utils.BaseParceledListSlice.AnonymousClass1(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0092, code lost:
    
        if (com.android.modules.utils.BaseParceledListSlice.DEBUG == false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0094, code lost:
    
        android.util.Log.d(com.android.modules.utils.BaseParceledListSlice.TAG, "Breaking @" + r3 + " of " + r0 + ": retriever=" + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00b7, code lost:
    
        r8.writeStrongBinder(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00ba, code lost:
    
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
            java.util.List<T> r0 = r7.mList
            int r0 = r0.size()
            r8.writeInt(r0)
            boolean r1 = com.android.modules.utils.BaseParceledListSlice.DEBUG
            if (r1 == 0) goto L25
            java.lang.String r1 = com.android.modules.utils.BaseParceledListSlice.TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Writing "
            r2.<init>(r3)
            r2.append(r0)
            java.lang.String r3 = " items"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r1, r2)
        L25:
            if (r0 <= 0) goto Lba
            java.util.List<T> r1 = r7.mList
            r2 = 0
            java.lang.Object r1 = r1.get(r2)
            java.lang.Class r1 = r1.getClass()
            java.util.List<T> r3 = r7.mList
            java.lang.Object r3 = r3.get(r2)
            r7.writeParcelableCreator(r3, r8)
            r3 = r2
        L3c:
            if (r3 >= r0) goto L86
            int r4 = r7.mInlineCountLimit
            if (r3 >= r4) goto L86
            int r4 = r8.dataSize()
            int r5 = com.android.modules.utils.BaseParceledListSlice.MAX_IPC_SIZE
            if (r4 >= r5) goto L86
            r4 = 1
            r8.writeInt(r4)
            java.util.List<T> r4 = r7.mList
            java.lang.Object r4 = r4.get(r3)
            java.lang.Class r5 = r4.getClass()
            verifySameType(r1, r5)
            r7.writeElement(r4, r8, r9)
            boolean r4 = com.android.modules.utils.BaseParceledListSlice.DEBUG
            if (r4 == 0) goto L83
            java.lang.String r4 = com.android.modules.utils.BaseParceledListSlice.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Wrote inline #"
            r5.<init>(r6)
            r5.append(r3)
            java.lang.String r6 = ": "
            r5.append(r6)
            java.util.List<T> r6 = r7.mList
            java.lang.Object r6 = r6.get(r3)
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
        L83:
            int r3 = r3 + 1
            goto L3c
        L86:
            if (r3 >= r0) goto Lba
            r8.writeInt(r2)
            com.android.modules.utils.BaseParceledListSlice$1 r2 = new com.android.modules.utils.BaseParceledListSlice$1
            r2.<init>()
            boolean r7 = com.android.modules.utils.BaseParceledListSlice.DEBUG
            if (r7 == 0) goto Lb7
            java.lang.String r7 = com.android.modules.utils.BaseParceledListSlice.TAG
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r1 = "Breaking @"
            r9.<init>(r1)
            r9.append(r3)
            java.lang.String r1 = " of "
            r9.append(r1)
            r9.append(r0)
            java.lang.String r0 = ": retriever="
            r9.append(r0)
            r9.append(r2)
            java.lang.String r9 = r9.toString()
            android.util.Log.d(r7, r9)
        Lb7:
            r8.writeStrongBinder(r2)
        Lba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.modules.utils.BaseParceledListSlice.writeToParcel(android.os.Parcel, int):void");
    }
}
