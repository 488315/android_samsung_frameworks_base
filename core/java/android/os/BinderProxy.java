package android.os;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.location.ILocationManager;
import android.media.MediaMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.internal.os.BinderInternal;
import com.samsung.android.media.AudioParameter;
import java.io.FileDescriptor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes3.dex */
public final class BinderProxy implements IBinder {
  private static final String GMS_SHORT_LOOKUP = "gms.loc";
  private static final int NATIVE_ALLOCATION_SIZE = 1000;
  private final long mNativeData;
  volatile boolean mWarnOnBlocking = Binder.sWarnOnBlocking;
  private int msgForGoogleLocation = 0;
  private static volatile Binder.ProxyTransactListener sTransactListener = null;
  private static final ProxyMap sProxyMap = new ProxyMap();

  /* JADX INFO: Access modifiers changed from: private */
  public static native long getNativeFinalizer();

  @Override // android.os.IBinder
  public native IBinder getExtension() throws RemoteException;

  @Override // android.os.IBinder
  public native String getInterfaceDescriptor() throws RemoteException;

  @Override // android.os.IBinder
  public native boolean isBinderAlive();

  @Override // android.os.IBinder
  public native void linkToDeath(IBinder.DeathRecipient deathRecipient, int i)
      throws RemoteException;

  @Override // android.os.IBinder
  public native boolean pingBinder();

  public native boolean transactNative(int i, Parcel parcel, Parcel parcel2, int i2)
      throws RemoteException;

