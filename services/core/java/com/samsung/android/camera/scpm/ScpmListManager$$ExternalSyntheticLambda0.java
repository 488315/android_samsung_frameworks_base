package com.samsung.android.camera.scpm;

import com.samsung.android.camera.scpm.ScpmList;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ScpmListManager$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScpmList.PolicyType f$0;

    public /* synthetic */ ScpmListManager$$ExternalSyntheticLambda0(int i, ScpmList.PolicyType policyType) {
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
