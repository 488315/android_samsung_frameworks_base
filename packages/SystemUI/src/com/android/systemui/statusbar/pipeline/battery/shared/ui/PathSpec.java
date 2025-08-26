package com.android.systemui.statusbar.pipeline.battery.shared.ui;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        return Intrinsics.areEqual(this.path, pathSpec.path) && Dp.m838equalsimpl0(this.viewportWidth, pathSpec.viewportWidth) && Dp.m838equalsimpl0(this.viewportHeight, pathSpec.viewportHeight);
    }

    public final int hashCode() {
        int iHashCode = this.path.hashCode() * 31;
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.viewportHeight) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.viewportWidth, iHashCode, 31);
    }

    public final String toString() {
        String strM839toStringimpl = Dp.m839toStringimpl(this.viewportWidth);
        String strM839toStringimpl2 = Dp.m839toStringimpl(this.viewportHeight);
        StringBuilder sb = new StringBuilder("PathSpec(path=");
        sb.append(this.path);
        sb.append(", viewportWidth=");
        sb.append(strM839toStringimpl);
        sb.append(", viewportHeight=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, strM839toStringimpl2, ")");
    }

    private PathSpec(Path path, float f, float f2) {
        this.path = path;
        this.viewportWidth = f;
        this.viewportHeight = f2;
    }
}
