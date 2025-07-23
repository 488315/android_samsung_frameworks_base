package com.android.wm.shell.common.pip;

import android.window.DesktopModeFlags;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PipDesktopState {
    public final Optional desktopUserRepositoriesOptional;
    public final Optional dragToDesktopTransitionHandlerOptional;
    public final PipDisplayLayoutState pipDisplayLayoutState;
    public final RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer;

    public PipDesktopState(PipDisplayLayoutState pipDisplayLayoutState, Optional<DesktopUserRepositories> optional, Optional<DragToDesktopTransitionHandler> optional2, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        this.pipDisplayLayoutState = pipDisplayLayoutState;
        this.desktopUserRepositoriesOptional = optional;
        this.dragToDesktopTransitionHandlerOptional = optional2;
        this.rootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
    }

    public final boolean isDesktopWindowingPipEnabled() {
        return DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PIP.isTrue() && this.desktopUserRepositoriesOptional.isPresent() && this.dragToDesktopTransitionHandlerOptional.isPresent();
    }

    public final boolean isPipInDesktopMode() {
        if (!isDesktopWindowingPipEnabled()) {
            return false;
        }
        return ((DesktopUserRepositories) this.desktopUserRepositoriesOptional.get()).getCurrent().isAnyDeskActive(this.pipDisplayLayoutState.mDisplayId);
    }
}
