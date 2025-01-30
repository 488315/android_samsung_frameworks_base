package com.google.android.material.navigation;

import android.content.Context;
import android.view.SubMenu;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NavigationBarMenu extends MenuBuilder {
    public final Class viewClass;

    public NavigationBarMenu(Context context, Class<?> cls, int i) {
        super(context);
        this.viewClass = cls;
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder
    public final MenuItemImpl addInternal(int i, int i2, int i3, CharSequence charSequence) {
        stopDispatchingItemsChanged();
        MenuItemImpl addInternal = super.addInternal(i, i2, i3, charSequence);
        addInternal.setExclusiveCheckable(true);
        startDispatchingItemsChanged();
        return addInternal;
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder, android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        throw new UnsupportedOperationException(this.viewClass.getSimpleName().concat(" does not support submenus"));
    }
}
