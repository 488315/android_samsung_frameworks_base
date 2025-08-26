package androidx.compose.animation.graphics.vector;

import androidx.compose.ui.graphics.vector.PathNode;
import java.util.List;

/* loaded from: classes.dex */
public final class PropertyValuesHolderPath extends PropertyValuesHolder1D<List<? extends PathNode>> {
    public final List animatorKeyframes;

    public PropertyValuesHolderPath(String str, List<Keyframe<List<PathNode>>> list) {
        super(str, null);
        this.animatorKeyframes = list;
    }
}
