package com.android.server.enterprise.restriction;

import com.samsung.android.knox.restriction.RestrictionPolicy;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RestrictionPolicy$$ExternalSyntheticLambda3 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String[] strArr = RestrictionPolicy.excludedAdminList;
        return ((RestrictionPolicy.USBInterface) obj).toString();
    }
}
