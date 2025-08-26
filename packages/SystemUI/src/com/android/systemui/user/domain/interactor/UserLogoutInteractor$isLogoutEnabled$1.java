package com.android.systemui.user.domain.interactor;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

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
