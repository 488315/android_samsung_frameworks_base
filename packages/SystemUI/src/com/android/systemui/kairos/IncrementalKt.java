package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.util.MapPatchKt;
import com.android.systemui.kairos.util.WithPrev;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class IncrementalKt {
    public static final Incremental asIncremental(StateInit stateInit) {
        final int i = 0;
        final int i2 = 1;
        return new IncrementalInit(new Init("asIncremental", new IncrementalKt$$ExternalSyntheticLambda2(StateKt.map(stateInit, new Function2() { // from class: com.android.systemui.kairos.IncrementalKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i) {
                    case 0:
                        Map map = (Map) obj2;
                        return map instanceof HashMap ? (HashMap) map : new HashMap(map);
                    default:
                        WithPrev withPrev = (WithPrev) obj2;
                        Map mapPatchFromFullDiff = MapPatchKt.mapPatchFromFullDiff((Map) withPrev.previousValue, (Map) withPrev.newValue);
                        if (((HashMap) mapPatchFromFullDiff).isEmpty()) {
                            return null;
                        }
                        return mapPatchFromFullDiff;
                }
            }
        }), EventsKt.mapNotNull(EventsKt.map(StateKt.getChanges(stateInit), new StateKt$$ExternalSyntheticLambda8(stateInit, 0)), new Function2() { // from class: com.android.systemui.kairos.IncrementalKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i2) {
                    case 0:
                        Map map = (Map) obj2;
                        return map instanceof HashMap ? (HashMap) map : new HashMap(map);
                    default:
                        WithPrev withPrev = (WithPrev) obj2;
                        Map mapPatchFromFullDiff = MapPatchKt.mapPatchFromFullDiff((Map) withPrev.previousValue, (Map) withPrev.newValue);
                        if (((HashMap) mapPatchFromFullDiff).isEmpty()) {
                            return null;
                        }
                        return mapPatchFromFullDiff;
                }
            }
        }), 0)));
    }

    public static final IncrementalInit mapValues(Incremental incremental, Function2 function2) {
        return new IncrementalInit(new Init("mapValues", new IncrementalKt$$ExternalSyntheticLambda2(incremental, function2, 1)));
    }
}
