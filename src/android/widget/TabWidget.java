package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.flags.Flags;
import android.widget.LinearLayout;
import com.android.internal.R;
import com.samsung.android.widget.SemTabDotLineView;

@Deprecated
/* loaded from: classes5.dex */
public class TabWidget extends LinearLayout implements View.OnFocusChangeListener {
    private static final int FONT_WEIGHT_REGULAR = 400;
    private static final int FONT_WEIGHT_SEMIBOLD = 600;
    private final Rect mBounds;
    private float mDefaultTextSize;
    private boolean mDrawBottomStrips;
    private int[] mImposedTabWidths;
    private int mImposedTabsHeight;
    private boolean mIsThemeDeviceDefaultFamily;
    private Drawable mLeftStrip;
    private float mMaxFontScale;
    private Drawable mRightStrip;
    private int mSelectedTab;
    private OnTabSelectionChanged mSelectionChangedListener;
    private Typeface mSemRegularFont;
    private Typeface mSemSemiBoldFont;
    private boolean mStripMoved;
    private ColorStateList mTabTextColorStateList;

    interface OnTabSelectionChanged {
        void onTabSelectionChanged(int i, boolean z);
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
    }

    public TabWidget(Context context) {
        this(context, null);
    }

    public TabWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842883);
    }

    public TabWidget(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TabWidget(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i, i2);
        this.mBounds = new Rect();
        this.mSelectedTab = -1;
        this.mDrawBottomStrips = true;
        this.mImposedTabsHeight = -1;
        this.mDefaultTextSize = 14.0f;
        this.mMaxFontScale = 1.3f;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TabWidget, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.TabWidget, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        this.mDrawBottomStrips = typedArrayObtainStyledAttributes.getBoolean(3, this.mDrawBottomStrips);
        boolean z = context.getApplicationInfo().targetSdkVersion <= 4;
        if (typedArrayObtainStyledAttributes.hasValueOrEmpty(1)) {
            this.mLeftStrip = typedArrayObtainStyledAttributes.getDrawable(1);
        } else if (z) {
            this.mLeftStrip = context.getDrawable(R.drawable.tab_bottom_left_v4);
        } else {
            this.mLeftStrip = context.getDrawable(R.drawable.tab_bottom_left);
        }
        if (typedArrayObtainStyledAttributes.hasValueOrEmpty(2)) {
            this.mRightStrip = typedArrayObtainStyledAttributes.getDrawable(2);
        } else if (z) {
            this.mRightStrip = context.getDrawable(R.drawable.tab_bottom_right_v4);
        } else {
            this.mRightStrip = context.getDrawable(R.drawable.tab_bottom_right);
        }
        typedArrayObtainStyledAttributes.recycle();
        setChildrenDrawingOrderEnabled(true);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        boolean z2 = typedValue.data != 0;
        this.mIsThemeDeviceDefaultFamily = z2;
        if (z2) {
            TypedArray typedArrayObtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(null, R.styleable.Theme, 0, 0);
            int resourceId = typedArrayObtainStyledAttributes2.getResourceId(143, 0);
            typedArrayObtainStyledAttributes2.recycle();
            TypedArray typedArrayObtainStyledAttributes3 = getContext().obtainStyledAttributes(resourceId, R.styleable.TextAppearance);
            TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes3.peekValue(0);
            typedArrayObtainStyledAttributes3.recycle();
            if (typedValuePeekValue != null) {
                this.mDefaultTextSize = TypedValue.complexToFloat(typedValuePeekValue.data);
            }
            this.mSemRegularFont = Typeface.create(Typeface.create("sec", 0), 400, false);
            this.mSemSemiBoldFont = Typeface.create(Typeface.create("sec", 0), 600, false);
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mIsThemeDeviceDefaultFamily) {
            float f = getContext().getResources().getConfiguration().fontScale;
            float f2 = this.mMaxFontScale;
            if (f > f2) {
                f = f2;
            }
            int tabCount = getTabCount();
            for (int i3 = 0; i3 < tabCount; i3++) {
                View childTabViewAt = getChildTabViewAt(i3);
                if (childTabViewAt != null) {
                    View viewFindViewById = childTabViewAt.findViewById(16908310);
                    if (viewFindViewById instanceof TextView) {
                        ((TextView) viewFindViewById).setTextSize(1, this.mDefaultTextSize * f);
                    }
                }
            }
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        this.mStripMoved = true;
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        int i3 = this.mSelectedTab;
        return i3 == -1 ? i2 : i2 == i + (-1) ? i3 : i2 >= i3 ? i2 + 1 : i2;
    }

    @Override // android.widget.LinearLayout
    void measureChildBeforeLayout(View view, int i, int i2, int i3, int i4, int i5) {
        if (!isMeasureWithLargestChildEnabled() && this.mImposedTabsHeight >= 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(this.mImposedTabWidths[i] + i3, 1073741824);
            i4 = View.MeasureSpec.makeMeasureSpec(this.mImposedTabsHeight, 1073741824);
        }
        super.measureChildBeforeLayout(view, i, i2, i3, i4, i5);
    }

    @Override // android.widget.LinearLayout
    void measureHorizontal(int i, int i2) {
        if (View.MeasureSpec.getMode(i) == 0) {
            super.measureHorizontal(i, i2);
            return;
        }
        int size = View.MeasureSpec.getSize(i);
        int iMakeSafeMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(size, 0);
        this.mImposedTabsHeight = -1;
        super.measureHorizontal(iMakeSafeMeasureSpec, i2);
        int measuredWidth = getMeasuredWidth() - size;
        if (measuredWidth > 0) {
            int childCount = getChildCount();
            int i3 = 0;
            for (int i4 = 0; i4 < childCount; i4++) {
                if (getChildAt(i4).getVisibility() != 8) {
                    i3++;
                }
            }
            if (i3 > 0) {
                int[] iArr = this.mImposedTabWidths;
                if (iArr == null || iArr.length != childCount) {
                    this.mImposedTabWidths = new int[childCount];
                }
                for (int i5 = 0; i5 < childCount; i5++) {
                    View childAt = getChildAt(i5);
                    if (childAt.getVisibility() != 8) {
                        int measuredWidth2 = childAt.getMeasuredWidth();
                        int iMax = Math.max(0, measuredWidth2 - (measuredWidth / i3));
                        this.mImposedTabWidths[i5] = iMax;
                        measuredWidth -= measuredWidth2 - iMax;
                        i3--;
                        this.mImposedTabsHeight = Math.max(this.mImposedTabsHeight, childAt.getMeasuredHeight());
                    }
                }
            }
        }
        super.measureHorizontal(i, i2);
    }

    public View getChildTabViewAt(int i) {
        return getChildAt(i);
    }

    public int getTabCount() {
        return getChildCount();
    }

    @Override // android.widget.LinearLayout
    public void setDividerDrawable(Drawable drawable) {
        super.setDividerDrawable(drawable);
    }

    public void setDividerDrawable(int i) {
        setDividerDrawable(this.mContext.getDrawable(i));
    }

    public void setLeftStripDrawable(Drawable drawable) {
        this.mLeftStrip = drawable;
        requestLayout();
        invalidate();
    }

    public void setLeftStripDrawable(int i) {
        setLeftStripDrawable(this.mContext.getDrawable(i));
    }

    public Drawable getLeftStripDrawable() {
        return this.mLeftStrip;
    }

    public void setRightStripDrawable(Drawable drawable) {
        this.mRightStrip = drawable;
        requestLayout();
        invalidate();
    }

    public void setRightStripDrawable(int i) {
        setRightStripDrawable(this.mContext.getDrawable(i));
    }

    public Drawable getRightStripDrawable() {
        return this.mRightStrip;
    }

    public void setStripEnabled(boolean z) {
        this.mDrawBottomStrips = z;
        invalidate();
    }

    public boolean isStripEnabled() {
        return this.mDrawBottomStrips;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void childDrawableStateChanged(View view) {
        if (getTabCount() > 0 && view == getChildTabViewAt(this.mSelectedTab)) {
            invalidate();
        }
        super.childDrawableStateChanged(view);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (getTabCount() != 0 && this.mDrawBottomStrips) {
            View childTabViewAt = getChildTabViewAt(this.mSelectedTab);
            Drawable drawable = this.mLeftStrip;
            Drawable drawable2 = this.mRightStrip;
            if (drawable != null) {
                drawable.setState(childTabViewAt.getDrawableState());
            }
            if (drawable2 != null) {
                drawable2.setState(childTabViewAt.getDrawableState());
            }
            if (this.mStripMoved) {
                Rect rect = this.mBounds;
                rect.left = childTabViewAt.getLeft();
                rect.right = childTabViewAt.getRight();
                int height = getHeight();
                if (drawable != null) {
                    drawable.setBounds(Math.min(0, rect.left - drawable.getIntrinsicWidth()), height - drawable.getIntrinsicHeight(), rect.left, height);
                }
                if (drawable2 != null) {
                    drawable2.setBounds(rect.right, height - drawable2.getIntrinsicHeight(), Math.max(getWidth(), rect.right + drawable2.getIntrinsicWidth()), height);
                }
                this.mStripMoved = false;
            }
            if (drawable != null) {
                drawable.draw(canvas);
            }
            if (drawable2 != null) {
                drawable2.draw(canvas);
            }
        }
    }

    public void setCurrentTab(int i) {
        int i2;
        View childTabViewAt;
        View childTabViewAt2;
        if (i < 0 || i >= getTabCount() || i == (i2 = this.mSelectedTab)) {
            return;
        }
        if (i2 != -1) {
            getChildTabViewAt(i2).setSelected(false);
            if (this.mIsThemeDeviceDefaultFamily && (childTabViewAt2 = getChildTabViewAt(this.mSelectedTab)) != null) {
                TextView textView = (TextView) childTabViewAt2.findViewById(16908310);
                if (textView != null) {
                    textView.setTextColor(this.mTabTextColorStateList);
                    textView.setTypeface(this.mSemRegularFont);
                }
                SemTabDotLineView semTabDotLineView = (SemTabDotLineView) childTabViewAt2.findViewById(R.id.sem_tab_indicator);
                if (semTabDotLineView != null) {
                    semTabDotLineView.setDrawState(false);
                }
            }
        }
        this.mSelectedTab = i;
        getChildTabViewAt(i).setSelected(true);
        this.mStripMoved = true;
        if (!this.mIsThemeDeviceDefaultFamily || (childTabViewAt = getChildTabViewAt(this.mSelectedTab)) == null) {
            return;
        }
        TextView textView2 = (TextView) childTabViewAt.findViewById(16908310);
        if (textView2 != null) {
            textView2.setTextColor(this.mTabTextColorStateList);
            textView2.setTypeface(this.mSemSemiBoldFont);
        }
        SemTabDotLineView semTabDotLineView2 = (SemTabDotLineView) childTabViewAt.findViewById(R.id.sem_tab_indicator);
        if (semTabDotLineView2 != null) {
            semTabDotLineView2.setDrawState(true);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return TabWidget.class.getName();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setItemCount(getTabCount());
        accessibilityEvent.setCurrentItemIndex(this.mSelectedTab);
    }

    public void focusCurrentTab(int i) throws Resources.NotFoundException {
        int i2 = this.mSelectedTab;
        setCurrentTab(i);
        if (i2 != i) {
            getChildTabViewAt(i).requestFocus();
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) throws Resources.NotFoundException {
        super.setEnabled(z);
        int tabCount = getTabCount();
        for (int i = 0; i < tabCount; i++) {
            getChildTabViewAt(i).setEnabled(z);
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view) throws Resources.NotFoundException {
        if (view.getLayoutParams() == null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1, 1.0f);
            layoutParams.setMargins(0, 0, 0, 0);
            view.setLayoutParams(layoutParams);
        }
        view.setFocusable(true);
        view.setClickable(true);
        if (!Flags.enableArrowIconOnHoverWhenClickable() && view.getPointerIcon() == null) {
            view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1002));
        }
        super.addView(view);
        view.setOnClickListener(new TabClickListener(getTabCount() - 1));
        if (this.mIsThemeDeviceDefaultFamily) {
            view.setOnTouchListener(new SemTabTouchListener(getTabCount() - 1));
            TextView textView = (TextView) view.findViewById(16908310);
            if (textView != null) {
                this.mTabTextColorStateList = textView.getTextColors();
            }
        }
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        super.removeAllViews();
        this.mSelectedTab = -1;
    }

    @Override // android.view.ViewGroup, android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        if (isEnabled()) {
            return super.onResolvePointerIcon(motionEvent, i);
        }
        return null;
    }

    void setTabSelectionListener(OnTabSelectionChanged onTabSelectionChanged) {
        this.mSelectionChangedListener = onTabSelectionChanged;
    }

    private class TabClickListener implements View.OnClickListener {
        private final int mTabIndex;

        private TabClickListener(int i) {
            this.mTabIndex = i;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TabWidget.this.mSelectionChangedListener.onTabSelectionChanged(this.mTabIndex, true);
        }
    }

    private class SemTabTouchListener implements View.OnTouchListener {
        private final int mTabIndex;

        private SemTabTouchListener(int i) {
            this.mTabIndex = i;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            TabWidget tabWidget;
            View childTabViewAt;
            if (TabWidget.this.mSelectedTab == this.mTabIndex) {
                return false;
            }
            int action = motionEvent.getAction();
            SemTabDotLineView semTabDotLineView = (SemTabDotLineView) view.findViewById(R.id.sem_tab_indicator);
            TextView textView = (TextView) view.findViewById(16908310);
            if (semTabDotLineView == null || textView == null || (childTabViewAt = (tabWidget = TabWidget.this).getChildTabViewAt(tabWidget.mSelectedTab)) == null) {
                return false;
            }
            SemTabDotLineView semTabDotLineView2 = (SemTabDotLineView) childTabViewAt.findViewById(R.id.sem_tab_indicator);
            TextView textView2 = (TextView) childTabViewAt.findViewById(16908310);
            if (semTabDotLineView2 != null && textView2 != null) {
                if (action == 0) {
                    semTabDotLineView2.setDrawState(false);
                    TabWidget tabWidget2 = TabWidget.this;
                    textView2.setTextColor(tabWidget2.getNotSelectedColor(tabWidget2.mTabTextColorStateList));
                    textView2.setTypeface(TabWidget.this.mSemRegularFont);
                    TabWidget tabWidget3 = TabWidget.this;
                    textView.setTextColor(tabWidget3.getSelectedColor(tabWidget3.mTabTextColorStateList));
                    textView.setTypeface(TabWidget.this.mSemSemiBoldFont);
                    textView.setSelected(false);
                } else if ((action == 1 || action == 2) && !view.isPressed()) {
                    semTabDotLineView2.setSelected(true);
                    semTabDotLineView2.mDrawDot = true;
                    textView2.setTextColor(TabWidget.this.mTabTextColorStateList);
                    textView2.setTypeface(TabWidget.this.mSemSemiBoldFont);
                    TabWidget tabWidget4 = TabWidget.this;
                    textView.setTextColor(tabWidget4.getNotSelectedColor(tabWidget4.mTabTextColorStateList));
                    textView.setTypeface(TabWidget.this.mSemRegularFont);
                    textView.setSelected(false);
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSelectedColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            return colorStateList.getColorForState(new int[]{16842913, 16842910}, colorStateList.getDefaultColor());
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNotSelectedColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            return colorStateList.getColorForState(new int[]{-16842913, -16842908}, colorStateList.getDefaultColor());
        }
        return -1;
    }
}
