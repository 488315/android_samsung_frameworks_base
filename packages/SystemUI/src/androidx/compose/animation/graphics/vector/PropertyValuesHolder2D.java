package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.Easing;
import androidx.compose.ui.graphics.vector.PathNode;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PropertyValuesHolder2D extends PropertyValuesHolder<Pair<? extends Float, ? extends Float>> {
    public final Easing interpolator;
    public final List pathData;
    public final String xPropertyName;
    public final String yPropertyName;

    public PropertyValuesHolder2D(String str, String str2, List<? extends PathNode> list, Easing easing) {
        super(null);
        this.xPropertyName = str;
        this.yPropertyName = str2;
        this.pathData = list;
        this.interpolator = easing;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PropertyValuesHolder2D)) {
            return false;
        }
        PropertyValuesHolder2D propertyValuesHolder2D = (PropertyValuesHolder2D) obj;
        return Intrinsics.areEqual(this.xPropertyName, propertyValuesHolder2D.xPropertyName) && Intrinsics.areEqual(this.yPropertyName, propertyValuesHolder2D.yPropertyName) && Intrinsics.areEqual(this.pathData, propertyValuesHolder2D.pathData) && Intrinsics.areEqual(this.interpolator, propertyValuesHolder2D.interpolator);
    }

    public final int hashCode() {
        return this.interpolator.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.pathData, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.xPropertyName.hashCode() * 31, 31, this.yPropertyName), 31);
    }

    public final String toString() {
        return "PropertyValuesHolder2D(xPropertyName=" + this.xPropertyName + ", yPropertyName=" + this.yPropertyName + ", pathData=" + this.pathData + ", interpolator=" + this.interpolator + ')';
    }
}
