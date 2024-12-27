package com.android.server.power;

import android.provider.Settings;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ScreenOnKeeper$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenOnKeeper f$0;

    public /* synthetic */ ScreenOnKeeper$$ExternalSyntheticLambda1(
            ScreenOnKeeper screenOnKeeper, int i) {
        this.$r8$classId = i;
        this.f$0 = screenOnKeeper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ScreenOnKeeper screenOnKeeper = this.f$0;
        switch (i) {
            case 0:
                screenOnKeeper
                        .mContext
                        .getContentResolver()
                        .unregisterContentObserver(screenOnKeeper.mSettingsObserver);
                break;
            default:
                screenOnKeeper.getClass();
                screenOnKeeper.mSettingsObserver =
                        new ScreenOnKeeper.SettingsObserver(
                                screenOnKeeper, screenOnKeeper.mHandler);
                screenOnKeeper
                        .mContext
                        .getContentResolver()
                        .registerContentObserver(
                                Settings.Global.getUriFor("screen_on_keeper"),
                                false,
                                screenOnKeeper.mSettingsObserver,
                                -1);
                break;
        }
    }
}
