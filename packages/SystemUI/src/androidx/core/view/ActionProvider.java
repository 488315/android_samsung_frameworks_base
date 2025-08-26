package androidx.core.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.SubMenuBuilder;

/* loaded from: classes.dex */
public abstract class ActionProvider {
    public MenuItemImpl.AnonymousClass1 mVisibilityListener;

    public ActionProvider(Context context) {
    }

    public boolean hasSubMenu() {
        return false;
    }

    public boolean isVisible() {
        return true;
    }

    public abstract View onCreateActionView();

    public View onCreateActionView(MenuItemImpl menuItemImpl) {
        return onCreateActionView();
    }

    public boolean onPerformDefaultAction() {
        return false;
    }

    public boolean overridesItemVisibility() {
        return false;
    }

    public void setVisibilityListener(MenuItemImpl.AnonymousClass1 anonymousClass1) {
        if (this.mVisibilityListener != null) {
            Log.w("ActionProvider(support)", "setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this " + getClass().getSimpleName() + " instance while it is still in use somewhere else?");
        }
        this.mVisibilityListener = anonymousClass1;
    }

    public void onPrepareSubMenu(SubMenuBuilder subMenuBuilder) {
    }
}
