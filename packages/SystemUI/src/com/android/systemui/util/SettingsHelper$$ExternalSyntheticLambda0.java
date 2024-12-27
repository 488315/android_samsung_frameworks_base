package com.android.systemui.util;

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
