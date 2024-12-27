package com.android.systemui.slimindicator;

import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SlimIndicatorReceiverManager {
    public boolean mIsRegistered = false;
    public final ArrayList receivers;

    public SlimIndicatorReceiverManager(SlimIndicatorSettingsBackUpManager slimIndicatorSettingsBackUpManager) {
        SlimIndicatorIconBlacklistReceiver slimIndicatorIconBlacklistReceiver = new SlimIndicatorIconBlacklistReceiver(slimIndicatorSettingsBackUpManager);
        SlimIndicatorPackageReceiver slimIndicatorPackageReceiver = new SlimIndicatorPackageReceiver(slimIndicatorSettingsBackUpManager);
        ArrayList arrayList = new ArrayList();
        this.receivers = arrayList;
        arrayList.add(slimIndicatorIconBlacklistReceiver);
        arrayList.add(slimIndicatorPackageReceiver);
    }
}
