package com.android.systemui.bouncer.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes.dex */
final /* synthetic */ class AlternateBouncerInteractor$canShowAlternateBouncer$1$3 extends AdaptedFunctionReference implements Function3 {
    public static final AlternateBouncerInteractor$canShowAlternateBouncer$1$3 INSTANCE = new AlternateBouncerInteractor$canShowAlternateBouncer$1$3();

    public AlternateBouncerInteractor$canShowAlternateBouncer$1$3() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int i = AlternateBouncerInteractor.$r8$clinit;
        return new Pair((KeyguardState) obj, (SceneKey) obj2);
    }
}
