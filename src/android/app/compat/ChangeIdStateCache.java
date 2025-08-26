package android.app.compat;

import android.app.PropertyInvalidatedCache;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.compat.IPlatformCompat;

/* loaded from: classes.dex */
public final class ChangeIdStateCache extends PropertyInvalidatedCache<ChangeIdStateQuery, Boolean> {
    private static final String CACHE_API = "is_compat_change_enabled";
    private static final String CACHE_MODULE = "system_server";
    private static final int MAX_ENTRIES = 2048;
    private static boolean sDisabled = getDefaultDisabled();
    private volatile IPlatformCompat mPlatformCompat;

    private static boolean getDefaultDisabled() {
        return false;
    }

    private static boolean getDefaultDisabled$ravenwood() {
        return true;
    }

    public ChangeIdStateCache() {
        super(new PropertyInvalidatedCache.Args("system_server").maxEntries(2048).isolateUids(false).cacheNulls(false).api(CACHE_API), CACHE_API, (PropertyInvalidatedCache.QueryHandler) null);
    }

    public static void disable() {
        sDisabled = true;
    }

    public static void invalidate() {
        if (sDisabled) {
            return;
        }
        PropertyInvalidatedCache.invalidateCache("system_server", CACHE_API);
    }

    IPlatformCompat getPlatformCompatService() {
        IPlatformCompat iPlatformCompatAsInterface;
        IPlatformCompat iPlatformCompat = this.mPlatformCompat;
        if (iPlatformCompat != null) {
            return iPlatformCompat;
        }
        synchronized (this) {
            iPlatformCompatAsInterface = this.mPlatformCompat;
            if (iPlatformCompatAsInterface == null) {
                iPlatformCompatAsInterface = IPlatformCompat.Stub.asInterface(ServiceManager.getService(Context.PLATFORM_COMPAT_SERVICE));
                if (iPlatformCompatAsInterface == null) {
                    throw new RuntimeException("Could not get PlatformCompatService instance!");
                }
                this.mPlatformCompat = iPlatformCompatAsInterface;
            }
        }
        return iPlatformCompatAsInterface;
    }

    @Override // android.app.PropertyInvalidatedCache
    public Boolean recompute(ChangeIdStateQuery changeIdStateQuery) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (changeIdStateQuery.type == 0) {
                    return Boolean.valueOf(getPlatformCompatService().isChangeEnabledByPackageName(changeIdStateQuery.changeId, changeIdStateQuery.packageName, changeIdStateQuery.userId));
                }
                if (changeIdStateQuery.type == 1) {
                    return Boolean.valueOf(getPlatformCompatService().isChangeEnabledByUid(changeIdStateQuery.changeId, changeIdStateQuery.uid));
                }
                throw new IllegalArgumentException("Invalid query type: " + changeIdStateQuery.type);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                throw new IllegalStateException("Could not recompute value!");
            }
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }
}
