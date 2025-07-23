package com.android.wm.shell.common;

import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.MultiDisplayDragMoveIndicatorSurface;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplayDragMoveIndicatorController {
    public final ShellExecutor desktopExecutor;
    public final DisplayController displayController;
    public final Map dragIndicators = new LinkedHashMap();
    public final MultiDisplayDragMoveIndicatorSurface.Factory indicatorSurfaceFactory;
    public final RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer;

    public MultiDisplayDragMoveIndicatorController(DisplayController displayController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, MultiDisplayDragMoveIndicatorSurface.Factory factory, ShellExecutor shellExecutor) {
        this.displayController = displayController;
        this.rootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.indicatorSurfaceFactory = factory;
        this.desktopExecutor = shellExecutor;
    }
}
