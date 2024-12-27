package com.android.server.media;

import android.text.TextUtils;
import com.android.server.media.MediaRouter2ServiceImpl;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda22 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaRouter2ServiceImpl$$ExternalSyntheticLambda22(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return TextUtils.equals(((MediaRouter2ServiceImpl.ManagerRecord) obj).mOwnerPackageName, (String) obj2);
            case 1:
                MediaRouter2ServiceImpl mediaRouter2ServiceImpl = (MediaRouter2ServiceImpl) obj2;
                MediaRouter2ServiceImpl.ManagerRecord managerRecord = (MediaRouter2ServiceImpl.ManagerRecord) obj;
                if (managerRecord.mScanningState == 1) {
                    if (mediaRouter2ServiceImpl.mActivityManager.getPackageImportance(managerRecord.mOwnerPackageName) <= 100) {
                        return true;
                    }
                }
                return managerRecord.mScanningState == 2;
            default:
                MediaRouter2ServiceImpl.RouterRecord routerRecord = (MediaRouter2ServiceImpl.RouterRecord) obj;
                return ((MediaRouter2ServiceImpl) obj2).mActivityManager.getPackageImportance(routerRecord.mPackageName) <= 100 || routerRecord.mScanningState == 2;
        }
    }
}
