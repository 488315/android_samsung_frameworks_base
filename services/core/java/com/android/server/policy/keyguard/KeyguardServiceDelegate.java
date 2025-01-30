package com.android.server.policy.keyguard;

import android.R;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.dreams.DreamManagerInternal;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.WindowManagerPolicyConstants;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardService;
import com.android.server.LocalServices;
import com.android.server.UiThread;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.wm.EventLogTags;
import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class KeyguardServiceDelegate {
  public final KeyguardStateMonitor.StateCallback mCallback;
  public final Context mContext;
  public DrawnListener mDrawnListenerWhenConnect;
  public KeyguardServiceWrapper mKeyguardService;
  public PersonaManagerInternal mPersonaManagerInternal;
  public final KeyguardState mKeyguardState = new KeyguardState();
  public final DreamManagerInternal.DreamManagerStateListener mDreamManagerStateListener =
      new DreamManagerInternal
          .DreamManagerStateListener() { // from class:
                                         // com.android.server.policy.keyguard.KeyguardServiceDelegate.1
        public void onDreamingStarted() {
          KeyguardServiceDelegate.this.onDreamingStarted();
        }

        public void onDreamingStopped() {
          KeyguardServiceDelegate.this.onDreamingStopped();
        }
      };
  public final ServiceConnection mKeyguardConnection = new ServiceConnectionC23182();
  public final Handler mHandler = UiThread.getHandler();

  public interface DrawnListener {
    void onDrawn();
  }

  public final class KeyguardState {
    public boolean bootAnimFinished;
    public boolean bootCompleted;
    public boolean coverOccluded;
    public int currentUser = 0;
    public boolean deviceHasKeyguard;
    public boolean dexOccluded;
    public boolean dreaming;
    public boolean enabled;
    public boolean inputRestricted;
    public int interactiveState;
    public volatile boolean occluded;
    public int offReason;
    public int screenState;
    public boolean secure;
    public boolean showing;
    public boolean systemIsReady;

    public KeyguardState() {
      reset();
    }

    public final void reset() {
      this.showing = true;
      this.occluded = false;
      this.secure = true;
      this.deviceHasKeyguard = true;
      this.enabled = true;
      this.bootAnimFinished = false;
    }
  }

  public final class KeyguardShowDelegate extends IKeyguardDrawnCallback.Stub {
    public DrawnListener mDrawnListener;

    public KeyguardShowDelegate(DrawnListener drawnListener) {
      this.mDrawnListener = drawnListener;
    }

    public void onDrawn() {
      Log.v("KeyguardServiceDelegate", "!@BOOT: **** SHOWN CALLED ****");
      DrawnListener drawnListener = this.mDrawnListener;
      if (drawnListener != null) {
        drawnListener.onDrawn();
      }
    }
  }

  public final class KeyguardExitDelegate extends IKeyguardExitCallback.Stub {
    public WindowManagerPolicy.OnKeyguardExitResult mOnKeyguardExitResult;

    public KeyguardExitDelegate(WindowManagerPolicy.OnKeyguardExitResult onKeyguardExitResult) {
      this.mOnKeyguardExitResult = onKeyguardExitResult;
    }

    public void onKeyguardExitResult(boolean z) {
      Log.v("KeyguardServiceDelegate", "**** onKeyguardExitResult(" + z + ") CALLED ****");
      WindowManagerPolicy.OnKeyguardExitResult onKeyguardExitResult = this.mOnKeyguardExitResult;
      if (onKeyguardExitResult != null) {
        onKeyguardExitResult.onKeyguardExitResult(z);
      }
    }
  }

  public KeyguardServiceDelegate(
      Context context, KeyguardStateMonitor.StateCallback stateCallback) {
    this.mContext = context;
    this.mCallback = stateCallback;
  }

  public void bindService(Context context) {
    Intent intent = new Intent();
    ComponentName unflattenFromString =
        ComponentName.unflattenFromString(
            context
                .getApplicationContext()
                .getResources()
                .getString(R.string.duration_days_relative_future));
    intent.addFlags(256);
    intent.setComponent(unflattenFromString);
    if (!context.bindServiceAsUser(
        intent, this.mKeyguardConnection, 1, this.mHandler, UserHandle.SYSTEM)) {
      Log.v(
          "KeyguardServiceDelegate", "!@BOOT: *** Keyguard: can't bind to " + unflattenFromString);
      KeyguardState keyguardState = this.mKeyguardState;
      keyguardState.showing = false;
      keyguardState.secure = false;
      synchronized (keyguardState) {
        this.mKeyguardState.deviceHasKeyguard = false;
      }
    } else {
      Log.v("KeyguardServiceDelegate", "!@BOOT: *** Keyguard started");
    }
    DreamManagerInternal dreamManagerInternal =
        (DreamManagerInternal) LocalServices.getService(DreamManagerInternal.class);
    if (CoreRune.SYSUI_GRADLE_BUILD) {
      dreamManagerInternal.unregisterDreamManagerStateListener(this.mDreamManagerStateListener);
    }
    dreamManagerInternal.registerDreamManagerStateListener(this.mDreamManagerStateListener);
  }

  /* renamed from: com.android.server.policy.keyguard.KeyguardServiceDelegate$2 */
  public class ServiceConnectionC23182 implements ServiceConnection {
    public ServiceConnectionC23182() {}

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      Log.v("KeyguardServiceDelegate", "!@BOOT: *** Keyguard connected (yay!)");
      KeyguardServiceDelegate keyguardServiceDelegate = KeyguardServiceDelegate.this;
      keyguardServiceDelegate.mKeyguardService =
          new KeyguardServiceWrapper(
              keyguardServiceDelegate.mContext,
              IKeyguardService.Stub.asInterface(iBinder),
              KeyguardServiceDelegate.this.mCallback);
      Log.v(
          "KeyguardServiceDelegate",
          "!@BOOT: *** Created keyguardService=" + KeyguardServiceDelegate.this.mKeyguardService);
      if (KeyguardServiceDelegate.this.mKeyguardState.systemIsReady) {
        KeyguardServiceDelegate.this.mKeyguardService.onSystemReady();
        if (KeyguardServiceDelegate.this.mKeyguardState.currentUser != -10000) {
          KeyguardServiceDelegate keyguardServiceDelegate2 = KeyguardServiceDelegate.this;
          keyguardServiceDelegate2.mKeyguardService.setCurrentUser(
              keyguardServiceDelegate2.mKeyguardState.currentUser);
        }
        if (KeyguardServiceDelegate.this.mKeyguardState.interactiveState == 2
            || KeyguardServiceDelegate.this.mKeyguardState.interactiveState == 1) {
          KeyguardServiceDelegate.this.mKeyguardService.onStartedWakingUp(0, false);
        }
        if (KeyguardServiceDelegate.this.mKeyguardState.interactiveState == 2) {
          KeyguardServiceDelegate.this.mKeyguardService.onFinishedWakingUp();
        }
        if (KeyguardServiceDelegate.this.mKeyguardState.screenState == 2
            || KeyguardServiceDelegate.this.mKeyguardState.screenState == 1) {
          KeyguardServiceWrapper keyguardServiceWrapper =
              KeyguardServiceDelegate.this.mKeyguardService;
          KeyguardServiceDelegate keyguardServiceDelegate3 = KeyguardServiceDelegate.this;
          keyguardServiceWrapper.onScreenTurningOn(
              keyguardServiceDelegate3
              .new KeyguardShowDelegate(keyguardServiceDelegate3.mDrawnListenerWhenConnect));
        }
        if (KeyguardServiceDelegate.this.mKeyguardState.screenState == 2) {
          KeyguardServiceDelegate.this.mKeyguardService.onScreenTurnedOn();
        }
        if (KeyguardServiceDelegate.this.mKeyguardState.interactiveState == 3
            || KeyguardServiceDelegate.this.mKeyguardState.interactiveState == 0) {
          KeyguardServiceDelegate.this.mKeyguardService.onStartedGoingToSleep(0);
        }
        if (KeyguardServiceDelegate.this.mKeyguardState.interactiveState == 0) {
          KeyguardServiceDelegate.this.mKeyguardService.onFinishedGoingToSleep(0, false);
        }
        if (KeyguardServiceDelegate.this.mKeyguardState.screenState == 0
            || KeyguardServiceDelegate.this.mKeyguardState.screenState == 3) {
          KeyguardServiceDelegate.this.mKeyguardService.onScreenTurningOff();
        }
        if (KeyguardServiceDelegate.this.mKeyguardState.screenState == 0) {
          KeyguardServiceDelegate.this.mKeyguardService.onScreenTurnedOff();
        }
        KeyguardServiceDelegate.this.mDrawnListenerWhenConnect = null;
      }
      if (KeyguardServiceDelegate.this.mKeyguardState.bootCompleted) {
        KeyguardServiceDelegate.this.mKeyguardService.onBootCompleted();
      }
      if (KeyguardServiceDelegate.this.mKeyguardState.occluded) {
        KeyguardServiceDelegate keyguardServiceDelegate4 = KeyguardServiceDelegate.this;
        keyguardServiceDelegate4.mKeyguardService.setOccluded(
            keyguardServiceDelegate4.mKeyguardState.occluded, false);
      }
      if (!KeyguardServiceDelegate.this.mKeyguardState.enabled) {
        KeyguardServiceDelegate keyguardServiceDelegate5 = KeyguardServiceDelegate.this;
        keyguardServiceDelegate5.mKeyguardService.setKeyguardEnabled(
            keyguardServiceDelegate5.mKeyguardState.enabled);
      }
      if (KeyguardServiceDelegate.this.mKeyguardState.dreaming) {
        KeyguardServiceDelegate.this.mKeyguardService.onDreamingStarted();
      }
      if (KeyguardServiceDelegate.this.mKeyguardState.bootAnimFinished) {
        KeyguardServiceDelegate.this.mKeyguardService.onFinishedBootAnim();
      }
      if (KeyguardServiceDelegate.this.mKeyguardState.dexOccluded) {
        KeyguardServiceDelegate keyguardServiceDelegate6 = KeyguardServiceDelegate.this;
        keyguardServiceDelegate6.mKeyguardService.setDexOccluded(
            keyguardServiceDelegate6.mKeyguardState.dexOccluded);
      }
      if (KeyguardServiceDelegate.this.mKeyguardState.coverOccluded) {
        KeyguardServiceDelegate keyguardServiceDelegate7 = KeyguardServiceDelegate.this;
        keyguardServiceDelegate7.mKeyguardService.setCoverOccluded(
            keyguardServiceDelegate7.mKeyguardState.coverOccluded);
      }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
      Log.v("KeyguardServiceDelegate", "*** Keyguard disconnected (boo!)");
      KeyguardServiceDelegate keyguardServiceDelegate = KeyguardServiceDelegate.this;
      keyguardServiceDelegate.mKeyguardService = null;
      keyguardServiceDelegate.mKeyguardState.reset();
      KeyguardServiceDelegate.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.policy.keyguard.KeyguardServiceDelegate$2$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              KeyguardServiceDelegate.ServiceConnectionC23182.lambda$onServiceDisconnected$0();
            }
          });
    }

    public static /* synthetic */ void lambda$onServiceDisconnected$0() {
      try {
        ActivityTaskManager.getService().setLockScreenShown(true, false);
      } catch (RemoteException unused) {
      }
    }
  }

  public boolean isShowing() {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      this.mKeyguardState.showing = keyguardServiceWrapper.isShowing();
    }
    return this.mKeyguardState.showing;
  }

  public boolean isTrusted() {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      return keyguardServiceWrapper.isTrusted();
    }
    return false;
  }

  public boolean hasKeyguard() {
    return this.mKeyguardState.deviceHasKeyguard;
  }

  public boolean isInputRestricted() {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      this.mKeyguardState.inputRestricted = keyguardServiceWrapper.isInputRestricted();
    }
    return this.mKeyguardState.inputRestricted;
  }

  public void verifyUnlock(WindowManagerPolicy.OnKeyguardExitResult onKeyguardExitResult) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.verifyUnlock(new KeyguardExitDelegate(onKeyguardExitResult));
    }
  }

  public void setOccluded(boolean z, boolean z2) {
    if (this.mKeyguardService != null && z2) {
      Log.v("KeyguardServiceDelegate", "setOccluded(" + z + ")");
      EventLogTags.writeWmSetKeyguardOccluded(z ? 1 : 0, 0, 0, "setOccluded");
      this.mKeyguardService.setOccluded(z, false);
    }
    this.mKeyguardState.occluded = z;
  }

  public boolean isOccluded() {
    return this.mKeyguardState.occluded;
  }

  public void dismiss(
      IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.dismiss(iKeyguardDismissCallback, charSequence);
    }
  }

  public boolean isSecure(int i) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      this.mKeyguardState.secure = keyguardServiceWrapper.isSecure(i);
    }
    return this.mKeyguardState.secure;
  }

  public void onDreamingStarted() {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.onDreamingStarted();
    }
    this.mKeyguardState.dreaming = true;
  }

  public void onDreamingStopped() {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.onDreamingStopped();
    }
    this.mKeyguardState.dreaming = false;
  }

  public void onStartedWakingUp(int i, boolean z) {
    if (this.mKeyguardService != null) {
      Log.v("KeyguardServiceDelegate", "onStartedWakingUp()");
      this.mKeyguardService.onStartedWakingUp(i, z);
    }
    this.mKeyguardState.interactiveState = 1;
  }

  public void onFinishedWakingUp() {
    if (this.mKeyguardService != null) {
      Log.v("KeyguardServiceDelegate", "onFinishedWakingUp()");
      this.mKeyguardService.onFinishedWakingUp();
    }
    this.mKeyguardState.interactiveState = 2;
  }

  public void onScreenTurningOff() {
    if (this.mKeyguardService != null) {
      Log.v("KeyguardServiceDelegate", "onScreenTurningOff()");
      this.mKeyguardService.onScreenTurningOff();
    }
    this.mKeyguardState.screenState = 3;
  }

  public void onScreenTurnedOff() {
    if (this.mKeyguardService != null) {
      Log.v("KeyguardServiceDelegate", "onScreenTurnedOff()");
      this.mKeyguardService.onScreenTurnedOff();
    }
    this.mKeyguardState.screenState = 0;
  }

  public void onScreenTurningOn(DrawnListener drawnListener) {
    if (this.mKeyguardService != null) {
      Log.v("KeyguardServiceDelegate", "onScreenTurnedOn(showListener = " + drawnListener + ")");
      this.mKeyguardService.onScreenTurningOn(new KeyguardShowDelegate(drawnListener));
    } else {
      Slog.w("KeyguardServiceDelegate", "onScreenTurningOn(): no keyguard service!");
      this.mDrawnListenerWhenConnect = drawnListener;
    }
    this.mKeyguardState.screenState = 1;
  }

  public void onScreenTurnedOn() {
    if (this.mKeyguardService != null) {
      Log.v("KeyguardServiceDelegate", "onScreenTurnedOn()");
      this.mKeyguardService.onScreenTurnedOn();
    }
    this.mKeyguardState.screenState = 2;
  }

  public void onStartedGoingToSleep(int i) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.onStartedGoingToSleep(i);
    }
    this.mKeyguardState.offReason = WindowManagerPolicyConstants.translateSleepReasonToOffReason(i);
    this.mKeyguardState.interactiveState = 3;
  }

  public void onFinishedGoingToSleep(int i, boolean z) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.onFinishedGoingToSleep(i, z);
    }
    this.mKeyguardState.interactiveState = 0;
  }

  public void setKeyguardEnabled(boolean z) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.setKeyguardEnabled(z);
    }
    this.mKeyguardState.enabled = z;
  }

  public void onSystemReady() {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.onSystemReady();
    } else {
      this.mKeyguardState.systemIsReady = true;
    }
  }

  public void doKeyguardTimeout(Bundle bundle) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.doKeyguardTimeout(bundle);
    }
    if (getPersonaManagerInternal() != null) {
      getPersonaManagerInternal().doKeyguardTimeout();
    }
  }

  public void setCurrentUser(int i) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.setCurrentUser(i);
    }
    this.mKeyguardState.currentUser = i;
  }

  public void setSwitchingUser(boolean z) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.setSwitchingUser(z);
    }
  }

  public void startKeyguardExitAnimation(long j) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.startKeyguardExitAnimation(j, 0L);
    }
  }

  public void onBootCompleted() {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.onBootCompleted();
    }
    this.mKeyguardState.bootCompleted = true;
  }

  public void onShortPowerPressedGoHome() {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.onShortPowerPressedGoHome();
    }
  }

  public void dismissKeyguardToLaunch(Intent intent) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.dismissKeyguardToLaunch(intent);
    }
  }

  public void onSystemKeyPressed(int i) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.onSystemKeyPressed(i);
    }
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
    long start = protoOutputStream.start(j);
    protoOutputStream.write(1133871366145L, this.mKeyguardState.showing);
    protoOutputStream.write(1133871366146L, this.mKeyguardState.occluded);
    protoOutputStream.write(1133871366147L, this.mKeyguardState.secure);
    protoOutputStream.write(1159641169924L, this.mKeyguardState.screenState);
    protoOutputStream.write(1159641169925L, this.mKeyguardState.interactiveState);
    protoOutputStream.end(start);
  }

  public void dump(String str, PrintWriter printWriter) {
    printWriter.println(str + "KeyguardServiceDelegate");
    String str2 = str + "  ";
    printWriter.println(str2 + "showing=" + this.mKeyguardState.showing);
    printWriter.println(str2 + "inputRestricted=" + this.mKeyguardState.inputRestricted);
    printWriter.println(str2 + "occluded=" + this.mKeyguardState.occluded);
    printWriter.println(str2 + "secure=" + this.mKeyguardState.secure);
    printWriter.println(str2 + "dreaming=" + this.mKeyguardState.dreaming);
    printWriter.println(str2 + "systemIsReady=" + this.mKeyguardState.systemIsReady);
    printWriter.println(str2 + "deviceHasKeyguard=" + this.mKeyguardState.deviceHasKeyguard);
    printWriter.println(str2 + "enabled=" + this.mKeyguardState.enabled);
    printWriter.println(
        str2
            + "offReason="
            + WindowManagerPolicyConstants.offReasonToString(this.mKeyguardState.offReason));
    printWriter.println(str2 + "currentUser=" + this.mKeyguardState.currentUser);
    printWriter.println(str2 + "bootCompleted=" + this.mKeyguardState.bootCompleted);
    printWriter.println(
        str2 + "screenState=" + screenStateToString(this.mKeyguardState.screenState));
    printWriter.println(
        str2
            + "interactiveState="
            + interactiveStateToString(this.mKeyguardState.interactiveState));
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.dump(str2, printWriter);
    }
  }

  public static String screenStateToString(int i) {
    return i != 0
        ? i != 1
            ? i != 2 ? i != 3 ? Integer.toString(i) : "SCREEN_STATE_TURNING_OFF" : "SCREEN_STATE_ON"
            : "SCREEN_STATE_TURNING_ON"
        : "SCREEN_STATE_OFF";
  }

  public static String interactiveStateToString(int i) {
    return i != 0
        ? i != 1
            ? i != 2
                ? i != 3 ? Integer.toString(i) : "INTERACTIVE_STATE_GOING_TO_SLEEP"
                : "INTERACTIVE_STATE_AWAKE"
            : "INTERACTIVE_STATE_WAKING"
        : "INTERACTIVE_STATE_SLEEP";
  }

  public final PersonaManagerInternal getPersonaManagerInternal() {
    if (this.mPersonaManagerInternal == null) {
      this.mPersonaManagerInternal =
          (PersonaManagerInternal) LocalServices.getService(PersonaManagerInternal.class);
    }
    return this.mPersonaManagerInternal;
  }

  public void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.setPendingIntentAfterUnlock(pendingIntent, intent);
    }
  }

  public boolean isSimLocked() {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper == null || !keyguardServiceWrapper.isKeyguardStateMonitorValid()) {
      return false;
    }
    return this.mKeyguardService.isSimLocked();
  }

  public boolean isScreenTurnedOn() {
    return this.mKeyguardState.screenState == 2;
  }

  public void startFingerprintAuthentication() {
    if (this.mKeyguardService != null) {
      Log.v("KeyguardServiceDelegate", "Start Fingerprint Authentication");
      this.mKeyguardService.startFingerprintAuthentication();
    }
  }

  public void finishedBootAnim() {
    KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
    if (keyguardServiceWrapper != null) {
      keyguardServiceWrapper.onFinishedBootAnim();
    } else {
      this.mKeyguardState.bootAnimFinished = true;
    }
  }

  public void startedEarlyWakingUp(int i) {
    if (this.mKeyguardService != null) {
      Log.v("KeyguardServiceDelegate", "startedEarlyWakingUp reason=" + i);
      this.mKeyguardService.startedEarlyWakingUp(i);
    }
  }

  public void setOccluded(boolean z, boolean z2, int i) {
    if (i == 2) {
      if (this.mKeyguardService != null && z2) {
        Log.v("KeyguardServiceDelegate", "setOccluded(" + z + ") , dex displayId=" + i);
        this.mKeyguardService.setDexOccluded(z);
      }
      this.mKeyguardState.dexOccluded = z;
      return;
    }
    setOccluded(z, z2);
  }
}
