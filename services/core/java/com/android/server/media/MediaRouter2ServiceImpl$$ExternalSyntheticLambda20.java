package com.android.server.media;

import com.android.server.media.MediaRouter2ServiceImpl;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda20 implements Predicate {
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
                if (((MediaRouter2ServiceImpl.RouterRecord) obj).mUid == i2) {
                }
                break;
            case 1:
                if (((MediaRouter2ServiceImpl.RouterRecord) obj).mUid == i2) {
                }
                break;
            default:
                if (((MediaRouter2ServiceImpl.ManagerRecord) obj).mOwnerUid == i2) {
                }
                break;
        }
        return false;
    }
}
