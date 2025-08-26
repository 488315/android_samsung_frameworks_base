package android.widget;

import android.app.jank.AppJankStats;
import android.app.slice.Slice;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Paint;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.autofill.AutofillManager;
import android.widget.Adapter;
import com.android.internal.widget.ScrollingTabContainerView;
import java.io.IOException;

/* loaded from: classes5.dex */
public abstract class AdapterView<T extends Adapter> extends ViewGroup {
    public static final int INVALID_POSITION = -1;
    public static final long INVALID_ROW_ID = Long.MIN_VALUE;
    public static final int ITEM_VIEW_TYPE_HEADER_OR_FOOTER = -2;
    public static final int ITEM_VIEW_TYPE_IGNORE = -1;
    static final int SYNC_FIRST_POSITION = 1;
    static final int SYNC_MAX_DURATION_MILLIS = 100;
    static final int SYNC_SELECTED_POSITION = 0;
    private static final String TAG = "AdapterView";
    int mAppWidgetId;
    boolean mBlockLayoutRequests;
    boolean mDataChanged;
    private boolean mDesiredFocusableInTouchModeState;
    private int mDesiredFocusableState;
    private View mEmptyView;

    @ViewDebug.ExportedProperty(category = AppJankStats.WIDGET_STATE_SCROLLING)
    int mFirstPosition;
    boolean mInLayout;

    @ViewDebug.ExportedProperty(category = Slice.HINT_LIST)
    int mItemCount;
    private int mLayoutHeight;
    boolean mNeedSync;

    @ViewDebug.ExportedProperty(category = Slice.HINT_LIST)
    int mNextSelectedPosition;
    long mNextSelectedRowId;
    int mOldItemCount;
    int mOldSelectedPosition;
    long mOldSelectedRowId;
    OnItemClickListener mOnItemClickListener;
    OnItemLongClickListener mOnItemLongClickListener;
    OnItemSelectedListener mOnItemSelectedListener;
    private boolean mPenPressState;
    private AdapterView<T>.SelectionNotifier mPendingSelectionNotifier;

    @ViewDebug.ExportedProperty(category = Slice.HINT_LIST)
    int mSelectedPosition;
    long mSelectedRowId;
    private AdapterView<T>.SelectionNotifier mSelectionNotifier;
    boolean mSemAdapterChanged;
    boolean mSemEnableFillOut;
    int mSemFillOutEmptyArea;
    Paint mSemFillOutPaint;
    SemLongPressMultiSelectionListener mSemLongPressMultiSelectionListener;
    SemMultiSelectionListener mSemMultiSelectionListener;
    SemOnMultiSelectedListener mSemOnMultiSelectedListener;
    SemOnNotifyKeyPressListener mSemOnNotifyKeyPressListener;
    int mSpecificTop;
    long mSyncHeight;
    int mSyncMode;
    int mSyncPosition;
    long mSyncRowId;

    public interface OnItemClickListener {
        void onItemClick(AdapterView<?> adapterView, View view, int i, long j);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(AdapterView<?> adapterView, View view, int i, long j);

        void onNothingSelected(AdapterView<?> adapterView);
    }

    public interface SemLongPressMultiSelectionListener {
        void onItemSelected(AdapterView<?> adapterView, View view, int i, long j);

        void onLongPressMultiSelectionEnded(int i, int i2);

        void onLongPressMultiSelectionStarted(int i, int i2);
    }

    public interface SemMultiSelectionListener {
        void onMultiSelectionEnded(int i, int i2);

        void onMultiSelectionStarted(int i, int i2);
    }

    public interface SemOnMultiSelectedListener {
        void onMultiSelectStart(int i, int i2);

        void onMultiSelectStop(int i, int i2);

        void onMultiSelected(AdapterView<?> adapterView, View view, int i, long j, boolean z, boolean z2, boolean z3);
    }

    public interface SemOnNotifyKeyPressListener {
        void onNotifyKeyPress(AdapterView<?> adapterView, View view, int i, long j, boolean z);
    }

    public abstract T getAdapter();

    public abstract View getSelectedView();

    boolean isInFilterMode() {
        return false;
    }

    int lookForSelectablePosition(int i, boolean z) {
        return i;
    }

    public abstract void setAdapter(T t);

