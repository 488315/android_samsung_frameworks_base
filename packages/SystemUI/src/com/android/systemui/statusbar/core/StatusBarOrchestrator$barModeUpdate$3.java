package com.android.systemui.statusbar.core;

import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import kotlin.Triple;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes3.dex */
final /* synthetic */ class StatusBarOrchestrator$barModeUpdate$3 extends AdaptedFunctionReference implements Function4 {
    public static final StatusBarOrchestrator$barModeUpdate$3 INSTANCE = new StatusBarOrchestrator$barModeUpdate$3();

    public StatusBarOrchestrator$barModeUpdate$3() {
        super(4, Triple.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return new Triple(bool, (PhoneStatusBarTransitions) obj2, (StatusBarMode) obj3);
    }
}
