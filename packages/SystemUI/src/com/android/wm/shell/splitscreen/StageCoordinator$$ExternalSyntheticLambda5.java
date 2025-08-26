package com.android.wm.shell.splitscreen;

import android.window.WindowContainerTransaction;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda5 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((WindowContainerTransaction.Change) obj).getWindowingMode() == 5;
    }
}
