package com.android.systemui.statusbar.phone;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class LightBarController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LightBarController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                LightBarController lightBarController = (LightBarController) this.f$0;
                LightBarTransitionsController lightBarTransitionsController = (LightBarTransitionsController) obj;
                if (lightBarTransitionsController == null) {
                    lightBarController.getClass();
                    break;
                } else {
                    lightBarTransitionsController.setIconsDark(lightBarController.mNavigationLight, lightBarController.animateChange());
                    break;
                }
            default:
                Consumer consumer = (Consumer) this.f$0;
                LightBarTransitionsController lightBarTransitionsController2 = (LightBarTransitionsController) obj;
                if (lightBarTransitionsController2 != null) {
                    consumer.accept(lightBarTransitionsController2);
                    break;
                }
                break;
        }
    }
}
