package com.android.server.desktopmode;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;
import com.android.server.ServiceThread;
import com.android.server.desktopmode.SettingsHelper;
import com.android.server.desktopmode.StateManager;
import com.android.server.desktopmode.UiManager;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.desktopmode.DesktopModeFeature;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TouchpadManager {
    public final AnonymousClass2 mAutoRunSettingChangedListener;
    public final Context mContext;
    public final Handler mHandler;
    public final AnonymousClass5 mNavBarModeObserver;
    public final AnonymousClass6 mPointerEventListener;
    public final AnonymousClass4 mSPenDetachedReceiver;
    public final SettingsHelper mSettingsHelper;
    public final IStateManager mStateManager;
    public final WindowManagerService mWindowManager;
    public boolean mIsTouchpadEnabled = false;
    public boolean mIsSPenDetached = false;
    public boolean mIsSPenEnabled = false;
    public int mDesktopDisplayId = -1;
    public final AtomicBoolean mTouchpadRequested = new AtomicBoolean();
    public final AnonymousClass1 mInternalUiCallback = new AnonymousClass1();
    public final AnonymousClass2 mSPenSettingChangedListener = new SettingsHelper.OnSettingChangedListener(this, 0) { // from class: com.android.server.desktopmode.TouchpadManager.2
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ TouchpadManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        {
            super("spen_enabled");
            this.$r8$classId = r2;
            switch (r2) {
                case 1:
                    this.this$0 = this;
                    super("touchpad_auto_run");
                    break;
                default:
                    this.this$0 = this;
                    break;
            }
        }

        @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
        public final void onSettingChanged(String str) {
            switch (this.$r8$classId) {
                case 0:
                    if (str == null) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.d("[DMS]TouchpadManager", "SPen Setting deleted.");
                        }
                        this.this$0.mIsSPenEnabled = false;
                        break;
                    }
                    break;
                default:
                    if (str != null && Boolean.parseBoolean(str)) {
                        DesktopModeSettings.setSettings(this.this$0.mContext.getContentResolver(), "touchpad_auto_run_guide_count", 4);
                        break;
                    }
                    break;
            }
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.desktopmode.TouchpadManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends UiManager.InternalUiCallback {
        public AnonymousClass1() {
        }

        @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
        public final void onDismiss() {
            TouchpadManager.this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.TouchpadManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TouchpadManager touchpadManager = TouchpadManager.this;
                    touchpadManager.getClass();
                    if (DesktopModeFeature.SPEN_INBOX_MODEL) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.d("[DMS]TouchpadManager", "S Pen inbox model");
                        }
                        touchpadManager.mIsSPenDetached = false;
                        touchpadManager.updateSPenState();
                    }
                    touchpadManager.mIsTouchpadEnabled = false;
                    ((StateManager) touchpadManager.mStateManager).setTouchpadEnabled(false);
                    touchpadManager.mTouchpadRequested.set(false);
                }
            });
        }

        @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
        public final void onShow() {
            TouchpadManager touchpadManager = TouchpadManager.this;
            touchpadManager.mIsTouchpadEnabled = true;
            ((StateManager) touchpadManager.mStateManager).setTouchpadEnabled(true);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StateListener extends StateManager.StateListener {
        public StateListener() {
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onDualModeStartLoadingScreen(boolean z) {
            TouchpadManager touchpadManager = TouchpadManager.this;
            if (z) {
                ((StateManager) touchpadManager.mStateManager).setNavBarGestureEnabled(2 <= Settings.Secure.getIntForUser(touchpadManager.mContext.getContentResolver(), "navigation_mode", 0, -2));
                touchpadManager.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("navigation_mode"), true, touchpadManager.mNavBarModeObserver, -2);
            } else {
                TouchpadManager.m418$$Nest$mupdateTouchpadAvailability(touchpadManager, ((StateManager) touchpadManager.mStateManager).getState());
                touchpadManager.mSettingsHelper.unregisterListener(touchpadManager.mSPenSettingChangedListener);
                touchpadManager.mContext.getContentResolver().unregisterContentObserver(touchpadManager.mNavBarModeObserver);
                if (DesktopModeFeature.SUPPORT_SPEN) {
                    touchpadManager.mContext.unregisterReceiver(touchpadManager.mSPenDetachedReceiver);
                    WindowManagerService windowManagerService = touchpadManager.mWindowManager;
                    AnonymousClass6 anonymousClass6 = touchpadManager.mPointerEventListener;
                    windowManagerService.unregisterPointerEventListener(anonymousClass6, 0);
                    windowManagerService.unregisterPointerEventListener(anonymousClass6, touchpadManager.mDesktopDisplayId);
                }
            }
            DesktopModeSettings.setSettings(touchpadManager.mContext.getContentResolver(), "touchpad_enabled", false);
            ((StateManager) touchpadManager.mStateManager).setTouchpadEnabled(false);
            touchpadManager.mTouchpadRequested.set(false);
            if (DesktopModeFeature.SUPPORT_SPEN) {
                touchpadManager.mIsSPenDetached = false;
                touchpadManager.updateSPenState();
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onDualModeStopLoadingScreen(boolean z) {
            if (z) {
                TouchpadManager touchpadManager = TouchpadManager.this;
                StateManager.InternalState state = ((StateManager) touchpadManager.mStateManager).getState();
                TouchpadManager.m418$$Nest$mupdateTouchpadAvailability(touchpadManager, state);
                touchpadManager.mSettingsHelper.registerListener(touchpadManager.mSPenSettingChangedListener);
                if (DesktopModeFeature.SUPPORT_SPEN) {
                    touchpadManager.mContext.registerReceiverAsUser(touchpadManager.mSPenDetachedReceiver, UserHandle.ALL, new IntentFilter("com.samsung.pen.INSERT"), null, null, 2);
                    WindowManagerService windowManagerService = touchpadManager.mWindowManager;
                    AnonymousClass6 anonymousClass6 = touchpadManager.mPointerEventListener;
                    windowManagerService.registerPointerEventListener(anonymousClass6, 0);
                    int i = state.mDesktopDisplayId;
                    touchpadManager.mDesktopDisplayId = i;
                    windowManagerService.registerPointerEventListener(anonymousClass6, i);
                }
                if (state.mDockState.isDexStation() && state.mCoverSupportState == 2 && !state.mIsMouseConnected) {
                    Context context = touchpadManager.mContext;
                    List list = ToastManager.sToasts;
                    ToastManager.showToast(context, context.getString(R.string.hours), 1);
                }
            }
        }
    }

    /* renamed from: -$$Nest$mupdateTouchpadAvailability, reason: not valid java name */
    public static void m418$$Nest$mupdateTouchpadAvailability(TouchpadManager touchpadManager, StateManager.InternalState internalState) {
        IStateManager iStateManager = touchpadManager.mStateManager;
        boolean z = (internalState.isDexOnPcConnected() ^ true) && internalState.mDesktopModeState.compareTo(4, 0, 102);
        StateManager stateManager = (StateManager) iStateManager;
        stateManager.getClass();
        if (DesktopModeFeature.DEBUG) {
            DesktopModeService$$ExternalSyntheticOutline0.m("setTouchpadAvailable(available=", ")", "[DMS]StateManager", z);
        }
        synchronized (stateManager.mLock) {
            try {
                if (stateManager.mInternalState.mTouchpadAvailable != z) {
                    stateManager.mInternalState.mTouchpadAvailable = z;
                    stateManager.mHandler.post(new StateManager$$ExternalSyntheticLambda0(stateManager, stateManager.copyInternalStateLocked(stateManager.mInternalState), 7));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.desktopmode.TouchpadManager$2] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.desktopmode.TouchpadManager$4] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.desktopmode.TouchpadManager$5] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.desktopmode.TouchpadManager$6] */
    public TouchpadManager(Context context, IStateManager iStateManager, ServiceThread serviceThread, SettingsHelper settingsHelper, WindowManagerService windowManagerService) {
        SettingsHelper.OnSettingChangedListener onSettingChangedListener = new SettingsHelper.OnSettingChangedListener(this, 1) { // from class: com.android.server.desktopmode.TouchpadManager.2
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ TouchpadManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super("spen_enabled");
                this.$r8$classId = r2;
                switch (r2) {
                    case 1:
                        this.this$0 = this;
                        super("touchpad_auto_run");
                        break;
                    default:
                        this.this$0 = this;
                        break;
                }
            }

            @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
            public final void onSettingChanged(String str) {
                switch (this.$r8$classId) {
                    case 0:
                        if (str == null) {
                            if (DesktopModeFeature.DEBUG) {
                                Log.d("[DMS]TouchpadManager", "SPen Setting deleted.");
                            }
                            this.this$0.mIsSPenEnabled = false;
                            break;
                        }
                        break;
                    default:
                        if (str != null && Boolean.parseBoolean(str)) {
                            DesktopModeSettings.setSettings(this.this$0.mContext.getContentResolver(), "touchpad_auto_run_guide_count", 4);
                            break;
                        }
                        break;
                }
            }
        };
        this.mSPenDetachedReceiver = new BroadcastReceiver() { // from class: com.android.server.desktopmode.TouchpadManager.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.samsung.pen.INSERT".equals(intent.getAction())) {
                    TouchpadManager.this.mIsSPenDetached = !intent.getBooleanExtra("penInsert", true);
                    if (DesktopModeFeature.DEBUG) {
                        Log.d("[DMS]TouchpadManager", "mIsSPenDetached=" + TouchpadManager.this.mIsSPenDetached);
                    }
                    TouchpadManager.this.updateSPenState();
                }
            }
        };
        this.mNavBarModeObserver = new ContentObserver() { // from class: com.android.server.desktopmode.TouchpadManager.5
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                TouchpadManager touchpadManager = TouchpadManager.this;
                ((StateManager) touchpadManager.mStateManager).setNavBarGestureEnabled(2 <= Settings.Secure.getIntForUser(touchpadManager.mContext.getContentResolver(), "navigation_mode", 0, -2));
            }
        };
        this.mPointerEventListener = new WindowManagerPolicyConstants.PointerEventListener() { // from class: com.android.server.desktopmode.TouchpadManager.6
            public final void onPointerEvent(MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if ((action == 9 || action == 0) && motionEvent.isFromSource(16386)) {
                    TouchpadManager touchpadManager = TouchpadManager.this;
                    touchpadManager.mIsSPenDetached = true;
                    touchpadManager.updateSPenState();
                }
            }
        };
        this.mContext = context;
        this.mHandler = new Handler(serviceThread.getLooper());
        this.mStateManager = iStateManager;
        ((StateManager) iStateManager).registerListener(new StateListener());
        this.mSettingsHelper = settingsHelper;
        this.mWindowManager = windowManagerService;
        settingsHelper.registerListener(onSettingChangedListener);
    }

    public final void updateSPenState() {
        boolean z = DesktopModeFeature.DEBUG;
        if (z) {
            Log.d("[DMS]TouchpadManager", "updateSPenState()");
        }
        StateManager.InternalState state = ((StateManager) this.mStateManager).getState();
        boolean z2 = false;
        if (DesktopModeFeature.SUPPORT_SPEN && !state.mDockState.isDexStation() && this.mIsSPenDetached && (!state.isDexOnPcConnected()) && state.mDesktopModeState.compareTo(4, 0, 102)) {
            z2 = true;
        }
        if (this.mIsSPenEnabled != z2) {
            this.mIsSPenEnabled = z2;
            if (z) {
                Log.d("[DMS]TouchpadManager", "mIsSPenEnabled= " + this.mIsSPenEnabled);
            }
            DesktopModeSettings.setSettings(this.mContext.getContentResolver(), "spen_enabled", z2);
            StateManager stateManager = (StateManager) this.mStateManager;
            stateManager.getClass();
            if (z) {
                DesktopModeService$$ExternalSyntheticOutline0.m("setSpenEnabled(enabled=", ")", "[DMS]StateManager", z2);
            }
            synchronized (stateManager.mLock) {
                try {
                    if (stateManager.mInternalState.mSpenEnabled != z2) {
                        stateManager.mInternalState.mSpenEnabled = z2;
                        stateManager.mHandler.post(new StateManager$$ExternalSyntheticLambda0(stateManager, stateManager.copyInternalStateLocked(stateManager.mInternalState), 3));
                    }
                } finally {
                }
            }
        }
    }
}
