package com.android.systemui.shade;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecPanelFoldHelper$screenRatioListener$1 extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String INTENT_ACTION;
    public final Lazy handler$delegate;
    public final /* synthetic */ SecPanelFoldHelper this$0;

    public SecPanelFoldHelper$screenRatioListener$1(SecPanelFoldHelper secPanelFoldHelper) {
        this.this$0 = secPanelFoldHelper;
        final int i = 0;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelFoldHelper$screenRatioListener$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = SecPanelFoldHelper$screenRatioListener$1.$r8$clinit;
                        return (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
                    default:
                        int i3 = SecPanelFoldHelper$screenRatioListener$1.$r8$clinit;
                        return (Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER);
                }
            }
        });
        final int i2 = 1;
        this.handler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelFoldHelper$screenRatioListener$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = SecPanelFoldHelper$screenRatioListener$1.$r8$clinit;
                        return (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
                    default:
                        int i3 = SecPanelFoldHelper$screenRatioListener$1.$r8$clinit;
                        return (Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER);
                }
            }
        });
        this.INTENT_ACTION = "com.samsung.intent.action.SET_SCREEN_RATIO_VALUE";
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (Intrinsics.areEqual(this.INTENT_ACTION, intent.getAction())) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onReceive(", this.INTENT_ACTION, ")", "SecPanelFoldHelper");
            this.this$0.getClass();
        }
    }
}
