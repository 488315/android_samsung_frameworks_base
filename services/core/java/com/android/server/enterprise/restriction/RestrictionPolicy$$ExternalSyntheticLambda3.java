package com.android.server.enterprise.restriction;

import com.samsung.android.knox.restriction.RestrictionPolicy;

import java.util.function.Function;

public final /* synthetic */ class RestrictionPolicy$$ExternalSyntheticLambda3 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String[] strArr = RestrictionPolicy.excludedAdminList;
        return ((RestrictionPolicy.USBInterface) obj).toString();
    }
}
