package com.android.systemui.coverlauncher.utils.badge;

import android.util.Log;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BadgeManager {
    public static final Companion Companion = new Companion(null);
    public static BadgeManager mInstance;
    public final HashMap items;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        HashMap hashMap = new HashMap();
        this.items = hashMap;
        hashMap.clear();
    }

    public final void addItem(String str, BadgeItem badgeItem) {
        Log.i("CoverLauncher_BadgeManager", "add item, key : " + str + ", item : " + badgeItem);
        this.items.put(str, badgeItem);
    }
}
