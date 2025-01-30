package com.android.systemui.statusbar.phone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class LightBarController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ LightBarController f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ LightBarController$$ExternalSyntheticLambda1(LightBarController lightBarController, String str) {
        this.f$0 = lightBarController;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        LightBarController lightBarController = this.f$0;
        lightBarController.updateStatus(lightBarController.mAppearanceRegions, this.f$1);
    }
}
