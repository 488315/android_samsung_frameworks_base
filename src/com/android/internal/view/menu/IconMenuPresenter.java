package com.android.internal.view.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.R;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class IconMenuPresenter extends BaseMenuPresenter {
    private static final String OPEN_SUBMENU_KEY = "android:menu:icon:submenu";
    private static final String VIEWS_TAG = "android:menu:icon";
    private int mMaxItems;
    private IconMenuItemView mMoreView;
    MenuDialogHelper mOpenSubMenu;
    int mOpenSubMenuId;
    SubMenuPresenterCallback mSubMenuPresenterCallback;

    public IconMenuPresenter(Context context) {
        super(new ContextThemeWrapper(context, R.style.Theme_IconMenu), R.layout.icon_menu_layout, R.layout.icon_menu_item_layout);
        this.mMaxItems = -1;
        this.mSubMenuPresenterCallback = new SubMenuPresenterCallback();
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        super.initForMenu(context, menuBuilder);
        this.mMaxItems = -1;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView) {
        IconMenuItemView iconMenuItemView = (IconMenuItemView) itemView;
        iconMenuItemView.setItemData(menuItemImpl);
        iconMenuItemView.initialize(menuItemImpl.getTitleForItemView(iconMenuItemView), menuItemImpl.getIcon());
        iconMenuItemView.setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        iconMenuItemView.setEnabled(iconMenuItemView.isEnabled());
        iconMenuItemView.setLayoutParams(iconMenuItemView.getTextAppropriateLayoutParams());
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public boolean shouldIncludeItem(int i, MenuItemImpl menuItemImpl) {
        int size = this.mMenu.getNonActionItems().size();
        int i2 = this.mMaxItems;
        return ((size == i2 && i < i2) || i < i2 - 1) && !menuItemImpl.isActionButton();
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    protected void addItemView(View view, int i) {
        IconMenuItemView iconMenuItemView = (IconMenuItemView) view;
        IconMenuView iconMenuView = (IconMenuView) this.mMenuView;
        iconMenuItemView.setIconMenuView(iconMenuView);
        iconMenuItemView.setItemInvoker(iconMenuView);
        iconMenuItemView.setBackgroundDrawable(iconMenuView.getItemBackgroundDrawable());
        super.addItemView(view, i);
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        MenuDialogHelper menuDialogHelper = new MenuDialogHelper(subMenuBuilder);
        menuDialogHelper.setPresenterCallback(this.mSubMenuPresenterCallback);
        menuDialogHelper.show(null);
        this.mOpenSubMenu = menuDialogHelper;
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId();
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        IconMenuItemView iconMenuItemView;
        IconMenuItemView iconMenuItemView2;
        IconMenuView iconMenuView = (IconMenuView) this.mMenuView;
        if (this.mMaxItems < 0) {
            this.mMaxItems = iconMenuView.getMaxItems();
        }
        ArrayList<MenuItemImpl> nonActionItems = this.mMenu.getNonActionItems();
        boolean z2 = nonActionItems.size() > this.mMaxItems;
        super.updateMenuView(z);
        if (z2 && ((iconMenuItemView2 = this.mMoreView) == null || iconMenuItemView2.getParent() != iconMenuView)) {
            if (this.mMoreView == null) {
                IconMenuItemView createMoreItemView = iconMenuView.createMoreItemView();
                this.mMoreView = createMoreItemView;
                createMoreItemView.setBackgroundDrawable(iconMenuView.getItemBackgroundDrawable());
            }
            iconMenuView.addView(this.mMoreView);
        } else if (!z2 && (iconMenuItemView = this.mMoreView) != null) {
            iconMenuView.removeView(iconMenuItemView);
        }
        iconMenuView.setNumActualItemsShown(z2 ? this.mMaxItems - 1 : nonActionItems.size());
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    protected boolean filterLeftoverView(ViewGroup viewGroup, int i) {
        if (viewGroup.getChildAt(i) != this.mMoreView) {
            return super.filterLeftoverView(viewGroup, i);
        }
        return false;
    }

    public int getNumActualItemsShown() {
        return ((IconMenuView) this.mMenuView).getNumActualItemsShown();
    }

    public void saveHierarchyState(Bundle bundle) {
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        if (this.mMenuView != null) {
            ((View) this.mMenuView).saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray(VIEWS_TAG, sparseArray);
    }

    public void restoreHierarchyState(Bundle bundle) {
        MenuItem findItem;
        SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(VIEWS_TAG);
        if (sparseParcelableArray != null) {
            ((View) this.mMenuView).restoreHierarchyState(sparseParcelableArray);
        }
        int i = bundle.getInt(OPEN_SUBMENU_KEY, 0);
        if (i <= 0 || this.mMenu == null || (findItem = this.mMenu.findItem(i)) == null) {
            return;
        }
        onSubMenuSelected((SubMenuBuilder) findItem.getSubMenu());
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        if (this.mMenuView == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        saveHierarchyState(bundle);
        int i = this.mOpenSubMenuId;
        if (i > 0) {
            bundle.putInt(OPEN_SUBMENU_KEY, i);
        }
        return bundle;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
        restoreHierarchyState((Bundle) parcelable);
    }

    class SubMenuPresenterCallback implements MenuPresenter.Callback {
        SubMenuPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            IconMenuPresenter.this.mOpenSubMenuId = 0;
            if (IconMenuPresenter.this.mOpenSubMenu != null) {
                IconMenuPresenter.this.mOpenSubMenu.dismiss();
                IconMenuPresenter.this.mOpenSubMenu = null;
            }
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            IconMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder) menuBuilder).getItem().getItemId();
            return false;
        }
    }
}
