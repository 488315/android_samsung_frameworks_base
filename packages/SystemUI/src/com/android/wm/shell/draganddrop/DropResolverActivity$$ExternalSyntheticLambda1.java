package com.android.wm.shell.draganddrop;

import android.content.pm.ResolveInfo;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final /* synthetic */ class DropResolverActivity$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = DropResolverActivity.$r8$clinit;
        return ((ResolveInfo) obj).activityInfo.getComponentName();
    }
}
