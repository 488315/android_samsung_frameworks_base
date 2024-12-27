package com.android.systemui.keyguard.data.repository;

import android.content.pm.UserInfo;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class TrustRepositoryImpl$isCurrentUserTrustManaged$2 extends AdaptedFunctionReference implements Function3 {
    public static final TrustRepositoryImpl$isCurrentUserTrustManaged$2 INSTANCE = new TrustRepositoryImpl$isCurrentUserTrustManaged$2();

    public TrustRepositoryImpl$isCurrentUserTrustManaged$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair(obj, (UserInfo) obj2);
    }
}
