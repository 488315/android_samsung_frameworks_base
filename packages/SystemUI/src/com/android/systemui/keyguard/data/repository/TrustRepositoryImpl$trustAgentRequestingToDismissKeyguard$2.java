package com.android.systemui.keyguard.data.repository;

import android.content.pm.UserInfo;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class TrustRepositoryImpl$trustAgentRequestingToDismissKeyguard$2 extends AdaptedFunctionReference implements Function3 {
    public static final TrustRepositoryImpl$trustAgentRequestingToDismissKeyguard$2 INSTANCE = new TrustRepositoryImpl$trustAgentRequestingToDismissKeyguard$2();

    public TrustRepositoryImpl$trustAgentRequestingToDismissKeyguard$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair(obj, (UserInfo) obj2);
    }
}
