package com.android.systemui.dreams.touch.scrim;

import com.android.systemui.dreams.touch.BouncerSwipeTouchHandler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class ScrimManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScrimManager f$0;
    public final /* synthetic */ BouncerSwipeTouchHandler.C12851 f$1;

    public /* synthetic */ ScrimManager$$ExternalSyntheticLambda1(ScrimManager scrimManager, BouncerSwipeTouchHandler.C12851 c12851, int i) {
        this.$r8$classId = i;
        this.f$0 = scrimManager;
        this.f$1 = c12851;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ScrimManager scrimManager = this.f$0;
                scrimManager.mCallbacks.add(this.f$1);
                break;
            default:
                ScrimManager scrimManager2 = this.f$0;
                scrimManager2.mCallbacks.remove(this.f$1);
                break;
        }
    }
}
