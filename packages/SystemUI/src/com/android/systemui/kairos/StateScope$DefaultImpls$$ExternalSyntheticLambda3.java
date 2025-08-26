package com.android.systemui.kairos;

import com.android.systemui.kairos.util.Maybe;
import java.util.Map;
import kotlin.collections.builders.MapBuilder;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class StateScope$DefaultImpls$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ State f$0;

    public /* synthetic */ StateScope$DefaultImpls$$ExternalSyntheticLambda3(State state, int i) {
        this.$r8$classId = i;
        this.f$0 = state;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                TransactionScope transactionScope = (TransactionScope) obj;
                return transactionScope.sample((Transactional) transactionScope.sample((StateInit) this.f$0));
            default:
                StateScope stateScope = (StateScope) obj;
                Map map = (Map) stateScope.sample((IncrementalInit) this.f$0);
                MapBuilder mapBuilder = new MapBuilder();
                for (Map.Entry entry : map.entrySet()) {
                    Maybe maybe = (Maybe) stateScope.sample((State) entry.getValue());
                    if (maybe instanceof Maybe.Present) {
                        mapBuilder.put(entry.getKey(), ((Maybe.Present) maybe).value);
                    }
                }
                return mapBuilder.build();
        }
    }
}
