package androidx.appcompat.view.menu;

/* loaded from: classes.dex */
public interface MenuView {

    public interface ItemView {
        MenuItemImpl getItemData();

        void initialize(MenuItemImpl menuItemImpl);

        boolean prefersCondensedTitle();
    }

    void initialize(MenuBuilder menuBuilder);
}
