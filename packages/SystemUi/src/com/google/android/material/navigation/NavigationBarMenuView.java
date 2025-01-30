package com.google.android.material.navigation;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SeslMenuItem;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pools$SynchronizedPool;
import androidx.core.view.ViewCompat;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.TextScale;
import com.google.android.material.navigation.NavigationBarPresenter;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.HashSet;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NavigationBarMenuView extends ViewGroup implements MenuView {
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public static final int[] DISABLED_STATE_SET = {-16842910};
    public final SparseArray badgeDrawables;
    public NavigationBarItemView[] buttons;
    public ColorStateList itemActiveIndicatorColor;
    public ShapeAppearanceModel itemActiveIndicatorShapeAppearance;
    public int itemBackgroundRes;
    public int itemIconSize;
    public ColorStateList itemIconTint;
    public final Pools$SynchronizedPool itemPool;
    public int itemTextAppearanceActive;
    public int itemTextAppearanceInactive;
    public final ColorStateList itemTextColorDefault;
    public ColorStateList itemTextColorFromUser;
    public int labelVisibilityMode;
    public final ContentResolver mContentResolver;
    public MenuBuilder mDummyMenu;
    public boolean mHasOverflowMenu;
    public InternalBtnInfo mInvisibleBtns;
    public int mMaxItemCount;
    public NavigationBarItemView mOverflowButton;
    public MenuBuilder mOverflowMenu;
    public ColorDrawable mSBBTextColorDrawable;
    public MenuBuilder.Callback mSelectedCallback;
    public int mSeslLabelTextAppearance;
    public boolean mUseItemPool;
    public int mViewType;
    public int mViewVisibleItemCount;
    public InternalBtnInfo mVisibleBtns;
    public int mVisibleItemCount;
    public MenuBuilder menu;
    public final ViewOnClickListenerC43151 onClickListener;
    public NavigationBarPresenter presenter;
    public int selectedItemId;
    public int selectedItemPosition;
    public final AutoTransition set;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InternalBtnInfo {
        public int cnt = 0;
        public final int[] originPos;

        public InternalBtnInfo(int i) {
            this.originPos = new int[i];
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.google.android.material.navigation.NavigationBarMenuView$1] */
    public NavigationBarMenuView(Context context) {
        super(context);
        this.itemPool = new Pools$SynchronizedPool(5);
        new SparseArray(5);
        this.selectedItemId = 0;
        this.selectedItemPosition = 0;
        this.badgeDrawables = new SparseArray(5);
        this.mViewType = 1;
        this.mVisibleBtns = null;
        this.mInvisibleBtns = null;
        this.mOverflowButton = null;
        this.mHasOverflowMenu = false;
        this.mOverflowMenu = null;
        this.mVisibleItemCount = 0;
        this.mViewVisibleItemCount = 0;
        this.mMaxItemCount = 0;
        this.mUseItemPool = true;
        this.itemTextColorDefault = createDefaultColorStateList();
        if (isInEditMode()) {
            this.set = null;
        } else {
            AutoTransition autoTransition = new AutoTransition();
            this.set = autoTransition;
            autoTransition.setOrdering(0);
            autoTransition.setDuration(0L);
            autoTransition.addTransition(new TextScale());
        }
        this.onClickListener = new View.OnClickListener() { // from class: com.google.android.material.navigation.NavigationBarMenuView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MenuItemImpl menuItemImpl = ((NavigationBarItemView) view).itemData;
                NavigationBarMenuView navigationBarMenuView = NavigationBarMenuView.this;
                if (navigationBarMenuView.menu.performItemAction(menuItemImpl, navigationBarMenuView.presenter, 0)) {
                    return;
                }
                menuItemImpl.setChecked(true);
            }
        };
        this.mContentResolver = context.getContentResolver();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(this, 1);
    }

    /* JADX WARN: Type inference failed for: r0v74 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9, types: [boolean, int] */
    public final void buildMenuView() {
        MenuItemImpl menuItemImpl;
        MenuBuilder menuBuilder;
        int i;
        BadgeDrawable badgeDrawable;
        removeAllViews();
        TransitionManager.beginDelayedTransition(this.set, this);
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null && this.mUseItemPool) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView != null) {
                    seslRemoveBadge(navigationBarItemView.getId());
                    this.itemPool.release(navigationBarItemView);
                    ImageView imageView = navigationBarItemView.icon;
                    if (navigationBarItemView.badgeDrawable != null) {
                        if (imageView != null) {
                            navigationBarItemView.setClipChildren(true);
                            navigationBarItemView.setClipToPadding(true);
                            BadgeDrawable badgeDrawable2 = navigationBarItemView.badgeDrawable;
                            if (badgeDrawable2 != null) {
                                if (badgeDrawable2.getCustomBadgeParent() != null) {
                                    badgeDrawable2.getCustomBadgeParent().setForeground(null);
                                } else {
                                    imageView.getOverlay().remove(badgeDrawable2);
                                }
                            }
                        }
                        navigationBarItemView.badgeDrawable = null;
                    }
                    navigationBarItemView.itemData = null;
                    navigationBarItemView.activeIndicatorProgress = 0.0f;
                    navigationBarItemView.initialized = false;
                }
            }
        }
        if (this.mOverflowButton != null) {
            seslRemoveBadge(com.android.systemui.R.id.bottom_overflow);
        }
        int size = this.menu.size();
        if (size == 0) {
            this.selectedItemId = 0;
            this.selectedItemPosition = 0;
            this.buttons = null;
            this.mVisibleItemCount = 0;
            this.mOverflowButton = null;
            this.mOverflowMenu = null;
            this.mVisibleBtns = null;
            this.mInvisibleBtns = null;
            return;
        }
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < this.menu.size(); i2++) {
            hashSet.add(Integer.valueOf(this.menu.getItem(i2).getItemId()));
        }
        for (int i3 = 0; i3 < this.badgeDrawables.size(); i3++) {
            int keyAt = this.badgeDrawables.keyAt(i3);
            if (!hashSet.contains(Integer.valueOf(keyAt))) {
                this.badgeDrawables.delete(keyAt);
            }
        }
        int i4 = this.labelVisibilityMode;
        this.menu.getVisibleItems().size();
        boolean z = i4 == 0;
        this.buttons = new NavigationBarItemView[this.menu.size()];
        this.mVisibleBtns = new InternalBtnInfo(size);
        this.mInvisibleBtns = new InternalBtnInfo(size);
        this.mOverflowMenu = new MenuBuilder(getContext());
        this.mVisibleBtns.cnt = 0;
        this.mInvisibleBtns.cnt = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < size; i7++) {
            this.presenter.updateSuspended = true;
            this.menu.getItem(i7).setCheckable(true);
            this.presenter.updateSuspended = false;
            if (((MenuItemImpl) this.menu.getItem(i7)).requiresOverflow()) {
                InternalBtnInfo internalBtnInfo = this.mInvisibleBtns;
                int[] iArr = internalBtnInfo.originPos;
                int i8 = internalBtnInfo.cnt;
                internalBtnInfo.cnt = i8 + 1;
                iArr[i8] = i7;
                if (!this.menu.getItem(i7).isVisible()) {
                    i5++;
                }
            } else {
                InternalBtnInfo internalBtnInfo2 = this.mVisibleBtns;
                int[] iArr2 = internalBtnInfo2.originPos;
                int i9 = internalBtnInfo2.cnt;
                internalBtnInfo2.cnt = i9 + 1;
                iArr2[i9] = i7;
                if (this.menu.getItem(i7).isVisible()) {
                    i6++;
                }
            }
        }
        ?? r0 = this.mInvisibleBtns.cnt - i5 > 0 ? 1 : 0;
        this.mHasOverflowMenu = r0;
        int i10 = i6 + r0;
        int i11 = this.mMaxItemCount;
        if (i10 > i11) {
            int i12 = i10 - (i11 - 1);
            if (r0 != 0) {
                i12--;
            }
            for (int i13 = this.mVisibleBtns.cnt - 1; i13 >= 0; i13--) {
                if (this.menu.getItem(this.mVisibleBtns.originPos[i13]).isVisible()) {
                    InternalBtnInfo internalBtnInfo3 = this.mInvisibleBtns;
                    int[] iArr3 = internalBtnInfo3.originPos;
                    int i14 = internalBtnInfo3.cnt;
                    internalBtnInfo3.cnt = i14 + 1;
                    InternalBtnInfo internalBtnInfo4 = this.mVisibleBtns;
                    iArr3[i14] = internalBtnInfo4.originPos[i13];
                    internalBtnInfo4.cnt--;
                    i12--;
                    if (i12 == 0) {
                        break;
                    }
                } else {
                    InternalBtnInfo internalBtnInfo5 = this.mInvisibleBtns;
                    int[] iArr4 = internalBtnInfo5.originPos;
                    int i15 = internalBtnInfo5.cnt;
                    internalBtnInfo5.cnt = i15 + 1;
                    InternalBtnInfo internalBtnInfo6 = this.mVisibleBtns;
                    iArr4[i15] = internalBtnInfo6.originPos[i13];
                    internalBtnInfo6.cnt--;
                }
            }
        }
        this.mVisibleItemCount = 0;
        this.mViewVisibleItemCount = 0;
        int i16 = 0;
        while (true) {
            InternalBtnInfo internalBtnInfo7 = this.mVisibleBtns;
            if (i16 >= internalBtnInfo7.cnt) {
                break;
            }
            int i17 = internalBtnInfo7.originPos[i16];
            if (this.buttons != null) {
                final int i18 = this.mViewType;
                NavigationBarItemView navigationBarItemView2 = (NavigationBarItemView) this.itemPool.acquire();
                if (navigationBarItemView2 == null) {
                    navigationBarItemView2 = new NavigationBarItemView(this, getContext(), i18) { // from class: com.google.android.material.navigation.NavigationBarMenuView.3
                        @Override // com.google.android.material.navigation.NavigationBarItemView
                        public final int getItemLayoutResId() {
                            return i18 != 3 ? com.android.systemui.R.layout.sesl_bottom_navigation_item : com.android.systemui.R.layout.sesl_bottom_navigation_item_text;
                        }
                    };
                }
                this.buttons[this.mVisibleItemCount] = navigationBarItemView2;
                navigationBarItemView2.setVisibility(this.menu.getItem(i17).isVisible() ? 0 : 8);
                navigationBarItemView2.setIconTintList(this.itemIconTint);
                navigationBarItemView2.setIconSize(this.itemIconSize);
                navigationBarItemView2.setTextColor(this.itemTextColorDefault);
                navigationBarItemView2.seslSetLabelTextAppearance(this.mSeslLabelTextAppearance);
                navigationBarItemView2.setTextAppearanceInactive(this.itemTextAppearanceInactive);
                navigationBarItemView2.setTextAppearanceActive(this.itemTextAppearanceActive);
                navigationBarItemView2.setTextColor(this.itemTextColorFromUser);
                navigationBarItemView2.setItemBackground(this.itemBackgroundRes);
                if (navigationBarItemView2.isShifting != z) {
                    navigationBarItemView2.isShifting = z;
                    MenuItemImpl menuItemImpl2 = navigationBarItemView2.itemData;
                    if (menuItemImpl2 != null) {
                        navigationBarItemView2.setChecked(menuItemImpl2.isChecked());
                    }
                }
                navigationBarItemView2.setLabelVisibilityMode(this.labelVisibilityMode);
                navigationBarItemView2.initialize((MenuItemImpl) this.menu.getItem(i17));
                navigationBarItemView2.setOnClickListener(this.onClickListener);
                if (this.selectedItemId != 0 && this.menu.getItem(i17).getItemId() == this.selectedItemId) {
                    this.selectedItemPosition = this.mVisibleItemCount;
                }
                MenuItemImpl menuItemImpl3 = (MenuItemImpl) this.menu.getItem(i17);
                String str = menuItemImpl3.mBadgeText;
                if (str != null) {
                    seslAddBadge(menuItemImpl3.mId, str);
                } else {
                    seslRemoveBadge(menuItemImpl3.mId);
                }
                int id = navigationBarItemView2.getId();
                if ((id != -1) && (badgeDrawable = (BadgeDrawable) this.badgeDrawables.get(id)) != null) {
                    navigationBarItemView2.setBadge(badgeDrawable);
                }
                if (navigationBarItemView2.getParent() instanceof ViewGroup) {
                    ((ViewGroup) navigationBarItemView2.getParent()).removeView(navigationBarItemView2);
                }
                addView(navigationBarItemView2);
                this.mVisibleItemCount++;
                if (navigationBarItemView2.getVisibility() == 0) {
                    this.mViewVisibleItemCount++;
                }
            }
            i16++;
        }
        if (this.mInvisibleBtns.cnt > 0) {
            int i19 = 0;
            int i20 = 0;
            while (true) {
                InternalBtnInfo internalBtnInfo8 = this.mInvisibleBtns;
                i = internalBtnInfo8.cnt;
                if (i19 >= i) {
                    break;
                }
                MenuItemImpl menuItemImpl4 = (MenuItemImpl) this.menu.getItem(internalBtnInfo8.originPos[i19]);
                if (menuItemImpl4 != null) {
                    CharSequence charSequence = menuItemImpl4.mTitle;
                    if (charSequence == null) {
                        charSequence = menuItemImpl4.mContentDescription;
                    }
                    MenuItemImpl addInternal = this.mOverflowMenu.addInternal(menuItemImpl4.mGroup, menuItemImpl4.mId, menuItemImpl4.mCategoryOrder, charSequence);
                    addInternal.setVisible(menuItemImpl4.isVisible());
                    addInternal.setEnabled(menuItemImpl4.isEnabled());
                    this.mOverflowMenu.mGroupDividerEnabled = false;
                    menuItemImpl4.setBadgeText(menuItemImpl4.mBadgeText);
                    if (!menuItemImpl4.isVisible()) {
                        i20++;
                    }
                }
                i19++;
            }
            if (i - i20 > 0) {
                this.mHasOverflowMenu = true;
                this.mDummyMenu = new MenuBuilder(getContext());
                new MenuInflater(getContext()).inflate(com.android.systemui.R.menu.nv_dummy_overflow_menu_icon, this.mDummyMenu);
                if (this.mDummyMenu.getItem(0) instanceof MenuItemImpl) {
                    MenuItemImpl menuItemImpl5 = (MenuItemImpl) this.mDummyMenu.getItem(0);
                    if (this.mViewType == 1) {
                        menuItemImpl5.setTooltipText((CharSequence) null);
                    } else {
                        menuItemImpl5.setTooltipText((CharSequence) getResources().getString(com.android.systemui.R.string.sesl_more_item_label));
                    }
                }
                final int i21 = this.mViewType;
                NavigationBarItemView navigationBarItemView3 = (NavigationBarItemView) this.itemPool.acquire();
                if (navigationBarItemView3 == null) {
                    navigationBarItemView3 = new NavigationBarItemView(this, getContext(), i21) { // from class: com.google.android.material.navigation.NavigationBarMenuView.3
                        @Override // com.google.android.material.navigation.NavigationBarItemView
                        public final int getItemLayoutResId() {
                            return i21 != 3 ? com.android.systemui.R.layout.sesl_bottom_navigation_item : com.android.systemui.R.layout.sesl_bottom_navigation_item_text;
                        }
                    };
                }
                navigationBarItemView3.setIconTintList(this.itemIconTint);
                navigationBarItemView3.setIconSize(this.itemIconSize);
                navigationBarItemView3.setTextColor(this.itemTextColorDefault);
                navigationBarItemView3.seslSetLabelTextAppearance(this.mSeslLabelTextAppearance);
                navigationBarItemView3.setTextAppearanceInactive(this.itemTextAppearanceInactive);
                navigationBarItemView3.setTextAppearanceActive(this.itemTextAppearanceActive);
                navigationBarItemView3.setTextColor(this.itemTextColorFromUser);
                navigationBarItemView3.setItemBackground(this.itemBackgroundRes);
                if (navigationBarItemView3.isShifting != z) {
                    navigationBarItemView3.isShifting = z;
                    MenuItemImpl menuItemImpl6 = navigationBarItemView3.itemData;
                    if (menuItemImpl6 != null) {
                        navigationBarItemView3.setChecked(menuItemImpl6.isChecked());
                    }
                }
                navigationBarItemView3.setLabelVisibilityMode(this.labelVisibilityMode);
                navigationBarItemView3.initialize((MenuItemImpl) this.mDummyMenu.getItem(0));
                navigationBarItemView3.mBadgeType = 0;
                navigationBarItemView3.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.navigation.NavigationBarMenuView.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        NavigationBarMenuView navigationBarMenuView = NavigationBarMenuView.this;
                        MenuBuilder menuBuilder2 = navigationBarMenuView.mOverflowMenu;
                        menuBuilder2.mCallback = navigationBarMenuView.mSelectedCallback;
                        navigationBarMenuView.presenter.showOverflowMenu(menuBuilder2);
                    }
                });
                navigationBarItemView3.setContentDescription(getResources().getString(com.android.systemui.R.string.sesl_action_menu_overflow_description));
                if (this.mViewType == 3) {
                    Drawable drawable = getContext().getDrawable(com.android.systemui.R.drawable.sesl_ic_menu_overflow_dark);
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(" ");
                    ImageSpan imageSpan = new ImageSpan(drawable);
                    drawable.setState(new int[]{R.attr.state_enabled, -16842910});
                    drawable.setTintList(this.itemTextColorFromUser);
                    drawable.setBounds(0, 0, getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_bottom_navigation_icon_size), getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_bottom_navigation_icon_size));
                    spannableStringBuilder.setSpan(imageSpan, 0, 1, 18);
                    navigationBarItemView3.mLabelImgSpan = spannableStringBuilder;
                    navigationBarItemView3.smallLabel.setText(spannableStringBuilder);
                    navigationBarItemView3.largeLabel.setText(spannableStringBuilder);
                }
                if (navigationBarItemView3.getParent() instanceof ViewGroup) {
                    ((ViewGroup) navigationBarItemView3.getParent()).removeView(navigationBarItemView3);
                }
                addView(navigationBarItemView3);
                this.mOverflowButton = navigationBarItemView3;
                this.buttons[this.mVisibleBtns.cnt] = navigationBarItemView3;
                this.mVisibleItemCount++;
                this.mViewVisibleItemCount++;
                navigationBarItemView3.setVisibility(0);
            }
        }
        if (this.mViewVisibleItemCount > this.mMaxItemCount) {
            StringBuilder sb = new StringBuilder("Maximum number of visible items supported by BottomNavigationView is ");
            sb.append(this.mMaxItemCount);
            sb.append(". Current visible count is ");
            TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, this.mViewVisibleItemCount, "NavigationBarMenuView");
            int i22 = this.mMaxItemCount;
            this.mVisibleItemCount = i22;
            this.mViewVisibleItemCount = i22;
        }
        int i23 = 0;
        while (true) {
            NavigationBarItemView[] navigationBarItemViewArr2 = this.buttons;
            if (i23 >= navigationBarItemViewArr2.length) {
                int min = Math.min(this.mMaxItemCount - 1, this.selectedItemPosition);
                this.selectedItemPosition = min;
                this.menu.getItem(min).setChecked(true);
                return;
            }
            NavigationBarItemView navigationBarItemView4 = navigationBarItemViewArr2[i23];
            if (navigationBarItemView4 != null) {
                ColorStateList colorStateList = this.itemTextColorFromUser;
                if (Settings.System.getInt(this.mContentResolver, "show_button_background", 0) == 1) {
                    ColorDrawable colorDrawable = this.mSBBTextColorDrawable;
                    int color = colorDrawable != null ? colorDrawable.getColor() : getResources().getColor(SeslMisc.isLightTheme(getContext()) ? com.android.systemui.R.color.sesl_bottom_navigation_background_light : com.android.systemui.R.color.sesl_bottom_navigation_background_dark, null);
                    Drawable drawable2 = navigationBarItemView4.getResources().getDrawable(com.android.systemui.R.drawable.sesl_bottom_nav_show_button_shapes_background);
                    navigationBarItemView4.smallLabel.setTextColor(color);
                    navigationBarItemView4.largeLabel.setTextColor(color);
                    navigationBarItemView4.smallLabel.setBackground(drawable2);
                    navigationBarItemView4.largeLabel.setBackground(drawable2);
                    navigationBarItemView4.smallLabel.setBackgroundTintList(colorStateList);
                    navigationBarItemView4.largeLabel.setBackgroundTintList(colorStateList);
                    if (this.mOverflowButton != null && (menuItemImpl = navigationBarItemView4.itemData) != null && (menuBuilder = this.mDummyMenu) != null && menuItemImpl.mId == menuBuilder.getItem(0).getItemId()) {
                        setOverflowSpanColor(color, false);
                    }
                }
            }
            i23++;
        }
    }

    public final ColorStateList createDefaultColorStateList() {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(R.attr.textColorSecondary, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateList = ContextCompat.getColorStateList(typedValue.resourceId, getContext());
        if (!getContext().getTheme().resolveAttribute(com.android.systemui.R.attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i = typedValue.data;
        int defaultColor = colorStateList.getDefaultColor();
        int[] iArr = DISABLED_STATE_SET;
        return new ColorStateList(new int[][]{iArr, CHECKED_STATE_SET, ViewGroup.EMPTY_STATE_SET}, new int[]{colorStateList.getColorForState(iArr, defaultColor), i, defaultColor});
    }

    public final NavigationBarItemView findItemView(int i) {
        if (!(i != -1)) {
            throw new IllegalArgumentException(i + " is not a valid view id");
        }
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr == null) {
            return null;
        }
        for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
            if (navigationBarItemView == null) {
                return null;
            }
            if (navigationBarItemView.getId() == i) {
                return navigationBarItemView;
            }
        }
        return null;
    }

    public final void hideOverflowMenu() {
        NavigationBarPresenter navigationBarPresenter;
        Object obj;
        if (!this.mHasOverflowMenu || (navigationBarPresenter = this.presenter) == null) {
            return;
        }
        NavigationBarPresenter.OverflowPopup overflowPopup = navigationBarPresenter.mOverflowPopup;
        if (overflowPopup != null && overflowPopup.isShowing()) {
            NavigationBarPresenter navigationBarPresenter2 = this.presenter;
            NavigationBarPresenter.OpenOverflowRunnable openOverflowRunnable = navigationBarPresenter2.mPostedOpenRunnable;
            if (openOverflowRunnable != null && (obj = navigationBarPresenter2.mMenuView) != null) {
                ((View) obj).removeCallbacks(openOverflowRunnable);
                navigationBarPresenter2.mPostedOpenRunnable = null;
                return;
            }
            NavigationBarPresenter.OverflowPopup overflowPopup2 = navigationBarPresenter2.mOverflowPopup;
            if (overflowPopup2 == null || !overflowPopup2.isShowing()) {
                return;
            }
            overflowPopup2.mPopup.dismiss();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView
    public final void initialize(MenuBuilder menuBuilder) {
        this.menu = menuBuilder;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mViewType != 3) {
            setItemIconSize(getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_bottom_navigation_icon_size));
            NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
            if (navigationBarItemViewArr != null) {
                for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                    if (navigationBarItemView == null) {
                        break;
                    }
                    int dimensionPixelSize = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_bottom_navigation_icon_size);
                    if (navigationBarItemView.labelGroup != null) {
                        navigationBarItemView.defaultMargin = navigationBarItemView.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_bottom_navigation_icon_inset);
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) navigationBarItemView.labelGroup.getLayoutParams();
                        if (marginLayoutParams != null) {
                            marginLayoutParams.topMargin = dimensionPixelSize + navigationBarItemView.defaultMargin;
                            navigationBarItemView.labelGroup.setLayoutParams(marginLayoutParams);
                        }
                    }
                }
            }
        }
        hideOverflowMenu();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void seslAddBadge(int i, String str) {
        TextView textView;
        boolean z;
        NavigationBarItemView findItemView = findItemView(i);
        if (findItemView != null) {
            View findViewById = findItemView.findViewById(com.android.systemui.R.id.notifications_badge_container);
            if (findViewById != null) {
                textView = (TextView) findViewById.findViewById(com.android.systemui.R.id.notifications_badge);
            } else {
                View inflate = LayoutInflater.from(getContext()).inflate(com.android.systemui.R.layout.sesl_navigation_bar_badge_layout, (ViewGroup) this, false);
                TextView textView2 = (TextView) inflate.findViewById(com.android.systemui.R.id.notifications_badge);
                findItemView.addView(inflate);
                textView = textView2;
            }
            if (str != null) {
                try {
                    Integer.parseInt(str);
                    z = true;
                } catch (NumberFormatException unused) {
                }
                if (z) {
                    findItemView.mIsBadgeNumberless = false;
                } else if (Integer.parseInt(str) > 999) {
                    findItemView.mIsBadgeNumberless = true;
                    str = "999+";
                } else {
                    findItemView.mIsBadgeNumberless = false;
                }
            }
            z = false;
            if (z) {
            }
        } else {
            textView = null;
        }
        if (textView != null) {
            textView.setText(str);
        }
        updateBadge(findItemView);
    }

    public final void seslRemoveBadge(int i) {
        View findViewById;
        NavigationBarItemView findItemView = findItemView(i);
        if (findItemView == null || (findViewById = findItemView.findViewById(com.android.systemui.R.id.notifications_badge_container)) == null) {
            return;
        }
        findItemView.removeView(findViewById);
    }

    public final void setIconTintList(ColorStateList colorStateList) {
        this.itemIconTint = colorStateList;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    break;
                }
                navigationBarItemView.setIconTintList(colorStateList);
            }
        }
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        if (navigationBarItemView2 != null) {
            navigationBarItemView2.setIconTintList(colorStateList);
        }
    }

    public final void setItemIconSize(int i) {
        this.itemIconSize = i;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    break;
                }
                navigationBarItemView.setIconSize(i);
            }
        }
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        if (navigationBarItemView2 != null) {
            navigationBarItemView2.setIconSize(i);
        }
    }

    public final void setOverflowSpanColor(int i, boolean z) {
        SpannableStringBuilder spannableStringBuilder;
        NavigationBarItemView navigationBarItemView = this.mOverflowButton;
        if (navigationBarItemView == null || (spannableStringBuilder = navigationBarItemView.mLabelImgSpan) == null) {
            return;
        }
        Drawable drawable = getContext().getDrawable(com.android.systemui.R.drawable.sesl_ic_menu_overflow_dark);
        ImageSpan[] imageSpanArr = (ImageSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ImageSpan.class);
        if (imageSpanArr != null) {
            for (ImageSpan imageSpan : imageSpanArr) {
                spannableStringBuilder.removeSpan(imageSpan);
            }
        }
        ImageSpan imageSpan2 = new ImageSpan(drawable);
        drawable.setState(new int[]{R.attr.state_enabled, -16842910});
        if (z) {
            drawable.setTintList(this.itemTextColorFromUser);
        } else {
            drawable.setTint(i);
        }
        drawable.setBounds(0, 0, getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_bottom_navigation_icon_size), getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_bottom_navigation_icon_size));
        spannableStringBuilder.setSpan(imageSpan2, 0, 1, 18);
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        navigationBarItemView2.mLabelImgSpan = spannableStringBuilder;
        navigationBarItemView2.smallLabel.setText(spannableStringBuilder);
        navigationBarItemView2.largeLabel.setText(spannableStringBuilder);
    }

    public final void updateBadge(NavigationBarItemView navigationBarItemView) {
        TextView textView;
        int i;
        int i2;
        int measuredWidth;
        if (navigationBarItemView == null || (textView = (TextView) navigationBarItemView.findViewById(com.android.systemui.R.id.notifications_badge)) == null) {
            return;
        }
        Resources resources = getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_navigation_bar_num_badge_size);
        float f = getResources().getConfiguration().fontScale;
        if (f > 1.2f) {
            textView.setTextSize(0, (dimensionPixelSize / f) * 1.2f);
        }
        int i3 = navigationBarItemView.mBadgeType;
        int dimensionPixelOffset = resources.getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_bottom_navigation_dot_badge_size);
        int dimensionPixelSize2 = this.mVisibleItemCount == this.mMaxItemCount ? resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_bottom_navigation_icon_mode_min_padding_horizontal) : resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_bottom_navigation_icon_mode_padding_horizontal);
        int dimensionPixelSize3 = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_bottom_navigation_N_badge_top_margin);
        int dimensionPixelSize4 = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_bottom_navigation_N_badge_start_margin);
        TextView textView2 = navigationBarItemView.smallLabel;
        if (textView2 == null) {
            textView2 = navigationBarItemView.largeLabel;
        }
        int width = textView2 == null ? 1 : textView2.getWidth();
        int height = textView2 == null ? 1 : textView2.getHeight();
        if (i3 == 1 || i3 == 0) {
            Drawable drawable = resources.getDrawable(com.android.systemui.R.drawable.sesl_dot_badge);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(textView, drawable);
            i = dimensionPixelOffset;
            i2 = i;
        } else {
            Drawable drawable2 = resources.getDrawable(com.android.systemui.R.drawable.sesl_tab_n_badge);
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(textView, drawable2);
            textView.measure(0, 0);
            i = textView.getMeasuredWidth();
            i2 = textView.getMeasuredHeight();
        }
        if (this.mViewType != 3) {
            if (i3 == 1) {
                measuredWidth = this.itemIconSize / 2;
            } else {
                measuredWidth = (textView.getMeasuredWidth() / 2) - dimensionPixelSize2;
                dimensionPixelOffset /= 2;
            }
        } else if (i3 == 1) {
            measuredWidth = (textView.getMeasuredWidth() + width) / 2;
            dimensionPixelOffset = (navigationBarItemView.getHeight() - height) / 2;
        } else if (i3 == 0) {
            measuredWidth = ((width - textView.getMeasuredWidth()) - dimensionPixelSize4) / 2;
            dimensionPixelOffset = ((navigationBarItemView.getHeight() - height) / 2) - dimensionPixelSize3;
        } else {
            measuredWidth = (textView.getMeasuredWidth() + width) / 2;
            dimensionPixelOffset = ((navigationBarItemView.getHeight() - height) / 2) - dimensionPixelSize3;
            if ((textView.getMeasuredWidth() / 2) + (navigationBarItemView.getWidth() / 2) + measuredWidth > navigationBarItemView.getWidth()) {
                measuredWidth += navigationBarItemView.getWidth() - ((textView.getMeasuredWidth() / 2) + ((navigationBarItemView.getWidth() / 2) + measuredWidth));
            }
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
        int i4 = layoutParams.width;
        int i5 = layoutParams.leftMargin;
        if (i4 == i && i5 == measuredWidth) {
            return;
        }
        layoutParams.width = i;
        layoutParams.height = i2;
        layoutParams.topMargin = dimensionPixelOffset;
        layoutParams.setMarginStart(measuredWidth);
        textView.setLayoutParams(layoutParams);
    }

    public final void updateMenuView() {
        MenuBuilder menuBuilder;
        AutoTransition autoTransition;
        MenuBuilder menuBuilder2 = this.menu;
        if (menuBuilder2 == null || this.buttons == null || this.mVisibleBtns == null || this.mInvisibleBtns == null) {
            return;
        }
        int size = menuBuilder2.size();
        hideOverflowMenu();
        if (size != this.mVisibleBtns.cnt + this.mInvisibleBtns.cnt) {
            buildMenuView();
            return;
        }
        int i = this.selectedItemId;
        int i2 = 0;
        while (true) {
            InternalBtnInfo internalBtnInfo = this.mVisibleBtns;
            if (i2 >= internalBtnInfo.cnt) {
                break;
            }
            MenuItem item = this.menu.getItem(internalBtnInfo.originPos[i2]);
            if (item.isChecked()) {
                this.selectedItemId = item.getItemId();
                this.selectedItemPosition = i2;
            }
            if (item instanceof SeslMenuItem) {
                seslRemoveBadge(item.getItemId());
                String str = ((MenuItemImpl) ((SeslMenuItem) item)).mBadgeText;
                if (str != null) {
                    seslAddBadge(item.getItemId(), str);
                }
            }
            i2++;
        }
        if (i != this.selectedItemId && (autoTransition = this.set) != null) {
            TransitionManager.beginDelayedTransition(autoTransition, this);
        }
        int i3 = this.labelVisibilityMode;
        this.menu.getVisibleItems().size();
        boolean z = i3 == 0;
        for (int i4 = 0; i4 < this.mVisibleBtns.cnt; i4++) {
            this.presenter.updateSuspended = true;
            this.buttons[i4].setLabelVisibilityMode(this.labelVisibilityMode);
            NavigationBarItemView navigationBarItemView = this.buttons[i4];
            if (navigationBarItemView.isShifting != z) {
                navigationBarItemView.isShifting = z;
                MenuItemImpl menuItemImpl = navigationBarItemView.itemData;
                if (menuItemImpl != null) {
                    navigationBarItemView.setChecked(menuItemImpl.isChecked());
                }
            }
            this.buttons[i4].initialize((MenuItemImpl) this.menu.getItem(this.mVisibleBtns.originPos[i4]));
            this.presenter.updateSuspended = false;
        }
        int i5 = 0;
        boolean z2 = false;
        while (true) {
            InternalBtnInfo internalBtnInfo2 = this.mInvisibleBtns;
            if (i5 >= internalBtnInfo2.cnt) {
                break;
            }
            MenuItem item2 = this.menu.getItem(internalBtnInfo2.originPos[i5]);
            if ((item2 instanceof SeslMenuItem) && (menuBuilder = this.mOverflowMenu) != null) {
                SeslMenuItem seslMenuItem = (SeslMenuItem) item2;
                MenuItem findItem = menuBuilder.findItem(item2.getItemId());
                if (findItem instanceof SeslMenuItem) {
                    findItem.setTitle(item2.getTitle());
                    ((MenuItemImpl) ((SeslMenuItem) findItem)).setBadgeText(((MenuItemImpl) seslMenuItem).mBadgeText);
                }
                z2 |= ((MenuItemImpl) seslMenuItem).mBadgeText != null;
            }
            i5++;
        }
        if (z2) {
            seslAddBadge(com.android.systemui.R.id.bottom_overflow, "");
        } else {
            seslRemoveBadge(com.android.systemui.R.id.bottom_overflow);
        }
    }
}
