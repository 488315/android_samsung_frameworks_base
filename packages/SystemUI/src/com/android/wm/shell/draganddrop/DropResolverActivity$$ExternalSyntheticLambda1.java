package com.android.wm.shell.draganddrop;

import android.content.pm.ResolveInfo;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DropResolverActivity$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = DropResolverActivity.$r8$clinit;
        return ((ResolveInfo) obj).activityInfo.getComponentName();
    }
}
