package android.view.inputmethod;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.Application;
import android.app.PropertyInvalidatedCache;
import android.app.compat.CompatChanges;
import android.app.tvsettings.TvSettingsEnums;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.scontext.SContextConstants;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.ResultReceiver;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.style.SuggestionSpan;
import android.util.Log;
import android.util.Pair;
import android.util.Pools;
import android.util.PrintWriterPrinter;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.ImeFocusController;
import android.view.ImeInsetsSourceConsumer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventSender;
import android.view.InsetsController;
import android.view.InsetsController$$ExternalSyntheticLambda2;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.window.ImeOnBackInvokedDispatcher;
import android.window.WindowOnBackInvokedDispatcher;
import com.android.internal.inputmethod.IBooleanListener;
import com.android.internal.inputmethod.IConnectionlessHandwritingCallback;
import com.android.internal.inputmethod.IInputMethodClient;
import com.android.internal.inputmethod.IInputMethodSession;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.inputmethod.InputBindResult;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry;
import com.android.internal.os.SomeArgs;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.view.IInputMethodManager;
import com.samsung.android.rune.ViewRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
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
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/* loaded from: classes4.dex */
public final class InputMethodManager {
    private static final long ALWAYS_RETURN_TRUE_HIDE_SOFT_INPUT_FROM_WINDOW = 395521150;
    private static final String CACHE_KEY_CONNECTIONLESS_STYLUS_HANDWRITING_PROPERTY = "cache_key.system_server.connectionless_stylus_handwriting";
    private static final String CACHE_KEY_STYLUS_HANDWRITING_PROPERTY = "cache_key.system_server.stylus_handwriting";
    public static final long CLEAR_SHOW_FORCED_FLAG_WHEN_LEAVING = 214016041;
    private static final boolean DEBUG;
    static final boolean DEBUG_SEP;
    public static final int DISPATCH_HANDLED = 1;
    public static final int DISPATCH_IN_PROGRESS = -1;
    public static final int DISPATCH_NOT_HANDLED = 0;
    public static final int HANDWRITING_DELEGATE_FLAG_HOME_DELEGATOR_ALLOWED = 1;
    public static final int HIDE_IMPLICIT_ONLY = 1;
    public static final int HIDE_NOT_ALWAYS = 2;
    private static final long INPUT_METHOD_NOT_RESPONDING_TIMEOUT = 2500;
    static final int INVALID_SEQ_ID = -1;
    private static final int MSG_BIND = 2;
    private static final int MSG_BIND_ACCESSIBILITY_SERVICE = 11;
    private static final int MSG_DUMP = 1;
    private static final int MSG_FLUSH_INPUT_EVENT = 7;
    private static final int MSG_ON_SHOW_REQUESTED = 31;
    private static final int MSG_REPORT_FULLSCREEN_MODE = 10;
    private static final int MSG_SEND_INPUT_EVENT = 5;
    private static final int MSG_SET_ACTIVE = 4;
    private static final int MSG_SET_INTERACTIVE = 13;
    private static final int MSG_SET_VISIBILITY = 14;
    private static final int MSG_START_INPUT_RESULT = 40;
    private static final int MSG_TIMEOUT_INPUT_EVENT = 6;
    private static final int MSG_UNBIND = 3;
    private static final int MSG_UNBIND_ACCESSIBILITY_SERVICE = 12;
    private static final int NOT_A_SUBTYPE_ID = -1;
    private static final boolean OPTIMIZE_NONEDITABLE_VIEWS;
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
    private static final long USE_ASYNC_SHOW_HIDE_METHOD = 352594277;
    private static Bundle mWtSelectionInfo;

    @Deprecated
    static InputMethodManager sInstance;
    private static final SparseArray<InputMethodManager> sInstanceMap;
    private static final Object sLock;
    private static boolean sPreventImeStartupUnlessTextEditor;
    private final SparseArray<IAccessibilityInputMethodSessionInvoker> mAccessibilityInputMethodSession;
    private final boolean mAsyncShowHideMethodEnabled;
    private final IInputMethodClient.Stub mClient;
    private CompletionInfo[] mCompletions;
    private PropertyInvalidatedCache<Integer, Boolean> mConnectionlessStylusHandwritingAvailableCache;
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
    private CursorAnchorInfo mCursorAnchorInfo;
    private int mCursorCandEnd;
    private int mCursorCandStart;
    private int mCursorSelEnd;
    private int mCursorSelStart;
    private final DelegateImpl mDelegate;
    private final int mDisplayId;
    private final RemoteInputConnectionImpl mFallbackInputConnection;
    private boolean mFullscreenMode;
    final H mH;
    private ImeInsetsSourceConsumer mImeInsetsConsumer;
    private boolean mImeSwitched;
    private int mInitialSelEnd;
    private int mInitialSelStart;
    boolean mIsShowRequested;
    private final Looper mMainLooper;
    private View mNextServedView;
    private final Pools.Pool<PendingEvent> mPendingEventPool;
    private final SparseArray<PendingEvent> mPendingEvents;
    private ViewFocusParameterInfo mPreviousViewFocusParameters;
    private ReportInputConnectionOpenedRunner mReportInputConnectionOpenedRunner;
    final AtomicBoolean mRequestCursorUpdateDisplayIdCheck;

    @Deprecated
    private int mRequestUpdateCursorAnchorInfoMonitorMode;
    private boolean mServedConnecting;
    private RemoteInputConnectionImpl mServedInputConnection;
    private Handler mServedInputConnectionHandler;
    private View mServedView;

    @Deprecated
    final IInputMethodManager mService;
    private PropertyInvalidatedCache<Integer, Boolean> mStylusHandwritingAvailableCache;
    private boolean mWasFullscreenMode;
    private final ImeOnBackInvokedDispatcher mImeDispatcher = new ImeOnBackInvokedDispatcher(Handler.getMain()) { // from class: android.view.inputmethod.InputMethodManager.1
        @Override // android.window.ImeOnBackInvokedDispatcher
        public WindowOnBackInvokedDispatcher getReceivingDispatcher() {
            WindowOnBackInvokedDispatcher onBackInvokedDispatcher;
            synchronized (InputMethodManager.this.mH) {
                onBackInvokedDispatcher = InputMethodManager.this.mCurRootView != null ? InputMethodManager.this.mCurRootView.getOnBackInvokedDispatcher() : null;
            }
            return onBackInvokedDispatcher;
        }
    };
    private boolean mActive = false;
    private boolean mRestartOnNextWindowFocus = true;
    private int mLastPendingStartSeqId = -1;
    Rect mTmpCursorRect = new Rect();
    Rect mCursorRect = new Rect();

