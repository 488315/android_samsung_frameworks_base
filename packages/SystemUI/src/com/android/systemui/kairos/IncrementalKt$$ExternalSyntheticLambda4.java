package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.IncrementalImpl;
import com.android.systemui.kairos.internal.NetworkScope;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class IncrementalKt$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Incremental f$0;

    public /* synthetic */ IncrementalKt$$ExternalSyntheticLambda4(Incremental incremental, int i) {
        this.$r8$classId = i;
        this.f$0 = incremental;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        NetworkScope networkScope = (NetworkScope) obj;
        switch (this.$r8$classId) {
            case 0:
                return ((IncrementalImpl) this.f$0.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(networkScope)).patches;
            default:
                return (IncrementalImpl) this.f$0.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(networkScope);
        }
    }
}
