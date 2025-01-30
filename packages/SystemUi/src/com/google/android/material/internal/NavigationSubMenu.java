package com.google.android.material.internal;

import android.content.Context;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.SubMenuBuilder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NavigationSubMenu extends SubMenuBuilder {
    public NavigationSubMenu(Context context, NavigationMenu navigationMenu, MenuItemImpl menuItemImpl) {
        super(context, navigationMenu, menuItemImpl);
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder
    public final void onItemsChanged(boolean z) {
        super.onItemsChanged(z);
        this.mParentMenu.onItemsChanged(z);
    }
}