    public interface FinishedInputEventCallback {
        void onFinishedInputEvent(Object obj, boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HandwritingDelegateFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HideFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowFlags {
    }

    private static boolean isInEditMode() {
        return false;
    }

    @Deprecated
    public boolean isWatchingCursor(View view) {
        return false;
    }

    @Deprecated
    public void windowDismissed(IBinder iBinder) {
    }

    static {
        boolean z = !SystemProperties.getBoolean("ro.product_ship", true);
        DEBUG = z;
        DEBUG_SEP = z;
        sLock = new Object();
        sInstanceMap = new SparseArray<>();
        OPTIMIZE_NONEDITABLE_VIEWS = SystemProperties.getBoolean("debug.imm.optimize_noneditable_views", true);
    }

    static class Rune {
        static final boolean SUPPORT_SEP_TOGGLE_SOFT_INPUT = true;

        Rune() {
        }
    }

    private static abstract class ReportInputConnectionOpenedRunner implements Runnable {
        int mSequenceNum;

        ReportInputConnectionOpenedRunner(int i) {
            this.mSequenceNum = i;
        }
    }

    public static void ensureDefaultInstanceForDefaultDisplayIfNecessary() {
        if (ActivityThread.isSystem()) {
            return;
        }
        forContextInternal(0, Looper.getMainLooper());
    }

    public static void invalidateLocalStylusHandwritingAvailabilityCaches() {
        PropertyInvalidatedCache.invalidateCache(CACHE_KEY_STYLUS_HANDWRITING_PROPERTY);
    }

    public static void invalidateLocalConnectionlessStylusHandwritingAvailabilityCaches() {
        PropertyInvalidatedCache.invalidateCache(CACHE_KEY_CONNECTIONLESS_STYLUS_HANDWRITING_PROPERTY);
    }

    private static boolean isAutofillUIShowing(View view) {
        AutofillManager autofillManager = (AutofillManager) view.getContext().getSystemService(AutofillManager.class);
        return autofillManager != null && autofillManager.isAutofillUiShowing();
    }

    private InputMethodManager getFallbackInputMethodManagerIfNecessary(View view) {
        ViewRootImpl viewRootImpl;
        int displayId;
        if (view == null || (viewRootImpl = view.getViewRootImpl()) == null || (displayId = viewRootImpl.getDisplayId()) == this.mDisplayId) {
            return null;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) viewRootImpl.mContext.getSystemService(InputMethodManager.class);
        if (inputMethodManager == null) {
            Log.v(TAG, "b/117267690: Failed to get non-null fallback IMM. view=" + view);
            return null;
        }
        if (inputMethodManager.mDisplayId != displayId) {
            Log.v(TAG, "b/117267690: Failed to get fallback IMM with expected displayId=" + displayId + " actual IMM#displayId=" + inputMethodManager.mDisplayId + " view=" + view);
            return null;
        }
        Log.v(TAG, "b/117267690: Display ID mismatch found. ViewRootImpl displayId=" + displayId + " InputMethodManager displayId=" + this.mDisplayId + ". Use the right InputMethodManager instance to avoid performance overhead.", new Throwable());
        return inputMethodManager;
    }

    Context getFallbackContextFromServedView() {
        synchronized (this.mH) {
            if (this.mCurRootView == null) {
                return null;
            }
            View view = this.mServedView;
            return view != null ? view.getContext() : null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean canStartInput(View view) {
        return view.hasWindowFocus() || isAutofillUIShowing(view);
    }

    public void reportPerceptible(IBinder iBinder, boolean z) {
        IInputMethodManagerGlobalInvoker.reportPerceptibleAsync(iBinder, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasViewImeRequestedVisible(View view) {
        return (!Flags.refactorInsetsController() || view == null || view.getWindowInsetsController() == null || (view.getWindowInsetsController().getRequestedVisibleTypes() & WindowInsets.Type.ime()) == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final class DelegateImpl implements ImeFocusController.InputMethodManagerDelegate {
        private DelegateImpl() {
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onPreWindowGainedFocus(ViewRootImpl viewRootImpl) {
            synchronized (InputMethodManager.this.mH) {
                setCurrentRootViewLocked(viewRootImpl);
                InputMethodManager.this.mCurRootViewWindowFocused = true;
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onPostWindowGainedFocus(View view, WindowManager.LayoutParams layoutParams) {
            boolean z;
            boolean z2;
            int i;
            int i2;
            int i3;
            synchronized (InputMethodManager.this.mH) {
                z = true;
                InputMethodManager.this.onViewFocusChangedInternal(view, true);
                z2 = InputMethodManager.this.mServedView == view && !InputMethodManager.this.hasActiveInputConnectionInternal(view);
            }
            int i4 = layoutParams.softInputMode;
            int i5 = layoutParams.flags;
            int startInputFlags = InputMethodManager.this.getStartInputFlags(view, 0) | 8;
            ImeTracing.getInstance().triggerClientDump("InputMethodManager.DelegateImpl#startInputAsyncOnWindowFocusGain", InputMethodManager.this, null);
            synchronized (InputMethodManager.this.mH) {
                if (InputMethodManager.this.mCurRootView == null) {
                    return;
                }
                if (InputMethodManager.this.mRestartOnNextWindowFocus) {
                    if (InputMethodManager.DEBUG) {
                        Log.v(InputMethodManager.TAG, "Restarting due to mRestartOnNextWindowFocus as true");
                    }
                    InputMethodManager.this.mRestartOnNextWindowFocus = false;
                } else {
                    z = z2;
                }
                InputMethodManager inputMethodManager = InputMethodManager.this;
                boolean checkFocusInternalLocked = inputMethodManager.checkFocusInternalLocked(z, inputMethodManager.mCurRootView);
                if (checkFocusInternalLocked) {
                    i = startInputFlags;
                    i2 = i4;
                    i3 = i5;
                    if (InputMethodManager.this.startInputOnWindowFocusGainInternal(1, view, i, i2, i3)) {
                        return;
                    }
                } else {
                    i = startInputFlags;
                    i2 = i4;
                    i3 = i5;
                }
                synchronized (InputMethodManager.this.mH) {
                    if (InputMethodManager.DEBUG) {
                        Log.v(InputMethodManager.TAG, "Reporting focus gain, without startInput");
                    }
                    boolean hasViewImeRequestedVisible = InputMethodManager.hasViewImeRequestedVisible(InputMethodManager.this.mCurRootView.getView());
                    Trace.traceBegin(32L, "IMM.startInputOrWindowGainedFocus");
                    Log.i(InputMethodManager.TAG, "startInputAsyncOnWindowFocusGain - IInputMethodManagerGlobalInvoker.startInputOrWindowGainedFocus");
                    IInputMethodManagerGlobalInvoker.startInputOrWindowGainedFocus(2, InputMethodManager.this.mClient, view.getWindowToken(), i, i2, i3, null, null, null, InputMethodManager.this.mCurRootView.mContext.getApplicationInfo().targetSdkVersion, UserHandle.myUserId(), InputMethodManager.this.mImeDispatcher, hasViewImeRequestedVisible);
                    Trace.traceEnd(32L);
                }
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onWindowLostFocus(ViewRootImpl viewRootImpl) {
            synchronized (InputMethodManager.this.mH) {
                if (InputMethodManager.this.mCurRootView == viewRootImpl) {
                    InputMethodManager.this.mCurRootViewWindowFocused = false;
                    InputMethodManager.this.clearCurRootViewIfNeeded();
                }
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onViewFocusChanged(View view, boolean z) {
            InputMethodManager.this.onViewFocusChangedInternal(view, z);
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onScheduledCheckFocus(ViewRootImpl viewRootImpl) {
            synchronized (InputMethodManager.this.mH) {
                if (InputMethodManager.this.checkFocusInternalLocked(false, viewRootImpl)) {
                    InputMethodManager.this.startInputOnWindowFocusGainInternal(3, null, 0, 0, 0);
                }
            }
        }

        @Override // android.view.ImeFocusController.InputMethodManagerDelegate
        public void onViewDetachedFromWindow(View view, ViewRootImpl viewRootImpl) {
            synchronized (InputMethodManager.this.mH) {
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
            synchronized (InputMethodManager.this.mH) {
                if (InputMethodManager.this.mCurRootView != viewRootImpl) {
                    return;
                }
                if (InputMethodManager.this.mServedView != null) {
                    if (InputMethodManager.DEBUG_SEP) {
                        Log.i(InputMethodManager.TAG, "onWindowDismissed");
                    }
                    InputMethodManager.this.finishInputLocked();
                }
                setCurrentRootViewLocked(null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCurrentRootViewLocked(ViewRootImpl viewRootImpl) {
            boolean z = InputMethodManager.this.mCurRootView == null;
            if (Flags.refactorInsetsController() && !z && InputMethodManager.this.mCurRootView != viewRootImpl && SemInputMethodManagerUtils.isCurrentMockIme(InputMethodManager.this.mCurId)) {
                InputMethodManager inputMethodManager = InputMethodManager.this;
                inputMethodManager.onImeFocusLost(inputMethodManager.mCurRootView);
            }
            InputMethodManager.this.mImeDispatcher.switchRootView(InputMethodManager.this.mCurRootView, viewRootImpl);
            InputMethodManager.this.mCurRootView = viewRootImpl;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onImeFocusLost(ViewRootImpl viewRootImpl) {
        if ((viewRootImpl.mWindowAttributes.softInputMode & 15) == 3) {
            ImeTracker.Token onStart = ImeTracker.forLogging().onStart(2, 5, 58, false);
            if (DEBUG) {
                Log.d(TAG, "onImeFocusLost, hiding IME because of STATE_ALWAYS_HIDDEN");
            }
            viewRootImpl.getInsetsController().hide(WindowInsets.Type.ime(), false, onStart);
        }
    }

    public DelegateImpl getDelegate() {
        return this.mDelegate;
    }

    public boolean hasActiveInputConnection(View view) {
        boolean z;
        RemoteInputConnectionImpl remoteInputConnectionImpl;
        synchronized (this.mH) {
            z = this.mCurRootView != null && view != null && this.mServedView == view && (remoteInputConnectionImpl = this.mServedInputConnection) != null && remoteInputConnectionImpl.isAssociatedWith(view) && isImeSessionAvailableLocked();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasActiveInputConnectionInternal(View view) {
        synchronized (this.mH) {
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
    public boolean startInputOnWindowFocusGainInternal(int i, View view, int i2, int i3, int i4) {
        synchronized (this.mH) {
            this.mCurrentEditorInfo = null;
            this.mCompletions = null;
            this.mServedConnecting = true;
        }
        return startInputInner(i, view != null ? view.getWindowToken() : null, i2, i3, i4);
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
        View servedViewLocked = getServedViewLocked();
        if (servedViewLocked != view) {
            return servedViewLocked != null && servedViewLocked.checkInputConnectionProxy(view);
        }
        return true;
    }

    class H extends Handler {
        H(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z;
            IAccessibilityInputMethodSessionInvoker createOrNull;
            int i = message.what;
            if (i == 31) {
                synchronized (InputMethodManager.this.mH) {
                    if (InputMethodManager.this.mImeInsetsConsumer != null) {
                        InputMethodManager.this.mImeInsetsConsumer.onShowRequested();
                    }
                }
                return;
            }
            RemoteInputConnectionImpl remoteInputConnectionImpl = null;
            remoteInputConnectionImpl = null;
            if (i != 40) {
                switch (i) {
                    case 1:
                        SomeArgs someArgs = (SomeArgs) message.obj;
                        try {
                            InputMethodManager.this.doDump((FileDescriptor) someArgs.arg1, (PrintWriter) someArgs.arg2, (String[]) someArgs.arg3);
                        } catch (RuntimeException e) {
                            ((PrintWriter) someArgs.arg2).println("Exception: " + e);
                        }
                        synchronized (someArgs.arg4) {
                            ((CountDownLatch) someArgs.arg4).countDown();
                        }
                        someArgs.recycle();
                        return;
                    case 2:
                        InputBindResult inputBindResult = (InputBindResult) message.obj;
                        if (InputMethodManager.DEBUG) {
                            Log.i(InputMethodManager.TAG, "handleMessage: MSG_BIND " + inputBindResult.sequence + "," + inputBindResult.id);
                        }
                        synchronized (InputMethodManager.this.mH) {
                            int bindSequenceLocked = InputMethodManager.this.getBindSequenceLocked();
                            if (bindSequenceLocked >= 0 && bindSequenceLocked == inputBindResult.sequence) {
                                InputMethodManager.this.mRequestUpdateCursorAnchorInfoMonitorMode = 0;
                                InputMethodManager.this.updateInputChannelLocked(inputBindResult.channel);
                                InputMethodManager.this.mCurMethod = inputBindResult.method;
                                InputMethodManager.this.mCurBindState = new BindState(inputBindResult);
                                InputMethodManager.this.mCurId = inputBindResult.id;
                                if (ViewRune.SUPPORT_WRITING_TOOLKIT && SemInputMethodManagerUtils.METHOD_ID_TOOLKIT_HONEYBOARD.equals(InputMethodManager.this.mCurId)) {
                                    InputMethodManager inputMethodManager = InputMethodManager.this;
                                    inputMethodManager.checkFocusInternalLocked(true, inputMethodManager.mCurRootView);
                                    InputMethodManager.this.mServedConnecting = true;
                                }
                                InputMethodManager.this.startInputInner(6, null, 0, 0, 0);
                                return;
                            }
                            Log.w(InputMethodManager.TAG, "Ignoring onBind: cur seq=" + bindSequenceLocked + ", given seq=" + inputBindResult.sequence);
                            if (inputBindResult.channel != null && inputBindResult.channel != InputMethodManager.this.mCurChannel) {
                                inputBindResult.channel.dispose();
                            }
                            return;
                        }
                    case 3:
                        int i2 = message.arg1;
                        int i3 = message.arg2;
                        if (InputMethodManager.DEBUG) {
                            Log.i(InputMethodManager.TAG, "handleMessage: MSG_UNBIND " + i2 + " reason=" + InputMethodDebug.unbindReasonToString(i3));
                        }
                        synchronized (InputMethodManager.this.mH) {
                            if (i3 == 3) {
                                try {
                                    InputMethodManager.this.mImeDispatcher.clear();
                                } finally {
                                }
                            }
                            if (i3 == 2) {
                                InputMethodManager.this.mImeSwitched = true;
                            }
                            if (InputMethodManager.this.getBindSequenceLocked() != i2) {
                                return;
                            }
                            InputMethodManager.this.clearAllAccessibilityBindingLocked();
                            InputMethodManager.this.clearBindingLocked();
                            View servedViewLocked = InputMethodManager.this.getServedViewLocked();
                            if (servedViewLocked != null && servedViewLocked.isFocused()) {
                                InputMethodManager.this.mServedConnecting = true;
                            }
                            boolean z2 = InputMethodManager.this.mActive;
                            if (z2) {
                                if (InputMethodManager.DEBUG_SEP) {
                                    Log.w(InputMethodManager.TAG_LIFE_CYCLE, "MSG_UNBIND: startInputInner is called with null IBinder ");
                                }
                                InputMethodManager.this.startInputInner(7, null, 0, 0, 0);
                                return;
                            }
                            return;
                        }
                    case 4:
                        boolean z3 = message.arg1 != 0;
                        z = message.arg2 != 0;
                        if (InputMethodManager.DEBUG) {
                            Log.i(InputMethodManager.TAG, "handleMessage: MSG_SET_ACTIVE " + z3 + ", was " + InputMethodManager.this.mActive);
                        }
                        synchronized (InputMethodManager.this.mH) {
                            InputMethodManager.this.mActive = z3;
                            InputMethodManager.this.mFullscreenMode = z;
                            if (!z3) {
                                InputMethodManager.this.mRestartOnNextWindowFocus = true;
                                InputMethodManager.this.mFallbackInputConnection.finishComposingTextFromImm();
                                if (ViewRune.SUPPORT_WRITING_TOOLKIT && SemInputMethodManagerUtils.METHOD_ID_TOOLKIT_HONEYBOARD.equals(InputMethodManager.this.mCurId) && InputMethodManager.this.mServedInputConnection != null) {
                                    InputMethodManager.this.mServedInputConnection.setWritingToolkitMode(true);
                                }
                                if (InputMethodManager.this.clearCurRootViewIfNeeded()) {
                                    return;
                                }
                            }
                            View servedViewLocked2 = InputMethodManager.this.getServedViewLocked();
                            if (servedViewLocked2 != null && InputMethodManager.canStartInput(servedViewLocked2)) {
                                if (InputMethodManager.this.mCurRootView == null) {
                                    return;
                                }
                                InputMethodManager inputMethodManager2 = InputMethodManager.this;
                                if (inputMethodManager2.checkFocusInternalLocked(inputMethodManager2.mRestartOnNextWindowFocus, InputMethodManager.this.mCurRootView)) {
                                    InputMethodManager.this.mCurrentEditorInfo = null;
                                    InputMethodManager.this.mCompletions = null;
                                    InputMethodManager.this.mServedConnecting = true;
                                    InputMethodManager.this.startInputInner(z3 ? 8 : 9, null, 0, 0, 0);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                    case 5:
                        InputMethodManager.this.sendInputEventAndReportResultOnMainLooper((PendingEvent) message.obj);
                        return;
                    case 6:
                        InputMethodManager.this.finishedInputEvent(message.arg1, false, true);
                        return;
                    case 7:
                        InputMethodManager.this.finishedInputEvent(message.arg1, false, false);
                        return;
                    default:
                        switch (i) {
                            case 10:
                                z = message.arg1 != 0;
                                synchronized (InputMethodManager.this.mH) {
                                    if (InputMethodManager.this.mFullscreenMode != z && InputMethodManager.this.mServedInputConnection != null) {
                                        remoteInputConnectionImpl = InputMethodManager.this.mServedInputConnection;
                                        InputMethodManager inputMethodManager3 = InputMethodManager.this;
                                        inputMethodManager3.mWasFullscreenMode = inputMethodManager3.mFullscreenMode;
                                        InputMethodManager.this.mFullscreenMode = z;
                                    }
                                }
                                if (remoteInputConnectionImpl != null) {
                                    remoteInputConnectionImpl.dispatchReportFullscreenMode(z);
                                    return;
                                }
                                return;
                            case 11:
                                int i4 = message.arg1;
                                InputBindResult inputBindResult2 = (InputBindResult) message.obj;
                                if (InputMethodManager.DEBUG) {
                                    Log.i(InputMethodManager.TAG, "handleMessage: MSG_BIND_ACCESSIBILITY " + inputBindResult2.sequence + "," + inputBindResult2.id);
                                }
                                synchronized (InputMethodManager.this.mH) {
                                    int bindSequenceLocked2 = InputMethodManager.this.getBindSequenceLocked();
                                    if (bindSequenceLocked2 >= 0 && bindSequenceLocked2 == inputBindResult2.sequence) {
                                        if (inputBindResult2.accessibilitySessions != null && (createOrNull = IAccessibilityInputMethodSessionInvoker.createOrNull(inputBindResult2.accessibilitySessions.get(i4))) != null) {
                                            InputMethodManager.this.mAccessibilityInputMethodSession.put(i4, createOrNull);
                                            if (InputMethodManager.this.mServedInputConnection != null) {
                                                createOrNull.updateSelection(InputMethodManager.this.mInitialSelStart, InputMethodManager.this.mInitialSelEnd, InputMethodManager.this.mCursorSelStart, InputMethodManager.this.mCursorSelEnd, InputMethodManager.this.mCursorCandStart, InputMethodManager.this.mCursorCandEnd);
                                            } else {
                                                createOrNull.updateSelection(-1, -1, -1, -1, -1, -1);
                                            }
                                        }
                                        InputMethodManager.this.startInputInner(12, null, 0, 0, 0);
                                        return;
                                    }
                                    Log.w(InputMethodManager.TAG, "Ignoring onBind: cur seq=" + bindSequenceLocked2 + ", given seq=" + inputBindResult2.sequence);
                                    if (inputBindResult2.channel != null && inputBindResult2.channel != InputMethodManager.this.mCurChannel) {
                                        inputBindResult2.channel.dispose();
                                    }
                                    return;
                                }
                            case 12:
                                int i5 = message.arg1;
                                int i6 = message.arg2;
                                if (InputMethodManager.DEBUG) {
                                    Log.i(InputMethodManager.TAG, "handleMessage: MSG_UNBIND_ACCESSIBILITY_SERVICE " + i5 + " id=" + i6);
                                }
                                synchronized (InputMethodManager.this.mH) {
                                    if (InputMethodManager.this.getBindSequenceLocked() != i5) {
                                        if (InputMethodManager.DEBUG) {
                                            Log.i(InputMethodManager.TAG, "current BindSequence =" + InputMethodManager.this.getBindSequenceLocked() + " sequence =" + i5 + " id=" + i6);
                                        }
                                        return;
                                    }
                                    InputMethodManager.this.clearAccessibilityBindingLocked(i6);
                                    return;
                                }
                            case 13:
                                boolean z4 = message.arg1 != 0;
                                z = message.arg2 != 0;
                                if (InputMethodManager.DEBUG) {
                                    Log.i(InputMethodManager.TAG, "handleMessage: MSG_SET_INTERACTIVE " + z4 + ", was " + InputMethodManager.this.mActive);
                                }
                                if (InputMethodManager.this.usingWritingToolkit()) {
                                    Log.d(InputMethodManager.TAG, "set interactive return because of using writing toolkit");
                                    return;
                                }
                                synchronized (InputMethodManager.this.mH) {
                                    InputMethodManager.this.mActive = z4;
                                    InputMethodManager.this.mFullscreenMode = z;
                                    if (z4) {
                                        View view = InputMethodManager.this.mCurRootView != null ? InputMethodManager.this.mCurRootView.getView() : null;
                                        if (view == null) {
                                            return;
                                        }
                                        final ViewRootImpl viewRootImpl = InputMethodManager.this.mCurRootView;
                                        view.post(new Runnable() { // from class: android.view.inputmethod.InputMethodManager$H$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                InputMethodManager.H.this.lambda$handleMessage$0(viewRootImpl);
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
                            case 14:
                                SomeArgs someArgs2 = (SomeArgs) message.obj;
                                boolean booleanValue = ((Boolean) someArgs2.arg1).booleanValue();
                                ImeTracker.Token token = (ImeTracker.Token) someArgs2.arg2;
                                synchronized (InputMethodManager.this.mH) {
                                    if (InputMethodManager.this.mCurRootView != null) {
                                        InsetsController insetsController = InputMethodManager.this.mCurRootView.getInsetsController();
                                        if (insetsController != null) {
                                            ImeTracker.forLogging().onProgress(token, 58);
                                            Log.i(InputMethodManager.TAG, "handleMessage: setImeVisibility visible=" + booleanValue);
                                            if (booleanValue) {
                                                insetsController.show(WindowInsets.Type.ime(), false, token);
                                            } else {
                                                insetsController.hide(WindowInsets.Type.ime(), false, token);
                                            }
                                        }
                                    } else {
                                        ImeTracker.forLogging().onFailed(token, 58);
                                    }
                                }
                                return;
                            default:
                                return;
                        }
                }
            }
            InputBindResult inputBindResult3 = (InputBindResult) message.obj;
            int i7 = message.arg1;
            synchronized (InputMethodManager.this.mH) {
                if (InputMethodManager.this.mLastPendingStartSeqId == i7) {
                    InputMethodManager.this.mLastPendingStartSeqId = -1;
                    if (InputMethodManager.DEBUG_SEP) {
                        Log.i(InputMethodManager.TAG, "handleMessage: MSG_START_INPUT_RESULT invalidate mLastPendingStartSeqId=" + InputMethodManager.this.mLastPendingStartSeqId);
                    }
                }
                if (inputBindResult3 == null) {
                    return;
                }
                if (InputMethodManager.DEBUG) {
                    Log.v(InputMethodManager.TAG, "Starting input: Bind result=" + inputBindResult3);
                }
                if (inputBindResult3.id != null) {
                    InputMethodManager.this.updateInputChannelLocked(inputBindResult3.channel);
                    InputMethodManager.this.mCurMethod = inputBindResult3.method;
                    InputMethodManager.this.mCurBindState = new BindState(inputBindResult3);
                    InputMethodManager.this.mAccessibilityInputMethodSession.clear();
                    if (inputBindResult3.accessibilitySessions != null) {
                        for (int i8 = 0; i8 < inputBindResult3.accessibilitySessions.size(); i8++) {
                            IAccessibilityInputMethodSessionInvoker createOrNull2 = IAccessibilityInputMethodSessionInvoker.createOrNull(inputBindResult3.accessibilitySessions.valueAt(i8));
                            if (createOrNull2 != null) {
                                InputMethodManager.this.mAccessibilityInputMethodSession.append(inputBindResult3.accessibilitySessions.keyAt(i8), createOrNull2);
                            }
                        }
                    }
                    InputMethodManager.this.mCurId = inputBindResult3.id;
                } else if (inputBindResult3.channel != null && inputBindResult3.channel != InputMethodManager.this.mCurChannel) {
                    inputBindResult3.channel.dispose();
                }
                if (inputBindResult3.result == 12) {
                    InputMethodManager.this.mRestartOnNextWindowFocus = true;
                    InputMethodManager.this.mServedView = null;
                }
                if (InputMethodManager.this.mCompletions != null && InputMethodManager.this.isImeSessionAvailableLocked()) {
                    InputMethodManager.this.mCurBindState.mImeSession.displayCompletions(InputMethodManager.this.mCompletions);
                }
                if (inputBindResult3 != null && inputBindResult3.method != null && InputMethodManager.this.mServedView != null && InputMethodManager.this.mReportInputConnectionOpenedRunner != null && InputMethodManager.this.mReportInputConnectionOpenedRunner.mSequenceNum == i7) {
                    InputMethodManager.this.mReportInputConnectionOpenedRunner.run();
                }
                InputMethodManager.this.mReportInputConnectionOpenedRunner = null;
                if (inputBindResult3.result == 0 && InputMethodManager.this.mImeSwitched) {
                    InputMethodManager.this.mImeSwitched = false;
                    if (InputMethodManager.this.usingWritingToolkit() && InputMethodManager.this.mServedView != null && InputMethodManager.this.mServedView.getViewRootImpl() != null && !InputMethodManager.hasViewImeRequestedVisible(InputMethodManager.this.mServedView.getViewRootImpl().getView()) && InputMethodManager.this.isImeSessionAvailableLocked()) {
                        Log.i(InputMethodManager.TAG, "actionRequestSelfShow");
                        InputMethodManager.this.mCurBindState.mImeSession.appPrivateCommand(SemInputMethodManagerUtils.ACTION_REQUEST_SELF_SHOW, null);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(ViewRootImpl viewRootImpl) {
            synchronized (InputMethodManager.this.mH) {
                if (InputMethodManager.this.mCurRootView != viewRootImpl) {
                    return;
                }
                View view = viewRootImpl.getView();
                if (view == null) {
                    return;
                }
                View findFocus = view.findFocus();
                InputMethodManager.this.onViewFocusChangedInternal(findFocus, findFocus != null);
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

    static boolean isInEditModeInternal() {
        return isInEditMode();
    }

    private static InputMethodManager createInstance(int i, Looper looper) {
        return isInEditMode() ? createStubInstance(i, looper) : createRealInstance(i, looper);
    }

    private static InputMethodManager createRealInstance(int i, Looper looper) {
        IInputMethodManager service = IInputMethodManagerGlobalInvoker.getService();
        if (service == null) {
            throw new IllegalStateException("IInputMethodManager is not available");
        }
        InputMethodManager inputMethodManager = new InputMethodManager(service, i, looper);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IInputMethodManagerGlobalInvoker.addClient(inputMethodManager.mClient, inputMethodManager.mFallbackInputConnection, i);
            return inputMethodManager;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static InputMethodManager createStubInstance(int i, Looper looper) {
        return new InputMethodManager((IInputMethodManager) Proxy.newProxyInstance(IInputMethodManager.class.getClassLoader(), new Class[]{IInputMethodManager.class}, new InvocationHandler() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda6
            @Override // java.lang.reflect.InvocationHandler
            public final Object invoke(Object obj, Method method, Object[] objArr) {
                return InputMethodManager.lambda$createStubInstance$0(obj, method, objArr);
            }
        }), i, looper);
    }

    static /* synthetic */ Object lambda$createStubInstance$0(Object obj, Method method, Object[] objArr) throws Throwable {
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

    private InputMethodManager(IInputMethodManager iInputMethodManager, int i, Looper looper) {
        this.mAsyncShowHideMethodEnabled = !Flags.compatchangeForZerojankproxy() || CompatChanges.isChangeEnabled(USE_ASYNC_SHOW_HIDE_METHOD);
        this.mCursorAnchorInfo = null;
        this.mAccessibilityInputMethodSession = new SparseArray<>();
        this.mRequestUpdateCursorAnchorInfoMonitorMode = 0;
        this.mPendingEventPool = new Pools.SimplePool(20);
        this.mPendingEvents = new SparseArray<>(20);
        this.mDelegate = new DelegateImpl();
        this.mClient = new IInputMethodClient.Stub() { // from class: android.view.inputmethod.InputMethodManager.2
            @Override // android.os.Binder
            protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = fileDescriptor;
                obtain.arg2 = printWriter;
                obtain.arg3 = strArr;
                obtain.arg4 = countDownLatch;
                InputMethodManager.this.mH.sendMessage(InputMethodManager.this.mH.obtainMessage(1, obtain));
                try {
                    if (countDownLatch.await(5L, TimeUnit.SECONDS)) {
                        return;
                    }
                    printWriter.println("Timeout waiting for dump");
                } catch (InterruptedException unused) {
                    printWriter.println("Interrupted waiting for dump");
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onBindMethod(InputBindResult inputBindResult) {
                InputMethodManager.this.mH.obtainMessage(2, inputBindResult).sendToTarget();
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onStartInputResult(InputBindResult inputBindResult, int i2) {
                InputMethodManager.this.mH.obtainMessage(40, i2, -1, inputBindResult).sendToTarget();
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onBindAccessibilityService(InputBindResult inputBindResult, int i2) {
                InputMethodManager.this.mH.obtainMessage(11, i2, 0, inputBindResult).sendToTarget();
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onUnbindMethod(int i2, int i3) {
                InputMethodManager.this.mH.obtainMessage(3, i2, i3).sendToTarget();
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onUnbindAccessibilityService(int i2, int i3) {
                InputMethodManager.this.mH.obtainMessage(12, i2, i3).sendToTarget();
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setActive(boolean z, boolean z2) {
                InputMethodManager.this.mH.obtainMessage(4, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setInteractive(boolean z, boolean z2) {
                InputMethodManager.this.mH.obtainMessage(13, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setImeVisibility(boolean z, ImeTracker.Token token) {
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = Boolean.valueOf(z);
                obtain.arg2 = token;
                ImeTracker.forLogging().onProgress(token, 59);
                InputMethodManager.this.mH.obtainMessage(14, obtain).sendToTarget();
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void scheduleStartInputIfNecessary(boolean z) {
                InputMethodManager.this.mH.obtainMessage(4, 0, z ? 1 : 0).sendToTarget();
                InputMethodManager.this.mH.obtainMessage(4, 1, z ? 1 : 0).sendToTarget();
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void reportFullscreenMode(boolean z) {
                InputMethodManager.this.mH.obtainMessage(10, z ? 1 : 0, 0).sendToTarget();
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setImeTraceEnabled(boolean z) {
                ImeTracing.getInstance().setEnabled(z);
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void throwExceptionFromSystem(String str) {
                throw new RuntimeException(str);
            }
        };
        this.mRequestCursorUpdateDisplayIdCheck = new AtomicBoolean(true);
        this.mService = iInputMethodManager;
        this.mMainLooper = looper;
        this.mH = new H(looper);
        this.mDisplayId = i;
        this.mFallbackInputConnection = new RemoteInputConnectionImpl(looper, new BaseInputConnection(this, false), this, null);
    }

    public static InputMethodManager forContext(Context context) {
        int displayId = context.getDisplayId();
        Looper mainLooper = displayId == 0 ? Looper.getMainLooper() : context.getMainLooper();
        sPreventImeStartupUnlessTextEditor = context.getResources().getBoolean(17891335);
        return forContextInternal(displayId, mainLooper);
    }

    private static InputMethodManager forContextInternal(int i, Looper looper) {
        boolean z = i == 0;
        synchronized (sLock) {
            SparseArray<InputMethodManager> sparseArray = sInstanceMap;
            InputMethodManager inputMethodManager = sparseArray.get(i);
            if (inputMethodManager != null) {
                return inputMethodManager;
            }
            InputMethodManager createInstance = createInstance(i, looper);
            if (sInstance == null && z) {
                sInstance = createInstance;
            }
            sparseArray.put(i, createInstance);
            return createInstance;
        }
    }

    @Deprecated
    public static InputMethodManager getInstance() {
        Log.w(TAG, "InputMethodManager.getInstance() is deprecated because it cannot be compatible with multi-display. Use context.getSystemService(InputMethodManager.class) instead.", new Throwable());
        ensureDefaultInstanceForDefaultDisplayIfNecessary();
        return peekInstance();
    }

    @Deprecated
    public static InputMethodManager peekInstance() {
        InputMethodManager inputMethodManager;
        Log.w(TAG, "InputMethodManager.peekInstance() is deprecated because it cannot be compatible with multi-display. Use context.getSystemService(InputMethodManager.class) instead.", new Throwable());
        synchronized (sLock) {
            inputMethodManager = sInstance;
        }
        return inputMethodManager;
    }

    public List<InputMethodInfo> getInputMethodList() {
        return IInputMethodManagerGlobalInvoker.getInputMethodList(UserHandle.myUserId(), 0);
    }

    public boolean isStylusHandwritingAvailable() {
        return isStylusHandwritingAvailableAsUser(UserHandle.of(UserHandle.myUserId()));
    }

    public boolean isStylusHandwritingAvailableAsUser(UserHandle userHandle) {
        boolean booleanValue;
        if (ActivityThread.currentApplication() == null) {
            return false;
        }
        synchronized (this.mH) {
            if (this.mStylusHandwritingAvailableCache == null) {
                this.mStylusHandwritingAvailableCache = new PropertyInvalidatedCache<Integer, Boolean>(this, 4, CACHE_KEY_STYLUS_HANDWRITING_PROPERTY) { // from class: android.view.inputmethod.InputMethodManager.3
                    @Override // android.app.PropertyInvalidatedCache
                    public Boolean recompute(Integer num) {
                        return Boolean.valueOf(IInputMethodManagerGlobalInvoker.isStylusHandwritingAvailableAsUser(num.intValue(), false));
                    }
                };
            }
            booleanValue = this.mStylusHandwritingAvailableCache.query(Integer.valueOf(userHandle.getIdentifier())).booleanValue();
        }
        return booleanValue;
    }

    public boolean isConnectionlessStylusHandwritingAvailable() {
        boolean booleanValue;
        if (ActivityThread.currentApplication() == null) {
            return false;
        }
        synchronized (this.mH) {
            if (this.mConnectionlessStylusHandwritingAvailableCache == null) {
                this.mConnectionlessStylusHandwritingAvailableCache = new PropertyInvalidatedCache<Integer, Boolean>(this, 4, CACHE_KEY_CONNECTIONLESS_STYLUS_HANDWRITING_PROPERTY) { // from class: android.view.inputmethod.InputMethodManager.4
                    @Override // android.app.PropertyInvalidatedCache
                    public Boolean recompute(Integer num) {
                        return Boolean.valueOf(IInputMethodManagerGlobalInvoker.isStylusHandwritingAvailableAsUser(num.intValue(), true));
                    }
                };
            }
            booleanValue = this.mConnectionlessStylusHandwritingAvailableCache.query(Integer.valueOf(UserHandle.myUserId())).booleanValue();
        }
        return booleanValue;
    }

    public List<InputMethodInfo> getInputMethodListAsUser(int i) {
        return IInputMethodManagerGlobalInvoker.getInputMethodList(i, 0);
    }

    public List<InputMethodInfo> getInputMethodListAsUser(int i, int i2) {
        return IInputMethodManagerGlobalInvoker.getInputMethodList(i, i2);
    }

    public InputMethodInfo getCurrentInputMethodInfo() {
        return IInputMethodManagerGlobalInvoker.getCurrentInputMethodInfoAsUser(UserHandle.myUserId());
    }

    @SystemApi
    public InputMethodInfo getCurrentInputMethodInfoAsUser(UserHandle userHandle) {
        Objects.requireNonNull(userHandle);
        return IInputMethodManagerGlobalInvoker.getCurrentInputMethodInfoAsUser(userHandle.getIdentifier());
    }

    public List<InputMethodInfo> getEnabledInputMethodList() {
        return IInputMethodManagerGlobalInvoker.getEnabledInputMethodList(UserHandle.myUserId());
    }

    public List<InputMethodInfo> getEnabledInputMethodListAsUser(UserHandle userHandle) {
        return IInputMethodManagerGlobalInvoker.getEnabledInputMethodList(userHandle.getIdentifier());
    }

    public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(InputMethodInfo inputMethodInfo, boolean z) {
        return IInputMethodManagerGlobalInvoker.getEnabledInputMethodSubtypeList(inputMethodInfo == null ? null : inputMethodInfo.getId(), z, UserHandle.myUserId());
    }

    public List<InputMethodSubtype> getEnabledInputMethodSubtypeListAsUser(String str, boolean z, UserHandle userHandle) {
        return IInputMethodManagerGlobalInvoker.getEnabledInputMethodSubtypeList((String) Objects.requireNonNull(str), z, userHandle.getIdentifier());
    }

    @Deprecated
    public void showStatusIcon(IBinder iBinder, String str, int i) {
        InputMethodPrivilegedOperationsRegistry.get(iBinder).updateStatusIconAsync(str, i);
    }

    @Deprecated
    public void hideStatusIcon(IBinder iBinder) {
        InputMethodPrivilegedOperationsRegistry.get(iBinder).updateStatusIconAsync(null, 0);
    }

    @Deprecated
    public void registerSuggestionSpansForNotification(SuggestionSpan[] suggestionSpanArr) {
        Log.w(TAG, "registerSuggestionSpansForNotification() is deprecated.  Does nothing.");
    }

    @Deprecated
    public void notifySuggestionPicked(SuggestionSpan suggestionSpan, String str, int i) {
        Log.w(TAG, "notifySuggestionPicked() is deprecated.  Does nothing.");
    }

    public boolean isFullscreenMode() {
        boolean z;
        synchronized (this.mH) {
            z = this.mFullscreenMode;
        }
        return z;
    }

    public boolean isFullscreenModeAnim() {
        synchronized (this.mH) {
            if (DEBUG_SEP) {
                Log.d(TAG, "isFullscreenModeAnim: fullscreenMode=" + this.mFullscreenMode + ", wasFullscreenMode=" + this.mWasFullscreenMode);
            }
            if (!this.mFullscreenMode && !this.mWasFullscreenMode) {
                return false;
            }
            this.mWasFullscreenMode = false;
            return true;
        }
    }

    public boolean isActive(View view) {
        boolean z;
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            return fallbackInputMethodManagerIfNecessary.isActive(view);
        }
        checkFocus();
        synchronized (this.mH) {
            z = hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null;
        }
        return z;
    }

    public boolean isActive() {
        boolean z;
        checkFocus();
        synchronized (this.mH) {
            z = (getServedViewLocked() == null || this.mCurrentEditorInfo == null) ? false : true;
        }
        return z;
    }

    public boolean isCurrentRootView(View view) {
        boolean z;
        synchronized (this.mH) {
            z = this.mCurRootView == view.getViewRootImpl();
        }
        return z;
    }

    public boolean isAcceptingText() {
        boolean z;
        checkFocus();
        synchronized (this.mH) {
            z = this.mServedInputConnection != null;
        }
        return z;
    }

    public boolean isInputMethodSuppressingSpellChecker() {
        boolean z;
        synchronized (this.mH) {
            BindState bindState = this.mCurBindState;
            z = bindState != null && bindState.mIsInputMethodSuppressingSpellChecker;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearBindingLocked() {
        if (DEBUG) {
            Log.v(TAG, "Clearing binding!");
        }
        clearConnectionLocked();
        updateInputChannelLocked(null);
        this.mCurId = null;
        this.mCurMethod = null;
        this.mCurBindState = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAccessibilityBindingLocked(int i) {
        if (DEBUG) {
            Log.v(TAG, "Clearing accessibility binding " + i);
        }
        this.mAccessibilityInputMethodSession.remove(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAllAccessibilityBindingLocked() {
        if (DEBUG) {
            Log.v(TAG, "Clearing all accessibility bindings");
        }
        this.mAccessibilityInputMethodSession.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInputChannelLocked(InputChannel inputChannel) {
        if (areSameInputChannel(this.mCurChannel, inputChannel)) {
            return;
        }
        if (this.mCurSender != null) {
            flushPendingEventsLocked();
            this.mCurSender.dispose();
            this.mCurSender = null;
        }
        InputChannel inputChannel2 = this.mCurChannel;
        if (inputChannel2 != null) {
            inputChannel2.dispose();
        }
        this.mCurChannel = inputChannel;
    }

    private static boolean areSameInputChannel(InputChannel inputChannel, InputChannel inputChannel2) {
        if (inputChannel == inputChannel2) {
            return true;
        }
        return (inputChannel == null || inputChannel2 == null || inputChannel.getToken() != inputChannel2.getToken()) ? false : true;
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
        this.mNextServedView = null;
        View view = this.mServedView;
        if (view != null) {
            this.mServedView = null;
            if (Flags.initiationWithoutInputConnection() && view.getViewRootImpl() != null) {
                view.getViewRootImpl().getHandwritingInitiator().clearFocusedView(view);
            }
        } else {
            view = null;
        }
        if (view != null) {
            if (DEBUG) {
                Log.v(TAG, "FINISH INPUT: mServedView=" + InputMethodDebug.dumpViewInfo(view));
            }
            this.mCompletions = null;
            this.mServedConnecting = false;
            this.mLastPendingStartSeqId = -1;
            if (DEBUG_SEP) {
                Log.i(TAG, "finishInputLocked: invalidate mLastPendingStartSeqId=" + this.mLastPendingStartSeqId);
            }
            clearConnectionLocked();
            updateIMESwitchEnable();
        }
        this.mReportInputConnectionOpenedRunner = null;
        this.mImeDispatcher.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean clearCurRootViewIfNeeded() {
        if (this.mActive || this.mCurRootViewWindowFocused) {
            return false;
        }
        finishInputLocked();
        this.mDelegate.setCurrentRootViewLocked(null);
        return true;
    }

    public void displayCompletions(View view, CompletionInfo[] completionInfoArr) {
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.displayCompletions(view, completionInfoArr);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view)) {
                this.mCompletions = completionInfoArr;
                if (isImeSessionAvailableLocked()) {
                    this.mCurBindState.mImeSession.displayCompletions(this.mCompletions);
                }
            }
        }
    }

    public void updateExtractedText(View view, int i, ExtractedText extractedText) {
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.updateExtractedText(view, i, extractedText);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view)) {
                if (isImeSessionAvailableLocked()) {
                    if (this.mFullscreenMode) {
                        this.mCurBindState.mImeSession.updateExtractedTextSync(i, extractedText);
                        return;
                    }
                    this.mCurBindState.mImeSession.updateExtractedText(i, extractedText);
                }
            }
        }
    }

    public boolean showSoftInput(View view, int i) {
        Log.i(TAG_LIFE_CYCLE, "showSoftInput(View,I)");
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            return fallbackInputMethodManagerIfNecessary.showSoftInput(view, i);
        }
        return showSoftInput(view, i, null);
    }

    public boolean showSoftInput(View view, int i, ResultReceiver resultReceiver) {
        return showSoftInput(view, i, resultReceiver, 1);
    }

    private boolean showSoftInput(View view, int i, ResultReceiver resultReceiver, int i2) {
        return showSoftInput(view, ImeTracker.forLogging().onStart(1, 5, i2, ImeTracker.isFromUser(view)), i, resultReceiver, i2);
    }

    private boolean showSoftInput(View view, final ImeTracker.Token token, int i, ResultReceiver resultReceiver, int i2) {
        ImeTracker.forLatency().onRequestShow(token, 5, i2, new InsetsController$$ExternalSyntheticLambda2());
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#showSoftInput", this, null);
        StringBuilder sb = new StringBuilder("ssi(): flags=");
        sb.append(i);
        sb.append(" view=");
        sb.append(view != null ? view.getContext().getPackageName() : 0);
        sb.append(" reason=");
        sb.append(InputMethodDebug.softInputDisplayReasonToString(i2));
        Log.i(TAG_LIFE_CYCLE, sb.toString());
        if (!(view instanceof EditText)) {
            Log.i(TAG_LIFE_CYCLE, "ssi() view is not EditText");
        }
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.d(TAG, "showSoftInput callers=" + Debug.getCallers(10));
        }
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            return fallbackInputMethodManagerIfNecessary.showSoftInput(view, token, i, resultReceiver, i2);
        }
        this.mIsShowRequested = true;
        checkFocus();
        this.mIsShowRequested = false;
        synchronized (this.mH) {
            if (!hasServedByInputMethodLocked(view)) {
                ImeTracker.forLogging().onFailed(token, 1);
                ImeTracker.forLatency().onShowFailed(token, 1, new InsetsController$$ExternalSyntheticLambda2());
                Log.w(TAG, "Ignoring showSoftInput() as view=" + view + " is not served.");
                Log.w(TAG_LIFE_CYCLE, "ssi(): Ignoring showSoftInput() servedView=" + InputMethodDebug.dumpViewInfo(getServedViewLocked()) + " mDisplayId=" + this.mDisplayId + " view=" + InputMethodDebug.dumpViewInfo(view));
                return false;
            }
            ImeTracker.forLogging().onProgress(token, 1);
            if (Flags.refactorInsetsController()) {
                final ViewRootImpl viewRootImpl = view.getViewRootImpl();
                if (viewRootImpl != null && ((viewRootImpl.getInsetsController().computeUserAnimatingTypes() & WindowInsets.Type.ime()) == 0 || viewRootImpl.getInsetsController().isPredictiveBackImeHideAnimInProgress())) {
                    Handler handler = view.getHandler();
                    ImeTracker.forLogging().onProgress(token, 61);
                    if (resultReceiver != null) {
                        resultReceiver.send(hasViewImeRequestedVisible(viewRootImpl.getView()) ? 0 : 2, null);
                    }
                    if (SemInputMethodManagerUtils.isDexDesktopDisplay(view.getContext(), view.getDisplay() == null ? 0 : view.getDisplay().getDisplayId()) && SemInputMethodManagerUtils.isFlipLargeCoverScreenFolded()) {
                        SemInputMethodManagerUtils.showDexToast(view.getContext());
                    }
                    if (handler.getLooper() != Looper.myLooper()) {
                        if (DEBUG) {
                            Log.v(TAG, "Show soft input: reschedule to view thread");
                        }
                        handler.post(new Runnable() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                ViewRootImpl.this.getInsetsController().show(WindowInsets.Type.ime(), false, token);
                            }
                        });
                    } else {
                        viewRootImpl.getInsetsController().show(WindowInsets.Type.ime(), false, token);
                    }
                    return true;
                }
                ImeTracker.forLogging().onCancelled(token, 61);
                return false;
            }
            H h = this.mH;
            h.executeOrSendMessage(Message.obtain(h, 31));
            Log.d(TAG, "showSoftInput() view=" + view + " flags=" + i + " reason=" + InputMethodDebug.softInputDisplayReasonToString(i2));
            return IInputMethodManagerGlobalInvoker.showSoftInput(this.mClient, view.getWindowToken(), token, i, this.mCurRootView.getLastClickToolType(), resultReceiver, i2, this.mAsyncShowHideMethodEnabled);
        }
    }

    @Deprecated
    public void showSoftInputUnchecked(int i, ResultReceiver resultReceiver) {
        synchronized (this.mH) {
            ImeTracker.Token onStart = ImeTracker.forLogging().onStart(1, 5, 1, false);
            Log.w(TAG, "showSoftInputUnchecked() is a hidden method, which will be removed soon. If you are using androidx.appcompat.widget.SearchView, please update to version 26.0 or newer version.");
            ViewRootImpl viewRootImpl = this.mCurRootView;
            View view = viewRootImpl != null ? viewRootImpl.getView() : null;
            if (view == null) {
                ImeTracker.forLogging().onFailed(onStart, 1);
                Log.w(TAG, "No current root view, ignoring showSoftInputUnchecked()");
            } else {
                if (Flags.refactorInsetsController()) {
                    showSoftInput(view, onStart, i, resultReceiver, 1);
                    return;
                }
                ImeTracker.forLogging().onProgress(onStart, 1);
                H h = this.mH;
                h.executeOrSendMessage(Message.obtain(h, 31));
                IInputMethodManagerGlobalInvoker.showSoftInput(this.mClient, view.getWindowToken(), onStart, i, this.mCurRootView.getLastClickToolType(), resultReceiver, 1, this.mAsyncShowHideMethodEnabled);
            }
        }
    }

    public boolean hideSoftInputFromWindow(IBinder iBinder, int i) {
        return hideSoftInputFromWindow(iBinder, i, null);
    }

    public boolean hideSoftInputFromWindow(IBinder iBinder, int i, ResultReceiver resultReceiver) {
        return hideSoftInputFromWindow(iBinder, i, resultReceiver, 4, null);
    }

    private boolean hideSoftInputFromWindow(IBinder iBinder, int i, ResultReceiver resultReceiver, int i2, ImeTracker.Token token) {
        View servedViewLocked;
        synchronized (this.mH) {
            servedViewLocked = getServedViewLocked();
        }
        if (token == null) {
            token = ImeTracker.forLogging().onStart(2, 5, i2, ImeTracker.isFromUser(servedViewLocked));
            ImeTracker.forLatency().onRequestHide(token, 5, i2, new InsetsController$$ExternalSyntheticLambda2());
        }
        final ImeTracker.Token token2 = token;
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#hideSoftInputFromWindow", this, null);
        Log.i(TAG_LIFE_CYCLE, "hsifw() - flag : " + i);
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.d(TAG, "hideSoftInput callers=" + Debug.getCallers(10));
        }
        checkFocus();
        synchronized (this.mH) {
            View servedViewLocked2 = getServedViewLocked();
            boolean z = false;
            int i3 = 1;
            if (servedViewLocked2 != null && servedViewLocked2.getWindowToken() == iBinder) {
                ImeTracker.forLogging().onProgress(token2, 1);
                Log.i(TAG_LIFE_CYCLE, "hsifw() - mService.hideSoftInput");
                if (Flags.refactorInsetsController()) {
                    final ViewRootImpl viewRootImpl = servedViewLocked2.getViewRootImpl();
                    if (viewRootImpl != null) {
                        Handler handler = servedViewLocked2.getHandler();
                        if (handler == null) {
                            ImeTracker.forLogging().onFailed(token2, 66);
                            if (Flags.refactorInsetsController() && CompatChanges.isChangeEnabled(ALWAYS_RETURN_TRUE_HIDE_SOFT_INPUT_FROM_WINDOW)) {
                                z = true;
                            }
                            return z;
                        }
                        ImeTracker.forLogging().onProgress(token2, 66);
                        boolean hasViewImeRequestedVisible = hasViewImeRequestedVisible(viewRootImpl.getView());
                        if (resultReceiver != null) {
                            if (hasViewImeRequestedVisible) {
                                i3 = 3;
                            }
                            resultReceiver.send(i3, null);
                        }
                        if (handler.getLooper() != Looper.myLooper()) {
                            if (DEBUG) {
                                Log.v(TAG, "Hiding soft input: reschedule to view thread");
                            }
                            handler.post(new Runnable() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ViewRootImpl.this.getInsetsController().hide(WindowInsets.Type.ime(), false, token2);
                                }
                            });
                        } else {
                            viewRootImpl.getInsetsController().hide(WindowInsets.Type.ime(), false, token2);
                        }
                        if (!CompatChanges.isChangeEnabled(ALWAYS_RETURN_TRUE_HIDE_SOFT_INPUT_FROM_WINDOW)) {
                            return hasViewImeRequestedVisible;
                        }
                    }
                    return CompatChanges.isChangeEnabled(ALWAYS_RETURN_TRUE_HIDE_SOFT_INPUT_FROM_WINDOW);
                }
                return IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, iBinder, token2, i, resultReceiver, i2, this.mAsyncShowHideMethodEnabled);
            }
            ImeTracker.forLogging().onFailed(token2, 1);
            ImeTracker.forLatency().onHideFailed(token2, 1, new InsetsController$$ExternalSyntheticLambda2());
            printLog("hsifw() ignored", iBinder, servedViewLocked2);
            if (Flags.refactorInsetsController() && CompatChanges.isChangeEnabled(ALWAYS_RETURN_TRUE_HIDE_SOFT_INPUT_FROM_WINDOW)) {
                z = true;
            }
            return z;
        }
    }

    public boolean hideSoftInputFromView(View view, int i) {
        checkFocus();
        boolean z = view.hasWindowFocus() && view.isFocused();
        synchronized (this.mH) {
            boolean hasServedByInputMethodLocked = hasServedByInputMethodLocked(view);
            if (!z && !hasServedByInputMethodLocked) {
                return false;
            }
            ImeTracker.Token onStart = ImeTracker.forLogging().onStart(2, 5, 39, ImeTracker.isFromUser(view));
            ImeTracker.forLatency().onRequestHide(onStart, 5, 39, new InsetsController$$ExternalSyntheticLambda2());
            ImeTracing.getInstance().triggerClientDump("InputMethodManager#hideSoftInputFromView", this, null);
            if (!hasServedByInputMethodLocked) {
                ImeTracker.forLogging().onFailed(onStart, 1);
                ImeTracker.forLatency().onShowFailed(onStart, 1, new InsetsController$$ExternalSyntheticLambda2());
                Log.w(TAG, "Ignoring hideSoftInputFromView() as view=" + view + " is not served.");
                return false;
            }
            ImeTracker.forLogging().onProgress(onStart, 1);
            if (Flags.refactorInsetsController()) {
                return hideSoftInputFromWindow(view.getWindowToken(), i, null, 39, onStart);
            }
            return IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, view.getWindowToken(), onStart, i, null, 39, this.mAsyncShowHideMethodEnabled);
        }
    }

    public void hideSoftInputFromServerForTest() {
        IInputMethodManagerGlobalInvoker.hideSoftInputFromServerForTest();
    }

    public void startStylusHandwriting(View view) {
        startStylusHandwritingInternal(view, null, 0);
    }

    private void sendFailureCallback(Executor executor, final Consumer<Boolean> consumer) {
        if (executor == null || consumer == null) {
            return;
        }
        executor.execute(new Runnable() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(false);
            }
        });
    }

    private boolean startStylusHandwritingInternal(View view, String str, int i) {
        return startStylusHandwritingInternal(view, str, i, null, null);
    }

    private boolean startStylusHandwritingInternal(View view, String str, int i, Executor executor, Consumer<Boolean> consumer) {
        Executor executor2;
        Consumer<Boolean> consumer2;
        Objects.requireNonNull(view);
        boolean z = consumer != null;
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.startStylusHandwritingInternal(view, str, i, executor, consumer);
            executor2 = executor;
            consumer2 = consumer;
        } else {
            executor2 = executor;
            consumer2 = consumer;
        }
        boolean isEmpty = TextUtils.isEmpty(str);
        checkFocus();
        synchronized (this.mH) {
            if (!hasServedByInputMethodLocked(view)) {
                Log.w(TAG, "Ignoring startStylusHandwriting as view=" + view + " is not served.");
                sendFailureCallback(executor2, consumer2);
                return false;
            }
            if (view.getViewRootImpl() != this.mCurRootView) {
                Log.w(TAG, "Ignoring startStylusHandwriting: View's window does not have focus.");
                sendFailureCallback(executor2, consumer2);
                return false;
            }
            if (!isEmpty) {
                WeakReference weakReference = new WeakReference(executor2);
                WeakReference weakReference2 = new WeakReference(consumer2);
                if (z) {
                    if (!IInputMethodManagerGlobalInvoker.acceptStylusHandwritingDelegationAsync(this.mClient, UserHandle.myUserId(), view.getContext().getOpPackageName(), str, i, new AnonymousClass5(this, weakReference, weakReference2))) {
                        sendFailureCallback(executor2, consumer2);
                    }
                    return true;
                }
                return IInputMethodManagerGlobalInvoker.acceptStylusHandwritingDelegation(this.mClient, UserHandle.myUserId(), view.getContext().getOpPackageName(), str, i);
            }
            IInputMethodManagerGlobalInvoker.startStylusHandwriting(this.mClient);
            return false;
        }
    }

    /* renamed from: android.view.inputmethod.InputMethodManager$5, reason: invalid class name */
    class AnonymousClass5 extends IBooleanListener.Stub {
        final /* synthetic */ WeakReference val$callbackRef;
        final /* synthetic */ WeakReference val$executorRef;

        AnonymousClass5(InputMethodManager inputMethodManager, WeakReference weakReference, WeakReference weakReference2) {
            this.val$executorRef = weakReference;
            this.val$callbackRef = weakReference2;
        }

        @Override // com.android.internal.inputmethod.IBooleanListener
        public void onResult(final boolean z) {
            Executor executor = (Executor) this.val$executorRef.get();
            final Consumer consumer = (Consumer) this.val$callbackRef.get();
            if (executor == null || consumer == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: android.view.inputmethod.InputMethodManager$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(Boolean.valueOf(z));
                }
            });
        }
    }

    public void startConnectionlessStylusHandwriting(View view, CursorAnchorInfo cursorAnchorInfo, Executor executor, ConnectionlessHandwritingCallback connectionlessHandwritingCallback) {
        startConnectionlessStylusHandwritingInternal(view, cursorAnchorInfo, null, null, executor, connectionlessHandwritingCallback);
    }

    public void startConnectionlessStylusHandwritingForDelegation(View view, CursorAnchorInfo cursorAnchorInfo, Executor executor, ConnectionlessHandwritingCallback connectionlessHandwritingCallback) {
        String opPackageName = view.getContext().getOpPackageName();
        startConnectionlessStylusHandwritingInternal(view, cursorAnchorInfo, opPackageName, opPackageName, executor, connectionlessHandwritingCallback);
    }

    public void startConnectionlessStylusHandwritingForDelegation(View view, CursorAnchorInfo cursorAnchorInfo, String str, Executor executor, ConnectionlessHandwritingCallback connectionlessHandwritingCallback) {
        Objects.requireNonNull(str);
        startConnectionlessStylusHandwritingInternal(view, cursorAnchorInfo, view.getContext().getOpPackageName(), str, executor, connectionlessHandwritingCallback);
    }

    private void startConnectionlessStylusHandwritingInternal(View view, CursorAnchorInfo cursorAnchorInfo, String str, String str2, Executor executor, ConnectionlessHandwritingCallback connectionlessHandwritingCallback) {
        String str3;
        String str4;
        View view2;
        Executor executor2;
        ConnectionlessHandwritingCallback connectionlessHandwritingCallback2;
        Objects.requireNonNull(view);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(connectionlessHandwritingCallback);
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            view2 = view;
            executor2 = executor;
            connectionlessHandwritingCallback2 = connectionlessHandwritingCallback;
            fallbackInputMethodManagerIfNecessary.startConnectionlessStylusHandwritingInternal(view2, cursorAnchorInfo, str, str2, executor2, connectionlessHandwritingCallback2);
            str3 = str;
            str4 = str2;
        } else {
            str3 = str;
            str4 = str2;
            view2 = view;
            executor2 = executor;
            connectionlessHandwritingCallback2 = connectionlessHandwritingCallback;
        }
        checkFocus();
        synchronized (this.mH) {
            if (view2.getViewRootImpl() != this.mCurRootView) {
                Log.w(TAG, "Ignoring startConnectionlessStylusHandwriting: View's window does not have focus.");
            } else {
                IInputMethodManagerGlobalInvoker.startConnectionlessStylusHandwriting(this.mClient, UserHandle.myUserId(), cursorAnchorInfo, str4, str3, new ConnectionlessHandwritingCallbackProxy(executor2, connectionlessHandwritingCallback2));
            }
        }
    }

    public void prepareStylusHandwritingDelegation(View view) {
        prepareStylusHandwritingDelegation(view, view.getContext().getOpPackageName());
    }

    public void prepareStylusHandwritingDelegation(View view, String str) {
        Objects.requireNonNull(view);
        Objects.requireNonNull(str);
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.prepareStylusHandwritingDelegation(view, str);
        }
        IInputMethodManagerGlobalInvoker.prepareStylusHandwritingDelegation(this.mClient, UserHandle.myUserId(), str, view.getContext().getOpPackageName());
    }

    public boolean acceptStylusHandwritingDelegation(View view) {
        return startStylusHandwritingInternal(view, view.getContext().getOpPackageName(), view.getHandwritingDelegateFlags());
    }

    public boolean acceptStylusHandwritingDelegation(View view, String str) {
        Objects.requireNonNull(str);
        return startStylusHandwritingInternal(view, str, view.getHandwritingDelegateFlags());
    }

    public void acceptStylusHandwritingDelegation(View view, String str, Executor executor, Consumer<Boolean> consumer) {
        Objects.requireNonNull(str);
        acceptStylusHandwritingDelegation(view, str, Flags.homeScreenHandwritingDelegator() ? view.getHandwritingDelegateFlags() : 0, executor, consumer);
    }

    public void acceptStylusHandwritingDelegation(View view, String str, int i, Executor executor, Consumer<Boolean> consumer) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(view);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        startStylusHandwritingInternal(view, str, i, executor, consumer);
    }

    @Deprecated
    public void toggleSoftInputFromWindow(IBinder iBinder, int i, int i2) {
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#toggleSoftInputFromWindow", this, null);
        synchronized (this.mH) {
            View servedViewLocked = getServedViewLocked();
            if (servedViewLocked != null && servedViewLocked.getWindowToken() == iBinder) {
                Log.i(TAG_LIFE_CYCLE, "tsifw()");
                toggleSoftInput(i, i2);
                return;
            }
            printLog("tsifw() ignored", iBinder, servedViewLocked);
        }
    }

    @Deprecated
    public void toggleSoftInput(int i, int i2) {
        semToggleSoftInput(i, i2);
    }

    public void restartInput(View view) {
        if (DEBUG) {
            Log.d(TAG, "restartInput()");
        }
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.restartInput(view);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view)) {
                this.mServedConnecting = true;
                startInputInner(4, null, 0, 0, 0);
            }
        }
    }

    public boolean doInvalidateInput(RemoteInputConnectionImpl remoteInputConnectionImpl, TextSnapshot textSnapshot, final int i) {
        synchronized (this.mH) {
            if (this.mServedInputConnection == remoteInputConnectionImpl && this.mCurrentEditorInfo != null) {
                if (!isImeSessionAvailableLocked()) {
                    return false;
                }
                final EditorInfo createCopyInternal = this.mCurrentEditorInfo.createCopyInternal();
                int selectionStart = textSnapshot.getSelectionStart();
                this.mCursorSelStart = selectionStart;
                createCopyInternal.initialSelStart = selectionStart;
                int selectionEnd = textSnapshot.getSelectionEnd();
                this.mCursorSelEnd = selectionEnd;
                createCopyInternal.initialSelEnd = selectionEnd;
                this.mCursorCandStart = textSnapshot.getCompositionStart();
                this.mCursorCandEnd = textSnapshot.getCompositionEnd();
                createCopyInternal.initialCapsMode = textSnapshot.getCursorCapsMode();
                createCopyInternal.setInitialSurroundingTextInternal(textSnapshot.getSurroundingText());
                if (createCopyInternal.extras == null) {
                    createCopyInternal.extras = new Bundle();
                }
                SemInputMethodManagerUtils.putInfoInExtra(getServedViewLocked(), createCopyInternal, "doInvalidateInput");
                this.mCurBindState.mImeSession.invalidateInput(createCopyInternal, this.mServedInputConnection, i);
                final IRemoteAccessibilityInputConnection asIRemoteAccessibilityInputConnection = this.mServedInputConnection.asIRemoteAccessibilityInputConnection();
                forAccessibilitySessionsLocked(new Consumer() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((IAccessibilityInputMethodSessionInvoker) obj).invalidateInput(EditorInfo.this, asIRemoteAccessibilityInputConnection, i);
                    }
                });
                return true;
            }
            return true;
        }
    }

    public void invalidateInput(View view) {
        Objects.requireNonNull(view);
        if (DEBUG) {
            Log.d(TAG, "IMM#invaldateInput()");
        }
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.invalidateInput(view);
            return;
        }
        synchronized (this.mH) {
            if (this.mServedInputConnection != null && getServedViewLocked() == view) {
                StringBuilder sb = new StringBuilder("invalidateInput: scheduleInvalidateInput isRestarting");
                boolean z = true;
                sb.append(this.mLastPendingStartSeqId != -1);
                Log.i(TAG, sb.toString());
                if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
                    Log.d(TAG, "invalidateInput callers=" + Debug.getCallers(15));
                }
                RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
                if (this.mLastPendingStartSeqId == -1) {
                    z = false;
                }
                remoteInputConnectionImpl.scheduleInvalidateInput(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:88:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x021a A[Catch: all -> 0x047c, TryCatch #1 {, blocks: (B:40:0x00de, B:42:0x00e4, B:46:0x043d, B:47:0x0468, B:51:0x046f, B:52:0x047a, B:54:0x00f1, B:56:0x00f5, B:57:0x00f7, B:59:0x010f, B:61:0x0118, B:63:0x0133, B:64:0x0139, B:66:0x013f, B:67:0x0148, B:68:0x0155, B:70:0x0160, B:71:0x0167, B:73:0x0180, B:75:0x0184, B:76:0x019a, B:78:0x019e, B:79:0x01d7, B:83:0x01e0, B:86:0x01fb, B:89:0x020f, B:90:0x0216, B:92:0x021a, B:94:0x0220, B:95:0x022b, B:97:0x0242, B:100:0x024c, B:101:0x02a7, B:104:0x02b2, B:106:0x02b8, B:107:0x02ba, B:109:0x02c0, B:110:0x02d5, B:111:0x02f0, B:113:0x02ec, B:116:0x02fe, B:118:0x0313, B:119:0x033e, B:121:0x0340, B:123:0x0344, B:126:0x035f, B:128:0x0367, B:130:0x0375, B:132:0x0380, B:135:0x0383, B:136:0x0397, B:139:0x03b7, B:141:0x03bb, B:143:0x03c1, B:144:0x03ca, B:147:0x03d0, B:158:0x039e, B:160:0x03a6, B:161:0x03b5, B:162:0x0388, B:164:0x038c, B:166:0x0392, B:167:0x0248, B:168:0x0279, B:171:0x0291, B:172:0x028d, B:173:0x0227, B:177:0x0144, B:180:0x0150), top: B:39:0x00de }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean startInputInner(final int r29, android.os.IBinder r30, int r31, int r32, int r33) {
        /*
            Method dump skipped, instructions count: 1154
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.startInputInner(int, android.os.IBinder, int, int, int):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startInputInner$5(int i) {
        startInputOnWindowFocusGainInternal(i, null, 0, 0, 0);
    }

    private boolean isSwitchingBetweenEquivalentNonEditableViews(ViewFocusParameterInfo viewFocusParameterInfo, int i, int i2, int i3, int i4) {
        return (i & 8) == 0 && (i & 2) == 0 && viewFocusParameterInfo != null && viewFocusParameterInfo.sameAs(this.mCurrentEditorInfo, i, i2, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportInputConnectionOpened(InputConnection inputConnection, EditorInfo editorInfo, Handler handler, View view) {
        view.onInputConnectionOpenedInternal(inputConnection, editorInfo, handler);
        ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.getHandwritingInitiator().onInputConnectionCreated(view);
        }
    }

    public void addVirtualStylusIdForTestSession() {
        synchronized (this.mH) {
            IInputMethodManagerGlobalInvoker.addVirtualStylusIdForTestSession(this.mClient);
        }
    }

    public void setStylusWindowIdleTimeoutForTest(long j) {
        synchronized (this.mH) {
            IInputMethodManagerGlobalInvoker.setStylusWindowIdleTimeoutForTest(this.mClient, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getStartInputFlags(View view, int i) {
        return view.onCheckIsTextEditor() ? i | 3 : i | 1;
    }

    public void checkFocus() {
        synchronized (this.mH) {
            ViewRootImpl viewRootImpl = this.mCurRootView;
            if (viewRootImpl == null) {
                return;
            }
            if (checkFocusInternalLocked(false, viewRootImpl)) {
                startInputOnWindowFocusGainInternal(5, null, 0, 0, 0);
            }
        }
    }

    public ImeOnBackInvokedDispatcher getImeOnBackInvokedDispatcher() {
        return this.mImeDispatcher;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkFocusInternalLocked(boolean z, ViewRootImpl viewRootImpl) {
        if (this.mCurRootView != viewRootImpl) {
            return false;
        }
        if (this.mServedView == this.mNextServedView && !z) {
            return false;
        }
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("checkFocus: view=");
            sb.append(this.mServedView);
            sb.append(" next=");
            sb.append(this.mNextServedView);
            sb.append(" force=");
            sb.append(z);
            sb.append(" package=");
            View view = this.mServedView;
            sb.append(view != null ? view.getContext().getPackageName() : "<none>");
            Log.v(TAG, sb.toString());
        }
        View view2 = this.mNextServedView;
        if (view2 == null) {
            if (DEBUG_SEP) {
                Log.i(TAG, "checkFocus: return, mNextServedView is null");
            }
            finishInputLocked();
            closeCurrentInput();
            return false;
        }
        this.mServedView = view2;
        RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
        if (remoteInputConnectionImpl == null) {
            return true;
        }
        remoteInputConnectionImpl.finishComposingTextFromImm();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onViewFocusChangedInternal(View view, boolean z) {
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.d(TAG, "onViewFocusChangedInternal: hasFocus=" + z + ", callers=" + Debug.getCallers(10));
        }
        if (view == null || view.isTemporarilyDetached()) {
            if (DEBUG_SEP) {
                Log.i(TAG, "onViewFocusChangedInternal return, view is ".concat(view == null ? PerfettoProtoLogImpl.NULL_STRING : "detached temporarily"));
                return;
            }
            return;
        }
        ViewRootImpl viewRootImpl = view.getViewRootImpl();
        synchronized (this.mH) {
            if (this.mCurRootView != viewRootImpl) {
                if (DEBUG_SEP) {
                    Log.i(TAG, "onViewFocusChangedInternal return, mCurRootView=" + this.mCurRootView + ",  view.getViewRootImpl()=" + view.getViewRootImpl());
                }
                return;
            }
            if (view.hasImeFocus() && view.hasWindowFocus()) {
                if (DEBUG) {
                    Log.d(TAG, "onViewFocusChangedInternal, view=" + InputMethodDebug.dumpViewInfo(view));
                }
                if (z) {
                    this.mNextServedView = view;
                }
                viewRootImpl.dispatchCheckFocus();
                return;
            }
            if (DEBUG_SEP) {
                Log.i(TAG, "onViewFocusChangedInternal return, view=" + InputMethodDebug.dumpViewInfo(view));
            }
        }
    }

    void closeCurrentInput() {
        final ImeTracker.Token onStart = ImeTracker.forLogging().onStart(2, 5, 38, false);
        ImeTracker.forLatency().onRequestHide(onStart, 5, 38, new InsetsController$$ExternalSyntheticLambda2());
        synchronized (this.mH) {
            ViewRootImpl viewRootImpl = this.mCurRootView;
            View view = viewRootImpl != null ? viewRootImpl.getView() : null;
            if (view == null) {
                ImeTracker.forLogging().onFailed(onStart, 1);
                ImeTracker.forLatency().onHideFailed(onStart, 1, new InsetsController$$ExternalSyntheticLambda2());
                Log.w(TAG, "No current root view, ignoring closeCurrentInput()");
                return;
            }
            ImeTracker.forLogging().onProgress(onStart, 1);
            if (Flags.refactorInsetsController()) {
                synchronized (this.mH) {
                    Handler handler = view.getHandler();
                    if (handler == null) {
                        ImeTracker.forLogging().onFailed(onStart, 66);
                        return;
                    }
                    ImeTracker.forLogging().onProgress(onStart, 66);
                    if (handler.getLooper() != Looper.myLooper()) {
                        if (DEBUG) {
                            Log.v(TAG, "Close current input: reschedule hide to view thread");
                        }
                        final ViewRootImpl viewRootImpl2 = this.mCurRootView;
                        handler.post(new Runnable() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                ViewRootImpl.this.getInsetsController().hide(WindowInsets.Type.ime(), false, onStart);
                            }
                        });
                    } else {
                        this.mCurRootView.getInsetsController().hide(WindowInsets.Type.ime(), false, onStart);
                    }
                    return;
                }
            }
            Log.i(TAG_LIFE_CYCLE, "closeCurrentInput: IInputMethodManagerGlobalInvoker.hideSoftInput");
            try {
                IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, view.getWindowToken(), onStart, 2, null, 38, true);
            } catch (NullPointerException e) {
                ViewRootImpl viewRootImpl3 = this.mCurRootView;
                if (viewRootImpl3 == null || viewRootImpl3.getView() == null) {
                    Log.w(TAG, "NullPointerException: No current root view, ignoring closeCurrentInput()");
                }
                e.printStackTrace();
            }
            return;
        }
    }

    public void registerImeConsumer(ImeInsetsSourceConsumer imeInsetsSourceConsumer) {
        if (imeInsetsSourceConsumer == null) {
            throw new IllegalStateException("ImeInsetsSourceConsumer cannot be null.");
        }
        synchronized (this.mH) {
            this.mImeInsetsConsumer = imeInsetsSourceConsumer;
        }
    }

    public void unregisterImeConsumer(ImeInsetsSourceConsumer imeInsetsSourceConsumer) {
        if (imeInsetsSourceConsumer == null) {
            throw new IllegalStateException("ImeInsetsSourceConsumer cannot be null.");
        }
        synchronized (this.mH) {
            if (this.mImeInsetsConsumer == imeInsetsSourceConsumer) {
                this.mImeInsetsConsumer = null;
            }
        }
    }

    public boolean requestImeShow(IBinder iBinder, ImeTracker.Token token) {
        Log.i(TAG_LIFE_CYCLE, "ris()");
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.d(TAG, "requestImeShow callers=" + Debug.getCallers(10));
        }
        checkFocus();
        synchronized (this.mH) {
            View servedViewLocked = getServedViewLocked();
            if (servedViewLocked != null && servedViewLocked.getWindowToken() == iBinder) {
                ImeTracker.forLogging().onProgress(token, 37);
                showSoftInput(servedViewLocked, token, 0, null, 26);
                return true;
            }
            ImeTracker.forLogging().onFailed(token, 37);
            printLog("ris() ignored", iBinder, servedViewLocked);
            return false;
        }
    }

    public void notifyImeHidden(IBinder iBinder, ImeTracker.Token token) {
        ViewRootImpl viewRootImpl;
        Log.i(TAG_LIFE_CYCLE, "notifyImeHidden: IInputMethodManagerGlobalInvoker.hideSoftInput");
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.d(TAG, "notifyImeHidden callers=" + Debug.getCallers(10));
        }
        this.mWasFullscreenMode = false;
        ImeTracker.forLatency().onRequestHide(token, 5, 28, new InsetsController$$ExternalSyntheticLambda2());
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#notifyImeHidden", this, null);
        synchronized (this.mH) {
            if (isImeSessionAvailableLocked() && (viewRootImpl = this.mCurRootView) != null && viewRootImpl.getWindowToken() == iBinder) {
                ImeTracker.forLogging().onProgress(token, 1);
                IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, iBinder, token, 0, null, 28, true);
                return;
            }
            ImeTracker.forLogging().onFailed(token, 1);
            ImeTracker.forLatency().onHideFailed(token, 1, new InsetsController$$ExternalSyntheticLambda2());
        }
    }

    public void removeImeSurface(IBinder iBinder) {
        if (DEBUG_SEP) {
            Log.i(TAG, "removeImeSurface");
        }
        synchronized (this.mH) {
            IInputMethodManagerGlobalInvoker.removeImeSurfaceFromWindowAsync(iBinder);
        }
    }

    public void updateSelection(View view, final int i, final int i2, final int i3, final int i4) {
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.updateSelection(view, i, i2, i3, i4);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
                if (remoteInputConnectionImpl == null || !remoteInputConnectionImpl.hasPendingInvalidation()) {
                    if (this.mCursorSelStart != i || this.mCursorSelEnd != i2 || this.mCursorCandStart != i3 || this.mCursorCandEnd != i4) {
                        boolean z = DEBUG;
                        if (z) {
                            Log.d(TAG, "updateSelection");
                        }
                        if (z) {
                            Log.v(TAG, "SELECTION CHANGE: " + this.mCurBindState.mImeSession);
                        }
                        this.mCurBindState.mImeSession.updateSelection(this.mCursorSelStart, this.mCursorSelEnd, i, i2, i3, i4);
                        forAccessibilitySessionsLocked(new Consumer() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda8
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                InputMethodManager.this.lambda$updateSelection$7(i, i2, i3, i4, (IAccessibilityInputMethodSessionInvoker) obj);
                            }
                        });
                        this.mCursorSelStart = i;
                        this.mCursorSelEnd = i2;
                        this.mCursorCandStart = i3;
                        this.mCursorCandEnd = i4;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSelection$7(int i, int i2, int i3, int i4, IAccessibilityInputMethodSessionInvoker iAccessibilityInputMethodSessionInvoker) {
        iAccessibilityInputMethodSessionInvoker.updateSelection(this.mCursorSelStart, this.mCursorSelEnd, i, i2, i3, i4);
    }

    @Deprecated
    public void viewClicked(View view) {
        View servedViewLocked;
        View nextServedViewLocked;
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.viewClicked(view);
            return;
        }
        synchronized (this.mH) {
            servedViewLocked = getServedViewLocked();
            nextServedViewLocked = getNextServedViewLocked();
        }
        boolean z = servedViewLocked != nextServedViewLocked;
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                if (DEBUG) {
                    Log.v(TAG, "onViewClicked: " + z);
                }
                this.mCurBindState.mImeSession.viewClicked(z);
            }
        }
    }

    @Deprecated
    public boolean isCursorAnchorInfoEnabled() {
        boolean z;
        synchronized (this.mH) {
            int i = this.mRequestUpdateCursorAnchorInfoMonitorMode;
            z = ((i & 1) != 0) || ((i & 2) != 0);
        }
        return z;
    }

    @Deprecated
    public void setUpdateCursorAnchorInfoMode(int i) {
        synchronized (this.mH) {
            this.mRequestUpdateCursorAnchorInfoMonitorMode = i;
        }
    }

    @Deprecated
    public void updateCursor(View view, int i, int i2, int i3, int i4) {
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.updateCursor(view, i, i2, i3, i4);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                this.mTmpCursorRect.set(i, i2, i3, i4);
                if (!this.mCursorRect.equals(this.mTmpCursorRect)) {
                    if (DEBUG) {
                        Log.d(TAG, "updateCursor: " + this.mCurBindState.mImeSession);
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
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.updateCursorAnchorInfo(view, cursorAnchorInfo);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
                if ((remoteInputConnectionImpl == null || !remoteInputConnectionImpl.resetHasPendingImmediateCursorAnchorInfoUpdate()) && Objects.equals(this.mCursorAnchorInfo, cursorAnchorInfo)) {
                    return;
                }
                this.mCurBindState.mImeSession.updateCursorAnchorInfo(cursorAnchorInfo);
                this.mCursorAnchorInfo = cursorAnchorInfo;
            }
        }
    }

    public void sendAppPrivateCommand(View view, String str, Bundle bundle) {
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.sendAppPrivateCommand(view, str, bundle);
            return;
        }
        checkFocus();
        synchronized (this.mH) {
            if (ViewRune.SUPPORT_WRITING_TOOLKIT && SemInputMethodManagerUtils.ACTION_SHOW_TOOLKIT_HBD.equals(str)) {
                if (SemInputMethodManagerUtils.isWritingToolkitDisallowedByKnox()) {
                    Log.w(TAG, "Writing Toolkit is disallowed");
                    return;
                }
                if (!isImeSessionAvailableLocked()) {
                    Log.w(TAG, "Writing Toolkit is not allowed because ime session is not available");
                    return;
                }
                mWtSelectionInfo = bundle;
                if (usingWritingToolkit()) {
                    Log.d(TAG, "Writing Toolkit result update");
                    str = SemInputMethodManagerUtils.ACTION_UPDATE_RESULT_TOOLKIT_HBD;
                }
                this.mCurBindState.mImeSession.appPrivateCommand(str, bundle);
                return;
            }
            if (hasServedByInputMethodLocked(view) && this.mCurrentEditorInfo != null && isImeSessionAvailableLocked()) {
                if (DEBUG) {
                    Log.v(TAG, "APP PRIVATE COMMAND " + str + ": " + bundle);
                }
                this.mCurBindState.mImeSession.appPrivateCommand(str, bundle);
            }
        }
    }

    @Deprecated
    public void setInputMethod(IBinder iBinder, String str) {
        if (iBinder != null) {
            Log.d(TAG, "setInputMethod: Calling setInputMethod token=" + iBinder + ", id=" + str);
            InputMethodPrivilegedOperationsRegistry.get(iBinder).setInputMethod(str);
            return;
        }
        if (str == null) {
            return;
        }
        if (Process.myUid() == 1000) {
            Log.w(TAG, "System process should not be calling setInputMethod() because almost always it is a bug under multi-user / multi-profile environment. Consider interacting with InputMethodManagerService directly via LocalServices.");
            return;
        }
        Application currentApplication = ActivityThread.currentApplication();
        if (currentApplication != null && currentApplication.checkSelfPermission(Manifest.permission.WRITE_SECURE_SETTINGS) == 0) {
            List<InputMethodInfo> enabledInputMethodList = getEnabledInputMethodList();
            int size = enabledInputMethodList.size();
            int i = 0;
            while (true) {
                if (i < size) {
                    if (str.equals(enabledInputMethodList.get(i).getId())) {
                        break;
                    } else {
                        i++;
                    }
                } else if (!SemInputMethodManagerUtils.METHOD_ID_BIXBY_DICTATION.equals(str)) {
                    Log.e(TAG, "Ignoring setInputMethod(null, " + str + ") because the specified id not found in enabled IMEs.");
                    return;
                }
            }
            Log.w(TAG, "The undocumented behavior that setInputMethod() accepts null token when the caller has WRITE_SECURE_SETTINGS is deprecated. This behavior may be completely removed in a future version.  Update secure settings directly instead.");
            ContentResolver contentResolver = currentApplication.getContentResolver();
            Settings.Secure.putInt(contentResolver, Settings.Secure.SELECTED_INPUT_METHOD_SUBTYPE, -1);
            Settings.Secure.putString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD, str);
            Log.i(TAG, "setInputMethod: Putting Settings.Secure.DEFAULT_INPUT_METHOD, id=" + str);
        }
    }

    @Deprecated
    public void setInputMethodAndSubtype(IBinder iBinder, String str, InputMethodSubtype inputMethodSubtype) {
        if (iBinder == null) {
            Log.e(TAG, "setInputMethodAndSubtype() does not accept null token on Android Q and later.");
        } else {
            InputMethodPrivilegedOperationsRegistry.get(iBinder).setInputMethodAndSubtype(str, inputMethodSubtype);
        }
    }

    @Deprecated
    public void hideSoftInputFromInputMethod(IBinder iBinder, int i) {
        Log.i(TAG_LIFE_CYCLE, "hsifi() - flag : " + i);
        InputMethodPrivilegedOperationsRegistry.get(iBinder).hideMySoftInput(ImeTracker.forLogging().onStart(2, 5, 32, false), i, 32);
    }

    @Deprecated
    public void showSoftInputFromInputMethod(IBinder iBinder, int i) {
        Log.i(TAG_LIFE_CYCLE, "ssifim() - flag : " + i);
        InputMethodPrivilegedOperationsRegistry.get(iBinder).showMySoftInput(ImeTracker.forLogging().onStart(1, 5, 54, false), i, 54);
    }

    public int dispatchInputEvent(InputEvent inputEvent, Object obj, FinishedInputEventCallback finishedInputEventCallback, Handler handler) {
        synchronized (this.mH) {
            if (isImeSessionAvailableLocked()) {
                if (inputEvent instanceof KeyEvent) {
                    KeyEvent keyEvent = (KeyEvent) inputEvent;
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
                    Log.v(TAG, "DISPATCH INPUT EVENT: " + this.mCurBindState.mImeSession);
                }
                PendingEvent obtainPendingEventLocked = obtainPendingEventLocked(inputEvent, obj, this.mCurBindState.mImeId, finishedInputEventCallback, handler);
                if (this.mMainLooper.isCurrentThread()) {
                    return sendInputEventOnMainLooperLocked(obtainPendingEventLocked);
                }
                Message obtainMessage = this.mH.obtainMessage(5, obtainPendingEventLocked);
                obtainMessage.setAsynchronous(true);
                this.mH.sendMessage(obtainMessage);
                return -1;
            }
            if (DEBUG) {
                Log.i(TAG, "dispatchInputEvent: mCurBindState or mCurBindState.mImeSession is null.");
            }
            return 0;
        }
    }

    public void dispatchKeyEventFromInputMethod(View view, KeyEvent keyEvent) {
        ViewRootImpl viewRootImpl;
        View servedViewLocked;
        InputMethodManager fallbackInputMethodManagerIfNecessary = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackInputMethodManagerIfNecessary != null) {
            fallbackInputMethodManagerIfNecessary.dispatchKeyEventFromInputMethod(view, keyEvent);
            return;
        }
        synchronized (this.mH) {
            if (view != null) {
                try {
                    viewRootImpl = view.getViewRootImpl();
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                viewRootImpl = null;
            }
            if (viewRootImpl == null && (servedViewLocked = getServedViewLocked()) != null) {
                viewRootImpl = servedViewLocked.getViewRootImpl();
            }
            if (viewRootImpl != null) {
                viewRootImpl.dispatchKeyFromIme(keyEvent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendInputEventAndReportResultOnMainLooper(PendingEvent pendingEvent) {
        synchronized (this.mH) {
            int sendInputEventOnMainLooperLocked = sendInputEventOnMainLooperLocked(pendingEvent);
            if (sendInputEventOnMainLooperLocked == -1) {
                return;
            }
            boolean z = true;
            if (sendInputEventOnMainLooperLocked != 1) {
                z = false;
            }
            invokeFinishedInputEventCallback(pendingEvent, z);
        }
    }

    private int sendInputEventOnMainLooperLocked(PendingEvent pendingEvent) {
        if (this.mCurChannel != null) {
            if (this.mCurSender == null) {
                this.mCurSender = new ImeInputEventSender(this.mCurChannel, this.mH.getLooper());
            }
            InputEvent inputEvent = pendingEvent.mEvent;
            int sequenceNumber = inputEvent.getSequenceNumber();
            if (this.mCurSender.sendInputEvent(sequenceNumber, inputEvent)) {
                this.mPendingEvents.put(sequenceNumber, pendingEvent);
                Trace.traceCounter(4L, PENDING_EVENT_COUNTER, this.mPendingEvents.size());
                Message obtainMessage = this.mH.obtainMessage(6, sequenceNumber, 0, pendingEvent);
                obtainMessage.setAsynchronous(true);
                this.mH.sendMessageDelayed(obtainMessage, INPUT_METHOD_NOT_RESPONDING_TIMEOUT);
                return -1;
            }
            if (sPreventImeStartupUnlessTextEditor) {
                Log.d(TAG, "Dropping event because IME is evicted: " + inputEvent);
            } else {
                Log.w(TAG, "Unable to send input event to IME: " + getImeIdLocked() + " dropping: " + inputEvent);
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishedInputEvent(int i, boolean z, boolean z2) {
        synchronized (this.mH) {
            int indexOfKey = this.mPendingEvents.indexOfKey(i);
            if (indexOfKey < 0) {
                return;
            }
            PendingEvent valueAt = this.mPendingEvents.valueAt(indexOfKey);
            this.mPendingEvents.removeAt(indexOfKey);
            Trace.traceCounter(4L, PENDING_EVENT_COUNTER, this.mPendingEvents.size());
            if (z2) {
                Log.w(TAG, "Timeout waiting for IME to handle input event after 2500 ms: " + valueAt.mInputMethodId);
            } else {
                this.mH.removeMessages(6, valueAt);
            }
            invokeFinishedInputEventCallback(valueAt, z);
        }
    }

    private void invokeFinishedInputEventCallback(PendingEvent pendingEvent, boolean z) {
        pendingEvent.mHandled = z;
        if (pendingEvent.mHandler.getLooper().isCurrentThread()) {
            pendingEvent.run();
            return;
        }
        Message obtain = Message.obtain(pendingEvent.mHandler, pendingEvent);
        obtain.setAsynchronous(true);
        obtain.sendToTarget();
    }

    private void flushPendingEventsLocked() {
        this.mH.removeMessages(7);
        int size = this.mPendingEvents.size();
        for (int i = 0; i < size; i++) {
            Message obtainMessage = this.mH.obtainMessage(7, this.mPendingEvents.keyAt(i), 0);
            obtainMessage.setAsynchronous(true);
            obtainMessage.sendToTarget();
        }
    }

    private PendingEvent obtainPendingEventLocked(InputEvent inputEvent, Object obj, String str, FinishedInputEventCallback finishedInputEventCallback, Handler handler) {
        PendingEvent acquire = this.mPendingEventPool.acquire();
        if (acquire == null) {
            acquire = new PendingEvent();
        }
        acquire.mEvent = inputEvent;
        acquire.mToken = obj;
        acquire.mInputMethodId = str;
        acquire.mCallback = finishedInputEventCallback;
        acquire.mHandler = handler;
        return acquire;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recyclePendingEventLocked(PendingEvent pendingEvent) {
        pendingEvent.recycle();
        this.mPendingEventPool.release(pendingEvent);
    }

    public void showInputMethodPicker() {
        synchronized (this.mH) {
            showInputMethodPickerLocked();
        }
    }

    public void showInputMethodPickerFromSystem(boolean z, int i) {
        IInputMethodManagerGlobalInvoker.showInputMethodPickerFromSystem(z ? 1 : 2, i);
    }

    private void showInputMethodPickerLocked() {
        Log.d(TAG, "showInputMethodPickerLocked");
        IInputMethodManagerGlobalInvoker.showInputMethodPickerFromClient(this.mClient, 0);
    }

    public boolean isInputMethodPickerShown() {
        return IInputMethodManagerGlobalInvoker.isInputMethodPickerShownForTest();
    }

    public void onImeSwitchButtonClickFromSystem(int i) {
        IInputMethodManagerGlobalInvoker.onImeSwitchButtonClickFromSystem(i);
    }

    public boolean shouldShowImeSwitcherButtonForTest() {
        return IInputMethodManagerGlobalInvoker.shouldShowImeSwitcherButtonForTest();
    }

    public boolean hasPendingImeVisibilityRequests() {
        return IInputMethodManagerGlobalInvoker.hasPendingImeVisibilityRequests();
    }

    public void finishTrackingPendingImeVisibilityRequests() {
        IInputMethodManagerGlobalInvoker.finishTrackingPendingImeVisibilityRequests();
    }

    public void showInputMethodAndSubtypeEnabler(String str) {
        Context context;
        synchronized (this.mH) {
            ViewRootImpl viewRootImpl = this.mCurRootView;
            context = viewRootImpl != null ? viewRootImpl.mContext : null;
        }
        if (context == null) {
            Application currentApplication = ActivityThread.currentApplication();
            context = currentApplication.createDisplayContext(((DisplayManager) currentApplication.getSystemService(DisplayManager.class)).getDisplay(this.mDisplayId));
        }
        Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS);
        intent.setFlags(TvSettingsEnums.PRIVACY_DIAGNOSTICS);
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra(Settings.EXTRA_INPUT_METHOD_ID, str);
        }
        context.startActivity(intent);
    }

    public InputMethodSubtype getCurrentInputMethodSubtype() {
        return IInputMethodManagerGlobalInvoker.getCurrentInputMethodSubtype(UserHandle.myUserId());
    }

    @Deprecated
    public boolean setCurrentInputMethodSubtype(InputMethodSubtype inputMethodSubtype) {
        Application currentApplication;
        if (Process.myUid() == 1000) {
            Log.w(TAG, "System process should not call setCurrentInputMethodSubtype() because almost always it is a bug under multi-user / multi-profile environment. Consider directly interacting with InputMethodManagerService via LocalServices.");
            return false;
        }
        if (inputMethodSubtype == null || (currentApplication = ActivityThread.currentApplication()) == null || currentApplication.checkSelfPermission(Manifest.permission.WRITE_SECURE_SETTINGS) != 0) {
            return false;
        }
        ContentResolver contentResolver = currentApplication.getContentResolver();
        String string = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD);
        if (ComponentName.unflattenFromString(string) == null) {
            return false;
        }
        List<InputMethodSubtype> enabledInputMethodSubtypeList = IInputMethodManagerGlobalInvoker.getEnabledInputMethodSubtypeList(string, true, UserHandle.myUserId());
        int size = enabledInputMethodSubtypeList.size();
        for (int i = 0; i < size; i++) {
            InputMethodSubtype inputMethodSubtype2 = enabledInputMethodSubtypeList.get(i);
            if (inputMethodSubtype2.equals(inputMethodSubtype)) {
                Settings.Secure.putInt(contentResolver, Settings.Secure.SELECTED_INPUT_METHOD_SUBTYPE, inputMethodSubtype2.hashCode());
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public void notifyUserAction() {
        Log.w(TAG, "notifyUserAction() is a hidden method, which is now just a stub method that does nothing.  Leave comments in b.android.com/114740982 if your  application still depends on the previous behavior of this method.");
    }

    public Map<InputMethodInfo, List<InputMethodSubtype>> getShortcutInputMethodsAndSubtypes() {
        List<InputMethodInfo> enabledInputMethodList = getEnabledInputMethodList();
        enabledInputMethodList.sort(Comparator.comparingInt(new ToIntFunction() { // from class: android.view.inputmethod.InputMethodManager$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return InputMethodManager.lambda$getShortcutInputMethodsAndSubtypes$8((InputMethodInfo) obj);
            }
        }));
        int size = enabledInputMethodList.size();
        for (int i = 0; i < size; i++) {
            InputMethodInfo inputMethodInfo = enabledInputMethodList.get(i);
            int size2 = getEnabledInputMethodSubtypeList(inputMethodInfo, true).size();
            for (int i2 = 0; i2 < size2; i2++) {
                InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i2);
                if (SUBTYPE_MODE_VOICE.equals(subtypeAt.getMode())) {
                    return Collections.singletonMap(inputMethodInfo, Collections.singletonList(subtypeAt));
                }
            }
        }
        return Collections.EMPTY_MAP;
    }

    static /* synthetic */ int lambda$getShortcutInputMethodsAndSubtypes$8(InputMethodInfo inputMethodInfo) {
        return !inputMethodInfo.isSystem() ? 1 : 0;
    }

    public int getInputMethodWindowVisibleHeight() {
        return IInputMethodManagerGlobalInvoker.getInputMethodWindowVisibleHeight(this.mClient);
    }

    public void setRequestCursorUpdateDisplayIdCheck(boolean z) {
        this.mRequestCursorUpdateDisplayIdCheck.set(z);
    }

    @Deprecated
    public boolean switchToLastInputMethod(IBinder iBinder) {
        return InputMethodPrivilegedOperationsRegistry.get(iBinder).switchToPreviousInputMethod();
    }

    @Deprecated
    public boolean switchToNextInputMethod(IBinder iBinder, boolean z) {
        return InputMethodPrivilegedOperationsRegistry.get(iBinder).switchToNextInputMethod(z);
    }

    @Deprecated
    public boolean shouldOfferSwitchingToNextInputMethod(IBinder iBinder) {
        return InputMethodPrivilegedOperationsRegistry.get(iBinder).shouldOfferSwitchingToNextInputMethod();
    }

    @Deprecated
    public void setAdditionalInputMethodSubtypes(String str, InputMethodSubtype[] inputMethodSubtypeArr) {
        IInputMethodManagerGlobalInvoker.setAdditionalInputMethodSubtypes(str, inputMethodSubtypeArr, UserHandle.myUserId());
    }

    public void setExplicitlyEnabledInputMethodSubtypes(String str, int[] iArr) {
        IInputMethodManagerGlobalInvoker.setExplicitlyEnabledInputMethodSubtypes(str, iArr, UserHandle.myUserId());
    }

    public InputMethodSubtype getLastInputMethodSubtype() {
        return IInputMethodManagerGlobalInvoker.getLastInputMethodSubtype(UserHandle.myUserId());
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (processDump(fileDescriptor, strArr)) {
            return;
        }
        PrintWriterPrinter printWriterPrinter = new PrintWriterPrinter(printWriter);
        printWriterPrinter.println("Input method client state for " + this + ":");
        StringBuilder sb = new StringBuilder("  mFallbackInputConnection=");
        sb.append(this.mFallbackInputConnection);
        printWriterPrinter.println(sb.toString());
        printWriterPrinter.println("  mActive=" + this.mActive + " mRestartOnNextWindowFocus=" + this.mRestartOnNextWindowFocus + " mBindSequence=" + getBindSequenceLocked() + " mCurImeId=" + getImeIdLocked());
        StringBuilder sb2 = new StringBuilder("  mFullscreenMode=");
        sb2.append(this.mFullscreenMode);
        printWriterPrinter.println(sb2.toString());
        if (isImeSessionAvailableLocked()) {
            printWriterPrinter.println("  mCurMethod=" + this.mCurBindState.mImeSession);
        } else {
            printWriterPrinter.println("  mCurMethod= null");
        }
        for (int i = 0; i < this.mAccessibilityInputMethodSession.size(); i++) {
            printWriterPrinter.println("  mAccessibilityInputMethodSession(" + this.mAccessibilityInputMethodSession.keyAt(i) + ")=" + this.mAccessibilityInputMethodSession.valueAt(i));
        }
        printWriterPrinter.println("  mCurRootView=" + this.mCurRootView);
        printWriterPrinter.println("  mServedView=" + getServedViewLocked());
        printWriterPrinter.println("  mNextServedView=" + getNextServedViewLocked());
        printWriterPrinter.println("  mServedConnecting=" + this.mServedConnecting);
        if (this.mCurrentEditorInfo != null) {
            printWriterPrinter.println("  mCurrentEditorInfo:");
            this.mCurrentEditorInfo.dump(printWriterPrinter, "    ", false);
        } else {
            printWriterPrinter.println("  mCurrentEditorInfo: null");
        }
        printWriterPrinter.println("  mServedInputConnection=" + this.mServedInputConnection);
        printWriterPrinter.println("  mServedInputConnectionHandler=" + this.mServedInputConnectionHandler);
        printWriterPrinter.println("  mLastPendingStartSeqId=" + this.mLastPendingStartSeqId);
        printWriterPrinter.println("  mCompletions=" + Arrays.toString(this.mCompletions));
        printWriterPrinter.println("  mCursorRect=" + this.mCursorRect);
        printWriterPrinter.println("  mCursorSelStart=" + this.mCursorSelStart + " mCursorSelEnd=" + this.mCursorSelEnd + " mCursorCandStart=" + this.mCursorCandStart + " mCursorCandEnd=" + this.mCursorCandEnd);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ConnectionlessHandwritingCallbackProxy extends IConnectionlessHandwritingCallback.Stub {
        private ConnectionlessHandwritingCallback mCallback;
        private Executor mExecutor;
        private final Object mLock = new Object();

        ConnectionlessHandwritingCallbackProxy(Executor executor, ConnectionlessHandwritingCallback connectionlessHandwritingCallback) {
            this.mExecutor = executor;
            this.mCallback = connectionlessHandwritingCallback;
        }

        @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
        public void onResult(final CharSequence charSequence) {
            final ConnectionlessHandwritingCallback connectionlessHandwritingCallback;
            synchronized (this.mLock) {
                Executor executor = this.mExecutor;
                if (executor != null && (connectionlessHandwritingCallback = this.mCallback) != null) {
                    this.mExecutor = null;
                    this.mCallback = null;
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        if (TextUtils.isEmpty(charSequence)) {
                            executor.execute(new Runnable() { // from class: android.view.inputmethod.InputMethodManager$ConnectionlessHandwritingCallbackProxy$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ConnectionlessHandwritingCallback.this.onError(0);
                                }
                            });
                        } else {
                            executor.execute(new Runnable() { // from class: android.view.inputmethod.InputMethodManager$ConnectionlessHandwritingCallbackProxy$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ConnectionlessHandwritingCallback.this.onResult(charSequence);
                                }
                            });
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }

        @Override // com.android.internal.inputmethod.IConnectionlessHandwritingCallback
        public void onError(final int i) {
            final ConnectionlessHandwritingCallback connectionlessHandwritingCallback;
            synchronized (this.mLock) {
                Executor executor = this.mExecutor;
                if (executor != null && (connectionlessHandwritingCallback = this.mCallback) != null) {
                    this.mExecutor = null;
                    this.mCallback = null;
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        executor.execute(new Runnable() { // from class: android.view.inputmethod.InputMethodManager$ConnectionlessHandwritingCallbackProxy$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ConnectionlessHandwritingCallback.this.onError(i);
                            }
                        });
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }
    }

    private final class ImeInputEventSender extends InputEventSender {
        public ImeInputEventSender(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventSender
        public void onInputEventFinished(int i, boolean z) {
            InputMethodManager.this.finishedInputEvent(i, z, false);
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
            synchronized (InputMethodManager.this.mH) {
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
            this.mImeId = inputBindResult.id;
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

    private boolean processDump(FileDescriptor fileDescriptor, String[] strArr) {
        if (strArr == null) {
            return false;
        }
        for (String str : strArr) {
            if (str.equals(ImeTracing.PROTO_ARG)) {
                ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
                dumpDebug(protoOutputStream, null);
                protoOutputStream.flush();
                return true;
            }
        }
        return false;
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, byte[] bArr) {
        synchronized (this.mH) {
            if (isImeSessionAvailableLocked()) {
                protoOutputStream.write(1120986464257L, this.mDisplayId);
                long start = protoOutputStream.start(1146756268034L);
                protoOutputStream.write(1138166333441L, this.mCurBindState.mImeId);
                protoOutputStream.write(1133871366146L, this.mFullscreenMode);
                protoOutputStream.write(1133871366148L, this.mActive);
                protoOutputStream.write(1133871366149L, this.mServedConnecting);
                protoOutputStream.write(1138166333446L, Objects.toString(this.mServedView));
                protoOutputStream.write(1138166333447L, Objects.toString(this.mNextServedView));
                protoOutputStream.end(start);
                ViewRootImpl viewRootImpl = this.mCurRootView;
                if (viewRootImpl != null) {
                    viewRootImpl.dumpDebug(protoOutputStream, 1146756268035L);
                }
                EditorInfo editorInfo = this.mCurrentEditorInfo;
                if (editorInfo != null) {
                    editorInfo.dumpDebug(protoOutputStream, 1146756268038L);
                }
                ImeInsetsSourceConsumer imeInsetsSourceConsumer = this.mImeInsetsConsumer;
                if (imeInsetsSourceConsumer != null) {
                    imeInsetsSourceConsumer.dumpDebug(protoOutputStream, 1146756268037L);
                }
                RemoteInputConnectionImpl remoteInputConnectionImpl = this.mServedInputConnection;
                if (remoteInputConnectionImpl != null) {
                    remoteInputConnectionImpl.dumpDebug(protoOutputStream, 1146756268040L);
                }
                if (bArr != null) {
                    protoOutputStream.write(1146756268041L, bArr);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forAccessibilitySessionsLocked(Consumer<IAccessibilityInputMethodSessionInvoker> consumer) {
        for (int i = 0; i < this.mAccessibilityInputMethodSession.size(); i++) {
            consumer.accept(this.mAccessibilityInputMethodSession.valueAt(i));
        }
    }

    private static Pair<InputConnection, EditorInfo> createInputConnection(View view) {
        EditorInfo editorInfo = new EditorInfo();
        editorInfo.packageName = view.getContext().getOpPackageName();
        editorInfo.setAutofillId(view.getAutofillId());
        editorInfo.fieldId = view.getId();
        InputConnection onCreateInputConnection = view.onCreateInputConnection(editorInfo);
        if (DEBUG) {
            Log.v(TAG, "Starting input: editorInfo=" + editorInfo + " ic=" + onCreateInputConnection);
        }
        if (onCreateInputConnection == null) {
            editorInfo.setAutofillId(AutofillId.NO_AUTOFILL_ID);
            editorInfo.fieldId = 0;
        }
        return new Pair<>(onCreateInputConnection, editorInfo);
    }

    private void semToggleSoftInput(int i, int i2) {
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#toggleSoftInput", this, null);
        synchronized (this.mH) {
            View servedViewLocked = getServedViewLocked();
            Log.i(TAG_LIFE_CYCLE, "stsi(" + i + ", " + i2 + "), served view : " + servedViewLocked);
            if (servedViewLocked != null) {
                if (isInputMethodShown()) {
                    hideSoftInputFromWindow(servedViewLocked.getWindowToken(), i2, null, 25, null);
                } else {
                    showSoftInput(servedViewLocked, null, i, null, 24);
                }
            }
        }
    }

    public void semShowSoftInput(int i, ResultReceiver resultReceiver) {
        Log.i(TAG_LIFE_CYCLE, "semShowSoftInput - flag : " + i);
        synchronized (this.mH) {
            View servedViewLocked = getServedViewLocked();
            if (servedViewLocked != null) {
                showSoftInput(servedViewLocked, i, resultReceiver);
            } else {
                showSoftInputUnchecked(i, resultReceiver);
            }
        }
    }

    @Deprecated
    public boolean semIsInputMethodShown() {
        return isInputMethodShown();
    }

    public boolean isInputMethodShown() {
        return IInputMethodManagerGlobalInvoker.isInputMethodShown();
    }

    public boolean semMinimizeSoftInput(IBinder iBinder, int i) {
        Log.i(TAG_LIFE_CYCLE, "semMinimizeSoftInput");
        return minimizeSoftInput(iBinder, i);
    }

    public boolean minimizeSoftInput(IBinder iBinder, int i) {
        Log.i(TAG_LIFE_CYCLE, "minimizeSoftInput h " + i);
        checkFocus();
        synchronized (this.mH) {
            View servedViewLocked = getServedViewLocked();
            if (servedViewLocked != null && servedViewLocked.getWindowToken() == iBinder) {
                return IInputMethodManagerGlobalInvoker.minimizeSoftInput(this.mClient, i);
            }
            printLog("minimizeSoftInput: cancel", iBinder, servedViewLocked);
            return false;
        }
    }

    private void printLog(String str, IBinder iBinder, View view) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" windowToken=");
        String str2 = PerfettoProtoLogImpl.NULL_STRING;
        Object obj = iBinder;
        if (iBinder == null) {
            obj = PerfettoProtoLogImpl.NULL_STRING;
        }
        sb.append(obj);
        sb.append(" servedView=");
        if (view != null) {
            str2 = InputMethodDebug.dumpViewInfo(view);
        }
        sb.append(str2);
        sb.append(" mDisplayId=");
        sb.append(this.mDisplayId);
        Log.i(TAG, sb.toString());
    }

    public boolean semForceHideSoftInput() {
        if (DEBUG_SEP) {
            Log.i(TAG_LIFE_CYCLE, "semForceHideSoftInput");
        }
        return forceHideSoftInput();
    }

    public boolean forceHideSoftInput() {
        if (DEBUG_SEP) {
            Log.i(TAG_LIFE_CYCLE, "forceHideSoftInput");
        }
        return forceHideSoftInput(null);
    }

    public boolean forceHideSoftInput(ResultReceiver resultReceiver) {
        Log.i(TAG_LIFE_CYCLE, "fhsi()");
        if (SemInputMethodManagerUtils.DEBUG_CALL_STACK) {
            Log.d(TAG, "forceHideSoftInput callers=" + Debug.getCallers(10));
        }
        if (Flags.refactorInsetsController()) {
            IInputMethodManagerGlobalInvoker.forceHideSoftInput();
            return true;
        }
        ImeTracker.Token onStart = ImeTracker.forLogging().onStart(2, 5, 64, false);
        ImeTracker.forLatency().onRequestHide(onStart, 5, 64, new InsetsController$$ExternalSyntheticLambda2());
        ImeTracing.getInstance().triggerClientDump("InputMethodManager#forceHideSoftInput", this, null);
        synchronized (this.mH) {
            ImeTracker.forLogging().onProgress(onStart, 1);
            View servedViewLocked = getServedViewLocked();
            if (servedViewLocked == null) {
                return IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, null, onStart, 0, resultReceiver, 64, this.mAsyncShowHideMethodEnabled);
            }
            return IInputMethodManagerGlobalInvoker.hideSoftInput(this.mClient, servedViewLocked.getWindowToken(), onStart, 0, resultReceiver, 64, this.mAsyncShowHideMethodEnabled);
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
        synchronized (this.mH) {
            dismissAndShowAgainInputMethodPickerLocked();
        }
    }

    private void dismissAndShowAgainInputMethodPickerLocked() {
        IInputMethodManagerGlobalInvoker.dismissAndShowAgainInputMethodPicker();
    }

    public boolean isCurrentInputMethodAsSamsungKeyboard() {
        boolean isCurrentInputMethodAsSamsungKeyboard;
        synchronized (this.mH) {
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
        View servedViewLocked = getServedViewLocked();
        if (servedViewLocked != null && servedViewLocked.getContext() != null) {
            IInputMethodManagerGlobalInvoker.setInputMethodSwitchDisable(this.mClient, servedViewLocked.getContext().getPackageName().contains("com.samsung.android.honeyboard"));
        } else {
            IInputMethodManagerGlobalInvoker.setInputMethodSwitchDisable(this.mClient, false);
        }
    }

    public boolean getDexSettingsValue(String str, String str2) {
        Log.d(TAG, "getDexSettingsValue");
        return IInputMethodManagerGlobalInvoker.getDexSettingsValue(str, str2);
    }

    private void handleVoiceHWKey() {
        checkFocus();
        View servedViewLocked = getServedViewLocked();
        if (servedViewLocked == null) {
            Log.d(TAG, "handleVoiceHWKey: have no served view");
        } else if (!hasActiveInputConnection(servedViewLocked)) {
            Log.d(TAG, "handleVoiceHWKey: have no active input connection");
        } else {
            IInputMethodManagerGlobalInvoker.handleVoiceHWKey();
        }
    }

    public boolean usingWritingToolkit() {
        return SemInputMethodManagerUtils.METHOD_ID_TOOLKIT_HONEYBOARD.equals(this.mCurId);
    }

    public void showInputMethodPickerFromSystemWithUserId(boolean z, int i, int i2) {
        IInputMethodManagerGlobalInvoker.showInputMethodPickerFromSystemWithUserId(z ? 1 : 2, i, i2);
    }
}
