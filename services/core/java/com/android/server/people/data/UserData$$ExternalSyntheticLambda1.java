package com.android.server.people.data;

import android.text.TextUtils;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class UserData$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserData f$0;

    public /* synthetic */ UserData$$ExternalSyntheticLambda1(UserData userData, int i) {
        this.$r8$classId = i;
        this.f$0 = userData;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        UserData userData = this.f$0;
        String str = (String) obj;
        switch (i) {
            case 0:
                return TextUtils.equals(userData.mDefaultDialer, str);
            default:
                return TextUtils.equals(userData.mDefaultSmsApp, str);
        }
    }
}
