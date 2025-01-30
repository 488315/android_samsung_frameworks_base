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
import androidx.reflect.p001os.SeslBuildReflector$SeslVersionReflector;
import com.android.systemui.R;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mActionButtonPaddingEnd;
    public int mActionButtonPaddingStart;
    public MenuPresenter.Callback mActionMenuPresenterCallback;
    public boolean mFormatItems;
    public int mFormatItemsWidth;
    public final int mGeneratedItemPadding;
    public final boolean mIsOneUI41;
    public int mLastItemEndPadding;
    public MenuBuilder mMenu;
    public MenuBuilder.Callback mMenuBuilderCallback;
    public final int mMinCellSize;
    public Toolbar.C00991 mOnMenuItemClickListener;
    public final String mOverflowBadgeText;
    public int mOverflowButtonMinWidth;
    public int mOverflowButtonPaddingEnd;
    public int mOverflowButtonPaddingStart;
    public Context mPopupContext;
    public int mPopupTheme;
    public ActionMenuPresenter mPresenter;
    public boolean mReserveOverflow;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends LinearLayoutCompat.LayoutParams {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MenuBuilderCallback implements MenuBuilder.Callback {
        public MenuBuilderCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            boolean onMenuItemSelected;
            Toolbar.C00991 c00991 = ActionMenuView.this.mOnMenuItemClickListener;
            if (c00991 == null) {
                return false;
            }
            Toolbar toolbar = Toolbar.this;
            if (toolbar.mMenuHostHelper.onMenuItemSelected(menuItem)) {
                onMenuItemSelected = true;
            } else {
                ToolbarActionBar.C00362 c00362 = toolbar.mOnMenuItemClickListener;
                onMenuItemSelected = c00362 != null ? ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, menuItem) : false;
            }
            return onMenuItemSelected;
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
            MenuPresenter.Callback callback = this.mActionMenuPresenterCallback;
            if (callback == null) {
                callback = new ActionMenuPresenterCallback();
            }
            actionMenuPresenter.mCallback = callback;
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            ActionMenuPresenter actionMenuPresenter2 = this.mPresenter;
            actionMenuPresenter2.mMenuView = this;
            this.mMenu = actionMenuPresenter2.mMenu;
        }
        return this.mMenu;
    }

    public final boolean hasSupportDividerBeforeChildAt(int i) {
        boolean z = false;
        if (i == 0) {
            return false;
        }
        KeyEvent.Callback childAt = getChildAt(i - 1);
        KeyEvent.Callback childAt2 = getChildAt(i);
        if (i < getChildCount() && (childAt instanceof ActionMenuChildView)) {
            z = false | ((ActionMenuChildView) childAt).needsDividerAfter();
        }
        return (i <= 0 || !(childAt2 instanceof ActionMenuChildView)) ? z : z | ((ActionMenuChildView) childAt2).needsDividerBefore();
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, iArr, R.attr.actionButtonStyle, 0);
        this.mActionButtonPaddingStart = obtainStyledAttributes.getDimensionPixelSize(7, 0);
        this.mActionButtonPaddingEnd = obtainStyledAttributes.getDimensionPixelSize(6, 0);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(null, iArr, R.attr.actionOverflowButtonStyle, 0);
        this.mOverflowButtonPaddingStart = obtainStyledAttributes2.getDimensionPixelSize(7, 0);
        this.mOverflowButtonPaddingEnd = obtainStyledAttributes2.getDimensionPixelSize(6, 0);
        this.mOverflowButtonMinWidth = obtainStyledAttributes2.getDimensionPixelSize(3, 0);
        obtainStyledAttributes2.recycle();
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
        int i5;
        if (!this.mFormatItems) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int childCount = getChildCount();
        int i6 = (i4 - i2) / 2;
        int i7 = this.mDividerWidth;
        int i8 = i3 - i;
        int paddingRight = (i8 - getPaddingRight()) - getPaddingLeft();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int i9 = 0;
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            View childAt = getChildAt(i11);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isOverflowButton) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    if (hasSupportDividerBeforeChildAt(i11)) {
                        measuredWidth += i7;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (isLayoutRtl) {
                        i5 = getPaddingLeft() + ((LinearLayout.LayoutParams) layoutParams).leftMargin;
                        width = i5 + measuredWidth;
                    } else {
                        width = (getWidth() - getPaddingRight()) - ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                        i5 = width - measuredWidth;
                    }
                    int i12 = i6 - (measuredHeight / 2);
                    childAt.layout(i5, i12, width, measuredHeight + i12);
                    paddingRight -= measuredWidth;
                    i9 = 1;
                } else {
                    paddingRight -= (childAt.getMeasuredWidth() + ((LinearLayout.LayoutParams) layoutParams).leftMargin) + ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                    hasSupportDividerBeforeChildAt(i11);
                    i10++;
                }
            }
        }
        if (childCount == 1 && i9 == 0) {
            View childAt2 = getChildAt(0);
            int measuredWidth2 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i13 = (i8 / 2) - (measuredWidth2 / 2);
            int i14 = i6 - (measuredHeight2 / 2);
            childAt2.layout(i13, i14, measuredWidth2 + i13, measuredHeight2 + i14);
            return;
        }
        int i15 = i10 - (i9 ^ 1);
        int max = Math.max(0, i15 > 0 ? paddingRight / i15 : 0);
        if (isLayoutRtl) {
            int width2 = getWidth() - getPaddingRight();
            for (int i16 = 0; i16 < childCount; i16++) {
                View childAt3 = getChildAt(i16);
                LayoutParams layoutParams2 = (LayoutParams) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !layoutParams2.isOverflowButton) {
                    int i17 = width2 - ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                    int measuredWidth3 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i18 = i6 - (measuredHeight3 / 2);
                    childAt3.layout(i17 - measuredWidth3, i18, i17, measuredHeight3 + i18);
                    width2 = i17 - ((measuredWidth3 + ((LinearLayout.LayoutParams) layoutParams2).leftMargin) + max);
                }
            }
            return;
        }
        int paddingLeft = getPaddingLeft();
        for (int i19 = 0; i19 < childCount; i19++) {
            View childAt4 = getChildAt(i19);
            LayoutParams layoutParams3 = (LayoutParams) childAt4.getLayoutParams();
            if (childAt4.getVisibility() != 8 && !layoutParams3.isOverflowButton) {
                int i20 = paddingLeft + ((LinearLayout.LayoutParams) layoutParams3).leftMargin;
                int measuredWidth4 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i21 = i6 - (measuredHeight4 / 2);
                childAt4.layout(i20, i21, i20 + measuredWidth4, measuredHeight4 + i21);
                paddingLeft = measuredWidth4 + ((LinearLayout.LayoutParams) layoutParams3).rightMargin + max + i20;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r12v10, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r12v29 */
    /* JADX WARN: Type inference failed for: r12v9 */
    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    public final void onMeasure(int i, int i2) {
        boolean z;
        int i3;
        boolean z2;
        int i4;
        ?? r12;
        int i5;
        int i6;
        int i7;
        int i8;
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
            for (int i9 = 0; i9 < childCount; i9++) {
                View childAt = getChildAt(i9);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                ((LinearLayout.LayoutParams) layoutParams).rightMargin = 0;
                ((LinearLayout.LayoutParams) layoutParams).leftMargin = 0;
                if (childAt instanceof ActionMenuItemView) {
                    int i10 = this.mActionButtonPaddingStart;
                    int i11 = this.mActionButtonPaddingEnd;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api17Impl.setPaddingRelative(childAt, i10, 0, i11, 0);
                    int i12 = childCount - 1;
                    if (i9 == i12) {
                        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) childAt;
                        if (actionMenuItemView.hasText()) {
                            if (ViewCompat.Api17Impl.getLayoutDirection(this) == 0) {
                                ((LinearLayout.LayoutParams) layoutParams).rightMargin = this.mLastItemEndPadding;
                                childAt.setLayoutParams(layoutParams);
                            } else {
                                ((LinearLayout.LayoutParams) layoutParams).leftMargin = this.mLastItemEndPadding;
                                childAt.setLayoutParams(layoutParams);
                            }
                            z = false;
                        } else if (this.mIsOneUI41) {
                            childAt.setLayoutParams(layoutParams);
                            z = false;
                            ViewCompat.Api17Impl.setPaddingRelative(childAt, this.mActionButtonPaddingStart, 0, this.mOverflowButtonPaddingEnd, 0);
                        } else {
                            z = false;
                            actionMenuItemView.setMinWidth(this.mOverflowButtonMinWidth);
                            childAt.setLayoutParams(layoutParams);
                            ViewCompat.Api17Impl.setPaddingRelative(childAt, this.mOverflowButtonPaddingStart, 0, this.mOverflowButtonPaddingEnd, 0);
                        }
                    } else if (i9 < i12) {
                        ((ActionMenuItemView) childAt).hasText();
                    }
                } else if (layoutParams.isOverflowButton) {
                    if (childAt instanceof ActionMenuPresenter.OverflowMenuButton) {
                        ViewGroup viewGroup = (ViewGroup) childAt;
                        viewGroup.getChildAt(0).setPaddingRelative(this.mOverflowButtonPaddingStart, 0, this.mOverflowButtonPaddingEnd, 0);
                        viewGroup.getChildAt(0).setMinimumWidth(this.mOverflowButtonMinWidth);
                    } else {
                        int i13 = this.mOverflowButtonPaddingStart;
                        int i14 = this.mOverflowButtonPaddingEnd;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api17Impl.setPaddingRelative(childAt, i13, 0, i14, 0);
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
        int i15 = size2 - paddingRight;
        int i16 = this.mMinCellSize;
        int i17 = i15 / i16;
        int i18 = i15 % i16;
        if (i17 == 0) {
            setMeasuredDimension(i15, 0);
            return;
        }
        int i19 = (i18 / i17) + i16;
        int childCount2 = getChildCount();
        int i20 = 0;
        int i21 = 0;
        int i22 = 0;
        int i23 = 0;
        boolean z5 = false;
        int i24 = 0;
        long j = 0;
        while (i23 < childCount2) {
            View childAt2 = getChildAt(i23);
            int i25 = size3;
            if (childAt2.getVisibility() == 8) {
                i6 = mode;
                i5 = i15;
                i7 = paddingBottom;
            } else {
                boolean z6 = childAt2 instanceof ActionMenuItemView;
                int i26 = i21 + 1;
                if (z6) {
                    int i27 = this.mGeneratedItemPadding;
                    i4 = i26;
                    r12 = 0;
                    childAt2.setPadding(i27, 0, i27, 0);
                } else {
                    i4 = i26;
                    r12 = 0;
                }
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                layoutParams2.expanded = r12;
                layoutParams2.extraPixels = r12;
                layoutParams2.cellsUsed = r12;
                layoutParams2.expandable = r12;
                ((LinearLayout.LayoutParams) layoutParams2).leftMargin = r12;
                ((LinearLayout.LayoutParams) layoutParams2).rightMargin = r12;
                layoutParams2.preventEdgeOffset = z6 && ((ActionMenuItemView) childAt2).hasText();
                int i28 = layoutParams2.isOverflowButton ? 1 : i17;
                i5 = i15;
                LayoutParams layoutParams3 = (LayoutParams) childAt2.getLayoutParams();
                i6 = mode;
                i7 = paddingBottom;
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(childMeasureSpec) - paddingBottom, View.MeasureSpec.getMode(childMeasureSpec));
                ActionMenuItemView actionMenuItemView2 = z6 ? (ActionMenuItemView) childAt2 : null;
                boolean z7 = actionMenuItemView2 != null && actionMenuItemView2.hasText();
                if (i28 <= 0 || (z7 && i28 < 2)) {
                    i8 = 0;
                } else {
                    childAt2.measure(View.MeasureSpec.makeMeasureSpec(i28 * i19, VideoPlayer.MEDIA_ERROR_SYSTEM), makeMeasureSpec);
                    int measuredWidth = childAt2.getMeasuredWidth();
                    i8 = measuredWidth / i19;
                    if (measuredWidth % i19 != 0) {
                        i8++;
                    }
                    if (z7 && i8 < 2) {
                        i8 = 2;
                    }
                }
                layoutParams3.expandable = !layoutParams3.isOverflowButton && z7;
                layoutParams3.cellsUsed = i8;
                childAt2.measure(View.MeasureSpec.makeMeasureSpec(i19 * i8, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), makeMeasureSpec);
                i22 = Math.max(i22, i8);
                if (layoutParams2.expandable) {
                    i24++;
                }
                if (layoutParams2.isOverflowButton) {
                    z5 = true;
                }
                i17 -= i8;
                i20 = Math.max(i20, childAt2.getMeasuredHeight());
                if (i8 == 1) {
                    j |= 1 << i23;
                }
                i21 = i4;
            }
            i23++;
            size3 = i25;
            paddingBottom = i7;
            i15 = i5;
            mode = i6;
        }
        int i29 = mode;
        int i30 = i15;
        int i31 = size3;
        boolean z8 = z5 && i21 == 2;
        boolean z9 = false;
        while (i24 > 0 && i17 > 0) {
            int i32 = Integer.MAX_VALUE;
            int i33 = 0;
            long j2 = 0;
            for (int i34 = 0; i34 < childCount2; i34++) {
                LayoutParams layoutParams4 = (LayoutParams) getChildAt(i34).getLayoutParams();
                if (layoutParams4.expandable) {
                    int i35 = layoutParams4.cellsUsed;
                    if (i35 < i32) {
                        j2 = 1 << i34;
                        i33 = 1;
                        i32 = i35;
                    } else if (i35 == i32) {
                        i33++;
                        j2 |= 1 << i34;
                    }
                }
            }
            j |= j2;
            if (i33 > i17) {
                break;
            }
            int i36 = i32 + 1;
            int i37 = 0;
            while (i37 < childCount2) {
                View childAt3 = getChildAt(i37);
                LayoutParams layoutParams5 = (LayoutParams) childAt3.getLayoutParams();
                int i38 = childMeasureSpec;
                int i39 = childCount2;
                long j3 = 1 << i37;
                if ((j2 & j3) != 0) {
                    if (z8 && layoutParams5.preventEdgeOffset && i17 == 1) {
                        int i40 = this.mGeneratedItemPadding;
                        childAt3.setPadding(i40 + i19, 0, i40, 0);
                    }
                    layoutParams5.cellsUsed++;
                    layoutParams5.expanded = true;
                    i17--;
                } else if (layoutParams5.cellsUsed == i36) {
                    j |= j3;
                }
                i37++;
                childMeasureSpec = i38;
                childCount2 = i39;
            }
            z9 = true;
        }
        int i41 = childMeasureSpec;
        int i42 = childCount2;
        boolean z10 = !z5 && i21 == 1;
        if (i17 <= 0 || j == 0 || (i17 >= i21 - 1 && !z10 && i22 <= 1)) {
            i3 = i42;
        } else {
            float bitCount = Long.bitCount(j);
            if (!z10) {
                if ((j & 1) != 0 && !((LayoutParams) getChildAt(0).getLayoutParams()).preventEdgeOffset) {
                    bitCount -= 0.5f;
                }
                int i43 = i42 - 1;
                if ((j & (1 << i43)) != 0 && !((LayoutParams) getChildAt(i43).getLayoutParams()).preventEdgeOffset) {
                    bitCount -= 0.5f;
                }
            }
            int i44 = bitCount > 0.0f ? (int) ((i17 * i19) / bitCount) : 0;
            i3 = i42;
            for (int i45 = 0; i45 < i3; i45++) {
                if ((j & (1 << i45)) != 0) {
                    View childAt4 = getChildAt(i45);
                    LayoutParams layoutParams6 = (LayoutParams) childAt4.getLayoutParams();
                    if (childAt4 instanceof ActionMenuItemView) {
                        layoutParams6.extraPixels = i44;
                        layoutParams6.expanded = true;
                        if (i45 == 0 && !layoutParams6.preventEdgeOffset) {
                            ((LinearLayout.LayoutParams) layoutParams6).leftMargin = (-i44) / 2;
                        }
                        z2 = true;
                    } else if (layoutParams6.isOverflowButton) {
                        layoutParams6.extraPixels = i44;
                        z2 = true;
                        layoutParams6.expanded = true;
                        ((LinearLayout.LayoutParams) layoutParams6).rightMargin = (-i44) / 2;
                    } else {
                        if (i45 != 0) {
                            ((LinearLayout.LayoutParams) layoutParams6).leftMargin = i44 / 2;
                        }
                        if (i45 != i3 - 1) {
                            ((LinearLayout.LayoutParams) layoutParams6).rightMargin = i44 / 2;
                        }
                    }
                    z9 = z2;
                }
            }
        }
        if (z9) {
            for (int i46 = 0; i46 < i3; i46++) {
                View childAt5 = getChildAt(i46);
                LayoutParams layoutParams7 = (LayoutParams) childAt5.getLayoutParams();
                if (layoutParams7.expanded) {
                    childAt5.measure(View.MeasureSpec.makeMeasureSpec((layoutParams7.cellsUsed * i19) + layoutParams7.extraPixels, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), i41);
                }
            }
        }
        setMeasuredDimension(i30, i29 != 1073741824 ? i20 : i31);
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, R.attr.actionButtonStyle, 0);
        this.mActionButtonPaddingStart = obtainStyledAttributes.getDimensionPixelSize(7, 0);
        this.mActionButtonPaddingEnd = obtainStyledAttributes.getDimensionPixelSize(6, 0);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr, R.attr.actionOverflowButtonStyle, 0);
        this.mOverflowButtonPaddingStart = obtainStyledAttributes2.getDimensionPixelSize(7, 0);
        this.mOverflowButtonPaddingEnd = obtainStyledAttributes2.getDimensionPixelSize(6, 0);
        this.mOverflowButtonMinWidth = obtainStyledAttributes2.getDimensionPixelSize(3, 0);
        obtainStyledAttributes2.recycle();
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }
    }
}
