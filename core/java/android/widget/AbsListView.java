package android.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ActivityThread;
import android.app.KeyguardManager;
import android.app.slice.Slice;
import android.appwidget.AppWidgetManager;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.os.Trace;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Display;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.HapticScrollFeedbackProvider;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View$InspectionCompanion$$ExternalSyntheticLambda0;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewStructure;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.ContentCaptureSession;
import android.view.flags.Flags;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.SurroundingText;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;

import com.android.internal.R;

import com.samsung.android.animation.SemSweepListAnimator;
import com.samsung.android.os.SemPerfManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public abstract class AbsListView extends AdapterView<ListAdapter>
        implements TextWatcher,
                ViewTreeObserver.OnGlobalLayoutListener,
                Filter.FilterListener,
                ViewTreeObserver.OnTouchModeChangeListener,
                RemoteViewsAdapter.RemoteAdapterConnectionCallback {
    private static final String APPWIDGET_CURRENT_POSITION_ACTION =
            "com.sec.android.app.clockpackage.APPWIDGET_CURRENT_POSITION";
    private static final String APPWIDGET_EXTRA_CURRENT_POSITION = "appwidgetCurrentPosition";
    private static final String APPWIDGET_EXTRA_FIRST_POSITION = "appwidgetFirstPosition";
    private static final String APPWIDGET_FIRST_POSITION_ACTION =
            "android.widget.ListView.APPWIDGET_FIRST_POSITION";
    static final int APP_WIDGET_BROADCAST_CURRENT_POSITION_TYPE = 1;
    static final int APP_WIDGET_BROADCAST_FIRST_POSITION_TYPE = 2;
    private static final int APP_WIDGET_INDICATOR_ALPHA = 255;
    private static final int APP_WIDGET_INDICATOR_LEFT = 1;
    private static final int APP_WIDGET_INDICATOR_MAX_COUNT = 20;
    private static final int APP_WIDGET_INDICATOR_RIGHT = 2;
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
    public static final int CHOICE_MODE_MULTIPLE = 2;
    public static final int CHOICE_MODE_MULTIPLE_MODAL = 3;
    public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;
    private static final int DRAGSCROLL_WORKING_ZONE_DP = 25;
    private static final float FLING_DESTRETCH_FACTOR = 4.0f;
    private static final int GTP_STATE_NONE = 0;
    private static final int GTP_STATE_PRESSED = 2;
    private static final int GTP_STATE_SHOWN = 1;
    private static final int HOVERSCROLL_DELAY = 0;
    private static final int HOVERSCROLL_DOWN = 2;
    private static final int HOVERSCROLL_HEIGHT_BOTTOM_DP = 25;
    private static final int HOVERSCROLL_HEIGHT_TOP_DP = 25;
    private static final float HOVERSCROLL_SPEED = 15.0f;
    private static final int HOVERSCROLL_UP = 1;
    private static final int INVALID_POINTER = -1;
    static final int LAYOUT_FORCE_BOTTOM = 3;
    static final int LAYOUT_FORCE_TOP = 1;
    static final int LAYOUT_MOVE_SELECTION = 6;
    static final int LAYOUT_NORMAL = 0;
    static final int LAYOUT_SET_SELECTION = 2;
    static final int LAYOUT_SPECIFIC = 4;
    static final int LAYOUT_SYNC = 5;
    private static final int MSG_HOVERSCROLL_MOVE = 1;
    static final int OVERSCROLL_LIMIT_DIVISOR = 3;
    private static final boolean PROFILE_FLINGING = false;
    private static final boolean PROFILE_SCROLLING = false;
    public static final int SEM_GO_TO_TOP_BUTTON_STYLE_BLACK = 1;
    public static final int SEM_GO_TO_TOP_BUTTON_STYLE_WHITE = 0;
    private static final String TAG = "AbsListView";
    static final int TOUCH_MODE_DONE_WAITING = 2;
    static final int TOUCH_MODE_DOWN = 0;
    static final int TOUCH_MODE_FLING = 4;
    private static final int TOUCH_MODE_OFF = 1;
    private static final int TOUCH_MODE_ON = 0;
    static final int TOUCH_MODE_OVERFLING = 6;
    static final int TOUCH_MODE_OVERSCROLL = 5;
    static final int TOUCH_MODE_REST = -1;
    static final int TOUCH_MODE_SCROLL = 3;
    static final int TOUCH_MODE_TAP = 1;
    private static final int TOUCH_MODE_UNKNOWN = -1;
    public static final int TRANSCRIPT_MODE_ALWAYS_SCROLL = 2;
    public static final int TRANSCRIPT_MODE_DISABLED = 0;
    public static final int TRANSCRIPT_MODE_NORMAL = 1;
    private int GO_TO_TOP_HIDE;
    private final int ON_ABSORB_VELOCITY;
    private final int SWITCH_CONTROL_SCROLL_MAX_DURATION;
    private final int SWITCH_CONTROL_SCROLL_MIN_DURATION;
    private ListItemAccessibilityDelegate mAccessibilityDelegate;
    private int mActivePointerId;
    ListAdapter mAdapter;
    boolean mAdapterHasStableIds;
    boolean mAllowDeferNotifyAfterRemoteViewsAdapterSet;
    ValueAnimator mAnimator;
    private boolean mAppWidgetEnabled;
    private boolean mAppWidgetFastScroll;
    private String mAppWidgetGetCurrentPosition;
    private String mAppWidgetGetFirstPosition;
    private boolean mAppWidgetGoToTop;
    private int mAppWidgetGoToTopOffset;
    boolean mAppWidgetImmersiveEnalbed;
    boolean mAppWidgetIndicator;
    boolean mAppWidgetInnerFocus;
    boolean mAppWidgetSnapScroll;
    private int mAutoscrollDuration;
    private int mAutoscrollDurationGap;
    private int mCacheColorHint;
    boolean mCachingActive;
    boolean mCachingStarted;
    SparseBooleanArray mCheckStates;
    LongSparseArray<Integer> mCheckedIdStates;
    int mCheckedItemCount;
    ActionMode mChoiceActionMode;
    int mChoiceMode;
    private Runnable mClearScrollingCache;
    final Map<Integer, ClickableViewState> mClickableViewStates;
    private ContextMenu.ContextMenuInfo mContextMenuInfo;
    private int mCurrentKeyCode;
    private boolean mDVFSLockAcquired;
    AdapterDataSetObserver mDataSetObserver;
    private final DecelerateInterpolator mDecelerateInterpolator;
    private InputConnection mDefInputConnection;
    private boolean mDeferNotifyDataSetChanged;
    private boolean mDeferSetSelectionFromTop;
    private int mDeferSetSelectionPosition;
    private float mDensityScale;
    private DifferentialMotionFlingHelper mDifferentialMotionFlingHelper;
    private int mDirection;
    boolean mDoubleFlingEnabled;
    private int mDragScrollWorkingZonePx;
    boolean mDrawSelectorOnTop;
    public EdgeEffect mEdgeGlowBottom;
    public EdgeEffect mEdgeGlowTop;
    private boolean mEnableVibrationAtLongPress;
    private int mExtraPaddingInBottomHoverArea;
    private int mExtraPaddingInTopHoverArea;
    private FastScroller mFastScroll;
    boolean mFastScrollAlwaysVisible;
    boolean mFastScrollEnabled;
    private int mFastScrollStyle;
    private boolean mFiltered;
    private int mFirstPositionDistanceGuess;
    private int mFirstPressedPoint;
    private boolean mFlingProfilingStarted;
    private FlingRunnable mFlingRunnable;
    private StrictMode.Span mFlingStrictSpan;
    int mFocusedPos;
    private boolean mForceTranscriptScroll;
    private boolean mForcedClick;
    private boolean mGlobalLayoutListenerAddedFilter;
    private RenderNode mGoToTopRenderNode;
    private boolean mGoToToping;
    private HapticScrollFeedbackProvider mHapticScrollFeedbackProvider;
    private int mHasDividerHeight;
    private boolean mHasDivier;
    private boolean mHasPerformedLongPress;
    public boolean mHoverAreaEnter;
    private int mHoverBottomAreaHeight;
    private HoverScrollHandler mHoverHandler;
    private int mHoverPosition;
    private long mHoverRecognitionStartTime;
    private int mHoverScrollDirection;
    private boolean mHoverScrollEnable;
    private long mHoverScrollStartTime;
    private boolean mHoverScrollStateChanged;
    private int mHoverScrollStateForListener;
    private long mHoverScrollTimeInterval;
    private int mHoverTopAreaHeight;
    private boolean mHoveredOnEllipsizedText;
    boolean mHoveringEnabled;
    private int mIndicatorAnimatedSize;
    private int mIndicatorBottomPadding;
    private int mIndicatorDefaultSize;
    private int mIndicatorFocusedSize;
    private List<Integer> mIndicatorIndex;
    private int mIndicatorItemCnt;
    private int mIndicatorMarginHorizontal;
    private Paint mIndicatorPaint;
    private int mIndicatorRectSize;
    private int mIndicatorWhere;
    private boolean mIsChildViewEnabled;
    private boolean mIsCloseChildSetted;
    private boolean mIsCtrlMultiSelection;
    private boolean mIsCtrlkeyPressed;
    private boolean mIsDetaching;
    private boolean mIsDragBlockEnabled;
    private boolean mIsDragScrolled;
    private boolean mIsEnabledPaddingInHoverScroll;
    private boolean mIsFirstMultiSelectionMove;
    private boolean mIsFirstPenClick;
    private boolean mIsHoverOverscrolled;
    private boolean mIsHoverScrolled;
    boolean mIsHoveredByMouse;
    boolean mIsLayoutSpecificDone;
    private boolean mIsLongPressMultiSelection;
    private boolean mIsLongPressTriggeredByKey;
    private boolean mIsMouseHoverScroll;
    private int mIsMouseHoverScrollX;
    private int mIsMouseHoverScrollY;
    private boolean mIsMovedbeforeUP;
    private boolean mIsMultiFocusEnabled;
    private boolean mIsNeedPenSelectIconSet;
    private boolean mIsNeedPenSelection;
    private boolean mIsPenHovered;
    private boolean mIsPenPressed;
    private boolean mIsPenSelectPointerSetted;
    final boolean[] mIsScrap;
    private boolean mIsSemOnClickEnabled;
    private boolean mIsSendHoverScrollState;
    private boolean mIsShiftkeyPressed;
    private boolean mIsTextSelectionStarted;
    private boolean mIsfirstMoveEvent;
    private int mJumpScrollToTopState;
    private int mLastHandledItemCount;
    private int mLastPositionDistanceGuess;
    private int mLastScrollState;
    private int mLastTouchMode;
    int mLastY;
    int mLayoutMode;
    Rect mListPadding;
    private boolean mLongPressMultiSelectionEnabled;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    int mMotionCorrection;
    int mMotionPosition;
    int mMotionViewNewTop;
    int mMotionViewOriginalTop;
    int mMotionX;
    int mMotionY;
    MultiChoiceModeWrapper mMultiChoiceModeCallback;
    private Drawable mMultiFocusImage;
    boolean mNeedLayoutSpecificDone;
    private boolean mNeedsHoverScroll;
    private int mNestedYOffset;
    int mNewFocusedPos;
    private boolean mNewTextViewHoverState;
    View mNextClickable;
    private int mOldAdapterItemCount;
    private int mOldHoverScrollDirection;
    private int mOldKeyCode;
    private boolean mOldTextViewHoverState;
    private OnScrollListener mOnScrollListener;
    private OnScrollOffsetListener mOnScrollOffsetListener;
    private Outline mOutline;
    int mOverflingDistance;
    int mOverscrollDistance;
    int mOverscrollMax;
    private final Thread mOwnerThread;
    private long mPenDragScrollTimeInterval;
    private CheckForDoublePenClick mPendingCheckForDoublePenClick;
    private CheckForKeyLongPress mPendingCheckForKeyLongPress;
    private CheckForLongPress mPendingCheckForLongPress;
    private CheckForTap mPendingCheckForTap;
    private SavedState mPendingSync;
    private PerformClick mPerformClick;
    private int mPointerCount;
    PopupWindow mPopup;
    private boolean mPopupHidden;
    Runnable mPositionScrollAfterLayout;
    AbsPositionScroller mPositionScroller;
    private boolean mPreviousTextViewScroll;
    private InputConnectionWrapper mPublicInputConnection;
    final RecycleBin mRecycler;
    private RemoteViewsAdapter mRemoteAdapter;
    private boolean mReportChildrenToContentCaptureOnNextUpdate;
    int mResurrectToPosition;
    private final int[] mScrollConsumed;
    View mScrollDown;
    private final int[] mScrollOffset;
    private boolean mScrollProfilingStarted;
    private StrictMode.Span mScrollStrictSpan;
    View mScrollUp;
    boolean mScrollingCacheEnabled;
    int mSelectedTop;
    int mSelectionBottomPadding;
    int mSelectionLeftPadding;
    int mSelectionRightPadding;
    int mSelectionTopPadding;
    Drawable mSelector;
    int mSelectorPosition;
    Rect mSelectorRect;
    private int[] mSelectorState;
    private final Runnable mSemAutoHide;
    private boolean mSemCanGoFuther;
    private View mSemCloseChildByBottom;
    private View mSemCloseChildByTop;
    private int mSemCloseChildPositionByBottom;
    private int mSemCloseChildPositionByTop;
    protected int mSemCurrentFocusPosition;
    private boolean mSemCustomMultiChoiceMode;
    private int mSemDistanceFromCloseChildBottom;
    private int mSemDistanceFromCloseChildTop;
    private int mSemDistanceFromTrackedChildTop;
    private int mSemDragBlockBottom;
    private Drawable mSemDragBlockImage;
    private int mSemDragBlockLeft;
    private Rect mSemDragBlockRect;
    private int mSemDragBlockRight;
    private int mSemDragBlockTop;
    private int mSemDragEndX;
    private int mSemDragEndY;
    private ArrayList<Integer> mSemDragSelectedItemArray;
    private int mSemDragSelectedItemSize;
    private int mSemDragSelectedViewPosition;
    private int mSemDragStartX;
    private int mSemDragStartY;
    private boolean mSemEnableGoToTop;
    private SemFastScroller mSemFastScroll;
    boolean mSemFastScrollCustomEffectEnabled;
    public boolean mSemFastScrollEffectState;
    private SemFastScrollEventListener mSemFastScrollEventListener;
    protected boolean mSemForcedDrawEdgeEffect;
    private final Runnable mSemGoToToFadeInRunnable;
    private final Runnable mSemGoToToFadeOutRunnable;
    private Bitmap mSemGoToTopBitmap;
    private ValueAnimator mSemGoToTopFadeInAnimator;
    private ValueAnimator mSemGoToTopFadeOutAnimator;
    private Drawable mSemGoToTopImage;
    private int mSemGoToTopLastState;
    private Drawable mSemGoToTopLightImage;
    private Rect mSemGoToTopRect;
    private int mSemGoToTopState;
    private ArrayList<Integer> mSemPressItemListArray;
    private int mSemScrollAmount;
    private LinkedList<Integer> mSemScrollRemains;
    private boolean mSemSizeChnage;
    private SemSmoothScrollByMove mSemSmoothScrollByMove;
    private View mSemTrackedChild;
    private int mSemTrackedChildPosition;
    private int mShowFadeOutGTP;
    boolean mShowGTPAtFirstTime;
    private boolean mSmoothScrollbarEnabled;
    boolean mStackFromBottom;
    protected SemSweepListAnimator mSweepListAnimator;
    EditText mTextFilter;
    private boolean mTextFilterEnabled;
    private final float[] mTmpPoint;
    private Rect mTouchFrame;
    int mTouchMode;
    private Runnable mTouchModeReset;
    private int mTouchSlop;
    private int mTouchdownX;
    private int mTouchdownY;
    private int mTranscriptMode;
    private float mVelocityScale;
    private VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;
    int mWidthMeasureSpec;
    private static boolean sContentCaptureReportingEnabledByDeviceConfig = false;
    private static DeviceConfig.OnPropertiesChangedListener sDeviceConfigChangeListener = null;
    static final Interpolator sLinearInterpolator = new LinearInterpolator();
    private static int JUMP_SCROLL_TO_TOP_IDLE = 0;
    private static int JUMP_SCROLL_TO_TOP_INITIATED = 1;
    private static int JUMP_SCROLL_TO_TOP_FINISHING = 2;

    public interface MultiChoiceModeListener extends ActionMode.Callback {
        void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z);
    }

    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScroll(AbsListView absListView, int i, int i2, int i3);

        void onScrollStateChanged(AbsListView absListView, int i);
    }

    public interface OnScrollOffsetListener {
        void onScrollMotionDone(AbsListView absListView);

        void onScrollOffsetChanged(AbsListView absListView, int i);
    }

    public interface RecyclerListener {
        void onMovedToScrapHeap(View view);
    }

    public interface SelectionBoundsAdjuster {
        void adjustListItemSelectionBounds(Rect rect);
    }

    public interface SemFastScrollEventListener {
        void onPressed(float f);

        void onReleased(float f);
    }

    public interface SemFluidScrollerEventListener {
        void onPressed(float f);

        void onReleased(float f);
    }

    abstract void fillGap(boolean z);

    abstract int findMotionRow(int i);

    abstract void setSelectionInt(int i);

    public final class InspectionCompanion
            implements android.view.inspector.InspectionCompanion<AbsListView> {
        private int mCacheColorHintId;
        private int mChoiceModeId;
        private int mDrawSelectorOnTopId;
        private int mFastScrollEnabledId;
        private int mListSelectorId;
        private boolean mPropertiesMapped = false;
        private int mScrollingCacheId;
        private int mSmoothScrollbarId;
        private int mStackFromBottomId;
        private int mTextFilterEnabledId;
        private int mTranscriptModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mCacheColorHintId = propertyMapper.mapColor("cacheColorHint", 16843009);
            SparseArray<String> choiceModeEnumMapping = new SparseArray<>();
            choiceModeEnumMapping.put(0, "none");
            choiceModeEnumMapping.put(1, "singleChoice");
            choiceModeEnumMapping.put(2, "multipleChoice");
            choiceModeEnumMapping.put(3, "multipleChoiceModal");
            Objects.requireNonNull(choiceModeEnumMapping);
            this.mChoiceModeId =
                    propertyMapper.mapIntEnum(
                            "choiceMode",
                            16843051,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    choiceModeEnumMapping));
            this.mDrawSelectorOnTopId = propertyMapper.mapBoolean("drawSelectorOnTop", 16843004);
            this.mFastScrollEnabledId = propertyMapper.mapBoolean("fastScrollEnabled", 16843302);
            this.mListSelectorId = propertyMapper.mapObject("listSelector", 16843003);
            this.mScrollingCacheId = propertyMapper.mapBoolean("scrollingCache", 16843006);
            this.mSmoothScrollbarId = propertyMapper.mapBoolean("smoothScrollbar", 16843313);
            this.mStackFromBottomId = propertyMapper.mapBoolean("stackFromBottom", 16843005);
            this.mTextFilterEnabledId = propertyMapper.mapBoolean("textFilterEnabled", 16843007);
            SparseArray<String> transcriptModeEnumMapping = new SparseArray<>();
            transcriptModeEnumMapping.put(0, "disabled");
            transcriptModeEnumMapping.put(1, "normal");
            transcriptModeEnumMapping.put(2, "alwaysScroll");
            Objects.requireNonNull(transcriptModeEnumMapping);
            this.mTranscriptModeId =
                    propertyMapper.mapIntEnum(
                            "transcriptMode",
                            16843008,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    transcriptModeEnumMapping));
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(AbsListView node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readColor(this.mCacheColorHintId, node.getCacheColorHint());
            propertyReader.readIntEnum(this.mChoiceModeId, node.getChoiceMode());
            propertyReader.readBoolean(this.mDrawSelectorOnTopId, node.isDrawSelectorOnTop());
            propertyReader.readBoolean(this.mFastScrollEnabledId, node.isFastScrollEnabled());
            propertyReader.readObject(this.mListSelectorId, node.getSelector());
            propertyReader.readBoolean(this.mScrollingCacheId, node.isScrollingCacheEnabled());
            propertyReader.readBoolean(this.mSmoothScrollbarId, node.isSmoothScrollbarEnabled());
            propertyReader.readBoolean(this.mStackFromBottomId, node.isStackFromBottom());
            propertyReader.readBoolean(this.mTextFilterEnabledId, node.isTextFilterEnabled());
            propertyReader.readIntEnum(this.mTranscriptModeId, node.getTranscriptMode());
        }
    }

    private static class DeviceConfigChangeListener
            implements DeviceConfig.OnPropertiesChangedListener {
        private DeviceConfigChangeListener() {}

        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            if (!Context.CONTENT_CAPTURE_MANAGER_SERVICE.equals(properties.getNamespace())) {
                return;
            }
            for (String key : properties.getKeyset()) {
                if (ContentCaptureManager.DEVICE_CONFIG_PROPERTY_REPORT_LIST_VIEW_CHILDREN.equals(
                        key)) {
                    AbsListView.sContentCaptureReportingEnabledByDeviceConfig =
                            properties.getBoolean(key, false);
                }
            }
        }
    }

    private static void setupDeviceConfigProperties() {
        if (sDeviceConfigChangeListener == null) {
            sContentCaptureReportingEnabledByDeviceConfig =
                    DeviceConfig.getBoolean(
                            Context.CONTENT_CAPTURE_MANAGER_SERVICE,
                            ContentCaptureManager.DEVICE_CONFIG_PROPERTY_REPORT_LIST_VIEW_CHILDREN,
                            false);
            sDeviceConfigChangeListener = new DeviceConfigChangeListener();
            DeviceConfig.addOnPropertiesChangedListener(
                    Context.CONTENT_CAPTURE_MANAGER_SERVICE,
                    ActivityThread.currentApplication().getMainExecutor(),
                    sDeviceConfigChangeListener);
        }
    }

    public AbsListView(Context context) {
        super(context);
        this.mChoiceMode = 0;
        this.mLayoutMode = 0;
        this.mDeferNotifyDataSetChanged = false;
        this.mDrawSelectorOnTop = false;
        this.mSelectorPosition = -1;
        this.mSelectorRect = new Rect();
        this.mRecycler = new RecycleBin();
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mListPadding = new Rect();
        this.mWidthMeasureSpec = 0;
        this.mTouchMode = -1;
        this.mSelectedTop = 0;
        this.mSmoothScrollbarEnabled = true;
        this.mResurrectToPosition = -1;
        this.mContextMenuInfo = null;
        this.mLastTouchMode = -1;
        this.mScrollProfilingStarted = false;
        this.mFlingProfilingStarted = false;
        this.mScrollStrictSpan = null;
        this.mFlingStrictSpan = null;
        this.mLastScrollState = 0;
        this.mReportChildrenToContentCaptureOnNextUpdate = true;
        this.mVelocityScale = 1.0f;
        this.mIsScrap = new boolean[1];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mTmpPoint = new float[2];
        this.mNestedYOffset = 0;
        this.mActivePointerId = -1;
        this.mDirection = 0;
        this.mHasDivier = false;
        this.mHasDividerHeight = 0;
        this.mSemFastScrollEffectState = false;
        this.mSemEnableGoToTop = false;
        this.mSemSizeChnage = false;
        this.mSemCanGoFuther = false;
        this.mSemGoToTopRect = new Rect();
        this.mOutline = new Outline();
        this.mSemGoToTopState = 0;
        this.mSemGoToTopLastState = 0;
        this.mShowFadeOutGTP = 0;
        this.mGoToToping = false;
        this.mShowGTPAtFirstTime = false;
        this.GO_TO_TOP_HIDE = 2500;
        this.mSemGoToToFadeOutRunnable =
                new Runnable() { // from class: android.widget.AbsListView.5
                    @Override // java.lang.Runnable
                    public void run() {
                        AbsListView.this.semPlayGotoToFadeOut();
                    }
                };
        this.mSemGoToToFadeInRunnable =
                new Runnable() { // from class: android.widget.AbsListView.6
                    @Override // java.lang.Runnable
                    public void run() {
                        AbsListView.this.semPlayGotoToFadeIn();
                    }
                };
        this.mSemAutoHide =
                new Runnable() { // from class: android.widget.AbsListView.7
                    @Override // java.lang.Runnable
                    public void run() {
                        AbsListView.this.semSetupGoToTop(0);
                    }
                };
        this.mHoverTopAreaHeight = 0;
        this.mHoverBottomAreaHeight = 0;
        this.mHoverRecognitionStartTime = 0L;
        this.mHoverScrollTimeInterval = 300L;
        this.mPenDragScrollTimeInterval = 500L;
        this.mHoverScrollStartTime = 0L;
        this.mHoverScrollDirection = -1;
        this.mIsHoverOverscrolled = false;
        this.mHoverScrollEnable = true;
        this.mHoverScrollStateChanged = false;
        this.mIsSendHoverScrollState = false;
        this.mNeedsHoverScroll = false;
        this.mHoverScrollStateForListener = 0;
        this.mIsEnabledPaddingInHoverScroll = false;
        this.mExtraPaddingInTopHoverArea = 0;
        this.mExtraPaddingInBottomHoverArea = 0;
        this.mHoverPosition = -1;
        this.mHoveredOnEllipsizedText = false;
        this.mHoverAreaEnter = false;
        this.mIsFirstPenClick = false;
        this.mIsMovedbeforeUP = false;
        this.mIsCtrlkeyPressed = false;
        this.mIsShiftkeyPressed = false;
        this.mIsfirstMoveEvent = true;
        this.mIsPenHovered = false;
        this.mIsPenPressed = false;
        this.mIsTextSelectionStarted = false;
        this.mIsNeedPenSelection = false;
        this.mSemDragSelectedItemSize = 0;
        this.mSemDragSelectedViewPosition = -1;
        this.mIsPenSelectPointerSetted = false;
        this.mIsNeedPenSelectIconSet = false;
        this.mOldTextViewHoverState = false;
        this.mNewTextViewHoverState = false;
        this.mPreviousTextViewScroll = false;
        this.mSemDragBlockRect = new Rect();
        this.mIsDragBlockEnabled = false;
        this.mSemDragStartX = 0;
        this.mSemDragStartY = 0;
        this.mSemDragEndX = 0;
        this.mSemDragEndY = 0;
        this.mSemDragBlockLeft = 0;
        this.mSemDragBlockTop = 0;
        this.mSemDragBlockRight = 0;
        this.mSemDragBlockBottom = 0;
        this.mSemTrackedChild = null;
        this.mSemTrackedChildPosition = -1;
        this.mSemDistanceFromTrackedChildTop = 0;
        this.mIsCloseChildSetted = false;
        this.mOldHoverScrollDirection = -1;
        this.mSemCloseChildByTop = null;
        this.mSemCloseChildPositionByTop = -1;
        this.mSemDistanceFromCloseChildTop = 0;
        this.mSemCloseChildByBottom = null;
        this.mSemCloseChildPositionByBottom = -1;
        this.mSemDistanceFromCloseChildBottom = 0;
        this.mIsSemOnClickEnabled = true;
        this.mIsLongPressMultiSelection = false;
        this.mLongPressMultiSelectionEnabled = false;
        this.mIsFirstMultiSelectionMove = true;
        this.mIsCtrlMultiSelection = false;
        this.mIsHoverScrolled = false;
        this.mIsMouseHoverScroll = false;
        this.mIsHoveredByMouse = false;
        this.mIsMouseHoverScrollX = 0;
        this.mIsMouseHoverScrollY = 0;
        this.mSemCustomMultiChoiceMode = false;
        this.mIsMultiFocusEnabled = false;
        this.mFirstPressedPoint = -1;
        this.mOldAdapterItemCount = 0;
        this.mOldKeyCode = 0;
        this.mCurrentKeyCode = 0;
        this.mSemCurrentFocusPosition = -1;
        this.SWITCH_CONTROL_SCROLL_MIN_DURATION = 100;
        this.SWITCH_CONTROL_SCROLL_MAX_DURATION = 2032;
        this.mAutoscrollDurationGap = 138;
        this.ON_ABSORB_VELOCITY = 10000;
        this.mDVFSLockAcquired = false;
        this.mSemForcedDrawEdgeEffect = false;
        this.mHoveringEnabled = true;
        this.mDoubleFlingEnabled = false;
        this.mEnableVibrationAtLongPress = true;
        this.mIsLongPressTriggeredByKey = false;
        this.mSemSmoothScrollByMove = null;
        this.mSemScrollRemains = null;
        this.mSemScrollAmount = 500;
        this.mJumpScrollToTopState = JUMP_SCROLL_TO_TOP_IDLE;
        this.mDragScrollWorkingZonePx = 0;
        this.mIsDragScrolled = false;
        this.mForcedClick = false;
        this.mPointerCount = 0;
        this.mAppWidgetGoToTopOffset = 0;
        this.mAppWidgetGoToTop = false;
        this.mAppWidgetSnapScroll = false;
        this.mIsLayoutSpecificDone = false;
        this.mNeedLayoutSpecificDone = false;
        this.mAppWidgetEnabled = true;
        this.mAppWidgetGetCurrentPosition = "";
        this.mAppWidgetGetFirstPosition = "";
        this.mAppWidgetFastScroll = false;
        this.mAppWidgetIndicator = false;
        this.mAppWidgetInnerFocus = false;
        this.mIndicatorWhere = 1;
        this.mAppWidgetImmersiveEnalbed = false;
        this.mAllowDeferNotifyAfterRemoteViewsAdapterSet = false;
        this.mDecelerateInterpolator = new DecelerateInterpolator();
        this.mIndicatorItemCnt = 0;
        this.mFocusedPos = 0;
        this.mNewFocusedPos = 0;
        this.mIndicatorAnimatedSize = 0;
        this.mIndicatorRectSize = 0;
        this.mIndicatorMarginHorizontal = 0;
        this.mIndicatorBottomPadding = 0;
        this.mIndicatorFocusedSize = 0;
        this.mIndicatorDefaultSize = 0;
        this.mDeferSetSelectionFromTop = false;
        this.mDeferSetSelectionPosition = 0;
        this.mClickableViewStates = new HashMap();
        setupDeviceConfigProperties();
        this.mEdgeGlowBottom = new EdgeEffect(context);
        this.mEdgeGlowTop = new EdgeEffect(context);
        this.mEdgeGlowBottom.semSetHostView(this, true);
        this.mEdgeGlowTop.semSetHostView(this, true);
        initAbsListView();
        this.mOwnerThread = Thread.currentThread();
        setVerticalScrollBarEnabled(true);
        TypedArray a = context.obtainStyledAttributes(R.styleable.View);
        initializeScrollbarsInternal(a);
        a.recycle();
    }

    public AbsListView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842858);
    }

    public AbsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AbsListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mChoiceMode = 0;
        this.mLayoutMode = 0;
        this.mDeferNotifyDataSetChanged = false;
        this.mDrawSelectorOnTop = false;
        this.mSelectorPosition = -1;
        this.mSelectorRect = new Rect();
        this.mRecycler = new RecycleBin();
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mListPadding = new Rect();
        this.mWidthMeasureSpec = 0;
        this.mTouchMode = -1;
        this.mSelectedTop = 0;
        this.mSmoothScrollbarEnabled = true;
        this.mResurrectToPosition = -1;
        this.mContextMenuInfo = null;
        this.mLastTouchMode = -1;
        this.mScrollProfilingStarted = false;
        this.mFlingProfilingStarted = false;
        this.mScrollStrictSpan = null;
        this.mFlingStrictSpan = null;
        this.mLastScrollState = 0;
        this.mReportChildrenToContentCaptureOnNextUpdate = true;
        this.mVelocityScale = 1.0f;
        this.mIsScrap = new boolean[1];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mTmpPoint = new float[2];
        this.mNestedYOffset = 0;
        this.mActivePointerId = -1;
        this.mDirection = 0;
        this.mHasDivier = false;
        this.mHasDividerHeight = 0;
        this.mSemFastScrollEffectState = false;
        this.mSemEnableGoToTop = false;
        this.mSemSizeChnage = false;
        this.mSemCanGoFuther = false;
        this.mSemGoToTopRect = new Rect();
        this.mOutline = new Outline();
        this.mSemGoToTopState = 0;
        this.mSemGoToTopLastState = 0;
        this.mShowFadeOutGTP = 0;
        this.mGoToToping = false;
        this.mShowGTPAtFirstTime = false;
        this.GO_TO_TOP_HIDE = 2500;
        this.mSemGoToToFadeOutRunnable =
                new Runnable() { // from class: android.widget.AbsListView.5
                    @Override // java.lang.Runnable
                    public void run() {
                        AbsListView.this.semPlayGotoToFadeOut();
                    }
                };
        this.mSemGoToToFadeInRunnable =
                new Runnable() { // from class: android.widget.AbsListView.6
                    @Override // java.lang.Runnable
                    public void run() {
                        AbsListView.this.semPlayGotoToFadeIn();
                    }
                };
        this.mSemAutoHide =
                new Runnable() { // from class: android.widget.AbsListView.7
                    @Override // java.lang.Runnable
                    public void run() {
                        AbsListView.this.semSetupGoToTop(0);
                    }
                };
        this.mHoverTopAreaHeight = 0;
        this.mHoverBottomAreaHeight = 0;
        this.mHoverRecognitionStartTime = 0L;
        this.mHoverScrollTimeInterval = 300L;
        this.mPenDragScrollTimeInterval = 500L;
        this.mHoverScrollStartTime = 0L;
        this.mHoverScrollDirection = -1;
        this.mIsHoverOverscrolled = false;
        this.mHoverScrollEnable = true;
        this.mHoverScrollStateChanged = false;
        this.mIsSendHoverScrollState = false;
        this.mNeedsHoverScroll = false;
        this.mHoverScrollStateForListener = 0;
        this.mIsEnabledPaddingInHoverScroll = false;
        this.mExtraPaddingInTopHoverArea = 0;
        this.mExtraPaddingInBottomHoverArea = 0;
        this.mHoverPosition = -1;
        this.mHoveredOnEllipsizedText = false;
        this.mHoverAreaEnter = false;
        this.mIsFirstPenClick = false;
        this.mIsMovedbeforeUP = false;
        this.mIsCtrlkeyPressed = false;
        this.mIsShiftkeyPressed = false;
        this.mIsfirstMoveEvent = true;
        this.mIsPenHovered = false;
        this.mIsPenPressed = false;
        this.mIsTextSelectionStarted = false;
        this.mIsNeedPenSelection = false;
        this.mSemDragSelectedItemSize = 0;
        this.mSemDragSelectedViewPosition = -1;
        this.mIsPenSelectPointerSetted = false;
        this.mIsNeedPenSelectIconSet = false;
        this.mOldTextViewHoverState = false;
        this.mNewTextViewHoverState = false;
        this.mPreviousTextViewScroll = false;
        this.mSemDragBlockRect = new Rect();
        this.mIsDragBlockEnabled = false;
        this.mSemDragStartX = 0;
        this.mSemDragStartY = 0;
        this.mSemDragEndX = 0;
        this.mSemDragEndY = 0;
        this.mSemDragBlockLeft = 0;
        this.mSemDragBlockTop = 0;
        this.mSemDragBlockRight = 0;
        this.mSemDragBlockBottom = 0;
        this.mSemTrackedChild = null;
        this.mSemTrackedChildPosition = -1;
        this.mSemDistanceFromTrackedChildTop = 0;
        this.mIsCloseChildSetted = false;
        this.mOldHoverScrollDirection = -1;
        this.mSemCloseChildByTop = null;
        this.mSemCloseChildPositionByTop = -1;
        this.mSemDistanceFromCloseChildTop = 0;
        this.mSemCloseChildByBottom = null;
        this.mSemCloseChildPositionByBottom = -1;
        this.mSemDistanceFromCloseChildBottom = 0;
        this.mIsSemOnClickEnabled = true;
        this.mIsLongPressMultiSelection = false;
        this.mLongPressMultiSelectionEnabled = false;
        this.mIsFirstMultiSelectionMove = true;
        this.mIsCtrlMultiSelection = false;
        this.mIsHoverScrolled = false;
        this.mIsMouseHoverScroll = false;
        this.mIsHoveredByMouse = false;
        this.mIsMouseHoverScrollX = 0;
        this.mIsMouseHoverScrollY = 0;
        this.mSemCustomMultiChoiceMode = false;
        this.mIsMultiFocusEnabled = false;
        this.mFirstPressedPoint = -1;
        this.mOldAdapterItemCount = 0;
        this.mOldKeyCode = 0;
        this.mCurrentKeyCode = 0;
        this.mSemCurrentFocusPosition = -1;
        this.SWITCH_CONTROL_SCROLL_MIN_DURATION = 100;
        this.SWITCH_CONTROL_SCROLL_MAX_DURATION = 2032;
        this.mAutoscrollDurationGap = 138;
        this.ON_ABSORB_VELOCITY = 10000;
        this.mDVFSLockAcquired = false;
        this.mSemForcedDrawEdgeEffect = false;
        this.mHoveringEnabled = true;
        this.mDoubleFlingEnabled = false;
        this.mEnableVibrationAtLongPress = true;
        this.mIsLongPressTriggeredByKey = false;
        this.mSemSmoothScrollByMove = null;
        this.mSemScrollRemains = null;
        this.mSemScrollAmount = 500;
        this.mJumpScrollToTopState = JUMP_SCROLL_TO_TOP_IDLE;
        this.mDragScrollWorkingZonePx = 0;
        this.mIsDragScrolled = false;
        this.mForcedClick = false;
        this.mPointerCount = 0;
        this.mAppWidgetGoToTopOffset = 0;
        this.mAppWidgetGoToTop = false;
        this.mAppWidgetSnapScroll = false;
        this.mIsLayoutSpecificDone = false;
        this.mNeedLayoutSpecificDone = false;
        this.mAppWidgetEnabled = true;
        this.mAppWidgetGetCurrentPosition = "";
        this.mAppWidgetGetFirstPosition = "";
        this.mAppWidgetFastScroll = false;
        this.mAppWidgetIndicator = false;
        this.mAppWidgetInnerFocus = false;
        this.mIndicatorWhere = 1;
        this.mAppWidgetImmersiveEnalbed = false;
        this.mAllowDeferNotifyAfterRemoteViewsAdapterSet = false;
        this.mDecelerateInterpolator = new DecelerateInterpolator();
        this.mIndicatorItemCnt = 0;
        this.mFocusedPos = 0;
        this.mNewFocusedPos = 0;
        this.mIndicatorAnimatedSize = 0;
        this.mIndicatorRectSize = 0;
        this.mIndicatorMarginHorizontal = 0;
        this.mIndicatorBottomPadding = 0;
        this.mIndicatorFocusedSize = 0;
        this.mIndicatorDefaultSize = 0;
        this.mDeferSetSelectionFromTop = false;
        this.mDeferSetSelectionPosition = 0;
        this.mClickableViewStates = new HashMap();
        setupDeviceConfigProperties();
        this.mEdgeGlowBottom = new EdgeEffect(context, attrs);
        this.mEdgeGlowTop = new EdgeEffect(context, attrs);
        this.mEdgeGlowBottom.semSetHostView(this, true);
        this.mEdgeGlowTop.semSetHostView(this, true);
        initAbsListView();
        this.mOwnerThread = Thread.currentThread();
        TypedArray a =
                context.obtainStyledAttributes(
                        attrs, R.styleable.AbsListView, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(
                context, R.styleable.AbsListView, attrs, a, defStyleAttr, defStyleRes);
        Drawable selector = a.getDrawable(0);
        if (selector != null) {
            setSelector(selector);
        }
        this.mDrawSelectorOnTop = a.getBoolean(1, false);
        setStackFromBottom(a.getBoolean(2, false));
        setScrollingCacheEnabled(a.getBoolean(3, true));
        setTextFilterEnabled(a.getBoolean(4, false));
        setTranscriptMode(a.getInt(5, 0));
        setCacheColorHint(a.getColor(6, 0));
        setSmoothScrollbarEnabled(a.getBoolean(9, true));
        setChoiceMode(a.getInt(7, 0));
        setFastScrollEnabled(a.getBoolean(8, false));
        setFastScrollStyle(a.getResourceId(11, 0));
        setFastScrollAlwaysVisible(a.getBoolean(10, false));
        a.recycle();
        if (context.getResources().getConfiguration().uiMode == 6) {
            setRevealOnFocusHint(false);
        }
    }

    private void initAbsListView() {
        setClickable(true);
        setFocusableInTouchMode(true);
        setWillNotDraw(false);
        setAlwaysDrawnWithCacheEnabled(false);
        setScrollingCacheEnabled(true);
        ViewConfiguration configuration = ViewConfiguration.get(this.mContext);
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mVerticalScrollFactor = configuration.getScaledVerticalScrollFactor();
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.mOverscrollDistance = configuration.getScaledOverscrollDistance();
        this.mOverflingDistance = configuration.getScaledOverflingDistance();
        this.mDensityScale = getContext().getResources().getDisplayMetrics().density;
        TypedValue value = new TypedValue();
        boolean resolved =
                this.mContext
                        .getTheme()
                        .resolveAttribute(R.attr.twListMultiSelectBackground, value, true);
        if (resolved) {
            this.mMultiFocusImage = this.mContext.getResources().getDrawable(value.resourceId);
        }
        boolean resolved2 =
                this.mContext.getTheme().resolveAttribute(R.attr.twDragBlockImage, value, true);
        if (resolved2) {
            this.mSemDragBlockImage = this.mContext.getResources().getDrawable(value.resourceId);
        }
        boolean resolved3 =
                this.mContext.getTheme().resolveAttribute(R.attr.semGoToTopStyle, value, true);
        if (resolved3) {
            this.mSemGoToTopLightImage = this.mContext.getResources().getDrawable(value.resourceId);
        }
        if (sIsSamsungBasicInteraction) {
            this.mSemFillOutPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            this.mSemFillOutPaint.setColor(
                    getResources().getColor(R.color.sem_round_and_bgcolor_dark, null));
        }
    }

    @Override // android.widget.AdapterView
    public void setAdapter(ListAdapter adapter) {
        if (adapter != null) {
            this.mAdapterHasStableIds = this.mAdapter.hasStableIds();
            if (this.mChoiceMode != 0
                    && this.mAdapterHasStableIds
                    && this.mCheckedIdStates == null) {
                this.mCheckedIdStates = new LongSparseArray<>();
            }
            if (this.mSemEnableGoToTop) {
                semPlayGotoToFadeOut();
                initGoToTOP();
            }
            this.mSemAdapterChanged = true;
            if (this.mAppWidgetIndicator) {
                initIndicator();
            }
        }
        clearChoices();
        if (this.mIsMultiFocusEnabled && this.mAdapter != null) {
            this.mSemPressItemListArray = new ArrayList<>();
            resetPressItemListArray();
            this.mOldAdapterItemCount = this.mAdapter.getCount();
        }
    }

    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    public boolean isItemChecked(int position) {
        if (this.mChoiceMode != 0 && this.mCheckStates != null) {
            return this.mCheckStates.get(position);
        }
        return false;
    }

    public int getCheckedItemPosition() {
        if (this.mChoiceMode == 1 && this.mCheckStates != null && this.mCheckStates.size() == 1) {
            return this.mCheckStates.keyAt(0);
        }
        return -1;
    }

    public SparseBooleanArray getCheckedItemPositions() {
        if (this.mChoiceMode != 0) {
            return this.mCheckStates;
        }
        return null;
    }

    public long[] getCheckedItemIds() {
        if (this.mChoiceMode == 0 || this.mCheckedIdStates == null || this.mAdapter == null) {
            return new long[0];
        }
        LongSparseArray<Integer> idStates = this.mCheckedIdStates;
        int count = idStates.size();
        long[] ids = new long[count];
        for (int i = 0; i < count; i++) {
            ids[i] = idStates.keyAt(i);
        }
        return ids;
    }

    public void clearChoices() {
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        this.mCheckedItemCount = 0;
    }

    public void setItemChecked(int position, boolean value) {
        boolean itemCheckChanged;
        if (this.mChoiceMode == 0) {
            return;
        }
        if (value && this.mChoiceMode == 3 && this.mChoiceActionMode == null) {
            if (this.mMultiChoiceModeCallback == null
                    || !this.mMultiChoiceModeCallback.hasWrappedCallback()) {
                throw new IllegalStateException(
                        "AbsListView: attempted to start selection mode for"
                            + " CHOICE_MODE_MULTIPLE_MODAL but no choice mode callback was"
                            + " supplied. Call setMultiChoiceModeListener to set a callback.");
            }
            this.mChoiceActionMode = startActionMode(this.mMultiChoiceModeCallback);
        }
        if (this.mChoiceMode == 2 || this.mChoiceMode == 3) {
            boolean oldValue = this.mCheckStates.get(position);
            this.mCheckStates.put(position, value);
            if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                if (value) {
                    this.mCheckedIdStates.put(
                            this.mAdapter.getItemId(position), Integer.valueOf(position));
                } else {
                    this.mCheckedIdStates.delete(this.mAdapter.getItemId(position));
                }
            }
            itemCheckChanged = oldValue != value;
            if (itemCheckChanged) {
                if (value) {
                    this.mCheckedItemCount++;
                } else {
                    this.mCheckedItemCount--;
                }
            }
            if (this.mChoiceActionMode != null) {
                long id = this.mAdapter.getItemId(position);
                this.mMultiChoiceModeCallback.onItemCheckedStateChanged(
                        this.mChoiceActionMode, position, id, value);
            }
        } else {
            boolean updateIds = this.mCheckedIdStates != null && this.mAdapter.hasStableIds();
            itemCheckChanged = isItemChecked(position) != value;
            if (value || isItemChecked(position)) {
                this.mCheckStates.clear();
                if (updateIds) {
                    this.mCheckedIdStates.clear();
                }
            }
            if (value) {
                this.mCheckStates.put(position, true);
                if (updateIds) {
                    this.mCheckedIdStates.put(
                            this.mAdapter.getItemId(position), Integer.valueOf(position));
                }
                this.mCheckedItemCount = 1;
            } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                this.mCheckedItemCount = 0;
            }
        }
        if (!this.mInLayout && !this.mBlockLayoutRequests && itemCheckChanged) {
            if (!this.mForcedClick) {
                this.mDataChanged = true;
            }
            rememberSyncState();
            requestLayout();
        }
    }

    @Override // android.widget.AdapterView
    public boolean performItemClick(View view, int position, long id) {
        boolean handled = false;
        boolean dispatchItemClick = true;
        if (this.mChoiceMode != 0) {
            boolean checkedStateChanged = false;
            if (this.mChoiceMode == 2
                    || (this.mChoiceMode == 3 && this.mChoiceActionMode != null)) {
                boolean checked = !this.mCheckStates.get(position, false);
                this.mCheckStates.put(position, checked);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    if (checked) {
                        this.mCheckedIdStates.put(
                                this.mAdapter.getItemId(position), Integer.valueOf(position));
                    } else {
                        this.mCheckedIdStates.delete(this.mAdapter.getItemId(position));
                    }
                }
                if (checked) {
                    this.mCheckedItemCount++;
                } else {
                    this.mCheckedItemCount--;
                }
                if (this.mChoiceActionMode != null) {
                    this.mMultiChoiceModeCallback.onItemCheckedStateChanged(
                            this.mChoiceActionMode, position, id, checked);
                    dispatchItemClick = false;
                }
                checkedStateChanged = true;
            } else if (this.mChoiceMode == 1) {
                if (!this.mCheckStates.get(position, false)) {
                    this.mCheckStates.clear();
                    this.mCheckStates.put(position, true);
                    if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                        this.mCheckedIdStates.clear();
                        this.mCheckedIdStates.put(
                                this.mAdapter.getItemId(position), Integer.valueOf(position));
                    }
                    this.mCheckedItemCount = 1;
                } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                    this.mCheckedItemCount = 0;
                }
                checkedStateChanged = true;
            }
            if (checkedStateChanged) {
                updateOnScreenCheckedViews();
            }
            handled = true;
        }
        if (dispatchItemClick) {
            return handled | super.performItemClick(view, position, id);
        }
        return handled;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateOnScreenCheckedViews() {
        int firstPos = this.mFirstPosition;
        int count = getChildCount();
        boolean useActivated = getContext().getApplicationInfo().targetSdkVersion >= 11;
        for (int i = 0; i < count; i++) {
            View childAt = getChildAt(i);
            int position = firstPos + i;
            if (childAt instanceof Checkable) {
                ((Checkable) childAt).setChecked(this.mCheckStates.get(position));
            } else if (useActivated) {
                childAt.setActivated(this.mCheckStates.get(position));
            }
        }
    }

    public int getChoiceMode() {
        return this.mChoiceMode;
    }

    public void setChoiceMode(int choiceMode) {
        this.mChoiceMode = choiceMode;
        if (this.mChoiceActionMode != null) {
            this.mChoiceActionMode.finish();
            this.mChoiceActionMode = null;
        }
        if (this.mChoiceMode != 0) {
            if (this.mCheckStates == null) {
                this.mCheckStates = new SparseBooleanArray(0);
            }
            if (this.mCheckedIdStates == null
                    && this.mAdapter != null
                    && this.mAdapter.hasStableIds()) {
                this.mCheckedIdStates = new LongSparseArray<>(0);
            }
            if (this.mChoiceMode == 3) {
                clearChoices();
                setLongClickable(true);
            }
        }
        if (this.mChoiceMode == 2) {
            this.mIsDragBlockEnabled = true;
            return;
        }
        if (this.mChoiceMode == 3) {
            this.mIsDragBlockEnabled = true;
        } else if (this.mChoiceMode == 0 || this.mChoiceMode == 1) {
            this.mIsDragBlockEnabled = false;
        }
    }

    public void setMultiChoiceModeListener(MultiChoiceModeListener listener) {
        if (this.mMultiChoiceModeCallback == null) {
            this.mMultiChoiceModeCallback = new MultiChoiceModeWrapper();
        }
        this.mMultiChoiceModeCallback.setWrapped(listener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean contentFits() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return true;
        }
        if (childCount != this.mItemCount) {
            return false;
        }
        return getChildAt(0).getTop() >= this.mListPadding.top
                && getChildAt(childCount + (-1)).getBottom()
                        <= getHeight() - this.mListPadding.bottom;
    }

    public void setFastScrollEnabled(final boolean enabled) {
        if (this.mFastScrollEnabled != enabled) {
            this.mFastScrollEnabled = enabled;
            if (isOwnerThread()) {
                if (this.mSemFastScrollCustomEffectEnabled) {
                    semSetFastScrollEnabledUiThread(enabled);
                    return;
                } else {
                    setFastScrollerEnabledUiThread(enabled);
                    return;
                }
            }
            post(
                    new Runnable() { // from class: android.widget.AbsListView.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (AbsListView.this.mSemFastScrollCustomEffectEnabled) {
                                AbsListView.this.semSetFastScrollEnabledUiThread(enabled);
                            } else {
                                AbsListView.this.setFastScrollerEnabledUiThread(enabled);
                            }
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFastScrollerEnabledUiThread(boolean enabled) {
        if (this.mFastScroll != null) {
            this.mFastScroll.setEnabled(enabled);
        } else if (enabled) {
            this.mFastScroll = new FastScroller(this, this.mFastScrollStyle);
            this.mFastScroll.setEnabled(true);
            this.mSemFastScroll = null;
        }
        resolvePadding();
        if (this.mFastScroll != null) {
            this.mFastScroll.updateLayout();
        }
    }

    public void setFastScrollStyle(int styleResId) {
        if (this.mFastScroll == null) {
            this.mFastScrollStyle = styleResId;
        } else {
            this.mFastScroll.setStyle(styleResId);
        }
    }

    public void setFastScrollAlwaysVisible(final boolean alwaysShow) {
        if (this.mFastScrollAlwaysVisible != alwaysShow) {
            if (alwaysShow && !this.mFastScrollEnabled) {
                setFastScrollEnabled(true);
            }
            this.mFastScrollAlwaysVisible = alwaysShow;
            if (isOwnerThread()) {
                setFastScrollerAlwaysVisibleUiThread(alwaysShow);
            } else {
                post(
                        new Runnable() { // from class: android.widget.AbsListView.2
                            @Override // java.lang.Runnable
                            public void run() {
                                AbsListView.this.setFastScrollerAlwaysVisibleUiThread(alwaysShow);
                            }
                        });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFastScrollerAlwaysVisibleUiThread(boolean alwaysShow) {
        if (this.mFastScroll != null) {
            this.mFastScroll.setAlwaysShow(alwaysShow);
        }
    }

    private boolean isOwnerThread() {
        return this.mOwnerThread == Thread.currentThread();
    }

    public boolean isFastScrollAlwaysVisible() {
        return this.mFastScroll == null
                ? this.mFastScrollEnabled && this.mFastScrollAlwaysVisible
                : this.mFastScroll.isEnabled() && this.mFastScroll.isAlwaysShowEnabled();
    }

    @Override // android.view.View
    public int getVerticalScrollbarWidth() {
        if (this.mFastScroll != null && this.mFastScroll.isEnabled()) {
            return Math.max(super.getVerticalScrollbarWidth(), this.mFastScroll.getWidth());
        }
        if (this.mSemFastScroll != null && this.mSemFastScroll.isEnabled()) {
            return Math.max(super.getVerticalScrollbarWidth(), this.mSemFastScroll.getWidth());
        }
        return super.getVerticalScrollbarWidth();
    }

    @ViewDebug.ExportedProperty
    public boolean isFastScrollEnabled() {
        if (this.mFastScroll == null) {
            return this.mFastScrollEnabled;
        }
        return this.mFastScroll.isEnabled();
    }

    @Override // android.view.View
    public void setVerticalScrollbarPosition(int position) {
        super.setVerticalScrollbarPosition(position);
        if (this.mFastScroll != null) {
            this.mFastScroll.setScrollbarPosition(position);
        } else if (this.mSemFastScroll != null) {
            this.mSemFastScroll.setScrollbarPosition(position);
        }
    }

    @Override // android.view.View
    public void setScrollBarStyle(int style) {
        super.setScrollBarStyle(style);
        if (this.mFastScroll != null) {
            this.mFastScroll.setScrollBarStyle(style);
        } else if (this.mSemFastScroll != null) {
            this.mSemFastScroll.setScrollBarStyle(style);
        }
    }

    @Override // android.view.View
    protected boolean isVerticalScrollBarHidden() {
        return isFastScrollEnabled() || semIsFastScrollEnabled();
    }

    public void setSmoothScrollbarEnabled(boolean enabled) {
        this.mSmoothScrollbarEnabled = enabled;
    }

    @ViewDebug.ExportedProperty
    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mOnScrollListener = l;
        invokeOnItemScrollListener();
    }

    void invokeOnItemScrollListener() {
        if (this.mFastScroll != null) {
            this.mFastScroll.onScroll(this.mFirstPosition, getChildCount(), this.mItemCount);
        } else if (this.mSemFastScroll != null) {
            this.mSemFastScroll.onScroll(this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(
                    this, this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        onScrollChanged(0, 0, 0, 0);
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return AbsListView.class.getName();
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        if (isEnabled()) {
            if (canScrollUp()) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
                info.setScrollable(true);
            }
            if (canScrollDown()) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
                info.setScrollable(true);
            }
        }
        info.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
        info.setClickable(false);
        info.addAction(AccessibilityNodeInfo.AccessibilityAction.SEM_ACTION_AUTOSCROLL_ON);
        info.addAction(AccessibilityNodeInfo.AccessibilityAction.SEM_ACTION_AUTOSCROLL_OFF);
    }

    int getSelectionModeForAccessibility() {
        int choiceMode = getChoiceMode();
        switch (choiceMode) {
        }
        return 0;
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        if (super.performAccessibilityActionInternal(action, arguments)) {
            return true;
        }
        int autoScrollSpeedLevel = 7;
        if (arguments != null) {
            int autoScrollSpeedLevelCount = arguments.getInt("auto_scroll_speed_level_count", 15);
            this.mAutoscrollDurationGap = 1932 / (autoScrollSpeedLevelCount - 1);
            autoScrollSpeedLevel = arguments.getInt("auto_scroll_speed_level", 8) - 1;
        }
        switch (action) {
            case 4096:
            case 16908346:
                if (!isEnabled()
                        || (!(canScrollDown() && getLastVisiblePosition() == getCount() - 1)
                                && getLastVisiblePosition() >= getCount() - 1)) {
                    return false;
                }
                int viewportHeight =
                        (getHeight() - this.mListPadding.top) - this.mListPadding.bottom;
                smoothScrollBy(viewportHeight, 200);
                semSendBroadcastPosition(this.mFocusedPos + 1, 1);
                if (this.mAppWidgetIndicator) {
                    semInvalidateIndicator(this.mFocusedPos + 1);
                }
                return true;
            case 8192:
            case 16908344:
                if (!isEnabled() || !canScrollUp()) {
                    return false;
                }
                int viewportHeight2 =
                        (getHeight() - this.mListPadding.top) - this.mListPadding.bottom;
                smoothScrollBy(-viewportHeight2, 200);
                semSendBroadcastPosition(this.mFocusedPos - 1, 1);
                if (this.mAppWidgetIndicator) {
                    semInvalidateIndicator(this.mFocusedPos - 1);
                }
                return true;
            case 4194304:
                Log.d(TAG, "case SEM_ACTION_AUTOSCROLL_ON, canScrollDown = " + canScrollDown());
                if (!isEnabled() || !canScrollDown()) {
                    return false;
                }
                this.mAutoscrollDuration =
                        2032 - (this.mAutoscrollDurationGap * autoScrollSpeedLevel);
                autoScrollWithDuration(this.mAutoscrollDuration);
                return true;
            case 8388608:
                Log.d(TAG, "SEM_ACTION_AUTOSCROLL_OFF");
                smoothScrollBy(0, 0);
                if (this.mPositionScroller != null) {
                    this.mPositionScroller.stop();
                }
                return true;
            case 67108864:
                Log.d(TAG, "SEM_ACTION_AUTOSCROLL_TOP");
                if (!canScrollUp()) {
                    return false;
                }
                smoothScrollToPositionFromTop(0, 0, 0);
                return true;
            case 268435456:
                Log.d(
                        TAG,
                        "SEM_ACTION_AUTOSCROLL_SPEED_UP, current duration = "
                                + this.mAutoscrollDuration);
                if (!canScrollDown()) {
                    return false;
                }
                if (this.mAutoscrollDuration > 100) {
                    this.mAutoscrollDuration -= this.mAutoscrollDurationGap;
                }
                autoScrollWithDuration(this.mAutoscrollDuration);
                return true;
            case 536870912:
                Log.d(
                        TAG,
                        "SEM_ACTION_AUTOSCROLL_SPEED_DOWN, current duration = "
                                + this.mAutoscrollDuration);
                if (!canScrollDown()) {
                    return false;
                }
                if (this.mAutoscrollDuration < 2032) {
                    this.mAutoscrollDuration += this.mAutoscrollDurationGap;
                }
                autoScrollWithDuration(this.mAutoscrollDuration);
                return true;
            default:
                return false;
        }
    }

    private void autoScrollWithDuration(int duration) {
        int firstPosition = getFirstVisiblePosition();
        View firstView = getChildAt(firstPosition);
        View lastView = getChildAt(getLastVisiblePosition());
        int height = 0;
        if (firstView != null) {
            height = firstView.getHeight();
        }
        if (lastView != null) {
            height += lastView.getHeight();
        }
        int count = getCount();
        smoothScrollToPositionFromTop(
                count - 1,
                ((firstPosition * height) / 2) * (-1),
                (count - firstPosition) * duration);
    }

    @ViewDebug.ExportedProperty
    public boolean isScrollingCacheEnabled() {
        return this.mScrollingCacheEnabled;
    }

    public void setScrollingCacheEnabled(boolean enabled) {
        if (this.mScrollingCacheEnabled && !enabled) {
            clearScrollingCache();
        }
        this.mScrollingCacheEnabled = enabled;
    }

    public void setTextFilterEnabled(boolean textFilterEnabled) {
        this.mTextFilterEnabled = textFilterEnabled;
    }

    @ViewDebug.ExportedProperty
    public boolean isTextFilterEnabled() {
        return this.mTextFilterEnabled;
    }

    @Override // android.view.View
    public void getFocusedRect(Rect r) {
        View view = getSelectedView();
        if (view != null && view.getParent() == this) {
            view.getFocusedRect(r);
            offsetDescendantRectToMyCoords(view, r);
        } else {
            super.getFocusedRect(r);
        }
    }

    private void useDefaultSelector() {
        setSelector(getContext().getDrawable(17301602));
    }

    @ViewDebug.ExportedProperty
    public boolean isStackFromBottom() {
        return this.mStackFromBottom;
    }

    public void setStackFromBottom(boolean stackFromBottom) {
        if (this.mStackFromBottom != stackFromBottom) {
            this.mStackFromBottom = stackFromBottom;
            requestLayoutIfNecessary();
        }
    }

    void requestLayoutIfNecessary() {
        if (getChildCount() > 0) {
            resetList();
            requestLayout();
            invalidate();
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<
                        SavedState>() { // from class: android.widget.AbsListView.SavedState.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
        LongSparseArray<Integer> checkIdState;
        SparseBooleanArray checkState;
        int checkedItemCount;
        String filter;
        long firstId;
        int height;
        boolean inActionMode;
        int position;
        long selectedId;
        int viewTop;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.selectedId = in.readLong();
            this.firstId = in.readLong();
            this.viewTop = in.readInt();
            this.position = in.readInt();
            this.height = in.readInt();
            this.filter = in.readString();
            this.inActionMode = in.readByte() != 0;
            this.checkedItemCount = in.readInt();
            this.checkState = in.readSparseBooleanArray();
            int N = in.readInt();
            if (N > 0) {
                this.checkIdState = new LongSparseArray<>();
                for (int i = 0; i < N; i++) {
                    long key = in.readLong();
                    int value = in.readInt();
                    this.checkIdState.put(key, Integer.valueOf(value));
                }
            }
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState,
                  // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeLong(this.selectedId);
            parcel.writeLong(this.firstId);
            parcel.writeInt(this.viewTop);
            parcel.writeInt(this.position);
            parcel.writeInt(this.height);
            parcel.writeString(this.filter);
            parcel.writeByte(this.inActionMode ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.checkedItemCount);
            parcel.writeSparseBooleanArray(this.checkState);
            int size = this.checkIdState != null ? this.checkIdState.size() : 0;
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeLong(this.checkIdState.keyAt(i2));
                parcel.writeInt(this.checkIdState.valueAt(i2).intValue());
            }
        }

        public String toString() {
            return "AbsListView.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " selectedId="
                    + this.selectedId
                    + " firstId="
                    + this.firstId
                    + " viewTop="
                    + this.viewTop
                    + " position="
                    + this.position
                    + " height="
                    + this.height
                    + " filter="
                    + this.filter
                    + " checkState="
                    + this.checkState
                    + "}";
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        EditText textFilter;
        Editable filterText;
        dismissPopup();
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        if (this.mPendingSync != null) {
            ss.selectedId = this.mPendingSync.selectedId;
            ss.firstId = this.mPendingSync.firstId;
            ss.viewTop = this.mPendingSync.viewTop;
            ss.position = this.mPendingSync.position;
            ss.height = this.mPendingSync.height;
            ss.filter = this.mPendingSync.filter;
            ss.inActionMode = this.mPendingSync.inActionMode;
            ss.checkedItemCount = this.mPendingSync.checkedItemCount;
            ss.checkState = this.mPendingSync.checkState;
            ss.checkIdState = this.mPendingSync.checkIdState;
            return ss;
        }
        boolean haveChildren = getChildCount() > 0 && this.mItemCount > 0;
        long selectedId = getSelectedItemId();
        ss.selectedId = selectedId;
        ss.height = getHeight();
        if (selectedId >= 0) {
            ss.viewTop = this.mSelectedTop;
            ss.position = getSelectedItemPosition();
            ss.firstId = -1L;
        } else if (haveChildren && this.mFirstPosition > 0) {
            View v = getChildAt(0);
            ss.viewTop = v.getTop();
            int firstPos = this.mFirstPosition;
            if (firstPos >= this.mItemCount) {
                firstPos = this.mItemCount - 1;
            }
            ss.position = firstPos;
            ss.firstId = this.mAdapter.getItemId(firstPos);
        } else {
            ss.viewTop = 0;
            ss.firstId = -1L;
            ss.position = 0;
        }
        ss.filter = null;
        if (this.mFiltered
                && (textFilter = this.mTextFilter) != null
                && (filterText = textFilter.getText()) != null) {
            ss.filter = filterText.toString();
        }
        ss.inActionMode = this.mChoiceMode == 3 && this.mChoiceActionMode != null;
        if (this.mCheckStates != null) {
            ss.checkState = this.mCheckStates.m5235clone();
        }
        if (this.mCheckedIdStates != null) {
            LongSparseArray<Integer> idState = new LongSparseArray<>();
            int count = this.mCheckedIdStates.size();
            for (int i = 0; i < count; i++) {
                idState.put(this.mCheckedIdStates.keyAt(i), this.mCheckedIdStates.valueAt(i));
            }
            ss.checkIdState = idState;
        }
        ss.checkedItemCount = this.mCheckedItemCount;
        if (this.mRemoteAdapter != null) {
            this.mRemoteAdapter.saveRemoteViewsCache();
        }
        return ss;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = ss.height;
        if (ss.selectedId >= 0) {
            this.mNeedSync = true;
            this.mPendingSync = ss;
            this.mSyncRowId = ss.selectedId;
            this.mSyncPosition = ss.position;
            this.mSpecificTop = ss.viewTop;
            this.mSyncMode = 0;
        } else if (ss.firstId >= 0) {
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectorPosition = -1;
            this.mNeedSync = true;
            this.mPendingSync = ss;
            this.mSyncRowId = ss.firstId;
            this.mSyncPosition = ss.position;
            this.mSpecificTop = ss.viewTop;
            this.mSyncMode = 1;
        }
        setFilterText(ss.filter);
        if (ss.checkState != null) {
            this.mCheckStates = ss.checkState;
        }
        if (ss.checkIdState != null) {
            this.mCheckedIdStates = ss.checkIdState;
        }
        this.mCheckedItemCount = ss.checkedItemCount;
        if (ss.inActionMode && this.mChoiceMode == 3 && this.mMultiChoiceModeCallback != null) {
            this.mChoiceActionMode = startActionMode(this.mMultiChoiceModeCallback);
        }
        requestLayout();
    }

    private boolean acceptFilter() {
        return this.mTextFilterEnabled
                && (getAdapter() instanceof Filterable)
                && ((Filterable) getAdapter()).getFilter() != null;
    }

    public void setFilterText(String filterText) {
        if (this.mTextFilterEnabled && !TextUtils.isEmpty(filterText)) {
            createTextFilter(false);
            this.mTextFilter.lambda$setTextAsync$0(filterText);
            this.mTextFilter.setSelection(filterText.length());
            if (this.mAdapter instanceof Filterable) {
                if (this.mPopup == null) {
                    Filter f = ((Filterable) this.mAdapter).getFilter();
                    f.filter(filterText);
                }
                this.mFiltered = true;
                this.mDataSetObserver.clearSavedState();
            }
        }
    }

    public CharSequence getTextFilter() {
        if (this.mTextFilterEnabled && this.mTextFilter != null) {
            return this.mTextFilter.getText();
        }
        return null;
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus && this.mSelectedPosition < 0 && !isInTouchMode()) {
            if (!isAttachedToWindow() && this.mAdapter != null) {
                this.mDataChanged = true;
                this.mOldItemCount = this.mItemCount;
                this.mItemCount = this.mAdapter.getCount();
            }
            resurrectSelection();
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (!this.mBlockLayoutRequests && !this.mInLayout) {
            super.requestLayout();
        }
    }

    void resetList() {
        removeAllViewsInLayout();
        this.mFirstPosition = 0;
        this.mDataChanged = false;
        this.mPositionScrollAfterLayout = null;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        setSelectedPositionInt(-1);
        setNextSelectedPositionInt(-1);
        this.mSelectedTop = 0;
        this.mSelectorPosition = -1;
        this.mSelectorRect.setEmpty();
        invalidate();
    }

    @Override // android.view.View
    protected int computeVerticalScrollExtent() {
        int count = getChildCount();
        if (count <= 0) {
            return 0;
        }
        if (this.mSmoothScrollbarEnabled) {
            int extent = count * 100;
            View view = getChildAt(0);
            int top = view.getTop();
            int height = view.getHeight();
            if (height > 0) {
                extent += (top * 100) / height;
            }
            View view2 = getChildAt(count - 1);
            int bottom = view2.getBottom();
            int height2 = view2.getHeight();
            if (height2 > 0) {
                return extent - (((bottom - getHeight()) * 100) / height2);
            }
            return extent;
        }
        return 1;
    }

    @Override // android.view.View
    protected int computeVerticalScrollOffset() {
        int index;
        int firstPosition = this.mFirstPosition;
        int childCount = getChildCount();
        if (firstPosition >= 0 && childCount > 0) {
            if (this.mSmoothScrollbarEnabled) {
                View view = getChildAt(0);
                int top = view.getTop();
                int height = view.getHeight();
                if (height > 0) {
                    return Math.max(
                            ((firstPosition * 100) - ((top * 100) / height))
                                    + ((int)
                                            ((this.mScrollY / getHeight())
                                                    * this.mItemCount
                                                    * 100.0f)),
                            0);
                }
            } else {
                int count = this.mItemCount;
                if (firstPosition == 0) {
                    index = 0;
                } else {
                    int index2 = firstPosition + childCount;
                    if (index2 == count) {
                        index = count;
                    } else {
                        int index3 = childCount / 2;
                        index = index3 + firstPosition;
                    }
                }
                return (int) (firstPosition + (childCount * (index / count)));
            }
        }
        return 0;
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        if (this.mSmoothScrollbarEnabled) {
            int result = Math.max(this.mItemCount * 100, 0);
            if (this.mScrollY != 0) {
                return result
                        + Math.abs(
                                (int) ((this.mScrollY / getHeight()) * this.mItemCount * 100.0f));
            }
            return result;
        }
        return this.mItemCount;
    }

    @Override // android.view.View
    protected float getTopFadingEdgeStrength() {
        int count = getChildCount();
        float fadeEdge = super.getTopFadingEdgeStrength();
        if (count == 0) {
            return fadeEdge;
        }
        if (this.mFirstPosition > 0) {
            return 1.0f;
        }
        int top = getChildAt(0).getTop();
        float fadeLength = getVerticalFadingEdgeLength();
        return top < this.mPaddingTop ? (-(top - this.mPaddingTop)) / fadeLength : fadeEdge;
    }

    @Override // android.view.View
    protected float getBottomFadingEdgeStrength() {
        int count = getChildCount();
        float fadeEdge = super.getBottomFadingEdgeStrength();
        if (count == 0) {
            return fadeEdge;
        }
        if ((this.mFirstPosition + count) - 1 < this.mItemCount - 1) {
            return 1.0f;
        }
        int bottom = getChildAt(count - 1).getBottom();
        int height = getHeight();
        float fadeLength = getVerticalFadingEdgeLength();
        return bottom > height - this.mPaddingBottom
                ? ((bottom - height) + this.mPaddingBottom) / fadeLength
                : fadeEdge;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mSelector == null) {
            useDefaultSelector();
        }
        Rect listPadding = this.mListPadding;
        listPadding.left = this.mSelectionLeftPadding + this.mPaddingLeft;
        listPadding.top = this.mSelectionTopPadding + this.mPaddingTop;
        listPadding.right = this.mSelectionRightPadding + this.mPaddingRight;
        listPadding.bottom = this.mSelectionBottomPadding + this.mPaddingBottom;
        if (this.mTranscriptMode == 1) {
            int childCount = getChildCount();
            int listBottom = getHeight() - getPaddingBottom();
            View lastChild = getChildAt(childCount - 1);
            int lastBottom = lastChild != null ? lastChild.getBottom() : listBottom;
            this.mForceTranscriptScroll =
                    this.mFirstPosition + childCount >= this.mLastHandledItemCount
                            && lastBottom <= listBottom;
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mInLayout = true;
        int childCount = getChildCount();
        if (changed) {
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).forceLayout();
            }
            this.mRecycler.markChildrenDirty();
        }
        layoutChildren();
        if (changed) {
            Log.d(TAG, " in onLayout changed ");
            this.mSemSizeChnage = true;
            semSetupGoToTop(-1);
            semAutoHide(1);
        }
        this.mOverscrollMax = (b - t) / 3;
        if (this.mFastScroll != null) {
            this.mFastScroll.onItemCountChanged(getChildCount(), this.mItemCount);
        } else if (this.mSemFastScroll != null) {
            this.mSemFastScroll.onItemCountChanged(getChildCount(), this.mItemCount);
            if (isLayoutRequested()) {
                this.mInLayout = true;
                layoutChildren();
                this.mInLayout = false;
            }
        }
        this.mInLayout = false;
    }

    @Override // android.view.View
    protected boolean setFrame(int left, int top, int right, int bottom) {
        boolean changed = super.setFrame(left, top, right, bottom);
        if (changed) {
            boolean visible = getWindowVisibility() == 0;
            if (this.mFiltered && visible && this.mPopup != null && this.mPopup.isShowing()) {
                positionPopup();
            }
        }
        return changed;
    }

    protected void layoutChildren() {}

    View getAccessibilityFocusedChild(View focusedView) {
        ViewParent viewParent = focusedView.getParent();
        while ((viewParent instanceof View) && viewParent != this) {
            focusedView = viewParent;
            viewParent = viewParent.getParent();
        }
        if (!(viewParent instanceof View)) {
            return null;
        }
        return focusedView;
    }

    void updateScrollIndicators() {
        if (this.mScrollUp != null) {
            this.mScrollUp.setVisibility(canScrollUp() ? 0 : 4);
        }
        if (this.mScrollDown != null) {
            this.mScrollDown.setVisibility(canScrollDown() ? 0 : 4);
        }
    }

    private boolean canScrollUp() {
        boolean canScrollUp = this.mFirstPosition > 0;
        if (!canScrollUp && getChildCount() > 0) {
            View child = getChildAt(0);
            return child.getTop() < this.mListPadding.top;
        }
        return canScrollUp;
    }

    private boolean canScrollDown() {
        int count = getChildCount();
        boolean canScrollDown = this.mFirstPosition + count < this.mItemCount;
        if (!canScrollDown && count > 0) {
            View child = getChildAt(count - 1);
            return child.getBottom() > this.mBottom - this.mListPadding.bottom;
        }
        return canScrollDown;
    }

    @Override // android.widget.AdapterView
    @ViewDebug.ExportedProperty
    public View getSelectedView() {
        if (this.mItemCount > 0 && this.mSelectedPosition >= 0) {
            return getChildAt(this.mSelectedPosition - this.mFirstPosition);
        }
        return null;
    }

    public int getListPaddingTop() {
        return this.mListPadding.top;
    }

    public int getListPaddingBottom() {
        return this.mListPadding.bottom;
    }

    public int getListPaddingLeft() {
        return this.mListPadding.left;
    }

    public int getListPaddingRight() {
        return this.mListPadding.right;
    }

    View obtainView(int position, boolean[] outMetadata) {
        View updatedView;
        Trace.traceBegin(8L, "obtainView");
        outMetadata[0] = false;
        View transientView = this.mRecycler.getTransientStateView(position);
        if (transientView != null) {
            LayoutParams params = (LayoutParams) transientView.getLayoutParams();
            if (params.viewType == this.mAdapter.getItemViewType(position)
                    && (updatedView = this.mAdapter.getView(position, transientView, this))
                            != transientView) {
                setItemViewLayoutParams(updatedView, position);
                this.mRecycler.addScrapView(updatedView, position);
            }
            outMetadata[0] = true;
            transientView.dispatchFinishTemporaryDetach();
            return transientView;
        }
        View scrapView = null;
        if (this.mAdapter.getItemViewType(position) != -2) {
            scrapView = this.mRecycler.getScrapView(position);
        }
        View child = this.mAdapter.getView(position, scrapView, this);
        if (scrapView != null) {
            if (child != scrapView) {
                this.mRecycler.addScrapView(scrapView, position);
            } else if (child.isTemporarilyDetached()) {
                outMetadata[0] = true;
                child.dispatchFinishTemporaryDetach();
            }
        }
        if (child == null) {
            Log.d(TAG, " try again to check child on obtainview");
            child = this.mAdapter.getView(position, null, this);
            if (child == null) {
                Log.d(TAG, " child is null again");
                Log.d(TAG, " position = " + position);
                Log.d(TAG, " mAdapter =" + this.mAdapter);
                Log.d(TAG, " getChildCount = " + getChildCount());
                Log.d(TAG, " mAdapter.getCount = " + this.mAdapter.getCount());
                Log.d(TAG, " mItemCount = " + this.mItemCount);
                Log.d(TAG, " mOldItemCount = " + this.mOldItemCount);
                if (this.mAdapter instanceof HeaderViewListAdapter) {
                    Log.d(
                            TAG,
                            "HeaderCount = "
                                    + ((HeaderViewListAdapter) this.mAdapter).getHeadersCount());
                    Log.d(
                            TAG,
                            "FooterCount = "
                                    + ((HeaderViewListAdapter) this.mAdapter).getFootersCount());
                }
                return null;
            }
        }
        if (this.mCacheColorHint != 0) {
            child.setDrawingCacheBackgroundColor(this.mCacheColorHint);
        }
        if (child.getImportantForAccessibility() == 0) {
            child.setImportantForAccessibility(1);
        }
        setItemViewLayoutParams(child, position);
        if (this.mAccessibilityDelegate == null) {
            this.mAccessibilityDelegate = new ListItemAccessibilityDelegate();
        }
        if (child.getAccessibilityDelegate() == null) {
            child.setAccessibilityDelegate(this.mAccessibilityDelegate);
        }
        Trace.traceEnd(8L);
        return child;
    }

    private void setItemViewLayoutParams(View child, int position) {
        LayoutParams lp;
        ViewGroup.LayoutParams vlp = child.getLayoutParams();
        if (vlp == null) {
            lp = (LayoutParams) generateDefaultLayoutParams();
        } else if (!checkLayoutParams(vlp)) {
            lp = (LayoutParams) generateLayoutParams(vlp);
        } else {
            lp = (LayoutParams) vlp;
        }
        if (this.mAdapterHasStableIds) {
            lp.itemId = this.mAdapter.getItemId(position);
        }
        lp.viewType = this.mAdapter.getItemViewType(position);
        lp.isEnabled = this.mAdapter.isEnabled(position);
        if (lp != vlp) {
            child.setLayoutParams(lp);
        }
    }

    class ListItemAccessibilityDelegate extends View.AccessibilityDelegate {
        ListItemAccessibilityDelegate() {}

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            int position = AbsListView.this.getPositionForView(host);
            AbsListView.this.onInitializeAccessibilityNodeInfoForItem(host, position, info);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean performAccessibilityAction(View host, int action, Bundle arguments) {
            boolean isItemEnabled;
            if (super.performAccessibilityAction(host, action, arguments)) {
                return true;
            }
            int position = AbsListView.this.getPositionForView(host);
            if (position == -1
                    || AbsListView.this.mAdapter == null
                    || position >= AbsListView.this.mAdapter.getCount()) {
                return false;
            }
            ViewGroup.LayoutParams lp = host.getLayoutParams();
            if (lp instanceof LayoutParams) {
                isItemEnabled = ((LayoutParams) lp).isEnabled;
            } else {
                isItemEnabled = false;
            }
            if (!AbsListView.this.isEnabled() || !isItemEnabled) {
                return false;
            }
            switch (action) {
                case 4:
                    if (AbsListView.this.getSelectedItemPosition() == position) {
                        return false;
                    }
                    AbsListView.this.setSelection(position);
                    return true;
                case 8:
                    if (AbsListView.this.getSelectedItemPosition() != position) {
                        return false;
                    }
                    AbsListView.this.setSelection(-1);
                    return true;
                case 16:
                    if (!AbsListView.this.isItemClickable(host)) {
                        return false;
                    }
                    long id = AbsListView.this.getItemIdAtPosition(position);
                    return AbsListView.this.performItemClick(host, position, id);
                case 32:
                    if (!AbsListView.this.isLongClickable()) {
                        return false;
                    }
                    long id2 = AbsListView.this.getItemIdAtPosition(position);
                    return AbsListView.this.performLongPress(host, position, id2);
                default:
                    return false;
            }
        }
    }

    public void onInitializeAccessibilityNodeInfoForItem(
            View view, int position, AccessibilityNodeInfo info) {
        if (position == -1) {
            return;
        }
        boolean isItemActionable = isEnabled();
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof LayoutParams) {
            isItemActionable &= ((LayoutParams) lp).isEnabled;
        }
        if (position == getSelectedItemPosition()) {
            info.setSelected(true);
            addAccessibilityActionIfEnabled(
                    info,
                    isItemActionable,
                    AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_SELECTION);
        } else {
            addAccessibilityActionIfEnabled(
                    info,
                    isItemActionable,
                    AccessibilityNodeInfo.AccessibilityAction.ACTION_SELECT);
        }
        if (isItemClickable(view)) {
            addAccessibilityActionIfEnabled(
                    info, isItemActionable, AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            info.setClickable(isItemActionable);
        }
        if (isLongClickable()) {
            addAccessibilityActionIfEnabled(
                    info,
                    isItemActionable,
                    AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
            info.setLongClickable(isItemActionable);
        }
    }

    private void addAccessibilityActionIfEnabled(
            AccessibilityNodeInfo info,
            boolean enabled,
            AccessibilityNodeInfo.AccessibilityAction action) {
        if (enabled) {
            info.addAction(action);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isItemClickable(View view) {
        return !view.hasExplicitFocusable();
    }

    void positionSelectorLikeTouch(int position, View sel, float x, float y) {
        positionSelector(position, sel, true, x, y);
    }

    void positionSelectorLikeFocus(int position, View sel) {
        if (this.mSelector != null && this.mSelectorPosition != position && position != -1) {
            Rect bounds = this.mSelectorRect;
            float x = bounds.exactCenterX();
            float y = bounds.exactCenterY();
            positionSelector(position, sel, true, x, y);
            return;
        }
        positionSelector(position, sel);
    }

    void positionSelector(int position, View sel) {
        positionSelector(position, sel, false, -1.0f, -1.0f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void positionSelector(
            int position, View view, boolean manageHotspot, float x, float y) {
        boolean positionChanged = position != this.mSelectorPosition;
        if (position != -1) {
            this.mSelectorPosition = position;
        }
        if (this.mAppWidgetInnerFocus) {
            this.mNextClickable = null;
            this.mClickableViewStates.clear();
            if (this.mSelectorPosition == -1 && view != 0) {
                this.mSelectorPosition = getPositionForView(view);
            }
        }
        Rect selectorRect = this.mSelectorRect;
        selectorRect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        if (view instanceof SelectionBoundsAdjuster) {
            ((SelectionBoundsAdjuster) view).adjustListItemSelectionBounds(selectorRect);
        }
        if ((this instanceof SemExpandableListView)
                && (view instanceof FrameLayout)
                && ((FrameLayout) view).getChildCount() > 0) {
            KeyEvent.Callback childAt = ((FrameLayout) view).getChildAt(0);
            if (childAt instanceof SelectionBoundsAdjuster) {
                ((SelectionBoundsAdjuster) childAt).adjustListItemSelectionBounds(selectorRect);
            }
        }
        selectorRect.left -= this.mSelectionLeftPadding;
        selectorRect.top -= this.mSelectionTopPadding;
        selectorRect.right += this.mSelectionRightPadding;
        selectorRect.bottom += this.mSelectionBottomPadding - view.mExtraPaddingBottomForPreference;
        boolean isChildViewEnabled = view.isEnabled();
        if (this.mIsChildViewEnabled != isChildViewEnabled) {
            this.mIsChildViewEnabled = isChildViewEnabled;
        }
        Drawable selector = this.mSelector;
        if (selector != null) {
            if (positionChanged && !shouldShowSelector()) {
                selector.setVisible(false, false);
                selector.setState(StateSet.NOTHING);
            }
            selector.setBounds(selectorRect);
            if (positionChanged) {
                if (getVisibility() == 0) {
                    selector.setVisible(true, false);
                }
                updateSelectorState();
            }
            if (manageHotspot) {
                selector.setHotspot(x, y);
            }
        }
    }

    public boolean isSelectedChildViewEnabled() {
        return this.mIsChildViewEnabled;
    }

    public void setSelectedChildViewEnabled(boolean selectedChildViewEnabled) {
        this.mIsChildViewEnabled = selectedChildViewEnabled;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        int saveCount = 0;
        boolean clipToPadding = (this.mGroupFlags & 34) == 34;
        if (clipToPadding) {
            saveCount = canvas.save();
            int scrollX = this.mScrollX;
            int scrollY = this.mScrollY;
            canvas.clipRect(
                    this.mPaddingLeft + scrollX,
                    this.mPaddingTop + scrollY,
                    ((this.mRight + scrollX) - this.mLeft) - this.mPaddingRight,
                    ((this.mBottom + scrollY) - this.mTop) - this.mPaddingBottom);
            this.mGroupFlags &= -35;
        }
        boolean drawSelectorOnTop = this.mDrawSelectorOnTop;
        if (!drawSelectorOnTop) {
            drawSelector(canvas);
        }
        super.dispatchDraw(canvas);
        if (drawSelectorOnTop) {
            drawSelector(canvas);
        }
        if (clipToPadding) {
            canvas.restoreToCount(saveCount);
            this.mGroupFlags = 34 | this.mGroupFlags;
        }
        if (this.mIsDragBlockEnabled && !this.mIsLongPressMultiSelection) {
            if (this.mSemDragBlockLeft == 0 && this.mSemDragBlockTop == 0) {
                return;
            }
            int trackChildTop = 0;
            int firstChildPosition = getFirstVisiblePosition();
            int lastChildPosition = getLastVisiblePosition();
            if (this.mSemTrackedChildPosition >= firstChildPosition
                    && this.mSemTrackedChildPosition <= lastChildPosition) {
                this.mSemTrackedChild =
                        getChildAt(this.mSemTrackedChildPosition - getFirstVisiblePosition());
                if (this.mSemTrackedChild != null) {
                    trackChildTop = this.mSemTrackedChild.getTop();
                }
                this.mSemDragStartY = this.mSemDistanceFromTrackedChildTop + trackChildTop;
            }
            this.mSemDragBlockTop = Math.min(this.mSemDragStartY, this.mSemDragEndY);
            this.mSemDragBlockBottom = Math.max(this.mSemDragEndY, this.mSemDragStartY);
            this.mSemDragBlockRect.set(
                    this.mSemDragBlockLeft,
                    this.mSemDragBlockTop,
                    this.mSemDragBlockRight,
                    this.mSemDragBlockBottom);
            this.mSemDragBlockImage.setBounds(this.mSemDragBlockRect);
            this.mSemDragBlockImage.draw(canvas);
        }
    }

    @Override // android.view.View
    protected boolean isPaddingOffsetRequired() {
        return (this.mGroupFlags & 34) != 34;
    }

    @Override // android.view.View
    protected int getLeftPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return -this.mPaddingLeft;
    }

    @Override // android.view.View
    protected int getTopPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return -this.mPaddingTop;
    }

    @Override // android.view.View
    protected int getRightPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return this.mPaddingRight;
    }

    @Override // android.view.View
    protected int getBottomPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return this.mPaddingBottom;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void internalSetPadding(int left, int top, int right, int bottom) {
        super.internalSetPadding(left, top, right, bottom);
        if (isLayoutRequested()) {
            handleBoundsChange();
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        handleBoundsChange();
        if (this.mFastScroll != null) {
            this.mFastScroll.onSizeChanged(w, h, oldw, oldh);
        } else if (this.mSemFastScroll != null) {
            this.mSemFastScroll.onSizeChanged(w, h, oldw, oldh);
        }
    }

    void handleBoundsChange() {
        int childCount;
        if (!this.mInLayout && (childCount = getChildCount()) > 0) {
            this.mDataChanged = true;
            rememberSyncState();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                ViewGroup.LayoutParams lp = child.getLayoutParams();
                if (lp == null || lp.width < 1 || lp.height < 1) {
                    child.forceLayout();
                }
            }
        }
    }

    boolean touchModeDrawsInPressedState() {
        switch (this.mTouchMode) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    boolean shouldShowSelector() {
        return (isFocused() && !isInTouchMode()) || (touchModeDrawsInPressedState() && isPressed());
    }

    private void drawSelector(Canvas canvas) {
        if (shouldDrawSelector()) {
            Drawable selector = this.mSelector;
            selector.setBounds(this.mSelectorRect);
            selector.draw(canvas);
        }
        if (this.mIsMultiFocusEnabled) {
            Rect tempSelectorRect = new Rect();
            Iterator<Integer> it = this.mSemPressItemListArray.iterator();
            while (it.hasNext()) {
                Integer selectedPosition = it.next();
                View selectedChild = getChildAt(selectedPosition.intValue() - this.mFirstPosition);
                if (selectedChild != null) {
                    tempSelectorRect.set(
                            selectedChild.getLeft(),
                            selectedChild.getTop(),
                            selectedChild.getRight(),
                            selectedChild.getBottom());
                    this.mMultiFocusImage.setBounds(tempSelectorRect);
                    this.mMultiFocusImage.draw(canvas);
                }
            }
        }
    }

    public final boolean shouldDrawSelector() {
        return !this.mSelectorRect.isEmpty();
    }

    public void setDrawSelectorOnTop(boolean onTop) {
        this.mDrawSelectorOnTop = onTop;
    }

    public boolean isDrawSelectorOnTop() {
        return this.mDrawSelectorOnTop;
    }

    public void setSelector(int resID) {
        setSelector(getContext().getDrawable(resID));
    }

    public void setSelector(Drawable sel) {
        if (this.mSelector != null) {
            this.mSelector.setCallback(null);
            unscheduleDrawable(this.mSelector);
        }
        this.mSelector = sel;
        Rect padding = new Rect();
        sel.getPadding(padding);
        this.mSelectionLeftPadding = padding.left;
        this.mSelectionTopPadding = padding.top;
        this.mSelectionRightPadding = padding.right;
        this.mSelectionBottomPadding = padding.bottom;
        sel.setCallback(this);
        updateSelectorState();
    }

    public Drawable getSelector() {
        return this.mSelector;
    }

    void keyPressed() {
        if (!isEnabled() || !isClickable()) {
            return;
        }
        Drawable selector = this.mSelector;
        Rect selectorRect = this.mSelectorRect;
        if (selector != null) {
            if ((isFocused() || touchModeDrawsInPressedState()) && !selectorRect.isEmpty()) {
                View v = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (v != null && this.mNextClickable == null) {
                    if (v.hasExplicitFocusable()) {
                        return;
                    } else {
                        v.setPressed(true);
                    }
                } else if (this.mAppWidgetInnerFocus && this.mNextClickable != null) {
                    this.mNextClickable.setPressed(true);
                }
                setPressed(true);
                boolean longClickable = isLongClickable();
                Drawable d = selector.getCurrent();
                if (d != null && (d instanceof TransitionDrawable)) {
                    if (longClickable) {
                        ((TransitionDrawable) d)
                                .startTransition(ViewConfiguration.getLongPressTimeout());
                    } else {
                        ((TransitionDrawable) d).resetTransition();
                    }
                }
                Rect bounds = this.mSelectorRect;
                this.mSelector.setHotspot(bounds.exactCenterX(), bounds.exactCenterY());
                if (longClickable && !this.mDataChanged) {
                    if (this.mPendingCheckForKeyLongPress == null) {
                        this.mPendingCheckForKeyLongPress = new CheckForKeyLongPress();
                    } else {
                        removeCallbacks(this.mPendingCheckForKeyLongPress);
                    }
                    this.mPendingCheckForKeyLongPress.rememberWindowAttachCount();
                    postDelayed(
                            this.mPendingCheckForKeyLongPress,
                            ViewConfiguration.getLongPressTimeout());
                }
            }
        }
    }

    public void setScrollIndicators(View up, View down) {
        this.mScrollUp = up;
        this.mScrollDown = down;
    }

    void updateSelectorState() {
        Drawable selector = this.mSelector;
        if (selector != null && selector.isStateful()) {
            if (shouldShowSelector()) {
                if (selector.setState(getDrawableStateForSelector())) {
                    invalidateDrawable(selector);
                    return;
                }
                return;
            }
            selector.setState(StateSet.NOTHING);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateSelectorState();
    }

    private int[] getDrawableStateForSelector() {
        if (this.mIsChildViewEnabled) {
            return super.getDrawableState();
        }
        int enabledState = ENABLED_STATE_SET[0];
        int[] state = onCreateDrawableState(1);
        int enabledPos = -1;
        int i = state.length - 1;
        while (true) {
            if (i < 0) {
                break;
            }
            if (state[i] != enabledState) {
                i--;
            } else {
                enabledPos = i;
                break;
            }
        }
        if (enabledPos >= 0) {
            System.arraycopy(
                    state, enabledPos + 1, state, enabledPos, (state.length - enabledPos) - 1);
        }
        return state;
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable dr) {
        return this.mSelector == dr || super.verifyDrawable(dr) || this.mSemGoToTopImage == dr;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mSelector != null) {
            this.mSelector.jumpToCurrentState();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewTreeObserver treeObserver = getViewTreeObserver();
        treeObserver.addOnTouchModeChangeListener(this);
        if (this.mTextFilterEnabled
                && this.mPopup != null
                && !this.mGlobalLayoutListenerAddedFilter) {
            treeObserver.addOnGlobalLayoutListener(this);
        }
        if (this.mAdapter != null && this.mDataSetObserver == null) {
            this.mDataSetObserver = new AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mDataChanged = true;
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
        }
        if (isLayoutRtl() && this.mFastScroll != null) {
            this.mFastScroll.setScrollbarPosition(getVerticalScrollbarPosition());
        } else if (isLayoutRtl() && this.mSemFastScroll != null) {
            this.mSemFastScroll.setScrollbarPosition(getVerticalScrollbarPosition());
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIsDetaching = true;
        dismissPopup();
        this.mRecycler.clear();
        ViewTreeObserver treeObserver = getViewTreeObserver();
        treeObserver.removeOnTouchModeChangeListener(this);
        if (this.mTextFilterEnabled && this.mPopup != null) {
            treeObserver.removeOnGlobalLayoutListener(this);
            this.mGlobalLayoutListenerAddedFilter = false;
        }
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
            this.mDataSetObserver = null;
        }
        if (this.mScrollStrictSpan != null) {
            this.mScrollStrictSpan.finish();
            this.mScrollStrictSpan = null;
        }
        if (this.mFlingStrictSpan != null) {
            this.mFlingStrictSpan.finish();
            this.mFlingStrictSpan = null;
        }
        if (this.mFlingRunnable != null) {
            removeCallbacks(this.mFlingRunnable);
        }
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        if (this.mClearScrollingCache != null) {
            removeCallbacks(this.mClearScrollingCache);
        }
        if (this.mPerformClick != null) {
            removeCallbacks(this.mPerformClick);
        }
        if (this.mTouchModeReset != null) {
            removeCallbacks(this.mTouchModeReset);
            this.mTouchModeReset.run();
        }
        if (this.mTouchMode != -1) {
            this.mTouchMode = -1;
        }
        releaseAllBoosters();
        this.mIsDetaching = false;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        int i = !isInTouchMode() ? 1 : 0;
        if (!z) {
            setChildrenDrawingCacheEnabled(false);
            if (this.mFlingRunnable != null) {
                removeCallbacks(this.mFlingRunnable);
                this.mFlingRunnable.mSuppressIdleStateChangeCall = false;
                this.mFlingRunnable.endFling();
                if (this.mPositionScroller != null) {
                    this.mPositionScroller.stop();
                }
                if (this.mScrollY != 0) {
                    this.mScrollY = 0;
                    invalidateParentCaches();
                    finishGlows();
                    invalidate();
                }
            }
            dismissPopup();
            if (i == 1) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
        } else {
            if (this.mFiltered && !this.mPopupHidden) {
                showPopup();
            }
            if (i != this.mLastTouchMode && this.mLastTouchMode != -1) {
                if (i == 1) {
                    resurrectSelection();
                } else {
                    hideSelector();
                    this.mLayoutMode = 0;
                    layoutChildren();
                }
            }
        }
        this.mLastTouchMode = i;
        if (!z) {
            releaseAllBoosters();
        }
        semAutoHide(1);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        if (this.mFastScroll != null) {
            this.mFastScroll.setScrollbarPosition(getVerticalScrollbarPosition());
        } else if (this.mSemFastScroll != null) {
            this.mSemFastScroll.setScrollbarPosition(getVerticalScrollbarPosition());
        }
    }

    ContextMenu.ContextMenuInfo createContextMenuInfo(View view, int position, long id) {
        return new AdapterView.AdapterContextMenuInfo(view, position, id);
    }

    @Override // android.view.View
    public void onCancelPendingInputEvents() {
        super.onCancelPendingInputEvents();
        if (this.mPerformClick != null) {
            removeCallbacks(this.mPerformClick);
        }
        if (this.mPendingCheckForTap != null) {
            removeCallbacks(this.mPendingCheckForTap);
        }
        if (this.mPendingCheckForLongPress != null) {
            removeCallbacks(this.mPendingCheckForLongPress);
        }
        if (this.mPendingCheckForKeyLongPress != null) {
            removeCallbacks(this.mPendingCheckForKeyLongPress);
        }
        if (this.mPendingCheckForDoublePenClick != null) {
            removeCallbacks(this.mPendingCheckForDoublePenClick);
        }
    }

    private class WindowRunnnable {
        private int mOriginalAttachCount;

        private WindowRunnnable() {}

        public void rememberWindowAttachCount() {
            this.mOriginalAttachCount = AbsListView.this.getWindowAttachCount();
        }

        public boolean sameWindow() {
            return AbsListView.this.getWindowAttachCount() == this.mOriginalAttachCount;
        }
    }

    private class PerformClick extends WindowRunnnable implements Runnable {
        int mClickMotionPosition;

        private PerformClick() {
            super();
        }

        @Override // java.lang.Runnable
        public void run() {
            View view;
            if (!AbsListView.this.mDataChanged || AbsListView.this.mForcedClick) {
                ListAdapter adapter = AbsListView.this.mAdapter;
                int motionPosition = this.mClickMotionPosition;
                if (adapter != null
                        && AbsListView.this.mItemCount > 0
                        && motionPosition != -1
                        && motionPosition < adapter.getCount()
                        && sameWindow()
                        && adapter.isEnabled(motionPosition)
                        && (view =
                                        AbsListView.this.getChildAt(
                                                motionPosition - AbsListView.this.mFirstPosition))
                                != null) {
                    AbsListView.this.performItemClick(
                            view, motionPosition, adapter.getItemId(motionPosition));
                    if ((AbsListView.this.mIsShiftkeyPressed || AbsListView.this.mIsCtrlkeyPressed)
                            && AbsListView.this.mAdapter != null) {
                        if (AbsListView.this.mIsCtrlkeyPressed) {
                            AbsListView.this.addToPressItemListArray(motionPosition, -1);
                            return;
                        }
                        if (AbsListView.this.mIsShiftkeyPressed) {
                            AbsListView.this.resetPressItemListArray();
                            if (AbsListView.this.mFirstPressedPoint == -1) {
                                AbsListView.this.addToPressItemListArray(motionPosition, -1);
                                AbsListView.this.mFirstPressedPoint = motionPosition;
                            } else {
                                AbsListView.this.addToPressItemListArray(
                                        AbsListView.this.mFirstPressedPoint, motionPosition);
                            }
                        }
                    }
                }
            }
        }
    }

    private class CheckForLongPress extends WindowRunnnable implements Runnable {
        private static final int INVALID_COORD = -1;
        private float mX;
        private float mY;

        private CheckForLongPress() {
            super();
            this.mX = -1.0f;
            this.mY = -1.0f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCoords(float x, float y) {
            this.mX = x;
            this.mY = y;
        }

        @Override // java.lang.Runnable
        public void run() {
            int motionPosition = AbsListView.this.mMotionPosition;
            View child =
                    AbsListView.this.getChildAt(motionPosition - AbsListView.this.mFirstPosition);
            if (child != null) {
                int longPressPosition = AbsListView.this.mMotionPosition;
                long longPressId =
                        AbsListView.this.mAdapter.getItemId(AbsListView.this.mMotionPosition);
                boolean handled = false;
                if (sameWindow() && !AbsListView.this.mDataChanged) {
                    AbsListView.this.mIsLongPressTriggeredByKey = false;
                    handled =
                            (this.mX == -1.0f || this.mY == -1.0f)
                                    ? AbsListView.this.performLongPress(
                                            child, longPressPosition, longPressId)
                                    : AbsListView.this.performLongPress(
                                            child,
                                            longPressPosition,
                                            longPressId,
                                            this.mX,
                                            this.mY);
                }
                if (handled) {
                    AbsListView.this.mHasPerformedLongPress = true;
                    AbsListView.this.mTouchMode = -1;
                    AbsListView.this.setPressed(false);
                    child.setPressed(false);
                } else {
                    AbsListView.this.mTouchMode = 2;
                }
                if (AbsListView.this.mLongPressMultiSelectionEnabled) {
                    AbsListView.this.mHasPerformedLongPress = false;
                    AbsListView.this.mIsLongPressMultiSelection = true;
                    AbsListView.this.mTouchMode = -1;
                }
            }
        }
    }

    private class CheckForKeyLongPress extends WindowRunnnable implements Runnable {
        private CheckForKeyLongPress() {
            super();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (AbsListView.this.isPressed() && AbsListView.this.mSelectedPosition >= 0) {
                int index = AbsListView.this.mSelectedPosition - AbsListView.this.mFirstPosition;
                View v = AbsListView.this.getChildAt(index);
                if (!AbsListView.this.mDataChanged) {
                    boolean handled = false;
                    if (sameWindow()) {
                        AbsListView.this.mIsLongPressTriggeredByKey = true;
                        handled =
                                AbsListView.this.performLongPress(
                                        v,
                                        AbsListView.this.mSelectedPosition,
                                        AbsListView.this.mSelectedRowId);
                        AbsListView.this.mIsLongPressTriggeredByKey = false;
                    }
                    if (handled) {
                        AbsListView.this.setPressed(false);
                        if (v != null) {
                            v.setPressed(false);
                            return;
                        }
                        return;
                    }
                    return;
                }
                AbsListView.this.setPressed(false);
                if (v != null) {
                    v.setPressed(false);
                }
            }
        }
    }

    private boolean performStylusButtonPressAction(MotionEvent ev) {
        View child;
        if (this.mChoiceMode == 3
                && this.mChoiceActionMode == null
                && (child = getChildAt(this.mMotionPosition - this.mFirstPosition)) != null) {
            int longPressPosition = this.mMotionPosition;
            long longPressId = this.mAdapter.getItemId(this.mMotionPosition);
            if (performLongPress(child, longPressPosition, longPressId)) {
                this.mTouchMode = -1;
                setPressed(false);
                child.setPressed(false);
                return true;
            }
        }
        return false;
    }

    boolean performLongPress(View child, int longPressPosition, long longPressId) {
        return performLongPress(child, longPressPosition, longPressId, -1.0f, -1.0f);
    }

    boolean performLongPress(
            View child, int longPressPosition, long longPressId, float x, float y) {
        if (this.mChoiceMode == 3) {
            if (this.mChoiceActionMode == null) {
                ActionMode startActionMode = startActionMode(this.mMultiChoiceModeCallback);
                this.mChoiceActionMode = startActionMode;
                if (startActionMode != null) {
                    if (this.mChoiceMode == 3) {
                        this.mIsDragBlockEnabled = true;
                    }
                    setItemChecked(longPressPosition, true);
                    performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
                }
            }
            return true;
        }
        boolean handled = false;
        if (this.mOnItemLongClickListener != null) {
            handled =
                    this.mOnItemLongClickListener.onItemLongClick(
                            this, child, longPressPosition, longPressId);
        }
        if (!handled) {
            this.mContextMenuInfo = createContextMenuInfo(child, longPressPosition, longPressId);
            if (x != -1.0f && y != -1.0f) {
                handled = super.showContextMenuForChild(this, x, y);
            } else {
                handled = super.showContextMenuForChild(this);
            }
        }
        if (handled && semGetEnableVibrationAtLongPress()) {
            performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
        }
        return handled;
    }

    @Override // android.view.View
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    @Override // android.view.View
    public boolean showContextMenu() {
        return showContextMenuInternal(0.0f, 0.0f, false);
    }

    @Override // android.view.View
    public boolean showContextMenu(float x, float y) {
        return showContextMenuInternal(x, y, true);
    }

    private boolean showContextMenuInternal(float x, float y, boolean useOffsets) {
        int position = pointToPosition((int) x, (int) y);
        if (position != -1) {
            long id = this.mAdapter.getItemId(position);
            View child = getChildAt(position - this.mFirstPosition);
            if (child != null) {
                this.mContextMenuInfo = createContextMenuInfo(child, position, id);
                if (useOffsets) {
                    return super.showContextMenuForChild(this, x, y);
                }
                return super.showContextMenuForChild(this);
            }
        } else {
            this.mContextMenuInfo = null;
        }
        if (useOffsets) {
            return super.showContextMenu(x, y);
        }
        return super.showContextMenu();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(View originalView) {
        if (isShowingContextMenuWithCoords()) {
            return false;
        }
        return showContextMenuForChildInternal(originalView, 0.0f, 0.0f, false);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(View originalView, float x, float y) {
        return showContextMenuForChildInternal(originalView, x, y, true);
    }

    private boolean showContextMenuForChildInternal(
            View originalView, float x, float y, boolean useOffsets) {
        int longPressPosition = getPositionForView(originalView);
        if (longPressPosition < 0) {
            return false;
        }
        long longPressId = this.mAdapter.getItemId(longPressPosition);
        boolean handled = false;
        if (this.mOnItemLongClickListener != null) {
            handled =
                    this.mOnItemLongClickListener.onItemLongClick(
                            this, originalView, longPressPosition, longPressId);
        }
        if (!handled) {
            View child = getChildAt(longPressPosition - this.mFirstPosition);
            this.mContextMenuInfo = createContextMenuInfo(child, longPressPosition, longPressId);
            if (useOffsets) {
                boolean handled2 = super.showContextMenuForChild(originalView, x, y);
                return handled2;
            }
            boolean handled3 = super.showContextMenuForChild(originalView);
            return handled3;
        }
        return handled;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 31:
                if (this.mIsCtrlkeyPressed) {
                    resetPressItemListArray();
                    break;
                }
                break;
            case 59:
            case 60:
                this.mIsShiftkeyPressed = true;
                break;
            case 113:
            case 114:
                this.mIsCtrlkeyPressed = true;
                break;
        }
        return false;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.isConfirmKey(keyCode)) {
            if (!isEnabled()) {
                return true;
            }
            if (this.mAppWidgetInnerFocus && this.mNextClickable != null) {
                this.mNextClickable.performClick();
                this.mNextClickable.setPressed(false);
                setPressed(false);
                return true;
            }
            if (isClickable()
                    && isPressed()
                    && this.mSelectedPosition >= 0
                    && this.mAdapter != null
                    && this.mSelectedPosition < this.mAdapter.getCount()
                    && this.mAdapter.isEnabled(this.mSelectedPosition)) {
                View view = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (view != null) {
                    performItemClick(view, this.mSelectedPosition, this.mSelectedRowId);
                    view.setPressed(false);
                }
                setPressed(false);
                return true;
            }
        }
        switch (keyCode) {
            case 19:
            case 20:
            case 21:
            case 22:
                if (this.mIsShiftkeyPressed) {
                    if (this.mOldKeyCode == 0) {
                        this.mOldKeyCode = keyCode;
                    } else {
                        this.mCurrentKeyCode = keyCode;
                    }
                }
                if (isClickable()
                        && this.mSelectedPosition >= 0
                        && this.mAdapter != null
                        && this.mSelectedPosition < this.mAdapter.getCount()) {
                    View selectedView = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                    View currentView = getChildAt(this.mSemCurrentFocusPosition);
                    if (this.mIsShiftkeyPressed && selectedView != null) {
                        if (this.mCurrentKeyCode == 0) {
                            resetPressItemListArray();
                            semNotifyKeyPressState(
                                    currentView,
                                    this.mSemCurrentFocusPosition,
                                    this.mSelectedRowId);
                            semNotifyKeyPressState(
                                    selectedView, this.mSelectedPosition, this.mSelectedRowId);
                            addToPressItemListArray(
                                    this.mSemCurrentFocusPosition, this.mSelectedPosition);
                            this.mFirstPressedPoint = this.mSemCurrentFocusPosition;
                        } else {
                            resetPressItemListArray();
                            semNotifyKeyPressState(
                                    selectedView, this.mSelectedPosition, this.mSelectedRowId);
                            addToPressItemListArray(
                                    this.mFirstPressedPoint, this.mSelectedPosition);
                        }
                    }
                    if (this.mCurrentKeyCode != 0) {
                        this.mOldKeyCode = this.mCurrentKeyCode;
                        break;
                    }
                }
                break;
            case 31:
                if (this.mIsCtrlkeyPressed) {
                    resetPressItemListArray();
                    break;
                }
                break;
            case 59:
            case 60:
                this.mIsShiftkeyPressed = false;
                this.mOldKeyCode = 0;
                this.mCurrentKeyCode = 0;
                this.mFirstPressedPoint = -1;
                break;
            case 113:
            case 114:
                this.mIsCtrlkeyPressed = false;
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSetPressed(boolean pressed) {}

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDrawableHotspotChanged(float x, float y) {}

    public int pointToPosition(int x, int y) {
        Rect frame = this.mTouchFrame;
        if (frame == null) {
            this.mTouchFrame = new Rect();
            frame = this.mTouchFrame;
        }
        boolean z = false;
        this.mHasDividerHeight = this instanceof ListView ? ((ListView) this).mDividerHeight : 0;
        if (this.mHasDividerHeight > 0 && ((ListView) this).mDivider != null) {
            z = true;
        }
        this.mHasDivier = z;
        int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                child.getHitRect(frame);
                if (this.mHasDivier) {
                    frame.bottom += this.mHasDividerHeight;
                }
                if (frame.contains(x, y)) {
                    return this.mFirstPosition + i;
                }
            }
        }
        return -1;
    }

    public long pointToRowId(int x, int y) {
        int position = pointToPosition(x, y);
        if (position >= 0) {
            return this.mAdapter.getItemId(position);
        }
        return Long.MIN_VALUE;
    }

    private final class CheckForTap implements Runnable {
        float x;
        float y;

        private CheckForTap() {}

        @Override // java.lang.Runnable
        public void run() {
            if (AbsListView.this.mTouchMode == 0) {
                AbsListView.this.mTouchMode = 1;
                View child =
                        AbsListView.this.getChildAt(
                                AbsListView.this.mMotionPosition - AbsListView.this.mFirstPosition);
                if (child != null
                        && !child.hasExplicitFocusable()
                        && AbsListView.this.getAdapter() != null
                        && AbsListView.this.mMotionPosition >= 0
                        && AbsListView.this
                                .getAdapter()
                                .isEnabled(AbsListView.this.mMotionPosition)) {
                    AbsListView.this.mLayoutMode = 0;
                    if (!AbsListView.this.mDataChanged) {
                        if (AbsListView.this.mSweepListAnimator != null
                                && AbsListView.this.mSweepListAnimator.isSwiping()) {
                            return;
                        }
                        float[] point = AbsListView.this.mTmpPoint;
                        point[0] = this.x;
                        point[1] = this.y;
                        AbsListView.this.transformPointToViewLocal(point, child);
                        child.drawableHotspotChanged(point[0], point[1]);
                        child.setPressed(true);
                        AbsListView.this.setPressed(true);
                        AbsListView.this.layoutChildren();
                        AbsListView.this.positionSelector(AbsListView.this.mMotionPosition, child);
                        AbsListView.this.refreshDrawableState();
                        int longPressTimeout = ViewConfiguration.getLongPressTimeout();
                        boolean longClickable = AbsListView.this.isLongClickable();
                        if (AbsListView.this.mSelector != null) {
                            Drawable d = AbsListView.this.mSelector.getCurrent();
                            if (d != null && (d instanceof TransitionDrawable)) {
                                if (longClickable) {
                                    ((TransitionDrawable) d).startTransition(longPressTimeout);
                                } else {
                                    ((TransitionDrawable) d).resetTransition();
                                }
                            }
                            AbsListView.this.mSelector.setHotspot(this.x, this.y);
                        }
                        if (longClickable) {
                            if (AbsListView.this.mPendingCheckForLongPress == null) {
                                AbsListView.this.mPendingCheckForLongPress =
                                        new CheckForLongPress();
                            }
                            AbsListView.this.mPendingCheckForLongPress.setCoords(this.x, this.y);
                            AbsListView.this.mPendingCheckForLongPress.rememberWindowAttachCount();
                            AbsListView.this.postDelayed(
                                    AbsListView.this.mPendingCheckForLongPress, longPressTimeout);
                            return;
                        }
                        AbsListView.this.mTouchMode = 2;
                        return;
                    }
                    AbsListView.this.mTouchMode = 2;
                }
            }
        }
    }

    private boolean startScrollIfNeeded(int x, int y, MotionEvent vtev) {
        int deltaY = y - this.mMotionY;
        int distance = Math.abs(deltaY);
        boolean overscroll = this.mScrollY != 0;
        if ((!overscroll && distance <= this.mTouchSlop) || (getNestedScrollAxes() & 2) != 0) {
            return false;
        }
        createScrollingCache();
        if (overscroll) {
            this.mTouchMode = 5;
            this.mMotionCorrection = 0;
        } else {
            this.mTouchMode = 3;
            int i = this.mTouchSlop;
            if (deltaY <= 0) {
                i = -i;
            }
            this.mMotionCorrection = i;
        }
        removeCallbacks(this.mPendingCheckForLongPress);
        setPressed(false);
        View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (motionView != null) {
            motionView.setPressed(false);
        }
        if (this.mPointerCount > 1) {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                getChildAt(i2).setPressed(false);
            }
        }
        reportScrollStateChange(1);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        scrollIfNeeded(x, y, vtev);
        return true;
    }

    private void scrollIfNeeded(int x, int y, MotionEvent vtev) {
        int rawDeltaY;
        int scrollOffsetCorrection;
        int incrementalDeltaY;
        int incrementalDeltaY2;
        int overScrollDistance;
        int incrementalDeltaY3;
        int incrementalDeltaY4;
        int newDirection;
        int motionIndex;
        int motionViewPrevTop;
        boolean atEdge;
        int incrementalDeltaY5;
        ViewParent parent;
        int rawDeltaY2 = y - this.mMotionY;
        if (this.mLastY == Integer.MIN_VALUE) {
            rawDeltaY2 -= this.mMotionCorrection;
        }
        int incrementalDeltaY6 =
                releaseGlow(this.mLastY != Integer.MIN_VALUE ? y - this.mLastY : rawDeltaY2, x);
        if (!dispatchNestedPreScroll(
                0, -incrementalDeltaY6, this.mScrollConsumed, this.mScrollOffset)) {
            rawDeltaY = rawDeltaY2;
            scrollOffsetCorrection = 0;
            incrementalDeltaY = incrementalDeltaY6;
        } else {
            int rawDeltaY3 = rawDeltaY2 + this.mScrollConsumed[1];
            int scrollOffsetCorrection2 = -this.mScrollOffset[1];
            int incrementalDeltaY7 = incrementalDeltaY6 + this.mScrollConsumed[1];
            if (vtev != null) {
                vtev.offsetLocation(0.0f, this.mScrollOffset[1]);
                this.mNestedYOffset += this.mScrollOffset[1];
            }
            rawDeltaY = rawDeltaY3;
            scrollOffsetCorrection = scrollOffsetCorrection2;
            incrementalDeltaY = incrementalDeltaY7;
        }
        int deltaY = rawDeltaY;
        int lastYCorrection = 0;
        if (this.mTouchMode == 3) {
            if (this.mScrollStrictSpan == null) {
                this.mScrollStrictSpan = StrictMode.enterCriticalSpan("AbsListView-scroll");
            }
            if (y != this.mLastY) {
                if ((this.mGroupFlags & 524288) == 0
                        && Math.abs(rawDeltaY) > this.mTouchSlop
                        && (parent = getParent()) != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                if (this.mMotionPosition >= 0) {
                    motionIndex = this.mMotionPosition - this.mFirstPosition;
                } else {
                    int motionIndex2 = getChildCount();
                    motionIndex = motionIndex2 / 2;
                }
                View motionView = getChildAt(motionIndex);
                if (motionView == null) {
                    motionViewPrevTop = 0;
                } else {
                    int motionViewPrevTop2 = motionView.getTop();
                    motionViewPrevTop = motionViewPrevTop2;
                }
                if (incrementalDeltaY == 0) {
                    atEdge = false;
                } else {
                    boolean atEdge2 = trackMotionScroll(deltaY, incrementalDeltaY);
                    atEdge = atEdge2;
                }
                View motionView2 = getChildAt(motionIndex);
                if (motionView2 != null) {
                    int motionViewRealTop = motionView2.getTop();
                    if (atEdge) {
                        int overscroll =
                                (-incrementalDeltaY) - (motionViewRealTop - motionViewPrevTop);
                        if (!dispatchNestedScroll(
                                        0,
                                        overscroll - incrementalDeltaY,
                                        0,
                                        overscroll,
                                        this.mScrollOffset)
                                || this.mSemForcedDrawEdgeEffect) {
                            int incrementalDeltaY8 = incrementalDeltaY;
                            boolean atOverscrollEdge =
                                    overScrollBy(
                                            0,
                                            overscroll,
                                            0,
                                            this.mScrollY,
                                            0,
                                            0,
                                            0,
                                            this.mOverscrollDistance,
                                            true);
                            int overscrollMode = getOverScrollMode();
                            if (overscrollMode != 0) {
                                if (overscrollMode != 1) {
                                    incrementalDeltaY5 = incrementalDeltaY8;
                                } else if (contentFits()) {
                                    incrementalDeltaY5 = incrementalDeltaY8;
                                }
                            }
                            if (!atOverscrollEdge) {
                                this.mDirection = 0;
                                this.mTouchMode = 5;
                            }
                            incrementalDeltaY5 = incrementalDeltaY8;
                            if (incrementalDeltaY5 > 0) {
                                this.mEdgeGlowTop.onPullDistance(
                                        (-overscroll) / getHeight(), x / getWidth());
                                if (!this.mEdgeGlowBottom.isFinished()) {
                                    this.mEdgeGlowBottom.onRelease();
                                }
                                invalidateEdgeEffects();
                            } else if (incrementalDeltaY5 < 0) {
                                this.mEdgeGlowBottom.onPullDistance(
                                        overscroll / getHeight(), 1.0f - (x / getWidth()));
                                if (!this.mEdgeGlowTop.isFinished()) {
                                    this.mEdgeGlowTop.onRelease();
                                }
                                invalidateEdgeEffects();
                            }
                        } else {
                            lastYCorrection = 0 - this.mScrollOffset[1];
                            if (vtev != null) {
                                vtev.offsetLocation(0.0f, this.mScrollOffset[1]);
                                this.mNestedYOffset += this.mScrollOffset[1];
                            }
                            incrementalDeltaY5 = incrementalDeltaY;
                        }
                    } else {
                        incrementalDeltaY5 = incrementalDeltaY;
                    }
                    this.mMotionY = y + lastYCorrection + scrollOffsetCorrection;
                } else {
                    incrementalDeltaY5 = incrementalDeltaY;
                }
                this.mLastY = y + lastYCorrection + scrollOffsetCorrection;
                return;
            }
            incrementalDeltaY2 = incrementalDeltaY;
        } else {
            incrementalDeltaY2 = incrementalDeltaY;
            if (this.mTouchMode == 5 && y != this.mLastY) {
                int oldScroll = this.mScrollY;
                int newScroll = oldScroll - incrementalDeltaY2;
                int newDirection2 = y > this.mLastY ? 1 : -1;
                if (this.mDirection == 0) {
                    this.mDirection = newDirection2;
                }
                int overScrollDistance2 = -incrementalDeltaY2;
                if ((newScroll < 0 && oldScroll >= 0) || (newScroll > 0 && oldScroll <= 0)) {
                    int overScrollDistance3 = -oldScroll;
                    overScrollDistance = overScrollDistance3;
                    incrementalDeltaY3 = incrementalDeltaY2 + overScrollDistance3;
                } else {
                    overScrollDistance = overScrollDistance2;
                    incrementalDeltaY3 = 0;
                }
                if (overScrollDistance != 0) {
                    incrementalDeltaY4 = incrementalDeltaY3;
                    int overScrollDistance4 = overScrollDistance;
                    newDirection = newDirection2;
                    overScrollBy(
                            0,
                            overScrollDistance,
                            0,
                            this.mScrollY,
                            0,
                            0,
                            0,
                            this.mOverscrollDistance,
                            true);
                    int overscrollMode2 = getOverScrollMode();
                    if (overscrollMode2 == 0 || (overscrollMode2 == 1 && !contentFits())) {
                        if (rawDeltaY > 0) {
                            this.mEdgeGlowTop.onPullDistance(
                                    overScrollDistance4 / getHeight(), x / getWidth());
                            if (!this.mEdgeGlowBottom.isFinished()) {
                                this.mEdgeGlowBottom.onRelease();
                            }
                            invalidateEdgeEffects();
                        } else if (rawDeltaY < 0) {
                            this.mEdgeGlowBottom.onPullDistance(
                                    (-overScrollDistance4) / getHeight(), 1.0f - (x / getWidth()));
                            if (!this.mEdgeGlowTop.isFinished()) {
                                this.mEdgeGlowTop.onRelease();
                            }
                            invalidateEdgeEffects();
                        }
                    }
                } else {
                    incrementalDeltaY4 = incrementalDeltaY3;
                    newDirection = newDirection2;
                }
                if (incrementalDeltaY4 != 0) {
                    if (this.mScrollY != 0) {
                        this.mScrollY = 0;
                        invalidateParentIfNeeded();
                    }
                    trackMotionScroll(incrementalDeltaY4, incrementalDeltaY4);
                    this.mTouchMode = 3;
                    int motionPosition = findClosestMotionRow(y);
                    this.mMotionCorrection = 0;
                    View motionView3 = getChildAt(motionPosition - this.mFirstPosition);
                    this.mMotionViewOriginalTop = motionView3 != null ? motionView3.getTop() : 0;
                    this.mMotionY = y + scrollOffsetCorrection;
                    this.mMotionPosition = motionPosition;
                }
                this.mLastY = y + 0 + scrollOffsetCorrection;
                this.mDirection = newDirection;
            }
        }
    }

    private int releaseGlow(int deltaY, int x) {
        float consumed = 0.0f;
        if (this.mEdgeGlowTop.getDistance() != 0.0f) {
            if (canScrollUp()) {
                this.mEdgeGlowTop.onRelease();
            } else {
                consumed = this.mEdgeGlowTop.onPullDistance(deltaY / getHeight(), x / getWidth());
            }
            invalidateEdgeEffects();
        } else if (this.mEdgeGlowBottom.getDistance() != 0.0f) {
            if (canScrollDown()) {
                this.mEdgeGlowBottom.onRelease();
            } else {
                consumed =
                        -this.mEdgeGlowBottom.onPullDistance(
                                (-deltaY) / getHeight(), 1.0f - (x / getWidth()));
            }
            invalidateEdgeEffects();
        }
        int pixelsConsumed = Math.round(getHeight() * consumed);
        return deltaY - pixelsConsumed;
    }

    private boolean doesTouchStopStretch() {
        return ((this.mEdgeGlowBottom.getDistance() == 0.0f || canScrollDown())
                        && (this.mEdgeGlowTop.getDistance() == 0.0f || canScrollUp()))
                ? false
                : true;
    }

    private void invalidateEdgeEffects() {
        if (!shouldDisplayEdgeEffects()) {
            return;
        }
        invalidate();
    }

    @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
    public void onTouchModeChanged(boolean isInTouchMode) {
        if (isInTouchMode) {
            hideSelector();
            if (getHeight() > 0 && getChildCount() > 0) {
                layoutChildren();
            }
            updateSelectorState();
            return;
        }
        int touchMode = this.mTouchMode;
        if (touchMode == 5 || touchMode == 6) {
            if (this.mFlingRunnable != null) {
                this.mFlingRunnable.endFling();
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            if (this.mScrollY != 0) {
                this.mScrollY = 0;
                invalidateParentCaches();
                finishGlows();
                invalidate();
            }
        }
    }

    @Override // android.view.View
    protected boolean handleScrollBarDragging(MotionEvent event) {
        return false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.mAppWidgetEnabled) {
            return true;
        }
        if (!isEnabled()) {
            Log.d(
                    TAG,
                    "onTouchEvent() mIsLongPressMultiSelection : "
                            + this.mIsLongPressMultiSelection);
            return isClickable() || isLongClickable();
        }
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        if (this.mIsDetaching || !isAttachedToWindow()) {
            Log.d(
                    TAG,
                    "onTouchEvent() mIsDetaching : "
                            + this.mIsDetaching
                            + ", isAttachedToWindow() : "
                            + isAttachedToWindow());
            return false;
        }
        startNestedScroll(2);
        if (this.mFastScroll != null && this.mFastScroll.onTouchEvent(ev)) {
            return true;
        }
        if (this.mSemFastScroll != null) {
            boolean intercepted = this.mSemFastScroll.onTouchEvent(ev);
            if (this.mSemFastScrollEventListener != null) {
                if ((ev.getActionMasked() == 0 || ev.getActionMasked() == 2)
                        && this.mSemFastScroll.getEffectState() == 1) {
                    this.mSemFastScrollEventListener.onPressed(this.mSemFastScroll.getScrollY());
                } else {
                    this.mSemFastScrollEventListener.onReleased(this.mSemFastScroll.getScrollY());
                }
            }
            this.mSemFastScrollEffectState = this.mSemFastScroll.getEffectState() == 1;
            if (intercepted) {
                return true;
            }
        }
        initVelocityTrackerIfNotExists();
        MotionEvent vtev = MotionEvent.obtain(ev);
        int actionMasked = ev.getActionMasked();
        if (actionMasked == 0) {
            this.mNestedYOffset = 0;
        }
        vtev.offsetLocation(0.0f, this.mNestedYOffset);
        switch (actionMasked) {
            case 0:
                onTouchDown(ev);
                break;
            case 1:
                onTouchUp(ev);
                break;
            case 2:
                onTouchMove(ev, vtev);
                break;
            case 3:
                onTouchCancel();
                break;
            case 5:
                int index = ev.getActionIndex();
                int id = ev.getPointerId(index);
                int x = (int) ev.getX(index);
                int y = (int) ev.getY(index);
                this.mMotionCorrection = 0;
                this.mActivePointerId = id;
                this.mMotionX = x;
                this.mMotionY = y;
                int motionPosition = pointToPosition(x, y);
                if (motionPosition >= 0) {
                    this.mMotionViewOriginalTop =
                            getChildAt(motionPosition - this.mFirstPosition).getTop();
                    this.mMotionPosition = motionPosition;
                }
                this.mLastY = y;
                this.mPointerCount++;
                break;
            case 6:
                onSecondaryPointerUp(ev);
                int x2 = this.mMotionX;
                int y2 = this.mMotionY;
                int motionPosition2 = pointToPosition(x2, y2);
                if (motionPosition2 >= 0) {
                    View child = getChildAt(motionPosition2 - this.mFirstPosition);
                    this.mMotionViewOriginalTop = child.getTop();
                    this.mMotionPosition = motionPosition2;
                    if (this.mAdapter != null
                            && this.mAdapter.isEnabled(motionPosition2)
                            && !child.hasFocusable()) {
                        layoutChildren();
                    }
                } else {
                    layoutChildren();
                }
                this.mLastY = y2;
                this.mPointerCount--;
                break;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(vtev);
        }
        vtev.recycle();
        return true;
    }

    private void onTouchDown(MotionEvent ev) {
        this.mHasPerformedLongPress = false;
        this.mActivePointerId = ev.getPointerId(0);
        hideSelector();
        this.mPointerCount++;
        if (this.mTouchMode == 6) {
            if (this.mFlingRunnable != null) {
                this.mFlingRunnable.endFling();
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            this.mTouchMode = 5;
            this.mMotionX = (int) ev.getX();
            this.mMotionY = (int) ev.getY();
            this.mLastY = this.mMotionY;
            this.mMotionCorrection = 0;
            this.mDirection = 0;
            stopEdgeGlowRecede(ev.getX());
        } else {
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            int motionPosition = pointToPosition(x, y);
            if (!this.mDataChanged) {
                if (this.mTouchMode == 4) {
                    createScrollingCache();
                    this.mTouchMode = 3;
                    this.mMotionCorrection = 0;
                    motionPosition = findMotionRow(y);
                    if (this.mFlingRunnable != null) {
                        this.mFlingRunnable.flywheelTouch();
                    }
                    stopEdgeGlowRecede(x);
                } else if (motionPosition >= 0 && getAdapter().isEnabled(motionPosition)) {
                    this.mTouchMode = 0;
                    if (this.mPendingCheckForTap == null) {
                        this.mPendingCheckForTap = new CheckForTap();
                    }
                    this.mPendingCheckForTap.x = ev.getX();
                    this.mPendingCheckForTap.y = ev.getY();
                    postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                }
            }
            if (motionPosition >= 0) {
                View v = getChildAt(motionPosition - this.mFirstPosition);
                this.mMotionViewOriginalTop = v.getTop();
            }
            this.mMotionX = x;
            this.mMotionY = y;
            this.mMotionPosition = motionPosition;
            this.mLastY = Integer.MIN_VALUE;
        }
        if (this.mTouchMode == 0
                && this.mMotionPosition != -1
                && performButtonActionOnTouchDown(ev)) {
            removeCallbacks(this.mPendingCheckForTap);
        }
    }

    private void stopEdgeGlowRecede(float x) {
        if (this.mEdgeGlowTop.getDistance() != 0.0f) {
            this.mEdgeGlowTop.onPullDistance(0.0f, x / getWidth());
        }
        if (this.mEdgeGlowBottom.getDistance() != 0.0f) {
            this.mEdgeGlowBottom.onPullDistance(0.0f, x / getWidth());
        }
    }

    private void onTouchMove(MotionEvent ev, MotionEvent vtev) {
        if (this.mHasPerformedLongPress) {
            return;
        }
        int pointerIndex = ev.findPointerIndex(this.mActivePointerId);
        if (pointerIndex == -1) {
            pointerIndex = 0;
            this.mActivePointerId = ev.getPointerId(0);
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        int y = (int) ev.getY(pointerIndex);
        switch (this.mTouchMode) {
            case 0:
            case 1:
            case 2:
                if (!startScrollIfNeeded((int) ev.getX(pointerIndex), y, vtev)) {
                    View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
                    float x = ev.getX(pointerIndex);
                    if (!pointInView(x, y, this.mTouchSlop)) {
                        setPressed(false);
                        if (motionView != null) {
                            motionView.setPressed(false);
                        }
                        removeCallbacks(
                                this.mTouchMode == 0
                                        ? this.mPendingCheckForTap
                                        : this.mPendingCheckForLongPress);
                        this.mTouchMode = 2;
                        updateSelectorState();
                        break;
                    } else if (motionView != null) {
                        float[] point = this.mTmpPoint;
                        point[0] = x;
                        point[1] = y;
                        transformPointToViewLocal(point, motionView);
                        motionView.drawableHotspotChanged(point[0], point[1]);
                        break;
                    }
                }
                break;
            case 3:
            case 5:
                scrollIfNeeded((int) ev.getX(pointerIndex), y, vtev);
                break;
        }
        if (this.mAppWidgetIndicator) {
            int firstVisiblePos = getFirstVisiblePosition();
            int lastVisiblePos = getLastVisiblePosition();
            View firstVisibleView = null;
            View lastVisibleView = null;
            if (getChildCount() > 1) {
                firstVisibleView = getChildAt(0);
                lastVisibleView = getChildAt(1);
            }
            if (firstVisibleView != null
                    && getHeight() / 2 < firstVisibleView.getBottom()
                    && this.mFocusedPos != firstVisiblePos) {
                semInvalidateIndicator(firstVisiblePos);
            } else if (lastVisibleView != null
                    && getHeight() / 2 > lastVisibleView.getTop()
                    && this.mFocusedPos != lastVisiblePos) {
                semInvalidateIndicator(lastVisiblePos);
            }
        }
    }

    private void onTouchUp(MotionEvent ev) {
        Runnable runnable;
        View secondView;
        Log.d(TAG, "onTouchUp() mTouchMode : " + this.mTouchMode);
        switch (this.mTouchMode) {
            case 0:
            case 1:
            case 2:
                int childCount = this.mMotionPosition;
                final View child = getChildAt(childCount - this.mFirstPosition);
                if (child != null) {
                    if (this.mTouchMode != 0) {
                        child.setPressed(false);
                    }
                    float x = ev.getX();
                    boolean inList =
                            x > ((float) this.mListPadding.left)
                                    && x < ((float) (getWidth() - this.mListPadding.right));
                    if (inList && !child.hasExplicitFocusable()) {
                        if (this.mPerformClick == null) {
                            this.mPerformClick = new PerformClick();
                        }
                        final PerformClick performClick = this.mPerformClick;
                        performClick.mClickMotionPosition = childCount;
                        performClick.rememberWindowAttachCount();
                        this.mResurrectToPosition = childCount;
                        if (this.mTouchMode == 0 || this.mTouchMode == 1) {
                            if (this.mTouchMode != 0) {
                                runnable = this.mPendingCheckForLongPress;
                            } else {
                                runnable = this.mPendingCheckForTap;
                            }
                            removeCallbacks(runnable);
                            this.mLayoutMode = 0;
                            if (this.mDataChanged || !this.mAdapter.isEnabled(childCount)) {
                                this.mTouchMode = -1;
                                updateSelectorState();
                                if (this.mForcedClick && this.mAdapter.isEnabled(childCount)) {
                                    performClick.run();
                                    return;
                                }
                                return;
                            }
                            this.mTouchMode = 1;
                            setSelectedPositionInt(this.mMotionPosition);
                            layoutChildren();
                            child.setPressed(true);
                            positionSelector(this.mMotionPosition, child);
                            setPressed(true);
                            if (this.mSelector != null) {
                                Drawable d = this.mSelector.getCurrent();
                                if (d != null && (d instanceof TransitionDrawable)) {
                                    ((TransitionDrawable) d).resetTransition();
                                }
                                this.mSelector.setHotspot(x, ev.getY());
                            }
                            if (this.mTouchModeReset != null) {
                                removeCallbacks(this.mTouchModeReset);
                            }
                            this.mTouchModeReset =
                                    new Runnable() { // from class: android.widget.AbsListView.3
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            AbsListView.this.mTouchModeReset = null;
                                            AbsListView.this.mTouchMode = -1;
                                            child.setPressed(false);
                                            AbsListView.this.setPressed(false);
                                            if ((!AbsListView.this.mDataChanged
                                                            && !AbsListView.this.mIsDetaching
                                                            && AbsListView.this
                                                                    .isAttachedToWindow())
                                                    || AbsListView.this.mForcedClick) {
                                                performClick.run();
                                            }
                                        }
                                    };
                            postDelayed(
                                    this.mTouchModeReset,
                                    ViewConfiguration.getPressedStateDuration());
                            return;
                        }
                        if ((!this.mDataChanged || this.mForcedClick)
                                && this.mAdapter.isEnabled(childCount)) {
                            performClick.run();
                        }
                    }
                }
                this.mTouchMode = -1;
                updateSelectorState();
                break;
            case 3:
                int childCount2 = getChildCount();
                if (childCount2 <= 0) {
                    this.mTouchMode = -1;
                    reportScrollStateChange(0);
                    break;
                } else {
                    int firstChildTop = getChildAt(0).getTop();
                    int lastChildBottom = getChildAt(childCount2 - 1).getBottom();
                    int contentTop = this.mListPadding.top;
                    int contentBottom = getHeight() - this.mListPadding.bottom;
                    if (this.mFirstPosition == 0
                            && firstChildTop >= contentTop
                            && this.mFirstPosition + childCount2 < this.mItemCount
                            && lastChildBottom <= getHeight() - contentBottom) {
                        this.mTouchMode = -1;
                        reportScrollStateChange(0);
                        break;
                    } else {
                        VelocityTracker velocityTracker = this.mVelocityTracker;
                        velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                        int initialVelocity =
                                (int)
                                        (velocityTracker.getYVelocity(this.mActivePointerId)
                                                * this.mVelocityScale);
                        boolean flingVelocity = Math.abs(initialVelocity) > this.mMinimumVelocity;
                        if (flingVelocity && !this.mEdgeGlowTop.isFinished()) {
                            if (shouldAbsorb(this.mEdgeGlowTop, initialVelocity)) {
                                this.mEdgeGlowTop.onAbsorb(initialVelocity);
                                break;
                            } else {
                                if (this.mFlingRunnable == null) {
                                    this.mFlingRunnable = new FlingRunnable();
                                }
                                this.mFlingRunnable.start(-initialVelocity);
                                break;
                            }
                        } else if (!flingVelocity || this.mEdgeGlowBottom.isFinished()) {
                            if (flingVelocity
                                    && ((this.mFirstPosition != 0
                                                    || firstChildTop
                                                            != contentTop
                                                                    - this.mOverscrollDistance)
                                            && (this.mFirstPosition + childCount2 != this.mItemCount
                                                    || lastChildBottom
                                                            != this.mOverscrollDistance
                                                                    + contentBottom))) {
                                if (!dispatchNestedPreFling(0.0f, -initialVelocity)) {
                                    if (this.mFlingRunnable == null) {
                                        this.mFlingRunnable = new FlingRunnable();
                                    }
                                    reportScrollStateChange(2);
                                    this.mFlingRunnable.start(-initialVelocity);
                                    dispatchNestedFling(0.0f, -initialVelocity, true);
                                    break;
                                } else {
                                    this.mTouchMode = -1;
                                    reportScrollStateChange(0);
                                    break;
                                }
                            } else {
                                this.mTouchMode = -1;
                                if (this.mFlingRunnable != null) {
                                    this.mFlingRunnable.endFling();
                                }
                                reportScrollStateChange(0);
                                if (this.mPositionScroller != null) {
                                    this.mPositionScroller.stop();
                                }
                                if (flingVelocity
                                        && !dispatchNestedPreFling(0.0f, -initialVelocity)) {
                                    dispatchNestedFling(0.0f, -initialVelocity, false);
                                }
                                if (this.mAppWidgetSnapScroll
                                        && (secondView = getChildAt(1)) != null) {
                                    int contentH = getHeight();
                                    boolean scrollUp =
                                            getFirstVisiblePosition() != this.mFocusedPos;
                                    View focusedView = scrollUp ? secondView : getChildAt(0);
                                    if (scrollUp) {
                                        if (focusedView.getTop() > contentH / 2) {
                                            smoothScrollToPositionFromTop(this.mFirstPosition, 0);
                                            break;
                                        } else {
                                            smoothScrollToPositionFromTop(
                                                    this.mFirstPosition + 1, 0);
                                            break;
                                        }
                                    } else if (focusedView.getBottom() < contentH / 2) {
                                        smoothScrollToPositionFromTop(this.mFirstPosition + 1, 0);
                                        break;
                                    } else {
                                        smoothScrollToPositionFromTop(
                                                this.mFirstPosition,
                                                contentH - focusedView.getHeight());
                                        break;
                                    }
                                }
                            }
                        } else if (shouldAbsorb(this.mEdgeGlowBottom, -initialVelocity)) {
                            this.mEdgeGlowBottom.onAbsorb(-initialVelocity);
                            break;
                        } else {
                            if (this.mFlingRunnable == null) {
                                this.mFlingRunnable = new FlingRunnable();
                            }
                            this.mFlingRunnable.start(-initialVelocity);
                            break;
                        }
                    }
                }
                break;
            case 5:
                if (this.mFlingRunnable == null) {
                    this.mFlingRunnable = new FlingRunnable();
                }
                VelocityTracker velocityTracker2 = this.mVelocityTracker;
                velocityTracker2.computeCurrentVelocity(1000, this.mMaximumVelocity);
                int initialVelocity2 = (int) velocityTracker2.getYVelocity(this.mActivePointerId);
                reportScrollStateChange(2);
                if (Math.abs(initialVelocity2) > this.mMinimumVelocity) {
                    this.mFlingRunnable.startOverfling(-initialVelocity2);
                    break;
                } else {
                    this.mFlingRunnable.startSpringback();
                    break;
                }
        }
        setPressed(false);
        if (shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
        invalidate();
        removeCallbacks(this.mPendingCheckForLongPress);
        recycleVelocityTracker();
        this.mActivePointerId = -1;
        this.mPointerCount = 0;
        if (this.mScrollStrictSpan != null) {
            this.mScrollStrictSpan.finish();
            this.mScrollStrictSpan = null;
        }
    }

    private boolean shouldAbsorb(EdgeEffect edgeEffect, int velocity) {
        if (velocity > 0) {
            return true;
        }
        float distance = edgeEffect.getDistance() * getHeight();
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        float flingDistance = this.mFlingRunnable.getSplineFlingDistance(-velocity);
        return flingDistance < distance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int consumeFlingInStretch(int unconsumed) {
        if (unconsumed < 0
                && this.mEdgeGlowTop != null
                && this.mEdgeGlowTop.getDistance() != 0.0f) {
            int size = getHeight();
            float deltaDistance = (unconsumed * FLING_DESTRETCH_FACTOR) / size;
            int consumed =
                    Math.round(
                            (size / FLING_DESTRETCH_FACTOR)
                                    * this.mEdgeGlowTop.onPullDistance(deltaDistance, 0.5f));
            if (consumed != unconsumed) {
                this.mEdgeGlowTop.finish();
            }
            return unconsumed - consumed;
        }
        if (unconsumed > 0
                && this.mEdgeGlowBottom != null
                && this.mEdgeGlowBottom.getDistance() != 0.0f) {
            int size2 = getHeight();
            float deltaDistance2 = ((-unconsumed) * FLING_DESTRETCH_FACTOR) / size2;
            int consumed2 =
                    Math.round(
                            ((-size2) / FLING_DESTRETCH_FACTOR)
                                    * this.mEdgeGlowBottom.onPullDistance(deltaDistance2, 0.5f));
            if (consumed2 != unconsumed) {
                this.mEdgeGlowBottom.finish();
            }
            return unconsumed - consumed2;
        }
        return unconsumed;
    }

    private boolean shouldDisplayEdgeEffects() {
        return getOverScrollMode() != 2;
    }

    private void onTouchCancel() {
        View secondView;
        switch (this.mTouchMode) {
            case 5:
                if (this.mFlingRunnable == null) {
                    this.mFlingRunnable = new FlingRunnable();
                }
                this.mFlingRunnable.startSpringback();
                break;
            case 6:
                break;
            default:
                if (this.mAppWidgetSnapScroll
                        && this.mTouchMode != 0
                        && (secondView = getChildAt(1)) != null) {
                    int contentH = getHeight();
                    boolean scrollUp = getFirstVisiblePosition() != this.mFocusedPos;
                    View focusedView = scrollUp ? secondView : getChildAt(0);
                    if (scrollUp) {
                        if (focusedView.getTop() <= contentH / 2) {
                            scrollToPositionFromTop(this.mFirstPosition + 1, 0);
                        } else {
                            scrollToPositionFromTop(this.mFirstPosition, 0);
                        }
                    } else if (focusedView.getBottom() < contentH / 2) {
                        scrollToPositionFromTop(this.mFirstPosition + 1, 0);
                    } else {
                        scrollToPositionFromTop(
                                this.mFirstPosition, contentH - focusedView.getHeight());
                    }
                }
                this.mTouchMode = -1;
                setPressed(false);
                View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
                if (motionView != null) {
                    motionView.setPressed(false);
                }
                clearScrollingCache();
                removeCallbacks(this.mPendingCheckForLongPress);
                if (this.mFlingRunnable != null && semIsTalkBackIsRunning()) {
                    this.mFlingRunnable.endFling();
                }
                recycleVelocityTracker();
                break;
        }
        if (shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
        this.mActivePointerId = -1;
        this.mPointerCount = 0;
    }

    @Override // android.view.View
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        if (this.mScrollY != scrollY) {
            onScrollChanged(this.mScrollX, scrollY, this.mScrollX, this.mScrollY);
            if (!this.mSemEnableFillOut) {
                this.mScrollY = scrollY;
            }
            invalidateParentIfNeeded();
            awakenScrollBars();
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent event) {
        int axis;
        float axisValue;
        int actionButton;
        ViewRootImpl viewRootImpl;
        switch (event.getAction()) {
            case 8:
                if (event.isFromSource(2)) {
                    axis = 9;
                    axisValue = event.getAxisValue(9);
                } else if (event.isFromSource(4194304)) {
                    axis = 26;
                    axisValue = event.getAxisValue(26);
                } else {
                    axis = -1;
                    axisValue = 0.0f;
                }
                if (this.mAppWidgetSnapScroll) {
                    if (semHandleGenericMotionEvent(axisValue < 0.0f ? 130 : 33)) {
                        return true;
                    }
                }
                if (this.mIsHoveredByMouse) {
                    this.mIsMouseHoverScroll = true;
                    this.mIsMouseHoverScrollX = (int) event.getX();
                    this.mIsMouseHoverScrollY = (int) event.getY();
                }
                int delta = Math.round(this.mVerticalScrollFactor * axisValue);
                if (delta != 0) {
                    int motionIndex = delta > 0 ? 0 : getChildCount() - 1;
                    int motionViewPrevTop = 0;
                    View motionView = getChildAt(motionIndex);
                    if (motionView != null) {
                        motionViewPrevTop = motionView.getTop();
                    }
                    int overscrollMode = getOverScrollMode();
                    if (!trackMotionScroll(delta, delta)) {
                        if (Flags.scrollFeedbackApi()) {
                            initHapticScrollFeedbackProviderIfNotExists();
                            this.mHapticScrollFeedbackProvider.onScrollProgress(
                                    event.getDeviceId(), event.getSource(), axis, delta);
                        }
                        initDifferentialFlingHelperIfNotExists();
                        this.mDifferentialMotionFlingHelper.onMotionEvent(event, axis);
                        return true;
                    }
                    if (!event.isFromSource(8194)
                            && motionView != null
                            && (overscrollMode == 0 || (overscrollMode == 1 && !contentFits()))) {
                        int motionViewRealTop = motionView.getTop();
                        float overscroll =
                                (delta - (motionViewRealTop - motionViewPrevTop)) / getHeight();
                        boolean hitTopLimit = delta > 0;
                        if (Flags.scrollFeedbackApi()) {
                            initHapticScrollFeedbackProviderIfNotExists();
                            this.mHapticScrollFeedbackProvider.onScrollLimit(
                                    event.getDeviceId(), event.getSource(), axis, hitTopLimit);
                        }
                        if (hitTopLimit) {
                            this.mEdgeGlowTop.onPullDistance(overscroll, 0.5f);
                            this.mEdgeGlowTop.onRelease();
                        } else {
                            this.mEdgeGlowBottom.onPullDistance(-overscroll, 0.5f);
                            this.mEdgeGlowBottom.onRelease();
                        }
                        invalidate();
                        return true;
                    }
                }
                break;
            case 11:
                if (event.isFromSource(2)
                        && (((actionButton = event.getActionButton()) == 32 || actionButton == 2)
                                && ((this.mTouchMode == 0 || this.mTouchMode == 1)
                                        && (((viewRootImpl = getViewRootImpl()) == null
                                                        || !viewRootImpl.isDesktopMode())
                                                && performStylusButtonPressAction(event))))) {
                    removeCallbacks(this.mPendingCheckForLongPress);
                    removeCallbacks(this.mPendingCheckForTap);
                    break;
                }
                break;
        }
        return super.onGenericMotionEvent(event);
    }

    public void fling(int velocityY) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        reportScrollStateChange(2);
        this.mFlingRunnable.start(velocityY);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & 2) != 0;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
        startNestedScroll(2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(
            View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        int myUnconsumed;
        int myConsumed;
        int motionIndex = getChildCount() / 2;
        View motionView = getChildAt(motionIndex);
        int oldTop = motionView != null ? motionView.getTop() : 0;
        if (motionView == null || trackMotionScroll(-dyUnconsumed, -dyUnconsumed)) {
            if (motionView == null) {
                myUnconsumed = dyUnconsumed;
                myConsumed = 0;
            } else {
                int myConsumed2 = motionView.getTop() - oldTop;
                int myUnconsumed2 = dyUnconsumed - myConsumed2;
                myUnconsumed = myUnconsumed2;
                myConsumed = myConsumed2;
            }
            dispatchNestedScroll(0, myConsumed, 0, myUnconsumed, null);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        int childCount = getChildCount();
        if (!consumed
                && childCount > 0
                && canScrollList((int) velocityY)
                && Math.abs(velocityY) > this.mMinimumVelocity) {
            reportScrollStateChange(2);
            if (this.mFlingRunnable == null) {
                this.mFlingRunnable = new FlingRunnable();
            }
            if (!dispatchNestedPreFling(0.0f, velocityY)) {
                this.mFlingRunnable.start((int) velocityY);
                return true;
            }
            return true;
        }
        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        int width;
        int height;
        int translateX;
        int translateY;
        super.draw(canvas);
        if (shouldDisplayEdgeEffects()) {
            int scrollY = this.mScrollY;
            boolean clipToPadding = getClipToPadding();
            if (clipToPadding) {
                width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
                height = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
                translateX = this.mPaddingLeft;
                translateY = this.mPaddingTop;
            } else {
                width = getWidth();
                height = getHeight();
                translateX = 0;
                translateY = 0;
            }
            this.mEdgeGlowTop.setSize(width, height);
            this.mEdgeGlowBottom.setSize(width, height);
            if (!this.mEdgeGlowTop.isFinished()) {
                int restoreCount = canvas.save();
                canvas.clipRect(
                        translateX,
                        translateY,
                        translateX + width,
                        this.mEdgeGlowTop.getMaxHeight() + translateY);
                int edgeY = Math.min(0, this.mFirstPositionDistanceGuess + scrollY) + translateY;
                canvas.translate(translateX, edgeY);
                if (this.mEdgeGlowTop.draw(canvas)) {
                    invalidateEdgeEffects();
                }
                canvas.restoreToCount(restoreCount);
            }
            if (!this.mEdgeGlowBottom.isFinished()) {
                int restoreCount2 = canvas.save();
                canvas.clipRect(
                        translateX,
                        (translateY + height) - this.mEdgeGlowBottom.getMaxHeight(),
                        translateX + width,
                        translateY + height);
                int edgeX = (-width) + translateX;
                int edgeY2 =
                        Math.max(getHeight(), this.mLastPositionDistanceGuess + scrollY)
                                - (clipToPadding ? this.mPaddingBottom : 0);
                canvas.translate(edgeX, edgeY2);
                canvas.rotate(180.0f, width, 0.0f);
                if (this.mEdgeGlowBottom.draw(canvas)) {
                    invalidateEdgeEffects();
                }
                canvas.restoreToCount(restoreCount2);
            }
        }
        if (this.mSemEnableGoToTop) {
            drawGoToTop(canvas);
        }
        if (semIsTalkBackIsRunning()
                && this.mSemGoToTopImage != null
                && this.mSemGoToTopImage.getAlpha() != 0.0f) {
            this.mSemGoToTopImage.setAlpha(0);
        }
        if (this.mAppWidgetIndicator) {
            drawIndicator(canvas);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        if (this.mSemFastScroll != null) {
            this.mSemFastScroll.updateLayout();
        }
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void initDifferentialFlingHelperIfNotExists() {
        if (this.mDifferentialMotionFlingHelper == null) {
            this.mDifferentialMotionFlingHelper =
                    new DifferentialMotionFlingHelper(this.mContext, new DifferentialFlingTarget());
        }
    }

    private void initHapticScrollFeedbackProviderIfNotExists() {
        if (this.mHapticScrollFeedbackProvider == null) {
            this.mHapticScrollFeedbackProvider = new HapticScrollFeedbackProvider(this);
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptHoverEvent(MotionEvent event) {
        if (this.mFastScroll != null && this.mFastScroll.onInterceptHoverEvent(event)) {
            return true;
        }
        if (this.mSemFastScroll == null || !this.mSemFastScroll.onInterceptHoverEvent(event)) {
            return super.onInterceptHoverEvent(event);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        PointerIcon pointerIcon;
        if (this.mFastScroll != null
                && event.isFromSource(8194)
                && (pointerIcon = this.mFastScroll.onResolvePointerIcon(event, pointerIndex))
                        != null) {
            return pointerIcon;
        }
        return super.onResolvePointerIcon(event, pointerIndex);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00db A[FALL_THROUGH, RETURN] */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.widget.AbsListView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = (ev.getAction() & 65280) >> 8;
        int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == this.mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.mMotionX = (int) ev.getX(newPointerIndex);
            this.mMotionY = (int) ev.getY(newPointerIndex);
            this.mMotionCorrection = 0;
            this.mActivePointerId = ev.getPointerId(newPointerIndex);
            this.mLastY = this.mMotionY;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addTouchables(ArrayList<View> views) {
        int count = getChildCount();
        int firstPosition = this.mFirstPosition;
        ListAdapter adapter = this.mAdapter;
        if (adapter == null) {
            return;
        }
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (adapter.isEnabled(firstPosition + i)) {
                views.add(child);
            }
            child.addTouchables(views);
        }
    }

    void reportScrollStateChange(int newState) {
        if (newState != this.mLastScrollState) {
            Log.d(TAG, "reportScrollStateChange() newState : " + newState);
            if (this.mAppWidgetImmersiveEnalbed
                    && this.mOnScrollOffsetListener != null
                    && newState == 0) {
                this.mOnScrollOffsetListener.onScrollMotionDone(this);
            }
            if (newState != 0 && this.mLastScrollState == 0) {
                if (this.mSemEnableGoToTop) {
                    removeCallbacks(this.mSemAutoHide);
                    semSetupGoToTop(-1);
                }
                if (!this.mHoverAreaEnter) {
                    SemPerfManager.onScrollEvent(true);
                    this.mDVFSLockAcquired = true;
                }
            }
            if (newState == 0 && this.mLastScrollState != 0) {
                if (this.mSemEnableGoToTop) {
                    if (this.mGoToToping) {
                        this.mEdgeGlowTop.setSize(getWidth(), getHeight());
                        this.mEdgeGlowTop.onAbsorb(10000);
                        invalidate();
                    }
                    semAutoHide(1);
                }
                if (!this.mHoverAreaEnter && this.mDVFSLockAcquired) {
                    SemPerfManager.onScrollEvent(false);
                    this.mDVFSLockAcquired = false;
                }
            }
            if (newState != 0 && this.mLastScrollState != 0) {
                semSetupGoToTop(1);
            }
            this.mLastScrollState = newState;
            if (this.mOnScrollListener != null) {
                this.mOnScrollListener.onScrollStateChanged(this, newState);
            }
        } else if (newState != 0 && !this.mEdgeGlowTop.isFinished()) {
            semSetupGoToTop(1);
        }
        if (newState == 0 || newState == 2) {
            this.mReportChildrenToContentCaptureOnNextUpdate = true;
        }
    }

    private class FlingRunnable implements Runnable {
        private static final int FLYWHEEL_TIMEOUT = 40;
        private final Runnable mCheckFlywheel =
                new Runnable() { // from class: android.widget.AbsListView.FlingRunnable.1
                    @Override // java.lang.Runnable
                    public void run() {
                        int activeId = AbsListView.this.mActivePointerId;
                        VelocityTracker vt = AbsListView.this.mVelocityTracker;
                        OverScroller scroller = FlingRunnable.this.mScroller;
                        if (vt == null || activeId == -1) {
                            return;
                        }
                        vt.computeCurrentVelocity(1000, AbsListView.this.mMaximumVelocity);
                        float yvel = -vt.getYVelocity(activeId);
                        if (Math.abs(yvel) >= AbsListView.this.mMinimumVelocity
                                && scroller.isScrollingInDirection(0.0f, yvel)) {
                            AbsListView.this.postDelayed(this, 40L);
                            return;
                        }
                        FlingRunnable.this.endFling();
                        AbsListView.this.mTouchMode = 3;
                        AbsListView.this.reportScrollStateChange(1);
                    }
                };
        private int mLastFlingY;
        private final OverScroller mScroller;
        private boolean mSuppressIdleStateChangeCall;

        FlingRunnable() {
            this.mScroller = new OverScroller(AbsListView.this.getContext());
        }

        float getSplineFlingDistance(int velocity) {
            return (float) this.mScroller.getSplineFlingDistance(velocity);
        }

        void start(int initialVelocity) {
            int initialY = initialVelocity < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = initialY;
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(
                    0, initialY, 0, initialVelocity, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            AbsListView.this.mTouchMode = 4;
            if (AbsListView.this.mAppWidgetSnapScroll) {
                View secondView = AbsListView.this.getChildAt(1);
                int contentH = AbsListView.this.getHeight();
                int firstVisiblePos = AbsListView.this.getFirstVisiblePosition();
                int flingDistance = this.mScroller.getFinalY() - this.mScroller.getCurrY();
                View focusedView = AbsListView.this.getChildAt(0);
                if (secondView != null) {
                    this.mScroller.abortAnimation();
                    if (initialVelocity < 0) {
                        focusedView = secondView;
                    }
                    if (focusedView != null) {
                        if (initialVelocity < 0) {
                            if (focusedView.getTop() - flingDistance > contentH / 2) {
                                AbsListView.this.smoothScrollToPositionFromTop(firstVisiblePos, 0);
                            } else {
                                AbsListView.this.smoothScrollToPositionFromTop(
                                        firstVisiblePos + 1, 0);
                            }
                        } else if (focusedView.getBottom() - flingDistance < contentH / 2) {
                            AbsListView.this.smoothScrollToPositionFromTop(firstVisiblePos + 1, 0);
                        } else {
                            AbsListView.this.smoothScrollToPositionFromTop(
                                    firstVisiblePos, contentH - focusedView.getHeight());
                        }
                    }
                } else if (focusedView != null) {
                    if (initialVelocity < 0) {
                        if (focusedView.getTop() - flingDistance > 0) {
                            this.mScroller.abortAnimation();
                            AbsListView.this.smoothScrollBy(focusedView.getTop(), 200);
                        }
                    } else if (focusedView.getBottom() - flingDistance < contentH) {
                        this.mScroller.abortAnimation();
                        AbsListView.this.smoothScrollBy(focusedView.getBottom() - contentH, 200);
                    }
                }
            }
            this.mSuppressIdleStateChangeCall = false;
            AbsListView.this.removeCallbacks(this);
            AbsListView.this.postOnAnimation(this);
            if (AbsListView.this.mFlingStrictSpan == null) {
                AbsListView.this.mFlingStrictSpan =
                        StrictMode.enterCriticalSpan("AbsListView-fling");
            }
        }

        void start(int initialVelocity, boolean accDisabled) {
            int initialY = initialVelocity < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = initialY;
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(
                    0,
                    initialY,
                    0,
                    initialVelocity,
                    0,
                    Integer.MAX_VALUE,
                    0,
                    Integer.MAX_VALUE,
                    accDisabled);
            AbsListView.this.mTouchMode = 4;
            this.mSuppressIdleStateChangeCall = false;
            AbsListView.this.removeCallbacks(this);
            AbsListView.this.invalidate();
            AbsListView.this.postOnAnimation(this);
            if (AbsListView.this.mFlingStrictSpan == null) {
                AbsListView.this.mFlingStrictSpan =
                        StrictMode.enterCriticalSpan("AbsListView-fling");
            }
        }

        void startSpringback() {
            this.mSuppressIdleStateChangeCall = false;
            if (this.mScroller.springBack(0, AbsListView.this.mScrollY, 0, 0, 0, 0)) {
                AbsListView.this.mTouchMode = 6;
                AbsListView.this.invalidate();
                AbsListView.this.postOnAnimation(this);
            } else {
                AbsListView.this.mTouchMode = -1;
                AbsListView.this.reportScrollStateChange(0);
            }
        }

        void startOverfling(int initialVelocity) {
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(
                    0,
                    AbsListView.this.mScrollY,
                    0,
                    initialVelocity,
                    0,
                    0,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE,
                    0,
                    AbsListView.this.getHeight());
            AbsListView.this.mTouchMode = 6;
            this.mSuppressIdleStateChangeCall = false;
            AbsListView.this.invalidate();
            AbsListView.this.postOnAnimation(this);
        }

        void edgeReached(int delta) {
            this.mScroller.notifyVerticalEdgeReached(
                    AbsListView.this.mScrollY, 0, AbsListView.this.mOverflingDistance);
            int overscrollMode = AbsListView.this.getOverScrollMode();
            if (overscrollMode == 0 || (overscrollMode == 1 && !AbsListView.this.contentFits())) {
                AbsListView.this.mTouchMode = 6;
                int vel = (int) this.mScroller.getCurrVelocity();
                if (delta > 0) {
                    AbsListView.this.mEdgeGlowTop.onAbsorb(vel);
                } else {
                    AbsListView.this.mEdgeGlowBottom.onAbsorb(vel);
                }
            } else {
                AbsListView.this.mTouchMode = -1;
                if (AbsListView.this.mPositionScroller != null) {
                    AbsListView.this.mPositionScroller.stop();
                }
            }
            AbsListView.this.invalidate();
            AbsListView.this.postOnAnimation(this);
        }

        void startScroll(
                int distance,
                int duration,
                boolean linear,
                boolean suppressEndFlingStateChangeCall) {
            int initialY = distance < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = initialY;
            DecelerateInterpolator interpolator =
                    AbsListView.this.mAppWidgetSnapScroll
                            ? AbsListView.this.mDecelerateInterpolator
                            : null;
            this.mScroller.setInterpolator(linear ? AbsListView.sLinearInterpolator : interpolator);
            this.mScroller.startScroll(0, initialY, 0, distance, duration);
            AbsListView.this.mTouchMode = 4;
            this.mSuppressIdleStateChangeCall = suppressEndFlingStateChangeCall;
            AbsListView.this.postOnAnimation(this);
        }

        void endFling() {
            AbsListView.this.mTouchMode = -1;
            AbsListView.this.removeCallbacks(this);
            AbsListView.this.removeCallbacks(this.mCheckFlywheel);
            if (!this.mSuppressIdleStateChangeCall) {
                AbsListView.this.reportScrollStateChange(0);
            }
            AbsListView.this.clearScrollingCache();
            this.mScroller.abortAnimation();
            if (AbsListView.this.mFlingStrictSpan != null) {
                AbsListView.this.mFlingStrictSpan.finish();
                AbsListView.this.mFlingStrictSpan = null;
            }
        }

        void removeAllCallbacks() {
            AbsListView.this.removeCallbacks(this);
            AbsListView.this.removeCallbacks(this.mCheckFlywheel);
            this.mScroller.abortAnimation();
        }

        void flywheelTouch() {
            AbsListView.this.postDelayed(this.mCheckFlywheel, 40L);
        }

        @Override // java.lang.Runnable
        public void run() {
            int delta;
            boolean crossUp = false;
            switch (AbsListView.this.mTouchMode) {
                case 3:
                    if (this.mScroller.isFinished()) {
                        return;
                    }
                    break;
                case 4:
                    break;
                case 5:
                default:
                    endFling();
                    return;
                case 6:
                    OverScroller scroller = this.mScroller;
                    if (scroller.computeScrollOffset()) {
                        int scrollY = AbsListView.this.mScrollY;
                        int currY = scroller.getCurrY();
                        int deltaY = currY - scrollY;
                        if (AbsListView.this.overScrollBy(
                                0,
                                deltaY,
                                0,
                                scrollY,
                                0,
                                0,
                                0,
                                AbsListView.this.mOverflingDistance,
                                false)) {
                            boolean crossDown = scrollY <= 0 && currY > 0;
                            if (scrollY >= 0 && currY < 0) {
                                crossUp = true;
                            }
                            if (crossDown || crossUp) {
                                scroller.getCurrVelocity();
                            } else {
                                startSpringback();
                            }
                        } else {
                            AbsListView.this.invalidate();
                            AbsListView.this.postOnAnimation(this);
                        }
                        if (Flags.viewVelocityApi()) {
                            AbsListView.this.setFrameContentVelocity(
                                    Math.abs(this.mScroller.getCurrVelocity()));
                            return;
                        }
                        return;
                    }
                    endFling();
                    return;
            }
            if (AbsListView.this.mDataChanged) {
                AbsListView.this.layoutChildren();
            }
            if (AbsListView.this.mItemCount == 0 || AbsListView.this.getChildCount() == 0) {
                AbsListView.this.mEdgeGlowBottom.onRelease();
                AbsListView.this.mEdgeGlowTop.onRelease();
                endFling();
                return;
            }
            OverScroller scroller2 = this.mScroller;
            boolean more = scroller2.computeScrollOffset();
            int y = scroller2.getCurrY();
            if (Flags.viewVelocityApi()) {
                AbsListView.this.setFrameContentVelocity(
                        Math.abs(this.mScroller.getCurrVelocity()));
            }
            int delta2 = AbsListView.this.consumeFlingInStretch(this.mLastFlingY - y);
            if (delta2 > 0) {
                AbsListView.this.mMotionPosition = AbsListView.this.mFirstPosition;
                View firstView = AbsListView.this.getChildAt(0);
                AbsListView.this.mMotionViewOriginalTop = firstView.getTop();
                delta =
                        Math.min(
                                ((AbsListView.this.getHeight() - AbsListView.this.mPaddingBottom)
                                                - AbsListView.this.mPaddingTop)
                                        - 1,
                                delta2);
            } else {
                int offsetToLast = AbsListView.this.getChildCount() - 1;
                AbsListView.this.mMotionPosition = AbsListView.this.mFirstPosition + offsetToLast;
                View lastView = AbsListView.this.getChildAt(offsetToLast);
                AbsListView.this.mMotionViewOriginalTop = lastView.getTop();
                delta =
                        Math.max(
                                -(((AbsListView.this.getHeight() - AbsListView.this.mPaddingBottom)
                                                - AbsListView.this.mPaddingTop)
                                        - 1),
                                delta2);
            }
            View motionView =
                    AbsListView.this.getChildAt(
                            AbsListView.this.mMotionPosition - AbsListView.this.mFirstPosition);
            int oldTop = 0;
            if (motionView != null) {
                oldTop = motionView.getTop();
            }
            boolean atEdge = AbsListView.this.trackMotionScroll(delta, delta);
            if (atEdge && delta != 0) {
                crossUp = true;
            }
            if (crossUp) {
                if (motionView != null) {
                    int overshoot = -(delta - (motionView.getTop() - oldTop));
                    AbsListView.this.overScrollBy(
                            0,
                            overshoot,
                            0,
                            AbsListView.this.mScrollY,
                            0,
                            0,
                            0,
                            AbsListView.this.mOverflingDistance,
                            false);
                }
                if (more) {
                    edgeReached(delta);
                    return;
                }
                return;
            }
            if (more && !crossUp) {
                if (atEdge) {
                    AbsListView.this.invalidate();
                }
                this.mLastFlingY = y;
                AbsListView.this.postOnAnimation(this);
            } else {
                endFling();
            }
            if (AbsListView.this.mJumpScrollToTopState == AbsListView.JUMP_SCROLL_TO_TOP_FINISHING
                    && AbsListView.this.mFirstPosition == 0
                    && delta == 0
                    && !more) {
                AbsListView.this.mJumpScrollToTopState = AbsListView.JUMP_SCROLL_TO_TOP_IDLE;
                AbsListView.this.postOnJumpScrollToFinished();
            }
        }
    }

    public void setFriction(float friction) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        this.mFlingRunnable.mScroller.setFriction(friction);
    }

    public void setVelocityScale(float scale) {
        this.mVelocityScale = scale;
    }

    AbsPositionScroller createPositionScroller() {
        return new PositionScroller();
    }

    public void smoothScrollToPosition(int position) {
        semSendBroadcastPosition(position, 1);
        if (this.mAppWidgetIndicator) {
            semInvalidateIndicator(position);
        }
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.start(position);
    }

    public void smoothScrollToPositionFromTop(int position, int offset, int duration) {
        semSendBroadcastPosition(position, 1);
        if (this.mAppWidgetIndicator) {
            semInvalidateIndicator(position);
        }
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.startWithOffset(position, offset, duration);
    }

    public void smoothScrollToPositionFromTop(int position, int offset) {
        semSendBroadcastPosition(position, 1);
        if (this.mAppWidgetIndicator) {
            semInvalidateIndicator(position);
        }
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.startWithOffset(position, offset);
    }

    private void scrollToPositionFromTop(int position, int offset) {
        setSelectionFromTop(position, offset);
    }

    public void smoothScrollToPosition(int position, int boundPosition) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.start(position, boundPosition);
    }

    public void smoothScrollBy(int distance, int duration) {
        smoothScrollBy(distance, duration, false, false);
    }

    void smoothScrollBy(
            int distance, int duration, boolean linear, boolean suppressEndFlingStateChangeCall) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        int firstPos = this.mFirstPosition;
        int childCount = getChildCount();
        int lastPos = firstPos + childCount;
        int topLimit = getPaddingTop();
        int bottomLimit = getHeight() - getPaddingBottom();
        if (distance == 0
                || this.mItemCount == 0
                || childCount == 0
                || ((firstPos == 0 && getChildAt(0).getTop() == topLimit && distance < 0)
                        || (lastPos == this.mItemCount
                                && getChildAt(childCount - 1).getBottom() == bottomLimit
                                && distance > 0))) {
            this.mFlingRunnable.endFling();
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
        } else {
            reportScrollStateChange(2);
            this.mFlingRunnable.startScroll(
                    distance, duration, linear, suppressEndFlingStateChangeCall);
        }
        if (semIsSupportGotoTop()
                && this.mSemCanGoFuther
                && this.mFirstPosition == 0
                && !canScrollUp()) {
            Log.d(TAG, " re calculate done2 mPositionScroller = " + this.mPositionScroller);
            this.mSemCanGoFuther = false;
            this.mFlingRunnable.endFling();
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            if (this.mGoToToping) {
                this.mEdgeGlowTop.setSize(getWidth(), getHeight());
                this.mEdgeGlowTop.onAbsorb(10000);
                invalidate();
            }
        }
    }

    void smoothScrollByOffset(int position) {
        View child;
        int index = -1;
        if (position < 0) {
            index = getFirstVisiblePosition();
        } else if (position > 0) {
            index = getLastVisiblePosition();
        }
        if (index > -1 && (child = getChildAt(index - getFirstVisiblePosition())) != null) {
            Rect visibleRect = new Rect();
            if (child.getGlobalVisibleRect(visibleRect)) {
                int childRectArea = child.getWidth() * child.getHeight();
                int visibleRectArea = visibleRect.width() * visibleRect.height();
                float visibleArea = visibleRectArea / childRectArea;
                if (position < 0 && visibleArea < 0.75f) {
                    index++;
                } else if (position > 0 && visibleArea < 0.75f) {
                    index--;
                }
            }
            int childRectArea2 = getCount();
            smoothScrollToPosition(Math.max(0, Math.min(childRectArea2, index + position)));
        }
    }

    private void createScrollingCache() {
        if (this.mScrollingCacheEnabled && !this.mCachingStarted && !isHardwareAccelerated()) {
            setChildrenDrawnWithCacheEnabled(true);
            setChildrenDrawingCacheEnabled(true);
            this.mCachingActive = true;
            this.mCachingStarted = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearScrollingCache() {
        if (!isHardwareAccelerated()) {
            if (this.mClearScrollingCache == null) {
                this.mClearScrollingCache =
                        new Runnable() { // from class: android.widget.AbsListView.4
                            @Override // java.lang.Runnable
                            public void run() {
                                if (AbsListView.this.mCachingStarted) {
                                    AbsListView absListView = AbsListView.this;
                                    AbsListView.this.mCachingActive = false;
                                    absListView.mCachingStarted = false;
                                    AbsListView.this.setChildrenDrawnWithCacheEnabled(false);
                                    if ((AbsListView.this.mPersistentDrawingCache & 2) == 0) {
                                        AbsListView.this.setChildrenDrawingCacheEnabled(false);
                                    }
                                    if (!AbsListView.this.isAlwaysDrawnWithCacheEnabled()) {
                                        AbsListView.this.invalidate();
                                    }
                                }
                            }
                        };
            }
            post(this.mClearScrollingCache);
        }
    }

    public void scrollListBy(int y) {
        trackMotionScroll(-y, -y);
    }

    public boolean canScrollList(int direction) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        int firstPosition = this.mFirstPosition;
        Rect listPadding = this.mListPadding;
        if (direction > 0) {
            int lastBottom = getChildAt(childCount - 1).getBottom();
            int lastPosition = firstPosition + childCount;
            if (lastPosition >= this.mItemCount && lastBottom <= getHeight() - listPadding.bottom) {
                return false;
            }
            return true;
        }
        int firstTop = getChildAt(0).getTop();
        if (firstPosition <= 0 && firstTop >= listPadding.top) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x025c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean trackMotionScroll(int r26, int r27) {
        /*
            Method dump skipped, instructions count: 679
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.widget.AbsListView.trackMotionScroll(int,"
                    + " int):boolean");
    }

    int getHeaderViewsCount() {
        return 0;
    }

    int getFooterViewsCount() {
        return 0;
    }

    void hideSelector() {
        if (this.mSelectedPosition != -1) {
            if (this.mLayoutMode != 4) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
            if (this.mNextSelectedPosition >= 0
                    && this.mNextSelectedPosition != this.mSelectedPosition) {
                this.mResurrectToPosition = this.mNextSelectedPosition;
            }
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectedTop = 0;
            this.mSelectorPosition = -1;
        }
    }

    int reconcileSelectedPosition() {
        int position = this.mSelectedPosition;
        if (position < 0) {
            position = this.mResurrectToPosition;
        }
        return Math.min(Math.max(0, position), this.mItemCount - 1);
    }

    int findClosestMotionRow(int y) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return -1;
        }
        int motionRow = findMotionRow(y);
        return motionRow != -1 ? motionRow : (this.mFirstPosition + childCount) - 1;
    }

    public void invalidateViews() {
        this.mDataChanged = true;
        rememberSyncState();
        requestLayout();
        invalidate();
    }

    boolean resurrectSelectionIfNeeded() {
        if (this.mSelectedPosition < 0 && resurrectSelection()) {
            updateSelectorState();
            return true;
        }
        return false;
    }

    boolean resurrectSelection() {
        int selectedPos;
        int childCount = getChildCount();
        if (childCount <= 0) {
            return false;
        }
        int selectedTop = 0;
        int childrenTop = this.mListPadding.top;
        int childrenBottom = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        int firstPosition = this.mFirstPosition;
        int toPosition = this.mResurrectToPosition;
        boolean down = true;
        if (toPosition >= firstPosition && toPosition < firstPosition + childCount) {
            selectedPos = toPosition;
            View selected = getChildAt(selectedPos - this.mFirstPosition);
            selectedTop = selected.getTop();
            int selectedBottom = selected.getBottom();
            if (selectedTop < childrenTop) {
                selectedTop = childrenTop + getVerticalFadingEdgeLength();
            } else if (selectedBottom > childrenBottom) {
                selectedTop =
                        (childrenBottom - selected.getMeasuredHeight())
                                - getVerticalFadingEdgeLength();
            }
        } else if (toPosition < firstPosition) {
            selectedPos = firstPosition;
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                int top = getChildAt(i).getTop();
                if (i == 0) {
                    selectedTop = top;
                    if (firstPosition > 0 || top < childrenTop) {
                        childrenTop += getVerticalFadingEdgeLength();
                    }
                }
                if (top < childrenTop) {
                    i++;
                } else {
                    selectedPos = firstPosition + i;
                    selectedTop = top;
                    break;
                }
            }
        } else {
            int selectedPos2 = this.mItemCount;
            down = false;
            int selectedPos3 = (firstPosition + childCount) - 1;
            int i2 = childCount - 1;
            while (true) {
                if (i2 < 0) {
                    selectedPos = selectedPos3;
                    break;
                }
                View v = getChildAt(i2);
                int top2 = v.getTop();
                int bottom = v.getBottom();
                if (i2 == childCount - 1) {
                    selectedTop = top2;
                    if (firstPosition + childCount < selectedPos2 || bottom > childrenBottom) {
                        childrenBottom -= getVerticalFadingEdgeLength();
                    }
                }
                if (bottom > childrenBottom) {
                    i2--;
                } else {
                    int selectedPos4 = firstPosition + i2;
                    selectedTop = top2;
                    selectedPos = selectedPos4;
                    break;
                }
            }
        }
        this.mResurrectToPosition = -1;
        removeCallbacks(this.mFlingRunnable);
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        this.mTouchMode = -1;
        clearScrollingCache();
        this.mSpecificTop = selectedTop;
        int selectedPos5 = lookForSelectablePosition(selectedPos, down);
        if (selectedPos5 >= firstPosition && selectedPos5 <= getLastVisiblePosition()) {
            this.mLayoutMode = 4;
            updateSelectorState();
            setSelectionInt(selectedPos5);
            invokeOnItemScrollListener();
        } else {
            selectedPos5 = -1;
        }
        reportScrollStateChange(0);
        return selectedPos5 >= 0;
    }

    void confirmCheckedPositionsById() {
        int i;
        boolean found;
        this.mCheckStates.clear();
        boolean checkedCountChanged = false;
        int checkedIndex = 0;
        while (checkedIndex < this.mCheckedIdStates.size()) {
            long id = this.mCheckedIdStates.keyAt(checkedIndex);
            int lastPos = this.mCheckedIdStates.valueAt(checkedIndex).intValue();
            long lastPosId = this.mAdapter.getItemId(lastPos);
            boolean z = true;
            if (id != lastPosId) {
                int start = Math.max(0, lastPos - 20);
                int end = Math.min(lastPos + 20, this.mItemCount);
                int searchPos = start;
                while (true) {
                    if (searchPos >= end) {
                        found = false;
                        break;
                    }
                    long searchId = this.mAdapter.getItemId(searchPos);
                    if (id != searchId) {
                        searchPos++;
                        z = true;
                    } else {
                        this.mCheckStates.put(searchPos, z);
                        this.mCheckedIdStates.setValueAt(checkedIndex, Integer.valueOf(searchPos));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    this.mCheckedIdStates.delete(id);
                    checkedIndex--;
                    this.mCheckedItemCount--;
                    checkedCountChanged = true;
                    if (this.mChoiceActionMode != null && this.mMultiChoiceModeCallback != null) {
                        this.mMultiChoiceModeCallback.onItemCheckedStateChanged(
                                this.mChoiceActionMode, lastPos, id, false);
                    }
                }
                i = 1;
            } else {
                i = 1;
                this.mCheckStates.put(lastPos, true);
            }
            checkedIndex += i;
        }
        if (checkedCountChanged && this.mChoiceActionMode != null) {
            this.mChoiceActionMode.invalidate();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.widget.AdapterView
    protected void handleDataChanged() {
        int count = this.mItemCount;
        int lastHandledItemCount = this.mLastHandledItemCount;
        this.mLastHandledItemCount = this.mItemCount;
        if (this.mIsMultiFocusEnabled
                && this.mAdapter != null
                && this.mItemCount != this.mOldAdapterItemCount) {
            this.mSemPressItemListArray = new ArrayList<>();
            resetPressItemListArray();
            this.mOldAdapterItemCount = this.mItemCount;
        }
        if (this.mChoiceMode != 0 && this.mAdapter != null && this.mAdapter.hasStableIds()) {
            confirmCheckedPositionsById();
        }
        this.mRecycler.clearTransientStateViews();
        if (count > 0) {
            if (this.mNeedSync) {
                this.mNeedSync = false;
                this.mPendingSync = null;
                if (this.mTranscriptMode == 2) {
                    this.mLayoutMode = 3;
                    return;
                }
                if (this.mTranscriptMode == 1) {
                    if (this.mForceTranscriptScroll) {
                        this.mForceTranscriptScroll = false;
                        this.mLayoutMode = 3;
                        return;
                    }
                    int childCount = getChildCount();
                    int listBottom = getHeight() - getPaddingBottom();
                    View lastChild = getChildAt(childCount - 1);
                    int lastBottom = lastChild != null ? lastChild.getBottom() : listBottom;
                    if (this.mFirstPosition + childCount >= lastHandledItemCount
                            && lastBottom <= listBottom) {
                        this.mLayoutMode = 3;
                        return;
                    }
                    awakenScrollBars();
                }
                switch (this.mSyncMode) {
                    case 0:
                        if (isInTouchMode()) {
                            this.mLayoutMode = 5;
                            this.mSyncPosition =
                                    Math.min(Math.max(0, this.mSyncPosition), count - 1);
                            break;
                        } else {
                            int newPos = findSyncPosition();
                            if (newPos >= 0 && lookForSelectablePosition(newPos, true) == newPos) {
                                this.mSyncPosition = newPos;
                                if (this.mSyncHeight == getHeight()) {
                                    this.mLayoutMode = 5;
                                } else {
                                    this.mLayoutMode = 2;
                                }
                                setNextSelectedPositionInt(newPos);
                                break;
                            }
                        }
                        break;
                    case 1:
                        this.mLayoutMode = 5;
                        this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), count - 1);
                        break;
                }
                return;
            }
            if (!isInTouchMode()) {
                int newPos2 = getSelectedItemPosition();
                if (newPos2 >= count) {
                    newPos2 = count - 1;
                }
                if (newPos2 < 0) {
                    newPos2 = 0;
                }
                int selectablePos = lookForSelectablePosition(newPos2, true);
                if (selectablePos >= 0) {
                    setNextSelectedPositionInt(selectablePos);
                    return;
                }
                int selectablePos2 = lookForSelectablePosition(newPos2, false);
                if (selectablePos2 >= 0) {
                    setNextSelectedPositionInt(selectablePos2);
                    return;
                }
            } else if (this.mResurrectToPosition >= 0) {
                return;
            }
        }
        this.mLayoutMode = this.mStackFromBottom ? 3 : 1;
        this.mSelectedPosition = -1;
        this.mSelectedRowId = Long.MIN_VALUE;
        this.mNextSelectedPosition = -1;
        this.mNextSelectedRowId = Long.MIN_VALUE;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mSelectorPosition = -1;
        checkSelectionChanged();
    }

    @Override // android.view.View
    protected void onDisplayHint(int hint) {
        super.onDisplayHint(hint);
        switch (hint) {
            case 0:
                if (this.mFiltered && this.mPopup != null && !this.mPopup.isShowing()) {
                    showPopup();
                    break;
                }
                break;
            case 4:
                if (this.mPopup != null && this.mPopup.isShowing()) {
                    dismissPopup();
                    break;
                }
                break;
        }
        this.mPopupHidden = hint == 4;
    }

    private void dismissPopup() {
        if (this.mPopup != null) {
            this.mPopup.dismiss();
        }
    }

    private void showPopup() {
        if (getWindowVisibility() == 0) {
            createTextFilter(true);
            positionPopup();
            checkFocus();
        }
    }

    private void positionPopup() {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int[] xy = new int[2];
        getLocationOnScreen(xy);
        int bottomGap =
                ((screenHeight - xy[1]) - getHeight()) + ((int) (this.mDensityScale * 20.0f));
        if (!this.mPopup.isShowing()) {
            this.mPopup.showAtLocation(this, 81, xy[0], bottomGap);
        } else {
            this.mPopup.update(xy[0], bottomGap, -1, -1);
        }
    }

    static int getDistance(Rect source, Rect dest, int direction) {
        int sX;
        int sY;
        int dX;
        int dY;
        switch (direction) {
            case 1:
            case 2:
                int sX2 = source.right;
                sX = sX2 + (source.width() / 2);
                sY = source.top + (source.height() / 2);
                dX = dest.left + (dest.width() / 2);
                dY = dest.top + (dest.height() / 2);
                break;
            case 17:
                sX = source.left;
                sY = source.top + (source.height() / 2);
                dX = dest.right;
                dY = dest.top + (dest.height() / 2);
                break;
            case 33:
                int sX3 = source.left;
                sX = sX3 + (source.width() / 2);
                sY = source.top;
                dX = dest.left + (dest.width() / 2);
                dY = dest.bottom;
                break;
            case 66:
                sX = source.right;
                sY = source.top + (source.height() / 2);
                dX = dest.left;
                dY = dest.top + (dest.height() / 2);
                break;
            case 130:
                sX = source.left + (source.width() / 2);
                sY = source.bottom;
                dX = dest.left + (dest.width() / 2);
                dY = dest.top;
                break;
            default:
                throw new IllegalArgumentException(
                        "direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT,"
                            + " FOCUS_FORWARD, FOCUS_BACKWARD}.");
        }
        int deltaX = dX - sX;
        int deltaY = dY - sY;
        return (deltaY * deltaY) + (deltaX * deltaX);
    }

    @Override // android.widget.AdapterView
    protected boolean isInFilterMode() {
        return this.mFiltered;
    }

    boolean sendToTextFilter(int keyCode, int count, KeyEvent event) {
        if (!acceptFilter()) {
            return false;
        }
        boolean handled = false;
        boolean okToSend = true;
        switch (keyCode) {
            case 4:
            case 111:
                if (this.mFiltered && this.mPopup != null && this.mPopup.isShowing()) {
                    if (event.getAction() != 0 || event.getRepeatCount() != 0) {
                        if (event.getAction() == 1 && event.isTracking() && !event.isCanceled()) {
                            handled = true;
                            this.mTextFilter.lambda$setTextAsync$0("");
                        }
                    } else {
                        KeyEvent.DispatcherState state = getKeyDispatcherState();
                        if (state != null) {
                            state.startTracking(event, this);
                        }
                        handled = true;
                    }
                }
                okToSend = false;
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 66:
            case 160:
                okToSend = false;
                break;
            case 62:
                okToSend = this.mFiltered;
                break;
        }
        if (okToSend) {
            createTextFilter(true);
            KeyEvent forwardEvent = event;
            if (forwardEvent.getRepeatCount() > 0) {
                forwardEvent = KeyEvent.changeTimeRepeat(event, event.getEventTime(), 0);
            }
            int action = event.getAction();
            switch (action) {
                case 0:
                    boolean handled2 = this.mTextFilter.onKeyDown(keyCode, forwardEvent);
                    if (keyCode == 59 || keyCode == 60) {
                        this.mIsShiftkeyPressed = true;
                        return handled2;
                    }
                    if (keyCode == 113 || keyCode == 114) {
                        this.mIsCtrlkeyPressed = true;
                        return handled2;
                    }
                    return handled2;
                case 1:
                    boolean handled3 = this.mTextFilter.onKeyUp(keyCode, forwardEvent);
                    if (keyCode == 59 || keyCode == 60) {
                        this.mIsShiftkeyPressed = false;
                        this.mOldKeyCode = 0;
                        this.mCurrentKeyCode = 0;
                        this.mFirstPressedPoint = -1;
                        return handled3;
                    }
                    if (keyCode == 113 || keyCode == 114) {
                        this.mIsCtrlkeyPressed = false;
                        return handled3;
                    }
                    return handled3;
                case 2:
                    return this.mTextFilter.onKeyMultiple(keyCode, count, event);
                default:
                    return handled;
            }
        }
        return handled;
    }

    @Override // android.view.View
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        if (isTextFilterEnabled()) {
            if (this.mPublicInputConnection == null) {
                this.mDefInputConnection = new BaseInputConnection((View) this, false);
                this.mPublicInputConnection = new InputConnectionWrapper(outAttrs);
            }
            outAttrs.inputType = 177;
            outAttrs.imeOptions = 6;
            return this.mPublicInputConnection;
        }
        return null;
    }

    private class InputConnectionWrapper implements InputConnection {
        private final EditorInfo mOutAttrs;
        private InputConnection mTarget;

        public InputConnectionWrapper(EditorInfo outAttrs) {
            this.mOutAttrs = outAttrs;
        }

        private InputConnection getTarget() {
            if (this.mTarget == null) {
                this.mTarget =
                        AbsListView.this
                                .getTextFilterInput()
                                .onCreateInputConnection(this.mOutAttrs);
            }
            return this.mTarget;
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean reportFullscreenMode(boolean enabled) {
            return AbsListView.this.mDefInputConnection.reportFullscreenMode(enabled);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean performEditorAction(int editorAction) {
            if (editorAction != 6) {
                return false;
            }
            InputMethodManager imm =
                    (InputMethodManager)
                            AbsListView.this
                                    .getContext()
                                    .getSystemService(InputMethodManager.class);
            if (imm != null) {
                imm.hideSoftInputFromWindow(AbsListView.this.getWindowToken(), 0);
                return true;
            }
            return true;
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean sendKeyEvent(KeyEvent event) {
            return AbsListView.this.mDefInputConnection.sendKeyEvent(event);
        }

        @Override // android.view.inputmethod.InputConnection
        public CharSequence getTextBeforeCursor(int n, int flags) {
            return this.mTarget == null ? "" : this.mTarget.getTextBeforeCursor(n, flags);
        }

        @Override // android.view.inputmethod.InputConnection
        public CharSequence getTextAfterCursor(int n, int flags) {
            return this.mTarget == null ? "" : this.mTarget.getTextAfterCursor(n, flags);
        }

        @Override // android.view.inputmethod.InputConnection
        public CharSequence getSelectedText(int flags) {
            return this.mTarget == null ? "" : this.mTarget.getSelectedText(flags);
        }

        @Override // android.view.inputmethod.InputConnection
        public SurroundingText getSurroundingText(int beforeLength, int afterLength, int flags) {
            if (this.mTarget == null) {
                return null;
            }
            return this.mTarget.getSurroundingText(beforeLength, afterLength, flags);
        }

        @Override // android.view.inputmethod.InputConnection
        public int getCursorCapsMode(int reqModes) {
            if (this.mTarget == null) {
                return 16384;
            }
            return this.mTarget.getCursorCapsMode(reqModes);
        }

        @Override // android.view.inputmethod.InputConnection
        public ExtractedText getExtractedText(ExtractedTextRequest request, int flags) {
            return getTarget().getExtractedText(request, flags);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            return getTarget().deleteSurroundingText(beforeLength, afterLength);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean deleteSurroundingTextInCodePoints(int beforeLength, int afterLength) {
            return getTarget().deleteSurroundingTextInCodePoints(beforeLength, afterLength);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean setComposingText(CharSequence text, int newCursorPosition) {
            return getTarget().setComposingText(text, newCursorPosition);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean setComposingRegion(int start, int end) {
            return getTarget().setComposingRegion(start, end);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean finishComposingText() {
            return this.mTarget == null || this.mTarget.finishComposingText();
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean commitText(CharSequence text, int newCursorPosition) {
            return getTarget().commitText(text, newCursorPosition);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean commitCompletion(CompletionInfo text) {
            return getTarget().commitCompletion(text);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean commitCorrection(CorrectionInfo correctionInfo) {
            return getTarget().commitCorrection(correctionInfo);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean setSelection(int start, int end) {
            return getTarget().setSelection(start, end);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean performContextMenuAction(int id) {
            return getTarget().performContextMenuAction(id);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean beginBatchEdit() {
            return getTarget().beginBatchEdit();
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean endBatchEdit() {
            return getTarget().endBatchEdit();
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean clearMetaKeyStates(int states) {
            return getTarget().clearMetaKeyStates(states);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean performPrivateCommand(String action, Bundle data) {
            return getTarget().performPrivateCommand(action, data);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean requestCursorUpdates(int cursorUpdateMode) {
            return getTarget().requestCursorUpdates(cursorUpdateMode);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean requestCursorUpdates(int cursorUpdateMode, int cursorUpdateFilter) {
            return getTarget().requestCursorUpdates(cursorUpdateMode, cursorUpdateFilter);
        }

        @Override // android.view.inputmethod.InputConnection
        public Handler getHandler() {
            return getTarget().getHandler();
        }

        @Override // android.view.inputmethod.InputConnection
        public void closeConnection() {
            getTarget().closeConnection();
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean commitContent(InputContentInfo inputContentInfo, int flags, Bundle opts) {
            return getTarget().commitContent(inputContentInfo, flags, opts);
        }
    }

    @Override // android.view.View
    public boolean checkInputConnectionProxy(View view) {
        return view == this.mTextFilter;
    }

    private void createTextFilter(boolean animateEntrance) {
        if (this.mPopup == null) {
            PopupWindow p = new PopupWindow(getContext());
            p.setFocusable(false);
            p.setTouchable(false);
            p.setInputMethodMode(2);
            p.setContentView(getTextFilterInput());
            p.setWidth(-2);
            p.setHeight(-2);
            p.setBackgroundDrawable(null);
            this.mPopup = p;
            getViewTreeObserver().addOnGlobalLayoutListener(this);
            this.mGlobalLayoutListenerAddedFilter = true;
        }
        if (animateEntrance) {
            this.mPopup.setAnimationStyle(R.style.Animation_TypingFilter);
        } else {
            this.mPopup.setAnimationStyle(R.style.Animation_TypingFilterRestore);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EditText getTextFilterInput() {
        if (this.mTextFilter == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            this.mTextFilter =
                    (EditText) layoutInflater.inflate(R.layout.typing_filter, (ViewGroup) null);
            this.mTextFilter.setRawInputType(177);
            this.mTextFilter.setImeOptions(268435456);
            this.mTextFilter.addTextChangedListener(this);
        }
        return this.mTextFilter;
    }

    public void clearTextFilter() {
        if (this.mFiltered) {
            getTextFilterInput().lambda$setTextAsync$0("");
            this.mFiltered = false;
            if (this.mPopup != null && this.mPopup.isShowing()) {
                dismissPopup();
            }
        }
    }

    public boolean hasTextFilter() {
        return this.mFiltered;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        if (isShown()) {
            if (this.mFiltered
                    && this.mPopup != null
                    && !this.mPopup.isShowing()
                    && !this.mPopupHidden) {
                showPopup();
                return;
            }
            return;
        }
        if (this.mPopup != null && this.mPopup.isShowing()) {
            dismissPopup();
        }
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isTextFilterEnabled()) {
            createTextFilter(true);
            int length = s.length();
            boolean showing = this.mPopup.isShowing();
            if (!showing && length > 0) {
                showPopup();
                this.mFiltered = true;
            } else if (showing && length == 0) {
                dismissPopup();
                this.mFiltered = false;
            }
            if (this.mAdapter instanceof Filterable) {
                Filter f = ((Filterable) this.mAdapter).getFilter();
                if (f != null) {
                    f.filter(s, this);
                    return;
                }
                throw new IllegalStateException(
                        "You cannot call onTextChanged with a non filterable adapter");
            }
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable s) {}

    @Override // android.widget.Filter.FilterListener
    public void onFilterComplete(int count) {
        if (this.mSelectedPosition < 0 && count > 0) {
            this.mResurrectToPosition = -1;
            resurrectSelection();
        }
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2, 0);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void setTranscriptMode(int mode) {
        this.mTranscriptMode = mode;
    }

    public int getTranscriptMode() {
        return this.mTranscriptMode;
    }

    @Override // android.view.View
    public int getSolidColor() {
        return this.mCacheColorHint;
    }

    public void setCacheColorHint(int color) {
        if (color != this.mCacheColorHint) {
            this.mCacheColorHint = color;
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                getChildAt(i).setDrawingCacheBackgroundColor(color);
            }
            this.mRecycler.setCacheColorHint(color);
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public int getCacheColorHint() {
        return this.mCacheColorHint;
    }

    public void reclaimViews(List<View> views) {
        int childCount = getChildCount();
        RecyclerListener listener = this.mRecycler.mRecyclerListener;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp != null && this.mRecycler.shouldRecycleViewType(lp.viewType)) {
                views.add(child);
                child.setAccessibilityDelegate(null);
                child.resetSubtreeAutofillIds();
                if (listener != null) {
                    listener.onMovedToScrapHeap(child);
                }
            }
        }
        this.mRecycler.reclaimScrapViews(views);
        removeAllViewsInLayout();
    }

    private void finishGlows() {
        if (shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.finish();
            this.mEdgeGlowBottom.finish();
        }
    }

    public void setRemoteViewsAdapter(Intent intent) {
        setRemoteViewsAdapter(intent, false);
    }

    public Runnable setRemoteViewsAdapterAsync(Intent intent) {
        return new RemoteViewsAdapter.AsyncRemoteAdapterAction(this, intent);
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void setRemoteViewsAdapter(Intent intent, boolean isAsync) {
        if (this.mRemoteAdapter != null) {
            Intent.FilterComparison fcNew = new Intent.FilterComparison(intent);
            Intent.FilterComparison fcOld =
                    new Intent.FilterComparison(this.mRemoteAdapter.getRemoteViewsServiceIntent());
            if (fcNew.equals(fcOld)) {
                Log.i(TAG, "Equals previous remoteAadpter");
                return;
            }
        }
        this.mDeferNotifyDataSetChanged = this.mAllowDeferNotifyAfterRemoteViewsAdapterSet;
        if (this.mDeferNotifyDataSetChanged) {
            Log.i(TAG, "AppWidget deferNotify enabled");
        }
        this.mRemoteAdapter = new RemoteViewsAdapter(getContext(), intent, this, isAsync);
        if (this.mRemoteAdapter.isDataReady()) {
            setAdapter((ListAdapter) this.mRemoteAdapter);
        }
    }

    public void setRemoteViewsInteractionHandler(RemoteViews.InteractionHandler handler) {
        if (this.mRemoteAdapter != null) {
            this.mRemoteAdapter.setRemoteViewsInteractionHandler(handler);
        }
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void deferNotifyDataSetChanged() {
        this.mDeferNotifyDataSetChanged = true;
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public boolean onRemoteAdapterConnected() {
        if (this.mRemoteAdapter != this.mAdapter) {
            setAdapter((ListAdapter) this.mRemoteAdapter);
            if (this.mDeferNotifyDataSetChanged) {
                this.mRemoteAdapter.notifyDataSetChanged();
                this.mDeferNotifyDataSetChanged = false;
            }
            if (this.mDeferSetSelectionFromTop) {
                setSelectionFromTop(this.mDeferSetSelectionPosition, 0);
                this.mDeferSetSelectionFromTop = false;
            }
            return false;
        }
        if (this.mRemoteAdapter == null) {
            return false;
        }
        this.mRemoteAdapter.superNotifyDataSetChanged();
        return true;
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void onRemoteAdapterDisconnected() {}

    void setVisibleRangeHint(int start, int end) {
        if (this.mRemoteAdapter != null) {
            this.mRemoteAdapter.setVisibleRangeHint(start, end);
        }
    }

    public void setEdgeEffectColor(int color) {
        setTopEdgeEffectColor(color);
        setBottomEdgeEffectColor(color);
    }

    public void setBottomEdgeEffectColor(int color) {
        this.mEdgeGlowBottom.setColor(color);
        invalidateEdgeEffects();
    }

    public void setTopEdgeEffectColor(int color) {
        this.mEdgeGlowTop.setColor(color);
        invalidateEdgeEffects();
    }

    public int getTopEdgeEffectColor() {
        return this.mEdgeGlowTop.getColor();
    }

    public int getBottomEdgeEffectColor() {
        return this.mEdgeGlowBottom.getColor();
    }

    public void setRecyclerListener(RecyclerListener listener) {
        this.mRecycler.mRecyclerListener = listener;
    }

    @Override // android.view.View
    public void onProvideContentCaptureStructure(ViewStructure structure, int flags) {
        super.onProvideContentCaptureStructure(structure, flags);
        if (!sContentCaptureReportingEnabledByDeviceConfig) {
            return;
        }
        Bundle extras = structure.getExtras();
        if (extras == null) {
            Log.wtf(TAG, "Unexpected null extras Bundle in ViewStructure");
            return;
        }
        int childCount = getChildCount();
        ArrayList<AutofillId> idsList = new ArrayList<>(childCount);
        for (int i = 0; i < childCount; i++) {
            View activeView = getChildAt(i);
            if (activeView != null) {
                idsList.add(activeView.getAutofillId());
            }
        }
        extras.putParcelableArrayList(ViewStructure.EXTRA_ACTIVE_CHILDREN_IDS, idsList);
        extras.putInt(ViewStructure.EXTRA_FIRST_ACTIVE_POSITION, getFirstVisiblePosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportActiveViewsToContentCapture() {
        ContentCaptureSession session;
        if (sContentCaptureReportingEnabledByDeviceConfig
                && (session = getContentCaptureSession()) != null) {
            ViewStructure structure = session.newViewStructure(this);
            onProvideContentCaptureStructure(structure, 0);
            session.notifyViewAppeared(structure);
        }
    }

    class AdapterDataSetObserver extends AdapterView<ListAdapter>.AdapterDataSetObserver {
        AdapterDataSetObserver() {
            super();
        }

        @Override // android.widget.AdapterView.AdapterDataSetObserver,
                  // android.database.DataSetObserver
        public void onChanged() {
            if (AbsListView.this.mAppWidgetSnapScroll
                    && AbsListView.this.mNeedLayoutSpecificDone
                    && AbsListView.this.mLayoutMode == 4
                    && !AbsListView.this.mIsLayoutSpecificDone) {
                AbsListView.this.requestLayout();
            } else {
                super.onChanged();
            }
            if (AbsListView.this.mAppWidgetSnapScroll && AbsListView.this.mNeedLayoutSpecificDone) {
                AbsListView.this.mIsLayoutSpecificDone = true;
            }
            AbsListView.this.mReportChildrenToContentCaptureOnNextUpdate = true;
            if (AbsListView.this.mFastScroll != null) {
                AbsListView.this.mFastScroll.onSectionsChanged();
            } else if (AbsListView.this.mSemFastScroll != null) {
                AbsListView.this.mSemFastScroll.onSectionsChanged();
            }
        }

        @Override // android.widget.AdapterView.AdapterDataSetObserver,
                  // android.database.DataSetObserver
        public void onInvalidated() {
            super.onInvalidated();
            AbsListView.this.mReportChildrenToContentCaptureOnNextUpdate = true;
            if (AbsListView.this.mFastScroll != null) {
                AbsListView.this.mFastScroll.onSectionsChanged();
            } else if (AbsListView.this.mSemFastScroll != null) {
                AbsListView.this.mSemFastScroll.onSectionsChanged();
            }
        }
    }

    class MultiChoiceModeWrapper implements MultiChoiceModeListener {
        private MultiChoiceModeListener mWrapped;

        MultiChoiceModeWrapper() {}

        public void setWrapped(MultiChoiceModeListener wrapped) {
            this.mWrapped = wrapped;
        }

        public boolean hasWrappedCallback() {
            return this.mWrapped != null;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            if (!this.mWrapped.onCreateActionMode(mode, menu)) {
                return false;
            }
            if (!AbsListView.this.mLongPressMultiSelectionEnabled) {
                AbsListView.this.setLongClickable(false);
                return true;
            }
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(mode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return this.mWrapped.onActionItemClicked(mode, item);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode mode) {
            this.mWrapped.onDestroyActionMode(mode);
            AbsListView.this.mChoiceActionMode = null;
            AbsListView.this.clearChoices();
            AbsListView.this.mDataChanged = true;
            AbsListView.this.rememberSyncState();
            AbsListView.this.requestLayout();
            AbsListView.this.setLongClickable(true);
        }

        @Override // android.widget.AbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(
                ActionMode mode, int position, long id, boolean checked) {
            this.mWrapped.onItemCheckedStateChanged(mode, position, id, checked);
            if (AbsListView.this.getCheckedItemCount() == 0
                    && !AbsListView.this.mSemCustomMultiChoiceMode) {
                mode.finish();
            }
        }
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        @ViewDebug.ExportedProperty(category = Slice.HINT_LIST)
        boolean forceAdd;

        boolean isEnabled;
        long itemId;

        @ViewDebug.ExportedProperty(category = Slice.HINT_LIST)
        boolean recycledHeaderFooter;

        int scrappedFromPosition;

        @ViewDebug.ExportedProperty(
                category = Slice.HINT_LIST,
                mapping = {
                    @ViewDebug.IntToString(from = -1, to = "ITEM_VIEW_TYPE_IGNORE"),
                    @ViewDebug.IntToString(from = -2, to = "ITEM_VIEW_TYPE_HEADER_OR_FOOTER")
                })
        int viewType;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.itemId = -1L;
        }

        public LayoutParams(int w, int h) {
            super(w, h);
            this.itemId = -1L;
        }

        public LayoutParams(int w, int h, int viewType) {
            super(w, h);
            this.itemId = -1L;
            this.viewType = viewType;
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            this.itemId = -1L;
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void encodeProperties(ViewHierarchyEncoder encoder) {
            super.encodeProperties(encoder);
            encoder.addProperty("list:viewType", this.viewType);
            encoder.addProperty("list:recycledHeaderFooter", this.recycledHeaderFooter);
            encoder.addProperty("list:forceAdd", this.forceAdd);
            encoder.addProperty("list:isEnabled", this.isEnabled);
        }
    }

    class RecycleBin {
        private View[] mActiveViews = new View[0];
        private ArrayList<View> mCurrentScrap;
        private int mFirstActivePosition;
        private RecyclerListener mRecyclerListener;
        private ArrayList<View>[] mScrapViews;
        private ArrayList<View> mSkippedScrap;
        private SparseArray<View> mTransientStateViews;
        private LongSparseArray<View> mTransientStateViewsById;
        private int mViewTypeCount;

        RecycleBin() {}

        public void setViewTypeCount(int viewTypeCount) {
            if (viewTypeCount < 1) {
                throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
            }
            ArrayList<View>[] scrapViews = new ArrayList[viewTypeCount];
            for (int i = 0; i < viewTypeCount; i++) {
                scrapViews[i] = new ArrayList<>();
            }
            this.mViewTypeCount = viewTypeCount;
            this.mCurrentScrap = scrapViews[0];
            this.mScrapViews = scrapViews;
        }

        public void markChildrenDirty() {
            if (this.mViewTypeCount == 1) {
                ArrayList<View> scrap = this.mCurrentScrap;
                int scrapCount = scrap.size();
                for (int i = 0; i < scrapCount; i++) {
                    scrap.get(i).forceLayout();
                }
            } else {
                int typeCount = this.mViewTypeCount;
                for (int i2 = 0; i2 < typeCount; i2++) {
                    ArrayList<View> scrap2 = this.mScrapViews[i2];
                    int scrapCount2 = scrap2.size();
                    for (int j = 0; j < scrapCount2; j++) {
                        scrap2.get(j).forceLayout();
                    }
                }
            }
            if (this.mTransientStateViews != null) {
                int count = this.mTransientStateViews.size();
                for (int i3 = 0; i3 < count; i3++) {
                    this.mTransientStateViews.valueAt(i3).forceLayout();
                }
            }
            if (this.mTransientStateViewsById != null) {
                int count2 = this.mTransientStateViewsById.size();
                for (int i4 = 0; i4 < count2; i4++) {
                    this.mTransientStateViewsById.valueAt(i4).forceLayout();
                }
            }
        }

        public boolean shouldRecycleViewType(int viewType) {
            return viewType >= 0;
        }

        void clear() {
            if (this.mViewTypeCount == 1) {
                ArrayList<View> scrap = this.mCurrentScrap;
                clearScrap(scrap);
            } else {
                int typeCount = this.mViewTypeCount;
                for (int i = 0; i < typeCount; i++) {
                    ArrayList<View> scrap2 = this.mScrapViews[i];
                    clearScrap(scrap2);
                }
            }
            clearTransientStateViews();
        }

        void fillActiveViews(int childCount, int firstActivePosition) {
            if (this.mActiveViews.length < childCount) {
                this.mActiveViews = new View[childCount];
            }
            this.mFirstActivePosition = firstActivePosition;
            View[] activeViews = this.mActiveViews;
            for (int i = 0; i < childCount; i++) {
                View child = AbsListView.this.getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp != null && lp.viewType != -2) {
                    activeViews[i] = child;
                    lp.scrappedFromPosition = firstActivePosition + i;
                }
            }
            if (AbsListView.this.mReportChildrenToContentCaptureOnNextUpdate && childCount > 0) {
                AbsListView.this.reportActiveViewsToContentCapture();
                AbsListView.this.mReportChildrenToContentCaptureOnNextUpdate = false;
            }
        }

        View getActiveView(int position) {
            int index = position - this.mFirstActivePosition;
            View[] activeViews = this.mActiveViews;
            if (index < 0 || index >= activeViews.length) {
                return null;
            }
            View match = activeViews[index];
            activeViews[index] = null;
            return match;
        }

        View getTransientStateView(int position) {
            int index;
            if (AbsListView.this.mAdapter != null
                    && AbsListView.this.mAdapterHasStableIds
                    && this.mTransientStateViewsById != null) {
                long id = AbsListView.this.mAdapter.getItemId(position);
                View result = this.mTransientStateViewsById.get(id);
                this.mTransientStateViewsById.remove(id);
                return result;
            }
            if (this.mTransientStateViews != null
                    && (index = this.mTransientStateViews.indexOfKey(position)) >= 0) {
                View result2 = this.mTransientStateViews.valueAt(index);
                this.mTransientStateViews.removeAt(index);
                return result2;
            }
            return null;
        }

        void clearTransientStateViews() {
            SparseArray<View> viewsByPos = this.mTransientStateViews;
            if (viewsByPos != null) {
                int N = viewsByPos.size();
                for (int i = 0; i < N; i++) {
                    removeDetachedView(viewsByPos.valueAt(i), false);
                }
                viewsByPos.clear();
            }
            LongSparseArray<View> viewsById = this.mTransientStateViewsById;
            if (viewsById != null) {
                int N2 = viewsById.size();
                for (int i2 = 0; i2 < N2; i2++) {
                    removeDetachedView(viewsById.valueAt(i2), false);
                }
                viewsById.clear();
            }
        }

        View getScrapView(int position) {
            int whichScrap = AbsListView.this.mAdapter.getItemViewType(position);
            if (whichScrap < 0) {
                return null;
            }
            if (this.mViewTypeCount == 1) {
                return retrieveFromScrap(this.mCurrentScrap, position);
            }
            if (whichScrap >= this.mScrapViews.length) {
                return null;
            }
            return retrieveFromScrap(this.mScrapViews[whichScrap], position);
        }

        void addScrapView(View scrap, int position) {
            LayoutParams lp = (LayoutParams) scrap.getLayoutParams();
            if (lp == null) {
                return;
            }
            lp.scrappedFromPosition = position;
            int viewType = lp.viewType;
            if (!shouldRecycleViewType(viewType)) {
                if (viewType != -2) {
                    getSkippedScrap().add(scrap);
                    return;
                }
                return;
            }
            scrap.dispatchStartTemporaryDetach();
            AbsListView.this.notifyViewAccessibilityStateChangedIfNeeded(1);
            boolean scrapHasTransientState = scrap.hasTransientState();
            if (scrapHasTransientState) {
                if (AbsListView.this.mAdapter != null && AbsListView.this.mAdapterHasStableIds) {
                    if (this.mTransientStateViewsById == null) {
                        this.mTransientStateViewsById = new LongSparseArray<>();
                    }
                    this.mTransientStateViewsById.put(lp.itemId, scrap);
                    return;
                } else {
                    if (!AbsListView.this.mDataChanged) {
                        if (this.mTransientStateViews == null) {
                            this.mTransientStateViews = new SparseArray<>();
                        }
                        this.mTransientStateViews.put(position, scrap);
                        return;
                    }
                    getSkippedScrap().add(scrap);
                    return;
                }
            }
            if (this.mViewTypeCount == 1) {
                this.mCurrentScrap.add(scrap);
            } else if (this.mScrapViews.length > viewType
                    && !this.mScrapViews[viewType].contains(scrap)) {
                this.mScrapViews[viewType].add(scrap);
            }
            if (this.mRecyclerListener != null) {
                this.mRecyclerListener.onMovedToScrapHeap(scrap);
            }
        }

        private ArrayList<View> getSkippedScrap() {
            if (this.mSkippedScrap == null) {
                this.mSkippedScrap = new ArrayList<>();
            }
            return this.mSkippedScrap;
        }

        void removeSkippedScrap() {
            if (this.mSkippedScrap == null) {
                return;
            }
            int count = this.mSkippedScrap.size();
            for (int i = 0; i < count; i++) {
                removeDetachedView(this.mSkippedScrap.get(i), false);
            }
            this.mSkippedScrap.clear();
        }

        void scrapActiveViews() {
            View[] activeViews = this.mActiveViews;
            boolean hasListener = this.mRecyclerListener != null;
            boolean multipleScraps = this.mViewTypeCount > 1;
            ArrayList<View> scrapViews = this.mCurrentScrap;
            int count = activeViews.length;
            for (int i = count - 1; i >= 0; i--) {
                View victim = activeViews[i];
                if (victim != null) {
                    LayoutParams lp = (LayoutParams) victim.getLayoutParams();
                    int whichScrap = lp.viewType;
                    activeViews[i] = null;
                    if (victim.hasTransientState()) {
                        victim.dispatchStartTemporaryDetach();
                        if (AbsListView.this.mAdapter != null
                                && AbsListView.this.mAdapterHasStableIds) {
                            if (this.mTransientStateViewsById == null) {
                                this.mTransientStateViewsById = new LongSparseArray<>();
                            }
                            long id =
                                    AbsListView.this.mAdapter.getItemId(
                                            this.mFirstActivePosition + i);
                            this.mTransientStateViewsById.put(id, victim);
                        } else if (!AbsListView.this.mDataChanged) {
                            if (this.mTransientStateViews == null) {
                                this.mTransientStateViews = new SparseArray<>();
                            }
                            this.mTransientStateViews.put(this.mFirstActivePosition + i, victim);
                        } else if (whichScrap != -2) {
                            removeDetachedView(victim, false);
                        }
                    } else if (!shouldRecycleViewType(whichScrap)) {
                        if (whichScrap != -2) {
                            removeDetachedView(victim, false);
                        }
                    } else {
                        if (multipleScraps) {
                            scrapViews = this.mScrapViews[whichScrap];
                        }
                        lp.scrappedFromPosition = this.mFirstActivePosition + i;
                        removeDetachedView(victim, false);
                        scrapViews.add(victim);
                        if (hasListener) {
                            this.mRecyclerListener.onMovedToScrapHeap(victim);
                        }
                    }
                }
            }
            pruneScrapViews();
        }

        void fullyDetachScrapViews() {
            int viewTypeCount = this.mViewTypeCount;
            ArrayList<View>[] scrapViews = this.mScrapViews;
            for (int i = 0; i < viewTypeCount; i++) {
                ArrayList<View> scrapPile = scrapViews[i];
                for (int j = scrapPile.size() - 1; j >= 0; j--) {
                    View view = scrapPile.get(j);
                    if (view.isTemporarilyDetached()) {
                        removeDetachedView(view, false);
                    }
                }
            }
        }

        private void pruneScrapViews() {
            int maxViews = this.mActiveViews.length;
            int viewTypeCount = this.mViewTypeCount;
            ArrayList<View>[] scrapViews = this.mScrapViews;
            for (int i = 0; i < viewTypeCount; i++) {
                ArrayList<View> scrapPile = scrapViews[i];
                int size = scrapPile.size();
                while (size > maxViews) {
                    if (AbsListView.this.mAdapter instanceof RemoteViewsAdapter) {
                        size--;
                        removeDetachedView(scrapPile.remove(size), false);
                    } else {
                        size--;
                        scrapPile.remove(size);
                    }
                }
            }
            SparseArray<View> transViewsByPos = this.mTransientStateViews;
            if (transViewsByPos != null) {
                int i2 = 0;
                while (i2 < transViewsByPos.size()) {
                    View v = transViewsByPos.valueAt(i2);
                    if (!v.hasTransientState()) {
                        removeDetachedView(v, false);
                        transViewsByPos.removeAt(i2);
                        i2--;
                    }
                    i2++;
                }
            }
            LongSparseArray<View> transViewsById = this.mTransientStateViewsById;
            if (transViewsById != null) {
                int i3 = 0;
                while (i3 < transViewsById.size()) {
                    View v2 = transViewsById.valueAt(i3);
                    if (!v2.hasTransientState()) {
                        removeDetachedView(v2, false);
                        transViewsById.removeAt(i3);
                        i3--;
                    }
                    i3++;
                }
            }
        }

        void reclaimScrapViews(List<View> views) {
            if (this.mViewTypeCount == 1) {
                views.addAll(this.mCurrentScrap);
                return;
            }
            int viewTypeCount = this.mViewTypeCount;
            ArrayList<View>[] scrapViews = this.mScrapViews;
            for (int i = 0; i < viewTypeCount; i++) {
                ArrayList<View> scrapPile = scrapViews[i];
                views.addAll(scrapPile);
            }
        }

        void setCacheColorHint(int color) {
            if (this.mViewTypeCount == 1) {
                ArrayList<View> scrap = this.mCurrentScrap;
                int scrapCount = scrap.size();
                for (int i = 0; i < scrapCount; i++) {
                    scrap.get(i).setDrawingCacheBackgroundColor(color);
                }
            } else {
                int typeCount = this.mViewTypeCount;
                for (int i2 = 0; i2 < typeCount; i2++) {
                    ArrayList<View> scrap2 = this.mScrapViews[i2];
                    int scrapCount2 = scrap2.size();
                    for (int j = 0; j < scrapCount2; j++) {
                        scrap2.get(j).setDrawingCacheBackgroundColor(color);
                    }
                }
            }
            View[] activeViews = this.mActiveViews;
            for (View victim : activeViews) {
                if (victim != null) {
                    victim.setDrawingCacheBackgroundColor(color);
                }
            }
        }

        private View retrieveFromScrap(ArrayList<View> scrapViews, int position) {
            int size = scrapViews.size();
            if (size > 0) {
                for (int i = size - 1; i >= 0; i--) {
                    View view = scrapViews.get(i);
                    LayoutParams params = (LayoutParams) view.getLayoutParams();
                    if (AbsListView.this.mAdapterHasStableIds) {
                        long id = AbsListView.this.mAdapter.getItemId(position);
                        if (id == params.itemId) {
                            return scrapViews.remove(i);
                        }
                    } else if (params.scrappedFromPosition == position) {
                        View scrap = scrapViews.remove(i);
                        clearScrapForRebind(scrap);
                        return scrap;
                    }
                }
                int i2 = size - 1;
                View scrap2 = scrapViews.remove(i2);
                clearScrapForRebind(scrap2);
                return scrap2;
            }
            return null;
        }

        private void clearScrap(ArrayList<View> scrap) {
            int scrapCount = scrap.size();
            for (int j = 0; j < scrapCount; j++) {
                removeDetachedView(scrap.remove((scrapCount - 1) - j), false);
            }
        }

        private void clearScrapForRebind(View view) {
            view.clearAccessibilityFocus();
            view.setAccessibilityDelegate(null);
            view.resetSubtreeAutofillIds();
        }

        private void removeDetachedView(View child, boolean animate) {
            if (child != null) {
                child.setAccessibilityDelegate(null);
                child.resetSubtreeAutofillIds();
                AbsListView.this.removeDetachedView(child, animate);
                return;
            }
            Log.d(AbsListView.TAG, "removeDetachedView child is null");
        }
    }

    int getHeightForPosition(int position) {
        int firstVisiblePosition = getFirstVisiblePosition();
        int childCount = getChildCount();
        int index = position - firstVisiblePosition;
        if (index >= 0 && index < childCount) {
            return getChildAt(index).getHeight();
        }
        View view = obtainView(position, this.mIsScrap);
        view.measure(this.mWidthMeasureSpec, 0);
        int height = view.getMeasuredHeight();
        this.mRecycler.addScrapView(view, position);
        return height;
    }

    public void setSelectionFromTop(int position, int y) {
        if (this.mAdapter == null) {
            this.mDeferSetSelectionFromTop = true;
            this.mDeferSetSelectionPosition = position;
            return;
        }
        if (this.mAppWidgetSnapScroll) {
            semSendBroadcastPosition(position, 1);
        }
        if (this.mAppWidgetIndicator) {
            semInvalidateIndicator(position);
        }
        if (this.mSemEnableGoToTop && canScrollUp() && this.mSemGoToTopState != 1) {
            removeCallbacks(this.mSemGoToToFadeOutRunnable);
            semSetupGoToTop(-1);
            semAutoHide(1);
        }
        if (!isInTouchMode()) {
            position = lookForSelectablePosition(position, true);
            if (position >= 0) {
                setNextSelectedPositionInt(position);
            }
        } else {
            this.mResurrectToPosition = position;
        }
        if (position >= 0) {
            this.mLayoutMode = 4;
            this.mSpecificTop = this.mListPadding.top + y;
            if (this.mNeedSync) {
                this.mSyncPosition = position;
                this.mSyncRowId = this.mAdapter.getItemId(position);
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            requestLayout();
            if (this.mAppWidgetSnapScroll && this.mNeedLayoutSpecificDone) {
                this.mIsLayoutSpecificDone = false;
            }
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder encoder) {
        super.encodeProperties(encoder);
        encoder.addProperty("drawing:cacheColorHint", getCacheColorHint());
        encoder.addProperty("list:fastScrollEnabled", isFastScrollEnabled());
        encoder.addProperty("list:scrollingCacheEnabled", isScrollingCacheEnabled());
        encoder.addProperty("list:smoothScrollbarEnabled", isSmoothScrollbarEnabled());
        encoder.addProperty("list:stackFromBottom", isStackFromBottom());
        encoder.addProperty("list:textFilterEnabled", isTextFilterEnabled());
        View selectedView = getSelectedView();
        if (selectedView != null) {
            encoder.addPropertyKey("selectedView");
            selectedView.encode(encoder);
        }
    }

    abstract static class AbsPositionScroller {
        public abstract void start(int i);

        public abstract void start(int i, int i2);

        public abstract void startWithOffset(int i, int i2);

        public abstract void startWithOffset(int i, int i2, int i3);

        public abstract void stop();

        AbsPositionScroller() {}
    }

    class PositionScroller extends AbsPositionScroller implements Runnable {
        private static final int MOVE_DOWN_BOUND = 3;
        private static final int MOVE_DOWN_POS = 1;
        private static final int MOVE_OFFSET = 5;
        private static final int MOVE_UP_BOUND = 4;
        private static final int MOVE_UP_POS = 2;
        private static final int SCROLL_DURATION = 200;
        private int mBoundPos;
        private final int mExtraScroll;
        private int mLastSeenPos;
        private int mMode;
        private int mOffsetFromTop;
        private int mScrollDuration;
        private int mStoredFirstPosition;
        private int mTargetPos;

        PositionScroller() {
            this.mExtraScroll =
                    ViewConfiguration.get(AbsListView.this.mContext).getScaledFadingEdgeLength();
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void start(final int position) {
            int viewTravelCount;
            stop();
            if (AbsListView.this.mDataChanged) {
                AbsListView.this.mPositionScrollAfterLayout =
                        new Runnable() { // from class:
                                         // android.widget.AbsListView.PositionScroller.1
                            @Override // java.lang.Runnable
                            public void run() {
                                PositionScroller.this.start(position);
                            }
                        };
                return;
            }
            int childCount = AbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int firstPos = AbsListView.this.mFirstPosition;
            int lastPos = (firstPos + childCount) - 1;
            int clampedPosition = Math.max(0, Math.min(AbsListView.this.getCount() - 1, position));
            if (clampedPosition < firstPos) {
                viewTravelCount = (firstPos - clampedPosition) + 1;
                this.mMode = 2;
            } else if (clampedPosition > lastPos) {
                viewTravelCount = (clampedPosition - lastPos) + 1;
                this.mMode = 1;
            } else {
                if (AbsListView.this.mJumpScrollToTopState
                        == AbsListView.JUMP_SCROLL_TO_TOP_INITIATED) {
                    AbsListView.this.mJumpScrollToTopState =
                            AbsListView.JUMP_SCROLL_TO_TOP_FINISHING;
                }
                scrollToVisible(clampedPosition, -1, 200);
                return;
            }
            if (viewTravelCount > 0) {
                this.mScrollDuration = 200 / viewTravelCount;
            } else {
                this.mScrollDuration = 200;
            }
            this.mTargetPos = clampedPosition;
            this.mBoundPos = -1;
            this.mLastSeenPos = -1;
            AbsListView.this.postOnAnimation(this);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void start(final int position, final int boundPosition) {
            int viewTravelCount;
            stop();
            if (boundPosition == -1) {
                start(position);
                return;
            }
            if (AbsListView.this.mDataChanged) {
                AbsListView.this.mPositionScrollAfterLayout =
                        new Runnable() { // from class:
                                         // android.widget.AbsListView.PositionScroller.2
                            @Override // java.lang.Runnable
                            public void run() {
                                PositionScroller.this.start(position, boundPosition);
                            }
                        };
                return;
            }
            int childCount = AbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int firstPos = AbsListView.this.mFirstPosition;
            int lastPos = (firstPos + childCount) - 1;
            int clampedPosition = Math.max(0, Math.min(AbsListView.this.getCount() - 1, position));
            if (clampedPosition < firstPos) {
                int boundPosFromLast = lastPos - boundPosition;
                if (boundPosFromLast < 1) {
                    return;
                }
                int posTravel = (firstPos - clampedPosition) + 1;
                int boundTravel = boundPosFromLast - 1;
                if (boundTravel < posTravel) {
                    viewTravelCount = boundTravel;
                    this.mMode = 4;
                } else {
                    viewTravelCount = posTravel;
                    this.mMode = 2;
                }
            } else if (clampedPosition > lastPos) {
                int boundPosFromFirst = boundPosition - firstPos;
                if (boundPosFromFirst < 1) {
                    return;
                }
                int posTravel2 = (clampedPosition - lastPos) + 1;
                viewTravelCount = boundPosFromFirst - 1;
                if (viewTravelCount < posTravel2) {
                    this.mMode = 3;
                } else {
                    this.mMode = 1;
                    viewTravelCount = posTravel2;
                }
            } else {
                scrollToVisible(clampedPosition, boundPosition, 200);
                return;
            }
            if (viewTravelCount > 0) {
                this.mScrollDuration = 200 / viewTravelCount;
            } else {
                this.mScrollDuration = 200;
            }
            this.mTargetPos = clampedPosition;
            this.mBoundPos = boundPosition;
            this.mLastSeenPos = -1;
            AbsListView.this.postOnAnimation(this);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void startWithOffset(int position, int offset) {
            startWithOffset(position, offset, 200);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void startWithOffset(final int position, final int offset, final int duration) {
            int viewTravelCount;
            stop();
            if (AbsListView.this.mDataChanged) {
                AbsListView.this.mPositionScrollAfterLayout =
                        new Runnable() { // from class:
                                         // android.widget.AbsListView.PositionScroller.3
                            @Override // java.lang.Runnable
                            public void run() {
                                PositionScroller.this.startWithOffset(position, offset, duration);
                            }
                        };
                return;
            }
            int childCount = AbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int offset2 = offset + AbsListView.this.getPaddingTop();
            this.mTargetPos = Math.max(0, Math.min(AbsListView.this.getCount() - 1, position));
            this.mOffsetFromTop = offset2;
            this.mBoundPos = -1;
            this.mLastSeenPos = -1;
            this.mMode = 5;
            int firstPos = AbsListView.this.mFirstPosition;
            int lastPos = (firstPos + childCount) - 1;
            if (this.mTargetPos < firstPos) {
                viewTravelCount = firstPos - this.mTargetPos;
            } else if (this.mTargetPos > lastPos) {
                viewTravelCount = this.mTargetPos - lastPos;
            } else {
                int targetTop = AbsListView.this.getChildAt(this.mTargetPos - firstPos).getTop();
                AbsListView.this.smoothScrollBy(targetTop - offset2, duration, true, false);
                return;
            }
            float screenTravelCount = viewTravelCount / childCount;
            this.mScrollDuration =
                    screenTravelCount < 1.0f ? duration : (int) (duration / screenTravelCount);
            this.mLastSeenPos = -1;
            AbsListView.this.postOnAnimation(this);
        }

        private void scrollToVisible(int targetPos, int boundPos, int duration) {
            int boundPos2 = boundPos;
            int firstPos = AbsListView.this.mFirstPosition;
            int childCount = AbsListView.this.getChildCount();
            int lastPos = (firstPos + childCount) - 1;
            int paddedTop = AbsListView.this.mListPadding.top;
            int paddedBottom = AbsListView.this.getHeight() - AbsListView.this.mListPadding.bottom;
            if (targetPos < firstPos || targetPos > lastPos) {
                Log.w(
                        AbsListView.TAG,
                        "scrollToVisible called with targetPos "
                                + targetPos
                                + " not visible ["
                                + firstPos
                                + ", "
                                + lastPos
                                + NavigationBarInflaterView.SIZE_MOD_END);
            }
            if (boundPos2 < firstPos || boundPos2 > lastPos) {
                boundPos2 = -1;
            }
            View targetChild = AbsListView.this.getChildAt(targetPos - firstPos);
            int targetTop = targetChild.getTop();
            int targetBottom = targetChild.getBottom();
            int scrollBy = 0;
            if (targetBottom > paddedBottom) {
                scrollBy = targetBottom - paddedBottom;
            }
            if (targetTop < paddedTop) {
                scrollBy = targetTop - paddedTop;
            }
            if (scrollBy == 0) {
                if (AbsListView.this.mJumpScrollToTopState
                        == AbsListView.JUMP_SCROLL_TO_TOP_FINISHING) {
                    AbsListView.this.mJumpScrollToTopState = AbsListView.JUMP_SCROLL_TO_TOP_IDLE;
                    AbsListView.this.postOnJumpScrollToFinished();
                    return;
                }
                return;
            }
            if (boundPos2 >= 0) {
                View boundChild = AbsListView.this.getChildAt(boundPos2 - firstPos);
                int boundTop = boundChild.getTop();
                int boundBottom = boundChild.getBottom();
                int absScroll = Math.abs(scrollBy);
                if (scrollBy < 0 && boundBottom + absScroll > paddedBottom) {
                    scrollBy = Math.max(0, boundBottom - paddedBottom);
                } else if (scrollBy > 0 && boundTop - absScroll < paddedTop) {
                    scrollBy = Math.min(0, boundTop - paddedTop);
                }
            }
            AbsListView.this.smoothScrollBy(scrollBy, duration);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void stop() {
            AbsListView.this.removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            int dividerHeight;
            int i;
            int listHeight = AbsListView.this.getHeight();
            int firstPos = AbsListView.this.mFirstPosition;
            switch (this.mMode) {
                case 1:
                    boolean z = false;
                    int lastViewIndex = AbsListView.this.getChildCount() - 1;
                    int lastPos = firstPos + lastViewIndex;
                    if (lastViewIndex >= 0) {
                        if (lastPos == this.mLastSeenPos) {
                            AbsListView.this.postOnAnimation(this);
                            break;
                        } else {
                            View lastView = AbsListView.this.getChildAt(lastViewIndex);
                            int lastViewHeight = lastView.getHeight();
                            int lastViewPixelsShowing = listHeight - lastView.getTop();
                            if (AbsListView.this.mHasDivier) {
                                dividerHeight = AbsListView.this.mHasDividerHeight;
                            } else {
                                dividerHeight = 0;
                            }
                            int extraScroll =
                                    lastPos < AbsListView.this.mItemCount - 1
                                            ? Math.max(
                                                    AbsListView.this.mListPadding.bottom
                                                            + dividerHeight
                                                            + 1,
                                                    this.mExtraScroll)
                                            : AbsListView.this.mListPadding.bottom;
                            int scrollBy = (lastViewHeight - lastViewPixelsShowing) + extraScroll;
                            AbsListView absListView = AbsListView.this;
                            int i2 = this.mScrollDuration;
                            if (lastPos < this.mTargetPos) {
                                z = true;
                            }
                            absListView.smoothScrollBy(scrollBy, i2, true, z);
                            this.mLastSeenPos = lastPos;
                            if (lastPos < this.mTargetPos) {
                                AbsListView.this.postOnAnimation(this);
                                break;
                            }
                        }
                    }
                    break;
                case 2:
                    int nextViewIndex = this.mLastSeenPos;
                    if (firstPos == nextViewIndex) {
                        if (AbsListView.this.semIsSupportGotoTop()
                                && AbsListView.this.mSemCanGoFuther
                                && AbsListView.this.mFirstPosition > 0
                                && this.mStoredFirstPosition == firstPos) {
                            AbsListView.this.smoothScrollToPositionFromTop(0, 0, 0);
                        }
                        this.mStoredFirstPosition = firstPos;
                        AbsListView.this.postOnAnimation(this);
                        break;
                    } else {
                        View firstView = AbsListView.this.getChildAt(0);
                        if (firstView != null) {
                            int firstViewTop = firstView.getTop();
                            int dividerHeight2 =
                                    AbsListView.this.mHasDivier
                                            ? AbsListView.this.mHasDividerHeight
                                            : 0;
                            int extraScroll2 =
                                    firstPos > 0
                                            ? Math.max(
                                                    AbsListView.this.mListPadding.top
                                                            + dividerHeight2
                                                            + 1,
                                                    this.mExtraScroll)
                                            : AbsListView.this.mListPadding.top;
                            AbsListView.this.smoothScrollBy(
                                    firstViewTop - extraScroll2,
                                    this.mScrollDuration,
                                    true,
                                    firstPos > this.mTargetPos);
                            this.mLastSeenPos = firstPos;
                            if (firstPos > this.mTargetPos) {
                                AbsListView.this.postOnAnimation(this);
                                break;
                            } else if (AbsListView.this.mJumpScrollToTopState
                                    == AbsListView.JUMP_SCROLL_TO_TOP_INITIATED) {
                                AbsListView.this.mJumpScrollToTopState =
                                        AbsListView.JUMP_SCROLL_TO_TOP_FINISHING;
                                break;
                            }
                        }
                    }
                    break;
                case 3:
                    int childCount = AbsListView.this.getChildCount();
                    if (firstPos != this.mBoundPos && childCount > 1) {
                        if (firstPos + childCount < AbsListView.this.mItemCount) {
                            int nextPos = firstPos + 1;
                            if (nextPos == this.mLastSeenPos) {
                                AbsListView.this.postOnAnimation(this);
                                break;
                            } else {
                                View nextView = AbsListView.this.getChildAt(1);
                                int nextViewHeight = nextView.getHeight();
                                int nextViewTop = nextView.getTop();
                                int extraScroll3 =
                                        Math.max(
                                                AbsListView.this.mListPadding.bottom,
                                                this.mExtraScroll);
                                if (nextPos < this.mBoundPos) {
                                    AbsListView.this.smoothScrollBy(
                                            Math.max(
                                                    0,
                                                    (nextViewHeight + nextViewTop) - extraScroll3),
                                            this.mScrollDuration,
                                            true,
                                            true);
                                    this.mLastSeenPos = nextPos;
                                    AbsListView.this.postOnAnimation(this);
                                    break;
                                } else if (nextViewTop > extraScroll3) {
                                    AbsListView.this.smoothScrollBy(
                                            nextViewTop - extraScroll3,
                                            this.mScrollDuration,
                                            true,
                                            false);
                                    break;
                                } else {
                                    AbsListView.this.reportScrollStateChange(0);
                                    break;
                                }
                            }
                        } else {
                            i = 0;
                        }
                    } else {
                        i = 0;
                    }
                    AbsListView.this.reportScrollStateChange(i);
                    break;
                case 4:
                    int lastViewIndex2 = AbsListView.this.getChildCount() - 2;
                    if (lastViewIndex2 >= 0) {
                        int lastPos2 = firstPos + lastViewIndex2;
                        if (lastPos2 == this.mLastSeenPos) {
                            AbsListView.this.postOnAnimation(this);
                            break;
                        } else {
                            View lastView2 = AbsListView.this.getChildAt(lastViewIndex2);
                            int lastViewHeight2 = lastView2.getHeight();
                            int lastViewTop = lastView2.getTop();
                            int lastViewPixelsShowing2 = listHeight - lastViewTop;
                            int extraScroll4 =
                                    Math.max(AbsListView.this.mListPadding.top, this.mExtraScroll);
                            this.mLastSeenPos = lastPos2;
                            if (lastPos2 > this.mBoundPos) {
                                AbsListView.this.smoothScrollBy(
                                        -(lastViewPixelsShowing2 - extraScroll4),
                                        this.mScrollDuration,
                                        true,
                                        true);
                                AbsListView.this.postOnAnimation(this);
                                break;
                            } else {
                                int bottom = listHeight - extraScroll4;
                                int lastViewBottom = lastViewTop + lastViewHeight2;
                                if (bottom > lastViewBottom) {
                                    AbsListView.this.smoothScrollBy(
                                            -(bottom - lastViewBottom),
                                            this.mScrollDuration,
                                            true,
                                            false);
                                    break;
                                } else {
                                    AbsListView.this.reportScrollStateChange(0);
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case 5:
                    this.mLastSeenPos = firstPos;
                    int childCount2 = AbsListView.this.getChildCount();
                    if (childCount2 > 0) {
                        int position = this.mTargetPos;
                        int lastPos3 = (firstPos + childCount2) - 1;
                        View firstChild = AbsListView.this.getChildAt(0);
                        int firstChildHeight = firstChild.getHeight();
                        View lastChild = AbsListView.this.getChildAt(childCount2 - 1);
                        int lastChildHeight = lastChild.getHeight();
                        float firstPositionVisiblePart =
                                ((float) firstChildHeight) == 0.0f
                                        ? 1.0f
                                        : (firstChild.getTop() + firstChildHeight)
                                                / firstChildHeight;
                        float lastPositionVisiblePart =
                                ((float) lastChildHeight) == 0.0f
                                        ? 1.0f
                                        : ((AbsListView.this.getHeight() + lastChildHeight)
                                                        - lastChild.getBottom())
                                                / lastChildHeight;
                        float viewTravelCount = 0.0f;
                        if (position < firstPos) {
                            viewTravelCount =
                                    (firstPos - position)
                                            + (1.0f - firstPositionVisiblePart)
                                            + 1.0f;
                        } else if (position > lastPos3) {
                            viewTravelCount =
                                    (position - lastPos3) + (1.0f - lastPositionVisiblePart);
                        }
                        float screenTravelCount = viewTravelCount / childCount2;
                        float modifier = Math.min(Math.abs(screenTravelCount), 1.0f);
                        if (position >= firstPos) {
                            if (position > lastPos3) {
                                int duration = (int) (this.mScrollDuration * modifier);
                                AbsListView.this.smoothScrollBy(
                                        (int) (AbsListView.this.getHeight() * modifier),
                                        duration,
                                        true,
                                        true);
                                AbsListView.this.postOnAnimation(this);
                                break;
                            } else {
                                int targetTop =
                                        AbsListView.this.getChildAt(position - firstPos).getTop();
                                int distance = targetTop - this.mOffsetFromTop;
                                int duration2 =
                                        (int)
                                                (this.mScrollDuration
                                                        * (Math.abs(distance)
                                                                / AbsListView.this.getHeight()));
                                AbsListView.this.smoothScrollBy(distance, duration2, true, false);
                                break;
                            }
                        } else {
                            int duration3 = (int) (this.mScrollDuration * modifier);
                            AbsListView.this.smoothScrollBy(
                                    (int) ((-AbsListView.this.getHeight()) * modifier),
                                    duration3,
                                    true,
                                    true);
                            AbsListView.this.postOnAnimation(this);
                            break;
                        }
                    }
                    break;
            }
        }
    }

    public void setOnScrollOffsetListener(OnScrollOffsetListener scrollOffsetListener) {
        this.mOnScrollOffsetListener = scrollOffsetListener;
    }

    static class ClickableViewState {
        private boolean mIsFocused;
        private final View mView;
        private boolean mWasFocused;

        public ClickableViewState(View view, boolean wasFocused) {
            this.mView = view;
            this.mWasFocused = wasFocused;
        }

        void setWasFocused(boolean wasFocused) {
            this.mWasFocused = wasFocused;
        }

        boolean getWasFocused() {
            return this.mWasFocused;
        }

        void setIsFocused(boolean isFocused) {
            this.mIsFocused = isFocused;
        }

        boolean getIsFocused() {
            return this.mIsFocused;
        }

        View getView() {
            return this.mView;
        }
    }

    @Deprecated
    public void semStartMultiChoiceMode() {
        if (this.mChoiceMode == 3 && this.mMultiChoiceModeCallback != null) {
            this.mChoiceActionMode = startActionMode(this.mMultiChoiceModeCallback);
        }
    }

    @Deprecated
    public void semFinishMultiChoiceMode() {
        if (this.mChoiceActionMode != null) {
            this.mChoiceActionMode.finish();
            this.mChoiceActionMode = null;
        }
    }

    public void semSetCustomMultiChoiceModeEnabled(boolean enabled) {
        this.mSemCustomMultiChoiceMode = enabled;
    }

    private void onHoverDrawableState(MotionEvent event) {
        int action = event.getAction();
        int toolType = event.getToolType(0);
        int flags = event.getFlags();
        if ((action == 7 || action == 9) && toolType == 2) {
            this.mIsPenHovered = true;
        } else if (action == 10) {
            this.mIsPenHovered = false;
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        this.mIsHoveredByMouse =
                viewRootImpl != null
                        && viewRootImpl.isDesktopMode()
                        && (toolType == 3 || (67108864 & flags) != 0);
        if (this.mAdapter != null
                && this.mSelector != null
                && this.mSelector.isStateful()
                && !this.mHoverAreaEnter
                && this.mIsHoveredByMouse) {
            if (!isInTouchMode()) {
                hideSelector();
                updateSelectorState();
            }
            if (!isHovered() && this.mIsHoverScrolled) {
                setHovered(true);
                this.mIsHoverScrolled = false;
            }
            int x = (int) event.getX();
            int y = (int) event.getY();
            int newHoverPosition = pointToPosition(x, y);
            boolean shouldShowSelector = shouldShowSelector();
            if (this.mSemGoToTopState != 0
                    && semIsSupportGotoTop()
                    && this.mSemGoToTopRect.contains(x, y)) {
                this.mSelectorPosition = -1;
                this.mSelectorRect.setEmpty();
                return;
            }
            if (!isHovered() && !isInTouchMode()) {
                this.mSelector.setState(StateSet.NOTHING);
            }
            if (newHoverPosition < 0) {
                if (!shouldShowSelector) {
                    this.mSelectorRect.setEmpty();
                }
                if (this.mHoveredOnEllipsizedText) {
                    this.mSelector.setState(StateSet.NOTHING);
                    postInvalidateOnAnimation();
                    this.mHoveredOnEllipsizedText = false;
                }
                this.mHoverPosition = -1;
                return;
            }
            this.mHoverPosition = newHoverPosition;
            View child = getChildAt(this.mHoverPosition - this.mFirstPosition);
            if (this.mAdapter.isEnabled(this.mHoverPosition) && this.mIsHoveredByMouse) {
                positionSelector(this.mHoverPosition, child);
                this.mHoveredOnEllipsizedText = true;
                updateSelectorState();
            } else {
                this.mSelectorRect.setEmpty();
                this.mSelector.setState(StateSet.NOTHING);
                this.mHoveredOnEllipsizedText = false;
            }
            if (action == 10) {
                this.mHoveredOnEllipsizedText = false;
                this.mHoverPosition = -1;
                this.mSelector.setState(StateSet.NOTHING);
                this.mSelectorRect.setEmpty();
                postInvalidateOnAnimation();
            }
        }
    }

    private static class HoverScrollHandler extends Handler {
        private final WeakReference<AbsListView> mListView;

        HoverScrollHandler(AbsListView sv) {
            this.mListView = new WeakReference<>(sv);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            AbsListView sv = this.mListView.get();
            if (sv != null) {
                sv.handleMessage(msg);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessage(Message msg) {
        int offset;
        switch (msg.what) {
            case 1:
                long hoverRecognitionCurrentTime = System.currentTimeMillis();
                long hoverRecognitionDurationTime =
                        (hoverRecognitionCurrentTime - this.mHoverRecognitionStartTime) / 1000;
                if (!this.mIsPenHovered
                        || hoverRecognitionCurrentTime - this.mHoverScrollStartTime
                                >= this.mHoverScrollTimeInterval) {
                    if (!this.mIsPenPressed
                            || hoverRecognitionCurrentTime - this.mHoverScrollStartTime
                                    >= this.mPenDragScrollTimeInterval) {
                        if (this.mIsPenHovered && !this.mIsSendHoverScrollState) {
                            if (this.mOnScrollListener != null) {
                                this.mHoverScrollStateForListener = 1;
                                this.mOnScrollListener.onScrollStateChanged(this, 1);
                            }
                            this.mIsSendHoverScrollState = true;
                            this.mIsHoverScrolled = true;
                        }
                        int count = getChildCount();
                        boolean canOverScroll = false;
                        boolean canScrollDown = this.mFirstPosition + count < this.mItemCount;
                        if (!canScrollDown && count > 0) {
                            View child = getChildAt(count - 1);
                            canScrollDown =
                                    child.getBottom() > this.mBottom - this.mListPadding.bottom
                                            || child.getBottom()
                                                    > getHeight() - this.mListPadding.bottom;
                        }
                        boolean canScrollUp = this.mFirstPosition > 0;
                        if (!canScrollUp && getChildCount() > 0) {
                            canScrollUp = getChildAt(0).getTop() < this.mListPadding.top;
                        }
                        int mHoverScrollSpeed =
                                (int)
                                        (TypedValue.applyDimension(
                                                        1,
                                                        HOVERSCROLL_SPEED,
                                                        this.mContext
                                                                .getResources()
                                                                .getDisplayMetrics())
                                                + 0.5f);
                        if (hoverRecognitionDurationTime == 3) {
                            mHoverScrollSpeed += (int) (mHoverScrollSpeed * 0.1d);
                        } else if (hoverRecognitionDurationTime == 4) {
                            mHoverScrollSpeed += (int) (mHoverScrollSpeed * 0.2d);
                        } else if (hoverRecognitionDurationTime >= 5) {
                            mHoverScrollSpeed += (int) (mHoverScrollSpeed * 0.3d);
                        }
                        if (this.mHoverScrollDirection == 2) {
                            offset = mHoverScrollSpeed * (-1);
                            if ((this.mSemTrackedChild == null
                                            && this.mSemCloseChildByBottom != null)
                                    || (this.mOldHoverScrollDirection != this.mHoverScrollDirection
                                            && this.mIsCloseChildSetted)) {
                                this.mSemTrackedChild = this.mSemCloseChildByBottom;
                                this.mSemDistanceFromTrackedChildTop =
                                        this.mSemDistanceFromCloseChildBottom;
                                this.mSemTrackedChildPosition = this.mSemCloseChildPositionByBottom;
                                this.mOldHoverScrollDirection = this.mHoverScrollDirection;
                                this.mIsCloseChildSetted = true;
                            }
                        } else {
                            offset = mHoverScrollSpeed;
                            if ((this.mSemTrackedChild == null && this.mSemCloseChildByTop != null)
                                    || (this.mOldHoverScrollDirection != this.mHoverScrollDirection
                                            && this.mIsCloseChildSetted)) {
                                this.mSemTrackedChild = this.mSemCloseChildByTop;
                                this.mSemDistanceFromTrackedChildTop =
                                        this.mSemDistanceFromCloseChildTop;
                                this.mSemTrackedChildPosition = this.mSemCloseChildPositionByTop;
                                this.mOldHoverScrollDirection = this.mHoverScrollDirection;
                                this.mIsCloseChildSetted = true;
                            }
                        }
                        if (getChildAt(getChildCount() - 1) != null) {
                            if ((offset < 0 && canScrollUp) || (offset > 0 && canScrollDown)) {
                                smoothScrollBy(offset, 0);
                                if (this.mIsLongPressMultiSelection) {
                                    updateLongPressMultiSelection(
                                            this.mSemDragEndX, this.mSemDragEndY, false);
                                }
                                this.mHoverHandler.sendEmptyMessageDelayed(1, 0L);
                                break;
                            } else {
                                int overScrollMode = getOverScrollMode();
                                if (overScrollMode == 0
                                        || (overScrollMode == 1 && !contentFits())) {
                                    canOverScroll = true;
                                }
                                if (canOverScroll && !this.mIsHoverOverscrolled) {
                                    if (this.mHoverScrollDirection == 2) {
                                        this.mEdgeGlowTop.setSize(getWidth(), getHeight());
                                        this.mEdgeGlowTop.onAbsorb(10000);
                                        if (!this.mEdgeGlowBottom.isFinished()) {
                                            this.mEdgeGlowBottom.onRelease();
                                        }
                                    } else if (this.mHoverScrollDirection == 1) {
                                        this.mEdgeGlowBottom.setSize(getWidth(), getHeight());
                                        this.mEdgeGlowBottom.onAbsorb(10000);
                                        semSetupGoToTop(1);
                                        semAutoHide(1);
                                        if (!this.mEdgeGlowTop.isFinished()) {
                                            this.mEdgeGlowTop.onRelease();
                                        }
                                    }
                                    if (!this.mEdgeGlowTop.isFinished()
                                            || !this.mEdgeGlowBottom.isFinished()) {
                                        invalidate();
                                    }
                                    this.mIsHoverOverscrolled = true;
                                }
                                if (!canOverScroll && !this.mIsHoverOverscrolled) {
                                    this.mIsHoverOverscrolled = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showPointerIcon(MotionEvent ev, int iconId) {
        semSetPointerIcon(ev.getToolType(0), PointerIcon.getSystemIcon(this.mContext, iconId));
    }

    public void semSetHoverScrollEnabled(boolean enabled) {
        this.mHoverScrollEnable = enabled;
        this.mHoverScrollStateChanged = true;
    }

    public void setEnablePaddingInHoverScroll(boolean enable) {
        this.mIsEnabledPaddingInHoverScroll = enable;
    }

    public void addExtraPaddingInTopHoverArea(int extraSpace) {
        this.mExtraPaddingInTopHoverArea =
                (int)
                        (TypedValue.applyDimension(
                                        1,
                                        extraSpace,
                                        this.mContext.getResources().getDisplayMetrics())
                                + 0.5f);
    }

    public void addExtraPaddingInBottomHoverArea(int extraSpace) {
        this.mExtraPaddingInBottomHoverArea =
                (int)
                        (TypedValue.applyDimension(
                                        1,
                                        extraSpace,
                                        this.mContext.getResources().getDisplayMetrics())
                                + 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addToPressItemListArray(int firstpoint, int secondpoint) {
        if (!this.mIsMultiFocusEnabled) {
            return;
        }
        if (secondpoint == -1) {
            if (this.mSemPressItemListArray.contains(Integer.valueOf(firstpoint))) {
                this.mSemPressItemListArray.remove(Integer.valueOf(firstpoint));
            } else {
                this.mSemPressItemListArray.add(Integer.valueOf(firstpoint));
            }
        } else if (firstpoint < secondpoint) {
            int checkCount = (secondpoint - firstpoint) + 1;
            for (int i = 0; i < checkCount; i++) {
                if (this.mSemPressItemListArray.contains(Integer.valueOf(firstpoint))) {
                    this.mSemPressItemListArray.remove(Integer.valueOf(firstpoint));
                } else {
                    this.mSemPressItemListArray.add(Integer.valueOf(firstpoint));
                }
                firstpoint++;
            }
        } else if (firstpoint > secondpoint) {
            int checkCount2 = (firstpoint - secondpoint) + 1;
            for (int i2 = 0; i2 < checkCount2; i2++) {
                if (this.mSemPressItemListArray.contains(Integer.valueOf(firstpoint))) {
                    this.mSemPressItemListArray.remove(Integer.valueOf(firstpoint));
                } else {
                    this.mSemPressItemListArray.add(Integer.valueOf(firstpoint));
                }
                firstpoint--;
            }
        } else if (this.mSemPressItemListArray.contains(Integer.valueOf(firstpoint))) {
            this.mSemPressItemListArray.remove(Integer.valueOf(firstpoint));
        } else {
            this.mSemPressItemListArray.add(Integer.valueOf(firstpoint));
        }
        invalidate();
    }

    public void resetPressItemListArray() {
        if (this.mAdapter == null || this.mSemPressItemListArray == null) {
            return;
        }
        this.mSemPressItemListArray.clear();
        invalidate();
    }

    public void setMultiFocusListItem(int startItem, int endItem) {
        if (this.mSemPressItemListArray == null) {
            return;
        }
        resetPressItemListArray();
        addToPressItemListArray(startItem, endItem);
    }

    public void semSetMultiFocusEnabled(boolean enabled) {
        this.mIsMultiFocusEnabled = enabled;
    }

    public boolean isMultiFocusEnabled() {
        return this.mIsMultiFocusEnabled;
    }

    private void initGoToTOP() {
        this.mSemGoToTopRect = new Rect();
        if (this.mSemGoToTopState != 0) {
            this.mSemGoToTopImage.setBounds(0, 0, 0, 0);
        }
        this.mSemGoToTopState = 0;
        this.mSemGoToTopLastState = 0;
        this.mShowGTPAtFirstTime = false;
        this.mShowFadeOutGTP = 0;
        removeCallbacks(this.mSemAutoHide);
        removeCallbacks(this.mSemGoToToFadeInRunnable);
        removeCallbacks(this.mSemGoToToFadeOutRunnable);
    }

    public void semSetupGoToTop(int where) {
        int i;
        int gapH;
        int overlappedW;
        if (!semIsTalkBackIsRunning() && this.mSemEnableGoToTop) {
            removeCallbacks(this.mSemAutoHide);
            int where2 = where;
            if (where2 == 1 && !canScrollUp()) {
                where2 = 0;
            }
            if (where2 == -1 && this.mSemSizeChnage) {
                if (!canScrollUp() && !canScrollDown()) {
                    where2 = 0;
                } else {
                    where2 = this.mSemGoToTopLastState;
                }
            } else if (where2 == -1 && (canScrollUp() || canScrollDown())) {
                where2 = 1;
            }
            if (where2 != 0) {
                removeCallbacks(this.mSemGoToToFadeOutRunnable);
            } else if (where2 != 1) {
                removeCallbacks(this.mSemGoToToFadeInRunnable);
            }
            if (this.mShowFadeOutGTP == 0 && where2 == 0 && this.mSemGoToTopLastState != 0) {
                post(this.mSemGoToToFadeOutRunnable);
            }
            if (where2 != 2) {
                this.mSemGoToTopImage.setState(StateSet.NOTHING);
            }
            this.mSemGoToTopState = where2;
            int w = getWidth();
            int h = getHeight();
            int contentW = (w - this.mPaddingLeft) - this.mPaddingRight;
            int centerX = this.mPaddingLeft + (contentW / 2);
            int[] locOnScr = {0, 0};
            getLocationInWindow(locOnScr);
            DisplayMetrics dm = getResources().getDisplayMetrics();
            Display display =
                    ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE))
                            .getDefaultDisplay();
            int rotate = display.getRotation();
            boolean isLandScape = rotate == 1 || rotate == 3;
            Rect displayFrame = new Rect();
            getWindowVisibleDisplayFrame(displayFrame);
            int left = isLandScape ? displayFrame.left : 0;
            int right = isLandScape ? displayFrame.right : dm.widthPixels;
            int contentW2 = locOnScr[0];
            if (contentW2 < left && !this.mAppWidgetGoToTop) {
                int overlappedW2 = -locOnScr[0];
                int left2 = this.mPaddingLeft;
                if (overlappedW2 > left2) {
                    centerX += (overlappedW2 - this.mPaddingLeft) / 2;
                }
            }
            if (locOnScr[0] + w > right
                    && !this.mAppWidgetGoToTop
                    && (overlappedW = (locOnScr[0] + w) - dm.widthPixels) > this.mPaddingRight) {
                centerX -= (overlappedW - this.mPaddingRight) / 2;
            }
            switch (where2) {
                case 0:
                    int right2 = this.mShowFadeOutGTP;
                    if (right2 != 2) {
                        i = 0;
                        break;
                    } else {
                        i = 0;
                        this.mSemGoToTopRect.set(0, 0, 0, 0);
                        break;
                    }
                case 1:
                case 2:
                    removeCallbacks(this.mSemGoToToFadeOutRunnable);
                    int btnW =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.sem_go_to_top_scrollableview_size);
                    int btnH =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.sem_go_to_top_scrollableview_size);
                    if (this.mAppWidgetGoToTop) {
                        int gapH2 =
                                this.mContext
                                        .getResources()
                                        .getDimensionPixelSize(
                                                R.dimen.sem_go_to_top_scrollableview_gap_appwidget);
                        gapH = gapH2 + this.mAppWidgetGoToTopOffset;
                    } else {
                        gapH =
                                this.mContext
                                        .getResources()
                                        .getDimensionPixelSize(
                                                R.dimen.sem_go_to_top_scrollableview_gap);
                    }
                    int right3 = ((h - btnH) - 0) - gapH;
                    this.mSemGoToTopRect.set(
                            centerX - (btnW / 2), right3, centerX + (btnW / 2), (h - 0) - gapH);
                    i = 0;
                    break;
                default:
                    i = 0;
                    break;
            }
            if (this.mShowFadeOutGTP == 2) {
                this.mShowFadeOutGTP = i;
            }
            this.mSemGoToTopImage.setBounds(this.mSemGoToTopRect);
            if (where2 == 1
                    && (this.mSemGoToTopLastState == 0
                            || this.mSemGoToTopImage.getAlpha() == 0
                            || this.mSemSizeChnage)) {
                post(this.mSemGoToToFadeInRunnable);
            }
            this.mSemSizeChnage = false;
            this.mSemGoToTopLastState = this.mSemGoToTopState;
            this.mOutline.setOval(
                    0, 0, this.mSemGoToTopRect.width(), this.mSemGoToTopRect.height());
            this.mGoToTopRenderNode.setPosition(this.mSemGoToTopRect);
            this.mGoToTopRenderNode.setClipToBounds(false);
        }
    }

    private void drawGoToTop(Canvas canvas) {
        int scrollY = this.mScrollY;
        int restoreCount = canvas.save();
        canvas.translate(0.0f, scrollY);
        if (!canScrollUp() && this.mSemGoToTopState != 0) {
            semSetupGoToTop(0);
        }
        if (!this.mSemGoToTopRect.isEmpty()) {
            if (canvas.isHardwareAccelerated()) {
                canvas.enableZ();
                float alpha = this.mSemGoToTopImage.getAlpha() / 255.0f;
                RecordingCanvas recordingCanvas = this.mGoToTopRenderNode.beginRecording();
                this.mOutline.setAlpha(alpha);
                this.mGoToTopRenderNode.setOutline(this.mOutline);
                this.mGoToTopRenderNode.setAlpha(alpha);
                recordingCanvas.drawBitmap(this.mSemGoToTopBitmap, 0.0f, 0.0f, (Paint) null);
                canvas.drawRenderNode(this.mGoToTopRenderNode);
                this.mGoToTopRenderNode.endRecording();
                canvas.disableZ();
            } else {
                this.mSemGoToTopImage.draw(canvas);
            }
        }
        canvas.restoreToCount(restoreCount);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap =
                Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void semSetGoToTopEnabledForAppWidget(boolean enabled) {
        this.mAppWidgetGoToTop = enabled;
        semSetGoToTopEnabled(enabled, 1);
    }

    public void semAllowDeferNotifyAfterRemoteViewsAdapterSet(boolean update) {
        Log.i(TAG, "Allow notify after RemoteViewsAdapter set " + update);
        this.mAllowDeferNotifyAfterRemoteViewsAdapterSet = update;
    }

    public void semSetGoToTopOffsetForAppWidget(int offset) {
        this.mAppWidgetGoToTopOffset = offset;
    }

    public void semSetGoToTopEnabled(boolean enabled) {
        semSetGoToTopEnabled(enabled, 1);
    }

    public void semSetGoToTopEnabled(boolean enabled, int buttonStyle) {
        if (this.mAppWidgetGoToTop) {
            boolean isNight = (getResources().getConfiguration().uiMode & 48) == 32;
            this.mSemGoToTopImage =
                    isNight
                            ? getResources()
                                    .getDrawable(R.drawable.sem_list_go_to_top_dark_appwidget)
                            : getResources()
                                    .getDrawable(R.drawable.sem_list_go_to_top_light_appwidget);
        } else {
            this.mSemGoToTopImage =
                    buttonStyle == 0
                            ? this.mSemGoToTopLightImage
                            : this.mContext
                                    .getResources()
                                    .getDrawable(R.drawable.sem_list_go_to_top_dark);
        }
        if (this.mSemGoToTopImage != null) {
            this.mSemEnableGoToTop = enabled;
            if (this.mSemGoToTopImage.getAlpha() != 255) {
                this.mSemGoToTopImage.setAlpha(255);
            }
            this.mSemGoToTopBitmap = drawableToBitmap(this.mSemGoToTopImage);
            this.mSemGoToTopImage.setAlpha(0);
            if (enabled) {
                this.mSemGoToTopImage.setCallback(this);
            } else {
                this.mSemGoToTopImage.setCallback(null);
            }
            this.mSemGoToTopFadeInAnimator = ValueAnimator.ofInt(0, 255);
            this.mSemGoToTopFadeInAnimator.setDuration(333L);
            this.mSemGoToTopFadeInAnimator.setInterpolator(
                    new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
            this.mSemGoToTopFadeInAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class: android.widget.AbsListView.8
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = ((Integer) animation.getAnimatedValue()).intValue();
                            if (AbsListView.this.mAppWidgetGoToTop) {
                                Rect rect = AbsListView.this.mSemGoToTopImage.getBounds();
                                if (rect.left == 0 || rect.top == 0) {
                                    Log.w(
                                            AbsListView.TAG,
                                            "Hide GotoTop immediatley left: "
                                                    + rect.left
                                                    + " top"
                                                    + rect.top);
                                    value = 0;
                                    AbsListView.this.semPlayGotoTopHideImmediatley();
                                }
                            }
                            AbsListView.this.mSemGoToTopImage.setAlpha(value);
                            AbsListView.this.invalidate();
                        }
                    });
            this.mSemGoToTopFadeOutAnimator = ValueAnimator.ofInt(255, 0);
            this.mSemGoToTopFadeOutAnimator.setDuration(333L);
            this.mSemGoToTopFadeOutAnimator.setInterpolator(
                    new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
            this.mSemGoToTopFadeOutAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class: android.widget.AbsListView.9
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = ((Integer) animation.getAnimatedValue()).intValue();
                            AbsListView.this.mSemGoToTopImage.setAlpha(value);
                            AbsListView.this.invalidate();
                        }
                    });
            this.mSemGoToTopFadeOutAnimator.addListener(
                    new Animator.AnimatorListener() { // from class: android.widget.AbsListView.10
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                            AbsListView.this.mShowFadeOutGTP = 1;
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            AbsListView.this.mShowFadeOutGTP = 2;
                            AbsListView.this.semSetupGoToTop(0);
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animation) {}

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animation) {}
                    });
            int elevation = getResources().getDimensionPixelSize(R.dimen.sem_go_to_top_elevation);
            this.mGoToTopRenderNode = new RenderNode("goToTop");
            this.mGoToTopRenderNode.setElevation(elevation);
        }
    }

    public void semAutoHide(int when) {
        if (!this.mSemEnableGoToTop) {
            return;
        }
        if (when == 0) {
            if (!semIsFastScrollEnabled()) {
                removeCallbacks(this.mSemAutoHide);
                postDelayed(this.mSemAutoHide, this.GO_TO_TOP_HIDE);
                return;
            }
            return;
        }
        if (when == 1) {
            removeCallbacks(this.mSemAutoHide);
            postDelayed(this.mSemAutoHide, this.GO_TO_TOP_HIDE);
        }
    }

    public void semShowGoToTOP() {
        if (this.mSemEnableGoToTop && canScrollUp() && this.mSemGoToTopState != 2) {
            semSetupGoToTop(1);
            semAutoHide(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void semPlayGotoToFadeOut() {
        if (this.mSemGoToTopFadeOutAnimator.isRunning()) {
            return;
        }
        if (this.mSemGoToTopFadeInAnimator.isRunning()) {
            this.mSemGoToTopFadeOutAnimator.cancel();
        }
        this.mSemGoToTopFadeOutAnimator.setIntValues(this.mSemGoToTopImage.getAlpha(), 0);
        this.mSemGoToTopFadeOutAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void semPlayGotoToFadeIn() {
        if (this.mSemGoToTopFadeInAnimator.isRunning()) {
            return;
        }
        if (this.mSemGoToTopFadeOutAnimator.isRunning()) {
            this.mSemGoToTopFadeOutAnimator.cancel();
        }
        this.mSemGoToTopFadeInAnimator.setIntValues(this.mSemGoToTopImage.getAlpha(), 255);
        this.mSemGoToTopFadeInAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void semPlayGotoTopHideImmediatley() {
        if (this.mSemGoToTopFadeInAnimator.isRunning()) {
            return;
        }
        if (this.mSemGoToTopFadeOutAnimator.isRunning()) {
            this.mSemGoToTopFadeOutAnimator.cancel();
        }
        this.mSemGoToTopImage.setAlpha(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean semIsSupportGotoTop() {
        return this.mSemEnableGoToTop && !semIsTalkBackIsRunning();
    }

    public void semSetSmoothScrollEnabled(boolean enabled) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        this.mFlingRunnable.mScroller.semSetSmoothScrollEnabled(enabled);
    }

    @Deprecated
    public void semSetFastScrollEventListener(SemFastScrollEventListener l) {
        this.mSemFastScrollEventListener = l;
    }

    public void semSetFastScrollEnabledForAppWidget(boolean enabled) {
        this.mAppWidgetFastScroll = enabled;
        semSetFastScrollEnabled(enabled);
    }

    public void semSetFastScrollEnabled(final boolean enabled) {
        if (this.mFastScrollEnabled != enabled) {
            this.mFastScrollEnabled = enabled;
            if (isOwnerThread()) {
                semSetFastScrollEnabledUiThread(enabled);
            } else {
                post(
                        new Runnable() { // from class: android.widget.AbsListView.11
                            @Override // java.lang.Runnable
                            public void run() {
                                AbsListView.this.semSetFastScrollEnabledUiThread(enabled);
                            }
                        });
            }
        }
    }

    public boolean semIsFastScrollEnabled() {
        if (this.mSemFastScroll == null) {
            return this.mFastScrollEnabled;
        }
        return this.mSemFastScroll.isEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void semSetFastScrollEnabledUiThread(boolean enabled) {
        if (this.mSemFastScroll != null) {
            this.mSemFastScroll.setEnabled(enabled);
        } else if (enabled) {
            this.mSemFastScroll = new SemFastScroller(this, this.mFastScrollStyle);
            this.mSemFastScroll.setEnabled(true);
            this.mFastScroll = null;
        }
        if (this.mAppWidgetFastScroll) {
            this.mSemFastScroll.semSetUseOpenThemeResources(false);
        }
        resolvePadding();
        if (this.mSemFastScroll != null) {
            this.mSemFastScroll.updateLayout();
        }
    }

    public void semSetFastScrollStyle(int styleResId) {
        if (this.mSemFastScroll == null) {
            this.mFastScrollStyle = styleResId;
        } else {
            this.mSemFastScroll.setStyle(styleResId);
        }
    }

    public void semSetFastScrollCustomEffectEnabled(boolean enabled) {
        this.mSemFastScrollCustomEffectEnabled = enabled;
    }

    @Deprecated
    public boolean semIsFastScrollCustomEffectEnabled() {
        return this.mSemFastScrollCustomEffectEnabled;
    }

    public void setFastScrollTrackPadding(int paddingTop, int paddingBottom) {
        isFastScrollEnabled();
    }

    public void semSetLongPressMultiSelectionEnabled(boolean enabled) {
        this.mLongPressMultiSelectionEnabled = enabled;
    }

    @Deprecated
    public void semForceLongPressMultiSelectionForClickableItems() {
        if (this.mLongPressMultiSelectionEnabled) {
            Log.d(TAG, "requested semForceLongPressMultiSelectionForClickableItems by app");
            this.mHasPerformedLongPress = false;
            this.mIsLongPressMultiSelection = true;
            this.mTouchMode = -1;
        }
    }

    public void semSetCtrlKeyPressed(boolean pressed) {
        this.mIsCtrlkeyPressed = pressed;
    }

    private final class CheckForDoublePenClick implements Runnable {
        int x;
        int y;

        private CheckForDoublePenClick() {}

        @Override // java.lang.Runnable
        public void run() {
            boolean isNeedActionMode = false;
            if (AbsListView.this.mIsFirstPenClick && AbsListView.this.mAdapter != null) {
                if (AbsListView.this.mSemDragSelectedItemSize != 0) {
                    if (AbsListView.this.mCheckStates != null
                            && (AbsListView.this.mChoiceMode == 2
                                    || AbsListView.this.mChoiceMode == 3)) {
                        Iterator it = AbsListView.this.mSemDragSelectedItemArray.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            if (AbsListView.this.mAdapter.isEnabled(
                                    ((Integer) it.next()).intValue())) {
                                isNeedActionMode = true;
                                break;
                            }
                        }
                        if (AbsListView.this.mChoiceMode == 3
                                && AbsListView.this.mChoiceActionMode == null
                                && isNeedActionMode) {
                            AbsListView.this.mChoiceActionMode =
                                    AbsListView.this.startActionMode(
                                            AbsListView.this.mMultiChoiceModeCallback);
                        }
                        if (AbsListView.this.mIsSemOnClickEnabled
                                && AbsListView.this.mSemMultiSelectionListener == null) {
                            Iterator it2 = AbsListView.this.mSemDragSelectedItemArray.iterator();
                            while (it2.hasNext()) {
                                Integer dragSelectedViewPosition = (Integer) it2.next();
                                if (AbsListView.this.mAdapter.isEnabled(
                                        dragSelectedViewPosition.intValue())) {
                                    AbsListView.this.performItemClick(
                                            null,
                                            dragSelectedViewPosition.intValue(),
                                            AbsListView.this.getItemIdAtPosition(
                                                    dragSelectedViewPosition.intValue()));
                                }
                            }
                        }
                    }
                    AbsListView.this.semToNotifyMultiSelectionEnded(this.x, this.y);
                }
                AbsListView.this.mSemDragSelectedItemArray.clear();
                AbsListView.this.mSemDragSelectedItemSize = 0;
            }
            AbsListView.this.mIsFirstPenClick = false;
        }
    }

    private void semMultiSelection(
            int x, int y, int contentTop, int contentBottom, boolean needToScroll) {
        boolean needToScroll2;
        int i;
        int i2;
        int deltaMoveX;
        int deltaMoveY;
        int deltaMoveX2 = x - this.mTouchdownX;
        int deltaMoveY2 = y - this.mTouchdownY;
        if ((deltaMoveX2 * deltaMoveX2) + (deltaMoveY2 * deltaMoveY2)
                > this.mTouchSlop * this.mTouchSlop) {
            this.mIsMovedbeforeUP = true;
        }
        if (this.mIsNeedPenSelection) {
            int count = getChildCount();
            if (this.mIsfirstMoveEvent) {
                this.mSemDragStartX = x;
                this.mSemDragStartY = y;
                super.semNotifyMultiSelectedStart(x, y);
                this.mIsPenPressed = true;
                this.mSemTrackedChildPosition = pointToPosition(x, y);
                if (this.mSemTrackedChildPosition == -1) {
                    this.mSemTrackedChildPosition = semPointToNearPosition(x, y);
                    this.mSemTrackedChild =
                            getChildAt(this.mSemTrackedChildPosition - getFirstVisiblePosition());
                    if (this.mSemTrackedChild == null) {
                        this.mSemCloseChildByTop =
                                getChildAt(
                                        this.mSemCloseChildPositionByTop
                                                - getFirstVisiblePosition());
                        if (this.mSemCloseChildByTop != null) {
                            this.mSemDistanceFromCloseChildTop =
                                    this.mSemDragStartY - this.mSemCloseChildByTop.getTop();
                        }
                        this.mSemCloseChildByBottom =
                                getChildAt(
                                        this.mSemCloseChildPositionByBottom
                                                - getFirstVisiblePosition());
                        if (this.mSemCloseChildByBottom != null) {
                            this.mSemDistanceFromCloseChildBottom =
                                    this.mSemDragStartY - this.mSemCloseChildByBottom.getTop();
                        }
                    }
                } else {
                    this.mSemTrackedChild =
                            getChildAt(this.mSemTrackedChildPosition - getFirstVisiblePosition());
                }
                if (this.mSemTrackedChild != null) {
                    this.mSemDistanceFromTrackedChildTop =
                            this.mSemDragStartY - this.mSemTrackedChild.getTop();
                }
                this.mIsfirstMoveEvent = false;
            }
            if (this.mSemDragStartX == 0 && this.mSemDragStartY == 0) {
                this.mSemDragStartX = x;
                this.mSemDragStartY = y;
                super.semNotifyMultiSelectedStart(x, y);
                this.mIsPenPressed = true;
            }
            this.mSemDragEndX = x;
            this.mSemDragEndY = y;
            if (this.mSemDragEndY < 0) {
                this.mSemDragEndY = 0;
            } else if (this.mSemDragEndY > contentBottom) {
                this.mSemDragEndY = contentBottom;
            }
            this.mSemDragSelectedViewPosition = pointToPosition(x, y);
            this.mSemDragBlockLeft = Math.min(this.mSemDragStartX, this.mSemDragEndX);
            this.mSemDragBlockTop = Math.min(this.mSemDragStartY, this.mSemDragEndY);
            this.mSemDragBlockRight = Math.max(this.mSemDragEndX, this.mSemDragStartX);
            this.mSemDragBlockBottom = Math.max(this.mSemDragEndY, this.mSemDragStartY);
            int i3 = 0;
            while (i3 < count) {
                View child = getChildAt(i3);
                if (child == null) {
                    deltaMoveX = deltaMoveX2;
                    deltaMoveY = deltaMoveY2;
                } else {
                    int childLeft = child.getLeft();
                    int childTop = child.getTop();
                    int childRight = child.getRight();
                    int childBottom = child.getBottom();
                    if (child.getVisibility() != 0) {
                        deltaMoveX = deltaMoveX2;
                        deltaMoveY = deltaMoveY2;
                    } else if ((this.mSemDragBlockLeft > childLeft
                                    && this.mSemDragBlockTop > childTop
                                    && this.mSemDragBlockRight < childRight
                                    && this.mSemDragBlockBottom < childBottom)
                            || (((this.mSemDragBlockLeft > childLeft
                                                    && this.mSemDragBlockRight < childRight)
                                            || ((this.mSemDragBlockLeft < childLeft
                                                            && this.mSemDragBlockRight > childLeft)
                                                    || (this.mSemDragBlockLeft < childRight
                                                            && this.mSemDragBlockRight
                                                                    > childRight)))
                                    && ((this.mSemDragBlockTop >= childTop
                                                    && this.mSemDragBlockBottom <= childBottom)
                                            || ((this.mSemDragBlockTop <= childTop
                                                            && this.mSemDragBlockBottom > childTop)
                                                    || (this.mSemDragBlockTop < childBottom
                                                            && this.mSemDragBlockBottom
                                                                    >= childBottom))))) {
                        this.mSemDragSelectedViewPosition =
                                pointToPosition(childLeft + 1, childTop + 1);
                        if (this.mSemDragSelectedViewPosition == -1
                                || !this.mAdapter.isEnabled(this.mSemDragSelectedViewPosition)) {
                            deltaMoveX = deltaMoveX2;
                            deltaMoveY = deltaMoveY2;
                        } else if (!this.mSemDragSelectedItemArray.contains(
                                Integer.valueOf(this.mSemDragSelectedViewPosition))) {
                            this.mSemDragSelectedItemArray.add(
                                    Integer.valueOf(this.mSemDragSelectedViewPosition));
                            if (this.mSemMultiSelectionListener == null) {
                                addToPressItemListArray(this.mSemDragSelectedViewPosition, -1);
                                deltaMoveX = deltaMoveX2;
                                deltaMoveY = deltaMoveY2;
                                semToNotifyMultiSelectionState(
                                        child,
                                        this.mSemDragSelectedViewPosition,
                                        getItemIdAtPosition(this.mSemDragSelectedViewPosition));
                            } else {
                                deltaMoveX = deltaMoveX2;
                                deltaMoveY = deltaMoveY2;
                            }
                        } else {
                            deltaMoveX = deltaMoveX2;
                            deltaMoveY = deltaMoveY2;
                        }
                    } else {
                        deltaMoveX = deltaMoveX2;
                        deltaMoveY = deltaMoveY2;
                        this.mSemDragSelectedViewPosition =
                                pointToPosition(childLeft + 1, childTop + 1);
                        if (this.mSemDragSelectedViewPosition != -1
                                && this.mAdapter.isEnabled(this.mSemDragSelectedViewPosition)
                                && this.mSemDragSelectedItemArray.contains(
                                        Integer.valueOf(this.mSemDragSelectedViewPosition))) {
                            this.mSemDragSelectedItemArray.remove(
                                    Integer.valueOf(this.mSemDragSelectedViewPosition));
                            if (this.mSemMultiSelectionListener == null) {
                                addToPressItemListArray(this.mSemDragSelectedViewPosition, -1);
                                semToNotifyMultiSelectionState(
                                        child,
                                        this.mSemDragSelectedViewPosition,
                                        getItemIdAtPosition(this.mSemDragSelectedViewPosition));
                            }
                        }
                    }
                }
                i3++;
                deltaMoveX2 = deltaMoveX;
                deltaMoveY2 = deltaMoveY;
            }
            needToScroll2 = true;
        } else {
            needToScroll2 = needToScroll;
        }
        if (needToScroll2) {
            if (y <= contentTop + this.mHoverTopAreaHeight) {
                if (this.mHoverAreaEnter) {
                    i2 = 1;
                } else {
                    i2 = 1;
                    this.mHoverAreaEnter = true;
                    this.mHoverScrollStartTime = System.currentTimeMillis();
                    if (this.mOnScrollListener != null) {
                        this.mOnScrollListener.onScrollStateChanged(this, 1);
                    }
                }
                if (!this.mHoverHandler.hasMessages(i2)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    this.mHoverScrollDirection = 2;
                    this.mHoverHandler.sendEmptyMessage(i2);
                }
            } else if (y >= contentBottom - this.mHoverBottomAreaHeight) {
                if (this.mHoverAreaEnter) {
                    i = 1;
                } else {
                    i = 1;
                    this.mHoverAreaEnter = true;
                    this.mHoverScrollStartTime = System.currentTimeMillis();
                    if (this.mOnScrollListener != null) {
                        this.mOnScrollListener.onScrollStateChanged(this, 1);
                    }
                }
                if (!this.mHoverHandler.hasMessages(i)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    this.mHoverScrollDirection = i;
                    this.mHoverHandler.sendEmptyMessage(i);
                }
            } else {
                if (this.mHoverAreaEnter && this.mOnScrollListener != null) {
                    this.mOnScrollListener.onScrollStateChanged(this, 0);
                }
                this.mHoverScrollStartTime = 0L;
                this.mHoverRecognitionStartTime = 0L;
                this.mHoverAreaEnter = false;
                if (this.mHoverHandler.hasMessages(1)) {
                    this.mHoverHandler.removeMessages(1);
                }
                this.mIsHoverOverscrolled = false;
            }
            if (this.mIsDragBlockEnabled) {
                invalidate();
            }
        } else if (this.mPreviousTextViewScroll && this.mHoverHandler.hasMessages(1)) {
            this.mHoverHandler.removeMessages(1);
        }
        this.mPreviousTextViewScroll = needToScroll2;
    }

    private void semMultiSelectionEnd(int action, int x, int y) {
        if (!this.mIsTextSelectionStarted) {
            if (action == 212) {
                this.mIsFirstPenClick = !this.mIsFirstPenClick;
            }
            if (this.mHoverAreaEnter && this.mOnScrollListener != null) {
                this.mOnScrollListener.onScrollStateChanged(this, 0);
            }
            this.mHoverRecognitionStartTime = 0L;
            this.mHoverScrollStartTime = 0L;
            this.mHoverAreaEnter = false;
            this.mSemDragSelectedItemSize = this.mSemDragSelectedItemArray.size();
            if (this.mPendingCheckForDoublePenClick == null) {
                this.mPendingCheckForDoublePenClick = new CheckForDoublePenClick();
            }
            this.mPendingCheckForDoublePenClick.x = x;
            this.mPendingCheckForDoublePenClick.y = y;
            if (this.mIsFirstPenClick) {
                if (this.mIsMovedbeforeUP) {
                    post(this.mPendingCheckForDoublePenClick);
                } else {
                    postDelayed(
                            this.mPendingCheckForDoublePenClick,
                            ViewConfiguration.getDoubleTapTimeout());
                }
            } else {
                removeCallbacks(this.mPendingCheckForDoublePenClick);
                this.mSemDragSelectedItemArray.clear();
                this.mSemDragSelectedItemSize = 0;
            }
        }
        this.mIsPenPressed = false;
        this.mIsfirstMoveEvent = true;
        this.mSemDragSelectedViewPosition = -1;
        this.mSemDragStartX = 0;
        this.mSemDragStartY = 0;
        this.mSemDragEndX = 0;
        this.mSemDragEndY = 0;
        this.mSemDragBlockLeft = 0;
        this.mSemDragBlockTop = 0;
        this.mSemDragBlockRight = 0;
        this.mSemDragBlockBottom = 0;
        this.mSemTrackedChild = null;
        this.mSemDistanceFromTrackedChildTop = 0;
        this.mIsCloseChildSetted = false;
        this.mOldHoverScrollDirection = -1;
        this.mSemCloseChildByTop = null;
        this.mSemCloseChildPositionByTop = -1;
        this.mSemDistanceFromCloseChildTop = 0;
        this.mSemCloseChildByBottom = null;
        this.mSemCloseChildPositionByBottom = -1;
        this.mSemDistanceFromCloseChildBottom = 0;
        if (this.mIsDragBlockEnabled) {
            invalidate();
        }
        if (this.mHoverHandler.hasMessages(1)) {
            this.mHoverHandler.removeMessages(1);
        }
        this.mIsMovedbeforeUP = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void semToNotifyMultiSelectionEnded(int x, int y) {
        super.semNotifyMultiSelectedStop(x, y);
    }

    private void updateLongPressMultiSelection(int x, int y, boolean fromUserTouch) {
        int contentBottom;
        int startPosition;
        int endPosition;
        char c;
        int touchedPosition;
        int count = getChildCount();
        boolean z = false;
        char c2 = 65535;
        if (this.mIsFirstMultiSelectionMove) {
            this.mSemDragStartX = x;
            this.mSemDragStartY = y;
            super.semNotifyLongPressMultiSelectionStarted(x, y);
            int pointToPosition = pointToPosition(x, y);
            this.mSemTrackedChildPosition = pointToPosition;
            this.mSemDragSelectedViewPosition = pointToPosition;
            if (this.mSemTrackedChildPosition == -1) {
                this.mSemTrackedChildPosition = semPointToNearPosition(x, y);
                if (this.mSemTrackedChild == null) {
                    this.mSemCloseChildByTop =
                            getChildAt(
                                    this.mSemCloseChildPositionByTop - getFirstVisiblePosition());
                    if (this.mSemCloseChildByTop != null) {
                        this.mSemDistanceFromCloseChildTop =
                                this.mSemDragStartY - this.mSemCloseChildByTop.getTop();
                    }
                    this.mSemCloseChildByBottom =
                            getChildAt(
                                    this.mSemCloseChildPositionByBottom
                                            - getFirstVisiblePosition());
                    if (this.mSemCloseChildByBottom != null) {
                        this.mSemDistanceFromCloseChildBottom =
                                this.mSemDragStartY - this.mSemCloseChildByBottom.getTop();
                    }
                }
            } else {
                this.mSemTrackedChild =
                        getChildAt(this.mSemTrackedChildPosition - getFirstVisiblePosition());
            }
            if (this.mSemTrackedChild != null) {
                this.mSemDistanceFromTrackedChildTop =
                        this.mSemDragStartY - this.mSemTrackedChild.getTop();
            }
            this.mIsFirstMultiSelectionMove = false;
        }
        int contentTop = 0;
        if (this.mIsEnabledPaddingInHoverScroll) {
            contentTop = this.mListPadding.top;
            contentBottom = getHeight() - this.mListPadding.bottom;
        } else {
            contentBottom = getHeight();
        }
        this.mSemDragEndX = x;
        this.mSemDragEndY = y;
        if (this.mSemDragEndY < 0) {
            this.mSemDragEndY = 0;
        } else if (this.mSemDragEndY > contentBottom) {
            this.mSemDragEndY = contentBottom;
        }
        int touchedPosition2 = pointToPosition(this.mSemDragEndX, this.mSemDragEndY);
        if (touchedPosition2 == -1) {
            this.mSemDragSelectedViewPosition =
                    semPointToNearPosition(this.mSemDragEndX, this.mSemDragEndY);
        } else {
            this.mSemDragSelectedViewPosition = touchedPosition2;
        }
        if (this.mSemTrackedChildPosition < this.mSemDragSelectedViewPosition) {
            startPosition = this.mSemTrackedChildPosition;
            endPosition = this.mSemDragSelectedViewPosition;
        } else {
            startPosition = this.mSemDragSelectedViewPosition;
            endPosition = this.mSemTrackedChildPosition;
        }
        this.mSemDragBlockLeft = Math.min(this.mSemDragStartX, this.mSemDragEndX);
        this.mSemDragBlockTop = Math.min(this.mSemDragStartY, this.mSemDragEndY);
        this.mSemDragBlockRight = Math.max(this.mSemDragEndX, this.mSemDragStartX);
        this.mSemDragBlockBottom = Math.max(this.mSemDragEndY, this.mSemDragStartY);
        int i = 0;
        while (i < count) {
            View child = getChildAt(i);
            if (child == null) {
                c = c2;
                touchedPosition = touchedPosition2;
            } else {
                int childLeft = child.getLeft();
                int childTop = child.getTop();
                int childPosition = getPositionForView(child);
                if (child.getVisibility() != 0) {
                    c = c2;
                    touchedPosition = touchedPosition2;
                } else {
                    boolean needSelected = false;
                    boolean notNeedToCheck = false;
                    if (startPosition <= childPosition && childPosition <= endPosition) {
                        if (this.mCheckStates != null && this.mCheckStates.get(childPosition, z)) {
                            notNeedToCheck = true;
                        } else {
                            notNeedToCheck = false;
                        }
                        needSelected = true;
                    }
                    if (needSelected) {
                        this.mSemDragSelectedViewPosition =
                                pointToPosition(childLeft + 1, childTop + 1);
                        if (this.mSemDragSelectedViewPosition == -1
                                || !this.mAdapter.isEnabled(this.mSemDragSelectedViewPosition)) {
                            touchedPosition = touchedPosition2;
                            c = 65535;
                        } else if (this.mSemDragSelectedItemArray.contains(
                                Integer.valueOf(this.mSemDragSelectedViewPosition))) {
                            touchedPosition = touchedPosition2;
                            c = 65535;
                        } else {
                            if (!notNeedToCheck
                                    || startPosition > this.mSemDragSelectedViewPosition
                                    || this.mSemDragSelectedViewPosition > endPosition) {
                                touchedPosition = touchedPosition2;
                                semPerformItemCheck(
                                        null,
                                        this.mSemDragSelectedViewPosition,
                                        getItemIdAtPosition(this.mSemDragSelectedViewPosition));
                            } else {
                                touchedPosition = touchedPosition2;
                            }
                            this.mSemDragSelectedItemArray.add(
                                    Integer.valueOf(this.mSemDragSelectedViewPosition));
                            addToPressItemListArray(this.mSemDragSelectedViewPosition, -1);
                            super.semNotifyLongPressMultiSelectionState(
                                    child,
                                    this.mSemDragSelectedViewPosition,
                                    getItemIdAtPosition(this.mSemDragSelectedViewPosition));
                            c = 65535;
                        }
                    } else {
                        touchedPosition = touchedPosition2;
                        this.mSemDragSelectedViewPosition =
                                pointToPosition(childLeft + 1, childTop + 1);
                        c = 65535;
                        if (this.mSemDragSelectedViewPosition != -1) {
                            if (!this.mAdapter.isEnabled(this.mSemDragSelectedViewPosition)) {
                                c = 65535;
                            } else if (this.mSemDragSelectedItemArray.contains(
                                    Integer.valueOf(this.mSemDragSelectedViewPosition))) {
                                semPerformItemCheck(
                                        null,
                                        this.mSemDragSelectedViewPosition,
                                        getItemIdAtPosition(this.mSemDragSelectedViewPosition));
                                this.mSemDragSelectedItemArray.remove(
                                        Integer.valueOf(this.mSemDragSelectedViewPosition));
                                c = 65535;
                                addToPressItemListArray(this.mSemDragSelectedViewPosition, -1);
                                super.semNotifyLongPressMultiSelectionState(
                                        child,
                                        this.mSemDragSelectedViewPosition,
                                        getItemIdAtPosition(this.mSemDragSelectedViewPosition));
                            } else {
                                c = 65535;
                            }
                        }
                    }
                }
            }
            i++;
            c2 = c;
            touchedPosition2 = touchedPosition;
            z = false;
        }
        if (fromUserTouch) {
            if (y <= this.mHoverTopAreaHeight + contentTop) {
                if (!this.mHoverAreaEnter) {
                    this.mHoverAreaEnter = true;
                    this.mHoverScrollStartTime = System.currentTimeMillis();
                    if (this.mOnScrollListener != null) {
                        this.mOnScrollListener.onScrollStateChanged(this, 1);
                    }
                }
                if (!this.mHoverHandler.hasMessages(1)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    this.mHoverScrollDirection = 2;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else if (y >= contentBottom - this.mHoverBottomAreaHeight) {
                if (!this.mHoverAreaEnter) {
                    this.mHoverAreaEnter = true;
                    this.mHoverScrollStartTime = System.currentTimeMillis();
                    if (this.mOnScrollListener != null) {
                        this.mOnScrollListener.onScrollStateChanged(this, 1);
                    }
                }
                if (!this.mHoverHandler.hasMessages(1)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    this.mHoverScrollDirection = 1;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else {
                if (this.mHoverAreaEnter && this.mOnScrollListener != null) {
                    this.mOnScrollListener.onScrollStateChanged(this, 0);
                }
                this.mHoverScrollStartTime = 0L;
                this.mHoverRecognitionStartTime = 0L;
                this.mHoverAreaEnter = false;
                if (this.mHoverHandler.hasMessages(1)) {
                    this.mHoverHandler.removeMessages(1);
                }
                this.mIsHoverOverscrolled = false;
            }
        }
        invalidate();
    }

    private void semPerformItemCheck(View view, int position, long id) {
        boolean checkedStateChanged = false;
        if (this.mCheckStates != null
                && (this.mChoiceMode == 2
                        || (this.mChoiceMode == 3 && this.mChoiceActionMode != null))) {
            boolean checked = !this.mCheckStates.get(position, false);
            this.mCheckStates.put(position, checked);
            if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                if (checked) {
                    this.mCheckedIdStates.put(
                            this.mAdapter.getItemId(position), Integer.valueOf(position));
                } else {
                    this.mCheckedIdStates.delete(this.mAdapter.getItemId(position));
                }
            }
            if (checked) {
                this.mCheckedItemCount++;
            } else {
                this.mCheckedItemCount--;
            }
            if (this.mChoiceActionMode != null && this.mMultiChoiceModeCallback != null) {
                this.mMultiChoiceModeCallback.onItemCheckedStateChanged(
                        this.mChoiceActionMode, position, id, checked);
            }
            checkedStateChanged = true;
        }
        if (checkedStateChanged) {
            updateOnScreenCheckedViews();
        }
    }

    private void endLongPressMultiSelection(int x, int y) {
        super.semNotifyLongPressMultiSelectionEnded(x, y);
        this.mIsFirstMultiSelectionMove = true;
        this.mSemDragSelectedViewPosition = -1;
        this.mSemDragStartX = 0;
        this.mSemDragStartY = 0;
        this.mSemDragEndX = 0;
        this.mSemDragEndY = 0;
        this.mSemDragBlockLeft = 0;
        this.mSemDragBlockTop = 0;
        this.mSemDragBlockRight = 0;
        this.mSemDragBlockBottom = 0;
        this.mSemDragSelectedItemArray.clear();
        this.mSemDragSelectedItemSize = 0;
        this.mSemTrackedChild = null;
        this.mSemDistanceFromTrackedChildTop = 0;
        if (this.mHoverHandler.hasMessages(1)) {
            this.mHoverHandler.removeMessages(1);
        }
        this.mIsHoverOverscrolled = false;
        invalidate();
        this.mIsLongPressMultiSelection = false;
    }

    public void semSetDragBlockEnabled(boolean enabled) {
        this.mIsDragBlockEnabled = enabled;
    }

    public int semPointToNearPosition(int x, int y) {
        int oldDistanceY;
        AbsListView absListView = this;
        int count = getChildCount();
        int adjustY = y;
        int oldDistanceY2 = Integer.MAX_VALUE;
        int previousChildCenter = 0;
        for (int i = count - 1; i >= 0; i--) {
            View child = absListView.getChildAt(i);
            if (child != null) {
                int childTop = child.getTop();
                int childCenter = (childTop + child.getBottom()) / 2;
                if (previousChildCenter != childCenter) {
                    previousChildCenter = childCenter;
                    int newDistanceY = Math.abs(y - childCenter);
                    if (newDistanceY >= oldDistanceY2) {
                        break;
                    }
                    oldDistanceY2 = newDistanceY;
                    adjustY = childCenter;
                } else {
                    continue;
                }
            }
        }
        int oldDistanceFromLeft = 0;
        int oldDistanceFromRight = 0;
        int closeIndexByLeft = 0;
        int closeIndexByRight = 0;
        int i2 = count - 1;
        while (i2 >= 0) {
            View child2 = absListView.getChildAt(i2);
            if (child2 == null) {
                oldDistanceY = oldDistanceY2;
            } else {
                int childTop2 = child2.getTop();
                int childBottom = child2.getBottom();
                int childLeft = child2.getLeft();
                int childRight = child2.getRight();
                oldDistanceY = oldDistanceY2;
                int oldDistanceY3 = count - 1;
                if (i2 == oldDistanceY3) {
                    closeIndexByLeft = (count - 1) + getFirstVisiblePosition();
                    closeIndexByRight = (count - 1) + getFirstVisiblePosition();
                    oldDistanceFromLeft = Math.abs(x - childLeft);
                    oldDistanceFromRight = Math.abs(x - childRight);
                }
                if (adjustY >= childTop2 && adjustY <= childBottom) {
                    int newDistanceFromLeft = Math.abs(x - childLeft);
                    int newDistanceFromRight = Math.abs(x - childRight);
                    if (newDistanceFromLeft <= oldDistanceFromLeft) {
                        closeIndexByLeft = i2 + getFirstVisiblePosition();
                        oldDistanceFromLeft = newDistanceFromLeft;
                    }
                    if (newDistanceFromRight <= oldDistanceFromRight) {
                        closeIndexByRight = i2 + getFirstVisiblePosition();
                        oldDistanceFromRight = newDistanceFromRight;
                    }
                }
                if (adjustY > childBottom || i2 == 0) {
                    if (oldDistanceFromLeft < oldDistanceFromRight) {
                        return closeIndexByLeft;
                    }
                    return closeIndexByRight;
                }
            }
            i2--;
            absListView = this;
            oldDistanceY2 = oldDistanceY;
        }
        Log.e(TAG, "semPointToNearPosition didn't find valid position!! " + x + ", " + y);
        return -1;
    }

    public void semSetClickableInMultiSelectMode(boolean clickable) {
        this.mIsSemOnClickEnabled = clickable;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public void setTouchSlop(int value) {
        this.mTouchSlop = value;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0136  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean dispatchTouchEvent(android.view.MotionEvent r15) {
        /*
            Method dump skipped, instructions count: 520
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.widget.AbsListView.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x0384  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x03a8  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x03c4  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean dispatchHoverEvent(android.view.MotionEvent r18) {
        /*
            Method dump skipped, instructions count: 1000
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.widget.AbsListView.dispatchHoverEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchDragEvent(DragEvent ev) {
        int action = ev.getAction();
        ClipDescription cd = ev.getClipDescription();
        if (cd == null || !"cropUri".equals(cd.getLabel())) {
            return super.dispatchDragEvent(ev);
        }
        if (action == 1) {
            if (this.mDragScrollWorkingZonePx <= 0) {
                this.mDragScrollWorkingZonePx =
                        (int)
                                (TypedValue.applyDimension(
                                                1,
                                                25.0f,
                                                this.mContext.getResources().getDisplayMetrics())
                                        + 0.5f);
            }
            super.dispatchDragEvent(ev);
            return true;
        }
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int contentBottom = 0;
        int count = getChildCount();
        if (count != 0) {
            contentBottom = getHeight();
        }
        boolean canScrollDown = this.mFirstPosition + count < this.mItemCount;
        if (!canScrollDown && count > 0) {
            View child = getChildAt(count - 1);
            canScrollDown =
                    child.getBottom() > this.mBottom - this.mListPadding.bottom
                            || child.getBottom() > getHeight() - this.mListPadding.bottom;
        }
        boolean canScrollUp = this.mFirstPosition > 0;
        if (!canScrollUp && count > 0) {
            canScrollUp = getChildAt(0).getTop() < this.mListPadding.top;
        }
        if ((y > this.mDragScrollWorkingZonePx && y < contentBottom - this.mDragScrollWorkingZonePx)
                || x <= 0
                || x > getRight()
                || (!canScrollUp && !canScrollDown)) {
            if (this.mHoverHandler != null && this.mHoverHandler.hasMessages(1)) {
                this.mHoverHandler.removeMessages(1);
            }
            if (this.mIsHoverOverscrolled || this.mHoverScrollStartTime != 0) {
                this.mIsHoverOverscrolled = false;
            }
            this.mHoverRecognitionStartTime = 0L;
            this.mHoverScrollStartTime = 0L;
            this.mHoverAreaEnter = false;
            if (action == 2 && this.mIsDragScrolled) {
                this.mIsDragScrolled = false;
            }
            return super.dispatchDragEvent(ev);
        }
        if (this.mHoverHandler == null) {
            this.mHoverHandler = new HoverScrollHandler(this);
        }
        if (!this.mHoverAreaEnter) {
            this.mHoverScrollStartTime = System.currentTimeMillis();
        }
        switch (action) {
            case 2:
                this.mHoverAreaEnter = true;
                if (y >= 0 && y <= this.mDragScrollWorkingZonePx) {
                    if (!this.mHoverHandler.hasMessages(1)) {
                        this.mIsDragScrolled = true;
                        this.mHoverRecognitionStartTime = System.currentTimeMillis();
                        this.mHoverScrollDirection = 2;
                        this.mHoverHandler.sendEmptyMessage(1);
                        break;
                    }
                } else if (y >= contentBottom - this.mDragScrollWorkingZonePx
                        && y <= contentBottom
                        && !this.mHoverHandler.hasMessages(1)) {
                    this.mIsDragScrolled = true;
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    this.mHoverScrollDirection = 1;
                    this.mHoverHandler.sendEmptyMessage(1);
                    break;
                }
                break;
            case 3:
                if (this.mIsDragScrolled) {
                    this.mIsDragScrolled = false;
                }
            case 4:
            case 6:
                if (this.mHoverHandler.hasMessages(1)) {
                    this.mHoverHandler.removeMessages(1);
                }
                this.mIsDragScrolled = false;
                this.mHoverRecognitionStartTime = 0L;
                this.mHoverScrollStartTime = 0L;
                this.mIsHoverOverscrolled = false;
                this.mHoverAreaEnter = false;
                break;
            case 5:
                this.mHoverAreaEnter = true;
                if (y >= 0 && y <= this.mDragScrollWorkingZonePx) {
                    if (!this.mHoverHandler.hasMessages(1)) {
                        this.mIsDragScrolled = true;
                        this.mHoverRecognitionStartTime = System.currentTimeMillis();
                        this.mHoverScrollDirection = 2;
                        this.mHoverHandler.sendEmptyMessage(1);
                        break;
                    }
                } else if (y >= contentBottom - this.mDragScrollWorkingZonePx
                        && y <= contentBottom
                        && !this.mHoverHandler.hasMessages(1)) {
                    this.mIsDragScrolled = true;
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    this.mHoverScrollDirection = 1;
                    this.mHoverHandler.sendEmptyMessage(1);
                    break;
                }
                break;
        }
        return super.dispatchDragEvent(ev);
    }

    public void setEnableHoverDrawable(boolean enable) {
        this.mHoveringEnabled = enable;
    }

    public boolean isLockScreenMode() {
        KeyguardManager keyguardManager =
                (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    private boolean semIsTalkBackIsRunning() {
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(this.mContext);
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return false;
        }
        boolean isRunning =
                accessibilityManager.semIsAccessibilityServiceEnabled(32)
                        || accessibilityManager.semIsAccessibilityServiceEnabled(16)
                        || accessibilityManager.semIsAccessibilityServiceEnabled(64);
        return isRunning;
    }

    private class SemSmoothScrollByMove implements Runnable {
        private SemSmoothScrollByMove() {}

        @Override // java.lang.Runnable
        public void run() {
            if (AbsListView.this.mFlingRunnable.mScroller.isFinished()) {
                if (AbsListView.this.mSemScrollRemains == null
                        || AbsListView.this.mSemScrollRemains.isEmpty()) {
                    return;
                }
                Integer remain = (Integer) AbsListView.this.mSemScrollRemains.poll();
                if (remain != null) {
                    AbsListView.this.smoothScrollBy(remain.intValue(), 0);
                }
            }
            AbsListView.this.post(this);
        }
    }

    @Override // android.view.View
    protected boolean semIsShowingScrollbar() {
        return super.semIsShowingScrollbar() && !this.mFastScrollEnabled;
    }

    @Override // android.view.ViewGroup
    protected int semGetItemCount() {
        Adapter adapter = getAdapter();
        if (adapter == null) {
            return 0;
        }
        return adapter.getCount();
    }

    @Override // android.view.ViewGroup
    protected boolean isSemUsingAdapterView() {
        return true;
    }

    @Override // android.view.ViewGroup
    public void semSetSelection(int position) {
        setSelection(position);
    }

    @Override // android.view.ViewGroup
    public void semSmoothScrollBy(int distance) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        if (this.mSemScrollRemains == null) {
            this.mSemScrollRemains = new LinkedList<>();
            this.mSemSmoothScrollByMove = new SemSmoothScrollByMove();
            this.mSemScrollAmount = (int) (this.mDensityScale * 150.0f);
        }
        boolean isEmpty = this.mSemScrollRemains.isEmpty();
        if (Math.abs(distance) > this.mSemScrollAmount) {
            if (distance > 0) {
                while (distance > this.mSemScrollAmount) {
                    this.mSemScrollRemains.offer(Integer.valueOf(this.mSemScrollAmount));
                    distance -= this.mSemScrollAmount;
                }
            } else {
                while (distance < (-this.mSemScrollAmount)) {
                    this.mSemScrollRemains.offer(Integer.valueOf(-this.mSemScrollAmount));
                    distance += this.mSemScrollAmount;
                }
            }
        }
        this.mSemScrollRemains.offer(Integer.valueOf(distance));
        if (isEmpty) {
            post(this.mSemSmoothScrollByMove);
        }
    }

    @Deprecated
    public void semSetForcedEdgeEffectEnabled(boolean enable) {
        this.mSemForcedDrawEdgeEffect = enable;
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        this.mHoverPosition = -1;
        if (visibility != 0) {
            releaseAllBoosters();
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void setForcedClick(boolean force) {
        this.mForcedClick = force;
    }

    public void setOverScrollEffectPadding(int leftPadding, int rightPadding) {}

    public void triggerJumpScrollToTop() {
        this.mJumpScrollToTopState = JUMP_SCROLL_TO_TOP_INITIATED;
        triggerDoubleFling(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postOnJumpScrollToFinished() {
        postOnAnimation(
                new Runnable() { // from class: android.widget.AbsListView.13
                    @Override // java.lang.Runnable
                    public void run() {
                        AbsListView.this.onJumpScrollToTopFinished();
                    }
                });
    }

    void onJumpScrollToTopFinished() {
        Log.d(TAG, "onJumpScrollToTopFinished()");
    }

    void triggerDoubleFling(int initialVelocity) {
        int adapterCount = getAdapter().getCount();
        int visibleItemsCount = getChildCount();
        if (initialVelocity > 0) {
            if (getLastVisiblePosition() > visibleItemsCount * 2) {
                setSelection(visibleItemsCount * 2);
            }
            smoothScrollToPosition(0);
        } else if (initialVelocity < 0) {
            if ((adapterCount - 1) - getFirstVisiblePosition() > visibleItemsCount * 3) {
                setSelection((adapterCount - 1) - (visibleItemsCount * 3));
            }
            smoothScrollToPosition(adapterCount - 1);
        }
    }

    void removePendingCallbacks() {
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.mPendingCheckForTap);
            handler.removeCallbacks(this.mPendingCheckForLongPress);
        }
        this.mTouchMode = -1;
    }

    public void setSweepListAnimator(SemSweepListAnimator animator) {
        this.mSweepListAnimator = animator;
    }

    public int semGetLastScrollState() {
        return this.mLastScrollState;
    }

    public boolean semIsLongPressTriggeredByKey() {
        return this.mIsLongPressTriggeredByKey;
    }

    private boolean semGetEnableVibrationAtLongPress() {
        return this.mEnableVibrationAtLongPress;
    }

    public void semSetEnableVibrationAtLongPress(boolean enable) {
        this.mEnableVibrationAtLongPress = enable;
    }

    public void setEnableDoubleFling(boolean enable) {
        this.mDoubleFlingEnabled = enable;
    }

    private void releaseAllBoosters() {
        if (this.mDVFSLockAcquired) {
            SemPerfManager.onScrollEvent(false);
            this.mDVFSLockAcquired = false;
        }
    }

    private void hidden_mEdgeGlowBottom(EdgeEffect edgeEffect) {
        this.mEdgeGlowBottom = edgeEffect;
    }

    private void hidden_mEdgeGlowTop(EdgeEffect edgeEffect) {
        this.mEdgeGlowTop = edgeEffect;
    }

    private EdgeEffect hidden_mEdgeGlowTop() {
        return this.mEdgeGlowTop;
    }

    public void semSetFluidScrollerEventListener(SemFluidScrollerEventListener l) {}

    public void semSetFluidScrollerEnabled(boolean enabled) {
        setFastScrollEnabled(enabled);
    }

    public void semSetFluidScrollerStyle(int styleResId) {}

    public boolean semIsFluidScrollerEnabled() {
        return isFastScrollEnabled();
    }

    public boolean semNotifyKeyPressState(View view, int position, long id) {
        if (!this.mIsShiftkeyPressed) {
            return false;
        }
        boolean handledNotifyKeyPress =
                super.semNotifyKeyPress(view, position, id, this.mIsShiftkeyPressed);
        return handledNotifyKeyPress;
    }

    private boolean semToNotifyMultiSelectionState(View view, int position, long id) {
        boolean handledNotifyMultiSelect =
                super.semNotifyMultiSelectedState(
                        view,
                        position,
                        id,
                        this.mIsShiftkeyPressed,
                        this.mIsCtrlkeyPressed,
                        this.mIsPenPressed);
        return handledNotifyMultiSelect;
    }

    public void updateCustomEdgeGlow(
            Drawable edgeeffectCustomEdge, Drawable edgeeffectCustomGlow) {}

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldSkipScroll() {
        return Settings.Global.getInt(getContext().getContentResolver(), "remove_animations", 0)
                == 1;
    }

    private void initIndicator() {
        this.mIndicatorItemCnt = 0;
        this.mIndicatorAnimatedSize =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sem_indicator_radius_focused);
        this.mIndicatorRectSize =
                this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_indicator_rect_size);
        if (this.mIndicatorMarginHorizontal == 0) {
            this.mIndicatorMarginHorizontal =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(R.dimen.sem_indicator_rect_size);
        }
        this.mIndicatorFocusedSize =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sem_indicator_radius_focused);
        this.mIndicatorDefaultSize =
                this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_indicator_radius);
        if (this.mAdapter != null) {
            this.mIndicatorIndex = new ArrayList();
        }
        this.mFocusedPos = getFirstVisiblePosition();
        if (this.mAnimator == null) {
            this.mAnimator =
                    ValueAnimator.ofInt(this.mIndicatorDefaultSize, this.mIndicatorFocusedSize);
            this.mAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class: android.widget.AbsListView.14
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            AbsListView.this.mIndicatorAnimatedSize =
                                    ((Integer) animation.getAnimatedValue()).intValue();
                            AbsListView.this.invalidate();
                        }
                    });
            this.mAnimator.addListener(
                    new Animator.AnimatorListener() { // from class: android.widget.AbsListView.15
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            AbsListView.this.mFocusedPos = AbsListView.this.mNewFocusedPos;
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {}

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animation) {}

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animation) {}
                    });
        }
    }

    private void drawIndicator(Canvas canvas) {
        float radius;
        boolean z;
        int i;
        if (this.mAdapter != null) {
            this.mIndicatorItemCnt = this.mAdapter.getCount();
        }
        if (this.mIndicatorItemCnt < 2) {
            return;
        }
        if (this.mIndicatorPaint == null) {
            this.mIndicatorPaint = new Paint(1);
            this.mIndicatorPaint.setColor(
                    this.mContext.getResources().getColor(R.color.sem_indicator_color));
        }
        if (this.mIndicatorIndex == null) {
            this.mIndicatorIndex = new ArrayList();
        }
        if (!this.mIndicatorIndex.isEmpty()
                && this.mIndicatorIndex.size() != this.mIndicatorItemCnt) {
            this.mIndicatorIndex.clear();
        }
        int scrollY = this.mScrollY;
        int restoreCount = canvas.save();
        canvas.translate(0.0f, scrollY);
        int w = getWidth();
        int h = getHeight();
        int i2 = 20;
        int itemCnt = Math.min(this.mIndicatorItemCnt, 20);
        int halfHeight =
                ((h / 2) - ((this.mIndicatorRectSize * itemCnt) / 2))
                        - this.mIndicatorBottomPadding;
        for (int i3 = 0; i3 < this.mIndicatorItemCnt; i3++) {
            int realIndex = i3;
            if (this.mIndicatorItemCnt > 20 && i3 >= 40 - this.mIndicatorItemCnt) {
                realIndex = (20 - (((this.mIndicatorItemCnt - i3) - 1) / 2)) - 1;
            } else if (40 - this.mIndicatorItemCnt < 0) {
                realIndex = 19;
            }
            this.mIndicatorIndex.add(Integer.valueOf(realIndex));
        }
        int size = this.mIndicatorIndex.size();
        if (size <= this.mFocusedPos || size <= this.mNewFocusedPos) {
            return;
        }
        int i4 = 0;
        while (i4 < this.mIndicatorItemCnt) {
            if (this.mIndicatorIndex.get(i4) == this.mIndicatorIndex.get(this.mFocusedPos)) {
                if (this.mIndicatorIndex.get(this.mFocusedPos)
                        != this.mIndicatorIndex.get(this.mNewFocusedPos)) {
                    radius =
                            (this.mIndicatorFocusedSize
                                            - (this.mIndicatorAnimatedSize
                                                    - this.mIndicatorDefaultSize))
                                    / 2.0f;
                    this.mIndicatorPaint.setAlpha(127);
                } else {
                    radius = this.mIndicatorFocusedSize / 2.0f;
                    this.mIndicatorPaint.setAlpha(255);
                }
            } else if (this.mIndicatorIndex.get(i4)
                    == this.mIndicatorIndex.get(this.mNewFocusedPos)) {
                radius = this.mIndicatorAnimatedSize / 2.0f;
                this.mIndicatorPaint.setAlpha(255);
            } else {
                this.mIndicatorPaint.setAlpha(127);
                radius = this.mIndicatorDefaultSize / 2.0f;
            }
            if (this.mIndicatorItemCnt > i2 && i4 >= 40 - this.mIndicatorItemCnt && i4 % 2 == 0) {
                z = true;
            } else {
                z = true;
                if (this.mIndicatorWhere != 1) {
                    i = w - this.mIndicatorMarginHorizontal;
                } else {
                    i = this.mIndicatorMarginHorizontal;
                }
                canvas.drawCircle(
                        i,
                        halfHeight
                                + (this.mIndicatorRectSize
                                        * (this.mIndicatorIndex.get(i4).intValue() + 0.5f)),
                        radius,
                        this.mIndicatorPaint);
            }
            i4++;
            i2 = 20;
        }
        canvas.restoreToCount(restoreCount);
    }

    private void semSendBroadcastPositionInternal(String component, Intent intent) {
        String[] str = component.split("/");
        if (str.length > 1
                && str[0] != null
                && str[1] != null
                && !str[0].isEmpty()
                && !str[1].isEmpty()) {
            intent.setPackage(str[0]);
            intent.setComponent(new ComponentName(str[0], str[1]));
            if (str.length == 3 && str[2] != null && !str[2].isEmpty()) {
                this.mContext.sendBroadcast(intent, str[2]);
            } else {
                this.mContext.sendBroadcast(intent);
            }
        }
    }

    void semSendBroadcastPosition(int position, int type) {
        if (position < 0) {}
        switch (type) {
            case 1:
                if (!this.mAppWidgetGetCurrentPosition.isEmpty()) {
                    Intent intent = new Intent(APPWIDGET_CURRENT_POSITION_ACTION);
                    intent.putExtra(APPWIDGET_EXTRA_CURRENT_POSITION, position);
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, this.mAppWidgetId);
                    semSendBroadcastPositionInternal(this.mAppWidgetGetCurrentPosition, intent);
                }
                if (semIsTalkBackIsRunning() && Math.abs(this.mFocusedPos - position) == 1) {
                    boolean isScollUp = this.mFocusedPos - position > 0;
                    View child = isScollUp ? getChildAt(0) : getChildAt(1);
                    if (child != null) {
                        child.requestAccessibilityFocus();
                        break;
                    }
                }
                break;
            case 2:
                if (!this.mAppWidgetGetFirstPosition.isEmpty()) {
                    Intent intent2 = new Intent(APPWIDGET_FIRST_POSITION_ACTION);
                    intent2.putExtra(APPWIDGET_EXTRA_FIRST_POSITION, position);
                    intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, this.mAppWidgetId);
                    semSendBroadcastPositionInternal(this.mAppWidgetGetFirstPosition, intent2);
                    break;
                }
                break;
        }
    }

    void semInvalidateIndicator(int position) {
        if (position >= 0 && this.mNewFocusedPos != position) {
            this.mNewFocusedPos = position;
            if (this.mAnimator != null
                    && this.mNewFocusedPos != this.mFocusedPos
                    && this.mIndicatorAnimatedSize == this.mIndicatorFocusedSize) {
                if (this.mAnimator.isRunning()) {
                    this.mAnimator.cancel();
                }
                this.mAnimator.setDuration(200L);
                this.mAnimator.start();
            }
        }
    }

    public void semSetAppWidgetSnapScroll(boolean enabled) {
        this.mAppWidgetSnapScroll = enabled;
    }

    public void semSetAppWidgetEnabled(boolean enabled) {
        this.mAppWidgetEnabled = enabled;
    }

    public void semSetAppWidgetGetCurrentPosition(String component) {
        this.mAppWidgetGetCurrentPosition = component;
    }

    public void semSetAppWidgetGetFirstPosition(String component) {
        this.mAppWidgetGetFirstPosition = component;
        semSendBroadcastPosition(this.mFirstPosition, 2);
    }

    public void semSetAppWidgetIndicator(boolean enabled) {
        this.mAppWidgetIndicator = enabled;
    }

    public void semSetAppWidgetIndicatorBottomPadding(int bottomPadding) {
        this.mIndicatorBottomPadding = bottomPadding;
    }

    public void semSetAppWidgetIndicatorMarginHorizontal(int marginHorizontal) {
        this.mIndicatorMarginHorizontal = marginHorizontal;
    }

    public void semSetAppWidgetIndicatorWhere(int where) {
        this.mIndicatorWhere = where;
    }

    @Override // android.view.View
    public void semSetScrollBarBottomPadding(int scrollBarBottomPadding) {
        if (this.mSemFastScroll != null) {
            this.mSemFastScroll.semSetScrollBarBottomPadding(scrollBarBottomPadding);
        } else {
            super.semSetScrollBarBottomPadding(scrollBarBottomPadding);
        }
    }

    @Override // android.view.View
    public void semSetScrollBarTopPadding(int scrollBarTopPadding) {
        if (this.mSemFastScroll != null) {
            this.mSemFastScroll.semSetScrollBarTopPadding(scrollBarTopPadding);
        } else {
            super.semSetScrollBarTopPadding(scrollBarTopPadding);
        }
    }

    public boolean semHandleGenericMotionEvent(int direction) {
        return false;
    }

    public void semSetAppWidgetInnerFocus(boolean innerFocus) {
        this.mAppWidgetInnerFocus = innerFocus;
    }

    public void semSetAppWidgetImmersiveEnabled(boolean enabled) {
        this.mAppWidgetImmersiveEnalbed = enabled;
    }

    void viewSelectorLikeFocus(View sel) {
        Rect clickableRect = this.mSelectorRect;
        clickableRect.set(sel.getLeft(), sel.getTop(), sel.getRight(), sel.getBottom());
        View child = getChildAt(this.mSelectorPosition - this.mFirstPosition);
        if (child != null) {
            clickableRect.top += child.getTop();
            clickableRect.bottom += child.getTop();
        }
        Drawable selector = this.mSelector;
        if (selector != null) {
            selector.setVisible(false, false);
            selector.setState(StateSet.NOTHING);
            selector.setBounds(clickableRect);
            if (getVisibility() == 0) {
                selector.setVisible(true, false);
            }
            updateSelectorState();
        }
    }

    public void semSetAppWidgetNeedLayoutSpecificDone(boolean needLayoutSpecificDone) {
        this.mNeedLayoutSpecificDone = needLayoutSpecificDone;
    }

    private class DifferentialFlingTarget
            implements DifferentialMotionFlingHelper.DifferentialMotionFlingTarget {
        private DifferentialFlingTarget() {}

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public boolean startDifferentialMotionFling(float velocity) {
            stopDifferentialMotionFling();
            AbsListView.this.fling((int) velocity);
            return true;
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public void stopDifferentialMotionFling() {
            if (AbsListView.this.mFlingRunnable != null) {
                AbsListView.this.mFlingRunnable.endFling();
            }
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public float getScaledScrollFactor() {
            return -AbsListView.this.mVerticalScrollFactor;
        }
    }
}
