package com.android.systemui.keyguard.domain.interactor;

import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes2.dex */
final /* synthetic */ class KeyguardEnabledInteractor$showKeyguardWhenReenabled$5 extends AdaptedFunctionReference implements Function3 {
    public static final KeyguardEnabledInteractor$showKeyguardWhenReenabled$5 INSTANCE = new KeyguardEnabledInteractor$showKeyguardWhenReenabled$5();

    public KeyguardEnabledInteractor$showKeyguardWhenReenabled$5() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        Boolean bool2 = (Boolean) obj2;
        bool2.booleanValue();
        return new Pair(bool, bool2);
    }
}
