package com.samsung.android.camera.scpm;

import java.util.function.Predicate;

public final /* synthetic */ class ScpmListManager$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScpmList.PolicyType f$0;

    public /* synthetic */ ScpmListManager$$ExternalSyntheticLambda0(
            int i, ScpmList.PolicyType policyType) {
        this.$r8$classId = i;
        this.f$0 = policyType;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        ScpmList.PolicyType policyType = this.f$0;
        ScpmList scpmList = (ScpmList) obj;
        switch (i) {
        }
        return scpmList.mType.equals(policyType);
    }
}
