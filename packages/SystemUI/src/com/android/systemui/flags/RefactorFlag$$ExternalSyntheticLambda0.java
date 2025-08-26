package com.android.systemui.flags;

import com.android.systemui.flags.RefactorFlag;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class RefactorFlag$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        FeatureFlags featureFlags = (FeatureFlags) obj;
        switch (this.$r8$classId) {
            case 0:
                RefactorFlag.Companion companion = RefactorFlag.Companion;
                featureFlags.getClass();
                break;
            default:
                featureFlags.getClass();
                break;
        }
        return Boolean.FALSE;
    }
}
