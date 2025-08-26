package android.os;

import android.annotation.SystemApi;
import android.app.PropertyInvalidatedCache;
import android.multiuser.Flags;
import android.util.ArraySet;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class IpcDataCache<Query, Result> extends PropertyInvalidatedCache<Query, Result> {

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final String MODULE_BLUETOOTH = "bluetooth";
    public static final String MODULE_SYSTEM = "system_server";
    public static final String MODULE_TEST = "test";

    public interface BypassCall<Query> {
        Boolean apply(Query query);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IpcDataCacheModule {
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static abstract class QueryHandler<Q, R> extends PropertyInvalidatedCache.QueryHandler<Q, R> {
        @Override // android.app.PropertyInvalidatedCache.QueryHandler
        public abstract R apply(Q q);

        @Override // android.app.PropertyInvalidatedCache.QueryHandler
        public boolean shouldBypassCache(Q q) {
            return false;
        }
    }

    public interface RemoteCall<Query, Result> {
        Result apply(Query query) throws RemoteException;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public IpcDataCache(int i, String str, String str2, String str3, QueryHandler<Query, Result> queryHandler) {
        super(new PropertyInvalidatedCache.Args(str).maxEntries(i).api(str2), str3, queryHandler);
    }

    @Override // android.app.PropertyInvalidatedCache
    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void disableForCurrentProcess() {
        super.disableForCurrentProcess();
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static void disableForCurrentProcess(String str) {
        PropertyInvalidatedCache.disableForCurrentProcess(str);
    }

    @Override // android.app.PropertyInvalidatedCache
    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public Result query(Query query) {
        return (Result) super.query(query);
    }

    @Override // android.app.PropertyInvalidatedCache
    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void invalidateCache() {
        super.invalidateCache();
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static void invalidateCache(String str, String str2) {
        PropertyInvalidatedCache.invalidateCache(str, str2);
    }

    public static class Config {
        final PropertyInvalidatedCache.Args mArgs;
        private ArraySet<String> mChildren;
        private boolean mDisabled;
        final String mName;

        private Config(PropertyInvalidatedCache.Args args, String str) {
            this.mDisabled = false;
            this.mArgs = args;
            this.mName = str;
        }

        public Config(int i, String str, String str2, String str3) {
            this(new PropertyInvalidatedCache.Args(str).api(str2).maxEntries(i), str3);
        }

        public Config(int i, String str, String str2) {
            this(i, str, str2, str2);
        }

        public Config(Config config, String str, String str2) {
            this(config.mArgs.api(str), str2);
        }

        public Config(Config config, String str) {
            this(config.mArgs.api(str), str);
        }

        public Config child(String str) {
            Config config = new Config(this.mArgs, str);
            registerChild(str);
            return config;
        }

        public Config cacheNulls(boolean z) {
            return new Config(this.mArgs.cacheNulls(z), this.mName);
        }

        public Config isolateUids(boolean z) {
            return new Config(this.mArgs.isolateUids(z), this.mName);
        }

        private final void registerChild(String str) {
            synchronized (this) {
                if (this.mChildren == null) {
                    this.mChildren = new ArraySet<>();
                }
                this.mChildren.add(str);
                if (this.mDisabled) {
                    IpcDataCache.disableForCurrentProcess(str);
                }
            }
        }

        public void invalidateCache() {
            IpcDataCache.invalidateCache(this.mArgs);
        }

        public void disableForCurrentProcess() {
            IpcDataCache.disableForCurrentProcess(this.mName);
        }

        public void disableAllForCurrentProcess() {
            synchronized (this) {
                this.mDisabled = true;
                disableForCurrentProcess();
                ArraySet<String> arraySet = this.mChildren;
                if (arraySet != null) {
                    Iterator<String> it = arraySet.iterator();
                    while (it.hasNext()) {
                        IpcDataCache.disableForCurrentProcess(it.next());
                    }
                }
            }
        }
    }

    public IpcDataCache(Config config, QueryHandler<Query, Result> queryHandler) {
        super(config.mArgs, config.mName, queryHandler);
    }

    private static class SystemServerCallHandler<Query, Result> extends QueryHandler<Query, Result> {
        private final RemoteCall<Query, Result> mHandler;

        public SystemServerCallHandler(RemoteCall remoteCall) {
            this.mHandler = remoteCall;
        }

        @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
        public Result apply(Query query) {
            try {
                return this.mHandler.apply(query);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public IpcDataCache(Config config, final RemoteCall<Query, Result> remoteCall) {
        QueryHandler systemServerCallHandler;
        if (Flags.cachingDevelopmentImprovements()) {
            systemServerCallHandler = new QueryHandler<Query, Result>() { // from class: android.os.IpcDataCache.1
                @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
                public Result apply(Query query) {
                    try {
                        return (Result) remoteCall.apply(query);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            };
        } else {
            systemServerCallHandler = new SystemServerCallHandler(remoteCall);
        }
        this(config, systemServerCallHandler);
    }

    public IpcDataCache(Config config, final RemoteCall<Query, Result> remoteCall, final BypassCall<Query> bypassCall) {
        this(config, new QueryHandler<Query, Result>() { // from class: android.os.IpcDataCache.2
            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public Result apply(Query query) {
                try {
                    return (Result) remoteCall.apply(query);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public boolean shouldBypassCache(Query query) {
                return bypassCall.apply(query).booleanValue();
            }
        });
    }

    @Override // android.app.PropertyInvalidatedCache
    public final void forgetDisableLocal() {
        super.forgetDisableLocal();
    }

    @Override // android.app.PropertyInvalidatedCache
    public final boolean isDisabled() {
        return super.isDisabled();
    }

    public boolean getDisabledState() {
        return isDisabled();
    }

    @Override // android.app.PropertyInvalidatedCache
    public final void disableInstance() {
        super.disableInstance();
    }

    @Override // android.app.PropertyInvalidatedCache
    public final void disableSystemWide() {
        super.disableSystemWide();
    }

    public static void setTestMode(boolean z) {
        PropertyInvalidatedCache.setTestMode(z);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static void setCacheTestMode(boolean z) {
        setTestMode(z);
    }
}
