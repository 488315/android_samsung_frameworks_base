package com.android.server.companion.association;

import android.companion.AssociationInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AssociationStore$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        AssociationInfo associationInfo = (AssociationInfo) obj;
        switch (this.$r8$classId) {
            case 0:
                return associationInfo.isActive();
            default:
                return associationInfo.isRevoked();
        }
    }
}
