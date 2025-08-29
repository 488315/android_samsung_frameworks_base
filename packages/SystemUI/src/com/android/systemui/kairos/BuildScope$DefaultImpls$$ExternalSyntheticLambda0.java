package com.android.systemui.kairos;

import com.android.systemui.kairos.util.Maybe;
import java.util.Collections;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class BuildScope$DefaultImpls$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BuildScope$DefaultImpls$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return Unit.INSTANCE;
            case 1:
                Unit unit = Unit.INSTANCE;
                Maybe.Companion.getClass();
                Pair pair = new Pair(unit, Maybe.Present.m2588boximpl((Function1) obj2));
                return Collections.singletonMap(pair.getFirst(), pair.getSecond());
            case 2:
                Map map = (Map) obj2;
                Object obj3 = map.get(Unit.INSTANCE);
                if (obj3 != null) {
                    return (Maybe) obj3;
                }
                throw new IllegalStateException(("applyLatest: expected result, but none present in: " + map).toString());
            default:
                return ((Function1) obj2).mo781invoke(obj);
        }
    }
}
