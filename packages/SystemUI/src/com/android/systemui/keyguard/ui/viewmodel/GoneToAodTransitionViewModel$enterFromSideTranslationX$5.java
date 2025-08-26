package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.StateToValue;
import com.android.systemui.power.shared.model.WakefulnessModel;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes2.dex */
final /* synthetic */ class GoneToAodTransitionViewModel$enterFromSideTranslationX$5 extends AdaptedFunctionReference implements Function3 {
    public static final GoneToAodTransitionViewModel$enterFromSideTranslationX$5 INSTANCE = new GoneToAodTransitionViewModel$enterFromSideTranslationX$5();

    public GoneToAodTransitionViewModel$enterFromSideTranslationX$5() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair((StateToValue) obj, (WakefulnessModel) obj2);
    }
}
