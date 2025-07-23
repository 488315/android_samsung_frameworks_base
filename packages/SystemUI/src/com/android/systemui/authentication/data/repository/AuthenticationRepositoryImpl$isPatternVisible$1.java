package com.android.systemui.authentication.data.repository;

import com.android.internal.widget.LockPatternUtils;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class AuthenticationRepositoryImpl$isPatternVisible$1 extends AdaptedFunctionReference implements Function2 {
    public AuthenticationRepositoryImpl$isPatternVisible$1(Object obj) {
        super(2, obj, LockPatternUtils.class, "isVisiblePatternEnabled", "isVisiblePatternEnabled(I)Z", 4);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return Boolean.valueOf(((LockPatternUtils) this.receiver).isVisiblePatternEnabled(((Number) obj).intValue()));
    }
}
