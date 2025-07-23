package com.android.systemui.biometrics.ui.binder;

import com.android.internal.widget.LockPatternView;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OnPatternDetectedListener implements LockPatternView.OnPatternListener {
    public final Function1 onDetected;

    public OnPatternDetectedListener(Function1 function1) {
        this.onDetected = function1;
    }

    public final void onPatternDetected(List list) {
        this.onDetected.mo779invoke(list);
    }

    public final void onPatternCleared() {
    }

    public final void onPatternStart() {
    }

    public final void onPatternCellAdded(List list) {
    }
}
