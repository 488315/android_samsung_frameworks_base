package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.ExpandableListConnector;
import com.android.internal.R;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ExpandableListView extends ListView {
    public static final int CHILD_INDICATOR_INHERIT = -1;
    private static final int[] CHILD_LAST_STATE_SET = {16842918};
    private static final int[] EMPTY_STATE_SET;
    private static final int[] GROUP_EMPTY_STATE_SET;
    private static final int[] GROUP_EXPANDED_EMPTY_STATE_SET;
    private static final int[] GROUP_EXPANDED_STATE_SET;
    private static final int[][] GROUP_STATE_SETS;
    private static final int INDICATOR_UNDEFINED = -2;
    private static final long PACKED_POSITION_INT_MASK_CHILD = -1;
    private static final long PACKED_POSITION_INT_MASK_GROUP = 2147483647L;
    private static final long PACKED_POSITION_MASK_CHILD = 4294967295L;
    private static final long PACKED_POSITION_MASK_GROUP = 9223372032559808512L;
    private static final long PACKED_POSITION_MASK_TYPE = Long.MIN_VALUE;
    private static final long PACKED_POSITION_SHIFT_GROUP = 32;
    private static final long PACKED_POSITION_SHIFT_TYPE = 63;
    public static final int PACKED_POSITION_TYPE_CHILD = 1;
    public static final int PACKED_POSITION_TYPE_GROUP = 0;
    public static final int PACKED_POSITION_TYPE_NULL = 2;
    public static final long PACKED_POSITION_VALUE_NULL = 4294967295L;
    private ExpandableListAdapter mAdapter;
    private Drawable mChildDivider;
    private Drawable mChildIndicator;
    private int mChildIndicatorEnd;
    private int mChildIndicatorLeft;
    private int mChildIndicatorRight;
    private int mChildIndicatorStart;
    private ExpandableListConnector mConnector;
    private Drawable mGroupIndicator;
    private int mIndicatorEnd;
    private int mIndicatorLeft;
    private final Rect mIndicatorRect;
    private int mIndicatorRight;
    private int mIndicatorStart;
    private OnChildClickListener mOnChildClickListener;
    private OnGroupClickListener mOnGroupClickListener;
    private OnGroupCollapseListener mOnGroupCollapseListener;
    private OnGroupExpandListener mOnGroupExpandListener;

    public interface OnChildClickListener {
        boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j);
    }

    public interface OnGroupClickListener {
        boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j);
    }

    public interface OnGroupCollapseListener {
        void onGroupCollapse(int i);
    }

    public interface OnGroupExpandListener {
        void onGroupExpand(int i);
    }

    public static int getPackedPositionChild(long j) {
        if (j != 4294967295L && (j & Long.MIN_VALUE) == Long.MIN_VALUE) {
            return (int) (j & 4294967295L);
        }
        return -1;
    }

    public static long getPackedPositionForChild(int i, int i2) {
        return i2 | ((i & PACKED_POSITION_INT_MASK_GROUP) << 32) | Long.MIN_VALUE;
    }

    public static long getPackedPositionForGroup(int i) {
        return (i & PACKED_POSITION_INT_MASK_GROUP) << 32;
    }

    public static int getPackedPositionGroup(long j) {
        if (j == 4294967295L) {
            return -1;
        }
        return (int) ((j & PACKED_POSITION_MASK_GROUP) >> 32);
    }

    public static int getPackedPositionType(long j) {
        if (j == 4294967295L) {
            return 2;
        }
        return (j & Long.MIN_VALUE) == Long.MIN_VALUE ? 1 : 0;
    }

    static {
        int[] iArr = new int[0];
        EMPTY_STATE_SET = iArr;
        int[] iArr2 = {16842920};
        GROUP_EXPANDED_STATE_SET = iArr2;
        int[] iArr3 = {16842921};
        GROUP_EMPTY_STATE_SET = iArr3;
        int[] iArr4 = {16842920, 16842921};
        GROUP_EXPANDED_EMPTY_STATE_SET = iArr4;
        GROUP_STATE_SETS = new int[][]{iArr, iArr2, iArr3, iArr4};
    }

    public ExpandableListView(Context context) {
        this(context, null);
    }

    public ExpandableListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842863);
    }

    public ExpandableListView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ExpandableListView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Drawable drawable;
        this.mIndicatorRect = new Rect();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableListView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.ExpandableListView, attributeSet, obtainStyledAttributes, i, i2);
        this.mGroupIndicator = obtainStyledAttributes.getDrawable(0);
        this.mChildIndicator = obtainStyledAttributes.getDrawable(1);
        this.mIndicatorLeft = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        this.mIndicatorRight = dimensionPixelSize;
        if (dimensionPixelSize == 0 && (drawable = this.mGroupIndicator) != null) {
            this.mIndicatorRight = this.mIndicatorLeft + drawable.getIntrinsicWidth();
        }
        this.mChildIndicatorLeft = obtainStyledAttributes.getDimensionPixelSize(4, -1);
        this.mChildIndicatorRight = obtainStyledAttributes.getDimensionPixelSize(5, -1);
        this.mChildDivider = obtainStyledAttributes.getDrawable(6);
        if (!isRtlCompatibilityMode()) {
            this.mIndicatorStart = obtainStyledAttributes.getDimensionPixelSize(7, -2);
            this.mIndicatorEnd = obtainStyledAttributes.getDimensionPixelSize(8, -2);
            this.mChildIndicatorStart = obtainStyledAttributes.getDimensionPixelSize(9, -1);
            this.mChildIndicatorEnd = obtainStyledAttributes.getDimensionPixelSize(10, -1);
        }
        obtainStyledAttributes.recycle();
    }

    private boolean isRtlCompatibilityMode() {
        return this.mContext.getApplicationInfo().targetSdkVersion < 17 || !hasRtlSupport();
    }

    private boolean hasRtlSupport() {
        return this.mContext.getApplicationInfo().hasRtlSupport();
    }

    @Override // android.widget.AbsListView, android.view.View
    public void onRtlPropertiesChanged(int i) {
        resolveIndicator();
        resolveChildIndicator();
    }

    private void resolveIndicator() {
        Drawable drawable;
        if (isLayoutRtl()) {
            int i = this.mIndicatorStart;
            if (i >= 0) {
                this.mIndicatorRight = i;
            }
            int i2 = this.mIndicatorEnd;
            if (i2 >= 0) {
                this.mIndicatorLeft = i2;
            }
        } else {
            int i3 = this.mIndicatorStart;
            if (i3 >= 0) {
                this.mIndicatorLeft = i3;
            }
            int i4 = this.mIndicatorEnd;
            if (i4 >= 0) {
                this.mIndicatorRight = i4;
            }
        }
        if (this.mIndicatorRight != 0 || (drawable = this.mGroupIndicator) == null) {
            return;
        }
        this.mIndicatorRight = this.mIndicatorLeft + drawable.getIntrinsicWidth();
    }

    private void resolveChildIndicator() {
        if (isLayoutRtl()) {
            int i = this.mChildIndicatorStart;
            if (i >= -1) {
                this.mChildIndicatorRight = i;
            }
            int i2 = this.mChildIndicatorEnd;
            if (i2 >= -1) {
                this.mChildIndicatorLeft = i2;
                return;
            }
            return;
        }
        int i3 = this.mChildIndicatorStart;
        if (i3 >= -1) {
            this.mChildIndicatorLeft = i3;
        }
        int i4 = this.mChildIndicatorEnd;
        if (i4 >= -1) {
            this.mChildIndicatorRight = i4;
        }
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        int i;
        boolean z;
        super.dispatchDraw(canvas);
        if (this.mChildIndicator == null && this.mGroupIndicator == null) {
            return;
        }
        int i2 = 0;
        boolean z2 = true;
        boolean z3 = (this.mGroupFlags & 34) == 34;
        if (z3) {
            i = canvas.save();
            int i3 = this.mScrollX;
            int i4 = this.mScrollY;
            canvas.clipRect(this.mPaddingLeft + i3, this.mPaddingTop + i4, ((i3 + this.mRight) - this.mLeft) - this.mPaddingRight, ((i4 + this.mBottom) - this.mTop) - this.mPaddingBottom);
        } else {
            i = 0;
        }
        int headerViewsCount = getHeaderViewsCount();
        int footerViewsCount = ((this.mItemCount - getFooterViewsCount()) - headerViewsCount) - 1;
        int i5 = this.mBottom;
        Rect rect = this.mIndicatorRect;
        int childCount = getChildCount();
        int i6 = this.mFirstPosition - headerViewsCount;
        int i7 = -4;
        while (i2 < childCount) {
            if (i6 >= 0) {
                if (i6 > footerViewsCount) {
                    break;
                }
                View childAt = getChildAt(i2);
                int top = childAt.getTop();
                int bottom = childAt.getBottom();
                if (bottom >= 0 && top <= i5) {
                    ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(i6);
                    boolean isLayoutRtl = isLayoutRtl();
                    int width = getWidth();
                    if (unflattenedPos.position.type != i7) {
                        if (unflattenedPos.position.type == 1) {
                            int i8 = this.mChildIndicatorLeft;
                            if (i8 == -1) {
                                i8 = this.mIndicatorLeft;
                            }
                            rect.left = i8;
                            int i9 = this.mChildIndicatorRight;
                            if (i9 == -1) {
                                i9 = this.mIndicatorRight;
                            }
                            rect.right = i9;
                        } else {
                            rect.left = this.mIndicatorLeft;
                            rect.right = this.mIndicatorRight;
                        }
                        if (isLayoutRtl) {
                            int i10 = rect.left;
                            rect.left = width - rect.right;
                            rect.right = width - i10;
                            rect.left -= this.mPaddingRight;
                            rect.right -= this.mPaddingRight;
                        } else {
                            rect.left += this.mPaddingLeft;
                            rect.right += this.mPaddingLeft;
                        }
                        i7 = unflattenedPos.position.type;
                    }
                    z = true;
                    if (rect.left != rect.right) {
                        if (this.mStackFromBottom) {
                            rect.top = top;
                            rect.bottom = bottom;
                        } else {
                            rect.top = top;
                            rect.bottom = bottom;
                        }
                        Drawable indicator = getIndicator(unflattenedPos);
                        if (indicator != null) {
                            indicator.setBounds(rect);
                            indicator.draw(canvas);
                        }
                    }
                    unflattenedPos.recycle();
                    i2++;
                    i6++;
                    z2 = z;
                }
            }
            z = z2;
            i2++;
            i6++;
            z2 = z;
        }
        if (z3) {
            canvas.restoreToCount(i);
        }
    }

    private Drawable getIndicator(ExpandableListConnector.PositionMetadata positionMetadata) {
        int[] iArr;
        if (positionMetadata.position.type == 2) {
            Drawable drawable = this.mGroupIndicator;
            if (drawable != null && drawable.isStateful()) {
                boolean z = positionMetadata.groupMetadata == null || positionMetadata.groupMetadata.lastChildFlPos == positionMetadata.groupMetadata.flPos;
                drawable.setState(GROUP_STATE_SETS[(positionMetadata.isExpanded() ? 1 : 0) | (z ? 2 : 0)]);
            }
            return drawable;
        }
        Drawable drawable2 = this.mChildIndicator;
        if (drawable2 != null && drawable2.isStateful()) {
            if (positionMetadata.position.flatListPos == positionMetadata.groupMetadata.lastChildFlPos) {
                iArr = CHILD_LAST_STATE_SET;
            } else {
                iArr = EMPTY_STATE_SET;
            }
            drawable2.setState(iArr);
        }
        return drawable2;
    }

    public void setChildDivider(Drawable drawable) {
        this.mChildDivider = drawable;
    }

    @Override // android.widget.ListView
    void drawDivider(Canvas canvas, Rect rect, int i) {
        int i2 = i + this.mFirstPosition;
        if (i2 >= 0) {
            ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(i2));
            if (unflattenedPos.position.type == 1 || (unflattenedPos.isExpanded() && unflattenedPos.groupMetadata.lastChildFlPos != unflattenedPos.groupMetadata.flPos)) {
                Drawable drawable = this.mChildDivider;
                drawable.setBounds(rect);
                drawable.draw(canvas);
                unflattenedPos.recycle();
                return;
            }
            unflattenedPos.recycle();
        }
        super.drawDivider(canvas, rect, i2);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView
    public void setAdapter(ListAdapter listAdapter) {
        throw new RuntimeException("For ExpandableListView, use setAdapter(ExpandableListAdapter) instead of setAdapter(ListAdapter)");
    }

    @Override // android.widget.ListView, android.widget.AdapterView
    public ListAdapter getAdapter() {
        return super.getAdapter();
    }

    @Override // android.widget.AdapterView
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
    }

    public void setAdapter(ExpandableListAdapter expandableListAdapter) {
        this.mAdapter = expandableListAdapter;
        if (expandableListAdapter != null) {
            this.mConnector = new ExpandableListConnector(expandableListAdapter);
        } else {
            this.mConnector = null;
        }
        super.setAdapter((ListAdapter) this.mConnector);
    }

    public ExpandableListAdapter getExpandableListAdapter() {
        return this.mAdapter;
    }

    private boolean isHeaderOrFooterPosition(int i) {
        return i < getHeaderViewsCount() || i >= this.mItemCount - getFooterViewsCount();
    }

    private int getFlatPositionForConnector(int i) {
        return i - getHeaderViewsCount();
    }

    private int getAbsoluteFlatPosition(int i) {
        return i + getHeaderViewsCount();
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView
    public boolean performItemClick(View view, int i, long j) {
        if (isHeaderOrFooterPosition(i)) {
            return super.performItemClick(view, i, j);
        }
        boolean handleItemClick = handleItemClick(view, getFlatPositionForConnector(i), j);
        if (view != null) {
            view.sendAccessibilityEvent(1);
        }
        return handleItemClick;
    }

    boolean handleItemClick(View view, int i, long j) {
        ExpandableListView expandableListView;
        ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(i);
        long childOrGroupId = getChildOrGroupId(unflattenedPos.position);
        boolean z = false;
        if (unflattenedPos.position.type == 2) {
            OnGroupClickListener onGroupClickListener = this.mOnGroupClickListener;
            if (onGroupClickListener != null) {
                expandableListView = this;
                if (onGroupClickListener.onGroupClick(expandableListView, view, unflattenedPos.position.groupPos, childOrGroupId)) {
                    unflattenedPos.recycle();
                    return true;
                }
            } else {
                expandableListView = this;
            }
            if (unflattenedPos.isExpanded()) {
                expandableListView.mConnector.collapseGroup(unflattenedPos);
                expandableListView.playSoundEffect(0);
                OnGroupCollapseListener onGroupCollapseListener = expandableListView.mOnGroupCollapseListener;
                if (onGroupCollapseListener != null) {
                    onGroupCollapseListener.onGroupCollapse(unflattenedPos.position.groupPos);
                }
            } else {
                expandableListView.mConnector.expandGroup(unflattenedPos);
                expandableListView.playSoundEffect(0);
                OnGroupExpandListener onGroupExpandListener = expandableListView.mOnGroupExpandListener;
                if (onGroupExpandListener != null) {
                    onGroupExpandListener.onGroupExpand(unflattenedPos.position.groupPos);
                }
                int i2 = unflattenedPos.position.groupPos;
                int headerViewsCount = unflattenedPos.position.flatListPos + expandableListView.getHeaderViewsCount();
                expandableListView.smoothScrollToPosition(expandableListView.mAdapter.getChildrenCount(i2) + headerViewsCount, headerViewsCount);
            }
            z = true;
        } else if (this.mOnChildClickListener != null) {
            playSoundEffect(0);
            return this.mOnChildClickListener.onChildClick(this, view, unflattenedPos.position.groupPos, unflattenedPos.position.childPos, childOrGroupId);
        }
        unflattenedPos.recycle();
        return z;
    }

    public boolean expandGroup(int i) {
        return expandGroup(i, false);
    }

    public boolean expandGroup(int i, boolean z) {
        ExpandableListPosition obtain = ExpandableListPosition.obtain(2, i, -1, -1);
        ExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtain);
        obtain.recycle();
        boolean expandGroup = this.mConnector.expandGroup(flattenedPos);
        OnGroupExpandListener onGroupExpandListener = this.mOnGroupExpandListener;
        if (onGroupExpandListener != null) {
            onGroupExpandListener.onGroupExpand(i);
        }
        if (z) {
            int headerViewsCount = flattenedPos.position.flatListPos + getHeaderViewsCount();
            smoothScrollToPosition(this.mAdapter.getChildrenCount(i) + headerViewsCount, headerViewsCount);
        }
        flattenedPos.recycle();
        return expandGroup;
    }

    public boolean collapseGroup(int i) {
        boolean collapseGroup = this.mConnector.collapseGroup(i);
        OnGroupCollapseListener onGroupCollapseListener = this.mOnGroupCollapseListener;
        if (onGroupCollapseListener != null) {
            onGroupCollapseListener.onGroupCollapse(i);
        }
        return collapseGroup;
    }

    public void setOnGroupCollapseListener(OnGroupCollapseListener onGroupCollapseListener) {
        this.mOnGroupCollapseListener = onGroupCollapseListener;
    }

    public void setOnGroupExpandListener(OnGroupExpandListener onGroupExpandListener) {
        this.mOnGroupExpandListener = onGroupExpandListener;
    }

    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener) {
        this.mOnGroupClickListener = onGroupClickListener;
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.mOnChildClickListener = onChildClickListener;
    }

    public long getExpandableListPosition(int i) {
        if (isHeaderOrFooterPosition(i)) {
            return 4294967295L;
        }
        ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(i));
        long packedPosition = unflattenedPos.position.getPackedPosition();
        unflattenedPos.recycle();
        return packedPosition;
    }

    public int getFlatListPosition(long j) {
        ExpandableListPosition obtainPosition = ExpandableListPosition.obtainPosition(j);
        ExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtainPosition);
        obtainPosition.recycle();
        int i = flattenedPos.position.flatListPos;
        flattenedPos.recycle();
        return getAbsoluteFlatPosition(i);
    }

    public long getSelectedPosition() {
        return getExpandableListPosition(getSelectedItemPosition());
    }

    public long getSelectedId() {
        long selectedPosition = getSelectedPosition();
        if (selectedPosition == 4294967295L) {
            return -1L;
        }
        int packedPositionGroup = getPackedPositionGroup(selectedPosition);
        if (getPackedPositionType(selectedPosition) == 0) {
            return this.mAdapter.getGroupId(packedPositionGroup);
        }
        return this.mAdapter.getChildId(packedPositionGroup, getPackedPositionChild(selectedPosition));
    }

    public void setSelectedGroup(int i) {
        ExpandableListPosition obtainGroupPosition = ExpandableListPosition.obtainGroupPosition(i);
        ExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtainGroupPosition);
        obtainGroupPosition.recycle();
        super.setSelection(getAbsoluteFlatPosition(flattenedPos.position.flatListPos));
        flattenedPos.recycle();
    }

    public boolean setSelectedChild(int i, int i2, boolean z) {
        ExpandableListPosition obtainChildPosition = ExpandableListPosition.obtainChildPosition(i, i2);
        ExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtainChildPosition);
        if (flattenedPos == null) {
            if (!z) {
                return false;
            }
            expandGroup(i);
            flattenedPos = this.mConnector.getFlattenedPos(obtainChildPosition);
            if (flattenedPos == null) {
                throw new IllegalStateException("Could not find child");
            }
        }
        super.setSelection(getAbsoluteFlatPosition(flattenedPos.position.flatListPos));
        obtainChildPosition.recycle();
        flattenedPos.recycle();
        return true;
    }

    public boolean isGroupExpanded(int i) {
        return this.mConnector.isGroupExpanded(i);
    }

    @Override // android.widget.AbsListView
    ContextMenu.ContextMenuInfo createContextMenuInfo(View view, int i, long j) {
        if (isHeaderOrFooterPosition(i)) {
            return new AdapterView.AdapterContextMenuInfo(view, i, j);
        }
        ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(i));
        ExpandableListPosition expandableListPosition = unflattenedPos.position;
        long childOrGroupId = getChildOrGroupId(expandableListPosition);
        long packedPosition = expandableListPosition.getPackedPosition();
        unflattenedPos.recycle();
        return new ExpandableListContextMenuInfo(view, packedPosition, childOrGroupId);
    }

    @Override // android.widget.ListView, android.widget.AbsListView
    public void onInitializeAccessibilityNodeInfoForItem(View view, int i, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoForItem(view, i, accessibilityNodeInfo);
        ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(i);
        if (unflattenedPos.position.type == 2 && view != null && view.isEnabled()) {
            accessibilityNodeInfo.setClickable(true);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            if (isGroupExpanded(unflattenedPos.position.groupPos)) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
            } else {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
            }
        }
        unflattenedPos.recycle();
    }

    private long getChildOrGroupId(ExpandableListPosition expandableListPosition) {
        if (expandableListPosition.type == 1) {
            return this.mAdapter.getChildId(expandableListPosition.groupPos, expandableListPosition.childPos);
        }
        return this.mAdapter.getGroupId(expandableListPosition.groupPos);
    }

    public void setChildIndicator(Drawable drawable) {
        this.mChildIndicator = drawable;
    }

    public void setChildIndicatorBounds(int i, int i2) {
        this.mChildIndicatorLeft = i;
        this.mChildIndicatorRight = i2;
        resolveChildIndicator();
    }

    public void setChildIndicatorBoundsRelative(int i, int i2) {
        this.mChildIndicatorStart = i;
        this.mChildIndicatorEnd = i2;
        resolveChildIndicator();
    }

    public void setGroupIndicator(Drawable drawable) {
        this.mGroupIndicator = drawable;
        if (this.mIndicatorRight != 0 || drawable == null) {
            return;
        }
        this.mIndicatorRight = this.mIndicatorLeft + drawable.getIntrinsicWidth();
    }

    public void setIndicatorBounds(int i, int i2) {
        this.mIndicatorLeft = i;
        this.mIndicatorRight = i2;
        resolveIndicator();
    }

    public void setIndicatorBoundsRelative(int i, int i2) {
        this.mIndicatorStart = i;
        this.mIndicatorEnd = i2;
        resolveIndicator();
    }

    public static class ExpandableListContextMenuInfo implements ContextMenu.ContextMenuInfo {
        public long id;
        public long packedPosition;
        public View targetView;

        public ExpandableListContextMenuInfo(View view, long j, long j2) {
            this.targetView = view;
            this.packedPosition = j;
            this.id = j2;
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.ExpandableListView.SavedState.1
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
        ArrayList<ExpandableListConnector.GroupMetadata> expandedGroupMetadataList;

        SavedState(Parcelable parcelable, ArrayList<ExpandableListConnector.GroupMetadata> arrayList) {
            super(parcelable);
            this.expandedGroupMetadataList = arrayList;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            ArrayList<ExpandableListConnector.GroupMetadata> arrayList = new ArrayList<>();
            this.expandedGroupMetadataList = arrayList;
            parcel.readList(arrayList, ExpandableListConnector.class.getClassLoader(), ExpandableListConnector.GroupMetadata.class);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeList(this.expandedGroupMetadataList);
        }
    }

    @Override // android.widget.AbsListView, android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        ExpandableListConnector expandableListConnector = this.mConnector;
        return new SavedState(onSaveInstanceState, expandableListConnector != null ? expandableListConnector.getExpandedGroupMetadataList() : null);
    }

    @Override // android.widget.AbsListView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.mConnector == null || savedState.expandedGroupMetadataList == null) {
            return;
        }
        this.mConnector.setExpandedGroupMetadataList(savedState.expandedGroupMetadataList);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ExpandableListView.class.getName();
    }
}
