package com.android.systemui.keyguard.ui.preview;

import android.content.res.Resources;
import com.android.systemui.plugins.clocks.ClockController;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class KeyguardPreviewRenderer$setupKeyguardRootView$1 extends FunctionReferenceImpl implements Function3 {
    public KeyguardPreviewRenderer$setupKeyguardRootView$1(Object obj) {
        super(3, obj, KeyguardPreviewRenderer.class, "updateClockAppearance", "updateClockAppearance(Lcom/android/systemui/plugins/clocks/ClockController;Landroid/content/res/Resources;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return KeyguardPreviewRenderer.access$updateClockAppearance((KeyguardPreviewRenderer) this.receiver, (ClockController) obj, (Resources) obj2, (Continuation) obj3);
    }
}
