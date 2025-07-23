package com.android.systemui.util;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SettingsHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SettingsHelper f$0;

    public /* synthetic */ SettingsHelper$$ExternalSyntheticLambda0(SettingsHelper settingsHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = settingsHelper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SettingsHelper settingsHelper = this.f$0;
        switch (i) {
            case 0:
                settingsHelper.lambda$onUserSwitched$1();
                break;
            default:
                settingsHelper.lambda$new$0();
                break;
        }
    }
}
