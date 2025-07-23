package com.android.systemui.shared.rotation;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class RotationButtonController$TaskStackListenerImpl$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((ActivityManagerWrapper) obj).getRunningTask();
    }
}
