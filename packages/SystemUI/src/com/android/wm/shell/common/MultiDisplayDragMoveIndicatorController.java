package com.android.wm.shell.common;

import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.MultiDisplayDragMoveIndicatorSurface;
import java.util.LinkedHashMap;
import java.util.Map;

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
