package com.android.server.battery.batteryInfo;

import java.util.function.Predicate;

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
