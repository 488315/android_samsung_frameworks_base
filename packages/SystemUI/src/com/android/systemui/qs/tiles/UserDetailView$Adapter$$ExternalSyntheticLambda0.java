package com.android.systemui.qs.tiles;

import com.android.systemui.user.data.source.UserRecord;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class UserDetailView$Adapter$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !((UserRecord) obj).isManageUsers;
    }
}
