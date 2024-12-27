package android.os;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.LazyService;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import com.android.internal.os.BinderInternal;
import com.android.internal.util.Preconditions;
import com.android.internal.util.StatLogger;
import java.util.Map;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public final class ServiceManager {
    public static final String LAZY_SERVICE_NAME = "lazy_service";
    private static final int SLOW_LOG_INTERVAL_MS = 5000;
    private static final int STATS_LOG_INTERVAL_MS = 5000;
    private static final String TAG = "ServiceManager";
    private static Map<String, IBinder> sCache$ravenwood;
    private static int sGetServiceAccumulatedCallCount;
    private static int sGetServiceAccumulatedUs;
    private static long sLastSlowLogActualTime;
    private static long sLastSlowLogUptime;
    private static long sLastStatsLogUptime;
    private static IServiceManager sServiceManager;
    private static final Object sLock = new Object();
    private static Map<String, IBinder> sCache = new ArrayMap();
    private static final Binder dummyBinder = new Binder();
    private static final long GET_SERVICE_SLOW_THRESHOLD_US_CORE = SystemProperties.getInt("debug.servicemanager.slow_call_core_ms", 10) * 1000;
    private static final long GET_SERVICE_SLOW_THRESHOLD_US_NON_CORE = SystemProperties.getInt("debug.servicemanager.slow_call_ms", 50) * 1000;
    private static final int GET_SERVICE_LOG_EVERY_CALLS_CORE = SystemProperties.getInt("debug.servicemanager.log_calls_core", 100);
    private static final int GET_SERVICE_LOG_EVERY_CALLS_NON_CORE = SystemProperties.getInt("debug.servicemanager.log_calls", 200);
    public static final StatLogger sStatLogger = new StatLogger(new String[]{"getService()"});
    private static LazyService lazyServiceManager = null;
    private static Context _context = null;

    interface Stats {
        public static final int COUNT = 1;
        public static final int GET_SERVICE = 0;
    }

    private static native IBinder waitForServiceNative(String str);

    public void initLazyServiceManager(Context context) {
        _context = context;
        lazyServiceManager = new LazyService(context);
        try {
            addService(LAZY_SERVICE_NAME, lazyServiceManager);
        } catch (Throwable e) {
            Slog.e(TAG, "Failure starting Lazy Service", e);
            lazyServiceManager = null;
        }
    }

    public static void addService(String name, Class type) {
        if (lazyServiceManager == null) {
            try {
                LazyService.DefaultServiceCreator creator = new LazyService.DefaultServiceCreator(type);
                addService(name, creator.createService(_context));
                return;
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "error in addService", e);
                return;
            }
        }
        addService("#LAZY_PRE_ADD#_" + name, dummyBinder);
        lazyServiceManager.addService(name, type);
    }

    public static void addService(String name, IServiceCreator creator) {
        if (lazyServiceManager == null) {
            addService(name, creator.createService(_context));
        } else {
            addService("#LAZY_PRE_ADD#_" + name, dummyBinder);
            lazyServiceManager.addService(name, creator);
        }
    }

    public static void init$ravenwood() {
        synchronized (ServiceManager.class) {
            sCache$ravenwood = new ArrayMap();
        }
    }

    public static void reset$ravenwood() {
        synchronized (ServiceManager.class) {
            sCache$ravenwood.clear();
            sCache$ravenwood = null;
        }
    }

    private static IServiceManager getIServiceManager() {
        if (sServiceManager != null) {
            return sServiceManager;
        }
        sServiceManager = ServiceManagerNative.asInterface(Binder.allowBlocking(BinderInternal.getContextObject()));
        return sServiceManager;
    }

    public static IBinder getService(String name) {
        try {
            IBinder service = sCache.get(name);
            if (service != null) {
                return service;
            }
            return Binder.allowBlocking(rawGetService(name));
        } catch (RemoteException e) {
            Log.e(TAG, "error in getService", e);
            return null;
        }
    }

    public static IBinder getService$ravenwood(String name) {
        IBinder iBinder;
        synchronized (ServiceManager.class) {
            iBinder = (IBinder) ((Map) Preconditions.requireNonNullViaRavenwoodRule(sCache$ravenwood)).get(name);
        }
        return iBinder;
    }

    public static IBinder getServiceOrThrow(String name) throws ServiceNotFoundException {
        IBinder binder = getService(name);
        if (binder != null) {
            return binder;
        }
        throw new ServiceNotFoundException(name);
    }

    public static void addService(String name, IBinder service) {
        addService(name, service, false, 8);
    }

    public static void addService(String name, IBinder service, boolean allowIsolated) {
        addService(name, service, allowIsolated, 8);
    }

    public static void addService(String name, IBinder service, boolean allowIsolated, int dumpPriority) {
        try {
            getIServiceManager().addService(name, service, allowIsolated, dumpPriority);
        } catch (RemoteException e) {
            Log.e(TAG, "error in addService", e);
        }
    }

    public static void addService$ravenwood(String name, IBinder service, boolean allowIsolated, int dumpPriority) {
        synchronized (ServiceManager.class) {
            ((Map) Preconditions.requireNonNullViaRavenwoodRule(sCache$ravenwood)).put(name, service);
        }
    }

    public static IBinder checkService(String name) {
        try {
            IBinder service = sCache.get(name);
            if (service != null) {
                return service;
            }
            return Binder.allowBlocking(getIServiceManager().checkService(name));
        } catch (RemoteException e) {
            Log.e(TAG, "error in checkService", e);
            return null;
        }
    }

    public static boolean isDeclared(String name) {
        try {
            return getIServiceManager().isDeclared(name);
        } catch (RemoteException | SecurityException e) {
            Log.e(TAG, "error in isDeclared", e);
            return false;
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static String[] getDeclaredInstances(String iface) {
        try {
            return getIServiceManager().getDeclaredInstances(iface);
        } catch (RemoteException e) {
            Log.e(TAG, "error in getDeclaredInstances", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public static IBinder waitForService(String name) {
        return Binder.allowBlocking(waitForServiceNative(name));
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static IBinder waitForDeclaredService(String name) {
        if (isDeclared(name)) {
            return waitForService(name);
        }
        return null;
    }

    public static void registerForNotifications(String name, IServiceCallback callback) throws RemoteException {
        getIServiceManager().registerForNotifications(name, callback);
    }

    public static String[] listServices() {
        try {
            return getIServiceManager().listServices(15);
        } catch (RemoteException e) {
            Log.e(TAG, "error in listServices", e);
            return null;
        }
    }

    public static ServiceDebugInfo[] getServiceDebugInfo() {
        try {
            return getIServiceManager().getServiceDebugInfo();
        } catch (RemoteException e) {
            Log.e(TAG, "error in getServiceDebugInfo", e);
            return null;
        }
    }

    public static void initServiceCache(Map<String, IBinder> cache) {
        if (sCache.size() != 0) {
            throw new IllegalStateException("setServiceCache may only be called once");
        }
        sCache.putAll(cache);
    }

    public static class ServiceNotFoundException extends Exception {
        public ServiceNotFoundException(String name) {
            super("No service published for: " + name);
        }
    }

    private static IBinder rawGetService(String name) throws RemoteException {
        long slowThreshold;
        int logInterval;
        long start = sStatLogger.getTime();
        IBinder binder = getIServiceManager().getService(name);
        int time = (int) sStatLogger.logDurationStat(0, start);
        int myUid = Process.myUid();
        boolean isCore = UserHandle.isCore(myUid);
        if (isCore) {
            slowThreshold = GET_SERVICE_SLOW_THRESHOLD_US_CORE;
        } else {
            slowThreshold = GET_SERVICE_SLOW_THRESHOLD_US_NON_CORE;
        }
        synchronized (sLock) {
            try {
                try {
                    sGetServiceAccumulatedUs += time;
                    sGetServiceAccumulatedCallCount++;
                    long nowUptime = SystemClock.uptimeMillis();
                    if (time >= slowThreshold) {
                        try {
                            if (nowUptime > sLastSlowLogUptime + 5000 || sLastSlowLogActualTime < time) {
                                EventLogTags.writeServiceManagerSlow(time / 1000, name);
                                sLastSlowLogUptime = nowUptime;
                                sLastSlowLogActualTime = time;
                            }
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                    if (isCore) {
                        logInterval = GET_SERVICE_LOG_EVERY_CALLS_CORE;
                    } else {
                        logInterval = GET_SERVICE_LOG_EVERY_CALLS_NON_CORE;
                    }
                    if (sGetServiceAccumulatedCallCount >= logInterval && nowUptime >= sLastStatsLogUptime + 5000) {
                        EventLogTags.writeServiceManagerStats(sGetServiceAccumulatedCallCount, sGetServiceAccumulatedUs / 1000, (int) (nowUptime - sLastStatsLogUptime));
                        sGetServiceAccumulatedCallCount = 0;
                        sGetServiceAccumulatedUs = 0;
                        sLastStatsLogUptime = nowUptime;
                    }
                    return binder;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }
}
