package android.widget;

import android.app.KeyguardManager;
import android.app.slice.Slice;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.Settings;
import android.text.Editable;
import android.text.MultiSelection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InputMethodManager;

import com.android.internal.R;

import com.samsung.android.os.SemPerfManager;
import com.samsung.android.widget.SemHorizontalFastScroller;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class SemHorizontalAbsListView extends AdapterView<ListAdapter>
        implements TextWatcher,
                ViewTreeObserver.OnGlobalLayoutListener,
                Filter.FilterListener,
                ViewTreeObserver.OnTouchModeChangeListener,
                RemoteViewsAdapter.RemoteAdapterConnectionCallback {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;

    @Deprecated public static final int CHOICE_MODE_MULTIPLE = 2;

    @Deprecated public static final int CHOICE_MODE_MULTIPLE_MODAL = 3;

    @Deprecated public static final int CHOICE_MODE_NONE = 0;

    @Deprecated public static final int CHOICE_MODE_SINGLE = 1;
    private static final boolean DEBUG = false;
    private static final int DRAGSCROLL_WORKING_ZONE_DP = 25;
    private static final int HOVERSCROLL_LEFT = 1;
    private static final int HOVERSCROLL_RIGHT = 2;
    private static final int HOVERSCROLL_WIDTH_LEFT_DP = 25;
    private static final int HOVERSCROLL_WIDTH_RIGHT_DP = 25;
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
    private static final String SAVED_STATE_KEY_FOR_BUNDLE =
            "android.widget.SemHorizontalAbsListView.SavedState";
    private static final String TAG = "SemHorizontalAbsListView";
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

    @Deprecated public static final int TRANSCRIPT_MODE_ALWAYS_SCROLL = 2;

    @Deprecated public static final int TRANSCRIPT_MODE_DISABLED = 0;

    @Deprecated public static final int TRANSCRIPT_MODE_NORMAL = 1;
    private int HOVERSCROLL_DELAY;
    private float HOVERSCROLL_SPEED;
    private ListItemAccessibilityDelegate mAccessibilityDelegate;
    private int mActivePointerId;
    ListAdapter mAdapter;
    boolean mAdapterHasStableIds;
    private int mCacheColorHint;
    boolean mCachingActive;
    boolean mCachingStarted;
    SparseBooleanArray mCheckStates;
    LongSparseArray<Integer> mCheckedIdStates;
    int mCheckedItemCount;
    ActionMode mChoiceActionMode;
    int mChoiceMode;
    private Runnable mClearScrollingCache;
    private ContextMenu.ContextMenuInfo mContextMenuInfo;
    private int mCurrentKeyCode;
    private boolean mDVFSLockAcquired;
    AdapterDataSetObserver mDataSetObserver;
    private InputConnection mDefInputConnection;
    private boolean mDeferNotifyDataSetChanged;
    private float mDensityScale;
    private int mDirection;
    private int mDragScrollWorkingZonePx;
    boolean mDrawSelectorOnTop;
    private EdgeEffect mEdgeGlowLeft;
    private EdgeEffect mEdgeGlowRight;
    private boolean mEnableVibrationAtLongPress;
    private int mExtraPaddingInLeftHoverArea;
    private int mExtraPaddingInRightHoverArea;
    private SemHorizontalFastScroller mFastScroll;
    boolean mFastScrollAlwaysVisible;
    boolean mFastScrollEnabled;
    private int mFastScrollStyle;
    private boolean mFiltered;
    private int mFirstPositionDistanceGuess;
    private int mFirstPressedPoint;
    private boolean mFlingProfilingStarted;
    private FlingRunnable mFlingRunnable;
    private StrictMode.Span mFlingStrictSpan;
    private boolean mForceTranscriptScroll;
    private boolean mForcedClick;
    private boolean mGlobalLayoutListenerAddedFilter;
    private int mGlowPaddingBottom;
    private int mGlowPaddingTop;
    private boolean mHapticOverScroll;
    private boolean mHasWindowFocusForMotion;
    int mHeightMeasureSpec;
    public boolean mHoverAreaEnter;
    private HoverScrollHandler mHoverHandler;
    private int mHoverLeftAreaWidth;
    private int mHoverPosition;
    private long mHoverRecognitionCurrentTime;
    private long mHoverRecognitionDurationTime;
    private long mHoverRecognitionStartTime;
    private int mHoverRightAreaWidth;
    private int mHoverScrollDirection;
    private boolean mHoverScrollEnable;
    private int mHoverScrollSpeed;
    private long mHoverScrollStartTime;
    private boolean mHoverScrollStateChanged;
    private int mHoverScrollStateForListener;
    private long mHoverScrollTimeInterval;
    private boolean mHoveredOnEllipsizedText;
    boolean mHoveringEnabled;
    private boolean mIsChildViewEnabled;
    private boolean mIsCloseChildSetted;
    private boolean mIsCtrlkeyPressed;
    private boolean mIsDetaching;
    private boolean mIsDragBlockEnabled;
    private boolean mIsDragScrolled;
    private boolean mIsEnabledPaddingInHoverScroll;
    private boolean mIsHoverOverscrolled;
    private boolean mIsHoveredByMouse;
    private boolean mIsMultiFocusEnabled;
    private boolean mIsNeedPenSelectIconSet;
    private boolean mIsNeedPenSelection;
    private boolean mIsPenHovered;
    private boolean mIsPenPressed;
    private boolean mIsPenSelectPointerSetted;
    boolean mIsRTL;
    final boolean[] mIsScrap;
    private boolean mIsSendHoverScrollState;
    private boolean mIsShiftkeyPressed;
    private boolean mIsTextSelectionStarted;
    private boolean mIsfirstMoveEvent;
    private int mJumpScrollToTopState;
    private int mLastAccessibilityScrollEventFromIndex;
    private int mLastAccessibilityScrollEventToIndex;
    private int mLastHandledItemCount;
    private int mLastPosition;
    private int mLastPositionDistanceGuess;
    int mLastScrollState;
    private int mLastTouchMode;
    int mLastX;
    int mLayoutMode;
    Rect mListPadding;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    int mMotionCorrection;
    int mMotionPosition;
    int mMotionViewNewLeft;
    int mMotionViewOriginalLeft;
    int mMotionX;
    int mMotionY;
    MultiChoiceModeWrapper mMultiChoiceModeCallback;
    private Drawable mMultiFocusImage;
    private boolean mNeedsHoverScroll;
    private int mNestedXOffset;
    private boolean mNewTextViewHoverState;
    private int mOldAdapterItemCount;
    private int mOldHoverScrollDirection;
    private int mOldKeyCode;
    private boolean mOldTextViewHoverState;
    private OnScrollListener mOnScrollListener;
    int mOverflingDistance;
    int mOverscrollDistance;
    int mOverscrollMax;
    private final Thread mOwnerThread;
    private long mPenDragScrollTimeInterval;
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
    int mResurrectToPosition;
    private final int[] mScrollConsumed;
    View mScrollLeft;
    private final int[] mScrollOffset;
    private boolean mScrollProfilingStarted;
    View mScrollRight;
    private StrictMode.Span mScrollStrictSpan;
    boolean mScrollingCacheEnabled;
    private int mSecondPressedPoint;
    int mSelectedLeft;
    int mSelectionBottomPadding;
    int mSelectionLeftPadding;
    int mSelectionRightPadding;
    int mSelectionTopPadding;
    Drawable mSelector;
    int mSelectorPosition;
    Rect mSelectorRect;
    private View mSemCloseChildByLeft;
    private View mSemCloseChildByRight;
    private int mSemCloseChildPositionByLeft;
    private int mSemCloseChildPositionByRight;
    protected int mSemCurrentFocusPosition;
    private boolean mSemCustomMultiChoiceMode;
    private int mSemDistanceFromCloseChildLeft;
    private int mSemDistanceFromCloseChildRight;
    private int mSemDistanceFromTrackedChildLeft;
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
    private boolean mSemIsOnClickEnabled;
    private ArrayList<Integer> mSemPressItemListArray;
    private LinkedList<Integer> mSemScrollRemains;
    private SemSmoothScrollByMove mSemSmoothScrollByMove;
    private View mSemTrackedChild;
    private int mSemTrackedChildPosition;
    private boolean mSmoothScrollbarEnabled;
    boolean mStackFromBottom;
    EditText mTextFilter;
    private boolean mTextFilterEnabled;
    private Rect mTouchFrame;
    int mTouchMode;
    private Runnable mTouchModeReset;
    private int mTouchSlop;
    private int mTranscriptMode;
    private float mVelocityScale;
    private VelocityTracker mVelocityTracker;
    private static boolean DEBUG_VELOCITY_TRACKER_TRACE = false;
    static final Interpolator sLinearInterpolator = new LinearInterpolator();
    private static int JUMP_SCROLL_TO_TOP_IDLE = 0;
    private static int JUMP_SCROLL_TO_TOP_INITIATED = 1;
    private static int JUMP_SCROLL_TO_TOP_FINISHING = 2;
    private static int mSemScrollAmount = 500;

    @Deprecated
    public interface MultiChoiceModeListener extends ActionMode.Callback {
        @Deprecated
        void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z);
    }

    @Deprecated
    public interface OnScrollListener {

        @Deprecated public static final int SCROLL_STATE_FLING = 2;

        @Deprecated public static final int SCROLL_STATE_IDLE = 0;

        @Deprecated public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        @Deprecated
        void onScroll(SemHorizontalAbsListView semHorizontalAbsListView, int i, int i2, int i3);

        @Deprecated
        void onScrollStateChanged(SemHorizontalAbsListView semHorizontalAbsListView, int i);
    }

    @Deprecated
    public interface RecyclerListener {
        @Deprecated
        void onMovedToScrapHeap(View view);
    }

    abstract void fillGap(boolean z);

    abstract void fillGapRTL(boolean z);

    abstract int findMotionRow(int i);

    abstract void setSelectionInt(int i);

    private boolean semGetEnableVibrationAtLongPress() {
        return this.mEnableVibrationAtLongPress;
    }

    public void semSetEnableVibrationAtLongPress(boolean enable) {
        this.mEnableVibrationAtLongPress = enable;
    }

    private void releaseAllBoosters() {
        if (this.mDVFSLockAcquired) {
            SemPerfManager.onScrollEvent(false);
            this.mDVFSLockAcquired = false;
        }
    }

    public void setTiltMotionEvent(boolean flag) {}

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public void setTouchSlop(int value) {
        this.mTouchSlop = value;
    }

    public void setEnableHoverDrawable(boolean enable) {
        this.mHoveringEnabled = enable;
    }

    public void updateCustomEdgeGlow(
            Drawable edgeeffectCustomEdge, Drawable edgeeffectCustomGlow) {}

    @Deprecated
    public SemHorizontalAbsListView(Context context) {
        super(context);
        this.mHasWindowFocusForMotion = false;
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
        this.mHeightMeasureSpec = 0;
        this.mTouchMode = -1;
        this.mSelectedLeft = 0;
        this.mSmoothScrollbarEnabled = true;
        this.mResurrectToPosition = -1;
        this.mContextMenuInfo = null;
        this.mLastTouchMode = -1;
        this.mScrollProfilingStarted = false;
        this.mFlingProfilingStarted = false;
        this.mScrollStrictSpan = null;
        this.mFlingStrictSpan = null;
        this.mLastScrollState = 0;
        this.mVelocityScale = 1.0f;
        this.mIsScrap = new boolean[1];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mNestedXOffset = 0;
        this.mActivePointerId = -1;
        this.mPointerCount = 0;
        this.mHapticOverScroll = false;
        this.mDirection = 0;
        this.mHoverLeftAreaWidth = 0;
        this.mHoverRightAreaWidth = 0;
        this.mHoverRecognitionDurationTime = 0L;
        this.mHoverRecognitionCurrentTime = 0L;
        this.mHoverRecognitionStartTime = 0L;
        this.mHoverScrollTimeInterval = 300L;
        this.mPenDragScrollTimeInterval = 500L;
        this.mHoverScrollStartTime = 0L;
        this.mHoverScrollDirection = -1;
        this.mIsHoverOverscrolled = false;
        this.mHoverScrollEnable = true;
        this.mHoverScrollStateChanged = false;
        this.mHoverAreaEnter = false;
        this.mIsSendHoverScrollState = false;
        this.HOVERSCROLL_SPEED = 6.0f;
        this.HOVERSCROLL_DELAY = 0;
        this.mNeedsHoverScroll = false;
        this.mHoverScrollStateForListener = 0;
        this.mIsEnabledPaddingInHoverScroll = false;
        this.mHoveringEnabled = true;
        this.mExtraPaddingInLeftHoverArea = 0;
        this.mExtraPaddingInRightHoverArea = 0;
        this.mEnableVibrationAtLongPress = true;
        this.mSemCustomMultiChoiceMode = false;
        this.mIsCtrlkeyPressed = false;
        this.mIsShiftkeyPressed = false;
        this.mIsPenHovered = false;
        this.mIsPenPressed = false;
        this.mIsfirstMoveEvent = true;
        this.mIsMultiFocusEnabled = false;
        this.mFirstPressedPoint = -1;
        this.mSecondPressedPoint = -1;
        this.mOldAdapterItemCount = 0;
        this.mOldKeyCode = 0;
        this.mCurrentKeyCode = 0;
        this.mSemCurrentFocusPosition = -1;
        this.mIsTextSelectionStarted = false;
        this.mIsNeedPenSelection = false;
        this.mSemDragSelectedItemSize = 0;
        this.mSemDragSelectedViewPosition = -1;
        this.mIsPenSelectPointerSetted = false;
        this.mIsNeedPenSelectIconSet = false;
        this.mOldTextViewHoverState = false;
        this.mNewTextViewHoverState = false;
        this.mPreviousTextViewScroll = false;
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
        this.mSemDistanceFromTrackedChildLeft = 0;
        this.mIsCloseChildSetted = false;
        this.mOldHoverScrollDirection = -1;
        this.mSemCloseChildByLeft = null;
        this.mSemCloseChildPositionByLeft = -1;
        this.mSemDistanceFromCloseChildLeft = 0;
        this.mSemCloseChildByRight = null;
        this.mSemCloseChildPositionByRight = -1;
        this.mSemDistanceFromCloseChildRight = 0;
        this.mSemDragBlockRect = new Rect();
        this.mSemIsOnClickEnabled = true;
        this.mIsRTL = false;
        this.mDVFSLockAcquired = false;
        this.mForcedClick = false;
        this.mDragScrollWorkingZonePx = 0;
        this.mIsDragScrolled = false;
        this.mJumpScrollToTopState = JUMP_SCROLL_TO_TOP_IDLE;
        this.mHoverPosition = -1;
        this.mHoveredOnEllipsizedText = false;
        this.mIsHoveredByMouse = false;
        this.mSemSmoothScrollByMove = null;
        this.mSemScrollRemains = null;
        this.mHoverScrollSpeed = 0;
        initAbsListView();
        this.mOwnerThread = Thread.currentThread();
        setHorizontalScrollBarEnabled(true);
        TypedArray a = context.obtainStyledAttributes(R.styleable.View);
        initializeScrollbarsInternal(a);
        a.recycle();
    }

    @Deprecated
    public SemHorizontalAbsListView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842858);
    }

    @Deprecated
    public SemHorizontalAbsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @Deprecated
    public SemHorizontalAbsListView(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mHasWindowFocusForMotion = false;
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
        this.mHeightMeasureSpec = 0;
        this.mTouchMode = -1;
        this.mSelectedLeft = 0;
        this.mSmoothScrollbarEnabled = true;
        this.mResurrectToPosition = -1;
        this.mContextMenuInfo = null;
        this.mLastTouchMode = -1;
        this.mScrollProfilingStarted = false;
        this.mFlingProfilingStarted = false;
        this.mScrollStrictSpan = null;
        this.mFlingStrictSpan = null;
        this.mLastScrollState = 0;
        this.mVelocityScale = 1.0f;
        this.mIsScrap = new boolean[1];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mNestedXOffset = 0;
        this.mActivePointerId = -1;
        this.mPointerCount = 0;
        this.mHapticOverScroll = false;
        this.mDirection = 0;
        this.mHoverLeftAreaWidth = 0;
        this.mHoverRightAreaWidth = 0;
        this.mHoverRecognitionDurationTime = 0L;
        this.mHoverRecognitionCurrentTime = 0L;
        this.mHoverRecognitionStartTime = 0L;
        this.mHoverScrollTimeInterval = 300L;
        this.mPenDragScrollTimeInterval = 500L;
        this.mHoverScrollStartTime = 0L;
        this.mHoverScrollDirection = -1;
        this.mIsHoverOverscrolled = false;
        this.mHoverScrollEnable = true;
        this.mHoverScrollStateChanged = false;
        this.mHoverAreaEnter = false;
        this.mIsSendHoverScrollState = false;
        this.HOVERSCROLL_SPEED = 6.0f;
        this.HOVERSCROLL_DELAY = 0;
        this.mNeedsHoverScroll = false;
        this.mHoverScrollStateForListener = 0;
        this.mIsEnabledPaddingInHoverScroll = false;
        this.mHoveringEnabled = true;
        this.mExtraPaddingInLeftHoverArea = 0;
        this.mExtraPaddingInRightHoverArea = 0;
        this.mEnableVibrationAtLongPress = true;
        this.mSemCustomMultiChoiceMode = false;
        this.mIsCtrlkeyPressed = false;
        this.mIsShiftkeyPressed = false;
        this.mIsPenHovered = false;
        this.mIsPenPressed = false;
        this.mIsfirstMoveEvent = true;
        this.mIsMultiFocusEnabled = false;
        this.mFirstPressedPoint = -1;
        this.mSecondPressedPoint = -1;
        this.mOldAdapterItemCount = 0;
        this.mOldKeyCode = 0;
        this.mCurrentKeyCode = 0;
        this.mSemCurrentFocusPosition = -1;
        this.mIsTextSelectionStarted = false;
        this.mIsNeedPenSelection = false;
        this.mSemDragSelectedItemSize = 0;
        this.mSemDragSelectedViewPosition = -1;
        this.mIsPenSelectPointerSetted = false;
        this.mIsNeedPenSelectIconSet = false;
        this.mOldTextViewHoverState = false;
        this.mNewTextViewHoverState = false;
        this.mPreviousTextViewScroll = false;
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
        this.mSemDistanceFromTrackedChildLeft = 0;
        this.mIsCloseChildSetted = false;
        this.mOldHoverScrollDirection = -1;
        this.mSemCloseChildByLeft = null;
        this.mSemCloseChildPositionByLeft = -1;
        this.mSemDistanceFromCloseChildLeft = 0;
        this.mSemCloseChildByRight = null;
        this.mSemCloseChildPositionByRight = -1;
        this.mSemDistanceFromCloseChildRight = 0;
        this.mSemDragBlockRect = new Rect();
        this.mSemIsOnClickEnabled = true;
        this.mIsRTL = false;
        this.mDVFSLockAcquired = false;
        this.mForcedClick = false;
        this.mDragScrollWorkingZonePx = 0;
        this.mIsDragScrolled = false;
        this.mJumpScrollToTopState = JUMP_SCROLL_TO_TOP_IDLE;
        this.mHoverPosition = -1;
        this.mHoveredOnEllipsizedText = false;
        this.mIsHoveredByMouse = false;
        this.mSemSmoothScrollByMove = null;
        this.mSemScrollRemains = null;
        this.mHoverScrollSpeed = 0;
        initAbsListView();
        this.mOwnerThread = Thread.currentThread();
        TypedArray a =
                context.obtainStyledAttributes(
                        attrs, R.styleable.AbsListView, defStyleAttr, defStyleRes);
        Drawable d = a.getDrawable(0);
        if (d != null) {
            setSelector(d);
        }
        this.mDrawSelectorOnTop = a.getBoolean(1, false);
        boolean stackFromBottom = a.getBoolean(2, false);
        setStackFromBottom(stackFromBottom);
        boolean scrollingCacheEnabled = a.getBoolean(3, true);
        setScrollingCacheEnabled(scrollingCacheEnabled);
        boolean useTextFilter = a.getBoolean(4, false);
        setTextFilterEnabled(useTextFilter);
        int transcriptMode = a.getInt(5, 0);
        setTranscriptMode(transcriptMode);
        boolean enableFastScroll = a.getBoolean(8, false);
        setFastScrollEnabled(enableFastScroll);
        int fastScrollStyle = a.getResourceId(11, 0);
        setFastScrollStyle(fastScrollStyle);
        boolean smoothScrollbar = a.getBoolean(9, true);
        setSmoothScrollbarEnabled(smoothScrollbar);
        setFastScrollAlwaysVisible(a.getBoolean(10, false));
        a.recycle();
    }

    private void initAbsListView() {
        setClickable(true);
        setFocusableInTouchMode(true);
        setWillNotDraw(false);
        setAlwaysDrawnWithCacheEnabled(false);
        setScrollingCacheEnabled(true);
        semEnableHorizontalScrollbar();
        ViewConfiguration configuration = ViewConfiguration.get(this.mContext);
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.mOverscrollDistance = configuration.getScaledOverscrollDistance();
        this.mOverflingDistance = configuration.getScaledOverflingDistance();
        this.mDensityScale = this.mContext.getResources().getDisplayMetrics().density;
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
    }

    @Override // android.view.View
    @Deprecated
    public void setOverScrollMode(int mode) {
        if (mode != 2) {
            if (this.mEdgeGlowLeft == null) {
                Context context = getContext();
                this.mEdgeGlowLeft = new EdgeEffect(context);
                this.mEdgeGlowRight = new EdgeEffect(context);
                this.mEdgeGlowLeft.semSetHostView(this, false);
                this.mEdgeGlowRight.semSetHostView(this, false);
            }
        } else {
            this.mEdgeGlowLeft = null;
            this.mEdgeGlowRight = null;
        }
        super.setOverScrollMode(mode);
    }

    @Deprecated
    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    @Deprecated
    public boolean isItemChecked(int position) {
        if (this.mChoiceMode != 0 && this.mCheckStates != null) {
            return this.mCheckStates.get(position);
        }
        return false;
    }

    @Deprecated
    public int getCheckedItemPosition() {
        if (this.mChoiceMode == 1 && this.mCheckStates != null && this.mCheckStates.size() == 1) {
            return this.mCheckStates.keyAt(0);
        }
        return -1;
    }

    @Deprecated
    public SparseBooleanArray getCheckedItemPositions() {
        if (this.mChoiceMode != 0) {
            return this.mCheckStates;
        }
        return null;
    }

    @Deprecated
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

    @Deprecated
    public void clearChoices() {
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        this.mCheckedItemCount = 0;
    }

    @Deprecated
    public void setItemChecked(int position, boolean value) {
        if (this.mChoiceMode == 0) {
            return;
        }
        if (value && this.mChoiceMode == 3 && this.mChoiceActionMode == null) {
            if (this.mMultiChoiceModeCallback == null
                    || !this.mMultiChoiceModeCallback.hasWrappedCallback()) {
                throw new IllegalStateException(
                        "SemHorizontalAbsListView: attempted to start selection mode for"
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
            if (oldValue != value) {
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
        if (!this.mInLayout && !this.mBlockLayoutRequests) {
            if (!this.mForcedClick) {
                this.mDataChanged = true;
            }
            rememberSyncState();
            requestLayout();
        }
    }

    @Override // android.widget.AdapterView
    @Deprecated
    public boolean performItemClick(View view, int position, long id) {
        boolean handled = false;
        boolean dispatchItemClick = true;
        if (this.mChoiceMode != 0) {
            boolean checkedStateChanged = false;
            if (this.mCheckStates == null
                    || (this.mChoiceMode != 2
                            && (this.mChoiceMode != 3 || this.mChoiceActionMode == null))) {
                if (this.mCheckStates != null && this.mChoiceMode == 1) {
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
            } else {
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

    public boolean semNotifyKeyPressState(View view, int position, long id) {
        return this.mIsShiftkeyPressed
                && super.semNotifyKeyPress(view, position, id, this.mIsShiftkeyPressed);
    }

    private boolean semNotifyMultiSelectState(View view, int position, long id) {
        return super.semNotifyMultiSelectedState(
                view,
                position,
                id,
                this.mIsShiftkeyPressed,
                this.mIsCtrlkeyPressed,
                this.mIsPenPressed);
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

    @Deprecated
    public int getChoiceMode() {
        return this.mChoiceMode;
    }

    @Deprecated
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

    @Deprecated
    public void setMultiChoiceModeListener(MultiChoiceModeListener listener) {
        if (this.mMultiChoiceModeCallback == null) {
            this.mMultiChoiceModeCallback = new MultiChoiceModeWrapper();
        }
        this.mMultiChoiceModeCallback.setWrapped(listener);
    }

    @Deprecated
    public void startMultiChoiceMode() {
        if (this.mChoiceMode == 3 && this.mMultiChoiceModeCallback != null) {
            this.mChoiceActionMode = startActionMode(this.mMultiChoiceModeCallback);
        }
    }

    @Deprecated
    public void finishMultiChoiceMode() {
        if (this.mChoiceActionMode != null) {
            this.mChoiceActionMode.finish();
            this.mChoiceActionMode = null;
        }
    }

    public void semSetCustomMultiChoiceMode(boolean enable) {
        this.mSemCustomMultiChoiceMode = enable;
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
        return this.mIsRTL
                ? getChildAt(0).getRight() <= getWidth() - this.mListPadding.right
                        && getChildAt(childCount + (-1)).getLeft() >= this.mListPadding.left
                : getChildAt(0).getLeft() >= this.mListPadding.left
                        && getChildAt(childCount + (-1)).getRight()
                                <= getWidth() - this.mListPadding.right;
    }

    @Deprecated
    public void setFastScrollEnabled(boolean enabled) {
        if (this.mFastScrollEnabled != enabled) {
            this.mFastScrollEnabled = enabled;
            setFastScrollerEnabledUiThread(enabled);
        }
    }

    private void setFastScrollerEnabledUiThread(boolean enabled) {
        if (this.mFastScroll != null) {
            this.mFastScroll.setEnabled(enabled);
        } else if (enabled) {
            this.mFastScroll = new SemHorizontalFastScroller(this, this.mFastScrollStyle);
            this.mFastScroll.setEnabled(true);
        }
        resolvePadding();
        if (this.mFastScroll != null) {
            this.mFastScroll.updateLayout();
        }
    }

    @Deprecated
    public void setFastScrollStyle(int styleResId) {
        if (this.mFastScroll == null) {
            this.mFastScrollStyle = styleResId;
        } else {
            this.mFastScroll.setStyle(styleResId);
        }
    }

    @Deprecated
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
                        new Runnable() { // from class: android.widget.SemHorizontalAbsListView.1
                            @Override // java.lang.Runnable
                            public void run() {
                                SemHorizontalAbsListView.this.setFastScrollerAlwaysVisibleUiThread(
                                        alwaysShow);
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

    @ViewDebug.ExportedProperty
    @Deprecated
    public boolean isFastScrollAlwaysVisible() {
        return this.mFastScroll == null
                ? this.mFastScrollEnabled && this.mFastScrollAlwaysVisible
                : this.mFastScroll.isEnabled() && this.mFastScroll.isAlwaysShowEnabled();
    }

    @Override // android.view.View
    @Deprecated
    public int getHorizontalScrollbarHeight() {
        if (this.mFastScroll != null && this.mFastScroll.isEnabled()) {
            return Math.max(super.getHorizontalScrollbarHeight(), this.mFastScroll.getHeight());
        }
        return super.getHorizontalScrollbarHeight();
    }

    @ViewDebug.ExportedProperty
    @Deprecated
    public boolean isFastScrollEnabled() {
        if (this.mFastScroll == null) {
            return this.mFastScrollEnabled;
        }
        return this.mFastScroll.isEnabled();
    }

    @Override // android.view.View
    @Deprecated
    public void setScrollBarStyle(int style) {
        super.setScrollBarStyle(style);
        if (this.mFastScroll != null) {
            this.mFastScroll.setScrollBarStyle(style);
        }
    }

    @Override // android.view.View
    protected int semGetScaledMinScrollbarTouchTarget(ViewConfiguration configuration) {
        return 0;
    }

    @Override // android.view.View
    protected boolean semIsHorizontalScrollBarHidden() {
        return isFastScrollEnabled();
    }

    @Deprecated
    public void setSmoothScrollbarEnabled(boolean enabled) {
        this.mSmoothScrollbarEnabled = enabled;
    }

    public boolean isMultiWindows() {
        return "1".equals(SystemProperties.get("sys.multiwindow.running"));
    }

    @ViewDebug.ExportedProperty
    @Deprecated
    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener l) {
        this.mOnScrollListener = l;
        invokeOnItemScrollListener();
    }

    void invokeOnItemScrollListener() {
        if (this.mFastScroll != null) {
            this.mFastScroll.onScroll(this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(
                    this, this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        onScrollChanged(0, 0, 0, 0);
    }

    @Override // android.view.View
    @Deprecated
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(SemHorizontalAbsListView.class.getName());
    }

    @Override // android.view.View
    @Deprecated
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(SemHorizontalAbsListView.class.getName());
        if (isEnabled()) {
            if (getFirstVisiblePosition() > 0) {
                info.addAction(8192);
                info.setScrollable(true);
            }
            if (getLastVisiblePosition() < getCount() - 1) {
                info.addAction(4096);
                info.setScrollable(true);
            }
        }
    }

    int getSelectionModeForAccessibility() {
        int choiceMode = getChoiceMode();
        switch (choiceMode) {
        }
        return 0;
    }

    @Override // android.view.View
    @Deprecated
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (super.performAccessibilityAction(action, arguments)) {
            return true;
        }
        switch (action) {
            case 4096:
                if (!isEnabled() || getLastVisiblePosition() >= getCount() - 1) {
                    return false;
                }
                int viewportWidth = (getWidth() - this.mListPadding.left) - this.mListPadding.right;
                smoothScrollBy(viewportWidth, 200);
                return true;
            case 8192:
                if (!isEnabled() || this.mFirstPosition <= 0) {
                    return false;
                }
                int viewportWidth2 =
                        (getWidth() - this.mListPadding.left) - this.mListPadding.right;
                smoothScrollBy(-viewportWidth2, 200);
                return true;
            default:
                return false;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public View findViewByAccessibilityIdTraversal(int accessibilityId) {
        if (accessibilityId == getAccessibilityViewId()) {
            return this;
        }
        if (this.mDataChanged) {
            return null;
        }
        return super.findViewByAccessibilityIdTraversal(accessibilityId);
    }

    @ViewDebug.ExportedProperty
    @Deprecated
    public boolean isScrollingCacheEnabled() {
        return this.mScrollingCacheEnabled;
    }

    @Deprecated
    public void setScrollingCacheEnabled(boolean enabled) {
        if (this.mScrollingCacheEnabled && !enabled) {
            clearScrollingCache();
        }
        this.mScrollingCacheEnabled = enabled;
    }

    @Deprecated
    public void setTextFilterEnabled(boolean textFilterEnabled) {
        this.mTextFilterEnabled = textFilterEnabled;
    }

    @ViewDebug.ExportedProperty
    @Deprecated
    public boolean isTextFilterEnabled() {
        return this.mTextFilterEnabled;
    }

    @Override // android.view.View
    @Deprecated
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
    @Deprecated
    public boolean isStackFromBottom() {
        return this.mStackFromBottom;
    }

    @Deprecated
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
                        SavedState>() { // from class:
                                        // android.widget.SemHorizontalAbsListView.SavedState.1
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
        boolean inActionMode;
        int position;
        long selectedId;
        int viewLeft;
        int width;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.selectedId = in.readLong();
            this.firstId = in.readLong();
            this.viewLeft = in.readInt();
            this.position = in.readInt();
            this.width = in.readInt();
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
            parcel.writeInt(this.viewLeft);
            parcel.writeInt(this.position);
            parcel.writeInt(this.width);
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
            return "SemHorizontalAbsListView.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " selectedId="
                    + this.selectedId
                    + " firstId="
                    + this.firstId
                    + " viewLeft="
                    + this.viewLeft
                    + " position="
                    + this.position
                    + " width="
                    + this.width
                    + " filter="
                    + this.filter
                    + " checkState="
                    + this.checkState
                    + "}";
        }
    }

    @Override // android.view.View
    @Deprecated
    public Parcelable onSaveInstanceState() {
        EditText textFilter;
        Editable filterText;
        dismissPopup();
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        if (this.mPendingSync != null) {
            ss.selectedId = this.mPendingSync.selectedId;
            ss.firstId = this.mPendingSync.firstId;
            ss.viewLeft = this.mPendingSync.viewLeft;
            ss.position = this.mPendingSync.position;
            ss.width = this.mPendingSync.width;
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
        ss.width = getWidth();
        if (selectedId >= 0) {
            ss.viewLeft = this.mSelectedLeft;
            ss.position = getSelectedItemPosition();
            ss.firstId = -1L;
        } else if (haveChildren && this.mFirstPosition > 0) {
            View v = getChildAt(0);
            ss.viewLeft = v.getLeft();
            int firstPos = this.mFirstPosition;
            if (firstPos >= this.mItemCount) {
                firstPos = this.mItemCount - 1;
            }
            ss.position = firstPos;
            ss.firstId = this.mAdapter.getItemId(firstPos);
        } else {
            ss.viewLeft = 0;
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
        Bundle wrappedSavedState = new Bundle();
        wrappedSavedState.putParcelable(SAVED_STATE_KEY_FOR_BUNDLE, ss);
        return wrappedSavedState;
    }

    @Override // android.view.View
    @Deprecated
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss;
        if (state instanceof SavedState) {
            ss = (SavedState) state;
        } else if (state instanceof Bundle) {
            Bundle wrappedSavedState = (Bundle) state;
            wrappedSavedState.setClassLoader(SavedState.class.getClassLoader());
            ss = (SavedState) wrappedSavedState.getParcelable(SAVED_STATE_KEY_FOR_BUNDLE);
        } else {
            Log.e(
                    TAG,
                    "SemHorizontalAbsListView.onRestoreInstanceState() is of neither SavedState"
                        + " type nor Bundle type, but of "
                            + state.getClass().toString()
                            + " type");
            super.onRestoreInstanceState(state);
            return;
        }
        super.onRestoreInstanceState(ss.getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = ss.width;
        if (ss.selectedId >= 0) {
            this.mNeedSync = true;
            this.mPendingSync = ss;
            this.mSyncRowId = ss.selectedId;
            this.mSyncPosition = ss.position;
            this.mSpecificTop = ss.viewLeft;
            this.mSyncMode = 0;
        } else if (ss.firstId >= 0) {
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectorPosition = -1;
            this.mNeedSync = true;
            this.mPendingSync = ss;
            this.mSyncRowId = ss.firstId;
            this.mSyncPosition = ss.position;
            this.mSpecificTop = ss.viewLeft;
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

    @Deprecated
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

    @Deprecated
    public CharSequence getTextFilter() {
        if (this.mTextFilterEnabled && this.mTextFilter != null) {
            return this.mTextFilter.getText();
        }
        return null;
    }

    @Override // android.view.View
    @Deprecated
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
        this.mSelectedLeft = 0;
        this.mSelectorPosition = -1;
        this.mSelectorRect.setEmpty();
        invalidate();
    }

    @Override // android.view.View
    @Deprecated
    protected int computeHorizontalScrollExtent() {
        int count = getChildCount();
        if (count <= 0) {
            return 0;
        }
        if (this.mSmoothScrollbarEnabled) {
            int extent = count * 100;
            if (this.mIsRTL) {
                View viewLast = getChildAt(count - 1);
                int left = viewLast.getLeft();
                int width = viewLast.getWidth();
                if (width > 0) {
                    extent += (left * 100) / width;
                }
                View view = getChildAt(0);
                int right = view.getRight();
                int width2 = view.getWidth();
                if (width2 > 0) {
                    return extent - (((right - getWidth()) * 100) / width2);
                }
                return extent;
            }
            View view2 = getChildAt(0);
            int left2 = view2.getLeft();
            int width3 = view2.getWidth();
            if (width3 > 0) {
                extent += (left2 * 100) / width3;
            }
            View view3 = getChildAt(count - 1);
            int right2 = view3.getRight();
            int width4 = view3.getWidth();
            if (width4 > 0) {
                return extent - (((right2 - getWidth()) * 100) / width4);
            }
            return extent;
        }
        return 1;
    }

    @Override // android.view.View
    @Deprecated
    protected int computeHorizontalScrollOffset() {
        int index;
        int firstPosition = this.mFirstPosition;
        int childCount = getChildCount();
        int lastPosition = (this.mFirstPosition + getChildCount()) - 1;
        if (firstPosition >= 0 && childCount > 0) {
            if (this.mSmoothScrollbarEnabled) {
                if (this.mIsRTL) {
                    View view = getChildAt(childCount - 1);
                    int left = view.getRight();
                    int width = view.getWidth();
                    if (width > 0) {
                        return Math.max(
                                (((this.mItemCount - lastPosition) * 100) - ((left * 100) / width))
                                        + ((int)
                                                ((this.mScrollX / getWidth())
                                                        * this.mItemCount
                                                        * 100.0f)),
                                0);
                    }
                } else {
                    View view2 = getChildAt(0);
                    int left2 = view2.getLeft();
                    int width2 = view2.getWidth();
                    if (width2 > 0) {
                        return Math.max(
                                ((firstPosition * 100) - ((left2 * 100) / width2))
                                        + ((int)
                                                ((this.mScrollX / getWidth())
                                                        * this.mItemCount
                                                        * 100.0f)),
                                0);
                    }
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
    @Deprecated
    protected int computeHorizontalScrollRange() {
        if (this.mSmoothScrollbarEnabled) {
            int result = Math.max(this.mItemCount * 100, 0);
            if (this.mScrollX != 0) {
                return result
                        + Math.abs((int) ((this.mScrollX / getWidth()) * this.mItemCount * 100.0f));
            }
            return result;
        }
        return this.mItemCount;
    }

    @Override // android.view.View
    @Deprecated
    protected float getLeftFadingEdgeStrength() {
        int count = getChildCount();
        float fadeEdge = super.getLeftFadingEdgeStrength();
        if (count == 0) {
            return fadeEdge;
        }
        if (this.mIsRTL) {
            if ((this.mFirstPosition + count) - 1 < this.mItemCount - 1) {
                return 1.0f;
            }
        } else if (this.mFirstPosition > 0) {
            return 1.0f;
        }
        int left = getChildAt(this.mIsRTL ? count - 1 : 0).getLeft();
        float fadeLength = getHorizontalFadingEdgeLength();
        return left < this.mPaddingLeft ? (-(left - this.mPaddingLeft)) / fadeLength : fadeEdge;
    }

    @Override // android.view.View
    @Deprecated
    protected float getRightFadingEdgeStrength() {
        int count = getChildCount();
        float fadeEdge = super.getRightFadingEdgeStrength();
        if (count == 0) {
            return fadeEdge;
        }
        if (this.mIsRTL) {
            if (this.mFirstPosition > 0) {
                return 1.0f;
            }
        } else if ((this.mFirstPosition + count) - 1 < this.mItemCount - 1) {
            return 1.0f;
        }
        int right = getChildAt(this.mIsRTL ? 0 : count - 1).getRight();
        int width = getWidth();
        float fadeLength = getHorizontalFadingEdgeLength();
        return right > width - this.mPaddingRight
                ? ((right - width) + this.mPaddingRight) / fadeLength
                : fadeEdge;
    }

    @Override // android.view.View
    @Deprecated
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
            int listRight = getWidth() - getPaddingRight();
            View lastChild = this.mIsRTL ? getChildAt(0) : getChildAt(childCount - 1);
            int lastRight = lastChild != null ? lastChild.getRight() : listRight;
            this.mForceTranscriptScroll =
                    this.mFirstPosition + childCount >= this.mLastHandledItemCount
                            && lastRight <= listRight;
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    @Deprecated
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
        this.mInLayout = false;
        this.mOverscrollMax = (r - l) / 3;
        if (this.mFastScroll != null) {
            this.mFastScroll.onItemCountChanged(getChildCount(), this.mItemCount);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public boolean setFrame(int left, int top, int right, int bottom) {
        boolean changed = super.setFrame(left, top, right, bottom);
        if (changed) {
            boolean visible = getWindowVisibility() == 0;
            if (this.mFiltered && visible && this.mPopup != null && this.mPopup.isShowing()) {
                positionPopup();
            }
        }
        return changed;
    }

    @Deprecated
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
        if (this.mScrollLeft != null) {
            int count = getChildCount();
            boolean canScrollLeft =
                    !this.mIsRTL
                            ? this.mFirstPosition <= 0
                            : this.mFirstPosition + count >= this.mItemCount;
            if (!canScrollLeft && count > 0) {
                View child = this.mIsRTL ? getChildAt(count - 1) : getChildAt(0);
                canScrollLeft = child.getLeft() < this.mListPadding.left;
            }
            View child2 = this.mScrollLeft;
            child2.setVisibility(canScrollLeft ? 0 : 4);
        }
        if (this.mScrollRight != null) {
            int count2 = getChildCount();
            boolean canScrollRight =
                    !this.mIsRTL
                            ? this.mFirstPosition + count2 >= this.mItemCount
                            : this.mFirstPosition <= 0;
            if (!canScrollRight && count2 > 0) {
                View child3 = this.mIsRTL ? getChildAt(0) : getChildAt(count2 - 1);
                canScrollRight = child3.getRight() > this.mRight - this.mListPadding.right;
            }
            this.mScrollRight.setVisibility(canScrollRight ? 0 : 4);
        }
    }

    @Override // android.widget.AdapterView
    @ViewDebug.ExportedProperty
    @Deprecated
    public View getSelectedView() {
        if (this.mItemCount > 0 && this.mSelectedPosition >= 0) {
            return getChildAt(this.mSelectedPosition - this.mFirstPosition);
        }
        return null;
    }

    @Deprecated
    public int getListPaddingTop() {
        return this.mListPadding.top;
    }

    @Deprecated
    public int getListPaddingBottom() {
        return this.mListPadding.bottom;
    }

    @Deprecated
    public int getListPaddingLeft() {
        return this.mListPadding.left;
    }

    @Deprecated
    public int getListPaddingRight() {
        return this.mListPadding.right;
    }

    View obtainView(int position, boolean[] isScrap) {
        LayoutParams lp;
        Trace.traceBegin(8L, "obtainView");
        isScrap[0] = false;
        View transientView = this.mRecycler.getTransientStateView(position);
        if (transientView != null) {
            LayoutParams params = (LayoutParams) transientView.getLayoutParams();
            if (params.viewType == this.mAdapter.getItemViewType(position)) {
                View updatedView = this.mAdapter.getView(position, transientView, this);
                if (this.mAdapterHasStableIds) {
                    ViewGroup.LayoutParams vlp = transientView.getLayoutParams();
                    if (vlp == null) {
                        lp = (LayoutParams) generateDefaultLayoutParams();
                    } else if (!checkLayoutParams(vlp)) {
                        lp = (LayoutParams) generateLayoutParams(vlp);
                    } else {
                        lp = (LayoutParams) vlp;
                    }
                    lp.itemId = this.mAdapter.getItemId(position);
                    transientView.setLayoutParams(lp);
                }
                if (updatedView != transientView) {
                    setItemViewLayoutParams(updatedView, position);
                    this.mRecycler.addScrapView(updatedView, position);
                }
            }
            isScrap[0] = true;
            return transientView;
        }
        View scrapView = this.mRecycler.getScrapView(position);
        View child = this.mAdapter.getView(position, scrapView, this);
        if (child == null) {
            return null;
        }
        if (scrapView != null) {
            if (child != scrapView) {
                if (scrapView.isAccessibilityFocused()) {
                    scrapView.clearAccessibilityFocus();
                    child.requestAccessibilityFocus();
                }
                this.mRecycler.addScrapView(scrapView, position);
            } else {
                isScrap[0] = true;
                child.dispatchFinishTemporaryDetach();
            }
        }
        if (this.mCacheColorHint != 0) {
            child.setDrawingCacheBackgroundColor(this.mCacheColorHint);
        }
        if (child.getImportantForAccessibility() == 0) {
            child.setImportantForAccessibility(1);
        }
        setItemViewLayoutParams(child, position);
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            if (this.mAccessibilityDelegate == null) {
                this.mAccessibilityDelegate = new ListItemAccessibilityDelegate();
            }
            if (child.getAccessibilityDelegate() == null) {
                child.setAccessibilityDelegate(this.mAccessibilityDelegate);
            }
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
        child.setLayoutParams(lp);
    }

    class ListItemAccessibilityDelegate extends View.AccessibilityDelegate {
        ListItemAccessibilityDelegate() {}

        @Override // android.view.View.AccessibilityDelegate
        public AccessibilityNodeInfo createAccessibilityNodeInfo(View host) {
            if (SemHorizontalAbsListView.this.mDataChanged) {
                return null;
            }
            return super.createAccessibilityNodeInfo(host);
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            int position = SemHorizontalAbsListView.this.getPositionForView(host);
            SemHorizontalAbsListView.this.onInitializeAccessibilityNodeInfoForItem(
                    host, position, info);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean performAccessibilityAction(View host, int action, Bundle arguments) {
            if (super.performAccessibilityAction(host, action, arguments)) {
                return true;
            }
            int position = SemHorizontalAbsListView.this.getPositionForView(host);
            ListAdapter adapter = SemHorizontalAbsListView.this.getAdapter();
            if (position == -1
                    || adapter == null
                    || !SemHorizontalAbsListView.this.isEnabled()
                    || !adapter.isEnabled(position)) {
                return false;
            }
            long id = SemHorizontalAbsListView.this.getItemIdAtPosition(position);
            switch (action) {
                case 4:
                    if (SemHorizontalAbsListView.this.getSelectedItemPosition() == position) {
                        return false;
                    }
                    SemHorizontalAbsListView.this.setSelection(position);
                    return true;
                case 8:
                    if (SemHorizontalAbsListView.this.getSelectedItemPosition() != position) {
                        return false;
                    }
                    SemHorizontalAbsListView.this.setSelection(-1);
                    return true;
                case 16:
                    if (SemHorizontalAbsListView.this.isClickable()) {
                        return SemHorizontalAbsListView.this.performItemClick(host, position, id);
                    }
                    return false;
                case 32:
                    if (SemHorizontalAbsListView.this.isLongClickable()) {
                        return SemHorizontalAbsListView.this.performLongPress(host, position, id);
                    }
                    return false;
                default:
                    return false;
            }
        }
    }

    @Deprecated
    public void onInitializeAccessibilityNodeInfoForItem(
            View view, int position, AccessibilityNodeInfo info) {
        ListAdapter adapter = getAdapter();
        if (position == -1 || adapter == null) {
            return;
        }
        if (!isEnabled() || !adapter.isEnabled(position)) {
            info.setEnabled(false);
            return;
        }
        if (position == getSelectedItemPosition()) {
            info.setSelected(true);
            info.addAction(8);
        } else {
            info.addAction(4);
        }
        if (isClickable()) {
            info.addAction(16);
            info.setClickable(true);
        }
        if (isLongClickable()) {
            info.addAction(32);
            info.setLongClickable(true);
        }
    }

    void positionSelectorLikeTouch(int position, View sel, float x, float y) {
        positionSelectorLikeFocus(position, sel);
        if (this.mSelector != null && position != -1) {
            this.mSelector.setHotspot(x, y);
        }
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

    private void positionSelector(int position, View sel, boolean manageHotspot, float x, float y) {
        boolean positionChanged = position != this.mSelectorPosition;
        if (position != -1) {
            this.mSelectorPosition = position;
        }
        Rect selectorRect = this.mSelectorRect;
        selectorRect.set(sel.getLeft(), sel.getTop(), sel.getRight(), sel.getBottom());
        selectorRect.left -= this.mSelectionLeftPadding;
        selectorRect.top -= this.mSelectionTopPadding;
        selectorRect.right += this.mSelectionRightPadding;
        selectorRect.bottom += this.mSelectionBottomPadding;
        Drawable selector = this.mSelector;
        if (selector != null) {
            if (positionChanged) {
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
        boolean isChildViewEnabled = this.mIsChildViewEnabled;
        if (sel.isEnabled() != isChildViewEnabled) {
            this.mIsChildViewEnabled = !isChildViewEnabled;
            if (getSelectedItemPosition() != -1) {
                refreshDrawableState();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected void dispatchDraw(Canvas canvas) {
        int saveCount = 0;
        int trackChildLeft = 0;
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
        if (this.mIsDragBlockEnabled) {
            if (this.mSemDragBlockLeft == 0 && this.mSemDragBlockTop == 0) {
                return;
            }
            int firstChildPosition = getFirstVisiblePosition();
            int lastChildPosition = getLastVisiblePosition();
            if (this.mSemTrackedChildPosition >= firstChildPosition
                    && this.mSemTrackedChildPosition <= lastChildPosition) {
                this.mSemTrackedChild =
                        getChildAt(this.mSemTrackedChildPosition - getFirstVisiblePosition());
                if (this.mSemTrackedChild != null) {
                    trackChildLeft = this.mSemTrackedChild.getLeft();
                }
                this.mSemDragStartX = this.mSemDistanceFromTrackedChildLeft + trackChildLeft;
            }
            this.mSemDragBlockLeft =
                    this.mSemDragStartX < this.mSemDragEndX
                            ? this.mSemDragStartX
                            : this.mSemDragEndX;
            this.mSemDragBlockRight =
                    this.mSemDragEndX > this.mSemDragStartX
                            ? this.mSemDragEndX
                            : this.mSemDragStartX;
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
    @Deprecated
    protected boolean isPaddingOffsetRequired() {
        return (this.mGroupFlags & 34) != 34;
    }

    @Override // android.view.View
    @Deprecated
    protected int getLeftPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return -this.mPaddingLeft;
    }

    @Override // android.view.View
    @Deprecated
    protected int getTopPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return -this.mPaddingTop;
    }

    @Override // android.view.View
    @Deprecated
    protected int getRightPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return this.mPaddingRight;
    }

    @Override // android.view.View
    @Deprecated
    protected int getBottomPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return this.mPaddingBottom;
    }

    @Override // android.view.View
    @Deprecated
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (getChildCount() > 0) {
            this.mDataChanged = true;
            rememberSyncState();
        }
        if (this.mFastScroll != null) {
            this.mFastScroll.onSizeChanged(w, h, oldw, oldh);
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
        return (hasFocus() && !isInTouchMode()) || (touchModeDrawsInPressedState() && isPressed());
    }

    boolean shouldShowSelectorDefault() {
        return !isInTouchMode() || (touchModeDrawsInPressedState() && isPressed());
    }

    private void drawSelector(Canvas canvas) {
        Rect tempSelectorRect = new Rect();
        if (!this.mSelectorRect.isEmpty()) {
            Drawable selector = this.mSelector;
            selector.setBounds(this.mSelectorRect);
            selector.draw(canvas);
        }
        if (this.mIsMultiFocusEnabled) {
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

    @Deprecated
    public void setDrawSelectorOnTop(boolean onTop) {
        this.mDrawSelectorOnTop = onTop;
    }

    @Deprecated
    public void setSelector(int resID) {
        setSelector(getContext().getDrawable(resID));
    }

    @Deprecated
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

    @Deprecated
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
                if (v != null) {
                    if (v.hasExplicitFocusable()) {
                        return;
                    } else {
                        v.setPressed(true);
                    }
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

    @Deprecated
    public void setScrollIndicators(View left, View right) {
        this.mScrollLeft = left;
        this.mScrollRight = right;
    }

    void updateSelectorState() {
        if (this.mSelector != null) {
            if (shouldShowSelector()) {
                if (isHovered()
                        && !this.mIsHoveredByMouse
                        && this.mSelectorPosition >= this.mFirstPosition) {
                    View child = getChildAt(this.mSelectorPosition - this.mFirstPosition);
                    if (!this.mIsPenHovered && child != null && !child.isEnabled()) {
                        this.mSelector.setState(StateSet.NOTHING);
                        this.mSelectorRect.setEmpty();
                        return;
                    } else {
                        this.mSelector.setState(getDrawableState());
                        return;
                    }
                }
                this.mSelector.setState(getDrawableState());
                return;
            }
            this.mSelector.setState(StateSet.NOTHING);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateSelectorState();
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.mIsChildViewEnabled) {
            return super.onCreateDrawableState(extraSpace);
        }
        int enabledState = ENABLED_STATE_SET[0];
        int[] state = super.onCreateDrawableState(extraSpace + 1);
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
    @Deprecated
    public boolean verifyDrawable(Drawable dr) {
        return this.mSelector == dr || super.verifyDrawable(dr);
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mSelector != null) {
            this.mSelector.jumpToCurrentState();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
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
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    @Deprecated
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
    @Deprecated
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.mHasWindowFocusForMotion = z;
        int i = !isInTouchMode() ? 1 : 0;
        if (!z) {
            setChildrenDrawingCacheEnabled(false);
            if (this.mFlingRunnable != null) {
                removeCallbacks(this.mFlingRunnable);
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
    }

    @Override // android.view.View
    @Deprecated
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        this.mIsRTL = isLayoutRtl();
        if (this.mFastScroll != null) {
            this.mFastScroll.setScrollbarPosition(semGetHorizontalScrollbarPosition());
        }
    }

    ContextMenu.ContextMenuInfo createContextMenuInfo(View view, int position, long id) {
        return new AdapterView.AdapterContextMenuInfo(view, position, id);
    }

    @Override // android.view.View
    @Deprecated
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
    }

    private class WindowRunnnable {
        private int mOriginalAttachCount;

        private WindowRunnnable() {}

        public void rememberWindowAttachCount() {
            this.mOriginalAttachCount = SemHorizontalAbsListView.this.getWindowAttachCount();
        }

        public boolean sameWindow() {
            return SemHorizontalAbsListView.this.getWindowAttachCount()
                    == this.mOriginalAttachCount;
        }
    }

    public void setForcedClick(boolean force) {
        this.mForcedClick = force;
    }

    private class PerformClick extends WindowRunnnable implements Runnable {
        int mClickMotionPosition;

        private PerformClick() {
            super();
        }

        @Override // java.lang.Runnable
        public void run() {
            View view;
            if (SemHorizontalAbsListView.this.mForcedClick
                    || !SemHorizontalAbsListView.this.mDataChanged) {
                ListAdapter adapter = SemHorizontalAbsListView.this.mAdapter;
                int motionPosition = this.mClickMotionPosition;
                if (adapter != null
                        && SemHorizontalAbsListView.this.mItemCount > 0
                        && motionPosition != -1
                        && motionPosition < adapter.getCount()
                        && sameWindow()
                        && (view =
                                        SemHorizontalAbsListView.this.getChildAt(
                                                motionPosition
                                                        - SemHorizontalAbsListView.this
                                                                .mFirstPosition))
                                != null) {
                    try {
                        SemHorizontalAbsListView.this.performItemClick(
                                view, motionPosition, adapter.getItemId(motionPosition));
                        if (SemHorizontalAbsListView.this.mIsShiftkeyPressed
                                || SemHorizontalAbsListView.this.mIsCtrlkeyPressed) {
                            SemHorizontalAbsListView.this.semNotifyKeyPressState(
                                    view, motionPosition, adapter.getItemId(motionPosition));
                        }
                        if ((SemHorizontalAbsListView.this.mIsShiftkeyPressed
                                        || SemHorizontalAbsListView.this.mIsCtrlkeyPressed)
                                && SemHorizontalAbsListView.this.mAdapter != null) {
                            if (SemHorizontalAbsListView.this.mIsCtrlkeyPressed) {
                                SemHorizontalAbsListView.this.addToPressItemListArray(
                                        motionPosition, -1);
                                return;
                            }
                            if (SemHorizontalAbsListView.this.mIsShiftkeyPressed) {
                                SemHorizontalAbsListView.this.resetPressItemListArray();
                                if (SemHorizontalAbsListView.this.mFirstPressedPoint == -1) {
                                    SemHorizontalAbsListView.this.addToPressItemListArray(
                                            motionPosition, -1);
                                    SemHorizontalAbsListView.this.mFirstPressedPoint =
                                            motionPosition;
                                } else {
                                    SemHorizontalAbsListView.this.addToPressItemListArray(
                                            SemHorizontalAbsListView.this.mFirstPressedPoint,
                                            motionPosition);
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class CheckForLongPress extends WindowRunnnable implements Runnable {
        private CheckForLongPress() {
            super();
        }

        @Override // java.lang.Runnable
        public void run() {
            int motionPosition = SemHorizontalAbsListView.this.mMotionPosition;
            View child =
                    SemHorizontalAbsListView.this.getChildAt(
                            motionPosition - SemHorizontalAbsListView.this.mFirstPosition);
            if (child != null) {
                int longPressPosition = SemHorizontalAbsListView.this.mMotionPosition;
                long longPressId =
                        SemHorizontalAbsListView.this.mAdapter.getItemId(
                                SemHorizontalAbsListView.this.mMotionPosition);
                boolean handled = false;
                if (sameWindow() && !SemHorizontalAbsListView.this.mDataChanged) {
                    handled =
                            SemHorizontalAbsListView.this.performLongPress(
                                    child, longPressPosition, longPressId);
                }
                if (handled) {
                    SemHorizontalAbsListView.this.mTouchMode = -1;
                    SemHorizontalAbsListView.this.setPressed(false);
                    child.setPressed(false);
                    return;
                }
                SemHorizontalAbsListView.this.mTouchMode = 2;
            }
        }
    }

    private class CheckForKeyLongPress extends WindowRunnnable implements Runnable {
        private CheckForKeyLongPress() {
            super();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SemHorizontalAbsListView.this.isPressed()
                    && SemHorizontalAbsListView.this.mSelectedPosition >= 0) {
                int index =
                        SemHorizontalAbsListView.this.mSelectedPosition
                                - SemHorizontalAbsListView.this.mFirstPosition;
                View v = SemHorizontalAbsListView.this.getChildAt(index);
                if (v == null) {
                    return;
                }
                if (!SemHorizontalAbsListView.this.mDataChanged) {
                    boolean handled = false;
                    if (sameWindow()) {
                        handled =
                                SemHorizontalAbsListView.this.performLongPress(
                                        v,
                                        SemHorizontalAbsListView.this.mSelectedPosition,
                                        SemHorizontalAbsListView.this.mSelectedRowId);
                    }
                    if (handled) {
                        SemHorizontalAbsListView.this.setPressed(false);
                        v.setPressed(false);
                        return;
                    }
                    return;
                }
                SemHorizontalAbsListView.this.setPressed(false);
                v.setPressed(false);
            }
        }
    }

    boolean performLongPress(View child, int longPressPosition, long longPressId) {
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
            handled = super.showContextMenuForChild(this);
        }
        if (handled) {
            if (semGetEnableVibrationAtLongPress()) {
                performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
            } else {
                Log.d(TAG, " does not need vibration");
            }
        }
        return handled;
    }

    @Override // android.view.View
    @Deprecated
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    public boolean showContextMenu(float x, float y, int metaState) {
        int position = pointToPosition((int) x, (int) y);
        if (position != -1) {
            long id = this.mAdapter.getItemId(position);
            View child = getChildAt(position - this.mFirstPosition);
            if (child != null) {
                this.mContextMenuInfo = createContextMenuInfo(child, position, id);
                return super.showContextMenuForChild(this);
            }
        } else {
            this.mContextMenuInfo = null;
        }
        return super.showContextMenu();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    @Deprecated
    public boolean showContextMenuForChild(View originalView) {
        int longPressPosition = getPositionForView(originalView);
        if (longPressPosition >= 0) {
            long longPressId = this.mAdapter.getItemId(longPressPosition);
            boolean handled = false;
            if (this.mOnItemLongClickListener != null) {
                handled =
                        this.mOnItemLongClickListener.onItemLongClick(
                                this, originalView, longPressPosition, longPressId);
            }
            if (!handled) {
                this.mContextMenuInfo =
                        createContextMenuInfo(
                                getChildAt(longPressPosition - this.mFirstPosition),
                                longPressPosition,
                                longPressId);
                boolean handled2 = super.showContextMenuForChild(originalView);
                return handled2;
            }
            return handled;
        }
        return false;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    @Deprecated
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
    @Deprecated
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        View selectedview = getChildAt(this.mSelectedPosition - this.mFirstPosition);
        if (KeyEvent.isConfirmKey(keyCode)) {
            if (!isEnabled()) {
                return true;
            }
            if (isClickable()
                    && isPressed()
                    && this.mSelectedPosition >= 0
                    && this.mAdapter != null
                    && this.mSelectedPosition < this.mAdapter.getCount()) {
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
                    View currentview = getChildAt(this.mSemCurrentFocusPosition);
                    if (this.mIsShiftkeyPressed && selectedview != null) {
                        if (this.mCurrentKeyCode == 0) {
                            resetPressItemListArray();
                            semNotifyKeyPressState(
                                    currentview,
                                    this.mSemCurrentFocusPosition,
                                    this.mSelectedRowId);
                            semNotifyKeyPressState(
                                    selectedview, this.mSelectedPosition, this.mSelectedRowId);
                            addToPressItemListArray(
                                    this.mSemCurrentFocusPosition, this.mSelectedPosition);
                            this.mFirstPressedPoint = this.mSemCurrentFocusPosition;
                        } else {
                            resetPressItemListArray();
                            semNotifyKeyPressState(
                                    selectedview, this.mSelectedPosition, this.mSelectedRowId);
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
                this.mSecondPressedPoint = -1;
                break;
            case 113:
            case 114:
                this.mIsCtrlkeyPressed = false;
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected void dispatchSetPressed(boolean pressed) {}

    @Deprecated
    public int pointToPosition(int x, int y) {
        Rect frame = this.mTouchFrame;
        if (frame == null) {
            this.mTouchFrame = new Rect();
            frame = this.mTouchFrame;
        }
        boolean drawDividers = false;
        int dividerHeight =
                this instanceof SemHorizontalListView
                        ? ((SemHorizontalListView) this).mDividerHeight
                        : 0;
        if (dividerHeight > 0 && ((SemHorizontalListView) this).mDivider != null) {
            drawDividers = true;
        }
        int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                child.getHitRect(frame);
                if (drawDividers) {
                    frame.bottom += dividerHeight;
                }
                if (frame.contains(x, y)) {
                    return this.mFirstPosition + i;
                }
            }
        }
        return -1;
    }

    @Deprecated
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
            if (SemHorizontalAbsListView.this.mTouchMode == 0) {
                SemHorizontalAbsListView.this.mTouchMode = 1;
                View child =
                        SemHorizontalAbsListView.this.getChildAt(
                                SemHorizontalAbsListView.this.mMotionPosition
                                        - SemHorizontalAbsListView.this.mFirstPosition);
                if (child != null) {
                    SemHorizontalAbsListView.this.mIsChildViewEnabled = child.isEnabled();
                }
                if (child != null
                        && !child.hasExplicitFocusable()
                        && SemHorizontalAbsListView.this.getAdapter() != null
                        && SemHorizontalAbsListView.this.mMotionPosition >= 0
                        && SemHorizontalAbsListView.this
                                .getAdapter()
                                .isEnabled(SemHorizontalAbsListView.this.mMotionPosition)) {
                    SemHorizontalAbsListView.this.mLayoutMode = 0;
                    if (!SemHorizontalAbsListView.this.mDataChanged) {
                        child.setPressed(true);
                        SemHorizontalAbsListView.this.setPressed(true);
                        SemHorizontalAbsListView.this.layoutChildren();
                        SemHorizontalAbsListView.this.positionSelector(
                                SemHorizontalAbsListView.this.mMotionPosition, child);
                        SemHorizontalAbsListView.this.refreshDrawableState();
                        int longPressTimeout = ViewConfiguration.getLongPressTimeout();
                        boolean longClickable = SemHorizontalAbsListView.this.isLongClickable();
                        if (SemHorizontalAbsListView.this.mSelector != null) {
                            Drawable d = SemHorizontalAbsListView.this.mSelector.getCurrent();
                            if (d != null && (d instanceof TransitionDrawable)) {
                                if (longClickable) {
                                    ((TransitionDrawable) d).startTransition(longPressTimeout);
                                } else {
                                    ((TransitionDrawable) d).resetTransition();
                                }
                            }
                            SemHorizontalAbsListView.this.mSelector.setHotspot(this.x, this.y);
                        }
                        if (longClickable) {
                            if (SemHorizontalAbsListView.this.mPendingCheckForLongPress == null) {
                                SemHorizontalAbsListView.this.mPendingCheckForLongPress =
                                        new CheckForLongPress();
                            }
                            SemHorizontalAbsListView.this.mPendingCheckForLongPress
                                    .rememberWindowAttachCount();
                            SemHorizontalAbsListView.this.postDelayed(
                                    SemHorizontalAbsListView.this.mPendingCheckForLongPress,
                                    longPressTimeout);
                            return;
                        }
                        SemHorizontalAbsListView.this.mTouchMode = 2;
                        return;
                    }
                    SemHorizontalAbsListView.this.mTouchMode = 2;
                }
            }
        }
    }

    private boolean startScrollIfNeeded(int x, int y, MotionEvent vtev) {
        int deltaX = x - this.mMotionX;
        int distance = Math.abs(deltaX);
        boolean overscroll = this.mScrollX != 0;
        if ((!overscroll && distance <= this.mTouchSlop) || (getNestedScrollAxes() & 1) != 0) {
            return false;
        }
        createScrollingCache();
        if (overscroll) {
            this.mTouchMode = 5;
            this.mMotionCorrection = 0;
        } else {
            this.mTouchMode = 3;
            int i = this.mTouchSlop;
            if (deltaX <= 0) {
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
        int rawDeltaX;
        int scrollOffsetCorrection;
        int scrollConsumedCorrection;
        int incrementalDeltaX;
        int overScrollDistance;
        int incrementalDeltaX2;
        int incrementalDeltaX3;
        int newDirection;
        int motionIndex;
        int motionViewPrevLeft;
        boolean atEdge;
        int incrementalDeltaX4;
        ViewParent parent;
        int rawDeltaX2 = x - this.mMotionX;
        if (this.mLastX == Integer.MIN_VALUE) {
            rawDeltaX2 -= this.mMotionCorrection;
        }
        if (!dispatchNestedPreScroll(
                0,
                this.mLastX != Integer.MIN_VALUE ? this.mLastX - x : -rawDeltaX2,
                this.mScrollConsumed,
                this.mScrollOffset)) {
            rawDeltaX = rawDeltaX2;
            scrollOffsetCorrection = 0;
            scrollConsumedCorrection = 0;
        } else {
            int rawDeltaX3 = rawDeltaX2 + this.mScrollConsumed[0];
            int scrollOffsetCorrection2 = -this.mScrollOffset[0];
            int scrollConsumedCorrection2 = this.mScrollConsumed[0];
            if (vtev != null) {
                vtev.offsetLocation(this.mScrollOffset[0], 0.0f);
                this.mNestedXOffset += this.mScrollOffset[0];
            }
            rawDeltaX = rawDeltaX3;
            scrollOffsetCorrection = scrollOffsetCorrection2;
            scrollConsumedCorrection = scrollConsumedCorrection2;
        }
        int deltaX = rawDeltaX;
        int incrementalDeltaX5 =
                this.mLastX != Integer.MIN_VALUE
                        ? (x - this.mLastX) + scrollConsumedCorrection
                        : deltaX;
        int lastXCorrection = 0;
        if (this.mTouchMode == 3) {
            if (this.mScrollStrictSpan == null) {
                this.mScrollStrictSpan =
                        StrictMode.enterCriticalSpan("SemHorizontalAbsListView-scroll");
            }
            if (x == this.mLastX) {
                incrementalDeltaX = incrementalDeltaX5;
            } else {
                if ((this.mGroupFlags & 524288) == 0
                        && Math.abs(rawDeltaX) > this.mTouchSlop
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
                    motionViewPrevLeft = 0;
                } else {
                    int motionViewPrevLeft2 = motionView.getLeft();
                    motionViewPrevLeft = motionViewPrevLeft2;
                }
                if (incrementalDeltaX5 == 0) {
                    atEdge = false;
                } else {
                    boolean atEdge2 = trackMotionScroll(deltaX, incrementalDeltaX5);
                    atEdge = atEdge2;
                }
                View motionView2 = getChildAt(motionIndex);
                if (motionView2 != null) {
                    int motionViewRealLeft = motionView2.getLeft();
                    if (atEdge) {
                        int overscroll =
                                (-incrementalDeltaX5) - (motionViewRealLeft - motionViewPrevLeft);
                        if (!dispatchNestedScroll(
                                overscroll - incrementalDeltaX5,
                                0,
                                0,
                                overscroll,
                                this.mScrollOffset)) {
                            boolean atOverscrollEdge =
                                    overScrollBy(
                                            overscroll,
                                            0,
                                            this.mScrollX,
                                            0,
                                            0,
                                            0,
                                            this.mOverscrollDistance,
                                            0,
                                            true);
                            if (atOverscrollEdge && this.mVelocityTracker != null) {
                                this.mVelocityTracker.clear();
                            }
                            int overscrollMode = getOverScrollMode();
                            if (overscrollMode != 0) {
                                if (overscrollMode != 1) {
                                    incrementalDeltaX4 = incrementalDeltaX5;
                                } else if (contentFits()) {
                                    incrementalDeltaX4 = incrementalDeltaX5;
                                }
                            }
                            if (!atOverscrollEdge) {
                                this.mDirection = 0;
                                this.mTouchMode = 5;
                            }
                            incrementalDeltaX4 = incrementalDeltaX5;
                            if (incrementalDeltaX4 > 0) {
                                this.mEdgeGlowLeft.onPull(
                                        (-overscroll) / getWidth(), 1.0f - (y / getHeight()));
                                if (!this.mEdgeGlowRight.isFinished()) {
                                    this.mEdgeGlowRight.onRelease();
                                }
                                invalidate(
                                        0,
                                        0,
                                        this.mEdgeGlowLeft.getMaxHeight() + getPaddingLeft(),
                                        getHeight());
                            } else if (incrementalDeltaX4 < 0) {
                                this.mEdgeGlowRight.onPull(
                                        overscroll / getWidth(), y / getHeight());
                                if (!this.mEdgeGlowLeft.isFinished()) {
                                    this.mEdgeGlowLeft.onRelease();
                                }
                                invalidate(
                                        (getWidth() - getPaddingRight())
                                                - this.mEdgeGlowRight.getMaxHeight(),
                                        0,
                                        getWidth(),
                                        getHeight());
                            }
                        } else {
                            lastXCorrection = 0 - this.mScrollOffset[0];
                            if (vtev != null) {
                                vtev.offsetLocation(this.mScrollOffset[0], 0.0f);
                                this.mNestedXOffset += this.mScrollOffset[0];
                            }
                            incrementalDeltaX4 = incrementalDeltaX5;
                        }
                    } else {
                        incrementalDeltaX4 = incrementalDeltaX5;
                    }
                    this.mMotionX = x + lastXCorrection + scrollOffsetCorrection;
                } else {
                    incrementalDeltaX4 = incrementalDeltaX5;
                }
                this.mLastX = x + lastXCorrection + scrollOffsetCorrection;
            }
        } else {
            incrementalDeltaX = incrementalDeltaX5;
            if (this.mTouchMode == 5 && x != this.mLastX) {
                int oldScroll = this.mScrollX;
                int newScroll = oldScroll - incrementalDeltaX;
                int newDirection2 = x > this.mLastX ? 1 : -1;
                if (this.mDirection == 0) {
                    this.mDirection = newDirection2;
                }
                int overScrollDistance2 = -incrementalDeltaX;
                if ((newScroll < 0 && oldScroll >= 0) || (newScroll > 0 && oldScroll <= 0)) {
                    int overScrollDistance3 = -oldScroll;
                    overScrollDistance = overScrollDistance3;
                    incrementalDeltaX2 = incrementalDeltaX + overScrollDistance3;
                } else {
                    overScrollDistance = overScrollDistance2;
                    incrementalDeltaX2 = 0;
                }
                if (overScrollDistance != 0) {
                    incrementalDeltaX3 = incrementalDeltaX2;
                    int overScrollDistance4 = overScrollDistance;
                    newDirection = newDirection2;
                    overScrollBy(
                            overScrollDistance,
                            0,
                            this.mScrollX,
                            0,
                            0,
                            0,
                            this.mOverscrollDistance,
                            0,
                            true);
                    int overscrollMode2 = getOverScrollMode();
                    if (overscrollMode2 == 0 || (overscrollMode2 == 1 && !contentFits())) {
                        if (rawDeltaX > 0) {
                            this.mEdgeGlowLeft.onPull(
                                    overScrollDistance4 / getWidth(), 1.0f - (y / getHeight()));
                            if (!this.mEdgeGlowRight.isFinished()) {
                                this.mEdgeGlowRight.onRelease();
                            }
                            invalidate(
                                    0,
                                    0,
                                    this.mEdgeGlowLeft.getMaxHeight() + getPaddingLeft(),
                                    getHeight());
                        } else if (rawDeltaX < 0) {
                            this.mEdgeGlowRight.onPull(
                                    overScrollDistance4 / getWidth(), y / getHeight());
                            if (!this.mEdgeGlowLeft.isFinished()) {
                                this.mEdgeGlowLeft.onRelease();
                            }
                            invalidate(
                                    (getWidth() - getPaddingRight())
                                            - this.mEdgeGlowRight.getMaxHeight(),
                                    0,
                                    getWidth(),
                                    getHeight());
                        }
                    }
                } else {
                    incrementalDeltaX3 = incrementalDeltaX2;
                    newDirection = newDirection2;
                }
                int incrementalDeltaX6 = incrementalDeltaX3;
                if (incrementalDeltaX6 != 0) {
                    if (this.mScrollX != 0) {
                        this.mScrollX = 0;
                        invalidateParentIfNeeded();
                    }
                    trackMotionScroll(incrementalDeltaX6, incrementalDeltaX6);
                    this.mTouchMode = 3;
                    int motionPosition = findClosestMotionRow(x);
                    this.mMotionCorrection = 0;
                    View motionView3 = getChildAt(motionPosition - this.mFirstPosition);
                    this.mMotionViewOriginalLeft = motionView3 != null ? motionView3.getLeft() : 0;
                    this.mMotionX = x;
                    this.mMotionPosition = motionPosition;
                }
                this.mLastX = x + 0 + scrollOffsetCorrection;
                this.mDirection = newDirection;
            }
        }
    }

    @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
    @Deprecated
    public void onTouchModeChanged(boolean isInTouchMode) {
        if (isInTouchMode) {
            hideSelector();
            if (getWidth() > 0 && getChildCount() > 0) {
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
            if (this.mScrollX != 0) {
                this.mScrollX = 0;
                invalidateParentCaches();
                finishGlows();
                invalidate();
            }
        }
    }

    private boolean isLockScreenMode() {
        Context context = this.mContext;
        Context context2 = this.mContext;
        return ((KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE))
                .inKeyguardRestrictedInputMode();
    }

    public void semSetHoverScrollEnabled(boolean flag) {
        this.mHoverScrollEnable = flag;
        this.mHoverScrollStateChanged = true;
    }

    public void setEnablePaddingInHoverScroll(boolean enable) {
        this.mIsEnabledPaddingInHoverScroll = enable;
    }

    public void addExtraPaddingInLeftHoverArea(int extraSpace) {
        this.mExtraPaddingInLeftHoverArea =
                (int)
                        (TypedValue.applyDimension(
                                        1,
                                        extraSpace,
                                        this.mContext.getResources().getDisplayMetrics())
                                + 0.5f);
    }

    public void addExtraPaddingInRightHoverArea(int extraSpace) {
        this.mExtraPaddingInRightHoverArea =
                (int)
                        (TypedValue.applyDimension(
                                        1,
                                        extraSpace,
                                        this.mContext.getResources().getDisplayMetrics())
                                + 0.5f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0377  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x039b  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x03b7  */
    /* JADX WARN: Type inference failed for: r4v10, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v30 */
    /* JADX WARN: Type inference failed for: r4v31, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v34 */
    /* JADX WARN: Type inference failed for: r4v9 */
    @Override // android.view.ViewGroup, android.view.View
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean dispatchHoverEvent(android.view.MotionEvent r17) {
        /*
            Method dump skipped, instructions count: 988
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.widget.SemHorizontalAbsListView.dispatchHoverEvent(android.view.MotionEvent):boolean");
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
        int childCount = getChildCount();
        int contentRight = 0;
        int count = getChildCount();
        if (childCount != 0) {
            contentRight = getWidth();
        }
        boolean canScrollRight = this.mFirstPosition + count < this.mItemCount;
        if (!canScrollRight && count > 0) {
            View child = getChildAt(count - 1);
            canScrollRight =
                    child.getRight() > this.mRight - this.mListPadding.right
                            || child.getRight() > getWidth() - this.mListPadding.right;
        }
        boolean canScrollLeft = this.mFirstPosition > 0;
        if (!canScrollLeft && getChildCount() > 0) {
            canScrollLeft = getChildAt(0).getLeft() < this.mListPadding.left;
        }
        if ((x > this.mDragScrollWorkingZonePx && x < contentRight - this.mDragScrollWorkingZonePx)
                || y <= 0
                || y > getBottom()
                || (!canScrollLeft && !canScrollRight)) {
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
                if (!this.mHoverAreaEnter) {
                    this.mHoverAreaEnter = true;
                }
                if (x >= 0 && x <= this.mDragScrollWorkingZonePx) {
                    if (!this.mHoverHandler.hasMessages(1)) {
                        this.mIsDragScrolled = true;
                        this.mHoverRecognitionStartTime = System.currentTimeMillis();
                        this.mHoverScrollDirection = 2;
                        this.mHoverHandler.sendEmptyMessage(1);
                        break;
                    }
                } else if (x >= contentRight - this.mDragScrollWorkingZonePx
                        && x <= contentRight
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
                if (x >= 0 && x <= this.mDragScrollWorkingZonePx) {
                    if (!this.mHoverHandler.hasMessages(1)) {
                        this.mIsDragScrolled = true;
                        this.mHoverRecognitionStartTime = System.currentTimeMillis();
                        this.mHoverScrollDirection = 2;
                        this.mHoverHandler.sendEmptyMessage(1);
                        break;
                    }
                } else if (x >= contentRight - this.mDragScrollWorkingZonePx
                        && x <= contentRight
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

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int contentLeft;
        int contentRight;
        boolean z;
        int i;
        boolean z2;
        int action;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action2 = ev.getAction();
        boolean needToScroll = MultiSelection.isNeedToScroll();
        if (this.mSemDragSelectedItemArray == null) {
            this.mSemDragSelectedItemArray = new ArrayList<>();
        }
        if (this.mHoverHandler == null) {
            this.mHoverHandler = new HoverScrollHandler(this);
        }
        this.mIsTextSelectionStarted = TextView.semIsTextSelectionProgressing();
        if (action2 == 211) {
            this.mIsNeedPenSelection = true;
            boolean isCarModeOn =
                    Settings.System.getIntForUser(
                                    this.mContext.getContentResolver(), "car_mode_on", 0, -3)
                            == 1;
            if (isCarModeOn) {
                this.mIsNeedPenSelection = false;
            }
        }
        boolean isCarModeOn2 = this.mIsTextSelectionStarted;
        if (isCarModeOn2) {
            this.mIsNeedPenSelection = false;
        }
        if (this.mHoverLeftAreaWidth <= 0 || this.mHoverRightAreaWidth <= 0) {
            this.mHoverLeftAreaWidth =
                    (int)
                            (TypedValue.applyDimension(
                                            1,
                                            25.0f,
                                            this.mContext.getResources().getDisplayMetrics())
                                    + 0.5f);
            this.mHoverRightAreaWidth =
                    (int)
                            (TypedValue.applyDimension(
                                            1,
                                            25.0f,
                                            this.mContext.getResources().getDisplayMetrics())
                                    + 0.5f);
        }
        if (this.mIsEnabledPaddingInHoverScroll) {
            contentLeft = this.mListPadding.left;
            contentRight = getWidth() - this.mListPadding.right;
        } else {
            contentLeft = 0;
            contentRight = getWidth();
        }
        if (this.mIsEnabledPaddingInHoverScroll
                && ((x < contentLeft || x > contentRight)
                        && ev.getAction() != 1
                        && ev.getAction() != 212)) {
            return true;
        }
        switch (action2) {
            case 0:
                break;
            case 1:
            case 3:
            case 212:
                if (!this.mIsTextSelectionStarted) {
                    if (this.mHoverAreaEnter && this.mOnScrollListener != null) {
                        z = false;
                        this.mOnScrollListener.onScrollStateChanged(this, 0);
                    } else {
                        z = false;
                    }
                    this.mHoverRecognitionStartTime = 0L;
                    this.mHoverScrollStartTime = 0L;
                    this.mHoverAreaEnter = z;
                    boolean isNeedActionMode = false;
                    this.mSemDragSelectedItemSize = this.mSemDragSelectedItemArray.size();
                    if (this.mSemDragSelectedItemSize != 0) {
                        if (this.mCheckStates != null
                                && (this.mChoiceMode == 2 || this.mChoiceMode == 3)) {
                            Iterator<Integer> it = this.mSemDragSelectedItemArray.iterator();
                            while (it.hasNext()) {
                                if (this.mAdapter.isEnabled(it.next().intValue())) {
                                    isNeedActionMode = true;
                                }
                            }
                            if (this.mChoiceMode == 3
                                    && this.mChoiceActionMode == null
                                    && isNeedActionMode) {
                                this.mChoiceActionMode =
                                        startActionMode(this.mMultiChoiceModeCallback);
                            }
                            if (this.mSemIsOnClickEnabled) {
                                Iterator<Integer> it2 = this.mSemDragSelectedItemArray.iterator();
                                while (it2.hasNext()) {
                                    Integer dragSelectedViewPosition = it2.next();
                                    if (this.mAdapter.isEnabled(
                                            dragSelectedViewPosition.intValue())) {
                                        performItemClick(
                                                null,
                                                dragSelectedViewPosition.intValue(),
                                                getItemIdAtPosition(
                                                        dragSelectedViewPosition.intValue()));
                                    }
                                }
                            }
                        }
                        super.semNotifyMultiSelectedStop(x, y);
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
                this.mSemDragSelectedItemArray.clear();
                this.mSemDragSelectedItemSize = 0;
                this.mSemTrackedChild = null;
                this.mSemDistanceFromTrackedChildLeft = 0;
                this.mIsCloseChildSetted = false;
                this.mOldHoverScrollDirection = -1;
                this.mSemCloseChildByLeft = null;
                this.mSemCloseChildPositionByLeft = -1;
                this.mSemDistanceFromCloseChildLeft = 0;
                this.mSemCloseChildByRight = null;
                this.mSemCloseChildPositionByRight = -1;
                this.mSemDistanceFromCloseChildRight = 0;
                if (this.mIsDragBlockEnabled) {
                    invalidate();
                }
                if (this.mHoverHandler.hasMessages(1)) {
                    this.mHoverHandler.removeMessages(1);
                    break;
                }
                break;
            case 2:
                break;
            case 211:
                break;
            case 213:
                if (this.mIsNeedPenSelection) {
                    int count = getChildCount();
                    if (this.mIsfirstMoveEvent) {
                        this.mSemDragStartX = x;
                        this.mSemDragStartY = y;
                        super.semNotifyMultiSelectedStart(x, y);
                        this.mIsPenPressed = true;
                        this.mSemTrackedChildPosition = pointToPosition(x, y);
                        if (this.mSemTrackedChildPosition != -1) {
                            this.mSemTrackedChild =
                                    getChildAt(
                                            this.mSemTrackedChildPosition
                                                    - getFirstVisiblePosition());
                        } else {
                            int oldDistanceFromLeft = 0;
                            int oldDistanceFromRight = 0;
                            int i2 = count - 1;
                            while (true) {
                                if (i2 >= 0) {
                                    View child = getChildAt(i2);
                                    if (child == null) {
                                        action = action2;
                                    } else {
                                        int childLeft = child.getLeft();
                                        int childRight = child.getRight();
                                        if (this.mSemDragStartX >= childLeft
                                                && this.mSemDragStartX <= childRight) {
                                            this.mSemTrackedChild = child;
                                            this.mSemTrackedChildPosition =
                                                    getFirstVisiblePosition() + i2;
                                        } else {
                                            int newDistanceFromLeft =
                                                    Math.abs(this.mSemDragStartX - childLeft);
                                            int newDistanceFromRight =
                                                    Math.abs(this.mSemDragStartX - childRight);
                                            action = action2;
                                            if (i2 == count - 1) {
                                                this.mSemCloseChildPositionByLeft =
                                                        (count - 1) + getFirstVisiblePosition();
                                                this.mSemCloseChildPositionByRight =
                                                        (count - 1) + getFirstVisiblePosition();
                                                oldDistanceFromRight = newDistanceFromRight;
                                                oldDistanceFromLeft = newDistanceFromLeft;
                                            } else {
                                                if (newDistanceFromLeft <= oldDistanceFromLeft) {
                                                    this.mSemCloseChildPositionByLeft =
                                                            getFirstVisiblePosition() + i2;
                                                    oldDistanceFromLeft = newDistanceFromLeft;
                                                }
                                                if (newDistanceFromRight <= oldDistanceFromRight) {
                                                    this.mSemCloseChildPositionByRight =
                                                            getFirstVisiblePosition() + i2;
                                                    oldDistanceFromRight = newDistanceFromRight;
                                                }
                                            }
                                        }
                                    }
                                    i2--;
                                    action2 = action;
                                }
                            }
                            if (this.mSemTrackedChild == null) {
                                this.mSemCloseChildByLeft =
                                        getChildAt(
                                                this.mSemCloseChildPositionByLeft
                                                        - getFirstVisiblePosition());
                                if (this.mSemCloseChildByLeft != null) {
                                    this.mSemDistanceFromCloseChildLeft =
                                            this.mSemDragStartX
                                                    - this.mSemCloseChildByLeft.getLeft();
                                }
                                this.mSemCloseChildByRight =
                                        getChildAt(
                                                this.mSemCloseChildPositionByRight
                                                        - getFirstVisiblePosition());
                                if (this.mSemCloseChildByRight != null) {
                                    this.mSemDistanceFromCloseChildRight =
                                            this.mSemDragStartX
                                                    - this.mSemCloseChildByRight.getLeft();
                                }
                            }
                        }
                        if (this.mSemTrackedChild != null) {
                            this.mSemDistanceFromTrackedChildLeft =
                                    this.mSemDragStartX - this.mSemTrackedChild.getLeft();
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
                    if (this.mSemDragEndX < 0) {
                        this.mSemDragEndX = 0;
                    } else if (this.mSemDragEndX > contentRight) {
                        this.mSemDragEndX = contentRight;
                    }
                    this.mSemDragSelectedViewPosition = pointToPosition(x, y);
                    this.mSemDragBlockTop =
                            this.mSemDragStartY < this.mSemDragEndY
                                    ? this.mSemDragStartY
                                    : this.mSemDragEndY;
                    this.mSemDragBlockLeft =
                            this.mSemDragStartX < this.mSemDragEndX
                                    ? this.mSemDragStartX
                                    : this.mSemDragEndX;
                    this.mSemDragBlockBottom =
                            this.mSemDragEndY > this.mSemDragStartY
                                    ? this.mSemDragEndY
                                    : this.mSemDragStartY;
                    this.mSemDragBlockRight =
                            this.mSemDragEndX > this.mSemDragStartX
                                    ? this.mSemDragEndX
                                    : this.mSemDragStartX;
                    for (int i3 = 0; i3 < count; i3++) {
                        View child2 = getChildAt(i3);
                        if (child2 != null) {
                            int childLeft2 = child2.getLeft();
                            int childTop = child2.getTop();
                            int childRight2 = child2.getRight();
                            int childBottom = child2.getBottom();
                            if (child2.getVisibility() == 0) {
                                if ((this.mSemDragBlockTop <= childTop
                                                || this.mSemDragBlockLeft <= childLeft2
                                                || this.mSemDragBlockBottom >= childBottom
                                                || this.mSemDragBlockRight >= childRight2)
                                        && (((this.mSemDragBlockTop <= childTop
                                                                || this.mSemDragBlockBottom
                                                                        >= childBottom)
                                                        && ((this.mSemDragBlockTop >= childTop
                                                                        || this.mSemDragBlockBottom
                                                                                <= childTop)
                                                                && (this.mSemDragBlockTop
                                                                                >= childBottom
                                                                        || this.mSemDragBlockBottom
                                                                                <= childBottom)))
                                                || ((this.mSemDragBlockLeft < childLeft2
                                                                || this.mSemDragBlockRight
                                                                        > childRight2)
                                                        && ((this.mSemDragBlockLeft > childLeft2
                                                                        || this.mSemDragBlockRight
                                                                                <= childLeft2)
                                                                && (this.mSemDragBlockLeft
                                                                                >= childRight2
                                                                        || this.mSemDragBlockRight
                                                                                < childRight2))))) {
                                    this.mSemDragSelectedViewPosition =
                                            pointToPosition(childLeft2 + 1, childTop + 1);
                                    if (this.mSemDragSelectedViewPosition != -1
                                            && this.mAdapter.isEnabled(
                                                    this.mSemDragSelectedViewPosition)
                                            && this.mSemDragSelectedItemArray.contains(
                                                    Integer.valueOf(
                                                            this.mSemDragSelectedViewPosition))) {
                                        this.mSemDragSelectedItemArray.remove(
                                                Integer.valueOf(this.mSemDragSelectedViewPosition));
                                        addToPressItemListArray(
                                                this.mSemDragSelectedViewPosition, -1);
                                        semNotifyMultiSelectState(
                                                child2,
                                                this.mSemDragSelectedViewPosition,
                                                getItemIdAtPosition(
                                                        this.mSemDragSelectedViewPosition));
                                    }
                                } else {
                                    this.mSemDragSelectedViewPosition =
                                            pointToPosition(childLeft2 + 1, childTop + 1);
                                    if (this.mSemDragSelectedViewPosition != -1
                                            && this.mAdapter.isEnabled(
                                                    this.mSemDragSelectedViewPosition)
                                            && !this.mSemDragSelectedItemArray.contains(
                                                    Integer.valueOf(
                                                            this.mSemDragSelectedViewPosition))) {
                                        this.mSemDragSelectedItemArray.add(
                                                Integer.valueOf(this.mSemDragSelectedViewPosition));
                                        addToPressItemListArray(
                                                this.mSemDragSelectedViewPosition, -1);
                                        semNotifyMultiSelectState(
                                                child2,
                                                this.mSemDragSelectedViewPosition,
                                                getItemIdAtPosition(
                                                        this.mSemDragSelectedViewPosition));
                                    }
                                }
                            }
                        }
                    }
                    needToScroll = true;
                }
                if (needToScroll) {
                    boolean b1 = x >= contentLeft && x <= this.mHoverLeftAreaWidth + contentLeft;
                    boolean b2 = x >= contentRight - this.mHoverRightAreaWidth && x <= contentRight;
                    if (b1 || b2) {
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
                            this.mHoverScrollDirection = b1 ? 2 : 1;
                            this.mHoverHandler.sendEmptyMessage(1);
                        }
                    } else {
                        if (this.mHoverAreaEnter && this.mOnScrollListener != null) {
                            z2 = false;
                            this.mOnScrollListener.onScrollStateChanged(this, 0);
                        } else {
                            z2 = false;
                        }
                        this.mHoverScrollStartTime = 0L;
                        this.mHoverRecognitionStartTime = 0L;
                        this.mHoverAreaEnter = z2;
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
                this.mPreviousTextViewScroll = needToScroll;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // android.view.View
    @Deprecated
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return isClickable() || isLongClickable();
        }
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        if (this.mIsDetaching || !isAttachedToWindow()) {
            return false;
        }
        startNestedScroll(2);
        if (this.mFastScroll != null) {
            boolean intercepted = this.mFastScroll.onTouchEvent(ev);
            if (intercepted) {
                return true;
            }
        }
        initVelocityTrackerIfNotExists();
        MotionEvent vtev = MotionEvent.obtain(ev);
        int actionMasked = ev.getActionMasked();
        if (actionMasked == 0) {
            this.mNestedXOffset = 0;
        }
        vtev.offsetLocation(this.mNestedXOffset, 0.0f);
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
                    this.mMotionViewOriginalLeft =
                            getChildAt(motionPosition - this.mFirstPosition).getLeft();
                    this.mMotionPosition = motionPosition;
                }
                this.mLastX = x;
                break;
            case 6:
                onSecondaryPointerUp(ev);
                int motionPosition2 = pointToPosition(this.mMotionX, this.mMotionY);
                if (motionPosition2 >= 0) {
                    View child = getChildAt(motionPosition2 - this.mFirstPosition);
                    this.mMotionViewOriginalLeft = child.getLeft();
                    this.mMotionPosition = motionPosition2;
                    if (this.mAdapter != null
                            && this.mAdapter.isEnabled(motionPosition2)
                            && !child.hasFocusable()) {
                        layoutChildren();
                        break;
                    }
                } else {
                    layoutChildren();
                    break;
                }
                break;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(vtev);
        }
        vtev.recycle();
        return true;
    }

    private void onTouchDown(MotionEvent ev) {
        switch (this.mTouchMode) {
            case 6:
                this.mFlingRunnable.endFling();
                if (this.mPositionScroller != null) {
                    this.mPositionScroller.stop();
                }
                this.mTouchMode = 5;
                int x = (int) ev.getX();
                this.mLastX = x;
                this.mMotionX = x;
                this.mMotionY = (int) ev.getY();
                this.mMotionCorrection = 0;
                this.mActivePointerId = ev.getPointerId(0);
                this.mDirection = 0;
                break;
            default:
                this.mActivePointerId = ev.getPointerId(0);
                int x2 = (int) ev.getX();
                int y = (int) ev.getY();
                int motionPosition = pointToPosition(x2, y);
                if (!this.mDataChanged) {
                    if (this.mTouchMode != 4
                            && motionPosition >= 0
                            && getAdapter().isEnabled(motionPosition)) {
                        this.mTouchMode = 0;
                        if (this.mPendingCheckForTap == null) {
                            this.mPendingCheckForTap = new CheckForTap();
                        }
                        postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                    } else if (this.mTouchMode == 4) {
                        createScrollingCache();
                        this.mTouchMode = 3;
                        this.mMotionCorrection = 0;
                        motionPosition = findMotionRow(x2);
                        this.mFlingRunnable.flywheelTouch();
                    }
                }
                if (motionPosition >= 0) {
                    View v = getChildAt(motionPosition - this.mFirstPosition);
                    this.mMotionViewOriginalLeft = v.getLeft();
                }
                this.mMotionX = x2;
                this.mMotionY = y;
                this.mMotionPosition = motionPosition;
                this.mLastX = Integer.MIN_VALUE;
                break;
        }
        if (performButtonActionOnTouchDown(ev) && this.mTouchMode == 0) {
            removeCallbacks(this.mPendingCheckForTap);
        }
    }

    private void onTouchMove(MotionEvent ev, MotionEvent vtev) {
        int pointerIndex = ev.findPointerIndex(this.mActivePointerId);
        if (pointerIndex == -1) {
            pointerIndex = 0;
            this.mActivePointerId = ev.getPointerId(0);
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        int x = (int) ev.getX(pointerIndex);
        switch (this.mTouchMode) {
            case 0:
            case 1:
            case 2:
                if (!startScrollIfNeeded(x, (int) ev.getY(pointerIndex), vtev)) {
                    float y = ev.getY(pointerIndex);
                    if (!pointInView(x, y, this.mTouchSlop)) {
                        setPressed(false);
                        View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
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
                    }
                }
                break;
            case 3:
            case 5:
                scrollIfNeeded(x, (int) ev.getY(pointerIndex), vtev);
                break;
        }
    }

    private void onTouchUp(MotionEvent ev) {
        int firstChildLeft;
        int lastChildRight;
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
                    float y = ev.getY();
                    boolean inList =
                            y > ((float) this.mListPadding.top)
                                    && y < ((float) (getHeight() - this.mListPadding.bottom));
                    if (inList && !child.hasExplicitFocusable()) {
                        if (this.mPerformClick == null) {
                            this.mPerformClick = new PerformClick();
                        }
                        final PerformClick performClick = this.mPerformClick;
                        performClick.mClickMotionPosition = childCount;
                        performClick.rememberWindowAttachCount();
                        this.mResurrectToPosition = childCount;
                        if (this.mTouchMode == 0 || this.mTouchMode == 1) {
                            removeCallbacks(
                                    this.mTouchMode == 0
                                            ? this.mPendingCheckForTap
                                            : this.mPendingCheckForLongPress);
                            this.mLayoutMode = 0;
                            if (!this.mDataChanged && this.mAdapter.isEnabled(childCount)) {
                                this.mTouchMode = 1;
                                setSelectedPositionInt(this.mMotionPosition);
                                layoutChildren();
                                child.setPressed(true);
                                positionSelector(this.mMotionPosition, child);
                                setPressed(true);
                                if (this.mSelector != null) {
                                    Drawable d = this.mSelector.getCurrent();
                                    if (d instanceof TransitionDrawable) {
                                        ((TransitionDrawable) d).resetTransition();
                                    }
                                    this.mSelector.setHotspot(ev.getX(), y);
                                }
                                if (this.mTouchModeReset != null) {
                                    removeCallbacks(this.mTouchModeReset);
                                }
                                this.mTouchModeReset =
                                        new Runnable() { // from class:
                                                         // android.widget.SemHorizontalAbsListView.2
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SemHorizontalAbsListView.this.mTouchModeReset =
                                                        null;
                                                SemHorizontalAbsListView.this.mTouchMode = -1;
                                                child.setPressed(false);
                                                SemHorizontalAbsListView.this.setPressed(false);
                                                if (SemHorizontalAbsListView.this.mForcedClick
                                                        || (!SemHorizontalAbsListView.this
                                                                        .mDataChanged
                                                                && !SemHorizontalAbsListView.this
                                                                        .mIsDetaching
                                                                && SemHorizontalAbsListView.this
                                                                        .isAttachedToWindow())) {
                                                    performClick.run();
                                                }
                                            }
                                        };
                                postDelayed(
                                        this.mTouchModeReset,
                                        ViewConfiguration.getPressedStateDuration());
                                return;
                            }
                            this.mTouchMode = -1;
                            updateSelectorState();
                            if (this.mForcedClick && this.mAdapter.isEnabled(childCount)) {
                                performClick.run();
                                return;
                            }
                            return;
                        }
                        if ((this.mForcedClick || !this.mDataChanged)
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
                if (childCount2 > 0) {
                    if (this.mIsRTL) {
                        firstChildLeft = getChildAt(childCount2 - 1).getLeft();
                        lastChildRight = getChildAt(0).getRight();
                    } else {
                        firstChildLeft = getChildAt(0).getLeft();
                        lastChildRight = getChildAt(childCount2 - 1).getRight();
                    }
                    int contentLeft = this.mListPadding.left;
                    int contentRight = getWidth() - this.mListPadding.right;
                    if (this.mFirstPosition == 0
                            && firstChildLeft >= contentLeft
                            && this.mFirstPosition + childCount2 < this.mItemCount
                            && lastChildRight <= getWidth() - contentRight) {
                        this.mTouchMode = -1;
                        reportScrollStateChange(0);
                        break;
                    } else {
                        VelocityTracker velocityTracker = this.mVelocityTracker;
                        velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                        int initialVelocity =
                                (int)
                                        (velocityTracker.getXVelocity(this.mActivePointerId)
                                                * this.mVelocityScale);
                        if (Math.abs(initialVelocity) > this.mMinimumVelocity
                                && ((this.mFirstPosition != 0
                                                || firstChildLeft
                                                        != contentLeft - this.mOverscrollDistance)
                                        && (this.mFirstPosition + childCount2 != this.mItemCount
                                                || lastChildRight
                                                        != this.mOverscrollDistance
                                                                + contentRight))) {
                            if (this.mFlingRunnable == null) {
                                this.mFlingRunnable = new FlingRunnable();
                            }
                            reportScrollStateChange(2);
                            this.mFlingRunnable.start(-initialVelocity);
                            break;
                        } else {
                            this.mTouchMode = -1;
                            reportScrollStateChange(0);
                            if (this.mFlingRunnable != null) {
                                this.mFlingRunnable.endFling();
                            }
                            if (this.mPositionScroller != null) {
                                this.mPositionScroller.stop();
                                break;
                            }
                        }
                    }
                } else {
                    this.mTouchMode = -1;
                    reportScrollStateChange(0);
                    break;
                }
                break;
            case 5:
                if (this.mFlingRunnable == null) {
                    this.mFlingRunnable = new FlingRunnable();
                }
                VelocityTracker velocityTracker2 = this.mVelocityTracker;
                velocityTracker2.computeCurrentVelocity(1000, this.mMaximumVelocity);
                int initialVelocity2 = (int) velocityTracker2.getXVelocity(this.mActivePointerId);
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
        if (this.mEdgeGlowLeft != null) {
            this.mEdgeGlowLeft.onRelease();
            this.mEdgeGlowRight.onRelease();
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

    private void onTouchCancel() {
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
                this.mTouchMode = -1;
                setPressed(false);
                View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
                if (motionView != null) {
                    motionView.setPressed(false);
                }
                clearScrollingCache();
                removeCallbacks(this.mPendingCheckForLongPress);
                recycleVelocityTracker();
                break;
        }
        if (this.mEdgeGlowLeft != null) {
            this.mEdgeGlowLeft.onRelease();
            this.mEdgeGlowRight.onRelease();
        }
        this.mActivePointerId = -1;
        this.mPointerCount = 0;
    }

    void triggerJumpScrollToTop() {
        this.mJumpScrollToTopState = JUMP_SCROLL_TO_TOP_INITIATED;
        triggerDoubleFling(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postOnJumpScrollToFinished() {
        postOnAnimation(
                new Runnable() { // from class: android.widget.SemHorizontalAbsListView.3
                    @Override // java.lang.Runnable
                    public void run() {
                        SemHorizontalAbsListView.this.onJumpScrollToTopFinished();
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

    private void onHoverDrawableState(MotionEvent event) {
        int action = event.getAction();
        int toolType = event.getToolType(0);
        if ((action == 7 || action == 9) && toolType == 2) {
            this.mIsPenHovered = true;
        } else if (action == 10) {
            this.mIsPenHovered = false;
        }
        if (toolType != 1) {
            this.mIsHoveredByMouse = toolType == 3;
            return;
        }
        this.mIsHoveredByMouse = false;
        if (this.mSelector != null
                && this.mSelector.isStateful()
                && !this.mHoverAreaEnter
                && action == 9
                && !this.mIsPenHovered) {
            this.mSelectorRect.setEmpty();
        }
    }

    @Override // android.view.View
    @Deprecated
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        if (this.mScrollX != scrollX) {
            onScrollChanged(scrollX, this.mScrollY, this.mScrollX, this.mScrollY);
            this.mScrollX = scrollX;
            invalidateParentIfNeeded();
            awakenScrollBars();
        }
    }

    @Override // android.view.View
    @Deprecated
    public boolean onGenericMotionEvent(MotionEvent event) {
        if ((event.getSource() & 2) != 0) {
            switch (event.getAction()) {
                case 8:
                    if (this.mTouchMode == -1) {
                        float hscroll = event.getAxisValue(10);
                        ViewRootImpl viewRootImpl = getViewRootImpl();
                        if (hscroll != 0.0f) {
                            int delta = (int) (getHorizontalScrollFactor() * hscroll);
                            if (!trackMotionScroll(delta, delta)) {
                                return true;
                            }
                        } else if ((viewRootImpl != null && viewRootImpl.isDesktopMode())
                                || (event.getMetaState() & 1) != 0) {
                            float vscroll = event.getAxisValue(9);
                            if (vscroll != 0.0f) {
                                int delta2 = (int) (getVerticalScrollFactor() * vscroll);
                                if (!trackMotionScroll(delta2, delta2)) {
                                    return true;
                                }
                            }
                        }
                    }
                    break;
            }
        }
        return super.onGenericMotionEvent(event);
    }

    public void fling(int velocityX) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        reportScrollStateChange(2);
        this.mFlingRunnable.start(velocityX);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & 1) != 0;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
        startNestedScroll(1);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(
            View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        int myUnconsumed;
        int myConsumed;
        int motionIndex = getChildCount() / 2;
        View motionView = getChildAt(motionIndex);
        int oldLeft = motionView != null ? motionView.getLeft() : 0;
        if (motionView == null || trackMotionScroll(-dxUnconsumed, -dxUnconsumed)) {
            if (motionView == null) {
                myUnconsumed = dxUnconsumed;
                myConsumed = 0;
            } else {
                int myConsumed2 = motionView.getLeft() - oldLeft;
                int myUnconsumed2 = dxUnconsumed - myConsumed2;
                myUnconsumed = myUnconsumed2;
                myConsumed = myConsumed2;
            }
            dispatchNestedScroll(myConsumed, 0, myUnconsumed, 0, null);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    @Deprecated
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        int childCount = getChildCount();
        if (!consumed
                && childCount > 0
                && canScrollList((int) velocityX)
                && Math.abs(velocityX) > this.mMinimumVelocity) {
            reportScrollStateChange(2);
            if (this.mFlingRunnable == null) {
                this.mFlingRunnable = new FlingRunnable();
            }
            if (!dispatchNestedPreFling(velocityX, 0.0f)) {
                this.mFlingRunnable.start((int) velocityX);
                return true;
            }
            return true;
        }
        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override // android.view.View
    @Deprecated
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mEdgeGlowLeft != null) {
            int scrollX = this.mScrollX;
            if (!this.mEdgeGlowLeft.isFinished()) {
                int restoreCount = canvas.save();
                int height = getHeight();
                int edgeX = Math.min(0, this.mFirstPositionDistanceGuess + scrollX);
                canvas.translate(edgeX, height);
                canvas.rotate(270.0f);
                this.mEdgeGlowLeft.setSize(height, getWidth());
                if (this.mEdgeGlowLeft.draw(canvas)) {
                    invalidate(
                            0,
                            0,
                            this.mEdgeGlowLeft.getMaxHeight() + getPaddingLeft(),
                            getHeight());
                }
                canvas.restoreToCount(restoreCount);
            }
            if (!this.mEdgeGlowRight.isFinished()) {
                int restoreCount2 = canvas.save();
                int width = getWidth();
                int height2 = getHeight();
                int edgeX2 = Math.max(width, this.mLastPositionDistanceGuess + scrollX);
                canvas.translate(edgeX2, 0.0f);
                canvas.rotate(90.0f);
                this.mEdgeGlowRight.setSize(height2, width);
                if (this.mEdgeGlowRight.draw(canvas)) {
                    invalidate(
                            (getWidth() - getPaddingRight()) - this.mEdgeGlowRight.getMaxHeight(),
                            0,
                            getWidth(),
                            getHeight());
                }
                canvas.restoreToCount(restoreCount2);
            }
        }
    }

    public void setOverScrollEffectPadding(int topPadding, int bottomPadding) {
        this.mGlowPaddingTop = topPadding;
        this.mGlowPaddingBottom = bottomPadding;
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

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    @Override // android.view.ViewGroup
    @Deprecated
    public boolean onInterceptHoverEvent(MotionEvent event) {
        if (this.mFastScroll != null && this.mFastScroll.onInterceptHoverEvent(event)) {
            return true;
        }
        return super.onInterceptHoverEvent(event);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c4 A[FALL_THROUGH, RETURN] */
    @Override // android.view.ViewGroup
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r11) {
        /*
            r10 = this;
            int r0 = r11.getActionMasked()
            android.widget.SemHorizontalAbsListView$AbsPositionScroller r1 = r10.mPositionScroller
            if (r1 == 0) goto Ld
            android.widget.SemHorizontalAbsListView$AbsPositionScroller r1 = r10.mPositionScroller
            r1.stop()
        Ld:
            boolean r1 = r10.mIsDetaching
            r2 = 0
            if (r1 != 0) goto Lc5
            boolean r1 = r10.isAttachedToWindow()
            if (r1 != 0) goto L1a
            goto Lc5
        L1a:
            com.samsung.android.widget.SemHorizontalFastScroller r1 = r10.mFastScroll
            r3 = 1
            if (r1 == 0) goto L28
            com.samsung.android.widget.SemHorizontalFastScroller r1 = r10.mFastScroll
            boolean r1 = r1.onInterceptTouchEvent(r11)
            if (r1 == 0) goto L28
            return r3
        L28:
            r1 = -1
            switch(r0) {
                case 0: goto L71;
                case 1: goto L63;
                case 2: goto L33;
                case 3: goto L63;
                case 4: goto L2c;
                case 5: goto L2c;
                case 6: goto L2e;
                default: goto L2c;
            }
        L2c:
            goto Lc4
        L2e:
            r10.onSecondaryPointerUp(r11)
            goto Lc4
        L33:
            int r4 = r10.mTouchMode
            switch(r4) {
                case 0: goto L39;
                default: goto L38;
            }
        L38:
            goto L62
        L39:
            int r4 = r10.mActivePointerId
            int r4 = r11.findPointerIndex(r4)
            if (r4 != r1) goto L48
            r4 = 0
            int r1 = r11.getPointerId(r4)
            r10.mActivePointerId = r1
        L48:
            float r1 = r11.getX(r4)
            int r1 = (int) r1
            r10.initVelocityTrackerIfNotExists()
            android.view.VelocityTracker r5 = r10.mVelocityTracker
            r5.addMovement(r11)
            float r5 = r11.getY(r4)
            int r5 = (int) r5
            r6 = 0
            boolean r5 = r10.startScrollIfNeeded(r1, r5, r6)
            if (r5 == 0) goto L62
            return r3
        L62:
            goto Lc4
        L63:
            r10.mTouchMode = r1
            r10.mActivePointerId = r1
            r10.recycleVelocityTracker()
            r10.reportScrollStateChange(r2)
            r10.stopNestedScroll()
            goto Lc4
        L71:
            int r1 = r10.mTouchMode
            r4 = 6
            if (r1 == r4) goto Lc1
            r4 = 5
            if (r1 != r4) goto L7a
            goto Lc1
        L7a:
            float r4 = r11.getX()
            int r4 = (int) r4
            float r5 = r11.getY()
            int r5 = (int) r5
            int r6 = r11.getPointerId(r2)
            r10.mActivePointerId = r6
            int r6 = r10.findMotionRow(r4)
            r7 = 4
            if (r1 == r7) goto Lac
            if (r6 < 0) goto Lac
            int r8 = r10.mFirstPosition
            int r8 = r6 - r8
            android.view.View r8 = r10.getChildAt(r8)
            int r9 = r8.getLeft()
            r10.mMotionViewOriginalLeft = r9
            r10.mMotionX = r4
            r10.mMotionY = r5
            r10.mMotionPosition = r6
            r10.mTouchMode = r2
            r10.clearScrollingCache()
        Lac:
            r8 = -2147483648(0xffffffff80000000, float:-0.0)
            r10.mLastX = r8
            r10.initOrResetVelocityTracker()
            android.view.VelocityTracker r8 = r10.mVelocityTracker
            r8.addMovement(r11)
            r10.mNestedXOffset = r2
            r8 = 2
            r10.startNestedScroll(r8)
            if (r1 != r7) goto Lc4
            return r3
        Lc1:
            r10.mMotionCorrection = r2
            return r3
        Lc4:
            return r2
        Lc5:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.widget.SemHorizontalAbsListView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
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
            this.mLastX = this.mMotionX;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
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

    public void reportScrollStateChange(int newState) {
        if (newState != this.mLastScrollState) {
            if (!this.mHoverAreaEnter && !this.mSemScrollingByScrollbar) {
                if (newState != 0 && this.mLastScrollState == 0) {
                    SemPerfManager.onScrollEvent(true);
                    this.mDVFSLockAcquired = true;
                }
                if (newState == 0 && this.mLastScrollState != 0 && this.mDVFSLockAcquired) {
                    SemPerfManager.onScrollEvent(false);
                    this.mDVFSLockAcquired = false;
                }
            }
            this.mLastScrollState = newState;
            if (this.mOnScrollListener != null && !this.mHoverAreaEnter) {
                this.mOnScrollListener.onScrollStateChanged(this, newState);
            }
        }
    }

    private class FlingRunnable implements Runnable {
        private static final int FLYWHEEL_TIMEOUT = 40;
        private final Runnable mCheckFlywheel =
                new Runnable() { // from class:
                                 // android.widget.SemHorizontalAbsListView.FlingRunnable.1
                    @Override // java.lang.Runnable
                    public void run() {
                        int activeId = SemHorizontalAbsListView.this.mActivePointerId;
                        VelocityTracker vt = SemHorizontalAbsListView.this.mVelocityTracker;
                        OverScroller scroller = FlingRunnable.this.mScroller;
                        if (vt == null || activeId == -1) {
                            return;
                        }
                        vt.computeCurrentVelocity(
                                1000, SemHorizontalAbsListView.this.mMaximumVelocity);
                        float xvel = -vt.getXVelocity(activeId);
                        if (Math.abs(xvel) >= SemHorizontalAbsListView.this.mMinimumVelocity
                                && scroller.isScrollingInDirection(xvel, 0.0f)) {
                            SemHorizontalAbsListView.this.postDelayed(this, 40L);
                            return;
                        }
                        FlingRunnable.this.endFling();
                        SemHorizontalAbsListView.this.mTouchMode = 3;
                        SemHorizontalAbsListView.this.reportScrollStateChange(1);
                    }
                };
        private int mLastFlingX;
        private final OverScroller mScroller;

        FlingRunnable() {
            this.mScroller = new OverScroller(SemHorizontalAbsListView.this.getContext());
        }

        void start(int initialVelocity) {
            int initialX = initialVelocity < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingX = initialX;
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(
                    initialX, 0, initialVelocity, 0, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            SemHorizontalAbsListView.this.mTouchMode = 4;
            SemHorizontalAbsListView.this.postOnAnimation(this);
            if (SemHorizontalAbsListView.this.mFlingStrictSpan == null) {
                SemHorizontalAbsListView.this.mFlingStrictSpan =
                        StrictMode.enterCriticalSpan("SemHorizontalAbsListView-fling");
            }
        }

        void startSpringback() {
            if (this.mScroller.springBack(SemHorizontalAbsListView.this.mScrollX, 0, 0, 0, 0, 0)) {
                SemHorizontalAbsListView.this.mTouchMode = 6;
                SemHorizontalAbsListView.this.invalidate();
                SemHorizontalAbsListView.this.postOnAnimation(this);
            } else {
                SemHorizontalAbsListView.this.mTouchMode = -1;
                SemHorizontalAbsListView.this.reportScrollStateChange(0);
            }
        }

        void startOverfling(int initialVelocity) {
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(
                    SemHorizontalAbsListView.this.mScrollX,
                    0,
                    initialVelocity,
                    0,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE,
                    0,
                    0,
                    SemHorizontalAbsListView.this.getWidth(),
                    0);
            SemHorizontalAbsListView.this.mTouchMode = 6;
            SemHorizontalAbsListView.this.invalidate();
            SemHorizontalAbsListView.this.postOnAnimation(this);
        }

        void edgeReached(int delta) {
            this.mScroller.notifyHorizontalEdgeReached(
                    SemHorizontalAbsListView.this.mScrollX,
                    0,
                    SemHorizontalAbsListView.this.mOverflingDistance);
            int overscrollMode = SemHorizontalAbsListView.this.getOverScrollMode();
            if (overscrollMode == 0
                    || (overscrollMode == 1 && !SemHorizontalAbsListView.this.contentFits())) {
                SemHorizontalAbsListView.this.mTouchMode = 6;
                int vel = (int) this.mScroller.getCurrVelocity();
                if (delta > 0) {
                    SemHorizontalAbsListView.this.mEdgeGlowLeft.onAbsorb(vel);
                } else {
                    SemHorizontalAbsListView.this.mEdgeGlowRight.onAbsorb(vel);
                }
            } else {
                SemHorizontalAbsListView.this.mTouchMode = -1;
                if (SemHorizontalAbsListView.this.mPositionScroller != null) {
                    SemHorizontalAbsListView.this.mPositionScroller.stop();
                }
            }
            SemHorizontalAbsListView.this.invalidate();
            SemHorizontalAbsListView.this.postOnAnimation(this);
        }

        void startScroll(int distance, int duration, boolean linear) {
            int initialX = distance < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingX = initialX;
            this.mScroller.setInterpolator(
                    linear ? SemHorizontalAbsListView.sLinearInterpolator : null);
            this.mScroller.startScroll(initialX, 0, distance, 0, duration);
            SemHorizontalAbsListView.this.mTouchMode = 4;
            SemHorizontalAbsListView.this.postOnAnimation(this);
        }

        void endFling() {
            SemHorizontalAbsListView.this.mTouchMode = -1;
            SemHorizontalAbsListView.this.removeCallbacks(this);
            SemHorizontalAbsListView.this.removeCallbacks(this.mCheckFlywheel);
            SemHorizontalAbsListView.this.reportScrollStateChange(0);
            SemHorizontalAbsListView.this.clearScrollingCache();
            this.mScroller.abortAnimation();
            if (SemHorizontalAbsListView.this.mFlingStrictSpan != null) {
                SemHorizontalAbsListView.this.mFlingStrictSpan.finish();
                SemHorizontalAbsListView.this.mFlingStrictSpan = null;
            }
        }

        void flywheelTouch() {
            SemHorizontalAbsListView.this.postDelayed(this.mCheckFlywheel, 40L);
        }

        @Override // java.lang.Runnable
        public void run() {
            int delta;
            boolean crossLeft = false;
            switch (SemHorizontalAbsListView.this.mTouchMode) {
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
                        int scrollX = SemHorizontalAbsListView.this.mScrollX;
                        int currX = scroller.getCurrX();
                        int deltaX = currX - scrollX;
                        if (SemHorizontalAbsListView.this.overScrollBy(
                                deltaX,
                                0,
                                scrollX,
                                0,
                                0,
                                0,
                                SemHorizontalAbsListView.this.mOverflingDistance,
                                0,
                                false)) {
                            boolean crossRight = scrollX <= 0 && currX > 0;
                            if (scrollX >= 0 && currX < 0) {
                                crossLeft = true;
                            }
                            if (crossRight || crossLeft) {
                                int velocity = (int) scroller.getCurrVelocity();
                                if (crossLeft) {
                                    velocity = -velocity;
                                }
                                scroller.abortAnimation();
                                start(velocity);
                                return;
                            }
                            startSpringback();
                            return;
                        }
                        SemHorizontalAbsListView.this.invalidate();
                        SemHorizontalAbsListView.this.postOnAnimation(this);
                        return;
                    }
                    endFling();
                    return;
            }
            if (SemHorizontalAbsListView.this.mDataChanged) {
                SemHorizontalAbsListView.this.layoutChildren();
            }
            if (SemHorizontalAbsListView.this.mItemCount == 0
                    || SemHorizontalAbsListView.this.getChildCount() == 0) {
                endFling();
                return;
            }
            OverScroller scroller2 = this.mScroller;
            boolean more = scroller2.computeScrollOffset();
            int x = scroller2.getCurrX();
            int delta2 = this.mLastFlingX - x;
            if (delta2 > 0) {
                SemHorizontalAbsListView.this.mMotionPosition =
                        SemHorizontalAbsListView.this.mFirstPosition;
                View firstView = SemHorizontalAbsListView.this.getChildAt(0);
                SemHorizontalAbsListView.this.mMotionViewOriginalLeft = firstView.getLeft();
                delta =
                        Math.min(
                                ((SemHorizontalAbsListView.this.getWidth()
                                                        - SemHorizontalAbsListView.this
                                                                .mPaddingRight)
                                                - SemHorizontalAbsListView.this.mPaddingLeft)
                                        - 1,
                                delta2);
            } else {
                int offsetToLast = SemHorizontalAbsListView.this.getChildCount() - 1;
                SemHorizontalAbsListView.this.mMotionPosition =
                        SemHorizontalAbsListView.this.mFirstPosition + offsetToLast;
                View lastView = SemHorizontalAbsListView.this.getChildAt(offsetToLast);
                SemHorizontalAbsListView.this.mMotionViewOriginalLeft = lastView.getLeft();
                delta =
                        Math.max(
                                -(((SemHorizontalAbsListView.this.getWidth()
                                                        - SemHorizontalAbsListView.this
                                                                .mPaddingRight)
                                                - SemHorizontalAbsListView.this.mPaddingLeft)
                                        - 1),
                                delta2);
            }
            View motionView =
                    SemHorizontalAbsListView.this.getChildAt(
                            SemHorizontalAbsListView.this.mMotionPosition
                                    - SemHorizontalAbsListView.this.mFirstPosition);
            int oldLeft = 0;
            if (motionView != null) {
                oldLeft = motionView.getLeft();
            }
            boolean atEdge = SemHorizontalAbsListView.this.trackMotionScroll(delta, delta);
            if (atEdge && delta != 0) {
                crossLeft = true;
            }
            if (crossLeft) {
                if (motionView != null) {
                    int overshoot = -(delta - (motionView.getLeft() - oldLeft));
                    SemHorizontalAbsListView.this.overScrollBy(
                            overshoot,
                            0,
                            SemHorizontalAbsListView.this.mScrollX,
                            0,
                            0,
                            0,
                            SemHorizontalAbsListView.this.mOverflingDistance,
                            0,
                            false);
                }
                if (more) {
                    edgeReached(delta);
                    return;
                }
                return;
            }
            if (more && !crossLeft) {
                if (atEdge) {
                    SemHorizontalAbsListView.this.invalidate();
                }
                this.mLastFlingX = x;
                SemHorizontalAbsListView.this.postOnAnimation(this);
            } else {
                endFling();
            }
            if (SemHorizontalAbsListView.this.mJumpScrollToTopState
                            == SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_FINISHING
                    && SemHorizontalAbsListView.this.mFirstPosition == 0
                    && delta == 0
                    && !more) {
                SemHorizontalAbsListView.this.mJumpScrollToTopState =
                        SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_IDLE;
                SemHorizontalAbsListView.this.postOnJumpScrollToFinished();
            }
        }
    }

    @Deprecated
    public void setFriction(float friction) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        this.mFlingRunnable.mScroller.setFriction(friction);
    }

    @Deprecated
    public void setVelocityScale(float scale) {
        this.mVelocityScale = scale;
    }

    AbsPositionScroller createPositionScroller() {
        return new PositionScroller();
    }

    @Deprecated
    public void smoothScrollToPosition(int position) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller();
        }
        this.mPositionScroller.start(position);
    }

    @Deprecated
    public void smoothScrollToPositionFromTop(int position, int offset, int duration) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller();
        }
        this.mPositionScroller.startWithOffset(position, offset, duration);
    }

    @Deprecated
    public void smoothScrollToPositionFromTop(int position, int offset) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller();
        }
        this.mPositionScroller.startWithOffset(position, offset);
    }

    @Deprecated
    public void smoothScrollToPosition(int position, int boundPosition) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller();
        }
        this.mPositionScroller.start(position, boundPosition);
    }

    @Deprecated
    public void smoothScrollBy(int distance, int duration) {
        smoothScrollBy(distance, duration, false);
    }

    private class SemSmoothScrollByMove implements Runnable {
        private SemSmoothScrollByMove() {}

        @Override // java.lang.Runnable
        public void run() {
            if (SemHorizontalAbsListView.this.mFlingRunnable.mScroller.isFinished()) {
                if (SemHorizontalAbsListView.this.mSemScrollRemains == null
                        || SemHorizontalAbsListView.this.mSemScrollRemains.isEmpty()) {
                    return;
                }
                Integer remain = (Integer) SemHorizontalAbsListView.this.mSemScrollRemains.poll();
                if (remain != null) {
                    SemHorizontalAbsListView.this.smoothScrollBy(remain.intValue(), 0);
                }
            }
            SemHorizontalAbsListView.this.post(this);
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
        if (this.mAdapter == null) {
            return;
        }
        if (this.mIsRTL) {
            position = (this.mAdapter.getCount() - position) - getChildCount();
        }
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
            mSemScrollAmount = (int) (this.mDensityScale * 150.0f);
        }
        boolean isEmpty = this.mSemScrollRemains.isEmpty();
        if (Math.abs(distance) > mSemScrollAmount) {
            if (distance > 0) {
                while (distance > mSemScrollAmount) {
                    this.mSemScrollRemains.offer(Integer.valueOf(mSemScrollAmount));
                    distance -= mSemScrollAmount;
                }
            } else {
                while (distance < (-mSemScrollAmount)) {
                    this.mSemScrollRemains.offer(Integer.valueOf(-mSemScrollAmount));
                    distance += mSemScrollAmount;
                }
            }
        }
        this.mSemScrollRemains.offer(Integer.valueOf(distance));
        if (isEmpty) {
            post(this.mSemSmoothScrollByMove);
        }
    }

    void smoothScrollBy(int distance, int duration, boolean linear) {
        View leftView;
        View rightView;
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        int firstPos = this.mFirstPosition;
        int childCount = getChildCount();
        int lastPos = firstPos + childCount;
        int leftLimit = getPaddingLeft();
        int rightLimit = getWidth() - getPaddingRight();
        if (this.mIsRTL) {
            leftView = getChildAt(childCount - 1);
            rightView = getChildAt(0);
        } else {
            leftView = getChildAt(0);
            rightView = getChildAt(childCount - 1);
        }
        if (distance == 0
                || this.mItemCount == 0
                || childCount == 0
                || ((!this.mIsRTL
                                && firstPos == 0
                                && leftView.getLeft() == leftLimit
                                && distance < 0)
                        || ((!this.mIsRTL
                                        && lastPos == this.mItemCount
                                        && rightView.getRight() == rightLimit
                                        && distance > 0)
                                || ((this.mIsRTL
                                                && firstPos == 0
                                                && rightView.getRight() == rightLimit
                                                && distance > 0)
                                        || (this.mIsRTL
                                                && lastPos == this.mItemCount
                                                && leftView.getLeft() == leftLimit
                                                && distance < 0))))) {
            this.mFlingRunnable.endFling();
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
                return;
            }
            return;
        }
        reportScrollStateChange(2);
        this.mFlingRunnable.startScroll(distance, duration, linear);
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
                        new Runnable() { // from class: android.widget.SemHorizontalAbsListView.4
                            @Override // java.lang.Runnable
                            public void run() {
                                if (SemHorizontalAbsListView.this.mCachingStarted) {
                                    SemHorizontalAbsListView semHorizontalAbsListView =
                                            SemHorizontalAbsListView.this;
                                    SemHorizontalAbsListView.this.mCachingActive = false;
                                    semHorizontalAbsListView.mCachingStarted = false;
                                    SemHorizontalAbsListView.this.setChildrenDrawnWithCacheEnabled(
                                            false);
                                    if ((SemHorizontalAbsListView.this.mPersistentDrawingCache & 2)
                                            == 0) {
                                        SemHorizontalAbsListView.this
                                                .setChildrenDrawingCacheEnabled(false);
                                    }
                                    if (!SemHorizontalAbsListView.this
                                            .isAlwaysDrawnWithCacheEnabled()) {
                                        SemHorizontalAbsListView.this.invalidate();
                                    }
                                }
                            }
                        };
            }
            post(this.mClearScrollingCache);
        }
    }

    @Deprecated
    public boolean canScrollList(int direction) {
        int lastRight;
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        int firstPosition = this.mFirstPosition;
        Rect listPadding = this.mListPadding;
        if (direction > 0) {
            if (this.mIsRTL) {
                lastRight = getChildAt(0).getRight();
            } else {
                int lastRight2 = childCount - 1;
                lastRight = getChildAt(lastRight2).getRight();
            }
            int lastPosition = firstPosition + childCount;
            if (this.mIsRTL) {
                if (firstPosition <= 0) {
                    return false;
                }
            } else if (lastPosition >= this.mItemCount
                    && lastRight <= getWidth() - listPadding.right) {
                return false;
            }
            return true;
        }
        int firstLeft =
                this.mIsRTL ? getChildAt(childCount - 1).getLeft() : getChildAt(0).getLeft();
        int lastPosition2 = firstPosition + childCount;
        if (this.mIsRTL) {
            if (lastPosition2 >= this.mItemCount) {
                return false;
            }
        } else if (firstPosition <= 0 && firstLeft >= listPadding.left) {
            return false;
        }
        return true;
    }

    boolean trackMotionScroll(int deltaX, int incrementalDeltaX) {
        int spaceAbove;
        int spaceBelow;
        int deltaX2;
        int incrementalDeltaX2;
        boolean cannotScrollRight;
        boolean cannotScrollLeft;
        boolean z;
        boolean cannotScrollLeft2;
        int childCount;
        int count;
        int childCount2;
        int left;
        int right;
        int left2;
        int childCount3 = getChildCount();
        if (childCount3 != 0) {
            int firstLeft = getChildAt(0).getLeft();
            int lastRight = getChildAt(childCount3 - 1).getRight();
            int firstRight = getChildAt(0).getRight();
            int lastLeft = getChildAt(childCount3 - 1).getLeft();
            Rect listPadding = this.mListPadding;
            int effectivePaddingLeft = 0;
            int effectivePaddingRight = 0;
            if ((this.mGroupFlags & 34) == 34) {
                effectivePaddingLeft = listPadding.left;
                effectivePaddingRight = listPadding.right;
            }
            int end = getWidth() - effectivePaddingRight;
            if (this.mIsRTL) {
                spaceAbove = firstRight - end;
                spaceBelow = effectivePaddingLeft - lastLeft;
            } else {
                spaceAbove = effectivePaddingLeft - firstLeft;
                spaceBelow = lastRight - end;
            }
            int width = (getWidth() - this.mPaddingRight) - this.mPaddingLeft;
            if (deltaX < 0) {
                deltaX2 = Math.max(-(width - 1), deltaX);
            } else {
                deltaX2 = Math.min(width - 1, deltaX);
            }
            if (incrementalDeltaX < 0) {
                incrementalDeltaX2 = Math.max(-(width - 1), incrementalDeltaX);
            } else {
                incrementalDeltaX2 = Math.min(width - 1, incrementalDeltaX);
            }
            int firstPosition = this.mFirstPosition;
            if (firstPosition != 0) {
                this.mFirstPositionDistanceGuess += incrementalDeltaX2;
            } else if (this.mIsRTL) {
                this.mFirstPositionDistanceGuess = listPadding.right + firstRight;
            } else {
                this.mFirstPositionDistanceGuess = firstLeft - listPadding.left;
            }
            int i = firstPosition + childCount3;
            int effectivePaddingLeft2 = this.mItemCount;
            if (i != effectivePaddingLeft2) {
                this.mLastPositionDistanceGuess += incrementalDeltaX2;
            } else if (this.mIsRTL) {
                this.mFirstPositionDistanceGuess = listPadding.left + lastLeft;
            } else {
                this.mLastPositionDistanceGuess = listPadding.right + lastRight;
            }
            if (this.mIsRTL) {
                cannotScrollRight =
                        firstPosition + childCount3 == this.mItemCount
                                && lastLeft >= listPadding.left
                                && incrementalDeltaX2 >= 0;
                if (firstPosition == 0
                        && firstRight <= getWidth() - listPadding.right
                        && incrementalDeltaX2 <= 0) {
                    cannotScrollLeft = true;
                }
                cannotScrollLeft = false;
            } else {
                cannotScrollRight =
                        firstPosition == 0
                                && firstLeft >= listPadding.left
                                && incrementalDeltaX2 >= 0;
                cannotScrollLeft =
                        firstPosition + childCount3 == this.mItemCount
                                && lastRight <= getWidth() - listPadding.right
                                && incrementalDeltaX2 <= 0;
            }
            if (cannotScrollRight) {
                z = false;
                cannotScrollLeft2 = true;
            } else {
                if (!cannotScrollLeft) {
                    boolean rightSide = incrementalDeltaX2 < 0;
                    boolean inTouchMode = isInTouchMode();
                    if (inTouchMode) {
                        hideSelector();
                    }
                    int headerViewsCount = getHeaderViewsCount();
                    int footerViewsStart = this.mItemCount - getFooterViewsCount();
                    int start = 0;
                    int count2 = 0;
                    if (this.mIsRTL) {
                        if (rightSide) {
                            int left3 = -incrementalDeltaX2;
                            if ((this.mGroupFlags & 34) == 34) {
                                left3 += listPadding.left;
                            }
                            int i2 = childCount3 - 1;
                            while (i2 >= 0) {
                                View child = getChildAt(i2);
                                int effectivePaddingRight2 = effectivePaddingRight;
                                int effectivePaddingRight3 = child.getRight();
                                if (effectivePaddingRight3 >= left3) {
                                    break;
                                }
                                start = i2;
                                count2++;
                                int position = firstPosition + i2;
                                child.clearAccessibilityFocus();
                                if (position < headerViewsCount || position >= footerViewsStart) {
                                    left2 = left3;
                                } else {
                                    left2 = left3;
                                    this.mRecycler.addScrapView(child, position);
                                }
                                i2--;
                                left3 = left2;
                                effectivePaddingRight = effectivePaddingRight2;
                            }
                            childCount = start;
                            count = count2;
                        } else {
                            int right2 = getWidth() - incrementalDeltaX2;
                            if ((this.mGroupFlags & 34) == 34) {
                                right2 -= listPadding.right;
                            }
                            int i3 = 0;
                            while (i3 < childCount3) {
                                View child2 = getChildAt(i3);
                                if (child2.getLeft() <= right2) {
                                    break;
                                }
                                count2++;
                                int position2 = firstPosition + i3;
                                child2.clearAccessibilityFocus();
                                if (position2 < headerViewsCount || position2 >= footerViewsStart) {
                                    right = right2;
                                } else {
                                    right = right2;
                                    this.mRecycler.addScrapView(child2, position2);
                                }
                                i3++;
                                right2 = right;
                            }
                            childCount = 0;
                            count = count2;
                        }
                    } else if (rightSide) {
                        int left4 = -incrementalDeltaX2;
                        if ((this.mGroupFlags & 34) == 34) {
                            left4 += listPadding.left;
                        }
                        int i4 = 0;
                        while (i4 < childCount3) {
                            View child3 = getChildAt(i4);
                            if (child3.getRight() >= left4) {
                                break;
                            }
                            count2++;
                            int position3 = firstPosition + i4;
                            child3.clearAccessibilityFocus();
                            if (position3 < headerViewsCount || position3 >= footerViewsStart) {
                                left = left4;
                            } else {
                                left = left4;
                                this.mRecycler.addScrapView(child3, position3);
                            }
                            i4++;
                            left4 = left;
                        }
                        childCount = 0;
                        count = count2;
                    } else {
                        int right3 = getWidth() - incrementalDeltaX2;
                        if ((this.mGroupFlags & 34) == 34) {
                            right3 -= listPadding.right;
                        }
                        int i5 = childCount3 - 1;
                        while (i5 >= 0) {
                            View child4 = getChildAt(i5);
                            if (child4.getLeft() <= right3) {
                                break;
                            }
                            start = i5;
                            count2++;
                            int position4 = firstPosition + i5;
                            child4.clearAccessibilityFocus();
                            if (position4 < headerViewsCount || position4 >= footerViewsStart) {
                                childCount2 = childCount3;
                            } else {
                                childCount2 = childCount3;
                                this.mRecycler.addScrapView(child4, position4);
                            }
                            i5--;
                            childCount3 = childCount2;
                        }
                        childCount = start;
                        count = count2;
                    }
                    this.mMotionViewNewLeft = this.mMotionViewOriginalLeft + deltaX2;
                    this.mBlockLayoutRequests = true;
                    if (count > 0) {
                        detachViewsFromParent(childCount, count);
                        this.mRecycler.removeSkippedScrap();
                    }
                    if (!awakenScrollBars()) {
                        invalidate();
                    }
                    semOffsetChildrenLeftAndRight(incrementalDeltaX2);
                    if (this.mIsRTL && !rightSide) {
                        this.mFirstPosition += count;
                    } else if (!this.mIsRTL && rightSide) {
                        this.mFirstPosition += count;
                    }
                    int absIncrementalDeltaX = Math.abs(incrementalDeltaX2);
                    if (this.mIsRTL) {
                        if (spaceAbove < absIncrementalDeltaX
                                || spaceBelow < absIncrementalDeltaX) {
                            fillGapRTL(rightSide);
                        }
                    } else if (spaceAbove < absIncrementalDeltaX
                            || spaceBelow < absIncrementalDeltaX) {
                        fillGap(rightSide);
                    }
                    if (!inTouchMode && this.mSelectedPosition != -1) {
                        int childIndex = this.mSelectedPosition - this.mFirstPosition;
                        if (childIndex >= 0 && childIndex < getChildCount()) {
                            positionSelector(this.mSelectedPosition, getChildAt(childIndex));
                        }
                    } else if (this.mSelectorPosition != -1) {
                        int childIndex2 = this.mSelectorPosition - this.mFirstPosition;
                        if (childIndex2 >= 0 && childIndex2 < getChildCount()) {
                            positionSelector(-1, getChildAt(childIndex2));
                        }
                    } else {
                        this.mSelectorRect.setEmpty();
                    }
                    this.mBlockLayoutRequests = false;
                    invokeOnItemScrollListener();
                    return false;
                }
                z = false;
                cannotScrollLeft2 = true;
            }
            return incrementalDeltaX2 != 0 ? cannotScrollLeft2 : z;
        }
        return true;
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
            this.mSelectedLeft = 0;
        }
    }

    int reconcileSelectedPosition() {
        int position = this.mSelectedPosition;
        if (position < 0) {
            position = this.mResurrectToPosition;
        }
        return Math.min(Math.max(0, position), this.mItemCount - 1);
    }

    int findClosestMotionRow(int x) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return -1;
        }
        int motionRow = findMotionRow(x);
        return motionRow != -1 ? motionRow : (this.mFirstPosition + childCount) - 1;
    }

    @Deprecated
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
        int selectedLeft = 0;
        int selectedRight = 0;
        int childrenLeft = this.mListPadding.left;
        int childrenRight = (this.mRight - this.mLeft) - this.mListPadding.right;
        int firstPosition = this.mFirstPosition;
        int toPosition = this.mResurrectToPosition;
        boolean rightSide = true;
        if (toPosition >= firstPosition && toPosition < firstPosition + childCount) {
            selectedPos = toPosition;
            View selected = getChildAt(selectedPos - this.mFirstPosition);
            selectedLeft = selected.getLeft();
            selectedRight = selected.getRight();
            if (selectedLeft < childrenLeft) {
                selectedLeft = childrenLeft + getHorizontalFadingEdgeLength();
            } else if (selectedRight > childrenRight) {
                selectedLeft =
                        (childrenRight - selected.getMeasuredWidth())
                                - getHorizontalFadingEdgeLength();
            }
        } else if (toPosition < firstPosition) {
            selectedPos = firstPosition;
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                View v = getChildAt(i);
                int left = v.getLeft();
                int right = v.getRight();
                if (i == 0) {
                    selectedLeft = left;
                    selectedRight = right;
                    if (firstPosition > 0 || left < childrenLeft) {
                        childrenLeft += getHorizontalFadingEdgeLength();
                    }
                }
                if (left < childrenLeft) {
                    i++;
                } else {
                    selectedPos = firstPosition + i;
                    selectedLeft = left;
                    selectedRight = right;
                    break;
                }
            }
        } else {
            int selectedPos2 = this.mItemCount;
            rightSide = false;
            int selectedPos3 = (firstPosition + childCount) - 1;
            int i2 = childCount - 1;
            while (true) {
                if (i2 < 0) {
                    selectedPos = selectedPos3;
                    break;
                }
                View v2 = getChildAt(i2);
                int left2 = v2.getLeft();
                int right2 = v2.getRight();
                if (i2 == childCount - 1) {
                    if (firstPosition + childCount >= selectedPos2 && right2 <= childrenRight) {
                        selectedRight = right2;
                        selectedLeft = left2;
                    } else {
                        childrenRight -= getHorizontalFadingEdgeLength();
                        selectedRight = right2;
                        selectedLeft = left2;
                    }
                }
                if (right2 > childrenRight) {
                    i2--;
                } else {
                    int selectedPos4 = firstPosition + i2;
                    selectedLeft = left2;
                    selectedRight = right2;
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
        if (this.mIsRTL) {
            this.mSpecificTop = selectedRight;
        } else {
            this.mSpecificTop = selectedLeft;
        }
        int selectedPos5 = lookForSelectablePosition(selectedPos, rightSide);
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
    @Deprecated
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
                    int listRight = getWidth() - getPaddingRight();
                    View lastChild = this.mIsRTL ? getChildAt(0) : getChildAt(childCount - 1);
                    int lastRight = lastChild != null ? lastChild.getRight() : listRight;
                    if (this.mFirstPosition + childCount >= lastHandledItemCount
                            && lastRight <= listRight) {
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
                                if (this.mSyncHeight == getWidth()) {
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
    @Deprecated
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
    @Deprecated
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
                        this.mSecondPressedPoint = -1;
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
    @Deprecated
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
                        SemHorizontalAbsListView.this
                                .getTextFilterInput()
                                .onCreateInputConnection(this.mOutAttrs);
            }
            return this.mTarget;
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean reportFullscreenMode(boolean enabled) {
            return SemHorizontalAbsListView.this.mDefInputConnection.reportFullscreenMode(enabled);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean performEditorAction(int editorAction) {
            if (editorAction != 6) {
                return false;
            }
            InputMethodManager imm =
                    (InputMethodManager)
                            SemHorizontalAbsListView.this
                                    .getContext()
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(SemHorizontalAbsListView.this.getWindowToken(), 0);
                return true;
            }
            return true;
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean sendKeyEvent(KeyEvent event) {
            return SemHorizontalAbsListView.this.mDefInputConnection.sendKeyEvent(event);
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
    @Deprecated
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

    @Deprecated
    public void clearTextFilter() {
        if (this.mFiltered) {
            getTextFilterInput().lambda$setTextAsync$0("");
            this.mFiltered = false;
            if (this.mPopup != null && this.mPopup.isShowing()) {
                dismissPopup();
            }
        }
    }

    @Deprecated
    public boolean hasTextFilter() {
        return this.mFiltered;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    @Deprecated
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
    @Deprecated
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override // android.text.TextWatcher
    @Deprecated
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
    @Deprecated
    public void afterTextChanged(Editable s) {}

    @Override // android.widget.Filter.FilterListener
    @Deprecated
    public void onFilterComplete(int count) {
        if (this.mSelectedPosition < 0 && count > 0) {
            this.mResurrectToPosition = -1;
            resurrectSelection();
        }
    }

    @Override // android.view.ViewGroup
    @Deprecated
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2, 0);
    }

    @Override // android.view.ViewGroup
    @Deprecated
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override // android.view.ViewGroup
    @Deprecated
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override // android.view.ViewGroup
    @Deprecated
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Deprecated
    public void setTranscriptMode(int mode) {
        this.mTranscriptMode = mode;
    }

    @Deprecated
    public int getTranscriptMode() {
        return this.mTranscriptMode;
    }

    @Override // android.view.View
    @Deprecated
    public int getSolidColor() {
        return this.mCacheColorHint;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    @Deprecated
    public int getCacheColorHint() {
        return this.mCacheColorHint;
    }

    @Deprecated
    public void reclaimViews(List<View> views) {
        int childCount = getChildCount();
        RecyclerListener listener = this.mRecycler.mRecyclerListener;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp != null && this.mRecycler.shouldRecycleViewType(lp.viewType)) {
                views.add(child);
                child.setAccessibilityDelegate(null);
                if (listener != null) {
                    listener.onMovedToScrapHeap(child);
                }
            }
        }
        this.mRecycler.reclaimScrapViews(views);
        removeAllViewsInLayout();
    }

    private void finishGlows() {
        if (this.mEdgeGlowLeft != null) {
            this.mEdgeGlowLeft.finish();
            this.mEdgeGlowRight.finish();
        }
    }

    @Deprecated
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
                return;
            }
        }
        this.mDeferNotifyDataSetChanged = false;
        this.mRemoteAdapter = new RemoteViewsAdapter(getContext(), intent, this, isAsync);
        if (this.mRemoteAdapter.isDataReady()) {
            setAdapter(this.mRemoteAdapter);
        }
    }

    public void setRemoteViewsInteractionHandler(RemoteViews.InteractionHandler handler) {
        if (this.mRemoteAdapter != null) {
            this.mRemoteAdapter.setRemoteViewsInteractionHandler(handler);
        }
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    @Deprecated
    public void deferNotifyDataSetChanged() {
        this.mDeferNotifyDataSetChanged = true;
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    @Deprecated
    public boolean onRemoteAdapterConnected() {
        if (this.mRemoteAdapter != this.mAdapter) {
            setAdapter(this.mRemoteAdapter);
            if (this.mDeferNotifyDataSetChanged) {
                this.mRemoteAdapter.notifyDataSetChanged();
                this.mDeferNotifyDataSetChanged = false;
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
    @Deprecated
    public void onRemoteAdapterDisconnected() {}

    void setVisibleRangeHint(int start, int end) {
        if (this.mRemoteAdapter != null) {
            this.mRemoteAdapter.setVisibleRangeHint(start, end);
        }
    }

    @Deprecated
    public void setRecyclerListener(RecyclerListener listener) {
        this.mRecycler.mRecyclerListener = listener;
    }

    class AdapterDataSetObserver extends AdapterView<ListAdapter>.AdapterDataSetObserver {
        AdapterDataSetObserver() {
            super();
        }

        @Override // android.widget.AdapterView.AdapterDataSetObserver,
                  // android.database.DataSetObserver
        public void onChanged() {
            super.onChanged();
            if (SemHorizontalAbsListView.this.mFastScroll != null) {
                SemHorizontalAbsListView.this.mFastScroll.onSectionsChanged();
            }
        }

        @Override // android.widget.AdapterView.AdapterDataSetObserver,
                  // android.database.DataSetObserver
        public void onInvalidated() {
            super.onInvalidated();
            if (SemHorizontalAbsListView.this.mFastScroll != null) {
                SemHorizontalAbsListView.this.mFastScroll.onSectionsChanged();
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
            SemHorizontalAbsListView.this.setLongClickable(false);
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
            SemHorizontalAbsListView.this.mChoiceActionMode = null;
            SemHorizontalAbsListView.this.clearChoices();
            SemHorizontalAbsListView.this.mDataChanged = true;
            SemHorizontalAbsListView.this.rememberSyncState();
            SemHorizontalAbsListView.this.requestLayout();
            SemHorizontalAbsListView.this.setLongClickable(true);
        }

        @Override // android.widget.SemHorizontalAbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(
                ActionMode mode, int position, long id, boolean checked) {
            this.mWrapped.onItemCheckedStateChanged(mode, position, id, checked);
            if (SemHorizontalAbsListView.this.getCheckedItemCount() == 0
                    && !SemHorizontalAbsListView.this.mSemCustomMultiChoiceMode) {
                mode.finish();
            }
        }
    }

    @Deprecated
    public static class LayoutParams extends ViewGroup.LayoutParams {

        @ViewDebug.ExportedProperty(category = Slice.HINT_LIST)
        boolean forceAdd;

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

        @Deprecated
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.itemId = -1L;
        }

        @Deprecated
        public LayoutParams(int w, int h) {
            super(w, h);
            this.itemId = -1L;
        }

        @Deprecated
        public LayoutParams(int w, int h, int viewType) {
            super(w, h);
            this.itemId = -1L;
            this.viewType = viewType;
        }

        @Deprecated
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            this.itemId = -1L;
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

        public void addShouldRetainView(int position, View v) {}

        public void clearShouldRetainView() {}

        public void removeShouldRetainView(int position) {}

        public Object[] getRetainViewPositions() {
            return null;
        }

        public int getShouldRetainViewCount() {
            return 0;
        }

        public View getShouldRetainView(int position) {
            return null;
        }

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
                View child = SemHorizontalAbsListView.this.getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp != null && lp.viewType != -2) {
                    activeViews[i] = child;
                }
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
            if (SemHorizontalAbsListView.this.mAdapter != null
                    && SemHorizontalAbsListView.this.mAdapterHasStableIds
                    && this.mTransientStateViewsById != null) {
                long id = SemHorizontalAbsListView.this.mAdapter.getItemId(position);
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
            if (this.mViewTypeCount == 1) {
                return retrieveFromScrap(this.mCurrentScrap, position);
            }
            int whichScrap = SemHorizontalAbsListView.this.mAdapter.getItemViewType(position);
            if (whichScrap >= 0 && whichScrap < this.mScrapViews.length) {
                return retrieveFromScrap(this.mScrapViews[whichScrap], position);
            }
            return null;
        }

        void addScrapView(View scrap, boolean ignoreRetainView) {}

        void addScrapView(View scrap, int position) {
            LayoutParams lp;
            if (scrap == null || (lp = (LayoutParams) scrap.getLayoutParams()) == null) {
                return;
            }
            lp.scrappedFromPosition = position;
            int viewType = lp.viewType;
            if (!shouldRecycleViewType(viewType)) {
                return;
            }
            scrap.dispatchStartTemporaryDetach();
            SemHorizontalAbsListView.this.notifyViewAccessibilityStateChangedIfNeeded(1);
            boolean scrapHasTransientState = scrap.hasTransientState();
            if (scrapHasTransientState) {
                if (SemHorizontalAbsListView.this.mAdapter != null
                        && SemHorizontalAbsListView.this.mAdapterHasStableIds) {
                    if (this.mTransientStateViewsById == null) {
                        this.mTransientStateViewsById = new LongSparseArray<>();
                    }
                    this.mTransientStateViewsById.put(lp.itemId, scrap);
                    return;
                } else if (!SemHorizontalAbsListView.this.mDataChanged) {
                    if (this.mTransientStateViews == null) {
                        this.mTransientStateViews = new SparseArray<>();
                    }
                    this.mTransientStateViews.put(position, scrap);
                    return;
                } else {
                    if (this.mSkippedScrap == null) {
                        this.mSkippedScrap = new ArrayList<>();
                    }
                    this.mSkippedScrap.add(scrap);
                    return;
                }
            }
            if (this.mViewTypeCount == 1) {
                this.mCurrentScrap.add(scrap);
            } else if (!this.mScrapViews[viewType].contains(scrap)) {
                this.mScrapViews[viewType].add(scrap);
            }
            if (this.mRecyclerListener != null) {
                this.mRecyclerListener.onMovedToScrapHeap(scrap);
            }
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
                        if (SemHorizontalAbsListView.this.mAdapter != null
                                && SemHorizontalAbsListView.this.mAdapterHasStableIds) {
                            if (this.mTransientStateViewsById == null) {
                                this.mTransientStateViewsById = new LongSparseArray<>();
                            }
                            long id =
                                    SemHorizontalAbsListView.this.mAdapter.getItemId(
                                            this.mFirstActivePosition + i);
                            this.mTransientStateViewsById.put(id, victim);
                        } else if (!SemHorizontalAbsListView.this.mDataChanged) {
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
                        victim.dispatchStartTemporaryDetach();
                        lp.scrappedFromPosition = this.mFirstActivePosition + i;
                        scrapViews.add(victim);
                        if (hasListener) {
                            this.mRecyclerListener.onMovedToScrapHeap(victim);
                        }
                    }
                }
            }
            pruneScrapViews();
        }

        private void pruneScrapViews() {
            int maxViews = this.mActiveViews.length;
            int viewTypeCount = this.mViewTypeCount;
            ArrayList<View>[] scrapViews = this.mScrapViews;
            for (int i = 0; i < viewTypeCount; i++) {
                ArrayList<View> scrapPile = scrapViews[i];
                int size = scrapPile.size();
                int extras = size - maxViews;
                int size2 = size - 1;
                int j = 0;
                while (j < extras) {
                    removeDetachedView(scrapPile.remove(size2), false);
                    j++;
                    size2--;
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
                for (int i = 0; i < size; i++) {
                    View view = scrapViews.get(i);
                    LayoutParams params = (LayoutParams) view.getLayoutParams();
                    if (SemHorizontalAbsListView.this.mAdapterHasStableIds) {
                        long id = SemHorizontalAbsListView.this.mAdapter.getItemId(position);
                        if (id == params.itemId) {
                            return scrapViews.remove(i);
                        }
                    } else if (params.scrappedFromPosition == position) {
                        View scrap = scrapViews.remove(i);
                        clearAccessibilityFromScrap(scrap);
                        return scrap;
                    }
                }
                int i2 = size - 1;
                View scrap2 = scrapViews.remove(i2);
                clearAccessibilityFromScrap(scrap2);
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

        private void clearAccessibilityFromScrap(View view) {
            if (view.isAccessibilityFocused()) {
                view.clearAccessibilityFocus();
            }
            view.setAccessibilityDelegate(null);
        }

        private void removeDetachedView(View child, boolean animate) {
            child.setAccessibilityDelegate(null);
            SemHorizontalAbsListView.this.removeDetachedView(child, animate);
        }
    }

    int getWidthForPosition(int position) {
        int firstVisiblePosition = getFirstVisiblePosition();
        int childCount = getChildCount();
        int index = position - firstVisiblePosition;
        if (index >= 0 && index < childCount) {
            return getChildAt(index).getWidth();
        }
        View view = obtainView(position, this.mIsScrap);
        view.measure(this.mHeightMeasureSpec, 0);
        int width = view.getMeasuredWidth();
        this.mRecycler.addScrapView(view, position);
        return width;
    }

    public void setSelectionFromStart(int position, int x) {
        if (this.mAdapter == null) {
            return;
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
            if (this.mIsRTL) {
                this.mSpecificTop = getWidth() - x;
            } else {
                this.mSpecificTop = this.mListPadding.left + x;
            }
            if (this.mNeedSync) {
                this.mSyncPosition = position;
                this.mSyncRowId = this.mAdapter.getItemId(position);
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            requestLayout();
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
        private int mOffsetFromLeft;
        private int mScrollDuration;
        private int mTargetPos;

        PositionScroller() {
            this.mExtraScroll =
                    ViewConfiguration.get(SemHorizontalAbsListView.this.mContext)
                            .getScaledFadingEdgeLength();
        }

        @Override // android.widget.SemHorizontalAbsListView.AbsPositionScroller
        public void start(final int position) {
            int viewTravelCount;
            stop();
            if (SemHorizontalAbsListView.this.mDataChanged) {
                SemHorizontalAbsListView.this.mPositionScrollAfterLayout =
                        new Runnable() { // from class:
                                         // android.widget.SemHorizontalAbsListView.PositionScroller.1
                            @Override // java.lang.Runnable
                            public void run() {
                                PositionScroller.this.start(position);
                            }
                        };
                return;
            }
            int childCount = SemHorizontalAbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int firstPos = SemHorizontalAbsListView.this.mFirstPosition;
            int lastPos = (firstPos + childCount) - 1;
            int clampedPosition =
                    Math.max(0, Math.min(SemHorizontalAbsListView.this.getCount() - 1, position));
            if (clampedPosition < firstPos) {
                viewTravelCount = (firstPos - clampedPosition) + 1;
                this.mMode = 2;
            } else if (clampedPosition > lastPos) {
                viewTravelCount = (clampedPosition - lastPos) + 1;
                this.mMode = 1;
            } else {
                if (SemHorizontalAbsListView.this.mJumpScrollToTopState
                        == SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_INITIATED) {
                    SemHorizontalAbsListView.this.mJumpScrollToTopState =
                            SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_FINISHING;
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
            SemHorizontalAbsListView.this.postOnAnimation(this);
        }

        @Override // android.widget.SemHorizontalAbsListView.AbsPositionScroller
        public void start(final int position, final int boundPosition) {
            int viewTravelCount;
            stop();
            if (boundPosition == -1) {
                start(position);
                return;
            }
            if (SemHorizontalAbsListView.this.mDataChanged) {
                SemHorizontalAbsListView.this.mPositionScrollAfterLayout =
                        new Runnable() { // from class:
                                         // android.widget.SemHorizontalAbsListView.PositionScroller.2
                            @Override // java.lang.Runnable
                            public void run() {
                                PositionScroller.this.start(position, boundPosition);
                            }
                        };
                return;
            }
            int childCount = SemHorizontalAbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int firstPos = SemHorizontalAbsListView.this.mFirstPosition;
            int lastPos = (firstPos + childCount) - 1;
            int clampedPosition =
                    Math.max(0, Math.min(SemHorizontalAbsListView.this.getCount() - 1, position));
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
            SemHorizontalAbsListView.this.postOnAnimation(this);
        }

        @Override // android.widget.SemHorizontalAbsListView.AbsPositionScroller
        public void startWithOffset(int position, int offset) {
            startWithOffset(position, offset, 200);
        }

        @Override // android.widget.SemHorizontalAbsListView.AbsPositionScroller
        public void startWithOffset(final int position, final int offset, final int duration) {
            int viewTravelCount;
            stop();
            if (SemHorizontalAbsListView.this.mDataChanged) {
                SemHorizontalAbsListView.this.mPositionScrollAfterLayout =
                        new Runnable() { // from class:
                                         // android.widget.SemHorizontalAbsListView.PositionScroller.3
                            @Override // java.lang.Runnable
                            public void run() {
                                PositionScroller.this.startWithOffset(position, offset, duration);
                            }
                        };
                return;
            }
            int childCount = SemHorizontalAbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int offset2 = offset + SemHorizontalAbsListView.this.getPaddingLeft();
            this.mTargetPos =
                    Math.max(0, Math.min(SemHorizontalAbsListView.this.getCount() - 1, position));
            this.mOffsetFromLeft = offset2;
            this.mBoundPos = -1;
            this.mLastSeenPos = -1;
            this.mMode = 5;
            int firstPos = SemHorizontalAbsListView.this.mFirstPosition;
            int lastPos = (firstPos + childCount) - 1;
            if (this.mTargetPos < firstPos) {
                viewTravelCount = firstPos - this.mTargetPos;
            } else if (this.mTargetPos > lastPos) {
                viewTravelCount = this.mTargetPos - lastPos;
            } else {
                int targetLeft =
                        SemHorizontalAbsListView.this
                                .getChildAt(this.mTargetPos - firstPos)
                                .getLeft();
                SemHorizontalAbsListView.this.smoothScrollBy(targetLeft - offset2, duration, true);
                return;
            }
            float screenTravelCount = viewTravelCount / childCount;
            this.mScrollDuration =
                    screenTravelCount < 1.0f ? duration : (int) (duration / screenTravelCount);
            this.mLastSeenPos = -1;
            SemHorizontalAbsListView.this.postOnAnimation(this);
        }

        void scrollToVisible(int targetPos, int boundPos, int duration) {
            int boundPos2 = boundPos;
            int firstPos = SemHorizontalAbsListView.this.mFirstPosition;
            int childCount = SemHorizontalAbsListView.this.getChildCount();
            int lastPos = (firstPos + childCount) - 1;
            int paddedLeft = SemHorizontalAbsListView.this.mListPadding.left;
            int paddedRight =
                    SemHorizontalAbsListView.this.getWidth()
                            - SemHorizontalAbsListView.this.mListPadding.right;
            if (targetPos < firstPos || targetPos > lastPos) {
                Log.w(
                        SemHorizontalAbsListView.TAG,
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
            View targetChild = SemHorizontalAbsListView.this.getChildAt(targetPos - firstPos);
            int targetLeft = targetChild.getLeft();
            int targetRight = targetChild.getRight();
            int scrollBy = 0;
            if (targetRight > paddedRight) {
                scrollBy = targetRight - paddedRight;
            }
            if (targetLeft < paddedLeft) {
                scrollBy = targetLeft - paddedLeft;
            }
            if (scrollBy == 0) {
                if (SemHorizontalAbsListView.this.mJumpScrollToTopState
                        == SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_FINISHING) {
                    SemHorizontalAbsListView.this.mJumpScrollToTopState =
                            SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_IDLE;
                    SemHorizontalAbsListView.this.postOnJumpScrollToFinished();
                    return;
                }
                return;
            }
            if (boundPos2 >= 0) {
                View boundChild = SemHorizontalAbsListView.this.getChildAt(boundPos2 - firstPos);
                int boundLeft = boundChild.getLeft();
                int boundRight = boundChild.getRight();
                int absScroll = Math.abs(scrollBy);
                if (scrollBy < 0 && boundRight + absScroll > paddedRight) {
                    scrollBy = Math.max(0, boundRight - paddedRight);
                } else if (scrollBy > 0 && boundLeft - absScroll < paddedLeft) {
                    scrollBy = Math.min(0, boundLeft - paddedLeft);
                }
            }
            SemHorizontalAbsListView.this.smoothScrollBy(scrollBy, duration);
        }

        @Override // android.widget.SemHorizontalAbsListView.AbsPositionScroller
        public void stop() {
            SemHorizontalAbsListView.this.removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            int listWidth = SemHorizontalAbsListView.this.getWidth();
            int firstPos = SemHorizontalAbsListView.this.mFirstPosition;
            switch (this.mMode) {
                case 1:
                    int lastViewIndex = SemHorizontalAbsListView.this.getChildCount() - 1;
                    int lastPos = firstPos + lastViewIndex;
                    if (lastViewIndex >= 0) {
                        if (lastPos == this.mLastSeenPos) {
                            SemHorizontalAbsListView.this.postOnAnimation(this);
                            break;
                        } else {
                            View lastView = SemHorizontalAbsListView.this.getChildAt(lastViewIndex);
                            int lastViewWidth = lastView.getWidth();
                            int lastViewPixelsShowing = listWidth - lastView.getLeft();
                            int extraScroll =
                                    lastPos < SemHorizontalAbsListView.this.mItemCount - 1
                                            ? Math.max(
                                                    SemHorizontalAbsListView.this
                                                            .mListPadding
                                                            .right,
                                                    this.mExtraScroll)
                                            : SemHorizontalAbsListView.this.mListPadding.right;
                            int scrollBy = (lastViewWidth - lastViewPixelsShowing) + extraScroll;
                            SemHorizontalAbsListView.this.smoothScrollBy(
                                    scrollBy, this.mScrollDuration, true);
                            this.mLastSeenPos = lastPos;
                            if (lastPos < this.mTargetPos) {
                                SemHorizontalAbsListView.this.postOnAnimation(this);
                                break;
                            }
                        }
                    }
                    break;
                case 2:
                    int nextViewIndex = this.mLastSeenPos;
                    if (firstPos == nextViewIndex) {
                        SemHorizontalAbsListView.this.postOnAnimation(this);
                        break;
                    } else {
                        View firstView = SemHorizontalAbsListView.this.getChildAt(0);
                        if (firstView != null) {
                            int firstViewLeft = firstView.getLeft();
                            int extraScroll2 =
                                    firstPos > 0
                                            ? Math.max(
                                                    this.mExtraScroll,
                                                    SemHorizontalAbsListView.this.mListPadding.left)
                                            : SemHorizontalAbsListView.this.mListPadding.left;
                            SemHorizontalAbsListView.this.smoothScrollBy(
                                    firstViewLeft - extraScroll2, this.mScrollDuration, true);
                            this.mLastSeenPos = firstPos;
                            if (firstPos > this.mTargetPos) {
                                SemHorizontalAbsListView.this.postOnAnimation(this);
                                break;
                            } else if (SemHorizontalAbsListView.this.mJumpScrollToTopState
                                    == SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_INITIATED) {
                                SemHorizontalAbsListView.this.mJumpScrollToTopState =
                                        SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_FINISHING;
                                break;
                            }
                        }
                    }
                    break;
                case 3:
                    int childCount = SemHorizontalAbsListView.this.getChildCount();
                    if (firstPos != this.mBoundPos
                            && childCount > 1
                            && firstPos + childCount < SemHorizontalAbsListView.this.mItemCount) {
                        int nextPos = firstPos + 1;
                        if (nextPos == this.mLastSeenPos) {
                            SemHorizontalAbsListView.this.postOnAnimation(this);
                            break;
                        } else {
                            View nextView = SemHorizontalAbsListView.this.getChildAt(1);
                            int nextViewWidth = nextView.getWidth();
                            int nextViewLeft = nextView.getLeft();
                            int extraScroll3 =
                                    Math.max(
                                            SemHorizontalAbsListView.this.mListPadding.right,
                                            this.mExtraScroll);
                            if (nextPos < this.mBoundPos) {
                                SemHorizontalAbsListView.this.smoothScrollBy(
                                        Math.max(0, (nextViewWidth + nextViewLeft) - extraScroll3),
                                        this.mScrollDuration,
                                        true);
                                this.mLastSeenPos = nextPos;
                                SemHorizontalAbsListView.this.postOnAnimation(this);
                                break;
                            } else if (nextViewLeft > extraScroll3) {
                                SemHorizontalAbsListView.this.smoothScrollBy(
                                        nextViewLeft - extraScroll3, this.mScrollDuration, true);
                                break;
                            }
                        }
                    }
                    break;
                case 4:
                    int lastViewIndex2 = SemHorizontalAbsListView.this.getChildCount() - 2;
                    if (lastViewIndex2 >= 0) {
                        int lastPos2 = firstPos + lastViewIndex2;
                        if (lastPos2 == this.mLastSeenPos) {
                            SemHorizontalAbsListView.this.postOnAnimation(this);
                            break;
                        } else {
                            View lastView2 =
                                    SemHorizontalAbsListView.this.getChildAt(lastViewIndex2);
                            int lastViewWidth2 = lastView2.getWidth();
                            int lastViewLeft = lastView2.getLeft();
                            int lastViewPixelsShowing2 = listWidth - lastViewLeft;
                            int extraScroll4 =
                                    Math.max(
                                            SemHorizontalAbsListView.this.mListPadding.left,
                                            this.mExtraScroll);
                            this.mLastSeenPos = lastPos2;
                            if (lastPos2 > this.mBoundPos) {
                                SemHorizontalAbsListView.this.smoothScrollBy(
                                        -(lastViewPixelsShowing2 - extraScroll4),
                                        this.mScrollDuration,
                                        true);
                                SemHorizontalAbsListView.this.postOnAnimation(this);
                                break;
                            } else {
                                int right = listWidth - extraScroll4;
                                int lastViewRight = lastViewLeft + lastViewWidth2;
                                if (right > lastViewRight) {
                                    SemHorizontalAbsListView.this.smoothScrollBy(
                                            -(right - lastViewRight), this.mScrollDuration, true);
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case 5:
                    this.mLastSeenPos = firstPos;
                    int childCount2 = SemHorizontalAbsListView.this.getChildCount();
                    int position = this.mTargetPos;
                    int lastPos3 = (firstPos + childCount2) - 1;
                    int viewTravelCount = 0;
                    if (position < firstPos) {
                        viewTravelCount = (firstPos - position) + 1;
                    } else if (position > lastPos3) {
                        viewTravelCount = position - lastPos3;
                    }
                    float screenTravelCount = viewTravelCount / childCount2;
                    float modifier = Math.min(Math.abs(screenTravelCount), 1.0f);
                    if (position >= firstPos) {
                        if (position > lastPos3) {
                            int duration = (int) (this.mScrollDuration * modifier);
                            SemHorizontalAbsListView.this.smoothScrollBy(
                                    (int)
                                            ((SemHorizontalAbsListView.this.mIsRTL
                                                            ? -SemHorizontalAbsListView.this
                                                                    .getWidth()
                                                            : SemHorizontalAbsListView.this
                                                                    .getWidth())
                                                    * modifier),
                                    duration,
                                    true);
                            SemHorizontalAbsListView.this.postOnAnimation(this);
                            break;
                        } else {
                            int targetLeft =
                                    SemHorizontalAbsListView.this
                                            .getChildAt(position - firstPos)
                                            .getLeft();
                            int distance = targetLeft - this.mOffsetFromLeft;
                            int duration2 =
                                    (int)
                                            (this.mScrollDuration
                                                    * (Math.abs(distance)
                                                            / SemHorizontalAbsListView.this
                                                                    .getWidth()));
                            SemHorizontalAbsListView.this.smoothScrollBy(distance, duration2, true);
                            break;
                        }
                    } else {
                        int duration3 = (int) (this.mScrollDuration * modifier);
                        SemHorizontalAbsListView.this.smoothScrollBy(
                                (int)
                                        ((SemHorizontalAbsListView.this.mIsRTL
                                                        ? SemHorizontalAbsListView.this.getWidth()
                                                        : -SemHorizontalAbsListView.this.getWidth())
                                                * modifier),
                                duration3,
                                true);
                        SemHorizontalAbsListView.this.postOnAnimation(this);
                        break;
                    }
            }
        }
    }

    @Override // android.view.View
    @Deprecated
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        this.mHoverPosition = -1;
        if (visibility != 0) {
            releaseAllBoosters();
        }
    }

    private static class HoverScrollHandler extends Handler {
        private final WeakReference<SemHorizontalAbsListView> mListView;

        HoverScrollHandler(SemHorizontalAbsListView sv) {
            this.mListView = new WeakReference<>(sv);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            SemHorizontalAbsListView sv = this.mListView.get();
            if (sv != null) {
                sv.handleMessage(msg);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                this.mHoverRecognitionCurrentTime = System.currentTimeMillis();
                this.mHoverRecognitionDurationTime =
                        (this.mHoverRecognitionCurrentTime - this.mHoverRecognitionStartTime)
                                / 1000;
                if (!this.mIsPenHovered
                        || this.mHoverRecognitionCurrentTime - this.mHoverScrollStartTime
                                >= this.mHoverScrollTimeInterval) {
                    if (!this.mIsPenPressed
                            || this.mHoverRecognitionCurrentTime - this.mHoverScrollStartTime
                                    >= this.mPenDragScrollTimeInterval) {
                        this.mHoverScrollSpeed =
                                (int)
                                        (TypedValue.applyDimension(
                                                        1,
                                                        this.HOVERSCROLL_SPEED,
                                                        this.mContext
                                                                .getResources()
                                                                .getDisplayMetrics())
                                                + 0.5f);
                        if (this.mHoverRecognitionDurationTime == 3) {
                            this.mHoverScrollSpeed += (int) (this.mHoverScrollSpeed * 0.1d);
                        } else if (this.mHoverRecognitionDurationTime == 4) {
                            this.mHoverScrollSpeed += (int) (this.mHoverScrollSpeed * 0.2d);
                        } else if (this.mHoverRecognitionDurationTime >= 5) {
                            this.mHoverScrollSpeed += (int) (this.mHoverScrollSpeed * 0.3d);
                        }
                        int offset =
                                this.mHoverScrollDirection == 2
                                        ? -this.mHoverScrollSpeed
                                        : this.mHoverScrollSpeed;
                        if ((this.mSemTrackedChild == null && this.mSemCloseChildByRight != null)
                                || (this.mOldHoverScrollDirection != this.mHoverScrollDirection
                                        && this.mIsCloseChildSetted)) {
                            this.mSemTrackedChild = this.mSemCloseChildByRight;
                            this.mSemDistanceFromTrackedChildLeft =
                                    this.mSemDistanceFromCloseChildRight;
                            this.mSemTrackedChildPosition = this.mSemCloseChildPositionByRight;
                            this.mOldHoverScrollDirection = this.mHoverScrollDirection;
                            this.mIsCloseChildSetted = true;
                        }
                        if (getChildAt(getChildCount() - 1) != null) {
                            boolean canOverscroll = false;
                            if (this.mIsRTL
                                    && offset < 0
                                    && (this.mFirstPosition + getChildCount() != getCount()
                                            || getPaddingLeft()
                                                    != getChildAt(getChildCount() - 1).getLeft())) {
                                smoothScrollBy(offset, 0);
                                this.mHoverHandler.sendEmptyMessageDelayed(
                                        1, this.HOVERSCROLL_DELAY);
                                break;
                            } else if (this.mIsRTL
                                    && offset > 0
                                    && (this.mFirstPosition != 0
                                            || getWidth() - getPaddingRight()
                                                    != getChildAt(0).getRight())) {
                                smoothScrollBy(offset, 0);
                                this.mHoverHandler.sendEmptyMessageDelayed(
                                        1, this.HOVERSCROLL_DELAY);
                                break;
                            } else if (!this.mIsRTL
                                    && offset < 0
                                    && (this.mFirstPosition != 0
                                            || getPaddingLeft() != getChildAt(0).getLeft())) {
                                smoothScrollBy(offset, 0);
                                this.mHoverHandler.sendEmptyMessageDelayed(
                                        1, this.HOVERSCROLL_DELAY);
                                break;
                            } else if (!this.mIsRTL
                                    && offset > 0
                                    && (this.mFirstPosition + getChildCount() != getCount()
                                            || getWidth() - getPaddingRight()
                                                    != getChildAt(getChildCount() - 1)
                                                            .getRight())) {
                                smoothScrollBy(offset, 0);
                                this.mHoverHandler.sendEmptyMessageDelayed(
                                        1, this.HOVERSCROLL_DELAY);
                                break;
                            } else {
                                int overscrollMode = getOverScrollMode();
                                if (overscrollMode == 0
                                        || (overscrollMode == 1 && !contentFits())) {
                                    canOverscroll = true;
                                }
                                if (canOverscroll && !this.mIsHoverOverscrolled) {
                                    if (this.mHoverScrollDirection != 2) {
                                        if (this.mHoverScrollDirection == 1) {
                                            this.mEdgeGlowRight.setSize(getWidth(), getHeight());
                                            this.mEdgeGlowRight.onPull(0.4f);
                                            if (!this.mEdgeGlowLeft.isFinished()) {
                                                this.mEdgeGlowLeft.onRelease();
                                            }
                                        }
                                    } else {
                                        this.mEdgeGlowLeft.setSize(getWidth(), getHeight());
                                        this.mEdgeGlowLeft.onPull(0.4f);
                                        if (!this.mEdgeGlowRight.isFinished()) {
                                            this.mEdgeGlowRight.onRelease();
                                        }
                                    }
                                    if (this.mEdgeGlowLeft != null
                                            && (!this.mEdgeGlowLeft.isFinished()
                                                    || !this.mEdgeGlowRight.isFinished())) {
                                        invalidate();
                                    }
                                    this.mIsHoverOverscrolled = true;
                                }
                                if (!canOverscroll && !this.mIsHoverOverscrolled) {
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
        InputDevice inputDevice = ev.getDevice();
        if (inputDevice != null) {
            inputDevice.semSetPointerType(iconId);
        } else {
            Log.e(TAG, "Failed to change PointerIcon to " + iconId);
        }
    }

    private static void log(String log) {
        Log.d(TAG, log);
    }

    public void semSetMultiFocusEnabled(boolean enable) {
        this.mIsMultiFocusEnabled = enable;
    }

    public void semSetDragBlockEnabled(boolean enable) {
        this.mIsDragBlockEnabled = enable;
    }

    public boolean isMultiFocusEnabled() {
        return this.mIsMultiFocusEnabled;
    }

    public void semSetClickableInMultiSelectMode(boolean enable) {
        this.mSemIsOnClickEnabled = enable;
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

    @Override // android.widget.AdapterView
    void rememberSyncState() {
        rememberSyncStateHorizontal();
    }
}
