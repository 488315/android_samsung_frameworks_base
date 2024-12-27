package com.android.server.people.data;

import java.util.function.Function;

public final /* synthetic */ class UserData$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ UserData f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ UserData$$ExternalSyntheticLambda0(UserData userData, String str) {
        this.f$0 = userData;
        this.f$1 = str;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        UserData userData = this.f$0;
        String str = this.f$1;
        userData.getClass();
        return new PackageData(
                str,
                userData.mUserId,
                new UserData$$ExternalSyntheticLambda1(userData, 0),
                new UserData$$ExternalSyntheticLambda1(userData, 1),
                userData.mScheduledExecutorService,
                userData.mPerUserPeopleDataDir);
    }
}
