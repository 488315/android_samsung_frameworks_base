package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$scrimManagerCallback$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ScrimManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScrimManager f$0;
    public final /* synthetic */ BouncerSwipeTouchHandler$scrimManagerCallback$1 f$1;

    public /* synthetic */ ScrimManager$$ExternalSyntheticLambda1(ScrimManager scrimManager, BouncerSwipeTouchHandler$scrimManagerCallback$1 bouncerSwipeTouchHandler$scrimManagerCallback$1, int i) {
        this.$r8$classId = i;
        this.f$0 = scrimManager;
        this.f$1 = bouncerSwipeTouchHandler$scrimManagerCallback$1;
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
