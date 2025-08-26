package android.app.jank;

import android.app.jank.JankDataProcessor;
import android.app.jank.StateTracker;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.AttachedSurfaceControl;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class JankTracker {
    private static final boolean DEBUG = false;
    private static final String DEBUG_KEY = "JANKTRACKER";
    private static final int REGISTRATION_DELAY_MS = 1000;
    private String mActivityName;
    private int mAppUid;
    private View mDecorView;
    private SurfaceControl.OnJankDataListenerRegistration mJankDataListenerRegistration;
    private JankDataProcessor mJankDataProcessor;
    private StateTracker mStateTracker;
    private AttachedSurfaceControl mSurfaceControl;
    private HandlerThread mHandlerThread = new HandlerThread("AppJankTracker");
    private Handler mHandler = null;
    private boolean mTrackingEnabled = false;
    private boolean mListenersRegistered = false;
    private final SurfaceControl.OnJankDataListener mJankDataListener = new SurfaceControl.OnJankDataListener() { // from class: android.app.jank.JankTracker.1
        @Override // android.view.SurfaceControl.OnJankDataListener
        public void onJankDataAvailable(List<SurfaceControl.JankData> list) {
            if (JankTracker.this.mJankDataProcessor == null) {
                return;
            }
            JankTracker.this.mJankDataProcessor.processJankData(list, JankTracker.this.mActivityName, JankTracker.this.mAppUid);
        }
    };
    private final ViewTreeObserver.OnWindowAttachListener mOnWindowAttachListener = new ViewTreeObserver.OnWindowAttachListener() { // from class: android.app.jank.JankTracker.2
        @Override // android.view.ViewTreeObserver.OnWindowAttachListener
        public void onWindowDetached() {
        }

        @Override // android.view.ViewTreeObserver.OnWindowAttachListener
        public void onWindowAttached() {
            JankTracker.this.getHandler().postDelayed(new Runnable() { // from class: android.app.jank.JankTracker.2.1
                @Override // java.lang.Runnable
                public void run() {
                    JankTracker.this.mDecorView.getViewTreeObserver().removeOnWindowAttachListener(JankTracker.this.mOnWindowAttachListener);
                    JankTracker.this.initializeJankTrackingComponents();
                }
            }, 1000L);
        }
    };

    public JankTracker(Choreographer choreographer, View view) {
        this.mStateTracker = new StateTracker(choreographer);
        this.mJankDataProcessor = new JankDataProcessor(this.mStateTracker);
        this.mDecorView = view;
        this.mHandlerThread.start();
        registerWindowListeners();
    }

    public JankTracker(View view) {
        this.mDecorView = view;
        this.mHandlerThread.start();
        registerWindowListeners();
    }

    public void mergeAppJankStats(final AppJankStats appJankStats) {
        if (appJankStats.getUid() != this.mAppUid) {
            return;
        }
        getHandler().post(new Runnable() { // from class: android.app.jank.JankTracker.3
            @Override // java.lang.Runnable
            public void run() {
                if (JankTracker.this.mJankDataProcessor == null) {
                    return;
                }
                JankTracker.this.mJankDataProcessor.mergeJankStats(appJankStats, JankTracker.this.mActivityName);
            }
        });
    }

    public void setActivityName(String str) {
        this.mActivityName = str;
    }

    public void setAppUid(int i) {
        this.mAppUid = i;
    }

    public void addUiState(String str, String str2, String str3) {
        if (shouldTrack()) {
            this.mStateTracker.putState(str, str2, str3);
        }
    }

    public void removeUiState(String str, String str2, String str3) {
        if (shouldTrack()) {
            this.mStateTracker.removeState(str, str2, str3);
        }
    }

    public void updateUiState(String str, String str2, String str3, String str4) {
        if (shouldTrack()) {
            this.mStateTracker.updateState(str, str2, str3, str4);
        }
    }

    public void enableAppJankTracking() {
        addActivityToStateTracking();
        this.mTrackingEnabled = true;
        registerForJankData();
    }

    public void disableAppJankTracking() {
        this.mTrackingEnabled = false;
        removeActivityFromStateTracking();
        unregisterForJankData();
    }

    public void getAllUiStates(ArrayList<StateTracker.StateData> arrayList) {
        StateTracker stateTracker = this.mStateTracker;
        if (stateTracker == null) {
            return;
        }
        stateTracker.retrieveAllStates(arrayList);
    }

    public HashMap<String, JankDataProcessor.PendingJankStat> getPendingJankStats() {
        JankDataProcessor jankDataProcessor = this.mJankDataProcessor;
        if (jankDataProcessor == null) {
            return new HashMap<>();
        }
        return jankDataProcessor.getPendingJankStats();
    }

    public void forceListenerRegistration() {
        addActivityToStateTracking();
        this.mSurfaceControl = this.mDecorView.getRootSurfaceControl();
        registerJankDataListener();
        this.mListenersRegistered = true;
    }

    private void unregisterForJankData() {
        if (this.mJankDataListenerRegistration == null) {
            return;
        }
        if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.jankApi()) {
            this.mJankDataListenerRegistration.release();
        }
        this.mJankDataListenerRegistration = null;
        this.mListenersRegistered = false;
    }

    private void registerForJankData() {
        View view = this.mDecorView;
        if (view == null) {
            return;
        }
        AttachedSurfaceControl rootSurfaceControl = view.getRootSurfaceControl();
        this.mSurfaceControl = rootSurfaceControl;
        if (rootSurfaceControl == null || this.mListenersRegistered) {
            return;
        }
        getHandler().postDelayed(new Runnable() { // from class: android.app.jank.JankTracker.4
            @Override // java.lang.Runnable
            public void run() {
                JankTracker.this.registerJankDataListener();
            }
        }, 1000L);
    }

    public boolean shouldTrack() {
        return this.mTrackingEnabled && this.mListenersRegistered;
    }

    private void registerWindowListeners() {
        View view = this.mDecorView;
        if (view == null) {
            return;
        }
        view.getViewTreeObserver().addOnWindowAttachListener(this.mOnWindowAttachListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerJankDataListener() {
        if (this.mSurfaceControl != null && com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.jankApi()) {
            SurfaceControl.OnJankDataListenerRegistration onJankDataListenerRegistrationRegisterOnJankDataListener = this.mSurfaceControl.registerOnJankDataListener(this.mHandlerThread.getThreadExecutor(), this.mJankDataListener);
            this.mJankDataListenerRegistration = onJankDataListenerRegistrationRegisterOnJankDataListener;
            if (onJankDataListenerRegistrationRegisterOnJankDataListener == SurfaceControl.OnJankDataListenerRegistration.NONE) {
                return;
            }
            this.mListenersRegistered = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(this.mHandlerThread.getLooper());
        }
        return this.mHandler;
    }

    private void addActivityToStateTracking() {
        StateTracker stateTracker = this.mStateTracker;
        if (stateTracker == null) {
            return;
        }
        stateTracker.putState("unspecified", this.mActivityName, "unspecified");
    }

    private void removeActivityFromStateTracking() {
        StateTracker stateTracker = this.mStateTracker;
        if (stateTracker == null) {
            return;
        }
        stateTracker.removeState("unspecified", this.mActivityName, "unspecified");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initializeJankTrackingComponents() {
        ViewRootImpl viewRootImpl = this.mDecorView.getViewRootImpl();
        if (viewRootImpl == null || viewRootImpl.getChoreographer() == null) {
            return;
        }
        if (this.mStateTracker == null) {
            this.mStateTracker = new StateTracker(viewRootImpl.getChoreographer());
        }
        if (this.mJankDataProcessor == null) {
            this.mJankDataProcessor = new JankDataProcessor(this.mStateTracker);
        }
        addActivityToStateTracking();
        registerForJankData();
    }
}
