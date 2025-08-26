package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.BaseMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPopup;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.StandardMenuPopup;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.core.view.ActionProvider;
import androidx.reflect.widget.SeslTextViewReflector;
import com.android.systemui.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes.dex */
public class ActionMenuPresenter extends BaseMenuPresenter {
    public final SparseBooleanArray mActionButtonGroups;
    public ActionButtonSubmenu mActionButtonPopup;
    public int mActionItemWidthLimit;
    public boolean mExpandedActionViewsExclusive;
    public int mMaxItems;
    public final NumberFormat mNumberFormat;
    public OverflowMenuButton mOverflowButton;
    public OverflowPopup mOverflowPopup;
    public ActionMenuPopupCallback mPopupCallback;
    public final PopupPresenterCallback mPopupPresenterCallback;
    public OpenOverflowRunnable mPostedOpenRunnable;
    public boolean mReserveOverflow;
    public boolean mReserveOverflowSet;
    public final boolean mUseTextItemMode;
    public int mWidthLimit;

    public class ActionButtonSubmenu extends MenuPopupHelper {
        public ActionButtonSubmenu(Context context, SubMenuBuilder subMenuBuilder, View view) {
            super(context, subMenuBuilder, view, false, R.attr.actionOverflowMenuStyle);
            if ((subMenuBuilder.mItem.mFlags & 32) != 32) {
                View view2 = ActionMenuPresenter.this.mOverflowButton;
                this.mAnchorView = view2 == null ? (View) ActionMenuPresenter.this.mMenuView : view2;
            }
            PopupPresenterCallback popupPresenterCallback = ActionMenuPresenter.this.mPopupPresenterCallback;
            this.mPresenterCallback = popupPresenterCallback;
            StandardMenuPopup standardMenuPopup = this.mPopup;
            if (standardMenuPopup != null) {
                standardMenuPopup.mPresenterCallback = popupPresenterCallback;
            }
        }

        @Override // androidx.appcompat.view.menu.MenuPopupHelper
        public final void onDismiss() {
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            actionMenuPresenter.mActionButtonPopup = null;
            actionMenuPresenter.getClass();
            super.onDismiss();
        }
    }

    public class ActionMenuPopupCallback extends ActionMenuItemView.PopupCallback {
        public ActionMenuPopupCallback() {
        }

        @Override // androidx.appcompat.view.menu.ActionMenuItemView.PopupCallback
        public final MenuPopup getPopup() {
            ActionButtonSubmenu actionButtonSubmenu = ActionMenuPresenter.this.mActionButtonPopup;
            if (actionButtonSubmenu != null) {
                return actionButtonSubmenu.getPopup();
            }
            return null;
        }
    }

    public class OpenOverflowRunnable implements Runnable {
        public final OverflowPopup mPopup;

        public OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }

