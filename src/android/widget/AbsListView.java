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
import android.content.res.Resources;
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
import android.text.MultiSelection;
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
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.HapticScrollFeedbackProvider;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.RemotableViewMethod;
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
import android.widget.AdapterView;
import android.widget.DifferentialMotionFlingHelper;
import android.widget.Filter;
import android.widget.RemoteViews;
import android.widget.RemoteViewsAdapter;
import com.android.internal.R;
import com.samsung.android.animation.SemSweepListAnimator;
import com.samsung.android.os.SemPerfManager;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public abstract class AbsListView extends AdapterView<ListAdapter> implements TextWatcher, ViewTreeObserver.OnGlobalLayoutListener, Filter.FilterListener, ViewTreeObserver.OnTouchModeChangeListener, RemoteViewsAdapter.RemoteAdapterConnectionCallback {
    private static final String APPWIDGET_CURRENT_POSITION_ACTION = "com.sec.android.app.clockpackage.APPWIDGET_CURRENT_POSITION";
    private static final String APPWIDGET_EXTRA_CURRENT_POSITION = "appwidgetCurrentPosition";
    private static final String APPWIDGET_EXTRA_FIRST_POSITION = "appwidgetFirstPosition";
    private static final String APPWIDGET_FIRST_POSITION_ACTION = "android.widget.ListView.APPWIDGET_FIRST_POSITION";
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
    private static boolean sContentCaptureReportingEnabledByDeviceConfig = false;
    private static DeviceConfig.OnPropertiesChangedListener sDeviceConfigChangeListener;
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
    private float mBottomFadingEdgeStrength;
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
    private boolean mIsForceSelection;
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
    private float mTopFadingEdgeStrength;
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

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDrawableHotspotChanged(float f, float f2) {
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSetPressed(boolean z) {
    }

    abstract void fillGap(boolean z);

    abstract int findMotionRow(int i);

    int getFooterViewsCount() {
        return 0;
    }

    int getHeaderViewsCount() {
        return 0;
    }

    @Override // android.view.View
    protected boolean handleScrollBarDragging(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.ViewGroup
    protected boolean isSemUsingAdapterView() {
        return true;
    }

    protected void layoutChildren() {
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void onRemoteAdapterDisconnected() {
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        return (i & 2) != 0;
    }

    public boolean semHandleGenericMotionEvent(int i) {
        return false;
    }

    public void semSetFluidScrollerEventListener(SemFluidScrollerEventListener semFluidScrollerEventListener) {
    }

    public void semSetFluidScrollerStyle(int i) {
    }

    public void setOverScrollEffectPadding(int i, int i2) {
    }

    abstract void setSelectionInt(int i);

    public void updateCustomEdgeGlow(Drawable drawable, Drawable drawable2) {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<AbsListView> {
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
            SparseArray sparseArray = new SparseArray();
            sparseArray.put(0, "none");
            sparseArray.put(1, "singleChoice");
            sparseArray.put(2, "multipleChoice");
            sparseArray.put(3, "multipleChoiceModal");
            this.mChoiceModeId = propertyMapper.mapIntEnum("choiceMode", 16843051, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mDrawSelectorOnTopId = propertyMapper.mapBoolean("drawSelectorOnTop", 16843004);
            this.mFastScrollEnabledId = propertyMapper.mapBoolean("fastScrollEnabled", 16843302);
            this.mListSelectorId = propertyMapper.mapObject("listSelector", 16843003);
            this.mScrollingCacheId = propertyMapper.mapBoolean("scrollingCache", 16843006);
            this.mSmoothScrollbarId = propertyMapper.mapBoolean("smoothScrollbar", 16843313);
            this.mStackFromBottomId = propertyMapper.mapBoolean("stackFromBottom", 16843005);
            this.mTextFilterEnabledId = propertyMapper.mapBoolean("textFilterEnabled", 16843007);
            SparseArray sparseArray2 = new SparseArray();
            sparseArray2.put(0, "disabled");
            sparseArray2.put(1, "normal");
            sparseArray2.put(2, "alwaysScroll");
            this.mTranscriptModeId = propertyMapper.mapIntEnum("transcriptMode", 16843008, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray2));
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(AbsListView absListView, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readColor(this.mCacheColorHintId, absListView.getCacheColorHint());
            propertyReader.readIntEnum(this.mChoiceModeId, absListView.getChoiceMode());
            propertyReader.readBoolean(this.mDrawSelectorOnTopId, absListView.isDrawSelectorOnTop());
            propertyReader.readBoolean(this.mFastScrollEnabledId, absListView.isFastScrollEnabled());
            propertyReader.readObject(this.mListSelectorId, absListView.getSelector());
            propertyReader.readBoolean(this.mScrollingCacheId, absListView.isScrollingCacheEnabled());
            propertyReader.readBoolean(this.mSmoothScrollbarId, absListView.isSmoothScrollbarEnabled());
            propertyReader.readBoolean(this.mStackFromBottomId, absListView.isStackFromBottom());
            propertyReader.readBoolean(this.mTextFilterEnabledId, absListView.isTextFilterEnabled());
            propertyReader.readIntEnum(this.mTranscriptModeId, absListView.getTranscriptMode());
        }
    }

    private static class DeviceConfigChangeListener implements DeviceConfig.OnPropertiesChangedListener {
        private DeviceConfigChangeListener() {
        }

        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            if (Context.CONTENT_CAPTURE_MANAGER_SERVICE.equals(properties.getNamespace())) {
                for (String str : properties.getKeyset()) {
                    if (ContentCaptureManager.DEVICE_CONFIG_PROPERTY_REPORT_LIST_VIEW_CHILDREN.equals(str)) {
                        AbsListView.sContentCaptureReportingEnabledByDeviceConfig = properties.getBoolean(str, false);
                    }
                }
            }
        }
    }

    private static void setupDeviceConfigProperties() {
        if (sDeviceConfigChangeListener == null) {
            sContentCaptureReportingEnabledByDeviceConfig = DeviceConfig.getBoolean(Context.CONTENT_CAPTURE_MANAGER_SERVICE, ContentCaptureManager.DEVICE_CONFIG_PROPERTY_REPORT_LIST_VIEW_CHILDREN, false);
            sDeviceConfigChangeListener = new DeviceConfigChangeListener();
            DeviceConfig.addOnPropertiesChangedListener(Context.CONTENT_CAPTURE_MANAGER_SERVICE, ActivityThread.currentApplication().getMainExecutor(), sDeviceConfigChangeListener);
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
        this.mBottomFadingEdgeStrength = -1.0f;
        this.mTopFadingEdgeStrength = -1.0f;
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
        this.mSemGoToToFadeOutRunnable = new Runnable() { // from class: android.widget.AbsListView.5
            @Override // java.lang.Runnable
            public void run() {
                AbsListView.this.semPlayGotoToFadeOut();
            }
        };
        this.mSemGoToToFadeInRunnable = new Runnable() { // from class: android.widget.AbsListView.6
            @Override // java.lang.Runnable
            public void run() {
                AbsListView.this.semPlayGotoToFadeIn();
            }
        };
        this.mSemAutoHide = new Runnable() { // from class: android.widget.AbsListView.7
            @Override // java.lang.Runnable
            public void run() throws Resources.NotFoundException {
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
        this.mIsForceSelection = false;
        setupDeviceConfigProperties();
        this.mEdgeGlowBottom = new EdgeEffect(context);
        this.mEdgeGlowTop = new EdgeEffect(context);
        this.mEdgeGlowBottom.semSetHostView(this, true);
        this.mEdgeGlowTop.semSetHostView(this, true);
        initAbsListView();
        this.mOwnerThread = Thread.currentThread();
        setVerticalScrollBarEnabled(true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.styleable.View);
        initializeScrollbarsInternal(typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
    }

    public AbsListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842858);
    }

    public AbsListView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AbsListView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
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
        this.mBottomFadingEdgeStrength = -1.0f;
        this.mTopFadingEdgeStrength = -1.0f;
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
        this.mSemGoToToFadeOutRunnable = new Runnable() { // from class: android.widget.AbsListView.5
            @Override // java.lang.Runnable
            public void run() {
                AbsListView.this.semPlayGotoToFadeOut();
            }
        };
        this.mSemGoToToFadeInRunnable = new Runnable() { // from class: android.widget.AbsListView.6
            @Override // java.lang.Runnable
            public void run() {
                AbsListView.this.semPlayGotoToFadeIn();
            }
        };
        this.mSemAutoHide = new Runnable() { // from class: android.widget.AbsListView.7
            @Override // java.lang.Runnable
            public void run() throws Resources.NotFoundException {
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
        this.mIsForceSelection = false;
        setupDeviceConfigProperties();
        this.mEdgeGlowBottom = new EdgeEffect(context, attributeSet);
        this.mEdgeGlowTop = new EdgeEffect(context, attributeSet);
        this.mEdgeGlowBottom.semSetHostView(this, true);
        this.mEdgeGlowTop.semSetHostView(this, true);
        initAbsListView();
        this.mOwnerThread = Thread.currentThread();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AbsListView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.AbsListView, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        if (drawable != null) {
            setSelector(drawable);
        }
        this.mDrawSelectorOnTop = typedArrayObtainStyledAttributes.getBoolean(1, false);
        setStackFromBottom(typedArrayObtainStyledAttributes.getBoolean(2, false));
        setScrollingCacheEnabled(typedArrayObtainStyledAttributes.getBoolean(3, true));
        setTextFilterEnabled(typedArrayObtainStyledAttributes.getBoolean(4, false));
        setTranscriptMode(typedArrayObtainStyledAttributes.getInt(5, 0));
        setCacheColorHint(typedArrayObtainStyledAttributes.getColor(6, 0));
        setSmoothScrollbarEnabled(typedArrayObtainStyledAttributes.getBoolean(9, true));
        setChoiceMode(typedArrayObtainStyledAttributes.getInt(7, 0));
        setFastScrollEnabled(typedArrayObtainStyledAttributes.getBoolean(8, false));
        setFastScrollStyle(typedArrayObtainStyledAttributes.getResourceId(11, 0));
        setFastScrollAlwaysVisible(typedArrayObtainStyledAttributes.getBoolean(10, false));
        typedArrayObtainStyledAttributes.recycle();
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
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mContext);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mVerticalScrollFactor = viewConfiguration.getScaledVerticalScrollFactor();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mOverscrollDistance = viewConfiguration.getScaledOverscrollDistance();
        this.mOverflingDistance = viewConfiguration.getScaledOverflingDistance();
        this.mDensityScale = getContext().getResources().getDisplayMetrics().density;
        TypedValue typedValue = new TypedValue();
        if (this.mContext.getTheme().resolveAttribute(R.attr.twListMultiSelectBackground, typedValue, true)) {
            this.mMultiFocusImage = this.mContext.getResources().getDrawable(typedValue.resourceId);
        }
        if (this.mContext.getTheme().resolveAttribute(R.attr.twDragBlockImage, typedValue, true)) {
            this.mSemDragBlockImage = this.mContext.getResources().getDrawable(typedValue.resourceId);
        }
        if (this.mContext.getTheme().resolveAttribute(R.attr.semGoToTopStyle, typedValue, true)) {
            this.mSemGoToTopLightImage = this.mContext.getResources().getDrawable(typedValue.resourceId);
        }
        if (sIsSamsungBasicInteraction) {
            this.mSemFillOutPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            this.mSemFillOutPaint.setColor(getResources().getColor(R.color.sem_round_and_bgcolor_dark, null));
        }
    }

    @Override // android.widget.AdapterView
    public void setAdapter(ListAdapter listAdapter) throws Resources.NotFoundException {
        if (listAdapter != null) {
            boolean zHasStableIds = this.mAdapter.hasStableIds();
            this.mAdapterHasStableIds = zHasStableIds;
            if (this.mChoiceMode != 0 && zHasStableIds && this.mCheckedIdStates == null) {
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
        if (!this.mIsMultiFocusEnabled || this.mAdapter == null) {
            return;
        }
        this.mSemPressItemListArray = new ArrayList<>();
        resetPressItemListArray();
        this.mOldAdapterItemCount = this.mAdapter.getCount();
    }

    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    public boolean isItemChecked(int i) {
        SparseBooleanArray sparseBooleanArray;
        if (this.mChoiceMode == 0 || (sparseBooleanArray = this.mCheckStates) == null) {
            return false;
        }
        return sparseBooleanArray.get(i);
    }

    public int getCheckedItemPosition() {
        SparseBooleanArray sparseBooleanArray;
        if (this.mChoiceMode == 1 && (sparseBooleanArray = this.mCheckStates) != null && sparseBooleanArray.size() == 1) {
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

    public void setItemChecked(int i, boolean z) {
        boolean z2;
        int i2 = this.mChoiceMode;
        if (i2 == 0) {
            return;
        }
        if (z && i2 == 3 && this.mChoiceActionMode == null) {
            MultiChoiceModeWrapper multiChoiceModeWrapper = this.mMultiChoiceModeCallback;
            if (multiChoiceModeWrapper == null || !multiChoiceModeWrapper.hasWrappedCallback()) {
                throw new IllegalStateException("AbsListView: attempted to start selection mode for CHOICE_MODE_MULTIPLE_MODAL but no choice mode callback was supplied. Call setMultiChoiceModeListener to set a callback.");
            }
            this.mChoiceActionMode = startActionMode(this.mMultiChoiceModeCallback);
        }
        int i3 = this.mChoiceMode;
        if (i3 == 2 || i3 == 3) {
            boolean z3 = this.mCheckStates.get(i);
            this.mCheckStates.put(i, z);
            if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                if (z) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(i), Integer.valueOf(i));
                } else {
                    this.mCheckedIdStates.delete(this.mAdapter.getItemId(i));
                }
            }
            boolean z4 = z3 != z;
            if (z4) {
                if (z) {
                    this.mCheckedItemCount++;
                } else {
                    this.mCheckedItemCount--;
                }
            }
            if (this.mChoiceActionMode != null) {
                this.mMultiChoiceModeCallback.onItemCheckedStateChanged(this.mChoiceActionMode, i, this.mAdapter.getItemId(i), z);
            }
            z2 = z4;
        } else {
            boolean z5 = this.mCheckedIdStates != null && this.mAdapter.hasStableIds();
            z2 = isItemChecked(i) != z;
            if (z || isItemChecked(i)) {
                this.mCheckStates.clear();
                if (z5) {
                    this.mCheckedIdStates.clear();
                }
            }
            if (z) {
                this.mCheckStates.put(i, true);
                if (z5) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(i), Integer.valueOf(i));
                }
                this.mCheckedItemCount = 1;
            } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                this.mCheckedItemCount = 0;
            }
        }
        if (this.mInLayout || this.mBlockLayoutRequests || !z2) {
            return;
        }
        if (!this.mForcedClick) {
            this.mDataChanged = true;
        }
        rememberSyncState();
        requestLayout();
    }

    @Override // android.widget.AdapterView
    public boolean performItemClick(View view, int i, long j) {
        int i2;
        long j2;
        boolean z;
        int i3 = this.mChoiceMode;
        boolean z2 = false;
        boolean z3 = true;
        if (i3 != 0) {
            if (i3 == 2 || (i3 == 3 && this.mChoiceActionMode != null)) {
                boolean z4 = this.mCheckStates.get(i, false);
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
            } else if (i3 == 1) {
                if (!this.mCheckStates.get(i, false)) {
                    this.mCheckStates.clear();
                    this.mCheckStates.put(i, true);
                    if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                        this.mCheckedIdStates.clear();
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(i), Integer.valueOf(i));
                    }
                    this.mCheckedItemCount = 1;
                } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                    this.mCheckedItemCount = 0;
                }
                i2 = i;
                j2 = j;
                z = true;
                z2 = true;
            } else {
                i2 = i;
                j2 = j;
                z = true;
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

    public int getChoiceMode() {
        return this.mChoiceMode;
    }

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

    public void setMultiChoiceModeListener(MultiChoiceModeListener multiChoiceModeListener) {
        if (this.mMultiChoiceModeCallback == null) {
            this.mMultiChoiceModeCallback = new MultiChoiceModeWrapper();
        }
        this.mMultiChoiceModeCallback.setWrapped(multiChoiceModeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean contentFits() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return true;
        }
        return childCount == this.mItemCount && getChildAt(0).getTop() >= this.mListPadding.top && getChildAt(childCount - 1).getBottom() <= getHeight() - this.mListPadding.bottom;
    }

    public void setFastScrollEnabled(final boolean z) {
        if (this.mFastScrollEnabled != z) {
            this.mFastScrollEnabled = z;
            if (isOwnerThread()) {
                if (this.mSemFastScrollCustomEffectEnabled) {
                    semSetFastScrollEnabledUiThread(z);
                    return;
                } else {
                    setFastScrollerEnabledUiThread(z);
                    return;
                }
            }
            post(new Runnable() { // from class: android.widget.AbsListView.1
                @Override // java.lang.Runnable
                public void run() {
                    if (AbsListView.this.mSemFastScrollCustomEffectEnabled) {
                        AbsListView.this.semSetFastScrollEnabledUiThread(z);
                    } else {
                        AbsListView.this.setFastScrollerEnabledUiThread(z);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFastScrollerEnabledUiThread(boolean z) {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.setEnabled(z);
        } else if (z) {
            FastScroller fastScroller2 = new FastScroller(this, this.mFastScrollStyle);
            this.mFastScroll = fastScroller2;
            fastScroller2.setEnabled(true);
            this.mSemFastScroll = null;
        }
        resolvePadding();
        FastScroller fastScroller3 = this.mFastScroll;
        if (fastScroller3 != null) {
            fastScroller3.updateLayout();
        }
    }

    public void setFastScrollStyle(int i) {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller == null) {
            this.mFastScrollStyle = i;
        } else {
            fastScroller.setStyle(i);
        }
    }

    public void setFastScrollAlwaysVisible(final boolean z) {
        if (this.mFastScrollAlwaysVisible != z) {
            if (z && !this.mFastScrollEnabled) {
                setFastScrollEnabled(true);
            }
            this.mFastScrollAlwaysVisible = z;
            if (isOwnerThread()) {
                setFastScrollerAlwaysVisibleUiThread(z);
            } else {
                post(new Runnable() { // from class: android.widget.AbsListView.2
                    @Override // java.lang.Runnable
                    public void run() {
                        AbsListView.this.setFastScrollerAlwaysVisibleUiThread(z);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFastScrollerAlwaysVisibleUiThread(boolean z) {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.setAlwaysShow(z);
        }
    }

    private boolean isOwnerThread() {
        return this.mOwnerThread == Thread.currentThread();
    }

    public boolean isFastScrollAlwaysVisible() {
        FastScroller fastScroller = this.mFastScroll;
        return fastScroller == null ? this.mFastScrollEnabled && this.mFastScrollAlwaysVisible : fastScroller.isEnabled() && this.mFastScroll.isAlwaysShowEnabled();
    }

    @Override // android.view.View
    public int getVerticalScrollbarWidth() {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null && fastScroller.isEnabled()) {
            return Math.max(super.getVerticalScrollbarWidth(), this.mFastScroll.getWidth());
        }
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller != null && semFastScroller.isEnabled()) {
            return Math.max(super.getVerticalScrollbarWidth(), this.mSemFastScroll.getWidth());
        }
        return super.getVerticalScrollbarWidth();
    }

    @ViewDebug.ExportedProperty
    public boolean isFastScrollEnabled() {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller == null) {
            return this.mFastScrollEnabled;
        }
        return fastScroller.isEnabled();
    }

    @Override // android.view.View
    public void setVerticalScrollbarPosition(int i) {
        super.setVerticalScrollbarPosition(i);
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.setScrollbarPosition(i);
            return;
        }
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller != null) {
            semFastScroller.setScrollbarPosition(i);
        }
    }

    @Override // android.view.View
    public void setScrollBarStyle(int i) {
        super.setScrollBarStyle(i);
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.setScrollBarStyle(i);
            return;
        }
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller != null) {
            semFastScroller.setScrollBarStyle(i);
        }
    }

    @Override // android.view.View
    protected boolean isVerticalScrollBarHidden() {
        return isFastScrollEnabled() || semIsFastScrollEnabled();
    }

    public void setSmoothScrollbarEnabled(boolean z) {
        this.mSmoothScrollbarEnabled = z;
    }

    @ViewDebug.ExportedProperty
    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
        invokeOnItemScrollListener();
    }

    void invokeOnItemScrollListener() {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.onScroll(this.mFirstPosition, getChildCount(), this.mItemCount);
        } else {
            SemFastScroller semFastScroller = this.mSemFastScroll;
            if (semFastScroller != null) {
                semFastScroller.onScroll(this.mFirstPosition, getChildCount(), this.mItemCount);
            }
        }
        OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScroll(this, this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        onScrollChanged(0, 0, 0, 0);
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return AbsListView.class.getName();
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (isEnabled()) {
            if (canScrollUp()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
                accessibilityNodeInfo.setScrollable(true);
            }
            if (canScrollDown()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
                accessibilityNodeInfo.setScrollable(true);
            }
        }
        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
        accessibilityNodeInfo.setClickable(false);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.SEM_ACTION_AUTOSCROLL_ON);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.SEM_ACTION_AUTOSCROLL_OFF);
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
    public boolean performAccessibilityActionInternal(int i, Bundle bundle) throws Resources.NotFoundException {
        int i2;
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (bundle != null) {
            this.mAutoscrollDurationGap = 1932 / (bundle.getInt("auto_scroll_speed_level_count", 15) - 1);
            i2 = bundle.getInt("auto_scroll_speed_level", 8) - 1;
        } else {
            i2 = 7;
        }
        if (i != 4096) {
            if (i != 8192) {
                if (i == 4194304) {
                    Log.d(TAG, "case SEM_ACTION_AUTOSCROLL_ON, canScrollDown = " + canScrollDown());
                    if (!isEnabled() || !canScrollDown()) {
                        return false;
                    }
                    int i3 = 2032 - (this.mAutoscrollDurationGap * i2);
                    this.mAutoscrollDuration = i3;
                    autoScrollWithDuration(i3);
                    return true;
                }
                if (i == 8388608) {
                    Log.d(TAG, "SEM_ACTION_AUTOSCROLL_OFF");
                    smoothScrollBy(0, 0);
                    AbsPositionScroller absPositionScroller = this.mPositionScroller;
                    if (absPositionScroller != null) {
                        absPositionScroller.stop();
                    }
                    return true;
                }
                if (i != 16908344) {
                    if (i != 16908346) {
                        if (i == 67108864) {
                            Log.d(TAG, "SEM_ACTION_AUTOSCROLL_TOP");
                            if (!canScrollUp()) {
                                return false;
                            }
                            smoothScrollToPositionFromTop(0, 0, 0);
                            return true;
                        }
                        if (i == 268435456) {
                            Log.d(TAG, "SEM_ACTION_AUTOSCROLL_SPEED_UP, current duration = " + this.mAutoscrollDuration);
                            if (!canScrollDown()) {
                                return false;
                            }
                            int i4 = this.mAutoscrollDuration;
                            if (i4 > 100) {
                                this.mAutoscrollDuration = i4 - this.mAutoscrollDurationGap;
                            }
                            autoScrollWithDuration(this.mAutoscrollDuration);
                            return true;
                        }
                        if (i != 536870912) {
                            return false;
                        }
                        Log.d(TAG, "SEM_ACTION_AUTOSCROLL_SPEED_DOWN, current duration = " + this.mAutoscrollDuration);
                        if (!canScrollDown()) {
                            return false;
                        }
                        int i5 = this.mAutoscrollDuration;
                        if (i5 < 2032) {
                            this.mAutoscrollDuration = i5 + this.mAutoscrollDurationGap;
                        }
                        autoScrollWithDuration(this.mAutoscrollDuration);
                        return true;
                    }
                }
            }
            if (!isEnabled() || !canScrollUp()) {
                return false;
            }
            smoothScrollBy(-((getHeight() - this.mListPadding.top) - this.mListPadding.bottom), 200);
            semSendBroadcastPosition(this.mFocusedPos - 1, 1);
            if (this.mAppWidgetIndicator) {
                semInvalidateIndicator(this.mFocusedPos - 1);
            }
            return true;
        }
        if (!isEnabled() || (!(canScrollDown() && getLastVisiblePosition() == getCount() - 1) && getLastVisiblePosition() >= getCount() - 1)) {
            return false;
        }
        smoothScrollBy((getHeight() - this.mListPadding.top) - this.mListPadding.bottom, 200);
        semSendBroadcastPosition(this.mFocusedPos + 1, 1);
        if (this.mAppWidgetIndicator) {
            semInvalidateIndicator(this.mFocusedPos + 1);
        }
        return true;
    }

    private void autoScrollWithDuration(int i) {
        int firstVisiblePosition = getFirstVisiblePosition();
        View childAt = getChildAt(firstVisiblePosition);
        View childAt2 = getChildAt(getLastVisiblePosition());
        int height = childAt != null ? childAt.getHeight() : 0;
        if (childAt2 != null) {
            height += childAt2.getHeight();
        }
        int count = getCount();
        smoothScrollToPositionFromTop(count - 1, ((height * firstVisiblePosition) / 2) * (-1), i * (count - firstVisiblePosition));
    }

    @ViewDebug.ExportedProperty
    public boolean isScrollingCacheEnabled() {
        return this.mScrollingCacheEnabled;
    }

    public void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled && !z) {
            clearScrollingCache();
        }
        this.mScrollingCacheEnabled = z;
    }

    public void setTextFilterEnabled(boolean z) {
        this.mTextFilterEnabled = z;
    }

    @ViewDebug.ExportedProperty
    public boolean isTextFilterEnabled() {
        return this.mTextFilterEnabled;
    }

    @Override // android.view.View
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
    public boolean isStackFromBottom() {
        return this.mStackFromBottom;
    }

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
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.AbsListView.SavedState.1
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
        int height;
        boolean inActionMode;
        int position;
        long selectedId;
        int viewTop;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.selectedId = parcel.readLong();
            this.firstId = parcel.readLong();
            this.viewTop = parcel.readInt();
            this.position = parcel.readInt();
            this.height = parcel.readInt();
            this.filter = parcel.readString();
            this.inActionMode = parcel.readByte() != 0;
            this.checkedItemCount = parcel.readInt();
            this.checkState = parcel.readSparseBooleanArray();
            int i = parcel.readInt();
            if (i > 0) {
                this.checkIdState = new LongSparseArray<>();
                for (int i2 = 0; i2 < i; i2++) {
                    this.checkIdState.put(parcel.readLong(), Integer.valueOf(parcel.readInt()));
                }
            }
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
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
            LongSparseArray<Integer> longSparseArray = this.checkIdState;
            int size = longSparseArray != null ? longSparseArray.size() : 0;
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeLong(this.checkIdState.keyAt(i2));
                parcel.writeInt(this.checkIdState.valueAt(i2).intValue());
            }
        }

        public String toString() {
            return "AbsListView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " firstId=" + this.firstId + " viewTop=" + this.viewTop + " position=" + this.position + " height=" + this.height + " filter=" + this.filter + " checkState=" + this.checkState + "}";
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        EditText editText;
        Editable text;
        dismissPopup();
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState savedState2 = this.mPendingSync;
        if (savedState2 != null) {
            savedState.selectedId = savedState2.selectedId;
            savedState.firstId = this.mPendingSync.firstId;
            savedState.viewTop = this.mPendingSync.viewTop;
            savedState.position = this.mPendingSync.position;
            savedState.height = this.mPendingSync.height;
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
        savedState.height = getHeight();
        if (selectedItemId >= 0) {
            savedState.viewTop = this.mSelectedTop;
            savedState.position = getSelectedItemPosition();
            savedState.firstId = -1L;
        } else if (z && this.mFirstPosition > 0) {
            savedState.viewTop = getChildAt(0).getTop();
            int i = this.mFirstPosition;
            if (i >= this.mItemCount) {
                i = this.mItemCount - 1;
            }
            savedState.position = i;
            savedState.firstId = this.mAdapter.getItemId(i);
        } else {
            savedState.viewTop = 0;
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
            savedState.checkState = sparseBooleanArray.m5537clone();
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
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        MultiChoiceModeWrapper multiChoiceModeWrapper;
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = savedState.height;
        if (savedState.selectedId >= 0) {
            this.mNeedSync = true;
            this.mPendingSync = savedState;
            this.mSyncRowId = savedState.selectedId;
            this.mSyncPosition = savedState.position;
            this.mSpecificTop = savedState.viewTop;
            this.mSyncMode = 0;
        } else if (savedState.firstId >= 0) {
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectorPosition = -1;
            this.mNeedSync = true;
            this.mPendingSync = savedState;
            this.mSyncRowId = savedState.firstId;
            this.mSyncPosition = savedState.position;
            this.mSpecificTop = savedState.viewTop;
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

    public CharSequence getTextFilter() {
        EditText editText;
        if (!this.mTextFilterEnabled || (editText = this.mTextFilter) == null) {
            return null;
        }
        return editText.getText();
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) throws Resources.NotFoundException {
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

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mBlockLayoutRequests || this.mInLayout) {
            return;
        }
        super.requestLayout();
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
        int childCount = getChildCount();
        if (childCount <= 0) {
            return 0;
        }
        if (!this.mSmoothScrollbarEnabled) {
            return 1;
        }
        int i = childCount * 100;
        View childAt = getChildAt(0);
        int top = childAt.getTop();
        int height = childAt.getHeight();
        if (height > 0) {
            i += (top * 100) / height;
        }
        View childAt2 = getChildAt(childCount - 1);
        int bottom = childAt2.getBottom();
        int height2 = childAt2.getHeight();
        return height2 > 0 ? i - (((bottom - getHeight()) * 100) / height2) : i;
    }

    @Override // android.view.View
    protected int computeVerticalScrollOffset() {
        int i = this.mFirstPosition;
        int childCount = getChildCount();
        int i2 = 0;
        if (i >= 0 && childCount > 0) {
            if (this.mSmoothScrollbarEnabled) {
                View childAt = getChildAt(0);
                int top = childAt.getTop();
                int height = childAt.getHeight();
                if (height > 0) {
                    return Math.max(((i * 100) - ((top * 100) / height)) + ((int) ((this.mScrollY / getHeight()) * this.mItemCount * 100.0f)), 0);
                }
            } else {
                int i3 = this.mItemCount;
                if (i != 0) {
                    i2 = i + childCount == i3 ? i3 : (childCount / 2) + i;
                }
                return (int) (i + (childCount * (i2 / i3)));
            }
        }
        return 0;
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        if (this.mSmoothScrollbarEnabled) {
            int iMax = Math.max(this.mItemCount * 100, 0);
            return this.mScrollY != 0 ? iMax + Math.abs((int) ((this.mScrollY / getHeight()) * this.mItemCount * 100.0f)) : iMax;
        }
        return this.mItemCount;
    }

    @Override // android.view.View
    protected float getTopFadingEdgeStrength() {
        float f = this.mTopFadingEdgeStrength;
        if (f != -1.0f) {
            return f;
        }
        int childCount = getChildCount();
        float topFadingEdgeStrength = super.getTopFadingEdgeStrength();
        if (childCount != 0) {
            if (this.mFirstPosition > 0) {
                return 1.0f;
            }
            int top = getChildAt(0).getTop();
            float verticalFadingEdgeLength = getVerticalFadingEdgeLength();
            if (top < this.mPaddingTop) {
                return (-(top - this.mPaddingTop)) / verticalFadingEdgeLength;
            }
        }
        return topFadingEdgeStrength;
    }

    @Override // android.view.View
    protected float getBottomFadingEdgeStrength() {
        float f = this.mBottomFadingEdgeStrength;
        if (f != -1.0f) {
            return f;
        }
        int childCount = getChildCount();
        float bottomFadingEdgeStrength = super.getBottomFadingEdgeStrength();
        if (childCount != 0) {
            if ((this.mFirstPosition + childCount) - 1 < this.mItemCount - 1) {
                return 1.0f;
            }
            int bottom = getChildAt(childCount - 1).getBottom();
            int height = getHeight();
            float verticalFadingEdgeLength = getVerticalFadingEdgeLength();
            if (bottom > height - this.mPaddingBottom) {
                return ((bottom - height) + this.mPaddingBottom) / verticalFadingEdgeLength;
            }
        }
        return bottomFadingEdgeStrength;
    }

    @Override // android.view.View
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
            int height = getHeight() - getPaddingBottom();
            View childAt = getChildAt(childCount - 1);
            this.mForceTranscriptScroll = this.mFirstPosition + childCount >= this.mLastHandledItemCount && (childAt != null ? childAt.getBottom() : height) <= height;
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
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
        if (z) {
            Log.d(TAG, " in onLayout changed ");
            this.mSemSizeChnage = true;
            semSetupGoToTop(-1);
            semAutoHide(1);
        }
        this.mOverscrollMax = (i4 - i2) / 3;
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.onItemCountChanged(getChildCount(), this.mItemCount);
        } else {
            SemFastScroller semFastScroller = this.mSemFastScroll;
            if (semFastScroller != null) {
                semFastScroller.onItemCountChanged(getChildCount(), this.mItemCount);
                if (isLayoutRequested()) {
                    this.mInLayout = true;
                    layoutChildren();
                    this.mInLayout = false;
                }
            }
        }
        this.mInLayout = false;
    }

    @Override // android.view.View
    protected boolean setFrame(int i, int i2, int i3, int i4) {
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
        View view = this.mScrollUp;
        if (view != null) {
            view.setVisibility(canScrollUp() ? 0 : 4);
        }
        View view2 = this.mScrollDown;
        if (view2 != null) {
            view2.setVisibility(canScrollDown() ? 0 : 4);
        }
    }

    private boolean canScrollUp() {
        boolean z = this.mFirstPosition > 0;
        return (z || getChildCount() <= 0) ? z : getChildAt(0).getTop() < this.mListPadding.top;
    }

    private boolean canScrollDown() {
        int childCount = getChildCount();
        boolean z = this.mFirstPosition + childCount < this.mItemCount;
        return (z || childCount <= 0) ? z : getChildAt(childCount - 1).getBottom() > this.mBottom - this.mListPadding.bottom;
    }

    @Override // android.widget.AdapterView
    @ViewDebug.ExportedProperty
    public View getSelectedView() {
        if (this.mItemCount <= 0 || this.mSelectedPosition < 0) {
            return null;
        }
        return getChildAt(this.mSelectedPosition - this.mFirstPosition);
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

    View obtainView(int i, boolean[] zArr) {
        View view;
        Trace.traceBegin(8L, "obtainView");
        zArr[0] = false;
        View transientStateView = this.mRecycler.getTransientStateView(i);
        if (transientStateView != null) {
            if (((LayoutParams) transientStateView.getLayoutParams()).viewType == this.mAdapter.getItemViewType(i) && (view = this.mAdapter.getView(i, transientStateView, this)) != transientStateView) {
                setItemViewLayoutParams(view, i);
                this.mRecycler.addScrapView(view, i);
            }
            zArr[0] = true;
            transientStateView.dispatchFinishTemporaryDetach();
            return transientStateView;
        }
        View scrapView = this.mAdapter.getItemViewType(i) != -2 ? this.mRecycler.getScrapView(i) : null;
        View view2 = this.mAdapter.getView(i, scrapView, this);
        if (scrapView != null) {
            if (view2 != scrapView) {
                this.mRecycler.addScrapView(scrapView, i);
            } else if (view2.isTemporarilyDetached()) {
                zArr[0] = true;
                view2.dispatchFinishTemporaryDetach();
            }
        }
        if (view2 == null) {
            Log.d(TAG, " try again to check child on obtainview");
            view2 = this.mAdapter.getView(i, null, this);
            if (view2 == null) {
                Log.d(TAG, " child is null again");
                Log.d(TAG, " position = " + i);
                Log.d(TAG, " mAdapter =" + this.mAdapter);
                Log.d(TAG, " getChildCount = " + getChildCount());
                Log.d(TAG, " mAdapter.getCount = " + this.mAdapter.getCount());
                Log.d(TAG, " mItemCount = " + this.mItemCount);
                Log.d(TAG, " mOldItemCount = " + this.mOldItemCount);
                if (this.mAdapter instanceof HeaderViewListAdapter) {
                    Log.d(TAG, "HeaderCount = " + ((HeaderViewListAdapter) this.mAdapter).getHeadersCount());
                    Log.d(TAG, "FooterCount = " + ((HeaderViewListAdapter) this.mAdapter).getFootersCount());
                }
                return null;
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
        if (this.mAccessibilityDelegate == null) {
            this.mAccessibilityDelegate = new ListItemAccessibilityDelegate();
        }
        if (view2.getAccessibilityDelegate() == null) {
            view2.setAccessibilityDelegate(this.mAccessibilityDelegate);
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
        layoutParams.isEnabled = this.mAdapter.isEnabled(i);
        if (layoutParams != layoutParams2) {
            view.setLayoutParams(layoutParams);
        }
    }

    class ListItemAccessibilityDelegate extends View.AccessibilityDelegate {
        ListItemAccessibilityDelegate() {
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            AbsListView.this.onInitializeAccessibilityNodeInfoForItem(view, AbsListView.this.getPositionForView(view), accessibilityNodeInfo);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            int positionForView = AbsListView.this.getPositionForView(view);
            if (positionForView == -1 || AbsListView.this.mAdapter == null || positionForView >= AbsListView.this.mAdapter.getCount()) {
                return false;
            }
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            boolean z = layoutParams instanceof LayoutParams ? ((LayoutParams) layoutParams).isEnabled : false;
            if (AbsListView.this.isEnabled() && z) {
                if (i != 4) {
                    if (i == 8) {
                        if (AbsListView.this.getSelectedItemPosition() != positionForView) {
                            return false;
                        }
                        AbsListView.this.setSelection(-1);
                        return true;
                    }
                    if (i == 16) {
                        if (AbsListView.this.isItemClickable(view)) {
                            return AbsListView.this.performItemClick(view, positionForView, AbsListView.this.getItemIdAtPosition(positionForView));
                        }
                        return false;
                    }
                    if (i == 32 && AbsListView.this.isLongClickable()) {
                        return AbsListView.this.performLongPress(view, positionForView, AbsListView.this.getItemIdAtPosition(positionForView));
                    }
                    return false;
                }
                if (AbsListView.this.getSelectedItemPosition() != positionForView) {
                    AbsListView.this.setSelection(positionForView);
                    return true;
                }
            }
            return false;
        }
    }

    public void onInitializeAccessibilityNodeInfoForItem(View view, int i, AccessibilityNodeInfo accessibilityNodeInfo) {
        if (i == -1) {
            return;
        }
        boolean zIsEnabled = isEnabled();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            zIsEnabled &= ((LayoutParams) layoutParams).isEnabled;
        }
        if (i == getSelectedItemPosition()) {
            accessibilityNodeInfo.setSelected(true);
            addAccessibilityActionIfEnabled(accessibilityNodeInfo, zIsEnabled, AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_SELECTION);
        } else {
            addAccessibilityActionIfEnabled(accessibilityNodeInfo, zIsEnabled, AccessibilityNodeInfo.AccessibilityAction.ACTION_SELECT);
        }
        if (isItemClickable(view)) {
            addAccessibilityActionIfEnabled(accessibilityNodeInfo, zIsEnabled, AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            accessibilityNodeInfo.setClickable(zIsEnabled);
        }
        if (isLongClickable()) {
            addAccessibilityActionIfEnabled(accessibilityNodeInfo, zIsEnabled, AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
            accessibilityNodeInfo.setLongClickable(zIsEnabled);
        }
    }

    private void addAccessibilityActionIfEnabled(AccessibilityNodeInfo accessibilityNodeInfo, boolean z, AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
        if (z) {
            accessibilityNodeInfo.addAction(accessibilityAction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isItemClickable(View view) {
        return !view.hasExplicitFocusable();
    }

    void positionSelectorLikeTouch(int i, View view, float f, float f2) {
        positionSelector(i, view, true, f, f2);
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

    /* JADX WARN: Multi-variable type inference failed */
    private void positionSelector(int i, View view, boolean z, float f, float f2) {
        boolean z2 = i != this.mSelectorPosition;
        if (i != -1) {
            this.mSelectorPosition = i;
        }
        if (this.mAppWidgetInnerFocus) {
            this.mNextClickable = null;
            this.mClickableViewStates.clear();
            if (this.mSelectorPosition == -1 && view != 0) {
                this.mSelectorPosition = getPositionForView(view);
            }
        }
        Rect rect = this.mSelectorRect;
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        if (view instanceof SelectionBoundsAdjuster) {
            ((SelectionBoundsAdjuster) view).adjustListItemSelectionBounds(rect);
        }
        if ((this instanceof SemExpandableListView) && (view instanceof FrameLayout)) {
            FrameLayout frameLayout = (FrameLayout) view;
            if (frameLayout.getChildCount() > 0) {
                KeyEvent.Callback childAt = frameLayout.getChildAt(0);
                if (childAt instanceof SelectionBoundsAdjuster) {
                    ((SelectionBoundsAdjuster) childAt).adjustListItemSelectionBounds(rect);
                }
            }
        }
        rect.left -= this.mSelectionLeftPadding;
        rect.top -= this.mSelectionTopPadding;
        rect.right += this.mSelectionRightPadding;
        rect.bottom += this.mSelectionBottomPadding - view.mExtraPaddingBottomForPreference;
        boolean zIsEnabled = view.isEnabled();
        if (this.mIsChildViewEnabled != zIsEnabled) {
            this.mIsChildViewEnabled = zIsEnabled;
        }
        Drawable drawable = this.mSelector;
        if (drawable != null) {
            if (z2 && !shouldShowSelector()) {
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
    }

    public boolean isSelectedChildViewEnabled() {
        return this.mIsChildViewEnabled;
    }

    public void setSelectedChildViewEnabled(boolean z) {
        this.mIsChildViewEnabled = z;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        int iSave;
        boolean z = (this.mGroupFlags & 34) == 34;
        if (z) {
            iSave = canvas.save();
            int i = this.mScrollX;
            int i2 = this.mScrollY;
            canvas.clipRect(this.mPaddingLeft + i, this.mPaddingTop + i2, ((i + this.mRight) - this.mLeft) - this.mPaddingRight, ((i2 + this.mBottom) - this.mTop) - this.mPaddingBottom);
            this.mGroupFlags &= -35;
        } else {
            iSave = 0;
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
            canvas.restoreToCount(iSave);
            this.mGroupFlags |= 34;
        }
        if (!this.mIsDragBlockEnabled || this.mIsLongPressMultiSelection) {
            return;
        }
        if (this.mSemDragBlockLeft == 0 && this.mSemDragBlockTop == 0) {
            return;
        }
        int firstVisiblePosition = getFirstVisiblePosition();
        int lastVisiblePosition = getLastVisiblePosition();
        int i3 = this.mSemTrackedChildPosition;
        if (i3 >= firstVisiblePosition && i3 <= lastVisiblePosition) {
            View childAt = getChildAt(i3 - getFirstVisiblePosition());
            this.mSemTrackedChild = childAt;
            this.mSemDragStartY = (childAt != null ? childAt.getTop() : 0) + this.mSemDistanceFromTrackedChildTop;
        }
        this.mSemDragBlockTop = Math.min(this.mSemDragStartY, this.mSemDragEndY);
        int iMax = Math.max(this.mSemDragEndY, this.mSemDragStartY);
        this.mSemDragBlockBottom = iMax;
        this.mSemDragBlockRect.set(this.mSemDragBlockLeft, this.mSemDragBlockTop, this.mSemDragBlockRight, iMax);
        this.mSemDragBlockImage.setBounds(this.mSemDragBlockRect);
        this.mSemDragBlockImage.draw(canvas);
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
    protected void internalSetPadding(int i, int i2, int i3, int i4) {
        super.internalSetPadding(i, i2, i3, i4);
        if (isLayoutRequested()) {
            handleBoundsChange();
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        handleBoundsChange();
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.onSizeChanged(i, i2, i3, i4);
            return;
        }
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller != null) {
            semFastScroller.onSizeChanged(i, i2, i3, i4);
        }
    }

    void handleBoundsChange() {
        int childCount;
        if (!this.mInLayout && (childCount = getChildCount()) > 0) {
            this.mDataChanged = true;
            rememberSyncState();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams == null || layoutParams.width < 1 || layoutParams.height < 1) {
                    childAt.forceLayout();
                }
            }
        }
    }

    boolean touchModeDrawsInPressedState() {
        int i = this.mTouchMode;
        return i == 1 || i == 2;
    }

    boolean shouldShowSelector() {
        if (!isFocused() || isInTouchMode()) {
            return touchModeDrawsInPressedState() && isPressed();
        }
        return true;
    }

    private void drawSelector(Canvas canvas) {
        if (shouldDrawSelector()) {
            Drawable drawable = this.mSelector;
            drawable.setBounds(this.mSelectorRect);
            drawable.draw(canvas);
        }
        if (this.mIsMultiFocusEnabled) {
            Rect rect = new Rect();
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

    public final boolean shouldDrawSelector() {
        return !this.mSelectorRect.isEmpty();
    }

    public void setDrawSelectorOnTop(boolean z) {
        this.mDrawSelectorOnTop = z;
    }

    public boolean isDrawSelectorOnTop() {
        return this.mDrawSelectorOnTop;
    }

    public void setSelector(int i) {
        setSelector(getContext().getDrawable(i));
    }

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

    public Drawable getSelector() {
        return this.mSelector;
    }

    void keyPressed() {
        View view;
        if (isEnabled() && isClickable()) {
            Drawable drawable = this.mSelector;
            Rect rect = this.mSelectorRect;
            if (drawable != null) {
                if ((isFocused() || touchModeDrawsInPressedState()) && !rect.isEmpty()) {
                    View childAt = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                    if (childAt != null && this.mNextClickable == null) {
                        if (childAt.hasExplicitFocusable()) {
                            return;
                        } else {
                            childAt.setPressed(true);
                        }
                    } else if (this.mAppWidgetInnerFocus && (view = this.mNextClickable) != null) {
                        view.setPressed(true);
                    }
                    setPressed(true);
                    boolean zIsLongClickable = isLongClickable();
                    Drawable current = drawable.getCurrent();
                    if (current != null && (current instanceof TransitionDrawable)) {
                        if (zIsLongClickable) {
                            ((TransitionDrawable) current).startTransition(ViewConfiguration.getLongPressTimeout());
                        } else {
                            ((TransitionDrawable) current).resetTransition();
                        }
                    }
                    Rect rect2 = this.mSelectorRect;
                    this.mSelector.setHotspot(rect2.exactCenterX(), rect2.exactCenterY());
                    if (!zIsLongClickable || this.mDataChanged) {
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

    public void setScrollIndicators(View view, View view2) {
        this.mScrollUp = view;
        this.mScrollDown = view2;
    }

    void updateSelectorState() {
        Drawable drawable = this.mSelector;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        if (shouldShowSelector()) {
            if (drawable.setState(getDrawableStateForSelector())) {
                invalidateDrawable(drawable);
                return;
            }
            return;
        }
        drawable.setState(StateSet.NOTHING);
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
        int i = ENABLED_STATE_SET[0];
        int[] iArrOnCreateDrawableState = onCreateDrawableState(1);
        int length = iArrOnCreateDrawableState.length - 1;
        while (true) {
            if (length < 0) {
                length = -1;
                break;
            }
            if (iArrOnCreateDrawableState[length] == i) {
                break;
            }
            length--;
        }
        if (length >= 0) {
            System.arraycopy(iArrOnCreateDrawableState, length + 1, iArrOnCreateDrawableState, length, (iArrOnCreateDrawableState.length - length) - 1);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable drawable) {
        return this.mSelector == drawable || super.verifyDrawable(drawable) || this.mSemGoToTopImage == drawable;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mSelector;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        SemFastScroller semFastScroller;
        FastScroller fastScroller;
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
        if (isLayoutRtl() && (fastScroller = this.mFastScroll) != null) {
            fastScroller.setScrollbarPosition(getVerticalScrollbarPosition());
        } else {
            if (!isLayoutRtl() || (semFastScroller = this.mSemFastScroll) == null) {
                return;
            }
            semFastScroller.setScrollbarPosition(getVerticalScrollbarPosition());
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
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
    public void onWindowFocusChanged(boolean z) throws Resources.NotFoundException {
        super.onWindowFocusChanged(z);
        int i = !isInTouchMode() ? 1 : 0;
        if (!z) {
            setChildrenDrawingCacheEnabled(false);
            FlingRunnable flingRunnable = this.mFlingRunnable;
            if (flingRunnable != null) {
                removeCallbacks(flingRunnable);
                this.mFlingRunnable.mSuppressIdleStateChangeCall = false;
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
        if (!z) {
            releaseAllBoosters();
        }
        semAutoHide(1);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.setScrollbarPosition(getVerticalScrollbarPosition());
            return;
        }
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller != null) {
            semFastScroller.setScrollbarPosition(getVerticalScrollbarPosition());
        }
    }

    ContextMenu.ContextMenuInfo createContextMenuInfo(View view, int i, long j) {
        return new AdapterView.AdapterContextMenuInfo(view, i, j);
    }

    @Override // android.view.View
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
        CheckForDoublePenClick checkForDoublePenClick = this.mPendingCheckForDoublePenClick;
        if (checkForDoublePenClick != null) {
            removeCallbacks(checkForDoublePenClick);
        }
    }

    private class WindowRunnnable {
        private int mOriginalAttachCount;

        private WindowRunnnable() {
        }

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
            if (!AbsListView.this.mDataChanged || AbsListView.this.mForcedClick) {
                ListAdapter listAdapter = AbsListView.this.mAdapter;
                int i = this.mClickMotionPosition;
                if (listAdapter == null || AbsListView.this.mItemCount <= 0 || i == -1 || i >= listAdapter.getCount() || !sameWindow() || !listAdapter.isEnabled(i)) {
                    return;
                }
                AbsListView absListView = AbsListView.this;
                View childAt = absListView.getChildAt(i - absListView.mFirstPosition);
                if (childAt != null) {
                    AbsListView.this.performItemClick(childAt, i, listAdapter.getItemId(i));
                    if ((AbsListView.this.mIsShiftkeyPressed || AbsListView.this.mIsCtrlkeyPressed) && AbsListView.this.mAdapter != null) {
                        if (AbsListView.this.mIsCtrlkeyPressed) {
                            AbsListView.this.addToPressItemListArray(i, -1);
                            return;
                        }
                        if (AbsListView.this.mIsShiftkeyPressed) {
                            AbsListView.this.resetPressItemListArray();
                            if (AbsListView.this.mFirstPressedPoint == -1) {
                                AbsListView.this.addToPressItemListArray(i, -1);
                                AbsListView.this.mFirstPressedPoint = i;
                            } else {
                                AbsListView absListView2 = AbsListView.this;
                                absListView2.addToPressItemListArray(absListView2.mFirstPressedPoint, i);
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
        public void setCoords(float f, float f2) {
            this.mX = f;
            this.mY = f2;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0046  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            boolean zPerformLongPress;
            int i = AbsListView.this.mMotionPosition;
            AbsListView absListView = AbsListView.this;
            View childAt = absListView.getChildAt(i - absListView.mFirstPosition);
            if (childAt != null) {
                int i2 = AbsListView.this.mMotionPosition;
                long itemId = AbsListView.this.mAdapter.getItemId(AbsListView.this.mMotionPosition);
                if (!sameWindow() || AbsListView.this.mDataChanged) {
                    zPerformLongPress = false;
                } else {
                    AbsListView.this.mIsLongPressTriggeredByKey = false;
                    float f = this.mX;
                    if (f != -1.0f) {
                        float f2 = this.mY;
                        if (f2 != -1.0f) {
                            zPerformLongPress = AbsListView.this.performLongPress(childAt, i2, itemId, f, f2);
                        } else {
                            zPerformLongPress = AbsListView.this.performLongPress(childAt, i2, itemId);
                        }
                    }
                }
                if (zPerformLongPress) {
                    AbsListView.this.mHasPerformedLongPress = true;
                    AbsListView.this.mTouchMode = -1;
                    AbsListView.this.setPressed(false);
                    childAt.setPressed(false);
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
            boolean zPerformLongPress;
            if (!AbsListView.this.isPressed() || AbsListView.this.mSelectedPosition < 0) {
                return;
            }
            View childAt = AbsListView.this.getChildAt(AbsListView.this.mSelectedPosition - AbsListView.this.mFirstPosition);
            if (!AbsListView.this.mDataChanged) {
                if (sameWindow()) {
                    AbsListView.this.mIsLongPressTriggeredByKey = true;
                    AbsListView absListView = AbsListView.this;
                    zPerformLongPress = absListView.performLongPress(childAt, absListView.mSelectedPosition, AbsListView.this.mSelectedRowId);
                    AbsListView.this.mIsLongPressTriggeredByKey = false;
                } else {
                    zPerformLongPress = false;
                }
                if (zPerformLongPress) {
                    AbsListView.this.setPressed(false);
                    if (childAt != null) {
                        childAt.setPressed(false);
                        return;
                    }
                    return;
                }
                return;
            }
            AbsListView.this.setPressed(false);
            if (childAt != null) {
                childAt.setPressed(false);
            }
        }
    }

    private boolean performStylusButtonPressAction(MotionEvent motionEvent) {
        View childAt;
        if (this.mChoiceMode == 3 && this.mChoiceActionMode == null && (childAt = getChildAt(this.mMotionPosition - this.mFirstPosition)) != null) {
            int i = this.mMotionPosition;
            if (performLongPress(childAt, i, this.mAdapter.getItemId(i))) {
                this.mTouchMode = -1;
                setPressed(false);
                childAt.setPressed(false);
                return true;
            }
        }
        return false;
    }

    boolean performLongPress(View view, int i, long j) {
        return performLongPress(view, i, j, -1.0f, -1.0f);
    }

    boolean performLongPress(View view, int i, long j, float f, float f2) {
        AbsListView absListView;
        View view2;
        int i2;
        long j2;
        boolean zShowContextMenuForChild;
        if (this.mChoiceMode == 3) {
            if (this.mChoiceActionMode == null) {
                ActionMode actionModeStartActionMode = startActionMode(this.mMultiChoiceModeCallback);
                this.mChoiceActionMode = actionModeStartActionMode;
                if (actionModeStartActionMode != null) {
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
            AbsListView absListView2 = this;
            view2 = view;
            i2 = i;
            j2 = j;
            zShowContextMenuForChild = this.mOnItemLongClickListener.onItemLongClick(absListView2, view2, i2, j2);
            absListView = absListView2;
        } else {
            absListView = this;
            view2 = view;
            i2 = i;
            j2 = j;
            zShowContextMenuForChild = false;
        }
        if (!zShowContextMenuForChild) {
            absListView.mContextMenuInfo = absListView.createContextMenuInfo(view2, i2, j2);
            if (f != -1.0f && f2 != -1.0f) {
                zShowContextMenuForChild = super.showContextMenuForChild(absListView, f, f2);
            } else {
                zShowContextMenuForChild = super.showContextMenuForChild(absListView);
            }
        }
        if (zShowContextMenuForChild && absListView.semGetEnableVibrationAtLongPress()) {
            absListView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
        }
        return zShowContextMenuForChild;
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
    public boolean showContextMenu(float f, float f2) {
        return showContextMenuInternal(f, f2, true);
    }

    private boolean showContextMenuInternal(float f, float f2, boolean z) {
        int iPointToPosition = pointToPosition((int) f, (int) f2);
        if (iPointToPosition != -1) {
            long itemId = this.mAdapter.getItemId(iPointToPosition);
            View childAt = getChildAt(iPointToPosition - this.mFirstPosition);
            if (childAt != null) {
                this.mContextMenuInfo = createContextMenuInfo(childAt, iPointToPosition, itemId);
                if (z) {
                    return super.showContextMenuForChild(this, f, f2);
                }
                return super.showContextMenuForChild(this);
            }
        } else {
            this.mContextMenuInfo = null;
        }
        if (z) {
            return super.showContextMenu(f, f2);
        }
        return super.showContextMenu();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(View view) {
        if (isShowingContextMenuWithCoords()) {
            return false;
        }
        return showContextMenuForChildInternal(view, 0.0f, 0.0f, false);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(View view, float f, float f2) {
        return showContextMenuForChildInternal(view, f, f2, true);
    }

    private boolean showContextMenuForChildInternal(View view, float f, float f2, boolean z) {
        AbsListView absListView;
        View view2;
        int positionForView = getPositionForView(view);
        boolean zOnItemLongClick = false;
        if (positionForView < 0) {
            return false;
        }
        long itemId = this.mAdapter.getItemId(positionForView);
        if (this.mOnItemLongClickListener != null) {
            absListView = this;
            view2 = view;
            zOnItemLongClick = this.mOnItemLongClickListener.onItemLongClick(absListView, view2, positionForView, itemId);
        } else {
            absListView = this;
            view2 = view;
        }
        if (zOnItemLongClick) {
            return zOnItemLongClick;
        }
        absListView.mContextMenuInfo = absListView.createContextMenuInfo(absListView.getChildAt(positionForView - absListView.mFirstPosition), positionForView, itemId);
        if (z) {
            return super.showContextMenuForChild(view2, f, f2);
        }
        return super.showContextMenuForChild(view2);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
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
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        View view;
        if (KeyEvent.isConfirmKey(i)) {
            if (!isEnabled()) {
                return true;
            }
            if (this.mAppWidgetInnerFocus && (view = this.mNextClickable) != null) {
                view.performClick();
                this.mNextClickable.setPressed(false);
                setPressed(false);
                return true;
            }
            if (isClickable() && isPressed() && this.mSelectedPosition >= 0 && this.mAdapter != null && this.mSelectedPosition < this.mAdapter.getCount() && this.mAdapter.isEnabled(this.mSelectedPosition)) {
                View childAt = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (childAt != null) {
                    performItemClick(childAt, this.mSelectedPosition, this.mSelectedRowId);
                    childAt.setPressed(false);
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
                                View childAt2 = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                                View childAt3 = getChildAt(this.mSemCurrentFocusPosition);
                                if (this.mIsShiftkeyPressed && childAt2 != null) {
                                    if (this.mCurrentKeyCode == 0) {
                                        resetPressItemListArray();
                                        semNotifyKeyPressState(childAt3, this.mSemCurrentFocusPosition, this.mSelectedRowId);
                                        semNotifyKeyPressState(childAt2, this.mSelectedPosition, this.mSelectedRowId);
                                        addToPressItemListArray(this.mSemCurrentFocusPosition, this.mSelectedPosition);
                                        this.mFirstPressedPoint = this.mSemCurrentFocusPosition;
                                    } else {
                                        resetPressItemListArray();
                                        semNotifyKeyPressState(childAt2, this.mSelectedPosition, this.mSelectedRowId);
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
            }
        } else if (this.mIsCtrlkeyPressed) {
            resetPressItemListArray();
        }
        return super.onKeyUp(i, keyEvent);
    }

    public int pointToPosition(int i, int i2) {
        Rect rect = this.mTouchFrame;
        if (rect == null) {
            rect = new Rect();
            this.mTouchFrame = rect;
        }
        boolean z = false;
        int i3 = this instanceof ListView ? ((ListView) this).mDividerHeight : 0;
        this.mHasDividerHeight = i3;
        if (i3 > 0 && ((ListView) this).mDivider != null) {
            z = true;
        }
        this.mHasDivier = z;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt.getVisibility() == 0) {
                childAt.getHitRect(rect);
                if (this.mHasDivier) {
                    rect.bottom += this.mHasDividerHeight;
                }
                if (rect.contains(i, i2)) {
                    return this.mFirstPosition + childCount;
                }
            }
        }
        return -1;
    }

    public long pointToRowId(int i, int i2) {
        int iPointToPosition = pointToPosition(i, i2);
        if (iPointToPosition >= 0) {
            return this.mAdapter.getItemId(iPointToPosition);
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
            if (AbsListView.this.mTouchMode == 0) {
                AbsListView.this.mTouchMode = 1;
                AbsListView absListView = AbsListView.this;
                View childAt = absListView.getChildAt(absListView.mMotionPosition - AbsListView.this.mFirstPosition);
                if (childAt == null || childAt.hasExplicitFocusable() || AbsListView.this.getAdapter() == null || AbsListView.this.mMotionPosition < 0 || !AbsListView.this.getAdapter().isEnabled(AbsListView.this.mMotionPosition)) {
                    return;
                }
                AbsListView.this.mLayoutMode = 0;
                if (!AbsListView.this.mDataChanged) {
                    if (AbsListView.this.mSweepListAnimator == null || !AbsListView.this.mSweepListAnimator.isSwiping()) {
                        float[] fArr = AbsListView.this.mTmpPoint;
                        fArr[0] = this.x;
                        fArr[1] = this.y;
                        AbsListView.this.transformPointToViewLocal(fArr, childAt);
                        childAt.drawableHotspotChanged(fArr[0], fArr[1]);
                        childAt.setPressed(true);
                        AbsListView.this.setPressed(true);
                        AbsListView.this.layoutChildren();
                        AbsListView absListView2 = AbsListView.this;
                        absListView2.positionSelector(absListView2.mMotionPosition, childAt);
                        AbsListView.this.refreshDrawableState();
                        int longPressTimeout = ViewConfiguration.getLongPressTimeout();
                        boolean zIsLongClickable = AbsListView.this.isLongClickable();
                        if (AbsListView.this.mSelector != null) {
                            Drawable current = AbsListView.this.mSelector.getCurrent();
                            if (current != null && (current instanceof TransitionDrawable)) {
                                if (zIsLongClickable) {
                                    ((TransitionDrawable) current).startTransition(longPressTimeout);
                                } else {
                                    ((TransitionDrawable) current).resetTransition();
                                }
                            }
                            AbsListView.this.mSelector.setHotspot(this.x, this.y);
                        }
                        if (zIsLongClickable) {
                            if (AbsListView.this.mPendingCheckForLongPress == null) {
                                AbsListView.this.mPendingCheckForLongPress = new CheckForLongPress();
                            }
                            AbsListView.this.mPendingCheckForLongPress.setCoords(this.x, this.y);
                            AbsListView.this.mPendingCheckForLongPress.rememberWindowAttachCount();
                            AbsListView absListView3 = AbsListView.this;
                            absListView3.postDelayed(absListView3.mPendingCheckForLongPress, longPressTimeout);
                            return;
                        }
                        AbsListView.this.mTouchMode = 2;
                        return;
                    }
                    return;
                }
                AbsListView.this.mTouchMode = 2;
            }
        }
    }

    private boolean startScrollIfNeeded(int i, int i2, MotionEvent motionEvent) throws Resources.NotFoundException {
        int i3 = i2 - this.mMotionY;
        int iAbs = Math.abs(i3);
        boolean z = this.mScrollY != 0;
        if ((!z && iAbs <= this.mTouchSlop) || (getNestedScrollAxes() & 2) != 0) {
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

    private void scrollIfNeeded(int i, int i2, MotionEvent motionEvent) throws Resources.NotFoundException {
        int i3;
        int i4;
        int i5;
        int i6;
        int childCount;
        boolean zTrackMotionScroll;
        ViewParent parent;
        int i7 = i2 - this.mMotionY;
        int i8 = this.mLastY;
        if (i8 == Integer.MIN_VALUE) {
            i7 -= this.mMotionCorrection;
        }
        int iReleaseGlow = releaseGlow(i8 != Integer.MIN_VALUE ? i2 - i8 : i7, i);
        int i9 = 0;
        if (dispatchNestedPreScroll(0, -iReleaseGlow, this.mScrollConsumed, this.mScrollOffset)) {
            int i10 = this.mScrollConsumed[1];
            i7 += i10;
            int i11 = this.mScrollOffset[1];
            int i12 = -i11;
            iReleaseGlow += i10;
            if (motionEvent != null) {
                motionEvent.offsetLocation(0.0f, i11);
                this.mNestedYOffset += this.mScrollOffset[1];
            }
            i3 = i12;
        } else {
            i3 = 0;
        }
        int i13 = i7;
        int i14 = iReleaseGlow;
        int i15 = this.mTouchMode;
        if (i15 == 3) {
            if (this.mScrollStrictSpan == null) {
                this.mScrollStrictSpan = StrictMode.enterCriticalSpan("AbsListView-scroll");
            }
            if (i2 != this.mLastY) {
                if ((this.mGroupFlags & 524288) == 0 && Math.abs(i13) > this.mTouchSlop && (parent = getParent()) != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                int i16 = this.mMotionPosition;
                if (i16 >= 0) {
                    childCount = i16 - this.mFirstPosition;
                } else {
                    childCount = getChildCount() / 2;
                }
                View childAt = getChildAt(childCount);
                int top = childAt != null ? childAt.getTop() : 0;
                if (i14 != 0) {
                    zTrackMotionScroll = trackMotionScroll(i13, i14);
                    if (motionEvent != null && Flags.enableScrollFeedbackForTouch()) {
                        initHapticScrollFeedbackProviderIfNotExists();
                        this.mHapticScrollFeedbackProvider.onScrollProgress(motionEvent.getDeviceId(), motionEvent.getSource(), 1, i14);
                    }
                } else {
                    zTrackMotionScroll = false;
                }
                View childAt2 = getChildAt(childCount);
                if (childAt2 != null) {
                    int top2 = childAt2.getTop();
                    if (zTrackMotionScroll) {
                        int i17 = (-i14) - (top2 - top);
                        if (dispatchNestedScroll(0, i17 - i14, 0, i17, this.mScrollOffset) && !this.mSemForcedDrawEdgeEffect) {
                            int i18 = this.mScrollOffset[1];
                            int i19 = 0 - i18;
                            if (motionEvent != null) {
                                motionEvent.offsetLocation(0.0f, i18);
                                this.mNestedYOffset += this.mScrollOffset[1];
                            }
                            i9 = i19;
                        } else {
                            boolean zOverScrollBy = overScrollBy(0, i17, 0, this.mScrollY, 0, 0, 0, this.mOverscrollDistance, true);
                            int overScrollMode = getOverScrollMode();
                            if (overScrollMode == 0 || (overScrollMode == 1 && !contentFits())) {
                                if (!zOverScrollBy) {
                                    this.mDirection = 0;
                                    this.mTouchMode = 5;
                                }
                                if (motionEvent != null && Flags.enableScrollFeedbackForTouch()) {
                                    initHapticScrollFeedbackProviderIfNotExists();
                                    this.mHapticScrollFeedbackProvider.onScrollLimit(motionEvent.getDeviceId(), motionEvent.getSource(), 1, i14 > 0);
                                }
                                if (i14 > 0) {
                                    this.mEdgeGlowTop.onPullDistance((-i17) / getHeight(), i / getWidth());
                                    if (!this.mEdgeGlowBottom.isFinished()) {
                                        this.mEdgeGlowBottom.onRelease();
                                    }
                                    invalidateEdgeEffects();
                                } else if (i14 < 0) {
                                    this.mEdgeGlowBottom.onPullDistance(i17 / getHeight(), 1.0f - (i / getWidth()));
                                    if (!this.mEdgeGlowTop.isFinished()) {
                                        this.mEdgeGlowTop.onRelease();
                                    }
                                    invalidateEdgeEffects();
                                }
                            }
                        }
                    }
                    this.mMotionY = i2 + i9 + i3;
                }
                this.mLastY = i2 + i9 + i3;
                return;
            }
            return;
        }
        if (i15 != 5 || i2 == this.mLastY) {
            return;
        }
        int i20 = this.mScrollY;
        int i21 = i20 - i14;
        int i22 = i2 > this.mLastY ? 1 : -1;
        if (this.mDirection == 0) {
            this.mDirection = i22;
        }
        int i23 = -i14;
        if ((i21 >= 0 || i20 < 0) && (i21 <= 0 || i20 > 0)) {
            i4 = 0;
        } else {
            i23 = -i20;
            i4 = i14 + i23;
        }
        if (i23 != 0) {
            int i24 = i23;
            i5 = i4;
            overScrollBy(0, i24, 0, this.mScrollY, 0, 0, 0, this.mOverscrollDistance, true);
            int overScrollMode2 = getOverScrollMode();
            if (overScrollMode2 == 0 || (overScrollMode2 == 1 && !contentFits())) {
                if (i13 > 0) {
                    this.mEdgeGlowTop.onPullDistance(i24 / getHeight(), i / getWidth());
                    if (!this.mEdgeGlowBottom.isFinished()) {
                        this.mEdgeGlowBottom.onRelease();
                    }
                    invalidateEdgeEffects();
                } else if (i13 < 0) {
                    this.mEdgeGlowBottom.onPullDistance((-i24) / getHeight(), 1.0f - (i / getWidth()));
                    if (!this.mEdgeGlowTop.isFinished()) {
                        this.mEdgeGlowTop.onRelease();
                    }
                    invalidateEdgeEffects();
                }
            }
        } else {
            i5 = i4;
        }
        if (i5 != 0) {
            if (this.mScrollY != 0) {
                i6 = 0;
                this.mScrollY = 0;
                invalidateParentIfNeeded();
            } else {
                i6 = 0;
            }
            trackMotionScroll(i5, i5);
            this.mTouchMode = 3;
            int iFindClosestMotionRow = findClosestMotionRow(i2);
            this.mMotionCorrection = i6;
            View childAt3 = getChildAt(iFindClosestMotionRow - this.mFirstPosition);
            this.mMotionViewOriginalTop = childAt3 != null ? childAt3.getTop() : i6;
            this.mMotionY = i2 + i3;
            this.mMotionPosition = iFindClosestMotionRow;
        }
        this.mLastY = i2 + i3;
        this.mDirection = i22;
    }

    private int releaseGlow(int i, int i2) {
        float fOnPullDistance = 0.0f;
        if (this.mEdgeGlowTop.getDistance() != 0.0f) {
            if (canScrollUp()) {
                this.mEdgeGlowTop.onRelease();
            } else {
                fOnPullDistance = this.mEdgeGlowTop.onPullDistance(i / getHeight(), i2 / getWidth());
            }
            invalidateEdgeEffects();
        } else if (this.mEdgeGlowBottom.getDistance() != 0.0f) {
            if (canScrollDown()) {
                this.mEdgeGlowBottom.onRelease();
            } else {
                fOnPullDistance = -this.mEdgeGlowBottom.onPullDistance((-i) / getHeight(), 1.0f - (i2 / getWidth()));
            }
            invalidateEdgeEffects();
        }
        return i - Math.round(fOnPullDistance * getHeight());
    }

    private boolean doesTouchStopStretch() {
        if (this.mEdgeGlowBottom.getDistance() == 0.0f || canScrollDown()) {
            return (this.mEdgeGlowTop.getDistance() == 0.0f || canScrollUp()) ? false : true;
        }
        return true;
    }

    private void invalidateEdgeEffects() {
        if (shouldDisplayEdgeEffects()) {
            invalidate();
        }
    }

    @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
    public void onTouchModeChanged(boolean z) throws Resources.NotFoundException {
        if (z) {
            hideSelector();
            if (getHeight() > 0 && getChildCount() > 0) {
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
            if (this.mScrollY != 0) {
                this.mScrollY = 0;
                invalidateParentCaches();
                finishGlows();
                invalidate();
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        if (!this.mAppWidgetEnabled) {
            return true;
        }
        if (!isEnabled()) {
            Log.d(TAG, "onTouchEvent() mIsLongPressMultiSelection : " + this.mIsLongPressMultiSelection);
            return isClickable() || isLongClickable();
        }
        AbsPositionScroller absPositionScroller = this.mPositionScroller;
        if (absPositionScroller != null) {
            absPositionScroller.stop();
        }
        if (this.mIsDetaching || !isAttachedToWindow()) {
            Log.d(TAG, "onTouchEvent() mIsDetaching : " + this.mIsDetaching + ", isAttachedToWindow() : " + isAttachedToWindow());
            return false;
        }
        startNestedScroll(2);
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null && fastScroller.onTouchEvent(motionEvent)) {
            return true;
        }
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller != null) {
            boolean zOnTouchEvent = semFastScroller.onTouchEvent(motionEvent);
            if (this.mSemFastScrollEventListener != null) {
                if ((motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 2) && this.mSemFastScroll.getEffectState() == 1) {
                    this.mSemFastScrollEventListener.onPressed(this.mSemFastScroll.getScrollY());
                } else {
                    this.mSemFastScrollEventListener.onReleased(this.mSemFastScroll.getScrollY());
                }
            }
            this.mSemFastScrollEffectState = this.mSemFastScroll.getEffectState() == 1;
            if (zOnTouchEvent) {
                return true;
            }
        }
        initVelocityTrackerIfNotExists();
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mNestedYOffset = 0;
        }
        motionEventObtain.offsetLocation(0.0f, this.mNestedYOffset);
        if (actionMasked == 0) {
            onTouchDown(motionEvent);
        } else if (actionMasked == 1) {
            onTouchUp(motionEvent);
        } else if (actionMasked == 2) {
            onTouchMove(motionEvent, motionEventObtain);
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
            int iPointToPosition = pointToPosition(x, y);
            if (iPointToPosition >= 0) {
                this.mMotionViewOriginalTop = getChildAt(iPointToPosition - this.mFirstPosition).getTop();
                this.mMotionPosition = iPointToPosition;
            }
            this.mLastY = y;
            this.mPointerCount++;
        } else if (actionMasked == 6) {
            onSecondaryPointerUp(motionEvent);
            int i = this.mMotionX;
            int i2 = this.mMotionY;
            int iPointToPosition2 = pointToPosition(i, i2);
            if (iPointToPosition2 >= 0) {
                View childAt = getChildAt(iPointToPosition2 - this.mFirstPosition);
                this.mMotionViewOriginalTop = childAt.getTop();
                this.mMotionPosition = iPointToPosition2;
                ListAdapter listAdapter = this.mAdapter;
                if (listAdapter != null && listAdapter.isEnabled(iPointToPosition2) && !childAt.hasFocusable()) {
                    layoutChildren();
                }
            } else {
                layoutChildren();
            }
            this.mLastY = i2;
            this.mPointerCount--;
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEventObtain);
        }
        motionEventObtain.recycle();
        return true;
    }

    private void onTouchDown(MotionEvent motionEvent) throws Resources.NotFoundException {
        this.mHasPerformedLongPress = false;
        this.mActivePointerId = motionEvent.getPointerId(0);
        hideSelector();
        this.mPointerCount++;
        if (this.mTouchMode == 6) {
            FlingRunnable flingRunnable = this.mFlingRunnable;
            if (flingRunnable != null) {
                flingRunnable.endFling();
            }
            AbsPositionScroller absPositionScroller = this.mPositionScroller;
            if (absPositionScroller != null) {
                absPositionScroller.stop();
            }
            this.mTouchMode = 5;
            this.mMotionX = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            this.mMotionY = y;
            this.mLastY = y;
            this.mMotionCorrection = 0;
            this.mDirection = 0;
            stopEdgeGlowRecede(motionEvent.getX());
        } else {
            int x = (int) motionEvent.getX();
            int y2 = (int) motionEvent.getY();
            int iPointToPosition = pointToPosition(x, y2);
            if (!this.mDataChanged) {
                if (this.mTouchMode == 4) {
                    createScrollingCache();
                    this.mTouchMode = 3;
                    this.mMotionCorrection = 0;
                    iPointToPosition = findMotionRow(y2);
                    FlingRunnable flingRunnable2 = this.mFlingRunnable;
                    if (flingRunnable2 != null) {
                        flingRunnable2.flywheelTouch();
                    }
                    stopEdgeGlowRecede(x);
                } else if (iPointToPosition >= 0 && getAdapter().isEnabled(iPointToPosition)) {
                    this.mTouchMode = 0;
                    if (this.mPendingCheckForTap == null) {
                        this.mPendingCheckForTap = new CheckForTap();
                    }
                    this.mPendingCheckForTap.x = motionEvent.getX();
                    this.mPendingCheckForTap.y = motionEvent.getY();
                    postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                }
            }
            if (iPointToPosition >= 0) {
                this.mMotionViewOriginalTop = getChildAt(iPointToPosition - this.mFirstPosition).getTop();
            }
            this.mMotionX = x;
            this.mMotionY = y2;
            this.mMotionPosition = iPointToPosition;
            this.mLastY = Integer.MIN_VALUE;
        }
        if (this.mTouchMode == 0 && this.mMotionPosition != -1 && performButtonActionOnTouchDown(motionEvent)) {
            removeCallbacks(this.mPendingCheckForTap);
        }
    }

    private void stopEdgeGlowRecede(float f) {
        if (this.mEdgeGlowTop.getDistance() != 0.0f) {
            this.mEdgeGlowTop.onPullDistance(0.0f, f / getWidth());
        }
        if (this.mEdgeGlowBottom.getDistance() != 0.0f) {
            this.mEdgeGlowBottom.onPullDistance(0.0f, f / getWidth());
        }
    }

    private void onTouchMove(MotionEvent motionEvent, MotionEvent motionEvent2) throws Resources.NotFoundException {
        View childAt;
        View childAt2;
        if (this.mHasPerformedLongPress) {
            return;
        }
        int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (iFindPointerIndex == -1) {
            this.mActivePointerId = motionEvent.getPointerId(0);
            iFindPointerIndex = 0;
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        int y = (int) motionEvent.getY(iFindPointerIndex);
        int i = this.mTouchMode;
        if (i == 0 || i == 1 || i == 2) {
            if (!startScrollIfNeeded((int) motionEvent.getX(iFindPointerIndex), y, motionEvent2)) {
                View childAt3 = getChildAt(this.mMotionPosition - this.mFirstPosition);
                float x = motionEvent.getX(iFindPointerIndex);
                float f = y;
                if (!pointInView(x, f, this.mTouchSlop)) {
                    setPressed(false);
                    if (childAt3 != null) {
                        childAt3.setPressed(false);
                    }
                    removeCallbacks(this.mTouchMode == 0 ? this.mPendingCheckForTap : this.mPendingCheckForLongPress);
                    this.mTouchMode = 2;
                    updateSelectorState();
                } else if (childAt3 != null) {
                    float[] fArr = this.mTmpPoint;
                    fArr[0] = x;
                    fArr[1] = f;
                    transformPointToViewLocal(fArr, childAt3);
                    childAt3.drawableHotspotChanged(fArr[0], fArr[1]);
                }
            }
        } else if (i == 3 || i == 5) {
            scrollIfNeeded((int) motionEvent.getX(iFindPointerIndex), y, motionEvent2);
        }
        if (this.mAppWidgetIndicator) {
            int firstVisiblePosition = getFirstVisiblePosition();
            int lastVisiblePosition = getLastVisiblePosition();
            if (getChildCount() > 1) {
                childAt = getChildAt(0);
                childAt2 = getChildAt(1);
            } else {
                childAt = null;
                childAt2 = null;
            }
            if (childAt != null && getHeight() / 2 < childAt.getBottom() && this.mFocusedPos != firstVisiblePosition) {
                semInvalidateIndicator(firstVisiblePosition);
            } else {
                if (childAt2 == null || getHeight() / 2 <= childAt2.getTop() || this.mFocusedPos == lastVisiblePosition) {
                    return;
                }
                semInvalidateIndicator(lastVisiblePosition);
            }
        }
    }

    private void onTouchUp(MotionEvent motionEvent) throws Resources.NotFoundException {
        View childAt;
        Log.d(TAG, "onTouchUp() mTouchMode : " + this.mTouchMode);
        int i = this.mTouchMode;
        if (i == 0 || i == 1 || i == 2) {
            int i2 = this.mMotionPosition;
            final View childAt2 = getChildAt(i2 - this.mFirstPosition);
            if (childAt2 != null) {
                if (this.mTouchMode != 0) {
                    childAt2.setPressed(false);
                }
                float x = motionEvent.getX();
                if (x > this.mListPadding.left && x < getWidth() - this.mListPadding.right && !childAt2.hasExplicitFocusable()) {
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
                            childAt2.setPressed(true);
                            positionSelector(this.mMotionPosition, childAt2);
                            setPressed(true);
                            Drawable drawable = this.mSelector;
                            if (drawable != null) {
                                Drawable current = drawable.getCurrent();
                                if (current != null && (current instanceof TransitionDrawable)) {
                                    ((TransitionDrawable) current).resetTransition();
                                }
                                this.mSelector.setHotspot(x, motionEvent.getY());
                            }
                            Runnable runnable = this.mTouchModeReset;
                            if (runnable != null) {
                                removeCallbacks(runnable);
                            }
                            Runnable runnable2 = new Runnable() { // from class: android.widget.AbsListView.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    AbsListView.this.mTouchModeReset = null;
                                    AbsListView.this.mTouchMode = -1;
                                    childAt2.setPressed(false);
                                    AbsListView.this.setPressed(false);
                                    if ((AbsListView.this.mDataChanged || AbsListView.this.mIsDetaching || !AbsListView.this.isAttachedToWindow()) && !AbsListView.this.mForcedClick) {
                                        return;
                                    }
                                    performClick.run();
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
                    if ((!this.mDataChanged || this.mForcedClick) && this.mAdapter.isEnabled(i2)) {
                        performClick.run();
                    }
                }
            }
            this.mTouchMode = -1;
            updateSelectorState();
        } else if (i == 3) {
            int childCount = getChildCount();
            if (childCount > 0) {
                int top = getChildAt(0).getTop();
                int bottom = getChildAt(childCount - 1).getBottom();
                int i4 = this.mListPadding.top;
                int height = getHeight() - this.mListPadding.bottom;
                if (this.mFirstPosition == 0 && top >= i4 && this.mFirstPosition + childCount < this.mItemCount && bottom <= getHeight() - height) {
                    this.mTouchMode = -1;
                    reportScrollStateChange(0);
                } else {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                    int yVelocity = (int) (velocityTracker.getYVelocity(this.mActivePointerId) * this.mVelocityScale);
                    boolean z = Math.abs(yVelocity) > this.mMinimumVelocity;
                    if (z && !this.mEdgeGlowTop.isFinished()) {
                        if (shouldAbsorb(this.mEdgeGlowTop, yVelocity)) {
                            this.mEdgeGlowTop.onAbsorb(yVelocity);
                        } else {
                            if (this.mFlingRunnable == null) {
                                this.mFlingRunnable = new FlingRunnable();
                            }
                            this.mFlingRunnable.start(-yVelocity);
                        }
                    } else if (z && !this.mEdgeGlowBottom.isFinished()) {
                        int i5 = -yVelocity;
                        if (shouldAbsorb(this.mEdgeGlowBottom, i5)) {
                            this.mEdgeGlowBottom.onAbsorb(i5);
                        } else {
                            if (this.mFlingRunnable == null) {
                                this.mFlingRunnable = new FlingRunnable();
                            }
                            this.mFlingRunnable.start(i5);
                        }
                    } else if (z && ((this.mFirstPosition != 0 || top != i4 - this.mOverscrollDistance) && (this.mFirstPosition + childCount != this.mItemCount || bottom != height + this.mOverscrollDistance))) {
                        int i6 = -yVelocity;
                        float f = i6;
                        if (!dispatchNestedPreFling(0.0f, f)) {
                            if (this.mFlingRunnable == null) {
                                this.mFlingRunnable = new FlingRunnable();
                            }
                            reportScrollStateChange(2);
                            this.mFlingRunnable.start(i6);
                            dispatchNestedFling(0.0f, f, true);
                        } else {
                            this.mTouchMode = -1;
                            reportScrollStateChange(0);
                        }
                    } else {
                        this.mTouchMode = -1;
                        FlingRunnable flingRunnable = this.mFlingRunnable;
                        if (flingRunnable != null) {
                            flingRunnable.endFling();
                        }
                        reportScrollStateChange(0);
                        AbsPositionScroller absPositionScroller = this.mPositionScroller;
                        if (absPositionScroller != null) {
                            absPositionScroller.stop();
                        }
                        if (z) {
                            float f2 = -yVelocity;
                            if (!dispatchNestedPreFling(0.0f, f2)) {
                                dispatchNestedFling(0.0f, f2, false);
                            }
                        }
                        if (this.mAppWidgetSnapScroll && (childAt = getChildAt(1)) != null) {
                            int height2 = getHeight();
                            boolean z2 = getFirstVisiblePosition() != this.mFocusedPos;
                            if (!z2) {
                                childAt = getChildAt(0);
                            }
                            if (z2) {
                                if (childAt.getTop() > height2 / 2) {
                                    smoothScrollToPositionFromTop(this.mFirstPosition, 0);
                                } else {
                                    smoothScrollToPositionFromTop(this.mFirstPosition + 1, 0);
                                }
                            } else if (childAt.getBottom() < height2 / 2) {
                                smoothScrollToPositionFromTop(this.mFirstPosition + 1, 0);
                            } else {
                                smoothScrollToPositionFromTop(this.mFirstPosition, height2 - childAt.getHeight());
                            }
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
            int yVelocity2 = (int) velocityTracker2.getYVelocity(this.mActivePointerId);
            reportScrollStateChange(2);
            if (Math.abs(yVelocity2) > this.mMinimumVelocity) {
                this.mFlingRunnable.startOverfling(-yVelocity2);
            } else {
                this.mFlingRunnable.startSpringback();
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
        StrictMode.Span span = this.mScrollStrictSpan;
        if (span != null) {
            span.finish();
            this.mScrollStrictSpan = null;
        }
    }

    private boolean shouldAbsorb(EdgeEffect edgeEffect, int i) {
        if (i > 0) {
            return true;
        }
        float distance = edgeEffect.getDistance() * getHeight();
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        return this.mFlingRunnable.getSplineFlingDistance(-i) < distance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int consumeFlingInStretch(int i) {
        EdgeEffect edgeEffect;
        EdgeEffect edgeEffect2;
        if (i < 0 && (edgeEffect2 = this.mEdgeGlowTop) != null && edgeEffect2.getDistance() != 0.0f) {
            float height = getHeight();
            int iRound = Math.round((height / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowTop.onPullDistance((i * FLING_DESTRETCH_FACTOR) / height, 0.5f));
            if (iRound != i) {
                this.mEdgeGlowTop.finish();
            }
            return i - iRound;
        }
        if (i <= 0 || (edgeEffect = this.mEdgeGlowBottom) == null || edgeEffect.getDistance() == 0.0f) {
            return i;
        }
        int iRound2 = Math.round(((-r1) / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowBottom.onPullDistance(((-i) * FLING_DESTRETCH_FACTOR) / getHeight(), 0.5f));
        if (iRound2 != i) {
            this.mEdgeGlowBottom.finish();
        }
        return i - iRound2;
    }

    private boolean shouldDisplayEdgeEffects() {
        return getOverScrollMode() != 2;
    }

    private void onTouchCancel() throws Resources.NotFoundException {
        View childAt;
        int i = this.mTouchMode;
        if (i == 5) {
            if (this.mFlingRunnable == null) {
                this.mFlingRunnable = new FlingRunnable();
            }
            this.mFlingRunnable.startSpringback();
        } else if (i != 6) {
            if (this.mAppWidgetSnapScroll && i != 0 && (childAt = getChildAt(1)) != null) {
                int height = getHeight();
                boolean z = getFirstVisiblePosition() != this.mFocusedPos;
                if (!z) {
                    childAt = getChildAt(0);
                }
                if (z) {
                    if (childAt.getTop() > height / 2) {
                        scrollToPositionFromTop(this.mFirstPosition, 0);
                    } else {
                        scrollToPositionFromTop(this.mFirstPosition + 1, 0);
                    }
                } else if (childAt.getBottom() < height / 2) {
                    scrollToPositionFromTop(this.mFirstPosition + 1, 0);
                } else {
                    scrollToPositionFromTop(this.mFirstPosition, height - childAt.getHeight());
                }
            }
            this.mTouchMode = -1;
            setPressed(false);
            View childAt2 = getChildAt(this.mMotionPosition - this.mFirstPosition);
            if (childAt2 != null) {
                childAt2.setPressed(false);
            }
            clearScrollingCache();
            removeCallbacks(this.mPendingCheckForLongPress);
            if (this.mFlingRunnable != null && semIsTalkBackIsRunning()) {
                this.mFlingRunnable.endFling();
            }
            recycleVelocityTracker();
        }
        if (shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
        this.mActivePointerId = -1;
        this.mPointerCount = 0;
    }

    @Override // android.view.View
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        if (this.mScrollY != i2) {
            onScrollChanged(this.mScrollX, i2, this.mScrollX, this.mScrollY);
            if (!this.mSemEnableFillOut) {
                this.mScrollY = i2;
            }
            invalidateParentIfNeeded();
            awakenScrollBars();
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        int i;
        float axisValue;
        int actionButton;
        int i2;
        ViewRootImpl viewRootImpl;
        int action = motionEvent.getAction();
        if (action == 8) {
            if (motionEvent.isFromSource(2)) {
                i = 9;
                axisValue = motionEvent.getAxisValue(9);
            } else if (motionEvent.isFromSource(4194304)) {
                i = 26;
                axisValue = motionEvent.getAxisValue(26);
            } else {
                i = -1;
                axisValue = 0.0f;
            }
            if (this.mAppWidgetSnapScroll) {
                if (semHandleGenericMotionEvent(axisValue < 0.0f ? 130 : 33)) {
                    return true;
                }
            }
            if (this.mIsHoveredByMouse) {
                this.mIsMouseHoverScroll = true;
                this.mIsMouseHoverScrollX = (int) motionEvent.getX();
                this.mIsMouseHoverScrollY = (int) motionEvent.getY();
            }
            int iRound = Math.round(axisValue * this.mVerticalScrollFactor);
            if (iRound != 0) {
                View childAt = getChildAt(iRound > 0 ? 0 : getChildCount() - 1);
                int top = childAt != null ? childAt.getTop() : 0;
                int overScrollMode = getOverScrollMode();
                if (!trackMotionScroll(iRound, iRound)) {
                    if (Flags.scrollFeedbackApi()) {
                        initHapticScrollFeedbackProviderIfNotExists();
                        this.mHapticScrollFeedbackProvider.onScrollProgress(motionEvent.getDeviceId(), motionEvent.getSource(), i, iRound);
                    }
                    initDifferentialFlingHelperIfNotExists();
                    this.mDifferentialMotionFlingHelper.onMotionEvent(motionEvent, i);
                    return true;
                }
                if (!motionEvent.isFromSource(8194) && childAt != null && (overScrollMode == 0 || (overScrollMode == 1 && !contentFits()))) {
                    float top2 = (iRound - (childAt.getTop() - top)) / getHeight();
                    boolean z = iRound > 0;
                    if (Flags.scrollFeedbackApi()) {
                        initHapticScrollFeedbackProviderIfNotExists();
                        this.mHapticScrollFeedbackProvider.onScrollLimit(motionEvent.getDeviceId(), motionEvent.getSource(), i, z);
                    }
                    if (z) {
                        this.mEdgeGlowTop.onPullDistance(top2, 0.5f);
                        this.mEdgeGlowTop.onRelease();
                    } else {
                        this.mEdgeGlowBottom.onPullDistance(-top2, 0.5f);
                        this.mEdgeGlowBottom.onRelease();
                    }
                    invalidate();
                    return true;
                }
            }
        } else if (action == 11 && motionEvent.isFromSource(2) && (((actionButton = motionEvent.getActionButton()) == 32 || actionButton == 2) && (((i2 = this.mTouchMode) == 0 || i2 == 1) && (((viewRootImpl = getViewRootImpl()) == null || !viewRootImpl.isDesktopMode()) && performStylusButtonPressAction(motionEvent))))) {
            removeCallbacks(this.mPendingCheckForLongPress);
            removeCallbacks(this.mPendingCheckForTap);
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    public void fling(int i) throws Resources.NotFoundException {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        reportScrollStateChange(2);
        this.mFlingRunnable.start(i);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        super.onNestedScrollAccepted(view, view2, i);
        startNestedScroll(2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        View childAt = getChildAt(getChildCount() / 2);
        int top = 0;
        int top2 = childAt != null ? childAt.getTop() : 0;
        if (childAt != null) {
            int i5 = -i4;
            if (!trackMotionScroll(i5, i5)) {
                return;
            }
        }
        if (childAt != null) {
            top = childAt.getTop() - top2;
            i4 -= top;
        }
        dispatchNestedScroll(0, top, 0, i4, null);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) throws Resources.NotFoundException {
        int childCount = getChildCount();
        if (!z && childCount > 0) {
            int i = (int) f2;
            if (canScrollList(i) && Math.abs(f2) > this.mMinimumVelocity) {
                reportScrollStateChange(2);
                if (this.mFlingRunnable == null) {
                    this.mFlingRunnable = new FlingRunnable();
                }
                if (dispatchNestedPreFling(0.0f, f2)) {
                    return true;
                }
                this.mFlingRunnable.start(i);
                return true;
            }
        }
        return dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) throws Resources.NotFoundException {
        Drawable drawable;
        int width;
        int height;
        int i;
        int i2;
        super.draw(canvas);
        if (shouldDisplayEdgeEffects()) {
            int i3 = this.mScrollY;
            boolean clipToPadding = getClipToPadding();
            if (clipToPadding) {
                width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
                height = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
                i = this.mPaddingLeft;
                i2 = this.mPaddingTop;
            } else {
                width = getWidth();
                height = getHeight();
                i = 0;
                i2 = 0;
            }
            this.mEdgeGlowTop.setSize(width, height);
            this.mEdgeGlowBottom.setSize(width, height);
            if (!this.mEdgeGlowTop.isFinished()) {
                int iSave = canvas.save();
                canvas.clipRect(i, i2, i + width, this.mEdgeGlowTop.getMaxHeight() + i2);
                canvas.translate(i, Math.min(0, this.mFirstPositionDistanceGuess + i3) + i2);
                if (this.mEdgeGlowTop.draw(canvas)) {
                    invalidateEdgeEffects();
                }
                canvas.restoreToCount(iSave);
            }
            if (!this.mEdgeGlowBottom.isFinished()) {
                int iSave2 = canvas.save();
                int i4 = i2 + height;
                canvas.clipRect(i, i4 - this.mEdgeGlowBottom.getMaxHeight(), i + width, i4);
                canvas.translate((-width) + i, Math.max(getHeight(), i3 + this.mLastPositionDistanceGuess) - (clipToPadding ? this.mPaddingBottom : 0));
                canvas.rotate(180.0f, width, 0.0f);
                if (this.mEdgeGlowBottom.draw(canvas)) {
                    invalidateEdgeEffects();
                }
                canvas.restoreToCount(iSave2);
            }
        }
        if (this.mSemEnableGoToTop) {
            drawGoToTop(canvas);
        }
        if (semIsTalkBackIsRunning() && (drawable = this.mSemGoToTopImage) != null && drawable.getAlpha() != 0.0f) {
            this.mSemGoToTopImage.setAlpha(0);
        }
        if (this.mAppWidgetIndicator) {
            drawIndicator(canvas);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller != null) {
            semFastScroller.updateLayout();
        }
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

    private void initDifferentialFlingHelperIfNotExists() {
        if (this.mDifferentialMotionFlingHelper == null) {
            this.mDifferentialMotionFlingHelper = new DifferentialMotionFlingHelper(this.mContext, new DifferentialFlingTarget());
        }
    }

    private void initHapticScrollFeedbackProviderIfNotExists() {
        if (this.mHapticScrollFeedbackProvider == null) {
            this.mHapticScrollFeedbackProvider = new HapticScrollFeedbackProvider(this);
        }
    }

    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null && fastScroller.onInterceptHoverEvent(motionEvent)) {
            return true;
        }
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller == null || !semFastScroller.onInterceptHoverEvent(motionEvent)) {
            return super.onInterceptHoverEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        PointerIcon pointerIconOnResolvePointerIcon;
        return (this.mFastScroll == null || !motionEvent.isFromSource(8194) || (pointerIconOnResolvePointerIcon = this.mFastScroll.onResolvePointerIcon(motionEvent, i)) == null) ? super.onResolvePointerIcon(motionEvent, i) : pointerIconOnResolvePointerIcon;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0073  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        int actionMasked = motionEvent.getActionMasked();
        AbsPositionScroller absPositionScroller = this.mPositionScroller;
        if (absPositionScroller != null) {
            absPositionScroller.stop();
        }
        if (this.mIsDetaching || !isAttachedToWindow()) {
            return false;
        }
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null && fastScroller.onInterceptTouchEvent(motionEvent)) {
            return true;
        }
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller != null && semFastScroller.onInterceptTouchEvent(motionEvent)) {
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
            int iFindMotionRow = findMotionRow(y);
            if (doesTouchStopStretch()) {
                this.mTouchMode = 4;
                i = 4;
            } else if (i != 4 && iFindMotionRow >= 0) {
                this.mMotionViewOriginalTop = getChildAt(iFindMotionRow - this.mFirstPosition).getTop();
                this.mMotionX = x;
                this.mMotionY = y;
                this.mMotionPosition = iFindMotionRow;
                this.mTouchMode = 0;
                clearScrollingCache();
            }
            this.mLastY = Integer.MIN_VALUE;
            initOrResetVelocityTracker();
            this.mVelocityTracker.addMovement(motionEvent);
            this.mNestedYOffset = 0;
            startNestedScroll(2);
            if (i == 4) {
                return true;
            }
        } else if (actionMasked == 1) {
            this.mTouchMode = -1;
            this.mActivePointerId = -1;
            recycleVelocityTracker();
            reportScrollStateChange(0);
            stopNestedScroll();
        } else if (actionMasked != 2) {
            if (actionMasked != 3) {
                if (actionMasked == 6) {
                    onSecondaryPointerUp(motionEvent);
                }
            }
        } else if (this.mTouchMode == 0) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            if (iFindPointerIndex == -1) {
                this.mActivePointerId = motionEvent.getPointerId(0);
                iFindPointerIndex = 0;
            }
            int y2 = (int) motionEvent.getY(iFindPointerIndex);
            initVelocityTrackerIfNotExists();
            this.mVelocityTracker.addMovement(motionEvent);
            if (startScrollIfNeeded((int) motionEvent.getX(iFindPointerIndex), y2, null)) {
                return true;
            }
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
            this.mLastY = this.mMotionY;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
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

    void reportScrollStateChange(int i) throws Resources.NotFoundException {
        OnScrollOffsetListener onScrollOffsetListener;
        if (i != this.mLastScrollState) {
            Log.d(TAG, "reportScrollStateChange() newState : " + i);
            if (this.mAppWidgetImmersiveEnalbed && (onScrollOffsetListener = this.mOnScrollOffsetListener) != null && i == 0) {
                onScrollOffsetListener.onScrollMotionDone(this);
            }
            if (i != 0 && this.mLastScrollState == 0) {
                if (this.mSemEnableGoToTop) {
                    removeCallbacks(this.mSemAutoHide);
                    semSetupGoToTop(-1);
                }
                if (!this.mHoverAreaEnter) {
                    SemPerfManager.onScrollEvent(true);
                    this.mDVFSLockAcquired = true;
                }
            }
            if (i == 0 && this.mLastScrollState != 0) {
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
            if (i != 0 && this.mLastScrollState != 0) {
                semSetupGoToTop(1);
            }
            this.mLastScrollState = i;
            OnScrollListener onScrollListener = this.mOnScrollListener;
            if (onScrollListener != null) {
                onScrollListener.onScrollStateChanged(this, i);
            }
        } else if (i != 0 && !this.mEdgeGlowTop.isFinished()) {
            semSetupGoToTop(1);
        }
        if (i == 0 || i == 2) {
            this.mReportChildrenToContentCaptureOnNextUpdate = true;
        }
    }

    private class FlingRunnable implements Runnable {
        private static final int FLYWHEEL_TIMEOUT = 40;
        private final Runnable mCheckFlywheel = new Runnable() { // from class: android.widget.AbsListView.FlingRunnable.1
            @Override // java.lang.Runnable
            public void run() throws Resources.NotFoundException {
                int i = AbsListView.this.mActivePointerId;
                VelocityTracker velocityTracker = AbsListView.this.mVelocityTracker;
                OverScroller overScroller = FlingRunnable.this.mScroller;
                if (velocityTracker == null || i == -1) {
                    return;
                }
                velocityTracker.computeCurrentVelocity(1000, AbsListView.this.mMaximumVelocity);
                float f = -velocityTracker.getYVelocity(i);
                if (Math.abs(f) >= AbsListView.this.mMinimumVelocity && overScroller.isScrollingInDirection(0.0f, f)) {
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

        float getSplineFlingDistance(int i) {
            return (float) this.mScroller.getSplineFlingDistance(i);
        }

        void start(int i) throws Resources.NotFoundException {
            int i2 = i < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = i2;
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(0, i2, 0, i, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            AbsListView.this.mTouchMode = 4;
            if (AbsListView.this.mAppWidgetSnapScroll) {
                View childAt = AbsListView.this.getChildAt(1);
                int height = AbsListView.this.getHeight();
                int firstVisiblePosition = AbsListView.this.getFirstVisiblePosition();
                int finalY = this.mScroller.getFinalY() - this.mScroller.getCurrY();
                View childAt2 = AbsListView.this.getChildAt(0);
                if (childAt != null) {
                    this.mScroller.abortAnimation();
                    if (i >= 0) {
                        childAt = childAt2;
                    }
                    if (childAt != null) {
                        if (i < 0) {
                            if (childAt.getTop() - finalY > height / 2) {
                                AbsListView.this.smoothScrollToPositionFromTop(firstVisiblePosition, 0);
                            } else {
                                AbsListView.this.smoothScrollToPositionFromTop(firstVisiblePosition + 1, 0);
                            }
                        } else if (childAt.getBottom() - finalY < height / 2) {
                            AbsListView.this.smoothScrollToPositionFromTop(firstVisiblePosition + 1, 0);
                        } else {
                            AbsListView.this.smoothScrollToPositionFromTop(firstVisiblePosition, height - childAt.getHeight());
                        }
                    }
                } else if (childAt2 != null) {
                    if (i < 0) {
                        if (childAt2.getTop() - finalY > 0) {
                            this.mScroller.abortAnimation();
                            AbsListView.this.smoothScrollBy(childAt2.getTop(), 200);
                        }
                    } else if (childAt2.getBottom() - finalY < height) {
                        this.mScroller.abortAnimation();
                        AbsListView.this.smoothScrollBy(childAt2.getBottom() - height, 200);
                    }
                }
            }
            this.mSuppressIdleStateChangeCall = false;
            AbsListView.this.removeCallbacks(this);
            AbsListView.this.postOnAnimation(this);
            if (AbsListView.this.mFlingStrictSpan == null) {
                AbsListView.this.mFlingStrictSpan = StrictMode.enterCriticalSpan("AbsListView-fling");
            }
        }

        void start(int i, boolean z) {
            int i2 = i < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = i2;
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(0, i2, 0, i, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, z);
            AbsListView.this.mTouchMode = 4;
            this.mSuppressIdleStateChangeCall = false;
            AbsListView.this.removeCallbacks(this);
            AbsListView.this.invalidate();
            AbsListView.this.postOnAnimation(this);
            if (AbsListView.this.mFlingStrictSpan == null) {
                AbsListView.this.mFlingStrictSpan = StrictMode.enterCriticalSpan("AbsListView-fling");
            }
        }

        void startSpringback() throws Resources.NotFoundException {
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

        void startOverfling(int i) {
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(0, AbsListView.this.mScrollY, 0, i, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, AbsListView.this.getHeight());
            AbsListView.this.mTouchMode = 6;
            this.mSuppressIdleStateChangeCall = false;
            AbsListView.this.invalidate();
            AbsListView.this.postOnAnimation(this);
        }

        void edgeReached(int i) {
            this.mScroller.notifyVerticalEdgeReached(AbsListView.this.mScrollY, 0, AbsListView.this.mOverflingDistance);
            int overScrollMode = AbsListView.this.getOverScrollMode();
            if (overScrollMode == 0 || (overScrollMode == 1 && !AbsListView.this.contentFits())) {
                AbsListView.this.mTouchMode = 6;
                int currVelocity = (int) this.mScroller.getCurrVelocity();
                if (i > 0) {
                    AbsListView.this.mEdgeGlowTop.onAbsorb(currVelocity);
                } else {
                    AbsListView.this.mEdgeGlowBottom.onAbsorb(currVelocity);
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

        void startScroll(int i, int i2, boolean z, boolean z2) {
            int i3 = i < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = i3;
            Interpolator interpolator = AbsListView.this.mAppWidgetSnapScroll ? AbsListView.this.mDecelerateInterpolator : null;
            OverScroller overScroller = this.mScroller;
            if (z) {
                interpolator = AbsListView.sLinearInterpolator;
            }
            overScroller.setInterpolator(interpolator);
            this.mScroller.startScroll(0, i3, 0, i, i2);
            AbsListView.this.mTouchMode = 4;
            this.mSuppressIdleStateChangeCall = z2;
            AbsListView.this.postOnAnimation(this);
        }

        void endFling() throws Resources.NotFoundException {
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
        public void run() throws Resources.NotFoundException {
            int iMax;
            int i = AbsListView.this.mTouchMode;
            boolean z = false;
            if (i != 3) {
                if (i != 4) {
                    if (i != 6) {
                        endFling();
                        return;
                    }
                    OverScroller overScroller = this.mScroller;
                    if (overScroller.computeScrollOffset()) {
                        int i2 = AbsListView.this.mScrollY;
                        int currY = overScroller.getCurrY();
                        AbsListView absListView = AbsListView.this;
                        if (absListView.overScrollBy(0, currY - i2, 0, i2, 0, 0, 0, absListView.mOverflingDistance, false)) {
                            boolean z2 = i2 <= 0 && currY > 0;
                            if (i2 >= 0 && currY < 0) {
                                z = true;
                            }
                            if (z2 || z) {
                                overScroller.getCurrVelocity();
                            } else {
                                startSpringback();
                            }
                        } else {
                            AbsListView.this.invalidate();
                            AbsListView.this.postOnAnimation(this);
                        }
                        if (Flags.viewVelocityApi()) {
                            AbsListView.this.setFrameContentVelocity(Math.abs(this.mScroller.getCurrVelocity()));
                            return;
                        }
                        return;
                    }
                    endFling();
                    return;
                }
            } else if (this.mScroller.isFinished()) {
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
            OverScroller overScroller2 = this.mScroller;
            boolean zComputeScrollOffset = overScroller2.computeScrollOffset();
            int currY2 = overScroller2.getCurrY();
            if (Flags.viewVelocityApi()) {
                AbsListView.this.setFrameContentVelocity(Math.abs(this.mScroller.getCurrVelocity()));
            }
            int iConsumeFlingInStretch = AbsListView.this.consumeFlingInStretch(this.mLastFlingY - currY2);
            if (iConsumeFlingInStretch > 0) {
                AbsListView absListView2 = AbsListView.this;
                absListView2.mMotionPosition = absListView2.mFirstPosition;
                AbsListView.this.mMotionViewOriginalTop = AbsListView.this.getChildAt(0).getTop();
                iMax = Math.min(((AbsListView.this.getHeight() - AbsListView.this.mPaddingBottom) - AbsListView.this.mPaddingTop) - 1, iConsumeFlingInStretch);
            } else {
                int childCount = AbsListView.this.getChildCount() - 1;
                AbsListView absListView3 = AbsListView.this;
                absListView3.mMotionPosition = absListView3.mFirstPosition + childCount;
                AbsListView.this.mMotionViewOriginalTop = AbsListView.this.getChildAt(childCount).getTop();
                iMax = Math.max(-(((AbsListView.this.getHeight() - AbsListView.this.mPaddingBottom) - AbsListView.this.mPaddingTop) - 1), iConsumeFlingInStretch);
            }
            AbsListView absListView4 = AbsListView.this;
            View childAt = absListView4.getChildAt(absListView4.mMotionPosition - AbsListView.this.mFirstPosition);
            int top = childAt != null ? childAt.getTop() : 0;
            boolean zTrackMotionScroll = AbsListView.this.trackMotionScroll(iMax, iMax);
            if (zTrackMotionScroll && iMax != 0) {
                z = true;
            }
            if (z) {
                if (childAt != null) {
                    int i3 = -(iMax - (childAt.getTop() - top));
                    AbsListView absListView5 = AbsListView.this;
                    absListView5.overScrollBy(0, i3, 0, absListView5.mScrollY, 0, 0, 0, AbsListView.this.mOverflingDistance, false);
                }
                if (zComputeScrollOffset) {
                    edgeReached(iMax);
                    return;
                }
                return;
            }
            if (zComputeScrollOffset && !z) {
                if (zTrackMotionScroll) {
                    AbsListView.this.invalidate();
                }
                this.mLastFlingY = currY2;
                AbsListView.this.postOnAnimation(this);
            } else {
                endFling();
            }
            if (AbsListView.this.mJumpScrollToTopState == AbsListView.JUMP_SCROLL_TO_TOP_FINISHING && AbsListView.this.mFirstPosition == 0 && iMax == 0 && !zComputeScrollOffset) {
                AbsListView.this.mJumpScrollToTopState = AbsListView.JUMP_SCROLL_TO_TOP_IDLE;
                AbsListView.this.postOnJumpScrollToFinished();
            }
        }
    }

    public void setFriction(float f) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        this.mFlingRunnable.mScroller.setFriction(f);
    }

    public void setVelocityScale(float f) {
        this.mVelocityScale = f;
    }

    AbsPositionScroller createPositionScroller() {
        return new PositionScroller();
    }

    public void smoothScrollToPosition(int i) {
        semSendBroadcastPosition(i, 1);
        if (this.mAppWidgetIndicator) {
            semInvalidateIndicator(i);
        }
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.start(i);
    }

    public void smoothScrollToPositionFromTop(int i, int i2, int i3) {
        semSendBroadcastPosition(i, 1);
        if (this.mAppWidgetIndicator) {
            semInvalidateIndicator(i);
        }
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.startWithOffset(i, i2, i3);
    }

    public void smoothScrollToPositionFromTop(int i, int i2) {
        semSendBroadcastPosition(i, 1);
        if (this.mAppWidgetIndicator) {
            semInvalidateIndicator(i);
        }
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.startWithOffset(i, i2);
    }

    private void scrollToPositionFromTop(int i, int i2) throws Resources.NotFoundException {
        setSelectionFromTop(i, i2);
    }

    public void smoothScrollToPosition(int i, int i2) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.start(i, i2);
    }

    public void smoothScrollBy(int i, int i2) throws Resources.NotFoundException {
        smoothScrollBy(i, i2, false, false);
    }

    void smoothScrollBy(int i, int i2, boolean z, boolean z2) throws Resources.NotFoundException {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        int i3 = this.mFirstPosition;
        int childCount = getChildCount();
        int i4 = i3 + childCount;
        int paddingTop = getPaddingTop();
        int height = getHeight() - getPaddingBottom();
        if (i == 0 || this.mItemCount == 0 || childCount == 0 || ((i3 == 0 && getChildAt(0).getTop() == paddingTop && i < 0) || (i4 == this.mItemCount && getChildAt(childCount - 1).getBottom() == height && i > 0))) {
            this.mFlingRunnable.endFling();
            AbsPositionScroller absPositionScroller = this.mPositionScroller;
            if (absPositionScroller != null) {
                absPositionScroller.stop();
            }
        } else {
            reportScrollStateChange(2);
            this.mFlingRunnable.startScroll(i, i2, z, z2);
        }
        if (semIsSupportGotoTop() && this.mSemCanGoFuther && this.mFirstPosition == 0 && !canScrollUp()) {
            Log.d(TAG, " re calculate done2 mPositionScroller = " + this.mPositionScroller);
            this.mSemCanGoFuther = false;
            this.mFlingRunnable.endFling();
            AbsPositionScroller absPositionScroller2 = this.mPositionScroller;
            if (absPositionScroller2 != null) {
                absPositionScroller2.stop();
            }
            if (this.mGoToToping) {
                this.mEdgeGlowTop.setSize(getWidth(), getHeight());
                this.mEdgeGlowTop.onAbsorb(10000);
                invalidate();
            }
        }
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
            float fWidth = (r2.width() * r2.height()) / (childAt.getWidth() * childAt.getHeight());
            if (i < 0 && fWidth < 0.75f) {
                lastVisiblePosition++;
            } else if (i > 0 && fWidth < 0.75f) {
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
            this.mClearScrollingCache = new Runnable() { // from class: android.widget.AbsListView.4
                @Override // java.lang.Runnable
                public void run() {
                    if (AbsListView.this.mCachingStarted) {
                        AbsListView absListView = AbsListView.this;
                        absListView.mCachingActive = false;
                        absListView.mCachingStarted = false;
                        AbsListView.this.setChildrenDrawnWithCacheEnabled(false);
                        if ((AbsListView.this.mPersistentDrawingCache & 2) == 0) {
                            AbsListView.this.setChildrenDrawingCacheEnabled(false);
                        }
                        if (AbsListView.this.isAlwaysDrawnWithCacheEnabled()) {
                            return;
                        }
                        AbsListView.this.invalidate();
                    }
                }
            };
        }
        post(this.mClearScrollingCache);
    }

    public void scrollListBy(int i) throws Resources.NotFoundException {
        int i2 = -i;
        trackMotionScroll(i2, i2);
    }

    public boolean canScrollList(int i) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        int i2 = this.mFirstPosition;
        Rect rect = this.mListPadding;
        if (i > 0) {
            return i2 + childCount < this.mItemCount || getChildAt(childCount + (-1)).getBottom() > getHeight() - rect.bottom;
        }
        return i2 > 0 || getChildAt(0).getTop() < rect.top;
    }

    boolean trackMotionScroll(int i, int i2) throws Resources.NotFoundException {
        int i3;
        int i4;
        int iMin;
        int iMin2;
        int i5;
        int i6;
        int i7;
        int i8;
        OnScrollOffsetListener onScrollOffsetListener;
        int i9;
        int childCount = getChildCount();
        if (childCount == 0) {
            return true;
        }
        int top = getChildAt(0).getTop();
        int i10 = childCount - 1;
        int bottom = getChildAt(i10).getBottom();
        Rect rect = this.mListPadding;
        if ((this.mGroupFlags & 34) == 34) {
            i3 = rect.top;
            i4 = rect.bottom;
        } else {
            i3 = 0;
            i4 = 0;
        }
        int i11 = i3 - top;
        int height = bottom - (getHeight() - i4);
        int height2 = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
        if (i < 0) {
            iMin = Math.max(-(height2 - 1), i);
        } else {
            iMin = Math.min(height2 - 1, i);
        }
        if (i2 < 0) {
            iMin2 = Math.max(-(height2 - 1), i2);
        } else {
            iMin2 = Math.min(height2 - 1, i2);
        }
        int i12 = this.mFirstPosition;
        if (i12 == 0) {
            this.mFirstPositionDistanceGuess = top - rect.top;
        } else {
            this.mFirstPositionDistanceGuess += iMin2;
        }
        int i13 = i12 + childCount;
        if (i13 == this.mItemCount) {
            this.mLastPositionDistanceGuess = rect.bottom + bottom;
        } else {
            this.mLastPositionDistanceGuess += iMin2;
        }
        boolean z = i12 == 0 && top >= rect.top && iMin2 >= 0;
        boolean z2 = i13 == this.mItemCount && bottom <= getHeight() - rect.bottom && iMin2 <= 0;
        if (z || z2) {
            return iMin2 != 0;
        }
        boolean z3 = iMin2 < 0;
        boolean zIsInTouchMode = isInTouchMode();
        if (zIsInTouchMode) {
            hideSelector();
        }
        int headerViewsCount = getHeaderViewsCount();
        int footerViewsCount = this.mItemCount - getFooterViewsCount();
        if (z3) {
            int i14 = -iMin2;
            if ((this.mGroupFlags & 34) == 34) {
                i14 += rect.top;
            }
            int i15 = 0;
            i7 = 0;
            while (i15 < childCount) {
                View childAt = getChildAt(i15);
                if (childAt.getBottom() >= i14) {
                    break;
                }
                i7++;
                int i16 = i12 + i15;
                childAt.clearAccessibilityFocus();
                if (i16 < headerViewsCount || i16 >= footerViewsCount) {
                    i9 = iMin;
                } else {
                    i9 = iMin;
                    this.mRecycler.addScrapView(childAt, i16);
                }
                i15++;
                iMin = i9;
            }
            i5 = iMin;
            i6 = 0;
        } else {
            i5 = iMin;
            int height3 = getHeight() - iMin2;
            if ((this.mGroupFlags & 34) == 34) {
                height3 -= rect.bottom;
            }
            int i17 = 0;
            i6 = 0;
            while (i10 >= 0) {
                View childAt2 = getChildAt(i10);
                if (childAt2.getTop() <= height3) {
                    break;
                }
                i17++;
                int i18 = i12 + i10;
                childAt2.clearAccessibilityFocus();
                if (i18 >= headerViewsCount && i18 < footerViewsCount) {
                    this.mRecycler.addScrapView(childAt2, i18);
                }
                int i19 = i10;
                i10--;
                i6 = i19;
            }
            i7 = i17;
        }
        this.mMotionViewNewTop = this.mMotionViewOriginalTop + i5;
        this.mBlockLayoutRequests = true;
        if (i7 > 0) {
            detachViewsFromParent(i6, i7);
            this.mRecycler.removeSkippedScrap();
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        offsetChildrenTopAndBottom(iMin2);
        if (this.mAppWidgetImmersiveEnalbed && (onScrollOffsetListener = this.mOnScrollOffsetListener) != null) {
            onScrollOffsetListener.onScrollOffsetChanged(this, iMin2);
        }
        if (z3) {
            this.mFirstPosition += i7;
        }
        int iAbs = Math.abs(iMin2);
        if (i11 < iAbs || height < iAbs) {
            fillGap(z3);
        }
        this.mRecycler.fullyDetachScrapViews();
        if (!zIsInTouchMode && this.mSelectedPosition != -1) {
            int i20 = this.mSelectedPosition - this.mFirstPosition;
            if (i20 >= 0 && i20 < getChildCount()) {
                positionSelector(this.mSelectedPosition, getChildAt(i20));
            } else {
                this.mSelectorRect.setEmpty();
            }
        } else {
            if (this.mIsMouseHoverScroll && this.mSelectorPosition != -1) {
                int iPointToPosition = pointToPosition(this.mIsMouseHoverScrollX, this.mIsMouseHoverScrollY);
                this.mSelectorPosition = iPointToPosition;
                int i21 = iPointToPosition - this.mFirstPosition;
                if (this.mSelectorPosition != -1) {
                    if (i21 >= 0 && i21 < getChildCount() && this.mAdapter.isEnabled(this.mSelectorPosition)) {
                        positionSelector(-1, getChildAt(i21));
                    } else if (i21 >= 0 && i21 < getChildCount() && !this.mAdapter.isEnabled(this.mSelectorPosition)) {
                        this.mSelectorRect.setEmpty();
                    }
                }
            } else {
                int i22 = this.mSelectorPosition;
                if (i22 != -1 && (i8 = i22 - this.mFirstPosition) >= 0 && i8 < getChildCount()) {
                    positionSelector(this.mSelectorPosition, getChildAt(i8));
                }
            }
            this.mSelectorRect.setEmpty();
        }
        this.mBlockLayoutRequests = false;
        if (this.mAppWidgetImmersiveEnalbed) {
            if (i5 >= 0) {
                if (this.mSemEnableGoToTop && canScrollUp() && this.mSemGoToTopState == 0) {
                    semSetupGoToTop(1);
                    semAutoHide(1);
                }
            } else {
                semSetupGoToTop(0);
            }
        } else if (this.mSemEnableGoToTop && canScrollUp() && this.mSemGoToTopState == 0) {
            semSetupGoToTop(1);
            semAutoHide(1);
        }
        invokeOnItemScrollListener();
        return false;
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
            this.mSelectedTop = 0;
            this.mSelectorPosition = -1;
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
        int iFindMotionRow = findMotionRow(i);
        return iFindMotionRow != -1 ? iFindMotionRow : (this.mFirstPosition + r0) - 1;
    }

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

    /* JADX WARN: Removed duplicated region for block: B:42:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d3 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean resurrectSelection() throws Resources.NotFoundException {
        boolean z;
        int top;
        int i;
        AbsPositionScroller absPositionScroller;
        int iLookForSelectablePosition;
        int childCount = getChildCount();
        if (childCount <= 0) {
            return false;
        }
        int verticalFadingEdgeLength = this.mListPadding.top;
        int verticalFadingEdgeLength2 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        int i2 = this.mFirstPosition;
        int i3 = this.mResurrectToPosition;
        if (i3 >= i2 && i3 < i2 + childCount) {
            View childAt = getChildAt(i3 - this.mFirstPosition);
            top = childAt.getTop();
            int bottom = childAt.getBottom();
            if (top < verticalFadingEdgeLength) {
                top = verticalFadingEdgeLength + getVerticalFadingEdgeLength();
            } else if (bottom > verticalFadingEdgeLength2) {
                top = (verticalFadingEdgeLength2 - childAt.getMeasuredHeight()) - getVerticalFadingEdgeLength();
            }
        } else {
            if (i3 >= i2) {
                int i4 = this.mItemCount;
                int i5 = i2 + childCount;
                int i6 = i5 - 1;
                int i7 = childCount - 1;
                int i8 = i7;
                int i9 = 0;
                while (true) {
                    if (i8 < 0) {
                        z = false;
                        i3 = i6;
                        top = i9;
                        break;
                    }
                    View childAt2 = getChildAt(i8);
                    int top2 = childAt2.getTop();
                    int bottom2 = childAt2.getBottom();
                    if (i8 == i7) {
                        if (i5 < i4 || bottom2 > verticalFadingEdgeLength2) {
                            verticalFadingEdgeLength2 -= getVerticalFadingEdgeLength();
                        }
                        i9 = top2;
                    }
                    if (bottom2 <= verticalFadingEdgeLength2) {
                        i3 = i2 + i8;
                        z = false;
                        top = top2;
                        break;
                    }
                    i8--;
                }
                i = -1;
                this.mResurrectToPosition = -1;
                removeCallbacks(this.mFlingRunnable);
                absPositionScroller = this.mPositionScroller;
                if (absPositionScroller != null) {
                    absPositionScroller.stop();
                }
                this.mTouchMode = -1;
                clearScrollingCache();
                this.mSpecificTop = top;
                iLookForSelectablePosition = lookForSelectablePosition(i3, z);
                if (iLookForSelectablePosition >= i2 && iLookForSelectablePosition <= getLastVisiblePosition()) {
                    this.mLayoutMode = 4;
                    updateSelectorState();
                    setSelectionInt(iLookForSelectablePosition);
                    invokeOnItemScrollListener();
                    i = iLookForSelectablePosition;
                }
                reportScrollStateChange(0);
                return i < 0;
            }
            int i10 = 0;
            int i11 = 0;
            while (true) {
                if (i10 >= childCount) {
                    top = i11;
                    i3 = i2;
                    break;
                }
                top = getChildAt(i10).getTop();
                if (i10 == 0) {
                    if (i2 > 0 || top < verticalFadingEdgeLength) {
                        verticalFadingEdgeLength += getVerticalFadingEdgeLength();
                    }
                    i11 = top;
                }
                if (top >= verticalFadingEdgeLength) {
                    i3 = i10 + i2;
                    break;
                }
                i10++;
            }
        }
        z = true;
        i = -1;
        this.mResurrectToPosition = -1;
        removeCallbacks(this.mFlingRunnable);
        absPositionScroller = this.mPositionScroller;
        if (absPositionScroller != null) {
        }
        this.mTouchMode = -1;
        clearScrollingCache();
        this.mSpecificTop = top;
        iLookForSelectablePosition = lookForSelectablePosition(i3, z);
        if (iLookForSelectablePosition >= i2) {
            this.mLayoutMode = 4;
            updateSelectorState();
            setSelectionInt(iLookForSelectablePosition);
            invokeOnItemScrollListener();
            i = iLookForSelectablePosition;
        }
        reportScrollStateChange(0);
        if (i < 0) {
        }
    }

    void confirmCheckedPositionsById() {
        ActionMode actionMode;
        MultiChoiceModeWrapper multiChoiceModeWrapper;
        this.mCheckStates.clear();
        int i = 0;
        boolean z = false;
        while (i < this.mCheckedIdStates.size()) {
            long jKeyAt = this.mCheckedIdStates.keyAt(i);
            int iIntValue = this.mCheckedIdStates.valueAt(i).intValue();
            if (jKeyAt != this.mAdapter.getItemId(iIntValue)) {
                int iMax = Math.max(0, iIntValue - 20);
                int iMin = Math.min(iIntValue + 20, this.mItemCount);
                while (true) {
                    if (iMax >= iMin) {
                        this.mCheckedIdStates.delete(jKeyAt);
                        i--;
                        this.mCheckedItemCount--;
                        ActionMode actionMode2 = this.mChoiceActionMode;
                        if (actionMode2 != null && (multiChoiceModeWrapper = this.mMultiChoiceModeCallback) != null) {
                            multiChoiceModeWrapper.onItemCheckedStateChanged(actionMode2, iIntValue, jKeyAt, false);
                        }
                        z = true;
                    } else {
                        if (jKeyAt == this.mAdapter.getItemId(iMax)) {
                            this.mCheckStates.put(iMax, true);
                            this.mCheckedIdStates.setValueAt(i, Integer.valueOf(iMax));
                            break;
                        }
                        iMax++;
                    }
                }
            } else {
                this.mCheckStates.put(iIntValue, true);
            }
            i++;
        }
        if (!z || (actionMode = this.mChoiceActionMode) == null) {
            return;
        }
        actionMode.invalidate();
    }

    @Override // android.widget.AdapterView
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
                    int height = getHeight() - getPaddingBottom();
                    View childAt = getChildAt(childCount - 1);
                    int bottom = childAt != null ? childAt.getBottom() : height;
                    if (this.mFirstPosition + childCount >= i2 && bottom <= height) {
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
                    int iFindSyncPosition = findSyncPosition();
                    if (iFindSyncPosition >= 0 && lookForSelectablePosition(iFindSyncPosition, true) == iFindSyncPosition) {
                        this.mSyncPosition = iFindSyncPosition;
                        if (this.mSyncHeight == getHeight()) {
                            this.mLayoutMode = 5;
                        } else {
                            this.mLayoutMode = 2;
                        }
                        setNextSelectedPositionInt(iFindSyncPosition);
                        return;
                    }
                }
            }
            if (!isInTouchMode()) {
                int selectedItemPosition = getSelectedItemPosition();
                int i5 = selectedItemPosition >= i ? i - 1 : selectedItemPosition;
                if (i5 < 0) {
                    i5 = 0;
                }
                if (!this.mIsForceSelection) {
                    selectedItemPosition = i5;
                }
                int iLookForSelectablePosition = lookForSelectablePosition(selectedItemPosition, true);
                if (iLookForSelectablePosition >= 0) {
                    setNextSelectedPositionInt(iLookForSelectablePosition);
                    return;
                }
                int iLookForSelectablePosition2 = lookForSelectablePosition(selectedItemPosition, false);
                if (iLookForSelectablePosition2 >= 0) {
                    setNextSelectedPositionInt(iLookForSelectablePosition2);
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
        int iWidth;
        int iHeight;
        int iWidth2;
        int i2;
        int iHeight2;
        int i3;
        if (i == 1 || i == 2) {
            iWidth = rect.right + (rect.width() / 2);
            iHeight = (rect.height() / 2) + rect.top;
            iWidth2 = rect2.left + (rect2.width() / 2);
            i2 = rect2.top;
            iHeight2 = rect2.height() / 2;
        } else {
            if (i != 17) {
                if (i == 33) {
                    iWidth = rect.left + (rect.width() / 2);
                    iHeight = rect.top;
                    iWidth2 = rect2.left + (rect2.width() / 2);
                    i3 = rect2.bottom;
                } else if (i == 66) {
                    iWidth = rect.right;
                    iHeight = (rect.height() / 2) + rect.top;
                    iWidth2 = rect2.left;
                    i2 = rect2.top;
                    iHeight2 = rect2.height() / 2;
                } else if (i == 130) {
                    iWidth = rect.left + (rect.width() / 2);
                    iHeight = rect.bottom;
                    iWidth2 = rect2.left + (rect2.width() / 2);
                    i3 = rect2.top;
                } else {
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
                }
                int i4 = iWidth2 - iWidth;
                int i5 = i3 - iHeight;
                return (i5 * i5) + (i4 * i4);
            }
            iWidth = rect.left;
            iHeight = (rect.height() / 2) + rect.top;
            iWidth2 = rect2.right;
            i2 = rect2.top;
            iHeight2 = rect2.height() / 2;
        }
        i3 = iHeight2 + i2;
        int i42 = iWidth2 - iWidth;
        int i52 = i3 - iHeight;
        return (i52 * i52) + (i42 * i42);
    }

    @Override // android.widget.AdapterView
    protected boolean isInFilterMode() {
        return this.mFiltered;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                } else {
                    if (keyEvent.getAction() == 1 && keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.mTextFilter.lambda$setTextAsync$0("");
                    }
                    z = false;
                    z2 = z;
                    z3 = false;
                }
                z = true;
                z2 = z;
                z3 = false;
            } else {
                z = false;
                z2 = z;
                z3 = false;
            }
        } else if (i == 62) {
            z3 = this.mFiltered;
            z2 = false;
        } else if (i == 66) {
            z3 = false;
            z2 = false;
        } else if (i != 111) {
            if (i != 160) {
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
        }
        if (z3) {
            createTextFilter(true);
            KeyEvent keyEventChangeTimeRepeat = keyEvent.getRepeatCount() > 0 ? KeyEvent.changeTimeRepeat(keyEvent, keyEvent.getEventTime(), 0) : keyEvent;
            int action = keyEvent.getAction();
            if (action == 0) {
                boolean zOnKeyDown = this.mTextFilter.onKeyDown(i, keyEventChangeTimeRepeat);
                if (i == 59 || i == 60) {
                    this.mIsShiftkeyPressed = true;
                    return zOnKeyDown;
                }
                if (i != 113 && i != 114) {
                    return zOnKeyDown;
                }
                this.mIsCtrlkeyPressed = true;
                return zOnKeyDown;
            }
            if (action == 1) {
                boolean zOnKeyUp = this.mTextFilter.onKeyUp(i, keyEventChangeTimeRepeat);
                if (i == 59 || i == 60) {
                    this.mIsShiftkeyPressed = false;
                    this.mOldKeyCode = 0;
                    this.mCurrentKeyCode = 0;
                    this.mFirstPressedPoint = -1;
                    return zOnKeyUp;
                }
                if (i != 113 && i != 114) {
                    return zOnKeyUp;
                }
                this.mIsCtrlkeyPressed = false;
                return zOnKeyUp;
            }
            if (action == 2) {
                return this.mTextFilter.onKeyMultiple(i, i2, keyEvent);
            }
        }
        return z2;
    }

    @Override // android.view.View
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
                this.mTarget = AbsListView.this.getTextFilterInput().onCreateInputConnection(this.mOutAttrs);
            }
            return this.mTarget;
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean reportFullscreenMode(boolean z) {
            return AbsListView.this.mDefInputConnection.reportFullscreenMode(z);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean performEditorAction(int i) {
            if (i != 6) {
                return false;
            }
            InputMethodManager inputMethodManager = (InputMethodManager) AbsListView.this.getContext().getSystemService(InputMethodManager.class);
            if (inputMethodManager == null) {
                return true;
            }
            inputMethodManager.hideSoftInputFromWindow(AbsListView.this.getWindowToken(), 0);
            return true;
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean sendKeyEvent(KeyEvent keyEvent) {
            return AbsListView.this.mDefInputConnection.sendKeyEvent(keyEvent);
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
        public SurroundingText getSurroundingText(int i, int i2, int i3) {
            InputConnection inputConnection = this.mTarget;
            if (inputConnection == null) {
                return null;
            }
            return inputConnection.getSurroundingText(i, i2, i3);
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
        public boolean requestCursorUpdates(int i, int i2) {
            return getTarget().requestCursorUpdates(i, i2);
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

    public boolean hasTextFilter() {
        return this.mFiltered;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
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
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (isTextFilterEnabled()) {
            createTextFilter(true);
            int length = charSequence.length();
            boolean zIsShowing = this.mPopup.isShowing();
            if (!zIsShowing && length > 0) {
                showPopup();
                this.mFiltered = true;
            } else if (zIsShowing && length == 0) {
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
    public void onFilterComplete(int i) throws Resources.NotFoundException {
        if (this.mSelectedPosition >= 0 || i <= 0) {
            return;
        }
        this.mResurrectToPosition = -1;
        resurrectSelection();
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2, 0);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void setTranscriptMode(int i) {
        this.mTranscriptMode = i;
    }

    public int getTranscriptMode() {
        return this.mTranscriptMode;
    }

    @Override // android.view.View
    public int getSolidColor() {
        return this.mCacheColorHint;
    }

    public void setCacheColorHint(int i) {
        if (i != this.mCacheColorHint) {
            this.mCacheColorHint = i;
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                getChildAt(i2).setDrawingCacheBackgroundColor(i);
            }
            this.mRecycler.setCacheColorHint(i);
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public int getCacheColorHint() {
        return this.mCacheColorHint;
    }

    public void reclaimViews(List<View> list) {
        int childCount = getChildCount();
        RecyclerListener recyclerListener = this.mRecycler.mRecyclerListener;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams != null && this.mRecycler.shouldRecycleViewType(layoutParams.viewType)) {
                list.add(childAt);
                childAt.setAccessibilityDelegate(null);
                childAt.resetSubtreeAutofillIds();
                if (recyclerListener != null) {
                    recyclerListener.onMovedToScrapHeap(childAt);
                }
            }
        }
        this.mRecycler.reclaimScrapViews(list);
        removeAllViewsInLayout();
    }

    private void finishGlows() {
        if (shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.finish();
            this.mEdgeGlowBottom.finish();
        }
    }

    public void setRemoteViewsAdapter(Intent intent) throws Resources.NotFoundException {
        setRemoteViewsAdapter(intent, false);
    }

    public Runnable setRemoteViewsAdapterAsync(Intent intent) {
        return new RemoteViewsAdapter.AsyncRemoteAdapterAction(this, intent);
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void setRemoteViewsAdapter(Intent intent, boolean z) throws Resources.NotFoundException {
        if (this.mRemoteAdapter != null && new Intent.FilterComparison(intent).equals(new Intent.FilterComparison(this.mRemoteAdapter.getRemoteViewsServiceIntent()))) {
            Log.i(TAG, "Equals previous remoteAadpter");
            return;
        }
        boolean z2 = this.mAllowDeferNotifyAfterRemoteViewsAdapterSet;
        this.mDeferNotifyDataSetChanged = z2;
        if (z2) {
            Log.i(TAG, "AppWidget deferNotify enabled");
        }
        RemoteViewsAdapter remoteViewsAdapter = new RemoteViewsAdapter(getContext(), intent, this, z);
        this.mRemoteAdapter = remoteViewsAdapter;
        if (remoteViewsAdapter.isDataReady()) {
            setAdapter((ListAdapter) this.mRemoteAdapter);
        }
    }

    public void setRemoteViewsInteractionHandler(RemoteViews.InteractionHandler interactionHandler) {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.setRemoteViewsInteractionHandler(interactionHandler);
        }
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void deferNotifyDataSetChanged() {
        this.mDeferNotifyDataSetChanged = true;
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public boolean onRemoteAdapterConnected() throws Resources.NotFoundException {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteAdapter;
        if (remoteViewsAdapter == this.mAdapter) {
            if (remoteViewsAdapter == null) {
                return false;
            }
            remoteViewsAdapter.superNotifyDataSetChanged();
            return true;
        }
        setAdapter((ListAdapter) remoteViewsAdapter);
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

    void setVisibleRangeHint(int i, int i2) {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.setVisibleRangeHint(i, i2);
        }
    }

    public void setEdgeEffectColor(int i) {
        setTopEdgeEffectColor(i);
        setBottomEdgeEffectColor(i);
    }

    public void setBottomEdgeEffectColor(int i) {
        this.mEdgeGlowBottom.setColor(i);
        invalidateEdgeEffects();
    }

    public void setTopEdgeEffectColor(int i) {
        this.mEdgeGlowTop.setColor(i);
        invalidateEdgeEffects();
    }

    public int getTopEdgeEffectColor() {
        return this.mEdgeGlowTop.getColor();
    }

    public int getBottomEdgeEffectColor() {
        return this.mEdgeGlowBottom.getColor();
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.mRecycler.mRecyclerListener = recyclerListener;
    }

    @Override // android.view.View
    public void onProvideContentCaptureStructure(ViewStructure viewStructure, int i) {
        super.onProvideContentCaptureStructure(viewStructure, i);
        if (sContentCaptureReportingEnabledByDeviceConfig) {
            Bundle extras = viewStructure.getExtras();
            if (extras == null) {
                Log.wtf(TAG, "Unexpected null extras Bundle in ViewStructure");
                return;
            }
            int childCount = getChildCount();
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>(childCount);
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                if (childAt != null) {
                    arrayList.add(childAt.getAutofillId());
                }
            }
            extras.putParcelableArrayList(ViewStructure.EXTRA_ACTIVE_CHILDREN_IDS, arrayList);
            extras.putInt(ViewStructure.EXTRA_FIRST_ACTIVE_POSITION, getFirstVisiblePosition());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportActiveViewsToContentCapture() {
        ContentCaptureSession contentCaptureSession;
        if (sContentCaptureReportingEnabledByDeviceConfig && (contentCaptureSession = getContentCaptureSession()) != null) {
            ViewStructure viewStructureNewViewStructure = contentCaptureSession.newViewStructure(this);
            onProvideContentCaptureStructure(viewStructureNewViewStructure, 0);
            contentCaptureSession.notifyViewAppeared(viewStructureNewViewStructure);
        }
    }

    class AdapterDataSetObserver extends AdapterView<ListAdapter>.AdapterDataSetObserver {
        AdapterDataSetObserver() {
            super();
        }

        @Override // android.widget.AdapterView.AdapterDataSetObserver, android.database.DataSetObserver
        public void onChanged() {
            if (AbsListView.this.mAppWidgetSnapScroll && AbsListView.this.mNeedLayoutSpecificDone && AbsListView.this.mLayoutMode == 4 && !AbsListView.this.mIsLayoutSpecificDone) {
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

        @Override // android.widget.AdapterView.AdapterDataSetObserver, android.database.DataSetObserver
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
            if (AbsListView.this.mLongPressMultiSelectionEnabled) {
                return true;
            }
            AbsListView.this.setLongClickable(false);
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
            AbsListView.this.mChoiceActionMode = null;
            AbsListView.this.clearChoices();
            AbsListView.this.mDataChanged = true;
            AbsListView.this.rememberSyncState();
            AbsListView.this.requestLayout();
            AbsListView.this.setLongClickable(true);
        }

        @Override // android.widget.AbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z) {
            this.mWrapped.onItemCheckedStateChanged(actionMode, i, j, z);
            if (AbsListView.this.getCheckedItemCount() != 0 || AbsListView.this.mSemCustomMultiChoiceMode) {
                return;
            }
            actionMode.finish();
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

        @ViewDebug.ExportedProperty(category = Slice.HINT_LIST, mapping = {@ViewDebug.IntToString(from = -1, to = "ITEM_VIEW_TYPE_IGNORE"), @ViewDebug.IntToString(from = -2, to = "ITEM_VIEW_TYPE_HEADER_OR_FOOTER")})
        int viewType;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.itemId = -1L;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.itemId = -1L;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2);
            this.itemId = -1L;
            this.viewType = i3;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.itemId = -1L;
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws IOException {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("list:viewType", this.viewType);
            viewHierarchyEncoder.addProperty("list:recycledHeaderFooter", this.recycledHeaderFooter);
            viewHierarchyEncoder.addProperty("list:forceAdd", this.forceAdd);
            viewHierarchyEncoder.addProperty("list:isEnabled", this.isEnabled);
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
                View childAt = AbsListView.this.getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams != null && layoutParams.viewType != -2) {
                    viewArr[i3] = childAt;
                    layoutParams.scrappedFromPosition = i2 + i3;
                }
            }
            if (!AbsListView.this.mReportChildrenToContentCaptureOnNextUpdate || i <= 0) {
                return;
            }
            AbsListView.this.reportActiveViewsToContentCapture();
            AbsListView.this.mReportChildrenToContentCaptureOnNextUpdate = false;
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
            int iIndexOfKey;
            if (AbsListView.this.mAdapter != null && AbsListView.this.mAdapterHasStableIds && this.mTransientStateViewsById != null) {
                long itemId = AbsListView.this.mAdapter.getItemId(i);
                View view = this.mTransientStateViewsById.get(itemId);
                this.mTransientStateViewsById.remove(itemId);
                return view;
            }
            SparseArray<View> sparseArray = this.mTransientStateViews;
            if (sparseArray == null || (iIndexOfKey = sparseArray.indexOfKey(i)) < 0) {
                return null;
            }
            View viewValueAt = this.mTransientStateViews.valueAt(iIndexOfKey);
            this.mTransientStateViews.removeAt(iIndexOfKey);
            return viewValueAt;
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
            int itemViewType = AbsListView.this.mAdapter.getItemViewType(i);
            if (itemViewType < 0) {
                return null;
            }
            if (this.mViewTypeCount == 1) {
                return retrieveFromScrap(this.mCurrentScrap, i);
            }
            ArrayList<View>[] arrayListArr = this.mScrapViews;
            if (itemViewType < arrayListArr.length) {
                return retrieveFromScrap(arrayListArr[itemViewType], i);
            }
            return null;
        }

        void addScrapView(View view, int i) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            layoutParams.scrappedFromPosition = i;
            int i2 = layoutParams.viewType;
            if (!shouldRecycleViewType(i2)) {
                if (i2 != -2) {
                    getSkippedScrap().add(view);
                    return;
                }
                return;
            }
            view.dispatchStartTemporaryDetach();
            AbsListView.this.notifyViewAccessibilityStateChangedIfNeeded(1);
            if (view.hasTransientState()) {
                if (AbsListView.this.mAdapter != null && AbsListView.this.mAdapterHasStableIds) {
                    if (this.mTransientStateViewsById == null) {
                        this.mTransientStateViewsById = new LongSparseArray<>();
                    }
                    this.mTransientStateViewsById.put(layoutParams.itemId, view);
                    return;
                } else {
                    if (!AbsListView.this.mDataChanged) {
                        if (this.mTransientStateViews == null) {
                            this.mTransientStateViews = new SparseArray<>();
                        }
                        this.mTransientStateViews.put(i, view);
                        return;
                    }
                    getSkippedScrap().add(view);
                    return;
                }
            }
            if (this.mViewTypeCount == 1) {
                this.mCurrentScrap.add(view);
            } else {
                ArrayList<View>[] arrayListArr = this.mScrapViews;
                if (arrayListArr.length > i2 && !arrayListArr[i2].contains(view)) {
                    this.mScrapViews[i2].add(view);
                }
            }
            RecyclerListener recyclerListener = this.mRecyclerListener;
            if (recyclerListener != null) {
                recyclerListener.onMovedToScrapHeap(view);
            }
        }

        private ArrayList<View> getSkippedScrap() {
            if (this.mSkippedScrap == null) {
                this.mSkippedScrap = new ArrayList<>();
            }
            return this.mSkippedScrap;
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
                        if (AbsListView.this.mAdapter != null && AbsListView.this.mAdapterHasStableIds) {
                            if (this.mTransientStateViewsById == null) {
                                this.mTransientStateViewsById = new LongSparseArray<>();
                            }
                            this.mTransientStateViewsById.put(AbsListView.this.mAdapter.getItemId(this.mFirstActivePosition + length), view);
                        } else if (!AbsListView.this.mDataChanged) {
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
                        layoutParams.scrappedFromPosition = this.mFirstActivePosition + length;
                        removeDetachedView(view, false);
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

        void fullyDetachScrapViews() {
            int i = this.mViewTypeCount;
            ArrayList<View>[] arrayListArr = this.mScrapViews;
            for (int i2 = 0; i2 < i; i2++) {
                ArrayList<View> arrayList = arrayListArr[i2];
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    View view = arrayList.get(size);
                    if (view.isTemporarilyDetached()) {
                        removeDetachedView(view, false);
                    }
                }
            }
        }

        private void pruneScrapViews() {
            int length = this.mActiveViews.length;
            int i = this.mViewTypeCount;
            ArrayList<View>[] arrayListArr = this.mScrapViews;
            for (int i2 = 0; i2 < i; i2++) {
                ArrayList<View> arrayList = arrayListArr[i2];
                int size = arrayList.size();
                while (size > length) {
                    if (AbsListView.this.mAdapter instanceof RemoteViewsAdapter) {
                        size--;
                        removeDetachedView(arrayList.remove(size), false);
                    } else {
                        size--;
                        arrayList.remove(size);
                    }
                }
            }
            SparseArray<View> sparseArray = this.mTransientStateViews;
            if (sparseArray != null) {
                int i3 = 0;
                while (i3 < sparseArray.size()) {
                    View viewValueAt = sparseArray.valueAt(i3);
                    if (!viewValueAt.hasTransientState()) {
                        removeDetachedView(viewValueAt, false);
                        sparseArray.removeAt(i3);
                        i3--;
                    }
                    i3++;
                }
            }
            LongSparseArray<View> longSparseArray = this.mTransientStateViewsById;
            if (longSparseArray != null) {
                int i4 = 0;
                while (i4 < longSparseArray.size()) {
                    View viewValueAt2 = longSparseArray.valueAt(i4);
                    if (!viewValueAt2.hasTransientState()) {
                        removeDetachedView(viewValueAt2, false);
                        longSparseArray.removeAt(i4);
                        i4--;
                    }
                    i4++;
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
            int i2 = size - 1;
            for (int i3 = i2; i3 >= 0; i3--) {
                LayoutParams layoutParams = (LayoutParams) arrayList.get(i3).getLayoutParams();
                if (AbsListView.this.mAdapterHasStableIds) {
                    if (AbsListView.this.mAdapter.getItemId(i) == layoutParams.itemId) {
                        return arrayList.remove(i3);
                    }
                } else if (layoutParams.scrappedFromPosition == i) {
                    View viewRemove = arrayList.remove(i3);
                    clearScrapForRebind(viewRemove);
                    return viewRemove;
                }
            }
            View viewRemove2 = arrayList.remove(i2);
            clearScrapForRebind(viewRemove2);
            return viewRemove2;
        }

        private void clearScrap(ArrayList<View> arrayList) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                removeDetachedView(arrayList.remove((size - 1) - i), false);
            }
        }

        private void clearScrapForRebind(View view) {
            view.clearAccessibilityFocus();
            view.setAccessibilityDelegate(null);
            view.resetSubtreeAutofillIds();
        }

        private void removeDetachedView(View view, boolean z) {
            if (view != null) {
                view.setAccessibilityDelegate(null);
                view.resetSubtreeAutofillIds();
                AbsListView.this.removeDetachedView(view, z);
                return;
            }
            Log.d(AbsListView.TAG, "removeDetachedView child is null");
        }
    }

    int getHeightForPosition(int i) {
        int firstVisiblePosition = getFirstVisiblePosition();
        int childCount = getChildCount();
        int i2 = i - firstVisiblePosition;
        if (i2 >= 0 && i2 < childCount) {
            return getChildAt(i2).getHeight();
        }
        View viewObtainView = obtainView(i, this.mIsScrap);
        viewObtainView.measure(this.mWidthMeasureSpec, 0);
        int measuredHeight = viewObtainView.getMeasuredHeight();
        this.mRecycler.addScrapView(viewObtainView, i);
        return measuredHeight;
    }

    public void setSelectionFromTop(int i, int i2) throws Resources.NotFoundException {
        if (this.mAdapter == null) {
            this.mDeferSetSelectionFromTop = true;
            this.mDeferSetSelectionPosition = i;
            return;
        }
        if (this.mAppWidgetSnapScroll) {
            semSendBroadcastPosition(i, 1);
        }
        if (this.mAppWidgetIndicator) {
            semInvalidateIndicator(i);
        }
        if (this.mSemEnableGoToTop && canScrollUp() && this.mSemGoToTopState != 1) {
            removeCallbacks(this.mSemGoToToFadeOutRunnable);
            semSetupGoToTop(-1);
            semAutoHide(1);
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
            this.mSpecificTop = this.mListPadding.top + i2;
            if (this.mNeedSync) {
                this.mSyncPosition = i;
                this.mSyncRowId = this.mAdapter.getItemId(i);
            }
            AbsPositionScroller absPositionScroller = this.mPositionScroller;
            if (absPositionScroller != null) {
                absPositionScroller.stop();
            }
            requestLayout();
            if (this.mAppWidgetSnapScroll && this.mNeedLayoutSpecificDone) {
                this.mIsLayoutSpecificDone = false;
            }
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws Resources.NotFoundException, IOException {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("drawing:cacheColorHint", getCacheColorHint());
        viewHierarchyEncoder.addProperty("list:fastScrollEnabled", isFastScrollEnabled());
        viewHierarchyEncoder.addProperty("list:scrollingCacheEnabled", isScrollingCacheEnabled());
        viewHierarchyEncoder.addProperty("list:smoothScrollbarEnabled", isSmoothScrollbarEnabled());
        viewHierarchyEncoder.addProperty("list:stackFromBottom", isStackFromBottom());
        viewHierarchyEncoder.addProperty("list:textFilterEnabled", isTextFilterEnabled());
        View selectedView = getSelectedView();
        if (selectedView != null) {
            viewHierarchyEncoder.addPropertyKey("selectedView");
            selectedView.encode(viewHierarchyEncoder);
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
        private int mOffsetFromTop;
        private int mScrollDuration;
        private int mStoredFirstPosition;
        private int mTargetPos;

        PositionScroller() {
            this.mExtraScroll = ViewConfiguration.get(AbsListView.this.mContext).getScaledFadingEdgeLength();
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void start(final int i) throws Resources.NotFoundException {
            int i2;
            stop();
            if (AbsListView.this.mDataChanged) {
                AbsListView.this.mPositionScrollAfterLayout = new Runnable() { // from class: android.widget.AbsListView.PositionScroller.1
                    @Override // java.lang.Runnable
                    public void run() throws Resources.NotFoundException {
                        PositionScroller.this.start(i);
                    }
                };
                return;
            }
            int childCount = AbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int i3 = AbsListView.this.mFirstPosition;
            int i4 = (childCount + i3) - 1;
            int iMax = Math.max(0, Math.min(AbsListView.this.getCount() - 1, i));
            if (iMax < i3) {
                i2 = (i3 - iMax) + 1;
                this.mMode = 2;
            } else if (iMax > i4) {
                i2 = (iMax - i4) + 1;
                this.mMode = 1;
            } else {
                if (AbsListView.this.mJumpScrollToTopState == AbsListView.JUMP_SCROLL_TO_TOP_INITIATED) {
                    AbsListView.this.mJumpScrollToTopState = AbsListView.JUMP_SCROLL_TO_TOP_FINISHING;
                }
                scrollToVisible(iMax, -1, 200);
                return;
            }
            if (i2 > 0) {
                this.mScrollDuration = 200 / i2;
            } else {
                this.mScrollDuration = 200;
            }
            this.mTargetPos = iMax;
            this.mBoundPos = -1;
            this.mLastSeenPos = -1;
            AbsListView.this.postOnAnimation(this);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void start(final int i, final int i2) throws Resources.NotFoundException {
            int i3;
            int i4;
            stop();
            if (i2 == -1) {
                start(i);
                return;
            }
            if (AbsListView.this.mDataChanged) {
                AbsListView.this.mPositionScrollAfterLayout = new Runnable() { // from class: android.widget.AbsListView.PositionScroller.2
                    @Override // java.lang.Runnable
                    public void run() throws Resources.NotFoundException {
                        PositionScroller.this.start(i, i2);
                    }
                };
                return;
            }
            int childCount = AbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int i5 = AbsListView.this.mFirstPosition;
            int i6 = (childCount + i5) - 1;
            int iMax = Math.max(0, Math.min(AbsListView.this.getCount() - 1, i));
            if (iMax < i5) {
                int i7 = i6 - i2;
                if (i7 < 1) {
                    return;
                }
                i4 = (i5 - iMax) + 1;
                i3 = i7 - 1;
                if (i3 < i4) {
                    this.mMode = 4;
                    i4 = i3;
                } else {
                    this.mMode = 2;
                }
            } else {
                if (iMax <= i6) {
                    scrollToVisible(iMax, i2, 200);
                    return;
                }
                int i8 = i2 - i5;
                if (i8 < 1) {
                    return;
                }
                i3 = (iMax - i6) + 1;
                i4 = i8 - 1;
                if (i4 < i3) {
                    this.mMode = 3;
                } else {
                    this.mMode = 1;
                    i4 = i3;
                }
            }
            if (i4 > 0) {
                this.mScrollDuration = 200 / i4;
            } else {
                this.mScrollDuration = 200;
            }
            this.mTargetPos = iMax;
            this.mBoundPos = i2;
            this.mLastSeenPos = -1;
            AbsListView.this.postOnAnimation(this);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void startWithOffset(int i, int i2) throws Resources.NotFoundException {
            startWithOffset(i, i2, 200);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void startWithOffset(final int i, final int i2, final int i3) throws Resources.NotFoundException {
            int i4;
            stop();
            if (AbsListView.this.mDataChanged) {
                AbsListView.this.mPositionScrollAfterLayout = new Runnable() { // from class: android.widget.AbsListView.PositionScroller.3
                    @Override // java.lang.Runnable
                    public void run() throws Resources.NotFoundException {
                        PositionScroller.this.startWithOffset(i, i2, i3);
                    }
                };
                return;
            }
            int childCount = AbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int paddingTop = i2 + AbsListView.this.getPaddingTop();
            this.mTargetPos = Math.max(0, Math.min(AbsListView.this.getCount() - 1, i));
            this.mOffsetFromTop = paddingTop;
            this.mBoundPos = -1;
            this.mLastSeenPos = -1;
            this.mMode = 5;
            int i5 = AbsListView.this.mFirstPosition;
            int i6 = (i5 + childCount) - 1;
            int i7 = this.mTargetPos;
            if (i7 < i5) {
                i4 = i5 - i7;
            } else {
                if (i7 <= i6) {
                    AbsListView.this.smoothScrollBy(AbsListView.this.getChildAt(i7 - i5).getTop() - paddingTop, i3, true, false);
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
            AbsListView.this.postOnAnimation(this);
        }

        private void scrollToVisible(int i, int i2, int i3) throws Resources.NotFoundException {
            int i4 = AbsListView.this.mFirstPosition;
            int childCount = (AbsListView.this.getChildCount() + i4) - 1;
            int i5 = AbsListView.this.mListPadding.top;
            int height = AbsListView.this.getHeight() - AbsListView.this.mListPadding.bottom;
            if (i < i4 || i > childCount) {
                Log.w(AbsListView.TAG, "scrollToVisible called with targetPos " + i + " not visible [" + i4 + ", " + childCount + NavigationBarInflaterView.SIZE_MOD_END);
            }
            if (i2 < i4 || i2 > childCount) {
                i2 = -1;
            }
            View childAt = AbsListView.this.getChildAt(i - i4);
            int top = childAt.getTop();
            int bottom = childAt.getBottom();
            int iMin = bottom > height ? bottom - height : 0;
            if (top < i5) {
                iMin = top - i5;
            }
            if (iMin == 0) {
                if (AbsListView.this.mJumpScrollToTopState == AbsListView.JUMP_SCROLL_TO_TOP_FINISHING) {
                    AbsListView.this.mJumpScrollToTopState = AbsListView.JUMP_SCROLL_TO_TOP_IDLE;
                    AbsListView.this.postOnJumpScrollToFinished();
                    return;
                }
                return;
            }
            if (i2 >= 0) {
                View childAt2 = AbsListView.this.getChildAt(i2 - i4);
                int top2 = childAt2.getTop();
                int bottom2 = childAt2.getBottom();
                int iAbs = Math.abs(iMin);
                if (iMin < 0 && bottom2 + iAbs > height) {
                    iMin = Math.max(0, bottom2 - height);
                } else if (iMin > 0 && top2 - iAbs < i5) {
                    iMin = Math.min(0, top2 - i5);
                }
            }
            AbsListView.this.smoothScrollBy(iMin, i3);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void stop() {
            AbsListView.this.removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public void run() throws Resources.NotFoundException {
            int height = AbsListView.this.getHeight();
            int i = AbsListView.this.mFirstPosition;
            int i2 = this.mMode;
            if (i2 == 1) {
                int childCount = AbsListView.this.getChildCount() - 1;
                int i3 = i + childCount;
                if (childCount < 0) {
                    return;
                }
                if (i3 == this.mLastSeenPos) {
                    AbsListView.this.postOnAnimation(this);
                    return;
                }
                View childAt = AbsListView.this.getChildAt(childCount);
                AbsListView.this.smoothScrollBy((childAt.getHeight() - (height - childAt.getTop())) + (i3 < AbsListView.this.mItemCount - 1 ? Math.max(AbsListView.this.mListPadding.bottom + (AbsListView.this.mHasDivier ? AbsListView.this.mHasDividerHeight : 0) + 1, this.mExtraScroll) : AbsListView.this.mListPadding.bottom), this.mScrollDuration, true, i3 < this.mTargetPos);
                this.mLastSeenPos = i3;
                if (i3 < this.mTargetPos) {
                    AbsListView.this.postOnAnimation(this);
                    return;
                }
                return;
            }
            if (i2 == 2) {
                if (i == this.mLastSeenPos) {
                    if (AbsListView.this.semIsSupportGotoTop() && AbsListView.this.mSemCanGoFuther && AbsListView.this.mFirstPosition > 0 && this.mStoredFirstPosition == i) {
                        AbsListView.this.smoothScrollToPositionFromTop(0, 0, 0);
                    }
                    this.mStoredFirstPosition = i;
                    AbsListView.this.postOnAnimation(this);
                    return;
                }
                View childAt2 = AbsListView.this.getChildAt(0);
                if (childAt2 == null) {
                    return;
                }
                AbsListView.this.smoothScrollBy(childAt2.getTop() - (i > 0 ? Math.max((AbsListView.this.mListPadding.top + (AbsListView.this.mHasDivier ? AbsListView.this.mHasDividerHeight : 0)) + 1, this.mExtraScroll) : AbsListView.this.mListPadding.top), this.mScrollDuration, true, i > this.mTargetPos);
                this.mLastSeenPos = i;
                if (i > this.mTargetPos) {
                    AbsListView.this.postOnAnimation(this);
                    return;
                } else {
                    if (AbsListView.this.mJumpScrollToTopState == AbsListView.JUMP_SCROLL_TO_TOP_INITIATED) {
                        AbsListView.this.mJumpScrollToTopState = AbsListView.JUMP_SCROLL_TO_TOP_FINISHING;
                        return;
                    }
                    return;
                }
            }
            if (i2 == 3) {
                int childCount2 = AbsListView.this.getChildCount();
                if (i == this.mBoundPos || childCount2 <= 1 || childCount2 + i >= AbsListView.this.mItemCount) {
                    AbsListView.this.reportScrollStateChange(0);
                    return;
                }
                int i4 = i + 1;
                if (i4 == this.mLastSeenPos) {
                    AbsListView.this.postOnAnimation(this);
                    return;
                }
                View childAt3 = AbsListView.this.getChildAt(1);
                int height2 = childAt3.getHeight();
                int top = childAt3.getTop();
                int iMax = Math.max(AbsListView.this.mListPadding.bottom, this.mExtraScroll);
                if (i4 < this.mBoundPos) {
                    AbsListView.this.smoothScrollBy(Math.max(0, (height2 + top) - iMax), this.mScrollDuration, true, true);
                    this.mLastSeenPos = i4;
                    AbsListView.this.postOnAnimation(this);
                    return;
                } else if (top > iMax) {
                    AbsListView.this.smoothScrollBy(top - iMax, this.mScrollDuration, true, false);
                    return;
                } else {
                    AbsListView.this.reportScrollStateChange(0);
                    return;
                }
            }
            if (i2 == 4) {
                int childCount3 = AbsListView.this.getChildCount() - 2;
                if (childCount3 < 0) {
                    return;
                }
                int i5 = i + childCount3;
                if (i5 == this.mLastSeenPos) {
                    AbsListView.this.postOnAnimation(this);
                    return;
                }
                View childAt4 = AbsListView.this.getChildAt(childCount3);
                int height3 = childAt4.getHeight();
                int top2 = childAt4.getTop();
                int i6 = height - top2;
                int iMax2 = Math.max(AbsListView.this.mListPadding.top, this.mExtraScroll);
                this.mLastSeenPos = i5;
                if (i5 > this.mBoundPos) {
                    AbsListView.this.smoothScrollBy(-(i6 - iMax2), this.mScrollDuration, true, true);
                    AbsListView.this.postOnAnimation(this);
                    return;
                }
                int i7 = height - iMax2;
                int i8 = top2 + height3;
                if (i7 > i8) {
                    AbsListView.this.smoothScrollBy(-(i7 - i8), this.mScrollDuration, true, false);
                    return;
                } else {
                    AbsListView.this.reportScrollStateChange(0);
                    return;
                }
            }
            if (i2 != 5) {
                return;
            }
            this.mLastSeenPos = i;
            int childCount4 = AbsListView.this.getChildCount();
            if (childCount4 <= 0) {
                return;
            }
            int i9 = this.mTargetPos;
            int i10 = (i + childCount4) - 1;
            int height4 = AbsListView.this.getChildAt(0).getHeight();
            int height5 = AbsListView.this.getChildAt(childCount4 - 1).getHeight();
            float f = height4;
            float f2 = 0.0f;
            float top3 = f == 0.0f ? 1.0f : (height4 + r6.getTop()) / f;
            float f3 = height5;
            float height6 = f3 == 0.0f ? 1.0f : ((height5 + AbsListView.this.getHeight()) - r8.getBottom()) / f3;
            if (i9 < i) {
                f2 = (i - i9) + (1.0f - top3) + 1.0f;
            } else if (i9 > i10) {
                f2 = (i9 - i10) + (1.0f - height6);
            }
            float fMin = Math.min(Math.abs(f2 / childCount4), 1.0f);
            if (i9 < i) {
                AbsListView.this.smoothScrollBy((int) ((-AbsListView.this.getHeight()) * fMin), (int) (this.mScrollDuration * fMin), true, true);
                AbsListView.this.postOnAnimation(this);
            } else if (i9 > i10) {
                AbsListView.this.smoothScrollBy((int) (AbsListView.this.getHeight() * fMin), (int) (this.mScrollDuration * fMin), true, true);
                AbsListView.this.postOnAnimation(this);
            } else {
                AbsListView.this.smoothScrollBy(AbsListView.this.getChildAt(i9 - i).getTop() - this.mOffsetFromTop, (int) (this.mScrollDuration * (Math.abs(r0) / AbsListView.this.getHeight())), true, false);
            }
        }
    }

    public void setOnScrollOffsetListener(OnScrollOffsetListener onScrollOffsetListener) {
        this.mOnScrollOffsetListener = onScrollOffsetListener;
    }

    static class ClickableViewState {
        private boolean mIsFocused;
        private final View mView;
        private boolean mWasFocused;

        public ClickableViewState(View view, boolean z) {
            this.mView = view;
            this.mWasFocused = z;
        }

        void setWasFocused(boolean z) {
            this.mWasFocused = z;
        }

        boolean getWasFocused() {
            return this.mWasFocused;
        }

        void setIsFocused(boolean z) {
            this.mIsFocused = z;
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
        MultiChoiceModeWrapper multiChoiceModeWrapper;
        if (this.mChoiceMode != 3 || (multiChoiceModeWrapper = this.mMultiChoiceModeCallback) == null) {
            return;
        }
        this.mChoiceActionMode = startActionMode(multiChoiceModeWrapper);
    }

    @Deprecated
    public void semFinishMultiChoiceMode() {
        ActionMode actionMode = this.mChoiceActionMode;
        if (actionMode != null) {
            actionMode.finish();
            this.mChoiceActionMode = null;
        }
    }

    public void semSetCustomMultiChoiceModeEnabled(boolean z) {
        this.mSemCustomMultiChoiceMode = z;
    }

    private void onHoverDrawableState(MotionEvent motionEvent) {
        Drawable drawable;
        int action = motionEvent.getAction();
        int toolType = motionEvent.getToolType(0);
        int flags = motionEvent.getFlags();
        if ((action == 7 || action == 9) && toolType == 2) {
            this.mIsPenHovered = true;
        } else if (action == 10) {
            this.mIsPenHovered = false;
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        this.mIsHoveredByMouse = viewRootImpl != null && viewRootImpl.isDesktopMode() && (toolType == 3 || (67108864 & flags) != 0);
        if (this.mAdapter == null || (drawable = this.mSelector) == null || !drawable.isStateful() || this.mHoverAreaEnter || !this.mIsHoveredByMouse) {
            return;
        }
        if (!isInTouchMode()) {
            hideSelector();
            updateSelectorState();
        }
        if (!isHovered() && this.mIsHoverScrolled) {
            setHovered(true);
            this.mIsHoverScrolled = false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int iPointToPosition = pointToPosition(x, y);
        boolean zShouldShowSelector = shouldShowSelector();
        if (this.mSemGoToTopState != 0 && semIsSupportGotoTop() && this.mSemGoToTopRect.contains(x, y)) {
            this.mSelectorPosition = -1;
            this.mSelectorRect.setEmpty();
            return;
        }
        if (!isHovered() && !isInTouchMode()) {
            this.mSelector.setState(StateSet.NOTHING);
        }
        if (iPointToPosition < 0) {
            if (!zShouldShowSelector) {
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
        this.mHoverPosition = iPointToPosition;
        View childAt = getChildAt(iPointToPosition - this.mFirstPosition);
        if (this.mAdapter.isEnabled(this.mHoverPosition) && this.mIsHoveredByMouse) {
            positionSelector(this.mHoverPosition, childAt);
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

    private static class HoverScrollHandler extends Handler {
        private final WeakReference<AbsListView> mListView;

        HoverScrollHandler(AbsListView absListView) {
            this.mListView = new WeakReference<>(absListView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws Resources.NotFoundException {
            AbsListView absListView = this.mListView.get();
            if (absListView != null) {
                absListView.handleMessage(message);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:122:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x012f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleMessage(Message message) throws Resources.NotFoundException {
        double d;
        double d2;
        int i;
        if (message.what != 1) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = (jCurrentTimeMillis - this.mHoverRecognitionStartTime) / 1000;
        boolean z = this.mIsPenHovered;
        if (z && jCurrentTimeMillis - this.mHoverScrollStartTime < this.mHoverScrollTimeInterval) {
            return;
        }
        if (this.mIsPenPressed && jCurrentTimeMillis - this.mHoverScrollStartTime < this.mPenDragScrollTimeInterval) {
            return;
        }
        if (z && !this.mIsSendHoverScrollState) {
            OnScrollListener onScrollListener = this.mOnScrollListener;
            if (onScrollListener != null) {
                this.mHoverScrollStateForListener = 1;
                onScrollListener.onScrollStateChanged(this, 1);
            }
            this.mIsSendHoverScrollState = true;
            this.mIsHoverScrolled = true;
        }
        int childCount = getChildCount();
        boolean z2 = false;
        boolean z3 = this.mFirstPosition + childCount < this.mItemCount;
        if (!z3 && childCount > 0) {
            View childAt = getChildAt(childCount - 1);
            z3 = childAt.getBottom() > this.mBottom - this.mListPadding.bottom || childAt.getBottom() > getHeight() - this.mListPadding.bottom;
        }
        boolean z4 = this.mFirstPosition > 0;
        if (!z4 && getChildCount() > 0) {
            z4 = getChildAt(0).getTop() < this.mListPadding.top;
        }
        int iApplyDimension = (int) (TypedValue.applyDimension(1, HOVERSCROLL_SPEED, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
        if (j == 3) {
            d = iApplyDimension;
            d2 = 0.1d;
        } else {
            if (j != 4) {
                if (j >= 5) {
                    d = iApplyDimension;
                    d2 = 0.3d;
                }
                i = this.mHoverScrollDirection;
                if (i != 2) {
                    iApplyDimension *= -1;
                    if ((this.mSemTrackedChild == null && this.mSemCloseChildByBottom != null) || (this.mOldHoverScrollDirection != i && this.mIsCloseChildSetted)) {
                        this.mSemTrackedChild = this.mSemCloseChildByBottom;
                        this.mSemDistanceFromTrackedChildTop = this.mSemDistanceFromCloseChildBottom;
                        this.mSemTrackedChildPosition = this.mSemCloseChildPositionByBottom;
                        this.mOldHoverScrollDirection = i;
                        this.mIsCloseChildSetted = true;
                    }
                } else if ((this.mSemTrackedChild == null && this.mSemCloseChildByTop != null) || (this.mOldHoverScrollDirection != i && this.mIsCloseChildSetted)) {
                    this.mSemTrackedChild = this.mSemCloseChildByTop;
                    this.mSemDistanceFromTrackedChildTop = this.mSemDistanceFromCloseChildTop;
                    this.mSemTrackedChildPosition = this.mSemCloseChildPositionByTop;
                    this.mOldHoverScrollDirection = i;
                    this.mIsCloseChildSetted = true;
                }
                if (getChildAt(getChildCount() - 1) != null) {
                    return;
                }
                if ((iApplyDimension < 0 && z4) || (iApplyDimension > 0 && z3)) {
                    smoothScrollBy(iApplyDimension, 0);
                    if (this.mIsLongPressMultiSelection) {
                        updateLongPressMultiSelection(this.mSemDragEndX, this.mSemDragEndY, false);
                    }
                    this.mHoverHandler.sendEmptyMessageDelayed(1, 0L);
                    return;
                }
                int overScrollMode = getOverScrollMode();
                if (overScrollMode == 0 || (overScrollMode == 1 && !contentFits())) {
                    z2 = true;
                }
                if (z2 && !this.mIsHoverOverscrolled) {
                    int i2 = this.mHoverScrollDirection;
                    if (i2 == 2) {
                        this.mEdgeGlowTop.setSize(getWidth(), getHeight());
                        this.mEdgeGlowTop.onAbsorb(10000);
                        if (!this.mEdgeGlowBottom.isFinished()) {
                            this.mEdgeGlowBottom.onRelease();
                        }
                    } else if (i2 == 1) {
                        this.mEdgeGlowBottom.setSize(getWidth(), getHeight());
                        this.mEdgeGlowBottom.onAbsorb(10000);
                        semSetupGoToTop(1);
                        semAutoHide(1);
                        if (!this.mEdgeGlowTop.isFinished()) {
                            this.mEdgeGlowTop.onRelease();
                        }
                    }
                    if (!this.mEdgeGlowTop.isFinished() || !this.mEdgeGlowBottom.isFinished()) {
                        invalidate();
                    }
                    this.mIsHoverOverscrolled = true;
                }
                if (z2 || this.mIsHoverOverscrolled) {
                    return;
                }
                this.mIsHoverOverscrolled = true;
                return;
            }
            d = iApplyDimension;
            d2 = 0.2d;
        }
        iApplyDimension += (int) (d * d2);
        i = this.mHoverScrollDirection;
        if (i != 2) {
        }
        if (getChildAt(getChildCount() - 1) != null) {
        }
    }

    private void showPointerIcon(MotionEvent motionEvent, int i) {
        semSetPointerIcon(motionEvent.getToolType(0), PointerIcon.getSystemIcon(this.mContext, i));
    }

    public void semSetHoverScrollEnabled(boolean z) {
        this.mHoverScrollEnable = z;
        this.mHoverScrollStateChanged = true;
    }

    public void setEnablePaddingInHoverScroll(boolean z) {
        this.mIsEnabledPaddingInHoverScroll = z;
    }

    public void addExtraPaddingInTopHoverArea(int i) {
        this.mExtraPaddingInTopHoverArea = (int) (TypedValue.applyDimension(1, i, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
    }

    public void addExtraPaddingInBottomHoverArea(int i) {
        this.mExtraPaddingInBottomHoverArea = (int) (TypedValue.applyDimension(1, i, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
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

    public void setMultiFocusListItem(int i, int i2) {
        if (this.mSemPressItemListArray == null) {
            return;
        }
        resetPressItemListArray();
        addToPressItemListArray(i, i2);
    }

    public void semSetMultiFocusEnabled(boolean z) {
        this.mIsMultiFocusEnabled = z;
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

    public void semSetupGoToTop(int i) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int i2;
        int i3;
        if (semIsTalkBackIsRunning() || !this.mSemEnableGoToTop) {
            return;
        }
        removeCallbacks(this.mSemAutoHide);
        if (i == 1 && !canScrollUp()) {
            i = 0;
        }
        if (i == -1 && this.mSemSizeChnage) {
            i = (canScrollUp() || canScrollDown()) ? this.mSemGoToTopLastState : 0;
        } else if (i == -1 && (canScrollUp() || canScrollDown())) {
            i = 1;
        }
        if (i != 0) {
            removeCallbacks(this.mSemGoToToFadeOutRunnable);
        } else if (i != 1) {
            removeCallbacks(this.mSemGoToToFadeInRunnable);
        }
        if (this.mShowFadeOutGTP == 0 && i == 0 && this.mSemGoToTopLastState != 0) {
            post(this.mSemGoToToFadeOutRunnable);
        }
        if (i != 2) {
            this.mSemGoToTopImage.setState(StateSet.NOTHING);
        }
        this.mSemGoToTopState = i;
        int width = getWidth();
        int height = getHeight();
        int i4 = this.mPaddingLeft + (((width - this.mPaddingLeft) - this.mPaddingRight) / 2);
        int[] iArr = {0, 0};
        getLocationInWindow(iArr);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int rotation = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        boolean z = rotation == 1 || rotation == 3;
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        int i5 = z ? rect.left : 0;
        int i6 = z ? rect.right : displayMetrics.widthPixels;
        int i7 = iArr[0];
        if (i7 < i5 && !this.mAppWidgetGoToTop && (i3 = -i7) > this.mPaddingLeft) {
            i4 += (i3 - this.mPaddingLeft) / 2;
        }
        int i8 = iArr[0];
        if (i8 + width > i6 && !this.mAppWidgetGoToTop && (i2 = (i8 + width) - displayMetrics.widthPixels) > this.mPaddingRight) {
            i4 -= (i2 - this.mPaddingRight) / 2;
        }
        if (i != 0) {
            if (i == 1 || i == 2) {
                removeCallbacks(this.mSemGoToToFadeOutRunnable);
                int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_go_to_top_scrollableview_size);
                int dimensionPixelSize3 = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_go_to_top_scrollableview_size);
                if (this.mAppWidgetGoToTop) {
                    dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_go_to_top_scrollableview_gap_appwidget) + this.mAppWidgetGoToTopOffset;
                } else {
                    dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_go_to_top_scrollableview_gap);
                }
                int i9 = dimensionPixelSize2 / 2;
                this.mSemGoToTopRect.set(i4 - i9, (height - dimensionPixelSize3) - dimensionPixelSize, i4 + i9, height - dimensionPixelSize);
            }
        } else if (this.mShowFadeOutGTP == 2) {
            this.mSemGoToTopRect.set(0, 0, 0, 0);
        }
        if (this.mShowFadeOutGTP == 2) {
            this.mShowFadeOutGTP = 0;
        }
        this.mSemGoToTopImage.setBounds(this.mSemGoToTopRect);
        if (i == 1 && (this.mSemGoToTopLastState == 0 || this.mSemGoToTopImage.getAlpha() == 0 || this.mSemSizeChnage)) {
            post(this.mSemGoToToFadeInRunnable);
        }
        this.mSemSizeChnage = false;
        this.mSemGoToTopLastState = this.mSemGoToTopState;
        this.mOutline.setOval(0, 0, this.mSemGoToTopRect.width(), this.mSemGoToTopRect.height());
        this.mGoToTopRenderNode.setPosition(this.mSemGoToTopRect);
        this.mGoToTopRenderNode.setClipToBounds(false);
    }

    private void drawGoToTop(Canvas canvas) throws Resources.NotFoundException {
        int i = this.mScrollY;
        int iSave = canvas.save();
        canvas.translate(0.0f, i);
        if (!canScrollUp() && this.mSemGoToTopState != 0) {
            semSetupGoToTop(0);
        }
        if (!this.mSemGoToTopRect.isEmpty()) {
            if (canvas.isHardwareAccelerated()) {
                canvas.enableZ();
                float alpha = this.mSemGoToTopImage.getAlpha() / 255.0f;
                RecordingCanvas recordingCanvasBeginRecording = this.mGoToTopRenderNode.beginRecording();
                this.mOutline.setAlpha(alpha);
                this.mGoToTopRenderNode.setOutline(this.mOutline);
                this.mGoToTopRenderNode.setAlpha(alpha);
                recordingCanvasBeginRecording.drawBitmap(this.mSemGoToTopBitmap, 0.0f, 0.0f, (Paint) null);
                canvas.drawRenderNode(this.mGoToTopRenderNode);
                this.mGoToTopRenderNode.endRecording();
                canvas.disableZ();
            } else {
                this.mSemGoToTopImage.draw(canvas);
            }
        }
        canvas.restoreToCount(iSave);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public void semSetGoToTopEnabledForAppWidget(boolean z) throws Resources.NotFoundException {
        this.mAppWidgetGoToTop = z;
        semSetGoToTopEnabled(z, 1);
    }

    public void semAllowDeferNotifyAfterRemoteViewsAdapterSet(boolean z) {
        Log.i(TAG, "Allow notify after RemoteViewsAdapter set " + z);
        this.mAllowDeferNotifyAfterRemoteViewsAdapterSet = z;
    }

    public void semSetGoToTopOffsetForAppWidget(int i) {
        this.mAppWidgetGoToTopOffset = i;
    }

    public void semSetGoToTopEnabled(boolean z) throws Resources.NotFoundException {
        semSetGoToTopEnabled(z, 1);
    }

    public void semSetGoToTopEnabled(boolean z, int i) throws Resources.NotFoundException {
        Drawable drawable;
        Drawable drawable2;
        if (this.mAppWidgetGoToTop) {
            if ((getResources().getConfiguration().uiMode & 48) == 32) {
                drawable2 = getResources().getDrawable(R.drawable.sem_list_go_to_top_dark_appwidget);
            } else {
                drawable2 = getResources().getDrawable(R.drawable.sem_list_go_to_top_light_appwidget);
            }
            this.mSemGoToTopImage = drawable2;
        } else {
            if (i == 0) {
                drawable = this.mSemGoToTopLightImage;
            } else {
                drawable = this.mContext.getResources().getDrawable(R.drawable.sem_list_go_to_top_dark);
            }
            this.mSemGoToTopImage = drawable;
        }
        Drawable drawable3 = this.mSemGoToTopImage;
        if (drawable3 != null) {
            this.mSemEnableGoToTop = z;
            if (drawable3.getAlpha() != 255) {
                this.mSemGoToTopImage.setAlpha(255);
            }
            this.mSemGoToTopBitmap = drawableToBitmap(this.mSemGoToTopImage);
            this.mSemGoToTopImage.setAlpha(0);
            if (z) {
                this.mSemGoToTopImage.setCallback(this);
            } else {
                this.mSemGoToTopImage.setCallback(null);
            }
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 255);
            this.mSemGoToTopFadeInAnimator = valueAnimatorOfInt;
            valueAnimatorOfInt.setDuration(333L);
            this.mSemGoToTopFadeInAnimator.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
            this.mSemGoToTopFadeInAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.AbsListView.8
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    if (AbsListView.this.mAppWidgetGoToTop) {
                        Rect bounds = AbsListView.this.mSemGoToTopImage.getBounds();
                        if (bounds.left == 0 || bounds.top == 0) {
                            Log.w(AbsListView.TAG, "Hide GotoTop immediatley left: " + bounds.left + " top" + bounds.top);
                            AbsListView.this.semPlayGotoTopHideImmediatley();
                            iIntValue = 0;
                        }
                    }
                    AbsListView.this.mSemGoToTopImage.setAlpha(iIntValue);
                    AbsListView.this.invalidate();
                }
            });
            ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(255, 0);
            this.mSemGoToTopFadeOutAnimator = valueAnimatorOfInt2;
            valueAnimatorOfInt2.setDuration(333L);
            this.mSemGoToTopFadeOutAnimator.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
            this.mSemGoToTopFadeOutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.AbsListView.9
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    AbsListView.this.mSemGoToTopImage.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    AbsListView.this.invalidate();
                }
            });
            this.mSemGoToTopFadeOutAnimator.addListener(new Animator.AnimatorListener() { // from class: android.widget.AbsListView.10
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    AbsListView.this.mShowFadeOutGTP = 1;
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) throws Resources.NotFoundException {
                    AbsListView.this.mShowFadeOutGTP = 2;
                    AbsListView.this.semSetupGoToTop(0);
                }
            });
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sem_go_to_top_elevation);
            RenderNode renderNode = new RenderNode("goToTop");
            this.mGoToTopRenderNode = renderNode;
            renderNode.setElevation(dimensionPixelSize);
        }
    }

    public void semAutoHide(int i) {
        if (this.mSemEnableGoToTop) {
            if (i == 0) {
                if (semIsFastScrollEnabled()) {
                    return;
                }
                removeCallbacks(this.mSemAutoHide);
                postDelayed(this.mSemAutoHide, this.GO_TO_TOP_HIDE);
                return;
            }
            if (i == 1) {
                removeCallbacks(this.mSemAutoHide);
                postDelayed(this.mSemAutoHide, this.GO_TO_TOP_HIDE);
            }
        }
    }

    public void semShowGoToTOP() throws Resources.NotFoundException {
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

    public void semSetSmoothScrollEnabled(boolean z) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        this.mFlingRunnable.mScroller.semSetSmoothScrollEnabled(z);
    }

    @Deprecated
    public void semSetFastScrollEventListener(SemFastScrollEventListener semFastScrollEventListener) {
        this.mSemFastScrollEventListener = semFastScrollEventListener;
    }

    public void semSetFastScrollEnabledForAppWidget(boolean z) {
        this.mAppWidgetFastScroll = z;
        semSetFastScrollEnabled(z);
    }

    public void semSetFastScrollEnabled(final boolean z) {
        if (this.mFastScrollEnabled != z) {
            this.mFastScrollEnabled = z;
            if (isOwnerThread()) {
                semSetFastScrollEnabledUiThread(z);
            } else {
                post(new Runnable() { // from class: android.widget.AbsListView.11
                    @Override // java.lang.Runnable
                    public void run() {
                        AbsListView.this.semSetFastScrollEnabledUiThread(z);
                    }
                });
            }
        }
    }

    public boolean semIsFastScrollEnabled() {
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller == null) {
            return this.mFastScrollEnabled;
        }
        return semFastScroller.isEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void semSetFastScrollEnabledUiThread(boolean z) {
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller != null) {
            semFastScroller.setEnabled(z);
        } else if (z) {
            SemFastScroller semFastScroller2 = new SemFastScroller(this, this.mFastScrollStyle);
            this.mSemFastScroll = semFastScroller2;
            semFastScroller2.setEnabled(true);
            this.mFastScroll = null;
        }
        if (this.mAppWidgetFastScroll) {
            this.mSemFastScroll.semSetUseOpenThemeResources(false);
        }
        resolvePadding();
        SemFastScroller semFastScroller3 = this.mSemFastScroll;
        if (semFastScroller3 != null) {
            semFastScroller3.updateLayout();
        }
    }

    public void semSetFastScrollStyle(int i) {
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller == null) {
            this.mFastScrollStyle = i;
        } else {
            semFastScroller.setStyle(i);
        }
    }

    public void semSetFastScrollCustomEffectEnabled(boolean z) {
        this.mSemFastScrollCustomEffectEnabled = z;
    }

    @Deprecated
    public boolean semIsFastScrollCustomEffectEnabled() {
        return this.mSemFastScrollCustomEffectEnabled;
    }

    public void setFastScrollTrackPadding(int i, int i2) {
        isFastScrollEnabled();
    }

    public void semSetLongPressMultiSelectionEnabled(boolean z) {
        this.mLongPressMultiSelectionEnabled = z;
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

    public void semSetCtrlKeyPressed(boolean z) {
        this.mIsCtrlkeyPressed = z;
    }

    private final class CheckForDoublePenClick implements Runnable {
        int x;
        int y;

        private CheckForDoublePenClick() {
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            if (AbsListView.this.mIsFirstPenClick && AbsListView.this.mAdapter != null) {
                if (AbsListView.this.mSemDragSelectedItemSize != 0) {
                    if (AbsListView.this.mCheckStates != null && (AbsListView.this.mChoiceMode == 2 || AbsListView.this.mChoiceMode == 3)) {
                        Iterator it = AbsListView.this.mSemDragSelectedItemArray.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z = false;
                                break;
                            }
                            if (AbsListView.this.mAdapter.isEnabled(((Integer) it.next()).intValue())) {
                                z = true;
                                break;
                            }
                        }
                        if (AbsListView.this.mChoiceMode == 3 && AbsListView.this.mChoiceActionMode == null && z) {
                            AbsListView absListView = AbsListView.this;
                            absListView.mChoiceActionMode = absListView.startActionMode(absListView.mMultiChoiceModeCallback);
                        }
                        if (AbsListView.this.mIsSemOnClickEnabled && AbsListView.this.mSemMultiSelectionListener == null) {
                            Iterator it2 = AbsListView.this.mSemDragSelectedItemArray.iterator();
                            while (it2.hasNext()) {
                                Integer num = (Integer) it2.next();
                                if (AbsListView.this.mAdapter.isEnabled(num.intValue())) {
                                    AbsListView.this.performItemClick(null, num.intValue(), AbsListView.this.getItemIdAtPosition(num.intValue()));
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

    private void semMultiSelection(int i, int i2, int i3, int i4, boolean z) {
        OnScrollListener onScrollListener;
        int i5;
        int i6 = i - this.mTouchdownX;
        int i7 = i2 - this.mTouchdownY;
        int i8 = (i6 * i6) + (i7 * i7);
        int i9 = this.mTouchSlop;
        if (i8 > i9 * i9) {
            this.mIsMovedbeforeUP = true;
        }
        if (this.mIsNeedPenSelection) {
            int childCount = getChildCount();
            if (this.mIsfirstMoveEvent) {
                this.mSemDragStartX = i;
                this.mSemDragStartY = i2;
                super.semNotifyMultiSelectedStart(i, i2);
                this.mIsPenPressed = true;
                int iPointToPosition = pointToPosition(i, i2);
                this.mSemTrackedChildPosition = iPointToPosition;
                if (iPointToPosition == -1) {
                    int iSemPointToNearPosition = semPointToNearPosition(i, i2);
                    this.mSemTrackedChildPosition = iSemPointToNearPosition;
                    View childAt = getChildAt(iSemPointToNearPosition - getFirstVisiblePosition());
                    this.mSemTrackedChild = childAt;
                    if (childAt == null) {
                        View childAt2 = getChildAt(this.mSemCloseChildPositionByTop - getFirstVisiblePosition());
                        this.mSemCloseChildByTop = childAt2;
                        if (childAt2 != null) {
                            this.mSemDistanceFromCloseChildTop = this.mSemDragStartY - childAt2.getTop();
                        }
                        View childAt3 = getChildAt(this.mSemCloseChildPositionByBottom - getFirstVisiblePosition());
                        this.mSemCloseChildByBottom = childAt3;
                        if (childAt3 != null) {
                            this.mSemDistanceFromCloseChildBottom = this.mSemDragStartY - childAt3.getTop();
                        }
                    }
                } else {
                    this.mSemTrackedChild = getChildAt(iPointToPosition - getFirstVisiblePosition());
                }
                View view = this.mSemTrackedChild;
                if (view != null) {
                    this.mSemDistanceFromTrackedChildTop = this.mSemDragStartY - view.getTop();
                }
                this.mIsfirstMoveEvent = false;
            }
            if (this.mSemDragStartX == 0 && this.mSemDragStartY == 0) {
                this.mSemDragStartX = i;
                this.mSemDragStartY = i2;
                super.semNotifyMultiSelectedStart(i, i2);
                this.mIsPenPressed = true;
            }
            this.mSemDragEndX = i;
            this.mSemDragEndY = i2;
            if (i2 < 0) {
                this.mSemDragEndY = 0;
            } else if (i2 > i4) {
                this.mSemDragEndY = i4;
            }
            this.mSemDragSelectedViewPosition = pointToPosition(i, i2);
            this.mSemDragBlockLeft = Math.min(this.mSemDragStartX, this.mSemDragEndX);
            this.mSemDragBlockTop = Math.min(this.mSemDragStartY, this.mSemDragEndY);
            this.mSemDragBlockRight = Math.max(this.mSemDragEndX, this.mSemDragStartX);
            this.mSemDragBlockBottom = Math.max(this.mSemDragEndY, this.mSemDragStartY);
            for (int i10 = 0; i10 < childCount; i10++) {
                View childAt4 = getChildAt(i10);
                if (childAt4 != null) {
                    int left = childAt4.getLeft();
                    int top = childAt4.getTop();
                    int right = childAt4.getRight();
                    int bottom = childAt4.getBottom();
                    if (childAt4.getVisibility() == 0) {
                        int i11 = this.mSemDragBlockLeft;
                        if ((i11 > left && this.mSemDragBlockTop > top && this.mSemDragBlockRight < right && this.mSemDragBlockBottom < bottom) || (((i11 > left && this.mSemDragBlockRight < right) || ((i11 < left && this.mSemDragBlockRight > left) || (i11 < right && this.mSemDragBlockRight > right))) && (((i5 = this.mSemDragBlockTop) >= top && this.mSemDragBlockBottom <= bottom) || ((i5 <= top && this.mSemDragBlockBottom > top) || (i5 < bottom && this.mSemDragBlockBottom >= bottom))))) {
                            int iPointToPosition2 = pointToPosition(left + 1, top + 1);
                            this.mSemDragSelectedViewPosition = iPointToPosition2;
                            if (iPointToPosition2 != -1 && this.mAdapter.isEnabled(iPointToPosition2) && !this.mSemDragSelectedItemArray.contains(Integer.valueOf(this.mSemDragSelectedViewPosition))) {
                                this.mSemDragSelectedItemArray.add(Integer.valueOf(this.mSemDragSelectedViewPosition));
                                if (this.mSemMultiSelectionListener == null) {
                                    addToPressItemListArray(this.mSemDragSelectedViewPosition, -1);
                                    int i12 = this.mSemDragSelectedViewPosition;
                                    semToNotifyMultiSelectionState(childAt4, i12, getItemIdAtPosition(i12));
                                }
                            }
                        } else {
                            int iPointToPosition3 = pointToPosition(left + 1, top + 1);
                            this.mSemDragSelectedViewPosition = iPointToPosition3;
                            if (iPointToPosition3 != -1 && this.mAdapter.isEnabled(iPointToPosition3) && this.mSemDragSelectedItemArray.contains(Integer.valueOf(this.mSemDragSelectedViewPosition))) {
                                this.mSemDragSelectedItemArray.remove(Integer.valueOf(this.mSemDragSelectedViewPosition));
                                if (this.mSemMultiSelectionListener == null) {
                                    addToPressItemListArray(this.mSemDragSelectedViewPosition, -1);
                                    int i13 = this.mSemDragSelectedViewPosition;
                                    semToNotifyMultiSelectionState(childAt4, i13, getItemIdAtPosition(i13));
                                }
                            }
                        }
                    }
                }
            }
            z = true;
        }
        if (z) {
            if (i2 <= i3 + this.mHoverTopAreaHeight) {
                if (!this.mHoverAreaEnter) {
                    this.mHoverAreaEnter = true;
                    this.mHoverScrollStartTime = System.currentTimeMillis();
                    OnScrollListener onScrollListener2 = this.mOnScrollListener;
                    if (onScrollListener2 != null) {
                        onScrollListener2.onScrollStateChanged(this, 1);
                    }
                }
                if (!this.mHoverHandler.hasMessages(1)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    this.mHoverScrollDirection = 2;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else if (i2 >= i4 - this.mHoverBottomAreaHeight) {
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
                    this.mHoverScrollDirection = 1;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else {
                if (this.mHoverAreaEnter && (onScrollListener = this.mOnScrollListener) != null) {
                    onScrollListener.onScrollStateChanged(this, 0);
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
        this.mPreviousTextViewScroll = z;
    }

    private void semMultiSelectionEnd(int i, int i2, int i3) {
        OnScrollListener onScrollListener;
        if (!this.mIsTextSelectionStarted) {
            if (i == 212) {
                this.mIsFirstPenClick = !this.mIsFirstPenClick;
            }
            if (this.mHoverAreaEnter && (onScrollListener = this.mOnScrollListener) != null) {
                onScrollListener.onScrollStateChanged(this, 0);
            }
            this.mHoverRecognitionStartTime = 0L;
            this.mHoverScrollStartTime = 0L;
            this.mHoverAreaEnter = false;
            this.mSemDragSelectedItemSize = this.mSemDragSelectedItemArray.size();
            if (this.mPendingCheckForDoublePenClick == null) {
                this.mPendingCheckForDoublePenClick = new CheckForDoublePenClick();
            }
            this.mPendingCheckForDoublePenClick.x = i2;
            this.mPendingCheckForDoublePenClick.y = i3;
            if (this.mIsFirstPenClick) {
                if (this.mIsMovedbeforeUP) {
                    post(this.mPendingCheckForDoublePenClick);
                } else {
                    postDelayed(this.mPendingCheckForDoublePenClick, ViewConfiguration.getDoubleTapTimeout());
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
    public void semToNotifyMultiSelectionEnded(int i, int i2) {
        super.semNotifyMultiSelectedStop(i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0163  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateLongPressMultiSelection(int i, int i2, boolean z) {
        int height;
        int i3;
        OnScrollListener onScrollListener;
        boolean z2;
        int i4;
        int childCount = getChildCount();
        if (this.mIsFirstMultiSelectionMove) {
            this.mSemDragStartX = i;
            this.mSemDragStartY = i2;
            super.semNotifyLongPressMultiSelectionStarted(i, i2);
            int iPointToPosition = pointToPosition(i, i2);
            this.mSemTrackedChildPosition = iPointToPosition;
            this.mSemDragSelectedViewPosition = iPointToPosition;
            if (iPointToPosition == -1) {
                this.mSemTrackedChildPosition = semPointToNearPosition(i, i2);
                if (this.mSemTrackedChild == null) {
                    View childAt = getChildAt(this.mSemCloseChildPositionByTop - getFirstVisiblePosition());
                    this.mSemCloseChildByTop = childAt;
                    if (childAt != null) {
                        this.mSemDistanceFromCloseChildTop = this.mSemDragStartY - childAt.getTop();
                    }
                    View childAt2 = getChildAt(this.mSemCloseChildPositionByBottom - getFirstVisiblePosition());
                    this.mSemCloseChildByBottom = childAt2;
                    if (childAt2 != null) {
                        this.mSemDistanceFromCloseChildBottom = this.mSemDragStartY - childAt2.getTop();
                    }
                }
            } else {
                this.mSemTrackedChild = getChildAt(iPointToPosition - getFirstVisiblePosition());
            }
            View view = this.mSemTrackedChild;
            if (view != null) {
                this.mSemDistanceFromTrackedChildTop = this.mSemDragStartY - view.getTop();
            }
            this.mIsFirstMultiSelectionMove = false;
        }
        if (this.mIsEnabledPaddingInHoverScroll) {
            i3 = this.mListPadding.top;
            height = getHeight() - this.mListPadding.bottom;
        } else {
            height = getHeight();
            i3 = 0;
        }
        this.mSemDragEndX = i;
        this.mSemDragEndY = i2;
        if (i2 < 0) {
            this.mSemDragEndY = 0;
        } else if (i2 > height) {
            this.mSemDragEndY = height;
        }
        int iPointToPosition2 = pointToPosition(i, this.mSemDragEndY);
        if (iPointToPosition2 != -1) {
            this.mSemDragSelectedViewPosition = iPointToPosition2;
        } else {
            this.mSemDragSelectedViewPosition = semPointToNearPosition(this.mSemDragEndX, this.mSemDragEndY);
        }
        int i5 = this.mSemTrackedChildPosition;
        int i6 = this.mSemDragSelectedViewPosition;
        if (i5 >= i6) {
            i6 = i5;
            i5 = i6;
        }
        this.mSemDragBlockLeft = Math.min(this.mSemDragStartX, this.mSemDragEndX);
        this.mSemDragBlockTop = Math.min(this.mSemDragStartY, this.mSemDragEndY);
        this.mSemDragBlockRight = Math.max(this.mSemDragEndX, this.mSemDragStartX);
        this.mSemDragBlockBottom = Math.max(this.mSemDragEndY, this.mSemDragStartY);
        int i7 = 0;
        while (true) {
            boolean z3 = true;
            if (i7 >= childCount) {
                break;
            }
            View childAt3 = getChildAt(i7);
            if (childAt3 != null) {
                int left = childAt3.getLeft();
                int top = childAt3.getTop();
                int positionForView = getPositionForView(childAt3);
                if (childAt3.getVisibility() == 0) {
                    if (i5 > positionForView || positionForView > i6) {
                        z3 = false;
                    } else {
                        SparseBooleanArray sparseBooleanArray = this.mCheckStates;
                        if (sparseBooleanArray == null || !sparseBooleanArray.get(positionForView, false)) {
                            z2 = false;
                        }
                        if (!z3) {
                            int iPointToPosition3 = pointToPosition(left + 1, top + 1);
                            this.mSemDragSelectedViewPosition = iPointToPosition3;
                            if (iPointToPosition3 != -1 && this.mAdapter.isEnabled(iPointToPosition3) && !this.mSemDragSelectedItemArray.contains(Integer.valueOf(this.mSemDragSelectedViewPosition))) {
                                if (!z2 || i5 > (i4 = this.mSemDragSelectedViewPosition) || i4 > i6) {
                                    int i8 = this.mSemDragSelectedViewPosition;
                                    semPerformItemCheck(null, i8, getItemIdAtPosition(i8));
                                }
                                this.mSemDragSelectedItemArray.add(Integer.valueOf(this.mSemDragSelectedViewPosition));
                                addToPressItemListArray(this.mSemDragSelectedViewPosition, -1);
                                int i9 = this.mSemDragSelectedViewPosition;
                                super.semNotifyLongPressMultiSelectionState(childAt3, i9, getItemIdAtPosition(i9));
                            }
                        } else {
                            int iPointToPosition4 = pointToPosition(left + 1, top + 1);
                            this.mSemDragSelectedViewPosition = iPointToPosition4;
                            if (iPointToPosition4 != -1 && this.mAdapter.isEnabled(iPointToPosition4) && this.mSemDragSelectedItemArray.contains(Integer.valueOf(this.mSemDragSelectedViewPosition))) {
                                int i10 = this.mSemDragSelectedViewPosition;
                                semPerformItemCheck(null, i10, getItemIdAtPosition(i10));
                                this.mSemDragSelectedItemArray.remove(Integer.valueOf(this.mSemDragSelectedViewPosition));
                                addToPressItemListArray(this.mSemDragSelectedViewPosition, -1);
                                int i11 = this.mSemDragSelectedViewPosition;
                                super.semNotifyLongPressMultiSelectionState(childAt3, i11, getItemIdAtPosition(i11));
                            }
                        }
                    }
                    z2 = z3;
                    if (!z3) {
                    }
                }
            }
            i7++;
        }
        if (z) {
            if (i2 <= i3 + this.mHoverTopAreaHeight) {
                if (!this.mHoverAreaEnter) {
                    this.mHoverAreaEnter = true;
                    this.mHoverScrollStartTime = System.currentTimeMillis();
                    OnScrollListener onScrollListener2 = this.mOnScrollListener;
                    if (onScrollListener2 != null) {
                        onScrollListener2.onScrollStateChanged(this, 1);
                    }
                }
                if (!this.mHoverHandler.hasMessages(1)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    this.mHoverScrollDirection = 2;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else if (i2 >= height - this.mHoverBottomAreaHeight) {
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
                    this.mHoverScrollDirection = 1;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else {
                if (this.mHoverAreaEnter && (onScrollListener = this.mOnScrollListener) != null) {
                    onScrollListener.onScrollStateChanged(this, 0);
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

    private void semPerformItemCheck(View view, int i, long j) {
        MultiChoiceModeWrapper multiChoiceModeWrapper;
        SparseBooleanArray sparseBooleanArray = this.mCheckStates;
        if (sparseBooleanArray != null) {
            int i2 = this.mChoiceMode;
            if (i2 == 2 || (i2 == 3 && this.mChoiceActionMode != null)) {
                boolean z = sparseBooleanArray.get(i, false);
                boolean z2 = !z;
                this.mCheckStates.put(i, z2);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    if (!z) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(i), Integer.valueOf(i));
                    } else {
                        this.mCheckedIdStates.delete(this.mAdapter.getItemId(i));
                    }
                }
                if (!z) {
                    this.mCheckedItemCount++;
                } else {
                    this.mCheckedItemCount--;
                }
                ActionMode actionMode = this.mChoiceActionMode;
                if (actionMode != null && (multiChoiceModeWrapper = this.mMultiChoiceModeCallback) != null) {
                    multiChoiceModeWrapper.onItemCheckedStateChanged(actionMode, i, j, z2);
                }
                updateOnScreenCheckedViews();
            }
        }
    }

    private void endLongPressMultiSelection(int i, int i2) {
        super.semNotifyLongPressMultiSelectionEnded(i, i2);
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

    public void semSetDragBlockEnabled(boolean z) {
        this.mIsDragBlockEnabled = z;
    }

    public int semPointToNearPosition(int i, int i2) {
        int top;
        int childCount = getChildCount() - 1;
        int i3 = Integer.MAX_VALUE;
        int iAbs = 0;
        int i4 = i2;
        int i5 = 0;
        for (int i6 = childCount; i6 >= 0; i6--) {
            View childAt = getChildAt(i6);
            if (childAt != null && i5 != (top = (childAt.getTop() + childAt.getBottom()) / 2)) {
                int iAbs2 = Math.abs(i2 - top);
                if (iAbs2 >= i3) {
                    break;
                }
                i3 = iAbs2;
                i5 = top;
                i4 = i5;
            }
        }
        int iAbs3 = 0;
        int firstVisiblePosition = 0;
        int firstVisiblePosition2 = 0;
        for (int i7 = childCount; i7 >= 0; i7--) {
            View childAt2 = getChildAt(i7);
            if (childAt2 != null) {
                int top2 = childAt2.getTop();
                int bottom = childAt2.getBottom();
                int left = childAt2.getLeft();
                int right = childAt2.getRight();
                if (i7 == childCount) {
                    firstVisiblePosition = childCount + getFirstVisiblePosition();
                    firstVisiblePosition2 = childCount + getFirstVisiblePosition();
                    iAbs = Math.abs(i - left);
                    iAbs3 = Math.abs(i - right);
                }
                if (i4 >= top2 && i4 <= bottom) {
                    int iAbs4 = Math.abs(i - left);
                    int iAbs5 = Math.abs(i - right);
                    if (iAbs4 <= iAbs) {
                        firstVisiblePosition = i7 + getFirstVisiblePosition();
                        iAbs = iAbs4;
                    }
                    if (iAbs5 <= iAbs3) {
                        firstVisiblePosition2 = i7 + getFirstVisiblePosition();
                        iAbs3 = iAbs5;
                    }
                }
                if (i4 > bottom || i7 == 0) {
                    return iAbs < iAbs3 ? firstVisiblePosition : firstVisiblePosition2;
                }
            }
        }
        Log.e(TAG, "semPointToNearPosition didn't find valid position!! " + i + ", " + i2);
        return -1;
    }

    public void semSetClickableInMultiSelectMode(boolean z) {
        this.mIsSemOnClickEnabled = z;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public void setTouchSlop(int i) {
        this.mTouchSlop = i;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        int height;
        int i;
        AbsListView absListView;
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int action = motionEvent.getAction();
        boolean zIsNeedToScroll = MultiSelection.isNeedToScroll();
        if (this.mSemDragSelectedItemArray == null) {
            this.mSemDragSelectedItemArray = new ArrayList<>();
        }
        if (this.mHoverHandler == null) {
            this.mHoverHandler = new HoverScrollHandler(this);
        }
        this.mIsTextSelectionStarted = TextView.semIsTextSelectionProgressing();
        if (action == 211) {
            this.mIsNeedPenSelection = true;
            this.mTouchdownX = x;
            this.mTouchdownY = y;
            if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "car_mode_on", 0, -3) == 1) {
                this.mIsNeedPenSelection = false;
            }
        }
        if (this.mIsTextSelectionStarted) {
            this.mIsNeedPenSelection = false;
        }
        if (this.mHoverTopAreaHeight <= 0 || this.mHoverBottomAreaHeight <= 0) {
            this.mHoverTopAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
            this.mHoverBottomAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
        }
        if (this.mIsEnabledPaddingInHoverScroll) {
            i = this.mListPadding.top;
            height = getHeight() - this.mListPadding.bottom;
        } else {
            height = getHeight();
            i = 0;
        }
        if (this.mIsEnabledPaddingInHoverScroll && ((y < i || y > height) && motionEvent.getAction() != 1 && motionEvent.getAction() != 212)) {
            return true;
        }
        if (action == 0) {
            int i2 = i;
            absListView = this;
            int i3 = height;
            absListView.mGoToToping = false;
            if (absListView.mSemGoToTopState != 2 && absListView.semIsSupportGotoTop() && absListView.mSemGoToTopRect.contains(x, y)) {
                absListView.semSetupGoToTop(2);
                absListView.mSemGoToTopImage.setHotspot(x, y);
                absListView.mSemGoToTopImage.setState(new int[]{16842919, 16842910, 16842913});
                return true;
            }
            if (absListView.mIsCtrlkeyPressed && motionEvent.getToolType(0) == 3) {
                absListView.mIsCtrlMultiSelection = true;
                absListView.mIsNeedPenSelection = true;
                absListView.mTouchdownX = x;
                absListView.mTouchdownY = y;
                if (Settings.System.getIntForUser(absListView.mContext.getContentResolver(), "car_mode_on", 0, -3) == 1) {
                    absListView.mIsNeedPenSelection = false;
                }
                absListView.semMultiSelection(x, y, i2, i3, zIsNeedToScroll);
                return true;
            }
        } else {
            if (action == 1) {
                absListView = this;
            } else if (action == 2) {
                int i4 = i;
                int i5 = height;
                absListView = this;
                if (absListView.mIsCtrlMultiSelection) {
                    absListView.semMultiSelection(x, y, i4, i5, zIsNeedToScroll);
                    return true;
                }
                if (absListView.mIsLongPressMultiSelection) {
                    absListView.updateLongPressMultiSelection(x, y, true);
                }
                if (absListView.mSemGoToTopState == 2 && absListView.semIsSupportGotoTop()) {
                    if (!absListView.mSemGoToTopRect.contains(x, y)) {
                        absListView.mSemGoToTopState = 1;
                        absListView.semAutoHide(1);
                        absListView.mSemGoToTopImage.setState(StateSet.NOTHING);
                    }
                    return true;
                }
            } else if (action == 3) {
                absListView = this;
                if (absListView.mSemGoToTopState != 0 && absListView.semIsSupportGotoTop()) {
                    if (absListView.mSemGoToTopState == 2) {
                        absListView.mSemGoToTopState = 1;
                    }
                    absListView.mSemGoToTopImage.setState(StateSet.NOTHING);
                }
                if (absListView.mIsLongPressMultiSelection) {
                    absListView.endLongPressMultiSelection(absListView.mSemDragEndX, absListView.mSemDragEndY);
                }
            } else if (action == 212) {
                absListView = this;
                if (absListView.mSemGoToTopState != 2 && absListView.semIsSupportGotoTop()) {
                    if (absListView.canScrollUp()) {
                        absListView.mGoToToping = true;
                        Log.d(TAG, " can scroll top ");
                        int childCount = absListView.getChildCount();
                        if (childCount > 0 && childCount < absListView.getFirstVisiblePosition()) {
                            absListView.setSelection(childCount);
                        }
                        absListView.mSemCanGoFuther = true;
                        absListView.post(new Runnable() { // from class: android.widget.AbsListView.12
                            @Override // java.lang.Runnable
                            public void run() {
                                if (AbsListView.this.shouldSkipScroll()) {
                                    AbsListView.this.smoothScrollToPositionFromTop(0, 0, 0);
                                } else {
                                    AbsListView.this.smoothScrollToPosition(0);
                                }
                            }
                        });
                    }
                    absListView.semAutoHide(0);
                    absListView.mSemGoToTopImage.setState(StateSet.NOTHING);
                    absListView.playSoundEffect(0);
                    return true;
                }
                absListView.semMultiSelectionEnd(action, x, y);
            } else if (action != 213) {
                absListView = this;
            } else {
                absListView = this;
                absListView.semMultiSelection(x, y, i, height, zIsNeedToScroll);
            }
            if (absListView.mIsCtrlMultiSelection) {
                if (!absListView.mIsTextSelectionStarted) {
                    absListView.mIsFirstPenClick = !absListView.mIsFirstPenClick;
                }
                absListView.semMultiSelectionEnd(action, x, y);
                absListView.mIsCtrlMultiSelection = false;
                return true;
            }
            if (absListView.mIsLongPressMultiSelection) {
                absListView.endLongPressMultiSelection(x, y);
            }
            if (absListView.mSemGoToTopState != 2) {
            }
            absListView.semMultiSelectionEnd(action, x, y);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        int i;
        int height;
        int i2;
        boolean z;
        if (!isHoveringUIEnabled()) {
            return super.dispatchHoverEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (this.mHoveringEnabled && (this.mTouchMode != 1 || !this.mIsPenPressed)) {
            onHoverDrawableState(motionEvent);
        }
        boolean zSemIsTextViewHovered = TextView.semIsTextViewHovered();
        this.mNewTextViewHoverState = zSemIsTextViewHovered;
        if (!zSemIsTextViewHovered && this.mOldTextViewHoverState && this.mIsDragBlockEnabled && (motionEvent.getButtonState() == 32 || motionEvent.getButtonState() == 2)) {
            this.mIsNeedPenSelectIconSet = true;
        } else {
            this.mIsNeedPenSelectIconSet = false;
        }
        this.mOldTextViewHoverState = this.mNewTextViewHoverState;
        if (action == 9 || this.mHoverScrollStateChanged) {
            int toolType = motionEvent.getToolType(0);
            this.mNeedsHoverScroll = true;
            this.mHoverScrollStateChanged = false;
            if (!this.mHoverScrollEnable) {
                this.mNeedsHoverScroll = false;
            }
            if (this.mNeedsHoverScroll && toolType == 2) {
                boolean z2 = Settings.System.getInt(this.mContext.getContentResolver(), Settings.System.SEM_PEN_HOVERING, 0) == 1;
                boolean z3 = Settings.System.getIntForUser(this.mContext.getContentResolver(), "car_mode_on", 0, -3) == 1;
                if (!z2 || z3) {
                    this.mNeedsHoverScroll = false;
                }
                if (z2 && this.mIsDragBlockEnabled && !this.mIsPenSelectPointerSetted && toolType == 2 && (motionEvent.getButtonState() == 32 || motionEvent.getButtonState() == 2)) {
                    showPointerIcon(motionEvent, 20021);
                    this.mIsPenSelectPointerSetted = true;
                }
            }
            if (this.mNeedsHoverScroll && toolType == 3) {
                this.mNeedsHoverScroll = false;
            }
        } else if (action == 7) {
            if ((this.mIsDragBlockEnabled && !this.mIsPenSelectPointerSetted && motionEvent.getToolType(0) == 2 && (motionEvent.getButtonState() == 32 || motionEvent.getButtonState() == 2)) || this.mIsNeedPenSelectIconSet) {
                showPointerIcon(motionEvent, 20021);
                this.mIsPenSelectPointerSetted = true;
            } else if (this.mIsDragBlockEnabled && this.mIsPenSelectPointerSetted && motionEvent.getButtonState() != 32 && motionEvent.getButtonState() != 2) {
                showPointerIcon(motionEvent, 20001);
                this.mIsPenSelectPointerSetted = false;
            }
        } else if (action == 10 && this.mIsPenSelectPointerSetted) {
            showPointerIcon(motionEvent, 20001);
            this.mIsPenSelectPointerSetted = false;
        }
        if (!this.mNeedsHoverScroll) {
            return super.dispatchHoverEvent(motionEvent);
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int childCount = getChildCount();
        if (this.mHoverHandler == null) {
            this.mHoverHandler = new HoverScrollHandler(this);
        }
        if (this.mHoverTopAreaHeight <= 0 || this.mHoverBottomAreaHeight <= 0) {
            this.mHoverTopAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
            this.mHoverBottomAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
        }
        if (this.mIsEnabledPaddingInHoverScroll) {
            i = this.mListPadding.top;
            height = getHeight();
            i2 = this.mListPadding.bottom;
        } else {
            i = this.mExtraPaddingInTopHoverArea;
            height = getHeight();
            i2 = this.mExtraPaddingInBottomHoverArea;
        }
        int i3 = height - i2;
        boolean z4 = this.mFirstPosition + childCount < this.mItemCount;
        if (!z4 && childCount > 0) {
            View childAt = getChildAt(childCount - 1);
            z4 = childAt.getBottom() > this.mBottom - this.mListPadding.bottom || childAt.getBottom() > getHeight() - this.mListPadding.bottom;
        }
        boolean z5 = this.mFirstPosition > 0;
        if (!z5 && childCount > 0) {
            z5 = getChildAt(0).getTop() < this.mListPadding.top;
        }
        boolean z6 = motionEvent.getToolType(0) == 2;
        if ((y > this.mHoverTopAreaHeight + i && y < i3 - this.mHoverBottomAreaHeight) || x <= 0 || x > getRight() || ((!z5 && !z4) || ((y >= i && y <= this.mHoverTopAreaHeight + i && !z5 && this.mIsHoverOverscrolled) || ((y >= i3 - this.mHoverBottomAreaHeight && y <= i3 && !z4 && this.mIsHoverOverscrolled) || ((z6 && (motionEvent.getButtonState() == 32 || motionEvent.getButtonState() == 2)) || !z6 || isLockScreenMode() || (this.mSemEnableGoToTop && this.mSemGoToTopState != 0 && this.mSemGoToTopRect.contains(x, y))))))) {
            if (this.mHoverHandler.hasMessages(1)) {
                this.mHoverHandler.removeMessages(1);
                showPointerIcon(motionEvent, 20001);
            }
            if ((y > i + this.mHoverTopAreaHeight && y < i3 - this.mHoverBottomAreaHeight) || x <= 0 || x > getRight()) {
                this.mIsHoverOverscrolled = false;
            }
            if (this.mHoverAreaEnter || this.mHoverScrollStartTime != 0) {
                showPointerIcon(motionEvent, 20001);
            }
            this.mHoverRecognitionStartTime = 0L;
            this.mHoverScrollStartTime = 0L;
            this.mHoverAreaEnter = false;
            this.mIsSendHoverScrollState = false;
            if (action == 10 && this.mHoverScrollStateForListener != 0) {
                this.mHoverScrollStateForListener = 0;
                OnScrollListener onScrollListener = this.mOnScrollListener;
                if (onScrollListener != null && this.mTouchMode != 4) {
                    onScrollListener.onScrollStateChanged(this, 0);
                }
            }
            return super.dispatchHoverEvent(motionEvent);
        }
        if (!this.mHoverAreaEnter) {
            this.mHoverScrollStartTime = System.currentTimeMillis();
        }
        if (action != 7) {
            if (action != 9) {
                if (action == 10) {
                    if (this.mHoverHandler.hasMessages(1)) {
                        this.mHoverHandler.removeMessages(1);
                    }
                    showPointerIcon(motionEvent, 20001);
                    this.mHoverRecognitionStartTime = 0L;
                    this.mHoverScrollStartTime = 0L;
                    this.mIsHoverOverscrolled = false;
                    this.mHoverAreaEnter = false;
                    this.mIsSendHoverScrollState = false;
                    if (this.mHoverScrollStateForListener != 0) {
                        this.mHoverScrollStateForListener = 0;
                        OnScrollListener onScrollListener2 = this.mOnScrollListener;
                        if (onScrollListener2 != null && this.mTouchMode != 4) {
                            onScrollListener2.onScrollStateChanged(this, 0);
                        }
                    }
                    return super.dispatchHoverEvent(motionEvent);
                }
                return true;
            }
            z = true;
            this.mHoverAreaEnter = true;
            if (y >= i && y <= i + this.mHoverTopAreaHeight) {
                if (!this.mHoverHandler.hasMessages(1)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    showPointerIcon(motionEvent, 20011);
                    this.mHoverScrollDirection = 2;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else if (y >= i3 - this.mHoverBottomAreaHeight && y <= i3 && !this.mHoverHandler.hasMessages(1)) {
                this.mHoverRecognitionStartTime = System.currentTimeMillis();
                showPointerIcon(motionEvent, 20015);
                this.mHoverScrollDirection = 1;
                this.mHoverHandler.sendEmptyMessage(1);
            }
            return z;
        }
        z = true;
        if (!this.mHoverAreaEnter) {
            this.mHoverAreaEnter = true;
            motionEvent.setAction(10);
            return super.dispatchHoverEvent(motionEvent);
        }
        if (y >= i && y <= i + this.mHoverTopAreaHeight) {
            if (!this.mHoverHandler.hasMessages(1)) {
                this.mHoverRecognitionStartTime = System.currentTimeMillis();
                if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 1) {
                    showPointerIcon(motionEvent, 20011);
                }
                this.mHoverScrollDirection = 2;
                this.mHoverHandler.sendEmptyMessage(1);
            }
            return z;
        }
        if (y >= i3 - this.mHoverBottomAreaHeight && y <= i3) {
            if (!this.mHoverHandler.hasMessages(1)) {
                this.mHoverRecognitionStartTime = System.currentTimeMillis();
                if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 2) {
                    showPointerIcon(motionEvent, 20015);
                }
                this.mHoverScrollDirection = 1;
                this.mHoverHandler.sendEmptyMessage(1);
                return true;
            }
            return true;
        }
        if (this.mHoverHandler.hasMessages(1)) {
            this.mHoverHandler.removeMessages(1);
        }
        showPointerIcon(motionEvent, 20001);
        this.mHoverRecognitionStartTime = 0L;
        this.mHoverScrollStartTime = 0L;
        this.mIsHoverOverscrolled = false;
        this.mHoverAreaEnter = false;
        this.mIsHoverScrolled = false;
        this.mIsSendHoverScrollState = false;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchDragEvent(DragEvent dragEvent) {
        int action = dragEvent.getAction();
        ClipDescription clipDescription = dragEvent.getClipDescription();
        if (clipDescription == null || !"cropUri".equals(clipDescription.getLabel())) {
            return super.dispatchDragEvent(dragEvent);
        }
        if (action == 1) {
            if (this.mDragScrollWorkingZonePx <= 0) {
                this.mDragScrollWorkingZonePx = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
            }
            super.dispatchDragEvent(dragEvent);
            return true;
        }
        int x = (int) dragEvent.getX();
        int y = (int) dragEvent.getY();
        int childCount = getChildCount();
        int height = childCount != 0 ? getHeight() : 0;
        boolean z = this.mFirstPosition + childCount < this.mItemCount;
        if (!z && childCount > 0) {
            View childAt = getChildAt(childCount - 1);
            z = childAt.getBottom() > this.mBottom - this.mListPadding.bottom || childAt.getBottom() > getHeight() - this.mListPadding.bottom;
        }
        boolean z2 = this.mFirstPosition > 0;
        if (!z2 && childCount > 0) {
            z2 = getChildAt(0).getTop() < this.mListPadding.top;
        }
        int i = this.mDragScrollWorkingZonePx;
        if ((y > i && y < height - i) || x <= 0 || x > getRight() || (!z2 && !z)) {
            HoverScrollHandler hoverScrollHandler = this.mHoverHandler;
            if (hoverScrollHandler != null && hoverScrollHandler.hasMessages(1)) {
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
            return super.dispatchDragEvent(dragEvent);
        }
        if (this.mHoverHandler == null) {
            this.mHoverHandler = new HoverScrollHandler(this);
        }
        if (!this.mHoverAreaEnter) {
            this.mHoverScrollStartTime = System.currentTimeMillis();
        }
        if (action == 2) {
            this.mHoverAreaEnter = true;
            if (y >= 0 && y <= this.mDragScrollWorkingZonePx) {
                if (!this.mHoverHandler.hasMessages(1)) {
                    this.mIsDragScrolled = true;
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    this.mHoverScrollDirection = 2;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else if (y >= height - this.mDragScrollWorkingZonePx && y <= height && !this.mHoverHandler.hasMessages(1)) {
                this.mIsDragScrolled = true;
                this.mHoverRecognitionStartTime = System.currentTimeMillis();
                this.mHoverScrollDirection = 1;
                this.mHoverHandler.sendEmptyMessage(1);
            }
        } else {
            if (action != 3) {
                if (action != 4) {
                    if (action == 5) {
                        this.mHoverAreaEnter = true;
                        if (y >= 0 && y <= this.mDragScrollWorkingZonePx) {
                            if (!this.mHoverHandler.hasMessages(1)) {
                                this.mIsDragScrolled = true;
                                this.mHoverRecognitionStartTime = System.currentTimeMillis();
                                this.mHoverScrollDirection = 2;
                                this.mHoverHandler.sendEmptyMessage(1);
                            }
                        } else if (y >= height - this.mDragScrollWorkingZonePx && y <= height && !this.mHoverHandler.hasMessages(1)) {
                            this.mIsDragScrolled = true;
                            this.mHoverRecognitionStartTime = System.currentTimeMillis();
                            this.mHoverScrollDirection = 1;
                            this.mHoverHandler.sendEmptyMessage(1);
                        }
                    } else if (action == 6) {
                    }
                }
            } else if (this.mIsDragScrolled) {
                this.mIsDragScrolled = false;
            }
            if (this.mHoverHandler.hasMessages(1)) {
                this.mHoverHandler.removeMessages(1);
            }
            this.mIsDragScrolled = false;
            this.mHoverRecognitionStartTime = 0L;
            this.mHoverScrollStartTime = 0L;
            this.mIsHoverOverscrolled = false;
            this.mHoverAreaEnter = false;
        }
        return super.dispatchDragEvent(dragEvent);
    }

    public void setEnableHoverDrawable(boolean z) {
        this.mHoveringEnabled = z;
    }

    public boolean isLockScreenMode() {
        return ((KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode();
    }

    private boolean semIsTalkBackIsRunning() {
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(this.mContext);
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return false;
        }
        return accessibilityManager.semIsAccessibilityServiceEnabled(32) || accessibilityManager.semIsAccessibilityServiceEnabled(16) || accessibilityManager.semIsAccessibilityServiceEnabled(64);
    }

    private class SemSmoothScrollByMove implements Runnable {
        private SemSmoothScrollByMove() {
        }

        @Override // java.lang.Runnable
        public void run() throws Resources.NotFoundException {
            if (AbsListView.this.mFlingRunnable.mScroller.isFinished()) {
                if (AbsListView.this.mSemScrollRemains == null || AbsListView.this.mSemScrollRemains.isEmpty()) {
                    return;
                }
                Integer num = (Integer) AbsListView.this.mSemScrollRemains.poll();
                if (num != null) {
                    AbsListView.this.smoothScrollBy(num.intValue(), 0);
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
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return 0;
        }
        return adapter.getCount();
    }

    @Override // android.view.ViewGroup
    public void semSetSelection(int i) {
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
            this.mSemScrollAmount = (int) (this.mDensityScale * 150.0f);
        }
        boolean zIsEmpty = this.mSemScrollRemains.isEmpty();
        if (Math.abs(i) > this.mSemScrollAmount) {
            if (i <= 0) {
                while (true) {
                    int i2 = this.mSemScrollAmount;
                    if (i >= (-i2)) {
                        break;
                    }
                    this.mSemScrollRemains.offer(Integer.valueOf(-i2));
                    i += this.mSemScrollAmount;
                }
            } else {
                while (true) {
                    int i3 = this.mSemScrollAmount;
                    if (i <= i3) {
                        break;
                    }
                    this.mSemScrollRemains.offer(Integer.valueOf(i3));
                    i -= this.mSemScrollAmount;
                }
            }
        }
        this.mSemScrollRemains.offer(Integer.valueOf(i));
        if (zIsEmpty) {
            post(this.mSemSmoothScrollByMove);
        }
    }

    @Deprecated
    public void semSetForcedEdgeEffectEnabled(boolean z) {
        this.mSemForcedDrawEdgeEffect = z;
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        this.mHoverPosition = -1;
        if (i == 0) {
            return;
        }
        releaseAllBoosters();
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void setForcedClick(boolean z) {
        this.mForcedClick = z;
    }

    public void triggerJumpScrollToTop() {
        this.mJumpScrollToTopState = JUMP_SCROLL_TO_TOP_INITIATED;
        triggerDoubleFling(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postOnJumpScrollToFinished() {
        postOnAnimation(new Runnable() { // from class: android.widget.AbsListView.13
            @Override // java.lang.Runnable
            public void run() {
                AbsListView.this.onJumpScrollToTopFinished();
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

    public void setSweepListAnimator(SemSweepListAnimator semSweepListAnimator) {
        this.mSweepListAnimator = semSweepListAnimator;
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

    public void semSetEnableVibrationAtLongPress(boolean z) {
        this.mEnableVibrationAtLongPress = z;
    }

    public void setEnableDoubleFling(boolean z) {
        this.mDoubleFlingEnabled = z;
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

    public void semSetFluidScrollerEnabled(boolean z) {
        setFastScrollEnabled(z);
    }

    public boolean semIsFluidScrollerEnabled() {
        return isFastScrollEnabled();
    }

    public boolean semNotifyKeyPressState(View view, int i, long j) {
        boolean z = this.mIsShiftkeyPressed;
        if (z) {
            return super.semNotifyKeyPress(view, i, j, z);
        }
        return false;
    }

    private boolean semToNotifyMultiSelectionState(View view, int i, long j) {
        return super.semNotifyMultiSelectedState(view, i, j, this.mIsShiftkeyPressed, this.mIsCtrlkeyPressed, this.mIsPenPressed);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldSkipScroll() {
        return Settings.Global.getInt(getContext().getContentResolver(), "remove_animations", 0) == 1;
    }

    private void initIndicator() throws Resources.NotFoundException {
        this.mIndicatorItemCnt = 0;
        this.mIndicatorAnimatedSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_indicator_radius_focused);
        this.mIndicatorRectSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_indicator_rect_size);
        if (this.mIndicatorMarginHorizontal == 0) {
            this.mIndicatorMarginHorizontal = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_indicator_rect_size);
        }
        this.mIndicatorFocusedSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_indicator_radius_focused);
        this.mIndicatorDefaultSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_indicator_radius);
        if (this.mAdapter != null) {
            this.mIndicatorIndex = new ArrayList();
        }
        this.mFocusedPos = getFirstVisiblePosition();
        if (this.mAnimator == null) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mIndicatorDefaultSize, this.mIndicatorFocusedSize);
            this.mAnimator = valueAnimatorOfInt;
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.AbsListView.14
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    AbsListView.this.mIndicatorAnimatedSize = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    AbsListView.this.invalidate();
                }
            });
            this.mAnimator.addListener(new Animator.AnimatorListener() { // from class: android.widget.AbsListView.15
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    AbsListView absListView = AbsListView.this;
                    absListView.mFocusedPos = absListView.mNewFocusedPos;
                }
            });
        }
    }

    private void drawIndicator(Canvas canvas) {
        float f;
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            this.mIndicatorItemCnt = listAdapter.getCount();
        }
        if (this.mIndicatorItemCnt < 2) {
            return;
        }
        if (this.mIndicatorPaint == null) {
            Paint paint = new Paint(1);
            this.mIndicatorPaint = paint;
            paint.setColor(this.mContext.getResources().getColor(R.color.sem_indicator_color));
        }
        if (this.mIndicatorIndex == null) {
            this.mIndicatorIndex = new ArrayList();
        }
        if (!this.mIndicatorIndex.isEmpty() && this.mIndicatorIndex.size() != this.mIndicatorItemCnt) {
            this.mIndicatorIndex.clear();
        }
        int i = this.mScrollY;
        int iSave = canvas.save();
        canvas.translate(0.0f, i);
        int width = getWidth();
        int height = ((getHeight() / 2) - ((this.mIndicatorRectSize * Math.min(this.mIndicatorItemCnt, 20)) / 2)) - this.mIndicatorBottomPadding;
        int i2 = 0;
        while (true) {
            int i3 = this.mIndicatorItemCnt;
            if (i2 >= i3) {
                break;
            }
            int i4 = 19;
            if (i3 > 20 && i2 >= 40 - i3) {
                i4 = 19 - (((i3 - i2) - 1) / 2);
            } else if (40 - i3 >= 0) {
                i4 = i2;
            }
            this.mIndicatorIndex.add(Integer.valueOf(i4));
            i2++;
        }
        int size = this.mIndicatorIndex.size();
        if (size <= this.mFocusedPos || size <= this.mNewFocusedPos) {
            return;
        }
        for (int i5 = 0; i5 < this.mIndicatorItemCnt; i5++) {
            if (this.mIndicatorIndex.get(i5) == this.mIndicatorIndex.get(this.mFocusedPos)) {
                if (this.mIndicatorIndex.get(this.mFocusedPos) == this.mIndicatorIndex.get(this.mNewFocusedPos)) {
                    f = this.mIndicatorFocusedSize / 2.0f;
                    this.mIndicatorPaint.setAlpha(255);
                } else {
                    f = (this.mIndicatorFocusedSize - (this.mIndicatorAnimatedSize - this.mIndicatorDefaultSize)) / 2.0f;
                    this.mIndicatorPaint.setAlpha(127);
                }
            } else if (this.mIndicatorIndex.get(i5) == this.mIndicatorIndex.get(this.mNewFocusedPos)) {
                f = this.mIndicatorAnimatedSize / 2.0f;
                this.mIndicatorPaint.setAlpha(255);
            } else {
                this.mIndicatorPaint.setAlpha(127);
                f = this.mIndicatorDefaultSize / 2.0f;
            }
            int i6 = this.mIndicatorItemCnt;
            if (i6 <= 20 || i5 < 40 - i6 || i5 % 2 != 0) {
                canvas.drawCircle(this.mIndicatorWhere == 1 ? this.mIndicatorMarginHorizontal : width - this.mIndicatorMarginHorizontal, height + (this.mIndicatorRectSize * (this.mIndicatorIndex.get(i5).intValue() + 0.5f)), f, this.mIndicatorPaint);
            }
        }
        canvas.restoreToCount(iSave);
    }

    private void semSendBroadcastPositionInternal(String str, Intent intent) {
        String str2;
        String str3;
        String[] strArrSplit = str.split("/");
        if (strArrSplit.length <= 1 || (str2 = strArrSplit[0]) == null || strArrSplit[1] == null || str2.isEmpty() || strArrSplit[1].isEmpty()) {
            return;
        }
        intent.setPackage(strArrSplit[0]);
        intent.setComponent(new ComponentName(strArrSplit[0], strArrSplit[1]));
        if (strArrSplit.length == 3 && (str3 = strArrSplit[2]) != null && !str3.isEmpty()) {
            this.mContext.sendBroadcast(intent, strArrSplit[2]);
        } else {
            this.mContext.sendBroadcast(intent);
        }
    }

    void semSendBroadcastPosition(int i, int i2) {
        if (i < 0) {
            return;
        }
        if (i2 == 1) {
            if (!this.mAppWidgetGetCurrentPosition.isEmpty()) {
                Intent intent = new Intent(APPWIDGET_CURRENT_POSITION_ACTION);
                intent.putExtra(APPWIDGET_EXTRA_CURRENT_POSITION, i);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, this.mAppWidgetId);
                semSendBroadcastPositionInternal(this.mAppWidgetGetCurrentPosition, intent);
            }
            if (semIsTalkBackIsRunning() && Math.abs(this.mFocusedPos - i) == 1) {
                View childAt = this.mFocusedPos - i > 0 ? getChildAt(0) : getChildAt(1);
                if (childAt != null) {
                    childAt.requestAccessibilityFocus();
                    return;
                }
                return;
            }
            return;
        }
        if (i2 == 2 && !this.mAppWidgetGetFirstPosition.isEmpty()) {
            Intent intent2 = new Intent(APPWIDGET_FIRST_POSITION_ACTION);
            intent2.putExtra(APPWIDGET_EXTRA_FIRST_POSITION, i);
            intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, this.mAppWidgetId);
            semSendBroadcastPositionInternal(this.mAppWidgetGetFirstPosition, intent2);
        }
    }

    void semInvalidateIndicator(int i) {
        if (i >= 0 && this.mNewFocusedPos != i) {
            this.mNewFocusedPos = i;
            ValueAnimator valueAnimator = this.mAnimator;
            if (valueAnimator == null || i == this.mFocusedPos || this.mIndicatorAnimatedSize != this.mIndicatorFocusedSize) {
                return;
            }
            if (valueAnimator.isRunning()) {
                this.mAnimator.cancel();
            }
            this.mAnimator.setDuration(200L);
            this.mAnimator.start();
        }
    }

    public void semSetAppWidgetSnapScroll(boolean z) {
        this.mAppWidgetSnapScroll = z;
    }

    public void semSetAppWidgetEnabled(boolean z) {
        this.mAppWidgetEnabled = z;
    }

    public void semSetAppWidgetGetCurrentPosition(String str) {
        this.mAppWidgetGetCurrentPosition = str;
    }

    public void semSetAppWidgetGetFirstPosition(String str) {
        this.mAppWidgetGetFirstPosition = str;
        semSendBroadcastPosition(this.mFirstPosition, 2);
    }

    public void semSetAppWidgetIndicator(boolean z) {
        this.mAppWidgetIndicator = z;
    }

    public void semSetAppWidgetIndicatorBottomPadding(int i) {
        this.mIndicatorBottomPadding = i;
    }

    public void semSetAppWidgetIndicatorMarginHorizontal(int i) {
        this.mIndicatorMarginHorizontal = i;
    }

    public void semSetAppWidgetIndicatorWhere(int i) {
        this.mIndicatorWhere = i;
    }

    @Override // android.view.View
    public void semSetScrollBarBottomPadding(int i) {
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller != null) {
            semFastScroller.semSetScrollBarBottomPadding(i);
        } else {
            super.semSetScrollBarBottomPadding(i);
        }
    }

    @Override // android.view.View
    public void semSetScrollBarTopPadding(int i) {
        SemFastScroller semFastScroller = this.mSemFastScroll;
        if (semFastScroller != null) {
            semFastScroller.semSetScrollBarTopPadding(i);
        } else {
            super.semSetScrollBarTopPadding(i);
        }
    }

    public void semSetAppWidgetInnerFocus(boolean z) {
        this.mAppWidgetInnerFocus = z;
    }

    public void semSetAppWidgetImmersiveEnabled(boolean z) {
        this.mAppWidgetImmersiveEnalbed = z;
    }

    void viewSelectorLikeFocus(View view) {
        Rect rect = this.mSelectorRect;
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        View childAt = getChildAt(this.mSelectorPosition - this.mFirstPosition);
        if (childAt != null) {
            rect.top += childAt.getTop();
            rect.bottom += childAt.getTop();
        }
        Drawable drawable = this.mSelector;
        if (drawable != null) {
            drawable.setVisible(false, false);
            drawable.setState(StateSet.NOTHING);
            drawable.setBounds(rect);
            if (getVisibility() == 0) {
                drawable.setVisible(true, false);
            }
            updateSelectorState();
        }
    }

    public void semSetAppWidgetNeedLayoutSpecificDone(boolean z) {
        this.mNeedLayoutSpecificDone = z;
    }

    @RemotableViewMethod
    public void hidden_semSetVerticalFadingEdgeEnabled(boolean z) {
        setVerticalFadingEdgeEnabled(z);
    }

    @RemotableViewMethod
    public void hidden_semSetBottomFadingEdgeStrength(float f) {
        this.mBottomFadingEdgeStrength = f;
    }

    @RemotableViewMethod
    public void hidden_semSetTopFadingEdgeStrength(float f) {
        this.mTopFadingEdgeStrength = f;
    }

    @RemotableViewMethod
    public void hidden_setFadingEdgeLength(int i) {
        setFadingEdgeLength(i);
    }

    private class DifferentialFlingTarget implements DifferentialMotionFlingHelper.DifferentialMotionFlingTarget {
        private DifferentialFlingTarget() {
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public boolean startDifferentialMotionFling(float f) throws Resources.NotFoundException {
            stopDifferentialMotionFling();
            AbsListView.this.fling((int) f);
            return true;
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public void stopDifferentialMotionFling() throws Resources.NotFoundException {
            if (AbsListView.this.mFlingRunnable != null) {
                AbsListView.this.mFlingRunnable.endFling();
            }
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public float getScaledScrollFactor() {
            return -AbsListView.this.mVerticalScrollFactor;
        }
    }

    public void setSelectionForcely(int i) {
        this.mIsForceSelection = true;
        setSelection(i);
    }
}
