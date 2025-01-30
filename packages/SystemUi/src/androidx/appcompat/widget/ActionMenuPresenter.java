package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
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
import androidx.core.content.ContextCompat;
import androidx.core.view.ActionProvider;
import androidx.reflect.widget.SeslTextViewReflector;
import com.android.systemui.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ActionMenuPresenter extends BaseMenuPresenter {
    public final SparseBooleanArray mActionButtonGroups;
    public ActionButtonSubmenu mActionButtonPopup;
    public int mActionItemWidthLimit;
    public boolean mExpandedActionViewsExclusive;
    public int mMaxItems;
    public final NumberFormat mNumberFormat;
    public int mOpenSubMenuId;
    public OverflowMenuButton mOverflowButton;
    public OverflowPopup mOverflowPopup;
    public ActionMenuPopupCallback mPopupCallback;
    public final PopupPresenterCallback mPopupPresenterCallback;
    public OpenOverflowRunnable mPostedOpenRunnable;
    public boolean mReserveOverflow;
    public boolean mReserveOverflowSet;
    public final boolean mUseTextItemMode;
    public int mWidthLimit;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActionButtonSubmenu extends MenuPopupHelper {
        public ActionButtonSubmenu(Context context, SubMenuBuilder subMenuBuilder, View view) {
            super(context, subMenuBuilder, view, false, R.attr.actionOverflowMenuStyle);
            if (!((subMenuBuilder.mItem.mFlags & 32) == 32)) {
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
            actionMenuPresenter.mOpenSubMenuId = 0;
            super.onDismiss();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActionMenuPopupCallback extends ActionMenuItemView.PopupCallback {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OpenOverflowRunnable implements Runnable {
        public final OverflowPopup mPopup;

        public OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }

        @Override // java.lang.Runnable
        public final void run() {
            MenuBuilder.Callback callback;
            MenuBuilder menuBuilder = ActionMenuPresenter.this.mMenu;
            if (menuBuilder != null && (callback = menuBuilder.mCallback) != null) {
                callback.onMenuModeChange(menuBuilder);
            }
            View view = (View) ActionMenuPresenter.this.mMenuView;
            if (view != null && view.getWindowToken() != null && this.mPopup.tryShow$1()) {
                ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
            }
            ActionMenuPresenter.this.mPostedOpenRunnable = null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OverflowImageView extends AppCompatImageView {
        public Configuration mConfiguration;

        public OverflowImageView(Context context) {
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
        public final void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            Configuration configuration2 = this.mConfiguration;
            int diff = configuration2 != null ? configuration2.diff(configuration) : 4096;
            this.mConfiguration = configuration;
            Context context = getContext();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R$styleable.View, R.attr.actionOverflowButtonStyle, 0);
            setMinimumHeight(obtainStyledAttributes.getDimensionPixelSize(4, 0));
            obtainStyledAttributes.recycle();
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            context.getResources().getString(R.string.sesl_action_menu_overflow_description);
            actionMenuPresenter.getClass();
            if ((diff & 4096) != 0) {
                TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(null, R$styleable.AppCompatImageView, R.attr.actionOverflowButtonStyle, 0);
                int resourceId = obtainStyledAttributes2.getResourceId(0, -1);
                Object obj = ContextCompat.sLock;
                Drawable drawable = context.getDrawable(resourceId);
                if (drawable != null) {
                    setImageDrawable(drawable);
                }
                obtainStyledAttributes2.recycle();
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OverflowMenuButton extends FrameLayout implements ActionMenuView.ActionMenuChildView {
        public final ViewGroup mBadgeBackground;
        public CharSequence mBadgeContentDescription;
        public final TextView mBadgeText;
        public CharSequence mContentDescription;
        public final View mInnerView;

        public OverflowMenuButton(Context context) {
            super(context);
            View overflowTextView = ActionMenuPresenter.this.mUseTextItemMode ? ActionMenuPresenter.this.new OverflowTextView(context) : ActionMenuPresenter.this.new OverflowImageView(context);
            this.mInnerView = overflowTextView;
            addView(overflowTextView, new FrameLayout.LayoutParams(-2, -2));
            Resources resources = getResources();
            if (overflowTextView instanceof OverflowImageView) {
                this.mContentDescription = overflowTextView.getContentDescription();
                this.mBadgeContentDescription = ((Object) this.mContentDescription) + " , " + resources.getString(R.string.sesl_action_menu_overflow_badge_description);
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
        public final void onConfigurationChanged(Configuration configuration) {
            float f;
            super.onConfigurationChanged(configuration);
            Resources resources = getResources();
            this.mBadgeText.setTextSize(0, (int) resources.getDimension(R.dimen.sesl_menu_item_badge_text_size));
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBadgeBackground.getLayoutParams();
            CharSequence text = this.mBadgeText.getText();
            if (text == null || text.toString() == null) {
                float dimension = resources.getDimension(R.dimen.sesl_badge_default_width);
                if (text != null) {
                    f = resources.getDimension(R.dimen.sesl_badge_additional_width) * text.length();
                } else {
                    f = 0.0f;
                }
                marginLayoutParams.width = (int) (dimension + f);
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
                this.mBadgeContentDescription = ((Object) this.mContentDescription) + " , " + resources.getString(R.string.sesl_action_menu_overflow_badge_description);
            }
            if (TextUtils.isEmpty(this.mContentDescription)) {
                this.mContentDescription = resources.getString(R.string.sesl_action_menu_overflow_description);
                this.mBadgeContentDescription = ((Object) this.mContentDescription) + " , " + resources.getString(R.string.sesl_action_menu_overflow_badge_description);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OverflowPopup extends MenuPopupHelper {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OverflowTextView extends AppCompatTextView {
        public OverflowTextView(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R$styleable.AppCompatTheme, 0, 0);
            setTextAppearance(obtainStyledAttributes.getResourceId(26, 0));
            obtainStyledAttributes.recycle();
            setText(getResources().getString(R.string.sesl_more_item_label));
            boolean isLightTheme = SeslMisc.isLightTheme(context);
            ActionMenuPresenter.this.getClass();
            if (isLightTheme) {
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

        @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
        public final void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PopupPresenterCallback implements MenuPresenter.Callback {
        public PopupPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder instanceof SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false);
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
            actionMenuPresenter.mOpenSubMenuId = ((SubMenuBuilder) menuBuilder).mItem.mId;
            MenuPresenter.Callback callback = actionMenuPresenter.mCallback;
            if (callback != null) {
                return callback.onOpenSubMenu(menuBuilder);
            }
            return false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.appcompat.widget.ActionMenuPresenter.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int openSubMenuId;

        public SavedState() {
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.openSubMenuId);
        }

        public SavedState(Parcel parcel) {
            this.openSubMenuId = parcel.readInt();
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public final boolean flagActionItems() {
        ArrayList arrayList;
        int i;
        boolean z;
        boolean z2;
        ActionMenuPresenter actionMenuPresenter = this;
        MenuBuilder menuBuilder = actionMenuPresenter.mMenu;
        View view = null;
        boolean z3 = false;
        if (menuBuilder != null) {
            arrayList = menuBuilder.getVisibleItems();
            i = arrayList.size();
        } else {
            arrayList = null;
            i = 0;
        }
        int i2 = actionMenuPresenter.mMaxItems;
        int i3 = actionMenuPresenter.mActionItemWidthLimit;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        Object obj = actionMenuPresenter.mMenuView;
        if (obj == null) {
            Log.d("ActionMenuPresenter", "mMenuView is null, maybe Menu has not been initialized.");
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) obj;
        int i4 = 0;
        boolean z4 = false;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            z = 1;
            if (i4 >= i) {
                break;
            }
            MenuItemImpl menuItemImpl = (MenuItemImpl) arrayList.get(i4);
            if (menuItemImpl.requiresActionButton()) {
                i5++;
            } else if ((menuItemImpl.mShowAsAction & 1) == 1) {
                i6++;
            } else {
                z4 = true;
            }
            if (actionMenuPresenter.mExpandedActionViewsExclusive && menuItemImpl.mIsActionViewExpanded) {
                i2 = 0;
            }
            i4++;
        }
        if (actionMenuPresenter.mReserveOverflow && (z4 || i6 + i5 > i2)) {
            i2--;
        }
        int i7 = i2 - i5;
        SparseBooleanArray sparseBooleanArray = actionMenuPresenter.mActionButtonGroups;
        sparseBooleanArray.clear();
        int i8 = 0;
        int i9 = 0;
        ActionMenuPresenter actionMenuPresenter2 = actionMenuPresenter;
        while (i8 < i) {
            MenuItemImpl menuItemImpl2 = (MenuItemImpl) arrayList.get(i8);
            if (menuItemImpl2.requiresActionButton()) {
                View itemView = actionMenuPresenter2.getItemView(menuItemImpl2, view, viewGroup);
                itemView.measure(makeMeasureSpec, makeMeasureSpec);
                int measuredWidth = itemView.getMeasuredWidth();
                i3 -= measuredWidth;
                if (i9 == 0) {
                    i9 = measuredWidth;
                }
                int i10 = menuItemImpl2.mGroup;
                if (i10 != 0) {
                    sparseBooleanArray.put(i10, z);
                }
                menuItemImpl2.setIsActionButton(z);
                z2 = z3;
            } else if ((menuItemImpl2.mShowAsAction & z) == z ? z : z3) {
                int i11 = menuItemImpl2.mGroup;
                boolean z5 = sparseBooleanArray.get(i11);
                boolean z6 = ((i7 > 0 || z5) && i3 > 0) ? z : z3;
                if (z6) {
                    View itemView2 = actionMenuPresenter2.getItemView(menuItemImpl2, view, viewGroup);
                    itemView2.measure(makeMeasureSpec, makeMeasureSpec);
                    int measuredWidth2 = itemView2.getMeasuredWidth();
                    i3 -= measuredWidth2;
                    if (i9 == 0) {
                        i9 = measuredWidth2;
                    }
                    z6 &= i3 >= 0 ? z : false;
                }
                boolean z7 = z6;
                if (z7 && i11 != 0) {
                    sparseBooleanArray.put(i11, z);
                } else if (z5) {
                    sparseBooleanArray.put(i11, false);
                    for (int i12 = 0; i12 < i8; i12++) {
                        MenuItemImpl menuItemImpl3 = (MenuItemImpl) arrayList.get(i12);
                        if (menuItemImpl3.mGroup == i11) {
                            if ((menuItemImpl3.mFlags & 32) == 32) {
                                i7++;
                            }
                            menuItemImpl3.setIsActionButton(false);
                        }
                    }
                }
                if (z7) {
                    i7--;
                }
                menuItemImpl2.setIsActionButton(z7);
                z2 = false;
            } else {
                z2 = z3;
                menuItemImpl2.setIsActionButton(z2);
            }
            i8++;
            z3 = z2;
            view = null;
            z = 1;
            actionMenuPresenter2 = this;
        }
        return z;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public final View getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        View actionView = menuItemImpl.getActionView();
        if (actionView == null || menuItemImpl.hasCollapsibleActionView()) {
            actionView = super.getItemView(menuItemImpl, view, viewGroup);
        }
        actionView.setVisibility(menuItemImpl.mIsActionViewExpanded ? 8 : 0);
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!((ActionMenuView) viewGroup).checkLayoutParams(layoutParams)) {
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
        int i = this.mWidthLimit;
        if (this.mReserveOverflow) {
            if (this.mOverflowButton == null) {
                OverflowMenuButton overflowMenuButton = new OverflowMenuButton(this.mSystemContext);
                this.mOverflowButton = overflowMenuButton;
                overflowMenuButton.setId(R.id.sesl_action_bar_overflow_button);
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.mOverflowButton.measure(makeMeasureSpec, makeMeasureSpec);
            }
            i -= this.mOverflowButton.getMeasuredWidth();
        } else {
            this.mOverflowButton = null;
        }
        this.mActionItemWidthLimit = i;
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

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void onRestoreInstanceState(Parcelable parcelable) {
        int i;
        MenuBuilder menuBuilder;
        MenuItem findItem;
        if ((parcelable instanceof SavedState) && (i = ((SavedState) parcelable).openSubMenuId) > 0 && (menuBuilder = this.mMenu) != null && (findItem = menuBuilder.findItem(i)) != null) {
            onSubMenuSelected((SubMenuBuilder) findItem.getSubMenu());
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.openSubMenuId = this.mOpenSubMenuId;
        return savedState;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x008c  */
    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        View view;
        boolean z;
        boolean z2 = false;
        if (subMenuBuilder == null || !subMenuBuilder.hasVisibleItems()) {
            return false;
        }
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
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                view = viewGroup.getChildAt(i);
                if ((view instanceof MenuView.ItemView) && ((MenuView.ItemView) view).getItemData() == menuItemImpl) {
                    break;
                }
            }
        }
        view = 0;
        if (view == 0) {
            return false;
        }
        this.mOpenSubMenuId = subMenuBuilder.mItem.mId;
        int size = subMenuBuilder.size();
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
            if (actionButtonSubmenu.mAnchorView != null) {
                actionButtonSubmenu.showPopup(false, false);
            }
            if (z2) {
                throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
            }
            super.onSubMenuSelected(subMenuBuilder);
            return true;
        }
        z2 = true;
        if (z2) {
        }
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:81:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0091  */
    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateMenuView(boolean z) {
        ArrayList arrayList;
        boolean z2;
        OverflowMenuButton overflowMenuButton;
        OverflowMenuButton overflowMenuButton2;
        MenuView menuView;
        MenuView menuView2;
        ActionMenuView actionMenuView;
        String str;
        int i;
        int i2;
        String format;
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
        if (this.mReserveOverflow && arrayList != null) {
            int size2 = arrayList.size();
            if (size2 == 1) {
                z2 = !((MenuItemImpl) arrayList.get(0)).mIsActionViewExpanded;
            } else if (size2 > 0) {
                z2 = true;
            }
            if (z2) {
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
            } else {
                if (this.mOverflowButton == null) {
                    OverflowMenuButton overflowMenuButton4 = new OverflowMenuButton(this.mSystemContext);
                    this.mOverflowButton = overflowMenuButton4;
                    overflowMenuButton4.setId(R.id.sesl_action_bar_overflow_button);
                }
                ViewGroup viewGroup = (ViewGroup) this.mOverflowButton.getParent();
                if (viewGroup != this.mMenuView) {
                    if (viewGroup != null) {
                        viewGroup.removeView(this.mOverflowButton);
                    }
                    ActionMenuView actionMenuView2 = (ActionMenuView) this.mMenuView;
                    if (actionMenuView2 != null) {
                        OverflowMenuButton overflowMenuButton5 = this.mOverflowButton;
                        int i4 = ActionMenuView.$r8$clinit;
                        ActionMenuView.LayoutParams layoutParams = new ActionMenuView.LayoutParams(-2, -2);
                        ((LinearLayout.LayoutParams) layoutParams).gravity = 16;
                        layoutParams.isOverflowButton = true;
                        actionMenuView2.addView(overflowMenuButton5, layoutParams);
                    }
                }
            }
            overflowMenuButton = this.mOverflowButton;
            if (overflowMenuButton != null && (menuView2 = this.mMenuView) != null) {
                actionMenuView = (ActionMenuView) menuView2;
                str = actionMenuView.mOverflowBadgeText;
                if (actionMenuView.mMenu != null) {
                    i = 0;
                } else {
                    i = 0;
                    for (int i5 = 0; i5 < actionMenuView.mMenu.size(); i5++) {
                        MenuItemImpl menuItemImpl = (MenuItemImpl) actionMenuView.mMenu.getItem(i5);
                        if (menuItemImpl.isVisible()) {
                            String str2 = menuItemImpl.mBadgeText;
                            if (str2 == null) {
                                i2 = 0;
                            } else {
                                try {
                                    i2 = Integer.parseInt(str2);
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
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) overflowMenuButton.mBadgeBackground.getLayoutParams();
                if (str == null) {
                    dimension = (int) overflowMenuButton.getResources().getDimension(R.dimen.sesl_menu_item_badge_size);
                    dimension2 = (int) overflowMenuButton.getResources().getDimension(R.dimen.sesl_menu_item_badge_size);
                    format = "";
                } else {
                    format = ActionMenuPresenter.this.mNumberFormat.format(i);
                    dimension = (int) ((overflowMenuButton.getResources().getDimension(R.dimen.sesl_badge_additional_width) * format.length()) + overflowMenuButton.getResources().getDimension(R.dimen.sesl_badge_default_width));
                    dimension2 = (int) (overflowMenuButton.getResources().getDimension(R.dimen.sesl_badge_additional_width) + overflowMenuButton.getResources().getDimension(R.dimen.sesl_badge_default_width));
                    marginLayoutParams.topMargin = (int) overflowMenuButton.getResources().getDimension(R.dimen.sesl_menu_item_number_badge_top_margin);
                    marginLayoutParams.setMarginEnd((int) overflowMenuButton.getResources().getDimension(R.dimen.sesl_menu_item_number_badge_end_margin));
                }
                overflowMenuButton.mBadgeText.setText(format);
                marginLayoutParams.width = dimension;
                marginLayoutParams.height = dimension2;
                overflowMenuButton.mBadgeBackground.setLayoutParams(marginLayoutParams);
                overflowMenuButton.mBadgeBackground.setVisibility(i <= 0 ? 8 : 0);
                if (overflowMenuButton.mBadgeBackground.getVisibility() != 0) {
                    View view = overflowMenuButton.mInnerView;
                    if (view instanceof OverflowImageView) {
                        view.setContentDescription(overflowMenuButton.mBadgeContentDescription);
                    }
                } else {
                    View view2 = overflowMenuButton.mInnerView;
                    if (view2 instanceof OverflowImageView) {
                        view2.setContentDescription(overflowMenuButton.mContentDescription);
                    }
                }
            }
            overflowMenuButton2 = this.mOverflowButton;
            if ((overflowMenuButton2 != null || overflowMenuButton2.getVisibility() != 0) && isOverflowMenuShowing()) {
                hideOverflowMenu();
            }
            menuView = this.mMenuView;
            if (menuView == null) {
                ((ActionMenuView) menuView).mReserveOverflow = this.mReserveOverflow;
                return;
            }
            return;
        }
        z2 = false;
        if (z2) {
        }
        overflowMenuButton = this.mOverflowButton;
        if (overflowMenuButton != null) {
            actionMenuView = (ActionMenuView) menuView2;
            str = actionMenuView.mOverflowBadgeText;
            if (actionMenuView.mMenu != null) {
            }
            if (i > 99) {
            }
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) overflowMenuButton.mBadgeBackground.getLayoutParams();
            if (str == null) {
            }
            overflowMenuButton.mBadgeText.setText(format);
            marginLayoutParams2.width = dimension;
            marginLayoutParams2.height = dimension2;
            overflowMenuButton.mBadgeBackground.setLayoutParams(marginLayoutParams2);
            overflowMenuButton.mBadgeBackground.setVisibility(i <= 0 ? 8 : 0);
            if (overflowMenuButton.mBadgeBackground.getVisibility() != 0) {
            }
        }
        overflowMenuButton2 = this.mOverflowButton;
        if (overflowMenuButton2 != null) {
        }
        hideOverflowMenu();
        menuView = this.mMenuView;
        if (menuView == null) {
        }
    }
}
