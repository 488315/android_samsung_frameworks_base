package com.android.wm.shell.back;

import com.android.wm.shell.shared.TransitionUtil;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BackAnimationController$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Integer num = (Integer) obj;
        switch (this.$r8$classId) {
            case 0:
                return TransitionUtil.isOpenOrCloseMode(num.intValue());
            case 1:
                return TransitionUtil.isClosingMode(num.intValue());
            default:
                return TransitionUtil.isOpeningMode(num.intValue());
        }
    }
}
