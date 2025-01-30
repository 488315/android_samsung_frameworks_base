package android.app;

import android.annotation.SystemApi;
import android.app.IOnProjectionStateChangedListener;
import android.app.IUiModeManager;
import android.app.IUiModeManagerCallback;
import android.app.UiModeManager;
import android.content.Context;
import android.p009os.Binder;
import android.p009os.Debug;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.UserHandle;
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

    @SystemApi
    public static final String ACTION_ENTER_CAR_MODE_PRIORITIZED = "android.app.action.ENTER_CAR_MODE_PRIORITIZED";

    @SystemApi
    public static final String ACTION_EXIT_CAR_MODE_PRIORITIZED = "android.app.action.EXIT_CAR_MODE_PRIORITIZED";

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

    @SystemApi
    public static final int PROJECTION_TYPE_ALL = -1;

    @SystemApi
    public static final int PROJECTION_TYPE_AUTOMOTIVE = 1;

    @SystemApi
    public static final int PROJECTION_TYPE_NONE = 0;
    public static final int SEM_DISPLAY_TYPE_DUAL = 102;
    public static final int SEM_DISPLAY_TYPE_STANDALONE = 101;
    public static final String SEM_EXTRA_DISPLAY_TYPE = "android.app.extra.DISPLAY_TYPE";
    private static final String TAG = "UiModeManager";
    private static Globals sGlobals;
    private Context mContext;
    private final Object mLock;
    private final OnProjectionStateChangedListenerResourceManager mOnProjectionStateChangedListenerResourceManager;
    private final Map<OnProjectionStateChangedListener, InnerListener> mProjectionStateListenerMap;
    public static String ACTION_ENTER_CAR_MODE = "android.app.action.ENTER_CAR_MODE";
    public static String ACTION_EXIT_CAR_MODE = "android.app.action.EXIT_CAR_MODE";
    public static String ACTION_ENTER_DESK_MODE = "android.app.action.ENTER_DESK_MODE";
    public static String ACTION_EXIT_DESK_MODE = "android.app.action.EXIT_DESK_MODE";
    public static String SEM_ACTION_ENTER_KNOX_DESKTOP_MODE = "android.app.action.ENTER_KNOX_DESKTOP_MODE";
    public static String SEM_ACTION_EXIT_KNOX_DESKTOP_MODE = "android.app.action.EXIT_KNOX_DESKTOP_MODE";
    public static String SEM_ACTION_ENTER_DESKTOP_MODE = "com.samsung.android.desktopmode.action.ENTER_DESKTOP_MODE";
    public static String SEM_ACTION_EXIT_DESKTOP_MODE = "com.samsung.android.desktopmode.action.EXIT_DESKTOP_MODE";

    public interface ContrastChangeListener {
        void onContrastChanged(float f);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisableCarMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnableCarMode {
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
        private final IUiModeManager mService;
        private final Object mGlobalsLock = new Object();
        private final ArrayMap<ContrastChangeListener, Executor> mContrastChangeListeners = new ArrayMap<>();

        Globals(IUiModeManager service) {
            this.mContrast = 0.0f;
            this.mService = service;
            try {
                service.addCallback(this);
                this.mContrast = service.getContrast();
            } catch (RemoteException e) {
                Log.m97e(UiModeManager.TAG, "Setup failed: UiModeManagerService is dead", e);
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
        public void addContrastChangeListener(ContrastChangeListener listener, Executor executor) {
            synchronized (this.mGlobalsLock) {
                this.mContrastChangeListeners.put(listener, executor);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeContrastChangeListener(ContrastChangeListener listener) {
            synchronized (this.mGlobalsLock) {
                this.mContrastChangeListeners.remove(listener);
            }
        }

        @Override // android.app.IUiModeManagerCallback
        public void notifyContrastChanged(final float contrast) {
            synchronized (this.mGlobalsLock) {
                if (Math.abs(this.mContrast - contrast) < 1.0E-10d) {
                    return;
                }
                this.mContrast = contrast;
                this.mContrastChangeListeners.forEach(new BiConsumer() { // from class: android.app.UiModeManager$Globals$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        Executor executor = (Executor) obj2;
                        executor.execute(new Runnable() { // from class: android.app.UiModeManager$Globals$$ExternalSyntheticLambda0
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

        public static int toContrastLevel(final float contrast) {
            if (contrast < -1.0f || contrast > 1.0f) {
                throw new IllegalArgumentException("contrast values should be in [-1, 1]");
            }
            if (contrast == -1.0f) {
                return -1;
            }
            return allContrastLevels().min(Comparator.comparingDouble(new ToDoubleFunction() { // from class: android.app.UiModeManager$ContrastUtils$$ExternalSyntheticLambda1
                /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
                    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v1 double, still in use, count: 1, list:
                      (r0v1 double) from 0x0008: RETURN (r0v1 double)
                    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
                    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
                    	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
                    	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
                    	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
                    	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
                    */
                @Override // java.util.function.ToDoubleFunction
                public final double applyAsDouble(java.lang.Object r3) {
                    /*
                        r2 = this;
                        float r0 = r1
                        java.lang.Integer r3 = (java.lang.Integer) r3
                        double r0 = android.app.UiModeManager.ContrastUtils.lambda$toContrastLevel$0(r0, r3)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: android.app.UiModeManager$ContrastUtils$$ExternalSyntheticLambda1.applyAsDouble(java.lang.Object):double");
                }
            })).orElseThrow().intValue();
        }

        public static float fromContrastLevel(final int contrastLevel) {
            if (allContrastLevels().noneMatch(new Predicate() { // from class: android.app.UiModeManager$ContrastUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return UiModeManager.ContrastUtils.lambda$fromContrastLevel$1(contrastLevel, (Integer) obj);
                }
            })) {
                throw new IllegalArgumentException("unrecognized contrast level: " + contrastLevel);
            }
            return contrastLevel / 2.0f;
        }

        static /* synthetic */ boolean lambda$fromContrastLevel$1(int contrastLevel, Integer level) {
            return level.intValue() == contrastLevel;
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
        IUiModeManager service = IUiModeManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.UI_MODE_SERVICE));
        this.mContext = context;
        if (service == null) {
            return;
        }
        synchronized (obj) {
            if (sGlobals == null) {
                sGlobals = new Globals(service);
            }
        }
    }

    public void enableCarMode(int flags) {
        enableCarMode(0, flags);
    }

    @SystemApi
    public void enableCarMode(int priority, int flags) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                IUiModeManager iUiModeManager = globals.mService;
                Context context = this.mContext;
                iUiModeManager.enableCarMode(flags, priority, context == null ? null : context.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void disableCarMode(int flags) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                IUiModeManager iUiModeManager = globals.mService;
                Context context = this.mContext;
                iUiModeManager.disableCarModeByCallingPackage(flags, context == null ? null : context.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getCurrentModeType() {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.getCurrentModeType();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 1;
    }

    public void setNightMode(int mode) {
        Slog.m117i(TAG, "setNightMode : " + mode + ", caller : " + Debug.getCallers(7));
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setNightMode(mode);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void setNightModeCustomType(int nightModeCustomType) {
        Slog.m117i(TAG, "setNightModeCustomType : " + nightModeCustomType + ", caller : " + Debug.getCallers(7));
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setNightModeCustomType(nightModeCustomType);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public int getNightModeCustomType() {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.getNightModeCustomType();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public void setApplicationNightMode(int mode) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setApplicationNightMode(mode);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setPackageNightMode(String packageName, int mode) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setPackageNightMode(packageName, UserHandle.getCallingUserId(), mode);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getPackageNightMode(String packageName) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.getPackageNightMode(packageName, UserHandle.getCallingUserId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public void setNightPriorityAllowedPackagesFromScpm(List<String> packages) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setNightPriorityAllowedPackagesFromScpm(packages);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<String> getNightPriorityAllowedPackagesFromScpm() {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.getNightPriorityAllowedPackagesFromScpm();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public int getNightMode() {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.getNightMode();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public boolean isUiModeLocked() {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.isUiModeLocked();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public boolean isNightModeLocked() {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.isNightModeLocked();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    @SystemApi
    public boolean setNightModeActivatedForCustomMode(int nightModeCustomType, boolean active) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.setNightModeActivatedForCustomMode(nightModeCustomType, active);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean setNightModeActivated(boolean active) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.setNightModeActivated(active);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
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

    public void setCustomNightModeStart(LocalTime time) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setCustomNightModeStart(time.toNanoOfDay() / 1000);
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

    public void setCustomNightModeEnd(LocalTime time) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                globals.mService.setCustomNightModeEnd(time.toNanoOfDay() / 1000);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean requestProjection(int projectionType) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.requestProjection(new Binder(), projectionType, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean releaseProjection(int projectionType) {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.releaseProjection(projectionType, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public Set<String> getProjectingPackages(int projectionType) {
        if (sGlobals != null) {
            try {
                return new ArraySet(sGlobals.mService.getProjectingPackages(projectionType));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return Set.of();
    }

    @SystemApi
    public int getActiveProjectionTypes() {
        Globals globals = sGlobals;
        if (globals != null) {
            try {
                return globals.mService.getActiveProjectionTypes();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    @SystemApi
    public void addOnProjectionStateChangedListener(int projectionType, Executor executor, OnProjectionStateChangedListener listener) {
        synchronized (this.mLock) {
            if (this.mProjectionStateListenerMap.containsKey(listener)) {
                Slog.m117i(TAG, "Attempted to add listener that was already added.");
                return;
            }
            if (sGlobals != null) {
                InnerListener innerListener = new InnerListener(executor, listener, this.mOnProjectionStateChangedListenerResourceManager);
                try {
                    sGlobals.mService.addOnProjectionStateChangedListener(innerListener, projectionType);
                    this.mProjectionStateListenerMap.put(listener, innerListener);
                } catch (RemoteException e) {
                    this.mOnProjectionStateChangedListenerResourceManager.remove(innerListener);
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @SystemApi
    public void removeOnProjectionStateChangedListener(OnProjectionStateChangedListener listener) {
        synchronized (this.mLock) {
            InnerListener innerListener = this.mProjectionStateListenerMap.get(listener);
            if (innerListener == null) {
                Slog.m117i(TAG, "Attempted to remove listener that was not added.");
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
            this.mProjectionStateListenerMap.remove(listener);
            this.mOnProjectionStateChangedListenerResourceManager.remove(innerListener);
        }
    }

    private static class InnerListener extends IOnProjectionStateChangedListener.Stub {
        private final WeakReference<OnProjectionStateChangedListenerResourceManager> mResourceManager;

        private InnerListener(Executor executor, OnProjectionStateChangedListener outerListener, OnProjectionStateChangedListenerResourceManager resourceManager) {
            resourceManager.put(this, executor, outerListener);
            this.mResourceManager = new WeakReference<>(resourceManager);
        }

        @Override // android.app.IOnProjectionStateChangedListener
        public void onProjectionStateChanged(int activeProjectionTypes, List<String> projectingPackages) {
            OnProjectionStateChangedListenerResourceManager resourceManager = this.mResourceManager.get();
            if (resourceManager == null) {
                Slog.m121w(UiModeManager.TAG, "Can't execute onProjectionStateChanged, resource manager is gone.");
                return;
            }
            OnProjectionStateChangedListener outerListener = resourceManager.getOuterListener(this);
            Executor executor = resourceManager.getExecutor(this);
            if (outerListener == null || executor == null) {
                Slog.m121w(UiModeManager.TAG, "Can't execute onProjectionStatechanged, references are null.");
            } else {
                executor.execute(PooledLambda.obtainRunnable(new TriConsumer() { // from class: android.app.UiModeManager$InnerListener$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        ((UiModeManager.OnProjectionStateChangedListener) obj).onProjectionStateChanged(((Integer) obj2).intValue(), (ArraySet) obj3);
                    }
                }, outerListener, Integer.valueOf(activeProjectionTypes), new ArraySet(projectingPackages)).recycleOnUse());
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

        void put(InnerListener innerListener, Executor executor, OnProjectionStateChangedListener outerListener) {
            this.mOuterListenerMap.put(innerListener, outerListener);
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

    public void addContrastChangeListener(Executor executor, ContrastChangeListener listener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(listener);
        sGlobals.addContrastChangeListener(listener, executor);
    }

    public void removeContrastChangeListener(ContrastChangeListener listener) {
        Objects.requireNonNull(listener);
        sGlobals.removeContrastChangeListener(listener);
    }
}
