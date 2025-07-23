package com.android.wm.shell.windowdecor;

import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecoration$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopModeWindowDecoration f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ DesktopModeWindowDecoration$$ExternalSyntheticLambda7(DesktopModeWindowDecoration desktopModeWindowDecoration, boolean z, boolean z2, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopModeWindowDecoration;
        this.f$1 = z;
        this.f$2 = z2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DesktopModeWindowDecoration desktopModeWindowDecoration = this.f$0;
                boolean z = this.f$1;
                boolean z2 = this.f$2;
                desktopModeWindowDecoration.getClass();
                if (((Boolean) obj).booleanValue() || z) {
                    desktopModeWindowDecoration.updateExclusionRegion(z2);
                    break;
                }
            default:
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = this.f$0;
                boolean z3 = this.f$1;
                boolean z4 = this.f$2;
                desktopModeWindowDecoration2.getClass();
                if (((Boolean) obj).booleanValue() || z3) {
                    desktopModeWindowDecoration2.updateExclusionRegion(z4);
                    break;
                }
        }
    }
}
