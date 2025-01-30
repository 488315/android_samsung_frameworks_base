package com.android.server.knox.p010zt.devicetrust;

import android.os.Build;
import android.os.Bundle;
import android.os.IZtdListener;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.ThreadLocalWorkSource;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.knox.p010zt.devicetrust.data.EndpointData;
import com.android.server.knox.p010zt.devicetrust.data.FsData;
import com.android.server.knox.p010zt.devicetrust.data.PktData;
import com.android.server.knox.p010zt.devicetrust.data.ScData;
import com.android.server.knox.p010zt.devicetrust.data.SkData;
import com.samsung.android.knox.p024zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;
import com.samsung.android.server.p025pm.PmServerUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class EndpointMonitorImpl {
  public static final boolean DEBUG = isDebuggableBinary();
  public static final long DEFAULT_INIT_DELAY_MS = 100;
  public static final long DEFAULT_PERIOD_MS = 10;
  public static final String DomainAccessMonitorThreadName = "DomainMonitor";
  public static final String FileAccessMonitorThreadName = "FileMonitor";
  public static final int MAX_SESSION_CNT = 2;
  public static final String SockStateChangeMonitorThreadName = "SocketMonitor";
  public static final String SystemCallMonitorThreadName = "SystemCallMonitor";
  public static final String TAG = "EndpointMonitorImpl";
  public static final String TlsPacketMonitorThreadName = "TlsPacketMonitor";
  public boolean mInitialized;
  public OemNetdAdapterImpl mOemNetdAdapterImpl;
  public Map mSessions;
  public final Object mSessionsLock = new Object();
  public final long mBootTimeNanos =
      (System.currentTimeMillis() * 1000000) - SystemClock.elapsedRealtimeNanos();

  public native ArrayList nativeReadFsData();

  public native ArrayList nativeReadPktData();

  public native ArrayList nativeReadScData();

  public native ArrayList nativeReadSkData();

  public native int nativeSetBpfHelper(OemNetdAdapter oemNetdAdapter);

  public native int nativeSetTargetFiles(ArrayList arrayList, ArrayList arrayList2);

  public native int nativeSetTracer(int i);

  public native int nativeStartDpTracing();

  public native int nativeStartTracing(int i);

  public native int nativeStopTracing(int i);

  public final synchronized void ensureInitialized() {
    if (!this.mInitialized) {
      this.mSessions = new HashMap();
      this.mOemNetdAdapterImpl = new OemNetdAdapterImpl();
      this.mInitialized = true;
      Log.d(TAG, "Lazily initialized");
    }
  }

  public final int setBpfHelper(OemNetdAdapter oemNetdAdapter) {
    try {
      return nativeSetBpfHelper(oemNetdAdapter);
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      return -1;
    }
  }

  public final long calculateEventTime(long j) {
    return (this.mBootTimeNanos + j) / 1000000;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$createMonitorRunnable$0(int i) {
    onFileAccessDetected(i, readFsData());
  }

  public final Runnable createMonitorRunnable(int i, final int i2) {
    if (i == 2) {
      return new Runnable() { // from class:
                              // com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$$ExternalSyntheticLambda4
        @Override // java.lang.Runnable
        public final void run() {
          EndpointMonitorImpl.this.lambda$createMonitorRunnable$0(i2);
        }
      };
    }
    if (i == 6) {
      return null;
    }
    if (i == 3) {
      return new Runnable() { // from class:
                              // com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$$ExternalSyntheticLambda5
        @Override // java.lang.Runnable
        public final void run() {
          EndpointMonitorImpl.this.lambda$createMonitorRunnable$1(i2);
        }
      };
    }
    if (i == 1) {
      return new Runnable() { // from class:
                              // com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$$ExternalSyntheticLambda6
        @Override // java.lang.Runnable
        public final void run() {
          EndpointMonitorImpl.this.lambda$createMonitorRunnable$2(i2);
        }
      };
    }
    if (i == 5) {
      return new Runnable() { // from class:
                              // com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$$ExternalSyntheticLambda7
        @Override // java.lang.Runnable
        public final void run() {
          EndpointMonitorImpl.this.lambda$createMonitorRunnable$3(i2);
        }
      };
    }
    return null;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$createMonitorRunnable$1(int i) {
    onSocketStateChanged(i, readSkData());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$createMonitorRunnable$2(int i) {
    onSystemCallDetected(i, readScData());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$createMonitorRunnable$3(int i) {
    onTlsPacketDetected(i, readPktData());
  }

  public final ThreadFactory createMonitorFactory(int i) {
    final String str =
        i == 2
            ? FileAccessMonitorThreadName
            : i == 6
                ? DomainAccessMonitorThreadName
                : i == 3
                    ? SockStateChangeMonitorThreadName
                    : i == 1
                        ? SystemCallMonitorThreadName
                        : i == 5 ? TlsPacketMonitorThreadName : "Nop";
    return new ThreadFactory() { // from class:
                                 // com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$$ExternalSyntheticLambda1
      @Override // java.util.concurrent.ThreadFactory
      public final Thread newThread(Runnable runnable) {
        Thread lambda$createMonitorFactory$5;
        lambda$createMonitorFactory$5 =
            EndpointMonitorImpl.lambda$createMonitorFactory$5(str, runnable);
        return lambda$createMonitorFactory$5;
      }
    };
  }

  public static /* synthetic */ Thread lambda$createMonitorFactory$5(
      String str, final Runnable runnable) {
    Log.i(TAG, "Monitor created : " + str);
    Thread thread =
        new Thread(
            new Runnable() { // from class:
                             // com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                EndpointMonitorImpl.lambda$createMonitorFactory$4(runnable);
              }
            },
            str);
    thread.setPriority(5);
    thread.setDaemon(true);
    return thread;
  }

  public static /* synthetic */ void lambda$createMonitorFactory$4(Runnable runnable) {
    ThreadLocalWorkSource.setUid(Process.myUid());
    runnable.run();
  }

  public final MonitorSession getMonitorSessionLocked(int i, int i2) {
    if (i == 1) {
      return getSystemCallMonitorSessionLocked(i2);
    }
    if (i == 2) {
      return getFileMonitorSessionLocked(i2);
    }
    if (i == 3) {
      return getSocketMonitorSessionLocked(i2);
    }
    if (i == 5) {
      return getTlsPacketMonitorSessionLocked(i2);
    }
    if (i != 6) {
      return null;
    }
    return getDomainMonitorSessionLocked(i2);
  }

  public final MonitorSession getFileMonitorSessionLocked(int i) {
    MonitorSession monitorSession = (MonitorSession) this.mSessions.get(Integer.valueOf(i));
    if (monitorSession == null || monitorSession.type != 2) {
      return null;
    }
    return monitorSession;
  }

  public final MonitorSession getDomainMonitorSessionLocked(int i) {
    MonitorSession monitorSession = (MonitorSession) this.mSessions.get(Integer.valueOf(i));
    if (monitorSession == null || monitorSession.type != 6) {
      return null;
    }
    return monitorSession;
  }

  public final MonitorSession getSocketMonitorSessionLocked(int i) {
    MonitorSession monitorSession = (MonitorSession) this.mSessions.get(Integer.valueOf(i));
    if (monitorSession == null || monitorSession.type != 3) {
      return null;
    }
    return monitorSession;
  }

  public final MonitorSession getSystemCallMonitorSessionLocked(int i) {
    MonitorSession monitorSession = (MonitorSession) this.mSessions.get(Integer.valueOf(i));
    if (monitorSession == null || monitorSession.type != 1) {
      return null;
    }
    return monitorSession;
  }

  public final MonitorSession getTlsPacketMonitorSessionLocked(int i) {
    MonitorSession monitorSession = (MonitorSession) this.mSessions.get(Integer.valueOf(i));
    if (monitorSession == null || monitorSession.type != 5) {
      return null;
    }
    return monitorSession;
  }

  public class MonitorSession {
    public final Set allowedUids = new HashSet();
    public final ScheduledExecutorService executor;
    public final int extras;
    public final Predicate filter;
    public final int flags;
    public final IEndpointMonitorListener listener;
    public final int mode;
    public final Runnable monitor;
    public final int requestorUid;
    public final Map targets;
    public final int type;
    public final IZtdListener uadListener;

    public static /* synthetic */ boolean lambda$new$0(EndpointData endpointData) {
      return true;
    }

    public int startLocked() {
      if (this.monitor == null) {
        return -2;
      }
      EndpointMonitorImpl.this.mSessions.put(Integer.valueOf(this.requestorUid), this);
      this.executor.scheduleAtFixedRate(this.monitor, 100L, 10L, TimeUnit.MILLISECONDS);
      return 0;
    }

    public void onEvent(EndpointData endpointData) {
      if (this.filter.test(endpointData)) {
        int i = this.mode;
        if (i == 1) {
          this.listener.onEventSimplified(
              this.type,
              endpointData
                  .adjustTime(EndpointMonitorImpl.this.mBootTimeNanos)
                  .updateExtras(this.extras)
                  .toLine());
        } else if (i == 2) {
          this.listener.onEventGeneralized(
              this.type,
              endpointData
                  .adjustTime(EndpointMonitorImpl.this.mBootTimeNanos)
                  .updateExtras(this.extras)
                  .toJson());
        } else {
          if (i != 3) {
            return;
          }
          this.listener.onEvent(
              this.type,
              endpointData
                  .adjustTime(EndpointMonitorImpl.this.mBootTimeNanos)
                  .updateExtras(this.extras)
                  .toBundle());
        }
      }
    }

    public MonitorSession(
        int i,
        int i2,
        int[] iArr,
        Map map,
        IZtdListener iZtdListener,
        IEndpointMonitorListener iEndpointMonitorListener,
        Predicate predicate,
        int i3,
        int i4,
        int i5) {
      this.type = i;
      this.requestorUid = i2;
      if (iArr != null) {
        for (int i6 : iArr) {
          this.allowedUids.add(Integer.valueOf(i6));
        }
      }
      this.targets = map;
      this.uadListener = iZtdListener;
      this.listener = iEndpointMonitorListener;
      this.filter =
          predicate == null
              ? new Predicate() { // from class:
                                  // com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$MonitorSession$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$new$0;
                  lambda$new$0 =
                      EndpointMonitorImpl.MonitorSession.lambda$new$0((EndpointData) obj);
                  return lambda$new$0;
                }
              }
              : predicate;
      this.executor =
          Executors.newSingleThreadScheduledExecutor(
              EndpointMonitorImpl.this.createMonitorFactory(i));
      this.monitor = EndpointMonitorImpl.this.createMonitorRunnable(i, i2);
      this.flags = i3;
      this.extras = i4;
      this.mode = i5;
    }
  }

  public final Map createTargetFiles(List list, List list2) {
    if (list == null || list2 == null || list.size() != list2.size() || list.size() == 0) {
      Log.e(TAG, "Failed to create target files due to invalid args");
      return null;
    }
    HashMap hashMap = new HashMap();
    for (int i = 0; i < list.size(); i++) {
      try {
        String ensureNotNull = ensureNotNull((String) list2.get(i));
        hashMap.put(
            Long.valueOf(Long.parseLong(ensureNotNull)), ensureNotNull((String) list.get(i)));
      } catch (RuntimeException e) {
        e.printStackTrace();
        return null;
      }
    }
    return hashMap;
  }

  public int startTracing(
      int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) {
    int i3;
    ensureInitialized();
    String str = TAG;
    Log.i(str, "startTracing() - type : " + i + ", reqId : " + i2);
    if (bundle != null
        && iEndpointMonitorListener != null
        && EndpointMonitorConst.validateTraceType(i)) {
      if (i == 1) {
        i3 = bundle.getInt("flags", 0);
      } else {
        i3 = i == 3 ? 32 : i == 2 ? 1 : i == 5 ? 64 : 0;
      }
      if (i3 <= 0) {
        return -2;
      }
      int i4 = bundle.getInt("extras", 0);
      int i5 = bundle.getInt("mode", 3);
      if (i5 >= 1 && i5 <= 3) {
        Predicate predicate =
            bundle.getBoolean(EndpointMonitorConst.OPT_TRACE_APPLICATION_ONLY, false)
                ? new Predicate() { // from class:
                                    // com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$$ExternalSyntheticLambda2
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean lambda$startTracing$6;
                    lambda$startTracing$6 =
                        EndpointMonitorImpl.lambda$startTracing$6((EndpointData) obj);
                    return lambda$startTracing$6;
                  }
                }
                : null;
        synchronized (this.mSessionsLock) {
          if (getMonitorSessionLocked(i, i2) != null) {
            Log.e(str, "Failed :: Session is already opened");
            return -4;
          }
          if (this.mSessions.size() >= 2) {
            Log.e(str, "Failed :: Session pool is full");
            return -3;
          }
          int prepare = prepare(i3, i2);
          if (prepare != 0) {
            Log.e(str, "prepare(" + prepare + ")");
            return -5;
          }
          int startTracing = startTracing(i3);
          if (startTracing != 0) {
            Log.e(str, "startTracing(" + startTracing + ")");
            return -5;
          }
          return createMonitorSessionForEpm(i, i2, i3, i4, i5, iEndpointMonitorListener, predicate)
              .startLocked();
        }
      }
    }
    return -2;
  }

  public static /* synthetic */ boolean lambda$startTracing$6(EndpointData endpointData) {
    return UserHandle.isApp(endpointData.getUid());
  }

  public final int prepare(int i, int i2) {
    if ((i & 2) > 0 || (i & 4) > 0) {
      return setTracer(i2);
    }
    if ((i & 64) > 0) {
      return this.mOemNetdAdapterImpl.attachProbes(i);
    }
    return 0;
  }

  public final int reset(int i) {
    if ((i & 64) > 0) {
      return this.mOemNetdAdapterImpl.detachProbes(i);
    }
    return 0;
  }

  public final int startTracing(int i) {
    try {
      return nativeStartTracing(i);
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      return -1;
    }
  }

  public final int setTracer(int i) {
    try {
      return nativeSetTracer(i);
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      return -1;
    }
  }

  public final MonitorSession createMonitorSessionForUad(
      int i, int i2, int[] iArr, Map map, IZtdListener iZtdListener) {
    return createMonitorSession(i, i2, iArr, map, iZtdListener, null, null, 0, 0, 0);
  }

  public final MonitorSession createMonitorSessionForEpm(
      int i,
      int i2,
      int i3,
      int i4,
      int i5,
      IEndpointMonitorListener iEndpointMonitorListener,
      Predicate predicate) {
    return createMonitorSession(
        i, i2, new int[0], new HashMap(), null, iEndpointMonitorListener, predicate, i3, i4, i5);
  }

  public final MonitorSession createMonitorSession(
      int i,
      int i2,
      int[] iArr,
      Map map,
      IZtdListener iZtdListener,
      IEndpointMonitorListener iEndpointMonitorListener,
      Predicate predicate,
      int i3,
      int i4,
      int i5) {
    return new MonitorSession(
        i, i2, iArr, map, iZtdListener, iEndpointMonitorListener, predicate, i3, i4, i5);
  }

  public int stopTracing(int i, int i2) {
    ensureInitialized();
    String str = TAG;
    Log.i(str, "stopTracing() - type : " + i + ", reqId : " + i2);
    if (!EndpointMonitorConst.validateTraceType(i)) {
      return -2;
    }
    synchronized (this.mSessionsLock) {
      MonitorSession monitorSessionLocked = getMonitorSessionLocked(i, i2);
      if (monitorSessionLocked == null) {
        Log.e(str, "Session not found");
        return -4;
      }
      closeSessionLocked(monitorSessionLocked);
      int reset = reset(monitorSessionLocked.flags);
      if (reset != 0) {
        Log.e(str, "reset(" + reset + ")");
      }
      int i3 = 1;
      if (i == 1) {
        i3 = 30;
      } else if (i != 2) {
        i3 = i == 3 ? 32 : i == 5 ? 64 : 0;
      }
      Log.i(str, "stopTracing() - rc : " + stopTracing(i3));
      return 0;
    }
  }

  public final int stopTracing(int i) {
    try {
      return nativeStopTracing(i);
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      return -1;
    }
  }

  public void startMonitoringFiles(
      final int i, int[] iArr, List list, List list2, IZtdListener iZtdListener) {
    ensureInitialized();
    String str = TAG;
    Log.i(str, "startMonitoringFiles() - reqId : " + i);
    if (iZtdListener == null || !(list instanceof ArrayList) || !(list2 instanceof ArrayList)) {
      Log.e(str, "Failed :: Invalid argument");
      return;
    }
    synchronized (this.mSessionsLock) {
      if (getFileMonitorSessionLocked(i) != null) {
        Log.e(str, "Failed :: Session is already opened");
        return;
      }
      if (this.mSessions.size() >= 2) {
        Log.e(str, "Failed :: Session pool is full");
        return;
      }
      Iterator it = list.iterator();
      while (it.hasNext()) {
        String str2 = (String) it.next();
        Log.d(TAG, "startMonitoringFiles() - file  : " + str2);
      }
      Iterator it2 = list2.iterator();
      while (it2.hasNext()) {
        String str3 = (String) it2.next();
        Log.d(TAG, "startMonitoringFiles() - inode : " + str3);
      }
      Map createTargetFiles = createTargetFiles(list, list2);
      if (createTargetFiles == null) {
        Log.d(TAG, "Failed :: Invalid targets");
        return;
      }
      int targetFiles = setTargetFiles((ArrayList) list, (ArrayList) list2);
      if (targetFiles != 0) {
        Log.e(TAG, "Failed :: setTargetFiles(" + targetFiles + ")");
        return;
      }
      int startFsTracing = startFsTracing();
      if (startFsTracing != 0) {
        Log.d(TAG, "startMonitoringFiles() - startFsTracing(" + startFsTracing + ")");
        return;
      }
      MonitorSession createMonitorSessionForUad =
          createMonitorSessionForUad(2, i, iArr, createTargetFiles, iZtdListener);
      this.mSessions.put(Integer.valueOf(i), createMonitorSessionForUad);
      createMonitorSessionForUad.executor.scheduleAtFixedRate(
          new Runnable() { // from class:
                           // com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              EndpointMonitorImpl.this.lambda$startMonitoringFiles$7(i);
            }
          },
          100L,
          10L,
          TimeUnit.MILLISECONDS);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startMonitoringFiles$7(int i) {
    onFileAccessDetected(i, readFsData());
  }

  public void stopMonitoringFiles(int i) {
    ensureInitialized();
    String str = TAG;
    Log.i(str, "stopMonitoringFiles() - reqId : " + i);
    synchronized (this.mSessionsLock) {
      MonitorSession fileMonitorSessionLocked = getFileMonitorSessionLocked(i);
      if (fileMonitorSessionLocked == null) {
        Log.e(str, "Session not found");
      } else {
        closeSessionLocked(fileMonitorSessionLocked);
      }
    }
  }

  public final void onFileAccessDetected(int i, ArrayList arrayList) {
    MonitorSession fileMonitorSessionLocked;
    if (arrayList == null || arrayList.size() == 0) {
      if (DEBUG) {
        Log.d(TAG, "onFileAccessDetected(" + i + ") :: Nothing detected");
        return;
      }
      return;
    }
    synchronized (this.mSessionsLock) {
      fileMonitorSessionLocked = getFileMonitorSessionLocked(i);
    }
    if (fileMonitorSessionLocked == null) {
      Log.e(TAG, "onFileAccessDetected(" + i + ") :: Lost session");
      return;
    }
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      FsData fsData = (FsData) it.next();
      if (fileMonitorSessionLocked.targets.containsKey(Long.valueOf(fsData.ino))) {
        int uid = fsData.getUid();
        if (fileMonitorSessionLocked.allowedUids.contains(Integer.valueOf(uid))) {
          if (DEBUG) {
            Log.d(TAG, "onFileAccessDetected(" + i + ") :: Do not handle allowed app(" + uid + ")");
          }
        } else {
          int pid = fsData.getPid();
          try {
            fileMonitorSessionLocked.uadListener.onUnauthorizedAccessDetected(
                1,
                2,
                fsData.getEvent(),
                calculateEventTime(fsData.getTime()),
                uid,
                pid,
                getProcessName(pid),
                (String) fileMonitorSessionLocked.targets.get(Long.valueOf(fsData.ino)));
          } catch (RemoteException e) {
            onFailed(i, fileMonitorSessionLocked.type, "Binder died", e);
            closeSessionLocked(fileMonitorSessionLocked);
          }
        }
      }
    }
  }

  public final void closeSessionLocked(MonitorSession monitorSession) {
    Log.i(
        TAG,
        "closeSession() - reqId : "
            + monitorSession.requestorUid
            + ", type : "
            + monitorSession.type);
    monitorSession.executor.shutdownNow();
    this.mSessions.remove(Integer.valueOf(monitorSession.requestorUid));
  }

  public final void onFailed(int i, int i2, String str, Exception exc) {
    Log.e(TAG, "onFailed() - reqId : " + i + ", type : " + i2 + ", reason : " + str);
    if (exc != null) {
      exc.printStackTrace();
    }
  }

  public final int setTargetFiles(ArrayList arrayList, ArrayList arrayList2) {
    try {
      return nativeSetTargetFiles(arrayList, arrayList2);
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      return -1;
    }
  }

  public final int startFsTracing() {
    try {
      return nativeStartTracing(1);
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      return -1;
    }
  }

  public final ArrayList readFsData() {
    try {
      return nativeReadFsData();
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      return null;
    }
  }

  public void startMonitoringDomains(int i, int[] iArr, List list, IZtdListener iZtdListener) {
    ensureInitialized();
    String str = TAG;
    Log.i(str, "startMonitoringDomains() - reqId : " + i);
    if (iZtdListener == null) {
      Log.e(str, "Failed :: Invalid argument");
      return;
    }
    synchronized (this.mSessionsLock) {
      if (getDomainMonitorSessionLocked(i) != null) {
        Log.e(str, "Failed :: Session is already opened");
        return;
      }
      if (this.mSessions.size() >= 2) {
        Log.e(str, "Failed :: Session pool is full");
        return;
      }
      Iterator it = list.iterator();
      while (it.hasNext()) {
        String str2 = (String) it.next();
        Log.d(TAG, "startMonitoringDomains() - domain : " + str2);
      }
      int startDpTracing = startDpTracing();
      if (startDpTracing != 0) {
        Log.d(TAG, "startMonitoringDomains() - startDpTracing(" + startDpTracing + ")");
      }
    }
  }

  public final int startDpTracing() {
    try {
      return nativeStartDpTracing();
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      return -1;
    }
  }

  public void stopMonitoringDomains(int i) {
    ensureInitialized();
    String str = TAG;
    Log.i(str, "stopMonitoringDomains() - reqId : " + i);
    synchronized (this.mSessionsLock) {
      MonitorSession domainMonitorSessionLocked = getDomainMonitorSessionLocked(i);
      if (domainMonitorSessionLocked == null) {
        Log.e(str, "Session not found");
      } else {
        domainMonitorSessionLocked.executor.shutdown();
        this.mSessions.remove(Integer.valueOf(i));
      }
    }
  }

  public final ArrayList readSkData() {
    try {
      return nativeReadSkData();
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      return null;
    }
  }

  public final void onSocketStateChanged(int i, ArrayList arrayList) {
    MonitorSession socketMonitorSessionLocked;
    if (arrayList == null || arrayList.size() == 0) {
      if (DEBUG) {
        Log.d(TAG, "onSocketStateChanged(" + i + ") :: Nothing detected");
        return;
      }
      return;
    }
    synchronized (this.mSessionsLock) {
      socketMonitorSessionLocked = getSocketMonitorSessionLocked(i);
    }
    if (socketMonitorSessionLocked == null) {
      Log.e(TAG, "onSocketStateChanged(" + i + ") :: Lost session");
      return;
    }
    try {
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        socketMonitorSessionLocked.onEvent((SkData) it.next());
      }
    } catch (RemoteException unused) {
      Log.e(TAG, "onSocketStateChanged() - Failed in binder transaction");
    }
  }

  public final ArrayList readScData() {
    try {
      return nativeReadScData();
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      return null;
    }
  }

  public final void onSystemCallDetected(int i, ArrayList arrayList) {
    MonitorSession systemCallMonitorSessionLocked;
    if (arrayList == null || arrayList.size() == 0) {
      if (DEBUG) {
        Log.d(TAG, "onSystemCallDetected(" + i + ") :: Nothing detected");
        return;
      }
      return;
    }
    synchronized (this.mSessionsLock) {
      systemCallMonitorSessionLocked = getSystemCallMonitorSessionLocked(i);
    }
    if (systemCallMonitorSessionLocked == null) {
      Log.e(TAG, "onSystemCallDetected(" + i + ") :: Lost session");
      return;
    }
    try {
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        ScData scData = (ScData) it.next();
        if (EndpointMonitorConst.matchScEventToScFlags(
            scData.getEvent(), systemCallMonitorSessionLocked.flags)) {
          systemCallMonitorSessionLocked.onEvent(scData);
        }
      }
    } catch (RemoteException unused) {
      Log.e(TAG, "onSystemCallDetected() - Failed in binder transaction");
    }
  }

  public final ArrayList readPktData() {
    try {
      return nativeReadPktData();
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      return null;
    }
  }

  public final void onTlsPacketDetected(int i, ArrayList arrayList) {
    MonitorSession tlsPacketMonitorSessionLocked;
    if (arrayList == null || arrayList.size() == 0) {
      if (DEBUG) {
        Log.d(TAG, "onTlsPacketDetected(" + i + ") :: Nothing detected");
        return;
      }
      return;
    }
    synchronized (this.mSessionsLock) {
      tlsPacketMonitorSessionLocked = getTlsPacketMonitorSessionLocked(i);
    }
    if (tlsPacketMonitorSessionLocked == null) {
      Log.e(TAG, "onTlsPacketDetected(" + i + ") :: Lost session");
      return;
    }
    try {
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        tlsPacketMonitorSessionLocked.onEvent((PktData) it.next());
      }
    } catch (RemoteException unused) {
      Log.e(TAG, "onTlsPacketDetected() - Failed in binder transaction");
    }
  }

  public static String getProcessName(int i) {
    return PmServerUtils.getProcessNameForPid(i);
  }

  public static String ensureNotNull(String str) {
    if (str != null) {
      return str;
    }
    throw new IllegalArgumentException("Argument must not be null");
  }

  public static boolean isDebuggableBinary() {
    String str = Build.TYPE;
    return "eng".equals(str) || "userdebug".equals(str);
  }
}
