package android.widget;

import android.app.KeyguardManager;
import android.app.slice.Slice;
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
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.RemoteViews;
import android.widget.RemoteViewsAdapter;
import com.android.internal.R;
import com.samsung.android.os.SemPerfManager;
import com.samsung.android.widget.SemHorizontalFastScroller;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes5.dex */
public abstract class SemHorizontalAbsListView extends AdapterView<ListAdapter> implements TextWatcher, ViewTreeObserver.OnGlobalLayoutListener, Filter.FilterListener, ViewTreeObserver.OnTouchModeChangeListener, RemoteViewsAdapter.RemoteAdapterConnectionCallback {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;

    @Deprecated
    public static final int CHOICE_MODE_MULTIPLE = 2;

    @Deprecated
    public static final int CHOICE_MODE_MULTIPLE_MODAL = 3;

    @Deprecated
    public static final int CHOICE_MODE_NONE = 0;

    @Deprecated
    public static final int CHOICE_MODE_SINGLE = 1;
    private static final boolean DEBUG = false;
    private static boolean DEBUG_VELOCITY_TRACKER_TRACE = false;
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
    private static final String SAVED_STATE_KEY_FOR_BUNDLE = "android.widget.SemHorizontalAbsListView.SavedState";
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

    @Deprecated
    public static final int TRANSCRIPT_MODE_ALWAYS_SCROLL = 2;

    @Deprecated
    public static final int TRANSCRIPT_MODE_DISABLED = 0;

    @Deprecated
    public static final int TRANSCRIPT_MODE_NORMAL = 1;
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

        @Deprecated
        public static final int SCROLL_STATE_FLING = 2;

        @Deprecated
        public static final int SCROLL_STATE_IDLE = 0;

        @Deprecated
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

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

    @Override // android.text.TextWatcher
    @Deprecated
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    @Deprecated
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected void dispatchSetPressed(boolean z) {
    }

    abstract void fillGap(boolean z);

    abstract void fillGapRTL(boolean z);

    abstract int findMotionRow(int i);

    int getFooterViewsCount() {
        return 0;
    }

    int getHeaderViewsCount() {
        return 0;
    }

    @Override // android.view.ViewGroup
    protected boolean isSemUsingAdapterView() {
        return true;
    }

    @Deprecated
    protected void layoutChildren() {
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    @Deprecated
    public void onRemoteAdapterDisconnected() {
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        return (i & 1) != 0;
    }

    @Override // android.view.View
    protected int semGetScaledMinScrollbarTouchTarget(ViewConfiguration viewConfiguration) {
        return 0;
    }

    abstract void setSelectionInt(int i);

    public void setTiltMotionEvent(boolean z) {
    }

    public void updateCustomEdgeGlow(Drawable drawable, Drawable drawable2) {
    }

    private boolean semGetEnableVibrationAtLongPress() {
        return this.mEnableVibrationAtLongPress;
    }

    public void semSetEnableVibrationAtLongPress(boolean z) {
        this.mEnableVibrationAtLongPress = z;
    }

    private void releaseAllBoosters() {
        if (this.mDVFSLockAcquired) {
            SemPerfManager.onScrollEvent(false);
            this.mDVFSLockAcquired = false;
        }
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public void setTouchSlop(int i) {
        this.mTouchSlop = i;
    }

    public void setEnableHoverDrawable(boolean z) {
        this.mHoveringEnabled = z;
    }

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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.View);
        initializeScrollbarsInternal(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    @Deprecated
    public SemHorizontalAbsListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842858);
    }

