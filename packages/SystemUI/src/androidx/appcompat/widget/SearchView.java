package androidx.appcompat.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.view.CollapsibleActionView;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.view.inputmethod.SeslInputMethodManagerReflector;
import androidx.reflect.widget.SeslTextViewReflector;
import com.android.systemui.R;
import com.sec.ims.volte2.data.VolteConstants;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SearchView extends LinearLayoutCompat implements CollapsibleActionView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mClearingFocus;
    public final ImageView mCloseButton;
    public final ImageView mCollapsedIcon;
    public int mCollapsedImeOptions;
    public final Context mContext;
    public final CharSequence mDefaultQueryHint;
    public final View mDropDownAnchor;
    public boolean mExpandedInActionView;
    public final Drawable mGVIVoiceIcon;
    public final ImageView mGoButton;
    public boolean mIconified;
    public final boolean mIconifiedByDefault;
    public final InputMethodManager mImm;
    public final int mMaxWidth;
    public CharSequence mOldQueryText;
    public final AnonymousClass5 mOnClickListener;
    public final AnonymousClass7 mOnEditorActionListener;
    public final AnonymousClass8 mOnItemClickListener;
    public final AnonymousClass9 mOnItemSelectedListener;
    public final CharSequence mQueryHint;
    public final AnonymousClass2 mReleaseCursorRunnable;
    public final Drawable mSVIVoiceIcon;
    public final ImageView mSearchButton;
    public final View mSearchEditFrame;
    public final View mSearchPlate;
    public final SearchAutoComplete mSearchSrcTextView;
    public final Rect mSearchSrcTextViewBounds;
    public final Rect mSearchSrtTextViewBoundsExpanded;
    public final View mSubmitArea;
    public final int[] mTemp;
    public final int[] mTemp2;
    public final AnonymousClass6 mTextKeyListener;
    public final AnonymousClass10 mTextWatcher;
    public UpdatableTouchDelegate mTouchDelegate;
    public final AnonymousClass1 mUpdateDrawableStateRunnable;
    public final ImageView mVoiceButton;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: androidx.appcompat.widget.SearchView.SavedState.1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        };
        public boolean isIconified;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SearchView.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" isIconified=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isIconified, "}");
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.isIconified));
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.isIconified = ((Boolean) parcel.readValue(null)).booleanValue();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SearchAutoComplete extends AppCompatAutoCompleteTextView {
        public boolean mHasPendingShowSoftInputRequest;
        public final AnonymousClass1 mRunShowSoftInputIfNecessary;
        public SearchView mSearchView;
        public int mThreshold;

        public SearchAutoComplete(Context context) {
            this(context, null);
        }

        @Override // android.widget.AutoCompleteTextView
        public final boolean enoughToFilter() {
            return this.mThreshold <= 0 || super.enoughToFilter();
        }

        @Override // androidx.appcompat.widget.AppCompatAutoCompleteTextView, android.widget.TextView, android.view.View
        public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.mHasPendingShowSoftInputRequest) {
                removeCallbacks(this.mRunShowSoftInputIfNecessary);
                post(this.mRunShowSoftInputIfNecessary);
            }
            return onCreateInputConnection;
        }

        @Override // android.view.View
        public final void onFinishInflate() {
            super.onFinishInflate();
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            Configuration configuration = getResources().getConfiguration();
            int i = configuration.screenWidthDp;
            int i2 = configuration.screenHeightDp;
            setMinWidth((int) TypedValue.applyDimension(1, (i < 960 || i2 < 720 || configuration.orientation != 2) ? (i >= 600 || (i >= 640 && i2 >= 480)) ? 192 : 160 : 256, displayMetrics));
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public final void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            SearchView searchView = this.mSearchView;
            searchView.updateViewsVisibility(searchView.mIconified);
            searchView.post(searchView.mUpdateDrawableStateRunnable);
            if (searchView.mSearchSrcTextView.hasFocus()) {
                searchView.mSearchSrcTextView.refreshAutoCompleteResults();
            }
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public final void onWindowFocusChanged(boolean z) {
            super.onWindowFocusChanged(z);
            if (z && this.mSearchView.hasFocus() && getVisibility() == 0) {
                this.mHasPendingShowSoftInputRequest = true;
                Context context = getContext();
                int i = SearchView.$r8$clinit;
                if (context.getResources().getConfiguration().orientation == 2) {
                    setInputMethodMode(1);
                    if (getFilter() == null || !enoughToFilter()) {
                        return;
                    }
                    showDropDown();
                }
            }
        }

        public final void setImeVisibility(boolean z) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            if (!z) {
                this.mHasPendingShowSoftInputRequest = false;
                removeCallbacks(this.mRunShowSoftInputIfNecessary);
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else {
                if (!inputMethodManager.isActive(this)) {
                    this.mHasPendingShowSoftInputRequest = true;
                    return;
                }
                this.mHasPendingShowSoftInputRequest = false;
                removeCallbacks(this.mRunShowSoftInputIfNecessary);
                inputMethodManager.showSoftInput(this, 0);
            }
        }

        @Override // android.widget.AutoCompleteTextView
        public final void setThreshold(int i) {
            super.setThreshold(i);
            this.mThreshold = i;
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [androidx.appcompat.widget.SearchView$SearchAutoComplete$1] */
        public SearchAutoComplete(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.mRunShowSoftInputIfNecessary = new Runnable() { // from class: androidx.appcompat.widget.SearchView.SearchAutoComplete.1
                @Override // java.lang.Runnable
                public final void run() {
                    SearchAutoComplete searchAutoComplete = SearchAutoComplete.this;
                    if (searchAutoComplete.mHasPendingShowSoftInputRequest) {
                        ((InputMethodManager) searchAutoComplete.getContext().getSystemService("input_method")).showSoftInput(searchAutoComplete, 0);
                        searchAutoComplete.mHasPendingShowSoftInputRequest = false;
                    }
                }
            };
            this.mThreshold = getThreshold();
        }

        @Override // android.widget.AutoCompleteTextView
        public final void performCompletion() {
        }

        @Override // android.widget.AutoCompleteTextView
        public final void replaceText(CharSequence charSequence) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum SeslSearchViewStyle {
        LIGHT_WITH_BACKGROUND(R.color.sesl_search_view_background_text_color_light, R.color.sesl_search_view_background_hint_text_color_light, R.color.sesl_search_view_background_icon_color_light),
        LIGHT_WITHOUT_BACKGROUND(R.color.sesl_search_view_text_color, R.color.sesl_search_view_hint_text_color, R.color.sesl_search_view_icon_color),
        DARK_WITH_BACKGROUND(R.color.sesl_search_view_background_text_color_dark, R.color.sesl_search_view_background_hint_text_color_dark, R.color.sesl_search_view_background_icon_color_dark),
        DARK_WITHOUT_BACKGROUND(R.color.sesl_search_view_text_color_dark, R.color.sesl_search_view_hint_text_color_dark, R.color.sesl_search_view_icon_color_dark);

        private final int mHintTextColorRes;
        private final int mIconColorRes;
        private final int mTextColorRes;

        SeslSearchViewStyle(int i, int i2, int i3) {
            this.mTextColorRes = i;
            this.mHintTextColorRes = i2;
            this.mIconColorRes = i3;
        }

        public final void apply(Resources resources, SearchAutoComplete searchAutoComplete, List list) {
            Log.d("SearchView", "[SeslSearchViewStyle] apply " + this);
            searchAutoComplete.setTextColor(resources.getColor(this.mTextColorRes));
            searchAutoComplete.setHintTextColor(resources.getColor(this.mHintTextColorRes));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((ImageView) it.next()).setColorFilter(resources.getColor(this.mIconColorRes));
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UpdatableTouchDelegate extends TouchDelegate {
        public final Rect mActualBounds;
        public boolean mDelegateTargeted;
        public final View mDelegateView;
        public final int mSlop;
        public final Rect mSlopBounds;
        public final Rect mTargetBounds;

        public UpdatableTouchDelegate(Rect rect, Rect rect2, View view) {
            super(rect, view);
            int scaledTouchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            this.mSlop = scaledTouchSlop;
            Rect rect3 = new Rect();
            this.mTargetBounds = rect3;
            Rect rect4 = new Rect();
            this.mSlopBounds = rect4;
            Rect rect5 = new Rect();
            this.mActualBounds = rect5;
            rect3.set(rect);
            rect4.set(rect);
            int i = -scaledTouchSlop;
            rect4.inset(i, i);
            rect5.set(rect2);
            this.mDelegateView = view;
        }

        @Override // android.view.TouchDelegate
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            boolean z;
            boolean z2;
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int action = motionEvent.getAction();
            boolean z3 = true;
            if (action != 0) {
                if (action == 1 || action == 2) {
                    z2 = this.mDelegateTargeted;
                    if (z2 && !this.mSlopBounds.contains(x, y)) {
                        z3 = z2;
                        z = false;
                    }
                } else {
                    if (action == 3) {
                        z2 = this.mDelegateTargeted;
                        this.mDelegateTargeted = false;
                    }
                    z = true;
                    z3 = false;
                }
                z3 = z2;
                z = true;
            } else {
                if (this.mTargetBounds.contains(x, y)) {
                    this.mDelegateTargeted = true;
                    z = true;
                }
                z = true;
                z3 = false;
            }
            if (!z3) {
                return false;
            }
            if (!z || this.mActualBounds.contains(x, y)) {
                Rect rect = this.mActualBounds;
                motionEvent.setLocation(x - rect.left, y - rect.top);
            } else {
                motionEvent.setLocation(this.mDelegateView.getWidth() / 2, this.mDelegateView.getHeight() / 2);
            }
            return this.mDelegateView.dispatchTouchEvent(motionEvent);
        }
    }

    public SearchView(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void clearFocus() {
        this.mClearingFocus = true;
        super.clearFocus();
        this.mSearchSrcTextView.clearFocus();
        this.mSearchSrcTextView.setImeVisibility(false);
        this.mClearingFocus = false;
    }

    @Override // androidx.appcompat.view.CollapsibleActionView
    public final void onActionViewCollapsed() {
        this.mSearchSrcTextView.setText("");
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        searchAutoComplete.setSelection(searchAutoComplete.length());
        clearFocus();
        updateViewsVisibility(true);
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions);
        this.mExpandedInActionView = false;
    }

    @Override // androidx.appcompat.view.CollapsibleActionView
    public final void onActionViewExpanded() {
        if (this.mExpandedInActionView) {
            return;
        }
        this.mExpandedInActionView = true;
        int imeOptions = this.mSearchSrcTextView.getImeOptions();
        this.mCollapsedImeOptions = imeOptions;
        this.mSearchSrcTextView.setImeOptions(imeOptions | 33554432);
        this.mSearchSrcTextView.setText("");
        onSearchClicked();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        seslCheckMaxFont();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        removeCallbacks(this.mUpdateDrawableStateRunnable);
        post(this.mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
            Rect rect = this.mSearchSrcTextViewBounds;
            searchAutoComplete.getLocationInWindow(this.mTemp);
            getLocationInWindow(this.mTemp2);
            int[] iArr = this.mTemp;
            int i5 = iArr[1];
            int[] iArr2 = this.mTemp2;
            int i6 = i5 - iArr2[1];
            int i7 = iArr[0] - iArr2[0];
            rect.set(i7, i6, searchAutoComplete.getWidth() + i7, searchAutoComplete.getHeight() + i6);
            Rect rect2 = this.mSearchSrtTextViewBoundsExpanded;
            Rect rect3 = this.mSearchSrcTextViewBounds;
            rect2.set(rect3.left, 0, rect3.right, i4 - i2);
            UpdatableTouchDelegate updatableTouchDelegate = this.mTouchDelegate;
            if (updatableTouchDelegate == null) {
                UpdatableTouchDelegate updatableTouchDelegate2 = new UpdatableTouchDelegate(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds, this.mSearchSrcTextView);
                this.mTouchDelegate = updatableTouchDelegate2;
                setTouchDelegate(updatableTouchDelegate2);
                return;
            }
            Rect rect4 = this.mSearchSrtTextViewBoundsExpanded;
            Rect rect5 = this.mSearchSrcTextViewBounds;
            updatableTouchDelegate.mTargetBounds.set(rect4);
            updatableTouchDelegate.mSlopBounds.set(rect4);
            Rect rect6 = updatableTouchDelegate.mSlopBounds;
            int i8 = -updatableTouchDelegate.mSlop;
            rect6.inset(i8, i8);
            updatableTouchDelegate.mActualBounds.set(rect5);
        }
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        if (this.mIconified) {
            super.onMeasure(i, i2);
            return;
        }
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode == Integer.MIN_VALUE) {
            int i4 = this.mMaxWidth;
            if (i4 > 0) {
                size = Math.min(i4, size);
            }
        } else if (mode == 0) {
            size = this.mMaxWidth;
            if (size <= 0) {
                size = getContext().getResources().getDimensionPixelSize(R.dimen.sesl_search_view_preferred_width);
            }
        } else if (mode == 1073741824 && (i3 = this.mMaxWidth) > 0) {
            size = Math.min(i3, size);
        }
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode2 == Integer.MIN_VALUE) {
            size2 = Math.min(getContext().getResources().getDimensionPixelSize(R.dimen.sesl_search_view_preferred_height), size2);
        } else if (mode2 == 0) {
            size2 = getContext().getResources().getDimensionPixelSize(R.dimen.sesl_search_view_preferred_height);
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        updateViewsVisibility(savedState.isIconified);
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isIconified = this.mIconified;
        return savedState;
    }

    public final void onSearchClicked() {
        updateViewsVisibility(false);
        this.mSearchSrcTextView.requestFocus();
        if (SeslInputMethodManagerReflector.isAccessoryKeyboardState(this.mImm) != 0) {
            this.mSearchSrcTextView.setImeVisibility(false);
        } else {
            this.mSearchSrcTextView.setImeVisibility(true);
        }
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (SeslInputMethodManagerReflector.isAccessoryKeyboardState(this.mImm) != 0) {
            return;
        }
        post(this.mUpdateDrawableStateRunnable);
    }

    @Override // android.view.View
    public final boolean performLongClick() {
        TooltipCompatHandler.sIsForceBelow = true;
        TooltipCompatHandler.sIsForceActionBarX = true;
        return super.performLongClick();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean requestFocus(int i, Rect rect) {
        if (this.mClearingFocus || !isFocusable()) {
            return false;
        }
        if (this.mIconified) {
            return super.requestFocus(i, rect);
        }
        boolean requestFocus = this.mSearchSrcTextView.requestFocus(i, rect);
        if (requestFocus) {
            updateViewsVisibility(false);
        }
        return requestFocus;
    }

    public final void seslCheckMaxFont() {
        float f = getContext().getResources().getConfiguration().fontScale;
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.sesl_search_view_search_text_size);
        if (f > 1.3f) {
            this.mSearchSrcTextView.setTextSize(0, (dimensionPixelSize / f) * 1.3f);
        } else {
            this.mSearchSrcTextView.setTextSize(0, dimensionPixelSize);
        }
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        View view = this.mSearchPlate;
        if (view != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.setBackground(drawable);
        }
    }

    @Override // android.view.View
    public final void setBackgroundResource(int i) {
        View view = this.mSearchPlate;
        if (view != null) {
            Drawable drawable = getContext().getResources().getDrawable(i);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.setBackground(drawable);
        }
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        View view = this.mSearchPlate;
        if (view != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setElevation(view, f);
        }
    }

    public final void updateCloseButton() {
        boolean isEmpty = TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        this.mCloseButton.setVisibility(!isEmpty ? 0 : 8);
        Drawable drawable = this.mCloseButton.getDrawable();
        if (drawable != null) {
            drawable.setState(!isEmpty ? ViewGroup.ENABLED_STATE_SET : ViewGroup.EMPTY_STATE_SET);
        }
    }

    public final void updateFocusedState() {
        int[] iArr = this.mSearchSrcTextView.hasFocus() ? ViewGroup.FOCUSED_STATE_SET : ViewGroup.EMPTY_STATE_SET;
        Drawable background = this.mSearchPlate.getBackground();
        if (background != null) {
            background.setState(iArr);
        }
        Drawable background2 = this.mSubmitArea.getBackground();
        if (background2 != null) {
            background2.setState(iArr);
        }
        invalidate();
    }

    public final void updateViewsVisibility(boolean z) {
        this.mIconified = z;
        int i = z ? 0 : 8;
        TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        this.mSearchButton.setVisibility(i);
        this.mGoButton.setVisibility(8);
        this.mSearchEditFrame.setVisibility(z ? 8 : 0);
        this.mCollapsedIcon.setVisibility(8);
        updateCloseButton();
        this.mVoiceButton.setImageDrawable(this.mGVIVoiceIcon);
        this.mVoiceButton.setVisibility(8);
        this.mSubmitArea.setVisibility(8);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.searchViewStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [android.widget.TextView$OnEditorActionListener, androidx.appcompat.widget.SearchView$7] */
    /* JADX WARN: Type inference failed for: r10v4, types: [android.widget.ImageView[], java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r11v0, types: [android.widget.AdapterView$OnItemClickListener, androidx.appcompat.widget.SearchView$8] */
    /* JADX WARN: Type inference failed for: r12v0, types: [android.widget.AdapterView$OnItemSelectedListener, androidx.appcompat.widget.SearchView$9] */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Type inference failed for: r12v8 */
    /* JADX WARN: Type inference failed for: r12v9 */
    /* JADX WARN: Type inference failed for: r13v0, types: [android.text.TextWatcher, androidx.appcompat.widget.SearchView$10] */
    /* JADX WARN: Type inference failed for: r14v3, types: [android.view.View, android.widget.ImageView] */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r2v11, types: [android.widget.AutoCompleteTextView, androidx.appcompat.widget.SearchView$SearchAutoComplete, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v4, types: [androidx.appcompat.widget.SearchView$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [androidx.appcompat.widget.SearchView$2] */
    /* JADX WARN: Type inference failed for: r3v34, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r4v5, types: [android.view.View, android.widget.ImageView] */
    /* JADX WARN: Type inference failed for: r6v4, types: [android.view.View, android.widget.ImageView] */
    /* JADX WARN: Type inference failed for: r7v3, types: [android.view.View, android.widget.ImageView] */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.view.View$OnClickListener, androidx.appcompat.widget.SearchView$5] */
    /* JADX WARN: Type inference failed for: r9v0, types: [android.view.View$OnKeyListener, androidx.appcompat.widget.SearchView$6] */
    /* JADX WARN: Type inference failed for: r9v22, types: [androidx.appcompat.widget.SearchView$SeslSearchViewStyle] */
    public SearchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        char c;
        char c2;
        ?? r12;
        Method declaredMethod;
        this.mSearchSrcTextViewBounds = new Rect();
        this.mSearchSrtTextViewBoundsExpanded = new Rect();
        this.mTemp = new int[2];
        this.mTemp2 = new int[2];
        this.mUpdateDrawableStateRunnable = new Runnable() { // from class: androidx.appcompat.widget.SearchView.1
            @Override // java.lang.Runnable
            public final void run() {
                SearchView.this.updateFocusedState();
            }
        };
        this.mReleaseCursorRunnable = new Runnable() { // from class: androidx.appcompat.widget.SearchView.2
            @Override // java.lang.Runnable
            public final void run() {
                SearchView.this.getClass();
            }
        };
        new WeakHashMap();
        ?? r8 = new View.OnClickListener() { // from class: androidx.appcompat.widget.SearchView.5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchAutoComplete searchAutoComplete;
                SearchView searchView = SearchView.this;
                if (view == searchView.mSearchButton) {
                    searchView.onSearchClicked();
                    return;
                }
                if (view != searchView.mCloseButton) {
                    if (view != searchView.mGoButton) {
                        if (view != searchView.mVoiceButton && view == (searchAutoComplete = searchView.mSearchSrcTextView)) {
                            searchAutoComplete.refreshAutoCompleteResults();
                            return;
                        }
                        return;
                    }
                    Editable text = searchView.mSearchSrcTextView.getText();
                    if (text == null || TextUtils.getTrimmedLength(text) <= 0) {
                        return;
                    }
                    searchView.mSearchSrcTextView.setImeVisibility(false);
                    searchView.mSearchSrcTextView.dismissDropDown();
                    return;
                }
                if (TextUtils.isEmpty(searchView.mSearchSrcTextView.getText())) {
                    if (searchView.mIconifiedByDefault) {
                        searchView.clearFocus();
                        searchView.updateViewsVisibility(true);
                        return;
                    }
                    return;
                }
                searchView.mSearchSrcTextView.setText("");
                searchView.mSearchSrcTextView.requestFocus();
                searchView.mSearchSrcTextView.announceForAccessibility(searchView.getResources().getString(R.string.sesl_searchview_description_clear_field));
                if (SeslInputMethodManagerReflector.isAccessoryKeyboardState(searchView.mImm) != 0) {
                    searchView.mSearchSrcTextView.setImeVisibility(false);
                } else {
                    searchView.mSearchSrcTextView.setImeVisibility(true);
                }
            }
        };
        this.mOnClickListener = r8;
        ?? r9 = new View.OnKeyListener() { // from class: androidx.appcompat.widget.SearchView.6
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i2, KeyEvent keyEvent) {
                InputMethodManager inputMethodManager;
                if (SearchView.this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.folder_type") && (inputMethodManager = (InputMethodManager) SearchView.this.getContext().getSystemService("input_method")) != null && i2 == 23) {
                    inputMethodManager.viewClicked(view);
                    inputMethodManager.showSoftInput(view, 1);
                }
                SearchView.this.getClass();
                return false;
            }
        };
        this.mTextKeyListener = r9;
        ?? r10 = new TextView.OnEditorActionListener() { // from class: androidx.appcompat.widget.SearchView.7
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                SearchView searchView = SearchView.this;
                Editable text = searchView.mSearchSrcTextView.getText();
                if (text == null || TextUtils.getTrimmedLength(text) <= 0) {
                    return true;
                }
                searchView.mSearchSrcTextView.setImeVisibility(false);
                searchView.mSearchSrcTextView.dismissDropDown();
                return true;
            }
        };
        this.mOnEditorActionListener = r10;
        ?? r11 = new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.widget.SearchView.8
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j) {
                SearchView.this.getClass();
                throw null;
            }
        };
        this.mOnItemClickListener = r11;
        ?? r122 = new AdapterView.OnItemSelectedListener() { // from class: androidx.appcompat.widget.SearchView.9
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onItemSelected(AdapterView adapterView, View view, int i2, long j) {
                SearchView.this.mSearchSrcTextView.getText();
                throw null;
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onNothingSelected(AdapterView adapterView) {
            }
        };
        this.mOnItemSelectedListener = r122;
        ?? r13 = new TextWatcher() { // from class: androidx.appcompat.widget.SearchView.10
            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                SearchView searchView = SearchView.this;
                TextUtils.isEmpty(searchView.mSearchSrcTextView.getText());
                searchView.mGoButton.setVisibility(8);
                searchView.mVoiceButton.setImageDrawable(searchView.mGVIVoiceIcon);
                searchView.mVoiceButton.setVisibility(8);
                searchView.updateCloseButton();
                searchView.mSubmitArea.setVisibility(8);
                if (TextUtils.equals(charSequence, searchView.mOldQueryText)) {
                    return;
                }
                searchView.mOldQueryText = charSequence.toString();
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }
        };
        this.mTextWatcher = r13;
        int[] iArr = R$styleable.SearchView;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArray, i, 0);
        LayoutInflater.from(context).inflate(obtainStyledAttributes.mWrapped.getResourceId(19, R.layout.sesl_search_view), (ViewGroup) this, true);
        this.mContext = context;
        ?? r2 = (SearchAutoComplete) findViewById(R.id.search_src_text);
        this.mSearchSrcTextView = r2;
        r2.mSearchView = this;
        this.mSearchEditFrame = findViewById(R.id.search_edit_frame);
        View findViewById = findViewById(R.id.search_plate);
        this.mSearchPlate = findViewById;
        View findViewById2 = findViewById(R.id.submit_area);
        this.mSubmitArea = findViewById2;
        ?? r6 = (ImageView) findViewById(R.id.search_button);
        this.mSearchButton = r6;
        ?? r7 = (ImageView) findViewById(R.id.search_go_btn);
        this.mGoButton = r7;
        ?? r4 = (ImageView) findViewById(R.id.search_close_btn);
        this.mCloseButton = r4;
        ?? r14 = (ImageView) findViewById(R.id.search_voice_btn);
        this.mVoiceButton = r14;
        ImageView imageView = (ImageView) findViewById(R.id.search_more_btn);
        ImageView imageView2 = (ImageView) findViewById(R.id.search_back_btn);
        ImageView imageView3 = (ImageView) findViewById(R.id.search_mag_icon);
        this.mCollapsedIcon = imageView3;
        findViewById.setBackground(obtainStyledAttributes.getDrawable(20));
        findViewById2.setBackground(obtainStyledAttributes.getDrawable(25));
        obtainStyledAttributes.mWrapped.getResourceId(23, 0);
        r6.setImageDrawable(obtainStyledAttributes.getDrawable(23));
        r7.setImageDrawable(obtainStyledAttributes.getDrawable(15));
        r4.setImageDrawable(obtainStyledAttributes.getDrawable(12));
        imageView3.setImageDrawable(obtainStyledAttributes.getDrawable(23));
        Drawable drawable = obtainStyledAttributes.getDrawable(28);
        this.mGVIVoiceIcon = drawable;
        this.mSVIVoiceIcon = obtainStyledAttributes.getDrawable(29);
        r14.setImageDrawable(drawable);
        obtainStyledAttributes.getDrawable(22);
        r6.setTooltipText(r6.getContentDescription());
        r4.setTooltipText(r4.getContentDescription());
        r7.setTooltipText(r7.getContentDescription());
        r14.setTooltipText(r14.getContentDescription());
        imageView.setTooltipText(imageView.getContentDescription());
        imageView2.setTooltipText(imageView2.getContentDescription());
        obtainStyledAttributes.mWrapped.getResourceId(26, R.layout.sesl_search_dropdown_item_icons_2line);
        obtainStyledAttributes.mWrapped.getResourceId(13, 0);
        r6.setOnClickListener(r8);
        r4.setOnClickListener(r8);
        r7.setOnClickListener(r8);
        r14.setOnClickListener(r8);
        r2.setOnClickListener(r8);
        r2.addTextChangedListener(r13);
        r2.setOnEditorActionListener(r10);
        r2.setOnItemClickListener(r11);
        r2.setOnItemSelectedListener(r122);
        r2.setOnKeyListener(r9);
        r2.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.appcompat.widget.SearchView.3
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                SearchView.this.getClass();
            }
        });
        boolean z = obtainStyledAttributes.mWrapped.getBoolean(18, true);
        if (this.mIconifiedByDefault != z) {
            this.mIconifiedByDefault = z;
            updateViewsVisibility(z);
            String str = this.mQueryHint;
            str = str == null ? this.mDefaultQueryHint : str;
            r2.setHint(str == null ? "" : str);
        }
        int dimensionPixelSize = obtainStyledAttributes.mWrapped.getDimensionPixelSize(2, -1);
        if (dimensionPixelSize != -1) {
            this.mMaxWidth = dimensionPixelSize;
            requestLayout();
        }
        String text = obtainStyledAttributes.mWrapped.getText(14);
        this.mDefaultQueryHint = text;
        this.mQueryHint = obtainStyledAttributes.mWrapped.getText(21);
        int i2 = obtainStyledAttributes.mWrapped.getInt(6, -1);
        if (i2 != -1) {
            r2.setImeOptions(i2);
        }
        int i3 = obtainStyledAttributes.mWrapped.getInt(5, -1);
        if (i3 != -1) {
            r2.setInputType(i3);
        }
        setFocusable(obtainStyledAttributes.mWrapped.getBoolean(1, true));
        imageView3.setImageDrawable(obtainStyledAttributes.getDrawable(23));
        r6.setImageDrawable(obtainStyledAttributes.getDrawable(23));
        Resources resources = context.getResources();
        r2.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
        char c3 = findViewById.getBackground() != null ? (char) 1 : (char) 0;
        SeslSearchViewStyle seslSearchViewStyle = SeslSearchViewStyle.LIGHT_WITH_BACKGROUND;
        if (SeslMisc.isLightTheme(context)) {
            c = 0;
            c2 = 1;
            r12 = new SeslSearchViewStyle[]{SeslSearchViewStyle.LIGHT_WITH_BACKGROUND, SeslSearchViewStyle.LIGHT_WITHOUT_BACKGROUND};
        } else {
            c = 0;
            c2 = 1;
            r12 = new SeslSearchViewStyle[]{SeslSearchViewStyle.DARK_WITH_BACKGROUND, SeslSearchViewStyle.DARK_WITHOUT_BACKGROUND};
        }
        ?? r92 = r12[c3 ^ 1];
        ?? r102 = new ImageView[5];
        r102[c] = r7;
        r102[c2] = r4;
        r102[2] = r14;
        r102[3] = imageView;
        r102[4] = r6;
        r92.apply(resources, r2, Arrays.asList(r102));
        obtainStyledAttributes.recycle();
        Intent intent = new Intent("android.speech.action.WEB_SEARCH");
        intent.addFlags(268435456);
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        new Intent("android.speech.action.RECOGNIZE_SPEECH").addFlags(268435456);
        new Intent("samsung.honeyboard.honeyvoice.action.RECOGNIZE_SPEECH").addFlags(268435456);
        View findViewById3 = findViewById(r2.getDropDownAnchor());
        this.mDropDownAnchor = findViewById3;
        if (findViewById3 != null) {
            findViewById3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.appcompat.widget.SearchView.4
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                    SearchView searchView = SearchView.this;
                    if (searchView.mDropDownAnchor.getWidth() > 1) {
                        Rect rect = new Rect();
                        boolean z2 = searchView.getLayoutDirection() == 1;
                        if (searchView.mSearchSrcTextView.getDropDownBackground() != null) {
                            searchView.mSearchSrcTextView.getDropDownBackground().getPadding(rect);
                        }
                        searchView.mSearchSrcTextView.setDropDownHorizontalOffset(z2 ? -rect.left : 0 - rect.left);
                        searchView.mSearchSrcTextView.setDropDownWidth(searchView.mDropDownAnchor.getWidth() + rect.left + rect.right);
                        if (searchView.mSearchSrcTextView.isPopupShowing()) {
                            searchView.mSearchSrcTextView.showDropDown();
                        }
                    }
                }
            });
        }
        updateViewsVisibility(this.mIconifiedByDefault);
        ?? r1 = this.mQueryHint;
        String str2 = r1 != null ? r1 : text;
        r2.setHint(str2 != null ? str2 : "");
        this.mImm = (InputMethodManager) getContext().getSystemService("input_method");
        Class cls = SeslTextViewReflector.mClass;
        Method declaredMethod2 = SeslBaseReflector.getDeclaredMethod(cls, "hidden_SEM_AUTOFILL_ID", new Class[0]);
        Object invoke = declaredMethod2 != null ? SeslBaseReflector.invoke(null, declaredMethod2, new Object[0]) : null;
        int intValue = invoke instanceof Integer ? ((Integer) invoke).intValue() : 0;
        if (intValue != 0 && (declaredMethod = SeslBaseReflector.getDeclaredMethod(cls, "hidden_semSetActionModeMenuItemEnabled", Integer.TYPE, Boolean.TYPE)) != null) {
            SeslBaseReflector.invoke(r2, declaredMethod, Integer.valueOf(intValue), Boolean.FALSE);
        }
        seslCheckMaxFont();
    }
}
