package androidx.compose.animation.graphics.vector;

import androidx.compose.ui.graphics.vector.PathNode;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PropertyValuesHolderPath extends PropertyValuesHolder1D<List<? extends PathNode>> {
    public final List animatorKeyframes;

    public PropertyValuesHolderPath(String str, List<Keyframe<List<PathNode>>> list) {
        super(str, null);
        this.animatorKeyframes = list;
    }
}
