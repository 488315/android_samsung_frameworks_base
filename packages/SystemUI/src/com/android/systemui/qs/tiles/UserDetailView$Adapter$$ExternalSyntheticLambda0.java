package com.android.systemui.qs.tiles;

import com.android.systemui.user.data.source.UserRecord;
import java.util.function.Predicate;

public final /* synthetic */ class UserDetailView$Adapter$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !((UserRecord) obj).isManageUsers;
    }
}
