package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.BuildScopeImpl;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class BuildScope$DefaultImpls$$ExternalSyntheticLambda12 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BuildScope$DefaultImpls$$ExternalSyntheticLambda12(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        BuildScope buildScope = (BuildScope) obj;
        switch (this.$r8$classId) {
            case 0:
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                return ((Function1) buildScopeImpl.sample((StateInit) this.f$0)).mo779invoke(buildScopeImpl);
            default:
                Map map = (Map) ((DeferredValue) this.f$0).unwrapped.getValue();
                Unit unit = Unit.INSTANCE;
                if (map.containsKey(unit)) {
                    return map.getOrDefault(unit, new Function0() { // from class: com.android.systemui.kairos.BuildScope$applyLatestSpec$outInit$1$2
                        @Override // kotlin.jvm.functions.Function0
                        public final /* bridge */ /* synthetic */ Object invoke() {
                            return null;
                        }
                    });
                }
                throw new IllegalStateException(("applyLatest: expected initial result, but none present in: " + map).toString());
        }
    }
}
