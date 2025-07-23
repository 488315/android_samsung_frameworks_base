package com.android.wm.shell.splitscreen;

import android.window.WindowContainerTransaction;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda5 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((WindowContainerTransaction.Change) obj).getWindowingMode() == 5;
    }
}
