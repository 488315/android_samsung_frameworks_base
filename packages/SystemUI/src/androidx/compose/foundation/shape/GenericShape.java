package androidx.compose.foundation.shape;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
public final class GenericShape implements Shape {
    public final Function3 builder;

    public GenericShape(Function3 function3) {
        this.builder = function3;
    }

    @Override // androidx.compose.ui.graphics.Shape
    /* renamed from: createOutline-Pq9zytI */
    public final Outline mo41createOutlinePq9zytI(long j, LayoutDirection layoutDirection, Density density) {
        AndroidPath androidPathPath = AndroidPath_androidKt.Path();
        this.builder.invoke(androidPathPath, Size.m415boximpl(j), layoutDirection);
        androidPathPath.internalPath.close();
        return new Outline.Generic(androidPathPath);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        GenericShape genericShape = obj instanceof GenericShape ? (GenericShape) obj : null;
        return (genericShape != null ? genericShape.builder : null) == this.builder;
    }

    public final int hashCode() {
        return this.builder.hashCode();
    }
}
