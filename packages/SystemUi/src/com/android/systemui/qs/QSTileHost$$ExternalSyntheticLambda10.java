package com.android.systemui.qs;

import com.android.systemui.Dumpable;
import com.android.systemui.qs.nano.QsTileState;
import com.android.systemui.plugins.qs.QSTile;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda10 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Objects.nonNull((QsTileState) obj);
            default:
                return ((QSTile) obj) instanceof Dumpable;
        }
    }
}
