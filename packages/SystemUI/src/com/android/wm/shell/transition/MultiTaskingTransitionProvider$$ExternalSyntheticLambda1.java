package com.android.wm.shell.transition;

import android.window.TransitionInfo;
import java.util.ArrayList;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class MultiTaskingTransitionProvider$$ExternalSyntheticLambda1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ArrayList arrayList = MultiTaskingTransitionProvider.sForceHidingAnimators;
        return ((TransitionInfo.Change) obj).hasFlags(32);
    }
}
