package com.android.server.power;

import android.provider.Settings;

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
