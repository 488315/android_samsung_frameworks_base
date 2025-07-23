package com.android.systemui.slimindicator;

import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
