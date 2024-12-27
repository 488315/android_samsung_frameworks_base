package com.android.systemui.biometrics;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class UdfpsLogger$e$2 extends Lambda implements Function1 {
    final /* synthetic */ String $msg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsLogger$e$2(String str) {
        super(1);
        this.$msg = str;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return this.$msg;
    }
}
