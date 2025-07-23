package com.android.systemui.recents;

import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.drawable.shapes.PathShape;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TriangleShape extends PathShape {
    public final Path mTriangularPath;

    public TriangleShape(Path path, float f, float f2) {
        super(path, f, f2);
        this.mTriangularPath = path;
    }

    public static TriangleShape createHorizontal(float f, float f2, boolean z) {
        Path path = new Path();
        if (z) {
            path.moveTo(0.0f, f2 / 2.0f);
            path.lineTo(f, f2);
            path.lineTo(f, 0.0f);
            path.close();
        } else {
            path.moveTo(0.0f, f2);
            path.lineTo(f, f2 / 2.0f);
            path.lineTo(0.0f, 0.0f);
            path.close();
        }
        return new TriangleShape(path, f, f2);
    }

    @Override // android.graphics.drawable.shapes.Shape
    public final void getOutline(Outline outline) {
        outline.setPath(this.mTriangularPath);
    }
}
