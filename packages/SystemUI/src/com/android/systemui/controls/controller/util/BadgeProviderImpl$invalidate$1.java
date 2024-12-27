package com.android.systemui.controls.controller.util;

import androidx.appcompat.view.menu.MenuItemImpl;
import com.android.systemui.R;
import java.util.Iterator;

public final class BadgeProviderImpl$invalidate$1 implements Runnable {
    public final /* synthetic */ BadgeProviderImpl this$0;

    public BadgeProviderImpl$invalidate$1(BadgeProviderImpl badgeProviderImpl) {
        this.this$0 = badgeProviderImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String string = this.this$0.badgeRequiredSet.isEmpty() ^ true ? this.this$0.context.getResources().getString(R.string.controls_badge_symbol) : null;
        Iterator it = this.this$0.badgeObservers.iterator();
        while (it.hasNext()) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) ((BadgeObserver) it.next()).menuItem;
            String str = menuItemImpl.mBadgeText;
            if (str == null || !str.equals(string)) {
                menuItemImpl.mBadgeText = string;
                menuItemImpl.mMenu.onItemsChanged(false);
            }
        }
    }
}
