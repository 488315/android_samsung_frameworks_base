package com.android.server.media;

import java.util.function.Predicate;

public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda20
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ MediaRouter2ServiceImpl$$ExternalSyntheticLambda20(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                if (((MediaRouter2ServiceImpl.RouterRecord) obj).mUid == i2) {}
                break;
            case 1:
                if (((MediaRouter2ServiceImpl.RouterRecord) obj).mUid == i2) {}
                break;
            default:
                if (((MediaRouter2ServiceImpl.ManagerRecord) obj).mOwnerUid == i2) {}
                break;
        }
        return false;
    }
}
