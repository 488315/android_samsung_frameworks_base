package com.android.settingslib.mobile.dataservice;

import androidx.room.InvalidationTracker;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MobileNetworkDatabase_Impl extends MobileNetworkDatabase {
    @Override // androidx.room.RoomDatabase
    public final InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "subscriptionInfo", "uiccInfo", "MobileNetworkInfo");
    }
}
