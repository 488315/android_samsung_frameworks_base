package android.view.inputmethod;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.PropertyInvalidatedCache;
import android.app.tvsettings.TvSettingsEnums;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.scontext.SContextConstants;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.Debug;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.Process;
import android.p009os.ResultReceiver;
import android.p009os.SystemProperties;
import android.p009os.Trace;
import android.p009os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.style.SuggestionSpan;
import android.util.Log;
import android.util.Pair;
import android.util.Pools;
import android.util.PrintWriterPrinter;
import android.util.Printer;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.ImeFocusController;
import android.view.ImeInsetsSourceConsumer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventSender;
import android.view.InsetsController$$ExternalSyntheticLambda2;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.window.ImeOnBackInvokedDispatcher;
import android.window.WindowOnBackInvokedDispatcher;
import com.android.internal.inputmethod.IInputMethodClient;
import com.android.internal.inputmethod.IInputMethodSession;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.inputmethod.InputBindResult;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry;
import com.android.internal.p029os.SomeArgs;
import com.android.internal.view.IInputMethodManager;
import com.samsung.android.ims.options.SemCapabilities;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/* loaded from: classes4.dex */
public final class InputMethodManager {
    private static final String CACHE_KEY_STYLUS_HANDWRITING_PROPERTY = "cache_key.system_server.stylus_handwriting";
    public static final long CLEAR_SHOW_FORCED_FLAG_WHEN_LEAVING = 214016041;
    public static final int DISPATCH_HANDLED = 1;
    public static final int DISPATCH_IN_PROGRESS = -1;
    public static final int DISPATCH_NOT_HANDLED = 0;
    public static final int HIDE_IMPLICIT_ONLY = 1;
    public static final int HIDE_NOT_ALWAYS = 2;
    private static final long INPUT_METHOD_NOT_RESPONDING_TIMEOUT = 2500;
    private static final int MSG_BIND = 2;
    private static final int MSG_BIND_ACCESSIBILITY_SERVICE = 11;
    private static final int MSG_DUMP = 1;
    private static final int MSG_FLUSH_INPUT_EVENT = 7;
    private static final int MSG_ON_SHOW_REQUESTED = 31;
    private static final int MSG_REPORT_FULLSCREEN_MODE = 10;
    private static final int MSG_SEND_INPUT_EVENT = 5;
    private static final int MSG_SET_ACTIVE = 4;
    private static final int MSG_SET_INTERACTIVE = 13;
    private static final int MSG_TIMEOUT_INPUT_EVENT = 6;
    private static final int MSG_UNBIND = 3;
    private static final int MSG_UNBIND_ACCESSIBILITY_SERVICE = 12;
    private static final int MSG_UPDATE_VIRTUAL_DISPLAY_TO_SCREEN_MATRIX = 30;
    private static final int NOT_A_SUBTYPE_ID = -1;
    private static final String PENDING_EVENT_COUNTER = "aq:imm";
    private static final int REQUEST_UPDATE_CURSOR_ANCHOR_INFO_NONE = 0;
    public static final int RESULT_HIDDEN = 3;
    public static final int RESULT_SHOWN = 2;
    public static final int RESULT_UNCHANGED_HIDDEN = 1;
    public static final int RESULT_UNCHANGED_SHOWN = 0;

    @Deprecated
    public static final int SHOW_FORCED = 2;
    public static final int SHOW_IMPLICIT = 1;
    public static final int SHOW_IM_PICKER_MODE_AUTO = 0;
    public static final int SHOW_IM_PICKER_MODE_EXCLUDE_AUXILIARY_SUBTYPES = 2;
    public static final int SHOW_IM_PICKER_MODE_INCLUDE_AUXILIARY_SUBTYPES = 1;
    private static final String SUBTYPE_MODE_VOICE = "voice";
    private static final String TAG = "InputMethodManager";
    static final String TAG_LIFE_CYCLE = "InputMethodManager_LC";

    @Deprecated
    static InputMethodManager sInstance;
    private static boolean sPreventImeStartupUnlessTextEditor;
    private CompletionInfo[] mCompletions;
    private BindState mCurBindState;
    private InputChannel mCurChannel;

    @Deprecated
    String mCurId;

    @Deprecated
    IInputMethodSession mCurMethod;
    ViewRootImpl mCurRootView;
    boolean mCurRootViewWindowFocused;
    private ImeInputEventSender mCurSender;
    private EditorInfo mCurrentEditorInfo;
    private int mCursorCandEnd;
    private int mCursorCandStart;
    private int mCursorSelEnd;
    private int mCursorSelStart;
    private final int mDisplayId;
    private final RemoteInputConnectionImpl mFallbackInputConnection;
    private boolean mFullscreenMode;

    /* renamed from: mH */
    final HandlerC3829H f584mH;
    private ImeInsetsSourceConsumer mImeInsetsConsumer;
    private int mInitialSelEnd;
    private int mInitialSelStart;
    private final Looper mMainLooper;
    private View mNextServedView;
    private ViewFocusParameterInfo mPreviousViewFocusParameters;
    private boolean mServedConnecting;
    private RemoteInputConnectionImpl mServedInputConnection;
    private Handler mServedInputConnectionHandler;
    private View mServedView;

    @Deprecated
    final IInputMethodManager mService;
    private PropertyInvalidatedCache<Integer, Boolean> mStylusHandwritingAvailableCache;
    private static final boolean DEBUG = Debug.semIsProductDev();
    private static final Object sLock = new Object();
    private static final SparseArray<InputMethodManager> sInstanceMap = new SparseArray<>();
    private static final boolean OPTIMIZE_NONEDITABLE_VIEWS = SystemProperties.getBoolean("debug.imm.optimize_noneditable_views", true);
    static final boolean DEBUG_SEP = Debug.semIsProductDev();
    private final ImeOnBackInvokedDispatcher mImeDispatcher = new ImeOnBackInvokedDispatcher(Handler.getMain()) { // from class: android.view.inputmethod.InputMethodManager.1
        @Override // android.window.ImeOnBackInvokedDispatcher
        public WindowOnBackInvokedDispatcher getReceivingDispatcher() {
            WindowOnBackInvokedDispatcher onBackInvokedDispatcher;
            synchronized (InputMethodManager.this.f584mH) {
                onBackInvokedDispatcher = InputMethodManager.this.mCurRootView != null ? InputMethodManager.this.mCurRootView.getOnBackInvokedDispatcher() : null;
            }
            return onBackInvokedDispatcher;
        }
    };
    private boolean mActive = false;
    private boolean mRestartOnNextWindowFocus = true;
    Rect mTmpCursorRect = new Rect();
    Rect mCursorRect = new Rect();
    private CursorAnchorInfo mCursorAnchorInfo = null;
    private Matrix mVirtualDisplayToScreenMatrix = null;
    private final SparseArray<IAccessibilityInputMethodSessionInvoker> mAccessibilityInputMethodSession = new SparseArray<>();

