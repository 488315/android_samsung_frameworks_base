package com.android.systemui.keyguard;

import com.android.systemui.keyguard.SecLifecycle;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecLifecycle$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        SecLifecycle.Msg msg = (SecLifecycle.Msg) obj;
        switch (this.$r8$classId) {
            case 0:
                return msg.msg == 1;
            default:
                return msg.msg == 3 && msg.reason == 13;
        }
    }
}
