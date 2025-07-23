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
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.ArrayMap;
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
    public static final int AUTOCLICK_CURSOR_AREA_INCREMENT_SIZE = 20;
    public static final int AUTOCLICK_CURSOR_AREA_SIZE_DEFAULT = 60;
    public static final int AUTOCLICK_CURSOR_AREA_SIZE_MAX = 100;
    public static final int AUTOCLICK_CURSOR_AREA_SIZE_MIN = 20;
    public static final int AUTOCLICK_DELAY_DEFAULT = 600;
    public static final boolean AUTOCLICK_IGNORE_MINOR_CURSOR_MOVEMENT_DEFAULT = false;
    public static final boolean AUTOCLICK_REVERT_TO_LEFT_CLICK_DEFAULT = true;
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
    private final ArrayMap<AccessibilityStateChangeListener, Handler> mAccessibilityStateChangeListeners;
    int mAccessibilityTracingState;
    private final ArrayMap<AudioDescriptionRequestedChangeListener, Executor> mAudioDescriptionRequestedChangeListeners;
    private final Binder mBinder;
    final Handler.Callback mCallback;
    private final IAccessibilityManagerClient.Stub mClient;
    private int mFocusColor;
    private int mFocusStrokeWidth;
    final Handler mHandler;
    private final ArrayMap<HighContrastTextStateChangeListener, Executor> mHighContrastTextStateChangeListeners;
    int mInteractiveUiTimeout;
    boolean mIsAudioDescriptionByDefaultRequested;
    boolean mIsEnabled;
    boolean mIsHighContrastTextEnabled;
    boolean mIsTouchExplorationEnabled;
    private final Object mLock;
    int mNonInteractiveUiTimeout;
    private int mPerformingAction;
    int mRelevantEventTypes;
    private boolean mRequestFromAccessibilityTool;
    private SparseArray<List<AccessibilityRequestPreparer>> mRequestPreparerLists;
    private IAccessibilityManager mService;
    private final ArrayMap<AccessibilityServicesStateChangeListener, Executor> mServicesStateChangeListeners;
    private final ArrayMap<TouchExplorationStateChangeListener, Handler> mTouchExplorationStateChangeListeners;
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

    public interface HighContrastTextStateChangeListener {
        void onHighContrastTextStateChanged(boolean z);
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
        public void setState(int i) {
            AccessibilityManager.this.mHandler.obtainMessage(1, i, 0).sendToTarget();
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void notifyServicesStateChanged(long j) {
            AccessibilityManager.this.updateUiTimeout(j);
            synchronized (AccessibilityManager.this.mLock) {
                if (AccessibilityManager.this.mServicesStateChangeListeners.isEmpty()) {
                    return;
                }
                int size = new ArrayMap(AccessibilityManager.this.mServicesStateChangeListeners).size();
                for (int i = 0; i < size; i++) {
                    final AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener = (AccessibilityServicesStateChangeListener) AccessibilityManager.this.mServicesStateChangeListeners.keyAt(i);
                    ((Executor) AccessibilityManager.this.mServicesStateChangeListeners.valueAt(i)).execute(new Runnable() { // from class: android.view.accessibility.AccessibilityManager$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AccessibilityManager.AnonymousClass1.this.lambda$notifyServicesStateChanged$0(accessibilityServicesStateChangeListener);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyServicesStateChanged$0(AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener) {
            accessibilityServicesStateChangeListener.onAccessibilityServicesStateChanged(AccessibilityManager.this);
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setRelevantEventTypes(int i) {
            AccessibilityManager.this.mRelevantEventTypes = i;
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setFocusAppearance(int i, int i2) {
            synchronized (AccessibilityManager.this.mLock) {
                AccessibilityManager.this.updateFocusAppearanceLocked(i, i2);
            }
        }
    }

    public static AccessibilityManager getInstance(Context context) {
        int i;
        synchronized (sInstanceSync) {
            if (sInstance == null) {
                if (Binder.getCallingUid() != 1000 && context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS) != 0 && context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) != 0) {
                    i = context.getUserId();
                    sInstance = new AccessibilityManager(context, null, i);
                }
                i = -2;
                sInstance = new AccessibilityManager(context, null, i);
            }
        }
        return sInstance;
    }

    public AccessibilityManager(Context context, IAccessibilityManager iAccessibilityManager, int i) {
        Object obj = new Object();
        this.mLock = obj;
        this.mRelevantEventTypes = -1;
        this.mAccessibilityTracingState = 0;
        this.mPerformingAction = 0;
        this.mAccessibilityStateChangeListeners = new ArrayMap<>();
        this.mTouchExplorationStateChangeListeners = new ArrayMap<>();
        this.mHighContrastTextStateChangeListeners = new ArrayMap<>();
        this.mServicesStateChangeListeners = new ArrayMap<>();
        this.mAudioDescriptionRequestedChangeListeners = new ArrayMap<>();
        this.mBinder = new Binder();
        this.mClient = new AnonymousClass1();
        MyCallback myCallback = new MyCallback();
        this.mCallback = myCallback;
        this.mHandler = new Handler(context.getMainLooper(), myCallback);
        this.mUserId = i;
        synchronized (obj) {
            initialFocusAppearanceLocked(context.getResources());
            tryConnectToServiceLocked(iAccessibilityManager);
        }
    }

    public AccessibilityManager(Context context, Handler handler, IAccessibilityManager iAccessibilityManager, int i, boolean z) {
        Object obj = new Object();
        this.mLock = obj;
        this.mRelevantEventTypes = -1;
        this.mAccessibilityTracingState = 0;
        this.mPerformingAction = 0;
        this.mAccessibilityStateChangeListeners = new ArrayMap<>();
        this.mTouchExplorationStateChangeListeners = new ArrayMap<>();
        this.mHighContrastTextStateChangeListeners = new ArrayMap<>();
        this.mServicesStateChangeListeners = new ArrayMap<>();
        this.mAudioDescriptionRequestedChangeListeners = new ArrayMap<>();
        this.mBinder = new Binder();
        this.mClient = new AnonymousClass1();
        this.mCallback = new MyCallback();
        this.mHandler = handler;
        this.mUserId = i;
        synchronized (obj) {
            initialFocusAppearanceLocked(context.getResources());
            if (z) {
                tryConnectToServiceLocked(iAccessibilityManager);
            }
        }
    }

    public IAccessibilityManagerClient getClient() {
        return this.mClient;
    }

    public boolean removeClient() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.removeClient(this.mClient, this.mUserId);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "AccessibilityManagerService is dead", e);
                return false;
            }
        }
    }

    public Handler.Callback getCallback() {
        return this.mCallback;
    }

    public boolean isEnabled() {
        boolean z;
        AccessibilityPolicy accessibilityPolicy;
        synchronized (this.mLock) {
            z = this.mIsEnabled || hasAnyDirectConnection() || ((accessibilityPolicy = this.mAccessibilityPolicy) != null && accessibilityPolicy.isEnabled(this.mIsEnabled));
        }
        return z;
    }

    public boolean hasAnyDirectConnection() {
        return AccessibilityInteractionClient.hasAnyDirectConnection();
    }

    public boolean isTouchExplorationEnabled() {
        synchronized (this.mLock) {
            if (getServiceLocked() == null) {
                return false;
            }
            return this.mIsTouchExplorationEnabled;
        }
    }

    public boolean isHighContrastTextEnabled() {
        synchronized (this.mLock) {
            if (getServiceLocked() == null) {
                return false;
            }
            return this.mIsHighContrastTextEnabled;
        }
    }

    public void sendAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent accessibilityEvent2;
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            accessibilityEvent.setEventTime(SystemClock.uptimeMillis());
            if (accessibilityEvent.getAction() == 0) {
                accessibilityEvent.setAction(this.mPerformingAction);
            }
            AccessibilityPolicy accessibilityPolicy = this.mAccessibilityPolicy;
            if (accessibilityPolicy != null) {
                accessibilityEvent2 = accessibilityPolicy.onAccessibilityEvent(accessibilityEvent, this.mIsEnabled, this.mRelevantEventTypes);
                if (accessibilityEvent2 == null) {
                    return;
                }
            } else {
                accessibilityEvent2 = accessibilityEvent;
            }
            if (!isEnabled()) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    throw new IllegalStateException("Accessibility off. Did you forget to check that?");
                }
                Log.e(LOG_TAG, "AccessibilityEvent sent with accessibility disabled");
                return;
            }
            if ((accessibilityEvent2.getEventType() & this.mRelevantEventTypes) == 0) {
                return;
            }
            int i = this.mUserId;
            try {
                try {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        serviceLocked.sendAccessibilityEvent(accessibilityEvent2, i);
                        if (accessibilityEvent != accessibilityEvent2) {
                            accessibilityEvent.recycle();
                        }
                        accessibilityEvent2.recycle();
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (RemoteException e) {
                    Log.e(LOG_TAG, "Error during sending " + accessibilityEvent2 + " ", e);
                    if (accessibilityEvent != accessibilityEvent2) {
                        accessibilityEvent.recycle();
                    }
                    accessibilityEvent2.recycle();
                }
            } catch (Throwable th) {
                if (accessibilityEvent != accessibilityEvent2) {
                    accessibilityEvent.recycle();
                }
                accessibilityEvent2.recycle();
                throw th;
            }
        }
    }

    public void interrupt() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            if (!isEnabled()) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    throw new IllegalStateException("Accessibility off. Did you forget to check that?");
                }
                Log.e(LOG_TAG, "Interrupt called with accessibility disabled");
            } else {
                int i = this.mUserId;
                try {
                    serviceLocked.interrupt(i);
                } catch (RemoteException e) {
                    Log.e(LOG_TAG, "Error while requesting interrupt from all services. ", e);
                }
            }
        }
    }

    @Deprecated
    public List<ServiceInfo> getAccessibilityServiceList() {
        List<AccessibilityServiceInfo> installedAccessibilityServiceList = getInstalledAccessibilityServiceList();
        ArrayList arrayList = new ArrayList();
        int size = installedAccessibilityServiceList.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(installedAccessibilityServiceList.get(i).getResolveInfo().serviceInfo);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList() {
        List<AccessibilityServiceInfo> list;
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return Collections.EMPTY_LIST;
            }
            int i = this.mUserId;
            try {
                list = serviceLocked.getInstalledAccessibilityServiceList(i).getList();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while obtaining the installed AccessibilityServices. ", e);
                list = null;
            }
            AccessibilityPolicy accessibilityPolicy = this.mAccessibilityPolicy;
            if (accessibilityPolicy != null) {
                list = accessibilityPolicy.getInstalledAccessibilityServiceList(list);
            }
            if (list != null) {
                return Collections.unmodifiableList(list);
            }
            return Collections.EMPTY_LIST;
        }
    }

    public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, int i2) {
        List<AccessibilityServiceInfo> list;
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return Collections.EMPTY_LIST;
            }
            try {
                list = serviceLocked.getEnabledAccessibilityServiceList(i, i2);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while obtaining the enabled AccessibilityServices. ", e);
                list = null;
            }
            AccessibilityPolicy accessibilityPolicy = this.mAccessibilityPolicy;
            if (accessibilityPolicy != null) {
                list = accessibilityPolicy.getEnabledAccessibilityServiceList(i, list);
            }
            if (list != null) {
                return Collections.unmodifiableList(list);
            }
            return Collections.EMPTY_LIST;
        }
    }

    public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mUserId;
        }
        return getEnabledAccessibilityServiceList(i, i2);
    }

    public boolean isAccessibilityServiceWarningRequired(AccessibilityServiceInfo accessibilityServiceInfo) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return true;
            }
            try {
                return serviceLocked.isAccessibilityServiceWarningRequired(accessibilityServiceInfo);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while checking isAccessibilityServiceWarningRequired: ", e);
                return true;
            }
        }
    }

    public boolean addAccessibilityStateChangeListener(AccessibilityStateChangeListener accessibilityStateChangeListener) {
        addAccessibilityStateChangeListener(accessibilityStateChangeListener, null);
        return true;
    }

    public void addAccessibilityStateChangeListener(AccessibilityStateChangeListener accessibilityStateChangeListener, Handler handler) {
        synchronized (this.mLock) {
            ArrayMap<AccessibilityStateChangeListener, Handler> arrayMap = this.mAccessibilityStateChangeListeners;
            if (handler == null) {
                handler = this.mHandler;
            }
            arrayMap.put(accessibilityStateChangeListener, handler);
        }
    }

    public boolean removeAccessibilityStateChangeListener(AccessibilityStateChangeListener accessibilityStateChangeListener) {
        boolean z;
        synchronized (this.mLock) {
            int indexOfKey = this.mAccessibilityStateChangeListeners.indexOfKey(accessibilityStateChangeListener);
            this.mAccessibilityStateChangeListeners.remove(accessibilityStateChangeListener);
            z = indexOfKey >= 0;
        }
        return z;
    }

    public boolean addTouchExplorationStateChangeListener(TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        addTouchExplorationStateChangeListener(touchExplorationStateChangeListener, null);
        return true;
    }

    public void addTouchExplorationStateChangeListener(TouchExplorationStateChangeListener touchExplorationStateChangeListener, Handler handler) {
        synchronized (this.mLock) {
            ArrayMap<TouchExplorationStateChangeListener, Handler> arrayMap = this.mTouchExplorationStateChangeListeners;
            if (handler == null) {
                handler = this.mHandler;
            }
            arrayMap.put(touchExplorationStateChangeListener, handler);
        }
    }

    public boolean removeTouchExplorationStateChangeListener(TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        boolean z;
        synchronized (this.mLock) {
            int indexOfKey = this.mTouchExplorationStateChangeListeners.indexOfKey(touchExplorationStateChangeListener);
            this.mTouchExplorationStateChangeListeners.remove(touchExplorationStateChangeListener);
            z = indexOfKey >= 0;
        }
        return z;
    }

    public void addAccessibilityServicesStateChangeListener(Executor executor, AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener) {
        synchronized (this.mLock) {
            this.mServicesStateChangeListeners.put(accessibilityServicesStateChangeListener, executor);
        }
    }

    public void addAccessibilityServicesStateChangeListener(AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener) {
        addAccessibilityServicesStateChangeListener(new HandlerExecutor(this.mHandler), accessibilityServicesStateChangeListener);
    }

    public boolean removeAccessibilityServicesStateChangeListener(AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mServicesStateChangeListeners.remove(accessibilityServicesStateChangeListener) != null;
        }
        return z;
    }

    public void registerUserInitializationCompleteCallback(IUserInitializationCompleteCallback iUserInitializationCompleteCallback) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.registerUserInitializationCompleteCallback(iUserInitializationCompleteCallback);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while registering userInitializationCompleteCallback. ", e);
            }
        }
    }

    public void unregisterUserInitializationCompleteCallback(IUserInitializationCompleteCallback iUserInitializationCompleteCallback) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.unregisterUserInitializationCompleteCallback(iUserInitializationCompleteCallback);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while unregistering userInitializationCompleteCallback. ", e);
            }
        }
    }

    public boolean isRequestFromAccessibilityTool() {
        return this.mRequestFromAccessibilityTool;
    }

    public void setRequestFromAccessibilityTool(boolean z) {
        this.mRequestFromAccessibilityTool = z;
    }

    public void addAccessibilityRequestPreparer(AccessibilityRequestPreparer accessibilityRequestPreparer) {
        if (this.mRequestPreparerLists == null) {
            this.mRequestPreparerLists = new SparseArray<>(1);
        }
        int accessibilityViewId = accessibilityRequestPreparer.getAccessibilityViewId();
        List<AccessibilityRequestPreparer> list = this.mRequestPreparerLists.get(accessibilityViewId);
        if (list == null) {
            list = new ArrayList<>(1);
            this.mRequestPreparerLists.put(accessibilityViewId, list);
        }
        list.add(accessibilityRequestPreparer);
    }

    public void removeAccessibilityRequestPreparer(AccessibilityRequestPreparer accessibilityRequestPreparer) {
        int accessibilityViewId;
        List<AccessibilityRequestPreparer> list;
        if (this.mRequestPreparerLists == null || (list = this.mRequestPreparerLists.get((accessibilityViewId = accessibilityRequestPreparer.getAccessibilityViewId()))) == null) {
            return;
        }
        list.remove(accessibilityRequestPreparer);
        if (list.isEmpty()) {
            this.mRequestPreparerLists.remove(accessibilityViewId);
        }
    }

    public int getRecommendedTimeoutMillis(int i, int i2) {
        boolean z = (i2 & 4) != 0;
        boolean z2 = ((i2 & 1) == 0 && (i2 & 2) == 0) ? false : true;
        if (z) {
            i = Math.max(i, this.mInteractiveUiTimeout);
        }
        return z2 ? Math.max(i, this.mNonInteractiveUiTimeout) : i;
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
            if (getServiceLocked() == null) {
                Log.e(LOG_TAG, "Error service is null");
                return this.mFocusStrokeWidth;
            }
            try {
                return dipToPixel(context, r1.convertPixelToDpi(this.mFocusStrokeWidth));
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error performing convertPixelToDpi() ", e);
                return this.mFocusStrokeWidth;
            }
        }
    }

    private int dipToPixel(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * ((int) (f / (r1.densityDpi / 160.0f)))) + 0.5f);
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

    public List<AccessibilityRequestPreparer> getRequestPreparersForAccessibilityId(int i) {
        SparseArray<List<AccessibilityRequestPreparer>> sparseArray = this.mRequestPreparerLists;
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i);
    }

    public void notifyPerformingAction(int i) {
        this.mPerformingAction = i;
    }

    public int getPerformingAction() {
        return this.mPerformingAction;
    }

    public void addHighContrastTextStateChangeListener(Executor executor, HighContrastTextStateChangeListener highContrastTextStateChangeListener) {
        synchronized (this.mLock) {
            this.mHighContrastTextStateChangeListeners.put(highContrastTextStateChangeListener, executor);
        }
    }

    public void removeHighContrastTextStateChangeListener(HighContrastTextStateChangeListener highContrastTextStateChangeListener) {
        synchronized (this.mLock) {
            this.mHighContrastTextStateChangeListeners.remove(highContrastTextStateChangeListener);
        }
    }

    public void addAudioDescriptionRequestedChangeListener(Executor executor, AudioDescriptionRequestedChangeListener audioDescriptionRequestedChangeListener) {
        synchronized (this.mLock) {
            this.mAudioDescriptionRequestedChangeListeners.put(audioDescriptionRequestedChangeListener, executor);
        }
    }

    public boolean removeAudioDescriptionRequestedChangeListener(AudioDescriptionRequestedChangeListener audioDescriptionRequestedChangeListener) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mAudioDescriptionRequestedChangeListeners.remove(audioDescriptionRequestedChangeListener) != null;
        }
        return z;
    }

    public void setAccessibilityPolicy(AccessibilityPolicy accessibilityPolicy) {
        synchronized (this.mLock) {
            this.mAccessibilityPolicy = accessibilityPolicy;
        }
    }

    public boolean isAccessibilityVolumeStreamActive() {
        List<AccessibilityServiceInfo> enabledAccessibilityServiceList = getEnabledAccessibilityServiceList(-1);
        for (int i = 0; i < enabledAccessibilityServiceList.size(); i++) {
            if ((enabledAccessibilityServiceList.get(i).flags & 128) != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean sendFingerprintGesture(int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.sendFingerprintGesture(i);
            } catch (RemoteException unused) {
                return false;
            }
        }
    }

    @SystemApi
    public int getAccessibilityWindowId(IBinder iBinder) {
        if (iBinder == null) {
            return -1;
        }
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return -1;
            }
            try {
                return serviceLocked.getAccessibilityWindowId(iBinder);
            } catch (RemoteException unused) {
                return -1;
            }
        }
    }

    public void associateEmbeddedHierarchy(IBinder iBinder, IBinder iBinder2) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.associateEmbeddedHierarchy(iBinder, iBinder2);
            } catch (RemoteException unused) {
            }
        }
    }

    public void disassociateEmbeddedHierarchy(IBinder iBinder) {
        if (iBinder == null) {
            return;
        }
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.disassociateEmbeddedHierarchy(iBinder);
            } catch (RemoteException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStateLocked(int i) {
        boolean z = (i & 1) != 0;
        boolean z2 = (i & 2) != 0;
        boolean z3 = (i & 4) != 0;
        boolean z4 = (i & 4096) != 0;
        boolean isEnabled = isEnabled();
        boolean z5 = this.mIsTouchExplorationEnabled;
        boolean z6 = this.mIsHighContrastTextEnabled;
        boolean z7 = this.mIsAudioDescriptionByDefaultRequested;
        this.mIsEnabled = z;
        this.mIsTouchExplorationEnabled = z2;
        this.mIsHighContrastTextEnabled = z3;
        this.mIsAudioDescriptionByDefaultRequested = z4;
        if (isEnabled != isEnabled()) {
            notifyAccessibilityStateChanged();
        }
        if (z5 != z2) {
            notifyTouchExplorationStateChanged();
        }
        if (z6 != z3) {
            notifyHighContrastTextStateChanged();
        }
        if (z7 != z4) {
            notifyAudioDescriptionbyDefaultStateChanged();
        }
        updateAccessibilityTracingState(i);
    }

    public AccessibilityServiceInfo getInstalledServiceInfoWithComponentName(ComponentName componentName) {
        List<AccessibilityServiceInfo> installedAccessibilityServiceList = getInstalledAccessibilityServiceList();
        if (installedAccessibilityServiceList != null && componentName != null) {
            for (int i = 0; i < installedAccessibilityServiceList.size(); i++) {
                if (componentName.equals(installedAccessibilityServiceList.get(i).getComponentName())) {
                    return installedAccessibilityServiceList.get(i);
                }
            }
        }
        return null;
    }

    public int addAccessibilityInteractionConnection(IWindow iWindow, IBinder iBinder, String str, IAccessibilityInteractionConnection iAccessibilityInteractionConnection) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return -1;
            }
            int i = this.mUserId;
            try {
                return serviceLocked.addAccessibilityInteractionConnection(iWindow, iBinder, iAccessibilityInteractionConnection, str, i);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while adding an accessibility interaction connection. ", e);
                return -1;
            }
        }
    }

    public void removeAccessibilityInteractionConnection(IWindow iWindow) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.removeAccessibilityInteractionConnection(iWindow);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while removing an accessibility interaction connection. ", e);
            }
        }
    }

    @SystemApi
    public void performAccessibilityShortcut() {
        performAccessibilityShortcut(0, 2, null);
    }

    public void performAccessibilityShortcut(int i, int i2, String str) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.performAccessibilityShortcut(i, i2, str);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error performing accessibility shortcut. ", e);
            }
        }
    }

    public void enableShortcutsForTargets(boolean z, int i, Set<String> set, int i2) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.enableShortcutsForTargets(z, i, set.stream().toList(), i2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public Map<ComponentName, ComponentName> getA11yFeatureToTileMap(int i) {
        ComponentName componentName;
        ArrayMap arrayMap = new ArrayMap();
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return arrayMap;
            }
            try {
                Bundle a11yFeatureToTileMap = serviceLocked.getA11yFeatureToTileMap(i);
                for (String str : a11yFeatureToTileMap.keySet()) {
                    ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                    if (unflattenFromString != null && (componentName = (ComponentName) a11yFeatureToTileMap.getParcelable(str, ComponentName.class)) != null) {
                        arrayMap.put(unflattenFromString, componentName);
                    }
                }
                return arrayMap;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void registerSystemAction(RemoteAction remoteAction, int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.registerSystemAction(remoteAction, i);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error registering system action " + ((Object) remoteAction.getTitle()) + " ", e);
            }
        }
    }

    @SystemApi
    public void unregisterSystemAction(int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.unregisterSystemAction(i);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error unregistering system action with actionId " + i + " ", e);
            }
        }
    }

    public void notifyAccessibilityButtonClicked(int i) {
        notifyAccessibilityButtonClicked(i, null);
    }

    public void notifyAccessibilityButtonClicked(int i, String str) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.notifyAccessibilityButtonClicked(i, str);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while dispatching accessibility button click", e);
            }
        }
    }

    public void notifyAccessibilityButtonLongClicked(int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.notifyAccessibilityButtonLongClicked(i);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while dispatching accessibility button long click. ", e);
            }
        }
    }

    public void notifyAccessibilityButtonVisibilityChanged(boolean z) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.notifyAccessibilityButtonVisibilityChanged(z);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while dispatching accessibility button visibility change", e);
            }
        }
    }

    public boolean semIsAccessibilityButtonShown() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.semIsAccessibilityButtonShown();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "isAccessibilityButtonShown Exception:", e);
                return false;
            }
        }
    }

    public List<String> semGetExclusiveTaskList(Context context, String str) {
        ISemExclusiveTaskManager exclusiveTaskManagerService;
        Log.d(LOG_TAG, "semGetExclusiveTaskList()");
        try {
            exclusiveTaskManagerService = getExclusiveTaskManagerService(context);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "semGetExclusiveTaskList() Exception:", e);
        }
        if (exclusiveTaskManagerService != null) {
            return exclusiveTaskManagerService.getExclusiveTaskList(str);
        }
        Log.d(LOG_TAG, "ISemExclusiveTaskManager is null");
        return new ArrayList();
    }

    private ISemExclusiveTaskManager getExclusiveTaskManagerService(Context context) {
        return ISemExclusiveTaskManager.Stub.asInterface(((SemUnionManager) context.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService(UnionConstants.SERVICE_EXCLUSIVE_TASK));
    }

    public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setPictureInPictureActionReplacingConnection(iAccessibilityInteractionConnection);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error setting picture in picture action replacement", e);
            }
        }
    }

    public List<String> getAccessibilityShortcutTargets(int i) {
        IAccessibilityManager serviceLocked;
        synchronized (this.mLock) {
            serviceLocked = getServiceLocked();
        }
        if (serviceLocked != null) {
            try {
                return serviceLocked.getAccessibilityShortcutTargets(i);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_LIST;
    }

    public List<AccessibilityShortcutInfo> getInstalledAccessibilityShortcutListAsUser(Context context, int i) {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_ACCESSIBILITY_SHORTCUT_TARGET);
        List<ResolveInfo> queryIntentActivitiesAsUser = context.getPackageManager().queryIntentActivitiesAsUser(intent, 819329, i);
        for (int i2 = 0; i2 < queryIntentActivitiesAsUser.size(); i2++) {
            AccessibilityShortcutInfo shortcutInfo = getShortcutInfo(context, queryIntentActivitiesAsUser.get(i2));
            if (shortcutInfo != null) {
                arrayList.add(shortcutInfo);
            }
        }
        return arrayList;
    }

    public List<SemAccessibilityShortcutInfo> semGetInstalledAccessibilityShortcutInfoAsUser(Context context, int i) {
        ArrayList arrayList = new ArrayList();
        List<AccessibilityShortcutInfo> installedAccessibilityShortcutListAsUser = getInstalledAccessibilityShortcutListAsUser(context, i);
        PackageManager packageManager = context.getPackageManager();
        for (AccessibilityShortcutInfo accessibilityShortcutInfo : installedAccessibilityShortcutListAsUser) {
            arrayList.add(new SemAccessibilityShortcutInfo(accessibilityShortcutInfo.getActivityInfo().loadLabel(packageManager).toString(), accessibilityShortcutInfo.getActivityInfo().loadIcon(packageManager)));
        }
        return arrayList;
    }

    public void semPerformAccessibilityButtonClick(int i, int i2, String str) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.semPerformAccessibilityButtonClick(i, i2, str);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "setMagnificationDisactivate Exception:", e);
            }
        }
    }

    private AccessibilityShortcutInfo getShortcutInfo(Context context, ResolveInfo resolveInfo) {
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        if (activityInfo != null && activityInfo.metaData != null && activityInfo.metaData.getInt(AccessibilityShortcutInfo.META_DATA) != 0) {
            try {
                return new AccessibilityShortcutInfo(context, activityInfo);
            } catch (IOException | XmlPullParserException e) {
                Log.e(LOG_TAG, "Error while initializing AccessibilityShortcutInfo", e);
            }
        }
        return null;
    }

    public void setMagnificationConnection(IMagnificationConnection iMagnificationConnection) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setMagnificationConnection(iMagnificationConnection);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error setting magnification connection", e);
            }
        }
    }

    public void setMagnificationDisactivate() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setMagnificationDisactivate();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "setMagnificationDisactivate Exception:", e);
            }
        }
    }

    public boolean isAudioDescriptionRequested() {
        synchronized (this.mLock) {
            if (getServiceLocked() == null) {
                return false;
            }
            return this.mIsAudioDescriptionByDefaultRequested;
        }
    }

    public void setSystemAudioCaptioningEnabled(boolean z, int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setSystemAudioCaptioningEnabled(z, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isSystemAudioCaptioningUiEnabled(int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.isSystemAudioCaptioningUiEnabled(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setSystemAudioCaptioningUiEnabled(boolean z, int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setSystemAudioCaptioningUiEnabled(z, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setAccessibilityWindowAttributes(int i, int i2, AccessibilityWindowAttributes accessibilityWindowAttributes) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setAccessibilityWindowAttributes(i, i2, this.mUserId, accessibilityWindowAttributes);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean registerDisplayProxy(AccessibilityDisplayProxy accessibilityDisplayProxy) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.registerProxyForDisplay(accessibilityDisplayProxy.mServiceClient, accessibilityDisplayProxy.getDisplayId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean unregisterDisplayProxy(AccessibilityDisplayProxy accessibilityDisplayProxy) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.unregisterProxyForDisplay(accessibilityDisplayProxy.getDisplayId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean startFlashNotificationSequence(Context context, int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.startFlashNotificationSequence(context.getOpPackageName(), i, this.mBinder);
            } catch (RemoteException | SecurityException e) {
                Log.e(LOG_TAG, "Error while start flash notification sequence", e);
                return false;
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean stopFlashNotificationSequence(Context context) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.stopFlashNotificationSequence(context.getOpPackageName());
            } catch (RemoteException | SecurityException e) {
                Log.e(LOG_TAG, "Error while stop flash notification sequence", e);
                return false;
            }
        }
    }

    public boolean semStartFlashNotificationSequence(Context context, int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.semStartFlashNotificationSequence(context.getOpPackageName(), i, this.mBinder);
            } catch (RemoteException | SecurityException e) {
                Log.e(LOG_TAG, "Error while start flash notification sequence", e);
                return false;
            }
        }
    }

    public boolean semStopFlashNotificationSequence(Context context) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.semStopFlashNotificationSequence(context.getOpPackageName());
            } catch (RemoteException | SecurityException e) {
                Log.e(LOG_TAG, "Error while stop flash notification sequence", e);
                return false;
            }
        }
    }

    public boolean isCameraFlashNotificationRunning() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.isCameraFlashNotificationRunning();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "failed to get Ccamera flash notification running state", e);
                return false;
            }
        }
    }

    public boolean startFlashNotificationEvent(Context context, int i, String str) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.startFlashNotificationEvent(context.getOpPackageName(), i, str);
            } catch (RemoteException | SecurityException e) {
                Log.e(LOG_TAG, "Error while start flash notification event", e);
                return false;
            }
        }
    }

    public boolean isAccessibilityTargetAllowed(String str, int i, int i2) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.isAccessibilityTargetAllowed(str, i, i2);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while check accessibility target status", e);
                return false;
            }
        }
    }

    public boolean sendRestrictedDialogIntent(String str, int i, int i2) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.sendRestrictedDialogIntent(str, i, i2);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while show restricted dialog", e);
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

    private void tryConnectToServiceLocked(IAccessibilityManager iAccessibilityManager) {
        if (iAccessibilityManager == null) {
            IBinder service = ServiceManager.getService(Context.ACCESSIBILITY_SERVICE);
            if (service == null) {
                return;
            } else {
                iAccessibilityManager = IAccessibilityManager.Stub.asInterface(service);
            }
        }
        try {
            long addClient = iAccessibilityManager.addClient(this.mClient, this.mUserId);
            setStateLocked(IntPair.first(addClient));
            this.mRelevantEventTypes = IntPair.second(addClient);
            updateUiTimeout(iAccessibilityManager.getRecommendedTimeoutMillis());
            updateFocusAppearanceLocked(iAccessibilityManager.getFocusStrokeWidth(), iAccessibilityManager.getFocusColor());
            this.mService = iAccessibilityManager;
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "AccessibilityManagerService is dead", e);
        }
    }

    public void notifyAccessibilityStateChanged() {
        synchronized (this.mLock) {
            if (this.mAccessibilityStateChangeListeners.isEmpty()) {
                return;
            }
            final boolean isEnabled = isEnabled();
            ArrayMap arrayMap = new ArrayMap(this.mAccessibilityStateChangeListeners);
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                final AccessibilityStateChangeListener accessibilityStateChangeListener = (AccessibilityStateChangeListener) arrayMap.keyAt(i);
                ((Handler) arrayMap.valueAt(i)).post(new Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda0
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
            final boolean z = this.mIsTouchExplorationEnabled;
            ArrayMap arrayMap = new ArrayMap(this.mTouchExplorationStateChangeListeners);
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                final TouchExplorationStateChangeListener touchExplorationStateChangeListener = (TouchExplorationStateChangeListener) arrayMap.keyAt(i);
                ((Handler) arrayMap.valueAt(i)).post(new Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityManager.TouchExplorationStateChangeListener.this.onTouchExplorationStateChanged(z);
                    }
                });
            }
        }
    }

    private void notifyHighContrastTextStateChanged() {
        synchronized (this.mLock) {
            if (this.mHighContrastTextStateChangeListeners.isEmpty()) {
                return;
            }
            final boolean z = this.mIsHighContrastTextEnabled;
            ArrayMap arrayMap = new ArrayMap(this.mHighContrastTextStateChangeListeners);
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                final HighContrastTextStateChangeListener highContrastTextStateChangeListener = (HighContrastTextStateChangeListener) arrayMap.keyAt(i);
                ((Executor) arrayMap.valueAt(i)).execute(new Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityManager.HighContrastTextStateChangeListener.this.onHighContrastTextStateChanged(z);
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
            final boolean z = this.mIsAudioDescriptionByDefaultRequested;
            ArrayMap arrayMap = new ArrayMap(this.mAudioDescriptionRequestedChangeListeners);
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                final AudioDescriptionRequestedChangeListener audioDescriptionRequestedChangeListener = (AudioDescriptionRequestedChangeListener) arrayMap.keyAt(i);
                ((Executor) arrayMap.valueAt(i)).execute(new Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityManager.AudioDescriptionRequestedChangeListener.this.onAudioDescriptionRequestedChanged(z);
                    }
                });
            }
        }
    }

    private void updateAccessibilityTracingState(int i) {
        synchronized (this.mLock) {
            this.mAccessibilityTracingState = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUiTimeout(long j) {
        this.mInteractiveUiTimeout = IntPair.first(j);
        this.mNonInteractiveUiTimeout = IntPair.second(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFocusAppearanceLocked(int i, int i2) {
        if (this.mFocusStrokeWidth == i && this.mFocusColor == i2) {
            return;
        }
        this.mFocusStrokeWidth = i;
        this.mFocusColor = i2;
    }

    private void initialFocusAppearanceLocked(Resources resources) {
        try {
            this.mFocusStrokeWidth = resources.getDimensionPixelSize(R.dimen.accessibility_focus_highlight_stroke_width);
            this.mFocusColor = resources.getColor(R.color.accessibility_focus_highlight_color);
        } catch (Resources.NotFoundException e) {
            this.mFocusStrokeWidth = (int) (resources.getDisplayMetrics().density * 4.0f);
            this.mFocusColor = -1086737152;
            Log.e(LOG_TAG, "Error while initialing the focus appearance data then setting to default value by hardcoded", e);
        }
    }

    public static boolean isAccessibilityButtonSupported() {
        return Resources.getSystem().getBoolean(R.bool.config_showNavigationBar);
    }

    private final class MyCallback implements Handler.Callback {
        public static final int MSG_SET_STATE = 1;

        private MyCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                int i = message.arg1;
                synchronized (AccessibilityManager.this.mLock) {
                    AccessibilityManager.this.setStateLocked(i);
                }
            }
            return true;
        }
    }

    public IAccessibilityManager.WindowTransformationSpec getWindowTransformationSpec(int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return null;
            }
            try {
                return serviceLocked.getWindowTransformationSpec(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void attachAccessibilityOverlayToDisplay(int i, SurfaceControl surfaceControl) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.attachAccessibilityOverlayToDisplay(i, surfaceControl);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void notifyQuickSettingsTilesChanged(int i, List<ComponentName> list) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.notifyQuickSettingsTilesChanged(i, list);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semTurnOffAccessibilityService(int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                if (semIsAccessibilityServiceEnabled(i)) {
                    serviceLocked.semTurnOffAccessibilityService(i);
                }
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semTurnOffAccessibilityService exception.", e);
            }
        }
    }

    public void semTurnOnAccessibilityService(int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                if (semIsAccessibilityServiceEnabled(i)) {
                    return;
                }
                serviceLocked.semTurnOnAccessibilityService(i);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semTurnOnAccessibilityService exception.", e);
            }
        }
    }

    public boolean semIsAccessibilityServiceEnabled(int i) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                if (isEnabled()) {
                    return serviceLocked.semIsAccessibilityServiceEnabled(i);
                }
                Log.d(LOG_TAG, "accessibility service is not enabled");
                return false;
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semIsAccessibilityServiceEnabled exception.", e);
                return false;
            }
        }
    }

    public boolean semSetMdnieColorBlind(boolean z, float f) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.semSetColorBlind(z, f);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semSetColorBlind Exception:", e);
                return false;
            }
        }
    }

    public boolean semCheckMdnieColorBlind(int[] iArr) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.semCheckMdnieColorBlind(iArr);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semCheckMdnieColorBlind Exception:", e);
                return false;
            }
        }
    }

    public boolean semSetMdnieAccessibilityMode(int i, boolean z) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.semSetMdnieAccessibilityMode(i, z);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semSetMdnieAccessibilityMode Exception:", e);
                return false;
            }
        }
    }

    public boolean semEnableMdnieColorFilter(int i, int i2) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.semEnableMdnieColorFilter(i, i2);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semEnableMdnieColorFilter Exception:", e);
                return false;
            }
        }
    }

    public boolean semDisableMdnieColorFilter() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.semDisableMdnieColorFilter();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semDisableMdnieColorFilter Exception:", e);
                return false;
            }
        }
    }

    public void semRegisterAssistantMenu(IBinder iBinder) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                Log.e(LOG_TAG, "semRegisterAssistantMenu invoking from manager start:");
                serviceLocked.semRegisterAssistantMenu(iBinder);
                Log.e(LOG_TAG, "semRegisterAssistantMenu invoking from manager end:");
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semRegisterAssistantMenu Exception:", e);
            }
        }
    }

    public void semUpdateAssitantMenu(Bundle bundle) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.semUpdateAssitantMenu(bundle);
                Log.e(LOG_TAG, "semUpdateAssitantMenu invoking from manager:");
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semUpdateAssitantMenu Exception:", e);
            }
        }
    }

    public void semSetTwoFingerGestureRecognitionEnabled(boolean z) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            if (!this.mIsEnabled) {
                throw new IllegalStateException("Accessibility off. Did you forget to check that?");
            }
            try {
                Log.i(LOG_TAG, "AccessibilityManager - semSetTwoFingerGestureRecognitionEnabled: " + z);
                serviceLocked.semSetTwoFingerGestureRecognitionEnabled(z);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semSetTwoFingerGestureRecognitionEnabled Exception:", e);
            }
        }
    }

    public boolean isTwoFingerGestureRecognitionEnabled() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.isTwoFingerGestureRecognitionEnabled();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "isTwoFingerGestureRecognitionEnabled Exception:", e);
                return false;
            }
        }
    }

    public boolean semIsScreenReaderEnabled() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.isScreenReaderEnabled();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semIsScreenReaderEnabled Exception : ", e);
                return false;
            }
        }
    }

    public String semGetScreenReaderName() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return "";
            }
            try {
                return serviceLocked.getScreenReaderName();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semGetScreenReaderName Exception : ", e);
                return "";
            }
        }
    }

    public void semSetScreenReaderEnabled(boolean z) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setScreenReaderEnabled(z);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semSetScreenReaderEnabled Exception : ", e);
            }
        }
    }

    public boolean semIsDarkScreenMode() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.semIsDarkScreenMode();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semIsDarkScreenMode Exception:", e);
                return false;
            }
        }
    }

    public void semToggleDarkScreenMode() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.semToggleDarkScreenMode();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semToggleDarkScreenMode Exception:", e);
            }
        }
    }

    public void semLockNow() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.semLockNow();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semLockNow Exception:", e);
            }
        }
    }

    @Deprecated(forRemoval = true, since = "15.0")
    public boolean semStartFlashNotification(Context context) {
        return semStartFlashNotificationSequence(context, 3);
    }

    @Deprecated(forRemoval = true, since = "15.0")
    public boolean semStopFlashNotification(Context context) {
        return semStopFlashNotificationSequence(context);
    }

    public boolean semEnableAirGestureWakeUp() {
        return OnStartGestureWakeup();
    }

    public boolean semDisableAirGestureWakeUp() {
        return OnStopGestureWakeup();
    }

    public boolean OnStartGestureWakeup() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.OnStartGestureWakeup();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "OnStartGestureWakeup Exception:", e);
                return false;
            }
        }
    }

    public boolean OnStopGestureWakeup() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.OnStopGestureWakeup();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "OnStopGestureWakeup Exception:", e);
                return false;
            }
        }
    }

    public void semDumpCallStack(String str) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.semDumpCallStack(str);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "semDumpCallStack Exception:", e);
            }
        }
    }

    public void performAccessibilityDirectAccess() {
        performAccessibilityDirectAccess(null);
    }

    public void performAccessibilityDirectAccess(String str) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.performAccessibilityDirectAccess(str);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error performing accessibility direct access. ", e);
            }
        }
    }

    public Rect semGetWindowMagnificationBounds() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return null;
            }
            try {
                return serviceLocked.semGetWindowMagnificationBounds();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error Get Magnification Bounds. ", e);
                return null;
            }
        }
    }

    public float semGetWindowMagnificationScale() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return 0.0f;
            }
            try {
                return serviceLocked.semGetWindowMagnificationScale();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error Set scale AM Magnification. ", e);
                return 0.0f;
            }
        }
    }

    public void semEnableWindowMagnification(int i, int i2) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.semEnableWindowMagnification(i, i2);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error Enable AM Magnification. ", e);
            }
        }
    }

    public void semDisableWindowMagnification() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.semDisableWindowMagnification();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error Disable AM Magnification. ", e);
            }
        }
    }

    public void semMoveWindowMagnification(float f, float f2) {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.semMoveWindowMagnification(f, f2);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error Move AM Magnification. ", e);
            }
        }
    }

    public boolean semIsWindowMagnificationEnabled() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.semIsWindowMagnificationEnabled();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error Set scale AM Magnification. ", e);
                return false;
            }
        }
    }

    public boolean isActivatedMagnification() {
        synchronized (this.mLock) {
            IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.isActivatedMagnification();
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error get Magnification enable value. ", e);
                return false;
            }
        }
    }
}
