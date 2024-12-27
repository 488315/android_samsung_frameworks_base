package com.android.server.pm;

import java.util.function.Consumer;

public final /* synthetic */ class AppCategoryHintHelper$$ExternalSyntheticLambda5
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppCategoryHintHelper f$0;

    public /* synthetic */ AppCategoryHintHelper$$ExternalSyntheticLambda5(
            AppCategoryHintHelper appCategoryHintHelper, int i) {
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
                appCategoryHintHelper.sendAppCategoryBroadcast(
                        appCategoryHintHelper.mCategoryMap.containsKey(str)
                                ? ((Integer) appCategoryHintHelper.mCategoryMap.get(str)).intValue()
                                : -1,
                        str);
                break;
            default:
                appCategoryHintHelper.sendAppCategoryBroadcast(
                        appCategoryHintHelper.mAppCategoryFilter.getPackageCategory(str), str);
                break;
        }
    }
}
