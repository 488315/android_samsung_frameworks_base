package com.android.systemui.coverlauncher.utils.badge;

import android.util.Log;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BadgeManager {
    public static BadgeManager mInstance;
    public final HashMap mItems = new HashMap();

    public static BadgeManager getInstance() {
        if (mInstance == null) {
            mInstance = new BadgeManager();
        }
        return mInstance;
    }

    public final void addItem(BadgeItem badgeItem, String str) {
        Log.i("CoverLauncher_BadgeManager", "add item, key : " + str + ", item : " + badgeItem);
        this.mItems.put(str, badgeItem);
    }
}
