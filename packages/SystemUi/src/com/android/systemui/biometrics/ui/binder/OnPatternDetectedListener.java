package com.android.systemui.biometrics.ui.binder;

import com.android.internal.widget.LockPatternView;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class OnPatternDetectedListener implements LockPatternView.OnPatternListener {
    public final Function1 onDetected;

    public OnPatternDetectedListener(Function1 function1) {
        this.onDetected = function1;
    }

    public final void onPatternDetected(List list) {
        this.onDetected.invoke(list);
    }

    public final void onPatternCellAdded(List list) {
    }

    public final void onPatternCleared() {
    }

    public final void onPatternStart() {
    }
}
