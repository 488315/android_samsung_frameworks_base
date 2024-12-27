package com.android.systemui.authentication.data.repository;

import com.android.internal.widget.LockPatternUtils;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class AuthenticationRepositoryImpl$isAutoConfirmFeatureEnabled$1 extends AdaptedFunctionReference implements Function2 {
    public AuthenticationRepositoryImpl$isAutoConfirmFeatureEnabled$1(Object obj) {
        super(2, obj, LockPatternUtils.class, "isAutoPinConfirmEnabled", "isAutoPinConfirmEnabled(I)Z", 4);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return Boolean.valueOf(((LockPatternUtils) this.receiver).isAutoPinConfirmEnabled(((Number) obj).intValue()));
    }
}
