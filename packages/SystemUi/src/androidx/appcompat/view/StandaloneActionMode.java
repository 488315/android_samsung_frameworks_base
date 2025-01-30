package androidx.appcompat.view;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.core.view.ViewCompat;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StandaloneActionMode extends ActionMode implements MenuBuilder.Callback {
    public final ActionMode.Callback mCallback;
    public final Context mContext;
    public final ActionBarContextView mContextView;
    public WeakReference mCustomView;
    public boolean mFinished;
    public final MenuBuilder mMenu;

    public StandaloneActionMode(Context context, ActionBarContextView actionBarContextView, ActionMode.Callback callback, boolean z) {
        this.mContext = context;
        this.mContextView = actionBarContextView;
        this.mCallback = callback;
        MenuBuilder menuBuilder = new MenuBuilder(actionBarContextView.getContext());
        menuBuilder.mDefaultShowAsAction = 1;
        this.mMenu = menuBuilder;
        menuBuilder.setCallback(this);
    }

    @Override // androidx.appcompat.view.ActionMode
    public final void finish() {
        if (this.mFinished) {
            return;
        }
        this.mFinished = true;
        this.mCallback.onDestroyActionMode(this);
    }

    @Override // androidx.appcompat.view.ActionMode
    public final View getCustomView() {
        WeakReference weakReference = this.mCustomView;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    @Override // androidx.appcompat.view.ActionMode
    public final MenuBuilder getMenu() {
        return this.mMenu;
    }

    @Override // androidx.appcompat.view.ActionMode
    public final MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.mContextView.getContext());
    }

    @Override // androidx.appcompat.view.ActionMode
    public final CharSequence getSubtitle() {
        return this.mContextView.mSubtitle;
    }

    @Override // androidx.appcompat.view.ActionMode
    public final CharSequence getTitle() {
        return this.mContextView.mTitle;
    }

    @Override // androidx.appcompat.view.ActionMode
    public final void invalidate() {
        this.mCallback.onPrepareActionMode(this, this.mMenu);
    }

    @Override // androidx.appcompat.view.ActionMode
    public final boolean isTitleOptional() {
        return this.mContextView.mTitleOptional;
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return this.mCallback.onActionItemClicked(this, menuItem);
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public final void onMenuModeChange(MenuBuilder menuBuilder) {
        invalidate();
        ActionMenuPresenter actionMenuPresenter = this.mContextView.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.showOverflowMenu();
        }
    }

    @Override // androidx.appcompat.view.ActionMode
    public final void setCustomView(View view) {
        this.mContextView.setCustomView(view);
        this.mCustomView = view != null ? new WeakReference(view) : null;
    }

    @Override // androidx.appcompat.view.ActionMode
    public final void setSubtitle(int i) {
        setSubtitle(this.mContext.getString(i));
    }

    @Override // androidx.appcompat.view.ActionMode
    public final void setTitle(int i) {
        setTitle(this.mContext.getString(i));
    }

    @Override // androidx.appcompat.view.ActionMode
    public final void setTitleOptionalHint(boolean z) {
        this.mTitleOptionalHint = z;
        ActionBarContextView actionBarContextView = this.mContextView;
        if (z != actionBarContextView.mTitleOptional) {
            actionBarContextView.requestLayout();
        }
        actionBarContextView.mTitleOptional = z;
    }

    @Override // androidx.appcompat.view.ActionMode
    public final void setSubtitle(CharSequence charSequence) {
        ActionBarContextView actionBarContextView = this.mContextView;
        actionBarContextView.mSubtitle = charSequence;
        actionBarContextView.initTitle();
    }

    @Override // androidx.appcompat.view.ActionMode
    public final void setTitle(CharSequence charSequence) {
        ActionBarContextView actionBarContextView = this.mContextView;
        actionBarContextView.mTitle = charSequence;
        actionBarContextView.initTitle();
        ViewCompat.setAccessibilityPaneTitle(actionBarContextView, charSequence);
    }
}
