package com.android.systemui.coverlauncher.utils.badge;

import android.util.Log;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class BadgeManager {
    public static final Companion Companion = new Companion(null);
    public static BadgeManager mInstance;
    public final HashMap items;

    public final class Companion {
        private Companion() {
        }

        public static BadgeManager getInstance() {
            if (BadgeManager.mInstance == null) {
                BadgeManager.mInstance = new BadgeManager();
            }
            BadgeManager badgeManager = BadgeManager.mInstance;
            Intrinsics.checkNotNull(badgeManager);
            return badgeManager;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BadgeManager() {
        HashMap hashMap = new HashMap();
        this.items = hashMap;
        hashMap.clear();
    }

    public final void addItem(String str, BadgeItem badgeItem) {
        Log.i("CoverLauncher_BadgeManager", "add item, key : " + str + ", item : " + badgeItem);
        this.items.put(str, badgeItem);
    }
}
