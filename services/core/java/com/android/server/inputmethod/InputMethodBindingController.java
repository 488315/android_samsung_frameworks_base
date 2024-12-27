package com.android.server.inputmethod;

import android.R;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.util.Slog;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;

import com.android.internal.inputmethod.IInputMethod;
import com.android.internal.inputmethod.IInputMethodSession;
import com.android.internal.inputmethod.InlineSuggestionsRequestCallback;
import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;
import com.android.internal.inputmethod.InputBindResult;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.autofill.AutofillInlineSuggestionsRequestSession;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wm.WindowManagerInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class InputMethodBindingController {
    static final int IME_CONNECTION_BIND_FLAGS = 1082654725;
    static final int IME_VISIBLE_BIND_FLAGS = 738201601;
    public final Context mContext;
    public String mCurId;
    public Intent mCurIntent;
    public IInputMethodInvoker mCurMethod;
    public int mCurSeq;
    public IBinder mCurToken;
    public InputMethodSubtype mCurrentSubtype;
    public boolean mHasMainConnection;
    public long mLastBindTime;
    public final PackageManagerInternal mPackageManagerInternal;
    public String mSelectedMethodId;
    public final InputMethodManagerService mService;
    public boolean mSupportsConnectionlessStylusHw;
    public boolean mSupportsStylusHw;
    public final int mUserId;
    public boolean mVisibleBound;
    public final WindowManagerInternal mWindowManagerInternal;
    public int mCurMethodUid = -1;
    public int mCurTokenDisplayId = -1;
    public int mDisplayIdToShowIme = -1;
    public int mDeviceIdToShowIme = 0;
    public final AnonymousClass1 mVisibleConnection =
            new ServiceConnection() { // from class:
                                      // com.android.server.inputmethod.InputMethodBindingController.1
                @Override // android.content.ServiceConnection
                public final void onBindingDied(ComponentName componentName) {
                    synchronized (ImfLock.class) {
                        InlineSuggestionsRequestCallback inlineSuggestionsRequestCallback =
                                InputMethodBindingController.this
                                        .mAutofillController
                                        .mInlineSuggestionsRequestCallback;
                        if (inlineSuggestionsRequestCallback != null) {
                            inlineSuggestionsRequestCallback
                                    .onInlineSuggestionsSessionInvalidated();
                        }
                        InputMethodBindingController inputMethodBindingController =
                                InputMethodBindingController.this;
                        if (inputMethodBindingController.mVisibleBound) {
                            inputMethodBindingController.mContext.unbindService(
                                    inputMethodBindingController.mVisibleConnection);
                            inputMethodBindingController.mVisibleBound = false;
                        }
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceConnected(
                        ComponentName componentName, IBinder iBinder) {}

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    synchronized (ImfLock.class) {
                        InlineSuggestionsRequestCallback inlineSuggestionsRequestCallback =
                                InputMethodBindingController.this
                                        .mAutofillController
                                        .mInlineSuggestionsRequestCallback;
                        if (inlineSuggestionsRequestCallback != null) {
                            inlineSuggestionsRequestCallback
                                    .onInlineSuggestionsSessionInvalidated();
                        }
                    }
                }
            };
    public final AnonymousClass2 mMainConnection =
            new ServiceConnection() { // from class:
                                      // com.android.server.inputmethod.InputMethodBindingController.2
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    IInputMethodInvoker iInputMethodInvoker;
                    Trace.traceBegin(32L, "IMMS.onServiceConnected");
                    synchronized (ImfLock.class) {
                        try {
                            Intent intent = InputMethodBindingController.this.mCurIntent;
                            if (intent != null && componentName.equals(intent.getComponent())) {
                                InputMethodBindingController inputMethodBindingController =
                                        InputMethodBindingController.this;
                                IInputMethod asInterface = IInputMethod.Stub.asInterface(iBinder);
                                if (asInterface == null) {
                                    iInputMethodInvoker = null;
                                } else {
                                    if (!Binder.isProxy(asInterface)) {
                                        throw new UnsupportedOperationException(
                                                asInterface + " must have been a BinderProxy.");
                                    }
                                    iInputMethodInvoker = new IInputMethodInvoker(asInterface);
                                }
                                inputMethodBindingController.mCurMethod = iInputMethodInvoker;
                                updateCurrentMethodUid();
                                Slog.w("InputMethodBindingController", "onServiceConnected");
                                if (InputMethodBindingController.this.mCurToken == null) {
                                    Slog.w(
                                            "InputMethodBindingController",
                                            "Service connected without a token!");
                                    InputMethodBindingController.this.unbindCurrentMethod();
                                    Trace.traceEnd(32L);
                                    return;
                                }
                                Slog.v(
                                        "InputMethodBindingController",
                                        "Initiating attach with token: "
                                                + InputMethodBindingController.this.mCurToken);
                                InputMethodInfo inputMethodInfo =
                                        InputMethodSettingsRepository.get(
                                                        InputMethodBindingController.this.mUserId)
                                                .mMethodMap
                                                .get(
                                                        InputMethodBindingController.this
                                                                .mSelectedMethodId);
                                boolean z =
                                        InputMethodBindingController.this.mSupportsStylusHw
                                                != inputMethodInfo.supportsStylusHandwriting();
                                InputMethodBindingController.this.mSupportsStylusHw =
                                        inputMethodInfo.supportsStylusHandwriting();
                                if (z) {
                                    InputMethodManager
                                            .invalidateLocalStylusHandwritingAvailabilityCaches();
                                }
                                if (InputMethodBindingController.this
                                                .mSupportsConnectionlessStylusHw
                                        != inputMethodInfo
                                                .supportsConnectionlessStylusHandwriting()) {
                                    InputMethodBindingController.this
                                                    .mSupportsConnectionlessStylusHw =
                                            inputMethodInfo
                                                    .supportsConnectionlessStylusHandwriting();
                                    InputMethodManager
                                            .invalidateLocalConnectionlessStylusHandwritingAvailabilityCaches();
                                }
                                InputMethodBindingController inputMethodBindingController2 =
                                        InputMethodBindingController.this;
                                InputMethodManagerService inputMethodManagerService =
                                        inputMethodBindingController2.mService;
                                IInputMethodInvoker iInputMethodInvoker2 =
                                        inputMethodBindingController2.mCurMethod;
                                IBinder iBinder2 = inputMethodBindingController2.mCurToken;
                                inputMethodManagerService.getClass();
                                InputMethodManagerService.InputMethodPrivilegedOperationsImpl
                                        inputMethodPrivilegedOperationsImpl =
                                                new InputMethodManagerService
                                                        .InputMethodPrivilegedOperationsImpl(
                                                        inputMethodManagerService, iBinder2);
                                int inputMethodNavButtonFlagsLocked =
                                        inputMethodManagerService
                                                .getInputMethodNavButtonFlagsLocked();
                                iInputMethodInvoker2.getClass();
                                IInputMethod.InitParams initParams = new IInputMethod.InitParams();
                                initParams.token = iBinder2;
                                initParams.privilegedOperations =
                                        inputMethodPrivilegedOperationsImpl;
                                initParams.navigationBarFlags = inputMethodNavButtonFlagsLocked;
                                try {
                                    iInputMethodInvoker2.mTarget.initializeInternal(initParams);
                                } catch (RemoteException e) {
                                    IInputMethodInvoker.logRemoteException(e);
                                }
                                InputMethodBindingController inputMethodBindingController3 =
                                        InputMethodBindingController.this;
                                InputMethodManagerService inputMethodManagerService2 =
                                        inputMethodBindingController3.mService;
                                int i = inputMethodBindingController3.mCurMethodUid;
                                inputMethodManagerService2.mHandler.removeMessages(7000);
                                inputMethodManagerService2
                                        .mHandler
                                        .obtainMessage(7000, i, 0)
                                        .sendToTarget();
                                InputMethodManagerService inputMethodManagerService3 =
                                        InputMethodBindingController.this.mService;
                                ClientState clientState = inputMethodManagerService3.mCurClient;
                                if (clientState != null) {
                                    inputMethodManagerService3.finishSessionLocked(
                                            clientState.mCurSession);
                                    clientState.mCurSession = null;
                                    clientState.mSessionRequested = false;
                                    InputMethodManagerService
                                            .clearClientSessionForAccessibilityLocked(
                                                    inputMethodManagerService3.mCurClient);
                                    inputMethodManagerService3.requestClientSessionLocked(
                                            inputMethodManagerService3.mCurClient);
                                    InputMethodManagerService
                                            .requestClientSessionForAccessibilityLocked(
                                                    inputMethodManagerService3.mCurClient);
                                }
                                InputMethodBindingController.this.mAutofillController
                                        .performOnCreateInlineSuggestionsRequest();
                            }
                            InputMethodBindingController.this
                                    .mService
                                    .mHandler
                                    .obtainMessage(1090)
                                    .sendToTarget();
                            Trace.traceEnd(32L);
                            InputMethodBindingController.this.getClass();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    Intent intent;
                    synchronized (ImfLock.class) {
                        try {
                            Slog.v(
                                    "InputMethodBindingController",
                                    "Service disconnected: "
                                            + componentName
                                            + " mCurIntent="
                                            + InputMethodBindingController.this.mCurIntent);
                            InputMethodBindingController inputMethodBindingController =
                                    InputMethodBindingController.this;
                            if (inputMethodBindingController.mCurMethod != null
                                    && (intent = inputMethodBindingController.mCurIntent) != null
                                    && componentName.equals(intent.getComponent())) {
                                InputMethodBindingController.this.mLastBindTime =
                                        SystemClock.uptimeMillis();
                                Slog.d(
                                        "InputMethodBindingController",
                                        "onServiceDisconnected: mLastBindTime="
                                                + InputMethodBindingController.this.mLastBindTime);
                                InputMethodBindingController.this.clearCurMethodAndSessions();
                                InputMethodManagerService inputMethodManagerService =
                                        InputMethodBindingController.this.mService;
                                inputMethodManagerService.mVisibilityStateComputer.mInputShown =
                                        false;
                                inputMethodManagerService.unbindCurrentClientLocked(3);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                public final void updateCurrentMethodUid() {
                    String packageName =
                            InputMethodBindingController.this
                                    .mCurIntent
                                    .getComponent()
                                    .getPackageName();
                    InputMethodBindingController inputMethodBindingController =
                            InputMethodBindingController.this;
                    int packageUid =
                            inputMethodBindingController.mPackageManagerInternal.getPackageUid(
                                    packageName, 0L, inputMethodBindingController.mUserId);
                    if (packageUid >= 0) {
                        InputMethodBindingController.this.mCurMethodUid = packageUid;
                    } else {
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                "Failed to get UID for package=",
                                packageName,
                                "InputMethodBindingController");
                        InputMethodBindingController.this.mCurMethodUid = -1;
                    }
                }
            };
    public final AutofillSuggestionsController mAutofillController =
            new AutofillSuggestionsController(this);

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.inputmethod.InputMethodBindingController$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.inputmethod.InputMethodBindingController$2] */
    public InputMethodBindingController(
            int i, InputMethodManagerService inputMethodManagerService) {
        this.mUserId = i;
        this.mService = inputMethodManagerService;
        this.mContext = inputMethodManagerService.mContext;
        this.mPackageManagerInternal = inputMethodManagerService.mPackageManagerInternal;
        this.mWindowManagerInternal = inputMethodManagerService.mWindowManagerInternal;
    }

    public final boolean bindCurrentInputMethodService(ServiceConnection serviceConnection, int i) {
        if (this.mCurIntent != null && serviceConnection != null) {
            ActivityManagerService$$ExternalSyntheticOutline0.m(
                    10,
                    new StringBuilder("bindCurrentInputMethodService: caller="),
                    "InputMethodBindingController");
            return this.mContext.bindServiceAsUser(
                    this.mCurIntent, serviceConnection, i, new UserHandle(this.mUserId));
        }
        Slog.e(
                "InputMethodBindingController",
                "--- bind failed: service = " + this.mCurIntent + ", conn = " + serviceConnection);
        return false;
    }

    public final InputBindResult bindCurrentMethod() {
        if (this.mSelectedMethodId == null) {
            Slog.e("InputMethodBindingController", "mSelectedMethodId is null!");
            return InputBindResult.NO_IME;
        }
        InputMethodInfo inputMethodInfo =
                InputMethodSettingsRepository.get(this.mUserId)
                        .mMethodMap
                        .get(this.mSelectedMethodId);
        if (inputMethodInfo == null) {
            throw new IllegalArgumentException("Unknown id: " + this.mSelectedMethodId);
        }
        ComponentName component = inputMethodInfo.getComponent();
        Intent intent = new Intent("android.view.InputMethod");
        intent.setComponent(component);
        intent.putExtra(
                "android.intent.extra.client_label",
                R.string.notification_reply_button_accessibility);
        intent.putExtra(
                "android.intent.extra.client_intent",
                PendingIntent.getActivity(
                        this.mContext,
                        0,
                        new Intent("android.settings.INPUT_METHOD_SETTINGS"),
                        67108864,
                        ActivityOptions.makeBasic()
                                .setPendingIntentCreatorBackgroundActivityStartMode(2)
                                .toBundle()));
        this.mCurIntent = intent;
        boolean bindCurrentInputMethodService =
                bindCurrentInputMethodService(this.mMainConnection, IME_CONNECTION_BIND_FLAGS);
        this.mHasMainConnection = bindCurrentInputMethodService;
        if (!bindCurrentInputMethodService) {
            Slog.w(
                    "InputMethodManagerService",
                    "Failure connecting to input method service: " + this.mCurIntent);
            this.mCurIntent = null;
            return InputBindResult.IME_NOT_CONNECTED;
        }
        this.mCurId = inputMethodInfo.getId();
        this.mLastBindTime = SystemClock.uptimeMillis();
        Slog.d(
                "InputMethodBindingController",
                "bindCurrentMethod: mLastBindTime=" + this.mLastBindTime);
        Binder binder = new Binder();
        this.mCurToken = binder;
        int i = this.mDisplayIdToShowIme;
        this.mCurTokenDisplayId = i;
        this.mWindowManagerInternal.addWindowToken(binder, 2011, i, null);
        return new InputBindResult(
                2,
                (IInputMethodSession) null,
                (SparseArray) null,
                (InputChannel) null,
                this.mCurId,
                this.mCurSeq,
                false);
    }

    public final void clearCurMethodAndSessions() {
        InputMethodManagerService inputMethodManagerService = this.mService;
        if (inputMethodManagerService.getCurMethodLocked() != null) {
            inputMethodManagerService.mClientController.forAllClients(
                    new InputMethodManagerService$$ExternalSyntheticLambda9(
                            1, inputMethodManagerService));
            inputMethodManagerService.finishSessionLocked(
                    inputMethodManagerService.mEnabledSession);
            for (int i = 0;
                    i < inputMethodManagerService.mEnabledAccessibilitySessions.size();
                    i++) {
                InputMethodManagerService.finishSessionForAccessibilityLocked(
                        (InputMethodManagerService.AccessibilitySessionState)
                                inputMethodManagerService.mEnabledAccessibilitySessions.valueAt(i));
            }
            inputMethodManagerService.mEnabledSession = null;
            inputMethodManagerService.mEnabledAccessibilitySessions.clear();
            inputMethodManagerService.mHandler.removeMessages(7000);
            inputMethodManagerService.mHandler.obtainMessage(7000, -1, 0).sendToTarget();
        }
        StatusBarManagerInternal statusBarManagerInternal =
                inputMethodManagerService.mStatusBarManagerInternal;
        if (statusBarManagerInternal != null) {
            StatusBarManagerService.this.setIconVisibility(
                    inputMethodManagerService.mSlotIme, false);
        }
        inputMethodManagerService.mInFullscreenMode = false;
        inputMethodManagerService.mWindowManagerInternal.setDismissImeOnBackKeyPressed(
                false, (inputMethodManagerService.mImeWindowVis & 2) != 0);
        this.mCurMethod = null;
        this.mCurMethodUid = -1;
    }

    public final void onCreateInlineSuggestionsRequest(
            InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo,
            AutofillInlineSuggestionsRequestSession.InlineSuggestionsRequestCallbackImpl
                    inlineSuggestionsRequestCallbackImpl,
            boolean z) {
        AutofillSuggestionsController autofillSuggestionsController = this.mAutofillController;
        autofillSuggestionsController.mPendingInlineSuggestionsRequest = null;
        autofillSuggestionsController.mInlineSuggestionsRequestCallback =
                inlineSuggestionsRequestCallbackImpl;
        InputMethodBindingController inputMethodBindingController =
                autofillSuggestionsController.mBindingController;
        InputMethodInfo inputMethodInfo =
                InputMethodSettingsRepository.get(inputMethodBindingController.mUserId)
                        .mMethodMap
                        .get(inputMethodBindingController.mSelectedMethodId);
        if (inputMethodInfo == null
                || !inputMethodInfo.isInlineSuggestionsEnabled()
                || (z && !inputMethodInfo.supportsInlineSuggestionsWithTouchExploration())) {
            inlineSuggestionsRequestCallbackImpl.onInlineSuggestionsUnsupported();
            return;
        }
        autofillSuggestionsController.mPendingInlineSuggestionsRequest =
                new AutofillSuggestionsController.CreateInlineSuggestionsRequest(
                        inlineSuggestionsRequestInfo,
                        inlineSuggestionsRequestCallbackImpl,
                        inputMethodInfo.getPackageName());
        if (inputMethodBindingController.mCurMethod != null) {
            autofillSuggestionsController.performOnCreateInlineSuggestionsRequest();
        }
    }

    public final void unbindCurrentMethod() {
        ActivityManagerService$$ExternalSyntheticOutline0.m(
                10,
                new StringBuilder("unbindCurrentMethod: caller="),
                "InputMethodBindingController");
        if (this.mVisibleBound) {
            this.mContext.unbindService(this.mVisibleConnection);
            this.mVisibleBound = false;
        }
        if (this.mHasMainConnection) {
            this.mContext.unbindService(this.mMainConnection);
            this.mHasMainConnection = false;
        }
        if (this.mCurToken != null) {
            StringBuilder sb = new StringBuilder("Removing window token: ");
            sb.append(this.mCurToken);
            sb.append(" for display: ");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb, this.mCurTokenDisplayId, "InputMethodBindingController");
            this.mWindowManagerInternal.removeWindowToken(
                    this.mCurToken, true, false, this.mCurTokenDisplayId);
            this.mCurTokenDisplayId = -1;
            InputMethodManagerService inputMethodManagerService = this.mService;
            inputMethodManagerService.mImeWindowVis = 0;
            inputMethodManagerService.mBackDisposition = 0;
            inputMethodManagerService.updateSystemUiLocked(0, 0);
            this.mAutofillController.mCurHostInputToken = null;
            this.mCurToken = null;
        }
        this.mCurId = null;
        clearCurMethodAndSessions();
    }
}
