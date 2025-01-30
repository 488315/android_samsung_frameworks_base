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
import com.android.internal.C4337R;
import com.android.internal.view.menu.MenuBuilder;

/* loaded from: classes5.dex */
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

    public ActionBarContextView(Context context) {
        this(context, null);
    }

    public ActionBarContextView(Context context, AttributeSet attrs) {
        this(context, attrs, 16843668);
    }

    public ActionBarContextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ActionBarContextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        boolean z;
        this.mMaxFontScale = 1.3f;
        TypedArray a = context.obtainStyledAttributes(attrs, C4337R.styleable.ActionMode, defStyleAttr, defStyleRes);
        setBackground(a.getDrawable(0));
        this.mTitleStyleRes = a.getResourceId(2, 0);
        this.mSubtitleStyleRes = a.getResourceId(3, 0);
        this.mContentHeight = a.getLayoutDimension(1, 0);
        this.mSplitBackground = a.getDrawable(4);
        this.mCloseItemLayout = a.getResourceId(5, C4337R.layout.action_mode_close_item);
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, outValue, true);
        if (outValue.data == 0) {
            z = false;
        } else {
            z = true;
        }
        this.mIsThemeDeviceDefaultFamily = z;
        boolean z2 = this.mIsThemeDeviceDefaultFamily && context.getResources().getAssets().getSamsungThemeOverlays().size() > 0;
        this.mIsSetOpenTheme = z2;
        if (z2) {
            if (a.getResourceId(0, 0) == 17303727) {
                setBackground(context.getDrawable(C4337R.drawable.tw_action_bar_background));
            } else if (a.getResourceId(0, 0) == 17303728) {
                setBackground(context.getDrawable(C4337R.drawable.tw_action_bar_background_dark));
            }
        }
        a.recycle();
    }

    @Override // com.android.internal.widget.AbsActionBarView, android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mIsThemeDeviceDefaultFamily) {
            TypedArray a = this.mContext.obtainStyledAttributes(null, C4337R.styleable.ActionMode, 16843668, 0);
            int height = a.getLayoutDimension(1, -1);
            if (height >= 0) {
                setContentHeight(height);
            }
            if (this.mIsSetOpenTheme) {
                if (a.getResourceId(0, 0) == 17303727) {
                    setBackground(getContext().getDrawable(C4337R.drawable.tw_action_bar_background));
                } else {
                    setBackground(a.getDrawable(0));
                }
            } else {
                setBackground(a.getDrawable(0));
            }
            a.recycle();
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
    public void setSplitToolbar(boolean split) {
        if (this.mSplitActionBar != split) {
            if (this.mActionMenuPresenter != null) {
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
                if (!split) {
                    this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                    this.mMenuView.setBackground(null);
                    ViewGroup oldParent = (ViewGroup) this.mMenuView.getParent();
                    if (oldParent != null) {
                        oldParent.removeView(this.mMenuView);
                    }
                    addView(this.mMenuView, layoutParams);
                } else {
                    this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                    this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
                    layoutParams.width = -1;
                    layoutParams.height = this.mContentHeight;
                    this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                    this.mMenuView.setBackground(this.mSplitBackground);
                    ViewGroup oldParent2 = (ViewGroup) this.mMenuView.getParent();
                    if (oldParent2 != null) {
                        oldParent2.removeView(this.mMenuView);
                    }
                    this.mSplitView.addView(this.mMenuView, layoutParams);
                }
            }
            super.setSplitToolbar(split);
        }
    }

    @Override // com.android.internal.widget.AbsActionBarView
    public void setContentHeight(int height) {
        this.mContentHeight = height;
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

    public void setTitle(CharSequence title) {
        this.mTitle = title;
        initTitle();
    }

    public void setSubtitle(CharSequence subtitle) {
        this.mSubtitle = subtitle;
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
            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(C4337R.layout.action_bar_title_item, this);
            LinearLayout linearLayout = (LinearLayout) getChildAt(getChildCount() - 1);
            this.mTitleLayout = linearLayout;
            this.mTitleView = (TextView) linearLayout.findViewById(C4337R.id.action_bar_title);
            this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(C4337R.id.action_bar_subtitle);
            int i = this.mTitleStyleRes;
            if (i != 0) {
                this.mTitleView.setTextAppearance(i);
            }
            int i2 = this.mSubtitleStyleRes;
            if (i2 != 0) {
                this.mSubtitleView.setTextAppearance(i2);
            }
        }
        this.mTitleView.setText(this.mTitle);
        this.mSubtitleView.setText(this.mSubtitle);
        boolean hasTitle = !TextUtils.isEmpty(this.mTitle);
        boolean hasSubtitle = !TextUtils.isEmpty(this.mSubtitle);
        int i3 = 0;
        this.mSubtitleView.setVisibility(hasSubtitle ? 0 : 8);
        LinearLayout linearLayout2 = this.mTitleLayout;
        if (!hasTitle && !hasSubtitle) {
            i3 = 8;
        }
        linearLayout2.setVisibility(i3);
        if (this.mTitleLayout.getParent() == null) {
            addView(this.mTitleLayout);
        }
    }

    public void initForMode(final ActionMode mode) {
        View view = this.mClose;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            View inflate = inflater.inflate(this.mCloseItemLayout, (ViewGroup) this, false);
            this.mClose = inflate;
            addView(inflate);
        } else if (view.getParent() == null) {
            addView(this.mClose);
        }
        View closeButton = this.mClose.findViewById(C4337R.id.action_mode_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.widget.ActionBarContextView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                mode.finish();
            }
        });
        closeButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.internal.widget.ActionBarContextView.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View v) {
                int xOffset;
                int[] screenPos = new int[2];
                ActionBarContextView.this.getLocationOnScreen(screenPos);
                int width = ActionBarContextView.this.getWidth();
                int height = ActionBarContextView.this.getHeight();
                int paddingStart = ActionBarContextView.this.getPaddingStart();
                int paddingEnd = ActionBarContextView.this.getPaddingEnd();
                int[] windowPos = new int[2];
                ActionBarContextView.this.getLocationInWindow(windowPos);
                Rect displayFrame = new Rect();
                ActionBarContextView.this.getWindowVisibleDisplayFrame(displayFrame);
                int yOffset = windowPos[1] + height;
                if (ActionBarContextView.this.getLayoutDirection() == 0) {
                    xOffset = ((((displayFrame.right - displayFrame.left) - (windowPos[0] + width)) + (((width - paddingStart) - paddingEnd) / 2)) + paddingEnd) - 0;
                } else {
                    int xOffset2 = windowPos[0];
                    xOffset = ((xOffset2 + width) - (((width - paddingStart) - paddingEnd) / 2)) - paddingStart;
                }
                ActionBarContextView.this.setTooltipPosition(xOffset, yOffset);
                return false;
            }
        });
        MenuBuilder menu = (MenuBuilder) mode.getMenu();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus();
        }
        this.mActionMenuPresenter = new ActionMenuPresenter(this.mContext);
        this.mActionMenuPresenter.setReserveOverflow(true);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
        if (!this.mSplitActionBar) {
            menu.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
            this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
            this.mMenuView.setBackground(null);
            addView(this.mMenuView, layoutParams);
            return;
        }
        this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
        this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
        layoutParams.width = -1;
        layoutParams.height = this.mContentHeight;
        menu.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
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
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ViewGroup.MarginLayoutParams(getContext(), attrs);
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ce, code lost:
    
        if (r2.getVisibility() == 8) goto L41;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != 1073741824) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_width=\"match_parent\" (or fill_parent)");
        }
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == 0) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"");
        }
        int contentWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = this.mContentHeight > 0 ? this.mContentHeight : View.MeasureSpec.getSize(heightMeasureSpec);
        int verticalPadding = getPaddingTop() + getPaddingBottom();
        int availableWidth = (contentWidth - getPaddingLeft()) - getPaddingRight();
        int height = maxHeight - verticalPadding;
        int childSpecHeight = View.MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE);
        View view = this.mClose;
        if (view != null && view.getVisibility() != 8) {
            int availableWidth2 = measureChildView(this.mClose, availableWidth, childSpecHeight, 0);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
            availableWidth = availableWidth2 - (lp.leftMargin + lp.rightMargin);
        }
        if (this.mMenuView != null && this.mMenuView.getParent() == this) {
            availableWidth = measureChildView(this.mMenuView, availableWidth, childSpecHeight, 0);
        }
        if (this.mTitleLayout != null && this.mCustomView == null) {
            if (!this.mIsThemeDeviceDefaultFamily) {
                i = 8;
            } else {
                if (this.mTitleView != null) {
                    TypedArray appearance = getContext().obtainStyledAttributes(this.mTitleStyleRes, C4337R.styleable.TextAppearance);
                    TypedValue value = appearance.peekValue(0);
                    appearance.recycle();
                    float textSize = TypedValue.complexToFloat(value.data);
                    if (TextUtils.isEmpty(this.mSubtitle)) {
                        float fontScale = getContext().getResources().getConfiguration().fontScale;
                        if (fontScale > this.mMaxFontScale) {
                            fontScale = this.mMaxFontScale;
                        }
                        this.mTitleView.setTextSize(1, textSize * fontScale);
                    } else {
                        this.mTitleView.setTextSize(1, textSize);
                    }
                }
                View view2 = this.mClose;
                if (view2 != null) {
                    i = 8;
                } else {
                    i = 8;
                }
                TextView textView = this.mTitleView;
                if (textView != null && textView.getVisibility() == 0) {
                    LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) this.mTitleView.getLayoutParams();
                    int contentInsetStart = (int) getContext().getResources().getDimension(C4337R.dimen.sem_toolbar_content_inset_start);
                    if (getLayoutDirection() == 0) {
                        lp2.leftMargin = contentInsetStart;
                    } else {
                        lp2.rightMargin = contentInsetStart;
                    }
                    this.mTitleView.setLayoutParams(lp2);
                }
                TextView textView2 = this.mSubtitleView;
                if (textView2 != null && textView2.getVisibility() == 0) {
                    LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) this.mSubtitleView.getLayoutParams();
                    int contentInsetStart2 = (int) getContext().getResources().getDimension(C4337R.dimen.sem_toolbar_content_inset_start);
                    if (getLayoutDirection() == 0) {
                        lp3.leftMargin = contentInsetStart2;
                    } else {
                        lp3.rightMargin = contentInsetStart2;
                    }
                    this.mSubtitleView.setLayoutParams(lp3);
                }
            }
            if (!this.mTitleOptional) {
                availableWidth = measureChildView(this.mTitleLayout, availableWidth, childSpecHeight, 0);
            } else {
                int titleWidthSpec = View.MeasureSpec.makeSafeMeasureSpec(contentWidth, 0);
                this.mTitleLayout.measure(titleWidthSpec, childSpecHeight);
                int titleWidth = this.mTitleLayout.getMeasuredWidth();
                boolean titleFits = titleWidth <= availableWidth;
                if (titleFits) {
                    availableWidth -= titleWidth;
                }
                LinearLayout linearLayout = this.mTitleLayout;
                if (titleFits) {
                    i = 0;
                }
                linearLayout.setVisibility(i);
            }
        }
        View view3 = this.mCustomView;
        if (view3 != null) {
            ViewGroup.LayoutParams lp4 = view3.getLayoutParams();
            int customWidthMode = lp4.width != -2 ? 1073741824 : Integer.MIN_VALUE;
            int customWidth = lp4.width >= 0 ? Math.min(lp4.width, availableWidth) : availableWidth;
            int customHeightMode = lp4.height != -2 ? 1073741824 : Integer.MIN_VALUE;
            int customHeight = lp4.height >= 0 ? Math.min(lp4.height, height) : height;
            View view4 = this.mCustomView;
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(customWidth, customWidthMode);
            int widthMode2 = View.MeasureSpec.makeMeasureSpec(customHeight, customHeightMode);
            view4.measure(makeMeasureSpec, widthMode2);
        }
        int widthMode3 = this.mContentHeight;
        if (widthMode3 <= 0) {
            int measuredHeight = 0;
            int count = getChildCount();
            for (int i2 = 0; i2 < count; i2++) {
                View v = getChildAt(i2);
                int paddedViewHeight = v.getMeasuredHeight() + verticalPadding;
                if (paddedViewHeight > measuredHeight) {
                    measuredHeight = paddedViewHeight;
                }
            }
            setMeasuredDimension(contentWidth, measuredHeight);
            return;
        }
        setMeasuredDimension(contentWidth, maxHeight);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x;
        boolean isLayoutRtl = isLayoutRtl();
        int x2 = isLayoutRtl ? (r - l) - getPaddingRight() : getPaddingLeft();
        int y = getPaddingTop();
        int contentHeight = ((b - t) - getPaddingTop()) - getPaddingBottom();
        View view = this.mClose;
        if (view != null && view.getVisibility() != 8) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
            int startMargin = isLayoutRtl ? lp.rightMargin : lp.leftMargin;
            int endMargin = isLayoutRtl ? lp.leftMargin : lp.rightMargin;
            int x3 = next(x2, startMargin, isLayoutRtl);
            x = next(x3 + positionChild(this.mClose, x3, y, contentHeight, isLayoutRtl), endMargin, isLayoutRtl);
        } else {
            x = x2;
        }
        LinearLayout linearLayout = this.mTitleLayout;
        if (linearLayout != null && this.mCustomView == null && linearLayout.getVisibility() != 8) {
            x += positionChild(this.mTitleLayout, x, y, contentHeight, isLayoutRtl);
        }
        View view2 = this.mCustomView;
        if (view2 != null) {
            int positionChild = x + positionChild(view2, x, y, contentHeight, isLayoutRtl);
        }
        int x4 = isLayoutRtl ? getPaddingLeft() : (r - l) - getPaddingRight();
        if (this.mMenuView != null) {
            int positionChild2 = x4 + positionChild(this.mMenuView, x4, y, contentHeight, !isLayoutRtl);
        }
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent event) {
        if (event.getEventType() == 32) {
            event.setSource(this);
            event.setClassName(getClass().getName());
            event.setPackageName(getContext().getPackageName());
            event.setContentDescription(this.mTitle);
            return;
        }
        super.onInitializeAccessibilityEventInternal(event);
    }

    public void setTitleOptional(boolean titleOptional) {
        if (titleOptional != this.mTitleOptional) {
            requestLayout();
        }
        this.mTitleOptional = titleOptional;
    }

    public boolean isTitleOptional() {
        return this.mTitleOptional;
    }
}
