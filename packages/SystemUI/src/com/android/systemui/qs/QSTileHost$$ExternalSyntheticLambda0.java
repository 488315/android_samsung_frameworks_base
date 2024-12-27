package com.android.systemui.qs;

import com.android.systemui.Dumpable;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.nano.QsTileState;
import java.util.Objects;
import java.util.function.Predicate;

public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((QSTile) obj) instanceof Dumpable;
            default:
                return Objects.nonNull((QsTileState) obj);
        }
    }
}
