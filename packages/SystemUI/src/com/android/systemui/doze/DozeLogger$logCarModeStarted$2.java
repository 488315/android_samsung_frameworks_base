package com.android.systemui.doze;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DozeLogger$logCarModeStarted$2 extends Lambda implements Function1 {
    public static final DozeLogger$logCarModeStarted$2 INSTANCE = new DozeLogger$logCarModeStarted$2();

    public DozeLogger$logCarModeStarted$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return "Doze car mode started";
    }
}
