package com.android.server.companion.association;

import android.companion.AssociationInfo;

import java.util.function.Predicate;

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
