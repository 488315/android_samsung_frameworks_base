package com.android.systemui.settings.brightness;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecBrightnessDialogController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SecBrightnessDialogController$createTimer$1 countDownTimer;
    public final BrightnessDialog dialog;
    public final Lazy keyguardUpdateMonitor$delegate;
    public final Lazy resourcePicker$delegate;
    public final SecBrightnessDialogController$updateMonitorCallback$1 updateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.settings.brightness.SecBrightnessDialogController$updateMonitorCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStartedGoingToSleep(int i) {
            SecBrightnessDialogController.this.dialog.finish();
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.settings.brightness.SecBrightnessDialogController$updateMonitorCallback$1] */
    public SecBrightnessDialogController(BrightnessDialog brightnessDialog) {
        this.dialog = brightnessDialog;
        final int i = 0;
        this.keyguardUpdateMonitor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.settings.brightness.SecBrightnessDialogController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = SecBrightnessDialogController.$r8$clinit;
                        return (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
                    default:
                        int i3 = SecBrightnessDialogController.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                }
            }
        });
        final int i2 = 1;
        this.resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.settings.brightness.SecBrightnessDialogController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = SecBrightnessDialogController.$r8$clinit;
                        return (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
                    default:
                        int i3 = SecBrightnessDialogController.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                }
            }
        });
    }
}
