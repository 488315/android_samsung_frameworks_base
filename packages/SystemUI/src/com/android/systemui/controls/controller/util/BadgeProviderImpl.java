package com.android.systemui.controls.controller.util;

import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.appcompat.view.menu.MenuItemImpl;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BadgeProviderImpl implements BadgeProvider, BadgeSubject {
    public static final Companion Companion = new Companion(null);
    public final Set badgeNotRequiredSet;
    public final Set badgeObservers = new LinkedHashSet();
    public final Set badgeRequiredSet;
    public final DelayableExecutor bgExecutor;
    public final Context context;
    public final DelayableExecutor uiExecutor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static Set toPackagesSet(Set set) {
            Set set2 = set;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
            Iterator it = set2.iterator();
            while (it.hasNext()) {
                arrayList.add(((ComponentName) it.next()).getPackageName());
            }
            return CollectionsKt___CollectionsKt.toSet(arrayList);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BadgeProviderImpl(Context context, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2) {
        this.context = context;
        this.bgExecutor = delayableExecutor;
        this.uiExecutor = delayableExecutor2;
        this.badgeRequiredSet = Prefs.get(context).getStringSet("ControlsBadgeRequired", new LinkedHashSet());
        this.badgeNotRequiredSet = Prefs.get(context).getStringSet("ControlsBadgeNotRequired", new LinkedHashSet());
    }

    public static final void dismiss$flush$9(final BadgeProviderImpl badgeProviderImpl, final Set set, final String str, String str2) {
        badgeProviderImpl.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.util.BadgeProviderImpl$dismiss$flush$1$1
            @Override // java.lang.Runnable
            public final void run() {
                BadgeProviderImpl badgeProviderImpl2 = BadgeProviderImpl.this;
                String str3 = str;
                Set set2 = set;
                BadgeProviderImpl.Companion companion = BadgeProviderImpl.Companion;
                badgeProviderImpl2.putPackagesSet(str3, set2);
            }
        });
        Log.d("BadgeProviderImpl", "dismiss(): " + str2 + ": " + set);
    }

    public static final void onServicesUpdated$flush(BadgeProviderImpl badgeProviderImpl, Set set, String str, String str2) {
        badgeProviderImpl.putPackagesSet(str, set);
        Log.d("BadgeProviderImpl", "onServicesUpdated(): " + str2 + ": " + set);
    }

    public final void dismiss() {
        Set set = this.badgeRequiredSet;
        if (set.isEmpty()) {
            set = null;
        }
        if (set != null) {
            Iterator it = this.badgeObservers.iterator();
            while (it.hasNext()) {
                MenuItemImpl menuItemImpl = (MenuItemImpl) ((BadgeObserver) it.next()).menuItem;
                String str = menuItemImpl.mBadgeText;
                if (str == null || !str.equals(null)) {
                    menuItemImpl.mBadgeText = null;
                    menuItemImpl.mMenu.onItemsChanged(false);
                }
            }
            Set set2 = this.badgeNotRequiredSet;
            set2.addAll(set);
            dismiss$flush$9(this, set2, "ControlsBadgeNotRequired", "badgeNotRequiredSet");
            set.clear();
            dismiss$flush$9(this, set, "ControlsBadgeRequired", "badgeRequiredSet");
        }
    }

    public final void putPackagesSet(String str, Set set) {
        if (!set.isEmpty()) {
            Prefs.get(this.context).edit().putStringSet(str, set).apply();
        } else {
            Prefs.get(this.context).edit().remove(str).apply();
        }
    }

    public final void setDescription(ComponentName componentName, View view, CharSequence charSequence) {
        if (!this.badgeRequiredSet.contains(componentName.getPackageName())) {
            view.setContentDescription(charSequence);
            return;
        }
        view.setContentDescription(((Object) charSequence) + ", " + this.context.getResources().getString(R.string.controls_badge_description));
    }
}
