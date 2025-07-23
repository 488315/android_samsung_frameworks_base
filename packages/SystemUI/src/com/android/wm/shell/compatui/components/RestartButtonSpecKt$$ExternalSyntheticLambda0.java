package com.android.wm.shell.compatui.components;

import com.android.wm.shell.compatui.api.CompatUIInfo;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class RestartButtonSpecKt$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return Boolean.valueOf(((CompatUIInfo) obj).taskInfo.appCompatTaskInfo.isTopActivityInSizeCompat());
    }
}
