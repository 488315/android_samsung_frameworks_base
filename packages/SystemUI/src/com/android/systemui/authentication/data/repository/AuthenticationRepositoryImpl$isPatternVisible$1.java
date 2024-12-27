package com.android.systemui.authentication.data.repository;

import com.android.internal.widget.LockPatternUtils;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class AuthenticationRepositoryImpl$isPatternVisible$1 extends AdaptedFunctionReference implements Function2 {
    public AuthenticationRepositoryImpl$isPatternVisible$1(Object obj) {
        super(2, obj, LockPatternUtils.class, "isVisiblePatternEnabled", "isVisiblePatternEnabled(I)Z", 4);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return Boolean.valueOf(((LockPatternUtils) this.receiver).isVisiblePatternEnabled(((Number) obj).intValue()));
    }
}
