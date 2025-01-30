package androidx.appcompat.app;

import android.content.Context;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ToolbarActionBar extends ActionBar {
    public final ToolbarWidgetWrapper mDecorToolbar;
    public boolean mLastMenuVisibility;
    public final ToolbarMenuCallback mMenuCallback;
    public boolean mMenuCallbackSet;
    public boolean mToolbarMenuPrepared;
    public final Window.Callback mWindowCallback;
    public final ArrayList mMenuVisibilityListeners = new ArrayList();
    public final RunnableC00351 mMenuInvalidator = new Runnable() { // from class: androidx.appcompat.app.ToolbarActionBar.1
        @Override // java.lang.Runnable
        public final void run() {
            ToolbarActionBar toolbarActionBar = ToolbarActionBar.this;
            Window.Callback callback = toolbarActionBar.mWindowCallback;
            MenuBuilder menu = toolbarActionBar.getMenu();
            MenuBuilder menuBuilder = menu instanceof MenuBuilder ? menu : null;
            if (menuBuilder != null) {
                menuBuilder.stopDispatchingItemsChanged();
            }
            try {
                menu.clear();
                if (!callback.onCreatePanelMenu(0, menu) || !callback.onPreparePanel(0, null, menu)) {
                    menu.clear();
                }
            } finally {
                if (menuBuilder != null) {
                    menuBuilder.startDispatchingItemsChanged();
                }
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.appcompat.app.ToolbarActionBar$2 */
    public final class C00362 {
        public C00362() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        public boolean mClosingActionMenu;

        public ActionMenuPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            ActionMenuPresenter actionMenuPresenter;
            if (this.mClosingActionMenu) {
                return;
            }
            this.mClosingActionMenu = true;
            ToolbarActionBar toolbarActionBar = ToolbarActionBar.this;
            ActionMenuView actionMenuView = toolbarActionBar.mDecorToolbar.mToolbar.mMenuView;
            if (actionMenuView != null && (actionMenuPresenter = actionMenuView.mPresenter) != null) {
                actionMenuPresenter.hideOverflowMenu();
                ActionMenuPresenter.ActionButtonSubmenu actionButtonSubmenu = actionMenuPresenter.mActionButtonPopup;
                if (actionButtonSubmenu != null && actionButtonSubmenu.isShowing()) {
                    actionButtonSubmenu.mPopup.dismiss();
                }
            }
            toolbarActionBar.mWindowCallback.onPanelClosed(108, menuBuilder);
            this.mClosingActionMenu = false;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MenuBuilderCallback implements MenuBuilder.Callback {
        public MenuBuilderCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return false;
        }

        /* JADX WARN: Code restructure failed: missing block: B:8:0x0019, code lost:
        
            if ((r0 != null && r0.isOverflowMenuShowing()) != false) goto L13;
         */
        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onMenuModeChange(MenuBuilder menuBuilder) {
            boolean z;
            ToolbarActionBar toolbarActionBar = ToolbarActionBar.this;
            ActionMenuView actionMenuView = toolbarActionBar.mDecorToolbar.mToolbar.mMenuView;
            if (actionMenuView != null) {
                ActionMenuPresenter actionMenuPresenter = actionMenuView.mPresenter;
                z = true;
            }
            z = false;
            Window.Callback callback = toolbarActionBar.mWindowCallback;
            if (z) {
                callback.onPanelClosed(108, menuBuilder);
            } else if (callback.onPreparePanel(0, null, menuBuilder)) {
                callback.onMenuOpened(108, menuBuilder);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ToolbarMenuCallback {
        public ToolbarMenuCallback() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.appcompat.app.ToolbarActionBar$1] */
    public ToolbarActionBar(Toolbar toolbar, CharSequence charSequence, Window.Callback callback) {
        C00362 c00362 = new C00362();
        toolbar.getClass();
        ToolbarWidgetWrapper toolbarWidgetWrapper = new ToolbarWidgetWrapper(toolbar, false);
        this.mDecorToolbar = toolbarWidgetWrapper;
        callback.getClass();
        this.mWindowCallback = callback;
        toolbarWidgetWrapper.mWindowCallback = callback;
        toolbar.mOnMenuItemClickListener = c00362;
        if (!toolbarWidgetWrapper.mTitleSet) {
            toolbarWidgetWrapper.mTitle = charSequence;
            if ((toolbarWidgetWrapper.mDisplayOpts & 8) != 0) {
                Toolbar toolbar2 = toolbarWidgetWrapper.mToolbar;
                toolbar2.setTitle(charSequence);
                if (toolbarWidgetWrapper.mTitleSet) {
                    ViewCompat.setAccessibilityPaneTitle(toolbar2.getRootView(), charSequence);
                }
            }
        }
        this.mMenuCallback = new ToolbarMenuCallback();
    }

    @Override // androidx.appcompat.app.ActionBar
    public final boolean closeOptionsMenu() {
        ActionMenuView actionMenuView = this.mDecorToolbar.mToolbar.mMenuView;
        if (actionMenuView == null) {
            return false;
        }
        ActionMenuPresenter actionMenuPresenter = actionMenuView.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.hideOverflowMenu();
    }

    @Override // androidx.appcompat.app.ActionBar
    public final boolean collapseActionView() {
        Toolbar.ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mDecorToolbar.mToolbar.mExpandedMenuPresenter;
        if (!((expandedActionViewMenuPresenter == null || expandedActionViewMenuPresenter.mCurrentExpandedItem == null) ? false : true)) {
            return false;
        }
        MenuItemImpl menuItemImpl = expandedActionViewMenuPresenter == null ? null : expandedActionViewMenuPresenter.mCurrentExpandedItem;
        if (menuItemImpl != null) {
            menuItemImpl.collapseActionView();
        }
        return true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void dispatchMenuVisibilityChanged(boolean z) {
        if (z == this.mLastMenuVisibility) {
            return;
        }
        this.mLastMenuVisibility = z;
        ArrayList arrayList = this.mMenuVisibilityListeners;
        if (arrayList.size() <= 0) {
            return;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(arrayList.get(0));
        throw null;
    }

    @Override // androidx.appcompat.app.ActionBar
    public final int getDisplayOptions() {
        return this.mDecorToolbar.mDisplayOpts;
    }

    public final MenuBuilder getMenu() {
        boolean z = this.mMenuCallbackSet;
        ToolbarWidgetWrapper toolbarWidgetWrapper = this.mDecorToolbar;
        if (!z) {
            ActionMenuPresenterCallback actionMenuPresenterCallback = new ActionMenuPresenterCallback();
            MenuBuilderCallback menuBuilderCallback = new MenuBuilderCallback();
            Toolbar toolbar = toolbarWidgetWrapper.mToolbar;
            toolbar.mActionMenuPresenterCallback = actionMenuPresenterCallback;
            toolbar.mMenuBuilderCallback = menuBuilderCallback;
            ActionMenuView actionMenuView = toolbar.mMenuView;
            if (actionMenuView != null) {
                actionMenuView.mActionMenuPresenterCallback = actionMenuPresenterCallback;
                actionMenuView.mMenuBuilderCallback = menuBuilderCallback;
            }
            this.mMenuCallbackSet = true;
        }
        return toolbarWidgetWrapper.mToolbar.getMenu();
    }

    @Override // androidx.appcompat.app.ActionBar
    public final Context getThemedContext() {
        return this.mDecorToolbar.mToolbar.getContext();
    }

    @Override // androidx.appcompat.app.ActionBar
    public final boolean invalidateOptionsMenu() {
        ToolbarWidgetWrapper toolbarWidgetWrapper = this.mDecorToolbar;
        Toolbar toolbar = toolbarWidgetWrapper.mToolbar;
        RunnableC00351 runnableC00351 = this.mMenuInvalidator;
        toolbar.removeCallbacks(runnableC00351);
        Toolbar toolbar2 = toolbarWidgetWrapper.mToolbar;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.postOnAnimation(toolbar2, runnableC00351);
        return true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void onDestroy() {
        this.mDecorToolbar.mToolbar.removeCallbacks(this.mMenuInvalidator);
    }

    @Override // androidx.appcompat.app.ActionBar
    public final boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        MenuBuilder menu = getMenu();
        if (menu == null) {
            return false;
        }
        menu.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
        return menu.performShortcut(i, keyEvent, 0);
    }

    @Override // androidx.appcompat.app.ActionBar
    public final boolean onMenuKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            openOptionsMenu();
        }
        return true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public final boolean openOptionsMenu() {
        ToolbarWidgetWrapper toolbarWidgetWrapper = this.mDecorToolbar;
        if (toolbarWidgetWrapper.isOverflowMenuShowPending()) {
            return true;
        }
        ActionMenuView actionMenuView = toolbarWidgetWrapper.mToolbar.mMenuView;
        if (actionMenuView != null) {
            ActionMenuPresenter actionMenuPresenter = actionMenuView.mPresenter;
            if (actionMenuPresenter != null && actionMenuPresenter.showOverflowMenu()) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void setDisplayHomeAsUpEnabled(boolean z) {
        ToolbarWidgetWrapper toolbarWidgetWrapper = this.mDecorToolbar;
        toolbarWidgetWrapper.setDisplayOptions((toolbarWidgetWrapper.mDisplayOpts & (-5)) | 4);
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void setDisplayShowTitleEnabled(boolean z) {
        int i = z ? 8 : 0;
        ToolbarWidgetWrapper toolbarWidgetWrapper = this.mDecorToolbar;
        toolbarWidgetWrapper.setDisplayOptions((i & 8) | ((-9) & toolbarWidgetWrapper.mDisplayOpts));
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void setTitle() {
        ToolbarWidgetWrapper toolbarWidgetWrapper = this.mDecorToolbar;
        CharSequence text = toolbarWidgetWrapper.mToolbar.getContext().getText(R.string.avatar_picker_title);
        toolbarWidgetWrapper.mTitleSet = true;
        toolbarWidgetWrapper.mTitle = text;
        if ((toolbarWidgetWrapper.mDisplayOpts & 8) != 0) {
            Toolbar toolbar = toolbarWidgetWrapper.mToolbar;
            toolbar.setTitle(text);
            if (toolbarWidgetWrapper.mTitleSet) {
                ViewCompat.setAccessibilityPaneTitle(toolbar.getRootView(), text);
            }
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void setWindowTitle(CharSequence charSequence) {
        ToolbarWidgetWrapper toolbarWidgetWrapper = this.mDecorToolbar;
        if (toolbarWidgetWrapper.mTitleSet) {
            return;
        }
        toolbarWidgetWrapper.mTitle = charSequence;
        if ((toolbarWidgetWrapper.mDisplayOpts & 8) != 0) {
            Toolbar toolbar = toolbarWidgetWrapper.mToolbar;
            toolbar.setTitle(charSequence);
            if (toolbarWidgetWrapper.mTitleSet) {
                ViewCompat.setAccessibilityPaneTitle(toolbar.getRootView(), charSequence);
            }
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void setTitle(CharSequence charSequence) {
        ToolbarWidgetWrapper toolbarWidgetWrapper = this.mDecorToolbar;
        toolbarWidgetWrapper.mTitleSet = true;
        toolbarWidgetWrapper.mTitle = charSequence;
        if ((toolbarWidgetWrapper.mDisplayOpts & 8) != 0) {
            Toolbar toolbar = toolbarWidgetWrapper.mToolbar;
            toolbar.setTitle(charSequence);
            if (toolbarWidgetWrapper.mTitleSet) {
                ViewCompat.setAccessibilityPaneTitle(toolbar.getRootView(), charSequence);
            }
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void setDefaultDisplayHomeAsUpEnabled(boolean z) {
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void setShowHideAnimationEnabled(boolean z) {
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void onConfigurationChanged() {
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void setHomeButtonEnabled() {
    }
}
