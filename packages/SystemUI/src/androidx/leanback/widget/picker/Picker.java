package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.leanback.R$styleable;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Picker extends FrameLayout {
    public final int mAlphaAnimDuration;
    public final AnonymousClass1 mColumnChangeListener;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PickerScrollArrayAdapter extends RecyclerView.Adapter {
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
            View view = viewHolder2.itemView;
            Picker picker = Picker.this;
            ArrayList arrayList = (ArrayList) picker.mColumnViews;
            int i3 = this.mColIndex;
            picker.setOrAnimateAlpha(view, ((VerticalGridView) arrayList.get(i3)).mLayoutManager.mFocusPosition == i, i3, false);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(this.mResource, viewGroup, false);
            int i2 = this.mTextViewResourceId;
            return new ViewHolder(inflate, i2 != 0 ? (TextView) inflate.findViewById(i2) : (TextView) inflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
            ((ViewHolder) viewHolder).itemView.setFocusable(Picker.this.isActivated());
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ViewHolder extends RecyclerView.ViewHolder {
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
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
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
                if (this.mSelectedColumn != i) {
                    this.mSelectedColumn = i;
                    for (int i2 = 0; i2 < ((ArrayList) this.mColumnViews).size(); i2++) {
                        updateColumnAlpha(i2);
                    }
                }
                VerticalGridView verticalGridView = (VerticalGridView) ((ArrayList) this.mColumnViews).get(i);
                if (hasFocus() && !verticalGridView.hasFocus()) {
                    verticalGridView.requestFocus();
                }
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
        int i2 = 0;
        while (true) {
            ArrayList arrayList = this.mColumns;
            if (i2 >= (arrayList == null ? 0 : arrayList.size())) {
                break;
            }
            ((VerticalGridView) ((ArrayList) this.mColumnViews).get(i2)).setFocusable(z);
            i2++;
        }
        int i3 = 0;
        while (true) {
            ArrayList arrayList2 = this.mColumns;
            if (i3 >= (arrayList2 == null ? 0 : arrayList2.size())) {
                break;
            }
            updateColumnSize((VerticalGridView) ((ArrayList) this.mColumnViews).get(i3));
            i3++;
        }
        boolean isActivated = isActivated();
        int i4 = 0;
        while (true) {
            ArrayList arrayList3 = this.mColumns;
            if (i4 >= (arrayList3 == null ? 0 : arrayList3.size())) {
                break;
            }
            VerticalGridView verticalGridView = (VerticalGridView) ((ArrayList) this.mColumnViews).get(i4);
            for (int i5 = 0; i5 < verticalGridView.getChildCount(); i5++) {
                verticalGridView.getChildAt(i5).setFocusable(isActivated);
            }
            i4++;
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
        verticalGridView.mLayoutManager.setSelection(pickerColumn.mCurrentValue - pickerColumn.mMinValue, false);
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

    public final void updateColumnAlpha(int i) {
        VerticalGridView verticalGridView = (VerticalGridView) ((ArrayList) this.mColumnViews).get(i);
        int i2 = verticalGridView.mLayoutManager.mFocusPosition;
        int i3 = 0;
        while (i3 < verticalGridView.mAdapter.getItemCount()) {
            View findViewByPosition = verticalGridView.getLayoutManager().findViewByPosition(i3);
            if (findViewByPosition != null) {
                setOrAnimateAlpha(findViewByPosition, i2 == i3, i, true);
            }
            i3++;
        }
    }

    public final void updateColumnSize(VerticalGridView verticalGridView) {
        ViewGroup.LayoutParams layoutParams = verticalGridView.getLayoutParams();
        float f = isActivated() ? this.mVisibleItemsActivated : 1.0f;
        layoutParams.height = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f, 1.0f, verticalGridView.mLayoutManager.mVerticalSpacing, getContext().getResources().getDimensionPixelSize(R.dimen.picker_item_height) * f);
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
            public final void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i2) {
                Picker picker = Picker.this;
                int indexOf = ((ArrayList) picker.mColumnViews).indexOf((VerticalGridView) recyclerView);
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