        @Override // java.lang.Runnable
        public final void run() throws Resources.NotFoundException {
            MenuBuilder.Callback callback;
            MenuBuilder menuBuilder = ActionMenuPresenter.this.mMenu;
            if (menuBuilder != null && (callback = menuBuilder.mCallback) != null) {
                callback.onMenuModeChange(menuBuilder);
            }
            View view = (View) ActionMenuPresenter.this.mMenuView;
            if (view != null && view.getWindowToken() != null) {
                OverflowPopup overflowPopup = this.mPopup;
                if (overflowPopup.isShowing()) {
                    ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
                } else if (overflowPopup.mAnchorView != null) {
                    overflowPopup.showPopup(true, true);
                    ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
                }
            }
            ActionMenuPresenter.this.mPostedOpenRunnable = null;
        }
    }

    public class OverflowImageView extends AppCompatImageView {
        public Configuration mConfiguration;

        public OverflowImageView(Context context) throws Resources.NotFoundException {
            super(context, null, R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setLongClickable(true);
            String string = getResources().getString(R.string.sesl_action_menu_overflow_description);
            ActionMenuPresenter.this.getClass();
            setTooltipText(string);
            this.mConfiguration = ActionMenuPresenter.this.mContext.getResources().getConfiguration();
        }

        @Override // android.view.View
        public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
            super.onConfigurationChanged(configuration);
            Configuration configuration2 = this.mConfiguration;
            int iDiff = configuration2 != null ? configuration2.diff(configuration) : 4096;
            this.mConfiguration = configuration;
            Context context = getContext();
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R$styleable.View, R.attr.actionOverflowButtonStyle, 0);
            setMinimumHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(4, 0));
            typedArrayObtainStyledAttributes.recycle();
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            context.getResources().getString(R.string.sesl_action_menu_overflow_description);
            actionMenuPresenter.getClass();
            if ((iDiff & 4096) != 0) {
                TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(null, R$styleable.AppCompatImageView, R.attr.actionOverflowButtonStyle, 0);
                Drawable drawable = context.getDrawable(typedArrayObtainStyledAttributes2.getResourceId(0, -1));
                if (drawable != null) {
                    setImageDrawable(drawable);
                }
                typedArrayObtainStyledAttributes2.recycle();
            }
        }

        @Override // android.widget.ImageView, android.view.View
        public final void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
        }

        @Override // android.view.View
        public final boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            if (ActionMenuPresenter.this.showOverflowMenu() && isHovered()) {
                TooltipCompatHandler.sIsTooltipNull = true;
            }
            return true;
        }

        @Override // android.view.View
        public final boolean performLongClick() {
            TooltipCompatHandler.sIsForceActionBarX = true;
            TooltipCompatHandler.sIsForceBelow = true;
            return super.performLongClick();
        }

        @Override // android.widget.ImageView
        public final boolean setFrame(int i, int i2, int i3, int i4) {
            boolean frame = super.setFrame(i, i2, i3, i4);
            Drawable drawable = getDrawable();
            Drawable background = getBackground();
            if (drawable != null && background != null) {
                int width = getWidth();
                int height = getHeight();
                int paddingLeft = (getPaddingLeft() - getPaddingRight()) / 2;
                background.setHotspotBounds(paddingLeft, 0, width + paddingLeft, height);
            }
            return frame;
        }
    }

    public class OverflowMenuButton extends FrameLayout implements ActionMenuView.ActionMenuChildView {
        public final ViewGroup mBadgeBackground;
        public CharSequence mBadgeContentDescription;
        public final TextView mBadgeText;
        public CharSequence mContentDescription;
        public final View mInnerView;

        public OverflowMenuButton(Context context) throws Resources.NotFoundException {
            super(context);
            View overflowTextView = ActionMenuPresenter.this.mUseTextItemMode ? ActionMenuPresenter.this.new OverflowTextView(context) : ActionMenuPresenter.this.new OverflowImageView(context);
            this.mInnerView = overflowTextView;
            addView(overflowTextView, new FrameLayout.LayoutParams(-2, -2));
            Resources resources = getResources();
            if (overflowTextView instanceof OverflowImageView) {
                this.mContentDescription = overflowTextView.getContentDescription();
                this.mBadgeContentDescription = ((Object) this.mContentDescription) + " , " + resources.getString(R.string.sesl_preferecne_badge_description);
            }
            if (TextUtils.isEmpty(this.mContentDescription)) {
                CharSequence string = resources.getString(R.string.sesl_action_menu_overflow_description);
                this.mContentDescription = string;
                overflowTextView.setContentDescription(string);
            }
            ViewGroup viewGroup = (ViewGroup) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.sesl_action_menu_item_badge, (ViewGroup) this, false);
            this.mBadgeBackground = viewGroup;
            this.mBadgeText = (TextView) viewGroup.getChildAt(0);
            addView(viewGroup);
        }

        @Override // androidx.appcompat.widget.ActionMenuView.ActionMenuChildView
        public final boolean needsDividerAfter() {
            return false;
        }

        @Override // androidx.appcompat.widget.ActionMenuView.ActionMenuChildView
        public final boolean needsDividerBefore() {
            return false;
        }

        @Override // android.view.View
        public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
            float dimension;
            super.onConfigurationChanged(configuration);
            Resources resources = getResources();
            this.mBadgeText.setTextSize(0, (int) resources.getDimension(R.dimen.sesl_menu_item_badge_text_size));
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBadgeBackground.getLayoutParams();
            CharSequence text = this.mBadgeText.getText();
            if (text == null || text.toString() == null) {
                float dimension2 = resources.getDimension(R.dimen.sesl_badge_default_width);
                if (text != null) {
                    dimension = resources.getDimension(R.dimen.sesl_badge_additional_width) * text.length();
                } else {
                    dimension = 0.0f;
                }
                marginLayoutParams.width = (int) (dimension2 + dimension);
                marginLayoutParams.height = (int) (resources.getDimension(R.dimen.sesl_badge_additional_width) + resources.getDimension(R.dimen.sesl_badge_default_width));
                marginLayoutParams.topMargin = (int) getResources().getDimension(R.dimen.sesl_menu_item_number_badge_top_margin);
                marginLayoutParams.setMarginEnd((int) resources.getDimension(R.dimen.sesl_menu_item_number_badge_end_margin));
            } else {
                marginLayoutParams.width = (int) resources.getDimension(R.dimen.sesl_menu_item_badge_size);
                marginLayoutParams.height = (int) resources.getDimension(R.dimen.sesl_menu_item_badge_size);
            }
            this.mBadgeBackground.setLayoutParams(marginLayoutParams);
            if (this.mInnerView instanceof OverflowImageView) {
                this.mContentDescription = getContentDescription();
                this.mBadgeContentDescription = ((Object) this.mContentDescription) + " , " + resources.getString(R.string.sesl_preferecne_badge_description);
            }
            if (TextUtils.isEmpty(this.mContentDescription)) {
                this.mContentDescription = resources.getString(R.string.sesl_action_menu_overflow_description);
                this.mBadgeContentDescription = ((Object) this.mContentDescription) + " , " + resources.getString(R.string.sesl_preferecne_badge_description);
            }
            if (this.mBadgeBackground.getVisibility() == 0) {
                View view = this.mInnerView;
                if (view instanceof OverflowImageView) {
                    view.setContentDescription(this.mBadgeContentDescription);
                    return;
                }
                return;
            }
            View view2 = this.mInnerView;
            if (view2 instanceof OverflowImageView) {
                view2.setContentDescription(this.mContentDescription);
            }
        }
    }

    public class OverflowPopup extends MenuPopupHelper {
        public OverflowPopup(Context context, MenuBuilder menuBuilder, View view, boolean z) {
            super(context, menuBuilder, view, z, R.attr.actionOverflowMenuStyle);
            this.mDropDownGravity = 8388613;
            PopupPresenterCallback popupPresenterCallback = ActionMenuPresenter.this.mPopupPresenterCallback;
            this.mPresenterCallback = popupPresenterCallback;
            StandardMenuPopup standardMenuPopup = this.mPopup;
            if (standardMenuPopup != null) {
                standardMenuPopup.mPresenterCallback = popupPresenterCallback;
            }
        }

        @Override // androidx.appcompat.view.menu.MenuPopupHelper
        public final void onDismiss() {
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            MenuBuilder menuBuilder = actionMenuPresenter.mMenu;
            if (menuBuilder != null) {
                menuBuilder.close(true);
            }
            actionMenuPresenter.mOverflowPopup = null;
            super.onDismiss();
        }
    }

    public class OverflowTextView extends AppCompatTextView {
        public OverflowTextView(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R$styleable.AppCompatTheme, 0, 0);
            setTextAppearance(typedArrayObtainStyledAttributes.getResourceId(26, 0));
            typedArrayObtainStyledAttributes.recycle();
            setText(getResources().getString(R.string.sesl_more_item_label));
            boolean zIsLightTheme = SeslMisc.isLightTheme(context);
            ActionMenuPresenter.this.getClass();
            if (zIsLightTheme) {
                setBackgroundResource(R.drawable.sesl_action_bar_item_text_background_light);
            } else {
                setBackgroundResource(R.drawable.sesl_action_bar_item_text_background_dark);
            }
            SeslTextViewReflector.semSetButtonShapeEnabled(this, true);
        }

        @Override // android.widget.TextView, android.view.View
        public final void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
        }

        @Override // android.view.View
        public final boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            ActionMenuPresenter.this.showOverflowMenu();
            return true;
        }
    }

    public class PopupPresenterCallback implements MenuPresenter.Callback {
        public PopupPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder instanceof SubMenuBuilder) {
                ((SubMenuBuilder) menuBuilder).mParentMenu.getRootMenu().close(false);
            }
            MenuPresenter.Callback callback = ActionMenuPresenter.this.mCallback;
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z);
            }
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            if (menuBuilder == actionMenuPresenter.mMenu) {
                return false;
            }
            ((SubMenuBuilder) menuBuilder).mItem.getClass();
            actionMenuPresenter.getClass();
            MenuPresenter.Callback callback = actionMenuPresenter.mCallback;
            if (callback != null) {
                return callback.onOpenSubMenu(menuBuilder);
            }
            return false;
        }
    }

    public ActionMenuPresenter(Context context) {
        super(context, R.layout.sesl_action_menu_layout, R.layout.sesl_action_menu_item_layout);
        this.mActionButtonGroups = new SparseBooleanArray();
        this.mPopupPresenterCallback = new PopupPresenterCallback();
        this.mNumberFormat = NumberFormat.getInstance(Locale.getDefault());
        this.mUseTextItemMode = context.getResources().getBoolean(R.bool.sesl_action_bar_text_item_mode);
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public final void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView) {
        itemView.initialize(menuItemImpl);
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) itemView;
        actionMenuItemView.mItemInvoker = (ActionMenuView) this.mMenuView;
        if (this.mPopupCallback == null) {
            this.mPopupCallback = new ActionMenuPopupCallback();
        }
        actionMenuItemView.mPopupCallback = this.mPopupCallback;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public final boolean filterLeftoverView(ViewGroup viewGroup, int i) {
        if (viewGroup.getChildAt(i) == this.mOverflowButton) {
            return false;
        }
        viewGroup.removeViewAt(i);
        return true;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public final boolean flagActionItems() {
        ArrayList visibleItems;
        int size;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        ActionMenuPresenter actionMenuPresenter = this;
        MenuBuilder menuBuilder = actionMenuPresenter.mMenu;
        View view = null;
        boolean z5 = false;
        if (menuBuilder != null) {
            visibleItems = menuBuilder.getVisibleItems();
            size = visibleItems.size();
        } else {
            visibleItems = null;
            size = 0;
        }
        int i = actionMenuPresenter.mMaxItems;
        int i2 = actionMenuPresenter.mActionItemWidthLimit;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        Object obj = actionMenuPresenter.mMenuView;
        if (obj == null) {
            Log.d("ActionMenuPresenter", "mMenuView is null, maybe Menu has not been initialized.");
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) obj;
        int i3 = 0;
        boolean z6 = false;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            z = true;
            if (i3 >= size) {
                break;
            }
            MenuItemImpl menuItemImpl = (MenuItemImpl) visibleItems.get(i3);
            if (menuItemImpl.requiresActionButton()) {
                i4++;
                z4 = z6;
            } else if ((menuItemImpl.mShowAsAction & 1) == 1) {
                i5++;
                z4 = z6;
            } else {
                z4 = true;
            }
            if (actionMenuPresenter.mExpandedActionViewsExclusive && menuItemImpl.mIsActionViewExpanded) {
                i = 0;
            }
            i3++;
            z6 = z4;
        }
        if (actionMenuPresenter.mReserveOverflow && (z6 || i5 + i4 > i)) {
            i--;
        }
        int i6 = i - i4;
        SparseBooleanArray sparseBooleanArray = actionMenuPresenter.mActionButtonGroups;
        sparseBooleanArray.clear();
        int i7 = 0;
        int i8 = 0;
        while (i7 < size) {
            MenuItemImpl menuItemImpl2 = (MenuItemImpl) visibleItems.get(i7);
            if (menuItemImpl2.requiresActionButton()) {
                View itemView = actionMenuPresenter.getItemView(menuItemImpl2, view, viewGroup);
                itemView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                int measuredWidth = itemView.getMeasuredWidth();
                i2 -= measuredWidth;
                if (i8 == 0) {
                    i8 = measuredWidth;
                }
                int i9 = menuItemImpl2.mGroup;
                if (i9 != 0) {
                    sparseBooleanArray.put(i9, z);
                }
                menuItemImpl2.setIsActionButton(z);
                z2 = z5;
                z3 = z ? 1 : 0;
            } else if ((menuItemImpl2.mShowAsAction & (z ? 1 : 0)) == z) {
                int i10 = menuItemImpl2.mGroup;
                boolean z7 = sparseBooleanArray.get(i10);
                boolean z8 = ((i6 > 0 || z7) && i2 > 0) ? z ? 1 : 0 : z5;
                if (z8) {
                    View itemView2 = actionMenuPresenter.getItemView(menuItemImpl2, view, viewGroup);
                    itemView2.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                    int measuredWidth2 = itemView2.getMeasuredWidth();
                    i2 -= measuredWidth2;
                    if (i8 == 0) {
                        i8 = measuredWidth2;
                    }
                    z8 &= i2 >= 0 ? z ? 1 : 0 : false;
                }
                boolean z9 = z8;
                if (z9 && i10 != 0) {
                    sparseBooleanArray.put(i10, z);
                } else if (z7) {
                    sparseBooleanArray.put(i10, false);
                    int i11 = 0;
                    while (i11 < i7) {
                        MenuItemImpl menuItemImpl3 = (MenuItemImpl) visibleItems.get(i11);
                        boolean z10 = z ? 1 : 0;
                        if (menuItemImpl3.mGroup == i10) {
                            if ((menuItemImpl3.mFlags & 32) == 32) {
                                i6++;
                            }
                            menuItemImpl3.setIsActionButton(false);
                        }
                        i11++;
                        z = z10;
                    }
                }
                z3 = z;
                if (z9) {
                    i6--;
                }
                menuItemImpl2.setIsActionButton(z9);
                z2 = false;
            } else {
                z2 = z5;
                z3 = z ? 1 : 0;
                menuItemImpl2.setIsActionButton(z2);
            }
            i7++;
            actionMenuPresenter = this;
            z5 = z2;
            z = z3;
            view = null;
        }
        return z ? 1 : 0;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public final View getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        View actionView = menuItemImpl.getActionView();
        if (actionView == null || menuItemImpl.hasCollapsibleActionView()) {
            actionView = super.getItemView(menuItemImpl, view, viewGroup);
        }
        actionView.setVisibility(menuItemImpl.mIsActionViewExpanded ? 8 : 0);
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        ((ActionMenuView) viewGroup).getClass();
        if (!(layoutParams instanceof ActionMenuView.LayoutParams)) {
            actionView.setLayoutParams(ActionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    public final boolean hideOverflowMenu() {
        Object obj;
        OpenOverflowRunnable openOverflowRunnable = this.mPostedOpenRunnable;
        if (openOverflowRunnable != null && (obj = this.mMenuView) != null) {
            ((View) obj).removeCallbacks(openOverflowRunnable);
            this.mPostedOpenRunnable = null;
            return true;
        }
        OverflowPopup overflowPopup = this.mOverflowPopup;
        if (overflowPopup == null) {
            return false;
        }
        if (overflowPopup.isShowing()) {
            overflowPopup.mPopup.dismiss();
        }
        return true;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public final void initForMenu(Context context, MenuBuilder menuBuilder) {
        super.initForMenu(context, menuBuilder);
        Resources resources = context.getResources();
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(context);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = true;
        }
        this.mWidthLimit = (int) (actionBarPolicy.mContext.getResources().getDisplayMetrics().widthPixels * 0.7f);
        this.mMaxItems = actionBarPolicy.getMaxActionButtons();
        int measuredWidth = this.mWidthLimit;
        if (this.mReserveOverflow) {
            if (this.mOverflowButton == null) {
                OverflowMenuButton overflowMenuButton = new OverflowMenuButton(this.mSystemContext);
                this.mOverflowButton = overflowMenuButton;
                overflowMenuButton.setId(R.id.sesl_action_bar_overflow_button);
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.mOverflowButton.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            }
            measuredWidth -= this.mOverflowButton.getMeasuredWidth();
        } else {
            this.mOverflowButton = null;
        }
        this.mActionItemWidthLimit = measuredWidth;
        float f = resources.getDisplayMetrics().density;
    }

    public final boolean isOverflowMenuShowing() {
        OverflowPopup overflowPopup = this.mOverflowPopup;
        return overflowPopup != null && overflowPopup.isShowing();
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        hideOverflowMenu();
        ActionButtonSubmenu actionButtonSubmenu = this.mActionButtonPopup;
        if (actionButtonSubmenu != null && actionButtonSubmenu.isShowing()) {
            actionButtonSubmenu.mPopup.dismiss();
        }
        super.onCloseMenu(menuBuilder, z);
    }

    public final void onConfigurationChanged() {
        OverflowMenuButton overflowMenuButton;
        this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons();
        int i = (int) (r0.mContext.getResources().getDisplayMetrics().widthPixels * 0.7f);
        this.mWidthLimit = i;
        if (!this.mReserveOverflow || (overflowMenuButton = this.mOverflowButton) == null) {
            this.mActionItemWidthLimit = i;
        } else {
            this.mActionItemWidthLimit = i - overflowMenuButton.getMeasuredWidth();
        }
        MenuBuilder menuBuilder = this.mMenu;
        if (menuBuilder != null) {
            menuBuilder.onItemsChanged(true);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public final boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) throws Resources.NotFoundException {
        boolean z;
        if (subMenuBuilder != null && subMenuBuilder.hasVisibleItems()) {
            SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
            while (true) {
                MenuBuilder menuBuilder = subMenuBuilder2.mParentMenu;
                if (menuBuilder == this.mMenu) {
                    break;
                }
                subMenuBuilder2 = (SubMenuBuilder) menuBuilder;
            }
            MenuItemImpl menuItemImpl = subMenuBuilder2.mItem;
            ViewGroup viewGroup = (ViewGroup) this.mMenuView;
            View view = null;
            if (viewGroup != null) {
                int childCount = viewGroup.getChildCount();
                int i = 0;
                while (true) {
                    if (i >= childCount) {
                        break;
                    }
                    View childAt = viewGroup.getChildAt(i);
                    if ((childAt instanceof MenuView.ItemView) && ((MenuView.ItemView) childAt).getItemData() == menuItemImpl) {
                        view = childAt;
                        break;
                    }
                    i++;
                }
            }
            if (view != null) {
                subMenuBuilder.mItem.getClass();
                int size = subMenuBuilder.mItems.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        z = false;
                        break;
                    }
                    MenuItem item = subMenuBuilder.getItem(i2);
                    if (item.isVisible() && item.getIcon() != null) {
                        z = true;
                        break;
                    }
                    i2++;
                }
                ActionButtonSubmenu actionButtonSubmenu = new ActionButtonSubmenu(this.mContext, subMenuBuilder, view);
                this.mActionButtonPopup = actionButtonSubmenu;
                actionButtonSubmenu.mForceShowIcon = z;
                StandardMenuPopup standardMenuPopup = actionButtonSubmenu.mPopup;
                if (standardMenuPopup != null) {
                    standardMenuPopup.mAdapter.mForceShowIcon = z;
                }
                if (!actionButtonSubmenu.isShowing()) {
                    if (actionButtonSubmenu.mAnchorView == null) {
                        throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
                    }
                    actionButtonSubmenu.showPopup(false, false);
                }
                super.onSubMenuSelected(subMenuBuilder);
                return true;
            }
        }
        return false;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public final boolean shouldIncludeItem(MenuItemImpl menuItemImpl) {
        return (menuItemImpl.mFlags & 32) == 32;
    }

    public final boolean showOverflowMenu() {
        MenuBuilder menuBuilder;
        if (!this.mReserveOverflow || isOverflowMenuShowing() || (menuBuilder = this.mMenu) == null || this.mMenuView == null || this.mPostedOpenRunnable != null) {
            return false;
        }
        menuBuilder.flagActionItems();
        if (menuBuilder.mNonActionItems.isEmpty()) {
            return false;
        }
        OpenOverflowRunnable openOverflowRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, this.mOverflowButton, true));
        this.mPostedOpenRunnable = openOverflowRunnable;
        ((View) this.mMenuView).post(openOverflowRunnable);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004d  */
    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateMenuView(boolean z) {
        ArrayList arrayList;
        boolean z2;
        MenuView menuView;
        int i;
        int i2;
        String str;
        int dimension;
        int dimension2;
        super.updateMenuView(z);
        Object obj = this.mMenuView;
        if (obj != null) {
            ((View) obj).requestLayout();
        }
        MenuBuilder menuBuilder = this.mMenu;
        if (menuBuilder != null) {
            menuBuilder.flagActionItems();
            ArrayList arrayList2 = menuBuilder.mActionItems;
            int size = arrayList2.size();
            for (int i3 = 0; i3 < size; i3++) {
                ActionProvider actionProvider = ((MenuItemImpl) arrayList2.get(i3)).mActionProvider;
            }
        }
        MenuBuilder menuBuilder2 = this.mMenu;
        if (menuBuilder2 != null) {
            menuBuilder2.flagActionItems();
            arrayList = menuBuilder2.mNonActionItems;
        } else {
            arrayList = null;
        }
        if (!this.mReserveOverflow || arrayList == null) {
            z2 = false;
        } else {
            int size2 = arrayList.size();
            if (size2 == 1) {
                z2 = !((MenuItemImpl) arrayList.get(0)).mIsActionViewExpanded;
            } else if (size2 > 0) {
                z2 = true;
            }
        }
        if (z2) {
            if (this.mOverflowButton == null) {
                OverflowMenuButton overflowMenuButton = new OverflowMenuButton(this.mSystemContext);
                this.mOverflowButton = overflowMenuButton;
                overflowMenuButton.setId(R.id.sesl_action_bar_overflow_button);
            }
            ViewGroup viewGroup = (ViewGroup) this.mOverflowButton.getParent();
            if (viewGroup != this.mMenuView) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.mOverflowButton);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.mMenuView;
                if (actionMenuView != null) {
                    OverflowMenuButton overflowMenuButton2 = this.mOverflowButton;
                    int i4 = ActionMenuView.$r8$clinit;
                    ActionMenuView.LayoutParams layoutParams = new ActionMenuView.LayoutParams(-2, -2);
                    ((LinearLayout.LayoutParams) layoutParams).gravity = 16;
                    layoutParams.isOverflowButton = true;
                    actionMenuView.addView(overflowMenuButton2, layoutParams);
                }
            }
        } else {
            OverflowMenuButton overflowMenuButton3 = this.mOverflowButton;
            if (overflowMenuButton3 != null) {
                Object parent = overflowMenuButton3.getParent();
                Object obj2 = this.mMenuView;
                if (parent == obj2) {
                    if (obj2 != null) {
                        ((ViewGroup) obj2).removeView(this.mOverflowButton);
                    }
                    if (isOverflowMenuShowing()) {
                        hideOverflowMenu();
                    }
                }
            }
        }
        OverflowMenuButton overflowMenuButton4 = this.mOverflowButton;
        if (overflowMenuButton4 != null && (menuView = this.mMenuView) != null) {
            ActionMenuView actionMenuView2 = (ActionMenuView) menuView;
            String str2 = actionMenuView2.mOverflowBadgeText;
            if (actionMenuView2.mMenu == null) {
                i = 0;
            } else {
                i = 0;
                for (int i5 = 0; i5 < actionMenuView2.mMenu.mItems.size(); i5++) {
                    MenuItemImpl menuItemImpl = (MenuItemImpl) actionMenuView2.mMenu.getItem(i5);
                    if (menuItemImpl.isVisible()) {
                        String str3 = menuItemImpl.mBadgeText;
                        if (str3 == null) {
                            i2 = 0;
                        } else {
                            try {
                                i2 = Integer.parseInt(str3);
                            } catch (NumberFormatException unused) {
                                i2 = 1;
                            }
                        }
                        i += i2;
                    }
                }
            }
            if (i > 99) {
                i = 99;
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) overflowMenuButton4.mBadgeBackground.getLayoutParams();
            if (str2 != null) {
                dimension = (int) overflowMenuButton4.getResources().getDimension(R.dimen.sesl_menu_item_badge_size);
                dimension2 = (int) overflowMenuButton4.getResources().getDimension(R.dimen.sesl_menu_item_badge_size);
                str = "";
            } else {
                str = ActionMenuPresenter.this.mNumberFormat.format(i);
                dimension = (int) ((overflowMenuButton4.getResources().getDimension(R.dimen.sesl_badge_additional_width) * str.length()) + overflowMenuButton4.getResources().getDimension(R.dimen.sesl_badge_default_width));
                dimension2 = (int) (overflowMenuButton4.getResources().getDimension(R.dimen.sesl_badge_additional_width) + overflowMenuButton4.getResources().getDimension(R.dimen.sesl_badge_default_width));
                marginLayoutParams.topMargin = (int) overflowMenuButton4.getResources().getDimension(R.dimen.sesl_menu_item_number_badge_top_margin);
                marginLayoutParams.setMarginEnd((int) overflowMenuButton4.getResources().getDimension(R.dimen.sesl_menu_item_number_badge_end_margin));
            }
            overflowMenuButton4.mBadgeText.setText(str);
            marginLayoutParams.width = dimension;
            marginLayoutParams.height = dimension2;
            overflowMenuButton4.mBadgeBackground.setLayoutParams(marginLayoutParams);
            overflowMenuButton4.mBadgeBackground.setVisibility(i <= 0 ? 8 : 0);
            if (overflowMenuButton4.mBadgeBackground.getVisibility() == 0) {
                View view = overflowMenuButton4.mInnerView;
                if (view instanceof OverflowImageView) {
                    view.setContentDescription(overflowMenuButton4.mBadgeContentDescription);
                }
            } else {
                View view2 = overflowMenuButton4.mInnerView;
                if (view2 instanceof OverflowImageView) {
                    view2.setContentDescription(overflowMenuButton4.mContentDescription);
                }
            }
        }
        OverflowMenuButton overflowMenuButton5 = this.mOverflowButton;
        if ((overflowMenuButton5 == null || overflowMenuButton5.getVisibility() != 0) && isOverflowMenuShowing()) {
            hideOverflowMenu();
        }
        MenuView menuView2 = this.mMenuView;
        if (menuView2 != null) {
            ((ActionMenuView) menuView2).mReserveOverflow = this.mReserveOverflow;
        }
    }
}
