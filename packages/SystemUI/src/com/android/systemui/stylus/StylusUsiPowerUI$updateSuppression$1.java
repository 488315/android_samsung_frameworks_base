package com.android.systemui.stylus;

import android.os.Build;
import com.android.systemui.log.DebugLogger;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StylusUsiPowerUI$updateSuppression$1 implements Runnable {
    public final /* synthetic */ boolean $suppress;
    public final /* synthetic */ StylusUsiPowerUI this$0;

    public StylusUsiPowerUI$updateSuppression$1(StylusUsiPowerUI stylusUsiPowerUI, boolean z) {
        this.this$0 = stylusUsiPowerUI;
        this.$suppress = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.this$0.suppressed == this.$suppress) {
            return;
        }
        int i = DebugLogger.$r8$clinit;
        boolean z = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(StylusUsiPowerUI.class).getSimpleName();
        StylusUsiPowerUI stylusUsiPowerUI = this.this$0;
        stylusUsiPowerUI.suppressed = this.$suppress;
        stylusUsiPowerUI.handler.post(new StylusUsiPowerUI$refresh$1(stylusUsiPowerUI));
    }
}
