package android.view;

import android.Manifest;
import android.animation.AnimationHandler;
import android.animation.LayoutTransition;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.app.ResourcesManager;
import android.app.UiModeManager;
import android.app.WindowConfiguration;
import android.app.compat.CompatChanges;
import android.app.servertransaction.WindowStateTransactionItem;
import android.app.slice.Slice;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
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
import android.graphics.drawable.ColorDrawable;
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
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.Vibrator;
import android.provider.Settings;
import android.sysprop.DisplayProperties;
import android.sysprop.ViewProperties;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.LongArray;
import android.util.MergedConfiguration;
import android.util.NtpTrustedTime;
import android.util.SequenceUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.proto.ProtoOutputStream;
import android.view.ActionMode;
import android.view.AttachedSurfaceControl;
import android.view.Choreographer;
import android.view.GestureDetector;
import android.view.ISensitiveContentProtectionManager;
import android.view.IWindow;
import android.view.InputQueue;
import android.view.InsetsSourceControl;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ScrollCaptureResponse;
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
import android.view.accessibility.IWindowSurfaceInfoCallback;
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
import android.window.DesktopModeFlags;
import android.window.InputTransferToken;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import android.window.ScreenCapture;
import android.window.SurfaceSyncGroup;
import android.window.WindowOnBackInvokedDispatcher;
import android.window.WindowTokenClient;
import android.window.WindowTokenClientController;
import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.internal.os.IResultReceiver;
import com.android.internal.os.SomeArgs;
import com.android.internal.policy.DecorView;
import com.android.internal.policy.PhoneFallbackEventHandler;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.protolog.ProtoLog;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.view.BaseSurfaceHolder;
import com.android.internal.view.RootViewSurfaceTaker;
import com.android.internal.view.SurfaceCallbackHelper;
import com.android.modules.expresslog.Counter;
import com.android.os.coregraphics.HwuiStatsLog;
import com.samsung.android.content.smartclip.SmartClipDataCropperImpl;
import com.samsung.android.content.smartclip.SmartClipDataExtractionEvent;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import com.samsung.android.util.SemViewUtils;
import com.samsung.android.widget.SemPressGestureDetector;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import libcore.io.IoUtils;

/* loaded from: classes4.dex */
public final class ViewRootImpl implements ViewParent, View.AttachInfo.Callbacks, ThreadedRenderer.DrawCallbacks, AttachedSurfaceControl {
    private static final String AOD_SHOW_STATE = "aod_show_state";
    private static final int BOUNDS_SURFACE_SUB_LAYER = -3;
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
    private static final long DISABLE_DRAW_WAKE_LOCK = 349153669;
    private static final boolean ENABLE_INPUT_LATENCY_TRACKING = true;
    private static final int FRAME_RATE_BOOST_TIME = 3000;
    private static final int FRAME_RATE_CATEGORY_COUNT = 5;
    private static final int FRAME_RATE_INITIAL_TOUCH_BOOST_TIME = 30;
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
    private static final int MSG_INITIAL_TOUCH_BOOST_TIMEOUT = 44;
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
    private static boolean sPreInitializedBufferAllocator;
    private static boolean sProtoLogInitialized;
    static final ThreadLocal<HandlerActionQueue> sRunQueues;
    private static boolean sSafeScheduleTraversals;
    private static boolean sSurfaceFlingerBugfixFlagValue;
    private static final Object sSyncProgressLock;
    private static boolean sToolkitEnableInvalidateCheckThreadFlagValue;
    private static boolean sToolkitFrameRateDebugFlagValue;
    private static boolean sToolkitFrameRateFunctionEnablingReadOnlyFlagValue;
    private static boolean sToolkitFrameRateTypingReadOnlyFlagValue;
    private static boolean sToolkitFrameRateVelocityMappingReadOnlyFlagValue;
    private static final boolean sToolkitFrameRateViewEnablingReadOnlyFlagValue;
    private static final boolean sToolkitInitialTouchBoostFlagValue;
    private static boolean sToolkitMetricsForFrameRateDecisionFlagValue;
    private static boolean sToolkitSetFrameRateReadOnlyFlagValue;
    static BLASTBufferQueue.TransactionHangCallback sTransactionHangCallback;
    private final int FLAG_EXTERNAL_DESKTOP_WINDOWING;
    private boolean isApplicationUid;
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
    private IBinder mBbqApplyToken;
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
    private long mColorModeLastSetMillis;
    private CompatOnBackInvokedCallback mCompatOnBackInvokedCallback;
    final SystemUiVisibilityInfo mCompatibleVisibilityInfo;
    final ConsumeBatchedInputImmediatelyRunnable mConsumeBatchedInputImmediatelyRunnable;
    boolean mConsumeBatchedInputImmediatelyScheduled;
    boolean mConsumeBatchedInputScheduled;
    final ConsumeBatchedInputRunnable mConsumedBatchedInputRunnable;
    int mContentCaptureEnabled;
    final ContentResolver mContentResolver;
    public final Context mContext;
    int mCurScrollY;
    private int mCurrentColorMode;
    View mCurrentDragView;
    private int mCutoutPolicy;
    private boolean mDeferTransactionRequested;
    private final int mDensity;
    private boolean mDesktopMode;
    private Rect mDirty;
    private final boolean mDisableDrawWakeLock;
    int mDispatchedSystemBarAppearance;
    int mDispatchedSystemUiVisibility;
    Display mDisplay;
    private boolean mDisplayChanged;
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
    private int mFlingFrameRateChange;
    private ContentObserver mFlingFrameRateSettingObserver;
    private boolean mForceDecorViewVisibility;
    private boolean mForceDraw;
    private ContentObserver mForceInvertObserver;
    private boolean mForceModeInScreenshot;
    private boolean mForceNextConfigUpdate;
    boolean mForceNextWindowRelayout;
    private boolean mForceUpdateBoundsLayer;
    private int mFpsNumFrames;
    private long mFpsPrevTime;
    private long mFpsStartTime;
    private String mFpsTraceName;
    private int mFrameRateCategoryChangeReason;
    private int mFrameRateCategoryDirtyHintCount;
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
    private boolean mIsFrameRateBoosting;
    private boolean mIsFrameRateConflicted;
    private boolean mIsHRR;
    boolean mIsInTraversal;
    private boolean mIsPressedGesture;
    private final boolean mIsStylusPointerIconEnabled;
    private final boolean mIsSubscribeGranularDisplayEventsEnabled;
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
    private int mLastReportedActiveControlsSeq;
    private ActivityWindowInfo mLastReportedActivityWindowInfo;
    private final ClientWindowFrames mLastReportedFrames;
    private int mLastReportedInsetsStateSeq;
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
    boolean mNewSurfaceNeeded;
    private final int mNoncompatDensity;
    private int mNumPausedForSync;
    private final WindowOnBackInvokedDispatcher mOnBackInvokedDispatcher;
    int mOrigWindowType;
    Rect mOverrideInsetsFrame;
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
    private boolean mProfile;
    private boolean mProfileRendering;
    private QueuedInputEvent mQueuedInputEventPool;
    private int mQueuedInputEventPoolSize;
    private boolean mRelayoutRequested;
    private final WindowRelayoutResult mRelayoutResult;
    private int mRelayoutSeq;
    private boolean mRemoved;
    private Choreographer.FrameCallback mRenderProfiler;
    private boolean mRenderProfilingEnabled;
    private long mRenderThreadDrawStartTimeNs;
    boolean mReportNextDraw;
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
    private final boolean mSendPerfHintOnTouch;
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
    private boolean mTouchAndDrawn;
    private final SurfaceControl.Transaction mTouchHintTransaction;
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
    private boolean mUpdateSurfaceNeeded;
    private boolean mUseMTRenderer;
    Vibrator mVibrator;
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

    public interface SurfaceChangedCallback {
        void surfaceCreated(SurfaceControl.Transaction transaction);

        void surfaceDestroyed();

        void surfaceReplaced(SurfaceControl.Transaction transaction);

        default void vriDrawStarted(boolean z) {
        }
    }

    public static void invokeFunctor(long j, boolean z) {
    }

    private int mappingToMousePointer(int i) {
        if (i == 20001) {
            return 10121;
        }
        switch (i) {
            case 20006:
                return 10122;
            case 20007:
                return 10123;
            case 20008:
                return 10124;
            case 20009:
                return 10125;
            case 20010:
                return 10121;
            default:
                return i > 20000 ? i - 9900 : i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldTouchHint(int i, int i2) {
        return (i != 4) && !(i2 == 2011);
    }

    @Override // android.view.ViewParent
    public void bringChildToFront(View view) {
    }

    @Override // android.view.ViewParent
    public boolean canResolveLayoutDirection() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean canResolveTextAlignment() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean canResolveTextDirection() {
        return true;
    }

    @Override // android.view.ViewParent
    public void childDrawableStateChanged(View view) {
    }

    @Override // android.view.ViewParent
    public void childHasTransientStateChanged(View view, boolean z) {
    }

    @Override // android.view.ViewParent
    public void createContextMenu(ContextMenu contextMenu) {
    }

    public void detachFunctor(long j) {
    }

    @Override // android.view.ViewParent
    public int getLayoutDirection() {
        return 0;
    }

    @Override // android.view.ViewParent
    public ViewParent getParent() {
        return null;
    }

    @Override // android.view.ViewParent
    public ViewParent getParentForAccessibility() {
        return null;
    }

    @Override // android.view.ViewParent
    public int getTextAlignment() {
        return 1;
    }

    @Override // android.view.ViewParent
    public int getTextDirection() {
        return 1;
    }

    @Override // android.view.ViewParent
    public boolean isLayoutDirectionResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean isTextAlignmentResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean isTextDirectionResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean onNestedPrePerformAccessibilityAction(View view, int i, Bundle bundle) {
        return false;
    }

    @Override // android.view.ViewParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
    }

    @Override // android.view.ViewParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
    }

    @Override // android.view.ViewParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
    }

    @Override // android.view.ViewParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        return false;
    }

    @Override // android.view.ViewParent
    public void onStopNestedScroll(View view) {
    }

    @Override // android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
    }

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(View view) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(View view, float f, float f2) {
        return false;
    }

    @Override // android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i) {
        return null;
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
        sRunQueues = new ThreadLocal<>();
        sFirstDrawHandlers = new ArrayList<>();
        sFirstDrawComplete = false;
        sConfigCallbacks = new ArrayList<>();
        sCompatibilityDone = false;
        sPreInitializedBufferAllocator = false;
        mResizeInterpolator = new AccelerateDecelerateInterpolator();
        sSyncProgressLock = new Object();
        sNumSyncsInProgress = 0;
        sAnrReported = false;
        sTransactionHangCallback = new BLASTBufferQueue.TransactionHangCallback() { // from class: android.view.ViewRootImpl.1
            @Override // android.graphics.BLASTBufferQueue.TransactionHangCallback
            public void onTransactionHang(String str) {
                if (ViewRootImpl.sAnrReported) {
                    return;
                }
                ViewRootImpl.sAnrReported = true;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ActivityManager.getService().appNotResponding(str);
                } catch (RemoteException unused) {
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };
        sSafeScheduleTraversals = false;
        sToolkitFrameRateVelocityMappingReadOnlyFlagValue = Flags.toolkitFrameRateVelocityMappingReadOnly();
        sToolkitEnableInvalidateCheckThreadFlagValue = Flags.enableInvalidateCheckThread();
        sSurfaceFlingerBugfixFlagValue = com.android.graphics.surfaceflinger.flags.Flags.vrrBugfix24q4();
        sEnableVrr = ViewProperties.vrr_enabled().orElse(true).booleanValue();
        sToolkitInitialTouchBoostFlagValue = Flags.toolkitInitialTouchBoost();
        sToolkitFrameRateDebugFlagValue = Flags.toolkitFrameRateDebug();
        sToolkitSetFrameRateReadOnlyFlagValue = Flags.toolkitSetFrameRateReadOnly();
        sToolkitMetricsForFrameRateDecisionFlagValue = Flags.toolkitMetricsForFrameRateDecision();
        sToolkitFrameRateTypingReadOnlyFlagValue = Flags.toolkitFrameRateTypingReadOnly();
        sToolkitFrameRateFunctionEnablingReadOnlyFlagValue = Flags.toolkitFrameRateFunctionEnablingReadOnly();
        sToolkitFrameRateViewEnablingReadOnlyFlagValue = Flags.toolkitFrameRateViewEnablingReadOnly();
        sProtoLogInitialized = false;
    }

    public interface ActivityConfigCallback {
        default void onConfigurationChanged(Configuration configuration, int i) {
            throw new IllegalStateException("Not implemented");
        }

        default void onConfigurationChanged(Configuration configuration, int i, ActivityWindowInfo activityWindowInfo) {
            onConfigurationChanged(configuration, i);
        }
    }

    protected FrameInfo getUpdatedFrameInfo() {
        FrameInfo frameInfo = this.mChoreographer.mFrameInfo;
        this.mViewFrameInfo.populateFrameInfo(frameInfo);
        this.mViewFrameInfo.reset();
        this.mInputEventAssigner.notifyFrameProcessed();
        return frameInfo;
    }

    public boolean isCutoutRemoveNeeded() {
        return this.mIsCutoutRemoveNeeded;
    }

    private void updateCutoutRemoveNeeded(int i) {
        boolean z = (2097152 & i) != 0 || (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED && getCompatWindowConfiguration().isSplitScreen());
        if (this.mIsCutoutRemoveNeeded != z) {
            this.mIsCutoutRemoveNeeded = z;
            if (!z) {
                this.mLastWindowInsets = null;
            }
            this.mApplyInsetsRequested = true;
        }
        boolean z2 = (i & 4194304) != 0;
        if (this.mIsCutoutRemoveForDispatchNeeded != z2) {
            this.mIsCutoutRemoveForDispatchNeeded = z2;
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

        SystemUiVisibilityInfo() {
        }
    }

    public HandwritingInitiator getHandwritingInitiator() {
        return this.mHandwritingInitiator;
    }

    public ViewRootImpl(Context context, Display display) {
        this(context, display, WindowManagerGlobal.getWindowSession(), new WindowLayout());
    }

    public ViewRootImpl(Context context, Display display, IWindowSession iWindowSession, WindowLayout windowLayout) {
        this.mTransformHintListeners = new ArrayList<>();
        this.mPreviousTransformHint = 0;
        this.mFlingFrameRateChange = 0;
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
        this.mPendingTransaction = new SurfaceControl.Transaction();
        this.mUnbufferedInputSource = 0;
        this.mPendingInputEventQueueLengthCounterName = "pq";
        this.mUnhandledKeyManager = new UnhandledKeyManager();
        this.mWindowAttributesChanged = false;
        this.mSurface = new Surface();
        SurfaceControl surfaceControl = new SurfaceControl();
        this.mSurfaceControl = surfaceControl;
        this.mBbqApplyToken = new Binder();
        this.mHdrRenderState = new HdrRenderState(this);
        this.mTransaction = new SurfaceControl.Transaction();
        this.mFrameRateTransaction = new SurfaceControl.Transaction();
        this.mTouchHintTransaction = new SurfaceControl.Transaction();
        ClientWindowFrames clientWindowFrames = new ClientWindowFrames();
        this.mTmpFrames = clientWindowFrames;
        this.mPendingBackDropFrame = new Rect();
        this.mWinFrameInScreen = new Rect();
        InsetsState insetsState = new InsetsState();
        this.mTempInsets = insetsState;
        InsetsSourceControl.Array array = new InsetsSourceControl.Array();
        this.mTempControls = array;
        this.mTempWinConfig = new WindowConfiguration();
        this.mInvCompatScale = 1.0f;
        this.mLastGivenInsets = new ViewTreeObserver.InternalInsetsInfo();
        this.mTypesHiddenByFlags = 0;
        this.mLastConfigurationFromResources = new Configuration();
        this.mLastReportedMergedConfiguration = new MergedConfiguration();
        MergedConfiguration mergedConfiguration = new MergedConfiguration();
        this.mPendingMergedConfiguration = mergedConfiguration;
        this.mLastReportedFrames = new ClientWindowFrames();
        this.mLastReportedInsetsStateSeq = SequenceUtils.getInitSeq();
        this.mLastReportedActiveControlsSeq = SequenceUtils.getInitSeq();
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
        this.mAccessibilityInteractionConnectionManager = new AccessibilityInteractionConnectionManager();
        this.mInLayout = false;
        this.mLayoutRequesters = new ArrayList<>();
        this.mHandlingLayoutInLayoutRequest = false;
        this.mInputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        this.mBlurRegionAggregator = new BackgroundBlurDrawable.Aggregator(this);
        this.mSmartClipDispatcherProxy = null;
        this.mCutoutPolicy = 0;
        this.mTmpAttrs = new WindowManager.LayoutParams();
        this.mGestureExclusionTracker = new ViewRootRectTracker(new Function() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda21
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                List systemGestureExclusionRects;
                systemGestureExclusionRects = ((View) obj).getSystemGestureExclusionRects();
                return systemGestureExclusionRects;
            }
        });
        this.mKeepClearRectsTracker = new ViewRootRectTracker(new Function() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda22
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                List collectPreferKeepClearRects;
                collectPreferKeepClearRects = ((View) obj).collectPreferKeepClearRects();
                return collectPreferKeepClearRects;
            }
        });
        this.mUnrestrictedKeepClearRectsTracker = new ViewRootRectTracker(new Function() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda23
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
        this.mTouchAndDrawn = false;
        this.mFrameRateCompatibility = 1;
        this.mIsTouchHint = false;
        this.mFrameRateCategoryHighCount = 0;
        this.mFrameRateCategoryHighHintCount = 0;
        this.mFrameRateCategoryNormalCount = 0;
        this.mFrameRateCategoryLowCount = 0;
        this.mFrameRateCategoryDirtyHintCount = 0;
        this.mRelayoutResult = new WindowRelayoutResult(clientWindowFrames, mergedConfiguration, surfaceControl, insetsState, array);
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
        this.FLAG_EXTERNAL_DESKTOP_WINDOWING = 131072;
        this.mDesktopMode = false;
        this.mAppStartTimestampsSent = new AtomicBoolean(false);
        this.mAppStartTrackingStarted = false;
        this.mRenderThreadDrawStartTimeNs = -1L;
        this.mFirstFramePresentedTimeNs = -1L;
        this.mPointerIconEvent = null;
        this.mCurrentColorMode = 0;
        this.mColorModeLastSetMillis = -1L;
        this.mViewRootSurfaceController = new ViewRootSurfaceController(this);
        this.mMinimumSizeForOverlappingWithCutoutAsDefault = 0;
        this.mSemPressGestureDetector = null;
        this.mBixbyTouchTriggered = false;
        this.mCanTriggerBixbyTouch = false;
        this.mFlexPanelScrollEnabled = false;
        this.mFlexPanelScrollY = 0.0f;
        this.mIsHRR = false;
        this.isApplicationUid = false;
        this.mProfile = false;
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: android.view.ViewRootImpl.5
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                if (ViewRootImpl.this.mExtraDisplayListenerLogging) {
                    Slog.i(ViewRootImpl.this.mTag, "Received onDisplayChanged - " + ViewRootImpl.this.mView);
                }
                if (ViewRootImpl.this.mView == null || ViewRootImpl.this.mDisplay.getDisplayId() != i) {
                    return;
                }
                int i2 = ViewRootImpl.this.mAttachInfo.mDisplayState;
                int state = ViewRootImpl.this.mDisplay.getState();
                Log.i(ViewRootImpl.this.mTag, "onDisplayChanged oldDisplayState=" + i2 + " newDisplayState=" + state);
                if (ViewRootImpl.this.mExtraDisplayListenerLogging) {
                    Slog.i(ViewRootImpl.this.mTag, "DisplayState - old: " + i2 + ", new: " + state);
                }
                if (Trace.isTagEnabled(32L)) {
                    Trace.traceCounter(32L, "vri#screenState[" + ViewRootImpl.this.mTag + "] state=", state);
                }
                if (i2 != state) {
                    ViewRootImpl.this.mAttachInfo.mDisplayState = state;
                    ViewRootImpl.this.pokeDrawLockIfNeeded();
                    if (i2 != 0) {
                        int viewScreenState = toViewScreenState(i2);
                        int viewScreenState2 = toViewScreenState(state);
                        if (viewScreenState != viewScreenState2) {
                            ViewRootImpl.this.mView.dispatchScreenStateChanged(viewScreenState2);
                        }
                        if (ViewRootImpl.DEBUG_TRAVERSAL && ViewRootImpl.DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
                            Log.i(ViewRootImpl.this.mTag, "Traversal, [4] mView=" + ViewRootImpl.this.mView + " oldDisplayState=" + i2);
                        }
                        if (i2 == 1) {
                            ViewRootImpl.this.mFullRedrawNeeded = true;
                            ViewRootImpl.this.scheduleTraversals();
                        } else if ((i2 == 3 || i2 == 4) && state == 2 && i == 0 && (ViewRootImpl.this.mWindowAttributes.samsungFlags & 262144) == 0) {
                            ViewRootImpl.this.mFullRedrawNeeded = true;
                            ViewRootImpl.this.scheduleTraversals();
                        }
                    }
                }
            }

            private int toViewScreenState(int i) {
                return Settings.System.getInt(ViewRootImpl.this.mContentResolver, ViewRootImpl.AOD_SHOW_STATE, 0) != 0 ? i == 2 ? 1 : 0 : i == 1 ? 0 : 1;
            }
        };
        this.mSurfaceChangedCallbacks = new ArrayList<>();
        this.mWebViewAttached = false;
        ViewRootHandler viewRootHandler = new ViewRootHandler();
        this.mHandler = viewRootHandler;
        this.mExecutor = new Executor() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda24
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
        this.mContext = context;
        this.mWindowSession = iWindowSession;
        this.mWindowLayout = windowLayout;
        this.mDisplay = display;
        if (display == null) {
            Log.i(TAG, "ViewRootImpl, mDisplay is null #1");
        }
        String basePackageName = context.getBasePackageName();
        this.mBasePackageName = basePackageName;
        String orElse = DisplayProperties.debug_vri_package().orElse(null);
        this.mExtraDisplayListenerLogging = !TextUtils.isEmpty(orElse) && orElse.equals(basePackageName);
        this.mContentResolver = context.getContentResolver();
        this.mThread = Thread.currentThread();
        this.mHCTRelayoutHandler = new HCTRelayoutHandler();
        this.mLocation = new WindowLeaked(null);
        this.mWidth = -1;
        this.mHeight = -1;
        this.mDirty = new Rect();
        this.mWinFrame = new Rect();
        this.mLastLayoutFrame = new Rect();
        W w = new W(this);
        this.mWindow = w;
        this.mLeashToken = new Binder();
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        this.mViewVisibility = 8;
        this.mTransparentRegion = new Region();
        this.mPreviousTransparentRegion = new Region();
        this.mFirst = true;
        this.mPerformContentCapture = true;
        this.mAdded = false;
        this.mAttachInfo = new View.AttachInfo(iWindowSession, w, display, this, viewRootHandler, this, context);
        this.mCompatibleVisibilityInfo = new SystemUiVisibilityInfo();
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        this.mHighContrastTextManager = new HighContrastTextManager();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mViewConfiguration = viewConfiguration;
        this.mDensity = context.getResources().getDisplayMetrics().densityDpi;
        this.mNoncompatDensity = context.getResources().getDisplayMetrics().noncompatDensityDpi;
        this.mFallbackEventHandler = new PhoneFallbackEventHandler(context);
        this.mChoreographer = Choreographer.getInstance();
        InsetsController insetsController = new InsetsController(new ViewRootInsetsControllerHost(this));
        this.mInsetsController = insetsController;
        this.mImeBackAnimationController = new ImeBackAnimationController(this, insetsController);
        this.mHandwritingInitiator = new HandwritingInitiator(viewConfiguration, (InputMethodManager) context.getSystemService(InputMethodManager.class));
        this.mViewBoundsSandboxingEnabled = getViewBoundsSandboxingEnabled();
        this.mIsStylusPointerIconEnabled = InputSettings.isStylusPointerIconEnabled(context);
        initializeProtoLogInProcess();
        String string = context.getResources().getString(R.string.config_inputEventCompatProcessorOverrideClassName);
        if (string.isEmpty()) {
            this.mInputCompatProcessor = new InputEventCompatProcessor(context, viewRootHandler);
        } else {
            try {
                try {
                    this.mInputCompatProcessor = (InputEventCompatProcessor) Class.forName(string).getConstructor(Context.class).newInstance(context);
                } catch (Exception e) {
                    Log.e(TAG, "Unable to create the InputEventCompatProcessor. ", e);
                    this.mInputCompatProcessor = null;
                }
            } catch (Throwable th) {
                this.mInputCompatProcessor = null;
                throw th;
            }
        }
        if (!sCompatibilityDone) {
            sAlwaysAssignFocus = this.mTargetSdkVersion < 28;
            sCompatibilityDone = true;
        }
        this.mIsDeviceDefault = SemViewUtils.isDeviceDefaultFamily(context);
        updateDesktopMode();
        if (CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT) {
            this.mMinimumSizeForOverlappingWithCutoutAsDefault = this.mContext.getResources().getDimensionPixelSize(R.dimen.samsung_minimum_size_for_overlapping_with_cutout_as_default);
        }
        this.mInputManagerService = IInputManager.Stub.asInterface(ServiceManager.getService("input"));
        loadSystemProperties();
        this.mImeFocusController = new ImeFocusController(this);
        this.mScrollCaptureRequestTimeout = 2500L;
        this.mOnBackInvokedDispatcher = new WindowOnBackInvokedDispatcher(context, Looper.myLooper());
        if (Flags.sensitiveContentAppProtection()) {
            ISensitiveContentProtectionManager asInterface = ISensitiveContentProtectionManager.Stub.asInterface(ServiceManager.getService(Context.SENSITIVE_CONTENT_PROTECTION_SERVICE));
            this.mSensitiveContentProtectionService = asInterface;
            if (asInterface == null) {
                Log.e(TAG, "SensitiveContentProtectionService shouldn't be null");
            }
        } else {
            this.mSensitiveContentProtectionService = null;
        }
        this.mAppStartInfoTimestampsFlagValue = android.app.Flags.appStartInfoTimestamps();
        this.mDisableDrawWakeLock = CompatChanges.isChangeEnabled(DISABLE_DRAW_WAKE_LOCK) && Flags.disableDrawWakeLock();
        this.mIsSubscribeGranularDisplayEventsEnabled = com.android.server.display.feature.flags.Flags.subscribeGranularDisplayEvents();
        this.mSendPerfHintOnTouch = android.adpf.Flags.adpfViewrootimplActionDownBoost();
        if (!sPreInitializedBufferAllocator) {
            preInitBufferAllocator();
            sPreInitializedBufferAllocator = true;
        }
        this.mSmartClipDispatcherProxy = new SmartClipRemoteRequestDispatcherProxy(context);
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            this.mMotionEventMonitor = new MotionEventMonitor();
        }
        if (!CoreRune.FW_VRR_DISCRETE) {
            sToolkitSetFrameRateReadOnlyFlagValue = false;
        }
        Log.i(TAG, "dVRR is ".concat(sToolkitSetFrameRateReadOnlyFlagValue ? "enabled" : "disabled"));
    }

    private void updateDesktopMode() {
        Display display = this.mDisplay;
        if (display != null && display.getDisplayId() != -1 && this.mDisplay.getDisplayId() != 0 && (this.mDisplay.getFlags() & 131072) != 0) {
            this.mDesktopMode = true;
        } else {
            this.mDesktopMode = false;
        }
        Log.d(this.mTag, "desktopMode is " + this.mDesktopMode);
    }

    public static void addFirstDrawHandler(Runnable runnable) {
        ArrayList<Runnable> arrayList = sFirstDrawHandlers;
        synchronized (arrayList) {
            if (!sFirstDrawComplete) {
                arrayList.add(runnable);
            }
        }
    }

    public static void addConfigCallback(ConfigChangedCallback configChangedCallback) {
        ArrayList<ConfigChangedCallback> arrayList = sConfigCallbacks;
        synchronized (arrayList) {
            arrayList.add(configChangedCallback);
        }
    }

    public static void removeConfigCallback(ConfigChangedCallback configChangedCallback) {
        ArrayList<ConfigChangedCallback> arrayList = sConfigCallbacks;
        synchronized (arrayList) {
            arrayList.remove(configChangedCallback);
        }
    }

    public void setActivityConfigCallback(ActivityConfigCallback activityConfigCallback) {
        this.mActivityConfigCallback = activityConfigCallback;
        if (activityConfigCallback == null) {
            this.mPendingActivityWindowInfo = null;
            this.mLastReportedActivityWindowInfo = null;
        } else {
            this.mPendingActivityWindowInfo = new ActivityWindowInfo();
            this.mLastReportedActivityWindowInfo = new ActivityWindowInfo();
        }
    }

    public void setOnContentApplyWindowInsetsListener(Window.OnContentApplyWindowInsetsListener onContentApplyWindowInsetsListener) {
        this.mAttachInfo.mContentOnApplyWindowInsetsListener = onContentApplyWindowInsetsListener;
        if (this.mFirst) {
            return;
        }
        requestFitSystemWindows();
    }

    public void addWindowCallbacks(WindowCallbacks windowCallbacks) {
        this.mWindowCallbacks.add(windowCallbacks);
    }

    public void removeWindowCallbacks(WindowCallbacks windowCallbacks) {
        this.mWindowCallbacks.remove(windowCallbacks);
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
        View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return this.mContext.getResources().getBoolean(R.bool.config_defaultInTouchMode);
        }
        return attachInfo.mInTouchMode;
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

    /* JADX INFO: Access modifiers changed from: private */
    public Configuration getConfiguration() {
        return this.mContext.getResources().getConfiguration();
    }

    private WindowConfiguration getCompatWindowConfiguration() {
        WindowConfiguration windowConfiguration = getConfiguration().windowConfiguration;
        if (this.mInvCompatScale == 1.0f) {
            return windowConfiguration;
        }
        this.mTempWinConfig.setTo(windowConfiguration);
        this.mTempWinConfig.scale(this.mInvCompatScale);
        return this.mTempWinConfig;
    }

    public void setView(View view, WindowManager.LayoutParams layoutParams, View view2) {
        setView(view, layoutParams, view2, UserHandle.myUserId());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:121:0x040d A[Catch: all -> 0x0622, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x04ac A[Catch: all -> 0x0622, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0172 A[Catch: all -> 0x0622, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x010d A[Catch: all -> 0x0622, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0127 A[Catch: all -> 0x0622, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x013c A[Catch: all -> 0x0622, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0178 A[Catch: all -> 0x0622, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0186 A[Catch: all -> 0x0622, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0196 A[Catch: all -> 0x0622, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01c6 A[Catch: all -> 0x0622, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x021a A[Catch: all -> 0x05f4, RemoteException | RuntimeException -> 0x05f6, TryCatch #2 {RemoteException | RuntimeException -> 0x05f6, blocks: (B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247), top: B:72:0x01ce, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0244 A[Catch: all -> 0x05f4, RemoteException | RuntimeException -> 0x05f6, TryCatch #2 {RemoteException | RuntimeException -> 0x05f6, blocks: (B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247), top: B:72:0x01ce, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0259 A[Catch: all -> 0x0622, DONT_GENERATE, TRY_ENTER, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02c0 A[Catch: all -> 0x0622, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02d5 A[Catch: all -> 0x0622, TryCatch #0 {, blocks: (B:5:0x0037, B:7:0x003b, B:9:0x0057, B:10:0x005d, B:12:0x008a, B:14:0x0090, B:16:0x0096, B:17:0x009e, B:19:0x00ae, B:21:0x00b9, B:22:0x00ca, B:24:0x00cf, B:26:0x00d6, B:28:0x00dc, B:30:0x00e2, B:31:0x00f9, B:33:0x010d, B:36:0x011a, B:38:0x011e, B:40:0x0123, B:42:0x0127, B:43:0x0138, B:45:0x013c, B:46:0x0152, B:49:0x0167, B:52:0x0174, B:54:0x0178, B:55:0x0180, B:57:0x0186, B:58:0x0189, B:60:0x0196, B:61:0x019d, B:64:0x01a9, B:66:0x01b3, B:68:0x01bb, B:69:0x01c0, B:71:0x01c6, B:84:0x0259, B:85:0x025c, B:88:0x0266, B:90:0x02c0, B:92:0x02d5, B:94:0x02e0, B:95:0x02e4, B:96:0x02f0, B:97:0x02f3, B:98:0x03fb, B:99:0x040c, B:101:0x02f7, B:102:0x030f, B:103:0x0310, B:104:0x0328, B:105:0x0329, B:106:0x0341, B:107:0x0342, B:108:0x035a, B:109:0x035b, B:111:0x035d, B:112:0x0381, B:113:0x0382, B:114:0x03a1, B:115:0x03a2, B:116:0x03ba, B:117:0x03bb, B:118:0x03df, B:119:0x03e0, B:120:0x03fa, B:121:0x040d, B:123:0x041e, B:124:0x0444, B:126:0x0448, B:128:0x0453, B:130:0x0457, B:131:0x0463, B:133:0x047b, B:138:0x0485, B:140:0x0489, B:141:0x04a6, B:143:0x04ac, B:144:0x04c6, B:146:0x04cc, B:149:0x04d6, B:152:0x04df, B:154:0x04e5, B:157:0x04ed, B:159:0x04ef, B:161:0x04f3, B:162:0x04fe, B:164:0x0506, B:165:0x050e, B:167:0x0514, B:168:0x0518, B:170:0x051c, B:171:0x0527, B:173:0x05e0, B:174:0x05e3, B:176:0x05ea, B:178:0x05ee, B:187:0x061c, B:188:0x061f, B:197:0x0172, B:202:0x0620, B:73:0x01ce, B:75:0x021a, B:76:0x0224, B:79:0x0240, B:81:0x0244, B:82:0x0247, B:190:0x05f7, B:192:0x05fd, B:193:0x0600, B:194:0x0619), top: B:4:0x0037, inners: #1 }] */
    /* JADX WARN: Type inference failed for: r23v0 */
    /* JADX WARN: Type inference failed for: r23v1 */
    /* JADX WARN: Type inference failed for: r23v2 */
    /* JADX WARN: Type inference failed for: r43v0, types: [android.view.View, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setView(android.view.View r43, android.view.WindowManager.LayoutParams r44, android.view.View r45, int r46) {
        /*
            Method dump skipped, instructions count: 1600
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.setView(android.view.View, android.view.WindowManager$LayoutParams, android.view.View, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAccessibilityWindowAttributesIfNeeded() {
        if (this.mAttachInfo.mAccessibilityWindowId != -1) {
            AccessibilityWindowAttributes accessibilityWindowAttributes = new AccessibilityWindowAttributes(this.mWindowAttributes, this.mContext.getResources().getConfiguration().getLocales());
            if (accessibilityWindowAttributes.equals(this.mAccessibilityWindowAttributes)) {
                return;
            }
            this.mAccessibilityWindowAttributes = accessibilityWindowAttributes;
            this.mAccessibilityManager.setAccessibilityWindowAttributes(getDisplayId(), this.mAttachInfo.mAccessibilityWindowId, accessibilityWindowAttributes);
        }
    }

    private void registerListeners() {
        if (this.mExtraDisplayListenerLogging) {
            Slog.i(this.mTag, "Register listeners: " + this.mBasePackageName);
        }
        this.mAccessibilityManager.addAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager, this.mHandler);
        this.mAccessibilityManager.addHighContrastTextStateChangeListener(this.mExecutor, this.mHighContrastTextManager);
        DisplayManagerGlobal.getInstance().registerDisplayListener(this.mDisplayListener, this.mHandler, this.mIsSubscribeGranularDisplayEventsEnabled ? 133L : 71L, this.mBasePackageName);
        if (android.view.accessibility.Flags.forceInvertColor() && this.mForceInvertObserver == null) {
            this.mForceInvertObserver = new ContentObserver(this.mHandler) { // from class: android.view.ViewRootImpl.2
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    ViewRootImpl.this.updateForceDarkMode();
                }
            };
            Uri[] uriArr = {Settings.Secure.getUriFor(Settings.Secure.ACCESSIBILITY_FORCE_INVERT_COLOR_ENABLED), Settings.Secure.getUriFor(Settings.Secure.UI_NIGHT_MODE)};
            for (int i = 0; i < 2; i++) {
                this.mContext.getContentResolver().registerContentObserver(uriArr[i], false, this.mForceInvertObserver, UserHandle.myUserId());
            }
        }
        if (CoreRune.FW_ARR_FLING_FLEXIBLE_FRAME_RATE && this.mFlingFrameRateSettingObserver == null) {
            this.mFlingFrameRateSettingObserver = new ContentObserver(this.mHandler) { // from class: android.view.ViewRootImpl.3
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    ViewRootImpl viewRootImpl = ViewRootImpl.this;
                    viewRootImpl.mFlingFrameRateChange = Settings.Global.getInt(viewRootImpl.mContext.getContentResolver(), Settings.Global.FLING_FLEXIBLE_FRAME_RATE, 0);
                    Log.d(ViewRootImpl.this.mTag, "mFlingFrameRateSettingObserver onChange : " + ViewRootImpl.this.mFlingFrameRateChange);
                }
            };
            this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor(Settings.Global.FLING_FLEXIBLE_FRAME_RATE), false, this.mFlingFrameRateSettingObserver);
        }
        if (this.mAttachInfo == null || this.mDisplay.getState() == this.mAttachInfo.mDisplayState || this.mAttachInfo.mDisplay == null || this.mAttachInfo.mDisplay.getDisplayId() != this.mDisplay.getDisplayId()) {
            return;
        }
        this.mAttachInfo.mDisplayState = this.mDisplay.getState();
        Log.i(this.mTag, "synced displayState. AttachInfo displayState=" + this.mAttachInfo.mDisplayState);
    }

    private void unregisterListeners() {
        this.mAccessibilityManager.removeAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager);
        this.mAccessibilityManager.removeHighContrastTextStateChangeListener(this.mHighContrastTextManager);
        DisplayManagerGlobal.getInstance().unregisterDisplayListener(this.mDisplayListener);
        if (android.view.accessibility.Flags.forceInvertColor() && this.mForceInvertObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mForceInvertObserver);
            this.mForceInvertObserver = null;
        }
        if (CoreRune.FW_ARR_FLING_FLEXIBLE_FRAME_RATE && this.mFlingFrameRateSettingObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mFlingFrameRateSettingObserver);
            this.mFlingFrameRateSettingObserver = null;
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
            if (this.mWindowAttributes.type == 2008) {
                this.mTag += "_2008";
            }
        }
    }

    public String getTag() {
        return this.mTag;
    }

    public static void setSafeScheduleTraversals(boolean z) {
        sSafeScheduleTraversals = z;
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
        ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
        if (threadedRenderer != null) {
            if (Looper.myLooper() != this.mAttachInfo.mHandler.getLooper()) {
                this.mAttachInfo.mHandler.postAtFrontOfQueue(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewRootImpl.this.destroyHardwareResources();
                    }
                });
            } else {
                threadedRenderer.destroyHardwareResources(this.mView);
                threadedRenderer.destroy();
            }
        }
    }

    void resetSoftwareCaches(View view) {
        if (view == null) {
            return;
        }
        view.destroyDrawingCache();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                resetSoftwareCaches(viewGroup.getChildAt(i));
            }
        }
    }

    public void registerAnimatingRenderNode(RenderNode renderNode) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerAnimatingRenderNode(renderNode);
            return;
        }
        if (this.mAttachInfo.mPendingAnimatingRenderNodes == null) {
            this.mAttachInfo.mPendingAnimatingRenderNodes = new ArrayList();
        }
        this.mAttachInfo.mPendingAnimatingRenderNodes.add(renderNode);
    }

    public void registerVectorDrawableAnimator(NativeVectorDrawableAnimator nativeVectorDrawableAnimator) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerVectorDrawableAnimator(nativeVectorDrawableAnimator);
        }
    }

    public void registerRtFrameCallback(final HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerRtFrameCallback(new HardwareRenderer.FrameDrawingCallback(this) { // from class: android.view.ViewRootImpl.4
                @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                public void onFrameDraw(long j) {
                }

                @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                public HardwareRenderer.FrameCommitCallback onFrameDraw(int i, long j) {
                    try {
                        return frameDrawingCallback.onFrameDraw(i, j);
                    } catch (Exception e) {
                        Log.e(ViewRootImpl.TAG, "Exception while executing onFrameDraw", e);
                        return null;
                    }
                }
            });
        }
    }

    private void enableHardwareAcceleration(WindowManager.LayoutParams layoutParams) {
        this.mAttachInfo.mHardwareAccelerated = false;
        this.mAttachInfo.mHardwareAccelerationRequested = false;
        if (this.mTranslator != null || (layoutParams.flags & 16777216) == 0 || CoreRune.GFW_DEBUG_DISABLE_HWRENDERING) {
            return;
        }
        boolean z = (layoutParams.privateFlags & 2) != 0;
        if (ThreadedRenderer.sRendererEnabled || z) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.destroy();
            }
            Rect rect = layoutParams.surfaceInsets;
            boolean z2 = layoutParams.format != -1 || (rect.left != 0 || rect.right != 0 || rect.top != 0 || rect.bottom != 0);
            ThreadedRenderer create = ThreadedRenderer.create(this.mContext, z2, layoutParams.getTitle().toString());
            this.mAttachInfo.mThreadedRenderer = create;
            create.setSurfaceControl(this.mSurfaceControl, this.mBlastBufferQueue);
            updateColorModeIfNeeded(layoutParams.getColorMode(), layoutParams.getDesiredHdrHeadroom());
            this.mHdrRenderState.forceUpdateHdrSdrRatio();
            updateForceDarkMode();
            if (ViewRune.COMMON_IS_PRODUCT_DEV) {
                Log.d(this.mTag, "ThreadedRenderer.create() translucent=" + z2);
            }
            this.mAttachInfo.mHardwareAccelerated = true;
            this.mAttachInfo.mHardwareAccelerationRequested = true;
            HardwareRendererObserver hardwareRendererObserver = this.mHardwareRendererObserver;
            if (hardwareRendererObserver != null) {
                create.addObserver(hardwareRendererObserver);
            }
        }
    }

    private int getNightMode() {
        return getConfiguration().uiMode & 48;
    }

    public int determineForceDarkType() {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(R.styleable.Theme);
        try {
            if (android.view.accessibility.Flags.forceInvertColor() && shouldApplyForceInvertDark()) {
                boolean z = obtainStyledAttributes.getBoolean(279, false);
                View view = this.mView;
                if (view != null) {
                    Drawable background = view.getBackground();
                    if (background instanceof ColorDrawable) {
                        r3 = 1 ^ (ContrastColorUtil.isColorDarkLab(((ColorDrawable) background).getColor()) ? 1 : 0);
                    }
                }
                if (!z || r3 == 0) {
                    return 0;
                }
                obtainStyledAttributes.recycle();
                return 2;
            }
            int i = getNightMode() == 32 ? 1 : 0;
            if (i != 0) {
                i = (obtainStyledAttributes.getBoolean(279, true) && obtainStyledAttributes.getBoolean(278, SystemProperties.getBoolean(ThreadedRenderer.DEBUG_FORCE_DARK, false))) ? 1 : 0;
            }
            return i;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    private boolean shouldApplyForceInvertDark() {
        UiModeManager uiModeManager = (UiModeManager) this.mContext.getSystemService(UiModeManager.class);
        return uiModeManager != null && uiModeManager.getForceInvertState() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForceDarkMode() {
        if (this.mAttachInfo.mThreadedRenderer != null && this.mAttachInfo.mThreadedRenderer.setForceDark(determineForceDarkType())) {
            if (android.view.accessibility.Flags.forceInvertColor()) {
                destroyAndInvalidate();
            } else {
                invalidateWorld(this.mView);
            }
        }
    }

    public View getView() {
        return this.mView;
    }

    final WindowLeaked getLocation() {
        return this.mLocation;
    }

    public void setLayoutParams(WindowManager.LayoutParams layoutParams, boolean z) {
        synchronized (this) {
            int i = this.mWindowAttributes.surfaceInsets.left;
            int i2 = this.mWindowAttributes.surfaceInsets.top;
            int i3 = this.mWindowAttributes.surfaceInsets.right;
            int i4 = this.mWindowAttributes.surfaceInsets.bottom;
            int i5 = this.mWindowAttributes.softInputMode;
            boolean z2 = this.mWindowAttributes.hasManualSurfaceInsets;
            if (DEBUG_KEEP_SCREEN_ON && (this.mClientWindowLayoutFlags & 128) != 0 && (layoutParams.flags & 128) == 0) {
                Slog.d(this.mTag, "setLayoutParams: FLAG_KEEP_SCREEN_ON from true to false!");
            }
            if ((this.mWindowAttributes.flags & 512) != 0 && (layoutParams.flags & 512) == 0) {
                Log.i(this.mTag, "setLayoutParams: set mApplyInsetsRequested = true");
                this.mApplyInsetsRequested = true;
            }
            if (this.mWindowAttributes.type != layoutParams.type) {
                Slog.e(this.mTag, "Window type can not be changed after the window is added. old=" + this.mWindowAttributes.type + ", new=" + layoutParams.type + ", Callers=" + Debug.getCallers(10));
            }
            this.mClientWindowLayoutFlags = layoutParams.flags;
            int i6 = this.mWindowAttributes.systemUiVisibility;
            int i7 = this.mWindowAttributes.subtreeSystemUiVisibility;
            int i8 = this.mWindowAttributes.insetsFlags.appearance;
            int i9 = this.mWindowAttributes.insetsFlags.behavior;
            int adjustLayoutInDisplayCutoutMode = adjustLayoutInDisplayCutoutMode(layoutParams);
            int copyFrom = this.mWindowAttributes.copyFrom(layoutParams);
            if ((524288 & copyFrom) != 0) {
                this.mAttachInfo.mRecomputeGlobalAttributes = true;
            }
            if ((copyFrom & 1) != 0) {
                this.mAttachInfo.mNeedsUpdateLightCenter = true;
            }
            if ((copyFrom & 67108864) != 0) {
                invalidate();
            }
            if (this.mWindowAttributes.packageName == null) {
                this.mWindowAttributes.packageName = this.mBasePackageName;
            }
            layoutParams.layoutInDisplayCutoutMode = adjustLayoutInDisplayCutoutMode;
            this.mWindowAttributes.systemUiVisibility = i6;
            this.mWindowAttributes.subtreeSystemUiVisibility = i7;
            this.mWindowAttributes.insetsFlags.appearance = i8;
            this.mWindowAttributes.insetsFlags.behavior = i9;
            if (this.mWindowAttributes.preservePreviousSurfaceInsets) {
                this.mWindowAttributes.surfaceInsets.set(i, i2, i3, i4);
                this.mWindowAttributes.hasManualSurfaceInsets = z2;
            } else if (this.mWindowAttributes.surfaceInsets.left != i || this.mWindowAttributes.surfaceInsets.top != i2 || this.mWindowAttributes.surfaceInsets.right != i3 || this.mWindowAttributes.surfaceInsets.bottom != i4) {
                this.mNeedsRendererSetup = true;
            }
            applyKeepScreenOnFlag(this.mWindowAttributes);
            if (z) {
                this.mSoftInputMode = layoutParams.softInputMode;
                requestLayout();
            }
            if ((layoutParams.softInputMode & 240) == 0) {
                WindowManager.LayoutParams layoutParams2 = this.mWindowAttributes;
                layoutParams2.softInputMode = (layoutParams2.softInputMode & (-241)) | (i5 & 240);
            }
            if (this.mWindowAttributes.softInputMode != i5) {
                requestFitSystemWindows();
            }
            if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
                Log.i(this.mTag, "Traversal, [1] mView=" + this.mView);
            }
            this.mWindowAttributesChanged = true;
            scheduleTraversals();
            setAccessibilityWindowAttributesIfNeeded();
        }
    }

    private int adjustLayoutInDisplayCutoutMode(WindowManager.LayoutParams layoutParams) {
        int i = layoutParams.layoutInDisplayCutoutMode;
        if ((layoutParams.privateFlags & 264192) != 0 && layoutParams.isFullscreen() && layoutParams.getFitInsetsTypes() == 0 && layoutParams.getFitInsetsSides() == 0 && i != 3) {
            layoutParams.layoutInDisplayCutoutMode = 3;
        }
        return i;
    }

    private boolean isImpossibleRenderer() {
        return this.mSemEarlyAppVisibilityChanged && this.mAppVisible && this.mStopped && !this.mSemEarlyAppVisibility;
    }

    void handleAppVisibility(boolean z) {
        if (Trace.isTagEnabled(8L)) {
            Trace.instant(8L, TextUtils.formatSimple("%s visibilityChanged oldVisibility=%b newVisibility=%b", this.mTag, Boolean.valueOf(this.mAppVisible), Boolean.valueOf(z)));
        }
        this.mSemEarlyAppVisibilityChanged = false;
        Log.i(this.mTag, "handleAppVisibility mAppVisible = " + this.mAppVisible + " visible = " + z);
        if (this.mAppVisible != z) {
            boolean z2 = getHostVisibility() == 0;
            this.mAppVisible = z;
            boolean z3 = getHostVisibility() == 0;
            boolean z4 = DEBUG_TRAVERSAL;
            if (z4 && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
                Log.i(this.mTag, "Traversal, [2] mView=" + this.mView + " visible=" + z + " previousVisible=" + z2 + " currentVisible=" + z3);
            }
            if (z2 != z3) {
                Log.d(this.mTag, "visibilityChanged oldVisibility=" + z2 + " newVisibility=" + z3);
                this.mAppVisibilityChanged = true;
                scheduleTraversals();
            }
            if (!this.mRemoved || !this.mAppVisible || !this.mIsDetached) {
                AnimationHandler.requestAnimatorsEnabled(this.mAppVisible, this);
                return;
            }
            Log.v(this.mTag, "handleAppVisibility() enabling visibility when removed");
            if (z4 && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
                Log.i(this.mTag, "Traversal, [2] mView=" + this.mView + " mAppVisible=" + this.mAppVisible + " visible=" + z);
            }
        }
    }

    void handleGetNewSurface() {
        this.mNewSurfaceNeeded = true;
        this.mFullRedrawNeeded = true;
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [3]  mView=" + this.mView);
        }
        scheduleTraversals();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleResized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4, ActivityWindowInfo activityWindowInfo) {
        Rect rect;
        boolean z5;
        if (this.mAdded) {
            onClientWindowFramesChanged(clientWindowFrames);
            CompatibilityInfo.applyOverrideIfNeeded(mergedConfiguration);
            Rect rect2 = clientWindowFrames.frame;
            Rect rect3 = clientWindowFrames.displayFrame;
            Rect rect4 = clientWindowFrames.attachedFrame;
            CompatibilityInfo.Translator translator = this.mTranslator;
            if (translator != null) {
                translator.translateRectInScreenToAppWindow(rect2);
                this.mTranslator.translateRectInScreenToAppWindow(rect3);
                this.mTranslator.translateRectInScreenToAppWindow(rect4);
            }
            onInsetsStateChanged(insetsState);
            float f = clientWindowFrames.compatScale;
            boolean equals = this.mWinFrame.equals(rect2);
            boolean z6 = !equals;
            ActivityWindowInfo activityWindowInfo2 = this.mLastReportedActivityWindowInfo;
            boolean z7 = !this.mLastReportedMergedConfiguration.equals(mergedConfiguration) || (activityWindowInfo2 != null && activityWindowInfo != null && !activityWindowInfo2.equals(activityWindowInfo));
            boolean equals2 = Objects.equals(this.mTmpFrames.attachedFrame, rect4);
            boolean z8 = !equals2;
            boolean z9 = this.mDisplay.getDisplayId() != i;
            boolean z10 = this.mTmpFrames.compatScale != f;
            boolean z11 = this.mPendingDragResizing != z4;
            if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT) {
                rect = rect4;
                if (i == 1 && this.mIsCutoutRemoveForDispatchNeeded) {
                    z2 = true;
                }
            } else {
                rect = rect4;
            }
            Log.i(this.mTag, "handleResized, frames=" + clientWindowFrames + " displayId=" + i + " dragResizing=" + z4 + " compatScale=" + f + " frameChanged=" + z6 + " attachedFrameChanged=" + z8 + " configChanged=" + z7 + " displayChanged=" + z9 + " compatScaleChanged=" + z10 + " dragResizingChanged=" + z11);
            if (z || !equals || z7 || !equals2 || z9 || z2 || z10 || z11) {
                this.mPendingDragResizing = z4;
                this.mTmpFrames.compatScale = f;
                this.mInvCompatScale = 1.0f / f;
                if (z7) {
                    z5 = false;
                    performConfigurationChange(mergedConfiguration, false, z9 ? i : -1, activityWindowInfo);
                } else {
                    z5 = false;
                    if (z9) {
                        onMovedToDisplay(i, this.mLastConfigurationFromResources);
                    }
                }
                setFrame(rect2, z5);
                this.mTmpFrames.displayFrame.set(rect3);
                if (this.mTmpFrames.attachedFrame != null && rect != null) {
                    this.mTmpFrames.attachedFrame.set(rect);
                }
                if (this.mDragResizing && this.mUseMTRenderer) {
                    boolean equals3 = rect2.equals(this.mPendingBackDropFrame);
                    for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
                        this.mWindowCallbacks.get(size).onWindowSizeIsChanging(this.mPendingBackDropFrame, equals3, this.mAttachInfo.mVisibleInsets, this.mAttachInfo.mStableInsets);
                    }
                }
                this.mForceNextWindowRelayout |= z2;
                this.mPendingAlwaysConsumeSystemBars = z3;
                int i3 = this.mSyncSeqId;
                if (i2 > i3) {
                    i3 = i2;
                }
                this.mSyncSeqId = i3;
                Log.i(this.mTag, "handleResized mSyncSeqId = " + this.mSyncSeqId);
                if (z) {
                    reportNextDraw("resized");
                }
                View view = this.mView;
                if (view != null && (!equals || z7)) {
                    forceLayout(view);
                }
                requestLayout();
            }
        }
    }

    public void handleInsetsControlChanged(InsetsState insetsState, InsetsSourceControl.Array array) {
        onInsetsStateChanged(insetsState);
        onActiveControlsChanged(array);
    }

    private void onClientWindowFramesChanged(ClientWindowFrames clientWindowFrames) {
        if (SequenceUtils.isIncomingSeqStale(this.mLastReportedFrames.seq, clientWindowFrames.seq)) {
            clientWindowFrames.setTo(this.mLastReportedFrames);
        } else {
            this.mLastReportedFrames.setTo(clientWindowFrames);
        }
    }

    private void onInsetsStateChanged(InsetsState insetsState) {
        if (SequenceUtils.isIncomingSeqStale(this.mLastReportedInsetsStateSeq, insetsState.getSeq())) {
            return;
        }
        this.mLastReportedInsetsStateSeq = insetsState.getSeq();
        CompatibilityInfo.Translator translator = this.mTranslator;
        if (translator != null) {
            translator.translateInsetsStateInScreenToAppWindow(insetsState);
        }
        this.mInsetsController.onStateChanged(insetsState);
    }

    private void onActiveControlsChanged(InsetsSourceControl.Array array) {
        if (CoreRune.FW_TEMP_INSETS_BUG_FIX && this.mWindowAttributes.type == 3) {
            Log.i(this.mTag, "onActiveControlsChanged, removed=" + this.mRemoved + ", mAdded=" + this.mAdded + ", caller=" + Debug.getCallers(5));
        }
        if (!this.mAdded) {
            array.release();
            return;
        }
        if (SequenceUtils.isIncomingSeqStale(this.mLastReportedActiveControlsSeq, array.getSeq())) {
            array.release();
            return;
        }
        this.mLastReportedActiveControlsSeq = array.getSeq();
        InsetsSourceControl[] insetsSourceControlArr = array.get();
        CompatibilityInfo.Translator translator = this.mTranslator;
        if (translator != null) {
            translator.translateSourceControlsInScreenToAppWindow(insetsSourceControlArr);
        }
        this.mInsetsController.onControlsChanged(insetsSourceControlArr);
    }

    public void onMovedToDisplay(int i, Configuration configuration) {
        View view;
        if (this.mDisplay.getDisplayId() == i || (view = this.mView) == null) {
            return;
        }
        updateInternalDisplay(i, view.getResources());
        this.mImeFocusController.onMovedToDisplay();
        this.mAttachInfo.mDisplayState = this.mDisplay.getState();
        updateDesktopMode();
        this.mView.dispatchMovedToDisplay(this.mDisplay, configuration);
    }

    private void updateInternalDisplay(int i, Resources resources) {
        Display adjustedDisplay = ResourcesManager.getInstance().getAdjustedDisplay(i, resources);
        this.mDisplay.getDisplayId();
        this.mHdrRenderState.stopListening();
        if (adjustedDisplay == null) {
            Slog.w(TAG, "Cannot get desired display with Id: " + i);
            this.mDisplay = ResourcesManager.getInstance().getAdjustedDisplay(0, resources);
        } else {
            this.mDisplay = adjustedDisplay;
        }
        this.mHdrRenderState.startListening();
        this.mContext.updateDisplay(this.mDisplay.getDisplayId());
    }

    void pokeDrawLockIfNeeded() {
        if (!this.mDisableDrawWakeLock && this.mAttachInfo.mDisplayState == 3 && this.mWindowAttributes.type == 1 && this.mAdded && this.mTraversalScheduled && this.mAttachInfo.mHasWindowFocus) {
            try {
                this.mWindowSession.pokeDrawLock(this.mWindow);
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // android.view.ViewParent
    public void requestFitSystemWindows() {
        checkThread();
        this.mApplyInsetsRequested = true;
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [5] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    void notifyInsetsChanged() {
        InsetsSource peekSource = this.mInsetsController.getState().peekSource(InsetsSource.ID_IME);
        if (getConfiguration().windowConfiguration.getWindowingMode() != 5 || peekSource == null || !peekSource.isVisible() || peekSource.getFrame().isEmpty() || peekSource.getFrame().top >= this.mWinFrame.top) {
            this.mApplyInsetsRequested = true;
            requestLayout();
            if (View.sForceLayoutWhenInsetsChanged && this.mView != null && (this.mWindowAttributes.softInputMode & 240) == 16) {
                forceLayout(this.mView);
            }
            if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
                Log.i(this.mTag, "Traversal, [6] mView=" + this.mView + " mIsInTraversal=" + this.mIsInTraversal);
            }
            if (this.mIsInTraversal) {
                return;
            }
            scheduleTraversals();
        }
    }

    public void updateAnimatingTypes(int i, ImeTracker.Token token) {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            boolean z = i != 0;
            if (Trace.isTagEnabled(8L)) {
                Trace.instant(8L, TextUtils.formatSimple("notifyInsetsAnimationRunningStateChanged(%s)", Boolean.toString(z)));
            }
            this.mInsetsAnimationRunning = z;
            try {
                this.mWindowSession.updateAnimatingTypes(this.mWindow, i, token);
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // android.view.ViewParent
    public void requestLayout() {
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [7] mView=" + this.mView + " mHandlingLayoutInLayoutRequest=" + this.mHandlingLayoutInLayoutRequest);
        }
        if (this.mHandlingLayoutInLayoutRequest) {
            return;
        }
        checkThread();
        this.mLayoutRequested = true;
        scheduleTraversals();
    }

    @Override // android.view.ViewParent
    public boolean isLayoutRequested() {
        return this.mLayoutRequested;
    }

    @Override // android.view.ViewParent
    public void onDescendantInvalidated(View view, View view2) {
        if (sToolkitEnableInvalidateCheckThreadFlagValue) {
            checkThread();
        }
        if ((view2.mPrivateFlags & 64) != 0) {
            this.mIsAnimating = true;
        }
        invalidate();
    }

    void invalidate() {
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [8] mView=" + this.mView + " mWillDrawSoon=" + this.mWillDrawSoon);
        }
        this.mDirty.set(0, 0, this.mWidth, this.mHeight);
        if (this.mWillDrawSoon) {
            return;
        }
        scheduleTraversals();
    }

    void invalidateWorld(View view) {
        view.invalidate();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                invalidateWorld(viewGroup.getChildAt(i));
            }
        }
    }

    @Override // android.view.ViewParent
    public void invalidateChild(View view, Rect rect) {
        invalidateChildInParent(null, rect);
    }

    @Override // android.view.ViewParent
    public ViewParent invalidateChildInParent(int[] iArr, Rect rect) {
        checkThread();
        if (DEBUG_DRAW) {
            Log.v(this.mTag, "Invalidate child: " + rect);
        }
        if (rect == null) {
            invalidate();
            return null;
        }
        if (rect.isEmpty() && !this.mIsAnimating) {
            return null;
        }
        if (this.mCurScrollY != 0 || this.mTranslator != null) {
            this.mTempRect.set(rect);
            rect = this.mTempRect;
            int i = this.mCurScrollY;
            if (i != 0) {
                rect.offset(0, -i);
            }
            CompatibilityInfo.Translator translator = this.mTranslator;
            if (translator != null) {
                translator.translateRectInAppWindowToScreen(rect);
            }
            if (this.mAttachInfo.mScalingRequired) {
                rect.inset(-1, -1);
            }
        }
        invalidateRectOnScreen(rect);
        return null;
    }

    private void invalidateRectOnScreen(Rect rect) {
        if (DEBUG_DRAW) {
            Log.v(this.mTag, "invalidateRectOnScreen: " + rect);
        }
        Rect rect2 = this.mDirty;
        rect2.union(rect.left, rect.top, rect.right, rect.bottom);
        float f = this.mAttachInfo.mApplicationScale;
        boolean intersect = rect2.intersect(0, 0, (int) ((this.mWidth * f) + 0.5f), (int) ((this.mHeight * f) + 0.5f));
        if (!intersect) {
            rect2.setEmpty();
        }
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [9] mView=" + this.mView + " mWillDrawSoon=" + this.mWillDrawSoon + " intersected=" + intersect + " mIsAnimating=" + this.mIsAnimating);
        }
        if (this.mWillDrawSoon) {
            return;
        }
        if (intersect || this.mIsAnimating) {
            scheduleTraversals();
        }
    }

    public void setIsAmbientMode(boolean z) {
        this.mIsAmbientMode = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setWindowStopped(boolean z) {
        Log.i(this.mTag, "stopped(" + z + ") old = " + this.mStopped);
        checkThread();
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [10] mView=" + this.mView + " mStopped=" + this.mStopped + " stopped=" + z);
        }
        if (this.mStopped != z) {
            this.mStopped = z;
            ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
            if (threadedRenderer != null) {
                Log.d(this.mTag, "WindowStopped on " + ((Object) getTitle()) + " set to " + this.mStopped);
                threadedRenderer.setStopped(this.mStopped);
            }
            if (!this.mStopped) {
                this.mAppVisibilityChanged = true;
                scheduleTraversals();
            } else {
                if (threadedRenderer != null) {
                    threadedRenderer.destroyHardwareResources(this.mView);
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
        logColorMode(this.mCurrentColorMode, true);
    }

    public void addSurfaceChangedCallback(SurfaceChangedCallback surfaceChangedCallback) {
        this.mSurfaceChangedCallbacks.add(surfaceChangedCallback);
    }

    public void removeSurfaceChangedCallback(SurfaceChangedCallback surfaceChangedCallback) {
        this.mSurfaceChangedCallbacks.remove(surfaceChangedCallback);
    }

    private void notifySurfaceCreated(SurfaceControl.Transaction transaction) {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).surfaceCreated(transaction);
        }
    }

    private void notifySurfaceReplaced(SurfaceControl.Transaction transaction) {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).surfaceReplaced(transaction);
        }
    }

    private void notifySurfaceDestroyed() {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).surfaceDestroyed();
        }
    }

    private void notifyDrawStarted(boolean z) {
        for (int i = 0; i < this.mSurfaceChangedCallbacks.size(); i++) {
            this.mSurfaceChangedCallbacks.get(i).vriDrawStarted(z);
        }
    }

    public SurfaceControl updateAndGetBoundsLayer(SurfaceControl.Transaction transaction) {
        if (this.mBoundsLayer == null) {
            boolean z = this.mContext.getResources().getConfiguration().windowConfiguration.getWindowingMode() == 5 && this.mWindowAttributes.type == 1 && this.mWindowAttributes.isFullscreen();
            this.mBoundsLayer = new SurfaceControl.Builder().setContainerLayer().setName("Bounds for - " + getTitle().toString() + "@" + this.mBoundsLayerCreatedCount).setParent(getSurfaceControl()).setColorLayer().setCallsite("ViewRootImpl.getBoundsLayer").build();
            setBoundsLayerCrop(transaction);
            if (!z) {
                transaction.unsetColor(this.mBoundsLayer);
                this.mIsBoundsColorLayer = false;
            } else {
                transaction.setColor(this.mBoundsLayer, new float[]{0.0f, 0.0f, 0.0f});
                transaction.setLayer(this.mBoundsLayer, -3);
                this.mIsBoundsColorLayer = true;
            }
            transaction.show(this.mBoundsLayer);
            this.mBoundsLayerCreatedCount++;
        }
        return this.mBoundsLayer;
    }

    void updateBlastSurfaceIfNeeded() {
        Surface createSurface;
        if (this.mSurfaceControl.isValid()) {
            BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
            if (bLASTBufferQueue != null && bLASTBufferQueue.isSameSurfaceControl(this.mSurfaceControl)) {
                this.mBlastBufferQueue.update(this.mSurfaceControl, this.mSurfaceSize.x, this.mSurfaceSize.y, this.mWindowAttributes.format);
                return;
            }
            BLASTBufferQueue bLASTBufferQueue2 = this.mBlastBufferQueue;
            if (bLASTBufferQueue2 != null) {
                bLASTBufferQueue2.destroy();
            }
            BLASTBufferQueue bLASTBufferQueue3 = new BLASTBufferQueue(this.mTag, true);
            this.mBlastBufferQueue = bLASTBufferQueue3;
            bLASTBufferQueue3.setApplyToken(this.mBbqApplyToken);
            this.mBlastBufferQueue.update(this.mSurfaceControl, this.mSurfaceSize.x, this.mSurfaceSize.y, this.mWindowAttributes.format);
            this.mBlastBufferQueue.setTransactionHangCallback(sTransactionHangCallback);
            BLASTBufferQueue bLASTBufferQueue4 = this.mBlastBufferQueue;
            final Choreographer choreographer = this.mChoreographer;
            Objects.requireNonNull(choreographer);
            bLASTBufferQueue4.setWaitForBufferReleaseCallback(new BLASTBufferQueue.WaitForBufferReleaseCallback() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda17
                @Override // android.graphics.BLASTBufferQueue.WaitForBufferReleaseCallback
                public final void onWaitForBufferRelease(long j) {
                    Choreographer.this.onWaitForBufferRelease(j);
                }
            });
            if (Flags.addSchandleToVriSurface()) {
                createSurface = this.mBlastBufferQueue.createSurfaceWithHandle();
            } else {
                createSurface = this.mBlastBufferQueue.createSurface();
            }
            this.mSurface.transferFrom(createSurface);
            this.mTransaction.setRecoverableFromBufferStuffing(this.mSurfaceControl).applyAsyncUnsafe();
        }
    }

    private void setBoundsLayerCrop(SurfaceControl.Transaction transaction) {
        if (this.mWinFrame.isEmpty()) {
            Log.i(this.mTag, "setBoundsLayerCrop, frame is empty");
            return;
        }
        this.mTempRect.set(0, 0, this.mSurfaceSize.x, this.mSurfaceSize.y);
        this.mTempRect.inset(this.mWindowAttributes.surfaceInsets.left, this.mWindowAttributes.surfaceInsets.top, this.mWindowAttributes.surfaceInsets.right, this.mWindowAttributes.surfaceInsets.bottom);
        this.mTempRect.inset(this.mChildBoundingInsets.left, this.mChildBoundingInsets.top, this.mChildBoundingInsets.right, this.mChildBoundingInsets.bottom);
        transaction.setWindowCrop(this.mBoundsLayer, this.mTempRect);
    }

    private boolean updateBoundsLayer(SurfaceControl.Transaction transaction) {
        if (this.mBoundsLayer == null) {
            return false;
        }
        setBoundsLayerCrop(transaction);
        Log.i(this.mTag, "updateBoundsLayer: t=" + transaction + " sc=" + this.mBoundsLayer + " frame=" + this.mSurface.getNextFrameNumber());
        if (this.mAttachInfo.mDisplayState == 1) {
            this.mDeferTransactionRequested = true;
            Log.i(this.mTag, "updateBoundsLayer: set mDeferTransactionRequested=" + this.mDeferTransactionRequested);
        }
        boolean z = this.mContext.getResources().getConfiguration().windowConfiguration.getWindowingMode() == 5;
        if (this.mWindowAttributes.type == 1 && this.mWindowAttributes.isFullscreen()) {
            if (!this.mIsWindowOpaque && z) {
                transaction.setLayer(this.mBoundsLayer, 0);
                transaction.unsetColor(this.mBoundsLayer);
                this.mIsBoundsColorLayer = false;
            } else {
                boolean z2 = this.mIsBoundsColorLayer;
                if (!z2 && z) {
                    transaction.setLayer(this.mBoundsLayer, -3);
                    transaction.setColor(this.mBoundsLayer, new float[]{0.0f, 0.0f, 0.0f});
                    this.mIsBoundsColorLayer = true;
                } else if (z2 && !z) {
                    transaction.setLayer(this.mBoundsLayer, 0);
                    transaction.unsetColor(this.mBoundsLayer);
                    this.mIsBoundsColorLayer = false;
                }
            }
        }
        this.mFullRedrawNeeded = true;
        return true;
    }

    private void prepareSurfaces() {
        SurfaceControl.Transaction transaction = this.mTransaction;
        SurfaceControl surfaceControl = getSurfaceControl();
        if (surfaceControl.isValid()) {
            if (updateBoundsLayer(transaction)) {
                applyTransactionOnDraw(transaction);
            }
            if (shouldEnableDvrr()) {
                try {
                    if (sToolkitFrameRateFunctionEnablingReadOnlyFlagValue) {
                        this.mFrameRateTransaction.setFrameRateSelectionStrategy(surfaceControl, 2).applyAsyncUnsafe();
                    }
                } catch (Exception e) {
                    Log.e(this.mTag, "Unable to set frame rate selection strategy ", e);
                }
            }
            if (CoreRune.FW_VRR_SEND_TOUCH_HINT) {
                try {
                    this.mTouchHintTransaction.setFrameRateSelectionStrategy(surfaceControl, 2).applyAsyncUnsafe();
                } catch (Exception e2) {
                    Log.e(this.mTag, "Unable to set frame rate selection strategy ", e2);
                }
            }
        }
    }

    private void destroySurface() {
        if (this.mBoundsLayer != null) {
            new SurfaceControl.Transaction().remove(this.mBoundsLayer).apply();
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
        this.mPreferredFrameRateCategory = 0;
        this.mLastPreferredFrameRateCategory = 0;
        this.mPreferredFrameRate = 0.0f;
        this.mLastPreferredFrameRate = 0.0f;
    }

    public void setPausedForTransition(boolean z) {
        this.mPausedForTransition = z;
    }

    @Override // android.view.ViewParent
    public boolean getChildVisibleRect(View view, Rect rect, Point point) {
        if (view != this.mView) {
            throw new RuntimeException("child is not mine, honest!");
        }
        return rect.intersect(0, 0, this.mWidth, this.mHeight);
    }

    @Override // android.view.ViewParent
    public boolean getChildLocalHitRegion(View view, Region region, Matrix matrix, boolean z) {
        if (view != this.mView) {
            throw new IllegalArgumentException("child " + view + " is not the root view " + this.mView + " managed by this ViewRootImpl");
        }
        RectF rectF = new RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
        matrix.mapRect(rectF);
        return region.op(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom), Region.Op.INTERSECT);
    }

    int getHostVisibility() {
        View view = this.mView;
        if (view == null) {
            return 8;
        }
        if (this.mAppVisible || this.mForceDecorViewVisibility) {
            return view.getVisibility();
        }
        return 8;
    }

    String getHostVisibilityReason() {
        View view = this.mView;
        if (view == null) {
            return "mView is null";
        }
        if (!this.mAppVisible && !this.mForceDecorViewVisibility) {
            return "!mAppVisible && !mForceDecorViewVisibility";
        }
        int visibility = view.getVisibility();
        if (visibility == 0) {
            return "View.VISIBLE";
        }
        if (visibility == 4) {
            return "View.INVISIBLE";
        }
        if (visibility == 8) {
            return "View.GONE";
        }
        return "";
    }

    public void requestTransitionStart(LayoutTransition layoutTransition) {
        ArrayList<LayoutTransition> arrayList = this.mPendingTransitions;
        if (arrayList == null || !arrayList.contains(layoutTransition)) {
            if (this.mPendingTransitions == null) {
                this.mPendingTransitions = new ArrayList<>();
            }
            this.mPendingTransitions.add(layoutTransition);
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

    public void notifyRendererOfExpensiveFrame(String str) {
        Trace.traceBegin(8L, str);
        try {
            notifyRendererOfExpensiveFrame();
        } finally {
            Trace.traceEnd(8L);
        }
    }

    void scheduleTraversals() {
        if (sSafeScheduleTraversals) {
            checkThread();
        }
        if (this.mTraversalScheduled) {
            return;
        }
        this.mTraversalScheduled = true;
        this.mTraversalBarrier = this.mHandler.getLooper().getQueue().postSyncBarrier();
        this.mChoreographer.postCallback(3, this.mTraversalRunnable, null);
        notifyRendererOfFramePending();
        pokeDrawLockIfNeeded();
    }

    void unscheduleTraversals() {
        if (sSafeScheduleTraversals) {
            checkThread();
        }
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

    private void applyKeepScreenOnFlag(WindowManager.LayoutParams layoutParams) {
        if (this.mAttachInfo.mKeepScreenOn) {
            layoutParams.flags |= 128;
        } else {
            layoutParams.flags = (this.mClientWindowLayoutFlags & 128) | (layoutParams.flags & PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE);
        }
    }

    private boolean collectViewAttributes() {
        if (this.mAttachInfo.mRecomputeGlobalAttributes) {
            this.mAttachInfo.mRecomputeGlobalAttributes = false;
            boolean z = this.mAttachInfo.mKeepScreenOn;
            this.mAttachInfo.mKeepScreenOn = false;
            this.mAttachInfo.mSystemUiVisibility = 0;
            this.mAttachInfo.mHasSystemUiListeners = false;
            this.mView.dispatchCollectViewAttributes(this.mAttachInfo, 0);
            this.mAttachInfo.mSystemUiVisibility &= ~this.mAttachInfo.mDisabledSystemUiVisibility;
            WindowManager.LayoutParams layoutParams = this.mWindowAttributes;
            this.mAttachInfo.mSystemUiVisibility |= getImpliedSystemUiVisibility(layoutParams);
            SystemUiVisibilityInfo systemUiVisibilityInfo = this.mCompatibleVisibilityInfo;
            systemUiVisibilityInfo.globalVisibility = (systemUiVisibilityInfo.globalVisibility & (-2)) | (this.mAttachInfo.mSystemUiVisibility & 1);
            dispatchDispatchSystemUiVisibilityChanged();
            if (this.mAttachInfo.mKeepScreenOn != z || this.mAttachInfo.mSystemUiVisibility != layoutParams.subtreeSystemUiVisibility || this.mAttachInfo.mHasSystemUiListeners != layoutParams.hasSystemUiListeners) {
                applyKeepScreenOnFlag(layoutParams);
                layoutParams.subtreeSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                layoutParams.hasSystemUiListeners = this.mAttachInfo.mHasSystemUiListeners;
                this.mView.dispatchWindowSystemUiVisiblityChanged(this.mAttachInfo.mSystemUiVisibility);
                return true;
            }
        }
        return false;
    }

    private int getImpliedSystemUiVisibility(WindowManager.LayoutParams layoutParams) {
        int i = (layoutParams.flags & 67108864) != 0 ? 1280 : 0;
        return (layoutParams.flags & 134217728) != 0 ? i | 768 : i;
    }

    void updateCompatSysUiVisibility(int i, int i2, int i3) {
        int i4 = (i & (~i3)) | (i2 & i3);
        updateCompatSystemUiVisibilityInfo(4, WindowInsets.Type.statusBars(), i4, i3);
        updateCompatSystemUiVisibilityInfo(2, WindowInsets.Type.navigationBars(), i4, i3);
        dispatchDispatchSystemUiVisibilityChanged();
    }

    private void updateCompatSystemUiVisibilityInfo(int i, int i2, int i3, int i4) {
        SystemUiVisibilityInfo systemUiVisibilityInfo = this.mCompatibleVisibilityInfo;
        boolean z = (i3 & i2) != 0;
        boolean z2 = (i2 & i4) != 0;
        boolean z3 = (this.mAttachInfo.mSystemUiVisibility & i) != 0;
        if (z) {
            systemUiVisibilityInfo.globalVisibility &= ~i;
            if (z2 && z3) {
                systemUiVisibilityInfo.localChanges |= i;
                return;
            }
            return;
        }
        systemUiVisibilityInfo.globalVisibility |= i;
        systemUiVisibilityInfo.localChanges &= ~i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearLowProfileModeIfNeeded(int i, boolean z) {
        SystemUiVisibilityInfo systemUiVisibilityInfo = this.mCompatibleVisibilityInfo;
        if ((i & WindowInsets.Type.systemBars()) == 0 || z || (systemUiVisibilityInfo.globalVisibility & 1) == 0) {
            return;
        }
        systemUiVisibilityInfo.globalVisibility &= -2;
        systemUiVisibilityInfo.localChanges |= 1;
        dispatchDispatchSystemUiVisibilityChanged();
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
        SystemUiVisibilityInfo systemUiVisibilityInfo = this.mCompatibleVisibilityInfo;
        if (systemUiVisibilityInfo.localChanges != 0) {
            this.mView.updateLocalSystemUiVisibility(systemUiVisibilityInfo.localValue, systemUiVisibilityInfo.localChanges);
            systemUiVisibilityInfo.localChanges = 0;
        }
        int i = systemUiVisibilityInfo.globalVisibility & 7;
        if (this.mDispatchedSystemUiVisibility != i) {
            this.mDispatchedSystemUiVisibility = i;
            this.mView.dispatchSystemUiVisibilityChanged(i);
        }
    }

    public static void adjustLayoutParamsForCompatibility(WindowManager.LayoutParams layoutParams, int i, boolean z) {
        int i2 = layoutParams.systemUiVisibility | layoutParams.subtreeSystemUiVisibility;
        int i3 = layoutParams.flags;
        int i4 = layoutParams.type;
        int i5 = layoutParams.softInputMode & 240;
        int i6 = layoutParams.insetsFlags.appearance;
        if ((i & 4) == 0) {
            i6 = (i6 & (-5)) | ((i2 & 1) != 0 ? 4 : 0);
        }
        if ((i & 8) == 0) {
            i6 = (i6 & (-9)) | ((i2 & 8192) != 0 ? 8 : 0);
        }
        if ((i & 16) == 0) {
            i6 = (i6 & (-17)) | ((i2 & 16) != 0 ? 16 : 0);
        }
        layoutParams.insetsFlags.appearance = i6;
        boolean z2 = true;
        if (!z) {
            if ((i2 & 4096) != 0 || (i3 & 1024) != 0) {
                layoutParams.insetsFlags.behavior = 2;
            } else {
                layoutParams.insetsFlags.behavior = 1;
            }
        }
        layoutParams.privateFlags &= -1073741825;
        if ((layoutParams.privateFlags & 268435456) != 0) {
            return;
        }
        int fitInsetsTypes = layoutParams.getFitInsetsTypes();
        boolean isFitInsetsIgnoringVisibility = layoutParams.isFitInsetsIgnoringVisibility();
        if ((i2 & 1024) != 0 || (i3 & 256) != 0 || (67108864 & i3) != 0) {
            fitInsetsTypes &= ~WindowInsets.Type.statusBars();
        }
        if ((i2 & 512) != 0 || (134217728 & i3) != 0) {
            fitInsetsTypes &= ~WindowInsets.Type.systemBars();
        }
        if (i4 != 2005 && i4 != 2003) {
            if ((WindowInsets.Type.systemBars() & fitInsetsTypes) == WindowInsets.Type.systemBars()) {
                if (i5 == 16) {
                    fitInsetsTypes |= WindowInsets.Type.ime();
                } else {
                    layoutParams.privateFlags |= 1073741824;
                }
            }
            z2 = isFitInsetsIgnoringVisibility;
        }
        layoutParams.setFitInsetsTypes(fitInsetsTypes);
        layoutParams.setFitInsetsIgnoringVisibility(z2);
        layoutParams.privateFlags &= -268435457;
    }

    public void controlInsetsForCompatibility(WindowManager.LayoutParams layoutParams) {
        int statusBars;
        int i = layoutParams.systemUiVisibility | layoutParams.subtreeSystemUiVisibility;
        int i2 = layoutParams.flags;
        boolean z = true;
        int i3 = 0;
        boolean z2 = layoutParams.width == -1 && layoutParams.height == -1;
        boolean z3 = layoutParams.type >= 1 && layoutParams.type <= 99;
        boolean z4 = (this.mTypesHiddenByFlags & WindowInsets.Type.statusBars()) != 0;
        int i4 = i & 4;
        boolean z5 = i4 != 0 || ((i2 & 1024) != 0 && z2 && z3);
        boolean z6 = (this.mTypesHiddenByFlags & WindowInsets.Type.navigationBars()) != 0;
        boolean z7 = (i & 2) != 0;
        boolean z8 = (this.mTypesHiddenByFlags & WindowInsets.Type.captionBar()) != 0;
        if (i4 == 0 && ((i2 & 1024) == 0 || !z2 || !z3)) {
            z = false;
        }
        if (z5 && !z4) {
            i3 = WindowInsets.Type.statusBars();
            statusBars = 0;
        } else {
            statusBars = (z5 || !z4) ? 0 : WindowInsets.Type.statusBars();
        }
        if (z7 && !z6) {
            i3 |= WindowInsets.Type.navigationBars();
        } else if (!z7 && z6) {
            statusBars |= WindowInsets.Type.navigationBars();
        }
        if (z && !z8 && DesktopModeFlags.ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION.isTrue()) {
            i3 |= WindowInsets.Type.captionBar();
        } else if (!z && z8 && DesktopModeFlags.ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION.isTrue()) {
            statusBars |= WindowInsets.Type.captionBar();
        }
        if (i3 != 0) {
            getInsetsController().hide(i3);
        }
        if (statusBars != 0) {
            getInsetsController().show(statusBars);
        }
        this.mTypesHiddenByFlags = (~statusBars) & (this.mTypesHiddenByFlags | i3);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x014d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0142  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean measureHierarchy(android.view.View r18, android.view.WindowManager.LayoutParams r19, android.content.res.Resources r20, int r21, int r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 492
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.measureHierarchy(android.view.View, android.view.WindowManager$LayoutParams, android.content.res.Resources, int, int, boolean):boolean");
    }

    private boolean setMeasuredRootSizeFromSpec(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (mode != 1073741824 || mode2 != 1073741824) {
            return false;
        }
        this.mMeasuredWidth = View.MeasureSpec.getSize(i);
        this.mMeasuredHeight = View.MeasureSpec.getSize(i2);
        return true;
    }

    void transformMatrixToGlobal(Matrix matrix) {
        matrix.preTranslate(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
    }

    void transformMatrixToLocal(Matrix matrix) {
        matrix.postTranslate(-this.mAttachInfo.mWindowLeft, -this.mAttachInfo.mWindowTop);
    }

    WindowInsets getWindowInsets(boolean z) {
        return getWindowInsets(z, false);
    }

    WindowInsets getWindowInsets(boolean z, boolean z2) {
        if (this.mLastWindowInsets == null || z) {
            Configuration configuration = getConfiguration();
            WindowInsets calculateInsets = this.mInsetsController.calculateInsets(configuration.isScreenRound(), this.mWindowAttributes.type, configuration.windowConfiguration.getActivityType(), this.mWindowAttributes.softInputMode, this.mWindowAttributes.flags, this.mWindowAttributes.systemUiVisibility | this.mWindowAttributes.subtreeSystemUiVisibility, needStatusbarInsets(configuration.windowConfiguration));
            this.mLastWindowInsets = calculateInsets;
            if (this.mIsCutoutRemoveNeeded || z2) {
                WindowInsets removeCutoutInsets = calculateInsets.removeCutoutInsets(this.mInsetsController.getState().mCanDispatchUdcCutout);
                if (this.mIsCutoutRemoveNeeded) {
                    this.mLastWindowInsets = removeCutoutInsets;
                } else {
                    this.mAttachInfo.mContentInsets.set(removeCutoutInsets.getSystemWindowInsets().toRect());
                    this.mAttachInfo.mStableInsets.set(removeCutoutInsets.getStableInsets().toRect());
                    this.mAttachInfo.mVisibleInsets.set(this.mInsetsController.calculateVisibleInsets(this.mWindowAttributes.type, configuration.windowConfiguration.getWindowingMode(), this.mWindowAttributes.softInputMode, this.mWindowAttributes.flags).toRect());
                    return removeCutoutInsets;
                }
            }
            this.mAttachInfo.mContentInsets.set(this.mLastWindowInsets.getSystemWindowInsets().toRect());
            this.mAttachInfo.mStableInsets.set(this.mLastWindowInsets.getStableInsets().toRect());
            this.mAttachInfo.mVisibleInsets.set(this.mInsetsController.calculateVisibleInsets(this.mWindowAttributes.type, configuration.windowConfiguration.getActivityType(), this.mWindowAttributes.softInputMode, this.mWindowAttributes.flags).toRect());
        }
        return this.mLastWindowInsets;
    }

    private boolean needStatusbarInsets(WindowConfiguration windowConfiguration) {
        if (!CoreRune.MW_EMBED_ACTIVITY) {
            return false;
        }
        if (windowConfiguration.getEmbedActivityMode() == 3 || windowConfiguration.getEmbedActivityMode() == 2) {
            return windowConfiguration.getStagePosition() == 8 || windowConfiguration.getStagePosition() == 32;
        }
        return false;
    }

    public void dispatchApplyInsets(View view) {
        Trace.traceBegin(8L, "dispatchApplyInsets");
        this.mApplyInsetsRequested = false;
        WindowInsets windowInsets = getWindowInsets(true, this.mIsCutoutRemoveForDispatchNeeded || (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED && getCompatWindowConfiguration().isSplitScreen()));
        if (!shouldDispatchCutout()) {
            windowInsets = windowInsets.consumeDisplayCutout(this.mInsetsController.getState().mCanDispatchUdcCutout);
        }
        if (CoreRune.MW_CAPTION_INSETS && (view instanceof DecorView) && ((DecorView) view).shouldConsumeCaptionInsets()) {
            windowInsets = windowInsets.consumeCaptionInsets();
        }
        if (DEBUG_WINDOW_INSETS) {
            Log.i(this.mTag, "dispatchApplyInsets : " + windowInsets);
        }
        InsetsSource peekSource = this.mInsetsController.getState().peekSource(InsetsSource.ID_IME);
        if (peekSource != null && peekSource.isVisible() && getConfiguration().windowConfiguration.isPopOver()) {
            this.mForceNextWindowRelayout = true;
        }
        view.dispatchApplyWindowInsets(windowInsets);
        this.mAttachInfo.delayNotifyContentCaptureInsetsEvent(windowInsets.getInsets(WindowInsets.Type.all()));
        Trace.traceEnd(8L);
    }

    private boolean shouldDispatchCutout() {
        return this.mWindowAttributes.layoutInDisplayCutoutMode == 3 || this.mWindowAttributes.layoutInDisplayCutoutMode == 1;
    }

    public InsetsController getInsetsController() {
        return this.mInsetsController;
    }

    private static boolean shouldUseDisplaySize(WindowManager.LayoutParams layoutParams) {
        return layoutParams.type == 2041 || layoutParams.type == 2011 || layoutParams.type == 2020;
    }

    private static boolean shouldOptimizeMeasure(WindowManager.LayoutParams layoutParams) {
        return com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.reduceUnnecessaryMeasure() || (layoutParams.privateFlags & 512) != 0;
    }

    private Rect getWindowBoundsInsetSystemBars() {
        Rect rect = new Rect(this.mContext.getResources().getConfiguration().windowConfiguration.getBounds());
        rect.inset(this.mInsetsController.getState().calculateInsets(rect, WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(), false));
        return rect;
    }

    int dipToPx(int i) {
        return (int) ((this.mContext.getResources().getDisplayMetrics().density * i) + 0.5f);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(190:16|(1:956)(1:24)|25|(4:27|(1:29)(1:954)|(1:31)(1:953)|(186:33|34|(6:36|(1:38)(2:939|(1:944)(1:943))|39|(1:41)|42|(1:46))(2:945|(182:949|(1:951)|952|48|(5:50|(2:(1:53)(1:55)|54)|(1:63)|59|(1:62))|64|(1:66)|67|(1:69)|70|(1:938)(1:76)|(3:78|(1:936)(3:84|(1:86)(1:935)|87)|88)(1:937)|89|(1:91)(1:934)|92|(1:94)|95|(2:918|(6:920|(3:922|(2:924|925)(1:927)|926)|928|(1:930)|931|(1:933)))|99|(2:101|(59:103|(1:105)|(1:916)(1:124)|125|(1:915)(1:129)|130|(1:914)(1:134)|135|(1:137)(1:913)|138|(1:140)|(4:142|(1:146)|147|(1:149))|150|(96:468|(3:470|(1:472)(1:911)|473)(1:912)|474|(1:910)(1:478)|479|(1:481)|482|483|484|(9:486|487|488|489|490|491|492|493|494)(1:902)|495|(79:499|500|501|(1:503)(1:885)|504|505|506|507|508|(6:865|866|(1:868)|869|(3:871|872|873)|876)(1:510)|511|(1:513)(1:864)|514|515|(7:834|835|(1:839)|(1:841)(1:860)|(1:859)(4:845|846|847|848)|849|(3:853|(1:855)(1:857)|856))(1:517)|(1:519)|520|521|(1:831)(1:525)|526|(1:829)(6:529|(1:531)|532|(1:534)(1:828)|535|536)|537|538|(6:809|810|811|812|813|814)(1:540)|541|542|(1:544)(1:807)|545|546|547|548|(3:796|797|(47:799|(3:790|791|(43:793|553|554|555|(2:784|(38:786|(9:560|561|562|563|564|565|566|567|568)(1:783)|(2:570|571)(1:774)|(3:766|767|(1:769))|573|574|575|576|577|(4:579|580|581|(28:688|689|691|692|693|694|695|696|697|698|(1:702)|585|(1:(3:588|(1:594)(1:592)|593)(1:595))|596|(1:(1:599)(1:600))|601|(1:603)|604|605|(1:607)|608|(1:687)|612|(7:614|(1:616)|617|(2:619|(3:621|(1:623)|624))|625|(3:679|(3:681|(1:683)|684)|685)(1:630)|(4:632|633|634|635))(1:686)|640|(1:651)|652|(8:662|(1:664)|665|(1:667)(1:676)|668|(1:670)|(2:(1:673)|674)|675))(1:583))(3:728|729|(8:731|(1:733)|734|(1:736)|737|(1:739)|740|(1:742))(1:(3:752|753|754)))|584|585|(0)|596|(0)|601|(0)|604|605|(0)|608|(1:610)|687|612|(0)(0)|640|(2:642|651)|652|(1:654)|656|662|(0)|665|(0)(0)|668|(0)|(0)|675))|558|(0)(0)|(0)(0)|(0)|573|574|575|576|577|(0)(0)|584|585|(0)|596|(0)|601|(0)|604|605|(0)|608|(0)|687|612|(0)(0)|640|(0)|652|(0)|656|662|(0)|665|(0)(0)|668|(0)|(0)|675))|552|553|554|555|(0)|784|(0)|558|(0)(0)|(0)(0)|(0)|573|574|575|576|577|(0)(0)|584|585|(0)|596|(0)|601|(0)|604|605|(0)|608|(0)|687|612|(0)(0)|640|(0)|652|(0)|656|662|(0)|665|(0)(0)|668|(0)|(0)|675))|550|(0)|552|553|554|555|(0)|784|(0)|558|(0)(0)|(0)(0)|(0)|573|574|575|576|577|(0)(0)|584|585|(0)|596|(0)|601|(0)|604|605|(0)|608|(0)|687|612|(0)(0)|640|(0)|652|(0)|656|662|(0)|665|(0)(0)|668|(0)|(0)|675)|890|891|892|893|500|501|(0)(0)|504|505|506|507|508|(0)(0)|511|(0)(0)|514|515|(0)(0)|(0)|520|521|(1:523)|831|526|(0)|829|537|538|(0)(0)|541|542|(0)(0)|545|546|547|548|(0)|550|(0)|552|553|554|555|(0)|784|(0)|558|(0)(0)|(0)(0)|(0)|573|574|575|576|577|(0)(0)|584|585|(0)|596|(0)|601|(0)|604|605|(0)|608|(0)|687|612|(0)(0)|640|(0)|652|(0)|656|662|(0)|665|(0)(0)|668|(0)|(0)|675)(1:157)|158|(1:160)|161|(6:165|166|167|168|(1:170)|172)|(1:467)|(1:466)(1:189)|(1:465)(1:193)|(2:195|(8:197|(1:212)|201|(1:203)|204|(1:206)|207|(2:209|(1:211))))|(1:214)(1:(1:462)(29:(1:464)|(1:217)|218|(3:220|(5:451|(1:453)(1:459)|454|(1:456)(1:458)|457)(1:224)|225)(1:460)|226|(1:450)(1:230)|231|(10:233|(4:235|(1:237)|238|(1:240))(1:438)|(1:242)(1:437)|243|(1:245)|246|(1:248)(1:(1:436))|249|250|251)(2:439|(3:443|444|445))|252|(4:254|(4:266|(1:268)|269|(2:271|(2:273|(1:275))(1:(1:277))))(2:258|(1:262))|263|(1:265))|(1:428)|281|(1:426)(1:284)|285|(1:287)|(1:425)(1:290)|291|(1:424)(1:296)|(4:298|(1:300)|301|(1:305))|(1:423)(1:308)|309|(1:313)|(5:315|(1:317)|318|(4:322|(2:325|323)|326|327)|328)(2:(4:386|(3:388|(1:394)(1:392)|393)|(1:396)(1:399)|397)(8:400|(1:402)|403|(1:405)|406|(4:410|(2:413|411)|414|415)|416|(1:418))|398)|329|(1:331)|332|(4:334|(1:336)|337|(1:339))|340|(18:342|(1:379)(1:346)|347|(1:351)|352|(1:378)|358|(1:360)|361|(1:363)|364|(3:366|(1:368)|369)|370|(1:372)|373|(1:375)|376|377)(2:380|(2:382|383)(1:384))))|215|(0)|218|(0)(0)|226|(1:228)|450|231|(0)(0)|252|(0)|(1:279)|428|281|(0)|426|285|(0)|(0)|425|291|(1:294)|424|(0)|(0)|419|423|309|(2:311|313)|(0)(0)|329|(0)|332|(0)|340|(0)(0)))|917|(0)|(0)|916|125|(1:127)|915|130|(1:132)|914|135|(0)(0)|138|(0)|(0)|150|(0)|468|(0)(0)|474|(1:476)|910|479|(0)|482|483|484|(0)(0)|495|(129:499|500|501|(0)(0)|504|505|506|507|508|(0)(0)|511|(0)(0)|514|515|(0)(0)|(0)|520|521|(0)|831|526|(0)|829|537|538|(0)(0)|541|542|(0)(0)|545|546|547|548|(0)|550|(0)|552|553|554|555|(0)|784|(0)|558|(0)(0)|(0)(0)|(0)|573|574|575|576|577|(0)(0)|584|585|(0)|596|(0)|601|(0)|604|605|(0)|608|(0)|687|612|(0)(0)|640|(0)|652|(0)|656|662|(0)|665|(0)(0)|668|(0)|(0)|675|158|(0)|161|(7:163|165|166|167|168|(0)|172)|(0)|467|(2:185|187)|466|(1:191)|465|(0)|(0)(0)|215|(0)|218|(0)(0)|226|(0)|450|231|(0)(0)|252|(0)|(0)|428|281|(0)|426|285|(0)|(0)|425|291|(0)|424|(0)|(0)|419|423|309|(0)|(0)(0)|329|(0)|332|(0)|340|(0)(0))|890|891|892|893|500|501|(0)(0)|504|505|506|507|508|(0)(0)|511|(0)(0)|514|515|(0)(0)|(0)|520|521|(0)|831|526|(0)|829|537|538|(0)(0)|541|542|(0)(0)|545|546|547|548|(0)|550|(0)|552|553|554|555|(0)|784|(0)|558|(0)(0)|(0)(0)|(0)|573|574|575|576|577|(0)(0)|584|585|(0)|596|(0)|601|(0)|604|605|(0)|608|(0)|687|612|(0)(0)|640|(0)|652|(0)|656|662|(0)|665|(0)(0)|668|(0)|(0)|675|158|(0)|161|(0)|(0)|467|(0)|466|(0)|465|(0)|(0)(0)|215|(0)|218|(0)(0)|226|(0)|450|231|(0)(0)|252|(0)|(0)|428|281|(0)|426|285|(0)|(0)|425|291|(0)|424|(0)|(0)|419|423|309|(0)|(0)(0)|329|(0)|332|(0)|340|(0)(0)))|47|48|(0)|64|(0)|67|(0)|70|(2:72|74)|938|(0)(0)|89|(0)(0)|92|(0)|95|(1:97)|918|(0)|99|(0)|917|(0)|(0)|916|125|(0)|915|130|(0)|914|135|(0)(0)|138|(0)|(0)|150|(0)|468|(0)(0)|474|(0)|910|479|(0)|482|483|484|(0)(0)|495|(0)|890|891|892|893|500|501|(0)(0)|504|505|506|507|508|(0)(0)|511|(0)(0)|514|515|(0)(0)|(0)|520|521|(0)|831|526|(0)|829|537|538|(0)(0)|541|542|(0)(0)|545|546|547|548|(0)|550|(0)|552|553|554|555|(0)|784|(0)|558|(0)(0)|(0)(0)|(0)|573|574|575|576|577|(0)(0)|584|585|(0)|596|(0)|601|(0)|604|605|(0)|608|(0)|687|612|(0)(0)|640|(0)|652|(0)|656|662|(0)|665|(0)(0)|668|(0)|(0)|675|158|(0)|161|(0)|(0)|467|(0)|466|(0)|465|(0)|(0)(0)|215|(0)|218|(0)(0)|226|(0)|450|231|(0)(0)|252|(0)|(0)|428|281|(0)|426|285|(0)|(0)|425|291|(0)|424|(0)|(0)|419|423|309|(0)|(0)(0)|329|(0)|332|(0)|340|(0)(0)))|955|34|(0)(0)|47|48|(0)|64|(0)|67|(0)|70|(0)|938|(0)(0)|89|(0)(0)|92|(0)|95|(0)|918|(0)|99|(0)|917|(0)|(0)|916|125|(0)|915|130|(0)|914|135|(0)(0)|138|(0)|(0)|150|(0)|468|(0)(0)|474|(0)|910|479|(0)|482|483|484|(0)(0)|495|(0)|890|891|892|893|500|501|(0)(0)|504|505|506|507|508|(0)(0)|511|(0)(0)|514|515|(0)(0)|(0)|520|521|(0)|831|526|(0)|829|537|538|(0)(0)|541|542|(0)(0)|545|546|547|548|(0)|550|(0)|552|553|554|555|(0)|784|(0)|558|(0)(0)|(0)(0)|(0)|573|574|575|576|577|(0)(0)|584|585|(0)|596|(0)|601|(0)|604|605|(0)|608|(0)|687|612|(0)(0)|640|(0)|652|(0)|656|662|(0)|665|(0)(0)|668|(0)|(0)|675|158|(0)|161|(0)|(0)|467|(0)|466|(0)|465|(0)|(0)(0)|215|(0)|218|(0)(0)|226|(0)|450|231|(0)(0)|252|(0)|(0)|428|281|(0)|426|285|(0)|(0)|425|291|(0)|424|(0)|(0)|419|423|309|(0)|(0)(0)|329|(0)|332|(0)|340|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:762:0x07fa, code lost:
    
        r8 = r35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:770:0x0637, code lost:
    
        if (r40.mApplyInsetsRequested != false) goto L923;
     */
    /* JADX WARN: Code restructure failed: missing block: B:788:0x0806, code lost:
    
        r32 = r7;
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:805:0x080f, code lost:
    
        r32 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:808:0x080d, code lost:
    
        r31 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:821:0x081d, code lost:
    
        r29 = r4;
        r32 = r7;
        r7 = false;
        r15 = false;
        r17 = 8;
        r21 = false;
        r24 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:833:0x082e, code lost:
    
        r32 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:863:0x082c, code lost:
    
        r28 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:882:0x083d, code lost:
    
        r28 = r6;
        r32 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:884:0x083b, code lost:
    
        r22 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:887:0x0852, code lost:
    
        r22 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:904:0x086a, code lost:
    
        r25 = r4;
        r22 = r6;
        r26 = r7;
        r17 = 8;
        r7 = false;
        r8 = null;
        r15 = false;
     */
    /* JADX WARN: Not initialized variable reg: 35, insn: 0x07fa: MOVE (r8 I:??[OBJECT, ARRAY]) = (r35 I:??[OBJECT, ARRAY]), block:B:762:0x07fa */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0299 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0342 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0ac3  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0adc  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0aef A[Catch: RemoteException -> 0x0b05, TRY_LEAVE, TryCatch #23 {RemoteException -> 0x0b05, blocks: (B:168:0x0ae8, B:170:0x0aef), top: B:167:0x0ae8 }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0b09 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0b24  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0b31  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0b3d  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0bdb  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0bf5  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0c04  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0c78  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0c82  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0cfd  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0d91  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0da9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0dbb  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0dc2 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:293:0x0dd4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0de0  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0e05 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:311:0x0e3a  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0e65  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x0f66  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x0f6f  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0f9b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x1027  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x0eb6  */
    /* JADX WARN: Removed duplicated region for block: B:439:0x0ce0  */
    /* JADX WARN: Removed duplicated region for block: B:460:0x0c67  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0be1  */
    /* JADX WARN: Removed duplicated region for block: B:470:0x0370  */
    /* JADX WARN: Removed duplicated region for block: B:476:0x03ac  */
    /* JADX WARN: Removed duplicated region for block: B:481:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:486:0x03cb  */
    /* JADX WARN: Removed duplicated region for block: B:497:0x0410 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:503:0x042a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:510:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:513:0x0470  */
    /* JADX WARN: Removed duplicated region for block: B:517:0x04d9  */
    /* JADX WARN: Removed duplicated region for block: B:519:0x04df A[Catch: RemoteException -> 0x04d5, all -> 0x085d, TRY_LEAVE, TryCatch #38 {RemoteException -> 0x04d5, blocks: (B:848:0x04a9, B:849:0x04b3, B:853:0x04bd, B:856:0x04c7, B:519:0x04df, B:523:0x050e, B:529:0x0522, B:531:0x0526, B:532:0x0542, B:534:0x0553, B:535:0x055c), top: B:847:0x04a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:523:0x050e A[Catch: RemoteException -> 0x04d5, all -> 0x085d, TRY_ENTER, TRY_LEAVE, TryCatch #38 {RemoteException -> 0x04d5, blocks: (B:848:0x04a9, B:849:0x04b3, B:853:0x04bd, B:856:0x04c7, B:519:0x04df, B:523:0x050e, B:529:0x0522, B:531:0x0526, B:532:0x0542, B:534:0x0553, B:535:0x055c), top: B:847:0x04a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:528:0x0520 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:540:0x0593  */
    /* JADX WARN: Removed duplicated region for block: B:544:0x059e  */
    /* JADX WARN: Removed duplicated region for block: B:557:0x05e2 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:560:0x05f1  */
    /* JADX WARN: Removed duplicated region for block: B:570:0x0622 A[Catch: RemoteException -> 0x0615, all -> 0x085d, TRY_LEAVE, TryCatch #37 {RemoteException -> 0x0615, blocks: (B:568:0x060d, B:570:0x0622), top: B:567:0x060d }] */
    /* JADX WARN: Removed duplicated region for block: B:579:0x0649  */
    /* JADX WARN: Removed duplicated region for block: B:587:0x079d  */
    /* JADX WARN: Removed duplicated region for block: B:598:0x07d7  */
    /* JADX WARN: Removed duplicated region for block: B:603:0x07f3 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:607:0x0896  */
    /* JADX WARN: Removed duplicated region for block: B:610:0x08c7  */
    /* JADX WARN: Removed duplicated region for block: B:614:0x08df  */
    /* JADX WARN: Removed duplicated region for block: B:642:0x09c2  */
    /* JADX WARN: Removed duplicated region for block: B:654:0x09f2  */
    /* JADX WARN: Removed duplicated region for block: B:664:0x0a22  */
    /* JADX WARN: Removed duplicated region for block: B:667:0x0a72  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:670:0x0a8b  */
    /* JADX WARN: Removed duplicated region for block: B:672:0x0a9e  */
    /* JADX WARN: Removed duplicated region for block: B:676:0x0a84  */
    /* JADX WARN: Removed duplicated region for block: B:686:0x09b8  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:710:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:717:0x088b  */
    /* JADX WARN: Removed duplicated region for block: B:728:0x06ef  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:766:0x062d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:774:0x062a  */
    /* JADX WARN: Removed duplicated region for block: B:783:0x061a  */
    /* JADX WARN: Removed duplicated region for block: B:786:0x05ec  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:790:0x05c1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:796:0x05b0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:807:0x05a0  */
    /* JADX WARN: Removed duplicated region for block: B:809:0x0572 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:834:0x047b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:864:0x0472  */
    /* JADX WARN: Removed duplicated region for block: B:865:0x0437 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:885:0x042c  */
    /* JADX WARN: Removed duplicated region for block: B:902:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:912:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:913:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:920:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:934:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:937:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:945:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x022f  */
    /* JADX WARN: Type inference failed for: r11v61 */
    /* JADX WARN: Type inference failed for: r11v62 */
    /* JADX WARN: Type inference failed for: r11v63, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r11v65 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void performTraversals() {
        /*
            Method dump skipped, instructions count: 4161
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.performTraversals():void");
    }

    private void createSyncIfNeeded() {
        if (isInWMSRequestedSync() || !this.mReportNextDraw) {
            return;
        }
        final int i = this.mSyncSeqId;
        this.mWmsRequestSyncGroupState = 1;
        this.mWmsRequestSyncGroup = new SurfaceSyncGroup("wmsSync-" + this.mTag, new Consumer() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ViewRootImpl.this.lambda$createSyncIfNeeded$4(i, (SurfaceControl.Transaction) obj);
            }
        });
        if (this.mAppStartInfoTimestampsFlagValue && !this.mAppStartTrackingStarted) {
            this.mAppStartTrackingStarted = true;
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.addTransactionCompletedListener(this.mSimpleExecutor, new Consumer<SurfaceControl.TransactionStats>() { // from class: android.view.ViewRootImpl.6
                @Override // java.util.function.Consumer
                public void accept(SurfaceControl.TransactionStats transactionStats) {
                    SyncFence presentFence = transactionStats.getPresentFence();
                    if (presentFence.awaitForever() && ViewRootImpl.this.mFirstFramePresentedTimeNs == -1) {
                        ViewRootImpl.this.mFirstFramePresentedTimeNs = presentFence.getSignalTime();
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
    public /* synthetic */ void lambda$createSyncIfNeeded$4(final int i, SurfaceControl.Transaction transaction) {
        if (CoreRune.FW_SURFACE_DEBUG_APPLY && transaction != null && !TextUtils.isEmpty(transaction.mDebugName)) {
            transaction.mDebugName += "_seqId<" + i + ">";
        }
        this.mWmsRequestSyncGroupState = 3;
        if (this.mWindowSession instanceof Binder) {
            final SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
            transaction2.merge(transaction);
            this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    ViewRootImpl.this.lambda$createSyncIfNeeded$3(transaction2, i);
                }
            });
            return;
        }
        lambda$createSyncIfNeeded$3(transaction, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeSendAppStartTimes() {
        if (this.mAppStartTimestampsSent.get()) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: android.view.ViewRootImpl.7
            @Override // java.lang.Runnable
            public void run() {
                if (ViewRootImpl.this.mRenderThreadDrawStartTimeNs == -1) {
                    return;
                }
                try {
                    ActivityManager.getService().reportStartInfoViewTimestamps(ViewRootImpl.this.mRenderThreadDrawStartTimeNs, ViewRootImpl.this.mFirstFramePresentedTimeNs);
                    ViewRootImpl.this.mAppStartTimestampsSent.set(true);
                } catch (RemoteException unused) {
                }
            }
        });
    }

    private void applySensitiveContentAppProtection(boolean z) {
        try {
            ISensitiveContentProtectionManager iSensitiveContentProtectionManager = this.mSensitiveContentProtectionService;
            if (iSensitiveContentProtectionManager == null) {
                return;
            }
            iSensitiveContentProtectionManager.setSensitiveContentProtection(getWindowToken(), this.mContext.getPackageName(), z);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to protect sensitive content during screen share", e);
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
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.addTransactionCommittedListener(this.mExecutor, new SurfaceControl.TransactionCommittedListener() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda10
            @Override // android.view.SurfaceControl.TransactionCommittedListener
            public final void onTransactionCommitted() {
                ViewRootImpl.this.lambda$removeSensitiveContentAppProtection$5();
            }
        });
        applyTransactionOnDraw(transaction);
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
            ContentCaptureManager contentCaptureManager = this.mAttachInfo.mContentCaptureManager;
            if (contentCaptureManager != null && this.mAttachInfo.mContentCaptureEvents != null) {
                contentCaptureManager.getMainContentCaptureSession().notifyContentCaptureEvents(this.mAttachInfo.mContentCaptureEvents);
            }
            this.mAttachInfo.mContentCaptureEvents = null;
        }
    }

    private void notifyHolderSurfaceDestroyed() {
        this.mSurfaceHolder.ungetCallbacks();
        SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
        if (callbacks != null) {
            for (SurfaceHolder.Callback callback : callbacks) {
                callback.surfaceDestroyed(this.mSurfaceHolder);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeHandleWindowMove(Rect rect) {
        boolean z = (this.mAttachInfo.mWindowLeft == rect.left && this.mAttachInfo.mWindowTop == rect.top) ? false : true;
        if (z) {
            this.mAttachInfo.mWindowLeft = rect.left;
            this.mAttachInfo.mWindowTop = rect.top;
            this.mForceNextWindowRelayout = true;
        }
        if (z || this.mAttachInfo.mNeedsUpdateLightCenter) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.setLightCenter(this.mAttachInfo);
            }
            this.mAttachInfo.mNeedsUpdateLightCenter = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWindowFocusChanged() {
        synchronized (this) {
            if (this.mWindowFocusChanged) {
                this.mWindowFocusChanged = false;
                boolean z = this.mUpcomingWindowFocus;
                if (z) {
                    this.mInsetsController.onWindowFocusGained(getFocusedViewOrNull() != null);
                } else {
                    this.mInsetsController.onWindowFocusLost();
                }
                if (this.mAdded) {
                    dispatchFocusEvent(z, false);
                    this.mImeFocusController.onPostWindowFocus(getFocusedViewOrNull(), z, this.mWindowAttributes);
                    if (z) {
                        this.mWindowAttributes.softInputMode &= -257;
                        ((WindowManager.LayoutParams) this.mView.getLayoutParams()).softInputMode &= -257;
                        maybeFireAccessibilityWindowStateChangedEvent();
                        fireAccessibilityFocusEventIfHasFocusedNode();
                    } else if (this.mPointerCapture) {
                        handlePointerCaptureChanged(false);
                    }
                }
                this.mFirstInputStage.onWindowFocusChanged(z);
                if (z) {
                    handleContentCaptureFlush();
                }
                if (CoreRune.BIXBY_TOUCH && z && this.mSemPressGestureDetector != null) {
                    this.mSemPressGestureDetector.setBixbyTouchEnable(Settings.System.getInt(this.mContext.getContentResolver(), "bixby_touch_enable", 0) == 1);
                }
            }
        }
    }

    public void dispatchCompatFakeFocus() {
        boolean z;
        synchronized (this) {
            z = this.mWindowFocusChanged && this.mUpcomingWindowFocus;
        }
        boolean z2 = this.mAttachInfo.mHasWindowFocus;
        if (z || z2) {
            return;
        }
        EventLog.writeEvent(LOGTAG_INPUT_FOCUS, "Giving fake focus to " + this.mBasePackageName, "reason=unity bug workaround");
        dispatchFocusEvent(true, true);
        EventLog.writeEvent(LOGTAG_INPUT_FOCUS, "Removing fake focus from " + this.mBasePackageName, "reason=timeout callback");
        dispatchFocusEvent(false, true);
    }

    private void dispatchFocusEvent(boolean z, boolean z2) {
        profileRendering(z);
        if (z && this.mAttachInfo.mThreadedRenderer != null && this.mSurface.isValid()) {
            this.mFullRedrawNeeded = true;
            try {
                this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(this.mWidth, this.mHeight, this.mAttachInfo, this.mSurface, this.mWindowAttributes.surfaceInsets);
                Log.d(this.mTag, String.format("mThreadedRenderer.initializeIfNeeded()#2 mSurface={%s}", "isValid=" + this.mSurface.isValid() + " 0x" + Long.toHexString(this.mSurface.mNativeObject)));
            } catch (Surface.OutOfResourcesException e) {
                Log.e(this.mTag, "OutOfResourcesException locking surface", e);
                try {
                    if (!this.mWindowSession.outOfMemory(this.mWindow)) {
                        Slog.w(this.mTag, "No processes killed for memory; killing self");
                        Process.killProcess(Process.myPid());
                    }
                } catch (RemoteException unused) {
                }
                ViewRootHandler viewRootHandler = this.mHandler;
                viewRootHandler.sendMessageDelayed(viewRootHandler.obtainMessage(6), 500L);
                return;
            }
        }
        if (this.mFirst) {
            this.mEarlyHasWindowFocus = z;
        }
        this.mAttachInfo.mHasWindowFocus = z;
        if (!z2) {
            this.mImeFocusController.onPreWindowFocus(z, this.mWindowAttributes);
        }
        if (this.mView != null) {
            this.mAttachInfo.mKeyDispatchState.reset();
            this.mView.dispatchWindowFocusChanged(z);
            this.mAttachInfo.mTreeObserver.dispatchOnWindowFocusChange(z);
            if (this.mAttachInfo.mTooltipHost != null) {
                this.mAttachInfo.mTooltipHost.hideTooltip();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWindowTouchModeChanged() {
        boolean z;
        synchronized (this) {
            z = this.mUpcomingInTouchMode;
        }
        ensureTouchModeLocally(z);
    }

    private void maybeFireAccessibilityWindowStateChangedEvent() {
        View view;
        WindowManager.LayoutParams layoutParams = this.mWindowAttributes;
        if ((layoutParams == null || layoutParams.type != 2005) && (view = this.mView) != null) {
            view.sendAccessibilityEvent(32);
        }
    }

    private void fireAccessibilityFocusEventIfHasFocusedNode() {
        View findFocus;
        if (this.mAccessibilityManager.isEnabled() && (findFocus = this.mView.findFocus()) != null) {
            AccessibilityNodeProvider accessibilityNodeProvider = findFocus.getAccessibilityNodeProvider();
            if (accessibilityNodeProvider == null) {
                findFocus.sendAccessibilityEvent(8);
                return;
            }
            AccessibilityNodeInfo findFocusedVirtualNode = findFocusedVirtualNode(accessibilityNodeProvider);
            if (findFocusedVirtualNode != null) {
                int virtualDescendantId = AccessibilityNodeInfo.getVirtualDescendantId(findFocusedVirtualNode.getSourceNodeId());
                AccessibilityEvent obtain = AccessibilityEvent.obtain(8);
                obtain.setSource(findFocus, virtualDescendantId);
                obtain.setPackageName(findFocusedVirtualNode.getPackageName());
                obtain.setChecked(findFocusedVirtualNode.isChecked());
                obtain.setContentDescription(findFocusedVirtualNode.getContentDescription());
                obtain.setPassword(findFocusedVirtualNode.isPassword());
                obtain.getText().add(findFocusedVirtualNode.getText());
                obtain.setEnabled(findFocusedVirtualNode.isEnabled());
                findFocus.getParent().requestSendAccessibilityEvent(findFocus, obtain);
                findFocusedVirtualNode.recycle();
            }
        }
    }

    private AccessibilityNodeInfo findFocusedVirtualNode(AccessibilityNodeProvider accessibilityNodeProvider) {
        AccessibilityNodeInfo createAccessibilityNodeInfo;
        AccessibilityNodeInfo findFocus = accessibilityNodeProvider.findFocus(1);
        if (findFocus != null) {
            return findFocus;
        }
        if (!this.mContext.isAutofillCompatibilityEnabled() || (createAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(-1)) == null) {
            return null;
        }
        if (createAccessibilityNodeInfo.isFocused()) {
            return createAccessibilityNodeInfo;
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.offer(createAccessibilityNodeInfo);
        while (!arrayDeque.isEmpty()) {
            AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) arrayDeque.poll();
            LongArray childNodeIds = accessibilityNodeInfo.getChildNodeIds();
            if (childNodeIds != null && childNodeIds.size() > 0) {
                int size = childNodeIds.size();
                for (int i = 0; i < size; i++) {
                    AccessibilityNodeInfo createAccessibilityNodeInfo2 = accessibilityNodeProvider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(childNodeIds.get(i)));
                    if (createAccessibilityNodeInfo2 != null) {
                        if (createAccessibilityNodeInfo2.isFocused()) {
                            return createAccessibilityNodeInfo2;
                        }
                        arrayDeque.offer(createAccessibilityNodeInfo2);
                    }
                }
                accessibilityNodeInfo.recycle();
            }
        }
        return null;
    }

    private void handleOutOfResourcesException(Surface.OutOfResourcesException outOfResourcesException) {
        Log.e(this.mTag, "OutOfResourcesException initializing HW surface", outOfResourcesException);
        try {
            if (!this.mWindowSession.outOfMemory(this.mWindow) && Process.myUid() != 1000) {
                Slog.w(this.mTag, "No processes killed for memory; killing self");
                Process.killProcess(Process.myPid());
            }
        } catch (RemoteException unused) {
        }
        this.mLayoutRequested = true;
    }

    private void performMeasure(int i, int i2) {
        if (this.mView == null) {
            return;
        }
        Trace.traceBegin(8L, "measure");
        try {
            this.mView.measure(i, i2);
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

    private void performLayout(WindowManager.LayoutParams layoutParams, int i, int i2) {
        ViewRootImpl viewRootImpl;
        ArrayList<View> validLayoutRequesters;
        this.mScrollMayChange = true;
        this.mInLayout = true;
        View view = this.mView;
        if (view == null) {
            return;
        }
        if (DEBUG_ORIENTATION || DEBUG_LAYOUT) {
            Log.v(this.mTag, "Laying out " + view + " to (" + view.getMeasuredWidth() + ", " + view.getMeasuredHeight() + NavigationBarInflaterView.KEY_CODE_END);
        }
        Trace.traceBegin(8L, TtmlUtils.TAG_LAYOUT);
        try {
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            this.mInLayout = false;
            if (this.mLayoutRequesters.size() <= 0 || (validLayoutRequesters = getValidLayoutRequesters(this.mLayoutRequesters, false)) == null) {
                viewRootImpl = this;
            } else {
                this.mHandlingLayoutInLayoutRequest = true;
                int size = validLayoutRequesters.size();
                for (int i3 = 0; i3 < size; i3++) {
                    View view2 = validLayoutRequesters.get(i3);
                    Log.w("View", "requestLayout() improperly called by " + view2 + " during layout: running second layout pass");
                    view2.requestLayout();
                }
                viewRootImpl = this;
                viewRootImpl.measureHierarchy(view, layoutParams, this.mView.getContext().getResources(), i, i2, false);
                viewRootImpl.mInLayout = true;
                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                viewRootImpl.mHandlingLayoutInLayoutRequest = false;
                final ArrayList<View> validLayoutRequesters2 = viewRootImpl.getValidLayoutRequesters(viewRootImpl.mLayoutRequesters, true);
                if (validLayoutRequesters2 != null) {
                    getRunQueue().post(new Runnable(viewRootImpl) { // from class: android.view.ViewRootImpl.8
                        @Override // java.lang.Runnable
                        public void run() {
                            int size2 = validLayoutRequesters2.size();
                            for (int i4 = 0; i4 < size2; i4++) {
                                View view3 = (View) validLayoutRequesters2.get(i4);
                                Log.w("View", "requestLayout() improperly called by " + view3 + " during second layout pass: posting in next frame");
                                view3.requestLayout();
                            }
                        }
                    });
                }
            }
            Trace.traceEnd(8L);
            viewRootImpl.mInLayout = false;
        } catch (Throwable th) {
            Trace.traceEnd(8L);
            throw th;
        }
    }

    private ArrayList<View> getValidLayoutRequesters(ArrayList<View> arrayList, boolean z) {
        int size = arrayList.size();
        ArrayList<View> arrayList2 = null;
        for (int i = 0; i < size; i++) {
            View view = arrayList.get(i);
            if (view != null && view.mAttachInfo != null && view.mParent != null && (z || (view.mPrivateFlags & 4096) == 4096)) {
                View view2 = view;
                while (true) {
                    if (view2 != null) {
                        if ((view2.mViewFlags & 12) == 8) {
                            break;
                        }
                        view2 = view2.mParent instanceof View ? (View) view2.mParent : null;
                    } else {
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList<>();
                        }
                        arrayList2.add(view);
                    }
                }
            }
        }
        if (!z) {
            for (int i2 = 0; i2 < size; i2++) {
                View view3 = arrayList.get(i2);
                while (view3 != null && (view3.mPrivateFlags & 4096) != 0) {
                    view3.mPrivateFlags &= -4097;
                    view3 = view3.mParent instanceof View ? (View) view3.mParent : null;
                }
            }
        }
        arrayList.clear();
        return arrayList2;
    }

    @Override // android.view.ViewParent
    public void requestTransparentRegion(View view) {
        checkThread();
        View view2 = this.mView;
        if (view2 != view) {
            return;
        }
        if ((view2.mPrivateFlags & 512) == 0) {
            this.mView.mPrivateFlags |= 512;
            this.mWindowAttributesChanged = true;
        }
        requestLayout();
    }

    private static int getRootMeasureSpec(int i, int i2, int i3) {
        if ((i3 & 4096) != 0) {
            i2 = -1;
        }
        if (i2 == -2) {
            return View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE);
        }
        if (i2 == -1) {
            return View.MeasureSpec.makeMeasureSpec(i, 1073741824);
        }
        return View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
    }

    @Override // android.view.ThreadedRenderer.DrawCallbacks
    public void onPreDraw(RecordingCanvas recordingCanvas) {
        if (this.mCurScrollY != 0 && this.mHardwareYOffset != 0 && this.mAttachInfo.mThreadedRenderer.isOpaque()) {
            recordingCanvas.drawColor(-16777216);
        }
        recordingCanvas.translate(-this.mHardwareXOffset, -this.mHardwareYOffset);
    }

    @Override // android.view.ThreadedRenderer.DrawCallbacks
    public void onPostDraw(RecordingCanvas recordingCanvas) {
        drawAccessibilityFocusedDrawableIfNeeded(recordingCanvas);
        if (this.mUseMTRenderer) {
            for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
                this.mWindowCallbacks.get(size).onPostDraw(recordingCanvas);
            }
        }
    }

    void outputDisplayList(View view) {
        view.mRenderNode.output();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void profileRendering(boolean z) {
        if (this.mProfileRendering) {
            this.mRenderProfilingEnabled = z;
            Choreographer.FrameCallback frameCallback = this.mRenderProfiler;
            if (frameCallback != null) {
                this.mChoreographer.removeFrameCallback(frameCallback);
            }
            if (this.mRenderProfilingEnabled) {
                if (this.mRenderProfiler == null) {
                    this.mRenderProfiler = new Choreographer.FrameCallback() { // from class: android.view.ViewRootImpl.9
                        @Override // android.view.Choreographer.FrameCallback
                        public void doFrame(long j) {
                            ViewRootImpl.this.mDirty.set(0, 0, ViewRootImpl.this.mWidth, ViewRootImpl.this.mHeight);
                            if (ViewRootImpl.DEBUG_TRAVERSAL && ViewRootImpl.DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
                                Log.i(ViewRootImpl.this.mTag, "Traversal, [12] mView=" + ViewRootImpl.this.mView);
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
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mFpsStartTime < 0) {
            this.mFpsPrevTime = currentTimeMillis;
            this.mFpsStartTime = currentTimeMillis;
            this.mFpsNumFrames = 0;
            return;
        }
        this.mFpsNumFrames++;
        String hexString = Integer.toHexString(System.identityHashCode(this));
        long j = currentTimeMillis - this.mFpsPrevTime;
        long j2 = currentTimeMillis - this.mFpsStartTime;
        Log.v(this.mTag, "0x" + hexString + "\tFrame time:\t" + j);
        this.mFpsPrevTime = currentTimeMillis;
        if (j2 > 1000) {
            String str = this.mTag;
            Log.v(str, "0x" + hexString + "\tFPS:\t" + ((this.mFpsNumFrames * 1000.0f) / j2));
            this.mFpsStartTime = currentTimeMillis;
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
                this.mPreviousFrameDrawnTime = this.mChoreographer.getExpectedPresentationTimeNanos();
                return;
            }
            long expectedPresentationTimeNanos = this.mChoreographer.getExpectedPresentationTimeNanos();
            long j = expectedPresentationTimeNanos - this.mPreviousFrameDrawnTime;
            if (j <= 0) {
                return;
            }
            Trace.setCounter(this.mFpsTraceName, 1000000000 / j);
            this.mPreviousFrameDrawnTime = expectedPresentationTimeNanos;
            Trace.setCounter(this.mLargestViewTraceName, (long) (this.mLargestChildPercentage * 100.0f));
            this.mLargestChildPercentage = 0.0f;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: reportDrawFinished, reason: merged with bridge method [inline-methods] */
    public void lambda$createSyncIfNeeded$3(SurfaceControl.Transaction transaction, int i) {
        logAndTrace("reportDrawFinished seqId=" + i);
        try {
            try {
                this.mWindowSession.finishDrawing(this.mWindow, transaction, i);
                if (transaction != null) {
                    transaction.clear();
                }
            } catch (RemoteException e) {
                Log.e(this.mTag, "Unable to report draw finished", e);
                if (transaction != null) {
                    transaction.apply();
                }
                if (transaction != null) {
                    transaction.clear();
                }
            }
        } catch (Throwable th) {
            if (transaction != null) {
                transaction.clear();
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
        final ArrayList<Runnable> captureFrameCommitCallbacks;
        if (isHardwareEnabled() && (captureFrameCommitCallbacks = this.mAttachInfo.mTreeObserver.captureFrameCommitCallbacks()) != null && captureFrameCommitCallbacks.size() > 0) {
            Log.d(this.mTag, "Creating frameCommitCallback commitCallbacks size=" + captureFrameCommitCallbacks.size());
            this.mAttachInfo.mThreadedRenderer.setFrameCommitCallback(new HardwareRenderer.FrameCommitCallback() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda18
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z) {
                    ViewRootImpl.this.lambda$addFrameCommitCallbackIfNeeded$7(captureFrameCommitCallbacks, z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addFrameCommitCallbackIfNeeded$7(final ArrayList arrayList, boolean z) {
        Log.d(this.mTag, "Received frameCommitCallback didProduceBuffer=" + z);
        this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                ViewRootImpl.lambda$addFrameCommitCallbackIfNeeded$6(arrayList);
            }
        });
    }

    static /* synthetic */ void lambda$addFrameCommitCallbackIfNeeded$6(ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            ((Runnable) arrayList.get(i)).run();
        }
    }

    private void registerCallbackForPendingTransactions() {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.merge(this.mPendingTransaction);
        this.mHasPendingTransactions = false;
        Log.i(this.mTag, "registerCallbackForPendingTransactions");
        registerRtFrameCallback(new AnonymousClass10(transaction));
    }

    /* renamed from: android.view.ViewRootImpl$10, reason: invalid class name */
    class AnonymousClass10 implements HardwareRenderer.FrameDrawingCallback {
        final /* synthetic */ SurfaceControl.Transaction val$t;

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public void onFrameDraw(long j) {
        }

        AnonymousClass10(SurfaceControl.Transaction transaction) {
            this.val$t = transaction;
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public HardwareRenderer.FrameCommitCallback onFrameDraw(int i, final long j) {
            ViewRootImpl.this.mergeWithNextTransaction(this.val$t, j);
            if ((i & 6) != 0) {
                if (ViewRootImpl.this.mBlastBufferQueue == null) {
                    return null;
                }
                ViewRootImpl.this.mBlastBufferQueue.applyPendingTransactions(j);
                return null;
            }
            return new HardwareRenderer.FrameCommitCallback() { // from class: android.view.ViewRootImpl$10$$ExternalSyntheticLambda0
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z) {
                    ViewRootImpl.AnonymousClass10.this.lambda$onFrameDraw$0(j, z);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$0(long j, boolean z) {
            if (z || ViewRootImpl.this.mBlastBufferQueue == null) {
                return;
            }
            ViewRootImpl.this.mBlastBufferQueue.applyPendingTransactions(j);
        }
    }

    private boolean performDraw(final SurfaceSyncGroup surfaceSyncGroup) {
        final SurfaceControl.Transaction transaction;
        this.mLastPerformDrawSkippedReason = null;
        if (!this.mReportNextDraw) {
            int i = this.mAttachInfo.mDisplayState;
            if (i == 1) {
                if (this.mDeferTransactionRequested) {
                    this.mDeferTransactionRequested = false;
                    Log.i(this.mTag, "performDraw() SCREEN_OFF but mDeferTransactionRequested = true break");
                } else {
                    this.mLastPerformDrawSkippedReason = "screen_off";
                    if (!this.mLastDrawScreenOff) {
                        logAndTrace("Not drawing due to screen off");
                    }
                    this.mLastDrawScreenOff = true;
                    return false;
                }
            } else if ((i == 3 || i == 4) && this.mDisplay.getDisplayId() == 0 && (this.mWindowAttributes.samsungFlags & 262144) == 0 && Settings.System.getInt(this.mContentResolver, AOD_SHOW_STATE, 0) != 0) {
                Log.i(this.mTag, "performDraw() was skipped by AOD_SHOW_STATE... DisplayState = " + this.mAttachInfo.mDisplayState);
                return false;
            }
        } else if (this.mView == null) {
            this.mLastPerformDrawSkippedReason = "no_root_view";
            return false;
        }
        if (this.mLastDrawScreenOff) {
            logAndTrace("Resumed drawing after screen turned on");
            this.mLastDrawScreenOff = false;
        }
        boolean z = this.mFullRedrawNeeded || surfaceSyncGroup != null;
        this.mFullRedrawNeeded = false;
        this.mIsDrawing = true;
        Trace.traceBegin(8L, "draw-" + this.mTag);
        addFrameCommitCallbackIfNeeded();
        try {
            boolean draw = draw(z, surfaceSyncGroup, this.mSyncBuffer);
            if (this.mAttachInfo.mThreadedRenderer != null && !draw) {
                this.mAttachInfo.mThreadedRenderer.setFrameCallback(null);
            }
            this.mIsDrawing = false;
            Trace.traceEnd(8L);
            if (this.mAttachInfo.mPendingAnimatingRenderNodes != null) {
                int size = this.mAttachInfo.mPendingAnimatingRenderNodes.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mAttachInfo.mPendingAnimatingRenderNodes.get(i2).endAllAnimators();
                }
                this.mAttachInfo.mPendingAnimatingRenderNodes.clear();
            }
            if (draw || !this.mHasPendingTransactions) {
                transaction = null;
            } else {
                transaction = new SurfaceControl.Transaction();
                transaction.merge(this.mPendingTransaction);
                this.mHasPendingTransactions = false;
            }
            if (this.mReportNextDraw) {
                CountDownLatch countDownLatch = this.mWindowDrawCountDown;
                if (countDownLatch != null) {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException unused) {
                        Log.e(this.mTag, "Window redraw count down interrupted!");
                    }
                    this.mWindowDrawCountDown = null;
                }
                if (this.mAttachInfo.mThreadedRenderer != null) {
                    this.mAttachInfo.mThreadedRenderer.setStopped(this.mStopped);
                }
                if (this.mSurfaceHolder != null && this.mSurface.isValid()) {
                    new SurfaceCallbackHelper(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda15
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewRootImpl.this.lambda$performDraw$8(surfaceSyncGroup, transaction);
                        }
                    }).dispatchSurfaceRedrawNeededAsync(this.mSurfaceHolder, this.mSurfaceHolder.getCallbacks());
                    draw = true;
                } else if (!draw && this.mAttachInfo.mThreadedRenderer != null) {
                    Trace.traceBegin(8L, "fence");
                    this.mAttachInfo.mThreadedRenderer.fence();
                    Trace.traceEnd(8L);
                }
            }
            if (!draw) {
                handleSyncRequestWhenNoAsyncDraw(surfaceSyncGroup, transaction != null, transaction, "no async report");
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
    public /* synthetic */ void lambda$performDraw$8(SurfaceSyncGroup surfaceSyncGroup, SurfaceControl.Transaction transaction) {
        handleSyncRequestWhenNoAsyncDraw(surfaceSyncGroup, transaction != null, transaction, "SurfaceHolder");
    }

    private void handleSyncRequestWhenNoAsyncDraw(SurfaceSyncGroup surfaceSyncGroup, boolean z, SurfaceControl.Transaction transaction, String str) {
        if (surfaceSyncGroup != null) {
            if (z && transaction != null) {
                surfaceSyncGroup.addTransaction(transaction);
            }
            surfaceSyncGroup.markSyncReady();
            return;
        }
        if (!z || transaction == null) {
            return;
        }
        Trace.instant(8L, "Transaction not synced due to " + str + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.mTag);
        if (DEBUG_BLAST) {
            Log.d(this.mTag, "Pending transaction will not be applied in sync with a draw due to " + str);
        }
        mergeWithNextTransaction(transaction, 0L);
    }

    private boolean isContentCaptureEnabled() {
        int i = this.mContentCaptureEnabled;
        boolean z = true;
        if (i == 0) {
            boolean isContentCaptureReallyEnabled = isContentCaptureReallyEnabled();
            this.mContentCaptureEnabled = isContentCaptureReallyEnabled ? 1 : 2;
            return isContentCaptureReallyEnabled;
        }
        if (i != 1) {
            z = false;
            if (i != 2) {
                Log.w(TAG, "isContentCaptureEnabled(): invalid state " + this.mContentCaptureEnabled);
            }
        }
        return z;
    }

    private boolean isContentCaptureReallyEnabled() {
        ContentCaptureManager contentCaptureManager;
        return (this.mContext.getContentCaptureOptions() == null || (contentCaptureManager = this.mAttachInfo.getContentCaptureManager(this.mContext)) == null || !contentCaptureManager.isContentCaptureEnabled()) ? false : true;
    }

    private void performContentCaptureInitialReport() {
        boolean z = false;
        this.mPerformContentCapture = false;
        View view = this.mView;
        if (DEBUG_CONTENT_CAPTURE) {
            Log.v(this.mTag, "performContentCaptureInitialReport() on " + view);
        }
        try {
            if (isContentCaptureEnabled()) {
                z = Trace.isTagEnabled(8L);
                if (z) {
                    Trace.traceBegin(8L, "dispatchContentCapture() for " + getClass().getSimpleName());
                }
                if (this.mAttachInfo.mContentCaptureManager != null) {
                    ContentCaptureSession mainContentCaptureSession = this.mAttachInfo.mContentCaptureManager.getMainContentCaptureSession();
                    mainContentCaptureSession.notifyWindowBoundsChanged(mainContentCaptureSession.getId(), getConfiguration().windowConfiguration.getBounds());
                }
                view.dispatchInitialProvideContentCaptureStructure();
            }
        } finally {
            if (z) {
                Trace.traceEnd(8L);
            }
        }
    }

    private void handleContentCaptureFlush() {
        if (DEBUG_CONTENT_CAPTURE) {
            Log.v(this.mTag, "handleContentCaptureFlush()");
        }
        try {
            if (isContentCaptureEnabled()) {
                boolean isTagEnabled = Trace.isTagEnabled(8L);
                if (isTagEnabled) {
                    Trace.traceBegin(8L, "flushContentCapture for " + getClass().getSimpleName());
                }
                ContentCaptureManager contentCaptureManager = this.mAttachInfo.mContentCaptureManager;
                if (contentCaptureManager == null) {
                    Log.w(TAG, "No ContentCapture on AttachInfo");
                    if (isTagEnabled) {
                        Trace.traceEnd(8L);
                        return;
                    }
                    return;
                }
                contentCaptureManager.flush(2);
                if (isTagEnabled) {
                    Trace.traceEnd(8L);
                }
            }
        } catch (Throwable th) {
            if (0 != 0) {
                Trace.traceEnd(8L);
            }
            throw th;
        }
    }

    private boolean draw(boolean z, SurfaceSyncGroup surfaceSyncGroup, boolean z2) {
        int i;
        boolean z3;
        Scroller scroller;
        Surface surface = this.mSurface;
        boolean z4 = false;
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
            ArrayList<Runnable> arrayList = sFirstDrawHandlers;
            synchronized (arrayList) {
                sFirstDrawComplete = true;
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mHandler.post(sFirstDrawHandlers.get(i2));
                }
            }
        }
        scrollToRectOrFocus(null, false);
        if (this.mAttachInfo.mViewScrollChanged) {
            this.mAttachInfo.mViewScrollChanged = false;
            this.mAttachInfo.mTreeObserver.dispatchOnScrollChanged();
        }
        Scroller scroller2 = this.mScroller;
        boolean z5 = scroller2 != null && scroller2.computeScrollOffset();
        if (z5) {
            i = this.mScroller.getCurrY();
        } else {
            i = this.mScrollY;
        }
        if (this.mCurScrollY != i) {
            this.mCurScrollY = i;
            KeyEvent.Callback callback = this.mView;
            if (callback instanceof RootViewSurfaceTaker) {
                ((RootViewSurfaceTaker) callback).onRootViewScrollYChanged(i);
            }
            z3 = true;
        } else {
            z3 = z;
        }
        float f = this.mAttachInfo.mApplicationScale;
        boolean z6 = this.mAttachInfo.mScalingRequired;
        Rect rect = this.mDirty;
        if (this.mSurfaceHolder != null) {
            rect.setEmpty();
            if (z5 && (scroller = this.mScroller) != null) {
                scroller.abortAnimation();
            }
            return false;
        }
        if (z3) {
            rect.set(0, 0, (int) ((this.mWidth * f) + 0.5f), (int) ((this.mHeight * f) + 0.5f));
        }
        if (DEBUG_ORIENTATION || DEBUG_DRAW) {
            Log.v(this.mTag, "Draw " + this.mView + "/" + ((Object) this.mWindowAttributes.getTitle()) + ": dirty={" + rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom + "} surface=" + surface + " surface.isValid()=" + surface.isValid() + ", appScale:" + f + ", width=" + this.mWidth + ", height=" + this.mHeight);
        }
        this.mAttachInfo.mTreeObserver.dispatchOnDraw();
        int i3 = -this.mCanvasOffsetX;
        int i4 = (-this.mCanvasOffsetY) + i;
        WindowManager.LayoutParams layoutParams = this.mWindowAttributes;
        Rect rect2 = layoutParams != null ? layoutParams.surfaceInsets : null;
        if (rect2 != null) {
            i3 -= rect2.left;
            i4 -= rect2.top;
            rect.offset(rect2.left, rect2.top);
        }
        int i5 = i4;
        boolean isAccessibilityFocusDirty = isAccessibilityFocusDirty();
        if (isAccessibilityFocusDirty && getAccessibilityFocusedRect(this.mAttachInfo.mTmpInvalRect)) {
            requestLayout();
        }
        this.mAttachInfo.mDrawingTime = this.mChoreographer.getFrameTimeNanos() / 1000000;
        boolean z7 = DEBUG_TRAVERSAL;
        if (z7 && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [13] mView=" + this.mView + " dirty.isEmpty=" + rect.isEmpty() + " mIsAnimating=" + this.mIsAnimating + " accessibilityFocusDirty=" + isAccessibilityFocusDirty + " mForceDraw=" + this.mForceDraw);
        }
        if (!rect.isEmpty() || this.mIsAnimating || isAccessibilityFocusDirty || this.mForceDraw) {
            if (isHardwareEnabled()) {
                boolean z8 = isAccessibilityFocusDirty || this.mInvalidateRootRequested;
                this.mInvalidateRootRequested = false;
                this.mIsAnimating = false;
                if (this.mHardwareYOffset != i5 || this.mHardwareXOffset != i3) {
                    this.mHardwareYOffset = i5;
                    this.mHardwareXOffset = i3;
                    z8 = true;
                }
                if (z8) {
                    this.mAttachInfo.mThreadedRenderer.invalidateRoot();
                }
                rect.setEmpty();
                boolean updateContentDrawBounds = updateContentDrawBounds();
                if (this.mReportNextDraw) {
                    this.mAttachInfo.mThreadedRenderer.setStopped(false);
                }
                if (updateContentDrawBounds) {
                    requestDrawWindow();
                }
                if (this.mHdrRenderState.updateForFrame(this.mAttachInfo.mDrawingTime)) {
                    float renderHdrSdrRatio = this.mHdrRenderState.getRenderHdrSdrRatio();
                    applyTransactionOnDraw(this.mTransaction.setExtendedRangeBrightness(getSurfaceControl(), renderHdrSdrRatio, this.mHdrRenderState.getDesiredHdrSdrRatio()));
                    this.mAttachInfo.mThreadedRenderer.setTargetHdrSdrRatio(renderHdrSdrRatio);
                }
                if (surfaceSyncGroup != null) {
                    registerCallbacksForSync(z2, surfaceSyncGroup);
                    if (z2) {
                        this.mAttachInfo.mThreadedRenderer.forceDrawNextFrame();
                    }
                } else if (this.mHasPendingTransactions) {
                    registerCallbackForPendingTransactions();
                }
                if (this.mForceDraw) {
                    Log.i(this.mTag, "Force to draw even when frame is empty");
                    this.mForceDraw = false;
                }
                long uptimeNanos = SystemClock.uptimeNanos();
                this.mAttachInfo.mThreadedRenderer.draw(this.mView, this.mAttachInfo, this);
                if (this.mAppStartInfoTimestampsFlagValue && this.mRenderThreadDrawStartTimeNs == -1) {
                    this.mRenderThreadDrawStartTimeNs = uptimeNanos;
                }
                z4 = true;
            } else {
                if (this.mAttachInfo.mThreadedRenderer != null && !this.mAttachInfo.mThreadedRenderer.isEnabled() && this.mAttachInfo.mThreadedRenderer.isRequested() && this.mSurface.isValid()) {
                    if (z7 && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
                        Log.i(this.mTag, "Traversal, [13-1] mView=" + this.mView + " isImpossibleRenderer=" + isImpossibleRenderer());
                    }
                    if (isImpossibleRenderer()) {
                        Log.e(this.mTag, "Renderer can't be initialized due to isImpossibleRenderer()");
                        this.mFullRedrawNeeded = true;
                        scheduleTraversals();
                        return false;
                    }
                    try {
                        this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(this.mWidth, this.mHeight, this.mAttachInfo, this.mSurface, rect2);
                        if (ViewRune.COMMON_IS_PRODUCT_DEV) {
                            Log.d(this.mTag, String.format("mThreadedRenderer.initializeIfNeeded()#1 mSurface={%s}", "isValid=" + this.mSurface.isValid() + " 0x" + Long.toHexString(this.mSurface.mNativeObject)));
                        }
                        this.mFullRedrawNeeded = true;
                        scheduleTraversals();
                        return false;
                    } catch (Surface.OutOfResourcesException e) {
                        handleOutOfResourcesException(e);
                        return false;
                    }
                }
                if (!drawSoftware(surface, this.mAttachInfo, i3, i5, z6, rect, rect2)) {
                    return false;
                }
            }
        }
        if (z7 && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [13-2] mView=" + this.mView + " animating=" + z5);
        }
        if (z5) {
            this.mFullRedrawNeeded = true;
            scheduleTraversals();
        }
        return z4;
    }

    private boolean drawSoftware(Surface surface, View.AttachInfo attachInfo, int i, int i2, boolean z, Rect rect, Rect rect2) {
        try {
            Canvas lockCanvas = this.mSurface.lockCanvas(rect);
            lockCanvas.setDensity(this.mDensity);
            try {
                if (DEBUG_ORIENTATION || DEBUG_DRAW) {
                    Log.v(this.mTag, "Surface " + surface + " drawing to bitmap w=" + lockCanvas.getWidth() + ", h=" + lockCanvas.getHeight() + ", dirty: " + rect + ", xOff=" + i + ", yOff=" + i2);
                }
                if (!lockCanvas.isOpaque() || i2 != 0 || i != 0) {
                    lockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                }
                rect.setEmpty();
                this.mIsAnimating = false;
                this.mView.mPrivateFlags |= 32;
                if (DEBUG_DRAW) {
                    Context context = this.mView.getContext();
                    Log.i(this.mTag, "Drawing: package:" + context.getPackageName() + ", metrics=" + context.getResources().getDisplayMetrics() + ", compatibilityInfo=" + context.getResources().getCompatibilityInfo());
                }
                lockCanvas.translate(-i, -i2);
                CompatibilityInfo.Translator translator = this.mTranslator;
                if (translator != null) {
                    translator.translateCanvas(lockCanvas);
                }
                lockCanvas.setScreenDensity(z ? this.mNoncompatDensity : 0);
                this.mView.draw(lockCanvas);
                drawAccessibilityFocusedDrawableIfNeeded(lockCanvas);
                try {
                    surface.unlockCanvasAndPost(lockCanvas);
                    return true;
                } catch (IllegalArgumentException e) {
                    Log.e(this.mTag, "Could not unlock surface", e);
                    this.mLayoutRequested = true;
                    return false;
                }
            } catch (Throwable th) {
                try {
                    surface.unlockCanvasAndPost(lockCanvas);
                    throw th;
                } catch (IllegalArgumentException e2) {
                    Log.e(this.mTag, "Could not unlock surface", e2);
                    this.mLayoutRequested = true;
                    return false;
                }
            }
        } catch (Surface.OutOfResourcesException e3) {
            handleOutOfResourcesException(e3);
            return false;
        } catch (IllegalArgumentException e4) {
            Log.e(this.mTag, "Could not lock surface", e4);
            this.mLayoutRequested = true;
            return false;
        }
    }

    private void drawAccessibilityFocusedDrawableIfNeeded(Canvas canvas) {
        Rect rect = this.mAttachInfo.mTmpInvalRect;
        boolean z = false;
        if (getAccessibilityFocusedRect(rect)) {
            if (this.mContext.getResources().getConfiguration().isScreenRound() && this.mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH)) {
                z = true;
            }
            Drawable accessibilityFocusedDrawable = getAccessibilityFocusedDrawable();
            if (accessibilityFocusedDrawable != null) {
                accessibilityFocusedDrawable.setBounds(rect);
                accessibilityFocusedDrawable.draw(canvas);
                if (this.mDisplay == null || !z) {
                    return;
                }
                drawAccessibilityFocusedBorderOnRoundDisplay(canvas, rect, getRoundDisplayRadius(), getRoundDisplayAccessibilityHighlightPaint());
                return;
            }
            return;
        }
        if (this.mAttachInfo.mAccessibilityFocusDrawable != null) {
            this.mAttachInfo.mAccessibilityFocusDrawable.setBounds(0, 0, 0, 0);
        }
    }

    private int getRoundDisplayRadius() {
        Point point = new Point();
        this.mDisplay.getRealSize(point);
        return point.x / 2;
    }

    private Paint getRoundDisplayAccessibilityHighlightPaint() {
        if (this.mRoundDisplayAccessibilityHighlightPaint == null) {
            Paint paint = new Paint();
            this.mRoundDisplayAccessibilityHighlightPaint = paint;
            paint.setStyle(Paint.Style.STROKE);
            this.mRoundDisplayAccessibilityHighlightPaint.setAntiAlias(true);
        }
        this.mRoundDisplayAccessibilityHighlightPaint.setStrokeWidth(this.mAccessibilityManager.getAccessibilityFocusStrokeWidth());
        this.mRoundDisplayAccessibilityHighlightPaint.setColor(this.mAccessibilityManager.getAccessibilityFocusColor());
        return this.mRoundDisplayAccessibilityHighlightPaint;
    }

    private void drawAccessibilityFocusedBorderOnRoundDisplay(Canvas canvas, Rect rect, int i, Paint paint) {
        int save = canvas.save();
        canvas.clipRect(rect);
        float f = i;
        canvas.drawCircle(f, f, f - (this.mAccessibilityManager.getAccessibilityFocusStrokeWidth() / 2.0f), paint);
        canvas.restoreToCount(save);
    }

    private boolean getAccessibilityFocusedRect(Rect rect) {
        View view;
        if (this.mView == null) {
            Slog.w(TAG, "calling getAccessibilityFocusedRect() while the mView is null");
            return false;
        }
        if (this.mAccessibilityManager.isEnabled() && this.mAccessibilityManager.isTouchExplorationEnabled() && (view = this.mAccessibilityFocusedHost) != null && view.mAttachInfo != null) {
            if (view.getAccessibilityNodeProvider() == null) {
                view.getBoundsOnScreen(rect, true);
            } else {
                AccessibilityNodeInfo accessibilityNodeInfo = this.mAccessibilityFocusedVirtualView;
                if (accessibilityNodeInfo != null) {
                    accessibilityNodeInfo.getBoundsInScreen(rect);
                }
            }
            View.AttachInfo attachInfo = this.mAttachInfo;
            rect.offset(0, attachInfo.mViewRootImpl.mScrollY);
            rect.offset(-attachInfo.mWindowLeft, -attachInfo.mWindowTop);
            if (!rect.intersect(0, 0, attachInfo.mViewRootImpl.mWidth, attachInfo.mViewRootImpl.mHeight)) {
                rect.setEmpty();
            }
            if (rect.isEmpty()) {
                return false;
            }
            if (android.view.accessibility.Flags.focusRectMinSize()) {
                adjustAccessibilityFocusedRectBoundsIfNeeded(rect);
            }
            return true;
        }
        return false;
    }

    public void adjustAccessibilityFocusedRectBoundsIfNeeded(Rect rect) {
        int accessibilityFocusStrokeWidth = this.mAccessibilityManager.getAccessibilityFocusStrokeWidth() * 2;
        if (rect.width() < accessibilityFocusStrokeWidth || rect.height() < accessibilityFocusStrokeWidth) {
            rect.inset(((int) Math.ceil(Math.max(0, accessibilityFocusStrokeWidth - rect.width()) / 2.0f)) * (-1), ((int) Math.ceil(Math.max(0, accessibilityFocusStrokeWidth - rect.height()) / 2.0f)) * (-1));
        }
    }

    private Drawable getAccessibilityFocusedDrawable() {
        if (this.mAttachInfo.mAccessibilityFocusDrawable == null) {
            TypedValue typedValue = new TypedValue();
            if (this.mView.mContext.getTheme().resolveAttribute(R.attr.accessibilityFocusedDrawable, typedValue, true)) {
                this.mAttachInfo.mAccessibilityFocusDrawable = this.mView.mContext.getDrawable(typedValue.resourceId);
            }
        }
        if (this.mAttachInfo.mAccessibilityFocusDrawable instanceof GradientDrawable) {
            ((GradientDrawable) this.mAttachInfo.mAccessibilityFocusDrawable).setStroke(this.mAccessibilityManager.semGetAccessibilityFocusStrokeWidth(this.mContext), this.mAccessibilityManager.getAccessibilityFocusColor());
        }
        return this.mAttachInfo.mAccessibilityFocusDrawable;
    }

    void updateSystemGestureExclusionRectsForView(View view) {
        boolean z = com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.reduceChangedExclusionRectsMsgs() && this.mGestureExclusionTracker.isWaitingForComputeChanges();
        this.mGestureExclusionTracker.updateRectsForView(view);
        if (z) {
            return;
        }
        this.mHandler.sendEmptyMessage(30);
    }

    void systemGestureExclusionChanged() {
        List<Rect> computeChangedRects = this.mGestureExclusionTracker.computeChangedRects();
        if (computeChangedRects == null || this.mView == null) {
            return;
        }
        try {
            this.mWindowSession.reportSystemGestureExclusionChanged(this.mWindow, computeChangedRects);
            this.mAttachInfo.mTreeObserver.dispatchOnSystemGestureExclusionRectsChanged(computeChangedRects);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateDecorViewGestureInterception(boolean z) {
        ViewRootHandler viewRootHandler = this.mHandler;
        viewRootHandler.sendMessage(viewRootHandler.obtainMessage(38, z ? 1 : 0, 0));
    }

    void decorViewInterceptionChanged(boolean z) {
        if (this.mView != null) {
            try {
                this.mWindowSession.reportDecorViewGestureInterceptionChanged(this.mWindow, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setRootSystemGestureExclusionRects(List<Rect> list) {
        boolean z = com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.reduceChangedExclusionRectsMsgs() && this.mGestureExclusionTracker.isWaitingForComputeChanges();
        this.mGestureExclusionTracker.setRootRects(list);
        if (z) {
            return;
        }
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
            if (!getAccessibilityFocusedRect(this.mKeepClearAccessibilityFocusRect)) {
                this.mKeepClearAccessibilityFocusRect.setEmpty();
            }
            this.mHandler.obtainMessage(35, 1, 0).sendToTarget();
        }
    }

    void keepClearRectsChanged(boolean z) {
        boolean computeChanges = this.mKeepClearRectsTracker.computeChanges();
        boolean computeChanges2 = this.mUnrestrictedKeepClearRectsTracker.computeChanges();
        if ((computeChanges || computeChanges2 || z) && this.mView != null) {
            this.mHasPendingKeepClearAreaChange = true;
            if (this.mHandler.hasMessages(36)) {
                return;
            }
            this.mHandler.sendEmptyMessageDelayed(36, 100L);
            reportKeepClearAreasChanged();
        }
    }

    void reportKeepClearAreasChanged() {
        if (!this.mHasPendingKeepClearAreaChange || this.mView == null) {
            return;
        }
        this.mHasPendingKeepClearAreaChange = false;
        List<Rect> lastComputedRects = this.mKeepClearRectsTracker.getLastComputedRects();
        List<Rect> lastComputedRects2 = this.mUnrestrictedKeepClearRectsTracker.getLastComputedRects();
        Rect rect = this.mKeepClearAccessibilityFocusRect;
        if (rect != null && !rect.isEmpty()) {
            ArrayList arrayList = new ArrayList(lastComputedRects);
            arrayList.add(this.mKeepClearAccessibilityFocusRect);
            lastComputedRects = arrayList;
        }
        try {
            this.mWindowSession.reportKeepClearAreasChanged(this.mWindow, lastComputedRects, lastComputedRects2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestInvalidateRootRenderNode() {
        this.mInvalidateRootRequested = true;
    }

    boolean scrollToRectOrFocus(Rect rect, boolean z) {
        Rect rect2;
        int i;
        if (this.mImeBackAnimationController.isAnimationInProgress()) {
            return false;
        }
        Rect rect3 = this.mAttachInfo.mContentInsets;
        boolean z2 = true;
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT && getDisplayId() == 1 && this.mIsCutoutRemoveForDispatchNeeded) {
            rect2 = this.mAttachInfo.mContentInsets;
        } else {
            rect2 = this.mAttachInfo.mVisibleInsets;
        }
        if (rect2.left > rect3.left || rect2.top > rect3.top || rect2.right > rect3.right || rect2.bottom > rect3.bottom) {
            i = this.mScrollY;
            View findFocus = this.mView.findFocus();
            if (findFocus == null) {
                return false;
            }
            WeakReference<View> weakReference = this.mLastScrolledFocus;
            View view = weakReference != null ? weakReference.get() : null;
            Rect rect4 = findFocus != view ? null : rect;
            boolean z3 = DEBUG_INPUT_RESIZE;
            if (z3) {
                Log.v(this.mTag, "Eval scroll: focus=" + findFocus + " rectangle=" + rect4 + " ci=" + rect3 + " vi=" + rect2);
            }
            if (findFocus != view || this.mScrollMayChange || rect4 != null) {
                this.mLastScrolledFocus = new WeakReference<>(findFocus);
                this.mScrollMayChange = false;
                if (z3) {
                    Log.v(this.mTag, "Need to scroll?");
                }
                if (findFocus.getGlobalVisibleRect(this.mVisRect, null)) {
                    if (z3) {
                        Log.v(this.mTag, "Root w=" + this.mView.getWidth() + " h=" + this.mView.getHeight() + " ci=" + rect3.toShortString() + " vi=" + rect2.toShortString());
                    }
                    if (rect4 == null) {
                        findFocus.getFocusedRect(this.mTempRect);
                        if (z3) {
                            Log.v(this.mTag, "Focus " + findFocus + ": focusRect=" + this.mTempRect.toShortString());
                        }
                        View view2 = this.mView;
                        if (view2 instanceof ViewGroup) {
                            try {
                                ((ViewGroup) view2).offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
                            } catch (IllegalArgumentException e) {
                                Log.e(this.mTag, "offsetDescendantRectToMyCoords() error occurred. focus=" + findFocus + " mTempRect=" + this.mTempRect.toShortString() + " " + e);
                                e.printStackTrace();
                            }
                        }
                        if (DEBUG_INPUT_RESIZE) {
                            Log.v(this.mTag, "Focus in window: focusRect=" + this.mTempRect.toShortString() + " visRect=" + this.mVisRect.toShortString());
                        }
                    } else {
                        this.mTempRect.set(rect4);
                        if (z3) {
                            Log.v(this.mTag, "Request scroll to rect: " + this.mTempRect.toShortString() + " visRect=" + this.mVisRect.toShortString());
                        }
                    }
                    if (this.mTempRect.intersect(this.mVisRect)) {
                        boolean z4 = DEBUG_INPUT_RESIZE;
                        if (z4) {
                            Log.v(this.mTag, "Focus window visible rect: " + this.mTempRect.toShortString());
                        }
                        if (this.mTempRect.height() > (this.mView.getHeight() - rect2.top) - rect2.bottom) {
                            if (z4) {
                                Log.v(this.mTag, "Too tall; leaving scrollY=" + i);
                            }
                        } else if (this.mTempRect.top < rect2.top) {
                            i = this.mTempRect.top - rect2.top;
                            if (z4) {
                                Log.v(this.mTag, "Top covered; scrollY=" + i);
                            }
                        } else if (this.mTempRect.bottom > this.mView.getHeight() - rect2.bottom) {
                            i = this.mTempRect.bottom - (this.mView.getHeight() - rect2.bottom);
                            if (z4) {
                                Log.v(this.mTag, "Bottom covered; scrollY=" + i);
                            }
                        } else {
                            i = 0;
                        }
                    }
                }
            } else if (z3) {
                Log.v(this.mTag, "Keeping scroll y=" + this.mScrollY + " vi=" + rect2.toShortString());
            }
            z2 = false;
        } else {
            z2 = false;
            i = 0;
        }
        if (i != this.mScrollY) {
            if (DEBUG_INPUT_RESIZE) {
                Log.v(this.mTag, "Pan scroll changed: old=" + this.mScrollY + " , new=" + i);
            }
            if (!z) {
                if (this.mScroller == null) {
                    this.mScroller = new Scroller(this.mView.getContext());
                }
                Scroller scroller = this.mScroller;
                int i2 = this.mScrollY;
                scroller.startScroll(0, i2, 0, i - i2);
            } else {
                Scroller scroller2 = this.mScroller;
                if (scroller2 != null) {
                    scroller2.abortAnimation();
                }
            }
            this.mScrollY = i;
        }
        return z2;
    }

    public void setScrollY(int i) {
        Scroller scroller = this.mScroller;
        if (scroller != null) {
            scroller.abortAnimation();
        }
        this.mScrollY = i;
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

    void setAccessibilityFocus(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfo2 = this.mAccessibilityFocusedVirtualView;
        if (accessibilityNodeInfo2 != null) {
            View view2 = this.mAccessibilityFocusedHost;
            this.mAccessibilityFocusedHost = null;
            this.mAccessibilityFocusedVirtualView = null;
            view2.clearAccessibilityFocusNoCallbacks(64);
            AccessibilityNodeProvider accessibilityNodeProvider = view2.getAccessibilityNodeProvider();
            if (accessibilityNodeProvider != null) {
                accessibilityNodeInfo2.getBoundsInParent(this.mTempRect);
                view2.invalidate(this.mTempRect);
                accessibilityNodeProvider.performAction(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo2.getSourceNodeId()), 128, null);
            }
            accessibilityNodeInfo2.recycle();
        }
        View view3 = this.mAccessibilityFocusedHost;
        if (view3 != null && view3 != view) {
            view3.clearAccessibilityFocusNoCallbacks(64);
        }
        this.mAccessibilityFocusedHost = view;
        this.mAccessibilityFocusedVirtualView = accessibilityNodeInfo;
        updateKeepClearForAccessibilityFocusRect();
        requestInvalidateRootRenderNode();
        if (isAccessibilityFocusDirty()) {
            scheduleTraversals();
        }
    }

    boolean hasPointerCapture() {
        return this.mPointerCapture;
    }

    void requestPointerCapture(boolean z) {
        IBinder inputToken = getInputToken();
        if (inputToken == null) {
            Log.e(this.mTag, "No input channel to request Pointer Capture.");
        } else {
            InputManagerGlobal.getInstance().requestPointerCapture(inputToken, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePointerCaptureChanged(boolean z) {
        if (this.mPointerCapture == z) {
            return;
        }
        this.mPointerCapture = z;
        View view = this.mView;
        if (view != null) {
            view.dispatchPointerCaptureChanged(z);
        }
    }

    private void updateColorModeIfNeeded(int i, float f) {
        if (this.mAttachInfo.mThreadedRenderer == null) {
            return;
        }
        boolean z = i == 2 || i == 3;
        if (z && !this.mDisplay.isHdrSdrRatioAvailable()) {
            i = 1;
            z = false;
        }
        if (i != 4 && !getConfiguration().isScreenWideColorGamut()) {
            i = 0;
        }
        logColorMode(i, false);
        if (CoreRune.FW_SCREENSHOT_FOR_HDR && this.mForceModeInScreenshot && this.mInvalidateForScreenshotRunnable != null && i != 2 && i != 3) {
            Log.i(this.mTag, "removeCallbacks mInvalidateForScreenshotRunnable");
            this.mHandler.removeCallbacks(this.mInvalidateForScreenshotRunnable);
        }
        float colorMode = this.mAttachInfo.mThreadedRenderer.setColorMode(i);
        if (f == 0.0f || f > colorMode) {
            f = colorMode;
        }
        this.mHdrRenderState.setDesiredHdrSdrRatio(z, f);
    }

    @Override // android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        if (DEBUG_INPUT_RESIZE) {
            Log.v(this.mTag, "Request child focus: focus now " + view2);
        }
        checkThread();
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [14] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    @Override // android.view.ViewParent
    public void clearChildFocus(View view) {
        if (DEBUG_INPUT_RESIZE) {
            Log.v(this.mTag, "Clearing child focus");
        }
        checkThread();
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [15] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    @Override // android.view.ViewParent
    public void focusableViewAvailable(View view) {
        checkThread();
        View view2 = this.mView;
        if (view2 != null) {
            if (!view2.hasFocus()) {
                if (sAlwaysAssignFocus || !this.mAttachInfo.mInTouchMode) {
                    view.requestFocus();
                    return;
                }
                return;
            }
            View findFocus = this.mView.findFocus();
            if ((findFocus instanceof ViewGroup) && ((ViewGroup) findFocus).getDescendantFocusability() == 262144 && isViewDescendantOf(view, findFocus)) {
                view.requestFocus();
            }
        }
    }

    @Override // android.view.ViewParent
    public void recomputeViewAttributes(View view) {
        checkThread();
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [16] mView=" + this.mView + " child=" + view + " mWillDrawSoon=" + this.mWillDrawSoon);
        }
        if (this.mView == view) {
            if (this.mIsInTraversal) {
                this.mHandler.post(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewRootImpl.this.lambda$recomputeViewAttributes$9();
                    }
                });
            } else {
                this.mAttachInfo.mRecomputeGlobalAttributes = true;
            }
            if (this.mWillDrawSoon) {
                return;
            }
            scheduleTraversals();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$recomputeViewAttributes$9() {
        this.mAttachInfo.mRecomputeGlobalAttributes = true;
    }

    void dispatchDetachedFromWindow() {
        SemPressGestureDetector semPressGestureDetector;
        InputQueue inputQueue;
        AccessibilityInteractionController accessibilityInteractionController;
        this.mInsetsController.onWindowFocusLost();
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
        if (android.view.accessibility.Flags.preventLeakingViewrootimpl() && (accessibilityInteractionController = this.mAccessibilityInteractionController) != null) {
            accessibilityInteractionController.destroy();
            this.mAccessibilityInteractionController = null;
        }
        destroyHardwareRenderer();
        setAccessibilityFocus(null, null);
        this.mInsetsController.cancelExistingAnimations();
        View view2 = this.mView;
        if (view2 != null) {
            view2.assignParent(null);
            this.mView = null;
        }
        this.mBlurRegionAggregator.setViewRoot(null);
        Log.i(this.mTag, "dispatchDetachedFromWindow");
        if (this.mThread != Thread.currentThread()) {
            Log.w(this.mTag, "There is possible to occur CalledFromWrongThreadException. " + Debug.getCallers(10));
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
            this.mWindowSession.remove(this.mWindow.asBinder());
        } catch (RemoteException unused) {
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
    public void performConfigurationChange(MergedConfiguration mergedConfiguration, boolean z, int i, ActivityWindowInfo activityWindowInfo) {
        if (mergedConfiguration == null) {
            throw new IllegalArgumentException("No merged config provided.");
        }
        if (this.mLastReportedMergedConfiguration.getMergedConfiguration().windowConfiguration.getRotation() != mergedConfiguration.getMergedConfiguration().windowConfiguration.getRotation()) {
            this.mUpdateSurfaceNeeded = true;
            if (!this.mIsInTraversal) {
                this.mForceNextWindowRelayout = true;
            }
        }
        Configuration globalConfiguration = mergedConfiguration.getGlobalConfiguration();
        Configuration overrideConfiguration = mergedConfiguration.getOverrideConfiguration();
        if (DEBUG_CONFIGURATION) {
            Log.v(this.mTag, "Applying new config to window " + ((Object) this.mWindowAttributes.getTitle()) + ", globalConfig: " + globalConfiguration + ", overrideConfig: " + overrideConfiguration);
        }
        CompatibilityInfo compatibilityInfo = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
        if (!compatibilityInfo.equals(CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO)) {
            Configuration configuration = new Configuration(globalConfiguration);
            Configuration configuration2 = new Configuration(overrideConfiguration);
            compatibilityInfo.applyToConfiguration(this.mNoncompatDensity, configuration);
            compatibilityInfo.applyToConfiguration(this.mNoncompatDensity, configuration2);
            overrideConfiguration = configuration2;
            globalConfiguration = configuration;
        }
        if (this.mLastReportedMergedConfiguration.getOverrideConfiguration().nightDim != globalConfiguration.nightDim) {
            int i2 = globalConfiguration.nightDim;
            ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
            ThreadedRenderer.setNightDimText(i2);
            Log.i(this.mTag, "performConfigurationChange setNightDimText nightDimLevel=" + i2);
            invalidateWorld(this.mView);
        }
        ArrayList<ConfigChangedCallback> arrayList = sConfigCallbacks;
        synchronized (arrayList) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                sConfigCallbacks.get(size).onConfigurationChanged(globalConfiguration);
            }
        }
        this.mLastReportedMergedConfiguration.setConfiguration(globalConfiguration, overrideConfiguration);
        ActivityWindowInfo activityWindowInfo2 = this.mLastReportedActivityWindowInfo;
        if (activityWindowInfo2 != null && activityWindowInfo != null) {
            activityWindowInfo2.set(activityWindowInfo);
        }
        this.mForceNextConfigUpdate = z;
        ActivityConfigCallback activityConfigCallback = this.mActivityConfigCallback;
        if (activityConfigCallback != null) {
            activityConfigCallback.onConfigurationChanged(overrideConfiguration, i, activityWindowInfo);
        } else {
            if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.enableWindowContextResourcesUpdateOnConfigChange()) {
                IBinder windowContextToken = this.mContext.getWindowContextToken();
                if (windowContextToken instanceof WindowTokenClient) {
                    WindowTokenClientController.getInstance().onWindowConfigurationChanged(windowContextToken, this.mLastReportedMergedConfiguration.getMergedConfiguration(), i == -1 ? this.mDisplay.getDisplayId() : i);
                }
            }
            updateConfiguration(i);
        }
        this.mForceNextConfigUpdate = false;
    }

    public void updateConfiguration(int i) {
        View view = this.mView;
        if (view == null) {
            return;
        }
        Resources resources = view.getResources();
        Configuration configuration = resources.getConfiguration();
        if (i != -1) {
            onMovedToDisplay(i, configuration);
        }
        if (this.mForceNextConfigUpdate || this.mLastConfigurationFromResources.diff(configuration) != 0) {
            updateInternalDisplay(this.mDisplay.getDisplayId(), resources);
            updateLastConfigurationFromResources(configuration);
            this.mView.dispatchConfigurationChanged(configuration);
            this.mForceNextWindowRelayout = true;
            requestLayout();
        }
        updateForceDarkMode();
    }

    private void updateLastConfigurationFromResources(Configuration configuration) {
        View view;
        int layoutDirection = this.mLastConfigurationFromResources.getLayoutDirection();
        int layoutDirection2 = configuration.getLayoutDirection();
        this.mLastConfigurationFromResources.setTo(configuration);
        if (layoutDirection == layoutDirection2 || (view = this.mView) == null || this.mViewLayoutDirectionInitial != 2) {
            return;
        }
        view.setLayoutDirection(layoutDirection2);
    }

    public static boolean isViewDescendantOf(View view, View view2) {
        if (view == view2) {
            return true;
        }
        Object parent = view.getParent();
        return (parent instanceof ViewGroup) && isViewDescendantOf((View) parent, view2);
    }

    private static void forceLayout(View view) {
        view.forceLayout();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                forceLayout(viewGroup.getChildAt(i));
            }
        }
    }

    public void setWebViewAttached(boolean z) {
        this.mWebViewAttached = z;
    }

    public boolean isWebViewAttached() {
        return this.mWebViewAttached;
    }

    final class ViewRootHandler extends Handler {
        ViewRootHandler() {
        }

        @Override // android.os.Handler
        public String getMessageName(Message message) {
            int i = message.what;
            if (i == 21) {
                return "MSG_CLEAR_ACCESSIBILITY_FOCUS_HOST";
            }
            if (i == 106) {
                return "MSG_TOUCH_HINT_TIMEOUT";
            }
            if (i == 34) {
                return "MSG_WINDOW_TOUCH_MODE_CHANGED";
            }
            if (i == 35) {
                return "MSG_KEEP_CLEAR_RECTS_CHANGED";
            }
            if (i == 103) {
                return "MSG_SPEN_GESTURE_EVENT";
            }
            if (i != 104) {
                switch (i) {
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
                    default:
                        switch (i) {
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
                            default:
                                switch (i) {
                                    case 23:
                                        return "MSG_WINDOW_MOVED";
                                    case 24:
                                        return "MSG_SYNTHESIZE_INPUT_EVENT";
                                    case 25:
                                        return "MSG_DISPATCH_WINDOW_SHOWN";
                                    default:
                                        switch (i) {
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
                                            default:
                                                switch (i) {
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
                                                    case 44:
                                                        return "MSG_INITIAL_TOUCH_BOOST_TIMEOUT";
                                                    default:
                                                        return super.getMessageName(message);
                                                }
                                        }
                                }
                        }
                }
            }
            return "MSG_DISPATCH_LETTERBOX_DIRECTION_CHANGED";
        }

        @Override // android.os.Handler
        public boolean sendMessageAtTime(Message message, long j) {
            if (message.what == 26 && message.obj == null) {
                throw new NullPointerException("Attempted to call MSG_REQUEST_KEYBOARD_SHORTCUTS with null receiver:");
            }
            return super.sendMessageAtTime(message, j);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (Trace.isTagEnabled(8L)) {
                Trace.traceBegin(8L, getMessageName(message));
            }
            try {
                handleMessageImpl(message);
            } finally {
                Trace.traceEnd(8L);
            }
        }

        private void handleMessageImpl(Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                    ((View) message.obj).invalidate();
                    return;
                case 2:
                    View.AttachInfo.InvalidateInfo invalidateInfo = (View.AttachInfo.InvalidateInfo) message.obj;
                    invalidateInfo.target.invalidate(invalidateInfo.left, invalidateInfo.top, invalidateInfo.right, invalidateInfo.bottom);
                    invalidateInfo.recycle();
                    return;
                case 3:
                    ViewRootImpl.this.doDie();
                    return;
                case 4:
                case 5:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    ViewRootImpl.this.handleResized((ClientWindowFrames) someArgs.arg1, message.what == 5, (MergedConfiguration) someArgs.arg2, (InsetsState) someArgs.arg3, someArgs.argi1 != 0, someArgs.argi2 != 0, someArgs.argi3, someArgs.argi4, someArgs.argi5 != 0, (ActivityWindowInfo) someArgs.arg4);
                    someArgs.recycle();
                    return;
                case 6:
                    ViewRootImpl.this.handleWindowFocusChanged();
                    return;
                case 7:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    ViewRootImpl.this.enqueueInputEvent((InputEvent) someArgs2.arg1, (InputEventReceiver) someArgs2.arg2, 0, true);
                    someArgs2.recycle();
                    return;
                case 8:
                    ViewRootImpl.this.handleAppVisibility(message.arg1 != 0);
                    return;
                case 9:
                    ViewRootImpl.this.handleGetNewSurface();
                    return;
                default:
                    switch (i) {
                        case 11:
                            KeyEvent keyEvent = (KeyEvent) message.obj;
                            if ((keyEvent.getFlags() & 8) != 0) {
                                keyEvent = KeyEvent.changeFlags(keyEvent, keyEvent.getFlags() & (-9));
                            }
                            ViewRootImpl.this.enqueueInputEvent(keyEvent, null, 1, true);
                            return;
                        case 12:
                            ViewRootImpl.this.enqueueInputEvent((KeyEvent) message.obj, null, 0, true);
                            return;
                        case 13:
                            ViewRootImpl.this.getImeFocusController().onScheduledCheckFocus();
                            return;
                        case 14:
                            if (ViewRootImpl.this.mView != null) {
                                ViewRootImpl.this.mView.onCloseSystemDialogs((String) message.obj);
                                return;
                            }
                            return;
                        case 15:
                        case 16:
                            DragEvent dragEvent = (DragEvent) message.obj;
                            dragEvent.mLocalState = ViewRootImpl.this.mLocalDragState;
                            boolean z = dragEvent.mAction != 2;
                            if (z) {
                                try {
                                    Trace.traceBegin(8L, "c#" + DragEvent.actionToString(dragEvent.mAction));
                                } finally {
                                    if (z) {
                                        Trace.traceEnd(8L);
                                    }
                                }
                            }
                            ViewRootImpl.this.handleDragEvent(dragEvent);
                            if (z) {
                                return;
                            } else {
                                return;
                            }
                        case 17:
                            ViewRootImpl.this.handleDispatchSystemUiVisibilityChanged();
                            return;
                        case 18:
                            Configuration configuration = (Configuration) message.obj;
                            if (configuration.isOtherSeqNewer(ViewRootImpl.this.mLastReportedMergedConfiguration.getMergedConfiguration())) {
                                configuration = ViewRootImpl.this.mLastReportedMergedConfiguration.getGlobalConfiguration();
                            }
                            ViewRootImpl.this.mPendingMergedConfiguration.setConfiguration(configuration, ViewRootImpl.this.mLastReportedMergedConfiguration.getOverrideConfiguration());
                            if (ViewRootImpl.this.mPendingActivityWindowInfo != null) {
                                ViewRootImpl.this.mPendingActivityWindowInfo.set(ViewRootImpl.this.mLastReportedActivityWindowInfo);
                            }
                            ViewRootImpl.this.performConfigurationChange(new MergedConfiguration(ViewRootImpl.this.mPendingMergedConfiguration), false, -1, ViewRootImpl.this.mPendingActivityWindowInfo != null ? new ActivityWindowInfo(ViewRootImpl.this.mPendingActivityWindowInfo) : null);
                            return;
                        case 19:
                            ViewRootImpl.this.mProcessInputEventsScheduled = false;
                            ViewRootImpl.this.doProcessInputEvents();
                            return;
                        default:
                            switch (i) {
                                case 21:
                                    ViewRootImpl.this.setAccessibilityFocus(null, null);
                                    return;
                                case 22:
                                    if (ViewRootImpl.this.mView != null) {
                                        ViewRootImpl viewRootImpl = ViewRootImpl.this;
                                        viewRootImpl.invalidateWorld(viewRootImpl.mView);
                                        return;
                                    }
                                    return;
                                case 23:
                                    if (ViewRootImpl.this.mAdded) {
                                        int width = ViewRootImpl.this.mWinFrame.width();
                                        int height = ViewRootImpl.this.mWinFrame.height();
                                        int i2 = message.arg1;
                                        int i3 = message.arg2;
                                        ViewRootImpl.this.mTmpFrames.frame.left = i2;
                                        ViewRootImpl.this.mTmpFrames.frame.right = i2 + width;
                                        ViewRootImpl.this.mTmpFrames.frame.top = i3;
                                        ViewRootImpl.this.mTmpFrames.frame.bottom = i3 + height;
                                        ViewRootImpl viewRootImpl2 = ViewRootImpl.this;
                                        viewRootImpl2.setFrame(viewRootImpl2.mTmpFrames.frame, false);
                                        ViewRootImpl viewRootImpl3 = ViewRootImpl.this;
                                        viewRootImpl3.maybeHandleWindowMove(viewRootImpl3.mWinFrame);
                                        return;
                                    }
                                    return;
                                case 24:
                                    ViewRootImpl.this.enqueueInputEvent((InputEvent) message.obj, null, 32, true);
                                    return;
                                case 25:
                                    ViewRootImpl.this.handleDispatchWindowShown();
                                    return;
                                case 26:
                                    ViewRootImpl.this.handleRequestKeyboardShortcuts((IResultReceiver) message.obj, message.arg1);
                                    return;
                                default:
                                    switch (i) {
                                        case 28:
                                            ViewRootImpl.this.handlePointerCaptureChanged(message.arg1 != 0);
                                            return;
                                        case 29:
                                            SomeArgs someArgs3 = (SomeArgs) message.obj;
                                            ViewRootImpl.this.handleInsetsControlChanged((InsetsState) someArgs3.arg1, (InsetsSourceControl.Array) someArgs3.arg2);
                                            someArgs3.recycle();
                                            return;
                                        case 30:
                                            ViewRootImpl.this.systemGestureExclusionChanged();
                                            return;
                                        case 31:
                                            ImeTracker.Token token = (ImeTracker.Token) message.obj;
                                            ImeTracker.forLogging().onProgress(token, 30);
                                            if (ViewRootImpl.this.mView == null) {
                                                Log.e(ViewRootImpl.TAG, String.format("Calling showInsets(%d,%b) on window that no longer has views.", Integer.valueOf(message.arg1), Boolean.valueOf(message.arg2 == 1)));
                                            }
                                            ViewRootImpl.this.clearLowProfileModeIfNeeded(message.arg1, message.arg2 == 1);
                                            ViewRootImpl.this.mInsetsController.show(message.arg1, message.arg2 == 1, token);
                                            return;
                                        case 32:
                                            ImeTracker.Token token2 = (ImeTracker.Token) message.obj;
                                            ImeTracker.forLogging().onProgress(token2, 31);
                                            ViewRootImpl.this.mInsetsController.hide(message.arg1, message.arg2 == 1, token2);
                                            return;
                                        case 33:
                                            ViewRootImpl.this.handleScrollCaptureRequest((IScrollCaptureResponseListener) message.obj);
                                            return;
                                        case 34:
                                            ViewRootImpl.this.handleWindowTouchModeChanged();
                                            return;
                                        case 35:
                                            ViewRootImpl.this.keepClearRectsChanged(message.arg1 == 1);
                                            return;
                                        case 36:
                                            ViewRootImpl.this.reportKeepClearAreasChanged();
                                            return;
                                        case 37:
                                            ViewRootImpl.this.resumeAfterSyncTimeout();
                                            return;
                                        case 38:
                                            ViewRootImpl.this.decorViewInterceptionChanged(message.arg1 == 1);
                                            return;
                                        case 39:
                                            if (CoreRune.FW_DVRR_TOOLKIT_PROLONG_TOUCH_BOOST && ViewRootImpl.this.mIsDragging) {
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
                                            if (ViewRootImpl.this.mDrawnThisFrame) {
                                                return;
                                            }
                                            ViewRootImpl.this.setPreferredFrameRateCategory(1);
                                            return;
                                        case 40:
                                            long nanoTime = (ViewRootImpl.this.mIsTouchBoosting || ViewRootImpl.this.mIsFrameRateBoosting || ViewRootImpl.this.mInsetsAnimationRunning) ? 0L : (System.nanoTime() / 1000000) - ViewRootImpl.this.mLastUpdateTimeMillis;
                                            if (nanoTime >= 750) {
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
                                                ViewRootImpl viewRootImpl4 = ViewRootImpl.this;
                                                viewRootImpl4.setPreferredFrameRate(viewRootImpl4.mPreferredFrameRate);
                                                ViewRootImpl viewRootImpl5 = ViewRootImpl.this;
                                                viewRootImpl5.setPreferredFrameRateCategory(viewRootImpl5.mPreferredFrameRateCategory);
                                                ViewRootImpl.this.mInvalidationIdleMessagePosted = false;
                                                ViewRootImpl.this.mIsPressedGesture = false;
                                                return;
                                            }
                                            ViewRootImpl.this.mInvalidationIdleMessagePosted = true;
                                            ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(40, 750 - nanoTime);
                                            return;
                                        case 41:
                                            if (ViewRootImpl.this.mPointerIconEvent == null) {
                                                return;
                                            }
                                            ViewRootImpl viewRootImpl6 = ViewRootImpl.this;
                                            viewRootImpl6.updatePointerIcon(viewRootImpl6.mPointerIconEvent);
                                            return;
                                        case 42:
                                            ViewRootImpl.this.mPreferredFrameRate = 0.0f;
                                            ViewRootImpl.this.mFrameRateCompatibility = 1;
                                            return;
                                        case 43:
                                            ViewRootImpl.this.mSurfaceReplaced = false;
                                            return;
                                        case 44:
                                            if (ViewRootImpl.this.mTouchAndDrawn) {
                                                ViewRootImpl.this.mHandler.removeMessages(39);
                                                ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(39, 3000L);
                                            } else {
                                                ViewRootImpl.this.mIsTouchBoosting = false;
                                                ViewRootImpl.this.setPreferredFrameRateCategory(1);
                                            }
                                            ViewRootImpl.this.mTouchAndDrawn = false;
                                            return;
                                        default:
                                            switch (i) {
                                                case 103:
                                                    ViewRootImpl.this.handleDispatchSPenGestureEvent((InputEvent[]) message.obj);
                                                    return;
                                                case 104:
                                                    ViewRootImpl.this.handleDispatchLetterboxDirectionChanged(message.arg1);
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
                    }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$10(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    boolean ensureTouchMode(boolean z) {
        if (this.mAttachInfo.mInTouchMode == z) {
            return false;
        }
        if (z && this.mAttachInfo.mThreadedRenderer != null && this.mSendPerfHintOnTouch) {
            this.mAttachInfo.mThreadedRenderer.notifyExpensiveFrame();
        }
        if (!z) {
            try {
                Log.i(this.mTag, "setInTouchMode(false), " + Debug.getCallers(10));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        WindowManagerGlobal.getWindowManagerService().setInTouchMode(z, getDisplayId());
        return ensureTouchModeLocally(z);
    }

    private boolean ensureTouchModeLocally(boolean z) {
        if (this.mAttachInfo.mInTouchMode == z) {
            return false;
        }
        this.mAttachInfo.mInTouchMode = z;
        this.mAttachInfo.mTreeObserver.dispatchOnTouchModeChanged(z);
        try {
            return z ? enterTouchMode() : leaveTouchMode();
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "ensureTouchModeLocally() error occurred. inTouchMode=" + z + " " + e);
            e.printStackTrace();
            return false;
        }
    }

    private boolean enterTouchMode() {
        View findFocus;
        View view = this.mView;
        if (view == null || !view.hasFocus() || (findFocus = this.mView.findFocus()) == null || findFocus.isFocusableInTouchMode()) {
            return false;
        }
        ViewGroup findAncestorToTakeFocusInTouchMode = findAncestorToTakeFocusInTouchMode(findFocus);
        if (findAncestorToTakeFocusInTouchMode != null) {
            return findAncestorToTakeFocusInTouchMode.requestFocus();
        }
        findFocus.clearFocusInternal(null, true, false);
        return true;
    }

    private static ViewGroup findAncestorToTakeFocusInTouchMode(View view) {
        ViewParent parent = view.getParent();
        while (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) parent;
            if (viewGroup.getDescendantFocusability() == 262144 && viewGroup.isFocusableInTouchMode()) {
                return viewGroup;
            }
            if (viewGroup.isRootNamespace()) {
                return null;
            }
            parent = viewGroup.getParent();
        }
        return null;
    }

    private boolean leaveTouchMode() {
        View view = this.mView;
        if (view == null) {
            return false;
        }
        if (view.hasFocus()) {
            View findFocus = this.mView.findFocus();
            if (!(findFocus instanceof ViewGroup) || ((ViewGroup) findFocus).getDescendantFocusability() != 262144) {
                return false;
            }
        }
        return this.mView.restoreDefaultFocus();
    }

    private boolean checkPalmRejection(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        int i = 0;
        boolean z = false;
        for (int i2 = 0; i2 < pointerCount; i2++) {
            if (motionEvent.getPalm(i2) == 1.0f || motionEvent.getPalm(i2) == 2.0f || motionEvent.getPalm(i2) == 3.0f) {
                z = true;
            }
            i += (int) motionEvent.getTouchMajor(i2);
        }
        if (motionEvent.getPalm() == -2.0f) {
            return false;
        }
        return i >= 100 || z;
    }

    private boolean getPalmRejection(MotionEvent motionEvent) {
        int i;
        int i2;
        ArrayList arrayList = new ArrayList(20);
        ArrayList arrayList2 = new ArrayList(20);
        int pointerCount = motionEvent.getPointerCount();
        Context context = this.mContext;
        if (context != null) {
            Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);
            i = displayMetrics.widthPixels;
            i2 = displayMetrics.heightPixels;
        } else {
            i = 0;
            i2 = 0;
        }
        float f = i2 > i ? i : i2;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            arrayList.add(Float.valueOf(motionEvent.getX(i3)));
            arrayList2.add(Float.valueOf(motionEvent.getY(i3)));
            f3 += motionEvent.getX(i3);
            motionEvent.getY(i3);
            f4 += motionEvent.getTouchMajor(i3);
            f5 += motionEvent.getTouchMinor(i3);
        }
        float f6 = pointerCount;
        float f7 = f3 / f6;
        float f8 = f4 / f5;
        boolean z = false;
        for (int i4 = 0; i4 < pointerCount; i4++) {
            f2 += (float) Math.sqrt((((Float) arrayList.get(i4)).floatValue() - f7) * (((Float) arrayList.get(i4)).floatValue() - f7));
            if (motionEvent.getPalm(i4) == 1.0f || motionEvent.getPalm(i4) == 2.0f || motionEvent.getPalm(i4) == 3.0f) {
                z = true;
            }
        }
        float f9 = f2 / f6;
        if (z && motionEvent.getToolType(0) == 1 && motionEvent.getAction() != 1) {
            Log.i(TAG, "[ViewRootImpl] action cancel - 1, eccen:" + f8);
            return true;
        }
        if (motionEvent.getToolType(0) != 1 || f4 < 100.0f || f8 <= 2.0f || f9 >= f / (pointerCount + 4)) {
            return false;
        }
        Log.i(TAG, "[ViewRootImpl] action cancel - 2, Palm Sweep, SsumMajor:" + f4 + " eccen:" + f8 + " varX:" + f9 + " TILT_TO_ZOOM_XVAR" + f + " N" + pointerCount);
        return true;
    }

    abstract class InputStage {
        protected static final int FINISH_HANDLED = 1;
        protected static final int FINISH_NOT_HANDLED = 2;
        protected static final int FORWARD = 0;
        private final InputStage mNext;
        private String mTracePrefix;

        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            return 0;
        }

        public InputStage(InputStage inputStage) {
            this.mNext = inputStage;
        }

        public final void deliver(QueuedInputEvent queuedInputEvent) {
            if ((queuedInputEvent.mFlags & 4) != 0) {
                forward(queuedInputEvent);
                return;
            }
            if (shouldDropInputEvent(queuedInputEvent)) {
                finish(queuedInputEvent, false);
                return;
            }
            traceEvent(queuedInputEvent, 8L);
            try {
                int onProcess = onProcess(queuedInputEvent);
                Trace.traceEnd(8L);
                apply(queuedInputEvent, onProcess);
            } catch (Throwable th) {
                Trace.traceEnd(8L);
                throw th;
            }
        }

        protected void finish(QueuedInputEvent queuedInputEvent, boolean z) {
            queuedInputEvent.mFlags |= 4;
            if (z) {
                queuedInputEvent.mFlags |= 8;
            }
            forward(queuedInputEvent);
        }

        protected void forward(QueuedInputEvent queuedInputEvent) {
            onDeliverToNext(queuedInputEvent);
        }

        protected void apply(QueuedInputEvent queuedInputEvent, int i) {
            if (i == 0) {
                forward(queuedInputEvent);
                return;
            }
            if (i == 1) {
                finish(queuedInputEvent, true);
            } else if (i == 2) {
                finish(queuedInputEvent, false);
            } else {
                throw new IllegalArgumentException("Invalid result: " + i);
            }
        }

        protected void onDeliverToNext(QueuedInputEvent queuedInputEvent) {
            if (ViewRootImpl.DEBUG_INPUT_STAGES) {
                Log.v(ViewRootImpl.this.mTag, "Done with " + getClass().getSimpleName() + ". " + queuedInputEvent);
            }
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.deliver(queuedInputEvent);
            } else {
                ViewRootImpl.this.finishInputEvent(queuedInputEvent);
            }
        }

        protected void onWindowFocusChanged(boolean z) {
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.onWindowFocusChanged(z);
            }
        }

        protected void onDetachedFromWindow() {
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.onDetachedFromWindow();
            }
        }

        protected boolean shouldDropInputEvent(QueuedInputEvent queuedInputEvent) {
            String str;
            if (ViewRootImpl.this.mView == null || !ViewRootImpl.this.mAdded) {
                Slog.w(ViewRootImpl.this.mTag, "Dropping event due to root view being removed: " + queuedInputEvent.mEvent);
                Slog.e(ViewRootImpl.this.mTag, "mStopped=" + ViewRootImpl.this.mStopped + " mHasWindowFocus=" + ViewRootImpl.this.mAttachInfo.mHasWindowFocus + " mPausedForTransition=" + ViewRootImpl.this.mPausedForTransition);
                return true;
            }
            boolean z = ViewRootImpl.this.mAttachInfo.mHasWindowFocus;
            if ((ViewRootImpl.this.mView instanceof DecorView) && (queuedInputEvent.mEvent instanceof KeyEvent) && ((KeyEvent) queuedInputEvent.mEvent).getKeyCode() == 4) {
                z |= ((DecorView) ViewRootImpl.this.mView).hasWindowFocusInTask();
            }
            if (!z && !isBack(queuedInputEvent.mEvent) && (ViewRootImpl.this.mWindowAttributes.samsungFlags & 65536) == 0 && !queuedInputEvent.mEvent.isFromSource(2) && !ViewRootImpl.this.isAutofillUiShowing()) {
                str = "no window focus";
            } else if (ViewRootImpl.this.mStopped) {
                str = "window is stopped";
            } else if (ViewRootImpl.this.mIsAmbientMode && !queuedInputEvent.mEvent.isFromSource(1)) {
                str = "non-button event in ambient mode";
            } else {
                if (!ViewRootImpl.this.mPausedForTransition || isBack(queuedInputEvent.mEvent)) {
                    return false;
                }
                str = "paused for transition";
            }
            if (ViewRootImpl.isTerminalInputEvent(queuedInputEvent.mEvent)) {
                queuedInputEvent.mEvent.cancel();
                Slog.w(ViewRootImpl.this.mTag, "Cancelling event (" + str + "):" + queuedInputEvent.mEvent);
                Slog.e(ViewRootImpl.this.mTag, "mStopped=" + ViewRootImpl.this.mStopped + " mHasWindowFocus=" + ViewRootImpl.this.mAttachInfo.mHasWindowFocus + " mPausedForTransition=" + ViewRootImpl.this.mPausedForTransition);
                return false;
            }
            Slog.w(ViewRootImpl.this.mTag, "Dropping event (" + str + "):" + queuedInputEvent.mEvent);
            Slog.e(ViewRootImpl.this.mTag, "mStopped=" + ViewRootImpl.this.mStopped + " mHasWindowFocus=" + ViewRootImpl.this.mAttachInfo.mHasWindowFocus + " mPausedForTransition=" + ViewRootImpl.this.mPausedForTransition);
            return true;
        }

        void dump(String str, PrintWriter printWriter) {
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.dump(str, printWriter);
            }
        }

        boolean isBack(InputEvent inputEvent) {
            return (inputEvent instanceof KeyEvent) && ((KeyEvent) inputEvent).getKeyCode() == 4;
        }

        private void traceEvent(QueuedInputEvent queuedInputEvent, long j) {
            if (Trace.isTagEnabled(j)) {
                if (this.mTracePrefix == null) {
                    this.mTracePrefix = getClass().getSimpleName();
                }
                Trace.traceBegin(j, this.mTracePrefix + " id=0x" + Integer.toHexString(queuedInputEvent.mEvent.getId()));
            }
        }
    }

    abstract class AsyncInputStage extends InputStage {
        protected static final int DEFER = 3;
        private QueuedInputEvent mQueueHead;
        private int mQueueLength;
        private QueuedInputEvent mQueueTail;
        private final String mTraceCounter;

        public AsyncInputStage(ViewRootImpl viewRootImpl, InputStage inputStage, String str) {
            super(inputStage);
            this.mTraceCounter = str;
        }

        protected void defer(QueuedInputEvent queuedInputEvent) {
            queuedInputEvent.mFlags |= 2;
            enqueue(queuedInputEvent);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void forward(QueuedInputEvent queuedInputEvent) {
            queuedInputEvent.mFlags &= -3;
            QueuedInputEvent queuedInputEvent2 = this.mQueueHead;
            if (queuedInputEvent2 == null) {
                super.forward(queuedInputEvent);
                return;
            }
            int deviceId = queuedInputEvent.mEvent.getDeviceId();
            QueuedInputEvent queuedInputEvent3 = null;
            boolean z = false;
            while (queuedInputEvent2 != null && queuedInputEvent2 != queuedInputEvent) {
                if (!z && deviceId == queuedInputEvent2.mEvent.getDeviceId()) {
                    z = true;
                }
                queuedInputEvent3 = queuedInputEvent2;
                queuedInputEvent2 = queuedInputEvent2.mNext;
            }
            if (z) {
                if (queuedInputEvent2 == null) {
                    enqueue(queuedInputEvent);
                    return;
                }
                return;
            }
            if (queuedInputEvent2 != null) {
                queuedInputEvent2 = queuedInputEvent2.mNext;
                dequeue(queuedInputEvent, queuedInputEvent3);
            }
            super.forward(queuedInputEvent);
            QueuedInputEvent queuedInputEvent4 = queuedInputEvent3;
            while (true) {
                QueuedInputEvent queuedInputEvent5 = queuedInputEvent2;
                while (queuedInputEvent5 != null) {
                    if (deviceId == queuedInputEvent5.mEvent.getDeviceId()) {
                        if ((queuedInputEvent5.mFlags & 2) != 0) {
                            return;
                        }
                        queuedInputEvent2 = queuedInputEvent5.mNext;
                        dequeue(queuedInputEvent5, queuedInputEvent4);
                        super.forward(queuedInputEvent5);
                    } else {
                        QueuedInputEvent queuedInputEvent6 = queuedInputEvent5;
                        queuedInputEvent5 = queuedInputEvent5.mNext;
                        queuedInputEvent4 = queuedInputEvent6;
                    }
                }
                return;
            }
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void apply(QueuedInputEvent queuedInputEvent, int i) {
            if (i == 3) {
                defer(queuedInputEvent);
            } else {
                super.apply(queuedInputEvent, i);
            }
        }

        private void enqueue(QueuedInputEvent queuedInputEvent) {
            QueuedInputEvent queuedInputEvent2 = this.mQueueTail;
            if (queuedInputEvent2 == null) {
                this.mQueueHead = queuedInputEvent;
                this.mQueueTail = queuedInputEvent;
            } else {
                queuedInputEvent2.mNext = queuedInputEvent;
                this.mQueueTail = queuedInputEvent;
            }
            int i = this.mQueueLength + 1;
            this.mQueueLength = i;
            Trace.traceCounter(4L, this.mTraceCounter, i);
        }

        private void dequeue(QueuedInputEvent queuedInputEvent, QueuedInputEvent queuedInputEvent2) {
            if (queuedInputEvent2 == null) {
                this.mQueueHead = queuedInputEvent.mNext;
            } else {
                queuedInputEvent2.mNext = queuedInputEvent.mNext;
            }
            if (this.mQueueTail == queuedInputEvent) {
                this.mQueueTail = queuedInputEvent2;
            }
            queuedInputEvent.mNext = null;
            int i = this.mQueueLength - 1;
            this.mQueueLength = i;
            Trace.traceCounter(4L, this.mTraceCounter, i);
        }

        @Override // android.view.ViewRootImpl.InputStage
        void dump(String str, PrintWriter printWriter) {
            printWriter.print(str);
            printWriter.print(getClass().getName());
            printWriter.print(": mQueueLength=");
            printWriter.println(this.mQueueLength);
            super.dump(str, printWriter);
        }
    }

    final class NativePreImeInputStage extends AsyncInputStage implements InputQueue.FinishedInputEventCallback {
        public NativePreImeInputStage(InputStage inputStage, String str) {
            super(ViewRootImpl.this, inputStage, str);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            if (!queuedInputEvent.forPreImeOnly() && (queuedInputEvent.mEvent instanceof KeyEvent)) {
                KeyEvent keyEvent = (KeyEvent) queuedInputEvent.mEvent;
                if (isBack(keyEvent)) {
                    if (ViewRootImpl.this.mWindowlessBackKeyCallback != null) {
                        if (ViewRootImpl.this.mWindowlessBackKeyCallback.test(keyEvent)) {
                            return (keyEvent.getAction() != 1 || keyEvent.isCanceled()) ? 2 : 1;
                        }
                        return 0;
                    }
                    if (ViewRootImpl.this.mContext != null && ViewRootImpl.this.mOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled()) {
                        return doOnBackKeyEvent(keyEvent);
                    }
                }
                if (ViewRootImpl.this.mInputQueue != null) {
                    ViewRootImpl.this.mInputQueue.sendInputEvent(queuedInputEvent.mEvent, queuedInputEvent, true, this);
                    return 3;
                }
            }
            return 0;
        }

        private int doOnBackKeyEvent(KeyEvent keyEvent) {
            BackEvent backEvent;
            WindowOnBackInvokedDispatcher onBackInvokedDispatcher = ViewRootImpl.this.getOnBackInvokedDispatcher();
            OnBackInvokedCallback topCallback = onBackInvokedDispatcher.getTopCallback();
            if (onBackInvokedDispatcher.isBackGestureInProgress()) {
                return 2;
            }
            if ((topCallback instanceof OnBackAnimationCallback) && !(topCallback instanceof ImeBackAnimationController)) {
                OnBackAnimationCallback onBackAnimationCallback = (OnBackAnimationCallback) topCallback;
                int action = keyEvent.getAction();
                if (action != 0) {
                    if (action == 1) {
                        if (keyEvent.isCanceled()) {
                            onBackAnimationCallback.onBackCancelled();
                        } else {
                            onBackInvokedDispatcher.tryInvokeSystemNavigationObserverCallback();
                            topCallback.onBackInvoked();
                        }
                    }
                } else if (keyEvent.getRepeatCount() == 0) {
                    if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.predictiveBackSwipeEdgeNoneApi()) {
                        backEvent = new BackEvent(0.0f, 0.0f, 0.0f, 2);
                    } else {
                        backEvent = new BackEvent(0.0f, 0.0f, 0.0f, 0);
                    }
                    onBackAnimationCallback.onBackStarted(backEvent);
                }
            } else if (topCallback != null && keyEvent.getAction() == 1) {
                if (!keyEvent.isCanceled()) {
                    onBackInvokedDispatcher.tryInvokeSystemNavigationObserverCallback();
                    topCallback.onBackInvoked();
                } else {
                    Log.d(ViewRootImpl.this.mTag, "Skip onBackInvoked(), reason: keyEvent.isCanceled=true");
                }
            }
            if (keyEvent.getAction() == 1) {
                keyEvent.cancel();
            }
            return 0;
        }

        @Override // android.view.InputQueue.FinishedInputEventCallback
        public void onFinishedInputEvent(Object obj, boolean z) {
            QueuedInputEvent queuedInputEvent = (QueuedInputEvent) obj;
            if (z) {
                finish(queuedInputEvent, true);
            } else {
                forward(queuedInputEvent);
            }
        }
    }

    final class ViewPreImeInputStage extends InputStage {
        public ViewPreImeInputStage(InputStage inputStage) {
            super(inputStage);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            if (queuedInputEvent.mEvent instanceof KeyEvent) {
                return processKeyEvent(queuedInputEvent);
            }
            return 0;
        }

        private int processKeyEvent(QueuedInputEvent queuedInputEvent) {
            KeyEvent keyEvent = (KeyEvent) queuedInputEvent.mEvent;
            if ((ViewRune.WIDGET_PEN_SUPPORTED && ViewRootImpl.this.mView.dispatchKeyEventTextMultiSelection(keyEvent)) || ViewRootImpl.this.mView.dispatchKeyEventPreIme(keyEvent)) {
                return 1;
            }
            return queuedInputEvent.forPreImeOnly() ? 2 : 0;
        }
    }

    final class ImeInputStage extends AsyncInputStage implements InputMethodManager.FinishedInputEventCallback {
        public ImeInputStage(InputStage inputStage, String str) {
            super(ViewRootImpl.this, inputStage, str);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            int onProcessImeInputStage = ViewRootImpl.this.mImeFocusController.onProcessImeInputStage(queuedInputEvent, queuedInputEvent.mEvent, ViewRootImpl.this.mWindowAttributes, this);
            if (onProcessImeInputStage == -1) {
                return 3;
            }
            if (onProcessImeInputStage == 0) {
                return 0;
            }
            if (onProcessImeInputStage == 1) {
                return 1;
            }
            throw new IllegalStateException("Unexpected result=" + onProcessImeInputStage);
        }

        @Override // android.view.inputmethod.InputMethodManager.FinishedInputEventCallback
        public void onFinishedInputEvent(Object obj, boolean z) {
            QueuedInputEvent queuedInputEvent = (QueuedInputEvent) obj;
            if (z) {
                finish(queuedInputEvent, true);
                Log.i(ViewRootImpl.this.mTag, "The input has been finished in ImeInputStage.");
            } else {
                forward(queuedInputEvent);
            }
        }
    }

    final class EarlyPostImeInputStage extends InputStage {
        public EarlyPostImeInputStage(InputStage inputStage) {
            super(inputStage);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            if (queuedInputEvent.mEvent instanceof KeyEvent) {
                return processKeyEvent(queuedInputEvent);
            }
            if (queuedInputEvent.mEvent instanceof MotionEvent) {
                return processMotionEvent(queuedInputEvent);
            }
            return 0;
        }

        private int processKeyEvent(QueuedInputEvent queuedInputEvent) {
            KeyEvent keyEvent = (KeyEvent) queuedInputEvent.mEvent;
            if (ViewRootImpl.this.mAttachInfo.mTooltipHost != null) {
                ViewRootImpl.this.mAttachInfo.mTooltipHost.handleTooltipKey(keyEvent);
            }
            if (ViewRootImpl.this.checkForLeavingTouchModeAndConsume(keyEvent)) {
                return 1;
            }
            ViewRootImpl.this.mFallbackEventHandler.preDispatchKeyEvent(keyEvent);
            if (keyEvent.getAction() == 0) {
                ViewRootImpl.this.mLastClickToolType = 0;
            }
            return 0;
        }

        private int processMotionEvent(QueuedInputEvent queuedInputEvent) {
            MotionEvent motionEvent = (MotionEvent) queuedInputEvent.mEvent;
            if (motionEvent.isFromSource(2)) {
                return processPointerEvent(queuedInputEvent);
            }
            int actionMasked = motionEvent.getActionMasked();
            if ((actionMasked == 0 || actionMasked == 8) && motionEvent.isFromSource(8)) {
                ViewRootImpl.this.ensureTouchMode(false);
            }
            return 0;
        }

        private int processPointerEvent(QueuedInputEvent queuedInputEvent) {
            AutofillManager autofillManager;
            MotionEvent motionEvent = (MotionEvent) queuedInputEvent.mEvent;
            if (ViewRootImpl.this.mTranslator != null) {
                ViewRootImpl.this.mTranslator.translateEventInScreenToAppWindow(motionEvent);
            }
            int action = motionEvent.getAction();
            if (action == 0 || action == 8) {
                ViewRootImpl.this.ensureTouchMode(true);
            }
            if (action == 0 && (autofillManager = ViewRootImpl.this.getAutofillManager()) != null) {
                autofillManager.requestHideFillUi();
            }
            if (action == 0 && ViewRootImpl.this.mAttachInfo.mTooltipHost != null) {
                ViewRootImpl.this.mAttachInfo.mTooltipHost.hideTooltip();
            }
            if (ViewRootImpl.this.mCurScrollY != 0) {
                motionEvent.offsetLocation(0.0f, ViewRootImpl.this.mCurScrollY);
            }
            if (motionEvent.isTouchEvent()) {
                ViewRootImpl.this.mLastTouchPoint.x = motionEvent.getRawX();
                ViewRootImpl.this.mLastTouchPoint.y = motionEvent.getRawY();
                ViewRootImpl.this.mLastTouchSource = motionEvent.getSource();
                ViewRootImpl.this.mLastTouchDeviceId = motionEvent.getDeviceId();
                ViewRootImpl.this.mLastTouchPointerId = motionEvent.getPointerId(0);
                if (motionEvent.getActionMasked() == 1) {
                    ViewRootImpl.this.mLastClickToolType = motionEvent.getToolType(motionEvent.getActionIndex());
                }
            }
            return 0;
        }
    }

    final class NativePostImeInputStage extends AsyncInputStage implements InputQueue.FinishedInputEventCallback {
        public NativePostImeInputStage(InputStage inputStage, String str) {
            super(ViewRootImpl.this, inputStage, str);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            if (ViewRootImpl.this.mInputQueue == null) {
                return 0;
            }
            ViewRootImpl.this.mInputQueue.sendInputEvent(queuedInputEvent.mEvent, queuedInputEvent, false, this);
            return 3;
        }

        @Override // android.view.InputQueue.FinishedInputEventCallback
        public void onFinishedInputEvent(Object obj, boolean z) {
            QueuedInputEvent queuedInputEvent = (QueuedInputEvent) obj;
            if (z) {
                finish(queuedInputEvent, true);
            } else {
                forward(queuedInputEvent);
            }
        }
    }

    final class ViewPostImeInputStage extends InputStage {
        public ViewPostImeInputStage(InputStage inputStage) {
            super(inputStage);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            if (queuedInputEvent.mEvent instanceof KeyEvent) {
                return processKeyEvent(queuedInputEvent);
            }
            int source = queuedInputEvent.mEvent.getSource();
            if ((source & 2) != 0) {
                return processPointerEvent(queuedInputEvent);
            }
            if ((source & 4) != 0) {
                return processTrackballEvent(queuedInputEvent);
            }
            return processGenericMotionEvent(queuedInputEvent);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onDeliverToNext(QueuedInputEvent queuedInputEvent) {
            if (ViewRootImpl.this.mUnbufferedInputDispatch && (queuedInputEvent.mEvent instanceof MotionEvent) && ((MotionEvent) queuedInputEvent.mEvent).isTouchEvent() && ViewRootImpl.isTerminalInputEvent(queuedInputEvent.mEvent)) {
                ViewRootImpl.this.mUnbufferedInputDispatch = false;
                ViewRootImpl.this.scheduleConsumeBatchedInput();
            }
            super.onDeliverToNext(queuedInputEvent);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private boolean performFocusNavigation(KeyEvent keyEvent) {
            int i;
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 61) {
                switch (keyCode) {
                    case 19:
                        if (keyEvent.hasNoModifiers()) {
                            i = 33;
                            break;
                        }
                        i = 0;
                        break;
                    case 20:
                        if (keyEvent.hasNoModifiers()) {
                            i = 130;
                            break;
                        }
                        i = 0;
                        break;
                    case 21:
                        if (keyEvent.hasNoModifiers()) {
                            i = 17;
                            break;
                        }
                        i = 0;
                        break;
                    case 22:
                        if (keyEvent.hasNoModifiers()) {
                            i = 66;
                            break;
                        }
                        i = 0;
                        break;
                    default:
                        i = 0;
                        break;
                }
            } else if (keyEvent.hasNoModifiers()) {
                i = 2;
            } else {
                if (keyEvent.hasModifiers(1)) {
                    i = 1;
                }
                i = 0;
            }
            if (i != 0) {
                View findFocus = ViewRootImpl.this.mView.findFocus();
                if (findFocus != null) {
                    ViewRootImpl.this.mAttachInfo.mNextFocusLooped = false;
                    View focusSearch = findFocus.focusSearch(i);
                    if (focusSearch != null && focusSearch != findFocus) {
                        if (ViewRootImpl.this.mAttachInfo.mNextFocusLooped) {
                            moveFocusToAdjacentWindow(i);
                        }
                        findFocus.getFocusedRect(ViewRootImpl.this.mTempRect);
                        if (ViewRootImpl.this.mView instanceof ViewGroup) {
                            ((ViewGroup) ViewRootImpl.this.mView).offsetDescendantRectToMyCoords(findFocus, ViewRootImpl.this.mTempRect);
                            ((ViewGroup) ViewRootImpl.this.mView).offsetRectIntoDescendantCoords(focusSearch, ViewRootImpl.this.mTempRect);
                        }
                        if (focusSearch.requestFocus(i, ViewRootImpl.this.mTempRect)) {
                            ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getConstantForFocusDirection(i, keyEvent.getRepeatCount() > 0));
                            return true;
                        }
                    } else if (moveFocusToAdjacentWindow(i)) {
                        return true;
                    }
                    if (ViewRootImpl.this.mView.dispatchUnhandledMove(findFocus, i)) {
                        return true;
                    }
                } else if (ViewRootImpl.this.mView.restoreDefaultFocus() || moveFocusToAdjacentWindow(i)) {
                    return true;
                }
            }
            return false;
        }

        private boolean moveFocusToAdjacentWindow(int i) {
            int windowingMode = ViewRootImpl.this.getConfiguration().windowConfiguration.getWindowingMode();
            if (windowingMode != 6 && windowingMode != 5) {
                return false;
            }
            try {
                return ViewRootImpl.this.mWindowSession.moveFocusToAdjacentWindow(ViewRootImpl.this.mWindow, i);
            } catch (RemoteException unused) {
                return false;
            }
        }

        private boolean performKeyboardGroupNavigation(int i) {
            View keyboardNavigationClusterSearch;
            View findFocus = ViewRootImpl.this.mView.findFocus();
            if (findFocus == null && ViewRootImpl.this.mView.restoreDefaultFocus()) {
                return true;
            }
            if (findFocus == null) {
                keyboardNavigationClusterSearch = ViewRootImpl.this.keyboardNavigationClusterSearch(null, i);
            } else {
                keyboardNavigationClusterSearch = findFocus.keyboardNavigationClusterSearch(null, i);
            }
            int i2 = (i == 2 || i == 1) ? 130 : i;
            if (keyboardNavigationClusterSearch != null && keyboardNavigationClusterSearch.isRootNamespace()) {
                if (keyboardNavigationClusterSearch.restoreFocusNotInCluster()) {
                    ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
                    return true;
                }
                keyboardNavigationClusterSearch = ViewRootImpl.this.keyboardNavigationClusterSearch(null, i);
            }
            if (keyboardNavigationClusterSearch == null || !keyboardNavigationClusterSearch.restoreFocusInCluster(i2)) {
                return false;
            }
            ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:41:0x00c9 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00ca  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x00db A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x00dc  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private int processKeyEvent(android.view.ViewRootImpl.QueuedInputEvent r7) {
            /*
                Method dump skipped, instructions count: 250
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.ViewPostImeInputStage.processKeyEvent(android.view.ViewRootImpl$QueuedInputEvent):int");
        }

        private int processPointerEvent(QueuedInputEvent queuedInputEvent) {
            MotionEvent motionEvent = (MotionEvent) queuedInputEvent.mEvent;
            int action = motionEvent.getAction();
            if (action == 1 || action == 3) {
                ViewRootImpl.this.mIsPressedGesture = false;
            }
            if (ViewRootImpl.this.mMotionEventMonitor != null) {
                ViewRootImpl.this.mMotionEventMonitor.dispatchInputEvent(queuedInputEvent.mEvent);
            }
            boolean onTouchEvent = (com.android.text.flags.Flags.disableHandwritingInitiatorForIme() && ViewRootImpl.this.mWindowAttributes.type == 2011) ? false : ViewRootImpl.this.mHandwritingInitiator.onTouchEvent(motionEvent);
            if (onTouchEvent) {
                ViewRootImpl.this.mLastClickToolType = motionEvent.getToolType(motionEvent.getActionIndex());
            }
            if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
                Log.i(ViewRootImpl.this.mTag, "ViewPostIme pointer " + action);
            } else if (action == 0 || action == 1 || (ViewRune.COMMON_IS_PRODUCT_DEV && action != 2 && action != 7 && action != 213)) {
                Log.i(ViewRootImpl.this.mTag, "ViewPostIme pointer " + action);
            }
            ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested = false;
            ViewRootImpl.this.mAttachInfo.mHandlingPointerEvent = true;
            int i = (onTouchEvent || ViewRootImpl.this.mView.dispatchPointerEvent(motionEvent)) ? 1 : 0;
            maybeUpdatePointerIcon(motionEvent);
            ViewRootImpl.this.maybeUpdateTooltip(motionEvent);
            ViewRootImpl.this.mAttachInfo.mHandlingPointerEvent = false;
            if (ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested && !ViewRootImpl.this.mUnbufferedInputDispatch) {
                ViewRootImpl.this.mUnbufferedInputDispatch = true;
                if (ViewRootImpl.this.mConsumeBatchedInputScheduled) {
                    ViewRootImpl.this.scheduleConsumeBatchedInputImmediately();
                }
            }
            if (i != 0) {
                ViewRootImpl viewRootImpl = ViewRootImpl.this;
                if (viewRootImpl.shouldTouchBoost(action & 255, viewRootImpl.mWindowAttributes.type)) {
                    ViewRootImpl.this.mIsTouchBoosting = true;
                    if (action == 0) {
                        ViewRootImpl.this.mIsPressedGesture = true;
                    }
                    ViewRootImpl.this.setPreferredFrameRateCategory(CoreRune.FW_DVRR_TOOLKIT_PRIORITIZE_HIGH_HINT ? 5 : ViewRootImpl.this.mLastPreferredFrameRateCategory);
                }
            }
            if (ViewRootImpl.this.mIsTouchBoosting && (action == 1 || action == 3)) {
                if (ViewRootImpl.sToolkitInitialTouchBoostFlagValue) {
                    ViewRootImpl.this.mHandler.removeMessages(44);
                    ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(44, 30L);
                } else {
                    ViewRootImpl.this.mHandler.removeMessages(39);
                    ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(39, 3000L);
                }
            }
            if (CoreRune.FW_VRR_SEND_TOUCH_HINT) {
                if (i != 0) {
                    ViewRootImpl viewRootImpl2 = ViewRootImpl.this;
                    if (viewRootImpl2.shouldTouchHint(action & 255, viewRootImpl2.mWindowAttributes.type)) {
                        ViewRootImpl.this.mIsTouchHint = true;
                        ViewRootImpl.this.setFrameRateCategoryForTouchHint(5);
                    }
                }
                if (ViewRootImpl.this.mIsTouchHint && (action == 1 || action == 3)) {
                    ViewRootImpl.this.mHandler.removeMessages(106);
                    ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(106, 3000L);
                }
            }
            return i;
        }

        private void maybeUpdatePointerIcon(MotionEvent motionEvent) {
            if (motionEvent.getPointerCount() != 1) {
                return;
            }
            int actionMasked = motionEvent.getActionMasked();
            if (!motionEvent.isStylusPointer() ? !motionEvent.isHoverEvent() || !ViewRootImpl.this.mIsStylusPointerIconEnabled : !motionEvent.isHoverEvent() && motionEvent.getActionMasked() != 0) {
                if (!motionEvent.isFromSource(8194)) {
                    return;
                }
            }
            if (actionMasked == 9 || actionMasked == 10) {
                ViewRootImpl.this.mResolvedPointerIcon = null;
            }
            if (actionMasked != 10 && actionMasked != 4 && !ViewRootImpl.this.updatePointerIcon(motionEvent) && actionMasked == 7) {
                ViewRootImpl.this.mResolvedPointerIcon = null;
            }
            if (actionMasked == 1 || actionMasked == 3 || actionMasked == 6 || actionMasked == 10) {
                if (ViewRootImpl.this.mPointerIconEvent != null) {
                    ViewRootImpl.this.mPointerIconEvent.recycle();
                }
                ViewRootImpl.this.mPointerIconEvent = null;
            } else {
                ViewRootImpl.this.mPointerIconEvent = MotionEvent.obtain(motionEvent);
            }
        }

        private int processTrackballEvent(QueuedInputEvent queuedInputEvent) {
            MotionEvent motionEvent = (MotionEvent) queuedInputEvent.mEvent;
            return ((!motionEvent.isFromSource(131076) || (ViewRootImpl.this.hasPointerCapture() && !ViewRootImpl.this.mView.dispatchCapturedPointerEvent(motionEvent))) && !ViewRootImpl.this.mView.dispatchTrackballEvent(motionEvent)) ? 0 : 1;
        }

        private int processGenericMotionEvent(QueuedInputEvent queuedInputEvent) {
            MotionEvent motionEvent = (MotionEvent) queuedInputEvent.mEvent;
            return ((motionEvent.isFromSource(1048584) && ViewRootImpl.this.hasPointerCapture() && ViewRootImpl.this.mView.dispatchCapturedPointerEvent(motionEvent)) || ViewRootImpl.this.mView.dispatchGenericMotionEvent(motionEvent)) ? 1 : 0;
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

    /* JADX INFO: Access modifiers changed from: private */
    public void windowFocusInTaskChanged(boolean z) {
        synchronized (this) {
            this.mWindowFocusInTaskChanged = true;
            this.mUpcomingWindowFocusInTask = z;
        }
        Message obtain = Message.obtain();
        obtain.what = 105;
        this.mHandler.sendMessage(obtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWindowFocusInTaskChanged() {
        synchronized (this) {
            if (this.mWindowFocusInTaskChanged) {
                this.mWindowFocusInTaskChanged = false;
                boolean z = this.mUpcomingWindowFocusInTask;
                View view = this.mView;
                if (view instanceof DecorView) {
                    ((DecorView) view).onWindowFocusInTaskChanged(z);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updatePointerIcon(MotionEvent motionEvent) {
        float x = motionEvent.getX(0);
        float y = motionEvent.getY(0);
        if (this.mView == null) {
            Slog.d(this.mTag, "updatePointerIcon called after view was removed");
            return false;
        }
        if (x < 0.0f || x >= r3.getWidth() || y < 0.0f || y >= this.mView.getHeight()) {
            Slog.d(this.mTag, "updatePointerIcon called with position out of bounds");
            return false;
        }
        int toolType = motionEvent.getToolType(0);
        boolean z = (motionEvent.getFlags() & 67108864) != 0;
        boolean z2 = toolType == 2;
        boolean z3 = z2 && z;
        InputManagerGlobal.getInstance().setIsStylusFromTouchpad(z3);
        PointerIcon onResolvePointerIcon = (motionEvent.isStylusPointer() && this.mIsStylusPointerIconEnabled && (!com.android.text.flags.Flags.disableHandwritingInitiatorForIme() || this.mWindowAttributes.type != 2011) && !z3) ? this.mHandwritingInitiator.onResolvePointerIcon(this.mContext, motionEvent) : null;
        if (onResolvePointerIcon == null) {
            onResolvePointerIcon = this.mView.onResolvePointerIcon(motionEvent, 0);
        }
        boolean z4 = onResolvePointerIcon == null;
        if (!z4) {
            int type = onResolvePointerIcon.getType();
            if (z2) {
                if (z) {
                    if (type > 20000) {
                        int mappingToMousePointer = mappingToMousePointer(type);
                        onResolvePointerIcon = PointerIcon.getSystemIcon(this.mContext, mappingToMousePointer);
                        Log.d(this.mTag, "mapping pointerIcon because of mIsStylusFromTouchpad (" + type + " => " + mappingToMousePointer + NavigationBarInflaterView.KEY_CODE_END);
                    }
                } else if (type == 1000) {
                    onResolvePointerIcon = PointerIcon.getSystemIcon(this.mContext, 20001);
                    z4 = true;
                }
            } else if (type == 20001) {
                onResolvePointerIcon = PointerIcon.getSystemIcon(this.mContext, 1000);
                z4 = true;
            }
        }
        if (z4) {
            try {
                if (this.mInputManagerService.isDefaultPointerIconChanged() && toolType == this.mInputManagerService.getToolTypeForDefaultPointerIcon()) {
                    onResolvePointerIcon = this.mInputManagerService.getDefaultPointerIcon();
                }
            } catch (Exception e) {
                Log.e(this.mTag, "failed to check default pointer icon", e);
            }
        }
        if (onResolvePointerIcon == null) {
            onResolvePointerIcon = PointerIcon.getSystemIcon(this.mContext, 1);
        }
        PointerIcon pointerIcon = onResolvePointerIcon;
        if (Objects.equals(this.mResolvedPointerIcon, pointerIcon)) {
            return true;
        }
        Log.i(this.mTag, "updatePointerIcon : " + pointerIcon);
        this.mResolvedPointerIcon = pointerIcon;
        InputManagerGlobal.getInstance().setPointerIcon(pointerIcon, motionEvent.getDisplayId(), motionEvent.getDeviceId(), motionEvent.getPointerId(0), getInputToken());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeUpdateTooltip(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 1) {
            return;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9 || actionMasked == 7 || actionMasked == 10) {
            if (this.mAccessibilityManager.isEnabled() && this.mAccessibilityManager.isTouchExplorationEnabled()) {
                return;
            }
            View view = this.mView;
            if (view == null) {
                Slog.d(this.mTag, "maybeUpdateTooltip called after view was removed");
            } else {
                view.dispatchTooltipHoverEvent(motionEvent);
            }
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
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            queuedInputEvent.mFlags |= 16;
            if (queuedInputEvent.mEvent instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent) queuedInputEvent.mEvent;
                int source = motionEvent.getSource();
                if ((source & 4) != 0) {
                    if (!motionEvent.isFromSource(131076)) {
                        this.mTrackball.process(motionEvent);
                    }
                    return 1;
                }
                if ((source & 16) != 0) {
                    this.mJoystick.process(motionEvent);
                    return 1;
                }
                if ((source & 2097152) != 2097152) {
                    return 0;
                }
                this.mTouchNavigation.process(motionEvent);
                return 1;
            }
            if ((queuedInputEvent.mFlags & 32) == 0) {
                return 0;
            }
            this.mKeyboard.process((KeyEvent) queuedInputEvent.mEvent);
            return 1;
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onDeliverToNext(QueuedInputEvent queuedInputEvent) {
            if ((queuedInputEvent.mFlags & 16) == 0 && (queuedInputEvent.mEvent instanceof MotionEvent)) {
                int source = ((MotionEvent) queuedInputEvent.mEvent).getSource();
                if ((source & 4) != 0) {
                    this.mTrackball.cancel();
                } else if ((source & 16) != 0) {
                    this.mJoystick.cancel();
                }
            }
            super.onDeliverToNext(queuedInputEvent);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onWindowFocusChanged(boolean z) {
            if (z) {
                return;
            }
            this.mJoystick.cancel();
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

        SyntheticTrackballHandler() {
        }

        public void process(MotionEvent motionEvent) {
            int i;
            int i2;
            int generate;
            int i3;
            long uptimeMillis = SystemClock.uptimeMillis();
            if (this.mLastTime + 250 < uptimeMillis) {
                this.mX.reset(0);
                this.mY.reset(0);
                this.mLastTime = uptimeMillis;
            }
            int action = motionEvent.getAction();
            int metaState = motionEvent.getMetaState();
            if (action == 0) {
                this.mX.reset(2);
                this.mY.reset(2);
                ViewRootImpl.this.enqueueInputEvent(new KeyEvent(uptimeMillis, uptimeMillis, 0, 23, 0, metaState, -1, 0, 1024, 257));
            } else if (action == 1) {
                this.mX.reset(2);
                this.mY.reset(2);
                ViewRootImpl.this.enqueueInputEvent(new KeyEvent(uptimeMillis, uptimeMillis, 1, 23, 0, metaState, -1, 0, 1024, 257));
            }
            if (ViewRootImpl.DEBUG_TRACKBALL) {
                Log.v(ViewRootImpl.this.mTag, "TB X=" + this.mX.position + " step=" + this.mX.step + " dir=" + this.mX.dir + " acc=" + this.mX.acceleration + " move=" + motionEvent.getX() + " / Y=" + this.mY.position + " step=" + this.mY.step + " dir=" + this.mY.dir + " acc=" + this.mY.acceleration + " move=" + motionEvent.getY());
            }
            float collect = this.mX.collect(motionEvent.getX(), motionEvent.getEventTime(), GnssSignalType.CODE_TYPE_X);
            float collect2 = this.mY.collect(motionEvent.getY(), motionEvent.getEventTime(), GnssSignalType.CODE_TYPE_Y);
            float f = 1.0f;
            if (collect > collect2) {
                generate = this.mX.generate();
                if (generate != 0) {
                    i3 = generate > 0 ? 22 : 21;
                    f = this.mX.acceleration;
                    this.mY.reset(2);
                    i = i3;
                    i2 = generate;
                }
                i2 = generate;
                i = 0;
            } else if (collect2 > 0.0f) {
                generate = this.mY.generate();
                if (generate != 0) {
                    i3 = generate > 0 ? 20 : 19;
                    f = this.mY.acceleration;
                    this.mX.reset(2);
                    i = i3;
                    i2 = generate;
                }
                i2 = generate;
                i = 0;
            } else {
                i = 0;
                i2 = 0;
            }
            if (i != 0) {
                if (i2 < 0) {
                    i2 = -i2;
                }
                int i4 = (int) (i2 * f);
                if (ViewRootImpl.DEBUG_TRACKBALL) {
                    Log.v(ViewRootImpl.this.mTag, "Move: movement=" + i2 + " accelMovement=" + i4 + " accel=" + f);
                }
                String str = "Delivering fake DPAD: ";
                if (i4 > i2) {
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.v(ViewRootImpl.this.mTag, "Delivering fake DPAD: " + i);
                    }
                    int i5 = i2 - 1;
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(uptimeMillis, uptimeMillis, 2, i, i4 - i5, metaState, -1, 0, 1024, 257));
                    i2 = i5;
                }
                while (i2 > 0) {
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.v(ViewRootImpl.this.mTag, str + i);
                    }
                    i2--;
                    long uptimeMillis2 = SystemClock.uptimeMillis();
                    int i6 = i;
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(uptimeMillis2, uptimeMillis2, 0, i6, 0, metaState, -1, 0, 1024, 257));
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(uptimeMillis2, uptimeMillis2, 1, i6, 0, metaState, -1, 0, 1024, 257));
                    str = str;
                    uptimeMillis = uptimeMillis2;
                }
                this.mLastTime = uptimeMillis;
            }
        }

        public void cancel() {
            this.mLastTime = -2147483648L;
            if (ViewRootImpl.this.mView == null || !ViewRootImpl.this.mAdded) {
                return;
            }
            ViewRootImpl.this.ensureTouchMode(false);
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

        void reset(int i) {
            this.position = 0.0f;
            this.acceleration = 1.0f;
            this.lastMoveTime = 0L;
            this.step = i;
            this.dir = 0;
        }

        float collect(float f, long j, String str) {
            long j2;
            if (f > 0.0f) {
                j2 = (long) (f * 150.0f);
                if (this.dir < 0) {
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.v(ViewRootImpl.TAG, str + " reversed to positive!");
                    }
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0L;
                }
                this.dir = 1;
            } else if (f < 0.0f) {
                j2 = (long) ((-f) * 150.0f);
                if (this.dir > 0) {
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.v(ViewRootImpl.TAG, str + " reversed to negative!");
                    }
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0L;
                }
                this.dir = -1;
            } else {
                j2 = 0;
            }
            if (j2 > 0) {
                long j3 = j - this.lastMoveTime;
                this.lastMoveTime = j;
                float f2 = this.acceleration;
                if (j3 < j2) {
                    long j4 = j2;
                    float f3 = (j4 - j3) * ACCEL_MOVE_SCALING_FACTOR;
                    if (f3 > 1.0f) {
                        f2 *= f3;
                    }
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.v(ViewRootImpl.TAG, str + " accelerate: off=" + f + " normTime=" + j4 + " delta=" + j3 + " scale=" + f3 + " acc=" + f2);
                    }
                    if (f2 >= MAX_ACCELERATION) {
                        f2 = 20.0f;
                    }
                    this.acceleration = f2;
                } else {
                    float f4 = (j3 - j2) * ACCEL_MOVE_SCALING_FACTOR;
                    if (f4 > 1.0f) {
                        f2 /= f4;
                    }
                    if (ViewRootImpl.DEBUG_TRACKBALL) {
                        Log.v(ViewRootImpl.TAG, str + " deccelerate: off=" + f + " normTime=" + j2 + " delta=" + j3 + " scale=" + f4 + " acc=" + f2);
                    }
                    this.acceleration = f2 > 1.0f ? f2 : 1.0f;
                }
            }
            float f5 = this.position + f;
            this.position = f5;
            return Math.abs(f5);
        }

        int generate() {
            int i = 0;
            this.nonAccelMovement = 0;
            while (true) {
                float f = this.position;
                int i2 = f >= 0.0f ? 1 : -1;
                int i3 = this.step;
                if (i3 != 0) {
                    if (i3 != 1) {
                        if (Math.abs(f) < 1.0f) {
                            break;
                        }
                        i += i2;
                        this.position -= i2 * 1.0f;
                        float f2 = this.acceleration;
                        float f3 = 1.1f * f2;
                        if (f3 < MAX_ACCELERATION) {
                            f2 = f3;
                        }
                        this.acceleration = f2;
                    } else {
                        if (Math.abs(f) < 2.0f) {
                            break;
                        }
                        i += i2;
                        this.nonAccelMovement += i2;
                        this.position -= i2 * 2.0f;
                        this.step = 2;
                    }
                } else {
                    if (Math.abs(f) < 0.5f) {
                        break;
                    }
                    i += i2;
                    this.nonAccelMovement += i2;
                    this.step = 1;
                }
            }
            return i;
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
        public void handleMessage(Message message) {
            int i = message.what;
            if ((i == 1 || i == 2) && ViewRootImpl.this.mAttachInfo.mHasWindowFocus) {
                KeyEvent keyEvent = (KeyEvent) message.obj;
                KeyEvent changeTimeRepeat = KeyEvent.changeTimeRepeat(keyEvent, SystemClock.uptimeMillis(), keyEvent.getRepeatCount() + 1);
                ViewRootImpl.this.enqueueInputEvent(changeTimeRepeat);
                Message obtainMessage = obtainMessage(message.what, changeTimeRepeat);
                obtainMessage.setAsynchronous(true);
                sendMessageDelayed(obtainMessage, ViewConfiguration.getKeyRepeatDelay());
            }
        }

        public void process(MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 2) {
                update(motionEvent);
                return;
            }
            if (actionMasked == 3) {
                cancel();
                return;
            }
            Log.w(ViewRootImpl.this.mTag, "Unexpected action: " + motionEvent.getActionMasked());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cancel() {
            removeMessages(1);
            removeMessages(2);
            for (int i = 0; i < this.mDeviceKeyEvents.size(); i++) {
                KeyEvent valueAt = this.mDeviceKeyEvents.valueAt(i);
                if (valueAt != null) {
                    ViewRootImpl.this.enqueueInputEvent(KeyEvent.changeTimeRepeat(valueAt, SystemClock.uptimeMillis(), 0));
                }
            }
            this.mDeviceKeyEvents.clear();
            this.mJoystickAxesState.resetState();
        }

        private void update(MotionEvent motionEvent) {
            int historySize = motionEvent.getHistorySize();
            for (int i = 0; i < historySize; i++) {
                long historicalEventTime = motionEvent.getHistoricalEventTime(i);
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 0, motionEvent.getHistoricalAxisValue(0, 0, i));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 1, motionEvent.getHistoricalAxisValue(1, 0, i));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 15, motionEvent.getHistoricalAxisValue(15, 0, i));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 16, motionEvent.getHistoricalAxisValue(16, 0, i));
            }
            long eventTime = motionEvent.getEventTime();
            this.mJoystickAxesState.updateStateForAxis(motionEvent, eventTime, 0, motionEvent.getAxisValue(0));
            this.mJoystickAxesState.updateStateForAxis(motionEvent, eventTime, 1, motionEvent.getAxisValue(1));
            this.mJoystickAxesState.updateStateForAxis(motionEvent, eventTime, 15, motionEvent.getAxisValue(15));
            this.mJoystickAxesState.updateStateForAxis(motionEvent, eventTime, 16, motionEvent.getAxisValue(16));
        }

        final class JoystickAxesState {
            private static final int STATE_DOWN_OR_RIGHT = 1;
            private static final int STATE_NEUTRAL = 0;
            private static final int STATE_UP_OR_LEFT = -1;
            final int[] mAxisStatesHat = {0, 0};
            final int[] mAxisStatesStick = {0, 0};

            private boolean isXAxis(int i) {
                return i == 0 || i == 15;
            }

            private boolean isYAxis(int i) {
                return i == 1 || i == 16;
            }

            private int joystickAxisValueToState(float f) {
                if (f >= 0.5f) {
                    return 1;
                }
                return f <= -0.5f ? -1 : 0;
            }

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

            void updateStateForAxis(MotionEvent motionEvent, long j, int i, float f) {
                int i2;
                char c;
                int i3;
                int i4;
                int joystickAxisAndStateToKeycode;
                if (isXAxis(i)) {
                    c = 0;
                    i2 = 1;
                } else {
                    if (!isYAxis(i)) {
                        Log.e(ViewRootImpl.this.mTag, "Unexpected axis " + i + " in updateStateForAxis!");
                        return;
                    }
                    i2 = 2;
                    c = 1;
                }
                int joystickAxisValueToState = joystickAxisValueToState(f);
                if (i == 0 || i == 1) {
                    i3 = this.mAxisStatesStick[c];
                } else {
                    i3 = this.mAxisStatesHat[c];
                }
                if (i3 == joystickAxisValueToState) {
                    return;
                }
                int metaState = motionEvent.getMetaState();
                int deviceId = motionEvent.getDeviceId();
                int source = motionEvent.getSource();
                if (i3 == 1 || i3 == -1) {
                    int joystickAxisAndStateToKeycode2 = joystickAxisAndStateToKeycode(i, i3);
                    if (joystickAxisAndStateToKeycode2 != 0) {
                        i4 = deviceId;
                        ViewRootImpl.this.enqueueInputEvent(new KeyEvent(j, j, 1, joystickAxisAndStateToKeycode2, 0, metaState, deviceId, 0, 1024, source));
                        SyntheticJoystickHandler.this.mDeviceKeyEvents.put(i4, null);
                    } else {
                        i4 = deviceId;
                    }
                    SyntheticJoystickHandler.this.removeMessages(i2);
                } else {
                    i4 = deviceId;
                }
                if ((joystickAxisValueToState == 1 || joystickAxisValueToState == -1) && (joystickAxisAndStateToKeycode = joystickAxisAndStateToKeycode(i, joystickAxisValueToState)) != 0) {
                    int i5 = i4;
                    KeyEvent keyEvent = new KeyEvent(j, j, 0, joystickAxisAndStateToKeycode, 0, metaState, i5, 0, 1024, source);
                    ViewRootImpl.this.enqueueInputEvent(keyEvent);
                    Message obtainMessage = SyntheticJoystickHandler.this.obtainMessage(i2, keyEvent);
                    obtainMessage.setAsynchronous(true);
                    SyntheticJoystickHandler.this.sendMessageDelayed(obtainMessage, ViewConfiguration.getKeyRepeatTimeout());
                    SyntheticJoystickHandler.this.mDeviceKeyEvents.put(i5, new KeyEvent(j, j, 1, joystickAxisAndStateToKeycode, 0, metaState, i5, 0, 1056, source));
                }
                if (i == 0 || i == 1) {
                    this.mAxisStatesStick[c] = joystickAxisValueToState;
                } else {
                    this.mAxisStatesHat[c] = joystickAxisValueToState;
                }
            }

            private int joystickAxisAndStateToKeycode(int i, int i2) {
                if (isXAxis(i) && i2 == -1) {
                    return 21;
                }
                if (isXAxis(i) && i2 == 1) {
                    return 22;
                }
                if (isYAxis(i) && i2 == -1) {
                    return 19;
                }
                if (isYAxis(i) && i2 == 1) {
                    return 20;
                }
                Log.e(ViewRootImpl.this.mTag, "Unknown axis " + i + " or direction " + i2);
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
                public boolean onDown(MotionEvent motionEvent) {
                    return true;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public void onLongPress(MotionEvent motionEvent) {
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    return true;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public void onShowPress(MotionEvent motionEvent) {
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    SyntheticTouchNavigationHandler.this.dispatchTap(motionEvent.getEventTime());
                    return true;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    SyntheticTouchNavigationHandler.this.dispatchFling(f, f2, motionEvent2.getEventTime());
                    return true;
                }
            }, (Handler) null, 0);
        }

        public void process(MotionEvent motionEvent) {
            if (motionEvent.getDevice() == null) {
                return;
            }
            this.mPendingKeyMetaState = motionEvent.getMetaState();
            int deviceId = motionEvent.getDeviceId();
            int source = motionEvent.getSource();
            if (this.mCurrentDeviceId != deviceId || this.mCurrentSource != source) {
                this.mCurrentDeviceId = deviceId;
                this.mCurrentSource = source;
            }
            this.mGestureDetector.onTouchEvent(motionEvent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchTap(long j) {
            dispatchEvent(j, 23);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchFling(float f, float f2, long j) {
            if (Math.abs(f) > Math.abs(f2)) {
                dispatchEvent(j, f > 0.0f ? 22 : 21);
            } else {
                dispatchEvent(j, f2 > 0.0f ? 20 : 19);
            }
        }

        private void dispatchEvent(long j, int i) {
            ViewRootImpl.this.enqueueInputEvent(new KeyEvent(j, j, 0, i, 0, this.mPendingKeyMetaState, this.mCurrentDeviceId, 0, 1024, this.mCurrentSource));
            ViewRootImpl.this.enqueueInputEvent(new KeyEvent(j, j, 1, i, 0, this.mPendingKeyMetaState, this.mCurrentDeviceId, 0, 1024, this.mCurrentSource));
        }
    }

    final class SyntheticKeyboardHandler {
        SyntheticKeyboardHandler() {
        }

        public void process(KeyEvent keyEvent) {
            KeyCharacterMap.FallbackAction fallbackAction;
            if ((keyEvent.getFlags() & 1024) == 0 && (fallbackAction = keyEvent.getKeyCharacterMap().getFallbackAction(keyEvent.getKeyCode(), keyEvent.getMetaState())) != null) {
                KeyEvent obtain = KeyEvent.obtain(keyEvent.getDownTime(), keyEvent.getEventTime(), keyEvent.getAction(), fallbackAction.keyCode, keyEvent.getRepeatCount(), fallbackAction.metaState, keyEvent.getDeviceId(), keyEvent.getScanCode(), keyEvent.getFlags() | 1024, keyEvent.getSource(), null);
                fallbackAction.recycle();
                ViewRootImpl.this.enqueueInputEvent(obtain);
            }
        }
    }

    private static boolean isNavigationKey(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 61 || keyCode == 62 || keyCode == 66 || keyCode == 92 || keyCode == 93 || keyCode == 122 || keyCode == 123) {
            return true;
        }
        switch (keyCode) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                return true;
            default:
                return false;
        }
    }

    private static boolean isTypingKey(KeyEvent keyEvent) {
        return keyEvent.getUnicodeChar() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkForLeavingTouchModeAndConsume(KeyEvent keyEvent) {
        if (!this.mAttachInfo.mInTouchMode) {
            return false;
        }
        int action = keyEvent.getAction();
        if ((action != 0 && action != 2) || (keyEvent.getFlags() & 4) != 0) {
            return false;
        }
        if (keyEvent.hasNoModifiers() && isNavigationKey(keyEvent)) {
            return ensureTouchMode(false);
        }
        if (isTypingKey(keyEvent)) {
            ensureTouchMode(false);
        }
        return false;
    }

    void setLocalDragState(Object obj) {
        this.mLocalDragState = obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDragEvent(DragEvent dragEvent) {
        if (this.mView != null && this.mAdded) {
            int i = dragEvent.mAction;
            if (CoreRune.FW_DVRR_TOOLKIT_PROLONG_TOUCH_BOOST && i == 2) {
                this.mIsDragging = true;
            }
            if (i == 1) {
                this.mCurrentDragView = null;
                this.mDragDescription = dragEvent.mClipDescription;
                View view = this.mStartedDragViewForA11y;
                if (view != null) {
                    view.sendWindowContentChangedAccessibilityEvent(128);
                }
            } else {
                if (i == 4) {
                    this.mDragDescription = null;
                }
                dragEvent.mClipDescription = this.mDragDescription;
            }
            if (i == 6) {
                if (View.sCascadedDragDrop) {
                    this.mView.dispatchDragEnterExitInPreN(dragEvent);
                }
                setDragFocus(null, dragEvent);
            } else {
                if (i == 2 || i == 3) {
                    this.mDragPoint.set(dragEvent.mX, dragEvent.mY);
                    CompatibilityInfo.Translator translator = this.mTranslator;
                    if (translator != null) {
                        translator.translatePointInScreenToAppWindow(this.mDragPoint);
                    }
                    int i2 = this.mCurScrollY;
                    if (i2 != 0) {
                        this.mDragPoint.offset(0.0f, i2);
                    }
                    dragEvent.mX = this.mDragPoint.x;
                    dragEvent.mY = this.mDragPoint.y;
                }
                View view2 = this.mCurrentDragView;
                if (i == 3 && dragEvent.mClipData != null) {
                    dragEvent.mClipData.prepareToEnterProcess(this.mView.getContext().getAttributionSource());
                }
                boolean dispatchDragEvent = this.mView.dispatchDragEvent(dragEvent);
                if (!dispatchDragEvent && i == 1 && dragEvent.isStickyEvent()) {
                    Log.i(this.mTag, "Save sticky drag event");
                    this.mSavedStickyDragEvent = DragEvent.obtain(dragEvent);
                }
                if (i == 1) {
                    InputManagerGlobal.getInstance().setDragPointerInfo(getDragStateInputToken(), getDragDeviceId(), getDragPointerId());
                } else if (i == 4) {
                    InputManagerGlobal.getInstance().clreaDragPointerInfo();
                }
                boolean isEavesDrop = dragEvent.isEavesDrop();
                if (i == 2 && !isEavesDrop) {
                    if (dragEvent.mEventHandlerWasCalled) {
                        InputManagerGlobal.getInstance().setDragPointerIcon(PointerIcon.getSystemIcon(this.mContext, 1021));
                    } else {
                        InputManagerGlobal.getInstance().setDragPointerIcon(PointerIcon.getSystemIcon(this.mContext, 1012));
                        setDragFocus(null, dragEvent);
                    }
                    InputManagerGlobal.getInstance().updateDragPointerIcon(getDisplayId());
                }
                if (view2 != this.mCurrentDragView) {
                    if (view2 != null) {
                        try {
                            InputManagerGlobal.getInstance().setDragPointerIcon(PointerIcon.getSystemIcon(this.mContext, 1012));
                            this.mWindowSession.dragRecipientExited(this.mWindow);
                        } catch (RemoteException unused) {
                            Slog.e(this.mTag, "Unable to note drag target change");
                        }
                    }
                    if (this.mCurrentDragView != null) {
                        InputManagerGlobal.getInstance().setDragPointerIcon(PointerIcon.getSystemIcon(this.mContext, 1021));
                        this.mWindowSession.dragRecipientEntered(this.mWindow);
                    }
                    InputManagerGlobal.getInstance().updateDragPointerIcon(getDisplayId());
                }
                if (i == 3) {
                    try {
                        Log.i(this.mTag, "Reporting drop result: " + dispatchDragEvent);
                        this.mWindowSession.reportDropResult(this.mWindow, dispatchDragEvent);
                    } catch (RemoteException unused2) {
                        Log.e(this.mTag, "Unable to report drop result");
                    }
                }
                if (i == 4) {
                    if (this.mStartedDragViewForA11y != null) {
                        if (!dragEvent.getResult()) {
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
                    if (this.mAttachInfo.mDragData != null) {
                        View.cleanUpPendingIntents(this.mAttachInfo.mDragData);
                        this.mAttachInfo.mDragData = null;
                    }
                    clearSavedStickyDragEvent();
                }
            }
        }
        dragEvent.recycle();
    }

    @Override // android.view.ViewParent
    public void requestSendStickyDragStartedEvent(View view) {
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

    public void handleRequestKeyboardShortcuts(IResultReceiver iResultReceiver, int i) {
        Bundle bundle = new Bundle();
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        View view = this.mView;
        if (view != null) {
            view.requestKeyboardShortcuts(arrayList, i);
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((KeyboardShortcutGroup) arrayList.get(i2)).setPackageName(this.mBasePackageName);
        }
        bundle.putParcelableArrayList(WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY, arrayList);
        try {
            iResultReceiver.send(0, bundle);
        } catch (RemoteException unused) {
        }
    }

    public void getLastTouchPoint(Point point) {
        point.x = (int) this.mLastTouchPoint.x;
        point.y = (int) this.mLastTouchPoint.y;
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

    public void setDragFocus(View view, DragEvent dragEvent) {
        if (this.mCurrentDragView != view && !View.sCascadedDragDrop) {
            float f = dragEvent.mX;
            float f2 = dragEvent.mY;
            int i = dragEvent.mAction;
            ClipData clipData = dragEvent.mClipData;
            dragEvent.mX = 0.0f;
            dragEvent.mY = 0.0f;
            dragEvent.mClipData = null;
            if (this.mCurrentDragView != null) {
                dragEvent.mAction = 6;
                this.mCurrentDragView.callDragEventHandler(dragEvent);
            }
            if (view != null) {
                dragEvent.mAction = 5;
                view.callDragEventHandler(dragEvent);
            }
            dragEvent.mAction = i;
            dragEvent.mX = f;
            dragEvent.mY = f2;
            dragEvent.mClipData = clipData;
        }
        this.mCurrentDragView = view;
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

    private Vibrator getSystemVibrator() {
        if (this.mVibrator == null) {
            this.mVibrator = (Vibrator) this.mContext.getSystemService(Vibrator.class);
        }
        return this.mVibrator;
    }

    public void clearSystemVibrator() {
        this.mVibrator = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AutofillManager getAutofillManager() {
        View view = this.mView;
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (viewGroup.getChildCount() > 0) {
            return (AutofillManager) viewGroup.getChildAt(0).getContext().getSystemService(AutofillManager.class);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAutofillUiShowing() {
        AutofillManager autofillManager = getAutofillManager();
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.isAutofillUiShowing();
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

    private boolean shouldNotLocalLayout(WindowConfiguration windowConfiguration) {
        if (windowConfiguration.isOverlappingWithCutout()) {
            return true;
        }
        if (CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT) {
            DisplayCutout displayCutout = this.mInsetsController.getState().getDisplayCutout();
            int max = Math.max(Math.max(displayCutout.getSafeInsetLeft(), displayCutout.getSafeInsetRight()), Math.max(displayCutout.getSafeInsetTop(), displayCutout.getSafeInsetBottom()));
            return max > 0 && max <= this.mMinimumSizeForOverlappingWithCutoutAsDefault;
        }
        if ((CoreRune.MW_EMBED_ACTIVITY && shouldNotLocalLayoutEmbedded(windowConfiguration)) || shouldNotLocalLayoutPopOver(windowConfiguration)) {
            return true;
        }
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN && this.mDisplay.getDisplayId() == 1) {
            return true;
        }
        return (android.view.inputmethod.Flags.refactorInsetsController() && this.mInsetsController.hasImeOverriddenLocalVisibility()) || this.mInsetsController.hasPendingFrame();
    }

    private boolean shouldNotLocalLayoutEmbedded(WindowConfiguration windowConfiguration) {
        return windowConfiguration.isEmbedded() && this.mWindowAttributes.type == 2038;
    }

    private boolean shouldNotLocalLayoutPopOver(WindowConfiguration windowConfiguration) {
        InsetsSource peekSource;
        return windowConfiguration.isPopOver() && (peekSource = this.mInsetsController.getState().peekSource(InsetsSource.ID_IME)) != null && peekSource.isVisible();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0471  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0473  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0349  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0371  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x03ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int relayoutWindow(android.view.WindowManager.LayoutParams r28, int r29, boolean r30) throws android.os.RemoteException {
        /*
            Method dump skipped, instructions count: 1161
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.relayoutWindow(android.view.WindowManager$LayoutParams, int, boolean):int");
    }

    private void updateOpacity(WindowManager.LayoutParams layoutParams, boolean z, boolean z2) {
        boolean z3 = !PixelFormat.formatHasAlpha(layoutParams.format) && layoutParams.surfaceInsets.left == 0 && layoutParams.surfaceInsets.top == 0 && layoutParams.surfaceInsets.right == 0 && layoutParams.surfaceInsets.bottom == 0 && !z;
        if (z2 || this.mIsSurfaceOpaque != z3) {
            ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
            if (threadedRenderer != null && threadedRenderer.rendererOwnsSurfaceControlOpacity()) {
                z3 = threadedRenderer.setSurfaceControlOpaque(z3);
            } else {
                this.mTransaction.setOpaque(this.mSurfaceControl, z3).apply();
            }
            this.mIsSurfaceOpaque = z3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFrame(Rect rect, boolean z) {
        boolean z2 = !this.mWinFrame.equals(rect);
        if (!this.mForceUpdateBoundsLayer) {
            this.mForceUpdateBoundsLayer = z2;
        }
        if (!this.mFirst && !this.mWinFrame.equals(rect) && rect.isEmpty()) {
            Log.i(this.mTag, "Force to draw a frame when frame become empty from non-empty");
            this.mForceDraw = true;
        }
        Rect rect2 = this.mPendingWinFrame;
        if (rect2 != null && rect2.equals(rect)) {
            this.mPendingWinFrame = null;
        }
        this.mWinFrame.set(rect);
        if (z) {
            this.mLastLayoutFrame.set(rect);
        }
        WindowConfiguration compatWindowConfiguration = getCompatWindowConfiguration();
        this.mPendingBackDropFrame.set((!this.mPendingDragResizing || compatWindowConfiguration.useWindowFrameForBackdrop()) ? rect : compatWindowConfiguration.getMaxBounds());
        this.mPendingBackDropFrame.offsetTo(0, 0);
        InsetsController insetsController = this.mInsetsController;
        Rect rect3 = this.mOverrideInsetsFrame;
        if (rect3 != null) {
            rect = rect3;
        }
        insetsController.onFrameChanged(rect);
    }

    void setOverrideInsetsFrame(Rect rect) {
        Rect rect2 = new Rect(rect);
        this.mOverrideInsetsFrame = rect2;
        this.mInsetsController.onFrameChanged(rect2);
    }

    void getDisplayFrame(Rect rect) {
        rect.set(this.mTmpFrames.displayFrame);
        applyViewBoundsSandboxingIfNeeded(rect);
    }

    void getWindowVisibleDisplayFrame(Rect rect) {
        rect.set(this.mTmpFrames.displayFrame);
        Rect rect2 = this.mAttachInfo.mVisibleInsets;
        rect.left += rect2.left;
        rect.top += rect2.top;
        rect.right -= rect2.right;
        rect.bottom -= rect2.bottom;
        applyViewBoundsSandboxingIfNeeded(rect);
    }

    boolean shouldIgnoreInsetsAnimation() {
        return CompatSandbox.shouldIgnoreInsetsAnimation(this.mLastReportedMergedConfiguration.getMergedConfiguration(), this.mView, this.mTag);
    }

    void applyInsetsHintSandboxingIfNeeded(InsetsSourceControl[] insetsSourceControlArr) {
        CompatSandbox.applyInsetsHintSandboxingIfNeeded(this.mLastReportedMergedConfiguration.getMergedConfiguration(), insetsSourceControlArr);
    }

    void applyViewBoundsSandboxingIfNeeded(Rect rect) {
        applyViewBoundsSandboxingIfNeeded(rect, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyViewBoundsSandboxingIfNeeded(Rect rect, boolean z) {
        if (this.mViewBoundsSandboxingEnabled) {
            Rect bounds = getConfiguration().windowConfiguration.getBounds();
            if (z) {
                rect.offset(bounds.left, bounds.top);
                return;
            } else {
                rect.offset(-bounds.left, -bounds.top);
                return;
            }
        }
        CompatSandbox.applyViewBoundsSandboxingIfNeeded(this.mLastReportedMergedConfiguration.getMergedConfiguration(), rect, z);
    }

    public void applyViewLocationSandboxingIfNeeded(int[] iArr) {
        if (this.mViewBoundsSandboxingEnabled) {
            Rect bounds = getConfiguration().windowConfiguration.getBounds();
            iArr[0] = iArr[0] - bounds.left;
            iArr[1] = iArr[1] - bounds.top;
            return;
        }
        CompatSandbox.applyViewLocationSandboxingIfNeeded(this.mLastReportedMergedConfiguration.getMergedConfiguration(), iArr);
    }

    private boolean getViewBoundsSandboxingEnabled() {
        if (ActivityThread.isSystem() || !CompatChanges.isChangeEnabled(ActivityInfo.OVERRIDE_SANDBOX_VIEW_BOUNDS_APIS)) {
            return false;
        }
        try {
            List<PackageManager.Property> queryApplicationProperty = this.mContext.getPackageManager().queryApplicationProperty(WindowManager.PROPERTY_COMPAT_ALLOW_SANDBOXING_VIEW_BOUNDS_APIS);
            if (queryApplicationProperty.isEmpty()) {
                return true;
            }
            return queryApplicationProperty.get(0).getBoolean();
        } catch (RuntimeException unused) {
            return true;
        }
    }

    @Override // android.view.View.AttachInfo.Callbacks
    public void playSoundEffect(int i) {
        if ((this.mDisplay.getFlags() & 1024) != 0) {
            return;
        }
        checkThread();
        try {
            AudioManager audioManager = getAudioManager();
            if (this.mFastScrollSoundEffectsEnabled && SoundEffectConstants.isNavigationRepeat(i)) {
                audioManager.playSoundEffect(SoundEffectConstants.nextNavigationRepeatSoundEffectId());
                return;
            }
            switch (i) {
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
                    throw new IllegalArgumentException("unknown effect id " + i + " not defined in " + SoundEffectConstants.class.getCanonicalName());
            }
        } catch (IllegalStateException e) {
            Log.e(this.mTag, "FATAL EXCEPTION when attempting to play sound effect: " + e);
            e.printStackTrace();
        }
    }

    @Override // android.view.View.AttachInfo.Callbacks
    public boolean performHapticFeedback(int i, int i2, int i3) {
        if ((this.mDisplay.getFlags() & 1024) != 0) {
            return false;
        }
        getSystemVibrator().performHapticFeedback(i, "ViewRootImpl#performHapticFeedback", i2, i3);
        return true;
    }

    @Override // android.view.View.AttachInfo.Callbacks
    public void performHapticFeedbackForInputDevice(int i, int i2, int i3, int i4, int i5) {
        if ((this.mDisplay.getFlags() & 1024) != 0) {
            return;
        }
        getSystemVibrator().performHapticFeedbackForInputDevice(i, i2, i3, "ViewRootImpl#performHapticFeedbackForInputDevice", i4, i5);
    }

    @Override // android.view.ViewParent
    public View focusSearch(View view, int i) {
        checkThread();
        if (this.mView instanceof ViewGroup) {
            return FocusFinder.getInstance().findNextFocus((ViewGroup) this.mView, view, i);
        }
        return null;
    }

    @Override // android.view.ViewParent
    public View keyboardNavigationClusterSearch(View view, int i) {
        checkThread();
        return FocusFinder.getInstance().findNextKeyboardNavigationCluster(this.mView, view, i);
    }

    public void debug() {
        this.mView.debug();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, Objects.toString(this.mView));
        protoOutputStream.write(1120986464258L, this.mDisplay.getDisplayId());
        protoOutputStream.write(1133871366147L, this.mAppVisible);
        protoOutputStream.write(1120986464261L, this.mHeight);
        protoOutputStream.write(1120986464260L, this.mWidth);
        protoOutputStream.write(1133871366150L, this.mIsAnimating);
        this.mVisRect.dumpDebug(protoOutputStream, 1146756268039L);
        protoOutputStream.write(1133871366152L, this.mIsDrawing);
        protoOutputStream.write(1133871366153L, this.mAdded);
        this.mWinFrame.dumpDebug(protoOutputStream, 1146756268042L);
        protoOutputStream.write(1138166333452L, Objects.toString(this.mLastWindowInsets));
        protoOutputStream.write(1138166333453L, InputMethodDebug.softInputModeToString(this.mSoftInputMode));
        protoOutputStream.write(1120986464270L, this.mScrollY);
        protoOutputStream.write(1120986464271L, this.mCurScrollY);
        protoOutputStream.write(1133871366160L, this.mRemoved);
        this.mWindowAttributes.dumpDebug(protoOutputStream, 1146756268049L);
        protoOutputStream.end(start);
        this.mInsetsController.dumpDebug(protoOutputStream, 1146756268036L);
        this.mImeFocusController.dumpDebug(protoOutputStream, 1146756268039L);
    }

    public void dump(String str, PrintWriter printWriter) {
        String str2 = str + "  ";
        printWriter.println(str + "ViewRoot:");
        printWriter.println(str2 + "mAdded=" + this.mAdded);
        printWriter.println(str2 + "mRemoved=" + this.mRemoved);
        printWriter.println(str2 + "mStopped=" + this.mStopped);
        printWriter.println(str2 + "mPausedForTransition=" + this.mPausedForTransition);
        printWriter.println(str2 + "mConsumeBatchedInputScheduled=" + this.mConsumeBatchedInputScheduled);
        printWriter.println(str2 + "mConsumeBatchedInputImmediatelyScheduled=" + this.mConsumeBatchedInputImmediatelyScheduled);
        printWriter.println(str2 + "mPendingInputEventCount=" + this.mPendingInputEventCount);
        printWriter.println(str2 + "mProcessInputEventsScheduled=" + this.mProcessInputEventsScheduled);
        printWriter.println(str2 + "mTraversalScheduled=" + this.mTraversalScheduled);
        if (this.mTraversalScheduled) {
            printWriter.println(str2 + " (barrier=" + this.mTraversalBarrier + NavigationBarInflaterView.KEY_CODE_END);
        }
        printWriter.println(str2 + "mReportNextDraw=" + this.mReportNextDraw);
        if (this.mReportNextDraw) {
            printWriter.println(str2 + " (reason=" + this.mLastReportNextDrawReason + NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mLastPerformTraversalsSkipDrawReason != null) {
            printWriter.println(str2 + "mLastPerformTraversalsFailedReason=" + this.mLastPerformTraversalsSkipDrawReason);
        }
        if (this.mLastPerformDrawSkippedReason != null) {
            printWriter.println(str2 + "mLastPerformDrawFailedReason=" + this.mLastPerformDrawSkippedReason);
        }
        if (this.mWmsRequestSyncGroupState != 0) {
            printWriter.println(str2 + "mWmsRequestSyncGroupState=" + this.mWmsRequestSyncGroupState);
        }
        printWriter.println(str2 + "mLastReportedMergedConfiguration=" + this.mLastReportedMergedConfiguration);
        printWriter.println(str2 + "mLastConfigurationFromResources=" + this.mLastConfigurationFromResources);
        if (this.mLastReportedActivityWindowInfo != null) {
            printWriter.println(str2 + "mLastReportedActivityWindowInfo=" + this.mLastReportedActivityWindowInfo);
        }
        printWriter.println(str2 + "mIsAmbientMode=" + this.mIsAmbientMode);
        printWriter.println(str2 + "mUnbufferedInputSource=" + Integer.toHexString(this.mUnbufferedInputSource));
        if (this.mAttachInfo != null) {
            printWriter.print(str2 + "mAttachInfo= ");
            this.mAttachInfo.dump(str2, printWriter);
        } else {
            printWriter.println(str2 + "mAttachInfo=<null>");
        }
        this.mFirstInputStage.dump(str2, printWriter);
        WindowInputEventReceiver windowInputEventReceiver = this.mInputEventReceiver;
        if (windowInputEventReceiver != null) {
            windowInputEventReceiver.dump(str2, printWriter);
        }
        this.mChoreographer.dump(str, printWriter);
        this.mInsetsController.dump(str, printWriter);
        this.mOnBackInvokedDispatcher.dump(str, printWriter);
        this.mImeBackAnimationController.dump(str, printWriter);
        this.mViewRootSurfaceController.dump(str, printWriter);
        printWriter.println(str + "View Hierarchy:");
        dumpViewHierarchy(str2, printWriter, this.mView);
    }

    private void dumpViewHierarchy(String str, PrintWriter printWriter, View view) {
        ViewGroup viewGroup;
        int childCount;
        printWriter.print(str);
        if (view == null) {
            printWriter.println(PerfettoProtoLogImpl.NULL_STRING);
            return;
        }
        printWriter.println(view.toString());
        if ((view instanceof ViewGroup) && (childCount = (viewGroup = (ViewGroup) view).getChildCount()) > 0) {
            String str2 = str + "  ";
            for (int i = 0; i < childCount; i++) {
                dumpViewHierarchy(str2, printWriter, viewGroup.getChildAt(i));
            }
        }
    }

    static final class GfxInfo {
        public long renderNodeMemoryAllocated;
        public long renderNodeMemoryUsage;
        public int viewCount;

        GfxInfo() {
        }

        void add(GfxInfo gfxInfo) {
            this.viewCount += gfxInfo.viewCount;
            this.renderNodeMemoryUsage += gfxInfo.renderNodeMemoryUsage;
            this.renderNodeMemoryAllocated += gfxInfo.renderNodeMemoryAllocated;
        }
    }

    GfxInfo getGfxInfo() {
        GfxInfo gfxInfo = new GfxInfo();
        View view = this.mView;
        if (view != null) {
            appendGfxInfo(view, gfxInfo);
        }
        return gfxInfo;
    }

    private static void computeRenderNodeUsage(RenderNode renderNode, GfxInfo gfxInfo) {
        if (renderNode == null) {
            return;
        }
        gfxInfo.renderNodeMemoryUsage += renderNode.computeApproximateMemoryUsage();
        gfxInfo.renderNodeMemoryAllocated += renderNode.computeApproximateMemoryAllocated();
    }

    private static void appendGfxInfo(View view, GfxInfo gfxInfo) {
        gfxInfo.viewCount++;
        computeRenderNodeUsage(view.mRenderNode, gfxInfo);
        computeRenderNodeUsage(view.mBackgroundRenderNode, gfxInfo);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                appendGfxInfo(viewGroup.getChildAt(i), gfxInfo);
            }
        }
    }

    boolean die(boolean z) {
        if (z && !this.mIsInTraversal) {
            doDie();
            return false;
        }
        if (!com.android.graphics.hwui.flags.Flags.removeVriSketchyDestroy()) {
            if (!this.mIsDrawing) {
                destroyHardwareRenderer();
            } else {
                Log.e(this.mTag, "Attempting to destroy the window while drawing!\n  window=" + this + ", title=" + ((Object) this.mWindowAttributes.getTitle()));
            }
        }
        this.mHandler.sendEmptyMessage(3);
        return true;
    }

    void doDie() {
        View view;
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
            destroyHardwareRenderer();
            if (this.mAdded && !this.mFirst && (view = this.mView) != null) {
                int visibility = view.getVisibility();
                boolean z = this.mViewVisibility != visibility;
                if (this.mWindowAttributesChanged || z) {
                    try {
                        if ((1 & relayoutWindow(this.mWindowAttributes, visibility, false)) != 0) {
                            this.mWindowSession.finishDrawing(this.mWindow, null, Integer.MAX_VALUE);
                        }
                    } catch (RemoteException unused) {
                    }
                }
                destroySurface();
            }
            this.mInsetsController.onControlsChanged(null);
            this.mAdded = false;
            AnimationHandler.removeRequestor(this);
            handleSyncRequestWhenNoAsyncDraw(this.mActiveSurfaceSyncGroup, this.mHasPendingTransactions, this.mPendingTransaction, "shutting down VRI");
            this.mHasPendingTransactions = false;
            WindowManagerGlobal.getInstance().doRemoveView(this);
        }
    }

    public void requestUpdateConfiguration(Configuration configuration) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(18, configuration));
    }

    public void loadSystemProperties() {
        this.mHandler.post(new Runnable() { // from class: android.view.ViewRootImpl.11
            @Override // java.lang.Runnable
            public void run() {
                ViewRootImpl.this.mProfileRendering = SystemProperties.getBoolean(ViewRootImpl.PROPERTY_PROFILE_RENDERING, false);
                ViewRootImpl viewRootImpl = ViewRootImpl.this;
                viewRootImpl.profileRendering(viewRootImpl.mAttachInfo.mHasWindowFocus);
                if (ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null && ViewRootImpl.this.mAttachInfo.mThreadedRenderer.loadSystemProperties()) {
                    ViewRootImpl.this.invalidate();
                }
                boolean booleanValue = DisplayProperties.debug_layout().orElse(false).booleanValue();
                if (booleanValue != ViewRootImpl.this.mAttachInfo.mDebugLayout) {
                    ViewRootImpl.this.mAttachInfo.mDebugLayout = booleanValue;
                    if (ViewRootImpl.this.mHandler.hasMessages(22)) {
                        return;
                    }
                    ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(22, 200L);
                }
            }
        });
    }

    private void destroyHardwareRenderer() {
        ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
        this.mHdrRenderState.stopListening();
        if (threadedRenderer != null) {
            HardwareRendererObserver hardwareRendererObserver = this.mHardwareRendererObserver;
            if (hardwareRendererObserver != null) {
                threadedRenderer.removeObserver(hardwareRendererObserver);
            }
            View view = this.mView;
            if (view != null) {
                threadedRenderer.destroyHardwareResources(view);
            }
            threadedRenderer.destroy();
            if (ViewRune.COMMON_IS_PRODUCT_DEV) {
                Log.d(this.mTag, "mThreadedRenderer.destroy()#4");
            }
            threadedRenderer.setRequested(false);
            this.mAttachInfo.mThreadedRenderer = null;
            this.mAttachInfo.mHardwareAccelerated = false;
            logColorMode(this.mCurrentColorMode, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchResized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4, ActivityWindowInfo activityWindowInfo) {
        Message obtainMessage = this.mHandler.obtainMessage(z ? 5 : 4);
        SomeArgs obtain = SomeArgs.obtain();
        Rect rect = clientWindowFrames.frame;
        Log.i(this.mTag, "Resizing " + this + ": frame = " + rect.toShortString() + " reportDraw = " + z + " forceLayout = " + z2 + " syncSeqId = " + i2);
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
    public void dispatchInsetsControlChanged(InsetsState insetsState, InsetsSourceControl.Array array) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = insetsState;
        obtain.arg2 = array;
        this.mHandler.obtainMessage(29, obtain).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showInsets(int i, boolean z, ImeTracker.Token token) {
        this.mHandler.obtainMessage(31, i, z ? 1 : 0, token).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideInsets(int i, boolean z, ImeTracker.Token token) {
        this.mHandler.obtainMessage(32, i, z ? 1 : 0, token).sendToTarget();
    }

    public void dispatchMoved(int i, int i2) {
        if (DEBUG_LAYOUT) {
            Log.v(this.mTag, "Window moved " + this + ": newX=" + i + " newY=" + i2);
        }
        if (this.mTranslator != null) {
            this.mTranslator.translatePointInScreenToAppWindow(new PointF(i, i2));
            i = (int) (r0.x + 0.5d);
            i2 = (int) (r0.y + 0.5d);
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(23, i, i2));
    }

    public void notifyImeVisibilityChanged(boolean z, ImeTracker.Token token) {
        ImeTracker.forLogging().onProgress(token, 52);
        try {
            this.mWindowSession.notifyImeWindowVisibilityChangedFromClient(this.mWindow, z, token);
        } catch (RemoteException e) {
            ImeTracker.forLogging().onFailed(token, 52);
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class QueuedInputEvent {
        public static final int FLAG_DEFERRED = 2;
        public static final int FLAG_DELIVER_POST_IME = 1;
        public static final int FLAG_FINISHED = 4;
        public static final int FLAG_FINISHED_HANDLED = 8;
        public static final int FLAG_MODIFIED_FOR_COMPATIBILITY = 64;
        public static final int FLAG_PRE_IME_ONLY = 128;
        public static final int FLAG_RESYNTHESIZED = 16;
        public static final int FLAG_UNHANDLED = 32;
        public InputEvent mEvent;
        public int mFlags;
        public QueuedInputEvent mNext;
        public InputEventReceiver mReceiver;

        private QueuedInputEvent() {
        }

        public boolean forPreImeOnly() {
            return (this.mFlags & 128) != 0;
        }

        public boolean shouldSkipIme() {
            if ((this.mFlags & 1) != 0) {
                return true;
            }
            InputEvent inputEvent = this.mEvent;
            return (inputEvent instanceof MotionEvent) && inputEvent.isFromSource(2);
        }

        public boolean shouldSendToSynthesizer() {
            return (this.mFlags & 32) != 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("QueuedInputEvent{flags=");
            if (!flagToString("FLAG_PRE_IME_ONLY", 128, flagToString("UNHANDLED", 32, flagToString("RESYNTHESIZED", 16, flagToString("FINISHED_HANDLED", 8, flagToString("FINISHED", 4, flagToString("DEFERRED", 2, flagToString("DELIVER_POST_IME", 1, false, sb), sb), sb), sb), sb), sb), sb)) {
                sb.append("0");
            }
            sb.append(", hasNextQueuedEvent=".concat(this.mEvent != null ? "true" : "false"));
            sb.append(", hasInputEventReceiver=".concat(this.mReceiver == null ? "false" : "true"));
            sb.append(", mEvent=" + this.mEvent + "}");
            return sb.toString();
        }

        private boolean flagToString(String str, int i, boolean z, StringBuilder sb) {
            if ((this.mFlags & i) == 0) {
                return z;
            }
            if (z) {
                sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            }
            sb.append(str);
            return true;
        }
    }

    private QueuedInputEvent obtainQueuedInputEvent(InputEvent inputEvent, InputEventReceiver inputEventReceiver, int i) {
        QueuedInputEvent queuedInputEvent = this.mQueuedInputEventPool;
        if (queuedInputEvent != null) {
            this.mQueuedInputEventPoolSize--;
            this.mQueuedInputEventPool = queuedInputEvent.mNext;
            queuedInputEvent.mNext = null;
        } else {
            queuedInputEvent = new QueuedInputEvent();
        }
        queuedInputEvent.mEvent = inputEvent;
        queuedInputEvent.mReceiver = inputEventReceiver;
        queuedInputEvent.mFlags = i;
        return queuedInputEvent;
    }

    private void recycleQueuedInputEvent(QueuedInputEvent queuedInputEvent) {
        queuedInputEvent.mEvent = null;
        queuedInputEvent.mReceiver = null;
        int i = this.mQueuedInputEventPoolSize;
        if (i < 10) {
            this.mQueuedInputEventPoolSize = i + 1;
            queuedInputEvent.mNext = this.mQueuedInputEventPool;
            this.mQueuedInputEventPool = queuedInputEvent;
        }
    }

    public void enqueueInputEvent(InputEvent inputEvent) {
        enqueueInputEvent(inputEvent, null, 0, false);
    }

    QueuedInputEvent enqueueInputEvent(InputEvent inputEvent, InputEventReceiver inputEventReceiver, int i, boolean z) {
        if (CoreRune.FW_SPEN_HOVER && (inputEvent instanceof KeyEvent) && (((KeyEvent) inputEvent).getFlags() & 33554432) != 0) {
            i |= 1;
        }
        QueuedInputEvent obtainQueuedInputEvent = obtainQueuedInputEvent(inputEvent, inputEventReceiver, i);
        if (inputEvent instanceof MotionEvent) {
            if (((MotionEvent) inputEvent).getAction() == 3) {
                EventLog.writeEvent(EventLogTags.VIEW_ENQUEUE_INPUT_EVENT, "Motion - Cancel", getTitle().toString());
            }
        } else if ((inputEvent instanceof KeyEvent) && ((KeyEvent) inputEvent).isCanceled()) {
            EventLog.writeEvent(EventLogTags.VIEW_ENQUEUE_INPUT_EVENT, "Key - Cancel", getTitle().toString());
        }
        QueuedInputEvent queuedInputEvent = this.mPendingInputEventTail;
        if (queuedInputEvent == null) {
            this.mPendingInputEventHead = obtainQueuedInputEvent;
            this.mPendingInputEventTail = obtainQueuedInputEvent;
        } else {
            queuedInputEvent.mNext = obtainQueuedInputEvent;
            this.mPendingInputEventTail = obtainQueuedInputEvent;
        }
        int i2 = this.mPendingInputEventCount + 1;
        this.mPendingInputEventCount = i2;
        Trace.traceCounter(4L, this.mPendingInputEventQueueLengthCounterName, i2);
        if (z) {
            doProcessInputEvents();
            return obtainQueuedInputEvent;
        }
        scheduleProcessInputEvents();
        return obtainQueuedInputEvent;
    }

    private void scheduleProcessInputEvents() {
        if (this.mProcessInputEventsScheduled) {
            return;
        }
        this.mProcessInputEventsScheduled = true;
        Message obtainMessage = this.mHandler.obtainMessage(19);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    void doProcessInputEvents() {
        while (true) {
            QueuedInputEvent queuedInputEvent = this.mPendingInputEventHead;
            if (queuedInputEvent == null) {
                break;
            }
            QueuedInputEvent queuedInputEvent2 = queuedInputEvent.mNext;
            this.mPendingInputEventHead = queuedInputEvent2;
            if (queuedInputEvent2 == null) {
                this.mPendingInputEventTail = null;
            }
            queuedInputEvent.mNext = null;
            int i = this.mPendingInputEventCount - 1;
            this.mPendingInputEventCount = i;
            Trace.traceCounter(4L, this.mPendingInputEventQueueLengthCounterName, i);
            if (queuedInputEvent.mEvent instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent) queuedInputEvent.mEvent;
                View view = this.mView;
                if (view != null && ((view.getTop() != 0 || this.mView.getLeft() != 0) && this.mWindowAttributes.layoutInDisplayCutoutMode == 1 && this.mWindowAttributes.type == 2)) {
                    motionEvent.offsetLocation(-this.mView.getLeft(), -this.mView.getTop());
                }
            }
            this.mViewFrameInfo.setInputEvent(this.mInputEventAssigner.processEvent(queuedInputEvent.mEvent));
            deliverInputEvent(queuedInputEvent);
        }
        if (this.mProcessInputEventsScheduled) {
            this.mProcessInputEventsScheduled = false;
            this.mHandler.removeMessages(19);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00e6, code lost:
    
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ea, code lost:
    
        throw r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x012d, code lost:
    
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0131, code lost:
    
        throw r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void deliverInputEvent(android.view.ViewRootImpl.QueuedInputEvent r10) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.deliverInputEvent(android.view.ViewRootImpl$QueuedInputEvent):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishInputEvent(QueuedInputEvent queuedInputEvent) {
        Trace.asyncTraceEnd(8L, "deliverInputEvent", queuedInputEvent.mEvent.getId());
        if (queuedInputEvent.mReceiver != null) {
            boolean z = (queuedInputEvent.mFlags & 8) != 0;
            if ((queuedInputEvent.mFlags & 64) != 0) {
                Trace.traceBegin(8L, "processInputEventBeforeFinish");
                try {
                    InputEvent processInputEventBeforeFinish = this.mInputCompatProcessor.processInputEventBeforeFinish(queuedInputEvent.mEvent);
                    if (processInputEventBeforeFinish != null) {
                        queuedInputEvent.mReceiver.finishInputEvent(processInputEventBeforeFinish, z);
                    }
                } finally {
                    Trace.traceEnd(8L);
                }
            } else {
                queuedInputEvent.mReceiver.finishInputEvent(queuedInputEvent.mEvent, z);
            }
            if (queuedInputEvent.mEvent instanceof KeyEvent) {
                logHandledSystemKey((KeyEvent) queuedInputEvent.mEvent, z);
            }
        } else {
            queuedInputEvent.mEvent.recycleIfNeededAfterDispatch();
        }
        recycleQueuedInputEvent(queuedInputEvent);
    }

    private void logHandledSystemKey(KeyEvent keyEvent, boolean z) {
        if (keyEvent.getKeyCode() == 264 && keyEvent.isDown() && keyEvent.getRepeatCount() == 0 && z) {
            Counter.logIncrementWithUid("input.value_app_handled_stem_primary_key_gestures_count", Process.myUid());
        }
    }

    static boolean isTerminalInputEvent(InputEvent inputEvent) {
        if (inputEvent instanceof KeyEvent) {
            return ((KeyEvent) inputEvent).getAction() == 1;
        }
        int action = ((MotionEvent) inputEvent).getAction();
        return action == 1 || action == 3 || action == 10;
    }

    void scheduleConsumeBatchedInput() {
        if (this.mConsumeBatchedInputScheduled || this.mConsumeBatchedInputImmediatelyScheduled) {
            return;
        }
        this.mConsumeBatchedInputScheduled = true;
        this.mChoreographer.postCallback(0, this.mConsumedBatchedInputRunnable, null);
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.notifyCallbackPending();
        }
    }

    void unscheduleConsumeBatchedInput() {
        if (this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = false;
            this.mChoreographer.removeCallbacks(0, this.mConsumedBatchedInputRunnable, null);
        }
    }

    void scheduleConsumeBatchedInputImmediately() {
        if (this.mConsumeBatchedInputImmediatelyScheduled) {
            return;
        }
        unscheduleConsumeBatchedInput();
        this.mConsumeBatchedInputImmediatelyScheduled = true;
        this.mHandler.post(this.mConsumeBatchedInputImmediatelyRunnable);
    }

    boolean doConsumeBatchedInput(long j) {
        WindowInputEventReceiver windowInputEventReceiver = this.mInputEventReceiver;
        boolean consumeBatchedInputEvents = windowInputEventReceiver != null ? windowInputEventReceiver.consumeBatchedInputEvents(j) : false;
        doProcessInputEvents();
        return consumeBatchedInputEvents;
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
        public void onInputEvent(InputEvent inputEvent) {
            ViewRootImpl.this.processRawInputEvent(inputEvent);
        }

        @Override // android.view.InputEventReceiver
        public void onBatchedInputEventPending(int i) {
            if (ViewRootImpl.this.mUnbufferedInputDispatch || (i & ViewRootImpl.this.mUnbufferedInputSource) != 0) {
                if (ViewRootImpl.this.mConsumeBatchedInputScheduled) {
                    ViewRootImpl.this.unscheduleConsumeBatchedInput();
                }
                consumeBatchedInputEvents(-1L);
                return;
            }
            ViewRootImpl.this.scheduleConsumeBatchedInput();
        }

        @Override // android.view.InputEventReceiver
        public void onFocusEvent(boolean z) {
            ViewRootImpl.this.windowFocusChanged(z);
        }

        @Override // android.view.InputEventReceiver
        public void onTouchModeChanged(boolean z) {
            ViewRootImpl.this.touchModeChanged(z);
        }

        @Override // android.view.InputEventReceiver
        public void onPointerCaptureEvent(boolean z) {
            ViewRootImpl.this.dispatchPointerCaptureChanged(z);
        }

        @Override // android.view.InputEventReceiver
        public void onDragEvent(boolean z, float f, float f2, int i) {
            ViewRootImpl.this.dispatchDragEvent(DragEvent.obtain(z ? 6 : 2, f, f2, 0.0f, 0.0f, i, 0, null, null, null, null, null, false));
        }

        @Override // android.view.InputEventReceiver
        public void dispose() {
            ViewRootImpl.this.unscheduleConsumeBatchedInput();
            super.dispose();
        }
    }

    final class InputMetricsListener implements HardwareRendererObserver.OnFrameMetricsAvailableListener {
        public long[] data = new long[24];

        InputMetricsListener() {
        }

        @Override // android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener
        public void onFrameMetricsAvailable(int i) {
            long[] jArr = this.data;
            int i2 = (int) jArr[4];
            if (i2 == 0) {
                return;
            }
            long j = jArr[22];
            if (j <= 0) {
                return;
            }
            long j2 = jArr[20];
            if (ViewRootImpl.this.mInputEventReceiver == null) {
                return;
            }
            if (j2 >= j) {
                Log.w(ViewRootImpl.TAG, "Not reporting timeline because gpuCompletedTime is " + ((j2 - j) * 1.0E-6d) + "ms ahead of presentTime. FRAME_TIMELINE_VSYNC_ID=" + this.data[1] + ", INPUT_EVENT_ID=" + i2);
                return;
            }
            ViewRootImpl.this.mInputEventReceiver.reportTimeline(i2, j2, j);
        }
    }

    final class ConsumeBatchedInputRunnable implements Runnable {
        ConsumeBatchedInputRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Trace.traceBegin(8L, ViewRootImpl.this.mTag);
            try {
                ViewRootImpl.this.mConsumeBatchedInputScheduled = false;
                ViewRootImpl viewRootImpl = ViewRootImpl.this;
                if (viewRootImpl.doConsumeBatchedInput(viewRootImpl.mChoreographer.getFrameTimeNanos())) {
                    ViewRootImpl.this.scheduleConsumeBatchedInput();
                }
            } finally {
                Trace.traceEnd(8L);
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

        public void addViewRect(View.AttachInfo.InvalidateInfo invalidateInfo) {
            synchronized (this) {
                this.mViewRects.add(invalidateInfo);
                postIfNeededLocked();
            }
            if (ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null) {
                ViewRootImpl.this.mAttachInfo.mThreadedRenderer.notifyCallbackPending();
            }
        }

        public void removeView(View view) {
            synchronized (this) {
                this.mViews.remove(view);
                int size = this.mViewRects.size();
                while (true) {
                    int i = size - 1;
                    if (size <= 0) {
                        break;
                    }
                    View.AttachInfo.InvalidateInfo invalidateInfo = this.mViewRects.get(i);
                    if (invalidateInfo.target == view) {
                        this.mViewRects.remove(i);
                        invalidateInfo.recycle();
                    }
                    size = i;
                }
                if (this.mPosted && this.mViews.isEmpty() && this.mViewRects.isEmpty()) {
                    ViewRootImpl.this.mChoreographer.removeCallbacks(1, this, null);
                    this.mPosted = false;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            int i;
            int size;
            int size2;
            synchronized (this) {
                this.mPosted = false;
                size = this.mViews.size();
                if (size != 0) {
                    ArrayList<View> arrayList = this.mViews;
                    View[] viewArr = this.mTempViews;
                    if (viewArr == null) {
                        viewArr = new View[size];
                    }
                    this.mTempViews = (View[]) arrayList.toArray(viewArr);
                    this.mViews.clear();
                }
                size2 = this.mViewRects.size();
                if (size2 != 0) {
                    ArrayList<View.AttachInfo.InvalidateInfo> arrayList2 = this.mViewRects;
                    View.AttachInfo.InvalidateInfo[] invalidateInfoArr = this.mTempViewRects;
                    if (invalidateInfoArr == null) {
                        invalidateInfoArr = new View.AttachInfo.InvalidateInfo[size2];
                    }
                    this.mTempViewRects = (View.AttachInfo.InvalidateInfo[]) arrayList2.toArray(invalidateInfoArr);
                    this.mViewRects.clear();
                }
            }
            for (int i2 = 0; i2 < size; i2++) {
                this.mTempViews[i2].invalidate();
                this.mTempViews[i2] = null;
            }
            for (i = 0; i < size2; i++) {
                View.AttachInfo.InvalidateInfo invalidateInfo = this.mTempViewRects[i];
                invalidateInfo.target.invalidate(invalidateInfo.left, invalidateInfo.top, invalidateInfo.right, invalidateInfo.bottom);
                invalidateInfo.recycle();
            }
        }

        private void postIfNeededLocked() {
            if (this.mPosted) {
                return;
            }
            ViewRootImpl.this.mChoreographer.postCallback(1, this, null);
            this.mPosted = true;
        }
    }

    public void processRawInputEvent(InputEvent inputEvent) {
        String str;
        Trace.traceBegin(8L, "processInputEventForCompatibility");
        try {
            List<InputEvent> processInputEventForCompatibility = this.mInputCompatProcessor.processInputEventForCompatibility(inputEvent);
            Trace.traceEnd(8L);
            if (processInputEventForCompatibility != null) {
                if (processInputEventForCompatibility.isEmpty()) {
                    this.mInputEventReceiver.finishInputEvent(inputEvent, true);
                    return;
                }
                for (int i = 0; i < processInputEventForCompatibility.size(); i++) {
                    enqueueInputEvent(processInputEventForCompatibility.get(i), this.mInputEventReceiver, 64, true);
                }
                return;
            }
            if (ViewRune.COMMON_IS_PRODUCT_DEV && (inputEvent instanceof MotionEvent)) {
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                str = String.format("(X=%d, Y=%d, Action=%d)", Integer.valueOf((int) motionEvent.getX()), Integer.valueOf((int) motionEvent.getY()), Integer.valueOf(motionEvent.getAction()));
                Trace.traceBegin(8L, str);
            } else {
                str = null;
            }
            enqueueInputEvent(inputEvent, this.mInputEventReceiver, 0, true);
            if (str != null) {
            }
        } finally {
            Trace.traceEnd(8L);
        }
    }

    public void dispatchInvalidateDelayed(View view, long j) {
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, view), j);
    }

    public void dispatchInvalidateRectDelayed(View.AttachInfo.InvalidateInfo invalidateInfo, long j) {
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2, invalidateInfo), j);
    }

    public void dispatchInvalidateOnAnimation(View view) {
        this.mInvalidateOnAnimationRunnable.addView(view);
    }

    public void dispatchInvalidateRectOnAnimation(View.AttachInfo.InvalidateInfo invalidateInfo) {
        this.mInvalidateOnAnimationRunnable.addViewRect(invalidateInfo);
    }

    public void cancelInvalidate(View view) {
        this.mHandler.removeMessages(1, view);
        this.mHandler.removeMessages(2, view);
        this.mInvalidateOnAnimationRunnable.removeView(view);
    }

    public void dispatchInputEvent(InputEvent inputEvent) {
        dispatchInputEvent(inputEvent, null);
    }

    public void dispatchInputEvent(InputEvent inputEvent, InputEventReceiver inputEventReceiver) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = inputEvent;
        obtain.arg2 = inputEventReceiver;
        Message obtainMessage = this.mHandler.obtainMessage(7, obtain);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void synthesizeInputEvent(InputEvent inputEvent) {
        Message obtainMessage = this.mHandler.obtainMessage(24, inputEvent);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void dispatchKeyFromIme(KeyEvent keyEvent) {
        Message obtainMessage = this.mHandler.obtainMessage(11, keyEvent);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void dispatchKeyFromAutofill(KeyEvent keyEvent) {
        Message obtainMessage = this.mHandler.obtainMessage(12, keyEvent);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void dispatchUnhandledInputEvent(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            inputEvent = MotionEvent.obtain((MotionEvent) inputEvent);
        }
        synthesizeInputEvent(inputEvent);
    }

    public void dispatchAppVisibility(boolean z) {
        this.mSemEarlyAppVisibilityChanged = true;
        this.mSemEarlyAppVisibility = z;
        Message obtainMessage = this.mHandler.obtainMessage(8);
        obtainMessage.arg1 = z ? 1 : 0;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void dispatchGetNewSurface() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(9));
    }

    public void windowFocusChanged(boolean z) {
        synchronized (this) {
            this.mWindowFocusChanged = true;
            this.mUpcomingWindowFocus = z;
        }
        Message obtain = Message.obtain();
        obtain.what = 6;
        this.mHandler.sendMessage(obtain);
    }

    public void touchModeChanged(boolean z) {
        synchronized (this) {
            this.mUpcomingInTouchMode = z;
        }
        Message obtain = Message.obtain();
        obtain.what = 34;
        this.mHandler.sendMessage(obtain);
    }

    public void dispatchWindowShown() {
        this.mHandler.sendEmptyMessage(25);
    }

    public void dispatchCloseSystemDialogs(String str) {
        Message obtain = Message.obtain();
        obtain.what = 14;
        obtain.obj = str;
        this.mHandler.sendMessage(obtain);
    }

    public void dispatchDragEvent(DragEvent dragEvent) {
        int i;
        if (dragEvent.getAction() == 2) {
            i = 16;
            this.mHandler.removeMessages(16);
        } else {
            i = 15;
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(i, dragEvent));
    }

    public void dispatchDragEventUpdated(DragEvent dragEvent) {
        if (this.mAttachInfo.mDragToken != null) {
            Log.i(TAG, "dispatchDragEventUpdated() return.");
            return;
        }
        this.mHandler.removeMessages(16);
        this.mHandler.removeMessages(15);
        sendDispatchDragEvent(6, dragEvent);
        sendDispatchDragEvent(4, dragEvent);
        sendDispatchDragEvent(1, dragEvent);
        sendDispatchDragEvent(2, dragEvent);
    }

    private void sendDispatchDragEvent(int i, DragEvent dragEvent) {
        float x;
        float y;
        float offsetX;
        float offsetY;
        if (i == 1 || i == 2) {
            x = dragEvent.getX();
            y = dragEvent.getY();
            offsetX = dragEvent.getOffsetX();
            offsetY = dragEvent.getOffsetY();
        } else {
            x = 0.0f;
            y = 0.0f;
            offsetX = 0.0f;
            offsetY = 0.0f;
        }
        DragEvent obtain = DragEvent.obtain(i, x, y, offsetX, offsetY, getDisplayId(), dragEvent.getDragFlags(), dragEvent.mLocalState, dragEvent.getClipDescription(), dragEvent.mClipData, 6 == i ? null : dragEvent.getDragSurface(), dragEvent.mDragAndDropPermissions, dragEvent.mDragResult);
        ViewRootHandler viewRootHandler = this.mHandler;
        viewRootHandler.sendMessage(viewRootHandler.obtainMessage(15, obtain));
    }

    public void dispatchCheckFocus() {
        if (this.mHandler.hasMessages(13)) {
            return;
        }
        this.mHandler.sendEmptyMessage(13);
    }

    public void dispatchRequestKeyboardShortcuts(IResultReceiver iResultReceiver, int i) {
        this.mHandler.obtainMessage(26, i, 0, iResultReceiver).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPointerCaptureChanged(boolean z) {
        this.mHandler.removeMessages(28);
        Message obtainMessage = this.mHandler.obtainMessage(28);
        obtainMessage.arg1 = z ? 1 : 0;
        this.mHandler.sendMessage(obtainMessage);
    }

    private void postSendWindowContentChangedCallback(View view, int i) {
        if (this.mSendWindowContentChangedAccessibilityEvent == null) {
            this.mSendWindowContentChangedAccessibilityEvent = new SendWindowContentChangedAccessibilityEvent();
        }
        this.mSendWindowContentChangedAccessibilityEvent.runOrPost(view, i);
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
    public boolean requestSendAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeProvider accessibilityNodeProvider;
        SendWindowContentChangedAccessibilityEvent sendWindowContentChangedAccessibilityEvent;
        if (this.mView == null || this.mStopped || this.mPausedForTransition) {
            return false;
        }
        if (accessibilityEvent.getEventType() != 2048 && (sendWindowContentChangedAccessibilityEvent = this.mSendWindowContentChangedAccessibilityEvent) != null && sendWindowContentChangedAccessibilityEvent.mSource != null) {
            this.mSendWindowContentChangedAccessibilityEvent.removeCallbacksAndRun();
        }
        int eventType = accessibilityEvent.getEventType();
        View sourceForAccessibilityEvent = getSourceForAccessibilityEvent(accessibilityEvent);
        if (eventType == 2048) {
            handleWindowContentChangedEvent(accessibilityEvent);
        } else if (eventType != 32768) {
            if (eventType == 65536 && sourceForAccessibilityEvent != null && sourceForAccessibilityEvent.getAccessibilityNodeProvider() != null) {
                setAccessibilityFocus(null, null);
            }
        } else if (sourceForAccessibilityEvent != null && (accessibilityNodeProvider = sourceForAccessibilityEvent.getAccessibilityNodeProvider()) != null) {
            setAccessibilityFocus(sourceForAccessibilityEvent, accessibilityNodeProvider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityEvent.getSourceNodeId())));
        }
        this.mAccessibilityManager.sendAccessibilityEvent(accessibilityEvent);
        return true;
    }

    private View getSourceForAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return AccessibilityNodeIdManager.getInstance().findView(AccessibilityNodeInfo.getAccessibilityViewId(accessibilityEvent.getSourceNodeId()));
    }

    private boolean isAccessibilityFocusDirty() {
        Drawable drawable = this.mAttachInfo.mAccessibilityFocusDrawable;
        if (drawable == null) {
            return false;
        }
        Rect rect = this.mAttachInfo.mTmpInvalRect;
        if (!getAccessibilityFocusedRect(rect)) {
            rect.setEmpty();
        }
        return !rect.equals(drawable.getBounds());
    }

    private void handleWindowContentChangedEvent(AccessibilityEvent accessibilityEvent) {
        View view = this.mAccessibilityFocusedHost;
        if (view == null || this.mAccessibilityFocusedVirtualView == null) {
            return;
        }
        AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
        if (accessibilityNodeProvider == null) {
            this.mAccessibilityFocusedHost = null;
            this.mAccessibilityFocusedVirtualView = null;
            view.clearAccessibilityFocusNoCallbacks(0);
            return;
        }
        int contentChangeTypes = accessibilityEvent.getContentChangeTypes();
        if ((contentChangeTypes & 1) != 0 || contentChangeTypes == 0) {
            int accessibilityViewId = AccessibilityNodeInfo.getAccessibilityViewId(accessibilityEvent.getSourceNodeId());
            View view2 = this.mAccessibilityFocusedHost;
            boolean z = false;
            while (view2 != null && !z) {
                if (accessibilityViewId == view2.getAccessibilityViewId()) {
                    z = true;
                } else {
                    Object parent = view2.getParent();
                    view2 = parent instanceof View ? (View) parent : null;
                }
            }
            if (z) {
                int virtualDescendantId = AccessibilityNodeInfo.getVirtualDescendantId(this.mAccessibilityFocusedVirtualView.getSourceNodeId());
                Rect rect = this.mTempRect;
                this.mAccessibilityFocusedVirtualView.getBoundsInScreen(rect);
                AccessibilityNodeInfo createAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(virtualDescendantId);
                this.mAccessibilityFocusedVirtualView = createAccessibilityNodeInfo;
                if (createAccessibilityNodeInfo == null) {
                    this.mAccessibilityFocusedHost = null;
                    view.clearAccessibilityFocusNoCallbacks(0);
                    accessibilityNodeProvider.performAction(virtualDescendantId, AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS.getId(), null);
                    invalidateRectOnScreen(rect);
                    return;
                }
                Rect boundsInScreen = createAccessibilityNodeInfo.getBoundsInScreen();
                if (rect.equals(boundsInScreen)) {
                    return;
                }
                rect.union(boundsInScreen);
                invalidateRectOnScreen(rect);
            }
        }
    }

    @Override // android.view.ViewParent
    public void notifySubtreeAccessibilityStateChanged(View view, View view2, int i) {
        postSendWindowContentChangedCallback((View) Objects.requireNonNull(view2), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getCommonPredecessor(View view, View view2) {
        if (this.mTempHashSet == null) {
            this.mTempHashSet = new HashSet<>();
        }
        HashSet<View> hashSet = this.mTempHashSet;
        hashSet.clear();
        while (view != null) {
            hashSet.add(view);
            Object obj = view.mParent;
            view = obj instanceof View ? (View) obj : null;
        }
        while (view2 != null) {
            if (hashSet.contains(view2)) {
                hashSet.clear();
                return view2;
            }
            Object obj2 = view2.mParent;
            view2 = obj2 instanceof View ? (View) obj2 : null;
        }
        hashSet.clear();
        return null;
    }

    void checkThread() {
        Thread currentThread = Thread.currentThread();
        if (this.mThread == currentThread) {
            return;
        }
        Log.i(this.mTag, "checkThread " + Debug.getCallers(20));
        throw new CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views. Expected: " + this.mThread.getName() + " Calling: " + currentThread.getName());
    }

    @Override // android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        if (rect == null) {
            return scrollToRectOrFocus(null, z);
        }
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        boolean scrollToRectOrFocus = scrollToRectOrFocus(rect, z);
        this.mTempRect.set(rect);
        this.mTempRect.offset(0, -this.mCurScrollY);
        this.mTempRect.offset(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
        try {
            this.mWindowSession.onRectangleOnScreenRequested(this.mWindow, this.mTempRect);
        } catch (RemoteException unused) {
        }
        return scrollToRectOrFocus;
    }

    public boolean probablyHasInput() {
        WindowInputEventReceiver windowInputEventReceiver = this.mInputEventReceiver;
        if (windowInputEventReceiver == null) {
            return false;
        }
        return windowInputEventReceiver.probablyHasInput();
    }

    public void addScrollCaptureCallback(ScrollCaptureCallback scrollCaptureCallback) {
        if (this.mRootScrollCaptureCallbacks == null) {
            this.mRootScrollCaptureCallbacks = new HashSet<>();
        }
        this.mRootScrollCaptureCallbacks.add(scrollCaptureCallback);
    }

    public void removeScrollCaptureCallback(ScrollCaptureCallback scrollCaptureCallback) {
        HashSet<ScrollCaptureCallback> hashSet = this.mRootScrollCaptureCallbacks;
        if (hashSet != null) {
            hashSet.remove(scrollCaptureCallback);
            if (this.mRootScrollCaptureCallbacks.isEmpty()) {
                this.mRootScrollCaptureCallbacks = null;
            }
        }
    }

    public void dispatchScrollCaptureRequest(IScrollCaptureResponseListener iScrollCaptureResponseListener) {
        this.mHandler.obtainMessage(33, iScrollCaptureResponseListener).sendToTarget();
    }

    private void collectRootScrollCaptureTargets(ScrollCaptureSearchResults scrollCaptureSearchResults) {
        HashSet<ScrollCaptureCallback> hashSet = this.mRootScrollCaptureCallbacks;
        if (hashSet == null) {
            return;
        }
        Iterator<ScrollCaptureCallback> it = hashSet.iterator();
        while (it.hasNext()) {
            ScrollCaptureCallback next = it.next();
            Point point = new Point(this.mView.getLeft(), this.mView.getTop());
            scrollCaptureSearchResults.addTarget(new ScrollCaptureTarget(this.mView, new Rect(0, 0, this.mView.getWidth(), this.mView.getHeight()), point, next));
        }
    }

    public void setScrollCaptureRequestTimeout(int i) {
        this.mScrollCaptureRequestTimeout = i;
    }

    public long getScrollCaptureRequestTimeout() {
        return this.mScrollCaptureRequestTimeout;
    }

    public void handleScrollCaptureRequest(final IScrollCaptureResponseListener iScrollCaptureResponseListener) {
        final ScrollCaptureSearchResults scrollCaptureSearchResults = new ScrollCaptureSearchResults(this.mContext.getMainExecutor());
        collectRootScrollCaptureTargets(scrollCaptureSearchResults);
        View view = getView();
        if (view == null) {
            ScrollCaptureResponse.Builder builder = new ScrollCaptureResponse.Builder();
            builder.setWindowTitle(getTitle().toString());
            builder.setPackageName(this.mContext.getPackageName());
            builder.setDescription("The root view was null");
            try {
                iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                return;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to send scroll capture search result", e);
                return;
            }
        }
        Point point = new Point();
        Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
        getChildVisibleRect(view, rect, point);
        view.dispatchScrollCaptureSearch(rect, point, new Consumer() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ScrollCaptureSearchResults.this.addTarget((ScrollCaptureTarget) obj);
            }
        });
        scrollCaptureSearchResults.setOnCompleteListener(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ViewRootImpl.this.lambda$handleScrollCaptureRequest$11(iScrollCaptureResponseListener, scrollCaptureSearchResults);
            }
        });
        if (scrollCaptureSearchResults.isComplete()) {
            return;
        }
        this.mHandler.postDelayed(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ScrollCaptureSearchResults.this.finish();
            }
        }, getScrollCaptureRequestTimeout());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dispatchScrollCaptureSearchResponse, reason: merged with bridge method [inline-methods] */
    public void lambda$handleScrollCaptureRequest$11(IScrollCaptureResponseListener iScrollCaptureResponseListener, ScrollCaptureSearchResults scrollCaptureSearchResults) {
        ScrollCaptureTarget topResult = scrollCaptureSearchResults.getTopResult();
        ScrollCaptureResponse.Builder builder = new ScrollCaptureResponse.Builder();
        builder.setWindowTitle(getTitle().toString());
        builder.setPackageName(this.mContext.getPackageName());
        StringWriter stringWriter = new StringWriter();
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(stringWriter);
        scrollCaptureSearchResults.dump(indentingPrintWriter);
        indentingPrintWriter.flush();
        builder.addMessage(stringWriter.toString());
        if (this.mView == null) {
            builder.setDescription("The root view disappeared!");
            try {
                iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                return;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to send scroll capture search result", e);
                return;
            }
        }
        if (topResult == null) {
            builder.setDescription("No scrollable targets found in window");
            try {
                iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                return;
            } catch (RemoteException e2) {
                Log.e(TAG, "Failed to send scroll capture search result", e2);
                return;
            }
        }
        builder.setDescription("Connected");
        Rect rect = new Rect();
        topResult.getContainingView().getLocationInWindow(this.mAttachInfo.mTmpLocation);
        rect.set(topResult.getScrollBounds());
        rect.offset(this.mAttachInfo.mTmpLocation[0], this.mAttachInfo.mTmpLocation[1]);
        builder.setBoundsInWindow(rect);
        Rect rect2 = new Rect();
        this.mView.getLocationOnScreen(this.mAttachInfo.mTmpLocation);
        rect2.set(0, 0, this.mView.getWidth(), this.mView.getHeight());
        rect2.offset(this.mAttachInfo.mTmpLocation[0], this.mAttachInfo.mTmpLocation[1]);
        builder.setWindowBounds(rect2);
        Log.d(TAG, "ScrollCaptureSearchResponse: " + builder);
        ScrollCaptureConnection scrollCaptureConnection = new ScrollCaptureConnection(this.mView.getContext().getMainExecutor(), topResult);
        builder.setConnection(scrollCaptureConnection);
        try {
            iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
        } catch (RemoteException e3) {
            if (DEBUG_SCROLL_CAPTURE) {
                Log.w(TAG, "Failed to send scroll capture search response.", e3);
            }
            scrollCaptureConnection.close();
        }
    }

    private void reportNextDraw(String str) {
        if (DEBUG_BLAST) {
            Log.d(this.mTag, "reportNextDraw " + Debug.getCallers(5));
        }
        this.mReportNextDraw = true;
        this.mLastReportNextDrawReason = str;
    }

    public void setReportNextDraw(boolean z, String str) {
        if (z) {
            Log.i(this.mTag, "setReportNextDraw syncBuffer=" + z + ", reason=" + str + ", caller=" + Debug.getCallers(5));
        }
        this.mSyncBuffer = z;
        reportNextDraw(str);
        invalidate();
    }

    void changeCanvasOpacity(boolean z) {
        Log.d(this.mTag, "changeCanvasOpacity: opaque=" + z);
        boolean z2 = z & ((this.mView.mPrivateFlags & 512) == 0);
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.setOpaque(z2);
        }
    }

    public boolean dispatchUnhandledKeyEvent(KeyEvent keyEvent) {
        return this.mUnhandledKeyManager.dispatch(this.mView, keyEvent);
    }

    class TakenSurfaceHolder extends BaseSurfaceHolder {
        @Override // com.android.internal.view.BaseSurfaceHolder
        public void onRelayoutContainer() {
        }

        TakenSurfaceHolder() {
        }

        @Override // com.android.internal.view.BaseSurfaceHolder
        public boolean onAllowLockCanvas() {
            return ViewRootImpl.this.mDrawingAllowed;
        }

        @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
        public void setFormat(int i) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceFormat(i);
        }

        @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
        public void setType(int i) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceType(i);
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
        public void setFixedSize(int i, int i2) {
            throw new UnsupportedOperationException("Currently only support sizing from layout");
        }

        @Override // android.view.SurfaceHolder
        public void setKeepScreenOn(boolean z) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceKeepScreenOn(z);
        }
    }

    static class W extends IWindow.Stub implements WindowStateTransactionItem.TransactionListener {
        private boolean mIsFromTransactionItem;
        private final WeakReference<ViewRootImpl> mViewAncestor;
        private final IWindowSession mWindowSession;

        W(ViewRootImpl viewRootImpl) {
            this.mViewAncestor = new WeakReference<>(viewRootImpl);
            this.mWindowSession = viewRootImpl.mWindowSession;
        }

        @Override // android.app.servertransaction.WindowStateTransactionItem.TransactionListener
        public void onExecutingWindowStateTransactionItem() {
            this.mIsFromTransactionItem = true;
        }

        @Override // android.view.IWindow
        public void resized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4, ActivityWindowInfo activityWindowInfo) {
            ClientWindowFrames clientWindowFrames2;
            MergedConfiguration mergedConfiguration2;
            InsetsState insetsState2;
            boolean z5 = this.mIsFromTransactionItem;
            this.mIsFromTransactionItem = false;
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl == null) {
                return;
            }
            if (insetsState.isSourceOrDefaultVisible(InsetsSource.ID_IME, WindowInsets.Type.ime())) {
                ImeTracing.getInstance().triggerClientDump("ViewRootImpl.W#resized", viewRootImpl.getInsetsController().getHost().getInputMethodManager(), null);
            }
            if (z5 && viewRootImpl.mHandler.getLooper() == ActivityThread.currentActivityThread().getLooper()) {
                viewRootImpl.handleResized(clientWindowFrames, z, mergedConfiguration, insetsState, z2, z3, i, i2, z4, activityWindowInfo);
                return;
            }
            if (z5 || Binder.getCallingPid() != Process.myPid()) {
                clientWindowFrames2 = clientWindowFrames;
                mergedConfiguration2 = mergedConfiguration;
                insetsState2 = insetsState;
            } else {
                InsetsState insetsState3 = new InsetsState(insetsState, true);
                insetsState2 = insetsState3;
                clientWindowFrames2 = new ClientWindowFrames(clientWindowFrames);
                mergedConfiguration2 = new MergedConfiguration(mergedConfiguration);
            }
            viewRootImpl.dispatchResized(clientWindowFrames2, z, mergedConfiguration2, insetsState2, z2, z3, i, i2, z4, activityWindowInfo);
        }

        @Override // android.view.IWindow
        public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl.Array array) {
            boolean z = this.mIsFromTransactionItem;
            this.mIsFromTransactionItem = false;
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl == null) {
                if (z) {
                    array.release();
                    return;
                }
                return;
            }
            if (insetsState.isSourceOrDefaultVisible(InsetsSource.ID_IME, WindowInsets.Type.ime())) {
                ImeTracing.getInstance().triggerClientDump("ViewRootImpl#dispatchInsetsControlChanged", viewRootImpl.getInsetsController().getHost().getInputMethodManager(), null);
            }
            if (z && viewRootImpl.mHandler.getLooper() == ActivityThread.currentActivityThread().getLooper()) {
                viewRootImpl.handleInsetsControlChanged(insetsState, array);
                return;
            }
            if (!z && Binder.getCallingPid() == Process.myPid()) {
                InsetsState insetsState2 = new InsetsState(insetsState, true);
                array = new InsetsSourceControl.Array(array, true);
                insetsState = insetsState2;
            }
            viewRootImpl.dispatchInsetsControlChanged(insetsState, array);
        }

        @Override // android.view.IWindow
        public void showInsets(int i, boolean z, ImeTracker.Token token) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (z) {
                ImeTracing.getInstance().triggerClientDump("ViewRootImpl.W#showInsets", viewRootImpl.getInsetsController().getHost().getInputMethodManager(), null);
            }
            if (viewRootImpl != null) {
                ImeTracker.forLogging().onProgress(token, 28);
                viewRootImpl.showInsets(i, z, token);
            } else {
                ImeTracker.forLogging().onFailed(token, 28);
            }
        }

        @Override // android.view.IWindow
        public void hideInsets(int i, boolean z, ImeTracker.Token token) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (z) {
                ImeTracing.getInstance().triggerClientDump("ViewRootImpl.W#hideInsets", viewRootImpl.getInsetsController().getHost().getInputMethodManager(), null);
            }
            if (viewRootImpl != null) {
                ImeTracker.forLogging().onProgress(token, 29);
                viewRootImpl.hideInsets(i, z, token);
            } else {
                ImeTracker.forLogging().onFailed(token, 29);
            }
        }

        @Override // android.view.IWindow
        public void moved(int i, int i2) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchMoved(i, i2);
            }
        }

        @Override // android.view.IWindow
        public void dispatchAppVisibility(boolean z) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchAppVisibility(z);
            }
        }

        @Override // android.view.IWindow
        public void dispatchGetNewSurface() {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchGetNewSurface();
            }
        }

        private static int checkCallingPermission(String str) {
            try {
                return ActivityManager.getService().checkPermission(str, Binder.getCallingPid(), Binder.getCallingUid());
            } catch (RemoteException unused) {
                return -1;
            }
        }

        @Override // android.view.IWindow
        public void executeCommand(String str, String str2, ParcelFileDescriptor parcelFileDescriptor) {
            View view;
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl == null || (view = viewRootImpl.mView) == null) {
                return;
            }
            if (checkCallingPermission(Manifest.permission.DUMP) != 0) {
                throw new SecurityException("Insufficient permissions to invoke executeCommand() from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            }
            ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = null;
            try {
                try {
                    try {
                        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                        try {
                            ViewDebug.dispatchCommand(view, str, str2, autoCloseOutputStream2);
                            autoCloseOutputStream2.close();
                        } catch (IOException e) {
                            e = e;
                            autoCloseOutputStream = autoCloseOutputStream2;
                            e.printStackTrace();
                            if (autoCloseOutputStream != null) {
                                autoCloseOutputStream.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            autoCloseOutputStream = autoCloseOutputStream2;
                            if (autoCloseOutputStream != null) {
                                try {
                                    autoCloseOutputStream.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e3) {
                        e = e3;
                    }
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        @Override // android.view.IWindow
        public void closeSystemDialogs(String str) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchCloseSystemDialogs(str);
            }
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) {
            if (z) {
                try {
                    this.mWindowSession.wallpaperOffsetsComplete(asBinder());
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
            if (z) {
                try {
                    this.mWindowSession.wallpaperCommandComplete(asBinder(), null);
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // android.view.IWindow
        public void dispatchDragEvent(DragEvent dragEvent) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchDragEvent(dragEvent);
            }
        }

        @Override // android.view.IWindow
        public void dispatchWindowShown() {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchWindowShown();
            }
        }

        @Override // android.view.IWindow
        public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchRequestKeyboardShortcuts(iResultReceiver, i);
            }
        }

        @Override // android.view.IWindow
        public void requestScrollCapture(IScrollCaptureResponseListener iScrollCaptureResponseListener) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchScrollCaptureRequest(iScrollCaptureResponseListener);
            }
        }

        @Override // android.view.IWindow
        public void dumpWindow(final ParcelFileDescriptor parcelFileDescriptor) {
            final ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl == null) {
                return;
            }
            viewRootImpl.mHandler.postAtFrontOfQueue(new Runnable() { // from class: android.view.ViewRootImpl$W$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ViewRootImpl.W.lambda$dumpWindow$0(ParcelFileDescriptor.this, viewRootImpl);
                }
            });
        }

        static /* synthetic */ void lambda$dumpWindow$0(ParcelFileDescriptor parcelFileDescriptor, ViewRootImpl viewRootImpl) {
            StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
            try {
                FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                viewRootImpl.dump("", fastPrintWriter);
                fastPrintWriter.flush();
            } finally {
                IoUtils.closeQuietly(parcelFileDescriptor);
                StrictMode.setThreadPolicy(allowThreadDiskWrites);
            }
        }

        @Override // android.view.IWindow
        public void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchSmartClipRemoteRequest(smartClipRemoteRequestInfo);
            }
        }

        @Override // android.view.IWindow
        public void dispatchSPenGestureEvent(InputEvent[] inputEventArr) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl == null) {
                return;
            }
            viewRootImpl.dispatchSPenGestureEvent(inputEventArr);
        }

        @Override // android.view.IWindow
        public void dispatchLetterboxDirectionChanged(int i) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchLetterboxDirectionChanged(i);
            }
        }

        @Override // android.view.IWindow
        public void dispatchDragEventUpdated(DragEvent dragEvent) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchDragEventUpdated(dragEvent);
            }
        }

        @Override // android.view.IWindow
        public void invalidateForScreenShot(boolean z) {
            final ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            final String tag = viewRootImpl.getTag() != null ? viewRootImpl.getTag() : ViewRootImpl.TAG;
            Log.i(tag, "invalidateForScreenShot forceMode=" + z);
            viewRootImpl.mForceModeInScreenshot = z;
            if (z) {
                viewRootImpl.mAttachInfo.mThreadedRenderer.setColorMode(1);
                viewRootImpl.mAttachInfo.mThreadedRenderer.setTargetHdrSdrRatio(1.0f);
            } else {
                viewRootImpl.mAttachInfo.mThreadedRenderer.setColorMode(2);
                viewRootImpl.mAttachInfo.mThreadedRenderer.setTargetHdrSdrRatio(viewRootImpl.mHdrRenderState.getRenderHdrSdrRatio());
            }
            if (viewRootImpl.mInvalidateForScreenshotRunnable == null) {
                viewRootImpl.mInvalidateForScreenshotRunnable = new Runnable(this) { // from class: android.view.ViewRootImpl.W.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.i(tag, "invalidateForScreenShot post vri invalidate");
                        viewRootImpl.invalidate();
                    }
                };
            }
            viewRootImpl.mAttachInfo.mHandler.post(viewRootImpl.mInvalidateForScreenshotRunnable);
        }

        @Override // android.view.IWindow
        public void windowFocusInTaskChanged(boolean z) {
            ViewRootImpl viewRootImpl = this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.windowFocusInTaskChanged(z);
            }
        }
    }

    public static final class CalledFromWrongThreadException extends AndroidRuntimeException {
        public CalledFromWrongThreadException(String str) {
            super(str);
        }
    }

    static HandlerActionQueue getRunQueue() {
        ThreadLocal<HandlerActionQueue> threadLocal = sRunQueues;
        HandlerActionQueue handlerActionQueue = threadLocal.get();
        if (handlerActionQueue != null) {
            return handlerActionQueue;
        }
        HandlerActionQueue handlerActionQueue2 = new HandlerActionQueue();
        threadLocal.set(handlerActionQueue2);
        return handlerActionQueue2;
    }

    private void startDragResizing(Rect rect, boolean z, Rect rect2, Rect rect3) {
        if (this.mDragResizing) {
            return;
        }
        this.mDragResizing = true;
        if (this.mUseMTRenderer) {
            for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
                this.mWindowCallbacks.get(size).onWindowDragResizeStart(rect, z, rect2, rect3);
            }
        }
        this.mFullRedrawNeeded = true;
    }

    private void endDragResizing() {
        if (this.mDragResizing) {
            this.mDragResizing = false;
            if (this.mUseMTRenderer) {
                for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
                    this.mWindowCallbacks.get(size).onWindowDragResizeEnd();
                }
            }
            this.mFullRedrawNeeded = true;
        }
    }

    private boolean updateContentDrawBounds() {
        boolean z;
        if (this.mUseMTRenderer) {
            z = false;
            for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
                z |= this.mWindowCallbacks.get(size).onContentDrawn(this.mWindowAttributes.surfaceInsets.left, this.mWindowAttributes.surfaceInsets.top, this.mWidth, this.mHeight);
            }
        } else {
            z = false;
        }
        return z | (this.mDragResizing && this.mReportNextDraw);
    }

    private void requestDrawWindow() {
        if (this.mUseMTRenderer) {
            if (this.mReportNextDraw) {
                this.mWindowDrawCountDown = new CountDownLatch(this.mWindowCallbacks.size());
            }
            for (int size = this.mWindowCallbacks.size() - 1; size >= 0; size--) {
                this.mWindowCallbacks.get(size).onRequestDraw(this.mReportNextDraw);
            }
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

    @Override // android.view.AttachedSurfaceControl
    public InputTransferToken getInputTransferToken() {
        IBinder inputToken = getInputToken();
        if (inputToken == null) {
            throw new IllegalStateException("Called getInputTransferToken for Window with no input channel");
        }
        return new InputTransferToken(inputToken);
    }

    public IBinder getWindowToken() {
        return this.mAttachInfo.mWindowToken;
    }

    @Override // android.view.AttachedSurfaceControl
    public SurfaceControl.OnJankDataListenerRegistration registerOnJankDataListener(final Executor executor, final SurfaceControl.OnJankDataListener onJankDataListener) {
        return this.mSurfaceControl.addOnJankDataListener(new SurfaceControl.OnJankDataListener() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda1
            @Override // android.view.SurfaceControl.OnJankDataListener
            public final void onJankDataAvailable(List list) {
                executor.execute(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceControl.OnJankDataListener.this.onJankDataAvailable(list);
                    }
                });
            }
        });
    }

    final class AccessibilityInteractionConnectionManager implements AccessibilityManager.AccessibilityStateChangeListener {
        private int mDirectConnectionId = -1;

        AccessibilityInteractionConnectionManager() {
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
        public void onAccessibilityStateChanged(boolean z) {
            if (z) {
                ensureConnection();
                ViewRootImpl.this.setAccessibilityWindowAttributesIfNeeded();
                if (ViewRootImpl.this.mAttachInfo.mHasWindowFocus && ViewRootImpl.this.mView != null) {
                    ViewRootImpl.this.mView.sendAccessibilityEvent(32);
                    View findFocus = ViewRootImpl.this.mView.findFocus();
                    if (findFocus != null && findFocus != ViewRootImpl.this.mView) {
                        findFocus.sendAccessibilityEvent(8);
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
            if (ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1) {
                return;
            }
            ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = ViewRootImpl.this.mAccessibilityManager.addAccessibilityInteractionConnection(ViewRootImpl.this.mWindow, ViewRootImpl.this.mLeashToken, ViewRootImpl.this.mContext.getPackageName(), new AccessibilityInteractionConnection(ViewRootImpl.this));
        }

        public void ensureNoConnection() {
            if (ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1) {
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
    public void doRelayoutForHCT(boolean z) {
        if (this.mThread != Thread.currentThread()) {
            if (z) {
                this.mHCTRelayoutHandler.sendEmptyMessage(1);
                return;
            } else {
                Log.d(TAG, "Recursion detected");
                return;
            }
        }
        destroyHardwareResources();
        resetSoftwareCaches(this.mView);
        invalidate();
        requestLayout();
        View view = this.mView;
        if (view != null) {
            forceLayout(view);
        }
    }

    private final class HCTRelayoutHandler extends Handler {
        public static final int MSG_NEED_TO_DO_RELAYOUT = 1;

        public HCTRelayoutHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            ViewRootImpl.this.doRelayoutForHCT(false);
        }
    }

    final class HighContrastTextManager implements AccessibilityManager.HighContrastTextStateChangeListener {
        HighContrastTextManager() {
            ThreadedRenderer.setHighContrastText(ViewRootImpl.this.mAccessibilityManager.isHighContrastTextEnabled());
        }

        @Override // android.view.accessibility.AccessibilityManager.HighContrastTextStateChangeListener
        public void onHighContrastTextStateChanged(boolean z) {
            ThreadedRenderer.setHighContrastText(z);
            if (CoreRune.GRAPHICS_RENDERER_HCF) {
                ViewRootImpl.this.doRelayoutForHCT(true);
            } else {
                ViewRootImpl.this.destroyAndInvalidate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroyAndInvalidate() {
        destroyHardwareResources();
        invalidate();
    }

    static final class AccessibilityInteractionConnection extends IAccessibilityInteractionConnection.Stub {
        private final WeakReference<ViewRootImpl> mViewRootImpl;

        AccessibilityInteractionConnection(ViewRootImpl viewRootImpl) {
            this.mViewRootImpl = new WeakReference<>(viewRootImpl);
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfoByAccessibilityId(long j, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr, Bundle bundle) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfoByAccessibilityIdClientThread(j, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, fArr, bundle);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfosResult(null, i);
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void performAccessibilityAction(long j, int i, Bundle bundle, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().performAccessibilityActionClientThread(j, i, bundle, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setPerformAccessibilityActionResult(false, i2);
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByViewId(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByViewIdClientThread(j, str, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, fArr);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(null, i);
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByText(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByTextClientThread(j, str, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, fArr);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfosResult(null, i);
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findFocus(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findFocusClientThread(j, i, region, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2, magnificationSpec, fArr);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(null, i2);
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void focusSearch(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().focusSearchClientThread(j, i, region, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2, magnificationSpec, fArr);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(null, i2);
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void clearAccessibilityFocus() {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl == null || viewRootImpl.mView == null) {
                return;
            }
            viewRootImpl.getAccessibilityInteractionController().clearAccessibilityFocusClientThread();
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void notifyOutsideTouch() {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl == null || viewRootImpl.mView == null) {
                return;
            }
            viewRootImpl.getAccessibilityInteractionController().notifyOutsideTouchClientThread();
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void takeScreenshotOfWindow(int i, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().takeScreenshotOfWindowClientThread(i, screenCaptureListener, iAccessibilityInteractionConnectionCallback);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(1, i);
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void getWindowSurfaceInfo(IWindowSurfaceInfoCallback iWindowSurfaceInfoCallback) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl == null || viewRootImpl.mView == null) {
                return;
            }
            viewRootImpl.getAccessibilityInteractionController().getWindowSurfaceInfoClientThread(iWindowSurfaceInfoCallback);
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void attachAccessibilityOverlayToWindow(SurfaceControl surfaceControl, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null) {
                viewRootImpl.getAccessibilityInteractionController().attachAccessibilityOverlayToWindowClientThread(surfaceControl, i, iAccessibilityInteractionConnectionCallback);
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
            View view = this.mSource;
            this.mSource = null;
            if (view == null) {
                Log.e(ViewRootImpl.TAG, "Accessibility content change has no source");
                return;
            }
            if (ViewRootImpl.this.mAccessibilityManager.isEnabled()) {
                this.mLastEventTimeMillis = SystemClock.uptimeMillis();
                AccessibilityEvent obtain = AccessibilityEvent.obtain();
                obtain.setEventType(2048);
                obtain.setContentChangeTypes(this.mChangeTypes);
                if (this.mAction.isPresent()) {
                    obtain.setAction(this.mAction.getAsInt());
                }
                view.sendAccessibilityEventUnchecked(obtain);
            } else {
                this.mLastEventTimeMillis = 0L;
            }
            view.resetSubtreeAccessibilityStateChanged();
            this.mChangeTypes = 0;
            this.mAction = OptionalInt.empty();
        }

        public void runOrPost(View view, int i) {
            if (ViewRootImpl.this.mHandler.getLooper() != Looper.myLooper()) {
                Log.e(ViewRootImpl.TAG, "Accessibility content change on non-UI thread. Future Android versions will throw an exception.", new CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views."));
                ViewRootImpl.this.mHandler.removeCallbacks(this);
                if (this.mSource != null) {
                    run();
                }
            }
            if (!canContinueThrottle(view, i)) {
                removeCallbacksAndRun();
            }
            View view2 = this.mSource;
            if (view2 != null) {
                View commonPredecessor = ViewRootImpl.this.getCommonPredecessor(view2, view);
                if (commonPredecessor != null) {
                    commonPredecessor = commonPredecessor.getSelfOrParentImportantForA11y();
                }
                if (commonPredecessor != null) {
                    view = commonPredecessor;
                }
                int i2 = i | this.mChangeTypes;
                this.mChangeTypes = i2;
                if (this.mSource != view) {
                    this.mChangeTypes = i2 | 1;
                    this.mSource = view;
                }
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
            this.mSource = view;
            this.mChangeTypes = i;
            if (ViewRootImpl.this.mAccessibilityManager.getPerformingAction() != 0) {
                this.mAction = OptionalInt.of(ViewRootImpl.this.mAccessibilityManager.getPerformingAction());
            }
            long uptimeMillis = SystemClock.uptimeMillis() - this.mLastEventTimeMillis;
            long sendRecurringAccessibilityEventsInterval = ViewConfiguration.getSendRecurringAccessibilityEventsInterval();
            if (uptimeMillis >= sendRecurringAccessibilityEventsInterval) {
                removeCallbacksAndRun();
            } else {
                ViewRootImpl.this.mHandler.postDelayed(this, sendRecurringAccessibilityEventsInterval - uptimeMillis);
            }
        }

        public void removeCallbacksAndRun() {
            ViewRootImpl.this.mHandler.removeCallbacks(this);
            run();
        }

        private boolean canContinueThrottle(View view, int i) {
            View view2;
            if (!android.view.accessibility.Flags.reduceWindowContentChangedEventThrottle() || (view2 = this.mSource) == null || view2 == view) {
                return true;
            }
            return i == 1 && this.mChangeTypes == 1;
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

        boolean dispatch(View view, KeyEvent keyEvent) {
            if (this.mDispatched) {
                return false;
            }
            try {
                Trace.traceBegin(8L, "UnhandledKeyEvent dispatch");
                this.mDispatched = true;
                View dispatchUnhandledKeyEvent = view.dispatchUnhandledKeyEvent(keyEvent);
                if (keyEvent.getAction() == 0) {
                    int keyCode = keyEvent.getKeyCode();
                    if (dispatchUnhandledKeyEvent != null && !KeyEvent.isModifierKey(keyCode)) {
                        this.mCapturedKeys.put(keyCode, new WeakReference<>(dispatchUnhandledKeyEvent));
                    }
                }
                return dispatchUnhandledKeyEvent != null;
            } finally {
                Trace.traceEnd(8L);
            }
        }

        void preDispatch(KeyEvent keyEvent) {
            int indexOfKey;
            this.mCurrentReceiver = null;
            if (keyEvent.getAction() != 1 || (indexOfKey = this.mCapturedKeys.indexOfKey(keyEvent.getKeyCode())) < 0) {
                return;
            }
            this.mCurrentReceiver = this.mCapturedKeys.valueAt(indexOfKey);
            this.mCapturedKeys.removeAt(indexOfKey);
        }

        boolean preViewDispatch(KeyEvent keyEvent) {
            this.mDispatched = false;
            if (this.mCurrentReceiver == null) {
                this.mCurrentReceiver = this.mCapturedKeys.get(keyEvent.getKeyCode());
            }
            WeakReference<View> weakReference = this.mCurrentReceiver;
            if (weakReference == null) {
                return false;
            }
            View view = weakReference.get();
            if (keyEvent.getAction() == 1) {
                this.mCurrentReceiver = null;
            }
            if (view != null && view.isAttachedToWindow()) {
                view.onUnhandledKeyEvent(keyEvent);
            }
            return true;
        }
    }

    public void setDisplayDecoration(boolean z) {
        if (z == this.mDisplayDecorationCached) {
            return;
        }
        this.mDisplayDecorationCached = z;
        if (this.mSurfaceControl.isValid()) {
            updateDisplayDecoration();
        }
    }

    private void updateDisplayDecoration() {
        this.mTransaction.setDisplayDecoration(this.mSurfaceControl, this.mDisplayDecorationCached).apply();
    }

    public void setSkipScreenshot(SurfaceControl.Transaction transaction, boolean z) {
        this.mViewRootSurfaceController.setSkipScreenshot(transaction, z);
    }

    public void setDisableSuperHdr(SurfaceControl.Transaction transaction, boolean z) {
        this.mViewRootSurfaceController.setDisableSuperHdr(transaction, z);
    }

    public void setMetaData(int i, boolean z) {
        this.mViewRootSurfaceController.setMetaData(this.mTransaction, i, z);
    }

    public void dispatchBlurRegions(float[][] fArr, long j) {
        if (DEBUG_BLUR) {
            Log.i(this.mTag, "dispatchBlurRegions " + j);
        }
        SurfaceControl surfaceControl = getSurfaceControl();
        if (surfaceControl.isValid()) {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.setBlurRegions(surfaceControl, fArr);
            if (this.mBlastBufferQueue != null) {
                transaction.onMergeWithNextTransaction(getTitle());
                this.mBlastBufferQueue.mergeWithNextTransaction(transaction, j);
            }
        }
    }

    public BackgroundBlurDrawable createBackgroundBlurDrawable() {
        return this.mBlurRegionAggregator.createBackgroundBlurDrawable(this.mContext);
    }

    public BackgroundBlurDrawable createBackgroundBlurDrawable(boolean z) {
        return this.mBlurRegionAggregator.createBackgroundBlurDrawable(this.mContext, z);
    }

    @Override // android.view.ViewParent
    public void onDescendantUnbufferedRequested() {
        this.mUnbufferedInputSource = this.mView.mUnbufferedInputSource;
    }

    int getSurfaceSequenceId() {
        return this.mSurfaceSequenceId;
    }

    public void mergeWithNextTransaction(SurfaceControl.Transaction transaction, long j) {
        String str = this.mTag;
        StringBuilder sb = new StringBuilder("mWNT: t=0x");
        String str2 = PerfettoProtoLogImpl.NULL_STRING;
        sb.append(transaction != null ? Long.toHexString(transaction.mNativeObject) : PerfettoProtoLogImpl.NULL_STRING);
        sb.append(" mBlastBufferQueue=0x");
        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            str2 = Long.toHexString(bLASTBufferQueue.mNativeObject);
        }
        sb.append(str2);
        sb.append(" fn= ");
        sb.append(j);
        sb.append(" HdrRenderState mRenderHdrSdrRatio=");
        sb.append(this.mHdrRenderState.getRenderHdrSdrRatio());
        sb.append(" caller= ");
        sb.append(Debug.getCallers(3));
        Log.i(str, sb.toString());
        if (this.mBlastBufferQueue != null) {
            if (transaction != null) {
                transaction.onMergeWithNextTransaction(getTitle());
            }
            this.mBlastBufferQueue.mergeWithNextTransaction(transaction, j);
            return;
        }
        transaction.apply();
    }

    @Override // android.view.AttachedSurfaceControl
    public SurfaceControl.Transaction buildReparentTransaction(SurfaceControl surfaceControl) {
        if (!this.mSurfaceControl.isValid()) {
            return null;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        return transaction.reparent(surfaceControl, updateAndGetBoundsLayer(transaction));
    }

    @Override // android.view.AttachedSurfaceControl
    public boolean applyTransactionOnDraw(SurfaceControl.Transaction transaction) {
        if (this.mRemoved || !isHardwareEnabled()) {
            logAndTrace("applyTransactionOnDraw applyImmediately");
            transaction.apply();
        } else {
            Trace.instant(8L, "applyTransactionOnDraw-" + this.mTag);
            this.mPendingTransaction.merge(transaction);
            this.mHasPendingTransactions = true;
        }
        return true;
    }

    @Override // android.view.AttachedSurfaceControl
    public int getBufferTransformHint() {
        if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.enableBufferTransformHintFromDisplay()) {
            return this.mPreviousTransformHint;
        }
        if (this.mSurfaceControl.isValid()) {
            return this.mSurfaceControl.getTransformHint();
        }
        return 0;
    }

    @Override // android.view.AttachedSurfaceControl
    public void addOnBufferTransformHintChangedListener(AttachedSurfaceControl.OnBufferTransformHintChangedListener onBufferTransformHintChangedListener) {
        Objects.requireNonNull(onBufferTransformHintChangedListener);
        if (this.mTransformHintListeners.contains(onBufferTransformHintChangedListener)) {
            throw new IllegalArgumentException("attempt to call addOnBufferTransformHintChangedListener() with a previously registered listener");
        }
        this.mTransformHintListeners.add(onBufferTransformHintChangedListener);
    }

    @Override // android.view.AttachedSurfaceControl
    public void removeOnBufferTransformHintChangedListener(AttachedSurfaceControl.OnBufferTransformHintChangedListener onBufferTransformHintChangedListener) {
        Objects.requireNonNull(onBufferTransformHintChangedListener);
        this.mTransformHintListeners.remove(onBufferTransformHintChangedListener);
    }

    private void dispatchTransformHintChanged(int i) {
        if (this.mTransformHintListeners.isEmpty()) {
            return;
        }
        ArrayList arrayList = (ArrayList) this.mTransformHintListeners.clone();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ((AttachedSurfaceControl.OnBufferTransformHintChangedListener) arrayList.get(i2)).onBufferTransformHintChanged(i);
        }
    }

    boolean wasRelayoutRequested() {
        return this.mRelayoutRequested;
    }

    void forceWmRelayout() {
        this.mForceNextWindowRelayout = true;
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [17] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    public WindowOnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return this.mOnBackInvokedDispatcher;
    }

    @Override // android.view.ViewParent
    public OnBackInvokedDispatcher findOnBackInvokedDispatcherForChild(View view, View view2) {
        return getOnBackInvokedDispatcher();
    }

    private void registerBackCallbackOnWindow() {
        this.mOnBackInvokedDispatcher.attachToWindow(this.mWindowSession, this.mWindow, this, this.mImeBackAnimationController);
    }

    public boolean injectBackKeyEvents(boolean z) {
        sendBackKeyEvent(0, z);
        return sendBackKeyEvent(1, z);
    }

    private boolean sendBackKeyEvent(int i, boolean z) {
        long uptimeMillis = SystemClock.uptimeMillis();
        return (enqueueInputEvent(new KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 72, 257), null, z ? 128 : 0, true).mFlags & 8) != 0;
    }

    private void registerCompatOnBackInvokedCallback() {
        this.mCompatOnBackInvokedCallback = new CompatOnBackInvokedCallback() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda16
            @Override // android.window.CompatOnBackInvokedCallback, android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                ViewRootImpl.this.lambda$registerCompatOnBackInvokedCallback$14();
            }
        };
        if (this.mOnBackInvokedDispatcher.hasImeOnBackInvokedDispatcher()) {
            Log.d(TAG, "Skip registering CompatOnBackInvokedCallback on IME dispatcher");
        } else {
            this.mOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, this.mCompatOnBackInvokedCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerCompatOnBackInvokedCallback$14() {
        injectBackKeyEvents(false);
    }

    @Override // android.view.AttachedSurfaceControl
    public void setTouchableRegion(Region region) {
        if (region != null) {
            this.mTouchableRegion = new Region(region);
        } else {
            this.mTouchableRegion = null;
        }
        this.mLastGivenInsets.reset();
        requestLayout();
    }

    IWindowSession getWindowSession() {
        return this.mWindowSession;
    }

    private void registerCallbacksForSync(boolean z, SurfaceSyncGroup surfaceSyncGroup) {
        SurfaceControl.Transaction transaction;
        if (isHardwareEnabled()) {
            if (DEBUG_BLAST) {
                Log.d(this.mTag, "registerCallbacksForSync syncBuffer=" + z);
            }
            if (this.mHasPendingTransactions) {
                transaction = new SurfaceControl.Transaction();
                transaction.merge(this.mPendingTransaction);
                this.mHasPendingTransactions = false;
            } else {
                transaction = null;
            }
            this.mAttachInfo.mThreadedRenderer.registerRtFrameCallback(new AnonymousClass12(transaction, surfaceSyncGroup, z));
        }
    }

    /* renamed from: android.view.ViewRootImpl$12, reason: invalid class name */
    class AnonymousClass12 implements HardwareRenderer.FrameDrawingCallback {
        final /* synthetic */ SurfaceSyncGroup val$surfaceSyncGroup;
        final /* synthetic */ boolean val$syncBuffer;
        final /* synthetic */ SurfaceControl.Transaction val$t;

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public void onFrameDraw(long j) {
        }

        AnonymousClass12(SurfaceControl.Transaction transaction, SurfaceSyncGroup surfaceSyncGroup, boolean z) {
            this.val$t = transaction;
            this.val$surfaceSyncGroup = surfaceSyncGroup;
            this.val$syncBuffer = z;
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public HardwareRenderer.FrameCommitCallback onFrameDraw(int i, final long j) {
            if (ViewRootImpl.DEBUG_BLAST) {
                Log.d(ViewRootImpl.this.mTag, "Received frameDrawingCallback syncResult=" + i + " frameNum=" + j + MediaMetrics.SEPARATOR);
            }
            SurfaceControl.Transaction transaction = this.val$t;
            if (transaction != null) {
                ViewRootImpl.this.mergeWithNextTransaction(transaction, j);
            }
            if ((i & 6) != 0) {
                this.val$surfaceSyncGroup.addTransaction(ViewRootImpl.this.mBlastBufferQueue.gatherPendingTransactions(j));
                this.val$surfaceSyncGroup.markSyncReady();
                return null;
            }
            if (ViewRootImpl.DEBUG_BLAST) {
                Log.i(ViewRootImpl.this.mTag, "Setting up sync and frameCommitCallback");
            }
            if (this.val$syncBuffer) {
                BLASTBufferQueue bLASTBufferQueue = ViewRootImpl.this.mBlastBufferQueue;
                final SurfaceSyncGroup surfaceSyncGroup = this.val$surfaceSyncGroup;
                if (!bLASTBufferQueue.syncNextTransaction(new Consumer() { // from class: android.view.ViewRootImpl$12$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ViewRootImpl.AnonymousClass12.this.lambda$onFrameDraw$2(surfaceSyncGroup, (SurfaceControl.Transaction) obj);
                    }
                })) {
                    Log.w(ViewRootImpl.this.mTag, "Unable to syncNextTransaction. Possibly something else is trying to sync?");
                    this.val$surfaceSyncGroup.markSyncReady();
                }
            }
            final SurfaceSyncGroup surfaceSyncGroup2 = this.val$surfaceSyncGroup;
            final boolean z = this.val$syncBuffer;
            return new HardwareRenderer.FrameCommitCallback() { // from class: android.view.ViewRootImpl$12$$ExternalSyntheticLambda1
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z2) {
                    ViewRootImpl.AnonymousClass12.this.lambda$onFrameDraw$3(j, surfaceSyncGroup2, z, z2);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$2(SurfaceSyncGroup surfaceSyncGroup, SurfaceControl.Transaction transaction) {
            if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                transaction.addDebugName("syncBuffer_" + surfaceSyncGroup.getName());
                Log.i(ViewRootImpl.this.mTag, "Received ready transaction from native, debugName=" + transaction.mDebugName);
            }
            final Runnable runnable = new Runnable() { // from class: android.view.ViewRootImpl$12$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ViewRootImpl.AnonymousClass12.this.lambda$onFrameDraw$0();
                }
            };
            ViewRootImpl.this.mHandler.postDelayed(runnable, Build.HW_TIMEOUT_MULTIPLIER * 4000);
            transaction.addTransactionCommittedListener(ViewRootImpl.this.mSimpleExecutor, new SurfaceControl.TransactionCommittedListener() { // from class: android.view.ViewRootImpl$12$$ExternalSyntheticLambda3
                @Override // android.view.SurfaceControl.TransactionCommittedListener
                public final void onTransactionCommitted() {
                    ViewRootImpl.AnonymousClass12.this.lambda$onFrameDraw$1(runnable);
                }
            });
            surfaceSyncGroup.addTransaction(transaction);
            surfaceSyncGroup.markSyncReady();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$0() {
            Log.e(ViewRootImpl.this.mTag, "Failed to submit the sync transaction after 4s. Likely to ANR soon");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$1(Runnable runnable) {
            ViewRootImpl.this.mHandler.removeCallbacks(runnable);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$3(long j, SurfaceSyncGroup surfaceSyncGroup, boolean z, boolean z2) {
            if (ViewRootImpl.DEBUG_BLAST) {
                Log.i(ViewRootImpl.this.mTag, "Received frameCommittedCallback lastAttemptedDrawFrameNum=" + j + " didProduceBuffer=" + z2);
            }
            if (z2) {
                if (z) {
                    return;
                }
                surfaceSyncGroup.markSyncReady();
            } else {
                ViewRootImpl.this.mBlastBufferQueue.clearSyncTransaction();
                surfaceSyncGroup.addTransaction(ViewRootImpl.this.mBlastBufferQueue.gatherPendingTransactions(j));
                surfaceSyncGroup.markSyncReady();
            }
        }
    }

    private void safeguardOverlappingSyncs(final SurfaceSyncGroup surfaceSyncGroup) {
        final SurfaceSyncGroup surfaceSyncGroup2 = new SurfaceSyncGroup("Safeguard-" + this.mTag);
        surfaceSyncGroup2.toggleTimeout(false);
        synchronized (this.mPreviousSyncSafeguardLock) {
            SurfaceSyncGroup surfaceSyncGroup3 = this.mPreviousSyncSafeguard;
            if (surfaceSyncGroup3 != null) {
                surfaceSyncGroup.add(surfaceSyncGroup3, (Runnable) null);
                surfaceSyncGroup.toggleTimeout(false);
                this.mPreviousSyncSafeguard.addSyncCompleteCallback(this.mSimpleExecutor, new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceSyncGroup.this.toggleTimeout(true);
                    }
                });
            }
            this.mPreviousSyncSafeguard = surfaceSyncGroup2;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.addTransactionCommittedListener(this.mSimpleExecutor, new SurfaceControl.TransactionCommittedListener() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda12
            @Override // android.view.SurfaceControl.TransactionCommittedListener
            public final void onTransactionCommitted() {
                ViewRootImpl.this.lambda$safeguardOverlappingSyncs$16(surfaceSyncGroup2);
            }
        });
        surfaceSyncGroup.addTransaction(transaction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$safeguardOverlappingSyncs$16(SurfaceSyncGroup surfaceSyncGroup) {
        surfaceSyncGroup.markSyncReady();
        synchronized (this.mPreviousSyncSafeguardLock) {
            if (this.mPreviousSyncSafeguard == surfaceSyncGroup) {
                this.mPreviousSyncSafeguard = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resumeAfterSyncTimeout() {
        Log.e(this.mTag, "Timedout waiting to unpause for sync mNumPausedForSync=" + this.mNumPausedForSync);
        this.mNumPausedForSync = 0;
        scheduleTraversals();
    }

    @Override // android.view.AttachedSurfaceControl
    public SurfaceSyncGroup getOrCreateSurfaceSyncGroup() {
        boolean z;
        if (this.mActiveSurfaceSyncGroup == null) {
            SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup(this.mTag);
            this.mActiveSurfaceSyncGroup = surfaceSyncGroup;
            surfaceSyncGroup.setAddedToSyncListener(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    ViewRootImpl.this.lambda$getOrCreateSurfaceSyncGroup$18();
                }
            });
            this.mActiveSurfaceSyncGroup.addSyncCompleteCallback(this.mExecutor, new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    ViewRootImpl.this.lambda$getOrCreateSurfaceSyncGroup$19();
                }
            });
            z = true;
        } else {
            z = false;
        }
        Trace.instant(8L, "getOrCreateSurfaceSyncGroup isNew=" + z + " " + this.mTag);
        if (DEBUG_BLAST) {
            if (z) {
                Log.i(this.mTag, "Creating new active sync group " + this.mActiveSurfaceSyncGroup.getName());
            } else {
                Log.d(this.mTag, "Return already created active sync group " + this.mActiveSurfaceSyncGroup.getName());
            }
        }
        if (!this.mActiveSurfaceSyncGroup.isComplete()) {
            this.mNumPausedForSync++;
            this.mHandler.removeMessages(37);
            this.mHandler.sendEmptyMessageDelayed(37, Build.HW_TIMEOUT_MULTIPLIER * 1000);
        } else {
            Log.d(this.mTag, "Active sync group is already completed " + this.mActiveSurfaceSyncGroup.getName());
        }
        return this.mActiveSurfaceSyncGroup;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOrCreateSurfaceSyncGroup$18() {
        Runnable runnable = new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                ViewRootImpl.this.lambda$getOrCreateSurfaceSyncGroup$17();
            }
        };
        if (Thread.currentThread() == this.mThread) {
            runnable.run();
        } else {
            this.mHandler.post(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOrCreateSurfaceSyncGroup$17() {
        int i = this.mNumPausedForSync;
        if (i > 0) {
            this.mNumPausedForSync = i - 1;
        }
        if (this.mNumPausedForSync == 0) {
            this.mHandler.removeMessages(37);
            if (this.mIsInTraversal) {
                return;
            }
            scheduleTraversals();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOrCreateSurfaceSyncGroup$19() {
        SurfaceSyncGroup surfaceSyncGroup = this.mActiveSurfaceSyncGroup;
        if (surfaceSyncGroup == null || !surfaceSyncGroup.isComplete() || this.mNumPausedForSync <= 0) {
            return;
        }
        this.mHandler.removeMessages(37);
        resumeAfterSyncTimeout();
    }

    private void updateSyncInProgressCount(SurfaceSyncGroup surfaceSyncGroup) {
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
        surfaceSyncGroup.addSyncCompleteCallback(this.mSimpleExecutor, new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                ViewRootImpl.lambda$updateSyncInProgressCount$20();
            }
        });
    }

    static /* synthetic */ void lambda$updateSyncInProgressCount$20() {
        synchronized (sSyncProgressLock) {
            int i = sNumSyncsInProgress - 1;
            sNumSyncsInProgress = i;
            if (i == 0) {
                HardwareRenderer.setRtAnimationsEnabled(true);
            }
        }
    }

    void addToSync(SurfaceSyncGroup surfaceSyncGroup) {
        SurfaceSyncGroup surfaceSyncGroup2 = this.mActiveSurfaceSyncGroup;
        if (surfaceSyncGroup2 == null) {
            return;
        }
        surfaceSyncGroup2.add(surfaceSyncGroup, (Runnable) null);
    }

    @Override // android.view.AttachedSurfaceControl
    public void setChildBoundingInsets(Rect rect) {
        if (rect.left < 0 || rect.top < 0 || rect.right < 0 || rect.bottom < 0) {
            throw new IllegalArgumentException("Negative insets passed to setChildBoundingInsets.");
        }
        this.mChildBoundingInsets.set(rect);
        this.mChildBoundingInsetsChanged = true;
        scheduleTraversals();
    }

    public boolean isSyncBuffer() {
        return this.mSyncBuffer;
    }

    public void updateWindowOpacity(boolean z) {
        this.mIsWindowOpaque = z;
        this.mForceUpdateBoundsLayer = true;
        invalidate();
    }

    public boolean isWindowOpaque() {
        return this.mIsWindowOpaque;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchLetterboxDirectionChanged(int i) {
        this.mHandler.removeMessages(104);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(104, i, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDispatchLetterboxDirectionChanged(int i) {
        this.mRequestedLetterboxDirection = i;
        if (updateAppliedLetterboxDirection(i) && (this.mView instanceof DecorView)) {
            requestInvalidateRootRenderNode();
            this.mView.invalidate();
        }
    }

    public boolean updateAppliedLetterboxDirection(int i) {
        boolean z = this.mAppliedLetterboxDirection != i;
        if (z) {
            this.mAppliedLetterboxDirection = i;
            Log.v(this.mTag, "updateAppliedLetterboxDirection, direction=" + this.mAppliedLetterboxDirection + ", Caller=" + Debug.getCaller());
        }
        return z;
    }

    private void logAndTrace(String str) {
        if (Trace.isTagEnabled(8L)) {
            Trace.instant(8L, this.mTag + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + str);
        }
        if (DEBUG_BLAST) {
            Log.d(this.mTag, str);
        }
        EventLog.writeEvent(60004, this.mTag, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFrameRateFromThreadedRendererViews() {
        ArrayList<View> arrayList = this.mThreadedRendererViews;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            View view = arrayList.get(size);
            View.AttachInfo attachInfo = view.mAttachInfo;
            if (attachInfo == null || attachInfo.mViewRootImpl != this) {
                arrayList.remove(size);
            } else {
                view.votePreferredFrameRate();
            }
        }
    }

    private void setCategoryFromCategoryCounts() {
        int i = this.mPreferredFrameRateCategory;
        if (i == 2) {
            this.mFrameRateCategoryLowCount = 5;
        } else if (i == 3) {
            this.mFrameRateCategoryNormalCount = 5;
        } else if (i == 4) {
            this.mFrameRateCategoryDirtyHintCount = 5;
        } else if (i == 5) {
            this.mFrameRateCategoryHighHintCount = 5;
        } else if (i == 6) {
            this.mFrameRateCategoryHighCount = 5;
        }
        if (this.mFrameRateCategoryHighCount > 0) {
            this.mPreferredFrameRateCategory = 6;
        } else if (this.mFrameRateCategoryHighHintCount > 0) {
            this.mPreferredFrameRateCategory = 5;
        } else if (CoreRune.FW_ARR_SUPPORT_DIRTY_HINT && this.mFrameRateCategoryDirtyHintCount > 0) {
            this.mPreferredFrameRateCategory = 4;
        } else if (this.mFrameRateCategoryNormalCount > 0) {
            this.mPreferredFrameRateCategory = 3;
        } else if (this.mFrameRateCategoryLowCount > 0) {
            this.mPreferredFrameRateCategory = 2;
        }
        if (!CoreRune.FW_DVRR_TOOLKIT_POLICY || i == this.mPreferredFrameRateCategory) {
            return;
        }
        this.mFrameRateCategoryChangeReason = 218103808;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x004f, code lost:
    
        if (r11.mSurfaceReplaced != false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0051, code lost:
    
        r6 = android.os.Trace.isTagEnabled(8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0055, code lost:
    
        if (r6 == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0084, code lost:
    
        if (android.view.ViewRootImpl.sToolkitFrameRateFunctionEnablingReadOnlyFlagValue == false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0086, code lost:
    
        android.util.Log.i(r11.mTag, "call setFrameRateCategory category=" + categoryToString(r12) + ", reason=" + reasonToString(r3) + ", vri=" + r11.mTag);
        r11.mFrameRateTransaction.setFrameRateCategory(r11.mSurfaceControl, r12, false).applyAsyncUnsafe();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00c0, code lost:
    
        if (android.view.ViewRootImpl.sToolkitFrameRateDebugFlagValue == false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00c2, code lost:
    
        android.util.Log.v(r11.mTag, "### ViewRootImpl setFrameRateCategory '" + categoryToString(r12) + "'");
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00dc, code lost:
    
        r11.mLastPreferredFrameRateCategory = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00de, code lost:
    
        r2 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00e3, code lost:
    
        r12 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00e4, code lost:
    
        r2 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00e0, code lost:
    
        r11 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00e1, code lost:
    
        r2 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0057, code lost:
    
        r8 = reasonToString(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x005b, code lost:
    
        if (r7 != null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x005d, code lost:
    
        r7 = com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x005f, code lost:
    
        android.os.Trace.traceBegin(8, "ViewRootImpl#setFrameRateCategory " + categoryToString(r12) + ", reason " + r8 + ", " + r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00e6, code lost:
    
        if (r2 == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00e8, code lost:
    
        android.os.Trace.traceEnd(8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00eb, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x004b, code lost:
    
        if (r11.mLastPreferredFrameRateCategory == r12) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00fe  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setPreferredFrameRateCategory(int r12) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.setPreferredFrameRateCategory(int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFrameRateCategoryForTouchHint(int i) {
        int i2;
        if (this.mSurface.isValid()) {
            if (!this.mIsTouchHint || i > 5) {
                i2 = this.mFrameRateCategoryChangeReason;
            } else {
                i2 = 150994944;
                i = 5;
            }
            if (i != 0) {
                try {
                    if (this.mLastPreferredFrameRateCategory != i) {
                        Log.i(this.mTag, "call setFrameRateCategory for touch hint category=" + categoryToString(i) + ", reason=" + reasonToString(i2) + ", vri=" + this.mTag);
                        this.mTouchHintTransaction.setFrameRateCategory(this.mSurfaceControl, i, false).applyAsyncUnsafe();
                        this.mLastPreferredFrameRateCategory = i;
                    }
                } catch (Exception e) {
                    Log.e(this.mTag, "Unable to set frame rate category", e);
                }
            }
        }
    }

    static String categoryToString(int i) {
        switch (i) {
            case 1:
                return "no preference";
            case 2:
                return "low";
            case 3:
                return "normal";
            case 4:
                return "dirty hint";
            case 5:
                return "high hint";
            case 6:
                return "high";
            default:
                return "default";
        }
    }

    private static String reasonToString(int i) {
        switch (i) {
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
                return "category counts";
            default:
                return String.valueOf(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPreferredFrameRate(float f) {
        if (!shouldSetFrameRate() || f < 0.0f) {
            return;
        }
        boolean z = false;
        try {
            try {
                if (this.mLastPreferredFrameRate != f || this.mSurfaceReplaced) {
                    z = Trace.isTagEnabled(8L);
                    if (z) {
                        Trace.traceBegin(8L, "ViewRootImpl#setFrameRate " + f + " compatibility " + this.mFrameRateCompatibility);
                    }
                    if (sToolkitFrameRateFunctionEnablingReadOnlyFlagValue) {
                        if (f > 0.0f) {
                            Log.i(this.mTag, "call setFrameRate frameRate=" + f + ", compatibility=" + this.mFrameRateCompatibility + ", vri=" + this.mTag);
                            this.mFrameRateTransaction.setFrameRate(this.mSurfaceControl, f, this.mFrameRateCompatibility);
                            if (sToolkitFrameRateDebugFlagValue) {
                                Log.v(this.mTag, "### ViewRootImpl setFrameRate '" + f + "'");
                            }
                        } else {
                            Log.d(this.mTag, "call clearFrameRate");
                            this.mFrameRateTransaction.clearFrameRate(this.mSurfaceControl);
                            if (sToolkitFrameRateDebugFlagValue) {
                                Log.v(this.mTag, "### ViewRootImpl setFrameRate 0 Hz");
                            }
                        }
                        this.mFrameRateTransaction.applyAsyncUnsafe();
                    }
                    this.mLastPreferredFrameRate = f;
                }
                if (z) {
                    Trace.traceEnd(8L);
                }
            } catch (Exception e) {
                Log.e(this.mTag, "Unable to set frame rate", e);
                if (z) {
                    Trace.traceEnd(8L);
                }
            }
        } catch (Throwable th) {
            if (z) {
                Trace.traceEnd(8L);
            }
            throw th;
        }
    }

    private boolean shouldSetFrameRateCategory() {
        return shouldEnableDvrr() && this.mSurface.isValid();
    }

    private boolean shouldSetFrameRate() {
        return shouldEnableDvrr() && this.mSurface.isValid() && this.mPreferredFrameRate >= 0.0f && !this.mIsFrameRateConflicted;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldTouchBoost(int i, int i2) {
        boolean z = i != 4;
        return Flags.toolkitFrameRateTouchBoost25q1() ? z && shouldEnableDvrr() && getFrameRateBoostOnTouchEnabled() : z && !(i2 == 2011 && sToolkitFrameRateTypingReadOnlyFlagValue) && shouldEnableDvrr() && getFrameRateBoostOnTouchEnabled();
    }

    public void votePreferredFrameRateCategory(int i, int i2, View view) {
        if (CoreRune.FW_DVRR_TOOLKIT_REQUESTED_REFRESH_RATE && this.mFrameRateCategoryChangeReason == 67108864) {
            return;
        }
        if (i2 == 67108864) {
            Log.i(this.mTag, "Requested frameRateCategory " + i + " by " + view);
        }
        if ((CoreRune.FW_DVRR_TOOLKIT_REQUESTED_REFRESH_RATE && i2 == 67108864) || i > this.mPreferredFrameRateCategory) {
            this.mPreferredFrameRateCategory = i;
            this.mFrameRateCategoryChangeReason = i2;
        }
        this.mDrawnThisFrame = true;
        if (sToolkitFrameRateDebugFlagValue) {
            String simpleName = view == null ? NativeLibraryHelper.CLEAR_ABI_OVERRIDE : view.getClass().getSimpleName();
            Log.v(this.mTag, "### View: " + simpleName + " votes '" + categoryToString(i) + "'");
        }
    }

    public void addThreadedRendererView(View view) {
        if (!shouldEnableDvrr() || this.mThreadedRendererViews.contains(view)) {
            return;
        }
        this.mThreadedRendererViews.add(view);
    }

    public void removeThreadedRendererView(View view) {
        this.mThreadedRendererViews.remove(view);
        if (shouldEnableDvrr() && !this.mInvalidationIdleMessagePosted && sSurfaceFlingerBugfixFlagValue) {
            this.mInvalidationIdleMessagePosted = true;
            this.mHandler.sendEmptyMessageDelayed(40, 750L);
        }
    }

    int intermittentUpdateState() {
        if (this.mMinusOneFrameIntervalMillis + this.mMinusTwoFrameIntervalMillis < 100) {
            return 1;
        }
        return this.mInfrequentUpdateCount == 2 ? 0 : -1;
    }

    public boolean shouldCheckFrameRateCategory() {
        return this.mPreferredFrameRateCategory < 6;
    }

    public boolean shouldCheckFrameRate(boolean z) {
        if (this.mPreferredFrameRate >= 120.0f) {
            return (z || sToolkitFrameRateVelocityMappingReadOnlyFlagValue || this.mPreferredFrameRateCategory >= 6) ? false : true;
        }
        return true;
    }

    public void votePreferredFrameRate(float f, int i) {
        float f2;
        if (f <= 0.0f) {
            return;
        }
        if (i == 2 && !this.mIsPressedGesture) {
            this.mIsTouchBoosting = false;
            this.mIsFrameRateBoosting = false;
            if (!sToolkitFrameRateVelocityMappingReadOnlyFlagValue) {
                this.mPreferredFrameRateCategory = 6;
                this.mFrameRateCategoryHighCount = 5;
                this.mFrameRateCategoryChangeReason = 100663296;
                this.mFrameRateCategoryView = null;
                this.mDrawnThisFrame = true;
                return;
            }
        }
        float f3 = this.mPreferredFrameRate;
        if (f > f3) {
            f2 = f;
        } else {
            i = this.mFrameRateCompatibility;
            f2 = f3;
        }
        if (f3 > 0.0f && f3 % f != 0.0f && f % f3 != 0.0f) {
            this.mIsFrameRateConflicted = true;
            if (f2 > 60.0f && this.mFrameRateCategoryHighCount != 5) {
                this.mFrameRateCategoryHighCount = 5;
                this.mFrameRateCategoryChangeReason = 167772160;
                this.mFrameRateCategoryView = null;
            } else if (this.mFrameRateCategoryHighCount == 0 && this.mFrameRateCategoryHighHintCount == 0 && this.mFrameRateCategoryNormalCount < 5) {
                this.mFrameRateCategoryNormalCount = 5;
                this.mFrameRateCategoryChangeReason = 167772160;
                this.mFrameRateCategoryView = null;
            }
        }
        this.mPreferredFrameRate = f2;
        this.mFrameRateCompatibility = i;
        this.mDrawnThisFrame = true;
    }

    public int getPreferredFrameRateCategory() {
        return this.mPreferredFrameRateCategory;
    }

    public int getLastPreferredFrameRateCategory() {
        return this.mLastPreferredFrameRateCategory;
    }

    public float getPreferredFrameRate() {
        float f = this.mPreferredFrameRate;
        return f >= 0.0f ? f : this.mLastPreferredFrameRate;
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

    private void boostFrameRate(int i) {
        this.mIsFrameRateBoosting = true;
        this.mHandler.removeMessages(39);
        this.mHandler.sendEmptyMessageDelayed(39, i);
    }

    void setBackKeyCallbackForWindowlessWindow(Predicate<KeyEvent> predicate) {
        this.mWindowlessBackKeyCallback = predicate;
    }

    void recordViewPercentage(float f) {
        if (Trace.isEnabled()) {
            this.mLargestChildPercentage = Math.max(f, this.mLargestChildPercentage);
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
        return sEnableVrr && sToolkitFrameRateViewEnablingReadOnlyFlagValue && sToolkitSetFrameRateReadOnlyFlagValue && isFrameRatePowerSavingsBalanced();
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
        long j = this.mAttachInfo.mDrawingTime;
        int min = (int) Math.min(2147483647L, j - this.mLastUpdateTimeMillis);
        this.mMinusTwoFrameIntervalMillis = this.mMinusOneFrameIntervalMillis;
        this.mMinusOneFrameIntervalMillis = min;
        this.mLastUpdateTimeMillis = j;
        if (this.mThreadedRendererViews.isEmpty() && min + this.mMinusTwoFrameIntervalMillis >= 100) {
            int i = this.mInfrequentUpdateCount;
            if (i != 2) {
                i++;
            }
            this.mInfrequentUpdateCount = i;
            return;
        }
        this.mInfrequentUpdateCount = 0;
    }

    private void logColorMode(int i, boolean z) {
        if (this.mColorModeLastSetMillis == -1 && z) {
            Log.d(TAG, "Skipping stats log for color mode");
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (z) {
            HwuiStatsLog.write(946, Process.myUid(), currentTimeMillis - this.mColorModeLastSetMillis, this.mCurrentColorMode);
            this.mColorModeLastSetMillis = -1L;
        } else {
            if (this.mColorModeLastSetMillis > 0) {
                HwuiStatsLog.write(946, Process.myUid(), currentTimeMillis - this.mColorModeLastSetMillis, this.mCurrentColorMode);
            }
            this.mColorModeLastSetMillis = currentTimeMillis;
        }
        this.mCurrentColorMode = i;
    }

    private void initializeProtoLogInProcess() {
        if (sProtoLogInitialized) {
            return;
        }
        ProtoLog.init(ViewProtoLogGroups.ALL_GROUPS);
        sProtoLogInitialized = true;
    }

    private void preInitBufferAllocator() {
        if (com.android.graphics.hwui.flags.Flags.earlyPreinitBufferAllocator()) {
            ThreadedRenderer.preInitBufferAllocator();
        }
    }

    final class SmartClipRemoteRequestDispatcherProxy {
        private boolean DEBUG;
        private Context mContext;
        private SmartClipRemoteRequestDispatcher mDispatcher;
        private final String TAG = "SmartClipRemoteRequestDispatcher_ViewRootImpl";
        private SmartClipRemoteRequestDispatcher.ViewRootImplGateway mGateway = new SmartClipRemoteRequestDispatcher.ViewRootImplGateway() { // from class: android.view.ViewRootImpl.SmartClipRemoteRequestDispatcherProxy.1
            @Override // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.ViewRootImplGateway
            public PointF getTranslatedPoint() {
                return null;
            }

            @Override // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.ViewRootImplGateway
            public void enqueueInputEvent(InputEvent inputEvent, InputEventReceiver inputEventReceiver, int i, boolean z) {
                ViewRootImpl.this.enqueueInputEvent(inputEvent, inputEventReceiver, i, z);
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
            public void getTranslatedRectIfNeeded(Rect rect) {
                ViewRootImpl.this.applyViewBoundsSandboxingIfNeeded(rect, true);
            }
        };

        public SmartClipRemoteRequestDispatcherProxy(Context context) {
            this.DEBUG = false;
            this.mContext = context;
            SmartClipRemoteRequestDispatcher smartClipRemoteRequestDispatcher = new SmartClipRemoteRequestDispatcher(context, this.mGateway);
            this.mDispatcher = smartClipRemoteRequestDispatcher;
            this.DEBUG = smartClipRemoteRequestDispatcher.isDebugMode();
        }

        public void dispatchSmartClipRemoteRequest(final SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) {
            if (this.DEBUG) {
                Log.i("SmartClipRemoteRequestDispatcher_ViewRootImpl", "dispatchSmartClipRemoteRequest : req id=" + smartClipRemoteRequestInfo.mRequestId + " type=" + smartClipRemoteRequestInfo.mRequestType + " pid=" + smartClipRemoteRequestInfo.mCallerPid + " uid=" + smartClipRemoteRequestInfo.mCallerUid);
            }
            if (smartClipRemoteRequestInfo.mRequestType == 1) {
                this.mDispatcher.checkPermission("com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA", smartClipRemoteRequestInfo.mCallerPid, smartClipRemoteRequestInfo.mCallerUid);
                ViewRootImpl.this.mHandler.post(new Runnable() { // from class: android.view.ViewRootImpl.SmartClipRemoteRequestDispatcherProxy.2
                    @Override // java.lang.Runnable
                    public void run() {
                        SmartClipRemoteRequestDispatcherProxy.this.dispatchSmartClipMetaDataExtraction(smartClipRemoteRequestInfo);
                    }
                });
            } else {
                this.mDispatcher.dispatchSmartClipRemoteRequest(smartClipRemoteRequestInfo);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchSmartClipMetaDataExtraction(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) {
            SmartClipDataExtractionEvent smartClipDataExtractionEvent = (SmartClipDataExtractionEvent) smartClipRemoteRequestInfo.mRequestData;
            smartClipDataExtractionEvent.mRequestId = smartClipRemoteRequestInfo.mRequestId;
            smartClipDataExtractionEvent.mTargetWindowLayer = smartClipRemoteRequestInfo.mTargetWindowLayer;
            if (ViewRootImpl.this.mView != null) {
                new SmartClipDataCropperImpl(ViewRootImpl.this.mView.getContext(), smartClipDataExtractionEvent).doExtractSmartClipData(ViewRootImpl.this.mView);
            } else {
                new SmartClipDataCropperImpl(this.mContext, smartClipDataExtractionEvent).sendExtractionResultToSmartClipService(null);
            }
        }
    }

    public void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) {
        SmartClipRemoteRequestDispatcherProxy smartClipRemoteRequestDispatcherProxy = this.mSmartClipDispatcherProxy;
        if (smartClipRemoteRequestDispatcherProxy != null) {
            smartClipRemoteRequestDispatcherProxy.dispatchSmartClipRemoteRequest(smartClipRemoteRequestInfo);
            return;
        }
        Log.e(TAG, "dispatchSmartClipRemoteRequest : SmartClip dispatcher is null! req id=" + smartClipRemoteRequestInfo.mRequestId + " type=" + smartClipRemoteRequestInfo.mRequestType);
    }

    public void setTspDeadzone(Bundle bundle) {
        if (CoreRune.FW_TSP_STATE_CONTROLLER) {
            try {
                this.mWindowSession.setTspDeadzone(this.mWindow, bundle);
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

    public void setTspNoteMode(boolean z) {
        if (CoreRune.FW_TSP_NOTE_MODE && this.mView != null && this.mAdded) {
            try {
                this.mWindowSession.setTspNoteMode(this.mWindow, z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static class MotionEventMonitor {
        private static boolean DEBUG = false;
        private static final String TAG = "MotionEventMonitor";
        private ArrayList<OnTouchListener> mListeners = new ArrayList<>();

        public interface OnTouchListener {
            void onTouch(MotionEvent motionEvent);
        }

        public void registerMotionEventMonitor(OnTouchListener onTouchListener) {
            if (this.mListeners.size() > 0) {
                Log.e(TAG, "registerMotionEventMonitor : Just one event listener is allowed");
                return;
            }
            this.mListeners.add(onTouchListener);
            if (DEBUG) {
                Log.i(TAG, "registerMotionEventMonitor : Listener count=" + this.mListeners.size());
            }
        }

        public void unregisterMotionEventMonitor(OnTouchListener onTouchListener) {
            this.mListeners.remove(onTouchListener);
            if (DEBUG) {
                Log.i(TAG, "unregisterMotionEventMonitor : Listener count=" + this.mListeners.size());
            }
        }

        public void dispatchInputEvent(InputEvent inputEvent) {
            if (this.mListeners.size() == 0) {
                return;
            }
            if (inputEvent instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                int action = motionEvent.getAction();
                if (DEBUG) {
                    Log.i(TAG, "dispatchInputEvent : action=" + action);
                }
                if (action == 0 || action == 1 || action == 3 || action == 7 || action == 9 || action == 10) {
                    notifyTouchEvent(motionEvent);
                    return;
                }
                return;
            }
            if (DEBUG) {
                Log.i(TAG, "dispatchInputEvent : The event is not instance of MotionEvent");
            }
        }

        private void notifyTouchEvent(MotionEvent motionEvent) {
            int size = this.mListeners.size();
            Log.i(TAG, "notifyTouchEvent : Listener cnt=" + size);
            for (int i = 0; i < size; i++) {
                OnTouchListener onTouchListener = this.mListeners.get(i);
                if (onTouchListener != null) {
                    onTouchListener.onTouch(motionEvent);
                }
            }
        }
    }

    public MotionEventMonitor getMotionEventMonitor() {
        return this.mMotionEventMonitor;
    }

    private boolean isWheelScrollingHandled(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mFlexPanelScrollY = motionEvent.getY();
            this.mFlexPanelScrollEnabled = false;
            return true;
        }
        if (action != 1) {
            if (action == 2) {
                if (this.mFlexPanelScrollEnabled) {
                    this.mView.cancelLongPress();
                    return false;
                }
                if (Math.abs(motionEvent.getY() - this.mFlexPanelScrollY) <= ViewConfiguration.get(this.mContext).getScaledTouchSlop() + 1) {
                    return true;
                }
                this.mFlexPanelScrollEnabled = true;
                MotionEvent copy = motionEvent.copy();
                copy.setLocation(motionEvent.getX(), this.mFlexPanelScrollY);
                copy.setAction(0);
                this.mView.dispatchPointerEvent(copy);
                return this.mView == null;
            }
            if (action != 3) {
                return false;
            }
        }
        this.mFlexPanelScrollEnabled = false;
        return false;
    }

    public void requestRecomputeViewAttributes() {
        if (this.mIsInTraversal) {
            this.mHandler.post(new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    ViewRootImpl.this.lambda$requestRecomputeViewAttributes$21();
                }
            });
        } else {
            this.mAttachInfo.mRecomputeGlobalAttributes = true;
        }
        if (DEBUG_TRAVERSAL && DEBUG_TRAVERSAL_PACKAGE_NAME.equals(ActivityThread.currentPackageName())) {
            Log.i(this.mTag, "Traversal, [19] mView=" + this.mView);
        }
        scheduleTraversals();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestRecomputeViewAttributes$21() {
        this.mAttachInfo.mRecomputeGlobalAttributes = true;
    }

    public void dispatchSPenGestureEvent(InputEvent[] inputEventArr) {
        Message obtainMessage = this.mHandler.obtainMessage(103);
        obtainMessage.obj = inputEventArr;
        this.mHandler.sendMessage(obtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDispatchSPenGestureEvent(InputEvent[] inputEventArr) {
        if (inputEventArr == null) {
            Slog.e(TAG, "dispatchSPenGestureEventInjection : Event is null!");
            return;
        }
        long eventTime = inputEventArr.length > 0 ? inputEventArr[0].getEventTime() : -1L;
        for (final InputEvent inputEvent : inputEventArr) {
            if (inputEvent != null) {
                Runnable runnable = new Runnable() { // from class: android.view.ViewRootImpl$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewRootImpl.this.lambda$handleDispatchSPenGestureEvent$22(inputEvent);
                    }
                };
                long eventTime2 = inputEvent.getEventTime() - eventTime;
                if (eventTime2 > 0) {
                    this.mHandler.postDelayed(runnable, eventTime2);
                } else {
                    runnable.run();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDispatchSPenGestureEvent$22(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            translateSPenGestureEventPositionToAppWindow((MotionEvent) inputEvent);
        }
        enqueueInputEvent(inputEvent, null, 0, true);
    }

    private void translateSPenGestureEventPositionToAppWindow(MotionEvent motionEvent) {
        if (this.mWinFrame.left == 0 && this.mWinFrame.top == 0) {
            return;
        }
        motionEvent.setLocation(motionEvent.getRawX() - this.mWinFrame.left, motionEvent.getRawY() - this.mWinFrame.top);
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

    public boolean isFlingFrameRateChange() {
        return this.mFlingFrameRateChange == 1;
    }

    public Choreographer getChoreographer() {
        return this.mChoreographer;
    }
}
