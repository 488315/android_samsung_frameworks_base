package androidx.appcompat.view.menu;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface MenuPresenter {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void onCloseMenu(MenuBuilder menuBuilder, boolean z);

        boolean onOpenSubMenu(MenuBuilder menuBuilder);
    }

    boolean collapseItemActionView(MenuItemImpl menuItemImpl);

    boolean expandItemActionView(MenuItemImpl menuItemImpl);

    boolean flagActionItems();

    void initForMenu(Context context, MenuBuilder menuBuilder);

    void onCloseMenu(MenuBuilder menuBuilder, boolean z);

    boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder);

    void updateMenuView(boolean z);
}
