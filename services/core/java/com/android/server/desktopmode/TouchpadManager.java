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
import android.util.IndentingPrintWriter;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;
import com.android.server.ServiceThread;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.desktopmode.DesktopModeFeature;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class TouchpadManager {
  public static final String TAG = "[DMS]" + TouchpadManager.class.getSimpleName();
  public final SettingsHelper.OnSettingChangedListener mAutoRunSettingChangedListener;
  public final Context mContext;
  public final Handler mHandler;
  public final ContentObserver mNavBarModeObserver;
  public final WindowManagerPolicyConstants.PointerEventListener mPointerEventListener;
  public final BroadcastReceiver mSPenDetachedReceiver;
  public final SettingsHelper mSettingsHelper;
  public final IStateManager mStateManager;
  public final WindowManagerService mWindowManager;
  public boolean mIsTouchpadEnabled = false;
  public boolean mIsSPenDetached = false;
  public boolean mIsSPenEnabled = false;
  public int mDesktopDisplayId = -1;
  public final int AUTO_RUN_GUIDE_SHOWING_COUNT = 3;
  public final AtomicBoolean mTouchpadRequested = new AtomicBoolean();
  public final UiManager.InternalUiCallback mInternalUiCallback = new C11451();
  public final SettingsHelper.OnSettingChangedListener mSPenSettingChangedListener =
      new SettingsHelper.OnSettingChangedListener(
          "spen_enabled") { // from class: com.android.server.desktopmode.TouchpadManager.2
        @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
        public void onSettingChanged(String str, int i) {
          if (str == null) {
            if (DesktopModeFeature.DEBUG) {
              Log.m7d(TouchpadManager.TAG, "SPen Setting deleted.");
            }
            TouchpadManager.this.mIsSPenEnabled = false;
          }
        }
      };

  /* renamed from: com.android.server.desktopmode.TouchpadManager$1 */
  public class C11451 extends UiManager.InternalUiCallback {
    public C11451() {}

    @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
    public void onShow() {
      TouchpadManager.this.updateTouchpad(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDismiss$0() {
      TouchpadManager.this.updateTouchpadOnDismiss();
    }

    @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
    public void onDismiss() {
      TouchpadManager.this.mHandler.post(
          new Runnable() { // from class:
            // com.android.server.desktopmode.TouchpadManager$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              TouchpadManager.C11451.this.lambda$onDismiss$0();
            }
          });
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public TouchpadManager(
      Context context,
      IStateManager iStateManager,
      ServiceThread serviceThread,
      SettingsHelper settingsHelper,
      WindowManagerService windowManagerService) {
    SettingsHelper.OnSettingChangedListener onSettingChangedListener =
        new SettingsHelper.OnSettingChangedListener(
            "touchpad_auto_run") { // from class: com.android.server.desktopmode.TouchpadManager.3
          @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
          public void onSettingChanged(String str, int i) {
            if (str == null || !Boolean.parseBoolean(str)) {
              return;
            }
            DesktopModeSettings.setSettings(
                TouchpadManager.this.mContext.getContentResolver(),
                "touchpad_auto_run_guide_count",
                4);
          }
        };
    this.mAutoRunSettingChangedListener = onSettingChangedListener;
    this.mSPenDetachedReceiver =
        new BroadcastReceiver() { // from class: com.android.server.desktopmode.TouchpadManager.4
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            if ("com.samsung.pen.INSERT".equals(intent.getAction())) {
              TouchpadManager.this.mIsSPenDetached = !intent.getBooleanExtra("penInsert", true);
              if (DesktopModeFeature.DEBUG) {
                Log.m7d(
                    TouchpadManager.TAG, "mIsSPenDetached=" + TouchpadManager.this.mIsSPenDetached);
              }
              TouchpadManager.this.updateSPenState();
            }
          }
        };
    this.mNavBarModeObserver =
        new ContentObserver(null) { // from class: com.android.server.desktopmode.TouchpadManager.5
          @Override // android.database.ContentObserver
          public void onChange(boolean z) {
            TouchpadManager.this.mStateManager.setNavBarGestureEnabled(
                TouchpadManager.this.isNavBarGestureEnabled());
          }
        };
    this.mPointerEventListener =
        new WindowManagerPolicyConstants.PointerEventListener() { // from class:
          // com.android.server.desktopmode.TouchpadManager.6
          public void onPointerEvent(MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if ((action == 9 || action == 0) && motionEvent.isFromSource(16386)) {
              TouchpadManager.this.mIsSPenDetached = true;
              TouchpadManager.this.updateSPenState();
            }
          }
        };
    this.mContext = context;
    this.mHandler = new Handler(serviceThread.getLooper());
    this.mStateManager = iStateManager;
    iStateManager.registerListener(new StateListener());
    this.mSettingsHelper = settingsHelper;
    this.mWindowManager = windowManagerService;
    settingsHelper.registerListener(onSettingChangedListener);
  }

  public UiManager.InternalUiCallback getInternalUiCallback() {
    return this.mInternalUiCallback;
  }

  public boolean isTouchpadFeatureAvailable(State state) {
    return !state.isDexOnPcConnected();
  }

  public boolean isSPenFeatureAvailable(State state) {
    return DesktopModeFeature.SUPPORT_SPEN && !state.getDockState().isDexStation();
  }

  public void notifyStartedByNotification() {
    if (this.mTouchpadRequested.get()) {
      return;
    }
    int settings =
        DesktopModeSettings.getSettings(
            this.mContext.getContentResolver(), "touchpad_auto_run_guide_count", 0);
    if (settings < 3) {
      DesktopModeSettings.setSettings(
          this.mContext.getContentResolver(), "touchpad_auto_run_guide_count", settings + 1);
    }
    this.mTouchpadRequested.set(true);
  }

  public final void updateSPenState() {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "updateSPenState()");
    }
    State state = this.mStateManager.getState();
    boolean z = isSPenFeatureAvailable(state) && this.mIsSPenDetached && isTouchpadAvailable(state);
    if (this.mIsSPenEnabled != z) {
      this.mIsSPenEnabled = z;
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "mIsSPenEnabled= " + this.mIsSPenEnabled);
      }
      DesktopModeSettings.setSettings(this.mContext.getContentResolver(), "spen_enabled", z);
      this.mStateManager.setSpenEnabled(z);
    }
  }

  public final boolean isNavBarGestureEnabled() {
    return 2
        <= Settings.Secure.getIntForUser(
            this.mContext.getContentResolver(), "navigation_mode", 0, -2);
  }

  public final boolean isTouchpadAvailable(State state) {
    return isTouchpadFeatureAvailable(state) && state.getDesktopModeState().compareTo(4, 0, 102);
  }

  public final void updateTouchpadAvailability(State state) {
    this.mStateManager.setTouchpadAvailable(isTouchpadAvailable(state));
  }

  public final void updateTouchpad(boolean z) {
    this.mIsTouchpadEnabled = z;
    this.mStateManager.setTouchpadEnabled(z);
  }

  public final void updateTouchpadOnDismiss() {
    if (DesktopModeFeature.SPEN_INBOX_MODEL) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "S Pen inbox model");
      }
      this.mIsSPenDetached = false;
      updateSPenState();
    }
    updateTouchpad(false);
    this.mTouchpadRequested.set(false);
  }

  public class StateListener extends StateManager.StateListener {
    public StateListener() {}

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onDualModeStopLoadingScreen(boolean z) {
      if (z) {
        State state = TouchpadManager.this.mStateManager.getState();
        TouchpadManager.this.updateTouchpadAvailability(state);
        TouchpadManager.this.mSettingsHelper.registerListener(
            TouchpadManager.this.mSPenSettingChangedListener);
        if (DesktopModeFeature.SUPPORT_SPEN) {
          TouchpadManager.this.mContext.registerReceiverAsUser(
              TouchpadManager.this.mSPenDetachedReceiver,
              UserHandle.ALL,
              new IntentFilter("com.samsung.pen.INSERT"),
              null,
              null);
          TouchpadManager.this.mWindowManager.registerPointerEventListener(
              TouchpadManager.this.mPointerEventListener, 0);
          TouchpadManager.this.mDesktopDisplayId = state.getDesktopDisplayId();
          TouchpadManager.this.mWindowManager.registerPointerEventListener(
              TouchpadManager.this.mPointerEventListener, TouchpadManager.this.mDesktopDisplayId);
        }
        if (state.getDockState().isDexStation()
            && state.isCoverSupportStatePartial()
            && !state.isMouseConnected()) {
          ToastManager.showToast(TouchpadManager.this.mContext, R.string.global_action_emergency);
        }
      }
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onDualModeStartLoadingScreen(boolean z) {
      if (z) {
        TouchpadManager.this.mStateManager.setNavBarGestureEnabled(
            TouchpadManager.this.isNavBarGestureEnabled());
        TouchpadManager.this
            .mContext
            .getContentResolver()
            .registerContentObserver(
                Settings.Secure.getUriFor("navigation_mode"),
                true,
                TouchpadManager.this.mNavBarModeObserver,
                -2);
      } else {
        TouchpadManager touchpadManager = TouchpadManager.this;
        touchpadManager.updateTouchpadAvailability(touchpadManager.mStateManager.getState());
        TouchpadManager.this.mSettingsHelper.unregisterListener(
            TouchpadManager.this.mSPenSettingChangedListener);
        TouchpadManager.this
            .mContext
            .getContentResolver()
            .unregisterContentObserver(TouchpadManager.this.mNavBarModeObserver);
        if (DesktopModeFeature.SUPPORT_SPEN) {
          TouchpadManager.this.mContext.unregisterReceiver(
              TouchpadManager.this.mSPenDetachedReceiver);
          TouchpadManager.this.mWindowManager.unregisterPointerEventListener(
              TouchpadManager.this.mPointerEventListener, 0);
          TouchpadManager.this.mWindowManager.unregisterPointerEventListener(
              TouchpadManager.this.mPointerEventListener, TouchpadManager.this.mDesktopDisplayId);
        }
      }
      DesktopModeSettings.setSettings(
          TouchpadManager.this.mContext.getContentResolver(), "touchpad_enabled", false);
      TouchpadManager.this.mStateManager.setTouchpadEnabled(false);
      TouchpadManager.this.mTouchpadRequested.set(false);
      if (DesktopModeFeature.SUPPORT_SPEN) {
        TouchpadManager.this.mIsSPenDetached = false;
        TouchpadManager.this.updateSPenState();
      }
    }
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("Current " + TouchpadManager.class.getSimpleName() + " state:");
    indentingPrintWriter.increaseIndent();
    indentingPrintWriter.println("mIsSPenDetached=" + this.mIsSPenDetached);
    indentingPrintWriter.println("mIsSPenEnabled=" + this.mIsSPenEnabled);
    indentingPrintWriter.println("mIsTouchpadEnabled=" + this.mIsTouchpadEnabled);
    indentingPrintWriter.decreaseIndent();
  }
}
