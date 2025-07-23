package com.android.internal.app;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.ActionMode;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowCallbackWrapper;
import android.widget.SpinnerAdapter;
import android.widget.Toolbar;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.widget.DecorToolbar;
import com.android.internal.widget.ToolbarWidgetWrapper;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ToolbarActionBar extends ActionBar {
    private DecorToolbar mDecorToolbar;
    private boolean mLastMenuVisibility;
    private boolean mMenuCallbackSet;
    private final Toolbar.OnMenuItemClickListener mMenuClicker;
    private boolean mToolbarMenuPrepared;
    private Window.Callback mWindowCallback;
    private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList<>();
    private final Runnable mMenuInvalidator = new Runnable() { // from class: com.android.internal.app.ToolbarActionBar.1
        @Override // java.lang.Runnable
        public void run() {
            ToolbarActionBar.this.populateOptionsMenu();
        }
    };

    @Override // android.app.ActionBar
    public int getNavigationItemCount() {
        return 0;
    }

    @Override // android.app.ActionBar
    public int getNavigationMode() {
        return 0;
    }

    @Override // android.app.ActionBar
    public int getSelectedNavigationIndex() {
        return -1;
    }

    @Override // android.app.ActionBar
    public int getTabCount() {
        return 0;
    }

    @Override // android.app.ActionBar
    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
    }

    @Override // android.app.ActionBar
    public void setHomeButtonEnabled(boolean z) {
    }

    @Override // android.app.ActionBar
    public void setShowHideAnimationEnabled(boolean z) {
    }

    @Override // android.app.ActionBar
    public void setSplitBackgroundDrawable(Drawable drawable) {
    }

    @Override // android.app.ActionBar
    public void setStackedBackgroundDrawable(Drawable drawable) {
    }

    @Override // android.app.ActionBar
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return null;
    }

    public ToolbarActionBar(Toolbar toolbar, CharSequence charSequence, Window.Callback callback) {
        Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() { // from class: com.android.internal.app.ToolbarActionBar.2
            @Override // android.widget.Toolbar.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                return ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, menuItem);
            }
        };
        this.mMenuClicker = onMenuItemClickListener;
        this.mDecorToolbar = new ToolbarWidgetWrapper(toolbar, false);
        ToolbarCallbackWrapper toolbarCallbackWrapper = new ToolbarCallbackWrapper(callback);
        this.mWindowCallback = toolbarCallbackWrapper;
        this.mDecorToolbar.setWindowCallback(toolbarCallbackWrapper);
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    public Window.Callback getWrappedWindowCallback() {
        return this.mWindowCallback;
    }

    @Override // android.app.ActionBar
    public void setCustomView(View view) {
        setCustomView(view, new ActionBar.LayoutParams(-2, -2));
    }

    @Override // android.app.ActionBar
    public void setCustomView(View view, ActionBar.LayoutParams layoutParams) {
        if (view != null) {
            view.setLayoutParams(layoutParams);
        }
        this.mDecorToolbar.setCustomView(view);
    }

    @Override // android.app.ActionBar
    public void setCustomView(int i) {
        setCustomView(LayoutInflater.from(this.mDecorToolbar.getContext()).inflate(i, this.mDecorToolbar.getViewGroup(), false));
    }

    @Override // android.app.ActionBar
    public void setIcon(int i) {
        this.mDecorToolbar.setIcon(i);
    }

    @Override // android.app.ActionBar
    public void setIcon(Drawable drawable) {
        this.mDecorToolbar.setIcon(drawable);
    }

    @Override // android.app.ActionBar
    public void setLogo(int i) {
        this.mDecorToolbar.setLogo(i);
    }

    @Override // android.app.ActionBar
    public void setLogo(Drawable drawable) {
        this.mDecorToolbar.setLogo(drawable);
    }

    @Override // android.app.ActionBar
    public void setElevation(float f) {
        this.mDecorToolbar.getViewGroup().setElevation(f);
    }

    @Override // android.app.ActionBar
    public float getElevation() {
        return this.mDecorToolbar.getViewGroup().getElevation();
    }

    @Override // android.app.ActionBar
    public Context getThemedContext() {
        return this.mDecorToolbar.getContext();
    }

    @Override // android.app.ActionBar
    public boolean isTitleTruncated() {
        return super.isTitleTruncated();
    }

    @Override // android.app.ActionBar
    public void setHomeAsUpIndicator(Drawable drawable) {
        this.mDecorToolbar.setNavigationIcon(drawable);
    }

    @Override // android.app.ActionBar
    public void setHomeAsUpIndicator(int i) {
        this.mDecorToolbar.setNavigationIcon(i);
    }

    @Override // android.app.ActionBar
    public void setHomeActionContentDescription(CharSequence charSequence) {
        this.mDecorToolbar.setNavigationContentDescription(charSequence);
    }

    @Override // android.app.ActionBar
    public void setHomeActionContentDescription(int i) {
        this.mDecorToolbar.setNavigationContentDescription(i);
    }

    @Override // android.app.ActionBar
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.ActionBar
    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, ActionBar.OnNavigationListener onNavigationListener) {
        this.mDecorToolbar.setDropdownParams(spinnerAdapter, new NavItemSelectedListener(onNavigationListener));
    }

    @Override // android.app.ActionBar
    public void setSelectedNavigationItem(int i) {
        if (this.mDecorToolbar.getNavigationMode() == 1) {
            this.mDecorToolbar.setDropdownSelectedPosition(i);
            return;
        }
        throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
    }

    @Override // android.app.ActionBar
    public void setTitle(CharSequence charSequence) {
        this.mDecorToolbar.setTitle(charSequence);
    }

    @Override // android.app.ActionBar
    public void setTitle(int i) {
        DecorToolbar decorToolbar = this.mDecorToolbar;
        decorToolbar.setTitle(i != 0 ? decorToolbar.getContext().getText(i) : null);
    }

    @Override // android.app.ActionBar
    public void setWindowTitle(CharSequence charSequence) {
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    @Override // android.app.ActionBar
    public void setSubtitle(CharSequence charSequence) {
        this.mDecorToolbar.setSubtitle(charSequence);
    }

    @Override // android.app.ActionBar
    public void setSubtitle(int i) {
        DecorToolbar decorToolbar = this.mDecorToolbar;
        decorToolbar.setSubtitle(i != 0 ? decorToolbar.getContext().getText(i) : null);
    }

    @Override // android.app.ActionBar
    public void setDisplayOptions(int i) {
        setDisplayOptions(i, -1);
    }

    @Override // android.app.ActionBar
    public void setDisplayOptions(int i, int i2) {
        this.mDecorToolbar.setDisplayOptions((i & i2) | ((~i2) & this.mDecorToolbar.getDisplayOptions()));
    }

    @Override // android.app.ActionBar
    public void setDisplayUseLogoEnabled(boolean z) {
        setDisplayOptions(z ? 1 : 0, 1);
    }

    @Override // android.app.ActionBar
    public void setDisplayShowHomeEnabled(boolean z) {
        setDisplayOptions(z ? 2 : 0, 2);
    }

    @Override // android.app.ActionBar
    public void setDisplayHomeAsUpEnabled(boolean z) {
        setDisplayOptions(z ? 4 : 0, 4);
    }

    @Override // android.app.ActionBar
    public void setDisplayShowTitleEnabled(boolean z) {
        setDisplayOptions(z ? 8 : 0, 8);
    }

    @Override // android.app.ActionBar
    public void setDisplayShowCustomEnabled(boolean z) {
        setDisplayOptions(z ? 16 : 0, 16);
    }

    @Override // android.app.ActionBar
    public void setBackgroundDrawable(Drawable drawable) {
        this.mDecorToolbar.setBackgroundDrawable(drawable);
    }

    @Override // android.app.ActionBar
    public View getCustomView() {
        return this.mDecorToolbar.getCustomView();
    }

    @Override // android.app.ActionBar
    public CharSequence getTitle() {
        return this.mDecorToolbar.getTitle();
    }

    @Override // android.app.ActionBar
    public CharSequence getSubtitle() {
        return this.mDecorToolbar.getSubtitle();
    }

    @Override // android.app.ActionBar
    public void setNavigationMode(int i) {
        if (i == 2) {
            throw new IllegalArgumentException("Tabs not supported in this configuration");
        }
        this.mDecorToolbar.setNavigationMode(i);
    }

    @Override // android.app.ActionBar
    public int getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions();
    }

    @Override // android.app.ActionBar
    public ActionBar.Tab newTab() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void addTab(ActionBar.Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void addTab(ActionBar.Tab tab, boolean z) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i, boolean z) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void removeTab(ActionBar.Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void removeTabAt(int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void removeAllTabs() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void selectTab(ActionBar.Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public ActionBar.Tab getSelectedTab() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public ActionBar.Tab getTabAt(int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public int getHeight() {
        return this.mDecorToolbar.getHeight();
    }

    @Override // android.app.ActionBar
    public void show() {
        this.mDecorToolbar.setVisibility(0);
    }

    @Override // android.app.ActionBar
    public void hide() {
        this.mDecorToolbar.setVisibility(8);
    }

    @Override // android.app.ActionBar
    public boolean isShowing() {
        return this.mDecorToolbar.getVisibility() == 0;
    }

    @Override // android.app.ActionBar
    public boolean openOptionsMenu() {
        return this.mDecorToolbar.showOverflowMenu();
    }

    @Override // android.app.ActionBar
    public boolean closeOptionsMenu() {
        return this.mDecorToolbar.hideOverflowMenu();
    }

    @Override // android.app.ActionBar
    public boolean invalidateOptionsMenu() {
        this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
        this.mDecorToolbar.getViewGroup().postOnAnimation(this.mMenuInvalidator);
        return true;
    }

    @Override // android.app.ActionBar
    public boolean collapseActionView() {
        if (!this.mDecorToolbar.hasExpandedActionView()) {
            return false;
        }
        this.mDecorToolbar.collapseActionView();
        return true;
    }

    void populateOptionsMenu() {
        if (!this.mMenuCallbackSet) {
            this.mDecorToolbar.setMenuCallbacks(new ActionMenuPresenterCallback(), new MenuBuilderCallback());
            this.mMenuCallbackSet = true;
        }
        Menu menu = this.mDecorToolbar.getMenu();
        MenuBuilder menuBuilder = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
        if (menuBuilder != null) {
            menuBuilder.stopDispatchingItemsChanged();
        }
        try {
            menu.clear();
            if (!this.mWindowCallback.onCreatePanelMenu(0, menu) || !this.mWindowCallback.onPreparePanel(0, null, menu)) {
                menu.clear();
            }
        } finally {
            if (menuBuilder != null) {
                menuBuilder.startDispatchingItemsChanged();
            }
        }
    }

    @Override // android.app.ActionBar
    public boolean onMenuKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            openOptionsMenu();
        }
        return true;
    }

    @Override // android.app.ActionBar
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        Menu menu = this.mDecorToolbar.getMenu();
        if (menu == null) {
            return false;
        }
        menu.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
        return menu.performShortcut(i, keyEvent, 0);
    }

    @Override // android.app.ActionBar
    public void onDestroy() {
        this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
    }

    @Override // android.app.ActionBar
    public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.add(onMenuVisibilityListener);
    }

    @Override // android.app.ActionBar
    public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.remove(onMenuVisibilityListener);
    }

    @Override // android.app.ActionBar
    public void dispatchMenuVisibilityChanged(boolean z) {
        if (z == this.mLastMenuVisibility) {
            return;
        }
        this.mLastMenuVisibility = z;
        int size = this.mMenuVisibilityListeners.size();
        for (int i = 0; i < size; i++) {
            this.mMenuVisibilityListeners.get(i).onMenuVisibilityChanged(z);
        }
    }

    private class ToolbarCallbackWrapper extends WindowCallbackWrapper {
        public ToolbarCallbackWrapper(Window.Callback callback) {
            super(callback);
        }

        @Override // android.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onPreparePanel(int i, View view, Menu menu) {
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (onPreparePanel && !ToolbarActionBar.this.mToolbarMenuPrepared) {
                ToolbarActionBar.this.mDecorToolbar.setMenuPrepared();
                ToolbarActionBar.this.mToolbarMenuPrepared = true;
            }
            return onPreparePanel;
        }

        @Override // android.view.WindowCallbackWrapper, android.view.Window.Callback
        public View onCreatePanelView(int i) {
            if (i == 0) {
                return new View(ToolbarActionBar.this.mDecorToolbar.getContext());
            }
            return super.onCreatePanelView(i);
        }
    }

    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        private boolean mClosingActionMenu;

        private ActionMenuPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (ToolbarActionBar.this.mWindowCallback == null) {
                return false;
            }
            ToolbarActionBar.this.mWindowCallback.onMenuOpened(8, menuBuilder);
            return true;
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (this.mClosingActionMenu) {
                return;
            }
            this.mClosingActionMenu = true;
            ToolbarActionBar.this.mDecorToolbar.dismissPopupMenus();
            if (ToolbarActionBar.this.mWindowCallback != null) {
                ToolbarActionBar.this.mWindowCallback.onPanelClosed(8, menuBuilder);
            }
            this.mClosingActionMenu = false;
        }
    }

    private final class MenuBuilderCallback implements MenuBuilder.Callback {
        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return false;
        }

        private MenuBuilderCallback() {
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (ToolbarActionBar.this.mWindowCallback != null) {
                if (ToolbarActionBar.this.mDecorToolbar.isOverflowMenuShowing()) {
                    ToolbarActionBar.this.mWindowCallback.onPanelClosed(8, menuBuilder);
                } else if (ToolbarActionBar.this.mWindowCallback.onPreparePanel(0, null, menuBuilder)) {
                    ToolbarActionBar.this.mWindowCallback.onMenuOpened(8, menuBuilder);
                }
            }
        }
    }
}
