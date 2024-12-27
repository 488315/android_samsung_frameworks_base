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

import java.util.ArrayList;

public class IconMenuPresenter extends BaseMenuPresenter {
    private static final String OPEN_SUBMENU_KEY = "android:menu:icon:submenu";
    private static final String VIEWS_TAG = "android:menu:icon";
    private int mMaxItems;
    private IconMenuItemView mMoreView;
    MenuDialogHelper mOpenSubMenu;
    int mOpenSubMenuId;
    SubMenuPresenterCallback mSubMenuPresenterCallback;

    public IconMenuPresenter(Context context) {
        super(
                new ContextThemeWrapper(context, R.style.Theme_IconMenu),
                R.layout.icon_menu_layout,
                R.layout.icon_menu_item_layout);
        this.mMaxItems = -1;
        this.mSubMenuPresenterCallback = new SubMenuPresenterCallback();
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter,
              // com.android.internal.view.menu.MenuPresenter
    public void initForMenu(Context context, MenuBuilder menu) {
        super.initForMenu(context, menu);
        this.mMaxItems = -1;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public void bindItemView(MenuItemImpl item, MenuView.ItemView itemView) {
        IconMenuItemView view = (IconMenuItemView) itemView;
        view.setItemData(item);
        view.initialize(item.getTitleForItemView(view), item.getIcon());
        view.setVisibility(item.isVisible() ? 0 : 8);
        view.setEnabled(view.isEnabled());
        view.setLayoutParams(view.getTextAppropriateLayoutParams());
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public boolean shouldIncludeItem(int childIndex, MenuItemImpl item) {
        ArrayList<MenuItemImpl> itemsToShow = this.mMenu.getNonActionItems();
        boolean fits =
                (itemsToShow.size() == this.mMaxItems && childIndex < this.mMaxItems)
                        || childIndex < this.mMaxItems - 1;
        return fits && !item.isActionButton();
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    protected void addItemView(View itemView, int childIndex) {
        IconMenuItemView v = (IconMenuItemView) itemView;
        IconMenuView parent = (IconMenuView) this.mMenuView;
        v.setIconMenuView(parent);
        v.setItemInvoker(parent);
        v.setBackgroundDrawable(parent.getItemBackgroundDrawable());
        super.addItemView(itemView, childIndex);
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter,
              // com.android.internal.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
        if (!subMenu.hasVisibleItems()) {
            return false;
        }
        MenuDialogHelper helper = new MenuDialogHelper(subMenu);
        helper.setPresenterCallback(this.mSubMenuPresenterCallback);
        helper.show(null);
        this.mOpenSubMenu = helper;
        this.mOpenSubMenuId = subMenu.getItem().getItemId();
        super.onSubMenuSelected(subMenu);
        return true;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter,
              // com.android.internal.view.menu.MenuPresenter
    public void updateMenuView(boolean cleared) {
        IconMenuView menuView = (IconMenuView) this.mMenuView;
        if (this.mMaxItems < 0) {
            this.mMaxItems = menuView.getMaxItems();
        }
        ArrayList<MenuItemImpl> itemsToShow = this.mMenu.getNonActionItems();
        boolean needsMore = itemsToShow.size() > this.mMaxItems;
        super.updateMenuView(cleared);
        if (needsMore && (this.mMoreView == null || this.mMoreView.getParent() != menuView)) {
            if (this.mMoreView == null) {
                this.mMoreView = menuView.createMoreItemView();
                this.mMoreView.setBackgroundDrawable(menuView.getItemBackgroundDrawable());
            }
            menuView.addView(this.mMoreView);
        } else if (!needsMore && this.mMoreView != null) {
            menuView.removeView(this.mMoreView);
        }
        menuView.setNumActualItemsShown(needsMore ? this.mMaxItems - 1 : itemsToShow.size());
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    protected boolean filterLeftoverView(ViewGroup parent, int childIndex) {
        if (parent.getChildAt(childIndex) != this.mMoreView) {
            return super.filterLeftoverView(parent, childIndex);
        }
        return false;
    }

    public int getNumActualItemsShown() {
        return ((IconMenuView) this.mMenuView).getNumActualItemsShown();
    }

    public void saveHierarchyState(Bundle outState) {
        SparseArray<Parcelable> viewStates = new SparseArray<>();
        if (this.mMenuView != null) {
            ((View) this.mMenuView).saveHierarchyState(viewStates);
        }
        outState.putSparseParcelableArray(VIEWS_TAG, viewStates);
    }

    public void restoreHierarchyState(Bundle inState) {
        MenuItem item;
        SparseArray<Parcelable> viewStates = inState.getSparseParcelableArray(VIEWS_TAG);
        if (viewStates != null) {
            ((View) this.mMenuView).restoreHierarchyState(viewStates);
        }
        int subMenuId = inState.getInt(OPEN_SUBMENU_KEY, 0);
        if (subMenuId > 0
                && this.mMenu != null
                && (item = this.mMenu.findItem(subMenuId)) != null) {
            onSubMenuSelected((SubMenuBuilder) item.getSubMenu());
        }
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        if (this.mMenuView == null) {
            return null;
        }
        Bundle state = new Bundle();
        saveHierarchyState(state);
        if (this.mOpenSubMenuId > 0) {
            state.putInt(OPEN_SUBMENU_KEY, this.mOpenSubMenuId);
        }
        return state;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable state) {
        restoreHierarchyState((Bundle) state);
    }

    class SubMenuPresenterCallback implements MenuPresenter.Callback {
        SubMenuPresenterCallback() {}

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            IconMenuPresenter.this.mOpenSubMenuId = 0;
            if (IconMenuPresenter.this.mOpenSubMenu != null) {
                IconMenuPresenter.this.mOpenSubMenu.dismiss();
                IconMenuPresenter.this.mOpenSubMenu = null;
            }
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            if (subMenu != null) {
                IconMenuPresenter.this.mOpenSubMenuId =
                        ((SubMenuBuilder) subMenu).getItem().getItemId();
                return false;
            }
            return false;
        }
    }
}
