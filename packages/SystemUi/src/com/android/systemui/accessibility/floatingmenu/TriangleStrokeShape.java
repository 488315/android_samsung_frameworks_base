package com.android.systemui.accessibility.floatingmenu;

import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.drawable.shapes.PathShape;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
