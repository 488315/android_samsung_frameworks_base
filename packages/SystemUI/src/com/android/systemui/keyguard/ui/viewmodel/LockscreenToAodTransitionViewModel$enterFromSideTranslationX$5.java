package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.StateToValue;
import com.android.systemui.power.shared.model.WakefulnessModel;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class LockscreenToAodTransitionViewModel$enterFromSideTranslationX$5 extends AdaptedFunctionReference implements Function3 {
    public static final LockscreenToAodTransitionViewModel$enterFromSideTranslationX$5 INSTANCE = new LockscreenToAodTransitionViewModel$enterFromSideTranslationX$5();

    public LockscreenToAodTransitionViewModel$enterFromSideTranslationX$5() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair((StateToValue) obj, (WakefulnessModel) obj2);
    }
}
