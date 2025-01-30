package com.android.server.inputmethod;

import android.R;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManagerInternal;
import android.graphics.Matrix;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Slog;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.inputmethod.IInputMethod;
import com.android.internal.inputmethod.IInputMethodSession;
import com.android.internal.inputmethod.InputBindResult;
import com.android.server.wm.WindowManagerInternal;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes2.dex */
public final class InputMethodBindingController {
  static final int IME_CONNECTION_BIND_FLAGS = 1082654725;
  static final int IME_VISIBLE_BIND_FLAGS = 738201601;
  public static final String TAG = "InputMethodBindingController";
  public final Context mContext;
  public String mCurId;
  public Intent mCurIntent;
  public IInputMethodInvoker mCurMethod;
  public int mCurMethodUid;
  public int mCurSeq;
  public IBinder mCurToken;
  public boolean mHasConnection;
  public final int mImeConnectionBindFlags;
  public long mLastBindTime;
  public CountDownLatch mLatchForTesting;
  public final ServiceConnection mMainConnection;
  public final ArrayMap mMethodMap;
  public final PackageManagerInternal mPackageManagerInternal;
  public String mSelectedMethodId;
  public final InputMethodManagerService mService;
  public final InputMethodUtils.InputMethodSettings mSettings;
  public boolean mSupportsStylusHw;
  public boolean mVisibleBound;
  public final ServiceConnection mVisibleConnection;
  public final WindowManagerInternal mWindowManagerInternal;

  public InputMethodBindingController(InputMethodManagerService inputMethodManagerService) {
    this(inputMethodManagerService, IME_CONNECTION_BIND_FLAGS, null);
  }

