package com.android.wm.shell.transition;

import android.window.TransitionInfo;
import java.util.ArrayList;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MultiTaskingTransitionProvider$$ExternalSyntheticLambda1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ArrayList arrayList = MultiTaskingTransitionProvider.sForceHidingAnimators;
        return ((TransitionInfo.Change) obj).hasFlags(32);
    }
}
