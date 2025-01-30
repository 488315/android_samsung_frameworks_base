package androidx.core.view;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.mediarouter.app.MediaRouteActionProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ActionProvider {
    public final Context mContext;
    public MenuItemImpl.C00441 mVisibilityListener;

    public ActionProvider(Context context) {
        this.mContext = context;
    }

    public boolean hasSubMenu() {
        return false;
    }

    public boolean isVisible() {
        return true;
    }

    public abstract View onCreateActionView();

    public View onCreateActionView(MenuItem menuItem) {
        return onCreateActionView();
    }

    public boolean onPerformDefaultAction() {
        return false;
    }

    public boolean overridesItemVisibility() {
        return this instanceof MediaRouteActionProvider;
    }

    public void setVisibilityListener(MenuItemImpl.C00441 c00441) {
        if (this.mVisibilityListener != null) {
            Log.w("ActionProvider(support)", "setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this " + getClass().getSimpleName() + " instance while it is still in use somewhere else?");
        }
        this.mVisibilityListener = c00441;
    }

    public void onPrepareSubMenu(SubMenuBuilder subMenuBuilder) {
    }
}