  public InputMethodBindingController(
      InputMethodManagerService inputMethodManagerService, int i, CountDownLatch countDownLatch) {
    this.mCurMethodUid = -1;
    this.mVisibleConnection =
        new ServiceConnection() { // from class:
                                  // com.android.server.inputmethod.InputMethodBindingController.1
          @Override // android.content.ServiceConnection
          public void onServiceConnected(ComponentName componentName, IBinder iBinder) {}

          @Override // android.content.ServiceConnection
          public void onBindingDied(ComponentName componentName) {
            synchronized (ImfLock.class) {
              InputMethodBindingController.this.mService.invalidateAutofillSessionLocked();
              if (InputMethodBindingController.this.isVisibleBound()) {
                InputMethodBindingController.this.unbindVisibleConnection();
              }
            }
          }

          @Override // android.content.ServiceConnection
          public void onServiceDisconnected(ComponentName componentName) {
            synchronized (ImfLock.class) {
              InputMethodBindingController.this.mService.invalidateAutofillSessionLocked();
            }
          }
        };
    this.mMainConnection =
        new ServiceConnection() { // from class:
                                  // com.android.server.inputmethod.InputMethodBindingController.2
          @Override // android.content.ServiceConnection
          public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Trace.traceBegin(32L, "IMMS.onServiceConnected");
            synchronized (ImfLock.class) {
              if (InputMethodBindingController.this.mCurIntent != null
                  && componentName.equals(
                      InputMethodBindingController.this.mCurIntent.getComponent())) {
                InputMethodBindingController.this.mCurMethod =
                    IInputMethodInvoker.create(IInputMethod.Stub.asInterface(iBinder));
                updateCurrentMethodUid();
                Slog.w(InputMethodBindingController.TAG, "onServiceConnected");
                if (InputMethodBindingController.this.mCurToken == null) {
                  Slog.w(InputMethodBindingController.TAG, "Service connected without a token!");
                  InputMethodBindingController.this.unbindCurrentMethod();
                  Trace.traceEnd(32L);
                  return;
                }
                Slog.v(
                    InputMethodBindingController.TAG,
                    "Initiating attach with token: " + InputMethodBindingController.this.mCurToken);
                InputMethodInfo inputMethodInfo =
                    (InputMethodInfo)
                        InputMethodBindingController.this.mMethodMap.get(
                            InputMethodBindingController.this.mSelectedMethodId);
                boolean z =
                    InputMethodBindingController.this.mSupportsStylusHw
                        != inputMethodInfo.supportsStylusHandwriting();
                InputMethodBindingController.this.mSupportsStylusHw =
                    inputMethodInfo.supportsStylusHandwriting();
                if (z) {
                  InputMethodManager.invalidateLocalStylusHandwritingAvailabilityCaches();
                }
                InputMethodBindingController.this.mService.initializeImeLocked(
                    InputMethodBindingController.this.mCurMethod,
                    InputMethodBindingController.this.mCurToken);
                InputMethodBindingController.this.mService.scheduleNotifyImeUidToAudioService(
                    InputMethodBindingController.this.mCurMethodUid);
                InputMethodBindingController.this.mService.reRequestCurrentClientSessionLocked();
                InputMethodBindingController.this.mService
                    .performOnCreateInlineSuggestionsRequestLocked();
              }
              InputMethodBindingController.this.mService.scheduleResetStylusHandwriting();
              Trace.traceEnd(32L);
              if (InputMethodBindingController.this.mLatchForTesting != null) {
                InputMethodBindingController.this.mLatchForTesting.countDown();
              }
            }
          }

          public final void updateCurrentMethodUid() {
            String packageName =
                InputMethodBindingController.this.mCurIntent.getComponent().getPackageName();
            int packageUid =
                InputMethodBindingController.this.mPackageManagerInternal.getPackageUid(
                    packageName,
                    0L,
                    InputMethodBindingController.this.mSettings.getCurrentUserId());
            if (packageUid < 0) {
              Slog.e(
                  InputMethodBindingController.TAG, "Failed to get UID for package=" + packageName);
              InputMethodBindingController.this.mCurMethodUid = -1;
              return;
            }
            InputMethodBindingController.this.mCurMethodUid = packageUid;
          }

          @Override // android.content.ServiceConnection
          public void onServiceDisconnected(ComponentName componentName) {
            synchronized (ImfLock.class) {
              Slog.v(
                  InputMethodBindingController.TAG,
                  "Service disconnected: "
                      + componentName
                      + " mCurIntent="
                      + InputMethodBindingController.this.mCurIntent);
              if (InputMethodBindingController.this.mCurMethod != null
                  && InputMethodBindingController.this.mCurIntent != null
                  && componentName.equals(
                      InputMethodBindingController.this.mCurIntent.getComponent())) {
                InputMethodBindingController.this.mLastBindTime = SystemClock.uptimeMillis();
                Slog.d(
                    InputMethodBindingController.TAG,
                    "onServiceDisconnected: mLastBindTime="
                        + InputMethodBindingController.this.mLastBindTime);
                InputMethodBindingController.this.clearCurMethodAndSessions();
                InputMethodBindingController.this.mService.clearInputShownLocked();
                InputMethodBindingController.this.mService.unbindCurrentClientLocked(3);
              }
            }
          }
        };
    this.mService = inputMethodManagerService;
    this.mContext = inputMethodManagerService.mContext;
    this.mMethodMap = inputMethodManagerService.mMethodMap;
    this.mSettings = inputMethodManagerService.mSettings;
    this.mPackageManagerInternal = inputMethodManagerService.mPackageManagerInternal;
    this.mWindowManagerInternal = inputMethodManagerService.mWindowManagerInternal;
    this.mImeConnectionBindFlags = i;
    this.mLatchForTesting = countDownLatch;
  }

  public long getLastBindTime() {
    return this.mLastBindTime;
  }

  public boolean hasConnection() {
    return this.mHasConnection;
  }

  public String getCurId() {
    return this.mCurId;
  }

  public String getSelectedMethodId() {
    return this.mSelectedMethodId;
  }

  public void setSelectedMethodId(String str) {
    this.mSelectedMethodId = str;
  }

  public IBinder getCurToken() {
    return this.mCurToken;
  }

  public Intent getCurIntent() {
    return this.mCurIntent;
  }

  public int getSequenceNumber() {
    return this.mCurSeq;
  }

  public void advanceSequenceNumber() {
    int i = this.mCurSeq + 1;
    this.mCurSeq = i;
    if (i <= 0) {
      this.mCurSeq = 1;
    }
  }

  public IInputMethodInvoker getCurMethod() {
    return this.mCurMethod;
  }

  public int getCurMethodUid() {
    return this.mCurMethodUid;
  }

  public boolean isVisibleBound() {
    return this.mVisibleBound;
  }

  public boolean supportsStylusHandwriting() {
    return this.mSupportsStylusHw;
  }

  public void unbindCurrentMethod() {
    Slog.d(TAG, "unbindCurrentMethod: caller=" + Debug.getCallers(10));
    if (isVisibleBound()) {
      unbindVisibleConnection();
    }
    if (hasConnection()) {
      unbindMainConnection();
    }
    if (getCurToken() != null) {
      removeCurrentToken();
      this.mService.resetSystemUiLocked();
      this.mCurToken = null;
    }
    this.mCurId = null;
    clearCurMethodAndSessions();
  }

  public final void clearCurMethodAndSessions() {
    this.mService.clearClientSessionsLocked();
    this.mCurMethod = null;
    this.mCurMethodUid = -1;
  }

  public final void removeCurrentToken() {
    int curTokenDisplayIdLocked = this.mService.getCurTokenDisplayIdLocked();
    Slog.v(
        TAG,
        "Removing window token: " + this.mCurToken + " for display: " + curTokenDisplayIdLocked);
    this.mWindowManagerInternal.removeWindowToken(
        this.mCurToken, false, false, curTokenDisplayIdLocked);
  }

  public InputBindResult bindCurrentMethod() {
    String str = this.mSelectedMethodId;
    if (str == null) {
      Slog.e(TAG, "mSelectedMethodId is null!");
      return InputBindResult.NO_IME;
    }
    InputMethodInfo inputMethodInfo = (InputMethodInfo) this.mMethodMap.get(str);
    if (inputMethodInfo == null) {
      throw new IllegalArgumentException("Unknown id: " + this.mSelectedMethodId);
    }
    this.mCurIntent = createImeBindingIntent(inputMethodInfo.getComponent());
    if (bindCurrentInputMethodServiceMainConnection()) {
      this.mCurId = inputMethodInfo.getId();
      this.mLastBindTime = SystemClock.uptimeMillis();
      Slog.d(TAG, "bindCurrentMethod: mLastBindTime=" + this.mLastBindTime);
      addFreshWindowToken();
      return new InputBindResult(
          2,
          (IInputMethodSession) null,
          (SparseArray) null,
          (InputChannel) null,
          this.mCurId,
          this.mCurSeq,
          (Matrix) null,
          false);
    }
    Slog.w(
        "InputMethodManagerService",
        "Failure connecting to input method service: " + this.mCurIntent);
    this.mCurIntent = null;
    return InputBindResult.IME_NOT_CONNECTED;
  }

  public final Intent createImeBindingIntent(ComponentName componentName) {
    Intent intent = new Intent("android.view.InputMethod");
    intent.setComponent(componentName);
    intent.putExtra("android.intent.extra.client_label", R.string.miniresolver_switch_to_work);
    intent.putExtra(
        "android.intent.extra.client_intent",
        PendingIntent.getActivity(
            this.mContext, 0, new Intent("android.settings.INPUT_METHOD_SETTINGS"), 67108864));
    return intent;
  }

  public final void addFreshWindowToken() {
    int displayIdToShowImeLocked = this.mService.getDisplayIdToShowImeLocked();
    this.mCurToken = new Binder();
    this.mService.setCurTokenDisplayIdLocked(displayIdToShowImeLocked);
    this.mWindowManagerInternal.addWindowToken(
        this.mCurToken, 2011, displayIdToShowImeLocked, null);
  }

  public final void unbindMainConnection() {
    this.mContext.unbindService(this.mMainConnection);
    this.mHasConnection = false;
  }

  public void unbindVisibleConnection() {
    this.mContext.unbindService(this.mVisibleConnection);
    this.mVisibleBound = false;
  }

  public final boolean bindCurrentInputMethodService(ServiceConnection serviceConnection, int i) {
    if (this.mCurIntent == null || serviceConnection == null) {
      Slog.e(
          TAG, "--- bind failed: service = " + this.mCurIntent + ", conn = " + serviceConnection);
      return false;
    }
    Slog.d(TAG, "bindCurrentInputMethodService: caller=" + Debug.getCallers(10));
    return this.mContext.bindServiceAsUser(
        this.mCurIntent, serviceConnection, i, new UserHandle(this.mSettings.getCurrentUserId()));
  }

  public final boolean bindCurrentInputMethodServiceMainConnection() {
    boolean bindCurrentInputMethodService =
        bindCurrentInputMethodService(this.mMainConnection, this.mImeConnectionBindFlags);
    this.mHasConnection = bindCurrentInputMethodService;
    return bindCurrentInputMethodService;
  }

  public void setCurrentMethodVisible() {
    if (this.mCurMethod != null) {
      if (!hasConnection() || isVisibleBound()) {
        return;
      }
      this.mVisibleBound =
          bindCurrentInputMethodService(this.mVisibleConnection, IME_VISIBLE_BIND_FLAGS);
      return;
    }
    if (!hasConnection()) {
      bindCurrentMethod();
      return;
    }
    long uptimeMillis = SystemClock.uptimeMillis() - this.mLastBindTime;
    String str = TAG;
    Slog.d(
        str,
        "setCurrentMethodVisible: bindingDuration="
            + uptimeMillis
            + ", mLastBindTime="
            + this.mLastBindTime);
    if (uptimeMillis >= 3000) {
      EventLog.writeEvent(32000, getSelectedMethodId(), Long.valueOf(uptimeMillis), 1);
      Slog.w(str, "Force disconnect/connect to the IME in setCurrentMethodVisible()");
      unbindMainConnection();
      bindCurrentInputMethodServiceMainConnection();
    }
  }

  public void setCurrentMethodNotVisible() {
    if (isVisibleBound()) {
      unbindVisibleConnection();
    }
  }
}
