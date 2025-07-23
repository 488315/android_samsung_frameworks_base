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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ToolbarActionBar extends ActionBar {
    public final ToolbarWidgetWrapper mDecorToolbar;
    public boolean mLastMenuVisibility;
    public final ToolbarMenuCallback mMenuCallback;
    public boolean mMenuCallbackSet;
    public boolean mToolbarMenuPrepared;
    public final Window.Callback mWindowCallback;
    public final ArrayList mMenuVisibilityListeners = new ArrayList();
    public final AnonymousClass1 mMenuInvalidator = new Runnable() { // from class: androidx.appcompat.app.ToolbarActionBar.1
        /* JADX WARN: Removed duplicated region for block: B:13:0x002d  */
        /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                r5 = this;
                androidx.appcompat.app.ToolbarActionBar r5 = androidx.appcompat.app.ToolbarActionBar.this
                androidx.appcompat.view.menu.MenuBuilder r0 = r5.getMenu()
                r1 = 0
                if (r0 == 0) goto Lb
                r2 = r0
                goto Lc
            Lb:
                r2 = r1
            Lc:
                if (r2 == 0) goto L11
                r2.stopDispatchingItemsChanged()
            L11:
                r0.clear()     // Catch: java.lang.Throwable -> L26
                android.view.Window$Callback r3 = r5.mWindowCallback     // Catch: java.lang.Throwable -> L26
                r4 = 0
                boolean r3 = r3.onCreatePanelMenu(r4, r0)     // Catch: java.lang.Throwable -> L26
                if (r3 == 0) goto L28
                android.view.Window$Callback r5 = r5.mWindowCallback     // Catch: java.lang.Throwable -> L26
                boolean r5 = r5.onPreparePanel(r4, r1, r0)     // Catch: java.lang.Throwable -> L26
                if (r5 != 0) goto L2b
                goto L28
            L26:
                r5 = move-exception
                goto L31
            L28:
                r0.clear()     // Catch: java.lang.Throwable -> L26
            L2b:
                if (r2 == 0) goto L30
                r2.startDispatchingItemsChanged()
            L30:
                return
            L31:
                if (r2 == 0) goto L36
                r2.startDispatchingItemsChanged()
            L36:
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.ToolbarActionBar.AnonymousClass1.run():void");
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.appcompat.app.ToolbarActionBar$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MenuBuilderCallback implements MenuBuilder.Callback {
        public MenuBuilderCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public final void onMenuModeChange(MenuBuilder menuBuilder) {
            ToolbarActionBar toolbarActionBar = ToolbarActionBar.this;
            if (toolbarActionBar.mDecorToolbar.mToolbar.isOverflowMenuShowing()) {
                toolbarActionBar.mWindowCallback.onPanelClosed(108, menuBuilder);
            } else if (toolbarActionBar.mWindowCallback.onPreparePanel(0, null, menuBuilder)) {
                toolbarActionBar.mWindowCallback.onMenuOpened(108, menuBuilder);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ToolbarMenuCallback {
        public ToolbarMenuCallback() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.appcompat.app.ToolbarActionBar$1] */
    public ToolbarActionBar(Toolbar toolbar, CharSequence charSequence, Window.Callback callback) {
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        toolbar.getClass();
        ToolbarWidgetWrapper toolbarWidgetWrapper = new ToolbarWidgetWrapper(toolbar, false);
        this.mDecorToolbar = toolbarWidgetWrapper;
        callback.getClass();
        this.mWindowCallback = callback;
        toolbarWidgetWrapper.mWindowCallback = callback;
        toolbar.mOnMenuItemClickListener = anonymousClass2;
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
        ActionMenuPresenter actionMenuPresenter;
        ActionMenuView actionMenuView = this.mDecorToolbar.mToolbar.mMenuView;
        return (actionMenuView == null || (actionMenuPresenter = actionMenuView.mPresenter) == null || !actionMenuPresenter.hideOverflowMenu()) ? false : true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public final boolean collapseActionView() {
        MenuItemImpl menuItemImpl;
        Toolbar.ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mDecorToolbar.mToolbar.mExpandedMenuPresenter;
        if (expandedActionViewMenuPresenter == null || (menuItemImpl = expandedActionViewMenuPresenter.mCurrentExpandedItem) == null) {
            return false;
        }
        if (expandedActionViewMenuPresenter == null) {
            menuItemImpl = null;
        }
        if (menuItemImpl == null) {
            return true;
        }
        menuItemImpl.collapseActionView();
        return true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void dispatchMenuVisibilityChanged(boolean z) {
        if (z == this.mLastMenuVisibility) {
            return;
        }
        this.mLastMenuVisibility = z;
        if (this.mMenuVisibilityListeners.size() <= 0) {
            return;
        }
        this.mMenuVisibilityListeners.get(0).getClass();
        throw new ClassCastException();
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
        AnonymousClass1 anonymousClass1 = this.mMenuInvalidator;
        toolbar.removeCallbacks(anonymousClass1);
        Toolbar toolbar2 = toolbarWidgetWrapper.mToolbar;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        toolbar2.postOnAnimation(anonymousClass1);
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
        menu.setQwertyMode(KeyCharacterMap.load(keyEvent.getDeviceId()).getKeyboardType() != 1);
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
        ActionMenuPresenter actionMenuPresenter;
        ToolbarWidgetWrapper toolbarWidgetWrapper = this.mDecorToolbar;
        ActionMenuView actionMenuView = toolbarWidgetWrapper.mToolbar.mMenuView;
        if (actionMenuView == null || (actionMenuPresenter = actionMenuView.mPresenter) == null || (actionMenuPresenter.mPostedOpenRunnable == null && !actionMenuPresenter.isOverflowMenuShowing())) {
            return toolbarWidgetWrapper.mToolbar.showOverflowMenu();
        }
        return true;
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
        toolbarWidgetWrapper.setDisplayOptions((i & 8) | (toolbarWidgetWrapper.mDisplayOpts & (-9)));
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
    public final void onConfigurationChanged() {
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void setHomeButtonEnabled() {
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void setDefaultDisplayHomeAsUpEnabled(boolean z) {
    }

    @Override // androidx.appcompat.app.ActionBar
    public final void setShowHideAnimationEnabled(boolean z) {
    }
}
