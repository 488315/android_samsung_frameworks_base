package com.android.systemui.slimindicator;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class SlimIndicatorReceiverManager {
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
