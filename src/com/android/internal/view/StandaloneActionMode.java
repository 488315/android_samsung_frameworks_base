package com.android.internal.view;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuPopupHelper;
import com.android.internal.view.menu.SubMenuBuilder;
import com.android.internal.widget.ActionBarContextView;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class StandaloneActionMode extends ActionMode implements MenuBuilder.Callback {
    private ActionMode.Callback mCallback;
    private Context mContext;
    private ActionBarContextView mContextView;
    private WeakReference<View> mCustomView;
    private boolean mFinished;
    private boolean mFocusable;
    private MenuBuilder mMenu;

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
    }

    public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
    }

    public StandaloneActionMode(Context context, ActionBarContextView actionBarContextView, ActionMode.Callback callback, boolean z) {
        this.mContext = context;
        this.mContextView = actionBarContextView;
        this.mCallback = callback;
        MenuBuilder defaultShowAsAction = new MenuBuilder(actionBarContextView.getContext()).setDefaultShowAsAction(1);
        this.mMenu = defaultShowAsAction;
        defaultShowAsAction.setCallback(this);
        this.mFocusable = z;
    }

    @Override // android.view.ActionMode
    public void setTitle(CharSequence charSequence) {
        this.mContextView.setTitle(charSequence);
    }

    @Override // android.view.ActionMode
    public void setSubtitle(CharSequence charSequence) {
        this.mContextView.setSubtitle(charSequence);
    }

    @Override // android.view.ActionMode
    public void setTitle(int i) {
        setTitle(i != 0 ? this.mContext.getString(i) : null);
    }

    @Override // android.view.ActionMode
    public void setSubtitle(int i) {
        setSubtitle(i != 0 ? this.mContext.getString(i) : null);
    }

    @Override // android.view.ActionMode
    public void setTitleOptionalHint(boolean z) {
        super.setTitleOptionalHint(z);
        this.mContextView.setTitleOptional(z);
    }

    @Override // android.view.ActionMode
    public boolean isTitleOptional() {
        return this.mContextView.isTitleOptional();
    }

    @Override // android.view.ActionMode
    public void setCustomView(View view) {
        this.mContextView.setCustomView(view);
        this.mCustomView = view != null ? new WeakReference<>(view) : null;
    }

    @Override // android.view.ActionMode
    public void invalidate() {
        this.mCallback.onPrepareActionMode(this, this.mMenu);
    }

    @Override // android.view.ActionMode
    public void finish() {
        if (this.mFinished) {
            return;
        }
        this.mFinished = true;
        this.mContextView.sendAccessibilityEvent(32);
        this.mCallback.onDestroyActionMode(this);
    }

    @Override // android.view.ActionMode
    public Menu getMenu() {
        return this.mMenu;
    }

    @Override // android.view.ActionMode
    public CharSequence getTitle() {
        return this.mContextView.getTitle();
    }

    @Override // android.view.ActionMode
    public CharSequence getSubtitle() {
        return this.mContextView.getSubtitle();
    }

    @Override // android.view.ActionMode
    public View getCustomView() {
        WeakReference<View> weakReference = this.mCustomView;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    @Override // android.view.ActionMode
    public MenuInflater getMenuInflater() {
        return new MenuInflater(this.mContextView.getContext());
    }

    @Override // com.android.internal.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return this.mCallback.onActionItemClicked(this, menuItem);
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return true;
        }
        new MenuPopupHelper(this.mContextView.getContext(), subMenuBuilder).show();
        return true;
    }

    @Override // com.android.internal.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        invalidate();
        this.mContextView.showOverflowMenu();
    }

    @Override // android.view.ActionMode
    public boolean isUiFocusable() {
        return this.mFocusable;
    }
}
