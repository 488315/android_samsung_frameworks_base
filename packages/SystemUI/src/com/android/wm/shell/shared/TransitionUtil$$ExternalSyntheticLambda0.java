package com.android.wm.shell.shared;

import android.window.TransitionInfo;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public final /* synthetic */ class TransitionUtil$$ExternalSyntheticLambda0 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((TransitionInfo.Change) obj).getTaskInfo().taskId;
    }
}
