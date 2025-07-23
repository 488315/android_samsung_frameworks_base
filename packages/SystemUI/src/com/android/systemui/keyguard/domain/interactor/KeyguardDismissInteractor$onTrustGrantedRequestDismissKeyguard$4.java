package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.util.kotlin.Utils;
import kotlin.Triple;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class KeyguardDismissInteractor$onTrustGrantedRequestDismissKeyguard$4 extends AdaptedFunctionReference implements Function3 {
    public KeyguardDismissInteractor$onTrustGrantedRequestDismissKeyguard$4(Object obj) {
        super(3, obj, Utils.Companion.class, "toQuad", "toQuad(Ljava/lang/Object;Lkotlin/Triple;)Lcom/android/systemui/util/kotlin/Quad;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return ((Utils.Companion) this.receiver).toQuad((Utils.Companion) obj, (Triple) obj2);
    }
}
