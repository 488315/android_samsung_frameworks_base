package com.android.server.pm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppCategoryHintHelper$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppCategoryHintHelper f$0;

    public /* synthetic */ AppCategoryHintHelper$$ExternalSyntheticLambda5(AppCategoryHintHelper appCategoryHintHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = appCategoryHintHelper;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        AppCategoryHintHelper appCategoryHintHelper = this.f$0;
        String str = (String) obj;
        switch (i) {
            case 0:
                appCategoryHintHelper.sendAppCategoryBroadcast(appCategoryHintHelper.mCategoryMap.containsKey(str) ? ((Integer) appCategoryHintHelper.mCategoryMap.get(str)).intValue() : -1, str);
                break;
            default:
                appCategoryHintHelper.sendAppCategoryBroadcast(appCategoryHintHelper.mAppCategoryFilter.getPackageCategory(str), str);
                break;
        }
    }
}
