package com.android.systemui.doze;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
