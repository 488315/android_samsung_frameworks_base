package com.android.wm.shell.back;

import com.android.wm.shell.shared.TransitionUtil;
import java.util.function.Predicate;

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
