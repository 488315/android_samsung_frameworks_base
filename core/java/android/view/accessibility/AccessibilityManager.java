package android.view.accessibility;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.accessibilityservice.SemAccessibilityShortcutInfo;
import android.annotation.SystemApi;
import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.IWindow;
import android.view.SurfaceControl;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.IAccessibilityManager;
import android.view.accessibility.IAccessibilityManagerClient;
import com.android.internal.R;
import com.android.internal.util.IntPair;
import com.samsung.android.sepunion.ISemExclusiveTaskManager;
import com.samsung.android.sepunion.SemUnionManager;
import com.samsung.android.sepunion.UnionConstants;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public final class AccessibilityManager {
    public static final String ACTION_CHOOSE_ACCESSIBILITY_BUTTON = "com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON";
    public static final int AUTOCLICK_DELAY_DEFAULT = 600;
    public static final int AUTO_ACTION_DELAY_DEFAULT = 600;
    public static final int AUTO_ACTION_TYPE_DEFAULT = 0;
    public static final int DALTONIZER_CORRECT_DEUTERANOMALY = 12;
    public static final int DALTONIZER_DISABLED = -1;
    public static final int DALTONIZER_SIMULATE_MONOCHROMACY = 0;
    private static final boolean DEBUG = false;
    public static final int FLAG_CONTENT_CONTROLS = 4;
    public static final int FLAG_CONTENT_ICONS = 1;
    public static final int FLAG_CONTENT_TEXT = 2;
    public static final int FLASH_REASON_ALARM = 2;
    public static final int FLASH_REASON_CALL = 1;
    public static final int FLASH_REASON_NOTIFICATION = 3;
    public static final int FLASH_REASON_PREVIEW = 4;
    private static final String LOG_TAG = "AccessibilityManager";
    public static final int SEM_FLASH_REASON_ALARM = 2;
    public static final int SEM_FLASH_REASON_CALL = 1;
    public static final int SEM_FLASH_REASON_NOTIFICATION = 3;
    public static final int SEM_FLASH_REASON_PREVIEW = 4;
    public static final int SEM_STATE_FLAG_ACCESSIBILITY_MENU = 2048;
    public static final int SEM_STATE_FLAG_ASSISTANT_MENU = 8192;
    public static final int SEM_STATE_FLAG_GOOGLE_TALKBACK = 16;
    public static final int SEM_STATE_FLAG_SELECT_TO_SPEAK = 4096;
    public static final int SEM_STATE_FLAG_UNIVERSAL_SWITCH = 64;
    public static final int SEM_STATE_FLAG_VOICE_ASSISTANT = 32;
    public static final int STATE_FLAG_ACCESSIBILITY_ENABLED = 1;
    public static final int STATE_FLAG_AUDIO_DESCRIPTION_BY_DEFAULT_ENABLED = 4096;
    public static final int STATE_FLAG_DISPATCH_DOUBLE_TAP = 8;
    public static final int STATE_FLAG_HIGH_TEXT_CONTRAST_ENABLED = 4;
    public static final int STATE_FLAG_REQUEST_MULTI_FINGER_GESTURES = 16;
    public static final int STATE_FLAG_TOUCH_EXPLORATION_ENABLED = 2;
    public static final int STATE_FLAG_TRACE_A11Y_INTERACTION_CLIENT_ENABLED = 1024;
    public static final int STATE_FLAG_TRACE_A11Y_INTERACTION_CONNECTION_CB_ENABLED = 512;
    public static final int STATE_FLAG_TRACE_A11Y_INTERACTION_CONNECTION_ENABLED = 256;
    public static final int STATE_FLAG_TRACE_A11Y_SERVICE_ENABLED = 2048;
    private static AccessibilityManager sInstance;
    static final Object sInstanceSync = new Object();
    AccessibilityPolicy mAccessibilityPolicy;
    private int mFocusColor;
    private int mFocusStrokeWidth;
    final Handler mHandler;
    int mInteractiveUiTimeout;
    boolean mIsAudioDescriptionByDefaultRequested;
    boolean mIsEnabled;
    boolean mIsHighTextContrastEnabled;
    boolean mIsTouchExplorationEnabled;
    int mNonInteractiveUiTimeout;
    private boolean mRequestFromAccessibilityTool;
    private SparseArray<List<AccessibilityRequestPreparer>> mRequestPreparerLists;
    private IAccessibilityManager mService;
    final int mUserId;
    public final int SEM_COLOR_FILTER_TYPE_BLUE = 0;
    public final int SEM_COLOR_FILTER_TYPE_AZURE = 1;
    public final int SEM_COLOR_FILTER_TYPE_CYAN = 2;
    public final int SEM_COLOR_FILTER_TYPE_SPRING_GREEN = 3;
    public final int SEM_COLOR_FILTER_TYPE_GREEN = 4;
    public final int SEM_COLOR_FILTER_TYPE_CHARTREUSE_GREEN = 5;
    public final int SEM_COLOR_FILTER_TYPE_YELLOW = 6;
    public final int SEM_COLOR_FILTER_TYPE_ORANGE = 7;
    public final int SEM_COLOR_FILTER_TYPE_RED = 8;
    public final int SEM_COLOR_FILTER_TYPE_ROSE = 9;
    public final int SEM_COLOR_FILTER_TYPE_MAGENTA = 10;
    public final int SEM_COLOR_FILTER_TYPE_VIOLET = 11;
    private final Object mLock = new Object();
    int mRelevantEventTypes = -1;
    int mAccessibilityTracingState = 0;
    private int mPerformingAction = 0;
    private final ArrayMap<AccessibilityStateChangeListener, Handler> mAccessibilityStateChangeListeners = new ArrayMap<>();
    private final ArrayMap<TouchExplorationStateChangeListener, Handler> mTouchExplorationStateChangeListeners = new ArrayMap<>();
    private final ArrayMap<HighTextContrastChangeListener, Handler> mHighTextContrastStateChangeListeners = new ArrayMap<>();
    private final ArrayMap<AccessibilityServicesStateChangeListener, Executor> mServicesStateChangeListeners = new ArrayMap<>();
    private final ArrayMap<AudioDescriptionRequestedChangeListener, Executor> mAudioDescriptionRequestedChangeListeners = new ArrayMap<>();
    private final Binder mBinder = new Binder();
    private final IAccessibilityManagerClient.Stub mClient = new AnonymousClass1();
    final Handler.Callback mCallback = new MyCallback();

    public interface AccessibilityPolicy {
        List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, List<AccessibilityServiceInfo> list);

        List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(List<AccessibilityServiceInfo> list);

        int getRelevantEventTypes(int i);

        boolean isEnabled(boolean z);

        AccessibilityEvent onAccessibilityEvent(AccessibilityEvent accessibilityEvent, boolean z, int i);
    }

    public interface AccessibilityServicesStateChangeListener {
        void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager);
    }

    public interface AccessibilityStateChangeListener {
        void onAccessibilityStateChanged(boolean z);
    }

    public interface AudioDescriptionRequestedChangeListener {
        void onAudioDescriptionRequestedChanged(boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FlashNotificationReason {
    }

    public interface HighTextContrastChangeListener {
        void onHighTextContrastStateChanged(boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemFlashNotificationReason {
    }

    public interface TouchExplorationStateChangeListener {
        void onTouchExplorationStateChanged(boolean z);
    }

    /* renamed from: android.view.accessibility.AccessibilityManager$1, reason: invalid class name */
    class AnonymousClass1 extends IAccessibilityManagerClient.Stub {
        AnonymousClass1() {
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setState(int state) {
            AccessibilityManager.this.mHandler.obtainMessage(1, state, 0).sendToTarget();
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void notifyServicesStateChanged(long updatedUiTimeout) {
            AccessibilityManager.this.updateUiTimeout(updatedUiTimeout);
            synchronized (AccessibilityManager.this.mLock) {
                if (AccessibilityManager.this.mServicesStateChangeListeners.isEmpty()) {
                    return;
                }
                ArrayMap<AccessibilityServicesStateChangeListener, Executor> listeners = new ArrayMap<>((ArrayMap<AccessibilityServicesStateChangeListener, Executor>) AccessibilityManager.this.mServicesStateChangeListeners);
                int numListeners = listeners.size();
                for (int i = 0; i < numListeners; i++) {
                    final AccessibilityServicesStateChangeListener listener = (AccessibilityServicesStateChangeListener) AccessibilityManager.this.mServicesStateChangeListeners.keyAt(i);
                    ((Executor) AccessibilityManager.this.mServicesStateChangeListeners.valueAt(i)).execute(new Runnable() { // from class: android.view.accessibility.AccessibilityManager$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AccessibilityManager.AnonymousClass1.this.lambda$notifyServicesStateChanged$0(listener);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyServicesStateChanged$0(AccessibilityServicesStateChangeListener listener) {
            listener.onAccessibilityServicesStateChanged(AccessibilityManager.this);
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setRelevantEventTypes(int eventTypes) {
            AccessibilityManager.this.mRelevantEventTypes = eventTypes;
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setFocusAppearance(int strokeWidth, int color) {
            synchronized (AccessibilityManager.this.mLock) {
                AccessibilityManager.this.updateFocusAppearanceLocked(strokeWidth, color);
            }
        }
    }

    public static AccessibilityManager getInstance(Context context) {
        int userId;
        synchronized (sInstanceSync) {
            if (sInstance == null) {
                if (Binder.getCallingUid() != 1000 && context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS) != 0 && context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) != 0) {
                    userId = context.getUserId();
                    sInstance = new AccessibilityManager(context, null, userId);
                }
                userId = -2;
                sInstance = new AccessibilityManager(context, null, userId);
            }
        }
        return sInstance;
    }

    public AccessibilityManager(Context context, IAccessibilityManager service, int userId) {
        this.mHandler = new Handler(context.getMainLooper(), this.mCallback);
        this.mUserId = userId;
        synchronized (this.mLock) {
            initialFocusAppearanceLocked(context.getResources());
            tryConnectToServiceLocked(service);
        }
    }

    public AccessibilityManager(Context context, Handler handler, IAccessibilityManager service, int userId, boolean serviceConnect) {
        this.mHandler = handler;
        this.mUserId = userId;
        synchronized (this.mLock) {
            initialFocusAppearanceLocked(context.getResources());
            if (serviceConnect) {
                tryConnectToServiceLocked(service);
            }
        }
    }

    public IAccessibilityManagerClient getClient() {
        return this.mClient;
    }

    public boolean removeClient() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.removeClient(this.mClient, this.mUserId);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "AccessibilityManagerService is dead", re);
                return false;
            }
        }
    }

    public Handler.Callback getCallback() {
        return this.mCallback;
    }

    public boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsEnabled || hasAnyDirectConnection() || (this.mAccessibilityPolicy != null && this.mAccessibilityPolicy.isEnabled(this.mIsEnabled));
        }
        return z;
    }

    public boolean hasAnyDirectConnection() {
        return AccessibilityInteractionClient.hasAnyDirectConnection();
    }

    public boolean isTouchExplorationEnabled() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            return this.mIsTouchExplorationEnabled;
        }
    }

    public boolean isHighTextContrastEnabled() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            return this.mIsHighTextContrastEnabled;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x009e, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x009b, code lost:
    
        if (r8 == r2) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void sendAccessibilityEvent(android.view.accessibility.AccessibilityEvent r8) {
        /*
            r7 = this;
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            android.view.accessibility.IAccessibilityManager r1 = r7.getServiceLocked()     // Catch: java.lang.Throwable -> La8
            if (r1 != 0) goto Lb
            monitor-exit(r0)     // Catch: java.lang.Throwable -> La8
            return
        Lb:
            long r2 = android.os.SystemClock.uptimeMillis()     // Catch: java.lang.Throwable -> La8
            r8.setEventTime(r2)     // Catch: java.lang.Throwable -> La8
            int r2 = r8.getAction()     // Catch: java.lang.Throwable -> La8
            if (r2 != 0) goto L1d
            int r2 = r7.mPerformingAction     // Catch: java.lang.Throwable -> La8
            r8.setAction(r2)     // Catch: java.lang.Throwable -> La8
        L1d:
            android.view.accessibility.AccessibilityManager$AccessibilityPolicy r2 = r7.mAccessibilityPolicy     // Catch: java.lang.Throwable -> La8
            if (r2 == 0) goto L2f
            android.view.accessibility.AccessibilityManager$AccessibilityPolicy r2 = r7.mAccessibilityPolicy     // Catch: java.lang.Throwable -> La8
            boolean r3 = r7.mIsEnabled     // Catch: java.lang.Throwable -> La8
            int r4 = r7.mRelevantEventTypes     // Catch: java.lang.Throwable -> La8
            android.view.accessibility.AccessibilityEvent r2 = r2.onAccessibilityEvent(r8, r3, r4)     // Catch: java.lang.Throwable -> La8
            if (r2 != 0) goto L30
            monitor-exit(r0)     // Catch: java.lang.Throwable -> La8
            return
        L2f:
            r2 = r8
        L30:
            boolean r3 = r7.isEnabled()     // Catch: java.lang.Throwable -> La8
            if (r3 != 0) goto L51
            android.os.Looper r3 = android.os.Looper.myLooper()     // Catch: java.lang.Throwable -> La8
            android.os.Looper r4 = android.os.Looper.getMainLooper()     // Catch: java.lang.Throwable -> La8
            if (r3 == r4) goto L49
            java.lang.String r4 = "AccessibilityManager"
            java.lang.String r5 = "AccessibilityEvent sent with accessibility disabled"
            android.util.Log.e(r4, r5)     // Catch: java.lang.Throwable -> La8
            monitor-exit(r0)     // Catch: java.lang.Throwable -> La8
            return
        L49:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> La8
            java.lang.String r5 = "Accessibility off. Did you forget to check that?"
            r4.<init>(r5)     // Catch: java.lang.Throwable -> La8
            throw r4     // Catch: java.lang.Throwable -> La8
        L51:
            int r3 = r2.getEventType()     // Catch: java.lang.Throwable -> La8
            int r4 = r7.mRelevantEventTypes     // Catch: java.lang.Throwable -> La8
            r3 = r3 & r4
            if (r3 != 0) goto L5c
            monitor-exit(r0)     // Catch: java.lang.Throwable -> La8
            return
        L5c:
            int r3 = r7.mUserId     // Catch: java.lang.Throwable -> La8
            monitor-exit(r0)     // Catch: java.lang.Throwable -> La8
            long r4 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> L79 android.os.RemoteException -> L7b
            r1.sendAccessibilityEvent(r2, r3)     // Catch: java.lang.Throwable -> L73
            android.os.Binder.restoreCallingIdentity(r4)     // Catch: java.lang.Throwable -> L79 android.os.RemoteException -> L7b
            if (r8 == r2) goto L6f
        L6c:
            r8.recycle()
        L6f:
            r2.recycle()
            goto L9e
        L73:
            r0 = move-exception
            android.os.Binder.restoreCallingIdentity(r4)     // Catch: java.lang.Throwable -> L79 android.os.RemoteException -> L7b
            throw r0     // Catch: java.lang.Throwable -> L79 android.os.RemoteException -> L7b
        L79:
            r0 = move-exception
            goto L9f
        L7b:
            r0 = move-exception
            java.lang.String r4 = "AccessibilityManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L79
            r5.<init>()     // Catch: java.lang.Throwable -> L79
            java.lang.String r6 = "Error during sending "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> L79
            java.lang.StringBuilder r5 = r5.append(r2)     // Catch: java.lang.Throwable -> L79
            java.lang.String r6 = " "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> L79
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L79
            android.util.Log.e(r4, r5, r0)     // Catch: java.lang.Throwable -> L79
            if (r8 == r2) goto L6f
            goto L6c
        L9e:
            return
        L9f:
            if (r8 == r2) goto La4
            r8.recycle()
        La4:
            r2.recycle()
            throw r0
        La8:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> La8
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityManager.sendAccessibilityEvent(android.view.accessibility.AccessibilityEvent):void");
    }

    public void interrupt() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            if (!isEnabled()) {
                Looper myLooper = Looper.myLooper();
                if (myLooper == Looper.getMainLooper()) {
                    throw new IllegalStateException("Accessibility off. Did you forget to check that?");
                }
                Log.e(LOG_TAG, "Interrupt called with accessibility disabled");
                return;
            }
            int userId = this.mUserId;
            try {
                service.interrupt(userId);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while requesting interrupt from all services. ", re);
            }
        }
    }

    @Deprecated
    public List<ServiceInfo> getAccessibilityServiceList() {
        List<AccessibilityServiceInfo> infos = getInstalledAccessibilityServiceList();
        List<ServiceInfo> services = new ArrayList<>();
        int infoCount = infos.size();
        for (int i = 0; i < infoCount; i++) {
            AccessibilityServiceInfo info = infos.get(i);
            services.add(info.getResolveInfo().serviceInfo);
        }
        return Collections.unmodifiableList(services);
    }

    public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return Collections.emptyList();
            }
            int userId = this.mUserId;
            List<AccessibilityServiceInfo> services = null;
            try {
                services = service.getInstalledAccessibilityServiceList(userId).getList();
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while obtaining the installed AccessibilityServices. ", re);
            }
            if (this.mAccessibilityPolicy != null) {
                services = this.mAccessibilityPolicy.getInstalledAccessibilityServiceList(services);
            }
            if (services != null) {
                return Collections.unmodifiableList(services);
            }
            return Collections.emptyList();
        }
    }

    public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int feedbackTypeFlags) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return Collections.emptyList();
            }
            int userId = this.mUserId;
            List<AccessibilityServiceInfo> services = null;
            try {
                services = service.getEnabledAccessibilityServiceList(feedbackTypeFlags, userId);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while obtaining the enabled AccessibilityServices. ", re);
            }
            if (this.mAccessibilityPolicy != null) {
                services = this.mAccessibilityPolicy.getEnabledAccessibilityServiceList(feedbackTypeFlags, services);
            }
            if (services != null) {
                return Collections.unmodifiableList(services);
            }
            return Collections.emptyList();
        }
    }

    public boolean isAccessibilityServiceWarningRequired(AccessibilityServiceInfo info) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return true;
            }
            try {
                return service.isAccessibilityServiceWarningRequired(info);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while checking isAccessibilityServiceWarningRequired: ", re);
                return true;
            }
        }
    }

    public boolean addAccessibilityStateChangeListener(AccessibilityStateChangeListener listener) {
        addAccessibilityStateChangeListener(listener, null);
        return true;
    }

    public void addAccessibilityStateChangeListener(AccessibilityStateChangeListener listener, Handler handler) {
        synchronized (this.mLock) {
            this.mAccessibilityStateChangeListeners.put(listener, handler == null ? this.mHandler : handler);
        }
    }

    public boolean removeAccessibilityStateChangeListener(AccessibilityStateChangeListener listener) {
        boolean z;
        synchronized (this.mLock) {
            int index = this.mAccessibilityStateChangeListeners.indexOfKey(listener);
            this.mAccessibilityStateChangeListeners.remove(listener);
            z = index >= 0;
        }
        return z;
    }

    public boolean addTouchExplorationStateChangeListener(TouchExplorationStateChangeListener listener) {
        addTouchExplorationStateChangeListener(listener, null);
        return true;
    }

    public void addTouchExplorationStateChangeListener(TouchExplorationStateChangeListener listener, Handler handler) {
        synchronized (this.mLock) {
            this.mTouchExplorationStateChangeListeners.put(listener, handler == null ? this.mHandler : handler);
        }
    }

    public boolean removeTouchExplorationStateChangeListener(TouchExplorationStateChangeListener listener) {
        boolean z;
        synchronized (this.mLock) {
            int index = this.mTouchExplorationStateChangeListeners.indexOfKey(listener);
            this.mTouchExplorationStateChangeListeners.remove(listener);
            z = index >= 0;
        }
        return z;
    }

    public void addAccessibilityServicesStateChangeListener(Executor executor, AccessibilityServicesStateChangeListener listener) {
        synchronized (this.mLock) {
            this.mServicesStateChangeListeners.put(listener, executor);
        }
    }

    public void addAccessibilityServicesStateChangeListener(AccessibilityServicesStateChangeListener listener) {
        addAccessibilityServicesStateChangeListener(new HandlerExecutor(this.mHandler), listener);
    }

    public boolean removeAccessibilityServicesStateChangeListener(AccessibilityServicesStateChangeListener listener) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mServicesStateChangeListeners.remove(listener) != null;
        }
        return z;
    }

    public boolean isRequestFromAccessibilityTool() {
        return this.mRequestFromAccessibilityTool;
    }

    public void setRequestFromAccessibilityTool(boolean requestFromAccessibilityTool) {
        this.mRequestFromAccessibilityTool = requestFromAccessibilityTool;
    }

    public void addAccessibilityRequestPreparer(AccessibilityRequestPreparer preparer) {
        if (this.mRequestPreparerLists == null) {
            this.mRequestPreparerLists = new SparseArray<>(1);
        }
        int id = preparer.getAccessibilityViewId();
        List<AccessibilityRequestPreparer> requestPreparerList = this.mRequestPreparerLists.get(id);
        if (requestPreparerList == null) {
            requestPreparerList = new ArrayList(1);
            this.mRequestPreparerLists.put(id, requestPreparerList);
        }
        requestPreparerList.add(preparer);
    }

    public void removeAccessibilityRequestPreparer(AccessibilityRequestPreparer preparer) {
        int viewId;
        List<AccessibilityRequestPreparer> requestPreparerList;
        if (this.mRequestPreparerLists != null && (requestPreparerList = this.mRequestPreparerLists.get((viewId = preparer.getAccessibilityViewId()))) != null) {
            requestPreparerList.remove(preparer);
            if (requestPreparerList.isEmpty()) {
                this.mRequestPreparerLists.remove(viewId);
            }
        }
    }

    public int getRecommendedTimeoutMillis(int originalTimeout, int uiContentFlags) {
        boolean hasControls = (uiContentFlags & 4) != 0;
        boolean hasIconsOrText = ((uiContentFlags & 1) == 0 && (uiContentFlags & 2) == 0) ? false : true;
        int recommendedTimeout = originalTimeout;
        if (hasControls) {
            recommendedTimeout = Math.max(recommendedTimeout, this.mInteractiveUiTimeout);
        }
        if (hasIconsOrText) {
            return Math.max(recommendedTimeout, this.mNonInteractiveUiTimeout);
        }
        return recommendedTimeout;
    }

    public int getAccessibilityFocusStrokeWidth() {
        int i;
        synchronized (this.mLock) {
            i = this.mFocusStrokeWidth;
        }
        return i;
    }

    public int semGetAccessibilityFocusStrokeWidth(Context context) {
        int i;
        if (context.getDisplayId() == 0) {
            synchronized (this.mLock) {
                i = this.mFocusStrokeWidth;
            }
            return i;
        }
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                Log.e(LOG_TAG, "Error service is null");
                return this.mFocusStrokeWidth;
            }
            try {
                int dpi = service.convertPixelToDpi(this.mFocusStrokeWidth);
                return dipToPixel(context, dpi);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error performing convertPixelToDpi() ", re);
                return this.mFocusStrokeWidth;
            }
        }
    }

    private int dipToPixel(Context context, float pixels) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dpi = (int) (pixels / (displayMetrics.densityDpi / 160.0f));
        return (int) ((displayMetrics.density * dpi) + 0.5f);
    }

    public int getAccessibilityFocusColor() {
        int i;
        synchronized (this.mLock) {
            i = this.mFocusColor;
        }
        return i;
    }

    public boolean isA11yInteractionConnectionTraceEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = (this.mAccessibilityTracingState & 256) != 0;
        }
        return z;
    }

    public boolean isA11yInteractionConnectionCBTraceEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = (this.mAccessibilityTracingState & 512) != 0;
        }
        return z;
    }

    public boolean isA11yInteractionClientTraceEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = (this.mAccessibilityTracingState & 1024) != 0;
        }
        return z;
    }

    public boolean isA11yServiceTraceEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = (this.mAccessibilityTracingState & 2048) != 0;
        }
        return z;
    }

    public List<AccessibilityRequestPreparer> getRequestPreparersForAccessibilityId(int id) {
        if (this.mRequestPreparerLists == null) {
            return null;
        }
        return this.mRequestPreparerLists.get(id);
    }

    public void notifyPerformingAction(int actionId) {
        this.mPerformingAction = actionId;
    }

    public int getPerformingAction() {
        return this.mPerformingAction;
    }

    public void addHighTextContrastStateChangeListener(HighTextContrastChangeListener listener, Handler handler) {
        synchronized (this.mLock) {
            this.mHighTextContrastStateChangeListeners.put(listener, handler == null ? this.mHandler : handler);
        }
    }

    public void removeHighTextContrastStateChangeListener(HighTextContrastChangeListener listener) {
        synchronized (this.mLock) {
            this.mHighTextContrastStateChangeListeners.remove(listener);
        }
    }

    public void addAudioDescriptionRequestedChangeListener(Executor executor, AudioDescriptionRequestedChangeListener listener) {
        synchronized (this.mLock) {
            this.mAudioDescriptionRequestedChangeListeners.put(listener, executor);
        }
    }

    public boolean removeAudioDescriptionRequestedChangeListener(AudioDescriptionRequestedChangeListener listener) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mAudioDescriptionRequestedChangeListeners.remove(listener) != null;
        }
        return z;
    }

    public void setAccessibilityPolicy(AccessibilityPolicy policy) {
        synchronized (this.mLock) {
            this.mAccessibilityPolicy = policy;
        }
    }

    public boolean isAccessibilityVolumeStreamActive() {
        List<AccessibilityServiceInfo> serviceInfos = getEnabledAccessibilityServiceList(-1);
        for (int i = 0; i < serviceInfos.size(); i++) {
            if ((serviceInfos.get(i).flags & 128) != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean sendFingerprintGesture(int keyCode) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.sendFingerprintGesture(keyCode);
            } catch (RemoteException e) {
                return false;
            }
        }
    }

    @SystemApi
    public int getAccessibilityWindowId(IBinder windowToken) {
        if (windowToken == null) {
            return -1;
        }
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return -1;
            }
            try {
                return service.getAccessibilityWindowId(windowToken);
            } catch (RemoteException e) {
                return -1;
            }
        }
    }

    public void associateEmbeddedHierarchy(IBinder host, IBinder embedded) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.associateEmbeddedHierarchy(host, embedded);
            } catch (RemoteException e) {
            }
        }
    }

    public void disassociateEmbeddedHierarchy(IBinder token) {
        if (token == null) {
            return;
        }
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.disassociateEmbeddedHierarchy(token);
            } catch (RemoteException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStateLocked(int stateFlags) {
        boolean enabled = (stateFlags & 1) != 0;
        boolean touchExplorationEnabled = (stateFlags & 2) != 0;
        boolean highTextContrastEnabled = (stateFlags & 4) != 0;
        boolean audioDescriptionEnabled = (stateFlags & 4096) != 0;
        boolean wasEnabled = isEnabled();
        boolean wasTouchExplorationEnabled = this.mIsTouchExplorationEnabled;
        boolean wasHighTextContrastEnabled = this.mIsHighTextContrastEnabled;
        boolean wasAudioDescriptionByDefaultRequested = this.mIsAudioDescriptionByDefaultRequested;
        this.mIsEnabled = enabled;
        this.mIsTouchExplorationEnabled = touchExplorationEnabled;
        this.mIsHighTextContrastEnabled = highTextContrastEnabled;
        this.mIsAudioDescriptionByDefaultRequested = audioDescriptionEnabled;
        if (wasEnabled != isEnabled()) {
            notifyAccessibilityStateChanged();
        }
        if (wasTouchExplorationEnabled != touchExplorationEnabled) {
            notifyTouchExplorationStateChanged();
        }
        if (wasHighTextContrastEnabled != highTextContrastEnabled) {
            notifyHighTextContrastStateChanged();
        }
        if (wasAudioDescriptionByDefaultRequested != audioDescriptionEnabled) {
            notifyAudioDescriptionbyDefaultStateChanged();
        }
        updateAccessibilityTracingState(stateFlags);
    }

    public AccessibilityServiceInfo getInstalledServiceInfoWithComponentName(ComponentName componentName) {
        List<AccessibilityServiceInfo> installedServiceInfos = getInstalledAccessibilityServiceList();
        if (installedServiceInfos == null || componentName == null) {
            return null;
        }
        for (int i = 0; i < installedServiceInfos.size(); i++) {
            if (componentName.equals(installedServiceInfos.get(i).getComponentName())) {
                return installedServiceInfos.get(i);
            }
        }
        return null;
    }

    public int addAccessibilityInteractionConnection(IWindow windowToken, IBinder leashToken, String packageName, IAccessibilityInteractionConnection connection) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return -1;
            }
            int userId = this.mUserId;
            try {
                return service.addAccessibilityInteractionConnection(windowToken, leashToken, connection, packageName, userId);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while adding an accessibility interaction connection. ", re);
                return -1;
            }
        }
    }

    public void removeAccessibilityInteractionConnection(IWindow windowToken) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.removeAccessibilityInteractionConnection(windowToken);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while removing an accessibility interaction connection. ", re);
            }
        }
    }

    @SystemApi
    public void performAccessibilityShortcut() {
        performAccessibilityShortcut(null);
    }

    public void performAccessibilityShortcut(String targetName) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.performAccessibilityShortcut(targetName);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error performing accessibility shortcut. ", re);
            }
        }
    }

    public void enableShortcutsForTargets(boolean enable, int shortcutTypes, Set<String> targets, int userId) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.enableShortcutsForTargets(enable, shortcutTypes, targets.stream().toList(), userId);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
    }

    public Map<ComponentName, ComponentName> getA11yFeatureToTileMap(int userId) {
        ComponentName tileService;
        Map<ComponentName, ComponentName> a11yFeatureToTileMap = new ArrayMap<>();
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return a11yFeatureToTileMap;
            }
            try {
                Bundle a11yFeatureToTile = service.getA11yFeatureToTileMap(userId);
                for (String key : a11yFeatureToTile.keySet()) {
                    ComponentName feature = ComponentName.unflattenFromString(key);
                    if (feature != null && (tileService = (ComponentName) a11yFeatureToTile.getParcelable(key, ComponentName.class)) != null) {
                        a11yFeatureToTileMap.put(feature, tileService);
                    }
                }
                return a11yFeatureToTileMap;
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void registerSystemAction(RemoteAction action, int actionId) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.registerSystemAction(action, actionId);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error registering system action " + ((Object) action.getTitle()) + " ", re);
            }
        }
    }

    @SystemApi
    public void unregisterSystemAction(int actionId) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.unregisterSystemAction(actionId);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error unregistering system action with actionId " + actionId + " ", re);
            }
        }
    }

    public void notifyAccessibilityButtonClicked(int displayId) {
        notifyAccessibilityButtonClicked(displayId, null);
    }

    public void notifyAccessibilityButtonClicked(int displayId, String targetName) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.notifyAccessibilityButtonClicked(displayId, targetName);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while dispatching accessibility button click", re);
            }
        }
    }

    public void notifyAccessibilityButtonVisibilityChanged(boolean shown) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.notifyAccessibilityButtonVisibilityChanged(shown);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while dispatching accessibility button visibility change", re);
            }
        }
    }

    public boolean semIsAccessibilityButtonShown() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                boolean retVal = service.semIsAccessibilityButtonShown();
                return retVal;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "isAccessibilityButtonShown Exception:", re);
                return false;
            }
        }
    }

    public List<String> semGetExclusiveTaskList(Context context, String taskName) {
        ISemExclusiveTaskManager service;
        Log.d(LOG_TAG, "semGetExclusiveTaskList()");
        try {
            service = getExclusiveTaskManagerService(context);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "semGetExclusiveTaskList() Exception:", re);
        }
        if (service != null) {
            return service.getExclusiveTaskList(taskName);
        }
        Log.d(LOG_TAG, "ISemExclusiveTaskManager is null");
        return new ArrayList();
    }

    private ISemExclusiveTaskManager getExclusiveTaskManagerService(Context context) {
        SemUnionManager um = (SemUnionManager) context.getSystemService(Context.SEP_UNION_SERVICE);
        IBinder b = um.getSemSystemService(UnionConstants.SERVICE_EXCLUSIVE_TASK);
        ISemExclusiveTaskManager service = ISemExclusiveTaskManager.Stub.asInterface(b);
        return service;
    }

    public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection connection) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.setPictureInPictureActionReplacingConnection(connection);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error setting picture in picture action replacement", re);
            }
        }
    }

    public List<String> getAccessibilityShortcutTargets(int shortcutType) {
        IAccessibilityManager service;
        synchronized (this.mLock) {
            service = getServiceLocked();
        }
        if (service != null) {
            try {
                return service.getAccessibilityShortcutTargets(shortcutType);
            } catch (RemoteException re) {
                re.rethrowFromSystemServer();
            }
        }
        return Collections.emptyList();
    }

    public List<AccessibilityShortcutInfo> getInstalledAccessibilityShortcutListAsUser(Context context, int userId) {
        List<AccessibilityShortcutInfo> shortcutInfos = new ArrayList<>();
        Intent actionMain = new Intent(Intent.ACTION_MAIN);
        actionMain.addCategory(Intent.CATEGORY_ACCESSIBILITY_SHORTCUT_TARGET);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> installedShortcutList = packageManager.queryIntentActivitiesAsUser(actionMain, 819329, userId);
        for (int i = 0; i < installedShortcutList.size(); i++) {
            AccessibilityShortcutInfo shortcutInfo = getShortcutInfo(context, installedShortcutList.get(i));
            if (shortcutInfo != null) {
                shortcutInfos.add(shortcutInfo);
            }
        }
        return shortcutInfos;
    }

    public List<SemAccessibilityShortcutInfo> semGetInstalledAccessibilityShortcutInfoAsUser(Context context, int userId) {
        List<SemAccessibilityShortcutInfo> semAccessibilityShortcutInfos = new ArrayList<>();
        List<AccessibilityShortcutInfo> accessibilityShortcutInfos = getInstalledAccessibilityShortcutListAsUser(context, userId);
        PackageManager packageManager = context.getPackageManager();
        for (AccessibilityShortcutInfo info : accessibilityShortcutInfos) {
            String title = info.getActivityInfo().loadLabel(packageManager).toString();
            Drawable icon = info.getActivityInfo().loadIcon(packageManager);
            semAccessibilityShortcutInfos.add(new SemAccessibilityShortcutInfo(title, icon));
        }
        return semAccessibilityShortcutInfos;
    }

    public void semPerformAccessibilityButtonClick(int displayId, int shortcutType, String targetName) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.semPerformAccessibilityButtonClick(displayId, shortcutType, targetName);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "setMagnificationDisactivate Exception:", re);
            }
        }
    }

    private AccessibilityShortcutInfo getShortcutInfo(Context context, ResolveInfo resolveInfo) {
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        if (activityInfo == null || activityInfo.metaData == null || activityInfo.metaData.getInt(AccessibilityShortcutInfo.META_DATA) == 0) {
            return null;
        }
        try {
            return new AccessibilityShortcutInfo(context, activityInfo);
        } catch (IOException | XmlPullParserException exp) {
            Log.e(LOG_TAG, "Error while initializing AccessibilityShortcutInfo", exp);
            return null;
        }
    }

    public void setMagnificationConnection(IMagnificationConnection connection) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.setMagnificationConnection(connection);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error setting magnification connection", re);
            }
        }
    }

    public void setMagnificationDisactivate() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.setMagnificationDisactivate();
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "setMagnificationDisactivate Exception:", re);
            }
        }
    }

    public boolean isAudioDescriptionRequested() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            return this.mIsAudioDescriptionByDefaultRequested;
        }
    }

    public void setSystemAudioCaptioningEnabled(boolean isEnabled, int userId) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.setSystemAudioCaptioningEnabled(isEnabled, userId);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
    }

    public boolean isSystemAudioCaptioningUiEnabled(int userId) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.isSystemAudioCaptioningUiEnabled(userId);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
    }

    public void setSystemAudioCaptioningUiEnabled(boolean isEnabled, int userId) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.setSystemAudioCaptioningUiEnabled(isEnabled, userId);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
    }

    public void setAccessibilityWindowAttributes(int displayId, int windowId, AccessibilityWindowAttributes attributes) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.setAccessibilityWindowAttributes(displayId, windowId, this.mUserId, attributes);
            } catch (RemoteException re) {
                re.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean registerDisplayProxy(AccessibilityDisplayProxy proxy) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.registerProxyForDisplay(proxy.mServiceClient, proxy.getDisplayId());
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean unregisterDisplayProxy(AccessibilityDisplayProxy proxy) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.unregisterProxyForDisplay(proxy.getDisplayId());
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean startFlashNotificationSequence(Context context, int reason) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.startFlashNotificationSequence(context.getOpPackageName(), reason, this.mBinder);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while start flash notification sequence", re);
                return false;
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean stopFlashNotificationSequence(Context context) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.stopFlashNotificationSequence(context.getOpPackageName());
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while stop flash notification sequence", re);
                return false;
            }
        }
    }

    public boolean semStartFlashNotificationSequence(Context context, int reason) {
        return startFlashNotificationSequence(context, reason);
    }

    public boolean semStopFlashNotificationSequence(Context context) {
        return stopFlashNotificationSequence(context);
    }

    public boolean isCameraFlashNotificationRunning() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.isCameraFlashNotificationRunning();
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "failed to get Ccamera flash notification running state", re);
                return false;
            }
        }
    }

    public boolean startFlashNotificationEvent(Context context, int reason, String reasonPkg) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.startFlashNotificationEvent(context.getOpPackageName(), reason, reasonPkg);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while start flash notification event", re);
                return false;
            }
        }
    }

    public boolean isAccessibilityTargetAllowed(String packageName, int uid, int userId) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.isAccessibilityTargetAllowed(packageName, uid, userId);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while check accessibility target status", re);
                return false;
            }
        }
    }

    public boolean sendRestrictedDialogIntent(String packageName, int uid, int userId) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.sendRestrictedDialogIntent(packageName, uid, userId);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error while show restricted dialog", re);
                return false;
            }
        }
    }

    private IAccessibilityManager getServiceLocked() {
        if (this.mService == null) {
            tryConnectToServiceLocked(null);
        }
        return this.mService;
    }

    private void tryConnectToServiceLocked(IAccessibilityManager service) {
        if (service == null) {
            IBinder iBinder = ServiceManager.getService(Context.ACCESSIBILITY_SERVICE);
            if (iBinder == null) {
                return;
            } else {
                service = IAccessibilityManager.Stub.asInterface(iBinder);
            }
        }
        try {
            long userStateAndRelevantEvents = service.addClient(this.mClient, this.mUserId);
            setStateLocked(IntPair.first(userStateAndRelevantEvents));
            this.mRelevantEventTypes = IntPair.second(userStateAndRelevantEvents);
            updateUiTimeout(service.getRecommendedTimeoutMillis());
            updateFocusAppearanceLocked(service.getFocusStrokeWidth(), service.getFocusColor());
            this.mService = service;
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "AccessibilityManagerService is dead", re);
        }
    }

    public void notifyAccessibilityStateChanged() {
        synchronized (this.mLock) {
            if (this.mAccessibilityStateChangeListeners.isEmpty()) {
                return;
            }
            final boolean isEnabled = isEnabled();
            ArrayMap<AccessibilityStateChangeListener, Handler> listeners = new ArrayMap<>(this.mAccessibilityStateChangeListeners);
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; i++) {
                final AccessibilityStateChangeListener listener = listeners.keyAt(i);
                listeners.valueAt(i).post(new Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityManager.AccessibilityStateChangeListener.this.onAccessibilityStateChanged(isEnabled);
                    }
                });
            }
        }
    }

    private void notifyTouchExplorationStateChanged() {
        synchronized (this.mLock) {
            if (this.mTouchExplorationStateChangeListeners.isEmpty()) {
                return;
            }
            final boolean isTouchExplorationEnabled = this.mIsTouchExplorationEnabled;
            ArrayMap<TouchExplorationStateChangeListener, Handler> listeners = new ArrayMap<>(this.mTouchExplorationStateChangeListeners);
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; i++) {
                final TouchExplorationStateChangeListener listener = listeners.keyAt(i);
                listeners.valueAt(i).post(new Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityManager.TouchExplorationStateChangeListener.this.onTouchExplorationStateChanged(isTouchExplorationEnabled);
                    }
                });
            }
        }
    }

    private void notifyHighTextContrastStateChanged() {
        synchronized (this.mLock) {
            if (this.mHighTextContrastStateChangeListeners.isEmpty()) {
                return;
            }
            final boolean isHighTextContrastEnabled = this.mIsHighTextContrastEnabled;
            ArrayMap<HighTextContrastChangeListener, Handler> listeners = new ArrayMap<>(this.mHighTextContrastStateChangeListeners);
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; i++) {
                final HighTextContrastChangeListener listener = listeners.keyAt(i);
                listeners.valueAt(i).post(new Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityManager.HighTextContrastChangeListener.this.onHighTextContrastStateChanged(isHighTextContrastEnabled);
                    }
                });
            }
        }
    }

    private void notifyAudioDescriptionbyDefaultStateChanged() {
        synchronized (this.mLock) {
            if (this.mAudioDescriptionRequestedChangeListeners.isEmpty()) {
                return;
            }
            final boolean isAudioDescriptionByDefaultRequested = this.mIsAudioDescriptionByDefaultRequested;
            ArrayMap<AudioDescriptionRequestedChangeListener, Executor> listeners = new ArrayMap<>(this.mAudioDescriptionRequestedChangeListeners);
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; i++) {
                final AudioDescriptionRequestedChangeListener listener = listeners.keyAt(i);
                listeners.valueAt(i).execute(new Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityManager.AudioDescriptionRequestedChangeListener.this.onAudioDescriptionRequestedChanged(isAudioDescriptionByDefaultRequested);
                    }
                });
            }
        }
    }

    private void updateAccessibilityTracingState(int stateFlag) {
        synchronized (this.mLock) {
            this.mAccessibilityTracingState = stateFlag;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUiTimeout(long uiTimeout) {
        this.mInteractiveUiTimeout = IntPair.first(uiTimeout);
        this.mNonInteractiveUiTimeout = IntPair.second(uiTimeout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFocusAppearanceLocked(int strokeWidth, int color) {
        if (this.mFocusStrokeWidth == strokeWidth && this.mFocusColor == color) {
            return;
        }
        this.mFocusStrokeWidth = strokeWidth;
        this.mFocusColor = color;
    }

    private void initialFocusAppearanceLocked(Resources resource) {
        try {
            this.mFocusStrokeWidth = resource.getDimensionPixelSize(R.dimen.accessibility_focus_highlight_stroke_width);
            this.mFocusColor = resource.getColor(R.color.accessibility_focus_highlight_color);
        } catch (Resources.NotFoundException re) {
            this.mFocusStrokeWidth = (int) (resource.getDisplayMetrics().density * 4.0f);
            this.mFocusColor = -1086737152;
            Log.e(LOG_TAG, "Error while initialing the focus appearance data then setting to default value by hardcoded", re);
        }
    }

    public static boolean isAccessibilityButtonSupported() {
        Resources res = Resources.getSystem();
        return res.getBoolean(R.bool.config_showNavigationBar);
    }

    private final class MyCallback implements Handler.Callback {
        public static final int MSG_SET_STATE = 1;

        private MyCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    int state = message.arg1;
                    synchronized (AccessibilityManager.this.mLock) {
                        AccessibilityManager.this.setStateLocked(state);
                    }
                    return true;
                default:
                    return true;
            }
        }
    }

    public IAccessibilityManager.WindowTransformationSpec getWindowTransformationSpec(int windowId) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return null;
            }
            try {
                return service.getWindowTransformationSpec(windowId);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
    }

    public void attachAccessibilityOverlayToDisplay(int displayId, SurfaceControl surfaceControl) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.attachAccessibilityOverlayToDisplay(displayId, surfaceControl);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
    }

    public void notifyQuickSettingsTilesChanged(int userId, List<ComponentName> tileComponentNames) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.notifyQuickSettingsTilesChanged(userId, tileComponentNames);
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
    }

    public void semTurnOffAccessibilityService(int stateFlags) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                if (semIsAccessibilityServiceEnabled(stateFlags)) {
                    service.semTurnOffAccessibilityService(stateFlags);
                }
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semTurnOffAccessibilityService exception.", re);
            }
        }
    }

    public void semTurnOnAccessibilityService(int stateFlags) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                if (!semIsAccessibilityServiceEnabled(stateFlags)) {
                    service.semTurnOnAccessibilityService(stateFlags);
                }
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semTurnOnAccessibilityService exception.", re);
            }
        }
    }

    public boolean semIsAccessibilityServiceEnabled(int stateFlags) {
        boolean retVal = false;
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                if (isEnabled()) {
                    retVal = service.semIsAccessibilityServiceEnabled(stateFlags);
                } else {
                    Log.d(LOG_TAG, "accessibility service is not enabled");
                }
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semIsAccessibilityServiceEnabled exception.", re);
            }
            return retVal;
        }
    }

    public boolean semSetMdnieColorBlind(boolean enable, float userParameter) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                boolean retVal = service.semSetColorBlind(enable, userParameter);
                return retVal;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semSetColorBlind Exception:", re);
                return false;
            }
        }
    }

    public boolean semCheckMdnieColorBlind(int[] nums) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                boolean retVal = service.semCheckMdnieColorBlind(nums);
                return retVal;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semCheckMdnieColorBlind Exception:", re);
                return false;
            }
        }
    }

    public boolean semSetMdnieAccessibilityMode(int mode, boolean enable) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                boolean retVal = service.semSetMdnieAccessibilityMode(mode, enable);
                return retVal;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semSetMdnieAccessibilityMode Exception:", re);
                return false;
            }
        }
    }

    public boolean semEnableMdnieColorFilter(int color, int opacity) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                boolean retVal = service.semEnableMdnieColorFilter(color, opacity);
                return retVal;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semEnableMdnieColorFilter Exception:", re);
                return false;
            }
        }
    }

    public boolean semDisableMdnieColorFilter() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                boolean retVal = service.semDisableMdnieColorFilter();
                return retVal;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semDisableMdnieColorFilter Exception:", re);
                return false;
            }
        }
    }

    public void semRegisterAssistantMenu(IBinder iBinder) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                Log.e(LOG_TAG, "semRegisterAssistantMenu invoking from manager start:");
                service.semRegisterAssistantMenu(iBinder);
                Log.e(LOG_TAG, "semRegisterAssistantMenu invoking from manager end:");
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semRegisterAssistantMenu Exception:", re);
            }
        }
    }

    public void semUpdateAssitantMenu(Bundle bundle) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.semUpdateAssitantMenu(bundle);
                Log.e(LOG_TAG, "semUpdateAssitantMenu invoking from manager:");
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semUpdateAssitantMenu Exception:", re);
            }
        }
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public void semOpenDeviceOptions() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.semOpenDeviceOptions();
                Log.e(LOG_TAG, "Open Device Options from manager:");
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Open Device Options Exception:", re);
            }
        }
    }

    public void semSetTwoFingerGestureRecognitionEnabled(boolean enable) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            if (!this.mIsEnabled) {
                throw new IllegalStateException("Accessibility off. Did you forget to check that?");
            }
            try {
                Log.i(LOG_TAG, "AccessibilityManager - semSetTwoFingerGestureRecognitionEnabled: " + enable);
                service.semSetTwoFingerGestureRecognitionEnabled(enable);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semSetTwoFingerGestureRecognitionEnabled Exception:", re);
            }
        }
    }

    public boolean isTwoFingerGestureRecognitionEnabled() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                boolean value = service.isTwoFingerGestureRecognitionEnabled();
                return value;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "isTwoFingerGestureRecognitionEnabled Exception:", re);
                return false;
            }
        }
    }

    public boolean semIsScreenReaderEnabled() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                boolean value = service.isScreenReaderEnabled();
                return value;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semIsScreenReaderEnabled Exception : ", re);
                return false;
            }
        }
    }

    public String semGetScreenReaderName() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return "";
            }
            try {
                String name = service.getScreenReaderName();
                return name;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semGetScreenReaderName Exception : ", re);
                return "";
            }
        }
    }

    public void semSetScreenReaderEnabled(boolean enable) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.setScreenReaderEnabled(enable);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semSetScreenReaderEnabled Exception : ", re);
            }
        }
    }

    public boolean semIsDarkScreenMode() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                boolean result = service.semIsDarkScreenMode();
                return result;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semIsDarkScreenMode Exception:", re);
                return false;
            }
        }
    }

    public void semToggleDarkScreenMode() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.semToggleDarkScreenMode();
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semToggleDarkScreenMode Exception:", re);
            }
        }
    }

    public void semLockNow() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.semLockNow();
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semLockNow Exception:", re);
            }
        }
    }

    @Deprecated(forRemoval = true, since = "15.0")
    public boolean semStartFlashNotification(Context context) {
        return startFlashNotificationSequence(context, 3);
    }

    @Deprecated(forRemoval = true, since = "15.0")
    public boolean semStopFlashNotification(Context context) {
        return stopFlashNotificationSequence(context);
    }

    public boolean semEnableAirGestureWakeUp() {
        return OnStartGestureWakeup();
    }

    public boolean semDisableAirGestureWakeUp() {
        return OnStopGestureWakeup();
    }

    public boolean OnStartGestureWakeup() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                boolean result = service.OnStartGestureWakeup();
                return result;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "OnStartGestureWakeup Exception:", re);
                return false;
            }
        }
    }

    public boolean OnStopGestureWakeup() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                boolean result = service.OnStopGestureWakeup();
                return result;
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "OnStopGestureWakeup Exception:", re);
                return false;
            }
        }
    }

    public void semDumpCallStack(String callStack) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.semDumpCallStack(callStack);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "semDumpCallStack Exception:", re);
            }
        }
    }

    public void performAccessibilityDirectAccess() {
        performAccessibilityDirectAccess(null);
    }

    public void performAccessibilityDirectAccess(String targetName) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.performAccessibilityDirectAccess(targetName);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error performing accessibility direct access. ", re);
            }
        }
    }

    public Rect semGetWindowMagnificationBounds() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return null;
            }
            try {
                return service.semGetWindowMagnificationBounds();
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error Get Magnification Bounds. ", re);
                return null;
            }
        }
    }

    public float semGetWindowMagnificationScale() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return 0.0f;
            }
            try {
                return service.semGetWindowMagnificationScale();
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error Set scale AM Magnification. ", re);
                return 0.0f;
            }
        }
    }

    public void semEnableWindowMagnification(int center_x, int center_y) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.semEnableWindowMagnification(center_x, center_y);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error Enable AM Magnification. ", re);
            }
        }
    }

    public void semDisableWindowMagnification() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.semDisableWindowMagnification();
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error Disable AM Magnification. ", re);
            }
        }
    }

    public void semMoveWindowMagnification(float offsetX, float offsetY) {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return;
            }
            try {
                service.semMoveWindowMagnification(offsetX, offsetY);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error Move AM Magnification. ", re);
            }
        }
    }

    public boolean semIsWindowMagnificationEnabled() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.semIsWindowMagnificationEnabled();
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error Set scale AM Magnification. ", re);
                return false;
            }
        }
    }

    public boolean isActivatedMagnification() {
        synchronized (this.mLock) {
            IAccessibilityManager service = getServiceLocked();
            if (service == null) {
                return false;
            }
            try {
                return service.isActivatedMagnification();
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error get Magnification enable value. ", re);
                return false;
            }
        }
    }
}
