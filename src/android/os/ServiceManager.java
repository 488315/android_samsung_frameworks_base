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
        LazyService lazyService = new LazyService(context);
        lazyServiceManager = lazyService;
        try {
            addService(LAZY_SERVICE_NAME, lazyService);
        } catch (Throwable th) {
            Slog.e(TAG, "Failure starting Lazy Service", th);
            lazyServiceManager = null;
        }
    }

    public static void addService(String str, Class cls) {
        if (lazyServiceManager == null) {
            try {
                addService(str, new LazyService.DefaultServiceCreator(cls).createService(_context));
                return;
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "error in addService", e);
                return;
            }
        }
        addService("#LAZY_PRE_ADD#_" + str, dummyBinder);
        lazyServiceManager.addService(str, cls);
    }

    public static void addService(String str, IServiceCreator iServiceCreator) {
        if (lazyServiceManager == null) {
            addService(str, iServiceCreator.createService(_context));
            return;
        }
        addService("#LAZY_PRE_ADD#_" + str, dummyBinder);
        lazyServiceManager.addService(str, iServiceCreator);
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
        IServiceManager iServiceManager = sServiceManager;
        if (iServiceManager != null) {
            return iServiceManager;
        }
        IServiceManager asInterface = ServiceManagerNative.asInterface(Binder.allowBlocking(BinderInternal.getContextObject()));
        sServiceManager = asInterface;
        return asInterface;
    }

    public static IBinder getService(String str) {
        try {
            IBinder iBinder = sCache.get(str);
            return iBinder != null ? iBinder : Binder.allowBlocking(rawGetService(str));
        } catch (RemoteException e) {
            Log.e(TAG, "error in getService", e);
            return null;
        }
    }

    public static IBinder getService$ravenwood(String str) {
        IBinder iBinder;
        synchronized (ServiceManager.class) {
            iBinder = (IBinder) ((Map) Preconditions.requireNonNullViaRavenwoodRule(sCache$ravenwood)).get(str);
        }
        return iBinder;
    }

    public static IBinder getServiceOrThrow(String str) throws ServiceNotFoundException {
        IBinder service = getService(str);
        if (service != null) {
            return service;
        }
        throw new ServiceNotFoundException(str);
    }

    public static void addService(String str, IBinder iBinder) {
        addService(str, iBinder, false, 8);
    }

    public static void addService(String str, IBinder iBinder, boolean z) {
        addService(str, iBinder, z, 8);
    }

    public static void addService(String str, IBinder iBinder, boolean z, int i) {
        try {
            getIServiceManager().addService(str, iBinder, z, i);
        } catch (RemoteException e) {
            Log.e(TAG, "error in addService", e);
        }
    }

    public static void addService$ravenwood(String str, IBinder iBinder, boolean z, int i) {
        synchronized (ServiceManager.class) {
            ((Map) Preconditions.requireNonNullViaRavenwoodRule(sCache$ravenwood)).put(str, iBinder);
        }
    }

    public static IBinder checkService(String str) {
        try {
            IBinder iBinder = sCache.get(str);
            return iBinder != null ? iBinder : Binder.allowBlocking(getIServiceManager().checkService2(str).getServiceWithMetadata().service);
        } catch (RemoteException e) {
            Log.e(TAG, "error in checkService", e);
            return null;
        }
    }

    public static boolean isDeclared(String str) {
        try {
            return getIServiceManager().isDeclared(str);
        } catch (RemoteException | SecurityException e) {
            Log.e(TAG, "error in isDeclared", e);
            return false;
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static String[] getDeclaredInstances(String str) {
        try {
            return getIServiceManager().getDeclaredInstances(str);
        } catch (RemoteException e) {
            Log.e(TAG, "error in getDeclaredInstances", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public static IBinder waitForService(String str) {
        return Binder.allowBlocking(waitForServiceNative(str));
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static IBinder waitForDeclaredService(String str) {
        if (isDeclared(str)) {
            return waitForService(str);
        }
        return null;
    }

    public static void registerForNotifications(String str, IServiceCallback iServiceCallback) throws RemoteException {
        getIServiceManager().registerForNotifications(str, iServiceCallback);
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

    public static void initServiceCache(Map<String, IBinder> map) {
        if (sCache.size() != 0) {
            throw new IllegalStateException("setServiceCache may only be called once");
        }
        sCache.putAll(map);
    }

    public static class ServiceNotFoundException extends Exception {
        public ServiceNotFoundException(String str) {
            super("No service published for: " + str);
        }
    }

    private static IBinder rawGetService(String str) throws RemoteException {
        long j;
        int i;
        StatLogger statLogger = sStatLogger;
        long time = statLogger.getTime();
        IBinder iBinder = getIServiceManager().getService2(str).getServiceWithMetadata().service;
        int logDurationStat = (int) statLogger.logDurationStat(0, time);
        boolean isCore = UserHandle.isCore(Process.myUid());
        if (isCore) {
            j = GET_SERVICE_SLOW_THRESHOLD_US_CORE;
        } else {
            j = GET_SERVICE_SLOW_THRESHOLD_US_NON_CORE;
        }
        synchronized (sLock) {
            sGetServiceAccumulatedUs += logDurationStat;
            sGetServiceAccumulatedCallCount++;
            long uptimeMillis = SystemClock.uptimeMillis();
            long j2 = logDurationStat;
            if (j2 >= j && (uptimeMillis > sLastSlowLogUptime + 5000 || sLastSlowLogActualTime < j2)) {
                EventLogTags.writeServiceManagerSlow(logDurationStat / 1000, str);
                sLastSlowLogUptime = uptimeMillis;
                sLastSlowLogActualTime = j2;
            }
            if (isCore) {
                i = GET_SERVICE_LOG_EVERY_CALLS_CORE;
            } else {
                i = GET_SERVICE_LOG_EVERY_CALLS_NON_CORE;
            }
            int i2 = sGetServiceAccumulatedCallCount;
            if (i2 >= i) {
                long j3 = sLastStatsLogUptime;
                if (uptimeMillis >= 5000 + j3) {
                    EventLogTags.writeServiceManagerStats(i2, sGetServiceAccumulatedUs / 1000, (int) (uptimeMillis - j3));
                    sGetServiceAccumulatedCallCount = 0;
                    sGetServiceAccumulatedUs = 0;
                    sLastStatsLogUptime = uptimeMillis;
                }
            }
        }
        return iBinder;
    }
}
