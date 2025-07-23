package com.android.systemui.user.domain.interactor;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class UserLogoutInteractor$isLogoutEnabled$1 extends AdaptedFunctionReference implements Function3 {
    public static final UserLogoutInteractor$isLogoutEnabled$1 INSTANCE = new UserLogoutInteractor$isLogoutEnabled$1();

    public UserLogoutInteractor$isLogoutEnabled$1() {
        super(3, Boolean.TYPE, "or", "or(Z)Z", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return Boolean.valueOf(((Boolean) obj).booleanValue() | ((Boolean) obj2).booleanValue());
    }
}
