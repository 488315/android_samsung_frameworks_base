package com.android.systemui.accessibility.floatingmenu;

import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.drawable.shapes.PathShape;

public final class TriangleStrokeShape extends PathShape {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Path mTriangularPath;

    public TriangleStrokeShape(Path path, float f, float f2) {
        super(path, f, f2);
        this.mTriangularPath = path;
    }

    @Override // android.graphics.drawable.shapes.Shape
    public final void getOutline(Outline outline) {
        outline.setPath(this.mTriangularPath);
    }
}
