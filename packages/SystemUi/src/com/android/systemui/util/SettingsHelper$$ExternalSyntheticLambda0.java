package com.android.systemui.util;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SettingsHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SettingsHelper f$0;

    public /* synthetic */ SettingsHelper$$ExternalSyntheticLambda0(SettingsHelper settingsHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = settingsHelper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SettingsHelper settingsHelper = this.f$0;
                settingsHelper.readSettingsDB();
                settingsHelper.registerSettingsObserver();
                break;
            default:
                this.f$0.registerSettingsObserver();
                break;
        }
    }
}
