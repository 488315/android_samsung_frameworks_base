package com.android.wm.shell.draganddrop;

import android.content.pm.ResolveInfo;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SingleIntentAppResult$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SingleIntentAppResult f$0;
    public final /* synthetic */ List f$1;

    public /* synthetic */ SingleIntentAppResult$$ExternalSyntheticLambda1(SingleIntentAppResult singleIntentAppResult, List list, int i) {
        this.$r8$classId = i;
        this.f$0 = singleIntentAppResult;
        this.f$1 = list;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SingleIntentAppResult singleIntentAppResult = this.f$0;
                List list = this.f$1;
                singleIntentAppResult.getClass();
                return singleIntentAppResult.isVisibleSingleInstance(list, ((ResolveInfo) obj).activityInfo, false);
            case 1:
                SingleIntentAppResult singleIntentAppResult2 = this.f$0;
                List list2 = this.f$1;
                singleIntentAppResult2.getClass();
                return singleIntentAppResult2.isVisibleSingleInstance(list2, ((ResolveInfo) obj).activityInfo, false);
            default:
                SingleIntentAppResult singleIntentAppResult3 = this.f$0;
                List list3 = this.f$1;
                singleIntentAppResult3.getClass();
                return singleIntentAppResult3.isVisibleSingleInstance(list3, ((ResolveInfo) obj).activityInfo, false);
        }
    }
}
