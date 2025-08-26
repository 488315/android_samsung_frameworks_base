package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.ToolbarActionBar;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.reflect.os.SeslBuildReflector$SeslVersionReflector;
import com.android.systemui.R;
import java.util.Iterator;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mActionButtonPaddingEnd;
    public int mActionButtonPaddingStart;
    public ToolbarActionBar.ActionMenuPresenterCallback mActionMenuPresenterCallback;
    public boolean mFormatItems;
    public int mFormatItemsWidth;
    public final int mGeneratedItemPadding;
    public final boolean mIsOneUI41;
    public int mLastItemEndPadding;
    public MenuBuilder mMenu;
    public MenuBuilder.Callback mMenuBuilderCallback;
    public final int mMinCellSize;
    public Toolbar.AnonymousClass1 mOnMenuItemClickListener;
    public final String mOverflowBadgeText;
    public int mOverflowButtonMinWidth;
    public int mOverflowButtonPaddingEnd;
    public int mOverflowButtonPaddingStart;
    public Context mPopupContext;
    public int mPopupTheme;
    public ActionMenuPresenter mPresenter;
    public boolean mReserveOverflow;

    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    public class LayoutParams extends LinearLayoutCompat.LayoutParams {
        public int cellsUsed;
        public boolean expandable;
        public boolean expanded;
        public int extraPixels;
        public boolean isOverflowButton;
        public boolean preventEdgeOffset;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams) layoutParams);
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
    }

    public class MenuBuilderCallback implements MenuBuilder.Callback {
        public MenuBuilderCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            boolean z;
            boolean zOnMenuItemSelected;
            Toolbar.AnonymousClass1 anonymousClass1 = ActionMenuView.this.mOnMenuItemClickListener;
            if (anonymousClass1 != null) {
                Toolbar toolbar = Toolbar.this;
                Iterator it = toolbar.mMenuHostHelper.mMenuProviders.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    if (FragmentManager.this.dispatchOptionsItemSelected(menuItem)) {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    zOnMenuItemSelected = true;
                } else {
                    ToolbarActionBar.AnonymousClass2 anonymousClass2 = toolbar.mOnMenuItemClickListener;
                    zOnMenuItemSelected = anonymousClass2 != null ? ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, menuItem) : false;
                }
                if (zOnMenuItemSelected) {
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public final void onMenuModeChange(MenuBuilder menuBuilder) {
            MenuBuilder.Callback callback = ActionMenuView.this.mMenuBuilderCallback;
            if (callback != null) {
                callback.onMenuModeChange(menuBuilder);
            }
        }
    }

    public ActionMenuView(Context context) {
        this(context, null);
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.View
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        ((LinearLayout.LayoutParams) layoutParams).gravity = 16;
        return layoutParams;
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateLayoutParams(layoutParams);
    }

    public final MenuBuilder getMenu() {
        if (this.mMenu == null) {
            Context context = getContext();
            MenuBuilder menuBuilder = new MenuBuilder(context);
            this.mMenu = menuBuilder;
            menuBuilder.mCallback = new MenuBuilderCallback();
            ActionMenuPresenter actionMenuPresenter = new ActionMenuPresenter(context);
            this.mPresenter = actionMenuPresenter;
            actionMenuPresenter.mReserveOverflow = true;
            actionMenuPresenter.mReserveOverflowSet = true;
            MenuPresenter.Callback actionMenuPresenterCallback = this.mActionMenuPresenterCallback;
            if (actionMenuPresenterCallback == null) {
                actionMenuPresenterCallback = new ActionMenuPresenterCallback();
            }
            actionMenuPresenter.mCallback = actionMenuPresenterCallback;
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            ActionMenuPresenter actionMenuPresenter2 = this.mPresenter;
            actionMenuPresenter2.mMenuView = this;
            this.mMenu = actionMenuPresenter2.mMenu;
        }
        return this.mMenu;
    }

    public final boolean hasSupportDividerBeforeChildAt(int i) {
        boolean zNeedsDividerAfter = false;
        if (i == 0) {
            return false;
        }
        KeyEvent.Callback childAt = getChildAt(i - 1);
        KeyEvent.Callback childAt2 = getChildAt(i);
        if (i < getChildCount() && (childAt instanceof ActionMenuChildView)) {
            zNeedsDividerAfter = ((ActionMenuChildView) childAt).needsDividerAfter();
        }
        return (i <= 0 || !(childAt2 instanceof ActionMenuChildView)) ? zNeedsDividerAfter : ((ActionMenuChildView) childAt2).needsDividerBefore() | zNeedsDividerAfter;
    }

    @Override // androidx.appcompat.view.menu.MenuView
    public final void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.ItemInvoker
    public final boolean invokeItem(MenuItemImpl menuItemImpl) {
        MenuBuilder menuBuilder = this.mMenu;
        if (menuBuilder != null) {
            return menuBuilder.performItemAction(menuItemImpl, null, 0);
        }
        return false;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.onConfigurationChanged();
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
        Context context = getContext();
        int[] iArr = R$styleable.View;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, iArr, R.attr.actionButtonStyle, 0);
        this.mActionButtonPaddingStart = typedArrayObtainStyledAttributes.getDimensionPixelSize(7, 0);
        this.mActionButtonPaddingEnd = typedArrayObtainStyledAttributes.getDimensionPixelSize(6, 0);
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(null, iArr, R.attr.actionOverflowButtonStyle, 0);
        this.mOverflowButtonPaddingStart = typedArrayObtainStyledAttributes2.getDimensionPixelSize(7, 0);
        this.mOverflowButtonPaddingEnd = typedArrayObtainStyledAttributes2.getDimensionPixelSize(6, 0);
        this.mOverflowButtonMinWidth = typedArrayObtainStyledAttributes2.getDimensionPixelSize(3, 0);
        typedArrayObtainStyledAttributes2.recycle();
        if (this.mIsOneUI41) {
            this.mActionButtonPaddingStart = getResources().getDimensionPixelSize(R.dimen.sesl_action_button_side_padding);
            this.mActionButtonPaddingEnd = getResources().getDimensionPixelSize(R.dimen.sesl_action_button_side_padding);
            this.mOverflowButtonPaddingStart = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_overflow_side_padding);
            this.mOverflowButtonPaddingEnd = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_overflow_padding_end);
        }
        this.mLastItemEndPadding = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_last_padding);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.hideOverflowMenu();
            ActionMenuPresenter.ActionButtonSubmenu actionButtonSubmenu = actionMenuPresenter.mActionButtonPopup;
            if (actionButtonSubmenu == null || !actionButtonSubmenu.isShowing()) {
                return;
            }
            actionButtonSubmenu.mPopup.dismiss();
        }
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int width;
        int paddingLeft;
        if (!this.mFormatItems) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int childCount = getChildCount();
        int i5 = (i4 - i2) / 2;
        int i6 = this.mDividerWidth;
        int i7 = i3 - i;
        int paddingRight = (i7 - getPaddingRight()) - getPaddingLeft();
        boolean z2 = getLayoutDirection() == 1;
        int i8 = 0;
        int i9 = 0;
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isOverflowButton) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    if (hasSupportDividerBeforeChildAt(i10)) {
                        measuredWidth += i6;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (z2) {
                        paddingLeft = getPaddingLeft() + ((LinearLayout.LayoutParams) layoutParams).leftMargin;
                        width = paddingLeft + measuredWidth;
                    } else {
                        width = (getWidth() - getPaddingRight()) - ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                        paddingLeft = width - measuredWidth;
                    }
                    int i11 = i5 - (measuredHeight / 2);
                    childAt.layout(paddingLeft, i11, width, measuredHeight + i11);
                    paddingRight -= measuredWidth;
                    i8 = 1;
                } else {
                    paddingRight -= (childAt.getMeasuredWidth() + ((LinearLayout.LayoutParams) layoutParams).leftMargin) + ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                    hasSupportDividerBeforeChildAt(i10);
                    i9++;
                }
            }
        }
        if (childCount == 1 && i8 == 0) {
            View childAt2 = getChildAt(0);
            int measuredWidth2 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i12 = (i7 / 2) - (measuredWidth2 / 2);
            int i13 = i5 - (measuredHeight2 / 2);
            childAt2.layout(i12, i13, measuredWidth2 + i12, measuredHeight2 + i13);
            return;
        }
        int i14 = i9 - (i8 ^ 1);
        int iMax = Math.max(0, i14 > 0 ? paddingRight / i14 : 0);
        if (z2) {
            int width2 = getWidth() - getPaddingRight();
            for (int i15 = 0; i15 < childCount; i15++) {
                View childAt3 = getChildAt(i15);
                LayoutParams layoutParams2 = (LayoutParams) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !layoutParams2.isOverflowButton) {
                    int i16 = width2 - ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                    int measuredWidth3 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i17 = i5 - (measuredHeight3 / 2);
                    childAt3.layout(i16 - measuredWidth3, i17, i16, measuredHeight3 + i17);
                    width2 = i16 - ((measuredWidth3 + ((LinearLayout.LayoutParams) layoutParams2).leftMargin) + iMax);
                }
            }
            return;
        }
        int paddingLeft2 = getPaddingLeft();
        for (int i18 = 0; i18 < childCount; i18++) {
            View childAt4 = getChildAt(i18);
            LayoutParams layoutParams3 = (LayoutParams) childAt4.getLayoutParams();
            if (childAt4.getVisibility() != 8 && !layoutParams3.isOverflowButton) {
                int i19 = paddingLeft2 + ((LinearLayout.LayoutParams) layoutParams3).leftMargin;
                int measuredWidth4 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i20 = i5 - (measuredHeight4 / 2);
                childAt4.layout(i19, i20, i19 + measuredWidth4, measuredHeight4 + i20);
                paddingLeft2 = measuredWidth4 + ((LinearLayout.LayoutParams) layoutParams3).rightMargin + iMax + i19;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v27, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v33 */
    /* JADX WARN: Type inference failed for: r1v42 */
    /* JADX WARN: Type inference failed for: r1v43, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v44 */
    /* JADX WARN: Type inference failed for: r1v45, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v50 */
    /* JADX WARN: Type inference failed for: r1v51, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v57 */
    /* JADX WARN: Type inference failed for: r1v67 */
    /* JADX WARN: Type inference failed for: r1v68 */
    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        long j;
        int i5;
        boolean z;
        ?? r1;
        boolean z2;
        ?? r12;
        int i6;
        int i7;
        int i8;
        int i9;
        MenuBuilder menuBuilder;
        boolean z3 = this.mFormatItems;
        boolean z4 = View.MeasureSpec.getMode(i) == 1073741824;
        this.mFormatItems = z4;
        if (z3 != z4) {
            this.mFormatItemsWidth = 0;
        }
        int size = View.MeasureSpec.getSize(i);
        if (this.mFormatItems && (menuBuilder = this.mMenu) != null && size != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = size;
            menuBuilder.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (!this.mFormatItems || childCount <= 0) {
            for (int i10 = 0; i10 < childCount; i10++) {
                View childAt = getChildAt(i10);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                ((LinearLayout.LayoutParams) layoutParams).rightMargin = 0;
                ((LinearLayout.LayoutParams) layoutParams).leftMargin = 0;
                if (childAt instanceof ActionMenuItemView) {
                    int i11 = this.mActionButtonPaddingStart;
                    int i12 = this.mActionButtonPaddingEnd;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    childAt.setPaddingRelative(i11, 0, i12, 0);
                    int i13 = childCount - 1;
                    if (i10 == i13) {
                        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) childAt;
                        if (actionMenuItemView.hasText()) {
                            if (getLayoutDirection() == 0) {
                                ((LinearLayout.LayoutParams) layoutParams).rightMargin = this.mLastItemEndPadding;
                                childAt.setLayoutParams(layoutParams);
                            } else {
                                ((LinearLayout.LayoutParams) layoutParams).leftMargin = this.mLastItemEndPadding;
                                childAt.setLayoutParams(layoutParams);
                            }
                        } else if (this.mIsOneUI41) {
                            childAt.setLayoutParams(layoutParams);
                            childAt.setPaddingRelative(this.mActionButtonPaddingStart, 0, this.mOverflowButtonPaddingEnd, 0);
                        } else {
                            actionMenuItemView.setMinWidth(this.mOverflowButtonMinWidth);
                            childAt.setLayoutParams(layoutParams);
                            childAt.setPaddingRelative(this.mOverflowButtonPaddingStart, 0, this.mOverflowButtonPaddingEnd, 0);
                        }
                    } else if (i10 < i13) {
                        ((ActionMenuItemView) childAt).hasText();
                    }
                } else if (layoutParams.isOverflowButton) {
                    if (childAt instanceof ActionMenuPresenter.OverflowMenuButton) {
                        ViewGroup viewGroup = (ViewGroup) childAt;
                        viewGroup.getChildAt(0).setPaddingRelative(this.mOverflowButtonPaddingStart, 0, this.mOverflowButtonPaddingEnd, 0);
                        viewGroup.getChildAt(0).setMinimumWidth(this.mOverflowButtonMinWidth);
                    } else {
                        int i14 = this.mOverflowButtonPaddingStart;
                        int i15 = this.mOverflowButtonPaddingEnd;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        childAt.setPaddingRelative(i14, 0, i15, 0);
                        childAt.setMinimumWidth(this.mOverflowButtonMinWidth);
                    }
                }
            }
            super.onMeasure(i, i2);
            return;
        }
        int mode = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i);
        int size3 = View.MeasureSpec.getSize(i2);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, paddingBottom, -2);
        int i16 = size2 - paddingRight;
        int i17 = this.mMinCellSize;
        int i18 = i16 / i17;
        int i19 = i16 % i17;
        if (i18 == 0) {
            setMeasuredDimension(i16, 0);
            return;
        }
        int i20 = (i19 / i18) + i17;
        int childCount2 = getChildCount();
        int i21 = 0;
        int iMax = 0;
        int i22 = 0;
        int i23 = 0;
        int i24 = 0;
        int i25 = 1;
        long j2 = 0;
        int iMax2 = 0;
        while (i22 < childCount2) {
            View childAt2 = getChildAt(i22);
            if (childAt2.getVisibility() == 8) {
                i6 = size3;
                i7 = paddingBottom;
                i9 = i25;
            } else {
                boolean z5 = childAt2 instanceof ActionMenuItemView;
                i21++;
                if (z5) {
                    int i26 = this.mGeneratedItemPadding;
                    z2 = z5;
                    r12 = 0;
                    childAt2.setPadding(i26, 0, i26, 0);
                } else {
                    z2 = z5;
                    r12 = 0;
                }
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                layoutParams2.expanded = r12;
                layoutParams2.extraPixels = r12;
                layoutParams2.cellsUsed = r12;
                layoutParams2.expandable = r12;
                ((LinearLayout.LayoutParams) layoutParams2).leftMargin = r12;
                ((LinearLayout.LayoutParams) layoutParams2).rightMargin = r12;
                layoutParams2.preventEdgeOffset = (z2 && ((ActionMenuItemView) childAt2).hasText()) ? i25 : 0;
                int i27 = layoutParams2.isOverflowButton ? i25 : i18;
                i6 = size3;
                LayoutParams layoutParams3 = (LayoutParams) childAt2.getLayoutParams();
                i7 = paddingBottom;
                int i28 = i18;
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(childMeasureSpec) - i7, View.MeasureSpec.getMode(childMeasureSpec));
                ActionMenuItemView actionMenuItemView2 = z2 ? (ActionMenuItemView) childAt2 : null;
                int i29 = (actionMenuItemView2 == null || !actionMenuItemView2.hasText()) ? 0 : i25;
                int i30 = i29;
                if (i27 <= 0 || (i29 != 0 && i27 < 2)) {
                    i8 = 0;
                } else {
                    childAt2.measure(View.MeasureSpec.makeMeasureSpec(i27 * i20, Integer.MIN_VALUE), iMakeMeasureSpec);
                    int measuredWidth = childAt2.getMeasuredWidth();
                    i8 = measuredWidth / i20;
                    if (measuredWidth % i20 != 0) {
                        i8++;
                    }
                    if (i30 != 0 && i8 < 2) {
                        i8 = 2;
                    }
                }
                layoutParams3.expandable = (layoutParams3.isOverflowButton || i30 == 0) ? 0 : i25;
                layoutParams3.cellsUsed = i8;
                childAt2.measure(View.MeasureSpec.makeMeasureSpec(i8 * i20, 1073741824), iMakeMeasureSpec);
                iMax = Math.max(iMax, i8);
                if (layoutParams2.expandable) {
                    i24++;
                }
                if (layoutParams2.isOverflowButton) {
                    i23 = i25;
                }
                int i31 = i28 - i8;
                iMax2 = Math.max(iMax2, childAt2.getMeasuredHeight());
                i9 = i25;
                if (i8 == i9) {
                    j2 |= i9 << i22;
                }
                i18 = i31;
            }
            i22 += i9;
            i25 = i9;
            size3 = i6;
            paddingBottom = i7;
        }
        int i32 = size3;
        int i33 = i18;
        boolean z6 = i23 != 0 && i21 == 2;
        int i34 = i33;
        int i35 = 0;
        while (i24 > 0 && i34 > 0) {
            int i36 = Integer.MAX_VALUE;
            long j3 = 0;
            int i37 = 0;
            int i38 = 0;
            j = 1;
            while (i37 < childCount2) {
                LayoutParams layoutParams4 = (LayoutParams) getChildAt(i37).getLayoutParams();
                boolean z7 = z6;
                if (layoutParams4.expandable) {
                    int i39 = layoutParams4.cellsUsed;
                    if (i39 < i36) {
                        j3 = 1 << i37;
                        i36 = i39;
                        i38 = 1;
                    } else if (i39 == i36) {
                        j3 |= 1 << i37;
                        i38++;
                    }
                }
                i37++;
                z6 = z7;
            }
            boolean z8 = z6;
            int i40 = 1;
            j2 |= j3;
            if (i38 > i34) {
                i3 = mode;
                i4 = 1;
                break;
            }
            int i41 = i36 + 1;
            int i42 = 0;
            while (i42 < childCount2) {
                View childAt3 = getChildAt(i42);
                LayoutParams layoutParams5 = (LayoutParams) childAt3.getLayoutParams();
                int i43 = i42;
                int i44 = mode;
                long j4 = i40 << i42;
                if ((j3 & j4) == 0) {
                    if (layoutParams5.cellsUsed == i41) {
                        j2 |= j4;
                    }
                    r1 = 1;
                } else {
                    if (!z8 || !layoutParams5.preventEdgeOffset) {
                        z = true;
                    } else if (i34 == 1) {
                        int i45 = this.mGeneratedItemPadding;
                        z = true;
                        childAt3.setPadding(i45 + i20, 0, i45, 0);
                    } else {
                        z = true;
                    }
                    layoutParams5.cellsUsed++;
                    r1 = z;
                    layoutParams5.expanded = r1;
                    i34--;
                }
                i40 = r1;
                i42 = i43 + 1;
                mode = i44;
            }
            i35 = i40;
            z6 = z8;
        }
        i3 = mode;
        i4 = 1;
        j = 1;
        int i46 = (i23 == 0 && i21 == i4) ? i4 : 0;
        if (i34 > 0 && j2 != 0 && (i34 < i21 - i4 || i46 != 0 || iMax > i4)) {
            float fBitCount = Long.bitCount(j2);
            if (i46 == 0) {
                if ((j2 & j) != 0 && !((LayoutParams) getChildAt(0).getLayoutParams()).preventEdgeOffset) {
                    fBitCount -= 0.5f;
                }
                int i47 = childCount2 - 1;
                if ((j2 & (1 << i47)) != 0 && !((LayoutParams) getChildAt(i47).getLayoutParams()).preventEdgeOffset) {
                    fBitCount -= 0.5f;
                }
            }
            int i48 = fBitCount > 0.0f ? (int) ((i34 * i20) / fBitCount) : 0;
            int i49 = 0;
            while (i49 < childCount2) {
                if ((j2 & (1 << i49)) == 0) {
                    i5 = 1;
                } else {
                    View childAt4 = getChildAt(i49);
                    LayoutParams layoutParams6 = (LayoutParams) childAt4.getLayoutParams();
                    if (childAt4 instanceof ActionMenuItemView) {
                        layoutParams6.extraPixels = i48;
                        layoutParams6.expanded = true;
                        if (i49 == 0 && !layoutParams6.preventEdgeOffset) {
                            ((LinearLayout.LayoutParams) layoutParams6).leftMargin = (-i48) / 2;
                        }
                        i35 = 1;
                        i5 = 1;
                    } else if (layoutParams6.isOverflowButton) {
                        layoutParams6.extraPixels = i48;
                        i5 = 1;
                        layoutParams6.expanded = true;
                        ((LinearLayout.LayoutParams) layoutParams6).rightMargin = (-i48) / 2;
                        i35 = 1;
                    } else {
                        i5 = 1;
                        if (i49 != 0) {
                            ((LinearLayout.LayoutParams) layoutParams6).leftMargin = i48 / 2;
                        }
                        if (i49 != childCount2 - 1) {
                            ((LinearLayout.LayoutParams) layoutParams6).rightMargin = i48 / 2;
                        }
                    }
                }
                i49 += i5;
            }
        }
        if (i35 != 0) {
            for (int i50 = 0; i50 < childCount2; i50++) {
                View childAt5 = getChildAt(i50);
                LayoutParams layoutParams7 = (LayoutParams) childAt5.getLayoutParams();
                if (layoutParams7.expanded) {
                    childAt5.measure(View.MeasureSpec.makeMeasureSpec((layoutParams7.cellsUsed * i20) + layoutParams7.extraPixels, 1073741824), childMeasureSpec);
                }
            }
        }
        setMeasuredDimension(i16, i3 != 1073741824 ? iMax2 : i32);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBaselineAligned = false;
        float f = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * f);
        this.mGeneratedItemPadding = (int) (f * 4.0f);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
        boolean z = SeslBuildReflector$SeslVersionReflector.getField_SEM_PLATFORM_INT() >= 130100;
        this.mIsOneUI41 = z;
        int[] iArr = R$styleable.View;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, R.attr.actionButtonStyle, 0);
        this.mActionButtonPaddingStart = typedArrayObtainStyledAttributes.getDimensionPixelSize(7, 0);
        this.mActionButtonPaddingEnd = typedArrayObtainStyledAttributes.getDimensionPixelSize(6, 0);
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr, R.attr.actionOverflowButtonStyle, 0);
        this.mOverflowButtonPaddingStart = typedArrayObtainStyledAttributes2.getDimensionPixelSize(7, 0);
        this.mOverflowButtonPaddingEnd = typedArrayObtainStyledAttributes2.getDimensionPixelSize(6, 0);
        this.mOverflowButtonMinWidth = typedArrayObtainStyledAttributes2.getDimensionPixelSize(3, 0);
        typedArrayObtainStyledAttributes2.recycle();
        this.mOverflowBadgeText = context.getResources().getString(R.string.sesl_action_menu_overflow_badge_text_n);
        if (z) {
            this.mActionButtonPaddingStart = getResources().getDimensionPixelSize(R.dimen.sesl_action_button_side_padding);
            this.mActionButtonPaddingEnd = getResources().getDimensionPixelSize(R.dimen.sesl_action_button_side_padding);
            this.mOverflowButtonPaddingStart = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_overflow_side_padding);
            this.mOverflowButtonPaddingEnd = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_overflow_padding_end);
        }
        this.mLastItemEndPadding = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_last_padding);
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ LinearLayoutCompat.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateLayoutParams(layoutParams);
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final LinearLayoutCompat.LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        ((LinearLayout.LayoutParams) layoutParams).gravity = 16;
        return layoutParams;
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final LinearLayoutCompat.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public static LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutParams layoutParams2;
        if (layoutParams != null) {
            if (layoutParams instanceof LayoutParams) {
                layoutParams2 = new LayoutParams((LayoutParams) layoutParams);
            } else {
                layoutParams2 = new LayoutParams(layoutParams);
            }
            if (((LinearLayout.LayoutParams) layoutParams2).gravity <= 0) {
                ((LinearLayout.LayoutParams) layoutParams2).gravity = 16;
            }
            return layoutParams2;
        }
        LayoutParams layoutParams3 = new LayoutParams(-2, -2);
        ((LinearLayout.LayoutParams) layoutParams3).gravity = 16;
        return layoutParams3;
    }

    public class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }
    }
}
