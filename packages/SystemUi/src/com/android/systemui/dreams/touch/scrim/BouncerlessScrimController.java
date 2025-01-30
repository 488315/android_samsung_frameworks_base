package com.android.systemui.dreams.touch.scrim;

import android.os.PowerManager;
import android.os.SystemClock;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.unfold.util.CallbackController;
import java.util.HashSet;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerlessScrimController implements ScrimController, CallbackController {
    public final HashSet mCallbacks = new HashSet();
    public final Executor mExecutor;
    public final PowerManager mPowerManager;

    public BouncerlessScrimController(Executor executor, PowerManager powerManager) {
        this.mExecutor = executor;
        this.mPowerManager = powerManager;
    }

    @Override // com.android.systemui.dreams.touch.scrim.ScrimController
    public final void expand(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        boolean z = shadeExpansionChangeEvent.expanded;
        Executor executor = this.mExecutor;
        if (!z) {
            executor.execute(new BouncerlessScrimController$$ExternalSyntheticLambda0(this, shadeExpansionChangeEvent, 1));
            return;
        }
        this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 4, "com.android.systemui:SwipeUp");
        executor.execute(new Runnable() { // from class: com.android.systemui.dreams.touch.scrim.BouncerlessScrimController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BouncerlessScrimController.this.mCallbacks.forEach(new BouncerlessScrimController$$ExternalSyntheticLambda3());
            }
        });
    }
}