    @Deprecated
    private int mRequestUpdateCursorAnchorInfoMonitorMode = 0;
    private final Pools.Pool<PendingEvent> mPendingEventPool = new Pools.SimplePool(20);
    private final SparseArray<PendingEvent> mPendingEvents = new SparseArray<>(20);
    private final DelegateImpl mDelegate = new DelegateImpl();
    private final IInputMethodClient.Stub mClient = new IInputMethodClient.Stub() { // from class: android.view.inputmethod.InputMethodManager.2
        @Override // android.p009os.Binder
        protected void dump(FileDescriptor fd, PrintWriter fout, String[] args) {
            CountDownLatch latch = new CountDownLatch(1);
            SomeArgs sargs = SomeArgs.obtain();
            sargs.arg1 = fd;
            sargs.arg2 = fout;
            sargs.arg3 = args;
            sargs.arg4 = latch;
            InputMethodManager.this.f584mH.sendMessage(InputMethodManager.this.f584mH.obtainMessage(1, sargs));
            try {
                if (!latch.await(5L, TimeUnit.SECONDS)) {
                    fout.println("Timeout waiting for dump");
                }
            } catch (InterruptedException e) {
                fout.println("Interrupted waiting for dump");
            }
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onBindMethod(InputBindResult res) {
            InputMethodManager.this.f584mH.obtainMessage(2, res).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onBindAccessibilityService(InputBindResult res, int id) {
            InputMethodManager.this.f584mH.obtainMessage(11, id, 0, res).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onUnbindMethod(int sequence, int unbindReason) {
            InputMethodManager.this.f584mH.obtainMessage(3, sequence, unbindReason).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onUnbindAccessibilityService(int sequence, int id) {
            InputMethodManager.this.f584mH.obtainMessage(12, sequence, id).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setActive(boolean z, boolean z2) {
            InputMethodManager.this.f584mH.obtainMessage(4, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setInteractive(boolean z, boolean z2) {
            InputMethodManager.this.f584mH.obtainMessage(13, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void scheduleStartInputIfNecessary(boolean z) {
            InputMethodManager.this.f584mH.obtainMessage(4, 0, z ? 1 : 0).sendToTarget();
            InputMethodManager.this.f584mH.obtainMessage(4, 1, z ? 1 : 0).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void reportFullscreenMode(boolean z) {
            InputMethodManager.this.f584mH.obtainMessage(10, z ? 1 : 0, 0).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void updateVirtualDisplayToScreenMatrix(int bindSequence, float[] matrixValues) {
            InputMethodManager.this.f584mH.obtainMessage(30, bindSequence, 0, matrixValues).sendToTarget();
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setImeTraceEnabled(boolean enabled) {
            ImeTracing.getInstance().setEnabled(enabled);
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void throwExceptionFromSystem(String message) {
            throw new RuntimeException(message);
        }
    };
    final AtomicBoolean mRequestCursorUpdateDisplayIdCheck = new AtomicBoolean(true);

    public interface FinishedInputEventCallback {
        void onFinishedInputEvent(Object obj, boolean z);
    }

    public static void ensureDefaultInstanceForDefaultDisplayIfNecessary() {
        forContextInternal(0, Looper.getMainLooper());
    }

    public static void invalidateLocalStylusHandwritingAvailabilityCaches() {
        PropertyInvalidatedCache.invalidateCache(CACHE_KEY_STYLUS_HANDWRITING_PROPERTY);
    }

    static class Rune {
        static final boolean SUPPORT_SEP_TOGGLE_SOFT_INPUT = true;

        Rune() {
        }
    }

    private static boolean isAutofillUIShowing(View servedView) {
        AutofillManager afm = (AutofillManager) servedView.getContext().getSystemService(AutofillManager.class);
        return afm != null && afm.isAutofillUiShowing();
    }

    private InputMethodManager getFallbackInputMethodManagerIfNecessary(View view) {
        ViewRootImpl viewRootImpl;
        int viewRootDisplayId;
        if (view == null || (viewRootImpl = view.getViewRootImpl()) == null || (viewRootDisplayId = viewRootImpl.getDisplayId()) == this.mDisplayId) {
            return null;
        }
        InputMethodManager fallbackImm = (InputMethodManager) viewRootImpl.mContext.getSystemService(InputMethodManager.class);
        if (fallbackImm == null) {
            Log.m100v(TAG, "b/117267690: Failed to get non-null fallback IMM. view=" + view);
            return null;
        }
        if (fallbackImm.mDisplayId != viewRootDisplayId) {
            Log.m100v(TAG, "b/117267690: Failed to get fallback IMM with expected displayId=" + viewRootDisplayId + " actual IMM#displayId=" + fallbackImm.mDisplayId + " view=" + view);
            return null;
        }
        Log.m101v(TAG, "b/117267690: Display ID mismatch found. ViewRootImpl displayId=" + viewRootDisplayId + " InputMethodManager displayId=" + this.mDisplayId + ". Use the right InputMethodManager instance to avoid performance overhead.", new Throwable());
        return fallbackImm;
    }

    Context getFallbackContextFromServedView() {
        synchronized (this.f584mH) {
            if (this.mCurRootView == null) {
                return null;
            }
            View view = this.mServedView;
            return view != null ? view.getContext() : null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean canStartInput(View servedView) {
        return servedView.hasWindowFocus() || isAutofillUIShowing(servedView);
    }

    public void reportPerceptible(IBinder windowToken, boolean perceptible) {
        IInputMethodManagerGlobalInvoker.reportPerceptibleAsync(windowToken, perceptible);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final class DelegateImpl implements ImeFocusController.InputMethodManagerDelegate {
        private DelegateImpl() {
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onPreWindowGainedFocus(ViewRootImpl viewRootImpl) {
            synchronized (InputMethodManager.this.f584mH) {
                setCurrentRootViewLocked(viewRootImpl);
                InputMethodManager.this.mCurRootViewWindowFocused = true;
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onPostWindowGainedFocus(View viewForWindowFocus, WindowManager.LayoutParams windowAttribute) {
            boolean forceFocus;
            boolean forceFocus2 = false;
            synchronized (InputMethodManager.this.f584mH) {
                boolean z = true;
                InputMethodManager.this.onViewFocusChangedInternal(viewForWindowFocus, true);
                if (InputMethodManager.this.mServedView != viewForWindowFocus) {
                    z = false;
                }
                boolean nextFocusIsServedView = z;
                if (nextFocusIsServedView && !InputMethodManager.this.hasActiveInputConnectionInternal(viewForWindowFocus)) {
                    forceFocus2 = true;
                }
            }
            int softInputMode = windowAttribute.softInputMode;
            int windowFlags = windowAttribute.flags;
            int startInputFlags = InputMethodManager.this.getStartInputFlags(viewForWindowFocus, 0) | 8;
            ImeTracing.getInstance().triggerClientDump("InputMethodManager.DelegateImpl#startInputAsyncOnWindowFocusGain", InputMethodManager.this, null);
            synchronized (InputMethodManager.this.f584mH) {
                try {
                    try {
                        if (InputMethodManager.this.mCurRootView == null) {
                            return;
                        }
                        if (!InputMethodManager.this.mRestartOnNextWindowFocus) {
                            forceFocus = forceFocus2;
                        } else {
                            if (InputMethodManager.DEBUG) {
                                Log.m100v(InputMethodManager.TAG, "Restarting due to mRestartOnNextWindowFocus as true");
                            }
                            InputMethodManager.this.mRestartOnNextWindowFocus = false;
                            forceFocus = true;
                        }
                        try {
                            InputMethodManager inputMethodManager = InputMethodManager.this;
                            boolean checkFocusResult = inputMethodManager.checkFocusInternalLocked(forceFocus, inputMethodManager.mCurRootView);
                            if (checkFocusResult && InputMethodManager.this.startInputOnWindowFocusGainInternal(1, viewForWindowFocus, startInputFlags, softInputMode, windowFlags)) {
                                return;
                            }
                            synchronized (InputMethodManager.this.f584mH) {
                                try {
                                    try {
                                        if (InputMethodManager.DEBUG) {
                                            try {
                                                Log.m100v(InputMethodManager.TAG, "Reporting focus gain, without startInput");
                                            } catch (Throwable th) {
                                                th = th;
                                                throw th;
                                            }
                                        }
                                        Trace.traceBegin(32L, "IMM.startInputOrWindowGainedFocus");
                                        Log.m98i(InputMethodManager.TAG, "startInputAsyncOnWindowFocusGain - IInputMethodManagerGlobalInvoker.startInputOrWindowGainedFocus");
                                        IInputMethodManagerGlobalInvoker.startInputOrWindowGainedFocus(2, InputMethodManager.this.mClient, viewForWindowFocus.getWindowToken(), startInputFlags, softInputMode, windowFlags, null, null, null, InputMethodManager.this.mCurRootView.mContext.getApplicationInfo().targetSdkVersion, UserHandle.myUserId(), InputMethodManager.this.mImeDispatcher);
                                        Trace.traceEnd(32L);
                                    } catch (Throwable th2) {
                                        th = th2;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                }
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            while (true) {
                                try {
                                    throw th;
                                } catch (Throwable th5) {
                                    th = th5;
                                }
                            }
                        }
                    } catch (Throwable th6) {
                        th = th6;
                    }
                } catch (Throwable th7) {
                    th = th7;
                }
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onWindowLostFocus(ViewRootImpl viewRootImpl) {
            synchronized (InputMethodManager.this.f584mH) {
                if (InputMethodManager.this.mCurRootView == viewRootImpl) {
                    InputMethodManager.this.mCurRootViewWindowFocused = false;
                    InputMethodManager.this.clearCurRootViewIfNeeded();
                }
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onViewFocusChanged(View view, boolean hasFocus) {
            InputMethodManager.this.onViewFocusChangedInternal(view, hasFocus);
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onScheduledCheckFocus(ViewRootImpl viewRootImpl) {
            synchronized (InputMethodManager.this.f584mH) {
                if (InputMethodManager.this.checkFocusInternalLocked(false, viewRootImpl)) {
                    InputMethodManager.this.startInputOnWindowFocusGainInternal(3, null, 0, 0, 0);
                }
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onViewDetachedFromWindow(View view, ViewRootImpl viewRootImpl) {
            synchronized (InputMethodManager.this.f584mH) {
                if (InputMethodManager.this.mCurRootView != view.getViewRootImpl()) {
                    return;
                }
                if (InputMethodManager.this.mNextServedView == view) {
                    InputMethodManager.this.mNextServedView = null;
                }
                if (InputMethodManager.this.mServedView == view) {
                    viewRootImpl.dispatchCheckFocus();
                }
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onWindowDismissed(ViewRootImpl viewRootImpl) {
            synchronized (InputMethodManager.this.f584mH) {
                if (InputMethodManager.this.mCurRootView != viewRootImpl) {
                    return;
                }
                if (InputMethodManager.this.mServedView != null) {
                    if (InputMethodManager.DEBUG_SEP) {
                        Log.m98i(InputMethodManager.TAG, "onWindowDismissed");
                    }
                    InputMethodManager.this.finishInputLocked();
                }
                setCurrentRootViewLocked(null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCurrentRootViewLocked(ViewRootImpl rootView) {
            InputMethodManager.this.mImeDispatcher.switchRootView(InputMethodManager.this.mCurRootView, rootView);
            InputMethodManager.this.mCurRootView = rootView;
        }
    }

    public DelegateImpl getDelegate() {
        return this.mDelegate;
    }

    public boolean hasActiveInputConnection(View view) {
        boolean z;
        RemoteInputConnectionImpl remoteInputConnectionImpl;
        synchronized (this.f584mH) {
            z = this.mCurRootView != null && view != null && this.mServedView == view && (remoteInputConnectionImpl = this.mServedInputConnection) != null && remoteInputConnectionImpl.isAssociatedWith(view) && isImeSessionAvailableLocked();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasActiveInputConnectionInternal(View view) {
        synchronized (this.f584mH) {
            boolean z = false;
            if (hasServedByInputMethodLocked(view) && isImeSessionAvailableLocked()) {
                RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
                if (remoteInputConnectionImpl != null && remoteInputConnectionImpl.isAssociatedWith(view)) {
                    z = true;
                }
                return z;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean startInputOnWindowFocusGainInternal(int startInputReason, View focusedView, int startInputFlags, int softInputMode, int windowFlags) {
        synchronized (this.f584mH) {
            this.mCurrentEditorInfo = null;
            this.mCompletions = null;
            this.mServedConnecting = true;
        }
        return startInputInner(startInputReason, focusedView != null ? focusedView.getWindowToken() : null, startInputFlags, softInputMode, windowFlags);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getServedViewLocked() {
        if (this.mCurRootView != null) {
            return this.mServedView;
        }
        return null;
    }

    private View getNextServedViewLocked() {
        if (this.mCurRootView != null) {
            return this.mNextServedView;
        }
        return null;
    }

    private boolean hasServedByInputMethodLocked(View view) {
        View servedView = getServedViewLocked();
        return servedView == view || (servedView != null && servedView.checkInputConnectionProxy(view));
    }

    /* renamed from: android.view.inputmethod.InputMethodManager$H */
    class HandlerC3829H extends Handler {
        HandlerC3829H(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            IAccessibilityInputMethodSessionInvoker invoker;
            switch (msg.what) {
                case 1:
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        InputMethodManager.this.doDump((FileDescriptor) args.arg1, (PrintWriter) args.arg2, (String[]) args.arg3);
                    } catch (RuntimeException e) {
                        ((PrintWriter) args.arg2).println("Exception: " + e);
                    }
                    synchronized (args.arg4) {
                        ((CountDownLatch) args.arg4).countDown();
                    }
                    args.recycle();
                    return;
                case 2:
                    InputBindResult res = (InputBindResult) msg.obj;
                    if (InputMethodManager.DEBUG) {
                        Log.m98i(InputMethodManager.TAG, "handleMessage: MSG_BIND " + res.sequence + "," + res.f684id);
                    }
                    synchronized (InputMethodManager.this.f584mH) {
                        int curBindSequence = InputMethodManager.this.getBindSequenceLocked();
                        if (curBindSequence >= 0 && curBindSequence == res.sequence) {
                            InputMethodManager.this.mRequestUpdateCursorAnchorInfoMonitorMode = 0;
                            InputMethodManager.this.updateInputChannelLocked(res.channel);
                            InputMethodManager.this.mCurMethod = res.method;
                            InputMethodManager.this.mCurBindState = new BindState(res);
                            InputMethodManager.this.mCurId = res.f684id;
                            InputMethodManager.this.mVirtualDisplayToScreenMatrix = res.getVirtualDisplayToScreenMatrix();
                            InputMethodManager.this.startInputInner(6, null, 0, 0, 0);
                            return;
                        }
                        Log.m102w(InputMethodManager.TAG, "Ignoring onBind: cur seq=" + curBindSequence + ", given seq=" + res.sequence);
                        if (res.channel != null && res.channel != InputMethodManager.this.mCurChannel) {
                            res.channel.dispose();
                        }
                        return;
                    }
                case 3:
                    int sequence = msg.arg1;
                    int reason = msg.arg2;
                    if (InputMethodManager.DEBUG) {
                        Log.m98i(InputMethodManager.TAG, "handleMessage: MSG_UNBIND " + sequence + " reason=" + InputMethodDebug.unbindReasonToString(reason));
                    }
                    synchronized (InputMethodManager.this.f584mH) {
                        if (InputMethodManager.this.getBindSequenceLocked() != sequence) {
                            return;
                        }
                        InputMethodManager.this.clearAllAccessibilityBindingLocked();
                        InputMethodManager.this.clearBindingLocked();
                        View servedView = InputMethodManager.this.getServedViewLocked();
                        if (servedView != null && servedView.isFocused()) {
                            InputMethodManager.this.mServedConnecting = true;
                        }
                        boolean startInput = InputMethodManager.this.mActive;
                        if (startInput) {
                            if (InputMethodManager.DEBUG_SEP) {
                                Log.m102w(InputMethodManager.TAG_LIFE_CYCLE, "MSG_UNBIND: startInputInner is called with null IBinder ");
                            }
                            InputMethodManager.this.startInputInner(7, null, 0, 0, 0);
                            return;
                        }
                        return;
                    }
                case 4:
                    boolean active = msg.arg1 != 0;
                    boolean fullscreen = msg.arg2 != 0;
                    if (InputMethodManager.DEBUG) {
                        Log.m98i(InputMethodManager.TAG, "handleMessage: MSG_SET_ACTIVE " + active + ", was " + InputMethodManager.this.mActive);
                    }
                    synchronized (InputMethodManager.this.f584mH) {
                        InputMethodManager.this.mActive = active;
                        InputMethodManager.this.mFullscreenMode = fullscreen;
                        if (!active) {
                            InputMethodManager.this.mRestartOnNextWindowFocus = true;
                            InputMethodManager.this.mFallbackInputConnection.finishComposingTextFromImm();
                            if (InputMethodManager.this.clearCurRootViewIfNeeded()) {
                                return;
                            }
                        }
                        View servedView2 = InputMethodManager.this.getServedViewLocked();
                        if (servedView2 != null && InputMethodManager.canStartInput(servedView2)) {
                            if (InputMethodManager.this.mCurRootView == null) {
                                return;
                            }
                            InputMethodManager inputMethodManager = InputMethodManager.this;
                            if (inputMethodManager.checkFocusInternalLocked(inputMethodManager.mRestartOnNextWindowFocus, InputMethodManager.this.mCurRootView)) {
                                InputMethodManager.this.mCurrentEditorInfo = null;
                                InputMethodManager.this.mCompletions = null;
                                InputMethodManager.this.mServedConnecting = true;
                                int reason2 = active ? 8 : 9;
                                InputMethodManager.this.startInputInner(reason2, null, 0, 0, 0);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                case 5:
                    InputMethodManager.this.sendInputEventAndReportResultOnMainLooper((PendingEvent) msg.obj);
                    return;
                case 6:
                    InputMethodManager.this.finishedInputEvent(msg.arg1, false, true);
                    return;
                case 7:
                    InputMethodManager.this.finishedInputEvent(msg.arg1, false, false);
                    return;
                case 10:
                    boolean fullscreen2 = msg.arg1 != 0;
                    RemoteInputConnectionImpl ic = null;
                    synchronized (InputMethodManager.this.f584mH) {
                        if (InputMethodManager.this.mFullscreenMode != fullscreen2 && InputMethodManager.this.mServedInputConnection != null) {
                            ic = InputMethodManager.this.mServedInputConnection;
                            InputMethodManager.this.mFullscreenMode = fullscreen2;
                        }
                    }
                    if (ic != null) {
                        ic.dispatchReportFullscreenMode(fullscreen2);
                        return;
                    }
                    return;
                case 11:
                    int sequence2 = msg.arg1;
                    InputBindResult res2 = (InputBindResult) msg.obj;
                    if (InputMethodManager.DEBUG) {
                        Log.m98i(InputMethodManager.TAG, "handleMessage: MSG_BIND_ACCESSIBILITY " + res2.sequence + "," + res2.f684id);
                    }
                    synchronized (InputMethodManager.this.f584mH) {
                        int curBindSequence2 = InputMethodManager.this.getBindSequenceLocked();
                        if (curBindSequence2 >= 0 && curBindSequence2 == res2.sequence) {
                            if (res2.accessibilitySessions != null && (invoker = IAccessibilityInputMethodSessionInvoker.createOrNull(res2.accessibilitySessions.get(sequence2))) != null) {
                                InputMethodManager.this.mAccessibilityInputMethodSession.put(sequence2, invoker);
                                if (InputMethodManager.this.mServedInputConnection != null) {
                                    invoker.updateSelection(InputMethodManager.this.mInitialSelStart, InputMethodManager.this.mInitialSelEnd, InputMethodManager.this.mCursorSelStart, InputMethodManager.this.mCursorSelEnd, InputMethodManager.this.mCursorCandStart, InputMethodManager.this.mCursorCandEnd);
                                } else {
                                    invoker.updateSelection(-1, -1, -1, -1, -1, -1);
                                }
                            }
                            InputMethodManager.this.startInputInner(12, null, 0, 0, 0);
                            return;
                        }
                        Log.m102w(InputMethodManager.TAG, "Ignoring onBind: cur seq=" + curBindSequence2 + ", given seq=" + res2.sequence);
                        if (res2.channel != null && res2.channel != InputMethodManager.this.mCurChannel) {
                            res2.channel.dispose();
                        }
                        return;
                    }
                case 12:
                    int sequence3 = msg.arg1;
                    int id = msg.arg2;
                    if (InputMethodManager.DEBUG) {
                        Log.m98i(InputMethodManager.TAG, "handleMessage: MSG_UNBIND_ACCESSIBILITY_SERVICE " + sequence3 + " id=" + id);
                    }
                    synchronized (InputMethodManager.this.f584mH) {
                        if (InputMethodManager.this.getBindSequenceLocked() != sequence3) {
                            if (InputMethodManager.DEBUG) {
                                Log.m98i(InputMethodManager.TAG, "current BindSequence =" + InputMethodManager.this.getBindSequenceLocked() + " sequence =" + sequence3 + " id=" + id);
                            }
                            return;
                        } else {
                            InputMethodManager.this.clearAccessibilityBindingLocked(id);
                            return;
                        }
                    }
                case 13:
                    boolean interactive = msg.arg1 != 0;
                    boolean fullscreen3 = msg.arg2 != 0;
                    if (InputMethodManager.DEBUG) {
                        Log.m98i(InputMethodManager.TAG, "handleMessage: MSG_SET_INTERACTIVE " + interactive + ", was " + InputMethodManager.this.mActive);
                    }
                    synchronized (InputMethodManager.this.f584mH) {
                        InputMethodManager.this.mActive = interactive;
                        InputMethodManager.this.mFullscreenMode = fullscreen3;
                        if (interactive) {
                            View rootView = InputMethodManager.this.mCurRootView != null ? InputMethodManager.this.mCurRootView.getView() : null;
                            if (rootView == null) {
                                return;
                            }
                            final ViewRootImpl currentViewRootImpl = InputMethodManager.this.mCurRootView;
                            rootView.post(new Runnable() { // from class: android.view.inputmethod.InputMethodManager$H$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    InputMethodManager.HandlerC3829H.this.lambda$handleMessage$0(currentViewRootImpl);
                                }
                            });
                        } else {
                            InputMethodManager.this.finishInputLocked();
                            if (InputMethodManager.this.isImeSessionAvailableLocked()) {
                                InputMethodManager.this.mCurBindState.mImeSession.finishInput();
                            }
                            InputMethodManager.this.forAccessibilitySessionsLocked(new Consumer() { // from class: android.view.inputmethod.InputMethodManager$H$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    ((IAccessibilityInputMethodSessionInvoker) obj).finishInput();
                                }
                            });
                        }
                        return;
                    }
                case 30:
                    float[] matrixValues = (float[]) msg.obj;
                    int bindSequence = msg.arg1;
                    synchronized (InputMethodManager.this.f584mH) {
                        if (InputMethodManager.this.getBindSequenceLocked() != bindSequence) {
                            return;
                        }
                        if (matrixValues != null && InputMethodManager.this.mVirtualDisplayToScreenMatrix != null) {
                            float[] currentValues = new float[9];
                            InputMethodManager.this.mVirtualDisplayToScreenMatrix.getValues(currentValues);
                            if (Arrays.equals(currentValues, matrixValues)) {
                                return;
                            }
                            InputMethodManager.this.mVirtualDisplayToScreenMatrix.setValues(matrixValues);
                            if (InputMethodManager.this.mCursorAnchorInfo != null && InputMethodManager.this.isImeSessionAvailableLocked() && InputMethodManager.this.mServedInputConnection != null) {
                                if (InputMethodManager.this.mServedInputConnection.isCursorAnchorInfoMonitoring()) {
                                    InputMethodManager.this.mCurBindState.mImeSession.updateCursorAnchorInfo(CursorAnchorInfo.createForAdditionalParentMatrix(InputMethodManager.this.mCursorAnchorInfo, InputMethodManager.this.mVirtualDisplayToScreenMatrix));
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        InputMethodManager.this.mVirtualDisplayToScreenMatrix = null;
                        return;
                    }
                case 31:
                    synchronized (InputMethodManager.this.f584mH) {
                        if (InputMethodManager.this.mImeInsetsConsumer != null) {
                            InputMethodManager.this.mImeInsetsConsumer.onShowRequested();
                        }
                    }
                    return;
                default:
                    return;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(ViewRootImpl currentViewRootImpl) {
            synchronized (InputMethodManager.this.f584mH) {
                if (InputMethodManager.this.mCurRootView != currentViewRootImpl) {
                    return;
                }
                if (currentViewRootImpl.getView() == null) {
                    return;
                }
                View focusedView = currentViewRootImpl.getView().findFocus();
                InputMethodManager.this.onViewFocusChangedInternal(focusedView, focusedView != null);
            }
        }
    }

    static void tearDownEditMode() {
        if (!isInEditMode()) {
            throw new UnsupportedOperationException("This method must be called only from layoutlib");
        }
        synchronized (sLock) {
            sInstance = null;
        }
    }

    private static boolean isInEditMode() {
        return false;
    }

    static boolean isInEditModeInternal() {
        return isInEditMode();
    }

    private static InputMethodManager createInstance(int displayId, Looper looper) {
        return isInEditMode() ? createStubInstance(displayId, looper) : createRealInstance(displayId, looper);
    }

    private static InputMethodManager createRealInstance(int displayId, Looper looper) {
        IInputMethodManager service = IInputMethodManagerGlobalInvoker.getService();
        if (service == null) {
            throw new IllegalStateException("IInputMethodManager is not available");
        }
        InputMethodManager imm = new InputMethodManager(service, displayId, looper);
        long identity = Binder.clearCallingIdentity();
        try {
            IInputMethodManagerGlobalInvoker.addClient(imm.mClient, imm.mFallbackInputConnection, displayId);
            return imm;
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    private static InputMethodManager createStubInstance(int displayId, Looper looper) {
        IInputMethodManager stubInterface = (IInputMethodManager) Proxy.newProxyInstance(IInputMethodManager.class.getClassLoader(), new Class[]{IInputMethodManager.class}, new InvocationHandler() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda1
            @Override // java.lang.reflect.InvocationHandler
            public final Object invoke(Object obj, Method method, Object[] objArr) {
                return InputMethodManager.lambda$createStubInstance$0(obj, method, objArr);
            }
        });
        return new InputMethodManager(stubInterface, displayId, looper);
    }

    static /* synthetic */ Object lambda$createStubInstance$0(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        if (returnType == Boolean.TYPE) {
            return false;
        }
        if (returnType == Integer.TYPE) {
            return 0;
        }
        if (returnType == Long.TYPE) {
            return 0L;
        }
        if (returnType == Short.TYPE || returnType == Character.TYPE || returnType == Byte.TYPE) {
            return 0;
        }
        if (returnType == Float.TYPE) {
            return Float.valueOf(0.0f);
        }
        if (returnType == Double.TYPE) {
            return Double.valueOf(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
        }
        return null;
    }

    private InputMethodManager(IInputMethodManager service, int displayId, Looper looper) {
        this.mService = service;
        this.mMainLooper = looper;
        this.f584mH = new HandlerC3829H(looper);
        this.mDisplayId = displayId;
        this.mFallbackInputConnection = new RemoteInputConnectionImpl(looper, new BaseInputConnection(this, false), this, null);
    }

    public static InputMethodManager forContext(Context context) {
        int displayId = context.getDisplayId();
        Looper looper = displayId == 0 ? Looper.getMainLooper() : context.getMainLooper();
        sPreventImeStartupUnlessTextEditor = context.getResources().getBoolean(17891335);
        return forContextInternal(displayId, looper);
    }

    private static InputMethodManager forContextInternal(int displayId, Looper looper) {
        boolean isDefaultDisplay = displayId == 0;
        synchronized (sLock) {
            SparseArray<InputMethodManager> sparseArray = sInstanceMap;
            InputMethodManager instance = sparseArray.get(displayId);
            if (instance != null) {
                return instance;
            }
            InputMethodManager instance2 = createInstance(displayId, looper);
            if (sInstance == null && isDefaultDisplay) {
                sInstance = instance2;
            }
            sparseArray.put(displayId, instance2);
            return instance2;
        }
    }

    @Deprecated
    public static InputMethodManager getInstance() {
        Log.m103w(TAG, "InputMethodManager.getInstance() is deprecated because it cannot be compatible with multi-display. Use context.getSystemService(InputMethodManager.class) instead.", new Throwable());
        ensureDefaultInstanceForDefaultDisplayIfNecessary();
        return peekInstance();
    }

    @Deprecated
    public static InputMethodManager peekInstance() {
        InputMethodManager inputMethodManager;
        Log.m103w(TAG, "InputMethodManager.peekInstance() is deprecated because it cannot be compatible with multi-display. Use context.getSystemService(InputMethodManager.class) instead.", new Throwable());
        synchronized (sLock) {
            inputMethodManager = sInstance;
        }
        return inputMethodManager;
    }

    public List<InputMethodInfo> getInputMethodList() {
        return IInputMethodManagerGlobalInvoker.getInputMethodList(UserHandle.myUserId(), 0);
    }

    public boolean isStylusHandwritingAvailable() {
        return isStylusHandwritingAvailableAsUser(UserHandle.myUserId());
    }

    public boolean isStylusHandwritingAvailableAsUser(int userId) {
        boolean isAvailable;
        Context fallbackContext = ActivityThread.currentApplication();
        if (fallbackContext == null) {
            return false;
        }
        synchronized (this.f584mH) {
            if (this.mStylusHandwritingAvailableCache == null) {
                this.mStylusHandwritingAvailableCache = new PropertyInvalidatedCache<Integer, Boolean>(4, CACHE_KEY_STYLUS_HANDWRITING_PROPERTY) { // from class: android.view.inputmethod.InputMethodManager.3
                    @Override // android.app.PropertyInvalidatedCache
                    public Boolean recompute(Integer userId2) {
                        return Boolean.valueOf(IInputMethodManagerGlobalInvoker.isStylusHandwritingAvailableAsUser(userId2.intValue()));
                    }
                };
            }
            isAvailable = this.mStylusHandwritingAvailableCache.query(Integer.valueOf(userId)).booleanValue();
        }
        return isAvailable;
    }

    public List<InputMethodInfo> getInputMethodListAsUser(int userId) {
        return IInputMethodManagerGlobalInvoker.getInputMethodList(userId, 0);
    }

    public List<InputMethodInfo> getInputMethodListAsUser(int userId, int directBootAwareness) {
        return IInputMethodManagerGlobalInvoker.getInputMethodList(userId, directBootAwareness);
    }

    public InputMethodInfo getCurrentInputMethodInfo() {
        return IInputMethodManagerGlobalInvoker.getCurrentInputMethodInfoAsUser(UserHandle.myUserId());
    }

    @SystemApi
    public InputMethodInfo getCurrentInputMethodInfoAsUser(UserHandle user) {
        Objects.requireNonNull(user);
        return IInputMethodManagerGlobalInvoker.getCurrentInputMethodInfoAsUser(user.getIdentifier());
    }

    public List<InputMethodInfo> getEnabledInputMethodList() {
        return IInputMethodManagerGlobalInvoker.getEnabledInputMethodList(UserHandle.myUserId());
    }

    public List<InputMethodInfo> getEnabledInputMethodListAsUser(int userId) {
        return IInputMethodManagerGlobalInvoker.getEnabledInputMethodList(userId);
    }

    public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(InputMethodInfo imi, boolean allowsImplicitlyEnabledSubtypes) {
        return IInputMethodManagerGlobalInvoker.getEnabledInputMethodSubtypeList(imi == null ? null : imi.getId(), allowsImplicitlyEnabledSubtypes, UserHandle.myUserId());
    }

    public List<InputMethodSubtype> getEnabledInputMethodSubtypeListAsUser(String imeId, boolean allowsImplicitlyEnabledSubtypes, int userId) {
        return IInputMethodManagerGlobalInvoker.getEnabledInputMethodSubtypeList((String) Objects.requireNonNull(imeId), allowsImplicitlyEnabledSubtypes, userId);
    }

    @Deprecated
    public void showStatusIcon(IBinder imeToken, String packageName, int iconId) {
        InputMethodPrivilegedOperationsRegistry.get(imeToken).updateStatusIconAsync(packageName, iconId);
    }

    @Deprecated
    public void hideStatusIcon(IBinder imeToken) {
        InputMethodPrivilegedOperationsRegistry.get(imeToken).updateStatusIconAsync(null, 0);
    }

    @Deprecated
    public void registerSuggestionSpansForNotification(SuggestionSpan[] spans) {
        Log.m102w(TAG, "registerSuggestionSpansForNotification() is deprecated.  Does nothing.");
    }

    @Deprecated
    public void notifySuggestionPicked(SuggestionSpan span, String originalString, int index) {
        Log.m102w(TAG, "notifySuggestionPicked() is deprecated.  Does nothing.");
    }

    public boolean isFullscreenMode() {
        boolean z;
        synchronized (this.f584mH) {
            z = this.mFullscreenMode;
        }
        return z;
    }

    public boolean isActive(View view) {
        boolean z;
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            return fallbackImm.isActive(view);
        }
        checkFocus();
        synchronized (this.f584mH) {
            z = hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null;
        }
        return z;
    }

    public boolean isActive() {
        boolean z;
        checkFocus();
        synchronized (this.f584mH) {
            z = (getServedViewLocked() == null || this.mCurrentEditorInfo == null) ? false : true;
        }
        return z;
    }

    public boolean isCurrentRootView(View attachedView) {
        boolean z;
        synchronized (this.f584mH) {
            z = this.mCurRootView == attachedView.getViewRootImpl();
        }
        return z;
    }

    public boolean isAcceptingText() {
        boolean z;
        checkFocus();
        synchronized (this.f584mH) {
            z = this.mServedInputConnection != null;
        }
        return z;
    }

    public boolean isInputMethodSuppressingSpellChecker() {
        boolean z;
        synchronized (this.f584mH) {
            BindState bindState = this.mCurBindState;
            z = bindState != null && bindState.mIsInputMethodSuppressingSpellChecker;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearBindingLocked() {
        if (DEBUG) {
            Log.m100v(TAG, "Clearing binding!");
        }
        clearConnectionLocked();
        updateInputChannelLocked(null);
        this.mCurId = null;
        this.mCurMethod = null;
        this.mCurBindState = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAccessibilityBindingLocked(int id) {
        if (DEBUG) {
            Log.m100v(TAG, "Clearing accessibility binding " + id);
        }
        this.mAccessibilityInputMethodSession.remove(id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAllAccessibilityBindingLocked() {
        if (DEBUG) {
            Log.m100v(TAG, "Clearing all accessibility bindings");
        }
        this.mAccessibilityInputMethodSession.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInputChannelLocked(InputChannel channel) {
        if (areSameInputChannel(this.mCurChannel, channel)) {
            return;
        }
        if (this.mCurSender != null) {
            flushPendingEventsLocked();
            this.mCurSender.dispose();
            this.mCurSender = null;
        }
        InputChannel inputChannel = this.mCurChannel;
        if (inputChannel != null) {
            inputChannel.dispose();
        }
        this.mCurChannel = channel;
    }

    private static boolean areSameInputChannel(InputChannel lhs, InputChannel rhs) {
        if (lhs == rhs) {
            return true;
        }
        if (lhs != null && rhs != null && lhs.getToken() == rhs.getToken()) {
            return true;
        }
        return false;
    }

    private void clearConnectionLocked() {
        this.mCurrentEditorInfo = null;
        this.mPreviousViewFocusParameters = null;
        RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
        if (remoteInputConnectionImpl != null) {
            remoteInputConnectionImpl.deactivate();
            this.mServedInputConnection = null;
            this.mServedInputConnectionHandler = null;
        }
    }

    void finishInputLocked() {
        this.mVirtualDisplayToScreenMatrix = null;
        View clearedView = null;
        this.mNextServedView = null;
        if (this.mServedView != null) {
            clearedView = this.mServedView;
            this.mServedView = null;
        }
        if (clearedView != null) {
            if (DEBUG) {
                Log.m100v(TAG, "FINISH INPUT: mServedView=" + InputMethodDebug.dumpViewInfo(clearedView));
            }
            this.mCompletions = null;
            this.mServedConnecting = false;
            clearConnectionLocked();
            updateIMESwitchEnable();
        }
        this.mImeDispatcher.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean clearCurRootViewIfNeeded() {
        if (!this.mActive && !this.mCurRootViewWindowFocused) {
            finishInputLocked();
            this.mDelegate.setCurrentRootViewLocked(null);
            return true;
        }
        return false;
    }

    public void displayCompletions(View view, CompletionInfo[] completions) {
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            fallbackImm.displayCompletions(view, completions);
            return;
        }
        checkFocus();
        synchronized (this.f584mH) {
            if (hasServedByInputMethodLocked(view)) {
                this.mCompletions = completions;
                if (isImeSessionAvailableLocked()) {
                    this.mCurBindState.mImeSession.displayCompletions(this.mCompletions);
                }
            }
        }
    }

    public void updateExtractedText(View view, int token, ExtractedText text) {
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            fallbackImm.updateExtractedText(view, token, text);
            return;
        }
        checkFocus();
        synchronized (this.f584mH) {
            if (hasServedByInputMethodLocked(view)) {
                if (isImeSessionAvailableLocked()) {
                    if (this.mFullscreenMode) {
                        this.mCurBindState.mImeSession.updateExtractedTextSync(token, text);
                        return;
                    }
                    this.mCurBindState.mImeSession.updateExtractedText(token, text);
                }
            }
        }
    }

    public boolean showSoftInput(View view, int flags) {
        Log.m98i(TAG_LIFE_CYCLE, "showSoftInput(View,I)");
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            return fallbackImm.showSoftInput(view, flags);
        }
        return showSoftInput(view, flags, null);
    }

    public boolean showSoftInput(View view, int flags, ResultReceiver resultReceiver) {
        return showSoftInput(view, null, flags, resultReceiver, 1);
    }

    private boolean showSoftInput(View view, ImeTracker.Token statsToken, int flags, ResultReceiver resultReceiver, int reason) {
        if (statsToken == null) {
            statsToken = ImeTracker.forLogging().onRequestShow(null, Process.myUid(), 1, reason);
        }
        ImeTracker.forLatency().onRequestShow(statsToken, 1, reason, new InsetsController$$ExternalSyntheticLambda2());
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#showSoftInput", this, null);
        Log.m98i(TAG_LIFE_CYCLE, "ssi(): flags=" + flags + " view=" + (view != null ? view.getContext().getPackageName() : 0) + " reason = " + InputMethodDebug.softInputDisplayReasonToString(reason));
        if (!(view instanceof EditText)) {
            Log.m98i(TAG_LIFE_CYCLE, "ssi() view is not EditText");
        }
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.m94d(TAG, "showSoftInput callers=" + Debug.getCallers(10));
        }
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            return fallbackImm.showSoftInput(view, flags, resultReceiver);
        }
        checkFocus();
        synchronized (this.f584mH) {
            if (!hasServedByInputMethodLocked(view)) {
                ImeTracker.forLogging().onFailed(statsToken, 1);
                ImeTracker.forLatency().onShowFailed(statsToken, 1, new InsetsController$$ExternalSyntheticLambda2());
                Log.m102w(TAG, "Ignoring showSoftInput() as view=" + view + " is not served.");
                View servedView = getServedViewLocked();
                Log.m102w(TAG_LIFE_CYCLE, "ssi(): Ignoring showSoftInput() servedView=" + InputMethodDebug.dumpViewInfo(servedView) + " mDisplayId=" + this.mDisplayId + " view=" + InputMethodDebug.dumpViewInfo(view));
                return false;
            }
            ImeTracker.forLogging().onProgress(statsToken, 1);
            HandlerC3829H handlerC3829H = this.f584mH;
            handlerC3829H.executeOrSendMessage(Message.obtain(handlerC3829H, 31));
            Log.m94d(TAG, "showSoftInput() view=" + view + " flags=" + flags + " reason=" + InputMethodDebug.softInputDisplayReasonToString(reason));
            return IInputMethodManagerGlobalInvoker.showSoftInput(this.mClient, view.getWindowToken(), statsToken, flags, this.mCurRootView.getLastClickToolType(), resultReceiver, reason);
        }
    }

    @Deprecated
    public void showSoftInputUnchecked(int flags, ResultReceiver resultReceiver) {
        synchronized (this.f584mH) {
            ImeTracker.Token statsToken = ImeTracker.forLogging().onRequestShow(null, Process.myUid(), 1, 1);
            Log.m102w(TAG, "showSoftInputUnchecked() is a hidden method, which will be removed soon. If you are using androidx.appcompat.widget.SearchView, please update to version 26.0 or newer version.");
            ViewRootImpl viewRootImpl = this.mCurRootView;
            if (viewRootImpl != null && viewRootImpl.getView() != null) {
                ImeTracker.forLogging().onProgress(statsToken, 1);
                HandlerC3829H handlerC3829H = this.f584mH;
                handlerC3829H.executeOrSendMessage(Message.obtain(handlerC3829H, 31));
                IInputMethodManagerGlobalInvoker.showSoftInput(this.mClient, this.mCurRootView.getView().getWindowToken(), statsToken, flags, this.mCurRootView.getLastClickToolType(), resultReceiver, 1);
                return;
            }
            ImeTracker.forLogging().onFailed(statsToken, 1);
            Log.m102w(TAG, "No current root view, ignoring showSoftInputUnchecked()");
        }
    }

    public boolean hideSoftInputFromWindow(IBinder windowToken, int flags) {
        return hideSoftInputFromWindow(windowToken, flags, null);
    }

    public boolean hideSoftInputFromWindow(IBinder windowToken, int flags, ResultReceiver resultReceiver) {
        return hideSoftInputFromWindow(windowToken, flags, resultReceiver, 4);
    }

    private boolean hideSoftInputFromWindow(IBinder windowToken, int flags, ResultReceiver resultReceiver, int reason) {
        ImeTracker.Token statsToken = ImeTracker.forLogging().onRequestHide(null, Process.myUid(), 2, reason);
        ImeTracker.forLatency().onRequestHide(statsToken, 2, reason, new InsetsController$$ExternalSyntheticLambda2());
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#hideSoftInputFromWindow", this, null);
        Log.m98i(TAG_LIFE_CYCLE, "hsifw() - flag : " + flags);
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.m94d(TAG, "hideSoftInput callers=" + Debug.getCallers(10));
        }
        checkFocus();
        synchronized (this.f584mH) {
            View servedView = getServedViewLocked();
            if (servedView != null && servedView.getWindowToken() == windowToken) {
                ImeTracker.forLogging().onProgress(statsToken, 1);
                Log.m98i(TAG_LIFE_CYCLE, "hsifw() - mService.hideSoftInput");
                return IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, windowToken, statsToken, flags, resultReceiver, reason);
            }
            ImeTracker.forLogging().onFailed(statsToken, 1);
            ImeTracker.forLatency().onHideFailed(statsToken, 1, new InsetsController$$ExternalSyntheticLambda2());
            Log.m98i(TAG_LIFE_CYCLE, "hsifw() ignored windowToken=" + (windowToken == null ? SemCapabilities.FEATURE_TAG_NULL : windowToken) + " servedView=" + (servedView == null ? SemCapabilities.FEATURE_TAG_NULL : InputMethodDebug.dumpViewInfo(servedView)) + " mDisplayId=" + this.mDisplayId);
            return false;
        }
    }

    public void startStylusHandwriting(View view) {
        startStylusHandwritingInternal(view, null);
    }

    private boolean startStylusHandwritingInternal(View view, String delegatorPackageName) {
        Objects.requireNonNull(view);
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            fallbackImm.startStylusHandwritingInternal(view, delegatorPackageName);
        }
        boolean useDelegation = !TextUtils.isEmpty(delegatorPackageName);
        checkFocus();
        synchronized (this.f584mH) {
            if (!hasServedByInputMethodLocked(view)) {
                Log.m102w(TAG, "Ignoring startStylusHandwriting as view=" + view + " is not served.");
                return false;
            }
            if (view.getViewRootImpl() != this.mCurRootView) {
                Log.m102w(TAG, "Ignoring startStylusHandwriting: View's window does not have focus.");
                return false;
            }
            if (useDelegation) {
                return IInputMethodManagerGlobalInvoker.acceptStylusHandwritingDelegation(this.mClient, UserHandle.myUserId(), view.getContext().getOpPackageName(), delegatorPackageName);
            }
            IInputMethodManagerGlobalInvoker.startStylusHandwriting(this.mClient);
            return false;
        }
    }

    public void prepareStylusHandwritingDelegation(View delegatorView) {
        prepareStylusHandwritingDelegation(delegatorView, delegatorView.getContext().getOpPackageName());
    }

    public void prepareStylusHandwritingDelegation(View delegatorView, String delegatePackageName) {
        Objects.requireNonNull(delegatorView);
        Objects.requireNonNull(delegatePackageName);
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(delegatorView);
        if (fallbackImm != null) {
            fallbackImm.prepareStylusHandwritingDelegation(delegatorView, delegatePackageName);
        }
        IInputMethodManagerGlobalInvoker.prepareStylusHandwritingDelegation(this.mClient, UserHandle.myUserId(), delegatePackageName, delegatorView.getContext().getOpPackageName());
    }

    public boolean acceptStylusHandwritingDelegation(View delegateView) {
        return startStylusHandwritingInternal(delegateView, delegateView.getContext().getOpPackageName());
    }

    public boolean acceptStylusHandwritingDelegation(View delegateView, String delegatorPackageName) {
        Objects.requireNonNull(delegatorPackageName);
        return startStylusHandwritingInternal(delegateView, delegatorPackageName);
    }

    @Deprecated
    public void toggleSoftInputFromWindow(IBinder windowToken, int showFlags, int hideFlags) {
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#toggleSoftInputFromWindow", this, null);
        synchronized (this.f584mH) {
            View servedView = getServedViewLocked();
            if (servedView != null && servedView.getWindowToken() == windowToken) {
                Log.m98i(TAG_LIFE_CYCLE, "tsifw()");
                toggleSoftInput(showFlags, hideFlags);
                return;
            }
            Log.m98i(TAG_LIFE_CYCLE, "tsifw() ignored windowToken=" + (windowToken == null ? SemCapabilities.FEATURE_TAG_NULL : windowToken) + " servedView=" + (servedView == null ? SemCapabilities.FEATURE_TAG_NULL : InputMethodDebug.dumpViewInfo(servedView)) + " mDisplayId=" + this.mDisplayId);
        }
    }

    @Deprecated
    public void toggleSoftInput(int showFlags, int hideFlags) {
        semToggleSoftInput(showFlags, hideFlags);
    }

    private void semToggleSoftInput(int showFlags, int hideFlags) {
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#toggleSoftInput", this, null);
        synchronized (this.f584mH) {
            View view = getServedViewLocked();
            Log.m98i(TAG_LIFE_CYCLE, "stsi(" + showFlags + ", " + hideFlags + "), served view : " + view);
            if (view != null) {
                if (isInputMethodShown()) {
                    hideSoftInputFromWindow(view.getWindowToken(), hideFlags, null, 25);
                } else {
                    showSoftInput(view, null, showFlags, null, 24);
                }
            }
        }
    }

    public void restartInput(View view) {
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            fallbackImm.restartInput(view);
            return;
        }
        checkFocus();
        synchronized (this.f584mH) {
            if (hasServedByInputMethodLocked(view)) {
                this.mServedConnecting = true;
                startInputInner(4, null, 0, 0, 0);
            }
        }
    }

    public boolean doInvalidateInput(RemoteInputConnectionImpl inputConnection, TextSnapshot textSnapshot, final int sessionId) {
        synchronized (this.f584mH) {
            if (this.mServedInputConnection == inputConnection && this.mCurrentEditorInfo != null) {
                if (!isImeSessionAvailableLocked()) {
                    return false;
                }
                final EditorInfo editorInfo = this.mCurrentEditorInfo.createCopyInternal();
                int selectionStart = textSnapshot.getSelectionStart();
                this.mCursorSelStart = selectionStart;
                editorInfo.initialSelStart = selectionStart;
                int selectionEnd = textSnapshot.getSelectionEnd();
                this.mCursorSelEnd = selectionEnd;
                editorInfo.initialSelEnd = selectionEnd;
                this.mCursorCandStart = textSnapshot.getCompositionStart();
                this.mCursorCandEnd = textSnapshot.getCompositionEnd();
                editorInfo.initialCapsMode = textSnapshot.getCursorCapsMode();
                editorInfo.setInitialSurroundingTextInternal(textSnapshot.getSurroundingText());
                this.mCurBindState.mImeSession.invalidateInput(editorInfo, this.mServedInputConnection, sessionId);
                final IRemoteAccessibilityInputConnection accessibilityInputConnection = this.mServedInputConnection.asIRemoteAccessibilityInputConnection();
                forAccessibilitySessionsLocked(new Consumer() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((IAccessibilityInputMethodSessionInvoker) obj).invalidateInput(EditorInfo.this, accessibilityInputConnection, sessionId);
                    }
                });
                return true;
            }
            return true;
        }
    }

    public void invalidateInput(View view) {
        Log.m98i(TAG, "invalidateInput");
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.m94d(TAG, "invalidateInput callers=" + Debug.getCallers(15));
        }
        Objects.requireNonNull(view);
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            fallbackImm.invalidateInput(view);
            return;
        }
        synchronized (this.f584mH) {
            if (this.mServedInputConnection != null && getServedViewLocked() == view) {
                this.mServedInputConnection.scheduleInvalidateInput();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0202  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean startInputInner(final int startInputReason, IBinder windowGainingFocus, int startInputFlags, int softInputMode, int windowFlags) {
        int startInputFlags2;
        IBinder windowGainingFocus2;
        int softInputMode2;
        int windowFlags2;
        HandlerC3829H handlerC3829H;
        View servedView;
        RemoteInputConnectionImpl previouslyServedConnection;
        Handler icHandler;
        RemoteInputConnectionImpl servedInputConnection;
        boolean z;
        RemoteInputConnectionImpl servedInputConnection2;
        boolean z2;
        boolean canSkip;
        boolean z3;
        View view;
        View servedView2;
        boolean z4;
        View view2;
        synchronized (this.f584mH) {
            try {
                View view3 = getServedViewLocked();
                boolean z5 = DEBUG;
                if (z5) {
                    Log.m100v(TAG, "Starting input: view=" + InputMethodDebug.dumpViewInfo(view3) + " reason=" + InputMethodDebug.startInputReasonToString(startInputReason));
                }
                if (view3 == null) {
                    if (z5) {
                        Log.m100v(TAG, "ABORT input: no served view!");
                    }
                    return false;
                }
                Handler vh = view3.getHandler();
                if (vh == null) {
                    if (z5) {
                        Log.m100v(TAG, "ABORT input: no handler for view! Close current input.");
                    }
                    Log.m102w(TAG, "ABORT input: no handler for view! Close current input.");
                    closeCurrentInput();
                    return false;
                }
                if (vh.getLooper() != Looper.myLooper()) {
                    if (z5) {
                        Log.m100v(TAG, "Starting input: reschedule to view thread");
                    }
                    vh.post(new Runnable() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            InputMethodManager.this.lambda$startInputInner$2(startInputReason);
                        }
                    });
                    return false;
                }
                if (windowGainingFocus == null) {
                    IBinder windowGainingFocus3 = view3.getWindowToken();
                    if (windowGainingFocus3 == null) {
                        Log.m96e(TAG, "ABORT input: ServedView must be attached to a Window");
                        return false;
                    }
                    if (view3.getViewRootImpl() == null) {
                        Log.m102w(TAG, "startInputInner: viewRootImpl is null");
                        return false;
                    }
                    startInputFlags2 = getStartInputFlags(view3, startInputFlags);
                    int softInputMode3 = view3.getViewRootImpl().mWindowAttributes.softInputMode;
                    windowGainingFocus2 = windowGainingFocus3;
                    softInputMode2 = softInputMode3;
                    windowFlags2 = view3.getViewRootImpl().mWindowAttributes.flags;
                } else {
                    startInputFlags2 = startInputFlags;
                    windowGainingFocus2 = windowGainingFocus;
                    softInputMode2 = softInputMode;
                    windowFlags2 = windowFlags;
                }
                Pair<InputConnection, EditorInfo> connectionPair = createInputConnection(view3);
                InputConnection ic = connectionPair.first;
                EditorInfo editorInfo = connectionPair.second;
                if (editorInfo.extras == null) {
                    editorInfo.extras = new Bundle();
                }
                SemInputMethodManagerUtils.putInfoInExtra(view3, editorInfo);
                HandlerC3829H handlerC3829H2 = this.f584mH;
                synchronized (handlerC3829H2) {
                    try {
                        try {
                            servedView = getServedViewLocked();
                        } catch (Throwable th) {
                            th = th;
                            handlerC3829H = handlerC3829H2;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                    if (servedView == view3) {
                        try {
                        } catch (Throwable th3) {
                            th = th3;
                            handlerC3829H = handlerC3829H2;
                        }
                        if (this.mServedConnecting) {
                            int startInputFlags3 = this.mCurrentEditorInfo == null ? startInputFlags2 | 4 : startInputFlags2;
                            try {
                                editorInfo.setInitialToolType(this.mCurRootView.getLastClickToolType());
                                this.mCurrentEditorInfo = editorInfo.createCopyInternal();
                                previouslyServedConnection = this.mServedInputConnection;
                                this.mServedConnecting = false;
                                if (previouslyServedConnection != null) {
                                    try {
                                        previouslyServedConnection.deactivate();
                                        this.mServedInputConnection = null;
                                        this.mServedInputConnectionHandler = null;
                                    } catch (Throwable th4) {
                                        th = th4;
                                        handlerC3829H = handlerC3829H2;
                                    }
                                }
                                if (ic != null) {
                                    this.mCursorSelStart = editorInfo.initialSelStart;
                                    int i = editorInfo.initialSelEnd;
                                    this.mCursorSelEnd = i;
                                    this.mInitialSelStart = this.mCursorSelStart;
                                    this.mInitialSelEnd = i;
                                    this.mCursorCandStart = -1;
                                    this.mCursorCandEnd = -1;
                                    this.mCursorRect.setEmpty();
                                    this.mCursorAnchorInfo = null;
                                    Handler handler = null;
                                    try {
                                        handler = ic.getHandler();
                                    } catch (AbstractMethodError e) {
                                    }
                                    icHandler = handler;
                                    this.mServedInputConnectionHandler = icHandler;
                                    RemoteInputConnectionImpl servedInputConnection3 = new RemoteInputConnectionImpl(icHandler != null ? icHandler.getLooper() : vh.getLooper(), ic, this, view3);
                                    servedInputConnection = servedInputConnection3;
                                } else {
                                    icHandler = null;
                                    this.mServedInputConnectionHandler = null;
                                    servedInputConnection = null;
                                }
                                this.mServedInputConnection = servedInputConnection;
                                z = DEBUG;
                                if (z) {
                                    Log.m100v(TAG, "START INPUT: view=" + InputMethodDebug.dumpViewInfo(view3) + " ic=" + ic + " editorInfo=" + editorInfo + " startInputFlags=" + InputMethodDebug.startInputFlagsToString(startInputFlags3));
                                }
                            } catch (Throwable th5) {
                                th = th5;
                                handlerC3829H = handlerC3829H2;
                            }
                            if (OPTIMIZE_NONEDITABLE_VIEWS && previouslyServedConnection == null && ic == null) {
                                servedInputConnection2 = servedInputConnection;
                                if (isSwitchingBetweenEquivalentNonEditableViews(this.mPreviousViewFocusParameters, startInputFlags3, startInputReason, softInputMode2, windowFlags2)) {
                                    z2 = true;
                                    canSkip = z2;
                                    this.mPreviousViewFocusParameters = new ViewFocusParameterInfo(this.mCurrentEditorInfo, startInputFlags3, startInputReason, softInputMode2, windowFlags2);
                                    if (canSkip) {
                                        int targetUserId = editorInfo.targetInputMethodUser != null ? editorInfo.targetInputMethodUser.getIdentifier() : UserHandle.myUserId();
                                        Trace.traceBegin(32L, "IMM.startInputOrWindowGainedFocus");
                                        Log.m98i(TAG, "startInputInner - IInputMethodManagerGlobalInvoker.startInputOrWindowGainedFocus");
                                        handlerC3829H = handlerC3829H2;
                                        try {
                                            InputBindResult res = IInputMethodManagerGlobalInvoker.startInputOrWindowGainedFocus(startInputReason, this.mClient, windowGainingFocus2, startInputFlags3, softInputMode2, windowFlags2, editorInfo, servedInputConnection2, servedInputConnection2 == null ? null : servedInputConnection2.asIRemoteAccessibilityInputConnection(), view3.getContext().getApplicationInfo().targetSdkVersion, targetUserId, this.mImeDispatcher);
                                            try {
                                                Trace.traceEnd(32L);
                                                if (z) {
                                                    try {
                                                        Log.m100v(TAG, "Starting input: Bind result=" + res);
                                                    } catch (Throwable th6) {
                                                        th = th6;
                                                    }
                                                }
                                                try {
                                                    if (res == null) {
                                                        try {
                                                            Log.wtf(TAG, "startInputOrWindowGainedFocus must not return null. startInputReason=" + InputMethodDebug.startInputReasonToString(startInputReason) + " editorInfo=" + editorInfo + " startInputFlags=" + InputMethodDebug.startInputFlagsToString(startInputFlags3));
                                                            return false;
                                                        } catch (Throwable th7) {
                                                            th = th7;
                                                        }
                                                    } else {
                                                        try {
                                                            this.mVirtualDisplayToScreenMatrix = res.getVirtualDisplayToScreenMatrix();
                                                            if (res.f684id != null) {
                                                                updateInputChannelLocked(res.channel);
                                                                this.mCurMethod = res.method;
                                                                this.mCurBindState = new BindState(res);
                                                                this.mAccessibilityInputMethodSession.clear();
                                                                if (res.accessibilitySessions != null) {
                                                                    for (int i2 = 0; i2 < res.accessibilitySessions.size(); i2++) {
                                                                        IAccessibilityInputMethodSessionInvoker wrapper = IAccessibilityInputMethodSessionInvoker.createOrNull(res.accessibilitySessions.valueAt(i2));
                                                                        if (wrapper != null) {
                                                                            this.mAccessibilityInputMethodSession.append(res.accessibilitySessions.keyAt(i2), wrapper);
                                                                        }
                                                                    }
                                                                }
                                                                this.mCurId = res.f684id;
                                                            } else if (res.channel != null && res.channel != this.mCurChannel) {
                                                                res.channel.dispose();
                                                            }
                                                            switch (res.result) {
                                                                case 12:
                                                                    if (DEBUG_SEP) {
                                                                        Log.m100v(TAG, "startInputInner: not ime target window so set served view : " + this.mServedView + " to null");
                                                                    }
                                                                    z3 = true;
                                                                    this.mRestartOnNextWindowFocus = true;
                                                                    this.mServedView = null;
                                                                    break;
                                                                default:
                                                                    z3 = true;
                                                                    break;
                                                            }
                                                            if (this.mCompletions != null && isImeSessionAvailableLocked()) {
                                                                this.mCurBindState.mImeSession.displayCompletions(this.mCompletions);
                                                            }
                                                            boolean hasServedView = this.mServedView != null ? z3 : false;
                                                            if (ic != null && res != null && res.method != null && hasServedView) {
                                                                if (DEBUG_SEP) {
                                                                    view = view3;
                                                                    Log.m100v(TAG, "Calling View.onInputConnectionOpened: view= " + view + ", ic=" + ic + ", editorInfo=" + editorInfo + ", handler=" + icHandler + ", editorInfo inputType=" + Integer.toHexString(editorInfo.inputType) + ", imeOptions=" + Integer.toHexString(editorInfo.imeOptions) + ", internalImeOptions=" + editorInfo.internalImeOptions);
                                                                } else {
                                                                    view = view3;
                                                                }
                                                                reportInputConnectionOpened(ic, editorInfo, icHandler, view);
                                                            }
                                                            return z3;
                                                        } catch (Throwable th8) {
                                                            th = th8;
                                                        }
                                                    }
                                                } catch (Throwable th9) {
                                                    th = th9;
                                                }
                                            } catch (Throwable th10) {
                                                th = th10;
                                            }
                                        } catch (Throwable th11) {
                                            th = th11;
                                        }
                                        throw th;
                                    }
                                    if (z) {
                                        Log.m94d(TAG, "Not calling IMMS due to switching between non-editable views.");
                                    }
                                }
                            } else {
                                servedInputConnection2 = servedInputConnection;
                            }
                            z2 = false;
                            canSkip = z2;
                            this.mPreviousViewFocusParameters = new ViewFocusParameterInfo(this.mCurrentEditorInfo, startInputFlags3, startInputReason, softInputMode2, windowFlags2);
                            if (canSkip) {
                            }
                        } else {
                            servedView2 = servedView;
                            handlerC3829H = handlerC3829H2;
                            z4 = false;
                            view2 = view3;
                        }
                    } else {
                        servedView2 = servedView;
                        handlerC3829H = handlerC3829H2;
                        z4 = false;
                        view2 = view3;
                    }
                    if (z5) {
                        try {
                            Log.m100v(TAG, "Starting input: finished by someone else. view=" + InputMethodDebug.dumpViewInfo(view2) + " servedView=" + InputMethodDebug.dumpViewInfo(servedView2) + " mServedConnecting=" + this.mServedConnecting);
                        } catch (Throwable th12) {
                            th = th12;
                        }
                    }
                    RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
                    if (remoteInputConnectionImpl != null && startInputReason == 6) {
                        reportInputConnectionOpened(remoteInputConnectionImpl.getInputConnection(), this.mCurrentEditorInfo, this.mServedInputConnectionHandler, view2);
                    }
                    return z4;
                }
                return false;
            } catch (Throwable th13) {
                th = th13;
                while (true) {
                    try {
                        throw th;
                    } catch (Throwable th14) {
                        th = th14;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startInputInner$2(int startInputReason) {
        startInputOnWindowFocusGainInternal(startInputReason, null, 0, 0, 0);
    }

    private boolean isSwitchingBetweenEquivalentNonEditableViews(ViewFocusParameterInfo previousViewFocusParameters, int startInputFlags, int startInputReason, int softInputMode, int windowFlags) {
        return (startInputFlags & 8) == 0 && (startInputFlags & 2) == 0 && previousViewFocusParameters != null && previousViewFocusParameters.sameAs(this.mCurrentEditorInfo, startInputFlags, startInputReason, softInputMode, windowFlags);
    }

    private void reportInputConnectionOpened(InputConnection ic, EditorInfo editorInfo, Handler icHandler, View view) {
        view.onInputConnectionOpenedInternal(ic, editorInfo, icHandler);
        ViewRootImpl viewRoot = view.getViewRootImpl();
        if (viewRoot != null) {
            viewRoot.getHandwritingInitiator().onInputConnectionCreated(view);
        }
    }

    public void addVirtualStylusIdForTestSession() {
        synchronized (this.f584mH) {
            IInputMethodManagerGlobalInvoker.addVirtualStylusIdForTestSession(this.mClient);
        }
    }

    public void setStylusWindowIdleTimeoutForTest(long timeout) {
        synchronized (this.f584mH) {
            IInputMethodManagerGlobalInvoker.setStylusWindowIdleTimeoutForTest(this.mClient, timeout);
        }
    }

    @Deprecated
    public void windowDismissed(IBinder appWindowToken) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getStartInputFlags(View focusedView, int startInputFlags) {
        int startInputFlags2 = startInputFlags | 1;
        if (focusedView.onCheckIsTextEditor()) {
            return startInputFlags2 | 2;
        }
        return startInputFlags2;
    }

    public void checkFocus() {
        synchronized (this.f584mH) {
            ViewRootImpl viewRootImpl = this.mCurRootView;
            if (viewRootImpl == null) {
                return;
            }
            if (checkFocusInternalLocked(false, viewRootImpl)) {
                startInputOnWindowFocusGainInternal(5, null, 0, 0, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkFocusInternalLocked(boolean forceNewFocus, ViewRootImpl viewRootImpl) {
        if (this.mCurRootView != viewRootImpl) {
            return false;
        }
        if (this.mServedView == this.mNextServedView && !forceNewFocus) {
            return false;
        }
        if (DEBUG) {
            StringBuilder append = new StringBuilder().append("checkFocus: view=").append(this.mServedView).append(" next=").append(this.mNextServedView).append(" force=").append(forceNewFocus).append(" package=");
            View view = this.mServedView;
            Log.m100v(TAG, append.append(view != null ? view.getContext().getPackageName() : "<none>").toString());
        }
        View view2 = this.mNextServedView;
        if (view2 == null) {
            if (DEBUG_SEP) {
                Log.m98i(TAG, "checkFocus: return, mNextServedView is null");
            }
            finishInputLocked();
            closeCurrentInput();
            return false;
        }
        this.mServedView = view2;
        RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
        if (remoteInputConnectionImpl != null) {
            remoteInputConnectionImpl.finishComposingTextFromImm();
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onViewFocusChangedInternal(View view, boolean hasFocus) {
        if (view == null || view.isTemporarilyDetached()) {
            if (DEBUG_SEP) {
                Log.m98i(TAG, "onViewFocusChangedInternal return, view is " + (view == null ? SemCapabilities.FEATURE_TAG_NULL : "detached temporarily"));
                return;
            }
            return;
        }
        ViewRootImpl viewRootImpl = view.getViewRootImpl();
        synchronized (this.f584mH) {
            if (this.mCurRootView != viewRootImpl) {
                if (DEBUG_SEP) {
                    Log.m98i(TAG, "onViewFocusChangedInternal return, mCurRootView=" + this.mCurRootView + ",  view.getViewRootImpl()=" + view.getViewRootImpl());
                }
                return;
            }
            if (view.hasImeFocus() && view.hasWindowFocus()) {
                if (DEBUG) {
                    Log.m94d(TAG, "onViewFocusChangedInternal, view=" + InputMethodDebug.dumpViewInfo(view));
                }
                if (hasFocus) {
                    this.mNextServedView = view;
                }
                viewRootImpl.dispatchCheckFocus();
                return;
            }
            if (DEBUG_SEP) {
                Log.m98i(TAG, "onViewFocusChangedInternal return, view=" + InputMethodDebug.dumpViewInfo(view));
            }
        }
    }

    void closeCurrentInput() {
        ImeTracker.Token statsToken = ImeTracker.forLogging().onRequestHide(null, Process.myUid(), 2, 4);
        ImeTracker.forLatency().onRequestHide(statsToken, 2, 4, new InsetsController$$ExternalSyntheticLambda2());
        synchronized (this.f584mH) {
            ViewRootImpl viewRootImpl = this.mCurRootView;
            if (viewRootImpl != null && viewRootImpl.getView() != null) {
                ImeTracker.forLogging().onProgress(statsToken, 1);
                Log.m98i(TAG_LIFE_CYCLE, "closeCurrentInput: IInputMethodManagerGlobalInvoker.hideSoftInput");
                try {
                    IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, this.mCurRootView.getView().getWindowToken(), statsToken, 2, null, 4);
                } catch (NullPointerException e) {
                    ViewRootImpl viewRootImpl2 = this.mCurRootView;
                    if (viewRootImpl2 == null || viewRootImpl2.getView() == null) {
                        Log.m102w(TAG, "NullPointerException: No current root view, ignoring closeCurrentInput()");
                    }
                    e.printStackTrace();
                }
                return;
            }
            ImeTracker.forLogging().onFailed(statsToken, 1);
            ImeTracker.forLatency().onHideFailed(statsToken, 1, new InsetsController$$ExternalSyntheticLambda2());
            Log.m102w(TAG, "No current root view, ignoring closeCurrentInput()");
        }
    }

    public void registerImeConsumer(ImeInsetsSourceConsumer imeInsetsConsumer) {
        if (imeInsetsConsumer == null) {
            throw new IllegalStateException("ImeInsetsSourceConsumer cannot be null.");
        }
        synchronized (this.f584mH) {
            this.mImeInsetsConsumer = imeInsetsConsumer;
        }
    }

    public void unregisterImeConsumer(ImeInsetsSourceConsumer imeInsetsConsumer) {
        if (imeInsetsConsumer == null) {
            throw new IllegalStateException("ImeInsetsSourceConsumer cannot be null.");
        }
        synchronized (this.f584mH) {
            if (this.mImeInsetsConsumer == imeInsetsConsumer) {
                this.mImeInsetsConsumer = null;
            }
        }
    }

    public boolean requestImeShow(IBinder windowToken, ImeTracker.Token statsToken) {
        Log.m98i(TAG_LIFE_CYCLE, "ris()");
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.m94d(TAG, "requestImeShow callers=" + Debug.getCallers(10));
        }
        checkFocus();
        synchronized (this.f584mH) {
            View servedView = getServedViewLocked();
            if (servedView != null && servedView.getWindowToken() == windowToken) {
                ImeTracker.forLogging().onProgress(statsToken, 37);
                showSoftInput(servedView, statsToken, 0, null, 26);
                return true;
            }
            ImeTracker.forLogging().onFailed(statsToken, 37);
            Log.m98i(TAG_LIFE_CYCLE, "ris() ignored windowToken=" + (windowToken == null ? SemCapabilities.FEATURE_TAG_NULL : windowToken) + " servedView=" + (servedView == null ? SemCapabilities.FEATURE_TAG_NULL : InputMethodDebug.dumpViewInfo(servedView)) + " mDisplayId=" + this.mDisplayId);
            return false;
        }
    }

    public void notifyImeHidden(IBinder windowToken, ImeTracker.Token statsToken) {
        ViewRootImpl viewRootImpl;
        Log.m98i(TAG_LIFE_CYCLE, "notifyImeHidden: IInputMethodManagerGlobalInvoker.hideSoftInput");
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.m94d(TAG, "notifyImeHidden callers=" + Debug.getCallers(10));
        }
        if (statsToken == null) {
            statsToken = ImeTracker.forLogging().onRequestHide(null, Process.myUid(), 2, 28);
        }
        ImeTracker.forLatency().onRequestHide(statsToken, 2, 28, new InsetsController$$ExternalSyntheticLambda2());
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#notifyImeHidden", this, null);
        synchronized (this.f584mH) {
            if (isImeSessionAvailableLocked() && (viewRootImpl = this.mCurRootView) != null && viewRootImpl.getWindowToken() == windowToken) {
                ImeTracker.forLogging().onProgress(statsToken, 1);
                IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, windowToken, statsToken, 0, null, 28);
                return;
            }
            ImeTracker.forLogging().onFailed(statsToken, 1);
            ImeTracker.forLatency().onHideFailed(statsToken, 1, new InsetsController$$ExternalSyntheticLambda2());
        }
    }

    public void removeImeSurface(IBinder windowToken) {
        if (DEBUG_SEP) {
            Log.m98i(TAG, "removeImeSurface");
        }
        synchronized (this.f584mH) {
            IInputMethodManagerGlobalInvoker.removeImeSurfaceFromWindowAsync(windowToken);
        }
    }

    public void updateSelection(View view, final int selStart, final int selEnd, final int candidatesStart, final int candidatesEnd) {
        HandlerC3829H handlerC3829H;
        HandlerC3829H handlerC3829H2;
        HandlerC3829H handlerC3829H3;
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            fallbackImm.updateSelection(view, selStart, selEnd, candidatesStart, candidatesEnd);
            return;
        }
        checkFocus();
        HandlerC3829H handlerC3829H4 = this.f584mH;
        synchronized (handlerC3829H4) {
            try {
                try {
                    if (!hasServedByInputMethodLocked(view) || this.mCurrentEditorInfo == null) {
                        handlerC3829H2 = handlerC3829H4;
                    } else if (isImeSessionAvailableLocked()) {
                        RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
                        if (remoteInputConnectionImpl == null || !remoteInputConnectionImpl.hasPendingInvalidation()) {
                            try {
                                if (this.mCursorSelStart == selStart && this.mCursorSelEnd == selEnd && this.mCursorCandStart == candidatesStart && this.mCursorCandEnd == candidatesEnd) {
                                    handlerC3829H3 = handlerC3829H4;
                                    return;
                                }
                                this.mCurBindState.mImeSession.updateSelection(this.mCursorSelStart, this.mCursorSelEnd, selStart, selEnd, candidatesStart, candidatesEnd);
                                handlerC3829H3 = handlerC3829H4;
                                forAccessibilitySessionsLocked(new Consumer() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        InputMethodManager.this.lambda$updateSelection$3(selStart, selEnd, candidatesStart, candidatesEnd, (IAccessibilityInputMethodSessionInvoker) obj);
                                    }
                                });
                                this.mCursorSelStart = selStart;
                                this.mCursorSelEnd = selEnd;
                                this.mCursorCandStart = candidatesStart;
                                this.mCursorCandEnd = candidatesEnd;
                                return;
                            } catch (Throwable th) {
                                th = th;
                                handlerC3829H = handlerC3829H4;
                                throw th;
                            }
                            boolean z = DEBUG;
                            if (z) {
                                Log.m94d(TAG, "updateSelection");
                            }
                            if (z) {
                                Log.m100v(TAG, "SELECTION CHANGE: " + this.mCurBindState.mImeSession);
                            }
                        }
                    } else {
                        handlerC3829H2 = handlerC3829H4;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                handlerC3829H = handlerC3829H4;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSelection$3(int selStart, int selEnd, int candidatesStart, int candidatesEnd, IAccessibilityInputMethodSessionInvoker wrapper) {
        wrapper.updateSelection(this.mCursorSelStart, this.mCursorSelEnd, selStart, selEnd, candidatesStart, candidatesEnd);
    }

    @Deprecated
    public void viewClicked(View view) {
        View servedView;
        View nextServedView;
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            fallbackImm.viewClicked(view);
            return;
        }
        synchronized (this.f584mH) {
            servedView = getServedViewLocked();
            nextServedView = getNextServedViewLocked();
        }
        boolean focusChanged = servedView != nextServedView;
        checkFocus();
        synchronized (this.f584mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                if (DEBUG) {
                    Log.m100v(TAG, "onViewClicked: " + focusChanged);
                }
                this.mCurBindState.mImeSession.viewClicked(focusChanged);
            }
        }
    }

    @Deprecated
    public boolean isWatchingCursor(View view) {
        return false;
    }

    @Deprecated
    public boolean isCursorAnchorInfoEnabled() {
        boolean z;
        synchronized (this.f584mH) {
            int i = this.mRequestUpdateCursorAnchorInfoMonitorMode;
            boolean isImmediate = (i & 1) != 0;
            boolean isMonitoring = (i & 2) != 0;
            z = isImmediate || isMonitoring;
        }
        return z;
    }

    @Deprecated
    public void setUpdateCursorAnchorInfoMode(int flags) {
        synchronized (this.f584mH) {
            this.mRequestUpdateCursorAnchorInfoMonitorMode = flags;
        }
    }

    @Deprecated
    public void updateCursor(View view, int left, int top, int right, int bottom) {
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            fallbackImm.updateCursor(view, left, top, right, bottom);
            return;
        }
        checkFocus();
        synchronized (this.f584mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                this.mTmpCursorRect.set(left, top, right, bottom);
                if (!this.mCursorRect.equals(this.mTmpCursorRect)) {
                    if (DEBUG) {
                        Log.m94d(TAG, "updateCursor: " + this.mCurBindState.mImeSession);
                    }
                    this.mCurBindState.mImeSession.updateCursor(this.mTmpCursorRect);
                    this.mCursorRect.set(this.mTmpCursorRect);
                }
            }
        }
    }

    public void updateCursorAnchorInfo(View view, CursorAnchorInfo cursorAnchorInfo) {
        if (view == null || cursorAnchorInfo == null) {
            return;
        }
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            fallbackImm.updateCursorAnchorInfo(view, cursorAnchorInfo);
            return;
        }
        checkFocus();
        synchronized (this.f584mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
                boolean isImmediate = remoteInputConnectionImpl != null && remoteInputConnectionImpl.resetHasPendingImmediateCursorAnchorInfoUpdate();
                if (!isImmediate && Objects.equals(this.mCursorAnchorInfo, cursorAnchorInfo)) {
                    if (DEBUG) {
                        Log.m102w(TAG, "Ignoring redundant updateCursorAnchorInfo: info=" + cursorAnchorInfo);
                    }
                    return;
                }
                if (DEBUG) {
                    Log.m100v(TAG, "updateCursorAnchorInfo: " + cursorAnchorInfo);
                }
                if (this.mVirtualDisplayToScreenMatrix != null) {
                    this.mCurBindState.mImeSession.updateCursorAnchorInfo(CursorAnchorInfo.createForAdditionalParentMatrix(cursorAnchorInfo, this.mVirtualDisplayToScreenMatrix));
                } else {
                    this.mCurBindState.mImeSession.updateCursorAnchorInfo(cursorAnchorInfo);
                }
                this.mCursorAnchorInfo = cursorAnchorInfo;
            }
        }
    }

    public void sendAppPrivateCommand(View view, String action, Bundle data) {
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            fallbackImm.sendAppPrivateCommand(view, action, data);
            return;
        }
        checkFocus();
        synchronized (this.f584mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                if (DEBUG) {
                    Log.m100v(TAG, "APP PRIVATE COMMAND " + action + ": " + data);
                }
                this.mCurBindState.mImeSession.appPrivateCommand(action, data);
            }
        }
    }

    @Deprecated
    public void setInputMethod(IBinder token, String id) {
        if (token != null) {
            Log.m94d(TAG, "setInputMethod: Calling setInputMethodtoken = " + token + "id = " + id);
            InputMethodPrivilegedOperationsRegistry.get(token).setInputMethod(id);
            return;
        }
        if (id == null) {
            return;
        }
        if (Process.myUid() == 1000) {
            Log.m102w(TAG, "System process should not be calling setInputMethod() because almost always it is a bug under multi-user / multi-profile environment. Consider interacting with InputMethodManagerService directly via LocalServices.");
            return;
        }
        Context fallbackContext = ActivityThread.currentApplication();
        if (fallbackContext == null || fallbackContext.checkSelfPermission(Manifest.permission.WRITE_SECURE_SETTINGS) != 0) {
            return;
        }
        List<InputMethodInfo> imis = getEnabledInputMethodList();
        int numImis = imis.size();
        boolean found = false;
        int i = 0;
        while (true) {
            if (i >= numImis) {
                break;
            }
            InputMethodInfo imi = imis.get(i);
            if (!id.equals(imi.getId())) {
                i++;
            } else {
                found = true;
                break;
            }
        }
        if (found || SemInputMethodManagerUtils.METHOD_ID_BIXBY_DICTATION.equals(id)) {
            Log.m102w(TAG, "The undocumented behavior that setInputMethod() accepts null token when the caller has WRITE_SECURE_SETTINGS is deprecated. This behavior may be completely removed in a future version.  Update secure settings directly instead.");
            ContentResolver resolver = fallbackContext.getContentResolver();
            Settings.Secure.putInt(resolver, Settings.Secure.SELECTED_INPUT_METHOD_SUBTYPE, -1);
            Settings.Secure.putString(resolver, Settings.Secure.DEFAULT_INPUT_METHOD, id);
            Log.m98i(TAG, "setInputMethod: Putting Settings.Secure.DEFAULT_INPUT_METHOD, id=" + id);
            return;
        }
        Log.m96e(TAG, "Ignoring setInputMethod(null, " + id + ") because the specified id not found in enabled IMEs.");
    }

    @Deprecated
    public void setInputMethodAndSubtype(IBinder token, String id, InputMethodSubtype subtype) {
        if (token == null) {
            Log.m96e(TAG, "setInputMethodAndSubtype() does not accept null token on Android Q and later.");
        } else {
            InputMethodPrivilegedOperationsRegistry.get(token).setInputMethodAndSubtype(id, subtype);
        }
    }

    @Deprecated
    public void hideSoftInputFromInputMethod(IBinder token, int flags) {
        Log.m98i(TAG_LIFE_CYCLE, "hsifi() - flag : " + flags);
        InputMethodPrivilegedOperationsRegistry.get(token).hideMySoftInput(flags, 32);
    }

    @Deprecated
    public void showSoftInputFromInputMethod(IBinder token, int flags) {
        Log.m98i(TAG_LIFE_CYCLE, "ssifim() - flag : " + flags);
        InputMethodPrivilegedOperationsRegistry.get(token).showMySoftInput(flags);
    }

    public int dispatchInputEvent(InputEvent event, Object token, FinishedInputEventCallback callback, Handler handler) {
        synchronized (this.f584mH) {
            if (isImeSessionAvailableLocked()) {
                if (event instanceof KeyEvent) {
                    KeyEvent keyEvent = (KeyEvent) event;
                    if (keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 63 && keyEvent.getRepeatCount() == 0) {
                        showInputMethodPickerLocked();
                        return 1;
                    }
                    if (keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 1103 && keyEvent.getRepeatCount() == 0) {
                        handleVoiceHWKey();
                        return 1;
                    }
                }
                if (DEBUG) {
                    Log.m100v(TAG, "DISPATCH INPUT EVENT: " + this.mCurBindState.mImeSession);
                }
                PendingEvent p = obtainPendingEventLocked(event, token, this.mCurBindState.mImeId, callback, handler);
                if (this.mMainLooper.isCurrentThread()) {
                    return sendInputEventOnMainLooperLocked(p);
                }
                Message msg = this.f584mH.obtainMessage(5, p);
                msg.setAsynchronous(true);
                this.f584mH.sendMessage(msg);
                return -1;
            }
            if (DEBUG) {
                Log.m98i(TAG, "dispatchInputEvent: mCurBindState or mCurBindState.mImeSession is null.");
            }
            return 0;
        }
    }

    public void dispatchKeyEventFromInputMethod(View targetView, KeyEvent event) {
        ViewRootImpl viewRootImpl;
        View servedView;
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(targetView);
        if (fallbackImm != null) {
            fallbackImm.dispatchKeyEventFromInputMethod(targetView, event);
            return;
        }
        synchronized (this.f584mH) {
            if (targetView == null) {
                viewRootImpl = null;
            } else {
                try {
                    viewRootImpl = targetView.getViewRootImpl();
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (viewRootImpl == null && (servedView = getServedViewLocked()) != null) {
                viewRootImpl = servedView.getViewRootImpl();
            }
            if (viewRootImpl != null) {
                viewRootImpl.dispatchKeyFromIme(event);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendInputEventAndReportResultOnMainLooper(PendingEvent p) {
        synchronized (this.f584mH) {
            int result = sendInputEventOnMainLooperLocked(p);
            if (result == -1) {
                return;
            }
            boolean z = true;
            if (result != 1) {
                z = false;
            }
            boolean handled = z;
            invokeFinishedInputEventCallback(p, handled);
        }
    }

    private int sendInputEventOnMainLooperLocked(PendingEvent p) {
        InputChannel inputChannel = this.mCurChannel;
        if (inputChannel != null) {
            if (this.mCurSender == null) {
                this.mCurSender = new ImeInputEventSender(inputChannel, this.f584mH.getLooper());
            }
            InputEvent event = p.mEvent;
            int seq = event.getSequenceNumber();
            if (this.mCurSender.sendInputEvent(seq, event)) {
                this.mPendingEvents.put(seq, p);
                Trace.traceCounter(4L, PENDING_EVENT_COUNTER, this.mPendingEvents.size());
                Message msg = this.f584mH.obtainMessage(6, seq, 0, p);
                msg.setAsynchronous(true);
                this.f584mH.sendMessageDelayed(msg, INPUT_METHOD_NOT_RESPONDING_TIMEOUT);
                return -1;
            }
            if (sPreventImeStartupUnlessTextEditor) {
                Log.m94d(TAG, "Dropping event because IME is evicted: " + event);
            } else {
                Log.m102w(TAG, "Unable to send input event to IME: " + getImeIdLocked() + " dropping: " + event);
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishedInputEvent(int seq, boolean handled, boolean timeout) {
        synchronized (this.f584mH) {
            int index = this.mPendingEvents.indexOfKey(seq);
            if (index < 0) {
                return;
            }
            PendingEvent p = this.mPendingEvents.valueAt(index);
            this.mPendingEvents.removeAt(index);
            Trace.traceCounter(4L, PENDING_EVENT_COUNTER, this.mPendingEvents.size());
            if (timeout) {
                Log.m102w(TAG, "Timeout waiting for IME to handle input event after 2500 ms: " + p.mInputMethodId);
            } else {
                this.f584mH.removeMessages(6, p);
            }
            invokeFinishedInputEventCallback(p, handled);
        }
    }

    private void invokeFinishedInputEventCallback(PendingEvent p, boolean handled) {
        p.mHandled = handled;
        if (p.mHandler.getLooper().isCurrentThread()) {
            p.run();
            return;
        }
        Message msg = Message.obtain(p.mHandler, p);
        msg.setAsynchronous(true);
        msg.sendToTarget();
    }

    private void flushPendingEventsLocked() {
        this.f584mH.removeMessages(7);
        int count = this.mPendingEvents.size();
        for (int i = 0; i < count; i++) {
            int seq = this.mPendingEvents.keyAt(i);
            Message msg = this.f584mH.obtainMessage(7, seq, 0);
            msg.setAsynchronous(true);
            msg.sendToTarget();
        }
    }

    private PendingEvent obtainPendingEventLocked(InputEvent event, Object token, String inputMethodId, FinishedInputEventCallback callback, Handler handler) {
        PendingEvent p = this.mPendingEventPool.acquire();
        if (p == null) {
            p = new PendingEvent();
        }
        p.mEvent = event;
        p.mToken = token;
        p.mInputMethodId = inputMethodId;
        p.mCallback = callback;
        p.mHandler = handler;
        return p;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recyclePendingEventLocked(PendingEvent p) {
        p.recycle();
        this.mPendingEventPool.release(p);
    }

    public void showInputMethodPicker() {
        synchronized (this.f584mH) {
            showInputMethodPickerLocked();
        }
    }

    public void showInputMethodPickerFromSystem(boolean showAuxiliarySubtypes, int displayId) {
        int mode;
        if (showAuxiliarySubtypes) {
            mode = 1;
        } else {
            mode = 2;
        }
        IInputMethodManagerGlobalInvoker.showInputMethodPickerFromSystem(mode, displayId);
    }

    private void showInputMethodPickerLocked() {
        Log.m94d(TAG, "showInputMethodPickerLocked");
        IInputMethodManagerGlobalInvoker.showInputMethodPickerFromClient(this.mClient, 0);
    }

    public boolean isInputMethodPickerShown() {
        return IInputMethodManagerGlobalInvoker.isInputMethodPickerShownForTest();
    }

    public boolean hasPendingImeVisibilityRequests() {
        return IInputMethodManagerGlobalInvoker.hasPendingImeVisibilityRequests();
    }

    public void showInputMethodAndSubtypeEnabler(String imiId) {
        Context context = null;
        synchronized (this.f584mH) {
            ViewRootImpl viewRootImpl = this.mCurRootView;
            if (viewRootImpl != null) {
                context = viewRootImpl.mContext;
            }
        }
        if (context == null) {
            Context appContext = ActivityThread.currentApplication();
            DisplayManager displayManager = (DisplayManager) appContext.getSystemService(DisplayManager.class);
            context = appContext.createDisplayContext(displayManager.getDisplay(this.mDisplayId));
        }
        Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS);
        intent.setFlags(TvSettingsEnums.PRIVACY_DIAGNOSTICS);
        if (!TextUtils.isEmpty(imiId)) {
            intent.putExtra(Settings.EXTRA_INPUT_METHOD_ID, imiId);
        }
        context.startActivity(intent);
    }

    public InputMethodSubtype getCurrentInputMethodSubtype() {
        return IInputMethodManagerGlobalInvoker.getCurrentInputMethodSubtype(UserHandle.myUserId());
    }

    @Deprecated
    public boolean setCurrentInputMethodSubtype(InputMethodSubtype subtype) {
        Context fallbackContext;
        if (Process.myUid() == 1000) {
            Log.m102w(TAG, "System process should not call setCurrentInputMethodSubtype() because almost always it is a bug under multi-user / multi-profile environment. Consider directly interacting with InputMethodManagerService via LocalServices.");
            return false;
        }
        if (subtype == null || (fallbackContext = ActivityThread.currentApplication()) == null || fallbackContext.checkSelfPermission(Manifest.permission.WRITE_SECURE_SETTINGS) != 0) {
            return false;
        }
        ContentResolver contentResolver = fallbackContext.getContentResolver();
        String imeId = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD);
        if (ComponentName.unflattenFromString(imeId) == null) {
            return false;
        }
        List<InputMethodSubtype> enabledSubtypes = IInputMethodManagerGlobalInvoker.getEnabledInputMethodSubtypeList(imeId, true, UserHandle.myUserId());
        int numSubtypes = enabledSubtypes.size();
        for (int i = 0; i < numSubtypes; i++) {
            InputMethodSubtype enabledSubtype = enabledSubtypes.get(i);
            if (enabledSubtype.equals(subtype)) {
                Settings.Secure.putInt(contentResolver, Settings.Secure.SELECTED_INPUT_METHOD_SUBTYPE, enabledSubtype.hashCode());
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public void notifyUserAction() {
        Log.m102w(TAG, "notifyUserAction() is a hidden method, which is now just a stub method that does nothing.  Leave comments in b.android.com/114740982 if your  application still depends on the previous behavior of this method.");
    }

    public Map<InputMethodInfo, List<InputMethodSubtype>> getShortcutInputMethodsAndSubtypes() {
        List<InputMethodInfo> enabledImes = getEnabledInputMethodList();
        enabledImes.sort(Comparator.comparingInt(new ToIntFunction() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return InputMethodManager.lambda$getShortcutInputMethodsAndSubtypes$4((InputMethodInfo) obj);
            }
        }));
        int numEnabledImes = enabledImes.size();
        for (int imiIndex = 0; imiIndex < numEnabledImes; imiIndex++) {
            InputMethodInfo imi = enabledImes.get(imiIndex);
            List<InputMethodSubtype> subtypes = getEnabledInputMethodSubtypeList(imi, true);
            int subtypeCount = subtypes.size();
            for (int subtypeIndex = 0; subtypeIndex < subtypeCount; subtypeIndex++) {
                InputMethodSubtype subtype = imi.getSubtypeAt(subtypeIndex);
                if (SUBTYPE_MODE_VOICE.equals(subtype.getMode())) {
                    return Collections.singletonMap(imi, Collections.singletonList(subtype));
                }
            }
        }
        return Collections.emptyMap();
    }

    static /* synthetic */ int lambda$getShortcutInputMethodsAndSubtypes$4(InputMethodInfo inputMethodInfo) {
        return !inputMethodInfo.isSystem() ? 1 : 0;
    }

    public int getInputMethodWindowVisibleHeight() {
        return IInputMethodManagerGlobalInvoker.getInputMethodWindowVisibleHeight(this.mClient);
    }

    public void setRequestCursorUpdateDisplayIdCheck(boolean enabled) {
        this.mRequestCursorUpdateDisplayIdCheck.set(enabled);
    }

    public void reportVirtualDisplayGeometry(int childDisplayId, Matrix matrix) {
        float[] matrixValues;
        if (matrix == null) {
            matrixValues = null;
        } else {
            matrixValues = new float[9];
            matrix.getValues(matrixValues);
        }
        IInputMethodManagerGlobalInvoker.reportVirtualDisplayGeometryAsync(this.mClient, childDisplayId, matrixValues);
    }

    public boolean hasVirtualDisplayToScreenMatrix() {
        boolean z;
        synchronized (this.f584mH) {
            z = this.mVirtualDisplayToScreenMatrix != null;
        }
        return z;
    }

    @Deprecated
    public boolean switchToLastInputMethod(IBinder imeToken) {
        return InputMethodPrivilegedOperationsRegistry.get(imeToken).switchToPreviousInputMethod();
    }

    @Deprecated
    public boolean switchToNextInputMethod(IBinder imeToken, boolean onlyCurrentIme) {
        return InputMethodPrivilegedOperationsRegistry.get(imeToken).switchToNextInputMethod(onlyCurrentIme);
    }

    @Deprecated
    public boolean shouldOfferSwitchingToNextInputMethod(IBinder imeToken) {
        return InputMethodPrivilegedOperationsRegistry.get(imeToken).shouldOfferSwitchingToNextInputMethod();
    }

    @Deprecated
    public void setAdditionalInputMethodSubtypes(String imiId, InputMethodSubtype[] subtypes) {
        IInputMethodManagerGlobalInvoker.setAdditionalInputMethodSubtypes(imiId, subtypes, UserHandle.myUserId());
    }

    public void setExplicitlyEnabledInputMethodSubtypes(String imiId, int[] subtypeHashCodes) {
        IInputMethodManagerGlobalInvoker.setExplicitlyEnabledInputMethodSubtypes(imiId, subtypeHashCodes, UserHandle.myUserId());
    }

    public InputMethodSubtype getLastInputMethodSubtype() {
        return IInputMethodManagerGlobalInvoker.getLastInputMethodSubtype(UserHandle.myUserId());
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDump(FileDescriptor fd, PrintWriter fout, String[] args) {
        if (processDump(fd, args)) {
            return;
        }
        Printer p = new PrintWriterPrinter(fout);
        p.println("Input method client state for " + this + ":");
        p.println("  mFallbackInputConnection=" + this.mFallbackInputConnection);
        p.println("  mActive=" + this.mActive + " mRestartOnNextWindowFocus=" + this.mRestartOnNextWindowFocus + " mBindSequence=" + getBindSequenceLocked() + " mCurImeId=" + getImeIdLocked());
        p.println("  mFullscreenMode=" + this.mFullscreenMode);
        if (isImeSessionAvailableLocked()) {
            p.println("  mCurMethod=" + this.mCurBindState.mImeSession);
        } else {
            p.println("  mCurMethod= null");
        }
        for (int i = 0; i < this.mAccessibilityInputMethodSession.size(); i++) {
            p.println("  mAccessibilityInputMethodSession(" + this.mAccessibilityInputMethodSession.keyAt(i) + ")=" + this.mAccessibilityInputMethodSession.valueAt(i));
        }
        p.println("  mCurRootView=" + this.mCurRootView);
        p.println("  mServedView=" + getServedViewLocked());
        p.println("  mNextServedView=" + getNextServedViewLocked());
        p.println("  mServedConnecting=" + this.mServedConnecting);
        if (this.mCurrentEditorInfo != null) {
            p.println("  mCurrentEditorInfo:");
            this.mCurrentEditorInfo.dump(p, "    ", false);
        } else {
            p.println("  mCurrentEditorInfo: null");
        }
        p.println("  mServedInputConnection=" + this.mServedInputConnection);
        p.println("  mServedInputConnectionHandler=" + this.mServedInputConnectionHandler);
        p.println("  mCompletions=" + Arrays.toString(this.mCompletions));
        p.println("  mCursorRect=" + this.mCursorRect);
        p.println("  mCursorSelStart=" + this.mCursorSelStart + " mCursorSelEnd=" + this.mCursorSelEnd + " mCursorCandStart=" + this.mCursorCandStart + " mCursorCandEnd=" + this.mCursorCandEnd);
    }

    private final class ImeInputEventSender extends InputEventSender {
        public ImeInputEventSender(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventSender
        public void onInputEventFinished(int seq, boolean handled) {
            InputMethodManager.this.finishedInputEvent(seq, handled, false);
        }
    }

    private final class PendingEvent implements Runnable {
        public FinishedInputEventCallback mCallback;
        public InputEvent mEvent;
        public boolean mHandled;
        public Handler mHandler;
        public String mInputMethodId;
        public Object mToken;

        private PendingEvent() {
        }

        public void recycle() {
            this.mEvent = null;
            this.mToken = null;
            this.mInputMethodId = null;
            this.mCallback = null;
            this.mHandler = null;
            this.mHandled = false;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mCallback.onFinishedInputEvent(this.mToken, this.mHandled);
            synchronized (InputMethodManager.this.f584mH) {
                InputMethodManager.this.recyclePendingEventLocked(this);
            }
        }
    }

    private static final class BindState {
        final int mBindSequence;
        final String mImeId;
        final IInputMethodSessionInvoker mImeSession;
        final boolean mIsInputMethodSuppressingSpellChecker;

        BindState(InputBindResult inputBindResult) {
            this.mImeSession = IInputMethodSessionInvoker.createOrNull(inputBindResult.method);
            this.mIsInputMethodSuppressingSpellChecker = inputBindResult.isInputMethodSuppressingSpellChecker;
            this.mImeId = inputBindResult.f684id;
            this.mBindSequence = inputBindResult.sequence;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isImeSessionAvailableLocked() {
        BindState bindState = this.mCurBindState;
        return (bindState == null || bindState.mImeSession == null) ? false : true;
    }

    private String getImeIdLocked() {
        BindState bindState = this.mCurBindState;
        if (bindState != null) {
            return bindState.mImeId;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBindSequenceLocked() {
        BindState bindState = this.mCurBindState;
        if (bindState != null) {
            return bindState.mBindSequence;
        }
        return -1;
    }

    private boolean processDump(FileDescriptor fd, String[] args) {
        if (args == null) {
            return false;
        }
        for (String arg : args) {
            if (arg.equals(ImeTracing.PROTO_ARG)) {
                ProtoOutputStream proto = new ProtoOutputStream(fd);
                dumpDebug(proto, null);
                proto.flush();
                return true;
            }
        }
        return false;
    }

    public void dumpDebug(ProtoOutputStream proto, byte[] icProto) {
        if (!isImeSessionAvailableLocked()) {
            return;
        }
        proto.write(1120986464257L, this.mDisplayId);
        long token = proto.start(1146756268034L);
        synchronized (this.f584mH) {
            proto.write(1138166333441L, this.mCurBindState.mImeId);
            proto.write(1133871366146L, this.mFullscreenMode);
            proto.write(1133871366148L, this.mActive);
            proto.write(1133871366149L, this.mServedConnecting);
            proto.write(1138166333446L, Objects.toString(this.mServedView));
            proto.write(1138166333447L, Objects.toString(this.mNextServedView));
            proto.end(token);
            ViewRootImpl viewRootImpl = this.mCurRootView;
            if (viewRootImpl != null) {
                viewRootImpl.dumpDebug(proto, 1146756268035L);
            }
            EditorInfo editorInfo = this.mCurrentEditorInfo;
            if (editorInfo != null) {
                editorInfo.dumpDebug(proto, 1146756268038L);
            }
            ImeInsetsSourceConsumer imeInsetsSourceConsumer = this.mImeInsetsConsumer;
            if (imeInsetsSourceConsumer != null) {
                imeInsetsSourceConsumer.dumpDebug(proto, 1146756268037L);
            }
            RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
            if (remoteInputConnectionImpl != null) {
                remoteInputConnectionImpl.dumpDebug(proto, 1146756268040L);
            }
            if (icProto != null) {
                proto.write(1146756268041L, icProto);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forAccessibilitySessionsLocked(Consumer<IAccessibilityInputMethodSessionInvoker> consumer) {
        for (int i = 0; i < this.mAccessibilityInputMethodSession.size(); i++) {
            consumer.accept(this.mAccessibilityInputMethodSession.valueAt(i));
        }
    }

    private static Pair<InputConnection, EditorInfo> createInputConnection(View servedView) {
        EditorInfo editorInfo = new EditorInfo();
        editorInfo.packageName = servedView.getContext().getOpPackageName();
        editorInfo.autofillId = servedView.getAutofillId();
        editorInfo.fieldId = servedView.getId();
        InputConnection ic = servedView.onCreateInputConnection(editorInfo);
        if (DEBUG) {
            Log.m100v(TAG, "Starting input: editorInfo=" + editorInfo + " ic=" + ic);
        }
        if (ic == null) {
            editorInfo.autofillId = AutofillId.NO_AUTOFILL_ID;
            editorInfo.fieldId = 0;
        }
        return new Pair<>(ic, editorInfo);
    }

    public void semShowSoftInput(int flags, ResultReceiver resultReceiver) {
        Log.m98i(TAG_LIFE_CYCLE, "semShowSoftInput - flag : " + flags);
        showSoftInputUnchecked(flags, resultReceiver);
    }

    public boolean semIsInputMethodShown() {
        return isInputMethodShown();
    }

    public boolean isInputMethodShown() {
        return IInputMethodManagerGlobalInvoker.isInputMethodShown();
    }

    public boolean semMinimizeSoftInput(IBinder windowToken, int height) {
        Log.m98i(TAG_LIFE_CYCLE, "semMinimizeSoftInput");
        return minimizeSoftInput(windowToken, height);
    }

    public boolean minimizeSoftInput(IBinder windowToken, int height) {
        Log.m98i(TAG_LIFE_CYCLE, "minimizeSoftInput h " + height);
        checkFocus();
        synchronized (this.f584mH) {
            View servedView = getServedViewLocked();
            if (servedView != null && servedView.getWindowToken() == windowToken) {
                return IInputMethodManagerGlobalInvoker.minimizeSoftInput(this.mClient, height);
            }
            Log.m98i(TAG, "minimizeSoftInput: cancel windowToken=" + (windowToken == null ? SemCapabilities.FEATURE_TAG_NULL : windowToken) + " servedView=" + (servedView == null ? SemCapabilities.FEATURE_TAG_NULL : InputMethodDebug.dumpViewInfo(servedView)) + " mDisplayId=" + this.mDisplayId);
            return false;
        }
    }

    public boolean semForceHideSoftInput() {
        if (DEBUG_SEP) {
            Log.m98i(TAG_LIFE_CYCLE, "semForceHideSoftInput");
        }
        return forceHideSoftInput();
    }

    public boolean forceHideSoftInput() {
        if (DEBUG_SEP) {
            Log.m98i(TAG_LIFE_CYCLE, "forceHideSoftInput");
        }
        return forceHideSoftInput(null);
    }

    public boolean forceHideSoftInput(ResultReceiver resultReceiver) {
        Log.m98i(TAG_LIFE_CYCLE, "fhsi()");
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.m94d(TAG, "forceHideSoftInput callers=" + Debug.getCallers(10));
        }
        ImeTracker.Token statsToken = ImeTracker.forLogging().onRequestHide(null, Process.myUid(), 2, 42);
        ImeTracker.forLatency().onRequestHide(statsToken, 2, 42, new InsetsController$$ExternalSyntheticLambda2());
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#forceHideSoftInput", this, null);
        synchronized (this.f584mH) {
            ImeTracker.forLogging().onProgress(statsToken, 1);
            View servedView = getServedViewLocked();
            if (servedView == null) {
                return IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, null, statsToken, 0, resultReceiver, 42);
            }
            return IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, servedView.getWindowToken(), statsToken, 0, resultReceiver, 42);
        }
    }

    public boolean semIsAccessoryKeyboard() {
        return isAccessoryKeyboardState() > 0;
    }

    public int isAccessoryKeyboardState() {
        return IInputMethodManagerGlobalInvoker.isAccessoryKeyboard();
    }

    public boolean getWACOMPen() {
        return IInputMethodManagerGlobalInvoker.getWACOMPen();
    }

    public void undoMinimizeSoftInput() {
        IInputMethodManagerGlobalInvoker.undoMinimizeSoftInput();
    }

    public void dismissAndShowAgainInputMethodPicker() {
        synchronized (this.f584mH) {
            dismissAndShowAgainInputMethodPickerLocked();
        }
    }

    private void dismissAndShowAgainInputMethodPickerLocked() {
        IInputMethodManagerGlobalInvoker.dismissAndShowAgainInputMethodPicker();
    }

    public boolean isCurrentInputMethodAsSamsungKeyboard() {
        boolean isCurrentInputMethodAsSamsungKeyboard;
        synchronized (this.f584mH) {
            isCurrentInputMethodAsSamsungKeyboard = IInputMethodManagerGlobalInvoker.isCurrentInputMethodAsSamsungKeyboard();
        }
        return isCurrentInputMethodAsSamsungKeyboard;
    }

    public int getCurrentFocusDisplayID() {
        return IInputMethodManagerGlobalInvoker.getCurrentFocusDisplayID();
    }

    public int getCurTokenDisplayId() {
        return IInputMethodManagerGlobalInvoker.getCurTokenDisplayId();
    }

    private void updateIMESwitchEnable() {
        View servedView = getServedViewLocked();
        if (servedView != null && servedView.getContext() != null) {
            boolean switchDisable = servedView.getContext().getPackageName().contains("com.samsung.android.honeyboard");
            IInputMethodManagerGlobalInvoker.setInputMethodSwitchDisable(this.mClient, switchDisable);
        } else {
            IInputMethodManagerGlobalInvoker.setInputMethodSwitchDisable(this.mClient, false);
        }
    }

    public boolean getDexSettingsValue(String key, String defaultKey) {
        Log.m94d(TAG, "getDexSettingsValue");
        return IInputMethodManagerGlobalInvoker.getDexSettingsValue(key, defaultKey);
    }

    private void handleVoiceHWKey() {
        checkFocus();
        View view = getServedViewLocked();
        if (view == null) {
            Log.m94d(TAG, "handleVoiceHWKey: have no served view");
        } else if (!hasActiveInputConnection(view)) {
            Log.m94d(TAG, "handleVoiceHWKey: have no active input connection");
        } else {
            IInputMethodManagerGlobalInvoker.handleVoiceHWKey();
        }
    }
}
