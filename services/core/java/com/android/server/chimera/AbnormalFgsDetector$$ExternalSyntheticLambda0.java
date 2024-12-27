package com.android.server.chimera;

import java.util.function.Predicate;

public final /* synthetic */ class AbnormalFgsDetector$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ AbnormalFgsDetector$$ExternalSyntheticLambda0(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        String str = this.f$0;
        AbnormalFgsDetector.HeavyAppItem heavyAppItem = (AbnormalFgsDetector.HeavyAppItem) obj;
        switch (i) {
        }
        return heavyAppItem.processName.equals(str);
    }
}
