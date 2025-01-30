package com.android.wm.shell.draganddrop;

import android.content.pm.ResolveInfo;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
                return singleIntentAppResult.isVisibleSingleInstance(list, ((ResolveInfo) obj).activityInfo);
            case 1:
                SingleIntentAppResult singleIntentAppResult2 = this.f$0;
                List list2 = this.f$1;
                singleIntentAppResult2.getClass();
                return singleIntentAppResult2.isVisibleSingleInstance(list2, ((ResolveInfo) obj).activityInfo);
            default:
                SingleIntentAppResult singleIntentAppResult3 = this.f$0;
                List list3 = this.f$1;
                singleIntentAppResult3.getClass();
                return singleIntentAppResult3.isVisibleSingleInstance(list3, ((ResolveInfo) obj).activityInfo);
        }
    }
}
