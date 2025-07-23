package com.android.wm.shell.pip;

import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface Pip {
    void addOnIsInPipStateChangedListener(Consumer consumer);

    void addPipExclusionBoundsChangeListener(Consumer consumer);

    void onSystemUiStateChanged(long j, boolean z);

    void removeOnIsInPipStateChangedListener(EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0);

    void removePipExclusionBoundsChangeListener(Consumer consumer);

    void showPictureInPictureMenu();

    default void registerPipTransitionCallback(WMShell.AnonymousClass7 anonymousClass7, Executor executor) {
    }
}
