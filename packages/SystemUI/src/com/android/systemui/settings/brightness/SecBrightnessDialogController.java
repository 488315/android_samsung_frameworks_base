package com.android.systemui.settings.brightness;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecBrightnessDialogController {
    public SecBrightnessDialogController$createTimer$1 countDownTimer;
    public final BrightnessDialog dialog;
    public final Lazy keyguardUpdateMonitor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.settings.brightness.SecBrightnessDialogController$keyguardUpdateMonitor$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        }
    });
    public final Lazy resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.settings.brightness.SecBrightnessDialogController$resourcePicker$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        }
    });
    public final SecBrightnessDialogController$updateMonitorCallback$1 updateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.settings.brightness.SecBrightnessDialogController$updateMonitorCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStartedGoingToSleep(int i) {
            SecBrightnessDialogController.this.dialog.finish();
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.settings.brightness.SecBrightnessDialogController$updateMonitorCallback$1] */
    public SecBrightnessDialogController(BrightnessDialog brightnessDialog) {
        this.dialog = brightnessDialog;
    }
}
