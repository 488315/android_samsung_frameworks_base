package com.android.systemui.util;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
