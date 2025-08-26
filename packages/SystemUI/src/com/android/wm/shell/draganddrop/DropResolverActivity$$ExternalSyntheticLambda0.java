package com.android.wm.shell.draganddrop;

import android.content.pm.ResolveInfo;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class DropResolverActivity$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = DropResolverActivity.$r8$clinit;
        return ((ResolveInfo) obj).activityInfo != null;
    }
}
