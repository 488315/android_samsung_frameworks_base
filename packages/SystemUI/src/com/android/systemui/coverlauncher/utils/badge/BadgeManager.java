package com.android.systemui.coverlauncher.utils.badge;

import android.util.Log;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class BadgeManager {
    public static final Companion Companion = new Companion(null);
    public static BadgeManager mInstance;
    public final HashMap items;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static BadgeManager getInstance() {
            if (BadgeManager.mInstance == null) {
                BadgeManager.mInstance = new BadgeManager();
            }
            BadgeManager badgeManager = BadgeManager.mInstance;
            badgeManager.getClass();
            return badgeManager;
        }

        private Companion() {
        }
    }

    public BadgeManager() {
        HashMap map = new HashMap();
        this.items = map;
        map.clear();
    }

    public final void addItem(String str, BadgeItem badgeItem) {
        Log.i("CoverLauncher_BadgeManager", "add item, key : " + str + ", item : " + badgeItem);
        this.items.put(str, badgeItem);
    }
}
