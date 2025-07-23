package androidx.window.embedding;

import android.content.res.Configuration;
import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.core.view.WindowInsetsCompat;
import androidx.window.core.Bounds;
import androidx.window.layout.WindowLayoutInfo;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ParentContainerInfo {
    public final Configuration configuration;
    public final float density;
    public final Bounds windowBounds;
    public final WindowInsetsCompat windowInsets;
    public final WindowLayoutInfo windowLayoutInfo;

    public ParentContainerInfo(Bounds bounds, WindowLayoutInfo windowLayoutInfo, WindowInsetsCompat windowInsetsCompat, Configuration configuration, float f) {
        this.windowBounds = bounds;
        this.windowLayoutInfo = windowLayoutInfo;
        this.windowInsets = windowInsetsCompat;
        this.configuration = configuration;
        this.density = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ParentContainerInfo)) {
            return false;
        }
        ParentContainerInfo parentContainerInfo = (ParentContainerInfo) obj;
        return Intrinsics.areEqual(this.windowBounds, parentContainerInfo.windowBounds) && Intrinsics.areEqual(this.windowLayoutInfo, parentContainerInfo.windowLayoutInfo) && Intrinsics.areEqual(this.windowInsets, parentContainerInfo.windowInsets) && Intrinsics.areEqual(this.configuration, parentContainerInfo.configuration) && Float.compare(this.density, parentContainerInfo.density) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.density) + ((this.configuration.hashCode() + ((this.windowInsets.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.windowLayoutInfo.displayFeatures, this.windowBounds.hashCode() * 31, 31)) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ParentContainerInfo(windowBounds=");
        sb.append(this.windowBounds);
        sb.append(", windowLayoutInfo=");
        sb.append(this.windowLayoutInfo);
        sb.append(", windowInsets=");
        sb.append(this.windowInsets);
        sb.append(", configuration=");
        sb.append(this.configuration);
        sb.append(", density=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.density, ')');
    }
}
