package android.os;

import android.app.ActivityManager;
import android.location.ILocationManager;
import android.os.Binder;
import android.os.BinderProxy;
import android.os.IBinder;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.internal.os.BinderInternal;
import java.io.FileDescriptor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes3.dex */
public final class BinderProxy implements IBinder {
    private static final String GMS_SHORT_LOOKUP = "gms.loc";
    private static final int NATIVE_ALLOCATION_SIZE = 1000;
    private static final ProxyMap sProxyMap = new ProxyMap();
    private static volatile Binder.ProxyTransactListener sTransactListener;
    private final long mNativeData;
    volatile boolean mWarnOnBlocking = Binder.sWarnOnBlocking;
    private int msgForGoogleLocation = 0;
    private List<IBinder.DeathRecipient> mDeathRecipients = Collections.synchronizedList(new ArrayList());
    private Map<IBinder.FrozenStateChangeCallback, IBinder.FrozenStateChangeCallback> mFrozenStateChangeCallbacks = Collections.synchronizedMap(new HashMap());

    private native void addFrozenStateChangeCallbackNative(IBinder.FrozenStateChangeCallback frozenStateChangeCallback) throws RemoteException;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getNativeFinalizer();

    private static native boolean isFrozenStateChangeCallbackSupportedNative();

    private native void linkToDeathNative(IBinder.DeathRecipient deathRecipient, int i) throws RemoteException;

    private native boolean removeFrozenStateChangeCallbackNative(IBinder.FrozenStateChangeCallback frozenStateChangeCallback);

    private native boolean unlinkToDeathNative(IBinder.DeathRecipient deathRecipient, int i);

    @Override // android.os.IBinder
    public native IBinder getExtension() throws RemoteException;

    @Override // android.os.IBinder
    public native String getInterfaceDescriptor() throws RemoteException;

    @Override // android.os.IBinder
    public native boolean isBinderAlive();

    @Override // android.os.IBinder
    public native boolean pingBinder();

    @Override // android.os.IBinder
    public IInterface queryLocalInterface(String str) {
        return null;
    }