    public abstract void setSelection(int i);

    public AdapterView(Context context) {
        this(context, null);
    }

    public AdapterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AdapterView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AdapterView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mFirstPosition = 0;
        this.mSyncRowId = Long.MIN_VALUE;
        this.mNeedSync = false;
        this.mInLayout = false;
        this.mSemAdapterChanged = false;
        this.mSemFillOutEmptyArea = -1;
        this.mSemFillOutPaint = new Paint();
        this.mSemEnableFillOut = false;
        this.mAppWidgetId = 0;
        this.mNextSelectedPosition = -1;
        this.mNextSelectedRowId = Long.MIN_VALUE;
        this.mSelectedPosition = -1;
        this.mSelectedRowId = Long.MIN_VALUE;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        this.mDesiredFocusableState = 16;
        this.mBlockLayoutRequests = false;
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        int focusable = getFocusable();
        this.mDesiredFocusableState = focusable;
        if (focusable == 16) {
            super.setFocusable(0);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public final OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    public boolean performItemClick(View view, int i, long j) {
        View view2;
        OnItemClickListener onItemClickListener = this.mOnItemClickListener;
        boolean z = false;
        if (onItemClickListener != null) {
            if (!(onItemClickListener instanceof ScrollingTabContainerView) && !this.mPenPressState) {
                playSoundEffect(0);
            }
            view2 = view;
            this.mOnItemClickListener.onItemClick(this, view2, i, j);
            z = true;
        } else {
            view2 = view;
        }
        if (view2 != null) {
            view2.sendAccessibilityEvent(1);
        }
        return z;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public final OnItemSelectedListener getOnItemSelectedListener() {
        return this.mOnItemSelectedListener;
    }

    @RemotableViewMethod
    public void semSetBottomColor(int i) {
        if (!sIsSamsungBasicInteraction) {
            Log.d(TAG, "App should add meta data for Samsung UX first.");
        }
        this.mSemEnableFillOut = sIsSamsungBasicInteraction && i != 0;
        this.mSemFillOutPaint.setColor(i);
    }

    private void hidden_semSetBottomColor(int i) {
        semSetBottomColor(i);
    }

    public void hidden_semSetAppWidgetId(int i) {
        this.mAppWidgetId = i;
    }

    public void semSetOnMultiSelectedListener(SemOnMultiSelectedListener semOnMultiSelectedListener) {
        this.mSemOnMultiSelectedListener = semOnMultiSelectedListener;
    }

    public final SemOnMultiSelectedListener semGetOnMultiSelectedListener() {
        return this.mSemOnMultiSelectedListener;
    }

    protected boolean semNotifyMultiSelectedState(View view, int i, long j, boolean z, boolean z2, boolean z3) {
        if (this.mSemMultiSelectionListener != null) {
            return true;
        }
        this.mPenPressState = z3;
        SemOnMultiSelectedListener semOnMultiSelectedListener = this.mSemOnMultiSelectedListener;
        if (semOnMultiSelectedListener == null) {
            return false;
        }
        semOnMultiSelectedListener.onMultiSelected(this, view, i, j, z, z2, z3);
        return true;
    }

    void semNotifyMultiSelectedStart(int i, int i2) {
        SemMultiSelectionListener semMultiSelectionListener = this.mSemMultiSelectionListener;
        if (semMultiSelectionListener != null) {
            semMultiSelectionListener.onMultiSelectionStarted(i, i2);
            return;
        }
        SemOnMultiSelectedListener semOnMultiSelectedListener = this.mSemOnMultiSelectedListener;
        if (semOnMultiSelectedListener != null) {
            semOnMultiSelectedListener.onMultiSelectStart(i, i2);
        }
    }

    void semNotifyMultiSelectedStop(int i, int i2) {
        SemMultiSelectionListener semMultiSelectionListener = this.mSemMultiSelectionListener;
        if (semMultiSelectionListener != null) {
            semMultiSelectionListener.onMultiSelectionEnded(i, i2);
            return;
        }
        this.mPenPressState = false;
        SemOnMultiSelectedListener semOnMultiSelectedListener = this.mSemOnMultiSelectedListener;
        if (semOnMultiSelectedListener != null) {
            semOnMultiSelectedListener.onMultiSelectStop(i, i2);
        }
    }

    public void semSetMultiSelectionListener(SemMultiSelectionListener semMultiSelectionListener) {
        this.mSemMultiSelectionListener = semMultiSelectionListener;
    }

    @Deprecated
    public final SemMultiSelectionListener semGetMultiSelectionListener() {
        return this.mSemMultiSelectionListener;
    }

    public void semSetLongPressMultiSelectionListener(SemLongPressMultiSelectionListener semLongPressMultiSelectionListener) {
        this.mSemLongPressMultiSelectionListener = semLongPressMultiSelectionListener;
    }

    @Deprecated
    public final SemLongPressMultiSelectionListener semGetLongPressMultiSelectionListener() {
        return this.mSemLongPressMultiSelectionListener;
    }

    boolean semNotifyLongPressMultiSelectionState(View view, int i, long j) {
        SemLongPressMultiSelectionListener semLongPressMultiSelectionListener = this.mSemLongPressMultiSelectionListener;
        if (semLongPressMultiSelectionListener == null) {
            return false;
        }
        semLongPressMultiSelectionListener.onItemSelected(this, view, i, j);
        return true;
    }

    void semNotifyLongPressMultiSelectionStarted(int i, int i2) {
        SemLongPressMultiSelectionListener semLongPressMultiSelectionListener = this.mSemLongPressMultiSelectionListener;
        if (semLongPressMultiSelectionListener != null) {
            semLongPressMultiSelectionListener.onLongPressMultiSelectionStarted(i, i2);
        }
    }

    void semNotifyLongPressMultiSelectionEnded(int i, int i2) {
        SemLongPressMultiSelectionListener semLongPressMultiSelectionListener = this.mSemLongPressMultiSelectionListener;
        if (semLongPressMultiSelectionListener != null) {
            semLongPressMultiSelectionListener.onLongPressMultiSelectionEnded(i, i2);
        }
    }

    public void semSetNotifyOnKeyPressListener(SemOnNotifyKeyPressListener semOnNotifyKeyPressListener) {
        this.mSemOnNotifyKeyPressListener = semOnNotifyKeyPressListener;
    }

    public final SemOnNotifyKeyPressListener semGetOnNotifyKeyPressListener() {
        return this.mSemOnNotifyKeyPressListener;
    }

    public boolean semNotifyKeyPress(View view, int i, long j, boolean z) {
        SemOnNotifyKeyPressListener semOnNotifyKeyPressListener = this.mSemOnNotifyKeyPressListener;
        if (semOnNotifyKeyPressListener == null) {
            return false;
        }
        semOnNotifyKeyPressListener.onNotifyKeyPress(this, view, i, j, z);
        return true;
    }

    public static class AdapterContextMenuInfo implements ContextMenu.ContextMenuInfo {
        public long id;
        public int position;
        public View targetView;

        public AdapterContextMenuInfo(View view, int i, long j) {
            this.targetView = view;
            this.position = i;
            this.id = j;
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view) {
        throw new UnsupportedOperationException("addView(View) is not supported in AdapterView");
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i) {
        throw new UnsupportedOperationException("addView(View, int) is not supported in AdapterView");
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        throw new UnsupportedOperationException("addView(View, LayoutParams) is not supported in AdapterView");
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        throw new UnsupportedOperationException("addView(View, int, LayoutParams) is not supported in AdapterView");
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view) {
        throw new UnsupportedOperationException("removeView(View) is not supported in AdapterView");
    }

    @Override // android.view.ViewGroup
    public void removeViewAt(int i) {
        throw new UnsupportedOperationException("removeViewAt(int) is not supported in AdapterView");
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        throw new UnsupportedOperationException("removeAllViews() is not supported in AdapterView");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mLayoutHeight = getHeight();
    }

    @ViewDebug.CapturedViewProperty
    public int getSelectedItemPosition() {
        return this.mNextSelectedPosition;
    }

    @ViewDebug.CapturedViewProperty
    public long getSelectedItemId() {
        return this.mNextSelectedRowId;
    }

    public Object getSelectedItem() {
        Adapter adapter = getAdapter();
        int selectedItemPosition = getSelectedItemPosition();
        if (adapter == null || adapter.getCount() <= 0 || selectedItemPosition < 0) {
            return null;
        }
        return adapter.getItem(selectedItemPosition);
    }

    @ViewDebug.CapturedViewProperty
    public int getCount() {
        return this.mItemCount;
    }

    public int getPositionForView(View view) {
        while (true) {
            try {
                View view2 = (View) view.getParent();
                if (view2 == null || view2.equals(this)) {
                    break;
                }
                view = view2;
            } catch (ClassCastException unused) {
            }
        }
        if (view != null) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (getChildAt(i).equals(view)) {
                    return this.mFirstPosition + i;
                }
            }
        }
        return -1;
    }

    public int getFirstVisiblePosition() {
        return this.mFirstPosition;
    }

    public int getLastVisiblePosition() {
        return (this.mFirstPosition + getChildCount()) - 1;
    }

    @RemotableViewMethod
    public void setEmptyView(View view) {
        this.mEmptyView = view;
        boolean z = true;
        if (view != null && view.getImportantForAccessibility() == 0) {
            view.setImportantForAccessibility(1);
        }
        Adapter adapter = getAdapter();
        if (adapter != null && !adapter.isEmpty()) {
            z = false;
        }
        updateEmptyStatus(z);
    }

    public View getEmptyView() {
        return this.mEmptyView;
    }

    @Override // android.view.View
    public void setFocusable(int i) {
        Adapter adapter = getAdapter();
        boolean z = adapter == null || adapter.getCount() == 0;
        this.mDesiredFocusableState = i;
        if ((i & 17) == 0) {
            this.mDesiredFocusableInTouchModeState = false;
        }
        if (z && !isInFilterMode()) {
            i = 0;
        }
        super.setFocusable(i);
    }

    @Override // android.view.View
    public void setFocusableInTouchMode(boolean z) {
        Adapter adapter = getAdapter();
        boolean z2 = false;
        boolean z3 = adapter == null || adapter.getCount() == 0;
        this.mDesiredFocusableInTouchModeState = z;
        if (z) {
            this.mDesiredFocusableState = 1;
        }
        if (z && (!z3 || isInFilterMode())) {
            z2 = true;
        }
        super.setFocusableInTouchMode(z2);
    }

    void checkFocus() {
        Adapter adapter = getAdapter();
        boolean z = true;
        boolean z2 = !(adapter == null || adapter.getCount() == 0) || isInFilterMode();
        super.setFocusableInTouchMode(z2 && this.mDesiredFocusableInTouchModeState);
        super.setFocusable(z2 ? this.mDesiredFocusableState : 0);
        if (this.mEmptyView != null) {
            if (adapter != null && !adapter.isEmpty()) {
                z = false;
            }
            updateEmptyStatus(z);
        }
    }

    private void updateEmptyStatus(boolean z) {
        if (isInFilterMode()) {
            z = false;
        }
        if (z) {
            View view = this.mEmptyView;
            if (view != null) {
                view.setVisibility(0);
                setVisibility(8);
            } else {
                setVisibility(0);
            }
            if (this.mDataChanged) {
                onLayout(false, this.mLeft, this.mTop, this.mRight, this.mBottom);
                return;
            }
            return;
        }
        View view2 = this.mEmptyView;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        setVisibility(0);
    }

    public Object getItemAtPosition(int i) {
        Adapter adapter = getAdapter();
        if (adapter == null || i < 0) {
            return null;
        }
        return adapter.getItem(i);
    }

    public long getItemIdAtPosition(int i) {
        Adapter adapter = getAdapter();
        if (adapter == null || i < 0) {
            return Long.MIN_VALUE;
        }
        return adapter.getItemId(i);
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        throw new RuntimeException("Don't call setOnClickListener for an AdapterView. You probably want setOnItemClickListener instead");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    class AdapterDataSetObserver extends DataSetObserver {
        private Parcelable mInstanceState = null;

        AdapterDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            AdapterView.this.mDataChanged = true;
            AdapterView adapterView = AdapterView.this;
            adapterView.mOldItemCount = adapterView.mItemCount;
            AdapterView adapterView2 = AdapterView.this;
            adapterView2.mItemCount = adapterView2.getAdapter().getCount();
            if (AdapterView.this.getAdapter().hasStableIds() && this.mInstanceState != null && AdapterView.this.mOldItemCount == 0 && AdapterView.this.mItemCount > 0) {
                AdapterView.this.onRestoreInstanceState(this.mInstanceState);
                this.mInstanceState = null;
            } else {
                AdapterView.this.rememberSyncState();
            }
            AdapterView.this.checkFocus();
            AdapterView.this.requestLayout();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            AdapterView.this.mDataChanged = true;
            if (AdapterView.this.getAdapter().hasStableIds()) {
                this.mInstanceState = AdapterView.this.onSaveInstanceState();
            }
            AdapterView adapterView = AdapterView.this;
            adapterView.mOldItemCount = adapterView.mItemCount;
            AdapterView.this.mItemCount = 0;
            AdapterView.this.mSelectedPosition = -1;
            AdapterView.this.mSelectedRowId = Long.MIN_VALUE;
            AdapterView.this.mNextSelectedPosition = -1;
            AdapterView.this.mNextSelectedRowId = Long.MIN_VALUE;
            AdapterView.this.mNeedSync = false;
            AdapterView.this.checkFocus();
            AdapterView.this.requestLayout();
        }

        public void clearSavedState() {
            this.mInstanceState = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mSelectionNotifier);
    }

    private class SelectionNotifier implements Runnable {
        private SelectionNotifier() {
        }

        @Override // java.lang.Runnable
        public void run() {
            AdapterView.this.mPendingSelectionNotifier = null;
            if (AdapterView.this.mDataChanged && AdapterView.this.getViewRootImpl() != null && AdapterView.this.getViewRootImpl().isLayoutRequested()) {
                if (AdapterView.this.getAdapter() != null) {
                    AdapterView.this.mPendingSelectionNotifier = this;
                    return;
                }
                return;
            }
            AdapterView.this.dispatchOnItemSelected();
        }
    }

    void selectionChanged() {
        this.mPendingSelectionNotifier = null;
        if (this.mOnItemSelectedListener != null || AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            if (this.mInLayout || this.mBlockLayoutRequests) {
                AdapterView<T>.SelectionNotifier selectionNotifier = this.mSelectionNotifier;
                if (selectionNotifier == null) {
                    this.mSelectionNotifier = new SelectionNotifier();
                } else {
                    removeCallbacks(selectionNotifier);
                }
                post(this.mSelectionNotifier);
            } else {
                dispatchOnItemSelected();
            }
        }
        AutofillManager autofillManager = (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
        if (autofillManager != null) {
            autofillManager.notifyValueChanged(this);
        }
    }

    void selectionChangedForAccessibility() {
        performAccessibilityActionsOnSelected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnItemSelected() {
        fireOnSelected();
        performAccessibilityActionsOnSelected();
    }

    private void fireOnSelected() {
        if (this.mOnItemSelectedListener == null) {
            return;
        }
        int selectedItemPosition = getSelectedItemPosition();
        if (selectedItemPosition >= 0) {
            this.mOnItemSelectedListener.onItemSelected(this, getSelectedView(), selectedItemPosition, getAdapter().getItemId(selectedItemPosition));
        } else {
            this.mOnItemSelectedListener.onNothingSelected(this);
        }
    }

    private void performAccessibilityActionsOnSelected() {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && getSelectedItemPosition() >= 0) {
            post(new Runnable() { // from class: android.widget.AdapterView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$performAccessibilityActionsOnSelected$0();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performAccessibilityActionsOnSelected$0() {
        sendAccessibilityEvent(4);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        View selectedView = getSelectedView();
        return selectedView != null && selectedView.getVisibility() == 0 && selectedView.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.view.ViewGroup
    public boolean onRequestSendAccessibilityEventInternal(View view, AccessibilityEvent accessibilityEvent) {
        if (!super.onRequestSendAccessibilityEventInternal(view, accessibilityEvent)) {
            return false;
        }
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain();
        onInitializeAccessibilityEvent(accessibilityEventObtain);
        view.dispatchPopulateAccessibilityEvent(accessibilityEventObtain);
        accessibilityEvent.appendRecord(accessibilityEventObtain);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return AdapterView.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setScrollable(isScrollableForAccessibility());
        View selectedView = getSelectedView();
        if (selectedView != null) {
            accessibilityNodeInfo.setEnabled(selectedView.isEnabled());
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setScrollable(isScrollableForAccessibility());
        View selectedView = getSelectedView();
        if (selectedView != null) {
            accessibilityEvent.setEnabled(selectedView.isEnabled());
        }
        accessibilityEvent.setCurrentItemIndex(getSelectedItemPosition());
        accessibilityEvent.setFromIndex(getFirstVisiblePosition());
        accessibilityEvent.setToIndex(getLastVisiblePosition());
        accessibilityEvent.setItemCount(getCount());
    }

    private boolean isScrollableForAccessibility() {
        int count;
        Adapter adapter = getAdapter();
        return adapter != null && (count = adapter.getCount()) > 0 && (getFirstVisiblePosition() > 0 || getLastVisiblePosition() < count - 1);
    }

    @Override // android.view.ViewGroup
    protected boolean canAnimate() {
        return super.canAnimate() && this.mItemCount > 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void handleDataChanged() {
        boolean z;
        boolean z2;
        int i = this.mItemCount;
        if (i > 0) {
            z = true;
            if (this.mNeedSync) {
                this.mNeedSync = false;
                int iFindSyncPosition = findSyncPosition();
                if (iFindSyncPosition < 0 || lookForSelectablePosition(iFindSyncPosition, true) != iFindSyncPosition) {
                    z2 = false;
                } else {
                    setNextSelectedPositionInt(iFindSyncPosition);
                    z2 = true;
                }
                if (z2) {
                    z = z2;
                } else {
                    int selectedItemPosition = getSelectedItemPosition();
                    if (selectedItemPosition >= i) {
                        selectedItemPosition = i - 1;
                    }
                    if (selectedItemPosition < 0) {
                        selectedItemPosition = 0;
                    }
                    int iLookForSelectablePosition = lookForSelectablePosition(selectedItemPosition, true);
                    if (iLookForSelectablePosition < 0) {
                        iLookForSelectablePosition = lookForSelectablePosition(selectedItemPosition, false);
                    }
                    if (iLookForSelectablePosition >= 0) {
                        setNextSelectedPositionInt(iLookForSelectablePosition);
                        checkSelectionChanged();
                    }
                }
            }
        } else {
            z = false;
        }
        if (!z) {
            this.mSelectedPosition = -1;
            this.mSelectedRowId = Long.MIN_VALUE;
            this.mNextSelectedPosition = -1;
            this.mNextSelectedRowId = Long.MIN_VALUE;
            this.mNeedSync = false;
            checkSelectionChanged();
        }
        notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    void checkSelectionChanged() {
        if (this.mSelectedPosition != this.mOldSelectedPosition || this.mSelectedRowId != this.mOldSelectedRowId) {
            selectionChanged();
            this.mOldSelectedPosition = this.mSelectedPosition;
            this.mOldSelectedRowId = this.mSelectedRowId;
        }
        AdapterView<T>.SelectionNotifier selectionNotifier = this.mPendingSelectionNotifier;
        if (selectionNotifier != null) {
            selectionNotifier.run();
        }
    }

    int findSyncPosition() {
        int i = this.mItemCount;
        if (i == 0) {
            return -1;
        }
        if (getAdapter() instanceof RemoteViewsAdapter) {
            this.mSyncRowId = getItemIdAtPosition(this.mNextSelectedPosition);
        }
        long j = this.mSyncRowId;
        int i2 = this.mSyncPosition;
        if (j == Long.MIN_VALUE) {
            return -1;
        }
        int i3 = i - 1;
        int iMin = Math.min(i3, Math.max(0, i2));
        long jUptimeMillis = SystemClock.uptimeMillis() + 100;
        Adapter adapter = getAdapter();
        if (adapter == null) {
            return -1;
        }
        int i4 = iMin;
        int i5 = i4;
        boolean z = false;
        while (SystemClock.uptimeMillis() <= jUptimeMillis) {
            if (adapter.getItemId(iMin) != j) {
                boolean z2 = i4 == i3;
                boolean z3 = i5 == 0;
                if (z2 && z3) {
                    break;
                }
                if (z3 || (z && !z2)) {
                    i4++;
                    z = false;
                    iMin = i4;
                } else if (z2 || (!z && !z3)) {
                    i5--;
                    z = true;
                    iMin = i5;
                }
            } else {
                return iMin;
            }
        }
        return -1;
    }

    void setSelectedPositionInt(int i) {
        this.mSelectedPosition = i;
        this.mSelectedRowId = getItemIdAtPosition(i);
    }

    void setNextSelectedPositionInt(int i) {
        this.mNextSelectedPosition = i;
        long itemIdAtPosition = getItemIdAtPosition(i);
        this.mNextSelectedRowId = itemIdAtPosition;
        if (this.mNeedSync && this.mSyncMode == 0 && i >= 0) {
            this.mSyncPosition = i;
            this.mSyncRowId = itemIdAtPosition;
        }
    }

    void rememberSyncState() {
        if (getChildCount() > 0) {
            this.mNeedSync = true;
            this.mSyncHeight = this.mLayoutHeight;
            int i = this.mSelectedPosition;
            if (i >= 0) {
                View childAt = getChildAt(i - this.mFirstPosition);
                this.mSyncRowId = this.mNextSelectedRowId;
                this.mSyncPosition = this.mNextSelectedPosition;
                if (childAt != null) {
                    this.mSpecificTop = childAt.getTop();
                }
                this.mSyncMode = 0;
                return;
            }
            View childAt2 = getChildAt(0);
            Adapter adapter = getAdapter();
            int i2 = this.mFirstPosition;
            if (i2 >= 0 && i2 < adapter.getCount()) {
                this.mSyncRowId = adapter.getItemId(this.mFirstPosition);
            } else {
                this.mSyncRowId = -1L;
            }
            this.mSyncPosition = this.mFirstPosition;
            if (childAt2 != null) {
                this.mSpecificTop = childAt2.getTop();
            }
            this.mSyncMode = 1;
        }
    }

    void rememberSyncStateHorizontal() {
        if (getChildCount() > 0) {
            this.mNeedSync = true;
            this.mSyncHeight = this.mLayoutHeight;
            int i = this.mSelectedPosition;
            if (i >= 0) {
                View childAt = getChildAt(i - this.mFirstPosition);
                this.mSyncRowId = this.mNextSelectedRowId;
                this.mSyncPosition = this.mNextSelectedPosition;
                if (childAt != null) {
                    if (isLayoutRtl()) {
                        this.mSpecificTop = childAt.getRight();
                    } else {
                        this.mSpecificTop = childAt.getLeft();
                    }
                }
                this.mSyncMode = 0;
                return;
            }
            View childAt2 = getChildAt(0);
            Adapter adapter = getAdapter();
            int i2 = this.mFirstPosition;
            if (i2 >= 0 && i2 < adapter.getCount()) {
                this.mSyncRowId = adapter.getItemId(this.mFirstPosition);
            } else {
                this.mSyncRowId = -1L;
            }
            this.mSyncPosition = this.mFirstPosition;
            if (childAt2 != null) {
                if (isLayoutRtl()) {
                    this.mSpecificTop = childAt2.getRight();
                } else {
                    this.mSpecificTop = childAt2.getLeft();
                }
            }
            this.mSyncMode = 1;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws Resources.NotFoundException, IOException {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("scrolling:firstPosition", this.mFirstPosition);
        viewHierarchyEncoder.addProperty("list:nextSelectedPosition", this.mNextSelectedPosition);
        viewHierarchyEncoder.addProperty("list:nextSelectedRowId", this.mNextSelectedRowId);
        viewHierarchyEncoder.addProperty("list:selectedPosition", this.mSelectedPosition);
        viewHierarchyEncoder.addProperty("list:itemCount", this.mItemCount);
    }

    @Override // android.view.View
    public void onProvideAutofillStructure(ViewStructure viewStructure, int i) {
        super.onProvideAutofillStructure(viewStructure, i);
    }

    @Override // android.view.View
    protected void onProvideStructure(ViewStructure viewStructure, int i, int i2) {
        Adapter adapter;
        CharSequence[] autofillOptions;
        super.onProvideStructure(viewStructure, i, i2);
        if ((i != 1 && i != 2) || (adapter = getAdapter()) == null || (autofillOptions = adapter.getAutofillOptions()) == null) {
            return;
        }
        viewStructure.setAutofillOptions(autofillOptions);
    }
}