    @Deprecated
    public SemHorizontalAbsListView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    @Deprecated
    public SemHorizontalAbsListView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AbsListView, i, i2);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        if (drawable != null) {
            setSelector(drawable);
        }
        this.mDrawSelectorOnTop = obtainStyledAttributes.getBoolean(1, false);
        setStackFromBottom(obtainStyledAttributes.getBoolean(2, false));
        setScrollingCacheEnabled(obtainStyledAttributes.getBoolean(3, true));
        setTextFilterEnabled(obtainStyledAttributes.getBoolean(4, false));
        setTranscriptMode(obtainStyledAttributes.getInt(5, 0));
        setFastScrollEnabled(obtainStyledAttributes.getBoolean(8, false));
        setFastScrollStyle(obtainStyledAttributes.getResourceId(11, 0));
        setSmoothScrollbarEnabled(obtainStyledAttributes.getBoolean(9, true));
        setFastScrollAlwaysVisible(obtainStyledAttributes.getBoolean(10, false));
        obtainStyledAttributes.recycle();
    }

    private void initAbsListView() {
        setClickable(true);
        setFocusableInTouchMode(true);
        setWillNotDraw(false);
        setAlwaysDrawnWithCacheEnabled(false);
        setScrollingCacheEnabled(true);
        semEnableHorizontalScrollbar();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mContext);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mOverscrollDistance = viewConfiguration.getScaledOverscrollDistance();
        this.mOverflingDistance = viewConfiguration.getScaledOverflingDistance();
        this.mDensityScale = this.mContext.getResources().getDisplayMetrics().density;
        TypedValue typedValue = new TypedValue();
        if (this.mContext.getTheme().resolveAttribute(R.attr.twListMultiSelectBackground, typedValue, true)) {
            this.mMultiFocusImage = this.mContext.getResources().getDrawable(typedValue.resourceId);
        }
        if (this.mContext.getTheme().resolveAttribute(R.attr.twDragBlockImage, typedValue, true)) {
            this.mSemDragBlockImage = this.mContext.getResources().getDrawable(typedValue.resourceId);
        }
    }

    @Override // android.view.View
    @Deprecated
    public void setOverScrollMode(int i) {
        if (i != 2) {
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
        super.setOverScrollMode(i);
    }

    @Deprecated
    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    @Deprecated
    public boolean isItemChecked(int i) {
        SparseBooleanArray sparseBooleanArray;
        if (this.mChoiceMode == 0 || (sparseBooleanArray = this.mCheckStates) == null) {
            return false;
        }
        return sparseBooleanArray.get(i);
    }

    @Deprecated
    public int getCheckedItemPosition() {
        SparseBooleanArray sparseBooleanArray;
        if (this.mChoiceMode == 1 && (sparseBooleanArray = this.mCheckStates) != null && sparseBooleanArray.size() == 1) {
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
        LongSparseArray<Integer> longSparseArray;
        if (this.mChoiceMode == 0 || (longSparseArray = this.mCheckedIdStates) == null || this.mAdapter == null) {
            return new long[0];
        }
        int size = longSparseArray.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = longSparseArray.keyAt(i);
        }
        return jArr;
    }

    @Deprecated
    public void clearChoices() {
        SparseBooleanArray sparseBooleanArray = this.mCheckStates;
        if (sparseBooleanArray != null) {
            sparseBooleanArray.clear();
        }
        LongSparseArray<Integer> longSparseArray = this.mCheckedIdStates;
        if (longSparseArray != null) {
            longSparseArray.clear();
        }
        this.mCheckedItemCount = 0;
    }

    @Deprecated
    public void setItemChecked(int i, boolean z) {
        int i2 = this.mChoiceMode;
        if (i2 == 0) {
            return;
        }
        if (z && i2 == 3 && this.mChoiceActionMode == null) {
            MultiChoiceModeWrapper multiChoiceModeWrapper = this.mMultiChoiceModeCallback;
            if (multiChoiceModeWrapper == null || !multiChoiceModeWrapper.hasWrappedCallback()) {
                throw new IllegalStateException("SemHorizontalAbsListView: attempted to start selection mode for CHOICE_MODE_MULTIPLE_MODAL but no choice mode callback was supplied. Call setMultiChoiceModeListener to set a callback.");
            }
            this.mChoiceActionMode = startActionMode(this.mMultiChoiceModeCallback);
        }
        int i3 = this.mChoiceMode;
        if (i3 == 2 || i3 == 3) {
            boolean z2 = this.mCheckStates.get(i);
            this.mCheckStates.put(i, z);
            if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                if (z) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(i), Integer.valueOf(i));
                } else {
                    this.mCheckedIdStates.delete(this.mAdapter.getItemId(i));
                }
            }
            if (z2 != z) {
                if (z) {
                    this.mCheckedItemCount++;
                } else {
                    this.mCheckedItemCount--;
                }
            }
            if (this.mChoiceActionMode != null) {
                this.mMultiChoiceModeCallback.onItemCheckedStateChanged(this.mChoiceActionMode, i, this.mAdapter.getItemId(i), z);
            }
        } else {
            boolean z3 = this.mCheckedIdStates != null && this.mAdapter.hasStableIds();
            if (z || isItemChecked(i)) {
                this.mCheckStates.clear();
                if (z3) {
                    this.mCheckedIdStates.clear();
                }
            }
            if (z) {
                this.mCheckStates.put(i, true);
                if (z3) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(i), Integer.valueOf(i));
                }
                this.mCheckedItemCount = 1;
            } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                this.mCheckedItemCount = 0;
            }
        }
        if (this.mInLayout || this.mBlockLayoutRequests) {
            return;
        }
        if (!this.mForcedClick) {
            this.mDataChanged = true;
        }
        rememberSyncState();
        requestLayout();
    }

    @Override // android.widget.AdapterView
    @Deprecated
    public boolean performItemClick(View view, int i, long j) {
        int i2;
        long j2;
        boolean z;
        int i3 = this.mChoiceMode;
        boolean z2 = false;
        boolean z3 = true;
        if (i3 != 0) {
            SparseBooleanArray sparseBooleanArray = this.mCheckStates;
            if (sparseBooleanArray == null || (i3 != 2 && (i3 != 3 || this.mChoiceActionMode == null))) {
                i2 = i;
                j2 = j;
                if (sparseBooleanArray == null || i3 != 1) {
                    z = true;
                } else {
                    if (!sparseBooleanArray.get(i2, false)) {
                        this.mCheckStates.clear();
                        this.mCheckStates.put(i2, true);
                        if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                            this.mCheckedIdStates.clear();
                            this.mCheckedIdStates.put(this.mAdapter.getItemId(i2), Integer.valueOf(i2));
                        }
                        this.mCheckedItemCount = 1;
                    } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                        this.mCheckedItemCount = 0;
                    }
                    z = true;
                    z2 = true;
                }
            } else {
                boolean z4 = sparseBooleanArray.get(i, false);
                boolean z5 = !z4;
                this.mCheckStates.put(i, z5);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    if (!z4) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(i), Integer.valueOf(i));
                    } else {
                        this.mCheckedIdStates.delete(this.mAdapter.getItemId(i));
                    }
                }
                if (!z4) {
                    this.mCheckedItemCount++;
                } else {
                    this.mCheckedItemCount--;
                }
                ActionMode actionMode = this.mChoiceActionMode;
                if (actionMode != null) {
                    i2 = i;
                    j2 = j;
                    this.mMultiChoiceModeCallback.onItemCheckedStateChanged(actionMode, i2, j2, z5);
                } else {
                    i2 = i;
                    j2 = j;
                    z2 = true;
                }
                z = z2;
                z2 = true;
            }
            if (z2) {
                updateOnScreenCheckedViews();
            }
            z2 = true;
            z3 = z;
        } else {
            i2 = i;
            j2 = j;
        }
        return z3 ? super.performItemClick(view, i2, j2) | z2 : z2;
    }

    public boolean semNotifyKeyPressState(View view, int i, long j) {
        boolean z = this.mIsShiftkeyPressed;
        return z && super.semNotifyKeyPress(view, i, j, z);
    }

    private boolean semNotifyMultiSelectState(View view, int i, long j) {
        return super.semNotifyMultiSelectedState(view, i, j, this.mIsShiftkeyPressed, this.mIsCtrlkeyPressed, this.mIsPenPressed);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateOnScreenCheckedViews() {
        int i = this.mFirstPosition;
        int childCount = getChildCount();
        boolean z = getContext().getApplicationInfo().targetSdkVersion >= 11;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int i3 = i + i2;
            if (childAt instanceof Checkable) {
                ((Checkable) childAt).setChecked(this.mCheckStates.get(i3));
            } else if (z) {
                childAt.setActivated(this.mCheckStates.get(i3));
            }
        }
    }

    @Deprecated
    public int getChoiceMode() {
        return this.mChoiceMode;
    }

    @Deprecated
    public void setChoiceMode(int i) {
        ListAdapter listAdapter;
        this.mChoiceMode = i;
        ActionMode actionMode = this.mChoiceActionMode;
        if (actionMode != null) {
            actionMode.finish();
            this.mChoiceActionMode = null;
        }
        if (this.mChoiceMode != 0) {
            if (this.mCheckStates == null) {
                this.mCheckStates = new SparseBooleanArray(0);
            }
            if (this.mCheckedIdStates == null && (listAdapter = this.mAdapter) != null && listAdapter.hasStableIds()) {
                this.mCheckedIdStates = new LongSparseArray<>(0);
            }
            if (this.mChoiceMode == 3) {
                clearChoices();
                setLongClickable(true);
            }
        }
        int i2 = this.mChoiceMode;
        if (i2 == 2) {
            this.mIsDragBlockEnabled = true;
            return;
        }
        if (i2 == 3) {
            this.mIsDragBlockEnabled = true;
        } else if (i2 == 0 || i2 == 1) {
            this.mIsDragBlockEnabled = false;
        }
    }

    @Deprecated
    public void setMultiChoiceModeListener(MultiChoiceModeListener multiChoiceModeListener) {
        if (this.mMultiChoiceModeCallback == null) {
            this.mMultiChoiceModeCallback = new MultiChoiceModeWrapper();
        }
        this.mMultiChoiceModeCallback.setWrapped(multiChoiceModeListener);
    }

    @Deprecated
    public void startMultiChoiceMode() {
        MultiChoiceModeWrapper multiChoiceModeWrapper;
        if (this.mChoiceMode != 3 || (multiChoiceModeWrapper = this.mMultiChoiceModeCallback) == null) {
            return;
        }
        this.mChoiceActionMode = startActionMode(multiChoiceModeWrapper);
    }

    @Deprecated
    public void finishMultiChoiceMode() {
        ActionMode actionMode = this.mChoiceActionMode;
        if (actionMode != null) {
            actionMode.finish();
            this.mChoiceActionMode = null;
        }
    }

    public void semSetCustomMultiChoiceMode(boolean z) {
        this.mSemCustomMultiChoiceMode = z;
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
        return this.mIsRTL ? getChildAt(0).getRight() <= getWidth() - this.mListPadding.right && getChildAt(childCount - 1).getLeft() >= this.mListPadding.left : getChildAt(0).getLeft() >= this.mListPadding.left && getChildAt(childCount - 1).getRight() <= getWidth() - this.mListPadding.right;
    }

    @Deprecated
    public void setFastScrollEnabled(boolean z) {
        if (this.mFastScrollEnabled != z) {
            this.mFastScrollEnabled = z;
            setFastScrollerEnabledUiThread(z);
        }
    }

    private void setFastScrollerEnabledUiThread(boolean z) {
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller != null) {
            semHorizontalFastScroller.setEnabled(z);
        } else if (z) {
            SemHorizontalFastScroller semHorizontalFastScroller2 = new SemHorizontalFastScroller(this, this.mFastScrollStyle);
            this.mFastScroll = semHorizontalFastScroller2;
            semHorizontalFastScroller2.setEnabled(true);
        }
        resolvePadding();
        SemHorizontalFastScroller semHorizontalFastScroller3 = this.mFastScroll;
        if (semHorizontalFastScroller3 != null) {
            semHorizontalFastScroller3.updateLayout();
        }
    }

    @Deprecated
    public void setFastScrollStyle(int i) {
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller == null) {
            this.mFastScrollStyle = i;
        } else {
            semHorizontalFastScroller.setStyle(i);
        }
    }

    @Deprecated
    public void setFastScrollAlwaysVisible(final boolean z) {
        if (this.mFastScrollAlwaysVisible != z) {
            if (z && !this.mFastScrollEnabled) {
                setFastScrollEnabled(true);
            }
            this.mFastScrollAlwaysVisible = z;
            if (isOwnerThread()) {
                setFastScrollerAlwaysVisibleUiThread(z);
            } else {
                post(new Runnable() { // from class: android.widget.SemHorizontalAbsListView.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SemHorizontalAbsListView.this.setFastScrollerAlwaysVisibleUiThread(z);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFastScrollerAlwaysVisibleUiThread(boolean z) {
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller != null) {
            semHorizontalFastScroller.setAlwaysShow(z);
        }
    }

    private boolean isOwnerThread() {
        return this.mOwnerThread == Thread.currentThread();
    }

    @ViewDebug.ExportedProperty
    @Deprecated
    public boolean isFastScrollAlwaysVisible() {
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        return semHorizontalFastScroller == null ? this.mFastScrollEnabled && this.mFastScrollAlwaysVisible : semHorizontalFastScroller.isEnabled() && this.mFastScroll.isAlwaysShowEnabled();
    }

    @Override // android.view.View
    @Deprecated
    public int getHorizontalScrollbarHeight() {
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller != null && semHorizontalFastScroller.isEnabled()) {
            return Math.max(super.getHorizontalScrollbarHeight(), this.mFastScroll.getHeight());
        }
        return super.getHorizontalScrollbarHeight();
    }

    @ViewDebug.ExportedProperty
    @Deprecated
    public boolean isFastScrollEnabled() {
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller == null) {
            return this.mFastScrollEnabled;
        }
        return semHorizontalFastScroller.isEnabled();
    }

    @Override // android.view.View
    @Deprecated
    public void setScrollBarStyle(int i) {
        super.setScrollBarStyle(i);
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller != null) {
            semHorizontalFastScroller.setScrollBarStyle(i);
        }
    }

    @Override // android.view.View
    protected boolean semIsHorizontalScrollBarHidden() {
        return isFastScrollEnabled();
    }

    @Deprecated
    public void setSmoothScrollbarEnabled(boolean z) {
        this.mSmoothScrollbarEnabled = z;
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
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
        invokeOnItemScrollListener();
    }

    void invokeOnItemScrollListener() {
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller != null) {
            semHorizontalFastScroller.onScroll(this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScroll(this, this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        onScrollChanged(0, 0, 0, 0);
    }

    @Override // android.view.View
    @Deprecated
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(SemHorizontalAbsListView.class.getName());
    }

    @Override // android.view.View
    @Deprecated
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(SemHorizontalAbsListView.class.getName());
        if (isEnabled()) {
            if (getFirstVisiblePosition() > 0) {
                accessibilityNodeInfo.addAction(8192);
                accessibilityNodeInfo.setScrollable(true);
            }
            if (getLastVisiblePosition() < getCount() - 1) {
                accessibilityNodeInfo.addAction(4096);
                accessibilityNodeInfo.setScrollable(true);
            }
        }
    }

    int getSelectionModeForAccessibility() {
        int choiceMode = getChoiceMode();
        int i = 1;
        if (choiceMode != 1) {
            i = 2;
            if (choiceMode != 2 && choiceMode != 3) {
                return 0;
            }
        }
        return i;
    }

    @Override // android.view.View
    @Deprecated
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (super.performAccessibilityAction(i, bundle)) {
            return true;
        }
        if (i == 4096) {
            if (!isEnabled() || getLastVisiblePosition() >= getCount() - 1) {
                return false;
            }
            smoothScrollBy((getWidth() - this.mListPadding.left) - this.mListPadding.right, 200);
            return true;
        }
        if (i != 8192 || !isEnabled() || this.mFirstPosition <= 0) {
            return false;
        }
        smoothScrollBy(-((getWidth() - this.mListPadding.left) - this.mListPadding.right), 200);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public View findViewByAccessibilityIdTraversal(int i) {
        if (i == getAccessibilityViewId()) {
            return this;
        }
        if (this.mDataChanged) {
            return null;
        }
        return super.findViewByAccessibilityIdTraversal(i);
    }

    @ViewDebug.ExportedProperty
    @Deprecated
    public boolean isScrollingCacheEnabled() {
        return this.mScrollingCacheEnabled;
    }

    @Deprecated
    public void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled && !z) {
            clearScrollingCache();
        }
        this.mScrollingCacheEnabled = z;
    }

    @Deprecated
    public void setTextFilterEnabled(boolean z) {
        this.mTextFilterEnabled = z;
    }

    @ViewDebug.ExportedProperty
    @Deprecated
    public boolean isTextFilterEnabled() {
        return this.mTextFilterEnabled;
    }

    @Override // android.view.View
    @Deprecated
    public void getFocusedRect(Rect rect) {
        View selectedView = getSelectedView();
        if (selectedView != null && selectedView.getParent() == this) {
            selectedView.getFocusedRect(rect);
            offsetDescendantRectToMyCoords(selectedView, rect);
        } else {
            super.getFocusedRect(rect);
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
    public void setStackFromBottom(boolean z) {
        if (this.mStackFromBottom != z) {
            this.mStackFromBottom = z;
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
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.SemHorizontalAbsListView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
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

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.selectedId = parcel.readLong();
            this.firstId = parcel.readLong();
            this.viewLeft = parcel.readInt();
            this.position = parcel.readInt();
            this.width = parcel.readInt();
            this.filter = parcel.readString();
            this.inActionMode = parcel.readByte() != 0;
            this.checkedItemCount = parcel.readInt();
            this.checkState = parcel.readSparseBooleanArray();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                this.checkIdState = new LongSparseArray<>();
                for (int i = 0; i < readInt; i++) {
                    this.checkIdState.put(parcel.readLong(), Integer.valueOf(parcel.readInt()));
                }
            }
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
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
            LongSparseArray<Integer> longSparseArray = this.checkIdState;
            int size = longSparseArray != null ? longSparseArray.size() : 0;
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeLong(this.checkIdState.keyAt(i2));
                parcel.writeInt(this.checkIdState.valueAt(i2).intValue());
            }
        }

        public String toString() {
            return "SemHorizontalAbsListView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " firstId=" + this.firstId + " viewLeft=" + this.viewLeft + " position=" + this.position + " width=" + this.width + " filter=" + this.filter + " checkState=" + this.checkState + "}";
        }
    }

    @Override // android.view.View
    @Deprecated
    public Parcelable onSaveInstanceState() {
        EditText editText;
        Editable text;
        dismissPopup();
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState savedState2 = this.mPendingSync;
        if (savedState2 != null) {
            savedState.selectedId = savedState2.selectedId;
            savedState.firstId = this.mPendingSync.firstId;
            savedState.viewLeft = this.mPendingSync.viewLeft;
            savedState.position = this.mPendingSync.position;
            savedState.width = this.mPendingSync.width;
            savedState.filter = this.mPendingSync.filter;
            savedState.inActionMode = this.mPendingSync.inActionMode;
            savedState.checkedItemCount = this.mPendingSync.checkedItemCount;
            savedState.checkState = this.mPendingSync.checkState;
            savedState.checkIdState = this.mPendingSync.checkIdState;
            return savedState;
        }
        boolean z = getChildCount() > 0 && this.mItemCount > 0;
        long selectedItemId = getSelectedItemId();
        savedState.selectedId = selectedItemId;
        savedState.width = getWidth();
        if (selectedItemId >= 0) {
            savedState.viewLeft = this.mSelectedLeft;
            savedState.position = getSelectedItemPosition();
            savedState.firstId = -1L;
        } else if (z && this.mFirstPosition > 0) {
            savedState.viewLeft = getChildAt(0).getLeft();
            int i = this.mFirstPosition;
            if (i >= this.mItemCount) {
                i = this.mItemCount - 1;
            }
            savedState.position = i;
            savedState.firstId = this.mAdapter.getItemId(i);
        } else {
            savedState.viewLeft = 0;
            savedState.firstId = -1L;
            savedState.position = 0;
        }
        savedState.filter = null;
        if (this.mFiltered && (editText = this.mTextFilter) != null && (text = editText.getText()) != null) {
            savedState.filter = text.toString();
        }
        savedState.inActionMode = this.mChoiceMode == 3 && this.mChoiceActionMode != null;
        SparseBooleanArray sparseBooleanArray = this.mCheckStates;
        if (sparseBooleanArray != null) {
            savedState.checkState = sparseBooleanArray.m5530clone();
        }
        if (this.mCheckedIdStates != null) {
            LongSparseArray<Integer> longSparseArray = new LongSparseArray<>();
            int size = this.mCheckedIdStates.size();
            for (int i2 = 0; i2 < size; i2++) {
                longSparseArray.put(this.mCheckedIdStates.keyAt(i2), this.mCheckedIdStates.valueAt(i2));
            }
            savedState.checkIdState = longSparseArray;
        }
        savedState.checkedItemCount = this.mCheckedItemCount;
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.saveRemoteViewsCache();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(SAVED_STATE_KEY_FOR_BUNDLE, savedState);
        return bundle;
    }

    @Override // android.view.View
    @Deprecated
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState;
        MultiChoiceModeWrapper multiChoiceModeWrapper;
        if (parcelable instanceof SavedState) {
            savedState = (SavedState) parcelable;
        } else if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            bundle.setClassLoader(SavedState.class.getClassLoader());
            savedState = (SavedState) bundle.getParcelable(SAVED_STATE_KEY_FOR_BUNDLE);
        } else {
            Log.e(TAG, "SemHorizontalAbsListView.onRestoreInstanceState() is of neither SavedState type nor Bundle type, but of " + parcelable.getClass().toString() + " type");
            super.onRestoreInstanceState(parcelable);
            return;
        }
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = savedState.width;
        if (savedState.selectedId >= 0) {
            this.mNeedSync = true;
            this.mPendingSync = savedState;
            this.mSyncRowId = savedState.selectedId;
            this.mSyncPosition = savedState.position;
            this.mSpecificTop = savedState.viewLeft;
            this.mSyncMode = 0;
        } else if (savedState.firstId >= 0) {
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectorPosition = -1;
            this.mNeedSync = true;
            this.mPendingSync = savedState;
            this.mSyncRowId = savedState.firstId;
            this.mSyncPosition = savedState.position;
            this.mSpecificTop = savedState.viewLeft;
            this.mSyncMode = 1;
        }
        setFilterText(savedState.filter);
        if (savedState.checkState != null) {
            this.mCheckStates = savedState.checkState;
        }
        if (savedState.checkIdState != null) {
            this.mCheckedIdStates = savedState.checkIdState;
        }
        this.mCheckedItemCount = savedState.checkedItemCount;
        if (savedState.inActionMode && this.mChoiceMode == 3 && (multiChoiceModeWrapper = this.mMultiChoiceModeCallback) != null) {
            this.mChoiceActionMode = startActionMode(multiChoiceModeWrapper);
        }
        requestLayout();
    }

    private boolean acceptFilter() {
        return this.mTextFilterEnabled && (getAdapter() instanceof Filterable) && ((Filterable) getAdapter()).getFilter() != null;
    }

    @Deprecated
    public void setFilterText(String str) {
        if (!this.mTextFilterEnabled || TextUtils.isEmpty(str)) {
            return;
        }
        createTextFilter(false);
        this.mTextFilter.lambda$setTextAsync$0(str);
        this.mTextFilter.setSelection(str.length());
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter instanceof Filterable) {
            if (this.mPopup == null) {
                ((Filterable) listAdapter).getFilter().filter(str);
            }
            this.mFiltered = true;
            this.mDataSetObserver.clearSavedState();
        }
    }

    @Deprecated
    public CharSequence getTextFilter() {
        EditText editText;
        if (!this.mTextFilterEnabled || (editText = this.mTextFilter) == null) {
            return null;
        }
        return editText.getText();
    }

    @Override // android.view.View
    @Deprecated
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (!z || this.mSelectedPosition >= 0 || isInTouchMode()) {
            return;
        }
        if (!isAttachedToWindow() && this.mAdapter != null) {
            this.mDataChanged = true;
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
        }
        resurrectSelection();
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
        int width;
        int childCount = getChildCount();
        if (childCount <= 0) {
            return 0;
        }
        if (!this.mSmoothScrollbarEnabled) {
            return 1;
        }
        int i = childCount * 100;
        if (this.mIsRTL) {
            View childAt = getChildAt(childCount - 1);
            int left = childAt.getLeft();
            int width2 = childAt.getWidth();
            if (width2 > 0) {
                i += (left * 100) / width2;
            }
            View childAt2 = getChildAt(0);
            int right = childAt2.getRight();
            int width3 = childAt2.getWidth();
            if (width3 <= 0) {
                return i;
            }
            width = ((right - getWidth()) * 100) / width3;
        } else {
            View childAt3 = getChildAt(0);
            int left2 = childAt3.getLeft();
            int width4 = childAt3.getWidth();
            if (width4 > 0) {
                i += (left2 * 100) / width4;
            }
            View childAt4 = getChildAt(childCount - 1);
            int right2 = childAt4.getRight();
            int width5 = childAt4.getWidth();
            if (width5 <= 0) {
                return i;
            }
            width = ((right2 - getWidth()) * 100) / width5;
        }
        return i - width;
    }

    @Override // android.view.View
    @Deprecated
    protected int computeHorizontalScrollOffset() {
        int i = this.mFirstPosition;
        int childCount = getChildCount();
        int childCount2 = (this.mFirstPosition + getChildCount()) - 1;
        int i2 = 0;
        if (i >= 0 && childCount > 0) {
            if (this.mSmoothScrollbarEnabled) {
                if (this.mIsRTL) {
                    View childAt = getChildAt(childCount - 1);
                    int right = childAt.getRight();
                    int width = childAt.getWidth();
                    if (width > 0) {
                        return Math.max((((this.mItemCount - childCount2) * 100) - ((right * 100) / width)) + ((int) ((this.mScrollX / getWidth()) * this.mItemCount * 100.0f)), 0);
                    }
                } else {
                    View childAt2 = getChildAt(0);
                    int left = childAt2.getLeft();
                    int width2 = childAt2.getWidth();
                    if (width2 > 0) {
                        return Math.max(((i * 100) - ((left * 100) / width2)) + ((int) ((this.mScrollX / getWidth()) * this.mItemCount * 100.0f)), 0);
                    }
                }
            } else {
                int i3 = this.mItemCount;
                if (i != 0) {
                    i2 = i + childCount == i3 ? i3 : i + (childCount / 2);
                }
                return (int) (i + (childCount * (i2 / i3)));
            }
        }
        return 0;
    }

    @Override // android.view.View
    @Deprecated
    protected int computeHorizontalScrollRange() {
        if (this.mSmoothScrollbarEnabled) {
            int max = Math.max(this.mItemCount * 100, 0);
            return this.mScrollX != 0 ? max + Math.abs((int) ((this.mScrollX / getWidth()) * this.mItemCount * 100.0f)) : max;
        }
        return this.mItemCount;
    }

    @Override // android.view.View
    @Deprecated
    protected float getLeftFadingEdgeStrength() {
        int childCount = getChildCount();
        float leftFadingEdgeStrength = super.getLeftFadingEdgeStrength();
        if (childCount != 0) {
            if (this.mIsRTL) {
                if ((this.mFirstPosition + childCount) - 1 < this.mItemCount - 1) {
                    return 1.0f;
                }
            } else if (this.mFirstPosition > 0) {
                return 1.0f;
            }
            int left = getChildAt(this.mIsRTL ? childCount - 1 : 0).getLeft();
            float horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
            if (left < this.mPaddingLeft) {
                return (-(left - this.mPaddingLeft)) / horizontalFadingEdgeLength;
            }
        }
        return leftFadingEdgeStrength;
    }

    @Override // android.view.View
    @Deprecated
    protected float getRightFadingEdgeStrength() {
        int childCount = getChildCount();
        float rightFadingEdgeStrength = super.getRightFadingEdgeStrength();
        if (childCount != 0) {
            if (this.mIsRTL) {
                if (this.mFirstPosition > 0) {
                    return 1.0f;
                }
            } else if ((this.mFirstPosition + childCount) - 1 < this.mItemCount - 1) {
                return 1.0f;
            }
            int right = getChildAt(this.mIsRTL ? 0 : childCount - 1).getRight();
            int width = getWidth();
            float horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
            if (right > width - this.mPaddingRight) {
                return ((right - width) + this.mPaddingRight) / horizontalFadingEdgeLength;
            }
        }
        return rightFadingEdgeStrength;
    }

    @Override // android.view.View
    @Deprecated
    protected void onMeasure(int i, int i2) {
        if (this.mSelector == null) {
            useDefaultSelector();
        }
        Rect rect = this.mListPadding;
        rect.left = this.mSelectionLeftPadding + this.mPaddingLeft;
        rect.top = this.mSelectionTopPadding + this.mPaddingTop;
        rect.right = this.mSelectionRightPadding + this.mPaddingRight;
        rect.bottom = this.mSelectionBottomPadding + this.mPaddingBottom;
        if (this.mTranscriptMode == 1) {
            int childCount = getChildCount();
            int width = getWidth() - getPaddingRight();
            View childAt = this.mIsRTL ? getChildAt(0) : getChildAt(childCount - 1);
            this.mForceTranscriptScroll = this.mFirstPosition + childCount >= this.mLastHandledItemCount && (childAt != null ? childAt.getRight() : width) <= width;
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    @Deprecated
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mInLayout = true;
        int childCount = getChildCount();
        if (z) {
            for (int i5 = 0; i5 < childCount; i5++) {
                getChildAt(i5).forceLayout();
            }
            this.mRecycler.markChildrenDirty();
        }
        layoutChildren();
        this.mInLayout = false;
        this.mOverscrollMax = (i3 - i) / 3;
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller != null) {
            semHorizontalFastScroller.onItemCountChanged(getChildCount(), this.mItemCount);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public boolean setFrame(int i, int i2, int i3, int i4) {
        PopupWindow popupWindow;
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (frame) {
            boolean z = getWindowVisibility() == 0;
            if (this.mFiltered && z && (popupWindow = this.mPopup) != null && popupWindow.isShowing()) {
                positionPopup();
            }
        }
        return frame;
    }

    View getAccessibilityFocusedChild(View view) {
        boolean z;
        ViewParent parent = view.getParent();
        while (true) {
            z = parent instanceof View;
            if (!z || parent == this) {
                break;
            }
            view = parent;
            parent = parent.getParent();
        }
        if (z) {
            return view;
        }
        return null;
    }

    void updateScrollIndicators() {
        if (this.mScrollLeft != null) {
            int childCount = getChildCount();
            boolean z = !this.mIsRTL ? this.mFirstPosition <= 0 : this.mFirstPosition + childCount >= this.mItemCount;
            if (!z && childCount > 0) {
                z = (this.mIsRTL ? getChildAt(childCount - 1) : getChildAt(0)).getLeft() < this.mListPadding.left;
            }
            this.mScrollLeft.setVisibility(z ? 0 : 4);
        }
        if (this.mScrollRight != null) {
            int childCount2 = getChildCount();
            boolean z2 = !this.mIsRTL ? this.mFirstPosition + childCount2 >= this.mItemCount : this.mFirstPosition <= 0;
            if (!z2 && childCount2 > 0) {
                z2 = (this.mIsRTL ? getChildAt(0) : getChildAt(childCount2 - 1)).getRight() > this.mRight - this.mListPadding.right;
            }
            this.mScrollRight.setVisibility(z2 ? 0 : 4);
        }
    }

    @Override // android.widget.AdapterView
    @ViewDebug.ExportedProperty
    @Deprecated
    public View getSelectedView() {
        if (this.mItemCount <= 0 || this.mSelectedPosition < 0) {
            return null;
        }
        return getChildAt(this.mSelectedPosition - this.mFirstPosition);
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

    View obtainView(int i, boolean[] zArr) {
        LayoutParams layoutParams;
        Trace.traceBegin(8L, "obtainView");
        zArr[0] = false;
        View transientStateView = this.mRecycler.getTransientStateView(i);
        if (transientStateView != null) {
            if (((LayoutParams) transientStateView.getLayoutParams()).viewType == this.mAdapter.getItemViewType(i)) {
                View view = this.mAdapter.getView(i, transientStateView, this);
                if (this.mAdapterHasStableIds) {
                    ViewGroup.LayoutParams layoutParams2 = transientStateView.getLayoutParams();
                    if (layoutParams2 == null) {
                        layoutParams = (LayoutParams) generateDefaultLayoutParams();
                    } else if (!checkLayoutParams(layoutParams2)) {
                        layoutParams = (LayoutParams) generateLayoutParams(layoutParams2);
                    } else {
                        layoutParams = (LayoutParams) layoutParams2;
                    }
                    layoutParams.itemId = this.mAdapter.getItemId(i);
                    transientStateView.setLayoutParams(layoutParams);
                }
                if (view != transientStateView) {
                    setItemViewLayoutParams(view, i);
                    this.mRecycler.addScrapView(view, i);
                }
            }
            zArr[0] = true;
            return transientStateView;
        }
        View scrapView = this.mRecycler.getScrapView(i);
        View view2 = this.mAdapter.getView(i, scrapView, this);
        if (view2 == null) {
            return null;
        }
        if (scrapView != null) {
            if (view2 != scrapView) {
                if (scrapView.isAccessibilityFocused()) {
                    scrapView.clearAccessibilityFocus();
                    view2.requestAccessibilityFocus();
                }
                this.mRecycler.addScrapView(scrapView, i);
            } else {
                zArr[0] = true;
                view2.dispatchFinishTemporaryDetach();
            }
        }
        int i2 = this.mCacheColorHint;
        if (i2 != 0) {
            view2.setDrawingCacheBackgroundColor(i2);
        }
        if (view2.getImportantForAccessibility() == 0) {
            view2.setImportantForAccessibility(1);
        }
        setItemViewLayoutParams(view2, i);
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            if (this.mAccessibilityDelegate == null) {
                this.mAccessibilityDelegate = new ListItemAccessibilityDelegate();
            }
            if (view2.getAccessibilityDelegate() == null) {
                view2.setAccessibilityDelegate(this.mAccessibilityDelegate);
            }
        }
        Trace.traceEnd(8L);
        return view2;
    }

    private void setItemViewLayoutParams(View view, int i) {
        LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        if (layoutParams2 == null) {
            layoutParams = (LayoutParams) generateDefaultLayoutParams();
        } else if (!checkLayoutParams(layoutParams2)) {
            layoutParams = (LayoutParams) generateLayoutParams(layoutParams2);
        } else {
            layoutParams = (LayoutParams) layoutParams2;
        }
        if (this.mAdapterHasStableIds) {
            layoutParams.itemId = this.mAdapter.getItemId(i);
        }
        layoutParams.viewType = this.mAdapter.getItemViewType(i);
        view.setLayoutParams(layoutParams);
    }

    class ListItemAccessibilityDelegate extends View.AccessibilityDelegate {
        ListItemAccessibilityDelegate() {
        }

        @Override // android.view.View.AccessibilityDelegate
        public AccessibilityNodeInfo createAccessibilityNodeInfo(View view) {
            if (SemHorizontalAbsListView.this.mDataChanged) {
                return null;
            }
            return super.createAccessibilityNodeInfo(view);
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            SemHorizontalAbsListView.this.onInitializeAccessibilityNodeInfoForItem(view, SemHorizontalAbsListView.this.getPositionForView(view), accessibilityNodeInfo);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            int positionForView = SemHorizontalAbsListView.this.getPositionForView(view);
            ListAdapter adapter = SemHorizontalAbsListView.this.getAdapter();
            if (positionForView != -1 && adapter != null && SemHorizontalAbsListView.this.isEnabled() && adapter.isEnabled(positionForView)) {
                long itemIdAtPosition = SemHorizontalAbsListView.this.getItemIdAtPosition(positionForView);
                if (i != 4) {
                    if (i == 8) {
                        if (SemHorizontalAbsListView.this.getSelectedItemPosition() != positionForView) {
                            return false;
                        }
                        SemHorizontalAbsListView.this.setSelection(-1);
                        return true;
                    }
                    if (i == 16) {
                        if (SemHorizontalAbsListView.this.isClickable()) {
                            return SemHorizontalAbsListView.this.performItemClick(view, positionForView, itemIdAtPosition);
                        }
                        return false;
                    }
                    if (i == 32 && SemHorizontalAbsListView.this.isLongClickable()) {
                        return SemHorizontalAbsListView.this.performLongPress(view, positionForView, itemIdAtPosition);
                    }
                    return false;
                }
                if (SemHorizontalAbsListView.this.getSelectedItemPosition() != positionForView) {
                    SemHorizontalAbsListView.this.setSelection(positionForView);
                    return true;
                }
            }
            return false;
        }
    }

    @Deprecated
    public void onInitializeAccessibilityNodeInfoForItem(View view, int i, AccessibilityNodeInfo accessibilityNodeInfo) {
        ListAdapter adapter = getAdapter();
        if (i == -1 || adapter == null) {
            return;
        }
        if (!isEnabled() || !adapter.isEnabled(i)) {
            accessibilityNodeInfo.setEnabled(false);
            return;
        }
        if (i == getSelectedItemPosition()) {
            accessibilityNodeInfo.setSelected(true);
            accessibilityNodeInfo.addAction(8);
        } else {
            accessibilityNodeInfo.addAction(4);
        }
        if (isClickable()) {
            accessibilityNodeInfo.addAction(16);
            accessibilityNodeInfo.setClickable(true);
        }
        if (isLongClickable()) {
            accessibilityNodeInfo.addAction(32);
            accessibilityNodeInfo.setLongClickable(true);
        }
    }

    void positionSelectorLikeTouch(int i, View view, float f, float f2) {
        positionSelectorLikeFocus(i, view);
        Drawable drawable = this.mSelector;
        if (drawable == null || i == -1) {
            return;
        }
        drawable.setHotspot(f, f2);
    }

    void positionSelectorLikeFocus(int i, View view) {
        if (this.mSelector != null && this.mSelectorPosition != i && i != -1) {
            Rect rect = this.mSelectorRect;
            positionSelector(i, view, true, rect.exactCenterX(), rect.exactCenterY());
        } else {
            positionSelector(i, view);
        }
    }

    void positionSelector(int i, View view) {
        positionSelector(i, view, false, -1.0f, -1.0f);
    }

    private void positionSelector(int i, View view, boolean z, float f, float f2) {
        boolean z2 = i != this.mSelectorPosition;
        if (i != -1) {
            this.mSelectorPosition = i;
        }
        Rect rect = this.mSelectorRect;
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        rect.left -= this.mSelectionLeftPadding;
        rect.top -= this.mSelectionTopPadding;
        rect.right += this.mSelectionRightPadding;
        rect.bottom += this.mSelectionBottomPadding;
        Drawable drawable = this.mSelector;
        if (drawable != null) {
            if (z2) {
                drawable.setVisible(false, false);
                drawable.setState(StateSet.NOTHING);
            }
            drawable.setBounds(rect);
            if (z2) {
                if (getVisibility() == 0) {
                    drawable.setVisible(true, false);
                }
                updateSelectorState();
            }
            if (z) {
                drawable.setHotspot(f, f2);
            }
        }
        boolean z3 = this.mIsChildViewEnabled;
        if (view.isEnabled() != z3) {
            this.mIsChildViewEnabled = !z3;
            if (getSelectedItemPosition() != -1) {
                refreshDrawableState();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected void dispatchDraw(Canvas canvas) {
        int i;
        boolean z = (this.mGroupFlags & 34) == 34;
        if (z) {
            i = canvas.save();
            int i2 = this.mScrollX;
            int i3 = this.mScrollY;
            canvas.clipRect(this.mPaddingLeft + i2, this.mPaddingTop + i3, ((i2 + this.mRight) - this.mLeft) - this.mPaddingRight, ((i3 + this.mBottom) - this.mTop) - this.mPaddingBottom);
            this.mGroupFlags &= -35;
        } else {
            i = 0;
        }
        boolean z2 = this.mDrawSelectorOnTop;
        if (!z2) {
            drawSelector(canvas);
        }
        super.dispatchDraw(canvas);
        if (z2) {
            drawSelector(canvas);
        }
        if (z) {
            canvas.restoreToCount(i);
            this.mGroupFlags |= 34;
        }
        if (this.mIsDragBlockEnabled) {
            if (this.mSemDragBlockLeft == 0 && this.mSemDragBlockTop == 0) {
                return;
            }
            int firstVisiblePosition = getFirstVisiblePosition();
            int lastVisiblePosition = getLastVisiblePosition();
            int i4 = this.mSemTrackedChildPosition;
            if (i4 >= firstVisiblePosition && i4 <= lastVisiblePosition) {
                View childAt = getChildAt(i4 - getFirstVisiblePosition());
                this.mSemTrackedChild = childAt;
                this.mSemDragStartX = (childAt != null ? childAt.getLeft() : 0) + this.mSemDistanceFromTrackedChildLeft;
            }
            int i5 = this.mSemDragStartX;
            int i6 = this.mSemDragEndX;
            int i7 = i5 < i6 ? i5 : i6;
            this.mSemDragBlockLeft = i7;
            if (i6 > i5) {
                i5 = i6;
            }
            this.mSemDragBlockRight = i5;
            this.mSemDragBlockRect.set(i7, this.mSemDragBlockTop, i5, this.mSemDragBlockBottom);
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
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        if (getChildCount() > 0) {
            this.mDataChanged = true;
            rememberSyncState();
        }
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller != null) {
            semHorizontalFastScroller.onSizeChanged(i, i2, i3, i4);
        }
    }

    boolean touchModeDrawsInPressedState() {
        int i = this.mTouchMode;
        return i == 1 || i == 2;
    }

    boolean shouldShowSelector() {
        if (!hasFocus() || isInTouchMode()) {
            return touchModeDrawsInPressedState() && isPressed();
        }
        return true;
    }

    boolean shouldShowSelectorDefault() {
        if (isInTouchMode()) {
            return touchModeDrawsInPressedState() && isPressed();
        }
        return true;
    }

    private void drawSelector(Canvas canvas) {
        Rect rect = new Rect();
        if (!this.mSelectorRect.isEmpty()) {
            Drawable drawable = this.mSelector;
            drawable.setBounds(this.mSelectorRect);
            drawable.draw(canvas);
        }
        if (this.mIsMultiFocusEnabled) {
            Iterator<Integer> it = this.mSemPressItemListArray.iterator();
            while (it.hasNext()) {
                View childAt = getChildAt(it.next().intValue() - this.mFirstPosition);
                if (childAt != null) {
                    rect.set(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom());
                    this.mMultiFocusImage.setBounds(rect);
                    this.mMultiFocusImage.draw(canvas);
                }
            }
        }
    }

    @Deprecated
    public void setDrawSelectorOnTop(boolean z) {
        this.mDrawSelectorOnTop = z;
    }

    @Deprecated
    public void setSelector(int i) {
        setSelector(getContext().getDrawable(i));
    }

    @Deprecated
    public void setSelector(Drawable drawable) {
        Drawable drawable2 = this.mSelector;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mSelector);
        }
        this.mSelector = drawable;
        Rect rect = new Rect();
        drawable.getPadding(rect);
        this.mSelectionLeftPadding = rect.left;
        this.mSelectionTopPadding = rect.top;
        this.mSelectionRightPadding = rect.right;
        this.mSelectionBottomPadding = rect.bottom;
        drawable.setCallback(this);
        updateSelectorState();
    }

    @Deprecated
    public Drawable getSelector() {
        return this.mSelector;
    }

    void keyPressed() {
        if (isEnabled() && isClickable()) {
            Drawable drawable = this.mSelector;
            Rect rect = this.mSelectorRect;
            if (drawable != null) {
                if ((isFocused() || touchModeDrawsInPressedState()) && !rect.isEmpty()) {
                    View childAt = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                    if (childAt != null) {
                        if (childAt.hasExplicitFocusable()) {
                            return;
                        } else {
                            childAt.setPressed(true);
                        }
                    }
                    setPressed(true);
                    boolean isLongClickable = isLongClickable();
                    Drawable current = drawable.getCurrent();
                    if (current != null && (current instanceof TransitionDrawable)) {
                        if (isLongClickable) {
                            ((TransitionDrawable) current).startTransition(ViewConfiguration.getLongPressTimeout());
                        } else {
                            ((TransitionDrawable) current).resetTransition();
                        }
                    }
                    if (!isLongClickable || this.mDataChanged) {
                        return;
                    }
                    CheckForKeyLongPress checkForKeyLongPress = this.mPendingCheckForKeyLongPress;
                    if (checkForKeyLongPress == null) {
                        this.mPendingCheckForKeyLongPress = new CheckForKeyLongPress();
                    } else {
                        removeCallbacks(checkForKeyLongPress);
                    }
                    this.mPendingCheckForKeyLongPress.rememberWindowAttachCount();
                    postDelayed(this.mPendingCheckForKeyLongPress, ViewConfiguration.getLongPressTimeout());
                }
            }
        }
    }

    @Deprecated
    public void setScrollIndicators(View view, View view2) {
        this.mScrollLeft = view;
        this.mScrollRight = view2;
    }

    void updateSelectorState() {
        if (this.mSelector != null) {
            if (shouldShowSelector()) {
                if (isHovered() && !this.mIsHoveredByMouse && this.mSelectorPosition >= this.mFirstPosition) {
                    View childAt = getChildAt(this.mSelectorPosition - this.mFirstPosition);
                    if (!this.mIsPenHovered && childAt != null && !childAt.isEnabled()) {
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
    protected int[] onCreateDrawableState(int i) {
        if (this.mIsChildViewEnabled) {
            return super.onCreateDrawableState(i);
        }
        int i2 = ENABLED_STATE_SET[0];
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        int length = onCreateDrawableState.length - 1;
        while (true) {
            if (length < 0) {
                length = -1;
                break;
            }
            if (onCreateDrawableState[length] == i2) {
                break;
            }
            length--;
        }
        if (length >= 0) {
            System.arraycopy(onCreateDrawableState, length + 1, onCreateDrawableState, length, (onCreateDrawableState.length - length) - 1);
        }
        return onCreateDrawableState;
    }

    @Override // android.view.View
    @Deprecated
    public boolean verifyDrawable(Drawable drawable) {
        return this.mSelector == drawable || super.verifyDrawable(drawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mSelector;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected void onAttachedToWindow() {
        SemHorizontalFastScroller semHorizontalFastScroller;
        super.onAttachedToWindow();
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnTouchModeChangeListener(this);
        if (this.mTextFilterEnabled && this.mPopup != null && !this.mGlobalLayoutListenerAddedFilter) {
            viewTreeObserver.addOnGlobalLayoutListener(this);
        }
        if (this.mAdapter != null && this.mDataSetObserver == null) {
            AdapterDataSetObserver adapterDataSetObserver = new AdapterDataSetObserver();
            this.mDataSetObserver = adapterDataSetObserver;
            this.mAdapter.registerDataSetObserver(adapterDataSetObserver);
            this.mDataChanged = true;
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
        }
        if (!isLayoutRtl() || (semHorizontalFastScroller = this.mFastScroll) == null) {
            return;
        }
        semHorizontalFastScroller.setScrollbarPosition(getVerticalScrollbarPosition());
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    @Deprecated
    protected void onDetachedFromWindow() {
        AdapterDataSetObserver adapterDataSetObserver;
        super.onDetachedFromWindow();
        this.mIsDetaching = true;
        dismissPopup();
        this.mRecycler.clear();
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.removeOnTouchModeChangeListener(this);
        if (this.mTextFilterEnabled && this.mPopup != null) {
            viewTreeObserver.removeOnGlobalLayoutListener(this);
            this.mGlobalLayoutListenerAddedFilter = false;
        }
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null && (adapterDataSetObserver = this.mDataSetObserver) != null) {
            listAdapter.unregisterDataSetObserver(adapterDataSetObserver);
            this.mDataSetObserver = null;
        }
        StrictMode.Span span = this.mScrollStrictSpan;
        if (span != null) {
            span.finish();
            this.mScrollStrictSpan = null;
        }
        StrictMode.Span span2 = this.mFlingStrictSpan;
        if (span2 != null) {
            span2.finish();
            this.mFlingStrictSpan = null;
        }
        FlingRunnable flingRunnable = this.mFlingRunnable;
        if (flingRunnable != null) {
            removeCallbacks(flingRunnable);
        }
        AbsPositionScroller absPositionScroller = this.mPositionScroller;
        if (absPositionScroller != null) {
            absPositionScroller.stop();
        }
        Runnable runnable = this.mClearScrollingCache;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        PerformClick performClick = this.mPerformClick;
        if (performClick != null) {
            removeCallbacks(performClick);
        }
        Runnable runnable2 = this.mTouchModeReset;
        if (runnable2 != null) {
            removeCallbacks(runnable2);
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
            FlingRunnable flingRunnable = this.mFlingRunnable;
            if (flingRunnable != null) {
                removeCallbacks(flingRunnable);
                this.mFlingRunnable.endFling();
                AbsPositionScroller absPositionScroller = this.mPositionScroller;
                if (absPositionScroller != null) {
                    absPositionScroller.stop();
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
            int i2 = this.mLastTouchMode;
            if (i != i2 && i2 != -1) {
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
        if (z) {
            return;
        }
        releaseAllBoosters();
    }

    @Override // android.view.View
    @Deprecated
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        this.mIsRTL = isLayoutRtl();
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller != null) {
            semHorizontalFastScroller.setScrollbarPosition(semGetHorizontalScrollbarPosition());
        }
    }

    ContextMenu.ContextMenuInfo createContextMenuInfo(View view, int i, long j) {
        return new AdapterView.AdapterContextMenuInfo(view, i, j);
    }

    @Override // android.view.View
    @Deprecated
    public void onCancelPendingInputEvents() {
        super.onCancelPendingInputEvents();
        PerformClick performClick = this.mPerformClick;
        if (performClick != null) {
            removeCallbacks(performClick);
        }
        CheckForTap checkForTap = this.mPendingCheckForTap;
        if (checkForTap != null) {
            removeCallbacks(checkForTap);
        }
        CheckForLongPress checkForLongPress = this.mPendingCheckForLongPress;
        if (checkForLongPress != null) {
            removeCallbacks(checkForLongPress);
        }
        CheckForKeyLongPress checkForKeyLongPress = this.mPendingCheckForKeyLongPress;
        if (checkForKeyLongPress != null) {
            removeCallbacks(checkForKeyLongPress);
        }
    }

    private class WindowRunnnable {
        private int mOriginalAttachCount;

        private WindowRunnnable() {
        }

        public void rememberWindowAttachCount() {
            this.mOriginalAttachCount = SemHorizontalAbsListView.this.getWindowAttachCount();
        }

        public boolean sameWindow() {
            return SemHorizontalAbsListView.this.getWindowAttachCount() == this.mOriginalAttachCount;
        }
    }

    public void setForcedClick(boolean z) {
        this.mForcedClick = z;
    }

    private class PerformClick extends WindowRunnnable implements Runnable {
        int mClickMotionPosition;

        private PerformClick() {
            super();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SemHorizontalAbsListView.this.mForcedClick || !SemHorizontalAbsListView.this.mDataChanged) {
                ListAdapter listAdapter = SemHorizontalAbsListView.this.mAdapter;
                int i = this.mClickMotionPosition;
                if (listAdapter == null || SemHorizontalAbsListView.this.mItemCount <= 0 || i == -1 || i >= listAdapter.getCount() || !sameWindow()) {
                    return;
                }
                SemHorizontalAbsListView semHorizontalAbsListView = SemHorizontalAbsListView.this;
                View childAt = semHorizontalAbsListView.getChildAt(i - semHorizontalAbsListView.mFirstPosition);
                if (childAt != null) {
                    try {
                        SemHorizontalAbsListView.this.performItemClick(childAt, i, listAdapter.getItemId(i));
                        if (SemHorizontalAbsListView.this.mIsShiftkeyPressed || SemHorizontalAbsListView.this.mIsCtrlkeyPressed) {
                            SemHorizontalAbsListView.this.semNotifyKeyPressState(childAt, i, listAdapter.getItemId(i));
                        }
                        if ((SemHorizontalAbsListView.this.mIsShiftkeyPressed || SemHorizontalAbsListView.this.mIsCtrlkeyPressed) && SemHorizontalAbsListView.this.mAdapter != null) {
                            if (SemHorizontalAbsListView.this.mIsCtrlkeyPressed) {
                                SemHorizontalAbsListView.this.addToPressItemListArray(i, -1);
                                return;
                            }
                            if (SemHorizontalAbsListView.this.mIsShiftkeyPressed) {
                                SemHorizontalAbsListView.this.resetPressItemListArray();
                                if (SemHorizontalAbsListView.this.mFirstPressedPoint == -1) {
                                    SemHorizontalAbsListView.this.addToPressItemListArray(i, -1);
                                    SemHorizontalAbsListView.this.mFirstPressedPoint = i;
                                } else {
                                    SemHorizontalAbsListView semHorizontalAbsListView2 = SemHorizontalAbsListView.this;
                                    semHorizontalAbsListView2.addToPressItemListArray(semHorizontalAbsListView2.mFirstPressedPoint, i);
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
            int i = SemHorizontalAbsListView.this.mMotionPosition;
            SemHorizontalAbsListView semHorizontalAbsListView = SemHorizontalAbsListView.this;
            View childAt = semHorizontalAbsListView.getChildAt(i - semHorizontalAbsListView.mFirstPosition);
            if (childAt != null) {
                if ((!sameWindow() || SemHorizontalAbsListView.this.mDataChanged) ? false : SemHorizontalAbsListView.this.performLongPress(childAt, SemHorizontalAbsListView.this.mMotionPosition, SemHorizontalAbsListView.this.mAdapter.getItemId(SemHorizontalAbsListView.this.mMotionPosition))) {
                    SemHorizontalAbsListView.this.mTouchMode = -1;
                    SemHorizontalAbsListView.this.setPressed(false);
                    childAt.setPressed(false);
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
            boolean z;
            if (!SemHorizontalAbsListView.this.isPressed() || SemHorizontalAbsListView.this.mSelectedPosition < 0) {
                return;
            }
            View childAt = SemHorizontalAbsListView.this.getChildAt(SemHorizontalAbsListView.this.mSelectedPosition - SemHorizontalAbsListView.this.mFirstPosition);
            if (childAt == null) {
                return;
            }
            if (!SemHorizontalAbsListView.this.mDataChanged) {
                if (sameWindow()) {
                    SemHorizontalAbsListView semHorizontalAbsListView = SemHorizontalAbsListView.this;
                    z = semHorizontalAbsListView.performLongPress(childAt, semHorizontalAbsListView.mSelectedPosition, SemHorizontalAbsListView.this.mSelectedRowId);
                } else {
                    z = false;
                }
                if (z) {
                    SemHorizontalAbsListView.this.setPressed(false);
                    childAt.setPressed(false);
                    return;
                }
                return;
            }
            SemHorizontalAbsListView.this.setPressed(false);
            childAt.setPressed(false);
        }
    }

    boolean performLongPress(View view, int i, long j) {
        SemHorizontalAbsListView semHorizontalAbsListView;
        View view2;
        int i2;
        long j2;
        boolean z;
        if (this.mChoiceMode == 3) {
            if (this.mChoiceActionMode == null) {
                ActionMode startActionMode = startActionMode(this.mMultiChoiceModeCallback);
                this.mChoiceActionMode = startActionMode;
                if (startActionMode != null) {
                    if (this.mChoiceMode == 3) {
                        this.mIsDragBlockEnabled = true;
                    }
                    setItemChecked(i, true);
                    performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
                }
            }
            return true;
        }
        if (this.mOnItemLongClickListener != null) {
            SemHorizontalAbsListView semHorizontalAbsListView2 = this;
            view2 = view;
            i2 = i;
            j2 = j;
            z = this.mOnItemLongClickListener.onItemLongClick(semHorizontalAbsListView2, view2, i2, j2);
            semHorizontalAbsListView = semHorizontalAbsListView2;
        } else {
            semHorizontalAbsListView = this;
            view2 = view;
            i2 = i;
            j2 = j;
            z = false;
        }
        if (!z) {
            semHorizontalAbsListView.mContextMenuInfo = semHorizontalAbsListView.createContextMenuInfo(view2, i2, j2);
            z = super.showContextMenuForChild(semHorizontalAbsListView);
        }
        if (z) {
            if (semHorizontalAbsListView.semGetEnableVibrationAtLongPress()) {
                semHorizontalAbsListView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
                return z;
            }
            Log.d(TAG, " does not need vibration");
        }
        return z;
    }

    @Override // android.view.View
    @Deprecated
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    public boolean showContextMenu(float f, float f2, int i) {
        int pointToPosition = pointToPosition((int) f, (int) f2);
        if (pointToPosition != -1) {
            long itemId = this.mAdapter.getItemId(pointToPosition);
            View childAt = getChildAt(pointToPosition - this.mFirstPosition);
            if (childAt != null) {
                this.mContextMenuInfo = createContextMenuInfo(childAt, pointToPosition, itemId);
                return super.showContextMenuForChild(this);
            }
        } else {
            this.mContextMenuInfo = null;
        }
        return super.showContextMenu();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    @Deprecated
    public boolean showContextMenuForChild(View view) {
        SemHorizontalAbsListView semHorizontalAbsListView;
        View view2;
        int positionForView = getPositionForView(view);
        boolean z = false;
        if (positionForView >= 0) {
            long itemId = this.mAdapter.getItemId(positionForView);
            if (this.mOnItemLongClickListener != null) {
                semHorizontalAbsListView = this;
                view2 = view;
                z = this.mOnItemLongClickListener.onItemLongClick(semHorizontalAbsListView, view2, positionForView, itemId);
            } else {
                semHorizontalAbsListView = this;
                view2 = view;
            }
            if (!z) {
                semHorizontalAbsListView.mContextMenuInfo = semHorizontalAbsListView.createContextMenuInfo(semHorizontalAbsListView.getChildAt(positionForView - semHorizontalAbsListView.mFirstPosition), positionForView, itemId);
                return super.showContextMenuForChild(view2);
            }
        }
        return z;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    @Deprecated
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 31) {
            if (!this.mIsCtrlkeyPressed) {
                return false;
            }
            resetPressItemListArray();
            return false;
        }
        if (i == 59 || i == 60) {
            this.mIsShiftkeyPressed = true;
            return false;
        }
        if (i != 113 && i != 114) {
            return false;
        }
        this.mIsCtrlkeyPressed = true;
        return false;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    @Deprecated
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        View childAt = getChildAt(this.mSelectedPosition - this.mFirstPosition);
        if (KeyEvent.isConfirmKey(i)) {
            if (!isEnabled()) {
                return true;
            }
            if (isClickable() && isPressed() && this.mSelectedPosition >= 0 && this.mAdapter != null && this.mSelectedPosition < this.mAdapter.getCount()) {
                View childAt2 = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (childAt2 != null) {
                    performItemClick(childAt2, this.mSelectedPosition, this.mSelectedRowId);
                    childAt2.setPressed(false);
                }
                setPressed(false);
                return true;
            }
        }
        if (i != 31) {
            if (i != 59 && i != 60) {
                if (i != 113 && i != 114) {
                    switch (i) {
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                            if (this.mIsShiftkeyPressed) {
                                if (this.mOldKeyCode == 0) {
                                    this.mOldKeyCode = i;
                                } else {
                                    this.mCurrentKeyCode = i;
                                }
                            }
                            if (isClickable() && this.mSelectedPosition >= 0 && this.mAdapter != null && this.mSelectedPosition < this.mAdapter.getCount()) {
                                View childAt3 = getChildAt(this.mSemCurrentFocusPosition);
                                if (this.mIsShiftkeyPressed && childAt != null) {
                                    if (this.mCurrentKeyCode == 0) {
                                        resetPressItemListArray();
                                        semNotifyKeyPressState(childAt3, this.mSemCurrentFocusPosition, this.mSelectedRowId);
                                        semNotifyKeyPressState(childAt, this.mSelectedPosition, this.mSelectedRowId);
                                        addToPressItemListArray(this.mSemCurrentFocusPosition, this.mSelectedPosition);
                                        this.mFirstPressedPoint = this.mSemCurrentFocusPosition;
                                    } else {
                                        resetPressItemListArray();
                                        semNotifyKeyPressState(childAt, this.mSelectedPosition, this.mSelectedRowId);
                                        addToPressItemListArray(this.mFirstPressedPoint, this.mSelectedPosition);
                                    }
                                }
                                int i2 = this.mCurrentKeyCode;
                                if (i2 != 0) {
                                    this.mOldKeyCode = i2;
                                    break;
                                }
                            }
                            break;
                    }
                } else {
                    this.mIsCtrlkeyPressed = false;
                }
            } else {
                this.mIsShiftkeyPressed = false;
                this.mOldKeyCode = 0;
                this.mCurrentKeyCode = 0;
                this.mFirstPressedPoint = -1;
                this.mSecondPressedPoint = -1;
            }
        } else if (this.mIsCtrlkeyPressed) {
            resetPressItemListArray();
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Deprecated
    public int pointToPosition(int i, int i2) {
        Rect rect = this.mTouchFrame;
        if (rect == null) {
            rect = new Rect();
            this.mTouchFrame = rect;
        }
        boolean z = false;
        int i3 = this instanceof SemHorizontalListView ? ((SemHorizontalListView) this).mDividerHeight : 0;
        if (i3 > 0 && ((SemHorizontalListView) this).mDivider != null) {
            z = true;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt.getVisibility() == 0) {
                childAt.getHitRect(rect);
                if (z) {
                    rect.bottom += i3;
                }
                if (rect.contains(i, i2)) {
                    return this.mFirstPosition + childCount;
                }
            }
        }
        return -1;
    }

    @Deprecated
    public long pointToRowId(int i, int i2) {
        int pointToPosition = pointToPosition(i, i2);
        if (pointToPosition >= 0) {
            return this.mAdapter.getItemId(pointToPosition);
        }
        return Long.MIN_VALUE;
    }

    private final class CheckForTap implements Runnable {
        float x;
        float y;

        private CheckForTap() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SemHorizontalAbsListView.this.mTouchMode == 0) {
                SemHorizontalAbsListView.this.mTouchMode = 1;
                SemHorizontalAbsListView semHorizontalAbsListView = SemHorizontalAbsListView.this;
                View childAt = semHorizontalAbsListView.getChildAt(semHorizontalAbsListView.mMotionPosition - SemHorizontalAbsListView.this.mFirstPosition);
                if (childAt != null) {
                    SemHorizontalAbsListView.this.mIsChildViewEnabled = childAt.isEnabled();
                }
                if (childAt == null || childAt.hasExplicitFocusable() || SemHorizontalAbsListView.this.getAdapter() == null || SemHorizontalAbsListView.this.mMotionPosition < 0 || !SemHorizontalAbsListView.this.getAdapter().isEnabled(SemHorizontalAbsListView.this.mMotionPosition)) {
                    return;
                }
                SemHorizontalAbsListView.this.mLayoutMode = 0;
                if (!SemHorizontalAbsListView.this.mDataChanged) {
                    childAt.setPressed(true);
                    SemHorizontalAbsListView.this.setPressed(true);
                    SemHorizontalAbsListView.this.layoutChildren();
                    SemHorizontalAbsListView semHorizontalAbsListView2 = SemHorizontalAbsListView.this;
                    semHorizontalAbsListView2.positionSelector(semHorizontalAbsListView2.mMotionPosition, childAt);
                    SemHorizontalAbsListView.this.refreshDrawableState();
                    int longPressTimeout = ViewConfiguration.getLongPressTimeout();
                    boolean isLongClickable = SemHorizontalAbsListView.this.isLongClickable();
                    if (SemHorizontalAbsListView.this.mSelector != null) {
                        Drawable current = SemHorizontalAbsListView.this.mSelector.getCurrent();
                        if (current != null && (current instanceof TransitionDrawable)) {
                            if (isLongClickable) {
                                ((TransitionDrawable) current).startTransition(longPressTimeout);
                            } else {
                                ((TransitionDrawable) current).resetTransition();
                            }
                        }
                        SemHorizontalAbsListView.this.mSelector.setHotspot(this.x, this.y);
                    }
                    if (isLongClickable) {
                        if (SemHorizontalAbsListView.this.mPendingCheckForLongPress == null) {
                            SemHorizontalAbsListView.this.mPendingCheckForLongPress = new CheckForLongPress();
                        }
                        SemHorizontalAbsListView.this.mPendingCheckForLongPress.rememberWindowAttachCount();
                        SemHorizontalAbsListView semHorizontalAbsListView3 = SemHorizontalAbsListView.this;
                        semHorizontalAbsListView3.postDelayed(semHorizontalAbsListView3.mPendingCheckForLongPress, longPressTimeout);
                        return;
                    }
                    SemHorizontalAbsListView.this.mTouchMode = 2;
                    return;
                }
                SemHorizontalAbsListView.this.mTouchMode = 2;
            }
        }
    }

    private boolean startScrollIfNeeded(int i, int i2, MotionEvent motionEvent) {
        int i3 = i - this.mMotionX;
        int abs = Math.abs(i3);
        boolean z = this.mScrollX != 0;
        if ((!z && abs <= this.mTouchSlop) || (getNestedScrollAxes() & 1) != 0) {
            return false;
        }
        createScrollingCache();
        if (z) {
            this.mTouchMode = 5;
            this.mMotionCorrection = 0;
        } else {
            this.mTouchMode = 3;
            this.mMotionCorrection = i3 > 0 ? this.mTouchSlop : -this.mTouchSlop;
        }
        removeCallbacks(this.mPendingCheckForLongPress);
        setPressed(false);
        View childAt = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (childAt != null) {
            childAt.setPressed(false);
        }
        if (this.mPointerCount > 1) {
            int childCount = getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                getChildAt(i4).setPressed(false);
            }
        }
        reportScrollStateChange(1);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        scrollIfNeeded(i, i2, motionEvent);
        return true;
    }

    private void scrollIfNeeded(int i, int i2, MotionEvent motionEvent) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int childCount;
        VelocityTracker velocityTracker;
        ViewParent parent;
        int i8 = i - this.mMotionX;
        int i9 = this.mLastX;
        if (i9 == Integer.MIN_VALUE) {
            i8 -= this.mMotionCorrection;
        }
        int i10 = 0;
        if (dispatchNestedPreScroll(0, i9 != Integer.MIN_VALUE ? i9 - i : -i8, this.mScrollConsumed, this.mScrollOffset)) {
            i3 = this.mScrollConsumed[0];
            i8 += i3;
            int i11 = this.mScrollOffset[0];
            int i12 = -i11;
            if (motionEvent != null) {
                motionEvent.offsetLocation(i11, 0.0f);
                this.mNestedXOffset += this.mScrollOffset[0];
            }
            i4 = i12;
        } else {
            i3 = 0;
            i4 = 0;
        }
        int i13 = i8;
        int i14 = this.mLastX;
        int i15 = i14 != Integer.MIN_VALUE ? (i - i14) + i3 : i13;
        int i16 = this.mTouchMode;
        if (i16 == 3) {
            if (this.mScrollStrictSpan == null) {
                this.mScrollStrictSpan = StrictMode.enterCriticalSpan("SemHorizontalAbsListView-scroll");
            }
            if (i != this.mLastX) {
                if ((this.mGroupFlags & 524288) == 0 && Math.abs(i13) > this.mTouchSlop && (parent = getParent()) != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                int i17 = this.mMotionPosition;
                if (i17 >= 0) {
                    childCount = i17 - this.mFirstPosition;
                } else {
                    childCount = getChildCount() / 2;
                }
                View childAt = getChildAt(childCount);
                int left = childAt != null ? childAt.getLeft() : 0;
                boolean trackMotionScroll = i15 != 0 ? trackMotionScroll(i13, i15) : false;
                View childAt2 = getChildAt(childCount);
                if (childAt2 != null) {
                    int left2 = childAt2.getLeft();
                    if (trackMotionScroll) {
                        int i18 = (-i15) - (left2 - left);
                        if (dispatchNestedScroll(i18 - i15, 0, 0, i18, this.mScrollOffset)) {
                            int i19 = this.mScrollOffset[0];
                            int i20 = 0 - i19;
                            if (motionEvent != null) {
                                motionEvent.offsetLocation(i19, 0.0f);
                                this.mNestedXOffset += this.mScrollOffset[0];
                            }
                            i10 = i20;
                        } else {
                            boolean overScrollBy = overScrollBy(i18, 0, this.mScrollX, 0, 0, 0, this.mOverscrollDistance, 0, true);
                            if (overScrollBy && (velocityTracker = this.mVelocityTracker) != null) {
                                velocityTracker.clear();
                            }
                            int overScrollMode = getOverScrollMode();
                            if (overScrollMode == 0 || (overScrollMode == 1 && !contentFits())) {
                                if (!overScrollBy) {
                                    this.mDirection = 0;
                                    this.mTouchMode = 5;
                                }
                                if (i15 > 0) {
                                    this.mEdgeGlowLeft.onPull((-i18) / getWidth(), 1.0f - (i2 / getHeight()));
                                    if (!this.mEdgeGlowRight.isFinished()) {
                                        this.mEdgeGlowRight.onRelease();
                                    }
                                    invalidate(0, 0, this.mEdgeGlowLeft.getMaxHeight() + getPaddingLeft(), getHeight());
                                } else if (i15 < 0) {
                                    this.mEdgeGlowRight.onPull(i18 / getWidth(), i2 / getHeight());
                                    if (!this.mEdgeGlowLeft.isFinished()) {
                                        this.mEdgeGlowLeft.onRelease();
                                    }
                                    invalidate((getWidth() - getPaddingRight()) - this.mEdgeGlowRight.getMaxHeight(), 0, getWidth(), getHeight());
                                }
                            }
                        }
                    }
                    this.mMotionX = i + i10 + i4;
                }
                this.mLastX = i + i10 + i4;
                return;
            }
            return;
        }
        if (i16 != 5 || i == i14) {
            return;
        }
        int i21 = this.mScrollX;
        int i22 = i21 - i15;
        int i23 = i > this.mLastX ? 1 : -1;
        if (this.mDirection == 0) {
            this.mDirection = i23;
        }
        int i24 = -i15;
        if ((i22 >= 0 || i21 < 0) && (i22 <= 0 || i21 > 0)) {
            i5 = 0;
        } else {
            i24 = -i21;
            i5 = i15 + i24;
        }
        int i25 = i24;
        if (i25 != 0) {
            i6 = i23;
            overScrollBy(i25, 0, this.mScrollX, 0, 0, 0, this.mOverscrollDistance, 0, true);
            int overScrollMode2 = getOverScrollMode();
            if (overScrollMode2 == 0 || (overScrollMode2 == 1 && !contentFits())) {
                if (i13 > 0) {
                    this.mEdgeGlowLeft.onPull(i25 / getWidth(), 1.0f - (i2 / getHeight()));
                    if (!this.mEdgeGlowRight.isFinished()) {
                        this.mEdgeGlowRight.onRelease();
                    }
                    invalidate(0, 0, this.mEdgeGlowLeft.getMaxHeight() + getPaddingLeft(), getHeight());
                    i7 = 0;
                } else if (i13 < 0) {
                    this.mEdgeGlowRight.onPull(i25 / getWidth(), i2 / getHeight());
                    if (!this.mEdgeGlowLeft.isFinished()) {
                        this.mEdgeGlowLeft.onRelease();
                    }
                    i7 = 0;
                    invalidate((getWidth() - getPaddingRight()) - this.mEdgeGlowRight.getMaxHeight(), 0, getWidth(), getHeight());
                }
            }
            i7 = 0;
        } else {
            i6 = i23;
            i7 = 0;
        }
        if (i5 != 0) {
            if (this.mScrollX != 0) {
                this.mScrollX = i7;
                invalidateParentIfNeeded();
            }
            trackMotionScroll(i5, i5);
            this.mTouchMode = 3;
            int findClosestMotionRow = findClosestMotionRow(i);
            this.mMotionCorrection = i7;
            View childAt3 = getChildAt(findClosestMotionRow - this.mFirstPosition);
            this.mMotionViewOriginalLeft = childAt3 != null ? childAt3.getLeft() : i7;
            this.mMotionX = i;
            this.mMotionPosition = findClosestMotionRow;
        }
        this.mLastX = i + i4;
        this.mDirection = i6;
    }

    @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
    @Deprecated
    public void onTouchModeChanged(boolean z) {
        if (z) {
            hideSelector();
            if (getWidth() > 0 && getChildCount() > 0) {
                layoutChildren();
            }
            updateSelectorState();
            return;
        }
        int i = this.mTouchMode;
        if (i == 5 || i == 6) {
            FlingRunnable flingRunnable = this.mFlingRunnable;
            if (flingRunnable != null) {
                flingRunnable.endFling();
            }
            AbsPositionScroller absPositionScroller = this.mPositionScroller;
            if (absPositionScroller != null) {
                absPositionScroller.stop();
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
        return ((KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode();
    }

    public void semSetHoverScrollEnabled(boolean z) {
        this.mHoverScrollEnable = z;
        this.mHoverScrollStateChanged = true;
    }

    public void setEnablePaddingInHoverScroll(boolean z) {
        this.mIsEnabledPaddingInHoverScroll = z;
    }

    public void addExtraPaddingInLeftHoverArea(int i) {
        this.mExtraPaddingInLeftHoverArea = (int) (TypedValue.applyDimension(1, i, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
    }

    public void addExtraPaddingInRightHoverArea(int i) {
        this.mExtraPaddingInRightHoverArea = (int) (TypedValue.applyDimension(1, i, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:96)
        */
    @Override // android.view.ViewGroup, android.view.View
    @java.lang.Deprecated
    protected boolean dispatchHoverEvent(android.view.MotionEvent r19) {
        /*
            Method dump skipped, instructions count: 949
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SemHorizontalAbsListView.dispatchHoverEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x00e1, code lost:
    
        if (r0 != 6) goto L103;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean dispatchDragEvent(android.view.DragEvent r13) {
        /*
            Method dump skipped, instructions count: 447
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SemHorizontalAbsListView.dispatchDragEvent(android.view.DragEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int width;
        int i;
        int i2;
        OnScrollListener onScrollListener;
        OnScrollListener onScrollListener2;
        int i3;
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int action = motionEvent.getAction();
        boolean isNeedToScroll = MultiSelection.isNeedToScroll();
        if (this.mSemDragSelectedItemArray == null) {
            this.mSemDragSelectedItemArray = new ArrayList<>();
        }
        if (this.mHoverHandler == null) {
            this.mHoverHandler = new HoverScrollHandler(this);
        }
        this.mIsTextSelectionStarted = TextView.semIsTextSelectionProgressing();
        if (action == 211) {
            this.mIsNeedPenSelection = true;
            if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "car_mode_on", 0, -3) == 1) {
                this.mIsNeedPenSelection = false;
            }
        }
        if (this.mIsTextSelectionStarted) {
            this.mIsNeedPenSelection = false;
        }
        if (this.mHoverLeftAreaWidth <= 0 || this.mHoverRightAreaWidth <= 0) {
            this.mHoverLeftAreaWidth = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
            this.mHoverRightAreaWidth = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
        }
        if (this.mIsEnabledPaddingInHoverScroll) {
            i = this.mListPadding.left;
            width = getWidth() - this.mListPadding.right;
        } else {
            width = getWidth();
            i = 0;
        }
        if (this.mIsEnabledPaddingInHoverScroll && ((x < i || x > width) && motionEvent.getAction() != 1 && motionEvent.getAction() != 212)) {
            return true;
        }
        if (action == 1 || action == 3 || action == 212) {
            if (!this.mIsTextSelectionStarted) {
                if (this.mHoverAreaEnter && (onScrollListener = this.mOnScrollListener) != null) {
                    onScrollListener.onScrollStateChanged(this, 0);
                }
                this.mHoverRecognitionStartTime = 0L;
                this.mHoverScrollStartTime = 0L;
                this.mHoverAreaEnter = false;
                int size = this.mSemDragSelectedItemArray.size();
                this.mSemDragSelectedItemSize = size;
                if (size != 0) {
                    if (this.mCheckStates != null && ((i2 = this.mChoiceMode) == 2 || i2 == 3)) {
                        Iterator<Integer> it = this.mSemDragSelectedItemArray.iterator();
                        boolean z = false;
                        while (it.hasNext()) {
                            if (this.mAdapter.isEnabled(it.next().intValue())) {
                                z = true;
                            }
                        }
                        if (this.mChoiceMode == 3 && this.mChoiceActionMode == null && z) {
                            this.mChoiceActionMode = startActionMode(this.mMultiChoiceModeCallback);
                        }
                        if (this.mSemIsOnClickEnabled) {
                            Iterator<Integer> it2 = this.mSemDragSelectedItemArray.iterator();
                            while (it2.hasNext()) {
                                Integer next = it2.next();
                                if (this.mAdapter.isEnabled(next.intValue())) {
                                    performItemClick(null, next.intValue(), getItemIdAtPosition(next.intValue()));
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
            }
        } else if (action == 213) {
            if (this.mIsNeedPenSelection) {
                int childCount = getChildCount();
                if (this.mIsfirstMoveEvent) {
                    this.mSemDragStartX = x;
                    this.mSemDragStartY = y;
                    super.semNotifyMultiSelectedStart(x, y);
                    this.mIsPenPressed = true;
                    int pointToPosition = pointToPosition(x, y);
                    this.mSemTrackedChildPosition = pointToPosition;
                    if (pointToPosition == -1) {
                        int i4 = childCount - 1;
                        int i5 = i4;
                        int i6 = 0;
                        int i7 = 0;
                        while (true) {
                            if (i5 < 0) {
                                break;
                            }
                            View childAt = getChildAt(i5);
                            if (childAt != null) {
                                int left = childAt.getLeft();
                                int right = childAt.getRight();
                                int i8 = this.mSemDragStartX;
                                if (i8 >= left && i8 <= right) {
                                    this.mSemTrackedChild = childAt;
                                    this.mSemTrackedChildPosition = i5 + getFirstVisiblePosition();
                                    break;
                                }
                                int abs = Math.abs(i8 - left);
                                int abs2 = Math.abs(this.mSemDragStartX - right);
                                if (i5 == i4) {
                                    this.mSemCloseChildPositionByLeft = getFirstVisiblePosition() + i4;
                                    this.mSemCloseChildPositionByRight = getFirstVisiblePosition() + i4;
                                    i6 = abs;
                                } else {
                                    if (abs <= i6) {
                                        this.mSemCloseChildPositionByLeft = getFirstVisiblePosition() + i5;
                                        i6 = abs;
                                    }
                                    if (abs2 <= i7) {
                                        this.mSemCloseChildPositionByRight = getFirstVisiblePosition() + i5;
                                    }
                                }
                                i7 = abs2;
                            }
                            i5--;
                        }
                        if (this.mSemTrackedChild == null) {
                            View childAt2 = getChildAt(this.mSemCloseChildPositionByLeft - getFirstVisiblePosition());
                            this.mSemCloseChildByLeft = childAt2;
                            if (childAt2 != null) {
                                this.mSemDistanceFromCloseChildLeft = this.mSemDragStartX - childAt2.getLeft();
                            }
                            View childAt3 = getChildAt(this.mSemCloseChildPositionByRight - getFirstVisiblePosition());
                            this.mSemCloseChildByRight = childAt3;
                            if (childAt3 != null) {
                                this.mSemDistanceFromCloseChildRight = this.mSemDragStartX - childAt3.getLeft();
                            }
                        }
                    } else {
                        this.mSemTrackedChild = getChildAt(pointToPosition - getFirstVisiblePosition());
                    }
                    View view = this.mSemTrackedChild;
                    if (view != null) {
                        this.mSemDistanceFromTrackedChildLeft = this.mSemDragStartX - view.getLeft();
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
                if (x < 0) {
                    this.mSemDragEndX = 0;
                } else if (x > width) {
                    this.mSemDragEndX = width;
                }
                this.mSemDragSelectedViewPosition = pointToPosition(x, y);
                int i9 = this.mSemDragStartY;
                int i10 = this.mSemDragEndY;
                this.mSemDragBlockTop = i9 < i10 ? i9 : i10;
                int i11 = this.mSemDragStartX;
                int i12 = this.mSemDragEndX;
                this.mSemDragBlockLeft = i11 < i12 ? i11 : i12;
                if (i10 > i9) {
                    i9 = i10;
                }
                this.mSemDragBlockBottom = i9;
                if (i12 > i11) {
                    i11 = i12;
                }
                this.mSemDragBlockRight = i11;
                for (int i13 = 0; i13 < childCount; i13++) {
                    View childAt4 = getChildAt(i13);
                    if (childAt4 != null) {
                        int left2 = childAt4.getLeft();
                        int top = childAt4.getTop();
                        int right2 = childAt4.getRight();
                        int bottom = childAt4.getBottom();
                        if (childAt4.getVisibility() == 0) {
                            int i14 = this.mSemDragBlockTop;
                            if ((i14 > top && this.mSemDragBlockLeft > left2 && this.mSemDragBlockBottom < bottom && this.mSemDragBlockRight < right2) || (((i14 > top && this.mSemDragBlockBottom < bottom) || ((i14 < top && this.mSemDragBlockBottom > top) || (i14 < bottom && this.mSemDragBlockBottom > bottom))) && (((i3 = this.mSemDragBlockLeft) >= left2 && this.mSemDragBlockRight <= right2) || ((i3 <= left2 && this.mSemDragBlockRight > left2) || (i3 < right2 && this.mSemDragBlockRight >= right2))))) {
                                int pointToPosition2 = pointToPosition(left2 + 1, top + 1);
                                this.mSemDragSelectedViewPosition = pointToPosition2;
                                if (pointToPosition2 != -1 && this.mAdapter.isEnabled(pointToPosition2) && !this.mSemDragSelectedItemArray.contains(Integer.valueOf(this.mSemDragSelectedViewPosition))) {
                                    this.mSemDragSelectedItemArray.add(Integer.valueOf(this.mSemDragSelectedViewPosition));
                                    addToPressItemListArray(this.mSemDragSelectedViewPosition, -1);
                                    int i15 = this.mSemDragSelectedViewPosition;
                                    semNotifyMultiSelectState(childAt4, i15, getItemIdAtPosition(i15));
                                }
                            } else {
                                int pointToPosition3 = pointToPosition(left2 + 1, top + 1);
                                this.mSemDragSelectedViewPosition = pointToPosition3;
                                if (pointToPosition3 != -1 && this.mAdapter.isEnabled(pointToPosition3) && this.mSemDragSelectedItemArray.contains(Integer.valueOf(this.mSemDragSelectedViewPosition))) {
                                    this.mSemDragSelectedItemArray.remove(Integer.valueOf(this.mSemDragSelectedViewPosition));
                                    addToPressItemListArray(this.mSemDragSelectedViewPosition, -1);
                                    int i16 = this.mSemDragSelectedViewPosition;
                                    semNotifyMultiSelectState(childAt4, i16, getItemIdAtPosition(i16));
                                }
                            }
                        }
                    }
                }
                isNeedToScroll = true;
            }
            if (isNeedToScroll) {
                boolean z2 = x >= i && x <= i + this.mHoverLeftAreaWidth;
                boolean z3 = x >= width - this.mHoverRightAreaWidth && x <= width;
                if (z2 || z3) {
                    if (!this.mHoverAreaEnter) {
                        this.mHoverAreaEnter = true;
                        this.mHoverScrollStartTime = System.currentTimeMillis();
                        OnScrollListener onScrollListener3 = this.mOnScrollListener;
                        if (onScrollListener3 != null) {
                            onScrollListener3.onScrollStateChanged(this, 1);
                        }
                    }
                    if (!this.mHoverHandler.hasMessages(1)) {
                        this.mHoverRecognitionStartTime = System.currentTimeMillis();
                        this.mHoverScrollDirection = z2 ? 2 : 1;
                        this.mHoverHandler.sendEmptyMessage(1);
                    }
                } else {
                    if (this.mHoverAreaEnter && (onScrollListener2 = this.mOnScrollListener) != null) {
                        onScrollListener2.onScrollStateChanged(this, 0);
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
            this.mPreviousTextViewScroll = isNeedToScroll;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    @Deprecated
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return isClickable() || isLongClickable();
        }
        AbsPositionScroller absPositionScroller = this.mPositionScroller;
        if (absPositionScroller != null) {
            absPositionScroller.stop();
        }
        if (this.mIsDetaching || !isAttachedToWindow()) {
            return false;
        }
        startNestedScroll(2);
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller != null && semHorizontalFastScroller.onTouchEvent(motionEvent)) {
            return true;
        }
        initVelocityTrackerIfNotExists();
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mNestedXOffset = 0;
        }
        obtain.offsetLocation(this.mNestedXOffset, 0.0f);
        if (actionMasked == 0) {
            onTouchDown(motionEvent);
        } else if (actionMasked == 1) {
            onTouchUp(motionEvent);
        } else if (actionMasked == 2) {
            onTouchMove(motionEvent, obtain);
        } else if (actionMasked == 3) {
            onTouchCancel();
        } else if (actionMasked == 5) {
            int actionIndex = motionEvent.getActionIndex();
            int pointerId = motionEvent.getPointerId(actionIndex);
            int x = (int) motionEvent.getX(actionIndex);
            int y = (int) motionEvent.getY(actionIndex);
            this.mMotionCorrection = 0;
            this.mActivePointerId = pointerId;
            this.mMotionX = x;
            this.mMotionY = y;
            int pointToPosition = pointToPosition(x, y);
            if (pointToPosition >= 0) {
                this.mMotionViewOriginalLeft = getChildAt(pointToPosition - this.mFirstPosition).getLeft();
                this.mMotionPosition = pointToPosition;
            }
            this.mLastX = x;
        } else if (actionMasked == 6) {
            onSecondaryPointerUp(motionEvent);
            int pointToPosition2 = pointToPosition(this.mMotionX, this.mMotionY);
            if (pointToPosition2 >= 0) {
                View childAt = getChildAt(pointToPosition2 - this.mFirstPosition);
                this.mMotionViewOriginalLeft = childAt.getLeft();
                this.mMotionPosition = pointToPosition2;
                ListAdapter listAdapter = this.mAdapter;
                if (listAdapter != null && listAdapter.isEnabled(pointToPosition2) && !childAt.hasFocusable()) {
                    layoutChildren();
                }
            } else {
                layoutChildren();
            }
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(obtain);
        }
        obtain.recycle();
        return true;
    }

    private void onTouchDown(MotionEvent motionEvent) {
        if (this.mTouchMode == 6) {
            this.mFlingRunnable.endFling();
            AbsPositionScroller absPositionScroller = this.mPositionScroller;
            if (absPositionScroller != null) {
                absPositionScroller.stop();
            }
            this.mTouchMode = 5;
            int x = (int) motionEvent.getX();
            this.mLastX = x;
            this.mMotionX = x;
            this.mMotionY = (int) motionEvent.getY();
            this.mMotionCorrection = 0;
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.mDirection = 0;
        } else {
            this.mActivePointerId = motionEvent.getPointerId(0);
            int x2 = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int pointToPosition = pointToPosition(x2, y);
            if (!this.mDataChanged) {
                if (this.mTouchMode != 4 && pointToPosition >= 0 && getAdapter().isEnabled(pointToPosition)) {
                    this.mTouchMode = 0;
                    if (this.mPendingCheckForTap == null) {
                        this.mPendingCheckForTap = new CheckForTap();
                    }
                    postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                } else if (this.mTouchMode == 4) {
                    createScrollingCache();
                    this.mTouchMode = 3;
                    this.mMotionCorrection = 0;
                    pointToPosition = findMotionRow(x2);
                    this.mFlingRunnable.flywheelTouch();
                }
            }
            if (pointToPosition >= 0) {
                this.mMotionViewOriginalLeft = getChildAt(pointToPosition - this.mFirstPosition).getLeft();
            }
            this.mMotionX = x2;
            this.mMotionY = y;
            this.mMotionPosition = pointToPosition;
            this.mLastX = Integer.MIN_VALUE;
        }
        if (performButtonActionOnTouchDown(motionEvent) && this.mTouchMode == 0) {
            removeCallbacks(this.mPendingCheckForTap);
        }
    }

    private void onTouchMove(MotionEvent motionEvent, MotionEvent motionEvent2) {
        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (findPointerIndex == -1) {
            this.mActivePointerId = motionEvent.getPointerId(0);
            findPointerIndex = 0;
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        int x = (int) motionEvent.getX(findPointerIndex);
        int i = this.mTouchMode;
        if (i != 0 && i != 1 && i != 2) {
            if (i == 3 || i == 5) {
                scrollIfNeeded(x, (int) motionEvent.getY(findPointerIndex), motionEvent2);
                return;
            }
            return;
        }
        if (startScrollIfNeeded(x, (int) motionEvent.getY(findPointerIndex), motionEvent2)) {
            return;
        }
        if (pointInView(x, motionEvent.getY(findPointerIndex), this.mTouchSlop)) {
            return;
        }
        setPressed(false);
        View childAt = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (childAt != null) {
            childAt.setPressed(false);
        }
        removeCallbacks(this.mTouchMode == 0 ? this.mPendingCheckForTap : this.mPendingCheckForLongPress);
        this.mTouchMode = 2;
        updateSelectorState();
    }

    private void onTouchUp(MotionEvent motionEvent) {
        int left;
        int right;
        int i = this.mTouchMode;
        if (i == 0 || i == 1 || i == 2) {
            int i2 = this.mMotionPosition;
            final View childAt = getChildAt(i2 - this.mFirstPosition);
            if (childAt != null) {
                if (this.mTouchMode != 0) {
                    childAt.setPressed(false);
                }
                float y = motionEvent.getY();
                if (y > this.mListPadding.top && y < getHeight() - this.mListPadding.bottom && !childAt.hasExplicitFocusable()) {
                    if (this.mPerformClick == null) {
                        this.mPerformClick = new PerformClick();
                    }
                    final PerformClick performClick = this.mPerformClick;
                    performClick.mClickMotionPosition = i2;
                    performClick.rememberWindowAttachCount();
                    this.mResurrectToPosition = i2;
                    int i3 = this.mTouchMode;
                    if (i3 == 0 || i3 == 1) {
                        removeCallbacks(i3 == 0 ? this.mPendingCheckForTap : this.mPendingCheckForLongPress);
                        this.mLayoutMode = 0;
                        if (!this.mDataChanged && this.mAdapter.isEnabled(i2)) {
                            this.mTouchMode = 1;
                            setSelectedPositionInt(this.mMotionPosition);
                            layoutChildren();
                            childAt.setPressed(true);
                            positionSelector(this.mMotionPosition, childAt);
                            setPressed(true);
                            Drawable drawable = this.mSelector;
                            if (drawable != null) {
                                Drawable current = drawable.getCurrent();
                                if (current instanceof TransitionDrawable) {
                                    ((TransitionDrawable) current).resetTransition();
                                }
                                this.mSelector.setHotspot(motionEvent.getX(), y);
                            }
                            Runnable runnable = this.mTouchModeReset;
                            if (runnable != null) {
                                removeCallbacks(runnable);
                            }
                            Runnable runnable2 = new Runnable() { // from class: android.widget.SemHorizontalAbsListView.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    SemHorizontalAbsListView.this.mTouchModeReset = null;
                                    SemHorizontalAbsListView.this.mTouchMode = -1;
                                    childAt.setPressed(false);
                                    SemHorizontalAbsListView.this.setPressed(false);
                                    if (SemHorizontalAbsListView.this.mForcedClick || !(SemHorizontalAbsListView.this.mDataChanged || SemHorizontalAbsListView.this.mIsDetaching || !SemHorizontalAbsListView.this.isAttachedToWindow())) {
                                        performClick.run();
                                    }
                                }
                            };
                            this.mTouchModeReset = runnable2;
                            postDelayed(runnable2, ViewConfiguration.getPressedStateDuration());
                            return;
                        }
                        this.mTouchMode = -1;
                        updateSelectorState();
                        if (this.mForcedClick && this.mAdapter.isEnabled(i2)) {
                            performClick.run();
                            return;
                        }
                        return;
                    }
                    if ((this.mForcedClick || !this.mDataChanged) && this.mAdapter.isEnabled(i2)) {
                        performClick.run();
                    }
                }
            }
            this.mTouchMode = -1;
            updateSelectorState();
        } else if (i == 3) {
            int childCount = getChildCount();
            if (childCount > 0) {
                if (this.mIsRTL) {
                    left = getChildAt(childCount - 1).getLeft();
                    right = getChildAt(0).getRight();
                } else {
                    left = getChildAt(0).getLeft();
                    right = getChildAt(childCount - 1).getRight();
                }
                int i4 = this.mListPadding.left;
                int width = getWidth() - this.mListPadding.right;
                if (this.mFirstPosition == 0 && left >= i4 && this.mFirstPosition + childCount < this.mItemCount && right <= getWidth() - width) {
                    this.mTouchMode = -1;
                    reportScrollStateChange(0);
                } else {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                    int xVelocity = (int) (velocityTracker.getXVelocity(this.mActivePointerId) * this.mVelocityScale);
                    if (Math.abs(xVelocity) > this.mMinimumVelocity && ((this.mFirstPosition != 0 || left != i4 - this.mOverscrollDistance) && (this.mFirstPosition + childCount != this.mItemCount || right != width + this.mOverscrollDistance))) {
                        if (this.mFlingRunnable == null) {
                            this.mFlingRunnable = new FlingRunnable();
                        }
                        reportScrollStateChange(2);
                        this.mFlingRunnable.start(-xVelocity);
                    } else {
                        this.mTouchMode = -1;
                        reportScrollStateChange(0);
                        FlingRunnable flingRunnable = this.mFlingRunnable;
                        if (flingRunnable != null) {
                            flingRunnable.endFling();
                        }
                        AbsPositionScroller absPositionScroller = this.mPositionScroller;
                        if (absPositionScroller != null) {
                            absPositionScroller.stop();
                        }
                    }
                }
            } else {
                this.mTouchMode = -1;
                reportScrollStateChange(0);
            }
        } else if (i == 5) {
            if (this.mFlingRunnable == null) {
                this.mFlingRunnable = new FlingRunnable();
            }
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            velocityTracker2.computeCurrentVelocity(1000, this.mMaximumVelocity);
            int xVelocity2 = (int) velocityTracker2.getXVelocity(this.mActivePointerId);
            reportScrollStateChange(2);
            if (Math.abs(xVelocity2) > this.mMinimumVelocity) {
                this.mFlingRunnable.startOverfling(-xVelocity2);
            } else {
                this.mFlingRunnable.startSpringback();
            }
        }
        setPressed(false);
        EdgeEffect edgeEffect = this.mEdgeGlowLeft;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            this.mEdgeGlowRight.onRelease();
        }
        invalidate();
        removeCallbacks(this.mPendingCheckForLongPress);
        recycleVelocityTracker();
        this.mActivePointerId = -1;
        this.mPointerCount = 0;
        StrictMode.Span span = this.mScrollStrictSpan;
        if (span != null) {
            span.finish();
            this.mScrollStrictSpan = null;
        }
    }

    private void onTouchCancel() {
        int i = this.mTouchMode;
        if (i == 5) {
            if (this.mFlingRunnable == null) {
                this.mFlingRunnable = new FlingRunnable();
            }
            this.mFlingRunnable.startSpringback();
        } else if (i != 6) {
            this.mTouchMode = -1;
            setPressed(false);
            View childAt = getChildAt(this.mMotionPosition - this.mFirstPosition);
            if (childAt != null) {
                childAt.setPressed(false);
            }
            clearScrollingCache();
            removeCallbacks(this.mPendingCheckForLongPress);
            recycleVelocityTracker();
        }
        EdgeEffect edgeEffect = this.mEdgeGlowLeft;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
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
        postOnAnimation(new Runnable() { // from class: android.widget.SemHorizontalAbsListView.3
            @Override // java.lang.Runnable
            public void run() {
                SemHorizontalAbsListView.this.onJumpScrollToTopFinished();
            }
        });
    }

    void onJumpScrollToTopFinished() {
        Log.d(TAG, "onJumpScrollToTopFinished()");
    }

    void triggerDoubleFling(int i) {
        int count = getAdapter().getCount();
        int childCount = getChildCount();
        if (i > 0) {
            int i2 = childCount * 2;
            if (getLastVisiblePosition() > i2) {
                setSelection(i2);
            }
            smoothScrollToPosition(0);
            return;
        }
        if (i < 0) {
            int i3 = count - 1;
            int i4 = childCount * 3;
            if (i3 - getFirstVisiblePosition() > i4) {
                setSelection(i3 - i4);
            }
            smoothScrollToPosition(i3);
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

    private void onHoverDrawableState(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int toolType = motionEvent.getToolType(0);
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
        Drawable drawable = this.mSelector;
        if (drawable == null || !drawable.isStateful() || this.mHoverAreaEnter || action != 9 || this.mIsPenHovered) {
            return;
        }
        this.mSelectorRect.setEmpty();
    }

    @Override // android.view.View
    @Deprecated
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        if (this.mScrollX != i) {
            onScrollChanged(i, this.mScrollY, this.mScrollX, this.mScrollY);
            this.mScrollX = i;
            invalidateParentIfNeeded();
            awakenScrollBars();
        }
    }

    @Override // android.view.View
    @Deprecated
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8 && this.mTouchMode == -1) {
            float axisValue = motionEvent.getAxisValue(10);
            ViewRootImpl viewRootImpl = getViewRootImpl();
            if (axisValue != 0.0f) {
                int horizontalScrollFactor = (int) (axisValue * getHorizontalScrollFactor());
                if (!trackMotionScroll(horizontalScrollFactor, horizontalScrollFactor)) {
                    return true;
                }
            } else if ((viewRootImpl != null && viewRootImpl.isDesktopMode()) || (motionEvent.getMetaState() & 1) != 0) {
                float axisValue2 = motionEvent.getAxisValue(9);
                if (axisValue2 != 0.0f) {
                    int verticalScrollFactor = (int) (axisValue2 * getVerticalScrollFactor());
                    if (!trackMotionScroll(verticalScrollFactor, verticalScrollFactor)) {
                        return true;
                    }
                }
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    public void fling(int i) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        reportScrollStateChange(2);
        this.mFlingRunnable.start(i);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        super.onNestedScrollAccepted(view, view2, i);
        startNestedScroll(1);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        View childAt = getChildAt(getChildCount() / 2);
        int i5 = 0;
        int left = childAt != null ? childAt.getLeft() : 0;
        if (childAt != null) {
            int i6 = -i3;
            if (!trackMotionScroll(i6, i6)) {
                return;
            }
        }
        if (childAt != null) {
            i5 = childAt.getLeft() - left;
            i3 -= i5;
        }
        dispatchNestedScroll(i5, 0, i3, 0, null);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    @Deprecated
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        int childCount = getChildCount();
        if (!z && childCount > 0) {
            int i = (int) f;
            if (canScrollList(i) && Math.abs(f) > this.mMinimumVelocity) {
                reportScrollStateChange(2);
                if (this.mFlingRunnable == null) {
                    this.mFlingRunnable = new FlingRunnable();
                }
                if (dispatchNestedPreFling(f, 0.0f)) {
                    return true;
                }
                this.mFlingRunnable.start(i);
                return true;
            }
        }
        return dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View
    @Deprecated
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mEdgeGlowLeft != null) {
            int i = this.mScrollX;
            if (!this.mEdgeGlowLeft.isFinished()) {
                int save = canvas.save();
                int height = getHeight();
                canvas.translate(Math.min(0, this.mFirstPositionDistanceGuess + i), height);
                canvas.rotate(270.0f);
                this.mEdgeGlowLeft.setSize(height, getWidth());
                if (this.mEdgeGlowLeft.draw(canvas)) {
                    invalidate(0, 0, this.mEdgeGlowLeft.getMaxHeight() + getPaddingLeft(), getHeight());
                }
                canvas.restoreToCount(save);
            }
            if (this.mEdgeGlowRight.isFinished()) {
                return;
            }
            int save2 = canvas.save();
            int width = getWidth();
            int height2 = getHeight();
            canvas.translate(Math.max(width, i + this.mLastPositionDistanceGuess), 0.0f);
            canvas.rotate(90.0f);
            this.mEdgeGlowRight.setSize(height2, width);
            if (this.mEdgeGlowRight.draw(canvas)) {
                invalidate((getWidth() - getPaddingRight()) - this.mEdgeGlowRight.getMaxHeight(), 0, getWidth(), getHeight());
            }
            canvas.restoreToCount(save2);
        }
    }

    public void setOverScrollEffectPadding(int i, int i2) {
        this.mGlowPaddingTop = i;
        this.mGlowPaddingBottom = i2;
    }

    private void initOrResetVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    @Override // android.view.ViewGroup
    @Deprecated
    public boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller == null || !semHorizontalFastScroller.onInterceptHoverEvent(motionEvent)) {
            return super.onInterceptHoverEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.ViewGroup
    @Deprecated
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        AbsPositionScroller absPositionScroller = this.mPositionScroller;
        if (absPositionScroller != null) {
            absPositionScroller.stop();
        }
        if (this.mIsDetaching || !isAttachedToWindow()) {
            return false;
        }
        SemHorizontalFastScroller semHorizontalFastScroller = this.mFastScroll;
        if (semHorizontalFastScroller != null && semHorizontalFastScroller.onInterceptTouchEvent(motionEvent)) {
            return true;
        }
        if (actionMasked == 0) {
            int i = this.mTouchMode;
            if (i == 6 || i == 5) {
                this.mMotionCorrection = 0;
                return true;
            }
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            this.mActivePointerId = motionEvent.getPointerId(0);
            int findMotionRow = findMotionRow(x);
            if (i != 4 && findMotionRow >= 0) {
                this.mMotionViewOriginalLeft = getChildAt(findMotionRow - this.mFirstPosition).getLeft();
                this.mMotionX = x;
                this.mMotionY = y;
                this.mMotionPosition = findMotionRow;
                this.mTouchMode = 0;
                clearScrollingCache();
            }
            this.mLastX = Integer.MIN_VALUE;
            initOrResetVelocityTracker();
            this.mVelocityTracker.addMovement(motionEvent);
            this.mNestedXOffset = 0;
            startNestedScroll(2);
            if (i == 4) {
                return true;
            }
        } else {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        if (actionMasked == 6) {
                            onSecondaryPointerUp(motionEvent);
                        }
                    }
                } else if (this.mTouchMode == 0) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex == -1) {
                        this.mActivePointerId = motionEvent.getPointerId(0);
                        findPointerIndex = 0;
                    }
                    int x2 = (int) motionEvent.getX(findPointerIndex);
                    initVelocityTrackerIfNotExists();
                    this.mVelocityTracker.addMovement(motionEvent);
                    if (startScrollIfNeeded(x2, (int) motionEvent.getY(findPointerIndex), null)) {
                        return true;
                    }
                }
            }
            this.mTouchMode = -1;
            this.mActivePointerId = -1;
            recycleVelocityTracker();
            reportScrollStateChange(0);
            stopNestedScroll();
        }
        return false;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.mActivePointerId) {
            int i = action == 0 ? 1 : 0;
            this.mMotionX = (int) motionEvent.getX(i);
            this.mMotionY = (int) motionEvent.getY(i);
            this.mMotionCorrection = 0;
            this.mActivePointerId = motionEvent.getPointerId(i);
            this.mLastX = this.mMotionX;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    public void addTouchables(ArrayList<View> arrayList) {
        int childCount = getChildCount();
        int i = this.mFirstPosition;
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null) {
            return;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (listAdapter.isEnabled(i + i2)) {
                arrayList.add(childAt);
            }
            childAt.addTouchables(arrayList);
        }
    }

    public void reportScrollStateChange(int i) {
        if (i != this.mLastScrollState) {
            if (!this.mHoverAreaEnter && !this.mSemScrollingByScrollbar) {
                if (i != 0 && this.mLastScrollState == 0) {
                    SemPerfManager.onScrollEvent(true);
                    this.mDVFSLockAcquired = true;
                }
                if (i == 0 && this.mLastScrollState != 0 && this.mDVFSLockAcquired) {
                    SemPerfManager.onScrollEvent(false);
                    this.mDVFSLockAcquired = false;
                }
            }
            this.mLastScrollState = i;
            OnScrollListener onScrollListener = this.mOnScrollListener;
            if (onScrollListener == null || this.mHoverAreaEnter) {
                return;
            }
            onScrollListener.onScrollStateChanged(this, i);
        }
    }

    private class FlingRunnable implements Runnable {
        private static final int FLYWHEEL_TIMEOUT = 40;
        private final Runnable mCheckFlywheel = new Runnable() { // from class: android.widget.SemHorizontalAbsListView.FlingRunnable.1
            @Override // java.lang.Runnable
            public void run() {
                int i = SemHorizontalAbsListView.this.mActivePointerId;
                VelocityTracker velocityTracker = SemHorizontalAbsListView.this.mVelocityTracker;
                OverScroller overScroller = FlingRunnable.this.mScroller;
                if (velocityTracker == null || i == -1) {
                    return;
                }
                velocityTracker.computeCurrentVelocity(1000, SemHorizontalAbsListView.this.mMaximumVelocity);
                float f = -velocityTracker.getXVelocity(i);
                if (Math.abs(f) >= SemHorizontalAbsListView.this.mMinimumVelocity && overScroller.isScrollingInDirection(f, 0.0f)) {
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

        void start(int i) {
            int i2 = i < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingX = i2;
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(i2, 0, i, 0, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            SemHorizontalAbsListView.this.mTouchMode = 4;
            SemHorizontalAbsListView.this.postOnAnimation(this);
            if (SemHorizontalAbsListView.this.mFlingStrictSpan == null) {
                SemHorizontalAbsListView.this.mFlingStrictSpan = StrictMode.enterCriticalSpan("SemHorizontalAbsListView-fling");
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

        void startOverfling(int i) {
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(SemHorizontalAbsListView.this.mScrollX, 0, i, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0, SemHorizontalAbsListView.this.getWidth(), 0);
            SemHorizontalAbsListView.this.mTouchMode = 6;
            SemHorizontalAbsListView.this.invalidate();
            SemHorizontalAbsListView.this.postOnAnimation(this);
        }

        void edgeReached(int i) {
            this.mScroller.notifyHorizontalEdgeReached(SemHorizontalAbsListView.this.mScrollX, 0, SemHorizontalAbsListView.this.mOverflingDistance);
            int overScrollMode = SemHorizontalAbsListView.this.getOverScrollMode();
            if (overScrollMode == 0 || (overScrollMode == 1 && !SemHorizontalAbsListView.this.contentFits())) {
                SemHorizontalAbsListView.this.mTouchMode = 6;
                int currVelocity = (int) this.mScroller.getCurrVelocity();
                if (i > 0) {
                    SemHorizontalAbsListView.this.mEdgeGlowLeft.onAbsorb(currVelocity);
                } else {
                    SemHorizontalAbsListView.this.mEdgeGlowRight.onAbsorb(currVelocity);
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

        void startScroll(int i, int i2, boolean z) {
            int i3 = i < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingX = i3;
            this.mScroller.setInterpolator(z ? SemHorizontalAbsListView.sLinearInterpolator : null);
            this.mScroller.startScroll(i3, 0, i, 0, i2);
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
            int max;
            int i = SemHorizontalAbsListView.this.mTouchMode;
            boolean z = false;
            if (i != 3) {
                if (i != 4) {
                    if (i != 6) {
                        endFling();
                        return;
                    }
                    OverScroller overScroller = this.mScroller;
                    if (overScroller.computeScrollOffset()) {
                        int i2 = SemHorizontalAbsListView.this.mScrollX;
                        int currX = overScroller.getCurrX();
                        SemHorizontalAbsListView semHorizontalAbsListView = SemHorizontalAbsListView.this;
                        if (!semHorizontalAbsListView.overScrollBy(currX - i2, 0, i2, 0, 0, 0, semHorizontalAbsListView.mOverflingDistance, 0, false)) {
                            SemHorizontalAbsListView.this.invalidate();
                            SemHorizontalAbsListView.this.postOnAnimation(this);
                            return;
                        }
                        boolean z2 = i2 <= 0 && currX > 0;
                        if (i2 >= 0 && currX < 0) {
                            z = true;
                        }
                        if (z2 || z) {
                            int currVelocity = (int) overScroller.getCurrVelocity();
                            if (z) {
                                currVelocity = -currVelocity;
                            }
                            overScroller.abortAnimation();
                            start(currVelocity);
                            return;
                        }
                        startSpringback();
                        return;
                    }
                    endFling();
                    return;
                }
            } else if (this.mScroller.isFinished()) {
                return;
            }
            if (SemHorizontalAbsListView.this.mDataChanged) {
                SemHorizontalAbsListView.this.layoutChildren();
            }
            if (SemHorizontalAbsListView.this.mItemCount == 0 || SemHorizontalAbsListView.this.getChildCount() == 0) {
                endFling();
                return;
            }
            OverScroller overScroller2 = this.mScroller;
            boolean computeScrollOffset = overScroller2.computeScrollOffset();
            int currX2 = overScroller2.getCurrX();
            int i3 = this.mLastFlingX - currX2;
            if (i3 > 0) {
                SemHorizontalAbsListView semHorizontalAbsListView2 = SemHorizontalAbsListView.this;
                semHorizontalAbsListView2.mMotionPosition = semHorizontalAbsListView2.mFirstPosition;
                SemHorizontalAbsListView.this.mMotionViewOriginalLeft = SemHorizontalAbsListView.this.getChildAt(0).getLeft();
                max = Math.min(((SemHorizontalAbsListView.this.getWidth() - SemHorizontalAbsListView.this.mPaddingRight) - SemHorizontalAbsListView.this.mPaddingLeft) - 1, i3);
            } else {
                int childCount = SemHorizontalAbsListView.this.getChildCount() - 1;
                SemHorizontalAbsListView semHorizontalAbsListView3 = SemHorizontalAbsListView.this;
                semHorizontalAbsListView3.mMotionPosition = semHorizontalAbsListView3.mFirstPosition + childCount;
                SemHorizontalAbsListView.this.mMotionViewOriginalLeft = SemHorizontalAbsListView.this.getChildAt(childCount).getLeft();
                max = Math.max(-(((SemHorizontalAbsListView.this.getWidth() - SemHorizontalAbsListView.this.mPaddingRight) - SemHorizontalAbsListView.this.mPaddingLeft) - 1), i3);
            }
            SemHorizontalAbsListView semHorizontalAbsListView4 = SemHorizontalAbsListView.this;
            View childAt = semHorizontalAbsListView4.getChildAt(semHorizontalAbsListView4.mMotionPosition - SemHorizontalAbsListView.this.mFirstPosition);
            int left = childAt != null ? childAt.getLeft() : 0;
            boolean trackMotionScroll = SemHorizontalAbsListView.this.trackMotionScroll(max, max);
            if (trackMotionScroll && max != 0) {
                z = true;
            }
            if (z) {
                if (childAt != null) {
                    int i4 = -(max - (childAt.getLeft() - left));
                    SemHorizontalAbsListView semHorizontalAbsListView5 = SemHorizontalAbsListView.this;
                    semHorizontalAbsListView5.overScrollBy(i4, 0, semHorizontalAbsListView5.mScrollX, 0, 0, 0, SemHorizontalAbsListView.this.mOverflingDistance, 0, false);
                }
                if (computeScrollOffset) {
                    edgeReached(max);
                    return;
                }
                return;
            }
            if (computeScrollOffset && !z) {
                if (trackMotionScroll) {
                    SemHorizontalAbsListView.this.invalidate();
                }
                this.mLastFlingX = currX2;
                SemHorizontalAbsListView.this.postOnAnimation(this);
            } else {
                endFling();
            }
            if (SemHorizontalAbsListView.this.mJumpScrollToTopState == SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_FINISHING && SemHorizontalAbsListView.this.mFirstPosition == 0 && max == 0 && !computeScrollOffset) {
                SemHorizontalAbsListView.this.mJumpScrollToTopState = SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_IDLE;
                SemHorizontalAbsListView.this.postOnJumpScrollToFinished();
            }
        }
    }

    @Deprecated
    public void setFriction(float f) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        this.mFlingRunnable.mScroller.setFriction(f);
    }

    @Deprecated
    public void setVelocityScale(float f) {
        this.mVelocityScale = f;
    }

    AbsPositionScroller createPositionScroller() {
        return new PositionScroller();
    }

    @Deprecated
    public void smoothScrollToPosition(int i) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller();
        }
        this.mPositionScroller.start(i);
    }

    @Deprecated
    public void smoothScrollToPositionFromTop(int i, int i2, int i3) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller();
        }
        this.mPositionScroller.startWithOffset(i, i2, i3);
    }

    @Deprecated
    public void smoothScrollToPositionFromTop(int i, int i2) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller();
        }
        this.mPositionScroller.startWithOffset(i, i2);
    }

    @Deprecated
    public void smoothScrollToPosition(int i, int i2) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller();
        }
        this.mPositionScroller.start(i, i2);
    }

    @Deprecated
    public void smoothScrollBy(int i, int i2) {
        smoothScrollBy(i, i2, false);
    }

    private class SemSmoothScrollByMove implements Runnable {
        private SemSmoothScrollByMove() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SemHorizontalAbsListView.this.mFlingRunnable.mScroller.isFinished()) {
                if (SemHorizontalAbsListView.this.mSemScrollRemains == null || SemHorizontalAbsListView.this.mSemScrollRemains.isEmpty()) {
                    return;
                }
                Integer num = (Integer) SemHorizontalAbsListView.this.mSemScrollRemains.poll();
                if (num != null) {
                    SemHorizontalAbsListView.this.smoothScrollBy(num.intValue(), 0);
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
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return 0;
        }
        return adapter.getCount();
    }

    @Override // android.view.ViewGroup
    public void semSetSelection(int i) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null) {
            return;
        }
        if (this.mIsRTL) {
            i = (listAdapter.getCount() - i) - getChildCount();
        }
        setSelection(i);
    }

    @Override // android.view.ViewGroup
    public void semSmoothScrollBy(int i) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        if (this.mSemScrollRemains == null) {
            this.mSemScrollRemains = new LinkedList<>();
            this.mSemSmoothScrollByMove = new SemSmoothScrollByMove();
            mSemScrollAmount = (int) (this.mDensityScale * 150.0f);
        }
        boolean isEmpty = this.mSemScrollRemains.isEmpty();
        if (Math.abs(i) > mSemScrollAmount) {
            if (i <= 0) {
                while (true) {
                    int i2 = mSemScrollAmount;
                    if (i >= (-i2)) {
                        break;
                    }
                    this.mSemScrollRemains.offer(Integer.valueOf(-i2));
                    i += mSemScrollAmount;
                }
            } else {
                while (true) {
                    int i3 = mSemScrollAmount;
                    if (i <= i3) {
                        break;
                    }
                    this.mSemScrollRemains.offer(Integer.valueOf(i3));
                    i -= mSemScrollAmount;
                }
            }
        }
        this.mSemScrollRemains.offer(Integer.valueOf(i));
        if (isEmpty) {
            post(this.mSemSmoothScrollByMove);
        }
    }

    void smoothScrollBy(int i, int i2, boolean z) {
        View childAt;
        View childAt2;
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        int i3 = this.mFirstPosition;
        int childCount = getChildCount();
        int i4 = i3 + childCount;
        int paddingLeft = getPaddingLeft();
        int width = getWidth() - getPaddingRight();
        if (this.mIsRTL) {
            childAt = getChildAt(childCount - 1);
            childAt2 = getChildAt(0);
        } else {
            childAt = getChildAt(0);
            childAt2 = getChildAt(childCount - 1);
        }
        if (i == 0 || this.mItemCount == 0 || childCount == 0 || ((!this.mIsRTL && i3 == 0 && childAt.getLeft() == paddingLeft && i < 0) || ((!this.mIsRTL && i4 == this.mItemCount && childAt2.getRight() == width && i > 0) || ((this.mIsRTL && i3 == 0 && childAt2.getRight() == width && i > 0) || (this.mIsRTL && i4 == this.mItemCount && childAt.getLeft() == paddingLeft && i < 0))))) {
            this.mFlingRunnable.endFling();
            AbsPositionScroller absPositionScroller = this.mPositionScroller;
            if (absPositionScroller != null) {
                absPositionScroller.stop();
                return;
            }
            return;
        }
        reportScrollStateChange(2);
        this.mFlingRunnable.startScroll(i, i2, z);
    }

    void smoothScrollByOffset(int i) {
        int lastVisiblePosition;
        View childAt;
        if (i < 0) {
            lastVisiblePosition = getFirstVisiblePosition();
        } else {
            lastVisiblePosition = i > 0 ? getLastVisiblePosition() : -1;
        }
        if (lastVisiblePosition <= -1 || (childAt = getChildAt(lastVisiblePosition - getFirstVisiblePosition())) == null) {
            return;
        }
        if (childAt.getGlobalVisibleRect(new Rect())) {
            float width = (r2.width() * r2.height()) / (childAt.getWidth() * childAt.getHeight());
            if (i < 0 && width < 0.75f) {
                lastVisiblePosition++;
            } else if (i > 0 && width < 0.75f) {
                lastVisiblePosition--;
            }
        }
        smoothScrollToPosition(Math.max(0, Math.min(getCount(), lastVisiblePosition + i)));
    }

    private void createScrollingCache() {
        if (!this.mScrollingCacheEnabled || this.mCachingStarted || isHardwareAccelerated()) {
            return;
        }
        setChildrenDrawnWithCacheEnabled(true);
        setChildrenDrawingCacheEnabled(true);
        this.mCachingActive = true;
        this.mCachingStarted = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearScrollingCache() {
        if (isHardwareAccelerated()) {
            return;
        }
        if (this.mClearScrollingCache == null) {
            this.mClearScrollingCache = new Runnable() { // from class: android.widget.SemHorizontalAbsListView.4
                @Override // java.lang.Runnable
                public void run() {
                    if (SemHorizontalAbsListView.this.mCachingStarted) {
                        SemHorizontalAbsListView semHorizontalAbsListView = SemHorizontalAbsListView.this;
                        semHorizontalAbsListView.mCachingActive = false;
                        semHorizontalAbsListView.mCachingStarted = false;
                        SemHorizontalAbsListView.this.setChildrenDrawnWithCacheEnabled(false);
                        if ((SemHorizontalAbsListView.this.mPersistentDrawingCache & 2) == 0) {
                            SemHorizontalAbsListView.this.setChildrenDrawingCacheEnabled(false);
                        }
                        if (SemHorizontalAbsListView.this.isAlwaysDrawnWithCacheEnabled()) {
                            return;
                        }
                        SemHorizontalAbsListView.this.invalidate();
                    }
                }
            };
        }
        post(this.mClearScrollingCache);
    }

    @Deprecated
    public boolean canScrollList(int i) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        int i2 = this.mFirstPosition;
        Rect rect = this.mListPadding;
        if (i > 0) {
            return this.mIsRTL ? i2 > 0 : childCount + i2 < this.mItemCount || (this.mIsRTL ? getChildAt(0).getRight() : getChildAt(childCount + (-1)).getRight()) > getWidth() - rect.right;
        }
        return this.mIsRTL ? childCount + i2 < this.mItemCount : i2 > 0 || (this.mIsRTL ? getChildAt(childCount + (-1)).getLeft() : getChildAt(0).getLeft()) < rect.left;
    }

    /* JADX WARN: Code restructure failed: missing block: B:178:0x00e9, code lost:
    
        if (r2 <= 0) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00cb, code lost:
    
        if (r2 <= 0) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00cd, code lost:
    
        r6 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean trackMotionScroll(int r19, int r20) {
        /*
            Method dump skipped, instructions count: 627
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SemHorizontalAbsListView.trackMotionScroll(int, int):boolean");
    }

    void hideSelector() {
        if (this.mSelectedPosition != -1) {
            if (this.mLayoutMode != 4) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
            if (this.mNextSelectedPosition >= 0 && this.mNextSelectedPosition != this.mSelectedPosition) {
                this.mResurrectToPosition = this.mNextSelectedPosition;
            }
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectedLeft = 0;
        }
    }

    int reconcileSelectedPosition() {
        int i = this.mSelectedPosition;
        if (i < 0) {
            i = this.mResurrectToPosition;
        }
        return Math.min(Math.max(0, i), this.mItemCount - 1);
    }

    int findClosestMotionRow(int i) {
        if (getChildCount() == 0) {
            return -1;
        }
        int findMotionRow = findMotionRow(i);
        return findMotionRow != -1 ? findMotionRow : (this.mFirstPosition + r0) - 1;
    }

    @Deprecated
    public void invalidateViews() {
        this.mDataChanged = true;
        rememberSyncState();
        requestLayout();
        invalidate();
    }

    boolean resurrectSelectionIfNeeded() {
        if (this.mSelectedPosition >= 0 || !resurrectSelection()) {
            return false;
        }
        updateSelectorState();
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00e5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00e6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean resurrectSelection() {
        /*
            Method dump skipped, instructions count: 231
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SemHorizontalAbsListView.resurrectSelection():boolean");
    }

    void confirmCheckedPositionsById() {
        ActionMode actionMode;
        MultiChoiceModeWrapper multiChoiceModeWrapper;
        this.mCheckStates.clear();
        int i = 0;
        boolean z = false;
        while (i < this.mCheckedIdStates.size()) {
            long keyAt = this.mCheckedIdStates.keyAt(i);
            int intValue = this.mCheckedIdStates.valueAt(i).intValue();
            if (keyAt != this.mAdapter.getItemId(intValue)) {
                int max = Math.max(0, intValue - 20);
                int min = Math.min(intValue + 20, this.mItemCount);
                while (true) {
                    if (max >= min) {
                        this.mCheckedIdStates.delete(keyAt);
                        i--;
                        this.mCheckedItemCount--;
                        ActionMode actionMode2 = this.mChoiceActionMode;
                        if (actionMode2 != null && (multiChoiceModeWrapper = this.mMultiChoiceModeCallback) != null) {
                            multiChoiceModeWrapper.onItemCheckedStateChanged(actionMode2, intValue, keyAt, false);
                        }
                        z = true;
                    } else {
                        if (keyAt == this.mAdapter.getItemId(max)) {
                            this.mCheckStates.put(max, true);
                            this.mCheckedIdStates.setValueAt(i, Integer.valueOf(max));
                            break;
                        }
                        max++;
                    }
                }
            } else {
                this.mCheckStates.put(intValue, true);
            }
            i++;
        }
        if (!z || (actionMode = this.mChoiceActionMode) == null) {
            return;
        }
        actionMode.invalidate();
    }

    @Override // android.widget.AdapterView
    @Deprecated
    protected void handleDataChanged() {
        ListAdapter listAdapter;
        int i = this.mItemCount;
        int i2 = this.mLastHandledItemCount;
        this.mLastHandledItemCount = this.mItemCount;
        if (this.mIsMultiFocusEnabled && this.mAdapter != null && this.mItemCount != this.mOldAdapterItemCount) {
            this.mSemPressItemListArray = new ArrayList<>();
            resetPressItemListArray();
            this.mOldAdapterItemCount = this.mItemCount;
        }
        if (this.mChoiceMode != 0 && (listAdapter = this.mAdapter) != null && listAdapter.hasStableIds()) {
            confirmCheckedPositionsById();
        }
        this.mRecycler.clearTransientStateViews();
        if (i > 0) {
            if (this.mNeedSync) {
                this.mNeedSync = false;
                this.mPendingSync = null;
                int i3 = this.mTranscriptMode;
                if (i3 == 2) {
                    this.mLayoutMode = 3;
                    return;
                }
                if (i3 == 1) {
                    if (this.mForceTranscriptScroll) {
                        this.mForceTranscriptScroll = false;
                        this.mLayoutMode = 3;
                        return;
                    }
                    int childCount = getChildCount();
                    int width = getWidth() - getPaddingRight();
                    View childAt = this.mIsRTL ? getChildAt(0) : getChildAt(childCount - 1);
                    int right = childAt != null ? childAt.getRight() : width;
                    if (this.mFirstPosition + childCount >= i2 && right <= width) {
                        this.mLayoutMode = 3;
                        return;
                    }
                    awakenScrollBars();
                }
                int i4 = this.mSyncMode;
                if (i4 != 0) {
                    if (i4 == 1) {
                        this.mLayoutMode = 5;
                        this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), i - 1);
                        return;
                    }
                } else {
                    if (isInTouchMode()) {
                        this.mLayoutMode = 5;
                        this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), i - 1);
                        return;
                    }
                    int findSyncPosition = findSyncPosition();
                    if (findSyncPosition >= 0 && lookForSelectablePosition(findSyncPosition, true) == findSyncPosition) {
                        this.mSyncPosition = findSyncPosition;
                        if (this.mSyncHeight == getWidth()) {
                            this.mLayoutMode = 5;
                        } else {
                            this.mLayoutMode = 2;
                        }
                        setNextSelectedPositionInt(findSyncPosition);
                        return;
                    }
                }
            }
            if (!isInTouchMode()) {
                int selectedItemPosition = getSelectedItemPosition();
                if (selectedItemPosition >= i) {
                    selectedItemPosition = i - 1;
                }
                if (selectedItemPosition < 0) {
                    selectedItemPosition = 0;
                }
                int lookForSelectablePosition = lookForSelectablePosition(selectedItemPosition, true);
                if (lookForSelectablePosition >= 0) {
                    setNextSelectedPositionInt(lookForSelectablePosition);
                    return;
                }
                int lookForSelectablePosition2 = lookForSelectablePosition(selectedItemPosition, false);
                if (lookForSelectablePosition2 >= 0) {
                    setNextSelectedPositionInt(lookForSelectablePosition2);
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
    protected void onDisplayHint(int i) {
        PopupWindow popupWindow;
        PopupWindow popupWindow2;
        super.onDisplayHint(i);
        if (i != 0) {
            if (i == 4 && (popupWindow2 = this.mPopup) != null && popupWindow2.isShowing()) {
                dismissPopup();
            }
        } else if (this.mFiltered && (popupWindow = this.mPopup) != null && !popupWindow.isShowing()) {
            showPopup();
        }
        this.mPopupHidden = i == 4;
    }

    private void dismissPopup() {
        PopupWindow popupWindow = this.mPopup;
        if (popupWindow != null) {
            popupWindow.dismiss();
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
        int i = getResources().getDisplayMetrics().heightPixels;
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        int height = ((i - iArr[1]) - getHeight()) + ((int) (this.mDensityScale * 20.0f));
        if (!this.mPopup.isShowing()) {
            this.mPopup.showAtLocation(this, 81, iArr[0], height);
        } else {
            this.mPopup.update(iArr[0], height, -1, -1);
        }
    }

    static int getDistance(Rect rect, Rect rect2, int i) {
        int width;
        int height;
        int width2;
        int i2;
        int height2;
        int i3;
        if (i == 1 || i == 2) {
            width = rect.right + (rect.width() / 2);
            height = (rect.height() / 2) + rect.top;
            width2 = rect2.left + (rect2.width() / 2);
            i2 = rect2.top;
            height2 = rect2.height() / 2;
        } else {
            if (i != 17) {
                if (i == 33) {
                    width = rect.left + (rect.width() / 2);
                    height = rect.top;
                    width2 = rect2.left + (rect2.width() / 2);
                    i3 = rect2.bottom;
                } else if (i == 66) {
                    width = rect.right;
                    height = (rect.height() / 2) + rect.top;
                    width2 = rect2.left;
                    i2 = rect2.top;
                    height2 = rect2.height() / 2;
                } else if (i == 130) {
                    width = rect.left + (rect.width() / 2);
                    height = rect.bottom;
                    width2 = rect2.left + (rect2.width() / 2);
                    i3 = rect2.top;
                } else {
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
                }
                int i4 = width2 - width;
                int i5 = i3 - height;
                return (i5 * i5) + (i4 * i4);
            }
            width = rect.left;
            height = (rect.height() / 2) + rect.top;
            width2 = rect2.right;
            i2 = rect2.top;
            height2 = rect2.height() / 2;
        }
        i3 = height2 + i2;
        int i42 = width2 - width;
        int i52 = i3 - height;
        return (i52 * i52) + (i42 * i42);
    }

    @Override // android.widget.AdapterView
    @Deprecated
    protected boolean isInFilterMode() {
        return this.mFiltered;
    }

    boolean sendToTextFilter(int i, int i2, KeyEvent keyEvent) {
        boolean z;
        boolean z2;
        boolean z3;
        PopupWindow popupWindow;
        if (!acceptFilter()) {
            return false;
        }
        if (i == 4) {
            if (this.mFiltered && (popupWindow = this.mPopup) != null && popupWindow.isShowing()) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                } else if (keyEvent.getAction() == 1 && keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    this.mTextFilter.lambda$setTextAsync$0("");
                }
                z = true;
                z2 = z;
                z3 = false;
            }
            z = false;
            z2 = z;
            z3 = false;
        } else if (i != 62) {
            if (i != 66 && i != 160) {
                switch (i) {
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                        break;
                    default:
                        z2 = false;
                        z3 = true;
                        break;
                }
            }
            z3 = false;
            z2 = false;
        } else {
            z3 = this.mFiltered;
            z2 = false;
        }
        if (z3) {
            createTextFilter(true);
            KeyEvent changeTimeRepeat = keyEvent.getRepeatCount() > 0 ? KeyEvent.changeTimeRepeat(keyEvent, keyEvent.getEventTime(), 0) : keyEvent;
            int action = keyEvent.getAction();
            if (action == 0) {
                boolean onKeyDown = this.mTextFilter.onKeyDown(i, changeTimeRepeat);
                if (i == 59 || i == 60) {
                    this.mIsShiftkeyPressed = true;
                    return onKeyDown;
                }
                if (i != 113 && i != 114) {
                    return onKeyDown;
                }
                this.mIsCtrlkeyPressed = true;
                return onKeyDown;
            }
            if (action == 1) {
                boolean onKeyUp = this.mTextFilter.onKeyUp(i, changeTimeRepeat);
                if (i != 59 && i != 60) {
                    if (i != 113 && i != 114) {
                        return onKeyUp;
                    }
                    this.mIsCtrlkeyPressed = false;
                    return onKeyUp;
                }
                this.mIsShiftkeyPressed = false;
                this.mOldKeyCode = 0;
                this.mCurrentKeyCode = 0;
                this.mFirstPressedPoint = -1;
                this.mSecondPressedPoint = -1;
                return onKeyUp;
            }
            if (action == 2) {
                return this.mTextFilter.onKeyMultiple(i, i2, keyEvent);
            }
        }
        return z2;
    }

    @Override // android.view.View
    @Deprecated
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        if (!isTextFilterEnabled()) {
            return null;
        }
        if (this.mPublicInputConnection == null) {
            this.mDefInputConnection = new BaseInputConnection((View) this, false);
            this.mPublicInputConnection = new InputConnectionWrapper(editorInfo);
        }
        editorInfo.inputType = 177;
        editorInfo.imeOptions = 6;
        return this.mPublicInputConnection;
    }

    private class InputConnectionWrapper implements InputConnection {
        private final EditorInfo mOutAttrs;
        private InputConnection mTarget;

        public InputConnectionWrapper(EditorInfo editorInfo) {
            this.mOutAttrs = editorInfo;
        }

        private InputConnection getTarget() {
            if (this.mTarget == null) {
                this.mTarget = SemHorizontalAbsListView.this.getTextFilterInput().onCreateInputConnection(this.mOutAttrs);
            }
            return this.mTarget;
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean reportFullscreenMode(boolean z) {
            return SemHorizontalAbsListView.this.mDefInputConnection.reportFullscreenMode(z);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean performEditorAction(int i) {
            if (i != 6) {
                return false;
            }
            InputMethodManager inputMethodManager = (InputMethodManager) SemHorizontalAbsListView.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager == null) {
                return true;
            }
            inputMethodManager.hideSoftInputFromWindow(SemHorizontalAbsListView.this.getWindowToken(), 0);
            return true;
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean sendKeyEvent(KeyEvent keyEvent) {
            return SemHorizontalAbsListView.this.mDefInputConnection.sendKeyEvent(keyEvent);
        }

        @Override // android.view.inputmethod.InputConnection
        public CharSequence getTextBeforeCursor(int i, int i2) {
            InputConnection inputConnection = this.mTarget;
            return inputConnection == null ? "" : inputConnection.getTextBeforeCursor(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public CharSequence getTextAfterCursor(int i, int i2) {
            InputConnection inputConnection = this.mTarget;
            return inputConnection == null ? "" : inputConnection.getTextAfterCursor(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public CharSequence getSelectedText(int i) {
            InputConnection inputConnection = this.mTarget;
            return inputConnection == null ? "" : inputConnection.getSelectedText(i);
        }

        @Override // android.view.inputmethod.InputConnection
        public int getCursorCapsMode(int i) {
            InputConnection inputConnection = this.mTarget;
            if (inputConnection == null) {
                return 16384;
            }
            return inputConnection.getCursorCapsMode(i);
        }

        @Override // android.view.inputmethod.InputConnection
        public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
            return getTarget().getExtractedText(extractedTextRequest, i);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean deleteSurroundingText(int i, int i2) {
            return getTarget().deleteSurroundingText(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
            return getTarget().deleteSurroundingTextInCodePoints(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean setComposingText(CharSequence charSequence, int i) {
            return getTarget().setComposingText(charSequence, i);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean setComposingRegion(int i, int i2) {
            return getTarget().setComposingRegion(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean finishComposingText() {
            InputConnection inputConnection = this.mTarget;
            return inputConnection == null || inputConnection.finishComposingText();
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean commitText(CharSequence charSequence, int i) {
            return getTarget().commitText(charSequence, i);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean commitCompletion(CompletionInfo completionInfo) {
            return getTarget().commitCompletion(completionInfo);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean commitCorrection(CorrectionInfo correctionInfo) {
            return getTarget().commitCorrection(correctionInfo);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean setSelection(int i, int i2) {
            return getTarget().setSelection(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean performContextMenuAction(int i) {
            return getTarget().performContextMenuAction(i);
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
        public boolean clearMetaKeyStates(int i) {
            return getTarget().clearMetaKeyStates(i);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean performPrivateCommand(String str, Bundle bundle) {
            return getTarget().performPrivateCommand(str, bundle);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean requestCursorUpdates(int i) {
            return getTarget().requestCursorUpdates(i);
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
        public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
            return getTarget().commitContent(inputContentInfo, i, bundle);
        }
    }

    @Override // android.view.View
    @Deprecated
    public boolean checkInputConnectionProxy(View view) {
        return view == this.mTextFilter;
    }

    private void createTextFilter(boolean z) {
        if (this.mPopup == null) {
            PopupWindow popupWindow = new PopupWindow(getContext());
            popupWindow.setFocusable(false);
            popupWindow.setTouchable(false);
            popupWindow.setInputMethodMode(2);
            popupWindow.setContentView(getTextFilterInput());
            popupWindow.setWidth(-2);
            popupWindow.setHeight(-2);
            popupWindow.setBackgroundDrawable(null);
            this.mPopup = popupWindow;
            getViewTreeObserver().addOnGlobalLayoutListener(this);
            this.mGlobalLayoutListenerAddedFilter = true;
        }
        if (z) {
            this.mPopup.setAnimationStyle(R.style.Animation_TypingFilter);
        } else {
            this.mPopup.setAnimationStyle(R.style.Animation_TypingFilterRestore);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EditText getTextFilterInput() {
        if (this.mTextFilter == null) {
            EditText editText = (EditText) LayoutInflater.from(getContext()).inflate(R.layout.typing_filter, (ViewGroup) null);
            this.mTextFilter = editText;
            editText.setRawInputType(177);
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
            PopupWindow popupWindow = this.mPopup;
            if (popupWindow == null || !popupWindow.isShowing()) {
                return;
            }
            dismissPopup();
        }
    }

    @Deprecated
    public boolean hasTextFilter() {
        return this.mFiltered;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    @Deprecated
    public void onGlobalLayout() {
        PopupWindow popupWindow;
        if (isShown()) {
            if (!this.mFiltered || (popupWindow = this.mPopup) == null || popupWindow.isShowing() || this.mPopupHidden) {
                return;
            }
            showPopup();
            return;
        }
        PopupWindow popupWindow2 = this.mPopup;
        if (popupWindow2 == null || !popupWindow2.isShowing()) {
            return;
        }
        dismissPopup();
    }

    @Override // android.text.TextWatcher
    @Deprecated
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (isTextFilterEnabled()) {
            createTextFilter(true);
            int length = charSequence.length();
            boolean isShowing = this.mPopup.isShowing();
            if (!isShowing && length > 0) {
                showPopup();
                this.mFiltered = true;
            } else if (isShowing && length == 0) {
                dismissPopup();
                this.mFiltered = false;
            }
            ListAdapter listAdapter = this.mAdapter;
            if (listAdapter instanceof Filterable) {
                Filter filter = ((Filterable) listAdapter).getFilter();
                if (filter != null) {
                    filter.filter(charSequence, this);
                    return;
                }
                throw new IllegalStateException("You cannot call onTextChanged with a non filterable adapter");
            }
        }
    }

    @Override // android.widget.Filter.FilterListener
    @Deprecated
    public void onFilterComplete(int i) {
        if (this.mSelectedPosition >= 0 || i <= 0) {
            return;
        }
        this.mResurrectToPosition = -1;
        resurrectSelection();
    }

    @Override // android.view.ViewGroup
    @Deprecated
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2, 0);
    }

    @Override // android.view.ViewGroup
    @Deprecated
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    @Deprecated
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    @Deprecated
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Deprecated
    public void setTranscriptMode(int i) {
        this.mTranscriptMode = i;
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
    public void reclaimViews(List<View> list) {
        int childCount = getChildCount();
        RecyclerListener recyclerListener = this.mRecycler.mRecyclerListener;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams != null && this.mRecycler.shouldRecycleViewType(layoutParams.viewType)) {
                list.add(childAt);
                childAt.setAccessibilityDelegate(null);
                if (recyclerListener != null) {
                    recyclerListener.onMovedToScrapHeap(childAt);
                }
            }
        }
        this.mRecycler.reclaimScrapViews(list);
        removeAllViewsInLayout();
    }

    private void finishGlows() {
        EdgeEffect edgeEffect = this.mEdgeGlowLeft;
        if (edgeEffect != null) {
            edgeEffect.finish();
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
    public void setRemoteViewsAdapter(Intent intent, boolean z) {
        if (this.mRemoteAdapter == null || !new Intent.FilterComparison(intent).equals(new Intent.FilterComparison(this.mRemoteAdapter.getRemoteViewsServiceIntent()))) {
            this.mDeferNotifyDataSetChanged = false;
            RemoteViewsAdapter remoteViewsAdapter = new RemoteViewsAdapter(getContext(), intent, this, z);
            this.mRemoteAdapter = remoteViewsAdapter;
            if (remoteViewsAdapter.isDataReady()) {
                setAdapter(this.mRemoteAdapter);
            }
        }
    }

    public void setRemoteViewsInteractionHandler(RemoteViews.InteractionHandler interactionHandler) {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.setRemoteViewsInteractionHandler(interactionHandler);
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
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteAdapter;
        if (remoteViewsAdapter == this.mAdapter) {
            if (remoteViewsAdapter == null) {
                return false;
            }
            remoteViewsAdapter.superNotifyDataSetChanged();
            return true;
        }
        setAdapter(remoteViewsAdapter);
        if (this.mDeferNotifyDataSetChanged) {
            this.mRemoteAdapter.notifyDataSetChanged();
            this.mDeferNotifyDataSetChanged = false;
        }
        return false;
    }

    void setVisibleRangeHint(int i, int i2) {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.setVisibleRangeHint(i, i2);
        }
    }

    @Deprecated
    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.mRecycler.mRecyclerListener = recyclerListener;
    }

    class AdapterDataSetObserver extends AdapterView<ListAdapter>.AdapterDataSetObserver {
        AdapterDataSetObserver() {
            super();
        }

        @Override // android.widget.AdapterView.AdapterDataSetObserver, android.database.DataSetObserver
        public void onChanged() {
            super.onChanged();
            if (SemHorizontalAbsListView.this.mFastScroll != null) {
                SemHorizontalAbsListView.this.mFastScroll.onSectionsChanged();
            }
        }

        @Override // android.widget.AdapterView.AdapterDataSetObserver, android.database.DataSetObserver
        public void onInvalidated() {
            super.onInvalidated();
            if (SemHorizontalAbsListView.this.mFastScroll != null) {
                SemHorizontalAbsListView.this.mFastScroll.onSectionsChanged();
            }
        }
    }

    class MultiChoiceModeWrapper implements MultiChoiceModeListener {
        private MultiChoiceModeListener mWrapped;

        MultiChoiceModeWrapper() {
        }

        public void setWrapped(MultiChoiceModeListener multiChoiceModeListener) {
            this.mWrapped = multiChoiceModeListener;
        }

        public boolean hasWrappedCallback() {
            return this.mWrapped != null;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            if (!this.mWrapped.onCreateActionMode(actionMode, menu)) {
                return false;
            }
            SemHorizontalAbsListView.this.setLongClickable(false);
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            SemHorizontalAbsListView.this.mChoiceActionMode = null;
            SemHorizontalAbsListView.this.clearChoices();
            SemHorizontalAbsListView.this.mDataChanged = true;
            SemHorizontalAbsListView.this.rememberSyncState();
            SemHorizontalAbsListView.this.requestLayout();
            SemHorizontalAbsListView.this.setLongClickable(true);
        }

        @Override // android.widget.SemHorizontalAbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z) {
            this.mWrapped.onItemCheckedStateChanged(actionMode, i, j, z);
            if (SemHorizontalAbsListView.this.getCheckedItemCount() != 0 || SemHorizontalAbsListView.this.mSemCustomMultiChoiceMode) {
                return;
            }
            actionMode.finish();
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

        @ViewDebug.ExportedProperty(category = Slice.HINT_LIST, mapping = {@ViewDebug.IntToString(from = -1, to = "ITEM_VIEW_TYPE_IGNORE"), @ViewDebug.IntToString(from = -2, to = "ITEM_VIEW_TYPE_HEADER_OR_FOOTER")})
        int viewType;

        @Deprecated
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.itemId = -1L;
        }

        @Deprecated
        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.itemId = -1L;
        }

        @Deprecated
        public LayoutParams(int i, int i2, int i3) {
            super(i, i2);
            this.itemId = -1L;
            this.viewType = i3;
        }

        @Deprecated
        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
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

        void addScrapView(View view, boolean z) {
        }

        public void addShouldRetainView(int i, View view) {
        }

        public void clearShouldRetainView() {
        }

        public Object[] getRetainViewPositions() {
            return null;
        }

        public View getShouldRetainView(int i) {
            return null;
        }

        public int getShouldRetainViewCount() {
            return 0;
        }

        public void removeShouldRetainView(int i) {
        }

        public boolean shouldRecycleViewType(int i) {
            return i >= 0;
        }

        RecycleBin() {
        }

        public void setViewTypeCount(int i) {
            if (i < 1) {
                throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
            }
            ArrayList<View>[] arrayListArr = new ArrayList[i];
            for (int i2 = 0; i2 < i; i2++) {
                arrayListArr[i2] = new ArrayList<>();
            }
            this.mViewTypeCount = i;
            this.mCurrentScrap = arrayListArr[0];
            this.mScrapViews = arrayListArr;
        }

        public void markChildrenDirty() {
            int i = this.mViewTypeCount;
            if (i == 1) {
                ArrayList<View> arrayList = this.mCurrentScrap;
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    arrayList.get(i2).forceLayout();
                }
            } else {
                for (int i3 = 0; i3 < i; i3++) {
                    ArrayList<View> arrayList2 = this.mScrapViews[i3];
                    int size2 = arrayList2.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        arrayList2.get(i4).forceLayout();
                    }
                }
            }
            SparseArray<View> sparseArray = this.mTransientStateViews;
            if (sparseArray != null) {
                int size3 = sparseArray.size();
                for (int i5 = 0; i5 < size3; i5++) {
                    this.mTransientStateViews.valueAt(i5).forceLayout();
                }
            }
            LongSparseArray<View> longSparseArray = this.mTransientStateViewsById;
            if (longSparseArray != null) {
                int size4 = longSparseArray.size();
                for (int i6 = 0; i6 < size4; i6++) {
                    this.mTransientStateViewsById.valueAt(i6).forceLayout();
                }
            }
        }

        void clear() {
            int i = this.mViewTypeCount;
            if (i == 1) {
                clearScrap(this.mCurrentScrap);
            } else {
                for (int i2 = 0; i2 < i; i2++) {
                    clearScrap(this.mScrapViews[i2]);
                }
            }
            clearTransientStateViews();
        }

        void fillActiveViews(int i, int i2) {
            if (this.mActiveViews.length < i) {
                this.mActiveViews = new View[i];
            }
            this.mFirstActivePosition = i2;
            View[] viewArr = this.mActiveViews;
            for (int i3 = 0; i3 < i; i3++) {
                View childAt = SemHorizontalAbsListView.this.getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams != null && layoutParams.viewType != -2) {
                    viewArr[i3] = childAt;
                }
            }
        }

        View getActiveView(int i) {
            int i2 = i - this.mFirstActivePosition;
            View[] viewArr = this.mActiveViews;
            if (i2 < 0 || i2 >= viewArr.length) {
                return null;
            }
            View view = viewArr[i2];
            viewArr[i2] = null;
            return view;
        }

        View getTransientStateView(int i) {
            int indexOfKey;
            if (SemHorizontalAbsListView.this.mAdapter != null && SemHorizontalAbsListView.this.mAdapterHasStableIds && this.mTransientStateViewsById != null) {
                long itemId = SemHorizontalAbsListView.this.mAdapter.getItemId(i);
                View view = this.mTransientStateViewsById.get(itemId);
                this.mTransientStateViewsById.remove(itemId);
                return view;
            }
            SparseArray<View> sparseArray = this.mTransientStateViews;
            if (sparseArray == null || (indexOfKey = sparseArray.indexOfKey(i)) < 0) {
                return null;
            }
            View valueAt = this.mTransientStateViews.valueAt(indexOfKey);
            this.mTransientStateViews.removeAt(indexOfKey);
            return valueAt;
        }

        void clearTransientStateViews() {
            SparseArray<View> sparseArray = this.mTransientStateViews;
            if (sparseArray != null) {
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    removeDetachedView(sparseArray.valueAt(i), false);
                }
                sparseArray.clear();
            }
            LongSparseArray<View> longSparseArray = this.mTransientStateViewsById;
            if (longSparseArray != null) {
                int size2 = longSparseArray.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    removeDetachedView(longSparseArray.valueAt(i2), false);
                }
                longSparseArray.clear();
            }
        }

        View getScrapView(int i) {
            if (this.mViewTypeCount == 1) {
                return retrieveFromScrap(this.mCurrentScrap, i);
            }
            int itemViewType = SemHorizontalAbsListView.this.mAdapter.getItemViewType(i);
            if (itemViewType < 0) {
                return null;
            }
            ArrayList<View>[] arrayListArr = this.mScrapViews;
            if (itemViewType < arrayListArr.length) {
                return retrieveFromScrap(arrayListArr[itemViewType], i);
            }
            return null;
        }

        void addScrapView(View view, int i) {
            LayoutParams layoutParams;
            if (view == null || (layoutParams = (LayoutParams) view.getLayoutParams()) == null) {
                return;
            }
            layoutParams.scrappedFromPosition = i;
            int i2 = layoutParams.viewType;
            if (shouldRecycleViewType(i2)) {
                view.dispatchStartTemporaryDetach();
                SemHorizontalAbsListView.this.notifyViewAccessibilityStateChangedIfNeeded(1);
                if (view.hasTransientState()) {
                    if (SemHorizontalAbsListView.this.mAdapter != null && SemHorizontalAbsListView.this.mAdapterHasStableIds) {
                        if (this.mTransientStateViewsById == null) {
                            this.mTransientStateViewsById = new LongSparseArray<>();
                        }
                        this.mTransientStateViewsById.put(layoutParams.itemId, view);
                        return;
                    } else if (!SemHorizontalAbsListView.this.mDataChanged) {
                        if (this.mTransientStateViews == null) {
                            this.mTransientStateViews = new SparseArray<>();
                        }
                        this.mTransientStateViews.put(i, view);
                        return;
                    } else {
                        if (this.mSkippedScrap == null) {
                            this.mSkippedScrap = new ArrayList<>();
                        }
                        this.mSkippedScrap.add(view);
                        return;
                    }
                }
                if (this.mViewTypeCount == 1) {
                    this.mCurrentScrap.add(view);
                } else if (!this.mScrapViews[i2].contains(view)) {
                    this.mScrapViews[i2].add(view);
                }
                RecyclerListener recyclerListener = this.mRecyclerListener;
                if (recyclerListener != null) {
                    recyclerListener.onMovedToScrapHeap(view);
                }
            }
        }

        void removeSkippedScrap() {
            ArrayList<View> arrayList = this.mSkippedScrap;
            if (arrayList == null) {
                return;
            }
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                removeDetachedView(this.mSkippedScrap.get(i), false);
            }
            this.mSkippedScrap.clear();
        }

        void scrapActiveViews() {
            View[] viewArr = this.mActiveViews;
            boolean z = this.mRecyclerListener != null;
            boolean z2 = this.mViewTypeCount > 1;
            ArrayList<View> arrayList = this.mCurrentScrap;
            for (int length = viewArr.length - 1; length >= 0; length--) {
                View view = viewArr[length];
                if (view != null) {
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    int i = layoutParams.viewType;
                    viewArr[length] = null;
                    if (view.hasTransientState()) {
                        view.dispatchStartTemporaryDetach();
                        if (SemHorizontalAbsListView.this.mAdapter != null && SemHorizontalAbsListView.this.mAdapterHasStableIds) {
                            if (this.mTransientStateViewsById == null) {
                                this.mTransientStateViewsById = new LongSparseArray<>();
                            }
                            this.mTransientStateViewsById.put(SemHorizontalAbsListView.this.mAdapter.getItemId(this.mFirstActivePosition + length), view);
                        } else if (!SemHorizontalAbsListView.this.mDataChanged) {
                            if (this.mTransientStateViews == null) {
                                this.mTransientStateViews = new SparseArray<>();
                            }
                            this.mTransientStateViews.put(this.mFirstActivePosition + length, view);
                        } else if (i != -2) {
                            removeDetachedView(view, false);
                        }
                    } else if (shouldRecycleViewType(i)) {
                        if (z2) {
                            arrayList = this.mScrapViews[i];
                        }
                        view.dispatchStartTemporaryDetach();
                        layoutParams.scrappedFromPosition = this.mFirstActivePosition + length;
                        arrayList.add(view);
                        if (z) {
                            this.mRecyclerListener.onMovedToScrapHeap(view);
                        }
                    } else if (i != -2) {
                        removeDetachedView(view, false);
                    }
                }
            }
            pruneScrapViews();
        }

        private void pruneScrapViews() {
            int length = this.mActiveViews.length;
            int i = this.mViewTypeCount;
            ArrayList<View>[] arrayListArr = this.mScrapViews;
            for (int i2 = 0; i2 < i; i2++) {
                ArrayList<View> arrayList = arrayListArr[i2];
                int size = arrayList.size();
                int i3 = size - length;
                int i4 = size - 1;
                int i5 = 0;
                while (i5 < i3) {
                    removeDetachedView(arrayList.remove(i4), false);
                    i5++;
                    i4--;
                }
            }
            SparseArray<View> sparseArray = this.mTransientStateViews;
            if (sparseArray != null) {
                int i6 = 0;
                while (i6 < sparseArray.size()) {
                    View valueAt = sparseArray.valueAt(i6);
                    if (!valueAt.hasTransientState()) {
                        removeDetachedView(valueAt, false);
                        sparseArray.removeAt(i6);
                        i6--;
                    }
                    i6++;
                }
            }
            LongSparseArray<View> longSparseArray = this.mTransientStateViewsById;
            if (longSparseArray != null) {
                int i7 = 0;
                while (i7 < longSparseArray.size()) {
                    View valueAt2 = longSparseArray.valueAt(i7);
                    if (!valueAt2.hasTransientState()) {
                        removeDetachedView(valueAt2, false);
                        longSparseArray.removeAt(i7);
                        i7--;
                    }
                    i7++;
                }
            }
        }

        void reclaimScrapViews(List<View> list) {
            int i = this.mViewTypeCount;
            if (i == 1) {
                list.addAll(this.mCurrentScrap);
                return;
            }
            ArrayList<View>[] arrayListArr = this.mScrapViews;
            for (int i2 = 0; i2 < i; i2++) {
                list.addAll(arrayListArr[i2]);
            }
        }

        void setCacheColorHint(int i) {
            int i2 = this.mViewTypeCount;
            if (i2 == 1) {
                ArrayList<View> arrayList = this.mCurrentScrap;
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    arrayList.get(i3).setDrawingCacheBackgroundColor(i);
                }
            } else {
                for (int i4 = 0; i4 < i2; i4++) {
                    ArrayList<View> arrayList2 = this.mScrapViews[i4];
                    int size2 = arrayList2.size();
                    for (int i5 = 0; i5 < size2; i5++) {
                        arrayList2.get(i5).setDrawingCacheBackgroundColor(i);
                    }
                }
            }
            for (View view : this.mActiveViews) {
                if (view != null) {
                    view.setDrawingCacheBackgroundColor(i);
                }
            }
        }

        private View retrieveFromScrap(ArrayList<View> arrayList, int i) {
            int size = arrayList.size();
            if (size <= 0) {
                return null;
            }
            for (int i2 = 0; i2 < size; i2++) {
                LayoutParams layoutParams = (LayoutParams) arrayList.get(i2).getLayoutParams();
                if (SemHorizontalAbsListView.this.mAdapterHasStableIds) {
                    if (SemHorizontalAbsListView.this.mAdapter.getItemId(i) == layoutParams.itemId) {
                        return arrayList.remove(i2);
                    }
                } else if (layoutParams.scrappedFromPosition == i) {
                    View remove = arrayList.remove(i2);
                    clearAccessibilityFromScrap(remove);
                    return remove;
                }
            }
            View remove2 = arrayList.remove(size - 1);
            clearAccessibilityFromScrap(remove2);
            return remove2;
        }

        private void clearScrap(ArrayList<View> arrayList) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                removeDetachedView(arrayList.remove((size - 1) - i), false);
            }
        }

        private void clearAccessibilityFromScrap(View view) {
            if (view.isAccessibilityFocused()) {
                view.clearAccessibilityFocus();
            }
            view.setAccessibilityDelegate(null);
        }

        private void removeDetachedView(View view, boolean z) {
            view.setAccessibilityDelegate(null);
            SemHorizontalAbsListView.this.removeDetachedView(view, z);
        }
    }

    int getWidthForPosition(int i) {
        int firstVisiblePosition = getFirstVisiblePosition();
        int childCount = getChildCount();
        int i2 = i - firstVisiblePosition;
        if (i2 >= 0 && i2 < childCount) {
            return getChildAt(i2).getWidth();
        }
        View obtainView = obtainView(i, this.mIsScrap);
        obtainView.measure(this.mHeightMeasureSpec, 0);
        int measuredWidth = obtainView.getMeasuredWidth();
        this.mRecycler.addScrapView(obtainView, i);
        return measuredWidth;
    }

    public void setSelectionFromStart(int i, int i2) {
        if (this.mAdapter == null) {
            return;
        }
        if (!isInTouchMode()) {
            i = lookForSelectablePosition(i, true);
            if (i >= 0) {
                setNextSelectedPositionInt(i);
            }
        } else {
            this.mResurrectToPosition = i;
        }
        if (i >= 0) {
            this.mLayoutMode = 4;
            if (this.mIsRTL) {
                this.mSpecificTop = getWidth() - i2;
            } else {
                this.mSpecificTop = this.mListPadding.left + i2;
            }
            if (this.mNeedSync) {
                this.mSyncPosition = i;
                this.mSyncRowId = this.mAdapter.getItemId(i);
            }
            AbsPositionScroller absPositionScroller = this.mPositionScroller;
            if (absPositionScroller != null) {
                absPositionScroller.stop();
            }
            requestLayout();
        }
    }

    static abstract class AbsPositionScroller {
        public abstract void start(int i);

        public abstract void start(int i, int i2);

        public abstract void startWithOffset(int i, int i2);

        public abstract void startWithOffset(int i, int i2, int i3);

        public abstract void stop();

        AbsPositionScroller() {
        }
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
            this.mExtraScroll = ViewConfiguration.get(SemHorizontalAbsListView.this.mContext).getScaledFadingEdgeLength();
        }

        @Override // android.widget.SemHorizontalAbsListView.AbsPositionScroller
        public void start(final int i) {
            int i2;
            stop();
            if (SemHorizontalAbsListView.this.mDataChanged) {
                SemHorizontalAbsListView.this.mPositionScrollAfterLayout = new Runnable() { // from class: android.widget.SemHorizontalAbsListView.PositionScroller.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PositionScroller.this.start(i);
                    }
                };
                return;
            }
            int childCount = SemHorizontalAbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int i3 = SemHorizontalAbsListView.this.mFirstPosition;
            int i4 = (childCount + i3) - 1;
            int max = Math.max(0, Math.min(SemHorizontalAbsListView.this.getCount() - 1, i));
            if (max < i3) {
                i2 = (i3 - max) + 1;
                this.mMode = 2;
            } else if (max > i4) {
                i2 = (max - i4) + 1;
                this.mMode = 1;
            } else {
                if (SemHorizontalAbsListView.this.mJumpScrollToTopState == SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_INITIATED) {
                    SemHorizontalAbsListView.this.mJumpScrollToTopState = SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_FINISHING;
                }
                scrollToVisible(max, -1, 200);
                return;
            }
            if (i2 > 0) {
                this.mScrollDuration = 200 / i2;
            } else {
                this.mScrollDuration = 200;
            }
            this.mTargetPos = max;
            this.mBoundPos = -1;
            this.mLastSeenPos = -1;
            SemHorizontalAbsListView.this.postOnAnimation(this);
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x0066  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x006a  */
        @Override // android.widget.SemHorizontalAbsListView.AbsPositionScroller
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void start(final int r6, final int r7) {
            /*
                r5 = this;
                r5.stop()
                r0 = -1
                if (r7 != r0) goto La
                r5.start(r6)
                return
            La:
                android.widget.SemHorizontalAbsListView r1 = android.widget.SemHorizontalAbsListView.this
                boolean r1 = r1.mDataChanged
                if (r1 == 0) goto L1a
                android.widget.SemHorizontalAbsListView r0 = android.widget.SemHorizontalAbsListView.this
                android.widget.SemHorizontalAbsListView$PositionScroller$2 r1 = new android.widget.SemHorizontalAbsListView$PositionScroller$2
                r1.<init>()
                r0.mPositionScrollAfterLayout = r1
                return
            L1a:
                android.widget.SemHorizontalAbsListView r1 = android.widget.SemHorizontalAbsListView.this
                int r1 = r1.getChildCount()
                if (r1 != 0) goto L23
                goto L56
            L23:
                android.widget.SemHorizontalAbsListView r2 = android.widget.SemHorizontalAbsListView.this
                int r2 = r2.mFirstPosition
                int r1 = r1 + r2
                r3 = 1
                int r1 = r1 - r3
                android.widget.SemHorizontalAbsListView r4 = android.widget.SemHorizontalAbsListView.this
                int r4 = r4.getCount()
                int r4 = r4 - r3
                int r6 = java.lang.Math.min(r4, r6)
                r4 = 0
                int r6 = java.lang.Math.max(r4, r6)
                r4 = 200(0xc8, float:2.8E-43)
                if (r6 >= r2) goto L50
                int r1 = r1 - r7
                if (r1 >= r3) goto L42
                goto L56
            L42:
                int r2 = r2 - r6
                int r2 = r2 + r3
                int r1 = r1 - r3
                if (r1 >= r2) goto L4c
                r2 = 4
                r5.mMode = r2
            L4a:
                r2 = r1
                goto L64
            L4c:
                r1 = 2
                r5.mMode = r1
                goto L64
            L50:
                if (r6 <= r1) goto L78
                int r2 = r7 - r2
                if (r2 >= r3) goto L57
            L56:
                return
            L57:
                int r1 = r6 - r1
                int r1 = r1 + r3
                int r2 = r2 - r3
                if (r2 >= r1) goto L61
                r1 = 3
                r5.mMode = r1
                goto L64
            L61:
                r5.mMode = r3
                goto L4a
            L64:
                if (r2 <= 0) goto L6a
                int r4 = r4 / r2
                r5.mScrollDuration = r4
                goto L6c
            L6a:
                r5.mScrollDuration = r4
            L6c:
                r5.mTargetPos = r6
                r5.mBoundPos = r7
                r5.mLastSeenPos = r0
                android.widget.SemHorizontalAbsListView r6 = android.widget.SemHorizontalAbsListView.this
                r6.postOnAnimation(r5)
                return
            L78:
                r5.scrollToVisible(r6, r7, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.SemHorizontalAbsListView.PositionScroller.start(int, int):void");
        }

        @Override // android.widget.SemHorizontalAbsListView.AbsPositionScroller
        public void startWithOffset(int i, int i2) {
            startWithOffset(i, i2, 200);
        }

        @Override // android.widget.SemHorizontalAbsListView.AbsPositionScroller
        public void startWithOffset(final int i, final int i2, final int i3) {
            int i4;
            stop();
            if (SemHorizontalAbsListView.this.mDataChanged) {
                SemHorizontalAbsListView.this.mPositionScrollAfterLayout = new Runnable() { // from class: android.widget.SemHorizontalAbsListView.PositionScroller.3
                    @Override // java.lang.Runnable
                    public void run() {
                        PositionScroller.this.startWithOffset(i, i2, i3);
                    }
                };
                return;
            }
            int childCount = SemHorizontalAbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int paddingLeft = i2 + SemHorizontalAbsListView.this.getPaddingLeft();
            this.mTargetPos = Math.max(0, Math.min(SemHorizontalAbsListView.this.getCount() - 1, i));
            this.mOffsetFromLeft = paddingLeft;
            this.mBoundPos = -1;
            this.mLastSeenPos = -1;
            this.mMode = 5;
            int i5 = SemHorizontalAbsListView.this.mFirstPosition;
            int i6 = (i5 + childCount) - 1;
            int i7 = this.mTargetPos;
            if (i7 < i5) {
                i4 = i5 - i7;
            } else {
                if (i7 <= i6) {
                    SemHorizontalAbsListView.this.smoothScrollBy(SemHorizontalAbsListView.this.getChildAt(i7 - i5).getLeft() - paddingLeft, i3, true);
                    return;
                }
                i4 = i7 - i6;
            }
            float f = i4 / childCount;
            if (f >= 1.0f) {
                i3 = (int) (i3 / f);
            }
            this.mScrollDuration = i3;
            this.mLastSeenPos = -1;
            SemHorizontalAbsListView.this.postOnAnimation(this);
        }

        void scrollToVisible(int i, int i2, int i3) {
            int i4 = SemHorizontalAbsListView.this.mFirstPosition;
            int childCount = (SemHorizontalAbsListView.this.getChildCount() + i4) - 1;
            int i5 = SemHorizontalAbsListView.this.mListPadding.left;
            int width = SemHorizontalAbsListView.this.getWidth() - SemHorizontalAbsListView.this.mListPadding.right;
            if (i < i4 || i > childCount) {
                Log.w(SemHorizontalAbsListView.TAG, "scrollToVisible called with targetPos " + i + " not visible [" + i4 + ", " + childCount + NavigationBarInflaterView.SIZE_MOD_END);
            }
            if (i2 < i4 || i2 > childCount) {
                i2 = -1;
            }
            View childAt = SemHorizontalAbsListView.this.getChildAt(i - i4);
            int left = childAt.getLeft();
            int right = childAt.getRight();
            int i6 = right > width ? right - width : 0;
            if (left < i5) {
                i6 = left - i5;
            }
            if (i6 == 0) {
                if (SemHorizontalAbsListView.this.mJumpScrollToTopState == SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_FINISHING) {
                    SemHorizontalAbsListView.this.mJumpScrollToTopState = SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_IDLE;
                    SemHorizontalAbsListView.this.postOnJumpScrollToFinished();
                    return;
                }
                return;
            }
            if (i2 >= 0) {
                View childAt2 = SemHorizontalAbsListView.this.getChildAt(i2 - i4);
                int left2 = childAt2.getLeft();
                int right2 = childAt2.getRight();
                int abs = Math.abs(i6);
                if (i6 < 0 && right2 + abs > width) {
                    i6 = Math.max(0, right2 - width);
                } else if (i6 > 0 && left2 - abs < i5) {
                    i6 = Math.min(0, left2 - i5);
                }
            }
            SemHorizontalAbsListView.this.smoothScrollBy(i6, i3);
        }

        @Override // android.widget.SemHorizontalAbsListView.AbsPositionScroller
        public void stop() {
            SemHorizontalAbsListView.this.removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            int width = SemHorizontalAbsListView.this.getWidth();
            int i = SemHorizontalAbsListView.this.mFirstPosition;
            int i2 = this.mMode;
            if (i2 == 1) {
                int childCount = SemHorizontalAbsListView.this.getChildCount() - 1;
                int i3 = i + childCount;
                if (childCount < 0) {
                    return;
                }
                if (i3 == this.mLastSeenPos) {
                    SemHorizontalAbsListView.this.postOnAnimation(this);
                    return;
                }
                View childAt = SemHorizontalAbsListView.this.getChildAt(childCount);
                SemHorizontalAbsListView.this.smoothScrollBy((childAt.getWidth() - (width - childAt.getLeft())) + (i3 < SemHorizontalAbsListView.this.mItemCount - 1 ? Math.max(SemHorizontalAbsListView.this.mListPadding.right, this.mExtraScroll) : SemHorizontalAbsListView.this.mListPadding.right), this.mScrollDuration, true);
                this.mLastSeenPos = i3;
                if (i3 < this.mTargetPos) {
                    SemHorizontalAbsListView.this.postOnAnimation(this);
                    return;
                }
                return;
            }
            int i4 = 0;
            if (i2 == 2) {
                if (i == this.mLastSeenPos) {
                    SemHorizontalAbsListView.this.postOnAnimation(this);
                    return;
                }
                View childAt2 = SemHorizontalAbsListView.this.getChildAt(0);
                if (childAt2 == null) {
                    return;
                }
                SemHorizontalAbsListView.this.smoothScrollBy(childAt2.getLeft() - (i > 0 ? Math.max(this.mExtraScroll, SemHorizontalAbsListView.this.mListPadding.left) : SemHorizontalAbsListView.this.mListPadding.left), this.mScrollDuration, true);
                this.mLastSeenPos = i;
                if (i > this.mTargetPos) {
                    SemHorizontalAbsListView.this.postOnAnimation(this);
                    return;
                } else {
                    if (SemHorizontalAbsListView.this.mJumpScrollToTopState == SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_INITIATED) {
                        SemHorizontalAbsListView.this.mJumpScrollToTopState = SemHorizontalAbsListView.JUMP_SCROLL_TO_TOP_FINISHING;
                        return;
                    }
                    return;
                }
            }
            if (i2 == 3) {
                int childCount2 = SemHorizontalAbsListView.this.getChildCount();
                if (i == this.mBoundPos || childCount2 <= 1 || childCount2 + i >= SemHorizontalAbsListView.this.mItemCount) {
                    return;
                }
                int i5 = i + 1;
                if (i5 == this.mLastSeenPos) {
                    SemHorizontalAbsListView.this.postOnAnimation(this);
                    return;
                }
                View childAt3 = SemHorizontalAbsListView.this.getChildAt(1);
                int width2 = childAt3.getWidth();
                int left = childAt3.getLeft();
                int max = Math.max(SemHorizontalAbsListView.this.mListPadding.right, this.mExtraScroll);
                if (i5 < this.mBoundPos) {
                    SemHorizontalAbsListView.this.smoothScrollBy(Math.max(0, (width2 + left) - max), this.mScrollDuration, true);
                    this.mLastSeenPos = i5;
                    SemHorizontalAbsListView.this.postOnAnimation(this);
                    return;
                } else {
                    if (left > max) {
                        SemHorizontalAbsListView.this.smoothScrollBy(left - max, this.mScrollDuration, true);
                        return;
                    }
                    return;
                }
            }
            if (i2 != 4) {
                if (i2 != 5) {
                    return;
                }
                this.mLastSeenPos = i;
                int childCount3 = SemHorizontalAbsListView.this.getChildCount();
                int i6 = this.mTargetPos;
                int i7 = (i + childCount3) - 1;
                if (i6 < i) {
                    i4 = (i - i6) + 1;
                } else if (i6 > i7) {
                    i4 = i6 - i7;
                }
                float min = Math.min(Math.abs(i4 / childCount3), 1.0f);
                if (i6 < i) {
                    SemHorizontalAbsListView.this.smoothScrollBy((int) ((SemHorizontalAbsListView.this.mIsRTL ? SemHorizontalAbsListView.this.getWidth() : -SemHorizontalAbsListView.this.getWidth()) * min), (int) (this.mScrollDuration * min), true);
                    SemHorizontalAbsListView.this.postOnAnimation(this);
                    return;
                } else if (i6 > i7) {
                    SemHorizontalAbsListView.this.smoothScrollBy((int) ((SemHorizontalAbsListView.this.mIsRTL ? -SemHorizontalAbsListView.this.getWidth() : SemHorizontalAbsListView.this.getWidth()) * min), (int) (this.mScrollDuration * min), true);
                    SemHorizontalAbsListView.this.postOnAnimation(this);
                    return;
                } else {
                    SemHorizontalAbsListView.this.smoothScrollBy(SemHorizontalAbsListView.this.getChildAt(i6 - i).getLeft() - this.mOffsetFromLeft, (int) (this.mScrollDuration * (Math.abs(r0) / SemHorizontalAbsListView.this.getWidth())), true);
                    return;
                }
            }
            int childCount4 = SemHorizontalAbsListView.this.getChildCount() - 2;
            if (childCount4 < 0) {
                return;
            }
            int i8 = i + childCount4;
            if (i8 == this.mLastSeenPos) {
                SemHorizontalAbsListView.this.postOnAnimation(this);
                return;
            }
            View childAt4 = SemHorizontalAbsListView.this.getChildAt(childCount4);
            int width3 = childAt4.getWidth();
            int left2 = childAt4.getLeft();
            int i9 = width - left2;
            int max2 = Math.max(SemHorizontalAbsListView.this.mListPadding.left, this.mExtraScroll);
            this.mLastSeenPos = i8;
            if (i8 > this.mBoundPos) {
                SemHorizontalAbsListView.this.smoothScrollBy(-(i9 - max2), this.mScrollDuration, true);
                SemHorizontalAbsListView.this.postOnAnimation(this);
                return;
            }
            int i10 = width - max2;
            int i11 = left2 + width3;
            if (i10 > i11) {
                SemHorizontalAbsListView.this.smoothScrollBy(-(i10 - i11), this.mScrollDuration, true);
            }
        }
    }

    @Override // android.view.View
    @Deprecated
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        this.mHoverPosition = -1;
        if (i != 0) {
            releaseAllBoosters();
        }
    }

    private static class HoverScrollHandler extends Handler {
        private final WeakReference<SemHorizontalAbsListView> mListView;

        HoverScrollHandler(SemHorizontalAbsListView semHorizontalAbsListView) {
            this.mListView = new WeakReference<>(semHorizontalAbsListView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            SemHorizontalAbsListView semHorizontalAbsListView = this.mListView.get();
            if (semHorizontalAbsListView != null) {
                semHorizontalAbsListView.handleMessage(message);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessage(Message message) {
        if (message.what != 1) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.mHoverRecognitionCurrentTime = currentTimeMillis;
        this.mHoverRecognitionDurationTime = (currentTimeMillis - this.mHoverRecognitionStartTime) / 1000;
        if (!this.mIsPenHovered || currentTimeMillis - this.mHoverScrollStartTime >= this.mHoverScrollTimeInterval) {
            if (!this.mIsPenPressed || currentTimeMillis - this.mHoverScrollStartTime >= this.mPenDragScrollTimeInterval) {
                int applyDimension = (int) (TypedValue.applyDimension(1, this.HOVERSCROLL_SPEED, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
                this.mHoverScrollSpeed = applyDimension;
                long j = this.mHoverRecognitionDurationTime;
                if (j == 3) {
                    this.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.1d));
                } else if (j == 4) {
                    this.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.2d));
                } else if (j >= 5) {
                    this.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.3d));
                }
                int i = this.mHoverScrollDirection;
                int i2 = this.mHoverScrollSpeed;
                if (i == 2) {
                    i2 = -i2;
                }
                if ((this.mSemTrackedChild == null && this.mSemCloseChildByRight != null) || (this.mOldHoverScrollDirection != i && this.mIsCloseChildSetted)) {
                    this.mSemTrackedChild = this.mSemCloseChildByRight;
                    this.mSemDistanceFromTrackedChildLeft = this.mSemDistanceFromCloseChildRight;
                    this.mSemTrackedChildPosition = this.mSemCloseChildPositionByRight;
                    this.mOldHoverScrollDirection = i;
                    this.mIsCloseChildSetted = true;
                }
                if (getChildAt(getChildCount() - 1) == null) {
                    return;
                }
                boolean z = false;
                if (this.mIsRTL && i2 < 0 && (this.mFirstPosition + getChildCount() != getCount() || getPaddingLeft() != getChildAt(getChildCount() - 1).getLeft())) {
                    smoothScrollBy(i2, 0);
                    this.mHoverHandler.sendEmptyMessageDelayed(1, this.HOVERSCROLL_DELAY);
                    return;
                }
                if (this.mIsRTL && i2 > 0 && (this.mFirstPosition != 0 || getWidth() - getPaddingRight() != getChildAt(0).getRight())) {
                    smoothScrollBy(i2, 0);
                    this.mHoverHandler.sendEmptyMessageDelayed(1, this.HOVERSCROLL_DELAY);
                    return;
                }
                if (!this.mIsRTL && i2 < 0 && (this.mFirstPosition != 0 || getPaddingLeft() != getChildAt(0).getLeft())) {
                    smoothScrollBy(i2, 0);
                    this.mHoverHandler.sendEmptyMessageDelayed(1, this.HOVERSCROLL_DELAY);
                    return;
                }
                if (!this.mIsRTL && i2 > 0 && (this.mFirstPosition + getChildCount() != getCount() || getWidth() - getPaddingRight() != getChildAt(getChildCount() - 1).getRight())) {
                    smoothScrollBy(i2, 0);
                    this.mHoverHandler.sendEmptyMessageDelayed(1, this.HOVERSCROLL_DELAY);
                    return;
                }
                int overScrollMode = getOverScrollMode();
                if (overScrollMode == 0 || (overScrollMode == 1 && !contentFits())) {
                    z = true;
                }
                if (z && !this.mIsHoverOverscrolled) {
                    int i3 = this.mHoverScrollDirection;
                    if (i3 == 2) {
                        this.mEdgeGlowLeft.setSize(getWidth(), getHeight());
                        this.mEdgeGlowLeft.onPull(0.4f);
                        if (!this.mEdgeGlowRight.isFinished()) {
                            this.mEdgeGlowRight.onRelease();
                        }
                    } else if (i3 == 1) {
                        this.mEdgeGlowRight.setSize(getWidth(), getHeight());
                        this.mEdgeGlowRight.onPull(0.4f);
                        if (!this.mEdgeGlowLeft.isFinished()) {
                            this.mEdgeGlowLeft.onRelease();
                        }
                    }
                    EdgeEffect edgeEffect = this.mEdgeGlowLeft;
                    if (edgeEffect != null && (!edgeEffect.isFinished() || !this.mEdgeGlowRight.isFinished())) {
                        invalidate();
                    }
                    this.mIsHoverOverscrolled = true;
                }
                if (z || this.mIsHoverOverscrolled) {
                    return;
                }
                this.mIsHoverOverscrolled = true;
            }
        }
    }

    private void showPointerIcon(MotionEvent motionEvent, int i) {
        InputDevice device = motionEvent.getDevice();
        if (device != null) {
            device.semSetPointerType(i);
            return;
        }
        Log.e(TAG, "Failed to change PointerIcon to " + i);
    }

    private static void log(String str) {
        Log.d(TAG, str);
    }

    public void semSetMultiFocusEnabled(boolean z) {
        this.mIsMultiFocusEnabled = z;
    }

    public void semSetDragBlockEnabled(boolean z) {
        this.mIsDragBlockEnabled = z;
    }

    public boolean isMultiFocusEnabled() {
        return this.mIsMultiFocusEnabled;
    }

    public void semSetClickableInMultiSelectMode(boolean z) {
        this.mSemIsOnClickEnabled = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addToPressItemListArray(int i, int i2) {
        if (this.mIsMultiFocusEnabled) {
            if (i2 != -1) {
                int i3 = 0;
                if (i < i2) {
                    int i4 = (i2 - i) + 1;
                    while (i3 < i4) {
                        if (this.mSemPressItemListArray.contains(Integer.valueOf(i))) {
                            this.mSemPressItemListArray.remove(Integer.valueOf(i));
                        } else {
                            this.mSemPressItemListArray.add(Integer.valueOf(i));
                        }
                        i++;
                        i3++;
                    }
                } else if (i > i2) {
                    int i5 = (i - i2) + 1;
                    while (i3 < i5) {
                        if (this.mSemPressItemListArray.contains(Integer.valueOf(i))) {
                            this.mSemPressItemListArray.remove(Integer.valueOf(i));
                        } else {
                            this.mSemPressItemListArray.add(Integer.valueOf(i));
                        }
                        i--;
                        i3++;
                    }
                } else if (this.mSemPressItemListArray.contains(Integer.valueOf(i))) {
                    this.mSemPressItemListArray.remove(Integer.valueOf(i));
                } else {
                    this.mSemPressItemListArray.add(Integer.valueOf(i));
                }
            } else if (this.mSemPressItemListArray.contains(Integer.valueOf(i))) {
                this.mSemPressItemListArray.remove(Integer.valueOf(i));
            } else {
                this.mSemPressItemListArray.add(Integer.valueOf(i));
            }
            invalidate();
        }
    }

    public void resetPressItemListArray() {
        ArrayList<Integer> arrayList;
        if (this.mAdapter == null || (arrayList = this.mSemPressItemListArray) == null) {
            return;
        }
        arrayList.clear();
        invalidate();
    }

    @Override // android.widget.AdapterView
    void rememberSyncState() {
        rememberSyncStateHorizontal();
    }
}
