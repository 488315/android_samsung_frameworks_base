package android.inputmethodservice;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.graphics.Region;
import android.inputmethodservice.AbstractInputMethodService;
import android.inputmethodservice.InkWindow;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.Settings;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InlineSuggestionsRequest;
import android.view.inputmethod.InlineSuggestionsResponse;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.view.inputmethod.SemInputMethodManagerUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.CompatOnBackInvokedCallback;
import android.window.ImeOnBackInvokedDispatcher;
import android.window.WindowMetricsHelper;
import com.android.internal.R;
import com.android.internal.inputmethod.IConnectionlessHandwritingCallback;
import com.android.internal.inputmethod.IInlineSuggestionsRequestCallback;
import com.android.internal.inputmethod.IInputContentUriToken;
import com.android.internal.inputmethod.IInputMethod;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;
import com.android.internal.inputmethod.InputMethodPrivilegedOperations;
import com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry;
import com.android.internal.util.RingBuffer;
import com.samsung.android.rune.ViewRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class InputMethodService extends AbstractInputMethodService {
    public static final int BACK_DISPOSITION_ADJUST_NOTHING = 3;
    public static final int BACK_DISPOSITION_DEFAULT = 0;
    private static final int BACK_DISPOSITION_MAX = 3;
    private static final int BACK_DISPOSITION_MIN = 0;

    @Deprecated
    public static final int BACK_DISPOSITION_WILL_DISMISS = 2;

    @Deprecated
    public static final int BACK_DISPOSITION_WILL_NOT_DISMISS = 1;
    static final boolean DEBUG = false;
    public static final long DISALLOW_INPUT_METHOD_INTERFACE_OVERRIDE = 148086656;
    public static final long FINISH_INPUT_NO_FALLBACK_CONNECTION = 156215187;
    public static final int IME_ACTIVE = 1;
    public static final int IME_VISIBLE = 2;
    private static final int MAX_EVENTS_BUFFER = 500;
    private static final boolean MINIMIZED_IME_INSET_ANIM = SystemProperties.getBoolean("persist.wm.enable.minimized_ime.anim", false);
    static final int MOVEMENT_DOWN = -1;
    static final int MOVEMENT_UP = -2;
    private static final String PROP_CAN_RENDER_GESTURAL_NAV_BUTTONS = "persist.sys.ime.can_render_gestural_nav_buttons";
    private static final long STYLUS_HANDWRITING_IDLE_TIMEOUT_MAX_MS = 30000;
    private static final long STYLUS_HANDWRITING_IDLE_TIMEOUT_MS = 10000;
    private static final long STYLUS_WINDOW_IDLE_TIMEOUT_MILLIS = 300000;
    static final String TAG = "InputMethodService";
    private static final long TIMEOUT_SURFACE_REMOVAL_MILLIS = 500;
    int mBackDisposition;
    FrameLayout mCandidatesFrame;
    boolean mCandidatesViewStarted;
    int mCandidatesVisibility;
    private IConnectionlessHandwritingCallback mConnectionlessHandwritingCallback;
    CompletionInfo[] mCurCompletions;
    private IBinder mCurHideInputToken;
    private IBinder mCurShowInputToken;
    private ImeTracker.Token mCurStatsToken;
    private boolean mCustomImeSwitcherButtonRequestedVisible;
    boolean mDecorViewVisible;
    boolean mDecorViewWasVisible;
    private boolean mDestroyed;
    ViewGroup mExtractAccessories;
    View mExtractAction;
    ExtractEditText mExtractEditText;
    FrameLayout mExtractFrame;
    View mExtractView;
    boolean mExtractViewHidden;
    ExtractedText mExtractedText;
    int mExtractedToken;
    private Runnable mFinishHwRunnable;
    boolean mFullscreenApplied;
    ViewGroup mFullscreenArea;
    private Handler mHandler;
    private CharSequence mHandwritingDelegationText;
    private InputEventReceiver mHandwritingEventReceiver;
    private ImeOnBackInvokedDispatcher mImeDispatcher;
    private Runnable mImeSurfaceRemoverRunnable;
    private int mImeWindowVisibility;
    InputMethodManager mImm;
    boolean mInShowWindow;
    LayoutInflater mInflater;
    boolean mInitialized;
    private InkWindow mInkWindow;
    private InlineSuggestionSessionController mInlineSuggestionSessionController;
    InputBinding mInputBinding;
    InputConnection mInputConnection;
    EditorInfo mInputEditorInfo;
    FrameLayout mInputFrame;
    boolean mInputStarted;
    View mInputView;
    boolean mInputViewStarted;
    private boolean mIsConnectionlessHandwritingForDelegation;
    boolean mIsFullscreen;
    boolean mIsInputViewShown;
    private boolean mIsLastWindowVisible;
    private Region mLastHandwritingRegion;
    boolean mLastShowInputRequested;
    private boolean mLastWasInFullscreenMode;
    private boolean mNotifyUserActionSent;
    private boolean mOnPreparedStylusHwCalled;
    private RingBuffer<MotionEvent> mPendingEvents;
    View mRootView;
    private SettingsObserver mSettingsObserver;
    int mShowInputFlags;
    boolean mShowInputRequested;
    InputConnection mStartedInputConnection;
    int mStatusIcon;
    private long mStylusWindowIdleTimeoutForTest;
    private Runnable mStylusWindowIdleTimeoutRunnable;
    private Context mTargetDisplayContext;
    TypedArray mThemeAttrs;
    IBinder mToken;
    boolean mViewsCreated;
    SoftInputWindow mWindow;
    boolean mWindowVisible;
    private boolean mBackCallbackRegistered = false;
    private final CompatOnBackInvokedCallback mCompatBackCallback = new CompatOnBackInvokedCallback() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda2
        @Override // android.window.CompatOnBackInvokedCallback, android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            this.f$0.compatHandleBack();
        }
    };
    private long mStylusHwSessionsTimeout = 10000;
    private boolean mUsingCtrlShiftShortcut = false;
    private final boolean mCanImeRenderGesturalNavButtons = canImeRenderGesturalNavButtons();
    private InputMethodPrivilegedOperations mPrivOps = new InputMethodPrivilegedOperations();
    private final NavigationBarController mNavigationBarController = new NavigationBarController(this);
    int mTheme = 0;
    private Object mLock = new Object();
    final Insets mTmpInsets = new Insets();
    final int[] mTmpLocation = new int[2];
    private OptionalInt mHandwritingRequestId = OptionalInt.empty();
    private ImsConfigurationTracker mConfigTracker = new ImsConfigurationTracker();
    boolean minimized = false;
    boolean needSetLayout = false;
    int mMinimizedHeight = 0;
    private int mCurrentNightMode = 0;
    private int mCurrentOrientation = 1;
    final ViewTreeObserver.OnComputeInternalInsetsListener mInsetsComputer = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda3
        @Override // android.view.ViewTreeObserver.OnComputeInternalInsetsListener
        public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
            this.f$0.lambda$new$0(internalInsetsInfo);
        }
    };
    final View.OnClickListener mActionClickListener = new View.OnClickListener() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda4
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f$0.lambda$new$1(view);
        }
    };
    private final ImeTracing.ServiceDumper mDumper = new ImeTracing.ServiceDumper() { // from class: android.inputmethodservice.InputMethodService.2
        @Override // com.android.internal.inputmethod.ImeTracing.ServiceDumper
        public void dumpToProto(ProtoOutputStream protoOutputStream, byte[] bArr) {
            long jStart = protoOutputStream.start(1146756268035L);
            InputMethodService.this.mWindow.dumpDebug(protoOutputStream, 1146756268033L);
            protoOutputStream.write(1133871366146L, InputMethodService.this.mViewsCreated);
            protoOutputStream.write(1133871366147L, InputMethodService.this.mDecorViewVisible);
            protoOutputStream.write(1133871366148L, InputMethodService.this.mDecorViewWasVisible);
            protoOutputStream.write(1133871366149L, InputMethodService.this.mWindowVisible);
            protoOutputStream.write(1133871366150L, InputMethodService.this.mInShowWindow);
            protoOutputStream.write(1138166333447L, InputMethodService.this.getResources().getConfiguration().toString());
            protoOutputStream.write(1138166333448L, Objects.toString(InputMethodService.this.mToken));
            protoOutputStream.write(1138166333449L, Objects.toString(InputMethodService.this.mInputBinding));
            protoOutputStream.write(1133871366154L, InputMethodService.this.mInputStarted);
            protoOutputStream.write(1133871366155L, InputMethodService.this.mInputViewStarted);
            protoOutputStream.write(1133871366156L, InputMethodService.this.mCandidatesViewStarted);
            if (InputMethodService.this.mInputEditorInfo != null) {
                InputMethodService.this.mInputEditorInfo.dumpDebug(protoOutputStream, 1146756268045L);
            }
            protoOutputStream.write(1133871366158L, InputMethodService.this.mShowInputRequested);
            protoOutputStream.write(1133871366159L, InputMethodService.this.mLastShowInputRequested);
            protoOutputStream.write(1120986464274L, InputMethodService.this.mShowInputFlags);
            protoOutputStream.write(1120986464275L, InputMethodService.this.mCandidatesVisibility);
            protoOutputStream.write(1133871366164L, InputMethodService.this.mFullscreenApplied);
            protoOutputStream.write(1133871366165L, InputMethodService.this.mIsFullscreen);
            protoOutputStream.write(1133871366166L, InputMethodService.this.mExtractViewHidden);
            protoOutputStream.write(1120986464279L, InputMethodService.this.mExtractedToken);
            protoOutputStream.write(1133871366168L, InputMethodService.this.mIsInputViewShown);
            protoOutputStream.write(1120986464281L, InputMethodService.this.mStatusIcon);
            InputMethodService.this.mTmpInsets.dumpDebug(protoOutputStream, 1146756268058L);
            protoOutputStream.write(1138166333467L, Objects.toString(InputMethodService.this.mSettingsObserver));
            if (bArr != null) {
                protoOutputStream.write(1146756268060L, bArr);
            }
            protoOutputStream.end(jStart);
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface BackDispositionMode {
    }

    public @interface ImeWindowVisibility {
    }

    private int getIconForImeAction(int i) {
        switch (i & 255) {
            case 2:
                return R.drawable.ic_input_extract_action_go;
            case 3:
                return R.drawable.ic_input_extract_action_search;
            case 4:
                return R.drawable.ic_input_extract_action_send;
            case 5:
                return R.drawable.ic_input_extract_action_next;
            case 6:
                return R.drawable.ic_input_extract_action_done;
            case 7:
                return R.drawable.ic_input_extract_action_previous;
            default:
                return R.drawable.ic_input_extract_action_return;
        }
    }

    public void doMinimizeSoftInput(int i) {
    }

    public void onAppPrivateCommand(String str, Bundle bundle) {
    }

    public void onBindInput() {
    }

    public View onCreateCandidatesView() {
        return null;
    }

    public InlineSuggestionsRequest onCreateInlineSuggestionsRequest(Bundle bundle) {
        return null;
    }

    public View onCreateInputView() {
        return null;
    }

    protected void onCurrentInputMethodSubtypeChanged(InputMethodSubtype inputMethodSubtype) {
    }

    public void onCustomImeSwitcherButtonRequestedVisible(boolean z) {
    }

    public void onDisplayCompletions(CompletionInfo[] completionInfoArr) {
    }

    public void onFinishStylusHandwriting() {
    }

    @Override // android.inputmethodservice.AbstractInputMethodService
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    public void onInitializeInterface() {
    }

    public boolean onInlineSuggestionsResponse(InlineSuggestionsResponse inlineSuggestionsResponse) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return false;
    }

    public void onPrepareStylusHandwriting() {
    }

    @Override // android.inputmethodservice.AbstractInputMethodService
    public boolean onShouldVerifyKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    public void onStartCandidatesView(EditorInfo editorInfo, boolean z) {
    }

    public boolean onStartConnectionlessStylusHandwriting(int i, CursorAnchorInfo cursorAnchorInfo) {
        return false;
    }

    public void onStartInput(EditorInfo editorInfo, boolean z) {
    }

    public void onStartInputView(EditorInfo editorInfo, boolean z) {
    }

    public boolean onStartStylusHandwriting() {
        return false;
    }

    @Override // android.inputmethodservice.AbstractInputMethodService
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    public void onUnbindInput() {
    }

    @Deprecated
    public void onUpdateCursor(Rect rect) {
    }

    public void onUpdateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) {
    }

    public void onUpdateEditorToolType(int i) {
    }

    @Deprecated
    public void onViewClicked(boolean z) {
    }

    public void onWindowHidden() {
    }

    public void onWindowShown() {
    }

    public int setMinimizeSoftInputInsets() {
        return 0;
    }

    public void undoMinimizeSoftInput() {
    }

    public static boolean canImeRenderGesturalNavButtons() {
        return SystemProperties.getBoolean(PROP_CAN_RENDER_GESTURAL_NAV_BUTTONS, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        onComputeInsets(this.mTmpInsets);
        this.mNavigationBarController.updateInsets(this.mTmpInsets);
        if (!this.mViewsCreated) {
            this.mTmpInsets.visibleTopInsets = 0;
        }
        if (isExtractViewShown()) {
            View decorView = getWindow().getWindow().getDecorView();
            Rect rect = internalInsetsInfo.contentInsets;
            Rect rect2 = internalInsetsInfo.visibleInsets;
            int height = decorView.getHeight();
            rect2.top = height;
            rect.top = height;
            internalInsetsInfo.touchableRegion.setEmpty();
            internalInsetsInfo.setTouchableInsets(0);
        } else {
            internalInsetsInfo.contentInsets.top = this.mTmpInsets.contentTopInsets;
            internalInsetsInfo.visibleInsets.top = this.mTmpInsets.visibleTopInsets;
            internalInsetsInfo.touchableRegion.set(this.mTmpInsets.touchableRegion);
            internalInsetsInfo.setTouchableInsets(this.mTmpInsets.touchableInsets);
        }
        if (MINIMIZED_IME_INSET_ANIM) {
            internalInsetsInfo.minimizedInsets.top = this.mMinimizedHeight;
            internalInsetsInfo.contentInsets.top += this.mMinimizedHeight;
            internalInsetsInfo.visibleInsets.top += this.mMinimizedHeight;
        }
        this.mNavigationBarController.updateTouchableInsets(this.mTmpInsets, internalInsetsInfo);
        if (this.mInputFrame != null) {
            setImeExclusionRect(this.mTmpInsets.visibleTopInsets);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        EditorInfo currentInputEditorInfo = getCurrentInputEditorInfo();
        InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputEditorInfo == null || currentInputConnection == null) {
            return;
        }
        if (currentInputEditorInfo.actionId != 0) {
            currentInputConnection.performEditorAction(currentInputEditorInfo.actionId);
        } else if ((currentInputEditorInfo.imeOptions & 255) != 1) {
            currentInputConnection.performEditorAction(currentInputEditorInfo.imeOptions & 255);
        }
    }

    public class InputMethodImpl extends AbstractInputMethodService.AbstractInputMethodImpl {
        private boolean mSimultaneousStylusAndTouchEnabled;
        private boolean mSystemCallingHideSoftInput;
        private boolean mSystemCallingShowSoftInput;

        public InputMethodImpl() {
            super();
        }

        @Override // android.view.inputmethod.InputMethod
        public final void initializeInternal(IInputMethod.InitParams initParams) {
            Trace.traceBegin(32L, "IMS.initializeInternal");
            InputMethodService.this.mPrivOps.set(initParams.privilegedOperations);
            InputMethodPrivilegedOperationsRegistry.put(initParams.token, InputMethodService.this.mPrivOps);
            onNavButtonFlagsChanged(initParams.navigationBarFlags);
            attachToken(initParams.token);
            Trace.traceEnd(32L);
        }

        @Override // android.view.inputmethod.InputMethod
        public void onCreateInlineSuggestionsRequest(InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {
            InputMethodService.this.mInlineSuggestionSessionController.onMakeInlineSuggestionsRequest(inlineSuggestionsRequestInfo, iInlineSuggestionsRequestCallback);
        }

        @Override // android.view.inputmethod.InputMethod
        public void attachToken(IBinder iBinder) {
            if (InputMethodService.this.mToken != null) {
                throw new IllegalStateException("attachToken() must be called at most once. token=" + iBinder);
            }
            InputMethodService.this.attachToWindowToken(iBinder);
            InputMethodService.this.mToken = iBinder;
            InputMethodService.this.mWindow.setToken(iBinder);
        }

        @Override // android.view.inputmethod.InputMethod
        public void bindInput(InputBinding inputBinding) {
            Trace.traceBegin(32L, "IMS.bindInput");
            InputMethodService.this.mInputBinding = inputBinding;
            InputMethodService.this.mInputConnection = inputBinding.getConnection();
            InputMethodService.this.reportFullscreenMode();
            InputMethodService.this.initialize();
            InputMethodService.this.onBindInput();
            InputMethodService.this.mConfigTracker.onBindInput(InputMethodService.this.getResources());
            Trace.traceEnd(32L);
        }

        @Override // android.view.inputmethod.InputMethod
        public void unbindInput() {
            InputMethodService.this.onUnbindInput();
            InputMethodService.this.mInputBinding = null;
            InputMethodService.this.mInputConnection = null;
            if (InputMethodService.this.mInkWindow != null) {
                finishStylusHandwriting();
                InputMethodService.this.scheduleStylusWindowIdleTimeout();
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void startInput(InputConnection inputConnection, EditorInfo editorInfo) {
            Trace.traceBegin(32L, "IMS.startInput");
            InputMethodService.this.doStartInput(inputConnection, editorInfo, false);
            Trace.traceEnd(32L);
        }

        @Override // android.view.inputmethod.InputMethod
        public void restartInput(InputConnection inputConnection, EditorInfo editorInfo) {
            Trace.traceBegin(32L, "IMS.restartInput");
            InputMethodService.this.doStartInput(inputConnection, editorInfo, true);
            Trace.traceEnd(32L);
        }

        @Override // android.view.inputmethod.InputMethod
        public final void dispatchStartInput(InputConnection inputConnection, IInputMethod.StartInputParams startInputParams) {
            InputMethodService.this.mPrivOps.reportStartInputAsync(startInputParams.startInputToken);
            onNavButtonFlagsChanged(startInputParams.navigationBarFlags);
            if (startInputParams.restarting) {
                restartInput(inputConnection, startInputParams.editorInfo);
            } else {
                startInput(inputConnection, startInputParams.editorInfo);
            }
            InputMethodService.this.mImeDispatcher = startInputParams.imeDispatcher;
            if (InputMethodService.this.mWindow != null) {
                InputMethodService.this.mWindow.getOnBackInvokedDispatcher().setImeOnBackInvokedDispatcher(startInputParams.imeDispatcher);
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void onNavButtonFlagsChanged(int i) {
            InputMethodService.this.mNavigationBarController.onNavButtonFlagsChanged(i);
            if (InputMethodService.this.mCanImeRenderGesturalNavButtons) {
                return;
            }
            boolean z = ((i & 2) != 0) && InputMethodService.this.getApplicationContext().getResources().getBoolean(R.bool.config_hideNavBarForKeyboard);
            if (z != InputMethodService.this.mCustomImeSwitcherButtonRequestedVisible) {
                InputMethodService.this.mCustomImeSwitcherButtonRequestedVisible = z;
                InputMethodService.this.onCustomImeSwitcherButtonRequestedVisible(z);
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void hideSoftInputWithToken(int i, ResultReceiver resultReceiver, IBinder iBinder, ImeTracker.Token token) {
            this.mSystemCallingHideSoftInput = true;
            InputMethodService.this.mCurHideInputToken = iBinder;
            InputMethodService.this.mCurStatsToken = token;
            try {
                hideSoftInput(i, resultReceiver);
            } finally {
                InputMethodService.this.mCurHideInputToken = null;
                this.mSystemCallingHideSoftInput = false;
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void hideSoftInput(int i, ResultReceiver resultReceiver) {
            ImeTracker.Token tokenCreateStatsToken;
            Log.i(InputMethod.TAG, "hideSoftInput(): flags=" + i);
            int i2 = 0;
            if (InputMethodService.this.mCurStatsToken != null) {
                tokenCreateStatsToken = InputMethodService.this.mCurStatsToken;
            } else {
                InputMethodService inputMethodService = InputMethodService.this;
                tokenCreateStatsToken = inputMethodService.createStatsToken(false, 41, ImeTracker.isFromUser(inputMethodService.mRootView));
            }
            InputMethodService.this.mCurStatsToken = null;
            if (InputMethodService.this.getApplicationInfo().targetSdkVersion >= 30 && !this.mSystemCallingHideSoftInput) {
                Log.e(InputMethod.TAG, "IME shouldn't call hideSoftInput on itself. Use requestHideSelf(int) itself");
                ImeTracker.forLogging().onFailed(tokenCreateStatsToken, 14);
                return;
            }
            ImeTracker.forLogging().onProgress(tokenCreateStatsToken, 14);
            Trace.traceBegin(32L, "IMS.hideSoftInput");
            ImeTracing.getInstance().triggerServiceDump("InputMethodService.InputMethodImpl#hideSoftInput", InputMethodService.this.mDumper, null);
            boolean zIsInputViewShown = InputMethodService.this.isInputViewShown();
            InputMethodService.this.mShowInputFlags = 0;
            InputMethodService.this.mShowInputRequested = false;
            InputMethodService.this.mCurStatsToken = tokenCreateStatsToken;
            InputMethodService.this.hideWindow();
            boolean z = InputMethodService.this.isInputViewShown() != zIsInputViewShown;
            if (resultReceiver != null) {
                if (z) {
                    i2 = 3;
                } else if (!zIsInputViewShown) {
                    i2 = 1;
                }
                resultReceiver.send(i2, null);
            }
            Trace.traceEnd(32L);
            if (Flags.refactorInsetsController()) {
                InputMethodService.this.scheduleImeSurfaceRemoval();
                ImeTracker.forLogging().onHidden(tokenCreateStatsToken);
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void showSoftInputWithToken(int i, ResultReceiver resultReceiver, IBinder iBinder, ImeTracker.Token token) {
            this.mSystemCallingShowSoftInput = true;
            InputMethodService.this.mCurShowInputToken = iBinder;
            InputMethodService.this.mCurStatsToken = token;
            try {
                showSoftInput(i, resultReceiver);
            } finally {
                InputMethodService.this.mCurShowInputToken = null;
                this.mSystemCallingShowSoftInput = false;
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void showSoftInput(int i, ResultReceiver resultReceiver) {
            ImeTracker.Token tokenCreateStatsToken;
            Log.i(InputMethod.TAG, "showSoftInput(): flags=" + i);
            int i2 = 1;
            if (InputMethodService.this.mCurStatsToken != null) {
                tokenCreateStatsToken = InputMethodService.this.mCurStatsToken;
            } else {
                InputMethodService inputMethodService = InputMethodService.this;
                tokenCreateStatsToken = inputMethodService.createStatsToken(true, 40, ImeTracker.isFromUser(inputMethodService.mRootView));
            }
            InputMethodService.this.mCurStatsToken = null;
            if (InputMethodService.this.getApplicationInfo().targetSdkVersion >= 30 && !this.mSystemCallingShowSoftInput) {
                Log.e(InputMethod.TAG, "IME shouldn't call showSoftInput on itself. Use requestShowSelf(int) itself");
                ImeTracker.forLogging().onFailed(tokenCreateStatsToken, 13);
                return;
            }
            ImeTracker.forLogging().onProgress(tokenCreateStatsToken, 13);
            Trace.traceBegin(32L, "IMS.showSoftInput");
            ImeTracing.getInstance().triggerServiceDump("InputMethodService.InputMethodImpl#showSoftInput", InputMethodService.this.mDumper, null);
            boolean zIsInputViewShown = InputMethodService.this.isInputViewShown();
            if (InputMethodService.this.needSetLayout || InputMethodService.this.minimized) {
                InputMethodService.this.mWindow.getWindow().setLayout(-1, -2);
                unMinimizeSoftInput();
                InputMethodService.this.needSetLayout = false;
            }
            if (InputMethodService.this.dispatchOnShowInputRequested(i, false)) {
                ImeTracker.forLogging().onProgress(tokenCreateStatsToken, 15);
                InputMethodService.this.mCurStatsToken = tokenCreateStatsToken;
                InputMethodService.this.showWindow(true);
            } else {
                ImeTracker.forLogging().onFailed(tokenCreateStatsToken, 15);
                if ((InputMethodService.this.mDecorViewVisible && InputMethodService.this.mWindowVisible) || InputMethodService.this.mHandwritingRequestId.isPresent()) {
                    Log.d(InputMethod.TAG, "ime cancels a show input request. reason: visible or hwr");
                } else {
                    Log.w(InputMethod.TAG, "ime cancels a show input request. reason: polices.");
                    InputMethodService.this.requestHideSelf(0);
                }
            }
            InputMethodService inputMethodService2 = InputMethodService.this;
            inputMethodService2.setImeWindowVisibility(inputMethodService2.computeImeWindowVis());
            boolean z = InputMethodService.this.isInputViewShown() != zIsInputViewShown;
            if (resultReceiver != null) {
                if (z) {
                    i2 = 2;
                } else if (zIsInputViewShown) {
                    i2 = 0;
                }
                resultReceiver.send(i2, null);
            }
            InputMethodService.this.mIsPressBtnSIPOnOff = false;
            Trace.traceEnd(32L);
        }

        @Override // android.view.inputmethod.InputMethod
        public void updateEditorToolType(int i) {
            InputMethodService.this.updateEditorToolTypeInternal(i);
        }

        @Override // android.view.inputmethod.InputMethod
        public void canStartStylusHandwriting(int i, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, CursorAnchorInfo cursorAnchorInfo, boolean z) {
            if (InputMethodService.this.mHandwritingRequestId.isPresent()) {
                Log.d(InputMethod.TAG, "There is an ongoing Handwriting session. ignoring.");
                return;
            }
            if (!InputMethodService.this.mInputStarted) {
                Log.d(InputMethod.TAG, "Input should have started before starting Stylus handwriting.");
                return;
            }
            if (InputMethodService.this.mDestroyed) {
                Log.w(InputMethod.TAG, "canStartStylusHandwriting: a service is invalid.");
            }
            maybeCreateAndInitInkWindow();
            if (!InputMethodService.this.mOnPreparedStylusHwCalled) {
                InputMethodService.this.onPrepareStylusHandwriting();
            }
            InputMethodService.this.mOnPreparedStylusHwCalled = false;
            if (iConnectionlessHandwritingCallback != null) {
                if (InputMethodService.this.onStartConnectionlessStylusHandwriting(1, cursorAnchorInfo)) {
                    InputMethodService.this.mConnectionlessHandwritingCallback = iConnectionlessHandwritingCallback;
                    InputMethodService.this.mIsConnectionlessHandwritingForDelegation = z;
                    InputMethodService.this.cancelStylusWindowIdleTimeout();
                    InputMethodService.this.mPrivOps.onStylusHandwritingReady(i, Process.myPid());
                    return;
                }
                Log.i(InputMethod.TAG, "IME is not ready or doesn't currently support connectionless handwriting");
                try {
                    iConnectionlessHandwritingCallback.onError(1);
                    return;
                } catch (RemoteException e) {
                    Log.e(InputMethod.TAG, "Couldn't send connectionless handwriting error result", e);
                    return;
                }
            }
            if (InputMethodService.this.onStartStylusHandwriting()) {
                InputMethodService.this.cancelStylusWindowIdleTimeout();
                InputMethodService.this.mPrivOps.onStylusHandwritingReady(i, Process.myPid());
            } else {
                Log.i(InputMethod.TAG, "IME is not ready. Can't start Stylus Handwriting");
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void startStylusHandwriting(int i, InputChannel inputChannel, List<MotionEvent> list) {
            Objects.requireNonNull(inputChannel);
            Objects.requireNonNull(list);
            if (InputMethodService.this.mHandwritingRequestId.isPresent()) {
                return;
            }
            InputMethodService.this.mHandwritingRequestId = OptionalInt.of(i);
            InputMethodService.this.mShowInputRequested = false;
            InputMethodService.this.mInkWindow.show();
            this.mSimultaneousStylusAndTouchEnabled = com.android.input.flags.Flags.enableMultiDeviceInput();
            list.forEach(new Consumer() { // from class: android.inputmethodservice.InputMethodService$InputMethodImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.deliverStylusHandwritingMotionEvent((MotionEvent) obj);
                }
            });
            InputMethodService.this.mHandwritingEventReceiver = new InputEventReceiver(inputChannel, Looper.getMainLooper()) { // from class: android.inputmethodservice.InputMethodService.InputMethodImpl.1
                @Override // android.view.InputEventReceiver
                public void onInputEvent(InputEvent inputEvent) {
                    try {
                        if (inputEvent instanceof MotionEvent) {
                            MotionEvent motionEvent = (MotionEvent) inputEvent;
                            if (motionEvent.isStylusPointer()) {
                                InputMethodImpl.this.deliverStylusHandwritingMotionEvent(motionEvent);
                                InputMethodService.this.scheduleHandwritingSessionTimeout();
                                finishInputEvent(inputEvent, true);
                            }
                        }
                    } finally {
                        finishInputEvent(inputEvent, false);
                    }
                }
            };
            InputMethodService.this.scheduleHandwritingSessionTimeout();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void deliverStylusHandwritingMotionEvent(MotionEvent motionEvent) {
            InputMethodService.this.onStylusHandwritingMotionEvent(motionEvent);
            if (this.mSimultaneousStylusAndTouchEnabled) {
                int action = motionEvent.getAction();
                if (action != 0) {
                    if (action != 1 && action != 3) {
                        if (action == 4) {
                            finishStylusHandwriting();
                            return;
                        } else if (action != 9) {
                            if (action != 10) {
                                return;
                            }
                        }
                    }
                    InputMethodService.this.mPrivOps.setHandwritingSurfaceNotTouchable(true);
                    return;
                }
                InputMethodService.this.mPrivOps.setHandwritingSurfaceNotTouchable(false);
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void commitHandwritingDelegationTextIfAvailable() {
            InputMethodService.this.commitHandwritingDelegationTextIfAvailable();
        }

        @Override // android.view.inputmethod.InputMethod
        public void discardHandwritingDelegationText() {
            InputMethodService.this.discardHandwritingDelegationText();
        }

        @Override // android.view.inputmethod.InputMethod
        public void initInkWindow() {
            maybeCreateAndInitInkWindow();
            InputMethodService.this.onPrepareStylusHandwriting();
            InputMethodService.this.mOnPreparedStylusHwCalled = true;
        }

        private void maybeCreateAndInitInkWindow() {
            if (InputMethodService.this.mInkWindow == null) {
                InputMethodService.this.mInkWindow = new InkWindow(InputMethodService.this.mWindow.getContext());
                InputMethodService.this.mInkWindow.setToken(InputMethodService.this.mToken);
            }
            InputMethodService.this.mInkWindow.initOnly();
        }

        @Override // android.view.inputmethod.InputMethod
        public void finishStylusHandwriting() {
            InputMethodService.this.finishStylusHandwriting();
        }

        @Override // android.view.inputmethod.InputMethod
        public void removeStylusHandwritingWindow() {
            InputMethodService.this.finishAndRemoveStylusHandwritingWindow();
        }

        @Override // android.view.inputmethod.InputMethod
        public void setStylusWindowIdleTimeoutForTest(long j) {
            InputMethodService.this.mStylusWindowIdleTimeoutForTest = j;
        }

        @Override // android.view.inputmethod.InputMethod
        public void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype) {
            InputMethodService.this.dispatchOnCurrentInputMethodSubtypeChanged(inputMethodSubtype);
        }

        @Override // android.view.inputmethod.InputMethod
        public void minimizeSoftInput(int i) {
            if (!SemInputMethodManagerUtils.METHOD_ID_HONEYBOARD.equals(Settings.Secure.getString(InputMethodService.this.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD)) && InputMethodService.this.mImm != null && InputMethodService.this.mToken != null) {
                InputMethodService.this.mImm.hideSoftInputFromInputMethod(InputMethodService.this.mToken, 0);
                return;
            }
            if (InputMethodService.this.mInputView == null || InputMethodService.this.mCandidatesFrame == null) {
                Log.e(InputMethod.TAG, "mInputView or mCandidatesFrame is null in minimizeSoftInput");
                return;
            }
            if (!InputMethodService.this.mInputView.isShown()) {
                Log.e(InputMethod.TAG, "Keyboard is not showing so minimizeSoftInput not working.");
                return;
            }
            if (InputMethodService.this.mInputView.getHeight() == 0) {
                Log.v(InputMethod.TAG, "height is 0");
                return;
            }
            if (i <= 0 || i > 253) {
                i = 22;
            }
            int pixel = SemImsUtils.getPixel(InputMethodService.this.getResources(), i);
            InputMethodService.this.minimized = true;
            InputMethodService.this.mWindow.setMinimizeFlag(InputMethodService.this.minimized);
            Log.v(InputMethod.TAG, "height is " + pixel);
            InputMethodService.this.doMinimizeSoftInput(pixel);
            if (InputMethodService.MINIMIZED_IME_INSET_ANIM) {
                InputMethodService inputMethodService = InputMethodService.this;
                inputMethodService.mMinimizedHeight = inputMethodService.setMinimizeSoftInputInsets();
                Log.d(InputMethod.TAG, "minimizeSoftInput: set mMinimizedHeight=" + InputMethodService.this.mMinimizedHeight);
            }
        }

        @Override // android.view.inputmethod.InputMethod
        public void unMinimizeSoftInput() {
            if (InputMethodService.this.minimized) {
                InputMethodService.this.minimized = false;
                InputMethodService.this.mWindow.setMinimizeFlag(InputMethodService.this.minimized);
            }
            InputMethodService.this.undoMinimizeSoftInputWrapper();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IBinder getHostInputToken() {
        View view = this.mRootView;
        ViewRootImpl viewRootImpl = view != null ? view.getViewRootImpl() : null;
        if (viewRootImpl == null) {
            return null;
        }
        return viewRootImpl.getInputToken();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleImeSurfaceRemoval() {
        if (this.mShowInputRequested || this.mWindowVisible || this.mWindow == null || this.mImeSurfaceRemoverRunnable != null) {
            StringBuilder sb = new StringBuilder("scheduleImeSurfaceRemoval: canceled, mShowInputRequested=");
            sb.append(this.mShowInputRequested);
            sb.append(", mWindowVisible=");
            sb.append(this.mWindowVisible);
            sb.append(", IsmWindowNull=");
            sb.append(this.mWindow == null);
            sb.append(", IsmImeSurfaceRemoverRunnableNotNull=");
            sb.append(this.mImeSurfaceRemoverRunnable != null);
            Log.i(TAG, sb.toString());
            return;
        }
        if (this.mHandler == null) {
            this.mHandler = new Handler(getMainLooper());
        }
        if (this.mLastWasInFullscreenMode) {
            lambda$scheduleImeSurfaceRemoval$2();
            return;
        }
        Runnable runnable = new Runnable() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$scheduleImeSurfaceRemoval$2();
            }
        };
        this.mImeSurfaceRemoverRunnable = runnable;
        this.mHandler.postDelayed(runnable, TIMEOUT_SURFACE_REMOVAL_MILLIS);
        Log.i(TAG, "scheduleImeSurfaceRemoval: removeImeSurface is posted.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeImeSurface, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleImeSurfaceRemoval$2() {
        Log.i(TAG, "removeImeSurface");
        cancelImeSurfaceRemoval();
        SoftInputWindow softInputWindow = this.mWindow;
        if (softInputWindow != null) {
            softInputWindow.hide();
        }
    }

    private void cancelImeSurfaceRemoval() {
        if (this.mHandler != null && this.mImeSurfaceRemoverRunnable != null) {
            Log.i(TAG, "cancelImeSurfaceRemoval: removeCallbacks");
            this.mHandler.removeCallbacks(this.mImeSurfaceRemoverRunnable);
        }
        this.mImeSurfaceRemoverRunnable = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImeWindowVisibility(int i) {
        if (i == this.mImeWindowVisibility) {
            return;
        }
        this.mImeWindowVisibility = i;
        setImeWindowStatus(i, this.mBackDisposition);
    }

    private void setImeWindowStatus(int i, int i2) {
        this.mPrivOps.setImeWindowStatusAsync(i, i2);
    }

    private void setImeExclusionRect(int i) {
        View rootView = this.mInputFrame.getRootView();
        android.graphics.Insets insets = rootView.getRootWindowInsets().getInsets(WindowInsets.Type.systemGestures());
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Rect(0, i, insets.left, rootView.getHeight()));
        arrayList.add(new Rect(rootView.getWidth() - insets.right, i, rootView.getWidth(), rootView.getHeight()));
        rootView.setSystemGestureExclusionRects(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEditorToolTypeInternal(int i) {
        EditorInfo editorInfo;
        if (Flags.useHandwritingListenerForTooltype() && (editorInfo = this.mInputEditorInfo) != null) {
            editorInfo.setInitialToolType(i);
        }
        onUpdateEditorToolType(i);
    }

    public class InputMethodSessionImpl extends AbstractInputMethodService.AbstractInputMethodSessionImpl {
        public InputMethodSessionImpl() {
            super();
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void finishInput() {
            if (isEnabled()) {
                InputMethodService.this.doFinishInput();
            }
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void displayCompletions(CompletionInfo[] completionInfoArr) {
            if (isEnabled()) {
                InputMethodService.this.mCurCompletions = completionInfoArr;
                InputMethodService.this.onDisplayCompletions(completionInfoArr);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void updateExtractedText(int i, ExtractedText extractedText) {
            if (isEnabled()) {
                InputMethodService.this.onUpdateExtractedText(i, extractedText);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) {
            if (isEnabled()) {
                InputMethodService.this.onUpdateSelection(i, i2, i3, i4, i5, i6);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void viewClicked(boolean z) {
            if (isEnabled()) {
                if (InputMethodService.this.minimized) {
                    InputMethodService.this.mWindow.getWindow().setLayout(-1, -2);
                    InputMethodService.this.undoMinimizeSoftInputWrapper();
                    InputMethodService.this.minimized = false;
                    InputMethodService.this.mWindow.setMinimizeFlag(InputMethodService.this.minimized);
                }
                InputMethodService.this.onViewClicked(z);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void updateCursor(Rect rect) {
            if (isEnabled()) {
                InputMethodService.this.onUpdateCursor(rect);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void appPrivateCommand(String str, Bundle bundle) {
            if (isEnabled()) {
                if (ViewRune.SUPPORT_WRITING_TOOLKIT && SemInputMethodManagerUtils.ACTION_SHOW_TOOLKIT_HBD.equals(str)) {
                    InputMethodService.this.switchInputMethod(SemInputMethodManagerUtils.METHOD_ID_TOOLKIT_HONEYBOARD);
                } else {
                    InputMethodService.this.onAppPrivateCommand(str, bundle);
                }
            }
        }

        @Override // android.view.inputmethod.InputMethodSession
        @Deprecated
        public void toggleSoftInput(int i, int i2) {
            InputMethodService.this.onToggleSoftInput(i, i2);
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) {
            if (isEnabled()) {
                InputMethodService.this.onUpdateCursorAnchorInfo(cursorAnchorInfo);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession
        public final void removeImeSurface() {
            InputMethodService.this.scheduleImeSurfaceRemoval();
        }

        @Override // android.view.inputmethod.InputMethodSession
        public final void invalidateInputInternal(EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, int i) {
            Log.i(InputMethodService.TAG, "invalidateInputInternal: sessionId=" + i);
            if (InputMethodService.this.mStartedInputConnection instanceof RemoteInputConnection) {
                RemoteInputConnection remoteInputConnection = (RemoteInputConnection) InputMethodService.this.mStartedInputConnection;
                if (remoteInputConnection.isSameConnection(iRemoteInputConnection)) {
                    editorInfo.makeCompatible(InputMethodService.this.getApplicationInfo().targetSdkVersion);
                    InputMethodService.this.mLastHandwritingRegion = null;
                    Log.i(InputMethodService.TAG, "invalidateInputInternal: restartInput sessionId=" + i);
                    InputMethodService.this.getInputMethodInternal().restartInput(new RemoteInputConnection(remoteInputConnection, i), editorInfo);
                }
            }
        }
    }

    public static final class Insets {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public static final int TOUCHABLE_INSETS_VISIBLE = 2;
        public int contentTopInsets;
        public int touchableInsets;
        public final Region touchableRegion = new Region();
        public int visibleTopInsets;

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
            long jStart = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.contentTopInsets);
            protoOutputStream.write(1120986464258L, this.visibleTopInsets);
            protoOutputStream.write(1120986464259L, this.touchableInsets);
            protoOutputStream.write(1138166333444L, this.touchableRegion.toString());
            protoOutputStream.end(jStart);
        }
    }

    private static final class SettingsObserver extends ContentObserver {
        static SemDesktopModeManagerWrapper sDesktopModeManagerWrapper;
        private final InputMethodService mService;
        private int mShowImeWithHardKeyboard;

        @Retention(RetentionPolicy.SOURCE)
        private @interface ShowImeWithHardKeyboardType {
            public static final int FALSE = 1;
            public static final int TRUE = 2;
            public static final int UNKNOWN = 0;
        }

        private SettingsObserver(InputMethodService inputMethodService) {
            super(new Handler(inputMethodService.getMainLooper()));
            this.mShowImeWithHardKeyboard = 0;
            this.mService = inputMethodService;
        }

        public static SettingsObserver createAndRegister(InputMethodService inputMethodService) {
            SettingsObserver settingsObserver = new SettingsObserver(inputMethodService);
            inputMethodService.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD), false, settingsObserver);
            sDesktopModeManagerWrapper = new SemDesktopModeManagerWrapper(inputMethodService);
            return settingsObserver;
        }

        void unregister() {
            this.mService.getContentResolver().unregisterContentObserver(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldShowImeWithHardKeyboard() {
            if (this.mShowImeWithHardKeyboard == 0) {
                this.mShowImeWithHardKeyboard = Settings.Secure.getInt(this.mService.getContentResolver(), Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD, 0) != 0 ? 2 : 1;
            }
            if (sDesktopModeManagerWrapper.isDexDesktopDisplay(this.mService)) {
                return Settings.Global.getInt(this.mService.getContentResolver(), Settings.Global.SEM_DEX_SHOW_VIRTUAL_KEYBOARD, 0) == 1;
            }
            int i = this.mShowImeWithHardKeyboard;
            if (i == 1) {
                return false;
            }
            if (i == 2) {
                return true;
            }
            Log.e(InputMethodService.TAG, "Unexpected mShowImeWithHardKeyboard=" + this.mShowImeWithHardKeyboard);
            return false;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (Settings.Secure.getUriFor(Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD).equals(uri)) {
                this.mShowImeWithHardKeyboard = Settings.Secure.getInt(this.mService.getContentResolver(), Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD, 0) != 0 ? 2 : 1;
                this.mService.resetStateForNewConfiguration();
            }
        }

        public String toString() {
            return "SettingsObserver{mShowImeWithHardKeyboard=" + this.mShowImeWithHardKeyboard + "}";
        }

        public void updateClientDisplayId(EditorInfo editorInfo) {
            sDesktopModeManagerWrapper.updateClientDisplayId(editorInfo);
        }
    }

    public final boolean getShouldShowImeWithHardKeyboardForTesting() {
        return this.mSettingsObserver.shouldShowImeWithHardKeyboard();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void setTheme(int i) {
        if (this.mWindow != null) {
            throw new IllegalStateException("Must be called before onCreate()");
        }
        this.mTheme = i;
    }

    @Deprecated
    public boolean enableHardwareAcceleration() {
        if (this.mWindow != null) {
            throw new IllegalStateException("Must be called before onCreate()");
        }
        return ActivityManager.isHighEndGfx();
    }

    @Override // android.app.Service
    public void onCreate() throws Resources.NotFoundException {
        Log.i(TAG, "onCreate: pkg=" + getPackageName());
        if (methodIsOverridden("onCreateInputMethodSessionInterface", new Class[0]) && CompatChanges.isChangeEnabled(DISALLOW_INPUT_METHOD_INTERFACE_OVERRIDE)) {
            throw new LinkageError("InputMethodService#onCreateInputMethodSessionInterface() can no longer be overridden!");
        }
        Trace.traceBegin(32L, "IMS.onCreate");
        int iSelectSystemTheme = Resources.selectSystemTheme(this.mTheme, getApplicationInfo().targetSdkVersion, 16973908, 16973951, 16974142, 16974142);
        this.mTheme = iSelectSystemTheme;
        super.setTheme(iSelectSystemTheme);
        super.onCreate();
        this.mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        SettingsObserver settingsObserverCreateAndRegister = SettingsObserver.createAndRegister(this);
        this.mSettingsObserver = settingsObserverCreateAndRegister;
        settingsObserverCreateAndRegister.shouldShowImeWithHardKeyboard();
        boolean z = getApplicationContext().getResources().getBoolean(R.bool.config_hideNavBarForKeyboard);
        initConfigurationTracker();
        sendDisplayIdForDex();
        handleSipDualView();
        if (this.mImeDispatcher != null) {
            this.mWindow.getOnBackInvokedDispatcher().setImeOnBackInvokedDispatcher(this.mImeDispatcher);
        }
        this.mNavigationBarController.onSoftInputWindowCreated(this.mWindow);
        Window window = this.mWindow.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.setTitle(InputMethod.TAG);
        attributes.type = 2011;
        attributes.width = -1;
        attributes.height = -2;
        attributes.gravity = 80;
        attributes.setFitInsetsTypes(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        attributes.setFitInsetsSides(WindowInsets.Side.all() & (-9));
        attributes.receiveInsetsIgnoringZOrder = true;
        handleSepKeyboardLayoutParams(attributes);
        window.setAttributes(attributes);
        window.setFlags(-2147483384, -2147483382);
        if (z) {
            window.setDecorFitsSystemWindows(false);
        }
        initViews();
        Trace.traceEnd(32L);
        this.mInlineSuggestionSessionController = new InlineSuggestionSessionController(new Function() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.onCreateInlineSuggestionsRequest((Bundle) obj);
            }
        }, new Supplier() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda10
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.getHostInputToken();
            }
        }, new Consumer() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.onInlineSuggestionsResponse((InlineSuggestionsResponse) obj);
            }
        });
        undoMinimizeSoftInputWrapper();
        Configuration configuration = getResources().getConfiguration();
        this.mCurrentNightMode = configuration.uiMode & 48;
        this.mCurrentOrientation = configuration.orientation;
        Trace.traceEnd(32L);
    }

    private void initConfigurationTracker() {
        ComponentName componentName = new ComponentName(getPackageName(), getClass().getName());
        String strFlattenToShortString = componentName.flattenToShortString();
        try {
            try {
                XmlResourceParser xmlResourceParserLoadXmlMetaData = getPackageManager().getServiceInfo(componentName, PackageManager.ComponentInfoFlags.of(32896L)).loadXmlMetaData(getPackageManager(), InputMethod.SERVICE_META_DATA);
                try {
                    TypedArray typedArrayObtainAttributes = getResources().obtainAttributes(Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData), R.styleable.InputMethod);
                    try {
                        if (xmlResourceParserLoadXmlMetaData == null) {
                            throw new XmlPullParserException("No android.view.im meta-data");
                        }
                        this.mConfigTracker.onInitialize(typedArrayObtainAttributes.getInt(0, 0));
                        if (typedArrayObtainAttributes != null) {
                            typedArrayObtainAttributes.close();
                        }
                        if (xmlResourceParserLoadXmlMetaData != null) {
                            xmlResourceParserLoadXmlMetaData.close();
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                Log.wtf(TAG, "Unable to load input method " + strFlattenToShortString, e);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.wtf(TAG, "Unable to find input method " + strFlattenToShortString, e2);
        }
    }

    void initialize() {
        if (this.mInitialized) {
            return;
        }
        this.mInitialized = true;
        this.mIsFullscreen = false;
        onInitializeInterface();
    }

    void initViews() {
        Trace.traceBegin(32L, "IMS.initViews");
        this.mInitialized = false;
        this.mViewsCreated = false;
        this.mShowInputRequested = false;
        this.mShowInputFlags = 0;
        this.mThemeAttrs = obtainStyledAttributes(android.R.styleable.InputMethodService);
        View viewInflate = this.mInflater.inflate(R.layout.input_method, (ViewGroup) null);
        this.mRootView = viewInflate;
        this.mWindow.setContentView(viewInflate);
        this.mRootView.getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsComputer);
        this.mFullscreenArea = (ViewGroup) this.mRootView.findViewById(R.id.fullscreenArea);
        this.mExtractViewHidden = false;
        this.mExtractFrame = (FrameLayout) this.mRootView.findViewById(16908316);
        this.mExtractView = null;
        this.mExtractEditText = null;
        this.mExtractAccessories = null;
        this.mExtractAction = null;
        this.mFullscreenApplied = false;
        this.mCandidatesFrame = (FrameLayout) this.mRootView.findViewById(16908317);
        this.mInputFrame = (FrameLayout) this.mRootView.findViewById(16908318);
        this.mInputView = null;
        this.mIsInputViewShown = false;
        this.mExtractFrame.setVisibility(8);
        int candidatesHiddenVisibility = getCandidatesHiddenVisibility();
        this.mCandidatesVisibility = candidatesHiddenVisibility;
        this.mCandidatesFrame.setVisibility(candidatesHiddenVisibility);
        this.mInputFrame.setVisibility(8);
        this.mNavigationBarController.onViewInitialized();
        Trace.traceEnd(32L);
    }

    @Override // android.window.WindowProviderService, android.app.Service
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        SemSpenGestureManagerWrapper.notifyKeyboardClosedForAGIF(this);
        this.mDestroyed = true;
        super.onDestroy();
        this.mRootView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsComputer);
        doFinishInput();
        this.mNavigationBarController.onDestroy();
        this.mWindow.dismissForDestroyIfNecessary();
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.unregister();
            this.mSettingsObserver = null;
        }
        IBinder iBinder = this.mToken;
        if (iBinder != null) {
            InputMethodPrivilegedOperationsRegistry.remove(iBinder);
        }
        this.mImeDispatcher = null;
    }

    @Override // android.window.WindowProviderService, android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mConfigTracker.onConfigurationChanged(configuration, new Runnable() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.resetStateForNewConfiguration();
            }
        });
        int i = configuration.uiMode & 48;
        if (this.mCurrentNightMode != i) {
            checkandshowInputMehtodPicker();
            this.mCurrentNightMode = i;
        }
        if (configuration.orientation != this.mCurrentOrientation) {
            checkandshowInputMehtodPicker();
            this.mCurrentOrientation = configuration.orientation;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetStateForNewConfiguration() {
        Trace.traceBegin(32L, "IMS.resetStateForNewConfiguration");
        boolean z = this.mDecorViewVisible;
        int i = this.mShowInputFlags;
        boolean z2 = this.mShowInputRequested;
        CompletionInfo[] completionInfoArr = this.mCurCompletions;
        this.mRootView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsComputer);
        initViews();
        this.mInputViewStarted = false;
        this.mCandidatesViewStarted = false;
        if (this.mInputStarted) {
            doStartInput(getCurrentInputConnection(), getCurrentInputEditorInfo(), true);
        }
        if (z) {
            if (z2) {
                if (dispatchOnShowInputRequested(i, true)) {
                    showWindowWithToken(true, 44);
                    if (completionInfoArr != null) {
                        this.mCurCompletions = completionInfoArr;
                        onDisplayCompletions(completionInfoArr);
                    }
                } else {
                    hideWindowWithToken(44);
                }
            } else if (this.mCandidatesVisibility == 0) {
                showWindowWithToken(false, 44);
            } else {
                hideWindowWithToken(44);
            }
            setImeWindowVisibility((onEvaluateInputViewShown() ? 2 : 0) | 1);
        }
        Trace.traceEnd(32L);
    }

    @Override // android.inputmethodservice.AbstractInputMethodService
    @Deprecated
    public AbstractInputMethodService.AbstractInputMethodImpl onCreateInputMethodInterface() {
        return new InputMethodImpl();
    }

    @Override // android.inputmethodservice.AbstractInputMethodService
    @Deprecated
    public AbstractInputMethodService.AbstractInputMethodSessionImpl onCreateInputMethodSessionInterface() {
        return new InputMethodSessionImpl();
    }

    public LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    public Dialog getWindow() {
        return this.mWindow;
    }

    public void setBackDisposition(int i) {
        if (i == this.mBackDisposition) {
            return;
        }
        if (i > 3 || i < 0) {
            Log.e(TAG, "Invalid back disposition value (" + i + ") specified.");
            return;
        }
        this.mBackDisposition = i;
        setImeWindowStatus(this.mImeWindowVisibility, i);
    }

    public int getBackDisposition() {
        return this.mBackDisposition;
    }

    public int getMaxWidth() {
        return WindowMetricsHelper.getBoundsExcludingNavigationBarAndCutout(((WindowManager) getSystemService(WindowManager.class)).getCurrentWindowMetrics()).width();
    }

    public InputBinding getCurrentInputBinding() {
        return this.mInputBinding;
    }

    public InputConnection getCurrentInputConnection() {
        InputConnection inputConnection = this.mStartedInputConnection;
        return inputConnection != null ? inputConnection : this.mInputConnection;
    }

    public final boolean switchToPreviousInputMethod() {
        return this.mPrivOps.switchToPreviousInputMethod();
    }

    public final boolean switchToNextInputMethod(boolean z) {
        return this.mPrivOps.switchToNextInputMethod(z);
    }

    public final boolean shouldOfferSwitchingToNextInputMethod() {
        return this.mPrivOps.shouldOfferSwitchingToNextInputMethod();
    }

    public boolean getCurrentInputStarted() {
        return this.mInputStarted;
    }

    public EditorInfo getCurrentInputEditorInfo() {
        return this.mInputEditorInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportFullscreenMode() {
        this.mPrivOps.reportFullscreenModeAsync(this.mIsFullscreen);
    }

    public void updateFullscreenMode() {
        View viewOnCreateExtractTextView;
        Trace.traceBegin(32L, "IMS.updateFullscreenMode");
        boolean z = this.mShowInputRequested && onEvaluateFullscreenMode();
        boolean z2 = this.mLastShowInputRequested != this.mShowInputRequested;
        if (this.mIsFullscreen != z || !this.mFullscreenApplied) {
            this.mIsFullscreen = z;
            reportFullscreenMode();
            this.mFullscreenApplied = true;
            initialize();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mFullscreenArea.getLayoutParams();
            if (z) {
                this.mFullscreenArea.setBackgroundDrawable(this.mThemeAttrs.getDrawable(0));
                layoutParams.height = 0;
                layoutParams.weight = 1.0f;
            } else {
                this.mFullscreenArea.setBackgroundDrawable(null);
                layoutParams.height = -2;
                layoutParams.weight = 0.0f;
            }
            if (this.minimized) {
                this.minimized = false;
                this.mWindow.setMinimizeFlag(false);
            }
            undoMinimizeSoftInputWrapper();
            ((ViewGroup) this.mFullscreenArea.getParent()).updateViewLayout(this.mFullscreenArea, layoutParams);
            if (z) {
                if (this.mExtractView == null && (viewOnCreateExtractTextView = onCreateExtractTextView()) != null) {
                    setExtractView(viewOnCreateExtractTextView);
                }
                startExtractingText(false);
            }
            updateExtractFrameVisibility();
            z2 = true;
        }
        if (z2) {
            onConfigureWindow(this.mWindow.getWindow(), z, true ^ this.mShowInputRequested);
            this.mLastShowInputRequested = this.mShowInputRequested;
        }
        Trace.traceEnd(32L);
    }

    public void onConfigureWindow(Window window, boolean z, boolean z2) {
        int i = this.mWindow.getWindow().getAttributes().height;
        int i2 = z ? -1 : -2;
        if (this.mIsInputViewShown && i != i2 && !this.needSetLayout) {
            undoMinimizeSoftInputWrapper();
            try {
                this.mWindow.getWindow().setLayout(-1, i2);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "onConfigureWindow: IllegalArgumentException occured.");
                e.printStackTrace();
            }
        }
        this.mWindow.getWindow().setLayout(-1, i2);
    }

    public boolean isFullscreenMode() {
        return this.mIsFullscreen;
    }

    public boolean onEvaluateFullscreenMode() {
        if (getResources().getConfiguration().orientation != 2) {
            return false;
        }
        EditorInfo editorInfo = this.mInputEditorInfo;
        if (editorInfo != null && (editorInfo.imeOptions & 268435456) != 0) {
            return false;
        }
        EditorInfo editorInfo2 = this.mInputEditorInfo;
        boolean zAnyMatch = (editorInfo2 == null || editorInfo2.privateImeOptions == null) ? false : Arrays.stream(this.mInputEditorInfo.privateImeOptions.split(NavigationBarInflaterView.GRAVITY_SEPARATOR)).anyMatch(new Predicate() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((String) obj).equals("ignoreImeInternalFlagAppWindowPortrait=true");
            }
        });
        EditorInfo editorInfo3 = this.mInputEditorInfo;
        if (editorInfo3 == null || (((editorInfo3.imeOptions & 33554432) == 0 && (this.mInputEditorInfo.internalImeOptions & 1) == 0) || zAnyMatch)) {
            return true;
        }
        StringBuilder sb = new StringBuilder("onEvaluateFullscreenMode: false, noFullScreen=");
        sb.append((this.mInputEditorInfo.imeOptions & 33554432) != 0);
        sb.append(", internalImeOptions=");
        sb.append(this.mInputEditorInfo.internalImeOptions);
        Log.i(TAG, sb.toString());
        return false;
    }

    public void setExtractViewShown(boolean z) {
        if (this.mExtractViewHidden == z) {
            this.mExtractViewHidden = !z;
            updateExtractFrameVisibility();
        }
    }

    public boolean isExtractViewShown() {
        return this.mIsFullscreen && !this.mExtractViewHidden;
    }

    void updateExtractFrameVisibility() {
        int i;
        updateCandidatesVisibility(this.mCandidatesVisibility == 0);
        if (isFullscreenMode()) {
            i = this.mExtractViewHidden ? 4 : 0;
            this.mExtractFrame.setVisibility(i);
        } else {
            i = this.mCandidatesVisibility;
            this.mExtractFrame.setVisibility(8);
        }
        if (this.mDecorViewWasVisible && this.mFullscreenArea.getVisibility() != i) {
            int resourceId = this.mThemeAttrs.getResourceId(i != 0 ? 2 : 1, 0);
            if (resourceId != 0) {
                this.mFullscreenArea.startAnimation(AnimationUtils.loadAnimation(this, resourceId));
            }
        }
        this.mFullscreenArea.setVisibility(i);
    }

    public void onComputeInsets(Insets insets) {
        Trace.traceBegin(32L, "IMS.onComputeInsets");
        int[] iArr = this.mTmpLocation;
        if (this.mInputFrame.getVisibility() == 0) {
            this.mInputFrame.getLocationInWindow(iArr);
        } else {
            iArr[1] = getWindow().getWindow().getDecorView().getHeight();
        }
        int paddingBottom = getPaddingBottom(iArr);
        if (isFullscreenMode()) {
            insets.contentTopInsets = getWindow().getWindow().getDecorView().getHeight();
        } else {
            insets.contentTopInsets = iArr[1] + paddingBottom;
        }
        if (this.mCandidatesFrame.getVisibility() == 0) {
            this.mCandidatesFrame.getLocationInWindow(iArr);
        }
        insets.visibleTopInsets = iArr[1] + paddingBottom;
        insets.touchableInsets = 2;
        insets.touchableRegion.setEmpty();
        Trace.traceEnd(32L);
    }

    public void updateInputViewShown() {
        boolean z = this.mShowInputRequested && onEvaluateInputViewShown();
        if (this.mIsInputViewShown == z || !this.mDecorViewVisible) {
            return;
        }
        this.mIsInputViewShown = z;
        this.mInputFrame.setVisibility(z ? 0 : 8);
        if (this.mInputView == null) {
            initialize();
            View viewOnCreateInputView = onCreateInputView();
            if (viewOnCreateInputView != null) {
                setInputView(viewOnCreateInputView);
            }
        }
    }

    public boolean isShowInputRequested() {
        return this.mShowInputRequested;
    }

    public boolean isInputViewShown() {
        return this.mDecorViewVisible;
    }

    public boolean onEvaluateInputViewShown() {
        if (this.mIsPressBtnSIPOnOff) {
            return true;
        }
        boolean zSemIsAccessoryKeyboard = this.mImm.semIsAccessoryKeyboard();
        if (this.mSettingsObserver == null) {
            Log.w(TAG, "onEvaluateInputViewShown: mSettingsObserver must not be null here.");
            return false;
        }
        Configuration configuration = getResources().getConfiguration();
        if (configuration.keyboard != 1 && configuration.hardKeyboardHidden != 2 && configuration.keyboard != 3) {
            Log.d(TAG, "config.keyboard : " + configuration.keyboard + " config.hardKeyboardHidden : " + configuration.hardKeyboardHidden);
        }
        if (zSemIsAccessoryKeyboard && !this.mSettingsObserver.shouldShowImeWithHardKeyboard()) {
            Log.i(TAG, " virtual keyboard option is false so do not show keyboard");
            return false;
        }
        if (configuration.keyboard == 1 || configuration.hardKeyboardHidden == 2 || configuration.keyboard == 3) {
            return true;
        }
        if (zSemIsAccessoryKeyboard && this.mSettingsObserver.shouldShowImeWithHardKeyboard()) {
            Log.i(TAG, " virtual keyboard option is true so show keyboard");
            return true;
        }
        if (zSemIsAccessoryKeyboard) {
            return false;
        }
        Log.i(TAG, "AccessoryKeyboard is not connected but it can be connect BT mouse with keyboard attribute");
        return true;
    }

    public void setCandidatesViewShown(boolean z) {
        updateCandidatesVisibility(z);
        if (this.mShowInputRequested || this.mDecorViewVisible == z) {
            return;
        }
        if (z) {
            showWindowWithToken(false, 45);
        } else {
            hideWindowWithToken(45);
        }
    }

    void updateCandidatesVisibility(boolean z) {
        int candidatesHiddenVisibility = z ? 0 : getCandidatesHiddenVisibility();
        if (this.mCandidatesVisibility != candidatesHiddenVisibility) {
            this.mCandidatesFrame.setVisibility(candidatesHiddenVisibility);
            this.mCandidatesVisibility = candidatesHiddenVisibility;
        }
    }

    public int getCandidatesHiddenVisibility() {
        return isExtractViewShown() ? 8 : 4;
    }

    public void showStatusIcon(int i) {
        this.mStatusIcon = i;
        this.mPrivOps.updateStatusIconAsync(getPackageName(), i);
    }

    public void hideStatusIcon() {
        this.mStatusIcon = 0;
        this.mPrivOps.updateStatusIconAsync(null, 0);
    }

    public void switchInputMethod(String str) {
        if (SemImsUtils.isBixbyDictationId(str)) {
            this.mImm.setInputMethod(this.mToken, str);
        } else {
            this.mPrivOps.setInputMethod(str);
        }
    }

    public final void switchInputMethod(String str, InputMethodSubtype inputMethodSubtype) {
        this.mPrivOps.setInputMethodAndSubtype(str, inputMethodSubtype);
    }

    public void setExtractView(View view) {
        this.mExtractFrame.removeAllViews();
        this.mExtractFrame.addView(view, new FrameLayout.LayoutParams(-1, -1));
        this.mExtractView = view;
        if (view != null) {
            ExtractEditText extractEditText = (ExtractEditText) view.findViewById(16908325);
            this.mExtractEditText = extractEditText;
            extractEditText.setIME(this);
            View viewFindViewById = view.findViewById(16908377);
            this.mExtractAction = viewFindViewById;
            if (viewFindViewById != null) {
                this.mExtractAccessories = (ViewGroup) view.findViewById(16908378);
            }
            startExtractingText(false);
            return;
        }
        this.mExtractEditText = null;
        this.mExtractAccessories = null;
        this.mExtractAction = null;
    }

    public void setCandidatesView(View view) {
        this.mCandidatesFrame.removeAllViews();
        this.mCandidatesFrame.addView(view, new FrameLayout.LayoutParams(-1, -2));
    }

    public void setInputView(View view) {
        if (view != null && view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        this.mInputFrame.removeAllViews();
        this.mInputFrame.addView(view, new FrameLayout.LayoutParams(-1, -2));
        this.mInputView = view;
    }

    public View onCreateExtractTextView() {
        return this.mInflater.inflate(R.layout.input_method_extract_view, (ViewGroup) null);
    }

    public void onFinishInputView(boolean z) {
        InputConnection currentInputConnection;
        if (this.minimized) {
            Log.e(TAG, "hideWindow set minimized false");
            this.minimized = false;
            this.needSetLayout = true;
            this.mWindow.setMinimizeFlag(false);
        }
        if (z || (currentInputConnection = getCurrentInputConnection()) == null) {
            return;
        }
        currentInputConnection.finishComposingText();
    }

    public void onFinishCandidatesView(boolean z) {
        InputConnection currentInputConnection;
        if (z || (currentInputConnection = getCurrentInputConnection()) == null) {
            return;
        }
        currentInputConnection.finishComposingText();
    }

    public void onStylusHandwritingMotionEvent(MotionEvent motionEvent) {
        InkWindow inkWindow = this.mInkWindow;
        if (inkWindow != null && inkWindow.isInkViewVisible()) {
            this.mInkWindow.dispatchHandwritingEvent(motionEvent);
        } else {
            if (this.mPendingEvents == null) {
                this.mPendingEvents = new RingBuffer<>(MotionEvent.class, 500);
            }
            this.mPendingEvents.append(motionEvent);
            InkWindow inkWindow2 = this.mInkWindow;
            if (inkWindow2 != null) {
                inkWindow2.setInkViewVisibilityListener(new InkWindow.InkVisibilityListener() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda1
                    @Override // android.inputmethodservice.InkWindow.InkVisibilityListener
                    public final void onInkViewVisible() {
                        this.f$0.lambda$onStylusHandwritingMotionEvent$4();
                    }
                });
            }
        }
        if (motionEvent.getAction() == 0) {
            scheduleStylusWindowIdleTimeout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStylusHandwritingMotionEvent$4() {
        RingBuffer<MotionEvent> ringBuffer = this.mPendingEvents;
        if (ringBuffer == null || ringBuffer.isEmpty()) {
            return;
        }
        for (MotionEvent motionEvent : this.mPendingEvents.toArray()) {
            InkWindow inkWindow = this.mInkWindow;
            if (inkWindow == null) {
                break;
            }
            inkWindow.dispatchHandwritingEvent(motionEvent);
        }
        this.mPendingEvents.clear();
    }

    public final Window getStylusHandwritingWindow() {
        return this.mInkWindow;
    }

    public final void finishStylusHandwriting() {
        Runnable runnable;
        if (this.mInkWindow != null && this.mHandwritingRequestId.isPresent()) {
            Handler handler = this.mHandler;
            if (handler != null && (runnable = this.mFinishHwRunnable) != null) {
                handler.removeCallbacks(runnable);
            }
            this.mFinishHwRunnable = null;
            this.mLastHandwritingRegion = null;
            int asInt = this.mHandwritingRequestId.getAsInt();
            this.mHandwritingRequestId = OptionalInt.empty();
            this.mHandwritingEventReceiver.dispose();
            this.mHandwritingEventReceiver = null;
            this.mInkWindow.hide(false);
            if (this.mConnectionlessHandwritingCallback != null) {
                Log.i(TAG, "Connectionless handwriting session did not complete successfully");
                try {
                    this.mConnectionlessHandwritingCallback.onError(2);
                } catch (RemoteException e) {
                    Log.e(TAG, "Couldn't send connectionless handwriting error result", e);
                }
                this.mConnectionlessHandwritingCallback = null;
            }
            this.mIsConnectionlessHandwritingForDelegation = false;
            this.mPrivOps.resetStylusHandwriting(asInt);
            this.mOnPreparedStylusHwCalled = false;
            onFinishStylusHandwriting();
        }
    }

    public final void finishConnectionlessStylusHandwriting(CharSequence charSequence) {
        if (this.mConnectionlessHandwritingCallback != null) {
            try {
                if (!TextUtils.isEmpty(charSequence)) {
                    this.mConnectionlessHandwritingCallback.onResult(charSequence);
                    if (this.mIsConnectionlessHandwritingForDelegation) {
                        this.mHandwritingDelegationText = charSequence;
                    }
                } else {
                    this.mConnectionlessHandwritingCallback.onError(0);
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Couldn't send connectionless handwriting result", e);
            }
            this.mConnectionlessHandwritingCallback = null;
        }
        finishStylusHandwriting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commitHandwritingDelegationTextIfAvailable() {
        InputConnection currentInputConnection;
        if (!TextUtils.isEmpty(this.mHandwritingDelegationText) && (currentInputConnection = getCurrentInputConnection()) != null) {
            currentInputConnection.commitText(this.mHandwritingDelegationText, 1);
        }
        this.mHandwritingDelegationText = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void discardHandwritingDelegationText() {
        this.mHandwritingDelegationText = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishAndRemoveStylusHandwritingWindow() {
        cancelStylusWindowIdleTimeout();
        this.mOnPreparedStylusHwCalled = false;
        this.mStylusWindowIdleTimeoutRunnable = null;
        if (this.mInkWindow != null) {
            if (this.mHandwritingRequestId.isPresent()) {
                finishStylusHandwriting();
            }
            this.mInkWindow.hide(true);
            this.mInkWindow.destroy();
            this.mInkWindow = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelStylusWindowIdleTimeout() {
        Handler handler;
        Runnable runnable = this.mStylusWindowIdleTimeoutRunnable;
        if (runnable == null || (handler = this.mHandler) == null) {
            return;
        }
        handler.removeCallbacks(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleStylusWindowIdleTimeout() {
        if (this.mHandler == null) {
            return;
        }
        cancelStylusWindowIdleTimeout();
        long j = this.mStylusWindowIdleTimeoutForTest;
        if (j <= 0) {
            j = 300000;
        }
        this.mHandler.postDelayed(getStylusWindowIdleTimeoutRunnable(), j);
    }

    private Runnable getStylusWindowIdleTimeoutRunnable() {
        if (this.mStylusWindowIdleTimeoutRunnable == null) {
            this.mStylusWindowIdleTimeoutRunnable = new Runnable() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getStylusWindowIdleTimeoutRunnable$5();
                }
            };
        }
        return this.mStylusWindowIdleTimeoutRunnable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getStylusWindowIdleTimeoutRunnable$5() {
        finishAndRemoveStylusHandwritingWindow();
        this.mStylusWindowIdleTimeoutRunnable = null;
    }

    public final void setStylusHandwritingSessionTimeout(Duration duration) {
        long millis = duration.toMillis();
        if (millis <= 0) {
            throw new IllegalStateException("A positive value should be set for Stylus handwriting session timeout.");
        }
        if (millis > 30000) {
            millis = 30000;
        }
        this.mStylusHwSessionsTimeout = millis;
        scheduleHandwritingSessionTimeout();
    }

    public static final Duration getStylusHandwritingIdleTimeoutMax() {
        return Duration.ofMillis(30000L);
    }

    public final Duration getStylusHandwritingSessionTimeout() {
        return Duration.ofMillis(this.mStylusHwSessionsTimeout);
    }

    private Runnable getFinishHandwritingRunnable() {
        Runnable runnable = this.mFinishHwRunnable;
        if (runnable != null) {
            return runnable;
        }
        Runnable runnable2 = new Runnable() { // from class: android.inputmethodservice.InputMethodService$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getFinishHandwritingRunnable$6();
            }
        };
        this.mFinishHwRunnable = runnable2;
        return runnable2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getFinishHandwritingRunnable$6() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mFinishHwRunnable);
        }
        Log.d(TAG, "Stylus handwriting idle timed-out. calling finishStylusHandwriting()");
        this.mFinishHwRunnable = null;
        finishStylusHandwriting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleHandwritingSessionTimeout() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(getMainLooper());
        }
        Runnable runnable = this.mFinishHwRunnable;
        if (runnable != null) {
            this.mHandler.removeCallbacks(runnable);
        }
        this.mHandler.postDelayed(getFinishHandwritingRunnable(), this.mStylusHwSessionsTimeout);
    }

    public boolean onShowInputRequested(int i, boolean z) {
        EditorInfo editorInfo;
        if (!onEvaluateInputViewShown()) {
            Log.i(TAG, "onShowInputRequested: false, reason: onEvaluateInputViewShown false");
            return false;
        }
        if ((i & 1) == 0) {
            if (!z && onEvaluateFullscreenMode() && !isInputViewShown()) {
                Log.i(TAG, "onShowInputRequested: false, reason: configChange false, onEvaluateFullscreenMode true, isInputViewShown false");
                return false;
            }
            if (!this.mSettingsObserver.shouldShowImeWithHardKeyboard() && getResources().getConfiguration().keyboard != 1) {
                Log.i(TAG, "onShowInputRequested: false, reason: shouldShowImeWithHardKeyboard false, config.keyboard=" + getResources().getConfiguration().keyboard);
                return false;
            }
            Configuration configuration = getResources().getConfiguration();
            if (configuration.keyboard == 3 && (editorInfo = this.mInputEditorInfo) != null && editorInfo.inputType == 0) {
                Log.i(TAG, "onShowInputRequested: false, reason: inputType null, config.keyboard=" + configuration.keyboard);
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean dispatchOnShowInputRequested(int i, boolean z) {
        boolean zOnShowInputRequested = onShowInputRequested(i, z);
        this.mInlineSuggestionSessionController.notifyOnShowInputRequested(zOnShowInputRequested);
        if (zOnShowInputRequested) {
            this.mShowInputFlags = i;
            return zOnShowInputRequested;
        }
        this.mShowInputFlags = 0;
        return zOnShowInputRequested;
    }

    private void showWindowWithToken(boolean z, int i) {
        this.mCurStatsToken = createStatsToken(true, i, ImeTracker.isFromUser(this.mRootView));
        showWindow(z);
    }

    public void showWindow(boolean z) {
        ImeTracker.Token tokenCreateStatsToken = this.mCurStatsToken;
        if (tokenCreateStatsToken == null) {
            tokenCreateStatsToken = createStatsToken(true, 42, ImeTracker.isFromUser(this.mRootView));
        }
        this.mCurStatsToken = null;
        if (this.mInShowWindow) {
            Log.w(TAG, "Re-entrance in to showWindow");
            ImeTracker.forLogging().onCancelled(tokenCreateStatsToken, 44);
            return;
        }
        ImeTracker.forLogging().onProgress(tokenCreateStatsToken, 44);
        if (Flags.refactorInsetsController()) {
            notifyPreImeWindowVisibilityChanged(true, tokenCreateStatsToken);
        }
        ImeTracing.getInstance().triggerServiceDump("InputMethodService#showWindow", this.mDumper, null);
        Trace.traceBegin(32L, "IMS.showWindow");
        this.mDecorViewWasVisible = this.mDecorViewVisible;
        this.mInShowWindow = true;
        startViews(prepareWindow(z));
        setImeWindowVisibility(computeImeWindowVis());
        this.mNavigationBarController.onWindowShown();
        if (this.mDecorViewVisible) {
            sendInputViewShownState(z);
        }
        onWindowShown();
        this.mWindowVisible = true;
        this.mWindow.show();
        this.mDecorViewWasVisible = true;
        if (!Flags.refactorInsetsController()) {
            applyVisibilityInInsetsConsumerIfNecessary(true, tokenCreateStatsToken);
        }
        cancelImeSurfaceRemoval();
        this.mInShowWindow = false;
        Trace.traceEnd(32L);
        registerDefaultOnBackInvokedCallback();
    }

    public final void setStylusHandwritingRegion(Region region) {
        Region region2 = new Region(region);
        if (region2.equals(this.mLastHandwritingRegion)) {
            Log.v(TAG, "Failed to set setStylusHandwritingRegion(): same region set twice.");
        } else {
            this.mPrivOps.setHandwritingTouchableRegion(region2);
            this.mLastHandwritingRegion = region2;
        }
    }

    private void registerDefaultOnBackInvokedCallback() {
        if (this.mBackCallbackRegistered) {
            Log.d(TAG, "registerCompatOnBackInvokedCallback return because registered : " + this.mBackCallbackRegistered);
        } else if (this.mWindow != null) {
            Log.d(TAG, "registerCompatOnBackInvokedCallback : " + this.mCompatBackCallback);
            if (getApplicationInfo().isOnBackInvokedCallbackEnabled() && Flags.predictiveBackIme()) {
                this.mWindow.getOnBackInvokedDispatcher().registerSystemOnBackInvokedCallback(this.mCompatBackCallback);
            } else {
                this.mWindow.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mCompatBackCallback);
            }
            this.mBackCallbackRegistered = true;
        }
    }

    private void unregisterDefaultOnBackInvokedCallback() {
        if (!this.mBackCallbackRegistered) {
            Log.d(TAG, "unregisterCompatOnBackInvokedCallback return because registered : " + this.mBackCallbackRegistered);
        } else if (this.mWindow != null) {
            Log.d(TAG, "unregisterCompatOnBackInvokedCallback : " + this.mCompatBackCallback);
            this.mWindow.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mCompatBackCallback);
            this.mBackCallbackRegistered = false;
        }
    }

    private KeyEvent createBackKeyEvent(int i, boolean z) {
        long jUptimeMillis = SystemClock.uptimeMillis();
        return new KeyEvent(jUptimeMillis, jUptimeMillis, i, 4, 0, 0, -1, 0, (z ? 512 : 0) | 72, 257);
    }

    private boolean prepareWindow(boolean z) {
        boolean z2;
        this.mDecorViewVisible = true;
        if (!this.mShowInputRequested && this.mInputStarted && z) {
            this.mShowInputRequested = true;
            z2 = true;
        } else {
            z2 = false;
        }
        initialize();
        updateFullscreenMode();
        updateInputViewShown();
        if (!this.mViewsCreated) {
            this.mViewsCreated = true;
            initialize();
            View viewOnCreateCandidatesView = onCreateCandidatesView();
            if (viewOnCreateCandidatesView != null) {
                setCandidatesView(viewOnCreateCandidatesView);
            }
        }
        return z2;
    }

    private void startViews(boolean z) {
        boolean z2 = this.mShowInputRequested;
        if ((z2 && this.mInputViewStarted) || (!z2 && this.mCandidatesViewStarted)) {
            Log.i(TAG, "startViews: mShowInputRequested=" + this.mShowInputRequested + ", mInputViewStarted=" + this.mInputViewStarted + ", mCandidatesViewStarted= " + this.mCandidatesViewStarted);
        }
        if (this.mShowInputRequested) {
            if (!this.mInputViewStarted) {
                this.mInputViewStarted = true;
                this.mInlineSuggestionSessionController.notifyOnStartInputView();
                onStartInputView(this.mInputEditorInfo, false);
            }
        } else if (!this.mCandidatesViewStarted) {
            this.mCandidatesViewStarted = true;
            onStartCandidatesView(this.mInputEditorInfo, false);
        }
        if (z) {
            startExtractingText(false);
        }
    }

    private void applyVisibilityInInsetsConsumerIfNecessary(boolean z, ImeTracker.Token token) {
        ImeTracing.getInstance().triggerServiceDump("InputMethodService#applyVisibilityInInsetsConsumerIfNecessary", this.mDumper, null);
        this.mPrivOps.applyImeVisibilityAsync(z ? this.mCurShowInputToken : this.mCurHideInputToken, z, token);
    }

    private void notifyPreImeWindowVisibilityChanged(boolean z, ImeTracker.Token token) {
        ViewRootImpl viewRootImpl = getWindow().getWindow().getDecorView().getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.notifyImeVisibilityChanged(z, token);
        }
    }

    private void finishViews(boolean z) {
        if (this.mInputViewStarted) {
            this.mInlineSuggestionSessionController.notifyOnFinishInputView();
            onFinishInputView(z);
        } else if (this.mCandidatesViewStarted) {
            onFinishCandidatesView(z);
        }
        this.mInputViewStarted = false;
        this.mCandidatesViewStarted = false;
    }

    private void hideWindowWithToken(int i) {
        this.mCurStatsToken = createStatsToken(false, i, ImeTracker.isFromUser(this.mRootView) || i == 29);
        hideWindow();
    }

    public void hideWindow() {
        ImeTracker.Token tokenCreateStatsToken = this.mCurStatsToken;
        if (tokenCreateStatsToken == null) {
            tokenCreateStatsToken = createStatsToken(false, 43, ImeTracker.isFromUser(this.mRootView));
        }
        this.mCurStatsToken = null;
        ImeTracker.forLogging().onProgress(tokenCreateStatsToken, 45);
        ImeTracing.getInstance().triggerServiceDump("InputMethodService#hideWindow", this.mDumper, null);
        setImeWindowVisibility(0);
        if (Flags.refactorInsetsController()) {
            notifyPreImeWindowVisibilityChanged(false, tokenCreateStatsToken);
        } else {
            applyVisibilityInInsetsConsumerIfNecessary(false, tokenCreateStatsToken);
        }
        this.mWindowVisible = false;
        finishViews(false);
        if (this.mDecorViewVisible) {
            View view = this.mInputView;
            if (view != null) {
                view.dispatchWindowVisibilityChanged(8);
            }
            this.mDecorViewVisible = false;
            onWindowHidden();
            this.mDecorViewWasVisible = false;
        }
        this.mLastWasInFullscreenMode = this.mIsFullscreen;
        updateFullscreenMode();
        unregisterDefaultOnBackInvokedCallback();
        sendInputViewShownState(false);
        this.mIsPressBtnSIPOnOff = false;
    }

    void doFinishInput() {
        ImeTracing.getInstance().triggerServiceDump("InputMethodService#doFinishInput", this.mDumper, null);
        finishViews(true);
        if (this.mInputStarted) {
            this.mInlineSuggestionSessionController.notifyOnFinishInput();
            onFinishInput();
        }
        this.mInputStarted = false;
        this.mStartedInputConnection = null;
        this.mCurCompletions = null;
        if (!this.mOnPreparedStylusHwCalled) {
            finishStylusHandwriting();
        }
        unregisterDefaultOnBackInvokedCallback();
    }

    void doStartInput(InputConnection inputConnection, EditorInfo editorInfo, boolean z) {
        if (!z && this.mInputStarted && !useWritingToolkit()) {
            doFinishInput();
        }
        ImeTracing.getInstance().triggerServiceDump("InputMethodService#doStartInput", this.mDumper, null);
        this.mSettingsObserver.updateClientDisplayId(editorInfo);
        this.mInputStarted = true;
        this.mStartedInputConnection = inputConnection;
        this.mInputEditorInfo = editorInfo;
        initialize();
        this.mInlineSuggestionSessionController.notifyOnStartInput(editorInfo == null ? null : editorInfo.packageName, editorInfo != null ? editorInfo.getAutofillId() : null);
        onStartInput(editorInfo, z);
        if (this.mDecorViewVisible) {
            if (this.mShowInputRequested) {
                this.mInputViewStarted = true;
                this.mInlineSuggestionSessionController.notifyOnStartInputView();
                onStartInputView(this.mInputEditorInfo, z);
                startExtractingText(true);
                registerDefaultOnBackInvokedCallback();
                return;
            }
            if (this.mCandidatesVisibility == 0) {
                this.mCandidatesViewStarted = true;
                onStartCandidatesView(this.mInputEditorInfo, z);
            }
        }
    }

    public void onFinishInput() {
        InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection != null) {
            currentInputConnection.finishComposingText();
        }
    }

    public void onUpdateExtractedText(int i, ExtractedText extractedText) {
        ExtractEditText extractEditText;
        if (this.mExtractedToken != i || extractedText == null || (extractEditText = this.mExtractEditText) == null) {
            return;
        }
        this.mExtractedText = extractedText;
        extractEditText.setExtractedText(extractedText);
    }

    public void onUpdateSelection(int i, int i2, int i3, int i4, int i5, int i6) {
        ExtractedText extractedText;
        ExtractEditText extractEditText = this.mExtractEditText;
        if (extractEditText == null || !isFullscreenMode() || (extractedText = this.mExtractedText) == null) {
            return;
        }
        int i7 = extractedText.startOffset;
        extractEditText.startInternalChanges();
        int i8 = i3 - i7;
        int i9 = i4 - i7;
        int length = extractEditText.getText().length();
        if (i8 < 0) {
            i8 = 0;
        } else if (i8 > length) {
            i8 = length;
        }
        if (i9 < 0) {
            i9 = 0;
        } else if (i9 > length) {
            i9 = length;
        }
        extractEditText.setSelection(i8, i9);
        extractEditText.finishInternalChanges();
    }

    public void requestHideSelf(int i) {
        requestHideSelf(i, 5);
    }

    private void requestHideSelf(int i, int i2) {
        ImeTracker.Token tokenCreateStatsToken = createStatsToken(false, i2, ImeTracker.isFromUser(this.mRootView) || i2 == 29);
        ImeTracing.getInstance().triggerServiceDump("InputMethodService#requestHideSelf", this.mDumper, null);
        this.mPrivOps.hideMySoftInput(tokenCreateStatsToken, i, i2);
    }

    public final void requestShowSelf(int i) {
        requestShowSelf(i, 3);
    }

    private void requestShowSelf(int i, int i2) {
        ImeTracker.Token tokenCreateStatsToken = createStatsToken(true, i2, ImeTracker.isFromUser(this.mRootView));
        ImeTracing.getInstance().triggerServiceDump("InputMethodService#requestShowSelf", this.mDumper, null);
        this.mPrivOps.showMySoftInput(tokenCreateStatsToken, i, i2);
    }

    private boolean handleBack(boolean z) {
        Log.i(TAG, "handleBack: mShowInputRequested=" + this.mShowInputRequested + ", doIt=" + z);
        if (this.mShowInputRequested) {
            if (z) {
                requestHideSelf(0, 29);
            }
            return true;
        }
        if (!this.mDecorViewVisible) {
            return false;
        }
        if (this.mCandidatesVisibility == 0) {
            if (z) {
                setCandidatesViewShown(false);
            }
        } else if (z) {
            hideWindowWithToken(29);
        }
        return true;
    }

    private ExtractEditText getExtractEditTextIfVisible() {
        if (isExtractViewShown() && isInputViewShown()) {
            return this.mExtractEditText;
        }
        return null;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (Flags.useHandwritingListenerForTooltype()) {
            updateEditorToolTypeInternal(0);
        }
        if (i == 4) {
            ExtractEditText extractEditTextIfVisible = getExtractEditTextIfVisible();
            if (extractEditTextIfVisible != null && extractEditTextIfVisible.handleBackInTextActionModeIfNeeded(keyEvent)) {
                return true;
            }
            if (!handleBack(false)) {
                return false;
            }
            keyEvent.startTracking();
            return true;
        }
        if (i == 62 && KeyEvent.metaStateHasModifiers(keyEvent.getMetaState() & (-194), 4096) && this.mDecorViewVisible && this.mWindowVisible) {
            this.mPrivOps.switchKeyboardLayoutAsync((keyEvent.getMetaState() & 193) == 0 ? 1 : -1);
            keyEvent.startTracking();
            return true;
        }
        if (Flags.ctrlShiftShortcut()) {
            if (i == 59 || i == 60) {
                this.mUsingCtrlShiftShortcut = KeyEvent.metaStateHasModifiers(keyEvent.getMetaState() & (-194), 4096);
            } else if (i == 113 || i == 114) {
                this.mUsingCtrlShiftShortcut = KeyEvent.metaStateHasModifiers(keyEvent.getMetaState() & (-28673), 1);
            } else {
                this.mUsingCtrlShiftShortcut = false;
            }
        }
        return doMovementKey(i, keyEvent, -1);
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return doMovementKey(i, keyEvent, i2);
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (Flags.ctrlShiftShortcut()) {
            if (i == 59 || i == 60 || i == 113 || i == 114) {
                if (this.mUsingCtrlShiftShortcut && keyEvent.hasNoModifiers()) {
                    this.mUsingCtrlShiftShortcut = false;
                    if (this.mDecorViewVisible && this.mWindowVisible) {
                        switchToNextInputMethod(false);
                        return true;
                    }
                }
            } else {
                this.mUsingCtrlShiftShortcut = false;
            }
        }
        if (i == 4) {
            ExtractEditText extractEditTextIfVisible = getExtractEditTextIfVisible();
            if (extractEditTextIfVisible != null && extractEditTextIfVisible.handleBackInTextActionModeIfNeeded(keyEvent)) {
                return true;
            }
            if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                return handleBack(true);
            }
            Log.w(TAG, "onKeyUp: event.isTracking=" + keyEvent.isTracking() + ", event.isCanceled=" + keyEvent.isCanceled());
        } else if (i == 62 && keyEvent.isTracking() && !keyEvent.isCanceled()) {
            return true;
        }
        return doMovementKey(i, keyEvent, -2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onToggleSoftInput(int i, int i2) {
        if (isInputViewShown()) {
            requestHideSelf(i2, 30);
        } else {
            requestShowSelf(i, 53);
        }
    }

    void reportExtractedMovement(int i, int i2) {
        int i3 = 0;
        switch (i) {
            case 19:
                i2 = -i2;
            case 20:
                i3 = i2;
                i2 = 0;
                break;
            case 21:
                i2 = -i2;
                break;
            case 22:
                break;
            default:
                i2 = 0;
                break;
        }
        onExtractedCursorMovement(i2, i3);
    }

    boolean doMovementKey(int i, KeyEvent keyEvent, int i2) {
        ExtractEditText extractEditTextIfVisible = getExtractEditTextIfVisible();
        if (extractEditTextIfVisible != null) {
            MovementMethod movementMethod = extractEditTextIfVisible.getMovementMethod();
            Layout layout = extractEditTextIfVisible.getLayout();
            if (movementMethod != null && layout != null) {
                if (i2 == -1) {
                    if (movementMethod.onKeyDown(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, keyEvent)) {
                        reportExtractedMovement(i, 1);
                        return true;
                    }
                } else if (i2 == -2) {
                    if (movementMethod.onKeyUp(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, keyEvent)) {
                        return true;
                    }
                } else if (movementMethod.onKeyOther(extractEditTextIfVisible, extractEditTextIfVisible.getText(), keyEvent)) {
                    reportExtractedMovement(i, i2);
                } else {
                    KeyEvent keyEventChangeAction = KeyEvent.changeAction(keyEvent, 0);
                    if (movementMethod.onKeyDown(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, keyEventChangeAction)) {
                        KeyEvent keyEventChangeAction2 = KeyEvent.changeAction(keyEvent, 1);
                        movementMethod.onKeyUp(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, keyEventChangeAction2);
                        while (true) {
                            i2--;
                            if (i2 <= 0) {
                                break;
                            }
                            movementMethod.onKeyDown(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, keyEventChangeAction);
                            movementMethod.onKeyUp(extractEditTextIfVisible, extractEditTextIfVisible.getText(), i, keyEventChangeAction2);
                        }
                        reportExtractedMovement(i, i2);
                    }
                }
            }
            switch (i) {
            }
            return true;
        }
        return false;
    }

    public void sendDownUpKeyEvents(int i) {
        InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection == null) {
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        currentInputConnection.sendKeyEvent(new KeyEvent(jUptimeMillis, jUptimeMillis, 0, i, 0, 0, -1, 0, 6));
        currentInputConnection.sendKeyEvent(new KeyEvent(jUptimeMillis, SystemClock.uptimeMillis(), 1, i, 0, 0, -1, 0, 6));
    }

    public boolean sendDefaultEditorAction(boolean z) {
        EditorInfo currentInputEditorInfo = getCurrentInputEditorInfo();
        if (currentInputEditorInfo == null) {
            return false;
        }
        if ((z && (currentInputEditorInfo.imeOptions & 1073741824) != 0) || (currentInputEditorInfo.imeOptions & 255) == 1) {
            return false;
        }
        InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection != null) {
            currentInputConnection.performEditorAction(currentInputEditorInfo.imeOptions & 255);
        }
        return true;
    }

    public void sendKeyChar(char c) {
        if (c == '\n') {
            if (sendDefaultEditorAction(true)) {
                return;
            }
            sendDownUpKeyEvents(66);
        } else {
            if (c >= '0' && c <= '9') {
                sendDownUpKeyEvents(c - ')');
                return;
            }
            InputConnection currentInputConnection = getCurrentInputConnection();
            if (currentInputConnection != null) {
                currentInputConnection.commitText(String.valueOf(c), 1);
            }
        }
    }

    public void onExtractedSelectionChanged(int i, int i2) {
        InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection != null) {
            currentInputConnection.setSelection(i, i2);
        }
    }

    public void onExtractedDeleteText(int i, int i2) {
        InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection != null) {
            currentInputConnection.finishComposingText();
            currentInputConnection.setSelection(i, i);
            currentInputConnection.deleteSurroundingText(0, i2 - i);
        }
    }

    public void onExtractedReplaceText(int i, int i2, CharSequence charSequence) {
        InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection != null) {
            currentInputConnection.setComposingRegion(i, i2);
            currentInputConnection.commitText(charSequence, 1);
        }
    }

    public void onExtractedSetSpan(Object obj, int i, int i2, int i3) {
        InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection == null || !currentInputConnection.setSelection(i, i2)) {
            return;
        }
        CharSequence selectedText = currentInputConnection.getSelectedText(1);
        if (selectedText instanceof Spannable) {
            ((Spannable) selectedText).setSpan(obj, 0, selectedText.length(), i3);
            currentInputConnection.setComposingRegion(i, i2);
            currentInputConnection.commitText(selectedText, 1);
        }
    }

    public void onExtractedTextClicked() {
        ExtractEditText extractEditText = this.mExtractEditText;
        if (extractEditText != null && extractEditText.hasVerticalScrollBar()) {
            setCandidatesViewShown(false);
        }
    }

    public void onExtractedCursorMovement(int i, int i2) {
        ExtractEditText extractEditText = this.mExtractEditText;
        if (extractEditText == null || i2 == 0 || !extractEditText.hasVerticalScrollBar()) {
            return;
        }
        setCandidatesViewShown(false);
    }

    public boolean onExtractTextContextMenuItem(int i) {
        InputConnection currentInputConnection = getCurrentInputConnection();
        if (currentInputConnection == null) {
            return true;
        }
        currentInputConnection.performContextMenuAction(i);
        return true;
    }

    public CharSequence getTextForImeAction(int i) {
        switch (i & 255) {
            case 1:
                return null;
            case 2:
                return getText(R.string.ime_action_go);
            case 3:
                return getText(R.string.ime_action_search);
            case 4:
                return getText(R.string.ime_action_send);
            case 5:
                return getText(R.string.ime_action_next);
            case 6:
                return getText(R.string.ime_action_done);
            case 7:
                return getText(R.string.ime_action_previous);
            default:
                return getText(R.string.ime_action_default);
        }
    }

    public void onUpdateExtractingVisibility(EditorInfo editorInfo) {
        if (editorInfo.inputType == 0 || (editorInfo.imeOptions & 268435456) != 0) {
            setExtractViewShown(false);
        } else {
            setExtractViewShown(true);
        }
    }

    public void onUpdateExtractingViews(EditorInfo editorInfo) throws Resources.NotFoundException {
        if (isExtractViewShown() && this.mExtractAccessories != null) {
            if (editorInfo.actionLabel != null || ((editorInfo.imeOptions & 255) != 1 && (editorInfo.imeOptions & 536870912) == 0 && editorInfo.inputType != 0)) {
                this.mExtractAccessories.setVisibility(0);
                View view = this.mExtractAction;
                if (view != null) {
                    if (view instanceof ImageButton) {
                        ((ImageButton) view).setImageResource(getIconForImeAction(editorInfo.imeOptions));
                        if (editorInfo.actionLabel != null) {
                            this.mExtractAction.setContentDescription(editorInfo.actionLabel);
                        } else {
                            this.mExtractAction.setContentDescription(getTextForImeAction(editorInfo.imeOptions));
                        }
                    } else if (editorInfo.actionLabel != null) {
                        ((TextView) this.mExtractAction).lambda$setTextAsync$0(editorInfo.actionLabel);
                    } else {
                        ((TextView) this.mExtractAction).lambda$setTextAsync$0(getTextForImeAction(editorInfo.imeOptions));
                    }
                    this.mExtractAction.setOnClickListener(this.mActionClickListener);
                    return;
                }
                return;
            }
            this.mExtractAccessories.setVisibility(8);
            View view2 = this.mExtractAction;
            if (view2 != null) {
                view2.setOnClickListener(null);
            }
        }
    }

    public void onExtractingInputChanged(EditorInfo editorInfo) {
        if (!useWritingToolkit() && editorInfo.inputType == 0) {
            requestHideSelf(2, 31);
        }
    }

    void startExtractingText(boolean z) {
        ExtractEditText extractEditText = this.mExtractEditText;
        if (extractEditText != null && getCurrentInputStarted() && isFullscreenMode()) {
            this.mExtractedToken++;
            ExtractedTextRequest extractedTextRequest = new ExtractedTextRequest();
            extractedTextRequest.token = this.mExtractedToken;
            extractedTextRequest.flags = 1;
            extractedTextRequest.hintMaxLines = 10;
            extractedTextRequest.hintMaxChars = 10000;
            InputConnection currentInputConnection = getCurrentInputConnection();
            ExtractedText extractedText = currentInputConnection == null ? null : currentInputConnection.getExtractedText(extractedTextRequest, 1);
            this.mExtractedText = extractedText;
            if (extractedText == null || currentInputConnection == null) {
                Log.e(TAG, "Unexpected null in startExtractingText : mExtractedText = " + this.mExtractedText + ", input connection = " + currentInputConnection);
            }
            EditorInfo currentInputEditorInfo = getCurrentInputEditorInfo();
            try {
                extractEditText.startInternalChanges();
                onUpdateExtractingVisibility(currentInputEditorInfo);
                onUpdateExtractingViews(currentInputEditorInfo);
                int i = currentInputEditorInfo.inputType;
                if ((i & 15) == 1 && (262144 & i) != 0) {
                    i |= 131072;
                }
                extractEditText.setInputType(i);
                extractEditText.setHint(currentInputEditorInfo.hintText);
                changeBgColorIfNeeded(extractEditText, currentInputEditorInfo);
                if (this.mExtractedText != null) {
                    extractEditText.setEnabled(true);
                    extractEditText.setExtractedText(this.mExtractedText);
                } else {
                    extractEditText.setEnabled(false);
                    extractEditText.lambda$setTextAsync$0("");
                }
                if (z) {
                    onExtractingInputChanged(currentInputEditorInfo);
                }
            } finally {
                extractEditText.finishInternalChanges();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnCurrentInputMethodSubtypeChanged(InputMethodSubtype inputMethodSubtype) {
        synchronized (this.mLock) {
            this.mNotifyUserActionSent = false;
        }
        onCurrentInputMethodSubtypeChanged(inputMethodSubtype);
    }

    @Deprecated
    public int getInputMethodWindowRecommendedHeight() {
        Log.w(TAG, "getInputMethodWindowRecommendedHeight() is deprecated and now always returns 0. Do not use this method.");
        return 0;
    }

    public final boolean isImeNavigationBarShownForTesting() {
        return this.mNavigationBarController.isShown();
    }

    final void onImeSwitchButtonClickFromClient() {
        this.mPrivOps.onImeSwitchButtonClickFromClient(getDisplayId());
    }

    @Override // android.inputmethodservice.AbstractInputMethodService
    final InputMethodServiceInternal createInputMethodServiceInternal() {
        return new InputMethodServiceInternal() { // from class: android.inputmethodservice.InputMethodService.1
            @Override // android.inputmethodservice.InputMethodServiceInternal
            public Context getContext() {
                return InputMethodService.this;
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public void exposeContent(InputContentInfo inputContentInfo, InputConnection inputConnection) {
                if (inputConnection != null && InputMethodService.this.getCurrentInputConnection() == inputConnection) {
                    exposeContentInternal(inputContentInfo, InputMethodService.this.getCurrentInputEditorInfo());
                }
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public void notifyUserActionIfNecessary() {
                synchronized (InputMethodService.this.mLock) {
                    if (InputMethodService.this.mNotifyUserActionSent) {
                        return;
                    }
                    InputMethodService.this.mPrivOps.notifyUserActionAsync();
                    InputMethodService.this.mNotifyUserActionSent = true;
                }
            }

            private void exposeContentInternal(InputContentInfo inputContentInfo, EditorInfo editorInfo) {
                Uri contentUri = inputContentInfo.getContentUri();
                IInputContentUriToken iInputContentUriTokenCreateInputContentUriToken = InputMethodService.this.mPrivOps.createInputContentUriToken(contentUri, editorInfo.packageName);
                if (iInputContentUriTokenCreateInputContentUriToken == null) {
                    Log.e(InputMethodService.TAG, "createInputContentAccessToken failed. contentUri=" + contentUri.toString() + " packageName=" + editorInfo.packageName);
                    return;
                }
                inputContentInfo.setUriToken(iInputContentUriTokenCreateInputContentUriToken);
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
                InputMethodService.this.dump(fileDescriptor, printWriter, strArr);
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public void triggerServiceDump(String str, byte[] bArr) {
                ImeTracing.getInstance().triggerServiceDump(str, InputMethodService.this.mDumper, bArr);
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public boolean isServiceDestroyed() {
                return InputMethodService.this.mDestroyed;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int computeImeWindowVis() {
        return (isInputViewShown() ? 2 : 0) | 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ImeTracker.Token createStatsToken(boolean z, int i, boolean z2) {
        return ImeTracker.forLogging().onStart(z ? 1 : 2, 7, i, z2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.inputmethodservice.AbstractInputMethodService, android.app.Service
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        PrintWriterPrinter printWriterPrinter = new PrintWriterPrinter(printWriter);
        printWriterPrinter.println("Input method service state for " + this + ":");
        StringBuilder sb = new StringBuilder("  mViewsCreated=");
        sb.append(this.mViewsCreated);
        printWriterPrinter.println(sb.toString());
        printWriterPrinter.println("  mDecorViewVisible=" + this.mDecorViewVisible + " mDecorViewWasVisible=" + this.mDecorViewWasVisible + " mWindowVisible=" + this.mWindowVisible + " mInShowWindow=" + this.mInShowWindow);
        StringBuilder sb2 = new StringBuilder("  Configuration=");
        sb2.append(getResources().getConfiguration());
        printWriterPrinter.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder("  mToken=");
        sb3.append(this.mToken);
        printWriterPrinter.println(sb3.toString());
        printWriterPrinter.println("  mInputBinding=" + this.mInputBinding);
        printWriterPrinter.println("  mInputConnection=" + this.mInputConnection);
        printWriterPrinter.println("  mStartedInputConnection=" + this.mStartedInputConnection);
        printWriterPrinter.println("  mInputStarted=" + this.mInputStarted + " mInputViewStarted=" + this.mInputViewStarted + " mCandidatesViewStarted=" + this.mCandidatesViewStarted);
        if (this.mInputEditorInfo != null) {
            printWriterPrinter.println("  mInputEditorInfo:");
            this.mInputEditorInfo.dump(printWriterPrinter, "    ", false);
        } else {
            printWriterPrinter.println("  mInputEditorInfo: null");
        }
        printWriterPrinter.println("  mShowInputRequested=" + this.mShowInputRequested + " mLastShowInputRequested=" + this.mLastShowInputRequested + " mShowInputFlags=0x" + Integer.toHexString(this.mShowInputFlags));
        printWriterPrinter.println("  mCandidatesVisibility=" + this.mCandidatesVisibility + " mFullscreenApplied=" + this.mFullscreenApplied + " mIsFullscreen=" + this.mIsFullscreen + " mExtractViewHidden=" + this.mExtractViewHidden);
        if (this.mExtractedText != null) {
            printWriterPrinter.println("  mExtractedText:");
            printWriterPrinter.println("    text=" + this.mExtractedText.text.length() + " chars startOffset=" + this.mExtractedText.startOffset);
            printWriterPrinter.println("    selectionStart=" + this.mExtractedText.selectionStart + " selectionEnd=" + this.mExtractedText.selectionEnd + " flags=0x" + Integer.toHexString(this.mExtractedText.flags));
        } else {
            printWriterPrinter.println("  mExtractedText: null");
        }
        printWriterPrinter.println("  mExtractedToken=" + this.mExtractedToken);
        printWriterPrinter.println("  mIsInputViewShown=" + this.mIsInputViewShown + " mStatusIcon=" + this.mStatusIcon);
        printWriterPrinter.println("  Last computed insets:");
        printWriterPrinter.println("    contentTopInsets=" + this.mTmpInsets.contentTopInsets + " visibleTopInsets=" + this.mTmpInsets.visibleTopInsets + " touchableInsets=" + this.mTmpInsets.touchableInsets + " touchableRegion=" + this.mTmpInsets.touchableRegion);
        StringBuilder sb4 = new StringBuilder("  mSettingsObserver=");
        sb4.append(this.mSettingsObserver);
        printWriterPrinter.println(sb4.toString());
        StringBuilder sb5 = new StringBuilder("  mNavigationBarController=");
        sb5.append(this.mNavigationBarController.toDebugString());
        printWriterPrinter.println(sb5.toString());
        printWriterPrinter.println("  mCustomImeSwitcherButtonRequestedVisible=" + this.mCustomImeSwitcherButtonRequestedVisible);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void compatHandleBack() {
        if (!this.mDecorViewVisible) {
            Log.e(TAG, "Back callback invoked on a hidden IME. Removing the callback...");
            unregisterDefaultOnBackInvokedCallback();
        } else {
            KeyEvent keyEventCreateBackKeyEvent = createBackKeyEvent(0, false);
            onKeyDown(4, keyEventCreateBackKeyEvent);
            onKeyUp(4, createBackKeyEvent(1, (keyEventCreateBackKeyEvent.getFlags() & 1073741824) != 0));
        }
    }

    private boolean methodIsOverridden(String str, Class<?>... clsArr) {
        try {
            return getClass().getMethod(str, clsArr).getDeclaringClass() != InputMethodService.class;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Method must exist.", e);
        }
    }

    private void sendDisplayIdForDex() {
        if (SemImsUtils.isHoneyboard(getPackageName())) {
            Bundle bundle = new Bundle();
            bundle.putInt("display_id", this.mImm.getCurTokenDisplayId());
            onAppPrivateCommand("DISPLAY_ID", bundle);
        }
    }

    private void handleSipDualView() {
        Context contextCreateDisplayContextAndSetTheme = SemImsUtils.createDisplayContextAndSetTheme(this, this.mTheme, this.mImm);
        this.mTargetDisplayContext = contextCreateDisplayContextAndSetTheme;
        this.mInflater = (LayoutInflater) contextCreateDisplayContextAndSetTheme.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Trace.traceBegin(32L, "IMS.initSoftInputWindow");
        this.mWindow = new SoftInputWindow(this.mTargetDisplayContext, this.mTheme, this.mDispatcherState);
    }

    private static void handleSepKeyboardLayoutParams(WindowManager.LayoutParams layoutParams) {
        layoutParams.setFitInsetsIgnoringVisibility(true);
        if (SemImsRune.supportPreferredMinDisplayRefreshRate) {
            layoutParams.preferredMinDisplayRefreshRate = 60.0f;
        }
    }

    private int getPaddingBottom(int[] iArr) {
        if (!SemImsUtils.isMockIme(getPackageName())) {
            return 0;
        }
        int navigationBarHeight = SemImsUtils.getNavigationBarHeight(getResources());
        int i = iArr[1];
        if (i <= 0 || i > navigationBarHeight || this.mInputFrame.getVisibility() != 0) {
            return 0;
        }
        Log.i(TAG, "onComputeInsets: a navibar height padding is applied.");
        FrameLayout frameLayout = this.mInputFrame;
        frameLayout.setPadding(frameLayout.getPaddingLeft(), this.mInputFrame.getPaddingTop(), this.mInputFrame.getPaddingRight(), navigationBarHeight);
        return navigationBarHeight;
    }

    private static void changeBgColorIfNeeded(ExtractEditText extractEditText, EditorInfo editorInfo) {
        if (editorInfo.privateImeOptions != null) {
            String[] strArrSplit = editorInfo.privateImeOptions.split("#");
            if (strArrSplit.length == 2) {
                if ("AppName=Memo".equals(strArrSplit[0]) || "AppName=Diary".equals(strArrSplit[0])) {
                    String[] strArrSplit2 = strArrSplit[1].split("=");
                    if (strArrSplit2.length == 2 && "Color".equals(strArrSplit2[0])) {
                        String strReplaceAll = strArrSplit2[1].toLowerCase().replaceAll("0x", "");
                        strArrSplit2[1] = strReplaceAll;
                        try {
                            extractEditText.setBackgroundColor((int) Long.parseLong(strReplaceAll, 16));
                        } catch (NumberFormatException unused) {
                        }
                    }
                }
            }
        }
    }

    private void sendInputViewShownState(boolean z) {
        SemImsUtils.sendBroadcastShownState(this, this.mInputEditorInfo, z, this.mCandidatesVisibility);
        if (this.mIsLastWindowVisible != z) {
            SemImsUtils.sendBroadcastForSSRM(this, z);
            this.mIsLastWindowVisible = z;
        }
    }

    private void checkandshowInputMehtodPicker() {
        this.mImm.dismissAndShowAgainInputMethodPicker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void undoMinimizeSoftInputWrapper() {
        undoMinimizeSoftInput();
        if (MINIMIZED_IME_INSET_ANIM) {
            this.mMinimizedHeight = 0;
            Log.d(TAG, "undoMinimizeSoftInputWrapper: reset minimizedHeight=" + this.mMinimizedHeight);
        }
    }

    private boolean useWritingToolkit() {
        return ViewRune.SUPPORT_WRITING_TOOLKIT && SemInputMethodManagerUtils.CLASS_NAME_TOOLKIT_HONEYBOARD.equals(getClass().getName());
    }
}
