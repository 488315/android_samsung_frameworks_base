package com.android.systemui.biometrics.ui.binder;

import com.android.internal.widget.LockPatternView;
import java.util.List;
import kotlin.jvm.functions.Function1;

public final class OnPatternDetectedListener implements LockPatternView.OnPatternListener {
    public final Function1 onDetected;

    public OnPatternDetectedListener(Function1 function1) {
        this.onDetected = function1;
    }

    public final void onPatternDetected(List list) {
        this.onDetected.invoke(list);
    }

    public final void onPatternCleared() {
    }

    public final void onPatternStart() {
    }

    public final void onPatternCellAdded(List list) {
    }
}
