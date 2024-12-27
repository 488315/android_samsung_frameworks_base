package com.android.server.companion.association;

import android.companion.AssociationInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AssociationStore$$ExternalSyntheticLambda7 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ AssociationStore$$ExternalSyntheticLambda7(int i, int i2, String str) {
        this.$r8$classId = i2;
        this.f$0 = str;
        this.f$1 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = this.f$0;
                int i = this.f$1;
                AssociationInfo associationInfo = (AssociationInfo) obj;
                if (!str.equals(associationInfo.getPackageName())
                        || associationInfo.getUserId() != i
                        || !associationInfo.isPending()) {}
                break;
            default:
                String str2 = this.f$0;
                int i2 = this.f$1;
                AssociationInfo associationInfo2 = (AssociationInfo) obj;
                if (!str2.equals(associationInfo2.getPackageName())
                        || associationInfo2.getUserId() != i2
                        || !associationInfo2.isRevoked()) {}
                break;
        }
        return false;
    }
}
