package com.android.wm.shell.util;

import android.window.TransitionInfo;
import java.util.function.ToIntFunction;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TransitionUtil$$ExternalSyntheticLambda0 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((TransitionInfo.Change) obj).getTaskInfo().taskId;
    }
}
