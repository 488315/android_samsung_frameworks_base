package com.android.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ActionMenuPresenter;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.view.menu.MenuBuilder;

/* loaded from: classes6.dex */
public class ActionBarContextView extends AbsActionBarView {
    private static final String TAG = "ActionBarContextView";
    private View mClose;
    private int mCloseItemLayout;
    private View mCustomView;
    private boolean mIsSetOpenTheme;
    private float mMaxFontScale;
    private Drawable mSplitBackground;
    private CharSequence mSubtitle;
    private int mSubtitleStyleRes;
    private TextView mSubtitleView;
    private CharSequence mTitle;
    private LinearLayout mTitleLayout;
    private boolean mTitleOptional;
    private int mTitleStyleRes;
    private TextView mTitleView;

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public ActionBarContextView(Context context) {
        this(context, null);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843668);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaxFontScale = 1.3f;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionMode, i, i2);
        setBackground(obtainStyledAttributes.getDrawable(0));
        this.mTitleStyleRes = obtainStyledAttributes.getResourceId(2, 0);
        this.mSubtitleStyleRes = obtainStyledAttributes.getResourceId(3, 0);
        this.mContentHeight = obtainStyledAttributes.getLayoutDimension(1, 0);
        this.mSplitBackground = obtainStyledAttributes.getDrawable(4);
        this.mCloseItemLayout = obtainStyledAttributes.getResourceId(5, R.layout.action_mode_close_item);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        this.mIsThemeDeviceDefaultFamily = typedValue.data != 0;
        boolean z = this.mIsThemeDeviceDefaultFamily && context.getResources().getAssets().getSamsungThemeOverlays().size() > 0;
        this.mIsSetOpenTheme = z;
        if (z) {
            if (obtainStyledAttributes.getResourceId(0, 0) == 17304054) {
                setBackground(context.getDrawable(R.drawable.tw_action_bar_background));
            } else if (obtainStyledAttributes.getResourceId(0, 0) == 17304055) {
                setBackground(context.getDrawable(R.drawable.tw_action_bar_background_dark));
            }
        }
        obtainStyledAttributes.recycle();
    }

    @Override // com.android.internal.widget.AbsActionBarView, android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mIsThemeDeviceDefaultFamily) {
            TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, R.styleable.ActionMode, 16843668, 0);
            int layoutDimension = obtainStyledAttributes.getLayoutDimension(1, -1);
            if (layoutDimension >= 0) {
                setContentHeight(layoutDimension);
            }
            if (this.mIsSetOpenTheme) {
                if (obtainStyledAttributes.getResourceId(0, 0) == 17304054) {
                    setBackground(getContext().getDrawable(R.drawable.tw_action_bar_background));
                } else {
                    setBackground(obtainStyledAttributes.getDrawable(0));
                }
            } else {
                setBackground(obtainStyledAttributes.getDrawable(0));
            }
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.hideOverflowMenu();
            this.mActionMenuPresenter.hideSubMenus();
        }
    }

    @Override // com.android.internal.widget.AbsActionBarView
    public void setSplitToolbar(boolean z) {
        if (this.mSplitActionBar != z) {
            if (this.mActionMenuPresenter != null) {
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
                if (!z) {
                    this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                    this.mMenuView.setBackground(null);
                    ViewGroup viewGroup = (ViewGroup) this.mMenuView.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(this.mMenuView);
                    }
                    addView(this.mMenuView, layoutParams);
                } else {
                    this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                    this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
                    layoutParams.width = -1;
                    layoutParams.height = this.mContentHeight;
                    this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                    this.mMenuView.setBackground(this.mSplitBackground);
                    ViewGroup viewGroup2 = (ViewGroup) this.mMenuView.getParent();
                    if (viewGroup2 != null) {
                        viewGroup2.removeView(this.mMenuView);
                    }
                    this.mSplitView.addView(this.mMenuView, layoutParams);
                }
            }
            super.setSplitToolbar(z);
        }
    }

    @Override // com.android.internal.widget.AbsActionBarView
    public void setContentHeight(int i) {
        this.mContentHeight = i;
    }

    public void setCustomView(View view) {
        LinearLayout linearLayout;
        View view2 = this.mCustomView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mCustomView = view;
        if (view != null && (linearLayout = this.mTitleLayout) != null) {
            removeView(linearLayout);
            this.mTitleLayout = null;
        }
        if (view != null) {
            addView(view);
        }
        requestLayout();
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        initTitle();
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        initTitle();
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    private void initTitle() {
        if (this.mTitleLayout == null) {
            LayoutInflater.from(getContext()).inflate(R.layout.action_bar_title_item, this);
            LinearLayout linearLayout = (LinearLayout) getChildAt(getChildCount() - 1);
            this.mTitleLayout = linearLayout;
            this.mTitleView = (TextView) linearLayout.findViewById(R.id.action_bar_title);
            this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(R.id.action_bar_subtitle);
            int i = this.mTitleStyleRes;
            if (i != 0) {
                this.mTitleView.setTextAppearance(i);
            }
            int i2 = this.mSubtitleStyleRes;
            if (i2 != 0) {
                this.mSubtitleView.setTextAppearance(i2);
            }
        }
        this.mTitleView.lambda$setTextAsync$0(this.mTitle);
        this.mSubtitleView.lambda$setTextAsync$0(this.mSubtitle);
        boolean isEmpty = TextUtils.isEmpty(this.mTitle);
        boolean isEmpty2 = TextUtils.isEmpty(this.mSubtitle);
        int i3 = 0;
        this.mSubtitleView.setVisibility(!isEmpty2 ? 0 : 8);
        LinearLayout linearLayout2 = this.mTitleLayout;
        if (isEmpty && isEmpty2) {
            i3 = 8;
        }
        linearLayout2.setVisibility(i3);
        if (this.mTitleLayout.getParent() == null) {
            addView(this.mTitleLayout);
        }
    }

    public void initForMode(final ActionMode actionMode) {
        View view = this.mClose;
        if (view == null) {
            View inflate = LayoutInflater.from(this.mContext).inflate(this.mCloseItemLayout, (ViewGroup) this, false);
            this.mClose = inflate;
            addView(inflate);
        } else if (view.getParent() == null) {
            addView(this.mClose);
        }
        View findViewById = this.mClose.findViewById(R.id.action_mode_close_button);
        findViewById.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.internal.widget.ActionBarContextView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                actionMode.finish();
            }
        });
        findViewById.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.internal.widget.ActionBarContextView.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                int i;
                ActionBarContextView.this.getLocationOnScreen(new int[2]);
                int width = ActionBarContextView.this.getWidth();
                int height = ActionBarContextView.this.getHeight();
                int paddingStart = ActionBarContextView.this.getPaddingStart();
                int paddingEnd = ActionBarContextView.this.getPaddingEnd();
                int[] iArr = new int[2];
                ActionBarContextView.this.getLocationInWindow(iArr);
                Rect rect = new Rect();
                ActionBarContextView.this.getWindowVisibleDisplayFrame(rect);
                int i2 = iArr[1] + height;
                if (ActionBarContextView.this.getLayoutDirection() == 0) {
                    i = ((rect.right - rect.left) - (iArr[0] + width)) + (((width - paddingStart) - paddingEnd) / 2) + paddingEnd;
                } else {
                    i = ((iArr[0] + width) - (((width - paddingStart) - paddingEnd) / 2)) - paddingStart;
                }
                ActionBarContextView.this.setTooltipPosition(i, i2);
                return false;
            }
        });
        MenuBuilder menuBuilder = (MenuBuilder) actionMode.getMenu();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus();
        }
        this.mActionMenuPresenter = new ActionMenuPresenter(this.mContext);
        this.mActionMenuPresenter.setReserveOverflow(true);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
        if (!this.mSplitActionBar) {
            menuBuilder.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
            this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
            this.mMenuView.setBackground(null);
            addView(this.mMenuView, layoutParams);
            return;
        }
        this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
        this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
        layoutParams.width = -1;
        layoutParams.height = this.mContentHeight;
        menuBuilder.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
        this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
        this.mMenuView.setBackgroundDrawable(this.mSplitBackground);
        this.mSplitView.addView(this.mMenuView, layoutParams);
    }

    public void closeMode() {
        if (this.mClose == null) {
            killMode();
        }
    }

    public void killMode() {
        removeAllViews();
        if (this.mSplitView != null) {
            this.mSplitView.removeView(this.mMenuView);
        }
        this.mCustomView = null;
        this.mMenuView = null;
    }

    @Override // com.android.internal.widget.AbsActionBarView
    public boolean showOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.showOverflowMenu();
        }
        return false;
    }

    @Override // com.android.internal.widget.AbsActionBarView
    public boolean hideOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.hideOverflowMenu();
        }
        return false;
    }

    @Override // com.android.internal.widget.AbsActionBarView
    public boolean isOverflowMenuShowing() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowing();
        }
        return false;
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_width=\"match_parent\" (or fill_parent)");
        }
        if (View.MeasureSpec.getMode(i2) == 0) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"");
        }
        int size = View.MeasureSpec.getSize(i);
        int size2 = this.mContentHeight > 0 ? this.mContentHeight : View.MeasureSpec.getSize(i2);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int i3 = size2 - paddingTop;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
        View view = this.mClose;
        if (view != null && view.getVisibility() != 8) {
            int measureChildView = measureChildView(this.mClose, paddingLeft, makeMeasureSpec, 0);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
            paddingLeft = measureChildView - (marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
        }
        if (this.mMenuView != null && this.mMenuView.getParent() == this) {
            paddingLeft = measureChildView(this.mMenuView, paddingLeft, makeMeasureSpec, 0);
        }
        if (this.mTitleLayout != null && this.mCustomView == null) {
            if (this.mIsThemeDeviceDefaultFamily) {
                if (this.mTitleView != null) {
                    TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(this.mTitleStyleRes, R.styleable.TextAppearance);
                    TypedValue peekValue = obtainStyledAttributes.peekValue(0);
                    obtainStyledAttributes.recycle();
                    float complexToFloat = TypedValue.complexToFloat(peekValue.data);
                    if (TextUtils.isEmpty(this.mSubtitle)) {
                        float f = getContext().getResources().getConfiguration().fontScale;
                        float f2 = this.mMaxFontScale;
                        if (f > f2) {
                            f = f2;
                        }
                        this.mTitleView.setTextSize(1, complexToFloat * f);
                    } else {
                        this.mTitleView.setTextSize(1, complexToFloat);
                    }
                }
                View view2 = this.mClose;
                if (view2 == null || view2.getVisibility() == 8) {
                    TextView textView = this.mTitleView;
                    if (textView != null && textView.getVisibility() == 0) {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTitleView.getLayoutParams();
                        int dimension = (int) getContext().getResources().getDimension(R.dimen.sem_toolbar_content_inset_start);
                        if (getLayoutDirection() == 0) {
                            layoutParams.leftMargin = dimension;
                        } else {
                            layoutParams.rightMargin = dimension;
                        }
                        this.mTitleView.setLayoutParams(layoutParams);
                    }
                    TextView textView2 = this.mSubtitleView;
                    if (textView2 != null && textView2.getVisibility() == 0) {
                        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mSubtitleView.getLayoutParams();
                        int dimension2 = (int) getContext().getResources().getDimension(R.dimen.sem_toolbar_content_inset_start);
                        if (getLayoutDirection() == 0) {
                            layoutParams2.leftMargin = dimension2;
                        } else {
                            layoutParams2.rightMargin = dimension2;
                        }
                        this.mSubtitleView.setLayoutParams(layoutParams2);
                    }
                }
            }
            if (this.mTitleOptional) {
                this.mTitleLayout.measure(View.MeasureSpec.makeSafeMeasureSpec(size, 0), makeMeasureSpec);
                int measuredWidth = this.mTitleLayout.getMeasuredWidth();
                boolean z = measuredWidth <= paddingLeft;
                if (z) {
                    paddingLeft -= measuredWidth;
                }
                this.mTitleLayout.setVisibility(z ? 0 : 8);
            } else {
                paddingLeft = measureChildView(this.mTitleLayout, paddingLeft, makeMeasureSpec, 0);
            }
        }
        View view3 = this.mCustomView;
        if (view3 != null) {
            ViewGroup.LayoutParams layoutParams3 = view3.getLayoutParams();
            int i4 = layoutParams3.width != -2 ? 1073741824 : Integer.MIN_VALUE;
            if (layoutParams3.width >= 0) {
                paddingLeft = Math.min(layoutParams3.width, paddingLeft);
            }
            int i5 = layoutParams3.height == -2 ? Integer.MIN_VALUE : 1073741824;
            if (layoutParams3.height >= 0) {
                i3 = Math.min(layoutParams3.height, i3);
            }
            this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, i4), View.MeasureSpec.makeMeasureSpec(i3, i5));
        }
        if (this.mContentHeight <= 0) {
            int childCount = getChildCount();
            int i6 = 0;
            for (int i7 = 0; i7 < childCount; i7++) {
                int measuredHeight = getChildAt(i7).getMeasuredHeight() + paddingTop;
                if (measuredHeight > i6) {
                    i6 = measuredHeight;
                }
            }
            setMeasuredDimension(size, i6);
            return;
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean isLayoutRtl = isLayoutRtl();
        int paddingRight = isLayoutRtl ? (i3 - i) - getPaddingRight() : getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingTop2 = ((i4 - i2) - getPaddingTop()) - getPaddingBottom();
        View view = this.mClose;
        if (view != null && view.getVisibility() != 8) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
            int i5 = isLayoutRtl ? marginLayoutParams.rightMargin : marginLayoutParams.leftMargin;
            int i6 = isLayoutRtl ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin;
            int next = next(paddingRight, i5, isLayoutRtl);
            paddingRight = next(next + positionChild(this.mClose, next, paddingTop, paddingTop2, isLayoutRtl), i6, isLayoutRtl);
        }
        int i7 = paddingRight;
        LinearLayout linearLayout = this.mTitleLayout;
        if (linearLayout != null && this.mCustomView == null && linearLayout.getVisibility() != 8) {
            i7 += positionChild(this.mTitleLayout, i7, paddingTop, paddingTop2, isLayoutRtl);
        }
        View view2 = this.mCustomView;
        if (view2 != null) {
            positionChild(view2, i7, paddingTop, paddingTop2, isLayoutRtl);
        }
        int paddingLeft = isLayoutRtl ? getPaddingLeft() : (i3 - i) - getPaddingRight();
        if (this.mMenuView != null) {
            positionChild(this.mMenuView, paddingLeft, paddingTop, paddingTop2, !isLayoutRtl);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 32) {
            accessibilityEvent.setSource(this);
            accessibilityEvent.setClassName(getClass().getName());
            accessibilityEvent.setPackageName(getContext().getPackageName());
            accessibilityEvent.setContentDescription(this.mTitle);
            return;
        }
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
    }

    public void setTitleOptional(boolean z) {
        if (z != this.mTitleOptional) {
            requestLayout();
        }
        this.mTitleOptional = z;
    }

    public boolean isTitleOptional() {
        return this.mTitleOptional;
    }
}
