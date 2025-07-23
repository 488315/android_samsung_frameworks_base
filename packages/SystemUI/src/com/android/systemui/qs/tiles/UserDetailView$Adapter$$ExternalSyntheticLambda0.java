package com.android.systemui.qs.tiles;

import com.android.systemui.qs.tiles.UserDetailView;
import com.android.systemui.user.data.source.UserRecord;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserDetailView$Adapter$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = UserDetailView.Adapter.$r8$clinit;
        return !((UserRecord) obj).isManageUsers;
    }
}
