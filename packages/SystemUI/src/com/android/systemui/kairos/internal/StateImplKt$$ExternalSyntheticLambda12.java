package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.StoreEntry;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class StateImplKt$$ExternalSyntheticLambda12 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return PullNodesKt.neverImpl;
            default:
                Map.Entry entry = (Map.Entry) obj;
                return new StoreEntry(entry.getKey(), ((StateImpl) entry.getValue()).changes);
        }
    }
}
