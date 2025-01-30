package com.android.systemui.keyguard.data.repository;

import android.content.pm.UserInfo;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$2 extends AdaptedFunctionReference implements Function3 {
    public static final TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$2 INSTANCE = new TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$2();

    public TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair(obj, (UserInfo) obj2);
    }
}
