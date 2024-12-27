package com.android.systemui.coverlauncher.utils.badge;

import android.util.Log;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BadgeManager {
    public static final Companion Companion = new Companion(null);
    public static BadgeManager mInstance;
    public final HashMap items;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
