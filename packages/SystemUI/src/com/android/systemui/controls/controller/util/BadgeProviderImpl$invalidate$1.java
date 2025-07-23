package com.android.systemui.controls.controller.util;

import androidx.appcompat.view.menu.MenuItemImpl;
import com.android.systemui.R;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BadgeProviderImpl$invalidate$1 implements Runnable {
    public final /* synthetic */ BadgeProviderImpl this$0;

    public BadgeProviderImpl$invalidate$1(BadgeProviderImpl badgeProviderImpl) {
        this.this$0 = badgeProviderImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String string = !this.this$0.badgeRequiredSet.isEmpty() ? this.this$0.context.getResources().getString(R.string.controls_badge_symbol) : null;
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
