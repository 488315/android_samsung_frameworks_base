package com.android.wm.shell.shared;

import android.window.TransitionInfo;
import java.util.function.ToIntFunction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class TransitionUtil$$ExternalSyntheticLambda0 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((TransitionInfo.Change) obj).getTaskInfo().taskId;
    }
}
