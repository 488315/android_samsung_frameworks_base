package com.android.systemui.statusbar.phone;

import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShadeController f$0;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda1(ShadeController shadeController, int i) {
        this.$r8$classId = i;
        this.f$0 = shadeController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((ShadeControllerImpl) this.f$0).animateCollapseShade(0);
                break;
            case 1:
                ((ShadeControllerImpl) this.f$0).animateCollapsePanels(1.0f, 0, true, false);
                break;
            case 2:
                ((ShadeControllerImpl) this.f$0).collapseShade();
                break;
            default:
                ((ShadeControllerImpl) this.f$0).makeExpandedInvisible();
                break;
        }
    }
}
