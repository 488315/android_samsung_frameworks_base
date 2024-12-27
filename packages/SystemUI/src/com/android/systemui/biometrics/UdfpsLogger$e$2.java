package com.android.systemui.biometrics;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

final class UdfpsLogger$e$2 extends Lambda implements Function1 {
    final /* synthetic */ String $msg;

    public UdfpsLogger$e$2(String str) {
        super(1);
        this.$msg = str;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return this.$msg;
    }
}
