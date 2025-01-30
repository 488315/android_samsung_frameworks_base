package com.android.systemui.controls.controller.util;

import androidx.appcompat.view.menu.MenuItemImpl;
import com.android.systemui.R;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
            ((MenuItemImpl) ((BadgeObserver) it.next()).menuItem).setBadgeText(string);
        }
    }
}
