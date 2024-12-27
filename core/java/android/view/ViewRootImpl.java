package android.view;

import android.Manifest;
import android.animation.AnimationHandler;
import android.animation.LayoutTransition;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.app.ResourcesManager;
import android.app.WindowConfiguration;
import android.app.servertransaction.WindowStateTransactionItem;
import android.app.slice.Slice;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.BLASTBufferQueue;
import android.graphics.Canvas;
import android.graphics.FrameInfo;
import android.graphics.HardwareRenderer;
import android.graphics.HardwareRendererObserver;
import android.graphics.Matrix;
import android.graphics.Paint;
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
import android.hardware.SyncFence;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.gnss.GnssSignalType;
import android.hardware.input.IInputManager;
import android.hardware.input.InputManagerGlobal;
import android.hardware.input.InputSettings;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioManager;
import android.media.MediaMetrics;
import android.media.TtmlUtils;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.sysprop.DisplayProperties;
import android.sysprop.ViewProperties;
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
import android.view.autofill.AutofillManager;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.ContentCaptureSession;
import android.view.flags.Flags;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodManager;
import android.widget.Scroller;
import android.window.ActivityWindowInfo;
import android.window.BackEvent;
import android.window.ClientWindowFrames;
import android.window.CompatOnBackInvokedCallback;
import android.window.InputTransferToken;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import android.window.ScreenCapture;
import android.window.SurfaceSyncGroup;
import android.window.TaskConstants;
import android.window.WindowOnBackInvokedDispatcher;

import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.internal.os.IResultReceiver;
import com.android.internal.os.SomeArgs;
import com.android.internal.policy.DecorView;
import com.android.internal.policy.PhoneFallbackEventHandler;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.view.BaseSurfaceHolder;
import com.android.internal.view.RootViewSurfaceTaker;
import com.android.internal.view.SurfaceCallbackHelper;
import com.android.modules.expresslog.Counter;

import libcore.io.IoUtils;

import com.samsung.android.content.smartclip.SmartClipDataCropperImpl;
import com.samsung.android.content.smartclip.SmartClipDataExtractionEvent;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.core.CompatTranslator;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.InputRune;
import com.samsung.android.rune.ViewRune;
import com.samsung.android.util.SemViewUtils;
import com.samsung.android.widget.SemPressGestureDetector;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public final class ViewRootImpl
        implements ViewParent,
                View.AttachInfo.Callbacks,
                ThreadedRenderer.DrawCallbacks,
                AttachedSurfaceControl {
    private static final String AOD_SHOW_STATE = "aod_show_state";
    private static final int BOUNDS_SURFACE_SUB_LAYER = -3;
    public static final boolean CLIENT_IMMERSIVE_CONFIRMATION;
    public static final boolean CLIENT_TRANSIENT;
    private static final int CONTENT_CAPTURE_ENABLED_FALSE = 2;
    private static final int CONTENT_CAPTURE_ENABLED_NOT_CHECKED = 0;
    private static final int CONTENT_CAPTURE_ENABLED_TRUE = 1;
    private static final int CONVERSION_TYPE_SPEN_TO_MOUSE = 10100;
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
    private static final boolean DEBUG_SENSITIVE_CONTENT = false;
    static final boolean DEBUG_TOUCH_EVENT;
    private static final boolean DEBUG_TOUCH_NAVIGATION = false;
    private static final boolean DEBUG_TRACKBALL;
    private static final boolean DEBUG_TRAVERSAL;
    private static final String DEBUG_TRAVERSAL_PACKAGE_NAME;
    static final boolean DEBUG_WINDOW_INSETS;
    private static final boolean ENABLE_INPUT_LATENCY_TRACKING = true;
    private static final int FRAME_RATE_BOOST_TIME = 3000;
    private static final int FRAME_RATE_CATEGORY_COUNT = 5;
    private static final int FRAME_RATE_SETTING_REEVALUATE_TIME = 100;
    private static final int FRAME_RATE_SURFACE_REPLACED_TIME = 3000;
    private static final int FRAME_RATE_TOUCH_BOOST_TIME = 3000;
    private static final int FRAME_RATE_TOUCH_HINT_TIME = 3000;
    private static final int IDLE_TIME_MILLIS = 750;
    private static final int INFREQUENT_UPDATE_COUNTS = 2;
    private static final int INFREQUENT_UPDATE_INTERVAL_MILLIS = 100;
    public static final int INTERMITTENT_STATE_INTERMITTENT = 0;
    public static final int INTERMITTENT_STATE_IN_TRANSITION = -1;
    public static final int INTERMITTENT_STATE_NOT_INTERMITTENT = 1;
    private static final int INVALID_VALUE = Integer.MIN_VALUE;
    private static final int KEEP_CLEAR_AREA_REPORT_RATE_MILLIS = 100;
    private static final boolean LOCAL_LOGV = false;
    private static final int LOGTAG_INPUT_FOCUS = 62001;
    private static final int LOGTAG_VIEWROOT_DRAW_EVENT = 60004;
    private static final int MAX_QUEUED_INPUT_EVENT_POOL_SIZE = 10;
    static final int MAX_TRACKBALL_DELAY = 250;
    private static final int MSG_CHECK_FOCUS = 13;
    private static final int MSG_CHECK_INVALIDATION_IDLE = 40;
    private static final int MSG_CLEAR_ACCESSIBILITY_FOCUS_HOST = 21;
    private static final int MSG_CLOSE_SYSTEM_DIALOGS = 14;
    private static final int MSG_DECOR_VIEW_GESTURE_INTERCEPTION = 38;
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
    private static final int MSG_FRAME_RATE_SETTING = 42;
    private static final int MSG_HIDE_INSETS = 32;
    private static final int MSG_INSETS_CONTROL_CHANGED = 29;
    private static final int MSG_INVALIDATE = 1;
    private static final int MSG_INVALIDATE_RECT = 2;
    private static final int MSG_INVALIDATE_WORLD = 22;
    private static final int MSG_KEEP_CLEAR_RECTS_CHANGED = 35;
    private static final int MSG_PAUSED_FOR_SYNC_TIMEOUT = 37;
    private static final int MSG_POINTER_CAPTURE_CHANGED = 28;
    private static final int MSG_PROCESS_INPUT_EVENTS = 19;
    private static final int MSG_REFRESH_POINTER_ICON = 41;
    private static final int MSG_REPORT_KEEP_CLEAR_RECTS = 36;
    private static final int MSG_REQUEST_KEYBOARD_SHORTCUTS = 26;
    private static final int MSG_REQUEST_SCROLL_CAPTURE = 33;
    private static final int MSG_RESIZED = 4;
    private static final int MSG_RESIZED_REPORT = 5;
    private static final int MSG_SHOW_INSETS = 31;
    private static final int MSG_SPEN_GESTURE_EVENT = 103;
    private static final int MSG_SURFACE_REPLACED_TIMEOUT = 43;
    private static final int MSG_SYNTHESIZE_INPUT_EVENT = 24;
    private static final int MSG_SYSTEM_GESTURE_EXCLUSION_CHANGED = 30;
    private static final int MSG_TOUCH_BOOST_TIMEOUT = 39;
    private static final int MSG_TOUCH_HINT_TIMEOUT = 106;
    private static final int MSG_UPDATE_CONFIGURATION = 18;
    private static final int MSG_WINDOW_FOCUS_CHANGED = 6;
    private static final int MSG_WINDOW_FOCUS_IN_TASK_CHANGED = 105;
    private static final int MSG_WINDOW_MOVED = 23;
    private static final int MSG_WINDOW_TOUCH_MODE_CHANGED = 34;
    private static final boolean MT_RENDERER_AVAILABLE = true;
    private static final long NANOS_PER_MILLI = 1000000;
    private static final long NANOS_PER_SEC = 1000000000;
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
    private static final boolean sEnableVrr;
    static boolean sFirstDrawComplete;
    static final ArrayList<Runnable> sFirstDrawHandlers;
    private static int sNumSyncsInProgress;
    static final ThreadLocal<HandlerActionQueue> sRunQueues;
    private static boolean sSafeScheduleTraversals;
    private static boolean sSurfaceFlingerBugfixFlagValue;
    private static final Object sSyncProgressLock;
    private static boolean sToolkitEnableInvalidateCheckThreadFlagValue;
    private static boolean sToolkitFrameRateFunctionEnablingReadOnlyFlagValue;
    private static boolean sToolkitFrameRateTypingReadOnlyFlagValue;
    private static boolean sToolkitFrameRateVelocityMappingReadOnlyFlagValue;
    private static final boolean sToolkitFrameRateViewEnablingReadOnlyFlagValue;
    private static boolean sToolkitMetricsForFrameRateDecisionFlagValue;
    private static boolean sToolkitSetFrameRateReadOnlyFlagValue;
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
    private final boolean mAppStartInfoTimestampsFlagValue;
    private AtomicBoolean mAppStartTimestampsSent;
    private boolean mAppStartTrackingStarted;
    private boolean mAppVisibilityChanged;
    boolean mAppVisible;
    private int mAppliedLetterboxDirection;
    boolean mApplyInsetsRequested;
    final View.AttachInfo mAttachInfo;
    AudioManager mAudioManager;
    final String mBasePackageName;
    private boolean mBixbyTouchTriggered;
    private BLASTBufferQueue mBlastBufferQueue;
    private final BackgroundBlurDrawable.Aggregator mBlurRegionAggregator;
    private SurfaceControl mBoundsLayer;
    private int mBoundsLayerCreatedCount;
    private boolean mCanTriggerBixbyTouch;
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
    private int mCutoutPolicy;
    private boolean mDeferTransactionRequested;
    private final int mDensity;
    private boolean mDesktopMode;
    private SemDesktopModeManager mDesktopModeManager;
    private boolean mDesktopModeStandAlone;
    private Rect mDirty;
    int mDispatchedSystemBarAppearance;
    int mDispatchedSystemUiVisibility;
    Display mDisplay;
    boolean mDisplayDecorationCached;
    private final DisplayManager.DisplayListener mDisplayListener;
    ClipDescription mDragDescription;
    final PointF mDragPoint;
    private boolean mDragResizing;
    boolean mDrawingAllowed;
    private boolean mDrawnThisFrame;
    private boolean mDrewOnceForSync;
    private boolean mEarlyHasWindowFocus;
    final Executor mExecutor;
    final boolean mExtraDisplayListenerLogging;
    FallbackEventHandler mFallbackEventHandler;
    private boolean mFastScrollSoundEffectsEnabled;
    boolean mFirst;
    private long mFirstFramePresentedTimeNs;
    InputStage mFirstInputStage;
    InputStage mFirstPostImeInputStage;
    private boolean mFlexPanelScrollEnabled;
    private float mFlexPanelScrollY;
    private boolean mForceDecorViewVisibility;
    private boolean mForceDraw;
    private int mForceInvertEnabled;
    private ContentObserver mForceInvertObserver;
    private boolean mForceModeInScreenshot;
    private boolean mForceNextConfigUpdate;
    boolean mForceNextWindowRelayout;
    private boolean mForceUpdateBoundsLayer;
    private int mFpsNumFrames;
    private long mFpsPrevTime;
    private long mFpsStartTime;
    private String mFpsTraceName;
    long mFrameNumber;
    private int mFrameRateCategoryChangeReason;
    private int mFrameRateCategoryHighCount;
    private int mFrameRateCategoryHighHintCount;
    private int mFrameRateCategoryLowCount;
    private int mFrameRateCategoryNormalCount;
    private String mFrameRateCategoryView;
    int mFrameRateCompatibility;
    private final SurfaceControl.Transaction mFrameRateTransaction;
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
    private final HdrRenderState mHdrRenderState;
    int mHeight;
    final HighContrastTextManager mHighContrastTextManager;
    private final ImeBackAnimationController mImeBackAnimationController;
    private final ImeFocusController mImeFocusController;
    private boolean mInLayout;
    private int mInfrequentUpdateCount;
    private final InputEventCompatProcessor mInputCompatProcessor;
    private final InputEventAssigner mInputEventAssigner;
    protected final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    private WindowInputEventReceiver mInputEventReceiver;
    private IInputManager mInputManagerService;
    InputQueue mInputQueue;
    InputQueue.Callback mInputQueueCallback;
    private boolean mInsetsAnimationRunning;
    private final InsetsController mInsetsController;
    private float mInvCompatScale;
    private Runnable mInvalidateForScreenshotRunnable;
    final InvalidateOnAnimationRunnable mInvalidateOnAnimationRunnable;
    private boolean mInvalidateRootRequested;
    private boolean mInvalidationIdleMessagePosted;
    boolean mIsAmbientMode;
    public boolean mIsAnimating;
    private boolean mIsBoundsColorLayer;
    boolean mIsCreating;
    private boolean mIsCutoutRemoveForDispatchNeeded;
    private boolean mIsCutoutRemoveNeeded;
    private boolean mIsDetached;
    boolean mIsDeviceDefault;
    private boolean mIsDragging;
    boolean mIsDrawing;
    private boolean mIsFlingState;
    private boolean mIsFrameRateBoosting;
    private boolean mIsFrameRateConflicted;
    private boolean mIsHRR;
    boolean mIsInTraversal;
    private boolean mIsPressedGesture;
    private final boolean mIsStylusPointerIconEnabled;
    private boolean mIsSurfaceOpaque;
    private boolean mIsTouchBoosting;
    private boolean mIsTouchHint;
    private boolean mIsWindowOpaque;
    private Rect mKeepClearAccessibilityFocusRect;
    private final ViewRootRectTracker mKeepClearRectsTracker;
    private float mLargestChildPercentage;
    private String mLargestViewTraceName;
    private int mLastClickToolType;
    private final Configuration mLastConfigurationFromResources;
    private boolean mLastDrawScreenOff;
    final ViewTreeObserver.InternalInsetsInfo mLastGivenInsets;
    private final Rect mLastLayoutFrame;
    String mLastPerformDrawSkippedReason;
    String mLastPerformTraversalsSkipDrawReason;
    private float mLastPreferredFrameRate;
    private int mLastPreferredFrameRateCategory;
    String mLastReportNextDrawReason;
    private ActivityWindowInfo mLastReportedActivityWindowInfo;
    private final MergedConfiguration mLastReportedMergedConfiguration;
    WeakReference<View> mLastScrolledFocus;
    private final Point mLastSurfaceSize;
    int mLastSyncSeqId;
    int mLastSystemUiVisibility;
    int mLastTouchDeviceId;
    final PointF mLastTouchPoint;
    int mLastTouchPointerId;
    int mLastTouchSource;
    private boolean mLastTraversalWasVisible;
    private long mLastUpdateTimeMillis;
    private WindowInsets mLastWindowInsets;
    boolean mLayoutRequested;
    ArrayList<View> mLayoutRequesters;
    final IBinder mLeashToken;
    volatile Object mLocalDragState;
    final WindowLeaked mLocation;
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private int mMinimumSizeForOverlappingWithCutoutAsDefault;
    private int mMinusOneFrameIntervalMillis;
    private int mMinusTwoFrameIntervalMillis;
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
    private ActivityWindowInfo mPendingActivityWindowInfo;
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
    private MotionEvent mPointerIconEvent;
    private float mPreferredFrameRate;
    private int mPreferredFrameRateCategory;
    private long mPreviousFrameDrawnTime;
    private SurfaceSyncGroup mPreviousSyncSafeguard;
    private final Object mPreviousSyncSafeguardLock;
    Region mPreviousTouchableRegion;
    private int mPreviousTransformHint;
    final Region mPreviousTransparentRegion;
    boolean mProcessInputEventsScheduled;
    private boolean mProcessingBackKey;
    private boolean mProfile;
    private boolean mProfileRendering;
    private QueuedInputEvent mQueuedInputEventPool;
    private int mQueuedInputEventPoolSize;
    private final Bundle mRelayoutBundle;
    private boolean mRelayoutRequested;
    private final WindowRelayoutResult mRelayoutResult;
    private int mRelayoutSeq;
    private boolean mRemoved;
    private Choreographer.FrameCallback mRenderProfiler;
    private boolean mRenderProfilingEnabled;
    private long mRenderThreadDrawStartTimeNs;
    boolean mReportNextDraw;
    private float mRequestedFrameRateByFling;
    public int mRequestedLetterboxDirection;
    private PointerIcon mResolvedPointerIcon;
    private HashSet<ScrollCaptureCallback> mRootScrollCaptureCallbacks;
    Paint mRoundDisplayAccessibilityHighlightPaint;
    private DragEvent mSavedStickyDragEvent;
    private long mScrollCaptureRequestTimeout;
    boolean mScrollMayChange;
    int mScrollY;
    Scroller mScroller;
    private boolean mSemEarlyAppVisibility;
    private boolean mSemEarlyAppVisibilityChanged;
    private SemPressGestureDetector mSemPressGestureDetector;
    SendWindowContentChangedAccessibilityEvent mSendWindowContentChangedAccessibilityEvent;
    private final ISensitiveContentProtectionManager mSensitiveContentProtectionService;
    private final Executor mSimpleExecutor;
    SmartClipRemoteRequestDispatcherProxy mSmartClipDispatcherProxy;
    int mSoftInputMode;
    View mStartedDragViewForA11y;
    boolean mStopped;
    public final Surface mSurface;
    private final ArrayList<SurfaceChangedCallback> mSurfaceChangedCallbacks;
    private final SurfaceControl mSurfaceControl;
    BaseSurfaceHolder mSurfaceHolder;
    SurfaceHolder.Callback2 mSurfaceHolderCallback;
    private boolean mSurfaceReplaced;
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
    private ArrayList<View> mThreadedRendererViews;
    private final WindowManager.LayoutParams mTmpAttrs;
    private final ClientWindowFrames mTmpFrames;
    final int[] mTmpLocation;
    final TypedValue mTmpValue;
    private final SurfaceControl.Transaction mTouchHintTransaction;
    Region mTouchableRegion;
    private final SurfaceControl.Transaction mTransaction;
    private ArrayList<AttachedSurfaceControl.OnBufferTransformHintChangedListener>
            mTransformHintListeners;
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
    private boolean mUpdateSurfaceNeeded;
    private boolean mUseMTRenderer;
    View mView;
    private final boolean mViewBoundsSandboxingEnabled;
    final ViewConfiguration mViewConfiguration;
    protected final ViewFrameInfo mViewFrameInfo;
    private int mViewLayoutDirectionInitial;
    private boolean mViewMeasureDeferred;
    private final ViewRootSurfaceController mViewRootSurfaceController;
    int mViewVisibility;
    private final Rect mVisRect;
    private boolean mWasLastDrawCanceled;
    private boolean mWebViewAttached;
    int mWidth;
    boolean mWillDrawSoon;
    final Rect mWinFrame;
    private final Rect mWinFrameInScreen;
    private Rect mWinFrameScreen;
    final W mWindow;
    public final WindowManager.LayoutParams mWindowAttributes;
    boolean mWindowAttributesChanged;
    final ArrayList<WindowCallbacks> mWindowCallbacks;
    CountDownLatch mWindowDrawCountDown;
    boolean mWindowFocusChanged;
    private boolean mWindowFocusInTaskChanged;
    private final WindowLayout mWindowLayout;
    final IWindowSession mWindowSession;
    private Predicate<KeyEvent> mWindowlessBackKeyCallback;
    private SurfaceSyncGroup mWmsRequestSyncGroup;
    int mWmsRequestSyncGroupState;

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
        CLIENT_TRANSIENT = SystemProperties.getBoolean("persist.wm.debug.client_transient", false);
        CLIENT_IMMERSIVE_CONFIRMATION =
                SystemProperties.getBoolean(
                        "persist.wm.debug.client_immersive_confirmation", false);
        sRunQueues = new ThreadLocal<>();
        sFirstDrawHandlers = new ArrayList<>();
        sFirstDrawComplete = false;
        sConfigCallbacks = new ArrayList<>();
        sCompatibilityDone = false;
        mResizeInterpolator = new AccelerateDecelerateInterpolator();
        sSyncProgressLock = new Object();
        sNumSyncsInProgress = 0;
        sAnrReported = false;
        sTransactionHangCallback =
                new BLASTBufferQueue
                        .TransactionHangCallback() { // from class: android.view.ViewRootImpl.1
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
        sToolkitFrameRateVelocityMappingReadOnlyFlagValue =
                Flags.toolkitFrameRateVelocityMappingReadOnly();
        sToolkitEnableInvalidateCheckThreadFlagValue = Flags.enableInvalidateCheckThread();
        sSurfaceFlingerBugfixFlagValue =
                com.android.graphics.surfaceflinger.flags.Flags.vrrBugfix24q4();
        sEnableVrr = ViewProperties.vrr_enabled().orElse(true).booleanValue();
        sToolkitSetFrameRateReadOnlyFlagValue = Flags.toolkitSetFrameRateReadOnly();
        sToolkitMetricsForFrameRateDecisionFlagValue = Flags.toolkitMetricsForFrameRateDecision();
        sToolkitFrameRateTypingReadOnlyFlagValue = Flags.toolkitFrameRateTypingReadOnly();
        sToolkitFrameRateFunctionEnablingReadOnlyFlagValue =
                Flags.toolkitFrameRateFunctionEnablingReadOnly();
        sToolkitFrameRateViewEnablingReadOnlyFlagValue =
                Flags.toolkitFrameRateViewEnablingReadOnly();
    }

    public interface ActivityConfigCallback {
        default void onConfigurationChanged(Configuration overrideConfig, int newDisplayId) {
            throw new IllegalStateException("Not implemented");
        }

        default void onConfigurationChanged(
                Configuration overrideConfig,
                int newDisplayId,
                ActivityWindowInfo activityWindowInfo) {
            onConfigurationChanged(overrideConfig, newDisplayId);
        }
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
        if (this.mDesktopMode && (131072 & res) != 0) {
            compatTranslatorEnabled = true;
        }
        if (compatTranslatorEnabled && this.mCompatTranslator == null) {
            CompatTranslator parentTranslator = null;
            if (this.mParentDecorView != null
                    && this.mParentDecorView != this.mView
                    && (parentViewRootImpl = this.mParentDecorView.getViewRootImpl()) != null) {
                parentTranslator = parentViewRootImpl.getCompatTranslator();
            }
            this.mCompatTranslator = new CompatTranslator(parentTranslator);
        }
        if (this.mCompatTranslatorEnabled != compatTranslatorEnabled) {
            if (compatTranslatorEnabled) {
                this.mWinFrameScreen = new Rect();
            }
            this.mCompatTranslatorEnabled = compatTranslatorEnabled;
        }
    }

    CompatTranslator getCompatTranslator() {
        if (this.mCompatTranslatorEnabled) {
            return this.mCompatTranslator;
        }
        return null;
    }

    private void updateCutoutRemoveNeeded(int flags) {
        boolean isCutoutRemoveNeeded =
                (2097152 & flags) != 0
                        || (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED
                                && getCompatWindowConfiguration().isSplitScreen());
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

    public ImeFocusController getImeFocusController() {
        return this.mImeFocusController;
    }

    static final class SystemUiVisibilityInfo {
        int globalVisibility;
        int localChanges;
        int localValue;

        SystemUiVisibilityInfo() {}
    }

    public HandwritingInitiator getHandwritingInitiator() {
        return this.mHandwritingInitiator;
    }

    public ViewRootImpl(Context context, Display display) {
        this(context, display, WindowManagerGlobal.getWindowSession(), new WindowLayout());
    }

    public ViewRootImpl(
            Context context, Display display, IWindowSession session, WindowLayout windowLayout) {
        this.mTransformHintListeners = new ArrayList<>();
        this.mPreviousTransformHint = 0;
        this.mForceInvertEnabled = Integer.MIN_VALUE;
        this.mFastScrollSoundEffectsEnabled = false;
        this.mWindowCallbacks = new ArrayList<>();
        this.mTmpLocation = new int[2];
        this.mTmpValue = new TypedValue();
        this.mWindowAttributes = new WindowManager.LayoutParams();
        this.mAppVisible = true;
        this.mForceDecorViewVisibility = false;
        this.mOrigWindowType = -1;
        this.mStopped = false;
        this.mIsAmbientMode = false;
        this.mPausedForTransition = false;
        this.mViewFrameInfo = new ViewFrameInfo();
        this.mInputEventAssigner = new InputEventAssigner();
        this.mDisplayDecorationCached = false;
        this.mInfrequentUpdateCount = 0;
        this.mLastUpdateTimeMillis = 0L;
        this.mMinusOneFrameIntervalMillis = 0;
        this.mMinusTwoFrameIntervalMillis = 0;
        this.mInvalidationIdleMessagePosted = false;
        this.mThreadedRendererViews = new ArrayList<>();
        this.mSurfaceSize = new Point();
        this.mLastSurfaceSize = new Point();
        this.mVisRect = new Rect();
        this.mTempRect = new Rect();
        this.mContentCaptureEnabled = 0;
        this.mSyncBuffer = false;
        this.mCheckIfCanDraw = false;
        this.mLastTraversalWasVisible = true;
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
        this.mHdrRenderState = new HdrRenderState(this);
        this.mSurfaceSession = new SurfaceSession();
        this.mTransaction = new SurfaceControl.Transaction();
        this.mFrameRateTransaction = new SurfaceControl.Transaction();
        this.mTouchHintTransaction = new SurfaceControl.Transaction();
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
        this.mLastTouchDeviceId = -1;
        this.mFpsStartTime = -1L;
        this.mFpsPrevTime = -1L;
        this.mPreviousFrameDrawnTime = -1L;
        this.mLargestChildPercentage = 0.0f;
        this.mFrameRateCategoryChangeReason = 0;
        this.mInvalidateForScreenshotRunnable = null;
        this.mForceModeInScreenshot = false;
        this.mResolvedPointerIcon = null;
        this.mAccessibilityInteractionConnectionManager =
                new AccessibilityInteractionConnectionManager();
        this.mInLayout = false;
        this.mLayoutRequesters = new ArrayList<>();
        this.mHandlingLayoutInLayoutRequest = false;
        this.mInputEventConsistencyVerifier =
                InputEventConsistencyVerifier.isInstrumentationEnabled()
                        ? new InputEventConsistencyVerifier(this, 0)
                        : null;
        this.mBlurRegionAggregator = new BackgroundBlurDrawable.Aggregator(this);
        this.mSmartClipDispatcherProxy = null;
        this.mCutoutPolicy = 0;
        this.mTmpAttrs = new WindowManager.LayoutParams();
        this.mGestureExclusionTracker =
                new ViewRootRectTracker(
                        new Function() { // from class:
                                         // android.view.ViewRootImpl$$ExternalSyntheticLambda18
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                List systemGestureExclusionRects;
                                systemGestureExclusionRects =
                                        ((View) obj).getSystemGestureExclusionRects();
                                return systemGestureExclusionRects;
                            }
                        });
        this.mKeepClearRectsTracker =
                new ViewRootRectTracker(
                        new Function() { // from class:
                                         // android.view.ViewRootImpl$$ExternalSyntheticLambda19
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                List collectPreferKeepClearRects;
                                collectPreferKeepClearRects =
                                        ((View) obj).collectPreferKeepClearRects();
                                return collectPreferKeepClearRects;
                            }
                        });
        this.mUnrestrictedKeepClearRectsTracker =
                new ViewRootRectTracker(
                        new Function() { // from class:
                                         // android.view.ViewRootImpl$$ExternalSyntheticLambda20
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                List collectUnrestrictedPreferKeepClearRects;
                                collectUnrestrictedPreferKeepClearRects =
                                        ((View) obj).collectUnrestrictedPreferKeepClearRects();
                                return collectUnrestrictedPreferKeepClearRects;
                            }
                        });
        this.mPreviousSyncSafeguardLock = new Object();
        this.mNumPausedForSync = 0;
        this.mScrollCaptureRequestTimeout = 2500L;
        this.mSurfaceSequenceId = 0;
        this.mPreferredFrameRateCategory = 0;
        this.mLastPreferredFrameRateCategory = 0;
        this.mPreferredFrameRate = 0.0f;
        this.mLastPreferredFrameRate = 0.0f;
        this.mIsFrameRateBoosting = false;
        this.mIsTouchBoosting = false;
        this.mIsPressedGesture = false;
        this.mDrawnThisFrame = false;
        this.mIsFrameRateConflicted = false;
        this.mSurfaceReplaced = false;
        this.mFrameRateCompatibility = 1;
        this.mIsTouchHint = false;
        this.mFrameRateCategoryHighCount = 0;
        this.mFrameRateCategoryHighHintCount = 0;
        this.mFrameRateCategoryNormalCount = 0;
        this.mFrameRateCategoryLowCount = 0;
        this.mRelayoutBundle =
                com.android.window.flags.Flags.windowSessionRelayoutInfo() ? null : new Bundle();
        this.mRelayoutResult =
                com.android.window.flags.Flags.windowSessionRelayoutInfo()
                        ? new WindowRelayoutResult(
                                this.mTmpFrames,
                                this.mPendingMergedConfiguration,
                                this.mSurfaceControl,
                                this.mTempInsets,
                                this.mTempControls)
                        : null;
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
        this.mAppStartTimestampsSent = new AtomicBoolean(false);
        this.mAppStartTrackingStarted = false;
        this.mRenderThreadDrawStartTimeNs = -1L;
        this.mFirstFramePresentedTimeNs = -1L;
        this.mPointerIconEvent = null;
        this.mViewRootSurfaceController = new ViewRootSurfaceController(this);
        this.mMinimumSizeForOverlappingWithCutoutAsDefault = 0;
        this.mSemPressGestureDetector = null;
        this.mBixbyTouchTriggered = false;
        this.mCanTriggerBixbyTouch = false;
        this.mFlexPanelScrollEnabled = false;
        this.mFlexPanelScrollY = 0.0f;
        this.mIsHRR = false;
        this.mProfile = false;
        this.mDisplayListener =
                new DisplayManager.DisplayListener() { // from class: android.view.ViewRootImpl.4
                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public void onDisplayChanged(int displayId) {
                        if (ViewRootImpl.this.mExtraDisplayListenerLogging) {
                            Slog.i(
                                    ViewRootImpl.this.mTag,
                                    "Received onDisplayChanged - " + ViewRootImpl.this.mView);
                        }
                        if (ViewRootImpl.this.mView != null
                                && ViewRootImpl.this.mDisplay.getDisplayId() == displayId) {
                            int oldDisplayState = ViewRootImpl.this.mAttachInfo.mDisplayState;
                            int newDisplayState = ViewRootImpl.this.mDisplay.getState();
                            Log.i(
                                    ViewRootImpl.this.mTag,
                                    "onDisplayChanged oldDisplayState="
                                            + oldDisplayState
                                            + " newDisplayState="
                                            + newDisplayState);
                            if (ViewRootImpl.this.mExtraDisplayListenerLogging) {
                                Slog.i(
                                        ViewRootImpl.this.mTag,
                                        "DisplayState - old: "
                                                + oldDisplayState
                                                + ", new: "
                                                + newDisplayState);
                            }
                            if (Trace.isTagEnabled(32L)) {
                                Trace.traceCounter(
                                        32L,
                                        "vri#screenState[" + ViewRootImpl.this.mTag + "] state=",
                                        newDisplayState);
                            }
                            if (oldDisplayState != newDisplayState) {
                                ViewRootImpl.this.mAttachInfo.mDisplayState = newDisplayState;
                                ViewRootImpl.this.pokeDrawLockIfNeeded();
                                if (oldDisplayState != 0) {
                                    int oldScreenState = toViewScreenState(oldDisplayState);
                                    int newScreenState = toViewScreenState(newDisplayState);
                                    if (oldScreenState != newScreenState) {
                                        ViewRootImpl.this.mView.dispatchScreenStateChanged(
                                                newScreenState);
                                    }
                                    if (ViewRootImpl.DEBUG_TRAVERSAL
                                            && ViewRootImpl.DEBUG_TRAVERSAL_PACKAGE_NAME.equals(
                                                    ActivityThread.currentPackageName())) {
                                        Log.i(
                                                ViewRootImpl.this.mTag,
                                                "Traversal, [4] mView="
                                                        + ViewRootImpl.this.mView
                                                        + " oldDisplayState="
                                                        + oldDisplayState);
                                    }
                                    if (oldDisplayState == 1) {
                                        ViewRootImpl.this.mFullRedrawNeeded = true;
                                        ViewRootImpl.this.scheduleTraversals();
                                    } else if ((oldDisplayState == 3 || oldDisplayState == 4)
                                            && newDisplayState == 2
                                            && displayId == 0
                                            && (ViewRootImpl.this.mWindowAttributes.samsungFlags
                                                            & 262144)
                                                    == 0) {
                                        ViewRootImpl.this.mFullRedrawNeeded = true;
                                        ViewRootImpl.this.scheduleTraversals();
                                    }
                                }
                            }
                        }
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public void onDisplayRemoved(int displayId) {}

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public void onDisplayAdded(int displayId) {}

                    private int toViewScreenState(int displayState) {
                        return Settings.System.getInt(
                                                ViewRootImpl.this.mContentResolver,
                                                ViewRootImpl.AOD_SHOW_STATE,
                                                0)
                                        != 0
                                ? displayState == 2 ? 1 : 0
                                : displayState == 1 ? 0 : 1;
                    }
                };
        this.mSurfaceChangedCallbacks = new ArrayList<>();
        this.mWebViewAttached = false;
        this.mHandler = new ViewRootHandler();
        this.mExecutor =
                new Executor() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda21
                    @Override // java.util.concurrent.Executor
                    public final void execute(Runnable runnable) {
                        ViewRootImpl.this.lambda$new$10(runnable);
                    }
                };
        this.mIsDragging = false;
        this.mTraversalRunnable = new TraversalRunnable();
        this.mConsumedBatchedInputRunnable = new ConsumeBatchedInputRunnable();
        this.mConsumeBatchedInputImmediatelyRunnable = new ConsumeBatchedInputImmediatelyRunnable();
        this.mInvalidateOnAnimationRunnable = new InvalidateOnAnimationRunnable();
        this.mSimpleExecutor = new PendingIntent$$ExternalSyntheticLambda0();
        this.mRequestedLetterboxDirection = 0;
        this.mAppliedLetterboxDirection = 0;
        this.mIsFlingState = false;
        this.mRequestedFrameRateByFling = 0.0f;
        this.mContext = context;
        this.mWindowSession = session;
        this.mWindowLayout = windowLayout;
        this.mDisplay = display;
        if (this.mDisplay == null) {
            Log.i(TAG, "ViewRootImpl, mDisplay is null #1");
        }
        this.mBasePackageName = context.getBasePackageName();
        String name = DisplayProperties.debug_vri_package().orElse(null);
        this.mExtraDisplayListenerLogging =
                !TextUtils.isEmpty(name) && name.equals(this.mBasePackageName);
        this.mContentResolver = context.getContentResolver();
        this.mThread = Thread.currentThread();
        this.mHCTRelayoutHandler = new HCTRelayoutHandler();
        this.mLocation = new WindowLeaked(null);
        this.mLocation.fillInStackTrace();
        this.mWidth = -1;
        this.mHeight = -1;
        this.mDirty = new Rect();
        this.mWinFrame = new Rect();
        this.mLastLayoutFrame = new Rect();
        this.mWindow = new W(this);
        this.mLeashToken = new Binder();
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        this.mViewVisibility = 8;
        this.mTransparentRegion = new Region();
        this.mPreviousTransparentRegion = new Region();
        this.mFirst = true;
        this.mPerformContentCapture = true;
        this.mAdded = false;
        this.mAttachInfo =
                new View.AttachInfo(
                        this.mWindowSession,
                        this.mWindow,
                        display,
                        this,
                        this.mHandler,
                        this,
                        context);
        this.mCompatibleVisibilityInfo = new SystemUiVisibilityInfo();
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        this.mHighContrastTextManager = new HighContrastTextManager();
        this.mViewConfiguration = ViewConfiguration.get(context);
        this.mDensity = context.getResources().getDisplayMetrics().densityDpi;
        this.mNoncompatDensity = context.getResources().getDisplayMetrics().noncompatDensityDpi;
        this.mFallbackEventHandler = new PhoneFallbackEventHandler(context);
        this.mChoreographer = Choreographer.getInstance();
        this.mInsetsController = new InsetsController(new ViewRootInsetsControllerHost(this));
        this.mImeBackAnimationController =
                new ImeBackAnimationController(this, this.mInsetsController);
        this.mHandwritingInitiator =
                new HandwritingInitiator(
                        this.mViewConfiguration,
                        (InputMethodManager)
                                this.mContext.getSystemService(InputMethodManager.class));
        this.mViewBoundsSandboxingEnabled = getViewBoundsSandboxingEnabled();
        this.mIsStylusPointerIconEnabled = InputSettings.isStylusPointerIconEnabled(this.mContext);
        String processorOverrideName =
                context.getResources()
                        .getString(R.string.config_inputEventCompatProcessorOverrideClassName);
        if (processorOverrideName.isEmpty()) {
            this.mInputCompatProcessor = new InputEventCompatProcessor(context);
        } else {
            InputEventCompatProcessor compatProcessor = null;
            try {
                compatProcessor =
                        (InputEventCompatProcessor)
                                Class.forName(processorOverrideName)
                                        .getConstructor(Context.class)
                                        .newInstance(context);
            } catch (Exception e) {
                Log.e(TAG, "Unable to create the InputEventCompatProcessor. ", e);
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
        if (CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT) {
            this.mMinimumSizeForOverlappingWithCutoutAsDefault =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(
                                    R.dimen
                                            .samsung_minimum_size_for_overlapping_with_cutout_as_default);
        }
        this.mInputManagerService =
                IInputManager.Stub.asInterface(ServiceManager.getService("input"));
        loadSystemProperties();
        this.mImeFocusController = new ImeFocusController(this);
        this.mScrollCaptureRequestTimeout = 2500L;
        this.mOnBackInvokedDispatcher =
                new WindowOnBackInvokedDispatcher(context, Looper.myLooper());
        if (Flags.sensitiveContentAppProtection()) {
            this.mSensitiveContentProtectionService =
                    ISensitiveContentProtectionManager.Stub.asInterface(
                            ServiceManager.getService(
                                    Context.SENSITIVE_CONTENT_PROTECTION_SERVICE));
            if (this.mSensitiveContentProtectionService == null) {
                Log.e(TAG, "SensitiveContentProtectionService shouldn't be null");
            }
        } else {
            this.mSensitiveContentProtectionService = null;
        }
        this.mAppStartInfoTimestampsFlagValue = android.app.Flags.appStartInfoTimestamps();
        this.mSmartClipDispatcherProxy = new SmartClipRemoteRequestDispatcherProxy(context);
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            this.mMotionEventMonitor = new MotionEventMonitor();
        }
        if (!CoreRune.FW_VRR_DISCRETE) {
            sToolkitSetFrameRateReadOnlyFlagValue = false;
        }
        Log.i(TAG, "dVRR is " + (sToolkitSetFrameRateReadOnlyFlagValue ? "enabled" : "disabled"));
    }

    private void updateDesktopMode() {
        int desktopModeEnabled;
        if (this.mDesktopModeManager == null) {
            this.mDesktopModeManager =
                    (SemDesktopModeManager)
                            this.mContext.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        }
        if (this.mDesktopModeManager != null) {
            SemDesktopModeState desktopModeState = this.mDesktopModeManager.getDesktopModeState();
            boolean z = true;
            if (desktopModeState != null) {
                desktopModeEnabled = desktopModeState.getEnabled();
                this.mDesktopModeStandAlone = desktopModeState.getDisplayType() == 101;
            } else {
                desktopModeEnabled = 2;
                this.mDesktopModeStandAlone = false;
            }
            if (this.mDisplay.getDisplayId() != 2
                    && (!this.mDesktopModeStandAlone
                            || (desktopModeEnabled != 3 && desktopModeEnabled != 4))) {
                z = false;
            }
            this.mDesktopMode = z;
        }
    }

    public static void addFirstDrawHandler(Runnable callback) {
        synchronized (sFirstDrawHandlers) {
            if (!sFirstDrawComplete) {
                sFirstDrawHandlers.add(callback);
            }
        }
    }

    public static void addConfigCallback(ConfigChangedCallback callback) {
        synchronized (sConfigCallbacks) {
            sConfigCallbacks.add(callback);
        }
    }

    public static void removeConfigCallback(ConfigChangedCallback callback) {
        synchronized (sConfigCallbacks) {
            sConfigCallbacks.remove(callback);
        }
    }

    public void setActivityConfigCallback(ActivityConfigCallback callback) {
        this.mActivityConfigCallback = callback;
        if (!com.android.window.flags.Flags.activityWindowInfoFlag()) {
            return;
        }
        if (callback == null) {
            this.mPendingActivityWindowInfo = null;
            this.mLastReportedActivityWindowInfo = null;
        } else {
            this.mPendingActivityWindowInfo = new ActivityWindowInfo();
            this.mLastReportedActivityWindowInfo = new ActivityWindowInfo();
        }
    }

    public void setOnContentApplyWindowInsetsListener(
            Window.OnContentApplyWindowInsetsListener listener) {
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
        if (this.mWindowDrawCountDown != null) {
            this.mWindowDrawCountDown.countDown();
        }
    }

    public void profile() {
        this.mProfile = true;
    }

    private boolean isInTouchMode() {
        if (this.mAttachInfo == null) {
            return this.mContext.getResources().getBoolean(R.bool.config_defaultInTouchMode);
        }
        return this.mAttachInfo.mInTouchMode;
    }

    public void notifyChildRebuilt() {
        if (this.mView instanceof RootViewSurfaceTaker) {
            if (this.mSurfaceHolderCallback != null) {
                this.mSurfaceHolder.removeCallback(this.mSurfaceHolderCallback);
            }
            this.mSurfaceHolderCallback =
                    ((RootViewSurfaceTaker) this.mView).willYouTakeTheSurface();
            if (this.mSurfaceHolderCallback != null) {
                this.mSurfaceHolder = new TakenSurfaceHolder();
                this.mSurfaceHolder.setFormat(0);
                this.mSurfaceHolder.addCallback(this.mSurfaceHolderCallback);
            } else {
                this.mSurfaceHolder = null;
            }
            this.mInputQueueCallback =
                    ((RootViewSurfaceTaker) this.mView).willYouTakeTheInputQueue();
            if (this.mInputQueueCallback != null) {
                this.mInputQueueCallback.onInputQueueCreated(this.mInputQueue);
            }
        }
        updateLastConfigurationFromResources(getConfiguration());
        reportNextDraw("rebuilt");
        if (this.mStopped) {
            setWindowStopped(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Configuration getConfiguration() {
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

    public void setView(View view, WindowManager.LayoutParams attrs, View panelParentView) {
        setView(view, attrs, panelParentView, UserHandle.myUserId());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:158:0x04fb A[Catch: all -> 0x0685, TryCatch #3 {all -> 0x0685, blocks: (B:13:0x002d, B:15:0x0066, B:17:0x006c, B:19:0x0072, B:20:0x007a, B:22:0x008a, B:24:0x0097, B:25:0x00aa, B:27:0x00af, B:29:0x00b6, B:31:0x00bc, B:33:0x00c2, B:34:0x00d4, B:36:0x00e8, B:39:0x00f4, B:41:0x00f8, B:43:0x00fd, B:45:0x0102, B:46:0x0112, B:48:0x0116, B:49:0x012e, B:52:0x0141, B:55:0x0151, B:57:0x0155, B:58:0x015d, B:60:0x016a, B:61:0x0170, B:64:0x017b, B:66:0x0183, B:68:0x018d, B:69:0x0192, B:71:0x0198, B:103:0x0240, B:104:0x0243, B:107:0x024c, B:109:0x02b8, B:111:0x02d4, B:112:0x02e6, B:113:0x02e9, B:114:0x043c, B:115:0x0452, B:116:0x02ed, B:117:0x030d, B:118:0x030e, B:119:0x032e, B:120:0x032f, B:121:0x034f, B:122:0x0350, B:123:0x0370, B:124:0x0371, B:126:0x0373, B:127:0x03a1, B:128:0x03a2, B:129:0x03ca, B:130:0x03cb, B:131:0x03eb, B:132:0x03ec, B:133:0x041a, B:134:0x041b, B:135:0x043b, B:136:0x0453, B:138:0x0464, B:139:0x0491, B:141:0x0495, B:143:0x04a0, B:145:0x04a4, B:146:0x04b2, B:148:0x04ca, B:153:0x04d4, B:155:0x04d8, B:156:0x04f5, B:158:0x04fb, B:159:0x0515, B:161:0x051b, B:164:0x0525, B:167:0x052e, B:169:0x0534, B:172:0x053a, B:173:0x053c, B:175:0x0544, B:176:0x054c, B:178:0x0552, B:179:0x0556, B:181:0x055a, B:182:0x0565, B:184:0x0636, B:186:0x067c, B:188:0x063a, B:95:0x0672, B:96:0x0676, B:207:0x014d, B:213:0x0683), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0672 A[Catch: all -> 0x0685, TRY_ENTER, TryCatch #3 {all -> 0x0685, blocks: (B:13:0x002d, B:15:0x0066, B:17:0x006c, B:19:0x0072, B:20:0x007a, B:22:0x008a, B:24:0x0097, B:25:0x00aa, B:27:0x00af, B:29:0x00b6, B:31:0x00bc, B:33:0x00c2, B:34:0x00d4, B:36:0x00e8, B:39:0x00f4, B:41:0x00f8, B:43:0x00fd, B:45:0x0102, B:46:0x0112, B:48:0x0116, B:49:0x012e, B:52:0x0141, B:55:0x0151, B:57:0x0155, B:58:0x015d, B:60:0x016a, B:61:0x0170, B:64:0x017b, B:66:0x0183, B:68:0x018d, B:69:0x0192, B:71:0x0198, B:103:0x0240, B:104:0x0243, B:107:0x024c, B:109:0x02b8, B:111:0x02d4, B:112:0x02e6, B:113:0x02e9, B:114:0x043c, B:115:0x0452, B:116:0x02ed, B:117:0x030d, B:118:0x030e, B:119:0x032e, B:120:0x032f, B:121:0x034f, B:122:0x0350, B:123:0x0370, B:124:0x0371, B:126:0x0373, B:127:0x03a1, B:128:0x03a2, B:129:0x03ca, B:130:0x03cb, B:131:0x03eb, B:132:0x03ec, B:133:0x041a, B:134:0x041b, B:135:0x043b, B:136:0x0453, B:138:0x0464, B:139:0x0491, B:141:0x0495, B:143:0x04a0, B:145:0x04a4, B:146:0x04b2, B:148:0x04ca, B:153:0x04d4, B:155:0x04d8, B:156:0x04f5, B:158:0x04fb, B:159:0x0515, B:161:0x051b, B:164:0x0525, B:167:0x052e, B:169:0x0534, B:172:0x053a, B:173:0x053c, B:175:0x0544, B:176:0x054c, B:178:0x0552, B:179:0x0556, B:181:0x055a, B:182:0x0565, B:184:0x0636, B:186:0x067c, B:188:0x063a, B:95:0x0672, B:96:0x0676, B:207:0x014d, B:213:0x0683), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:98:? A[Catch: all -> 0x0685, SYNTHETIC, TryCatch #3 {all -> 0x0685, blocks: (B:13:0x002d, B:15:0x0066, B:17:0x006c, B:19:0x0072, B:20:0x007a, B:22:0x008a, B:24:0x0097, B:25:0x00aa, B:27:0x00af, B:29:0x00b6, B:31:0x00bc, B:33:0x00c2, B:34:0x00d4, B:36:0x00e8, B:39:0x00f4, B:41:0x00f8, B:43:0x00fd, B:45:0x0102, B:46:0x0112, B:48:0x0116, B:49:0x012e, B:52:0x0141, B:55:0x0151, B:57:0x0155, B:58:0x015d, B:60:0x016a, B:61:0x0170, B:64:0x017b, B:66:0x0183, B:68:0x018d, B:69:0x0192, B:71:0x0198, B:103:0x0240, B:104:0x0243, B:107:0x024c, B:109:0x02b8, B:111:0x02d4, B:112:0x02e6, B:113:0x02e9, B:114:0x043c, B:115:0x0452, B:116:0x02ed, B:117:0x030d, B:118:0x030e, B:119:0x032e, B:120:0x032f, B:121:0x034f, B:122:0x0350, B:123:0x0370, B:124:0x0371, B:126:0x0373, B:127:0x03a1, B:128:0x03a2, B:129:0x03ca, B:130:0x03cb, B:131:0x03eb, B:132:0x03ec, B:133:0x041a, B:134:0x041b, B:135:0x043b, B:136:0x0453, B:138:0x0464, B:139:0x0491, B:141:0x0495, B:143:0x04a0, B:145:0x04a4, B:146:0x04b2, B:148:0x04ca, B:153:0x04d4, B:155:0x04d8, B:156:0x04f5, B:158:0x04fb, B:159:0x0515, B:161:0x051b, B:164:0x0525, B:167:0x052e, B:169:0x0534, B:172:0x053a, B:173:0x053c, B:175:0x0544, B:176:0x054c, B:178:0x0552, B:179:0x0556, B:181:0x055a, B:182:0x0565, B:184:0x0636, B:186:0x067c, B:188:0x063a, B:95:0x0672, B:96:0x0676, B:207:0x014d, B:213:0x0683), top: B:3:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setView(
            android.view.View r26,
            android.view.WindowManager.LayoutParams r27,
            android.view.View r28,
            int r29) {
        /*
            Method dump skipped, instructions count: 1698
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.view.ViewRootImpl.setView(android.view.View,"
                    + " android.view.WindowManager$LayoutParams, android.view.View, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAccessibilityWindowAttributesIfNeeded() {
        boolean registered = this.mAttachInfo.mAccessibilityWindowId != -1;
        if (registered) {
            AccessibilityWindowAttributes attributes =
                    new AccessibilityWindowAttributes(
                            this.mWindowAttributes,
                            this.mContext.getResources().getConfiguration().getLocales());
            if (!attributes.equals(this.mAccessibilityWindowAttributes)) {
                this.mAccessibilityWindowAttributes = attributes;
                this.mAccessibilityManager.setAccessibilityWindowAttributes(
                        getDisplayId(), this.mAttachInfo.mAccessibilityWindowId, attributes);
            }
        }
    }

    private boolean isForceInvertEnabled() {
        if (this.mForceInvertEnabled == Integer.MIN_VALUE) {
            reloadForceInvertEnabled();
        }
        return this.mForceInvertEnabled == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadForceInvertEnabled() {
        if (android.view.accessibility.Flags.forceInvertColor()) {
            this.mForceInvertEnabled =
                    Settings.Secure.getIntForUser(
                            this.mContext.getContentResolver(),
                            Settings.Secure.ACCESSIBILITY_FORCE_INVERT_COLOR_ENABLED,
                            0,
                            UserHandle.myUserId());
        }
    }

    private void registerListeners() {
        if (this.mExtraDisplayListenerLogging) {
            Slog.i(this.mTag, "Register listeners: " + this.mBasePackageName);
        }
        this.mAccessibilityManager.addAccessibilityStateChangeListener(
                this.mAccessibilityInteractionConnectionManager, this.mHandler);
        this.mAccessibilityManager.addHighTextContrastStateChangeListener(
                this.mHighContrastTextManager, this.mHandler);
        DisplayManagerGlobal.getInstance()
                .registerDisplayListener(
                        this.mDisplayListener, this.mHandler, 7L, this.mBasePackageName);
        if (android.view.accessibility.Flags.forceInvertColor()
                && this.mForceInvertObserver == null) {
            this.mForceInvertObserver =
                    new ContentObserver(this.mHandler) { // from class: android.view.ViewRootImpl.2
                        @Override // android.database.ContentObserver
                        public void onChange(boolean selfChange) {
                            ViewRootImpl.this.reloadForceInvertEnabled();
                            ViewRootImpl.this.updateForceDarkMode();
                        }
                    };
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor(
                                    Settings.Secure.ACCESSIBILITY_FORCE_INVERT_COLOR_ENABLED),
                            false,
                            this.mForceInvertObserver,
                            UserHandle.myUserId());
        }
        if (this.mAttachInfo != null
                && this.mDisplay.getState() != this.mAttachInfo.mDisplayState
                && this.mAttachInfo.mDisplay != null
                && this.mAttachInfo.mDisplay.getDisplayId() == this.mDisplay.getDisplayId()) {
            this.mAttachInfo.mDisplayState = this.mDisplay.getState();
            Log.i(
                    this.mTag,
                    "synced displayState. AttachInfo displayState="
                            + this.mAttachInfo.mDisplayState);
        }
    }

    private void unregisterListeners() {
        this.mAccessibilityManager.removeAccessibilityStateChangeListener(
                this.mAccessibilityInteractionConnectionManager);
        this.mAccessibilityManager.removeHighTextContrastStateChangeListener(
                this.mHighContrastTextManager);
        DisplayManagerGlobal.getInstance().unregisterDisplayListener(this.mDisplayListener);
        if (android.view.accessibility.Flags.forceInvertColor()
                && this.mForceInvertObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mForceInvertObserver);
            this.mForceInvertObserver = null;
        }
        if (this.mExtraDisplayListenerLogging) {
            Slog.w(this.mTag, "Unregister listeners: " + this.mBasePackageName, new Throwable());
        }
    }

    private void setTag() {
        String[] split = this.mWindowAttributes.getTitle().toString().split("\\.");
        if (split.length > 0) {
            this.mTag = "VRI[" + split[split.length - 1] + NavigationBarInflaterView.SIZE_MOD_END;
            this.mTag += "@" + Integer.toHexString(this.mWindow.hashCode());
            if (this.mOnBackInvokedDispatcher != null) {
                this.mOnBackInvokedDispatcher.setOwnerTag(this.mTag);
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
                this.mAttachInfo.mHandler.postAtFrontOfQueue(
                        new Runnable() { // from class:
                                         // android.view.ViewRootImpl$$ExternalSyntheticLambda17
                            @Override // java.lang.Runnable
                            public final void run() {
                                ViewRootImpl.this.destroyHardwareResources();
                            }
                        });
            } else {
                renderer.destroyHardwareResources(this.mView);
                renderer.destroy();
            }
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

    public void detachFunctor(long functor) {}

    public static void invokeFunctor(long functor, boolean waitForCompletion) {}

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
            this.mAttachInfo.mThreadedRenderer.registerRtFrameCallback(
                    new HardwareRenderer
                            .FrameDrawingCallback() { // from class: android.view.ViewRootImpl.3
                        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                        public void onFrameDraw(long frame) {}

                        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                        public HardwareRenderer.FrameCommitCallback onFrameDraw(
                                int syncResult, long frame) {
                            try {
                                return callback.onFrameDraw(syncResult, frame);
                            } catch (Exception e) {
                                Log.e(ViewRootImpl.TAG, "Exception while executing onFrameDraw", e);
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
        if (!hardwareAccelerated || CoreRune.GFW_DEBUG_DISABLE_HWRENDERING) {
            return;
        }
        boolean forceHwAccelerated = (attrs.privateFlags & 2) != 0;
        if (ThreadedRenderer.sRendererEnabled || forceHwAccelerated) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.destroy();
            }
            Rect insets = attrs.surfaceInsets;
            boolean hasSurfaceInsets =
                    (insets.left == 0 && insets.right == 0 && insets.top == 0 && insets.bottom == 0)
                            ? false
                            : true;
            boolean translucent = attrs.format != -1 || hasSurfaceInsets;
            ThreadedRenderer renderer =
                    ThreadedRenderer.create(
                            this.mContext, translucent, attrs.getTitle().toString());
            this.mAttachInfo.mThreadedRenderer = renderer;
            renderer.setSurfaceControl(this.mSurfaceControl, this.mBlastBufferQueue);
            updateColorModeIfNeeded(attrs.getColorMode(), attrs.getDesiredHdrHeadroom());
            this.mHdrRenderState.forceUpdateHdrSdrRatio();
            updateForceDarkMode();
            if (ViewRune.COMMON_IS_PRODUCT_DEV) {
                Log.d(this.mTag, "ThreadedRenderer.create() translucent=" + translucent);
            }
            this.mAttachInfo.mHardwareAccelerated = true;
            this.mAttachInfo.mHardwareAccelerationRequested = true;
            if (this.mHardwareRendererObserver != null) {
                renderer.addObserver(this.mHardwareRendererObserver);
            }
        }
    }

    private int getNightMode() {
        return getConfiguration().uiMode & 48;
    }

    public int determineForceDarkType() {
        if (android.view.accessibility.Flags.forceInvertColor() && isForceInvertEnabled()) {
            return 2;
        }
        boolean useAutoDark = getNightMode() == 32;
        if (useAutoDark) {
            boolean forceDarkAllowedDefault =
                    SystemProperties.getBoolean(ThreadedRenderer.DEBUG_FORCE_DARK, false);
            TypedArray a = this.mContext.obtainStyledAttributes(R.styleable.Theme);
            useAutoDark = a.getBoolean(279, true) && a.getBoolean(278, forceDarkAllowedDefault);
            a.recycle();
        }
        return useAutoDark ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForceDarkMode() {
        if (this.mAttachInfo.mThreadedRenderer != null
                && this.mAttachInfo.mThreadedRenderer.setForceDark(determineForceDarkType())) {
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
        synchronized (this) {
            int oldInsetLeft = this.mWindowAttributes.surfaceInsets.left;
            int oldInsetTop = this.mWindowAttributes.surfaceInsets.top;
            int oldInsetRight = this.mWindowAttributes.surfaceInsets.right;
            int oldInsetBottom = this.mWindowAttributes.surfaceInsets.bottom;
            int oldSoftInputMode = this.mWindowAttributes.softInputMode;
            boolean oldHasManualSurfaceInsets = this.mWindowAttributes.hasManualSurfaceInsets;
            if (DEBUG_KEEP_SCREEN_ON
                    && (this.mClientWindowLayoutFlags & 128) != 0
                    && (attrs.flags & 128) == 0) {
                Slog.d(this.mTag, "setLayoutParams: FLAG_KEEP_SCREEN_ON from true to false!");
            }
            if ((this.mWindowAttributes.flags & 512) != 0 && (attrs.flags & 512) == 0) {
                Log.i(this.mTag, "setLayoutParams: set mApplyInsetsRequested = true");
                this.mApplyInsetsRequested = true;
            }
            this.mClientWindowLayoutFlags = attrs.flags;
            int systemUiVisibility = this.mWindowAttributes.systemUiVisibility;
            int subtreeSystemUiVisibility = this.mWindowAttributes.subtreeSystemUiVisibility;
            int appearance = this.mWindowAttributes.insetsFlags.appearance;
            int behavior = this.mWindowAttributes.insetsFlags.behavior;
            int layoutInDisplayCutoutModeFromCaller = adjustLayoutInDisplayCutoutMode(attrs);
            int changes = this.mWindowAttributes.copyFrom(attrs);
            if ((524288 & changes) != 0) {
                this.mAttachInfo.mRecomputeGlobalAttributes = true;
            }
            if ((changes & 1) != 0) {
                this.mAttachInfo.mNeedsUpdateLightCenter = true;
            }
            if ((67108864 & changes) != 0) {
                invalidate();
            }
            if (this.mWindowAttributes.packageName == null) {
                this.mWindowAttributes.packageName = this.mBasePackageName;
            }
            attrs.layoutInDisplayCutoutMode = layoutInDisplayCutoutModeFromCaller;
            this.mWindowAttributes.systemUiVisibility = systemUiVisibility;
            this.mWindowAttributes.subtreeSystemUiVisibility = subtreeSystemUiVisibility;
            this.mWindowAttributes.insetsFlags.appearance = appearance;
            this.mWindowAttributes.insetsFlags.behavior = behavior;
            if (this.mWindowAttributes.preservePreviousSurfaceInsets) {
                this.mWindowAttributes.surfaceInsets.set(
                        oldInsetLeft, oldInsetTop, oldInsetRight, oldInsetBottom);
                this.mWindowAttributes.hasManualSurfaceInsets = oldHasManualSurfaceInsets;
            } else if (this.mWindowAttributes.surfaceInsets.left != oldInsetLeft
                    || this.mWindowAttributes.surfaceInsets.top != oldInsetTop
                    || this.mWindowAttributes.surfaceInsets.right != oldInsetRight
                    || this.mWindowAttributes.surfaceInsets.bottom != oldInsetBottom) {
                this.mNeedsRendererSetup = true;
            }
            applyKeepScreenOnFlag(this.mWindowAttributes);
            if (newView) {
                this.mSoftInputMode = attrs.softInputMode;
                requestLayout();
            }
            if ((attrs.softInputMode & 240) == 0) {
                this.mWindowAttributes.softInputMode =
                        (oldSoftInputMode & 240) | (this.mWindowAttributes.softInputMode & (-241));
            }
            if (this.mWindowAttributes.softInputMode != oldSoftInputMode) {
                requestFitSystemWindows();
            }
            if (DEBUG_TRAVERSAL
                    && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
                Log.i(this.mTag, "Traversal, [1] mView=" + this.mView);
            }
            this.mWindowAttributesChanged = true;
            scheduleTraversals();
            setAccessibilityWindowAttributesIfNeeded();
        }
    }

    private int adjustLayoutInDisplayCutoutMode(WindowManager.LayoutParams attrs) {
        int originalMode = attrs.layoutInDisplayCutoutMode;
        if ((attrs.privateFlags & 264192) != 0
                && attrs.isFullscreen()
                && attrs.getFitInsetsTypes() == 0
                && attrs.getFitInsetsSides() == 0
                && originalMode != 3) {
            attrs.layoutInDisplayCutoutMode = 3;
        }
        return originalMode;
    }

    private boolean isImpossibleRenderer() {
        return this.mSemEarlyAppVisibilityChanged
                && this.mAppVisible
                && this.mStopped
                && !this.mSemEarlyAppVisibility;
    }

    void handleAppVisibility(boolean visible) {
        if (Trace.isTagEnabled(8L)) {
            Trace.instant(
                    8L,
                    TextUtils.formatSimple(
                            "%s visibilityChanged oldVisibility=%b newVisibility=%b",
                            this.mTag,
                            Boolean.valueOf(this.mAppVisible),
                            Boolean.valueOf(visible)));
        }
        this.mSemEarlyAppVisibilityChanged = false;
        Log.i(
                this.mTag,
                "handleAppVisibility mAppVisible = " + this.mAppVisible + " visible = " + visible);
        if (this.mAppVisible != visible) {
            boolean previousVisible = getHostVisibility() == 0;
            this.mAppVisible = visible;
            boolean currentVisible = getHostVisibility() == 0;
            if (DEBUG_TRAVERSAL
                    && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
                Log.i(
                        this.mTag,
                        "Traversal, [2] mView="
                                + this.mView
                                + " visible="
                                + visible
                                + " previousVisible="
                                + previousVisible
                                + " currentVisible="
                                + currentVisible);
            }
            if (previousVisible != currentVisible) {
                Log.d(
                        this.mTag,
                        "visibilityChanged oldVisibility="
                                + previousVisible
                                + " newVisibility="
                                + currentVisible);
                this.mAppVisibilityChanged = true;
                scheduleTraversals();
            }
            if (!this.mRemoved || !this.mAppVisible || !this.mIsDetached) {
                AnimationHandler.requestAnimatorsEnabled(this.mAppVisible, this);
                return;
            }
            Log.v(this.mTag, "handleAppVisibility() enabling visibility when removed");
            if (DEBUG_TRAVERSAL
                    && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
                Log.i(
                        this.mTag,
                        "Traversal, [2] mView="
                                + this.mView
                                + " mAppVisible="
                                + this.mAppVisible
                                + " visible="
                                + visible);
            }
        }
    }

    void handleGetNewSurface() {
        this.mNewSurfaceNeeded = true;
        this.mFullRedrawNeeded = true;
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [3]  mView=" + this.mView);
        }
        scheduleTraversals();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleResized(
            ClientWindowFrames frames,
            boolean reportDraw,
            MergedConfiguration mergedConfiguration,
            InsetsState insetsState,
            boolean forceLayout,
            boolean alwaysConsumeSystemBars,
            int displayId,
            int syncSeqId,
            boolean dragResizing,
            ActivityWindowInfo activityWindowInfo) {
        boolean z;
        Rect attachedFrame;
        if (!this.mAdded) {
            return;
        }
        CompatibilityInfo.applyOverrideScaleIfNeeded(mergedConfiguration);
        Rect frame = frames.frame;
        Rect displayFrame = frames.displayFrame;
        Rect attachedFrame2 = frames.attachedFrame;
        if (this.mTranslator != null) {
            this.mTranslator.translateInsetsStateInScreenToAppWindow(insetsState);
            this.mTranslator.translateRectInScreenToAppWindow(frame);
            this.mTranslator.translateRectInScreenToAppWindow(displayFrame);
            this.mTranslator.translateRectInScreenToAppWindow(attachedFrame2);
        }
        CompatTranslator translator = getCompatTranslator();
        if (translator != null) {
            translator.savePositionInScreen(frames.frame.left, frames.frame.top);
            translator.translateToWindow(frames.frame);
        }
        this.mInsetsController.onStateChanged(insetsState);
        float compatScale = frames.compatScale;
        boolean frameChanged = !this.mWinFrame.equals(frame);
        boolean shouldReportActivityWindowInfoChanged =
                (this.mLastReportedActivityWindowInfo == null
                                || activityWindowInfo == null
                                || this.mLastReportedActivityWindowInfo.equals(activityWindowInfo))
                        ? false
                        : true;
        boolean configChanged =
                !this.mLastReportedMergedConfiguration.equals(mergedConfiguration)
                        || shouldReportActivityWindowInfoChanged;
        boolean attachedFrameChanged =
                !Objects.equals(this.mTmpFrames.attachedFrame, attachedFrame2);
        boolean displayChanged = this.mDisplay.getDisplayId() != displayId;
        boolean compatScaleChanged = this.mTmpFrames.compatScale != compatScale;
        boolean shouldReportActivityWindowInfoChanged2 = this.mPendingDragResizing;
        boolean dragResizingChanged = shouldReportActivityWindowInfoChanged2 != dragResizing;
        Log.i(
                this.mTag,
                "handleResized, frames="
                        + frames
                        + " displayId="
                        + displayId
                        + " dragResizing="
                        + dragResizing
                        + " compatScale="
                        + compatScale
                        + " frameChanged="
                        + frameChanged
                        + " attachedFrameChanged="
                        + attachedFrameChanged
                        + " configChanged="
                        + configChanged
                        + " displayChanged="
                        + displayChanged
                        + " compatScaleChanged="
                        + compatScaleChanged
                        + " dragResizingChanged="
                        + dragResizingChanged);
        if (!reportDraw
                && !frameChanged
                && !configChanged
                && !attachedFrameChanged
                && !displayChanged
                && !forceLayout
                && !compatScaleChanged
                && !dragResizingChanged) {
            return;
        }
        this.mPendingDragResizing = dragResizing;
        this.mTmpFrames.compatScale = compatScale;
        this.mInvCompatScale = 1.0f / compatScale;
        if (configChanged) {
            z = false;
            performConfigurationChange(
                    mergedConfiguration,
                    false,
                    displayChanged ? displayId : -1,
                    activityWindowInfo);
        } else {
            z = false;
            if (displayChanged) {
                onMovedToDisplay(displayId, this.mLastConfigurationFromResources);
            }
        }
        setFrame(frame, z);
        this.mTmpFrames.displayFrame.set(displayFrame);
        if (this.mTmpFrames.attachedFrame != null && attachedFrame2 != null) {
            attachedFrame = attachedFrame2;
            this.mTmpFrames.attachedFrame.set(attachedFrame);
        } else {
            attachedFrame = attachedFrame2;
        }
        if (this.mDragResizing && this.mUseMTRenderer) {
            boolean fullscreen = frame.equals(this.mPendingBackDropFrame);
            int i = this.mWindowCallbacks.size() - 1;
            while (i >= 0) {
                this.mWindowCallbacks
                        .get(i)
                        .onWindowSizeIsChanging(
                                this.mPendingBackDropFrame,
                                fullscreen,
                                this.mAttachInfo.mVisibleInsets,
                                this.mAttachInfo.mStableInsets);
                i--;
                compatScaleChanged = compatScaleChanged;
            }
        }
        this.mForceNextWindowRelayout |= forceLayout;
        this.mPendingAlwaysConsumeSystemBars = alwaysConsumeSystemBars;
        this.mSyncSeqId = syncSeqId > this.mSyncSeqId ? syncSeqId : this.mSyncSeqId;
        Log.i(this.mTag, "handleResized mSyncSeqId = " + this.mSyncSeqId);
        if (reportDraw) {
            reportNextDraw("resized");
        }
        if (this.mView != null && (frameChanged || configChanged)) {
            forceLayout(this.mView);
        }
        requestLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInsetsControlChanged(
            InsetsState insetsState, InsetsSourceControl.Array activeControls) {
        InsetsSourceControl[] controls = activeControls.get();
        if (this.mTranslator != null) {
            this.mTranslator.translateInsetsStateInScreenToAppWindow(insetsState);
            this.mTranslator.translateSourceControlsInScreenToAppWindow(controls);
        }
        this.mInsetsController.onStateChanged(insetsState);
        if (this.mAdded) {
            this.mInsetsController.onControlsChanged(controls);
        } else {
            activeControls.release();
        }
    }

    public void onMovedToDisplay(int displayId, Configuration config) {
        if (this.mDisplay.getDisplayId() != displayId && this.mView != null) {
            updateInternalDisplay(displayId, this.mView.getResources());
            this.mImeFocusController.onMovedToDisplay();
            this.mAttachInfo.mDisplayState = this.mDisplay.getState();
            updateDesktopMode();
            this.mView.dispatchMovedToDisplay(this.mDisplay, config);
        }
    }

    private void updateInternalDisplay(int displayId, Resources resources) {
        Display preferredDisplay =
                ResourcesManager.getInstance().getAdjustedDisplay(displayId, resources);
        this.mHdrRenderState.stopListening();
        if (preferredDisplay == null) {
            Slog.w(TAG, "Cannot get desired display with Id: " + displayId);
            this.mDisplay = ResourcesManager.getInstance().getAdjustedDisplay(0, resources);
        } else {
            this.mDisplay = preferredDisplay;
        }
        this.mHdrRenderState.startListening();
        this.mContext.updateDisplay(this.mDisplay.getDisplayId());
    }

    void pokeDrawLockIfNeeded() {
        if (Display.isDozeState(this.mAttachInfo.mDisplayState)
                && this.mWindowAttributes.type == 1
                && this.mAdded
                && this.mTraversalScheduled
                && this.mAttachInfo.mHasWindowFocus) {
            try {
                this.mWindowSession.pokeDrawLock(this.mWindow);
            } catch (RemoteException e) {
            }
        }
    }

    @Override // android.view.ViewParent
    public void requestFitSystemWindows() {
        checkThread();
        this.mApplyInsetsRequested = true;
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [5] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    void notifyInsetsChanged() {
        InsetsSource imeSource = this.mInsetsController.getState().peekSource(InsetsSource.ID_IME);
        WindowConfiguration winConfig = getConfiguration().windowConfiguration;
        boolean isBottomSplit =
                winConfig.isSplitScreen() && (winConfig.getStagePosition() & 64) != 0;
        if ((winConfig.getWindowingMode() == 5 || isBottomSplit)
                && imeSource != null
                && imeSource.isVisible()
                && !imeSource.getFrame().isEmpty()
                && imeSource.getFrame().top < this.mWinFrame.top) {
            return;
        }
        this.mApplyInsetsRequested = true;
        requestLayout();
        if (View.sForceLayoutWhenInsetsChanged
                && this.mView != null
                && (this.mWindowAttributes.softInputMode & 240) == 16) {
            forceLayout(this.mView);
        }
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(
                    this.mTag,
                    "Traversal, [6] mView="
                            + this.mView
                            + " mIsInTraversal="
                            + this.mIsInTraversal);
        }
        if (!this.mIsInTraversal) {
            scheduleTraversals();
        }
    }

    public void notifyInsetsAnimationRunningStateChanged(boolean running) {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            this.mInsetsAnimationRunning = running;
        }
    }

    @Override // android.view.ViewParent
    public void requestLayout() {
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(
                    this.mTag,
                    "Traversal, [7] mView="
                            + this.mView
                            + " mHandlingLayoutInLayoutRequest="
                            + this.mHandlingLayoutInLayoutRequest);
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
        if (sToolkitEnableInvalidateCheckThreadFlagValue) {
            checkThread();
        }
        if ((descendant.mPrivateFlags & 64) != 0) {
            this.mIsAnimating = true;
        }
        invalidate();
    }

    void invalidate() {
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(
                    this.mTag,
                    "Traversal, [8] mView=" + this.mView + " mWillDrawSoon=" + this.mWillDrawSoon);
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
            Log.v(this.mTag, "Invalidate child: " + dirty);
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
            if (this.mCurScrollY != 0) {
                dirty.offset(0, -this.mCurScrollY);
            }
            if (this.mTranslator != null) {
                this.mTranslator.translateRectInAppWindowToScreen(dirty);
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
            Log.v(this.mTag, "invalidateRectOnScreen: " + dirty);
        }
        Rect localDirty = this.mDirty;
        localDirty.union(dirty.left, dirty.top, dirty.right, dirty.bottom);
        float appScale = this.mAttachInfo.mApplicationScale;
        boolean intersected =
                localDirty.intersect(
                        0,
                        0,
                        (int) ((this.mWidth * appScale) + 0.5f),
                        (int) ((this.mHeight * appScale) + 0.5f));
        if (!intersected) {
            localDirty.setEmpty();
        }
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(
                    this.mTag,
                    "Traversal, [9] mView="
                            + this.mView
                            + " mWillDrawSoon="
                            + this.mWillDrawSoon
                            + " intersected="
                            + intersected
                            + " mIsAnimating="
                            + this.mIsAnimating);
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
        Log.i(this.mTag, "stopped(" + stopped + ") old = " + this.mStopped);
        checkThread();
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(
                    this.mTag,
                    "Traversal, [10] mView="
                            + this.mView
                            + " mStopped="
                            + this.mStopped
                            + " stopped="
                            + stopped);
        }
        if (this.mStopped != stopped) {
            this.mStopped = stopped;
            ThreadedRenderer renderer = this.mAttachInfo.mThreadedRenderer;
            if (renderer != null) {
                Log.d(
                        this.mTag,
                        "WindowStopped on " + ((Object) getTitle()) + " set to " + this.mStopped);
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
            this.mAppStartTimestampsSent.set(false);
            this.mAppStartTrackingStarted = false;
            this.mRenderThreadDrawStartTimeNs = -1L;
            this.mFirstFramePresentedTimeNs = -1L;
        }
    }

    public interface SurfaceChangedCallback {
        void surfaceCreated(SurfaceControl.Transaction transaction);

        void surfaceDestroyed();

        void surfaceReplaced(SurfaceControl.Transaction transaction);

        default void vriDrawStarted(boolean isWmSync) {}
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

    public SurfaceControl updateAndGetBoundsLayer(SurfaceControl.Transaction t) {
        if (this.mBoundsLayer == null) {
            WindowConfiguration winConfig =
                    this.mContext.getResources().getConfiguration().windowConfiguration;
            boolean needBoundsLayer =
                    winConfig.getWindowingMode() == 5
                            && this.mWindowAttributes.type == 1
                            && this.mWindowAttributes.isFullscreen();
            this.mBoundsLayer =
                    new SurfaceControl.Builder(this.mSurfaceSession)
                            .setContainerLayer()
                            .setName(
                                    "Bounds for - "
                                            + getTitle().toString()
                                            + "@"
                                            + this.mBoundsLayerCreatedCount)
                            .setParent(getSurfaceControl())
                            .setColorLayer()
                            .setCallsite("ViewRootImpl.getBoundsLayer")
                            .build();
            setBoundsLayerCrop(t);
            if (!needBoundsLayer) {
                t.unsetColor(this.mBoundsLayer);
                this.mIsBoundsColorLayer = false;
            } else {
                t.setColor(this.mBoundsLayer, new float[] {0.0f, 0.0f, 0.0f});
                t.setLayer(this.mBoundsLayer, -3);
                this.mIsBoundsColorLayer = true;
            }
            t.show(this.mBoundsLayer);
            this.mBoundsLayerCreatedCount++;
        }
        return this.mBoundsLayer;
    }

    void updateBlastSurfaceIfNeeded() {
        Surface blastSurface;
        if (!this.mSurfaceControl.isValid()) {
            return;
        }
        if (this.mBlastBufferQueue != null
                && this.mBlastBufferQueue.isSameSurfaceControl(this.mSurfaceControl)) {
            this.mBlastBufferQueue.update(
                    this.mSurfaceControl,
                    this.mSurfaceSize.x,
                    this.mSurfaceSize.y,
                    this.mWindowAttributes.format);
            return;
        }
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.destroy();
        }
        this.mBlastBufferQueue =
                new BLASTBufferQueue(
                        this.mTag,
                        this.mSurfaceControl,
                        this.mSurfaceSize.x,
                        this.mSurfaceSize.y,
                        this.mWindowAttributes.format);
        this.mBlastBufferQueue.setTransactionHangCallback(sTransactionHangCallback);
        if (Flags.addSchandleToVriSurface()) {
            blastSurface = this.mBlastBufferQueue.createSurfaceWithHandle();
        } else {
            blastSurface = this.mBlastBufferQueue.createSurface();
        }
        this.mSurface.transferFrom(blastSurface);
    }

    private void setBoundsLayerCrop(SurfaceControl.Transaction t) {
        if (this.mWinFrame.isEmpty()) {
            Log.i(this.mTag, "setBoundsLayerCrop, frame is empty");
            return;
        }
        this.mTempRect.set(0, 0, this.mSurfaceSize.x, this.mSurfaceSize.y);
        this.mTempRect.inset(
                this.mWindowAttributes.surfaceInsets.left,
                this.mWindowAttributes.surfaceInsets.top,
                this.mWindowAttributes.surfaceInsets.right,
                this.mWindowAttributes.surfaceInsets.bottom);
        this.mTempRect.inset(
                this.mChildBoundingInsets.left,
                this.mChildBoundingInsets.top,
                this.mChildBoundingInsets.right,
                this.mChildBoundingInsets.bottom);
        t.setWindowCrop(this.mBoundsLayer, this.mTempRect);
    }

    private boolean updateBoundsLayer(SurfaceControl.Transaction t) {
        if (this.mBoundsLayer == null) {
            return false;
        }
        setBoundsLayerCrop(t);
        Log.i(
                this.mTag,
                "updateBoundsLayer: t="
                        + t
                        + " sc="
                        + this.mBoundsLayer
                        + " frame="
                        + this.mSurface.getNextFrameNumber());
        if (this.mAttachInfo.mDisplayState == 1) {
            this.mDeferTransactionRequested = true;
            Log.i(
                    this.mTag,
                    "updateBoundsLayer: set mDeferTransactionRequested="
                            + this.mDeferTransactionRequested);
        }
        WindowConfiguration winConfig =
                this.mContext.getResources().getConfiguration().windowConfiguration;
        boolean isFreeform = winConfig.getWindowingMode() == 5;
        if (this.mWindowAttributes.type == 1 && this.mWindowAttributes.isFullscreen()) {
            if (!this.mIsWindowOpaque && isFreeform) {
                t.setLayer(this.mBoundsLayer, 0);
                t.unsetColor(this.mBoundsLayer);
                this.mIsBoundsColorLayer = false;
            } else if (!this.mIsBoundsColorLayer && isFreeform) {
                t.setLayer(this.mBoundsLayer, -3);
                t.setColor(this.mBoundsLayer, new float[] {0.0f, 0.0f, 0.0f});
                this.mIsBoundsColorLayer = true;
            } else if (this.mIsBoundsColorLayer && !isFreeform) {
                t.setLayer(this.mBoundsLayer, 0);
                t.unsetColor(this.mBoundsLayer);
                this.mIsBoundsColorLayer = false;
            }
        }
        this.mFullRedrawNeeded = true;
        return true;
    }

    private void prepareSurfaces() {
        SurfaceControl.Transaction t = this.mTransaction;
        SurfaceControl sc = getSurfaceControl();
        if (sc.isValid()) {
            if (updateBoundsLayer(t)) {
                applyTransactionOnDraw(t);
            }
            if (shouldEnableDvrr()) {
                try {
                    if (sToolkitFrameRateFunctionEnablingReadOnlyFlagValue) {
                        this.mFrameRateTransaction
                                .setFrameRateSelectionStrategy(sc, 2)
                                .applyAsyncUnsafe();
                    }
                } catch (Exception e) {
                    Log.e(this.mTag, "Unable to set frame rate selection strategy ", e);
                }
            }
            if (CoreRune.FW_VRR_SEND_TOUCH_HINT) {
                try {
                    this.mTouchHintTransaction
                            .setFrameRateSelectionStrategy(sc, 2)
                            .applyAsyncUnsafe();
                } catch (Exception e2) {
                    Log.e(this.mTag, "Unable to set frame rate selection strategy ", e2);
                }
            }
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
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.destroy();
            this.mBlastBufferQueue = null;
        }
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.setSurfaceControl(null, null);
        }
        this.mPreferredFrameRateCategory = 0;
        this.mLastPreferredFrameRateCategory = 0;
        this.mPreferredFrameRate = 0.0f;
        this.mLastPreferredFrameRate = 0.0f;
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
    public boolean getChildLocalHitRegion(
            View child, Region region, Matrix matrix, boolean isHover) {
        if (child != this.mView) {
            throw new IllegalArgumentException(
                    "child "
                            + child
                            + " is not the root view "
                            + this.mView
                            + " managed by this ViewRootImpl");
        }
        RectF rectF = new RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
        matrix.mapRect(rectF);
        return region.op(
                Math.round(rectF.left),
                Math.round(rectF.top),
                Math.round(rectF.right),
                Math.round(rectF.bottom),
                Region.Op.INTERSECT);
    }

    @Override // android.view.ViewParent
    public void bringChildToFront(View child) {}

    int getHostVisibility() {
        if (this.mView == null || !(this.mAppVisible || this.mForceDecorViewVisibility)) {
            return 8;
        }
        return this.mView.getVisibility();
    }

    String getHostVisibilityReason() {
        if (this.mView == null) {
            return "mView is null";
        }
        if (!this.mAppVisible && !this.mForceDecorViewVisibility) {
            return "!mAppVisible && !mForceDecorViewVisibility";
        }
        switch (this.mView.getVisibility()) {
            case 0:
                return "View.VISIBLE";
            case 4:
                return "View.INVISIBLE";
            case 8:
                return "View.GONE";
            default:
                return "";
        }
    }

    public void requestTransitionStart(LayoutTransition transition) {
        if (this.mPendingTransitions == null || !this.mPendingTransitions.contains(transition)) {
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
        if (sSafeScheduleTraversals) {
            checkThread();
        }
        if (!this.mTraversalScheduled) {
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
            params.flags =
                    (params.flags & PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE)
                            | (this.mClientWindowLayoutFlags & 128);
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
            this.mCompatibleVisibilityInfo.globalVisibility =
                    (this.mCompatibleVisibilityInfo.globalVisibility & (-2))
                            | (this.mAttachInfo.mSystemUiVisibility & 1);
            dispatchDispatchSystemUiVisibilityChanged();
            if (this.mAttachInfo.mKeepScreenOn != oldScreenOn
                    || this.mAttachInfo.mSystemUiVisibility != params.subtreeSystemUiVisibility
                    || this.mAttachInfo.mHasSystemUiListeners != params.hasSystemUiListeners) {
                applyKeepScreenOnFlag(params);
                params.subtreeSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                params.hasSystemUiListeners = this.mAttachInfo.mHasSystemUiListeners;
                this.mView.dispatchWindowSystemUiVisiblityChanged(
                        this.mAttachInfo.mSystemUiVisibility);
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

    void updateCompatSysUiVisibility(
            int visibleTypes, int requestedVisibleTypes, int controllableTypes) {
        int visibleTypes2 =
                (requestedVisibleTypes & controllableTypes) | ((~controllableTypes) & visibleTypes);
        updateCompatSystemUiVisibilityInfo(
                4, WindowInsets.Type.statusBars(), visibleTypes2, controllableTypes);
        updateCompatSystemUiVisibilityInfo(
                2, WindowInsets.Type.navigationBars(), visibleTypes2, controllableTypes);
        dispatchDispatchSystemUiVisibilityChanged();
    }

    private void updateCompatSystemUiVisibilityInfo(
            int systemUiFlag, int insetsType, int visibleTypes, int controllableTypes) {
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
        if ((WindowInsets.Type.systemBars() & showTypes) != 0
                && !fromIme
                && (info.globalVisibility & 1) != 0) {
            info.globalVisibility &= -2;
            info.localChanges |= 1;
            dispatchDispatchSystemUiVisibilityChanged();
        }
    }

    private void dispatchDispatchSystemUiVisibilityChanged() {
        if (this.mDispatchedSystemUiVisibility != this.mCompatibleVisibilityInfo.globalVisibility) {
            this.mHandler.removeMessages(17);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(17));
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

    public static void adjustLayoutParamsForCompatibility(
            WindowManager.LayoutParams inOutParams,
            int appearanceControlled,
            boolean behaviorControlled) {
        int i;
        int i2;
        int sysUiVis = inOutParams.systemUiVisibility | inOutParams.subtreeSystemUiVisibility;
        int flags = inOutParams.flags;
        int type = inOutParams.type;
        int adjust = inOutParams.softInputMode & 240;
        int appearance = inOutParams.insetsFlags.appearance;
        if ((appearanceControlled & 4) == 0) {
            int appearance2 = appearance & (-5);
            if ((sysUiVis & 1) != 0) {
                i2 = 4;
            } else {
                i2 = 0;
            }
            appearance = appearance2 | i2;
        }
        if ((appearanceControlled & 8) == 0) {
            int appearance3 = appearance & (-9);
            if ((sysUiVis & 8192) != 0) {
                i = 8;
            } else {
                i = 0;
            }
            appearance = appearance3 | i;
        }
        if ((appearanceControlled & 16) == 0) {
            appearance = (appearance & (-17)) | ((sysUiVis & 16) != 0 ? 16 : 0);
        }
        inOutParams.insetsFlags.appearance = appearance;
        if (!behaviorControlled) {
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
        if ((sysUiVis & 512) != 0 || (134217728 & flags) != 0) {
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
        boolean captionIsHiddenByFlags = false;
        boolean matchParent = params.width == -1 && params.height == -1;
        boolean nonAttachedAppWindow = params.type >= 1 && params.type <= 99;
        boolean statusWasHiddenByFlags =
                (this.mTypesHiddenByFlags & WindowInsets.Type.statusBars()) != 0;
        boolean statusIsHiddenByFlags =
                (sysUiVis & 4) != 0 || ((flags & 1024) != 0 && matchParent && nonAttachedAppWindow);
        boolean navWasHiddenByFlags =
                (this.mTypesHiddenByFlags & WindowInsets.Type.navigationBars()) != 0;
        boolean navIsHiddenByFlags = (sysUiVis & 2) != 0;
        boolean captionWasHiddenByFlags =
                (this.mTypesHiddenByFlags & WindowInsets.Type.captionBar()) != 0;
        if ((sysUiVis & 4) != 0 || ((flags & 1024) != 0 && matchParent && nonAttachedAppWindow)) {
            captionIsHiddenByFlags = true;
        }
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
        if (captionIsHiddenByFlags && !captionWasHiddenByFlags) {
            typesToHide |= WindowInsets.Type.captionBar();
        } else if (!captionIsHiddenByFlags && captionWasHiddenByFlags) {
            typesToShow |= WindowInsets.Type.captionBar();
        }
        if (typesToHide != 0) {
            getInsetsController().hide(typesToHide);
        }
        if (typesToShow != 0) {
            getInsetsController().show(typesToShow);
        }
        this.mTypesHiddenByFlags |= typesToHide;
        this.mTypesHiddenByFlags &= ~typesToShow;
    }

    private boolean measureHierarchy(
            View host,
            WindowManager.LayoutParams lp,
            Resources res,
            int desiredWindowWidth,
            int desiredWindowHeight,
            boolean forRootSizeOnly) {
        boolean windowSizeMayChange;
        if (DEBUG_ORIENTATION || DEBUG_LAYOUT) {
            Log.v(
                    this.mTag,
                    "Measuring "
                            + host
                            + " in display "
                            + desiredWindowWidth
                            + "x"
                            + desiredWindowHeight
                            + Session.TRUNCATE_STRING);
        }
        boolean goodMeasure = false;
        if (lp.width != -2) {
            windowSizeMayChange = false;
        } else {
            DisplayMetrics packageMetrics = res.getDisplayMetrics();
            if (this.mIsDeviceDefault) {
                if (lp.type == 2005) {
                    res.getValue(R.dimen.sem_config_prefToastWidth, this.mTmpValue, true);
                } else {
                    res.getValue(R.dimen.sem_config_prefDialogWidth, this.mTmpValue, true);
                }
            } else {
                res.getValue(R.dimen.config_prefDialogWidth, this.mTmpValue, true);
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
                    baseSize =
                            (int)
                                    this.mTmpValue.getFraction(
                                            packageMetrics.widthPixels, packageMetrics.widthPixels);
                }
            }
            if (DEBUG_DIALOG) {
                Log.v(
                        this.mTag,
                        "Window "
                                + this.mView
                                + ": baseSize="
                                + baseSize
                                + ", desiredWindowWidth="
                                + desiredWindowWidth);
            }
            if (baseSize == 0 || desiredWindowWidth <= baseSize) {
                windowSizeMayChange = false;
            } else {
                int childWidthMeasureSpec = getRootMeasureSpec(baseSize, lp.width, lp.privateFlags);
                int childHeightMeasureSpec =
                        getRootMeasureSpec(desiredWindowHeight, lp.height, lp.privateFlags);
                performMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
                if (DEBUG_DIALOG) {
                    windowSizeMayChange = false;
                    Log.v(
                            this.mTag,
                            "Window "
                                    + this.mView
                                    + ": measured ("
                                    + host.getMeasuredWidth()
                                    + ","
                                    + host.getMeasuredHeight()
                                    + ") from width spec: "
                                    + View.MeasureSpec.toString(childWidthMeasureSpec)
                                    + " and height spec: "
                                    + View.MeasureSpec.toString(childHeightMeasureSpec));
                } else {
                    windowSizeMayChange = false;
                }
                if ((host.getMeasuredWidthAndState() & 16777216) == 0) {
                    goodMeasure = true;
                } else {
                    int baseSize2 = (baseSize + desiredWindowWidth) / 2;
                    if (DEBUG_DIALOG) {
                        Log.v(this.mTag, "Window " + this.mView + ": next baseSize=" + baseSize2);
                    }
                    performMeasure(
                            getRootMeasureSpec(baseSize2, lp.width, lp.privateFlags),
                            childHeightMeasureSpec);
                    if (DEBUG_DIALOG) {
                        Log.v(
                                this.mTag,
                                "Window "
                                        + this.mView
                                        + ": measured ("
                                        + host.getMeasuredWidth()
                                        + ","
                                        + host.getMeasuredHeight()
                                        + NavigationBarInflaterView.KEY_CODE_END);
                    }
                    if ((host.getMeasuredWidthAndState() & 16777216) == 0) {
                        if (DEBUG_DIALOG) {
                            Log.v(this.mTag, "Good!");
                        }
                        goodMeasure = true;
                    }
                }
            }
        }
        if (!goodMeasure) {
            int childWidthMeasureSpec2 =
                    getRootMeasureSpec(desiredWindowWidth, lp.width, lp.privateFlags);
            int childHeightMeasureSpec2 =
                    getRootMeasureSpec(desiredWindowHeight, lp.height, lp.privateFlags);
            if (forRootSizeOnly
                    && setMeasuredRootSizeFromSpec(
                            childWidthMeasureSpec2, childHeightMeasureSpec2)) {
                this.mViewMeasureDeferred = true;
            } else {
                performMeasure(childWidthMeasureSpec2, childHeightMeasureSpec2);
            }
            if (this.mWidth != host.getMeasuredWidth()
                    || this.mHeight != host.getMeasuredHeight()) {
                return true;
            }
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
            this.mLastWindowInsets =
                    this.mInsetsController.calculateInsets(
                            config.isScreenRound(),
                            this.mWindowAttributes.type,
                            config.windowConfiguration.getActivityType(),
                            this.mWindowAttributes.softInputMode,
                            this.mWindowAttributes.flags,
                            this.mWindowAttributes.systemUiVisibility
                                    | this.mWindowAttributes.subtreeSystemUiVisibility);
            if (this.mIsCutoutRemoveNeeded || removeCutout) {
                WindowInsets insets = this.mLastWindowInsets.removeCutoutInsets();
                if (this.mIsCutoutRemoveNeeded) {
                    this.mLastWindowInsets = insets;
                } else {
                    this.mAttachInfo.mContentInsets.set(insets.getSystemWindowInsets().toRect());
                    this.mAttachInfo.mStableInsets.set(insets.getStableInsets().toRect());
                    this.mAttachInfo.mVisibleInsets.set(
                            this.mInsetsController
                                    .calculateVisibleInsets(
                                            this.mWindowAttributes.type,
                                            config.windowConfiguration.getWindowingMode(),
                                            this.mWindowAttributes.softInputMode,
                                            this.mWindowAttributes.flags)
                                    .toRect());
                    return insets;
                }
            }
            this.mAttachInfo.mContentInsets.set(
                    this.mLastWindowInsets.getSystemWindowInsets().toRect());
            this.mAttachInfo.mStableInsets.set(this.mLastWindowInsets.getStableInsets().toRect());
            this.mAttachInfo.mVisibleInsets.set(
                    this.mInsetsController
                            .calculateVisibleInsets(
                                    this.mWindowAttributes.type,
                                    config.windowConfiguration.getActivityType(),
                                    this.mWindowAttributes.softInputMode,
                                    this.mWindowAttributes.flags)
                            .toRect());
        }
        return this.mLastWindowInsets;
    }

    public void dispatchApplyInsets(View host) {
        Trace.traceBegin(8L, "dispatchApplyInsets");
        this.mApplyInsetsRequested = false;
        boolean splitImmersiveMode =
                MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED
                        && getCompatWindowConfiguration().isSplitScreen();
        WindowInsets insets =
                getWindowInsets(true, this.mIsCutoutRemoveForDispatchNeeded || splitImmersiveMode);
        if (!shouldDispatchCutout()) {
            insets = insets.consumeDisplayCutout();
        }
        if (CoreRune.MW_CAPTION_SHELL_INSETS
                && (host instanceof DecorView)
                && ((DecorView) host).shouldConsumeCaptionInsets(insets)) {
            insets = insets.consumeCaptionInsets();
        }
        if (DEBUG_WINDOW_INSETS) {
            Log.i(this.mTag, "dispatchApplyInsets : " + insets);
        }
        if (host instanceof DecorView) {
            ((DecorView) host).updateCaptionHeightIfNeeded(insets);
        }
        InsetsSource imeSource = this.mInsetsController.getState().peekSource(InsetsSource.ID_IME);
        if (imeSource != null
                && imeSource.isVisible()
                && getConfiguration().windowConfiguration.isPopOver()) {
            this.mForceNextWindowRelayout = true;
        }
        host.dispatchApplyWindowInsets(insets);
        this.mAttachInfo.delayNotifyContentCaptureInsetsEvent(
                insets.getInsets(WindowInsets.Type.all()));
        Trace.traceEnd(8L);
    }

    private boolean shouldDispatchCutout() {
        return this.mWindowAttributes.layoutInDisplayCutoutMode == 3
                || this.mWindowAttributes.layoutInDisplayCutoutMode == 1;
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
        Rect bounds =
                new Rect(
                        this.mContext
                                .getResources()
                                .getConfiguration()
                                .windowConfiguration
                                .getBounds());
        bounds.inset(
                this.mInsetsController
                        .getState()
                        .calculateInsets(
                                bounds,
                                WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(),
                                false));
        return bounds;
    }

    int dipToPx(int dip) {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        return (int) ((displayMetrics.density * dip) + 0.5f);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(196:22|(1:1044)(1:30)|31|(4:33|(1:35)(1:1042)|(1:37)(1:1041)|(188:39|40|(6:42|(1:44)(2:1027|(1:1032)(1:1031))|45|(1:47)|48|(1:52))(2:1033|(3:1037|(1:1039)|1040))|53|(5:55|(2:(1:58)(1:60)|59)|(1:68)|64|(1:67))|69|(1:71)|72|(1:74)|75|(1:1026)(1:81)|82|(3:84|(1:1024)(2:90|(1:92)(1:1023))|93)(1:1025)|94|(1:96)|97|(1:99)|100|(2:1006|(6:1008|(3:1010|(2:1012|1013)(1:1015)|1014)|1016|(1:1018)|1019|(1:1021))(1:1022))(1:104)|105|(2:107|(1:109)(1:1004))(1:1005)|(1:111)|(1:1003)(159:114|(1:1002)(2:118|(2:994|(2:996|(1:998))(1:1001))(1:124))|125|126|(1:993)(1:130)|131|(1:992)(1:135)|136|(1:138)(1:991)|139|(1:141)(1:990)|(4:143|(1:147)|148|(1:150))(1:989)|151|(1:988)(2:156|(1:158)(47:987|366|(1:368)|369|(1:682)(4:373|374|(1:376)|378)|(1:679)|(1:678)(1:392)|(1:677)(1:396)|(2:398|(8:400|(1:674)|404|(1:406)|407|(1:409)|410|(2:412|(1:414)))(1:675))(1:676)|415|(1:417)(1:(1:671)(1:(1:673)))|(1:419)|(1:421)|422|(3:424|(5:660|(1:662)(1:668)|663|(1:665)(1:667)|666)(1:428)|429)(1:669)|430|(1:659)(1:434)|435|(7:437|(4:439|(1:441)|442|(1:444))(1:647)|(1:446)(1:646)|(1:448)|(1:450)(1:(1:645))|451|452)(2:648|(3:652|653|654))|453|(1:637)(8:455|(4:630|(1:634)|473|(1:475))|460|(1:462)|463|(2:465|(2:467|(1:469))(2:470|(1:472)))|473|(0))|(2:627|(20:629|(1:482)|483|(1:626)(1:486)|487|(1:489)|(1:625)(1:492)|493|(1:624)(1:498)|(1:623)(4:500|(1:502)(1:622)|503|(1:507))|(1:621)|511|(1:515)|(5:517|(1:519)(1:581)|520|(4:524|(2:527|525)|528|529)|530)(2:582|(4:584|(3:586|(1:592)(1:590)|591)|(1:594)(1:596)|595)(8:597|(1:599)|600|(1:602)|603|(4:607|(2:610|608)|611|612)|613|(1:615)))|531|(1:533)|534|(4:536|(1:538)(1:542)|539|(1:541))|543|(14:545|(1:549)|550|(1:575)(1:556)|557|(1:559)(1:574)|560|(1:562)(1:573)|563|(1:565)(1:572)|566|(1:568)(1:571)|569|570)(2:576|(2:578|579)(1:580))))(1:479)|480|(0)|483|(0)|626|487|(0)|(0)|625|493|(1:496)|624|(0)(0)|(0)|616|621|511|(2:513|515)|(0)(0)|531|(0)|534|(0)|543|(0)(0)))|159|(3:161|(1:163)(1:985)|164)(1:986)|165|(1:984)(1:169)|170|(1:172)|173|174|175|(9:177|178|179|180|181|182|183|184|185)(1:978)|186|187|(1:958)|190|191|(1:193)(1:956)|194|195|196|197|198|199|200|201|202|(5:935|936|(1:938)|939|(1:941))|204|(1:206)(1:934)|207|(7:898|899|(1:903)|(1:905)|(1:928)(4:914|915|916|917)|918|(3:922|(1:924)(1:926)|925))(1:209)|210|211|(1:213)|214|(1:893)(1:218)|219|(1:891)(9:222|223|(1:225)(1:886)|226|(1:228)(1:880)|229|(4:231|232|233|234)(1:879)|235|236)|237|238|(2:240|241)|242|(1:244)(1:869)|245|(1:868)(1:249)|250|(2:252|(97:254|255|256|(2:862|(93:864|260|(6:262|263|264|265|266|267)(1:861)|(2:269|270)(1:851)|(3:840|841|(1:843))|272|273|274|275|276|(4:278|279|280|(89:721|722|723|724|725|726|727|728|729|730|731|732|733|734|735|736|(1:740)|742|284|(1:(5:287|(1:297)(1:291)|292|(1:294)(1:296)|295)(1:298))|299|(1:(1:302)(1:303))|304|(1:306)|307|308|(1:310)|311|(1:720)|315|(6:317|(1:319)|320|(2:322|(3:324|(1:326)|327))|(2:707|(3:709|(4:711|(1:713)|714|715)(1:717)|716)(1:718))(1:332)|(4:334|335|336|337))(1:719)|342|(3:353|(1:359)(1:357)|358)|360|(8:689|(1:691)|692|(1:694)(1:706)|695|(1:697)|(1:705)(3:699|(1:701)(1:704)|702)|703)(1:364)|365|366|(0)|369|(1:371)|682|(0)|679|(2:388|390)|678|(1:394)|677|(0)(0)|415|(0)(0)|(0)|(0)|422|(0)(0)|430|(1:432)|659|435|(0)(0)|453|(0)(0)|(1:477)|627|(0)|480|(0)|483|(0)|626|487|(0)|(0)|625|493|(0)|624|(0)(0)|(0)|616|621|511|(0)|(0)(0)|531|(0)|534|(0)|543|(0)(0))(1:282))(3:791|792|(8:794|(1:796)|797|(1:799)|800|(1:802)|803|(1:805))(1:(3:815|816|817)))|283|284|(0)|299|(0)|304|(0)|307|308|(0)|311|(1:313)|720|315|(0)(0)|342|(5:344|353|(1:355)|359|358)|360|(1:362)|683|689|(0)|692|(0)(0)|695|(0)|(0)(0)|703|365|366|(0)|369|(0)|682|(0)|679|(0)|678|(0)|677|(0)(0)|415|(0)(0)|(0)|(0)|422|(0)(0)|430|(0)|659|435|(0)(0)|453|(0)(0)|(0)|627|(0)|480|(0)|483|(0)|626|487|(0)|(0)|625|493|(0)|624|(0)(0)|(0)|616|621|511|(0)|(0)(0)|531|(0)|534|(0)|543|(0)(0)))|259|260|(0)(0)|(0)(0)|(0)|272|273|274|275|276|(0)(0)|283|284|(0)|299|(0)|304|(0)|307|308|(0)|311|(0)|720|315|(0)(0)|342|(0)|360|(0)|683|689|(0)|692|(0)(0)|695|(0)|(0)(0)|703|365|366|(0)|369|(0)|682|(0)|679|(0)|678|(0)|677|(0)(0)|415|(0)(0)|(0)|(0)|422|(0)(0)|430|(0)|659|435|(0)(0)|453|(0)(0)|(0)|627|(0)|480|(0)|483|(0)|626|487|(0)|(0)|625|493|(0)|624|(0)(0)|(0)|616|621|511|(0)|(0)(0)|531|(0)|534|(0)|543|(0)(0)))|867|255|256|(0)|862|(0)|259|260|(0)(0)|(0)(0)|(0)|272|273|274|275|276|(0)(0)|283|284|(0)|299|(0)|304|(0)|307|308|(0)|311|(0)|720|315|(0)(0)|342|(0)|360|(0)|683|689|(0)|692|(0)(0)|695|(0)|(0)(0)|703|365|366|(0)|369|(0)|682|(0)|679|(0)|678|(0)|677|(0)(0)|415|(0)(0)|(0)|(0)|422|(0)(0)|430|(0)|659|435|(0)(0)|453|(0)(0)|(0)|627|(0)|480|(0)|483|(0)|626|487|(0)|(0)|625|493|(0)|624|(0)(0)|(0)|616|621|511|(0)|(0)(0)|531|(0)|534|(0)|543|(0)(0))|1000|126|(1:128)|993|131|(1:133)|992|136|(0)(0)|139|(0)(0)|(0)(0)|151|(0)|988|159|(0)(0)|165|(1:167)|984|170|(0)|173|174|175|(0)(0)|186|187|(0)|958|190|191|(0)(0)|194|195|196|197|198|199|200|201|202|(0)|204|(0)(0)|207|(0)(0)|210|211|(0)|214|(1:216)|893|219|(0)|891|237|238|(0)|242|(0)(0)|245|(1:247)|868|250|(0)|867|255|256|(0)|862|(0)|259|260|(0)(0)|(0)(0)|(0)|272|273|274|275|276|(0)(0)|283|284|(0)|299|(0)|304|(0)|307|308|(0)|311|(0)|720|315|(0)(0)|342|(0)|360|(0)|683|689|(0)|692|(0)(0)|695|(0)|(0)(0)|703|365|366|(0)|369|(0)|682|(0)|679|(0)|678|(0)|677|(0)(0)|415|(0)(0)|(0)|(0)|422|(0)(0)|430|(0)|659|435|(0)(0)|453|(0)(0)|(0)|627|(0)|480|(0)|483|(0)|626|487|(0)|(0)|625|493|(0)|624|(0)(0)|(0)|616|621|511|(0)|(0)(0)|531|(0)|534|(0)|543|(0)(0)))|1043|40|(0)(0)|53|(0)|69|(0)|72|(0)|75|(2:77|79)|1026|82|(0)(0)|94|(0)|97|(0)|100|(1:102)|1006|(0)(0)|105|(0)(0)|(0)|(0)|1003|1000|126|(0)|993|131|(0)|992|136|(0)(0)|139|(0)(0)|(0)(0)|151|(0)|988|159|(0)(0)|165|(0)|984|170|(0)|173|174|175|(0)(0)|186|187|(0)|958|190|191|(0)(0)|194|195|196|197|198|199|200|201|202|(0)|204|(0)(0)|207|(0)(0)|210|211|(0)|214|(0)|893|219|(0)|891|237|238|(0)|242|(0)(0)|245|(0)|868|250|(0)|867|255|256|(0)|862|(0)|259|260|(0)(0)|(0)(0)|(0)|272|273|274|275|276|(0)(0)|283|284|(0)|299|(0)|304|(0)|307|308|(0)|311|(0)|720|315|(0)(0)|342|(0)|360|(0)|683|689|(0)|692|(0)(0)|695|(0)|(0)(0)|703|365|366|(0)|369|(0)|682|(0)|679|(0)|678|(0)|677|(0)(0)|415|(0)(0)|(0)|(0)|422|(0)(0)|430|(0)|659|435|(0)(0)|453|(0)(0)|(0)|627|(0)|480|(0)|483|(0)|626|487|(0)|(0)|625|493|(0)|624|(0)(0)|(0)|616|621|511|(0)|(0)(0)|531|(0)|534|(0)|543|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:832:0x0ab4, code lost:

       r6 = r25;
       r14 = r43;
       r15 = r44;
       r9 = 8;
       r21 = r21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:833:0x0aa9, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:834:0x0aaa, code lost:

       r2 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:837:0x0acc, code lost:

       r14 = r2;
       r6 = r25;
       r13 = r42;
       r15 = r44;
       r9 = 8;
       r21 = r21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:838:0x0abe, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:839:0x0abf, code lost:

       r2 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:844:0x0803, code lost:

       if (r61.mApplyInsetsRequested != false) goto L1028;
    */
    /* JADX WARN: Code restructure failed: missing block: B:871:0x0aec, code lost:

       r48 = r3;
       r6 = r25;
       r13 = r42;
       r14 = r43;
       r15 = r44;
       r9 = 8;
       r21 = r21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:872:0x0ad9, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:873:0x0ada, code lost:

       r2 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:895:0x0b11, code lost:

       r48 = r3;
       r6 = r25;
       r13 = r42;
       r14 = r43;
       r9 = 8;
       r21 = r21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:896:0x0afe, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:897:0x0aff, code lost:

       r2 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:943:0x0b38, code lost:

       r48 = r3;
       r6 = r13;
       r13 = r42;
       r14 = r43;
       r9 = 8;
       r21 = r21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:944:0x0b23, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:945:0x0b24, code lost:

       r2 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:948:0x0b63, code lost:

       r48 = r3;
       r46 = r10;
       r6 = r13;
       r13 = r42;
       r14 = r43;
       r9 = 8;
       r21 = r21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:949:0x0b4c, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:950:0x0b4d, code lost:

       r2 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:953:0x0b92, code lost:

       r48 = r3;
       r46 = r10;
       r6 = r13;
       r13 = r42;
       r14 = r43;
       r9 = 8;
       r21 = r21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:954:0x0b79, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:955:0x0b7a, code lost:

       r2 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:960:0x0bc0, code lost:

       r48 = r3;
       r46 = r10;
       r6 = r38;
       r13 = r42;
       r14 = r43;
       r15 = r44;
       r9 = 8;
       r21 = r21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:961:0x0baa, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:962:0x0bab, code lost:

       r2 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:981:0x0bf6, code lost:

       r48 = r3;
       r46 = r10;
       r9 = 8;
       r6 = r38;
       r21 = r21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:982:0x0bd5, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:983:0x0bd6, code lost:

       r2 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:999:0x031e, code lost:

       if (r30.height() != r61.mHeight) goto L171;
    */
    /* JADX WARN: Removed duplicated region for block: B:1005:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:1008:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:1022:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:1025:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:1033:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02dc  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0334  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0346  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0374  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0384  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x03c9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03f5  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0440  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0458  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0525 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:193:0x053b  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x058e  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0611  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0617 A[Catch: all -> 0x0641, RemoteException -> 0x0651, TRY_ENTER, TRY_LEAVE, TryCatch #51 {RemoteException -> 0x0651, all -> 0x0641, blocks: (B:917:0x05c2, B:918:0x05cc, B:922:0x05d6, B:925:0x05e0, B:213:0x0617, B:216:0x066c, B:225:0x0684), top: B:916:0x05c2 }] */
    /* JADX WARN: Removed duplicated region for block: B:216:0x066c A[Catch: all -> 0x0641, RemoteException -> 0x0651, TRY_ENTER, TRY_LEAVE, TryCatch #51 {RemoteException -> 0x0651, all -> 0x0641, blocks: (B:917:0x05c2, B:918:0x05cc, B:922:0x05d6, B:925:0x05e0, B:213:0x0617, B:216:0x066c, B:225:0x0684), top: B:916:0x05c2 }] */
    /* JADX WARN: Removed duplicated region for block: B:221:0x067e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0708  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0745  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0755 A[Catch: all -> 0x0718, RemoteException -> 0x072a, TRY_ENTER, TryCatch #56 {RemoteException -> 0x072a, all -> 0x0718, blocks: (B:234:0x06bd, B:235:0x06c6, B:241:0x070a, B:247:0x0755, B:252:0x0764), top: B:233:0x06bd }] */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0764 A[Catch: all -> 0x0718, RemoteException -> 0x072a, TRY_LEAVE, TryCatch #56 {RemoteException -> 0x072a, all -> 0x0718, blocks: (B:234:0x06bd, B:235:0x06c6, B:241:0x070a, B:247:0x0755, B:252:0x0764), top: B:233:0x06bd }] */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0779 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:262:0x078a  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x07d0 A[Catch: all -> 0x07d8, RemoteException -> 0x07e6, TRY_LEAVE, TryCatch #44 {RemoteException -> 0x07e6, all -> 0x07d8, blocks: (B:267:0x07a1, B:269:0x07d0), top: B:266:0x07a1 }] */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0831  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x0a47  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0a82  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0a9e  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x0c18  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x0c50  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0c68  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0d49  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x0da7  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0ea6  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x0ebf  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x0ef8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:388:0x0f13  */
    /* JADX WARN: Removed duplicated region for block: B:394:0x0f20  */
    /* JADX WARN: Removed duplicated region for block: B:398:0x0f2c  */
    /* JADX WARN: Removed duplicated region for block: B:417:0x0fe8  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x0fff  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x1006  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x101b  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x10a0  */
    /* JADX WARN: Removed duplicated region for block: B:437:0x10aa  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x112e  */
    /* JADX WARN: Removed duplicated region for block: B:475:0x11db  */
    /* JADX WARN: Removed duplicated region for block: B:477:0x11e5  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x11f6  */
    /* JADX WARN: Removed duplicated region for block: B:485:0x120c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:489:0x1220  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x1227 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:495:0x1239 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:500:0x1245  */
    /* JADX WARN: Removed duplicated region for block: B:509:0x1274 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:513:0x12b6  */
    /* JADX WARN: Removed duplicated region for block: B:517:0x12e4  */
    /* JADX WARN: Removed duplicated region for block: B:533:0x1408  */
    /* JADX WARN: Removed duplicated region for block: B:536:0x1412  */
    /* JADX WARN: Removed duplicated region for block: B:545:0x143d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:576:0x14cf  */
    /* JADX WARN: Removed duplicated region for block: B:582:0x1345  */
    /* JADX WARN: Removed duplicated region for block: B:623:0x1270  */
    /* JADX WARN: Removed duplicated region for block: B:629:0x11f1  */
    /* JADX WARN: Removed duplicated region for block: B:637:0x11e1  */
    /* JADX WARN: Removed duplicated region for block: B:648:0x1110  */
    /* JADX WARN: Removed duplicated region for block: B:669:0x108c  */
    /* JADX WARN: Removed duplicated region for block: B:670:0x0fef  */
    /* JADX WARN: Removed duplicated region for block: B:676:0x0fdf  */
    /* JADX WARN: Removed duplicated region for block: B:691:0x0ddc  */
    /* JADX WARN: Removed duplicated region for block: B:694:0x0e3e  */
    /* JADX WARN: Removed duplicated region for block: B:697:0x0e5a  */
    /* JADX WARN: Removed duplicated region for block: B:699:0x0e6c  */
    /* JADX WARN: Removed duplicated region for block: B:705:0x0e9b  */
    /* JADX WARN: Removed duplicated region for block: B:706:0x0e51  */
    /* JADX WARN: Removed duplicated region for block: B:719:0x0d41  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:747:0x0c10  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:761:0x0bf1  */
    /* JADX WARN: Removed duplicated region for block: B:763:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:791:0x096f  */
    /* JADX WARN: Removed duplicated region for block: B:840:0x07f9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:851:0x07f5  */
    /* JADX WARN: Removed duplicated region for block: B:861:0x07ca  */
    /* JADX WARN: Removed duplicated region for block: B:864:0x0783  */
    /* JADX WARN: Removed duplicated region for block: B:869:0x0747  */
    /* JADX WARN: Removed duplicated region for block: B:898:0x0599 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:934:0x0590  */
    /* JADX WARN: Removed duplicated region for block: B:935:0x054d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:956:0x053d  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x024c  */
    /* JADX WARN: Removed duplicated region for block: B:978:0x051b  */
    /* JADX WARN: Removed duplicated region for block: B:986:0x042a  */
    /* JADX WARN: Removed duplicated region for block: B:989:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:990:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:991:0x0362  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0254  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void performTraversals() {
        /*
            Method dump skipped, instructions count: 5357
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.view.ViewRootImpl.performTraversals():void");
    }

    private void createSyncIfNeeded() {
        if (isInWMSRequestedSync() || !this.mReportNextDraw) {
            return;
        }
        final int seqId = this.mSyncSeqId;
        this.mWmsRequestSyncGroupState = 1;
        this.mWmsRequestSyncGroup =
                new SurfaceSyncGroup(
                        "wmsSync-" + this.mTag,
                        new Consumer() { // from class:
                                         // android.view.ViewRootImpl$$ExternalSyntheticLambda7
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ViewRootImpl.this.lambda$createSyncIfNeeded$4(
                                        seqId, (SurfaceControl.Transaction) obj);
                            }
                        });
        if (this.mAppStartInfoTimestampsFlagValue && !this.mAppStartTrackingStarted) {
            this.mAppStartTrackingStarted = true;
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.addTransactionCompletedListener(
                    this.mSimpleExecutor,
                    new Consumer<
                            SurfaceControl
                                    .TransactionStats>() { // from class:
                                                           // android.view.ViewRootImpl.5
                        @Override // java.util.function.Consumer
                        public void accept(SurfaceControl.TransactionStats transactionStats) {
                            SyncFence presentFence = transactionStats.getPresentFence();
                            if (presentFence.awaitForever()
                                    && ViewRootImpl.this.mFirstFramePresentedTimeNs == -1) {
                                ViewRootImpl.this.mFirstFramePresentedTimeNs =
                                        presentFence.getSignalTime();
                                ViewRootImpl.this.maybeSendAppStartTimes();
                            }
                            presentFence.close();
                        }
                    });
            applyTransactionOnDraw(transaction);
        }
        if (DEBUG_BLAST) {
            Log.d(this.mTag, "Setup new sync=" + this.mWmsRequestSyncGroup.getName());
        }
        this.mWmsRequestSyncGroup.add(this, (Runnable) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSyncIfNeeded$4(
            final int seqId, SurfaceControl.Transaction t) {
        if (CoreRune.FW_SURFACE_DEBUG_APPLY && t != null && !TextUtils.isEmpty(t.mDebugName)) {
            t.mDebugName += "_seqId<" + seqId + ">";
        }
        this.mWmsRequestSyncGroupState = 3;
        if (this.mWindowSession instanceof Binder) {
            final SurfaceControl.Transaction transactionCopy = new SurfaceControl.Transaction();
            transactionCopy.merge(t);
            this.mHandler.postAtFrontOfQueue(
                    new Runnable() { // from class:
                                     // android.view.ViewRootImpl$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewRootImpl.this.lambda$createSyncIfNeeded$3(transactionCopy, seqId);
                        }
                    });
            return;
        }
        lambda$createSyncIfNeeded$3(t, seqId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeSendAppStartTimes() {
        if (this.mAppStartTimestampsSent.get()) {
            return;
        }
        this.mHandler.post(
                new Runnable() { // from class: android.view.ViewRootImpl.6
                    @Override // java.lang.Runnable
                    public void run() {
                        if (ViewRootImpl.this.mRenderThreadDrawStartTimeNs == -1) {
                            return;
                        }
                        try {
                            ActivityManager.getService()
                                    .reportStartInfoViewTimestamps(
                                            ViewRootImpl.this.mRenderThreadDrawStartTimeNs,
                                            ViewRootImpl.this.mFirstFramePresentedTimeNs);
                            ViewRootImpl.this.mAppStartTimestampsSent.set(true);
                        } catch (RemoteException e) {
                        }
                    }
                });
    }

    private void applySensitiveContentAppProtection(boolean enableProtection) {
        try {
            if (this.mSensitiveContentProtectionService == null) {
                return;
            }
            this.mSensitiveContentProtectionService.setSensitiveContentProtection(
                    getWindowToken(), this.mContext.getPackageName(), enableProtection);
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to protect sensitive content during screen share", ex);
        }
    }

    void addSensitiveContentAppProtection() {
        applySensitiveContentAppProtection(true);
    }

    void removeSensitiveContentAppProtection() {
        if (!Flags.sensitiveContentPrematureProtectionRemovedFix()) {
            applySensitiveContentAppProtection(false);
            return;
        }
        SurfaceControl.Transaction t = new SurfaceControl.Transaction();
        t.addTransactionCommittedListener(
                this.mExecutor,
                new SurfaceControl
                        .TransactionCommittedListener() { // from class:
                                                          // android.view.ViewRootImpl$$ExternalSyntheticLambda8
                    @Override // android.view.SurfaceControl.TransactionCommittedListener
                    public final void onTransactionCommitted() {
                        ViewRootImpl.this.lambda$removeSensitiveContentAppProtection$5();
                    }
                });
        applyTransactionOnDraw(t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeSensitiveContentAppProtection$5() {
        if (this.mAttachInfo.mSensitiveViewsCount == 0) {
            applySensitiveContentAppProtection(false);
        }
    }

    private void notifyContentCaptureEvents() {
        if (!isContentCaptureEnabled()) {
            if (DEBUG_CONTENT_CAPTURE) {
                Log.d(this.mTag, "notifyContentCaptureEvents while disabled");
            }
            this.mAttachInfo.mContentCaptureEvents = null;
        } else {
            ContentCaptureManager manager = this.mAttachInfo.mContentCaptureManager;
            if (manager != null && this.mAttachInfo.mContentCaptureEvents != null) {
                ContentCaptureSession session = manager.getMainContentCaptureSession();
                session.notifyContentCaptureEvents(this.mAttachInfo.mContentCaptureEvents);
            }
            this.mAttachInfo.mContentCaptureEvents = null;
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
        boolean windowMoved =
                (this.mAttachInfo.mWindowLeft == frame.left
                                && this.mAttachInfo.mWindowTop == frame.top)
                        ? false
                        : true;
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
                    this.mImeFocusController.onPostWindowFocus(
                            getFocusedViewOrNull(), hasWindowFocus, this.mWindowAttributes);
                    if (hasWindowFocus) {
                        this.mWindowAttributes.softInputMode &= -257;
                        ((WindowManager.LayoutParams) this.mView.getLayoutParams()).softInputMode &=
                                -257;
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
                if (CoreRune.BIXBY_TOUCH
                        && hasWindowFocus
                        && this.mSemPressGestureDetector != null) {
                    if (Settings.System.getInt(
                                    this.mContext.getContentResolver(), "bixby_touch_enable", 0)
                            == 1) {
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
        EventLog.writeEvent(
                LOGTAG_INPUT_FOCUS,
                "Giving fake focus to " + this.mBasePackageName,
                "reason=unity bug workaround");
        dispatchFocusEvent(true, true);
        EventLog.writeEvent(
                LOGTAG_INPUT_FOCUS,
                "Removing fake focus from " + this.mBasePackageName,
                "reason=timeout callback");
        dispatchFocusEvent(false, true);
    }

    private void dispatchFocusEvent(boolean hasWindowFocus, boolean fakeFocus) {
        profileRendering(hasWindowFocus);
        if (hasWindowFocus
                && this.mAttachInfo.mThreadedRenderer != null
                && this.mSurface.isValid()) {
            this.mFullRedrawNeeded = true;
            try {
                Rect surfaceInsets = this.mWindowAttributes.surfaceInsets;
                this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(
                        this.mWidth, this.mHeight, this.mAttachInfo, this.mSurface, surfaceInsets);
                Log.d(
                        this.mTag,
                        String.format(
                                "mThreadedRenderer.initializeIfNeeded()#2 mSurface={%s}",
                                "isValid="
                                        + this.mSurface.isValid()
                                        + " 0x"
                                        + Long.toHexString(this.mSurface.mNativeObject)));
            } catch (Surface.OutOfResourcesException e) {
                Log.e(this.mTag, "OutOfResourcesException locking surface", e);
                try {
                    if (!this.mWindowSession.outOfMemory(this.mWindow)) {
                        Slog.w(this.mTag, "No processes killed for memory; killing self");
                        Process.killProcess(Process.myPid());
                    }
                } catch (RemoteException e2) {
                }
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(6), 500L);
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
        boolean isToast = this.mWindowAttributes != null && this.mWindowAttributes.type == 2005;
        if (!isToast && this.mView != null) {
            this.mView.sendAccessibilityEvent(32);
        }
    }

    private void fireAccessibilityFocusEventIfHasFocusedNode() {
        View focusedView;
        if (!this.mAccessibilityManager.isEnabled()
                || (focusedView = this.mView.findFocus()) == null) {
            return;
        }
        AccessibilityNodeProvider provider = focusedView.getAccessibilityNodeProvider();
        if (provider == null) {
            focusedView.sendAccessibilityEvent(8);
            return;
        }
        AccessibilityNodeInfo focusedNode = findFocusedVirtualNode(provider);
        if (focusedNode != null) {
            int virtualId =
                    AccessibilityNodeInfo.getVirtualDescendantId(focusedNode.getSourceNodeId());
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
        AccessibilityNodeInfo current;
        AccessibilityNodeInfo focusedNode = provider.findFocus(1);
        if (focusedNode != null) {
            return focusedNode;
        }
        if (!this.mContext.isAutofillCompatibilityEnabled()
                || (current = provider.createAccessibilityNodeInfo(-1)) == null) {
            return null;
        }
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
                    int virtualId =
                            AccessibilityNodeInfo.getVirtualDescendantId(childNodeIds.get(i));
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
        Log.e(this.mTag, "OutOfResourcesException initializing HW surface", e);
        try {
            if (!this.mWindowSession.outOfMemory(this.mWindow) && Process.myUid() != 1000) {
                Slog.w(this.mTag, "No processes killed for memory; killing self");
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

    private void performLayout(
            WindowManager.LayoutParams lp, int desiredWindowWidth, int desiredWindowHeight) {
        ArrayList<View> validLayoutRequesters;
        this.mScrollMayChange = true;
        this.mInLayout = true;
        View host = this.mView;
        if (host == null) {
            return;
        }
        if (DEBUG_ORIENTATION || DEBUG_LAYOUT) {
            Log.v(
                    this.mTag,
                    "Laying out "
                            + host
                            + " to ("
                            + host.getMeasuredWidth()
                            + ", "
                            + host.getMeasuredHeight()
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
        Trace.traceBegin(8L, TtmlUtils.TAG_LAYOUT);
        try {
            host.layout(0, 0, host.getMeasuredWidth(), host.getMeasuredHeight());
            this.mInLayout = false;
            int numViewsRequestingLayout = this.mLayoutRequesters.size();
            if (numViewsRequestingLayout > 0
                    && (validLayoutRequesters =
                                    getValidLayoutRequesters(this.mLayoutRequesters, false))
                            != null) {
                this.mHandlingLayoutInLayoutRequest = true;
                int numValidRequests = validLayoutRequesters.size();
                for (int i = 0; i < numValidRequests; i++) {
                    View view = validLayoutRequesters.get(i);
                    Log.w(
                            "View",
                            "requestLayout() improperly called by "
                                    + view
                                    + " during layout: running second layout pass");
                    view.requestLayout();
                }
                measureHierarchy(
                        host,
                        lp,
                        this.mView.getContext().getResources(),
                        desiredWindowWidth,
                        desiredWindowHeight,
                        false);
                this.mInLayout = true;
                host.layout(0, 0, host.getMeasuredWidth(), host.getMeasuredHeight());
                this.mHandlingLayoutInLayoutRequest = false;
                final ArrayList<View> validLayoutRequesters2 =
                        getValidLayoutRequesters(this.mLayoutRequesters, true);
                if (validLayoutRequesters2 != null) {
                    getRunQueue()
                            .post(
                                    new Runnable() { // from class: android.view.ViewRootImpl.7
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            int numValidRequests2 = validLayoutRequesters2.size();
                                            for (int i2 = 0; i2 < numValidRequests2; i2++) {
                                                View view2 = (View) validLayoutRequesters2.get(i2);
                                                Log.w(
                                                        "View",
                                                        "requestLayout() improperly called by "
                                                                + view2
                                                                + " during second layout pass:"
                                                                + " posting in next frame");
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

    private ArrayList<View> getValidLayoutRequesters(
            ArrayList<View> layoutRequesters, boolean secondLayoutRequests) {
        int numViewsRequestingLayout = layoutRequesters.size();
        ArrayList<View> validLayoutRequesters = null;
        for (int i = 0; i < numViewsRequestingLayout; i++) {
            View view = layoutRequesters.get(i);
            if (view != null
                    && view.mAttachInfo != null
                    && view.mParent != null
                    && (secondLayoutRequests || (view.mPrivateFlags & 4096) == 4096)) {
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
        if (this.mView != child) {
            return;
        }
        if ((this.mView.mPrivateFlags & 512) == 0) {
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
        if (this.mCurScrollY != 0
                && this.mHardwareYOffset != 0
                && this.mAttachInfo.mThreadedRenderer.isOpaque()) {
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
            if (this.mRenderProfiler != null) {
                this.mChoreographer.removeFrameCallback(this.mRenderProfiler);
            }
            if (this.mRenderProfilingEnabled) {
                if (this.mRenderProfiler == null) {
                    this.mRenderProfiler =
                            new Choreographer
                                    .FrameCallback() { // from class: android.view.ViewRootImpl.8
                                @Override // android.view.Choreographer.FrameCallback
                                public void doFrame(long frameTimeNanos) {
                                    ViewRootImpl.this.mDirty.set(
                                            0,
                                            0,
                                            ViewRootImpl.this.mWidth,
                                            ViewRootImpl.this.mHeight);
                                    if (ViewRootImpl.DEBUG_TRAVERSAL
                                            && ViewRootImpl.DEBUG_TRAVERSAL_PACKAGE_NAME.equals(
                                                    ActivityThread.currentPackageName())) {
                                        Log.i(
                                                ViewRootImpl.this.mTag,
                                                "Traversal, [12] mView=" + ViewRootImpl.this.mView);
                                    }
                                    ViewRootImpl.this.scheduleTraversals();
                                    if (ViewRootImpl.this.mRenderProfilingEnabled) {
                                        ViewRootImpl.this.mChoreographer.postFrameCallback(
                                                ViewRootImpl.this.mRenderProfiler);
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
        Log.v(this.mTag, "0x" + thisHash + "\tFrame time:\t" + frameTime);
        this.mFpsPrevTime = nowTime;
        if (totalTime > 1000) {
            float fps = (this.mFpsNumFrames * 1000.0f) / totalTime;
            Log.v(this.mTag, "0x" + thisHash + "\tFPS:\t" + fps);
            this.mFpsStartTime = nowTime;
            this.mFpsNumFrames = 0;
        }
    }

    private void collectFrameRateDecisionMetrics() {
        if (!Trace.isEnabled()) {
            if (this.mPreviousFrameDrawnTime > 0) {
                this.mPreviousFrameDrawnTime = -1L;
            }
        } else {
            if (this.mPreviousFrameDrawnTime < 0) {
                this.mPreviousFrameDrawnTime =
                        this.mChoreographer.getExpectedPresentationTimeNanos();
                return;
            }
            long expectedDrawnTime = this.mChoreographer.getExpectedPresentationTimeNanos();
            long timeDiff = expectedDrawnTime - this.mPreviousFrameDrawnTime;
            if (timeDiff <= 0) {
                return;
            }
            long fps = 1000000000 / timeDiff;
            Trace.setCounter(this.mFpsTraceName, fps);
            this.mPreviousFrameDrawnTime = expectedDrawnTime;
            long percentage = (long) (this.mLargestChildPercentage * 100.0f);
            Trace.setCounter(this.mLargestViewTraceName, percentage);
            this.mLargestChildPercentage = 0.0f;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: reportDrawFinished, reason: merged with bridge method [inline-methods] */
    public void lambda$createSyncIfNeeded$3(SurfaceControl.Transaction t, int seqId) {
        logAndTrace("reportDrawFinished seqId=" + seqId);
        try {
            try {
                this.mWindowSession.finishDrawing(this.mWindow, t, seqId);
                if (t == null) {
                    return;
                }
            } catch (RemoteException e) {
                Log.e(this.mTag, "Unable to report draw finished", e);
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
        return this.mAttachInfo.mThreadedRenderer != null
                && this.mAttachInfo.mThreadedRenderer.isEnabled();
    }

    public boolean isInWMSRequestedSync() {
        return this.mWmsRequestSyncGroup != null;
    }

    private void addFrameCommitCallbackIfNeeded() {
        if (!isHardwareEnabled()) {
            return;
        }
        final ArrayList<Runnable> commitCallbacks =
                this.mAttachInfo.mTreeObserver.captureFrameCommitCallbacks();
        boolean needFrameCommitCallback = commitCallbacks != null && commitCallbacks.size() > 0;
        if (!needFrameCommitCallback) {
            return;
        }
        Log.d(
                this.mTag,
                "Creating frameCommitCallback commitCallbacks size=" + commitCallbacks.size());
        this.mAttachInfo.mThreadedRenderer.setFrameCommitCallback(
                new HardwareRenderer
                        .FrameCommitCallback() { // from class:
                                                 // android.view.ViewRootImpl$$ExternalSyntheticLambda16
                    @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                    public final void onFrameCommit(boolean z) {
                        ViewRootImpl.this.lambda$addFrameCommitCallbackIfNeeded$7(
                                commitCallbacks, z);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addFrameCommitCallbackIfNeeded$7(
            final ArrayList commitCallbacks, boolean didProduceBuffer) {
        Log.d(this.mTag, "Received frameCommitCallback didProduceBuffer=" + didProduceBuffer);
        this.mHandler.postAtFrontOfQueue(
                new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewRootImpl.lambda$addFrameCommitCallbackIfNeeded$6(commitCallbacks);
                    }
                });
    }

    static /* synthetic */ void lambda$addFrameCommitCallbackIfNeeded$6(ArrayList commitCallbacks) {
        for (int i = 0; i < commitCallbacks.size(); i++) {
            ((Runnable) commitCallbacks.get(i)).run();
        }
    }

    private void registerCallbackForPendingTransactions() {
        SurfaceControl.Transaction t = new SurfaceControl.Transaction();
        t.merge(this.mPendingTransaction);
        this.mHasPendingTransactions = false;
        Log.i(this.mTag, "registerCallbackForPendingTransactions");
        registerRtFrameCallback(new AnonymousClass9(t));
    }

    /* renamed from: android.view.ViewRootImpl$9, reason: invalid class name */
    class AnonymousClass9 implements HardwareRenderer.FrameDrawingCallback {
        final /* synthetic */ SurfaceControl.Transaction val$t;

        AnonymousClass9(SurfaceControl.Transaction transaction) {
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
            return new HardwareRenderer
                    .FrameCommitCallback() { // from class:
                                             // android.view.ViewRootImpl$9$$ExternalSyntheticLambda0
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z) {
                    ViewRootImpl.AnonymousClass9.this.lambda$onFrameDraw$0(frame, z);
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
        public void onFrameDraw(long frame) {}
    }

    private boolean performDraw(final SurfaceSyncGroup surfaceSyncGroup) {
        final SurfaceControl.Transaction pendingTransaction;
        this.mLastPerformDrawSkippedReason = null;
        if (!this.mReportNextDraw) {
            switch (this.mAttachInfo.mDisplayState) {
                case 1:
                    if (this.mDeferTransactionRequested) {
                        this.mDeferTransactionRequested = false;
                        Log.i(
                                this.mTag,
                                "performDraw() SCREEN_OFF but mDeferTransactionRequested = true"
                                    + " break");
                        break;
                    } else {
                        this.mLastPerformDrawSkippedReason = "screen_off";
                        if (!this.mLastDrawScreenOff) {
                            logAndTrace("Not drawing due to screen off");
                        }
                        this.mLastDrawScreenOff = true;
                        return false;
                    }
                case 3:
                case 4:
                    if (this.mDisplay.getDisplayId() == 0
                            && (this.mWindowAttributes.samsungFlags & 262144) == 0
                            && Settings.System.getInt(this.mContentResolver, AOD_SHOW_STATE, 0)
                                    != 0) {
                        Log.i(
                                this.mTag,
                                "performDraw() was skipped by AOD_SHOW_STATE... DisplayState = "
                                        + this.mAttachInfo.mDisplayState);
                        return false;
                    }
                    break;
            }
        } else if (this.mView == null) {
            this.mLastPerformDrawSkippedReason = "no_root_view";
            return false;
        }
        if (this.mLastDrawScreenOff) {
            logAndTrace("Resumed drawing after screen turned on");
            this.mLastDrawScreenOff = false;
        }
        boolean fullRedrawNeeded = this.mFullRedrawNeeded || surfaceSyncGroup != null;
        this.mFullRedrawNeeded = false;
        this.mIsDrawing = true;
        Trace.traceBegin(8L, "draw-" + this.mTag);
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
            if (!usingAsyncReport && this.mHasPendingTransactions) {
                pendingTransaction = new SurfaceControl.Transaction();
                pendingTransaction.merge(this.mPendingTransaction);
                this.mHasPendingTransactions = false;
            } else {
                pendingTransaction = null;
            }
            if (this.mReportNextDraw) {
                if (this.mWindowDrawCountDown != null) {
                    try {
                        this.mWindowDrawCountDown.await();
                    } catch (InterruptedException e) {
                        Log.e(this.mTag, "Window redraw count down interrupted!");
                    }
                    this.mWindowDrawCountDown = null;
                }
                if (this.mAttachInfo.mThreadedRenderer != null) {
                    this.mAttachInfo.mThreadedRenderer.setStopped(this.mStopped);
                }
                if (this.mSurfaceHolder != null && this.mSurface.isValid()) {
                    usingAsyncReport = true;
                    SurfaceCallbackHelper sch =
                            new SurfaceCallbackHelper(
                                    new Runnable() { // from class:
                                                     // android.view.ViewRootImpl$$ExternalSyntheticLambda13
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            ViewRootImpl.this.lambda$performDraw$8(
                                                    surfaceSyncGroup, pendingTransaction);
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
            if (!usingAsyncReport) {
                handleSyncRequestWhenNoAsyncDraw(
                        surfaceSyncGroup,
                        pendingTransaction != null,
                        pendingTransaction,
                        "no async report");
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performDraw$8(
            SurfaceSyncGroup surfaceSyncGroup, SurfaceControl.Transaction pendingTransaction) {
        handleSyncRequestWhenNoAsyncDraw(
                surfaceSyncGroup, pendingTransaction != null, pendingTransaction, "SurfaceHolder");
    }

    private void handleSyncRequestWhenNoAsyncDraw(
            SurfaceSyncGroup surfaceSyncGroup,
            boolean hasPendingTransaction,
            SurfaceControl.Transaction pendingTransaction,
            String logReason) {
        if (surfaceSyncGroup != null) {
            if (hasPendingTransaction && pendingTransaction != null) {
                surfaceSyncGroup.addTransaction(pendingTransaction);
            }
            surfaceSyncGroup.markSyncReady();
            return;
        }
        if (hasPendingTransaction && pendingTransaction != null) {
            Trace.instant(
                    8L,
                    "Transaction not synced due to "
                            + logReason
                            + NativeLibraryHelper.CLEAR_ABI_OVERRIDE
                            + this.mTag);
            if (DEBUG_BLAST) {
                Log.d(
                        this.mTag,
                        "Pending transaction will not be applied in sync with a draw due to "
                                + logReason);
            }
            pendingTransaction.apply();
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
                Log.w(
                        TAG,
                        "isContentCaptureEnabled(): invalid state " + this.mContentCaptureEnabled);
                break;
        }
        return false;
    }

    private boolean isContentCaptureReallyEnabled() {
        ContentCaptureManager ccm;
        return (this.mContext.getContentCaptureOptions() == null
                        || (ccm = this.mAttachInfo.getContentCaptureManager(this.mContext)) == null
                        || !ccm.isContentCaptureEnabled())
                ? false
                : true;
    }

    private void performContentCaptureInitialReport() {
        this.mPerformContentCapture = false;
        View rootView = this.mView;
        if (DEBUG_CONTENT_CAPTURE) {
            Log.v(this.mTag, "performContentCaptureInitialReport() on " + rootView);
        }
        boolean traceDispatchCapture = false;
        try {
            if (isContentCaptureEnabled()) {
                traceDispatchCapture = Trace.isTagEnabled(8L);
                if (traceDispatchCapture) {
                    Trace.traceBegin(
                            8L, "dispatchContentCapture() for " + getClass().getSimpleName());
                }
                if (this.mAttachInfo.mContentCaptureManager != null) {
                    ContentCaptureSession session =
                            this.mAttachInfo.mContentCaptureManager.getMainContentCaptureSession();
                    session.notifyWindowBoundsChanged(
                            session.getId(), getConfiguration().windowConfiguration.getBounds());
                }
                rootView.dispatchInitialProvideContentCaptureStructure();
                if (traceDispatchCapture) {
                    Trace.traceEnd(8L);
                    return;
                }
                return;
            }
        } finally {
            if (traceDispatchCapture) {
                Trace.traceEnd(8L);
            }
        }
    }

    private void handleContentCaptureFlush() {
        if (DEBUG_CONTENT_CAPTURE) {
            Log.v(this.mTag, "handleContentCaptureFlush()");
        }
        try {
            if (!isContentCaptureEnabled()) {
                if (traceFlushContentCapture) {
                    return;
                } else {
                    return;
                }
            }
            boolean traceFlushContentCapture = Trace.isTagEnabled(8L);
            if (traceFlushContentCapture) {
                Trace.traceBegin(8L, "flushContentCapture for " + getClass().getSimpleName());
            }
            ContentCaptureManager ccm = this.mAttachInfo.mContentCaptureManager;
            if (ccm == null) {
                Log.w(TAG, "No ContentCapture on AttachInfo");
                if (traceFlushContentCapture) {
                    Trace.traceEnd(8L);
                    return;
                }
                return;
            }
            ccm.flush(2);
            if (traceFlushContentCapture) {
                Trace.traceEnd(8L);
            }
        } finally {
            if (0 != 0) {
                Trace.traceEnd(8L);
            }
        }
    }

    private boolean draw(
            boolean fullRedrawNeeded, SurfaceSyncGroup activeSyncGroup, boolean syncBuffer) {
        int curScrollY;
        boolean fullRedrawNeeded2;
        int xOffset;
        boolean accessibilityFocusDirty;
        Surface surface = this.mSurface;
        if (!surface.isValid()) {
            Log.e(this.mTag, "Surface is not valid.");
            return false;
        }
        if (DEBUG_FPS) {
            trackFPS();
        }
        if (sToolkitMetricsForFrameRateDecisionFlagValue) {
            collectFrameRateDecisionMetrics();
        }
        if (!sFirstDrawComplete) {
            synchronized (sFirstDrawHandlers) {
                sFirstDrawComplete = true;
                int count = sFirstDrawHandlers.size();
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
        boolean animating = this.mScroller != null && this.mScroller.computeScrollOffset();
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
            if (this.mView instanceof RootViewSurfaceTaker) {
                ((RootViewSurfaceTaker) this.mView).onRootViewScrollYChanged(this.mCurScrollY);
            }
            fullRedrawNeeded2 = true;
        }
        float appScale = this.mAttachInfo.mApplicationScale;
        boolean scalingRequired = this.mAttachInfo.mScalingRequired;
        Rect dirty = this.mDirty;
        if (this.mSurfaceHolder != null) {
            dirty.setEmpty();
            if (animating && this.mScroller != null) {
                this.mScroller.abortAnimation();
            }
            return false;
        }
        if (fullRedrawNeeded2) {
            dirty.set(
                    0,
                    0,
                    (int) ((this.mWidth * appScale) + 0.5f),
                    (int) ((this.mHeight * appScale) + 0.5f));
        }
        if (DEBUG_ORIENTATION || DEBUG_DRAW) {
            Log.v(
                    this.mTag,
                    "Draw "
                            + this.mView
                            + "/"
                            + ((Object) this.mWindowAttributes.getTitle())
                            + ": dirty={"
                            + dirty.left
                            + ","
                            + dirty.top
                            + ","
                            + dirty.right
                            + ","
                            + dirty.bottom
                            + "} surface="
                            + surface
                            + " surface.isValid()="
                            + surface.isValid()
                            + ", appScale:"
                            + appScale
                            + ", width="
                            + this.mWidth
                            + ", height="
                            + this.mHeight);
        }
        this.mAttachInfo.mTreeObserver.dispatchOnDraw();
        int xOffset2 = -this.mCanvasOffsetX;
        int yOffset = (-this.mCanvasOffsetY) + curScrollY;
        WindowManager.LayoutParams params = this.mWindowAttributes;
        Rect surfaceInsets = params != null ? params.surfaceInsets : null;
        if (surfaceInsets == null) {
            xOffset = xOffset2;
        } else {
            int xOffset3 = xOffset2 - surfaceInsets.left;
            yOffset -= surfaceInsets.top;
            dirty.offset(surfaceInsets.left, surfaceInsets.top);
            xOffset = xOffset3;
        }
        boolean accessibilityFocusDirty2 = isAccessibilityFocusDirty();
        if (accessibilityFocusDirty2) {
            Rect bounds = this.mAttachInfo.mTmpInvalRect;
            if (getAccessibilityFocusedRect(bounds)) {
                requestLayout();
            }
        }
        this.mAttachInfo.mDrawingTime = this.mChoreographer.getFrameTimeNanos() / 1000000;
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(
                    this.mTag,
                    "Traversal, [13] mView="
                            + this.mView
                            + " dirty.isEmpty="
                            + dirty.isEmpty()
                            + " mIsAnimating="
                            + this.mIsAnimating
                            + " accessibilityFocusDirty="
                            + accessibilityFocusDirty2
                            + " mForceDraw="
                            + this.mForceDraw);
        }
        boolean useAsyncReport = false;
        if (!dirty.isEmpty() || this.mIsAnimating || accessibilityFocusDirty2 || this.mForceDraw) {
            if (!isHardwareEnabled()) {
                if (this.mAttachInfo.mThreadedRenderer != null
                        && !this.mAttachInfo.mThreadedRenderer.isEnabled()
                        && this.mAttachInfo.mThreadedRenderer.isRequested()
                        && this.mSurface.isValid()) {
                    if (DEBUG_TRAVERSAL
                            && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(
                                    ActivityThread.currentPackageName())) {
                        Log.i(
                                this.mTag,
                                "Traversal, [13-1] mView="
                                        + this.mView
                                        + " isImpossibleRenderer="
                                        + isImpossibleRenderer());
                    }
                    if (!isImpossibleRenderer()) {
                        try {
                            this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(
                                    this.mWidth,
                                    this.mHeight,
                                    this.mAttachInfo,
                                    this.mSurface,
                                    surfaceInsets);
                            if (ViewRune.COMMON_IS_PRODUCT_DEV) {
                                Log.d(
                                        this.mTag,
                                        String.format(
                                                "mThreadedRenderer.initializeIfNeeded()#1"
                                                    + " mSurface={%s}",
                                                "isValid="
                                                        + this.mSurface.isValid()
                                                        + " 0x"
                                                        + Long.toHexString(
                                                                this.mSurface.mNativeObject)));
                            }
                            this.mFullRedrawNeeded = true;
                            scheduleTraversals();
                            return false;
                        } catch (Surface.OutOfResourcesException e) {
                            handleOutOfResourcesException(e);
                            return false;
                        }
                    }
                    Log.e(this.mTag, "Renderer can't be initialized due to isImpossibleRenderer()");
                    this.mFullRedrawNeeded = true;
                    scheduleTraversals();
                    return false;
                }
                if (!drawSoftware(
                        surface,
                        this.mAttachInfo,
                        xOffset,
                        yOffset,
                        scalingRequired,
                        dirty,
                        surfaceInsets)) {
                    return false;
                }
            } else {
                boolean invalidateRoot = accessibilityFocusDirty2 || this.mInvalidateRootRequested;
                this.mInvalidateRootRequested = false;
                this.mIsAnimating = false;
                if (this.mHardwareYOffset != yOffset || this.mHardwareXOffset != xOffset) {
                    this.mHardwareYOffset = yOffset;
                    this.mHardwareXOffset = xOffset;
                    invalidateRoot = true;
                }
                if (invalidateRoot) {
                    this.mAttachInfo.mThreadedRenderer.invalidateRoot();
                }
                dirty.setEmpty();
                boolean updated = updateContentDrawBounds();
                if (!this.mReportNextDraw) {
                    accessibilityFocusDirty = accessibilityFocusDirty2;
                } else {
                    accessibilityFocusDirty = accessibilityFocusDirty2;
                    this.mAttachInfo.mThreadedRenderer.setStopped(false);
                }
                if (updated) {
                    requestDrawWindow();
                }
                useAsyncReport = true;
                if (this.mHdrRenderState.updateForFrame(this.mAttachInfo.mDrawingTime)) {
                    float renderRatio = this.mHdrRenderState.getRenderHdrSdrRatio();
                    applyTransactionOnDraw(
                            this.mTransaction.setExtendedRangeBrightness(
                                    getSurfaceControl(),
                                    renderRatio,
                                    this.mHdrRenderState.getDesiredHdrSdrRatio()));
                    this.mAttachInfo.mThreadedRenderer.setTargetHdrSdrRatio(renderRatio);
                }
                if (activeSyncGroup != null) {
                    registerCallbacksForSync(syncBuffer, activeSyncGroup);
                    if (syncBuffer) {
                        this.mAttachInfo.mThreadedRenderer.forceDrawNextFrame();
                    }
                } else if (this.mHasPendingTransactions) {
                    registerCallbackForPendingTransactions();
                }
                if (this.mForceDraw) {
                    Log.i(this.mTag, "Force to draw even when frame is empty");
                    this.mForceDraw = false;
                }
                long timeNs = SystemClock.uptimeNanos();
                this.mAttachInfo.mThreadedRenderer.draw(this.mView, this.mAttachInfo, this);
                if (this.mAppStartInfoTimestampsFlagValue
                        && this.mRenderThreadDrawStartTimeNs == -1) {
                    this.mRenderThreadDrawStartTimeNs = timeNs;
                }
            }
        }
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [13-2] mView=" + this.mView + " animating=" + animating);
        }
        if (animating) {
            this.mFullRedrawNeeded = true;
            scheduleTraversals();
        }
        return useAsyncReport;
    }

    private boolean drawSoftware(
            Surface surface,
            View.AttachInfo attachInfo,
            int xoff,
            int yoff,
            boolean scalingRequired,
            Rect dirty,
            Rect surfaceInsets) {
        try {
            Canvas canvas = this.mSurface.lockCanvas(dirty);
            canvas.setDensity(this.mDensity);
            try {
                try {
                    if (DEBUG_ORIENTATION || DEBUG_DRAW) {
                        Log.v(
                                this.mTag,
                                "Surface "
                                        + surface
                                        + " drawing to bitmap w="
                                        + canvas.getWidth()
                                        + ", h="
                                        + canvas.getHeight()
                                        + ", dirty: "
                                        + dirty
                                        + ", xOff="
                                        + xoff
                                        + ", yOff="
                                        + yoff);
                    }
                    if (!canvas.isOpaque() || yoff != 0 || xoff != 0) {
                        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    }
                    dirty.setEmpty();
                    this.mIsAnimating = false;
                    this.mView.mPrivateFlags |= 32;
                    if (DEBUG_DRAW) {
                        Context cxt = this.mView.getContext();
                        Log.i(
                                this.mTag,
                                "Drawing: package:"
                                        + cxt.getPackageName()
                                        + ", metrics="
                                        + cxt.getResources().getDisplayMetrics()
                                        + ", compatibilityInfo="
                                        + cxt.getResources().getCompatibilityInfo());
                    }
                    canvas.translate(-xoff, -yoff);
                    if (this.mTranslator != null) {
                        this.mTranslator.translateCanvas(canvas);
                    }
                    canvas.setScreenDensity(scalingRequired ? this.mNoncompatDensity : 0);
                    this.mView.draw(canvas);
                    if (this.mView instanceof DecorView) {
                        ((DecorView) this.mView).drawFrameIfNeeded(canvas);
                    }
                    drawAccessibilityFocusedDrawableIfNeeded(canvas);
                    return true;
                } finally {
                    surface.unlockCanvasAndPost(canvas);
                }
            } catch (IllegalArgumentException e) {
                Log.e(this.mTag, "Could not unlock surface", e);
                this.mLayoutRequested = true;
                return false;
            }
        } catch (Surface.OutOfResourcesException e2) {
            handleOutOfResourcesException(e2);
            return false;
        } catch (IllegalArgumentException e3) {
            Log.e(this.mTag, "Could not lock surface", e3);
            this.mLayoutRequested = true;
            return false;
        }
    }

    private void drawAccessibilityFocusedDrawableIfNeeded(Canvas canvas) {
        Rect bounds = this.mAttachInfo.mTmpInvalRect;
        boolean z = false;
        if (getAccessibilityFocusedRect(bounds)) {
            if (this.mContext.getResources().getConfiguration().isScreenRound()
                    && this.mContext
                            .getPackageManager()
                            .hasSystemFeature(PackageManager.FEATURE_WATCH)) {
                z = true;
            }
            boolean isRoundWatch = z;
            Drawable drawable = getAccessibilityFocusedDrawable();
            if (drawable != null) {
                drawable.setBounds(bounds);
                drawable.draw(canvas);
                if (this.mDisplay != null && isRoundWatch) {
                    drawAccessibilityFocusedBorderOnRoundDisplay(
                            canvas,
                            bounds,
                            getRoundDisplayRadius(),
                            getRoundDisplayAccessibilityHighlightPaint());
                    return;
                }
                return;
            }
            return;
        }
        if (this.mAttachInfo.mAccessibilityFocusDrawable != null) {
            this.mAttachInfo.mAccessibilityFocusDrawable.setBounds(0, 0, 0, 0);
        }
    }

    private int getRoundDisplayRadius() {
        Point displaySize = new Point();
        this.mDisplay.getRealSize(displaySize);
        return displaySize.x / 2;
    }

    private Paint getRoundDisplayAccessibilityHighlightPaint() {
        if (this.mRoundDisplayAccessibilityHighlightPaint == null) {
            this.mRoundDisplayAccessibilityHighlightPaint = new Paint();
            this.mRoundDisplayAccessibilityHighlightPaint.setStyle(Paint.Style.STROKE);
            this.mRoundDisplayAccessibilityHighlightPaint.setAntiAlias(true);
        }
        this.mRoundDisplayAccessibilityHighlightPaint.setStrokeWidth(
                this.mAccessibilityManager.getAccessibilityFocusStrokeWidth());
        this.mRoundDisplayAccessibilityHighlightPaint.setColor(
                this.mAccessibilityManager.getAccessibilityFocusColor());
        return this.mRoundDisplayAccessibilityHighlightPaint;
    }

    private void drawAccessibilityFocusedBorderOnRoundDisplay(
            Canvas canvas,
            Rect bounds,
            int roundDisplayRadius,
            Paint accessibilityFocusHighlightPaint) {
        int saveCount = canvas.save();
        canvas.clipRect(bounds);
        canvas.drawCircle(
                roundDisplayRadius,
                roundDisplayRadius,
                roundDisplayRadius
                        - (this.mAccessibilityManager.getAccessibilityFocusStrokeWidth() / 2.0f),
                accessibilityFocusHighlightPaint);
        canvas.restoreToCount(saveCount);
    }

    private boolean getAccessibilityFocusedRect(Rect bounds) {
        View host;
        if (this.mView == null) {
            Slog.w(TAG, "calling getAccessibilityFocusedRect() while the mView is null");
            return false;
        }
        if (!this.mAccessibilityManager.isEnabled()
                || !this.mAccessibilityManager.isTouchExplorationEnabled()
                || (host = this.mAccessibilityFocusedHost) == null
                || host.mAttachInfo == null) {
            return false;
        }
        AccessibilityNodeProvider provider = host.getAccessibilityNodeProvider();
        if (provider == null) {
            host.getBoundsOnScreen(bounds, true);
        } else {
            if (this.mAccessibilityFocusedVirtualView == null) {
                return false;
            }
            this.mAccessibilityFocusedVirtualView.getBoundsInScreen(bounds);
        }
        View.AttachInfo attachInfo = this.mAttachInfo;
        bounds.offset(0, attachInfo.mViewRootImpl.mScrollY);
        bounds.offset(-attachInfo.mWindowLeft, -attachInfo.mWindowTop);
        if (!bounds.intersect(
                0, 0, attachInfo.mViewRootImpl.mWidth, attachInfo.mViewRootImpl.mHeight)) {
            bounds.setEmpty();
        }
        return !bounds.isEmpty();
    }

    private Drawable getAccessibilityFocusedDrawable() {
        if (this.mAttachInfo.mAccessibilityFocusDrawable == null) {
            TypedValue value = new TypedValue();
            boolean resolved =
                    this.mView
                            .mContext
                            .getTheme()
                            .resolveAttribute(R.attr.accessibilityFocusedDrawable, value, true);
            if (resolved) {
                this.mAttachInfo.mAccessibilityFocusDrawable =
                        this.mView.mContext.getDrawable(value.resourceId);
            }
        }
        if (this.mAttachInfo.mAccessibilityFocusDrawable instanceof GradientDrawable) {
            GradientDrawable drawable =
                    (GradientDrawable) this.mAttachInfo.mAccessibilityFocusDrawable;
            drawable.setStroke(
                    this.mAccessibilityManager.semGetAccessibilityFocusStrokeWidth(this.mContext),
                    this.mAccessibilityManager.getAccessibilityFocusColor());
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
                this.mWindowSession.reportSystemGestureExclusionChanged(
                        this.mWindow, rectsForWindowManager);
                this.mAttachInfo.mTreeObserver.dispatchOnSystemGestureExclusionRectsChanged(
                        rectsForWindowManager);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void updateDecorViewGestureInterception(boolean z) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(38, z ? 1 : 0, 0));
    }

    void decorViewInterceptionChanged(boolean intercepted) {
        if (this.mView != null) {
            try {
                this.mWindowSession.reportDecorViewGestureInterceptionChanged(
                        this.mWindow, intercepted);
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
            boolean hasAccessibilityFocus =
                    getAccessibilityFocusedRect(this.mKeepClearAccessibilityFocusRect);
            if (!hasAccessibilityFocus) {
                this.mKeepClearAccessibilityFocusRect.setEmpty();
            }
            this.mHandler.obtainMessage(35, 1, 0).sendToTarget();
        }
    }

    void keepClearRectsChanged(boolean accessibilityFocusRectChanged) {
        boolean restrictedKeepClearRectsChanged = this.mKeepClearRectsTracker.computeChanges();
        boolean unrestrictedKeepClearRectsChanged =
                this.mUnrestrictedKeepClearRectsTracker.computeChanges();
        if ((restrictedKeepClearRectsChanged
                        || unrestrictedKeepClearRectsChanged
                        || accessibilityFocusRectChanged)
                && this.mView != null) {
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
        List<Rect> unrestrictedKeepClearRects =
                this.mUnrestrictedKeepClearRectsTracker.getLastComputedRects();
        if (this.mKeepClearAccessibilityFocusRect != null
                && !this.mKeepClearAccessibilityFocusRect.isEmpty()) {
            restrictedKeepClearRects = new ArrayList(restrictedKeepClearRects);
            restrictedKeepClearRects.add(this.mKeepClearAccessibilityFocusRect);
        }
        try {
            this.mWindowSession.reportKeepClearAreasChanged(
                    this.mWindow, restrictedKeepClearRects, unrestrictedKeepClearRects);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestInvalidateRootRenderNode() {
        this.mInvalidateRootRequested = true;
    }

    boolean scrollToRectOrFocus(Rect rectangle, boolean immediate) {
        int scrollY;
        Rect ci = this.mAttachInfo.mContentInsets;
        Rect vi = this.mAttachInfo.mVisibleInsets;
        int scrollY2 = 0;
        boolean handled = false;
        if (vi.left > ci.left || vi.top > ci.top || vi.right > ci.right || vi.bottom > ci.bottom) {
            scrollY2 = this.mScrollY;
            View focus = this.mView.findFocus();
            if (focus == null) {
                return false;
            }
            View lastScrolledFocus =
                    this.mLastScrolledFocus != null ? this.mLastScrolledFocus.get() : null;
            if (focus != lastScrolledFocus) {
                rectangle = null;
            }
            if (DEBUG_INPUT_RESIZE) {
                Log.v(
                        this.mTag,
                        "Eval scroll: focus="
                                + focus
                                + " rectangle="
                                + rectangle
                                + " ci="
                                + ci
                                + " vi="
                                + vi);
            }
            if (focus == lastScrolledFocus && !this.mScrollMayChange && rectangle == null) {
                if (DEBUG_INPUT_RESIZE) {
                    Log.v(
                            this.mTag,
                            "Keeping scroll y=" + this.mScrollY + " vi=" + vi.toShortString());
                }
            } else {
                this.mLastScrolledFocus = new WeakReference<>(focus);
                this.mScrollMayChange = false;
                if (DEBUG_INPUT_RESIZE) {
                    Log.v(this.mTag, "Need to scroll?");
                }
                if (focus.getGlobalVisibleRect(this.mVisRect, null)) {
                    if (DEBUG_INPUT_RESIZE) {
                        Log.v(
                                this.mTag,
                                "Root w="
                                        + this.mView.getWidth()
                                        + " h="
                                        + this.mView.getHeight()
                                        + " ci="
                                        + ci.toShortString()
                                        + " vi="
                                        + vi.toShortString());
                    }
                    if (rectangle == null) {
                        focus.getFocusedRect(this.mTempRect);
                        if (DEBUG_INPUT_RESIZE) {
                            Log.v(
                                    this.mTag,
                                    "Focus "
                                            + focus
                                            + ": focusRect="
                                            + this.mTempRect.toShortString());
                        }
                        if (this.mView instanceof ViewGroup) {
                            try {
                                ((ViewGroup) this.mView)
                                        .offsetDescendantRectToMyCoords(focus, this.mTempRect);
                            } catch (IllegalArgumentException ex) {
                                Log.e(
                                        this.mTag,
                                        "offsetDescendantRectToMyCoords() error occurred. focus="
                                                + focus
                                                + " mTempRect="
                                                + this.mTempRect.toShortString()
                                                + " "
                                                + ex);
                                ex.printStackTrace();
                            }
                        }
                        if (DEBUG_INPUT_RESIZE) {
                            Log.v(
                                    this.mTag,
                                    "Focus in window: focusRect="
                                            + this.mTempRect.toShortString()
                                            + " visRect="
                                            + this.mVisRect.toShortString());
                        }
                    } else {
                        this.mTempRect.set(rectangle);
                        if (DEBUG_INPUT_RESIZE) {
                            Log.v(
                                    this.mTag,
                                    "Request scroll to rect: "
                                            + this.mTempRect.toShortString()
                                            + " visRect="
                                            + this.mVisRect.toShortString());
                        }
                    }
                    if (this.mTempRect.intersect(this.mVisRect)) {
                        if (DEBUG_INPUT_RESIZE) {
                            Log.v(
                                    this.mTag,
                                    "Focus window visible rect: " + this.mTempRect.toShortString());
                        }
                        if (this.mTempRect.height()
                                > (this.mView.getHeight() - vi.top) - vi.bottom) {
                            if (DEBUG_INPUT_RESIZE) {
                                Log.v(this.mTag, "Too tall; leaving scrollY=" + scrollY2);
                            }
                        } else {
                            if (this.mTempRect.top < vi.top) {
                                scrollY = this.mTempRect.top - vi.top;
                                if (DEBUG_INPUT_RESIZE) {
                                    Log.v(this.mTag, "Top covered; scrollY=" + scrollY);
                                }
                            } else if (this.mTempRect.bottom > this.mView.getHeight() - vi.bottom) {
                                scrollY =
                                        this.mTempRect.bottom
                                                - (this.mView.getHeight() - vi.bottom);
                                if (DEBUG_INPUT_RESIZE) {
                                    Log.v(this.mTag, "Bottom covered; scrollY=" + scrollY);
                                }
                            } else {
                                scrollY2 = 0;
                            }
                            scrollY2 = scrollY;
                        }
                        handled = true;
                    }
                }
            }
        }
        if (scrollY2 != this.mScrollY) {
            if (DEBUG_INPUT_RESIZE) {
                Log.v(this.mTag, "Pan scroll changed: old=" + this.mScrollY + " , new=" + scrollY2);
            }
            if (!immediate) {
                if (this.mScroller == null) {
                    this.mScroller = new Scroller(this.mView.getContext());
                }
                this.mScroller.startScroll(0, this.mScrollY, 0, scrollY2 - this.mScrollY);
            } else if (this.mScroller != null) {
                this.mScroller.abortAnimation();
            }
            this.mScrollY = scrollY2;
        }
        return handled;
    }

    public void setScrollY(int scrollY) {
        if (this.mScroller != null) {
            this.mScroller.abortAnimation();
        }
        this.mScrollY = scrollY;
    }

    public int getScrollY() {
        return this.mScrollY;
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
                int virtualNodeId =
                        AccessibilityNodeInfo.getVirtualDescendantId(focusNode.getSourceNodeId());
                provider.performAction(virtualNodeId, 128, null);
            }
            focusNode.recycle();
        }
        if (this.mAccessibilityFocusedHost != null && this.mAccessibilityFocusedHost != view) {
            this.mAccessibilityFocusedHost.clearAccessibilityFocusNoCallbacks(64);
        }
        this.mAccessibilityFocusedHost = view;
        this.mAccessibilityFocusedVirtualView = node;
        updateKeepClearForAccessibilityFocusRect();
        requestInvalidateRootRenderNode();
        if (isAccessibilityFocusDirty()) {
            scheduleTraversals();
        }
    }

    boolean hasPointerCapture() {
        return this.mPointerCapture;
    }

    void requestPointerCapture(boolean enabled) {
        IBinder inputToken = getInputToken();
        if (inputToken == null) {
            Log.e(this.mTag, "No input channel to request Pointer Capture.");
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
        if (this.mView != null) {
            this.mView.dispatchPointerCaptureChanged(hasCapture);
        }
    }

    private void updateColorModeIfNeeded(int colorMode, float desiredRatio) {
        if (this.mAttachInfo.mThreadedRenderer == null) {
            return;
        }
        boolean isHdr = colorMode == 2 || colorMode == 3;
        if (isHdr && !this.mDisplay.isHdrSdrRatioAvailable()) {
            colorMode = 1;
            isHdr = false;
        }
        if (colorMode != 4 && !getConfiguration().isScreenWideColorGamut()) {
            colorMode = 0;
        }
        if (CoreRune.FW_SCREENSHOT_FOR_HDR
                && this.mForceModeInScreenshot
                && this.mInvalidateForScreenshotRunnable != null
                && colorMode != 2
                && colorMode != 3) {
            Log.i(this.mTag, "removeCallbacks mInvalidateForScreenshotRunnable");
            this.mHandler.removeCallbacks(this.mInvalidateForScreenshotRunnable);
        }
        float automaticRatio = this.mAttachInfo.mThreadedRenderer.setColorMode(colorMode);
        if (desiredRatio == 0.0f || desiredRatio > automaticRatio) {
            desiredRatio = automaticRatio;
        }
        this.mHdrRenderState.setDesiredHdrSdrRatio(isHdr, desiredRatio);
    }

    @Override // android.view.ViewParent
    public void requestChildFocus(View child, View focused) {
        if (DEBUG_INPUT_RESIZE) {
            Log.v(this.mTag, "Request child focus: focus now " + focused);
        }
        checkThread();
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [14] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    @Override // android.view.ViewParent
    public void clearChildFocus(View child) {
        if (DEBUG_INPUT_RESIZE) {
            Log.v(this.mTag, "Clearing child focus");
        }
        checkThread();
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [15] mView=" + this.mView);
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
        if (this.mView != null) {
            if (!this.mView.hasFocus()) {
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
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(
                    this.mTag,
                    "Traversal, [16] mView="
                            + this.mView
                            + " child="
                            + child
                            + " mWillDrawSoon="
                            + this.mWillDrawSoon);
        }
        if (this.mView == child) {
            if (this.mIsInTraversal) {
                this.mHandler.post(
                        new Runnable() { // from class:
                                         // android.view.ViewRootImpl$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ViewRootImpl.this.lambda$recomputeViewAttributes$9();
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
    public /* synthetic */ void lambda$recomputeViewAttributes$9() {
        this.mAttachInfo.mRecomputeGlobalAttributes = true;
    }

    void dispatchDetachedFromWindow() {
        this.mInsetsController.onWindowFocusLost();
        this.mCompatTranslator = null;
        clearSavedStickyDragEvent();
        if (this.mFirstInputStage != null) {
            this.mFirstInputStage.onDetachedFromWindow();
        }
        if (this.mView != null && this.mView.mAttachInfo != null) {
            this.mAttachInfo.mTreeObserver.dispatchOnWindowAttachedChange(false);
            this.mView.dispatchDetachedFromWindow();
        }
        this.mAccessibilityInteractionConnectionManager.ensureNoConnection();
        this.mAccessibilityInteractionConnectionManager.ensureNoDirectConnection();
        removeSendWindowContentChangedCallback();
        if (android.view.accessibility.Flags.preventLeakingViewrootimpl()
                && this.mAccessibilityInteractionController != null) {
            this.mAccessibilityInteractionController.destroy();
            this.mAccessibilityInteractionController = null;
        }
        destroyHardwareRenderer();
        setAccessibilityFocus(null, null);
        this.mInsetsController.cancelExistingAnimations();
        if (this.mView != null) {
            this.mView.assignParent(null);
            this.mView = null;
        }
        this.mBlurRegionAggregator.setViewRoot(null);
        Log.i(this.mTag, "dispatchDetachedFromWindow");
        if (this.mThread != Thread.currentThread()) {
            Log.w(
                    this.mTag,
                    "There is possible to occur CalledFromWrongThreadException. "
                            + Debug.getCallers(10));
        }
        this.mAttachInfo.mRootView = null;
        destroySurface();
        if (this.mInputQueueCallback != null && this.mInputQueue != null) {
            this.mInputQueueCallback.onInputQueueDestroyed(this.mInputQueue);
            this.mInputQueue.dispose();
            this.mInputQueueCallback = null;
            this.mInputQueue = null;
        }
        try {
            this.mWindowSession.remove(this.mWindow.asBinder());
        } catch (RemoteException e) {
        }
        if (this.mInputEventReceiver != null) {
            this.mInputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
        unregisterListeners();
        unscheduleTraversals();
        if (CoreRune.BIXBY_TOUCH && this.mSemPressGestureDetector != null) {
            this.mSemPressGestureDetector.onDetached();
        }
        this.mIsDetached = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performConfigurationChange(
            MergedConfiguration mergedConfiguration,
            boolean force,
            int newDisplayId,
            ActivityWindowInfo activityWindowInfo) {
        if (mergedConfiguration == null) {
            throw new IllegalArgumentException("No merged config provided.");
        }
        int lastRotation =
                this.mLastReportedMergedConfiguration
                        .getMergedConfiguration()
                        .windowConfiguration
                        .getRotation();
        int newRotation =
                mergedConfiguration.getMergedConfiguration().windowConfiguration.getRotation();
        if (lastRotation != newRotation) {
            this.mUpdateSurfaceNeeded = true;
            if (!this.mIsInTraversal) {
                this.mForceNextWindowRelayout = true;
            }
        }
        Configuration globalConfig = mergedConfiguration.getGlobalConfiguration();
        Configuration overrideConfig = mergedConfiguration.getOverrideConfiguration();
        if (DEBUG_CONFIGURATION) {
            Log.v(
                    this.mTag,
                    "Applying new config to window "
                            + ((Object) this.mWindowAttributes.getTitle())
                            + ", globalConfig: "
                            + globalConfig
                            + ", overrideConfig: "
                            + overrideConfig);
        }
        CompatibilityInfo ci = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
        if (!ci.equals(CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO)) {
            globalConfig = new Configuration(globalConfig);
            ci.applyToConfiguration(this.mNoncompatDensity, globalConfig);
        }
        Configuration lastOverrideConfig =
                this.mLastReportedMergedConfiguration.getOverrideConfiguration();
        boolean changeNightDim = lastOverrideConfig.nightDim != globalConfig.nightDim;
        if (changeNightDim) {
            boolean isDeskTopModeEnabled =
                    this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1;
            if (!isDeskTopModeEnabled) {
                int nightDimLevel = globalConfig.nightDim;
                ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                ThreadedRenderer.setNightDimText(nightDimLevel);
                Log.i(
                        this.mTag,
                        "performConfigurationChange setNightDimText nightDimLevel="
                                + nightDimLevel);
                invalidateWorld(this.mView);
            }
        }
        boolean changeNightDim2 = CoreRune.MW_CAPTION_SHELL_BUG_FIX;
        if (changeNightDim2 && (this.mWindowSession instanceof WindowlessWindowManager)) {
            Rect lastBounds =
                    this.mLastReportedMergedConfiguration
                            .getOverrideConfiguration()
                            .windowConfiguration
                            .getBounds();
            Rect newBounds = overrideConfig.windowConfiguration.getBounds();
            if (!lastBounds.equals(newBounds)
                    && this.mAttachInfo != null
                    && this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.setLightCenter(this.mAttachInfo, newBounds);
            }
        }
        synchronized (sConfigCallbacks) {
            try {
                for (int i = sConfigCallbacks.size() - 1; i >= 0; i--) {
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
        if (this.mLastReportedActivityWindowInfo != null && activityWindowInfo != null) {
            this.mLastReportedActivityWindowInfo.set(activityWindowInfo);
        }
        this.mForceNextConfigUpdate = force;
        if (this.mActivityConfigCallback != null) {
            this.mActivityConfigCallback.onConfigurationChanged(
                    overrideConfig, newDisplayId, activityWindowInfo);
        } else {
            updateConfiguration(newDisplayId);
        }
        this.mForceNextConfigUpdate = false;
    }

    public void updateConfiguration(int newDisplayId) {
        if (this.mView == null) {
            return;
        }
        Resources localResources = this.mView.getResources();
        Configuration config = localResources.getConfiguration();
        if (newDisplayId != -1) {
            onMovedToDisplay(newDisplayId, config);
        }
        if (this.mForceNextConfigUpdate || this.mLastConfigurationFromResources.diff(config) != 0) {
            updateInternalDisplay(this.mDisplay.getDisplayId(), localResources);
            updateLastConfigurationFromResources(config);
            this.mView.dispatchConfigurationChanged(config);
            this.mForceNextWindowRelayout = true;
            requestLayout();
        }
        updateForceDarkMode();
    }

    private void updateLastConfigurationFromResources(Configuration resConfig) {
        int lastLayoutDirection = this.mLastConfigurationFromResources.getLayoutDirection();
        int currentLayoutDirection = resConfig.getLayoutDirection();
        this.mLastConfigurationFromResources.setTo(resConfig);
        if (lastLayoutDirection != currentLayoutDirection
                && this.mView != null
                && this.mViewLayoutDirectionInitial == 2) {
            this.mView.setLayoutDirection(currentLayoutDirection);
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

    public void setWebViewAttached(boolean attached) {
        this.mWebViewAttached = attached;
    }

    public boolean isWebViewAttached() {
        return this.mWebViewAttached;
    }

    final class ViewRootHandler extends Handler {
        ViewRootHandler() {}

        @Override // android.os.Handler
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
                case 39:
                    return "MSG_TOUCH_BOOST_TIMEOUT";
                case 40:
                    return "MSG_CHECK_INVALIDATION_IDLE";
                case 41:
                    return "MSG_REFRESH_POINTER_ICON";
                case 42:
                    return "MSG_FRAME_RATE_SETTING";
                case 43:
                    return "MSG_SURFACE_REPLACED_TIMEOUT";
                case 103:
                    return "MSG_SPEN_GESTURE_EVENT";
                case 104:
                    return "MSG_DISPATCH_LETTERBOX_DIRECTION_CHANGED";
                case 106:
                    return "MSG_TOUCH_HINT_TIMEOUT";
                default:
                    return super.getMessageName(message);
            }
        }

        @Override // android.os.Handler
        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            if (msg.what == 26 && msg.obj == null) {
                throw new NullPointerException(
                        "Attempted to call MSG_REQUEST_KEYBOARD_SHORTCUTS with null receiver:");
            }
            return super.sendMessageAtTime(msg, uptimeMillis);
        }

        @Override // android.os.Handler
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

        private void handleMessageImpl(Message msg) {
            long delta;
            switch (msg.what) {
                case 1:
                    ((View) msg.obj).invalidate();
                    return;
                case 2:
                    View.AttachInfo.InvalidateInfo info = (View.AttachInfo.InvalidateInfo) msg.obj;
                    info.target.invalidate(info.left, info.top, info.right, info.bottom);
                    info.recycle();
                    return;
                case 3:
                    ViewRootImpl.this.doDie();
                    return;
                case 4:
                case 5:
                    SomeArgs args = (SomeArgs) msg.obj;
                    ClientWindowFrames frames = (ClientWindowFrames) args.arg1;
                    boolean reportDraw = msg.what == 5;
                    MergedConfiguration mergedConfiguration = (MergedConfiguration) args.arg2;
                    InsetsState insetsState = (InsetsState) args.arg3;
                    ActivityWindowInfo activityWindowInfo = (ActivityWindowInfo) args.arg4;
                    boolean forceLayout = args.argi1 != 0;
                    boolean alwaysConsumeSystemBars = args.argi2 != 0;
                    int displayId = args.argi3;
                    int syncSeqId = args.argi4;
                    boolean dragResizing = args.argi5 != 0;
                    ViewRootImpl.this.handleResized(
                            frames,
                            reportDraw,
                            mergedConfiguration,
                            insetsState,
                            forceLayout,
                            alwaysConsumeSystemBars,
                            displayId,
                            syncSeqId,
                            dragResizing,
                            activityWindowInfo);
                    args.recycle();
                    return;
                case 6:
                    ViewRootImpl.this.handleWindowFocusChanged();
                    return;
                case 7:
                    SomeArgs args2 = (SomeArgs) msg.obj;
                    InputEvent event = (InputEvent) args2.arg1;
                    InputEventReceiver receiver = (InputEventReceiver) args2.arg2;
                    ViewRootImpl.this.enqueueInputEvent(event, receiver, 0, true);
                    args2.recycle();
                    return;
                case 8:
                    ViewRootImpl.this.handleAppVisibility(msg.arg1 != 0);
                    return;
                case 9:
                    ViewRootImpl.this.handleGetNewSurface();
                    return;
                case 11:
                    KeyEvent event2 = (KeyEvent) msg.obj;
                    if ((event2.getFlags() & 8) != 0) {
                        event2 = KeyEvent.changeFlags(event2, event2.getFlags() & (-9));
                    }
                    ViewRootImpl.this.enqueueInputEvent(event2, null, 1, true);
                    return;
                case 12:
                    KeyEvent event3 = (KeyEvent) msg.obj;
                    ViewRootImpl.this.enqueueInputEvent(event3, null, 0, true);
                    return;
                case 13:
                    ViewRootImpl.this.getImeFocusController().onScheduledCheckFocus();
                    return;
                case 14:
                    if (ViewRootImpl.this.mView != null) {
                        ViewRootImpl.this.mView.onCloseSystemDialogs((String) msg.obj);
                        return;
                    }
                    return;
                case 15:
                case 16:
                    DragEvent event4 = (DragEvent) msg.obj;
                    event4.mLocalState = ViewRootImpl.this.mLocalDragState;
                    boolean traceDragEvent = event4.mAction != 2;
                    if (traceDragEvent) {
                        try {
                            Trace.traceBegin(8L, "c#" + DragEvent.actionToString(event4.mAction));
                        } finally {
                            if (traceDragEvent) {
                                Trace.traceEnd(8L);
                            }
                        }
                    }
                    ViewRootImpl.this.handleDragEvent(event4);
                    if (traceDragEvent) {
                        return;
                    } else {
                        return;
                    }
                case 17:
                    ViewRootImpl.this.handleDispatchSystemUiVisibilityChanged();
                    return;
                case 18:
                    Configuration config = (Configuration) msg.obj;
                    if (config.isOtherSeqNewer(
                            ViewRootImpl.this.mLastReportedMergedConfiguration
                                    .getMergedConfiguration())) {
                        config =
                                ViewRootImpl.this.mLastReportedMergedConfiguration
                                        .getGlobalConfiguration();
                    }
                    ViewRootImpl.this.mPendingMergedConfiguration.setConfiguration(
                            config,
                            ViewRootImpl.this.mLastReportedMergedConfiguration
                                    .getOverrideConfiguration());
                    if (ViewRootImpl.this.mPendingActivityWindowInfo != null) {
                        ViewRootImpl.this.mPendingActivityWindowInfo.set(
                                ViewRootImpl.this.mLastReportedActivityWindowInfo);
                    }
                    ViewRootImpl.this.performConfigurationChange(
                            new MergedConfiguration(ViewRootImpl.this.mPendingMergedConfiguration),
                            false,
                            -1,
                            ViewRootImpl.this.mPendingActivityWindowInfo != null
                                    ? new ActivityWindowInfo(
                                            ViewRootImpl.this.mPendingActivityWindowInfo)
                                    : null);
                    return;
                case 19:
                    ViewRootImpl.this.mProcessInputEventsScheduled = false;
                    ViewRootImpl.this.doProcessInputEvents();
                    return;
                case 21:
                    ViewRootImpl.this.setAccessibilityFocus(null, null);
                    return;
                case 22:
                    if (ViewRootImpl.this.mView != null) {
                        ViewRootImpl.this.invalidateWorld(ViewRootImpl.this.mView);
                        return;
                    }
                    return;
                case 23:
                    if (ViewRootImpl.this.mAdded) {
                        int w = ViewRootImpl.this.mWinFrame.width();
                        int h = ViewRootImpl.this.mWinFrame.height();
                        int l = msg.arg1;
                        int t = msg.arg2;
                        ViewRootImpl.this.mTmpFrames.frame.left = l;
                        ViewRootImpl.this.mTmpFrames.frame.right = l + w;
                        ViewRootImpl.this.mTmpFrames.frame.top = t;
                        ViewRootImpl.this.mTmpFrames.frame.bottom = t + h;
                        ViewRootImpl.this.setFrame(ViewRootImpl.this.mTmpFrames.frame, false);
                        ViewRootImpl.this.maybeHandleWindowMove(ViewRootImpl.this.mWinFrame);
                        return;
                    }
                    return;
                case 24:
                    InputEvent event5 = (InputEvent) msg.obj;
                    ViewRootImpl.this.enqueueInputEvent(event5, null, 32, true);
                    return;
                case 25:
                    ViewRootImpl.this.handleDispatchWindowShown();
                    return;
                case 26:
                    IResultReceiver receiver2 = (IResultReceiver) msg.obj;
                    int deviceId = msg.arg1;
                    ViewRootImpl.this.handleRequestKeyboardShortcuts(receiver2, deviceId);
                    return;
                case 28:
                    boolean hasCapture = msg.arg1 != 0;
                    ViewRootImpl.this.handlePointerCaptureChanged(hasCapture);
                    return;
                case 29:
                    SomeArgs args3 = (SomeArgs) msg.obj;
                    InsetsState insetsState2 = (InsetsState) args3.arg1;
                    InsetsSourceControl.Array activeControls =
                            (InsetsSourceControl.Array) args3.arg2;
                    ViewRootImpl.this.handleInsetsControlChanged(insetsState2, activeControls);
                    args3.recycle();
                    return;
                case 30:
                    ViewRootImpl.this.systemGestureExclusionChanged();
                    return;
                case 31:
                    ImeTracker.Token statsToken = (ImeTracker.Token) msg.obj;
                    ImeTracker.forLogging().onProgress(statsToken, 30);
                    if (ViewRootImpl.this.mView == null) {
                        Log.e(
                                ViewRootImpl.TAG,
                                String.format(
                                        "Calling showInsets(%d,%b) on window that no longer has"
                                            + " views.",
                                        Integer.valueOf(msg.arg1), Boolean.valueOf(msg.arg2 == 1)));
                    }
                    ViewRootImpl.this.clearLowProfileModeIfNeeded(msg.arg1, msg.arg2 == 1);
                    ViewRootImpl.this.mInsetsController.show(msg.arg1, msg.arg2 == 1, statsToken);
                    return;
                case 32:
                    ImeTracker.Token statsToken2 = (ImeTracker.Token) msg.obj;
                    ImeTracker.forLogging().onProgress(statsToken2, 31);
                    ViewRootImpl.this.mInsetsController.hide(msg.arg1, msg.arg2 == 1, statsToken2);
                    return;
                case 33:
                    ViewRootImpl.this.handleScrollCaptureRequest(
                            (IScrollCaptureResponseListener) msg.obj);
                    return;
                case 34:
                    ViewRootImpl.this.handleWindowTouchModeChanged();
                    return;
                case 35:
                    ViewRootImpl.this.keepClearRectsChanged(msg.arg1 == 1);
                    return;
                case 36:
                    ViewRootImpl.this.reportKeepClearAreasChanged();
                    return;
                case 37:
                    Log.e(ViewRootImpl.this.mTag, "Timedout waiting to unpause for sync");
                    ViewRootImpl.this.mNumPausedForSync = 0;
                    ViewRootImpl.this.scheduleTraversals();
                    return;
                case 38:
                    ViewRootImpl.this.decorViewInterceptionChanged(msg.arg1 == 1);
                    return;
                case 39:
                    if (CoreRune.FW_DVRR_TOOLKIT_PROLONG_TOUCH_BOOST
                            && ViewRootImpl.this.mIsDragging) {
                        ViewRootImpl.this.mHandler.removeMessages(39);
                        ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(39, 750L);
                        ViewRootImpl.this.mIsDragging = false;
                        return;
                    }
                    ViewRootImpl.this.mIsFrameRateBoosting = false;
                    ViewRootImpl.this.mIsTouchBoosting = false;
                    if (CoreRune.FW_DVRR_TOOLKIT_POLICY) {
                        ViewRootImpl.this.mFrameRateCategoryChangeReason = 184549376;
                    }
                    if (!ViewRootImpl.this.mDrawnThisFrame) {
                        ViewRootImpl.this.setPreferredFrameRateCategory(1);
                        return;
                    }
                    return;
                case 40:
                    if (!ViewRootImpl.this.mIsTouchBoosting
                            && !ViewRootImpl.this.mIsFrameRateBoosting
                            && !ViewRootImpl.this.mInsetsAnimationRunning) {
                        delta =
                                (System.nanoTime() / 1000000)
                                        - ViewRootImpl.this.mLastUpdateTimeMillis;
                    } else {
                        delta = 0;
                    }
                    if (delta >= 750) {
                        ViewRootImpl.this.mFrameRateCategoryHighCount = 0;
                        ViewRootImpl.this.mFrameRateCategoryHighHintCount = 0;
                        ViewRootImpl.this.mFrameRateCategoryNormalCount = 0;
                        ViewRootImpl.this.mFrameRateCategoryLowCount = 0;
                        ViewRootImpl.this.mPreferredFrameRate = 0.0f;
                        ViewRootImpl.this.mPreferredFrameRateCategory = 1;
                        if (CoreRune.FW_DVRR_TOOLKIT_POLICY) {
                            ViewRootImpl.this.mFrameRateCategoryChangeReason = 201326592;
                        }
                        ViewRootImpl.this.updateFrameRateFromThreadedRendererViews();
                        ViewRootImpl.this.setPreferredFrameRate(
                                ViewRootImpl.this.mPreferredFrameRate);
                        ViewRootImpl.this.setPreferredFrameRateCategory(
                                ViewRootImpl.this.mPreferredFrameRateCategory);
                        ViewRootImpl.this.mInvalidationIdleMessagePosted = false;
                        ViewRootImpl.this.mIsPressedGesture = false;
                        return;
                    }
                    ViewRootImpl.this.mInvalidationIdleMessagePosted = true;
                    ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(40, 750 - delta);
                    return;
                case 41:
                    if (ViewRootImpl.this.mPointerIconEvent != null) {
                        ViewRootImpl.this.updatePointerIcon(ViewRootImpl.this.mPointerIconEvent);
                        return;
                    }
                    return;
                case 42:
                    ViewRootImpl.this.mPreferredFrameRate = 0.0f;
                    ViewRootImpl.this.mFrameRateCompatibility = 1;
                    return;
                case 43:
                    ViewRootImpl.this.mSurfaceReplaced = false;
                    return;
                case 103:
                    ViewRootImpl.this.handleDispatchSPenGestureEvent((InputEvent[]) msg.obj);
                    return;
                case 104:
                    ViewRootImpl.this.handleDispatchLetterboxDirectionChanged(msg.arg1);
                    return;
                case 105:
                    ViewRootImpl.this.handleWindowFocusInTaskChanged();
                    return;
                case 106:
                    ViewRootImpl.this.mIsTouchHint = false;
                    ViewRootImpl.this.mFrameRateCategoryChangeReason = 184549376;
                    ViewRootImpl.this.setFrameRateCategoryForTouchHint(1);
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$10(Runnable r) {
        this.mHandler.post(r);
    }

    boolean ensureTouchMode(boolean inTouchMode) {
        if (this.mAttachInfo.mInTouchMode == inTouchMode) {
            return false;
        }
        if (!inTouchMode) {
            try {
                Log.i(this.mTag, "setInTouchMode(false), " + Debug.getCallers(10));
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
        if (!inTouchMode && !this.mAttachInfo.mHasWindowFocus && this.mDesktopMode) {
            return false;
        }
        this.mAttachInfo.mInTouchMode = inTouchMode;
        this.mAttachInfo.mTreeObserver.dispatchOnTouchModeChanged(inTouchMode);
        try {
            return inTouchMode ? enterTouchMode() : leaveTouchMode();
        } catch (IllegalArgumentException ex) {
            Log.e(
                    TAG,
                    "ensureTouchModeLocally() error occurred. inTouchMode="
                            + inTouchMode
                            + " "
                            + ex);
            ex.printStackTrace();
            return false;
        }
    }

    private boolean enterTouchMode() {
        View focused;
        if (this.mView == null
                || !this.mView.hasFocus()
                || (focused = this.mView.findFocus()) == null
                || focused.isFocusableInTouchMode()) {
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
            if (vgParent.getDescendantFocusability() == 262144
                    && vgParent.isFocusableInTouchMode()) {
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
        if (this.mView == null) {
            return false;
        }
        if (this.mView.hasFocus()) {
            View focusedView = this.mView.findFocus();
            if (!(focusedView instanceof ViewGroup)
                    || ((ViewGroup) focusedView).getDescendantFocusability() != 262144) {
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
        ArrayList<Float> xList = new ArrayList<>(20);
        ArrayList<Float> yList = new ArrayList<>(20);
        float SsumX = 0.0f;
        float SsumY = 0.0f;
        float SsumMajor = 0.0f;
        float SsumMinor = 0.0f;
        boolean bPalm = false;
        int mScreenWidth = 0;
        int mScreenHeight = 0;
        int N = event.getPointerCount();
        float SvarX = 0.0f;
        if (this.mContext != null) {
            WindowManager wm =
                    (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
            Display disp = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            disp.getMetrics(metrics);
            mScreenWidth = metrics.widthPixels;
            mScreenHeight = metrics.heightPixels;
        }
        float TILT_TO_ZOOM_XVAR = mScreenHeight > mScreenWidth ? mScreenWidth : mScreenHeight;
        for (int i = 0; i < N; i++) {
            xList.add(Float.valueOf(event.getX(i)));
            yList.add(Float.valueOf(event.getY(i)));
            SsumX += event.getX(i);
            SsumY += event.getY(i);
            SsumMajor += event.getTouchMajor(i);
            SsumMinor += event.getTouchMinor(i);
        }
        float SmeanX = SsumX / N;
        float SsumEccen = SsumMajor / SsumMinor;
        int i2 = 0;
        while (i2 < N) {
            ArrayList<Float> xList2 = xList;
            ArrayList<Float> yList2 = yList;
            SvarX +=
                    (float)
                            Math.sqrt(
                                    (xList.get(i2).floatValue() - SmeanX)
                                            * (xList.get(i2).floatValue() - SmeanX));
            if (event.getPalm(i2) == 1.0f
                    || event.getPalm(i2) == 2.0f
                    || event.getPalm(i2) == 3.0f) {
                bPalm = true;
            }
            i2++;
            xList = xList2;
            yList = yList2;
        }
        float SvarX2 = SvarX / N;
        if (bPalm && event.getToolType(0) == 1 && event.getAction() != 1) {
            Log.i(TAG, "[ViewRootImpl] action cancel - 1, eccen:" + SsumEccen);
            return true;
        }
        if (event.getToolType(0) != 1
                || SsumMajor < 100.0f
                || SsumEccen <= 2.0f
                || SvarX2 >= TILT_TO_ZOOM_XVAR / (N + 4)) {
            return false;
        }
        Log.i(
                TAG,
                "[ViewRootImpl] action cancel - 2, Palm Sweep, SsumMajor:"
                        + SsumMajor
                        + " eccen:"
                        + SsumEccen
                        + " varX:"
                        + SvarX2
                        + " TILT_TO_ZOOM_XVAR"
                        + TILT_TO_ZOOM_XVAR
                        + " N"
                        + N);
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
                Log.v(ViewRootImpl.this.mTag, "Done with " + getClass().getSimpleName() + ". " + q);
            }
            if (this.mNext != null) {
                this.mNext.deliver(q);
            } else {
                ViewRootImpl.this.finishInputEvent(q);
            }
        }

        protected void onWindowFocusChanged(boolean hasWindowFocus) {
            if (this.mNext != null) {
                this.mNext.onWindowFocusChanged(hasWindowFocus);
            }
        }

        protected void onDetachedFromWindow() {
            if (this.mNext != null) {
                this.mNext.onDetachedFromWindow();
            }
        }

        protected boolean shouldDropInputEvent(QueuedInputEvent q) {
            String reason;
            if (ViewRootImpl.this.mView == null || !ViewRootImpl.this.mAdded) {
                Slog.w(
                        ViewRootImpl.this.mTag,
                        "Dropping event due to root view being removed: " + q.mEvent);
                Slog.e(
                        ViewRootImpl.this.mTag,
                        "mStopped="
                                + ViewRootImpl.this.mStopped
                                + " mHasWindowFocus="
                                + ViewRootImpl.this.mAttachInfo.mHasWindowFocus
                                + " mPausedForTransition="
                                + ViewRootImpl.this.mPausedForTransition);
                return true;
            }
            boolean hasWindowFocus = ViewRootImpl.this.mAttachInfo.mHasWindowFocus;
            if ((ViewRootImpl.this.mView instanceof DecorView)
                    && (q.mEvent instanceof KeyEvent)
                    && ((KeyEvent) q.mEvent).getKeyCode() == 4) {
                hasWindowFocus |= ((DecorView) ViewRootImpl.this.mView).hasWindowFocusInTask();
            }
            if (!hasWindowFocus
                    && ((!ViewRootImpl.this.mProcessingBackKey || !isBack(q.mEvent))
                            && (ViewRootImpl.this.mWindowAttributes.samsungFlags & 65536) == 0
                            && !q.mEvent.isFromSource(2)
                            && !ViewRootImpl.this.isAutofillUiShowing())) {
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
                Slog.w(ViewRootImpl.this.mTag, "Cancelling event (" + reason + "):" + q.mEvent);
                Slog.e(
                        ViewRootImpl.this.mTag,
                        "mStopped="
                                + ViewRootImpl.this.mStopped
                                + " mHasWindowFocus="
                                + ViewRootImpl.this.mAttachInfo.mHasWindowFocus
                                + " mPausedForTransition="
                                + ViewRootImpl.this.mPausedForTransition);
                return false;
            }
            Slog.w(ViewRootImpl.this.mTag, "Dropping event (" + reason + "):" + q.mEvent);
            Slog.e(
                    ViewRootImpl.this.mTag,
                    "mStopped="
                            + ViewRootImpl.this.mStopped
                            + " mHasWindowFocus="
                            + ViewRootImpl.this.mAttachInfo.mHasWindowFocus
                            + " mPausedForTransition="
                            + ViewRootImpl.this.mPausedForTransition);
            return true;
        }

        void dump(String prefix, PrintWriter writer) {
            if (this.mNext != null) {
                this.mNext.dump(prefix, writer);
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
            Trace.traceBegin(
                    traceTag, this.mTracePrefix + " id=0x" + Integer.toHexString(q.mEvent.getId()));
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
            if (this.mQueueTail == null) {
                this.mQueueHead = q;
                this.mQueueTail = q;
            } else {
                this.mQueueTail.mNext = q;
                this.mQueueTail = q;
            }
            this.mQueueLength++;
            Trace.traceCounter(4L, this.mTraceCounter, this.mQueueLength);
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
            this.mQueueLength--;
            Trace.traceCounter(4L, this.mTraceCounter, this.mQueueLength);
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

    final class NativePreImeInputStage extends AsyncInputStage
            implements InputQueue.FinishedInputEventCallback {
        public NativePreImeInputStage(InputStage next, String traceCounter) {
            super(next, traceCounter);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            if (q.mEvent instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) q.mEvent;
                if (isBack(keyEvent)) {
                    if (ViewRootImpl.this.mWindowlessBackKeyCallback != null) {
                        if (ViewRootImpl.this.mWindowlessBackKeyCallback.test(keyEvent)) {
                            return (keyEvent.getAction() != 1 || keyEvent.isCanceled()) ? 2 : 1;
                        }
                        return 0;
                    }
                    if (ViewRootImpl.this.mContext != null
                            && ViewRootImpl.this.mOnBackInvokedDispatcher
                                    .isOnBackInvokedCallbackEnabled()) {
                        return doOnBackKeyEvent(keyEvent);
                    }
                }
                if (ViewRootImpl.this.mInputQueue != null) {
                    ViewRootImpl.this.mInputQueue.sendInputEvent(q.mEvent, q, true, this);
                    return 3;
                }
            }
            return 0;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private int doOnBackKeyEvent(KeyEvent keyEvent) {
            WindowOnBackInvokedDispatcher dispatcher =
                    ViewRootImpl.this.getOnBackInvokedDispatcher();
            OnBackInvokedCallback topCallback = dispatcher.getTopCallback();
            if (dispatcher.isBackGestureInProgress()) {
                return 2;
            }
            if ((topCallback instanceof OnBackAnimationCallback)
                    && !(topCallback instanceof ImeBackAnimationController)) {
                OnBackAnimationCallback animationCallback = (OnBackAnimationCallback) topCallback;
                switch (keyEvent.getAction()) {
                    case 0:
                        if (keyEvent.getRepeatCount() == 0) {
                            animationCallback.onBackStarted(new BackEvent(0.0f, 0.0f, 0.0f, 0));
                            break;
                        }
                        break;
                    case 1:
                        if (keyEvent.isCanceled()) {
                            animationCallback.onBackCancelled();
                            break;
                        } else {
                            topCallback.onBackInvoked();
                            return 1;
                        }
                }
            } else if (topCallback != null && keyEvent.getAction() == 1) {
                if (!keyEvent.isCanceled()) {
                    topCallback.onBackInvoked();
                    return 1;
                }
                Log.d(
                        ViewRootImpl.this.mTag,
                        "Skip onBackInvoked(), reason: keyEvent.isCanceled=true");
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
                if (ViewRootImpl.this.mAttachInfo.mDragToken != null
                        && event.getKeyCode() == 4
                        && event.getAction() == 0) {
                    Log.i(
                            ViewRootImpl.this.mTag,
                            "Back key during drag in dex. Cancel drag and drop.");
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
            return ((ViewRune.WIDGET_PEN_SUPPORTED
                                    && ViewRootImpl.this.mView.dispatchKeyEventTextMultiSelection(
                                            event))
                            || ViewRootImpl.this.mView.dispatchKeyEventPreIme(event))
                    ? 1
                    : 0;
        }
    }

    final class ImeInputStage extends AsyncInputStage
            implements InputMethodManager.FinishedInputEventCallback {
        public ImeInputStage(InputStage next, String traceCounter) {
            super(next, traceCounter);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            int result =
                    ViewRootImpl.this.mImeFocusController.onProcessImeInputStage(
                            q, q.mEvent, ViewRootImpl.this.mWindowAttributes, this);
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
                Log.i(ViewRootImpl.this.mTag, "The input has been finished in ImeInputStage.");
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
                ViewRootImpl.this.mLastTouchPoint.x = event.getRawX();
                ViewRootImpl.this.mLastTouchPoint.y = event.getRawY();
                ViewRootImpl.this.mLastTouchSource = event.getSource();
                ViewRootImpl.this.mLastTouchDeviceId = event.getDeviceId();
                ViewRootImpl.this.mLastTouchPointerId = event.getPointerId(0);
                if (event.getActionMasked() == 1) {
                    ViewRootImpl.this.mLastClickToolType =
                            event.getToolType(event.getActionIndex());
                }
                if (event.mNeedWindowOffset
                        && (translator = ViewRootImpl.this.getCompatTranslator()) != null) {
                    translator.translateToScreen(ViewRootImpl.this.mLastTouchPoint);
                }
            }
            return 0;
        }
    }

    final class NativePostImeInputStage extends AsyncInputStage
            implements InputQueue.FinishedInputEventCallback {
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
            if (ViewRootImpl.this.mUnbufferedInputDispatch
                    && (q.mEvent instanceof MotionEvent)
                    && ((MotionEvent) q.mEvent).isTouchEvent()
                    && ViewRootImpl.isTerminalInputEvent(q.mEvent)) {
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
                    ViewRootImpl.this.mAttachInfo.mNextFocusLooped = false;
                    View v = focused.focusSearch(direction);
                    if (v != null && v != focused) {
                        if (ViewRootImpl.this.mAttachInfo.mNextFocusLooped) {
                            moveFocusToAdjacentWindow(direction);
                        }
                        focused.getFocusedRect(ViewRootImpl.this.mTempRect);
                        if (ViewRootImpl.this.mView instanceof ViewGroup) {
                            ((ViewGroup) ViewRootImpl.this.mView)
                                    .offsetDescendantRectToMyCoords(
                                            focused, ViewRootImpl.this.mTempRect);
                            ((ViewGroup) ViewRootImpl.this.mView)
                                    .offsetRectIntoDescendantCoords(v, ViewRootImpl.this.mTempRect);
                        }
                        if (v.requestFocus(direction, ViewRootImpl.this.mTempRect)) {
                            boolean isFastScrolling = event.getRepeatCount() > 0;
                            ViewRootImpl.this.playSoundEffect(
                                    SoundEffectConstants.getConstantForFocusDirection(
                                            direction, isFastScrolling));
                            return true;
                        }
                    } else if (moveFocusToAdjacentWindow(direction)) {
                        return true;
                    }
                    if (ViewRootImpl.this.mView.dispatchUnhandledMove(focused, direction)) {
                        return true;
                    }
                } else if (ViewRootImpl.this.mView.restoreDefaultFocus()
                        || moveFocusToAdjacentWindow(direction)) {
                    return true;
                }
            }
            return false;
        }

        private boolean moveFocusToAdjacentWindow(int direction) {
            if (ViewRootImpl.this.getConfiguration().windowConfiguration.getWindowingMode() != 6) {
                return false;
            }
            try {
                return ViewRootImpl.this.mWindowSession.moveFocusToAdjacentWindow(
                        ViewRootImpl.this.mWindow, direction);
            } catch (RemoteException e) {
                return false;
            }
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
                    ViewRootImpl.this.playSoundEffect(
                            SoundEffectConstants.getContantForFocusDirection(direction));
                    return true;
                }
            }
            if (cluster != null && cluster.restoreFocusInCluster(realDirection)) {
                ViewRootImpl.this.playSoundEffect(
                        SoundEffectConstants.getContantForFocusDirection(direction));
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
            Log.i(ViewRootImpl.this.mTag, "ViewPostIme key " + event.getAction());
            if (ViewRootImpl.this.mView.dispatchKeyEvent(event)) {
                if (InputRune.KNOX_CAPTURE_XCOVER_OR_TOP_KEY
                        && (((keycode = event.getKeyCode()) == 1015 || keycode == 1079)
                                && SystemProperties.getInt("sys.datawedge.prop", 0) == 1)) {
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
            if (event.getAction() == 0
                    && !KeyEvent.metaStateHasNoModifiers(event.getMetaState())
                    && event.getRepeatCount() == 0
                    && !KeyEvent.isModifierKey(event.getKeyCode())
                    && groupNavigationDirection == 0) {
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
            int i;
            MotionEvent event = (MotionEvent) q.mEvent;
            int action = event.getAction();
            if (action == 1 || action == 3) {
                ViewRootImpl.this.mIsPressedGesture = false;
            }
            if (ViewRootImpl.this.mMotionEventMonitor != null) {
                ViewRootImpl.this.mMotionEventMonitor.dispatchInputEvent(q.mEvent);
            }
            boolean handled = false;
            if (!com.android.text.flags.Flags.disableHandwritingInitiatorForIme()
                    || ViewRootImpl.this.mWindowAttributes.type != 2011) {
                handled = ViewRootImpl.this.mHandwritingInitiator.onTouchEvent(event);
            }
            if (handled) {
                ViewRootImpl.this.mLastClickToolType = event.getToolType(event.getActionIndex());
            }
            if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
                Log.i(ViewRootImpl.this.mTag, "ViewPostIme pointer " + action);
            } else if (action == 0
                    || action == 1
                    || (ViewRune.COMMON_IS_PRODUCT_DEV
                            && action != 2
                            && action != 7
                            && action != 213)) {
                Log.i(ViewRootImpl.this.mTag, "ViewPostIme pointer " + action);
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
            if (ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested
                    && !ViewRootImpl.this.mUnbufferedInputDispatch) {
                ViewRootImpl.this.mUnbufferedInputDispatch = true;
                if (ViewRootImpl.this.mConsumeBatchedInputScheduled) {
                    ViewRootImpl.this.scheduleConsumeBatchedInputImmediately();
                }
            }
            if (handled2
                    && ViewRootImpl.this.shouldTouchBoost(
                            action & 255, ViewRootImpl.this.mWindowAttributes.type)) {
                ViewRootImpl.this.mIsTouchBoosting = true;
                if (action == 0) {
                    ViewRootImpl.this.mIsPressedGesture = true;
                }
                ViewRootImpl viewRootImpl = ViewRootImpl.this;
                if (CoreRune.FW_DVRR_TOOLKIT_PRIORITIZE_HIGH_HINT) {
                    i = 4;
                } else {
                    i = ViewRootImpl.this.mLastPreferredFrameRateCategory;
                }
                viewRootImpl.setPreferredFrameRateCategory(i);
            }
            if (ViewRootImpl.this.mIsTouchBoosting && (action == 1 || action == 3)) {
                ViewRootImpl.this.mHandler.removeMessages(39);
                ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(39, 3000L);
            }
            if (CoreRune.FW_VRR_SEND_TOUCH_HINT) {
                if (handled2
                        && ViewRootImpl.this.shouldTouchHint(
                                action & 255, ViewRootImpl.this.mWindowAttributes.type)) {
                    ViewRootImpl.this.mIsTouchHint = true;
                    ViewRootImpl.this.setFrameRateCategoryForTouchHint(4);
                }
                if (ViewRootImpl.this.mIsTouchHint && (action == 1 || action == 3)) {
                    ViewRootImpl.this.mHandler.removeMessages(106);
                    ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(106, 3000L);
                }
            }
            return handled2 ? 1 : 0;
        }

        private void maybeUpdatePointerIcon(MotionEvent event) {
            boolean needsStylusPointerIcon = true;
            if (event.getPointerCount() != 1) {}
            int action = event.getActionMasked();
            if (event.isStylusPointer()) {
                if (!event.isHoverEvent() && event.getActionMasked() != 0) {
                    needsStylusPointerIcon = false;
                }
            } else if (!event.isHoverEvent() || !ViewRootImpl.this.mIsStylusPointerIconEnabled) {
                needsStylusPointerIcon = false;
            }
            if (!needsStylusPointerIcon && !event.isFromSource(8194)) {
                return;
            }
            if (action == 9 || action == 10) {
                ViewRootImpl.this.mResolvedPointerIcon = null;
            }
            if (action != 10
                    && action != 4
                    && !ViewRootImpl.this.updatePointerIcon(event)
                    && action == 7) {
                ViewRootImpl.this.mResolvedPointerIcon = null;
            }
            switch (action) {
                case 1:
                case 3:
                case 6:
                case 10:
                    if (ViewRootImpl.this.mPointerIconEvent != null) {
                        ViewRootImpl.this.mPointerIconEvent.recycle();
                    }
                    ViewRootImpl.this.mPointerIconEvent = null;
                    break;
                default:
                    ViewRootImpl.this.mPointerIconEvent = MotionEvent.obtain(event);
                    break;
            }
        }

        private int processTrackballEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            return ((!event.isFromSource(InputDevice.SOURCE_MOUSE_RELATIVE)
                                    || (ViewRootImpl.this.hasPointerCapture()
                                            && !ViewRootImpl.this.mView
                                                    .dispatchCapturedPointerEvent(event)))
                            && !ViewRootImpl.this.mView.dispatchTrackballEvent(event))
                    ? 0
                    : 1;
        }

        private int processGenericMotionEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            return ((event.isFromSource(InputDevice.SOURCE_TOUCHPAD)
                                    && ViewRootImpl.this.hasPointerCapture()
                                    && ViewRootImpl.this.mView.dispatchCapturedPointerEvent(event))
                            || ViewRootImpl.this.mView.dispatchGenericMotionEvent(event))
                    ? 1
                    : 0;
        }
    }

    public boolean isHandlingPointerEvent() {
        return this.mAttachInfo.mHandlingPointerEvent;
    }

    public void refreshPointerIcon() {
        this.mHandler.removeMessages(41);
        this.mHandler.sendEmptyMessage(41);
    }

    public boolean isDesktopMode() {
        return this.mDesktopMode;
    }

    public boolean isDesktopModeStandAlone() {
        return this.mDesktopModeStandAlone;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void windowFocusInTaskChanged(boolean hasFocus) {
        synchronized (this) {
            this.mWindowFocusInTaskChanged = true;
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
                if (this.mView instanceof DecorView) {
                    ((DecorView) this.mView).onWindowFocusInTaskChanged(hasWindowFocusInTask);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0126 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00f1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean updatePointerIcon(android.view.MotionEvent r23) {
        /*
            Method dump skipped, instructions count: 360
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.ViewRootImpl.updatePointerIcon(android.view.MotionEvent):boolean");
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
        if (this.mAccessibilityManager.isEnabled()
                && this.mAccessibilityManager.isTouchExplorationEnabled()) {
            return;
        }
        if (this.mView == null) {
            Slog.d(this.mTag, "maybeUpdateTooltip called after view was removed");
        } else {
            this.mView.dispatchTooltipHoverEvent(event);
        }
    }

    private int mappingToMousePointer(int iconId) {
        switch (iconId) {
            case 20001:
            case 20010:
                return 10121;
            case 20002:
            case 20003:
            case 20004:
            case 20005:
            default:
                if (iconId > 20000) {
                    return iconId + TaskConstants.TASK_CHILD_LAYER_LETTERBOX_BACKGROUND + 10100;
                }
                return iconId;
            case 20006:
                return 10122;
            case 20007:
                return 10123;
            case 20008:
                return 10124;
            case 20009:
                return 10125;
        }
    }

    private View getFocusedViewOrNull() {
        if (this.mView != null) {
            return this.mView.findFocus();
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
                    if (!event.isFromSource(InputDevice.SOURCE_MOUSE_RELATIVE)) {
                        this.mTrackball.process(event);
                    }
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
        private final TrackballAxis mX = new TrackballAxis();
        private final TrackballAxis mY = new TrackballAxis();

        SyntheticTrackballHandler() {}

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
                this.mX.reset(0);
                this.mY.reset(0);
                this.mLastTime = curTime3;
            }
            int action = event.getAction();
            int metaState = event.getMetaState();
            switch (action) {
                case 0:
                    curTime = curTime3;
                    this.mX.reset(2);
                    this.mY.reset(2);
                    ViewRootImpl.this.enqueueInputEvent(
                            new KeyEvent(curTime, curTime, 0, 23, 0, metaState, -1, 0, 1024, 257));
                    break;
                case 1:
                    this.mX.reset(2);
                    this.mY.reset(2);
                    curTime = curTime3;
                    ViewRootImpl.this.enqueueInputEvent(
                            new KeyEvent(
                                    curTime3, curTime3, 1, 23, 0, metaState, -1, 0, 1024, 257));
                    break;
                default:
                    curTime = curTime3;
                    break;
            }
            if (ViewRootImpl.DEBUG_TRACKBALL) {
                Log.v(
                        ViewRootImpl.this.mTag,
                        "TB X="
                                + this.mX.position
                                + " step="
                                + this.mX.step
                                + " dir="
                                + this.mX.dir
                                + " acc="
                                + this.mX.acceleration
                                + " move="
                                + event.getX()
                                + " / Y="
                                + this.mY.position
                                + " step="
                                + this.mY.step
                                + " dir="
                                + this.mY.dir
                                + " acc="
                                + this.mY.acceleration
                                + " move="
                                + event.getY());
            }
            float xOff =
                    this.mX.collect(event.getX(), event.getEventTime(), GnssSignalType.CODE_TYPE_X);
            float yOff =
                    this.mY.collect(event.getY(), event.getEventTime(), GnssSignalType.CODE_TYPE_Y);
            int movement = 0;
            if (xOff > yOff) {
                movement = this.mX.generate();
                if (movement == 0) {
                    keycode = 0;
                    accel = 1.0f;
                } else {
                    int keycode3 = movement > 0 ? 22 : 21;
                    float accel2 = this.mX.acceleration;
                    this.mY.reset(2);
                    keycode = keycode3;
                    accel = accel2;
                }
            } else if (yOff <= 0.0f) {
                keycode = 0;
                accel = 1.0f;
            } else {
                movement = this.mY.generate();
                if (movement == 0) {
                    keycode = 0;
                    accel = 1.0f;
                } else {
                    int keycode4 = movement > 0 ? 20 : 19;
                    float accel3 = this.mY.acceleration;
                    this.mX.reset(2);
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
                    Log.v(
                            ViewRootImpl.this.mTag,
                            "Move: movement="
                                    + movement
                                    + " accelMovement="
                                    + accelMovement
                                    + " accel="
                                    + accel);
                }
                if (accelMovement <= movement) {
                    str = "Delivering fake DPAD: ";
                    keycode2 = keycode;
                    curTime2 = curTime;
                } else {
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.v(ViewRootImpl.this.mTag, "Delivering fake DPAD: " + keycode);
                    }
                    int movement2 = movement - 1;
                    int repeatCount = accelMovement - movement2;
                    str = "Delivering fake DPAD: ";
                    keycode2 = keycode;
                    ViewRootImpl.this.enqueueInputEvent(
                            new KeyEvent(
                                    curTime,
                                    curTime,
                                    2,
                                    keycode,
                                    repeatCount,
                                    metaState,
                                    -1,
                                    0,
                                    1024,
                                    257));
                    movement = movement2;
                    curTime2 = curTime;
                }
                while (movement > 0) {
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        str2 = str;
                        Log.v(ViewRootImpl.this.mTag, str2 + keycode2);
                    } else {
                        str2 = str;
                    }
                    long curTime4 = SystemClock.uptimeMillis();
                    int i = keycode2;
                    ViewRootImpl.this.enqueueInputEvent(
                            new KeyEvent(curTime4, curTime4, 0, i, 0, metaState, -1, 0, 1024, 257));
                    ViewRootImpl.this.enqueueInputEvent(
                            new KeyEvent(curTime4, curTime4, 1, i, 0, metaState, -1, 0, 1024, 257));
                    movement--;
                    str = str2;
                    keycode2 = keycode2;
                    curTime2 = curTime4;
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

        TrackballAxis() {}

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
                        Log.v(ViewRootImpl.TAG, axis + " reversed to positive!");
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
                        Log.v(ViewRootImpl.TAG, axis + " reversed to negative!");
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
                        Log.v(
                                ViewRootImpl.TAG,
                                axis
                                        + " accelerate: off="
                                        + off
                                        + " normTime="
                                        + normTime
                                        + " delta="
                                        + delta
                                        + " scale="
                                        + scale
                                        + " acc="
                                        + acc);
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
                        Log.v(
                                ViewRootImpl.TAG,
                                axis
                                        + " deccelerate: off="
                                        + off
                                        + " normTime="
                                        + normTime
                                        + " delta="
                                        + delta
                                        + " scale="
                                        + scale2
                                        + " acc="
                                        + acc);
                    }
                    this.acceleration = acc > 1.0f ? acc : 1.0f;
                }
            }
            this.position += off;
            return Math.abs(this.position);
        }

        int generate() {
            int movement = 0;
            this.nonAccelMovement = 0;
            while (true) {
                int dir = this.position >= 0.0f ? 1 : -1;
                switch (this.step) {
                    case 0:
                        if (Math.abs(this.position) < 0.5f) {
                            return movement;
                        }
                        movement += dir;
                        this.nonAccelMovement += dir;
                        this.step = 1;
                        break;
                    case 1:
                        if (Math.abs(this.position) < 2.0f) {
                            return movement;
                        }
                        movement += dir;
                        this.nonAccelMovement += dir;
                        this.position -= dir * 2.0f;
                        this.step = 2;
                        break;
                    default:
                        if (Math.abs(this.position) < 1.0f) {
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

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                case 2:
                    if (ViewRootImpl.this.mAttachInfo.mHasWindowFocus) {
                        KeyEvent oldEvent = (KeyEvent) msg.obj;
                        KeyEvent e =
                                KeyEvent.changeTimeRepeat(
                                        oldEvent,
                                        SystemClock.uptimeMillis(),
                                        oldEvent.getRepeatCount() + 1);
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
                    Log.w(ViewRootImpl.this.mTag, "Unexpected action: " + event.getActionMasked());
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
                    ViewRootImpl.this.enqueueInputEvent(
                            KeyEvent.changeTimeRepeat(keyEvent, SystemClock.uptimeMillis(), 0));
                }
            }
            this.mDeviceKeyEvents.clear();
            this.mJoystickAxesState.resetState();
        }

        private void update(MotionEvent event) {
            int historySize = event.getHistorySize();
            for (int h = 0; h < historySize; h++) {
                long time = event.getHistoricalEventTime(h);
                this.mJoystickAxesState.updateStateForAxis(
                        event, time, 0, event.getHistoricalAxisValue(0, 0, h));
                this.mJoystickAxesState.updateStateForAxis(
                        event, time, 1, event.getHistoricalAxisValue(1, 0, h));
                this.mJoystickAxesState.updateStateForAxis(
                        event, time, 15, event.getHistoricalAxisValue(15, 0, h));
                this.mJoystickAxesState.updateStateForAxis(
                        event, time, 16, event.getHistoricalAxisValue(16, 0, h));
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

            JoystickAxesState() {}

            void resetState() {
                this.mAxisStatesHat[0] = 0;
                this.mAxisStatesHat[1] = 0;
                this.mAxisStatesStick[0] = 0;
                this.mAxisStatesStick[1] = 0;
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
                    Log.e(
                            ViewRootImpl.this.mTag,
                            "Unexpected axis " + axis + " in updateStateForAxis!");
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
                        ViewRootImpl.this.enqueueInputEvent(
                                new KeyEvent(
                                        time, time, 1, keyCode2, 0, metaState, deviceId, 0, 1024,
                                        source));
                        deviceId = deviceId;
                        SyntheticJoystickHandler.this.mDeviceKeyEvents.put(deviceId, null);
                    }
                    SyntheticJoystickHandler.this.removeMessages(repeatMessage);
                }
                if ((newState == 1 || newState == -1)
                        && (keyCode = joystickAxisAndStateToKeycode(axis, newState)) != 0) {
                    int deviceId2 = deviceId;
                    KeyEvent keyEvent =
                            new KeyEvent(
                                    time, time, 0, keyCode, 0, metaState, deviceId2, 0, 1024,
                                    source);
                    ViewRootImpl.this.enqueueInputEvent(keyEvent);
                    Message m =
                            SyntheticJoystickHandler.this.obtainMessage(repeatMessage, keyEvent);
                    m.setAsynchronous(true);
                    SyntheticJoystickHandler.this.sendMessageDelayed(
                            m, ViewConfiguration.getKeyRepeatTimeout());
                    SyntheticJoystickHandler.this.mDeviceKeyEvents.put(
                            deviceId2,
                            new KeyEvent(
                                    time, time, 1, keyCode, 0, metaState, deviceId2, 0, 1056,
                                    source));
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
                Log.e(ViewRootImpl.this.mTag, "Unknown axis " + axis + " or direction " + state);
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
            int gestureDetectorVelocityStrategy =
                    android.companion.virtual.flags.Flags
                                    .impulseVelocityStrategyForTouchNavigation()
                            ? 0
                            : -1;
            this.mGestureDetector =
                    new GestureDetector(
                            ViewRootImpl.this.mContext,
                            new GestureDetector
                                    .OnGestureListener() { // from class:
                                                           // android.view.ViewRootImpl.SyntheticTouchNavigationHandler.1
                                @Override // android.view.GestureDetector.OnGestureListener
                                public boolean onDown(MotionEvent e) {
                                    return true;
                                }

                                @Override // android.view.GestureDetector.OnGestureListener
                                public void onShowPress(MotionEvent e) {}

                                @Override // android.view.GestureDetector.OnGestureListener
                                public boolean onSingleTapUp(MotionEvent e) {
                                    SyntheticTouchNavigationHandler.this.dispatchTap(
                                            e.getEventTime());
                                    return true;
                                }

                                @Override // android.view.GestureDetector.OnGestureListener
                                public boolean onScroll(
                                        MotionEvent e1,
                                        MotionEvent e2,
                                        float distanceX,
                                        float distanceY) {
                                    return true;
                                }

                                @Override // android.view.GestureDetector.OnGestureListener
                                public void onLongPress(MotionEvent e) {}

                                @Override // android.view.GestureDetector.OnGestureListener
                                public boolean onFling(
                                        MotionEvent e1,
                                        MotionEvent e2,
                                        float velocityX,
                                        float velocityY) {
                                    SyntheticTouchNavigationHandler.this.dispatchFling(
                                            velocityX, velocityY, e2.getEventTime());
                                    return true;
                                }
                            },
                            (Handler) null,
                            gestureDetectorVelocityStrategy);
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
            ViewRootImpl.this.enqueueInputEvent(
                    new KeyEvent(
                            time,
                            time,
                            0,
                            keyCode,
                            0,
                            this.mPendingKeyMetaState,
                            this.mCurrentDeviceId,
                            0,
                            1024,
                            this.mCurrentSource));
            ViewRootImpl.this.enqueueInputEvent(
                    new KeyEvent(
                            time,
                            time,
                            1,
                            keyCode,
                            0,
                            this.mPendingKeyMetaState,
                            this.mCurrentDeviceId,
                            0,
                            1024,
                            this.mCurrentSource));
        }
    }

    final class SyntheticKeyboardHandler {
        SyntheticKeyboardHandler() {}

        public void process(KeyEvent event) {
            if ((event.getFlags() & 1024) != 0) {
                return;
            }
            KeyCharacterMap kcm = event.getKeyCharacterMap();
            int keyCode = event.getKeyCode();
            int metaState = event.getMetaState();
            KeyCharacterMap.FallbackAction fallbackAction =
                    kcm.getFallbackAction(keyCode, metaState);
            if (fallbackAction != null) {
                int flags = event.getFlags() | 1024;
                KeyEvent fallbackEvent =
                        KeyEvent.obtain(
                                event.getDownTime(),
                                event.getEventTime(),
                                event.getAction(),
                                fallbackAction.keyCode,
                                event.getRepeatCount(),
                                fallbackAction.metaState,
                                event.getDeviceId(),
                                event.getScanCode(),
                                flags,
                                event.getSource(),
                                null);
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
        if (this.mView != null && this.mAdded) {
            int what = event.mAction;
            if (CoreRune.FW_DVRR_TOOLKIT_PROLONG_TOUCH_BOOST && what == 2) {
                this.mIsDragging = true;
            }
            if (what == 1) {
                this.mCurrentDragView = null;
                this.mDragDescription = event.mClipDescription;
                if (this.mStartedDragViewForA11y != null) {
                    this.mStartedDragViewForA11y.sendWindowContentChangedAccessibilityEvent(128);
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
            } else {
                if (what == 2 || what == 3) {
                    this.mDragPoint.set(event.mX, event.mY);
                    if (this.mTranslator != null) {
                        this.mTranslator.translatePointInScreenToAppWindow(this.mDragPoint);
                    }
                    if (this.mCurScrollY != 0) {
                        this.mDragPoint.offset(0.0f, this.mCurScrollY);
                    }
                    event.mX = this.mDragPoint.x;
                    event.mY = this.mDragPoint.y;
                }
                View prevDragView = this.mCurrentDragView;
                if (what == 3 && event.mClipData != null) {
                    event.mClipData.prepareToEnterProcess(
                            this.mView.getContext().getAttributionSource());
                }
                boolean result = this.mView.dispatchDragEvent(event);
                if (!result && what == 1 && event.isStickyEvent()) {
                    Log.i(this.mTag, "Save sticky drag event");
                    this.mSavedStickyDragEvent = DragEvent.obtain(event);
                }
                if (what == 1) {
                    InputManagerGlobal.getInstance()
                            .setDragPointerInfo(
                                    getDragStateInputToken(),
                                    getDragDeviceId(),
                                    getDragPointerId());
                } else if (what == 4) {
                    InputManagerGlobal.getInstance().clreaDragPointerInfo();
                }
                boolean ignoreEvent = event.isEavesDrop();
                if (what == 2 && !ignoreEvent) {
                    if (event.mEventHandlerWasCalled) {
                        InputManagerGlobal.getInstance()
                                .setDragPointerIcon(PointerIcon.getSystemIcon(this.mContext, 1021));
                    } else {
                        InputManagerGlobal.getInstance()
                                .setDragPointerIcon(PointerIcon.getSystemIcon(this.mContext, 1012));
                        setDragFocus(null, event);
                    }
                    InputManagerGlobal.getInstance().updateDragPointerIcon(getDisplayId());
                }
                if (prevDragView != this.mCurrentDragView) {
                    if (prevDragView != null) {
                        try {
                            InputManagerGlobal.getInstance()
                                    .setDragPointerIcon(
                                            PointerIcon.getSystemIcon(this.mContext, 1012));
                            this.mWindowSession.dragRecipientExited(this.mWindow);
                        } catch (RemoteException e) {
                            Slog.e(this.mTag, "Unable to note drag target change");
                        }
                    }
                    if (this.mCurrentDragView != null) {
                        InputManagerGlobal.getInstance()
                                .setDragPointerIcon(PointerIcon.getSystemIcon(this.mContext, 1021));
                        this.mWindowSession.dragRecipientEntered(this.mWindow);
                    }
                    InputManagerGlobal.getInstance().updateDragPointerIcon(getDisplayId());
                }
                if (what == 3) {
                    try {
                        Log.i(this.mTag, "Reporting drop result: " + result);
                        this.mWindowSession.reportDropResult(this.mWindow, result);
                    } catch (RemoteException e2) {
                        Log.e(this.mTag, "Unable to report drop result");
                    }
                }
                if (what == 4) {
                    if (this.mStartedDragViewForA11y != null) {
                        if (!event.getResult()) {
                            this.mStartedDragViewForA11y.sendWindowContentChangedAccessibilityEvent(
                                    512);
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
                    if (this.mAttachInfo.mDragData != null) {
                        View.cleanUpPendingIntents(this.mAttachInfo.mDragData);
                        this.mAttachInfo.mDragData = null;
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
            Log.i(this.mTag, "sendSavedStickyDragEventIfNeeded");
            dispatchDragEvent(this.mSavedStickyDragEvent);
            this.mSavedStickyDragEvent = null;
        }
    }

    private void clearSavedStickyDragEvent() {
        if (this.mSavedStickyDragEvent != null) {
            Log.i(this.mTag, "clearSavedStickyDragEvent");
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
        if (this.mView != null) {
            this.mView.requestKeyboardShortcuts(list, deviceId);
        }
        int numGroups = list.size();
        for (int i = 0; i < numGroups; i++) {
            KeyboardShortcutGroup group = list.get(i);
            group.setPackageName(this.mBasePackageName);
        }
        data.putParcelableArrayList(WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY, list);
        try {
            receiver.send(0, data);
        } catch (RemoteException e) {
        }
    }

    public void getLastTouchPoint(Point outLocation) {
        outLocation.x = (int) this.mLastTouchPoint.x;
        outLocation.y = (int) this.mLastTouchPoint.y;
    }

    public int getLastTouchSource() {
        return this.mLastTouchSource;
    }

    public int getLastTouchDeviceId() {
        return this.mLastTouchDeviceId;
    }

    public int getLastTouchPointerId() {
        return this.mLastTouchPointerId;
    }

    public int getLastClickToolType() {
        return this.mLastClickToolType;
    }

    public void setDragFocus(View newDragTarget, DragEvent event) {
        if (this.mCurrentDragView != newDragTarget && !View.sCascadedDragDrop) {
            float tx = event.mX;
            float ty = event.mY;
            int action = event.mAction;
            ClipData td = event.mClipData;
            event.mX = 0.0f;
            event.mY = 0.0f;
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
            event.mX = tx;
            event.mY = ty;
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
        if (this.mView == null) {
            throw new IllegalStateException("getAudioManager called when there is no mView");
        }
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) this.mView.getContext().getSystemService("audio");
            this.mFastScrollSoundEffectsEnabled =
                    this.mAudioManager.areNavigationRepeatSoundEffectsEnabled();
        }
        return this.mAudioManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AutofillManager getAutofillManager() {
        if (this.mView instanceof ViewGroup) {
            ViewGroup decorView = (ViewGroup) this.mView;
            if (decorView.getChildCount() > 0) {
                return (AutofillManager)
                        decorView
                                .getChildAt(0)
                                .getContext()
                                .getSystemService(AutofillManager.class);
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
            throw new IllegalStateException(
                    "getAccessibilityInteractionController called when there is no mView");
        }
        if (this.mAccessibilityInteractionController == null) {
            this.mAccessibilityInteractionController = new AccessibilityInteractionController(this);
        }
        return this.mAccessibilityInteractionController;
    }

    private boolean shouldNotLocalLayout(
            WindowConfiguration winConfig, WindowManager.LayoutParams params) {
        if (winConfig.isOverlappingWithCutout()) {
            return true;
        }
        if (!CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT) {
            return false;
        }
        DisplayCutout cutout = this.mInsetsController.getState().getDisplayCutout();
        int largeCutoutSize =
                Math.max(
                        Math.max(cutout.getSafeInsetLeft(), cutout.getSafeInsetRight()),
                        Math.max(cutout.getSafeInsetTop(), cutout.getSafeInsetBottom()));
        return largeCutoutSize > 0
                && largeCutoutSize <= this.mMinimumSizeForOverlappingWithCutoutAsDefault;
    }

    private boolean shouldNotLocalLayoutEmbedded(WindowConfiguration winConfig) {
        return winConfig.isEmbedded() && this.mWindowAttributes.type == 2038;
    }

    private boolean shouldNotLocalLayoutPopOver(WindowConfiguration winConfig) {
        InsetsSource imeSource;
        return winConfig.isPopOver()
                && (imeSource = this.mInsetsController.getState().peekSource(InsetsSource.ID_IME))
                        != null
                && imeSource.isVisible();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x03ab  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x03df  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x03e9  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0412  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x045d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0484  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x04c6  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x04cf  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x04ec  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x05d9  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x05db  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x04a3  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0409  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x03e1  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x03ad  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0238  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int relayoutWindow(android.view.WindowManager.LayoutParams r44, int r45, boolean r46)
            throws android.os.RemoteException {
        /*
            Method dump skipped, instructions count: 1524
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.ViewRootImpl.relayoutWindow(android.view.WindowManager$LayoutParams,"
                    + " int, boolean):int");
    }

    private void updateOpacity(
            WindowManager.LayoutParams params, boolean dragResizing, boolean forceUpdate) {
        boolean opaque = false;
        if (!PixelFormat.formatHasAlpha(params.format)
                && params.surfaceInsets.left == 0
                && params.surfaceInsets.top == 0
                && params.surfaceInsets.right == 0
                && params.surfaceInsets.bottom == 0
                && !dragResizing) {
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
            Log.i(this.mTag, "Force to draw a frame when frame become empty from non-empty");
            this.mForceDraw = true;
        }
        if (this.mPendingWinFrame != null && this.mPendingWinFrame.equals(frame)) {
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
        Rect rect2 = this.mPendingBackDropFrame;
        if (this.mPendingDragResizing && !winConfig.useWindowFrameForBackdrop()) {
            rect = winConfig.getMaxBounds();
        } else {
            rect = frame;
        }
        rect2.set(rect);
        this.mPendingBackDropFrame.offsetTo(0, 0);
        this.mInsetsController.onFrameChanged(
                this.mOverrideInsetsFrame != null ? this.mOverrideInsetsFrame : frame);
    }

    void setOverrideInsetsFrame(Rect frame) {
        this.mOverrideInsetsFrame = new Rect(frame);
        this.mInsetsController.onFrameChanged(this.mOverrideInsetsFrame);
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

    void applyInsetsHintSandboxingIfNeeded(InsetsSourceControl[] controls) {
        CompatSandbox.applyInsetsHintSandboxingIfNeeded(
                this.mLastReportedMergedConfiguration.getMergedConfiguration(), controls);
    }

    void applyViewBoundsSandboxingIfNeeded(Rect inOutRect) {
        applyViewBoundsSandboxingIfNeeded(inOutRect, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyViewBoundsSandboxingIfNeeded(Rect inOutRect, boolean inverse) {
        if (this.mViewBoundsSandboxingEnabled) {
            Rect bounds = getConfiguration().windowConfiguration.getBounds();
            if (inverse) {
                inOutRect.offset(bounds.left, bounds.top);
                return;
            } else {
                inOutRect.offset(-bounds.left, -bounds.top);
                return;
            }
        }
        CompatSandbox.applyViewBoundsSandboxingIfNeeded(
                this.mLastReportedMergedConfiguration.getMergedConfiguration(), inOutRect, inverse);
    }

    public void applyViewLocationSandboxingIfNeeded(int[] outLocation) {
        if (this.mViewBoundsSandboxingEnabled) {
            Rect bounds = getConfiguration().windowConfiguration.getBounds();
            outLocation[0] = outLocation[0] - bounds.left;
            outLocation[1] = outLocation[1] - bounds.top;
            return;
        }
        CompatSandbox.applyViewLocationSandboxingIfNeeded(
                this.mLastReportedMergedConfiguration.getMergedConfiguration(), outLocation);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0035 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0038 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean getViewBoundsSandboxingEnabled() {
        /*
            r4 = this;
            boolean r0 = android.app.ActivityThread.isSystem()
            r1 = 0
            if (r0 != 0) goto L39
            r2 = 237531167(0xe28701f, double:1.173559894E-315)
            boolean r0 = android.app.compat.CompatChanges.isChangeEnabled(r2)
            if (r0 != 0) goto L11
            goto L39
        L11:
            r0 = 1
            android.content.Context r2 = r4.mContext     // Catch: java.lang.RuntimeException -> L37
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch: java.lang.RuntimeException -> L37
            java.lang.String r3 = "android.window.PROPERTY_COMPAT_ALLOW_SANDBOXING_VIEW_BOUNDS_APIS"
            java.util.List r2 = r2.queryApplicationProperty(r3)     // Catch: java.lang.RuntimeException -> L37
            boolean r3 = r2.isEmpty()     // Catch: java.lang.RuntimeException -> L37
            if (r3 != 0) goto L32
            java.lang.Object r3 = r2.get(r1)     // Catch: java.lang.RuntimeException -> L37
            android.content.pm.PackageManager$Property r3 = (android.content.pm.PackageManager.Property) r3     // Catch: java.lang.RuntimeException -> L37
            boolean r3 = r3.getBoolean()     // Catch: java.lang.RuntimeException -> L37
            if (r3 != 0) goto L32
            r3 = r0
            goto L33
        L32:
            r3 = r1
        L33:
            if (r3 == 0) goto L36
            return r1
        L36:
            goto L38
        L37:
            r1 = move-exception
        L38:
            return r0
        L39:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.ViewRootImpl.getViewBoundsSandboxingEnabled():boolean");
    }

    @Override // android.view.View.AttachInfo.Callbacks
    public void playSoundEffect(int effectId) {
        if ((this.mDisplay.getFlags() & 1024) != 0) {
            return;
        }
        checkThread();
        try {
            AudioManager audioManager = getAudioManager();
            if (this.mFastScrollSoundEffectsEnabled
                    && SoundEffectConstants.isNavigationRepeat(effectId)) {
                audioManager.playSoundEffect(
                        SoundEffectConstants.nextNavigationRepeatSoundEffectId());
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
                    throw new IllegalArgumentException(
                            "unknown effect id "
                                    + effectId
                                    + " not defined in "
                                    + SoundEffectConstants.class.getCanonicalName());
            }
        } catch (IllegalStateException e) {
            Log.e(this.mTag, "FATAL EXCEPTION when attempting to play sound effect: " + e);
            e.printStackTrace();
        }
    }

    @Override // android.view.View.AttachInfo.Callbacks
    public boolean performHapticFeedback(int effectId, boolean always, boolean fromIme) {
        if ((this.mDisplay.getFlags() & 1024) != 0) {
            return false;
        }
        if (this.mDisplay.getDisplayId() != 0 && getConfiguration().isDesktopModeEnabled()) {
            return false;
        }
        try {
            this.mWindowSession.performHapticFeedbackAsync(effectId, always, fromIme);
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
        return FocusFinder.getInstance()
                .findNextKeyboardNavigationCluster(this.mView, currentCluster, direction);
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
        writer.println(
                innerPrefix
                        + "mConsumeBatchedInputScheduled="
                        + this.mConsumeBatchedInputScheduled);
        writer.println(
                innerPrefix
                        + "mConsumeBatchedInputImmediatelyScheduled="
                        + this.mConsumeBatchedInputImmediatelyScheduled);
        writer.println(innerPrefix + "mPendingInputEventCount=" + this.mPendingInputEventCount);
        writer.println(
                innerPrefix + "mProcessInputEventsScheduled=" + this.mProcessInputEventsScheduled);
        writer.println(innerPrefix + "mTraversalScheduled=" + this.mTraversalScheduled);
        if (this.mTraversalScheduled) {
            writer.println(
                    innerPrefix
                            + " (barrier="
                            + this.mTraversalBarrier
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
        writer.println(innerPrefix + "mReportNextDraw=" + this.mReportNextDraw);
        if (this.mReportNextDraw) {
            writer.println(
                    innerPrefix
                            + " (reason="
                            + this.mLastReportNextDrawReason
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mLastPerformTraversalsSkipDrawReason != null) {
            writer.println(
                    innerPrefix
                            + "mLastPerformTraversalsFailedReason="
                            + this.mLastPerformTraversalsSkipDrawReason);
        }
        if (this.mLastPerformDrawSkippedReason != null) {
            writer.println(
                    innerPrefix
                            + "mLastPerformDrawFailedReason="
                            + this.mLastPerformDrawSkippedReason);
        }
        if (this.mWmsRequestSyncGroupState != 0) {
            writer.println(
                    innerPrefix + "mWmsRequestSyncGroupState=" + this.mWmsRequestSyncGroupState);
        }
        writer.println(
                innerPrefix
                        + "mLastReportedMergedConfiguration="
                        + this.mLastReportedMergedConfiguration);
        writer.println(
                innerPrefix
                        + "mLastConfigurationFromResources="
                        + this.mLastConfigurationFromResources);
        if (this.mLastReportedActivityWindowInfo != null) {
            writer.println(
                    innerPrefix
                            + "mLastReportedActivityWindowInfo="
                            + this.mLastReportedActivityWindowInfo);
        }
        writer.println(innerPrefix + "mIsAmbientMode=" + this.mIsAmbientMode);
        writer.println(
                innerPrefix
                        + "mUnbufferedInputSource="
                        + Integer.toHexString(this.mUnbufferedInputSource));
        if (this.mAttachInfo != null) {
            writer.print(innerPrefix + "mAttachInfo= ");
            this.mAttachInfo.dump(innerPrefix, writer);
        } else {
            writer.println(innerPrefix + "mAttachInfo=<null>");
        }
        this.mFirstInputStage.dump(innerPrefix, writer);
        if (this.mInputEventReceiver != null) {
            this.mInputEventReceiver.dump(innerPrefix, writer);
        }
        this.mChoreographer.dump(prefix, writer);
        this.mInsetsController.dump(prefix, writer);
        this.mOnBackInvokedDispatcher.dump(prefix, writer);
        this.mImeBackAnimationController.dump(prefix, writer);
        this.mViewRootSurfaceController.dump(prefix, writer);
        writer.println(prefix + "View Hierarchy:");
        dumpViewHierarchy(innerPrefix, writer, this.mView);
    }

    private void dumpViewHierarchy(String prefix, PrintWriter writer, View view) {
        ViewGroup grp;
        int N;
        writer.print(prefix);
        if (view == null) {
            writer.println("null");
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

        GfxInfo() {}

        void add(GfxInfo other) {
            this.viewCount += other.viewCount;
            this.renderNodeMemoryUsage += other.renderNodeMemoryUsage;
            this.renderNodeMemoryAllocated += other.renderNodeMemoryAllocated;
        }
    }

    GfxInfo getGfxInfo() {
        GfxInfo info = new GfxInfo();
        if (this.mView != null) {
            appendGfxInfo(this.mView, info);
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
            Log.e(
                    this.mTag,
                    "Attempting to destroy the window while drawing!\n  window="
                            + this
                            + ", title="
                            + ((Object) this.mWindowAttributes.getTitle()));
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
            removeVrrMessages();
            if (this.mAdded) {
                dispatchDetachedFromWindow();
            }
            if (this.mAdded && !this.mFirst) {
                destroyHardwareRenderer();
                if (this.mView != null) {
                    int viewVisibility = this.mView.getVisibility();
                    boolean viewVisibilityChanged = this.mViewVisibility != viewVisibility;
                    if (this.mWindowAttributesChanged || viewVisibilityChanged) {
                        try {
                            if ((1 & relayoutWindow(this.mWindowAttributes, viewVisibility, false))
                                    != 0) {
                                this.mWindowSession.finishDrawing(
                                        this.mWindow, null, Integer.MAX_VALUE);
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
            handleSyncRequestWhenNoAsyncDraw(
                    this.mActiveSurfaceSyncGroup,
                    this.mHasPendingTransactions,
                    this.mPendingTransaction,
                    "shutting down VRI");
            this.mHasPendingTransactions = false;
            WindowManagerGlobal.getInstance().doRemoveView(this);
        }
    }

    public void requestUpdateConfiguration(Configuration config) {
        Message msg = this.mHandler.obtainMessage(18, config);
        this.mHandler.sendMessage(msg);
    }

    public void loadSystemProperties() {
        this.mHandler.post(
                new Runnable() { // from class: android.view.ViewRootImpl.10
                    @Override // java.lang.Runnable
                    public void run() {
                        ViewRootImpl.this.mProfileRendering =
                                SystemProperties.getBoolean(
                                        ViewRootImpl.PROPERTY_PROFILE_RENDERING, false);
                        ViewRootImpl.this.profileRendering(
                                ViewRootImpl.this.mAttachInfo.mHasWindowFocus);
                        if (ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null
                                && ViewRootImpl.this.mAttachInfo.mThreadedRenderer
                                        .loadSystemProperties()) {
                            ViewRootImpl.this.invalidate();
                        }
                        boolean layout =
                                DisplayProperties.debug_layout().orElse(false).booleanValue();
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
        this.mHdrRenderState.stopListening();
        if (hardwareRenderer != null) {
            if (this.mHardwareRendererObserver != null) {
                hardwareRenderer.removeObserver(this.mHardwareRendererObserver);
            }
            if (this.mView != null) {
                hardwareRenderer.destroyHardwareResources(this.mView);
            }
            hardwareRenderer.destroy();
            if (ViewRune.COMMON_IS_PRODUCT_DEV) {
                Log.d(this.mTag, "mThreadedRenderer.destroy()#4");
            }
            hardwareRenderer.setRequested(false);
            this.mAttachInfo.mThreadedRenderer = null;
            this.mAttachInfo.mHardwareAccelerated = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchResized(
            ClientWindowFrames clientWindowFrames,
            boolean z,
            MergedConfiguration mergedConfiguration,
            InsetsState insetsState,
            boolean z2,
            boolean z3,
            int i,
            int i2,
            boolean z4,
            ActivityWindowInfo activityWindowInfo) {
        Message obtainMessage = this.mHandler.obtainMessage(z ? 5 : 4);
        SomeArgs obtain = SomeArgs.obtain();
        Log.i(
                this.mTag,
                "Resizing "
                        + this
                        + ": frame = "
                        + clientWindowFrames.frame.toShortString()
                        + " reportDraw = "
                        + z
                        + " forceLayout = "
                        + z2
                        + " syncSeqId = "
                        + i2);
        if (this.mWindowAttributes.type == 1) {
            this.mPendingWinFrame = clientWindowFrames.frame;
        }
        obtain.arg1 = clientWindowFrames;
        obtain.arg2 = mergedConfiguration;
        obtain.arg3 = insetsState;
        obtain.arg4 = activityWindowInfo;
        obtain.argi1 = z2 ? 1 : 0;
        obtain.argi2 = z3 ? 1 : 0;
        obtain.argi3 = i;
        obtain.argi4 = i2;
        obtain.argi5 = z4 ? 1 : 0;
        obtainMessage.obj = obtain;
        this.mHandler.sendMessage(obtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchInsetsControlChanged(
            InsetsState insetsState, InsetsSourceControl.Array activeControls) {
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
            Log.v(this.mTag, "Window moved " + this + ": newX=" + newX + " newY=" + newY);
        }
        if (this.mTranslator != null) {
            PointF point = new PointF(newX, newY);
            this.mTranslator.translatePointInScreenToAppWindow(point);
            newX = (int) (point.x + 0.5d);
            newY = (int) (point.y + 0.5d);
        }
        CompatTranslator translator = getCompatTranslator();
        if (translator != null) {
            if (translator.savePositionInScreen(newX, newY)) {
                this.mAttachInfo.mNeedsUpdateLightCenter = true;
            }
            Point point2 = new Point(newX, newY);
            translator.translateToWindow(point2);
            newX = point2.x;
            newY = point2.y;
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

        private QueuedInputEvent() {}

        public boolean shouldSkipIme() {
            if ((this.mFlags & 1) != 0) {
                return true;
            }
            return (this.mEvent instanceof MotionEvent) && this.mEvent.isFromSource(2);
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
            if (!flagToString(
                    "UNHANDLED",
                    32,
                    flagToString(
                            "RESYNTHESIZED",
                            16,
                            flagToString(
                                    "FINISHED_HANDLED",
                                    8,
                                    flagToString(
                                            "FINISHED",
                                            4,
                                            flagToString("DEFERRED", 2, hasPrevious, sb),
                                            sb),
                                    sb),
                            sb),
                    sb)) {
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

    private QueuedInputEvent obtainQueuedInputEvent(
            InputEvent event, InputEventReceiver receiver, int flags) {
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
        if (this.mQueuedInputEventPoolSize < 10) {
            this.mQueuedInputEventPoolSize++;
            q.mNext = this.mQueuedInputEventPool;
            this.mQueuedInputEventPool = q;
        }
    }

    public void enqueueInputEvent(InputEvent event) {
        enqueueInputEvent(event, null, 0, false);
    }

    void enqueueInputEvent(
            InputEvent event, InputEventReceiver receiver, int flags, boolean processImmediately) {
        if (CoreRune.FW_SPEN_HOVER
                && (event instanceof KeyEvent)
                && (((KeyEvent) event).getFlags() & 33554432) != 0) {
            flags |= 1;
        }
        QueuedInputEvent q = obtainQueuedInputEvent(event, receiver, flags);
        if (event instanceof MotionEvent) {
            MotionEvent me = (MotionEvent) event;
            if (me.getAction() == 3) {
                EventLog.writeEvent(
                        EventLogTags.VIEW_ENQUEUE_INPUT_EVENT,
                        "Motion - Cancel",
                        getTitle().toString());
            }
        } else if (event instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) event;
            if (ke.isCanceled()) {
                EventLog.writeEvent(
                        EventLogTags.VIEW_ENQUEUE_INPUT_EVENT,
                        "Key - Cancel",
                        getTitle().toString());
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
        this.mPendingInputEventCount++;
        Trace.traceCounter(
                4L, this.mPendingInputEventQueueLengthCounterName, this.mPendingInputEventCount);
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
            this.mPendingInputEventHead = q.mNext;
            if (this.mPendingInputEventHead == null) {
                this.mPendingInputEventTail = null;
            }
            q.mNext = null;
            this.mPendingInputEventCount--;
            Trace.traceCounter(
                    4L,
                    this.mPendingInputEventQueueLengthCounterName,
                    this.mPendingInputEventCount);
            if (q.mEvent instanceof MotionEvent) {
                MotionEvent me = (MotionEvent) q.mEvent;
                if (this.mView != null
                        && ((this.mView.getTop() != 0 || this.mView.getLeft() != 0)
                                && this.mWindowAttributes.layoutInDisplayCutoutMode == 1
                                && this.mWindowAttributes.type == 2)) {
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

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00f4, code lost:

       r3 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00f9, code lost:

       throw r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x013e, code lost:

       r3 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0142, code lost:

       throw r3;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void deliverInputEvent(android.view.ViewRootImpl.QueuedInputEvent r11) {
        /*
            Method dump skipped, instructions count: 323
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.ViewRootImpl.deliverInputEvent(android.view.ViewRootImpl$QueuedInputEvent):void");
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
                    InputEvent processedEvent =
                            this.mInputCompatProcessor.processInputEventBeforeFinish(q.mEvent);
                    if (processedEvent != null) {
                        q.mReceiver.finishInputEvent(processedEvent, handled);
                    }
                } finally {
                    Trace.traceEnd(8L);
                }
            } else {
                q.mReceiver.finishInputEvent(q.mEvent, handled);
            }
            if (q.mEvent instanceof KeyEvent) {
                logHandledSystemKey((KeyEvent) q.mEvent, handled);
            }
        } else {
            q.mEvent.recycleIfNeededAfterDispatch();
        }
        recycleQueuedInputEvent(q);
    }

    private void logHandledSystemKey(KeyEvent event, boolean handled) {
        int keyCode = event.getKeyCode();
        if (keyCode == 264 && event.isDown() && event.getRepeatCount() == 0 && handled) {
            Counter.logIncrementWithUid(
                    "input.value_app_handled_stem_primary_key_gestures_count", Process.myUid());
        }
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
        if (this.mInputEventReceiver != null) {
            consumedBatches = this.mInputEventReceiver.consumeBatchedInputEvents(frameTimeNanos);
        } else {
            consumedBatches = false;
        }
        doProcessInputEvents();
        return consumedBatches;
    }

    final class TraversalRunnable implements Runnable {
        TraversalRunnable() {}

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
                List<InputEvent> processedEvents =
                        ViewRootImpl.this.mInputCompatProcessor.processInputEventForCompatibility(
                                event);
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
                    traceKey =
                            String.format(
                                    "(X=%d, Y=%d, Action=%d)",
                                    Integer.valueOf((int) motionEvent.getX()),
                                    Integer.valueOf((int) motionEvent.getY()),
                                    Integer.valueOf(motionEvent.getAction()));
                    Trace.traceBegin(8L, traceKey);
                }
                ViewRootImpl.this.enqueueInputEvent(event, this, 0, true);
                if (traceKey != null) {}
            } finally {
                Trace.traceEnd(8L);
            }
        }

        @Override // android.view.InputEventReceiver
        public void onBatchedInputEventPending(int source) {
            boolean unbuffered =
                    ViewRootImpl.this.mUnbufferedInputDispatch
                            || (ViewRootImpl.this.mUnbufferedInputSource & source) != 0;
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
            DragEvent event =
                    DragEvent.obtain(
                            isExiting ? 6 : 2,
                            x,
                            y,
                            0.0f,
                            0.0f,
                            0,
                            null,
                            null,
                            null,
                            null,
                            null,
                            false);
            ViewRootImpl.this.dispatchDragEvent(event);
        }

        @Override // android.view.InputEventReceiver
        public void dispose() {
            ViewRootImpl.this.unscheduleConsumeBatchedInput();
            super.dispose();
        }
    }

    final class InputMetricsListener
            implements HardwareRendererObserver.OnFrameMetricsAvailableListener {
        public long[] data = new long[23];

        InputMetricsListener() {}

        @Override // android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener
        public void onFrameMetricsAvailable(int dropCountSinceLastInvocation) {
            int inputEventId = (int) this.data[4];
            if (inputEventId == 0) {
                return;
            }
            long presentTime = this.data[21];
            if (presentTime <= 0) {
                return;
            }
            long gpuCompletedTime = this.data[19];
            if (ViewRootImpl.this.mInputEventReceiver == null) {
                return;
            }
            if (gpuCompletedTime >= presentTime) {
                double discrepancyMs = (gpuCompletedTime - presentTime) * 1.0E-6d;
                long vsyncId = this.data[1];
                Log.w(
                        ViewRootImpl.TAG,
                        "Not reporting timeline because gpuCompletedTime is "
                                + discrepancyMs
                                + "ms ahead of presentTime. FRAME_TIMELINE_VSYNC_ID="
                                + vsyncId
                                + ", INPUT_EVENT_ID="
                                + inputEventId);
                return;
            }
            ViewRootImpl.this.mInputEventReceiver.reportTimeline(
                    inputEventId, gpuCompletedTime, presentTime);
        }
    }

    final class ConsumeBatchedInputRunnable implements Runnable {
        ConsumeBatchedInputRunnable() {}

        @Override // java.lang.Runnable
        public void run() {
            Trace.traceBegin(8L, ViewRootImpl.this.mTag);
            try {
                ViewRootImpl.this.mConsumeBatchedInputScheduled = false;
                if (ViewRootImpl.this.doConsumeBatchedInput(
                        ViewRootImpl.this.mChoreographer.getFrameTimeNanos())) {
                    ViewRootImpl.this.scheduleConsumeBatchedInput();
                }
            } finally {
                Trace.traceEnd(8L);
            }
        }
    }

    final class ConsumeBatchedInputImmediatelyRunnable implements Runnable {
        ConsumeBatchedInputImmediatelyRunnable() {}

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

        InvalidateOnAnimationRunnable() {}

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
                    this.mTempViews =
                            (View[])
                                    this.mViews.toArray(
                                            this.mTempViews != null
                                                    ? this.mTempViews
                                                    : new View[viewCount]);
                    this.mViews.clear();
                }
                viewRectCount = this.mViewRects.size();
                if (viewRectCount != 0) {
                    this.mTempViewRects =
                            (View.AttachInfo.InvalidateInfo[])
                                    this.mViewRects.toArray(
                                            this.mTempViewRects != null
                                                    ? this.mTempViewRects
                                                    : new View.AttachInfo.InvalidateInfo
                                                            [viewRectCount]);
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

    public void dispatchInvalidateRectDelayed(
            View.AttachInfo.InvalidateInfo info, long delayMilliseconds) {
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
            Log.i(TAG, "dispatchDragEventUpdated() return.");
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
        DragEvent eventToRestart =
                DragEvent.obtain(
                        action,
                        x,
                        y,
                        offsetX,
                        offsetY,
                        event.getDragFlags(),
                        event.mLocalState,
                        event.getClipDescription(),
                        event.mClipData,
                        dragSurface,
                        event.mDragAndDropPermissions,
                        event.mDragResult);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(15, eventToRestart));
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
            this.mSendWindowContentChangedAccessibilityEvent =
                    new SendWindowContentChangedAccessibilityEvent();
        }
        this.mSendWindowContentChangedAccessibilityEvent.runOrPost(source, changeType);
    }

    private void removeSendWindowContentChangedCallback() {
        if (this.mSendWindowContentChangedAccessibilityEvent != null) {
            this.mHandler.removeCallbacks(this.mSendWindowContentChangedAccessibilityEvent);
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
    public ActionMode startActionModeForChild(
            View originalView, ActionMode.Callback callback, int type) {
        return null;
    }

    @Override // android.view.ViewParent
    public void createContextMenu(ContextMenu menu) {}

    @Override // android.view.ViewParent
    public void childDrawableStateChanged(View child) {}

    @Override // android.view.ViewParent
    public boolean requestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        AccessibilityNodeProvider provider;
        if (this.mView == null || this.mStopped || this.mPausedForTransition) {
            return false;
        }
        if (event.getEventType() != 2048
                && this.mSendWindowContentChangedAccessibilityEvent != null
                && this.mSendWindowContentChangedAccessibilityEvent.mSource != null) {
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
                    int virtualNodeId =
                            AccessibilityNodeInfo.getVirtualDescendantId(event.getSourceNodeId());
                    AccessibilityNodeInfo node =
                            provider.createAccessibilityNodeInfo(virtualNodeId);
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

    private boolean isAccessibilityFocusDirty() {
        Drawable drawable = this.mAttachInfo.mAccessibilityFocusDrawable;
        if (drawable != null) {
            Rect bounds = this.mAttachInfo.mTmpInvalRect;
            boolean hasFocus = getAccessibilityFocusedRect(bounds);
            if (!hasFocus) {
                bounds.setEmpty();
            }
            if (!bounds.equals(drawable.getBounds())) {
                return true;
            }
            return false;
        }
        return false;
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
        this.mAccessibilityFocusedVirtualView =
                provider.createAccessibilityNodeInfo(focusedChildId);
        if (this.mAccessibilityFocusedVirtualView == null) {
            this.mAccessibilityFocusedHost = null;
            focusedHost.clearAccessibilityFocusNoCallbacks(0);
            provider.performAction(
                    focusedChildId,
                    AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS
                            .getId(),
                    null);
            invalidateRectOnScreen(oldBounds);
            return;
        }
        Rect newBounds = this.mAccessibilityFocusedVirtualView.getBoundsInScreen();
        if (!oldBounds.equals(newBounds)) {
            oldBounds.union(newBounds);
            invalidateRectOnScreen(oldBounds);
        }
    }

    @Override // android.view.ViewParent
    public void notifySubtreeAccessibilityStateChanged(View child, View source, int changeType) {
        postSendWindowContentChangedCallback((View) Objects.requireNonNull(source), changeType);
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
            throw new CalledFromWrongThreadException(
                    "Only the original thread that created a view hierarchy can touch its views."
                        + " Expected: "
                            + this.mThread.getName()
                            + " Calling: "
                            + current.getName());
        }
    }

    @Override // android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {}

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
    public void childHasTransientStateChanged(View child, boolean hasTransientState) {}

    @Override // android.view.ViewParent
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return false;
    }

    @Override // android.view.ViewParent
    public void onStopNestedScroll(View target) {}

    @Override // android.view.ViewParent
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {}

    @Override // android.view.ViewParent
    public void onNestedScroll(
            View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {}

    @Override // android.view.ViewParent
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {}

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

    public boolean probablyHasInput() {
        if (this.mInputEventReceiver == null) {
            return false;
        }
        return this.mInputEventReceiver.probablyHasInput();
    }

    public void addScrollCaptureCallback(ScrollCaptureCallback callback) {
        if (this.mRootScrollCaptureCallbacks == null) {
            this.mRootScrollCaptureCallbacks = new HashSet<>();
        }
        this.mRootScrollCaptureCallbacks.add(callback);
    }

    public void removeScrollCaptureCallback(ScrollCaptureCallback callback) {
        if (this.mRootScrollCaptureCallbacks != null) {
            this.mRootScrollCaptureCallbacks.remove(callback);
            if (this.mRootScrollCaptureCallbacks.isEmpty()) {
                this.mRootScrollCaptureCallbacks = null;
            }
        }
    }

    public void dispatchScrollCaptureRequest(IScrollCaptureResponseListener listener) {
        this.mHandler.obtainMessage(33, listener).sendToTarget();
    }

    void processingBackKey(boolean processing) {
        this.mProcessingBackKey = processing;
    }

    private void collectRootScrollCaptureTargets(ScrollCaptureSearchResults results) {
        if (this.mRootScrollCaptureCallbacks == null) {
            return;
        }
        Iterator<ScrollCaptureCallback> it = this.mRootScrollCaptureCallbacks.iterator();
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
        final ScrollCaptureSearchResults results =
                new ScrollCaptureSearchResults(this.mContext.getMainExecutor());
        collectRootScrollCaptureTargets(results);
        View rootView = getView();
        if (rootView != null) {
            Point point = new Point();
            Rect rect = new Rect(0, 0, rootView.getWidth(), rootView.getHeight());
            getChildVisibleRect(rootView, rect, point);
            Objects.requireNonNull(results);
            rootView.dispatchScrollCaptureSearch(
                    rect,
                    point,
                    new Consumer() { // from class:
                                     // android.view.ViewRootImpl$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ScrollCaptureSearchResults.this.addTarget((ScrollCaptureTarget) obj);
                        }
                    });
        }
        Runnable onComplete =
                new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewRootImpl.this.lambda$handleScrollCaptureRequest$11(listener, results);
                    }
                };
        results.setOnCompleteListener(onComplete);
        if (!results.isComplete()) {
            ViewRootHandler viewRootHandler = this.mHandler;
            Objects.requireNonNull(results);
            viewRootHandler.postDelayed(
                    new Runnable() { // from class:
                                     // android.view.ViewRootImpl$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            ScrollCaptureSearchResults.this.finish();
                        }
                    },
                    getScrollCaptureRequestTimeout());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dispatchScrollCaptureSearchResponse, reason: merged with bridge method [inline-methods] */
    public void lambda$handleScrollCaptureRequest$11(
            IScrollCaptureResponseListener listener, ScrollCaptureSearchResults results) {
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
                Log.e(TAG, "Failed to send scroll capture search result", e);
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
        ScrollCaptureConnection connection =
                new ScrollCaptureConnection(
                        this.mView.getContext().getMainExecutor(), selectedTarget);
        response.setConnection(connection);
        try {
            listener.onScrollCaptureResponse(response.build());
        } catch (RemoteException e2) {
            if (DEBUG_SCROLL_CAPTURE) {
                Log.w(TAG, "Failed to send scroll capture search response.", e2);
            }
            connection.close();
        }
    }

    private void reportNextDraw(String reason) {
        if (DEBUG_BLAST) {
            Log.d(this.mTag, "reportNextDraw " + Debug.getCallers(5));
        }
        this.mReportNextDraw = true;
        this.mLastReportNextDrawReason = reason;
    }

    public void setReportNextDraw(boolean syncBuffer, String reason) {
        if (syncBuffer) {
            Log.i(
                    this.mTag,
                    "setReportNextDraw syncBuffer="
                            + syncBuffer
                            + ", reason="
                            + reason
                            + ", caller="
                            + Debug.getCallers(5));
        }
        this.mSyncBuffer = syncBuffer;
        reportNextDraw(reason);
        invalidate();
    }

    void changeCanvasOpacity(boolean opaque) {
        Log.d(this.mTag, "changeCanvasOpacity: opaque=" + opaque);
        boolean opaque2 = opaque & ((this.mView.mPrivateFlags & 512) == 0);
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.setOpaque(opaque2);
        }
    }

    public boolean dispatchUnhandledKeyEvent(KeyEvent event) {
        return this.mUnhandledKeyManager.dispatch(this.mView, event);
    }

    class TakenSurfaceHolder extends BaseSurfaceHolder {
        TakenSurfaceHolder() {}

        @Override // com.android.internal.view.BaseSurfaceHolder
        public boolean onAllowLockCanvas() {
            return ViewRootImpl.this.mDrawingAllowed;
        }

        @Override // com.android.internal.view.BaseSurfaceHolder
        public void onRelayoutContainer() {}

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

    static class W extends IWindow.Stub implements WindowStateTransactionItem.TransactionListener {
        private boolean mIsFromTransactionItem;
        private final WeakReference<ViewRootImpl> mViewAncestor;
        private final IWindowSession mWindowSession;

        W(ViewRootImpl viewAncestor) {
            this.mViewAncestor = new WeakReference<>(viewAncestor);
            this.mWindowSession = viewAncestor.mWindowSession;
        }

        @Override // android.app.servertransaction.WindowStateTransactionItem.TransactionListener
        public void onExecutingWindowStateTransactionItem() {
            this.mIsFromTransactionItem = true;
        }

        @Override // android.view.IWindow
        public void resized(
                ClientWindowFrames frames,
                boolean reportDraw,
                MergedConfiguration mergedConfiguration,
                InsetsState insetsState,
                boolean forceLayout,
                boolean alwaysConsumeSystemBars,
                int displayId,
                int syncSeqId,
                boolean dragResizing,
                ActivityWindowInfo activityWindowInfo) {
            MergedConfiguration mergedConfiguration2;
            ClientWindowFrames frames2;
            InsetsState insetsState2 = insetsState;
            boolean isFromResizeItem = this.mIsFromTransactionItem;
            boolean needsCopy = false;
            this.mIsFromTransactionItem = false;
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor == null) {
                return;
            }
            if (insetsState2.isSourceOrDefaultVisible(
                    InsetsSource.ID_IME, WindowInsets.Type.ime())) {
                ImeTracing.getInstance()
                        .triggerClientDump(
                                "ViewRootImpl.W#resized",
                                viewAncestor
                                        .getInsetsController()
                                        .getHost()
                                        .getInputMethodManager(),
                                null);
            }
            if (isFromResizeItem
                    && viewAncestor.mHandler.getLooper()
                            == ActivityThread.currentActivityThread().getLooper()) {
                viewAncestor.handleResized(
                        frames,
                        reportDraw,
                        mergedConfiguration,
                        insetsState,
                        forceLayout,
                        alwaysConsumeSystemBars,
                        displayId,
                        syncSeqId,
                        dragResizing,
                        activityWindowInfo);
                return;
            }
            if (!isFromResizeItem && Binder.getCallingPid() == Process.myPid()) {
                needsCopy = true;
            }
            if (!needsCopy) {
                mergedConfiguration2 = mergedConfiguration;
                frames2 = frames;
            } else {
                InsetsState insetsState3 = new InsetsState(insetsState2, true);
                frames2 = new ClientWindowFrames(frames);
                insetsState2 = insetsState3;
                mergedConfiguration2 = new MergedConfiguration(mergedConfiguration);
            }
            viewAncestor.dispatchResized(
                    frames2,
                    reportDraw,
                    mergedConfiguration2,
                    insetsState2,
                    forceLayout,
                    alwaysConsumeSystemBars,
                    displayId,
                    syncSeqId,
                    dragResizing,
                    activityWindowInfo);
        }

        @Override // android.view.IWindow
        public void insetsControlChanged(
                InsetsState insetsState, InsetsSourceControl.Array activeControls) {
            boolean isFromInsetsControlChangeItem;
            boolean needsCopy = false;
            if (com.android.window.flags.Flags.insetsControlChangedItem()) {
                isFromInsetsControlChangeItem = this.mIsFromTransactionItem;
                this.mIsFromTransactionItem = false;
            } else {
                isFromInsetsControlChangeItem = false;
            }
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor == null) {
                if (isFromInsetsControlChangeItem) {
                    activeControls.release();
                    return;
                }
                return;
            }
            if (insetsState.isSourceOrDefaultVisible(
                    InsetsSource.ID_IME, WindowInsets.Type.ime())) {
                ImeTracing.getInstance()
                        .triggerClientDump(
                                "ViewRootImpl#dispatchInsetsControlChanged",
                                viewAncestor
                                        .getInsetsController()
                                        .getHost()
                                        .getInputMethodManager(),
                                null);
            }
            if (isFromInsetsControlChangeItem
                    && viewAncestor.mHandler.getLooper()
                            == ActivityThread.currentActivityThread().getLooper()) {
                viewAncestor.handleInsetsControlChanged(insetsState, activeControls);
                return;
            }
            if (!isFromInsetsControlChangeItem && Binder.getCallingPid() == Process.myPid()) {
                needsCopy = true;
            }
            if (needsCopy) {
                insetsState = new InsetsState(insetsState, true);
                activeControls = new InsetsSourceControl.Array(activeControls, true);
            }
            viewAncestor.dispatchInsetsControlChanged(insetsState, activeControls);
        }

        @Override // android.view.IWindow
        public void showInsets(int types, boolean fromIme, ImeTracker.Token statsToken) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (fromIme) {
                ImeTracing.getInstance()
                        .triggerClientDump(
                                "ViewRootImpl.W#showInsets",
                                viewAncestor
                                        .getInsetsController()
                                        .getHost()
                                        .getInputMethodManager(),
                                null);
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
                ImeTracing.getInstance()
                        .triggerClientDump(
                                "ViewRootImpl.W#hideInsets",
                                viewAncestor
                                        .getInsetsController()
                                        .getHost()
                                        .getInputMethodManager(),
                                null);
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
                return ActivityManager.getService()
                        .checkPermission(
                                permission, Binder.getCallingPid(), Binder.getCallingUid());
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
                throw new SecurityException(
                        "Insufficient permissions to invoke executeCommand() from pid="
                                + Binder.getCallingPid()
                                + ", uid="
                                + Binder.getCallingUid());
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
        public void dispatchWallpaperOffsets(
                float x, float y, float xStep, float yStep, float zoom, boolean sync) {
            if (sync) {
                try {
                    this.mWindowSession.wallpaperOffsetsComplete(asBinder());
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperCommand(
                String action, int x, int y, int z, Bundle extras, boolean sync) {
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
        public void dumpWindow(final ParcelFileDescriptor pfd) {
            final ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor == null) {
                return;
            }
            viewAncestor.mHandler.postAtFrontOfQueue(
                    new Runnable() { // from class:
                                     // android.view.ViewRootImpl$W$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewRootImpl.W.lambda$dumpWindow$0(
                                    ParcelFileDescriptor.this, viewAncestor);
                        }
                    });
        }

        static /* synthetic */ void lambda$dumpWindow$0(
                ParcelFileDescriptor pfd, ViewRootImpl viewAncestor) {
            StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
            try {
                PrintWriter pw = new FastPrintWriter(new FileOutputStream(pfd.getFileDescriptor()));
                viewAncestor.dump("", pw);
                pw.flush();
            } finally {
                IoUtils.closeQuietly(pfd);
                StrictMode.setThreadPolicy(oldPolicy);
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
        public void invalidateForScreenShot(boolean forceMode) {
            final ViewRootImpl viewAncestor = this.mViewAncestor.get();
            final String tag =
                    viewAncestor.getTag() != null ? viewAncestor.getTag() : ViewRootImpl.TAG;
            Log.i(tag, "invalidateForScreenShot forceMode=" + forceMode);
            viewAncestor.mForceModeInScreenshot = forceMode;
            if (forceMode) {
                viewAncestor.mAttachInfo.mThreadedRenderer.setColorMode(1);
                viewAncestor.mAttachInfo.mThreadedRenderer.setTargetHdrSdrRatio(1.0f);
            } else {
                viewAncestor.mAttachInfo.mThreadedRenderer.setColorMode(2);
                viewAncestor.mAttachInfo.mThreadedRenderer.setTargetHdrSdrRatio(
                        viewAncestor.mHdrRenderState.getRenderHdrSdrRatio());
            }
            if (viewAncestor.mInvalidateForScreenshotRunnable == null) {
                viewAncestor.mInvalidateForScreenshotRunnable =
                        new Runnable() { // from class: android.view.ViewRootImpl.W.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Log.i(tag, "invalidateForScreenShot post vri invalidate");
                                viewAncestor.invalidate();
                            }
                        };
            }
            viewAncestor.mAttachInfo.mHandler.post(viewAncestor.mInvalidateForScreenshotRunnable);
        }

        @Override // android.view.IWindow
        public void windowFocusInTaskChanged(boolean hasFocus) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.windowFocusInTaskChanged(hasFocus);
            }
        }
    }

    public static final class CalledFromWrongThreadException extends AndroidRuntimeException {
        public CalledFromWrongThreadException(String msg) {
            super(msg);
        }
    }

    static HandlerActionQueue getRunQueue() {
        HandlerActionQueue rq = sRunQueues.get();
        if (rq != null) {
            return rq;
        }
        HandlerActionQueue rq2 = new HandlerActionQueue();
        sRunQueues.set(rq2);
        return rq2;
    }

    private void startDragResizing(
            Rect initialBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets) {
        if (!this.mDragResizing) {
            this.mDragResizing = true;
            if (this.mUseMTRenderer) {
                for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                    this.mWindowCallbacks
                            .get(i)
                            .onWindowDragResizeStart(
                                    initialBounds, fullscreen, systemInsets, stableInsets);
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
                updated |=
                        this.mWindowCallbacks
                                .get(i)
                                .onContentDrawn(
                                        this.mWindowAttributes.surfaceInsets.left,
                                        this.mWindowAttributes.surfaceInsets.top,
                                        this.mWidth,
                                        this.mHeight);
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
        if (this.mInputEventReceiver == null) {
            return null;
        }
        return this.mInputEventReceiver.getToken();
    }

    @Override // android.view.AttachedSurfaceControl
    public InputTransferToken getInputTransferToken() {
        IBinder inputToken = getInputToken();
        if (inputToken == null) {
            throw new IllegalStateException(
                    "Called getInputTransferToken for Window with no input channel");
        }
        return new InputTransferToken(inputToken);
    }

    public IBinder getWindowToken() {
        return this.mAttachInfo.mWindowToken;
    }

    final class AccessibilityInteractionConnectionManager
            implements AccessibilityManager.AccessibilityStateChangeListener {
        private int mDirectConnectionId = -1;

        AccessibilityInteractionConnectionManager() {}

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
        public void onAccessibilityStateChanged(boolean enabled) {
            if (enabled) {
                ensureConnection();
                ViewRootImpl.this.setAccessibilityWindowAttributesIfNeeded();
                if (ViewRootImpl.this.mAttachInfo.mHasWindowFocus
                        && ViewRootImpl.this.mView != null) {
                    ViewRootImpl.this.mView.sendAccessibilityEvent(32);
                    View focusedView = ViewRootImpl.this.mView.findFocus();
                    if (focusedView != null && focusedView != ViewRootImpl.this.mView) {
                        focusedView.sendAccessibilityEvent(8);
                    }
                }
                if (ViewRootImpl.this.mAttachInfo.mLeashedParentToken != null) {
                    ViewRootImpl.this.mAccessibilityManager.associateEmbeddedHierarchy(
                            ViewRootImpl.this.mAttachInfo.mLeashedParentToken,
                            ViewRootImpl.this.mLeashToken);
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
                ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId =
                        ViewRootImpl.this.mAccessibilityManager
                                .addAccessibilityInteractionConnection(
                                        ViewRootImpl.this.mWindow,
                                        ViewRootImpl.this.mLeashToken,
                                        ViewRootImpl.this.mContext.getPackageName(),
                                        new AccessibilityInteractionConnection(ViewRootImpl.this));
            }
        }

        public void ensureNoConnection() {
            boolean registered = ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1;
            if (registered) {
                ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = -1;
                ViewRootImpl.this.mAccessibilityWindowAttributes = null;
                ViewRootImpl.this.mAccessibilityManager.removeAccessibilityInteractionConnection(
                        ViewRootImpl.this.mWindow);
            }
        }

        public int ensureDirectConnection() {
            if (this.mDirectConnectionId == -1) {
                this.mDirectConnectionId =
                        AccessibilityInteractionClient.addDirectConnection(
                                new AccessibilityInteractionConnection(ViewRootImpl.this),
                                ViewRootImpl.this.mAccessibilityManager);
                ViewRootImpl.this.mAccessibilityManager.notifyAccessibilityStateChanged();
            }
            return this.mDirectConnectionId;
        }

        public void ensureNoDirectConnection() {
            if (this.mDirectConnectionId != -1) {
                AccessibilityInteractionClient.removeConnection(this.mDirectConnectionId);
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
            if (this.mView != null) {
                forceLayout(this.mView);
                return;
            }
            return;
        }
        if (isNotFromHandler) {
            this.mHCTRelayoutHandler.sendEmptyMessage(1);
        } else {
            Log.d(TAG, "Recursion detected");
        }
    }

    private final class HCTRelayoutHandler extends Handler {
        public static final int MSG_NEED_TO_DO_RELAYOUT = 1;

        public HCTRelayoutHandler() {}

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    ViewRootImpl.this.doRelayoutForHCT(false);
                    break;
            }
        }
    }

    final class HighContrastTextManager
            implements AccessibilityManager.HighTextContrastChangeListener {
        HighContrastTextManager() {
            ThreadedRenderer.setHighContrastText(
                    ViewRootImpl.this.mAccessibilityManager.isHighTextContrastEnabled());
        }

        @Override // android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener
        public void onHighTextContrastStateChanged(boolean enabled) {
            ThreadedRenderer.setHighContrastText(enabled);
            ViewRootImpl.this.doRelayoutForHCT(true);
        }
    }

    static final class AccessibilityInteractionConnection
            extends IAccessibilityInteractionConnection.Stub {
        private final WeakReference<ViewRootImpl> mViewRootImpl;

        AccessibilityInteractionConnection(ViewRootImpl viewRootImpl) {
            this.mViewRootImpl = new WeakReference<>(viewRootImpl);
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfoByAccessibilityId(
                long accessibilityNodeId,
                Region interactiveRegion,
                int interactionId,
                IAccessibilityInteractionConnectionCallback callback,
                int flags,
                int interrogatingPid,
                long interrogatingTid,
                MagnificationSpec spec,
                float[] matrix,
                Bundle args) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl
                        .getAccessibilityInteractionController()
                        .findAccessibilityNodeInfoByAccessibilityIdClientThread(
                                accessibilityNodeId,
                                interactiveRegion,
                                interactionId,
                                callback,
                                flags,
                                interrogatingPid,
                                interrogatingTid,
                                spec,
                                matrix,
                                args);
            } else {
                try {
                    callback.setFindAccessibilityNodeInfosResult(null, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void performAccessibilityAction(
                long accessibilityNodeId,
                int action,
                Bundle arguments,
                int interactionId,
                IAccessibilityInteractionConnectionCallback callback,
                int flags,
                int interrogatingPid,
                long interrogatingTid) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl
                        .getAccessibilityInteractionController()
                        .performAccessibilityActionClientThread(
                                accessibilityNodeId,
                                action,
                                arguments,
                                interactionId,
                                callback,
                                flags,
                                interrogatingPid,
                                interrogatingTid);
            } else {
                try {
                    callback.setPerformAccessibilityActionResult(false, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByViewId(
                long accessibilityNodeId,
                String viewId,
                Region interactiveRegion,
                int interactionId,
                IAccessibilityInteractionConnectionCallback callback,
                int flags,
                int interrogatingPid,
                long interrogatingTid,
                MagnificationSpec spec,
                float[] matrix) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl
                        .getAccessibilityInteractionController()
                        .findAccessibilityNodeInfosByViewIdClientThread(
                                accessibilityNodeId,
                                viewId,
                                interactiveRegion,
                                interactionId,
                                callback,
                                flags,
                                interrogatingPid,
                                interrogatingTid,
                                spec,
                                matrix);
            } else {
                try {
                    callback.setFindAccessibilityNodeInfoResult(null, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByText(
                long accessibilityNodeId,
                String text,
                Region interactiveRegion,
                int interactionId,
                IAccessibilityInteractionConnectionCallback callback,
                int flags,
                int interrogatingPid,
                long interrogatingTid,
                MagnificationSpec spec,
                float[] matrix) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl
                        .getAccessibilityInteractionController()
                        .findAccessibilityNodeInfosByTextClientThread(
                                accessibilityNodeId,
                                text,
                                interactiveRegion,
                                interactionId,
                                callback,
                                flags,
                                interrogatingPid,
                                interrogatingTid,
                                spec,
                                matrix);
            } else {
                try {
                    callback.setFindAccessibilityNodeInfosResult(null, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findFocus(
                long accessibilityNodeId,
                int focusType,
                Region interactiveRegion,
                int interactionId,
                IAccessibilityInteractionConnectionCallback callback,
                int flags,
                int interrogatingPid,
                long interrogatingTid,
                MagnificationSpec spec,
                float[] matrix) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl
                        .getAccessibilityInteractionController()
                        .findFocusClientThread(
                                accessibilityNodeId,
                                focusType,
                                interactiveRegion,
                                interactionId,
                                callback,
                                flags,
                                interrogatingPid,
                                interrogatingTid,
                                spec,
                                matrix);
            } else {
                try {
                    callback.setFindAccessibilityNodeInfoResult(null, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void focusSearch(
                long accessibilityNodeId,
                int direction,
                Region interactiveRegion,
                int interactionId,
                IAccessibilityInteractionConnectionCallback callback,
                int flags,
                int interrogatingPid,
                long interrogatingTid,
                MagnificationSpec spec,
                float[] matrix) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl
                        .getAccessibilityInteractionController()
                        .focusSearchClientThread(
                                accessibilityNodeId,
                                direction,
                                interactiveRegion,
                                interactionId,
                                callback,
                                flags,
                                interrogatingPid,
                                interrogatingTid,
                                spec,
                                matrix);
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
                viewRootImpl
                        .getAccessibilityInteractionController()
                        .clearAccessibilityFocusClientThread();
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void notifyOutsideTouch() {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl
                        .getAccessibilityInteractionController()
                        .notifyOutsideTouchClientThread();
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void takeScreenshotOfWindow(
                int interactionId,
                ScreenCapture.ScreenCaptureListener listener,
                IAccessibilityInteractionConnectionCallback callback) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl
                        .getAccessibilityInteractionController()
                        .takeScreenshotOfWindowClientThread(interactionId, listener, callback);
            } else {
                try {
                    callback.sendTakeScreenshotOfWindowError(1, interactionId);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void attachAccessibilityOverlayToWindow(
                SurfaceControl sc,
                int interactionId,
                IAccessibilityInteractionConnectionCallback callback) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null) {
                viewRootImpl
                        .getAccessibilityInteractionController()
                        .attachAccessibilityOverlayToWindowClientThread(
                                sc, interactionId, callback);
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
                Log.e(ViewRootImpl.TAG, "Accessibility content change has no source");
                return;
            }
            if (ViewRootImpl.this.mAccessibilityManager.isEnabled()) {
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
                CalledFromWrongThreadException e =
                        new CalledFromWrongThreadException(
                                "Only the original thread that created a view hierarchy can touch"
                                    + " its views.");
                Log.e(
                        ViewRootImpl.TAG,
                        "Accessibility content change on non-UI thread. Future Android versions"
                            + " will throw an exception.",
                        e);
                ViewRootImpl.this.mHandler.removeCallbacks(this);
                if (this.mSource != null) {
                    run();
                }
            }
            if (!canContinueThrottle(source, changeType)) {
                removeCallbacksAndRun();
            }
            if (this.mSource != null) {
                if (android.view.accessibility.Flags.fixMergedContentChangeEventV2()) {
                    View newSource = ViewRootImpl.this.getCommonPredecessor(this.mSource, source);
                    if (newSource != null) {
                        newSource = newSource.getSelfOrParentImportantForA11y();
                    }
                    if (newSource == null) {
                        newSource = source;
                    }
                    this.mChangeTypes |= changeType;
                    if (this.mSource != newSource) {
                        this.mChangeTypes |= 1;
                        this.mSource = newSource;
                    }
                } else {
                    View predecessor = ViewRootImpl.this.getCommonPredecessor(this.mSource, source);
                    if (predecessor != null) {
                        predecessor = predecessor.getSelfOrParentImportantForA11y();
                    }
                    this.mSource = predecessor != null ? predecessor : source;
                    this.mChangeTypes |= changeType;
                }
                int performingAction =
                        ViewRootImpl.this.mAccessibilityManager.getPerformingAction();
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
                this.mAction =
                        OptionalInt.of(
                                ViewRootImpl.this.mAccessibilityManager.getPerformingAction());
            }
            long timeSinceLastMillis = SystemClock.uptimeMillis() - this.mLastEventTimeMillis;
            long minEventIntervalMillis =
                    ViewConfiguration.getSendRecurringAccessibilityEventsInterval();
            if (timeSinceLastMillis >= minEventIntervalMillis) {
                removeCallbacksAndRun();
            } else {
                ViewRootImpl.this.mHandler.postDelayed(
                        this, minEventIntervalMillis - timeSinceLastMillis);
            }
        }

        public void removeCallbacksAndRun() {
            ViewRootImpl.this.mHandler.removeCallbacks(this);
            run();
        }

        private boolean canContinueThrottle(View source, int changeType) {
            if (!android.view.accessibility.Flags.reduceWindowContentChangedEventThrottle()
                    || this.mSource == null
                    || this.mSource == source) {
                return true;
            }
            return changeType == 1 && this.mChangeTypes == 1;
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
            if (event.getAction() == 1
                    && (idx = this.mCapturedKeys.indexOfKey(event.getKeyCode())) >= 0) {
                this.mCurrentReceiver = this.mCapturedKeys.valueAt(idx);
                this.mCapturedKeys.removeAt(idx);
            }
        }

        boolean preViewDispatch(KeyEvent event) {
            this.mDispatched = false;
            if (this.mCurrentReceiver == null) {
                this.mCurrentReceiver = this.mCapturedKeys.get(event.getKeyCode());
            }
            if (this.mCurrentReceiver == null) {
                return false;
            }
            View target = this.mCurrentReceiver.get();
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
        this.mTransaction
                .setDisplayDecoration(this.mSurfaceControl, this.mDisplayDecorationCached)
                .apply();
    }

    public void setSkipScreenshot(SurfaceControl.Transaction t, boolean skipScreenshot) {
        this.mViewRootSurfaceController.setSkipScreenshot(t, skipScreenshot);
    }

    public void setDisableSuperHdr(SurfaceControl.Transaction t, boolean disableSuperHdr) {
        this.mViewRootSurfaceController.setDisableSuperHdr(t, disableSuperHdr);
    }

    public void setMetaData(int metaData, boolean isEnabled) {
        this.mViewRootSurfaceController.setMetaData(this.mTransaction, metaData, isEnabled);
    }

    public void dispatchBlurRegions(float[][] regionCopy, long frameNumber) {
        if (DEBUG_BLUR) {
            Log.i(this.mTag, "dispatchBlurRegions " + frameNumber);
        }
        SurfaceControl surfaceControl = getSurfaceControl();
        if (!surfaceControl.isValid()) {
            return;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.setBlurRegions(surfaceControl, regionCopy);
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.mergeWithNextTransaction(transaction, frameNumber);
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

    int getSurfaceSequenceId() {
        return this.mSurfaceSequenceId;
    }

    public void mergeWithNextTransaction(SurfaceControl.Transaction t, long frameNumber) {
        Log.i(
                this.mTag,
                "mWNT: t=0x"
                        + (t != null ? Long.toHexString(t.mNativeObject) : "null")
                        + " mBlastBufferQueue=0x"
                        + (this.mBlastBufferQueue != null
                                ? Long.toHexString(this.mBlastBufferQueue.mNativeObject)
                                : "null")
                        + " fn= "
                        + frameNumber
                        + " HdrRenderState mRenderHdrSdrRatio="
                        + this.mHdrRenderState.getRenderHdrSdrRatio()
                        + " caller= "
                        + Debug.getCallers(3));
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.mergeWithNextTransaction(t, frameNumber);
        } else {
            t.apply();
        }
    }

    @Override // android.view.AttachedSurfaceControl
    public SurfaceControl.Transaction buildReparentTransaction(SurfaceControl child) {
        if (this.mSurfaceControl.isValid()) {
            SurfaceControl.Transaction t = new SurfaceControl.Transaction();
            return t.reparent(child, updateAndGetBoundsLayer(t));
        }
        return null;
    }

    @Override // android.view.AttachedSurfaceControl
    public boolean applyTransactionOnDraw(SurfaceControl.Transaction t) {
        if (this.mRemoved || !isHardwareEnabled()) {
            logAndTrace("applyTransactionOnDraw applyImmediately");
            t.apply();
        } else {
            Trace.instant(8L, "applyTransactionOnDraw-" + this.mTag);
            this.mPendingTransaction.merge(t);
            this.mHasPendingTransactions = true;
        }
        return true;
    }

    @Override // android.view.AttachedSurfaceControl
    public int getBufferTransformHint() {
        if (com.android.window.flags.Flags.enableBufferTransformHintFromDisplay()) {
            return this.mPreviousTransformHint;
        }
        if (this.mSurfaceControl.isValid()) {
            return this.mSurfaceControl.getTransformHint();
        }
        return 0;
    }

    @Override // android.view.AttachedSurfaceControl
    public void addOnBufferTransformHintChangedListener(
            AttachedSurfaceControl.OnBufferTransformHintChangedListener listener) {
        Objects.requireNonNull(listener);
        if (this.mTransformHintListeners.contains(listener)) {
            throw new IllegalArgumentException(
                    "attempt to call addOnBufferTransformHintChangedListener() with a previously"
                        + " registered listener");
        }
        this.mTransformHintListeners.add(listener);
    }

    @Override // android.view.AttachedSurfaceControl
    public void removeOnBufferTransformHintChangedListener(
            AttachedSurfaceControl.OnBufferTransformHintChangedListener listener) {
        Objects.requireNonNull(listener);
        this.mTransformHintListeners.remove(listener);
    }

    private void dispatchTransformHintChanged(int hint) {
        if (this.mTransformHintListeners.isEmpty()) {
            return;
        }
        ArrayList<AttachedSurfaceControl.OnBufferTransformHintChangedListener> listeners =
                (ArrayList) this.mTransformHintListeners.clone();
        for (int i = 0; i < listeners.size(); i++) {
            AttachedSurfaceControl.OnBufferTransformHintChangedListener listener = listeners.get(i);
            listener.onBufferTransformHintChanged(hint);
        }
    }

    boolean wasRelayoutRequested() {
        return this.mRelayoutRequested;
    }

    void forceWmRelayout() {
        this.mForceNextWindowRelayout = true;
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [17] mView=" + this.mView);
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
        this.mOnBackInvokedDispatcher.attachToWindow(
                this.mWindowSession, this.mWindow, this.mImeBackAnimationController);
    }

    private void sendBackKeyEvent(int action) {
        long when = SystemClock.uptimeMillis();
        KeyEvent ev = new KeyEvent(when, when, action, 4, 0, 0, -1, 0, 72, 257);
        enqueueInputEvent(ev, null, 0, true);
    }

    private void registerCompatOnBackInvokedCallback() {
        this.mCompatOnBackInvokedCallback =
                new CompatOnBackInvokedCallback() { // from class:
                                                    // android.view.ViewRootImpl$$ExternalSyntheticLambda15
                    @Override // android.window.CompatOnBackInvokedCallback,
                              // android.window.OnBackInvokedCallback
                    public final void onBackInvoked() {
                        ViewRootImpl.this.lambda$registerCompatOnBackInvokedCallback$12();
                    }
                };
        if (this.mOnBackInvokedDispatcher.hasImeOnBackInvokedDispatcher()) {
            Log.d(TAG, "Skip registering CompatOnBackInvokedCallback on IME dispatcher");
        } else {
            this.mOnBackInvokedDispatcher.registerOnBackInvokedCallback(
                    0, this.mCompatOnBackInvokedCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerCompatOnBackInvokedCallback$12() {
        try {
            processingBackKey(true);
            sendBackKeyEvent(0);
            sendBackKeyEvent(1);
        } finally {
            processingBackKey(false);
        }
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
        SurfaceControl.Transaction t;
        if (!isHardwareEnabled()) {
            return;
        }
        if (DEBUG_BLAST) {
            Log.d(this.mTag, "registerCallbacksForSync syncBuffer=" + syncBuffer);
        }
        if (this.mHasPendingTransactions) {
            t = new SurfaceControl.Transaction();
            t.merge(this.mPendingTransaction);
            this.mHasPendingTransactions = false;
        } else {
            t = null;
        }
        this.mAttachInfo.mThreadedRenderer.registerRtFrameCallback(
                new AnonymousClass11(t, surfaceSyncGroup, syncBuffer));
    }

    /* renamed from: android.view.ViewRootImpl$11, reason: invalid class name */
    class AnonymousClass11 implements HardwareRenderer.FrameDrawingCallback {
        final /* synthetic */ SurfaceSyncGroup val$surfaceSyncGroup;
        final /* synthetic */ boolean val$syncBuffer;
        final /* synthetic */ SurfaceControl.Transaction val$t;

        AnonymousClass11(
                SurfaceControl.Transaction transaction,
                SurfaceSyncGroup surfaceSyncGroup,
                boolean z) {
            this.val$t = transaction;
            this.val$surfaceSyncGroup = surfaceSyncGroup;
            this.val$syncBuffer = z;
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public void onFrameDraw(long frame) {}

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public HardwareRenderer.FrameCommitCallback onFrameDraw(int syncResult, final long frame) {
            ViewRootImpl.this.mFrameNumber = frame;
            if (ViewRootImpl.DEBUG_BLAST) {
                Log.d(
                        ViewRootImpl.this.mTag,
                        "Received frameDrawingCallback syncResult="
                                + syncResult
                                + " frameNum="
                                + frame
                                + MediaMetrics.SEPARATOR);
            }
            if (this.val$t != null) {
                ViewRootImpl.this.mergeWithNextTransaction(this.val$t, frame);
            }
            if ((syncResult & 6) != 0) {
                this.val$surfaceSyncGroup.addTransaction(
                        ViewRootImpl.this.mBlastBufferQueue.gatherPendingTransactions(frame));
                this.val$surfaceSyncGroup.markSyncReady();
                return null;
            }
            if (ViewRootImpl.DEBUG_BLAST) {
                Log.i(ViewRootImpl.this.mTag, "Setting up sync and frameCommitCallback");
            }
            if (this.val$syncBuffer) {
                BLASTBufferQueue bLASTBufferQueue = ViewRootImpl.this.mBlastBufferQueue;
                final SurfaceSyncGroup surfaceSyncGroup = this.val$surfaceSyncGroup;
                boolean result =
                        bLASTBufferQueue.syncNextTransaction(
                                new Consumer() { // from class:
                                                 // android.view.ViewRootImpl$11$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ViewRootImpl.AnonymousClass11.this.lambda$onFrameDraw$2(
                                                surfaceSyncGroup, (SurfaceControl.Transaction) obj);
                                    }
                                });
                if (!result) {
                    Log.w(
                            ViewRootImpl.this.mTag,
                            "Unable to syncNextTransaction. Possibly something else is trying to"
                                + " sync?");
                    this.val$surfaceSyncGroup.markSyncReady();
                }
            }
            final SurfaceSyncGroup surfaceSyncGroup2 = this.val$surfaceSyncGroup;
            final boolean z = this.val$syncBuffer;
            return new HardwareRenderer
                    .FrameCommitCallback() { // from class:
                                             // android.view.ViewRootImpl$11$$ExternalSyntheticLambda3
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z2) {
                    ViewRootImpl.AnonymousClass11.this.lambda$onFrameDraw$3(
                            frame, surfaceSyncGroup2, z, z2);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$2(
                SurfaceSyncGroup surfaceSyncGroup, SurfaceControl.Transaction transaction) {
            if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                transaction.addDebugName("syncBuffer_" + surfaceSyncGroup.getName());
                Log.i(
                        ViewRootImpl.this.mTag,
                        "Received ready transaction from native, debugName="
                                + transaction.mDebugName);
            }
            final Runnable timeoutRunnable =
                    new Runnable() { // from class:
                                     // android.view.ViewRootImpl$11$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewRootImpl.AnonymousClass11.this.lambda$onFrameDraw$0();
                        }
                    };
            ViewRootImpl.this.mHandler.postDelayed(
                    timeoutRunnable, Build.HW_TIMEOUT_MULTIPLIER * 4000);
            transaction.addTransactionCommittedListener(
                    ViewRootImpl.this.mSimpleExecutor,
                    new SurfaceControl
                            .TransactionCommittedListener() { // from class:
                                                              // android.view.ViewRootImpl$11$$ExternalSyntheticLambda1
                        @Override // android.view.SurfaceControl.TransactionCommittedListener
                        public final void onTransactionCommitted() {
                            ViewRootImpl.AnonymousClass11.this.lambda$onFrameDraw$1(
                                    timeoutRunnable);
                        }
                    });
            surfaceSyncGroup.addTransaction(transaction);
            surfaceSyncGroup.markSyncReady();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$0() {
            Log.e(
                    ViewRootImpl.this.mTag,
                    "Failed to submit the sync transaction after 4s. Likely to ANR soon");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$1(Runnable timeoutRunnable) {
            ViewRootImpl.this.mHandler.removeCallbacks(timeoutRunnable);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$3(
                long frame,
                SurfaceSyncGroup surfaceSyncGroup,
                boolean syncBuffer,
                boolean didProduceBuffer) {
            if (ViewRootImpl.DEBUG_BLAST) {
                Log.i(
                        ViewRootImpl.this.mTag,
                        "Received frameCommittedCallback lastAttemptedDrawFrameNum="
                                + frame
                                + " didProduceBuffer="
                                + didProduceBuffer);
            }
            if (!didProduceBuffer) {
                ViewRootImpl.this.mBlastBufferQueue.clearSyncTransaction();
                surfaceSyncGroup.addTransaction(
                        ViewRootImpl.this.mBlastBufferQueue.gatherPendingTransactions(frame));
                surfaceSyncGroup.markSyncReady();
            } else if (!syncBuffer) {
                surfaceSyncGroup.markSyncReady();
            }
        }
    }

    private void safeguardOverlappingSyncs(final SurfaceSyncGroup activeSurfaceSyncGroup) {
        final SurfaceSyncGroup safeguardSsg = new SurfaceSyncGroup("Safeguard-" + this.mTag);
        safeguardSsg.toggleTimeout(false);
        synchronized (this.mPreviousSyncSafeguardLock) {
            if (this.mPreviousSyncSafeguard != null) {
                activeSurfaceSyncGroup.add(this.mPreviousSyncSafeguard, (Runnable) null);
                activeSurfaceSyncGroup.toggleTimeout(false);
                this.mPreviousSyncSafeguard.addSyncCompleteCallback(
                        this.mSimpleExecutor,
                        new Runnable() { // from class:
                                         // android.view.ViewRootImpl$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                SurfaceSyncGroup.this.toggleTimeout(true);
                            }
                        });
            }
            this.mPreviousSyncSafeguard = safeguardSsg;
        }
        SurfaceControl.Transaction t = new SurfaceControl.Transaction();
        t.addTransactionCommittedListener(
                this.mSimpleExecutor,
                new SurfaceControl
                        .TransactionCommittedListener() { // from class:
                                                          // android.view.ViewRootImpl$$ExternalSyntheticLambda10
                    @Override // android.view.SurfaceControl.TransactionCommittedListener
                    public final void onTransactionCommitted() {
                        ViewRootImpl.this.lambda$safeguardOverlappingSyncs$14(safeguardSsg);
                    }
                });
        activeSurfaceSyncGroup.addTransaction(t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$safeguardOverlappingSyncs$14(SurfaceSyncGroup safeguardSsg) {
        safeguardSsg.markSyncReady();
        synchronized (this.mPreviousSyncSafeguardLock) {
            if (this.mPreviousSyncSafeguard == safeguardSsg) {
                this.mPreviousSyncSafeguard = null;
            }
        }
    }

    @Override // android.view.AttachedSurfaceControl
    public SurfaceSyncGroup getOrCreateSurfaceSyncGroup() {
        boolean newSyncGroup = false;
        if (this.mActiveSurfaceSyncGroup == null) {
            this.mActiveSurfaceSyncGroup = new SurfaceSyncGroup(this.mTag);
            this.mActiveSurfaceSyncGroup.setAddedToSyncListener(
                    new Runnable() { // from class:
                                     // android.view.ViewRootImpl$$ExternalSyntheticLambda22
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewRootImpl.this.lambda$getOrCreateSurfaceSyncGroup$16();
                        }
                    });
            newSyncGroup = true;
        }
        Trace.instant(8L, "getOrCreateSurfaceSyncGroup isNew=" + newSyncGroup + " " + this.mTag);
        if (DEBUG_BLAST) {
            if (newSyncGroup) {
                Log.i(
                        this.mTag,
                        "Creating new active sync group " + this.mActiveSurfaceSyncGroup.getName());
            } else {
                Log.d(
                        this.mTag,
                        "Return already created active sync group "
                                + this.mActiveSurfaceSyncGroup.getName());
            }
        }
        this.mNumPausedForSync++;
        this.mHandler.removeMessages(37);
        this.mHandler.sendEmptyMessageDelayed(37, Build.HW_TIMEOUT_MULTIPLIER * 1000);
        return this.mActiveSurfaceSyncGroup;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOrCreateSurfaceSyncGroup$16() {
        Runnable runnable =
                new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewRootImpl.this.lambda$getOrCreateSurfaceSyncGroup$15();
                    }
                };
        if (Thread.currentThread() == this.mThread) {
            runnable.run();
        } else {
            this.mHandler.post(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOrCreateSurfaceSyncGroup$15() {
        if (this.mNumPausedForSync > 0) {
            this.mNumPausedForSync--;
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
        syncGroup.addSyncCompleteCallback(
                this.mSimpleExecutor,
                new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewRootImpl.lambda$updateSyncInProgressCount$17();
                    }
                });
    }

    static /* synthetic */ void lambda$updateSyncInProgressCount$17() {
        synchronized (sSyncProgressLock) {
            int i = sNumSyncsInProgress - 1;
            sNumSyncsInProgress = i;
            if (i == 0) {
                HardwareRenderer.setRtAnimationsEnabled(true);
            }
        }
    }

    void addToSync(SurfaceSyncGroup syncable) {
        if (this.mActiveSurfaceSyncGroup == null) {
            return;
        }
        this.mActiveSurfaceSyncGroup.add(syncable, (Runnable) null);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchLetterboxDirectionChanged(int direction) {
        this.mHandler.removeMessages(104);
        Message msg = this.mHandler.obtainMessage(104, direction, 0);
        this.mHandler.sendMessage(msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDispatchLetterboxDirectionChanged(int direction) {
        this.mRequestedLetterboxDirection = direction;
        if (updateAppliedLetterboxDirection(this.mRequestedLetterboxDirection)
                && (this.mView instanceof DecorView)) {
            requestInvalidateRootRenderNode();
            this.mView.invalidate();
        }
    }

    public boolean updateAppliedLetterboxDirection(int direction) {
        boolean updated = this.mAppliedLetterboxDirection != direction;
        if (updated) {
            this.mAppliedLetterboxDirection = direction;
            Log.v(
                    this.mTag,
                    "updateAppliedLetterboxDirection, direction="
                            + this.mAppliedLetterboxDirection
                            + ", Caller="
                            + Debug.getCaller());
        }
        return updated;
    }

    private void logAndTrace(String msg) {
        if (Trace.isTagEnabled(8L)) {
            Trace.instant(8L, this.mTag + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + msg);
        }
        if (DEBUG_BLAST) {
            Log.d(this.mTag, msg);
        }
        EventLog.writeEvent(60004, this.mTag, msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFrameRateFromThreadedRendererViews() {
        ArrayList<View> views = this.mThreadedRendererViews;
        for (int i = views.size() - 1; i >= 0; i--) {
            View view = views.get(i);
            View.AttachInfo attachInfo = view.mAttachInfo;
            if (attachInfo == null || attachInfo.mViewRootImpl != this) {
                views.remove(i);
            } else {
                view.votePreferredFrameRate();
            }
        }
    }

    private void setCategoryFromCategoryCounts() {
        switch (this.mPreferredFrameRateCategory) {
            case 2:
                this.mFrameRateCategoryLowCount = 5;
                break;
            case 3:
                this.mFrameRateCategoryNormalCount = 5;
                break;
            case 4:
                this.mFrameRateCategoryHighHintCount = 5;
                break;
            case 5:
                this.mFrameRateCategoryHighCount = 5;
                break;
        }
        if (this.mFrameRateCategoryHighCount > 0) {
            this.mPreferredFrameRateCategory = 5;
            return;
        }
        if (this.mFrameRateCategoryHighHintCount > 0) {
            this.mPreferredFrameRateCategory = 4;
        } else if (this.mFrameRateCategoryNormalCount > 0) {
            this.mPreferredFrameRateCategory = 3;
        } else if (this.mFrameRateCategoryLowCount > 0) {
            this.mPreferredFrameRateCategory = 2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0054, code lost:

       if (r11.mSurfaceReplaced != false) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0056, code lost:

       r3 = android.os.Trace.isTagEnabled(8);
    */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x005b, code lost:

       if (r3 == false) goto L42;
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x005d, code lost:

       r6 = reasonToString(r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0061, code lost:

       if (r2 != null) goto L40;
    */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0063, code lost:

       r7 = com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0067, code lost:

       r8 = categoryToString(r0);
       android.os.Trace.traceBegin(8, "ViewRootImpl#setFrameRateCategory " + r8 + ", reason " + r6 + ", " + r7);
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0066, code lost:

       r7 = r2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0097, code lost:

       if (android.view.ViewRootImpl.sToolkitFrameRateFunctionEnablingReadOnlyFlagValue == false) goto L45;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0099, code lost:

       android.util.Log.i(r11.mTag, "call setFrameRateCategory category=" + categoryToString(r0) + ", reason=" + reasonToString(r1) + ", vri=" + r11.mTag);
       r11.mFrameRateTransaction.setFrameRateCategory(r11.mSurfaceControl, r0, false).applyAsyncUnsafe();
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00d7, code lost:

       r11.mLastPreferredFrameRateCategory = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00d9, code lost:

       if (r3 == false) goto L54;
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ed, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0050, code lost:

       if (r11.mLastPreferredFrameRateCategory == r0) goto L33;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setPreferredFrameRateCategory(int r12) {
        /*
            Method dump skipped, instructions count: 244
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.ViewRootImpl.setPreferredFrameRateCategory(int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFrameRateCategoryForTouchHint(int preferredFrameRateCategory) {
        int frameRateCategory;
        int frameRateReason;
        if (this.mIsTouchHint && preferredFrameRateCategory <= 4) {
            frameRateCategory = 4;
            frameRateReason = 150994944;
        } else {
            frameRateCategory = preferredFrameRateCategory;
            frameRateReason = this.mFrameRateCategoryChangeReason;
        }
        if (frameRateCategory != 0) {
            try {
                if (this.mLastPreferredFrameRateCategory != frameRateCategory) {
                    Log.i(
                            this.mTag,
                            "call setFrameRateCategory for touch hint category="
                                    + categoryToString(frameRateCategory)
                                    + ", reason="
                                    + reasonToString(frameRateReason)
                                    + ", vri="
                                    + this.mTag);
                    this.mFrameRateTransaction
                            .setFrameRateCategory(this.mSurfaceControl, frameRateCategory, false)
                            .applyAsyncUnsafe();
                    this.mLastPreferredFrameRateCategory = frameRateCategory;
                }
            } catch (Exception e) {
                Log.e(this.mTag, "Unable to set frame rate category", e);
            }
        }
    }

    private static String categoryToString(int frameRateCategory) {
        switch (frameRateCategory) {
            case 1:
                return "no preference";
            case 2:
                return "low";
            case 3:
                return "normal";
            case 4:
                return "high hint";
            case 5:
                return "high";
            default:
                return "default";
        }
    }

    private static String reasonToString(int reason) {
        switch (reason) {
            case 0:
                return "unknown";
            case 16777216:
                return "small";
            case 33554432:
                return "intermittent";
            case 50331648:
                return Slice.HINT_LARGE;
            case 67108864:
                return "requested";
            case 83886080:
                return "invalid frame rate";
            case 100663296:
                return "velocity";
            case 134217728:
                return "boost";
            case 150994944:
                return "touch";
            case 167772160:
                return "conflicted";
            case 184549376:
                return "boost timeout";
            case 201326592:
                return "idle timeout";
            case 218103808:
                return "large hint";
            default:
                String str = String.valueOf(reason);
                return str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPreferredFrameRate(float preferredFrameRate) {
        if (!shouldSetFrameRate() || preferredFrameRate < 0.0f) {
            return;
        }
        boolean traceFrameRate = false;
        try {
            try {
                if (this.mLastPreferredFrameRate != preferredFrameRate || this.mSurfaceReplaced) {
                    traceFrameRate = Trace.isTagEnabled(8L);
                    if (traceFrameRate) {
                        Trace.traceBegin(
                                8L,
                                "ViewRootImpl#setFrameRate "
                                        + preferredFrameRate
                                        + " compatibility "
                                        + this.mFrameRateCompatibility);
                    }
                    if (sToolkitFrameRateFunctionEnablingReadOnlyFlagValue) {
                        if (preferredFrameRate > 0.0f) {
                            Log.i(
                                    this.mTag,
                                    "call setFrameRate frameRate="
                                            + preferredFrameRate
                                            + ", compatibility="
                                            + this.mFrameRateCompatibility
                                            + ", vri="
                                            + this.mTag);
                            this.mFrameRateTransaction.setFrameRate(
                                    this.mSurfaceControl,
                                    preferredFrameRate,
                                    this.mFrameRateCompatibility);
                        } else {
                            Log.d(this.mTag, "call clearFrameRate");
                            this.mFrameRateTransaction.clearFrameRate(this.mSurfaceControl);
                        }
                        this.mFrameRateTransaction.applyAsyncUnsafe();
                    }
                    this.mLastPreferredFrameRate = preferredFrameRate;
                }
                if (!traceFrameRate) {
                    return;
                }
            } catch (Exception e) {
                Log.e(this.mTag, "Unable to set frame rate", e);
                if (!traceFrameRate) {
                    return;
                }
            }
            Trace.traceEnd(8L);
        } catch (Throwable th) {
            if (traceFrameRate) {
                Trace.traceEnd(8L);
            }
            throw th;
        }
    }

    private boolean shouldSetFrameRateCategory() {
        return shouldEnableDvrr() && this.mSurface.isValid();
    }

    private boolean shouldSetFrameRate() {
        return shouldEnableDvrr()
                && this.mSurface.isValid()
                && this.mPreferredFrameRate >= 0.0f
                && !this.mIsFrameRateConflicted;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldTouchBoost(int motionEventAction, int windowType) {
        boolean desiredAction = motionEventAction != 4;
        boolean undesiredType = windowType == 2011 && sToolkitFrameRateTypingReadOnlyFlagValue;
        return desiredAction
                && !undesiredType
                && shouldEnableDvrr()
                && getFrameRateBoostOnTouchEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldTouchHint(int motionEventAction, int windowType) {
        boolean desiredAction = motionEventAction != 4;
        boolean undesiredType = windowType == 2011;
        return desiredAction && !undesiredType;
    }

    public void votePreferredFrameRateCategory(int frameRateCategory, int reason, View view) {
        if (CoreRune.FW_DVRR_TOOLKIT_REQUESTED_REFRESH_RATE
                && this.mFrameRateCategoryChangeReason == 67108864) {
            return;
        }
        if (reason == 67108864) {
            Log.i(this.mTag, "Requested frameRateCategory " + frameRateCategory + " by " + view);
        }
        if ((CoreRune.FW_DVRR_TOOLKIT_REQUESTED_REFRESH_RATE && reason == 67108864)
                || frameRateCategory > this.mPreferredFrameRateCategory) {
            this.mPreferredFrameRateCategory = frameRateCategory;
            this.mFrameRateCategoryChangeReason = reason;
        }
        if (((CoreRune.FW_DVRR_TOOLKIT_POLICY_FOR_SCROLL && this.mIsFlingState)
                        || (CoreRune.FW_DVRR_TOOLKIT_SUPPORT_HRR && this.mIsHRR))
                && CoreRune.FW_DVRR_TOOLKIT_SUPPORT_HIGH_FRAME_RATE
                && this.mPreferredFrameRateCategory == 5
                && this.mFrameRateCategoryChangeReason == 218103808) {
            this.mPreferredFrameRateCategory = 3;
            this.mFrameRateCategoryChangeReason = reason;
        }
        this.mDrawnThisFrame = true;
    }

    public void addThreadedRendererView(View view) {
        if (shouldEnableDvrr() && !this.mThreadedRendererViews.contains(view)) {
            this.mThreadedRendererViews.add(view);
        }
    }

    public void removeThreadedRendererView(View view) {
        this.mThreadedRendererViews.remove(view);
        if (shouldEnableDvrr()
                && !this.mInvalidationIdleMessagePosted
                && sSurfaceFlingerBugfixFlagValue) {
            this.mInvalidationIdleMessagePosted = true;
            this.mHandler.sendEmptyMessageDelayed(40, 750L);
        }
    }

    int intermittentUpdateState() {
        if (this.mMinusOneFrameIntervalMillis + this.mMinusTwoFrameIntervalMillis < 100) {
            return 1;
        }
        if (this.mInfrequentUpdateCount == 2) {
            return 0;
        }
        return -1;
    }

    public boolean shouldCheckFrameRateCategory() {
        return this.mPreferredFrameRateCategory < 5;
    }

    public boolean shouldCheckFrameRate(boolean isDirect) {
        return this.mPreferredFrameRate < 120.0f
                || !(isDirect
                        || sToolkitFrameRateVelocityMappingReadOnlyFlagValue
                        || this.mPreferredFrameRateCategory >= 5);
    }

    public void votePreferredFrameRate(float frameRate, int frameRateCompatibility) {
        float nextFrameRate;
        int nextFrameRateCompatibility;
        if (frameRate <= 0.0f) {
            return;
        }
        if (frameRateCompatibility == 103 && !this.mIsPressedGesture) {
            this.mIsTouchBoosting = false;
            if (CoreRune.FW_DVRR_TOOLKIT_POLICY_FOR_SCROLL) {
                if (this.mIsFrameRateBoosting && this.mIsFlingState) {
                    this.mIsFrameRateBoosting = false;
                }
                this.mRequestedFrameRateByFling = this.mIsFlingState ? frameRate : 0.0f;
            } else {
                this.mIsFrameRateBoosting = false;
            }
            if (!sToolkitFrameRateVelocityMappingReadOnlyFlagValue) {
                this.mPreferredFrameRateCategory = 5;
                this.mFrameRateCategoryHighCount = 5;
                this.mFrameRateCategoryChangeReason = 100663296;
                this.mFrameRateCategoryView = null;
                this.mDrawnThisFrame = true;
                return;
            }
        }
        if (frameRate > this.mPreferredFrameRate) {
            nextFrameRate = frameRate;
            nextFrameRateCompatibility = frameRateCompatibility;
        } else {
            nextFrameRate = this.mPreferredFrameRate;
            nextFrameRateCompatibility = this.mFrameRateCompatibility;
        }
        if (this.mPreferredFrameRate > 0.0f
                && this.mPreferredFrameRate % frameRate != 0.0f
                && frameRate % this.mPreferredFrameRate != 0.0f) {
            this.mIsFrameRateConflicted = true;
            if (nextFrameRate > 60.0f && this.mFrameRateCategoryHighCount != 5) {
                this.mFrameRateCategoryHighCount = 5;
                this.mFrameRateCategoryChangeReason = 167772160;
                this.mFrameRateCategoryView = null;
            } else if (this.mFrameRateCategoryHighCount == 0
                    && this.mFrameRateCategoryHighHintCount == 0
                    && this.mFrameRateCategoryNormalCount < 5) {
                this.mFrameRateCategoryNormalCount = 5;
                this.mFrameRateCategoryChangeReason = 167772160;
                this.mFrameRateCategoryView = null;
            }
        }
        this.mPreferredFrameRate = nextFrameRate;
        this.mFrameRateCompatibility = nextFrameRateCompatibility;
        this.mDrawnThisFrame = true;
    }

    public int getPreferredFrameRateCategory() {
        return this.mPreferredFrameRateCategory;
    }

    public int getLastPreferredFrameRateCategory() {
        return this.mLastPreferredFrameRateCategory;
    }

    public float getPreferredFrameRate() {
        return this.mPreferredFrameRate >= 0.0f
                ? this.mPreferredFrameRate
                : this.mLastPreferredFrameRate;
    }

    public float getLastPreferredFrameRate() {
        return this.mLastPreferredFrameRate;
    }

    public boolean getIsTouchBoosting() {
        return this.mIsTouchBoosting;
    }

    public int getFrameRateCompatibility() {
        return this.mFrameRateCompatibility;
    }

    public boolean getIsFrameRateBoosting() {
        return this.mIsFrameRateBoosting;
    }

    public boolean getFrameRateBoostOnTouchEnabled() {
        return this.mWindowAttributes.getFrameRateBoostOnTouchEnabled();
    }

    public void setScrollFlingState(boolean isFling) {
        this.mIsFlingState = isFling;
    }

    public boolean getScrollFlingState() {
        return this.mIsFlingState;
    }

    public float getRequestedFrameRateByFling() {
        return this.mRequestedFrameRateByFling;
    }

    private void boostFrameRate(int boostTimeOut) {
        this.mIsFrameRateBoosting = true;
        this.mHandler.removeMessages(39);
        this.mHandler.sendEmptyMessageDelayed(39, boostTimeOut);
    }

    void setBackKeyCallbackForWindowlessWindow(Predicate<KeyEvent> callback) {
        this.mWindowlessBackKeyCallback = callback;
    }

    void recordViewPercentage(float percentage) {
        if (Trace.isEnabled()) {
            this.mLargestChildPercentage = Math.max(percentage, this.mLargestChildPercentage);
        }
    }

    public boolean isFrameRatePowerSavingsBalanced() {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            return this.mWindowAttributes.isFrameRatePowerSavingsBalanced();
        }
        return true;
    }

    public boolean isFrameRateConflicted() {
        return this.mIsFrameRateConflicted;
    }

    private boolean shouldEnableDvrr() {
        return sEnableVrr
                && sToolkitFrameRateViewEnablingReadOnlyFlagValue
                && sToolkitSetFrameRateReadOnlyFlagValue
                && isFrameRatePowerSavingsBalanced();
    }

    private void removeVrrMessages() {
        this.mHandler.removeMessages(39);
        this.mHandler.removeMessages(42);
        this.mHandler.removeMessages(43);
        if (this.mInvalidationIdleMessagePosted && sSurfaceFlingerBugfixFlagValue) {
            this.mInvalidationIdleMessagePosted = false;
            this.mHandler.removeMessages(40);
        }
    }

    private void updateInfrequentCount() {
        long currentTimeMillis = this.mAttachInfo.mDrawingTime;
        int timeIntervalMillis =
                (int) Math.min(2147483647L, currentTimeMillis - this.mLastUpdateTimeMillis);
        this.mMinusTwoFrameIntervalMillis = this.mMinusOneFrameIntervalMillis;
        this.mMinusOneFrameIntervalMillis = timeIntervalMillis;
        this.mLastUpdateTimeMillis = currentTimeMillis;
        if (this.mThreadedRendererViews.isEmpty()
                && this.mMinusTwoFrameIntervalMillis + timeIntervalMillis >= 100) {
            int infrequentUpdateCount = this.mInfrequentUpdateCount;
            this.mInfrequentUpdateCount =
                    infrequentUpdateCount == 2 ? infrequentUpdateCount : infrequentUpdateCount + 1;
        } else {
            this.mInfrequentUpdateCount = 0;
        }
    }

    final class SmartClipRemoteRequestDispatcherProxy {
        private boolean DEBUG;
        private Context mContext;
        private SmartClipRemoteRequestDispatcher mDispatcher;
        private final String TAG = "SmartClipRemoteRequestDispatcher_ViewRootImpl";
        private SmartClipRemoteRequestDispatcher.ViewRootImplGateway mGateway =
                new SmartClipRemoteRequestDispatcher
                        .ViewRootImplGateway() { // from class:
                                                 // android.view.ViewRootImpl.SmartClipRemoteRequestDispatcherProxy.1
                    @Override // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.ViewRootImplGateway
                    public void enqueueInputEvent(
                            InputEvent event,
                            InputEventReceiver receiver,
                            int flags,
                            boolean processImmediately) {
                        ViewRootImpl.this.enqueueInputEvent(
                                event, receiver, flags, processImmediately);
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
                        ViewRootImpl.this.applyViewBoundsSandboxingIfNeeded(outRect, true);
                        CompatTranslator translator = ViewRootImpl.this.getCompatTranslator();
                        if (translator != null) {
                            translator.translateToScreen(outRect);
                        }
                    }
                };

        public SmartClipRemoteRequestDispatcherProxy(Context context) {
            this.DEBUG = false;
            this.mContext = context;
            this.mDispatcher = new SmartClipRemoteRequestDispatcher(context, this.mGateway);
            this.DEBUG = this.mDispatcher.isDebugMode();
        }

        public void dispatchSmartClipRemoteRequest(final SmartClipRemoteRequestInfo request) {
            if (this.DEBUG) {
                Log.i(
                        "SmartClipRemoteRequestDispatcher_ViewRootImpl",
                        "dispatchSmartClipRemoteRequest : req id="
                                + request.mRequestId
                                + " type="
                                + request.mRequestType
                                + " pid="
                                + request.mCallerPid
                                + " uid="
                                + request.mCallerUid);
            }
            switch (request.mRequestType) {
                case 1:
                    this.mDispatcher.checkPermission(
                            "com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA",
                            request.mCallerPid,
                            request.mCallerUid);
                    ViewRootImpl.this.mHandler.post(
                            new Runnable() { // from class:
                                             // android.view.ViewRootImpl.SmartClipRemoteRequestDispatcherProxy.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    SmartClipRemoteRequestDispatcherProxy.this
                                            .dispatchSmartClipMetaDataExtraction(request);
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
            SmartClipDataExtractionEvent requestInfo =
                    (SmartClipDataExtractionEvent) request.mRequestData;
            requestInfo.mRequestId = request.mRequestId;
            requestInfo.mTargetWindowLayer = request.mTargetWindowLayer;
            if (ViewRootImpl.this.mView != null) {
                SmartClipDataCropperImpl cropper =
                        new SmartClipDataCropperImpl(
                                ViewRootImpl.this.mView.getContext(), requestInfo);
                cropper.doExtractSmartClipData(ViewRootImpl.this.mView);
            } else {
                SmartClipDataCropperImpl cropper2 =
                        new SmartClipDataCropperImpl(this.mContext, requestInfo);
                cropper2.sendExtractionResultToSmartClipService(null);
            }
        }
    }

    public void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo request) {
        if (this.mSmartClipDispatcherProxy != null) {
            this.mSmartClipDispatcherProxy.dispatchSmartClipRemoteRequest(request);
        } else {
            Log.e(
                    TAG,
                    "dispatchSmartClipRemoteRequest : SmartClip dispatcher is null! req id="
                            + request.mRequestId
                            + " type="
                            + request.mRequestType);
        }
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
            this.mWindowSession.setTspNoteMode(this.mWindow, isTspNoteMode);
        } catch (RemoteException e) {
            e.printStackTrace();
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
                Log.e(TAG, "registerMotionEventMonitor : Just one event listener is allowed");
                return;
            }
            this.mListeners.add(listener);
            if (DEBUG) {
                Log.i(TAG, "registerMotionEventMonitor : Listener count=" + this.mListeners.size());
            }
        }

        public void unregisterMotionEventMonitor(OnTouchListener listener) {
            this.mListeners.remove(listener);
            if (DEBUG) {
                Log.i(
                        TAG,
                        "unregisterMotionEventMonitor : Listener count=" + this.mListeners.size());
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
                    Log.i(TAG, "dispatchInputEvent : action=" + action);
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
                Log.i(TAG, "dispatchInputEvent : The event is not instance of MotionEvent");
            }
        }

        private void notifyTouchEvent(MotionEvent event) {
            int cnt = this.mListeners.size();
            Log.i(TAG, "notifyTouchEvent : Listener cnt=" + cnt);
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

    public void updateLightCenter(Rect rect) {
        if (this.mAttachInfo != null && this.mAttachInfo.mThreadedRenderer != null) {
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
                if (Math.abs(event.getY() - this.mFlexPanelScrollY)
                        > ViewConfiguration.get(this.mContext).getScaledTouchSlop() + 1) {
                    this.mFlexPanelScrollEnabled = true;
                    MotionEvent downEvent = event.copy();
                    downEvent.setLocation(event.getX(), this.mFlexPanelScrollY);
                    downEvent.setAction(0);
                    this.mView.dispatchPointerEvent(downEvent);
                    return false;
                }
                return true;
            default:
                return false;
        }
    }

    public void requestRecomputeViewAttributes() {
        if (this.mIsInTraversal) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.view.ViewRootImpl$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewRootImpl.this.lambda$requestRecomputeViewAttributes$18();
                        }
                    });
        } else {
            this.mAttachInfo.mRecomputeGlobalAttributes = true;
        }
        if (DEBUG_TRAVERSAL
                && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [19] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestRecomputeViewAttributes$18() {
        this.mAttachInfo.mRecomputeGlobalAttributes = true;
    }

    public void dispatchSPenGestureEvent(InputEvent[] events) {
        Message msg = this.mHandler.obtainMessage(103);
        msg.obj = events;
        this.mHandler.sendMessage(msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDispatchSPenGestureEvent(InputEvent[] events) {
        if (events == null) {
            Slog.e(TAG, "dispatchSPenGestureEventInjection : Event is null!");
            return;
        }
        long firstEventTime = events.length > 0 ? events[0].getEventTime() : -1L;
        for (final InputEvent event : events) {
            if (event != null) {
                Runnable runnable =
                        new Runnable() { // from class:
                                         // android.view.ViewRootImpl$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                ViewRootImpl.this.lambda$handleDispatchSPenGestureEvent$19(event);
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
    public /* synthetic */ void lambda$handleDispatchSPenGestureEvent$19(InputEvent event) {
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

    private IBinder getDragStateInputToken() {
        try {
            return this.mAttachInfo.mSession.getDragStateInputToken();
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to getDragStateInputToken", e);
            return null;
        }
    }

    private int getDragPointerId() {
        try {
            return this.mAttachInfo.mSession.getDragPointerId();
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to getDragPointerId", e);
            return -1;
        }
    }

    private int getDragDeviceId() {
        try {
            return this.mAttachInfo.mSession.getDragDeviceId();
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to getDragDeviceId", e);
            return -1;
        }
    }
}
