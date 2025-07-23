package android.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.TtmlUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ActionMenuPresenter;
import android.widget.LinearLayout;
import com.android.internal.R;
import com.android.internal.view.menu.ActionMenuItemView;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;

/* loaded from: classes5.dex */
public class ActionMenuView extends LinearLayout implements MenuBuilder.ItemInvoker, MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private boolean mIsThemeDeviceDefaultFamily;
    private MenuBuilder mMenu;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private int mOriginalEndPadding;
    private int mOriginalOverflowEndPadding;
    private int mOriginalOverflowStartPadding;
    private int mOriginalStartPadding;
    private String mOverflowBadgeText;
    private int mOverflowButtonMinWidth;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override // com.android.internal.view.menu.MenuView
    public int getWindowAnimations() {
        return 0;
    }

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBaselineAligned(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * f);
        this.mGeneratedItemPadding = (int) (f * 4.0f);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        this.mIsThemeDeviceDefaultFamily = typedValue.data != 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.View, 16843480, 0);
        this.mOriginalStartPadding = obtainStyledAttributes.getDimensionPixelSize(68, 0);
        this.mOriginalEndPadding = obtainStyledAttributes.getDimensionPixelSize(69, 0);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.View, 16843510, 0);
        this.mOriginalOverflowStartPadding = obtainStyledAttributes2.getDimensionPixelSize(68, 0);
        this.mOriginalOverflowEndPadding = obtainStyledAttributes2.getDimensionPixelSize(69, 0);
        this.mOverflowButtonMinWidth = obtainStyledAttributes2.getDimensionPixelSize(36, 0);
        obtainStyledAttributes2.recycle();
        this.mOverflowBadgeText = context.getResources().getString(R.string.sem_action_menu_overflow_badge_text_n);
    }

    public void setPopupTheme(int i) {
        if (this.mPopupTheme != i) {
            this.mPopupTheme = i;
            if (i == 0) {
                this.mPopupContext = this.mContext;
            } else {
                this.mPopupContext = new ContextThemeWrapper(this.mContext, i);
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.mPresenter = actionMenuPresenter;
        actionMenuPresenter.setMenuView(this);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.onConfigurationChanged(configuration);
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, R.styleable.View, 16843480, 0);
        this.mOriginalStartPadding = obtainStyledAttributes.getDimensionPixelSize(68, 0);
        this.mOriginalEndPadding = obtainStyledAttributes.getDimensionPixelSize(69, 0);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(null, R.styleable.View, 16843510, 0);
        this.mOriginalOverflowStartPadding = obtainStyledAttributes2.getDimensionPixelSize(68, 0);
        this.mOriginalOverflowEndPadding = obtainStyledAttributes2.getDimensionPixelSize(69, 0);
        this.mOverflowButtonMinWidth = obtainStyledAttributes2.getDimensionPixelSize(36, 0);
        obtainStyledAttributes2.recycle();
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        MenuBuilder menuBuilder;
        boolean z = this.mFormatItems;
        boolean z2 = View.MeasureSpec.getMode(i) == 1073741824;
        this.mFormatItems = z2;
        if (z != z2) {
            this.mFormatItemsWidth = 0;
        }
        int size = View.MeasureSpec.getSize(i);
        if (this.mFormatItems && (menuBuilder = this.mMenu) != null && size != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = size;
            menuBuilder.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (this.mFormatItems && childCount > 0) {
            onMeasureExactFormat(i, i2);
            return;
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            layoutParams.rightMargin = 0;
            layoutParams.leftMargin = 0;
            if (childAt instanceof ActionMenuItemView) {
                childAt.setPaddingRelative(this.mOriginalStartPadding, 0, this.mOriginalEndPadding, 0);
                if (this.mIsThemeDeviceDefaultFamily && i3 == childCount - 1) {
                    ActionMenuItemView actionMenuItemView = (ActionMenuItemView) childAt;
                    if (actionMenuItemView.hasText()) {
                        if (getLayoutDirection() == 0) {
                            layoutParams.rightMargin = this.mOriginalEndPadding;
                            childAt.setLayoutParams(layoutParams);
                        } else {
                            layoutParams.leftMargin = this.mOriginalEndPadding;
                            childAt.setLayoutParams(layoutParams);
                        }
                    } else {
                        actionMenuItemView.setMinWidth(this.mOverflowButtonMinWidth);
                        actionMenuItemView.setLayoutParams(layoutParams);
                        actionMenuItemView.setPaddingRelative(this.mOriginalOverflowStartPadding, 0, this.mOriginalOverflowEndPadding, 0);
                    }
                }
            } else if (layoutParams.isOverflowButton) {
                if (childAt instanceof ActionMenuPresenter.SemOverflowMenuButtonContainer) {
                    View childAt2 = ((ViewGroup) childAt).getChildAt(0);
                    childAt2.setPaddingRelative(this.mOriginalOverflowStartPadding, 0, this.mOriginalOverflowEndPadding, 0);
                    childAt2.setMinimumWidth(this.mOverflowButtonMinWidth);
                    ((TextView) childAt2).setWidth(this.mOverflowButtonMinWidth);
                } else {
                    childAt.setPaddingRelative(this.mOriginalOverflowStartPadding, 0, this.mOriginalOverflowEndPadding, 0);
                    childAt.setMinimumWidth(this.mOverflowButtonMinWidth);
                }
            }
        }
        super.onMeasure(i, i2);
    }

    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v18, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v29 */
    private void onMeasureExactFormat(int i, int i2) {
        int i3;
        int i4;
        ?? r7;
        int i5;
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int childMeasureSpec = getChildMeasureSpec(i2, paddingTop, -2);
        int i6 = size - paddingLeft;
        int i7 = this.mMinCellSize;
        int i8 = i6 / i7;
        int i9 = i6 % i7;
        if (i8 == 0) {
            setMeasuredDimension(i6, 0);
            return;
        }
        int i10 = i7 + (i9 / i8);
        int childCount = getChildCount();
        int i11 = 0;
        int i12 = 0;
        boolean z = false;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        long j = 0;
        while (i12 < childCount) {
            View childAt = getChildAt(i12);
            int i16 = size2;
            if (childAt.getVisibility() != 8) {
                boolean z2 = childAt instanceof ActionMenuItemView;
                i13++;
                if (z2) {
                    int i17 = this.mGeneratedItemPadding;
                    i4 = i8;
                    r7 = 0;
                    childAt.setPadding(i17, 0, i17, 0);
                } else {
                    i4 = i8;
                    r7 = 0;
                }
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                layoutParams.expanded = r7;
                layoutParams.extraPixels = r7;
                layoutParams.cellsUsed = r7;
                layoutParams.expandable = r7;
                layoutParams.leftMargin = r7;
                layoutParams.rightMargin = r7;
                layoutParams.preventEdgeOffset = z2 && ((ActionMenuItemView) childAt).hasText();
                if (this.mIsThemeDeviceDefaultFamily) {
                    layoutParams.preventEdgeOffset = z2;
                }
                int measureChildForCells = measureChildForCells(childAt, i10, layoutParams.isOverflowButton ? 1 : i4, childMeasureSpec, paddingTop);
                i14 = Math.max(i14, measureChildForCells);
                if (layoutParams.expandable) {
                    i15++;
                }
                if (layoutParams.isOverflowButton) {
                    z = true;
                }
                i8 = i4 - measureChildForCells;
                i11 = Math.max(i11, childAt.getMeasuredHeight());
                if (measureChildForCells == 1) {
                    i5 = i10;
                    j |= 1 << i12;
                    i12++;
                    i10 = i5;
                    size2 = i16;
                }
            }
            i5 = i10;
            i12++;
            i10 = i5;
            size2 = i16;
        }
        int i18 = size2;
        int i19 = i10;
        int i20 = i8;
        char c = 2;
        boolean z3 = z && i13 == 2;
        int i21 = i20;
        boolean z4 = false;
        while (i15 > 0 && i21 > 0) {
            int i22 = Integer.MAX_VALUE;
            long j2 = 0;
            char c2 = c;
            int i23 = 0;
            int i24 = 0;
            while (i23 < childCount) {
                int i25 = i23;
                LayoutParams layoutParams2 = (LayoutParams) getChildAt(i23).getLayoutParams();
                boolean z5 = z3;
                if (layoutParams2.expandable) {
                    if (layoutParams2.cellsUsed < i22) {
                        j2 = 1 << i25;
                        i24 = 1;
                        i22 = layoutParams2.cellsUsed;
                    } else if (layoutParams2.cellsUsed == i22) {
                        j2 |= 1 << i25;
                        i24++;
                    }
                }
                i23 = i25 + 1;
                z3 = z5;
            }
            boolean z6 = z3;
            j |= j2;
            if (i24 > i21) {
                break;
            }
            int i26 = i22 + 1;
            int i27 = i21;
            int i28 = 0;
            while (i28 < childCount) {
                View childAt2 = getChildAt(i28);
                LayoutParams layoutParams3 = (LayoutParams) childAt2.getLayoutParams();
                int i29 = i19;
                int i30 = i11;
                long j3 = 1 << i28;
                if ((j2 & j3) == 0) {
                    if (layoutParams3.cellsUsed == i26) {
                        j |= j3;
                    }
                } else {
                    if (z6 && layoutParams3.preventEdgeOffset && i27 == 1 && this.mIsThemeDeviceDefaultFamily) {
                        int i31 = this.mGeneratedItemPadding;
                        childAt2.setPadding(i31 + i29, 0, i31, 0);
                    }
                    layoutParams3.cellsUsed++;
                    layoutParams3.expanded = true;
                    i27--;
                }
                i28++;
                i19 = i29;
                i11 = i30;
            }
            i21 = i27;
            c = c2;
            z3 = z6;
            z4 = true;
        }
        int i32 = i19;
        int i33 = i11;
        boolean z7 = !z && i13 == 1;
        if (i21 <= 0 || j == 0 || (i21 >= i13 - 1 && !z7 && i14 <= 1)) {
            i3 = 0;
        } else {
            float bitCount = Long.bitCount(j);
            if (z7) {
                i3 = 0;
            } else {
                i3 = 0;
                if ((j & 1) != 0 && !((LayoutParams) getChildAt(0).getLayoutParams()).preventEdgeOffset) {
                    bitCount -= 0.5f;
                }
                int i34 = childCount - 1;
                if ((j & (1 << i34)) != 0 && !((LayoutParams) getChildAt(i34).getLayoutParams()).preventEdgeOffset) {
                    bitCount -= 0.5f;
                }
            }
            int i35 = bitCount > 0.0f ? (int) ((i21 * i32) / bitCount) : i3;
            boolean z8 = z4;
            for (int i36 = i3; i36 < childCount; i36++) {
                if ((j & (1 << i36)) != 0) {
                    View childAt3 = getChildAt(i36);
                    LayoutParams layoutParams4 = (LayoutParams) childAt3.getLayoutParams();
                    if (childAt3 instanceof ActionMenuItemView) {
                        layoutParams4.extraPixels = i35;
                        layoutParams4.expanded = true;
                        if (i36 == 0 && !layoutParams4.preventEdgeOffset) {
                            layoutParams4.leftMargin = (-i35) / 2;
                        }
                        z8 = true;
                    } else if (layoutParams4.isOverflowButton) {
                        layoutParams4.extraPixels = i35;
                        layoutParams4.expanded = true;
                        layoutParams4.rightMargin = (-i35) / 2;
                        z8 = true;
                    } else {
                        if (i36 != 0) {
                            layoutParams4.leftMargin = i35 / 2;
                        }
                        if (i36 != childCount - 1) {
                            layoutParams4.rightMargin = i35 / 2;
                        }
                    }
                }
            }
            z4 = z8;
        }
        if (z4) {
            for (int i37 = i3; i37 < childCount; i37++) {
                View childAt4 = getChildAt(i37);
                LayoutParams layoutParams5 = (LayoutParams) childAt4.getLayoutParams();
                if (layoutParams5.expanded) {
                    childAt4.measure(View.MeasureSpec.makeMeasureSpec((layoutParams5.cellsUsed * i32) + layoutParams5.extraPixels, 1073741824), childMeasureSpec);
                }
            }
        }
        setMeasuredDimension(i6, mode != 1073741824 ? i33 : i18);
    }

    static int measureChildForCells(View view, int i, int i2, int i3, int i4) {
        int i5;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3) - i4, View.MeasureSpec.getMode(i3));
        ActionMenuItemView actionMenuItemView = view instanceof ActionMenuItemView ? (ActionMenuItemView) view : null;
        boolean z = actionMenuItemView != null && actionMenuItemView.hasText();
        if (i2 > 0) {
            i5 = 2;
            if (!z || i2 >= 2) {
                view.measure(View.MeasureSpec.makeMeasureSpec(i2 * i, Integer.MIN_VALUE), makeMeasureSpec);
                int measuredWidth = view.getMeasuredWidth();
                int i6 = measuredWidth / i;
                if (measuredWidth % i != 0) {
                    i6++;
                }
                if (!z || i6 >= 2) {
                    i5 = i6;
                }
                layoutParams.expandable = layoutParams.isOverflowButton && z;
                layoutParams.cellsUsed = i5;
                view.measure(View.MeasureSpec.makeMeasureSpec(i * i5, 1073741824), makeMeasureSpec);
                return i5;
            }
        }
        i5 = 0;
        layoutParams.expandable = layoutParams.isOverflowButton && z;
        layoutParams.cellsUsed = i5;
        view.measure(View.MeasureSpec.makeMeasureSpec(i * i5, 1073741824), makeMeasureSpec);
        return i5;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int width;
        int i5;
        if (!this.mFormatItems) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int childCount = getChildCount();
        int i6 = (i4 - i2) / 2;
        int dividerWidth = getDividerWidth();
        int i7 = i3 - i;
        int paddingRight = (i7 - getPaddingRight()) - getPaddingLeft();
        boolean isLayoutRtl = isLayoutRtl();
        int i8 = 0;
        int i9 = 0;
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isOverflowButton) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    if (hasDividerBeforeChildAt(i10)) {
                        measuredWidth += dividerWidth;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (isLayoutRtl) {
                        i5 = getPaddingLeft() + layoutParams.leftMargin;
                        width = i5 + measuredWidth;
                    } else {
                        width = (getWidth() - getPaddingRight()) - layoutParams.rightMargin;
                        i5 = width - measuredWidth;
                    }
                    int i11 = i6 - (measuredHeight / 2);
                    childAt.layout(i5, i11, width, measuredHeight + i11);
                    paddingRight -= measuredWidth;
                    i8 = 1;
                } else {
                    paddingRight -= (childAt.getMeasuredWidth() + layoutParams.leftMargin) + layoutParams.rightMargin;
                    hasDividerBeforeChildAt(i10);
                    i9++;
                }
            }
        }
        if (childCount == 1 && i8 == 0) {
            View childAt2 = getChildAt(0);
            int measuredWidth2 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i12 = (i7 / 2) - (measuredWidth2 / 2);
            int i13 = i6 - (measuredHeight2 / 2);
            childAt2.layout(i12, i13, measuredWidth2 + i12, measuredHeight2 + i13);
            return;
        }
        int i14 = i9 - (i8 ^ 1);
        int max = Math.max(0, i14 > 0 ? paddingRight / i14 : 0);
        if (isLayoutRtl) {
            int width2 = getWidth() - getPaddingRight();
            for (int i15 = 0; i15 < childCount; i15++) {
                View childAt3 = getChildAt(i15);
                LayoutParams layoutParams2 = (LayoutParams) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !layoutParams2.isOverflowButton) {
                    int i16 = width2 - layoutParams2.rightMargin;
                    int measuredWidth3 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i17 = i6 - (measuredHeight3 / 2);
                    childAt3.layout(i16 - measuredWidth3, i17, i16, measuredHeight3 + i17);
                    width2 = i16 - ((measuredWidth3 + layoutParams2.leftMargin) + max);
                }
            }
            return;
        }
        int paddingLeft = getPaddingLeft();
        for (int i18 = 0; i18 < childCount; i18++) {
            View childAt4 = getChildAt(i18);
            LayoutParams layoutParams3 = (LayoutParams) childAt4.getLayoutParams();
            if (childAt4.getVisibility() != 8 && !layoutParams3.isOverflowButton) {
                int i19 = paddingLeft + layoutParams3.leftMargin;
                int measuredWidth4 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i20 = i6 - (measuredHeight4 / 2);
                childAt4.layout(i19, i20, i19 + measuredWidth4, measuredHeight4 + i20);
                paddingLeft = i19 + measuredWidth4 + layoutParams3.rightMargin + max;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public void setOverflowIcon(Drawable drawable) {
        getMenu();
        this.mPresenter.setOverflowIcon(drawable);
    }

    public Drawable getOverflowIcon() {
        getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public void setOverflowReserved(boolean z) {
        this.mReserveOverflow = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        return layoutParams;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutParams layoutParams2;
        if (layoutParams != null) {
            if (layoutParams instanceof LayoutParams) {
                layoutParams2 = new LayoutParams((LayoutParams) layoutParams);
            } else {
                layoutParams2 = new LayoutParams(layoutParams);
            }
            if (layoutParams2.gravity <= 0) {
                layoutParams2.gravity = 16;
            }
            return layoutParams2;
        }
        return generateDefaultLayoutParams();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null && (layoutParams instanceof LayoutParams);
    }

    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
        generateDefaultLayoutParams.isOverflowButton = true;
        return generateDefaultLayoutParams;
    }

    @Override // com.android.internal.view.menu.MenuBuilder.ItemInvoker
    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        MenuBuilder menuBuilder = this.mMenu;
        if (menuBuilder != null) {
            return menuBuilder.performItemAction(menuItemImpl, 0);
        }
        return false;
    }

    @Override // com.android.internal.view.menu.MenuView
    public void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    public Menu getMenu() {
        if (this.mMenu == null) {
            Context context = getContext();
            MenuBuilder menuBuilder = new MenuBuilder(context);
            this.mMenu = menuBuilder;
            menuBuilder.setCallback(new MenuBuilderCallback());
            ActionMenuPresenter actionMenuPresenter = new ActionMenuPresenter(context);
            this.mPresenter = actionMenuPresenter;
            actionMenuPresenter.setReserveOverflow(true);
            ActionMenuPresenter actionMenuPresenter2 = this.mPresenter;
            MenuPresenter.Callback callback = this.mActionMenuPresenterCallback;
            if (callback == null) {
                callback = new ActionMenuPresenterCallback();
            }
            actionMenuPresenter2.setCallback(callback);
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
    }

    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public boolean showOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowPending();
    }

    public void dismissPopupMenus() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.dismissPopupMenus();
        }
    }

    @Override // android.widget.LinearLayout
    protected boolean hasDividerBeforeChildAt(int i) {
        boolean z = false;
        if (i == 0) {
            return false;
        }
        KeyEvent.Callback childAt = getChildAt(i - 1);
        KeyEvent.Callback childAt2 = getChildAt(i);
        if (i < getChildCount() && (childAt instanceof ActionMenuChildView)) {
            z = ((ActionMenuChildView) childAt).needsDividerAfter();
        }
        return (i <= 0 || !(childAt2 instanceof ActionMenuChildView)) ? z : ((ActionMenuChildView) childAt2).needsDividerBefore() | z;
    }

    public void setExpandedActionViewsExclusive(boolean z) {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.setExpandedActionViewsExclusive(z);
        }
    }

    private class MenuBuilderCallback implements MenuBuilder.Callback {
        private MenuBuilderCallback() {
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menuBuilder);
            }
        }
    }

    private class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            return false;
        }

        private ActionMenuPresenterCallback(ActionMenuView actionMenuView) {
        }
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public int cellsUsed;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public boolean expandable;
        public boolean expanded;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public int extraPixels;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public boolean isOverflowButton;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public boolean preventEdgeOffset;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((LinearLayout.LayoutParams) layoutParams);
            this.isOverflowButton = layoutParams.isOverflowButton;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.isOverflowButton = false;
        }

        public LayoutParams(int i, int i2, boolean z) {
            super(i, i2);
            this.isOverflowButton = z;
        }

        @Override // android.widget.LinearLayout.LayoutParams, android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:overFlowButton", this.isOverflowButton);
            viewHierarchyEncoder.addProperty("layout:cellsUsed", this.cellsUsed);
            viewHierarchyEncoder.addProperty("layout:extraPixels", this.extraPixels);
            viewHierarchyEncoder.addProperty("layout:expandable", this.expandable);
            viewHierarchyEncoder.addProperty("layout:preventEdgeOffset", this.preventEdgeOffset);
        }
    }

    int semGetSumOfDigitsInBadges() {
        if (this.mMenu == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mMenu.size(); i2++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mMenu.getItem(i2);
            if (menuItemImpl.isVisible()) {
                i += getNumericValue(menuItemImpl.getBadgeText());
            }
        }
        return i;
    }

    private int getNumericValue(String str) {
        if (str == null) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return 1;
        }
    }

    public void setOverflowBadgeText(String str) {
        this.mOverflowBadgeText = str;
    }

    public String getOverflowBadgeText() {
        return this.mOverflowBadgeText;
    }
}
