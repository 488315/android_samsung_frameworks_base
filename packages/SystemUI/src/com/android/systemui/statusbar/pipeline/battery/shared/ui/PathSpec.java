package com.android.systemui.statusbar.pipeline.battery.shared.ui;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PathSpec {
    public final Path path;
    public final float viewportHeight;
    public final float viewportWidth;

    public /* synthetic */ PathSpec(Path path, float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(path, f, f2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PathSpec)) {
            return false;
        }
        PathSpec pathSpec = (PathSpec) obj;
        return Intrinsics.areEqual(this.path, pathSpec.path) && Dp.m836equalsimpl0(this.viewportWidth, pathSpec.viewportWidth) && Dp.m836equalsimpl0(this.viewportHeight, pathSpec.viewportHeight);
    }

    public final int hashCode() {
        int hashCode = this.path.hashCode() * 31;
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.viewportHeight) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.viewportWidth, hashCode, 31);
    }

    public final String toString() {
        String m837toStringimpl = Dp.m837toStringimpl(this.viewportWidth);
        String m837toStringimpl2 = Dp.m837toStringimpl(this.viewportHeight);
        StringBuilder sb = new StringBuilder("PathSpec(path=");
        sb.append(this.path);
        sb.append(", viewportWidth=");
        sb.append(m837toStringimpl);
        sb.append(", viewportHeight=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, m837toStringimpl2, ")");
    }

    private PathSpec(Path path, float f, float f2) {
        this.path = path;
        this.viewportWidth = f;
        this.viewportHeight = f2;
    }
}
