package com.android.wm.shell.splitscreen;

import android.window.TransitionInfo;
import com.android.wm.shell.shared.TransitionUtil;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda12 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        TransitionInfo.Change change = (TransitionInfo.Change) obj;
        return TransitionUtil.isClosingMode(change.getMode()) && TransitionUtil.isHomeOrRecents(change);
    }
}
