package com.android.server.battery.batteryInfo;

import java.util.function.IntPredicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FirstUseDateData$$ExternalSyntheticLambda1
        implements IntPredicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FirstUseDateData f$0;

    public /* synthetic */ FirstUseDateData$$ExternalSyntheticLambda1(
            FirstUseDateData firstUseDateData, int i) {
        this.$r8$classId = i;
        this.f$0 = firstUseDateData;
    }

    @Override // java.util.function.IntPredicate
    public final boolean test(int i) {
        int i2 = this.$r8$classId;
        FirstUseDateData firstUseDateData = this.f$0;
        switch (i2) {
        }
        return firstUseDateData.mShouldCheckFaiExpireds[i];
    }
}
