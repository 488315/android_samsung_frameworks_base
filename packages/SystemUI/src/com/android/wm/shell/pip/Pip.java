package com.android.wm.shell.pip;

import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public interface Pip {
    void addOnIsInPipStateChangedListener(Consumer consumer);

    void addPipExclusionBoundsChangeListener(Consumer consumer);

    default boolean isExitingPipToLastParent(int i) {
        return false;
    }

    void onSystemUiStateChanged(long j, boolean z);

    void removeOnIsInPipStateChangedListener(EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0);

    void removePipExclusionBoundsChangeListener(Consumer consumer);

    void showPictureInPictureMenu();

    default void registerPipTransitionCallback(WMShell.AnonymousClass7 anonymousClass7, Executor executor) {
    }
}
