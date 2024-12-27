package com.android.server.battery.batteryInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FirstUseDateData$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FirstUseDateData f$0;

    public /* synthetic */ FirstUseDateData$$ExternalSyntheticLambda0(
            FirstUseDateData firstUseDateData, int i) {
        this.$r8$classId = i;
        this.f$0 = firstUseDateData;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        FirstUseDateData firstUseDateData = this.f$0;
        String str = (String) obj;
        switch (i) {
            case 0:
                firstUseDateData.getClass();
                break;
            default:
                firstUseDateData.getClass();
                break;
        }
        return FirstUseDateData.isValidDate(str);
    }
}
