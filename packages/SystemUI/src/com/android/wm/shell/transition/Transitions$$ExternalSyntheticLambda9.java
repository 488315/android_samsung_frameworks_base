package com.android.wm.shell.transition;

import android.util.Log;
import com.android.wm.shell.transition.Transitions;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class Transitions$$ExternalSyntheticLambda9 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        boolean z = Transitions.DEBUG_START_TRANSITION;
        Log.e("ShellTransitions", "runOnIdleSafely: " + ((Transitions.ActiveTransition) obj));
    }
}