  @Override // android.os.IBinder
  public native boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int i);

  private static class BinderProxyMapSizeException extends AssertionError {
    BinderProxyMapSizeException(String s) {
      super(s);
    }
  }

  public static void setTransactListener(Binder.ProxyTransactListener listener) {
    sTransactListener = listener;
  }

  /* JADX INFO: Access modifiers changed from: private */
  static final class ProxyMap {
    private static final int CRASH_AT_SIZE = 25000;
    private static final boolean FORCE_LIMIT_ENABLE = true;
    private static final int FORCE_LIMIT_SIZE = 25000;
    private static final int LOG_MAIN_INDEX_SIZE = 8;
    private static final int MAIN_INDEX_MASK = 255;
    private static final int MAIN_INDEX_SIZE = 256;
    static final int MAX_NUM_INTERFACES_TO_DUMP = 10;
    private static final int WARN_INCREMENT = 10;
    private final Long[][] mMainIndexKeys;
    private final ArrayList<WeakReference<BinderProxy>>[] mMainIndexValues;
    private int mRandom;
    private int mWarnBucketSize;

    private ProxyMap() {
      this.mWarnBucketSize = 20;
      this.mMainIndexKeys = new Long[256][];
      this.mMainIndexValues = new ArrayList[256];
    }

    private static int hash(long arg) {
      return ((int) ((arg >> 2) ^ (arg >> 10))) & 255;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int size() {
      int size = 0;
      for (ArrayList<WeakReference<BinderProxy>> a : this.mMainIndexValues) {
        if (a != null) {
          size += a.size();
        }
      }
      return size;
    }

    private int unclearedSize() {
      int size = 0;
      for (ArrayList<WeakReference<BinderProxy>> a : this.mMainIndexValues) {
        if (a != null) {
          Iterator<WeakReference<BinderProxy>> it = a.iterator();
          while (it.hasNext()) {
            WeakReference<BinderProxy> ref = it.next();
            if (!ref.refersTo(null)) {
              size++;
            }
          }
        }
      }
      return size;
    }

    private void remove(int hash, int index) {
      Long[] keyArray = this.mMainIndexKeys[hash];
      ArrayList<WeakReference<BinderProxy>> valueArray = this.mMainIndexValues[hash];
      int size = valueArray.size();
      if (index != size - 1) {
        keyArray[index] = keyArray[size - 1];
        valueArray.set(index, valueArray.get(size - 1));
      }
      valueArray.remove(size - 1);
    }

    BinderProxy get(long key) {
      int myHash = hash(key);
      Long[] keyArray = this.mMainIndexKeys[myHash];
      if (keyArray == null) {
        return null;
      }
      ArrayList<WeakReference<BinderProxy>> valueArray = this.mMainIndexValues[myHash];
      int bucketSize = valueArray.size();
      for (int i = 0; i < bucketSize; i++) {
        long foundKey = keyArray[i].longValue();
        if (key == foundKey) {
          WeakReference<BinderProxy> wr = valueArray.get(i);
          BinderProxy bp = wr.get();
          if (bp != null) {
            return bp;
          }
          remove(myHash, i);
          return null;
        }
      }
      return null;
    }

    void set(long key, BinderProxy value) {
      int myHash = hash(key);
      ArrayList<WeakReference<BinderProxy>>[] arrayListArr = this.mMainIndexValues;
      ArrayList<WeakReference<BinderProxy>> valueArray = arrayListArr[myHash];
      boolean enableProxyLimit = true;
      if (valueArray == null) {
        ArrayList<WeakReference<BinderProxy>> arrayList = new ArrayList<>();
        arrayListArr[myHash] = arrayList;
        valueArray = arrayList;
        this.mMainIndexKeys[myHash] = new Long[1];
      }
      int size = valueArray.size();
      WeakReference<BinderProxy> newWr = new WeakReference<>(value);
      for (int i = 0; i < size; i++) {
        if (valueArray.get(i).refersTo(null)) {
          valueArray.set(i, newWr);
          this.mMainIndexKeys[myHash][i] = Long.valueOf(key);
          if (i < size - 1) {
            int i2 = this.mRandom + 1;
            this.mRandom = i2;
            int rnd = Math.floorMod(i2, size - (i + 1));
            if (valueArray.get(i + 1 + rnd).refersTo(null)) {
              remove(myHash, i + 1 + rnd);
              return;
            }
            return;
          }
          return;
        }
      }
      valueArray.add(size, newWr);
      Long[] keyArray = this.mMainIndexKeys[myHash];
      if (keyArray.length == size) {
        Long[] newArray = new Long[(size / 2) + size + 2];
        System.arraycopy(keyArray, 0, newArray, 0, size);
        newArray[size] = Long.valueOf(key);
        this.mMainIndexKeys[myHash] = newArray;
      } else {
        keyArray[size] = Long.valueOf(key);
      }
      if (size >= this.mWarnBucketSize) {
        int totalSize = size();
        Log.m100v(
            "Binder", "BinderProxy map growth! bucket size = " + size + " total = " + totalSize);
        this.mWarnBucketSize += 10;
        boolean z = Build.IS_DEBUGGABLE;
        if (!Build.IS_DEBUGGABLE && Binder.isSystemServer) {
          enableProxyLimit = false;
        }
        if (enableProxyLimit && totalSize >= 25000) {
          int totalUnclearedSize = unclearedSize();
          if (totalUnclearedSize >= 25000) {
            if (Build.IS_DEBUGGABLE) {
              dumpProxyInterfaceCounts();
              dumpPerUidProxyCounts();
            }
            Runtime.getRuntime().gc();
            throw new BinderProxyMapSizeException(
                "Binder ProxyMap has too many entries: "
                    + totalSize
                    + " (total), "
                    + totalUnclearedSize
                    + " (uncleared), "
                    + unclearedSize()
                    + " (uncleared after GC). BinderProxy leak?");
          }
          if (totalSize > (totalUnclearedSize * 3) / 2) {
            Log.m100v(
                "Binder",
                "BinderProxy map has many cleared entries: "
                    + (totalSize - totalUnclearedSize)
                    + " of "
                    + totalSize
                    + " are cleared");
          }
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public InterfaceCount[] getSortedInterfaceCounts(int maxToReturn) {
      if (maxToReturn < 0) {
        throw new IllegalArgumentException("negative interface count");
      }
      final Map<String, Integer> counts = new HashMap<>();
      final ArrayList<WeakReference<BinderProxy>> proxiesToQuery = new ArrayList<>();
      synchronized (BinderProxy.sProxyMap) {
        for (ArrayList<WeakReference<BinderProxy>> a : this.mMainIndexValues) {
          if (a != null) {
            proxiesToQuery.addAll(a);
          }
        }
      }
      try {
        ActivityManager.getService().enableAppFreezer(false);
      } catch (RemoteException e) {
        Log.m96e("Binder", "RemoteException while disabling app freezer");
      }
      ExecutorService executorService = Executors.newSingleThreadExecutor();
      executorService.submit(
          new Runnable() { // from class: android.os.BinderProxy$ProxyMap$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              BinderProxy.ProxyMap.lambda$getSortedInterfaceCounts$0(proxiesToQuery, counts);
            }
          });
      try {
        executorService.shutdown();
        boolean dumpDone = executorService.awaitTermination(20L, TimeUnit.SECONDS);
        if (!dumpDone) {
          Log.m96e("Binder", "Failed to complete binder proxy dump, dumping what we have so far.");
        }
      } catch (InterruptedException e2) {
      }
      try {
        ActivityManager.getService().enableAppFreezer(true);
      } catch (RemoteException e3) {
        Log.m96e("Binder", "RemoteException while re-enabling app freezer");
      }
      Map.Entry<String, Integer>[] sorted =
          (Map.Entry[]) counts.entrySet().toArray(new Map.Entry[counts.size()]);
      Arrays.sort(
          sorted,
          new Comparator() { // from class:
                             // android.os.BinderProxy$ProxyMap$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
              int compareTo;
              compareTo =
                  ((Integer) ((Map.Entry) obj2).getValue())
                      .compareTo((Integer) ((Map.Entry) obj).getValue());
              return compareTo;
            }
          });
      int returnCount = Math.min(maxToReturn, sorted.length);
      InterfaceCount[] ifaceCounts = new InterfaceCount[returnCount];
      for (int i = 0; i < returnCount; i++) {
        ifaceCounts[i] = new InterfaceCount(sorted[i].getKey(), sorted[i].getValue().intValue());
      }
      return ifaceCounts;
    }

    static /* synthetic */ void lambda$getSortedInterfaceCounts$0(
        ArrayList proxiesToQuery, Map counts) {
      String key;
      Iterator it = proxiesToQuery.iterator();
      while (it.hasNext()) {
        WeakReference<BinderProxy> weakRef = (WeakReference) it.next();
        BinderProxy bp = weakRef.get();
        if (bp == null) {
          key = "<cleared weak-ref>";
        } else {
          try {
            key = bp.getInterfaceDescriptor();
            if ((key == null || key.isEmpty()) && !bp.isBinderAlive()) {
              key = "<proxy to dead node>";
            }
          } catch (Throwable th) {
            key = "<exception during getDescriptor>";
          }
        }
        Integer i = (Integer) counts.get(key);
        if (i != null) {
          counts.put(key, Integer.valueOf(i.intValue() + 1));
        } else {
          counts.put(key, 1);
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpProxyInterfaceCounts() {
      InterfaceCount[] sorted = getSortedInterfaceCounts(10);
      Log.m100v("Binder", "BinderProxy descriptor histogram (top " + Integer.toString(10) + "):");
      for (int i = 0; i < sorted.length; i++) {
        Log.m100v("Binder", " #" + (i + 1) + ": " + sorted[i]);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpPerUidProxyCounts() {
      SparseIntArray counts = BinderInternal.nGetBinderProxyPerUidCounts();
      if (counts.size() == 0) {
        return;
      }
      Log.m94d("Binder", "Per Uid Binder Proxy Counts:");
      for (int i = 0; i < counts.size(); i++) {
        int uid = counts.keyAt(i);
        int binderCount = counts.valueAt(i);
        Log.m94d("Binder", "UID : " + uid + "  count = " + binderCount);
      }
    }
  }

  public static final class InterfaceCount {
    private final int mCount;
    private final String mInterfaceName;

    InterfaceCount(String interfaceName, int count) {
      this.mInterfaceName = interfaceName;
      this.mCount = count;
    }

    public String toString() {
      return this.mInterfaceName + " x" + Integer.toString(this.mCount);
    }
  }

  public static InterfaceCount[] getSortedInterfaceCounts(int num) {
    return sProxyMap.getSortedInterfaceCounts(num);
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

  private static BinderProxy getInstance(long nativeData, long iBinder) {
    ProxyMap proxyMap = sProxyMap;
    synchronized (proxyMap) {
      try {
        BinderProxy result = proxyMap.get(iBinder);
        if (result != null) {
          return result;
        }
        BinderProxy result2 = new BinderProxy(nativeData);
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(result2, nativeData);
        proxyMap.set(iBinder, result2);
        return result2;
      } catch (Throwable e) {
        NativeAllocationRegistry.applyFreeFunction(
            NoImagePreloadHolder.sNativeFinalizer, nativeData);
        throw e;
      }
    }
  }

  private BinderProxy(long nativeData) {
    this.mNativeData = nativeData;
  }

  private static class NoImagePreloadHolder {
    public static final long sNativeFinalizer;
    public static final NativeAllocationRegistry sRegistry;

    private NoImagePreloadHolder() {}

    static {
      long nativeFinalizer = BinderProxy.getNativeFinalizer();
      sNativeFinalizer = nativeFinalizer;
      sRegistry =
          new NativeAllocationRegistry(BinderProxy.class.getClassLoader(), nativeFinalizer, 1000L);
    }
  }

  @Override // android.os.IBinder
  public IInterface queryLocalInterface(String descriptor) {
    return null;
  }

  @Override // android.os.IBinder
  public boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
    int flags2;
    Binder.checkParcel(this, code, data, "Unreasonably large binder buffer");
    boolean warnOnBlocking = this.mWarnOnBlocking;
    if (warnOnBlocking
        && (flags & 1) == 0
        && Binder.sWarnOnBlockingOnCurrentThread.get().booleanValue()) {
      this.mWarnOnBlocking = false;
      warnOnBlocking = false;
      if (Build.IS_USERDEBUG || Build.IS_ENG) {
        Log.wtf(
            "Binder",
            "Outgoing transactions from this process must be FLAG_ONEWAY",
            new Throwable());
      } else {
        Log.m103w(
            "Binder",
            "Outgoing transactions from this process must be FLAG_ONEWAY",
            new Throwable());
      }
    }
    boolean tracingEnabled = Binder.isStackTrackingEnabled();
    Throwable tr = null;
    if (tracingEnabled) {
      tr = new Throwable();
      if (!Binder.isSystemServerBinderTrackerEnabled) {
        Binder.getTransactionTracker().addTrace(tr);
      }
      StackTraceElement stackTraceElement = tr.getStackTrace()[1];
      Trace.traceBegin(
          1L,
          stackTraceElement.getClassName()
              + MediaMetrics.SEPARATOR
              + stackTraceElement.getMethodName());
    }
    if (isMsgForGoogleLocation(data)) {
      sendInfoToNSFLP(code, data);
    }
    Binder.ProxyTransactListener transactListener = sTransactListener;
    Object session = null;
    if (transactListener != null) {
      int origWorkSourceUid = Binder.getCallingWorkSourceUid();
      session = transactListener.onTransactStarted(this, code, flags);
      int updatedWorkSourceUid = Binder.getCallingWorkSourceUid();
      if (origWorkSourceUid != updatedWorkSourceUid) {
        data.replaceCallingWorkSourceUid(updatedWorkSourceUid);
      }
    }
    AppOpsManager.PausedNotedAppOpsCollection prevCollection =
        AppOpsManager.pauseNotedAppOpsCollection();
    if ((flags & 1) == 0 && AppOpsManager.isListeningForOpNoted()) {
      flags2 = flags | 2;
    } else {
      flags2 = flags;
    }
    if (Binder.isSystemServerBinderTrackerEnabled && !Binder.isSystemServer) {
      long duration = 0;
      long time = 0;
      boolean issystemserver = false;
      Parcel tempdata = Parcel.obtain();
      Parcel tempreply = Parcel.obtain();
      try {
        String intrfcName = data.getInterfaceName();
        if (intrfcName != null) {
          tempdata.writeInterfaceToken(intrfcName);
        }
        boolean extratrstatus = false;
        try {
          extratrstatus =
              transactNative(IBinder.ISSYSTEMSERVER_TRANSACTION, tempdata, tempreply, 0);
        } catch (SecurityException e) {
        }
        if (extratrstatus) {
          issystemserver = tempreply.readBoolean();
        }
        long time2 = System.currentTimeMillis();
        try {
          long time3 = SystemClock.elapsedRealtimeNanos();
          duration = time3;
          boolean result = transactNative(code, data, reply, flags2);
          if (reply != null && !warnOnBlocking) {
            reply.addFlags(1);
          }
          tempdata.recycle();
          tempreply.recycle();
          if (transactListener != null) {
            transactListener.onTransactEnded(session);
          }
          if (tracingEnabled) {
            if (tr == null) {
              tr = new Throwable();
            }
            if (issystemserver) {
              Binder.getTransactionTracker()
                  .addTimeStamp(
                      tr, time2, SystemClock.elapsedRealtimeNanos() - duration, (flags2 & 1) != 0);
            }
            Trace.traceEnd(1L);
          }
          return result;
        } catch (Throwable th) {
          th = th;
          time = time2;
          tempdata.recycle();
          tempreply.recycle();
          if (transactListener != null) {
            transactListener.onTransactEnded(session);
          }
          if (tracingEnabled) {
            if (tr == null) {
              tr = new Throwable();
            }
            if (issystemserver) {
              Binder.getTransactionTracker()
                  .addTimeStamp(
                      tr, time, SystemClock.elapsedRealtimeNanos() - duration, (flags2 & 1) != 0);
            }
            Trace.traceEnd(1L);
          }
          throw th;
        }
      } catch (Throwable th2) {
        th = th2;
      }
    } else {
      try {
        boolean result2 = transactNative(code, data, reply, flags2);
        if (reply != null && !warnOnBlocking) {
          reply.addFlags(1);
        }
        return result2;
      } finally {
        AppOpsManager.resumeNotedAppOpsCollection(prevCollection);
        if (transactListener != null) {
          transactListener.onTransactEnded(session);
        }
        if (tracingEnabled) {
          Trace.traceEnd(1L);
        }
      }
    }
  }

  @Override // android.os.IBinder
  public void dump(FileDescriptor fd, String[] args) throws RemoteException {
    Parcel data = Parcel.obtain();
    Parcel reply = Parcel.obtain();
    data.writeFileDescriptor(fd);
    data.writeStringArray(args);
    try {
      transact(IBinder.DUMP_TRANSACTION, data, reply, 0);
      reply.readException();
    } finally {
      data.recycle();
      reply.recycle();
    }
  }

  @Override // android.os.IBinder
  public void dumpAsync(FileDescriptor fd, String[] args) throws RemoteException {
    Parcel data = Parcel.obtain();
    Parcel reply = Parcel.obtain();
    data.writeFileDescriptor(fd);
    data.writeStringArray(args);
    try {
      transact(IBinder.DUMP_TRANSACTION, data, reply, 1);
    } finally {
      data.recycle();
      reply.recycle();
    }
  }

  @Override // android.os.IBinder
  public void shellCommand(
      FileDescriptor in,
      FileDescriptor out,
      FileDescriptor err,
      String[] args,
      ShellCallback callback,
      ResultReceiver resultReceiver)
      throws RemoteException {
    Parcel data = Parcel.obtain();
    Parcel reply = Parcel.obtain();
    data.writeFileDescriptor(in);
    data.writeFileDescriptor(out);
    data.writeFileDescriptor(err);
    data.writeStringArray(args);
    ShellCallback.writeToParcel(callback, data);
    resultReceiver.writeToParcel(data, 0);
    try {
      transact(IBinder.SHELL_COMMAND_TRANSACTION, data, reply, 0);
      reply.readException();
    } finally {
      data.recycle();
      reply.recycle();
    }
  }

  private static void sendDeathNotice(IBinder.DeathRecipient recipient, IBinder binderProxy) {
    try {
      recipient.binderDied(binderProxy);
    } catch (RuntimeException exc) {
      Log.m103w("BinderNative", "Uncaught exception from death notification", exc);
    }
  }

  private boolean isMsgForGoogleLocation(Parcel data) {
    int i = this.msgForGoogleLocation;
    if (i == 1) {
      return false;
    }
    if (i == 2) {
      return true;
    }
    this.msgForGoogleLocation = 1;
    if (data == null || !customContains(data.getInterfaceName())) {
      return false;
    }
    this.msgForGoogleLocation = 2;
    return true;
  }

  private boolean customContains(String target) {
    if (target == null || target.length() < 26) {
      return false;
    }
    for (int i = 0; i < 7; i++) {
      if (target.charAt(i + 19) != GMS_SHORT_LOOKUP.charAt(i)) {
        return false;
      }
    }
    return true;
  }

  private void sendInfoToNSFLP(int code, Parcel data) {
    Parcel parcel = Parcel.obtain();
    try {
      try {
        ILocationManager locationManager =
            ILocationManager.Stub.asInterface(ServiceManager.getService("location"));
        if (locationManager != null) {
          parcel.writeInt(data.dataSize());
          parcel.appendFrom(data, 0, data.dataSize());
          parcel.setDataPosition(0);
          ParcelableParcel pp = ParcelableParcel.CREATOR.createFromParcel(parcel);
          Bundle bundle = new Bundle();
          bundle.putParcelable("pp", pp);
          bundle.putString("interfaceName", data.getInterfaceName());
          bundle.putInt("uid", Binder.getCallingUid());
          bundle.putInt(AudioParameter.SUBKEY_HIDDEN_SOUND_PID, Binder.getCallingPid());
          Message msg = new Message();
          msg.what = 200;
          msg.arg1 = code;
          msg.setData(bundle);
          locationManager.notifyNSFLP(msg);
        }
      } catch (Exception e) {
        Log.m102w("Binder_FLP", "failed to send info to nsflp");
        e.printStackTrace();
      }
    } finally {
      parcel.recycle();
    }
  }
}
