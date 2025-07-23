package android.app;

import android.annotation.SystemApi;
import android.app.IOnProjectionStateChangedListener;
import android.app.IUiModeManager;
import android.app.IUiModeManagerCallback;
import android.app.UiModeManager;
import android.content.Context;
import android.os.Binder;
import android.os.Debug;
import android.os.IpcDataCache;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class UiModeManager {
    public static String ACTION_ENTER_CAR_MODE = "android.app.action.ENTER_CAR_MODE";

    @SystemApi
    public static final String ACTION_ENTER_CAR_MODE_PRIORITIZED = "android.app.action.ENTER_CAR_MODE_PRIORITIZED";
    public static String ACTION_ENTER_DESK_MODE = "android.app.action.ENTER_DESK_MODE";
    public static String ACTION_EXIT_CAR_MODE = "android.app.action.EXIT_CAR_MODE";

    @SystemApi
    public static final String ACTION_EXIT_CAR_MODE_PRIORITIZED = "android.app.action.EXIT_CAR_MODE_PRIORITIZED";
    public static String ACTION_EXIT_DESK_MODE = "android.app.action.EXIT_DESK_MODE";
    private static final String CURRENT_MODE_TYPE_API = "getCurrentModeType";

    @SystemApi
    public static final int DEFAULT_PRIORITY = 0;
    public static final int DISABLE_CAR_MODE_ALL_PRIORITIES = 2;
    public static final int DISABLE_CAR_MODE_GO_HOME = 1;
    public static final int ENABLE_CAR_MODE_ALLOW_SLEEP = 2;
    public static final int ENABLE_CAR_MODE_GO_CAR_HOME = 1;

    @SystemApi
    public static final String EXTRA_CALLING_PACKAGE = "android.app.extra.CALLING_PACKAGE";

    @SystemApi
    public static final String EXTRA_PRIORITY = "android.app.extra.PRIORITY";
    public static final int FORCE_INVERT_TYPE_DARK = 1;
    public static final int FORCE_INVERT_TYPE_LIGHT = 2;
    public static final int FORCE_INVERT_TYPE_OFF = 0;
    public static final int MODE_ATTENTION_THEME_OVERLAY_DAY = 1002;
    public static final int MODE_ATTENTION_THEME_OVERLAY_NIGHT = 1001;
    public static final int MODE_ATTENTION_THEME_OVERLAY_OFF = 1000;
    public static final int MODE_ATTENTION_THEME_OVERLAY_UNKNOWN = -1;
    public static final int MODE_NIGHT_AUTO = 0;
    public static final int MODE_NIGHT_CUSTOM = 3;

    @SystemApi
    public static final int MODE_NIGHT_CUSTOM_TYPE_BEDTIME = 1;

    @SystemApi
    public static final int MODE_NIGHT_CUSTOM_TYPE_SCHEDULE = 0;

    @SystemApi
    public static final int MODE_NIGHT_CUSTOM_TYPE_UNKNOWN = -1;
    public static final int MODE_NIGHT_NO = 1;
    public static final int MODE_NIGHT_YES = 2;
    private static final String NIGHT_MODE_API = "getNightMode";

    @SystemApi
    public static final int PROJECTION_TYPE_ALL = -1;

    @SystemApi
    public static final int PROJECTION_TYPE_AUTOMOTIVE = 1;

    @SystemApi
    public static final int PROJECTION_TYPE_NONE = 0;
    public static String SEM_ACTION_ENTER_DESKTOP_MODE = "com.samsung.android.desktopmode.action.ENTER_DESKTOP_MODE";
    public static String SEM_ACTION_ENTER_KNOX_DESKTOP_MODE = "android.app.action.ENTER_KNOX_DESKTOP_MODE";
    public static String SEM_ACTION_EXIT_DESKTOP_MODE = "com.samsung.android.desktopmode.action.EXIT_DESKTOP_MODE";
    public static String SEM_ACTION_EXIT_KNOX_DESKTOP_MODE = "android.app.action.EXIT_KNOX_DESKTOP_MODE";
    public static final int SEM_DISPLAY_TYPE_DUAL = 102;
    public static final int SEM_DISPLAY_TYPE_STANDALONE = 101;
    public static final String SEM_EXTRA_DISPLAY_TYPE = "android.app.extra.DISPLAY_TYPE";
    private static final String TAG = "UiModeManager";
    private static Globals sGlobals;
    private Context mContext;
    private final IpcDataCache<Void, Integer> mCurrentModeTypeCache;
    private final IpcDataCache.QueryHandler<Void, Integer> mCurrentModeTypeQuery;
    private final Object mLock;
    private final IpcDataCache<Void, Integer> mNightModeCache;
    private final IpcDataCache.QueryHandler<Void, Integer> mNightModeQuery;
    private final OnProjectionStateChangedListenerResourceManager mOnProjectionStateChangedListenerResourceManager;
    private final Map<OnProjectionStateChangedListener, InnerListener> mProjectionStateListenerMap;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttentionModeThemeOverlayReturnType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttentionModeThemeOverlayType {
    }

    public interface ContrastChangeListener {
        void onContrastChanged(float f);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisableCarMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnableCarMode {
    }

    public interface ForceInvertStateChangeListener {
        void onForceInvertStateChanged(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ForceInvertType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NightMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NightModeCustomReturnType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NightModeCustomType {
    }

    @SystemApi
    public interface OnProjectionStateChangedListener {
        void onProjectionStateChanged(int i, Set<String> set);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProjectionType {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Globals extends IUiModeManagerCallback.Stub {
        private float mContrast;
        private int mForceInvertState;
        private final IUiModeManager mService;
        private final Object mGlobalsLock = new Object();
        private final ArrayMap<ContrastChangeListener, Executor> mContrastChangeListeners = new ArrayMap<>();
        private final ArrayMap<ForceInvertStateChangeListener, Executor> mForceInvertStateChangeListeners = new ArrayMap<>();

        Globals(IUiModeManager iUiModeManager) {
            this.mForceInvertState = 0;
            this.mContrast = 0.0f;
            this.mService = iUiModeManager;
            try {
                iUiModeManager.addCallback(this);
                this.mContrast = iUiModeManager.getContrast();
                this.mForceInvertState = iUiModeManager.getForceInvertState();
            } catch (RemoteException e) {
                Log.e(UiModeManager.TAG, "Setup failed: UiModeManagerService is dead", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getForceInvertState() {
            int i;
            synchronized (this.mGlobalsLock) {
                i = this.mForceInvertState;
            }
            return i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addForceInvertStateChangeListener(ForceInvertStateChangeListener forceInvertStateChangeListener, Executor executor) {
            synchronized (this.mGlobalsLock) {
                this.mForceInvertStateChangeListeners.put(forceInvertStateChangeListener, executor);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeForceInvertStateChangeListener(ForceInvertStateChangeListener forceInvertStateChangeListener) {
            synchronized (this.mGlobalsLock) {
                this.mForceInvertStateChangeListeners.remove(forceInvertStateChangeListener);
            }
        }

        @Override // android.app.IUiModeManagerCallback
        public void notifyForceInvertStateChanged(final int i) {
            ArrayMap arrayMap = new ArrayMap();
            synchronized (this.mGlobalsLock) {
                if (this.mForceInvertState == i) {
                    return;
                }
                this.mForceInvertState = i;
                arrayMap.putAll((Map) this.mForceInvertStateChangeListeners);
                arrayMap.forEach(new BiConsumer() { // from class: android.app.UiModeManager$Globals$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        UiModeManager.Globals.lambda$notifyForceInvertStateChanged$1(i, (UiModeManager.ForceInvertStateChangeListener) obj, (Executor) obj2);
                    }
                });
            }
        }

        static /* synthetic */ void lambda$notifyForceInvertStateChanged$1(final int i, final ForceInvertStateChangeListener forceInvertStateChangeListener, Executor executor) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.app.UiModeManager$Globals$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        UiModeManager.ForceInvertStateChangeListener.this.onForceInvertStateChanged(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getContrast() {
            float f;
            synchronized (this.mGlobalsLock) {
                f = this.mContrast;
            }
            return f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addContrastChangeListener(ContrastChangeListener contrastChangeListener, Executor executor) {
            synchronized (this.mGlobalsLock) {
                this.mContrastChangeListeners.put(contrastChangeListener, executor);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeContrastChangeListener(ContrastChangeListener contrastChangeListener) {
            synchronized (this.mGlobalsLock) {
                this.mContrastChangeListeners.remove(contrastChangeListener);
            }
        }

        @Override // android.app.IUiModeManagerCallback
        public void notifyContrastChanged(final float f) {
            synchronized (this.mGlobalsLock) {
                if (Math.abs(this.mContrast - f) < 1.0E-10d) {
                    return;
                }
                this.mContrast = f;
                this.mContrastChangeListeners.forEach(new BiConsumer() { // from class: android.app.UiModeManager$Globals$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        Executor executor = (Executor) obj2;
                        executor.execute(new Runnable() { // from class: android.app.UiModeManager$Globals$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                UiModeManager.ContrastChangeListener.this.onContrastChanged(r2);
                            }
                        });
                    }
                });
            }
        }
    }

    public static class ContrastUtils {
        public static final float CONTRAST_DEFAULT_VALUE = 0.0f;
        public static final int CONTRAST_LEVEL_DISABLE = -1;
        public static final int CONTRAST_LEVEL_HIGH = 2;
        public static final int CONTRAST_LEVEL_MEDIUM = 1;
        public static final int CONTRAST_LEVEL_STANDARD = 0;
        private static final float CONTRAST_MAX_VALUE = 1.0f;
        private static final float CONTRAST_MIN_VALUE = -1.0f;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ContrastLevel {
        }

        private static Stream<Integer> allContrastLevels() {
            return Stream.of((Object[]) new Integer[]{0, 1, 2});
        }

        public static int toContrastLevel(final float f) {
            if (f < -1.0f || f > 1.0f) {
                throw new IllegalArgumentException("contrast values should be in [-1, 1]");
            }
            if (f == -1.0f) {
                return -1;
            }
            return allContrastLevels().min(Comparator.comparingDouble(new ToDoubleFunction() { // from class: android.app.UiModeManager$ContrastUtils$$ExternalSyntheticLambda1
                /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
                    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v2 double, still in use, count: 1, list:
                      (r0v2 double) from 0x0008: RETURN (r0v2 double)
                    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
                    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
                    	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
                    	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
                    	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
                    	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
                    */
                @Override // java.util.function.ToDoubleFunction
                public final double applyAsDouble(java.lang.Object r1) {
                    /*
                        r0 = this;
                        float r0 = r1
                        java.lang.Integer r1 = (java.lang.Integer) r1
                        double r0 = android.app.UiModeManager.ContrastUtils.lambda$toContrastLevel$0(r0, r1)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: android.app.UiModeManager$ContrastUtils$$ExternalSyntheticLambda1.applyAsDouble(java.lang.Object):double");
                }
            })).orElseThrow().intValue();
        }

        public static float fromContrastLevel(final int i) {
            if (!allContrastLevels().noneMatch(new Predicate() { // from class: android.app.UiModeManager$ContrastUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return UiModeManager.ContrastUtils.lambda$fromContrastLevel$1(i, (Integer) obj);
                }
            })) {
                return i / 2.0f;
            }
            throw new IllegalArgumentException("unrecognized contrast level: " + i);
        }

        static /* synthetic */ boolean lambda$fromContrastLevel$1(int i, Integer num) {
            return num.intValue() == i;
        }
    }

    UiModeManager() throws ServiceManager.ServiceNotFoundException {
        this(null);
    }

    UiModeManager(Context context) throws ServiceManager.ServiceNotFoundException {
        Object obj = new Object();
        this.mLock = obj;
        this.mProjectionStateListenerMap = new ArrayMap();
        this.mOnProjectionStateChangedListenerResourceManager = new OnProjectionStateChangedListenerResourceManager();
        IpcDataCache.QueryHandler<Void, Integer> queryHandler = new IpcDataCache.QueryHandler<Void, Integer>() { // from class: android.app.UiModeManager.1
            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public Integer apply(Void r1) {
                return UiModeManager.this.getCurrentModeTypeFromServer();
            }
        };
        this.mCurrentModeTypeQuery = queryHandler;
        this.mCurrentModeTypeCache = new IpcDataCache<>(1, "system_server", CURRENT_MODE_TYPE_API, "CurrentModeTypeCache", queryHandler);
        IpcDataCache.QueryHandler<Void, Integer> queryHandler2 = new IpcDataCache.QueryHandler<Void, Integer>() { // from class: android.app.UiModeManager.2
            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public Integer apply(Void r1) {
                return UiModeManager.this.getNightModeFromServer();
            }
        };
        this.mNightModeQuery = queryHandler2;
        this.mNightModeCache = new IpcDataCache<>(1, "system_server", NIGHT_MODE_API, "NightModeCache", queryHandler2);
        IUiModeManager asInterface = IUiModeManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.UI_MODE_SERVICE));
        this.mContext = context;
        if (asInterface == null) {
            return;
        }
        synchronized (obj) {
            if (sGlobals == null) {
                sGlobals = new Globals(asInterface);
            }
        }
    }

    public void enableCarMode(int i) {
        enableCarMode(0, i);
    }

    @SystemApi
    public void enableCarMode(int i, int i2) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                IUiModeManager iUiModeManager = globals.mService;
                Context context = this.mContext;
                iUiModeManager.enableCarMode(i2, i, context == null ? null : context.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void disableCarMode(int i) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                IUiModeManager iUiModeManager = globals.mService;
                Context context = this.mContext;
                iUiModeManager.disableCarModeByCallingPackage(i, context == null ? null : context.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Integer getCurrentModeTypeFromServer() {
        try {
            Globals globals = sGlobals;
            if (globals != null) {
                return Integer.valueOf(globals.mService.getCurrentModeType());
            }
            return 1;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void invalidateCurrentModeTypeCache() {
        IpcDataCache.invalidateCache("system_server", CURRENT_MODE_TYPE_API);
    }

    public int getCurrentModeType() {
        if (Flags.enableCurrentModeTypeBinderCache()) {
            return this.mCurrentModeTypeCache.query(null).intValue();
        }
        return getCurrentModeTypeFromServer().intValue();
    }

    public void setNightMode(int i) {
        Slog.i(TAG, "setNightMode : " + i + ", caller : " + Debug.getCallers(7));
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setNightMode(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void setNightModeCustomType(int i) {
        Slog.i(TAG, "setNightModeCustomType : " + i + ", caller : " + Debug.getCallers(7));
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setNightModeCustomType(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public int getNightModeCustomType() {
        Globals globals = sGlobals;
        if (globals == null) {
            return -1;
        }
        try {
            return globals.mService.getNightModeCustomType();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAttentionModeThemeOverlay(int i) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setAttentionModeThemeOverlay(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getAttentionModeThemeOverlay() {
        Globals globals = sGlobals;
        if (globals == null) {
            return -1;
        }
        try {
            return globals.mService.getAttentionModeThemeOverlay();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setApplicationNightMode(int i) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setApplicationNightMode(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Integer getNightModeFromServer() {
        try {
            Globals globals = sGlobals;
            if (globals != null) {
                return Integer.valueOf(globals.mService.getNightMode());
            }
            return -1;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPackageNightMode(String str, int i) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setPackageNightMode(str, UserHandle.getCallingUserId(), i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getPackageNightMode(String str) {
        Globals globals = sGlobals;
        if (globals == null) {
            return -1;
        }
        try {
            return globals.mService.getPackageNightMode(str, UserHandle.getCallingUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNightPriorityAllowedPackagesFromScpm(List<String> list) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setNightPriorityAllowedPackagesFromScpm(list);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<String> getNightPriorityAllowedPackagesFromScpm() {
        Globals globals = sGlobals;
        if (globals == null) {
            return null;
        }
        try {
            return globals.mService.getNightPriorityAllowedPackagesFromScpm();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void invalidateNightModeCache() {
        IpcDataCache.invalidateCache("system_server", NIGHT_MODE_API);
    }

    public int getNightMode() {
        if (Flags.enableNightModeBinderCache()) {
            return this.mNightModeCache.query(null).intValue();
        }
        return getNightModeFromServer().intValue();
    }

    public boolean isUiModeLocked() {
        Globals globals = sGlobals;
        if (globals == null) {
            return true;
        }
        try {
            return globals.mService.isUiModeLocked();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNightModeLocked() {
        Globals globals = sGlobals;
        if (globals == null) {
            return true;
        }
        try {
            return globals.mService.isNightModeLocked();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setNightModeActivatedForCustomMode(int i, boolean z) {
        Globals globals = sGlobals;
        if (globals == null) {
            return false;
        }
        try {
            return globals.mService.setNightModeActivatedForCustomMode(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setNightModeActivated(boolean z) {
        Globals globals = sGlobals;
        if (globals == null) {
            return false;
        }
        try {
            return globals.mService.setNightModeActivated(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public LocalTime getCustomNightModeStart() {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return LocalTime.ofNanoOfDay(globals.mService.getCustomNightModeStart() * 1000);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return LocalTime.MIDNIGHT;
    }

    public void setCustomNightModeStart(LocalTime localTime) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setCustomNightModeStart(localTime.toNanoOfDay() / 1000);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public LocalTime getCustomNightModeEnd() {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return LocalTime.ofNanoOfDay(globals.mService.getCustomNightModeEnd() * 1000);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return LocalTime.MIDNIGHT;
    }

    public void setCustomNightModeEnd(LocalTime localTime) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setCustomNightModeEnd(localTime.toNanoOfDay() / 1000);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean requestProjection(int i) {
        Globals globals = sGlobals;
        if (globals == null) {
            return false;
        }
        try {
            return globals.mService.requestProjection(new Binder(), i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean releaseProjection(int i) {
        Globals globals = sGlobals;
        if (globals == null) {
            return false;
        }
        try {
            return globals.mService.releaseProjection(i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Set<String> getProjectingPackages(int i) {
        if (sGlobals != null) {
            try {
                return new ArraySet(sGlobals.mService.getProjectingPackages(i));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_SET;
    }

    @SystemApi
    public int getActiveProjectionTypes() {
        Globals globals = sGlobals;
        if (globals == null) {
            return 0;
        }
        try {
            return globals.mService.getActiveProjectionTypes();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addOnProjectionStateChangedListener(int i, Executor executor, OnProjectionStateChangedListener onProjectionStateChangedListener) {
        synchronized (this.mLock) {
            if (this.mProjectionStateListenerMap.containsKey(onProjectionStateChangedListener)) {
                Slog.i(TAG, "Attempted to add listener that was already added.");
                return;
            }
            if (sGlobals != null) {
                InnerListener innerListener = new InnerListener(executor, onProjectionStateChangedListener, this.mOnProjectionStateChangedListenerResourceManager);
                try {
                    sGlobals.mService.addOnProjectionStateChangedListener(innerListener, i);
                    this.mProjectionStateListenerMap.put(onProjectionStateChangedListener, innerListener);
                } catch (RemoteException e) {
                    this.mOnProjectionStateChangedListenerResourceManager.remove(innerListener);
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @SystemApi
    public void removeOnProjectionStateChangedListener(OnProjectionStateChangedListener onProjectionStateChangedListener) {
        synchronized (this.mLock) {
            InnerListener innerListener = this.mProjectionStateListenerMap.get(onProjectionStateChangedListener);
            if (innerListener == null) {
                Slog.i(TAG, "Attempted to remove listener that was not added.");
                return;
            }
            Globals globals = sGlobals;
            if (globals != null) {
                try {
                    globals.mService.removeOnProjectionStateChangedListener(innerListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            this.mProjectionStateListenerMap.remove(onProjectionStateChangedListener);
            this.mOnProjectionStateChangedListenerResourceManager.remove(innerListener);
        }
    }

    private static class InnerListener extends IOnProjectionStateChangedListener.Stub {
        private final WeakReference<OnProjectionStateChangedListenerResourceManager> mResourceManager;

        private InnerListener(Executor executor, OnProjectionStateChangedListener onProjectionStateChangedListener, OnProjectionStateChangedListenerResourceManager onProjectionStateChangedListenerResourceManager) {
            onProjectionStateChangedListenerResourceManager.put(this, executor, onProjectionStateChangedListener);
            this.mResourceManager = new WeakReference<>(onProjectionStateChangedListenerResourceManager);
        }

        @Override // android.app.IOnProjectionStateChangedListener
        public void onProjectionStateChanged(int i, List<String> list) {
            OnProjectionStateChangedListenerResourceManager onProjectionStateChangedListenerResourceManager = this.mResourceManager.get();
            if (onProjectionStateChangedListenerResourceManager == null) {
                Slog.w(UiModeManager.TAG, "Can't execute onProjectionStateChanged, resource manager is gone.");
                return;
            }
            OnProjectionStateChangedListener outerListener = onProjectionStateChangedListenerResourceManager.getOuterListener(this);
            Executor executor = onProjectionStateChangedListenerResourceManager.getExecutor(this);
            if (outerListener == null || executor == null) {
                Slog.w(UiModeManager.TAG, "Can't execute onProjectionStatechanged, references are null.");
            } else {
                executor.execute(PooledLambda.obtainRunnable(new TriConsumer() { // from class: android.app.UiModeManager$InnerListener$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        ((UiModeManager.OnProjectionStateChangedListener) obj).onProjectionStateChanged(((Integer) obj2).intValue(), (ArraySet) obj3);
                    }
                }, outerListener, Integer.valueOf(i), new ArraySet(list)).recycleOnUse());
            }
        }
    }

    private static class OnProjectionStateChangedListenerResourceManager {
        private final Map<InnerListener, Executor> mExecutorMap;
        private final Map<InnerListener, OnProjectionStateChangedListener> mOuterListenerMap;

        private OnProjectionStateChangedListenerResourceManager() {
            this.mOuterListenerMap = new ArrayMap(1);
            this.mExecutorMap = new ArrayMap(1);
        }

        void put(InnerListener innerListener, Executor executor, OnProjectionStateChangedListener onProjectionStateChangedListener) {
            this.mOuterListenerMap.put(innerListener, onProjectionStateChangedListener);
            this.mExecutorMap.put(innerListener, executor);
        }

        void remove(InnerListener innerListener) {
            this.mOuterListenerMap.remove(innerListener);
            this.mExecutorMap.remove(innerListener);
        }

        OnProjectionStateChangedListener getOuterListener(InnerListener innerListener) {
            return this.mOuterListenerMap.get(innerListener);
        }

        Executor getExecutor(InnerListener innerListener) {
            return this.mExecutorMap.get(innerListener);
        }
    }

    public float getContrast() {
        return sGlobals.getContrast();
    }

    public void addContrastChangeListener(Executor executor, ContrastChangeListener contrastChangeListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(contrastChangeListener);
        sGlobals.addContrastChangeListener(contrastChangeListener, executor);
    }

    public void removeContrastChangeListener(ContrastChangeListener contrastChangeListener) {
        Objects.requireNonNull(contrastChangeListener);
        sGlobals.removeContrastChangeListener(contrastChangeListener);
    }

    public int getForceInvertState() {
        return sGlobals.getForceInvertState();
    }

    public void addForceInvertStateChangeListener(Executor executor, ForceInvertStateChangeListener forceInvertStateChangeListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(forceInvertStateChangeListener);
        sGlobals.addForceInvertStateChangeListener(forceInvertStateChangeListener, executor);
    }

    public void removeForceInvertStateChangeListener(ForceInvertStateChangeListener forceInvertStateChangeListener) {
        Objects.requireNonNull(forceInvertStateChangeListener);
        sGlobals.removeForceInvertStateChangeListener(forceInvertStateChangeListener);
    }
}
