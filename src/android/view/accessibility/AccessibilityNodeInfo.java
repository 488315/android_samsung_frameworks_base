package android.view.accessibility;

import android.app.admin.DevicePolicyResources;
import android.graphics.Rect;
import android.graphics.Region;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AccessibilityClickableSpan;
import android.text.style.AccessibilityReplacementSpan;
import android.text.style.AccessibilityURLSpan;
import android.text.style.ClickableSpan;
import android.text.style.ReplacementSpan;
import android.text.style.URLSpan;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongArray;
import android.util.Size;
import android.view.View;
import android.view.ViewRootImpl;
import com.android.internal.R;
import com.android.internal.util.BitUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public class AccessibilityNodeInfo implements Parcelable {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN = "android.view.accessibility.action.ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN";
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_DIRECTION_INT = "android.view.accessibility.action.ARGUMENT_DIRECTION_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_X = "ACTION_ARGUMENT_MOVE_WINDOW_X";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_Y = "ACTION_ARGUMENT_MOVE_WINDOW_Y";
    public static final String ACTION_ARGUMENT_PRESS_AND_HOLD_DURATION_MILLIS_INT = "android.view.accessibility.action.ARGUMENT_PRESS_AND_HOLD_DURATION_MILLIS_INT";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SCROLL_AMOUNT_FLOAT = "android.view.accessibility.action.ARGUMENT_SCROLL_AMOUNT_FLOAT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_PARCELABLE = "android.view.accessibility.action.ARGUMENT_SELECTION_PARCELABLE";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    private static final int BOOLEAN_PROPERTY_ACCESSIBILITY_DATA_SENSITIVE = 33554432;
    private static final int BOOLEAN_PROPERTY_ACCESSIBILITY_FOCUSED = 1024;
    private static final int BOOLEAN_PROPERTY_CHECKABLE = 1;
    private static final int BOOLEAN_PROPERTY_CHECKED = 2;
    private static final int BOOLEAN_PROPERTY_CLICKABLE = 32;
    private static final int BOOLEAN_PROPERTY_CONTENT_INVALID = 65536;
    private static final int BOOLEAN_PROPERTY_CONTEXT_CLICKABLE = 131072;
    private static final int BOOLEAN_PROPERTY_DISMISSABLE = 16384;
    private static final int BOOLEAN_PROPERTY_EDITABLE = 4096;
    private static final int BOOLEAN_PROPERTY_ENABLED = 128;
    private static final int BOOLEAN_PROPERTY_FIELD_REQUIRED = 134217728;
    private static final int BOOLEAN_PROPERTY_FOCUSABLE = 4;
    private static final int BOOLEAN_PROPERTY_FOCUSED = 8;
    private static final int BOOLEAN_PROPERTY_IMPORTANCE = 262144;
    private static final int BOOLEAN_PROPERTY_IS_HEADING = 2097152;
    private static final int BOOLEAN_PROPERTY_IS_SHOWING_HINT = 1048576;
    private static final int BOOLEAN_PROPERTY_IS_TEXT_ENTRY_KEY = 4194304;
    private static final int BOOLEAN_PROPERTY_IS_TEXT_SELECTABLE = 8388608;
    private static final int BOOLEAN_PROPERTY_LONG_CLICKABLE = 64;
    private static final int BOOLEAN_PROPERTY_MULTI_LINE = 32768;
    private static final int BOOLEAN_PROPERTY_OPENS_POPUP = 8192;
    private static final int BOOLEAN_PROPERTY_PASSWORD = 256;
    private static final int BOOLEAN_PROPERTY_REQUEST_INITIAL_ACCESSIBILITY_FOCUS = 16777216;
    private static final int BOOLEAN_PROPERTY_SCREEN_READER_FOCUSABLE = 524288;
    private static final int BOOLEAN_PROPERTY_SCROLLABLE = 512;
    private static final int BOOLEAN_PROPERTY_SELECTED = 16;
    private static final int BOOLEAN_PROPERTY_SUPPORTS_GRANULAR_SCROLLING = 67108864;
    private static final int BOOLEAN_PROPERTY_VISIBLE_TO_USER = 2048;
    public static final int CHECKED_STATE_FALSE = 0;
    public static final int CHECKED_STATE_PARTIAL = 2;
    public static final int CHECKED_STATE_TRUE = 1;
    public static final Parcelable.Creator<AccessibilityNodeInfo> CREATOR;
    private static final boolean DEBUG;
    private static final AccessibilityNodeInfo DEFAULT;
    public static final int EXPANDED_STATE_COLLAPSED = 1;
    public static final int EXPANDED_STATE_FULL = 3;
    public static final int EXPANDED_STATE_PARTIAL = 2;
    public static final int EXPANDED_STATE_UNDEFINED = 0;
    public static final String EXTRA_DATA_RENDERING_INFO_KEY = "android.view.accessibility.extra.DATA_RENDERING_INFO_KEY";
    public static final String EXTRA_DATA_REQUESTED_KEY = "android.view.accessibility.AccessibilityNodeInfo.extra_data_requested";
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH";
    public static final int EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH = 20000;
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX";
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_IN_WINDOW_KEY = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_IN_WINDOW_KEY";
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY";
    public static final int FLAG_PREFETCH_ANCESTORS = 1;
    public static final int FLAG_PREFETCH_DESCENDANTS_BREADTH_FIRST = 16;
    public static final int FLAG_PREFETCH_DESCENDANTS_DEPTH_FIRST = 8;
    public static final int FLAG_PREFETCH_DESCENDANTS_HYBRID = 4;
    public static final int FLAG_PREFETCH_DESCENDANTS_MASK = 28;
    public static final int FLAG_PREFETCH_MASK = 63;
    public static final int FLAG_PREFETCH_SIBLINGS = 2;
    public static final int FLAG_PREFETCH_UNINTERRUPTIBLE = 32;
    public static final int FLAG_REPORT_MASK = 896;
    public static final int FLAG_SERVICE_IS_ACCESSIBILITY_TOOL = 512;
    public static final int FLAG_SERVICE_REQUESTS_INCLUDE_NOT_IMPORTANT_VIEWS = 128;
    public static final int FLAG_SERVICE_REQUESTS_REPORT_VIEW_IDS = 256;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    private static final int INVALID_ACTIONS_MASK = -4194304;
    public static final int LAST_LEGACY_STANDARD_ACTION = 2097152;
    public static final int LEASHED_ITEM_ID = 2147483645;
    public static final long LEASHED_NODE_ID;
    public static final int MAX_NUMBER_OF_PREFETCHED_NODES = 50;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    public static final int ROOT_ITEM_ID = 2147483646;
    public static final long ROOT_NODE_ID;
    public static final int SEM_ACTION_AUTOSCROLL_DOWN = 33554432;
    public static final int SEM_ACTION_AUTOSCROLL_OFF = 8388608;
    public static final int SEM_ACTION_AUTOSCROLL_ON = 4194304;
    public static final int SEM_ACTION_AUTOSCROLL_SPEED_DOWN = 536870912;
    public static final int SEM_ACTION_AUTOSCROLL_SPEED_UP = 268435456;
    public static final int SEM_ACTION_AUTOSCROLL_TOP = 67108864;
    public static final int SEM_ACTION_AUTOSCROLL_UP = 16777216;
    private static final String TAG = "AccessibilityNodeInfo";
    public static final int UNDEFINED_CONNECTION_ID = -1;
    public static final int UNDEFINED_ITEM_ID = Integer.MAX_VALUE;
    public static final long UNDEFINED_NODE_ID;
    public static final int UNDEFINED_SELECTION_INDEX = -1;
    private static final long VIRTUAL_DESCENDANT_ID_MASK = -4294967296L;
    private static final int VIRTUAL_DESCENDANT_ID_SHIFT = 32;
    private ArrayList<AccessibilityAction> mActions;
    private int mBooleanProperties;
    private final Rect mBoundsInParent;
    private final Rect mBoundsInScreen;
    private final Rect mBoundsInWindow;
    private int mChecked;
    private LongArray mChildNodeIds;
    private CharSequence mClassName;
    private CollectionInfo mCollectionInfo;
    private CollectionItemInfo mCollectionItemInfo;
    private int mConnectionId;
    private CharSequence mContainerTitle;
    private CharSequence mContentDescription;
    private int mDrawingOrderInParent;
    private CharSequence mError;
    private int mExpandedState;
    private ArrayList<String> mExtraDataKeys;
    private ExtraRenderingInfo mExtraRenderingInfo;
    private Bundle mExtras;
    private CharSequence mHintText;
    private int mInputType;
    private long mLabelForId;
    private long mLabeledById;
    private LongArray mLabeledByIds;
    private IBinder mLeashedChild;
    private IBinder mLeashedParent;
    private long mLeashedParentNodeId;
    private int mLiveRegion;
    private int mMaxTextLength;
    private long mMinDurationBetweenContentChanges;
    private int mMovementGranularities;
    private CharSequence mOriginalText;
    private CharSequence mPackageName;
    private CharSequence mPaneTitle;
    private long mParentNodeId;
    private RangeInfo mRangeInfo;
    private boolean mSealed;
    private Selection mSelection;
    private long mSourceNodeId;
    private CharSequence mStateDescription;
    private CharSequence mSupplementalDescription;
    private CharSequence mText;
    private int mTextSelectionEnd;
    private int mTextSelectionStart;
    private CharSequence mTooltipText;
    private TouchDelegateInfo mTouchDelegateInfo;
    private long mTraversalAfter;
    private long mTraversalBefore;
    private String mUniqueId;
    private String mViewIdResourceName;
    private int mWindowId = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CheckedState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ExpandedState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrefetchingStrategy {
    }

    public static int getAccessibilityViewId(long j) {
        return (int) j;
    }

    public static int getVirtualDescendantId(long j) {
        return (int) ((j & VIRTUAL_DESCENDANT_ID_MASK) >> 32);
    }

    public static long makeNodeId(int i, int i2) {
        return i | (i2 << 32);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public void recycle() {
    }

    static {
        DEBUG = Log.isLoggable(TAG, 3) && Build.IS_DEBUGGABLE;
        UNDEFINED_NODE_ID = makeNodeId(Integer.MAX_VALUE, Integer.MAX_VALUE);
        ROOT_NODE_ID = makeNodeId(2147483646, -1);
        LEASHED_NODE_ID = makeNodeId(LEASHED_ITEM_ID, -1);
        DEFAULT = new AccessibilityNodeInfo();
        CREATOR = new Parcelable.Creator<AccessibilityNodeInfo>() { // from class: android.view.accessibility.AccessibilityNodeInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AccessibilityNodeInfo createFromParcel(Parcel parcel) {
                AccessibilityNodeInfo accessibilityNodeInfo = new AccessibilityNodeInfo();
                accessibilityNodeInfo.initFromParcel(parcel);
                return accessibilityNodeInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AccessibilityNodeInfo[] newArray(int i) {
                return new AccessibilityNodeInfo[i];
            }
        };
    }

    public AccessibilityNodeInfo() {
        long j = UNDEFINED_NODE_ID;
        this.mSourceNodeId = j;
        this.mParentNodeId = j;
        this.mLabelForId = j;
        this.mLabeledById = j;
        this.mTraversalBefore = j;
        this.mTraversalAfter = j;
        this.mMinDurationBetweenContentChanges = 0L;
        this.mBoundsInParent = new Rect();
        this.mBoundsInScreen = new Rect();
        this.mBoundsInWindow = new Rect();
        this.mMaxTextLength = -1;
        this.mTextSelectionStart = -1;
        this.mTextSelectionEnd = -1;
        this.mInputType = 0;
        this.mLiveRegion = 0;
        this.mConnectionId = -1;
        this.mLeashedParentNodeId = j;
    }

    public AccessibilityNodeInfo(View view) {
        long j = UNDEFINED_NODE_ID;
        this.mSourceNodeId = j;
        this.mParentNodeId = j;
        this.mLabelForId = j;
        this.mLabeledById = j;
        this.mTraversalBefore = j;
        this.mTraversalAfter = j;
        this.mMinDurationBetweenContentChanges = 0L;
        this.mBoundsInParent = new Rect();
        this.mBoundsInScreen = new Rect();
        this.mBoundsInWindow = new Rect();
        this.mMaxTextLength = -1;
        this.mTextSelectionStart = -1;
        this.mTextSelectionEnd = -1;
        this.mInputType = 0;
        this.mLiveRegion = 0;
        this.mConnectionId = -1;
        this.mLeashedParentNodeId = j;
        setSource(view);
    }

    public AccessibilityNodeInfo(View view, int i) {
        long j = UNDEFINED_NODE_ID;
        this.mSourceNodeId = j;
        this.mParentNodeId = j;
        this.mLabelForId = j;
        this.mLabeledById = j;
        this.mTraversalBefore = j;
        this.mTraversalAfter = j;
        this.mMinDurationBetweenContentChanges = 0L;
        this.mBoundsInParent = new Rect();
        this.mBoundsInScreen = new Rect();
        this.mBoundsInWindow = new Rect();
        this.mMaxTextLength = -1;
        this.mTextSelectionStart = -1;
        this.mTextSelectionEnd = -1;
        this.mInputType = 0;
        this.mLiveRegion = 0;
        this.mConnectionId = -1;
        this.mLeashedParentNodeId = j;
        setSource(view, i);
    }

    public AccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        long j = UNDEFINED_NODE_ID;
        this.mSourceNodeId = j;
        this.mParentNodeId = j;
        this.mLabelForId = j;
        this.mLabeledById = j;
        this.mTraversalBefore = j;
        this.mTraversalAfter = j;
        this.mMinDurationBetweenContentChanges = 0L;
        this.mBoundsInParent = new Rect();
        this.mBoundsInScreen = new Rect();
        this.mBoundsInWindow = new Rect();
        this.mMaxTextLength = -1;
        this.mTextSelectionStart = -1;
        this.mTextSelectionEnd = -1;
        this.mInputType = 0;
        this.mLiveRegion = 0;
        this.mConnectionId = -1;
        this.mLeashedParentNodeId = j;
        init(accessibilityNodeInfo);
    }

    public void setSource(View view) {
        setSource(view, -1);
    }

    public void setSource(View view, int i) {
        enforceNotSealed();
        this.mWindowId = view != null ? view.getAccessibilityWindowId() : Integer.MAX_VALUE;
        this.mSourceNodeId = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
    }

    public AccessibilityNodeInfo findFocus(int i) {
        enforceSealed();
        enforceValidFocusType(i);
        if (canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, this.mWindowId, this.mSourceNodeId, i);
        }
        return null;
    }

    public AccessibilityNodeInfo focusSearch(int i) {
        enforceSealed();
        enforceValidFocusDirection(i);
        if (canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return AccessibilityInteractionClient.getInstance().focusSearch(this.mConnectionId, this.mWindowId, this.mSourceNodeId, i);
        }
        return null;
    }

    public int getWindowId() {
        return this.mWindowId;
    }

    public boolean refresh(Bundle bundle, boolean z) {
        AccessibilityNodeInfo accessibilityNodeInfoFindAccessibilityNodeInfoByAccessibilityId;
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId) || (accessibilityNodeInfoFindAccessibilityNodeInfoByAccessibilityId = AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mWindowId, this.mSourceNodeId, z, 0, bundle)) == null) {
            return false;
        }
        init(accessibilityNodeInfoFindAccessibilityNodeInfoByAccessibilityId);
        return true;
    }

    public boolean refresh() {
        return refresh(null, true);
    }

    public boolean refreshWithExtraData(String str, Bundle bundle) {
        if (bundle.getInt(EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH, -1) > 20000) {
            bundle.putInt(EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH, 20000);
        }
        bundle.putString(EXTRA_DATA_REQUESTED_KEY, str);
        return refresh(bundle, true);
    }

    public LongArray getChildNodeIds() {
        return this.mChildNodeIds;
    }

    public long getChildId(int i) {
        LongArray longArray = this.mChildNodeIds;
        if (longArray == null) {
            throw new IndexOutOfBoundsException();
        }
        return longArray.get(i);
    }

    public int getChildCount() {
        LongArray longArray = this.mChildNodeIds;
        if (longArray == null) {
            return 0;
        }
        return longArray.size();
    }

    public AccessibilityNodeInfo getChild(int i) {
        return getChild(i, 4);
    }

    public AccessibilityNodeInfo getChild(int i, int i2) {
        enforceSealed();
        if (this.mChildNodeIds == null || !canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        long j = this.mChildNodeIds.get(i);
        AccessibilityInteractionClient accessibilityInteractionClient = AccessibilityInteractionClient.getInstance();
        IBinder iBinder = this.mLeashedChild;
        if (iBinder != null && j == LEASHED_NODE_ID) {
            return accessibilityInteractionClient.findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, iBinder, ROOT_NODE_ID, false, i2, (Bundle) null);
        }
        return accessibilityInteractionClient.findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mWindowId, j, false, i2, (Bundle) null);
    }

    public void addChild(View view) {
        addChildInternal(view, -1, true);
    }

    public void addChild(IBinder iBinder) {
        enforceNotSealed();
        if (iBinder == null) {
            return;
        }
        if (this.mChildNodeIds == null) {
            this.mChildNodeIds = new LongArray();
        }
        this.mLeashedChild = iBinder;
        LongArray longArray = this.mChildNodeIds;
        long j = LEASHED_NODE_ID;
        if (longArray.indexOf(j) >= 0) {
            return;
        }
        this.mChildNodeIds.add(j);
    }

    public void addChildUnchecked(View view) {
        addChildInternal(view, -1, false);
    }

    public boolean removeChild(View view) {
        return removeChild(view, -1);
    }

    public boolean removeChild(IBinder iBinder) {
        IBinder iBinder2;
        enforceNotSealed();
        if (this.mChildNodeIds == null || (iBinder2 = this.mLeashedChild) == null || !iBinder2.equals(iBinder)) {
            return false;
        }
        int iIndexOf = this.mChildNodeIds.indexOf(LEASHED_NODE_ID);
        this.mLeashedChild = null;
        if (iIndexOf < 0) {
            return false;
        }
        this.mChildNodeIds.remove(iIndexOf);
        return true;
    }

    public void addChild(View view, int i) {
        addChildInternal(view, i, true);
    }

    private void addChildInternal(View view, int i, boolean z) {
        enforceNotSealed();
        if (this.mChildNodeIds == null) {
            this.mChildNodeIds = new LongArray();
        }
        long jMakeNodeId = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
        if (jMakeNodeId == this.mSourceNodeId) {
            Log.e(TAG, "Rejecting attempt to make a View its own child");
        } else if (!z || this.mChildNodeIds.indexOf(jMakeNodeId) < 0) {
            this.mChildNodeIds.add(jMakeNodeId);
        }
    }

    public boolean removeChild(View view, int i) {
        enforceNotSealed();
        LongArray longArray = this.mChildNodeIds;
        if (longArray == null) {
            return false;
        }
        int iIndexOf = longArray.indexOf(makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i));
        if (iIndexOf < 0) {
            return false;
        }
        longArray.remove(iIndexOf);
        return true;
    }

    public List<AccessibilityAction> getActionList() {
        return CollectionUtils.emptyIfNull(this.mActions);
    }

    @Deprecated
    public int getActions() {
        ArrayList<AccessibilityAction> arrayList = this.mActions;
        if (arrayList == null) {
            return 0;
        }
        int size = arrayList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int id = this.mActions.get(i2).getId();
            if (id <= 2097152) {
                i |= id;
            }
        }
        return i;
    }

    public void addAction(AccessibilityAction accessibilityAction) {
        enforceNotSealed();
        addActionUnchecked(accessibilityAction);
    }

    private void addActionUnchecked(AccessibilityAction accessibilityAction) {
        if (accessibilityAction == null) {
            return;
        }
        if (this.mActions == null) {
            this.mActions = new ArrayList<>();
        }
        this.mActions.remove(accessibilityAction);
        this.mActions.add(accessibilityAction);
    }

    @Deprecated
    public void addAction(int i) {
        enforceNotSealed();
        if ((INVALID_ACTIONS_MASK & i) != 0) {
            throw new IllegalArgumentException("Action is not a combination of the standard actions: " + i);
        }
        addStandardActions(i);
    }

    @Deprecated
    public void removeAction(int i) {
        enforceNotSealed();
        removeAction(getActionSingleton(i));
    }

    public boolean removeAction(AccessibilityAction accessibilityAction) {
        enforceNotSealed();
        ArrayList<AccessibilityAction> arrayList = this.mActions;
        if (arrayList == null || accessibilityAction == null) {
            return false;
        }
        return arrayList.remove(accessibilityAction);
    }

    public void removeAllActions() {
        ArrayList<AccessibilityAction> arrayList = this.mActions;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public AccessibilityNodeInfo getTraversalBefore() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTraversalBefore);
    }

    public void setTraversalBefore(View view) {
        setTraversalBefore(view, -1);
    }

    public void setTraversalBefore(View view, int i) {
        enforceNotSealed();
        this.mTraversalBefore = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
    }

    public AccessibilityNodeInfo getTraversalAfter() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTraversalAfter);
    }

    public void setTraversalAfter(View view) {
        setTraversalAfter(view, -1);
    }

    public void setTraversalAfter(View view, int i) {
        enforceNotSealed();
        this.mTraversalAfter = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
    }

    public List<String> getAvailableExtraData() {
        ArrayList<String> arrayList = this.mExtraDataKeys;
        if (arrayList != null) {
            return Collections.unmodifiableList(arrayList);
        }
        return Collections.EMPTY_LIST;
    }

    public void setAvailableExtraData(List<String> list) {
        enforceNotSealed();
        this.mExtraDataKeys = new ArrayList<>(list);
    }

    public void setMaxTextLength(int i) {
        enforceNotSealed();
        this.mMaxTextLength = i;
    }

    public int getMaxTextLength() {
        return this.mMaxTextLength;
    }

    public void setMovementGranularities(int i) {
        enforceNotSealed();
        this.mMovementGranularities = i;
    }

    public int getMovementGranularities() {
        return this.mMovementGranularities;
    }

    public void setExpandedState(int i) {
        enforceValidExpandedState(i);
        enforceNotSealed();
        this.mExpandedState = i;
    }

    public int getExpandedState() {
        return this.mExpandedState;
    }

    public void setMinDurationBetweenContentChanges(Duration duration) {
        enforceNotSealed();
        this.mMinDurationBetweenContentChanges = duration.toMillis();
    }

    public Duration getMinDurationBetweenContentChanges() {
        return Duration.ofMillis(this.mMinDurationBetweenContentChanges);
    }

    public boolean performAction(int i) {
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return false;
        }
        AccessibilityInteractionClient accessibilityInteractionClient = AccessibilityInteractionClient.getInstance();
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            bundle = null;
        }
        return accessibilityInteractionClient.performAccessibilityAction(this.mConnectionId, this.mWindowId, this.mSourceNodeId, i, bundle);
    }

    public boolean performAction(int i, Bundle bundle) {
        enforceSealed();
        if (canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return AccessibilityInteractionClient.getInstance().performAccessibilityAction(this.mConnectionId, this.mWindowId, this.mSourceNodeId, i, bundle);
        }
        return false;
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String str) {
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return Collections.EMPTY_LIST;
        }
        return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfosByText(this.mConnectionId, this.mWindowId, this.mSourceNodeId, str);
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(String str) {
        enforceSealed();
        if (str == null) {
            Log.e(TAG, "returns empty list due to null viewId.");
            return Collections.EMPTY_LIST;
        }
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return Collections.EMPTY_LIST;
        }
        return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfosByViewId(this.mConnectionId, this.mWindowId, this.mSourceNodeId, str);
    }

    public AccessibilityWindowInfo getWindow() {
        enforceSealed();
        if (canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return AccessibilityInteractionClient.getInstance().getWindow(this.mConnectionId, this.mWindowId);
        }
        return null;
    }

    public AccessibilityNodeInfo getParent() {
        enforceSealed();
        IBinder iBinder = this.mLeashedParent;
        if (iBinder != null) {
            long j = this.mLeashedParentNodeId;
            if (j != UNDEFINED_NODE_ID) {
                return getNodeForAccessibilityId(this.mConnectionId, iBinder, j, 3);
            }
        }
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mParentNodeId);
    }

    public AccessibilityNodeInfo getParent(int i) {
        enforceSealed();
        IBinder iBinder = this.mLeashedParent;
        if (iBinder != null) {
            long j = this.mLeashedParentNodeId;
            if (j != UNDEFINED_NODE_ID) {
                return getNodeForAccessibilityId(this.mConnectionId, iBinder, j, i);
            }
        }
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mParentNodeId, i);
    }

    public long getParentNodeId() {
        return this.mParentNodeId;
    }

    public void setParent(View view) {
        setParent(view, -1);
    }

    public void setParent(View view, int i) {
        enforceNotSealed();
        this.mParentNodeId = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
    }

    @Deprecated
    public void getBoundsInParent(Rect rect) {
        rect.set(this.mBoundsInParent.left, this.mBoundsInParent.top, this.mBoundsInParent.right, this.mBoundsInParent.bottom);
    }

    @Deprecated
    public void setBoundsInParent(Rect rect) {
        enforceNotSealed();
        this.mBoundsInParent.set(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void getBoundsInScreen(Rect rect) {
        rect.set(this.mBoundsInScreen.left, this.mBoundsInScreen.top, this.mBoundsInScreen.right, this.mBoundsInScreen.bottom);
    }

    public Rect getBoundsInScreen() {
        return this.mBoundsInScreen;
    }

    public void setBoundsInScreen(Rect rect) {
        enforceNotSealed();
        this.mBoundsInScreen.set(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void getBoundsInWindow(Rect rect) {
        rect.set(this.mBoundsInWindow.left, this.mBoundsInWindow.top, this.mBoundsInWindow.right, this.mBoundsInWindow.bottom);
    }

    public Rect getBoundsInWindow() {
        return this.mBoundsInWindow;
    }

    public void setBoundsInWindow(Rect rect) {
        enforceNotSealed();
        this.mBoundsInWindow.set(rect);
    }

    public boolean isCheckable() {
        return getBooleanProperty(1);
    }

    public void setCheckable(boolean z) {
        setBooleanProperty(1, z);
    }

    @Deprecated
    public boolean isChecked() {
        return getBooleanProperty(2);
    }

    @Deprecated
    public void setChecked(boolean z) {
        setBooleanProperty(2, z);
        if (Flags.triStateChecked()) {
            this.mChecked = z ? 1 : 0;
        }
    }

    public int getChecked() {
        return this.mChecked;
    }

    public void setChecked(int i) {
        enforceNotSealed();
        if (i == 0 || i == 1 || i == 2) {
            this.mChecked = i;
            setBooleanProperty(2, i == 1);
        } else {
            throw new IllegalArgumentException("Unknown checked argument: " + i);
        }
    }

    public boolean isFieldRequired() {
        return getBooleanProperty(134217728);
    }

    public void setFieldRequired(boolean z) {
        setBooleanProperty(134217728, z);
    }

    public boolean isFocusable() {
        return getBooleanProperty(4);
    }

    public void setFocusable(boolean z) {
        setBooleanProperty(4, z);
    }

    public boolean isFocused() {
        return getBooleanProperty(8);
    }

    public void setFocused(boolean z) {
        setBooleanProperty(8, z);
    }

    public void setSelection(Selection selection) {
        enforceNotSealed();
        this.mSelection = selection;
    }

    public Selection getSelection() {
        Selection selection = this.mSelection;
        if (selection != null) {
            selection.getStart().setWindowId(this.mWindowId);
            this.mSelection.getStart().setConnectionId(this.mConnectionId);
            this.mSelection.getEnd().setWindowId(this.mWindowId);
            this.mSelection.getEnd().setConnectionId(this.mConnectionId);
        }
        return this.mSelection;
    }

    public boolean isVisibleToUser() {
        return getBooleanProperty(2048);
    }

    public void setVisibleToUser(boolean z) {
        setBooleanProperty(2048, z);
    }

    public boolean isAccessibilityFocused() {
        return getBooleanProperty(1024);
    }

    public void setAccessibilityFocused(boolean z) {
        setBooleanProperty(1024, z);
    }

    public boolean isSelected() {
        return getBooleanProperty(16);
    }

    public void setSelected(boolean z) {
        setBooleanProperty(16, z);
    }

    public boolean isClickable() {
        return getBooleanProperty(32);
    }

    public void setClickable(boolean z) {
        setBooleanProperty(32, z);
    }

    public boolean isLongClickable() {
        return getBooleanProperty(64);
    }

    public void setLongClickable(boolean z) {
        setBooleanProperty(64, z);
    }

    public boolean isEnabled() {
        return getBooleanProperty(128);
    }

    public void setEnabled(boolean z) {
        setBooleanProperty(128, z);
    }

    public boolean isPassword() {
        return getBooleanProperty(256);
    }

    public void setPassword(boolean z) {
        setBooleanProperty(256, z);
    }

    public boolean isScrollable() {
        return getBooleanProperty(512);
    }

    public void setScrollable(boolean z) {
        setBooleanProperty(512, z);
    }

    public boolean isGranularScrollingSupported() {
        return getBooleanProperty(67108864);
    }

    public void setGranularScrollingSupported(boolean z) {
        setBooleanProperty(67108864, z);
    }

    public boolean isTextSelectable() {
        return getBooleanProperty(8388608);
    }

    public void setTextSelectable(boolean z) {
        setBooleanProperty(8388608, z);
    }

    public boolean hasRequestInitialAccessibilityFocus() {
        return getBooleanProperty(16777216);
    }

    public void setRequestInitialAccessibilityFocus(boolean z) {
        setBooleanProperty(16777216, z);
    }

    public boolean isEditable() {
        return getBooleanProperty(4096);
    }

    public void setEditable(boolean z) {
        setBooleanProperty(4096, z);
    }

    public boolean isAccessibilityDataSensitive() {
        return getBooleanProperty(33554432);
    }

    public void setAccessibilityDataSensitive(boolean z) {
        setBooleanProperty(33554432, z);
    }

    public void setPaneTitle(CharSequence charSequence) {
        enforceNotSealed();
        this.mPaneTitle = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public CharSequence getPaneTitle() {
        return this.mPaneTitle;
    }

    public int getDrawingOrder() {
        return this.mDrawingOrderInParent;
    }

    public void setDrawingOrder(int i) {
        enforceNotSealed();
        this.mDrawingOrderInParent = i;
    }

    public CollectionInfo getCollectionInfo() {
        return this.mCollectionInfo;
    }

    public void setCollectionInfo(CollectionInfo collectionInfo) {
        enforceNotSealed();
        this.mCollectionInfo = collectionInfo;
    }

    public CollectionItemInfo getCollectionItemInfo() {
        return this.mCollectionItemInfo;
    }

    public void setCollectionItemInfo(CollectionItemInfo collectionItemInfo) {
        enforceNotSealed();
        this.mCollectionItemInfo = collectionItemInfo;
    }

    public RangeInfo getRangeInfo() {
        return this.mRangeInfo;
    }

    public void setRangeInfo(RangeInfo rangeInfo) {
        enforceNotSealed();
        this.mRangeInfo = rangeInfo;
    }

    public ExtraRenderingInfo getExtraRenderingInfo() {
        return this.mExtraRenderingInfo;
    }

    public void setExtraRenderingInfo(ExtraRenderingInfo extraRenderingInfo) {
        enforceNotSealed();
        this.mExtraRenderingInfo = extraRenderingInfo;
    }

    public boolean isContentInvalid() {
        return getBooleanProperty(65536);
    }

    public void setContentInvalid(boolean z) {
        setBooleanProperty(65536, z);
    }

    public boolean isContextClickable() {
        return getBooleanProperty(131072);
    }

    public void setContextClickable(boolean z) {
        setBooleanProperty(131072, z);
    }

    public int getLiveRegion() {
        return this.mLiveRegion;
    }

    public void setLiveRegion(int i) {
        enforceNotSealed();
        this.mLiveRegion = i;
    }

    public boolean isMultiLine() {
        return getBooleanProperty(32768);
    }

    public void setMultiLine(boolean z) {
        setBooleanProperty(32768, z);
    }

    public boolean canOpenPopup() {
        return getBooleanProperty(8192);
    }

    public void setCanOpenPopup(boolean z) {
        enforceNotSealed();
        setBooleanProperty(8192, z);
    }

    public boolean isDismissable() {
        return getBooleanProperty(16384);
    }

    public void setDismissable(boolean z) {
        setBooleanProperty(16384, z);
    }

    public boolean isImportantForAccessibility() {
        return getBooleanProperty(262144);
    }

    public void setImportantForAccessibility(boolean z) {
        setBooleanProperty(262144, z);
    }

    public boolean isScreenReaderFocusable() {
        return getBooleanProperty(524288);
    }

    public void setScreenReaderFocusable(boolean z) {
        setBooleanProperty(524288, z);
    }

    public boolean isShowingHintText() {
        return getBooleanProperty(1048576);
    }

    public void setShowingHintText(boolean z) {
        setBooleanProperty(1048576, z);
    }

    public boolean isHeading() {
        if (getBooleanProperty(2097152)) {
            return true;
        }
        CollectionItemInfo collectionItemInfo = getCollectionItemInfo();
        return collectionItemInfo != null && collectionItemInfo.mHeading;
    }

    public void setHeading(boolean z) {
        setBooleanProperty(2097152, z);
    }

    public boolean isTextEntryKey() {
        return getBooleanProperty(4194304);
    }

    public void setTextEntryKey(boolean z) {
        setBooleanProperty(4194304, z);
    }

    public CharSequence getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(CharSequence charSequence) {
        enforceNotSealed();
        this.mPackageName = charSequence;
    }

    public CharSequence getClassName() {
        return this.mClassName;
    }

    public void setClassName(CharSequence charSequence) {
        enforceNotSealed();
        this.mClassName = charSequence;
    }

    public CharSequence getText() {
        CharSequence charSequence = this.mText;
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            for (AccessibilityClickableSpan accessibilityClickableSpan : (AccessibilityClickableSpan[]) spanned.getSpans(0, charSequence.length(), AccessibilityClickableSpan.class)) {
                accessibilityClickableSpan.copyConnectionDataFrom(this);
            }
            for (AccessibilityURLSpan accessibilityURLSpan : (AccessibilityURLSpan[]) spanned.getSpans(0, this.mText.length(), AccessibilityURLSpan.class)) {
                accessibilityURLSpan.copyConnectionDataFrom(this);
            }
        }
        return this.mText;
    }

    public CharSequence getOriginalText() {
        return this.mOriginalText;
    }

    public void setText(CharSequence charSequence) {
        enforceNotSealed();
        this.mOriginalText = charSequence;
        if (charSequence instanceof Spanned) {
            this.mText = replaceReplacementSpan(replaceClickableSpan(charSequence));
        } else {
            this.mText = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
        }
    }

    private CharSequence replaceClickableSpan(CharSequence charSequence) {
        Object accessibilityClickableSpan;
        ClickableSpan[] clickableSpanArr = (ClickableSpan[]) ((Spanned) charSequence).getSpans(0, charSequence.length(), ClickableSpan.class);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        if (clickableSpanArr.length == 0) {
            return charSequence;
        }
        for (ClickableSpan clickableSpan : clickableSpanArr) {
            if ((clickableSpan instanceof AccessibilityClickableSpan) || (clickableSpan instanceof AccessibilityURLSpan)) {
                break;
            }
            int spanStart = spannableStringBuilder.getSpanStart(clickableSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(clickableSpan);
            int spanFlags = spannableStringBuilder.getSpanFlags(clickableSpan);
            if (spanStart >= 0) {
                spannableStringBuilder.removeSpan(clickableSpan);
                if (clickableSpan instanceof URLSpan) {
                    accessibilityClickableSpan = new AccessibilityURLSpan((URLSpan) clickableSpan);
                } else {
                    accessibilityClickableSpan = new AccessibilityClickableSpan(clickableSpan.getId());
                }
                spannableStringBuilder.setSpan(accessibilityClickableSpan, spanStart, spanEnd, spanFlags);
            }
        }
        return spannableStringBuilder;
    }

    private CharSequence replaceReplacementSpan(CharSequence charSequence) {
        ReplacementSpan[] replacementSpanArr = (ReplacementSpan[]) ((Spanned) charSequence).getSpans(0, charSequence.length(), ReplacementSpan.class);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        if (replacementSpanArr.length == 0) {
            return charSequence;
        }
        for (ReplacementSpan replacementSpan : replacementSpanArr) {
            CharSequence contentDescription = replacementSpan.getContentDescription();
            if (replacementSpan instanceof AccessibilityReplacementSpan) {
                break;
            }
            if (contentDescription != null) {
                int spanStart = spannableStringBuilder.getSpanStart(replacementSpan);
                int spanEnd = spannableStringBuilder.getSpanEnd(replacementSpan);
                int spanFlags = spannableStringBuilder.getSpanFlags(replacementSpan);
                if (spanStart >= 0) {
                    spannableStringBuilder.removeSpan(replacementSpan);
                    spannableStringBuilder.setSpan(new AccessibilityReplacementSpan(contentDescription), spanStart, spanEnd, spanFlags);
                }
            }
        }
        return spannableStringBuilder;
    }

    public CharSequence getHintText() {
        return this.mHintText;
    }

    public void setHintText(CharSequence charSequence) {
        enforceNotSealed();
        this.mHintText = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public void setError(CharSequence charSequence) {
        enforceNotSealed();
        this.mError = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public CharSequence getError() {
        return this.mError;
    }

    public CharSequence getStateDescription() {
        return this.mStateDescription;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public CharSequence getSupplementalDescription() {
        return this.mSupplementalDescription;
    }

    public void setStateDescription(CharSequence charSequence) {
        enforceNotSealed();
        this.mStateDescription = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public void setContentDescription(CharSequence charSequence) {
        enforceNotSealed();
        this.mContentDescription = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public void setSupplementalDescription(CharSequence charSequence) {
        enforceNotSealed();
        this.mSupplementalDescription = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public CharSequence getTooltipText() {
        return this.mTooltipText;
    }

    public void setTooltipText(CharSequence charSequence) {
        enforceNotSealed();
        this.mTooltipText = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    @Deprecated
    public void setLabelFor(View view) {
        setLabelFor(view, -1);
    }

    @Deprecated
    public void setLabelFor(View view, int i) {
        enforceNotSealed();
        this.mLabelForId = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
    }

    @Deprecated
    public AccessibilityNodeInfo getLabelFor() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mLabelForId);
    }

    public void addLabeledBy(View view) {
        addLabeledBy(view, -1);
    }

    public void addLabeledBy(View view, int i) {
        enforceNotSealed();
        Preconditions.checkNotNull(view, "%s must not be null", view);
        if (this.mLabeledByIds == null) {
            this.mLabeledByIds = new LongArray();
        }
        long jMakeNodeId = makeNodeId(view.getAccessibilityViewId(), i);
        this.mLabeledById = jMakeNodeId;
        this.mLabeledByIds.add(jMakeNodeId);
    }

    public List<AccessibilityNodeInfo> getLabeledByList() {
        enforceSealed();
        ArrayList arrayList = new ArrayList();
        if (this.mLabeledByIds != null) {
            for (int i = 0; i < this.mLabeledByIds.size(); i++) {
                arrayList.add(getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mLabeledByIds.get(i)));
            }
        }
        return arrayList;
    }

    public boolean removeLabeledBy(View view) {
        return removeLabeledBy(view, -1);
    }

    public boolean removeLabeledBy(View view, int i) {
        enforceNotSealed();
        LongArray longArray = this.mLabeledByIds;
        if (longArray == null) {
            return false;
        }
        long jMakeNodeId = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
        if (this.mLabeledById == jMakeNodeId) {
            this.mLabeledById = UNDEFINED_NODE_ID;
        }
        int iIndexOf = longArray.indexOf(jMakeNodeId);
        if (iIndexOf < 0) {
            return false;
        }
        longArray.remove(iIndexOf);
        return true;
    }

    @Deprecated
    public void setLabeledBy(View view) {
        setLabeledBy(view, -1);
    }

    @Deprecated
    public void setLabeledBy(View view, int i) {
        enforceNotSealed();
        int accessibilityViewId = view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE;
        if (Flags.supportMultipleLabeledby()) {
            LongArray longArray = this.mLabeledByIds;
            if (longArray == null) {
                this.mLabeledByIds = new LongArray();
            } else {
                longArray.clear();
            }
        }
        this.mLabeledById = makeNodeId(accessibilityViewId, i);
        if (Flags.supportMultipleLabeledby()) {
            this.mLabeledByIds.add(this.mLabeledById);
        }
    }

    @Deprecated
    public AccessibilityNodeInfo getLabeledBy() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mLabeledById);
    }

    public void setViewIdResourceName(String str) {
        enforceNotSealed();
        this.mViewIdResourceName = str;
    }

    public String getViewIdResourceName() {
        return this.mViewIdResourceName;
    }

    public int getTextSelectionStart() {
        if (Flags.a11ySelectionApi()) {
            Selection selection = getSelection();
            if (selection != null && selection.getStart().usesNode(this) && selection.getEnd().usesNode(this)) {
                return selection.getStart().getOffset();
            }
            return -1;
        }
        return this.mTextSelectionStart;
    }

    public int getTextSelectionEnd() {
        if (Flags.a11ySelectionApi()) {
            Selection selection = getSelection();
            if (selection != null && selection.getStart().usesNode(this) && selection.getEnd().usesNode(this)) {
                return selection.getEnd().getOffset();
            }
            return -1;
        }
        return this.mTextSelectionEnd;
    }

    public void setTextSelection(int i, int i2) {
        enforceNotSealed();
        if (Flags.a11ySelectionApi()) {
            setSelection(new Selection(new SelectionPosition(this, i), new SelectionPosition(this, i2)));
        } else {
            this.mTextSelectionStart = i;
            this.mTextSelectionEnd = i2;
        }
    }

    public int getInputType() {
        return this.mInputType;
    }

    public void setInputType(int i) {
        enforceNotSealed();
        this.mInputType = i;
    }

    public Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        return this.mExtras;
    }

    public boolean hasExtras() {
        return this.mExtras != null;
    }

    public TouchDelegateInfo getTouchDelegateInfo() {
        TouchDelegateInfo touchDelegateInfo = this.mTouchDelegateInfo;
        if (touchDelegateInfo != null) {
            touchDelegateInfo.setConnectionId(this.mConnectionId);
            this.mTouchDelegateInfo.setWindowId(this.mWindowId);
        }
        return this.mTouchDelegateInfo;
    }

    public void setTouchDelegateInfo(TouchDelegateInfo touchDelegateInfo) {
        enforceNotSealed();
        this.mTouchDelegateInfo = touchDelegateInfo;
    }

    private boolean getBooleanProperty(int i) {
        return (this.mBooleanProperties & i) != 0;
    }

    private void setBooleanProperty(int i, boolean z) {
        enforceNotSealed();
        if (z) {
            this.mBooleanProperties = i | this.mBooleanProperties;
        } else {
            this.mBooleanProperties = (~i) & this.mBooleanProperties;
        }
    }

    public void setConnectionId(int i) {
        enforceNotSealed();
        this.mConnectionId = i;
    }

    public int getConnectionId() {
        return this.mConnectionId;
    }

    public void setSourceNodeId(long j, int i) {
        enforceNotSealed();
        this.mSourceNodeId = j;
        this.mWindowId = i;
    }

    public long getSourceNodeId() {
        return this.mSourceNodeId;
    }

    public void setUniqueId(String str) {
        enforceNotSealed();
        this.mUniqueId = str;
    }

    public String getUniqueId() {
        return this.mUniqueId;
    }

    public void setContainerTitle(CharSequence charSequence) {
        enforceNotSealed();
        this.mContainerTitle = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public CharSequence getContainerTitle() {
        return this.mContainerTitle;
    }

    public void setLeashedParent(IBinder iBinder, int i) {
        enforceNotSealed();
        this.mLeashedParent = iBinder;
        this.mLeashedParentNodeId = makeNodeId(i, -1);
    }

    public IBinder getLeashedParent() {
        return this.mLeashedParent;
    }

    public long getLeashedParentNodeId() {
        return this.mLeashedParentNodeId;
    }

    public void setQueryFromAppProcessEnabled(View view, boolean z) {
        enforceNotSealed();
        if (!z) {
            setConnectionId(-1);
        } else {
            if (this.mConnectionId != -1) {
                return;
            }
            ViewRootImpl viewRootImpl = view.getViewRootImpl();
            if (viewRootImpl == null) {
                throw new IllegalStateException("Cannot link a node to a view that is not attached to a window.");
            }
            setConnectionId(viewRootImpl.getDirectAccessibilityConnectionId());
        }
    }

    public void setSealed(boolean z) {
        this.mSealed = z;
    }

    public boolean isSealed() {
        return this.mSealed;
    }

    private static boolean usingDirectConnection(int i) {
        return AccessibilityInteractionClient.getConnection(i) instanceof DirectAccessibilityConnection;
    }

    protected void enforceSealed() {
        if (!usingDirectConnection(this.mConnectionId) && !isSealed()) {
            throw new IllegalStateException("Cannot perform this action on a not sealed instance.");
        }
    }

    private void enforceValidFocusDirection(int i) {
        if (i == 1 || i == 2 || i == 17 || i == 33 || i == 66 || i == 130) {
            return;
        }
        throw new IllegalArgumentException("Unknown direction: " + i);
    }

    private void enforceValidFocusType(int i) {
        if (i == 1 || i == 2) {
            return;
        }
        throw new IllegalArgumentException("Unknown focus type: " + i);
    }

    private void enforceValidExpandedState(int i) {
        if (!Flags.a11yExpansionStateApi() || i == 0 || i == 1 || i == 2 || i == 3) {
            return;
        }
        throw new IllegalArgumentException("Unknown expanded state: " + i);
    }

    protected void enforceNotSealed() {
        if (isSealed()) {
            throw new IllegalStateException("Cannot perform this action on a sealed instance.");
        }
    }

    @Deprecated
    public static AccessibilityNodeInfo obtain(View view) {
        return new AccessibilityNodeInfo(view);
    }

    @Deprecated
    public static AccessibilityNodeInfo obtain(View view, int i) {
        return new AccessibilityNodeInfo(view, i);
    }

    @Deprecated
    public static AccessibilityNodeInfo obtain() {
        return new AccessibilityNodeInfo();
    }

    @Deprecated
    public static AccessibilityNodeInfo obtain(AccessibilityNodeInfo accessibilityNodeInfo) {
        return new AccessibilityNodeInfo(accessibilityNodeInfo);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        writeToParcelNoRecycle(parcel, i);
    }

    public void writeToParcelNoRecycle(Parcel parcel, int i) throws IOException {
        boolean zIsSealed = isSealed();
        AccessibilityNodeInfo accessibilityNodeInfo = DEFAULT;
        long jBitAt = zIsSealed != accessibilityNodeInfo.isSealed() ? BitUtils.bitAt(0) : 0L;
        if (this.mSourceNodeId != accessibilityNodeInfo.mSourceNodeId) {
            jBitAt |= BitUtils.bitAt(1);
        }
        if (this.mWindowId != accessibilityNodeInfo.mWindowId) {
            jBitAt |= BitUtils.bitAt(2);
        }
        if (this.mParentNodeId != accessibilityNodeInfo.mParentNodeId) {
            jBitAt |= BitUtils.bitAt(3);
        }
        if (this.mLabelForId != accessibilityNodeInfo.mLabelForId) {
            jBitAt |= BitUtils.bitAt(4);
        }
        if (this.mLabeledById != accessibilityNodeInfo.mLabeledById) {
            jBitAt |= BitUtils.bitAt(5);
        }
        if (!LongArray.elementsEqual(this.mLabeledByIds, accessibilityNodeInfo.mLabeledByIds)) {
            jBitAt |= BitUtils.bitAt(6);
        }
        if (this.mTraversalBefore != accessibilityNodeInfo.mTraversalBefore) {
            jBitAt |= BitUtils.bitAt(7);
        }
        if (this.mTraversalAfter != accessibilityNodeInfo.mTraversalAfter) {
            jBitAt |= BitUtils.bitAt(8);
        }
        if (this.mMinDurationBetweenContentChanges != accessibilityNodeInfo.mMinDurationBetweenContentChanges) {
            jBitAt |= BitUtils.bitAt(9);
        }
        if (this.mConnectionId != accessibilityNodeInfo.mConnectionId) {
            jBitAt |= BitUtils.bitAt(10);
        }
        if (!LongArray.elementsEqual(this.mChildNodeIds, accessibilityNodeInfo.mChildNodeIds)) {
            jBitAt |= BitUtils.bitAt(11);
        }
        if (!Objects.equals(this.mBoundsInParent, accessibilityNodeInfo.mBoundsInParent)) {
            jBitAt |= BitUtils.bitAt(12);
        }
        if (!Objects.equals(this.mBoundsInScreen, accessibilityNodeInfo.mBoundsInScreen)) {
            jBitAt |= BitUtils.bitAt(13);
        }
        if (!Objects.equals(this.mBoundsInWindow, accessibilityNodeInfo.mBoundsInWindow)) {
            jBitAt |= BitUtils.bitAt(14);
        }
        if (!Objects.equals(this.mActions, accessibilityNodeInfo.mActions)) {
            jBitAt |= BitUtils.bitAt(15);
        }
        if (this.mMaxTextLength != accessibilityNodeInfo.mMaxTextLength) {
            jBitAt |= BitUtils.bitAt(16);
        }
        if (this.mMovementGranularities != accessibilityNodeInfo.mMovementGranularities) {
            jBitAt |= BitUtils.bitAt(17);
        }
        if (this.mBooleanProperties != accessibilityNodeInfo.mBooleanProperties) {
            jBitAt |= BitUtils.bitAt(18);
        }
        if (!Objects.equals(this.mPackageName, accessibilityNodeInfo.mPackageName)) {
            jBitAt |= BitUtils.bitAt(19);
        }
        if (!Objects.equals(this.mClassName, accessibilityNodeInfo.mClassName)) {
            jBitAt |= BitUtils.bitAt(20);
        }
        if (!Objects.equals(this.mText, accessibilityNodeInfo.mText)) {
            jBitAt |= BitUtils.bitAt(21);
        }
        if (!Objects.equals(this.mHintText, accessibilityNodeInfo.mHintText)) {
            jBitAt |= BitUtils.bitAt(22);
        }
        if (!Objects.equals(this.mError, accessibilityNodeInfo.mError)) {
            jBitAt |= BitUtils.bitAt(23);
        }
        if (!Objects.equals(this.mStateDescription, accessibilityNodeInfo.mStateDescription)) {
            jBitAt |= BitUtils.bitAt(24);
        }
        if (!Objects.equals(this.mContentDescription, accessibilityNodeInfo.mContentDescription)) {
            jBitAt |= BitUtils.bitAt(25);
        }
        if (!Objects.equals(this.mSupplementalDescription, accessibilityNodeInfo.mSupplementalDescription)) {
            jBitAt |= BitUtils.bitAt(26);
        }
        if (!Objects.equals(this.mPaneTitle, accessibilityNodeInfo.mPaneTitle)) {
            jBitAt |= BitUtils.bitAt(27);
        }
        if (!Objects.equals(this.mTooltipText, accessibilityNodeInfo.mTooltipText)) {
            jBitAt |= BitUtils.bitAt(28);
        }
        if (!Objects.equals(this.mContainerTitle, accessibilityNodeInfo.mContainerTitle)) {
            jBitAt |= BitUtils.bitAt(29);
        }
        if (!Objects.equals(this.mViewIdResourceName, accessibilityNodeInfo.mViewIdResourceName)) {
            jBitAt |= BitUtils.bitAt(30);
        }
        if (!Objects.equals(this.mUniqueId, accessibilityNodeInfo.mUniqueId)) {
            jBitAt |= BitUtils.bitAt(31);
        }
        if (this.mTextSelectionStart != accessibilityNodeInfo.mTextSelectionStart) {
            jBitAt |= BitUtils.bitAt(32);
        }
        if (this.mTextSelectionEnd != accessibilityNodeInfo.mTextSelectionEnd) {
            jBitAt |= BitUtils.bitAt(33);
        }
        if (this.mInputType != accessibilityNodeInfo.mInputType) {
            jBitAt |= BitUtils.bitAt(34);
        }
        if (this.mLiveRegion != accessibilityNodeInfo.mLiveRegion) {
            jBitAt |= BitUtils.bitAt(35);
        }
        if (this.mDrawingOrderInParent != accessibilityNodeInfo.mDrawingOrderInParent) {
            jBitAt |= BitUtils.bitAt(36);
        }
        if (!Objects.equals(this.mExtraDataKeys, accessibilityNodeInfo.mExtraDataKeys)) {
            jBitAt |= BitUtils.bitAt(37);
        }
        if (!Objects.equals(this.mExtras, accessibilityNodeInfo.mExtras)) {
            jBitAt |= BitUtils.bitAt(38);
        }
        if (!Objects.equals(this.mRangeInfo, accessibilityNodeInfo.mRangeInfo)) {
            jBitAt |= BitUtils.bitAt(39);
        }
        if (!Objects.equals(this.mCollectionInfo, accessibilityNodeInfo.mCollectionInfo)) {
            jBitAt |= BitUtils.bitAt(40);
        }
        if (!Objects.equals(this.mCollectionItemInfo, accessibilityNodeInfo.mCollectionItemInfo)) {
            jBitAt |= BitUtils.bitAt(41);
        }
        if (!Objects.equals(this.mTouchDelegateInfo, accessibilityNodeInfo.mTouchDelegateInfo)) {
            jBitAt |= BitUtils.bitAt(42);
        }
        if (!Objects.equals(this.mExtraRenderingInfo, accessibilityNodeInfo.mExtraRenderingInfo)) {
            jBitAt |= BitUtils.bitAt(43);
        }
        if (this.mLeashedChild != accessibilityNodeInfo.mLeashedChild) {
            jBitAt |= BitUtils.bitAt(44);
        }
        if (this.mLeashedParent != accessibilityNodeInfo.mLeashedParent) {
            jBitAt |= BitUtils.bitAt(45);
        }
        if (this.mLeashedParentNodeId != accessibilityNodeInfo.mLeashedParentNodeId) {
            jBitAt |= BitUtils.bitAt(46);
        }
        if (!Objects.equals(this.mSelection, accessibilityNodeInfo.mSelection)) {
            jBitAt |= BitUtils.bitAt(47);
        }
        if (this.mChecked != accessibilityNodeInfo.mChecked) {
            jBitAt |= BitUtils.bitAt(48);
        }
        if (this.mExpandedState != accessibilityNodeInfo.mExpandedState) {
            jBitAt |= BitUtils.bitAt(49);
        }
        parcel.writeLong(jBitAt);
        if (BitUtils.isBitSet(jBitAt, 0)) {
            parcel.writeInt(isSealed() ? 1 : 0);
        }
        if (BitUtils.isBitSet(jBitAt, 1)) {
            parcel.writeLong(this.mSourceNodeId);
        }
        if (BitUtils.isBitSet(jBitAt, 2)) {
            parcel.writeInt(this.mWindowId);
        }
        if (BitUtils.isBitSet(jBitAt, 3)) {
            parcel.writeLong(this.mParentNodeId);
        }
        if (BitUtils.isBitSet(jBitAt, 4)) {
            parcel.writeLong(this.mLabelForId);
        }
        if (BitUtils.isBitSet(jBitAt, 5)) {
            parcel.writeLong(this.mLabeledById);
        }
        if (BitUtils.isBitSet(jBitAt, 6)) {
            LongArray longArray = this.mLabeledByIds;
            if (longArray == null) {
                parcel.writeInt(0);
            } else {
                int size = longArray.size();
                parcel.writeInt(size);
                for (int i2 = 0; i2 < size; i2++) {
                    parcel.writeLong(longArray.get(i2));
                }
            }
        }
        if (BitUtils.isBitSet(jBitAt, 7)) {
            parcel.writeLong(this.mTraversalBefore);
        }
        if (BitUtils.isBitSet(jBitAt, 8)) {
            parcel.writeLong(this.mTraversalAfter);
        }
        if (BitUtils.isBitSet(jBitAt, 9)) {
            parcel.writeLong(this.mMinDurationBetweenContentChanges);
        }
        if (BitUtils.isBitSet(jBitAt, 10)) {
            parcel.writeInt(this.mConnectionId);
        }
        if (BitUtils.isBitSet(jBitAt, 11)) {
            LongArray longArray2 = this.mChildNodeIds;
            if (longArray2 == null) {
                parcel.writeInt(0);
            } else {
                int size2 = longArray2.size();
                parcel.writeInt(size2);
                for (int i3 = 0; i3 < size2; i3++) {
                    parcel.writeLong(longArray2.get(i3));
                }
            }
        }
        if (BitUtils.isBitSet(jBitAt, 12)) {
            parcel.writeInt(this.mBoundsInParent.top);
            parcel.writeInt(this.mBoundsInParent.bottom);
            parcel.writeInt(this.mBoundsInParent.left);
            parcel.writeInt(this.mBoundsInParent.right);
        }
        if (BitUtils.isBitSet(jBitAt, 13)) {
            parcel.writeInt(this.mBoundsInScreen.top);
            parcel.writeInt(this.mBoundsInScreen.bottom);
            parcel.writeInt(this.mBoundsInScreen.left);
            parcel.writeInt(this.mBoundsInScreen.right);
        }
        if (BitUtils.isBitSet(jBitAt, 14)) {
            parcel.writeInt(this.mBoundsInWindow.top);
            parcel.writeInt(this.mBoundsInWindow.bottom);
            parcel.writeInt(this.mBoundsInWindow.left);
            parcel.writeInt(this.mBoundsInWindow.right);
        }
        if (BitUtils.isBitSet(jBitAt, 15)) {
            ArrayList<AccessibilityAction> arrayList = this.mActions;
            if (arrayList != null && !arrayList.isEmpty()) {
                int size3 = this.mActions.size();
                int i4 = 0;
                long j = 0;
                for (int i5 = 0; i5 < size3; i5++) {
                    AccessibilityAction accessibilityAction = this.mActions.get(i5);
                    if (isDefaultStandardAction(accessibilityAction)) {
                        j |= accessibilityAction.mSerializationFlag;
                    } else {
                        i4++;
                    }
                }
                parcel.writeLong(j);
                parcel.writeInt(i4);
                for (int i6 = 0; i6 < size3; i6++) {
                    AccessibilityAction accessibilityAction2 = this.mActions.get(i6);
                    if (!isDefaultStandardAction(accessibilityAction2)) {
                        accessibilityAction2.writeToParcel(parcel, i);
                    }
                }
            } else {
                parcel.writeLong(0L);
                parcel.writeInt(0);
            }
        }
        if (BitUtils.isBitSet(jBitAt, 16)) {
            parcel.writeInt(this.mMaxTextLength);
        }
        if (BitUtils.isBitSet(jBitAt, 17)) {
            parcel.writeInt(this.mMovementGranularities);
        }
        if (BitUtils.isBitSet(jBitAt, 18)) {
            parcel.writeInt(this.mBooleanProperties);
        }
        if (BitUtils.isBitSet(jBitAt, 19)) {
            parcel.writeCharSequence(this.mPackageName);
        }
        if (BitUtils.isBitSet(jBitAt, 20)) {
            parcel.writeCharSequence(this.mClassName);
        }
        if (BitUtils.isBitSet(jBitAt, 21)) {
            parcel.writeCharSequence(this.mText);
        }
        if (BitUtils.isBitSet(jBitAt, 22)) {
            parcel.writeCharSequence(this.mHintText);
        }
        if (BitUtils.isBitSet(jBitAt, 23)) {
            parcel.writeCharSequence(this.mError);
        }
        if (BitUtils.isBitSet(jBitAt, 24)) {
            parcel.writeCharSequence(this.mStateDescription);
        }
        if (BitUtils.isBitSet(jBitAt, 25)) {
            parcel.writeCharSequence(this.mContentDescription);
        }
        if (BitUtils.isBitSet(jBitAt, 26)) {
            parcel.writeCharSequence(this.mSupplementalDescription);
        }
        if (BitUtils.isBitSet(jBitAt, 27)) {
            parcel.writeCharSequence(this.mPaneTitle);
        }
        if (BitUtils.isBitSet(jBitAt, 28)) {
            parcel.writeCharSequence(this.mTooltipText);
        }
        if (BitUtils.isBitSet(jBitAt, 29)) {
            parcel.writeCharSequence(this.mContainerTitle);
        }
        if (BitUtils.isBitSet(jBitAt, 30)) {
            parcel.writeString(this.mViewIdResourceName);
        }
        if (BitUtils.isBitSet(jBitAt, 31)) {
            parcel.writeString(this.mUniqueId);
        }
        if (BitUtils.isBitSet(jBitAt, 32)) {
            parcel.writeInt(this.mTextSelectionStart);
        }
        if (BitUtils.isBitSet(jBitAt, 33)) {
            parcel.writeInt(this.mTextSelectionEnd);
        }
        if (BitUtils.isBitSet(jBitAt, 34)) {
            parcel.writeInt(this.mInputType);
        }
        if (BitUtils.isBitSet(jBitAt, 35)) {
            parcel.writeInt(this.mLiveRegion);
        }
        if (BitUtils.isBitSet(jBitAt, 36)) {
            parcel.writeInt(this.mDrawingOrderInParent);
        }
        if (BitUtils.isBitSet(jBitAt, 37)) {
            parcel.writeStringList(this.mExtraDataKeys);
        }
        if (BitUtils.isBitSet(jBitAt, 38)) {
            parcel.writeBundle(this.mExtras);
        }
        if (BitUtils.isBitSet(jBitAt, 39)) {
            parcel.writeInt(this.mRangeInfo.getType());
            parcel.writeFloat(this.mRangeInfo.getMin());
            parcel.writeFloat(this.mRangeInfo.getMax());
            parcel.writeFloat(this.mRangeInfo.getCurrent());
        }
        if (BitUtils.isBitSet(jBitAt, 40)) {
            parcel.writeInt(this.mCollectionInfo.getRowCount());
            parcel.writeInt(this.mCollectionInfo.getColumnCount());
            parcel.writeInt(this.mCollectionInfo.isHierarchical() ? 1 : 0);
            parcel.writeInt(this.mCollectionInfo.getSelectionMode());
            parcel.writeInt(this.mCollectionInfo.getItemCount());
            parcel.writeInt(this.mCollectionInfo.getImportantForAccessibilityItemCount());
        }
        if (BitUtils.isBitSet(jBitAt, 41)) {
            parcel.writeString(this.mCollectionItemInfo.getRowTitle());
            parcel.writeInt(this.mCollectionItemInfo.getRowIndex());
            parcel.writeInt(this.mCollectionItemInfo.getRowSpan());
            parcel.writeString(this.mCollectionItemInfo.getColumnTitle());
            parcel.writeInt(this.mCollectionItemInfo.getColumnIndex());
            parcel.writeInt(this.mCollectionItemInfo.getColumnSpan());
            parcel.writeInt(this.mCollectionItemInfo.isHeading() ? 1 : 0);
            parcel.writeInt(this.mCollectionItemInfo.isSelected() ? 1 : 0);
        }
        if (BitUtils.isBitSet(jBitAt, 42)) {
            this.mTouchDelegateInfo.writeToParcel(parcel, i);
        }
        if (BitUtils.isBitSet(jBitAt, 43)) {
            parcel.writeValue(this.mExtraRenderingInfo.getLayoutSize());
            parcel.writeFloat(this.mExtraRenderingInfo.getTextSizeInPx());
            parcel.writeInt(this.mExtraRenderingInfo.getTextSizeUnit());
        }
        if (BitUtils.isBitSet(jBitAt, 44)) {
            parcel.writeStrongBinder(this.mLeashedChild);
        }
        if (BitUtils.isBitSet(jBitAt, 45)) {
            parcel.writeStrongBinder(this.mLeashedParent);
        }
        if (BitUtils.isBitSet(jBitAt, 46)) {
            parcel.writeLong(this.mLeashedParentNodeId);
        }
        if (BitUtils.isBitSet(jBitAt, 47)) {
            this.mSelection.writeToParcel(parcel, i);
        }
        if (BitUtils.isBitSet(jBitAt, 48)) {
            parcel.writeInt(this.mChecked);
        }
        if (BitUtils.isBitSet(jBitAt, 49)) {
            parcel.writeInt(this.mExpandedState);
        }
    }

    private void init(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mSealed = accessibilityNodeInfo.mSealed;
        this.mSourceNodeId = accessibilityNodeInfo.mSourceNodeId;
        this.mParentNodeId = accessibilityNodeInfo.mParentNodeId;
        this.mLabelForId = accessibilityNodeInfo.mLabelForId;
        this.mLabeledById = accessibilityNodeInfo.mLabeledById;
        this.mLabeledByIds = accessibilityNodeInfo.mLabeledByIds;
        this.mTraversalBefore = accessibilityNodeInfo.mTraversalBefore;
        this.mTraversalAfter = accessibilityNodeInfo.mTraversalAfter;
        this.mMinDurationBetweenContentChanges = accessibilityNodeInfo.mMinDurationBetweenContentChanges;
        this.mWindowId = accessibilityNodeInfo.mWindowId;
        this.mConnectionId = accessibilityNodeInfo.mConnectionId;
        this.mUniqueId = accessibilityNodeInfo.mUniqueId;
        this.mBoundsInParent.set(accessibilityNodeInfo.mBoundsInParent);
        this.mBoundsInScreen.set(accessibilityNodeInfo.mBoundsInScreen);
        this.mBoundsInWindow.set(accessibilityNodeInfo.mBoundsInWindow);
        this.mPackageName = accessibilityNodeInfo.mPackageName;
        this.mClassName = accessibilityNodeInfo.mClassName;
        this.mText = accessibilityNodeInfo.mText;
        this.mOriginalText = accessibilityNodeInfo.mOriginalText;
        this.mHintText = accessibilityNodeInfo.mHintText;
        this.mError = accessibilityNodeInfo.mError;
        this.mStateDescription = accessibilityNodeInfo.mStateDescription;
        this.mContentDescription = accessibilityNodeInfo.mContentDescription;
        this.mSupplementalDescription = accessibilityNodeInfo.mSupplementalDescription;
        this.mPaneTitle = accessibilityNodeInfo.mPaneTitle;
        this.mTooltipText = accessibilityNodeInfo.mTooltipText;
        this.mContainerTitle = accessibilityNodeInfo.mContainerTitle;
        this.mViewIdResourceName = accessibilityNodeInfo.mViewIdResourceName;
        ArrayList<AccessibilityAction> arrayList = this.mActions;
        if (arrayList != null) {
            arrayList.clear();
        }
        ArrayList<AccessibilityAction> arrayList2 = accessibilityNodeInfo.mActions;
        if (arrayList2 != null && arrayList2.size() > 0) {
            ArrayList<AccessibilityAction> arrayList3 = this.mActions;
            if (arrayList3 == null) {
                this.mActions = new ArrayList<>(arrayList2);
            } else {
                arrayList3.addAll(accessibilityNodeInfo.mActions);
            }
        }
        this.mBooleanProperties = accessibilityNodeInfo.mBooleanProperties;
        this.mMaxTextLength = accessibilityNodeInfo.mMaxTextLength;
        this.mMovementGranularities = accessibilityNodeInfo.mMovementGranularities;
        LongArray longArray = this.mChildNodeIds;
        if (longArray != null) {
            longArray.clear();
        }
        LongArray longArray2 = accessibilityNodeInfo.mChildNodeIds;
        if (longArray2 != null && longArray2.size() > 0) {
            LongArray longArray3 = this.mChildNodeIds;
            if (longArray3 == null) {
                this.mChildNodeIds = longArray2.m5513clone();
            } else {
                longArray3.addAll(longArray2);
            }
        }
        this.mTextSelectionStart = accessibilityNodeInfo.mTextSelectionStart;
        this.mTextSelectionEnd = accessibilityNodeInfo.mTextSelectionEnd;
        this.mInputType = accessibilityNodeInfo.mInputType;
        this.mLiveRegion = accessibilityNodeInfo.mLiveRegion;
        this.mDrawingOrderInParent = accessibilityNodeInfo.mDrawingOrderInParent;
        this.mExtraDataKeys = accessibilityNodeInfo.mExtraDataKeys;
        this.mExtras = accessibilityNodeInfo.mExtras != null ? new Bundle(accessibilityNodeInfo.mExtras) : null;
        initCopyInfos(accessibilityNodeInfo);
        TouchDelegateInfo touchDelegateInfo = accessibilityNodeInfo.mTouchDelegateInfo;
        this.mTouchDelegateInfo = touchDelegateInfo != null ? new TouchDelegateInfo(touchDelegateInfo.mTargetMap, true) : null;
        this.mLeashedChild = accessibilityNodeInfo.mLeashedChild;
        this.mLeashedParent = accessibilityNodeInfo.mLeashedParent;
        this.mLeashedParentNodeId = accessibilityNodeInfo.mLeashedParentNodeId;
        this.mChecked = accessibilityNodeInfo.mChecked;
        this.mExpandedState = accessibilityNodeInfo.mExpandedState;
    }

    private void initCopyInfos(AccessibilityNodeInfo accessibilityNodeInfo) {
        RangeInfo rangeInfo = accessibilityNodeInfo.mRangeInfo;
        this.mRangeInfo = rangeInfo == null ? null : new RangeInfo(rangeInfo.mType, rangeInfo.mMin, rangeInfo.mMax, rangeInfo.mCurrent);
        CollectionInfo collectionInfo = accessibilityNodeInfo.mCollectionInfo;
        this.mCollectionInfo = collectionInfo == null ? null : new CollectionInfo(collectionInfo.mRowCount, collectionInfo.mColumnCount, collectionInfo.mHierarchical, collectionInfo.mSelectionMode, collectionInfo.mItemCount, collectionInfo.mImportantForAccessibilityItemCount);
        CollectionItemInfo collectionItemInfo = accessibilityNodeInfo.mCollectionItemInfo;
        this.mCollectionItemInfo = collectionItemInfo == null ? null : new CollectionItemInfo.Builder().setRowTitle(collectionItemInfo.mRowTitle).setRowIndex(collectionItemInfo.mRowIndex).setRowSpan(collectionItemInfo.mRowSpan).setColumnTitle(collectionItemInfo.mColumnTitle).setColumnIndex(collectionItemInfo.mColumnIndex).setColumnSpan(collectionItemInfo.mColumnSpan).setHeading(collectionItemInfo.mHeading).setSelected(collectionItemInfo.mSelected).build();
        ExtraRenderingInfo extraRenderingInfo = accessibilityNodeInfo.mExtraRenderingInfo;
        this.mExtraRenderingInfo = extraRenderingInfo == null ? null : new ExtraRenderingInfo(extraRenderingInfo);
        if (!Flags.a11ySelectionApi() || accessibilityNodeInfo.getSelection() == null) {
            return;
        }
        SelectionPosition start = accessibilityNodeInfo.getSelection().getStart();
        SelectionPosition end = accessibilityNodeInfo.getSelection().getEnd();
        this.mSelection = new Selection(new SelectionPosition(start.mSourceNodeId, start.getOffset()), new SelectionPosition(end.mSourceNodeId, end.getOffset()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void initFromParcel(Parcel parcel) {
        boolean z;
        CollectionInfo collectionInfo;
        CollectionItemInfo collectionItemInfo;
        long j = parcel.readLong();
        if (BitUtils.isBitSet(j, 0)) {
            z = parcel.readInt() == 1;
        } else {
            z = DEFAULT.mSealed;
        }
        if (BitUtils.isBitSet(j, 1)) {
            this.mSourceNodeId = parcel.readLong();
        }
        if (BitUtils.isBitSet(j, 2)) {
            this.mWindowId = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 3)) {
            this.mParentNodeId = parcel.readLong();
        }
        if (BitUtils.isBitSet(j, 4)) {
            this.mLabelForId = parcel.readLong();
        }
        if (BitUtils.isBitSet(j, 5)) {
            this.mLabeledById = parcel.readLong();
        }
        ExtraRenderingInfo extraRenderingInfo = null;
        Object[] objArr = 0;
        if (BitUtils.isBitSet(j, 6)) {
            int i = parcel.readInt();
            if (i <= 0) {
                this.mLabeledByIds = null;
            } else {
                this.mLabeledByIds = new LongArray(i);
                for (int i2 = 0; i2 < i; i2++) {
                    this.mLabeledByIds.add(parcel.readLong());
                }
            }
        }
        if (BitUtils.isBitSet(j, 7)) {
            this.mTraversalBefore = parcel.readLong();
        }
        if (BitUtils.isBitSet(j, 8)) {
            this.mTraversalAfter = parcel.readLong();
        }
        if (BitUtils.isBitSet(j, 9)) {
            this.mMinDurationBetweenContentChanges = parcel.readLong();
        }
        if (BitUtils.isBitSet(j, 10)) {
            this.mConnectionId = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 11)) {
            int i3 = parcel.readInt();
            if (i3 <= 0) {
                this.mChildNodeIds = null;
            } else {
                this.mChildNodeIds = new LongArray(i3);
                for (int i4 = 0; i4 < i3; i4++) {
                    this.mChildNodeIds.add(parcel.readLong());
                }
            }
        }
        if (BitUtils.isBitSet(j, 12)) {
            this.mBoundsInParent.top = parcel.readInt();
            this.mBoundsInParent.bottom = parcel.readInt();
            this.mBoundsInParent.left = parcel.readInt();
            this.mBoundsInParent.right = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 13)) {
            this.mBoundsInScreen.top = parcel.readInt();
            this.mBoundsInScreen.bottom = parcel.readInt();
            this.mBoundsInScreen.left = parcel.readInt();
            this.mBoundsInScreen.right = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 14)) {
            this.mBoundsInWindow.top = parcel.readInt();
            this.mBoundsInWindow.bottom = parcel.readInt();
            this.mBoundsInWindow.left = parcel.readInt();
            this.mBoundsInWindow.right = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 15)) {
            addStandardActions(parcel.readLong());
            int i5 = parcel.readInt();
            for (int i6 = 0; i6 < i5; i6++) {
                addActionUnchecked(AccessibilityAction.CREATOR.createFromParcel(parcel));
            }
        }
        if (BitUtils.isBitSet(j, 16)) {
            this.mMaxTextLength = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 17)) {
            this.mMovementGranularities = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 18)) {
            this.mBooleanProperties = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 19)) {
            this.mPackageName = parcel.readCharSequence();
        }
        if (BitUtils.isBitSet(j, 20)) {
            this.mClassName = parcel.readCharSequence();
        }
        if (BitUtils.isBitSet(j, 21)) {
            this.mText = parcel.readCharSequence();
        }
        if (BitUtils.isBitSet(j, 22)) {
            this.mHintText = parcel.readCharSequence();
        }
        if (BitUtils.isBitSet(j, 23)) {
            this.mError = parcel.readCharSequence();
        }
        if (BitUtils.isBitSet(j, 24)) {
            this.mStateDescription = parcel.readCharSequence();
        }
        if (BitUtils.isBitSet(j, 25)) {
            this.mContentDescription = parcel.readCharSequence();
        }
        if (BitUtils.isBitSet(j, 26)) {
            this.mSupplementalDescription = parcel.readCharSequence();
        }
        if (BitUtils.isBitSet(j, 27)) {
            this.mPaneTitle = parcel.readCharSequence();
        }
        if (BitUtils.isBitSet(j, 28)) {
            this.mTooltipText = parcel.readCharSequence();
        }
        if (BitUtils.isBitSet(j, 29)) {
            this.mContainerTitle = parcel.readCharSequence();
        }
        if (BitUtils.isBitSet(j, 30)) {
            this.mViewIdResourceName = parcel.readString();
        }
        if (BitUtils.isBitSet(j, 31)) {
            this.mUniqueId = parcel.readString();
        }
        if (BitUtils.isBitSet(j, 32)) {
            this.mTextSelectionStart = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 33)) {
            this.mTextSelectionEnd = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 34)) {
            this.mInputType = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 35)) {
            this.mLiveRegion = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 36)) {
            this.mDrawingOrderInParent = parcel.readInt();
        }
        this.mExtraDataKeys = BitUtils.isBitSet(j, 37) ? parcel.createStringArrayList() : null;
        this.mExtras = BitUtils.isBitSet(j, 38) ? parcel.readBundle() : null;
        this.mRangeInfo = BitUtils.isBitSet(j, 39) ? new RangeInfo(parcel.readInt(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat()) : null;
        if (BitUtils.isBitSet(j, 40)) {
            collectionInfo = new CollectionInfo(parcel.readInt(), parcel.readInt(), parcel.readInt() == 1, parcel.readInt(), parcel.readInt(), parcel.readInt());
        } else {
            collectionInfo = null;
        }
        this.mCollectionInfo = collectionInfo;
        if (BitUtils.isBitSet(j, 41)) {
            collectionItemInfo = new CollectionItemInfo(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt() == 1, parcel.readInt() == 1);
        } else {
            collectionItemInfo = null;
        }
        this.mCollectionItemInfo = collectionItemInfo;
        if (BitUtils.isBitSet(j, 42)) {
            this.mTouchDelegateInfo = TouchDelegateInfo.CREATOR.createFromParcel(parcel);
        }
        if (BitUtils.isBitSet(j, 43)) {
            ExtraRenderingInfo extraRenderingInfo2 = new ExtraRenderingInfo(extraRenderingInfo);
            this.mExtraRenderingInfo = extraRenderingInfo2;
            extraRenderingInfo2.mLayoutSize = (Size) parcel.readValue(null);
            this.mExtraRenderingInfo.mTextSizeInPx = parcel.readFloat();
            this.mExtraRenderingInfo.mTextSizeUnit = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 44)) {
            this.mLeashedChild = parcel.readStrongBinder();
        }
        if (BitUtils.isBitSet(j, 45)) {
            this.mLeashedParent = parcel.readStrongBinder();
        }
        if (BitUtils.isBitSet(j, 46)) {
            this.mLeashedParentNodeId = parcel.readLong();
        }
        if (BitUtils.isBitSet(j, 47)) {
            this.mSelection = Selection.CREATOR.createFromParcel(parcel);
        }
        if (BitUtils.isBitSet(j, 48)) {
            this.mChecked = parcel.readInt();
        }
        if (BitUtils.isBitSet(j, 49)) {
            this.mExpandedState = parcel.readInt();
        }
        this.mSealed = z;
    }

    private void clear() {
        init(DEFAULT);
    }

    private static boolean isDefaultStandardAction(AccessibilityAction accessibilityAction) {
        return accessibilityAction.mSerializationFlag != -1 && TextUtils.isEmpty(accessibilityAction.getLabel());
    }

    private static AccessibilityAction getActionSingleton(int i) {
        int size = AccessibilityAction.sStandardActions.size();
        for (int i2 = 0; i2 < size; i2++) {
            AccessibilityAction accessibilityActionValueAt = AccessibilityAction.sStandardActions.valueAt(i2);
            if (i == accessibilityActionValueAt.getId()) {
                return accessibilityActionValueAt;
            }
        }
        return null;
    }

    private static AccessibilityAction getActionSingletonBySerializationFlag(long j) {
        int size = AccessibilityAction.sStandardActions.size();
        for (int i = 0; i < size; i++) {
            AccessibilityAction accessibilityActionValueAt = AccessibilityAction.sStandardActions.valueAt(i);
            if (j == accessibilityActionValueAt.mSerializationFlag) {
                return accessibilityActionValueAt;
            }
        }
        return null;
    }

    private void addStandardActions(long j) {
        while (j > 0) {
            long jNumberOfTrailingZeros = 1 << Long.numberOfTrailingZeros(j);
            j &= ~jNumberOfTrailingZeros;
            addAction(getActionSingletonBySerializationFlag(jNumberOfTrailingZeros));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getActionSymbolicName(int i) {
        if (i == 1) {
            return "ACTION_FOCUS";
        }
        if (i == 2) {
            return "ACTION_CLEAR_FOCUS";
        }
        switch (i) {
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            case 262144:
                return "ACTION_EXPAND";
            case 524288:
                return "ACTION_COLLAPSE";
            case 1048576:
                return "ACTION_DISMISS";
            case 2097152:
                return "ACTION_SET_TEXT";
            default:
                switch (i) {
                    case 16908342:
                        return "ACTION_SHOW_ON_SCREEN";
                    case 16908343:
                        return "ACTION_SCROLL_TO_POSITION";
                    case 16908344:
                        return "ACTION_SCROLL_UP";
                    case 16908345:
                        return "ACTION_SCROLL_LEFT";
                    case 16908346:
                        return "ACTION_SCROLL_DOWN";
                    case 16908347:
                        return "ACTION_SCROLL_RIGHT";
                    case 16908348:
                        return "ACTION_CONTEXT_CLICK";
                    case 16908349:
                        return "ACTION_SET_PROGRESS";
                    default:
                        switch (i) {
                            case 16908356:
                                return "ACTION_SHOW_TOOLTIP";
                            case 16908357:
                                return "ACTION_HIDE_TOOLTIP";
                            case 16908358:
                                return "ACTION_PAGE_UP";
                            case 16908359:
                                return "ACTION_PAGE_DOWN";
                            case 16908360:
                                return "ACTION_PAGE_LEFT";
                            case 16908361:
                                return "ACTION_PAGE_RIGHT";
                            case 16908362:
                                return "ACTION_PRESS_AND_HOLD";
                            default:
                                switch (i) {
                                    case 16908372:
                                        return "ACTION_IME_ENTER";
                                    case 16908373:
                                        return "ACTION_DRAG";
                                    case 16908374:
                                        return "ACTION_DROP";
                                    case 16908375:
                                        return "ACTION_CANCEL_DRAG";
                                    default:
                                        if (i == 16908376) {
                                            return "ACTION_SHOW_TEXT_SUGGESTIONS";
                                        }
                                        if (i == 16908382) {
                                            return "ACTION_SCROLL_IN_DIRECTION";
                                        }
                                        if (i == R.id.accessibilityActionSetExtendedSelection) {
                                            return "ACTION_SET_EXTENDED_SELECTION";
                                        }
                                        return "ACTION_UNKNOWN";
                                }
                        }
                }
        }
    }

    private static String getMovementGranularitySymbolicName(int i) {
        if (i == 1) {
            return "MOVEMENT_GRANULARITY_CHARACTER";
        }
        if (i == 2) {
            return "MOVEMENT_GRANULARITY_WORD";
        }
        if (i == 4) {
            return "MOVEMENT_GRANULARITY_LINE";
        }
        if (i == 8) {
            return "MOVEMENT_GRANULARITY_PARAGRAPH";
        }
        if (i == 16) {
            return "MOVEMENT_GRANULARITY_PAGE";
        }
        throw new IllegalArgumentException("Unknown movement granularity: " + i);
    }

    private static boolean canPerformRequestOverConnection(int i, int i2, long j) {
        return ((!usingDirectConnection(i) && !(i2 != -1)) || getAccessibilityViewId(j) == Integer.MAX_VALUE || i == -1) ? false : true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) obj;
        return this.mSourceNodeId == accessibilityNodeInfo.mSourceNodeId && this.mWindowId == accessibilityNodeInfo.mWindowId;
    }

    public int hashCode() {
        return ((((getAccessibilityViewId(this.mSourceNodeId) + 31) * 31) + getVirtualDescendantId(this.mSourceNodeId)) * 31) + this.mWindowId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        if (DEBUG) {
            sb.append("; sourceNodeId: 0x");
            sb.append(Long.toHexString(this.mSourceNodeId));
            sb.append("; windowId: 0x");
            sb.append(Long.toHexString(this.mWindowId));
            sb.append("; accessibilityViewId: 0x");
            sb.append(Long.toHexString(getAccessibilityViewId(this.mSourceNodeId)));
            sb.append("; virtualDescendantId: 0x");
            sb.append(Long.toHexString(getVirtualDescendantId(this.mSourceNodeId)));
            sb.append("; mParentNodeId: 0x");
            sb.append(Long.toHexString(this.mParentNodeId));
            sb.append("; traversalBefore: 0x");
            sb.append(Long.toHexString(this.mTraversalBefore));
            sb.append("; traversalAfter: 0x");
            sb.append(Long.toHexString(this.mTraversalAfter));
            sb.append("; minDurationBetweenContentChanges: ");
            sb.append(this.mMinDurationBetweenContentChanges);
            int i = this.mMovementGranularities;
            sb.append("; MovementGranularities: [");
            while (i != 0) {
                int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
                i &= ~iNumberOfTrailingZeros;
                sb.append(getMovementGranularitySymbolicName(iNumberOfTrailingZeros));
                if (i != 0) {
                    sb.append(", ");
                }
            }
            sb.append("]; childAccessibilityIds: [");
            LongArray longArray = this.mChildNodeIds;
            if (longArray != null) {
                int size = longArray.size();
                for (int i2 = 0; i2 < size; i2++) {
                    sb.append("0x");
                    sb.append(Long.toHexString(longArray.get(i2)));
                    if (i2 < size - 1) {
                        sb.append(", ");
                    }
                }
            }
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        }
        sb.append("; boundsInParent: ");
        sb.append(this.mBoundsInParent);
        sb.append("; boundsInScreen: ");
        sb.append(this.mBoundsInScreen);
        sb.append("; boundsInWindow: ");
        sb.append(this.mBoundsInScreen);
        sb.append("; packageName: ");
        sb.append(this.mPackageName);
        sb.append("; className: ");
        sb.append(this.mClassName);
        sb.append("; text: ");
        sb.append(this.mText);
        sb.append("; error: ");
        sb.append(this.mError);
        sb.append("; maxTextLength: ");
        sb.append(this.mMaxTextLength);
        sb.append("; stateDescription: ");
        sb.append(this.mStateDescription);
        sb.append("; contentDescription: ");
        sb.append(this.mContentDescription);
        sb.append("; tooltipText: ");
        sb.append(this.mTooltipText);
        sb.append("; containerTitle: ");
        sb.append(this.mContainerTitle);
        sb.append("; viewIdResName: ");
        sb.append(this.mViewIdResourceName);
        sb.append("; uniqueId: ");
        sb.append(this.mUniqueId);
        sb.append("; checkable: ");
        sb.append(isCheckable());
        sb.append("; checked: ");
        sb.append(isChecked());
        sb.append("; focusable: ");
        sb.append(isFocusable());
        sb.append("; focused: ");
        sb.append(isFocused());
        sb.append("; selected: ");
        sb.append(isSelected());
        sb.append("; clickable: ");
        sb.append(isClickable());
        sb.append("; longClickable: ");
        sb.append(isLongClickable());
        sb.append("; contextClickable: ");
        sb.append(isContextClickable());
        sb.append("; enabled: ");
        sb.append(isEnabled());
        sb.append("; password: ");
        sb.append(isPassword());
        sb.append("; scrollable: ");
        sb.append(isScrollable());
        sb.append("; granularScrollingSupported: ");
        sb.append(isGranularScrollingSupported());
        sb.append("; importantForAccessibility: ");
        sb.append(isImportantForAccessibility());
        sb.append("; visible: ");
        sb.append(isVisibleToUser());
        sb.append("; actions: ");
        sb.append(this.mActions);
        sb.append("; isTextSelectable: ");
        sb.append(isTextSelectable());
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AccessibilityNodeInfo getNodeForAccessibilityId(int i, int i2, long j) {
        return getNodeForAccessibilityId(i, i2, j, 7);
    }

    private static AccessibilityNodeInfo getNodeForAccessibilityId(int i, int i2, long j, int i3) {
        if (canPerformRequestOverConnection(i, i2, j)) {
            return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(i, i2, j, false, i3, (Bundle) null);
        }
        return null;
    }

    private static AccessibilityNodeInfo getNodeForAccessibilityId(int i, IBinder iBinder, long j) {
        return getNodeForAccessibilityId(i, iBinder, j, 7);
    }

    private static AccessibilityNodeInfo getNodeForAccessibilityId(int i, IBinder iBinder, long j, int i2) {
        if (iBinder == null || getAccessibilityViewId(j) == Integer.MAX_VALUE || i == -1) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(i, iBinder, j, false, i2, (Bundle) null);
    }

    public static String idToString(long j) {
        int accessibilityViewId = getAccessibilityViewId(j);
        int virtualDescendantId = getVirtualDescendantId(j);
        if (virtualDescendantId == -1) {
            return idItemToString(accessibilityViewId);
        }
        return idItemToString(accessibilityViewId) + ":" + idItemToString(virtualDescendantId);
    }

    private static String idItemToString(int i) {
        if (i != -1) {
            switch (i) {
                case 2147483646:
                    return "ROOT";
                case Integer.MAX_VALUE:
                    return DevicePolicyResources.UNDEFINED;
                default:
                    return "" + i;
            }
        }
        return "HOST";
    }

    public static final class SelectionPosition implements Parcelable {
        public static final Parcelable.Creator<SelectionPosition> CREATOR = new Parcelable.Creator<SelectionPosition>() { // from class: android.view.accessibility.AccessibilityNodeInfo.SelectionPosition.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SelectionPosition createFromParcel(Parcel parcel) {
                return new SelectionPosition(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SelectionPosition[] newArray(int i) {
                return new SelectionPosition[i];
            }
        };
        private int mConnectionId;
        private final int mOffset;
        private final long mSourceNodeId;
        private int mWindowId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SelectionPosition(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
            this(accessibilityNodeInfo.mSourceNodeId, i);
        }

        public SelectionPosition(View view, int i) {
            this(AccessibilityNodeInfo.makeNodeId(view.getAccessibilityViewId(), -1), i);
        }

        public SelectionPosition(View view, int i, int i2) {
            this(AccessibilityNodeInfo.makeNodeId(view.getAccessibilityViewId(), i), i2);
        }

        private SelectionPosition(long j, int i) {
            this.mOffset = i;
            this.mSourceNodeId = j;
        }

        private SelectionPosition(Parcel parcel) {
            this.mOffset = parcel.readInt();
            this.mSourceNodeId = parcel.readLong();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setWindowId(int i) {
            this.mWindowId = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setConnectionId(int i) {
            this.mConnectionId = i;
        }

        public AccessibilityNodeInfo getNode() {
            return AccessibilityNodeInfo.getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mSourceNodeId);
        }

        public int getOffset() {
            return this.mOffset;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean usesNode(AccessibilityNodeInfo accessibilityNodeInfo) {
            return this.mSourceNodeId == accessibilityNodeInfo.mSourceNodeId && this.mConnectionId == accessibilityNodeInfo.mConnectionId && this.mWindowId == accessibilityNodeInfo.mWindowId;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            SelectionPosition selectionPosition = (SelectionPosition) obj;
            return getOffset() == selectionPosition.getOffset() && this.mSourceNodeId == selectionPosition.mSourceNodeId;
        }

        public int hashCode() {
            int i = this.mOffset;
            long j = i != 0 ? i : 1L;
            if (this.mSourceNodeId != AccessibilityNodeInfo.UNDEFINED_NODE_ID) {
                j *= this.mSourceNodeId;
            }
            return Long.hashCode(j * 877);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mOffset);
            parcel.writeLong(this.mSourceNodeId);
        }
    }

    public static final class Selection implements Parcelable {
        public static final Parcelable.Creator<Selection> CREATOR = new Parcelable.Creator<Selection>() { // from class: android.view.accessibility.AccessibilityNodeInfo.Selection.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Selection createFromParcel(Parcel parcel) {
                return new Selection(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Selection[] newArray(int i) {
                return new Selection[i];
            }
        };
        private final SelectionPosition mEnd;
        private final SelectionPosition mStart;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Selection(SelectionPosition selectionPosition, SelectionPosition selectionPosition2) {
            this.mStart = selectionPosition;
            this.mEnd = selectionPosition2;
        }

        private Selection(Parcel parcel) {
            this.mStart = SelectionPosition.CREATOR.createFromParcel(parcel);
            this.mEnd = SelectionPosition.CREATOR.createFromParcel(parcel);
        }

        public SelectionPosition getStart() {
            return this.mStart;
        }

        public SelectionPosition getEnd() {
            return this.mEnd;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Selection selection = (Selection) obj;
            return getStart().equals(selection.getStart()) && getEnd().equals(selection.getEnd());
        }

        public int hashCode() {
            return getStart().hashCode() * 17 * getEnd().hashCode();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.mStart.writeToParcel(parcel, i);
            this.mEnd.writeToParcel(parcel, i);
        }
    }

    public static final class AccessibilityAction implements Parcelable {
        private final int mActionId;
        private final CharSequence mLabel;
        public long mSerializationFlag;
        public static final ArraySet<AccessibilityAction> sStandardActions = new ArraySet<>();
        public static final AccessibilityAction ACTION_FOCUS = new AccessibilityAction(1);
        public static final AccessibilityAction ACTION_CLEAR_FOCUS = new AccessibilityAction(2);
        public static final AccessibilityAction ACTION_SELECT = new AccessibilityAction(4);
        public static final AccessibilityAction ACTION_CLEAR_SELECTION = new AccessibilityAction(8);
        public static final AccessibilityAction ACTION_CLICK = new AccessibilityAction(16);
        public static final AccessibilityAction ACTION_LONG_CLICK = new AccessibilityAction(32);
        public static final AccessibilityAction ACTION_ACCESSIBILITY_FOCUS = new AccessibilityAction(64);
        public static final AccessibilityAction ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityAction(128);
        public static final AccessibilityAction ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityAction(256);
        public static final AccessibilityAction ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityAction(512);
        public static final AccessibilityAction ACTION_NEXT_HTML_ELEMENT = new AccessibilityAction(1024);
        public static final AccessibilityAction ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityAction(2048);
        public static final AccessibilityAction ACTION_SCROLL_FORWARD = new AccessibilityAction(4096);
        public static final AccessibilityAction ACTION_SCROLL_BACKWARD = new AccessibilityAction(8192);
        public static final AccessibilityAction ACTION_COPY = new AccessibilityAction(16384);
        public static final AccessibilityAction ACTION_PASTE = new AccessibilityAction(32768);
        public static final AccessibilityAction ACTION_CUT = new AccessibilityAction(65536);
        public static final AccessibilityAction ACTION_SET_SELECTION = new AccessibilityAction(131072);
        public static final AccessibilityAction ACTION_EXPAND = new AccessibilityAction(262144);
        public static final AccessibilityAction ACTION_COLLAPSE = new AccessibilityAction(524288);
        public static final AccessibilityAction ACTION_DISMISS = new AccessibilityAction(1048576);
        public static final AccessibilityAction ACTION_SET_TEXT = new AccessibilityAction(2097152);
        public static final AccessibilityAction ACTION_SHOW_ON_SCREEN = new AccessibilityAction(16908342);
        public static final AccessibilityAction ACTION_SCROLL_TO_POSITION = new AccessibilityAction(16908343);
        public static final AccessibilityAction ACTION_SCROLL_IN_DIRECTION = new AccessibilityAction(16908382);
        public static final AccessibilityAction ACTION_SCROLL_UP = new AccessibilityAction(16908344);
        public static final AccessibilityAction ACTION_SCROLL_LEFT = new AccessibilityAction(16908345);
        public static final AccessibilityAction ACTION_SCROLL_DOWN = new AccessibilityAction(16908346);
        public static final AccessibilityAction ACTION_SCROLL_RIGHT = new AccessibilityAction(16908347);
        public static final AccessibilityAction SEM_ACTION_AUTOSCROLL_ON = new AccessibilityAction(4194304);
        public static final AccessibilityAction SEM_ACTION_AUTOSCROLL_OFF = new AccessibilityAction(8388608);
        public static final AccessibilityAction ACTION_PAGE_UP = new AccessibilityAction(16908358);
        public static final AccessibilityAction ACTION_PAGE_DOWN = new AccessibilityAction(16908359);
        public static final AccessibilityAction ACTION_PAGE_LEFT = new AccessibilityAction(16908360);
        public static final AccessibilityAction ACTION_PAGE_RIGHT = new AccessibilityAction(16908361);
        public static final AccessibilityAction ACTION_CONTEXT_CLICK = new AccessibilityAction(16908348);
        public static final AccessibilityAction ACTION_SET_PROGRESS = new AccessibilityAction(16908349);
        public static final AccessibilityAction ACTION_MOVE_WINDOW = new AccessibilityAction(16908354);
        public static final AccessibilityAction ACTION_SHOW_TOOLTIP = new AccessibilityAction(16908356);
        public static final AccessibilityAction ACTION_HIDE_TOOLTIP = new AccessibilityAction(16908357);
        public static final AccessibilityAction ACTION_PRESS_AND_HOLD = new AccessibilityAction(16908362);
        public static final AccessibilityAction ACTION_IME_ENTER = new AccessibilityAction(16908372);
        public static final AccessibilityAction ACTION_DRAG_START = new AccessibilityAction(16908373);
        public static final AccessibilityAction ACTION_DRAG_DROP = new AccessibilityAction(16908374);
        public static final AccessibilityAction ACTION_DRAG_CANCEL = new AccessibilityAction(16908375);
        public static final AccessibilityAction ACTION_SHOW_TEXT_SUGGESTIONS = new AccessibilityAction(16908376);
        public static final AccessibilityAction ACTION_SET_EXTENDED_SELECTION = new AccessibilityAction(R.id.accessibilityActionSetExtendedSelection);
        public static final Parcelable.Creator<AccessibilityAction> CREATOR = new Parcelable.Creator<AccessibilityAction>() { // from class: android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AccessibilityAction createFromParcel(Parcel parcel) {
                return new AccessibilityAction(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AccessibilityAction[] newArray(int i) {
                return new AccessibilityAction[i];
            }
        };

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AccessibilityAction(int i, CharSequence charSequence) {
            this.mSerializationFlag = -1L;
            this.mActionId = i;
            this.mLabel = charSequence;
        }

        private AccessibilityAction(int i) {
            this(i, (CharSequence) null);
            ArraySet<AccessibilityAction> arraySet = sStandardActions;
            this.mSerializationFlag = BitUtils.bitAt(arraySet.size());
            arraySet.add(this);
        }

        public int getId() {
            return this.mActionId;
        }

        public CharSequence getLabel() {
            return this.mLabel;
        }

        public int hashCode() {
            return this.mActionId;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            return getClass() == obj.getClass() && this.mActionId == ((AccessibilityAction) obj).mActionId;
        }

        public String toString() {
            return "AccessibilityAction: " + AccessibilityNodeInfo.getActionSymbolicName(this.mActionId) + " - " + ((Object) this.mLabel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mActionId);
            parcel.writeCharSequence(this.mLabel);
        }

        private AccessibilityAction(Parcel parcel) {
            this.mSerializationFlag = -1L;
            this.mActionId = parcel.readInt();
            this.mLabel = parcel.readCharSequence();
        }
    }

    public static final class RangeInfo {
        public static final RangeInfo INDETERMINATE = new RangeInfo(3, 0.0f, 0.0f, 0.0f);
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INDETERMINATE = 3;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        private float mCurrent;
        private float mMax;
        private float mMin;
        private int mType;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RangeType {
        }

        @Deprecated
        void recycle() {
        }

        @Deprecated
        public static RangeInfo obtain(int i, float f, float f2, float f3) {
            return new RangeInfo(i, f, f2, f3);
        }

        public RangeInfo(int i, float f, float f2, float f3) {
            this.mType = i;
            this.mMin = f;
            this.mMax = f2;
            this.mCurrent = f3;
        }

        public int getType() {
            return this.mType;
        }

        public float getMin() {
            return this.mMin;
        }

        public float getMax() {
            return this.mMax;
        }

        public float getCurrent() {
            return this.mCurrent;
        }

        private void clear() {
            this.mType = 0;
            this.mMin = 0.0f;
            this.mMax = 0.0f;
            this.mCurrent = 0.0f;
        }
    }

    public static final class CollectionInfo {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        public static final int UNDEFINED = -1;
        private int mColumnCount;
        private boolean mHierarchical;
        private int mImportantForAccessibilityItemCount;
        private int mItemCount;
        private int mRowCount;
        private int mSelectionMode;

        @Deprecated
        void recycle() {
        }

        public static CollectionInfo obtain(CollectionInfo collectionInfo) {
            return new CollectionInfo(collectionInfo.mRowCount, collectionInfo.mColumnCount, collectionInfo.mHierarchical, collectionInfo.mSelectionMode, collectionInfo.mItemCount, collectionInfo.mImportantForAccessibilityItemCount);
        }

        public static CollectionInfo obtain(int i, int i2, boolean z) {
            return new CollectionInfo(i, i2, z, 0);
        }

        public static CollectionInfo obtain(int i, int i2, boolean z, int i3) {
            return new CollectionInfo(i, i2, z, i3);
        }

        public CollectionInfo(int i, int i2, boolean z) {
            this(i, i2, z, 0);
        }

        public CollectionInfo(int i, int i2, boolean z, int i3) {
            this.mRowCount = i;
            this.mColumnCount = i2;
            this.mHierarchical = z;
            this.mSelectionMode = i3;
            this.mItemCount = -1;
            this.mImportantForAccessibilityItemCount = -1;
        }

        public CollectionInfo(int i, int i2, boolean z, int i3, int i4, int i5) {
            this.mRowCount = i;
            this.mColumnCount = i2;
            this.mHierarchical = z;
            this.mSelectionMode = i3;
            this.mItemCount = i4;
            this.mImportantForAccessibilityItemCount = i5;
        }

        public int getRowCount() {
            return this.mRowCount;
        }

        public int getColumnCount() {
            return this.mColumnCount;
        }

        public boolean isHierarchical() {
            return this.mHierarchical;
        }

        public int getSelectionMode() {
            return this.mSelectionMode;
        }

        public int getItemCount() {
            return this.mItemCount;
        }

        public int getImportantForAccessibilityItemCount() {
            return this.mImportantForAccessibilityItemCount;
        }

        private void clear() {
            this.mRowCount = 0;
            this.mColumnCount = 0;
            this.mHierarchical = false;
            this.mSelectionMode = 0;
            this.mItemCount = -1;
            this.mImportantForAccessibilityItemCount = -1;
        }

        public static final class Builder {
            private int mSelectionMode;
            private int mRowCount = 0;
            private int mColumnCount = 0;
            private boolean mHierarchical = false;
            private int mItemCount = -1;
            private int mImportantForAccessibilityItemCount = -1;

            public Builder setRowCount(int i) {
                this.mRowCount = i;
                return this;
            }

            public Builder setColumnCount(int i) {
                this.mColumnCount = i;
                return this;
            }

            public Builder setHierarchical(boolean z) {
                this.mHierarchical = z;
                return this;
            }

            public Builder setSelectionMode(int i) {
                this.mSelectionMode = i;
                return this;
            }

            public Builder setItemCount(int i) {
                this.mItemCount = i;
                return this;
            }

            public Builder setImportantForAccessibilityItemCount(int i) {
                this.mImportantForAccessibilityItemCount = i;
                return this;
            }

            public CollectionInfo build() {
                CollectionInfo collectionInfo = new CollectionInfo(this.mRowCount, this.mColumnCount, this.mHierarchical);
                collectionInfo.mSelectionMode = this.mSelectionMode;
                collectionInfo.mItemCount = this.mItemCount;
                collectionInfo.mImportantForAccessibilityItemCount = this.mImportantForAccessibilityItemCount;
                return collectionInfo;
            }
        }
    }

    public static final class CollectionItemInfo {
        private int mColumnIndex;
        private int mColumnSpan;
        private String mColumnTitle;
        private boolean mHeading;
        private int mRowIndex;
        private int mRowSpan;
        private String mRowTitle;
        private boolean mSelected;

        @Deprecated
        void recycle() {
        }

        @Deprecated
        public static CollectionItemInfo obtain(CollectionItemInfo collectionItemInfo) {
            return new CollectionItemInfo(collectionItemInfo.mRowTitle, collectionItemInfo.mRowIndex, collectionItemInfo.mRowSpan, collectionItemInfo.mColumnTitle, collectionItemInfo.mColumnIndex, collectionItemInfo.mColumnSpan, collectionItemInfo.mHeading, collectionItemInfo.mSelected);
        }

        @Deprecated
        public static CollectionItemInfo obtain(int i, int i2, int i3, int i4, boolean z) {
            return new CollectionItemInfo(i, i2, i3, i4, z, false);
        }

        @Deprecated
        public static CollectionItemInfo obtain(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            return new CollectionItemInfo(i, i2, i3, i4, z, z2);
        }

        @Deprecated
        public static CollectionItemInfo obtain(String str, int i, int i2, String str2, int i3, int i4, boolean z, boolean z2) {
            return new CollectionItemInfo(str, i, i2, str2, i3, i4, z, z2);
        }

        private CollectionItemInfo() {
        }

        public CollectionItemInfo(int i, int i2, int i3, int i4, boolean z) {
            this(i, i2, i3, i4, z, false);
        }

        public CollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            this(null, i, i2, null, i3, i4, z, z2);
        }

        public CollectionItemInfo(String str, int i, int i2, String str2, int i3, int i4, boolean z, boolean z2) {
            this.mRowIndex = i;
            this.mRowSpan = i2;
            this.mColumnIndex = i3;
            this.mColumnSpan = i4;
            this.mHeading = z;
            this.mSelected = z2;
            this.mRowTitle = str;
            this.mColumnTitle = str2;
        }

        public int getColumnIndex() {
            return this.mColumnIndex;
        }

        public int getRowIndex() {
            return this.mRowIndex;
        }

        public int getColumnSpan() {
            return this.mColumnSpan;
        }

        public int getRowSpan() {
            return this.mRowSpan;
        }

        public boolean isHeading() {
            return this.mHeading;
        }

        public boolean isSelected() {
            return this.mSelected;
        }

        public String getRowTitle() {
            return this.mRowTitle;
        }

        public String getColumnTitle() {
            return this.mColumnTitle;
        }

        private void clear() {
            this.mColumnIndex = 0;
            this.mColumnSpan = 0;
            this.mRowIndex = 0;
            this.mRowSpan = 0;
            this.mHeading = false;
            this.mSelected = false;
            this.mRowTitle = null;
            this.mColumnTitle = null;
        }

        public static final class Builder {
            private int mColumnIndex;
            private int mColumnSpan;
            private String mColumnTitle;
            private boolean mHeading;
            private int mRowIndex;
            private int mRowSpan;
            private String mRowTitle;
            private boolean mSelected;

            public Builder setHeading(boolean z) {
                this.mHeading = z;
                return this;
            }

            public Builder setColumnIndex(int i) {
                this.mColumnIndex = i;
                return this;
            }

            public Builder setRowIndex(int i) {
                this.mRowIndex = i;
                return this;
            }

            public Builder setColumnSpan(int i) {
                this.mColumnSpan = i;
                return this;
            }

            public Builder setRowSpan(int i) {
                this.mRowSpan = i;
                return this;
            }

            public Builder setSelected(boolean z) {
                this.mSelected = z;
                return this;
            }

            public Builder setRowTitle(String str) {
                this.mRowTitle = str;
                return this;
            }

            public Builder setColumnTitle(String str) {
                this.mColumnTitle = str;
                return this;
            }

            public CollectionItemInfo build() {
                CollectionItemInfo collectionItemInfo = new CollectionItemInfo();
                collectionItemInfo.mHeading = this.mHeading;
                collectionItemInfo.mColumnIndex = this.mColumnIndex;
                collectionItemInfo.mRowIndex = this.mRowIndex;
                collectionItemInfo.mColumnSpan = this.mColumnSpan;
                collectionItemInfo.mRowSpan = this.mRowSpan;
                collectionItemInfo.mSelected = this.mSelected;
                collectionItemInfo.mRowTitle = this.mRowTitle;
                collectionItemInfo.mColumnTitle = this.mColumnTitle;
                return collectionItemInfo;
            }
        }
    }

    public static final class TouchDelegateInfo implements Parcelable {
        public static final Parcelable.Creator<TouchDelegateInfo> CREATOR = new Parcelable.Creator<TouchDelegateInfo>() { // from class: android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TouchDelegateInfo createFromParcel(Parcel parcel) {
                int i = parcel.readInt();
                if (i == 0) {
                    return null;
                }
                ArrayMap arrayMap = new ArrayMap(i);
                for (int i2 = 0; i2 < i; i2++) {
                    arrayMap.put(Region.CREATOR.createFromParcel(parcel), Long.valueOf(parcel.readLong()));
                }
                return new TouchDelegateInfo(arrayMap, false);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TouchDelegateInfo[] newArray(int i) {
                return new TouchDelegateInfo[i];
            }
        };
        private int mConnectionId;
        private ArrayMap<Region, Long> mTargetMap;
        private int mWindowId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public TouchDelegateInfo(Map<Region, View> map) {
            Preconditions.checkArgument((map.isEmpty() || map.containsKey(null) || map.containsValue(null)) ? false : true);
            this.mTargetMap = new ArrayMap<>(map.size());
            Iterator<Region> it = map.keySet().iterator();
            while (it.hasNext()) {
                this.mTargetMap.put(it.next(), Long.valueOf(map.get(r1).getAccessibilityViewId()));
            }
        }

        TouchDelegateInfo(ArrayMap<Region, Long> arrayMap, boolean z) {
            Preconditions.checkArgument((arrayMap.isEmpty() || arrayMap.containsKey(null) || arrayMap.containsValue(null)) ? false : true);
            if (z) {
                ArrayMap<Region, Long> arrayMap2 = new ArrayMap<>(arrayMap.size());
                this.mTargetMap = arrayMap2;
                arrayMap2.putAll((ArrayMap<? extends Region, ? extends Long>) arrayMap);
                return;
            }
            this.mTargetMap = arrayMap;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setConnectionId(int i) {
            this.mConnectionId = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setWindowId(int i) {
            this.mWindowId = i;
        }

        public int getRegionCount() {
            return this.mTargetMap.size();
        }

        public Region getRegionAt(int i) {
            return this.mTargetMap.keyAt(i);
        }

        public AccessibilityNodeInfo getTargetForRegion(Region region) {
            return AccessibilityNodeInfo.getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTargetMap.get(region).longValue());
        }

        public long getAccessibilityIdForRegion(Region region) {
            return this.mTargetMap.get(region).longValue();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mTargetMap.size());
            for (int i2 = 0; i2 < this.mTargetMap.size(); i2++) {
                Region regionKeyAt = this.mTargetMap.keyAt(i2);
                Long lValueAt = this.mTargetMap.valueAt(i2);
                regionKeyAt.writeToParcel(parcel, i);
                parcel.writeLong(lValueAt.longValue());
            }
        }
    }

    public static final class ExtraRenderingInfo {
        private static final int UNDEFINED_VALUE = -1;
        private Size mLayoutSize;
        private float mTextSizeInPx;
        private int mTextSizeUnit;

        @Deprecated
        void recycle() {
        }

        @Deprecated
        public static ExtraRenderingInfo obtain() {
            return new ExtraRenderingInfo(null);
        }

        @Deprecated
        private static ExtraRenderingInfo obtain(ExtraRenderingInfo extraRenderingInfo) {
            return new ExtraRenderingInfo(extraRenderingInfo);
        }

        private ExtraRenderingInfo(ExtraRenderingInfo extraRenderingInfo) {
            this.mTextSizeInPx = -1.0f;
            this.mTextSizeUnit = -1;
            if (extraRenderingInfo != null) {
                this.mLayoutSize = extraRenderingInfo.mLayoutSize;
                this.mTextSizeInPx = extraRenderingInfo.mTextSizeInPx;
                this.mTextSizeUnit = extraRenderingInfo.mTextSizeUnit;
            }
        }

        public Size getLayoutSize() {
            return this.mLayoutSize;
        }

        public void setLayoutSize(int i, int i2) {
            this.mLayoutSize = new Size(i, i2);
        }

        public float getTextSizeInPx() {
            return this.mTextSizeInPx;
        }

        public void setTextSizeInPx(float f) {
            this.mTextSizeInPx = f;
        }

        public int getTextSizeUnit() {
            return this.mTextSizeUnit;
        }

        public void setTextSizeUnit(int i) {
            this.mTextSizeUnit = i;
        }

        private void clear() {
            this.mLayoutSize = null;
            this.mTextSizeInPx = -1.0f;
            this.mTextSizeUnit = -1;
        }
    }
}
