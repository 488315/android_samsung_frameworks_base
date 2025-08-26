package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.power.shared.model.WakefulnessModel;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class FromAodTransitionInteractor$listenForAodToAwake$1$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return Boolean.valueOf(((WakefulnessModel) obj).isAwake());
    }
}
