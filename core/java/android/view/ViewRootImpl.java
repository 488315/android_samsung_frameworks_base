package android.view;

import android.Manifest;
import android.animation.AnimationHandler;
import android.animation.LayoutTransition;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.ICompatCameraControlCallback;
import android.app.PendingIntent$$ExternalSyntheticLambda1;
import android.app.ResourcesManager;
import android.app.WindowConfiguration;
import android.app.compat.CompatChanges;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentResolver;
import android.content.Context;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BLASTBufferQueue;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.FrameInfo;
import android.graphics.HardwareRenderer;
import android.graphics.HardwareRendererObserver;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.gnss.GnssSignalType;
import android.hardware.input.InputManagerGlobal;
import android.hardware.input.InputSettings;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioManager;
import android.media.MediaMetrics;
import android.media.TtmlUtils;
import android.media.audio.Enums;
import android.p009os.Binder;
import android.p009os.Build;
import android.p009os.Bundle;
import android.p009os.Debug;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.ParcelFileDescriptor;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.SystemClock;
import android.p009os.SystemProperties;
import android.p009os.Trace;
import android.p009os.UserHandle;
import android.provider.Settings;
import android.sysprop.DisplayProperties;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.LongArray;
import android.util.MergedConfiguration;
import android.util.NtpTrustedTime;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.proto.ProtoOutputStream;
import android.view.ActionMode;
import android.view.AttachedSurfaceControl;
import android.view.Choreographer;
import android.view.GestureDetector;
import android.view.IWindow;
import android.view.InputQueue;
import android.view.InsetsSourceControl;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ScrollCaptureResponse;
import android.view.SemBlurInfo;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeIdManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.accessibility.AccessibilityWindowAttributes;
import android.view.accessibility.IAccessibilityEmbeddedConnection;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.ContentCaptureSession;
import android.view.contentcapture.MainContentCaptureSession;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodManager;
import android.widget.Scroller;
import android.window.BackEvent;
import android.window.ClientWindowFrames;
import android.window.CompatOnBackInvokedCallback;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import android.window.ScreenCapture;
import android.window.SurfaceSyncGroup;
import android.window.WindowOnBackInvokedDispatcher;
import com.android.internal.C4337R;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.internal.p029os.IResultReceiver;
import com.android.internal.p029os.SomeArgs;
import com.android.internal.policy.DecorView;
import com.android.internal.policy.PhoneFallbackEventHandler;
import com.android.internal.util.Preconditions;
import com.android.internal.view.BaseSurfaceHolder;
import com.android.internal.view.RootViewSurfaceTaker;
import com.android.internal.view.SurfaceCallbackHelper;
import com.samsung.android.content.smartclip.SmartClipDataCropperImpl;
import com.samsung.android.content.smartclip.SmartClipDataExtractionEvent;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.core.CompatTranslator;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.ims.options.SemCapabilities;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import com.samsung.android.util.SemViewUtils;
import com.samsung.android.widget.SemPressGestureDetector;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes4.dex */
public final class ViewRootImpl implements ViewParent, View.AttachInfo.Callbacks, ThreadedRenderer.DrawCallbacks, AttachedSurfaceControl {
    private static final String AOD_SHOW_STATE = "aod_show_state";
    private static final int BOUNDS_SURFACE_SUB_LAYER = -3;
    public static final boolean CAPTION_ON_SHELL;
    public static final boolean CLIENT_TRANSIENT;
    private static final int CONTENT_CAPTURE_ENABLED_FALSE = 2;
    private static final int CONTENT_CAPTURE_ENABLED_NOT_CHECKED = 0;
    private static final int CONTENT_CAPTURE_ENABLED_TRUE = 1;
    private static final boolean DBG = false;
    private static final boolean DEBUG_BLAST;
    private static final boolean DEBUG_BLUR;
    private static final boolean DEBUG_CONFIGURATION;
    private static final boolean DEBUG_CONTENT_CAPTURE;
    private static final boolean DEBUG_DIALOG;
    private static final boolean DEBUG_DRAW;
    private static final boolean DEBUG_FPS;
    private static final boolean DEBUG_IMF;
    private static final boolean DEBUG_INPUT_RESIZE;
    private static final boolean DEBUG_INPUT_STAGES;
    private static final boolean DEBUG_KEEP_SCREEN_ON;
    private static final boolean DEBUG_LAYOUT;
    static final boolean DEBUG_MEASURE;
    private static final boolean DEBUG_ORIENTATION;
    private static final boolean DEBUG_SCROLL_CAPTURE;
    static final boolean DEBUG_TOUCH_EVENT;
    private static final boolean DEBUG_TOUCH_NAVIGATION = false;
    private static final boolean DEBUG_TRACKBALL;
    private static final boolean DEBUG_TRAVERSAL;
    private static final String DEBUG_TRAVERSAL_PACKAGE_NAME;
    static final boolean DEBUG_WINDOW_INSETS;
    private static final boolean ENABLE_INPUT_LATENCY_TRACKING = true;
    private static final int KEEP_CLEAR_AREA_REPORT_RATE_MILLIS = 100;
    public static final boolean LOCAL_LAYOUT;
    private static final boolean LOCAL_LOGV = false;
    private static final int LOGTAG_INPUT_FOCUS = 62001;
    private static final int MAX_QUEUED_INPUT_EVENT_POOL_SIZE = 10;
    static final int MAX_TRACKBALL_DELAY = 250;
    private static final int MSG_CHECK_FOCUS = 13;
    private static final int MSG_CLEAR_ACCESSIBILITY_FOCUS_HOST = 21;
    private static final int MSG_CLOSE_SYSTEM_DIALOGS = 14;
    private static final int MSG_DIE = 3;
    private static final int MSG_DISPATCH_APP_VISIBILITY = 8;
    private static final int MSG_DISPATCH_DRAG_EVENT = 15;
    private static final int MSG_DISPATCH_DRAG_LOCATION_EVENT = 16;
    private static final int MSG_DISPATCH_GET_NEW_SURFACE = 9;
    private static final int MSG_DISPATCH_INPUT_EVENT = 7;
    private static final int MSG_DISPATCH_KEY_FROM_AUTOFILL = 12;
    private static final int MSG_DISPATCH_KEY_FROM_IME = 11;
    private static final int MSG_DISPATCH_LETTERBOX_DIRECTION_CHANGED = 104;
    private static final int MSG_DISPATCH_SYSTEM_UI_VISIBILITY = 17;
    private static final int MSG_DISPATCH_WINDOW_SHOWN = 25;
    private static final int MSG_HIDE_INSETS = 32;
    private static final int MSG_INSETS_CONTROL_CHANGED = 29;
    private static final int MSG_INVALIDATE = 1;
    private static final int MSG_INVALIDATE_RECT = 2;
    private static final int MSG_INVALIDATE_WORLD = 22;
    private static final int MSG_KEEP_CLEAR_RECTS_CHANGED = 35;
    private static final int MSG_PAUSED_FOR_SYNC_TIMEOUT = 37;
    private static final int MSG_POINTER_CAPTURE_CHANGED = 28;
    private static final int MSG_PROCESS_INPUT_EVENTS = 19;
    private static final int MSG_REPORT_KEEP_CLEAR_RECTS = 36;
    private static final int MSG_REQUEST_KEYBOARD_SHORTCUTS = 26;
    private static final int MSG_REQUEST_SCROLL_CAPTURE = 33;
    private static final int MSG_RESIZED = 4;
    private static final int MSG_RESIZED_REPORT = 5;
    private static final int MSG_SHOW_INSETS = 31;
    private static final int MSG_SPEN_GESTURE_EVENT = 103;
    private static final int MSG_SYNTHESIZE_INPUT_EVENT = 24;
    private static final int MSG_SYSTEM_GESTURE_EXCLUSION_CHANGED = 30;
    private static final int MSG_UPDATE_CONFIGURATION = 18;
    private static final int MSG_UPDATE_POINTER_ICON = 27;
    private static final int MSG_WINDOW_FOCUS_CHANGED = 6;
    private static final int MSG_WINDOW_FOCUS_IN_TASK_CHANGED = 105;
    private static final int MSG_WINDOW_MOVED = 23;
    private static final int MSG_WINDOW_TOUCH_MODE_CHANGED = 34;
    private static final boolean MT_RENDERER_AVAILABLE = true;
    private static final String PROPERTY_PROFILE_RENDERING = "viewroot.profile_rendering";
    private static final int REMOVE_CUTOUT_FLAGS = 2097152;
    private static final int REMOVE_CUTOUT_FOR_DISPATCH_FLAGS = 4194304;
    private static final int SCROLL_CAPTURE_REQUEST_TIMEOUT_MILLIS = 2500;
    private static final String TAG = "ViewRootImpl";
    private static final int UNSET_SYNC_ID = -1;
    private static final boolean USE_ASYNC_PERFORM_HAPTIC_FEEDBACK = true;
    private static final int WMS_SYNC_MERGED = 3;
    private static final int WMS_SYNC_NONE = 0;
    private static final int WMS_SYNC_PENDING = 1;
    private static final int WMS_SYNC_RETURNED = 2;
    static final Interpolator mResizeInterpolator;
    private static boolean sAlwaysAssignFocus;
    private static volatile boolean sAnrReported;
    private static boolean sCompatibilityDone;
    private static final ArrayList<ConfigChangedCallback> sConfigCallbacks;
    static boolean sFirstDrawComplete;
    static final ArrayList<Runnable> sFirstDrawHandlers;
    private static int sNumSyncsInProgress;
    static final ThreadLocal<HandlerActionQueue> sRunQueues;
    private static boolean sSafeScheduleTraversals;
    private static final Object sSyncProgressLock;
    static BLASTBufferQueue.TransactionHangCallback sTransactionHangCallback;
    private IAccessibilityEmbeddedConnection mAccessibilityEmbeddedConnection;
    View mAccessibilityFocusedHost;
    AccessibilityNodeInfo mAccessibilityFocusedVirtualView;
    final AccessibilityInteractionConnectionManager mAccessibilityInteractionConnectionManager;
    AccessibilityInteractionController mAccessibilityInteractionController;
    final AccessibilityManager mAccessibilityManager;
    private AccessibilityWindowAttributes mAccessibilityWindowAttributes;
    private SurfaceSyncGroup mActiveSurfaceSyncGroup;
    private ActivityConfigCallback mActivityConfigCallback;
    boolean mAdded;
    boolean mAddedTouchMode;
    private boolean mAppVisibilityChanged;
    boolean mAppVisible;
    private int mAppliedLetterboxDirection;
    boolean mApplyInsetsRequested;
    final View.AttachInfo mAttachInfo;
    AudioManager mAudioManager;
    final String mBasePackageName;
    private boolean mBixbyTouchTriggered;
    private BLASTBufferQueue mBlastBufferQueue;
    private Canvas mBlurCanvas;
    SemBlurInfo.ColorCurve mBlurColorCurve;
    boolean mBlurColorCurveEnabled;
    private SemGfxImageFilter mBlurFilter;
    int mBlurRadius;
    private final BackgroundBlurDrawable.Aggregator mBlurRegionAggregator;
    private boolean mBoundsCompatTranslatorEnabled;
    private SurfaceControl mBoundsLayer;
    private int mBoundsLayerCreatedCount;
    private boolean mCanAllowPokeDrawLock;
    private boolean mCanTriggerBixbyTouch;
    Bitmap mCanvasBlurBitmap;
    boolean mCanvasBlurEnabled;
    private int mCanvasDownScale;
    private int mCanvasOffsetX;
    private int mCanvasOffsetY;
    private boolean mCheckIfCanDraw;
    private final Rect mChildBoundingInsets;
    private boolean mChildBoundingInsetsChanged;
    final Choreographer mChoreographer;
    int mClientWindowLayoutFlags;
    private CompatOnBackInvokedCallback mCompatOnBackInvokedCallback;
    private CompatTranslator mCompatTranslator;
    private boolean mCompatTranslatorEnabled;
    final SystemUiVisibilityInfo mCompatibleVisibilityInfo;
    final ConsumeBatchedInputImmediatelyRunnable mConsumeBatchedInputImmediatelyRunnable;
    boolean mConsumeBatchedInputImmediatelyScheduled;
    boolean mConsumeBatchedInputScheduled;
    final ConsumeBatchedInputRunnable mConsumedBatchedInputRunnable;
    int mContentCaptureEnabled;
    final ContentResolver mContentResolver;
    public final Context mContext;
    int mCurScrollY;
    View mCurrentDragView;
    private PointerIcon mCustomPointerIcon;
    private boolean mDeferTransactionRequested;
    private final int mDensity;
    private float mDesiredHdrSdrRatio;
    private boolean mDesktopMode;
    private SemDesktopModeManager mDesktopModeManager;
    private boolean mDesktopModeStandAlone;
    private Rect mDirty;
    private boolean mDisableSuperHdr;
    int mDispatchedSystemBarAppearance;
    int mDispatchedSystemUiVisibility;
    Display mDisplay;
    boolean mDisplayDecorationCached;
    private final DisplayManager.DisplayListener mDisplayListener;
    ClipDescription mDragDescription;
    final PointF mDragPoint;
    private boolean mDragResizing;
    boolean mDrawingAllowed;
    private boolean mDrewOnceForSync;
    private boolean mEarlyHasWindowFocus;
    private final Executor mExecutor;
    FallbackEventHandler mFallbackEventHandler;
    private boolean mFastScrollSoundEffectsEnabled;
    boolean mFirst;
    InputStage mFirstInputStage;
    InputStage mFirstPostImeInputStage;
    private String mFirstPreviousSyncSafeguardInfo;
    private boolean mFlexPanelScrollEnabled;
    private float mFlexPanelScrollY;
    private boolean mForceDecorViewVisibility;
    private boolean mForceDisableBLAST;
    private boolean mForceDraw;
    private boolean mForceModeInScreenshot;
    private boolean mForceNextConfigUpdate;
    boolean mForceNextWindowRelayout;
    private boolean mForceUpdateBoundsLayer;
    private int mFpsNumFrames;
    private long mFpsPrevTime;
    private long mFpsStartTime;
    long mFrameNumber;
    boolean mFullRedrawNeeded;
    private final ViewRootRectTracker mGestureExclusionTracker;
    final HCTRelayoutHandler mHCTRelayoutHandler;
    final ViewRootHandler mHandler;
    boolean mHandlingLayoutInLayoutRequest;
    private final HandwritingInitiator mHandwritingInitiator;
    HardwareRendererObserver mHardwareRendererObserver;
    int mHardwareXOffset;
    int mHardwareYOffset;
    private boolean mHasPendingKeepClearAreaChange;
    boolean mHasPendingTransactions;
    private Consumer<Display> mHdrSdrRatioChangedListener;
    int mHeight;
    final HighContrastTextManager mHighContrastTextManager;
    private final ImeFocusController mImeFocusController;
    private boolean mInLayout;
    private final InputEventCompatProcessor mInputCompatProcessor;
    private final InputEventAssigner mInputEventAssigner;
    protected final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    private WindowInputEventReceiver mInputEventReceiver;
    InputQueue mInputQueue;
    InputQueue.Callback mInputQueueCallback;
    private final InsetsController mInsetsController;
    private float mInvCompatScale;
    private Runnable mInvalidateForScreenshotRunnable;
    final InvalidateOnAnimationRunnable mInvalidateOnAnimationRunnable;
    private boolean mInvalidateRootRequested;
    boolean mIsAmbientMode;
    public boolean mIsAnimating;
    private boolean mIsBoundsColorLayer;
    boolean mIsCreating;
    private boolean mIsCutoutRemoveForDispatchNeeded;
    private boolean mIsCutoutRemoveNeeded;
    private boolean mIsDetached;
    boolean mIsDeviceDefault;
    boolean mIsDrawing;
    boolean mIsInTraversal;
    private final boolean mIsStylusPointerIconEnabled;
    private boolean mIsSurfaceOpaque;
    private boolean mIsWindowOpaque;
    private Rect mKeepClearAccessibilityFocusRect;
    private final ViewRootRectTracker mKeepClearRectsTracker;
    private int mLastClickToolType;
    private final Configuration mLastConfigurationFromResources;
    final ViewTreeObserver.InternalInsetsInfo mLastGivenInsets;
    boolean mLastInCompatMode;
    private final Rect mLastLayoutFrame;
    String mLastPerformDrawSkippedReason;
    String mLastPerformTraversalsSkipDrawReason;
    String mLastReportNextDrawReason;
    private final MergedConfiguration mLastReportedMergedConfiguration;
    WeakReference<View> mLastScrolledFocus;
    private final Point mLastSurfaceSize;
    int mLastSyncSeqId;
    int mLastSystemUiVisibility;
    final PointF mLastTouchPoint;
    int mLastTouchSource;
    private int mLastTransformHint;
    private WindowInsets mLastWindowInsets;
    boolean mLayoutRequested;
    ArrayList<View> mLayoutRequesters;
    final IBinder mLeashToken;
    volatile Object mLocalDragState;
    final WindowLeaked mLocation;
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private int mMinimumSizeForOverlappingWithCutoutAsDefault;
    private MotionEventMonitor mMotionEventMonitor;
    private boolean mNeedsRendererSetup;
    private boolean mNewDexMode;
    boolean mNewSurfaceNeeded;
    private final int mNoncompatDensity;
    private int mNumPausedForSync;
    private final WindowOnBackInvokedDispatcher mOnBackInvokedDispatcher;
    int mOrigWindowType;
    Rect mOverrideInsetsFrame;
    public View mParentDecorView;
    boolean mPausedForTransition;
    boolean mPendingAlwaysConsumeSystemBars;
    final Rect mPendingBackDropFrame;
    private boolean mPendingDragResizing;
    int mPendingInputEventCount;
    QueuedInputEvent mPendingInputEventHead;
    String mPendingInputEventQueueLengthCounterName;
    QueuedInputEvent mPendingInputEventTail;
    private final MergedConfiguration mPendingMergedConfiguration;
    private SurfaceControl.Transaction mPendingTransaction;
    private ArrayList<LayoutTransition> mPendingTransitions;
    private Rect mPendingWinFrame;
    boolean mPerformContentCapture;
    boolean mPointerCapture;
    private Integer mPointerIconType;
    private PokeDrawLockController mPokeDrawLockController;
    private SurfaceSyncGroup mPreviousSyncSafeguard;
    private final Object mPreviousSyncSafeguardLock;
    Region mPreviousTouchableRegion;
    private int mPreviousTransformHint;
    final Region mPreviousTransparentRegion;
    boolean mProcessInputEventsScheduled;
    private boolean mProfile;
    private boolean mProfileRendering;
    private QueuedInputEvent mQueuedInputEventPool;
    private int mQueuedInputEventPoolSize;
    private Bundle mRelayoutBundle;
    private boolean mRelayoutRequested;
    private int mRelayoutSeq;
    private boolean mRemoved;
    private float mRenderHdrSdrRatio;
    private Choreographer.FrameCallback mRenderProfiler;
    private boolean mRenderProfilingEnabled;
    boolean mReportNextDraw;
    public int mRequestedLetterboxDirection;
    private HashSet<ScrollCaptureCallback> mRootScrollCaptureCallbacks;
    private DragEvent mSavedStickyDragEvent;
    private int mScheduleTraversalDeferCount;
    private long mScrollCaptureRequestTimeout;
    boolean mScrollMayChange;
    int mScrollY;
    Scroller mScroller;
    private boolean mSemEarlyAppVisibility;
    private boolean mSemEarlyAppVisibilityChanged;
    private SemPressGestureDetector mSemPressGestureDetector;
    SendWindowContentChangedAccessibilityEvent mSendWindowContentChangedAccessibilityEvent;
    private final Executor mSimpleExecutor;
    private boolean mSkipScreenshot;
    SmartClipRemoteRequestDispatcherProxy mSmartClipDispatcherProxy;
    int mSoftInputMode;
    View mStartedDragViewForA11y;
    boolean mStopped;
    public final Surface mSurface;
    private final ArrayList<SurfaceChangedCallback> mSurfaceChangedCallbacks;
    private final SurfaceControl mSurfaceControl;
    BaseSurfaceHolder mSurfaceHolder;
    SurfaceHolder.Callback2 mSurfaceHolderCallback;
    private int mSurfaceSequenceId;
    private final SurfaceSession mSurfaceSession;
    private final Point mSurfaceSize;
    private boolean mSyncBuffer;
    int mSyncSeqId;
    InputStage mSyntheticInputStage;
    private String mTag;
    final int mTargetSdkVersion;
    private final InsetsSourceControl.Array mTempControls;
    HashSet<View> mTempHashSet;
    private final InsetsState mTempInsets;
    private final Rect mTempRect;
    private final WindowConfiguration mTempWinConfig;
    final Thread mThread;
    private final ClientWindowFrames mTmpFrames;
    final int[] mTmpLocation;
    private Rect mTmpScaledBounds;
    final TypedValue mTmpValue;
    Region mTouchableRegion;
    private final SurfaceControl.Transaction mTransaction;
    private ArrayList<AttachedSurfaceControl.OnBufferTransformHintChangedListener> mTransformHintListeners;
    CompatibilityInfo.Translator mTranslator;
    final Region mTransparentRegion;
    int mTraversalBarrier;
    final TraversalRunnable mTraversalRunnable;
    public boolean mTraversalScheduled;
    private int mTypesHiddenByFlags;
    boolean mUnbufferedInputDispatch;
    int mUnbufferedInputSource;
    private final UnhandledKeyManager mUnhandledKeyManager;
    private final ViewRootRectTracker mUnrestrictedKeepClearRectsTracker;
    boolean mUpcomingInTouchMode;
    boolean mUpcomingWindowFocus;
    private boolean mUpcomingWindowFocusInTask;
    private boolean mUpdateHdrSdrRatioInfo;
    private boolean mUpdateSurfaceNeeded;
    private boolean mUseBLASTAdapter;
    private boolean mUseMTRenderer;
    View mView;
    private final boolean mViewBoundsSandboxingEnabled;
    final ViewConfiguration mViewConfiguration;
    protected final ViewFrameInfo mViewFrameInfo;
    private int mViewLayoutDirectionInitial;
    private boolean mViewMeasureDeferred;
    int mViewVisibility;
    private final Rect mVisRect;
    int mWidth;
    boolean mWillDrawSoon;
    final Rect mWinFrame;
    private final Rect mWinFrameInScreen;
    private Rect mWinFrameScreen;
    final BinderC3744W mWindow;
    public final WindowManager.LayoutParams mWindowAttributes;
    boolean mWindowAttributesChanged;
    final ArrayList<WindowCallbacks> mWindowCallbacks;
    CountDownLatch mWindowDrawCountDown;
    boolean mWindowFocusChanged;
    private boolean mWindowFocusInTaskChanged;
    private final WindowLayout mWindowLayout;
    final IWindowSession mWindowSession;
    private SurfaceSyncGroup mWmsRequestSyncGroup;
    int mWmsRequestSyncGroupState;

    public interface ActivityConfigCallback {
        void onConfigurationChanged(Configuration configuration, int i);

        void requestCompatCameraControl(boolean z, boolean z2, ICompatCameraControlCallback iCompatCameraControlCallback);
    }

    public interface ConfigChangedCallback {
        void onConfigurationChanged(Configuration configuration);
    }

    static {
        DEBUG_DRAW = SystemProperties.getInt("viewroot.debug.draw", 0) != 0;
        DEBUG_LAYOUT = SystemProperties.getInt("viewroot.debug.layout", 0) != 0;
        DEBUG_DIALOG = SystemProperties.getInt("viewroot.debug.dialog", 0) != 0;
        DEBUG_INPUT_RESIZE = SystemProperties.getInt("viewroot.debug.input_resize", 0) != 0;
        DEBUG_ORIENTATION = SystemProperties.getInt("viewroot.debug.orientation", 0) != 0;
        DEBUG_TRACKBALL = SystemProperties.getInt("viewroot.debug.trackball", 0) != 0;
        DEBUG_IMF = SystemProperties.getInt("viewroot.debug.imf", 0) != 0;
        DEBUG_CONFIGURATION = SystemProperties.getInt("viewroot.debug.configuration", 0) != 0;
        DEBUG_FPS = SystemProperties.getInt("viewroot.debug.fps", 0) != 0;
        DEBUG_INPUT_STAGES = SystemProperties.getInt("viewroot.debug.input_stages", 0) != 0;
        DEBUG_KEEP_SCREEN_ON = SystemProperties.getInt("viewroot.debug.keep_screen_on", 0) != 0;
        DEBUG_CONTENT_CAPTURE = SystemProperties.getInt("viewroot.debug.content_capture", 0) != 0;
        DEBUG_SCROLL_CAPTURE = SystemProperties.getInt("viewroot.debug.scroll_capture", 0) != 0;
        DEBUG_BLAST = true;
        DEBUG_BLUR = SystemProperties.getInt("viewroot.debug.blur", 0) != 0;
        DEBUG_WINDOW_INSETS = SystemProperties.getInt("viewroot.debug.window_insets", 0) != 0;
        DEBUG_TOUCH_EVENT = SystemProperties.getInt("viewroot.debug.touch_event", 0) != 0;
        DEBUG_MEASURE = SystemProperties.getInt("viewroot.debug.measure", 0) != 0;
        DEBUG_TRAVERSAL = SystemProperties.getInt("viewroot.debug.traversal", 0) != 0;
        DEBUG_TRAVERSAL_PACKAGE_NAME = SystemProperties.get("viewroot.debug.traversal_pkg", "");
        CAPTION_ON_SHELL = SystemProperties.getBoolean("persist.wm.debug.caption_on_shell", true);
        CLIENT_TRANSIENT = SystemProperties.getBoolean("persist.wm.debug.client_transient", false);
        LOCAL_LAYOUT = SystemProperties.getBoolean("persist.debug.local_layout", true);
        sRunQueues = new ThreadLocal<>();
        sFirstDrawHandlers = new ArrayList<>();
        sFirstDrawComplete = false;
        sConfigCallbacks = new ArrayList<>();
        sCompatibilityDone = false;
        mResizeInterpolator = new AccelerateDecelerateInterpolator();
        sSyncProgressLock = new Object();
        sNumSyncsInProgress = 0;
        sAnrReported = false;
        sTransactionHangCallback = new BLASTBufferQueue.TransactionHangCallback() { // from class: android.view.ViewRootImpl.1
            @Override // android.graphics.BLASTBufferQueue.TransactionHangCallback
            public void onTransactionHang(String reason) {
                if (ViewRootImpl.sAnrReported) {
                    return;
                }
                ViewRootImpl.sAnrReported = true;
                long identityToken = Binder.clearCallingIdentity();
                try {
                    ActivityManager.getService().appNotResponding(reason);
                } catch (RemoteException e) {
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(identityToken);
                    throw th;
                }
                Binder.restoreCallingIdentity(identityToken);
            }
        };
        sSafeScheduleTraversals = false;
    }

    protected FrameInfo getUpdatedFrameInfo() {
        FrameInfo frameInfo = this.mChoreographer.mFrameInfo;
        this.mViewFrameInfo.populateFrameInfo(frameInfo);
        this.mViewFrameInfo.reset();
        this.mInputEventAssigner.notifyFrameProcessed();
        return frameInfo;
    }

    private void updateCompatTranslator(int res) {
        ViewRootImpl parentViewRootImpl;
        boolean compatTranslatorEnabled = false;
        if (this.mDesktopMode) {
            if ((131072 & res) != 0) {
                compatTranslatorEnabled = true;
            }
        } else {
            compatTranslatorEnabled = (1048576 & res) != 0;
        }
        if (compatTranslatorEnabled && this.mCompatTranslator == null) {
            CompatTranslator parentTranslator = null;
            View view = this.mParentDecorView;
            if (view != null && view != this.mView && (parentViewRootImpl = view.getViewRootImpl()) != null) {
                parentTranslator = parentViewRootImpl.getCompatTranslator();
            }
            this.mCompatTranslator = new CompatTranslator(parentTranslator);
        }
        if (this.mCompatTranslatorEnabled != compatTranslatorEnabled) {
            if (CoreRune.SAFE_DEBUG) {
                Slog.m113d(this.mTag, "CompatTranslatorEnabled changed from " + this.mCompatTranslatorEnabled + " to " + compatTranslatorEnabled);
            }
            if (compatTranslatorEnabled) {
                this.mWinFrameScreen = new Rect();
            }
            this.mCompatTranslatorEnabled = compatTranslatorEnabled;
        }
        if (CoreRune.FW_BOUNDS_COMPAT_TRANSLATOR_AS_BOUNDS) {
            this.mBoundsCompatTranslatorEnabled = compatTranslatorEnabled && (8388608 & res) != 0;
        }
    }

    CompatTranslator getCompatTranslator() {
        if (this.mCompatTranslatorEnabled) {
            return this.mCompatTranslator;
        }
        return null;
    }

    private void updateCutoutRemoveNeeded(int flags) {
        boolean isCutoutRemoveNeeded = (2097152 & flags) != 0 || (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED && getCompatWindowConfiguration().isSplitScreen());
        if (this.mIsCutoutRemoveNeeded != isCutoutRemoveNeeded) {
            this.mIsCutoutRemoveNeeded = isCutoutRemoveNeeded;
            if (!isCutoutRemoveNeeded) {
                this.mLastWindowInsets = null;
            }
            this.mApplyInsetsRequested = true;
        }
        boolean isCutoutRemoveForDispatchNeeded = (4194304 & flags) != 0;
        if (this.mIsCutoutRemoveForDispatchNeeded != isCutoutRemoveForDispatchNeeded) {
            this.mIsCutoutRemoveForDispatchNeeded = isCutoutRemoveForDispatchNeeded;
            this.mApplyInsetsRequested = true;
        }
    }

    private void updatePositionInBounds(CompatTranslator translator, Configuration configuration) {
        int left = 0;
        int top = 0;
        if (this.mBoundsCompatTranslatorEnabled) {
            Rect bounds = configuration.windowConfiguration.getBounds();
            left = bounds.left;
            top = bounds.top;
        }
        if (translator.savePositionInBounds(left, top)) {
            Slog.m119v(this.mTag, "updatePositionInBounds, enabled=" + this.mBoundsCompatTranslatorEnabled + ", left=" + left + ", top=" + top);
        }
    }

    public ImeFocusController getImeFocusController() {
        return this.mImeFocusController;
    }

    static final class SystemUiVisibilityInfo {
        int globalVisibility;
        int localChanges;
        int localValue;

        SystemUiVisibilityInfo() {
        }
    }

    public HandwritingInitiator getHandwritingInitiator() {
        return this.mHandwritingInitiator;
    }

    public ViewRootImpl(Context context, Display display) {
        this(context, display, WindowManagerGlobal.getWindowSession(), new WindowLayout());
    }

    public ViewRootImpl(Context context, Display display, IWindowSession session, WindowLayout windowLayout) {
        this.mTransformHintListeners = new ArrayList<>();
        this.mPreviousTransformHint = 0;
        this.mFastScrollSoundEffectsEnabled = false;
        this.mWindowCallbacks = new ArrayList<>();
        this.mTmpLocation = new int[2];
        this.mTmpValue = new TypedValue();
        this.mScheduleTraversalDeferCount = 0;
        this.mWindowAttributes = new WindowManager.LayoutParams();
        this.mAppVisible = true;
        this.mForceDecorViewVisibility = false;
        this.mOrigWindowType = -1;
        this.mStopped = false;
        this.mIsAmbientMode = false;
        this.mPausedForTransition = false;
        this.mLastInCompatMode = false;
        this.mViewFrameInfo = new ViewFrameInfo();
        this.mInputEventAssigner = new InputEventAssigner();
        this.mDisplayDecorationCached = false;
        this.mSurfaceSize = new Point();
        this.mLastSurfaceSize = new Point();
        this.mVisRect = new Rect();
        this.mTempRect = new Rect();
        this.mContentCaptureEnabled = 0;
        this.mSyncBuffer = false;
        this.mCheckIfCanDraw = false;
        this.mDrewOnceForSync = false;
        this.mSyncSeqId = 0;
        this.mLastSyncSeqId = 0;
        this.mFrameNumber = 0L;
        this.mPendingTransaction = new SurfaceControl.Transaction();
        this.mUnbufferedInputSource = 0;
        this.mPendingInputEventQueueLengthCounterName = "pq";
        this.mUnhandledKeyManager = new UnhandledKeyManager();
        this.mWindowAttributesChanged = false;
        this.mSurface = new Surface();
        this.mSurfaceControl = new SurfaceControl();
        this.mUpdateHdrSdrRatioInfo = false;
        this.mDesiredHdrSdrRatio = 1.0f;
        this.mRenderHdrSdrRatio = 1.0f;
        this.mHdrSdrRatioChangedListener = null;
        this.mInvalidateForScreenshotRunnable = null;
        this.mForceModeInScreenshot = false;
        this.mSurfaceSession = new SurfaceSession();
        this.mTransaction = new SurfaceControl.Transaction();
        this.mTmpFrames = new ClientWindowFrames();
        this.mPendingBackDropFrame = new Rect();
        this.mWinFrameInScreen = new Rect();
        this.mTempInsets = new InsetsState();
        this.mTempControls = new InsetsSourceControl.Array();
        this.mTempWinConfig = new WindowConfiguration();
        this.mInvCompatScale = 1.0f;
        this.mLastGivenInsets = new ViewTreeObserver.InternalInsetsInfo();
        this.mTypesHiddenByFlags = 0;
        this.mLastConfigurationFromResources = new Configuration();
        this.mLastReportedMergedConfiguration = new MergedConfiguration();
        this.mPendingMergedConfiguration = new MergedConfiguration();
        this.mDragPoint = new PointF();
        this.mLastTouchPoint = new PointF();
        this.mFpsStartTime = -1L;
        this.mFpsPrevTime = -1L;
        this.mPointerIconType = null;
        this.mCustomPointerIcon = null;
        this.mAccessibilityInteractionConnectionManager = new AccessibilityInteractionConnectionManager();
        this.mInLayout = false;
        this.mLayoutRequesters = new ArrayList<>();
        this.mHandlingLayoutInLayoutRequest = false;
        this.mInputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        this.mBlurRegionAggregator = new BackgroundBlurDrawable.Aggregator(this);
        this.mSmartClipDispatcherProxy = null;
        this.mGestureExclusionTracker = new ViewRootRectTracker(new Function() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                List systemGestureExclusionRects;
                systemGestureExclusionRects = ((View) obj).getSystemGestureExclusionRects();
                return systemGestureExclusionRects;
            }
        });
        this.mKeepClearRectsTracker = new ViewRootRectTracker(new Function() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                List collectPreferKeepClearRects;
                collectPreferKeepClearRects = ((View) obj).collectPreferKeepClearRects();
                return collectPreferKeepClearRects;
            }
        });
        this.mUnrestrictedKeepClearRectsTracker = new ViewRootRectTracker(new Function() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                List collectUnrestrictedPreferKeepClearRects;
                collectUnrestrictedPreferKeepClearRects = ((View) obj).collectUnrestrictedPreferKeepClearRects();
                return collectUnrestrictedPreferKeepClearRects;
            }
        });
        this.mPreviousSyncSafeguardLock = new Object();
        this.mNumPausedForSync = 0;
        this.mScrollCaptureRequestTimeout = 2500L;
        this.mSurfaceSequenceId = 0;
        this.mLastTransformHint = Integer.MIN_VALUE;
        this.mRelayoutBundle = new Bundle();
        this.mChildBoundingInsets = new Rect();
        this.mChildBoundingInsetsChanged = false;
        this.mTag = TAG;
        this.mDeferTransactionRequested = false;
        this.mIsDetached = false;
        this.mEarlyHasWindowFocus = false;
        this.mIsDeviceDefault = false;
        this.mBoundsLayerCreatedCount = 0;
        this.mIsBoundsColorLayer = false;
        this.mForceUpdateBoundsLayer = false;
        this.mIsWindowOpaque = true;
        this.mDesktopModeManager = null;
        this.mDesktopMode = false;
        this.mDesktopModeStandAlone = false;
        this.mNewDexMode = false;
        this.mMinimumSizeForOverlappingWithCutoutAsDefault = 0;
        this.mSemPressGestureDetector = null;
        this.mBixbyTouchTriggered = false;
        this.mCanTriggerBixbyTouch = false;
        this.mFlexPanelScrollEnabled = false;
        this.mFlexPanelScrollY = 0.0f;
        this.mProfile = false;
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: android.view.ViewRootImpl.3
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int displayId) {
                if (ViewRootImpl.this.mView != null && ViewRootImpl.this.mDisplay.getDisplayId() == displayId) {
                    int oldDisplayState = ViewRootImpl.this.mAttachInfo.mDisplayState;
                    int newDisplayState = ViewRootImpl.this.mDisplay.getState();
                    Log.m98i(ViewRootImpl.this.mTag, "onDisplayChanged oldDisplayState=" + oldDisplayState + " newDisplayState=" + newDisplayState);
                    if (oldDisplayState != newDisplayState) {
                        ViewRootImpl.this.mAttachInfo.mDisplayState = newDisplayState;
                        ViewRootImpl.this.pokeDrawLockIfNeeded();
                        if (oldDisplayState != 0) {
                            int oldScreenState = toViewScreenState(oldDisplayState);
                            int newScreenState = toViewScreenState(newDisplayState);
                            if (oldScreenState != newScreenState) {
                                ViewRootImpl.this.mView.dispatchScreenStateChanged(newScreenState);
                            }
                            if (ViewRootImpl.DEBUG_TRAVERSAL && ViewRootImpl.DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ViewRootImpl.this.mView.getContext().getPackageName())) {
                                Log.m98i(ViewRootImpl.this.mTag, "Traversal, [4] mView=" + ViewRootImpl.this.mView + " oldDisplayState=" + oldDisplayState);
                            }
                            switch (oldDisplayState) {
                                case 1:
                                    ViewRootImpl.this.mFullRedrawNeeded = true;
                                    ViewRootImpl.this.scheduleTraversals();
                                    break;
                                case 3:
                                case 4:
                                    if (newDisplayState == 2 && displayId == 0 && (ViewRootImpl.this.mWindowAttributes.samsungFlags & 262144) == 0) {
                                        ViewRootImpl.this.mFullRedrawNeeded = true;
                                        ViewRootImpl.this.scheduleTraversals();
                                        break;
                                    }
                                    break;
                            }
                        }
                    }
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int displayId) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int displayId) {
            }

            private int toViewScreenState(int displayState) {
                return Settings.System.getInt(ViewRootImpl.this.mContentResolver, ViewRootImpl.AOD_SHOW_STATE, 0) != 0 ? displayState == 2 ? 1 : 0 : displayState == 1 ? 0 : 1;
            }
        };
        this.mSurfaceChangedCallbacks = new ArrayList<>();
        ViewRootHandler viewRootHandler = new ViewRootHandler();
        this.mHandler = viewRootHandler;
        this.mExecutor = new Executor() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda11
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                ViewRootImpl.this.lambda$new$9(runnable);
            }
        };
        this.mTraversalRunnable = new TraversalRunnable();
        this.mConsumedBatchedInputRunnable = new ConsumeBatchedInputRunnable();
        this.mConsumeBatchedInputImmediatelyRunnable = new ConsumeBatchedInputImmediatelyRunnable();
        this.mInvalidateOnAnimationRunnable = new InvalidateOnAnimationRunnable();
        this.mSkipScreenshot = false;
        this.mDisableSuperHdr = false;
        this.mCanvasBlurEnabled = false;
        this.mBlurColorCurveEnabled = false;
        this.mBlurColorCurve = null;
        this.mBlurRadius = 0;
        this.mCanvasDownScale = 0;
        this.mSimpleExecutor = new PendingIntent$$ExternalSyntheticLambda1();
        this.mRequestedLetterboxDirection = 0;
        this.mAppliedLetterboxDirection = 0;
        this.mContext = context;
        this.mWindowSession = session;
        this.mWindowLayout = windowLayout;
        this.mDisplay = display;
        if (display == null) {
            Log.m98i(TAG, "ViewRootImpl, mDisplay is null #1");
        }
        this.mBasePackageName = context.getBasePackageName();
        this.mContentResolver = context.getContentResolver();
        this.mThread = Thread.currentThread();
        this.mHCTRelayoutHandler = new HCTRelayoutHandler();
        WindowLeaked windowLeaked = new WindowLeaked(null);
        this.mLocation = windowLeaked;
        windowLeaked.fillInStackTrace();
        this.mWidth = -1;
        this.mHeight = -1;
        this.mDirty = new Rect();
        this.mWinFrame = new Rect();
        this.mLastLayoutFrame = new Rect();
        BinderC3744W binderC3744W = new BinderC3744W(this);
        this.mWindow = binderC3744W;
        this.mLeashToken = new Binder();
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        this.mViewVisibility = 8;
        this.mTransparentRegion = new Region();
        this.mPreviousTransparentRegion = new Region();
        this.mFirst = true;
        this.mPerformContentCapture = true;
        this.mAdded = false;
        this.mAttachInfo = new View.AttachInfo(session, binderC3744W, display, this, viewRootHandler, this, context);
        this.mCompatibleVisibilityInfo = new SystemUiVisibilityInfo();
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        this.mHighContrastTextManager = new HighContrastTextManager();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mViewConfiguration = viewConfiguration;
        this.mDensity = context.getResources().getDisplayMetrics().densityDpi;
        this.mNoncompatDensity = context.getResources().getDisplayMetrics().noncompatDensityDpi;
        this.mFallbackEventHandler = new PhoneFallbackEventHandler(context);
        this.mChoreographer = Choreographer.getInstance();
        this.mInsetsController = new InsetsController(new ViewRootInsetsControllerHost(this));
        this.mHandwritingInitiator = new HandwritingInitiator(viewConfiguration, (InputMethodManager) context.getSystemService(InputMethodManager.class));
        this.mViewBoundsSandboxingEnabled = getViewBoundsSandboxingEnabled();
        this.mIsStylusPointerIconEnabled = InputSettings.isStylusPointerIconEnabled(context);
        String processorOverrideName = context.getResources().getString(C4337R.string.config_inputEventCompatProcessorOverrideClassName);
        if (processorOverrideName.isEmpty()) {
            this.mInputCompatProcessor = new InputEventCompatProcessor(context);
        } else {
            InputEventCompatProcessor compatProcessor = null;
            try {
                compatProcessor = (InputEventCompatProcessor) Class.forName(processorOverrideName).getConstructor(Context.class).newInstance(context);
            } catch (Exception e) {
                Log.m97e(TAG, "Unable to create the InputEventCompatProcessor. ", e);
            } finally {
                this.mInputCompatProcessor = compatProcessor;
            }
        }
        if (!sCompatibilityDone) {
            sAlwaysAssignFocus = this.mTargetSdkVersion < 28;
            sCompatibilityDone = true;
        }
        this.mIsDeviceDefault = SemViewUtils.isDeviceDefaultFamily(context);
        if (this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1) {
            if (this.mDisplay.getDisplayId() == 0) {
                updateDesktopMode();
            } else {
                this.mDesktopMode = this.mDisplay.getDisplayId() == 2;
            }
        }
        this.mPokeDrawLockController = new PokeDrawLockController(this);
        if (CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT) {
            this.mMinimumSizeForOverlappingWithCutoutAsDefault = this.mContext.getResources().getDimensionPixelSize(C4337R.dimen.samsung_minimum_size_for_overlapping_with_cutout_as_default);
        }
        loadSystemProperties();
        this.mImeFocusController = new ImeFocusController(this);
        this.mScrollCaptureRequestTimeout = 2500L;
        this.mOnBackInvokedDispatcher = new WindowOnBackInvokedDispatcher(context);
        this.mSmartClipDispatcherProxy = new SmartClipRemoteRequestDispatcherProxy(context);
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            this.mMotionEventMonitor = new MotionEventMonitor();
        }
    }

    private void updateDesktopMode() {
        int desktopModeEnabled;
        if (this.mDesktopModeManager == null) {
            this.mDesktopModeManager = (SemDesktopModeManager) this.mContext.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        }
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager != null) {
            SemDesktopModeState desktopModeState = semDesktopModeManager.getDesktopModeState();
            boolean z = true;
            if (desktopModeState != null) {
                desktopModeEnabled = desktopModeState.getEnabled();
                this.mDesktopModeStandAlone = desktopModeState.getDisplayType() == 101;
            } else {
                desktopModeEnabled = 2;
                this.mDesktopModeStandAlone = false;
            }
            if (this.mDisplay.getDisplayId() != 2 && (!this.mDesktopModeStandAlone || (desktopModeEnabled != 3 && desktopModeEnabled != 4))) {
                z = false;
            }
            this.mDesktopMode = z;
            if (CoreRune.MT_NEW_DEX) {
                this.mNewDexMode = getConfiguration().isNewDexMode();
            }
        }
    }

    public static void addFirstDrawHandler(Runnable callback) {
        ArrayList<Runnable> arrayList = sFirstDrawHandlers;
        synchronized (arrayList) {
            if (!sFirstDrawComplete) {
                arrayList.add(callback);
            }
        }
    }

    public static void addConfigCallback(ConfigChangedCallback callback) {
        ArrayList<ConfigChangedCallback> arrayList = sConfigCallbacks;
        synchronized (arrayList) {
            arrayList.add(callback);
        }
    }

    public static void removeConfigCallback(ConfigChangedCallback callback) {
        ArrayList<ConfigChangedCallback> arrayList = sConfigCallbacks;
        synchronized (arrayList) {
            arrayList.remove(callback);
        }
    }

    public void setActivityConfigCallback(ActivityConfigCallback callback) {
        this.mActivityConfigCallback = callback;
    }

    public void setOnContentApplyWindowInsetsListener(Window.OnContentApplyWindowInsetsListener listener) {
        this.mAttachInfo.mContentOnApplyWindowInsetsListener = listener;
        if (!this.mFirst) {
            requestFitSystemWindows();
        }
    }

    public void addWindowCallbacks(WindowCallbacks callback) {
        this.mWindowCallbacks.add(callback);
    }

    public void removeWindowCallbacks(WindowCallbacks callback) {
        this.mWindowCallbacks.remove(callback);
    }

    public void reportDrawFinish() {
        CountDownLatch countDownLatch = this.mWindowDrawCountDown;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public void profile() {
        this.mProfile = true;
    }

    private boolean isInTouchMode() {
        IWindowManager windowManager = WindowManagerGlobal.getWindowManagerService();
        if (windowManager != null) {
            try {
                return windowManager.isInTouchMode(getDisplayId());
            } catch (RemoteException e) {
                return false;
            }
        }
        return false;
    }

    public void notifyChildRebuilt() {
        if (this.mView instanceof RootViewSurfaceTaker) {
            SurfaceHolder.Callback2 callback2 = this.mSurfaceHolderCallback;
            if (callback2 != null) {
                this.mSurfaceHolder.removeCallback(callback2);
            }
            SurfaceHolder.Callback2 willYouTakeTheSurface = ((RootViewSurfaceTaker) this.mView).willYouTakeTheSurface();
            this.mSurfaceHolderCallback = willYouTakeTheSurface;
            if (willYouTakeTheSurface != null) {
                TakenSurfaceHolder takenSurfaceHolder = new TakenSurfaceHolder();
                this.mSurfaceHolder = takenSurfaceHolder;
                takenSurfaceHolder.setFormat(0);
                this.mSurfaceHolder.addCallback(this.mSurfaceHolderCallback);
            } else {
                this.mSurfaceHolder = null;
            }
            InputQueue.Callback willYouTakeTheInputQueue = ((RootViewSurfaceTaker) this.mView).willYouTakeTheInputQueue();
            this.mInputQueueCallback = willYouTakeTheInputQueue;
            if (willYouTakeTheInputQueue != null) {
                willYouTakeTheInputQueue.onInputQueueCreated(this.mInputQueue);
            }
        }
        updateLastConfigurationFromResources(getConfiguration());
        reportNextDraw("rebuilt");
        if (this.mStopped) {
            setWindowStopped(false);
        }
    }

    private Configuration getConfiguration() {
        return this.mContext.getResources().getConfiguration();
    }

    private WindowConfiguration getCompatWindowConfiguration() {
        WindowConfiguration winConfig = getConfiguration().windowConfiguration;
        if (this.mInvCompatScale == 1.0f) {
            return winConfig;
        }
        this.mTempWinConfig.setTo(winConfig);
        this.mTempWinConfig.scale(this.mInvCompatScale);
        return this.mTempWinConfig;
    }

    private boolean isSplitScreen() {
        return getConfiguration().windowConfiguration.isSplitScreen();
    }

    private Rect getScaledBounds(WindowConfiguration winConfig) {
        if (this.mTmpScaledBounds == null) {
            this.mTmpScaledBounds = new Rect();
        }
        this.mTmpScaledBounds.set(winConfig.getCompatSandboxBounds());
        float scale = winConfig.getCompatSandboxInvScale();
        if (scale != 1.0f) {
            WindowConfiguration.scaleBounds(scale, this.mTmpScaledBounds);
        }
        return this.mTmpScaledBounds;
    }

    public InsetsSourceControl[] getScaledInsetHintControls(InsetsSourceControl[] controls) {
        if (controls == null) {
            return controls;
        }
        Configuration config = this.mLastReportedMergedConfiguration.getMergedConfiguration();
        if (CompatSandbox.isInsetsHintSandboxingEnabled(config)) {
            WindowConfiguration winConfig = config.windowConfiguration;
            float invScale = winConfig.getCompatSandboxInvScale();
            if (invScale == 1.0f) {
                return controls;
            }
            for (InsetsSourceControl control : controls) {
                if (control != null) {
                    Insets hint = control.getInsetsHint();
                    control.setInsetsHint((int) (hint.left * invScale), (int) (hint.top * invScale), (int) (hint.right * invScale), (int) (hint.bottom * invScale));
                }
            }
        }
        return controls;
    }

    public void setView(View view, WindowManager.LayoutParams attrs, View panelParentView) {
        setView(view, attrs, panelParentView, UserHandle.myUserId());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0483 A[Catch: all -> 0x05eb, TryCatch #3 {all -> 0x05eb, blocks: (B:19:0x0048, B:21:0x004f, B:23:0x0055, B:25:0x005b, B:26:0x0063, B:28:0x0070, B:30:0x007b, B:31:0x008c, B:33:0x0090, B:35:0x0097, B:37:0x009d, B:39:0x00a3, B:40:0x00b5, B:42:0x00c9, B:45:0x00d5, B:47:0x00d9, B:49:0x00de, B:51:0x00e3, B:52:0x00f1, B:54:0x00f5, B:55:0x010d, B:57:0x0113, B:58:0x011b, B:61:0x012e, B:64:0x013c, B:66:0x0140, B:67:0x0148, B:69:0x0155, B:70:0x015b, B:73:0x0166, B:75:0x016e, B:77:0x0176, B:90:0x0203, B:91:0x0206, B:94:0x020f, B:96:0x026c, B:98:0x0288, B:99:0x029a, B:100:0x029d, B:101:0x03f0, B:102:0x0406, B:104:0x02a1, B:105:0x02c1, B:106:0x02c2, B:107:0x02e2, B:108:0x02e3, B:109:0x0303, B:110:0x0304, B:111:0x0324, B:112:0x0325, B:114:0x0327, B:115:0x0355, B:116:0x0356, B:117:0x037e, B:118:0x037f, B:119:0x039f, B:120:0x03a0, B:121:0x03ce, B:122:0x03cf, B:123:0x03ef, B:124:0x0407, B:126:0x0418, B:127:0x041b, B:129:0x041f, B:131:0x042a, B:133:0x042e, B:134:0x043a, B:136:0x0452, B:141:0x045c, B:143:0x0460, B:144:0x047d, B:146:0x0483, B:147:0x049d, B:149:0x04a3, B:152:0x04ad, B:155:0x04b4, B:157:0x04be, B:158:0x04c6, B:160:0x04cc, B:161:0x04d0, B:163:0x0591, B:164:0x05a1, B:166:0x05ab, B:168:0x05e2, B:170:0x05af, B:180:0x05d8, B:181:0x05dc, B:188:0x013a, B:192:0x05e9, B:79:0x017b, B:81:0x01b3, B:82:0x01bd, B:85:0x01d9, B:87:0x01dd, B:88:0x01f2, B:185:0x05ba, B:186:0x05d5), top: B:18:0x0048, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setView(View view, WindowManager.LayoutParams attrs, View panelParentView, int userId) {
        WindowManager.LayoutParams layoutParams;
        Throwable th;
        Rect attachedFrame;
        boolean isGameApp;
        PendingInsetsController pendingInsetsController;
        synchronized (this) {
            try {
                if (this.mView == null) {
                    this.mView = view;
                    this.mViewLayoutDirectionInitial = view.getRawLayoutDirection();
                    this.mFallbackEventHandler.setView(view);
                    layoutParams = attrs;
                    try {
                        this.mWindowAttributes.copyFrom(layoutParams);
                        if (this.mWindowAttributes.packageName == null) {
                            this.mWindowAttributes.packageName = this.mBasePackageName;
                        }
                        this.mWindowAttributes.privateFlags |= 33554432;
                        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && this.mWindowAttributes.type == 2632) {
                            this.mWindowAttributes.inputFeatures |= 1;
                        }
                        WindowManager.LayoutParams attrs2 = this.mWindowAttributes;
                        try {
                            setTag();
                            if (DEBUG_KEEP_SCREEN_ON && (this.mClientWindowLayoutFlags & 128) != 0 && (attrs2.flags & 128) == 0) {
                                Slog.m113d(this.mTag, "setView: FLAG_KEEP_SCREEN_ON changed from true to false!");
                            }
                            this.mClientWindowLayoutFlags = attrs2.flags;
                            setAccessibilityFocus(null, null);
                            if (view instanceof RootViewSurfaceTaker) {
                                SurfaceHolder.Callback2 willYouTakeTheSurface = ((RootViewSurfaceTaker) view).willYouTakeTheSurface();
                                this.mSurfaceHolderCallback = willYouTakeTheSurface;
                                if (willYouTakeTheSurface != null) {
                                    TakenSurfaceHolder takenSurfaceHolder = new TakenSurfaceHolder();
                                    this.mSurfaceHolder = takenSurfaceHolder;
                                    takenSurfaceHolder.setFormat(0);
                                    this.mSurfaceHolder.addCallback(this.mSurfaceHolderCallback);
                                }
                            }
                            if (!attrs2.hasManualSurfaceInsets) {
                                attrs2.setSurfaceInsets(view, false, true);
                                if (CoreRune.MW_CAPTION_SHELL && (attrs2.multiwindowFlags & 8) != 0 && (attrs2.gravity & 49) != 0) {
                                    attrs2.f573x -= attrs2.surfaceInsets.left;
                                    attrs2.f574y -= attrs2.surfaceInsets.top;
                                }
                            }
                            CompatibilityInfo compatibilityInfo = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
                            this.mTranslator = compatibilityInfo.getTranslator();
                            if (this.mSurfaceHolder == null) {
                                enableHardwareAcceleration(attrs2);
                                boolean useMTRenderer = this.mAttachInfo.mThreadedRenderer != null;
                                if (this.mUseMTRenderer != useMTRenderer) {
                                    endDragResizing();
                                    this.mUseMTRenderer = useMTRenderer;
                                }
                            }
                            boolean restore = false;
                            CompatibilityInfo.Translator translator = this.mTranslator;
                            if (translator != null) {
                                this.mSurface.setCompatibilityTranslator(translator);
                                restore = true;
                                attrs2.backup();
                                this.mTranslator.translateWindowLayout(attrs2);
                            }
                            boolean z = DEBUG_LAYOUT;
                            if (z) {
                                Log.m94d(this.mTag, "WindowLayout in setView:" + attrs2);
                            }
                            if (!compatibilityInfo.supportsScreen()) {
                                attrs2.privateFlags |= 128;
                                this.mLastInCompatMode = true;
                            }
                            this.mSoftInputMode = attrs2.softInputMode;
                            this.mWindowAttributesChanged = true;
                            this.mAttachInfo.mRootView = view;
                            this.mAttachInfo.mScalingRequired = this.mTranslator != null;
                            View.AttachInfo attachInfo = this.mAttachInfo;
                            CompatibilityInfo.Translator translator2 = this.mTranslator;
                            attachInfo.mApplicationScale = translator2 == null ? 1.0f : translator2.applicationScale;
                            if (panelParentView != null) {
                                this.mAttachInfo.mPanelParentWindowToken = panelParentView.getApplicationWindowToken();
                            }
                            this.mAdded = true;
                            requestLayout();
                            InputChannel inputChannel = null;
                            if ((this.mWindowAttributes.inputFeatures & 1) == 0) {
                                inputChannel = new InputChannel();
                            }
                            this.mForceDecorViewVisibility = (this.mWindowAttributes.privateFlags & 8192) != 0;
                            KeyEvent.Callback callback = this.mView;
                            if ((callback instanceof RootViewSurfaceTaker) && (pendingInsetsController = ((RootViewSurfaceTaker) callback).providePendingInsetsController()) != null) {
                                pendingInsetsController.replayAndAttach(this.mInsetsController);
                            }
                            try {
                                try {
                                    this.mOrigWindowType = this.mWindowAttributes.type;
                                    this.mAttachInfo.mRecomputeGlobalAttributes = true;
                                    collectViewAttributes();
                                    adjustLayoutParamsForCompatibility(this.mWindowAttributes);
                                    controlInsetsForCompatibility(this.mWindowAttributes);
                                    Rect attachedFrame2 = new Rect();
                                    float[] compatScale = {1.0f};
                                    int res = this.mWindowSession.addToDisplayAsUser(this.mWindow, this.mWindowAttributes, getHostVisibility(), this.mDisplay.getDisplayId(), userId, this.mInsetsController.getRequestedVisibleTypes(), inputChannel == null ? new InputChannel() : inputChannel, this.mTempInsets, this.mTempControls, attachedFrame2, compatScale);
                                    if (attachedFrame2.isValid()) {
                                        attachedFrame = attachedFrame2;
                                    } else {
                                        attachedFrame = null;
                                    }
                                    CompatibilityInfo.Translator translator3 = this.mTranslator;
                                    if (translator3 != null) {
                                        translator3.translateInsetsStateInScreenToAppWindow(this.mTempInsets);
                                        this.mTranslator.translateSourceControlsInScreenToAppWindow(this.mTempControls.get());
                                        this.mTranslator.translateRectInScreenToAppWindow(attachedFrame);
                                    }
                                    this.mTmpFrames.attachedFrame = attachedFrame;
                                    this.mTmpFrames.compatScale = compatScale[0];
                                    this.mInvCompatScale = 1.0f / compatScale[0];
                                    if (restore) {
                                        attrs2.restore();
                                    }
                                    this.mAttachInfo.mAlwaysConsumeSystemBars = (res & 4) != 0;
                                    this.mPendingAlwaysConsumeSystemBars = this.mAttachInfo.mAlwaysConsumeSystemBars;
                                    this.mInsetsController.onStateChanged(this.mTempInsets);
                                    this.mInsetsController.onControlsChanged(this.mTempControls.get());
                                    InsetsState state = this.mInsetsController.getState();
                                    Rect displayCutoutSafe = this.mTempRect;
                                    state.getDisplayCutoutSafe(displayCutoutSafe);
                                    WindowConfiguration winConfig = getCompatWindowConfiguration();
                                    this.mWindowLayout.computeFrames(this.mWindowAttributes, state, displayCutoutSafe, winConfig.getBounds(), winConfig.getWindowingMode(), -1, -1, this.mInsetsController.getRequestedVisibleTypes(), 1.0f, this.mTmpFrames, winConfig.getStageType());
                                    setFrame(this.mTmpFrames.frame, true);
                                    registerBackCallbackOnWindow();
                                    if (z) {
                                        Log.m100v(this.mTag, "Added window " + this.mWindow);
                                    }
                                    if (res < 0) {
                                        this.mAttachInfo.mRootView = null;
                                        this.mAdded = false;
                                        this.mFallbackEventHandler.setView(null);
                                        unscheduleTraversals();
                                        setAccessibilityFocus(null, null);
                                        switch (res) {
                                            case -11:
                                                throw new WindowManager.BadTokenException("Unable to add Window " + this.mWindow + " -- requested userId is not valid");
                                            case -10:
                                                throw new WindowManager.InvalidDisplayException("Unable to add window " + this.mWindow + " -- the specified window type " + this.mWindowAttributes.type + " is not valid");
                                            case -9:
                                                throw new WindowManager.InvalidDisplayException("Unable to add window " + this.mWindow + " -- the specified display can not be found");
                                            case -8:
                                                throw new WindowManager.BadTokenException("Unable to add window " + this.mWindow + " -- permission denied for window type " + this.mWindowAttributes.type);
                                            case -7:
                                                throw new WindowManager.BadTokenException("Unable to add window " + this.mWindow + " -- another window of type " + this.mWindowAttributes.type + " already exists");
                                            case -6:
                                                return;
                                            case -5:
                                                throw new WindowManager.BadTokenException("Unable to add window -- window " + this.mWindow + " has already been added");
                                            case -4:
                                                throw new WindowManager.BadTokenException("Unable to add window -- app for token " + attrs2.token + " is exiting");
                                            case -3:
                                                throw new WindowManager.BadTokenException("Unable to add window -- token " + attrs2.token + " is not for an application");
                                            case -2:
                                            case -1:
                                                throw new WindowManager.BadTokenException("Unable to add window -- token " + attrs2.token + " is not valid; is your activity running?");
                                            default:
                                                throw new RuntimeException("Unable to add window -- unknown error code " + res);
                                        }
                                    }
                                    registerListeners();
                                    this.mAttachInfo.mDisplayState = this.mDisplay.getState();
                                    if ((res & 8) != 0) {
                                        this.mUseBLASTAdapter = true;
                                    }
                                    if (view instanceof RootViewSurfaceTaker) {
                                        this.mInputQueueCallback = ((RootViewSurfaceTaker) view).willYouTakeTheInputQueue();
                                    }
                                    if (inputChannel != null) {
                                        if (this.mInputQueueCallback != null) {
                                            InputQueue inputQueue = new InputQueue();
                                            this.mInputQueue = inputQueue;
                                            this.mInputQueueCallback.onInputQueueCreated(inputQueue);
                                        }
                                        this.mInputEventReceiver = new WindowInputEventReceiver(inputChannel, Looper.myLooper());
                                        ApplicationInfo ai = this.mContext.getApplicationInfo();
                                        if ((ai.flags & 33554432) == 0 && ai.category != 0) {
                                            isGameApp = false;
                                            if (isGameApp && this.mInputEventReceiver != null) {
                                                float xdpi = this.mContext.getResources().getDisplayMetrics().xdpi;
                                                float ydpi = this.mContext.getResources().getDisplayMetrics().ydpi;
                                                this.mInputEventReceiver.setImprovementEvent(isGameApp, xdpi, ydpi);
                                            }
                                            if (this.mAttachInfo.mThreadedRenderer != null) {
                                                InputMetricsListener listener = new InputMetricsListener();
                                                this.mHardwareRendererObserver = new HardwareRendererObserver(listener, listener.data, this.mHandler, true);
                                                this.mAttachInfo.mThreadedRenderer.addObserver(this.mHardwareRendererObserver);
                                            }
                                            this.mUnbufferedInputSource = this.mView.mUnbufferedInputSource;
                                        }
                                        isGameApp = true;
                                        if (isGameApp) {
                                            float xdpi2 = this.mContext.getResources().getDisplayMetrics().xdpi;
                                            float ydpi2 = this.mContext.getResources().getDisplayMetrics().ydpi;
                                            this.mInputEventReceiver.setImprovementEvent(isGameApp, xdpi2, ydpi2);
                                        }
                                        if (this.mAttachInfo.mThreadedRenderer != null) {
                                        }
                                        this.mUnbufferedInputSource = this.mView.mUnbufferedInputSource;
                                    }
                                    view.assignParent(this);
                                    this.mAddedTouchMode = (res & 1) != 0;
                                    this.mAppVisible = (res & 2) != 0;
                                    if (this.mAccessibilityManager.isEnabled()) {
                                        this.mAccessibilityInteractionConnectionManager.ensureConnection();
                                        setAccessibilityWindowAttributesIfNeeded();
                                    }
                                    if (view.getImportantForAccessibility() == 0) {
                                        view.setImportantForAccessibility(1);
                                    }
                                    Log.m98i(this.mTag, "setView = " + view.getClass().getName() + '@' + Integer.toHexString(view.hashCode()) + " TM=" + this.mAddedTouchMode);
                                    CharSequence counterSuffix = attrs2.getTitle();
                                    SyntheticInputStage syntheticInputStage = new SyntheticInputStage();
                                    this.mSyntheticInputStage = syntheticInputStage;
                                    InputStage viewPostImeStage = new ViewPostImeInputStage(syntheticInputStage);
                                    InputStage nativePostImeStage = new NativePostImeInputStage(viewPostImeStage, "aq:native-post-ime:" + ((Object) counterSuffix));
                                    InputStage earlyPostImeStage = new EarlyPostImeInputStage(nativePostImeStage);
                                    InputStage imeStage = new ImeInputStage(earlyPostImeStage, "aq:ime:" + ((Object) counterSuffix));
                                    InputStage viewPreImeStage = new ViewPreImeInputStage(imeStage);
                                    InputStage nativePreImeStage = new NativePreImeInputStage(viewPreImeStage, "aq:native-pre-ime:" + ((Object) counterSuffix));
                                    this.mFirstInputStage = nativePreImeStage;
                                    this.mFirstPostImeInputStage = earlyPostImeStage;
                                    this.mPendingInputEventQueueLengthCounterName = "aq:pending:" + ((Object) counterSuffix);
                                    if (CoreRune.BIXBY_TOUCH) {
                                        this.mSemPressGestureDetector = new SemPressGestureDetector(this.mContext, this.mView);
                                    }
                                    updateCutoutRemoveNeeded(res);
                                    updateCompatTranslator(res);
                                    if (!this.mRemoved || !this.mAppVisible) {
                                        AnimationHandler.requestAnimatorsEnabled(this.mAppVisible, this);
                                    }
                                } catch (RemoteException | RuntimeException e) {
                                    this.mAdded = false;
                                    this.mView = null;
                                    this.mAttachInfo.mRootView = null;
                                    this.mFallbackEventHandler.setView(null);
                                    unscheduleTraversals();
                                    setAccessibilityFocus(null, null);
                                    throw new RuntimeException("Adding window failed", e);
                                }
                            } finally {
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        th = th;
                        throw th;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                layoutParams = attrs;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAccessibilityWindowAttributesIfNeeded() {
        boolean registered = this.mAttachInfo.mAccessibilityWindowId != -1;
        if (registered) {
            AccessibilityWindowAttributes attributes = new AccessibilityWindowAttributes(this.mWindowAttributes, this.mContext.getResources().getConfiguration().getLocales());
            if (!attributes.equals(this.mAccessibilityWindowAttributes)) {
                this.mAccessibilityWindowAttributes = attributes;
                this.mAccessibilityManager.setAccessibilityWindowAttributes(getDisplayId(), this.mAttachInfo.mAccessibilityWindowId, attributes);
            }
        }
    }

    private void registerListeners() {
        this.mAccessibilityManager.addAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager, this.mHandler);
        this.mAccessibilityManager.addHighTextContrastStateChangeListener(this.mHighContrastTextManager, this.mHandler);
        DisplayManagerGlobal.getInstance().registerDisplayListener(this.mDisplayListener, this.mHandler, 7L);
        if (this.mAttachInfo != null && this.mDisplay.getState() != this.mAttachInfo.mDisplayState && this.mAttachInfo.mDisplay != null && this.mAttachInfo.mDisplay.getDisplayId() == this.mDisplay.getDisplayId()) {
            this.mAttachInfo.mDisplayState = this.mDisplay.getState();
            Log.m98i(this.mTag, "synced displayState. AttachInfo displayState=" + this.mAttachInfo.mDisplayState);
        }
    }

    private void unregisterListeners() {
        this.mAccessibilityManager.removeAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager);
        this.mAccessibilityManager.removeHighTextContrastStateChangeListener(this.mHighContrastTextManager);
        DisplayManagerGlobal.getInstance().unregisterDisplayListener(this.mDisplayListener);
    }

    private void setTag() {
        String[] split = this.mWindowAttributes.getTitle().toString().split("\\.");
        if (split.length > 0) {
            String str = "ViewRootImpl@" + Integer.toHexString(this.mWindow.hashCode()) + NavigationBarInflaterView.SIZE_MOD_START + split[split.length - 1] + NavigationBarInflaterView.SIZE_MOD_END;
            this.mTag = str;
            WindowOnBackInvokedDispatcher windowOnBackInvokedDispatcher = this.mOnBackInvokedDispatcher;
            if (windowOnBackInvokedDispatcher != null) {
                windowOnBackInvokedDispatcher.setOwnerTag(str);
            }
        }
    }

    public String getTag() {
        return this.mTag;
    }

    public static void setSafeScheduleTraversals(boolean safe) {
        sSafeScheduleTraversals = safe;
    }

    public int getWindowFlags() {
        return this.mWindowAttributes.flags;
    }

    public int getDisplayId() {
        return this.mDisplay.getDisplayId();
    }

    public CharSequence getTitle() {
        return this.mWindowAttributes.getTitle();
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    void destroyHardwareResources() {
        ThreadedRenderer renderer = this.mAttachInfo.mThreadedRenderer;
        if (renderer != null) {
            if (Looper.myLooper() != this.mAttachInfo.mHandler.getLooper()) {
                this.mAttachInfo.mHandler.postAtFrontOfQueue(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewRootImpl.this.destroyHardwareResources();
                    }
                });
                return;
            }
            Log.m98i(this.mTag, "destroyHardwareResources: Callers=" + Debug.getCallers(10));
            renderer.destroyHardwareResources(this.mView);
            renderer.destroy();
        }
    }

    void resetSoftwareCaches(View view) {
        if (view == null) {
            return;
        }
        view.destroyDrawingCache();
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                resetSoftwareCaches(group.getChildAt(i));
            }
        }
    }

    public void detachFunctor(long functor) {
    }

    public static void invokeFunctor(long functor, boolean waitForCompletion) {
    }

    public void registerAnimatingRenderNode(RenderNode animator) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerAnimatingRenderNode(animator);
            return;
        }
        if (this.mAttachInfo.mPendingAnimatingRenderNodes == null) {
            this.mAttachInfo.mPendingAnimatingRenderNodes = new ArrayList();
        }
        this.mAttachInfo.mPendingAnimatingRenderNodes.add(animator);
    }

    public void registerVectorDrawableAnimator(NativeVectorDrawableAnimator animator) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerVectorDrawableAnimator(animator);
        }
    }

    public void registerRtFrameCallback(final HardwareRenderer.FrameDrawingCallback callback) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerRtFrameCallback(new HardwareRenderer.FrameDrawingCallback() { // from class: android.view.ViewRootImpl.2
                @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                public void onFrameDraw(long frame) {
                }

                @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                public HardwareRenderer.FrameCommitCallback onFrameDraw(int syncResult, long frame) {
                    try {
                        return callback.onFrameDraw(syncResult, frame);
                    } catch (Exception e) {
                        Log.m97e(ViewRootImpl.TAG, "Exception while executing onFrameDraw", e);
                        return null;
                    }
                }
            });
        }
    }

    private void enableHardwareAcceleration(WindowManager.LayoutParams attrs) {
        this.mAttachInfo.mHardwareAccelerated = false;
        this.mAttachInfo.mHardwareAccelerationRequested = false;
        if (this.mTranslator != null) {
            return;
        }
        boolean hardwareAccelerated = (attrs.flags & 16777216) != 0;
        if (!hardwareAccelerated || CoreRune.FW_VIEW_DEBUG_DISABLE_HWRENDERING) {
            return;
        }
        boolean forceHwAccelerated = (attrs.privateFlags & 2) != 0;
        if (ThreadedRenderer.sRendererEnabled || forceHwAccelerated) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.destroy();
            }
            Rect insets = attrs.surfaceInsets;
            boolean hasSurfaceInsets = (insets.left == 0 && insets.right == 0 && insets.top == 0 && insets.bottom == 0) ? false : true;
            boolean translucent = attrs.format != -1 || hasSurfaceInsets;
            ThreadedRenderer renderer = ThreadedRenderer.create(this.mContext, translucent, attrs.getTitle().toString());
            this.mAttachInfo.mThreadedRenderer = renderer;
            renderer.setSurfaceControl(this.mSurfaceControl, this.mBlastBufferQueue);
            updateColorModeIfNeeded(attrs.getColorMode());
            updateRenderHdrSdrRatio();
            updateForceDarkMode();
            if (ViewRune.COMMON_IS_PRODUCT_DEV) {
                Log.m94d(this.mTag, "ThreadedRenderer.create() translucent=" + translucent);
            }
            this.mAttachInfo.mHardwareAccelerated = true;
            this.mAttachInfo.mHardwareAccelerationRequested = true;
            HardwareRendererObserver hardwareRendererObserver = this.mHardwareRendererObserver;
            if (hardwareRendererObserver != null) {
                renderer.addObserver(hardwareRendererObserver);
            }
        }
    }

    private int getNightMode() {
        return getConfiguration().uiMode & 48;
    }

    private void updateForceDarkMode() {
        if (this.mAttachInfo.mThreadedRenderer == null) {
            return;
        }
        boolean useAutoDark = getNightMode() == 32;
        if (useAutoDark) {
            boolean forceDarkAllowedDefault = SystemProperties.getBoolean(ThreadedRenderer.DEBUG_FORCE_DARK, false);
            TypedArray a = this.mContext.obtainStyledAttributes(C4337R.styleable.Theme);
            useAutoDark = a.getBoolean(279, true) && a.getBoolean(278, forceDarkAllowedDefault);
            a.recycle();
        }
        if (this.mAttachInfo.mThreadedRenderer.setForceDark(useAutoDark)) {
            invalidateWorld(this.mView);
        }
    }

    public View getView() {
        return this.mView;
    }

    final WindowLeaked getLocation() {
        return this.mLocation;
    }

    public void setLayoutParams(WindowManager.LayoutParams attrs, boolean newView) {
        int oldSoftInputMode;
        int oldInsetLeft;
        synchronized (this) {
            int oldInsetLeft2 = this.mWindowAttributes.surfaceInsets.left;
            int oldInsetTop = this.mWindowAttributes.surfaceInsets.top;
            int oldInsetRight = this.mWindowAttributes.surfaceInsets.right;
            int oldInsetBottom = this.mWindowAttributes.surfaceInsets.bottom;
            int oldSoftInputMode2 = this.mWindowAttributes.softInputMode;
            boolean oldHasManualSurfaceInsets = this.mWindowAttributes.hasManualSurfaceInsets;
            if (DEBUG_KEEP_SCREEN_ON && (this.mClientWindowLayoutFlags & 128) != 0 && (attrs.flags & 128) == 0) {
                Slog.m113d(this.mTag, "setLayoutParams: FLAG_KEEP_SCREEN_ON from true to false!");
            }
            if ((this.mWindowAttributes.flags & 512) != 0 && (attrs.flags & 512) == 0) {
                Log.m98i(this.mTag, "setLayoutParams: set mApplyInsetsRequested = true");
                this.mApplyInsetsRequested = true;
            }
            this.mClientWindowLayoutFlags = attrs.flags;
            int compatibleWindowFlag = this.mWindowAttributes.privateFlags & 128;
            int systemUiVisibility = this.mWindowAttributes.systemUiVisibility;
            int subtreeSystemUiVisibility = this.mWindowAttributes.subtreeSystemUiVisibility;
            int appearance = this.mWindowAttributes.insetsFlags.appearance;
            int behavior = this.mWindowAttributes.insetsFlags.behavior;
            int appearanceAndBehaviorPrivateFlags = this.mWindowAttributes.privateFlags & Enums.AUDIO_FORMAT_DTS_HD;
            int changes = this.mWindowAttributes.copyFrom(attrs);
            if ((changes & 524288) == 0) {
                oldSoftInputMode = oldSoftInputMode2;
            } else {
                oldSoftInputMode = oldSoftInputMode2;
                this.mAttachInfo.mRecomputeGlobalAttributes = true;
            }
            if ((changes & 1) != 0) {
                this.mAttachInfo.mNeedsUpdateLightCenter = true;
            }
            if (this.mWindowAttributes.packageName == null) {
                this.mWindowAttributes.packageName = this.mBasePackageName;
            }
            this.mWindowAttributes.systemUiVisibility = systemUiVisibility;
            this.mWindowAttributes.subtreeSystemUiVisibility = subtreeSystemUiVisibility;
            this.mWindowAttributes.insetsFlags.appearance = appearance;
            this.mWindowAttributes.insetsFlags.behavior = behavior;
            this.mWindowAttributes.privateFlags |= compatibleWindowFlag | appearanceAndBehaviorPrivateFlags | 33554432;
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && this.mWindowAttributes.type == 2632) {
                this.mWindowAttributes.inputFeatures |= 1;
            }
            if (this.mWindowAttributes.preservePreviousSurfaceInsets) {
                this.mWindowAttributes.surfaceInsets.set(oldInsetLeft2, oldInsetTop, oldInsetRight, oldInsetBottom);
                this.mWindowAttributes.hasManualSurfaceInsets = oldHasManualSurfaceInsets;
            } else if (this.mWindowAttributes.surfaceInsets.left != oldInsetLeft2 || this.mWindowAttributes.surfaceInsets.top != oldInsetTop || this.mWindowAttributes.surfaceInsets.right != oldInsetRight || this.mWindowAttributes.surfaceInsets.bottom != oldInsetBottom) {
                this.mNeedsRendererSetup = true;
            }
            applyKeepScreenOnFlag(this.mWindowAttributes);
            if (newView) {
                this.mSoftInputMode = attrs.softInputMode;
                requestLayout();
            }
            if ((attrs.softInputMode & 240) == 0) {
                WindowManager.LayoutParams layoutParams = this.mWindowAttributes;
                oldInsetLeft = oldSoftInputMode;
                layoutParams.softInputMode = (oldInsetLeft & 240) | (layoutParams.softInputMode & (-241));
            } else {
                oldInsetLeft = oldSoftInputMode;
            }
            if (this.mWindowAttributes.softInputMode != oldInsetLeft) {
                requestFitSystemWindows();
            }
            if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
                Log.m98i(this.mTag, "Traversal, [1] mView=" + this.mView);
            }
            this.mWindowAttributesChanged = true;
            scheduleTraversals();
            setAccessibilityWindowAttributesIfNeeded();
        }
    }

    private boolean isImpossibleRenderer() {
        return this.mSemEarlyAppVisibilityChanged && this.mAppVisible && this.mStopped && !this.mSemEarlyAppVisibility;
    }

    void handleAppVisibility(boolean visible) {
        this.mSemEarlyAppVisibilityChanged = false;
        Log.m98i(this.mTag, "handleAppVisibility mAppVisible = " + this.mAppVisible + " visible = " + visible);
        if (this.mAppVisible != visible) {
            boolean previousVisible = getHostVisibility() == 0;
            this.mAppVisible = visible;
            boolean currentVisible = getHostVisibility() == 0;
            boolean z = DEBUG_TRAVERSAL;
            if (z && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
                Log.m98i(this.mTag, "Traversal, [2] mView=" + this.mView + " visible=" + visible + " previousVisible=" + previousVisible + " currentVisible=" + currentVisible);
            }
            if (previousVisible != currentVisible) {
                this.mAppVisibilityChanged = true;
                scheduleTraversals();
            }
            if (!this.mRemoved || !this.mAppVisible || !this.mIsDetached) {
                AnimationHandler.requestAnimatorsEnabled(this.mAppVisible, this);
                return;
            }
            Log.m100v(this.mTag, "handleAppVisibility() enabling visibility when removed");
            if (z && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
                Log.m98i(this.mTag, "Traversal, [2] mView=" + this.mView + " mAppVisible=" + this.mAppVisible + " visible=" + visible);
            }
        }
    }

    void handleGetNewSurface() {
        this.mNewSurfaceNeeded = true;
        this.mFullRedrawNeeded = true;
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [3]  mView=" + this.mView);
        }
        scheduleTraversals();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleResized(int msg, SomeArgs args) {
        boolean z;
        Rect attachedFrame;
        boolean z2;
        if (!this.mAdded) {
            return;
        }
        ClientWindowFrames frames = (ClientWindowFrames) args.arg1;
        MergedConfiguration mergedConfiguration = (MergedConfiguration) args.arg2;
        CompatibilityInfo.applyOverrideScaleIfNeeded(mergedConfiguration);
        boolean forceNextWindowRelayout = args.argi1 != 0;
        int displayId = args.argi3;
        boolean dragResizing = args.argi5 != 0;
        Rect frame = frames.frame;
        Rect displayFrame = frames.displayFrame;
        Rect attachedFrame2 = frames.attachedFrame;
        CompatibilityInfo.Translator translator = this.mTranslator;
        if (translator != null) {
            translator.translateRectInScreenToAppWindow(frame);
            this.mTranslator.translateRectInScreenToAppWindow(displayFrame);
            this.mTranslator.translateRectInScreenToAppWindow(attachedFrame2);
        }
        CompatTranslator translator2 = getCompatTranslator();
        if (translator2 != null) {
            if (CoreRune.FW_BOUNDS_COMPAT_TRANSLATOR_AS_BOUNDS) {
                updatePositionInBounds(translator2, mergedConfiguration.getOverrideConfiguration());
            }
            translator2.savePositionInScreen(frames.frame.left, frames.frame.top);
            translator2.translateToWindow(frames.frame);
        }
        float compatScale = frames.compatScale;
        boolean frameChanged = !this.mWinFrame.equals(frame);
        boolean configChanged = !this.mLastReportedMergedConfiguration.equals(mergedConfiguration);
        boolean attachedFrameChanged = LOCAL_LAYOUT && !Objects.equals(this.mTmpFrames.attachedFrame, attachedFrame2);
        boolean displayChanged = this.mDisplay.getDisplayId() != displayId;
        boolean compatScaleChanged = this.mTmpFrames.compatScale != compatScale;
        Log.m98i(this.mTag, "handleResized, msg = " + msg + " frames=" + frames + " forceNextWindowRelayout=" + forceNextWindowRelayout + " displayId=" + displayId + " dragResizing=" + dragResizing + " compatScale=" + compatScale + " frameChanged=" + frameChanged + " attachedFrameChanged=" + attachedFrameChanged + " configChanged=" + configChanged + " displayChanged=" + displayChanged + " compatScaleChanged=" + compatScaleChanged);
        if (msg == 4 && !frameChanged && !configChanged && !attachedFrameChanged && !displayChanged && !forceNextWindowRelayout && !compatScaleChanged) {
            return;
        }
        this.mPendingDragResizing = dragResizing;
        this.mTmpFrames.compatScale = compatScale;
        this.mInvCompatScale = 1.0f / compatScale;
        if (configChanged) {
            z = false;
            performConfigurationChange(mergedConfiguration, false, displayChanged ? displayId : -1);
        } else {
            z = false;
            if (displayChanged) {
                onMovedToDisplay(displayId, this.mLastConfigurationFromResources);
            }
        }
        setFrame(frame, z);
        this.mTmpFrames.displayFrame.set(displayFrame);
        if (this.mTmpFrames.attachedFrame == null || attachedFrame2 == null) {
            attachedFrame = attachedFrame2;
        } else {
            attachedFrame = attachedFrame2;
            this.mTmpFrames.attachedFrame.set(attachedFrame);
        }
        if (this.mDragResizing && this.mUseMTRenderer) {
            boolean fullscreen = frame.equals(this.mPendingBackDropFrame);
            z2 = true;
            int i = this.mWindowCallbacks.size() - 1;
            while (i >= 0) {
                this.mWindowCallbacks.get(i).onWindowSizeIsChanging(this.mPendingBackDropFrame, fullscreen, this.mAttachInfo.mVisibleInsets, this.mAttachInfo.mStableInsets);
                i--;
                frames = frames;
                mergedConfiguration = mergedConfiguration;
                displayChanged = displayChanged;
                attachedFrameChanged = attachedFrameChanged;
            }
        } else {
            z2 = true;
        }
        this.mForceNextWindowRelayout |= forceNextWindowRelayout;
        this.mPendingAlwaysConsumeSystemBars = args.argi2 != 0 ? z2 : false;
        int i2 = args.argi4;
        int i3 = this.mSyncSeqId;
        if (i2 > i3) {
            i3 = args.argi4;
        }
        this.mSyncSeqId = i3;
        Log.m98i(this.mTag, "handleResized mSyncSeqId = " + this.mSyncSeqId);
        if (msg == 5) {
            reportNextDraw("resized");
        }
        View view = this.mView;
        if (view != null && (frameChanged || configChanged)) {
            forceLayout(view);
        }
        requestLayout();
    }

    public void onMovedToDisplay(int displayId, Configuration config) {
        View view;
        if (this.mDisplay.getDisplayId() != displayId && (view = this.mView) != null) {
            updateInternalDisplay(displayId, view.getResources());
            this.mImeFocusController.onMovedToDisplay();
            this.mAttachInfo.mDisplayState = this.mDisplay.getState();
            updateDesktopMode();
            this.mView.dispatchMovedToDisplay(this.mDisplay, config);
        }
    }

    private void updateInternalDisplay(int displayId, Resources resources) {
        Display display;
        Display display2;
        Display preferredDisplay = ResourcesManager.getInstance().getAdjustedDisplay(displayId, resources);
        Consumer<Display> consumer = this.mHdrSdrRatioChangedListener;
        if (consumer != null && (display2 = this.mDisplay) != null) {
            display2.unregisterHdrSdrRatioChangedListener(consumer);
        }
        if (preferredDisplay == null) {
            Slog.m121w(TAG, "Cannot get desired display with Id: " + displayId);
            this.mDisplay = ResourcesManager.getInstance().getAdjustedDisplay(0, resources);
        } else {
            this.mDisplay = preferredDisplay;
        }
        if (this.mHdrSdrRatioChangedListener != null && (display = this.mDisplay) != null && display.isHdrSdrRatioAvailable()) {
            this.mDisplay.registerHdrSdrRatioChangedListener(this.mExecutor, this.mHdrSdrRatioChangedListener);
        }
        this.mContext.updateDisplay(this.mDisplay.getDisplayId());
    }

    public void deferScheduleTraversals() {
        this.mScheduleTraversalDeferCount++;
    }

    public void resumeScheduleTraversals() {
        int i = this.mScheduleTraversalDeferCount - 1;
        this.mScheduleTraversalDeferCount = i;
        if (i == 0) {
            scheduleTraversals();
        }
    }

    public PokeDrawLockController getPokeDrawLockController() {
        return this.mPokeDrawLockController;
    }

    void pokeDrawLockIfNeeded() {
        if (!this.mPokeDrawLockController.consumeRequestedToAllowPokeDrawLock(false) || !Display.isDozeState(this.mAttachInfo.mDisplayState)) {
            return;
        }
        if ((this.mWindowAttributes.type == 1 || this.mCanAllowPokeDrawLock) && this.mAdded && this.mTraversalScheduled) {
            if (this.mAttachInfo.mHasWindowFocus || !this.mPokeDrawLockController.shouldSkipPokeDrawLockIfNeeded(this.mReportNextDraw)) {
                try {
                    this.mWindowSession.pokeDrawLock(this.mWindow);
                } catch (RemoteException e) {
                }
            }
        }
    }

    @Override // android.view.ViewParent
    public void requestFitSystemWindows() {
        checkThread();
        this.mApplyInsetsRequested = true;
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [5] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    void notifyInsetsChanged() {
        InsetsSource imeSource = this.mInsetsController.getState().peekSource(InsetsSource.ID_IME);
        WindowConfiguration winConfig = getConfiguration().windowConfiguration;
        boolean isBottomSplit = winConfig.isSplitScreen() && (winConfig.getStagePosition() & 64) != 0;
        if ((winConfig.getWindowingMode() == 5 || isBottomSplit) && imeSource != null && imeSource.isVisible() && !imeSource.getFrame().isEmpty() && imeSource.getFrame().top < this.mWinFrame.top) {
            return;
        }
        this.mApplyInsetsRequested = true;
        requestLayout();
        if (View.sForceLayoutWhenInsetsChanged && this.mView != null && (this.mWindowAttributes.softInputMode & 240) == 16) {
            forceLayout(this.mView);
        }
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [6] mView=" + this.mView + " mIsInTraversal=" + this.mIsInTraversal);
        }
        if (!this.mIsInTraversal) {
            scheduleTraversals();
        }
    }

    @Override // android.view.ViewParent
    public void requestLayout() {
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [7] mView=" + this.mView + " mHandlingLayoutInLayoutRequest=" + this.mHandlingLayoutInLayoutRequest);
        }
        if (!this.mHandlingLayoutInLayoutRequest) {
            checkThread();
            this.mLayoutRequested = true;
            scheduleTraversals();
        }
    }

    @Override // android.view.ViewParent
    public boolean isLayoutRequested() {
        return this.mLayoutRequested;
    }

    @Override // android.view.ViewParent
    public void onDescendantInvalidated(View child, View descendant) {
        if ((descendant.mPrivateFlags & 64) != 0) {
            this.mIsAnimating = true;
        }
        invalidate();
    }

    void invalidate() {
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [8] mView=" + this.mView + " mWillDrawSoon=" + this.mWillDrawSoon);
        }
        this.mDirty.set(0, 0, this.mWidth, this.mHeight);
        if (!this.mWillDrawSoon) {
            scheduleTraversals();
        }
    }

    void invalidateWorld(View view) {
        view.invalidate();
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            for (int i = 0; i < parent.getChildCount(); i++) {
                invalidateWorld(parent.getChildAt(i));
            }
        }
    }

    @Override // android.view.ViewParent
    public void invalidateChild(View child, Rect dirty) {
        invalidateChildInParent(null, dirty);
    }

    @Override // android.view.ViewParent
    public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
        checkThread();
        if (DEBUG_DRAW) {
            Log.m100v(this.mTag, "Invalidate child: " + dirty);
        }
        if (dirty == null) {
            invalidate();
            return null;
        }
        if (dirty.isEmpty() && !this.mIsAnimating) {
            return null;
        }
        if (this.mCurScrollY != 0 || this.mTranslator != null) {
            this.mTempRect.set(dirty);
            dirty = this.mTempRect;
            int i = this.mCurScrollY;
            if (i != 0) {
                dirty.offset(0, -i);
            }
            CompatibilityInfo.Translator translator = this.mTranslator;
            if (translator != null) {
                translator.translateRectInAppWindowToScreen(dirty);
            }
            if (this.mAttachInfo.mScalingRequired) {
                dirty.inset(-1, -1);
            }
        }
        invalidateRectOnScreen(dirty);
        return null;
    }

    private void invalidateRectOnScreen(Rect dirty) {
        if (DEBUG_DRAW) {
            Log.m100v(this.mTag, "invalidateRectOnScreen: " + dirty);
        }
        Rect localDirty = this.mDirty;
        localDirty.union(dirty.left, dirty.top, dirty.right, dirty.bottom);
        float appScale = this.mAttachInfo.mApplicationScale;
        boolean intersected = localDirty.intersect(0, 0, (int) ((this.mWidth * appScale) + 0.5f), (int) ((this.mHeight * appScale) + 0.5f));
        if (!intersected) {
            localDirty.setEmpty();
        }
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [9] mView=" + this.mView + " mWillDrawSoon=" + this.mWillDrawSoon + " intersected=" + intersected + " mIsAnimating=" + this.mIsAnimating);
        }
        if (this.mWillDrawSoon) {
            return;
        }
        if (intersected || this.mIsAnimating) {
            scheduleTraversals();
        }
    }

    public void setIsAmbientMode(boolean ambient) {
        this.mIsAmbientMode = ambient;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setWindowStopped(boolean stopped) {
        Log.m98i(this.mTag, "stopped(" + stopped + ") old = " + this.mStopped);
        checkThread();
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [10] mView=" + this.mView + " mStopped=" + this.mStopped + " stopped=" + stopped);
        }
        if (this.mStopped != stopped) {
            this.mStopped = stopped;
            ThreadedRenderer renderer = this.mAttachInfo.mThreadedRenderer;
            if (renderer != null) {
                Log.m94d(this.mTag, "WindowStopped on " + ((Object) getTitle()) + " set to " + this.mStopped);
                renderer.setStopped(this.mStopped);
            }
            if (!this.mStopped) {
                this.mAppVisibilityChanged = true;
                scheduleTraversals();
                return;
            }
            if (renderer != null) {
                renderer.destroyHardwareResources(this.mView);
            }
            if (this.mSurface.isValid()) {
                if (this.mSurfaceHolder != null) {
                    notifyHolderSurfaceDestroyed();
                }
                notifySurfaceDestroyed();
            }
            destroySurface();
        }
    }

    public interface SurfaceChangedCallback {
        void surfaceCreated(SurfaceControl.Transaction transaction);

        void surfaceDestroyed();

        void surfaceReplaced(SurfaceControl.Transaction transaction);

        default void vriDrawStarted(boolean isWmSync) {
        }
    }

    public void addSurfaceChangedCallback(SurfaceChangedCallback c) {
        this.mSurfaceChangedCallbacks.add(c);
    }

    public void removeSurfaceChangedCallback(SurfaceChangedCallback c) {
        this.mSurfaceChangedCallbacks.remove(c);
    }

    private void notifySurfaceCreated(SurfaceControl.Transaction t) {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).surfaceCreated(t);
        }
    }

    private void notifySurfaceReplaced(SurfaceControl.Transaction t) {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).surfaceReplaced(t);
        }
    }

    private void notifySurfaceDestroyed() {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).surfaceDestroyed();
        }
    }

    private void notifyDrawStarted(boolean isWmSync) {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).vriDrawStarted(isWmSync);
        }
    }

    public SurfaceControl getBoundsLayer() {
        if (this.mBoundsLayer == null) {
            WindowConfiguration winConfig = this.mContext.getResources().getConfiguration().windowConfiguration;
            boolean needBoundsLayer = winConfig.getWindowingMode() == 5 && winConfig.getFreeformTranslucent() != 1 && this.mWindowAttributes.type == 1;
            this.mBoundsLayer = new SurfaceControl.Builder(this.mSurfaceSession).setContainerLayer().setName("Bounds for - " + getTitle().toString() + "@" + this.mBoundsLayerCreatedCount).setParent(getSurfaceControl()).setColorLayer().setCallsite("ViewRootImpl.getBoundsLayer").build();
            setBoundsLayerCrop(this.mTransaction);
            if (!needBoundsLayer) {
                this.mTransaction.unsetColor(this.mBoundsLayer);
                this.mIsBoundsColorLayer = false;
            } else {
                this.mTransaction.setColor(this.mBoundsLayer, new float[]{0.0f, 0.0f, 0.0f});
                this.mTransaction.setLayer(this.mBoundsLayer, -3);
                this.mIsBoundsColorLayer = true;
            }
            this.mTransaction.show(this.mBoundsLayer).apply();
            this.mBoundsLayerCreatedCount++;
        }
        return this.mBoundsLayer;
    }

    void updateBlastSurfaceIfNeeded() {
        if (!this.mSurfaceControl.isValid()) {
            return;
        }
        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
        if (bLASTBufferQueue != null && bLASTBufferQueue.isSameSurfaceControl(this.mSurfaceControl)) {
            this.mBlastBufferQueue.update(this.mSurfaceControl, this.mSurfaceSize.f85x, this.mSurfaceSize.f86y, this.mWindowAttributes.format);
            return;
        }
        BLASTBufferQueue bLASTBufferQueue2 = this.mBlastBufferQueue;
        if (bLASTBufferQueue2 != null) {
            bLASTBufferQueue2.destroy();
        }
        BLASTBufferQueue bLASTBufferQueue3 = new BLASTBufferQueue(this.mTag, this.mSurfaceControl, this.mSurfaceSize.f85x, this.mSurfaceSize.f86y, this.mWindowAttributes.format);
        this.mBlastBufferQueue = bLASTBufferQueue3;
        bLASTBufferQueue3.setTransactionHangCallback(sTransactionHangCallback);
        Surface blastSurface = this.mBlastBufferQueue.createSurface();
        this.mSurface.transferFrom(blastSurface);
    }

    private void setBoundsLayerCrop(SurfaceControl.Transaction t) {
        this.mTempRect.set(0, 0, this.mSurfaceSize.f85x, this.mSurfaceSize.f86y);
        this.mTempRect.inset(this.mWindowAttributes.surfaceInsets.left, this.mWindowAttributes.surfaceInsets.top, this.mWindowAttributes.surfaceInsets.right, this.mWindowAttributes.surfaceInsets.bottom);
        this.mTempRect.inset(this.mChildBoundingInsets.left, this.mChildBoundingInsets.top, this.mChildBoundingInsets.right, this.mChildBoundingInsets.bottom);
        t.setWindowCrop(this.mBoundsLayer, this.mTempRect);
    }

    private boolean updateBoundsLayer(SurfaceControl.Transaction t) {
        if (this.mBoundsLayer == null) {
            return false;
        }
        setBoundsLayerCrop(t);
        Log.m98i(this.mTag, "updateBoundsLayer: t=" + t + " sc=" + this.mBoundsLayer + " frame=" + this.mSurface.getNextFrameNumber());
        if (this.mAttachInfo.mDisplayState == 1) {
            this.mDeferTransactionRequested = true;
            Log.m98i(this.mTag, "updateBoundsLayer: set mDeferTransactionRequested=" + this.mDeferTransactionRequested);
        }
        WindowConfiguration winConfig = this.mContext.getResources().getConfiguration().windowConfiguration;
        boolean isFreeform = winConfig.getWindowingMode() == 5;
        if (this.mWindowAttributes.type == 1) {
            if (!this.mIsWindowOpaque && isFreeform) {
                t.setLayer(this.mBoundsLayer, 0);
                t.unsetColor(this.mBoundsLayer);
                this.mIsBoundsColorLayer = false;
            } else {
                boolean z = this.mIsBoundsColorLayer;
                if (!z && isFreeform) {
                    t.setLayer(this.mBoundsLayer, -3);
                    t.setColor(this.mBoundsLayer, new float[]{0.0f, 0.0f, 0.0f});
                    this.mIsBoundsColorLayer = true;
                } else if (z && !isFreeform) {
                    t.setLayer(this.mBoundsLayer, 0);
                    t.unsetColor(this.mBoundsLayer);
                    this.mIsBoundsColorLayer = false;
                }
            }
        }
        this.mFullRedrawNeeded = true;
        return true;
    }

    private void prepareSurfaces() {
        SurfaceControl.Transaction t = this.mTransaction;
        SurfaceControl sc = getSurfaceControl();
        if (sc.isValid() && updateBoundsLayer(t)) {
            applyTransactionOnDraw(t);
        }
    }

    private void destroySurface() {
        if (this.mBoundsLayer != null) {
            SurfaceControl.Transaction t = new SurfaceControl.Transaction();
            t.remove(this.mBoundsLayer).apply();
            this.mBoundsLayer = null;
        }
        this.mSurface.release();
        this.mSurfaceControl.release();
        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            bLASTBufferQueue.destroy();
            this.mBlastBufferQueue = null;
        }
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.setSurfaceControl(null, null);
        }
    }

    public void setPausedForTransition(boolean paused) {
        this.mPausedForTransition = paused;
    }

    @Override // android.view.ViewParent
    public ViewParent getParent() {
        return null;
    }

    @Override // android.view.ViewParent
    public boolean getChildVisibleRect(View child, Rect r, Point offset) {
        if (child != this.mView) {
            throw new RuntimeException("child is not mine, honest!");
        }
        return r.intersect(0, 0, this.mWidth, this.mHeight);
    }

    @Override // android.view.ViewParent
    public boolean getChildLocalHitRegion(View child, Region region, Matrix matrix, boolean isHover) {
        if (child != this.mView) {
            throw new IllegalArgumentException("child " + child + " is not the root view " + this.mView + " managed by this ViewRootImpl");
        }
        RectF rectF = new RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
        matrix.mapRect(rectF);
        return region.m20op(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom), Region.EnumC0836Op.INTERSECT);
    }

    @Override // android.view.ViewParent
    public void bringChildToFront(View child) {
    }

    int getHostVisibility() {
        View view = this.mView;
        if (view == null || !(this.mAppVisible || this.mForceDecorViewVisibility)) {
            return 8;
        }
        return view.getVisibility();
    }

    public void requestTransitionStart(LayoutTransition transition) {
        ArrayList<LayoutTransition> arrayList = this.mPendingTransitions;
        if (arrayList == null || !arrayList.contains(transition)) {
            if (this.mPendingTransitions == null) {
                this.mPendingTransitions = new ArrayList<>();
            }
            this.mPendingTransitions.add(transition);
        }
    }

    void notifyRendererOfFramePending() {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.notifyFramePending();
        }
    }

    public void notifyRendererOfExpensiveFrame() {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.notifyExpensiveFrame();
        }
    }

    void scheduleTraversals() {
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, scheduleTraversals! mView=" + this.mView + " callers=" + Debug.getCallers(7));
        }
        if (sSafeScheduleTraversals) {
            checkThread();
        }
        if (this.mScheduleTraversalDeferCount <= 0 && !this.mTraversalScheduled) {
            this.mTraversalScheduled = true;
            this.mTraversalBarrier = this.mHandler.getLooper().getQueue().postSyncBarrier();
            this.mChoreographer.postCallback(3, this.mTraversalRunnable, null);
            notifyRendererOfFramePending();
            pokeDrawLockIfNeeded();
        }
    }

    void unscheduleTraversals() {
        if (this.mTraversalScheduled) {
            this.mTraversalScheduled = false;
            this.mHandler.getLooper().getQueue().removeSyncBarrier(this.mTraversalBarrier);
            this.mChoreographer.removeCallbacks(3, this.mTraversalRunnable, null);
        }
    }

    void doTraversal() {
        if (this.mTraversalScheduled) {
            if (this.mPokeDrawLockController.isRequestedToAllowPokeDrawLock()) {
                pokeDrawLockIfNeeded();
            }
            this.mTraversalScheduled = false;
            this.mHandler.getLooper().getQueue().removeSyncBarrier(this.mTraversalBarrier);
            if (this.mProfile) {
                Debug.startMethodTracing("ViewAncestor");
            }
            performTraversals();
            if (this.mProfile) {
                Debug.stopMethodTracing();
                this.mProfile = false;
            }
        }
    }

    private void applyKeepScreenOnFlag(WindowManager.LayoutParams params) {
        if (this.mAttachInfo.mKeepScreenOn) {
            params.flags |= 128;
        } else {
            params.flags = (params.flags & PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE) | (this.mClientWindowLayoutFlags & 128);
        }
    }

    private boolean collectViewAttributes() {
        if (this.mAttachInfo.mRecomputeGlobalAttributes) {
            this.mAttachInfo.mRecomputeGlobalAttributes = false;
            boolean oldScreenOn = this.mAttachInfo.mKeepScreenOn;
            this.mAttachInfo.mKeepScreenOn = false;
            this.mAttachInfo.mSystemUiVisibility = 0;
            this.mAttachInfo.mHasSystemUiListeners = false;
            this.mView.dispatchCollectViewAttributes(this.mAttachInfo, 0);
            this.mAttachInfo.mSystemUiVisibility &= ~this.mAttachInfo.mDisabledSystemUiVisibility;
            WindowManager.LayoutParams params = this.mWindowAttributes;
            this.mAttachInfo.mSystemUiVisibility |= getImpliedSystemUiVisibility(params);
            SystemUiVisibilityInfo systemUiVisibilityInfo = this.mCompatibleVisibilityInfo;
            systemUiVisibilityInfo.globalVisibility = (systemUiVisibilityInfo.globalVisibility & (-2)) | (this.mAttachInfo.mSystemUiVisibility & 1);
            dispatchDispatchSystemUiVisibilityChanged();
            if (this.mAttachInfo.mKeepScreenOn != oldScreenOn || this.mAttachInfo.mSystemUiVisibility != params.subtreeSystemUiVisibility || this.mAttachInfo.mHasSystemUiListeners != params.hasSystemUiListeners) {
                applyKeepScreenOnFlag(params);
                params.subtreeSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                params.hasSystemUiListeners = this.mAttachInfo.mHasSystemUiListeners;
                this.mView.dispatchWindowSystemUiVisiblityChanged(this.mAttachInfo.mSystemUiVisibility);
                return true;
            }
        }
        return false;
    }

    private int getImpliedSystemUiVisibility(WindowManager.LayoutParams params) {
        int vis = 0;
        if ((params.flags & 67108864) != 0) {
            vis = 0 | 1280;
        }
        if ((params.flags & 134217728) != 0) {
            return vis | 768;
        }
        return vis;
    }

    void updateCompatSysUiVisibility(int visibleTypes, int requestedVisibleTypes, int controllableTypes) {
        int visibleTypes2 = (requestedVisibleTypes & controllableTypes) | ((~controllableTypes) & visibleTypes);
        updateCompatSystemUiVisibilityInfo(4, WindowInsets.Type.statusBars(), visibleTypes2, controllableTypes);
        updateCompatSystemUiVisibilityInfo(2, WindowInsets.Type.navigationBars(), visibleTypes2, controllableTypes);
        dispatchDispatchSystemUiVisibilityChanged();
    }

    private void updateCompatSystemUiVisibilityInfo(int systemUiFlag, int insetsType, int visibleTypes, int controllableTypes) {
        SystemUiVisibilityInfo info = this.mCompatibleVisibilityInfo;
        boolean willBeVisible = (visibleTypes & insetsType) != 0;
        boolean hasControl = (controllableTypes & insetsType) != 0;
        boolean wasInvisible = (this.mAttachInfo.mSystemUiVisibility & systemUiFlag) != 0;
        if (willBeVisible) {
            info.globalVisibility &= ~systemUiFlag;
            if (hasControl && wasInvisible) {
                info.localChanges |= systemUiFlag;
                return;
            }
            return;
        }
        info.globalVisibility |= systemUiFlag;
        info.localChanges &= ~systemUiFlag;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearLowProfileModeIfNeeded(int showTypes, boolean fromIme) {
        SystemUiVisibilityInfo info = this.mCompatibleVisibilityInfo;
        if ((WindowInsets.Type.systemBars() & showTypes) != 0 && !fromIme && (info.globalVisibility & 1) != 0) {
            info.globalVisibility &= -2;
            info.localChanges |= 1;
            dispatchDispatchSystemUiVisibilityChanged();
        }
    }

    private void dispatchDispatchSystemUiVisibilityChanged() {
        if (this.mDispatchedSystemUiVisibility != this.mCompatibleVisibilityInfo.globalVisibility) {
            this.mHandler.removeMessages(17);
            ViewRootHandler viewRootHandler = this.mHandler;
            viewRootHandler.sendMessage(viewRootHandler.obtainMessage(17));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDispatchSystemUiVisibilityChanged() {
        if (this.mView == null) {
            return;
        }
        SystemUiVisibilityInfo info = this.mCompatibleVisibilityInfo;
        if (info.localChanges != 0) {
            this.mView.updateLocalSystemUiVisibility(info.localValue, info.localChanges);
            info.localChanges = 0;
        }
        int visibility = info.globalVisibility & 7;
        if (this.mDispatchedSystemUiVisibility != visibility) {
            this.mDispatchedSystemUiVisibility = visibility;
            this.mView.dispatchSystemUiVisibilityChanged(visibility);
        }
    }

    public static void adjustLayoutParamsForCompatibility(WindowManager.LayoutParams inOutParams) {
        int sysUiVis = inOutParams.systemUiVisibility | inOutParams.subtreeSystemUiVisibility;
        int flags = inOutParams.flags;
        int type = inOutParams.type;
        int adjust = inOutParams.softInputMode & 240;
        if ((inOutParams.privateFlags & 67108864) == 0) {
            inOutParams.insetsFlags.appearance = 0;
            if ((sysUiVis & 1) != 0) {
                inOutParams.insetsFlags.appearance |= 4;
            }
            if ((sysUiVis & 8192) != 0) {
                inOutParams.insetsFlags.appearance |= 8;
            }
            if ((sysUiVis & 16) != 0) {
                inOutParams.insetsFlags.appearance |= 16;
            }
        }
        if ((inOutParams.privateFlags & 134217728) == 0) {
            if ((sysUiVis & 4096) != 0 || (flags & 1024) != 0) {
                inOutParams.insetsFlags.behavior = 2;
            } else {
                inOutParams.insetsFlags.behavior = 1;
            }
        }
        inOutParams.privateFlags &= -1073741825;
        if ((inOutParams.privateFlags & 268435456) != 0) {
            return;
        }
        int types = inOutParams.getFitInsetsTypes();
        boolean ignoreVis = inOutParams.isFitInsetsIgnoringVisibility();
        if ((sysUiVis & 1024) != 0 || (flags & 256) != 0 || (67108864 & flags) != 0) {
            types &= ~WindowInsets.Type.statusBars();
        }
        if ((sysUiVis & 512) != 0 || (flags & 134217728) != 0) {
            types &= ~WindowInsets.Type.systemBars();
        }
        if (type == 2005 || type == 2003) {
            ignoreVis = true;
        } else if ((WindowInsets.Type.systemBars() & types) == WindowInsets.Type.systemBars()) {
            if (adjust == 16) {
                types |= WindowInsets.Type.ime();
            } else {
                inOutParams.privateFlags |= 1073741824;
            }
        }
        inOutParams.setFitInsetsTypes(types);
        inOutParams.setFitInsetsIgnoringVisibility(ignoreVis);
        inOutParams.privateFlags &= -268435457;
    }

    private void controlInsetsForCompatibility(WindowManager.LayoutParams params) {
        int sysUiVis = params.systemUiVisibility | params.subtreeSystemUiVisibility;
        int flags = params.flags;
        boolean matchParent = params.width == -1 && params.height == -1;
        boolean nonAttachedAppWindow = params.type >= 1 && params.type <= 99;
        boolean statusWasHiddenByFlags = (this.mTypesHiddenByFlags & WindowInsets.Type.statusBars()) != 0;
        boolean statusIsHiddenByFlags = (sysUiVis & 4) != 0 || ((flags & 1024) != 0 && matchParent && nonAttachedAppWindow);
        boolean navWasHiddenByFlags = (this.mTypesHiddenByFlags & WindowInsets.Type.navigationBars()) != 0;
        boolean navIsHiddenByFlags = (sysUiVis & 2) != 0;
        int typesToHide = 0;
        int typesToShow = 0;
        if (statusIsHiddenByFlags && !statusWasHiddenByFlags) {
            typesToHide = 0 | WindowInsets.Type.statusBars();
        } else if (!statusIsHiddenByFlags && statusWasHiddenByFlags) {
            typesToShow = 0 | WindowInsets.Type.statusBars();
        }
        if (navIsHiddenByFlags && !navWasHiddenByFlags) {
            typesToHide |= WindowInsets.Type.navigationBars();
        } else if (!navIsHiddenByFlags && navWasHiddenByFlags) {
            typesToShow |= WindowInsets.Type.navigationBars();
        }
        if (typesToHide != 0) {
            getInsetsController().hide(typesToHide);
        }
        if (typesToShow != 0) {
            getInsetsController().show(typesToShow);
        }
        int i = this.mTypesHiddenByFlags | typesToHide;
        this.mTypesHiddenByFlags = i;
        this.mTypesHiddenByFlags = i & (~typesToShow);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0203  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean measureHierarchy(View host, WindowManager.LayoutParams lp, Resources res, int desiredWindowWidth, int desiredWindowHeight, boolean forRootSizeOnly) {
        boolean windowSizeMayChange;
        boolean goodMeasure;
        boolean goodMeasure2;
        if (DEBUG_ORIENTATION || DEBUG_LAYOUT) {
            Log.m100v(this.mTag, "Measuring " + host + " in display " + desiredWindowWidth + "x" + desiredWindowHeight + Session.TRUNCATE_STRING);
        }
        if (lp.width != -2) {
            windowSizeMayChange = false;
            goodMeasure = false;
        } else {
            DisplayMetrics packageMetrics = res.getDisplayMetrics();
            if (this.mIsDeviceDefault) {
                if (lp.type == 2005) {
                    res.getValue(C4337R.dimen.sem_config_prefToastWidth, this.mTmpValue, true);
                } else {
                    res.getValue(C4337R.dimen.sem_config_prefDialogWidth, this.mTmpValue, true);
                }
            } else {
                res.getValue(C4337R.dimen.config_prefDialogWidth, this.mTmpValue, true);
            }
            int baseSize = 0;
            if (this.mTmpValue.type == 5) {
                baseSize = (int) this.mTmpValue.getDimension(packageMetrics);
            } else if (this.mTmpValue.type == 6) {
                if (this.mDesktopMode && lp.type == 2005 && this.mView != null) {
                    Rect rect = new Rect();
                    this.mView.getWindowDisplayFrame(rect);
                    baseSize = (int) this.mTmpValue.getFraction(rect.width(), rect.width());
                } else {
                    baseSize = (int) this.mTmpValue.getFraction(packageMetrics.widthPixels, packageMetrics.widthPixels);
                }
            }
            boolean z = DEBUG_DIALOG;
            if (z) {
                Log.m100v(this.mTag, "Window " + this.mView + ": baseSize=" + baseSize + ", desiredWindowWidth=" + desiredWindowWidth);
            }
            if (baseSize == 0 || desiredWindowWidth <= baseSize) {
                windowSizeMayChange = false;
                goodMeasure = false;
            } else {
                int childWidthMeasureSpec = getRootMeasureSpec(baseSize, lp.width, lp.privateFlags);
                int childHeightMeasureSpec = getRootMeasureSpec(desiredWindowHeight, lp.height, lp.privateFlags);
                performMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
                if (z) {
                    windowSizeMayChange = false;
                    goodMeasure = false;
                    Log.m100v(this.mTag, "Window " + this.mView + ": measured (" + host.getMeasuredWidth() + "," + host.getMeasuredHeight() + ") from width spec: " + View.MeasureSpec.toString(childWidthMeasureSpec) + " and height spec: " + View.MeasureSpec.toString(childHeightMeasureSpec));
                } else {
                    windowSizeMayChange = false;
                    goodMeasure = false;
                }
                if ((host.getMeasuredWidthAndState() & 16777216) == 0) {
                    goodMeasure2 = true;
                } else {
                    int baseSize2 = (baseSize + desiredWindowWidth) / 2;
                    if (z) {
                        Log.m100v(this.mTag, "Window " + this.mView + ": next baseSize=" + baseSize2);
                    }
                    performMeasure(getRootMeasureSpec(baseSize2, lp.width, lp.privateFlags), childHeightMeasureSpec);
                    if (z) {
                        Log.m100v(this.mTag, "Window " + this.mView + ": measured (" + host.getMeasuredWidth() + "," + host.getMeasuredHeight() + NavigationBarInflaterView.KEY_CODE_END);
                    }
                    if ((host.getMeasuredWidthAndState() & 16777216) == 0) {
                        if (z) {
                            Log.m100v(this.mTag, "Good!");
                        }
                        goodMeasure2 = true;
                    }
                }
                if (!goodMeasure2) {
                    int childWidthMeasureSpec2 = getRootMeasureSpec(desiredWindowWidth, lp.width, lp.privateFlags);
                    int childHeightMeasureSpec2 = getRootMeasureSpec(desiredWindowHeight, lp.height, lp.privateFlags);
                    if (forRootSizeOnly && setMeasuredRootSizeFromSpec(childWidthMeasureSpec2, childHeightMeasureSpec2)) {
                        this.mViewMeasureDeferred = true;
                    } else {
                        performMeasure(childWidthMeasureSpec2, childHeightMeasureSpec2);
                    }
                    if (this.mWidth != host.getMeasuredWidth() || this.mHeight != host.getMeasuredHeight()) {
                        return true;
                    }
                }
                return windowSizeMayChange;
            }
        }
        goodMeasure2 = goodMeasure;
        if (!goodMeasure2) {
        }
        return windowSizeMayChange;
    }

    private boolean setMeasuredRootSizeFromSpec(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode != 1073741824 || heightMode != 1073741824) {
            return false;
        }
        this.mMeasuredWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        this.mMeasuredHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        return true;
    }

    void transformMatrixToGlobal(Matrix m) {
        m.preTranslate(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
    }

    void transformMatrixToLocal(Matrix m) {
        m.postTranslate(-this.mAttachInfo.mWindowLeft, -this.mAttachInfo.mWindowTop);
    }

    WindowInsets getWindowInsets(boolean forceConstruct) {
        return getWindowInsets(forceConstruct, false);
    }

    WindowInsets getWindowInsets(boolean forceConstruct, boolean removeCutout) {
        if (this.mLastWindowInsets == null || forceConstruct) {
            Configuration config = getConfiguration();
            WindowInsets calculateInsets = this.mInsetsController.calculateInsets(config.isScreenRound(), this.mWindowAttributes.type, config.windowConfiguration.getActivityType(), this.mWindowAttributes.softInputMode, this.mWindowAttributes.flags, this.mWindowAttributes.systemUiVisibility | this.mWindowAttributes.subtreeSystemUiVisibility);
            this.mLastWindowInsets = calculateInsets;
            if (this.mIsCutoutRemoveNeeded || removeCutout) {
                WindowInsets insets = calculateInsets.removeCutoutInsets();
                if (this.mIsCutoutRemoveNeeded) {
                    this.mLastWindowInsets = insets;
                } else {
                    this.mAttachInfo.mContentInsets.set(insets.getSystemWindowInsets().toRect());
                    this.mAttachInfo.mStableInsets.set(insets.getStableInsets().toRect());
                    this.mAttachInfo.mVisibleInsets.set(this.mInsetsController.calculateVisibleInsets(this.mWindowAttributes.type, config.windowConfiguration.getWindowingMode(), this.mWindowAttributes.softInputMode, this.mWindowAttributes.flags).toRect());
                    return insets;
                }
            }
            this.mAttachInfo.mContentInsets.set(this.mLastWindowInsets.getSystemWindowInsets().toRect());
            this.mAttachInfo.mStableInsets.set(this.mLastWindowInsets.getStableInsets().toRect());
            this.mAttachInfo.mVisibleInsets.set(this.mInsetsController.calculateVisibleInsets(this.mWindowAttributes.type, config.windowConfiguration.getActivityType(), this.mWindowAttributes.softInputMode, this.mWindowAttributes.flags).toRect());
        }
        return this.mLastWindowInsets;
    }

    public void dispatchApplyInsets(View host) {
        Trace.traceBegin(8L, "dispatchApplyInsets");
        this.mApplyInsetsRequested = false;
        WindowInsets insets = getWindowInsets(true, this.mIsCutoutRemoveForDispatchNeeded);
        if (!shouldDispatchCutout()) {
            insets = insets.consumeDisplayCutout();
        }
        if (DEBUG_WINDOW_INSETS) {
            Log.m98i(this.mTag, "dispatchApplyInsets : " + insets);
        }
        if (host instanceof DecorView) {
            ((DecorView) host).updateCaptionHeightIfNeeded(insets);
        }
        InsetsSource imeSource = this.mInsetsController.getState().peekSource(InsetsSource.ID_IME);
        if (imeSource != null && imeSource.isVisible() && getConfiguration().windowConfiguration.isPopOver()) {
            this.mForceNextWindowRelayout = true;
        }
        host.dispatchApplyWindowInsets(insets);
        this.mAttachInfo.delayNotifyContentCaptureInsetsEvent(insets.getInsets(WindowInsets.Type.all()));
        Trace.traceEnd(8L);
    }

    private boolean updateCaptionInsets() {
        if (CAPTION_ON_SHELL) {
            return false;
        }
        View view = this.mView;
        if (!(view instanceof DecorView)) {
            return false;
        }
        int captionInsetsHeight = ((DecorView) view).getCaptionInsetsHeight();
        Rect captionFrame = new Rect();
        if (captionInsetsHeight != 0) {
            captionFrame.set(this.mWinFrame.left, this.mWinFrame.top, this.mWinFrame.right, this.mWinFrame.top + captionInsetsHeight);
        }
        if (this.mAttachInfo.mCaptionInsets.equals(captionFrame)) {
            return false;
        }
        this.mAttachInfo.mCaptionInsets.set(captionFrame);
        return true;
    }

    private boolean shouldDispatchCutout() {
        return this.mWindowAttributes.layoutInDisplayCutoutMode == 3 || this.mWindowAttributes.layoutInDisplayCutoutMode == 1;
    }

    public InsetsController getInsetsController() {
        return this.mInsetsController;
    }

    private static boolean shouldUseDisplaySize(WindowManager.LayoutParams lp) {
        return lp.type == 2041 || lp.type == 2011 || lp.type == 2020;
    }

    private static boolean shouldOptimizeMeasure(WindowManager.LayoutParams lp) {
        return (lp.privateFlags & 512) != 0;
    }

    private Rect getWindowBoundsInsetSystemBars() {
        Rect bounds = new Rect(this.mContext.getResources().getConfiguration().windowConfiguration.getBounds());
        bounds.inset(this.mInsetsController.getState().calculateInsets(bounds, WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(), false));
        return bounds;
    }

    int dipToPx(int dip) {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        return (int) ((displayMetrics.density * dip) + 0.5f);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(189:16|(1:980)(1:24)|25|(4:27|(1:29)(1:978)|(1:31)(1:977)|(181:33|34|(3:36|(1:38)(1:975)|39)(1:976)|40|(6:42|(1:44)(2:961|(1:966)(1:965))|45|(1:47)|48|(1:50))(2:967|(3:971|(1:973)|974))|(3:52|(2:(1:55)(1:57)|56)|(1:61))|62|(1:64)|65|(1:67)|68|(1:960)(1:74)|75|(3:77|(1:958)(2:83|(1:85)(1:957))|86)(1:959)|87|(1:89)|90|(1:92)|93|(2:940|(6:942|(3:944|(2:946|947)(1:949)|948)|950|(1:952)|953|(1:955))(1:956))(1:97)|98|(2:100|(1:102)(1:938))(1:939)|(1:104)|(1:937)(151:107|(1:936)(4:111|(2:113|(1:115))|929|(2:931|(1:933)))|117|118|(1:928)(1:122)|123|(1:927)(1:127)|128|(1:130)(1:926)|131|(1:133)(1:925)|(4:135|(1:139)|140|(1:142))(1:924)|143|(1:923)(2:148|(1:150)(45:922|277|(1:279)|280|(1:538)(4:284|285|(1:287)|289)|(1:535)|(1:534)(1:303)|(1:533)(1:307)|(2:309|(6:311|(1:530)|315|(1:317)|318|(2:320|(1:322)))(1:531))(1:532)|323|(1:325)(1:(1:527)(1:(1:529)))|(1:327)|(1:329)|330|(3:332|(3:520|(1:522)(1:524)|523)(1:336)|337)(1:525)|338|(1:519)(1:342)|343|(7:345|(4:347|(1:349)|350|(1:352))(1:507)|(1:354)(1:506)|(1:356)|(1:358)(1:(1:505))|359|360)(2:508|(3:512|513|514))|361|(1:497)(6:363|(2:489|(1:493))|368|(1:370)(1:488)|371|(2:373|(2:375|(1:377))(1:(1:379))))|(2:485|(20:487|(1:386)|387|(1:484)(1:390)|391|(1:393)|(1:483)(1:396)|397|(1:482)(1:402)|(1:481)(4:404|(1:406)(1:480)|407|(1:411))|478|415|(1:475)|425|(1:429)|(4:431|(4:435|(2:438|436)|439|440)|441|(1:443))(1:(2:(1:457)(1:459)|458)(4:460|(4:464|(2:467|465)|468|469)|470|(1:474)))|444|(1:446)|447|(2:449|(2:451|452)(1:453))(1:454)))(1:383)|384|(0)|387|(0)|484|391|(0)|(0)|483|397|(1:400)|482|(0)(0)|478|415|(1:477)(2:418|475)|425|(2:427|429)|(0)(0)|444|(0)|447|(0)(0)))|151|(3:153|(1:155)(1:920)|156)(1:921)|157|(1:159)|160|161|162|(9:164|165|166|167|168|169|170|171|172)(1:914)|174|175|(1:895)|178|179|(1:181)(1:893)|182|183|184|185|186|187|188|189|190|(5:872|873|(1:875)|876|(1:878))|192|(1:194)(1:871)|195|(6:838|839|(1:843)|(1:847)|(1:851)|(1:865)(4:860|861|862|863))(1:197)|(1:199)|200|201|(6:205|(1:207)|208|(1:210)(1:585)|211|212)|586|(6:588|589|590|591|592|593)(1:832)|594|595|(1:597)(1:818)|598|(1:817)(1:602)|603|(1:816)(1:607)|608|609|(2:812|(93:814|613|(1:615)|(2:617|618)(1:811)|619|620|(1:622)|(6:787|788|789|790|791|(1:793))(1:624)|625|626|627|628|(4:630|631|632|(87:660|661|662|663|664|665|666|667|668|669|670|671|672|673|674|675|(1:679)|681|636|(1:(5:639|(1:649)(1:643)|644|(1:646)(1:648)|647)(1:650))|651|(1:(1:654)(1:655))|656|(1:658)|659|219|(1:221)|222|(1:577)|226|(6:228|(1:230)|231|(2:233|(3:235|(1:237)|238))|(2:564|(3:566|(4:568|(1:570)|571|572)(1:574)|573)(1:575))(1:243)|(4:245|246|247|248))(1:576)|253|(3:264|(1:270)(1:268)|269)|271|(8:545|(1:547)(1:563)|548|(1:550)(1:562)|551|(1:553)(1:561)|(1:560)(2:(1:556)(1:559)|557)|558)(1:275)|276|277|(0)|280|(1:282)|538|(0)|535|(2:299|301)|534|(1:305)|533|(0)(0)|323|(0)(0)|(0)|(0)|330|(0)(0)|338|(1:340)|519|343|(0)(0)|361|(0)(0)|(1:381)|485|(0)|384|(0)|387|(0)|484|391|(0)|(0)|483|397|(0)|482|(0)(0)|478|415|(0)(0)|425|(0)|(0)(0)|444|(0)|447|(0)(0))(1:634))(3:731|732|(8:734|(1:736)|737|(1:739)|740|(1:742)|743|(1:745)(1:746))(3:(1:751)|752|(1:777)(2:756|(6:758|759|760|761|762|763)(1:776))))|635|636|(0)|651|(0)|656|(0)|659|219|(0)|222|(1:224)|577|226|(0)(0)|253|(5:255|264|(1:266)|270|269)|271|(1:273)|539|545|(0)(0)|548|(0)(0)|551|(0)(0)|(0)(0)|558|276|277|(0)|280|(0)|538|(0)|535|(0)|534|(0)|533|(0)(0)|323|(0)(0)|(0)|(0)|330|(0)(0)|338|(0)|519|343|(0)(0)|361|(0)(0)|(0)|485|(0)|384|(0)|387|(0)|484|391|(0)|(0)|483|397|(0)|482|(0)(0)|478|415|(0)(0)|425|(0)|(0)(0)|444|(0)|447|(0)(0)))|612|613|(0)|(0)(0)|619|620|(0)|(0)(0)|625|626|627|628|(0)(0)|635|636|(0)|651|(0)|656|(0)|659|219|(0)|222|(0)|577|226|(0)(0)|253|(0)|271|(0)|539|545|(0)(0)|548|(0)(0)|551|(0)(0)|(0)(0)|558|276|277|(0)|280|(0)|538|(0)|535|(0)|534|(0)|533|(0)(0)|323|(0)(0)|(0)|(0)|330|(0)(0)|338|(0)|519|343|(0)(0)|361|(0)(0)|(0)|485|(0)|384|(0)|387|(0)|484|391|(0)|(0)|483|397|(0)|482|(0)(0)|478|415|(0)(0)|425|(0)|(0)(0)|444|(0)|447|(0)(0))|935|118|(1:120)|928|123|(1:125)|927|128|(0)(0)|131|(0)(0)|(0)(0)|143|(0)|923|151|(0)(0)|157|(0)|160|161|162|(0)(0)|174|175|(0)|895|178|179|(0)(0)|182|183|184|185|186|187|188|189|190|(0)|192|(0)(0)|195|(0)(0)|(0)|200|201|(7:203|205|(0)|208|(0)(0)|211|212)|586|(0)(0)|594|595|(0)(0)|598|(1:600)|817|603|(1:605)|816|608|609|(0)|812|(0)|612|613|(0)|(0)(0)|619|620|(0)|(0)(0)|625|626|627|628|(0)(0)|635|636|(0)|651|(0)|656|(0)|659|219|(0)|222|(0)|577|226|(0)(0)|253|(0)|271|(0)|539|545|(0)(0)|548|(0)(0)|551|(0)(0)|(0)(0)|558|276|277|(0)|280|(0)|538|(0)|535|(0)|534|(0)|533|(0)(0)|323|(0)(0)|(0)|(0)|330|(0)(0)|338|(0)|519|343|(0)(0)|361|(0)(0)|(0)|485|(0)|384|(0)|387|(0)|484|391|(0)|(0)|483|397|(0)|482|(0)(0)|478|415|(0)(0)|425|(0)|(0)(0)|444|(0)|447|(0)(0)))|979|34|(0)(0)|40|(0)(0)|(0)|62|(0)|65|(0)|68|(2:70|72)|960|75|(0)(0)|87|(0)|90|(0)|93|(1:95)|940|(0)(0)|98|(0)(0)|(0)|(0)|937|935|118|(0)|928|123|(0)|927|128|(0)(0)|131|(0)(0)|(0)(0)|143|(0)|923|151|(0)(0)|157|(0)|160|161|162|(0)(0)|174|175|(0)|895|178|179|(0)(0)|182|183|184|185|186|187|188|189|190|(0)|192|(0)(0)|195|(0)(0)|(0)|200|201|(0)|586|(0)(0)|594|595|(0)(0)|598|(0)|817|603|(0)|816|608|609|(0)|812|(0)|612|613|(0)|(0)(0)|619|620|(0)|(0)(0)|625|626|627|628|(0)(0)|635|636|(0)|651|(0)|656|(0)|659|219|(0)|222|(0)|577|226|(0)(0)|253|(0)|271|(0)|539|545|(0)(0)|548|(0)(0)|551|(0)(0)|(0)(0)|558|276|277|(0)|280|(0)|538|(0)|535|(0)|534|(0)|533|(0)(0)|323|(0)(0)|(0)|(0)|330|(0)(0)|338|(0)|519|343|(0)(0)|361|(0)(0)|(0)|485|(0)|384|(0)|387|(0)|484|391|(0)|(0)|483|397|(0)|482|(0)(0)|478|415|(0)(0)|425|(0)|(0)(0)|444|(0)|447|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0314, code lost:
    
        if (r30.width() != r58.mWidth) goto L169;
     */
    /* JADX WARN: Code restructure failed: missing block: B:413:0x1254, code lost:
    
        if (r1 != false) goto L864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:414:0x1257, code lost:
    
        r25 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:784:0x0a9b, code lost:
    
        r50 = r15;
        r5 = r28;
        r15 = r29;
        r12 = r43;
        r9 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:785:0x0a8c, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:786:0x0a8d, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:794:0x077d, code lost:
    
        if (r58.mApplyInsetsRequested != false) goto L993;
     */
    /* JADX WARN: Code restructure failed: missing block: B:807:0x0aba, code lost:
    
        r50 = r15;
        r5 = r28;
        r15 = r29;
        r12 = r43;
        r9 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:808:0x0aa9, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:809:0x0aaa, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:820:0x0add, code lost:
    
        r50 = r15;
        r5 = r28;
        r15 = r29;
        r12 = r43;
        r13 = r44;
        r9 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:821:0x0aca, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:822:0x0acb, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:835:0x0b04, code lost:
    
        r50 = r15;
        r5 = r28;
        r15 = r13;
        r12 = r43;
        r13 = r44;
        r9 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:836:0x0aef, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:837:0x0af0, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:880:0x0b2f, code lost:
    
        r50 = r15;
        r5 = r12;
        r15 = r13;
        r12 = r43;
        r13 = r44;
        r9 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:881:0x0b18, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:882:0x0b19, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:885:0x0b5e, code lost:
    
        r47 = r3;
        r50 = r15;
        r5 = r12;
        r15 = r13;
        r12 = r43;
        r13 = r44;
        r9 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:886:0x0b45, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:887:0x0b46, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:890:0x0b91, code lost:
    
        r47 = r3;
        r50 = r15;
        r5 = r12;
        r15 = r13;
        r12 = r43;
        r13 = r44;
        r9 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:891:0x0b76, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:892:0x0b77, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:897:0x0bc1, code lost:
    
        r47 = r3;
        r50 = r15;
        r15 = r29;
        r5 = r37;
        r12 = r43;
        r13 = r44;
        r9 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:898:0x0bab, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:899:0x0bac, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:917:0x0bf9, code lost:
    
        r47 = r3;
        r50 = r15;
        r9 = 8;
        r15 = r29;
        r5 = r37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:918:0x0bd6, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:919:0x0bd7, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:934:0x0327, code lost:
    
        if (r30.height() != r58.mHeight) goto L169;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02eb  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0348  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0360  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0375  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x03bc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0441  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0457  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x052c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0542  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0594  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0603  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0607 A[Catch: all -> 0x0631, RemoteException -> 0x0642, TRY_LEAVE, TryCatch #59 {RemoteException -> 0x0642, all -> 0x0631, blocks: (B:863:0x05d1, B:199:0x0607, B:203:0x0658, B:205:0x0662, B:207:0x0666, B:208:0x0684, B:211:0x0692), top: B:862:0x05d1 }] */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0658 A[Catch: all -> 0x0631, RemoteException -> 0x0642, TRY_ENTER, TryCatch #59 {RemoteException -> 0x0642, all -> 0x0631, blocks: (B:863:0x05d1, B:199:0x0607, B:203:0x0658, B:205:0x0662, B:207:0x0666, B:208:0x0684, B:211:0x0692), top: B:862:0x05d1 }] */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0666 A[Catch: all -> 0x0631, RemoteException -> 0x0642, TryCatch #59 {RemoteException -> 0x0642, all -> 0x0631, blocks: (B:863:0x05d1, B:199:0x0607, B:203:0x0658, B:205:0x0662, B:207:0x0666, B:208:0x0684, B:211:0x0692), top: B:862:0x05d1 }] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x068f  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0c15  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0c1c  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x0c54  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0c6c  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0d4d  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0dab  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0eb7  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0ed0  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x0f09 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0f24  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0f31  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0f3d  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x0fdd  */
    /* JADX WARN: Removed duplicated region for block: B:327:0x0ff4  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x0ffb  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x100e  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x1083  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x108d  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x110d  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x11c2  */
    /* JADX WARN: Removed duplicated region for block: B:386:0x11d3  */
    /* JADX WARN: Removed duplicated region for block: B:389:0x11e7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:393:0x11fb  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x1202 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:399:0x1214 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:404:0x1220  */
    /* JADX WARN: Removed duplicated region for block: B:417:0x1260 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:427:0x12aa  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x12de  */
    /* JADX WARN: Removed duplicated region for block: B:446:0x138a  */
    /* JADX WARN: Removed duplicated region for block: B:449:0x1394  */
    /* JADX WARN: Removed duplicated region for block: B:454:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:455:0x1312  */
    /* JADX WARN: Removed duplicated region for block: B:477:0x12a2 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:481:0x124e  */
    /* JADX WARN: Removed duplicated region for block: B:487:0x11ce  */
    /* JADX WARN: Removed duplicated region for block: B:497:0x11bc  */
    /* JADX WARN: Removed duplicated region for block: B:508:0x10ef  */
    /* JADX WARN: Removed duplicated region for block: B:525:0x1070  */
    /* JADX WARN: Removed duplicated region for block: B:526:0x0fe4  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:532:0x0fd4  */
    /* JADX WARN: Removed duplicated region for block: B:547:0x0de0  */
    /* JADX WARN: Removed duplicated region for block: B:550:0x0e47  */
    /* JADX WARN: Removed duplicated region for block: B:553:0x0e65  */
    /* JADX WARN: Removed duplicated region for block: B:555:0x0e7f  */
    /* JADX WARN: Removed duplicated region for block: B:560:0x0eac  */
    /* JADX WARN: Removed duplicated region for block: B:561:0x0e79  */
    /* JADX WARN: Removed duplicated region for block: B:562:0x0e5b  */
    /* JADX WARN: Removed duplicated region for block: B:563:0x0e2f  */
    /* JADX WARN: Removed duplicated region for block: B:576:0x0d45  */
    /* JADX WARN: Removed duplicated region for block: B:582:0x0bf4  */
    /* JADX WARN: Removed duplicated region for block: B:584:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:585:0x0691  */
    /* JADX WARN: Removed duplicated region for block: B:588:0x06aa  */
    /* JADX WARN: Removed duplicated region for block: B:597:0x0714  */
    /* JADX WARN: Removed duplicated region for block: B:600:0x0720 A[Catch: all -> 0x06bc, RemoteException -> 0x06ce, TRY_ENTER, TryCatch #56 {RemoteException -> 0x06ce, all -> 0x06bc, blocks: (B:593:0x06b4, B:600:0x0720, B:605:0x072f, B:615:0x0755, B:617:0x075d), top: B:592:0x06b4 }] */
    /* JADX WARN: Removed duplicated region for block: B:605:0x072f A[Catch: all -> 0x06bc, RemoteException -> 0x06ce, TRY_LEAVE, TryCatch #56 {RemoteException -> 0x06ce, all -> 0x06bc, blocks: (B:593:0x06b4, B:600:0x0720, B:605:0x072f, B:615:0x0755, B:617:0x075d), top: B:592:0x06b4 }] */
    /* JADX WARN: Removed duplicated region for block: B:611:0x0744 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:615:0x0755 A[Catch: all -> 0x06bc, RemoteException -> 0x06ce, TRY_ENTER, TryCatch #56 {RemoteException -> 0x06ce, all -> 0x06bc, blocks: (B:593:0x06b4, B:600:0x0720, B:605:0x072f, B:615:0x0755, B:617:0x075d), top: B:592:0x06b4 }] */
    /* JADX WARN: Removed duplicated region for block: B:617:0x075d A[Catch: all -> 0x06bc, RemoteException -> 0x06ce, TRY_LEAVE, TryCatch #56 {RemoteException -> 0x06ce, all -> 0x06bc, blocks: (B:593:0x06b4, B:600:0x0720, B:605:0x072f, B:615:0x0755, B:617:0x075d), top: B:592:0x06b4 }] */
    /* JADX WARN: Removed duplicated region for block: B:622:0x076d  */
    /* JADX WARN: Removed duplicated region for block: B:624:0x07be  */
    /* JADX WARN: Removed duplicated region for block: B:630:0x07d1  */
    /* JADX WARN: Removed duplicated region for block: B:638:0x0a12  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:653:0x0a4d  */
    /* JADX WARN: Removed duplicated region for block: B:658:0x0a69  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:731:0x092d  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:787:0x0771 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:811:0x0765  */
    /* JADX WARN: Removed duplicated region for block: B:814:0x074e  */
    /* JADX WARN: Removed duplicated region for block: B:818:0x0716  */
    /* JADX WARN: Removed duplicated region for block: B:832:0x070a  */
    /* JADX WARN: Removed duplicated region for block: B:838:0x059f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:871:0x0596  */
    /* JADX WARN: Removed duplicated region for block: B:872:0x0553 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:893:0x0544  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:914:0x0522  */
    /* JADX WARN: Removed duplicated region for block: B:921:0x0435  */
    /* JADX WARN: Removed duplicated region for block: B:924:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:925:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:926:0x0363  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:939:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:942:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:956:0x02bd  */
    /* JADX WARN: Removed duplicated region for block: B:959:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:967:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:976:0x00c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void performTraversals() {
        boolean z;
        boolean supportsScreen;
        boolean z2;
        WindowManager.LayoutParams params;
        int desiredWindowHeight;
        int desiredWindowWidth;
        boolean layoutRequested;
        Rect frame;
        int viewVisibility;
        WindowManager.LayoutParams lp;
        boolean cancelDraw;
        boolean cancelDraw2;
        int desiredWindowHeight2;
        int desiredWindowHeight3;
        boolean windowSizeMayChange;
        int resizeMode;
        WindowManager.LayoutParams lp2;
        WindowManager.LayoutParams lp3;
        WindowManager.LayoutParams lp4;
        boolean windowShouldResize;
        boolean computesInternalInsets;
        int surfaceGenerationId;
        boolean surfaceSizeChanged;
        boolean surfaceCreated;
        boolean surfaceReplaced;
        boolean insetsPending;
        int relayoutResult;
        WindowManager.LayoutParams params2;
        boolean updatedConfiguration;
        boolean layoutRequested2;
        boolean updatedConfiguration2;
        Rect frame2;
        int viewVisibility2;
        boolean insetsPending2;
        boolean cancelDraw3;
        boolean layoutRequested3;
        int childWidthMeasureSpec;
        String cancelReason;
        boolean didLayout;
        boolean triggerGlobalLayoutListener;
        boolean didUseTransaction;
        boolean computedInternalInsets;
        boolean isViewVisible;
        boolean changedVisibility;
        boolean cancelAndRedraw;
        boolean isCancelDraw;
        boolean isCancelDraw2;
        SurfaceSyncGroup surfaceSyncGroup;
        int i;
        Region region;
        BaseSurfaceHolder baseSurfaceHolder;
        boolean dispatchApplyInsets;
        Throwable th;
        long j;
        int relayoutResult2;
        int relayoutResult3;
        ThreadedRenderer threadedRenderer;
        boolean z3;
        boolean measureAgain;
        int childHeightMeasureSpec;
        int childWidthMeasureSpec2;
        int childHeightMeasureSpec2;
        boolean z4;
        boolean hwInitialized;
        boolean dispatchApplyInsets2;
        boolean hwInitialized2;
        boolean dragResizing;
        int i2;
        int relayoutResult4;
        boolean cancelDraw4;
        boolean alwaysConsumeSystemBarsChanged;
        boolean z5;
        boolean dispatchApplyInsets3;
        Surface.OutOfResourcesException e;
        boolean hwInitialized3;
        Surface.OutOfResourcesException e2;
        boolean windowSizeMayChange2;
        this.mLastPerformTraversalsSkipDrawReason = null;
        View host = this.mView;
        if (host == null || !this.mAdded) {
            this.mLastPerformTraversalsSkipDrawReason = host == null ? "no_host" : "not_added";
            return;
        }
        if (this.mNumPausedForSync > 0) {
            if (Trace.isTagEnabled(8L)) {
                Trace.instant(8L, TextUtils.formatSimple("performTraversals#mNumPausedForSync=%d", Integer.valueOf(this.mNumPausedForSync)));
            }
            if (DEBUG_BLAST) {
                Log.m94d(this.mTag, "Skipping traversal due to sync " + this.mNumPausedForSync);
            }
            this.mLastPerformTraversalsSkipDrawReason = "paused_for_sync";
            return;
        }
        this.mIsInTraversal = true;
        this.mWillDrawSoon = true;
        String cancelReason2 = null;
        boolean isSyncRequest = false;
        boolean windowSizeMayChange3 = false;
        WindowManager.LayoutParams lp5 = this.mWindowAttributes;
        int viewVisibility3 = getHostVisibility();
        boolean z6 = this.mFirst;
        boolean viewVisibilityChanged = !z6 && (this.mViewVisibility != viewVisibility3 || this.mNewSurfaceNeeded || this.mAppVisibilityChanged);
        this.mAppVisibilityChanged = false;
        if (!z6) {
            if ((this.mViewVisibility == 0) != (viewVisibility3 == 0)) {
                z = true;
                boolean viewUserVisibilityChanged = z;
                boolean shouldOptimizeMeasure = shouldOptimizeMeasure(lp5);
                CompatibilityInfo compatibilityInfo = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
                supportsScreen = compatibilityInfo.supportsScreen();
                z2 = this.mLastInCompatMode;
                if (supportsScreen != z2) {
                    this.mFullRedrawNeeded = true;
                    this.mLayoutRequested = true;
                    if (z2) {
                        lp5.privateFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
                        this.mLastInCompatMode = false;
                    } else {
                        lp5.privateFlags |= 128;
                        this.mLastInCompatMode = true;
                    }
                    params = lp5;
                } else {
                    params = null;
                }
                Rect frame3 = this.mWinFrame;
                if (this.mFirst) {
                    desiredWindowHeight = frame3.width();
                    desiredWindowWidth = frame3.height();
                    if (desiredWindowHeight != this.mWidth || desiredWindowWidth != this.mHeight) {
                        if (DEBUG_ORIENTATION) {
                            Log.m100v(this.mTag, "View " + host + " resized to: " + frame3);
                        }
                        this.mFullRedrawNeeded = true;
                        this.mLayoutRequested = true;
                        windowSizeMayChange3 = true;
                    }
                } else {
                    this.mFullRedrawNeeded = true;
                    this.mLayoutRequested = true;
                    Configuration config = getConfiguration();
                    if (shouldUseDisplaySize(lp5)) {
                        Point size = new Point();
                        this.mDisplay.getRealSize(size);
                        desiredWindowHeight = size.f85x;
                        desiredWindowWidth = size.f86y;
                    } else if (lp5.width == -2 || lp5.height == -2) {
                        Rect bounds = getWindowBoundsInsetSystemBars();
                        int desiredWindowWidth2 = bounds.width();
                        desiredWindowWidth = bounds.height();
                        desiredWindowHeight = desiredWindowWidth2;
                    } else {
                        desiredWindowHeight = frame3.width();
                        desiredWindowWidth = frame3.height();
                    }
                    this.mAttachInfo.mUse32BitDrawingCache = true;
                    this.mAttachInfo.mHasWindowFocus = this.mEarlyHasWindowFocus;
                    this.mAttachInfo.mWindowVisibility = viewVisibility3;
                    this.mAttachInfo.mRecomputeGlobalAttributes = false;
                    this.mLastConfigurationFromResources.setTo(config);
                    this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                    if (this.mViewLayoutDirectionInitial == 2) {
                        host.setLayoutDirection(config.getLayoutDirection());
                    }
                    host.dispatchAttachedToWindow(this.mAttachInfo, 0);
                    this.mAttachInfo.mTreeObserver.dispatchOnWindowAttachedChange(true);
                    dispatchApplyInsets(host);
                    if (!this.mOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled()) {
                        registerCompatOnBackInvokedCallback();
                    }
                }
                if (viewVisibilityChanged) {
                    this.mAttachInfo.mWindowVisibility = viewVisibility3;
                    host.dispatchWindowVisibilityChanged(viewVisibility3);
                    this.mAttachInfo.mTreeObserver.dispatchOnWindowVisibilityChange(viewVisibility3);
                    if (viewUserVisibilityChanged) {
                        host.dispatchVisibilityAggregated(viewVisibility3 == 0);
                    }
                    if (viewVisibility3 != 0 || this.mNewSurfaceNeeded) {
                        endDragResizing();
                        destroyHardwareResources();
                    }
                }
                if (this.mAttachInfo.mWindowVisibility != 0) {
                    host.clearAccessibilityFocus();
                }
                getRunQueue().executeActions(this.mAttachInfo.mHandler);
                if (this.mFirst) {
                    this.mAttachInfo.mInTouchMode = !this.mAddedTouchMode;
                    ensureTouchModeLocally(this.mAddedTouchMode);
                }
                layoutRequested = !this.mLayoutRequested && (!this.mStopped || this.mReportNextDraw);
                if (layoutRequested) {
                    frame = frame3;
                    viewVisibility = viewVisibility3;
                    lp = lp5;
                    cancelDraw = false;
                    cancelDraw2 = false;
                    desiredWindowHeight2 = desiredWindowWidth;
                    desiredWindowHeight3 = desiredWindowHeight;
                    windowSizeMayChange = windowSizeMayChange3;
                } else {
                    if (this.mFirst || !(lp5.width == -2 || lp5.height == -2)) {
                        desiredWindowHeight2 = desiredWindowWidth;
                        desiredWindowHeight3 = desiredWindowHeight;
                        windowSizeMayChange2 = windowSizeMayChange3;
                    } else if (shouldUseDisplaySize(lp5)) {
                        Point size2 = new Point();
                        this.mDisplay.getRealSize(size2);
                        int desiredWindowWidth3 = size2.f85x;
                        desiredWindowHeight2 = size2.f86y;
                        desiredWindowHeight3 = desiredWindowWidth3;
                        windowSizeMayChange2 = true;
                    } else {
                        Rect bounds2 = getWindowBoundsInsetSystemBars();
                        int desiredWindowWidth4 = bounds2.width();
                        desiredWindowHeight2 = bounds2.height();
                        desiredWindowHeight3 = desiredWindowWidth4;
                        windowSizeMayChange2 = true;
                    }
                    cancelDraw = false;
                    frame = frame3;
                    cancelDraw2 = false;
                    viewVisibility = viewVisibility3;
                    lp = lp5;
                    windowSizeMayChange = measureHierarchy(host, lp5, this.mView.getContext().getResources(), desiredWindowHeight3, desiredWindowHeight2, shouldOptimizeMeasure) | windowSizeMayChange2;
                }
                if (collectViewAttributes()) {
                    params = lp;
                }
                if (this.mAttachInfo.mForceReportNewAttributes) {
                    this.mAttachInfo.mForceReportNewAttributes = cancelDraw2;
                    params = lp;
                }
                if (!this.mFirst || this.mAttachInfo.mViewVisibilityChanged) {
                    this.mAttachInfo.mViewVisibilityChanged = cancelDraw2;
                    resizeMode = this.mSoftInputMode & 240;
                    if (resizeMode != 0) {
                        int N = this.mAttachInfo.mScrollContainers.size();
                        for (int i3 = 0; i3 < N; i3++) {
                            if (this.mAttachInfo.mScrollContainers.get(i3).isShown()) {
                                resizeMode = 16;
                            }
                        }
                        if (resizeMode == 0) {
                            resizeMode = 32;
                        }
                        lp2 = lp;
                        if ((lp2.softInputMode & 240) != resizeMode) {
                            lp2.softInputMode = (lp2.softInputMode & (-241)) | resizeMode;
                            params = lp2;
                        }
                    } else {
                        lp2 = lp;
                    }
                } else {
                    lp2 = lp;
                }
                if (this.mApplyInsetsRequested) {
                    lp3 = lp2;
                } else {
                    dispatchApplyInsets(host);
                    if (this.mLayoutRequested) {
                        lp3 = lp2;
                        windowSizeMayChange |= measureHierarchy(host, lp2, this.mView.getContext().getResources(), desiredWindowHeight3, desiredWindowHeight2, shouldOptimizeMeasure);
                    } else {
                        lp3 = lp2;
                    }
                }
                if (layoutRequested) {
                    this.mLayoutRequested = cancelDraw2;
                }
                if (layoutRequested || !windowSizeMayChange) {
                    lp4 = lp3;
                } else {
                    if (this.mWidth == host.getMeasuredWidth() && this.mHeight == host.getMeasuredHeight()) {
                        lp4 = lp3;
                        if (lp4.width == -2) {
                            if (frame.width() < desiredWindowHeight3) {
                            }
                        }
                        if (lp4.height == -2) {
                            if (frame.height() < desiredWindowHeight2) {
                            }
                        }
                    } else {
                        lp4 = lp3;
                    }
                    windowShouldResize = true;
                    boolean windowShouldResize2 = windowShouldResize | ((this.mDragResizing || !this.mPendingDragResizing) ? cancelDraw2 : true);
                    computesInternalInsets = (!this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners() || this.mAttachInfo.mHasNonEmptyGivenInternalInsets) ? true : cancelDraw2;
                    surfaceGenerationId = this.mSurface.getGenerationId();
                    int viewVisibility4 = viewVisibility;
                    boolean isViewVisible2 = viewVisibility4 == 0;
                    surfaceSizeChanged = false;
                    surfaceCreated = false;
                    boolean surfaceDestroyed = false;
                    surfaceReplaced = false;
                    insetsPending = this.mWindowAttributesChanged;
                    if (insetsPending) {
                        relayoutResult = 0;
                        this.mWindowAttributesChanged = false;
                        WindowManager.LayoutParams params3 = lp4;
                        params2 = params3;
                    } else {
                        relayoutResult = 0;
                        params2 = params;
                    }
                    if (params2 != null) {
                        updatedConfiguration = false;
                        if ((host.mPrivateFlags & 512) != 0 && !PixelFormat.formatHasAlpha(params2.format)) {
                            params2.format = -3;
                        }
                        adjustLayoutParamsForCompatibility(params2);
                        controlInsetsForCompatibility(params2);
                        layoutRequested2 = layoutRequested;
                        if (this.mDispatchedSystemBarAppearance != params2.insetsFlags.appearance) {
                            int i4 = params2.insetsFlags.appearance;
                            this.mDispatchedSystemBarAppearance = i4;
                            this.mView.onSystemBarAppearanceChanged(i4);
                        }
                    } else {
                        updatedConfiguration = false;
                        layoutRequested2 = layoutRequested;
                    }
                    updatedConfiguration2 = this.mFirst;
                    if (!updatedConfiguration2 || windowShouldResize2 || viewVisibilityChanged || params2 != null) {
                        frame2 = frame;
                    } else if (this.mForceNextWindowRelayout) {
                        frame2 = frame;
                    } else {
                        frame2 = frame;
                        maybeHandleWindowMove(frame2);
                        viewVisibility2 = viewVisibility4;
                        cancelDraw3 = cancelDraw;
                        insetsPending2 = false;
                        childWidthMeasureSpec = relayoutResult;
                        layoutRequested3 = layoutRequested2;
                        if (this.mViewMeasureDeferred) {
                            performMeasure(View.MeasureSpec.makeMeasureSpec(frame2.width(), 1073741824), View.MeasureSpec.makeMeasureSpec(frame2.height(), 1073741824));
                        }
                        if (this.mRelayoutRequested && this.mCheckIfCanDraw) {
                            try {
                                cancelDraw3 = this.mWindowSession.cancelDraw(this.mWindow);
                                cancelReason2 = "wm_sync";
                                if (DEBUG_BLAST) {
                                    Log.m94d(this.mTag, "cancelDraw returned " + cancelDraw3);
                                }
                                cancelReason = cancelReason2;
                            } catch (RemoteException e3) {
                                cancelReason = cancelReason2;
                            }
                        } else {
                            cancelReason = cancelReason2;
                        }
                        if (!surfaceSizeChanged || surfaceReplaced || surfaceCreated || insetsPending || this.mChildBoundingInsetsChanged || this.mForceUpdateBoundsLayer) {
                            prepareSurfaces();
                            this.mChildBoundingInsetsChanged = false;
                            this.mForceUpdateBoundsLayer = false;
                            this.mFullRedrawNeeded = true;
                        }
                        didLayout = !layoutRequested3 && (!this.mStopped || this.mReportNextDraw);
                        triggerGlobalLayoutListener = !didLayout || this.mAttachInfo.mRecomputeGlobalAttributes;
                        if (didLayout) {
                            performLayout(lp4, this.mWidth, this.mHeight);
                            if ((host.mPrivateFlags & 512) != 0) {
                                host.getLocationInWindow(this.mTmpLocation);
                                Region region2 = this.mTransparentRegion;
                                int[] iArr = this.mTmpLocation;
                                int i5 = iArr[0];
                                region2.set(i5, iArr[1], (host.mRight + i5) - host.mLeft, (this.mTmpLocation[1] + host.mBottom) - host.mTop);
                                host.gatherTransparentRegion(this.mTransparentRegion);
                                if (this.mWindowAttributes.surfaceInsets.left > 0 || this.mWindowAttributes.surfaceInsets.top > 0) {
                                    this.mTransparentRegion.translate(this.mWindowAttributes.surfaceInsets.left, this.mWindowAttributes.surfaceInsets.top);
                                }
                                CompatibilityInfo.Translator translator = this.mTranslator;
                                if (translator != null) {
                                    translator.translateRegionInWindowToScreen(this.mTransparentRegion);
                                }
                                if (!this.mTransparentRegion.equals(this.mPreviousTransparentRegion)) {
                                    this.mPreviousTransparentRegion.set(this.mTransparentRegion);
                                    this.mFullRedrawNeeded = true;
                                    SurfaceControl sc = getSurfaceControl();
                                    if (sc.isValid()) {
                                        this.mTransaction.setTransparentRegionHint(sc, this.mTransparentRegion).apply();
                                    }
                                }
                            }
                        }
                        didUseTransaction = false;
                        if (surfaceCreated) {
                            notifySurfaceCreated(this.mTransaction);
                            didUseTransaction = true;
                        } else if (surfaceReplaced) {
                            notifySurfaceReplaced(this.mTransaction);
                            didUseTransaction = true;
                        } else if (surfaceDestroyed) {
                            notifySurfaceDestroyed();
                        }
                        if (didUseTransaction) {
                            applyTransactionOnDraw(this.mTransaction);
                        }
                        if (triggerGlobalLayoutListener) {
                            this.mAttachInfo.mRecomputeGlobalAttributes = false;
                            this.mAttachInfo.mTreeObserver.dispatchOnGlobalLayout();
                        }
                        Rect contentInsets = null;
                        Rect visibleInsets = null;
                        Region touchableRegion = null;
                        int touchableInsetMode = 3;
                        computedInternalInsets = false;
                        if (computesInternalInsets) {
                            ViewTreeObserver.InternalInsetsInfo insets = this.mAttachInfo.mGivenInternalInsets;
                            insets.reset();
                            this.mAttachInfo.mTreeObserver.dispatchOnComputeInternalInsets(insets);
                            this.mAttachInfo.mHasNonEmptyGivenInternalInsets = !insets.isEmpty();
                            if (insetsPending2 || !this.mLastGivenInsets.equals(insets)) {
                                this.mLastGivenInsets.set(insets);
                                CompatibilityInfo.Translator translator2 = this.mTranslator;
                                if (translator2 != null) {
                                    Rect contentInsets2 = translator2.getTranslatedContentInsets(insets.contentInsets);
                                    CompatibilityInfo.Translator translator3 = this.mTranslator;
                                    Rect contentInsets3 = insets.visibleInsets;
                                    visibleInsets = translator3.getTranslatedVisibleInsets(contentInsets3);
                                    touchableRegion = this.mTranslator.getTranslatedTouchableArea(insets.touchableRegion);
                                    contentInsets = contentInsets2;
                                } else {
                                    Rect visibleInsets2 = insets.contentInsets;
                                    Rect visibleInsets3 = insets.visibleInsets;
                                    touchableRegion = insets.touchableRegion;
                                    visibleInsets = visibleInsets3;
                                    contentInsets = visibleInsets2;
                                }
                                computedInternalInsets = true;
                            } else {
                                contentInsets = null;
                            }
                            touchableInsetMode = insets.mTouchableInsets;
                        }
                        if (computedInternalInsets || (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                            if (this.mTouchableRegion != null) {
                                if (this.mPreviousTouchableRegion == null) {
                                    this.mPreviousTouchableRegion = new Region();
                                }
                                this.mPreviousTouchableRegion.set(this.mTouchableRegion);
                                if (touchableInsetMode != 3) {
                                    Log.m96e(this.mTag, "Setting touchableInsetMode to non TOUCHABLE_INSETS_REGION from OnComputeInternalInsets, while also using setTouchableRegion causes setTouchableRegion to be ignored");
                                }
                            } else {
                                this.mPreviousTouchableRegion = null;
                            }
                            if (contentInsets == null) {
                                i = 0;
                                contentInsets = new Rect(0, 0, 0, 0);
                            } else {
                                i = 0;
                            }
                            if (visibleInsets == null) {
                                visibleInsets = new Rect(i, i, i, i);
                            }
                            if (touchableRegion == null) {
                                touchableRegion = this.mTouchableRegion;
                            } else if (touchableRegion != null && (region = this.mTouchableRegion) != null) {
                                touchableRegion.m24op(touchableRegion, region, Region.EnumC0836Op.UNION);
                            }
                            try {
                                this.mWindowSession.setInsets(this.mWindow, touchableInsetMode, contentInsets, visibleInsets, touchableRegion);
                            } catch (RemoteException e4) {
                                throw e4.rethrowFromSystemServer();
                            }
                        } else if (this.mTouchableRegion == null && this.mPreviousTouchableRegion != null) {
                            this.mPreviousTouchableRegion = null;
                            try {
                                this.mWindowSession.clearTouchableRegion(this.mWindow);
                            } catch (RemoteException e5) {
                                throw e5.rethrowFromSystemServer();
                            }
                        }
                        if (this.mFirst) {
                            if (!sAlwaysAssignFocus && isInTouchMode()) {
                                View focused = this.mView.findFocus();
                                if ((focused instanceof ViewGroup) && ((ViewGroup) focused).getDescendantFocusability() == 262144) {
                                    focused.restoreDefaultFocus();
                                }
                            }
                            boolean needsSetInsets = DEBUG_INPUT_RESIZE;
                            if (needsSetInsets) {
                                Log.m100v(this.mTag, "First: mView.hasFocus()=" + this.mView.hasFocus());
                            }
                            View view = this.mView;
                            if (view != null) {
                                if (!view.hasFocus()) {
                                    this.mView.restoreDefaultFocus();
                                    if (needsSetInsets) {
                                        Log.m100v(this.mTag, "First: requested focused view=" + this.mView.findFocus());
                                    }
                                } else if (needsSetInsets) {
                                    Log.m100v(this.mTag, "First: existing focused view=" + this.mView.findFocus());
                                }
                            }
                        }
                        if (!viewVisibilityChanged || this.mFirst) {
                            isViewVisible = isViewVisible2;
                            if (isViewVisible) {
                                changedVisibility = true;
                                if (changedVisibility) {
                                    maybeFireAccessibilityWindowStateChangedEvent();
                                }
                                this.mFirst = false;
                                this.mWillDrawSoon = false;
                                this.mNewSurfaceNeeded = false;
                                this.mViewVisibility = viewVisibility2;
                                boolean hasWindowFocus = !this.mAttachInfo.mHasWindowFocus && isViewVisible;
                                this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                                if ((childWidthMeasureSpec & 1) != 0) {
                                    reportNextDraw("first_relayout");
                                }
                                this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                                boolean cancelDueToPreDrawListener = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                cancelAndRedraw = !cancelDueToPreDrawListener || (cancelDraw3 && this.mDrewOnceForSync);
                                if (!cancelAndRedraw) {
                                    if (this.mActiveSurfaceSyncGroup != null) {
                                        this.mSyncBuffer = true;
                                    }
                                    createSyncIfNeeded();
                                    notifyDrawStarted(isInWMSRequestedSync());
                                    this.mDrewOnceForSync = true;
                                    SurfaceSyncGroup surfaceSyncGroup2 = this.mActiveSurfaceSyncGroup;
                                    if (surfaceSyncGroup2 != null && this.mSyncBuffer) {
                                        updateSyncInProgressCount(surfaceSyncGroup2);
                                        safeguardOverlappingSyncs(this.mActiveSurfaceSyncGroup);
                                    }
                                }
                                boolean z7 = true;
                                isCancelDraw = z7;
                                if (isCancelDraw && cancelAndRedraw && (CoreRune.SAFE_DEBUG || CoreRune.IS_DEBUG_LEVEL_MID || CoreRune.IS_DEBUG_LEVEL_HIGH)) {
                                    Log.m98i(this.mTag, "cancelAndRedraw " + this.mAttachInfo.mTreeObserver.mLog + " isViewVisible: " + isViewVisible);
                                }
                                isCancelDraw2 = DEBUG_TRAVERSAL;
                                if (isCancelDraw2 && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
                                    Log.m98i(this.mTag, "Traversal, [11] mView=" + this.mView + " cancelAndRedraw=" + cancelAndRedraw);
                                }
                                if (isViewVisible) {
                                    this.mLastPerformTraversalsSkipDrawReason = "view_not_visible";
                                    ArrayList<LayoutTransition> arrayList = this.mPendingTransitions;
                                    if (arrayList != null && arrayList.size() > 0) {
                                        for (int i6 = 0; i6 < this.mPendingTransitions.size(); i6++) {
                                            this.mPendingTransitions.get(i6).endChangingAnimations();
                                        }
                                        this.mPendingTransitions.clear();
                                    }
                                    SurfaceSyncGroup surfaceSyncGroup3 = this.mActiveSurfaceSyncGroup;
                                    if (surfaceSyncGroup3 != null) {
                                        surfaceSyncGroup3.markSyncReady();
                                    }
                                } else if (cancelAndRedraw) {
                                    this.mLastPerformTraversalsSkipDrawReason = cancelDueToPreDrawListener ? "predraw_" + this.mAttachInfo.mTreeObserver.getLastDispatchOnPreDrawCanceledReason() : "cancel_" + cancelReason;
                                    scheduleTraversals();
                                } else {
                                    prepareCanvasBlur();
                                    ArrayList<LayoutTransition> arrayList2 = this.mPendingTransitions;
                                    if (arrayList2 != null && arrayList2.size() > 0) {
                                        for (int i7 = 0; i7 < this.mPendingTransitions.size(); i7++) {
                                            this.mPendingTransitions.get(i7).startChangingAnimations();
                                        }
                                        this.mPendingTransitions.clear();
                                    }
                                    if (!performDraw(this.mActiveSurfaceSyncGroup) && (surfaceSyncGroup = this.mActiveSurfaceSyncGroup) != null) {
                                        surfaceSyncGroup.markSyncReady();
                                    }
                                }
                                if (this.mAttachInfo.mContentCaptureEvents != null) {
                                    notifyContentCaptureEvents();
                                }
                                this.mIsInTraversal = false;
                                this.mRelayoutRequested = false;
                                if (cancelAndRedraw) {
                                    this.mReportNextDraw = false;
                                    this.mLastReportNextDrawReason = null;
                                    this.mActiveSurfaceSyncGroup = null;
                                    this.mSyncBuffer = false;
                                    if (isInWMSRequestedSync()) {
                                        this.mWmsRequestSyncGroup.markSyncReady();
                                        this.mWmsRequestSyncGroup = null;
                                        this.mWmsRequestSyncGroupState = 0;
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                        } else {
                            isViewVisible = isViewVisible2;
                        }
                        changedVisibility = false;
                        if (changedVisibility) {
                        }
                        this.mFirst = false;
                        this.mWillDrawSoon = false;
                        this.mNewSurfaceNeeded = false;
                        this.mViewVisibility = viewVisibility2;
                        if (this.mAttachInfo.mHasWindowFocus) {
                        }
                        this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                        if ((childWidthMeasureSpec & 1) != 0) {
                        }
                        this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                        boolean cancelDueToPreDrawListener2 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                        if (cancelDueToPreDrawListener2) {
                        }
                        if (!cancelAndRedraw) {
                        }
                        boolean z72 = true;
                        isCancelDraw = z72;
                        if (isCancelDraw) {
                            Log.m98i(this.mTag, "cancelAndRedraw " + this.mAttachInfo.mTreeObserver.mLog + " isViewVisible: " + isViewVisible);
                        }
                        isCancelDraw2 = DEBUG_TRAVERSAL;
                        if (isCancelDraw2) {
                            Log.m98i(this.mTag, "Traversal, [11] mView=" + this.mView + " cancelAndRedraw=" + cancelAndRedraw);
                        }
                        if (isViewVisible) {
                        }
                        if (this.mAttachInfo.mContentCaptureEvents != null) {
                        }
                        this.mIsInTraversal = false;
                        this.mRelayoutRequested = false;
                        if (cancelAndRedraw) {
                        }
                    }
                    if (Trace.isTagEnabled(8L)) {
                        Object[] objArr = new Object[5];
                        objArr[0] = Boolean.valueOf(this.mFirst);
                        objArr[1] = Boolean.valueOf(windowShouldResize2);
                        objArr[2] = Boolean.valueOf(viewVisibilityChanged);
                        objArr[3] = Boolean.valueOf(params2 != null);
                        objArr[4] = Boolean.valueOf(this.mForceNextWindowRelayout);
                        Trace.traceBegin(8L, TextUtils.formatSimple("relayoutWindow#first=%b/resize=%b/vis=%b/params=%b/force=%b", objArr));
                    }
                    this.mForceNextWindowRelayout = false;
                    baseSurfaceHolder = this.mSurfaceHolder;
                    if (baseSurfaceHolder != null) {
                        baseSurfaceHolder.mSurfaceLock.lock();
                        this.mDrawingAllowed = true;
                    }
                    boolean hwInitialized4 = false;
                    dispatchApplyInsets = false;
                    boolean hadSurface = this.mSurface.isValid();
                    z4 = DEBUG_LAYOUT;
                    if (z4) {
                        hwInitialized = false;
                        try {
                            dispatchApplyInsets2 = false;
                            try {
                                try {
                                    Log.m98i(this.mTag, "host=w:" + host.getMeasuredWidth() + ", h:" + host.getMeasuredHeight() + ", params=" + params2);
                                } catch (RemoteException e6) {
                                    insetsPending2 = computesInternalInsets;
                                    viewVisibility2 = viewVisibility4;
                                    cancelDraw3 = cancelDraw;
                                    relayoutResult2 = relayoutResult;
                                    hwInitialized4 = false;
                                    dispatchApplyInsets = false;
                                    j = 8;
                                    if (Trace.isTagEnabled(j)) {
                                        Trace.traceEnd(j);
                                    }
                                    if (DEBUG_ORIENTATION) {
                                    }
                                    this.mAttachInfo.mWindowLeft = frame2.left;
                                    this.mAttachInfo.mWindowTop = frame2.top;
                                    if (this.mWidth == frame2.width()) {
                                    }
                                    this.mWidth = frame2.width();
                                    this.mHeight = frame2.height();
                                    if (this.mSurfaceHolder == null) {
                                    }
                                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                    if (threadedRenderer != null) {
                                    }
                                    if (this.mStopped) {
                                    }
                                    int childWidthMeasureSpec3 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                                    int childHeightMeasureSpec3 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                                    z3 = DEBUG_LAYOUT;
                                    if (!z3) {
                                    }
                                    performMeasure(childWidthMeasureSpec3, childHeightMeasureSpec3);
                                    int width = host.getMeasuredWidth();
                                    int height = host.getMeasuredHeight();
                                    measureAgain = false;
                                    if (lp4.horizontalWeight <= 0.0f) {
                                    }
                                    if (lp4.verticalWeight <= 0.0f) {
                                    }
                                    if (measureAgain) {
                                    }
                                    layoutRequested3 = true;
                                    childWidthMeasureSpec = relayoutResult3;
                                    if (this.mViewMeasureDeferred) {
                                    }
                                    if (this.mRelayoutRequested) {
                                    }
                                    cancelReason = cancelReason2;
                                    if (!surfaceSizeChanged) {
                                    }
                                    prepareSurfaces();
                                    this.mChildBoundingInsetsChanged = false;
                                    this.mForceUpdateBoundsLayer = false;
                                    this.mFullRedrawNeeded = true;
                                    if (layoutRequested3) {
                                    }
                                    if (didLayout) {
                                    }
                                    if (didLayout) {
                                    }
                                    didUseTransaction = false;
                                    if (surfaceCreated) {
                                    }
                                    if (didUseTransaction) {
                                    }
                                    if (triggerGlobalLayoutListener) {
                                    }
                                    Rect contentInsets4 = null;
                                    Rect visibleInsets4 = null;
                                    Region touchableRegion2 = null;
                                    int touchableInsetMode2 = 3;
                                    computedInternalInsets = false;
                                    if (computesInternalInsets) {
                                    }
                                    if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                                    }
                                    if (this.mFirst) {
                                    }
                                    if (viewVisibilityChanged) {
                                    }
                                    isViewVisible = isViewVisible2;
                                    if (isViewVisible) {
                                    }
                                    changedVisibility = false;
                                    if (changedVisibility) {
                                    }
                                    this.mFirst = false;
                                    this.mWillDrawSoon = false;
                                    this.mNewSurfaceNeeded = false;
                                    this.mViewVisibility = viewVisibility2;
                                    if (this.mAttachInfo.mHasWindowFocus) {
                                    }
                                    this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                                    if ((childWidthMeasureSpec & 1) != 0) {
                                    }
                                    this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                                    boolean cancelDueToPreDrawListener22 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                    if (cancelDueToPreDrawListener22) {
                                    }
                                    if (!cancelAndRedraw) {
                                    }
                                    boolean z722 = true;
                                    isCancelDraw = z722;
                                    if (isCancelDraw) {
                                    }
                                    isCancelDraw2 = DEBUG_TRAVERSAL;
                                    if (isCancelDraw2) {
                                    }
                                    if (isViewVisible) {
                                    }
                                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                                    }
                                    this.mIsInTraversal = false;
                                    this.mRelayoutRequested = false;
                                    if (cancelAndRedraw) {
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (!Trace.isTagEnabled(8L)) {
                                        throw th;
                                    }
                                    Trace.traceEnd(8L);
                                    throw th;
                                }
                            } catch (RemoteException e7) {
                                insetsPending2 = computesInternalInsets;
                                viewVisibility2 = viewVisibility4;
                                cancelDraw3 = cancelDraw;
                                relayoutResult2 = relayoutResult;
                                hwInitialized4 = false;
                                dispatchApplyInsets = false;
                                j = 8;
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        } catch (RemoteException e8) {
                            insetsPending2 = computesInternalInsets;
                            viewVisibility2 = viewVisibility4;
                            cancelDraw3 = cancelDraw;
                            relayoutResult2 = relayoutResult;
                            hwInitialized4 = false;
                            j = 8;
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    } else {
                        hwInitialized = false;
                        dispatchApplyInsets2 = false;
                    }
                    hwInitialized2 = this.mFirst;
                    if (!hwInitialized2 || viewVisibilityChanged) {
                        this.mViewFrameInfo.flags |= 1;
                    }
                    int relayoutResult5 = relayoutWindow(params2, viewVisibility4, computesInternalInsets);
                    boolean cancelDraw5 = (relayoutResult5 & 16) == 16;
                    cancelReason2 = "relayout";
                    dragResizing = this.mPendingDragResizing;
                    i2 = this.mSyncSeqId;
                    insetsPending2 = computesInternalInsets;
                    if (i2 > this.mLastSyncSeqId) {
                        try {
                            this.mLastSyncSeqId = i2;
                            if (DEBUG_BLAST) {
                                Log.m94d(this.mTag, "Relayout called with blastSync");
                            }
                            reportNextDraw("relayout");
                            this.mSyncBuffer = true;
                            isSyncRequest = true;
                            if (!cancelDraw5) {
                                this.mDrewOnceForSync = false;
                            }
                        } catch (RemoteException e9) {
                            relayoutResult2 = relayoutResult5;
                            viewVisibility2 = viewVisibility4;
                            hwInitialized4 = hwInitialized;
                            j = 8;
                            cancelDraw3 = cancelDraw5;
                            dispatchApplyInsets = dispatchApplyInsets2;
                            if (Trace.isTagEnabled(j)) {
                            }
                            if (DEBUG_ORIENTATION) {
                            }
                            this.mAttachInfo.mWindowLeft = frame2.left;
                            this.mAttachInfo.mWindowTop = frame2.top;
                            if (this.mWidth == frame2.width()) {
                            }
                            this.mWidth = frame2.width();
                            this.mHeight = frame2.height();
                            if (this.mSurfaceHolder == null) {
                            }
                            threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                            if (threadedRenderer != null) {
                            }
                            if (this.mStopped) {
                            }
                            int childWidthMeasureSpec32 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                            int childHeightMeasureSpec32 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                            z3 = DEBUG_LAYOUT;
                            if (!z3) {
                            }
                            performMeasure(childWidthMeasureSpec32, childHeightMeasureSpec32);
                            int width2 = host.getMeasuredWidth();
                            int height2 = host.getMeasuredHeight();
                            measureAgain = false;
                            if (lp4.horizontalWeight <= 0.0f) {
                            }
                            if (lp4.verticalWeight <= 0.0f) {
                            }
                            if (measureAgain) {
                            }
                            layoutRequested3 = true;
                            childWidthMeasureSpec = relayoutResult3;
                            if (this.mViewMeasureDeferred) {
                            }
                            if (this.mRelayoutRequested) {
                            }
                            cancelReason = cancelReason2;
                            if (!surfaceSizeChanged) {
                            }
                            prepareSurfaces();
                            this.mChildBoundingInsetsChanged = false;
                            this.mForceUpdateBoundsLayer = false;
                            this.mFullRedrawNeeded = true;
                            if (layoutRequested3) {
                            }
                            if (didLayout) {
                            }
                            if (didLayout) {
                            }
                            didUseTransaction = false;
                            if (surfaceCreated) {
                            }
                            if (didUseTransaction) {
                            }
                            if (triggerGlobalLayoutListener) {
                            }
                            Rect contentInsets42 = null;
                            Rect visibleInsets42 = null;
                            Region touchableRegion22 = null;
                            int touchableInsetMode22 = 3;
                            computedInternalInsets = false;
                            if (computesInternalInsets) {
                            }
                            if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                            }
                            if (this.mFirst) {
                            }
                            if (viewVisibilityChanged) {
                            }
                            isViewVisible = isViewVisible2;
                            if (isViewVisible) {
                            }
                            changedVisibility = false;
                            if (changedVisibility) {
                            }
                            this.mFirst = false;
                            this.mWillDrawSoon = false;
                            this.mNewSurfaceNeeded = false;
                            this.mViewVisibility = viewVisibility2;
                            if (this.mAttachInfo.mHasWindowFocus) {
                            }
                            this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                            if ((childWidthMeasureSpec & 1) != 0) {
                            }
                            this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                            boolean cancelDueToPreDrawListener222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                            if (cancelDueToPreDrawListener222) {
                            }
                            if (!cancelAndRedraw) {
                            }
                            boolean z7222 = true;
                            isCancelDraw = z7222;
                            if (isCancelDraw) {
                            }
                            isCancelDraw2 = DEBUG_TRAVERSAL;
                            if (isCancelDraw2) {
                            }
                            if (isViewVisible) {
                            }
                            if (this.mAttachInfo.mContentCaptureEvents != null) {
                            }
                            this.mIsInTraversal = false;
                            this.mRelayoutRequested = false;
                            if (cancelAndRedraw) {
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            if (!Trace.isTagEnabled(8L)) {
                            }
                        }
                    }
                    boolean surfaceControlChanged = (relayoutResult5 & 2) == 2;
                    if (this.mSurfaceControl.isValid()) {
                        try {
                            updateOpacity(this.mWindowAttributes, dragResizing, surfaceControlChanged);
                            if (surfaceControlChanged && this.mDisplayDecorationCached) {
                                updateDisplayDecoration();
                            }
                            if (surfaceControlChanged && this.mSkipScreenshot) {
                                updateSkipScreenshot();
                            }
                            if (surfaceControlChanged && this.mDisableSuperHdr) {
                                updateDisableSuperHdr();
                            }
                            if (surfaceControlChanged && this.mWindowAttributes.type == 2000) {
                                relayoutResult4 = relayoutResult5;
                                try {
                                    this.mTransaction.setDefaultFrameRateCompatibility(this.mSurfaceControl, 101).apply();
                                } catch (RemoteException e10) {
                                    viewVisibility2 = viewVisibility4;
                                    relayoutResult2 = relayoutResult4;
                                    hwInitialized4 = hwInitialized;
                                    j = 8;
                                    cancelDraw3 = cancelDraw5;
                                    dispatchApplyInsets = dispatchApplyInsets2;
                                    if (Trace.isTagEnabled(j)) {
                                    }
                                    if (DEBUG_ORIENTATION) {
                                    }
                                    this.mAttachInfo.mWindowLeft = frame2.left;
                                    this.mAttachInfo.mWindowTop = frame2.top;
                                    if (this.mWidth == frame2.width()) {
                                    }
                                    this.mWidth = frame2.width();
                                    this.mHeight = frame2.height();
                                    if (this.mSurfaceHolder == null) {
                                    }
                                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                    if (threadedRenderer != null) {
                                    }
                                    if (this.mStopped) {
                                    }
                                    int childWidthMeasureSpec322 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                                    int childHeightMeasureSpec322 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                                    z3 = DEBUG_LAYOUT;
                                    if (!z3) {
                                    }
                                    performMeasure(childWidthMeasureSpec322, childHeightMeasureSpec322);
                                    int width22 = host.getMeasuredWidth();
                                    int height22 = host.getMeasuredHeight();
                                    measureAgain = false;
                                    if (lp4.horizontalWeight <= 0.0f) {
                                    }
                                    if (lp4.verticalWeight <= 0.0f) {
                                    }
                                    if (measureAgain) {
                                    }
                                    layoutRequested3 = true;
                                    childWidthMeasureSpec = relayoutResult3;
                                    if (this.mViewMeasureDeferred) {
                                    }
                                    if (this.mRelayoutRequested) {
                                    }
                                    cancelReason = cancelReason2;
                                    if (!surfaceSizeChanged) {
                                    }
                                    prepareSurfaces();
                                    this.mChildBoundingInsetsChanged = false;
                                    this.mForceUpdateBoundsLayer = false;
                                    this.mFullRedrawNeeded = true;
                                    if (layoutRequested3) {
                                    }
                                    if (didLayout) {
                                    }
                                    if (didLayout) {
                                    }
                                    didUseTransaction = false;
                                    if (surfaceCreated) {
                                    }
                                    if (didUseTransaction) {
                                    }
                                    if (triggerGlobalLayoutListener) {
                                    }
                                    Rect contentInsets422 = null;
                                    Rect visibleInsets422 = null;
                                    Region touchableRegion222 = null;
                                    int touchableInsetMode222 = 3;
                                    computedInternalInsets = false;
                                    if (computesInternalInsets) {
                                    }
                                    if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                                    }
                                    if (this.mFirst) {
                                    }
                                    if (viewVisibilityChanged) {
                                    }
                                    isViewVisible = isViewVisible2;
                                    if (isViewVisible) {
                                    }
                                    changedVisibility = false;
                                    if (changedVisibility) {
                                    }
                                    this.mFirst = false;
                                    this.mWillDrawSoon = false;
                                    this.mNewSurfaceNeeded = false;
                                    this.mViewVisibility = viewVisibility2;
                                    if (this.mAttachInfo.mHasWindowFocus) {
                                    }
                                    this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                                    if ((childWidthMeasureSpec & 1) != 0) {
                                    }
                                    this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                                    boolean cancelDueToPreDrawListener2222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                    if (cancelDueToPreDrawListener2222) {
                                    }
                                    if (!cancelAndRedraw) {
                                    }
                                    boolean z72222 = true;
                                    isCancelDraw = z72222;
                                    if (isCancelDraw) {
                                    }
                                    isCancelDraw2 = DEBUG_TRAVERSAL;
                                    if (isCancelDraw2) {
                                    }
                                    if (isViewVisible) {
                                    }
                                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                                    }
                                    this.mIsInTraversal = false;
                                    this.mRelayoutRequested = false;
                                    if (cancelAndRedraw) {
                                    }
                                } catch (Throwable th6) {
                                    th = th6;
                                    if (!Trace.isTagEnabled(8L)) {
                                    }
                                }
                            } else {
                                relayoutResult4 = relayoutResult5;
                            }
                        } catch (RemoteException e11) {
                            viewVisibility2 = viewVisibility4;
                            relayoutResult2 = relayoutResult5;
                            hwInitialized4 = hwInitialized;
                            j = 8;
                            cancelDraw3 = cancelDraw5;
                            dispatchApplyInsets = dispatchApplyInsets2;
                            if (Trace.isTagEnabled(j)) {
                            }
                            if (DEBUG_ORIENTATION) {
                            }
                            this.mAttachInfo.mWindowLeft = frame2.left;
                            this.mAttachInfo.mWindowTop = frame2.top;
                            if (this.mWidth == frame2.width()) {
                            }
                            this.mWidth = frame2.width();
                            this.mHeight = frame2.height();
                            if (this.mSurfaceHolder == null) {
                            }
                            threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                            if (threadedRenderer != null) {
                            }
                            if (this.mStopped) {
                            }
                            int childWidthMeasureSpec3222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                            int childHeightMeasureSpec3222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                            z3 = DEBUG_LAYOUT;
                            if (!z3) {
                            }
                            performMeasure(childWidthMeasureSpec3222, childHeightMeasureSpec3222);
                            int width222 = host.getMeasuredWidth();
                            int height222 = host.getMeasuredHeight();
                            measureAgain = false;
                            if (lp4.horizontalWeight <= 0.0f) {
                            }
                            if (lp4.verticalWeight <= 0.0f) {
                            }
                            if (measureAgain) {
                            }
                            layoutRequested3 = true;
                            childWidthMeasureSpec = relayoutResult3;
                            if (this.mViewMeasureDeferred) {
                            }
                            if (this.mRelayoutRequested) {
                            }
                            cancelReason = cancelReason2;
                            if (!surfaceSizeChanged) {
                            }
                            prepareSurfaces();
                            this.mChildBoundingInsetsChanged = false;
                            this.mForceUpdateBoundsLayer = false;
                            this.mFullRedrawNeeded = true;
                            if (layoutRequested3) {
                            }
                            if (didLayout) {
                            }
                            if (didLayout) {
                            }
                            didUseTransaction = false;
                            if (surfaceCreated) {
                            }
                            if (didUseTransaction) {
                            }
                            if (triggerGlobalLayoutListener) {
                            }
                            Rect contentInsets4222 = null;
                            Rect visibleInsets4222 = null;
                            Region touchableRegion2222 = null;
                            int touchableInsetMode2222 = 3;
                            computedInternalInsets = false;
                            if (computesInternalInsets) {
                            }
                            if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                            }
                            if (this.mFirst) {
                            }
                            if (viewVisibilityChanged) {
                            }
                            isViewVisible = isViewVisible2;
                            if (isViewVisible) {
                            }
                            changedVisibility = false;
                            if (changedVisibility) {
                            }
                            this.mFirst = false;
                            this.mWillDrawSoon = false;
                            this.mNewSurfaceNeeded = false;
                            this.mViewVisibility = viewVisibility2;
                            if (this.mAttachInfo.mHasWindowFocus) {
                            }
                            this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                            if ((childWidthMeasureSpec & 1) != 0) {
                            }
                            this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                            boolean cancelDueToPreDrawListener22222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                            if (cancelDueToPreDrawListener22222) {
                            }
                            if (!cancelAndRedraw) {
                            }
                            boolean z722222 = true;
                            isCancelDraw = z722222;
                            if (isCancelDraw) {
                            }
                            isCancelDraw2 = DEBUG_TRAVERSAL;
                            if (isCancelDraw2) {
                            }
                            if (isViewVisible) {
                            }
                            if (this.mAttachInfo.mContentCaptureEvents != null) {
                            }
                            this.mIsInTraversal = false;
                            this.mRelayoutRequested = false;
                            if (cancelAndRedraw) {
                            }
                        } catch (Throwable th7) {
                            th = th7;
                            if (!Trace.isTagEnabled(8L)) {
                            }
                        }
                    } else {
                        relayoutResult4 = relayoutResult5;
                    }
                    if (z4) {
                        Log.m100v(this.mTag, "relayout: frame=" + frame2.toShortString() + " surface=" + this.mSurface);
                    }
                    if (this.mRelayoutRequested && !this.mPendingMergedConfiguration.equals(this.mLastReportedMergedConfiguration)) {
                        if (DEBUG_CONFIGURATION) {
                            Log.m100v(this.mTag, "Visible with new config: " + this.mPendingMergedConfiguration.getMergedConfiguration());
                        }
                        performConfigurationChange(new MergedConfiguration(this.mPendingMergedConfiguration), this.mFirst, -1);
                        updatedConfiguration = true;
                    }
                    boolean updateSurfaceNeeded = this.mUpdateSurfaceNeeded;
                    this.mUpdateSurfaceNeeded = false;
                    surfaceSizeChanged = false;
                    if (this.mLastSurfaceSize.equals(this.mSurfaceSize)) {
                        cancelDraw4 = cancelDraw5;
                    } else {
                        surfaceSizeChanged = true;
                        try {
                            cancelDraw4 = cancelDraw5;
                            try {
                                this.mLastSurfaceSize.set(this.mSurfaceSize.f85x, this.mSurfaceSize.f86y);
                            } catch (RemoteException e12) {
                                viewVisibility2 = viewVisibility4;
                                relayoutResult2 = relayoutResult4;
                                cancelDraw3 = cancelDraw4;
                                hwInitialized4 = hwInitialized;
                                dispatchApplyInsets = dispatchApplyInsets2;
                                j = 8;
                                if (Trace.isTagEnabled(j)) {
                                }
                                if (DEBUG_ORIENTATION) {
                                }
                                this.mAttachInfo.mWindowLeft = frame2.left;
                                this.mAttachInfo.mWindowTop = frame2.top;
                                if (this.mWidth == frame2.width()) {
                                }
                                this.mWidth = frame2.width();
                                this.mHeight = frame2.height();
                                if (this.mSurfaceHolder == null) {
                                }
                                threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                if (threadedRenderer != null) {
                                }
                                if (this.mStopped) {
                                }
                                int childWidthMeasureSpec32222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                                int childHeightMeasureSpec32222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                                z3 = DEBUG_LAYOUT;
                                if (!z3) {
                                }
                                performMeasure(childWidthMeasureSpec32222, childHeightMeasureSpec32222);
                                int width2222 = host.getMeasuredWidth();
                                int height2222 = host.getMeasuredHeight();
                                measureAgain = false;
                                if (lp4.horizontalWeight <= 0.0f) {
                                }
                                if (lp4.verticalWeight <= 0.0f) {
                                }
                                if (measureAgain) {
                                }
                                layoutRequested3 = true;
                                childWidthMeasureSpec = relayoutResult3;
                                if (this.mViewMeasureDeferred) {
                                }
                                if (this.mRelayoutRequested) {
                                }
                                cancelReason = cancelReason2;
                                if (!surfaceSizeChanged) {
                                }
                                prepareSurfaces();
                                this.mChildBoundingInsetsChanged = false;
                                this.mForceUpdateBoundsLayer = false;
                                this.mFullRedrawNeeded = true;
                                if (layoutRequested3) {
                                }
                                if (didLayout) {
                                }
                                if (didLayout) {
                                }
                                didUseTransaction = false;
                                if (surfaceCreated) {
                                }
                                if (didUseTransaction) {
                                }
                                if (triggerGlobalLayoutListener) {
                                }
                                Rect contentInsets42222 = null;
                                Rect visibleInsets42222 = null;
                                Region touchableRegion22222 = null;
                                int touchableInsetMode22222 = 3;
                                computedInternalInsets = false;
                                if (computesInternalInsets) {
                                }
                                if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                                }
                                if (this.mFirst) {
                                }
                                if (viewVisibilityChanged) {
                                }
                                isViewVisible = isViewVisible2;
                                if (isViewVisible) {
                                }
                                changedVisibility = false;
                                if (changedVisibility) {
                                }
                                this.mFirst = false;
                                this.mWillDrawSoon = false;
                                this.mNewSurfaceNeeded = false;
                                this.mViewVisibility = viewVisibility2;
                                if (this.mAttachInfo.mHasWindowFocus) {
                                }
                                this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                                if ((childWidthMeasureSpec & 1) != 0) {
                                }
                                this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                                boolean cancelDueToPreDrawListener222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                if (cancelDueToPreDrawListener222222) {
                                }
                                if (!cancelAndRedraw) {
                                }
                                boolean z7222222 = true;
                                isCancelDraw = z7222222;
                                if (isCancelDraw) {
                                }
                                isCancelDraw2 = DEBUG_TRAVERSAL;
                                if (isCancelDraw2) {
                                }
                                if (isViewVisible) {
                                }
                                if (this.mAttachInfo.mContentCaptureEvents != null) {
                                }
                                this.mIsInTraversal = false;
                                this.mRelayoutRequested = false;
                                if (cancelAndRedraw) {
                                }
                            } catch (Throwable th8) {
                                th = th8;
                                if (!Trace.isTagEnabled(8L)) {
                                }
                            }
                        } catch (RemoteException e13) {
                            viewVisibility2 = viewVisibility4;
                            relayoutResult2 = relayoutResult4;
                            cancelDraw3 = cancelDraw5;
                            hwInitialized4 = hwInitialized;
                            dispatchApplyInsets = dispatchApplyInsets2;
                            j = 8;
                            if (Trace.isTagEnabled(j)) {
                            }
                            if (DEBUG_ORIENTATION) {
                            }
                            this.mAttachInfo.mWindowLeft = frame2.left;
                            this.mAttachInfo.mWindowTop = frame2.top;
                            if (this.mWidth == frame2.width()) {
                            }
                            this.mWidth = frame2.width();
                            this.mHeight = frame2.height();
                            if (this.mSurfaceHolder == null) {
                            }
                            threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                            if (threadedRenderer != null) {
                            }
                            if (this.mStopped) {
                            }
                            int childWidthMeasureSpec322222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                            int childHeightMeasureSpec322222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                            z3 = DEBUG_LAYOUT;
                            if (!z3) {
                            }
                            performMeasure(childWidthMeasureSpec322222, childHeightMeasureSpec322222);
                            int width22222 = host.getMeasuredWidth();
                            int height22222 = host.getMeasuredHeight();
                            measureAgain = false;
                            if (lp4.horizontalWeight <= 0.0f) {
                            }
                            if (lp4.verticalWeight <= 0.0f) {
                            }
                            if (measureAgain) {
                            }
                            layoutRequested3 = true;
                            childWidthMeasureSpec = relayoutResult3;
                            if (this.mViewMeasureDeferred) {
                            }
                            if (this.mRelayoutRequested) {
                            }
                            cancelReason = cancelReason2;
                            if (!surfaceSizeChanged) {
                            }
                            prepareSurfaces();
                            this.mChildBoundingInsetsChanged = false;
                            this.mForceUpdateBoundsLayer = false;
                            this.mFullRedrawNeeded = true;
                            if (layoutRequested3) {
                            }
                            if (didLayout) {
                            }
                            if (didLayout) {
                            }
                            didUseTransaction = false;
                            if (surfaceCreated) {
                            }
                            if (didUseTransaction) {
                            }
                            if (triggerGlobalLayoutListener) {
                            }
                            Rect contentInsets422222 = null;
                            Rect visibleInsets422222 = null;
                            Region touchableRegion222222 = null;
                            int touchableInsetMode222222 = 3;
                            computedInternalInsets = false;
                            if (computesInternalInsets) {
                            }
                            if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                            }
                            if (this.mFirst) {
                            }
                            if (viewVisibilityChanged) {
                            }
                            isViewVisible = isViewVisible2;
                            if (isViewVisible) {
                            }
                            changedVisibility = false;
                            if (changedVisibility) {
                            }
                            this.mFirst = false;
                            this.mWillDrawSoon = false;
                            this.mNewSurfaceNeeded = false;
                            this.mViewVisibility = viewVisibility2;
                            if (this.mAttachInfo.mHasWindowFocus) {
                            }
                            this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                            if ((childWidthMeasureSpec & 1) != 0) {
                            }
                            this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                            boolean cancelDueToPreDrawListener2222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                            if (cancelDueToPreDrawListener2222222) {
                            }
                            if (!cancelAndRedraw) {
                            }
                            boolean z72222222 = true;
                            isCancelDraw = z72222222;
                            if (isCancelDraw) {
                            }
                            isCancelDraw2 = DEBUG_TRAVERSAL;
                            if (isCancelDraw2) {
                            }
                            if (isViewVisible) {
                            }
                            if (this.mAttachInfo.mContentCaptureEvents != null) {
                            }
                            this.mIsInTraversal = false;
                            this.mRelayoutRequested = false;
                            if (cancelAndRedraw) {
                            }
                        } catch (Throwable th9) {
                            th = th9;
                            if (!Trace.isTagEnabled(8L)) {
                            }
                        }
                    }
                    alwaysConsumeSystemBarsChanged = this.mPendingAlwaysConsumeSystemBars != this.mAttachInfo.mAlwaysConsumeSystemBars;
                    updateColorModeIfNeeded(lp4.getColorMode());
                    surfaceCreated = hadSurface && this.mSurface.isValid();
                    surfaceDestroyed = (hadSurface || this.mSurface.isValid()) ? false : true;
                    if (surfaceGenerationId == this.mSurface.getGenerationId() || surfaceControlChanged) {
                        if (this.mSurface.isValid()) {
                            z5 = true;
                            surfaceReplaced = z5;
                            if (surfaceReplaced) {
                                this.mSurfaceSequenceId++;
                            }
                            if (alwaysConsumeSystemBarsChanged) {
                                dispatchApplyInsets = dispatchApplyInsets2;
                            } else {
                                this.mAttachInfo.mAlwaysConsumeSystemBars = this.mPendingAlwaysConsumeSystemBars;
                                dispatchApplyInsets = true;
                            }
                            if (updateCaptionInsets()) {
                                dispatchApplyInsets = true;
                            }
                            if (dispatchApplyInsets) {
                                try {
                                } catch (RemoteException e14) {
                                    viewVisibility2 = viewVisibility4;
                                    relayoutResult2 = relayoutResult4;
                                    cancelDraw3 = cancelDraw4;
                                    hwInitialized4 = hwInitialized;
                                    j = 8;
                                } catch (Throwable th10) {
                                    th = th10;
                                }
                                try {
                                    if (this.mLastSystemUiVisibility == this.mAttachInfo.mSystemUiVisibility) {
                                    }
                                } catch (RemoteException e15) {
                                    viewVisibility2 = viewVisibility4;
                                    relayoutResult2 = relayoutResult4;
                                    cancelDraw3 = cancelDraw4;
                                    hwInitialized4 = hwInitialized;
                                    j = 8;
                                    if (Trace.isTagEnabled(j)) {
                                    }
                                    if (DEBUG_ORIENTATION) {
                                    }
                                    this.mAttachInfo.mWindowLeft = frame2.left;
                                    this.mAttachInfo.mWindowTop = frame2.top;
                                    if (this.mWidth == frame2.width()) {
                                    }
                                    this.mWidth = frame2.width();
                                    this.mHeight = frame2.height();
                                    if (this.mSurfaceHolder == null) {
                                    }
                                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                    if (threadedRenderer != null) {
                                    }
                                    if (this.mStopped) {
                                    }
                                    int childWidthMeasureSpec3222222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                                    int childHeightMeasureSpec3222222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                                    z3 = DEBUG_LAYOUT;
                                    if (!z3) {
                                    }
                                    performMeasure(childWidthMeasureSpec3222222, childHeightMeasureSpec3222222);
                                    int width222222 = host.getMeasuredWidth();
                                    int height222222 = host.getMeasuredHeight();
                                    measureAgain = false;
                                    if (lp4.horizontalWeight <= 0.0f) {
                                    }
                                    if (lp4.verticalWeight <= 0.0f) {
                                    }
                                    if (measureAgain) {
                                    }
                                    layoutRequested3 = true;
                                    childWidthMeasureSpec = relayoutResult3;
                                    if (this.mViewMeasureDeferred) {
                                    }
                                    if (this.mRelayoutRequested) {
                                    }
                                    cancelReason = cancelReason2;
                                    if (!surfaceSizeChanged) {
                                    }
                                    prepareSurfaces();
                                    this.mChildBoundingInsetsChanged = false;
                                    this.mForceUpdateBoundsLayer = false;
                                    this.mFullRedrawNeeded = true;
                                    if (layoutRequested3) {
                                    }
                                    if (didLayout) {
                                    }
                                    if (didLayout) {
                                    }
                                    didUseTransaction = false;
                                    if (surfaceCreated) {
                                    }
                                    if (didUseTransaction) {
                                    }
                                    if (triggerGlobalLayoutListener) {
                                    }
                                    Rect contentInsets4222222 = null;
                                    Rect visibleInsets4222222 = null;
                                    Region touchableRegion2222222 = null;
                                    int touchableInsetMode2222222 = 3;
                                    computedInternalInsets = false;
                                    if (computesInternalInsets) {
                                    }
                                    if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                                    }
                                    if (this.mFirst) {
                                    }
                                    if (viewVisibilityChanged) {
                                    }
                                    isViewVisible = isViewVisible2;
                                    if (isViewVisible) {
                                    }
                                    changedVisibility = false;
                                    if (changedVisibility) {
                                    }
                                    this.mFirst = false;
                                    this.mWillDrawSoon = false;
                                    this.mNewSurfaceNeeded = false;
                                    this.mViewVisibility = viewVisibility2;
                                    if (this.mAttachInfo.mHasWindowFocus) {
                                    }
                                    this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                                    if ((childWidthMeasureSpec & 1) != 0) {
                                    }
                                    this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                                    boolean cancelDueToPreDrawListener22222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                    if (cancelDueToPreDrawListener22222222) {
                                    }
                                    if (!cancelAndRedraw) {
                                    }
                                    boolean z722222222 = true;
                                    isCancelDraw = z722222222;
                                    if (isCancelDraw) {
                                    }
                                    isCancelDraw2 = DEBUG_TRAVERSAL;
                                    if (isCancelDraw2) {
                                    }
                                    if (isViewVisible) {
                                    }
                                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                                    }
                                    this.mIsInTraversal = false;
                                    this.mRelayoutRequested = false;
                                    if (cancelAndRedraw) {
                                    }
                                } catch (Throwable th11) {
                                    th = th11;
                                    if (!Trace.isTagEnabled(8L)) {
                                    }
                                }
                            }
                            this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                            dispatchApplyInsets(host);
                            dispatchApplyInsets = true;
                            if (surfaceCreated) {
                                dispatchApplyInsets3 = dispatchApplyInsets;
                                viewVisibility2 = viewVisibility4;
                                try {
                                    if (surfaceDestroyed) {
                                        WeakReference<View> weakReference = this.mLastScrolledFocus;
                                        if (weakReference != null) {
                                            weakReference.clear();
                                        }
                                        this.mCurScrollY = 0;
                                        this.mScrollY = 0;
                                        KeyEvent.Callback callback = this.mView;
                                        if (callback instanceof RootViewSurfaceTaker) {
                                            ((RootViewSurfaceTaker) callback).onRootViewScrollYChanged(0);
                                        }
                                        Scroller scroller = this.mScroller;
                                        if (scroller != null) {
                                            scroller.abortAnimation();
                                        }
                                        if (isHardwareEnabled()) {
                                            this.mAttachInfo.mThreadedRenderer.destroy();
                                            Log.m94d(this.mTag, "mThreadedRenderer.destroy()#3");
                                        }
                                    } else {
                                        if (!surfaceReplaced && !surfaceSizeChanged && !updateSurfaceNeeded) {
                                        }
                                        if (this.mSurfaceHolder == null && this.mAttachInfo.mThreadedRenderer != null) {
                                            if (this.mSurface.isValid()) {
                                                this.mFullRedrawNeeded = true;
                                                try {
                                                    this.mAttachInfo.mThreadedRenderer.updateSurface(this.mSurface);
                                                    String str = this.mTag;
                                                    Object[] objArr2 = new Object[1];
                                                    try {
                                                        objArr2[0] = "isValid=" + this.mSurface.isValid() + " 0x" + Long.toHexString(this.mSurface.mNativeObject);
                                                        Log.m94d(str, String.format("mThreadedRenderer.updateSurface() mSurface={%s}", objArr2));
                                                    } catch (Surface.OutOfResourcesException e16) {
                                                        e = e16;
                                                        handleOutOfResourcesException(e);
                                                        this.mLastPerformTraversalsSkipDrawReason = "oom_update_surface";
                                                        if (Trace.isTagEnabled(8L)) {
                                                            Trace.traceEnd(8L);
                                                            return;
                                                        }
                                                        return;
                                                    }
                                                } catch (Surface.OutOfResourcesException e17) {
                                                    e = e17;
                                                }
                                            }
                                        }
                                    }
                                } catch (RemoteException e18) {
                                    relayoutResult2 = relayoutResult4;
                                    cancelDraw3 = cancelDraw4;
                                    hwInitialized4 = hwInitialized;
                                    dispatchApplyInsets = dispatchApplyInsets3;
                                    j = 8;
                                    if (Trace.isTagEnabled(j)) {
                                    }
                                    if (DEBUG_ORIENTATION) {
                                    }
                                    this.mAttachInfo.mWindowLeft = frame2.left;
                                    this.mAttachInfo.mWindowTop = frame2.top;
                                    if (this.mWidth == frame2.width()) {
                                    }
                                    this.mWidth = frame2.width();
                                    this.mHeight = frame2.height();
                                    if (this.mSurfaceHolder == null) {
                                    }
                                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                    if (threadedRenderer != null) {
                                    }
                                    if (this.mStopped) {
                                    }
                                    int childWidthMeasureSpec32222222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                                    int childHeightMeasureSpec32222222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                                    z3 = DEBUG_LAYOUT;
                                    if (!z3) {
                                    }
                                    performMeasure(childWidthMeasureSpec32222222, childHeightMeasureSpec32222222);
                                    int width2222222 = host.getMeasuredWidth();
                                    int height2222222 = host.getMeasuredHeight();
                                    measureAgain = false;
                                    if (lp4.horizontalWeight <= 0.0f) {
                                    }
                                    if (lp4.verticalWeight <= 0.0f) {
                                    }
                                    if (measureAgain) {
                                    }
                                    layoutRequested3 = true;
                                    childWidthMeasureSpec = relayoutResult3;
                                    if (this.mViewMeasureDeferred) {
                                    }
                                    if (this.mRelayoutRequested) {
                                    }
                                    cancelReason = cancelReason2;
                                    if (!surfaceSizeChanged) {
                                    }
                                    prepareSurfaces();
                                    this.mChildBoundingInsetsChanged = false;
                                    this.mForceUpdateBoundsLayer = false;
                                    this.mFullRedrawNeeded = true;
                                    if (layoutRequested3) {
                                    }
                                    if (didLayout) {
                                    }
                                    if (didLayout) {
                                    }
                                    didUseTransaction = false;
                                    if (surfaceCreated) {
                                    }
                                    if (didUseTransaction) {
                                    }
                                    if (triggerGlobalLayoutListener) {
                                    }
                                    Rect contentInsets42222222 = null;
                                    Rect visibleInsets42222222 = null;
                                    Region touchableRegion22222222 = null;
                                    int touchableInsetMode22222222 = 3;
                                    computedInternalInsets = false;
                                    if (computesInternalInsets) {
                                    }
                                    if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                                    }
                                    if (this.mFirst) {
                                    }
                                    if (viewVisibilityChanged) {
                                    }
                                    isViewVisible = isViewVisible2;
                                    if (isViewVisible) {
                                    }
                                    changedVisibility = false;
                                    if (changedVisibility) {
                                    }
                                    this.mFirst = false;
                                    this.mWillDrawSoon = false;
                                    this.mNewSurfaceNeeded = false;
                                    this.mViewVisibility = viewVisibility2;
                                    if (this.mAttachInfo.mHasWindowFocus) {
                                    }
                                    this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                                    if ((childWidthMeasureSpec & 1) != 0) {
                                    }
                                    this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                                    boolean cancelDueToPreDrawListener222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                    if (cancelDueToPreDrawListener222222222) {
                                    }
                                    if (!cancelAndRedraw) {
                                    }
                                    boolean z7222222222 = true;
                                    isCancelDraw = z7222222222;
                                    if (isCancelDraw) {
                                    }
                                    isCancelDraw2 = DEBUG_TRAVERSAL;
                                    if (isCancelDraw2) {
                                    }
                                    if (isViewVisible) {
                                    }
                                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                                    }
                                    this.mIsInTraversal = false;
                                    this.mRelayoutRequested = false;
                                    if (cancelAndRedraw) {
                                    }
                                } catch (Throwable th12) {
                                    th = th12;
                                    if (!Trace.isTagEnabled(8L)) {
                                    }
                                }
                            } else {
                                try {
                                    this.mFullRedrawNeeded = true;
                                    this.mPreviousTransparentRegion.setEmpty();
                                } catch (RemoteException e19) {
                                    viewVisibility2 = viewVisibility4;
                                    relayoutResult2 = relayoutResult4;
                                    cancelDraw3 = cancelDraw4;
                                    hwInitialized4 = hwInitialized;
                                    j = 8;
                                } catch (Throwable th13) {
                                    th = th13;
                                }
                                if (this.mAttachInfo.mThreadedRenderer != null) {
                                    try {
                                        try {
                                            hwInitialized3 = this.mAttachInfo.mThreadedRenderer.initialize(this.mSurface);
                                            try {
                                                try {
                                                    try {
                                                        dispatchApplyInsets3 = dispatchApplyInsets;
                                                        try {
                                                            viewVisibility2 = viewVisibility4;
                                                        } catch (RemoteException e20) {
                                                            viewVisibility2 = viewVisibility4;
                                                            hwInitialized4 = hwInitialized3;
                                                            relayoutResult2 = relayoutResult4;
                                                            cancelDraw3 = cancelDraw4;
                                                            dispatchApplyInsets = dispatchApplyInsets3;
                                                            j = 8;
                                                        } catch (Surface.OutOfResourcesException e21) {
                                                            viewVisibility2 = viewVisibility4;
                                                            e2 = e21;
                                                            hwInitialized4 = hwInitialized3;
                                                        } catch (Throwable th14) {
                                                            th = th14;
                                                        }
                                                        try {
                                                            try {
                                                                Log.m94d(this.mTag, String.format("mThreadedRenderer.initialize() mSurface={%s} hwInitialized=" + hwInitialized3, "isValid=" + this.mSurface.isValid() + " 0x" + Long.toHexString(this.mSurface.mNativeObject)));
                                                                if (hwInitialized3 && (host.mPrivateFlags & 512) == 0) {
                                                                    this.mAttachInfo.mThreadedRenderer.allocateBuffers();
                                                                }
                                                                if (this.mDragResizing != dragResizing) {
                                                                    if (dragResizing) {
                                                                        boolean backdropSizeMatchesFrame = this.mWinFrame.width() == this.mPendingBackDropFrame.width() && this.mWinFrame.height() == this.mPendingBackDropFrame.height();
                                                                        startDragResizing(this.mPendingBackDropFrame, !backdropSizeMatchesFrame, this.mAttachInfo.mContentInsets, this.mAttachInfo.mStableInsets);
                                                                    } else {
                                                                        endDragResizing();
                                                                    }
                                                                }
                                                                if (!this.mUseMTRenderer) {
                                                                    if (dragResizing) {
                                                                        this.mCanvasOffsetX = this.mWinFrame.left;
                                                                        this.mCanvasOffsetY = this.mWinFrame.top;
                                                                    } else {
                                                                        this.mCanvasOffsetY = 0;
                                                                        this.mCanvasOffsetX = 0;
                                                                    }
                                                                }
                                                                if (Trace.isTagEnabled(8L)) {
                                                                    Trace.traceEnd(8L);
                                                                }
                                                                hwInitialized4 = hwInitialized3;
                                                                relayoutResult2 = relayoutResult4;
                                                                cancelDraw3 = cancelDraw4;
                                                                dispatchApplyInsets = dispatchApplyInsets3;
                                                            } catch (Surface.OutOfResourcesException e22) {
                                                                e2 = e22;
                                                                hwInitialized4 = hwInitialized3;
                                                                try {
                                                                    handleOutOfResourcesException(e2);
                                                                    this.mLastPerformTraversalsSkipDrawReason = "oom_initialize_renderer";
                                                                    if (Trace.isTagEnabled(8L)) {
                                                                        Trace.traceEnd(8L);
                                                                        return;
                                                                    }
                                                                    return;
                                                                } catch (RemoteException e23) {
                                                                    relayoutResult2 = relayoutResult4;
                                                                    cancelDraw3 = cancelDraw4;
                                                                    dispatchApplyInsets = dispatchApplyInsets3;
                                                                    j = 8;
                                                                    if (Trace.isTagEnabled(j)) {
                                                                    }
                                                                    if (DEBUG_ORIENTATION) {
                                                                    }
                                                                    this.mAttachInfo.mWindowLeft = frame2.left;
                                                                    this.mAttachInfo.mWindowTop = frame2.top;
                                                                    if (this.mWidth == frame2.width()) {
                                                                    }
                                                                    this.mWidth = frame2.width();
                                                                    this.mHeight = frame2.height();
                                                                    if (this.mSurfaceHolder == null) {
                                                                    }
                                                                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                                    if (threadedRenderer != null) {
                                                                    }
                                                                    if (this.mStopped) {
                                                                    }
                                                                    int childWidthMeasureSpec322222222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                                                                    int childHeightMeasureSpec322222222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                                                                    z3 = DEBUG_LAYOUT;
                                                                    if (!z3) {
                                                                    }
                                                                    performMeasure(childWidthMeasureSpec322222222, childHeightMeasureSpec322222222);
                                                                    int width22222222 = host.getMeasuredWidth();
                                                                    int height22222222 = host.getMeasuredHeight();
                                                                    measureAgain = false;
                                                                    if (lp4.horizontalWeight <= 0.0f) {
                                                                    }
                                                                    if (lp4.verticalWeight <= 0.0f) {
                                                                    }
                                                                    if (measureAgain) {
                                                                    }
                                                                    layoutRequested3 = true;
                                                                    childWidthMeasureSpec = relayoutResult3;
                                                                    if (this.mViewMeasureDeferred) {
                                                                    }
                                                                    if (this.mRelayoutRequested) {
                                                                    }
                                                                    cancelReason = cancelReason2;
                                                                    if (!surfaceSizeChanged) {
                                                                    }
                                                                    prepareSurfaces();
                                                                    this.mChildBoundingInsetsChanged = false;
                                                                    this.mForceUpdateBoundsLayer = false;
                                                                    this.mFullRedrawNeeded = true;
                                                                    if (layoutRequested3) {
                                                                    }
                                                                    if (didLayout) {
                                                                    }
                                                                    if (didLayout) {
                                                                    }
                                                                    didUseTransaction = false;
                                                                    if (surfaceCreated) {
                                                                    }
                                                                    if (didUseTransaction) {
                                                                    }
                                                                    if (triggerGlobalLayoutListener) {
                                                                    }
                                                                    Rect contentInsets422222222 = null;
                                                                    Rect visibleInsets422222222 = null;
                                                                    Region touchableRegion222222222 = null;
                                                                    int touchableInsetMode222222222 = 3;
                                                                    computedInternalInsets = false;
                                                                    if (computesInternalInsets) {
                                                                    }
                                                                    if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                                                                    }
                                                                    if (this.mFirst) {
                                                                    }
                                                                    if (viewVisibilityChanged) {
                                                                    }
                                                                    isViewVisible = isViewVisible2;
                                                                    if (isViewVisible) {
                                                                    }
                                                                    changedVisibility = false;
                                                                    if (changedVisibility) {
                                                                    }
                                                                    this.mFirst = false;
                                                                    this.mWillDrawSoon = false;
                                                                    this.mNewSurfaceNeeded = false;
                                                                    this.mViewVisibility = viewVisibility2;
                                                                    if (this.mAttachInfo.mHasWindowFocus) {
                                                                    }
                                                                    this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                                                                    if ((childWidthMeasureSpec & 1) != 0) {
                                                                    }
                                                                    this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                                                                    boolean cancelDueToPreDrawListener2222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                                    if (cancelDueToPreDrawListener2222222222) {
                                                                    }
                                                                    if (!cancelAndRedraw) {
                                                                    }
                                                                    boolean z72222222222 = true;
                                                                    isCancelDraw = z72222222222;
                                                                    if (isCancelDraw) {
                                                                    }
                                                                    isCancelDraw2 = DEBUG_TRAVERSAL;
                                                                    if (isCancelDraw2) {
                                                                    }
                                                                    if (isViewVisible) {
                                                                    }
                                                                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                                    }
                                                                    this.mIsInTraversal = false;
                                                                    this.mRelayoutRequested = false;
                                                                    if (cancelAndRedraw) {
                                                                    }
                                                                } catch (Throwable th15) {
                                                                    th = th15;
                                                                    if (!Trace.isTagEnabled(8L)) {
                                                                    }
                                                                }
                                                            }
                                                        } catch (RemoteException e24) {
                                                            hwInitialized4 = hwInitialized3;
                                                            relayoutResult2 = relayoutResult4;
                                                            cancelDraw3 = cancelDraw4;
                                                            dispatchApplyInsets = dispatchApplyInsets3;
                                                            j = 8;
                                                            if (Trace.isTagEnabled(j)) {
                                                            }
                                                            if (DEBUG_ORIENTATION) {
                                                            }
                                                            this.mAttachInfo.mWindowLeft = frame2.left;
                                                            this.mAttachInfo.mWindowTop = frame2.top;
                                                            if (this.mWidth == frame2.width()) {
                                                            }
                                                            this.mWidth = frame2.width();
                                                            this.mHeight = frame2.height();
                                                            if (this.mSurfaceHolder == null) {
                                                            }
                                                            threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                                            if (threadedRenderer != null) {
                                                            }
                                                            if (this.mStopped) {
                                                            }
                                                            int childWidthMeasureSpec3222222222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                                                            int childHeightMeasureSpec3222222222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                                                            z3 = DEBUG_LAYOUT;
                                                            if (!z3) {
                                                            }
                                                            performMeasure(childWidthMeasureSpec3222222222, childHeightMeasureSpec3222222222);
                                                            int width222222222 = host.getMeasuredWidth();
                                                            int height222222222 = host.getMeasuredHeight();
                                                            measureAgain = false;
                                                            if (lp4.horizontalWeight <= 0.0f) {
                                                            }
                                                            if (lp4.verticalWeight <= 0.0f) {
                                                            }
                                                            if (measureAgain) {
                                                            }
                                                            layoutRequested3 = true;
                                                            childWidthMeasureSpec = relayoutResult3;
                                                            if (this.mViewMeasureDeferred) {
                                                            }
                                                            if (this.mRelayoutRequested) {
                                                            }
                                                            cancelReason = cancelReason2;
                                                            if (!surfaceSizeChanged) {
                                                            }
                                                            prepareSurfaces();
                                                            this.mChildBoundingInsetsChanged = false;
                                                            this.mForceUpdateBoundsLayer = false;
                                                            this.mFullRedrawNeeded = true;
                                                            if (layoutRequested3) {
                                                            }
                                                            if (didLayout) {
                                                            }
                                                            if (didLayout) {
                                                            }
                                                            didUseTransaction = false;
                                                            if (surfaceCreated) {
                                                            }
                                                            if (didUseTransaction) {
                                                            }
                                                            if (triggerGlobalLayoutListener) {
                                                            }
                                                            Rect contentInsets4222222222 = null;
                                                            Rect visibleInsets4222222222 = null;
                                                            Region touchableRegion2222222222 = null;
                                                            int touchableInsetMode2222222222 = 3;
                                                            computedInternalInsets = false;
                                                            if (computesInternalInsets) {
                                                            }
                                                            if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                                                            }
                                                            if (this.mFirst) {
                                                            }
                                                            if (viewVisibilityChanged) {
                                                            }
                                                            isViewVisible = isViewVisible2;
                                                            if (isViewVisible) {
                                                            }
                                                            changedVisibility = false;
                                                            if (changedVisibility) {
                                                            }
                                                            this.mFirst = false;
                                                            this.mWillDrawSoon = false;
                                                            this.mNewSurfaceNeeded = false;
                                                            this.mViewVisibility = viewVisibility2;
                                                            if (this.mAttachInfo.mHasWindowFocus) {
                                                            }
                                                            this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                                                            if ((childWidthMeasureSpec & 1) != 0) {
                                                            }
                                                            this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                                                            boolean cancelDueToPreDrawListener22222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                                            if (cancelDueToPreDrawListener22222222222) {
                                                            }
                                                            if (!cancelAndRedraw) {
                                                            }
                                                            boolean z722222222222 = true;
                                                            isCancelDraw = z722222222222;
                                                            if (isCancelDraw) {
                                                            }
                                                            isCancelDraw2 = DEBUG_TRAVERSAL;
                                                            if (isCancelDraw2) {
                                                            }
                                                            if (isViewVisible) {
                                                            }
                                                            if (this.mAttachInfo.mContentCaptureEvents != null) {
                                                            }
                                                            this.mIsInTraversal = false;
                                                            this.mRelayoutRequested = false;
                                                            if (cancelAndRedraw) {
                                                            }
                                                        } catch (Throwable th16) {
                                                            th = th16;
                                                            if (!Trace.isTagEnabled(8L)) {
                                                            }
                                                        }
                                                    } catch (Surface.OutOfResourcesException e25) {
                                                        dispatchApplyInsets3 = dispatchApplyInsets;
                                                        viewVisibility2 = viewVisibility4;
                                                        e2 = e25;
                                                        hwInitialized4 = hwInitialized3;
                                                    }
                                                } catch (Surface.OutOfResourcesException e26) {
                                                    dispatchApplyInsets3 = dispatchApplyInsets;
                                                    viewVisibility2 = viewVisibility4;
                                                    e2 = e26;
                                                    hwInitialized4 = hwInitialized3;
                                                }
                                            } catch (RemoteException e27) {
                                                viewVisibility2 = viewVisibility4;
                                                hwInitialized4 = hwInitialized3;
                                                relayoutResult2 = relayoutResult4;
                                                cancelDraw3 = cancelDraw4;
                                                j = 8;
                                            } catch (Throwable th17) {
                                                th = th17;
                                            }
                                        } catch (RemoteException e28) {
                                            viewVisibility2 = viewVisibility4;
                                            relayoutResult2 = relayoutResult4;
                                            cancelDraw3 = cancelDraw4;
                                            hwInitialized4 = hwInitialized;
                                            j = 8;
                                        } catch (Surface.OutOfResourcesException e29) {
                                            dispatchApplyInsets3 = dispatchApplyInsets;
                                            viewVisibility2 = viewVisibility4;
                                            e2 = e29;
                                            hwInitialized4 = hwInitialized;
                                        } catch (Throwable th18) {
                                            th = th18;
                                        }
                                        if (DEBUG_ORIENTATION) {
                                            Log.m100v(TAG, "Relayout returned: frame=" + frame2 + ", surface=" + this.mSurface);
                                        }
                                        this.mAttachInfo.mWindowLeft = frame2.left;
                                        this.mAttachInfo.mWindowTop = frame2.top;
                                        if (this.mWidth == frame2.width() || this.mHeight != frame2.height()) {
                                            this.mWidth = frame2.width();
                                            this.mHeight = frame2.height();
                                        }
                                        if (this.mSurfaceHolder == null) {
                                            if (this.mSurface.isValid()) {
                                                this.mSurfaceHolder.mSurface = this.mSurface;
                                            }
                                            this.mSurfaceHolder.setSurfaceFrameSize(this.mWidth, this.mHeight);
                                            if (surfaceCreated) {
                                                this.mSurfaceHolder.ungetCallbacks();
                                                this.mIsCreating = true;
                                                Log.m98i(this.mTag, "ViewRootImpl >> surfaceCreated");
                                                SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
                                                if (callbacks != null) {
                                                    for (SurfaceHolder.Callback c : callbacks) {
                                                        c.surfaceCreated(this.mSurfaceHolder);
                                                    }
                                                }
                                            }
                                            if (!surfaceCreated && !surfaceReplaced && !surfaceSizeChanged && !insetsPending) {
                                                relayoutResult3 = relayoutResult2;
                                            } else if (this.mSurface.isValid()) {
                                                Log.m98i(this.mTag, String.format("ViewRootImpl >> surfaceChanged W=%d, H=%d)", Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight)));
                                                SurfaceHolder.Callback[] callbacks2 = this.mSurfaceHolder.getCallbacks();
                                                if (callbacks2 != null) {
                                                    int i8 = 0;
                                                    for (int length = callbacks2.length; i8 < length; length = length) {
                                                        SurfaceHolder.Callback c2 = callbacks2[i8];
                                                        BaseSurfaceHolder baseSurfaceHolder2 = this.mSurfaceHolder;
                                                        SurfaceHolder.Callback[] callbacks3 = callbacks2;
                                                        int i9 = lp4.format;
                                                        int relayoutResult6 = relayoutResult2;
                                                        int relayoutResult7 = this.mWidth;
                                                        c2.surfaceChanged(baseSurfaceHolder2, i9, relayoutResult7, this.mHeight);
                                                        i8++;
                                                        callbacks2 = callbacks3;
                                                        relayoutResult2 = relayoutResult6;
                                                    }
                                                    relayoutResult3 = relayoutResult2;
                                                } else {
                                                    relayoutResult3 = relayoutResult2;
                                                }
                                                this.mIsCreating = false;
                                            } else {
                                                relayoutResult3 = relayoutResult2;
                                            }
                                            if (surfaceDestroyed) {
                                                Log.m98i(this.mTag, "ViewRootImpl >> surfaceDestroyed");
                                                notifyHolderSurfaceDestroyed();
                                                this.mSurfaceHolder.mSurfaceLock.lock();
                                                try {
                                                    this.mSurfaceHolder.mSurface = new Surface();
                                                } finally {
                                                    this.mSurfaceHolder.mSurfaceLock.unlock();
                                                }
                                            }
                                        } else {
                                            relayoutResult3 = relayoutResult2;
                                        }
                                        threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                        if (threadedRenderer != null && threadedRenderer.isEnabled() && (hwInitialized4 || this.mWidth != threadedRenderer.getWidth() || this.mHeight != threadedRenderer.getHeight() || this.mNeedsRendererSetup)) {
                                            if (CoreRune.MW_CAPTION_SHELL_BUG_FIX || !(this.mWindowSession instanceof WindowlessWindowManager)) {
                                                threadedRenderer.setup(this.mWidth, this.mHeight, this.mAttachInfo, this.mWindowAttributes.surfaceInsets);
                                            } else {
                                                threadedRenderer.setup(this.mWidth, this.mHeight, this.mAttachInfo, this.mWindowAttributes.surfaceInsets, this.mLastReportedMergedConfiguration.getOverrideConfiguration().windowConfiguration.getBounds());
                                            }
                                            this.mNeedsRendererSetup = false;
                                        }
                                        if ((this.mStopped || this.mReportNextDraw) && (this.mWidth != host.getMeasuredWidth() || this.mHeight != host.getMeasuredHeight() || dispatchApplyInsets || updatedConfiguration)) {
                                            int childWidthMeasureSpec32222222222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                                            int childHeightMeasureSpec32222222222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                                            z3 = DEBUG_LAYOUT;
                                            if (!z3) {
                                                Log.m100v(this.mTag, "Ooops, something changed!  mWidth=" + this.mWidth + " measuredWidth=" + host.getMeasuredWidth() + " mHeight=" + this.mHeight + " measuredHeight=" + host.getMeasuredHeight() + " dispatchApplyInsets=" + dispatchApplyInsets);
                                            }
                                            performMeasure(childWidthMeasureSpec32222222222, childHeightMeasureSpec32222222222);
                                            int width2222222222 = host.getMeasuredWidth();
                                            int height2222222222 = host.getMeasuredHeight();
                                            measureAgain = false;
                                            if (lp4.horizontalWeight <= 0.0f) {
                                                childHeightMeasureSpec = childHeightMeasureSpec32222222222;
                                                width2222222222 += (int) ((this.mWidth - width2222222222) * lp4.horizontalWeight);
                                                int childWidthMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(width2222222222, 1073741824);
                                                measureAgain = true;
                                                childWidthMeasureSpec2 = childWidthMeasureSpec4;
                                            } else {
                                                childHeightMeasureSpec = childHeightMeasureSpec32222222222;
                                                childWidthMeasureSpec2 = childWidthMeasureSpec32222222222;
                                            }
                                            if (lp4.verticalWeight <= 0.0f) {
                                                height2222222222 += (int) ((this.mHeight - height2222222222) * lp4.verticalWeight);
                                                measureAgain = true;
                                                childHeightMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(height2222222222, 1073741824);
                                            } else {
                                                childHeightMeasureSpec2 = childHeightMeasureSpec;
                                            }
                                            if (measureAgain) {
                                                if (z3) {
                                                    Log.m100v(this.mTag, "And hey let's measure once more: width=" + width2222222222 + " height=" + height2222222222);
                                                }
                                                performMeasure(childWidthMeasureSpec2, childHeightMeasureSpec2);
                                            }
                                            layoutRequested3 = true;
                                        } else {
                                            layoutRequested3 = layoutRequested2;
                                        }
                                        childWidthMeasureSpec = relayoutResult3;
                                        if (this.mViewMeasureDeferred) {
                                        }
                                        if (this.mRelayoutRequested) {
                                        }
                                        cancelReason = cancelReason2;
                                        if (!surfaceSizeChanged) {
                                        }
                                        prepareSurfaces();
                                        this.mChildBoundingInsetsChanged = false;
                                        this.mForceUpdateBoundsLayer = false;
                                        this.mFullRedrawNeeded = true;
                                        if (layoutRequested3) {
                                        }
                                        if (didLayout) {
                                        }
                                        if (didLayout) {
                                        }
                                        didUseTransaction = false;
                                        if (surfaceCreated) {
                                        }
                                        if (didUseTransaction) {
                                        }
                                        if (triggerGlobalLayoutListener) {
                                        }
                                        Rect contentInsets42222222222 = null;
                                        Rect visibleInsets42222222222 = null;
                                        Region touchableRegion22222222222 = null;
                                        int touchableInsetMode22222222222 = 3;
                                        computedInternalInsets = false;
                                        if (computesInternalInsets) {
                                        }
                                        if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                                        }
                                        if (this.mFirst) {
                                        }
                                        if (viewVisibilityChanged) {
                                        }
                                        isViewVisible = isViewVisible2;
                                        if (isViewVisible) {
                                        }
                                        changedVisibility = false;
                                        if (changedVisibility) {
                                        }
                                        this.mFirst = false;
                                        this.mWillDrawSoon = false;
                                        this.mNewSurfaceNeeded = false;
                                        this.mViewVisibility = viewVisibility2;
                                        if (this.mAttachInfo.mHasWindowFocus) {
                                        }
                                        this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                                        if ((childWidthMeasureSpec & 1) != 0) {
                                        }
                                        this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                                        boolean cancelDueToPreDrawListener222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                                        if (cancelDueToPreDrawListener222222222222) {
                                        }
                                        if (!cancelAndRedraw) {
                                        }
                                        boolean z7222222222222 = true;
                                        isCancelDraw = z7222222222222;
                                        if (isCancelDraw) {
                                        }
                                        isCancelDraw2 = DEBUG_TRAVERSAL;
                                        if (isCancelDraw2) {
                                        }
                                        if (isViewVisible) {
                                        }
                                        if (this.mAttachInfo.mContentCaptureEvents != null) {
                                        }
                                        this.mIsInTraversal = false;
                                        this.mRelayoutRequested = false;
                                        if (cancelAndRedraw) {
                                        }
                                    } catch (Surface.OutOfResourcesException e30) {
                                        dispatchApplyInsets3 = dispatchApplyInsets;
                                        viewVisibility2 = viewVisibility4;
                                        e2 = e30;
                                        hwInitialized4 = hwInitialized;
                                    }
                                } else {
                                    dispatchApplyInsets3 = dispatchApplyInsets;
                                    viewVisibility2 = viewVisibility4;
                                }
                            }
                            hwInitialized3 = hwInitialized;
                            if (this.mDragResizing != dragResizing) {
                            }
                            if (!this.mUseMTRenderer) {
                            }
                            if (Trace.isTagEnabled(8L)) {
                            }
                            hwInitialized4 = hwInitialized3;
                            relayoutResult2 = relayoutResult4;
                            cancelDraw3 = cancelDraw4;
                            dispatchApplyInsets = dispatchApplyInsets3;
                            if (DEBUG_ORIENTATION) {
                            }
                            this.mAttachInfo.mWindowLeft = frame2.left;
                            this.mAttachInfo.mWindowTop = frame2.top;
                            if (this.mWidth == frame2.width()) {
                            }
                            this.mWidth = frame2.width();
                            this.mHeight = frame2.height();
                            if (this.mSurfaceHolder == null) {
                            }
                            threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                            if (threadedRenderer != null) {
                                if (CoreRune.MW_CAPTION_SHELL_BUG_FIX) {
                                }
                                threadedRenderer.setup(this.mWidth, this.mHeight, this.mAttachInfo, this.mWindowAttributes.surfaceInsets);
                                this.mNeedsRendererSetup = false;
                            }
                            if (this.mStopped) {
                            }
                            int childWidthMeasureSpec322222222222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                            int childHeightMeasureSpec322222222222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                            z3 = DEBUG_LAYOUT;
                            if (!z3) {
                            }
                            performMeasure(childWidthMeasureSpec322222222222, childHeightMeasureSpec322222222222);
                            int width22222222222 = host.getMeasuredWidth();
                            int height22222222222 = host.getMeasuredHeight();
                            measureAgain = false;
                            if (lp4.horizontalWeight <= 0.0f) {
                            }
                            if (lp4.verticalWeight <= 0.0f) {
                            }
                            if (measureAgain) {
                            }
                            layoutRequested3 = true;
                            childWidthMeasureSpec = relayoutResult3;
                            if (this.mViewMeasureDeferred) {
                            }
                            if (this.mRelayoutRequested) {
                            }
                            cancelReason = cancelReason2;
                            if (!surfaceSizeChanged) {
                            }
                            prepareSurfaces();
                            this.mChildBoundingInsetsChanged = false;
                            this.mForceUpdateBoundsLayer = false;
                            this.mFullRedrawNeeded = true;
                            if (layoutRequested3) {
                            }
                            if (didLayout) {
                            }
                            if (didLayout) {
                            }
                            didUseTransaction = false;
                            if (surfaceCreated) {
                            }
                            if (didUseTransaction) {
                            }
                            if (triggerGlobalLayoutListener) {
                            }
                            Rect contentInsets422222222222 = null;
                            Rect visibleInsets422222222222 = null;
                            Region touchableRegion222222222222 = null;
                            int touchableInsetMode222222222222 = 3;
                            computedInternalInsets = false;
                            if (computesInternalInsets) {
                            }
                            if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                            }
                            if (this.mFirst) {
                            }
                            if (viewVisibilityChanged) {
                            }
                            isViewVisible = isViewVisible2;
                            if (isViewVisible) {
                            }
                            changedVisibility = false;
                            if (changedVisibility) {
                            }
                            this.mFirst = false;
                            this.mWillDrawSoon = false;
                            this.mNewSurfaceNeeded = false;
                            this.mViewVisibility = viewVisibility2;
                            if (this.mAttachInfo.mHasWindowFocus) {
                            }
                            this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                            if ((childWidthMeasureSpec & 1) != 0) {
                            }
                            this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                            boolean cancelDueToPreDrawListener2222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                            if (cancelDueToPreDrawListener2222222222222) {
                            }
                            if (!cancelAndRedraw) {
                            }
                            boolean z72222222222222 = true;
                            isCancelDraw = z72222222222222;
                            if (isCancelDraw) {
                            }
                            isCancelDraw2 = DEBUG_TRAVERSAL;
                            if (isCancelDraw2) {
                            }
                            if (isViewVisible) {
                            }
                            if (this.mAttachInfo.mContentCaptureEvents != null) {
                            }
                            this.mIsInTraversal = false;
                            this.mRelayoutRequested = false;
                            if (cancelAndRedraw) {
                            }
                        }
                    }
                    z5 = false;
                    surfaceReplaced = z5;
                    if (surfaceReplaced) {
                    }
                    if (alwaysConsumeSystemBarsChanged) {
                    }
                    if (updateCaptionInsets()) {
                    }
                    if (dispatchApplyInsets) {
                    }
                    this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                    dispatchApplyInsets(host);
                    dispatchApplyInsets = true;
                    if (surfaceCreated) {
                    }
                    hwInitialized3 = hwInitialized;
                    if (this.mDragResizing != dragResizing) {
                    }
                    if (!this.mUseMTRenderer) {
                    }
                    if (Trace.isTagEnabled(8L)) {
                    }
                    hwInitialized4 = hwInitialized3;
                    relayoutResult2 = relayoutResult4;
                    cancelDraw3 = cancelDraw4;
                    dispatchApplyInsets = dispatchApplyInsets3;
                    if (DEBUG_ORIENTATION) {
                    }
                    this.mAttachInfo.mWindowLeft = frame2.left;
                    this.mAttachInfo.mWindowTop = frame2.top;
                    if (this.mWidth == frame2.width()) {
                    }
                    this.mWidth = frame2.width();
                    this.mHeight = frame2.height();
                    if (this.mSurfaceHolder == null) {
                    }
                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                    if (threadedRenderer != null) {
                    }
                    if (this.mStopped) {
                    }
                    int childWidthMeasureSpec3222222222222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                    int childHeightMeasureSpec3222222222222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                    z3 = DEBUG_LAYOUT;
                    if (!z3) {
                    }
                    performMeasure(childWidthMeasureSpec3222222222222, childHeightMeasureSpec3222222222222);
                    int width222222222222 = host.getMeasuredWidth();
                    int height222222222222 = host.getMeasuredHeight();
                    measureAgain = false;
                    if (lp4.horizontalWeight <= 0.0f) {
                    }
                    if (lp4.verticalWeight <= 0.0f) {
                    }
                    if (measureAgain) {
                    }
                    layoutRequested3 = true;
                    childWidthMeasureSpec = relayoutResult3;
                    if (this.mViewMeasureDeferred) {
                    }
                    if (this.mRelayoutRequested) {
                    }
                    cancelReason = cancelReason2;
                    if (!surfaceSizeChanged) {
                    }
                    prepareSurfaces();
                    this.mChildBoundingInsetsChanged = false;
                    this.mForceUpdateBoundsLayer = false;
                    this.mFullRedrawNeeded = true;
                    if (layoutRequested3) {
                    }
                    if (didLayout) {
                    }
                    if (didLayout) {
                    }
                    didUseTransaction = false;
                    if (surfaceCreated) {
                    }
                    if (didUseTransaction) {
                    }
                    if (triggerGlobalLayoutListener) {
                    }
                    Rect contentInsets4222222222222 = null;
                    Rect visibleInsets4222222222222 = null;
                    Region touchableRegion2222222222222 = null;
                    int touchableInsetMode2222222222222 = 3;
                    computedInternalInsets = false;
                    if (computesInternalInsets) {
                    }
                    if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                    }
                    if (this.mFirst) {
                    }
                    if (viewVisibilityChanged) {
                    }
                    isViewVisible = isViewVisible2;
                    if (isViewVisible) {
                    }
                    changedVisibility = false;
                    if (changedVisibility) {
                    }
                    this.mFirst = false;
                    this.mWillDrawSoon = false;
                    this.mNewSurfaceNeeded = false;
                    this.mViewVisibility = viewVisibility2;
                    if (this.mAttachInfo.mHasWindowFocus) {
                    }
                    this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                    if ((childWidthMeasureSpec & 1) != 0) {
                    }
                    this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                    boolean cancelDueToPreDrawListener22222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                    if (cancelDueToPreDrawListener22222222222222) {
                    }
                    if (!cancelAndRedraw) {
                    }
                    boolean z722222222222222 = true;
                    isCancelDraw = z722222222222222;
                    if (isCancelDraw) {
                    }
                    isCancelDraw2 = DEBUG_TRAVERSAL;
                    if (isCancelDraw2) {
                    }
                    if (isViewVisible) {
                    }
                    if (this.mAttachInfo.mContentCaptureEvents != null) {
                    }
                    this.mIsInTraversal = false;
                    this.mRelayoutRequested = false;
                    if (cancelAndRedraw) {
                    }
                }
                windowShouldResize = cancelDraw2;
                boolean windowShouldResize22 = windowShouldResize | ((this.mDragResizing || !this.mPendingDragResizing) ? cancelDraw2 : true);
                computesInternalInsets = (!this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners() || this.mAttachInfo.mHasNonEmptyGivenInternalInsets) ? true : cancelDraw2;
                surfaceGenerationId = this.mSurface.getGenerationId();
                int viewVisibility42 = viewVisibility;
                boolean isViewVisible22 = viewVisibility42 == 0;
                surfaceSizeChanged = false;
                surfaceCreated = false;
                boolean surfaceDestroyed2 = false;
                surfaceReplaced = false;
                insetsPending = this.mWindowAttributesChanged;
                if (insetsPending) {
                }
                if (params2 != null) {
                }
                updatedConfiguration2 = this.mFirst;
                if (updatedConfiguration2) {
                }
                frame2 = frame;
                if (Trace.isTagEnabled(8L)) {
                }
                this.mForceNextWindowRelayout = false;
                baseSurfaceHolder = this.mSurfaceHolder;
                if (baseSurfaceHolder != null) {
                }
                boolean hwInitialized42 = false;
                dispatchApplyInsets = false;
                boolean hadSurface2 = this.mSurface.isValid();
                z4 = DEBUG_LAYOUT;
                if (z4) {
                }
                hwInitialized2 = this.mFirst;
                if (!hwInitialized2) {
                }
                this.mViewFrameInfo.flags |= 1;
                int relayoutResult52 = relayoutWindow(params2, viewVisibility42, computesInternalInsets);
                if ((relayoutResult52 & 16) == 16) {
                }
                cancelReason2 = "relayout";
                dragResizing = this.mPendingDragResizing;
                i2 = this.mSyncSeqId;
                insetsPending2 = computesInternalInsets;
                if (i2 > this.mLastSyncSeqId) {
                }
                if ((relayoutResult52 & 2) == 2) {
                }
                if (this.mSurfaceControl.isValid()) {
                }
                if (z4) {
                }
                if (this.mRelayoutRequested) {
                    if (DEBUG_CONFIGURATION) {
                    }
                    performConfigurationChange(new MergedConfiguration(this.mPendingMergedConfiguration), this.mFirst, -1);
                    updatedConfiguration = true;
                }
                boolean updateSurfaceNeeded2 = this.mUpdateSurfaceNeeded;
                this.mUpdateSurfaceNeeded = false;
                surfaceSizeChanged = false;
                if (this.mLastSurfaceSize.equals(this.mSurfaceSize)) {
                }
                if (this.mPendingAlwaysConsumeSystemBars != this.mAttachInfo.mAlwaysConsumeSystemBars) {
                }
                updateColorModeIfNeeded(lp4.getColorMode());
                surfaceCreated = hadSurface2 && this.mSurface.isValid();
                surfaceDestroyed2 = (hadSurface2 || this.mSurface.isValid()) ? false : true;
                if (surfaceGenerationId == this.mSurface.getGenerationId()) {
                }
                if (this.mSurface.isValid()) {
                }
                z5 = false;
                surfaceReplaced = z5;
                if (surfaceReplaced) {
                }
                if (alwaysConsumeSystemBarsChanged) {
                }
                if (updateCaptionInsets()) {
                }
                if (dispatchApplyInsets) {
                }
                this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                dispatchApplyInsets(host);
                dispatchApplyInsets = true;
                if (surfaceCreated) {
                }
                hwInitialized3 = hwInitialized;
                if (this.mDragResizing != dragResizing) {
                }
                if (!this.mUseMTRenderer) {
                }
                if (Trace.isTagEnabled(8L)) {
                }
                hwInitialized42 = hwInitialized3;
                relayoutResult2 = relayoutResult4;
                cancelDraw3 = cancelDraw4;
                dispatchApplyInsets = dispatchApplyInsets3;
                if (DEBUG_ORIENTATION) {
                }
                this.mAttachInfo.mWindowLeft = frame2.left;
                this.mAttachInfo.mWindowTop = frame2.top;
                if (this.mWidth == frame2.width()) {
                }
                this.mWidth = frame2.width();
                this.mHeight = frame2.height();
                if (this.mSurfaceHolder == null) {
                }
                threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                if (threadedRenderer != null) {
                }
                if (this.mStopped) {
                }
                int childWidthMeasureSpec32222222222222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
                int childHeightMeasureSpec32222222222222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
                z3 = DEBUG_LAYOUT;
                if (!z3) {
                }
                performMeasure(childWidthMeasureSpec32222222222222, childHeightMeasureSpec32222222222222);
                int width2222222222222 = host.getMeasuredWidth();
                int height2222222222222 = host.getMeasuredHeight();
                measureAgain = false;
                if (lp4.horizontalWeight <= 0.0f) {
                }
                if (lp4.verticalWeight <= 0.0f) {
                }
                if (measureAgain) {
                }
                layoutRequested3 = true;
                childWidthMeasureSpec = relayoutResult3;
                if (this.mViewMeasureDeferred) {
                }
                if (this.mRelayoutRequested) {
                }
                cancelReason = cancelReason2;
                if (!surfaceSizeChanged) {
                }
                prepareSurfaces();
                this.mChildBoundingInsetsChanged = false;
                this.mForceUpdateBoundsLayer = false;
                this.mFullRedrawNeeded = true;
                if (layoutRequested3) {
                }
                if (didLayout) {
                }
                if (didLayout) {
                }
                didUseTransaction = false;
                if (surfaceCreated) {
                }
                if (didUseTransaction) {
                }
                if (triggerGlobalLayoutListener) {
                }
                Rect contentInsets42222222222222 = null;
                Rect visibleInsets42222222222222 = null;
                Region touchableRegion22222222222222 = null;
                int touchableInsetMode22222222222222 = 3;
                computedInternalInsets = false;
                if (computesInternalInsets) {
                }
                if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
                }
                if (this.mFirst) {
                }
                if (viewVisibilityChanged) {
                }
                isViewVisible = isViewVisible22;
                if (isViewVisible) {
                }
                changedVisibility = false;
                if (changedVisibility) {
                }
                this.mFirst = false;
                this.mWillDrawSoon = false;
                this.mNewSurfaceNeeded = false;
                this.mViewVisibility = viewVisibility2;
                if (this.mAttachInfo.mHasWindowFocus) {
                }
                this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
                if ((childWidthMeasureSpec & 1) != 0) {
                }
                this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
                boolean cancelDueToPreDrawListener222222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
                if (cancelDueToPreDrawListener222222222222222) {
                }
                if (!cancelAndRedraw) {
                }
                boolean z7222222222222222 = true;
                isCancelDraw = z7222222222222222;
                if (isCancelDraw) {
                }
                isCancelDraw2 = DEBUG_TRAVERSAL;
                if (isCancelDraw2) {
                }
                if (isViewVisible) {
                }
                if (this.mAttachInfo.mContentCaptureEvents != null) {
                }
                this.mIsInTraversal = false;
                this.mRelayoutRequested = false;
                if (cancelAndRedraw) {
                }
            }
        }
        z = false;
        boolean viewUserVisibilityChanged2 = z;
        boolean shouldOptimizeMeasure2 = shouldOptimizeMeasure(lp5);
        CompatibilityInfo compatibilityInfo2 = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
        supportsScreen = compatibilityInfo2.supportsScreen();
        z2 = this.mLastInCompatMode;
        if (supportsScreen != z2) {
        }
        Rect frame32 = this.mWinFrame;
        if (this.mFirst) {
        }
        if (viewVisibilityChanged) {
        }
        if (this.mAttachInfo.mWindowVisibility != 0) {
        }
        getRunQueue().executeActions(this.mAttachInfo.mHandler);
        if (this.mFirst) {
        }
        layoutRequested = !this.mLayoutRequested && (!this.mStopped || this.mReportNextDraw);
        if (layoutRequested) {
        }
        if (collectViewAttributes()) {
        }
        if (this.mAttachInfo.mForceReportNewAttributes) {
        }
        if (this.mFirst) {
        }
        this.mAttachInfo.mViewVisibilityChanged = cancelDraw2;
        resizeMode = this.mSoftInputMode & 240;
        if (resizeMode != 0) {
        }
        if (this.mApplyInsetsRequested) {
        }
        if (layoutRequested) {
        }
        if (layoutRequested) {
        }
        lp4 = lp3;
        windowShouldResize = cancelDraw2;
        boolean windowShouldResize222 = windowShouldResize | ((this.mDragResizing || !this.mPendingDragResizing) ? cancelDraw2 : true);
        computesInternalInsets = (!this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners() || this.mAttachInfo.mHasNonEmptyGivenInternalInsets) ? true : cancelDraw2;
        surfaceGenerationId = this.mSurface.getGenerationId();
        int viewVisibility422 = viewVisibility;
        boolean isViewVisible222 = viewVisibility422 == 0;
        surfaceSizeChanged = false;
        surfaceCreated = false;
        boolean surfaceDestroyed22 = false;
        surfaceReplaced = false;
        insetsPending = this.mWindowAttributesChanged;
        if (insetsPending) {
        }
        if (params2 != null) {
        }
        updatedConfiguration2 = this.mFirst;
        if (updatedConfiguration2) {
        }
        frame2 = frame;
        if (Trace.isTagEnabled(8L)) {
        }
        this.mForceNextWindowRelayout = false;
        baseSurfaceHolder = this.mSurfaceHolder;
        if (baseSurfaceHolder != null) {
        }
        boolean hwInitialized422 = false;
        dispatchApplyInsets = false;
        boolean hadSurface22 = this.mSurface.isValid();
        z4 = DEBUG_LAYOUT;
        if (z4) {
        }
        hwInitialized2 = this.mFirst;
        if (!hwInitialized2) {
        }
        this.mViewFrameInfo.flags |= 1;
        int relayoutResult522 = relayoutWindow(params2, viewVisibility422, computesInternalInsets);
        if ((relayoutResult522 & 16) == 16) {
        }
        cancelReason2 = "relayout";
        dragResizing = this.mPendingDragResizing;
        i2 = this.mSyncSeqId;
        insetsPending2 = computesInternalInsets;
        if (i2 > this.mLastSyncSeqId) {
        }
        if ((relayoutResult522 & 2) == 2) {
        }
        if (this.mSurfaceControl.isValid()) {
        }
        if (z4) {
        }
        if (this.mRelayoutRequested) {
        }
        boolean updateSurfaceNeeded22 = this.mUpdateSurfaceNeeded;
        this.mUpdateSurfaceNeeded = false;
        surfaceSizeChanged = false;
        if (this.mLastSurfaceSize.equals(this.mSurfaceSize)) {
        }
        if (this.mPendingAlwaysConsumeSystemBars != this.mAttachInfo.mAlwaysConsumeSystemBars) {
        }
        updateColorModeIfNeeded(lp4.getColorMode());
        surfaceCreated = hadSurface22 && this.mSurface.isValid();
        surfaceDestroyed22 = (hadSurface22 || this.mSurface.isValid()) ? false : true;
        if (surfaceGenerationId == this.mSurface.getGenerationId()) {
        }
        if (this.mSurface.isValid()) {
        }
        z5 = false;
        surfaceReplaced = z5;
        if (surfaceReplaced) {
        }
        if (alwaysConsumeSystemBarsChanged) {
        }
        if (updateCaptionInsets()) {
        }
        if (dispatchApplyInsets) {
        }
        this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
        dispatchApplyInsets(host);
        dispatchApplyInsets = true;
        if (surfaceCreated) {
        }
        hwInitialized3 = hwInitialized;
        if (this.mDragResizing != dragResizing) {
        }
        if (!this.mUseMTRenderer) {
        }
        if (Trace.isTagEnabled(8L)) {
        }
        hwInitialized422 = hwInitialized3;
        relayoutResult2 = relayoutResult4;
        cancelDraw3 = cancelDraw4;
        dispatchApplyInsets = dispatchApplyInsets3;
        if (DEBUG_ORIENTATION) {
        }
        this.mAttachInfo.mWindowLeft = frame2.left;
        this.mAttachInfo.mWindowTop = frame2.top;
        if (this.mWidth == frame2.width()) {
        }
        this.mWidth = frame2.width();
        this.mHeight = frame2.height();
        if (this.mSurfaceHolder == null) {
        }
        threadedRenderer = this.mAttachInfo.mThreadedRenderer;
        if (threadedRenderer != null) {
        }
        if (this.mStopped) {
        }
        int childWidthMeasureSpec322222222222222 = getRootMeasureSpec(this.mWidth, lp4.width, lp4.privateFlags);
        int childHeightMeasureSpec322222222222222 = getRootMeasureSpec(this.mHeight, lp4.height, lp4.privateFlags);
        z3 = DEBUG_LAYOUT;
        if (!z3) {
        }
        performMeasure(childWidthMeasureSpec322222222222222, childHeightMeasureSpec322222222222222);
        int width22222222222222 = host.getMeasuredWidth();
        int height22222222222222 = host.getMeasuredHeight();
        measureAgain = false;
        if (lp4.horizontalWeight <= 0.0f) {
        }
        if (lp4.verticalWeight <= 0.0f) {
        }
        if (measureAgain) {
        }
        layoutRequested3 = true;
        childWidthMeasureSpec = relayoutResult3;
        if (this.mViewMeasureDeferred) {
        }
        if (this.mRelayoutRequested) {
        }
        cancelReason = cancelReason2;
        if (!surfaceSizeChanged) {
        }
        prepareSurfaces();
        this.mChildBoundingInsetsChanged = false;
        this.mForceUpdateBoundsLayer = false;
        this.mFullRedrawNeeded = true;
        if (layoutRequested3) {
        }
        if (didLayout) {
        }
        if (didLayout) {
        }
        didUseTransaction = false;
        if (surfaceCreated) {
        }
        if (didUseTransaction) {
        }
        if (triggerGlobalLayoutListener) {
        }
        Rect contentInsets422222222222222 = null;
        Rect visibleInsets422222222222222 = null;
        Region touchableRegion222222222222222 = null;
        int touchableInsetMode222222222222222 = 3;
        computedInternalInsets = false;
        if (computesInternalInsets) {
        }
        if (computedInternalInsets | (Objects.equals(this.mPreviousTouchableRegion, this.mTouchableRegion) && this.mTouchableRegion != null)) {
        }
        if (this.mFirst) {
        }
        if (viewVisibilityChanged) {
        }
        isViewVisible = isViewVisible222;
        if (isViewVisible) {
        }
        changedVisibility = false;
        if (changedVisibility) {
        }
        this.mFirst = false;
        this.mWillDrawSoon = false;
        this.mNewSurfaceNeeded = false;
        this.mViewVisibility = viewVisibility2;
        if (this.mAttachInfo.mHasWindowFocus) {
        }
        this.mImeFocusController.onTraversal(hasWindowFocus, this.mWindowAttributes);
        if ((childWidthMeasureSpec & 1) != 0) {
        }
        this.mCheckIfCanDraw = !isSyncRequest || cancelDraw3;
        boolean cancelDueToPreDrawListener2222222222222222 = this.mAttachInfo.mTreeObserver.dispatchOnPreDraw();
        if (cancelDueToPreDrawListener2222222222222222) {
        }
        if (!cancelAndRedraw) {
        }
        boolean z72222222222222222 = true;
        isCancelDraw = z72222222222222222;
        if (isCancelDraw) {
        }
        isCancelDraw2 = DEBUG_TRAVERSAL;
        if (isCancelDraw2) {
        }
        if (isViewVisible) {
        }
        if (this.mAttachInfo.mContentCaptureEvents != null) {
        }
        this.mIsInTraversal = false;
        this.mRelayoutRequested = false;
        if (cancelAndRedraw) {
        }
    }

    private void createSyncIfNeeded() {
        if (isInWMSRequestedSync() || !this.mReportNextDraw) {
            return;
        }
        final int seqId = this.mSyncSeqId;
        this.mWmsRequestSyncGroupState = 1;
        this.mWmsRequestSyncGroup = new SurfaceSyncGroup("wmsSync-" + this.mTag, new Consumer() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ViewRootImpl.this.lambda$createSyncIfNeeded$3(seqId, (SurfaceControl.Transaction) obj);
            }
        });
        if (DEBUG_BLAST) {
            Log.m98i(this.mTag, "Setup new sync=" + this.mWmsRequestSyncGroup.getName());
        }
        this.mWmsRequestSyncGroup.add(this, (Runnable) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSyncIfNeeded$3(int seqId, SurfaceControl.Transaction t) {
        if (CoreRune.FW_SURFACE_DEBUG_APPLY && t != null && !TextUtils.isEmpty(t.mDebugName)) {
            t.mDebugName += "_seqId<" + seqId + ">";
        }
        this.mWmsRequestSyncGroupState = 3;
        reportDrawFinished(t, seqId);
    }

    private void notifyContentCaptureEvents() {
        MainContentCaptureSession mainSession;
        Trace.traceBegin(8L, "notifyContentCaptureEvents");
        try {
            try {
                if (CoreRune.FW_CCM_BUG_FIX) {
                    ContentCaptureManager contentCaptureManager = this.mAttachInfo.getContentCaptureManager(this.mContext);
                    mainSession = contentCaptureManager.getMainContentCaptureSession();
                } else {
                    mainSession = this.mAttachInfo.mContentCaptureManager.getMainContentCaptureSession();
                }
                for (int i = 0; i < this.mAttachInfo.mContentCaptureEvents.size(); i++) {
                    int sessionId = this.mAttachInfo.mContentCaptureEvents.keyAt(i);
                    mainSession.notifyViewTreeEvent(sessionId, true);
                    ArrayList<Object> events = this.mAttachInfo.mContentCaptureEvents.valueAt(i);
                    for (int j = 0; j < events.size(); j++) {
                        Object event = events.get(j);
                        if (event instanceof AutofillId) {
                            mainSession.notifyViewDisappeared(sessionId, (AutofillId) event);
                        } else if (event instanceof View) {
                            View view = (View) event;
                            ContentCaptureSession session = view.getContentCaptureSession();
                            if (session == null) {
                                Log.m102w(this.mTag, "no content capture session on view: " + view);
                            } else {
                                int actualId = session.getId();
                                if (actualId != sessionId) {
                                    Log.m102w(this.mTag, "content capture session mismatch for view (" + view + "): was " + sessionId + " before, it's " + actualId + " now");
                                } else {
                                    ViewStructure structure = session.newViewStructure(view);
                                    view.onProvideContentCaptureStructure(structure, 0);
                                    session.notifyViewAppeared(structure);
                                }
                            }
                        } else if (event instanceof Insets) {
                            mainSession.notifyViewInsetsChanged(sessionId, (Insets) event);
                        } else {
                            Log.m102w(this.mTag, "invalid content capture event: " + event);
                        }
                    }
                    mainSession.notifyViewTreeEvent(sessionId, false);
                }
                this.mAttachInfo.mContentCaptureEvents = null;
            } catch (Exception e) {
                Log.m97e(this.mTag, "failed notifyContentCaptureEvents", e);
            }
        } finally {
            Trace.traceEnd(8L);
        }
    }

    private void notifyHolderSurfaceDestroyed() {
        this.mSurfaceHolder.ungetCallbacks();
        SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
        if (callbacks != null) {
            for (SurfaceHolder.Callback c : callbacks) {
                c.surfaceDestroyed(this.mSurfaceHolder);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeHandleWindowMove(Rect frame) {
        boolean windowMoved = (this.mAttachInfo.mWindowLeft == frame.left && this.mAttachInfo.mWindowTop == frame.top) ? false : true;
        if (windowMoved) {
            this.mAttachInfo.mWindowLeft = frame.left;
            this.mAttachInfo.mWindowTop = frame.top;
            this.mForceNextWindowRelayout = true;
        }
        if (windowMoved || this.mAttachInfo.mNeedsUpdateLightCenter) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.setLightCenter(this.mAttachInfo);
            }
            this.mAttachInfo.mNeedsUpdateLightCenter = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWindowFocusChanged() {
        boolean z;
        synchronized (this) {
            if (this.mWindowFocusChanged) {
                boolean bixbyTouchEnable = false;
                this.mWindowFocusChanged = false;
                boolean hasWindowFocus = this.mUpcomingWindowFocus;
                if (this.mAdded) {
                    Log.m98i(this.mTag, "handleWindowFocusChanged: " + (this.mUpcomingWindowFocus ? "1 " : "0 ") + (this.mUpcomingInTouchMode ? "1" : "0") + " call from " + Debug.getCaller());
                }
                if (hasWindowFocus) {
                    InsetsController insetsController = this.mInsetsController;
                    if (getFocusedViewOrNull() == null) {
                        z = false;
                    } else {
                        z = true;
                    }
                    insetsController.onWindowFocusGained(z);
                } else {
                    this.mInsetsController.onWindowFocusLost();
                }
                if (this.mAdded) {
                    dispatchFocusEvent(hasWindowFocus, false);
                    this.mImeFocusController.onPostWindowFocus(getFocusedViewOrNull(), hasWindowFocus, this.mWindowAttributes);
                    if (hasWindowFocus) {
                        this.mWindowAttributes.softInputMode &= -257;
                        ((WindowManager.LayoutParams) this.mView.getLayoutParams()).softInputMode &= -257;
                        maybeFireAccessibilityWindowStateChangedEvent();
                        fireAccessibilityFocusEventIfHasFocusedNode();
                    } else if (this.mPointerCapture) {
                        handlePointerCaptureChanged(false);
                    }
                }
                this.mFirstInputStage.onWindowFocusChanged(hasWindowFocus);
                if (hasWindowFocus) {
                    handleContentCaptureFlush();
                }
                if (CoreRune.BIXBY_TOUCH && hasWindowFocus && this.mSemPressGestureDetector != null) {
                    if (Settings.System.getInt(this.mContext.getContentResolver(), "bixby_touch_enable", 0) == 1) {
                        bixbyTouchEnable = true;
                    }
                    this.mSemPressGestureDetector.setBixbyTouchEnable(bixbyTouchEnable);
                }
            }
        }
    }

    public void dispatchCompatFakeFocus() {
        boolean aboutToHaveFocus;
        synchronized (this) {
            aboutToHaveFocus = this.mWindowFocusChanged && this.mUpcomingWindowFocus;
        }
        boolean alreadyHaveFocus = this.mAttachInfo.mHasWindowFocus;
        if (aboutToHaveFocus || alreadyHaveFocus) {
            return;
        }
        EventLog.writeEvent(LOGTAG_INPUT_FOCUS, "Giving fake focus to " + this.mBasePackageName, "reason=unity bug workaround");
        dispatchFocusEvent(true, true);
        EventLog.writeEvent(LOGTAG_INPUT_FOCUS, "Removing fake focus from " + this.mBasePackageName, "reason=timeout callback");
        dispatchFocusEvent(false, true);
    }

    private void dispatchFocusEvent(boolean hasWindowFocus, boolean fakeFocus) {
        profileRendering(hasWindowFocus);
        if (hasWindowFocus && this.mAttachInfo.mThreadedRenderer != null && this.mSurface.isValid()) {
            this.mFullRedrawNeeded = true;
            try {
                Rect surfaceInsets = this.mWindowAttributes.surfaceInsets;
                this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(this.mWidth, this.mHeight, this.mAttachInfo, this.mSurface, surfaceInsets);
                Log.m94d(this.mTag, String.format("mThreadedRenderer.initializeIfNeeded()#2 mSurface={%s}", "isValid=" + this.mSurface.isValid() + " 0x" + Long.toHexString(this.mSurface.mNativeObject)));
            } catch (Surface.OutOfResourcesException e) {
                Log.m97e(this.mTag, "OutOfResourcesException locking surface", e);
                try {
                    if (!this.mWindowSession.outOfMemory(this.mWindow)) {
                        Slog.m121w(this.mTag, "No processes killed for memory; killing self");
                        Process.killProcess(Process.myPid());
                    }
                } catch (RemoteException e2) {
                }
                ViewRootHandler viewRootHandler = this.mHandler;
                viewRootHandler.sendMessageDelayed(viewRootHandler.obtainMessage(6), 500L);
                return;
            }
        }
        if (this.mFirst) {
            this.mEarlyHasWindowFocus = hasWindowFocus;
        }
        this.mAttachInfo.mHasWindowFocus = hasWindowFocus;
        if (!fakeFocus) {
            this.mImeFocusController.onPreWindowFocus(hasWindowFocus, this.mWindowAttributes);
        }
        if (this.mView != null) {
            this.mAttachInfo.mKeyDispatchState.reset();
            this.mView.dispatchWindowFocusChanged(hasWindowFocus);
            this.mAttachInfo.mTreeObserver.dispatchOnWindowFocusChange(hasWindowFocus);
            if (this.mAttachInfo.mTooltipHost != null) {
                this.mAttachInfo.mTooltipHost.hideTooltip();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWindowTouchModeChanged() {
        boolean inTouchMode;
        synchronized (this) {
            inTouchMode = this.mUpcomingInTouchMode;
        }
        ensureTouchModeLocally(inTouchMode);
    }

    private void maybeFireAccessibilityWindowStateChangedEvent() {
        View view;
        WindowManager.LayoutParams layoutParams = this.mWindowAttributes;
        boolean isToast = layoutParams != null && layoutParams.type == 2005;
        if (!isToast && (view = this.mView) != null) {
            view.sendAccessibilityEvent(32);
        }
    }

    private void fireAccessibilityFocusEventIfHasFocusedNode() {
        View focusedView;
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled() || (focusedView = this.mView.findFocus()) == null) {
            return;
        }
        AccessibilityNodeProvider provider = focusedView.getAccessibilityNodeProvider();
        if (provider == null) {
            focusedView.sendAccessibilityEvent(8);
            return;
        }
        AccessibilityNodeInfo focusedNode = findFocusedVirtualNode(provider);
        if (focusedNode != null) {
            int virtualId = AccessibilityNodeInfo.getVirtualDescendantId(focusedNode.getSourceNodeId());
            AccessibilityEvent event = AccessibilityEvent.obtain(8);
            event.setSource(focusedView, virtualId);
            event.setPackageName(focusedNode.getPackageName());
            event.setChecked(focusedNode.isChecked());
            event.setContentDescription(focusedNode.getContentDescription());
            event.setPassword(focusedNode.isPassword());
            event.getText().add(focusedNode.getText());
            event.setEnabled(focusedNode.isEnabled());
            focusedView.getParent().requestSendAccessibilityEvent(focusedView, event);
            focusedNode.recycle();
        }
    }

    private AccessibilityNodeInfo findFocusedVirtualNode(AccessibilityNodeProvider provider) {
        AccessibilityNodeInfo focusedNode = provider.findFocus(1);
        if (focusedNode != null) {
            return focusedNode;
        }
        if (!this.mContext.isAutofillCompatibilityEnabled()) {
            return null;
        }
        AccessibilityNodeInfo current = provider.createAccessibilityNodeInfo(-1);
        if (current.isFocused()) {
            return current;
        }
        Queue<AccessibilityNodeInfo> fringe = new ArrayDeque<>();
        fringe.offer(current);
        while (!fringe.isEmpty()) {
            AccessibilityNodeInfo current2 = fringe.poll();
            LongArray childNodeIds = current2.getChildNodeIds();
            if (childNodeIds != null && childNodeIds.size() > 0) {
                int childCount = childNodeIds.size();
                for (int i = 0; i < childCount; i++) {
                    int virtualId = AccessibilityNodeInfo.getVirtualDescendantId(childNodeIds.get(i));
                    AccessibilityNodeInfo child = provider.createAccessibilityNodeInfo(virtualId);
                    if (child != null) {
                        if (child.isFocused()) {
                            return child;
                        }
                        fringe.offer(child);
                    }
                }
                current2.recycle();
            }
        }
        return null;
    }

    private void handleOutOfResourcesException(Surface.OutOfResourcesException e) {
        Log.m97e(this.mTag, "OutOfResourcesException initializing HW surface", e);
        try {
            if (!this.mWindowSession.outOfMemory(this.mWindow) && Process.myUid() != 1000) {
                Slog.m121w(this.mTag, "No processes killed for memory; killing self");
                Process.killProcess(Process.myPid());
            }
        } catch (RemoteException e2) {
        }
        this.mLayoutRequested = true;
    }

    private void performMeasure(int childWidthMeasureSpec, int childHeightMeasureSpec) {
        if (this.mView == null) {
            return;
        }
        Trace.traceBegin(8L, "measure");
        try {
            this.mView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            Trace.traceEnd(8L);
            this.mMeasuredWidth = this.mView.getMeasuredWidth();
            this.mMeasuredHeight = this.mView.getMeasuredHeight();
            this.mViewMeasureDeferred = false;
        } catch (Throwable th) {
            Trace.traceEnd(8L);
            throw th;
        }
    }

    boolean isInLayout() {
        return this.mInLayout;
    }

    boolean requestLayoutDuringLayout(View view) {
        if (view.mParent == null || view.mAttachInfo == null) {
            return true;
        }
        if (!this.mLayoutRequesters.contains(view)) {
            this.mLayoutRequesters.add(view);
        }
        return !this.mHandlingLayoutInLayoutRequest;
    }

    private void performLayout(WindowManager.LayoutParams lp, int desiredWindowWidth, int desiredWindowHeight) {
        ArrayList<View> validLayoutRequesters;
        this.mScrollMayChange = true;
        this.mInLayout = true;
        View host = this.mView;
        if (host == null) {
            return;
        }
        if (DEBUG_ORIENTATION || DEBUG_LAYOUT) {
            Log.m100v(this.mTag, "Laying out " + host + " to (" + host.getMeasuredWidth() + ", " + host.getMeasuredHeight() + NavigationBarInflaterView.KEY_CODE_END);
        }
        Trace.traceBegin(8L, TtmlUtils.TAG_LAYOUT);
        try {
            host.layout(0, 0, host.getMeasuredWidth(), host.getMeasuredHeight());
            this.mInLayout = false;
            int numViewsRequestingLayout = this.mLayoutRequesters.size();
            if (numViewsRequestingLayout > 0 && (validLayoutRequesters = getValidLayoutRequesters(this.mLayoutRequesters, false)) != null) {
                this.mHandlingLayoutInLayoutRequest = true;
                int numValidRequests = validLayoutRequesters.size();
                for (int i = 0; i < numValidRequests; i++) {
                    View view = validLayoutRequesters.get(i);
                    Log.m102w("View", "requestLayout() improperly called by " + view + " during layout: running second layout pass");
                    view.requestLayout();
                }
                measureHierarchy(host, lp, this.mView.getContext().getResources(), desiredWindowWidth, desiredWindowHeight, false);
                this.mInLayout = true;
                host.layout(0, 0, host.getMeasuredWidth(), host.getMeasuredHeight());
                this.mHandlingLayoutInLayoutRequest = false;
                final ArrayList<View> validLayoutRequesters2 = getValidLayoutRequesters(this.mLayoutRequesters, true);
                if (validLayoutRequesters2 != null) {
                    getRunQueue().post(new Runnable() { // from class: android.view.ViewRootImpl.4
                        @Override // java.lang.Runnable
                        public void run() {
                            int numValidRequests2 = validLayoutRequesters2.size();
                            for (int i2 = 0; i2 < numValidRequests2; i2++) {
                                View view2 = (View) validLayoutRequesters2.get(i2);
                                Log.m102w("View", "requestLayout() improperly called by " + view2 + " during second layout pass: posting in next frame");
                                view2.requestLayout();
                            }
                        }
                    });
                }
            }
            Trace.traceEnd(8L);
            this.mInLayout = false;
        } catch (Throwable th) {
            Trace.traceEnd(8L);
            throw th;
        }
    }

    private ArrayList<View> getValidLayoutRequesters(ArrayList<View> layoutRequesters, boolean secondLayoutRequests) {
        int numViewsRequestingLayout = layoutRequesters.size();
        ArrayList<View> validLayoutRequesters = null;
        for (int i = 0; i < numViewsRequestingLayout; i++) {
            View view = layoutRequesters.get(i);
            if (view != null && view.mAttachInfo != null && view.mParent != null && (secondLayoutRequests || (view.mPrivateFlags & 4096) == 4096)) {
                boolean gone = false;
                View parent = view;
                while (true) {
                    if (parent == null) {
                        break;
                    }
                    if ((parent.mViewFlags & 12) == 8) {
                        gone = true;
                        break;
                    }
                    if (parent.mParent instanceof View) {
                        parent = (View) parent.mParent;
                    } else {
                        parent = null;
                    }
                }
                if (!gone) {
                    if (validLayoutRequesters == null) {
                        validLayoutRequesters = new ArrayList<>();
                    }
                    validLayoutRequesters.add(view);
                }
            }
        }
        if (!secondLayoutRequests) {
            for (int i2 = 0; i2 < numViewsRequestingLayout; i2++) {
                View view2 = layoutRequesters.get(i2);
                while (view2 != null && (view2.mPrivateFlags & 4096) != 0) {
                    view2.mPrivateFlags &= -4097;
                    if (view2.mParent instanceof View) {
                        view2 = (View) view2.mParent;
                    } else {
                        view2 = null;
                    }
                }
            }
        }
        layoutRequesters.clear();
        return validLayoutRequesters;
    }

    @Override // android.view.ViewParent
    public void requestTransparentRegion(View child) {
        checkThread();
        View view = this.mView;
        if (view != child) {
            return;
        }
        if ((view.mPrivateFlags & 512) == 0) {
            this.mView.mPrivateFlags |= 512;
            this.mWindowAttributesChanged = true;
        }
        requestLayout();
    }

    private static int getRootMeasureSpec(int windowSize, int measurement, int privateFlags) {
        int rootDimension = (privateFlags & 4096) != 0 ? -1 : measurement;
        switch (rootDimension) {
            case -2:
                int measureSpec = View.MeasureSpec.makeMeasureSpec(windowSize, Integer.MIN_VALUE);
                return measureSpec;
            case -1:
                int measureSpec2 = View.MeasureSpec.makeMeasureSpec(windowSize, 1073741824);
                return measureSpec2;
            default:
                int measureSpec3 = View.MeasureSpec.makeMeasureSpec(rootDimension, 1073741824);
                return measureSpec3;
        }
    }

    @Override // android.view.ThreadedRenderer.DrawCallbacks
    public void onPreDraw(RecordingCanvas canvas) {
        if (this.mCurScrollY != 0 && this.mHardwareYOffset != 0 && this.mAttachInfo.mThreadedRenderer.isOpaque()) {
            canvas.drawColor(-16777216);
        }
        canvas.translate(-this.mHardwareXOffset, -this.mHardwareYOffset);
    }

    @Override // android.view.ThreadedRenderer.DrawCallbacks
    public void onPostDraw(RecordingCanvas canvas) {
        drawAccessibilityFocusedDrawableIfNeeded(canvas);
        if (this.mUseMTRenderer) {
            for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                this.mWindowCallbacks.get(i).onPostDraw(canvas);
            }
        }
    }

    void outputDisplayList(View view) {
        view.mRenderNode.output();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void profileRendering(boolean enabled) {
        if (this.mProfileRendering) {
            this.mRenderProfilingEnabled = enabled;
            Choreographer.FrameCallback frameCallback = this.mRenderProfiler;
            if (frameCallback != null) {
                this.mChoreographer.removeFrameCallback(frameCallback);
            }
            if (this.mRenderProfilingEnabled) {
                if (this.mRenderProfiler == null) {
                    this.mRenderProfiler = new Choreographer.FrameCallback() { // from class: android.view.ViewRootImpl.5
                        @Override // android.view.Choreographer.FrameCallback
                        public void doFrame(long frameTimeNanos) {
                            ViewRootImpl.this.mDirty.set(0, 0, ViewRootImpl.this.mWidth, ViewRootImpl.this.mHeight);
                            if (ViewRootImpl.DEBUG_TRAVERSAL && ViewRootImpl.DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ViewRootImpl.this.mView.getContext().getPackageName())) {
                                Log.m98i(ViewRootImpl.this.mTag, "Traversal, [12] mView=" + ViewRootImpl.this.mView);
                            }
                            ViewRootImpl.this.scheduleTraversals();
                            if (ViewRootImpl.this.mRenderProfilingEnabled) {
                                ViewRootImpl.this.mChoreographer.postFrameCallback(ViewRootImpl.this.mRenderProfiler);
                            }
                        }
                    };
                }
                this.mChoreographer.postFrameCallback(this.mRenderProfiler);
                return;
            }
            this.mRenderProfiler = null;
        }
    }

    private void trackFPS() {
        long nowTime = System.currentTimeMillis();
        if (this.mFpsStartTime < 0) {
            this.mFpsPrevTime = nowTime;
            this.mFpsStartTime = nowTime;
            this.mFpsNumFrames = 0;
            return;
        }
        this.mFpsNumFrames++;
        String thisHash = Integer.toHexString(System.identityHashCode(this));
        long frameTime = nowTime - this.mFpsPrevTime;
        long totalTime = nowTime - this.mFpsStartTime;
        Log.m100v(this.mTag, "0x" + thisHash + "\tFrame time:\t" + frameTime);
        this.mFpsPrevTime = nowTime;
        if (totalTime > 1000) {
            float fps = (this.mFpsNumFrames * 1000.0f) / totalTime;
            Log.m100v(this.mTag, "0x" + thisHash + "\tFPS:\t" + fps);
            this.mFpsStartTime = nowTime;
            this.mFpsNumFrames = 0;
        }
    }

    private void reportDrawFinished(SurfaceControl.Transaction t, int seqId) {
        if (DEBUG_BLAST) {
            Log.m98i(this.mTag, "reportDrawFinished seqId=" + seqId);
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.instant(8L, "reportDrawFinished " + this.mTag + " seqId=" + seqId);
        }
        try {
            try {
                this.mWindowSession.finishDrawing(this.mWindow, t, seqId);
                if (t == null) {
                    return;
                }
            } catch (RemoteException e) {
                Log.m97e(this.mTag, "Unable to report draw finished", e);
                if (t != null) {
                    t.apply();
                }
                if (t == null) {
                    return;
                }
            }
            t.clear();
        } catch (Throwable th) {
            if (t != null) {
                t.clear();
            }
            throw th;
        }
    }

    public boolean isHardwareEnabled() {
        return this.mAttachInfo.mThreadedRenderer != null && this.mAttachInfo.mThreadedRenderer.isEnabled();
    }

    public boolean isInWMSRequestedSync() {
        return this.mWmsRequestSyncGroup != null;
    }

    private void addFrameCommitCallbackIfNeeded() {
        if (!isHardwareEnabled()) {
            return;
        }
        final ArrayList<Runnable> commitCallbacks = this.mAttachInfo.mTreeObserver.captureFrameCommitCallbacks();
        boolean needFrameCommitCallback = commitCallbacks != null && commitCallbacks.size() > 0;
        if (!needFrameCommitCallback) {
            return;
        }
        Log.m94d(this.mTag, "Creating frameCommitCallback commitCallbacks size=" + commitCallbacks.size());
        this.mAttachInfo.mThreadedRenderer.setFrameCommitCallback(new HardwareRenderer.FrameCommitCallback() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda16
            @Override // android.graphics.HardwareRenderer.FrameCommitCallback
            public final void onFrameCommit(boolean z) {
                ViewRootImpl.this.lambda$addFrameCommitCallbackIfNeeded$5(commitCallbacks, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addFrameCommitCallbackIfNeeded$5(final ArrayList commitCallbacks, boolean didProduceBuffer) {
        Log.m94d(this.mTag, "Received frameCommitCallback didProduceBuffer=" + didProduceBuffer);
        this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ViewRootImpl.lambda$addFrameCommitCallbackIfNeeded$4(commitCallbacks);
            }
        });
    }

    static /* synthetic */ void lambda$addFrameCommitCallbackIfNeeded$4(ArrayList commitCallbacks) {
        for (int i = 0; i < commitCallbacks.size(); i++) {
            ((Runnable) commitCallbacks.get(i)).run();
        }
    }

    private void registerCallbackForPendingTransactions() {
        SurfaceControl.Transaction t = new SurfaceControl.Transaction();
        t.merge(this.mPendingTransaction);
        Log.m98i(this.mTag, "registerCallbackForPendingTransactions");
        registerRtFrameCallback(new C37386(t));
    }

    /* renamed from: android.view.ViewRootImpl$6 */
    class C37386 implements HardwareRenderer.FrameDrawingCallback {
        final /* synthetic */ SurfaceControl.Transaction val$t;

        C37386(SurfaceControl.Transaction transaction) {
            this.val$t = transaction;
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public HardwareRenderer.FrameCommitCallback onFrameDraw(int syncResult, final long frame) {
            ViewRootImpl.this.mergeWithNextTransaction(this.val$t, frame);
            if ((syncResult & 6) != 0) {
                if (ViewRootImpl.this.mBlastBufferQueue != null) {
                    ViewRootImpl.this.mBlastBufferQueue.applyPendingTransactions(frame);
                    return null;
                }
                return null;
            }
            return new HardwareRenderer.FrameCommitCallback() { // from class: android.view.ViewRootImpl$6$$ExternalSyntheticLambda0
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z) {
                    ViewRootImpl.C37386.this.lambda$onFrameDraw$0(frame, z);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$0(long frame, boolean didProduceBuffer) {
            if (!didProduceBuffer && ViewRootImpl.this.mBlastBufferQueue != null) {
                ViewRootImpl.this.mBlastBufferQueue.applyPendingTransactions(frame);
            }
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public void onFrameDraw(long frame) {
        }
    }

    private boolean performDraw(final SurfaceSyncGroup surfaceSyncGroup) {
        this.mLastPerformDrawSkippedReason = null;
        if (!this.mReportNextDraw) {
            switch (this.mAttachInfo.mDisplayState) {
                case 1:
                    if (this.mDeferTransactionRequested) {
                        this.mDeferTransactionRequested = false;
                        Log.m98i(this.mTag, "performDraw() SCREEN_OFF but mDeferTransactionRequested = true break");
                        break;
                    } else {
                        this.mLastPerformDrawSkippedReason = "screen_off";
                        return false;
                    }
                case 3:
                case 4:
                    if (this.mDisplay.getDisplayId() == 0 && (this.mWindowAttributes.samsungFlags & 262144) == 0 && Settings.System.getInt(this.mContentResolver, AOD_SHOW_STATE, 0) != 0) {
                        Log.m98i(this.mTag, "performDraw() was skipped by AOD_SHOW_STATE... DisplayState = " + this.mAttachInfo.mDisplayState);
                        return false;
                    }
                    break;
            }
        } else if (this.mView == null) {
            this.mLastPerformDrawSkippedReason = "no_root_view";
            return false;
        }
        boolean fullRedrawNeeded = this.mFullRedrawNeeded || surfaceSyncGroup != null;
        this.mFullRedrawNeeded = false;
        this.mIsDrawing = true;
        Trace.traceBegin(8L, "draw");
        addFrameCommitCallbackIfNeeded();
        try {
            boolean usingAsyncReport = draw(fullRedrawNeeded, surfaceSyncGroup, this.mSyncBuffer);
            if (this.mAttachInfo.mThreadedRenderer != null && !usingAsyncReport) {
                this.mAttachInfo.mThreadedRenderer.setFrameCallback(null);
            }
            this.mIsDrawing = false;
            Trace.traceEnd(8L);
            if (this.mAttachInfo.mPendingAnimatingRenderNodes != null) {
                int count = this.mAttachInfo.mPendingAnimatingRenderNodes.size();
                for (int i = 0; i < count; i++) {
                    this.mAttachInfo.mPendingAnimatingRenderNodes.get(i).endAllAnimators();
                }
                this.mAttachInfo.mPendingAnimatingRenderNodes.clear();
            }
            if (this.mReportNextDraw) {
                CountDownLatch countDownLatch = this.mWindowDrawCountDown;
                if (countDownLatch != null) {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        Log.m96e(this.mTag, "Window redraw count down interrupted!");
                    }
                    this.mWindowDrawCountDown = null;
                }
                if (this.mAttachInfo.mThreadedRenderer != null) {
                    this.mAttachInfo.mThreadedRenderer.setStopped(this.mStopped);
                }
                if (this.mSurfaceHolder != null && this.mSurface.isValid()) {
                    usingAsyncReport = true;
                    SurfaceCallbackHelper sch = new SurfaceCallbackHelper(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewRootImpl.lambda$performDraw$6(SurfaceSyncGroup.this);
                        }
                    });
                    SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
                    sch.dispatchSurfaceRedrawNeededAsync(this.mSurfaceHolder, callbacks);
                } else if (!usingAsyncReport && this.mAttachInfo.mThreadedRenderer != null) {
                    Trace.traceBegin(8L, "fence");
                    this.mAttachInfo.mThreadedRenderer.fence();
                    Trace.traceEnd(8L);
                }
            }
            if (surfaceSyncGroup != null && !usingAsyncReport) {
                surfaceSyncGroup.markSyncReady();
            }
            if (this.mPerformContentCapture) {
                performContentCaptureInitialReport();
            }
            return true;
        } catch (Throwable th) {
            this.mIsDrawing = false;
            Trace.traceEnd(8L);
            throw th;
        }
    }

    static /* synthetic */ void lambda$performDraw$6(SurfaceSyncGroup surfaceSyncGroup) {
        if (surfaceSyncGroup != null) {
            surfaceSyncGroup.markSyncReady();
        }
    }

    private boolean isContentCaptureEnabled() {
        switch (this.mContentCaptureEnabled) {
            case 0:
                boolean reallyEnabled = isContentCaptureReallyEnabled();
                this.mContentCaptureEnabled = reallyEnabled ? 1 : 2;
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                Log.m102w(TAG, "isContentCaptureEnabled(): invalid state " + this.mContentCaptureEnabled);
                break;
        }
        return false;
    }

    private boolean isContentCaptureReallyEnabled() {
        ContentCaptureManager ccm;
        return (this.mContext.getContentCaptureOptions() == null || (ccm = this.mAttachInfo.getContentCaptureManager(this.mContext)) == null || !ccm.isContentCaptureEnabled()) ? false : true;
    }

    private void performContentCaptureInitialReport() {
        this.mPerformContentCapture = false;
        View rootView = this.mView;
        if (DEBUG_CONTENT_CAPTURE) {
            Log.m100v(this.mTag, "performContentCaptureInitialReport() on " + rootView);
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.traceBegin(8L, "dispatchContentCapture() for " + getClass().getSimpleName());
        }
        try {
            if (!isContentCaptureEnabled()) {
                return;
            }
            if (this.mAttachInfo.mContentCaptureManager != null) {
                MainContentCaptureSession session = this.mAttachInfo.mContentCaptureManager.getMainContentCaptureSession();
                session.notifyWindowBoundsChanged(session.getId(), getConfiguration().windowConfiguration.getBounds());
            }
            rootView.dispatchInitialProvideContentCaptureStructure();
        } finally {
            Trace.traceEnd(8L);
        }
    }

    private void handleContentCaptureFlush() {
        if (DEBUG_CONTENT_CAPTURE) {
            Log.m100v(this.mTag, "handleContentCaptureFlush()");
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.traceBegin(8L, "flushContentCapture for " + getClass().getSimpleName());
        }
        try {
            if (isContentCaptureEnabled()) {
                ContentCaptureManager ccm = this.mAttachInfo.mContentCaptureManager;
                if (ccm == null) {
                    Log.m102w(TAG, "No ContentCapture on AttachInfo");
                } else {
                    ccm.flush(2);
                }
            }
        } finally {
            Trace.traceEnd(8L);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:126:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0436  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x024e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean draw(boolean fullRedrawNeeded, SurfaceSyncGroup activeSyncGroup, boolean syncBuffer) {
        int curScrollY;
        boolean fullRedrawNeeded2;
        int xOffset;
        boolean accessibilityFocusDirty;
        boolean accessibilityFocusDirty2;
        boolean z;
        int yOffset;
        WindowManager.LayoutParams params;
        Scroller scroller;
        Surface surface = this.mSurface;
        if (!surface.isValid()) {
            Log.m96e(this.mTag, "Surface is not valid.");
            return false;
        }
        if (DEBUG_FPS) {
            trackFPS();
        }
        if (!sFirstDrawComplete) {
            ArrayList<Runnable> arrayList = sFirstDrawHandlers;
            synchronized (arrayList) {
                sFirstDrawComplete = true;
                int count = arrayList.size();
                for (int i = 0; i < count; i++) {
                    this.mHandler.post(sFirstDrawHandlers.get(i));
                }
            }
        }
        scrollToRectOrFocus(null, false);
        if (this.mAttachInfo.mViewScrollChanged) {
            this.mAttachInfo.mViewScrollChanged = false;
            this.mAttachInfo.mTreeObserver.dispatchOnScrollChanged();
        }
        Scroller scroller2 = this.mScroller;
        boolean animating = scroller2 != null && scroller2.computeScrollOffset();
        if (animating) {
            curScrollY = this.mScroller.getCurrY();
        } else {
            int curScrollY2 = this.mScrollY;
            curScrollY = curScrollY2;
        }
        if (this.mCurScrollY == curScrollY) {
            fullRedrawNeeded2 = fullRedrawNeeded;
        } else {
            this.mCurScrollY = curScrollY;
            KeyEvent.Callback callback = this.mView;
            if (callback instanceof RootViewSurfaceTaker) {
                ((RootViewSurfaceTaker) callback).onRootViewScrollYChanged(curScrollY);
            }
            fullRedrawNeeded2 = true;
        }
        float appScale = this.mAttachInfo.mApplicationScale;
        boolean scalingRequired = this.mAttachInfo.mScalingRequired;
        Rect dirty = this.mDirty;
        if (this.mSurfaceHolder != null) {
            dirty.setEmpty();
            if (animating && (scroller = this.mScroller) != null) {
                scroller.abortAnimation();
            }
            return false;
        }
        if (fullRedrawNeeded2) {
            dirty.set(0, 0, (int) ((this.mWidth * appScale) + 0.5f), (int) ((this.mHeight * appScale) + 0.5f));
        }
        if (DEBUG_ORIENTATION || DEBUG_DRAW) {
            Log.m100v(this.mTag, "Draw " + this.mView + "/" + ((Object) this.mWindowAttributes.getTitle()) + ": dirty={" + dirty.left + "," + dirty.top + "," + dirty.right + "," + dirty.bottom + "} surface=" + surface + " surface.isValid()=" + surface.isValid() + ", appScale:" + appScale + ", width=" + this.mWidth + ", height=" + this.mHeight);
        }
        this.mAttachInfo.mTreeObserver.dispatchOnDraw();
        int xOffset2 = -this.mCanvasOffsetX;
        int yOffset2 = (-this.mCanvasOffsetY) + curScrollY;
        WindowManager.LayoutParams params2 = this.mWindowAttributes;
        Rect surfaceInsets = params2 != null ? params2.surfaceInsets : null;
        if (surfaceInsets == null) {
            xOffset = xOffset2;
        } else {
            int xOffset3 = xOffset2 - surfaceInsets.left;
            yOffset2 -= surfaceInsets.top;
            dirty.offset(surfaceInsets.left, surfaceInsets.top);
            xOffset = xOffset3;
        }
        Drawable drawable = this.mAttachInfo.mAccessibilityFocusDrawable;
        if (drawable == null) {
            accessibilityFocusDirty = false;
        } else {
            Rect bounds = this.mAttachInfo.mTmpInvalRect;
            boolean hasFocus = getAccessibilityFocusedRect(bounds);
            if (!hasFocus) {
                bounds.setEmpty();
            }
            accessibilityFocusDirty = false;
            if (!bounds.equals(drawable.getBounds())) {
                accessibilityFocusDirty2 = true;
                this.mAttachInfo.mDrawingTime = this.mChoreographer.getFrameTimeNanos() / 1000000;
                z = DEBUG_TRAVERSAL;
                if (z && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
                    Log.m98i(this.mTag, "Traversal, [13] mView=" + this.mView + " dirty.isEmpty=" + dirty.isEmpty() + " mIsAnimating=" + this.mIsAnimating + " accessibilityFocusDirty=" + accessibilityFocusDirty2 + " mForceDraw=" + this.mForceDraw + " mUpdateHdrSdrRatioInfo=" + this.mUpdateHdrSdrRatioInfo);
                }
                boolean useAsyncReport = false;
                if (dirty.isEmpty() || this.mIsAnimating || accessibilityFocusDirty2 || this.mForceDraw || this.mUpdateHdrSdrRatioInfo) {
                    if (!isHardwareEnabled()) {
                        boolean invalidateRoot = accessibilityFocusDirty2 || this.mInvalidateRootRequested;
                        this.mInvalidateRootRequested = false;
                        this.mIsAnimating = false;
                        if (this.mHardwareYOffset != yOffset2 || this.mHardwareXOffset != xOffset) {
                            this.mHardwareYOffset = yOffset2;
                            this.mHardwareXOffset = xOffset;
                            invalidateRoot = true;
                        }
                        if (invalidateRoot) {
                            this.mAttachInfo.mThreadedRenderer.invalidateRoot();
                        }
                        dirty.setEmpty();
                        boolean updated = updateContentDrawBounds();
                        if (this.mReportNextDraw) {
                            this.mAttachInfo.mThreadedRenderer.setStopped(false);
                        }
                        if (updated) {
                            requestDrawWindow();
                        }
                        useAsyncReport = true;
                        if (this.mUpdateHdrSdrRatioInfo) {
                            this.mUpdateHdrSdrRatioInfo = false;
                            yOffset = yOffset2;
                            params = params2;
                            applyTransactionOnDraw(this.mTransaction.setExtendedRangeBrightness(getSurfaceControl(), this.mRenderHdrSdrRatio, this.mDesiredHdrSdrRatio));
                            this.mAttachInfo.mThreadedRenderer.setTargetHdrSdrRatio(this.mRenderHdrSdrRatio);
                        } else {
                            yOffset = yOffset2;
                            params = params2;
                        }
                        if (activeSyncGroup != null) {
                            registerCallbacksForSync(syncBuffer, activeSyncGroup);
                            if (syncBuffer) {
                                this.mAttachInfo.mThreadedRenderer.forceDrawNextFrame();
                            }
                        } else if (this.mHasPendingTransactions) {
                            registerCallbackForPendingTransactions();
                        }
                        this.mHasPendingTransactions = false;
                        if (this.mForceDraw) {
                            Log.m98i(this.mTag, "Force to draw even when frame is empty");
                            this.mForceDraw = false;
                        }
                        this.mAttachInfo.mThreadedRenderer.draw(this.mView, this.mAttachInfo, this);
                    } else {
                        int yOffset3 = yOffset2;
                        if (this.mAttachInfo.mThreadedRenderer != null && !this.mAttachInfo.mThreadedRenderer.isEnabled() && this.mAttachInfo.mThreadedRenderer.isRequested() && this.mSurface.isValid()) {
                            if (z && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
                                Log.m98i(this.mTag, "Traversal, [13-1] mView=" + this.mView + " isImpossibleRenderer=" + isImpossibleRenderer());
                            }
                            if (!isImpossibleRenderer()) {
                                try {
                                    this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(this.mWidth, this.mHeight, this.mAttachInfo, this.mSurface, surfaceInsets);
                                    if (ViewRune.COMMON_IS_PRODUCT_DEV) {
                                        Log.m94d(this.mTag, String.format("mThreadedRenderer.initializeIfNeeded()#1 mSurface={%s}", "isValid=" + this.mSurface.isValid() + " 0x" + Long.toHexString(this.mSurface.mNativeObject)));
                                    }
                                    this.mFullRedrawNeeded = true;
                                    scheduleTraversals();
                                    return false;
                                } catch (Surface.OutOfResourcesException e) {
                                    handleOutOfResourcesException(e);
                                    return false;
                                }
                            }
                            Log.m96e(this.mTag, "isImpossibleRenderer() Blocked ThreadedRenderer.initializeIfNeeded()");
                            this.mFullRedrawNeeded = true;
                            scheduleTraversals();
                            return false;
                        }
                        if (!drawSoftware(surface, this.mAttachInfo, xOffset, yOffset3, scalingRequired, dirty, surfaceInsets)) {
                            return false;
                        }
                    }
                }
                if (z && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
                    Log.m98i(this.mTag, "Traversal, [13-2] mView=" + this.mView + " animating=" + animating);
                }
                if (animating) {
                    this.mFullRedrawNeeded = true;
                    scheduleTraversals();
                }
                return useAsyncReport;
            }
        }
        accessibilityFocusDirty2 = accessibilityFocusDirty;
        this.mAttachInfo.mDrawingTime = this.mChoreographer.getFrameTimeNanos() / 1000000;
        z = DEBUG_TRAVERSAL;
        if (z) {
            Log.m98i(this.mTag, "Traversal, [13] mView=" + this.mView + " dirty.isEmpty=" + dirty.isEmpty() + " mIsAnimating=" + this.mIsAnimating + " accessibilityFocusDirty=" + accessibilityFocusDirty2 + " mForceDraw=" + this.mForceDraw + " mUpdateHdrSdrRatioInfo=" + this.mUpdateHdrSdrRatioInfo);
        }
        boolean useAsyncReport2 = false;
        if (dirty.isEmpty()) {
        }
        if (!isHardwareEnabled()) {
        }
        if (z) {
            Log.m98i(this.mTag, "Traversal, [13-2] mView=" + this.mView + " animating=" + animating);
        }
        if (animating) {
        }
        return useAsyncReport2;
    }

    private boolean drawSoftware(Surface surface, View.AttachInfo attachInfo, int xoff, int yoff, boolean scalingRequired, Rect dirty, Rect surfaceInsets) {
        try {
            Canvas canvas = this.mSurface.lockCanvas(dirty);
            canvas.setDensity(this.mDensity);
            try {
                try {
                    if (DEBUG_ORIENTATION || DEBUG_DRAW) {
                        Log.m100v(this.mTag, "Surface " + surface + " drawing to bitmap w=" + canvas.getWidth() + ", h=" + canvas.getHeight() + ", dirty: " + dirty + ", xOff=" + xoff + ", yOff=" + yoff);
                    }
                    if (!canvas.isOpaque() || yoff != 0 || xoff != 0) {
                        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    }
                    dirty.setEmpty();
                    this.mIsAnimating = false;
                    this.mView.mPrivateFlags |= 32;
                    if (DEBUG_DRAW) {
                        Context cxt = this.mView.getContext();
                        Log.m98i(this.mTag, "Drawing: package:" + cxt.getPackageName() + ", metrics=" + cxt.getResources().getDisplayMetrics() + ", compatibilityInfo=" + cxt.getResources().getCompatibilityInfo());
                    }
                    canvas.translate(-xoff, -yoff);
                    CompatibilityInfo.Translator translator = this.mTranslator;
                    if (translator != null) {
                        translator.translateCanvas(canvas);
                    }
                    canvas.setScreenDensity(scalingRequired ? this.mNoncompatDensity : 0);
                    this.mView.draw(canvas);
                    View view = this.mView;
                    if ((view instanceof DecorView) && ((DecorView) view).isFreeformMode()) {
                        ((DecorView) this.mView).drawFrameIfNeeded(canvas);
                    }
                    drawAccessibilityFocusedDrawableIfNeeded(canvas);
                    return true;
                } finally {
                    surface.unlockCanvasAndPost(canvas);
                }
            } catch (IllegalArgumentException e) {
                Log.m97e(this.mTag, "Could not unlock surface", e);
                this.mLayoutRequested = true;
                return false;
            }
        } catch (Surface.OutOfResourcesException e2) {
            handleOutOfResourcesException(e2);
            return false;
        } catch (IllegalArgumentException e3) {
            Log.m97e(this.mTag, "Could not lock surface", e3);
            this.mLayoutRequested = true;
            return false;
        }
    }

    private void drawAccessibilityFocusedDrawableIfNeeded(Canvas canvas) {
        Rect bounds = this.mAttachInfo.mTmpInvalRect;
        if (getAccessibilityFocusedRect(bounds)) {
            Drawable drawable = getAccessibilityFocusedDrawable();
            if (drawable != null) {
                drawable.setBounds(bounds);
                drawable.draw(canvas);
                return;
            }
            return;
        }
        if (this.mAttachInfo.mAccessibilityFocusDrawable != null) {
            this.mAttachInfo.mAccessibilityFocusDrawable.setBounds(0, 0, 0, 0);
        }
    }

    private boolean getAccessibilityFocusedRect(Rect bounds) {
        View host;
        AccessibilityManager manager = AccessibilityManager.getInstance(this.mView.mContext);
        if (!manager.isEnabled() || !manager.isTouchExplorationEnabled() || (host = this.mAccessibilityFocusedHost) == null || host.mAttachInfo == null) {
            return false;
        }
        AccessibilityNodeProvider provider = host.getAccessibilityNodeProvider();
        if (provider == null) {
            host.getBoundsOnScreen(bounds, true);
        } else {
            AccessibilityNodeInfo accessibilityNodeInfo = this.mAccessibilityFocusedVirtualView;
            if (accessibilityNodeInfo == null) {
                return false;
            }
            accessibilityNodeInfo.getBoundsInScreen(bounds);
        }
        View.AttachInfo attachInfo = this.mAttachInfo;
        bounds.offset(0, attachInfo.mViewRootImpl.mScrollY);
        bounds.offset(-attachInfo.mWindowLeft, -attachInfo.mWindowTop);
        if (!bounds.intersect(0, 0, attachInfo.mViewRootImpl.mWidth, attachInfo.mViewRootImpl.mHeight)) {
            bounds.setEmpty();
        }
        return !bounds.isEmpty();
    }

    private Drawable getAccessibilityFocusedDrawable() {
        if (this.mAttachInfo.mAccessibilityFocusDrawable == null) {
            TypedValue value = new TypedValue();
            boolean resolved = this.mView.mContext.getTheme().resolveAttribute(C4337R.attr.accessibilityFocusedDrawable, value, true);
            if (resolved) {
                this.mAttachInfo.mAccessibilityFocusDrawable = this.mView.mContext.getDrawable(value.resourceId);
            }
        }
        if (this.mAttachInfo.mAccessibilityFocusDrawable instanceof GradientDrawable) {
            GradientDrawable drawable = (GradientDrawable) this.mAttachInfo.mAccessibilityFocusDrawable;
            drawable.setStroke(this.mAccessibilityManager.semGetAccessibilityFocusStrokeWidth(this.mContext), this.mAccessibilityManager.getAccessibilityFocusColor());
        }
        return this.mAttachInfo.mAccessibilityFocusDrawable;
    }

    void updateSystemGestureExclusionRectsForView(View view) {
        this.mGestureExclusionTracker.updateRectsForView(view);
        this.mHandler.sendEmptyMessage(30);
    }

    void systemGestureExclusionChanged() {
        List<Rect> rectsForWindowManager = this.mGestureExclusionTracker.computeChangedRects();
        if (rectsForWindowManager != null && this.mView != null) {
            try {
                this.mWindowSession.reportSystemGestureExclusionChanged(this.mWindow, rectsForWindowManager);
                this.mAttachInfo.mTreeObserver.dispatchOnSystemGestureExclusionRectsChanged(rectsForWindowManager);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setRootSystemGestureExclusionRects(List<Rect> rects) {
        this.mGestureExclusionTracker.setRootRects(rects);
        this.mHandler.sendEmptyMessage(30);
    }

    public List<Rect> getRootSystemGestureExclusionRects() {
        return this.mGestureExclusionTracker.getRootRects();
    }

    void updateKeepClearRectsForView(View view) {
        this.mKeepClearRectsTracker.updateRectsForView(view);
        this.mUnrestrictedKeepClearRectsTracker.updateRectsForView(view);
        this.mHandler.sendEmptyMessage(35);
    }

    private void updateKeepClearForAccessibilityFocusRect() {
        if (this.mViewConfiguration.isPreferKeepClearForFocusEnabled()) {
            if (this.mKeepClearAccessibilityFocusRect == null) {
                this.mKeepClearAccessibilityFocusRect = new Rect();
            }
            boolean hasAccessibilityFocus = getAccessibilityFocusedRect(this.mKeepClearAccessibilityFocusRect);
            if (!hasAccessibilityFocus) {
                this.mKeepClearAccessibilityFocusRect.setEmpty();
            }
            this.mHandler.obtainMessage(35, 1, 0).sendToTarget();
        }
    }

    void keepClearRectsChanged(boolean accessibilityFocusRectChanged) {
        boolean restrictedKeepClearRectsChanged = this.mKeepClearRectsTracker.computeChanges();
        boolean unrestrictedKeepClearRectsChanged = this.mUnrestrictedKeepClearRectsTracker.computeChanges();
        if ((restrictedKeepClearRectsChanged || unrestrictedKeepClearRectsChanged || accessibilityFocusRectChanged) && this.mView != null) {
            this.mHasPendingKeepClearAreaChange = true;
            if (!this.mHandler.hasMessages(36)) {
                this.mHandler.sendEmptyMessageDelayed(36, 100L);
                reportKeepClearAreasChanged();
            }
        }
    }

    void reportKeepClearAreasChanged() {
        if (!this.mHasPendingKeepClearAreaChange || this.mView == null) {
            return;
        }
        this.mHasPendingKeepClearAreaChange = false;
        List<Rect> restrictedKeepClearRects = this.mKeepClearRectsTracker.getLastComputedRects();
        List<Rect> unrestrictedKeepClearRects = this.mUnrestrictedKeepClearRectsTracker.getLastComputedRects();
        Rect rect = this.mKeepClearAccessibilityFocusRect;
        if (rect != null && !rect.isEmpty()) {
            restrictedKeepClearRects = new ArrayList(restrictedKeepClearRects);
            restrictedKeepClearRects.add(this.mKeepClearAccessibilityFocusRect);
        }
        try {
            this.mWindowSession.reportKeepClearAreasChanged(this.mWindow, restrictedKeepClearRects, unrestrictedKeepClearRects);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestInvalidateRootRenderNode() {
        this.mInvalidateRootRequested = true;
    }

    boolean scrollToRectOrFocus(Rect rectangle, boolean immediate) {
        Rect rectangle2;
        int scrollY;
        Rect ci = this.mAttachInfo.mContentInsets;
        Rect vi = this.mAttachInfo.mVisibleInsets;
        int scrollY2 = 0;
        boolean handled = false;
        if (vi.left > ci.left || vi.top > ci.top || vi.right > ci.right || vi.bottom > ci.bottom) {
            int scrollY3 = this.mScrollY;
            View focus = this.mView.findFocus();
            if (focus == null) {
                return false;
            }
            WeakReference<View> weakReference = this.mLastScrolledFocus;
            View lastScrolledFocus = weakReference != null ? weakReference.get() : null;
            if (focus == lastScrolledFocus) {
                rectangle2 = rectangle;
            } else {
                rectangle2 = null;
            }
            boolean z = DEBUG_INPUT_RESIZE;
            if (z) {
                Log.m100v(this.mTag, "Eval scroll: focus=" + focus + " rectangle=" + rectangle2 + " ci=" + ci + " vi=" + vi);
            }
            if (focus != lastScrolledFocus || this.mScrollMayChange || rectangle2 != null) {
                this.mLastScrolledFocus = new WeakReference<>(focus);
                this.mScrollMayChange = false;
                if (z) {
                    Log.m100v(this.mTag, "Need to scroll?");
                }
                if (focus.getGlobalVisibleRect(this.mVisRect, null)) {
                    if (z) {
                        Log.m100v(this.mTag, "Root w=" + this.mView.getWidth() + " h=" + this.mView.getHeight() + " ci=" + ci.toShortString() + " vi=" + vi.toShortString());
                    }
                    if (rectangle2 == null) {
                        focus.getFocusedRect(this.mTempRect);
                        if (z) {
                            Log.m100v(this.mTag, "Focus " + focus + ": focusRect=" + this.mTempRect.toShortString());
                        }
                        View view = this.mView;
                        if (view instanceof ViewGroup) {
                            try {
                                ((ViewGroup) view).offsetDescendantRectToMyCoords(focus, this.mTempRect);
                            } catch (IllegalArgumentException ex) {
                                Log.m96e(this.mTag, "offsetDescendantRectToMyCoords() error occurred. focus=" + focus + " mTempRect=" + this.mTempRect.toShortString() + " " + ex);
                                ex.printStackTrace();
                            }
                        }
                        if (DEBUG_INPUT_RESIZE) {
                            Log.m100v(this.mTag, "Focus in window: focusRect=" + this.mTempRect.toShortString() + " visRect=" + this.mVisRect.toShortString());
                        }
                    } else {
                        this.mTempRect.set(rectangle2);
                        if (z) {
                            Log.m100v(this.mTag, "Request scroll to rect: " + this.mTempRect.toShortString() + " visRect=" + this.mVisRect.toShortString());
                        }
                    }
                    if (this.mTempRect.intersect(this.mVisRect)) {
                        boolean z2 = DEBUG_INPUT_RESIZE;
                        if (z2) {
                            Log.m100v(this.mTag, "Focus window visible rect: " + this.mTempRect.toShortString());
                        }
                        if (this.mTempRect.height() > (this.mView.getHeight() - vi.top) - vi.bottom) {
                            if (z2) {
                                Log.m100v(this.mTag, "Too tall; leaving scrollY=" + scrollY3);
                            }
                            scrollY2 = scrollY3;
                        } else {
                            if (this.mTempRect.top >= vi.top) {
                                if (this.mTempRect.bottom > this.mView.getHeight() - vi.bottom) {
                                    scrollY = this.mTempRect.bottom - (this.mView.getHeight() - vi.bottom);
                                    if (z2) {
                                        Log.m100v(this.mTag, "Bottom covered; scrollY=" + scrollY);
                                    }
                                } else {
                                    scrollY2 = 0;
                                }
                            } else {
                                scrollY = this.mTempRect.top - vi.top;
                                if (z2) {
                                    Log.m100v(this.mTag, "Top covered; scrollY=" + scrollY);
                                }
                            }
                            scrollY2 = scrollY;
                        }
                        handled = true;
                    }
                }
            } else if (z) {
                Log.m100v(this.mTag, "Keeping scroll y=" + this.mScrollY + " vi=" + vi.toShortString());
            }
            scrollY2 = scrollY3;
        }
        int scrollY4 = this.mScrollY;
        if (scrollY2 != scrollY4) {
            if (DEBUG_INPUT_RESIZE) {
                Log.m100v(this.mTag, "Pan scroll changed: old=" + this.mScrollY + " , new=" + scrollY2);
            }
            if (!immediate) {
                if (this.mScroller == null) {
                    this.mScroller = new Scroller(this.mView.getContext());
                }
                Scroller scroller = this.mScroller;
                int i = this.mScrollY;
                scroller.startScroll(0, i, 0, scrollY2 - i);
            } else {
                Scroller scroller2 = this.mScroller;
                if (scroller2 != null) {
                    scroller2.abortAnimation();
                }
            }
            this.mScrollY = scrollY2;
        }
        return handled;
    }

    public View getAccessibilityFocusedHost() {
        return this.mAccessibilityFocusedHost;
    }

    public AccessibilityNodeInfo getAccessibilityFocusedVirtualView() {
        return this.mAccessibilityFocusedVirtualView;
    }

    void setAccessibilityFocus(View view, AccessibilityNodeInfo node) {
        if (this.mAccessibilityFocusedVirtualView != null) {
            AccessibilityNodeInfo focusNode = this.mAccessibilityFocusedVirtualView;
            View focusHost = this.mAccessibilityFocusedHost;
            this.mAccessibilityFocusedHost = null;
            this.mAccessibilityFocusedVirtualView = null;
            focusHost.clearAccessibilityFocusNoCallbacks(64);
            AccessibilityNodeProvider provider = focusHost.getAccessibilityNodeProvider();
            if (provider != null) {
                focusNode.getBoundsInParent(this.mTempRect);
                focusHost.invalidate(this.mTempRect);
                int virtualNodeId = AccessibilityNodeInfo.getVirtualDescendantId(focusNode.getSourceNodeId());
                provider.performAction(virtualNodeId, 128, null);
            }
            focusNode.recycle();
        }
        View view2 = this.mAccessibilityFocusedHost;
        if (view2 != null && view2 != view) {
            view2.clearAccessibilityFocusNoCallbacks(64);
        }
        this.mAccessibilityFocusedHost = view;
        this.mAccessibilityFocusedVirtualView = node;
        updateKeepClearForAccessibilityFocusRect();
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.invalidateRoot();
        }
    }

    boolean hasPointerCapture() {
        return this.mPointerCapture;
    }

    void requestPointerCapture(boolean enabled) {
        IBinder inputToken = getInputToken();
        if (inputToken == null) {
            Log.m96e(this.mTag, "No input channel to request Pointer Capture.");
        } else {
            InputManagerGlobal.getInstance().requestPointerCapture(inputToken, enabled);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePointerCaptureChanged(boolean hasCapture) {
        if (this.mPointerCapture == hasCapture) {
            return;
        }
        this.mPointerCapture = hasCapture;
        View view = this.mView;
        if (view != null) {
            view.dispatchPointerCaptureChanged(hasCapture);
        }
    }

    private void updateRenderHdrSdrRatio() {
        this.mRenderHdrSdrRatio = Math.min(this.mDesiredHdrSdrRatio, this.mDisplay.getHdrSdrRatio());
        this.mUpdateHdrSdrRatioInfo = true;
    }

    private void updateColorModeIfNeeded(int colorMode) {
        if (this.mAttachInfo.mThreadedRenderer == null) {
            return;
        }
        if ((colorMode == 2 || colorMode == 3) && !this.mDisplay.isHdrSdrRatioAvailable()) {
            colorMode = 1;
        }
        if (colorMode != 4 && !getConfiguration().isScreenWideColorGamut()) {
            colorMode = 0;
        }
        if (CoreRune.FW_SCREENSHOT_FOR_HDR && this.mForceModeInScreenshot && this.mInvalidateForScreenshotRunnable != null && colorMode != 2 && colorMode != 3) {
            Log.m98i(this.mTag, "removeCallbacks mInvalidateForScreenshotRunnable");
            this.mHandler.removeCallbacks(this.mInvalidateForScreenshotRunnable);
        }
        float desiredRatio = this.mAttachInfo.mThreadedRenderer.setColorMode(colorMode);
        if (desiredRatio != this.mDesiredHdrSdrRatio) {
            this.mDesiredHdrSdrRatio = desiredRatio;
            updateRenderHdrSdrRatio();
            if (this.mDesiredHdrSdrRatio < 1.01f) {
                this.mDisplay.unregisterHdrSdrRatioChangedListener(this.mHdrSdrRatioChangedListener);
                this.mHdrSdrRatioChangedListener = null;
            } else {
                Consumer<Display> consumer = new Consumer() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda21
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ViewRootImpl.this.lambda$updateColorModeIfNeeded$7((Display) obj);
                    }
                };
                this.mHdrSdrRatioChangedListener = consumer;
                this.mDisplay.registerHdrSdrRatioChangedListener(this.mExecutor, consumer);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateColorModeIfNeeded$7(Display display) {
        updateRenderHdrSdrRatio();
        invalidate();
    }

    @Override // android.view.ViewParent
    public void requestChildFocus(View child, View focused) {
        if (DEBUG_INPUT_RESIZE) {
            Log.m100v(this.mTag, "Request child focus: focus now " + focused);
        }
        checkThread();
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [14] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    @Override // android.view.ViewParent
    public void clearChildFocus(View child) {
        if (DEBUG_INPUT_RESIZE) {
            Log.m100v(this.mTag, "Clearing child focus");
        }
        checkThread();
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [15] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    @Override // android.view.ViewParent
    public ViewParent getParentForAccessibility() {
        return null;
    }

    @Override // android.view.ViewParent
    public void focusableViewAvailable(View v) {
        checkThread();
        View view = this.mView;
        if (view != null) {
            if (!view.hasFocus()) {
                if (sAlwaysAssignFocus || !this.mAttachInfo.mInTouchMode) {
                    v.requestFocus();
                    return;
                }
                return;
            }
            View focused = this.mView.findFocus();
            if (focused instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) focused;
                if (group.getDescendantFocusability() == 262144 && isViewDescendantOf(v, focused)) {
                    v.requestFocus();
                }
            }
        }
    }

    @Override // android.view.ViewParent
    public void recomputeViewAttributes(View child) {
        checkThread();
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [16] mView=" + this.mView + " child=" + child + " mWillDrawSoon=" + this.mWillDrawSoon);
        }
        if (this.mView == child) {
            if (this.mIsInTraversal) {
                this.mHandler.post(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewRootImpl.this.lambda$recomputeViewAttributes$8();
                    }
                });
            } else {
                this.mAttachInfo.mRecomputeGlobalAttributes = true;
            }
            if (!this.mWillDrawSoon) {
                scheduleTraversals();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$recomputeViewAttributes$8() {
        this.mAttachInfo.mRecomputeGlobalAttributes = true;
    }

    void dispatchDetachedFromWindow() {
        SemPressGestureDetector semPressGestureDetector;
        InputQueue inputQueue;
        this.mInsetsController.onWindowFocusLost();
        this.mCompatTranslator = null;
        clearSavedStickyDragEvent();
        InputStage inputStage = this.mFirstInputStage;
        if (inputStage != null) {
            inputStage.onDetachedFromWindow();
        }
        View view = this.mView;
        if (view != null && view.mAttachInfo != null) {
            this.mAttachInfo.mTreeObserver.dispatchOnWindowAttachedChange(false);
            this.mView.dispatchDetachedFromWindow();
        }
        this.mAccessibilityInteractionConnectionManager.ensureNoConnection();
        this.mAccessibilityInteractionConnectionManager.ensureNoDirectConnection();
        removeSendWindowContentChangedCallback();
        destroyHardwareRenderer();
        setAccessibilityFocus(null, null);
        this.mInsetsController.cancelExistingAnimations();
        View view2 = this.mView;
        if (view2 != null) {
            view2.assignParent(null);
            this.mView = null;
        }
        clearCanvasBlurInstances();
        this.mBlurRegionAggregator.setViewRoot(null);
        Log.m98i(this.mTag, "dispatchDetachedFromWindow");
        if (this.mThread != Thread.currentThread()) {
            Log.m102w(this.mTag, "There is possible to occur CalledFromWrongThreadException. " + Debug.getCallers(10));
        }
        this.mAttachInfo.mRootView = null;
        destroySurface();
        InputQueue.Callback callback = this.mInputQueueCallback;
        if (callback != null && (inputQueue = this.mInputQueue) != null) {
            callback.onInputQueueDestroyed(inputQueue);
            this.mInputQueue.dispose();
            this.mInputQueueCallback = null;
            this.mInputQueue = null;
        }
        try {
            this.mWindowSession.remove(this.mWindow);
        } catch (RemoteException e) {
        }
        WindowInputEventReceiver windowInputEventReceiver = this.mInputEventReceiver;
        if (windowInputEventReceiver != null) {
            windowInputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
        unregisterListeners();
        unscheduleTraversals();
        if (CoreRune.BIXBY_TOUCH && (semPressGestureDetector = this.mSemPressGestureDetector) != null) {
            semPressGestureDetector.onDetached();
        }
        this.mIsDetached = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performConfigurationChange(MergedConfiguration mergedConfiguration, boolean force, int newDisplayId) {
        View.AttachInfo attachInfo;
        if (mergedConfiguration == null) {
            throw new IllegalArgumentException("No merged config provided.");
        }
        int lastRotation = this.mLastReportedMergedConfiguration.getMergedConfiguration().windowConfiguration.getRotation();
        int newRotation = mergedConfiguration.getMergedConfiguration().windowConfiguration.getRotation();
        if (lastRotation != newRotation) {
            this.mUpdateSurfaceNeeded = true;
            if (!this.mIsInTraversal) {
                this.mForceNextWindowRelayout = true;
            }
        }
        Configuration globalConfig = mergedConfiguration.getGlobalConfiguration();
        Configuration overrideConfig = mergedConfiguration.getOverrideConfiguration();
        if (DEBUG_CONFIGURATION) {
            Log.m100v(this.mTag, "Applying new config to window " + ((Object) this.mWindowAttributes.getTitle()) + ", globalConfig: " + globalConfig + ", overrideConfig: " + overrideConfig);
        }
        CompatibilityInfo ci = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
        if (!ci.equals(CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO)) {
            globalConfig = new Configuration(globalConfig);
            ci.applyToConfiguration(this.mNoncompatDensity, globalConfig);
        }
        Configuration lastOverrideConfig = this.mLastReportedMergedConfiguration.getOverrideConfiguration();
        boolean changeNightDim = lastOverrideConfig.nightDim != globalConfig.nightDim;
        if (changeNightDim) {
            boolean isDeskTopModeEnabled = this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1;
            if (!isDeskTopModeEnabled) {
                int nightDimLevel = globalConfig.nightDim;
                ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                ThreadedRenderer.setNightDimText(nightDimLevel);
                Log.m98i(this.mTag, "performConfigurationChange setNightDimText nightDimLevel=" + nightDimLevel);
                invalidateWorld(this.mView);
            }
        }
        boolean changeNightDim2 = CoreRune.MT_NEW_DEX;
        if (changeNightDim2) {
            this.mNewDexMode = globalConfig.isNewDexMode();
        }
        if (lastOverrideConfig.seq > 0 && overrideConfig.seq > 0 && overrideConfig.isOtherSeqNewer(lastOverrideConfig)) {
            Log.m100v(this.mTag, "Last overrideConfig is newer : " + overrideConfig + " -> " + lastOverrideConfig);
            overrideConfig.setTo(lastOverrideConfig);
        }
        if (CoreRune.MW_CAPTION_SHELL_BUG_FIX && (this.mWindowSession instanceof WindowlessWindowManager)) {
            Rect lastBounds = this.mLastReportedMergedConfiguration.getOverrideConfiguration().windowConfiguration.getBounds();
            Rect newBounds = overrideConfig.windowConfiguration.getBounds();
            if (!lastBounds.equals(newBounds) && (attachInfo = this.mAttachInfo) != null && attachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.setLightCenter(this.mAttachInfo, newBounds);
            }
        }
        ArrayList<ConfigChangedCallback> arrayList = sConfigCallbacks;
        synchronized (arrayList) {
            try {
                for (int i = arrayList.size() - 1; i >= 0; i--) {
                    sConfigCallbacks.get(i).onConfigurationChanged(globalConfig);
                }
            } catch (Throwable th) {
                th = th;
                while (true) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            }
        }
        this.mLastReportedMergedConfiguration.setConfiguration(globalConfig, overrideConfig);
        this.mForceNextConfigUpdate = force;
        ActivityConfigCallback activityConfigCallback = this.mActivityConfigCallback;
        if (activityConfigCallback != null) {
            activityConfigCallback.onConfigurationChanged(overrideConfig, newDisplayId);
        } else {
            updateConfiguration(newDisplayId);
        }
        this.mForceNextConfigUpdate = false;
    }

    public void updateConfiguration(int newDisplayId) {
        View view = this.mView;
        if (view == null) {
            return;
        }
        Resources localResources = view.getResources();
        Configuration config = localResources.getConfiguration();
        if (newDisplayId != -1) {
            onMovedToDisplay(newDisplayId, config);
        }
        if (this.mForceNextConfigUpdate || this.mLastConfigurationFromResources.diff(config) != 0) {
            updateInternalDisplay(this.mDisplay.getDisplayId(), localResources);
            updateLastConfigurationFromResources(config);
            if (CoreRune.MW_CAPTION_SHELL_OPACITY) {
                updateWindowOpacity(config.windowConfiguration.getFreeformTranslucent() != 1);
            }
            this.mView.dispatchConfigurationChanged(config);
            this.mForceNextWindowRelayout = true;
            requestLayout();
        }
        updateForceDarkMode();
    }

    private void updateLastConfigurationFromResources(Configuration resConfig) {
        View view;
        int lastLayoutDirection = this.mLastConfigurationFromResources.getLayoutDirection();
        int currentLayoutDirection = resConfig.getLayoutDirection();
        this.mLastConfigurationFromResources.setTo(resConfig);
        if (lastLayoutDirection != currentLayoutDirection && (view = this.mView) != null && this.mViewLayoutDirectionInitial == 2) {
            view.setLayoutDirection(currentLayoutDirection);
        }
    }

    public static boolean isViewDescendantOf(View child, View parent) {
        if (child == parent) {
            return true;
        }
        Object parent2 = child.getParent();
        return (parent2 instanceof ViewGroup) && isViewDescendantOf((View) parent2, parent);
    }

    private static void forceLayout(View view) {
        view.forceLayout();
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                forceLayout(group.getChildAt(i));
            }
        }
    }

    final class ViewRootHandler extends Handler {
        ViewRootHandler() {
        }

        @Override // android.p009os.Handler
        public String getMessageName(Message message) {
            switch (message.what) {
                case 1:
                    return "MSG_INVALIDATE";
                case 2:
                    return "MSG_INVALIDATE_RECT";
                case 3:
                    return "MSG_DIE";
                case 4:
                    return "MSG_RESIZED";
                case 5:
                    return "MSG_RESIZED_REPORT";
                case 6:
                    return "MSG_WINDOW_FOCUS_CHANGED";
                case 7:
                    return "MSG_DISPATCH_INPUT_EVENT";
                case 8:
                    return "MSG_DISPATCH_APP_VISIBILITY";
                case 9:
                    return "MSG_DISPATCH_GET_NEW_SURFACE";
                case 11:
                    return "MSG_DISPATCH_KEY_FROM_IME";
                case 12:
                    return "MSG_DISPATCH_KEY_FROM_AUTOFILL";
                case 13:
                    return "MSG_CHECK_FOCUS";
                case 14:
                    return "MSG_CLOSE_SYSTEM_DIALOGS";
                case 15:
                    return "MSG_DISPATCH_DRAG_EVENT";
                case 16:
                    return "MSG_DISPATCH_DRAG_LOCATION_EVENT";
                case 17:
                    return "MSG_DISPATCH_SYSTEM_UI_VISIBILITY";
                case 18:
                    return "MSG_UPDATE_CONFIGURATION";
                case 19:
                    return "MSG_PROCESS_INPUT_EVENTS";
                case 21:
                    return "MSG_CLEAR_ACCESSIBILITY_FOCUS_HOST";
                case 23:
                    return "MSG_WINDOW_MOVED";
                case 24:
                    return "MSG_SYNTHESIZE_INPUT_EVENT";
                case 25:
                    return "MSG_DISPATCH_WINDOW_SHOWN";
                case 27:
                    return "MSG_UPDATE_POINTER_ICON";
                case 28:
                    return "MSG_POINTER_CAPTURE_CHANGED";
                case 29:
                    return "MSG_INSETS_CONTROL_CHANGED";
                case 30:
                    return "MSG_SYSTEM_GESTURE_EXCLUSION_CHANGED";
                case 31:
                    return "MSG_SHOW_INSETS";
                case 32:
                    return "MSG_HIDE_INSETS";
                case 34:
                    return "MSG_WINDOW_TOUCH_MODE_CHANGED";
                case 35:
                    return "MSG_KEEP_CLEAR_RECTS_CHANGED";
                case 103:
                    return "MSG_SPEN_GESTURE_EVENT";
                case 104:
                    return "MSG_DISPATCH_LETTERBOX_DIRECTION_CHANGED";
                case 105:
                    return "MSG_WINDOW_FOCUS_IN_TASK_CHANGED";
                default:
                    return super.getMessageName(message);
            }
        }

        @Override // android.p009os.Handler
        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            if (msg.what == 26 && msg.obj == null) {
                throw new NullPointerException("Attempted to call MSG_REQUEST_KEYBOARD_SHORTCUTS with null receiver:");
            }
            return super.sendMessageAtTime(msg, uptimeMillis);
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            if (Trace.isTagEnabled(8L)) {
                Trace.traceBegin(8L, getMessageName(msg));
            }
            try {
                handleMessageImpl(msg);
            } finally {
                Trace.traceEnd(8L);
            }
        }

        private void handleMessageImpl(Message message) {
            switch (message.what) {
                case 1:
                    ((View) message.obj).invalidate();
                    break;
                case 2:
                    View.AttachInfo.InvalidateInfo invalidateInfo = (View.AttachInfo.InvalidateInfo) message.obj;
                    invalidateInfo.target.invalidate(invalidateInfo.left, invalidateInfo.top, invalidateInfo.right, invalidateInfo.bottom);
                    invalidateInfo.recycle();
                    break;
                case 3:
                    ViewRootImpl.this.doDie();
                    break;
                case 4:
                case 5:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    ViewRootImpl.this.mInsetsController.onStateChanged((InsetsState) someArgs.arg3);
                    ViewRootImpl.this.handleResized(message.what, someArgs);
                    someArgs.recycle();
                    break;
                case 6:
                    ViewRootImpl.this.handleWindowFocusChanged();
                    break;
                case 7:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    ViewRootImpl.this.enqueueInputEvent((InputEvent) someArgs2.arg1, (InputEventReceiver) someArgs2.arg2, 0, true);
                    someArgs2.recycle();
                    break;
                case 8:
                    ViewRootImpl.this.handleAppVisibility(message.arg1 != 0);
                    break;
                case 9:
                    ViewRootImpl.this.handleGetNewSurface();
                    break;
                case 11:
                    KeyEvent keyEvent = (KeyEvent) message.obj;
                    if ((keyEvent.getFlags() & 8) != 0) {
                        keyEvent = KeyEvent.changeFlags(keyEvent, keyEvent.getFlags() & (-9));
                    }
                    ViewRootImpl.this.enqueueInputEvent(keyEvent, null, 1, true);
                    break;
                case 12:
                    ViewRootImpl.this.enqueueInputEvent((KeyEvent) message.obj, null, 0, true);
                    break;
                case 13:
                    ViewRootImpl.this.getImeFocusController().onScheduledCheckFocus();
                    break;
                case 14:
                    if (ViewRootImpl.this.mView != null) {
                        ViewRootImpl.this.mView.onCloseSystemDialogs((String) message.obj);
                        break;
                    }
                    break;
                case 15:
                case 16:
                    DragEvent dragEvent = (DragEvent) message.obj;
                    dragEvent.mLocalState = ViewRootImpl.this.mLocalDragState;
                    ViewRootImpl.this.handleDragEvent(dragEvent);
                    break;
                case 17:
                    ViewRootImpl.this.handleDispatchSystemUiVisibilityChanged();
                    break;
                case 18:
                    Configuration configuration = (Configuration) message.obj;
                    if (configuration.isOtherSeqNewer(ViewRootImpl.this.mLastReportedMergedConfiguration.getMergedConfiguration())) {
                        configuration = ViewRootImpl.this.mLastReportedMergedConfiguration.getGlobalConfiguration();
                    }
                    ViewRootImpl.this.mPendingMergedConfiguration.setConfiguration(configuration, ViewRootImpl.this.mLastReportedMergedConfiguration.getOverrideConfiguration());
                    ViewRootImpl.this.performConfigurationChange(new MergedConfiguration(ViewRootImpl.this.mPendingMergedConfiguration), false, -1);
                    break;
                case 19:
                    ViewRootImpl.this.mProcessInputEventsScheduled = false;
                    ViewRootImpl.this.doProcessInputEvents();
                    break;
                case 21:
                    ViewRootImpl.this.setAccessibilityFocus(null, null);
                    break;
                case 22:
                    if (ViewRootImpl.this.mView != null) {
                        ViewRootImpl viewRootImpl = ViewRootImpl.this;
                        viewRootImpl.invalidateWorld(viewRootImpl.mView);
                        break;
                    }
                    break;
                case 23:
                    if (ViewRootImpl.this.mAdded) {
                        int width = ViewRootImpl.this.mWinFrame.width();
                        int height = ViewRootImpl.this.mWinFrame.height();
                        int i = message.arg1;
                        int i2 = message.arg2;
                        ViewRootImpl.this.mTmpFrames.frame.left = i;
                        ViewRootImpl.this.mTmpFrames.frame.right = i + width;
                        ViewRootImpl.this.mTmpFrames.frame.top = i2;
                        ViewRootImpl.this.mTmpFrames.frame.bottom = i2 + height;
                        ViewRootImpl viewRootImpl2 = ViewRootImpl.this;
                        viewRootImpl2.setFrame(viewRootImpl2.mTmpFrames.frame, false);
                        ViewRootImpl viewRootImpl3 = ViewRootImpl.this;
                        viewRootImpl3.maybeHandleWindowMove(viewRootImpl3.mWinFrame);
                        break;
                    }
                    break;
                case 24:
                    ViewRootImpl.this.enqueueInputEvent((InputEvent) message.obj, null, 32, true);
                    break;
                case 25:
                    ViewRootImpl.this.handleDispatchWindowShown();
                    break;
                case 26:
                    ViewRootImpl.this.handleRequestKeyboardShortcuts((IResultReceiver) message.obj, message.arg1);
                    break;
                case 27:
                    ViewRootImpl.this.resetPointerIcon((MotionEvent) message.obj);
                    break;
                case 28:
                    ViewRootImpl.this.handlePointerCaptureChanged(message.arg1 != 0);
                    break;
                case 29:
                    SomeArgs someArgs3 = (SomeArgs) message.obj;
                    ViewRootImpl.this.mInsetsController.onStateChanged((InsetsState) someArgs3.arg1);
                    InsetsSourceControl[] insetsSourceControlArr = (InsetsSourceControl[]) someArgs3.arg2;
                    if (ViewRootImpl.this.mAdded) {
                        ViewRootImpl.this.mInsetsController.onControlsChanged(insetsSourceControlArr);
                    } else if (insetsSourceControlArr != null) {
                        for (InsetsSourceControl insetsSourceControl : insetsSourceControlArr) {
                            if (insetsSourceControl != null) {
                                insetsSourceControl.release(new InsetsAnimationThreadControlRunner$$ExternalSyntheticLambda0());
                            }
                        }
                    }
                    someArgs3.recycle();
                    break;
                case 30:
                    ViewRootImpl.this.systemGestureExclusionChanged();
                    break;
                case 31:
                    ImeTracker.Token token = (ImeTracker.Token) message.obj;
                    ImeTracker.forLogging().onProgress(token, 30);
                    if (ViewRootImpl.this.mView == null) {
                        Object[] objArr = new Object[2];
                        objArr[0] = Integer.valueOf(message.arg1);
                        objArr[1] = Boolean.valueOf(message.arg2 == 1);
                        Log.m96e(ViewRootImpl.TAG, String.format("Calling showInsets(%d,%b) on window that no longer has views.", objArr));
                    }
                    ViewRootImpl.this.clearLowProfileModeIfNeeded(message.arg1, message.arg2 == 1);
                    ViewRootImpl.this.mInsetsController.show(message.arg1, message.arg2 == 1, token);
                    break;
                case 32:
                    ImeTracker.Token token2 = (ImeTracker.Token) message.obj;
                    ImeTracker.forLogging().onProgress(token2, 31);
                    ViewRootImpl.this.mInsetsController.hide(message.arg1, message.arg2 == 1, token2);
                    break;
                case 33:
                    ViewRootImpl.this.handleScrollCaptureRequest((IScrollCaptureResponseListener) message.obj);
                    break;
                case 34:
                    if (ViewRootImpl.this.mAdded) {
                        Log.m98i(ViewRootImpl.this.mTag, new StringBuilder("MSG_WINDOW_TOUCH_MODE_CHANGED ").toString());
                    }
                    ViewRootImpl.this.handleWindowTouchModeChanged();
                    break;
                case 35:
                    ViewRootImpl.this.keepClearRectsChanged(message.arg1 == 1);
                    break;
                case 36:
                    ViewRootImpl.this.reportKeepClearAreasChanged();
                    break;
                case 37:
                    Log.m96e(ViewRootImpl.this.mTag, "Timedout waiting to unpause for sync");
                    ViewRootImpl.this.mNumPausedForSync = 0;
                    ViewRootImpl.this.scheduleTraversals();
                    break;
                case 103:
                    ViewRootImpl.this.handleDispatchSPenGestureEvent((InputEvent[]) message.obj);
                    break;
                case 104:
                    ViewRootImpl.this.handleDispatchLetterboxDirectionChanged(message.arg1);
                    break;
                case 105:
                    ViewRootImpl.this.handleWindowFocusInTaskChanged();
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$9(Runnable r) {
        this.mHandler.post(r);
    }

    boolean ensureTouchMode(boolean inTouchMode) {
        if (this.mAttachInfo.mInTouchMode == inTouchMode) {
            return false;
        }
        if (!inTouchMode) {
            try {
                Log.m98i(this.mTag, "setInTouchMode(false), " + Debug.getCallers(10));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        IWindowManager windowManager = WindowManagerGlobal.getWindowManagerService();
        windowManager.setInTouchMode(inTouchMode, getDisplayId());
        return ensureTouchModeLocally(inTouchMode);
    }

    private boolean ensureTouchModeLocally(boolean inTouchMode) {
        if (this.mAttachInfo.mInTouchMode == inTouchMode) {
            return false;
        }
        if (!inTouchMode && !this.mAttachInfo.mHasWindowFocus && (this.mDesktopMode || ((CoreRune.MT_NEW_DEX && this.mNewDexMode) || isSplitScreen()))) {
            if (CoreRune.SAFE_DEBUG) {
                Log.m98i(this.mTag, "No window focus. Don't leave touch mode.");
            }
            return false;
        }
        this.mAttachInfo.mInTouchMode = inTouchMode;
        this.mAttachInfo.mTreeObserver.dispatchOnTouchModeChanged(inTouchMode);
        try {
            return inTouchMode ? enterTouchMode() : leaveTouchMode();
        } catch (IllegalArgumentException ex) {
            Log.m96e(TAG, "ensureTouchModeLocally() error occurred. inTouchMode=" + inTouchMode + " " + ex);
            ex.printStackTrace();
            return false;
        }
    }

    private boolean enterTouchMode() {
        View focused;
        View view = this.mView;
        if (view == null || !view.hasFocus() || (focused = this.mView.findFocus()) == null || focused.isFocusableInTouchMode()) {
            return false;
        }
        ViewGroup ancestorToTakeFocus = findAncestorToTakeFocusInTouchMode(focused);
        if (ancestorToTakeFocus != null) {
            return ancestorToTakeFocus.requestFocus();
        }
        focused.clearFocusInternal(null, true, false);
        return true;
    }

    private static ViewGroup findAncestorToTakeFocusInTouchMode(View focused) {
        ViewParent parent = focused.getParent();
        while (parent instanceof ViewGroup) {
            ViewGroup vgParent = (ViewGroup) parent;
            if (vgParent.getDescendantFocusability() == 262144 && vgParent.isFocusableInTouchMode()) {
                return vgParent;
            }
            if (vgParent.isRootNamespace()) {
                return null;
            }
            parent = vgParent.getParent();
        }
        return null;
    }

    private boolean leaveTouchMode() {
        View view = this.mView;
        if (view == null) {
            return false;
        }
        if (view.hasFocus()) {
            View focusedView = this.mView.findFocus();
            if (!(focusedView instanceof ViewGroup) || ((ViewGroup) focusedView).getDescendantFocusability() != 262144) {
                return false;
            }
        }
        return this.mView.restoreDefaultFocus();
    }

    private boolean checkPalmRejection(MotionEvent event) {
        int SsumMajor = 0;
        boolean bPalm = false;
        int N = event.getPointerCount();
        for (int i = 0; i < N; i++) {
            if (event.getPalm(i) == 1.0f || event.getPalm(i) == 2.0f || event.getPalm(i) == 3.0f) {
                bPalm = true;
            }
            SsumMajor += (int) event.getTouchMajor(i);
        }
        if (event.getPalm() == -2.0f) {
            return false;
        }
        return SsumMajor >= 100 || bPalm;
    }

    private boolean getPalmRejection(MotionEvent event) {
        int[] Sxd = new int[20];
        int[] Syd = new int[20];
        int[] Major = new int[20];
        int[] Minor = new int[20];
        float SsumMajor = 0.0f;
        float SsumMinor = 0.0f;
        boolean bPalm = false;
        int mScreenWidth = 0;
        int mScreenHeight = 0;
        float SvarX = 0.0f;
        int N = event.getPointerCount();
        float SsumX = 0.0f;
        float SsumY = 0.0f;
        Context context = this.mContext;
        if (context != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display disp = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            disp.getMetrics(metrics);
            mScreenWidth = metrics.widthPixels;
            mScreenHeight = metrics.heightPixels;
        }
        float TILT_TO_ZOOM_XVAR = mScreenHeight > mScreenWidth ? mScreenWidth : mScreenHeight;
        for (int i = 0; i < N; i++) {
            Sxd[i] = (int) event.getX(i);
            Syd[i] = (int) event.getY(i);
            Major[i] = (int) event.getTouchMajor(i);
            Minor[i] = (int) event.getTouchMinor(i);
        }
        for (int i2 = 0; i2 < N; i2++) {
            SsumX += Sxd[i2];
            SsumY += Syd[i2];
            SsumMajor += Major[i2];
            SsumMinor += Minor[i2];
        }
        float SmeanX = SsumX / N;
        float SsumEccen = SsumMajor / SsumMinor;
        int i3 = 0;
        while (i3 < N) {
            int[] Minor2 = Minor;
            int[] Sxd2 = Sxd;
            SvarX += (float) Math.sqrt((Sxd[i3] - SmeanX) * (Sxd[i3] - SmeanX));
            if (event.getPalm(i3) == 1.0f || event.getPalm(i3) == 2.0f || event.getPalm(i3) == 3.0f) {
                bPalm = true;
            }
            i3++;
            Minor = Minor2;
            Sxd = Sxd2;
        }
        float SvarX2 = SvarX / N;
        if (bPalm && event.getToolType(0) == 1 && event.getAction() != 1) {
            Log.m98i(TAG, "[ViewRootImpl] action cancel - 1, eccen:" + SsumEccen);
            return true;
        }
        if (event.getToolType(0) != 1 || SsumMajor < 100.0f || SsumEccen <= 2.0f || SvarX2 >= TILT_TO_ZOOM_XVAR / (N + 4)) {
            return false;
        }
        Log.m98i(TAG, "[ViewRootImpl] action cancel - 2, Palm Sweep, SsumMajor:" + SsumMajor + " eccen:" + SsumEccen + " varX:" + SvarX2 + " TILT_TO_ZOOM_XVAR" + TILT_TO_ZOOM_XVAR + " N" + N);
        return true;
    }

    abstract class InputStage {
        protected static final int FINISH_HANDLED = 1;
        protected static final int FINISH_NOT_HANDLED = 2;
        protected static final int FORWARD = 0;
        private final InputStage mNext;
        private String mTracePrefix;

        public InputStage(InputStage next) {
            this.mNext = next;
        }

        public final void deliver(QueuedInputEvent q) {
            if ((q.mFlags & 4) != 0) {
                forward(q);
                return;
            }
            if (shouldDropInputEvent(q)) {
                finish(q, false);
                return;
            }
            traceEvent(q, 8L);
            try {
                int result = onProcess(q);
                Trace.traceEnd(8L);
                apply(q, result);
            } catch (Throwable th) {
                Trace.traceEnd(8L);
                throw th;
            }
        }

        protected void finish(QueuedInputEvent q, boolean handled) {
            q.mFlags |= 4;
            if (handled) {
                q.mFlags |= 8;
            }
            forward(q);
        }

        protected void forward(QueuedInputEvent q) {
            onDeliverToNext(q);
        }

        protected void apply(QueuedInputEvent q, int result) {
            if (result == 0) {
                forward(q);
            } else if (result == 1) {
                finish(q, true);
            } else {
                if (result == 2) {
                    finish(q, false);
                    return;
                }
                throw new IllegalArgumentException("Invalid result: " + result);
            }
        }

        protected int onProcess(QueuedInputEvent q) {
            return 0;
        }

        protected void onDeliverToNext(QueuedInputEvent q) {
            if (ViewRootImpl.DEBUG_INPUT_STAGES) {
                Log.m100v(ViewRootImpl.this.mTag, "Done with " + getClass().getSimpleName() + ". " + q);
            }
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.deliver(q);
            } else {
                ViewRootImpl.this.finishInputEvent(q);
            }
        }

        protected void onWindowFocusChanged(boolean hasWindowFocus) {
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.onWindowFocusChanged(hasWindowFocus);
            }
        }

        protected void onDetachedFromWindow() {
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.onDetachedFromWindow();
            }
        }

        protected boolean shouldDropInputEvent(QueuedInputEvent q) {
            String reason;
            if (ViewRootImpl.this.mView == null || !ViewRootImpl.this.mAdded) {
                Slog.m121w(ViewRootImpl.this.mTag, "Dropping event due to root view being removed: " + q.mEvent);
                Slog.m115e(ViewRootImpl.this.mTag, "mStopped=" + ViewRootImpl.this.mStopped + " mHasWindowFocus=" + ViewRootImpl.this.mAttachInfo.mHasWindowFocus + " mPausedForTransition=" + ViewRootImpl.this.mPausedForTransition);
                return true;
            }
            boolean hasWindowFocus = ViewRootImpl.this.mAttachInfo.mHasWindowFocus;
            if ((ViewRootImpl.this.mView instanceof DecorView) && (q.mEvent instanceof KeyEvent) && ((KeyEvent) q.mEvent).getKeyCode() == 4) {
                hasWindowFocus |= ((DecorView) ViewRootImpl.this.mView).hasWindowFocusInTask();
            }
            boolean hasFakeFocusFlag = (ViewRootImpl.this.mWindowAttributes.samsungFlags & 65536) != 0;
            if (!hasWindowFocus && !hasFakeFocusFlag && !q.mEvent.isFromSource(2) && !ViewRootImpl.this.isAutofillUiShowing()) {
                reason = "no window focus";
            } else if (ViewRootImpl.this.mStopped) {
                reason = "window is stopped";
            } else if (ViewRootImpl.this.mIsAmbientMode && !q.mEvent.isFromSource(1)) {
                reason = "non-button event in ambient mode";
            } else {
                if (!ViewRootImpl.this.mPausedForTransition || isBack(q.mEvent)) {
                    return false;
                }
                reason = "paused for transition";
            }
            if (ViewRootImpl.isTerminalInputEvent(q.mEvent)) {
                q.mEvent.cancel();
                Slog.m121w(ViewRootImpl.this.mTag, "Cancelling event (" + reason + "):" + q.mEvent);
                Slog.m115e(ViewRootImpl.this.mTag, "mStopped=" + ViewRootImpl.this.mStopped + " mHasWindowFocus=" + ViewRootImpl.this.mAttachInfo.mHasWindowFocus + " mPausedForTransition=" + ViewRootImpl.this.mPausedForTransition);
                return false;
            }
            Slog.m121w(ViewRootImpl.this.mTag, "Dropping event (" + reason + "):" + q.mEvent);
            Slog.m115e(ViewRootImpl.this.mTag, "mStopped=" + ViewRootImpl.this.mStopped + " mHasWindowFocus=" + ViewRootImpl.this.mAttachInfo.mHasWindowFocus + " mPausedForTransition=" + ViewRootImpl.this.mPausedForTransition);
            return true;
        }

        void dump(String prefix, PrintWriter writer) {
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.dump(prefix, writer);
            }
        }

        boolean isBack(InputEvent event) {
            return (event instanceof KeyEvent) && ((KeyEvent) event).getKeyCode() == 4;
        }

        private void traceEvent(QueuedInputEvent q, long traceTag) {
            if (!Trace.isTagEnabled(traceTag)) {
                return;
            }
            if (this.mTracePrefix == null) {
                this.mTracePrefix = getClass().getSimpleName();
            }
            Trace.traceBegin(traceTag, this.mTracePrefix + " id=0x" + Integer.toHexString(q.mEvent.getId()));
        }
    }

    abstract class AsyncInputStage extends InputStage {
        protected static final int DEFER = 3;
        private QueuedInputEvent mQueueHead;
        private int mQueueLength;
        private QueuedInputEvent mQueueTail;
        private final String mTraceCounter;

        public AsyncInputStage(InputStage next, String traceCounter) {
            super(next);
            this.mTraceCounter = traceCounter;
        }

        protected void defer(QueuedInputEvent q) {
            q.mFlags |= 2;
            enqueue(q);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void forward(QueuedInputEvent q) {
            q.mFlags &= -3;
            QueuedInputEvent curr = this.mQueueHead;
            if (curr == null) {
                super.forward(q);
                return;
            }
            int deviceId = q.mEvent.getDeviceId();
            QueuedInputEvent prev = null;
            boolean blocked = false;
            while (curr != null && curr != q) {
                if (!blocked && deviceId == curr.mEvent.getDeviceId()) {
                    blocked = true;
                }
                prev = curr;
                curr = curr.mNext;
            }
            if (blocked) {
                if (curr == null) {
                    enqueue(q);
                    return;
                }
                return;
            }
            if (curr != null) {
                curr = curr.mNext;
                dequeue(q, prev);
            }
            super.forward(q);
            while (curr != null) {
                if (deviceId == curr.mEvent.getDeviceId()) {
                    if ((curr.mFlags & 2) == 0) {
                        QueuedInputEvent next = curr.mNext;
                        dequeue(curr, prev);
                        super.forward(curr);
                        curr = next;
                    } else {
                        return;
                    }
                } else {
                    prev = curr;
                    curr = curr.mNext;
                }
            }
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void apply(QueuedInputEvent q, int result) {
            if (result == 3) {
                defer(q);
            } else {
                super.apply(q, result);
            }
        }

        private void enqueue(QueuedInputEvent q) {
            QueuedInputEvent queuedInputEvent = this.mQueueTail;
            if (queuedInputEvent == null) {
                this.mQueueHead = q;
                this.mQueueTail = q;
            } else {
                queuedInputEvent.mNext = q;
                this.mQueueTail = q;
            }
            int i = this.mQueueLength + 1;
            this.mQueueLength = i;
            Trace.traceCounter(4L, this.mTraceCounter, i);
        }

        private void dequeue(QueuedInputEvent q, QueuedInputEvent prev) {
            if (prev == null) {
                this.mQueueHead = q.mNext;
            } else {
                prev.mNext = q.mNext;
            }
            if (this.mQueueTail == q) {
                this.mQueueTail = prev;
            }
            q.mNext = null;
            int i = this.mQueueLength - 1;
            this.mQueueLength = i;
            Trace.traceCounter(4L, this.mTraceCounter, i);
        }

        @Override // android.view.ViewRootImpl.InputStage
        void dump(String prefix, PrintWriter writer) {
            writer.print(prefix);
            writer.print(getClass().getName());
            writer.print(": mQueueLength=");
            writer.println(this.mQueueLength);
            super.dump(prefix, writer);
        }
    }

    final class NativePreImeInputStage extends AsyncInputStage implements InputQueue.FinishedInputEventCallback {
        public NativePreImeInputStage(InputStage next, String traceCounter) {
            super(next, traceCounter);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            if (q.mEvent instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) q.mEvent;
                if (isBack(keyEvent) && ViewRootImpl.this.mContext != null && ViewRootImpl.this.mOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled()) {
                    return doOnBackKeyEvent(keyEvent);
                }
                if (ViewRootImpl.this.mInputQueue != null) {
                    ViewRootImpl.this.mInputQueue.sendInputEvent(q.mEvent, q, true, this);
                    return 3;
                }
                return 0;
            }
            return 0;
        }

        private int doOnBackKeyEvent(KeyEvent keyEvent) {
            OnBackInvokedCallback topCallback = ViewRootImpl.this.getOnBackInvokedDispatcher().getTopCallback();
            if (topCallback instanceof OnBackAnimationCallback) {
                OnBackAnimationCallback animationCallback = (OnBackAnimationCallback) topCallback;
                switch (keyEvent.getAction()) {
                    case 0:
                        if (keyEvent.getRepeatCount() == 0) {
                            animationCallback.onBackStarted(new BackEvent(0.0f, 0.0f, 0.0f, 0));
                            return 2;
                        }
                        return 2;
                    case 1:
                        if (keyEvent.isCanceled()) {
                            animationCallback.onBackCancelled();
                            return 2;
                        }
                        topCallback.onBackInvoked();
                        return 1;
                    default:
                        return 2;
                }
            }
            if (topCallback != null && keyEvent.getAction() == 1) {
                if (!keyEvent.isCanceled()) {
                    topCallback.onBackInvoked();
                    return 1;
                }
                Log.m94d(ViewRootImpl.this.mTag, "Skip onBackInvoked(), reason: keyEvent.isCanceled=true");
                return 2;
            }
            return 2;
        }

        @Override // android.view.InputQueue.FinishedInputEventCallback
        public void onFinishedInputEvent(Object token, boolean handled) {
            QueuedInputEvent q = (QueuedInputEvent) token;
            if (handled) {
                finish(q, true);
            } else {
                forward(q);
            }
        }
    }

    final class ViewPreImeInputStage extends InputStage {
        private boolean mIsBackKeyDuringDrag;

        public ViewPreImeInputStage(InputStage next) {
            super(next);
            this.mIsBackKeyDuringDrag = false;
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            if (q.mEvent instanceof KeyEvent) {
                return processKeyEvent(q);
            }
            return 0;
        }

        private int processKeyEvent(QueuedInputEvent q) {
            KeyEvent event = (KeyEvent) q.mEvent;
            if (ViewRootImpl.this.mDesktopMode) {
                if (ViewRootImpl.this.mAttachInfo.mDragToken != null && event.getKeyCode() == 4 && event.getAction() == 0) {
                    Log.m98i(ViewRootImpl.this.mTag, "Back key during drag in dex. Cancel drag and drop.");
                    this.mIsBackKeyDuringDrag = true;
                    ViewRootImpl.this.mView.cancelDragAndDrop();
                }
                if (this.mIsBackKeyDuringDrag && event.getKeyCode() == 4) {
                    if (event.getAction() == 1) {
                        this.mIsBackKeyDuringDrag = false;
                    }
                    return 1;
                }
            }
            return ((ViewRune.WIDGET_PEN_SUPPORTED && ViewRootImpl.this.mView.dispatchKeyEventTextMultiSelection(event)) || ViewRootImpl.this.mView.dispatchKeyEventPreIme(event)) ? 1 : 0;
        }
    }

    final class ImeInputStage extends AsyncInputStage implements InputMethodManager.FinishedInputEventCallback {
        public ImeInputStage(InputStage next, String traceCounter) {
            super(next, traceCounter);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            int result = ViewRootImpl.this.mImeFocusController.onProcessImeInputStage(q, q.mEvent, ViewRootImpl.this.mWindowAttributes, this);
            switch (result) {
                case -1:
                    return 3;
                case 0:
                    return 0;
                case 1:
                    return 1;
                default:
                    throw new IllegalStateException("Unexpected result=" + result);
            }
        }

        @Override // android.view.inputmethod.InputMethodManager.FinishedInputEventCallback
        public void onFinishedInputEvent(Object token, boolean handled) {
            QueuedInputEvent q = (QueuedInputEvent) token;
            if (handled) {
                finish(q, true);
                Log.m98i(ViewRootImpl.this.mTag, "The input has been finished in ImeInputStage.");
            } else {
                forward(q);
            }
        }
    }

    final class EarlyPostImeInputStage extends InputStage {
        public EarlyPostImeInputStage(InputStage next) {
            super(next);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            if (q.mEvent instanceof KeyEvent) {
                return processKeyEvent(q);
            }
            if (q.mEvent instanceof MotionEvent) {
                return processMotionEvent(q);
            }
            return 0;
        }

        private int processKeyEvent(QueuedInputEvent q) {
            KeyEvent event = (KeyEvent) q.mEvent;
            if (ViewRootImpl.this.mAttachInfo.mTooltipHost != null) {
                ViewRootImpl.this.mAttachInfo.mTooltipHost.handleTooltipKey(event);
            }
            if (ViewRootImpl.this.checkForLeavingTouchModeAndConsume(event)) {
                return 1;
            }
            ViewRootImpl.this.mFallbackEventHandler.preDispatchKeyEvent(event);
            if (event.getAction() == 0) {
                ViewRootImpl.this.mLastClickToolType = 0;
            }
            return 0;
        }

        private int processMotionEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            if (event.isFromSource(2)) {
                return processPointerEvent(q);
            }
            int action = event.getActionMasked();
            if ((action == 0 || action == 8) && event.isFromSource(8)) {
                ViewRootImpl.this.ensureTouchMode(false);
            }
            return 0;
        }

        private int processPointerEvent(QueuedInputEvent q) {
            CompatTranslator translator;
            AutofillManager afm;
            MotionEvent event = (MotionEvent) q.mEvent;
            if (ViewRootImpl.this.mTranslator != null) {
                ViewRootImpl.this.mTranslator.translateEventInScreenToAppWindow(event);
            }
            int action = event.getAction();
            if (action == 0 || action == 8) {
                ViewRootImpl.this.ensureTouchMode(true);
            }
            if (action == 0 && (afm = ViewRootImpl.this.getAutofillManager()) != null) {
                afm.requestHideFillUi();
            }
            if (action == 0 && ViewRootImpl.this.mAttachInfo.mTooltipHost != null) {
                ViewRootImpl.this.mAttachInfo.mTooltipHost.hideTooltip();
            }
            if (ViewRootImpl.this.mCurScrollY != 0) {
                event.offsetLocation(0.0f, ViewRootImpl.this.mCurScrollY);
            }
            if (event.isTouchEvent()) {
                ViewRootImpl.this.mLastTouchPoint.f87x = event.getRawX();
                ViewRootImpl.this.mLastTouchPoint.f88y = event.getRawY();
                ViewRootImpl.this.mLastTouchSource = event.getSource();
                if (event.getActionMasked() == 1) {
                    ViewRootImpl.this.mLastClickToolType = event.getToolType(event.getActionIndex());
                }
                if (event.mNeedWindowOffset && (translator = ViewRootImpl.this.getCompatTranslator()) != null) {
                    translator.translateToScreen(ViewRootImpl.this.mLastTouchPoint);
                    return 0;
                }
                return 0;
            }
            return 0;
        }
    }

    final class NativePostImeInputStage extends AsyncInputStage implements InputQueue.FinishedInputEventCallback {
        public NativePostImeInputStage(InputStage next, String traceCounter) {
            super(next, traceCounter);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            if (ViewRootImpl.this.mInputQueue == null) {
                return 0;
            }
            ViewRootImpl.this.mInputQueue.sendInputEvent(q.mEvent, q, false, this);
            return 3;
        }

        @Override // android.view.InputQueue.FinishedInputEventCallback
        public void onFinishedInputEvent(Object token, boolean handled) {
            QueuedInputEvent q = (QueuedInputEvent) token;
            if (handled) {
                finish(q, true);
            } else {
                forward(q);
            }
        }
    }

    final class ViewPostImeInputStage extends InputStage {
        public ViewPostImeInputStage(InputStage next) {
            super(next);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            if (q.mEvent instanceof KeyEvent) {
                return processKeyEvent(q);
            }
            int source = q.mEvent.getSource();
            if ((source & 2) != 0) {
                return processPointerEvent(q);
            }
            if ((source & 4) != 0) {
                return processTrackballEvent(q);
            }
            return processGenericMotionEvent(q);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onDeliverToNext(QueuedInputEvent q) {
            if (ViewRootImpl.this.mUnbufferedInputDispatch && (q.mEvent instanceof MotionEvent) && ((MotionEvent) q.mEvent).isTouchEvent() && ViewRootImpl.isTerminalInputEvent(q.mEvent)) {
                ViewRootImpl.this.mUnbufferedInputDispatch = false;
                ViewRootImpl.this.scheduleConsumeBatchedInput();
            }
            super.onDeliverToNext(q);
        }

        private boolean performFocusNavigation(KeyEvent event) {
            int direction = 0;
            switch (event.getKeyCode()) {
                case 19:
                    if (event.hasNoModifiers()) {
                        direction = 33;
                        break;
                    }
                    break;
                case 20:
                    if (event.hasNoModifiers()) {
                        direction = 130;
                        break;
                    }
                    break;
                case 21:
                    if (event.hasNoModifiers()) {
                        direction = 17;
                        break;
                    }
                    break;
                case 22:
                    if (event.hasNoModifiers()) {
                        direction = 66;
                        break;
                    }
                    break;
                case 61:
                    if (event.hasNoModifiers()) {
                        direction = 2;
                        break;
                    } else if (event.hasModifiers(1)) {
                        direction = 1;
                        break;
                    }
                    break;
            }
            if (direction != 0) {
                View focused = ViewRootImpl.this.mView.findFocus();
                if (focused != null) {
                    View v = focused.focusSearch(direction);
                    if (v != null && v != focused) {
                        focused.getFocusedRect(ViewRootImpl.this.mTempRect);
                        if (ViewRootImpl.this.mView instanceof ViewGroup) {
                            ((ViewGroup) ViewRootImpl.this.mView).offsetDescendantRectToMyCoords(focused, ViewRootImpl.this.mTempRect);
                            ((ViewGroup) ViewRootImpl.this.mView).offsetRectIntoDescendantCoords(v, ViewRootImpl.this.mTempRect);
                        }
                        if (v.requestFocus(direction, ViewRootImpl.this.mTempRect)) {
                            boolean isFastScrolling = event.getRepeatCount() > 0;
                            ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getConstantForFocusDirection(direction, isFastScrolling));
                            return true;
                        }
                    }
                    if (ViewRootImpl.this.mView.dispatchUnhandledMove(focused, direction)) {
                        return true;
                    }
                } else if (ViewRootImpl.this.mView.restoreDefaultFocus()) {
                    return true;
                }
            }
            return false;
        }

        private boolean performKeyboardGroupNavigation(int direction) {
            View cluster;
            View focused = ViewRootImpl.this.mView.findFocus();
            if (focused == null && ViewRootImpl.this.mView.restoreDefaultFocus()) {
                return true;
            }
            if (focused == null) {
                cluster = ViewRootImpl.this.keyboardNavigationClusterSearch(null, direction);
            } else {
                cluster = focused.keyboardNavigationClusterSearch(null, direction);
            }
            int realDirection = direction;
            if (direction == 2 || direction == 1) {
                realDirection = 130;
            }
            if (cluster != null && cluster.isRootNamespace()) {
                if (!cluster.restoreFocusNotInCluster()) {
                    cluster = ViewRootImpl.this.keyboardNavigationClusterSearch(null, direction);
                } else {
                    ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                    return true;
                }
            }
            if (cluster != null && cluster.restoreFocusInCluster(realDirection)) {
                ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                return true;
            }
            return false;
        }

        private int processKeyEvent(QueuedInputEvent q) {
            int keycode;
            KeyEvent event = (KeyEvent) q.mEvent;
            if (ViewRootImpl.this.mUnhandledKeyManager.preViewDispatch(event)) {
                return 1;
            }
            Log.m98i(ViewRootImpl.this.mTag, "ViewPostIme key " + event.getAction());
            if (ViewRootImpl.this.mView.dispatchKeyEvent(event)) {
                if ((CoreRune.FW_ACTIVE_OR_XCOVER_KEY || CoreRune.FW_XCOVER_AND_TOP_KEY) && (((keycode = event.getKeyCode()) == 1015 || keycode == 1079) && SystemProperties.getInt("sys.datawedge.prop", 0) == 1)) {
                    ViewRootImpl.this.mFallbackEventHandler.dispatchKeyEvent(event);
                }
                return 1;
            }
            if (shouldDropInputEvent(q)) {
                return 2;
            }
            if (ViewRootImpl.this.mUnhandledKeyManager.dispatch(ViewRootImpl.this.mView, event)) {
                return 1;
            }
            int groupNavigationDirection = 0;
            if (event.getAction() == 0 && event.getKeyCode() == 61) {
                if (KeyEvent.metaStateHasModifiers(event.getMetaState(), 4096)) {
                    groupNavigationDirection = 2;
                } else if (KeyEvent.metaStateHasModifiers(event.getMetaState(), 4097)) {
                    groupNavigationDirection = 1;
                }
            }
            if (event.getAction() == 0 && !KeyEvent.metaStateHasNoModifiers(event.getMetaState()) && event.getRepeatCount() == 0 && !KeyEvent.isModifierKey(event.getKeyCode()) && groupNavigationDirection == 0) {
                if (ViewRootImpl.this.mView.dispatchKeyShortcutEvent(event)) {
                    return 1;
                }
                if (shouldDropInputEvent(q)) {
                    return 2;
                }
            }
            if (ViewRootImpl.this.mFallbackEventHandler.dispatchKeyEvent(event)) {
                return 1;
            }
            if (shouldDropInputEvent(q)) {
                return 2;
            }
            if (event.getAction() == 0) {
                if (groupNavigationDirection != 0) {
                    if (performKeyboardGroupNavigation(groupNavigationDirection)) {
                        return 1;
                    }
                } else if (performFocusNavigation(event)) {
                    return 1;
                }
            }
            return 0;
        }

        private int processPointerEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            int action = event.getAction();
            if (ViewRootImpl.this.mMotionEventMonitor != null) {
                ViewRootImpl.this.mMotionEventMonitor.dispatchInputEvent(q.mEvent);
            }
            boolean handled = ViewRootImpl.this.mHandwritingInitiator.onTouchEvent(event);
            if (handled) {
                ViewRootImpl.this.mLastClickToolType = event.getToolType(event.getActionIndex());
            }
            if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
                Log.m98i(ViewRootImpl.this.mTag, "ViewPostIme pointer " + action);
            } else if (action == 0 || action == 1 || (ViewRune.COMMON_IS_PRODUCT_DEV && action != 2 && action != 7 && action != 213)) {
                Log.m98i(ViewRootImpl.this.mTag, "ViewPostIme pointer " + action);
            }
            if (CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL && (event.getFlags() & 1048576) != 0) {
                handled = ViewRootImpl.this.isWheelScrollingHandled(event);
            }
            ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested = false;
            ViewRootImpl.this.mAttachInfo.mHandlingPointerEvent = true;
            boolean handled2 = handled || ViewRootImpl.this.mView.dispatchPointerEvent(event);
            maybeUpdatePointerIcon(event);
            ViewRootImpl.this.maybeUpdateTooltip(event);
            ViewRootImpl.this.mAttachInfo.mHandlingPointerEvent = false;
            if (ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested && !ViewRootImpl.this.mUnbufferedInputDispatch) {
                ViewRootImpl.this.mUnbufferedInputDispatch = true;
                if (ViewRootImpl.this.mConsumeBatchedInputScheduled) {
                    ViewRootImpl.this.scheduleConsumeBatchedInputImmediately();
                }
            }
            return handled2 ? 1 : 0;
        }

        private void maybeUpdatePointerIcon(MotionEvent event) {
            boolean z = true;
            if (event.getPointerCount() != 1) {
                return;
            }
            if (event.isStylusPointer()) {
                if (!event.isHoverEvent() && event.getActionMasked() != 0) {
                    z = false;
                }
            } else if (!event.isHoverEvent() || !ViewRootImpl.this.mIsStylusPointerIconEnabled) {
                z = false;
            }
            boolean needsStylusPointerIcon = z;
            if (needsStylusPointerIcon || event.isFromSource(8194)) {
                if (event.getActionMasked() == 9 || event.getActionMasked() == 10) {
                    ViewRootImpl.this.mPointerIconType = null;
                }
                if (event.getActionMasked() != 10 && event.getActionMasked() != 4 && !ViewRootImpl.this.updatePointerIcon(event) && event.getActionMasked() == 7) {
                    ViewRootImpl.this.mPointerIconType = null;
                }
            }
        }

        private int processTrackballEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            return ((!event.isFromSource(InputDevice.SOURCE_MOUSE_RELATIVE) || (ViewRootImpl.this.hasPointerCapture() && !ViewRootImpl.this.mView.dispatchCapturedPointerEvent(event))) && !ViewRootImpl.this.mView.dispatchTrackballEvent(event)) ? 0 : 1;
        }

        private int processGenericMotionEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            return ((event.isFromSource(InputDevice.SOURCE_TOUCHPAD) && ViewRootImpl.this.hasPointerCapture() && ViewRootImpl.this.mView.dispatchCapturedPointerEvent(event)) || ViewRootImpl.this.mView.dispatchGenericMotionEvent(event)) ? 1 : 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetPointerIcon(MotionEvent event) {
        this.mPointerIconType = null;
        updatePointerIcon(event);
    }

    public boolean isDesktopMode() {
        return this.mDesktopMode;
    }

    public boolean isDesktopModeStandAlone() {
        return this.mDesktopModeStandAlone;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updatePointerIcon(MotionEvent event) {
        float x = event.getX(0);
        float y = event.getY(0);
        if (this.mView == null) {
            Slog.m113d(this.mTag, "updatePointerIcon called after view was removed");
            return false;
        }
        if (x < 0.0f || x >= r6.getWidth() || y < 0.0f || y >= this.mView.getHeight()) {
            Slog.m113d(this.mTag, "updatePointerIcon called with position out of bounds");
            return false;
        }
        PointerIcon pointerIcon = null;
        int toolType = event.getToolType(0);
        boolean isFromTouchpad = (event.getFlags() & 67108864) != 0;
        boolean isStylusFromTouchPad = toolType == 2 && isFromTouchpad;
        InputManagerGlobal.getInstance().setIsStylusFromTouchpad(isStylusFromTouchPad);
        if (event.isStylusPointer() && this.mIsStylusPointerIconEnabled && !isStylusFromTouchPad) {
            pointerIcon = this.mHandwritingInitiator.onResolvePointerIcon(this.mContext, event);
        }
        if (pointerIcon == null) {
            pointerIcon = this.mView.onResolvePointerIcon(event, 0);
        }
        int pointerType = pointerIcon != null ? pointerIcon.getType() : 1;
        if (toolType != 4) {
            if (toolType == 2 && pointerType == 1000) {
                pointerType = 20001;
            } else if (pointerType == 10121) {
                pointerType = 1000;
            }
        }
        Integer num = this.mPointerIconType;
        if (num == null || num.intValue() != pointerType) {
            Integer valueOf = Integer.valueOf(pointerType);
            this.mPointerIconType = valueOf;
            this.mCustomPointerIcon = null;
            if (valueOf.intValue() != -1 && this.mPointerIconType.intValue() != 20000) {
                Log.m98i(TAG, "updatePointerIcon pointerType = " + pointerType + ", calling pid = " + Binder.getCallingPid());
                InputManagerGlobal.getInstance().setPointerIconType(pointerType);
                return true;
            }
        }
        if ((this.mPointerIconType.intValue() == -1 || this.mPointerIconType.intValue() == 20000) && !pointerIcon.equals(this.mCustomPointerIcon)) {
            this.mCustomPointerIcon = pointerIcon;
            Log.m98i(TAG, "updatePointerIcon Custom PointerIcon = " + this.mPointerIconType + ", calling pid = " + Binder.getCallingPid());
            InputManagerGlobal.getInstance().setCustomPointerIcon(this.mCustomPointerIcon);
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeUpdateTooltip(MotionEvent event) {
        if (event.getPointerCount() != 1) {
            return;
        }
        int action = event.getActionMasked();
        if (action != 9 && action != 7 && action != 10) {
            return;
        }
        AccessibilityManager manager = AccessibilityManager.getInstance(this.mContext);
        if (manager.isEnabled() && manager.isTouchExplorationEnabled()) {
            return;
        }
        View view = this.mView;
        if (view == null) {
            Slog.m113d(this.mTag, "maybeUpdateTooltip called after view was removed");
        } else {
            view.dispatchTooltipHoverEvent(event);
        }
    }

    private View getFocusedViewOrNull() {
        View view = this.mView;
        if (view != null) {
            return view.findFocus();
        }
        return null;
    }

    final class SyntheticInputStage extends InputStage {
        private final SyntheticJoystickHandler mJoystick;
        private final SyntheticKeyboardHandler mKeyboard;
        private final SyntheticTouchNavigationHandler mTouchNavigation;
        private final SyntheticTrackballHandler mTrackball;

        public SyntheticInputStage() {
            super(null);
            this.mTrackball = ViewRootImpl.this.new SyntheticTrackballHandler();
            this.mJoystick = ViewRootImpl.this.new SyntheticJoystickHandler();
            this.mTouchNavigation = ViewRootImpl.this.new SyntheticTouchNavigationHandler();
            this.mKeyboard = ViewRootImpl.this.new SyntheticKeyboardHandler();
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            q.mFlags |= 16;
            if (q.mEvent instanceof MotionEvent) {
                MotionEvent event = (MotionEvent) q.mEvent;
                int source = event.getSource();
                if ((source & 4) != 0) {
                    this.mTrackball.process(event);
                    return 1;
                }
                if ((source & 16) != 0) {
                    this.mJoystick.process(event);
                    return 1;
                }
                if ((source & 2097152) == 2097152) {
                    this.mTouchNavigation.process(event);
                    return 1;
                }
                return 0;
            }
            if ((q.mFlags & 32) != 0) {
                this.mKeyboard.process((KeyEvent) q.mEvent);
                return 1;
            }
            return 0;
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onDeliverToNext(QueuedInputEvent q) {
            if ((q.mFlags & 16) == 0 && (q.mEvent instanceof MotionEvent)) {
                MotionEvent event = (MotionEvent) q.mEvent;
                int source = event.getSource();
                if ((source & 4) != 0) {
                    this.mTrackball.cancel();
                } else if ((source & 16) != 0) {
                    this.mJoystick.cancel();
                }
            }
            super.onDeliverToNext(q);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onWindowFocusChanged(boolean hasWindowFocus) {
            if (!hasWindowFocus) {
                this.mJoystick.cancel();
            }
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onDetachedFromWindow() {
            this.mJoystick.cancel();
        }
    }

    final class SyntheticTrackballHandler {
        private long mLastTime;

        /* renamed from: mX */
        private final TrackballAxis f568mX = new TrackballAxis();

        /* renamed from: mY */
        private final TrackballAxis f569mY = new TrackballAxis();

        SyntheticTrackballHandler() {
        }

        public void process(MotionEvent event) {
            long curTime;
            int keycode;
            float accel;
            String str;
            int keycode2;
            long curTime2;
            String str2;
            long curTime3 = SystemClock.uptimeMillis();
            if (this.mLastTime + 250 < curTime3) {
                this.f568mX.reset(0);
                this.f569mY.reset(0);
                this.mLastTime = curTime3;
            }
            int action = event.getAction();
            int metaState = event.getMetaState();
            switch (action) {
                case 0:
                    curTime = curTime3;
                    this.f568mX.reset(2);
                    this.f569mY.reset(2);
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(curTime, curTime, 0, 23, 0, metaState, -1, 0, 1024, 257));
                    break;
                case 1:
                    this.f568mX.reset(2);
                    this.f569mY.reset(2);
                    curTime = curTime3;
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(curTime3, curTime3, 1, 23, 0, metaState, -1, 0, 1024, 257));
                    break;
                default:
                    curTime = curTime3;
                    break;
            }
            if (ViewRootImpl.DEBUG_TRACKBALL) {
                Log.m100v(ViewRootImpl.this.mTag, "TB X=" + this.f568mX.position + " step=" + this.f568mX.step + " dir=" + this.f568mX.dir + " acc=" + this.f568mX.acceleration + " move=" + event.getX() + " / Y=" + this.f569mY.position + " step=" + this.f569mY.step + " dir=" + this.f569mY.dir + " acc=" + this.f569mY.acceleration + " move=" + event.getY());
            }
            float xOff = this.f568mX.collect(event.getX(), event.getEventTime(), GnssSignalType.CODE_TYPE_X);
            float yOff = this.f569mY.collect(event.getY(), event.getEventTime(), GnssSignalType.CODE_TYPE_Y);
            int movement = 0;
            if (xOff > yOff) {
                movement = this.f568mX.generate();
                if (movement == 0) {
                    keycode = 0;
                    accel = 1.0f;
                } else {
                    int keycode3 = movement > 0 ? 22 : 21;
                    float accel2 = this.f568mX.acceleration;
                    this.f569mY.reset(2);
                    keycode = keycode3;
                    accel = accel2;
                }
            } else if (yOff <= 0.0f) {
                keycode = 0;
                accel = 1.0f;
            } else {
                movement = this.f569mY.generate();
                if (movement == 0) {
                    keycode = 0;
                    accel = 1.0f;
                } else {
                    int keycode4 = movement > 0 ? 20 : 19;
                    float accel3 = this.f569mY.acceleration;
                    this.f568mX.reset(2);
                    keycode = keycode4;
                    accel = accel3;
                }
            }
            if (keycode != 0) {
                if (movement < 0) {
                    movement = -movement;
                }
                int accelMovement = (int) (movement * accel);
                if (ViewRootImpl.DEBUG_TRACKBALL) {
                    Log.m100v(ViewRootImpl.this.mTag, "Move: movement=" + movement + " accelMovement=" + accelMovement + " accel=" + accel);
                }
                if (accelMovement <= movement) {
                    str = "Delivering fake DPAD: ";
                    keycode2 = keycode;
                    curTime2 = curTime;
                } else {
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.m100v(ViewRootImpl.this.mTag, "Delivering fake DPAD: " + keycode);
                    }
                    int movement2 = movement - 1;
                    int repeatCount = accelMovement - movement2;
                    str = "Delivering fake DPAD: ";
                    keycode2 = keycode;
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(curTime, curTime, 2, keycode, repeatCount, metaState, -1, 0, 1024, 257));
                    curTime2 = curTime;
                    movement = movement2;
                }
                while (movement > 0) {
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        str2 = str;
                        Log.m100v(ViewRootImpl.this.mTag, str2 + keycode2);
                    } else {
                        str2 = str;
                    }
                    long curTime4 = SystemClock.uptimeMillis();
                    int i = keycode2;
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(curTime4, curTime4, 0, i, 0, metaState, -1, 0, 1024, 257));
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(curTime4, curTime4, 1, i, 0, metaState, -1, 0, 1024, 257));
                    movement--;
                    curTime2 = curTime4;
                    str = str2;
                    keycode2 = keycode2;
                }
                this.mLastTime = curTime2;
            }
        }

        public void cancel() {
            this.mLastTime = -2147483648L;
            if (ViewRootImpl.this.mView != null && ViewRootImpl.this.mAdded) {
                ViewRootImpl.this.ensureTouchMode(false);
            }
        }
    }

    static final class TrackballAxis {
        static final float ACCEL_MOVE_SCALING_FACTOR = 0.025f;
        static final long FAST_MOVE_TIME = 150;
        static final float FIRST_MOVEMENT_THRESHOLD = 0.5f;
        static final float MAX_ACCELERATION = 20.0f;
        static final float SECOND_CUMULATIVE_MOVEMENT_THRESHOLD = 2.0f;
        static final float SUBSEQUENT_INCREMENTAL_MOVEMENT_THRESHOLD = 1.0f;
        int dir;
        int nonAccelMovement;
        float position;
        int step;
        float acceleration = 1.0f;
        long lastMoveTime = 0;

        TrackballAxis() {
        }

        void reset(int _step) {
            this.position = 0.0f;
            this.acceleration = 1.0f;
            this.lastMoveTime = 0L;
            this.step = _step;
            this.dir = 0;
        }

        float collect(float off, long time, String axis) {
            long normTime;
            if (off > 0.0f) {
                normTime = (long) (off * 150.0f);
                if (this.dir < 0) {
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.m100v(ViewRootImpl.TAG, axis + " reversed to positive!");
                    }
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0L;
                }
                this.dir = 1;
            } else if (off < 0.0f) {
                normTime = (long) ((-off) * 150.0f);
                if (this.dir > 0) {
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.m100v(ViewRootImpl.TAG, axis + " reversed to negative!");
                    }
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0L;
                }
                this.dir = -1;
            } else {
                normTime = 0;
            }
            if (normTime > 0) {
                long delta = time - this.lastMoveTime;
                this.lastMoveTime = time;
                float acc = this.acceleration;
                if (delta < normTime) {
                    float scale = (normTime - delta) * ACCEL_MOVE_SCALING_FACTOR;
                    if (scale > 1.0f) {
                        acc *= scale;
                    }
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.m100v(ViewRootImpl.TAG, axis + " accelerate: off=" + off + " normTime=" + normTime + " delta=" + delta + " scale=" + scale + " acc=" + acc);
                    }
                    float f = MAX_ACCELERATION;
                    if (acc < MAX_ACCELERATION) {
                        f = acc;
                    }
                    this.acceleration = f;
                } else {
                    float scale2 = (delta - normTime) * ACCEL_MOVE_SCALING_FACTOR;
                    if (scale2 > 1.0f) {
                        acc /= scale2;
                    }
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.m100v(ViewRootImpl.TAG, axis + " deccelerate: off=" + off + " normTime=" + normTime + " delta=" + delta + " scale=" + scale2 + " acc=" + acc);
                    }
                    this.acceleration = acc > 1.0f ? acc : 1.0f;
                }
            }
            float f2 = this.position + off;
            this.position = f2;
            return Math.abs(f2);
        }

        int generate() {
            int movement = 0;
            this.nonAccelMovement = 0;
            while (true) {
                float f = this.position;
                int dir = f >= 0.0f ? 1 : -1;
                switch (this.step) {
                    case 0:
                        if (Math.abs(f) < 0.5f) {
                            return movement;
                        }
                        movement += dir;
                        this.nonAccelMovement += dir;
                        this.step = 1;
                        break;
                    case 1:
                        if (Math.abs(f) < 2.0f) {
                            return movement;
                        }
                        movement += dir;
                        this.nonAccelMovement += dir;
                        this.position -= dir * 2.0f;
                        this.step = 2;
                        break;
                    default:
                        if (Math.abs(f) < 1.0f) {
                            return movement;
                        }
                        movement += dir;
                        this.position -= dir * 1.0f;
                        float acc = this.acceleration * 1.1f;
                        this.acceleration = acc < MAX_ACCELERATION ? acc : this.acceleration;
                        break;
                }
            }
        }
    }

    final class SyntheticJoystickHandler extends Handler {
        private static final int MSG_ENQUEUE_X_AXIS_KEY_REPEAT = 1;
        private static final int MSG_ENQUEUE_Y_AXIS_KEY_REPEAT = 2;
        private final SparseArray<KeyEvent> mDeviceKeyEvents;
        private final JoystickAxesState mJoystickAxesState;

        public SyntheticJoystickHandler() {
            super(true);
            this.mJoystickAxesState = new JoystickAxesState();
            this.mDeviceKeyEvents = new SparseArray<>();
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                case 2:
                    if (ViewRootImpl.this.mAttachInfo.mHasWindowFocus) {
                        KeyEvent oldEvent = (KeyEvent) msg.obj;
                        KeyEvent e = KeyEvent.changeTimeRepeat(oldEvent, SystemClock.uptimeMillis(), oldEvent.getRepeatCount() + 1);
                        ViewRootImpl.this.enqueueInputEvent(e);
                        Message m = obtainMessage(msg.what, e);
                        m.setAsynchronous(true);
                        sendMessageDelayed(m, ViewConfiguration.getKeyRepeatDelay());
                        break;
                    }
                    break;
            }
        }

        public void process(MotionEvent event) {
            switch (event.getActionMasked()) {
                case 2:
                    update(event);
                    break;
                case 3:
                    cancel();
                    break;
                default:
                    Log.m102w(ViewRootImpl.this.mTag, "Unexpected action: " + event.getActionMasked());
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cancel() {
            removeMessages(1);
            removeMessages(2);
            for (int i = 0; i < this.mDeviceKeyEvents.size(); i++) {
                KeyEvent keyEvent = this.mDeviceKeyEvents.valueAt(i);
                if (keyEvent != null) {
                    ViewRootImpl.this.enqueueInputEvent(KeyEvent.changeTimeRepeat(keyEvent, SystemClock.uptimeMillis(), 0));
                }
            }
            this.mDeviceKeyEvents.clear();
            this.mJoystickAxesState.resetState();
        }

        private void update(MotionEvent event) {
            int historySize = event.getHistorySize();
            for (int h = 0; h < historySize; h++) {
                long time = event.getHistoricalEventTime(h);
                this.mJoystickAxesState.updateStateForAxis(event, time, 0, event.getHistoricalAxisValue(0, 0, h));
                this.mJoystickAxesState.updateStateForAxis(event, time, 1, event.getHistoricalAxisValue(1, 0, h));
                this.mJoystickAxesState.updateStateForAxis(event, time, 15, event.getHistoricalAxisValue(15, 0, h));
                this.mJoystickAxesState.updateStateForAxis(event, time, 16, event.getHistoricalAxisValue(16, 0, h));
            }
            long time2 = event.getEventTime();
            this.mJoystickAxesState.updateStateForAxis(event, time2, 0, event.getAxisValue(0));
            this.mJoystickAxesState.updateStateForAxis(event, time2, 1, event.getAxisValue(1));
            this.mJoystickAxesState.updateStateForAxis(event, time2, 15, event.getAxisValue(15));
            this.mJoystickAxesState.updateStateForAxis(event, time2, 16, event.getAxisValue(16));
        }

        final class JoystickAxesState {
            private static final int STATE_DOWN_OR_RIGHT = 1;
            private static final int STATE_NEUTRAL = 0;
            private static final int STATE_UP_OR_LEFT = -1;
            final int[] mAxisStatesHat = {0, 0};
            final int[] mAxisStatesStick = {0, 0};

            JoystickAxesState() {
            }

            void resetState() {
                int[] iArr = this.mAxisStatesHat;
                iArr[0] = 0;
                iArr[1] = 0;
                int[] iArr2 = this.mAxisStatesStick;
                iArr2[0] = 0;
                iArr2[1] = 0;
            }

            void updateStateForAxis(MotionEvent event, long time, int axis, float value) {
                int axisStateIndex;
                int repeatMessage;
                int currentState;
                int keyCode;
                if (isXAxis(axis)) {
                    axisStateIndex = 0;
                    repeatMessage = 1;
                } else if (!isYAxis(axis)) {
                    Log.m96e(ViewRootImpl.this.mTag, "Unexpected axis " + axis + " in updateStateForAxis!");
                    return;
                } else {
                    axisStateIndex = 1;
                    repeatMessage = 2;
                }
                int newState = joystickAxisValueToState(value);
                if (axis == 0 || axis == 1) {
                    currentState = this.mAxisStatesStick[axisStateIndex];
                } else {
                    currentState = this.mAxisStatesHat[axisStateIndex];
                }
                if (currentState == newState) {
                    return;
                }
                int metaState = event.getMetaState();
                int deviceId = event.getDeviceId();
                int source = event.getSource();
                if (currentState == 1 || currentState == -1) {
                    int keyCode2 = joystickAxisAndStateToKeycode(axis, currentState);
                    if (keyCode2 != 0) {
                        ViewRootImpl.this.enqueueInputEvent(new KeyEvent(time, time, 1, keyCode2, 0, metaState, deviceId, 0, 1024, source));
                        deviceId = deviceId;
                        SyntheticJoystickHandler.this.mDeviceKeyEvents.put(deviceId, null);
                    }
                    SyntheticJoystickHandler.this.removeMessages(repeatMessage);
                }
                if ((newState == 1 || newState == -1) && (keyCode = joystickAxisAndStateToKeycode(axis, newState)) != 0) {
                    int deviceId2 = deviceId;
                    KeyEvent keyEvent = new KeyEvent(time, time, 0, keyCode, 0, metaState, deviceId2, 0, 1024, source);
                    ViewRootImpl.this.enqueueInputEvent(keyEvent);
                    Message m = SyntheticJoystickHandler.this.obtainMessage(repeatMessage, keyEvent);
                    m.setAsynchronous(true);
                    SyntheticJoystickHandler.this.sendMessageDelayed(m, ViewConfiguration.getKeyRepeatTimeout());
                    SyntheticJoystickHandler.this.mDeviceKeyEvents.put(deviceId2, new KeyEvent(time, time, 1, keyCode, 0, metaState, deviceId2, 0, 1056, source));
                }
                if (axis == 0 || axis == 1) {
                    this.mAxisStatesStick[axisStateIndex] = newState;
                } else {
                    this.mAxisStatesHat[axisStateIndex] = newState;
                }
            }

            private boolean isXAxis(int axis) {
                return axis == 0 || axis == 15;
            }

            private boolean isYAxis(int axis) {
                return axis == 1 || axis == 16;
            }

            private int joystickAxisAndStateToKeycode(int axis, int state) {
                if (isXAxis(axis) && state == -1) {
                    return 21;
                }
                if (isXAxis(axis) && state == 1) {
                    return 22;
                }
                if (isYAxis(axis) && state == -1) {
                    return 19;
                }
                if (isYAxis(axis) && state == 1) {
                    return 20;
                }
                Log.m96e(ViewRootImpl.this.mTag, "Unknown axis " + axis + " or direction " + state);
                return 0;
            }

            private int joystickAxisValueToState(float value) {
                if (value >= 0.5f) {
                    return 1;
                }
                if (value <= -0.5f) {
                    return -1;
                }
                return 0;
            }
        }
    }

    final class SyntheticTouchNavigationHandler extends Handler {
        private static final String LOCAL_TAG = "SyntheticTouchNavigationHandler";
        private int mCurrentDeviceId;
        private int mCurrentSource;
        private final GestureDetector mGestureDetector;
        private int mPendingKeyMetaState;

        SyntheticTouchNavigationHandler() {
            super(true);
            this.mCurrentDeviceId = -1;
            this.mGestureDetector = new GestureDetector(ViewRootImpl.this.mContext, new GestureDetector.OnGestureListener() { // from class: android.view.ViewRootImpl.SyntheticTouchNavigationHandler.1
                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public void onShowPress(MotionEvent e) {
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onSingleTapUp(MotionEvent e) {
                    SyntheticTouchNavigationHandler.this.dispatchTap(e.getEventTime());
                    return true;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    return true;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public void onLongPress(MotionEvent e) {
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    SyntheticTouchNavigationHandler.this.dispatchFling(velocityX, velocityY, e2.getEventTime());
                    return true;
                }
            });
        }

        public void process(MotionEvent event) {
            if (event.getDevice() == null) {
                return;
            }
            this.mPendingKeyMetaState = event.getMetaState();
            int deviceId = event.getDeviceId();
            int source = event.getSource();
            if (this.mCurrentDeviceId != deviceId || this.mCurrentSource != source) {
                this.mCurrentDeviceId = deviceId;
                this.mCurrentSource = source;
            }
            this.mGestureDetector.onTouchEvent(event);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchTap(long time) {
            dispatchEvent(time, 23);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchFling(float x, float y, long time) {
            if (Math.abs(x) > Math.abs(y)) {
                dispatchEvent(time, x > 0.0f ? 22 : 21);
            } else {
                dispatchEvent(time, y > 0.0f ? 20 : 19);
            }
        }

        private void dispatchEvent(long time, int keyCode) {
            ViewRootImpl.this.enqueueInputEvent(new KeyEvent(time, time, 0, keyCode, 0, this.mPendingKeyMetaState, this.mCurrentDeviceId, 0, 1024, this.mCurrentSource));
            ViewRootImpl.this.enqueueInputEvent(new KeyEvent(time, time, 1, keyCode, 0, this.mPendingKeyMetaState, this.mCurrentDeviceId, 0, 1024, this.mCurrentSource));
        }
    }

    final class SyntheticKeyboardHandler {
        SyntheticKeyboardHandler() {
        }

        public void process(KeyEvent event) {
            if ((event.getFlags() & 1024) != 0) {
                return;
            }
            KeyCharacterMap kcm = event.getKeyCharacterMap();
            int keyCode = event.getKeyCode();
            int metaState = event.getMetaState();
            KeyCharacterMap.FallbackAction fallbackAction = kcm.getFallbackAction(keyCode, metaState);
            if (fallbackAction != null) {
                int flags = event.getFlags() | 1024;
                KeyEvent fallbackEvent = KeyEvent.obtain(event.getDownTime(), event.getEventTime(), event.getAction(), fallbackAction.keyCode, event.getRepeatCount(), fallbackAction.metaState, event.getDeviceId(), event.getScanCode(), flags, event.getSource(), null);
                fallbackAction.recycle();
                ViewRootImpl.this.enqueueInputEvent(fallbackEvent);
            }
        }
    }

    private static boolean isNavigationKey(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 61:
            case 62:
            case 66:
            case 92:
            case 93:
            case 122:
            case 123:
                return true;
            default:
                return false;
        }
    }

    private static boolean isTypingKey(KeyEvent keyEvent) {
        return keyEvent.getUnicodeChar() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkForLeavingTouchModeAndConsume(KeyEvent event) {
        if (!this.mAttachInfo.mInTouchMode) {
            return false;
        }
        int action = event.getAction();
        if ((action != 0 && action != 2) || (event.getFlags() & 4) != 0) {
            return false;
        }
        if (event.hasNoModifiers() && isNavigationKey(event)) {
            return ensureTouchMode(false);
        }
        if (!isTypingKey(event)) {
            return false;
        }
        ensureTouchMode(false);
        return false;
    }

    void setLocalDragState(Object obj) {
        this.mLocalDragState = obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDragEvent(DragEvent event) {
        InputMethodManager imm;
        if (this.mView != null && this.mAdded) {
            int what = event.mAction;
            if (what == 1) {
                this.mCurrentDragView = null;
                this.mDragDescription = event.mClipDescription;
                View view = this.mStartedDragViewForA11y;
                if (view != null) {
                    view.sendWindowContentChangedAccessibilityEvent(128);
                }
            } else {
                if (what == 4) {
                    this.mDragDescription = null;
                }
                event.mClipDescription = this.mDragDescription;
            }
            if (what == 6) {
                if (View.sCascadedDragDrop) {
                    this.mView.dispatchDragEnterExitInPreN(event);
                }
                setDragFocus(null, event);
                WindowConfiguration windowConfiguration = this.mContext.getResources().getConfiguration().windowConfiguration;
                if (windowConfiguration.getWindowingMode() == 6 && !windowConfiguration.isAlwaysOnTop() && windowConfiguration.getStagePosition() == 16 && (imm = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class)) != null) {
                    imm.semForceHideSoftInput();
                    Log.m98i(this.mTag, "ACTION_DRAG_EXITED in primary window. Call hideSoftInput");
                }
            } else {
                if (what == 2 || what == 3) {
                    this.mDragPoint.set(event.f541mX, event.f542mY);
                    CompatibilityInfo.Translator translator = this.mTranslator;
                    if (translator != null) {
                        translator.translatePointInScreenToAppWindow(this.mDragPoint);
                    }
                    int i = this.mCurScrollY;
                    if (i != 0) {
                        this.mDragPoint.offset(0.0f, i);
                    }
                    event.f541mX = this.mDragPoint.f87x;
                    event.f542mY = this.mDragPoint.f88y;
                }
                View prevDragView = this.mCurrentDragView;
                if (what == 3 && event.mClipData != null) {
                    event.mClipData.prepareToEnterProcess(this.mView.getContext().getAttributionSource());
                }
                boolean result = this.mView.dispatchDragEvent(event);
                if (!result && what == 1 && event.isStickyEvent()) {
                    Log.m98i(this.mTag, "Save sticky drag event");
                    this.mSavedStickyDragEvent = DragEvent.obtain(event);
                }
                boolean ignoreEvent = event.isEavesDrop();
                if (what == 2 && !ignoreEvent) {
                    int pointerIconType = InputManagerGlobal.getInstance().getPointerIconType();
                    if (event.mEventHandlerWasCalled) {
                        if (pointerIconType != 1021) {
                            InputManagerGlobal.getInstance().setPointerIconType(1021);
                        }
                    } else {
                        setDragFocus(null, event);
                        if (pointerIconType != 1012) {
                            InputManagerGlobal.getInstance().setPointerIconType(1012);
                        }
                    }
                }
                if (prevDragView != this.mCurrentDragView) {
                    if (prevDragView != null) {
                        try {
                            InputManagerGlobal.getInstance().setPointerIconType(1012);
                            this.mWindowSession.dragRecipientExited(this.mWindow);
                        } catch (RemoteException e) {
                            Slog.m115e(this.mTag, "Unable to note drag target change");
                        }
                    }
                    if (this.mCurrentDragView != null) {
                        InputManagerGlobal.getInstance().setPointerIconType(1021);
                        this.mWindowSession.dragRecipientEntered(this.mWindow);
                    }
                }
                if (what == 3) {
                    try {
                        Log.m98i(this.mTag, "Reporting drop result: " + result);
                        this.mWindowSession.reportDropResult(this.mWindow, result);
                    } catch (RemoteException e2) {
                        Log.m96e(this.mTag, "Unable to report drop result");
                    }
                }
                if (what == 4) {
                    if (this.mStartedDragViewForA11y != null) {
                        if (!event.getResult()) {
                            this.mStartedDragViewForA11y.sendWindowContentChangedAccessibilityEvent(512);
                        }
                        this.mStartedDragViewForA11y.setAccessibilityDragStarted(false);
                    }
                    this.mStartedDragViewForA11y = null;
                    this.mCurrentDragView = null;
                    setLocalDragState(null);
                    this.mAttachInfo.mDragToken = null;
                    if (this.mAttachInfo.mDragSurface != null) {
                        this.mAttachInfo.mDragSurface.release();
                        this.mAttachInfo.mDragSurface = null;
                    }
                    clearSavedStickyDragEvent();
                }
            }
        }
        event.recycle();
    }

    @Override // android.view.ViewParent
    public void requestSendStickyDragStartedEvent(View child) {
        if (this.mSavedStickyDragEvent != null) {
            Log.m98i(this.mTag, "sendSavedStickyDragEventIfNeeded");
            dispatchDragEvent(this.mSavedStickyDragEvent);
            this.mSavedStickyDragEvent = null;
        }
    }

    private void clearSavedStickyDragEvent() {
        if (this.mSavedStickyDragEvent != null) {
            Log.m98i(this.mTag, "clearSavedStickyDragEvent");
            this.mSavedStickyDragEvent.recycle();
            this.mSavedStickyDragEvent = null;
        }
    }

    public void onWindowTitleChanged() {
        this.mAttachInfo.mForceReportNewAttributes = true;
    }

    public void handleDispatchWindowShown() {
        this.mAttachInfo.mTreeObserver.dispatchOnWindowShown();
    }

    public void handleRequestKeyboardShortcuts(IResultReceiver receiver, int deviceId) {
        Bundle data = new Bundle();
        ArrayList<KeyboardShortcutGroup> list = new ArrayList<>();
        View view = this.mView;
        if (view != null) {
            view.requestKeyboardShortcuts(list, deviceId);
        }
        data.putParcelableArrayList(WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY, list);
        try {
            receiver.send(0, data);
        } catch (RemoteException e) {
        }
    }

    public void getLastTouchPoint(Point outLocation) {
        outLocation.f85x = (int) this.mLastTouchPoint.f87x;
        outLocation.f86y = (int) this.mLastTouchPoint.f88y;
    }

    public int getLastTouchSource() {
        return this.mLastTouchSource;
    }

    public int getLastClickToolType() {
        return this.mLastClickToolType;
    }

    public void setDragFocus(View newDragTarget, DragEvent event) {
        if (this.mCurrentDragView != newDragTarget && !View.sCascadedDragDrop) {
            float tx = event.f541mX;
            float ty = event.f542mY;
            int action = event.mAction;
            ClipData td = event.mClipData;
            event.f541mX = 0.0f;
            event.f542mY = 0.0f;
            event.mClipData = null;
            if (this.mCurrentDragView != null) {
                event.mAction = 6;
                this.mCurrentDragView.callDragEventHandler(event);
            }
            if (newDragTarget != null) {
                event.mAction = 5;
                newDragTarget.callDragEventHandler(event);
            }
            event.mAction = action;
            event.f541mX = tx;
            event.f542mY = ty;
            event.mClipData = td;
        }
        this.mCurrentDragView = newDragTarget;
    }

    void setDragStartedViewForAccessibility(View view) {
        if (this.mStartedDragViewForA11y == null) {
            this.mStartedDragViewForA11y = view;
        }
    }

    private AudioManager getAudioManager() {
        View view = this.mView;
        if (view == null) {
            throw new IllegalStateException("getAudioManager called when there is no mView");
        }
        if (this.mAudioManager == null) {
            AudioManager audioManager = (AudioManager) view.getContext().getSystemService("audio");
            this.mAudioManager = audioManager;
            this.mFastScrollSoundEffectsEnabled = audioManager.areNavigationRepeatSoundEffectsEnabled();
        }
        return this.mAudioManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AutofillManager getAutofillManager() {
        View view = this.mView;
        if (view instanceof ViewGroup) {
            ViewGroup decorView = (ViewGroup) view;
            if (decorView.getChildCount() > 0) {
                return (AutofillManager) decorView.getChildAt(0).getContext().getSystemService(AutofillManager.class);
            }
            return null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAutofillUiShowing() {
        AutofillManager afm = getAutofillManager();
        if (afm == null) {
            return false;
        }
        return afm.isAutofillUiShowing();
    }

    public AccessibilityInteractionController getAccessibilityInteractionController() {
        if (this.mView == null) {
            throw new IllegalStateException("getAccessibilityInteractionController called when there is no mView");
        }
        if (this.mAccessibilityInteractionController == null) {
            this.mAccessibilityInteractionController = new AccessibilityInteractionController(this);
        }
        return this.mAccessibilityInteractionController;
    }

    private boolean shouldNotLocalLayout(WindowConfiguration winConfig, WindowManager.LayoutParams params) {
        if (winConfig.isOverlappingWithCutout()) {
            return true;
        }
        if (!CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT) {
            return false;
        }
        DisplayCutout cutout = this.mInsetsController.getState().getDisplayCutout();
        int largeCutoutSize = Math.max(Math.max(cutout.getSafeInsetLeft(), cutout.getSafeInsetRight()), Math.max(cutout.getSafeInsetTop(), cutout.getSafeInsetBottom()));
        return largeCutoutSize > 0 && largeCutoutSize <= this.mMinimumSizeForOverlappingWithCutoutAsDefault;
    }

    private boolean shouldNotLocalLayoutEmbedded(WindowConfiguration winConfig) {
        return winConfig.isEmbedded() && this.mWindowAttributes.type == 2038;
    }

    private boolean shouldNotLocalLayoutPopOver(WindowConfiguration winConfig) {
        InsetsSource imeSource;
        return winConfig.isPopOver() && (imeSource = this.mInsetsController.getState().peekSource(InsetsSource.ID_IME)) != null && imeSource.isVisible();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0349  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x035b  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0365  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x03db  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x042d  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0436  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0460  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x054d  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x054f  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x040a  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x035d  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x034b  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0249  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int relayoutWindow(WindowManager.LayoutParams layoutParams, int i, boolean z) throws RemoteException {
        boolean z2;
        Object[] objArr;
        int i2;
        int i3;
        StringBuilder sb;
        int i4;
        String str;
        boolean z3;
        WindowConfiguration windowConfiguration;
        int relayout;
        boolean z4;
        int i5;
        boolean z5;
        CompatTranslator compatTranslator;
        Rect rect;
        WindowConfiguration windowConfiguration2 = getConfiguration().windowConfiguration;
        WindowConfiguration windowConfiguration3 = this.mLastReportedMergedConfiguration.getGlobalConfiguration().windowConfiguration;
        WindowConfiguration compatWindowConfiguration = getCompatWindowConfiguration();
        int i6 = this.mMeasuredWidth;
        int i7 = this.mMeasuredHeight;
        if (LOCAL_LAYOUT && (this.mViewFrameInfo.flags & 1) == 0 && this.mWindowAttributes.type != 3 && this.mSyncSeqId <= this.mLastSyncSeqId) {
            if (windowConfiguration2.diff(windowConfiguration3, false) == 0 && !shouldNotLocalLayout(compatWindowConfiguration, layoutParams) && ((!CoreRune.MW_EMBED_ACTIVITY || !shouldNotLocalLayoutEmbedded(compatWindowConfiguration)) && !shouldNotLocalLayoutPopOver(compatWindowConfiguration))) {
                InsetsState state = this.mInsetsController.getState();
                Rect rect2 = this.mTempRect;
                state.getDisplayCutoutSafe(rect2);
                this.mWindowLayout.computeFrames(this.mWindowAttributes.forRotation(compatWindowConfiguration.getRotation()), state, rect2, compatWindowConfiguration.getBounds(), compatWindowConfiguration.getWindowingMode(), i6, i7, this.mInsetsController.getRequestedVisibleTypes(), 1.0f, this.mTmpFrames, compatWindowConfiguration.getStageType());
                this.mWinFrameInScreen.set(this.mTmpFrames.frame);
                CompatibilityInfo.Translator translator = this.mTranslator;
                if (translator != null) {
                    translator.translateRectInAppWindowToScreen(this.mWinFrameInScreen);
                }
                Rect rect3 = this.mLastLayoutFrame;
                Rect rect4 = this.mTmpFrames.frame;
                boolean z6 = ((rect4.top != rect3.top || rect4.left != rect3.left) && (rect4.width() != rect3.width() || rect4.height() != rect3.height())) ? false : true;
                if (z6 && (rect = this.mPendingWinFrame) != null) {
                    if (rect.width() != rect4.width() || this.mPendingWinFrame.height() != rect4.height()) {
                        z6 = false;
                        Log.m98i(this.mTag, "Request to relayout frame from wm due to using pendingFrame=" + this.mPendingWinFrame + " instead of newFrame=" + rect4);
                        this.mPendingWinFrame = null;
                    }
                }
                if (z6 && compatWindowConfiguration.getRotation() == 2 && this.mDisplay.getDisplayId() != 0 && (rect4.width() != this.mWinFrame.width() || rect4.height() != this.mWinFrame.height())) {
                    z6 = false;
                    Log.m98i(this.mTag, "Request to relayout frame because WindowFrame is changed by handleResized");
                }
                z2 = z6;
                float f = this.mAttachInfo.mApplicationScale;
                if (layoutParams == null && this.mTranslator != null) {
                    layoutParams.backup();
                    this.mTranslator.translateWindowLayout(layoutParams);
                    objArr = true;
                } else {
                    objArr = false;
                }
                if (layoutParams != null && this.mOrigWindowType != layoutParams.type && this.mTargetSdkVersion < 14) {
                    Slog.m121w(this.mTag, "Window type can not be changed after the window is added; ignoring change of " + this.mView);
                    layoutParams.type = this.mOrigWindowType;
                }
                StringBuilder append = new StringBuilder(NavigationBarInflaterView.KEY_CODE_START).append(this.mWinFrame.left).append(',').append(this.mWinFrame.top).append(',').append(this.mWinFrame.right).append(',').append(this.mWinFrame.bottom).append(')');
                int generationId = this.mSurface.getGenerationId();
                long currentTimeMillis = System.currentTimeMillis();
                int i8 = (int) ((i6 * f) + 0.5f);
                int i9 = (int) ((i7 * f) + 0.5f);
                int i10 = this.mRelayoutSeq + 1;
                this.mRelayoutSeq = i10;
                if (!z2) {
                    IWindowSession iWindowSession = this.mWindowSession;
                    BinderC3744W binderC3744W = this.mWindow;
                    int i11 = this.mLastSyncSeqId;
                    i2 = i8;
                    sb = append;
                    i4 = i9;
                    str = NavigationBarInflaterView.KEY_CODE_START;
                    z3 = z2;
                    i3 = generationId;
                    iWindowSession.relayoutAsync(binderC3744W, layoutParams, i2, i4, i, z ? 1 : 0, i10, i11);
                    windowConfiguration = compatWindowConfiguration;
                    relayout = 0;
                    z4 = true;
                } else {
                    i2 = i8;
                    i3 = generationId;
                    sb = append;
                    i4 = i9;
                    str = NavigationBarInflaterView.KEY_CODE_START;
                    z3 = z2;
                    windowConfiguration = compatWindowConfiguration;
                    relayout = this.mWindowSession.relayout(this.mWindow, layoutParams, i2, i4, i, z ? 1 : 0, i10, this.mLastSyncSeqId, this.mTmpFrames, this.mPendingMergedConfiguration, this.mSurfaceControl, this.mTempInsets, this.mTempControls, this.mRelayoutBundle);
                    z4 = true;
                    this.mRelayoutRequested = true;
                    int i12 = this.mRelayoutBundle.getInt("seqid");
                    if (i12 > 0) {
                        this.mSyncSeqId = i12;
                    }
                    this.mWinFrameInScreen.set(this.mTmpFrames.frame);
                    CompatibilityInfo.Translator translator2 = this.mTranslator;
                    if (translator2 != null) {
                        translator2.translateRectInScreenToAppWindow(this.mTmpFrames.frame);
                        this.mTranslator.translateRectInScreenToAppWindow(this.mTmpFrames.displayFrame);
                        this.mTranslator.translateRectInScreenToAppWindow(this.mTmpFrames.attachedFrame);
                        this.mTranslator.translateInsetsStateInScreenToAppWindow(this.mTempInsets);
                        this.mTranslator.translateSourceControlsInScreenToAppWindow(this.mTempControls.get());
                    }
                    this.mInvCompatScale = 1.0f / this.mTmpFrames.compatScale;
                    CompatibilityInfo.applyOverrideScaleIfNeeded(this.mPendingMergedConfiguration);
                    this.mInsetsController.onStateChanged(this.mTempInsets);
                    this.mInsetsController.onControlsChanged(this.mTempControls.get());
                    this.mPendingAlwaysConsumeSystemBars = (relayout & 8) != 0;
                    this.mCanAllowPokeDrawLock = (relayout & 1024) == 1024;
                }
                int rotationToBufferTransform = SurfaceControl.rotationToBufferTransform((this.mDisplay.getInstallOrientation() + this.mDisplay.getRotation()) % 4);
                WindowLayout.computeSurfaceSize(this.mWindowAttributes, windowConfiguration.getMaxBounds(), i2, i4, this.mWinFrameInScreen, this.mPendingDragResizing, this.mSurfaceSize);
                boolean z7 = rotationToBufferTransform == this.mLastTransformHint ? z4 : false;
                boolean equals = this.mLastSurfaceSize.equals(this.mSurfaceSize) ^ z4;
                boolean z8 = (relayout & 2) != 2 ? z4 : false;
                if (this.mAttachInfo.mThreadedRenderer != null) {
                    i5 = 0;
                } else if (!z7 && !equals && !z8) {
                    i5 = 0;
                } else if (this.mAttachInfo.mThreadedRenderer.pause()) {
                    i5 = 0;
                    this.mDirty.set(0, 0, this.mWidth, this.mHeight);
                } else {
                    i5 = 0;
                }
                if (this.mSurfaceControl.isValid() && !HardwareRenderer.isDrawingEnabled()) {
                    this.mTransaction.setWindowCrop(this.mSurfaceControl, this.mSurfaceSize.f85x, this.mSurfaceSize.f86y).apply();
                }
                this.mLastTransformHint = rotationToBufferTransform;
                this.mSurfaceControl.setTransformHint(rotationToBufferTransform);
                if (this.mAttachInfo.mContentCaptureManager != null) {
                    MainContentCaptureSession mainContentCaptureSession = this.mAttachInfo.mContentCaptureManager.getMainContentCaptureSession();
                    mainContentCaptureSession.notifyWindowBoundsChanged(mainContentCaptureSession.getId(), getConfiguration().windowConfiguration.getBounds());
                }
                long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                if (!this.mSurfaceControl.isValid()) {
                    if (!useBLAST()) {
                        this.mSurface.copyFrom(this.mSurfaceControl);
                    } else {
                        updateBlastSurfaceIfNeeded();
                    }
                    if (this.mAttachInfo.mThreadedRenderer != null) {
                        this.mAttachInfo.mThreadedRenderer.setSurfaceControl(this.mSurfaceControl, this.mBlastBufferQueue);
                    }
                    updateRenderHdrSdrRatio();
                    if (this.mPreviousTransformHint != rotationToBufferTransform) {
                        this.mPreviousTransformHint = rotationToBufferTransform;
                        dispatchTransformHintChanged(rotationToBufferTransform);
                    }
                } else {
                    if (this.mAttachInfo.mThreadedRenderer != null && this.mAttachInfo.mThreadedRenderer.pause()) {
                        this.mDirty.set(i5, i5, this.mWidth, this.mHeight);
                    }
                    destroySurface();
                }
                updateCutoutRemoveNeeded(relayout);
                z5 = z3;
                if (!z5) {
                    updateCompatTranslator(relayout);
                }
                compatTranslator = getCompatTranslator();
                if (compatTranslator != null) {
                    if (CoreRune.FW_BOUNDS_COMPAT_TRANSLATOR_AS_BOUNDS) {
                        updatePositionInBounds(compatTranslator, this.mPendingMergedConfiguration.getOverrideConfiguration());
                    }
                    compatTranslator.savePositionInScreen(this.mTmpFrames.frame.left, this.mTmpFrames.frame.top);
                    compatTranslator.translateToWindow(this.mTmpFrames.frame);
                    compatTranslator.translateToWindow(this.mPendingBackDropFrame);
                }
                if (objArr != false) {
                    layoutParams.restore();
                }
                setFrame(this.mTmpFrames.frame, z4);
                Log.m98i(this.mTag, "Relayout returned: old=" + ((CharSequence) sb) + " new=" + ((CharSequence) new StringBuilder(str).append(this.mWinFrame.left).append(',').append(this.mWinFrame.top).append(',').append(this.mWinFrame.right).append(',').append(this.mWinFrame.bottom).append(')')) + " relayoutAsync=" + z5 + " req=(" + i2 + ',' + i4 + ')' + i + " dur=" + currentTimeMillis2 + " res=0x" + Integer.toHexString(relayout) + " s={" + this.mSurface.isValid() + ' ' + ("0x" + Long.toHexString(this.mSurface.mNativeObject)) + "} ch=" + (i3 == this.mSurface.getGenerationId()) + " seqId=" + this.mSyncSeqId);
                return relayout;
            }
        }
        z2 = false;
        float f2 = this.mAttachInfo.mApplicationScale;
        if (layoutParams == null) {
        }
        objArr = false;
        if (layoutParams != null) {
            Slog.m121w(this.mTag, "Window type can not be changed after the window is added; ignoring change of " + this.mView);
            layoutParams.type = this.mOrigWindowType;
        }
        StringBuilder append2 = new StringBuilder(NavigationBarInflaterView.KEY_CODE_START).append(this.mWinFrame.left).append(',').append(this.mWinFrame.top).append(',').append(this.mWinFrame.right).append(',').append(this.mWinFrame.bottom).append(')');
        int generationId2 = this.mSurface.getGenerationId();
        long currentTimeMillis3 = System.currentTimeMillis();
        int i82 = (int) ((i6 * f2) + 0.5f);
        int i92 = (int) ((i7 * f2) + 0.5f);
        int i102 = this.mRelayoutSeq + 1;
        this.mRelayoutSeq = i102;
        if (!z2) {
        }
        int rotationToBufferTransform2 = SurfaceControl.rotationToBufferTransform((this.mDisplay.getInstallOrientation() + this.mDisplay.getRotation()) % 4);
        WindowLayout.computeSurfaceSize(this.mWindowAttributes, windowConfiguration.getMaxBounds(), i2, i4, this.mWinFrameInScreen, this.mPendingDragResizing, this.mSurfaceSize);
        boolean z72 = rotationToBufferTransform2 == this.mLastTransformHint ? z4 : false;
        boolean equals2 = this.mLastSurfaceSize.equals(this.mSurfaceSize) ^ z4;
        boolean z82 = (relayout & 2) != 2 ? z4 : false;
        if (this.mAttachInfo.mThreadedRenderer != null) {
        }
        if (this.mSurfaceControl.isValid()) {
            this.mTransaction.setWindowCrop(this.mSurfaceControl, this.mSurfaceSize.f85x, this.mSurfaceSize.f86y).apply();
        }
        this.mLastTransformHint = rotationToBufferTransform2;
        this.mSurfaceControl.setTransformHint(rotationToBufferTransform2);
        if (this.mAttachInfo.mContentCaptureManager != null) {
        }
        long currentTimeMillis22 = System.currentTimeMillis() - currentTimeMillis3;
        if (!this.mSurfaceControl.isValid()) {
        }
        updateCutoutRemoveNeeded(relayout);
        z5 = z3;
        if (!z5) {
        }
        compatTranslator = getCompatTranslator();
        if (compatTranslator != null) {
        }
        if (objArr != false) {
        }
        setFrame(this.mTmpFrames.frame, z4);
        Log.m98i(this.mTag, "Relayout returned: old=" + ((CharSequence) sb) + " new=" + ((CharSequence) new StringBuilder(str).append(this.mWinFrame.left).append(',').append(this.mWinFrame.top).append(',').append(this.mWinFrame.right).append(',').append(this.mWinFrame.bottom).append(')')) + " relayoutAsync=" + z5 + " req=(" + i2 + ',' + i4 + ')' + i + " dur=" + currentTimeMillis22 + " res=0x" + Integer.toHexString(relayout) + " s={" + this.mSurface.isValid() + ' ' + ("0x" + Long.toHexString(this.mSurface.mNativeObject)) + "} ch=" + (i3 == this.mSurface.getGenerationId()) + " seqId=" + this.mSyncSeqId);
        return relayout;
    }

    private void updateOpacity(WindowManager.LayoutParams params, boolean dragResizing, boolean forceUpdate) {
        boolean opaque = false;
        if (!PixelFormat.formatHasAlpha(params.format) && params.surfaceInsets.left == 0 && params.surfaceInsets.top == 0 && params.surfaceInsets.right == 0 && params.surfaceInsets.bottom == 0 && !dragResizing) {
            opaque = true;
        }
        if (!forceUpdate && this.mIsSurfaceOpaque == opaque) {
            return;
        }
        ThreadedRenderer renderer = this.mAttachInfo.mThreadedRenderer;
        if (renderer != null && renderer.rendererOwnsSurfaceControlOpacity()) {
            opaque = renderer.setSurfaceControlOpaque(opaque);
        } else {
            this.mTransaction.setOpaque(this.mSurfaceControl, opaque).apply();
        }
        this.mIsSurfaceOpaque = opaque;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFrame(Rect frame, boolean withinRelayout) {
        Rect rect;
        boolean changed = !this.mWinFrame.equals(frame);
        if (!this.mForceUpdateBoundsLayer) {
            this.mForceUpdateBoundsLayer = changed;
        }
        boolean changed2 = this.mFirst;
        if (!changed2 && !this.mWinFrame.equals(frame) && frame.isEmpty()) {
            Log.m98i(this.mTag, "Force to draw a frame when frame become empty from non-empty");
            this.mForceDraw = true;
        }
        Rect rect2 = this.mPendingWinFrame;
        if (rect2 != null && rect2.equals(frame)) {
            this.mPendingWinFrame = null;
        }
        this.mWinFrame.set(frame);
        if (withinRelayout) {
            this.mLastLayoutFrame.set(frame);
        }
        CompatTranslator translator = getCompatTranslator();
        if (translator != null) {
            this.mWinFrameScreen.set(frame);
            translator.translateToScreen(this.mWinFrameScreen);
            frame = this.mWinFrameScreen;
        }
        WindowConfiguration winConfig = getCompatWindowConfiguration();
        Rect rect3 = this.mPendingBackDropFrame;
        if (this.mPendingDragResizing && !winConfig.useWindowFrameForBackdrop()) {
            rect = winConfig.getMaxBounds();
        } else {
            rect = frame;
        }
        rect3.set(rect);
        this.mPendingBackDropFrame.offsetTo(0, 0);
        InsetsController insetsController = this.mInsetsController;
        Rect rect4 = this.mOverrideInsetsFrame;
        if (rect4 == null) {
            rect4 = frame;
        }
        insetsController.onFrameChanged(rect4);
    }

    void setOverrideInsetsFrame(Rect frame) {
        Rect rect = new Rect(frame);
        this.mOverrideInsetsFrame = rect;
        this.mInsetsController.onFrameChanged(rect);
    }

    void getDisplayFrame(Rect outFrame) {
        outFrame.set(this.mTmpFrames.displayFrame);
        applyViewBoundsSandboxingIfNeeded(outFrame);
    }

    void getWindowVisibleDisplayFrame(Rect outFrame) {
        outFrame.set(this.mTmpFrames.displayFrame);
        Rect insets = this.mAttachInfo.mVisibleInsets;
        outFrame.left += insets.left;
        outFrame.top += insets.top;
        outFrame.right -= insets.right;
        outFrame.bottom -= insets.bottom;
        applyViewBoundsSandboxingIfNeeded(outFrame);
    }

    void applyViewBoundsSandboxingIfNeeded(Rect inOutRect) {
        if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
            Configuration config = this.mLastReportedMergedConfiguration.getMergedConfiguration();
            if (CompatSandbox.isViewBoundsSandboxingEnabled(config)) {
                Rect bounds = getScaledBounds(config.windowConfiguration);
                inOutRect.offset(-bounds.left, -bounds.top);
                return;
            }
        }
        if (this.mViewBoundsSandboxingEnabled) {
            Rect bounds2 = getConfiguration().windowConfiguration.getBounds();
            inOutRect.offset(-bounds2.left, -bounds2.top);
        }
    }

    public void applyViewLocationSandboxingIfNeeded(int[] outLocation) {
        if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
            Configuration config = this.mLastReportedMergedConfiguration.getMergedConfiguration();
            if (CompatSandbox.isViewBoundsSandboxingEnabled(config)) {
                Rect bounds = getScaledBounds(config.windowConfiguration);
                outLocation[0] = outLocation[0] - bounds.left;
                outLocation[1] = outLocation[1] - bounds.top;
                return;
            }
        }
        if (this.mViewBoundsSandboxingEnabled) {
            Rect bounds2 = getConfiguration().windowConfiguration.getBounds();
            outLocation[0] = outLocation[0] - bounds2.left;
            outLocation[1] = outLocation[1] - bounds2.top;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0035 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0038 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean getViewBoundsSandboxingEnabled() {
        List<PackageManager.Property> properties;
        boolean isOptedOut;
        if (ActivityThread.isSystem() || !CompatChanges.isChangeEnabled(ActivityInfo.OVERRIDE_SANDBOX_VIEW_BOUNDS_APIS)) {
            return false;
        }
        try {
            properties = this.mContext.getPackageManager().queryApplicationProperty(WindowManager.PROPERTY_COMPAT_ALLOW_SANDBOXING_VIEW_BOUNDS_APIS);
        } catch (RuntimeException e) {
        }
        if (!properties.isEmpty()) {
            if (!properties.get(0).getBoolean()) {
                isOptedOut = true;
                return isOptedOut;
            }
        }
        isOptedOut = false;
        if (isOptedOut) {
        }
    }

    @Override // android.view.View.AttachInfo.Callbacks
    public void playSoundEffect(int effectId) {
        if ((this.mDisplay.getFlags() & 1024) != 0) {
            return;
        }
        checkThread();
        try {
            AudioManager audioManager = getAudioManager();
            if (this.mFastScrollSoundEffectsEnabled && SoundEffectConstants.isNavigationRepeat(effectId)) {
                audioManager.playSoundEffect(SoundEffectConstants.nextNavigationRepeatSoundEffectId());
                return;
            }
            switch (effectId) {
                case 0:
                    audioManager.playSoundEffect(0);
                    return;
                case 1:
                case 5:
                    audioManager.playSoundEffect(3);
                    return;
                case 2:
                case 6:
                    audioManager.playSoundEffect(1);
                    return;
                case 3:
                case 7:
                    audioManager.playSoundEffect(4);
                    return;
                case 4:
                case 8:
                    audioManager.playSoundEffect(2);
                    return;
                default:
                    throw new IllegalArgumentException("unknown effect id " + effectId + " not defined in " + SoundEffectConstants.class.getCanonicalName());
            }
        } catch (IllegalStateException e) {
            Log.m96e(this.mTag, "FATAL EXCEPTION when attempting to play sound effect: " + e);
            e.printStackTrace();
        }
    }

    @Override // android.view.View.AttachInfo.Callbacks
    public boolean performHapticFeedback(int effectId, boolean always) {
        if ((this.mDisplay.getFlags() & 1024) != 0) {
            return false;
        }
        if (this.mDisplay.getDisplayId() != 0 && getConfiguration().isDesktopModeEnabled()) {
            return false;
        }
        try {
            this.mWindowSession.performHapticFeedbackAsync(effectId, always);
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override // android.view.ViewParent
    public View focusSearch(View focused, int direction) {
        checkThread();
        if (!(this.mView instanceof ViewGroup)) {
            return null;
        }
        return FocusFinder.getInstance().findNextFocus((ViewGroup) this.mView, focused, direction);
    }

    @Override // android.view.ViewParent
    public View keyboardNavigationClusterSearch(View currentCluster, int direction) {
        checkThread();
        return FocusFinder.getInstance().findNextKeyboardNavigationCluster(this.mView, currentCluster, direction);
    }

    public void debug() {
        this.mView.debug();
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1138166333441L, Objects.toString(this.mView));
        proto.write(1120986464258L, this.mDisplay.getDisplayId());
        proto.write(1133871366147L, this.mAppVisible);
        proto.write(1120986464261L, this.mHeight);
        proto.write(1120986464260L, this.mWidth);
        proto.write(1133871366150L, this.mIsAnimating);
        this.mVisRect.dumpDebug(proto, 1146756268039L);
        proto.write(1133871366152L, this.mIsDrawing);
        proto.write(1133871366153L, this.mAdded);
        this.mWinFrame.dumpDebug(proto, 1146756268042L);
        proto.write(1138166333452L, Objects.toString(this.mLastWindowInsets));
        proto.write(1138166333453L, InputMethodDebug.softInputModeToString(this.mSoftInputMode));
        proto.write(1120986464270L, this.mScrollY);
        proto.write(1120986464271L, this.mCurScrollY);
        proto.write(1133871366160L, this.mRemoved);
        this.mWindowAttributes.dumpDebug(proto, 1146756268049L);
        proto.end(token);
        this.mInsetsController.dumpDebug(proto, 1146756268036L);
        this.mImeFocusController.dumpDebug(proto, 1146756268039L);
    }

    public void dump(String prefix, PrintWriter writer) {
        String innerPrefix = prefix + "  ";
        writer.println(prefix + "ViewRoot:");
        writer.println(innerPrefix + "mAdded=" + this.mAdded);
        writer.println(innerPrefix + "mRemoved=" + this.mRemoved);
        writer.println(innerPrefix + "mStopped=" + this.mStopped);
        writer.println(innerPrefix + "mPausedForTransition=" + this.mPausedForTransition);
        writer.println(innerPrefix + "mConsumeBatchedInputScheduled=" + this.mConsumeBatchedInputScheduled);
        writer.println(innerPrefix + "mConsumeBatchedInputImmediatelyScheduled=" + this.mConsumeBatchedInputImmediatelyScheduled);
        writer.println(innerPrefix + "mPendingInputEventCount=" + this.mPendingInputEventCount);
        writer.println(innerPrefix + "mProcessInputEventsScheduled=" + this.mProcessInputEventsScheduled);
        writer.println(innerPrefix + "mTraversalScheduled=" + this.mTraversalScheduled);
        if (this.mTraversalScheduled) {
            writer.println(innerPrefix + " (barrier=" + this.mTraversalBarrier + NavigationBarInflaterView.KEY_CODE_END);
        }
        writer.println(innerPrefix + "mReportNextDraw=" + this.mReportNextDraw);
        if (this.mReportNextDraw) {
            writer.println(innerPrefix + " (reason=" + this.mLastReportNextDrawReason + NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mLastPerformTraversalsSkipDrawReason != null) {
            writer.println(innerPrefix + "mLastPerformTraversalsFailedReason=" + this.mLastPerformTraversalsSkipDrawReason);
        }
        if (this.mLastPerformDrawSkippedReason != null) {
            writer.println(innerPrefix + "mLastPerformDrawFailedReason=" + this.mLastPerformDrawSkippedReason);
        }
        if (this.mWmsRequestSyncGroupState != 0) {
            writer.println(innerPrefix + "mWmsRequestSyncGroupState=" + this.mWmsRequestSyncGroupState);
        }
        writer.println(innerPrefix + "mLastReportedMergedConfiguration=" + this.mLastReportedMergedConfiguration);
        writer.println(innerPrefix + "mLastConfigurationFromResources=" + this.mLastConfigurationFromResources);
        writer.println(innerPrefix + "mIsAmbientMode=" + this.mIsAmbientMode);
        writer.println(innerPrefix + "mUnbufferedInputSource=" + Integer.toHexString(this.mUnbufferedInputSource));
        if (this.mAttachInfo != null) {
            writer.print(innerPrefix + "mAttachInfo= ");
            this.mAttachInfo.dump(innerPrefix, writer);
        } else {
            writer.println(innerPrefix + "mAttachInfo=<null>");
        }
        this.mFirstInputStage.dump(innerPrefix, writer);
        WindowInputEventReceiver windowInputEventReceiver = this.mInputEventReceiver;
        if (windowInputEventReceiver != null) {
            windowInputEventReceiver.dump(innerPrefix, writer);
        }
        this.mChoreographer.dump(prefix, writer);
        this.mInsetsController.dump(prefix, writer);
        this.mOnBackInvokedDispatcher.dump(prefix, writer);
        writer.println(prefix + "View Hierarchy:");
        dumpViewHierarchy(innerPrefix, writer, this.mView);
    }

    private void dumpViewHierarchy(String prefix, PrintWriter writer, View view) {
        ViewGroup grp;
        int N;
        writer.print(prefix);
        if (view == null) {
            writer.println(SemCapabilities.FEATURE_TAG_NULL);
            return;
        }
        writer.println(view.toString());
        if (!(view instanceof ViewGroup) || (N = (grp = (ViewGroup) view).getChildCount()) <= 0) {
            return;
        }
        String prefix2 = prefix + "  ";
        for (int i = 0; i < N; i++) {
            dumpViewHierarchy(prefix2, writer, grp.getChildAt(i));
        }
    }

    static final class GfxInfo {
        public long renderNodeMemoryAllocated;
        public long renderNodeMemoryUsage;
        public int viewCount;

        GfxInfo() {
        }

        void add(GfxInfo other) {
            this.viewCount += other.viewCount;
            this.renderNodeMemoryUsage += other.renderNodeMemoryUsage;
            this.renderNodeMemoryAllocated += other.renderNodeMemoryAllocated;
        }
    }

    GfxInfo getGfxInfo() {
        GfxInfo info = new GfxInfo();
        View view = this.mView;
        if (view != null) {
            appendGfxInfo(view, info);
        }
        return info;
    }

    private static void computeRenderNodeUsage(RenderNode node, GfxInfo info) {
        if (node == null) {
            return;
        }
        info.renderNodeMemoryUsage += node.computeApproximateMemoryUsage();
        info.renderNodeMemoryAllocated += node.computeApproximateMemoryAllocated();
    }

    private static void appendGfxInfo(View view, GfxInfo info) {
        info.viewCount++;
        computeRenderNodeUsage(view.mRenderNode, info);
        computeRenderNodeUsage(view.mBackgroundRenderNode, info);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                appendGfxInfo(group.getChildAt(i), info);
            }
        }
    }

    boolean die(boolean immediate) {
        if (immediate && !this.mIsInTraversal) {
            doDie();
            return false;
        }
        if (!this.mIsDrawing) {
            destroyHardwareRenderer();
        } else {
            Log.m96e(this.mTag, "Attempting to destroy the window while drawing!\n  window=" + this + ", title=" + ((Object) this.mWindowAttributes.getTitle()));
        }
        this.mHandler.sendEmptyMessage(3);
        return true;
    }

    void doDie() {
        checkThread();
        synchronized (this) {
            if (this.mRemoved) {
                return;
            }
            this.mRemoved = true;
            this.mOnBackInvokedDispatcher.detachFromWindow();
            if (this.mAdded) {
                dispatchDetachedFromWindow();
            }
            if (this.mAdded && !this.mFirst) {
                destroyHardwareRenderer();
                View view = this.mView;
                if (view != null) {
                    int viewVisibility = view.getVisibility();
                    boolean viewVisibilityChanged = this.mViewVisibility != viewVisibility;
                    if (this.mWindowAttributesChanged || viewVisibilityChanged) {
                        try {
                            if ((1 & relayoutWindow(this.mWindowAttributes, viewVisibility, false)) != 0) {
                                this.mWindowSession.finishDrawing(this.mWindow, null, Integer.MAX_VALUE);
                            }
                        } catch (RemoteException e) {
                        }
                    }
                    destroySurface();
                }
            }
            this.mInsetsController.onControlsChanged(null);
            this.mAdded = false;
            AnimationHandler.removeRequestor(this);
            SurfaceSyncGroup surfaceSyncGroup = this.mActiveSurfaceSyncGroup;
            if (surfaceSyncGroup != null) {
                surfaceSyncGroup.markSyncReady();
                this.mActiveSurfaceSyncGroup = null;
            }
            if (this.mHasPendingTransactions) {
                this.mPendingTransaction.apply();
            }
            WindowManagerGlobal.getInstance().doRemoveView(this);
        }
    }

    public void requestUpdateConfiguration(Configuration config) {
        Message msg = this.mHandler.obtainMessage(18, config);
        this.mHandler.sendMessage(msg);
    }

    public void loadSystemProperties() {
        this.mHandler.post(new Runnable() { // from class: android.view.ViewRootImpl.7
            @Override // java.lang.Runnable
            public void run() {
                ViewRootImpl.this.mProfileRendering = SystemProperties.getBoolean(ViewRootImpl.PROPERTY_PROFILE_RENDERING, false);
                ViewRootImpl viewRootImpl = ViewRootImpl.this;
                viewRootImpl.profileRendering(viewRootImpl.mAttachInfo.mHasWindowFocus);
                if (ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null && ViewRootImpl.this.mAttachInfo.mThreadedRenderer.loadSystemProperties()) {
                    ViewRootImpl.this.invalidate();
                }
                boolean layout = DisplayProperties.debug_layout().orElse(false).booleanValue();
                if (layout != ViewRootImpl.this.mAttachInfo.mDebugLayout) {
                    ViewRootImpl.this.mAttachInfo.mDebugLayout = layout;
                    if (!ViewRootImpl.this.mHandler.hasMessages(22)) {
                        ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(22, 200L);
                    }
                }
            }
        });
    }

    private void destroyHardwareRenderer() {
        ThreadedRenderer hardwareRenderer = this.mAttachInfo.mThreadedRenderer;
        Consumer<Display> consumer = this.mHdrSdrRatioChangedListener;
        if (consumer != null) {
            this.mDisplay.unregisterHdrSdrRatioChangedListener(consumer);
        }
        if (hardwareRenderer != null) {
            HardwareRendererObserver hardwareRendererObserver = this.mHardwareRendererObserver;
            if (hardwareRendererObserver != null) {
                hardwareRenderer.removeObserver(hardwareRendererObserver);
            }
            View view = this.mView;
            if (view != null) {
                hardwareRenderer.destroyHardwareResources(view);
            }
            hardwareRenderer.destroy();
            if (ViewRune.COMMON_IS_PRODUCT_DEV) {
                Log.m94d(this.mTag, "mThreadedRenderer.destroy()#4");
            }
            hardwareRenderer.setRequested(false);
            this.mAttachInfo.mThreadedRenderer = null;
            this.mAttachInfo.mHardwareAccelerated = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchResized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) {
        InsetsState insetsState2;
        Message obtainMessage = this.mHandler.obtainMessage(z ? 5 : 4);
        SomeArgs obtain = SomeArgs.obtain();
        Log.m98i(this.mTag, "Resizing " + this + ": frame = " + clientWindowFrames.frame.toShortString() + " reportDraw = " + z + " forceLayout = " + z2 + " syncSeqId = " + i2);
        if (this.mWindowAttributes.type == 1) {
            this.mPendingWinFrame = clientWindowFrames.frame;
        }
        boolean z5 = Binder.getCallingPid() == Process.myPid();
        if (!z5) {
            insetsState2 = insetsState;
        } else {
            insetsState2 = new InsetsState(insetsState, true);
        }
        CompatibilityInfo.Translator translator = this.mTranslator;
        if (translator != null) {
            translator.translateInsetsStateInScreenToAppWindow(insetsState2);
        }
        if (insetsState2.isSourceOrDefaultVisible(InsetsSource.ID_IME, WindowInsets.Type.ime())) {
            ImeTracing.getInstance().triggerClientDump("ViewRootImpl#dispatchResized", getInsetsController().getHost().getInputMethodManager(), null);
        }
        obtain.arg1 = z5 ? new ClientWindowFrames(clientWindowFrames) : clientWindowFrames;
        obtain.arg2 = (!z5 || mergedConfiguration == null) ? mergedConfiguration : new MergedConfiguration(mergedConfiguration);
        obtain.arg3 = insetsState2;
        obtain.argi1 = z2 ? 1 : 0;
        obtain.argi2 = z3 ? 1 : 0;
        obtain.argi3 = i;
        obtain.argi4 = i2;
        obtain.argi5 = z4 ? 1 : 0;
        obtainMessage.obj = obtain;
        this.mHandler.sendMessage(obtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchInsetsControlChanged(InsetsState insetsState, InsetsSourceControl[] activeControls) {
        if (Binder.getCallingPid() == Process.myPid()) {
            insetsState = new InsetsState(insetsState, true);
            if (activeControls != null) {
                for (int i = activeControls.length - 1; i >= 0; i--) {
                    activeControls[i] = new InsetsSourceControl(activeControls[i]);
                }
            }
        }
        CompatibilityInfo.Translator translator = this.mTranslator;
        if (translator != null) {
            translator.translateInsetsStateInScreenToAppWindow(insetsState);
            this.mTranslator.translateSourceControlsInScreenToAppWindow(activeControls);
        }
        if (insetsState != null && insetsState.isSourceOrDefaultVisible(InsetsSource.ID_IME, WindowInsets.Type.ime())) {
            ImeTracing.getInstance().triggerClientDump("ViewRootImpl#dispatchInsetsControlChanged", getInsetsController().getHost().getInputMethodManager(), null);
        }
        SomeArgs args = SomeArgs.obtain();
        args.arg1 = insetsState;
        args.arg2 = activeControls;
        this.mHandler.obtainMessage(29, args).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showInsets(int i, boolean z, ImeTracker.Token token) {
        this.mHandler.obtainMessage(31, i, z ? 1 : 0, token).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideInsets(int i, boolean z, ImeTracker.Token token) {
        this.mHandler.obtainMessage(32, i, z ? 1 : 0, token).sendToTarget();
    }

    public void dispatchMoved(int newX, int newY) {
        if (DEBUG_LAYOUT) {
            Log.m100v(this.mTag, "Window moved " + this + ": newX=" + newX + " newY=" + newY);
        }
        if (this.mTranslator != null) {
            PointF point = new PointF(newX, newY);
            this.mTranslator.translatePointInScreenToAppWindow(point);
            newX = (int) (point.f87x + 0.5d);
            newY = (int) (point.f88y + 0.5d);
        }
        CompatTranslator translator = getCompatTranslator();
        if (translator != null) {
            if (CoreRune.FW_BOUNDS_COMPAT_TRANSLATOR_AS_BOUNDS) {
                updatePositionInBounds(translator, this.mLastReportedMergedConfiguration.getOverrideConfiguration());
            }
            if (translator.savePositionInScreen(newX, newY)) {
                this.mAttachInfo.mNeedsUpdateLightCenter = true;
            }
            Point point2 = new Point(newX, newY);
            translator.translateToWindow(point2);
            newX = point2.f85x;
            newY = point2.f86y;
        }
        Message msg = this.mHandler.obtainMessage(23, newX, newY);
        this.mHandler.sendMessage(msg);
    }

    private static final class QueuedInputEvent {
        public static final int FLAG_DEFERRED = 2;
        public static final int FLAG_DELIVER_POST_IME = 1;
        public static final int FLAG_FINISHED = 4;
        public static final int FLAG_FINISHED_HANDLED = 8;
        public static final int FLAG_MODIFIED_FOR_COMPATIBILITY = 64;
        public static final int FLAG_RESYNTHESIZED = 16;
        public static final int FLAG_UNHANDLED = 32;
        public InputEvent mEvent;
        public int mFlags;
        public QueuedInputEvent mNext;
        public InputEventReceiver mReceiver;

        private QueuedInputEvent() {
        }

        public boolean shouldSkipIme() {
            if ((this.mFlags & 1) != 0) {
                return true;
            }
            InputEvent inputEvent = this.mEvent;
            return (inputEvent instanceof MotionEvent) && (inputEvent.isFromSource(2) || this.mEvent.isFromSource(4194304));
        }

        public boolean shouldSendToSynthesizer() {
            if ((this.mFlags & 32) != 0) {
                return true;
            }
            return false;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("QueuedInputEvent{flags=");
            boolean hasPrevious = flagToString("DELIVER_POST_IME", 1, false, sb);
            if (!flagToString("UNHANDLED", 32, flagToString("RESYNTHESIZED", 16, flagToString("FINISHED_HANDLED", 8, flagToString("FINISHED", 4, flagToString("DEFERRED", 2, hasPrevious, sb), sb), sb), sb), sb)) {
                sb.append("0");
            }
            sb.append(", hasNextQueuedEvent=" + (this.mEvent != null ? "true" : "false"));
            sb.append(", hasInputEventReceiver=" + (this.mReceiver == null ? "false" : "true"));
            sb.append(", mEvent=" + this.mEvent + "}");
            return sb.toString();
        }

        private boolean flagToString(String name, int flag, boolean hasPrevious, StringBuilder sb) {
            if ((this.mFlags & flag) != 0) {
                if (hasPrevious) {
                    sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                }
                sb.append(name);
                return true;
            }
            return hasPrevious;
        }
    }

    private QueuedInputEvent obtainQueuedInputEvent(InputEvent event, InputEventReceiver receiver, int flags) {
        QueuedInputEvent q = this.mQueuedInputEventPool;
        if (q != null) {
            this.mQueuedInputEventPoolSize--;
            this.mQueuedInputEventPool = q.mNext;
            q.mNext = null;
        } else {
            q = new QueuedInputEvent();
        }
        q.mEvent = event;
        q.mReceiver = receiver;
        q.mFlags = flags;
        return q;
    }

    private void recycleQueuedInputEvent(QueuedInputEvent q) {
        q.mEvent = null;
        q.mReceiver = null;
        int i = this.mQueuedInputEventPoolSize;
        if (i < 10) {
            this.mQueuedInputEventPoolSize = i + 1;
            q.mNext = this.mQueuedInputEventPool;
            this.mQueuedInputEventPool = q;
        }
    }

    public void enqueueInputEvent(InputEvent event) {
        enqueueInputEvent(event, null, 0, false);
    }

    void enqueueInputEvent(InputEvent event, InputEventReceiver receiver, int flags, boolean processImmediately) {
        if (CoreRune.FW_SPEN_HOVER && (event instanceof KeyEvent) && (((KeyEvent) event).getFlags() & 33554432) != 0) {
            flags |= 1;
        }
        QueuedInputEvent q = obtainQueuedInputEvent(event, receiver, flags);
        if (event instanceof MotionEvent) {
            MotionEvent me = (MotionEvent) event;
            if (me.getAction() == 3) {
                EventLog.writeEvent(EventLogTags.VIEW_ENQUEUE_INPUT_EVENT, "Motion - Cancel", getTitle().toString());
            }
        } else if (event instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) event;
            if (ke.isCanceled()) {
                EventLog.writeEvent(EventLogTags.VIEW_ENQUEUE_INPUT_EVENT, "Key - Cancel", getTitle().toString());
            }
        }
        QueuedInputEvent last = this.mPendingInputEventTail;
        if (last == null) {
            this.mPendingInputEventHead = q;
            this.mPendingInputEventTail = q;
        } else {
            last.mNext = q;
            this.mPendingInputEventTail = q;
        }
        int i = this.mPendingInputEventCount + 1;
        this.mPendingInputEventCount = i;
        Trace.traceCounter(4L, this.mPendingInputEventQueueLengthCounterName, i);
        if (processImmediately) {
            doProcessInputEvents();
        } else {
            scheduleProcessInputEvents();
        }
    }

    private void scheduleProcessInputEvents() {
        if (!this.mProcessInputEventsScheduled) {
            this.mProcessInputEventsScheduled = true;
            Message msg = this.mHandler.obtainMessage(19);
            msg.setAsynchronous(true);
            this.mHandler.sendMessage(msg);
        }
    }

    void doProcessInputEvents() {
        while (this.mPendingInputEventHead != null) {
            QueuedInputEvent q = this.mPendingInputEventHead;
            QueuedInputEvent queuedInputEvent = q.mNext;
            this.mPendingInputEventHead = queuedInputEvent;
            if (queuedInputEvent == null) {
                this.mPendingInputEventTail = null;
            }
            q.mNext = null;
            int i = this.mPendingInputEventCount - 1;
            this.mPendingInputEventCount = i;
            Trace.traceCounter(4L, this.mPendingInputEventQueueLengthCounterName, i);
            if (q.mEvent instanceof MotionEvent) {
                MotionEvent me = (MotionEvent) q.mEvent;
                View view = this.mView;
                if (view != null && ((view.getTop() != 0 || this.mView.getLeft() != 0) && this.mWindowAttributes.layoutInDisplayCutoutMode == 1 && this.mWindowAttributes.type == 2)) {
                    me.offsetLocation(-this.mView.getLeft(), -this.mView.getTop());
                }
            }
            this.mViewFrameInfo.setInputEvent(this.mInputEventAssigner.processEvent(q.mEvent));
            deliverInputEvent(q);
        }
        if (this.mProcessInputEventsScheduled) {
            this.mProcessInputEventsScheduled = false;
            this.mHandler.removeMessages(19);
        }
    }

    private void deliverInputEvent(QueuedInputEvent q) {
        InputStage stage;
        Trace.asyncTraceBegin(8L, "deliverInputEvent", q.mEvent.getId());
        boolean isMotionEvent = q.mEvent instanceof MotionEvent;
        if (isMotionEvent) {
            MotionEvent event = (MotionEvent) q.mEvent;
            if (checkPalmRejection(event) && getPalmRejection(event)) {
                event.setAction(3);
            }
        }
        if (isMotionEvent) {
            MotionEvent event2 = (MotionEvent) q.mEvent;
            if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
                Configuration config = this.mLastReportedMergedConfiguration.getMergedConfiguration();
                if (CompatSandbox.isMotionEventSandboxingEnabled(config)) {
                    Rect bounds = config.windowConfiguration.getCompatSandboxBounds();
                    float xOffset = -bounds.left;
                    float yOffset = -bounds.top;
                    float scale = config.windowConfiguration.getCompatSandboxInvScale();
                    float overrideInvertedScale = CompatibilityInfo.getOverrideInvertedScale();
                    event2.setCompatSandboxScale(xOffset, yOffset, scale * overrideInvertedScale);
                    if (DEBUG_TOUCH_EVENT) {
                        Log.m94d(this.mTag, "MotionEventSandboxingEnabled, rawX" + event2.getRawX() + ", rawY" + event2.getRawY() + ", xOffset" + xOffset + ", yOffset" + yOffset + ", scale" + scale + ", overrideInvertedScale" + overrideInvertedScale);
                    }
                }
            }
            CompatTranslator translator = getCompatTranslator();
            if (translator != null) {
                translator.translateToWindow(event2);
            }
        }
        if (CoreRune.BIXBY_TOUCH && isMotionEvent && this.mSemPressGestureDetector != null) {
            MotionEvent event3 = (MotionEvent) q.mEvent;
            if (event3.getAction() == 0) {
                this.mBixbyTouchTriggered = false;
                this.mCanTriggerBixbyTouch = true;
                if (this.mSemPressGestureDetector.isInitFailed()) {
                    this.mSemPressGestureDetector.init(this.mContext, this.mView);
                }
            } else if (this.mBixbyTouchTriggered) {
                if (event3.getAction() == 1) {
                    this.mSemPressGestureDetector.dispatchTouchEvent(event3);
                }
                finishInputEvent(q);
                return;
            }
            if (this.mCanTriggerBixbyTouch && this.mSemPressGestureDetector.dispatchTouchEvent(event3)) {
                event3.setAction(3);
                this.mBixbyTouchTriggered = true;
            }
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.traceBegin(8L, "deliverInputEvent src=0x" + Integer.toHexString(q.mEvent.getSource()) + " eventTimeNano=" + q.mEvent.getEventTimeNanos() + " id=0x" + Integer.toHexString(q.mEvent.getId()));
        }
        try {
            if (this.mInputEventConsistencyVerifier != null) {
                Trace.traceBegin(8L, "verifyEventConsistency");
                this.mInputEventConsistencyVerifier.onInputEvent(q.mEvent, 0);
                Trace.traceEnd(8L);
            }
            if (q.shouldSendToSynthesizer()) {
                stage = this.mSyntheticInputStage;
            } else {
                stage = q.shouldSkipIme() ? this.mFirstPostImeInputStage : this.mFirstInputStage;
            }
            if (q.mEvent instanceof KeyEvent) {
                Trace.traceBegin(8L, "preDispatchToUnhandledKeyManager");
                this.mUnhandledKeyManager.preDispatch((KeyEvent) q.mEvent);
                Trace.traceEnd(8L);
            }
            if (stage != null) {
                handleWindowFocusChanged();
                stage.deliver(q);
            } else {
                finishInputEvent(q);
            }
        } catch (Throwable th) {
            throw th;
        } finally {
            Trace.traceEnd(8L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishInputEvent(QueuedInputEvent q) {
        Trace.asyncTraceEnd(8L, "deliverInputEvent", q.mEvent.getId());
        if (q.mReceiver != null) {
            boolean handled = (q.mFlags & 8) != 0;
            boolean modified = (q.mFlags & 64) != 0;
            if (modified) {
                Trace.traceBegin(8L, "processInputEventBeforeFinish");
                try {
                    InputEvent processedEvent = this.mInputCompatProcessor.processInputEventBeforeFinish(q.mEvent);
                    if (processedEvent != null) {
                        q.mReceiver.finishInputEvent(processedEvent, handled);
                    }
                } finally {
                    Trace.traceEnd(8L);
                }
            } else {
                q.mReceiver.finishInputEvent(q.mEvent, handled);
            }
        } else {
            q.mEvent.recycleIfNeededAfterDispatch();
        }
        recycleQueuedInputEvent(q);
    }

    static boolean isTerminalInputEvent(InputEvent event) {
        if (event instanceof KeyEvent) {
            KeyEvent keyEvent = (KeyEvent) event;
            return keyEvent.getAction() == 1;
        }
        MotionEvent motionEvent = (MotionEvent) event;
        int action = motionEvent.getAction();
        return action == 1 || action == 3 || action == 10;
    }

    void scheduleConsumeBatchedInput() {
        if (!this.mConsumeBatchedInputScheduled && !this.mConsumeBatchedInputImmediatelyScheduled) {
            this.mConsumeBatchedInputScheduled = true;
            this.mChoreographer.postCallback(0, this.mConsumedBatchedInputRunnable, null);
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.notifyCallbackPending();
            }
        }
    }

    void unscheduleConsumeBatchedInput() {
        if (this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = false;
            this.mChoreographer.removeCallbacks(0, this.mConsumedBatchedInputRunnable, null);
        }
    }

    void scheduleConsumeBatchedInputImmediately() {
        if (!this.mConsumeBatchedInputImmediatelyScheduled) {
            unscheduleConsumeBatchedInput();
            this.mConsumeBatchedInputImmediatelyScheduled = true;
            this.mHandler.post(this.mConsumeBatchedInputImmediatelyRunnable);
        }
    }

    boolean doConsumeBatchedInput(long frameTimeNanos) {
        boolean consumedBatches;
        WindowInputEventReceiver windowInputEventReceiver = this.mInputEventReceiver;
        if (windowInputEventReceiver != null) {
            consumedBatches = windowInputEventReceiver.consumeBatchedInputEvents(frameTimeNanos);
        } else {
            consumedBatches = false;
        }
        doProcessInputEvents();
        return consumedBatches;
    }

    final class TraversalRunnable implements Runnable {
        TraversalRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewRootImpl.this.doTraversal();
        }
    }

    final class WindowInputEventReceiver extends InputEventReceiver {
        public WindowInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(InputEvent event) {
            Trace.traceBegin(8L, "processInputEventForCompatibility");
            try {
                List<InputEvent> processedEvents = ViewRootImpl.this.mInputCompatProcessor.processInputEventForCompatibility(event);
                Trace.traceEnd(8L);
                if (processedEvents != null) {
                    if (processedEvents.isEmpty()) {
                        finishInputEvent(event, true);
                        return;
                    }
                    for (int i = 0; i < processedEvents.size(); i++) {
                        ViewRootImpl.this.enqueueInputEvent(processedEvents.get(i), this, 64, true);
                    }
                    return;
                }
                String traceKey = null;
                if (ViewRune.COMMON_IS_PRODUCT_DEV && (event instanceof MotionEvent)) {
                    MotionEvent motionEvent = (MotionEvent) event;
                    traceKey = String.format("(X=%d, Y=%d, Action=%d)", Integer.valueOf((int) motionEvent.getX()), Integer.valueOf((int) motionEvent.getY()), Integer.valueOf(motionEvent.getAction()));
                    Trace.traceBegin(8L, traceKey);
                }
                ViewRootImpl.this.enqueueInputEvent(event, this, 0, true);
                if (traceKey != null) {
                }
            } finally {
                Trace.traceEnd(8L);
            }
        }

        @Override // android.view.InputEventReceiver
        public void onBatchedInputEventPending(int source) {
            boolean unbuffered = ViewRootImpl.this.mUnbufferedInputDispatch || (ViewRootImpl.this.mUnbufferedInputSource & source) != 0;
            if (unbuffered) {
                if (ViewRootImpl.this.mConsumeBatchedInputScheduled) {
                    ViewRootImpl.this.unscheduleConsumeBatchedInput();
                }
                consumeBatchedInputEvents(-1L);
                return;
            }
            ViewRootImpl.this.scheduleConsumeBatchedInput();
        }

        @Override // android.view.InputEventReceiver
        public void onFocusEvent(boolean hasFocus) {
            ViewRootImpl.this.windowFocusChanged(hasFocus);
        }

        @Override // android.view.InputEventReceiver
        public void onTouchModeChanged(boolean inTouchMode) {
            ViewRootImpl.this.touchModeChanged(inTouchMode);
        }

        @Override // android.view.InputEventReceiver
        public void onPointerCaptureEvent(boolean pointerCaptureEnabled) {
            ViewRootImpl.this.dispatchPointerCaptureChanged(pointerCaptureEnabled);
        }

        @Override // android.view.InputEventReceiver
        public void onDragEvent(boolean isExiting, float x, float y) {
            DragEvent event = DragEvent.obtain(isExiting ? 6 : 2, x, y, 0.0f, 0.0f, null, null, null, null, null, false);
            ViewRootImpl.this.dispatchDragEvent(event);
        }

        @Override // android.view.InputEventReceiver
        public void dispose() {
            ViewRootImpl.this.unscheduleConsumeBatchedInput();
            super.dispose();
        }
    }

    final class InputMetricsListener implements HardwareRendererObserver.OnFrameMetricsAvailableListener {
        public long[] data = new long[23];

        InputMetricsListener() {
        }

        @Override // android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener
        public void onFrameMetricsAvailable(int dropCountSinceLastInvocation) {
            long[] jArr = this.data;
            int inputEventId = (int) jArr[4];
            if (inputEventId == 0) {
                return;
            }
            long presentTime = jArr[21];
            if (presentTime <= 0) {
                return;
            }
            long gpuCompletedTime = jArr[19];
            if (ViewRootImpl.this.mInputEventReceiver == null) {
                return;
            }
            if (gpuCompletedTime >= presentTime) {
                double discrepancyMs = (gpuCompletedTime - presentTime) * 1.0E-6d;
                long vsyncId = this.data[1];
                Log.m102w(ViewRootImpl.TAG, "Not reporting timeline because gpuCompletedTime is " + discrepancyMs + "ms ahead of presentTime. FRAME_TIMELINE_VSYNC_ID=" + vsyncId + ", INPUT_EVENT_ID=" + inputEventId);
                return;
            }
            ViewRootImpl.this.mInputEventReceiver.reportTimeline(inputEventId, gpuCompletedTime, presentTime);
        }
    }

    final class ConsumeBatchedInputRunnable implements Runnable {
        ConsumeBatchedInputRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewRootImpl.this.mConsumeBatchedInputScheduled = false;
            ViewRootImpl viewRootImpl = ViewRootImpl.this;
            if (viewRootImpl.doConsumeBatchedInput(viewRootImpl.mChoreographer.getFrameTimeNanos())) {
                ViewRootImpl.this.scheduleConsumeBatchedInput();
            }
        }
    }

    final class ConsumeBatchedInputImmediatelyRunnable implements Runnable {
        ConsumeBatchedInputImmediatelyRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewRootImpl.this.mConsumeBatchedInputImmediatelyScheduled = false;
            ViewRootImpl.this.doConsumeBatchedInput(-1L);
        }
    }

    final class InvalidateOnAnimationRunnable implements Runnable {
        private boolean mPosted;
        private View.AttachInfo.InvalidateInfo[] mTempViewRects;
        private View[] mTempViews;
        private final ArrayList<View> mViews = new ArrayList<>();
        private final ArrayList<View.AttachInfo.InvalidateInfo> mViewRects = new ArrayList<>();

        InvalidateOnAnimationRunnable() {
        }

        public void addView(View view) {
            synchronized (this) {
                this.mViews.add(view);
                postIfNeededLocked();
            }
            if (ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null) {
                ViewRootImpl.this.mAttachInfo.mThreadedRenderer.notifyCallbackPending();
            }
        }

        public void addViewRect(View.AttachInfo.InvalidateInfo info) {
            synchronized (this) {
                this.mViewRects.add(info);
                postIfNeededLocked();
            }
            if (ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null) {
                ViewRootImpl.this.mAttachInfo.mThreadedRenderer.notifyCallbackPending();
            }
        }

        public void removeView(View view) {
            synchronized (this) {
                this.mViews.remove(view);
                int i = this.mViewRects.size();
                while (true) {
                    int i2 = i - 1;
                    if (i <= 0) {
                        break;
                    }
                    View.AttachInfo.InvalidateInfo info = this.mViewRects.get(i2);
                    if (info.target == view) {
                        this.mViewRects.remove(i2);
                        info.recycle();
                    }
                    i = i2;
                }
                if (this.mPosted && this.mViews.isEmpty() && this.mViewRects.isEmpty()) {
                    ViewRootImpl.this.mChoreographer.removeCallbacks(1, this, null);
                    this.mPosted = false;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            int viewCount;
            int viewRectCount;
            synchronized (this) {
                this.mPosted = false;
                viewCount = this.mViews.size();
                if (viewCount != 0) {
                    ArrayList<View> arrayList = this.mViews;
                    View[] viewArr = this.mTempViews;
                    if (viewArr == null) {
                        viewArr = new View[viewCount];
                    }
                    this.mTempViews = (View[]) arrayList.toArray(viewArr);
                    this.mViews.clear();
                }
                viewRectCount = this.mViewRects.size();
                if (viewRectCount != 0) {
                    ArrayList<View.AttachInfo.InvalidateInfo> arrayList2 = this.mViewRects;
                    View.AttachInfo.InvalidateInfo[] invalidateInfoArr = this.mTempViewRects;
                    if (invalidateInfoArr == null) {
                        invalidateInfoArr = new View.AttachInfo.InvalidateInfo[viewRectCount];
                    }
                    this.mTempViewRects = (View.AttachInfo.InvalidateInfo[]) arrayList2.toArray(invalidateInfoArr);
                    this.mViewRects.clear();
                }
            }
            for (int i = 0; i < viewCount; i++) {
                this.mTempViews[i].invalidate();
                this.mTempViews[i] = null;
            }
            for (int i2 = 0; i2 < viewRectCount; i2++) {
                View.AttachInfo.InvalidateInfo info = this.mTempViewRects[i2];
                info.target.invalidate(info.left, info.top, info.right, info.bottom);
                info.recycle();
            }
        }

        private void postIfNeededLocked() {
            if (!this.mPosted) {
                ViewRootImpl.this.mChoreographer.postCallback(1, this, null);
                this.mPosted = true;
            }
        }
    }

    public void dispatchInvalidateDelayed(View view, long delayMilliseconds) {
        Message msg = this.mHandler.obtainMessage(1, view);
        this.mHandler.sendMessageDelayed(msg, delayMilliseconds);
    }

    public void dispatchInvalidateRectDelayed(View.AttachInfo.InvalidateInfo info, long delayMilliseconds) {
        Message msg = this.mHandler.obtainMessage(2, info);
        this.mHandler.sendMessageDelayed(msg, delayMilliseconds);
    }

    public void dispatchInvalidateOnAnimation(View view) {
        this.mInvalidateOnAnimationRunnable.addView(view);
    }

    public void dispatchInvalidateRectOnAnimation(View.AttachInfo.InvalidateInfo info) {
        this.mInvalidateOnAnimationRunnable.addViewRect(info);
    }

    public void cancelInvalidate(View view) {
        this.mHandler.removeMessages(1, view);
        this.mHandler.removeMessages(2, view);
        this.mInvalidateOnAnimationRunnable.removeView(view);
    }

    public void dispatchInputEvent(InputEvent event) {
        dispatchInputEvent(event, null);
    }

    public void dispatchInputEvent(InputEvent event, InputEventReceiver receiver) {
        SomeArgs args = SomeArgs.obtain();
        args.arg1 = event;
        args.arg2 = receiver;
        Message msg = this.mHandler.obtainMessage(7, args);
        msg.setAsynchronous(true);
        this.mHandler.sendMessage(msg);
    }

    public void synthesizeInputEvent(InputEvent event) {
        Message msg = this.mHandler.obtainMessage(24, event);
        msg.setAsynchronous(true);
        this.mHandler.sendMessage(msg);
    }

    public void dispatchKeyFromIme(KeyEvent event) {
        Message msg = this.mHandler.obtainMessage(11, event);
        msg.setAsynchronous(true);
        this.mHandler.sendMessage(msg);
    }

    public void dispatchKeyFromAutofill(KeyEvent event) {
        Message msg = this.mHandler.obtainMessage(12, event);
        msg.setAsynchronous(true);
        this.mHandler.sendMessage(msg);
    }

    public void dispatchUnhandledInputEvent(InputEvent event) {
        if (event instanceof MotionEvent) {
            event = MotionEvent.obtain((MotionEvent) event);
        }
        synthesizeInputEvent(event);
    }

    public void dispatchAppVisibility(boolean z) {
        this.mSemEarlyAppVisibilityChanged = true;
        this.mSemEarlyAppVisibility = z;
        Message obtainMessage = this.mHandler.obtainMessage(8);
        obtainMessage.arg1 = z ? 1 : 0;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void dispatchGetNewSurface() {
        Message msg = this.mHandler.obtainMessage(9);
        this.mHandler.sendMessage(msg);
    }

    public void windowFocusChanged(boolean hasFocus) {
        synchronized (this) {
            this.mWindowFocusChanged = true;
            this.mUpcomingWindowFocus = hasFocus;
        }
        Message msg = Message.obtain();
        msg.what = 6;
        this.mHandler.sendMessage(msg);
    }

    public void touchModeChanged(boolean inTouchMode) {
        synchronized (this) {
            this.mUpcomingInTouchMode = inTouchMode;
        }
        Message msg = Message.obtain();
        msg.what = 34;
        this.mHandler.sendMessage(msg);
    }

    public void dispatchWindowShown() {
        this.mHandler.sendEmptyMessage(25);
    }

    public void dispatchCloseSystemDialogs(String reason) {
        Message msg = Message.obtain();
        msg.what = 14;
        msg.obj = reason;
        this.mHandler.sendMessage(msg);
    }

    public void dispatchDragEvent(DragEvent event) {
        int what;
        if (event.getAction() == 2) {
            what = 16;
            this.mHandler.removeMessages(16);
        } else {
            what = 15;
        }
        Message msg = this.mHandler.obtainMessage(what, event);
        this.mHandler.sendMessage(msg);
    }

    public void dispatchDragEventUpdated(DragEvent event) {
        if (this.mAttachInfo.mDragToken != null) {
            Log.m98i(TAG, "dispatchDragEventUpdated() return.");
            return;
        }
        this.mHandler.removeMessages(16);
        this.mHandler.removeMessages(15);
        sendDispatchDragEvent(6, event);
        sendDispatchDragEvent(4, event);
        sendDispatchDragEvent(1, event);
        sendDispatchDragEvent(2, event);
    }

    private void sendDispatchDragEvent(int action, DragEvent event) {
        float x;
        float y;
        float offsetX;
        float offsetY;
        if (action == 1 || action == 2) {
            float x2 = event.getX();
            float y2 = event.getY();
            float offsetX2 = event.getOffsetX();
            x = x2;
            y = y2;
            offsetX = offsetX2;
            offsetY = event.getOffsetY();
        } else {
            x = 0.0f;
            y = 0.0f;
            offsetX = 0.0f;
            offsetY = 0.0f;
        }
        SurfaceControl dragSurface = 6 == action ? null : event.getDragSurface();
        DragEvent eventToRestart = DragEvent.obtain(action, x, y, offsetX, offsetY, event.mLocalState, event.getClipDescription(), event.mClipData, dragSurface, event.mDragAndDropPermissions, event.mDragResult);
        ViewRootHandler viewRootHandler = this.mHandler;
        viewRootHandler.sendMessage(viewRootHandler.obtainMessage(15, eventToRestart));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void windowFocusInTaskChanged(boolean hasFocus) {
        synchronized (this) {
            this.mWindowFocusInTaskChanged = true;
            if (CoreRune.SAFE_DEBUG) {
                Log.m98i(this.mTag, "windowFocusInTaskChanged " + this.mUpcomingWindowFocusInTask + " to " + hasFocus + ", windowFocus=" + this.mUpcomingWindowFocus);
            }
            this.mUpcomingWindowFocusInTask = hasFocus;
        }
        Message msg = Message.obtain();
        msg.what = 105;
        this.mHandler.sendMessage(msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWindowFocusInTaskChanged() {
        synchronized (this) {
            if (this.mWindowFocusInTaskChanged) {
                this.mWindowFocusInTaskChanged = false;
                boolean hasWindowFocusInTask = this.mUpcomingWindowFocusInTask;
                View view = this.mView;
                if (view instanceof DecorView) {
                    ((DecorView) view).onWindowFocusInTaskChanged(hasWindowFocusInTask);
                }
            }
        }
    }

    public void updatePointerIcon(float x, float y) {
        this.mHandler.removeMessages(27);
        long now = SystemClock.uptimeMillis();
        MotionEvent event = MotionEvent.obtain(0L, now, 7, x, y, 0);
        Message msg = this.mHandler.obtainMessage(27, event);
        this.mHandler.sendMessage(msg);
    }

    public void dispatchCheckFocus() {
        if (!this.mHandler.hasMessages(13)) {
            this.mHandler.sendEmptyMessage(13);
        }
    }

    public void dispatchRequestKeyboardShortcuts(IResultReceiver receiver, int deviceId) {
        this.mHandler.obtainMessage(26, deviceId, 0, receiver).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPointerCaptureChanged(boolean z) {
        this.mHandler.removeMessages(28);
        Message obtainMessage = this.mHandler.obtainMessage(28);
        obtainMessage.arg1 = z ? 1 : 0;
        this.mHandler.sendMessage(obtainMessage);
    }

    private void postSendWindowContentChangedCallback(View source, int changeType) {
        if (this.mSendWindowContentChangedAccessibilityEvent == null) {
            this.mSendWindowContentChangedAccessibilityEvent = new SendWindowContentChangedAccessibilityEvent();
        }
        this.mSendWindowContentChangedAccessibilityEvent.runOrPost(source, changeType);
    }

    private void removeSendWindowContentChangedCallback() {
        SendWindowContentChangedAccessibilityEvent sendWindowContentChangedAccessibilityEvent = this.mSendWindowContentChangedAccessibilityEvent;
        if (sendWindowContentChangedAccessibilityEvent != null) {
            this.mHandler.removeCallbacks(sendWindowContentChangedAccessibilityEvent);
        }
    }

    public int getDirectAccessibilityConnectionId() {
        return this.mAccessibilityInteractionConnectionManager.ensureDirectConnection();
    }

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(View originalView) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(View originalView, float x, float y) {
        return false;
    }

    @Override // android.view.ViewParent
    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.ViewParent
    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback, int type) {
        return null;
    }

    @Override // android.view.ViewParent
    public void createContextMenu(ContextMenu menu) {
    }

    @Override // android.view.ViewParent
    public void childDrawableStateChanged(View child) {
    }

    @Override // android.view.ViewParent
    public boolean requestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        AccessibilityNodeProvider provider;
        SendWindowContentChangedAccessibilityEvent sendWindowContentChangedAccessibilityEvent;
        if (this.mView == null || this.mStopped || this.mPausedForTransition) {
            return false;
        }
        if (event.getEventType() != 2048 && (sendWindowContentChangedAccessibilityEvent = this.mSendWindowContentChangedAccessibilityEvent) != null && sendWindowContentChangedAccessibilityEvent.mSource != null) {
            this.mSendWindowContentChangedAccessibilityEvent.removeCallbacksAndRun();
        }
        int eventType = event.getEventType();
        View source = getSourceForAccessibilityEvent(event);
        switch (eventType) {
            case 2048:
                handleWindowContentChangedEvent(event);
                break;
            case 32768:
                if (source != null && (provider = source.getAccessibilityNodeProvider()) != null) {
                    int virtualNodeId = AccessibilityNodeInfo.getVirtualDescendantId(event.getSourceNodeId());
                    AccessibilityNodeInfo node = provider.createAccessibilityNodeInfo(virtualNodeId);
                    setAccessibilityFocus(source, node);
                    break;
                }
                break;
            case 65536:
                if (source != null && source.getAccessibilityNodeProvider() != null) {
                    setAccessibilityFocus(null, null);
                    break;
                }
                break;
        }
        this.mAccessibilityManager.sendAccessibilityEvent(event);
        return true;
    }

    private View getSourceForAccessibilityEvent(AccessibilityEvent event) {
        long sourceNodeId = event.getSourceNodeId();
        int accessibilityViewId = AccessibilityNodeInfo.getAccessibilityViewId(sourceNodeId);
        return AccessibilityNodeIdManager.getInstance().findView(accessibilityViewId);
    }

    private void handleWindowContentChangedEvent(AccessibilityEvent event) {
        View focusedHost = this.mAccessibilityFocusedHost;
        if (focusedHost == null || this.mAccessibilityFocusedVirtualView == null) {
            return;
        }
        AccessibilityNodeProvider provider = focusedHost.getAccessibilityNodeProvider();
        if (provider == null) {
            this.mAccessibilityFocusedHost = null;
            this.mAccessibilityFocusedVirtualView = null;
            focusedHost.clearAccessibilityFocusNoCallbacks(0);
            return;
        }
        int changes = event.getContentChangeTypes();
        if ((changes & 1) == 0 && changes != 0) {
            return;
        }
        long eventSourceNodeId = event.getSourceNodeId();
        int changedViewId = AccessibilityNodeInfo.getAccessibilityViewId(eventSourceNodeId);
        boolean hostInSubtree = false;
        View root = this.mAccessibilityFocusedHost;
        while (root != null && !hostInSubtree) {
            if (changedViewId == root.getAccessibilityViewId()) {
                hostInSubtree = true;
            } else {
                Object parent = root.getParent();
                if (parent instanceof View) {
                    root = (View) parent;
                } else {
                    root = null;
                }
            }
        }
        if (!hostInSubtree) {
            return;
        }
        long focusedSourceNodeId = this.mAccessibilityFocusedVirtualView.getSourceNodeId();
        int focusedChildId = AccessibilityNodeInfo.getVirtualDescendantId(focusedSourceNodeId);
        Rect oldBounds = this.mTempRect;
        this.mAccessibilityFocusedVirtualView.getBoundsInScreen(oldBounds);
        AccessibilityNodeInfo createAccessibilityNodeInfo = provider.createAccessibilityNodeInfo(focusedChildId);
        this.mAccessibilityFocusedVirtualView = createAccessibilityNodeInfo;
        if (createAccessibilityNodeInfo == null) {
            this.mAccessibilityFocusedHost = null;
            focusedHost.clearAccessibilityFocusNoCallbacks(0);
            provider.performAction(focusedChildId, AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS.getId(), null);
            invalidateRectOnScreen(oldBounds);
            return;
        }
        Rect newBounds = createAccessibilityNodeInfo.getBoundsInScreen();
        if (!oldBounds.equals(newBounds)) {
            oldBounds.union(newBounds);
            invalidateRectOnScreen(oldBounds);
        }
    }

    @Override // android.view.ViewParent
    public void notifySubtreeAccessibilityStateChanged(View child, View source, int changeType) {
        postSendWindowContentChangedCallback((View) Preconditions.checkNotNull(source), changeType);
    }

    @Override // android.view.ViewParent
    public boolean canResolveLayoutDirection() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean isLayoutDirectionResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public int getLayoutDirection() {
        return 0;
    }

    @Override // android.view.ViewParent
    public boolean canResolveTextDirection() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean isTextDirectionResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public int getTextDirection() {
        return 1;
    }

    @Override // android.view.ViewParent
    public boolean canResolveTextAlignment() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean isTextAlignmentResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public int getTextAlignment() {
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getCommonPredecessor(View first, View second) {
        if (this.mTempHashSet == null) {
            this.mTempHashSet = new HashSet<>();
        }
        HashSet<View> seen = this.mTempHashSet;
        seen.clear();
        View firstCurrent = first;
        while (firstCurrent != null) {
            seen.add(firstCurrent);
            Object obj = firstCurrent.mParent;
            if (obj instanceof View) {
                firstCurrent = (View) obj;
            } else {
                firstCurrent = null;
            }
        }
        View secondCurrent = second;
        while (secondCurrent != null) {
            if (seen.contains(secondCurrent)) {
                seen.clear();
                return secondCurrent;
            }
            Object obj2 = secondCurrent.mParent;
            if (obj2 instanceof View) {
                secondCurrent = (View) obj2;
            } else {
                secondCurrent = null;
            }
        }
        seen.clear();
        return null;
    }

    void checkThread() {
        Thread current = Thread.currentThread();
        if (this.mThread != current) {
            throw new CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views. Expected: " + this.mThread.getName() + " Calling: " + current.getName());
        }
    }

    @Override // android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    @Override // android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
        if (rectangle == null) {
            return scrollToRectOrFocus(null, immediate);
        }
        rectangle.offset(child.getLeft() - child.getScrollX(), child.getTop() - child.getScrollY());
        boolean scrolled = scrollToRectOrFocus(rectangle, immediate);
        this.mTempRect.set(rectangle);
        this.mTempRect.offset(0, -this.mCurScrollY);
        this.mTempRect.offset(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
        CompatTranslator translator = getCompatTranslator();
        if (translator != null) {
            translator.translateToScreen(this.mTempRect);
        }
        try {
            this.mWindowSession.onRectangleOnScreenRequested(this.mWindow, this.mTempRect);
        } catch (RemoteException e) {
        }
        return scrolled;
    }

    @Override // android.view.ViewParent
    public void childHasTransientStateChanged(View child, boolean hasTransientState) {
    }

    @Override // android.view.ViewParent
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return false;
    }

    @Override // android.view.ViewParent
    public void onStopNestedScroll(View target) {
    }

    @Override // android.view.ViewParent
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
    }

    @Override // android.view.ViewParent
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override // android.view.ViewParent
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
    }

    @Override // android.view.ViewParent
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean onNestedPrePerformAccessibilityAction(View target, int action, Bundle args) {
        return false;
    }

    public void addScrollCaptureCallback(ScrollCaptureCallback callback) {
        if (this.mRootScrollCaptureCallbacks == null) {
            this.mRootScrollCaptureCallbacks = new HashSet<>();
        }
        this.mRootScrollCaptureCallbacks.add(callback);
    }

    public void removeScrollCaptureCallback(ScrollCaptureCallback callback) {
        HashSet<ScrollCaptureCallback> hashSet = this.mRootScrollCaptureCallbacks;
        if (hashSet != null) {
            hashSet.remove(callback);
            if (this.mRootScrollCaptureCallbacks.isEmpty()) {
                this.mRootScrollCaptureCallbacks = null;
            }
        }
    }

    public void dispatchScrollCaptureRequest(IScrollCaptureResponseListener listener) {
        this.mHandler.obtainMessage(33, listener).sendToTarget();
    }

    private void collectRootScrollCaptureTargets(ScrollCaptureSearchResults results) {
        HashSet<ScrollCaptureCallback> hashSet = this.mRootScrollCaptureCallbacks;
        if (hashSet == null) {
            return;
        }
        Iterator<ScrollCaptureCallback> it = hashSet.iterator();
        while (it.hasNext()) {
            ScrollCaptureCallback cb = it.next();
            Point offset = new Point(this.mView.getLeft(), this.mView.getTop());
            Rect rect = new Rect(0, 0, this.mView.getWidth(), this.mView.getHeight());
            results.addTarget(new ScrollCaptureTarget(this.mView, rect, offset, cb));
        }
    }

    public void setScrollCaptureRequestTimeout(int timeMillis) {
        this.mScrollCaptureRequestTimeout = timeMillis;
    }

    public long getScrollCaptureRequestTimeout() {
        return this.mScrollCaptureRequestTimeout;
    }

    public void handleScrollCaptureRequest(final IScrollCaptureResponseListener listener) {
        final ScrollCaptureSearchResults results = new ScrollCaptureSearchResults(this.mContext.getMainExecutor());
        collectRootScrollCaptureTargets(results);
        View rootView = getView();
        if (rootView != null) {
            Point point = new Point();
            Rect rect = new Rect(0, 0, rootView.getWidth(), rootView.getHeight());
            getChildVisibleRect(rootView, rect, point);
            Objects.requireNonNull(results);
            rootView.dispatchScrollCaptureSearch(rect, point, new Consumer() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ScrollCaptureSearchResults.this.addTarget((ScrollCaptureTarget) obj);
                }
            });
        }
        Runnable onComplete = new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                ViewRootImpl.this.lambda$handleScrollCaptureRequest$10(listener, results);
            }
        };
        results.setOnCompleteListener(onComplete);
        if (!results.isComplete()) {
            ViewRootHandler viewRootHandler = this.mHandler;
            Objects.requireNonNull(results);
            viewRootHandler.postDelayed(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    ScrollCaptureSearchResults.this.finish();
                }
            }, getScrollCaptureRequestTimeout());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dispatchScrollCaptureSearchResponse, reason: merged with bridge method [inline-methods] */
    public void lambda$handleScrollCaptureRequest$10(IScrollCaptureResponseListener listener, ScrollCaptureSearchResults results) {
        ScrollCaptureTarget selectedTarget = results.getTopResult();
        ScrollCaptureResponse.Builder response = new ScrollCaptureResponse.Builder();
        response.setWindowTitle(getTitle().toString());
        response.setPackageName(this.mContext.getPackageName());
        StringWriter writer = new StringWriter();
        IndentingPrintWriter pw = new IndentingPrintWriter(writer);
        results.dump(pw);
        pw.flush();
        response.addMessage(writer.toString());
        if (selectedTarget == null) {
            response.setDescription("No scrollable targets found in window");
            try {
                listener.onScrollCaptureResponse(response.build());
                return;
            } catch (RemoteException e) {
                Log.m97e(TAG, "Failed to send scroll capture search result", e);
                return;
            }
        }
        response.setDescription("Connected");
        Rect boundsInWindow = new Rect();
        View containingView = selectedTarget.getContainingView();
        containingView.getLocationInWindow(this.mAttachInfo.mTmpLocation);
        boundsInWindow.set(selectedTarget.getScrollBounds());
        boundsInWindow.offset(this.mAttachInfo.mTmpLocation[0], this.mAttachInfo.mTmpLocation[1]);
        response.setBoundsInWindow(boundsInWindow);
        Rect boundsOnScreen = new Rect();
        this.mView.getLocationOnScreen(this.mAttachInfo.mTmpLocation);
        boundsOnScreen.set(0, 0, this.mView.getWidth(), this.mView.getHeight());
        boundsOnScreen.offset(this.mAttachInfo.mTmpLocation[0], this.mAttachInfo.mTmpLocation[1]);
        response.setWindowBounds(boundsOnScreen);
        ScrollCaptureConnection connection = new ScrollCaptureConnection(this.mView.getContext().getMainExecutor(), selectedTarget);
        response.setConnection(connection);
        try {
            listener.onScrollCaptureResponse(response.build());
        } catch (RemoteException e2) {
            if (DEBUG_SCROLL_CAPTURE) {
                Log.m103w(TAG, "Failed to send scroll capture search response.", e2);
            }
            connection.close();
        }
    }

    private void reportNextDraw(String reason) {
        if (DEBUG_BLAST) {
            Log.m94d(this.mTag, "reportNextDraw " + Debug.getCallers(5));
        }
        this.mReportNextDraw = true;
        this.mLastReportNextDrawReason = reason;
    }

    public void setReportNextDraw(boolean syncBuffer, String reason) {
        if (syncBuffer) {
            Log.m98i(this.mTag, "setReportNextDraw syncBuffer=" + syncBuffer + ", reason=" + reason + ", caller=" + Debug.getCallers(5));
        }
        this.mSyncBuffer = syncBuffer;
        reportNextDraw(reason);
        invalidate();
    }

    void changeCanvasOpacity(boolean opaque) {
        Log.m94d(this.mTag, "changeCanvasOpacity: opaque=" + opaque);
        boolean opaque2 = opaque & ((this.mView.mPrivateFlags & 512) == 0);
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.setOpaque(opaque2);
        }
    }

    public boolean dispatchUnhandledKeyEvent(KeyEvent event) {
        return this.mUnhandledKeyManager.dispatch(this.mView, event);
    }

    class TakenSurfaceHolder extends BaseSurfaceHolder {
        TakenSurfaceHolder() {
        }

        @Override // com.android.internal.view.BaseSurfaceHolder
        public boolean onAllowLockCanvas() {
            return ViewRootImpl.this.mDrawingAllowed;
        }

        @Override // com.android.internal.view.BaseSurfaceHolder
        public void onRelayoutContainer() {
        }

        @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
        public void setFormat(int format) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceFormat(format);
        }

        @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
        public void setType(int type) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceType(type);
        }

        @Override // com.android.internal.view.BaseSurfaceHolder
        public void onUpdateSurface() {
            throw new IllegalStateException("Shouldn't be here");
        }

        @Override // android.view.SurfaceHolder
        public boolean isCreating() {
            return ViewRootImpl.this.mIsCreating;
        }

        @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
        public void setFixedSize(int width, int height) {
            throw new UnsupportedOperationException("Currently only support sizing from layout");
        }

        @Override // android.view.SurfaceHolder
        public void setKeepScreenOn(boolean screenOn) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceKeepScreenOn(screenOn);
        }
    }

    /* renamed from: android.view.ViewRootImpl$W */
    static class BinderC3744W extends IWindow.Stub {
        private final WeakReference<ViewRootImpl> mViewAncestor;
        private final IWindowSession mWindowSession;

        BinderC3744W(ViewRootImpl viewAncestor) {
            this.mViewAncestor = new WeakReference<>(viewAncestor);
            this.mWindowSession = viewAncestor.mWindowSession;
        }

        @Override // android.view.IWindow
        public void resized(ClientWindowFrames frames, boolean reportDraw, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean forceLayout, boolean alwaysConsumeSystemBars, int displayId, int syncSeqId, boolean dragResizing) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchResized(frames, reportDraw, mergedConfiguration, insetsState, forceLayout, alwaysConsumeSystemBars, displayId, syncSeqId, dragResizing);
            }
        }

        @Override // android.view.IWindow
        public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] activeControls) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchInsetsControlChanged(insetsState, activeControls);
            }
        }

        @Override // android.view.IWindow
        public void showInsets(int types, boolean fromIme, ImeTracker.Token statsToken) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (fromIme) {
                ImeTracing.getInstance().triggerClientDump("ViewRootImpl.W#showInsets", viewAncestor.getInsetsController().getHost().getInputMethodManager(), null);
            }
            if (viewAncestor != null) {
                ImeTracker.forLogging().onProgress(statsToken, 28);
                viewAncestor.showInsets(types, fromIme, statsToken);
            } else {
                ImeTracker.forLogging().onFailed(statsToken, 28);
            }
        }

        @Override // android.view.IWindow
        public void hideInsets(int types, boolean fromIme, ImeTracker.Token statsToken) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (fromIme) {
                ImeTracing.getInstance().triggerClientDump("ViewRootImpl.W#hideInsets", viewAncestor.getInsetsController().getHost().getInputMethodManager(), null);
            }
            if (viewAncestor != null) {
                ImeTracker.forLogging().onProgress(statsToken, 29);
                viewAncestor.hideInsets(types, fromIme, statsToken);
            } else {
                ImeTracker.forLogging().onFailed(statsToken, 29);
            }
        }

        @Override // android.view.IWindow
        public void moved(int newX, int newY) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchMoved(newX, newY);
            }
        }

        @Override // android.view.IWindow
        public void dispatchAppVisibility(boolean visible) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchAppVisibility(visible);
            }
        }

        @Override // android.view.IWindow
        public void dispatchGetNewSurface() {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchGetNewSurface();
            }
        }

        private static int checkCallingPermission(String permission) {
            try {
                return ActivityManager.getService().checkPermission(permission, Binder.getCallingPid(), Binder.getCallingUid());
            } catch (RemoteException e) {
                return -1;
            }
        }

        @Override // android.view.IWindow
        public void executeCommand(String command, String parameters, ParcelFileDescriptor out) {
            View view;
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor == null || (view = viewAncestor.mView) == null) {
                return;
            }
            if (checkCallingPermission(Manifest.permission.DUMP) != 0) {
                throw new SecurityException("Insufficient permissions to invoke executeCommand() from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            }
            OutputStream clientStream = null;
            try {
                try {
                    try {
                        clientStream = new ParcelFileDescriptor.AutoCloseOutputStream(out);
                        ViewDebug.dispatchCommand(view, command, parameters, clientStream);
                        clientStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (clientStream == null) {
                        } else {
                            clientStream.close();
                        }
                    }
                } catch (Throwable th) {
                    if (clientStream != null) {
                        try {
                            clientStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }

        @Override // android.view.IWindow
        public void closeSystemDialogs(String reason) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchCloseSystemDialogs(reason);
            }
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperOffsets(float x, float y, float xStep, float yStep, float zoom, boolean sync) {
            if (sync) {
                try {
                    this.mWindowSession.wallpaperOffsetsComplete(asBinder());
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras, boolean sync) {
            if (sync) {
                try {
                    this.mWindowSession.wallpaperCommandComplete(asBinder(), null);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.IWindow
        public void dispatchDragEvent(DragEvent event) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchDragEvent(event);
            }
        }

        @Override // android.view.IWindow
        public void updatePointerIcon(float x, float y) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.updatePointerIcon(x, y);
            }
        }

        @Override // android.view.IWindow
        public void dispatchWindowShown() {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchWindowShown();
            }
        }

        @Override // android.view.IWindow
        public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchRequestKeyboardShortcuts(receiver, deviceId);
            }
        }

        @Override // android.view.IWindow
        public void requestScrollCapture(IScrollCaptureResponseListener listener) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchScrollCaptureRequest(listener);
            }
        }

        @Override // android.view.IWindow
        public void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo request) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchSmartClipRemoteRequest(request);
            }
        }

        @Override // android.view.IWindow
        public void dispatchSPenGestureEvent(InputEvent[] events) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor == null) {
                return;
            }
            viewAncestor.dispatchSPenGestureEvent(events);
        }

        @Override // android.view.IWindow
        public void dispatchLetterboxDirectionChanged(int direction) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchLetterboxDirectionChanged(direction);
            }
        }

        @Override // android.view.IWindow
        public void dispatchDragEventUpdated(DragEvent event) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchDragEventUpdated(event);
            }
        }

        @Override // android.view.IWindow
        public void windowFocusInTaskChanged(boolean hasFocus) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.windowFocusInTaskChanged(hasFocus);
            }
        }

        @Override // android.view.IWindow
        public void invalidateForScreenShot(boolean forceMode) {
            final ViewRootImpl viewAncestor = this.mViewAncestor.get();
            final String tag = viewAncestor.getTag() != null ? viewAncestor.getTag() : ViewRootImpl.TAG;
            Log.m98i(tag, "invalidateForScreenShot forceMode=" + forceMode);
            viewAncestor.mForceModeInScreenshot = forceMode;
            if (forceMode) {
                viewAncestor.mAttachInfo.mThreadedRenderer.setColorMode(1);
                viewAncestor.mAttachInfo.mThreadedRenderer.setTargetHdrSdrRatio(1.0f);
            } else {
                viewAncestor.mAttachInfo.mThreadedRenderer.setColorMode(2);
                viewAncestor.mAttachInfo.mThreadedRenderer.setTargetHdrSdrRatio(viewAncestor.mRenderHdrSdrRatio);
            }
            if (viewAncestor.mInvalidateForScreenshotRunnable == null) {
                viewAncestor.mInvalidateForScreenshotRunnable = new Runnable() { // from class: android.view.ViewRootImpl.W.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.m98i(tag, "invalidateForScreenShot post vri invalidate");
                        viewAncestor.invalidate();
                    }
                };
            }
            viewAncestor.mAttachInfo.mHandler.post(viewAncestor.mInvalidateForScreenshotRunnable);
        }
    }

    public static final class CalledFromWrongThreadException extends AndroidRuntimeException {
        public CalledFromWrongThreadException(String msg) {
            super(msg);
        }
    }

    static HandlerActionQueue getRunQueue() {
        ThreadLocal<HandlerActionQueue> threadLocal = sRunQueues;
        HandlerActionQueue rq = threadLocal.get();
        if (rq != null) {
            return rq;
        }
        HandlerActionQueue rq2 = new HandlerActionQueue();
        threadLocal.set(rq2);
        return rq2;
    }

    private void startDragResizing(Rect initialBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets) {
        if (!this.mDragResizing) {
            this.mDragResizing = true;
            if (this.mUseMTRenderer) {
                for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                    this.mWindowCallbacks.get(i).onWindowDragResizeStart(initialBounds, fullscreen, systemInsets, stableInsets);
                }
            }
            this.mFullRedrawNeeded = true;
        }
    }

    private void endDragResizing() {
        if (this.mDragResizing) {
            this.mDragResizing = false;
            if (this.mUseMTRenderer) {
                for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                    this.mWindowCallbacks.get(i).onWindowDragResizeEnd();
                }
            }
            this.mFullRedrawNeeded = true;
        }
    }

    private boolean updateContentDrawBounds() {
        boolean updated = false;
        if (this.mUseMTRenderer) {
            for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                updated |= this.mWindowCallbacks.get(i).onContentDrawn(this.mWindowAttributes.surfaceInsets.left, this.mWindowAttributes.surfaceInsets.top, this.mWidth, this.mHeight);
            }
        }
        return updated | (this.mDragResizing && this.mReportNextDraw);
    }

    private void requestDrawWindow() {
        if (!this.mUseMTRenderer) {
            return;
        }
        if (this.mReportNextDraw) {
            this.mWindowDrawCountDown = new CountDownLatch(this.mWindowCallbacks.size());
        }
        for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
            this.mWindowCallbacks.get(i).onRequestDraw(this.mReportNextDraw);
        }
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    public IBinder getInputToken() {
        WindowInputEventReceiver windowInputEventReceiver = this.mInputEventReceiver;
        if (windowInputEventReceiver == null) {
            return null;
        }
        return windowInputEventReceiver.getToken();
    }

    public IBinder getWindowToken() {
        return this.mAttachInfo.mWindowToken;
    }

    final class AccessibilityInteractionConnectionManager implements AccessibilityManager.AccessibilityStateChangeListener {
        private int mDirectConnectionId = -1;

        AccessibilityInteractionConnectionManager() {
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
        public void onAccessibilityStateChanged(boolean enabled) {
            if (enabled) {
                ensureConnection();
                ViewRootImpl.this.setAccessibilityWindowAttributesIfNeeded();
                if (ViewRootImpl.this.mAttachInfo.mHasWindowFocus && ViewRootImpl.this.mView != null) {
                    ViewRootImpl.this.mView.sendAccessibilityEvent(32);
                    View focusedView = ViewRootImpl.this.mView.findFocus();
                    if (focusedView != null && focusedView != ViewRootImpl.this.mView) {
                        focusedView.sendAccessibilityEvent(8);
                    }
                }
                if (ViewRootImpl.this.mAttachInfo.mLeashedParentToken != null) {
                    ViewRootImpl.this.mAccessibilityManager.associateEmbeddedHierarchy(ViewRootImpl.this.mAttachInfo.mLeashedParentToken, ViewRootImpl.this.mLeashToken);
                    return;
                }
                return;
            }
            ensureNoConnection();
            ViewRootImpl.this.mHandler.obtainMessage(21).sendToTarget();
        }

        public void ensureConnection() {
            boolean registered = ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1;
            if (!registered) {
                ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = ViewRootImpl.this.mAccessibilityManager.addAccessibilityInteractionConnection(ViewRootImpl.this.mWindow, ViewRootImpl.this.mLeashToken, ViewRootImpl.this.mContext.getPackageName(), new AccessibilityInteractionConnection(ViewRootImpl.this));
            }
        }

        public void ensureNoConnection() {
            boolean registered = ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1;
            if (registered) {
                ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = -1;
                ViewRootImpl.this.mAccessibilityWindowAttributes = null;
                ViewRootImpl.this.mAccessibilityManager.removeAccessibilityInteractionConnection(ViewRootImpl.this.mWindow);
            }
        }

        public int ensureDirectConnection() {
            if (this.mDirectConnectionId == -1) {
                this.mDirectConnectionId = AccessibilityInteractionClient.addDirectConnection(new AccessibilityInteractionConnection(ViewRootImpl.this), ViewRootImpl.this.mAccessibilityManager);
                ViewRootImpl.this.mAccessibilityManager.notifyAccessibilityStateChanged();
            }
            return this.mDirectConnectionId;
        }

        public void ensureNoDirectConnection() {
            int i = this.mDirectConnectionId;
            if (i != -1) {
                AccessibilityInteractionClient.removeConnection(i);
                this.mDirectConnectionId = -1;
                ViewRootImpl.this.mAccessibilityManager.notifyAccessibilityStateChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRelayoutForHCT(boolean isNotFromHandler) {
        if (this.mThread == Thread.currentThread()) {
            destroyHardwareResources();
            resetSoftwareCaches(this.mView);
            invalidate();
            requestLayout();
            View view = this.mView;
            if (view != null) {
                forceLayout(view);
                return;
            }
            return;
        }
        if (isNotFromHandler) {
            this.mHCTRelayoutHandler.sendEmptyMessage(1);
        } else {
            Log.m94d(TAG, "Recursion detected");
        }
    }

    private final class HCTRelayoutHandler extends Handler {
        public static final int MSG_NEED_TO_DO_RELAYOUT = 1;

        public HCTRelayoutHandler() {
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    ViewRootImpl.this.doRelayoutForHCT(false);
                    break;
            }
        }
    }

    final class HighContrastTextManager implements AccessibilityManager.HighTextContrastChangeListener {
        HighContrastTextManager() {
            ThreadedRenderer.setHighContrastText(ViewRootImpl.this.mAccessibilityManager.isHighTextContrastEnabled());
        }

        @Override // android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener
        public void onHighTextContrastStateChanged(boolean enabled) {
            ThreadedRenderer.setHighContrastText(enabled);
            ViewRootImpl.this.doRelayoutForHCT(true);
        }
    }

    static final class AccessibilityInteractionConnection extends IAccessibilityInteractionConnection.Stub {
        private final WeakReference<ViewRootImpl> mViewRootImpl;

        AccessibilityInteractionConnection(ViewRootImpl viewRootImpl) {
            this.mViewRootImpl = new WeakReference<>(viewRootImpl);
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfoByAccessibilityId(long accessibilityNodeId, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec, float[] matrix, Bundle args) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfoByAccessibilityIdClientThread(accessibilityNodeId, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec, matrix, args);
            } else {
                try {
                    callback.setFindAccessibilityNodeInfosResult(null, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void performAccessibilityAction(long accessibilityNodeId, int action, Bundle arguments, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().performAccessibilityActionClientThread(accessibilityNodeId, action, arguments, interactionId, callback, flags, interrogatingPid, interrogatingTid);
            } else {
                try {
                    callback.setPerformAccessibilityActionResult(false, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByViewId(long accessibilityNodeId, String viewId, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec, float[] matrix) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByViewIdClientThread(accessibilityNodeId, viewId, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec, matrix);
            } else {
                try {
                    callback.setFindAccessibilityNodeInfoResult(null, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByText(long accessibilityNodeId, String text, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec, float[] matrix) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByTextClientThread(accessibilityNodeId, text, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec, matrix);
            } else {
                try {
                    callback.setFindAccessibilityNodeInfosResult(null, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findFocus(long accessibilityNodeId, int focusType, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec, float[] matrix) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findFocusClientThread(accessibilityNodeId, focusType, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec, matrix);
            } else {
                try {
                    callback.setFindAccessibilityNodeInfoResult(null, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void focusSearch(long accessibilityNodeId, int direction, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec, float[] matrix) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().focusSearchClientThread(accessibilityNodeId, direction, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec, matrix);
            } else {
                try {
                    callback.setFindAccessibilityNodeInfoResult(null, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void clearAccessibilityFocus() {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().clearAccessibilityFocusClientThread();
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void notifyOutsideTouch() {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().notifyOutsideTouchClientThread();
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void takeScreenshotOfWindow(int interactionId, ScreenCapture.ScreenCaptureListener listener, IAccessibilityInteractionConnectionCallback callback) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().takeScreenshotOfWindowClientThread(interactionId, listener, callback);
            } else {
                try {
                    callback.sendTakeScreenshotOfWindowError(1, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void attachAccessibilityOverlayToWindow(SurfaceControl sc) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null) {
                viewRootImpl.getAccessibilityInteractionController().attachAccessibilityOverlayToWindowClientThread(sc);
            }
        }
    }

    public IAccessibilityEmbeddedConnection getAccessibilityEmbeddedConnection() {
        if (this.mAccessibilityEmbeddedConnection == null) {
            this.mAccessibilityEmbeddedConnection = new AccessibilityEmbeddedConnection(this);
        }
        return this.mAccessibilityEmbeddedConnection;
    }

    private class SendWindowContentChangedAccessibilityEvent implements Runnable {
        public OptionalInt mAction;
        private int mChangeTypes;
        public long mLastEventTimeMillis;
        public StackTraceElement[] mOrigin;
        public View mSource;

        private SendWindowContentChangedAccessibilityEvent() {
            this.mChangeTypes = 0;
            this.mAction = OptionalInt.empty();
        }

        @Override // java.lang.Runnable
        public void run() {
            View source = this.mSource;
            this.mSource = null;
            if (source == null) {
                Log.m96e(ViewRootImpl.TAG, "Accessibility content change has no source");
                return;
            }
            if (AccessibilityManager.getInstance(ViewRootImpl.this.mContext).isEnabled()) {
                this.mLastEventTimeMillis = SystemClock.uptimeMillis();
                AccessibilityEvent event = AccessibilityEvent.obtain();
                event.setEventType(2048);
                event.setContentChangeTypes(this.mChangeTypes);
                if (this.mAction.isPresent()) {
                    event.setAction(this.mAction.getAsInt());
                }
                source.sendAccessibilityEventUnchecked(event);
            } else {
                this.mLastEventTimeMillis = 0L;
            }
            source.resetSubtreeAccessibilityStateChanged();
            this.mChangeTypes = 0;
            this.mAction = OptionalInt.empty();
        }

        public void runOrPost(View source, int changeType) {
            if (ViewRootImpl.this.mHandler.getLooper() != Looper.myLooper()) {
                CalledFromWrongThreadException e = new CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views.");
                Log.m97e(ViewRootImpl.TAG, "Accessibility content change on non-UI thread. Future Android versions will throw an exception.", e);
                ViewRootImpl.this.mHandler.removeCallbacks(this);
                if (this.mSource != null) {
                    run();
                }
            }
            View view = this.mSource;
            if (view != null) {
                View predecessor = ViewRootImpl.this.getCommonPredecessor(view, source);
                if (predecessor != null) {
                    predecessor = predecessor.getSelfOrParentImportantForA11y();
                }
                this.mSource = predecessor != null ? predecessor : source;
                this.mChangeTypes |= changeType;
                int performingAction = ViewRootImpl.this.mAccessibilityManager.getPerformingAction();
                if (performingAction != 0) {
                    if (this.mAction.isEmpty()) {
                        this.mAction = OptionalInt.of(performingAction);
                        return;
                    } else {
                        if (this.mAction.getAsInt() != performingAction) {
                            this.mAction = OptionalInt.of(0);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            this.mSource = source;
            this.mChangeTypes = changeType;
            if (ViewRootImpl.this.mAccessibilityManager.getPerformingAction() != 0) {
                this.mAction = OptionalInt.of(ViewRootImpl.this.mAccessibilityManager.getPerformingAction());
            }
            long timeSinceLastMillis = SystemClock.uptimeMillis() - this.mLastEventTimeMillis;
            long minEventIntevalMillis = ViewConfiguration.getSendRecurringAccessibilityEventsInterval();
            if (timeSinceLastMillis >= minEventIntevalMillis) {
                removeCallbacksAndRun();
            } else {
                ViewRootImpl.this.mHandler.postDelayed(this, minEventIntevalMillis - timeSinceLastMillis);
            }
        }

        public void removeCallbacksAndRun() {
            ViewRootImpl.this.mHandler.removeCallbacks(this);
            run();
        }
    }

    private static class UnhandledKeyManager {
        private final SparseArray<WeakReference<View>> mCapturedKeys;
        private WeakReference<View> mCurrentReceiver;
        private boolean mDispatched;

        private UnhandledKeyManager() {
            this.mDispatched = true;
            this.mCapturedKeys = new SparseArray<>();
            this.mCurrentReceiver = null;
        }

        boolean dispatch(View root, KeyEvent event) {
            if (this.mDispatched) {
                return false;
            }
            try {
                Trace.traceBegin(8L, "UnhandledKeyEvent dispatch");
                this.mDispatched = true;
                View consumer = root.dispatchUnhandledKeyEvent(event);
                if (event.getAction() == 0) {
                    int keycode = event.getKeyCode();
                    if (consumer != null && !KeyEvent.isModifierKey(keycode)) {
                        this.mCapturedKeys.put(keycode, new WeakReference<>(consumer));
                    }
                }
                return consumer != null;
            } finally {
                Trace.traceEnd(8L);
            }
        }

        void preDispatch(KeyEvent event) {
            int idx;
            this.mCurrentReceiver = null;
            if (event.getAction() == 1 && (idx = this.mCapturedKeys.indexOfKey(event.getKeyCode())) >= 0) {
                this.mCurrentReceiver = this.mCapturedKeys.valueAt(idx);
                this.mCapturedKeys.removeAt(idx);
            }
        }

        boolean preViewDispatch(KeyEvent event) {
            this.mDispatched = false;
            if (this.mCurrentReceiver == null) {
                this.mCurrentReceiver = this.mCapturedKeys.get(event.getKeyCode());
            }
            WeakReference<View> weakReference = this.mCurrentReceiver;
            if (weakReference == null) {
                return false;
            }
            View target = weakReference.get();
            if (event.getAction() == 1) {
                this.mCurrentReceiver = null;
            }
            if (target != null && target.isAttachedToWindow()) {
                target.onUnhandledKeyEvent(event);
            }
            return true;
        }
    }

    public void setDisplayDecoration(boolean displayDecoration) {
        if (displayDecoration == this.mDisplayDecorationCached) {
            return;
        }
        this.mDisplayDecorationCached = displayDecoration;
        if (this.mSurfaceControl.isValid()) {
            updateDisplayDecoration();
        }
    }

    private void updateDisplayDecoration() {
        this.mTransaction.setDisplayDecoration(this.mSurfaceControl, this.mDisplayDecorationCached).apply();
    }

    public void setSkipScreenshot(boolean skipScreenshot) {
        if (skipScreenshot == this.mSkipScreenshot) {
            return;
        }
        this.mSkipScreenshot = skipScreenshot;
        if (this.mSurfaceControl.isValid()) {
            updateSkipScreenshot();
        }
    }

    private void updateSkipScreenshot() {
        if (this.mWindowAttributes.type < 2000 || (this.mWindowAttributes.privateFlags & 1048576) != 0) {
            Log.m96e(this.mTag, "updateSkipScreenshot not allowed on this window");
        } else {
            this.mTransaction.setSkipScreenshot(this.mSurfaceControl, this.mSkipScreenshot).apply();
        }
    }

    public void setDisableSuperHdr(boolean disableSuperHdr) {
        if (disableSuperHdr == this.mDisableSuperHdr) {
            return;
        }
        this.mDisableSuperHdr = disableSuperHdr;
        if (this.mSurfaceControl.isValid()) {
            updateDisableSuperHdr();
        }
    }

    private void updateDisableSuperHdr() {
        this.mTransaction.setDisableSuperHDR(this.mSurfaceControl, this.mDisableSuperHdr).apply();
    }

    public void dispatchBlurRegions(float[][] regionCopy, long frameNumber) {
        BLASTBufferQueue bLASTBufferQueue;
        if (DEBUG_BLUR) {
            Log.m98i(this.mTag, "dispatchBlurRegions " + frameNumber);
        }
        SurfaceControl surfaceControl = getSurfaceControl();
        if (!surfaceControl.isValid()) {
            return;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.setBlurRegions(surfaceControl, regionCopy);
        if (useBLAST() && (bLASTBufferQueue = this.mBlastBufferQueue) != null) {
            bLASTBufferQueue.mergeWithNextTransaction(transaction, frameNumber);
        }
    }

    public BackgroundBlurDrawable createBackgroundBlurDrawable() {
        return this.mBlurRegionAggregator.createBackgroundBlurDrawable(this.mContext);
    }

    public BackgroundBlurDrawable createBackgroundBlurDrawable(boolean showDebug) {
        return this.mBlurRegionAggregator.createBackgroundBlurDrawable(this.mContext, showDebug);
    }

    @Override // android.view.ViewParent
    public void onDescendantUnbufferedRequested() {
        this.mUnbufferedInputSource = this.mView.mUnbufferedInputSource;
    }

    void forceDisableBLAST() {
        this.mForceDisableBLAST = true;
    }

    boolean useBLAST() {
        return this.mUseBLASTAdapter && !this.mForceDisableBLAST;
    }

    int getSurfaceSequenceId() {
        return this.mSurfaceSequenceId;
    }

    public void mergeWithNextTransaction(SurfaceControl.Transaction t, long frameNumber) {
        String str = this.mTag;
        StringBuilder sb = new StringBuilder("mWNT: t=0x");
        String str2 = SemCapabilities.FEATURE_TAG_NULL;
        StringBuilder append = sb.append(t != null ? Long.toHexString(t.mNativeObject) : SemCapabilities.FEATURE_TAG_NULL).append(" mBlastBufferQueue=0x");
        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            str2 = Long.toHexString(bLASTBufferQueue.mNativeObject);
        }
        Log.m98i(str, append.append(str2).append(" fn= ").append(frameNumber).append(" mRenderHdrSdrRatio=").append(this.mRenderHdrSdrRatio).append(" caller= ").append(Debug.getCallers(3)).toString());
        BLASTBufferQueue bLASTBufferQueue2 = this.mBlastBufferQueue;
        if (bLASTBufferQueue2 != null) {
            bLASTBufferQueue2.mergeWithNextTransaction(t, frameNumber);
        } else {
            t.apply();
        }
    }

    @Override // android.view.AttachedSurfaceControl
    public SurfaceControl.Transaction buildReparentTransaction(SurfaceControl child) {
        if (this.mSurfaceControl.isValid()) {
            return new SurfaceControl.Transaction().reparent(child, getBoundsLayer());
        }
        return null;
    }

    @Override // android.view.AttachedSurfaceControl
    public boolean applyTransactionOnDraw(SurfaceControl.Transaction t) {
        if (this.mRemoved || !isHardwareEnabled()) {
            t.apply();
        } else {
            this.mPendingTransaction.merge(t);
            this.mHasPendingTransactions = true;
            scheduleTraversals();
        }
        return true;
    }

    public void setTspDeadzone(Bundle deadzone) {
        if (CoreRune.FW_TSP_STATE_CONTROLLER) {
            try {
                this.mWindowSession.setTspDeadzone(this.mWindow, deadzone);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearTspDeadzone() {
        if (CoreRune.FW_TSP_STATE_CONTROLLER) {
            try {
                this.mWindowSession.clearTspDeadzone(this.mWindow);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTspNoteMode(boolean isTspNoteMode) {
        if (!CoreRune.FW_TSP_NOTE_MODE || this.mView == null || !this.mAdded) {
            return;
        }
        try {
            if (CoreRune.SAFE_DEBUG) {
                Slog.m113d(TAG, "setTspNoteMode is called");
            }
            this.mWindowSession.setTspNoteMode(this.mWindow, isTspNoteMode);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // android.view.AttachedSurfaceControl
    public int getBufferTransformHint() {
        return this.mSurfaceControl.getTransformHint();
    }

    @Override // android.view.AttachedSurfaceControl
    public void addOnBufferTransformHintChangedListener(AttachedSurfaceControl.OnBufferTransformHintChangedListener listener) {
        Objects.requireNonNull(listener);
        if (this.mTransformHintListeners.contains(listener)) {
            throw new IllegalArgumentException("attempt to call addOnBufferTransformHintChangedListener() with a previously registered listener");
        }
        this.mTransformHintListeners.add(listener);
    }

    @Override // android.view.AttachedSurfaceControl
    public void removeOnBufferTransformHintChangedListener(AttachedSurfaceControl.OnBufferTransformHintChangedListener listener) {
        Objects.requireNonNull(listener);
        this.mTransformHintListeners.remove(listener);
    }

    private void dispatchTransformHintChanged(int hint) {
        if (this.mTransformHintListeners.isEmpty()) {
            return;
        }
        ArrayList<AttachedSurfaceControl.OnBufferTransformHintChangedListener> listeners = (ArrayList) this.mTransformHintListeners.clone();
        for (int i = 0; i < listeners.size(); i++) {
            AttachedSurfaceControl.OnBufferTransformHintChangedListener listener = listeners.get(i);
            listener.onBufferTransformHintChanged(hint);
        }
    }

    public void requestCompatCameraControl(boolean showControl, boolean transformationApplied, ICompatCameraControlCallback callback) {
        this.mActivityConfigCallback.requestCompatCameraControl(showControl, transformationApplied, callback);
    }

    private void prepareCanvasBlur() {
        if (this.mCanvasBlurEnabled && this.mView != null) {
            if (this.mCanvasDownScale != View.sCanvasDownScale) {
                clearCanvasBlurInstances();
                this.mCanvasDownScale = View.sCanvasDownScale;
                invalidate();
            }
            int width = this.mView.getWidth() / this.mCanvasDownScale;
            int height = this.mView.getHeight() / this.mCanvasDownScale;
            if (width <= 0 || height <= 0) {
                return;
            }
            if (this.mCanvasBlurBitmap == null) {
                this.mCanvasBlurBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            }
            Bitmap bitmap = this.mCanvasBlurBitmap;
            if (bitmap != null) {
                if (bitmap.getHeight() != height || this.mCanvasBlurBitmap.getWidth() != width) {
                    this.mCanvasBlurBitmap.recycle();
                    this.mCanvasBlurBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    this.mBlurCanvas = null;
                }
                if (this.mBlurCanvas == null) {
                    this.mBlurCanvas = new Canvas(this.mCanvasBlurBitmap);
                }
                Trace.traceBegin(8L, "Capturing canvas, scale : " + this.mCanvasDownScale);
                View.sCapturingCanvas = true;
                int restoreCount = this.mBlurCanvas.save();
                Canvas canvas = this.mBlurCanvas;
                int i = this.mCanvasDownScale;
                canvas.scale(1.0f / i, 1.0f / i);
                this.mView.draw(this.mBlurCanvas);
                this.mBlurCanvas.restoreToCount(restoreCount);
                View.sCapturingCanvas = false;
                Trace.traceEnd(8L);
                Trace.traceBegin(8L, "Apply blur bitmap");
                if (this.mBlurFilter == null) {
                    this.mBlurFilter = new SemGfxImageFilter();
                }
                if (this.mBlurRadius != View.sCanvasBlurRadius) {
                    int i2 = View.sCanvasBlurRadius;
                    this.mBlurRadius = i2;
                    this.mBlurFilter.setBlurRadius(i2);
                    if (this.mBlurColorCurveEnabled) {
                        setColorCurve();
                    }
                }
                SemGfxImageFilter semGfxImageFilter = this.mBlurFilter;
                Bitmap bitmap2 = this.mCanvasBlurBitmap;
                semGfxImageFilter.applyToBitmap(bitmap2, bitmap2);
                Trace.traceEnd(8L);
                return;
            }
            return;
        }
        clearCanvasBlurInstances();
    }

    private void setColorCurve() {
        SemBlurInfo.ColorCurve colorCurve = this.mBlurColorCurve;
        if (colorCurve != null) {
            this.mBlurFilter.setProportionalSaturation(colorCurve.mSaturation);
            this.mBlurFilter.setCurveLevel(this.mBlurColorCurve.mCurveBias);
            this.mBlurFilter.setCurveMinX(this.mBlurColorCurve.mMinX);
            this.mBlurFilter.setCurveMaxX(this.mBlurColorCurve.mMaxX);
            this.mBlurFilter.setCurveMinY(this.mBlurColorCurve.mMinY);
            this.mBlurFilter.setCurveMaxY(this.mBlurColorCurve.mMaxY);
        }
    }

    private void clearCanvasBlurInstances() {
        this.mCanvasDownScale = 0;
        this.mBlurRadius = 0;
        Bitmap bitmap = this.mCanvasBlurBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mCanvasBlurBitmap = null;
            if (this.mBlurCanvas != null) {
                this.mBlurCanvas = null;
            }
        }
        this.mBlurFilter = null;
    }

    boolean wasRelayoutRequested() {
        return this.mRelayoutRequested;
    }

    void forceWmRelayout() {
        this.mForceNextWindowRelayout = true;
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [17] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    public WindowOnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return this.mOnBackInvokedDispatcher;
    }

    @Override // android.view.ViewParent
    public OnBackInvokedDispatcher findOnBackInvokedDispatcherForChild(View child, View requester) {
        return getOnBackInvokedDispatcher();
    }

    private void registerBackCallbackOnWindow() {
        this.mOnBackInvokedDispatcher.attachToWindow(this.mWindowSession, this.mWindow);
    }

    private void sendBackKeyEvent(int action) {
        long when = SystemClock.uptimeMillis();
        KeyEvent ev = new KeyEvent(when, when, action, 4, 0, 0, -1, 0, 72, 257);
        enqueueInputEvent(ev);
    }

    private void registerCompatOnBackInvokedCallback() {
        this.mCompatOnBackInvokedCallback = new CompatOnBackInvokedCallback() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda19
            @Override // android.window.CompatOnBackInvokedCallback, android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                ViewRootImpl.this.lambda$registerCompatOnBackInvokedCallback$11();
            }
        };
        if (this.mOnBackInvokedDispatcher.hasImeOnBackInvokedDispatcher()) {
            Log.m94d(TAG, "Skip registering CompatOnBackInvokedCallback on IME dispatcher");
        } else {
            this.mOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, this.mCompatOnBackInvokedCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerCompatOnBackInvokedCallback$11() {
        sendBackKeyEvent(0);
        sendBackKeyEvent(1);
    }

    @Override // android.view.AttachedSurfaceControl
    public void setTouchableRegion(Region r) {
        if (r != null) {
            this.mTouchableRegion = new Region(r);
        } else {
            this.mTouchableRegion = null;
        }
        this.mLastGivenInsets.reset();
        requestLayout();
    }

    IWindowSession getWindowSession() {
        return this.mWindowSession;
    }

    private void registerCallbacksForSync(boolean syncBuffer, SurfaceSyncGroup surfaceSyncGroup) {
        if (!isHardwareEnabled()) {
            return;
        }
        if (DEBUG_BLAST) {
            Log.m98i(this.mTag, "registerCallbacksForSync syncBuffer=" + syncBuffer);
        }
        SurfaceControl.Transaction t = new SurfaceControl.Transaction();
        t.merge(this.mPendingTransaction);
        this.mAttachInfo.mThreadedRenderer.registerRtFrameCallback(new C37408(t, surfaceSyncGroup, syncBuffer));
    }

    /* renamed from: android.view.ViewRootImpl$8 */
    class C37408 implements HardwareRenderer.FrameDrawingCallback {
        final /* synthetic */ SurfaceSyncGroup val$surfaceSyncGroup;
        final /* synthetic */ boolean val$syncBuffer;
        final /* synthetic */ SurfaceControl.Transaction val$t;

        C37408(SurfaceControl.Transaction transaction, SurfaceSyncGroup surfaceSyncGroup, boolean z) {
            this.val$t = transaction;
            this.val$surfaceSyncGroup = surfaceSyncGroup;
            this.val$syncBuffer = z;
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public void onFrameDraw(long frame) {
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public HardwareRenderer.FrameCommitCallback onFrameDraw(int syncResult, final long frame) {
            ViewRootImpl.this.mFrameNumber = frame;
            if (ViewRootImpl.DEBUG_BLAST) {
                Log.m98i(ViewRootImpl.this.mTag, "Received frameDrawingCallback syncResult=" + syncResult + " frameNum=" + frame + MediaMetrics.SEPARATOR);
            }
            ViewRootImpl.this.mergeWithNextTransaction(this.val$t, frame);
            if ((syncResult & 6) != 0) {
                this.val$surfaceSyncGroup.addTransaction(ViewRootImpl.this.mBlastBufferQueue.gatherPendingTransactions(frame));
                this.val$surfaceSyncGroup.markSyncReady();
                return null;
            }
            if (ViewRootImpl.DEBUG_BLAST) {
                Log.m98i(ViewRootImpl.this.mTag, "Setting up sync and frameCommitCallback");
            }
            if (this.val$syncBuffer) {
                BLASTBufferQueue bLASTBufferQueue = ViewRootImpl.this.mBlastBufferQueue;
                final SurfaceSyncGroup surfaceSyncGroup = this.val$surfaceSyncGroup;
                boolean result = bLASTBufferQueue.syncNextTransaction(new Consumer() { // from class: android.view.ViewRootImpl$8$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ViewRootImpl.C37408.this.lambda$onFrameDraw$0(surfaceSyncGroup, (SurfaceControl.Transaction) obj);
                    }
                });
                if (!result) {
                    Log.m102w(ViewRootImpl.this.mTag, "Unable to syncNextTransaction. Possibly something else is trying to sync?");
                    this.val$surfaceSyncGroup.markSyncReady();
                }
            }
            final SurfaceSyncGroup surfaceSyncGroup2 = this.val$surfaceSyncGroup;
            final boolean z = this.val$syncBuffer;
            return new HardwareRenderer.FrameCommitCallback() { // from class: android.view.ViewRootImpl$8$$ExternalSyntheticLambda1
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z2) {
                    ViewRootImpl.C37408.this.lambda$onFrameDraw$1(frame, surfaceSyncGroup2, z, z2);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$0(SurfaceSyncGroup surfaceSyncGroup, SurfaceControl.Transaction transaction) {
            if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                transaction.addDebugName("syncBuffer_" + surfaceSyncGroup.getName());
                Log.m98i(ViewRootImpl.this.mTag, "Received ready transaction from native, debugName=" + transaction.mDebugName);
            }
            surfaceSyncGroup.addTransaction(transaction);
            surfaceSyncGroup.markSyncReady();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$1(long frame, SurfaceSyncGroup surfaceSyncGroup, boolean syncBuffer, boolean didProduceBuffer) {
            if (ViewRootImpl.DEBUG_BLAST) {
                Log.m98i(ViewRootImpl.this.mTag, "Received frameCommittedCallback lastAttemptedDrawFrameNum=" + frame + " didProduceBuffer=" + didProduceBuffer);
            }
            if (!didProduceBuffer) {
                ViewRootImpl.this.mBlastBufferQueue.clearSyncTransaction();
                surfaceSyncGroup.addTransaction(ViewRootImpl.this.mBlastBufferQueue.gatherPendingTransactions(frame));
                surfaceSyncGroup.markSyncReady();
            } else if (!syncBuffer) {
                surfaceSyncGroup.markSyncReady();
            }
        }
    }

    private void safeguardOverlappingSyncs(final SurfaceSyncGroup activeSurfaceSyncGroup) {
        final SurfaceSyncGroup safeguardSsg = new SurfaceSyncGroup("Safeguard-" + this.mTag);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        String safeguardSsgInfo = safeguardSsg.getName() + " @ " + sdf.format(Long.valueOf(Calendar.getInstance().getTimeInMillis()));
        String str = this.mTag;
        StringBuilder append = new StringBuilder().append("New safeguard sync group ").append(safeguardSsgInfo).append(", mPreviousSyncSafeguard=");
        SurfaceSyncGroup surfaceSyncGroup = this.mPreviousSyncSafeguard;
        Slog.m117i(str, append.append(surfaceSyncGroup != null ? surfaceSyncGroup.getName() : SemCapabilities.FEATURE_TAG_NULL).append(", mFirstPreviousSyncSafeguardInfo=").append(this.mFirstPreviousSyncSafeguardInfo).toString());
        safeguardSsg.toggleTimeout(false);
        synchronized (this.mPreviousSyncSafeguardLock) {
            SurfaceSyncGroup surfaceSyncGroup2 = this.mPreviousSyncSafeguard;
            if (surfaceSyncGroup2 != null) {
                activeSurfaceSyncGroup.add(surfaceSyncGroup2, (Runnable) null);
                activeSurfaceSyncGroup.toggleTimeout(false);
                this.mPreviousSyncSafeguard.addSyncCompleteCallback(this.mSimpleExecutor, new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceSyncGroup.this.toggleTimeout(true);
                    }
                });
            }
            this.mPreviousSyncSafeguard = safeguardSsg;
            if (this.mFirstPreviousSyncSafeguardInfo == null) {
                this.mFirstPreviousSyncSafeguardInfo = safeguardSsgInfo;
            }
        }
        SurfaceControl.Transaction t = new SurfaceControl.Transaction();
        t.addTransactionCommittedListener(this.mSimpleExecutor, new SurfaceControl.TransactionCommittedListener() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda5
            @Override // android.view.SurfaceControl.TransactionCommittedListener
            public final void onTransactionCommitted() {
                ViewRootImpl.this.lambda$safeguardOverlappingSyncs$13(safeguardSsg);
            }
        });
        activeSurfaceSyncGroup.addTransaction(t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$safeguardOverlappingSyncs$13(SurfaceSyncGroup safeguardSsg) {
        safeguardSsg.markSyncReady();
        synchronized (this.mPreviousSyncSafeguardLock) {
            if (this.mPreviousSyncSafeguard == safeguardSsg) {
                this.mPreviousSyncSafeguard = null;
                this.mFirstPreviousSyncSafeguardInfo = "";
            }
        }
    }

    @Override // android.view.AttachedSurfaceControl
    public SurfaceSyncGroup getOrCreateSurfaceSyncGroup() {
        boolean newSyncGroup = false;
        if (this.mActiveSurfaceSyncGroup == null) {
            SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup(this.mTag);
            this.mActiveSurfaceSyncGroup = surfaceSyncGroup;
            surfaceSyncGroup.setAddedToSyncListener(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ViewRootImpl.this.lambda$getOrCreateSurfaceSyncGroup$15();
                }
            });
            newSyncGroup = true;
        }
        Trace.instant(8L, "getOrCreateSurfaceSyncGroup isNew=" + newSyncGroup + " " + this.mTag);
        if (DEBUG_BLAST) {
            if (newSyncGroup) {
                Log.m98i(this.mTag, "Creating new active sync group " + this.mActiveSurfaceSyncGroup.getName());
            } else {
                Log.m94d(this.mTag, "Return already created active sync group " + this.mActiveSurfaceSyncGroup.getName());
            }
        }
        this.mNumPausedForSync++;
        this.mHandler.removeMessages(37);
        this.mHandler.sendEmptyMessageDelayed(37, Build.HW_TIMEOUT_MULTIPLIER * 1000);
        return this.mActiveSurfaceSyncGroup;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOrCreateSurfaceSyncGroup$15() {
        Runnable runnable = new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                ViewRootImpl.this.lambda$getOrCreateSurfaceSyncGroup$14();
            }
        };
        if (Thread.currentThread() == this.mThread) {
            runnable.run();
        } else {
            this.mHandler.post(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOrCreateSurfaceSyncGroup$14() {
        int i = this.mNumPausedForSync;
        if (i > 0) {
            this.mNumPausedForSync = i - 1;
        }
        if (this.mNumPausedForSync == 0) {
            this.mHandler.removeMessages(37);
            if (!this.mIsInTraversal) {
                scheduleTraversals();
            }
        }
    }

    private void updateSyncInProgressCount(SurfaceSyncGroup syncGroup) {
        if (this.mAttachInfo.mThreadedRenderer == null) {
            return;
        }
        synchronized (sSyncProgressLock) {
            int i = sNumSyncsInProgress;
            sNumSyncsInProgress = i + 1;
            if (i == 0) {
                HardwareRenderer.setRtAnimationsEnabled(false);
            }
        }
        syncGroup.addSyncCompleteCallback(this.mSimpleExecutor, new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ViewRootImpl.lambda$updateSyncInProgressCount$16();
            }
        });
    }

    static /* synthetic */ void lambda$updateSyncInProgressCount$16() {
        synchronized (sSyncProgressLock) {
            int i = sNumSyncsInProgress - 1;
            sNumSyncsInProgress = i;
            if (i == 0) {
                HardwareRenderer.setRtAnimationsEnabled(true);
            }
        }
    }

    void addToSync(SurfaceSyncGroup syncable) {
        SurfaceSyncGroup surfaceSyncGroup = this.mActiveSurfaceSyncGroup;
        if (surfaceSyncGroup == null) {
            return;
        }
        surfaceSyncGroup.add(syncable, (Runnable) null);
    }

    @Override // android.view.AttachedSurfaceControl
    public void setChildBoundingInsets(Rect insets) {
        if (insets.left < 0 || insets.top < 0 || insets.right < 0 || insets.bottom < 0) {
            throw new IllegalArgumentException("Negative insets passed to setChildBoundingInsets.");
        }
        this.mChildBoundingInsets.set(insets);
        this.mChildBoundingInsetsChanged = true;
        scheduleTraversals();
    }

    public boolean isSyncBuffer() {
        return this.mSyncBuffer;
    }

    public void updateWindowOpacity(boolean isOpaque) {
        this.mIsWindowOpaque = isOpaque;
        this.mForceUpdateBoundsLayer = true;
        invalidate();
    }

    public boolean isWindowOpaque() {
        return this.mIsWindowOpaque;
    }

    public void requestRecomputeViewAttributes() {
        if (this.mIsInTraversal) {
            this.mHandler.post(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    ViewRootImpl.this.lambda$requestRecomputeViewAttributes$17();
                }
            });
        } else {
            this.mAttachInfo.mRecomputeGlobalAttributes = true;
        }
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(this.mView.getContext().getPackageName())) {
            Log.m98i(this.mTag, "Traversal, [19] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestRecomputeViewAttributes$17() {
        this.mAttachInfo.mRecomputeGlobalAttributes = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchLetterboxDirectionChanged(int direction) {
        if (CoreRune.SAFE_DEBUG && DEBUG_LAYOUT) {
            Log.m100v(this.mTag, "dispatchLetterboxDirectionChanged, direction=" + direction);
        }
        this.mHandler.removeMessages(104);
        Message msg = this.mHandler.obtainMessage(104, direction, 0);
        this.mHandler.sendMessage(msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDispatchLetterboxDirectionChanged(int direction) {
        this.mRequestedLetterboxDirection = direction;
        if (updateAppliedLetterboxDirection(direction) && (this.mView instanceof DecorView)) {
            requestInvalidateRootRenderNode();
            this.mView.invalidate();
            if (CoreRune.SAFE_DEBUG && DEBUG_LAYOUT) {
                Log.m100v(this.mTag, "handleDispatchLetterboxDirectionChanged, direction=" + direction);
            }
        }
    }

    public boolean updateAppliedLetterboxDirection(int direction) {
        boolean updated = this.mAppliedLetterboxDirection != direction;
        if (updated) {
            this.mAppliedLetterboxDirection = direction;
            Log.m100v(this.mTag, "updateAppliedLetterboxDirection, direction=" + this.mAppliedLetterboxDirection + ", Caller=" + Debug.getCaller());
        }
        return updated;
    }

    final class SmartClipRemoteRequestDispatcherProxy {
        private boolean DEBUG;
        private final String TAG = "SmartClipRemoteRequestDispatcher_ViewRootImpl";
        private Context mContext;
        private SmartClipRemoteRequestDispatcher mDispatcher;
        private SmartClipRemoteRequestDispatcher.ViewRootImplGateway mGateway;

        public SmartClipRemoteRequestDispatcherProxy(Context context) {
            this.DEBUG = false;
            SmartClipRemoteRequestDispatcher.ViewRootImplGateway viewRootImplGateway = new SmartClipRemoteRequestDispatcher.ViewRootImplGateway() { // from class: android.view.ViewRootImpl.SmartClipRemoteRequestDispatcherProxy.1
                @Override // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.ViewRootImplGateway
                public void enqueueInputEvent(InputEvent event, InputEventReceiver receiver, int flags, boolean processImmediately) {
                    ViewRootImpl.this.enqueueInputEvent(event, receiver, flags, processImmediately);
                }

                @Override // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.ViewRootImplGateway
                public PointF getScaleFactor() {
                    return new PointF(1.0f, 1.0f);
                }

                @Override // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.ViewRootImplGateway
                public View getRootView() {
                    return ViewRootImpl.this.mView;
                }

                @Override // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.ViewRootImplGateway
                public Handler getHandler() {
                    return ViewRootImpl.this.mHandler;
                }

                @Override // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.ViewRootImplGateway
                public ViewRootImpl getViewRootImpl() {
                    return ViewRootImpl.this;
                }

                @Override // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.ViewRootImplGateway
                public PointF getTranslatedPoint() {
                    return null;
                }

                @Override // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.ViewRootImplGateway
                public void getTranslatedRectIfNeeded(Rect outRect) {
                    CompatTranslator translator = ViewRootImpl.this.getCompatTranslator();
                    if (translator != null) {
                        translator.translateToScreen(outRect);
                    }
                }
            };
            this.mGateway = viewRootImplGateway;
            this.mContext = context;
            SmartClipRemoteRequestDispatcher smartClipRemoteRequestDispatcher = new SmartClipRemoteRequestDispatcher(context, viewRootImplGateway);
            this.mDispatcher = smartClipRemoteRequestDispatcher;
            this.DEBUG = smartClipRemoteRequestDispatcher.isDebugMode();
        }

        public void dispatchSmartClipRemoteRequest(final SmartClipRemoteRequestInfo request) {
            if (this.DEBUG) {
                Log.m98i("SmartClipRemoteRequestDispatcher_ViewRootImpl", "dispatchSmartClipRemoteRequest : req id=" + request.mRequestId + " type=" + request.mRequestType + " pid=" + request.mCallerPid + " uid=" + request.mCallerUid);
            }
            switch (request.mRequestType) {
                case 1:
                    this.mDispatcher.checkPermission("com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA", request.mCallerPid, request.mCallerUid);
                    ViewRootImpl.this.mHandler.post(new Runnable() { // from class: android.view.ViewRootImpl.SmartClipRemoteRequestDispatcherProxy.2
                        @Override // java.lang.Runnable
                        public void run() {
                            SmartClipRemoteRequestDispatcherProxy.this.dispatchSmartClipMetaDataExtraction(request);
                        }
                    });
                    break;
                default:
                    this.mDispatcher.dispatchSmartClipRemoteRequest(request);
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchSmartClipMetaDataExtraction(SmartClipRemoteRequestInfo request) {
            SmartClipDataExtractionEvent requestInfo = (SmartClipDataExtractionEvent) request.mRequestData;
            requestInfo.mRequestId = request.mRequestId;
            requestInfo.mTargetWindowLayer = request.mTargetWindowLayer;
            if (ViewRootImpl.this.mView != null) {
                SmartClipDataCropperImpl cropper = new SmartClipDataCropperImpl(ViewRootImpl.this.mView.getContext(), requestInfo);
                cropper.doExtractSmartClipData(ViewRootImpl.this.mView);
            } else {
                SmartClipDataCropperImpl cropper2 = new SmartClipDataCropperImpl(this.mContext, requestInfo);
                cropper2.sendExtractionResultToSmartClipService(null);
            }
        }
    }

    public void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo request) {
        SmartClipRemoteRequestDispatcherProxy smartClipRemoteRequestDispatcherProxy = this.mSmartClipDispatcherProxy;
        if (smartClipRemoteRequestDispatcherProxy != null) {
            smartClipRemoteRequestDispatcherProxy.dispatchSmartClipRemoteRequest(request);
        } else {
            Log.m96e(TAG, "dispatchSmartClipRemoteRequest : SmartClip dispatcher is null! req id=" + request.mRequestId + " type=" + request.mRequestType);
        }
    }

    public static class MotionEventMonitor {
        private static boolean DEBUG = false;
        private static final String TAG = "MotionEventMonitor";
        private ArrayList<OnTouchListener> mListeners = new ArrayList<>();

        public interface OnTouchListener {
            void onTouch(MotionEvent motionEvent);
        }

        public void registerMotionEventMonitor(OnTouchListener listener) {
            if (this.mListeners.size() > 0) {
                Log.m96e(TAG, "registerMotionEventMonitor : Just one event listener is allowed");
                return;
            }
            this.mListeners.add(listener);
            if (DEBUG) {
                Log.m98i(TAG, "registerMotionEventMonitor : Listener count=" + this.mListeners.size());
            }
        }

        public void unregisterMotionEventMonitor(OnTouchListener listener) {
            this.mListeners.remove(listener);
            if (DEBUG) {
                Log.m98i(TAG, "unregisterMotionEventMonitor : Listener count=" + this.mListeners.size());
            }
        }

        public void dispatchInputEvent(InputEvent event) {
            if (this.mListeners.size() == 0) {
                return;
            }
            if (event instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent) event;
                int action = motionEvent.getAction();
                if (DEBUG) {
                    Log.m98i(TAG, "dispatchInputEvent : action=" + action);
                }
                switch (action) {
                    case 0:
                    case 1:
                    case 3:
                    case 7:
                    case 9:
                    case 10:
                        notifyTouchEvent(motionEvent);
                        break;
                }
                return;
            }
            if (DEBUG) {
                Log.m98i(TAG, "dispatchInputEvent : The event is not instance of MotionEvent");
            }
        }

        private void notifyTouchEvent(MotionEvent event) {
            int cnt = this.mListeners.size();
            Log.m98i(TAG, "notifyTouchEvent : Listener cnt=" + cnt);
            for (int i = 0; i < cnt; i++) {
                OnTouchListener listener = this.mListeners.get(i);
                if (listener != null) {
                    listener.onTouch(event);
                }
            }
        }
    }

    public MotionEventMonitor getMotionEventMonitor() {
        return this.mMotionEventMonitor;
    }

    public void dispatchSPenGestureEvent(InputEvent[] events) {
        Message msg = this.mHandler.obtainMessage(103);
        msg.obj = events;
        this.mHandler.sendMessage(msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDispatchSPenGestureEvent(InputEvent[] events) {
        if (events == null) {
            Slog.m115e(TAG, "dispatchSPenGestureEventInjection : Event is null!");
            return;
        }
        if (CoreRune.SAFE_DEBUG) {
            Slog.m113d(TAG, "dispatchSPenGestureEventInjection eventCount=" + events.length);
        }
        long firstEventTime = events.length > 0 ? events[0].getEventTime() : -1L;
        for (final InputEvent event : events) {
            if (event != null) {
                Runnable runnable = new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewRootImpl.this.lambda$handleDispatchSPenGestureEvent$18(event);
                    }
                };
                long delay = event.getEventTime() - firstEventTime;
                if (delay > 0) {
                    this.mHandler.postDelayed(runnable, delay);
                } else {
                    runnable.run();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDispatchSPenGestureEvent$18(InputEvent event) {
        if (CoreRune.SAFE_DEBUG) {
            Slog.m113d(TAG, "dispatchSPenGestureEventInjection : injecting.. " + event);
        }
        if (event instanceof MotionEvent) {
            translateSPenGestureEventPositionToAppWindow((MotionEvent) event);
        }
        enqueueInputEvent(event, null, 0, true);
    }

    private void translateSPenGestureEventPositionToAppWindow(MotionEvent event) {
        if (this.mWinFrame.left != 0 || this.mWinFrame.top != 0) {
            float x = event.getRawX() - this.mWinFrame.left;
            float y = event.getRawY() - this.mWinFrame.top;
            event.setLocation(x, y);
        }
    }

    public void updateLightCenter(Rect rect) {
        View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && attachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.setLightCenter(this.mAttachInfo, rect);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isWheelScrollingHandled(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.mFlexPanelScrollY = event.getY();
                this.mFlexPanelScrollEnabled = false;
                return true;
            case 1:
            case 3:
                this.mFlexPanelScrollEnabled = false;
                return false;
            case 2:
                if (this.mFlexPanelScrollEnabled) {
                    this.mView.cancelLongPress();
                    return false;
                }
                if (Math.abs(event.getY() - this.mFlexPanelScrollY) > ViewConfiguration.get(this.mContext).getScaledTouchSlop() + 1) {
                    this.mFlexPanelScrollEnabled = true;
                    MotionEvent downEvent = event.copy();
                    downEvent.setDownTime(downEvent.getEventTime());
                    downEvent.setLocation(event.getX(), this.mFlexPanelScrollY);
                    downEvent.setAction(0);
                    this.mView.dispatchPointerEvent(downEvent);
                    return true;
                }
                return true;
            default:
                return false;
        }
    }
}
