package com.android.systemui.log;

import android.app.StatusBarManager;
import android.os.RemoteException;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SessionTracker implements CoreStartable {
    public static final boolean DEBUG = Log.isLoggable("SessionTracker", 3);
    public final AuthController mAuthController;
    public boolean mKeyguardSessionStarted;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final IStatusBarService mStatusBarManagerService;
    public final UiEventLogger mUiEventLogger;
    public final InstanceIdSequence mInstanceIdGenerator = new InstanceIdSequence(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
    public final Map mSessionToInstanceId = new HashMap();
    public final C17711 mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.log.SessionTracker.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStartedGoingToSleep(int i) {
            SessionTracker sessionTracker = SessionTracker.this;
            if (sessionTracker.mKeyguardSessionStarted) {
                sessionTracker.endSession(1, SessionUiEvent.KEYGUARD_SESSION_END_GOING_TO_SLEEP);
            }
            sessionTracker.mKeyguardSessionStarted = true;
            sessionTracker.startSession(1);
        }
    };
    public final C17722 mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.log.SessionTracker.2
        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            SessionTracker sessionTracker = SessionTracker.this;
            boolean z = sessionTracker.mKeyguardSessionStarted;
            boolean z2 = ((KeyguardStateControllerImpl) sessionTracker.mKeyguardStateController).mShowing;
            if (z2 && !z) {
                sessionTracker.mKeyguardSessionStarted = true;
                sessionTracker.startSession(1);
            } else {
                if (z2 || !z) {
                    return;
                }
                sessionTracker.mKeyguardSessionStarted = false;
                sessionTracker.endSession(1, SessionUiEvent.KEYGUARD_SESSION_END_KEYGUARD_GOING_AWAY);
            }
        }
    };
    public final C17733 mAuthControllerCallback = new AuthController.Callback() { // from class: com.android.systemui.log.SessionTracker.3
        @Override // com.android.systemui.biometrics.AuthController.Callback
        public final void onBiometricPromptDismissed() {
            boolean z = SessionTracker.DEBUG;
            SessionTracker.this.endSession(2, null);
        }

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public final void onBiometricPromptShown() {
            boolean z = SessionTracker.DEBUG;
            SessionTracker.this.startSession(2);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum SessionUiEvent implements UiEventLogger.UiEventEnum {
        KEYGUARD_SESSION_END_KEYGUARD_GOING_AWAY(1354),
        KEYGUARD_SESSION_END_GOING_TO_SLEEP(1355);

        private final int mId;

        SessionUiEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.log.SessionTracker$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.log.SessionTracker$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.log.SessionTracker$3] */
    public SessionTracker(IStatusBarService iStatusBarService, AuthController authController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, UiEventLogger uiEventLogger) {
        this.mStatusBarManagerService = iStatusBarService;
        this.mAuthController = authController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mUiEventLogger = uiEventLogger;
    }

    public static String getString(int i) {
        return i == 1 ? "KEYGUARD" : i == 2 ? "BIOMETRIC_PROMPT" : AbstractC0000x2c234b15.m0m("unknownType=", i);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Iterator it = StatusBarManager.ALL_SESSIONS.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            printWriter.println("  " + getString(intValue) + " instanceId=" + ((HashMap) this.mSessionToInstanceId).get(Integer.valueOf(intValue)));
        }
    }

    public final void endSession(int i, SessionUiEvent sessionUiEvent) {
        Map map = this.mSessionToInstanceId;
        if (((HashMap) map).getOrDefault(Integer.valueOf(i), null) == null) {
            Log.e("SessionTracker", "session [" + getString(i) + "] was not started");
            return;
        }
        InstanceId instanceId = (InstanceId) ((HashMap) map).get(Integer.valueOf(i));
        ((HashMap) map).put(Integer.valueOf(i), null);
        try {
            if (DEBUG) {
                Log.d("SessionTracker", "Session end for [" + getString(i) + "] id=" + instanceId);
            }
            if (sessionUiEvent != null) {
                this.mUiEventLogger.log(sessionUiEvent, instanceId);
            }
            this.mStatusBarManagerService.onSessionEnded(i, instanceId);
        } catch (RemoteException e) {
            Log.e("SessionTracker", "Unable to send onSessionEnded for session=[" + getString(i) + "]", e);
        }
    }

    public final InstanceId getSessionId(int i) {
        return (InstanceId) ((HashMap) this.mSessionToInstanceId).getOrDefault(Integer.valueOf(i), null);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mAuthController.addCallback(this.mAuthControllerCallback);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.addCallback(this.mKeyguardStateCallback);
        if (keyguardStateControllerImpl.mShowing) {
            this.mKeyguardSessionStarted = true;
            startSession(1);
        }
    }

    public final void startSession(int i) {
        Map map = this.mSessionToInstanceId;
        if (((HashMap) map).getOrDefault(Integer.valueOf(i), null) != null) {
            Log.e("SessionTracker", "session [" + getString(i) + "] was already started");
            return;
        }
        InstanceId newInstanceId = this.mInstanceIdGenerator.newInstanceId();
        ((HashMap) map).put(Integer.valueOf(i), newInstanceId);
        try {
            if (DEBUG) {
                Log.d("SessionTracker", "Session start for [" + getString(i) + "] id=" + newInstanceId);
            }
            this.mStatusBarManagerService.onSessionStarted(i, newInstanceId);
        } catch (RemoteException e) {
            Log.e("SessionTracker", "Unable to send onSessionStarted for session=[" + getString(i) + "]", e);
        }
    }
}
