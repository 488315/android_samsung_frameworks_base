package com.android.wm.shell.desktopmode;

import android.content.Context;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.transition.Transitions;
import java.util.Optional;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DefaultDragToDesktopTransitionHandler extends DragToDesktopTransitionHandler {
    public DefaultDragToDesktopTransitionHandler(Context context, Transitions transitions, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesktopUserRepositories desktopUserRepositories, InteractionJankMonitor interactionJankMonitor, Optional<BubbleController> optional, DesktopState desktopState) {
        this(context, transitions, rootTaskDisplayAreaOrganizer, desktopUserRepositories, interactionJankMonitor, optional, null, desktopState, 64, null);
    }

    @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler
    public final DragToDesktopTransitionHandler.DragToDesktopLayers calculateStartDragToDesktopLayers(TransitionInfo transitionInfo) {
        return new DragToDesktopTransitionHandler.DragToDesktopLayers(transitionInfo.getChanges().size(), transitionInfo.getChanges().size() * 2, transitionInfo.getChanges().size() * 3, transitionInfo.getChanges().size() * 3);
    }

    public /* synthetic */ DefaultDragToDesktopTransitionHandler(Context context, Transitions transitions, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesktopUserRepositories desktopUserRepositories, InteractionJankMonitor interactionJankMonitor, Optional optional, Supplier supplier, DesktopState desktopState, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, transitions, rootTaskDisplayAreaOrganizer, desktopUserRepositories, interactionJankMonitor, optional, (i & 64) != 0 ? new Supplier() { // from class: com.android.wm.shell.desktopmode.DefaultDragToDesktopTransitionHandler.1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new SurfaceControl.Transaction();
            }
        } : supplier, desktopState);
    }

    public DefaultDragToDesktopTransitionHandler(Context context, Transitions transitions, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesktopUserRepositories desktopUserRepositories, InteractionJankMonitor interactionJankMonitor, Optional<BubbleController> optional, Supplier<SurfaceControl.Transaction> supplier, DesktopState desktopState) {
        super(context, transitions, rootTaskDisplayAreaOrganizer, desktopUserRepositories, interactionJankMonitor, optional, supplier, desktopState, null);
    }
}
