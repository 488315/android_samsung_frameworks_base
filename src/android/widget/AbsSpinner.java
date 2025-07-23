package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.AdapterView;
import com.android.internal.R;

/* loaded from: classes5.dex */
public abstract class AbsSpinner extends AdapterView<SpinnerAdapter> {
    private static final String LOG_TAG = "AbsSpinner";
    SpinnerAdapter mAdapter;
    private DataSetObserver mDataSetObserver;
    int mHeightMeasureSpec;
    final RecycleBin mRecycler;
    int mSelectionBottomPadding;
    int mSelectionLeftPadding;
    int mSelectionRightPadding;
    int mSelectionTopPadding;
    final Rect mSpinnerPadding;
    private Rect mTouchFrame;
    int mWidthMeasureSpec;

    abstract void layout(int i, boolean z);

    public AbsSpinner(Context context) {
        super(context);
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mSpinnerPadding = new Rect();
        this.mRecycler = new RecycleBin();
        initAbsSpinner();
    }

    public AbsSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbsSpinner(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AbsSpinner(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mSpinnerPadding = new Rect();
        this.mRecycler = new RecycleBin();
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        initAbsSpinner();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AbsSpinner, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.AbsSpinner, attributeSet, obtainStyledAttributes, i, i2);
        CharSequence[] textArray = obtainStyledAttributes.getTextArray(0);
        if (textArray != null) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(context, 17367048, textArray);
            arrayAdapter.setDropDownViewResource(17367049);
            setAdapter((SpinnerAdapter) arrayAdapter);
        }
        obtainStyledAttributes.recycle();
    }

    private void initAbsSpinner() {
        setFocusable(true);
        setWillNotDraw(false);
    }

    @Override // android.widget.AdapterView
    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        SpinnerAdapter spinnerAdapter2 = this.mAdapter;
        if (spinnerAdapter2 != null) {
            spinnerAdapter2.unregisterDataSetObserver(this.mDataSetObserver);
            resetList();
        }
        this.mAdapter = spinnerAdapter;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            checkFocus();
            AdapterView.AdapterDataSetObserver adapterDataSetObserver = new AdapterView.AdapterDataSetObserver();
            this.mDataSetObserver = adapterDataSetObserver;
            this.mAdapter.registerDataSetObserver(adapterDataSetObserver);
            int i = this.mItemCount > 0 ? 0 : -1;
            setSelectedPositionInt(i);
            setNextSelectedPositionInt(i);
            if (this.mItemCount == 0) {
                checkSelectionChanged();
            }
        } else {
            checkFocus();
            resetList();
            checkSelectionChanged();
        }
        requestLayout();
    }

    void resetList() {
        this.mDataChanged = false;
        this.mNeedSync = false;
        removeAllViewsInLayout();
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        setSelectedPositionInt(-1);
        setNextSelectedPositionInt(-1);
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00a3  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onMeasure(int r7, int r8) {
        /*
            r6 = this;
            int r0 = android.view.View.MeasureSpec.getMode(r7)
            android.graphics.Rect r1 = r6.mSpinnerPadding
            int r2 = r6.mPaddingLeft
            int r3 = r6.mSelectionLeftPadding
            if (r2 <= r3) goto Le
            int r3 = r6.mPaddingLeft
        Le:
            r1.left = r3
            android.graphics.Rect r1 = r6.mSpinnerPadding
            int r2 = r6.mPaddingTop
            int r3 = r6.mSelectionTopPadding
            if (r2 <= r3) goto L1a
            int r3 = r6.mPaddingTop
        L1a:
            r1.top = r3
            android.graphics.Rect r1 = r6.mSpinnerPadding
            int r2 = r6.mPaddingRight
            int r3 = r6.mSelectionRightPadding
            if (r2 <= r3) goto L26
            int r3 = r6.mPaddingRight
        L26:
            r1.right = r3
            android.graphics.Rect r1 = r6.mSpinnerPadding
            int r2 = r6.mPaddingBottom
            int r3 = r6.mSelectionBottomPadding
            if (r2 <= r3) goto L32
            int r3 = r6.mPaddingBottom
        L32:
            r1.bottom = r3
            boolean r1 = r6.mDataChanged
            if (r1 == 0) goto L3b
            r6.handleDataChanged()
        L3b:
            int r1 = r6.getSelectedItemPosition()
            r2 = 1
            r3 = 0
            if (r1 < 0) goto L9f
            android.widget.SpinnerAdapter r4 = r6.mAdapter
            if (r4 == 0) goto L9f
            int r4 = r4.getCount()
            if (r1 >= r4) goto L9f
            android.widget.AbsSpinner$RecycleBin r4 = r6.mRecycler
            android.view.View r4 = r4.get(r1)
            if (r4 != 0) goto L65
            android.widget.SpinnerAdapter r4 = r6.mAdapter
            r5 = 0
            android.view.View r4 = r4.getView(r1, r5, r6)
            int r5 = r4.getImportantForAccessibility()
            if (r5 != 0) goto L65
            r4.setImportantForAccessibility(r2)
        L65:
            if (r4 == 0) goto L9f
            android.widget.AbsSpinner$RecycleBin r5 = r6.mRecycler
            r5.put(r1, r4)
            android.view.ViewGroup$LayoutParams r1 = r4.getLayoutParams()
            if (r1 != 0) goto L7d
            r6.mBlockLayoutRequests = r2
            android.view.ViewGroup$LayoutParams r1 = r6.generateDefaultLayoutParams()
            r4.setLayoutParams(r1)
            r6.mBlockLayoutRequests = r3
        L7d:
            r6.measureChild(r4, r7, r8)
            int r1 = r6.getChildHeight(r4)
            android.graphics.Rect r2 = r6.mSpinnerPadding
            int r2 = r2.top
            int r1 = r1 + r2
            android.graphics.Rect r2 = r6.mSpinnerPadding
            int r2 = r2.bottom
            int r1 = r1 + r2
            int r2 = r6.getChildWidth(r4)
            android.graphics.Rect r4 = r6.mSpinnerPadding
            int r4 = r4.left
            int r2 = r2 + r4
            android.graphics.Rect r4 = r6.mSpinnerPadding
            int r4 = r4.right
            int r2 = r2 + r4
            r4 = r2
            r2 = r3
            goto La1
        L9f:
            r1 = r3
            r4 = r1
        La1:
            if (r2 == 0) goto Lb8
            android.graphics.Rect r1 = r6.mSpinnerPadding
            int r1 = r1.top
            android.graphics.Rect r2 = r6.mSpinnerPadding
            int r2 = r2.bottom
            int r1 = r1 + r2
            if (r0 != 0) goto Lb8
            android.graphics.Rect r0 = r6.mSpinnerPadding
            int r0 = r0.left
            android.graphics.Rect r2 = r6.mSpinnerPadding
            int r2 = r2.right
            int r4 = r0 + r2
        Lb8:
            int r0 = r6.getSuggestedMinimumHeight()
            int r0 = java.lang.Math.max(r1, r0)
            int r1 = r6.getSuggestedMinimumWidth()
            int r1 = java.lang.Math.max(r4, r1)
            int r0 = resolveSizeAndState(r0, r8, r3)
            int r1 = resolveSizeAndState(r1, r7, r3)
            r6.setMeasuredDimension(r1, r0)
            r6.mHeightMeasureSpec = r8
            r6.mWidthMeasureSpec = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.AbsSpinner.onMeasure(int, int):void");
    }

    int getChildHeight(View view) {
        return view.getMeasuredHeight();
    }

    int getChildWidth(View view) {
        return view.getMeasuredWidth();
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.LayoutParams(-1, -2);
    }

    void recycleAllViews() {
        int childCount = getChildCount();
        RecycleBin recycleBin = this.mRecycler;
        int i = this.mFirstPosition;
        for (int i2 = 0; i2 < childCount; i2++) {
            recycleBin.put(i + i2, getChildAt(i2));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000f, code lost:
    
        if (r2 <= ((r1.mFirstPosition + getChildCount()) - 1)) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setSelection(int r2, boolean r3) {
        /*
            r1 = this;
            if (r3 == 0) goto L12
            int r3 = r1.mFirstPosition
            if (r3 > r2) goto L12
            int r3 = r1.mFirstPosition
            int r0 = r1.getChildCount()
            int r3 = r3 + r0
            r0 = 1
            int r3 = r3 - r0
            if (r2 > r3) goto L12
            goto L13
        L12:
            r0 = 0
        L13:
            r1.setSelectionInt(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.AbsSpinner.setSelection(int, boolean):void");
    }

    @Override // android.widget.AdapterView
    public void setSelection(int i) {
        setNextSelectedPositionInt(i);
        requestLayout();
        invalidate();
    }

    void setSelectionInt(int i, boolean z) {
        if (i != this.mOldSelectedPosition) {
            this.mBlockLayoutRequests = true;
            int i2 = i - this.mSelectedPosition;
            setNextSelectedPositionInt(i);
            layout(i2, z);
            this.mBlockLayoutRequests = false;
        }
    }

    @Override // android.widget.AdapterView
    public View getSelectedView() {
        if (this.mItemCount <= 0 || this.mSelectedPosition < 0) {
            return null;
        }
        return getChildAt(this.mSelectedPosition - this.mFirstPosition);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mBlockLayoutRequests) {
            return;
        }
        super.requestLayout();
    }

    @Override // android.widget.AdapterView
    public SpinnerAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.widget.AdapterView
    public int getCount() {
        return this.mItemCount;
    }

    public int pointToPosition(int i, int i2) {
        Rect rect = this.mTouchFrame;
        if (rect == null) {
            rect = new Rect();
            this.mTouchFrame = rect;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt.getVisibility() == 0) {
                childAt.getHitRect(rect);
                if (rect.contains(i, i2)) {
                    return this.mFirstPosition + childCount;
                }
            }
        }
        return -1;
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        super.dispatchRestoreInstanceState(sparseArray);
        handleDataChanged();
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.AbsSpinner.SavedState.1
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
        int position;
        long selectedId;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        SavedState(Parcel parcel) {
            super(parcel);
            this.selectedId = parcel.readLong();
            this.position = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeLong(this.selectedId);
            parcel.writeInt(this.position);
        }

        public String toString() {
            return "AbsSpinner.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " position=" + this.position + "}";
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.selectedId = getSelectedItemId();
        if (savedState.selectedId >= 0) {
            savedState.position = getSelectedItemPosition();
            return savedState;
        }
        savedState.position = -1;
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.selectedId >= 0) {
            this.mDataChanged = true;
            this.mNeedSync = true;
            this.mSyncRowId = savedState.selectedId;
            this.mSyncPosition = savedState.position;
            this.mSyncMode = 0;
            requestLayout();
        }
    }

    class RecycleBin {
        private final SparseArray<View> mScrapHeap = new SparseArray<>();

        RecycleBin() {
        }

        public void put(int i, View view) {
            this.mScrapHeap.put(i, view);
        }

        View get(int i) {
            View view = this.mScrapHeap.get(i);
            if (view != null) {
                this.mScrapHeap.delete(i);
            }
            return view;
        }

        void clear() {
            SparseArray<View> sparseArray = this.mScrapHeap;
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                View valueAt = sparseArray.valueAt(i);
                if (valueAt != null) {
                    AbsSpinner.this.removeDetachedView(valueAt, true);
                }
            }
            sparseArray.clear();
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return AbsSpinner.class.getName();
    }

    @Override // android.view.View
    public void autofill(AutofillValue autofillValue) {
        if (isEnabled()) {
            if (!autofillValue.isList()) {
                Log.w(LOG_TAG, autofillValue + " could not be autofilled into " + this);
                return;
            }
            setSelection(autofillValue.getListValue());
        }
    }

    @Override // android.view.View
    public int getAutofillType() {
        return isEnabled() ? 3 : 0;
    }

    @Override // android.view.View
    public AutofillValue getAutofillValue() {
        if (isEnabled()) {
            return AutofillValue.forList(getSelectedItemPosition());
        }
        return null;
    }
}
