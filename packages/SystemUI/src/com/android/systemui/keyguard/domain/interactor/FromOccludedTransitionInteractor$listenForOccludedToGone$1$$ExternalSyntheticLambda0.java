package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.util.kotlin.Quint;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class FromOccludedTransitionInteractor$listenForOccludedToGone$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Pair pair = (Pair) obj;
                return Boolean.valueOf((((Boolean) pair.component1()).booleanValue() || ((Boolean) pair.component2()).booleanValue()) ? false : true);
            default:
                Quint quint = (Quint) obj;
                return Boolean.valueOf(!((Boolean) quint.component1()).booleanValue() && ((Boolean) quint.component2()).booleanValue());
        }
    }
}