    public native boolean transactNative(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException;

    private static class BinderProxyMapSizeException extends AssertionError {
        BinderProxyMapSizeException(String str) {
            super(str);
        }
    }

    public static void setTransactListener(Binder.ProxyTransactListener proxyTransactListener) {
        sTransactListener = proxyTransactListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ProxyMap {
        private static final int CRASH_AT_SIZE = 25000;
        private static final int LOG_MAIN_INDEX_SIZE = 8;
        private static final int MAIN_INDEX_MASK = 255;
        private static final int MAIN_INDEX_SIZE = 256;
        static final int MAX_NUM_INTERFACES_TO_DUMP = 10;
        private static final int WARN_INCREMENT = 10;
        private final Long[][] mMainIndexKeys;
        private final ArrayList<WeakReference<BinderProxy>>[] mMainIndexValues;
        private int mRandom;
        private int mWarnBucketSize;

        private static int hash(long j) {
            return ((int) ((j >> 10) ^ (j >> 2))) & 255;
        }

        private ProxyMap() {
            this.mWarnBucketSize = 20;
            this.mMainIndexKeys = new Long[256][];
            this.mMainIndexValues = new ArrayList[256];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int size() {
            int i = 0;
            for (ArrayList<WeakReference<BinderProxy>> arrayList : this.mMainIndexValues) {
                if (arrayList != null) {
                    i += arrayList.size();
                }
            }
            return i;
        }

        private int unclearedSize() {
            int i = 0;
            for (ArrayList<WeakReference<BinderProxy>> arrayList : this.mMainIndexValues) {
                if (arrayList != null) {
                    Iterator<WeakReference<BinderProxy>> it = arrayList.iterator();
                    while (it.hasNext()) {
                        if (!it.next().refersTo(null)) {
                            i++;
                        }
                    }
                }
            }
            return i;
        }

        private void remove(int i, int i2) {
            Long[] lArr = this.mMainIndexKeys[i];
            ArrayList<WeakReference<BinderProxy>> arrayList = this.mMainIndexValues[i];
            int size = arrayList.size() - 1;
            if (i2 != size) {
                lArr[i2] = lArr[size];
                arrayList.set(i2, arrayList.get(size));
            }
            arrayList.remove(size);
        }

        BinderProxy get(long j) {
            int hash = hash(j);
            Long[] lArr = this.mMainIndexKeys[hash];
            if (lArr == null) {
                return null;
            }
            ArrayList<WeakReference<BinderProxy>> arrayList = this.mMainIndexValues[hash];
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (j == lArr[i].longValue()) {
                    BinderProxy binderProxy = arrayList.get(i).get();
                    if (binderProxy != null) {
                        return binderProxy;
                    }
                    remove(hash, i);
                    return null;
                }
            }
            return null;
        }

        void set(long j, BinderProxy binderProxy) {
            int hash = hash(j);
            ArrayList<WeakReference<BinderProxy>>[] arrayListArr = this.mMainIndexValues;
            ArrayList<WeakReference<BinderProxy>> arrayList = arrayListArr[hash];
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                arrayListArr[hash] = arrayList;
                this.mMainIndexKeys[hash] = new Long[1];
            }
            int size = arrayList.size();
            WeakReference<BinderProxy> weakReference = new WeakReference<>(binderProxy);
            for (int i = 0; i < size; i++) {
                if (arrayList.get(i).refersTo(null)) {
                    arrayList.set(i, weakReference);
                    this.mMainIndexKeys[hash][i] = Long.valueOf(j);
                    if (i < size - 1) {
                        int i2 = this.mRandom + 1;
                        this.mRandom = i2;
                        int i3 = i + 1;
                        int floorMod = i3 + Math.floorMod(i2, size - i3);
                        if (arrayList.get(floorMod).refersTo(null)) {
                            remove(hash, floorMod);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            arrayList.add(size, weakReference);
            Long[] lArr = this.mMainIndexKeys[hash];
            if (lArr.length == size) {
                Long[] lArr2 = new Long[(size / 2) + size + 2];
                System.arraycopy(lArr, 0, lArr2, 0, size);
                lArr2[size] = Long.valueOf(j);
                this.mMainIndexKeys[hash] = lArr2;
            } else {
                lArr[size] = Long.valueOf(j);
            }
            if (size >= this.mWarnBucketSize) {
                int size2 = size();
                Log.v("Binder", "BinderProxy map growth! bucket size = " + size + " total = " + size2);
                this.mWarnBucketSize = this.mWarnBucketSize + 10;
                if (size2 >= 25000) {
                    int unclearedSize = unclearedSize();
                    if (unclearedSize >= 25000) {
                        dumpProxyInterfaceCounts();
                        dumpPerUidProxyCounts();
                        Runtime.getRuntime().gc();
                        throw new BinderProxyMapSizeException("Binder ProxyMap has too many entries: " + size2 + " (total), " + unclearedSize + " (uncleared), " + unclearedSize() + " (uncleared after GC). BinderProxy leak?");
                    }
                    if (size2 > (unclearedSize * 3) / 2) {
                        Log.v("Binder", "BinderProxy map has many cleared entries: " + (size2 - unclearedSize) + " of " + size2 + " are cleared");
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public InterfaceCount[] getSortedInterfaceCounts(int i) {
            int i2;
            if (i < 0) {
                throw new IllegalArgumentException("negative interface count");
            }
            final HashMap hashMap = new HashMap();
            final ArrayList arrayList = new ArrayList();
            synchronized (BinderProxy.sProxyMap) {
                for (ArrayList<WeakReference<BinderProxy>> arrayList2 : this.mMainIndexValues) {
                    if (arrayList2 != null) {
                        arrayList.addAll(arrayList2);
                    }
                }
            }
            try {
                ActivityManager.getService().enableAppFreezer(false);
            } catch (RemoteException unused) {
                Log.e("Binder", "RemoteException while disabling app freezer");
            }
            ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
            newSingleThreadExecutor.submit(new Runnable() { // from class: android.os.BinderProxy$ProxyMap$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BinderProxy.ProxyMap.lambda$getSortedInterfaceCounts$0(arrayList, hashMap);
                }
            });
            try {
                newSingleThreadExecutor.shutdown();
                if (!newSingleThreadExecutor.awaitTermination(20L, TimeUnit.SECONDS)) {
                    Log.e("Binder", "Failed to complete binder proxy dump, dumping what we have so far.");
                }
            } catch (InterruptedException unused2) {
            }
            try {
                ActivityManager.getService().enableAppFreezer(true);
            } catch (RemoteException unused3) {
                Log.e("Binder", "RemoteException while re-enabling app freezer");
            }
            Map.Entry[] entryArr = (Map.Entry[]) hashMap.entrySet().toArray(new Map.Entry[hashMap.size()]);
            Arrays.sort(entryArr, new Comparator() { // from class: android.os.BinderProxy$ProxyMap$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int compareTo;
                    compareTo = ((Integer) ((Map.Entry) obj2).getValue()).compareTo((Integer) ((Map.Entry) obj).getValue());
                    return compareTo;
                }
            });
            int min = Math.min(i, entryArr.length);
            InterfaceCount[] interfaceCountArr = new InterfaceCount[min];
            for (i2 = 0; i2 < min; i2++) {
                interfaceCountArr[i2] = new InterfaceCount((String) entryArr[i2].getKey(), ((Integer) entryArr[i2].getValue()).intValue());
            }
            return interfaceCountArr;
        }

        static /* synthetic */ void lambda$getSortedInterfaceCounts$0(ArrayList arrayList, Map map) {
            String str;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                BinderProxy binderProxy = (BinderProxy) ((WeakReference) it.next()).get();
                if (binderProxy == null) {
                    str = "<cleared weak-ref>";
                } else {
                    try {
                        String interfaceDescriptor = binderProxy.getInterfaceDescriptor();
                        str = ((interfaceDescriptor == null || interfaceDescriptor.isEmpty()) && !binderProxy.isBinderAlive()) ? "<proxy to dead node>" : interfaceDescriptor;
                    } catch (Throwable unused) {
                        str = "<exception during getDescriptor>";
                    }
                }
                Integer num = (Integer) map.get(str);
                if (num == null) {
                    map.put(str, 1);
                } else {
                    map.put(str, Integer.valueOf(num.intValue() + 1));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpProxyInterfaceCounts() {
            InterfaceCount[] sortedInterfaceCounts = getSortedInterfaceCounts(10);
            Log.v("Binder", "BinderProxy descriptor histogram (top " + Integer.toString(10) + "):");
            int i = 0;
            while (i < sortedInterfaceCounts.length) {
                StringBuilder sb = new StringBuilder(" #");
                int i2 = i + 1;
                sb.append(i2);
                sb.append(": ");
                sb.append(sortedInterfaceCounts[i]);
                Log.v("Binder", sb.toString());
                i = i2;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpPerUidProxyCounts() {
            SparseIntArray nGetBinderProxyPerUidCounts = BinderInternal.nGetBinderProxyPerUidCounts();
            if (nGetBinderProxyPerUidCounts.size() == 0) {
                return;
            }
            Log.d("Binder", "Per Uid Binder Proxy Counts:");
            for (int i = 0; i < nGetBinderProxyPerUidCounts.size(); i++) {
                Log.d("Binder", "UID : " + nGetBinderProxyPerUidCounts.keyAt(i) + "  count = " + nGetBinderProxyPerUidCounts.valueAt(i));
            }
        }
    }

    public static final class InterfaceCount {
        private final int mCount;
        private final String mInterfaceName;

        InterfaceCount(String str, int i) {
            this.mInterfaceName = str;
            this.mCount = i;
        }

        public String toString() {
            return this.mInterfaceName + " x" + Integer.toString(this.mCount);
        }
    }

    public static InterfaceCount[] getSortedInterfaceCounts(int i) {
        return sProxyMap.getSortedInterfaceCounts(i);
    }

    public static int getProxyCount() {
        int size;
        ProxyMap proxyMap = sProxyMap;
        synchronized (proxyMap) {
            size = proxyMap.size();
        }
        return size;
    }

    public static void dumpProxyDebugInfo() {
        if (Build.IS_DEBUGGABLE) {
            ProxyMap proxyMap = sProxyMap;
            proxyMap.dumpProxyInterfaceCounts();
            proxyMap.dumpPerUidProxyCounts();
        }
    }

    private static BinderProxy getInstance(long j, long j2) {
        ProxyMap proxyMap = sProxyMap;
        synchronized (proxyMap) {
            try {
                BinderProxy binderProxy = proxyMap.get(j2);
                if (binderProxy != null) {
                    return binderProxy;
                }
                BinderProxy binderProxy2 = new BinderProxy(j);
                NoImagePreloadHolder.sRegistry.registerNativeAllocation(binderProxy2, j);
                proxyMap.set(j2, binderProxy2);
                return binderProxy2;
            } catch (Throwable th) {
                NativeAllocationRegistry.applyFreeFunction(NoImagePreloadHolder.sNativeFinalizer, j);
                throw th;
            }
        }
    }

    private BinderProxy(long j) {
        this.mNativeData = j;
    }

    private static class NoImagePreloadHolder {
        public static final long sNativeFinalizer;
        public static final NativeAllocationRegistry sRegistry;

        private NoImagePreloadHolder() {
        }

        static {
            long nativeFinalizer = BinderProxy.getNativeFinalizer();
            sNativeFinalizer = nativeFinalizer;
            sRegistry = new NativeAllocationRegistry(BinderProxy.class.getClassLoader(), nativeFinalizer, 1000L);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:75:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x013f  */
    @Override // android.os.IBinder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean transact(int r28, android.os.Parcel r29, android.os.Parcel r30, int r31) throws android.os.RemoteException {
        /*
            Method dump skipped, instructions count: 398
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BinderProxy.transact(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }

    @Override // android.os.IBinder
    public void linkToDeath(IBinder.DeathRecipient deathRecipient, int i) throws RemoteException {
        linkToDeathNative(deathRecipient, i);
        this.mDeathRecipients.add(deathRecipient);
    }

    @Override // android.os.IBinder
    public boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
        this.mDeathRecipients.remove(deathRecipient);
        return unlinkToDeathNative(deathRecipient, i);
    }

    @Override // android.os.IBinder
    public void addFrozenStateChangeCallback(final Executor executor, final IBinder.FrozenStateChangeCallback frozenStateChangeCallback) throws RemoteException {
        IBinder.FrozenStateChangeCallback frozenStateChangeCallback2 = new IBinder.FrozenStateChangeCallback() { // from class: android.os.BinderProxy$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.FrozenStateChangeCallback
            public final void onFrozenStateChanged(IBinder iBinder, int i) {
                executor.execute(new Runnable() { // from class: android.os.BinderProxy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        IBinder.FrozenStateChangeCallback.this.onFrozenStateChanged(iBinder, i);
                    }
                });
            }
        };
        addFrozenStateChangeCallbackNative(frozenStateChangeCallback2);
        this.mFrozenStateChangeCallbacks.put(frozenStateChangeCallback, frozenStateChangeCallback2);
    }

    @Override // android.os.IBinder
    public boolean removeFrozenStateChangeCallback(IBinder.FrozenStateChangeCallback frozenStateChangeCallback) throws IllegalArgumentException {
        IBinder.FrozenStateChangeCallback remove = this.mFrozenStateChangeCallbacks.remove(frozenStateChangeCallback);
        if (remove == null) {
            throw new IllegalArgumentException("callback not found");
        }
        return removeFrozenStateChangeCallbackNative(remove);
    }

    public static boolean isFrozenStateChangeCallbackSupported() {
        return isFrozenStateChangeCallbackSupportedNative();
    }

    @Override // android.os.IBinder
    public void dump(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        obtain.writeFileDescriptor(fileDescriptor);
        obtain.writeStringArray(strArr);
        try {
            transact(IBinder.DUMP_TRANSACTION, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.os.IBinder
    public void dumpAsync(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        obtain.writeFileDescriptor(fileDescriptor);
        obtain.writeStringArray(strArr);
        try {
            transact(IBinder.DUMP_TRANSACTION, obtain, obtain2, 1);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.os.IBinder
    public void shellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        obtain.writeFileDescriptor(fileDescriptor);
        obtain.writeFileDescriptor(fileDescriptor2);
        obtain.writeFileDescriptor(fileDescriptor3);
        obtain.writeStringArray(strArr);
        ShellCallback.writeToParcel(shellCallback, obtain);
        resultReceiver.writeToParcel(obtain, 0);
        try {
            transact(IBinder.SHELL_COMMAND_TRANSACTION, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    private static void sendDeathNotice(IBinder.DeathRecipient deathRecipient, IBinder iBinder) {
        try {
            deathRecipient.binderDied(iBinder);
        } catch (RuntimeException e) {
            Log.w("BinderNative", "Uncaught exception from death notification", e);
        }
    }

    private static void invokeFrozenStateChangeCallback(IBinder.FrozenStateChangeCallback frozenStateChangeCallback, IBinder iBinder, int i) {
        try {
            frozenStateChangeCallback.onFrozenStateChanged(iBinder, i);
        } catch (RuntimeException e) {
            Log.w("BinderNative", "Uncaught exception from frozen state change callback", e);
        }
    }

    private boolean isMsgForGoogleLocation(Parcel parcel) {
        int i = this.msgForGoogleLocation;
        if (i == 1) {
            return false;
        }
        if (i == 2) {
            return true;
        }
        this.msgForGoogleLocation = 1;
        if (parcel == null || !customContains(parcel.getInterfaceName())) {
            return false;
        }
        this.msgForGoogleLocation = 2;
        return true;
    }

    private boolean customContains(String str) {
        if (str == null || str.length() < 26) {
            return false;
        }
        for (int i = 0; i < 7; i++) {
            if (str.charAt(i + 19) != GMS_SHORT_LOOKUP.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private void sendInfoToNSFLP(int i, Parcel parcel) {
        Parcel obtain = Parcel.obtain();
        try {
            ILocationManager asInterface = ILocationManager.Stub.asInterface(ServiceManager.getService("location"));
            if (asInterface != null) {
                obtain.writeInt(parcel.dataSize());
                obtain.appendFrom(parcel, 0, parcel.dataSize());
                obtain.setDataPosition(0);
                ParcelableParcel createFromParcel = ParcelableParcel.CREATOR.createFromParcel(obtain);
                Bundle bundle = new Bundle();
                bundle.putParcelable("pp", createFromParcel);
                bundle.putString("interfaceName", parcel.getInterfaceName());
                bundle.putInt("uid", Binder.getCallingUid());
                bundle.putInt("pid", Binder.getCallingPid());
                Message message = new Message();
                message.what = 200;
                message.arg1 = i;
                message.setData(bundle);
                asInterface.notifyNSFLP(message);
            }
        } catch (Exception e) {
            Log.w("Binder_FLP", "failed to send info to nsflp");
            e.printStackTrace();
        } finally {
            obtain.recycle();
        }
    }
}
