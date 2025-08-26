package android.os;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.location.ILocationManager;
import android.media.MediaMetrics;
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
            int size = 0;
            for (ArrayList<WeakReference<BinderProxy>> arrayList : this.mMainIndexValues) {
                if (arrayList != null) {
                    size += arrayList.size();
                }
            }
            return size;
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
            int iHash = hash(j);
            Long[] lArr = this.mMainIndexKeys[iHash];
            if (lArr == null) {
                return null;
            }
            ArrayList<WeakReference<BinderProxy>> arrayList = this.mMainIndexValues[iHash];
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (j == lArr[i].longValue()) {
                    BinderProxy binderProxy = arrayList.get(i).get();
                    if (binderProxy != null) {
                        return binderProxy;
                    }
                    remove(iHash, i);
                    return null;
                }
            }
            return null;
        }

        void set(long j, BinderProxy binderProxy) {
            int iHash = hash(j);
            ArrayList<WeakReference<BinderProxy>>[] arrayListArr = this.mMainIndexValues;
            ArrayList<WeakReference<BinderProxy>> arrayList = arrayListArr[iHash];
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                arrayListArr[iHash] = arrayList;
                this.mMainIndexKeys[iHash] = new Long[1];
            }
            int size = arrayList.size();
            WeakReference<BinderProxy> weakReference = new WeakReference<>(binderProxy);
            for (int i = 0; i < size; i++) {
                if (arrayList.get(i).refersTo(null)) {
                    arrayList.set(i, weakReference);
                    this.mMainIndexKeys[iHash][i] = Long.valueOf(j);
                    if (i < size - 1) {
                        int i2 = this.mRandom + 1;
                        this.mRandom = i2;
                        int i3 = i + 1;
                        int iFloorMod = i3 + Math.floorMod(i2, size - i3);
                        if (arrayList.get(iFloorMod).refersTo(null)) {
                            remove(iHash, iFloorMod);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            arrayList.add(size, weakReference);
            Long[] lArr = this.mMainIndexKeys[iHash];
            if (lArr.length == size) {
                Long[] lArr2 = new Long[(size / 2) + size + 2];
                System.arraycopy(lArr, 0, lArr2, 0, size);
                lArr2[size] = Long.valueOf(j);
                this.mMainIndexKeys[iHash] = lArr2;
            } else {
                lArr[size] = Long.valueOf(j);
            }
            if (size >= this.mWarnBucketSize) {
                int size2 = size();
                Log.v("Binder", "BinderProxy map growth! bucket size = " + size + " total = " + size2);
                this.mWarnBucketSize = this.mWarnBucketSize + 10;
                if (size2 >= 25000) {
                    int iUnclearedSize = unclearedSize();
                    if (iUnclearedSize >= 25000) {
                        dumpProxyInterfaceCounts();
                        dumpPerUidProxyCounts();
                        Runtime.getRuntime().gc();
                        throw new BinderProxyMapSizeException("Binder ProxyMap has too many entries: " + size2 + " (total), " + iUnclearedSize + " (uncleared), " + unclearedSize() + " (uncleared after GC). BinderProxy leak?");
                    }
                    if (size2 > (iUnclearedSize * 3) / 2) {
                        Log.v("Binder", "BinderProxy map has many cleared entries: " + (size2 - iUnclearedSize) + " of " + size2 + " are cleared");
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
            final HashMap map = new HashMap();
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
            ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
            executorServiceNewSingleThreadExecutor.submit(new Runnable() { // from class: android.os.BinderProxy$ProxyMap$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BinderProxy.ProxyMap.lambda$getSortedInterfaceCounts$0(arrayList, map);
                }
            });
            try {
                executorServiceNewSingleThreadExecutor.shutdown();
                if (!executorServiceNewSingleThreadExecutor.awaitTermination(20L, TimeUnit.SECONDS)) {
                    Log.e("Binder", "Failed to complete binder proxy dump, dumping what we have so far.");
                }
            } catch (InterruptedException unused2) {
            }
            try {
                ActivityManager.getService().enableAppFreezer(true);
            } catch (RemoteException unused3) {
                Log.e("Binder", "RemoteException while re-enabling app freezer");
            }
            Map.Entry[] entryArr = (Map.Entry[]) map.entrySet().toArray(new Map.Entry[map.size()]);
            Arrays.sort(entryArr, new Comparator() { // from class: android.os.BinderProxy$ProxyMap$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ((Integer) ((Map.Entry) obj2).getValue()).compareTo((Integer) ((Map.Entry) obj).getValue());
                }
            });
            int iMin = Math.min(i, entryArr.length);
            InterfaceCount[] interfaceCountArr = new InterfaceCount[iMin];
            for (i2 = 0; i2 < iMin; i2++) {
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
            SparseIntArray sparseIntArrayNGetBinderProxyPerUidCounts = BinderInternal.nGetBinderProxyPerUidCounts();
            if (sparseIntArrayNGetBinderProxyPerUidCounts.size() == 0) {
                return;
            }
            Log.d("Binder", "Per Uid Binder Proxy Counts:");
            for (int i = 0; i < sparseIntArrayNGetBinderProxyPerUidCounts.size(); i++) {
                Log.d("Binder", "UID : " + sparseIntArrayNGetBinderProxyPerUidCounts.keyAt(i) + "  count = " + sparseIntArrayNGetBinderProxyPerUidCounts.valueAt(i));
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

    /* JADX WARN: Removed duplicated region for block: B:82:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x013f  */
    @Override // android.os.IBinder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean transact(int i, Parcel parcel, Parcel parcel2, int i2) throws Throwable {
        Throwable th;
        boolean z;
        long jCurrentTimeMillis;
        boolean zTransactNative;
        int i3 = i2;
        Binder.checkParcel(this, i, parcel, "Unreasonably large binder buffer");
        boolean z2 = this.mWarnOnBlocking;
        if (z2 && (i3 & 1) == 0 && Binder.sWarnOnBlockingOnCurrentThread.get().booleanValue()) {
            this.mWarnOnBlocking = false;
            if (Build.IS_USERDEBUG || Build.IS_ENG) {
                Log.wtf("Binder", "Outgoing transactions from this process must be FLAG_ONEWAY", new Throwable());
            } else {
                Log.w("Binder", "Outgoing transactions from this process must be FLAG_ONEWAY", new Throwable());
            }
            z2 = false;
        }
        boolean zIsStackTrackingEnabled = Binder.isStackTrackingEnabled();
        Object obj = null;
        if (zIsStackTrackingEnabled) {
            th = new Throwable();
            if (!Binder.isSystemServerBinderTrackerEnabled) {
                Binder.getTransactionTracker().addTrace(th);
            }
            StackTraceElement stackTraceElement = th.getStackTrace()[1];
            Trace.traceBegin(1L, stackTraceElement.getClassName() + MediaMetrics.SEPARATOR + stackTraceElement.getMethodName());
        } else {
            th = null;
        }
        if (isMsgForGoogleLocation(parcel)) {
            sendInfoToNSFLP(i, parcel);
        }
        Binder.ProxyTransactListener proxyTransactListener = sTransactListener;
        if (proxyTransactListener != null) {
            int callingWorkSourceUid = Binder.getCallingWorkSourceUid();
            Object objOnTransactStarted = proxyTransactListener.onTransactStarted(this, i, i3);
            int callingWorkSourceUid2 = Binder.getCallingWorkSourceUid();
            if (callingWorkSourceUid != callingWorkSourceUid2) {
                parcel.replaceCallingWorkSourceUid(callingWorkSourceUid2);
            }
            obj = objOnTransactStarted;
        }
        AppOpsManager.PausedNotedAppOpsCollection pausedNotedAppOpsCollectionPauseNotedAppOpsCollection = AppOpsManager.pauseNotedAppOpsCollection();
        if ((i3 & 1) == 0 && AppOpsManager.isListeningForOpNoted()) {
            i3 |= 2;
        }
        if (Binder.isSystemServerBinderTrackerEnabled && !Binder.isSystemServer) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            long jElapsedRealtimeNanos = 0;
            try {
                String interfaceName = parcel.getInterfaceName();
                if (interfaceName != null) {
                    parcelObtain.writeInterfaceToken(interfaceName);
                }
                try {
                    zTransactNative = transactNative(IBinder.ISSYSTEMSERVER_TRANSACTION, parcelObtain, parcelObtain2, 0);
                } catch (SecurityException unused) {
                    zTransactNative = false;
                }
                z = zTransactNative ? parcelObtain2.readBoolean() : false;
                try {
                    jCurrentTimeMillis = System.currentTimeMillis();
                    try {
                        jElapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
                        boolean zTransactNative2 = transactNative(i, parcel, parcel2, i3);
                        if (parcel2 != null && !z2) {
                            parcel2.addFlags(1);
                        }
                        parcelObtain.recycle();
                        parcelObtain2.recycle();
                        if (proxyTransactListener != null) {
                            proxyTransactListener.onTransactEnded(obj);
                        }
                        if (zIsStackTrackingEnabled) {
                            if (th == null) {
                                th = new Throwable();
                            }
                            Throwable th2 = th;
                            if (z) {
                                Binder.getTransactionTracker().addTimeStamp(th2, jCurrentTimeMillis, SystemClock.elapsedRealtimeNanos() - jElapsedRealtimeNanos, (i3 & 1) != 0);
                            }
                            Trace.traceEnd(1L);
                        }
                        return zTransactNative2;
                    } catch (Throwable th3) {
                        th = th3;
                        parcelObtain.recycle();
                        parcelObtain2.recycle();
                        if (proxyTransactListener != null) {
                            proxyTransactListener.onTransactEnded(obj);
                        }
                        if (zIsStackTrackingEnabled) {
                            if (th == null) {
                                th = new Throwable();
                            }
                            Throwable th4 = th;
                            if (z) {
                                Binder.getTransactionTracker().addTimeStamp(th4, jCurrentTimeMillis, SystemClock.elapsedRealtimeNanos() - jElapsedRealtimeNanos, (i3 & 1) != 0);
                            }
                            Trace.traceEnd(1L);
                        }
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    jCurrentTimeMillis = 0;
                    parcelObtain.recycle();
                    parcelObtain2.recycle();
                    if (proxyTransactListener != null) {
                    }
                    if (zIsStackTrackingEnabled) {
                    }
                    throw th;
                }
            } catch (Throwable th6) {
                th = th6;
                z = false;
            }
        } else {
            try {
                boolean zTransactNative3 = transactNative(i, parcel, parcel2, i3);
                if (parcel2 != null && !z2) {
                    parcel2.addFlags(1);
                }
                return zTransactNative3;
            } finally {
                AppOpsManager.resumeNotedAppOpsCollection(pausedNotedAppOpsCollectionPauseNotedAppOpsCollection);
                if (proxyTransactListener != null) {
                    proxyTransactListener.onTransactEnded(obj);
                }
                if (zIsStackTrackingEnabled) {
                    Trace.traceEnd(1L);
                }
            }
        }
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
                        frozenStateChangeCallback.onFrozenStateChanged(iBinder, i);
                    }
                });
            }
        };
        addFrozenStateChangeCallbackNative(frozenStateChangeCallback2);
        this.mFrozenStateChangeCallbacks.put(frozenStateChangeCallback, frozenStateChangeCallback2);
    }

    @Override // android.os.IBinder
    public boolean removeFrozenStateChangeCallback(IBinder.FrozenStateChangeCallback frozenStateChangeCallback) throws IllegalArgumentException {
        IBinder.FrozenStateChangeCallback frozenStateChangeCallbackRemove = this.mFrozenStateChangeCallbacks.remove(frozenStateChangeCallback);
        if (frozenStateChangeCallbackRemove == null) {
            throw new IllegalArgumentException("callback not found");
        }
        return removeFrozenStateChangeCallbackNative(frozenStateChangeCallbackRemove);
    }

    public static boolean isFrozenStateChangeCallbackSupported() {
        return isFrozenStateChangeCallbackSupportedNative();
    }

    @Override // android.os.IBinder
    public void dump(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        parcelObtain.writeFileDescriptor(fileDescriptor);
        parcelObtain.writeStringArray(strArr);
        try {
            transact(IBinder.DUMP_TRANSACTION, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.os.IBinder
    public void dumpAsync(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        parcelObtain.writeFileDescriptor(fileDescriptor);
        parcelObtain.writeStringArray(strArr);
        try {
            transact(IBinder.DUMP_TRANSACTION, parcelObtain, parcelObtain2, 1);
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.os.IBinder
    public void shellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        parcelObtain.writeFileDescriptor(fileDescriptor);
        parcelObtain.writeFileDescriptor(fileDescriptor2);
        parcelObtain.writeFileDescriptor(fileDescriptor3);
        parcelObtain.writeStringArray(strArr);
        ShellCallback.writeToParcel(shellCallback, parcelObtain);
        resultReceiver.writeToParcel(parcelObtain, 0);
        try {
            transact(IBinder.SHELL_COMMAND_TRANSACTION, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
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
        Parcel parcelObtain = Parcel.obtain();
        try {
            ILocationManager iLocationManagerAsInterface = ILocationManager.Stub.asInterface(ServiceManager.getService("location"));
            if (iLocationManagerAsInterface != null) {
                parcelObtain.writeInt(parcel.dataSize());
                parcelObtain.appendFrom(parcel, 0, parcel.dataSize());
                parcelObtain.setDataPosition(0);
                ParcelableParcel parcelableParcelCreateFromParcel = ParcelableParcel.CREATOR.createFromParcel(parcelObtain);
                Bundle bundle = new Bundle();
                bundle.putParcelable("pp", parcelableParcelCreateFromParcel);
                bundle.putString("interfaceName", parcel.getInterfaceName());
                bundle.putInt("uid", Binder.getCallingUid());
                bundle.putInt("pid", Binder.getCallingPid());
                Message message = new Message();
                message.what = 200;
                message.arg1 = i;
                message.setData(bundle);
                iLocationManagerAsInterface.notifyNSFLP(message);
            }
        } catch (Exception e) {
            Log.w("Binder_FLP", "failed to send info to nsflp");
            e.printStackTrace();
        } finally {
            parcelObtain.recycle();
        }
    }
}
