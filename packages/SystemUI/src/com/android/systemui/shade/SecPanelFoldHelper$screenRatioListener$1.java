package com.android.systemui.shade;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class SecPanelFoldHelper$screenRatioListener$1 extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String INTENT_ACTION;
    public final Lazy handler$delegate;
    public final /* synthetic */ SecPanelFoldHelper this$0;

    public SecPanelFoldHelper$screenRatioListener$1(SecPanelFoldHelper secPanelFoldHelper) {
        this.this$0 = secPanelFoldHelper;
        LazyKt__LazyJVMKt.lazy(new SecPanelFoldHelper$$ExternalSyntheticLambda0(1));
        this.handler$delegate = LazyKt__LazyJVMKt.lazy(new SecPanelFoldHelper$$ExternalSyntheticLambda0(2));
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
