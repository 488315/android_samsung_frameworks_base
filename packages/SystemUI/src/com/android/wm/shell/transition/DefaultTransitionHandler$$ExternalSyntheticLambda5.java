package com.android.wm.shell.transition;

import android.window.TransitionInfo;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda5 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        TransitionInfo.Change change = (TransitionInfo.Change) obj;
        return change.getTaskInfo() != null && change.getTaskInfo().getWindowingMode() == 6;
    }
}
