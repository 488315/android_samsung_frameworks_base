package com.android.modules.utils;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
abstract class BaseParceledListSlice<T> implements Parcelable {
    private int mInlineCountLimit = Integer.MAX_VALUE;
    private final List<T> mList;
    private static String TAG = "ParceledListSlice";
    private static boolean DEBUG = false;
    private static final int MAX_IPC_SIZE = IBinder.getSuggestedMaxIpcSizeBytes();

    protected abstract Parcelable.Creator<?> readParcelableCreator(Parcel parcel, ClassLoader classLoader);

    protected abstract void writeElement(T t, Parcel parcel, int i);

    protected abstract void writeParcelableCreator(T t, Parcel parcel);

    public BaseParceledListSlice(List<T> list) {
        this.mList = list;
    }

    BaseParceledListSlice(Parcel p, ClassLoader loader) {
        int N = p.readInt();
        this.mList = new ArrayList(N);
        if (DEBUG) {
            Log.d(TAG, "Retrieving " + N + " items");
        }
        if (N <= 0) {
            return;
        }
        Parcelable.Creator<?> creator = readParcelableCreator(p, loader);
        Class<?> listElementClass = null;
        int i = 0;
        while (i < N && p.readInt() != 0) {
            T parcelable = readCreator(creator, p, loader);
            if (listElementClass == null) {
                listElementClass = parcelable.getClass();
            } else {
                verifySameType(listElementClass, parcelable.getClass());
            }
            this.mList.add(parcelable);
            if (DEBUG) {
                Log.d(TAG, "Read inline #" + i + ": " + this.mList.get(this.mList.size() - 1));
            }
            i++;
        }
        if (i >= N) {
            return;
        }
        IBinder retriever = p.readStrongBinder();
        int i2 = i;
        while (i2 < N) {
            if (DEBUG) {
                Log.d(TAG, "Reading more @" + i2 + " of " + N + ": retriever=" + retriever);
            }
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeInt(i2);
            try {
                retriever.transact(1, data, reply, 0);
                while (i2 < N && reply.readInt() != 0) {
                    T parcelable2 = readCreator(creator, reply, loader);
                    verifySameType(listElementClass, parcelable2.getClass());
                    this.mList.add(parcelable2);
                    if (DEBUG) {
                        Log.d(TAG, "Read extra #" + i2 + ": " + this.mList.get(this.mList.size() - 1));
                    }
                    i2++;
                }
                reply.recycle();
                data.recycle();
            } catch (RemoteException e) {
                Log.w(TAG, "Failure retrieving array; only received " + i2 + " of " + N, e);
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
    public static void verifySameType(Class<?> expected, Class<?> actual) {
        if (!actual.equals(expected)) {
            throw new IllegalArgumentException("Can't unparcel type " + (actual == null ? null : actual.getName()) + " in list of type " + (expected != null ? expected.getName() : null));
        }
    }

    public List<T> getList() {
        return this.mList;
    }

    public void setInlineCountLimit(int maxCount) {
        this.mInlineCountLimit = maxCount;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0097, code lost:
    
        r10.writeInt(0);
        r3 = new com.android.modules.utils.BaseParceledListSlice.AnonymousClass1(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00a1, code lost:
    
        if (com.android.modules.utils.BaseParceledListSlice.DEBUG == false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00a3, code lost:
    
        android.util.Log.d(com.android.modules.utils.BaseParceledListSlice.TAG, "Breaking @" + r4 + " of " + r0 + ": retriever=" + r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00cf, code lost:
    
        r10.writeStrongBinder(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00d2, code lost:
    
        return;
     */
    @Override // android.os.Parcelable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeToParcel(android.os.Parcel r10, final int r11) {
        /*
            r9 = this;
            java.util.List<T> r0 = r9.mList
            int r0 = r0.size()
            r1 = r11
            r10.writeInt(r0)
            boolean r2 = com.android.modules.utils.BaseParceledListSlice.DEBUG
            if (r2 == 0) goto L2c
            java.lang.String r2 = com.android.modules.utils.BaseParceledListSlice.TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Writing "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r4 = " items"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r2, r3)
        L2c:
            if (r0 <= 0) goto Ld2
            java.util.List<T> r2 = r9.mList
            r3 = 0
            java.lang.Object r2 = r2.get(r3)
            java.lang.Class r2 = r2.getClass()
            java.util.List<T> r4 = r9.mList
            java.lang.Object r4 = r4.get(r3)
            r9.writeParcelableCreator(r4, r10)
            r4 = 0
        L43:
            if (r4 >= r0) goto L95
            int r5 = r9.mInlineCountLimit
            if (r4 >= r5) goto L95
            int r5 = r10.dataSize()
            int r6 = com.android.modules.utils.BaseParceledListSlice.MAX_IPC_SIZE
            if (r5 >= r6) goto L95
            r5 = 1
            r10.writeInt(r5)
            java.util.List<T> r5 = r9.mList
            java.lang.Object r5 = r5.get(r4)
            java.lang.Class r6 = r5.getClass()
            verifySameType(r2, r6)
            r9.writeElement(r5, r10, r1)
            boolean r6 = com.android.modules.utils.BaseParceledListSlice.DEBUG
            if (r6 == 0) goto L91
            java.lang.String r6 = com.android.modules.utils.BaseParceledListSlice.TAG
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Wrote inline #"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r4)
            java.lang.String r8 = ": "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.util.List<T> r8 = r9.mList
            java.lang.Object r8 = r8.get(r4)
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Log.d(r6, r7)
        L91:
            int r4 = r4 + 1
            goto L43
        L95:
            if (r4 >= r0) goto Ld2
            r10.writeInt(r3)
            com.android.modules.utils.BaseParceledListSlice$1 r3 = new com.android.modules.utils.BaseParceledListSlice$1
            r3.<init>()
            boolean r5 = com.android.modules.utils.BaseParceledListSlice.DEBUG
            if (r5 == 0) goto Lcf
            java.lang.String r5 = com.android.modules.utils.BaseParceledListSlice.TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Breaking @"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r4)
            java.lang.String r7 = " of "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r7 = ": retriever="
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r3)
            java.lang.String r6 = r6.toString()
            android.util.Log.d(r5, r6)
        Lcf:
            r10.writeStrongBinder(r3)
        Ld2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.modules.utils.BaseParceledListSlice.writeToParcel(android.os.Parcel, int):void");
    }
}
