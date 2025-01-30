package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.leanback.R$styleable;
import androidx.leanback.widget.BaseGridView;
import androidx.leanback.widget.GridLayoutManager;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Picker extends FrameLayout {
    public final int mAlphaAnimDuration;
    public final C02971 mColumnChangeListener;
    public final List mColumnViews;
    public ArrayList mColumns;
    public final Interpolator mDecelerateInterpolator;
    public final float mFocusedAlpha;
    public final int mPickerItemLayoutId;
    public final int mPickerItemTextViewId;
    public final ViewGroup mPickerView;
    public int mSelectedColumn;
    public final List mSeparators;
    public final float mUnfocusedAlpha;
    public final float mVisibleColumnAlpha;
    public final float mVisibleItemsActivated;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PickerScrollArrayAdapter extends RecyclerView.Adapter {
        public final int mColIndex;
        public final PickerColumn mData;
        public final int mResource;
        public final int mTextViewResourceId;

        public PickerScrollArrayAdapter(int i, int i2, int i3) {
            this.mResource = i;
            this.mColIndex = i3;
            this.mTextViewResourceId = i2;
            this.mData = (PickerColumn) Picker.this.mColumns.get(i3);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            PickerColumn pickerColumn = this.mData;
            if (pickerColumn == null) {
                return 0;
            }
            return (pickerColumn.mMaxValue - pickerColumn.mMinValue) + 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            PickerColumn pickerColumn;
            ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            TextView textView = viewHolder2.textView;
            if (textView != null && (pickerColumn = this.mData) != null) {
                int i2 = pickerColumn.mMinValue + i;
                CharSequence[] charSequenceArr = pickerColumn.mStaticLabels;
                textView.setText(charSequenceArr == null ? String.format(pickerColumn.mLabelFormat, Integer.valueOf(i2)) : charSequenceArr[i2]);
            }
            Picker picker = Picker.this;
            ArrayList arrayList = (ArrayList) picker.mColumnViews;
            int i3 = this.mColIndex;
            picker.setOrAnimateAlpha(viewHolder2.itemView, ((VerticalGridView) arrayList.get(i3)).mLayoutManager.mFocusPosition == i, i3, false);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            View inflate = LayoutInflater.from(recyclerView.getContext()).inflate(this.mResource, (ViewGroup) recyclerView, false);
            int i2 = this.mTextViewResourceId;
            return new ViewHolder(inflate, i2 != 0 ? (TextView) inflate.findViewById(i2) : (TextView) inflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
            ((ViewHolder) viewHolder).itemView.setFocusable(Picker.this.isActivated());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public ViewHolder(View view, TextView textView) {
            super(view);
            this.textView = textView;
        }
    }

    public Picker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.pickerStyle);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!isActivated()) {
            return super.dispatchKeyEvent(keyEvent);
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode != 23 && keyCode != 66) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (keyEvent.getAction() == 1) {
            performClick();
        }
        return true;
    }

    public final int getColumnsCount() {
        ArrayList arrayList = this.mColumns;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public void onColumnValueChanged(int i, int i2) {
        PickerColumn pickerColumn = (PickerColumn) this.mColumns.get(i);
        if (pickerColumn.mCurrentValue != i2) {
            pickerColumn.mCurrentValue = i2;
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        int i2 = this.mSelectedColumn;
        if (i2 < 0 || i2 >= ((ArrayList) this.mColumnViews).size()) {
            return false;
        }
        return ((VerticalGridView) ((ArrayList) this.mColumnViews).get(i2)).requestFocus(i, rect);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        for (int i = 0; i < ((ArrayList) this.mColumnViews).size(); i++) {
            if (((VerticalGridView) ((ArrayList) this.mColumnViews).get(i)).hasFocus()) {
                setSelectedColumn(i);
            }
        }
    }

    @Override // android.view.View
    public final void setActivated(boolean z) {
        if (z == isActivated()) {
            super.setActivated(z);
            return;
        }
        super.setActivated(z);
        boolean hasFocus = hasFocus();
        int i = this.mSelectedColumn;
        setDescendantFocusability(131072);
        if (!z && hasFocus && isFocusable()) {
            requestFocus();
        }
        for (int i2 = 0; i2 < getColumnsCount(); i2++) {
            ((VerticalGridView) ((ArrayList) this.mColumnViews).get(i2)).setFocusable(z);
        }
        for (int i3 = 0; i3 < getColumnsCount(); i3++) {
            updateColumnSize((VerticalGridView) ((ArrayList) this.mColumnViews).get(i3));
        }
        boolean isActivated = isActivated();
        for (int i4 = 0; i4 < getColumnsCount(); i4++) {
            VerticalGridView verticalGridView = (VerticalGridView) ((ArrayList) this.mColumnViews).get(i4);
            for (int i5 = 0; i5 < verticalGridView.getChildCount(); i5++) {
                verticalGridView.getChildAt(i5).setFocusable(isActivated);
            }
        }
        if (z && hasFocus && i >= 0) {
            ((VerticalGridView) ((ArrayList) this.mColumnViews).get(i)).requestFocus();
        }
        setDescendantFocusability(262144);
    }

    public final void setColumnAt(int i, PickerColumn pickerColumn) {
        this.mColumns.set(i, pickerColumn);
        VerticalGridView verticalGridView = (VerticalGridView) ((ArrayList) this.mColumnViews).get(i);
        PickerScrollArrayAdapter pickerScrollArrayAdapter = (PickerScrollArrayAdapter) verticalGridView.mAdapter;
        if (pickerScrollArrayAdapter != null) {
            pickerScrollArrayAdapter.notifyDataSetChanged();
        }
        verticalGridView.mLayoutManager.setSelection$1(pickerColumn.mCurrentValue - pickerColumn.mMinValue, false);
    }

    public final void setColumnValue(int i, int i2, boolean z) {
        PickerColumn pickerColumn = (PickerColumn) this.mColumns.get(i);
        if (pickerColumn.mCurrentValue != i2) {
            pickerColumn.mCurrentValue = i2;
            VerticalGridView verticalGridView = (VerticalGridView) ((ArrayList) this.mColumnViews).get(i);
            if (verticalGridView != null) {
                int i3 = i2 - ((PickerColumn) this.mColumns.get(i)).mMinValue;
                if (z) {
                    verticalGridView.mLayoutManager.setSelection$1(i3, true);
                } else {
                    verticalGridView.mLayoutManager.setSelection$1(i3, false);
                }
            }
        }
    }

    public final void setColumns(List list) {
        if (((ArrayList) this.mSeparators).size() == 0) {
            throw new IllegalStateException("Separators size is: " + ((ArrayList) this.mSeparators).size() + ". At least one separator must be provided");
        }
        if (((ArrayList) this.mSeparators).size() == 1) {
            CharSequence charSequence = (CharSequence) ((ArrayList) this.mSeparators).get(0);
            ((ArrayList) this.mSeparators).clear();
            ((ArrayList) this.mSeparators).add("");
            for (int i = 0; i < ((ArrayList) list).size() - 1; i++) {
                ((ArrayList) this.mSeparators).add(charSequence);
            }
            ((ArrayList) this.mSeparators).add("");
        } else {
            ArrayList arrayList = (ArrayList) list;
            if (((ArrayList) this.mSeparators).size() != arrayList.size() + 1) {
                throw new IllegalStateException("Separators size: " + ((ArrayList) this.mSeparators).size() + " mustequal the size of columns: " + arrayList.size() + " + 1");
            }
        }
        ((ArrayList) this.mColumnViews).clear();
        this.mPickerView.removeAllViews();
        ArrayList arrayList2 = new ArrayList(list);
        this.mColumns = arrayList2;
        if (this.mSelectedColumn > arrayList2.size() - 1) {
            this.mSelectedColumn = this.mColumns.size() - 1;
        }
        LayoutInflater from = LayoutInflater.from(getContext());
        int columnsCount = getColumnsCount();
        if (!TextUtils.isEmpty((CharSequence) ((ArrayList) this.mSeparators).get(0))) {
            TextView textView = (TextView) from.inflate(R.layout.lb_picker_separator, this.mPickerView, false);
            textView.setText((CharSequence) ((ArrayList) this.mSeparators).get(0));
            this.mPickerView.addView(textView);
        }
        int i2 = 0;
        while (i2 < columnsCount) {
            VerticalGridView verticalGridView = (VerticalGridView) from.inflate(R.layout.lb_picker_column, this.mPickerView, false);
            updateColumnSize(verticalGridView);
            verticalGridView.mLayoutManager.mWindowAlignment.mMainAxis.mWindowAlignment = 0;
            verticalGridView.requestLayout();
            verticalGridView.mHasFixedSize = false;
            verticalGridView.setFocusable(isActivated());
            RecyclerView.Recycler recycler = verticalGridView.mRecycler;
            recycler.mRequestedCacheMax = 0;
            recycler.updateViewCacheSize();
            ((ArrayList) this.mColumnViews).add(verticalGridView);
            this.mPickerView.addView(verticalGridView);
            int i3 = i2 + 1;
            if (!TextUtils.isEmpty((CharSequence) ((ArrayList) this.mSeparators).get(i3))) {
                TextView textView2 = (TextView) from.inflate(R.layout.lb_picker_separator, this.mPickerView, false);
                textView2.setText((CharSequence) ((ArrayList) this.mSeparators).get(i3));
                this.mPickerView.addView(textView2);
            }
            verticalGridView.setAdapter(new PickerScrollArrayAdapter(this.mPickerItemLayoutId, this.mPickerItemTextViewId, i2));
            C02971 c02971 = this.mColumnChangeListener;
            GridLayoutManager gridLayoutManager = verticalGridView.mLayoutManager;
            if (c02971 == null) {
                gridLayoutManager.mChildViewHolderSelectedListeners = null;
            } else {
                ArrayList arrayList3 = gridLayoutManager.mChildViewHolderSelectedListeners;
                if (arrayList3 == null) {
                    gridLayoutManager.mChildViewHolderSelectedListeners = new ArrayList();
                } else {
                    arrayList3.clear();
                }
                gridLayoutManager.mChildViewHolderSelectedListeners.add(c02971);
            }
            i2 = i3;
        }
    }

    public final void setOrAnimateAlpha(View view, boolean z, int i, boolean z2) {
        boolean z3 = i == this.mSelectedColumn || !hasFocus();
        if (z) {
            if (z3) {
                setOrAnimateAlpha(view, z2, this.mFocusedAlpha, this.mDecelerateInterpolator);
                return;
            } else {
                setOrAnimateAlpha(view, z2, this.mUnfocusedAlpha, this.mDecelerateInterpolator);
                return;
            }
        }
        if (z3) {
            setOrAnimateAlpha(view, z2, this.mVisibleColumnAlpha, this.mDecelerateInterpolator);
        } else {
            setOrAnimateAlpha(view, z2, 0.0f, this.mDecelerateInterpolator);
        }
    }

    public final void setSelectedColumn(int i) {
        if (this.mSelectedColumn != i) {
            this.mSelectedColumn = i;
            for (int i2 = 0; i2 < ((ArrayList) this.mColumnViews).size(); i2++) {
                updateColumnAlpha(i2);
            }
        }
        VerticalGridView verticalGridView = (VerticalGridView) ((ArrayList) this.mColumnViews).get(i);
        if (!hasFocus() || verticalGridView.hasFocus()) {
            return;
        }
        verticalGridView.requestFocus();
    }

    public final void updateColumnAlpha(int i) {
        VerticalGridView verticalGridView = (VerticalGridView) ((ArrayList) this.mColumnViews).get(i);
        int i2 = verticalGridView.mLayoutManager.mFocusPosition;
        int i3 = 0;
        while (i3 < verticalGridView.mAdapter.getItemCount()) {
            View findViewByPosition = verticalGridView.getLayoutManager$1().findViewByPosition(i3);
            if (findViewByPosition != null) {
                setOrAnimateAlpha(findViewByPosition, i2 == i3, i, true);
            }
            i3++;
        }
    }

    public final void updateColumnSize(VerticalGridView verticalGridView) {
        ViewGroup.LayoutParams layoutParams = verticalGridView.getLayoutParams();
        float f = isActivated() ? this.mVisibleItemsActivated : 1.0f;
        layoutParams.height = (int) DependencyGraph$$ExternalSyntheticOutline0.m20m(f, 1.0f, verticalGridView.mLayoutManager.mVerticalSpacing, getContext().getResources().getDimensionPixelSize(R.dimen.picker_item_height) * f);
        verticalGridView.setLayoutParams(layoutParams);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.leanback.widget.picker.Picker$1] */
    public Picker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColumnViews = new ArrayList();
        this.mVisibleItemsActivated = 3.0f;
        this.mSelectedColumn = 0;
        this.mSeparators = new ArrayList();
        this.mColumnChangeListener = new OnChildViewHolderSelectedListener() { // from class: androidx.leanback.widget.picker.Picker.1
            @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
            public final void onChildViewHolderSelected(BaseGridView baseGridView, RecyclerView.ViewHolder viewHolder, int i2) {
                Picker picker = Picker.this;
                int indexOf = ((ArrayList) picker.mColumnViews).indexOf((VerticalGridView) baseGridView);
                picker.updateColumnAlpha(indexOf);
                if (viewHolder != null) {
                    picker.onColumnValueChanged(indexOf, ((PickerColumn) picker.mColumns.get(indexOf)).mMinValue + i2);
                }
            }
        };
        int[] iArr = R$styleable.lbPicker;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, i, 0);
        this.mPickerItemLayoutId = obtainStyledAttributes.getResourceId(0, R.layout.lb_picker_item);
        this.mPickerItemTextViewId = obtainStyledAttributes.getResourceId(1, 0);
        obtainStyledAttributes.recycle();
        setEnabled(true);
        setDescendantFocusability(262144);
        this.mFocusedAlpha = 1.0f;
        this.mUnfocusedAlpha = 1.0f;
        this.mVisibleColumnAlpha = 0.5f;
        this.mAlphaAnimDuration = 200;
        this.mDecelerateInterpolator = new DecelerateInterpolator(2.5f);
        this.mPickerView = (ViewGroup) ((ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.lb_picker, (ViewGroup) this, true)).findViewById(R.id.picker);
    }

    public final void setOrAnimateAlpha(View view, boolean z, float f, Interpolator interpolator) {
        view.animate().cancel();
        if (!z) {
            view.setAlpha(f);
        } else {
            view.animate().alpha(f).setDuration(this.mAlphaAnimDuration).setInterpolator(interpolator).start();
        }
    }
}
