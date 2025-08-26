package com.android.internal.widget.floatingtoolbar;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;
import com.android.internal.util.Preconditions;
import com.samsung.android.rune.ViewRune;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class FloatingToolbar {
    public static final String FLOATING_TOOLBAR_TAG = "floating_toolbar";
    private static final MenuItem.OnMenuItemClickListener NO_OP_MENUITEM_CLICK_LISTENER = new MenuItem.OnMenuItemClickListener() { // from class: com.android.internal.widget.floatingtoolbar.FloatingToolbar$$ExternalSyntheticLambda1
        @Override // android.view.MenuItem.OnMenuItemClickListener
        public final boolean onMenuItemClick(MenuItem menuItem) {
            return FloatingToolbar.lambda$static$0(menuItem);
        }
    };
    private Menu mMenu;
    private int mOrientation;
    private final FloatingToolbarPopup mPopup;
    private final Window mWindow;
    private final Rect mContentRect = new Rect();
    private MenuItem.OnMenuItemClickListener mMenuItemClickListener = NO_OP_MENUITEM_CLICK_LISTENER;
    private final View.OnLayoutChangeListener mOrientationChangeHandler = new View.OnLayoutChangeListener() { // from class: com.android.internal.widget.floatingtoolbar.FloatingToolbar.1
        private final Rect mNewRect = new Rect();
        private final Rect mOldRect = new Rect();

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            int i9 = view.getContext().getResources().getConfiguration().orientation;
            if (FloatingToolbar.this.mOrientation != i9) {
                FloatingToolbar.this.mPopup.setIsMovingStarted(false);
            }
            FloatingToolbar.this.mOrientation = i9;
            this.mNewRect.set(i, i2, i3, i4);
            this.mOldRect.set(i5, i6, i7, i8);
            if (FloatingToolbar.this.mPopup.isDismissed() || this.mNewRect.equals(this.mOldRect)) {
                return;
            }
            FloatingToolbar.this.mPopup.setWidthChanged(true);
            FloatingToolbar.this.updateLayout();
        }
    };
    private final Comparator<MenuItem> mMenuItemComparator = new Comparator() { // from class: com.android.internal.widget.floatingtoolbar.FloatingToolbar$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return FloatingToolbar.lambda$new$1((MenuItem) obj, (MenuItem) obj2);
        }
    };

    static /* synthetic */ boolean lambda$static$0(MenuItem menuItem) {
        return false;
    }

    static /* synthetic */ int lambda$new$1(MenuItem menuItem, MenuItem menuItem2) {
        if (menuItem.getItemId() == 16908353) {
            return menuItem2.getItemId() == 16908353 ? 0 : -1;
        }
        if (menuItem2.getItemId() == 16908353) {
            return 1;
        }
        if (menuItem.requiresActionButton()) {
            return menuItem2.requiresActionButton() ? 0 : -1;
        }
        if (menuItem2.requiresActionButton()) {
            return 1;
        }
        if (menuItem.requiresOverflow()) {
            return !menuItem2.requiresOverflow() ? 1 : 0;
        }
        if (menuItem2.requiresOverflow()) {
            return -1;
        }
        return menuItem.getOrder() - menuItem2.getOrder();
    }

    public FloatingToolbar(Window window) {
        this.mWindow = (Window) Objects.requireNonNull(window);
        this.mPopup = FloatingToolbarPopup.createInstance(window.getContext(), window.getDecorView(), false);
    }

    public FloatingToolbar(Window window, boolean z) {
        this.mWindow = (Window) Preconditions.checkNotNull(window);
        this.mPopup = FloatingToolbarPopup.createInstance(window.getContext(), window.getDecorView(), z);
    }

    public FloatingToolbar setMenu(Menu menu) {
        this.mMenu = (Menu) Objects.requireNonNull(menu);
        return this;
    }

    public FloatingToolbar setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        if (onMenuItemClickListener != null) {
            this.mMenuItemClickListener = onMenuItemClickListener;
            return this;
        }
        this.mMenuItemClickListener = NO_OP_MENUITEM_CLICK_LISTENER;
        return this;
    }

    public FloatingToolbar setContentRect(Rect rect) {
        this.mContentRect.set((Rect) Objects.requireNonNull(rect));
        return this;
    }

    public FloatingToolbar setSuggestedWidth(int i) {
        this.mPopup.setSuggestedWidth(i);
        return this;
    }

    public FloatingToolbar show() {
        registerOrientationHandler();
        doShow();
        return this;
    }

    public FloatingToolbar updateLayout() {
        if (this.mPopup.isShowing()) {
            doShow();
        }
        return this;
    }

    public void dismiss() {
        unregisterOrientationHandler();
        this.mPopup.dismiss();
    }

    public void hide() {
        this.mPopup.hide();
    }

    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public boolean isHidden() {
        return this.mPopup.isHidden();
    }

    public boolean isMovingStarted() {
        return this.mPopup.isMovingStarted();
    }

    public void setIsMovingStarted(boolean z) {
        this.mPopup.setIsMovingStarted(z);
    }

    public Point getMovedPos() {
        return this.mPopup.getMovedPos();
    }

    public boolean isDiscardTouch() {
        return this.mPopup.isDiscardTouch();
    }

    public void setOutsideTouchable(boolean z, PopupWindow.OnDismissListener onDismissListener) {
        this.mPopup.setOutsideTouchable(z, onDismissListener);
    }

    private void doShow() {
        List<MenuItem> visibleAndEnabledMenuItems = getVisibleAndEnabledMenuItems(this.mMenu);
        if (!ViewRune.SUPPORT_WRITING_TOOLKIT) {
            tidy(visibleAndEnabledMenuItems);
        }
        this.mPopup.show(visibleAndEnabledMenuItems, this.mMenuItemClickListener, this.mContentRect);
    }

    private static List<MenuItem> getVisibleAndEnabledMenuItems(Menu menu) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; menu != null && i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.isVisible() && item.isEnabled()) {
                SubMenu subMenu = item.getSubMenu();
                if (subMenu != null) {
                    arrayList.addAll(getVisibleAndEnabledMenuItems(subMenu));
                } else {
                    arrayList.add(item);
                }
            }
        }
        return arrayList;
    }

    private void registerOrientationHandler() {
        unregisterOrientationHandler();
        this.mWindow.getDecorView().addOnLayoutChangeListener(this.mOrientationChangeHandler);
    }

    private void unregisterOrientationHandler() {
        this.mWindow.getDecorView().removeOnLayoutChangeListener(this.mOrientationChangeHandler);
    }

    private void tidy(List<MenuItem> list) {
        int size = list.size();
        int i = -1;
        Drawable icon = null;
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem menuItem = list.get(i2);
            if (menuItem.getItemId() == 16908353) {
                icon = menuItem.getIcon();
                i = i2;
            }
            if (!TextUtils.isEmpty(menuItem.getTitle())) {
                menuItem.setIcon((Drawable) null);
            }
        }
        if (i > -1) {
            MenuItem menuItemRemove = list.remove(i);
            menuItemRemove.setIcon(icon);
            list.add(0, menuItemRemove);
        }
    }
}
