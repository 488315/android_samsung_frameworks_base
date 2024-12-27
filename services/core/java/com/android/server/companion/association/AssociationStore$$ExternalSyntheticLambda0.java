package com.android.server.companion.association;

import android.companion.AssociationInfo;
import android.net.MacAddress;

import java.util.function.Predicate;

public final /* synthetic */ class AssociationStore$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ AssociationStore$$ExternalSyntheticLambda0(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        String str = this.f$0;
        AssociationInfo associationInfo = (AssociationInfo) obj;
        switch (i) {
            case 0:
                if (associationInfo.getDeviceMacAddress() == null
                        || !associationInfo
                                .getDeviceMacAddress()
                                .equals(MacAddress.fromString(str))) {}
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                if (associationInfo.getDeviceMacAddress() == null
                        || !associationInfo
                                .getDeviceMacAddress()
                                .equals(MacAddress.fromString(str))) {}
                break;
        }
        return associationInfo.getPackageName().equals(str);
    }
}
