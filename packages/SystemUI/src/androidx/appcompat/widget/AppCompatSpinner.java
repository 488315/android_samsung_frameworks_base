package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AppCompatSpinner extends Spinner {
    public static final int[] ATTRS_ANDROID_SPINNERMODE = {R.attr.spinnerMode};
    public boolean isNeedToSkipRefreshDrawable;
    public final AppCompatBackgroundHelper mBackgroundTintHelper;
    public final int mDropDownHorizontalOffset;
    public int mDropDownWidth;
    public final AnonymousClass1 mForwardingListener;
    public AnonymousClass2 mOnGlobalLayoutListenerFromRestore;
    public DropdownPopup.AnonymousClass2 mOnGlobalLayoutListenerWhenShowing;
    public final SpinnerPopup mPopup;
    public final Context mPopupContext;
    public final boolean mPopupSet;
    public SpinnerAdapter mTempAdapter;
    public final Rect mTempRect;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DialogPopup implements SpinnerPopup, DialogInterface.OnClickListener {
        public DropDownAdapter mListAdapter;
        public AlertDialog mPopup;
        public CharSequence mPrompt;

        public DialogPopup() {
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void dismiss() {
            AlertDialog alertDialog = this.mPopup;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.mPopup = null;
            }
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final Drawable getBackground() {
            return null;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final CharSequence getHintText() {
            return this.mPrompt;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final int getHorizontalOffset() {
            return 0;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final int getVerticalOffset() {
            return 0;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final boolean isShowing() {
            AlertDialog alertDialog = this.mPopup;
            if (alertDialog != null) {
                return alertDialog.isShowing();
            }
            return false;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            AppCompatSpinner.this.setSelection(i);
            if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                AppCompatSpinner.this.performItemClick(null, i, this.mListAdapter.getItemId(i));
            }
            dismiss();
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void setAdapter(ListAdapter listAdapter) {
            this.mListAdapter = (DropDownAdapter) listAdapter;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void setBackgroundDrawable(Drawable drawable) {
            Log.e("AppCompatSpinner", "Cannot set popup background for MODE_DIALOG, ignoring");
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void setHorizontalOffset(int i) {
            Log.e("AppCompatSpinner", "Cannot set horizontal offset for MODE_DIALOG, ignoring");
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void setHorizontalOriginalOffset(int i) {
            Log.e("AppCompatSpinner", "Cannot set horizontal (original) offset for MODE_DIALOG, ignoring");
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void setPromptText(CharSequence charSequence) {
            this.mPrompt = charSequence;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void setVerticalOffset(int i) {
            Log.e("AppCompatSpinner", "Cannot set vertical offset for MODE_DIALOG, ignoring");
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void show(int i, int i2) {
            if (this.mListAdapter == null) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(AppCompatSpinner.this.mPopupContext);
            CharSequence charSequence = this.mPrompt;
            AlertController.AlertParams alertParams = builder.P;
            if (charSequence != null) {
                alertParams.mTitle = charSequence;
            }
            DropDownAdapter dropDownAdapter = this.mListAdapter;
            int selectedItemPosition = AppCompatSpinner.this.getSelectedItemPosition();
            alertParams.mAdapter = dropDownAdapter;
            alertParams.mOnClickListener = this;
            alertParams.mCheckedItem = selectedItemPosition;
            alertParams.mIsSingleChoice = true;
            AlertDialog create = builder.create();
            this.mPopup = create;
            AlertController.RecycleListView recycleListView = create.mAlert.mListView;
            recycleListView.setTextDirection(i);
            recycleListView.setTextAlignment(i2);
            this.mPopup.show();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DropDownAdapter implements ListAdapter, SpinnerAdapter {
        public final SpinnerAdapter mAdapter;
        public final ListAdapter mListAdapter;

        public DropDownAdapter(SpinnerAdapter spinnerAdapter, Resources.Theme theme) {
            this.mAdapter = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter) spinnerAdapter;
            }
            if (theme == null || !(spinnerAdapter instanceof ThemedSpinnerAdapter)) {
                return;
            }
            ThemedSpinnerAdapter themedSpinnerAdapter = (ThemedSpinnerAdapter) spinnerAdapter;
            if (Objects.equals(themedSpinnerAdapter.getDropDownViewTheme(), theme)) {
                return;
            }
            themedSpinnerAdapter.setDropDownViewTheme(theme);
        }

        @Override // android.widget.ListAdapter
        public final boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return 0;
            }
            return spinnerAdapter.getCount();
        }

        @Override // android.widget.SpinnerAdapter
        public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getDropDownView(i, view, viewGroup);
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getItem(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return -1L;
            }
            return spinnerAdapter.getItemId(i);
        }

        @Override // android.widget.Adapter
        public final int getItemViewType(int i) {
            return 0;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            return getDropDownView(i, view, viewGroup);
        }

        @Override // android.widget.Adapter
        public final int getViewTypeCount() {
            return 1;
        }

        @Override // android.widget.Adapter
        public final boolean hasStableIds() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            return spinnerAdapter != null && spinnerAdapter.hasStableIds();
        }

        @Override // android.widget.Adapter
        public final boolean isEmpty() {
            return getCount() == 0;
        }

        @Override // android.widget.ListAdapter
        public final boolean isEnabled(int i) {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.isEnabled(i);
            }
            return true;
        }

        @Override // android.widget.Adapter
        public final void registerDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        @Override // android.widget.Adapter
        public final void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DropdownPopup extends ListPopupWindow implements SpinnerPopup {
        public ListAdapter mAdapter;
        public CharSequence mHintText;
        public int mOriginalHorizontalOffset;

        public DropdownPopup(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            new Rect();
            AppCompatSpinner.this.getClass();
            this.mDropDownGravity = 0;
            this.mDropDownAnchorView = AppCompatSpinner.this;
            this.mModal = true;
            this.mPopup.setFocusable(true);
            this.mItemClickListener = new AdapterView.OnItemClickListener(AppCompatSpinner.this) { // from class: androidx.appcompat.widget.AppCompatSpinner.DropdownPopup.1
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView adapterView, View view, int i2, long j) {
                    AppCompatSpinner.this.setSelection(i2);
                    if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                        DropdownPopup dropdownPopup = DropdownPopup.this;
                        AppCompatSpinner.this.performItemClick(view, i2, dropdownPopup.mAdapter.getItemId(i2));
                    }
                    DropdownPopup.this.dismiss();
                }
            };
        }

        public final void computeContentWidth() {
            int i;
            AppCompatPopupWindow appCompatPopupWindow = this.mPopup;
            Drawable background = appCompatPopupWindow.getBackground();
            AppCompatSpinner appCompatSpinner = AppCompatSpinner.this;
            if (background != null) {
                background.getPadding(appCompatSpinner.mTempRect);
                i = appCompatSpinner.getLayoutDirection() == 1 ? appCompatSpinner.mTempRect.right : -appCompatSpinner.mTempRect.left;
            } else {
                Rect rect = appCompatSpinner.mTempRect;
                rect.right = 0;
                rect.left = 0;
                i = 0;
            }
            int paddingLeft = appCompatSpinner.getPaddingLeft();
            int paddingRight = appCompatSpinner.getPaddingRight();
            int width = appCompatSpinner.getWidth();
            int i2 = appCompatSpinner.mDropDownWidth;
            if (i2 == -2) {
                int compatMeasureContentWidth = appCompatSpinner.compatMeasureContentWidth((SpinnerAdapter) this.mAdapter, appCompatPopupWindow.getBackground());
                int i3 = appCompatSpinner.getContext().getResources().getDisplayMetrics().widthPixels;
                Rect rect2 = appCompatSpinner.mTempRect;
                int i4 = (i3 - rect2.left) - rect2.right;
                if (compatMeasureContentWidth > i4) {
                    compatMeasureContentWidth = i4;
                }
                setContentWidth(Math.max(compatMeasureContentWidth + 4, (width - paddingLeft) - paddingRight));
            } else if (i2 == -1) {
                setContentWidth((width - paddingLeft) - paddingRight);
            } else {
                setContentWidth(i2);
            }
            int i5 = appCompatSpinner.mDropDownHorizontalOffset;
            if (i5 == 0) {
                i5 = this.mOriginalHorizontalOffset;
            }
            this.mDropDownHorizontalOffset = appCompatSpinner.getLayoutDirection() == 1 ? (((i + width) - paddingRight) - this.mDropDownWidth) - i5 : i + paddingLeft + i5;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final CharSequence getHintText() {
            return this.mHintText;
        }

        @Override // androidx.appcompat.widget.ListPopupWindow, androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void setAdapter(ListAdapter listAdapter) {
            super.setAdapter(listAdapter);
            this.mAdapter = listAdapter;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void setHorizontalOriginalOffset(int i) {
            this.mOriginalHorizontalOffset = i;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void setPromptText(CharSequence charSequence) {
            this.mHintText = charSequence;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3, types: [android.view.ViewTreeObserver$OnGlobalLayoutListener, androidx.appcompat.widget.AppCompatSpinner$DropdownPopup$2] */
        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public final void show(int i, int i2) {
            AppCompatPopupWindow appCompatPopupWindow = this.mPopup;
            boolean isShowing = appCompatPopupWindow.isShowing();
            computeContentWidth();
            this.mPopup.setInputMethodMode(2);
            show();
            DropDownListView dropDownListView = this.mDropDownList;
            dropDownListView.setTextDirection(i);
            dropDownListView.setTextAlignment(i2);
            if (isShowing) {
                return;
            }
            dropDownListView.setChoiceMode(1);
            AppCompatSpinner appCompatSpinner = AppCompatSpinner.this;
            int selectedItemPosition = appCompatSpinner.getSelectedItemPosition();
            DropDownListView dropDownListView2 = this.mDropDownList;
            if (appCompatPopupWindow.isShowing() && dropDownListView2 != null) {
                dropDownListView2.mListSelectionHidden = false;
                dropDownListView2.setSelection(selectedItemPosition);
                if (dropDownListView2.getChoiceMode() != 0) {
                    dropDownListView2.setItemChecked(selectedItemPosition, true);
                }
            }
            ViewTreeObserver viewTreeObserver = appCompatSpinner.getViewTreeObserver();
            if (viewTreeObserver == 0 || appCompatSpinner.mOnGlobalLayoutListenerWhenShowing != null) {
                return;
            }
            ?? r0 = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.widget.AppCompatSpinner.DropdownPopup.2
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    DropdownPopup.this.computeContentWidth();
                    DropdownPopup.this.show();
                }
            };
            appCompatSpinner.mOnGlobalLayoutListenerWhenShowing = r0;
            viewTreeObserver.addOnGlobalLayoutListener(r0);
            this.mPopup.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: androidx.appcompat.widget.AppCompatSpinner.DropdownPopup.3
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    ViewTreeObserver viewTreeObserver2 = AppCompatSpinner.this.getViewTreeObserver();
                    if (viewTreeObserver2 == null || !viewTreeObserver2.isAlive()) {
                        return;
                    }
                    viewTreeObserver2.removeOnGlobalLayoutListener(AppCompatSpinner.this.mOnGlobalLayoutListenerWhenShowing);
                    AppCompatSpinner.this.mOnGlobalLayoutListenerWhenShowing = null;
                }
            });
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.appcompat.widget.AppCompatSpinner.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean mShowDropdown;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeByte(this.mShowDropdown ? (byte) 1 : (byte) 0);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mShowDropdown = parcel.readByte() != 0;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SpinnerPopup {
        void dismiss();

        Drawable getBackground();

        CharSequence getHintText();

        int getHorizontalOffset();

        int getVerticalOffset();

        boolean isShowing();

        void setAdapter(ListAdapter listAdapter);

        void setBackgroundDrawable(Drawable drawable);

        void setHorizontalOffset(int i);

        void setHorizontalOriginalOffset(int i);

        void setPromptText(CharSequence charSequence);

        void setVerticalOffset(int i);

        void show(int i, int i2);
    }

    public AppCompatSpinner(Context context) {
        this(context, (AttributeSet) null);
    }

    public final int compatMeasureContentWidth(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        int i = 0;
        if (spinnerAdapter == null) {
            return 0;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int max = Math.max(0, getSelectedItemPosition());
        int min = Math.min(spinnerAdapter.getCount(), max + 15);
        View view = null;
        int i2 = 0;
        for (int max2 = Math.max(0, max - (15 - (min - max))); max2 < min; max2++) {
            int itemViewType = spinnerAdapter.getItemViewType(max2);
            if (itemViewType != i) {
                view = null;
                i = itemViewType;
            }
            view = spinnerAdapter.getView(max2, view, this);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(makeMeasureSpec, makeMeasureSpec2);
            i2 = Math.max(i2, view.getMeasuredWidth());
        }
        if (drawable == null) {
            return i2;
        }
        drawable.getPadding(this.mTempRect);
        Rect rect = this.mTempRect;
        return rect.left + rect.right + i2;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.applySupportBackgroundTint();
        }
    }

    @Override // android.widget.Spinner
    public final int getDropDownHorizontalOffset() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getHorizontalOffset() : super.getDropDownHorizontalOffset();
    }

    @Override // android.widget.Spinner
    public final int getDropDownVerticalOffset() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getVerticalOffset() : super.getDropDownVerticalOffset();
    }

    @Override // android.widget.Spinner
    public final int getDropDownWidth() {
        return this.mPopup != null ? this.mDropDownWidth : super.getDropDownWidth();
    }

    public final SpinnerPopup getInternalPopup() {
        return this.mPopup;
    }

    @Override // android.widget.Spinner
    public final Drawable getPopupBackground() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getBackground() : super.getPopupBackground();
    }

    @Override // android.widget.Spinner
    public final Context getPopupContext() {
        return this.mPopupContext;
    }

    @Override // android.widget.Spinner
    public final CharSequence getPrompt() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getHintText() : super.getPrompt();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getBackground() instanceof SeslRecoilDrawable) {
            this.isNeedToSkipRefreshDrawable = true;
        }
    }

    @Override // android.widget.Spinner, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        ViewTreeObserver viewTreeObserver;
        ViewTreeObserver viewTreeObserver2;
        super.onDetachedFromWindow();
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null && spinnerPopup.isShowing()) {
            this.mPopup.dismiss();
        }
        if (this.mOnGlobalLayoutListenerFromRestore != null && (viewTreeObserver2 = getViewTreeObserver()) != null && viewTreeObserver2.isAlive()) {
            viewTreeObserver2.removeOnGlobalLayoutListener(this.mOnGlobalLayoutListenerFromRestore);
            this.mOnGlobalLayoutListenerFromRestore = null;
        }
        if (this.mOnGlobalLayoutListenerWhenShowing == null || (viewTreeObserver = getViewTreeObserver()) == null || !viewTreeObserver.isAlive()) {
            return;
        }
        viewTreeObserver.removeOnGlobalLayoutListener(this.mOnGlobalLayoutListenerWhenShowing);
        this.mOnGlobalLayoutListenerWhenShowing = null;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        View selectedView = getSelectedView();
        StringBuilder sb = new StringBuilder();
        if (selectedView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) selectedView;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof TextView) {
                    TextView textView = (TextView) childAt;
                    if (sb.length() == 0) {
                        sb = new StringBuilder(textView.getText());
                    } else {
                        sb.append(" ");
                        sb.append(textView.getText());
                    }
                }
            }
        } else if (selectedView instanceof TextView) {
            sb = new StringBuilder(((TextView) selectedView).getText());
        }
        accessibilityNodeInfo.setContentDescription(sb.toString());
        accessibilityNodeInfo.setClassName(Spinner.class.getName());
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public final void onMeasure(int i, int i2) {
        int compatMeasureContentWidth;
        super.onMeasure(i, i2);
        if (this.mPopup == null || View.MeasureSpec.getMode(i) != Integer.MIN_VALUE) {
            return;
        }
        getMeasuredWidth();
        if (getSelectedItemPosition() <= -1 || getSelectedItemPosition() >= getAdapter().getCount()) {
            compatMeasureContentWidth = compatMeasureContentWidth(getAdapter(), getBackground());
        } else {
            SpinnerAdapter adapter = getAdapter();
            Drawable background = getBackground();
            compatMeasureContentWidth = 0;
            if (adapter != null) {
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
                View view = adapter.getView(getSelectedItemPosition(), null, this);
                if (view.getLayoutParams() == null) {
                    view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                }
                view.measure(makeMeasureSpec, makeMeasureSpec2);
                compatMeasureContentWidth = view.getMeasuredWidth();
                if (background != null) {
                    background.getPadding(this.mTempRect);
                    Rect rect = this.mTempRect;
                    compatMeasureContentWidth += rect.left + rect.right;
                }
            }
        }
        setMeasuredDimension(Math.min(compatMeasureContentWidth, View.MeasureSpec.getSize(i)), getMeasuredHeight());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.view.ViewTreeObserver$OnGlobalLayoutListener, androidx.appcompat.widget.AppCompatSpinner$2] */
    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        ViewTreeObserver viewTreeObserver;
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.mShowDropdown && (viewTreeObserver = getViewTreeObserver()) != 0 && this.mOnGlobalLayoutListenerFromRestore == null) {
            ?? r0 = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.widget.AppCompatSpinner.2
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    if (!AppCompatSpinner.this.getInternalPopup().isShowing()) {
                        AppCompatSpinner appCompatSpinner = AppCompatSpinner.this;
                        appCompatSpinner.mPopup.show(appCompatSpinner.getTextDirection(), appCompatSpinner.getTextAlignment());
                    }
                    ViewTreeObserver viewTreeObserver2 = AppCompatSpinner.this.getViewTreeObserver();
                    if (viewTreeObserver2 != null) {
                        viewTreeObserver2.removeOnGlobalLayoutListener(AppCompatSpinner.this.mOnGlobalLayoutListenerFromRestore);
                        AppCompatSpinner.this.mOnGlobalLayoutListenerFromRestore = null;
                    }
                }
            };
            this.mOnGlobalLayoutListenerFromRestore = r0;
            viewTreeObserver.addOnGlobalLayoutListener(r0);
        }
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SpinnerPopup spinnerPopup = this.mPopup;
        savedState.mShowDropdown = spinnerPopup != null && spinnerPopup.isShowing();
        return savedState;
    }

    @Override // android.widget.Spinner, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        AnonymousClass1 anonymousClass1 = this.mForwardingListener;
        if (anonymousClass1 == null || !anonymousClass1.onTouch(this, motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.widget.Spinner, android.view.View
    public final boolean performClick() {
        if (this.mPopup == null) {
            return super.performClick();
        }
        playSoundEffect(0);
        if (this.mPopup.isShowing()) {
            return true;
        }
        this.mPopup.show(getTextDirection(), getTextAlignment());
        return true;
    }

    @Override // android.view.View
    public final void refreshDrawableState() {
        super.refreshDrawableState();
        if (!this.isNeedToSkipRefreshDrawable || getStateListAnimator() == null) {
            return;
        }
        getStateListAnimator().jumpToCurrentState();
        this.isNeedToSkipRefreshDrawable = false;
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.onSetBackgroundDrawable();
        }
    }

    @Override // android.view.View
    public final void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.onSetBackgroundResource(i);
        }
    }

    @Override // android.widget.Spinner
    public final void setDropDownHorizontalOffset(int i) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup == null) {
            super.setDropDownHorizontalOffset(i);
        } else {
            spinnerPopup.setHorizontalOriginalOffset(i);
            this.mPopup.setHorizontalOffset(i);
        }
    }

    @Override // android.widget.Spinner
    public final void setDropDownVerticalOffset(int i) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setVerticalOffset(i);
        } else {
            super.setDropDownVerticalOffset(i);
        }
    }

    @Override // android.widget.Spinner
    public final void setDropDownWidth(int i) {
        if (this.mPopup != null) {
            this.mDropDownWidth = i;
        } else {
            super.setDropDownWidth(i);
        }
    }

    @Override // android.widget.Spinner
    public final void setPopupBackgroundDrawable(Drawable drawable) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setBackgroundDrawable(drawable);
        } else {
            super.setPopupBackgroundDrawable(drawable);
        }
    }

    @Override // android.widget.Spinner
    public final void setPopupBackgroundResource(int i) {
        setPopupBackgroundDrawable(AppCompatResources.getDrawable(i, this.mPopupContext));
    }

    @Override // android.widget.Spinner
    public final void setPrompt(CharSequence charSequence) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setPromptText(charSequence);
        } else {
            super.setPrompt(charSequence);
        }
    }

    public AppCompatSpinner(Context context, int i) {
        this(context, null, com.android.systemui.R.attr.spinnerStyle, i);
    }

    @Override // android.widget.AdapterView
    public final void setAdapter(SpinnerAdapter spinnerAdapter) {
        if (!this.mPopupSet) {
            this.mTempAdapter = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        if (this.mPopup != null) {
            Context context = this.mPopupContext;
            if (context == null) {
                context = getContext();
            }
            this.mPopup.setAdapter(new DropDownAdapter(spinnerAdapter, context.getTheme()));
        }
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.spinnerStyle);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, -1);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, i2, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0065, code lost:
    
        if (r13 == null) goto L31;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v14, types: [androidx.appcompat.widget.AppCompatSpinner$1] */
    /* JADX WARN: Type inference failed for: r13v15 */
    /* JADX WARN: Type inference failed for: r13v16 */
    /* JADX WARN: Type inference failed for: r13v17 */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v7, types: [android.content.res.TypedArray] */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.view.View, android.widget.Spinner, androidx.appcompat.widget.AppCompatSpinner] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AppCompatSpinner(android.content.Context r9, android.util.AttributeSet r10, int r11, int r12, android.content.res.Resources.Theme r13) {
        /*
            Method dump skipped, instructions count: 225
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatSpinner.<init>(android.content.Context, android.util.AttributeSet, int, int, android.content.res.Resources$Theme):void");
    }
}
